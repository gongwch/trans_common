package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ��ЃR���g���[���}�X�^
 * 
 * @author liuchengcheng
 */
public class MG0030CompanyControlMasterPanel extends TPanelBusiness {

	/** �R���g���[���N���X */
	protected MG0030CompanyControlMasterPanelCtrl ctrl;

	/** �X�v���b�h�V�[�gDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param ctrl �R���g���[���N���X
	 */
	MG0030CompanyControlMasterPanel(MG0030CompanyControlMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// ��ʂ̏�����
		initComponents();
		// �X�v���b�h�̏�����
		initSpreadSheet();

		super.initPanel();

	}

	protected void initSpreadSheet() {
		// SpreadSheet �� init ����
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936",
				"C00937", "C00938", "C00939", "C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707",
				"C00708", "C00943", "C00944", "C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714",
				"C00715", "C00224", "C01248", "C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083" };
		// �X�v���b�h�񕝂̏�����
		int[] columnWidths = new int[] { 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 6, 9, 9, 9, 9, 9, 9, 4, 8, 8,
				8, 11, 10, 10, 10, 10, 8, 10, 12, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };
		// ��A�s�\��̃X�^�C���ݒ�
		ssCompanyCodeRoleList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// �X�v���b�h�C�x���g�̏�����
		ssCompanyCodeRoleList.addSpreadSheetSelectChange(btnEdit);

		// Scroll�ʒu�ݒ�
		ssCompanyCodeRoleList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssCompanyCodeRoleList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// �����\���f�[�^�̍\�z
		ds.setNumColumns(36);
		ds.setNumRows(0);

		ssCompanyCodeRoleList.setDataSource(ds);

	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	void setDataList(Vector cells) {
		// ���l���E�񂹂���
		CellStyleModel defaultStyle = ssCompanyCodeRoleList.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 3, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 5, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 6, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 7, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 10, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 17, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 18, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 19, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 28, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 32, centerStyle);

		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 23, rightStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 27, rightStyle);

		this.btnListOutput.setEnabled(cells.size() > 0);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssCompanyCodeRoleList.setDataSource(ds);
	}

	/** �V���A��UID */
	private static final long serialVersionUID = 6975993877695662983L;

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnEdit = new TButton();
		btnCopy = new TButton();
		btnDelete = new TButton();
		btnListOutput = new TButton();
		pnlDetail = new TPanel();
		ssCompanyCodeRoleList = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(2);
		btnEdit.addActionListener(new ActionListener() {

			// �ҏW�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(3);
		btnCopy.addActionListener(new ActionListener() {

			// ���ʃ{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(4);
		btnDelete.addActionListener(new ActionListener() {

			// �폜�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);

		btnListOutput.setLangMessageID("C03084");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(120, 25));
		btnListOutput.setMinimumSize(new Dimension(120, 25));
		btnListOutput.setPreferredSize(new Dimension(120, 25));
		btnListOutput.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);
		btnListOutput.addActionListener(new ActionListener() {

			// ���X�g�o�̓{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.outptExcel();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));

		pnlDetail.setMaximumSize(new Dimension(780, 545));
		pnlDetail.setMinimumSize(new Dimension(780, 545));
		pnlDetail.setPreferredSize(new Dimension(780, 545));

		ssCompanyCodeRoleList.addTraverseCellListener(new JCTraverseCellAdapter() {

			public void afterTraverseCell(JCTraverseCellEvent ev) {
				if (ev.getRow() == ev.getNextRow()) {
					return;
				}
				ctrl.isDeleteAbled();
			}
		});
		ssCompanyCodeRoleList.addSortListener(new JCSortAdapter() {

			public void sort(JCSortEvent ev) {
				ctrl.isDeleteAbled();
			}
		});

		pnlDetail.add(ssCompanyCodeRoleList);
		ssCompanyCodeRoleList.setTabControlNo(1);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

	}

	TButton btnCopy;

	TButton btnDelete;

	TButton btnEdit;

	TButton btnListOutput;

	TPanel pnlButton;

	TPanel pnlDetail;

	TTable ssCompanyCodeRoleList;

}
