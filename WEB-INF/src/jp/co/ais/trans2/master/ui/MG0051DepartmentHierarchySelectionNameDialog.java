package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 部門階層名称設定マスタの編集画面
 */
public class MG0051DepartmentHierarchySelectionNameDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 組織名称 */
	public TLabelField ctrlOrganizationName;

	/** レベル0名称 */
	public TLabelField ctrlLevel0;

	/** レベル1名称 */
	public TLabelField ctrlLevel1;

	/** レベル2名称 */
	public TLabelField ctrlLevel2;

	/** レベル3名称 */
	public TLabelField ctrlLevel3;

	/** レベル4名称 */
	public TLabelField ctrlLevel4;

	/** レベル5名称 */
	public TLabelField ctrlLevel5;

	/** レベル6名称 */
	public TLabelField ctrlLevel6;

	/** レベル7名称 */
	public TLabelField ctrlLevel7;

	/** レベル8名称 */
	public TLabelField ctrlLevel8;

	/** レベル9名称 */
	public TLabelField ctrlLevel9;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0051DepartmentHierarchySelectionNameDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlOrganizationName = new TLabelField();
		ctrlLevel0 = new TLabelField();
		ctrlLevel1 = new TLabelField();
		ctrlLevel2 = new TLabelField();
		ctrlLevel3 = new TLabelField();
		ctrlLevel4 = new TLabelField();
		ctrlLevel5 = new TLabelField();
		ctrlLevel6 = new TLabelField();
		ctrlLevel7 = new TLabelField();
		ctrlLevel8 = new TLabelField();
		ctrlLevel9 = new TLabelField();

	}

	@Override
	public void allocateComponents() {
		setSize(550, 380);

		// 確定ボタン
		int x = 330 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 330;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		int y = 20;

		// 組織名称
		ctrlOrganizationName.setLabelSize(110);
		ctrlOrganizationName.setFieldSize(200);
		ctrlOrganizationName.setSize(320, 20);
		ctrlOrganizationName.setLocation(0, y);
		ctrlOrganizationName.setLangMessageID("C11967");
		ctrlOrganizationName.setMaxLength(20);
		pnlBody.add(ctrlOrganizationName);

		// レベル0名称
		ctrlLevel0.setLabelSize(110);
		ctrlLevel0.setFieldSize(200);
		ctrlLevel0.setSize(320, 20);
		ctrlLevel0.setLocation(0, y += 40);
		ctrlLevel0.setLangMessageID("C11969");
		ctrlLevel0.setMaxLength(20);
		pnlBody.add(ctrlLevel0);

		// レベル1名称
		ctrlLevel1.setLabelSize(110);
		ctrlLevel1.setFieldSize(200);
		ctrlLevel1.setSize(320, 20);
		ctrlLevel1.setLocation(0, y += 20);
		ctrlLevel1.setLangMessageID("C11970");
		ctrlLevel1.setMaxLength(20);
		pnlBody.add(ctrlLevel1);

		// レベル2名称
		ctrlLevel2.setLabelSize(110);
		ctrlLevel2.setFieldSize(200);
		ctrlLevel2.setSize(320, 20);
		ctrlLevel2.setLocation(0, y += 20);
		ctrlLevel2.setLangMessageID("C11971");
		ctrlLevel2.setMaxLength(20);
		pnlBody.add(ctrlLevel2);

		// レベル3名称
		ctrlLevel3.setLabelSize(110);
		ctrlLevel3.setFieldSize(200);
		ctrlLevel3.setSize(320, 20);
		ctrlLevel3.setLocation(0, y += 20);
		ctrlLevel3.setLangMessageID("C11972");
		ctrlLevel3.setMaxLength(20);
		pnlBody.add(ctrlLevel3);

		// レベル4名称
		ctrlLevel4.setLabelSize(110);
		ctrlLevel4.setFieldSize(200);
		ctrlLevel4.setSize(320, 20);
		ctrlLevel4.setLocation(0, y += 20);
		ctrlLevel4.setLangMessageID("C11973");
		ctrlLevel4.setMaxLength(20);
		pnlBody.add(ctrlLevel4);

		// レベル5名称
		ctrlLevel5.setLabelSize(110);
		ctrlLevel5.setFieldSize(200);
		ctrlLevel5.setSize(320, 20);
		ctrlLevel5.setLocation(0, y += 20);
		ctrlLevel5.setLangMessageID("C11974");
		ctrlLevel5.setMaxLength(20);
		pnlBody.add(ctrlLevel5);

		// レベル6名称
		ctrlLevel6.setLabelSize(110);
		ctrlLevel6.setFieldSize(200);
		ctrlLevel6.setSize(320, 20);
		ctrlLevel6.setLocation(0, y += 20);
		ctrlLevel6.setLangMessageID("C11975");
		ctrlLevel6.setMaxLength(20);
		pnlBody.add(ctrlLevel6);

		// レベル7名称
		ctrlLevel7.setLabelSize(110);
		ctrlLevel7.setFieldSize(200);
		ctrlLevel7.setSize(320, 20);
		ctrlLevel7.setLocation(0, y += 20);
		ctrlLevel7.setLangMessageID("C11976");
		ctrlLevel7.setMaxLength(20);
		pnlBody.add(ctrlLevel7);

		// レベル8名称
		ctrlLevel8.setLabelSize(110);
		ctrlLevel8.setFieldSize(200);
		ctrlLevel8.setSize(320, 20);
		ctrlLevel8.setLocation(0, y += 20);
		ctrlLevel8.setLangMessageID("C11977");
		ctrlLevel8.setMaxLength(20);
		pnlBody.add(ctrlLevel8);

		// レベル9名称
		ctrlLevel9.setLabelSize(110);
		ctrlLevel9.setFieldSize(200);
		ctrlLevel9.setSize(320, 20);
		ctrlLevel9.setLocation(0, y += 20);
		ctrlLevel9.setLangMessageID("C11978");
		ctrlLevel9.setMaxLength(20);
		pnlBody.add(ctrlLevel9);
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlOrganizationName.setTabControlNo(i++);
		ctrlLevel0.setTabControlNo(i++);
		ctrlLevel1.setTabControlNo(i++);
		ctrlLevel2.setTabControlNo(i++);
		ctrlLevel3.setTabControlNo(i++);
		ctrlLevel4.setTabControlNo(i++);
		ctrlLevel5.setTabControlNo(i++);
		ctrlLevel6.setTabControlNo(i++);
		ctrlLevel7.setTabControlNo(i++);
		ctrlLevel8.setTabControlNo(i++);
		ctrlLevel9.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}