package com.attasoft.liferaytool.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

/**
 * @author trungnt
 *
 */
public class ConfigAction extends Action {

	public ConfigAction() {
		super("Config");
		this.setAccelerator(SWT.ALT + 'C');
	}
}
