package com.attasoft.liferaytool.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.attasoft.liferaytool.model.DefineTemplate;
import com.attasoft.liferaytool.model.TemplateSchema;
import com.attasoft.liferaytool.parse.impl.XMLHandlingFactoryImpl;
import com.attasoft.liferaytool.setting.SystemSetting;
import com.attasoft.liferaytool.view.util.GUIUtil;

public class ConfigDialog extends TitleAreaDialog {
	private Text _lrwText;
	private Text _tmpdText;
	private Combo _lvrCb;
	private Table _templateFileTbl;

	private String _selectedVersion;
	private String _lrwPath;
	private String _tmpdDir;
	private List<String[]> _templateData;

	public void init() {
		_lrwPath = SystemSetting.getWorkspace();
		_tmpdDir = SystemSetting.getTemplateSchema().getDir();
		_templateData = new ArrayList<>();
		TemplateSchema templateSchema = SystemSetting.getTemplateSchema();
		if (templateSchema != null
				&& !templateSchema.getDefineTemplates().isEmpty()) {
			for (DefineTemplate defineTemplate : templateSchema
					.getDefineTemplates()) {
				_templateData.add(new String[] {
						String.valueOf(defineTemplate.isEnable()),
						defineTemplate.getTemplateName(),
						String.valueOf(defineTemplate.getType()),
						defineTemplate.getDescription() });
			}
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ConfigDialog(Shell parentShell) {

		super(parentShell);
		init();
		setShellStyle(SWT.DIALOG_TRIM);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		setTitle("System Configuration Settings");
		setTitleImage(null);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		GridLayout containerLayout = new GridLayout(2, false);
		containerLayout.horizontalSpacing = 10;
		containerLayout.marginTop = 5;
		container.setLayout(containerLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		// ---------- SPLIT ----------
		{
			Label lrvLbl = new Label(container, SWT.NONE);
			lrvLbl.setText("Liferay Version");
		}
		// ---------- SPLIT ----------
		{
			_lvrCb = new Combo(container, SWT.NONE);
			_lvrCb.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
					1, 1));
			String[] versionNames = SystemSetting.getVersionNames();
			_selectedVersion = SystemSetting.getSelectedVersion();
			final String[] versionValues = SystemSetting.getVersionValues();
			int index = 0;
			for (index = 0; index < versionValues.length; index++) {
				if (versionValues[index].equals(_selectedVersion)) {
					break;
				}
			}
			_lvrCb.setItems(versionNames);
			_lvrCb.select(index);
			_lvrCb.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					int index = _lvrCb.getSelectionIndex();
					_selectedVersion = versionValues[index];
				}
			});
		}
		// ---------- SPLIT ----------
		{
			Label lrwLbl = new Label(container, SWT.NONE);
			lrwLbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
					false, 1, 1));
			lrwLbl.setText("Liferay Workspace");
		}
		// ---------- SPLIT ----------
		{
			Composite lrwPane = new Composite(container, SWT.NONE);
			GridLayout lrwPaneLayout = new GridLayout(2, false);
			lrwPaneLayout.horizontalSpacing = 0;
			lrwPaneLayout.marginWidth = 0;
			lrwPaneLayout.marginHeight = 0;
			lrwPane.setLayout(lrwPaneLayout);
			lrwPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
					1, 1));

			_lrwText = new Text(lrwPane, SWT.BORDER);
			GridData lrwTextGridData = new GridData(SWT.FILL, SWT.CENTER, true,
					false, 1, 1);
			lrwTextGridData.heightHint = 18;
			_lrwText.setLayoutData(lrwTextGridData);
			_lrwText.setText(_lrwPath);

			_lrwText.addListener(SWT.FocusOut, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					// TODO validate
					_lrwPath = _lrwText.getText();
				}
			});

			Button lrwBtn = new Button(lrwPane, SWT.CENTER);
			lrwBtn.setImage(SWTResourceManager.getImage(ConfigDialog.class,
					"/resources/icons/return.png"));
			GridData lrwBtnGridData = new GridData(SWT.FILL, SWT.FILL, false,
					false, 1, 1);
			lrwBtnGridData.horizontalIndent = 1;
			lrwBtn.setLayoutData(lrwBtnGridData);
			// ----- LISTENER -----
			{
				lrwBtn.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {

						DirectoryDialog dialog = GUIUtil.createDirectoryDialog(
								parent.getShell(),
								"Please select a Liferay Workspace(or Liferay SDK) directory and click OK",
								_lrwPath);

						_lrwPath = dialog.open();
						_lrwText.setText(_lrwPath);

					}
				});
			}
		}
		// ---------- SPLIT ----------
		{
			Label tmpdLbl = new Label(container, SWT.NONE);
			tmpdLbl.setText("Template Directory");

			Composite tmpdPane = new Composite(container, SWT.NONE);
			GridLayout tmpdPaneLayout = new GridLayout(2, false);
			tmpdPaneLayout.marginWidth = 0;
			tmpdPaneLayout.marginHeight = 0;
			tmpdPaneLayout.horizontalSpacing = 0;
			tmpdPane.setLayout(tmpdPaneLayout);
			tmpdPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
					false, 1, 1));
			_tmpdText = new Text(tmpdPane, SWT.BORDER);
			GridData tmpdTextGridData = new GridData(SWT.FILL, SWT.CENTER,
					true, false, 1, 1);
			tmpdTextGridData.heightHint = 18;
			_tmpdText.setLayoutData(tmpdTextGridData);
			_tmpdText.setText(_tmpdDir);
			_tmpdText.addListener(SWT.FocusOut, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (_tmpdText.getText() != null
							&& !_tmpdText.getText().trim().isEmpty()) {
						TemplateSchema templateSchema = reloadTemplate(parent,
								_tmpdText.getText());
						if (templateSchema != null) {
							_tmpdDir = _tmpdText.getText();
						}
					}
				}
			});

			Button tmpdBtn = new Button(tmpdPane, SWT.CENTER);
			GridData tmpdBtnGridData = new GridData(SWT.FILL, SWT.FILL, false,
					false, 1, 1);
			tmpdBtnGridData.horizontalIndent = 1;
			tmpdBtn.setLayoutData(tmpdBtnGridData);
			tmpdBtn.setImage(SWTResourceManager.getImage(ConfigDialog.class,
					"/resources/icons/return.png"));
			// ----- LISTENER -----
			{
				tmpdBtn.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {

						DirectoryDialog dialog = GUIUtil.createDirectoryDialog(
								parent.getShell(),
								"Please select a template directory and click OK",
								_tmpdDir);

						String templateDir = dialog.open();

						TemplateSchema templateSchema = reloadTemplate(parent,
								templateDir);
						if (templateSchema != null) {
							_tmpdDir = templateDir;
							_tmpdText.setText(_tmpdDir);
						}
					}
				});
			}
			{
				new Label(container, SWT.NONE);

				CheckboxTableViewer checkboxTableViewer = CheckboxTableViewer
						.newCheckList(container, SWT.BORDER
								| SWT.FULL_SELECTION);
				checkboxTableViewer.setAllGrayed(true);
				checkboxTableViewer.setAllChecked(true);
				checkboxTableViewer
						.setContentProvider(new ArrayContentProvider());

				GUIUtil.createTemplateViewHeader(checkboxTableViewer);

				_templateFileTbl = checkboxTableViewer.getTable();
				_templateFileTbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, true, 1, 1));
				_templateFileTbl.setHeaderVisible(true);

				for (String[] row : _templateData) {
					TableItem item = new TableItem(_templateFileTbl, SWT.NONE);
					item.setChecked(Boolean.parseBoolean(row[0]));
					item.setText(Arrays.copyOfRange(row, 0, row.length));
				}

				checkboxTableViewer
						.addSelectionChangedListener(new ISelectionChangedListener() {

							@Override
							public void selectionChanged(
									SelectionChangedEvent event) {
								// IStructuredSelection selection =
								// (IStructuredSelection) event
								// .getSelection();
								// System.out.println(selection.size());

								TableItem[] items = _templateFileTbl.getItems();
								if (items != null && items.length > 0) {
									for (int i = 0; i < items.length; i++) {
										TableItem item = items[i];
										boolean selected = item.getChecked();
										_templateData.get(i)[0] = String
												.valueOf(selected);
									}

								}

							}
						});

			}
		}

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		Button button = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		button.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {

				TemplateSchema templateSchema = SystemSetting
						.getTemplateSchema();
				if (templateSchema != null
						&& !templateSchema.getDefineTemplates().isEmpty()) {
					for (DefineTemplate defineTemplate : templateSchema
							.getDefineTemplates()) {
						String[] templateData = _templateData
								.get(templateSchema.getDefineTemplates()
										.indexOf(defineTemplate));
						boolean isEnable = Boolean
								.parseBoolean(templateData[0]);

						defineTemplate.setEnable(isEnable);
					}
				}

				SystemSetting.updateSetting(templateSchema, _selectedVersion,
						_lrwPath);

				try {
					GUIUtil.restartApplication();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 500);
	}

	private TemplateSchema reloadTemplate(Composite parent, String templateDir) {
		TemplateSchema templateSchema = null;
		File fileSchema = new File(templateDir);
		if (!fileSchema.exists() || fileSchema.isFile()) {
			MessageBox messageBox = new MessageBox(parent.getShell(),
					SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Template Directory Error");
			messageBox.setMessage(templateDir + " is not directory");
			messageBox.open();
			return templateSchema;
		}

		String schemaPath = templateDir + "\\schema.xml";

		fileSchema = new File(schemaPath);
		if (!fileSchema.exists()) {
			MessageBox messageBox = new MessageBox(parent.getShell(),
					SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Template Setting Error");
			messageBox.setMessage("Not found template schema.xml");
			messageBox.open();
		} else {
			XMLHandlingFactoryImpl factoryImpl = new XMLHandlingFactoryImpl();
			templateSchema = factoryImpl.parseSchema(schemaPath, templateDir);
		}

		return templateSchema;
	}
}
