package com.attasoft.liferaytool.view.model;

import org.eclipse.swt.graphics.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * @author trungnt
 *
 */
public class TreeNode {
	private String nodeName;
	private int level;
	private int order;
	private boolean isRoot;
	private TreeNode parent;
	private Object data;
	private List<TreeNode> childrens;
	private Image icon;

	public TreeNode(String nodeName, boolean isRoot, int level, int order,
			TreeNode parent, List<TreeNode> childrens) {
		this.setNodeName(nodeName);
		this.setRoot(isRoot);
		this.setLevel(level);
		this.setOrder(order);
		this.setParent(parent);
		this.setChildrens(childrens);
	}

	public TreeNode(String nodeName, boolean isRoot, int level, int order,
			TreeNode parent, List<TreeNode> childrens, Image icon, Object data) {
		this.setNodeName(nodeName);
		this.setRoot(isRoot);
		this.setLevel(level);
		this.setOrder(order);
		this.setParent(parent);
		this.setChildrens(childrens);
		this.setIcon(icon);
		this.setData(data);

	}

	public TreeNode append(TreeNode treeNode) {
		List<TreeNode> childrens = getChildrens();
		if (childrens == null) {
			childrens = new ArrayList<TreeNode>();
		}
		childrens.add(treeNode);
		this.setChildrens(childrens);
		return this;
	}

	public TreeNode append(List<TreeNode> treeNodes) {
		List<TreeNode> childrens = getChildrens();
		if (childrens == null) {
			childrens = new ArrayList<TreeNode>();
		}
		childrens.addAll(treeNodes);
		this.setChildrens(childrens);
		return this;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<TreeNode> childrens) {
		this.childrens = childrens;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

}
