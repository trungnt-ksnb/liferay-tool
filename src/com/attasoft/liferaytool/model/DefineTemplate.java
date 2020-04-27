package com.attasoft.liferaytool.model;

/**
 * @author trungnt
 *
 */
public class DefineTemplate {
	private String templateName;
	private String exportName;
	private int type;
	private boolean enable;
	private String description;
	private String[] dataRow;

	public DefineTemplate templateName(String name) {
		setTemplateName(name);
		return this;
	}
	
	public DefineTemplate exportName(String exportName) {
		setExportName(exportName);
		return this;
	}

	public DefineTemplate description(String description) {
		setDescription(description);
		return this;
	}

	public DefineTemplate enable(boolean enable) {
		setEnable(enable);
		return this;
	}

	public DefineTemplate type(int type) {
		setType(type);
		return this;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String name) {
		this.templateName = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getDataRow() {
		dataRow = new String[] { String.valueOf(isEnable()), getTemplateName(),
				String.valueOf(getType()), getDescription() };
		return dataRow;
	}

	public String getExportName() {
		return exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}

	public void setDataRow(String[] dataRow) {
		this.dataRow = dataRow;
	}

}
