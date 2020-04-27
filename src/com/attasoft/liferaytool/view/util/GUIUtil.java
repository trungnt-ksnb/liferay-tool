package com.attasoft.liferaytool.view.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.attasoft.liferaytool.view.ApplicationUI;
import com.attasoft.liferaytool.view.ConfigDialog;
import com.attasoft.liferaytool.view.model.TreeNode;

/**
 * @author trungnt
 *
 */
public class GUIUtil {
	public static DirectoryDialog createDirectoryDialog(Shell parent,
			String message, String filterPath) {
		DirectoryDialog directoryDialog = new DirectoryDialog(parent);
		directoryDialog.setFilterPath(filterPath);
		directoryDialog.setMessage(message);
		return directoryDialog;
	}

	public static CheckboxTableViewer createTemplateViewHeader(
			CheckboxTableViewer checkboxTableViewer) {
		TableViewerColumn viewerColumn1 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		viewerColumn1.getColumn().setMoveable(false);
		viewerColumn1.getColumn().setResizable(false);
		viewerColumn1.getColumn().setText("");
		viewerColumn1.getColumn().setWidth(30);
		viewerColumn1.getColumn().setToolTipText("Enable/Disable");
		viewerColumn1.getColumn().setImage(
				SWTResourceManager.getImage(ConfigDialog.class,
						"/resources/icons/success.gif"));

		TableViewerColumn viewerColumn2 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		viewerColumn2.getColumn().setMoveable(true);
		viewerColumn2.getColumn().setResizable(true);
		viewerColumn2.getColumn().setText("Name");
		viewerColumn2.getColumn().setWidth(120);
		viewerColumn2.getColumn().setToolTipText("Template name");
		/*
		 * viewerColumn2.getColumn().setImage(SWTResourceManager.getImage(
		 * ConfigDialog.class, "/resources/icons/icon.gif"));
		 */

		TableViewerColumn viewerColumn3 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		viewerColumn3.getColumn().setMoveable(true);
		viewerColumn3.getColumn().setResizable(true);
		viewerColumn3.getColumn().setText("Type");
		viewerColumn3.getColumn().setWidth(50);
		viewerColumn3
				.getColumn()
				.setToolTipText(
						"Template type. 1-Generate specific for an entity, 2-generate for all entities");
		/*
		 * viewerColumn3.getColumn().setImage(SWTResourceManager.getImage(
		 * ConfigDialog.class, "/resources/icons/case.png"));
		 */

		TableViewerColumn viewerColumn4 = new TableViewerColumn(
				checkboxTableViewer, SWT.NONE);
		viewerColumn4.getColumn().setMoveable(true);
		viewerColumn4.getColumn().setResizable(true);
		viewerColumn4.getColumn().setText("Description");
		viewerColumn4.getColumn().setWidth(120);
		viewerColumn4.getColumn().setToolTipText("Template description.");
		/*
		 * viewerColumn4.getColumn().setImage(SWTResourceManager.getImage(
		 * ConfigDialog.class, "/resources/icons/text.gif"));
		 */
		return checkboxTableViewer;
	}

	public static TreeItem buildTree(Tree tree, TreeItem parentTreeItem,
			TreeNode treeNode) {

		TreeItem treeItem = createTreeItem(tree, parentTreeItem, treeNode);
		
		treeItem.setExpanded(true);
		
		if (treeNode.getChildrens() != null
				&& !treeNode.getChildrens().isEmpty()) {

			for (TreeNode tmp : treeNode.getChildrens()) {

				buildTree(tree, treeItem, tmp);
			}
		}
		return treeItem;
	}

	public static TreeItem createTreeItem(Tree tree, TreeItem parent,
			TreeNode treeNode) {
		TreeItem treeItem = null;
		if (treeNode.isRoot()) {
			treeItem = new TreeItem(tree, SWT.NONE);
		} else {
			treeItem = new TreeItem(parent, SWT.NONE);
		}
		if (treeNode.getIcon() != null) {
			treeItem.setImage(treeNode.getIcon());
		}

		treeItem.setText(treeNode.getNodeName());
		treeItem.setData(treeNode.getData());
		treeItem.setExpanded(true);
		return treeItem;
	}

	public static void restartApplication() throws URISyntaxException,
			IOException {
		final String javaBin = System.getProperty("java.home") + File.separator
				+ "bin" + File.separator + "java";
		final File currentJar = new File(ApplicationUI.class
				.getProtectionDomain().getCodeSource().getLocation().toURI());

		/* is it a jar file? */
		if (!currentJar.getName().endsWith(".jar"))
			return;

		/* Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());

		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.start();
		System.exit(0);
	}
}
