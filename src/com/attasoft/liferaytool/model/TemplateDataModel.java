package com.attasoft.liferaytool.model;

import java.util.List;

public class TemplateDataModel {
	private String packagePath;
	private String author;
	private String namespace;
	private List<String> exceptions;
	private Entity currentEntity;
	private List<Entity> entities;

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Entity getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(Entity currentEntity) {
		this.currentEntity = currentEntity;
	}

}
