package com.attasoft.liferaytool.parse.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.attasoft.liferaytool.constant.ServiceConstant;
import com.attasoft.liferaytool.constant.TemplateConstant;
import com.attasoft.liferaytool.model.Column;
import com.attasoft.liferaytool.model.DefineTemplate;
import com.attasoft.liferaytool.model.Entity;
import com.attasoft.liferaytool.model.Finder;
import com.attasoft.liferaytool.model.Order;
import com.attasoft.liferaytool.model.ServiceBuilder;
import com.attasoft.liferaytool.model.TemplateSchema;

/**
 * @author trungnt
 *
 */
public class XMLHandlingFactoryUtil {

	public static ServiceBuilder parseXML(Document doc) {

		NodeList root = doc
				.getElementsByTagName(ServiceConstant.SERVICE_BUILDER);
		if (root == null || root.getLength() == 0) {
			return null;
		}

		Element rootElement = (Element) root.item(0);

		String packagePath = rootElement
				.getAttribute(ServiceConstant.PACKAGE_PATH);

		NodeList nodeList = rootElement
				.getElementsByTagName(ServiceConstant.AUTHOR);

		String author = "";

		if (nodeList != null && nodeList.getLength() > 0) {
			author = nodeList.item(0).getTextContent();
		}

		nodeList = rootElement.getElementsByTagName(ServiceConstant.NAMESPACE);

		String namespace = "";

		if (nodeList != null && nodeList.getLength() > 0) {
			namespace = nodeList.item(0).getTextContent();
		}

		// ----- Entity
		List<Entity> entities = null;
		nodeList = rootElement.getElementsByTagName(ServiceConstant.ENTITY);

		if (nodeList != null) {
			entities = new ArrayList<Entity>();
			for (int i = 0; i < nodeList.getLength(); i++) {

				List<Column> columns = new ArrayList<Column>();
				HashMap<String, Column> map = new HashMap<String, Column>();

				Node node = nodeList.item(i);
				Element element = (Element) node;
				String entityName = element.getAttribute(ServiceConstant.NAME);
				String localService = element
						.getAttribute(ServiceConstant.LOCAL_SERVICE);
				String removeService = element
						.getAttribute(ServiceConstant.REMOTE_SERVICE);
				System.out.println(entityName + "|" + localService + "|"
						+ removeService);

				NodeList tmpNodeList = element
						.getElementsByTagName(ServiceConstant.COLUMN);
				if (tmpNodeList != null) {
					for (int t = 0; t < tmpNodeList.getLength(); t++) {
						Node tmpNode = tmpNodeList.item(t);
						Element tmpElement = (Element) tmpNode;
						String name = tmpElement
								.getAttribute(ServiceConstant.NAME);
						System.out.println("column:" +name);
						String type = tmpElement
								.getAttribute(ServiceConstant.TYPE);
						String primary = tmpElement
								.getAttribute(ServiceConstant.PRIMARY);
						String dbName = tmpElement
								.getAttribute(ServiceConstant.DB_NAME);
						Column column = new Column()
								.name(name)
								.type(type)
								.dbName(dbName)
								.primary(
										(primary != null && !primary.trim()
												.equals("")) ? Boolean
												.parseBoolean(primary) : false)
								.build();
						columns.add(column);
						map.put(name, column);
					}
				}

				// ----- Order
				Order order = null;
				tmpNodeList = element
						.getElementsByTagName(ServiceConstant.ORDER);

				if (tmpNodeList != null && tmpNodeList.getLength() > 0) {
					Node tmpNode = tmpNodeList.item(0);
					Element tmpElement = (Element) tmpNode;
					String by = tmpElement.getAttribute(ServiceConstant.BY);
					tmpNodeList = tmpElement
							.getElementsByTagName(ServiceConstant.ORDER_COLUMN);
					List<Column> orderColumn = new ArrayList<Column>();
					if (tmpNodeList != null) {
						for (int t = 0; t < tmpNodeList.getLength(); t++) {
							tmpNode = tmpNodeList.item(t);
							tmpElement = (Element) tmpNode;
							String name = tmpElement
									.getAttribute(ServiceConstant.NAME);
							System.out.println("Order ColumnName: " + name);
							Column column = map.get(name);
							orderColumn.add(column);
						}
					}

					order = new Order().by(by).column(orderColumn);
				}

				// ----- Finder
				List<Finder> finders = null;
				tmpNodeList = element
						.getElementsByTagName(ServiceConstant.FINDER);
				if (tmpNodeList != null) {
					finders = new ArrayList<Finder>();
					for (int t = 0; t < tmpNodeList.getLength(); t++) {
						Node tmpNode = tmpNodeList.item(t);
						Element tmpElement = (Element) tmpNode;
						String name = tmpElement
								.getAttribute(ServiceConstant.NAME);
						String returnType = tmpElement
								.getAttribute(ServiceConstant.RETURN_TYPE);
						// finder-column
						NodeList tmpChildNodeList = tmpElement
								.getElementsByTagName(ServiceConstant.FINDER_COLUMN);
						List<Column> finderColumns = new ArrayList<Column>();
						for (int c = 0; c < tmpChildNodeList.getLength(); c++) {
							Node tmpChildNode = tmpChildNodeList.item(c);
							Element tmpChildElement = (Element) tmpChildNode;
							String columName = tmpChildElement
									.getAttribute(ServiceConstant.NAME);
							//System.out.println("Finder ColumnName: " + name);
							Column column = map.get(columName);
							finderColumns.add(column);
						}

						Finder finder = new Finder().name(name)
								.returnType(returnType).column(finderColumns);

						finders.add(finder);
					}
				}
				Entity entity = new Entity()
						.name(entityName)
						.localService(
								(localService != null && !localService.trim()
										.equals("")) ? Boolean
										.parseBoolean(localService) : false)
						.remoteService(
								(removeService != null && !removeService.trim()
										.equals("")) ? Boolean
										.parseBoolean(removeService) : false)
						.column(columns).order(order).finder(finders);
				entities.add(entity);
			}
		}
		// ----- Exception
		List<String> exceptions = null;
		nodeList = rootElement.getElementsByTagName(ServiceConstant.EXCEPTIONS);
		if (nodeList != null) {
			exceptions = new ArrayList<String>();
			for (int e = 0; e < nodeList.getLength(); e++) {
				Node node = nodeList.item(e);
				Element element = (Element) node;
				String exception = element.getTextContent();
				exceptions.add(exception);
			}
		}
		ServiceBuilder builder = new ServiceBuilder().author(author)
				.entitie(entities).exception(exceptions).namespace(namespace)
				.packagePath(packagePath);
		return builder;
	}

	public static TemplateSchema parseTemplateSchema(Document doc, String path) {

		TemplateSchema templateSchema = new TemplateSchema();

		NodeList root = doc.getElementsByTagName(TemplateConstant.TEMPLATES);
		if (root == null || root.getLength() == 0) {
			return null;
		}

		Element rootElement = (Element) root.item(0);

		String group = rootElement.getAttribute("group");

		templateSchema.setGroup(group);

		templateSchema.setDir(path);

		List<DefineTemplate> defineTemplates = null;

		NodeList nodeList = rootElement
				.getElementsByTagName(TemplateConstant.TEMPLATE);

		if (nodeList != null) {
			defineTemplates = new ArrayList<DefineTemplate>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element element = (Element) node;
				Node tmp = element.getElementsByTagName(TemplateConstant.NAME)
						.item(0);
				String templateName = tmp.getTextContent();
				tmp = element
						.getElementsByTagName(TemplateConstant.DESCRIPTION)
						.item(0);
				String description = tmp.getTextContent();
				tmp = element.getElementsByTagName(TemplateConstant.ENABLE)
						.item(0);
				boolean enable = Boolean.parseBoolean(tmp.getTextContent());
				tmp = element
						.getElementsByTagName(TemplateConstant.EXPORT_NAME)
						.item(0);
				String exportName = tmp.getTextContent();
				tmp = element.getElementsByTagName(TemplateConstant.TYPE).item(
						0);
				int type = Integer.parseInt(tmp.getTextContent());

				DefineTemplate defineTemplate = new DefineTemplate();
				defineTemplate.description(description).enable(enable)
						.exportName(exportName).templateName(templateName)
						.type(type);
				defineTemplates.add(defineTemplate);

			}
		}
		templateSchema.setDefineTemplates(defineTemplates);

		return templateSchema;
	}

	public static boolean updateTemplateSchema(Document doc,
			TemplateSchema templateSchema, String path) {

		try {
			NodeList root = doc
					.getElementsByTagName(TemplateConstant.TEMPLATES);

			Element rootElement = (Element) root.item(0);

			NodeList nodeList = rootElement
					.getElementsByTagName(TemplateConstant.TEMPLATE);

			if (nodeList != null) {

				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					Element element = (Element) node;

					String templateName = element
							.getElementsByTagName(TemplateConstant.NAME)
							.item(0).getTextContent();

					for (DefineTemplate defineTemplate : templateSchema
							.getDefineTemplates()) {
						if (templateName.equals(defineTemplate
								.getTemplateName())) {

							element.getElementsByTagName(
									TemplateConstant.ENABLE)
									.item(0)
									.setTextContent(
											String.valueOf(defineTemplate
													.isEnable()));

						}
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File(path));

			transformer.transform(domSource, streamResult);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
