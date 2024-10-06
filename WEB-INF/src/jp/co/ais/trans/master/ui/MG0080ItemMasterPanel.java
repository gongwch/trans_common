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

import jp.co.ais.trans.common.bizui.*;

/**
 * 科目マスタ画面
 * 
 * @author zhangzhenxing
 */
public class MG0080ItemMasterPanel extends TPanelBusiness {

	/** シリアルUID */
	protected static final long serialVersionUID = 504251053969105797L;

	/** コントロールクラス */
	protected MG0080ItemMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0080ItemMasterPanel(MG0080ItemMasterPanelCtrl ctrl) {
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
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00572", "C00700", "C00730", "C00731", "C01148",
				"C01007", "C01226", "C01314", "C02066", "C00573", "C00968", "C00959", "C00960", "C00962", "C01134",
				"C01284", "C01217", "C02078", "C01301", "C01272", "C01155", "C01188", "C01049", "C01083", "C01079",
				"C01081", "C01102", "C01094", "C01223", "C01120", "C01026", "C01028", "C01030", "C01032", "C01034",
				"C01036", "C01288", "C01289", "C01290", "C01282", "C01088", "C02076", "C02077", "C00055", "C00261" };

		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 0, 10, 20, 15, 20, 7, 7, 7, 7, 7, 8, 9, 9, 9, 10, 11, 11, 11, 11, 11, 11, 11,
				11, 13, 13, 13, 13, 13, 13, 10, 10, 9, 9, 9, 9, 9, 9, 10, 10, 10, 11, 11, 8, 8, 6, 6, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		ssItem.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// スプレッドイベントの初期化
		ssItem.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssItem.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssItem.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(46);
		ds.setNumRows(0);

		ssItem.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {

		// 数値を右寄せする
		CellStyleModel defaultStyle = ssItem.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 5, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 7, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 11, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 12, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 13, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 14, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 17, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 18, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 44, centerStyle);
		ssItem.setCellStyle(JCTableEnum.ALLCELLS, 45, centerStyle);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssItem.setDataSource(ds);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
	}

	/**
	 * 
	 */
	public void initComponents() {
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
		ctrlBeginItem = new TItemField();
		ctrlEndItem = new TItemField();
		pnlDetail = new TPanel();
		ssItem = new TTable();

		setLayout(new GridBagLayout());

		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(4);
		btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);

		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.find();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);

		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(6);
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(7);
		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(8);
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
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(9);
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.outptExcel();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));
		pnlRangeSpecification.setLayout(new GridBagLayout());

		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(660, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(660, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(660, 80));

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		ctrlBeginItem.setButtonSize(85);
		ctrlBeginItem.setFieldSize(120);
		ctrlBeginItem.setNoticeSize(350);
		ctrlBeginItem.setLangMessageID("C01006");
		ctrlBeginItem.setTabControlNo(1);
		ctrlBeginItem.setImeMode(false);
		ctrlBeginItem.setCheckMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginItem, gridBagConstraints);

		ctrlEndItem.setButtonSize(85);
		ctrlEndItem.setFieldSize(120);
		ctrlEndItem.setNoticeSize(350);
		ctrlEndItem.setLangMessageID("C01006");
		ctrlEndItem.setTabControlNo(2);
		ctrlEndItem.setImeMode(false);
		ctrlEndItem.setCheckMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndItem, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 60);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

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
		pnlDetail.add(ssItem);
		ssItem.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

		// 科目開始押下
		ctrlBeginItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrlBeginItem.getInputParameter().setEndCode(ctrlEndItem.getValue());
			}
		});

		// 科目終了押下
		ctrlEndItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrlEndItem.getInputParameter().setBeginCode(ctrlBeginItem.getValue());
			}
		});

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TItemField ctrlBeginItem;

	TItemField ctrlEndItem;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlRangeSpecification;

	TTable ssItem;

}
