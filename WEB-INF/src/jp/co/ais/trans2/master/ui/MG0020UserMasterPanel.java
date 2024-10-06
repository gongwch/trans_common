package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���[�U�[�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0020UserMasterPanel extends TMainPanel {

	/** �V�K�{�^�� */
	public TImageButton btnNew;

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �ҏW�{�^�� */
	public TImageButton btnModify;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** ���[�U�[�͈͌��� */
	public TUserReferenceRange ctrlUserRange;

	/** �L�����Ԑ؂��\�����邩 */
	public TCheckBox chkOutputTermEnd;

	/** �Ǘ�1�}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �R�[�h */
		code,
		/** ���� */
		name,
		/** ���� */
		names,
		/** �������� */
		namek,
		/** �v���O�������[���R�[�h */
		program_roleCode,
		/** �v���O�������[������ */
		program_roleNames,
		/** ���[�U���[���R�[�h */
		user_roleCode,
		/** ���[�U���[������ */
		user_roleNames,
		/** ���F�������[���R�[�h */
		aprv_roleCode,
		/** ���F�������[������ */
		aprv_roleNames,
		/** INV.SIGNER DEPT */
		signerDept,
		/** INV.SIGNER TITLE */
		signerTitle,
		/** INV.SIGNER NAME */
		signerName,
		/** INV.SIGN FILE NAME */
		signFileName,
		/** EMAIL ADDRESS */
		eMailAddress,
		/** �X�V�����敪 */
		updateAuthority,
		/** ���Z�`�[���͋敪 */
		isAccountant,
		/** �Ј��R�[�h */
		employeeCode,
		/** �Ј����� */
		employeeNames,
		/** ����R�[�h */
		departmentCode,
		/** ���嗪�� */
		departmentNames,
		/** ����R�[�h */
		language,
		/** FROM */
		dateFrom,
		/** TO */
		dateTo
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		ctrlUserRange = new TUserReferenceRange();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.code, getWord("C00589"), 100);// ���[�U�[�R�[�h
		tbl.addColumn(SC.name, getWord("C00691"), 200);// ���[�U�[����
		tbl.addColumn(SC.names, getWord("C00692"), 200);// ���[�U�[����
		tbl.addColumn(SC.namek, getWord("C00693"), 200);// ���[�U�[��������
		tbl.addColumn(SC.program_roleCode, getWord("C11159"), 200);// �v���O�������[���R�[�h
		tbl.addColumn(SC.program_roleNames, getWord("C11213"), 200);// �v���O�������[������
		tbl.addColumn(SC.user_roleCode, getWord("C11161"), 100);// ���[�U�[���[���R�[�h
		tbl.addColumn(SC.user_roleNames, getWord("C11214"), 200);// ���[�U�[���[������
		tbl.addColumn(SC.aprv_roleCode, getWord("C11941"), 100);// ���F�������[���R�[�h
		tbl.addColumn(SC.aprv_roleNames, getWord("C11944"), 200);// ���F�������[������
		if (MG0020UserMasterDialog.USE_BL_SIGNER) {
			tbl.addColumn(SC.signerDept, getWord("CM0074"), 200);// INV.SIGNER DEPT
			tbl.addColumn(SC.signerTitle, getWord("CM0075"), 200);// INV.SIGNER TITLE
			tbl.addColumn(SC.signerName, getWord("CM0076"), 200);// INV.SIGNER NAME
		} else {
			tbl.addColumn(SC.signerDept, "", -1, false);// INV.SIGNER DEPT
			tbl.addColumn(SC.signerTitle, "", -1, false);// INV.SIGNER TITLE
			tbl.addColumn(SC.signerName, "", -1, false);// INV.SIGNER NAME
		}
		if (MG0020UserMasterDialog.USE_SIGNER_ATTACH) {
			tbl.addColumn(SC.signFileName, getWord("SIGN"), 200);// INV.SIGN FILE NAME
		} else {
			tbl.addColumn(SC.signFileName, "", -1, false);// INV.SIGN FILE NAME
		}
		tbl.addColumn(SC.eMailAddress, getWord("COP065"), 200);// E-MAIL
		tbl.addColumn(SC.updateAuthority, getWord("C00170"), 150);// �X�V�������x��
		tbl.addColumn(SC.isAccountant, getWord("C01056"), 150);// ���Z�`�[���͋敪
		tbl.addColumn(SC.employeeCode, getWord("C00697"), 100);// �Ј��R�[�h
		tbl.addColumn(SC.employeeNames, getWord("C00808"), 200);// �Ј�����
		tbl.addColumn(SC.departmentCode, getWord("C02043"), 100);// ��������R�[�h
		tbl.addColumn(SC.departmentNames, getWord("C11215"), 200);// �������嗪��
		tbl.addColumn(SC.language, getWord("C00699"), 100);// ����R�[�h
		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER);
	}

	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// ���[�U�[�����͈�
		ctrlUserRange.setLocation(20, 20);
		pnlSearchCondition.add(ctrlUserRange);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

		// ����
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlUserRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
