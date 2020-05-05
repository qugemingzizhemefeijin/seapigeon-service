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
package com.tigerjoys.seapigeon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tigerjoys.seapigeon.framework.service.impl.BaseServiceImpl;
import com.tigerjoys.seapigeon.inter.entity.Role;
import com.tigerjoys.seapigeon.inter.entity.RoleMenu;
import com.tigerjoys.seapigeon.inter.mapper.RoleMapper;
import com.tigerjoys.seapigeon.model.dto.RoleDTO;
import com.tigerjoys.seapigeon.service.IRoleMenuService;
import com.tigerjoys.seapigeon.service.IRoleService;
import com.tigerjoys.seapigeon.utils.TypeUtils;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Caratacus
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public IPage<RoleDTO> pageRoleDTO(Page<Role> page, String roleName) {
        IPage<Role> rolePage = query().like(StringUtils.isNotBlank(roleName), Role::getRoleName, roleName).page(page);
        return rolePage.convert(role -> {
            RoleDTO roleDTO = role.convert(RoleDTO.class);
            roleDTO.setMenuIds(roleMenuService.query()
                    .select(RoleMenu::getMenuId)
                    .eq(RoleMenu::getRoleId, role.getId())
                    .listObjs(TypeUtils::castToInt)
            );
            return roleDTO;
        });
    }
}
