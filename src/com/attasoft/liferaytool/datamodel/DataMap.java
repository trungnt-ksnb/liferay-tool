package com.attasoft.liferaytool.datamodel;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import com.attasoft.liferaytool.model.ServiceBuilder;
import com.attasoft.liferaytool.parse.impl.XMLHandlingFactoryImpl;
import com.attasoft.liferaytool.view.SplashWindow;

/**
 * @author trungnt
 *
 */
public class DataMap {
	public static LinkedHashMap<String, ServiceBuilder> dataMap = new LinkedHashMap<>();

	public static LinkedHashMap<String, ServiceBuilder> getDataMap() {
		return dataMap;
	}

	public static void setDataMap(LinkedHashMap<String, ServiceBuilder> dataMap) {
		DataMap.dataMap = dataMap;
	}

	public static LinkedHashMap<String, ServiceBuilder> add(String key,
			ServiceBuilder value) {
		LinkedHashMap<String, ServiceBuilder> dataMap = getDataMap();
		dataMap.put(key, value);
		setDataMap(dataMap);
		return dataMap;
	}
	

	public static void initData(List<File> serviceFiles) {
		
		
		XMLHandlingFactoryImpl factoryImpl = new XMLHandlingFactoryImpl();

		for (File file : serviceFiles) {

			String path = file.getAbsolutePath();
			
			ServiceBuilder serviceBuilder = factoryImpl.parseServiceXml(path);
			
			if (serviceBuilder != null) {
				DataMap.add(serviceBuilder.getPackagePath(), serviceBuilder);
			}
			
			SplashWindow.splashPos += 1;
			SplashWindow.bar.setSelection(SplashWindow.splashPos);
		}

	}
}
