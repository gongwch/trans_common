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
 * 港マスタの編集画面
 * 
 * @author AIS
 */
public class CM0030PortMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** タブ */
	public TTabbedPane tab;

	/** 港情報設定タブ */
	public TPanel pnlBase;

	/** 港情報パネル */
	public TPanel pnlPort;

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
	public CM0030PortMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		tab = new TTabbedPane();
		pnlBase = new TPanel();
		pnlPort = new TPanel();
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
		tab.addTab(getWord("C00111"), pnlBase);// 港情報

		// 港情報
		pnlPort.setLayout(null);
		pnlPort.setBorder(TBorderFactory.createTitledBorder(getWord("C11785")));// 港情報設定
		pnlPort.setSize(750, 400);
		pnlPort.setLocation(10, 10);
		pnlBase.add(pnlPort);

		// 港コード
		ctrlCode.setLabelSize(120);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(200, 20);
		ctrlCode.setLocation(10, 20);
		ctrlCode.setLabelText(getWord("C00584"));
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlPort.add(ctrlCode);

		// 港名称
		ctrlName.setLabelSize(120);
		ctrlName.setFieldSize(300);
		ctrlName.setSize(425, 20);
		ctrlName.setLocation(10, 50);
		ctrlName.setLabelText(getWord("C00585"));
		ctrlName.setMaxLength(40);
		pnlPort.add(ctrlName);

		// 港略称
		ctrlNames.setLabelSize(120);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(275, 20);
		ctrlNames.setLocation(10, 80);
		ctrlNames.setLabelText(TModelUIUtil.getShortWord(("C00586")));
		ctrlNames.setMaxLength(20);
		pnlPort.add(ctrlNames);

		// 港検索名称
		ctrlNamek.setLabelSize(120);
		ctrlNamek.setFieldSize(300);
		ctrlNamek.setSize(425, 20);
		ctrlNamek.setLocation(10, 110);
		ctrlNamek.setLabelText(getWord("C00587"));
		ctrlNamek.setMaxLength(40);
		pnlPort.add(ctrlNamek);

		// 開始年月日
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 140);
		dtBeginDate.setLangMessageID("C00055");
		pnlPort.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 170);
		dtEndDate.setLangMessageID("C00261");
		pnlPort.add(dtEndDate);

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
