package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 船マスタの編集画面
 * 
 * @author AIS
 */
public class CM0010VesselMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** タブ */
	public TTabbedPane tab;

	/** 燃料管理タブ */
	public TPanel pnlFuel;

	/** 船情報パネル */
	public TPanel pnlVessel;

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

	/** 貯蔵品部門コード */
	public TLabelField ctrlStock;

	/** 貯蔵品部門コード */
	public TDepartmentReference ctrlStockReference;

	/** 燃料費部門コード */
	public TLabelField ctrlFuel;

	/** 燃料費部門コード */
	public TDepartmentReference ctrlFuelReference;

	/** 燃料管理タブ */
	public TScrollPane spFuel;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0010VesselMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		tab = new TTabbedPane();
		pnlFuel = new TPanel();
		pnlVessel = new TPanel();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNames = new TLabelField();
		ctrlNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlStock = new TLabelField();
		ctrlStockReference = new TDepartmentReference();
		ctrlFuel = new TLabelField();
		ctrlFuelReference = new TDepartmentReference();
		spFuel = new TScrollPane(pnlFuel);

	}

	@Override
	public void allocateComponents() {

		setSize(600, 400);

		// 確定ボタン
		btnSettle.setLangMessageID("C01019"); // 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(345, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setLangMessageID("C01747"); // 戻る
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(460, HEADER_Y);
		btnClose.setForClose(true);
		pnlHeader.add(btnClose);

		pnlBody.add(pnlVessel);

		// 燃料管理
		pnlFuel.setLayout(null);
		TGuiUtil.setComponentSize(pnlFuel, new Dimension(500, 100));
		tab.addTab(getWord("C11783"), spFuel); // 燃料管理

		// 会社情報
		pnlVessel.setLayout(null);
		TGuiUtil.setComponentSize(pnlVessel, new Dimension(720, 150));

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlBody.add(pnlVessel, gc);
		TGuiUtil.setComponentSize(tab, new Dimension(720, 530));

		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(tab, gc);

		// 船コード
		ctrlCode.setLabelSize(110);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(190, 20);
		ctrlCode.setLocation(10, 0);
		ctrlCode.setLabelText(getWord("C11758")); // 船コード
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlVessel.add(ctrlCode);
		pnlVessel.setBackground(Color.red);

		// 名称
		ctrlName.setLabelSize(110);
		ctrlName.setFieldSize(400);
		ctrlName.setSize(515, 20);
		ctrlName.setLocation(10, 25);
		ctrlName.setLabelText(getWord("C11773")); // 船名称
		ctrlName.setMaxLength(40);
		pnlVessel.add(ctrlName);

		// 略称
		ctrlNames.setLabelSize(110);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(265, 20);
		ctrlNames.setLocation(10, 50);
		ctrlNames.setLabelText(getWord("C11759")); // 船略称
		ctrlNames.setMaxLength(20);
		pnlVessel.add(ctrlNames);

		// 検索名称
		ctrlNamek.setLabelSize(110);
		ctrlNamek.setFieldSize(400);
		ctrlNamek.setSize(515, 20);
		ctrlNamek.setLocation(10, 75);
		ctrlNamek.setLabelText(getWord("C11774")); // 船検索名称
		ctrlNamek.setMaxLength(40);
		pnlVessel.add(ctrlNamek);

		// 開始年月日
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 100);
		dtBeginDate.setLangMessageID("C00055");
		pnlVessel.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 125);
		dtEndDate.setLangMessageID("C00261");
		pnlVessel.add(dtEndDate);

		// 貯蔵品部門コード
		ctrlStock.setLabelSize(120);
		ctrlStock.setFieldSize(0);
		ctrlStock.setSize(120, 20);
		ctrlStock.setLocation(0, 20);
		ctrlStock.setLangMessageID("C11775");
		pnlFuel.add(ctrlStock);

		// 貯蔵品部門コード
		ctrlStockReference.setLocation(130, 20);
		pnlFuel.add(ctrlStockReference);

		// 燃料費部門コード
		ctrlFuel.setLabelSize(120);
		ctrlFuel.setFieldSize(0);
		ctrlFuel.setSize(120, 20);
		ctrlFuel.setLocation(0, 45);
		ctrlFuel.setLangMessageID("C11777");
		pnlFuel.add(ctrlFuel);

		// 燃料費部門コード
		ctrlFuelReference.setLocation(130, 45);
		pnlFuel.add(ctrlFuelReference);

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
		tab.setTabControlNo(i++);
		ctrlStockReference.setTabControlNo(i++);
		ctrlFuelReference.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
