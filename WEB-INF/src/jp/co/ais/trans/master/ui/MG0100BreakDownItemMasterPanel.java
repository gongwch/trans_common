package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * ����Ȗڃ}�X�^���
 * 
 * @author zhangzhenxing
 */
public class MG0100BreakDownItemMasterPanel extends TPanelBusiness {

	/** �V���A��UID */
	protected static final long serialVersionUID = 1131220395709048332L;

	/** �R���g���[���N���X */
	protected MG0100BreakDownItemMasterPanelCtrl ctrl;

	/** �X�v���b�h�V�[�gDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/** �Ȗ�field Enter������FLAG */
	boolean lostFocusFlag = false;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param ctrl �R���g���[���N���X
	 */
	MG0100BreakDownItemMasterPanel(MG0100BreakDownItemMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;

		// ��ʂ̏�����
		initComponents();

		// �X�v���b�h�̏�����
		initSpreadSheet();

		super.initPanel();

		this.btnEdit.setEnabled(false);
		this.btnCopy.setEnabled(false);
		this.btnDelete.setEnabled(false);

	}

	protected void initSpreadSheet() {
		// SpreadSheet �� init ����
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00572", "C00602", "C00603", "C00702", "C01594",
				"C01593", "C00573", "C01272", "C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102",
				"C01094", "C01223", "C01301", "C01134", "C01284", "C01120", "C01026", "C01028", "C01030", "C01032",
				"C01034", "C01036", "C01288", "C01289", "C01290", "C01282", "C01088", "C00055", "C00261" };

		// �X�v���b�h�񕝂̏�����
		int[] columnWidths = new int[] { 0, 10, 10, 10, 40, 20, 40, 8, 11, 11, 11, 13, 12, 13, 13, 13, 13, 10, 10, 10,
				10, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 11, 11, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		ssBreakDownItem.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// �X�v���b�h�C�x���g�̏�����
		ssBreakDownItem.addSpreadSheetSelectChange(btnEdit);

		// Scroll�ʒu�ݒ�
		ssBreakDownItem.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssBreakDownItem.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// �����\���f�[�^�̍\�z
		ds.setNumColumns(35);
		ds.setNumRows(0);

		ssBreakDownItem.setDataSource(ds);
	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	void setDataList(Vector cells) {
		// ���l���E�񂹂���
		CellStyleModel defaultStyle = ssBreakDownItem.getDefaultCellStyle();
		JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
		leftStyle.setHorizontalAlignment(CellStyleModel.LEFT);
		ssBreakDownItem.setCellStyle(JCTableEnum.ALLCELLS, 5, leftStyle);
		ssBreakDownItem.setCellStyle(JCTableEnum.ALLCELLS, 6, leftStyle);
		ssBreakDownItem.setCellStyle(JCTableEnum.ALLCELLS, 7, leftStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssBreakDownItem.setCellStyle(JCTableEnum.ALLCELLS, 33, centerStyle);
		ssBreakDownItem.setCellStyle(JCTableEnum.ALLCELLS, 34, centerStyle);

		ds.setCells(cells);
		ds.setNumRows(cells.size());

		ssBreakDownItem.setDataSource(ds);

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
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		ctrlBeginBreakDownItem = new TBreakDownItemField();
		ctrlEndBreakDownItem = new TBreakDownItemField();
		ctrlItem = new TItemField();
		ctrlSubItem = new TSubItemField();
		pnlDetail = new TPanel();
		ssBreakDownItem = new TTable();

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
		btnNew.setTabControlNo(6);
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
		btnSearch.setTabControlNo(7);
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
		btnEdit.setTabControlNo(8);
		btnEdit.setMinimumSize(new Dimension(110, 25));
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
		btnCopy.setTabControlNo(9);
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
		btnDelete.setTabControlNo(10);
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
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// ���X�g�o�̓{�^��������
				ctrl.outptExcel();
			}
		});
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(11);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());
		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(780, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(780, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(780, 80));

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 10, 10, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		ctrlBeginBreakDownItem.setButtonSize(85);
		ctrlBeginBreakDownItem.setFieldSize(75);
		ctrlBeginBreakDownItem.setLangMessageID("C01263");
		ctrlBeginBreakDownItem.setTabControlNo(3);
		ctrlBeginBreakDownItem.setImeMode(false);
		ctrlBeginBreakDownItem.setChekcMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlRangeSpecification.add(ctrlBeginBreakDownItem, gridBagConstraints);

		ctrlEndBreakDownItem.setButtonSize(85);
		ctrlEndBreakDownItem.setFieldSize(75);
		ctrlEndBreakDownItem.setLangMessageID("C01263");
		ctrlEndBreakDownItem.setTabControlNo(4);
		ctrlEndBreakDownItem.setImeMode(false);
		ctrlEndBreakDownItem.setChekcMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlRangeSpecification.add(ctrlEndBreakDownItem, gridBagConstraints);

		ctrlItem.setButtonSize(85);
		ctrlItem.setFieldSize(75);
		ctrlItem.setLangMessageID("C01006");
		ctrlItem.setTabControlNo(1);
		ctrlItem.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlRangeSpecification.add(ctrlItem, gridBagConstraints);

		ctrlSubItem.setButtonSize(85);
		ctrlSubItem.setFieldSize(75);
		ctrlSubItem.setLangMessageID("C01313");
		ctrlSubItem.setTabControlNo(2);
		ctrlSubItem.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlRangeSpecification.add(ctrlSubItem, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));

		pnlDetail.setMaximumSize(new Dimension(780, 460));
		pnlDetail.setMinimumSize(new Dimension(780, 460));
		pnlDetail.setPreferredSize(new Dimension(780, 460));
		ssBreakDownItem.setTabControlNo(5);
		pnlDetail.add(ssBreakDownItem);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

		// �Ȗ�field Enter�L������
		ctrlItem.getField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER && lostFocusFlag == false) {
					ctrlItem.itemLostFocus();
					if (ctrlItem.getOutputParameter().isIncludeSubItem()) {
						ctrlSubItem.requestFocus();
					}
				}
				lostFocusFlag = false;
			}
		});

		// �u�Ȗځv�{�^��������
		ctrlItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setItemData();
			}

			public void after() {
				ctrlSubItem.setEditMode(ctrlItem.getOutputParameter().isIncludeSubItem());
				ctrlBeginBreakDownItem.setEditMode(false);
				ctrlEndBreakDownItem.setEditMode(false);
				lostFocusFlag = true;
			}
		});

		// �u�⏕�Ȗځv�{�^��������
		ctrlSubItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setSubItemData();
			}

			public void after() {
				boolean isEdit = ctrlSubItem.getOutputParameter().isIncludeBreakDownItem();
				ctrlBeginBreakDownItem.setEditMode(isEdit);
				ctrlEndBreakDownItem.setEditMode(isEdit);
			}
		});

		// �u����ȖڊJ�n�v�{�^��������
		ctrlBeginBreakDownItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setBeginBreakDownItemData();
			}
		});

		// �u����ȖڏI���v�{�^��������
		ctrlEndBreakDownItem.addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setEndBreakDownItemData();
			}
		});

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TBreakDownItemField ctrlBeginBreakDownItem;

	TBreakDownItemField ctrlEndBreakDownItem;

	TItemField ctrlItem;

	TSubItemField ctrlSubItem;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlRangeSpecification;

	TTable ssBreakDownItem;
}
