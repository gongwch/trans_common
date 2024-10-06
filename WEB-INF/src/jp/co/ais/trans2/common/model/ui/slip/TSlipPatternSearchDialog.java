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
 * �p�^�[������
 */
public class TSlipPatternSearchDialog extends TDialog {

	/** �e�[�u���񖼗񋓑� */
	public enum SC {
		/** ��f�[�^ */
		bean,

		/** �p�^�[���ԍ� */
		patternNo,

		/** �o�^���t */
		entryDate,

		/** ����R�[�h */
		depCode,

		/** ���嗪�� */
		depNames,

		/** �E�v */
		remarks;
	}

	/** �_�C�A���O �T�C�Y */
	public int dialogWidth = getDialogWidth();

	/** �ꗗ */
	public TTable tbl;

	/** ���� �`�[�ԍ� */
	public TTextField txtSlipNo;

	/** ���� �o�^���t */
	public THalfwayDateField txtEntryDate;

	/** ���� ����R�[�h */
	public TTextField txtDepCode;

	/** ���� ���嗪�� */
	public TTextField txtDepNames;

	/** ���� �`�[�E�v */
	public TTextField txtSlipRemarks;

	/** �����{�^�� */
	public TButton btnSearch;

	/** �m��{�^�� */
	public TButton btnEnter;

	/** ����{�^�� */
	public TButton btnCancel;

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
	public TSlipPatternSearchDialog(TPanel panel) {
		this(panel.getParentFrame());
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�t���[��
	 */
	public TSlipPatternSearchDialog(Frame parent) {
		super(parent, true);

		initComponents();
		initSpreadSheet();
		allocateComponents();
		setTabIndex();

		initDialog();
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
	 * �R���|�[�l���g�쐬
	 */
	public void initComponents() {
		this.setLangMessageID("C01685");// �d��p�^�[���ꗗ
		this.setResizable(true);

		pnlMain = new TPanel();
		pnlSS = new TPanel();
		pnlField = new TPanel();
		pnlButton = new TPanel();

		tbl = new TTable();

		txtSlipNo = new TTextField();
		txtEntryDate = new THalfwayDateField();
		txtDepCode = new TTextField();
		txtDepNames = new TTextField();
		txtSlipRemarks = new TTextField();

		btnSearch = new TButton();
		btnEnter = new TButton();
		btnCancel = new TButton();
	}

	/**
	 * �X�v���b�h�V�[�g�ݒ�
	 */
	public void initSpreadSheet() {
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.patternNo, "C00987", 150);// �p�^�[���ԍ�
		tbl.addColumn(SC.entryDate, "C10724", 80, SwingConstants.CENTER);// �o�^���t
		if (useDepartmentSearch) {
			tbl.addColumn(SC.depCode, "C00698", 80);// ����R�[�h
			tbl.addColumn(SC.depNames, "C00724", 100);// ���嗪��
		} else {
			tbl.addColumn(SC.depCode, "C00698", 0, false);
			tbl.addColumn(SC.depNames, "C00724", 0, false);
		}
		tbl.addColumn(SC.remarks, "C00569", dialogWidth - 320);// �`�[�E�v

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
		TGuiUtil.setComponentSize(txtEntryDate, 80, 20);
		txtEntryDate.setLocation(txtSlipNo.getX() + txtSlipNo.getWidth(), 0);
		pnlField.add(txtEntryDate);

		if (useDepartmentSearch) {
			depX = 180;

			this.setSize(dialogWidth + depX, 600);

			// ����R�[�h
			txtDepCode.setImeMode(false);
			txtDepCode.setMaxLength(10);
			TGuiUtil.setComponentSize(txtDepCode, 80, 20);
			txtDepCode.setLocation(txtEntryDate.getX() + txtEntryDate.getWidth(), 0);
			pnlField.add(txtDepCode);

			// ���嗪��
			txtDepNames.setMaxLength(40);
			TGuiUtil.setComponentSize(txtDepNames, 100, 20);
			txtDepNames.setLocation(txtDepCode.getX() + txtDepCode.getWidth(), 0);
			pnlField.add(txtDepNames);
		}

		// �E�v
		TGuiUtil.setComponentSize(txtSlipRemarks, dialogWidth - 320, 20);
		txtSlipRemarks.setLocation(txtEntryDate.getX() + txtEntryDate.getWidth() + depX, 0);
		pnlField.add(txtSlipRemarks);

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
		txtEntryDate.setTabControlNo(i++);
		txtDepCode.setTabControlNo(i++);
		txtDepNames.setTabControlNo(i++);
		txtSlipRemarks.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnEnter.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);

		tbl.setTabControlNo(i++);
	}
}
