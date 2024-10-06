package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �����}�X�^���
 * 
 * @author zhangzhenxing
 */
// TODO ���̒i�K�ł́ASPOT�{�o�^�͎g��Ȃ�
public class MG0150CustomerMasterPanel extends TPanelBusiness {

	/** �R���g���[���N���X */
	private MG0150CustomerMasterPanelCtrl ctrl;

	/**  */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param ctrl �R���g���[���N���X
	 */
	MG0150CustomerMasterPanel(MG0150CustomerMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// ��ʂ̏�����
		initComponents();
		// �X�v���b�h�̏�����
		initSpreadSheet();

		super.initPanel();
		this.btnCopy.setEnabled(false);
		this.btnDelete.setEnabled(false);
		this.btnEdit.setEnabled(false);
		this.btnSpotRealEntry.setEnabled(false);
	}

	/**
	 * 
	 */
	protected void initSpreadSheet() {
		// SpreadSheet �� init ����
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00786", "C00830", "C00787", "C00836", "C00581",
				"C00527", "C01152", "C01150", "C01151", "C00393", "C00690", "C00871", "C01089", "C01261", "C02038",
				"C02039", "C00870", "C02040", "C01130", "C10133", "C02041", "C02042", "C00055", "C00261" };
		// �X�v���b�h�񕝂̏�����
		int[] columnWidths = new int[] { 0, 10, 40, 40, 80, 40, 10, 80, 50, 50, 12, 12, 10, 6, 6, 10, 11, 10, 11, 7,
				48, 20, 10, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// ��A�s�\��̃X�^�C���ݒ�
		ssCustomer.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// �X�v���b�h�����̏�����
		ssCustomer.addSpreadSheetSelectChange(btnEdit);

		ds.setNumRows(0);
		ds.setNumColumns(25);
		ssCustomer.setDataSource(ds);
	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssCustomer.setDataSource(ds);

		// Scroll�ʒu�ݒ�
		ssCustomer.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssCustomer.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// ���l���E�񂹂���
		CellStyleModel defaultStyle = ssCustomer.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssCustomer.setCellStyle(JCTableEnum.ALLCELLS, 13, centerStyle);
		ssCustomer.setCellStyle(JCTableEnum.ALLCELLS, 14, centerStyle);
		ssCustomer.setCellStyle(JCTableEnum.ALLCELLS, 19, centerStyle);
		ssCustomer.setCellStyle(JCTableEnum.ALLCELLS, 22, centerStyle);
		ssCustomer.setCellStyle(JCTableEnum.ALLCELLS, 23, centerStyle);
		ssCustomer.setCellStyle(JCTableEnum.ALLCELLS, 24, centerStyle);

		// ��ЃR�[�h���\������
		ssCustomer.setColumnHidden(0, true);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
		this.btnSpotRealEntry.setEnabled(cells.size() > 0);
	}

	/** �V���A��UID */
	private static final long serialVersionUID = 6975993877695662983L;

	/**
	 * �t���[���擾
	 */
	@Override
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	/**
	 * ��ʃR���|�[�l���g�̍\�z
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnNew = new TButton();
		btnSearch = new TButton();
		btnEdit = new TButton();
		btnCopy = new TButton();
		btnDelete = new TButton();
		btnListOutput = new TButton();
		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		ctrlBeginCustomer = new TButtonField();
		ctrlEndCustomer = new TButtonField();
		btnSpotRealEntry = new TButton();
		pnlSpotRealEntry = new TPanel();
		pnlDetail = new TPanel();
		ssCustomer = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setMnemonic(5);
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
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);

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
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(6);
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
		btnCopy.setTabControlNo(7);
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
		btnDelete.setTabControlNo(8);
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
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(565, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(565, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(565, 80));

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

		ctrlBeginCustomer.setButtonSize(85);
		ctrlBeginCustomer.setFieldSize(120);
		ctrlBeginCustomer.setLangMessageID("C01498");
		ctrlBeginCustomer.setMaxLength(10);
		ctrlBeginCustomer.setNoticeSize(255);
		ctrlBeginCustomer.setTabControlNo(1);
		ctrlBeginCustomer.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginCustomer, gridBagConstraints);

		ctrlEndCustomer.setButtonSize(85);
		ctrlEndCustomer.setFieldSize(120);
		ctrlEndCustomer.setLangMessageID("C01498");
		ctrlEndCustomer.setMaxLength(10);
		ctrlEndCustomer.setNoticeSize(255);
		ctrlEndCustomer.setTabControlNo(2);
		ctrlEndCustomer.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndCustomer, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		// �{�^�����B��
		btnSpotRealEntry.setVisible(false);
		btnSpotRealEntry.setLangMessageID("C00974");
		btnSpotRealEntry.setMaximumSize(new Dimension(140, 25));
		btnSpotRealEntry.setMinimumSize(new Dimension(140, 25));
		btnSpotRealEntry.setPreferredSize(new Dimension(140, 25));
		btnSpotRealEntry.setTabControlNo(10);
		btnSpotRealEntry.addActionListener(new ActionListener() {

			// Spot�{�^��������
			public void actionPerformed(ActionEvent evt) {
				ctrl.spot();
			}
		});
		pnlSpotRealEntry.add(btnSpotRealEntry, gridBagConstraints);

		pnlSpotRealEntry.setMaximumSize(new Dimension(140, 25));
		pnlSpotRealEntry.setMinimumSize(new Dimension(140, 25));
		pnlSpotRealEntry.setPreferredSize(new Dimension(140, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 15, 5, 0);
		pnlHeader.add(pnlSpotRealEntry, gridBagConstraints);

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
		ssCustomer.setTabControlNo(3);
		pnlDetail.add(ssCustomer);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

	}

	/**  */
	TButton btnCopy;

	/**  */
	TButton btnDelete;

	/**  */
	TButton btnEdit;

	/**  */
	TButton btnListOutput;

	/**  */
	TButton btnNew;

	/**  */
	TButton btnSearch;

	/**  */
	TButton btnSpotRealEntry;

	/**  */
	TButtonField ctrlBeginCustomer;

	/**  */
	TButtonField ctrlEndCustomer;

	/**  */
	TLabel lblBegin;

	/**  */
	TLabel lblEnd;

	/**  */
	TPanel pnlButton;

	/**  */
	TPanel pnlDetail;

	/**  */
	TPanel pnlHeader;

	/**  */
	TPanel pnlSpotRealEntry;

	/**  */
	TPanel pnlRangeSpecification;

	/**  */
	TTable ssCustomer;

}
