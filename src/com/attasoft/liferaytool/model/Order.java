package com.attasoft.liferaytool.model;

import java.util.List;

/**
 * @author trungnt
 *
 */
public class Order {
	private String by;
	private List<Column> columns;

	public Order by(String by) {
		this.setBy(by);
		return this;
	}

	public Order column(List<Column> columns) {
		this.setColumns(columns);
		return this;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

}
