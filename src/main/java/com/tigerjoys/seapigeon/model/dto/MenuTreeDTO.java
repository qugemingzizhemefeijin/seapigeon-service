package com.tigerjoys.seapigeon.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜单树DTO
 * </p>
 *
 * @author Mybatis Plus
 */
public class MenuTreeDTO extends TreeNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes = "菜单名称")
    private String menuName;
    @ApiModelProperty(notes = "类型:1:目录,2:菜单,3:按钮")
    private Integer menuType;
    @ApiModelProperty(notes = "映射地址")
    private String router;
    @ApiModelProperty(notes = "路径")
    private String path;
    @ApiModelProperty(notes = "图标")
    private String icon;
    @ApiModelProperty(notes = "别名")
    private String alias;
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	public String getRouter() {
		return router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
