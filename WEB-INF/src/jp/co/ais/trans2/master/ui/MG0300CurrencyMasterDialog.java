package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 通貨マスタの編集画面
 */
public class MG0300CurrencyMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 通貨コード */
	public TLabelField ctrlCurrencyCode;

	/** 通貨名称 */
	public TLabelField ctrlCurrencyName;

	/** 通貨略称 */
	public TLabelField ctrlCurrencyNames;

	/** 通貨検索名称 */
	public TLabelField ctrlCurrencyNamek;

	/** レート係数 */
	public TLabelNumericField ctrlCurrencyRate;

	/** 小数点以下桁数 */
	public TLabelNumericField ctrlCurrencyDecimalPoint;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0300CurrencyMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCurrencyCode = new TLabelField();
		ctrlCurrencyName = new TLabelField();
		ctrlCurrencyNames = new TLabelField();
		ctrlCurrencyNamek = new TLabelField();
		ctrlCurrencyRate = new TLabelNumericField();
		ctrlCurrencyDecimalPoint = new TLabelNumericField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

	}

	@Override
	public void allocateComponents() {
		setSize(650, 280);

		// 確定ボタン
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// 通貨コード
		ctrlCurrencyCode.setLabelSize(110);
		ctrlCurrencyCode.setFieldSize(40);
		ctrlCurrencyCode.setSize(215, 20);
		ctrlCurrencyCode.setLocation(2, 10);
		ctrlCurrencyCode.setLangMessageID("C00665");
		ctrlCurrencyCode.setMaxLength(3);
		ctrlCurrencyCode.setImeMode(false);
		ctrlCurrencyCode.setAllowedSpace(false);
		pnlBody.add(ctrlCurrencyCode);

		// 通貨名称
		ctrlCurrencyName.setLabelSize(90);
		ctrlCurrencyName.setFieldSize(420);
		ctrlCurrencyName.setSize(600, 20);
		ctrlCurrencyName.setLocation(10, 35);
		ctrlCurrencyName.setLangMessageID("C00880");
		ctrlCurrencyName.setMaxLength(40);
		pnlBody.add(ctrlCurrencyName);

		// 通貨略称
		ctrlCurrencyNames.setLabelSize(110);
		ctrlCurrencyNames.setFieldSize(220);
		ctrlCurrencyNames.setSize(500, 20);
		ctrlCurrencyNames.setLocation(-50, 60);
		ctrlCurrencyNames.setLangMessageID("C00881");
		ctrlCurrencyNames.setMaxLength(20);
		pnlBody.add(ctrlCurrencyNames);

		// 通貨検索名称
		ctrlCurrencyNamek.setLabelSize(110);
		ctrlCurrencyNamek.setFieldSize(220);
		ctrlCurrencyNamek.setSize(500, 20);
		ctrlCurrencyNamek.setLocation(-50, 85);
		ctrlCurrencyNamek.setLangMessageID("C00882");
		ctrlCurrencyNamek.setMaxLength(20);
		pnlBody.add(ctrlCurrencyNamek);

		// レート係数
		ctrlCurrencyRate.setLabelSize(110);
		ctrlCurrencyRate.setFieldSize(25);
		ctrlCurrencyRate.setSize(190, 20);
		ctrlCurrencyRate.setLocation(7, 110);
		ctrlCurrencyRate.setLangMessageID("C00896");
		ctrlCurrencyRate.setMaxLength(1);
		pnlBody.add(ctrlCurrencyRate);

		// 小数点以下桁数
		ctrlCurrencyDecimalPoint.setLabelSize(200);
		ctrlCurrencyDecimalPoint.setFieldSize(25);
		ctrlCurrencyDecimalPoint.setSize(280, 20);
		ctrlCurrencyDecimalPoint.setLocation(160, 110);
		ctrlCurrencyDecimalPoint.setLangMessageID("C00884");
		ctrlCurrencyDecimalPoint.setMaxLength(1);
		pnlBody.add(ctrlCurrencyDecimalPoint);

		// 開始年月日
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(33, 135);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(275, 135);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlCurrencyCode.setTabControlNo(i++);
		ctrlCurrencyName.setTabControlNo(i++);
		ctrlCurrencyNames.setTabControlNo(i++);
		ctrlCurrencyNamek.setTabControlNo(i++);
		ctrlCurrencyRate.setTabControlNo(i++);
		ctrlCurrencyDecimalPoint.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}