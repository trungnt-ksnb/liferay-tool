package com.attasoft.liferaytool.model;

import java.util.Date;

/**
 * @author trungnt
 *
 */
public class Column {
	private String name;
	private String type;
	private Class<?> dataType;
	private boolean primary;
	private String dbName;
	private String wrapPrimitiveType;
	
	public Column name(String name) {
		this.setName(name);
		return this;
	}

	public Column type(String type) {
		this.setType(type);
		return this;
	}

	public Column primary(boolean primary) {
		this.setPrimary(primary);
		return this;
	}

	public Column dbName(String dbName) {
		this.setDbName(dbName);
		return this;
	}

	public Column build() {
		String type = getType();
		if (type.equalsIgnoreCase("string")) {
			this.setDataType(String.class);
			this.setWrapPrimitiveType("String");
		} else if (type.equals("long")) {
			this.setDataType(long.class);
			this.setWrapPrimitiveType("Long");
		} else if (type.equals("int")) {
			this.setDataType(int.class);
			this.setWrapPrimitiveType("Integer");
		} else if (type.equals("short")) {
			this.setDataType(short.class);
			this.setWrapPrimitiveType("Short");
		} else if (type.equals("boolean")) {
			this.setDataType(boolean.class);
			this.setWrapPrimitiveType("Boolean");
		} else if (type.equals("double")) {
			this.setDataType(double.class);
			this.setWrapPrimitiveType("Double");
		} else if (type.equals("float")) {
			this.setDataType(float.class);
			this.setWrapPrimitiveType("Float");
		} else if (type.equals("date")) {
			this.setDataType(Date.class);
			this.setWrapPrimitiveType("Date");
		}else if (type.equals("Date")) {
			this.setDataType(Date.class);
			this.setWrapPrimitiveType("Date");
		} else {
			this.setDataType(null);
		}
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public void setDataType(Class<?> dataType) {
		this.dataType = dataType;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getWrapPrimitiveType() {
		return wrapPrimitiveType;
	}

	public void setWrapPrimitiveType(String wrapPrimitiveType) {
		this.wrapPrimitiveType = wrapPrimitiveType;
	}

}
