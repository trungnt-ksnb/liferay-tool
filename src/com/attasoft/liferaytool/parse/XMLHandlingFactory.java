package com.attasoft.liferaytool.parse;

import com.attasoft.liferaytool.model.ServiceBuilder;
import com.attasoft.liferaytool.model.TemplateSchema;

/**
 * @author trungnt
 *
 */
public interface XMLHandlingFactory {
	public ServiceBuilder parseServiceXml(String path);
	public TemplateSchema parseSchema(String path, String templateDir);
	public String updateTemplateSchema(String path);
}
