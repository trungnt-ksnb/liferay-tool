package com.attasoft.liferaytool.view.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.wb.swt.SWTResourceManager;
import org.json.simple.JSONObject;

import com.attasoft.liferaytool.datamodel.DataMap;
import com.attasoft.liferaytool.model.DefineTemplate;
import com.attasoft.liferaytool.model.Entity;
import com.attasoft.liferaytool.model.ServiceBuilder;
import com.attasoft.liferaytool.setting.SystemSetting;
import com.attasoft.liferaytool.view.ApplicationUI;
import com.attasoft.liferaytool.view.builder.TreeNodeBuilder;
import com.attasoft.liferaytool.view.model.TreeNode;

public class BGTreeNodeBuilderImpl implements TreeNodeBuilder {

	@Override
	public TreeNode buildTreeNode(TreeNode treeNode, int level) {
		TreeNode node = null;
		if (level == 0) {
			node = new TreeNode(SystemSetting.getSelectedVersion(), true,
					level, 1, null, null, SWTResourceManager.getImage(
							ApplicationUI.class,
							"/resources/icons/J2EEModule16.gif"), null);
			node.append(buildTreeNodes(node, 1, null));

		}

		return node;
	}

	@Override
	public List<TreeNode> buildTreeNodes(TreeNode parent, int level,
			String dataKey) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		if (level == 1) {
			int order = 1;
			for (Map.Entry<String, ServiceBuilder> entry : DataMap.getDataMap()
					.entrySet()) {
				String key = entry.getKey();

				TreeNode node = new TreeNode(key, false, level, order, parent,
						null, SWTResourceManager.getImage(ApplicationUI.class,
								"/resources/icons/element.png"), null);
				order++;

				node.append(buildTreeNodes(node, 2, key));

				treeNodes.add(node);
			}

		} else if (level == 2) {

			String parentName = parent.getNodeName();
			ServiceBuilder serviceBuilder = DataMap.getDataMap()
					.get(parentName);
			int order = 1;
			if (serviceBuilder != null && serviceBuilder.getEntities() != null
					&& !serviceBuilder.getEntities().isEmpty()) {

				for (Entity entity : serviceBuilder.getEntities()) {
					TreeNode node = new TreeNode(entity.getName(), false,
							level, order, parent, null,
							SWTResourceManager.getImage(ApplicationUI.class,
									"/resources/icons/xpath_level_3.gif"), null);

					order++;

					node.append(buildTreeNodes(node, 3, dataKey));

					treeNodes.add(node);
				}
			}

			for (DefineTemplate defineTemplate : SystemSetting
					.getTemplateSchema().getDefineTemplates()) {
				if (defineTemplate.isEnable() && defineTemplate.getType() == 2) {
					String moduleName = parent.getNodeName();
					if (moduleName.contains(".")) {
						moduleName = moduleName.substring(
								moduleName.lastIndexOf(".") + 1,
								moduleName.length());
					}
					moduleName = moduleName.substring(0, 1).toUpperCase()
							+ moduleName.substring(1);

					JSONObject obj = new JSONObject();
					obj.put("dataKey", dataKey);
					obj.put("templateName", defineTemplate.getTemplateName());

					TreeNode node = new TreeNode(defineTemplate.getExportName()
							.replace("${moduleName}", moduleName), false,
							level, order, parent, null,
							SWTResourceManager.getImage(ApplicationUI.class,
									"/resources/icons/icon.gif"), obj);

					treeNodes.add(node);

					order++;
				}
			}

		} else if (level == 3) {
			String parentName = parent.getNodeName();
			if (SystemSetting.getTemplateSchema() != null
					&& !SystemSetting.getTemplateSchema().getDefineTemplates()
							.isEmpty()) {
				int order = 1;
				for (DefineTemplate defineTemplate : SystemSetting
						.getTemplateSchema().getDefineTemplates()) {
					if (defineTemplate.isEnable()
							&& defineTemplate.getType() == 1) {
						JSONObject obj = new JSONObject();
						obj.put("dataKey", dataKey);
						obj.put("templateName",
								defineTemplate.getTemplateName());
						obj.put("currentEntity", parent.getNodeName());
						TreeNode node = new TreeNode(defineTemplate
								.getExportName().replace("${entity}",
										parentName), false, level, order,
								parent, null, SWTResourceManager.getImage(
										ApplicationUI.class,
										"/resources/icons/icon.gif"), obj);

						treeNodes.add(node);
					}
				}

			}
		}

		return treeNodes;
	}

}
