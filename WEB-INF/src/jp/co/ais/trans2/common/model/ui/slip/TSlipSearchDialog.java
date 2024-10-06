package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �`�[����
 */
public class TSlipSearchDialog extends TDialog {

	/** �e�[�u���񖼗񋓑� */
	public enum SC {
		/** ��f�[�^ */
		bean,

		/** �`�[�ԍ� */
		slipNo,

		/** �`�[���t */
		slipDate,

		/** ����R�[�h */
		depCode,

		/** ���嗪�� */
		depNames,

		/** �E�v */
		remarks,

		/** �X�V�敪 */
		updDivision;
	}

	/** �_�C�A���O �T�C�Y */
	public int dialogWidth = getDialogWidth();

	/** �ꗗ */
	public TTable tbl;

	/** ���� �`�[�ԍ� */
	public TTextField txtSlipNo;

	/** ���� �`�[���t */
	public THalfwayDateField txtSlipDate;

	/** ���� ����R�[�h */
	public TTextField txtDepCode;

	/** ���� ���嗪�� */
	public TTextField txtDepNames;

	/** ���� �`�[�E�v */
	public TTextField txtSlipRemarks;

	/** ���� �X�V�敪 */
	public TComboBox cmbUpdDivision;

	/** �����{�^�� */
	public TButton btnSearch;

	/** �m��{�^�� */
	public TButton btnEnter;

	/** ����{�^�� */
	public TButton btnCancel;

	/** ���ʃ��[�h�I���p�l�� */
	public TRadioPanel ctrlCopyMode;

	/** ���C���p�l�� */
	protected TPanel pnlMain;

	/** �X�v���b�h�̈� */
	protected TPanel pnlSS;

	/** �t�B�[���h�̈� */
	protected TPanel pnlField;

	/** �{�^���̈� */
	protected TPanel pnlButton;

	/** true���A���������ɕ���ǉ� */
	protected static boolean useDepartmentSearch = ClientConfig.isFlagOn("trans.slip.use.search.depcode");

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel �p�l��
	 */
	public TSlipSearchDialog(TPanel panel) {
		this(panel.getParentFrame());
	}

	/**
	 * �_�C�A���O�̑傫��
	 * 
	 * @return �T�C�Y
	 */
	public int getDialogWidth() {
		return 850;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�t���[��
	 */
	public TSlipSearchDialog(Frame parent) {
		super(parent, true);

		initComponents();
		initSpreadSheet();
		allocateComponents();
		setTabIndex();

		initDialog();
	}

	/**
	 * �R���|�[�l���g�쐬
	 */
	public void initComponents() {
		this.setLangMessageID("C00391");// �`�[�ꗗ
		this.setResizable(true);

		pnlMain = new TPanel();
		pnlSS = new TPanel();
		pnlField = new TPanel();
		pnlButton = new TPanel();

		tbl = new TTable();

		txtSlipNo = new TTextField();
		txtSlipDate = new THalfwayDateField();
		txtDepCode = new TTextField();
		txtDepNames = new TTextField();
		txtSlipRemarks = new TTextField();
		cmbUpdDivision = new TComboBox();

		btnSearch = new TButton();
		btnEnter = new TButton();
		btnCancel = new TButton();

		ctrlCopyMode = new TRadioPanel();
	}

	/**
	 * �X�v���b�h�V�[�g�ݒ�
	 */
	public void initSpreadSheet() {
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.slipNo, "C00605", 150);// �`�[�ԍ�
		tbl.addColumn(SC.slipDate, "C00599", 80, SwingConstants.CENTER);// �`�[���t
		if (useDepartmentSearch) {
			tbl.addColumn(SC.depCode, "C00698", 80);// ����R�[�h
			tbl.addColumn(SC.depNames, "C00724", 100);// ���嗪��
		} else {
			tbl.addColumn(SC.depCode, "C00698", 0, false);
			tbl.addColumn(SC.depNames, "C00724", 0, false);
		}
		tbl.addColumn(SC.remarks, "C00569", dialogWidth - 420);// �`�[�E�v
		tbl.addColumn(SC.updDivision, "C01069", 100, SwingConstants.CENTER);// �X�V�敪

		tbl.setRowColumnWidth(38);
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	public void allocateComponents() {

		int depX = 0;

		this.setSize(dialogWidth, 600);
		this.setLayout(new BorderLayout());

		pnlMain.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0d;

		// �ꗗ
		pnlSS.setLayout(new BorderLayout());
		TGuiUtil.setComponentSize(pnlSS, 0, 0);

		tbl.addSpreadSheetSelectChange(btnEnter);
		tbl.setEnterToButton(true);
		tbl.getTableHeader().setReorderingAllowed(false);
		TGuiUtil.setComponentSize(tbl, new Dimension(0, 0));
		pnlSS.add(tbl, BorderLayout.CENTER);

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		pnlMain.add(pnlSS, gc);

		// ��������
		pnlField.setLayout(null);
		TGuiUtil.setComponentSize(pnlField, 0, 30);

		int x = tbl.getRowColumnWidth();

		// �`�[�ԍ�
		txtSlipNo.setImeMode(false);
		txtSlipNo.setMaxLength(20);
		TGuiUtil.setComponentSize(txtSlipNo, 150, 20);
		txtSlipNo.setLocation(x, 0);
		pnlField.add(txtSlipNo);

		// �`�[���t
		TGuiUtil.setComponentSize(txtSlipDate, 80, 20);
		txtSlipDate.setLocation(txtSlipNo.getX() + txtSlipNo.getWidth(), 0);
		pnlField.add(txtSlipDate);

		if (useDepartmentSearch) {
			depX = 180;
			this.setSize(dialogWidth + depX, 600);

			// ����R�[�h
			txtDepCode.setImeMode(false);
			txtDepCode.setMaxLength(10);
			TGuiUtil.setComponentSize(txtDepCode, 80, 20);
			txtDepCode.setLocation(txtSlipDate.getX() + txtSlipDate.getWidth(), 0);
			pnlField.add(txtDepCode);

			// ���嗪��
			txtDepNames.setMaxLength(40);
			TGuiUtil.setComponentSize(txtDepNames, 100, 20);
			txtDepNames.setLocation(txtDepCode.getX() + txtDepCode.getWidth(), 0);
			pnlField.add(txtDepNames);
		}

		// �E�v
		TGuiUtil.setComponentSize(txtSlipRemarks, dialogWidth - 420, 20);
		txtSlipRemarks.setLocation(txtSlipDate.getX() + txtSlipDate.getWidth() + depX, 0);
		pnlField.add(txtSlipRemarks);

		// �X�V�敪
		TGuiUtil.setComponentSize(cmbUpdDivision, 100, 20);
		cmbUpdDivision.setLocation(txtSlipRemarks.getX() + txtSlipRemarks.getWidth(), 0);
		pnlField.add(cmbUpdDivision);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.weighty = 0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 10, 0, 10);
		pnlMain.add(pnlField, gc);

		// �{�^��
		pnlButton.setLayout(null);
		TGuiUtil.setComponentSize(pnlButton, 0, 45);

		// �����{�^��
		btnSearch.setLangMessageID("C00155");// ����
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		TGuiUtil.setComponentSize(btnSearch, 25, 100);
		btnSearch.setLocation(0, 10);
		pnlButton.add(btnSearch);

		// �m��{�^��
		btnEnter.setLangMessageID("C01019");// �m��
		btnEnter.setShortcutKey(KeyEvent.VK_F8);
		btnEnter.setEnabled(false);
		TGuiUtil.setComponentSize(btnEnter, 25, 100);
		btnEnter.setLocation(btnSearch.getX() + btnSearch.getWidth() + 2, 10);
		pnlButton.add(btnEnter);

		// �L�����Z���{�^��
		btnCancel.setLangMessageID("C00405");// ���
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		TGuiUtil.setComponentSize(btnCancel, 25, 100);
		btnCancel.setLocation(dialogWidth - btnCancel.getWidth() - 30, 10);
		pnlButton.add(btnCancel);

		// ���ʃ��[�h�I���p�l��
		ctrlCopyMode.setSize(ctrlCopyMode.getWidth(), ctrlCopyMode.getHeight() + 1);
		ctrlCopyMode.setLangMessageID("C00459");// ����
		ctrlCopyMode.addRadioButton(getWord("C10758"), 80);// ���`����
		ctrlCopyMode.addRadioButton(getWord("C10759"), 80);// �ԓ`����
		ctrlCopyMode.addRadioButton(getWord("C10760"), 80);// �t�`����
		ctrlCopyMode.setSelectON(0);
		ctrlCopyMode.setVisible(false);
		ctrlCopyMode.setLocation(btnEnter.getX() + btnEnter.getWidth() + 30, -1);
		pnlButton.add(ctrlCopyMode);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.weighty = 0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0, 10, 0, 10);
		pnlMain.add(pnlButton, gc);

		add(pnlMain);
	}

	/**
	 * �^�u���ݒ�
	 */
	public void setTabIndex() {
		int i = 1;

		txtSlipNo.setTabControlNo(i++);
		txtSlipDate.setTabControlNo(i++);
		txtDepCode.setTabControlNo(i++);
		txtDepNames.setTabControlNo(i++);
		txtSlipRemarks.setTabControlNo(i++);
		cmbUpdDivision.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnEnter.setTabControlNo(i++);
		ctrlCopyMode.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);

		tbl.setTabControlNo(i++);
	}
}
