package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0120-RemarkMaster - 摘要マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MG0120RemarkMasterDialog extends TDialog {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 摘要区分パネル */
	public TPanel pnlTek;

	/** 摘要区分 ButtonGroup */
	public ButtonGroup bgTek;

	/** 伝票摘要 */
	public TRadioButton rdoSlipRemark;

	/** 行摘要 */
	public TRadioButton rdoSlipRowRemark;

	/** データ区分:コンボボックス */
	public TLabelComboBox ctrlDataKbn;

	/** 摘要コード */
	public TLabelField ctrlTekCode;

	/** 摘要名称 */
	public TLabelField ctrlTekName;

	/** 摘要検索略称 */
	public TLabelField ctrlTekNameK;

	/** 開始年月日 */
	public TLabelPopupCalendar ctrlStrDate;

	/** 終了年月日 */
	public TLabelPopupCalendar ctrlEndDate;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0120RemarkMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		pnlTek = new TPanel();
		rdoSlipRemark = new TRadioButton();
		rdoSlipRowRemark = new TRadioButton();
		ctrlDataKbn = new TLabelComboBox();
		ctrlTekCode = new TLabelField();
		ctrlTekName = new TLabelField();
		ctrlTekNameK = new TLabelField();
		ctrlStrDate = new TLabelPopupCalendar();
		ctrlEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 320);

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

		// 摘要区分
		pnlTek.setLangMessageID("C00568");
		pnlTek.setSize(250, 60);
		pnlTek.setLocation(80, 10);
		pnlBody.add(pnlTek);

		int x = 100;
		int y = 30;

		// 伝票摘要
		rdoSlipRemark.setLangMessageID("C00569");
		rdoSlipRemark.setSize(100, 30);
		rdoSlipRemark.setLocation(x, y);
		rdoSlipRemark.setSelected(true);
		rdoSlipRemark.setIndex(0);
		pnlBody.add(rdoSlipRemark);

		x += rdoSlipRemark.getWidth();

		// 行摘要
		rdoSlipRowRemark.setLangMessageID("C00119");
		rdoSlipRowRemark.setSize(100, 30);
		rdoSlipRowRemark.setLocation(x + 30, y);
		rdoSlipRowRemark.setIndex(1);
		pnlBody.add(rdoSlipRowRemark);

		bgTek = new ButtonGroup();
		bgTek.add(rdoSlipRemark);
		bgTek.add(rdoSlipRowRemark);

		/** コンボボックス */
		// データ区分
		ctrlDataKbn.setComboSize(220);
		ctrlDataKbn.setLabelSize(140);
		ctrlDataKbn.setLocation(20, 75);
		ctrlDataKbn.setLangMessageID("C00567");
		setDataKbnItem(ctrlDataKbn.getComboBox());
		pnlBody.add(ctrlDataKbn);

		// 摘要コード
		ctrlTekCode.setLabelSize(110);
		ctrlTekCode.setFieldSize(100);
		ctrlTekCode.setSize(215, 20);
		ctrlTekCode.setLocation(50, 100);
		ctrlTekCode.setLabelText(getWord("C00564"));
		ctrlTekCode.setMaxLength(10);
		ctrlTekCode.setImeMode(false);
		ctrlTekCode.setAllowedSpace(false);
		pnlBody.add(ctrlTekCode);

		// 摘要名称
		ctrlTekName.setLabelSize(110);
		ctrlTekName.setFieldSize(380);
		ctrlTekName.setSize(495, 20);
		ctrlTekName.setLocation(50, 125);
		ctrlTekName.setLabelText(getWord("C00565"));
		ctrlTekName.setMaxLength(80);
		pnlBody.add(ctrlTekName);

		// 摘要検索名称
		ctrlTekNameK.setLabelSize(110);
		ctrlTekNameK.setFieldSize(380);
		ctrlTekNameK.setSize(495, 20);
		ctrlTekNameK.setLocation(50, 150);
		ctrlTekNameK.setLabelText(getWord("C00566"));
		ctrlTekNameK.setMaxLength(40);
		pnlBody.add(ctrlTekNameK);

		// 開始年月日
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 20);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(50, 175);
		pnlBody.add(ctrlStrDate);

		// 終了年月日
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(50, 200);
		pnlBody.add(ctrlEndDate);
	}

	/**
	 * データ区分コンボボックスの値設定
	 * 
	 * @param comboBox
	 */
	protected void setDataKbnItem(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(DataDivision.INPUT, getWord(DataDivision.getDataDivisionName(DataDivision.INPUT)));
		comboBox.addTextValueItem(DataDivision.OUTPUT, getWord(DataDivision.getDataDivisionName(DataDivision.OUTPUT)));
		comboBox.addTextValueItem(DataDivision.TRANSFER,
			getWord(DataDivision.getDataDivisionName(DataDivision.TRANSFER)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE_DEL)));
		comboBox.addTextValueItem(DataDivision.LEDGER_IFRS,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_IFRS)));
		comboBox.addTextValueItem(DataDivision.LEDGER_SELF,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_SELF)));
		comboBox.addTextValueItem(DataDivision.EMP_KARI,
			getWord(DataDivision.getDataDivisionName(DataDivision.EMP_KARI)));
		comboBox
			.addTextValueItem(DataDivision.EXPENCE, getWord(DataDivision.getDataDivisionName(DataDivision.EXPENCE)));
		comboBox
			.addTextValueItem(DataDivision.ACCOUNT, getWord(DataDivision.getDataDivisionName(DataDivision.ACCOUNT)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_EMP,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_EMP)));
		comboBox.addTextValueItem(DataDivision.ABROAD, getWord(DataDivision.getDataDivisionName(DataDivision.ABROAD)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_COM,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_COM)));
		comboBox.addTextValueItem(DataDivision.CREDIT_SLIP,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_SLIP)));
		comboBox.addTextValueItem(DataDivision.CREDIT_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_DEL)));

	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;

		rdoSlipRemark.setTabControlNo(i++);
		rdoSlipRowRemark.setTabControlNo(i++);
		ctrlDataKbn.setTabControlNo(i++);
		ctrlTekCode.setTabControlNo(i++);
		ctrlTekName.setTabControlNo(i++);
		ctrlTekNameK.setTabControlNo(i++);
		ctrlStrDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}