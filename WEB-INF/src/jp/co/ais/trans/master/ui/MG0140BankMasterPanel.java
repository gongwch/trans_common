package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author zhangzhenxing
 */
public class MG0140BankMasterPanel extends TPanelBusiness {

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** コントロールクラス */
	protected MG0140BankMasterPanelCtrl ctrl;

	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0140BankMasterPanel(MG0140BankMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// 画面の初期化
		initComponents();
		// スプレッドの初期化
		initSpreadSheet();
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initPanel();
		this.btnCopy.setEnabled(false);
		this.btnEdit.setEnabled(false);
		this.btnDelete.setEnabled(false);
	}

	private void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00779", "C00780", "C00781", "C00782", "C00829", "C00783",
				"C00784", "C00785", "C00055", "C00261" };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 6, 8, 20, 20, 20, 20, 20, 20, 6, 6, 0, 0, 0, 0, 0, 0 };
		// 列、行表題のスタイル設定
		ssBank.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッド事件の初期化
		ssBank.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssBank.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssBank.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(10);
		ds.setNumRows(0);

		ssBank.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssBank.setDataSource(ds);

		// 数値を右寄せする
		CellStyleModel defaultStyle = ssBank.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssBank.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssBank.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);

		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnEdit = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		ctrlBeginBankBranch = new TButtonField();
		ctrlEndBankBranch = new TButtonField();
		ctrlBank = new TButtonField();
		pnlDetail = new TPanel();
		ssBank = new TTable();

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

		btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent e) {
				ctrl.insert();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);
		/**
		 * 検索ボタン
		 */
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(6);
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent e) {
				ctrl.find();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
		/**
		 * 編集ボタン
		 */
		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(7);
		btnEdit.setTabEnabled(false);
		btnEdit.setVerifyInputWhenFocusTarget(false);
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent e) {
				ctrl.update();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);
		/**
		 * 複写ボタン
		 */
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(8);
		btnCopy.setTabEnabled(false);
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent e) {
				ctrl.copy();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);
		/**
		 * 削除ボタン
		 */
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(9);
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent e) {
				ctrl.delete();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);
		/**
		 * リスト出力ボタン
		 */
		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(10);
		btnListOutput.addActionListener(new ActionListener() {

			// リスト出力ボタンを押下
			public void actionPerformed(ActionEvent e) {
				ctrl.outptExcel();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(400, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(400, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(400, 80));

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		ctrlBeginBankBranch.setButtonSize(85);
		ctrlBeginBankBranch.setFieldSize(55);
		ctrlBeginBankBranch.setLangMessageID("C00778");
		ctrlBeginBankBranch.getField().setMaxLength(4);
		ctrlBeginBankBranch.setNoticeMaxLength(30);

		ctrlBeginBankBranch.setNoticeSize(160);
		ctrlBeginBankBranch.setTabControlNo(2);
		ctrlBeginBankBranch.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlRangeSpecification.add(ctrlBeginBankBranch, gridBagConstraints);

		ctrlEndBankBranch.setButtonSize(85);
		ctrlEndBankBranch.setFieldSize(55);
		ctrlEndBankBranch.setLangMessageID("C00778");
		ctrlEndBankBranch.getField().setMaxLength(4);
		ctrlEndBankBranch.setNoticeMaxLength(30);

		ctrlEndBankBranch.setNoticeSize(160);
		ctrlEndBankBranch.setTabControlNo(3);
		ctrlEndBankBranch.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlRangeSpecification.add(ctrlEndBankBranch, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		ctrlBank.setButtonSize(85);
		ctrlBank.setFieldSize(55);
		ctrlBank.setLangMessageID("C01501");
		ctrlBank.getField().setMaxLength(4);
		ctrlBank.setNoticeMaxLength(30);

		ctrlBank.setNoticeSize(180);
		ctrlBank.setTabControlNo(1);
		ctrlBank.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlHeader.add(ctrlBank, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));

		pnlDetail.setMaximumSize(new Dimension(780, 460));
		pnlDetail.setMinimumSize(new Dimension(780, 460));
		pnlDetail.setPreferredSize(new Dimension(780, 460));

		ssBank.setTabControlNo(4);
		pnlDetail.add(ssBank);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TButtonField ctrlBank;

	TButtonField ctrlBeginBankBranch;

	TButtonField ctrlEndBankBranch;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlRangeSpecification;

	TTable ssBank;

}
