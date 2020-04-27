package com.attasoft.liferaytool.view;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.attasoft.liferaytool.datamodel.DataMap;
import com.attasoft.liferaytool.setting.SystemSetting;
import com.attasoft.liferaytool.util.CommonUtil;

public class SplashWindow {

	public static int splashPos = 0;

	public static int SPLASH_MAX = 100;

	public static List<File> serviceFiles = new ArrayList<File>();

	public static ProgressBar bar;

	public SplashWindow(Display display) {

		initEnv();

		String workspace = SystemSetting.getWorkspace();

		CommonUtil.findFileByName(serviceFiles, workspace, "service.xml");

		SPLASH_MAX = serviceFiles.size();

		final Image image = SWTResourceManager.getImage(SplashWindow.class,
				"/resources/icons/screen.jpg");

		final Shell splash = new Shell(SWT.ON_TOP);

		bar = new ProgressBar(splash, SWT.NONE);

		bar.setMaximum(SPLASH_MAX);

		Label label = new Label(splash, SWT.NONE);
		label.setImage(image);
		FormLayout layout = new FormLayout();
		splash.setLayout(layout);

		FormData labelData = new FormData();
		labelData.right = new FormAttachment(100, 0);
		labelData.bottom = new FormAttachment(100, 0);
		label.setLayoutData(labelData);

		FormData progressData = new FormData();
		progressData.left = new FormAttachment(0, -5);
		progressData.right = new FormAttachment(100, 0);
		progressData.bottom = new FormAttachment(100, 0);

		bar.setLayoutData(progressData);

		splash.pack();

		Rectangle splashRect = splash.getBounds();
		Rectangle displayRect = display.getBounds();
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		splash.setLocation(x, y);
		splash.open();

		display.asyncExec(new Runnable() {
			public void run() {
				DataMap.initData(serviceFiles);
				splash.close();
				image.dispose();
			}
		});

		while (splashPos != SPLASH_MAX) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void initEnv() {

		File temp = new File(CommonUtil.WORK_DIR);
		if (!temp.exists()) {
			temp.mkdirs();
		}

		File setting = new File(CommonUtil.SETTING_FILE);

		if (!setting.exists()) {
			InputStream ins = getClass().getClassLoader().getResourceAsStream(
					"resources/setting.properties");
			CommonUtil.inputStream2File(ins, setting);
		}

		SystemSetting.initSetting();

	}
}