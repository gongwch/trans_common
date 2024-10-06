package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 通貨レートマスタの編集画面
 * 
 * @author AIS
 */
public class MG0310RateMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -6136083150579331779L;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** レート区分 */
	public TLabelComboBox cboRateType;

	/** 通貨 */
	public TCurrencyReference ctrlCurrency;

	/** 適用開始日付 */
	public TLabelPopupCalendar dtBeginDate;

	/** レート */
	public TLabelNumericField ctrlRate;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0310RateMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		cboRateType = new TLabelComboBox();
		ctrlCurrency = new TCurrencyReference();
		dtBeginDate = new TLabelPopupCalendar();
		ctrlRate = new TLabelNumericField();
	}

	@Override
	public void allocateComponents() {

		setSize(400, 200);

		// 確定ボタン
		int x = 160 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 270;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// レート区分
		cboRateType.setLabelSize(90);
		cboRateType.setComboSize(200);
		cboRateType.setSize(295, 20);
		cboRateType.setLocation(10, 10);
		cboRateType.setLangMessageID("C00883");
		cboRateType.getComboBox().addTextValueItem(0, getShortWord("C11167"));// 社定レート
		cboRateType.getComboBox().addTextValueItem(1, getShortWord("C02820"));// 決算日レート
		cboRateType.getComboBox().addTextValueItem(2, getShortWord("C11216"));// 社定レート（機能通貨）
		cboRateType.getComboBox().addTextValueItem(3, getShortWord("C11217"));// 決算日レート（機能通貨）
		pnlBody.add(cboRateType);

		// 通貨
		ctrlCurrency.setLocation(25, 35);
		pnlBody.add(ctrlCurrency);

		// 適用開始日
		dtBeginDate.setLabelSize(90);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(0, 60);
		dtBeginDate.setLangMessageID("C03741");
		pnlBody.add(dtBeginDate);

		// レート
		ctrlRate.setLabelSize(90);
		ctrlRate.setFieldSize(100);
		ctrlRate.setSize(195, 20);
		ctrlRate.setLocation(10, 85);
		ctrlRate.setPositiveOnly(true);
		ctrlRate.setDecimalPoint(4);
		ctrlRate.setNumericFormat("#,##0.0000");
		ctrlRate.setLabelText(getWord("C00556"));
		pnlBody.add(ctrlRate);
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		cboRateType.setTabControlNo(i++);
		ctrlCurrency.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		ctrlRate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
