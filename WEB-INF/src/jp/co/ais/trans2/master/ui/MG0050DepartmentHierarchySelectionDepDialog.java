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
 * 部門マスタの編集画面
 */
public class MG0050DepartmentHierarchySelectionDepDialog extends TDialog {

	/** 指示画面 */
	protected MG0050DepartmentHierarchySelectionMasterPanel mainView = null;

	/** 部門検索画面 */
	protected MG0050DepartmentHierarchySelectionDepDialog editDepView = null;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 組織コード */
	public TLabelField ctrlOrganizationCode;

	/** 部門フィールド */
	public TDepartmentReference ctrlDepartmentReferenceUP;

	/** 部門フィールド */
	public TDepartmentReference ctrlDepartmentReferenceLow;

	/** 起動処理を判定 */
	public int flag = -1;

	/** 部門範囲検索 */
	public TDepartmentReferenceRange departmentRange;

	/** 上位部門ボタン */
	public TButtonField btnUpperDepartment;

	/** 下位部門ボタン */
	public TButtonField btnLowerDepartment;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0050DepartmentHierarchySelectionDepDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);

	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlOrganizationCode = new TLabelField();
		ctrlDepartmentReferenceUP = new TDepartmentReference();
		ctrlDepartmentReferenceLow = new TDepartmentReference();

	}

	@Override
	public void allocateComponents() {

		setSize(550, 180);

		// 確定ボタン
		int a = 330 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(a + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		a = 330;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(a + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// 上位部門
		ctrlDepartmentReferenceUP.setLocation(45, 20);
		ctrlDepartmentReferenceUP.btn.setLangMessageID("C00719");
		ctrlDepartmentReferenceUP.getSearchCondition().setSumDepartment(true);
		pnlBody.add(ctrlDepartmentReferenceUP);

		// 下位部門
		ctrlDepartmentReferenceLow.setLocation(45, 40);
		ctrlDepartmentReferenceLow.btn.setLangMessageID("C00720");
		ctrlDepartmentReferenceLow.getSearchCondition().setSumDepartment(true);
		pnlBody.add(ctrlDepartmentReferenceLow);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlOrganizationCode.setTabControlNo(i++);
		ctrlDepartmentReferenceUP.setTabControlNo(i++);
		ctrlDepartmentReferenceLow.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}