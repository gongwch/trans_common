package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author wangjing
 */
public class MG0210EditDisplayDialog extends TDialog {

	/** �V���A��UID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** �R���g���[���N���X */
	public MG0210EditDisplayDialogCtrl ctrl;

	/** �{�^���p�l�� */
	public TPanel pnlButton;

	/** ���C���p�l�� */
	public TPanel pnlDetail;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** ����{�^�� */
	public TButton btnReturn;

	/** �R�[�h */
	public TLabelField ctrlManagementCode;

	/** ���� */
	public TLabelField ctrlManagementName;

	/** ���� */
	public TLabelField ctrlManagementAbbreviationName;

	/** �������� */
	public TLabelField ctrlManagementNameForSearch;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/** �m�肳�ꂽ���ǂ��� */
	public boolean isSettle = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param modal ���[�_���_�C�A���O��(true: ���[�_�����[�h)
	 * @param ctrl �R���g���[���N���X
	 * @param titleid
	 */
	public MG0210EditDisplayDialog(Frame parent, boolean modal, MG0210EditDisplayDialogCtrl ctrl, String titleid) {
		// �e�t���[����ݒ�
		super(parent, modal);
		// �R���g���[���N���X��ݒ�
		this.ctrl = ctrl;
		this.setResizable(false);
		// ��ʂ̏�����
		initComponents();
		setLangMessageID(titleid);
		// ��ʂ̐ݒ�
		setSize(600, 250);
		super.initDialog();
	}

	/**
	 * �R���|�[�l���g��`
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlDetail = new TPanel();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlManagementCode = new TLabelField();
		ctrlManagementAbbreviationName = new TLabelField();
		ctrlManagementNameForSearch = createForSearchCtrl();
		ctrlManagementName = new TLabelField();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();

		getContentPane().setLayout(new GridBagLayout());

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(600, 185));
		pnlDetail.setMinimumSize(new Dimension(600, 185));
		pnlDetail.setPreferredSize(new Dimension(600, 185));
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(5);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelSize(110);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(6);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 30, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		ctrlManagementCode.setFieldSize(90);
		ctrlManagementCode.setLabelSize(160);
		ctrlManagementCode.setLangMessageID("C00814");
		ctrlManagementCode.getField().setAllowedSpace(false);
		ctrlManagementCode.setMaxLength(10);
		ctrlManagementCode.setTabControlNo(1);
		ctrlManagementCode.setImeMode(false);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, -50, 0, 0);
		pnlDetail.add(ctrlManagementCode, gridBagConstraints);

		ctrlManagementAbbreviationName.setFieldSize(160);
		ctrlManagementAbbreviationName.setLabelSize(160);
		ctrlManagementAbbreviationName.setLangMessageID("C00816");
		ctrlManagementAbbreviationName.setMaxLength(20);
		ctrlManagementAbbreviationName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, -50, 0, 0);
		pnlDetail.add(ctrlManagementAbbreviationName, gridBagConstraints);

		ctrlManagementNameForSearch.setFieldSize(300);
		ctrlManagementNameForSearch.setLabelSize(160);
		ctrlManagementNameForSearch.setLangMessageID("C00817");
		ctrlManagementNameForSearch.setMaxLength(40);
		ctrlManagementNameForSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, -50, 0, 0);
		pnlDetail.add(ctrlManagementNameForSearch, gridBagConstraints);

		ctrlManagementName.setFieldSize(300);
		ctrlManagementName.setLabelSize(160);
		ctrlManagementName.setLangMessageID("C00815");
		ctrlManagementName.setMaxLength(40);
		ctrlManagementName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, -50, 0, 0);
		pnlDetail.add(ctrlManagementName, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(230, 40));
		pnlButton.setMinimumSize(new Dimension(230, 40));
		pnlButton.setPreferredSize(new Dimension(230, 40));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 0, 5, 10);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(8);
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				btnReturnActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 350, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pack();
	}

	/**
	 * �������̃t�B�[���h�쐬
	 * 
	 * @return �������̃t�B�[���h
	 */
	public TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	/**
	 * �m��{�^�������̏���
	 */
	public void btnSettleActionPerformed() {
		// �J�����ʂ̐ݒ�
		isSettle = true;
		// ��ʂ�߂�
		ctrl.disposeDialog();
	}

	/**
	 * �߂�{�^�������̏���
	 */
	public void btnReturnActionPerformed() {
		// �J�����ʂ̐ݒ�
		isSettle = false;
		// ��ʂ�߂�
		ctrl.disposeDialog();
	}
}