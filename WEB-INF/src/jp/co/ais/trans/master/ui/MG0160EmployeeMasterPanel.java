package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �Ј��}�X�^
 * 
 * @author wangjing
 */
public class MG0160EmployeeMasterPanel extends TPanelBusiness {

	/** �V���A��UID */
	protected static final long serialVersionUID = 6975993877695662983L;

	/** �R���g���[���N���X */
	protected MG0160EmployeeMasterPanelCtrl ctrl;

	/** �X�v���b�h�V�[�gDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * Creates new form MG0160EmployeeMasterPanel
	 * 
	 * @param ctrl
	 */
	public MG0160EmployeeMasterPanel(MG0160EmployeeMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// ��ʂ̏�����
		initComponents();
		// �X�v���b�h�̏�����
		initSpreadSheet();

		// ** messageID�ϊ�, tab���o�^�̂��߁AinitComponents()�̌�ɕK���ĂԂ��� */
		super.initPanel();

		this.btnEdit.setEnabled(false);
		this.btnCopy.setEnabled(false);
		this.btnDelete.setEnabled(false);
	}

	protected void initSpreadSheet() {
		// SpreadSheet �� init ����
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00697", "C00807", "C00808", "C00809", "C00811",
				"C00812", "C00813", "C00471", "C01068", "C00810", "C00055", "C00261" };
		// �X�v���b�h�񕝂̏�����
		int[] columnWidths = new int[] { 0, 10, 40, 20, 40, 8, 8, 7, 9, 30, 10, 6, 6, 0, 0, 0, 0, 0, 0, 0 };
		// ��A�s�\��̃X�^�C���ݒ�
		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		ssJournal.addSpreadSheetSelectChange(btnEdit);

		// Scroll�ʒu�ݒ�
		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// �����\���f�[�^�̍\�z
		ds.setNumColumns(13);
		ds.setNumRows(0);

		ssJournal.setDataSource(ds);
	}

	/**
	 * �t���[���擾
	 */
	public Frame getParentFrame() {
		return super.getParentFrame();

	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssJournal.setDataSource(ds);

		// ���l���E�񂹂���
		CellStyleModel defaultStyle = ssJournal.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 11, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 12, centerStyle);

		JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
		leftStyle.setHorizontalAlignment(CellStyleModel.LEFT);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 1, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 2, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 3, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 4, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 5, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 6, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 7, leftStyle);

		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);

	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnEdit = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		lblEnd = new TLabel();
		ctrlBeginEmployee = new TButtonField();
		ctrlEndEmployee = new TButtonField();
		lblBegin = new TLabel();
		pnlJournal = new TPanel();
		ssJournal = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		/**
		 * �V�K
		 * 
		 * @param evt
		 */
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);
		btnNew.addActionListener(new ActionListener() {

			// �V�K�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});
		/**
		 * �����{�^��
		 */
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
		btnSearch.addActionListener(new ActionListener() {

			// �����{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.find();
			}
		});
		/**
		 * �ҏW�{�^��
		 */
		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);
		btnEdit.addActionListener(new ActionListener() {

			// �ҏW�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});

		/**
		 * ���ʃ{�^��
		 */
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);
		btnCopy.addActionListener(new ActionListener() {

			// ���ʃ{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});

		/**
		 * �폜�{�^��
		 */
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);
		btnDelete.addActionListener(new ActionListener() {

			// �폜�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		/**
		 * ���X�g�o�̓{�^��
		 */
		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(9);
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.addActionListener(new ActionListener() {

			// ���X�g�o�̓{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.outptExcel();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(720, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(720, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(720, 80));

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		ctrlBeginEmployee.setButtonSize(85);
		ctrlBeginEmployee.setFieldSize(120);
		ctrlBeginEmployee.setLangMessageID("C01499");
		ctrlBeginEmployee.setMaxLength(10);
		ctrlBeginEmployee.setNoticeSize(410);
		ctrlBeginEmployee.setTabControlNo(1);
		ctrlBeginEmployee.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginEmployee, gridBagConstraints);

		ctrlEndEmployee.setButtonSize(85);
		ctrlEndEmployee.setFieldSize(120);
		ctrlEndEmployee.setLangMessageID("C01499");
		ctrlEndEmployee.setMaxLength(10);
		ctrlEndEmployee.setNoticeSize(410);
		ctrlEndEmployee.setTabControlNo(2);
		ctrlEndEmployee.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndEmployee, gridBagConstraints);

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(pnlHeader, gridBagConstraints);

		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 460));
		pnlJournal.setMinimumSize(new Dimension(780, 460));
		pnlJournal.setPreferredSize(new Dimension(780, 460));
		ssJournal.setTabControlNo(3);
		pnlJournal.add(ssJournal);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlJournal, gridBagConstraints);
	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TPanel pnlHeader;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TButtonField ctrlBeginEmployee;

	TButtonField ctrlEndEmployee;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlJournal;

	TPanel pnlRangeSpecification;

	TTable ssJournal;

}
