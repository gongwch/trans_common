package jp.co.ais.trans.master.common;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �����_�C�A���O
 * 
 * @author Yit
 */
public class REFDialogMaster extends TDialog {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �R���g���[���N���X */
	protected REFDialogMasterCtrl ctrl;

	/** �m�肳�ꂽ���ǂ��� */
	boolean isSettle = false;

	/** �X�v���b�h�V�[�gDataSource */
	protected JCVectorDataSource ds = new JCVectorDataSource();

	/** ��ʋ敪�p�̕ϐ� */
	protected String kbn;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �t���[��
	 * @param modal ���f��
	 * @param ctrl �R���g���[���N���X
	 * @param gamenKbn ��ʋ敪
	 */
	public REFDialogMaster(Frame parent, boolean modal, REFDialogMasterCtrl ctrl, int gamenKbn) {
		super(parent, modal);
		this.ctrl = ctrl;

		initComponents();
		initSpreadSheet(gamenKbn);
		setSize(660, 500);
		GridBagConstraints gridBagConstraints;
		txtCode.setImeMode(false);
		switch (gamenKbn) {
			case REFDialogMasterCtrl.KMK_MST:
				String top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(40);
				txtNameForSearch.setMaxLength(20);
				break;
			case REFDialogMasterCtrl.HKM_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(40);
				txtNameForSearch.setMaxLength(20);
				break;
			case REFDialogMasterCtrl.UKM_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(40);
				break;

			case REFDialogMasterCtrl.BMN_MST:
				setLangMessageID("C01687");
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;

			case REFDialogMasterCtrl.EMP_MST:
				setLangMessageID("C01677");
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;

			case REFDialogMasterCtrl.KNR1_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;
			case REFDialogMasterCtrl.KNR2_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;
			case REFDialogMasterCtrl.KNR3_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;
			case REFDialogMasterCtrl.KNR4_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;
			case REFDialogMasterCtrl.KNR5_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;
			case REFDialogMasterCtrl.KNR6_MST:
				top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setMaxLength(40);
				break;
			case REFDialogMasterCtrl.KMK_TK_MST:
				setLangMessageID("C01688");
				txtCode.setMaxLength(2);
				txtAbbreviationName.setMaxLength(40);
				txtNameForSearch.setMaxLength(20);
				break;
			case REFDialogMasterCtrl.ENV_MST:
				setLangMessageID("C02006");
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(20);
				txtNameForSearch.setVisible(false);
				txtNameForSearch.setEditable(false);
				gridBagConstraints = new GridBagConstraints();
				txtCode.setMaximumSize(new Dimension(127, 20));
				txtCode.setMinimumSize(new Dimension(127, 20));
				txtCode.setPreferredSize(new Dimension(127, 20));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 5, 0, 0);
				pnlText.add(txtCode, gridBagConstraints);
				txtAbbreviationName.setMaximumSize(new Dimension(415, 20));
				txtAbbreviationName.setMinimumSize(new Dimension(415, 20));
				txtAbbreviationName.setPreferredSize(new Dimension(415, 20));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 0, 0, 0);
				pnlText.add(txtAbbreviationName, gridBagConstraints);

			default:
				break;
		}

		setTxtKeyListener();
		super.initDialog();

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				txtCode.requestFocus();
			}
		});
	}

	/**
	 * �R���X�g���N�^(�_�C�A���O����J���ꍇ)
	 * 
	 * @param parent
	 * @param modal
	 * @param ctrl
	 * @param gamenKbn
	 */
	public REFDialogMaster(Dialog parent, boolean modal, REFDialogMasterCtrl ctrl, int gamenKbn) {
		super(parent, modal);
		this.ctrl = ctrl;

		initComponents();
		initSpreadSheet(gamenKbn);
		setSize(660, 500);
		txtCode.setImeMode(false);
		switch (gamenKbn) {
			case REFDialogMasterCtrl.KMK_MST:
				String top = ctrl.convertTop(word);
				setLangMessageID(top);
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(40);
				txtNameForSearch.setMaxLength(20);
				break;
			case REFDialogMasterCtrl.HKM_MST:
				setLangMessageID(ctrl.convertTop(word));
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(40);
				txtNameForSearch.setMaxLength(20);
				break;
			case REFDialogMasterCtrl.ENV_MST:
				setLangMessageID("C02006");
				txtCode.setMaxLength(10);
				txtAbbreviationName.setMaxLength(40);
				txtNameForSearch.setVisible(false);
				break;

			default:
				break;
		}

		setTxtKeyListener();

		super.initDialog();

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				txtCode.requestFocus();
			}
		});

	}

	protected void setTxtKeyListener() {
		txtCode.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!txtCode.isFocusOwner() || !txtCode.isEditable()) {
						return;
					}
					btnSearchActionPerformed();

				}
			}
		});

		txtNameForSearch.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!txtNameForSearch.isFocusOwner() || !txtNameForSearch.isEditable()) {
						return;
					}
					btnSearchActionPerformed();

				}
			}
		});

		txtAbbreviationName.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!txtAbbreviationName.isFocusOwner() || !txtAbbreviationName.isEditable()) {
						return;
					}
					btnSearchActionPerformed();
				}
			}
		});
	}

	/**
	 * �R���X�g���N�^ �[�t���[������J���ꍇ
	 * 
	 * @param parent �t���[��
	 * @param modal ���f��
	 * @param ctrl �R���g���[���N���X
	 * @param param �p�����[�^
	 */
	public REFDialogMaster(Frame parent, boolean modal, REFDialogMasterCtrl ctrl, Map param) {
		super(parent, modal);
		init(ctrl, param);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �_�C�A���O
	 * @param modal ���f��
	 * @param ctrl �R���g���[���N���X
	 * @param param �p�����[�^
	 */
	public REFDialogMaster(Dialog parent, boolean modal, REFDialogMasterCtrl ctrl, Map param) {
		super(parent, modal);
		init(ctrl, param);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	protected void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlDetial = new TPanel();
		pnlJournal = new TPanel();
		ssJournal = new TTable();
		pnlText = new TPanel();
		txtCode = new TTextField();
		txtNameForSearch = new TTextField();
		txtAbbreviationName = new TTextField();

		pnlButton = new TPanel();
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnCancel = new TButton();

		setLayout(new GridBagLayout());

		pnlDetial.setLayout(new GridBagLayout());

		pnlDetial.setMaximumSize(new Dimension(640, 380));
		pnlDetial.setMinimumSize(new Dimension(640, 380));
		pnlDetial.setPreferredSize(new Dimension(640, 380));
		pnlJournal.setLayout(new javax.swing.BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(620, 300));
		pnlJournal.setMinimumSize(new Dimension(620, 300));
		pnlJournal.setPreferredSize(new Dimension(620, 300));
		ssJournal.setTabControlNo(7);
		ssJournal.setSort(true);
		ssJournal.setEnterToButton(true);
		pnlJournal.add(ssJournal);

		pnlDetial.add(pnlJournal, new GridBagConstraints());

		pnlText.setLayout(new GridBagLayout());

		pnlText.setMaximumSize(new Dimension(640, 30));
		pnlText.setMinimumSize(new Dimension(640, 30));
		pnlText.setPreferredSize(new Dimension(640, 30));
		txtCode.setMaximumSize(new Dimension(126, 20));
		txtCode.setMinimumSize(new Dimension(126, 20));
		txtCode.setPreferredSize(new Dimension(126, 20));
		txtCode.setTabControlNo(1);
		txtCode.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlText.add(txtCode, gridBagConstraints);

		txtNameForSearch.setMaximumSize(new Dimension(281, 20));
		txtNameForSearch.setMinimumSize(new Dimension(281, 20));
		txtNameForSearch.setPreferredSize(new Dimension(281, 20));
		txtNameForSearch.setTabControlNo(3);
		txtNameForSearch.setProhibitionWords("��", "�Q");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlText.add(txtNameForSearch, gridBagConstraints);

		txtAbbreviationName.setMaximumSize(new Dimension(151, 20));
		txtAbbreviationName.setMinimumSize(new Dimension(151, 20));
		txtAbbreviationName.setPreferredSize(new Dimension(151, 20));
		txtAbbreviationName.setTabControlNo(2);
		txtAbbreviationName.setProhibitionWords("��", "�Q");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlText.add(txtAbbreviationName, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		pnlDetial.add(pnlText, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(500, 35));
		pnlButton.setMinimumSize(new Dimension(500, 35));
		pnlButton.setPreferredSize(new Dimension(500, 35));
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
		btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSearchActionPerformed();
			}
		});

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		btnCancel.setLangMessageID("C00405");
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		btnCancel.setMaximumSize(new Dimension(110, 25));
		btnCancel.setMinimumSize(new Dimension(110, 25));
		btnCancel.setPreferredSize(new Dimension(110, 25));
		btnCancel.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 50, 0, 40);
		pnlButton.add(btnCancel, gridBagConstraints);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnCancelActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		pnlDetial.add(pnlButton, gridBagConstraints);

		add(pnlDetial, new GridBagConstraints());

	}

	/**
	 * �X�v���b�h������
	 * 
	 * @param gamenKbn ��ʋ敪
	 */
	private void initSpreadSheet(int gamenKbn) {
		// �^�C�g��
		String[] clabel = null;
		int[] columnWidths = null;
		Map map = null;
		String code = null;
		String name_S = null;
		String name_K = null;

		// �Z����
		ssJournal.setCharWidth(0, 10);
		ssJournal.setCharWidth(1, 12);
		ssJournal.setCharWidth(2, 23);
		ssJournal.setColumnHidden(3, true);
		columnWidths = new int[] { 10, 12, 23, 10 };

		switch (gamenKbn) {
			case REFDialogMasterCtrl.KMK_MST:// �Ȗڈꗗ�}�X�^����
				// �ȖڃR�[�h�A�Ȗڗ��́A�Ȗڌ�������
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("kmkName"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);
				break;
			case REFDialogMasterCtrl.HKM_MST:
				// �⏕�Ȗڃ}�X�^�ꗗ
				// �⏕�ȖڃR�[�h�A�⏕�Ȗڗ��́A�⏕�Ȗڌ�������
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("hkmName"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);
				break;
			case REFDialogMasterCtrl.UKM_MST:
				// ����Ȗڃ}�X�^�ꗗ
				// ����ȖڃR�[�h�A����Ȗڗ��́A����Ȗڌ�������
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("ukmName"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);
				break;

			case REFDialogMasterCtrl.KNR1_MST:// �Ǘ�1�}�X�^�ꗗ
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("knrName1"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				break;
			case REFDialogMasterCtrl.KNR2_MST:// �Ǘ�2�}�X�^�ꗗ
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("knrName2"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				break;
			case REFDialogMasterCtrl.KNR3_MST:// �Ǘ�3�}�X�^�ꗗ
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("knrName3"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				break;
			case REFDialogMasterCtrl.KNR4_MST:// �Ǘ�4�}�X�^�ꗗ
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("knrName4"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				break;
			case REFDialogMasterCtrl.KNR5_MST:// �Ǘ�5�}�X�^�ꗗ
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("knrName5"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				break;
			case REFDialogMasterCtrl.KNR6_MST:// �Ǘ�6�}�X�^�ꗗ
				// ��ЃR���g���[���}�X�^����\���f�[�^�̎擾
				map = ctrl.setCmpCondition();
				word = String.valueOf(map.get("knrName6"));
				code = ctrl.convertCode(word);
				name_S = ctrl.convertNameS(word);
				name_K = ctrl.convertNameK(word);
				clabel = new String[] { code, name_S, name_K };
				break;
			case REFDialogMasterCtrl.KMK_TK_MST:// �Ȗڑ̌n�}�X�^�ꗗ
				// �Ȗڑ̌n�R�[�h�A�Ȗڑ̌n���́A�Ȗڑ̌n��������
				clabel = new String[] { "C00617", "C00619", "C00620" };
				break;
			case REFDialogMasterCtrl.BMN_MST:// ����}�X�^�ꗗ
				clabel = new String[] { "C00698", "C00724", "C00725" };
				ds.setNumColumns(3);
				break;
			case REFDialogMasterCtrl.EMP_MST:// �Ј��}�X�^�ꗗ
				clabel = new String[] { "C00697", "C00808", "C00809" };
				ds.setNumColumns(3);
				break;
			case REFDialogMasterCtrl.ENV_MST:// ���ݒ�}�X�^�ꗗ
				clabel = new String[] { "C00596", "C00686", "C00160" };

				txtNameForSearch.setVisible(false);

				txtCode.setMaximumSize(new Dimension(127, 20));
				txtCode.setMinimumSize(new Dimension(127, 20));
				txtCode.setPreferredSize(new Dimension(127, 20));

				txtAbbreviationName.setMaximumSize(new Dimension(372, 20));
				txtAbbreviationName.setMinimumSize(new Dimension(372, 20));
				txtAbbreviationName.setPreferredSize(new Dimension(372, 20));

				ssJournal.setCharWidth(0, 10);
				ssJournal.setCharWidth(1, 34);
				columnWidths = new int[] { 10, 34 };
				ssJournal.setColumnHidden(2, true);
				ds.setNumColumns(3);

				break;

			case REFDialogMasterCtrl.BANK_ACCOUNT:// ��s�����}�X�^�ꗗ
				ssJournal.setCharWidth(0, 10);
				ssJournal.setCharWidth(1, 12);
				ssJournal.setCharWidth(2, 23);
				ssJournal.setColumnHidden(3, true);
				columnWidths = new int[] { 10, 12, 23, 10 };
				// clabel = new String[] { code, name_S, name_K, "" };
				// ds.setNumColumns(4);
				//			

				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);
				break;

			case REFDialogMasterCtrl.CUSTOMER_MST:
				ssJournal.setCharWidth(0, 10);
				ssJournal.setCharWidth(1, 12);
				ssJournal.setCharWidth(2, 23);
				ssJournal.setColumnHidden(3, true);
				columnWidths = new int[] { 10, 12, 23, 10 };
				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);
				break;

			case REFDialogMasterCtrl.CURRENCY_MST:
				ssJournal.setCharWidth(0, 10);
				ssJournal.setCharWidth(1, 12);
				ssJournal.setCharWidth(2, 23);
				ssJournal.setColumnHidden(3, true);
				columnWidths = new int[] { 10, 12, 23, 10 };
				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);

			case REFDialogMasterCtrl.DEPARTMENT_MST:
				ssJournal.setCharWidth(0, 10);
				ssJournal.setCharWidth(1, 12);
				ssJournal.setCharWidth(2, 23);
				ssJournal.setColumnHidden(3, true);
				columnWidths = new int[] { 10, 12, 23, 10 };
				clabel = new String[] { code, name_S, name_K };
				ds.setNumColumns(3);
			default:
				break;
		}
		// �����\���f�[�^�̍\�z
		// ds.setNumColumns(3);
		ds.setNumRows(0);

		// ��A�s�\��̃X�^�C���ݒ�
		ssJournal.initSpreadSheet(clabel, columnWidths);

		// �X�v���b�h�C�x���g�̏�����
		ssJournal.addSpreadSheetSelectChange(btnSettle);
		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		ssJournal.setDataSource(ds);

	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	protected void setDataList(Vector cells) {

		// Set the cell data in the data source.
		ds.setCells(cells);
		// �񐔂�ݒ肷��B
		// �x���������Ή� �� 4 : �x�������i�Q���j
		if (Util.avoidNull(kbn).equals("4")) {
			ds.setNumColumns(2);
		} else {
			ds.setNumColumns(3);
		}

		// �s����ݒ肷��B
		ds.setNumRows(cells.size());
		ssJournal.setDataSource(ds);
		// �w��row�Ƀt�H�[�J�X�𓖂Ă�(�擪)
		ssJournal.setRowSelection(0, 0);
		ssJournal.setCurrentCell(0, 0);
	}

	/**
	 * �m��{�^�������̏���
	 */
	protected void btnSettleActionPerformed() {
		isSettle = true;
		ctrl.disposeDialog();
	}

	/**
	 * �L�����Z���{�^�������̏���
	 */
	protected void btnCancelActionPerformed() {
		isSettle = false;
		ctrl.disposeDialog();
	}

	/**
	 * �����{�^�������̏���
	 */
	protected void btnSearchActionPerformed() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ctrl.searchData();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * ���̃Z�b�g
	 * 
	 * @param ctrl1 �R���g���[���N���X
	 * @param param �p�����[�^
	 */
	protected void init(REFDialogMasterCtrl ctrl1, Map param) {
		this.ctrl = ctrl1;
		String top = null;
		kbn = (String) param.get("kbn");
		top = (String) param.get("top");
		initAPComponents();
		initSpreadSheet(param);
		setSize(660, 500);
		// ���̑I�𕶂͏����������Ȃ����͌�ɂ���B
		// �Ȗڈꗗ�p ���W
		if ("8".equals(kbn)) {
			setTitle(top);
			txtCode.setMaxLength(10);
			txtNameForSearch.setMaxLength(20);
			txtAbbreviationName.setMaxLength(40);
			// �x���������S �� �Q��
		} else if ("4".equals(kbn)) {
			setLangMessageID(top);
			txtCode.setMaxLength(10);
			txtNameForSearch.setMaxLength(20);
		} else {
			setLangMessageID(top);
			txtCode.setMaxLength(10);
			txtNameForSearch.setMaxLength(20);
			txtAbbreviationName.setMaxLength(40);
		}
		setTxtKeyListener();
		super.initDialog();
	}

	/**
	 * �X�v���b�h������ (��s�����p�j
	 * 
	 * @param param
	 */
	protected void initSpreadSheet(Map param) {
		// �^�C�g��
		String[] clabel = null;
		int[] columnWidths = null;
		String code = null;
		String name_S = null;
		String name_K = null;

		code = (String) param.get("code");
		name_S = (String) param.get("nameS");
		name_K = (String) param.get("nameK");
		// �\���̑I�𕶂͎c���i��ŏ����ۂ����肷��j
		if ("4".equals(kbn)) {
			columnWidths = new int[] { 10, 35 };
			clabel = new String[] { code, name_S };
			ssJournal.setCharWidth(0, 10);
			ssJournal.setCharWidth(1, 35);
			ds.setNumColumns(2);
		} else if ("9".equals(kbn)) {
			ssJournal.setCharWidth(0, 10);
			ssJournal.setCharWidth(1, 12);
			ssJournal.setCharWidth(2, 23);
			ssJournal.setColumnHidden(3, true);
			ssJournal.setColumnHidden(4, true);
			columnWidths = new int[] { 10, 12, 23 };
			clabel = new String[] { code, name_S, name_K };
			ds.setNumColumns(5);
		} else if ("10".equals(kbn)) {
			ssJournal.setCharWidth(0, 10);
			ssJournal.setCharWidth(1, 12);
			ssJournal.setCharWidth(2, 23);
			ssJournal.setColumnHidden(3, true);
			ssJournal.setColumnHidden(4, true);
			ssJournal.setColumnHidden(5, true);
			ssJournal.setColumnHidden(6, true);
			ssJournal.setColumnHidden(7, true);
			ssJournal.setColumnHidden(8, true);
			columnWidths = new int[] { 10, 12, 23, 10, 10, 10, 10, 10, 10 };
			clabel = new String[] { code, name_S, name_K, "", "", "", "", "", "" };
			ds.setNumColumns(9);
		} else {
			// �Z����
			ssJournal.setCharWidth(0, 10);
			ssJournal.setCharWidth(1, 12);
			ssJournal.setCharWidth(2, 23);
			ssJournal.setColumnHidden(3, true);
			columnWidths = new int[] { 10, 12, 23, 10 };
			clabel = new String[] { code, name_S, name_K, "" };
			ds.setNumColumns(4);
		}
		// �����\���f�[�^�̍\�z

		ds.setNumRows(0);

		// ��A�s�\��̃X�^�C���ݒ�
		ssJournal.initSpreadSheet(clabel, columnWidths);
		// �X�v���b�h�C�x���g�̏�����
		ssJournal.addSpreadSheetSelectChange(btnSettle);
		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		ssJournal.setDataSource(ds);

	}

	protected void initAPComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlDetial = new TPanel();
		pnlJournal = new TPanel();
		ssJournal = new TTable();
		pnlText = new TPanel();
		txtCode = new TTextField();
		txtNameForSearch = new TTextField();
		txtAbbreviationName = new TTextField();
		if ("4".equals(kbn)) {
			txtAbbreviationName.setVisible(false);
		}
		pnlButton = new TPanel();
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnCancel = new TButton();

		setLayout(new GridBagLayout());

		pnlDetial.setLayout(new GridBagLayout());

		pnlDetial.setMaximumSize(new Dimension(640, 380));
		pnlDetial.setMinimumSize(new Dimension(640, 380));
		pnlDetial.setPreferredSize(new Dimension(640, 380));
		pnlJournal.setLayout(new javax.swing.BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(620, 300));
		pnlJournal.setMinimumSize(new Dimension(620, 300));
		pnlJournal.setPreferredSize(new Dimension(620, 300));
		ssJournal.setTabControlNo(7);
		ssJournal.setEnterToButton(true);
		ssJournal.setSort(true);
		pnlJournal.add(ssJournal);

		pnlDetial.add(pnlJournal, new GridBagConstraints());

		pnlText.setLayout(new GridBagLayout());

		pnlText.setMaximumSize(new Dimension(640, 30));
		pnlText.setMinimumSize(new Dimension(640, 30));
		pnlText.setPreferredSize(new Dimension(640, 30));
		txtCode.setMaximumSize(new Dimension(126, 20));
		txtCode.setMinimumSize(new Dimension(126, 20));
		txtCode.setPreferredSize(new Dimension(126, 20));
		txtCode.setImeMode(false);
		txtCode.setTabControlNo(1);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlText.add(txtCode, gridBagConstraints);

		if ("4".equals(kbn)) {
			txtNameForSearch.setMaximumSize(new Dimension(421, 20));
			txtNameForSearch.setMinimumSize(new Dimension(421, 20));
			txtNameForSearch.setPreferredSize(new Dimension(421, 20));
		} else {
			txtNameForSearch.setMaximumSize(new Dimension(281, 20));
			txtNameForSearch.setMinimumSize(new Dimension(281, 20));
			txtNameForSearch.setPreferredSize(new Dimension(281, 20));
		}
		txtNameForSearch.setProhibitionWords("��", "�Q");
		txtNameForSearch.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlText.add(txtNameForSearch, gridBagConstraints);

		if (!"4".equals(kbn)) {
			txtAbbreviationName.setMaximumSize(new Dimension(151, 20));
			txtAbbreviationName.setMinimumSize(new Dimension(151, 20));
			txtAbbreviationName.setPreferredSize(new Dimension(151, 20));
			txtAbbreviationName.setTabControlNo(2);
			txtAbbreviationName.setProhibitionWords("��", "�Q");
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			pnlText.add(txtAbbreviationName, gridBagConstraints);
		}
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		pnlDetial.add(pnlText, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(500, 35));
		pnlButton.setMinimumSize(new Dimension(500, 35));
		pnlButton.setPreferredSize(new Dimension(500, 35));
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
		btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSearchActionPerformed();
			}
		});

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		btnCancel.setLangMessageID("C00405");
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		btnCancel.setMaximumSize(new Dimension(110, 25));
		btnCancel.setMinimumSize(new Dimension(110, 25));
		btnCancel.setPreferredSize(new Dimension(110, 25));
		btnCancel.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 50, 0, 40);
		pnlButton.add(btnCancel, gridBagConstraints);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnCancelActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		pnlDetial.add(pnlButton, gridBagConstraints);

		add(pnlDetial, new GridBagConstraints());

	}// </editor-fold>//GEN-END:initComponents

	protected TButton btnCancel;

	protected TButton btnSearch;

	protected TButton btnSettle;

	protected TPanel pnlButton;

	protected TPanel pnlDetial;

	protected TPanel pnlJournal;

	protected TPanel pnlText;

	protected TTable ssJournal;

	protected TTextField txtAbbreviationName;

	protected TTextField txtCode;

	protected TTextField txtNameForSearch;

	String word;

}
