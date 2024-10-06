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
 * 科目制御区分マスタの編集画面
 */
public class MG0431AutomaticJournalizingItemMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 科目制御区分 */
	public TLabelNumericField ctrlAutoJnlItemCode;

	/** 科目制御区分名称 */
	public TLabelField ctrlAutoJnlItemName;

	/** 部門コンポーネント */
	public TDepartmentReference ctrlDepartment;

	/** 科目・補助・内訳コンポーネント */
	public TItemGroup ctrlTItemGroup;

	/** 貸借区分 */
	public TLabelField ctrlDCFiled;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	MG0431AutomaticJournalizingItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlAutoJnlItemCode = new TLabelNumericField();
		ctrlAutoJnlItemName = new TLabelField();
		ctrlDepartment = new TDepartmentReference();
		ctrlTItemGroup = new TItemGroup();
		ctrlDCFiled = new TLabelNumericField();
	}

	@Override
	public void allocateComponents() {
		setSize(600, 250);

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

		// 科目制御区分コード
		ctrlAutoJnlItemCode.setLabelSize(80);
		ctrlAutoJnlItemCode.setFieldSize(50);
		ctrlAutoJnlItemCode.setSize(215, 20);
		ctrlAutoJnlItemCode.setLocation(0, 10);
		ctrlAutoJnlItemCode.setLangMessageID("C01008");
		ctrlAutoJnlItemCode.setMaxLength(2);
		ctrlAutoJnlItemCode.setAllowedSpace(false);
		pnlBody.add(ctrlAutoJnlItemCode);

		// 科目制御区分名称
		ctrlAutoJnlItemName.setLabelSize(110);
		ctrlAutoJnlItemName.setFieldSize(380);
		ctrlAutoJnlItemName.setSize(495, 20);
		ctrlAutoJnlItemName.setLocation(10, 35);
		ctrlAutoJnlItemName.setLangMessageID("C11885");
		ctrlAutoJnlItemName.setMaxLength(40);
		pnlBody.add(ctrlAutoJnlItemName);

		// 部門コンポーネント
		ctrlDepartment.setLocation(45, 60);
		pnlBody.add(ctrlDepartment);

		// 科目・補助・内訳コンポーネント
		ctrlTItemGroup.setLocation(45, 85);
		pnlBody.add(ctrlTItemGroup);

		// 貸借区分
		ctrlDCFiled.setLocation(10, 110);
		ctrlDCFiled.setMaxLength(1);
		ctrlDCFiled.setVisible(false);
		pnlBody.add(ctrlDCFiled);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlAutoJnlItemCode.setTabControlNo(i++);
		ctrlAutoJnlItemName.setTabControlNo(i++);
		ctrlDepartment.setTabControlNo(i++);
		ctrlTItemGroup.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}