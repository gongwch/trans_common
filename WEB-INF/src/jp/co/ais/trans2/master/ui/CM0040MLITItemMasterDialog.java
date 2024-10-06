package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 輸送実績アイテムマスタ編集画面
 * 
 * @author AIS
 */
public class CM0040MLITItemMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** コード */
	public TLabelField ctrlItemCode;

	/** 名称 */
	public TLabelField ctrlItemName;

	/** Remark */
	public TLabelField ctrlRemark;

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0040MLITItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();

		ctrlItemCode = new TLabelField();
		ctrlItemName = new TLabelField();
		ctrlRemark = new TLabelField();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setSize(800, 200);

		int x = getWidth() - 300;
		int y = HEADER_Y;

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, y);

		pnlHeader.add(btnSettle);

		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;

		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, y);
		btnClose.setForClose(true);
		pnlHeader.add(btnClose);

		// 明細
		pnlBody.setLayout(null);

		x = 10;
		y = COMPONENT_MARGIN_Y + 10;

		// コード
		ctrlItemCode.setLabelSize(90);
		ctrlItemCode.setFieldSize(80);
		ctrlItemCode.setLangMessageID("CM0016"); // コード
		ctrlItemCode.setLocation(x, y);
		pnlBody.add(ctrlItemCode);

		y += 25;

		// 名称
		ctrlItemName.setLabelSize(90);
		ctrlItemName.setFieldSize(650);
		ctrlItemName.setLangMessageID("CM0017"); // 名称
		ctrlItemName.setLocation(x, y);
		pnlBody.add(ctrlItemName);

		y += 25;

		// Remark
		ctrlRemark.setLabelSize(90);
		ctrlRemark.setFieldSize(650);
		ctrlRemark.setLangMessageID("CM0015"); // Remark
		ctrlRemark.setLocation(x, y);
		pnlBody.add(ctrlRemark);

		// 基本制御
		controlSettings();
	}

	/**
	 * 制御
	 */
	public void controlSettings() {
		ctrlItemCode.setMaxLength(10);
		ctrlItemCode.setImeMode(false);

		ctrlItemName.setMaxLength(40);
		ctrlRemark.setMaxLength(200);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlItemCode.setTabControlNo(i++);
		ctrlItemName.setTabControlNo(i++);
		ctrlRemark.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
