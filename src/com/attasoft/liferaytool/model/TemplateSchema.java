package com.attasoft.liferaytool.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author trungnt
 *
 */
public class TemplateSchema {
	public String dir;
	public String group;
	public List<DefineTemplate> defineTemplates = new ArrayList<>();

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public List<DefineTemplate> getDefineTemplates() {
		return defineTemplates;
	}

	public void setDefineTemplates(List<DefineTemplate> defineTemplates) {
		this.defineTemplates = defineTemplates;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
