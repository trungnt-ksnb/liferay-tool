package com.attasoft.liferaytool.model;

import java.util.List;

/**
 * @author trungnt
 *
 */
public class Entity {
	private String name;
	private boolean localService;
	private boolean remoteService;
	private List<Column> columns;
	private List<Finder> finders;
	private Order order;

	public Entity name(String name) {
		this.setName(name);
		return this;
	}

	public Entity localService(boolean localService) {
		this.setLocalService(localService);
		return this;
	}

	public Entity remoteService(boolean remoteService) {
		this.setRemoteService(remoteService);
		return this;
	}

	public Entity column(List<Column> columns) {
		this.setColumns(columns);
		return this;
	}

	public Entity finder(List<Finder> finders) {
		this.setFinders(finders);
		return this;
	}

	public Entity order(Order order) {
		this.setOrder(order);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLocalService() {
		return localService;
	}

	public void setLocalService(boolean localService) {
		this.localService = localService;
	}

	public boolean isRemoteService() {
		return remoteService;
	}

	public void setRemoteService(boolean remoteService) {
		this.remoteService = remoteService;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<Finder> getFinders() {
		return finders;
	}

	public void setFinders(List<Finder> finders) {
		this.finders = finders;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
