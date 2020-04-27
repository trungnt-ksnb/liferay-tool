package com.attasoft.liferaytool.parse.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.attasoft.liferaytool.parse.impl.FreeMarkerHandlingFactoryImpl;

import freemarker.template.Template;

/**
 * @author trungnt
 *
 */
public class FreeMarkerHandlingFactoryUtil {
	public static String process(Object dataModel, String text) {

		StringWriter out = null;

		String result = "";

		FreeMarkerHandlingFactoryImpl factoryImpl = new FreeMarkerHandlingFactoryImpl();

		Template template = factoryImpl.getTemplate(text);

		if (template != null && dataModel != null) {
			try {
				out = new StringWriter();
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("model", dataModel);
				template.process(root, out);
				result = out.getBuffer().toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.flush();
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return result;
	}
}
