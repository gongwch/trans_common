package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0130-ConsumptionTaxMaster - 消費税マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MG0130ConsumptionTaxMasterDialog extends TDialog {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 消費税コード */
	public TLabelField ctrlTaxCode;

	/** 消費税名称 */
	public TLabelField ctrlTaxName;

	/** 消費税略称 */
	public TLabelField ctrlTaxNameS;

	/** 消費税検索名称 */
	public TLabelField ctrlTaxNameK;

	/** 売上仕入区分:コンボボックス */
	public TLabelComboBox ctrlcboUsKbn;

	/** 消費税計算書 ButtonGroup */
	public ButtonGroup bgTaxKei;

	/** 消費税計算書パネル */
	public TPanel pnlTaxKei;

	/** 使用しない */
	public TRadioButton rdoDisUse;

	/** 使用する */
	public TRadioButton rdoUse;

	/** 出力順序 */
	public TLabelNumericField numOutputOrder;

	/** 消費税率 */
	public TLabelNumericField numConsumptionTaxRate;

	/** 開始年月日 */
	public TLabelPopupCalendar ctrlStrDate;

	/** 終了年月日 */
	public TLabelPopupCalendar ctrlEndDate;

	/** 助数詞 % */
	public TLabel percent;

	/** チェックボックス非適格請求書発行事業者 */
	public TCheckBox ctrlNoInvReg;

	/** 経過措置控除可能率(KEKA_SOTI_RATE) */
	public TLabelNumericField ctrlTransitMeasure;
	
	/** % */
	public TLabel lblTransitMeasure;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0130ConsumptionTaxMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlTaxCode = new TLabelField();
		ctrlTaxName = new TLabelField();
		ctrlTaxNameS = new TLabelField();
		ctrlTaxNameK = new TLabelField();
		ctrlcboUsKbn = new TLabelComboBox();
		pnlTaxKei = new TPanel();
		rdoDisUse = new TRadioButton();
		rdoUse = new TRadioButton();
		numOutputOrder = new TLabelNumericField();
		numConsumptionTaxRate = new TLabelNumericField();
		percent = new TLabel();
		ctrlStrDate = new TLabelPopupCalendar();
		ctrlEndDate = new TLabelPopupCalendar();
		ctrlNoInvReg = new TCheckBox();
		ctrlTransitMeasure = new TLabelNumericField();
		lblTransitMeasure = new TLabel();

	}

	@Override
	public void allocateComponents() {

		setSize(650, 450);

		// Header初期化
		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body初期化
		pnlBody.setLayout(null);

		// 消費税コード
		ctrlTaxCode.setLabelSize(110);
		ctrlTaxCode.setFieldSize(35);
		ctrlTaxCode.setSize(215, 20);
		ctrlTaxCode.setLocation(19, 20);
		ctrlTaxCode.setLabelText(getWord("C00573"));
		ctrlTaxCode.setMaxLength(2);
		ctrlTaxCode.setImeMode(false);
		ctrlTaxCode.setAllowedSpace(false);
		pnlBody.add(ctrlTaxCode);

		// 消費税名称
		ctrlTaxName.setLabelSize(110);
		ctrlTaxName.setFieldSize(380);
		ctrlTaxName.setSize(495, 20);
		ctrlTaxName.setLocation(50, 50);
		ctrlTaxName.setLabelText(getWord("C00774"));
		ctrlTaxName.setMaxLength(40);
		pnlBody.add(ctrlTaxName);

		// 消費税略称
		ctrlTaxNameS.setLabelSize(110);
		ctrlTaxNameS.setFieldSize(220);
		ctrlTaxNameS.setSize(495, 20);
		ctrlTaxNameS.setLocation(-30, 80);
		ctrlTaxNameS.setLabelText(getWord("C00775"));
		ctrlTaxNameS.setMaxLength(20);
		pnlBody.add(ctrlTaxNameS);

		// 消費税検索名称
		ctrlTaxNameK.setLabelSize(110);
		ctrlTaxNameK.setFieldSize(380);
		ctrlTaxNameK.setSize(495, 20);
		ctrlTaxNameK.setLocation(50, 110);
		ctrlTaxNameK.setLabelText(getWord("C00828"));
		ctrlTaxNameK.setMaxLength(40);
		pnlBody.add(ctrlTaxNameK);

		/** コンボボックス */
		/** リストボックス引用 */
		// 売上仕入区分
		ctrlcboUsKbn.setComboSize(220);
		ctrlcboUsKbn.setLabelSize(140);
		ctrlcboUsKbn.setLocation(20, 140);
		ctrlcboUsKbn.setLangMessageID("C01283");
		setUSKbnItem(ctrlcboUsKbn.getComboBox());
		pnlBody.add(ctrlcboUsKbn);

		// 消費税計算書（パネル）
		pnlTaxKei.setLangMessageID("C01176");
		pnlTaxKei.setSize(380, 55);
		pnlTaxKei.setLocation(165, 170);
		pnlBody.add(pnlTaxKei);

		int x = 190;
		int y = 190;

		// 使用しない（ラジオボタン）
		rdoDisUse.setLangMessageID("CLM0291");
		rdoDisUse.setSize(120, 30);
		rdoDisUse.setLocation(x, y);
		rdoDisUse.setSelected(true);
		rdoDisUse.setIndex(0);
		pnlBody.add(rdoDisUse);

		x += rdoDisUse.getWidth();

		// 使用する（ラジオボタン）
		rdoUse.setLangMessageID("C00281");
		rdoUse.setSize(120, 30);
		rdoUse.setLocation(x, y);
		rdoUse.setIndex(1);
		pnlBody.add(rdoUse);

		bgTaxKei = new ButtonGroup();
		bgTaxKei.add(rdoDisUse);
		bgTaxKei.add(rdoUse);

		// 出力順序
		numOutputOrder.setLabelSize(90);
		numOutputOrder.setFieldSize(20);
		numOutputOrder.setSize(450, 240);
		numOutputOrder.setLocation(210, 85);
		numOutputOrder.setPositiveOnly(true);
		numOutputOrder.setNumericFormat("###,###,##0");
		numOutputOrder.setLabelText(getWord("C00776"));
		numOutputOrder.setMaxLength(2);
		pnlBody.add(numOutputOrder);

		// 消費税率
		numConsumptionTaxRate.setLabelSize(90);
		numConsumptionTaxRate.setFieldSize(40);
		numConsumptionTaxRate.setSize(320, 320);
		numConsumptionTaxRate.setLocation(-20, 85);
		numConsumptionTaxRate.setPositiveOnly(true);
		numConsumptionTaxRate.setDecimalPoint(1);
		numConsumptionTaxRate.setNumericFormat("##,###,##0.0");
		numConsumptionTaxRate.setValue(("0.0"));
		numConsumptionTaxRate.setLabelText(getWord("C00777"));
		numConsumptionTaxRate.setMaxLength(3);
		pnlBody.add(numConsumptionTaxRate);

		// 助数詞 ％
		percent.setLocation(215, 165);
		percent.setSize(20, 160);
		percent.setLangMessageID("COW242");
		pnlBody.add(percent);

		// 開始年月日
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 170);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(52, 190);
		pnlBody.add(ctrlStrDate);

		// 終了年月日
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 170);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(52, 220);
		pnlBody.add(ctrlEndDate);

		// 終了年月日
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 170);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(52, 220);
		pnlBody.add(ctrlEndDate);

		x = ctrlTaxCode.getX() + ctrlTaxCode.getWidth() + 15;
		y = 240;

		// 非適格請求書発行事業者
		ctrlNoInvReg.setLangMessageID("C12197");
		ctrlNoInvReg.setSize(160, 15);
		ctrlNoInvReg.setLocation(x, y);
		pnlBody.add(ctrlNoInvReg);

		x = ctrlNoInvReg.getX();
		y = ctrlNoInvReg.getY() + ctrlNoInvReg.getHeight() + 10;

		// 経過措置控除可能率
		ctrlTransitMeasure.setLangMessageID("C12228"); // 経過措置控除可能率
		ctrlTransitMeasure.setPositiveOnly();
		ctrlTransitMeasure.setMinValue(BigDecimal.ZERO);
		ctrlTransitMeasure.setMaxValue(new BigDecimal(100));
		ctrlTransitMeasure.setLabelSize(175);
		ctrlTransitMeasure.setFieldSize(40);
		ctrlTransitMeasure.setLocation(x, y);
		pnlBody.add(ctrlTransitMeasure);

		x += ctrlTransitMeasure.getWidth() + 2;

		// %
		TGuiUtil.setComponentSize(lblTransitMeasure, 55, 20);
		lblTransitMeasure.setLangMessageID("C01335"); // %
		lblTransitMeasure.setLocation(x, y);
		pnlBody.add(lblTransitMeasure);

	}

	/**
	 * 売上仕入区分コンボボックスの値設定
	 * 
	 * @param comboBox
	 */
	protected void setUSKbnItem(TComboBox comboBox) {
		comboBox.addTextValueItem(TaxType.NOT, getWord(TaxType.getName(TaxType.NOT)));
		comboBox.addTextValueItem(TaxType.SALES, getWord(TaxType.getName(TaxType.SALES)));
		comboBox.addTextValueItem(TaxType.PURCHAESE, getWord(TaxType.getName(TaxType.PURCHAESE)));
	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;

		ctrlTaxCode.setTabControlNo(i++);
		ctrlTaxName.setTabControlNo(i++);
		ctrlTaxNameS.setTabControlNo(i++);
		ctrlTaxNameK.setTabControlNo(i++);
		ctrlcboUsKbn.setTabControlNo(i++);
		rdoDisUse.setTabControlNo(i++);
		rdoUse.setTabControlNo(i++);
		numOutputOrder.setTabControlNo(i++);
		numConsumptionTaxRate.setTabControlNo(i++);
		ctrlStrDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		ctrlNoInvReg.setTabControlNo(i++);
		ctrlTransitMeasure.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}