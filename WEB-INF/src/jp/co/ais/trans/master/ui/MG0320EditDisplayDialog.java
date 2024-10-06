package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �V�X�e���敪�}�X�^
 */
public class MG0320EditDisplayDialog extends TDialog {

	/** �V���A��UID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** �R���g���[���N���X */
	public MG0320EditDisplayDialogCtrl ctrl;

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
	public MG0320EditDisplayDialog(Frame parent, boolean modal, MG0320EditDisplayDialogCtrl ctrl, String titleid) {
		// �e�t���[����ݒ�
		super(parent, modal);
		// �R���g���[���N���X��ݒ�
		this.ctrl = ctrl;
		this.setResizable(false);
		// ��ʂ̏�����
		initComponents();
		setLangMessageID(titleid);
		// ��ʂ̐ݒ�
		setSize(650, 220);
		super.initDialog();
	}

	/**
	 * �R���|�[�l���g������
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints;

		getContentPane().setLayout(new GridBagLayout());

		// �{�^���p�l��
		pnlButton = new TPanel();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(600, 40));
		pnlButton.setMinimumSize(new Dimension(600, 40));
		pnlButton.setPreferredSize(new Dimension(600, 40));

		// �m��{�^��
		btnSettle = new TImageButton(IconType.SETTLE);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 370, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				// �J�����ʂ̐ݒ�
				isSettle = true;
				// ��ʂ�߂�
				ctrl.disposeDialog();
			}
		});

		// ����{�^��
		btnReturn = new TButton();
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 10);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				// �J�����ʂ̐ݒ�
				isSettle = false;
				// ��ʂ�߂�
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		// ���C���p�l��
		pnlDetail = new TPanel();
		pnlDetail.setLayout(new GridBagLayout());
		pnlDetail.setMaximumSize(new Dimension(600, 132));
		pnlDetail.setMinimumSize(new Dimension(600, 132));
		pnlDetail.setPreferredSize(new Dimension(600, 132));

		// �V�X�e���敪
		ctrlSystemDivision = new TLabelField();
		ctrlSystemDivision.setFieldSize(35);
		ctrlSystemDivision.setLabelSize(130);
		ctrlSystemDivision.setLangMessageID("C00217");
		ctrlSystemDivision.getField().setAllowedSpace(false);
		ctrlSystemDivision.setMaxLength(2);
		ctrlSystemDivision.setTabControlNo(1);
		ctrlSystemDivision.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(ctrlSystemDivision, gridBagConstraints);

		// �V�X�e���敪����
		ctrlSystemDivisionName = new TLabelField();
		ctrlSystemDivisionName.setFieldSize(450);
		ctrlSystemDivisionName.setLabelSize(130);
		ctrlSystemDivisionName.setLangMessageID("C00832");
		ctrlSystemDivisionName.setMaxLength(40);
		ctrlSystemDivisionName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSystemDivisionName, gridBagConstraints);

		// �V�X�e���敪����
		ctrlSystemDivisionAbbreviatedName = new TLabelField();
		ctrlSystemDivisionAbbreviatedName.setFieldSize(230);
		ctrlSystemDivisionAbbreviatedName.setLabelSize(130);
		ctrlSystemDivisionAbbreviatedName.setLangMessageID("C00833");
		ctrlSystemDivisionAbbreviatedName.setMaxLength(20);
		ctrlSystemDivisionAbbreviatedName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSystemDivisionAbbreviatedName, gridBagConstraints);

		// �V�X�e���敪��������
		ctrlSystemDivisionNameForSearch = createForSearchCtrl();
		ctrlSystemDivisionNameForSearch.setFieldSize(230);
		ctrlSystemDivisionNameForSearch.setLabelSize(130);
		ctrlSystemDivisionNameForSearch.setLangMessageID("C00834");
		ctrlSystemDivisionNameForSearch.setMaxLength(20);
		ctrlSystemDivisionNameForSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSystemDivisionNameForSearch, gridBagConstraints);

		// �O���V�X�e���敪
		ctrlOutsideSystemDivision = new TLabelComboBox();
		ctrlOutsideSystemDivision.setComboSize(170);
		ctrlOutsideSystemDivision.setFocusable(false);
		ctrlOutsideSystemDivision.setLabelSize(130);
		ctrlOutsideSystemDivision.setLangMessageID("C01018");
		ctrlOutsideSystemDivision.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlDetail.add(ctrlOutsideSystemDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 10, 10, 20);
		getContentPane().add(pnlDetail, gridBagConstraints);

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

	/** �{�^���p�l�� */
	public TPanel pnlButton;

	/** ����p�l�� */

	public TPanel pnlDetail;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** ����{�^�� */
	public TButton btnReturn;

	/** �V�X�e���敪 */
	public TLabelField ctrlSystemDivision;

	/** �V�X�e���敪���� */
	public TLabelField ctrlSystemDivisionName;

	/** �V�X�e���敪���� */
	public TLabelField ctrlSystemDivisionAbbreviatedName;

	/** �V�X�e���敪�������� */
	public TLabelField ctrlSystemDivisionNameForSearch;

	/** �O���V�X�e���敪 */
	public TLabelComboBox ctrlOutsideSystemDivision;
}