package com.tigerjoys.seapigeon.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.tigerjoys.seapigeon.framework.model.BaseModel;

/**
 * <p>
 * 树形节点
 * </p>
 *
 * @author Caratacus
 */
public class TreeNode extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Integer parentId;

    protected List<TreeNode> childrens = new ArrayList<>();

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<TreeNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<TreeNode> childrens) {
		this.childrens = childrens;
	}
}
