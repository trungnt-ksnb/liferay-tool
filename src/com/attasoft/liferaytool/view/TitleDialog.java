package com.attasoft.liferaytool.view;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author trungnt
 *
 */
public class TitleDialog extends TitleAreaDialog {
	private String _title;
	private String _message;
	private String _errorMessage;
	private int _width = 430;
	private int _height = 300;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TitleDialog(Shell parentShell, String title, String message,
			String errorMessage, int width, int height) {
		super(parentShell);
		setShellStyle(SWT.NO_TRIM);
		this._title = title;
		this._message = message;
		this._errorMessage = errorMessage;
		if (width > 0) {
			this._width = width;
		}

		if (height > 0) {
			this._height = height;
		}
		setErrorMessage(_errorMessage);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitleImage(SWTResourceManager.getImage(TitleDialog.class,
				"/resources/icons/text.gif"));
		setTitle(_title);
		setMessage(_message);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(_width, _height);
	}

}
