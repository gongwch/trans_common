package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.define.TransUtil;
import jp.co.ais.trans2.model.company.*;

/**
 * ���F�������[���}�X�^�̕ҏW���
 */
public class MG0510AprvRoleGroupMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �R�[�h */
	public TLabelField ctrlGroupCode;

	/** ���� */
	public TLabelField ctrlGroupName;

	/** ���� */
	public TLabelField ctrlGroupNames;

	/** �������� */
	public TLabelField ctrlGroupNamek;

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

	/** ���p�l���s���� �� */
	public TButton btnUp;

	/** ���p�l���s���� �� */
	public TButton btnDown;

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
		/** bean */
		bean,
		/** �R�[�h */
		code,
		/** ���� */
		name,
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0510AprvRoleGroupMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * �R���|�[�l���g������
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlGroupCode = new TLabelField();
		ctrlGroupName = new TLabelField();
		ctrlGroupNames = new TLabelField();
		ctrlGroupNamek = new TLabelField();
		ctrlBeginDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		ctrlEndDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		splitPane = new JSplitPane();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		btnUp = new TButton();
		btnDown = new TButton();
		pnlLeft = new TPanel();
		btnAdd = new TButton();
		btnCancel = new TButton();
		tblRight = new TTable();
		tblRight.addColumn(SC.bean, getWord(""), -1);// bean
		tblRight.addColumn(SC.code, getWord("C11154"), 80);// �R�[�h
		tblRight.addColumn(SC.name, getWord("C11155"), 110);// ����
		tblLeft = new TTable();
		tblLeft.addColumn(SC.bean, getWord(""), -1);// bean
		tblLeft.addColumn(SC.code, getWord("C11154"), 80);// �R�[�h
		tblLeft.addColumn(SC.name, getWord("C11155"), 110);// ����
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
		ctrlGroupCode.setLabelSize(120);
		ctrlGroupCode.setFieldSize(75);
		ctrlGroupCode.setSize(200, 20);
		ctrlGroupCode.setLocation(x, y);
		ctrlGroupCode.setLabelText(getWord("C12230"));
		ctrlGroupCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		ctrlGroupCode.setImeMode(false);
		ctrlGroupCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlGroupCode);

		// ����
		ctrlGroupName.setLabelSize(120);
		ctrlGroupName.setFieldSize(300);
		ctrlGroupName.setSize(425, 20);
		ctrlGroupName.setLocation(x, y += 25);
		ctrlGroupName.setLabelText(getWord("C12231"));
		ctrlGroupName.setMaxLength(40);
		pnlBodyTop.add(ctrlGroupName);

		// ����
		ctrlGroupNames.setLabelSize(120);
		ctrlGroupNames.setFieldSize(220);
		ctrlGroupNames.setSize(345, 20);
		ctrlGroupNames.setLocation(x, y += 25);
		ctrlGroupNames.setLabelText(getWord("C12232"));
		ctrlGroupNames.setMaxLength(20);
		pnlBodyTop.add(ctrlGroupNames);

		// ��������
		ctrlGroupNamek.setLabelSize(120);
		ctrlGroupNamek.setFieldSize(300);
		ctrlGroupNamek.setSize(425, 20);
		ctrlGroupNamek.setLocation(x, y += 25);
		// ���[����������
		ctrlGroupNamek.setLangMessageID("C12233");
		ctrlGroupNamek.setMaxLength(40);
		pnlBodyTop.add(ctrlGroupNamek);

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
		gc.insets = new Insets(20, 20, 0, 0);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(tblRight, gc);

		TPanel pnlLeftButton = new TPanel();
		pnlLeftButton.setLayout(null);
		TGuiUtil.setComponentSize(pnlLeftButton, 60, 20);

		Icon up = ResourceUtil.getImage(TAppletMain.class, "images/btnrowup.png");
		TGuiUtil.setComponentSize(btnUp, 20, 20);
		btnUp.setIcon(up);
		btnUp.setLocation(35, 0);
		pnlLeftButton.add(btnUp);

		Icon down = ResourceUtil.getImage(TAppletMain.class, "images/btnrowdown.png");
		TGuiUtil.setComponentSize(btnDown, 20, 20);
		btnDown.setIcon(down);
		btnDown.setLocation(55, 0);
		pnlLeftButton.add(btnDown);

		GridBagConstraints gc1 = new GridBagConstraints();
		gc1.insets = new Insets(0, 5, 0, 0);
		gc1.gridx = 1;
		gc1.gridy = 1;
		gc1.weightx = 1.0d;
		gc1.weighty = 0.0d;
		gc1.fill = GridBagConstraints.HORIZONTAL;
		pnlLeft.add(pnlLeftButton, gc1);

		// ���ꗗ
		tblLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblLeft.setSortable(false);
		gc1.gridy = 2;
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
		ctrlGroupCode.setTabControlNo(i++);
		ctrlGroupName.setTabControlNo(i++);
		ctrlGroupNames.setTabControlNo(i++);
		ctrlGroupNamek.setTabControlNo(i++);
		ctrlBeginDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnAdd.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}