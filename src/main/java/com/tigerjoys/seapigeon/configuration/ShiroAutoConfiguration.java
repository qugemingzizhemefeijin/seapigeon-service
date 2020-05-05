package com.tigerjoys.seapigeon.configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import com.tigerjoys.seapigeon.configuration.shiro.JWTFilter;
import com.tigerjoys.seapigeon.configuration.shiro.JWTRealm;
import com.tigerjoys.seapigeon.service.IResourceService;

@Configuration
public class ShiroAutoConfiguration {
	
	@Value("${server.servlet.context-path:/}")
    private String contextPath;
	
	/**
	 * 配置securityManager 管理subject（默认）,并把自定义realm交由manager
	 * @param realm
	 * @return
	 */
	@Bean
    public SecurityManager securityManager(@Autowired JWTRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        /*
         * 关闭shiro自带的session/////非web关闭sessionManager(官网有介绍)
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        evaluator.setSessionStorageEnabled(false);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRealm(realm);
        return securityManager;
    }

	/**
	 * 拦截链
	 * @param securityManager
	 * @param resourceService
	 * @return ShiroFilterFactoryBean
	 */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager, @Autowired IResourceService resourceService) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        JWTFilter filter = new JWTFilter();
        filter.setAuthzScheme("Bearer");
        filter.setUrlPathHelper(new UrlPathHelper());
        filter.setResourceService(resourceService);
        filter.setPathMatcher(new AntPathMatcher());
        filter.setContextPath(cleanContextPath(contextPath));
        filters.put("jwt", filter);
        shiroFilter.setFilters(filters);
        
        //必须为LinkedHashMap 否则anon不生效
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**/**.*", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/**", "jwt");
        filterMap.put("/actuator/prometheus","anon");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        
        return shiroFilter;
    }

    private String cleanContextPath(String contextPath) {
        if (StringUtils.hasText(contextPath) && contextPath.endsWith("/")) {
            return contextPath.substring(0, contextPath.length() - 1);
        }
        return contextPath;
    }

}
