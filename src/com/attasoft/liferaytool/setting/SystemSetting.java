package com.attasoft.liferaytool.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.attasoft.liferaytool.constant.PropKeys;
import com.attasoft.liferaytool.model.TemplateSchema;
import com.attasoft.liferaytool.parse.impl.XMLHandlingFactoryImpl;
import com.attasoft.liferaytool.util.CommonUtil;

/**
 * @author trungnt
 *
 */
public class SystemSetting {
	public static TemplateSchema _templateSchema;
	public static String _selectedVersion = "";
	public static String[] _versionNames = new String[] {};
	public static String[] _versionValues = new String[] {};
	public static String _workspace = "";

	public static String _error = "";

	public static void initSetting() {
		try {
			File setting = new File(CommonUtil.SETTING_FILE);

			InputStream inputStream = new FileInputStream(setting);

			Properties properties = CommonUtil.loadPropertie(inputStream);

			String templateGroup = properties
					.getProperty(PropKeys.TEMPLATE_GROUP);

			String templateDir = properties.getProperty(PropKeys.TEMPLATE_DIR);

			String selectedVersion = properties
					.getProperty(PropKeys.SELECTED_VERSION);
			SystemSetting.setSelectedVersion(selectedVersion);

			String versionNames = properties.getProperty(PropKeys.VERSION_NAME);
			SystemSetting.setVersionNames(versionNames.split(","));

			String versionValues = properties
					.getProperty(PropKeys.VERSION_VALUE);
			SystemSetting.setVersionValues(versionValues.split(","));

			String workspace = properties.getProperty(PropKeys.WORKSPACE);
			SystemSetting.setWorkspace(workspace);

			String schemaName = properties
					.getProperty(PropKeys.TEMPLATE_SCHEMA);

			if (templateGroup == null || templateGroup.equals("")) {
				templateGroup = CommonUtil.DEFAULT_FOLDER;
				schemaName = CommonUtil.DEFAULT_SCHEMA;
			}
			templateDir = CommonUtil.TEMPLATE_DIR + "\\" + templateGroup;

			File tmp = new File(templateDir);

			if (!tmp.exists()) {
				tmp.mkdirs();
				if (templateGroup.equalsIgnoreCase("default")) {
					// copy default folder
					copyDefautTemplate(templateDir);
				}
			}

			String schemaPath = templateDir + "\\" + schemaName;

			File schema = new File(schemaPath);

			if (schema.exists()) {
				XMLHandlingFactoryImpl factoryImpl = new XMLHandlingFactoryImpl();

				TemplateSchema templateSchema = factoryImpl.parseSchema(
						schemaPath, templateDir);

				SystemSetting.setTemplateSchema(templateSchema);

			} else {
				SystemSetting.setError("Not found template schema");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copyDefautTemplate(String dir) {
		File tmp = new File(dir + "\\schema.xml");
		InputStream ins = SystemSetting.class.getClassLoader()
				.getResourceAsStream("resources/templates/schema.xml");
		CommonUtil.inputStream2File(ins, tmp);

		tmp = new File(dir + "\\constant.ftl");
		ins = SystemSetting.class.getClassLoader().getResourceAsStream(
				"resources/templates/constant.ftl");
		CommonUtil.inputStream2File(ins, tmp);

		tmp = new File(dir + "\\localservice.ftl");
		ins = SystemSetting.class.getClassLoader().getResourceAsStream(
				"resources/templates/localservice.ftl");
		CommonUtil.inputStream2File(ins, tmp);

		tmp = new File(dir + "\\portletaction.ftl");
		ins = SystemSetting.class.getClassLoader().getResourceAsStream(
				"resources/templates/portletaction.ftl");
		CommonUtil.inputStream2File(ins, tmp);
	}

	public static void updateSetting(TemplateSchema templateSchema,
			String selectedVersion, String workspace) {

		SystemSetting.setSelectedVersion(selectedVersion);
		SystemSetting.setTemplateSchema(templateSchema);
		SystemSetting.setWorkspace(workspace);

		// write properties

		CommonUtil.storeProperty(CommonUtil.SETTING_FILE);

		// write schema
		XMLHandlingFactoryImpl factoryImpl = new XMLHandlingFactoryImpl();

		factoryImpl.updateTemplateSchema(SystemSetting.getTemplateSchema()
				.getDir() + "\\schema.xml");

	}

	public static TemplateSchema getTemplateSchema() {
		return _templateSchema;
	}

	public static void setTemplateSchema(TemplateSchema templateSchema) {
		SystemSetting._templateSchema = templateSchema;
	}

	public static String getSelectedVersion() {
		return _selectedVersion;
	}

	public static void setSelectedVersion(String selectedVersion) {
		SystemSetting._selectedVersion = selectedVersion;
	}

	public static String[] getVersionNames() {
		return _versionNames;
	}

	public static void setVersionNames(String[] versionNames) {
		SystemSetting._versionNames = versionNames;
	}

	public static String getError() {
		return _error;
	}

	public static void setError(String error) {
		SystemSetting._error = error;
	}

	public static String[] getVersionValues() {
		return _versionValues;
	}

	public static void setVersionValues(String[] versionValues) {
		SystemSetting._versionValues = versionValues;
	}

	public static String getWorkspace() {
		return _workspace;
	}

	public static void setWorkspace(String workspace) {
		SystemSetting._workspace = workspace;
	}

}
