package com.attasoft.liferaytool.view.builder;

import java.util.List;

import com.attasoft.liferaytool.view.model.TreeNode;

/**
 * @author trungnt
 *
 */
public interface TreeNodeBuilder {
	public TreeNode buildTreeNode(TreeNode treeNode, int level);
	public List<TreeNode> buildTreeNodes(TreeNode parent, int level,
			String dataKey);
}
