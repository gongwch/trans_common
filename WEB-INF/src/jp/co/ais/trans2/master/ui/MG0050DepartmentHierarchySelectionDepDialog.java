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
 * ����}�X�^�̕ҏW���
 */
public class MG0050DepartmentHierarchySelectionDepDialog extends TDialog {

	/** �w����� */
	protected MG0050DepartmentHierarchySelectionMasterPanel mainView = null;

	/** ���匟����� */
	protected MG0050DepartmentHierarchySelectionDepDialog editDepView = null;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �g�D�R�[�h */
	public TLabelField ctrlOrganizationCode;

	/** ����t�B�[���h */
	public TDepartmentReference ctrlDepartmentReferenceUP;

	/** ����t�B�[���h */
	public TDepartmentReference ctrlDepartmentReferenceLow;

	/** �N�������𔻒� */
	public int flag = -1;

	/** ����͈͌��� */
	public TDepartmentReferenceRange departmentRange;

	/** ��ʕ���{�^�� */
	public TButtonField btnUpperDepartment;

	/** ���ʕ���{�^�� */
	public TButtonField btnLowerDepartment;

	/**
	 * �R���X�g���N�^.
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

		// ��ʕ���
		ctrlDepartmentReferenceUP.setLocation(45, 20);
		ctrlDepartmentReferenceUP.btn.setLangMessageID("C00719");
		ctrlDepartmentReferenceUP.getSearchCondition().setSumDepartment(true);
		pnlBody.add(ctrlDepartmentReferenceUP);

		// ���ʕ���
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