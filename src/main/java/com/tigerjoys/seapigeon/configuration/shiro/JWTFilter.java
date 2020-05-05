package com.tigerjoys.seapigeon.configuration.shiro;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.tigerjoys.seapigeon.common.enums.ErrorCodeEnum;
import com.tigerjoys.seapigeon.cons.APICons;
import com.tigerjoys.seapigeon.model.dto.ResourcePermDTO;
import com.tigerjoys.seapigeon.service.IResourceService;
import com.tigerjoys.seapigeon.utils.JWTUtils;
import com.tigerjoys.seapigeon.utils.ResponseUtils;

/**
 * JWT过滤器 适用于shiro
 *
 * @author Caratacus
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private PathMatcher pathMatcher;
    private IResourceService resourceService;
    private UrlPathHelper urlPathHelper;
    private String contextPath;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //获取请求token
        String token = getToken(WebUtils.toHttp(servletRequest));
        return StringUtils.isBlank(token) ? null : new JWTToken(token);
    }

    /**
     * 判断是否允许请求
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        request.setAttribute(APICons.API_BEGIN_TIME, System.currentTimeMillis());
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        String token = getToken(httpRequest);
        String method = httpRequest.getMethod();
        String requestUri = urlPathHelper.getOriginatingRequestUri(httpRequest);
        if (StringUtils.isNotEmpty(contextPath)) {
            requestUri = requestUri.replaceFirst(contextPath, "");
        }
        Optional<String> optional = resourceService.getResourcePerms(method)
                .stream()
                .filter(match(method, requestUri))
                .map(ResourcePermDTO::getMapping)
                .min(pathMatcher.getPatternComparator(requestUri));
        request.setAttribute(APICons.API_REQURL, requestUri);
        request.setAttribute(APICons.API_METHOD, method);
        if (optional.isPresent()) {
            request.setAttribute(APICons.API_MAPPING, optional.get());
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        if (Objects.isNull(token)) {
            List<ResourcePermDTO> openPerms = resourceService.getOpenPerms();
            boolean match = anyMatch(openPerms, method, requestUri);
            if (!match) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            return match;
        }
        if (isLoginRequest(request, response)) {
            if (executeLogin(request, response)) {
                Integer uid = JWTUtils.getUid(token);
                request.setAttribute(APICons.API_UID, uid);
                Set<ResourcePermDTO> perms = resourceService.getUserResourcePerms(uid);
                return anyMatch(perms, method, requestUri);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return sendUnauthorizedFail(request, response);
            }
        }
        return false;

    }

    /**
     * 处理未经验证的请求
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        switch (httpResponse.getStatus()) {
            case HttpServletResponse.SC_NOT_FOUND:
                return sendNotFoundFail(request, response);
            case HttpServletResponse.SC_UNAUTHORIZED:
                return sendUnauthorizedFail(request, response);
            default:
                return sendForbiddenFail(request, response);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return sendUnauthorizedFail(request, response);
    }

    /**
     * 获取请求的token
     */
    protected String getToken(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader(AUTHORIZATION_HEADER);
        return StringUtils.isBlank(token) ? null : token.replaceFirst("Bearer ", "");
    }

    /**
     * 无权限
     */
    protected boolean sendForbiddenFail(ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail(WebUtils.toHttp(request), WebUtils.toHttp(response), ErrorCodeEnum.FORBIDDEN);
        return false;
    }

    /**
     * 路径不存在
     */
    protected boolean sendNotFoundFail(ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail(WebUtils.toHttp(request), WebUtils.toHttp(response), ErrorCodeEnum.NOT_FOUND);
        return false;
    }

    /**
     * 未认证
     */
    protected boolean sendUnauthorizedFail(ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail(WebUtils.toHttp(request), WebUtils.toHttp(response), ErrorCodeEnum.UNAUTHORIZED);
        return false;
    }


    /**
     * 是否任意匹配权限URL
     *
     * @param perms
     * @return
     */
    protected boolean anyMatch(Collection<ResourcePermDTO> perms, String method, String requestUri) {
        return perms.stream().anyMatch(match(method, requestUri));
    }

    /**
     * 匹配请求方法与路径
     *
     * @param method
     * @param requestUri
     * @return
     */
    private Predicate<ResourcePermDTO> match(String method, String requestUri) {
        return res -> res.getMethod().equalsIgnoreCase(method) && pathMatcher.match(res.getMapping(), requestUri);
    }

    /**
     * 执行登录方法(由自定义realm判断,吃掉异常返回false)
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        try {
            return super.executeLogin(request, response);
        } catch (Exception ignored) {
        }
        return false;
    }
    
    /**
     * 请求是否已经登录（携带token）
     */
    @Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		return super.isLoginAttempt(request, response);
	}

    /**
     * 构建未授权的请求返回,filter层的异常不受exceptionAdvice控制,这里返回401,把返回的json丢到response中
     */
	@Override
	protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
		return super.sendChallenge(request, response);
	}

	/**
	 * 请求前处理,处理跨域
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		return super.preHandle(request, response);
	}

	public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}