package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 支払方法マスタ編集ダイアログ
 * 
 * @author ISFnet China
 */
public class MP0040EditDisplayDialog extends jp.co.ais.trans.common.gui.TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MP0040EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	public MP0040EditDisplayDialog(Frame parent, boolean modal, MP0040EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(650, 480);

		super.initDialog();
	}

	/**
	 * Frame 取得
	 * 
	 * @return Frame
	 */
	@Override
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	/**
	 * 画面初期化
	 */
	protected void initComponents() {

		// GridBagConstraints 宣言
		GridBagConstraints gridBagConstraints;

		// GridBagLayout 初期化
		getContentPane().setLayout(new GridBagLayout());

		// ボタンパネル
		pnlButton = new TPanel();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(610, 40));
		pnlButton.setMinimumSize(new Dimension(610, 40));
		pnlButton.setPreferredSize(new Dimension(610, 40));

		// 確定ボタン
		btnSettle = new TImageButton(IconType.SETTLE);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 350, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		// 確定ボタンイベント定義
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
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(14);
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		// 取消ボタンイベント定義
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * 戻りボタン押下の処理
			 */
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});

		// ボタンパネル 配置
		getContentPane().add(pnlButton, gridBagConstraints);

		// メインパネル1
		pnlDetail1 = new TPanel();
		pnlDetail1.setLayout(new GridBagLayout());
		pnlDetail1.setMaximumSize(new Dimension(610, 80));
		pnlDetail1.setMinimumSize(new Dimension(610, 80));
		pnlDetail1.setPreferredSize(new Dimension(610, 80));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);

		// 支払方法コード
		ctrlPaymentMethodCode = new TLabelField();
		ctrlPaymentMethodCode.setFieldSize(50);
		ctrlPaymentMethodCode.setLabelSize(150);
		ctrlPaymentMethodCode.setLangMessageID("C00864");
		ctrlPaymentMethodCode.getField().setAllowedSpace(false);
		ctrlPaymentMethodCode.setMaxLength(3);
		ctrlPaymentMethodCode.setTabControlNo(1);
		ctrlPaymentMethodCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlPaymentMethodCode, gridBagConstraints);

		// 支払方法名称
		ctrlPaymentMethodName = new TLabelField();
		ctrlPaymentMethodName.setFieldSize(300);
		ctrlPaymentMethodName.setLabelSize(150);
		ctrlPaymentMethodName.setLangMessageID("C00865");
		ctrlPaymentMethodName.setMaxLength(20);
		ctrlPaymentMethodName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlPaymentMethodName, gridBagConstraints);

		// 支払方法検索名称
		ctrlPaymentMethodNameForSearch = createForSearchCtrl();
		ctrlPaymentMethodNameForSearch.setFieldSize(300);
		ctrlPaymentMethodNameForSearch.setLabelSize(150);
		ctrlPaymentMethodNameForSearch.setLangMessageID("C00866");
		ctrlPaymentMethodNameForSearch.setMaxLength(40);
		ctrlPaymentMethodNameForSearch.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 150);
		pnlDetail1.add(ctrlPaymentMethodNameForSearch, gridBagConstraints);

		// メインパネル1 配置
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetail1, gridBagConstraints);

		// メインパネル2 (科目)
		pnlDetail2 = new TPanel();
		pnlDetail2.setLayout(new GridBagLayout());
		pnlDetail2.setMaximumSize(new Dimension(610, 130));
		pnlDetail2.setMinimumSize(new Dimension(610, 130));
		pnlDetail2.setPreferredSize(new Dimension(610, 130));

		// 科目サイズ初期化
		ctrlItemUnit = new TAccountItemUnit();
		setItemSize();
		ctrlItemUnit.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;

		// 科目コンポーネント取得
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setSummaryDivision("0");
		ctrlItemUnit.getTItemField().setInputParameter(inputParameter);
		pnlDetail2.add(ctrlItemUnit, gridBagConstraints);

		// 計上部門
		ctrlAppropriateDepartment = new TButtonField();
		ctrlAppropriateDepartment.setLangMessageID("C00863");
		ctrlAppropriateDepartment.setMaximumSize(new Dimension(520, 20));
		ctrlAppropriateDepartment.setMinimumSize(new Dimension(520, 20));
		ctrlAppropriateDepartment.setButtonSize(85);
		ctrlAppropriateDepartment.setFieldSize(80);
		ctrlAppropriateDepartment.setNoticeSize(221);
		ctrlAppropriateDepartment.setMaxLength(10);
		ctrlAppropriateDepartment.setTabControlNo(7);
		ctrlAppropriateDepartment.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(15, 0, 0, 82);
		pnlDetail2.add(ctrlAppropriateDepartment, gridBagConstraints);

		// メインパネル2 配置
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail2, gridBagConstraints);

		// メインパネル3
		pnlDetail3 = new TPanel();
		pnlDetail3.setLayout(new GridBagLayout());
		pnlDetail3.setMaximumSize(new Dimension(560, 150));
		pnlDetail3.setMinimumSize(new Dimension(560, 150));
		pnlDetail3.setPreferredSize(new Dimension(560, 150));

		// 支払対象区分パネル
		pnlPaymentDivision = new TPanel();
		pnlPaymentDivision.setLayout(new GridBagLayout());
		pnlPaymentDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setMaximumSize(new Dimension(220, 50));
		pnlPaymentDivision.setMinimumSize(new Dimension(220, 50));
		pnlPaymentDivision.setPreferredSize(new Dimension(220, 50));

		// Radio Button Group - 支払対象区分
		btngrpPaymentDivision = new ButtonGroup();

		// Radio Button - 社員支払
		rdoEmployeePayment = new TRadioButton();
		rdoEmployeePayment.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpPaymentDivision.add(rdoEmployeePayment);
		rdoEmployeePayment.setSelected(true);
		rdoEmployeePayment.setLangMessageID("C01119");
		rdoEmployeePayment.setMargin(new Insets(0, 0, 0, 0));
		rdoEmployeePayment.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlPaymentDivision.add(rdoEmployeePayment, gridBagConstraints);

		// Radio Button - 社外支払
		rdoExternalPayment = new TRadioButton();
		rdoExternalPayment.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpPaymentDivision.add(rdoExternalPayment);
		rdoExternalPayment.setLangMessageID("C01124");
		rdoExternalPayment.setMargin(new Insets(0, 0, 0, 0));
		rdoExternalPayment.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 5, 0);
		pnlPaymentDivision.add(rdoExternalPayment, gridBagConstraints);

		// メインパネル3に支払対象区分パネル追加
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 110, 0, 0);
		pnlDetail3.add(pnlPaymentDivision, gridBagConstraints);

		// 支払内部コード
		ctrlPaymentInternalCode = new TLabelComboBox();
		ctrlPaymentInternalCode.setComboSize(215);
		ctrlPaymentInternalCode.setLabelSize(100);
		ctrlPaymentInternalCode.setLangMessageID("C01097");
		ctrlPaymentInternalCode.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(15, 5, 0, 0);
		pnlDetail3.add(ctrlPaymentInternalCode, gridBagConstraints);

		// 開始年月日
		dtBeginDate = new TLabelPopupCalendar();
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtBeginDate.setMaximumSize(new Dimension(100, 30));
		dtBeginDate.setMinimumSize(new Dimension(100, 30));
		dtBeginDate.setLabelSize(90);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(11);
		dtBeginDate.getCalendar().setMaximumDate(PanelAndDialogCtrlBase.maxInputDate);
		dtBeginDate.getCalendar().setMinimumDate(PanelAndDialogCtrlBase.minInputDate);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(20, 0, 0, 128);
		pnlDetail3.add(dtBeginDate, gridBagConstraints);

		// 終了年月日
		dtEndDate = new TLabelPopupCalendar();
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtEndDate.setMaximumSize(new Dimension(100, 30));
		dtEndDate.setMinimumSize(new Dimension(100, 30));
		dtEndDate.setLabelSize(90);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(12);
		dtEndDate.getCalendar().setMaximumDate(PanelAndDialogCtrlBase.maxInputDate);
		dtEndDate.getCalendar().setMinimumDate(PanelAndDialogCtrlBase.minInputDate);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(-20, 180, 0, 0);
		pnlDetail3.add(dtEndDate, gridBagConstraints);

		// メインパネル3 配置
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(15, 0, 0, 290);
		getContentPane().add(pnlDetail3, gridBagConstraints);

		pack();
	}

	/**
	 * 科目サイズ初期化
	 */
	public void setItemSize() {

		this.setMaximumSize(new Dimension(520, 80));
		this.setMinimumSize(new Dimension(520, 80));
		this.setPreferredSize(new Dimension(520, 80));

		GridBagConstraints gridBagConstraints;

		ctrlItemUnit.getTBasePanel().setLayout(new GridBagLayout());
		ctrlItemUnit.getTBasePanel().setMaximumSize(new Dimension(520, 80));
		ctrlItemUnit.getTBasePanel().setMinimumSize(new Dimension(520, 80));
		ctrlItemUnit.getTBasePanel().setPreferredSize(new Dimension(520, 80));

		// 科目
		ctrlItemUnit.getTItemField().setButtonSize(85);
		ctrlItemUnit.getTItemField().setFieldSize(80);
		ctrlItemUnit.getTItemField().setNoticeSize(221);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(10, 0, 0, 82);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTItemField(), gridBagConstraints);

		// 補助科目
		ctrlItemUnit.getTSubItemField().setButtonSize(85);
		ctrlItemUnit.getTSubItemField().setFieldSize(80);
		ctrlItemUnit.getTSubItemField().setNoticeSize(221);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 82);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTSubItemField(), gridBagConstraints);

		// 内訳科目
		ctrlItemUnit.getTBreakDownItemField().setButtonSize(85);
		ctrlItemUnit.getTBreakDownItemField().setFieldSize(80);
		ctrlItemUnit.getTBreakDownItemField().setNoticeSize(221);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 82);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTBreakDownItemField(), gridBagConstraints);
	}

	/** ボタンパネル */
	protected TPanel pnlButton;

	/** メインパネル1 */
	protected TPanel pnlDetail1;

	/** メインパネル2 */
	protected TPanel pnlDetail2;

	/** メインパネル3 */
	protected TPanel pnlDetail3;

	/** 取消ボタン */
	protected TButton btnReturn;

	/** 確定ボタン */
	protected TImageButton btnSettle;

	/** 支払方法コード */
	protected TLabelField ctrlPaymentMethodCode;

	/** 支払方法名称 */
	protected TLabelField ctrlPaymentMethodName;

	/** 支払方法検索名称 */
	protected TLabelField ctrlPaymentMethodNameForSearch;

	/** 科目 */
	protected TAccountItemUnit ctrlItemUnit;

	/** 計上部門 */
	protected TButtonField ctrlAppropriateDepartment;

	/** 支払対象区分 パネル */
	protected TPanel pnlPaymentDivision;

	/** 支払対象区分 Button Group */
	protected ButtonGroup btngrpPaymentDivision;

	/** 支払対象区分 Button 社員支払 */
	protected TRadioButton rdoEmployeePayment;

	/** 支払対象区分 Button 社外支払 */
	protected TRadioButton rdoExternalPayment;

	/** 支払内部コード */
	protected TLabelComboBox ctrlPaymentInternalCode;

	/** 開始年月日 */
	protected TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	protected TLabelPopupCalendar dtEndDate;

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	protected TLabelField createForSearchCtrl() {
		return new TLabelField();
	}
}