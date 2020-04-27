package com.attasoft.liferaytool.parse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author trungnt
 *
 */
public interface FreeMarkerHandlingFactory {
	public Configuration getConfiguration();
	public Template getTemplate(String text);
}
