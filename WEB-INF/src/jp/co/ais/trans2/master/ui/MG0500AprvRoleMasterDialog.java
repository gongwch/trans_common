package jp.co.ais.trans2.master.ui;

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
 * ���F�������[���}�X�^�̕ҏW���
 */
public class MG0500AprvRoleMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090392L;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ���[���R�[�h */
	public TLabelField ctrlRoleCode;

	/** ���[������ */
	public TLabelField ctrlRoleName;

	/** ���[������ */
	public TLabelField ctrlRoleNames;

	/** ���[���������� */
	public TLabelField ctrlRoleNamek;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrlBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar ctrlEndDate;

	/** ���ƉE�̋��E */
	public JSplitPane splitPane;

	/** �����p�l�� */
	public TPanel pnlCenter;

	/** �E�p�l�� */
	public TPanel pnlRight;

	/** ���p�l�� */
	public TPanel pnlLeft;

	/** �ǉ��{�^�� */
	public TButton btnAdd;

	/** �폜�{�^�� */
	public TButton btnCancel;

	/** �E�e�[�u���ꗗ */
	public TTable tblRight;

	/** ���e�[�u���ꗗ */
	public TTable tblLeft;

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** ���[�U�[�R�[�h */
		userCode,
		/** ���[�U�[���� */
		userName,
		/** �������喼�� */
		depName;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0500AprvRoleMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * �R���|�[�l���g������
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlRoleCode = new TLabelField();
		ctrlRoleName = new TLabelField();
		ctrlRoleNames = new TLabelField();
		ctrlRoleNamek = new TLabelField();
		ctrlBeginDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		ctrlEndDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		splitPane = new JSplitPane();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		pnlLeft = new TPanel();
		btnAdd = new TButton();
		btnCancel = new TButton();
		tblRight = new TTable();
		tblRight.addColumn(SC.userCode, getWord("C00589"), 80);// ���[�U�[�R�[�h
		tblRight.addColumn(SC.userName, getWord("C00691"), 110);// ���[�U�[����
		tblRight.addColumn(SC.depName, getWord("C11163"), 110);// �������喼��
		tblLeft = new TTable();
		tblLeft.addColumn(SC.userCode, getWord("C11938"), 80);// �������[�U�[
		tblLeft.addColumn(SC.userName, getWord("C00691"), 110);// ���[�U�[����
		tblLeft.addColumn(SC.depName, getWord("C11163"), 110);// �������喼��
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	@Override
	public void allocateComponents() {

		setSize(850, 600);

		// �m��{�^��
		int x = 620 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 620;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(480, 155));
		pnlBodyTop.setMinimumSize(new Dimension(480, 155));
		pnlBodyTop.setPreferredSize(new Dimension(480, 155));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// �V�X�e���敪
		x = 10;
		int y = 10;

		// �R�[�h
		ctrlRoleCode.setLabelSize(120);
		ctrlRoleCode.setFieldSize(75);
		ctrlRoleCode.setSize(200, 20);
		ctrlRoleCode.setLocation(x, y);
		ctrlRoleCode.setLabelText(getWord("C11154"));
		ctrlRoleCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		ctrlRoleCode.setImeMode(false);
		ctrlRoleCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlRoleCode);

		// ����
		ctrlRoleName.setLabelSize(120);
		ctrlRoleName.setFieldSize(300);
		ctrlRoleName.setSize(425, 20);
		ctrlRoleName.setLocation(x, y += 25);
		ctrlRoleName.setLabelText(getWord("C11155"));
		ctrlRoleName.setMaxLength(40);
		pnlBodyTop.add(ctrlRoleName);

		// ����
		ctrlRoleNames.setLabelSize(120);
		ctrlRoleNames.setFieldSize(220);
		ctrlRoleNames.setSize(345, 20);
		ctrlRoleNames.setLocation(x, y += 25);
		ctrlRoleNames.setLabelText(getWord("C11156"));
		ctrlRoleNames.setMaxLength(20);
		pnlBodyTop.add(ctrlRoleNames);

		// ��������
		ctrlRoleNamek.setLabelSize(120);
		ctrlRoleNamek.setFieldSize(300);
		ctrlRoleNamek.setSize(425, 20);
		ctrlRoleNamek.setLocation(x, y += 25);
		// ���[����������
		ctrlRoleNamek.setLangMessageID("C11157");
		ctrlRoleNamek.setMaxLength(40);
		pnlBodyTop.add(ctrlRoleNamek);

		// �J�n�N����
		ctrlBeginDate.setLabelSize(120);
		ctrlBeginDate.setSize(120 + ctrlBeginDate.getCalendarSize() + 5, 20);
		ctrlBeginDate.setLocation(x, y += 25);
		ctrlBeginDate.setLangMessageID("C00055");
		pnlBodyTop.add(ctrlBeginDate);

		// �I���N����
		ctrlEndDate.setLabelSize(120);
		ctrlEndDate.setSize(120 + ctrlBeginDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLocation(x, y += 25);
		ctrlEndDate.setLangMessageID("C00261");
		pnlBodyTop.add(ctrlEndDate);

		// splitPane
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(365);
		splitPane.setDividerSize(2);
		splitPane.setBorder(null);

		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 0, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlBody.add(splitPane, gc);

		pnlRight.setLayout(new GridBagLayout());
		splitPane.setRightComponent(pnlRight);

		pnlCenter.setLayout(new GridBagLayout());
		pnlCenter.setMaximumSize(new Dimension(85, 600));
		pnlCenter.setMinimumSize(new Dimension(85, 600));
		pnlCenter.setPreferredSize(new Dimension(85, 600));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.VERTICAL;
		pnlRight.add(pnlCenter, gc);

		// ����
		pnlLeft.setLayout(new GridBagLayout());
		splitPane.setLeftComponent(pnlLeft);

		// ���[�U�[�ǉ��{�^��
		btnAdd.setLangMessageID("C10140");// ��
		btnAdd.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 20, 20, 0);
		pnlCenter.add(btnAdd, gc);

		// ���[�U�[�폜�{�^��
		btnCancel.setLangMessageID("C10139");// ��
		btnCancel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(20, 20, 0, 0);
		pnlCenter.add(btnCancel, gc);

		// �E�ꗗ
		tblRight.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 20, 0, 0);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(tblRight, gc);

		// ���ꗗ
		tblLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		GridBagConstraints gc1 = new GridBagConstraints();
		gc1.insets = new Insets(0, 5, 0, 0);
		gc1.gridx = 1;
		gc1.weightx = 1.0d;
		gc1.weighty = 1.0d;
		gc1.fill = GridBagConstraints.BOTH;
		pnlLeft.add(tblLeft, gc1);

	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlRoleCode.setTabControlNo(i++);
		ctrlRoleName.setTabControlNo(i++);
		ctrlRoleNames.setTabControlNo(i++);
		ctrlRoleNamek.setTabControlNo(i++);
		ctrlBeginDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnAdd.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}