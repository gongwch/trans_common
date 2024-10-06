package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 補助科目マスタ画面
 * 
 * @author zhangzhenxing
 */
public class MG0090SubItemMasterPanel extends TPanelBusiness {

	/** シリアルUID */
	protected static final long serialVersionUID = -2918089233158677264L;

	/** コントロールクラス */
	protected MG0090SubItemMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/** 科目field Enterを押下FLAG */
	boolean lostFocusFlag = false;

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0090SubItemMasterPanel(MG0090SubItemMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;

		// 画面の初期化
		initComponents();

		// スプレッドの初期化
		initSpreadSheet();

		super.initPanel();

		this.btnEdit.setEnabled(false);
		this.btnCopy.setEnabled(false);
		this.btnDelete.setEnabled(false);
	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00572", "C00602", "C00701", "C00739", "C00740",
				"C01264", "C00573", "C01272", "C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102",
				"C01094", "C01134", "C01284", "C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036",
				"C01288", "C01289", "C01290", "C01282", "C01088", "C01223", "C01301", "C00055", "C00261" };

		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 0, 10, 10, 40, 20, 40, 5, 8, 11, 11, 11, 13, 12, 13, 13, 13, 13, 11, 11, 9, 9,
				9, 9, 9, 9, 9, 10, 10, 10, 11, 11, 10, 10, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		ssSubItem.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// スプレッドイベントの初期化
		ssSubItem.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssSubItem.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssSubItem.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(35);
		ds.setNumRows(0);
		ssSubItem.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	protected void setDataList(Vector cells) {
		// 数値を右寄せする
		CellStyleModel defaultStyle = ssSubItem.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssSubItem.setCellStyle(JCTableEnum.ALLCELLS, 6, centerStyle);
		ssSubItem.setCellStyle(JCTableEnum.ALLCELLS, 33, centerStyle);
		ssSubItem.setCellStyle(JCTableEnum.ALLCELLS, 34, centerStyle);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssSubItem.setDataSource(ds);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
	}

	/**
	 * 画面コンポーネントの構築
	 */

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		ctrlItem = new TItemField();
		ctrlEndSubItem = new TSubItemField();
		ctrlBeginSubItem = new TSubItemField();
		pnlButton = new TMainHeaderPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnEdit = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		pnlDetail = new TPanel();
		ssSubItem = new TTable();

		setLayout(new GridBagLayout());

		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 108));
		pnlHeader.setMinimumSize(new Dimension(800, 108));
		pnlHeader.setPreferredSize(new Dimension(800, 108));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(720, 105));
		pnlRangeSpecification.setMinimumSize(new Dimension(720, 105));
		pnlRangeSpecification.setPreferredSize(new Dimension(720, 105));

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		ctrlItem.setButtonSize(85);
		ctrlItem.setFieldSize(120);
		ctrlItem.setNoticeSize(410);
		ctrlItem.setLangMessageID("C01006");
		ctrlItem.setTabControlNo(1);
		ctrlItem.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlItem, gridBagConstraints);

		// 科目field Enterキを押下
		ctrlItem.getField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER && lostFocusFlag == false) {
					ctrlItem.itemLostFocus();
					if (ctrlItem.getOutputParameter().isIncludeSubItem()) {
						ctrlBeginSubItem.requestFocus();
					}
				}
				lostFocusFlag = false;
			}
		});

		ctrlItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setItemCondition();
			}
		});

		ctrlItem.addCallControl(new CallBackListener() {

			public void after() {
				boolean isEdit = ctrlItem.getOutputParameter().isIncludeSubItem();
				ctrlBeginSubItem.setEditMode(isEdit);
				ctrlEndSubItem.setEditMode(isEdit);
				lostFocusFlag = true;
			}
		});

		ctrlEndSubItem.setButtonSize(85);
		ctrlEndSubItem.setFieldSize(120);
		ctrlEndSubItem.setNoticeSize(410);
		ctrlEndSubItem.setLangMessageID("C01313");
		ctrlEndSubItem.setTabControlNo(3);
		ctrlEndSubItem.setImeMode(false);
		ctrlEndSubItem.setCheckMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndSubItem, gridBagConstraints);

		ctrlEndSubItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setEndItemSubCondition();
			}
		});

		ctrlBeginSubItem.setButtonSize(85);
		ctrlBeginSubItem.setFieldSize(120);
		ctrlBeginSubItem.setNoticeSize(410);
		ctrlBeginSubItem.setLangMessageID("C01313");
		ctrlBeginSubItem.setTabControlNo(2);
		ctrlBeginSubItem.setImeMode(false);
		ctrlBeginSubItem.setCheckMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginSubItem, gridBagConstraints);

		ctrlBeginSubItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setBeginItemSubCondition();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlHeader, gridBagConstraints);

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
			public void actionPerformed(ActionEvent evt) {
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
		btnSearch.setTabControlNo(6);
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
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
		btnEdit.setTabControlNo(7);
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
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
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));

		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
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
		btnDelete.setTabControlNo(9);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);

		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// リスト出力ボタンを押下
				ctrl.outptExcel();
			}
		});
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));
		pnlDetail.setMaximumSize(new Dimension(780, 435));
		pnlDetail.setMinimumSize(new Dimension(780, 435));
		pnlDetail.setPreferredSize(new Dimension(780, 435));
		pnlDetail.add(ssSubItem);
		ssSubItem.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

		// 補助科目開始押下
		ctrlBeginSubItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrlBeginSubItem.getInputParameter().setEndCode(ctrlEndSubItem.getValue());
			}
		});

		// 補助科目終了押下
		ctrlEndSubItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrlEndSubItem.getInputParameter().setBeginCode(ctrlBeginSubItem.getValue());
			}
		});

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TSubItemField ctrlBeginSubItem;

	TSubItemField ctrlEndSubItem;

	TItemField ctrlItem;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlRangeSpecification;

	TTable ssSubItem;

}
