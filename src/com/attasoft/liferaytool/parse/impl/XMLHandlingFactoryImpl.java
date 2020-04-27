package com.attasoft.liferaytool.parse.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.attasoft.liferaytool.model.ServiceBuilder;
import com.attasoft.liferaytool.model.TemplateSchema;
import com.attasoft.liferaytool.parse.XMLHandlingFactory;
import com.attasoft.liferaytool.parse.util.XMLHandlingFactoryUtil;
import com.attasoft.liferaytool.setting.SystemSetting;

/**
 * @author trungnt
 *
 */
public class XMLHandlingFactoryImpl implements XMLHandlingFactory {

	@Override
	public ServiceBuilder parseServiceXml(String path) {
		try {

			File file = new File(path);

			if (!file.exists()) {
				return null;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(file);

			doc.getDocumentElement().normalize();

			return XMLHandlingFactoryUtil.parseXML(doc);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TemplateSchema parseSchema(String path, String templateDir) {
		try {

			File file = new File(path);

			if (!file.exists()) {
				return null;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(file);

			doc.getDocumentElement().normalize();

			return XMLHandlingFactoryUtil.parseTemplateSchema(doc, templateDir);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String updateTemplateSchema(String path) {
		try {

			File file = new File(path);

			if (!file.exists()) {
				return path + " not exist";
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(file);

			doc.getDocumentElement().normalize();

			boolean result = XMLHandlingFactoryUtil.updateTemplateSchema(doc, SystemSetting.getTemplateSchema(), path);
			if(result){
				return "";
			}else{
				return "Update schema.xml faild";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Update schema.xml faild";
		}
		
	}
}
