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
 * ����K�w�}�X�^�̕ҏW���
 */
public class MG0051DepartmentHierarchySelectionDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �g�D�R�[�h */
	public TLabelField ctrlOrganizationCode;

	/** �g�D���� */
	public TLabelField ctrlOrganizationName;

	/** ����t�B�[���h */
	public TDepartmentReference ctrlDepartmentReference;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0051DepartmentHierarchySelectionDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlOrganizationCode = new TLabelField();
		ctrlOrganizationName = new TLabelField();
		ctrlDepartmentReference = new TDepartmentReference();
	}

	@Override
	public void allocateComponents() {
		setSize(550, 180);

		// �m��{�^��
		int x = 330 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 330;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// �g�D�R�[�h
		ctrlOrganizationCode.setLabelSize(110);
		ctrlOrganizationCode.setFieldSize(100);
		ctrlOrganizationCode.setSize(215, 20);
		ctrlOrganizationCode.setLocation(0, 10);
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setMaxLength(5);
		ctrlOrganizationCode.setImeMode(false);
		ctrlOrganizationCode.setAllowedSpace(false);
		pnlBody.add(ctrlOrganizationCode);

		// �g�D����
		ctrlOrganizationName.setLabelSize(110);
		ctrlOrganizationName.setFieldSize(200);
		ctrlOrganizationName.setSize(315, 20);
		ctrlOrganizationName.setLocation(165, 10);
		ctrlOrganizationName.setLangMessageID("C11967");
		ctrlOrganizationName.setMaxLength(20);
		pnlBody.add(ctrlOrganizationName);

		// ���x���O
		ctrlDepartmentReference.setLocation(35, 40);
		ctrlDepartmentReference.btn.setLangMessageID("C00722");
		ctrlDepartmentReference.getSearchCondition().setSumDepartment(true);
		pnlBody.add(ctrlDepartmentReference);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlOrganizationCode.setTabControlNo(i++);
		ctrlOrganizationName.setTabControlNo(i++);
		ctrlDepartmentReference.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}