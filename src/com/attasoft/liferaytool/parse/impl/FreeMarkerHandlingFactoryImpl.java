package com.attasoft.liferaytool.parse.impl;

import com.attasoft.liferaytool.parse.FreeMarkerHandlingFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author trungnt
 *
 */
public class FreeMarkerHandlingFactoryImpl implements FreeMarkerHandlingFactory {
	public Configuration getConfiguration() {
		Configuration configuration = new Configuration(
				Configuration.getVersion());
		configuration.setDefaultEncoding("UTF-8");
		configuration.setObjectWrapper(new DefaultObjectWrapper(configuration
				.getIncompatibleImprovements()));
		return configuration;
	}

	public Template getTemplate(String text) {
		Template template = null;
		try {
			template = new Template("tmp_" + System.currentTimeMillis(), text,
					getConfiguration());

		} catch (Exception e) {
			e.printStackTrace();

		}

		return template;
	}
}
