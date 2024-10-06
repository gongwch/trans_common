package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 通貨マスタ編集ダイアログ
 */
public class MG0300EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	public MG0300EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	public boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	public MG0300EditDisplayDialog(Frame parent, boolean modal, MG0300EditDisplayDialogCtrl ctrl, String titleid) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		setLangMessageID(titleid);
		setSize(600, 250);
		super.initDialog();
	}

	/**
	 * 画面コンポーネントの構築
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints;

		getContentPane().setLayout(new GridBagLayout());

		// ボタンパネル
		pnlButton = new TPanel();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(600, 25));
		pnlButton.setMinimumSize(new Dimension(600, 25));
		pnlButton.setPreferredSize(new Dimension(600, 25));

		// 確定ボタン
		btnSettle = new TImageButton(IconType.SETTLE);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setTabControlNo(11);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 315, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});

		// 取消ボタン
		btnReturn = new TButton();
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setTabControlNo(12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * 戻りボタン押下の処理
			 */
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(20, 20, 10, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		// メインパネル
		pnlDetail = new TPanel();
		pnlDetail.setLayout(new GridBagLayout());
		pnlDetail.setMaximumSize(new Dimension(600, 145));
		pnlDetail.setMinimumSize(new Dimension(600, 145));
		pnlDetail.setPreferredSize(new Dimension(600, 145));

		// 通貨コード
		ctrlCurrencyCode = new TLabelField();
		ctrlCurrencyCode.setFieldSize(45);
		ctrlCurrencyCode.setLabelSize(100);
		ctrlCurrencyCode.setLangMessageID("C00665");
		ctrlCurrencyCode.getField().setAllowedSpace(false);
		ctrlCurrencyCode.getField().setTabControlNo(1);
		ctrlCurrencyCode.setImeMode(false);
		ctrlCurrencyCode.getField().setMaxLength(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(ctrlCurrencyCode, gridBagConstraints);

		// 通貨名称
		ctrlCurrencyName = new TLabelField();
		ctrlCurrencyName.setFieldSize(420);
		ctrlCurrencyName.setLabelSize(100);
		ctrlCurrencyName.setLangMessageID("C00880");
		ctrlCurrencyName.getField().setTabControlNo(2);
		ctrlCurrencyName.getField().setMaxLength(40);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlCurrencyName, gridBagConstraints);

		// 通貨略称
		ctrlCurrencyAbbreviatedName = new TLabelField();
		ctrlCurrencyAbbreviatedName.setFieldSize(230);
		ctrlCurrencyAbbreviatedName.setLabelSize(100);
		ctrlCurrencyAbbreviatedName.setLangMessageID("C00881");
		ctrlCurrencyAbbreviatedName.getField().setTabControlNo(3);
		ctrlCurrencyAbbreviatedName.getField().setMaxLength(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlCurrencyAbbreviatedName, gridBagConstraints);

		// 通貨検索名称
		ctrlCurrencyNameForSearch = createForSearchCtrl();
		ctrlCurrencyNameForSearch.setFieldSize(230);
		ctrlCurrencyNameForSearch.setLabelSize(100);
		ctrlCurrencyNameForSearch.setLangMessageID("C00882");
		ctrlCurrencyNameForSearch.getField().setTabControlNo(4);
		ctrlCurrencyNameForSearch.getField().setMaxLength(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlCurrencyNameForSearch, gridBagConstraints);

		// 換算区分 - パネル
		pnlTranslateDivision = new TPanel();
		pnlTranslateDivision.setLayout(new GridBagLayout());
		pnlTranslateDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlTranslateDivision.setLangMessageID("C00897");
		pnlTranslateDivision.setMaximumSize(new Dimension(180, 50));
		pnlTranslateDivision.setMinimumSize(new Dimension(180, 50));
		pnlTranslateDivision.setPreferredSize(new Dimension(180, 50));

		// 換算区分 - 掛算
		rdoCalculateMultiplication = new TRadioButton();
		rdoCalculateMultiplication.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		rdoCalculateMultiplication.setLangMessageID("C00065");
		rdoCalculateMultiplication.setMargin(new Insets(0, 0, 0, 0));
		pnlTranslateDivision.add(rdoCalculateMultiplication, new GridBagConstraints());
		rdoCalculateMultiplication.setTabControlNo(5);

		// 換算区分 - 割算
		rdoCalculateDivision = new TRadioButton();
		rdoCalculateDivision.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		rdoCalculateDivision.setLangMessageID("C00563");
		rdoCalculateDivision.setMargin(new Insets(0, 0, 0, 0));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 9, 0, 0);
		pnlTranslateDivision.add(rdoCalculateDivision, gridBagConstraints);
		rdoCalculateDivision.setTabControlNo(6);

		// 換算区分 - ボタングループ
		rdoGroup = new ButtonGroup();
		rdoGroup.add(rdoCalculateMultiplication);
		rdoGroup.add(rdoCalculateDivision);
		rdoCalculateMultiplication.doClick();

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, 12, 0, 0);
		pnlDetail.add(pnlTranslateDivision, gridBagConstraints);

		// レート係数
		numRatecoefficient = new TLabelNumericField();
		numRatecoefficient.setNumericFormat("#");
		numRatecoefficient.setFieldSize(20);
		numRatecoefficient.setLabelSize(100);
		numRatecoefficient.setLangMessageID("C00896");
		numRatecoefficient.getField().setTabControlNo(7);
		numRatecoefficient.getField().setMaxLength(1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(numRatecoefficient, gridBagConstraints);

		// 小数点桁数
		numDecimalPoint = new TLabelNumericField();
		numDecimalPoint.setNumericFormat("#");
		numDecimalPoint.setFieldSize(20);
		numDecimalPoint.setLabelSize(120);
		numDecimalPoint.setLangMessageID("C00884");
		numDecimalPoint.getField().setMaxLength(1);
		numDecimalPoint.getField().setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail.add(numDecimalPoint, gridBagConstraints);

		// 開始年月日
		dtBeginDate = new TLabelPopupCalendar();
		dtBeginDate.setLabelSize(100);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);
		dtBeginDate.setTabControlNo(9);

		// 終了年月日
		dtEndDate = new TLabelPopupCalendar();
		dtEndDate.setLabelSize(90);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);
		dtEndDate.setRequestFocusEnabled(false);
		dtEndDate.setTabControlNo(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(10, 20, 20, 20);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	public TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	/** ボタンパネル */
	public TPanel pnlButton;

	/** メインパネル */
	public TPanel pnlDetail;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 取消ボタン */
	public TButton btnReturn;

	/** 通貨コード */
	public TLabelField ctrlCurrencyCode;

	/** 通貨名称 */
	public TLabelField ctrlCurrencyName;

	/** 通貨略称 */
	public TLabelField ctrlCurrencyAbbreviatedName;

	/** 通貨検索名称 */
	public TLabelField ctrlCurrencyNameForSearch;

	/** 換算区分パネル */
	public TPanel pnlTranslateDivision;

	/** 換算区分ボタングループ */
	public ButtonGroup rdoGroup;

	/** 掛算 */
	public TRadioButton rdoCalculateMultiplication;

	/** 割算 */
	public TRadioButton rdoCalculateDivision;

	/** レート係数 */
	public TLabelNumericField numRatecoefficient;

	/** 小数点以下桁数 */
	public TLabelNumericField numDecimalPoint;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;
}