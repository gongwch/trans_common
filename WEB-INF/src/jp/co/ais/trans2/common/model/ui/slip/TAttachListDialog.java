package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �Y�t�ꗗ���
 * 
 * @author AIS
 */
public class TAttachListDialog extends TDialog {

	/** �ǉ��{�^�� */
	public TButton btnAdd;

	/** �Ɖ�{�^�� */
	public TButton btnDrillDown;

	/** �폜�{�^�� */
	public TButton btnDelete;

	/** ����{�^�� */
	public TButton btnClose;

	/** �X�v���b�h */
	public TTable tbl;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company ��Џ��
	 * @param parent �e�t���[��
	 */
	public TAttachListDialog(Company company, Frame parent) {
		super(company, parent, true);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company ��Џ��
	 * @param parent �e�t���[��
	 */
	public TAttachListDialog(Company company, Dialog parent) {
		super(company, parent, true);
	}

	@Override
	public void initComponents() {
		btnAdd = new TImageButton(IconType.NEW);
		btnDrillDown = new TImageButton(IconType.PREVIEW);
		btnDelete = new TImageButton(IconType.DELETE);
		btnClose = new TButton();

		tbl = new TTable();
	}

	@Override
	public void allocateComponents() {

		setResizable(true);

		setPreferredSize(new Dimension(850, 520));

		setTitle(getWord("C11613")); // �Y�t�t�@�C��

		int count = 4; // �{�^����
		int x = getPreferredSize().width - 110 * count - HEADER_MARGIN_X * count - 95;
		int y = 10;

		// �ǉ��{�^��
		btnAdd.setLangMessageID("C03263"); // �ǉ�
		btnAdd.setShortcutKey(KeyEvent.VK_F2);
		btnAdd.setSize(25, 110);
		btnAdd.setLocation(x, y);
		pnlHeader.add(btnAdd);

		x += btnAdd.getWidth() + HEADER_MARGIN_X;

		// �Ɖ�{�^��
		btnDrillDown.setLangMessageID("C03661"); // �Ɖ�
		btnDrillDown.setShortcutKey(KeyEvent.VK_F3);
		btnDrillDown.setSize(25, 110);
		btnDrillDown.setLocation(x, y);
		pnlHeader.add(btnDrillDown);

		x += btnDrillDown.getWidth() + HEADER_MARGIN_X + 30;

		// �폜�{�^��
		btnDelete.setLangMessageID("C01544"); // �폜
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, y);
		pnlHeader.add(btnDelete);

		x += btnDelete.getWidth() + HEADER_MARGIN_X + 30;

		// ����{�^��
		btnClose.setLangMessageID("C02374"); // ����
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, y);
		pnlHeader.add(btnClose);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(tbl, gc);

		// �X�v���b�h�̏�����
		initSpread();

		pack();

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		btnAdd.setTabControlNo(i++);
		btnDrillDown.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
	}

	/**
	 * �X�v���b�h������
	 */
	public void initSpread() {
		tbl.addColumn(SC.bean, "", -1); // Bean
		tbl.addColumn(SC.employeeCode, getWord("C00363"), 75); // �S����
		tbl.addColumn(SC.employeeName, getWord("C11296"), 95); // �S���Җ���
		tbl.addColumn(SC.inputDate, getWord("C11757"), 115, SwingConstants.CENTER); // ��������
		tbl.addColumn(SC.filename, getWord("C10018"), 440); // �t�@�C����
	}

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** �G���e�B�e�B */
		bean,

		/** �S���҃R�[�h */
		employeeCode,

		/** �S���Җ��� */
		employeeName,

		/** �������� */
		inputDate,

		/** �t�@�C���� */
		filename

	}
}
