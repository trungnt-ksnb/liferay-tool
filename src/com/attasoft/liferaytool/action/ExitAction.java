package com.attasoft.liferaytool.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.ResourceManager;

import com.attasoft.liferaytool.view.ApplicationUI;

/**
 * @author trungnt
 *
 */
public class ExitAction extends Action {
	private ApplicationWindow _window;

	public ExitAction(ApplicationWindow window) {
		super("Exit", ResourceManager.getImageDescriptor(ApplicationUI.class,
				"/resources/icons/stop.png"));
		this._window = window;
		this.setAccelerator(SWT.CTRL + 'E');
	}

	@Override
	public void run() {
		_window.close();
	}
}
