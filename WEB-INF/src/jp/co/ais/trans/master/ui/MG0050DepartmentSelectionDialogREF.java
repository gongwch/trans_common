package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 
 */
public class MG0050DepartmentSelectionDialogREF extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = 2920413827594545848L;

	private String selectedCode = null;

	private String selectedName_S = null;

	protected Map depMap;

	protected MG0050DepartmentHierarchyMasterPanelCtrl ctrl;

	protected Frame parent;

	protected Dialog parentDialog;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param modal
	 * @param depMap
	 * @param ctrl コントロールクラス
	 */
	public MG0050DepartmentSelectionDialogREF(Frame parent, boolean modal, Map depMap,
		MG0050DepartmentHierarchyMasterPanelCtrl ctrl) {
		super(parent, modal);
		this.depMap = depMap;
		this.ctrl = ctrl;
		this.parent = parent;
		initComponents();
		initSpreadSheet();
		setSize(700, 430);
		super.initDialog();

		// fix bug: 確定ボタン押下して UI チェックが失敗の場合は、
		// ダイアログを閉じ時、dialog.isSettle = true;
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				btnCancel.doClick();
			}
		});
		Vector cells = new Vector();
		setDataList(cells);

	}

	/**
	 * コンストラクタ
	 * 
	 * @param parentF
	 * @param parentD
	 * @param modal
	 * @param depMap
	 * @param ctrl コントロールクラス
	 */
	public MG0050DepartmentSelectionDialogREF(Frame parentF, Dialog parentD, boolean modal, Map depMap,
		MG0050DepartmentHierarchyMasterPanelCtrl ctrl) {
		super(parentD, modal);
		this.depMap = depMap;
		this.ctrl = ctrl;
		this.parentDialog = parentD;
		this.parent = parentF;
		initComponents();
		initSpreadSheet();
		setSize(700, 430);
		super.initDialog();

		// fix bug: 確定ボタン押下して UI チェックが失敗の場合は、
		// ダイアログを閉じ時、dialog.isSettle = true;
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				btnCancel.doClick();
			}
		});
		Vector cells = new Vector();
		setDataList(cells);

	}

	void showMessage(String messageID) {
		ctrl.showMessagePub(parent, messageID);
	}

	private void initSpreadSheet() {
		int[] columnWidths = new int[] { 10, 20, 20, 0, 0 };
		String[] columnLabelMessageIDs = new String[] { "C00698", "C00724", "C00725" };

		// 列、行表題のスタイル設定
		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// スプレッドイベントの初期化
		ssJournal.addSpreadSheetSelectChange(btnSettle);

		// 初期表示データの構築
		ds.setNumColumns(columnWidths.length);
		ds.setNumRows(0);

		ssJournal.setDataSource(ds);
	}

	/*
	 * スプレッドデータの設定
	 */
	void setDataList(Vector cells) {
		// sort by 部門コード
		for (int i = 0; i < cells.size(); i++) {
			for (int j = i; j < cells.size(); j++) {
				Vector inner1 = (Vector) cells.get(i);
				Vector inner2 = (Vector) cells.get(j);

				String depCode1 = (String) inner1.get(0);
				String depCode2 = (String) inner2.get(0);

				if (depCode1.compareTo(depCode2) > 0) {
					cells.set(i, inner2);
					cells.set(j, inner1);
				}
			}
		}

		ds.setCells(cells);

		// Set the number of rows in the data source.
		ds.setNumRows(cells.size());

		ssJournal.setDataSource(ds);

	}

	void setCode(String code) {
		this.txtCode.setValue(code);
		this.txtAbbreviationName.setValue("");
		this.txtNameForSearch.setValue("");
	}

	String getSelectedCode() {
		return selectedCode;
	}

	String getSelectedName_S() {
		return selectedName_S;
	}

	/** 確定されたかどうか */
	boolean isSettle = false;

	void find(String code, String name_S, String name_K) {
		find(code, name_S, name_K, true);
	}

	void find(String code, String name_S, String name_K, boolean msgFlg) {
		btnSettle.setEnabled(false);
		Iterator ite = depMap.entrySet().iterator();
		Vector<Vector> cells = new Vector<Vector>();

		while (ite.hasNext()) {
			Map.Entry entry = (Map.Entry) ite.next();

			MG0050DepartmentHierarchyMasterPanelCtrl.TCodeNameSNameK cnn = (MG0050DepartmentHierarchyMasterPanelCtrl.TCodeNameSNameK) entry
				.getValue();

			if ((Util.isNullOrEmpty(code) || cnn.getCode().startsWith(code))
				&& (Util.isNullOrEmpty(name_S) || cnn.getName_S().indexOf(name_S) > -1)
				&& (Util.isNullOrEmpty(name_K) || cnn.getName_K().indexOf(name_K) > -1)) {
				Vector<String> colum = new Vector<String>();
				colum.add(cnn.getCode());
				colum.add(cnn.getName_S());
				colum.add(cnn.getName_K());

				cells.add(colum);
			}
		}
		setDataList(cells);

		if (cells.size() == 0) {
			if (msgFlg) {
				ctrl.showMessagePub(parent, "W00100");
			}
			txtCode.requestFocus();
		} else {
			btnSettle.setEnabled(true);
			ssJournal.requestFocus();
		}
	}

	// *****************************************************************

	/**
	 * フレームを返す
	 */
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlDetial = new TPanel();
		pnlJournal = new TPanel();
		ssJournal = new TTable();
		pnlText = new TPanel();
		txtCode = new TTextField();
		txtNameForSearch = new TTextField();
		txtAbbreviationName = new TTextField();
		pnlButton = new TPanel();
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnCancel = new TButton();

		setLayout(new GridBagLayout());

		pnlDetial.setLayout(new GridBagLayout());

		pnlDetial.setMaximumSize(new Dimension(700, 350));
		pnlDetial.setMinimumSize(new Dimension(700, 350));
		pnlDetial.setPreferredSize(new Dimension(700, 350));
		pnlJournal.setLayout(new javax.swing.BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(680, 290));
		pnlJournal.setMinimumSize(new Dimension(680, 290));
		pnlJournal.setPreferredSize(new Dimension(680, 290));
		ssJournal.setTabControlNo(7);
		ssJournal.setSort(true);
		ssJournal.setEnterToButton(true);
		pnlJournal.add(ssJournal);

		pnlDetial.add(pnlJournal, new GridBagConstraints());

		pnlText.setLayout(new GridBagLayout());

		pnlText.setMaximumSize(new Dimension(680, 30));
		pnlText.setMinimumSize(new Dimension(680, 30));
		pnlText.setPreferredSize(new Dimension(680, 30));
		txtCode.setMaximumSize(new Dimension(126, 20));
		txtCode.setMinimumSize(new Dimension(126, 20));
		txtCode.setPreferredSize(new Dimension(126, 20));
		txtCode.setTabControlNo(1);
		txtCode.setImeMode(false);
		txtCode.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!txtCode.isFocusOwner() || !txtCode.isEditable()) {
						return;
					}
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					find((String) txtCode.getValue(), (String) txtAbbreviationName.getValue(),
						(String) txtNameForSearch.getValue());
					btnSettle.setEnabled(true);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlText.add(txtCode, gridBagConstraints);

		txtNameForSearch.setMaximumSize(new Dimension(245, 20));
		txtNameForSearch.setMinimumSize(new Dimension(245, 20));
		txtNameForSearch.setPreferredSize(new Dimension(245, 20));
		txtNameForSearch.setTabControlNo(3);
		txtNameForSearch.setProhibitionWords("％", "＿");
		txtNameForSearch.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!txtNameForSearch.isFocusOwner() || !txtNameForSearch.isEditable()) {
						return;
					}
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					find((String) txtCode.getValue(), (String) txtAbbreviationName.getValue(),
						(String) txtNameForSearch.getValue());
					btnSettle.setEnabled(true);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlText.add(txtNameForSearch, gridBagConstraints);

		txtAbbreviationName.setMaximumSize(new Dimension(246, 20));
		txtAbbreviationName.setMinimumSize(new Dimension(246, 20));
		txtAbbreviationName.setPreferredSize(new Dimension(246, 20));
		txtAbbreviationName.setTabControlNo(2);
		txtAbbreviationName.setProhibitionWords("％", "＿");
		txtAbbreviationName.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!txtAbbreviationName.isFocusOwner() || !txtAbbreviationName.isEditable()) {
						return;
					}
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					find((String) txtCode.getValue(), (String) txtAbbreviationName.getValue(),
						(String) txtNameForSearch.getValue());
					btnSettle.setEnabled(true);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlText.add(txtAbbreviationName, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 21, 0, 0);
		pnlDetial.add(pnlText, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(680, 35));
		pnlButton.setMinimumSize(new Dimension(680, 35));
		pnlButton.setPreferredSize(new Dimension(680, 35));
		// btnSearch.setText("\u691c \u7d22");
		btnSearch.setLangMessageID("C01057");
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタン押下の処理
			public void actionPerformed(ActionEvent evt) {
				find((String) txtCode.getValue(), (String) txtAbbreviationName.getValue(), (String) txtNameForSearch
					.getValue());
				btnSettle.setEnabled(true);
			}
		});

		btnSettle.setLangMessageID("C01020");
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			// 確定ボタン押下の処理
			public void actionPerformed(ActionEvent evt) {
				int nomRow = ssJournal.getCurrentRow();
				TableDataModel model = ssJournal.getDataSource();

				selectedCode = (String) model.getTableDataItem(nomRow, 0); // コード
				selectedName_S = (String) model.getTableDataItem(nomRow, 1); // 略称
				isSettle = true;
				setVisible(false);
			}
		});

		btnCancel.setLangMessageID("C01136");
		btnCancel.setMaximumSize(new Dimension(110, 25));
		btnCancel.setMinimumSize(new Dimension(110, 25));
		btnCancel.setPreferredSize(new Dimension(110, 25));
		btnCancel.setTabControlNo(6);
		btnCancel.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlButton.add(btnCancel, gridBagConstraints);
		btnCancel.addActionListener(new ActionListener() {

			// 取消ボタン押下の処理
			public void actionPerformed(ActionEvent evt) {
				selectedCode = null;
				selectedName_S = null;
				isSettle = false;
				setVisible(false);
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		pnlDetial.add(pnlButton, gridBagConstraints);

		add(pnlDetial, new GridBagConstraints());

	}

	TButton btnCancel;

	TButton btnSearch;

	TButton btnSettle;

	private TPanel pnlButton;

	private TPanel pnlDetial;

	private TPanel pnlJournal;

	private TPanel pnlText;

	TTable ssJournal;

	TTextField txtAbbreviationName;

	TTextField txtCode;

	TTextField txtNameForSearch;

}
