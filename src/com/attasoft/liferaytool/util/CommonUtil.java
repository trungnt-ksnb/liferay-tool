package com.attasoft.liferaytool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;
import java.io.FilenameFilter;

import com.attasoft.liferaytool.constant.PropKeys;
import com.attasoft.liferaytool.datamodel.DataMap;
import com.attasoft.liferaytool.model.Entity;
import com.attasoft.liferaytool.model.ServiceBuilder;
import com.attasoft.liferaytool.model.TemplateDataModel;
import com.attasoft.liferaytool.parse.util.FreeMarkerHandlingFactoryUtil;
import com.attasoft.liferaytool.setting.SystemSetting;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author trungnt
 *
 */
public class CommonUtil {
	public static Properties loadPropertie(InputStream ins) {
		Properties prop = new Properties();

		try {
			if (ins != null) {
				prop.load(ins);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		return prop;
	}

	public static Properties loadPropertie(String path) {

		InputStream inputStream = null;

		Properties prop = new Properties();

		try {
			inputStream = new FileInputStream(path);
			if (inputStream != null) {
				prop.load(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		return prop;
	}

	public static String file2Text(String path) {
		InputStream ins = file2InputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins));

		String str;
		StringBuffer result = new StringBuffer();

		try {
			while ((str = reader.readLine()) != null) {
				result.append(str + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				ins.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String file2Text(InputStream ins) throws IOException {
		StringBuffer result = new StringBuffer();

		if (ins != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
			try {
				String str = "";
				while ((str = reader.readLine()) != null) {

					result.append(str + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
				ins.close();
			}
		}

		return result.toString();
	}

	public static void inputStream2File(InputStream inputStream, File file) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			int read;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static InputStream file2InputStream(String path) {
		File initialFile = new File(path);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(initialFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static void storeProperty(String path) {
		Properties prop = CommonUtil.loadPropertie(path);
		OutputStream output = null;
		try {
			output = new FileOutputStream(path);

			// set the properties value
			prop.setProperty(PropKeys.TEMPLATE_GROUP, SystemSetting
					.getTemplateSchema().getGroup());
			prop.setProperty(PropKeys.TEMPLATE_DIR, SystemSetting
					.getTemplateSchema().getDir());
			prop.setProperty(PropKeys.SELECTED_VERSION,
					SystemSetting.getSelectedVersion());
			prop.setProperty(PropKeys.WORKSPACE, SystemSetting.getWorkspace());
			prop.setProperty(PropKeys.TEMPLATE_SCHEMA,
					prop.getProperty(PropKeys.TEMPLATE_SCHEMA));
			prop.setProperty(PropKeys.VERSION_NAME,
					prop.getProperty(PropKeys.VERSION_NAME));
			prop.setProperty(PropKeys.VERSION_VALUE,
					prop.getProperty(PropKeys.VERSION_VALUE));

			prop.store(output, "Config Setting");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String[] getSubFolderName(String path) {
		File file = new File(path);
		String[] directories = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {

				return new File(dir, name).isDirectory();
			}
		});

		return directories;
	}

	public static File[] findFileByName(String path, final String fileName) {
		File file = new File(path);
		File[] matchingFiles = file.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals(fileName);
			}
		});

		return matchingFiles;
	}

	public static List<File> findFileByName(List<File> files, String path,
			String fileName) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] tmpFiles = file.listFiles();
			for (File f : tmpFiles) {
				findFileByName(files, f.getPath(), fileName);
			}
		} else {
			if (fileName.equals(file.getName())) {
				files.add(file);
			}
		}
		return files;
	}

	public static String processTemplate(String dataKey, String currentEntity,
			String templateName) {

		ServiceBuilder serviceBuilder = DataMap.getDataMap().get(dataKey);
		if (currentEntity != null && !currentEntity.isEmpty()
				&& serviceBuilder != null
				&& serviceBuilder.getEntities() != null) {
			for (Entity entity : serviceBuilder.getEntities()) {
				if (currentEntity.equals(entity.getName())) {
					serviceBuilder.setCurrentEntity(entity);
					break;
				}
			}
		}

		String templatePath = SystemSetting.getTemplateSchema().getDir() + "\\"
				+ templateName;

		String text = CommonUtil.file2Text(templatePath);

		TemplateDataModel dataModel = null;

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String jsonStr = objectMapper.writeValueAsString(serviceBuilder);
			//System.out.println(jsonStr);
			Reader reader = new StringReader(jsonStr);

			dataModel = objectMapper.readValue(reader, TemplateDataModel.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		String result = FreeMarkerHandlingFactoryUtil.process(dataModel, text);

		return result;
	}

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String WORK_FOLDER = "work";
	public static final String TEMPLATE_FOLDER = "templates";
	public static final String DEFAULT_FOLDER = "default";
	public static final String DEFAULT_SCHEMA = "schema.xml";

	public static final String WORK_DIR = USER_DIR + "\\" + WORK_FOLDER;
	public static final String TEMPLATE_DIR = WORK_DIR + "\\" + TEMPLATE_FOLDER;
	public static final String DEFAULT_DIR = TEMPLATE_DIR + "\\"
			+ DEFAULT_FOLDER;
	public static final String SETTING_FILE = WORK_DIR + "\\"
			+ "setting.properties";
}
