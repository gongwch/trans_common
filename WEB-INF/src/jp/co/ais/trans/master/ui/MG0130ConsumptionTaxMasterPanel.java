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
 * ����Ń}�X�^���
 * 
 * @author ISFnet China
 */
public class MG0130ConsumptionTaxMasterPanel extends TPanelBusiness {

	/** �V���A��UID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** �R���g���[���N���X */
	private MG0130ConsumptionTaxMasterPanelCtrl ctrl;

	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param ctrl �R���g���[���N���X
	 */
	MG0130ConsumptionTaxMasterPanel(MG0130ConsumptionTaxMasterPanelCtrl ctrl) {
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

	private void initSpreadSheet() {
		// SpreadSheet �� init ����
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00573", "C00774", "C00775", "C00828", "C01283",
				"C00777", "C02045", "C00055", "C00261" };
		// �X�v���b�h�񕝂̏�����
		int[] columnWidths = new int[] { 0, 8, 27, 20, 27, 7, 7, 14, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// ��A�s�\��̃X�^�C���ݒ�
		ssConsumptionTax.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// �X�v���b�h�����̏�����
		ssConsumptionTax.addSpreadSheetSelectChange(btnEdit);

		// Scroll�ʒu�ݒ�
		ssConsumptionTax.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssConsumptionTax.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		ds.setNumColumns(10);
		ds.setNumRows(0);

		ssConsumptionTax.setDataSource(ds);
	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssConsumptionTax.setDataSource(ds);
		// ���l���E�񂹂���
		CellStyleModel defaultStyle = ssConsumptionTax.getDefaultCellStyle();
		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssConsumptionTax.setCellStyle(JCTableEnum.ALLCELLS, 6, rightStyle);
		ssConsumptionTax.setCellStyle(JCTableEnum.ALLCELLS, 7, rightStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssConsumptionTax.setCellStyle(JCTableEnum.ALLCELLS, 5, centerStyle);
		ssConsumptionTax.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssConsumptionTax.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);

		this.btnEdit.setEnabled(cells.size() > 0);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
	}

	private void initComponents() {
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
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		ctrlBeginConsumptionTax = new TButtonField();
		ctrlEndConsumptionTax = new TButtonField();
		pnlDetail = new TPanel();
		ssConsumptionTax = new TTable();

		setLayout(new GridBagLayout());

		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(4);

		btnNew.addActionListener(new ActionListener() {

			// �V�K�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);

		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.addActionListener(new ActionListener() {

			// �����{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.find();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);

		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(6);
		btnEdit.setPreferredSize(new Dimension(110, 25));
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
		btnCopy.setTabControlNo(7);
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
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
		btnDelete.setTabControlNo(8);
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.addActionListener(new ActionListener() {

			// �폜�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);

		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(9);
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.outptExcel();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		// ���E��
		JSeparator sep = new JSeparator();
		TGuiUtil.setComponentSize(sep, 0, 3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		add(sep, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(675, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(675, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(675, 80));

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		ctrlBeginConsumptionTax.setButtonSize(85);
		ctrlBeginConsumptionTax.setFieldSize(50);
		ctrlBeginConsumptionTax.setLangMessageID("C01171");
		ctrlBeginConsumptionTax.setMaxLength(2);
		ctrlBeginConsumptionTax.setNoticeSize(435);
		ctrlBeginConsumptionTax.setTabControlNo(1);
		ctrlBeginConsumptionTax.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginConsumptionTax, gridBagConstraints);

		ctrlEndConsumptionTax.setButtonSize(85);
		ctrlEndConsumptionTax.setFieldSize(50);
		ctrlEndConsumptionTax.setLangMessageID("C01171");
		ctrlEndConsumptionTax.setMaxLength(2);
		ctrlEndConsumptionTax.setNoticeSize(435);
		ctrlEndConsumptionTax.setTabControlNo(2);
		ctrlEndConsumptionTax.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndConsumptionTax, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 45);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));

		pnlDetail.setMaximumSize(new Dimension(780, 460));
		pnlDetail.setMinimumSize(new Dimension(780, 460));
		pnlDetail.setPreferredSize(new Dimension(780, 460));
		ssConsumptionTax.setTabControlNo(3);
		pnlDetail.add(ssConsumptionTax);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TButtonField ctrlBeginConsumptionTax;

	TButtonField ctrlEndConsumptionTax;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlRangeSpecification;

	TTable ssConsumptionTax;

}
