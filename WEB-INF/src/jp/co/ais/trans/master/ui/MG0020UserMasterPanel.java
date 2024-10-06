package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ユーザーマスタパネル
 */
public class MG0020UserMasterPanel extends TPanelBusiness {

	/** コントロールクラス */
	private MG0020UserMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	protected JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	protected MG0020UserMasterPanel(MG0020UserMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		initSpreadSheet();
		super.initPanel();
		this.btnEdit.setEnabled(false);
		this.btnCopy.setEnabled(false);
		this.btnDelete.setEnabled(false);

	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00589", "C00597", "C00699", "C00691", "C00692",
				"C00693", "C00112", "C01082", "C01078", "C00180", "C00230", "C00020", "C00096", "C00041", "C00491",
				"C01222", "C00537", "C00206", "C00176", "C02044", "C00525", "C00297", "C00170", "C00697", "C02043",
				"C00139", "C00055", "C00261" };
		int[] columnWidths = new int[] { 6, 8, 0, 6, 22, 22, 22, 5, 5, 5, 5, 5, 5, 5, 6, 6, 0, 5, 5, 6, 0, 5, 8, 8, 10,
				10, 10, 6, 6, 0, 0, 0 };
		// 列、行表題のスタイル設定
		ssUserList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッドイベントの初期化
		ssUserList.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssUserList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssUserList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(29);
		ds.setNumRows(0);

		ssUserList.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		// 数値を右寄せする
		CellStyleModel defaultStyle = ssUserList.getDefaultCellStyle();
		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssUserList.setCellStyle(JCTableEnum.ALLCELLS, 22, rightStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssUserList.setCellStyle(JCTableEnum.ALLCELLS, 26, centerStyle);
		ssUserList.setCellStyle(JCTableEnum.ALLCELLS, 27, centerStyle);
		ssUserList.setCellStyle(JCTableEnum.ALLCELLS, 28, centerStyle);

		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssUserList.setDataSource(ds);
	}

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnNew = new TButton();
		btnSearch = new TButton();
		btnEdit = new TButton();
		btnCopy = new TButton();
		btnDelete = new TButton();
		btnListOutput = new TButton();
		pnlDetail1 = new TPanel();
		pnlRangeSpecification = new TPanel();
		lblBeginUser = new TLabel();
		lblEndUser = new TLabel();
		ctrlBeginUser = new TButtonField();
		ctrlEndUser = new TButtonField();
		ctrlCompanyCode = new TButtonField();

		pnlDetail2 = new TPanel();
		ssUserList = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));
		/**
		 * 新規ボタン
		 */
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);
		btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});

		/**
		 * 検索ボタン
		 */
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
		pnlButton.add(btnSearch, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
			GridBagConstraints.NONE, new Insets(10, 10, 5, 0), 0, 0));
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.find();
			}
		});

		/**
		 * 編集ボタン
		 */
		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});

		/**
		 * 複写ボタン
		 */
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);
		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});

		/**
		 * 削除ボタン
		 */
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		/**
		 * リスト出力ボタン
		 */
		btnListOutput.setLangMessageID("C03084");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(120, 25));
		btnListOutput.setMinimumSize(new Dimension(120, 25));
		btnListOutput.setPreferredSize(new Dimension(120, 25));
		btnListOutput.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);
		btnListOutput.addActionListener(new ActionListener() {

			// リスト出力ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.outptExcel();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(pnlButton, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());
		pnlDetail1.setMaximumSize(new Dimension(800, 105));
		pnlDetail1.setMinimumSize(new Dimension(800, 105));
		pnlDetail1.setPreferredSize(new Dimension(800, 105));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(720, 105));
		pnlRangeSpecification.setMinimumSize(new Dimension(720, 105));
		pnlRangeSpecification.setPreferredSize(new Dimension(720, 105));

		// 会社
		ctrlCompanyCode.setButtonSize(85);
		ctrlCompanyCode.setFieldSize(120);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setNoticeSize(410);
		ctrlCompanyCode.setTabControlNo(1);
		ctrlCompanyCode.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlCompanyCode, gridBagConstraints);

		lblBeginUser.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBeginUser, gridBagConstraints);

		lblEndUser.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEndUser, gridBagConstraints);

		ctrlBeginUser.setButtonSize(85);
		ctrlBeginUser.setFieldSize(120);
		ctrlBeginUser.setLangMessageID("C00528");
		ctrlBeginUser.setMaxLength(10);
		ctrlBeginUser.setNoticeSize(410);
		ctrlBeginUser.setTabControlNo(2);
		ctrlBeginUser.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginUser, gridBagConstraints);

		ctrlEndUser.setButtonSize(85);
		ctrlEndUser.setFieldSize(120);
		ctrlEndUser.setLangMessageID("C00528");
		ctrlEndUser.setMaxLength(10);
		ctrlEndUser.setNoticeSize(410);
		ctrlEndUser.setTabControlNo(3);
		ctrlEndUser.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndUser, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail1.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new BoxLayout(pnlDetail2, BoxLayout.X_AXIS));
		pnlDetail2.setMaximumSize(new Dimension(780, 435));
		pnlDetail2.setMinimumSize(new Dimension(780, 435));
		pnlDetail2.setPreferredSize(new Dimension(780, 435));
		ssUserList.setTabControlNo(4);
		pnlDetail2.add(ssUserList);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail2, gridBagConstraints);

	}

	protected TButton btnCopy;

	protected TButton btnDelete;

	protected TButton btnEdit;

	protected TButton btnListOutput;

	protected TButton btnNew;

	protected TButton btnSearch;

	protected TButtonField ctrlBeginUser;

	protected TButtonField ctrlEndUser;

	/** 会社 */
	protected TButtonField ctrlCompanyCode;

	protected TLabel lblBeginUser;

	protected TLabel lblEndUser;

	protected TPanel pnlButton;

	protected TPanel pnlDetail1;

	protected TPanel pnlDetail2;

	protected TPanel pnlRangeSpecification;

	protected TTable ssUserList;

}
