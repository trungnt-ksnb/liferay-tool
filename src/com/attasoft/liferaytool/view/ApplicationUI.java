package com.attasoft.liferaytool.view;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.simple.JSONObject;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;

import com.attasoft.liferaytool.action.ConfigAction;
import com.attasoft.liferaytool.action.ExitAction;
import com.attasoft.liferaytool.setting.SystemSetting;
import com.attasoft.liferaytool.util.CommonUtil;
import com.attasoft.liferaytool.view.builder.impl.BGTreeNodeBuilderImpl;
import com.attasoft.liferaytool.view.model.TreeNode;
import com.attasoft.liferaytool.view.util.GUIUtil;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.custom.CLabel;

public class ApplicationUI extends ApplicationWindow {
	private ExitAction _exitAction;
	private ConfigDialog _configDialog;
	private Tree _moduleTree;
	private TreeNode _treeNode;
	private StyledText _styledText;

	/**
	 * Create the application window.
	 */
	public ApplicationUI() {
		super(null);
		Display display = new Display();

		new SplashWindow(display);
		while ((Display.getCurrent().getShells().length != 0)
				&& !Display.getCurrent().getShells()[0].isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		
		createActions(this);
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		if (SystemSetting.getError() != null
				&& !SystemSetting.getError().isEmpty()) {
			// TODO alert
		}
		// load work env
		BGTreeNodeBuilderImpl builderImpl = new BGTreeNodeBuilderImpl();
		_treeNode = builderImpl.buildTreeNode(null, 0);

	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		final CLabel _progressBarLable = new CLabel(composite, SWT.NONE);
		_progressBarLable.setText("Progress");
		
		final ProgressBar _progressBar = new ProgressBar(composite, SWT.SMOOTH);
		_progressBar.setMaximum(3);

		SashForm sashForm = new SashForm(container, SWT.NONE);
		sashForm.setLayoutData(BorderLayout.CENTER);

		ScrolledComposite _leftSc = new ScrolledComposite(sashForm, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		_leftSc.setExpandHorizontal(true);
		_leftSc.setExpandVertical(true);

		_moduleTree = new Tree(_leftSc, SWT.BORDER);
		_moduleTree.setRedraw(true);
		_moduleTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {

				TreeItem[] selection = _moduleTree.getSelection();
				for (int i = 0; i < selection.length; i++) {
					_progressBar.setSelection(0);
					_progressBarLable.setText("Progress");
					TreeItem treeItem = selection[i];
					JSONObject object = (JSONObject) treeItem.getData();
					if (object != null) {
						String dataKey = (String) object.get("dataKey");
						String templateName = (String) object
								.get("templateName");
						String currentEntity = (String) object
								.get("currentEntity");
						_progressBar.setSelection(1);
						_progressBarLable.setText("33/100");
						String text = CommonUtil.processTemplate(dataKey,
								currentEntity, templateName);
						_progressBar.setSelection(2);
						_progressBarLable.setText("66/100");
						_styledText.setText(text);
						_progressBar.setSelection(3);
						_progressBarLable.setText("100/100");
						
					}
				}

			}
		});

		GUIUtil.buildTree(_moduleTree, null, _treeNode);

		_leftSc.setContent(_moduleTree);
		_leftSc.setMinSize(_moduleTree.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		ScrolledComposite _rightSc = new ScrolledComposite(sashForm, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		_rightSc.setExpandHorizontal(true);
		_rightSc.setExpandVertical(true);

		_styledText = new StyledText(_rightSc, SWT.BORDER);

		Menu _menu = new Menu(_styledText);
		_styledText.setMenu(_menu);

		MenuItem _selectAllItem = new MenuItem(_menu, SWT.NONE);
		_selectAllItem.setImage(SWTResourceManager.getImage(
				ApplicationUI.class, "/resources/icons/switch.png"));
		_selectAllItem.setText("Select All");

		_selectAllItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				_styledText.selectAll();
			}
		});

		MenuItem _copyItem = new MenuItem(_menu, SWT.NONE);
		_copyItem.setImage(SWTResourceManager.getImage(ApplicationUI.class,
				"/resources/icons/editor_page_source.png"));
		_copyItem.setText("Copy");
		// _copyItem.setAccelerator(SWT.CTRL + 'C');
		_copyItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Clipboard clipboard = new Clipboard(_styledText.getDisplay());
				String plainText = _styledText.getSelectionText();
				String rtfText = _styledText.getSelectionText();
				TextTransfer textTransfer = TextTransfer.getInstance();
				RTFTransfer rftTransfer = RTFTransfer.getInstance();
				clipboard.setContents(new String[] { plainText, rtfText },
						new Transfer[] { textTransfer, rftTransfer });
				clipboard.dispose();
			}
		});
		new MenuItem(_menu, SWT.SEPARATOR);

		MenuItem _clearnItem = new MenuItem(_menu, SWT.NONE);
		_clearnItem.setImage(SWTResourceManager.getImage(ApplicationUI.class,
				"/resources/icons/garbage.png"));
		_clearnItem.setText("Delete");
		_clearnItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				_styledText.setText("");
			}
		});
		// _clearnItem.setAccelerator(SWT.CTRL + 'D');
		_styledText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// CTRL + SPACE to complement
				// System.out.println(e.keyCode);
				if (e.keyCode == 97 && e.stateMask == SWT.CTRL) {
					_styledText.selectAll();
				}

				if (e.keyCode == 100 && e.stateMask == SWT.CTRL) {
					_styledText.setText("");
				}
			}
		});

		_rightSc.setContent(_styledText);
		_rightSc.setMinSize(_styledText.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sashForm.setWeights(new int[] {1, 3});

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions(ApplicationWindow window) {
		_exitAction = new ExitAction(window);

	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager mainMenu = new MenuManager();
		MenuManager fileMenu = new MenuManager("File");
		fileMenu.add(_exitAction);

		MenuManager editMenu = new MenuManager("Edit");

		MenuManager configMenu = new MenuManager("Setting");
		ConfigAction configAction = new ConfigAction() {
			@Override
			public void run() {
				ConfigDialog configDialog = getConfigDialog();
				configDialog.create();
				configDialog.open();
			}
		};
		configAction.setImageDescriptor(ResourceManager.getImageDescriptor(
				ApplicationUI.class, "/resources/icons/macro_instance.png"));

		configMenu.add(configAction);

		MenuManager helpMenu = new MenuManager("Help");
		mainMenu.add(fileMenu);
		mainMenu.add(editMenu);
		mainMenu.add(configMenu);
		mainMenu.add(helpMenu);
		return mainMenu;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			ApplicationUI window = new ApplicationUI();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell mainui) {
		mainui.setImage(SWTResourceManager.getImage(ApplicationUI.class,
				"/resources/icons/macro.png"));
		super.configureShell(mainui);
		mainui.setText("Liferay Tool");
		_configDialog = new ConfigDialog(mainui);
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(1024, 768);
	}

	public ConfigDialog getConfigDialog() {
		return _configDialog;
	}

	public void setConfigDialog(ConfigDialog configDialog) {
		this._configDialog = configDialog;
	}
}
