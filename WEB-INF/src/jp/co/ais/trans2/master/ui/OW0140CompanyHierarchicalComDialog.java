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
 * ��ЊK�w�}�X�^�̕ҏW���
 */
public class OW0140CompanyHierarchicalComDialog extends TDialog {

	/** �w����� */
	protected OW0140CompanyHierarchicalMasterPanel mainView = null;

	/** ��Ќ������ */
	protected OW0140CompanyHierarchicalComDialog editComView = null;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �g�D�R�[�h */
	public TLabelField ctrlOrganizationCode;

	/** ��Ѓt�B�[���h */
	public TCompanyReference ctrlCompanyReferenceUP;

	/** ��Ѓt�B�[���h */
	public TCompanyReference ctrlCompanyReferenceLow;

	/** �N�������𔻒� */
	public int flag = -1;

	/** ��Д͈͌��� */
	public TCompanyReferenceRange companyRange;

	/** ��ʉ�Ѓ{�^�� */
	public TButtonField btnUpperCompany;

	/** ���ʉ�Ѓ{�^�� */
	public TButtonField btnLowerCompany;

	/**
	 * �R���X�g���N�^.
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

		// �m��{�^��
		int a = 330 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(a + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		a = 330;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(a + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// ��ʉ��
		ctrlCompanyReferenceUP.setLocation(45, 20);
		ctrlCompanyReferenceUP.btn.setLangMessageID("C01487");
		pnlBody.add(ctrlCompanyReferenceUP);

		// ���ʉ��
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