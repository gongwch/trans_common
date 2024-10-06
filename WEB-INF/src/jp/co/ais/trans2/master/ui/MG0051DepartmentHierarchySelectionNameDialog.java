package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ����K�w���̐ݒ�}�X�^�̕ҏW���
 */
public class MG0051DepartmentHierarchySelectionNameDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �g�D���� */
	public TLabelField ctrlOrganizationName;

	/** ���x��0���� */
	public TLabelField ctrlLevel0;

	/** ���x��1���� */
	public TLabelField ctrlLevel1;

	/** ���x��2���� */
	public TLabelField ctrlLevel2;

	/** ���x��3���� */
	public TLabelField ctrlLevel3;

	/** ���x��4���� */
	public TLabelField ctrlLevel4;

	/** ���x��5���� */
	public TLabelField ctrlLevel5;

	/** ���x��6���� */
	public TLabelField ctrlLevel6;

	/** ���x��7���� */
	public TLabelField ctrlLevel7;

	/** ���x��8���� */
	public TLabelField ctrlLevel8;

	/** ���x��9���� */
	public TLabelField ctrlLevel9;

	/**
	 * �R���X�g���N�^.
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

		int y = 20;

		// �g�D����
		ctrlOrganizationName.setLabelSize(110);
		ctrlOrganizationName.setFieldSize(200);
		ctrlOrganizationName.setSize(320, 20);
		ctrlOrganizationName.setLocation(0, y);
		ctrlOrganizationName.setLangMessageID("C11967");
		ctrlOrganizationName.setMaxLength(20);
		pnlBody.add(ctrlOrganizationName);

		// ���x��0����
		ctrlLevel0.setLabelSize(110);
		ctrlLevel0.setFieldSize(200);
		ctrlLevel0.setSize(320, 20);
		ctrlLevel0.setLocation(0, y += 40);
		ctrlLevel0.setLangMessageID("C11969");
		ctrlLevel0.setMaxLength(20);
		pnlBody.add(ctrlLevel0);

		// ���x��1����
		ctrlLevel1.setLabelSize(110);
		ctrlLevel1.setFieldSize(200);
		ctrlLevel1.setSize(320, 20);
		ctrlLevel1.setLocation(0, y += 20);
		ctrlLevel1.setLangMessageID("C11970");
		ctrlLevel1.setMaxLength(20);
		pnlBody.add(ctrlLevel1);

		// ���x��2����
		ctrlLevel2.setLabelSize(110);
		ctrlLevel2.setFieldSize(200);
		ctrlLevel2.setSize(320, 20);
		ctrlLevel2.setLocation(0, y += 20);
		ctrlLevel2.setLangMessageID("C11971");
		ctrlLevel2.setMaxLength(20);
		pnlBody.add(ctrlLevel2);

		// ���x��3����
		ctrlLevel3.setLabelSize(110);
		ctrlLevel3.setFieldSize(200);
		ctrlLevel3.setSize(320, 20);
		ctrlLevel3.setLocation(0, y += 20);
		ctrlLevel3.setLangMessageID("C11972");
		ctrlLevel3.setMaxLength(20);
		pnlBody.add(ctrlLevel3);

		// ���x��4����
		ctrlLevel4.setLabelSize(110);
		ctrlLevel4.setFieldSize(200);
		ctrlLevel4.setSize(320, 20);
		ctrlLevel4.setLocation(0, y += 20);
		ctrlLevel4.setLangMessageID("C11973");
		ctrlLevel4.setMaxLength(20);
		pnlBody.add(ctrlLevel4);

		// ���x��5����
		ctrlLevel5.setLabelSize(110);
		ctrlLevel5.setFieldSize(200);
		ctrlLevel5.setSize(320, 20);
		ctrlLevel5.setLocation(0, y += 20);
		ctrlLevel5.setLangMessageID("C11974");
		ctrlLevel5.setMaxLength(20);
		pnlBody.add(ctrlLevel5);

		// ���x��6����
		ctrlLevel6.setLabelSize(110);
		ctrlLevel6.setFieldSize(200);
		ctrlLevel6.setSize(320, 20);
		ctrlLevel6.setLocation(0, y += 20);
		ctrlLevel6.setLangMessageID("C11975");
		ctrlLevel6.setMaxLength(20);
		pnlBody.add(ctrlLevel6);

		// ���x��7����
		ctrlLevel7.setLabelSize(110);
		ctrlLevel7.setFieldSize(200);
		ctrlLevel7.setSize(320, 20);
		ctrlLevel7.setLocation(0, y += 20);
		ctrlLevel7.setLangMessageID("C11976");
		ctrlLevel7.setMaxLength(20);
		pnlBody.add(ctrlLevel7);

		// ���x��8����
		ctrlLevel8.setLabelSize(110);
		ctrlLevel8.setFieldSize(200);
		ctrlLevel8.setSize(320, 20);
		ctrlLevel8.setLocation(0, y += 20);
		ctrlLevel8.setLangMessageID("C11977");
		ctrlLevel8.setMaxLength(20);
		pnlBody.add(ctrlLevel8);

		// ���x��9����
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