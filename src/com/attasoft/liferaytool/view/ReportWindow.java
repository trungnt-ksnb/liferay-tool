package com.attasoft.liferaytool.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ReportWindow {
	private Display display;
	private Shell shell;

	public ReportWindow(Display display) {
		this.display = display;
	}

	public void initWindow() {
		shell = new Shell(display, SWT.CLOSE | SWT.MIN);
		shell.setText("Title");

		shell.open();
		shell.pack();

		Rectangle splashRect = shell.getBounds();
		Rectangle displayRect = display.getBounds();
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		shell.setLocation(x, y);
	}

	public void destroyWindow() {
		shell.close();
		shell.dispose();
	}
}