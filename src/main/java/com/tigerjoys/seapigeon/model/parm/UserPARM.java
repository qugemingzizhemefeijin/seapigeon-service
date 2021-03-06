/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.tigerjoys.seapigeon.model.parm;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.tigerjoys.seapigeon.common.enums.StatusEnum;
import com.tigerjoys.seapigeon.cons.Regex;
import com.tigerjoys.seapigeon.framework.model.Convert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户PARM
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
public class UserPARM extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "登陆名")
    @NotBlank(groups = {Create.class, Update.class}, message = "用户名不能为空")
    private String loginName;
    @ApiModelProperty(notes = "昵称")
    @NotBlank(groups = {Create.class, Update.class}, message = "昵称不能为空")
    private String nickname;
    @Email(groups = {Create.class, Update.class}, message = "邮箱格式不正确")
    @ApiModelProperty(notes = "邮箱")
    private String email;
    @Pattern(groups = {Create.class, Update.class}, regexp = Regex.PHONE, message = "手机号码格式不正确")
    @ApiModelProperty(notes = "手机号")
    private String phone;
    @NotNull(groups = Status.class, message = "用户状态不能为空")
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private StatusEnum status;
    @ApiModelProperty(notes = "用户角色ID")
    @NotEmpty(groups = {Create.class, Update.class}, message = "用户角色不能为空")
    private List<Integer> roleIds;

    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }

}
