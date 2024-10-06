package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 次航マスタの編集画面
 * 
 * @author AIS
 */
public class CM0020VoyageMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** タブ */
	public TTabbedPane tab;

	/** 次航情報設定タブ */
	public TPanel pnlBase;

	/** 次航情報パネル */
	public TPanel pnlVoyage;

	/** コード */
	public TLabelField ctrlCode;

	/** 名称 */
	public TLabelField ctrlName;

	/** 略称 */
	public TLabelField ctrlNames;

	/** 検索名称 */
	public TLabelField ctrlNamek;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0020VoyageMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		tab = new TTabbedPane();
		pnlBase = new TPanel();
		pnlVoyage = new TPanel();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNames = new TLabelField();
		ctrlNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

	}

	@Override
	public void allocateComponents() {

		setSize(520, 360);

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 10, 10);

		pnlBody.add(tab, gc);

		// 基本設定タブ
		pnlBase.setLayout(null);
		tab.addTab(getWord("C00111"), pnlBase);// 次航情報

		// 次航情報
		pnlVoyage.setLayout(null);
		pnlVoyage.setBorder(TBorderFactory.createTitledBorder(getWord("C11784")));// 次航情報設定
		pnlVoyage.setSize(750, 400);
		pnlVoyage.setLocation(10, 10);
		pnlBase.add(pnlVoyage);

		// 次航コード
		ctrlCode.setLabelSize(120);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(200, 20);
		ctrlCode.setLocation(10, 20);
		ctrlCode.setLabelText(getWord("C03003"));
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlVoyage.add(ctrlCode);

		// 次航名称
		ctrlName.setLabelSize(120);
		ctrlName.setFieldSize(300);
		ctrlName.setSize(425, 20);
		ctrlName.setLocation(10, 50);
		ctrlName.setLabelText(getWord("C11780"));
		ctrlName.setMaxLength(40);
		pnlVoyage.add(ctrlName);

		// 次航略称
		ctrlNames.setLabelSize(120);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(275, 20);
		ctrlNames.setLocation(10, 80);
		ctrlNames.setLabelText(TModelUIUtil.getShortWord(("C11781")));
		ctrlNames.setMaxLength(20);
		pnlVoyage.add(ctrlNames);

		// 次航検索名称
		ctrlNamek.setLabelSize(120);
		ctrlNamek.setFieldSize(300);
		ctrlNamek.setSize(425, 20);
		ctrlNamek.setLocation(10, 110);
		ctrlNamek.setLabelText(getWord("C11782"));
		ctrlNamek.setMaxLength(40);
		pnlVoyage.add(ctrlNamek);

		// 開始年月日
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 140);
		dtBeginDate.setLangMessageID("C00055");
		pnlVoyage.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 170);
		dtEndDate.setLangMessageID("C00261");
		pnlVoyage.add(dtEndDate);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNames.setTabControlNo(i++);
		ctrlNamek.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
