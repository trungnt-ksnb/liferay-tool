package com.attasoft.liferaytool.model;

import java.util.List;

/**
 * @author trungnt
 *
 */
public class Finder {
	private String name;
	private String returnType;
	private List<Column> columns;

	public Finder name(String name) {
		this.setName(name);
		return this;
	}

	public Finder returnType(String returnType) {
		this.setReturnType(returnType);
		return this;
	}

	public Finder column(List<Column> columns) {
		this.setColumns(columns);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

}
