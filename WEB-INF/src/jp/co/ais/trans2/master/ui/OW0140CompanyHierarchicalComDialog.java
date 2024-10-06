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
 * 会社階層マスタの編集画面
 */
public class OW0140CompanyHierarchicalComDialog extends TDialog {

	/** 指示画面 */
	protected OW0140CompanyHierarchicalMasterPanel mainView = null;

	/** 会社検索画面 */
	protected OW0140CompanyHierarchicalComDialog editComView = null;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 組織コード */
	public TLabelField ctrlOrganizationCode;

	/** 会社フィールド */
	public TCompanyReference ctrlCompanyReferenceUP;

	/** 会社フィールド */
	public TCompanyReference ctrlCompanyReferenceLow;

	/** 起動処理を判定 */
	public int flag = -1;

	/** 会社範囲検索 */
	public TCompanyReferenceRange companyRange;

	/** 上位会社ボタン */
	public TButtonField btnUpperCompany;

	/** 下位会社ボタン */
	public TButtonField btnLowerCompany;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public OW0140CompanyHierarchicalComDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);

	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlOrganizationCode = new TLabelField();
		ctrlCompanyReferenceUP = new TCompanyReference();
		ctrlCompanyReferenceLow = new TCompanyReference();

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

		// 上位会社
		ctrlCompanyReferenceUP.setLocation(45, 20);
		ctrlCompanyReferenceUP.btn.setLangMessageID("C01487");
		pnlBody.add(ctrlCompanyReferenceUP);

		// 下位会社
		ctrlCompanyReferenceLow.setLocation(45, 40);
		ctrlCompanyReferenceLow.btn.setLangMessageID("C01488");
		pnlBody.add(ctrlCompanyReferenceLow);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlOrganizationCode.setTabControlNo(i++);
		ctrlCompanyReferenceUP.setTabControlNo(i++);
		ctrlCompanyReferenceLow.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}