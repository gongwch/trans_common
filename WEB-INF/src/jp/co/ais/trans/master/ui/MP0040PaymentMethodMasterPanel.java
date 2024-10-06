package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author ISFnet China
 */
public class MP0040PaymentMethodMasterPanel extends TPanelBusiness {

	/** コントロールクラス */
	private MP0040PaymentMethodMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	public MP0040PaymentMethodMasterPanel(MP0040PaymentMethodMasterPanelCtrl ctrl) {

		this.ctrl = ctrl;
		// 画面の初期化
		initComponents();
		// スプレッドの初期化
		initSpreadSheet();

		setBtnLock(false);
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initPanel();

	}

	/**
	 * パネルでボタンの状態を設定する
	 * 
	 * @param bool true或false
	 */
	public void setBtnLock(Boolean bool) {

		this.btnEdit.setEnabled(bool);
		this.btnCopy.setEnabled(bool);
		this.btnDelete.setEnabled(bool);

	}

	protected void initSpreadSheet() {

		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
		int ukmSize = 0;
		if (compInfo.isUseBreakDownItem()) {
			ukmSize = 10;
		}
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00864", "C00865", "C00866", compInfo.getItemName(),
				compInfo.getSubItemName(), compInfo.getBreakDownItemName(), "C00571", "C01096", "C01097", "C00055",
				"C00261" };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 0, 8, 20, 40, 10, 10, ukmSize, 10, 7, 9, 6, 6, 0, 0, 0, 0, 0, 0, 0 };

		compInfo.getBreakDownItemName();

		// 列、行表題のスタイル設定
		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// スプレッドイベントの初期化
		ssJournal.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(columnWidths.length);
		ds.setNumRows(0);

		ssJournal.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);

		// Set the number of rows in the data source.
		ds.setNumRows(cells.size());

		ssJournal.setDataSource(ds);

		// 数値を右寄せする
		CellStyleModel defaultStyle = ssJournal.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 10, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 11, centerStyle);

		// データがあると、ボタンの状態を使用可能になる
		setBtnLock(cells.size() > 0);
	}

	// **********************************************************************

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/**
	 * フレーム取得
	 */
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlBusiness = new jp.co.ais.trans.common.gui.TPanel();
		pnlButton = new TMainHeaderPanel();
		btnNew = new jp.co.ais.trans2.common.gui.TImageButton(IconType.NEW);
		btnSearch = new jp.co.ais.trans2.common.gui.TImageButton(IconType.SEARCH);
		btnEdit = new jp.co.ais.trans2.common.gui.TImageButton(IconType.EDIT);
		btnCopy = new jp.co.ais.trans2.common.gui.TImageButton(IconType.COPY);
		btnDelete = new jp.co.ais.trans2.common.gui.TImageButton(IconType.DELETE);
		btnListOutput = new jp.co.ais.trans2.common.gui.TImageButton(IconType.EXCEL);
		pnlHeader = new jp.co.ais.trans.common.gui.TPanel();
		pnlRangeSpecification = new jp.co.ais.trans.common.gui.TPanel();
		ctrlBeginPaymentMethod = new jp.co.ais.trans.common.gui.TButtonField();
		ctrlEndPaymentMethod = new jp.co.ais.trans.common.gui.TButtonField();
		lblBegin = new jp.co.ais.trans.common.gui.TLabel();
		lblEnd = new jp.co.ais.trans.common.gui.TLabel();
		pnlPaymentDivision = new jp.co.ais.trans.common.gui.TPanel();
		chkEmployeePayment = new jp.co.ais.trans.common.gui.TCheckBox();
		chkExternalPayment = new jp.co.ais.trans.common.gui.TCheckBox();
		pnlJournal = new jp.co.ais.trans.common.gui.TPanel();
		ssJournal = new jp.co.ais.trans.common.gui.TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlBusiness.setLayout(new GridBagLayout());
		pnlBusiness.setMaximumSize(new Dimension(800, 600));
		pnlBusiness.setMinimumSize(new Dimension(800, 600));
		pnlBusiness.setPreferredSize(new Dimension(800, 600));

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
		btnNew.setTabControlNo(6);
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
		btnSearch.setTabControlNo(7);
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
		btnEdit.setTabControlNo(8);
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
		btnCopy.setTabControlNo(9);
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
		btnDelete.setTabControlNo(10);
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
		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(11);
		btnListOutput.addActionListener(new ActionListener() {

			// リスト出力ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.outptExcel();
			}
		});
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		pnlBusiness.add(pnlButton, gridBagConstraints);

		// 境界線
		JSeparator sep = new JSeparator();
		TGuiUtil.setComponentSize(sep, 0, 3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		pnlBusiness.add(sep, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());
		pnlHeader.setMaximumSize(new Dimension(800, 143));
		pnlHeader.setMinimumSize(new Dimension(800, 143));
		pnlHeader.setPreferredSize(new Dimension(800, 143));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(475, 140));
		pnlRangeSpecification.setMinimumSize(new Dimension(475, 140));
		pnlRangeSpecification.setPreferredSize(new Dimension(475, 140));

		ctrlBeginPaymentMethod.setButtonSize(85);
		ctrlBeginPaymentMethod.setFieldSize(45);
		ctrlBeginPaymentMethod.setLangMessageID("C00233");
		ctrlBeginPaymentMethod.setMaxLength(3);
		ctrlBeginPaymentMethod.setNoticeSize(230);
		ctrlBeginPaymentMethod.setTabControlNo(3);
		ctrlBeginPaymentMethod.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		pnlRangeSpecification.add(ctrlBeginPaymentMethod, gridBagConstraints);

		ctrlEndPaymentMethod.setButtonSize(85);
		ctrlEndPaymentMethod.setFieldSize(45);
		ctrlEndPaymentMethod.setLangMessageID("C00233");
		ctrlEndPaymentMethod.setMaxLength(3);
		ctrlEndPaymentMethod.setNoticeSize(230);
		ctrlEndPaymentMethod.setTabControlNo(4);
		ctrlEndPaymentMethod.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 10, 10, 0);
		pnlRangeSpecification.add(ctrlEndPaymentMethod, gridBagConstraints);

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		pnlPaymentDivision.setLayout(new GridBagLayout());
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setMaximumSize(new Dimension(250, 50));
		pnlPaymentDivision.setMinimumSize(new Dimension(250, 50));
		pnlPaymentDivision.setPreferredSize(new Dimension(250, 50));

		chkEmployeePayment.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkEmployeePayment.setLangMessageID("C01119");
		TGuiUtil.setComponentSize(chkEmployeePayment, 90, 20);
		chkEmployeePayment.setTabControlNo(1);
		chkEmployeePayment.setMargin(new Insets(0, 0, 0, 0));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlPaymentDivision.add(chkEmployeePayment, gridBagConstraints);

		chkExternalPayment.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkExternalPayment.setLangMessageID("C01124");
		TGuiUtil.setComponentSize(chkExternalPayment, 90, 20);
		chkExternalPayment.setMargin(new Insets(0, 0, 0, 0));
		chkExternalPayment.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 15, 0, 0);
		pnlPaymentDivision.add(chkExternalPayment, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 113);
		pnlRangeSpecification.add(pnlPaymentDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 245);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlBusiness.add(pnlHeader, gridBagConstraints);

		pnlJournal.setLayout(new javax.swing.BoxLayout(pnlJournal, javax.swing.BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 400));
		pnlJournal.setMinimumSize(new Dimension(780, 400));
		pnlJournal.setPreferredSize(new Dimension(780, 400));
		ssJournal.setTabControlNo(5);
		pnlJournal.add(ssJournal);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlBusiness.add(pnlJournal, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlBusiness, gridBagConstraints);

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TCheckBox chkEmployeePayment;

	TCheckBox chkExternalPayment;

	TButtonField ctrlBeginPaymentMethod;

	TButtonField ctrlEndPaymentMethod;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlHeader;

	TPanel pnlJournal;

	TPanel pnlPaymentDivision;

	TPanel pnlRangeSpecification;

	TTable ssJournal;

	TPanel pnlBusiness;

}
