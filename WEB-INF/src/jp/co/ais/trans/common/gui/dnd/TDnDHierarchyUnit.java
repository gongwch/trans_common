package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �K�w�f�[�^�쐬��ՃR���|�[���l���g
 */
public class TDnDHierarchyUnit extends TPanel {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �X�v���b�h�V�[�gDataSource */
	JCVectorDataSource ds;

	/** �K�w�f�[�^�̃��[�g�f�[�^ */
	public DnDData baseDndData = null;

	/** �x�[�X�p�l�� */
	protected TPanel pnlBase;

	/** �ꗗpanel */
	protected TPanel pnlJournal;

	/** �K�wpanel */
	protected JScrollPane pnlTree;

	/** �ꗗ */
	protected TDnDTable ssJournal;

	/** �K�w�f�[�^ */
	protected TDnDTree tree;

	/** �K�w�f�[�^�쐬��ՃR���|�[���l���g��CTRL */
	private TDnDHierarchyUnitCtrl ctrl;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param dndData �K�w�f�[�^
	 */
	public TDnDHierarchyUnit(DnDData dndData) {
		this.baseDndData = dndData;

		ctrl = new TDnDHierarchyUnitCtrl(this);

		// ��ʍ\�z
		initComponents();

		// �X�v���b�h�V�[�g�ݒ�
		initSpreadSheet();

	}

	/**
	 * �R���X�g���N�^
	 */
	public TDnDHierarchyUnit() {
		ctrl = new TDnDHierarchyUnitCtrl(this);

		// ��ʍ\�z
		initComponents();

		// �X�v���b�h�V�[�g�ݒ�
		initSpreadSheet();
	}

	/**
	 * TTree�̋@�\�g���N���X
	 * 
	 * @return TDnDTree
	 */
	public TDnDTree createTDnDTree() {
		if (baseDndData == null) {
			return new TDnDTree();
		} else {
			return new TDnDTree(baseDndData);
		}
	}

	/**
	 * TTable�̋@�\�g���N���X
	 * 
	 * @return TTable
	 */
	public TDnDTable createTDnDTable() {
		return new TDnDTable();
	}

	/**
	 * ��ʍ\�z
	 */
	public void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		pnlJournal = new TPanel();
		pnlTree = new JScrollPane();

		// TTable�̋@�\�g���N���X
		ssJournal = createTDnDTable();

		// TTree�̋@�\�g���N���X
		tree = createTDnDTree();

		this.setMaximumSize(new java.awt.Dimension(600, 400));
		this.setMinimumSize(new java.awt.Dimension(600, 400));
		this.setPreferredSize(new java.awt.Dimension(600, 400));
		setLayout(new GridBagLayout());

		pnlBase.setMaximumSize(new java.awt.Dimension(600, 400));
		pnlBase.setMinimumSize(new java.awt.Dimension(600, 400));
		pnlBase.setPreferredSize(new java.awt.Dimension(600, 400));
		pnlBase.setLayout(new java.awt.GridBagLayout());

		pnlJournal.setMaximumSize(new java.awt.Dimension(274, 400));
		pnlJournal.setMinimumSize(new java.awt.Dimension(274, 400));
		pnlJournal.setPreferredSize(new java.awt.Dimension(274, 400));
		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));
		pnlJournal.add(ssJournal);

		ssJournal.setTabControlNo(1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		pnlBase.add(pnlJournal, gridBagConstraints);

		pnlTree.setMaximumSize(new java.awt.Dimension(270, 400));
		pnlTree.setMinimumSize(new java.awt.Dimension(270, 400));
		pnlTree.setPreferredSize(new java.awt.Dimension(270, 400));
		pnlTree.getViewport().add(tree, null);

		tree.setTabControlNo(2);
		tree.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					deleteTreeData();
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 46, 0, 0);
		pnlBase.add(pnlTree, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		add(pnlBase, gridBagConstraints);
	}

	/**
	 * �ꗗ�̃J�������̂�ݒ肷��
	 * 
	 * @param colNames �J��������
	 */
	public void setColumnNames(String[] colNames) {
		// ctrl.setColumnNames(colNames);
		ssJournal.setColumnTitleMessageID(colNames);
		ssJournal.setColumnLabels(colNames);
	}

	/**
	 * �ꗗ�̃J��������ݒ肷��
	 * 
	 * @param colWidths �J������
	 */
	public void setColumnWidths(int[] colWidths) {
		ssJournal.setColumnWidths(colWidths);

	}

	/**
	 * �ꗗ�̃J�����ʒu�𒆉������ɂ���
	 * 
	 * @param col �J�����C���f�b�N�X
	 */
	public void setCenterColumn(int col) {
		this.setCellStyle(col, CellStyleModel.CENTER);
	}

	/**
	 * �ꗗ�̃J�����ʒu���E�񂹂ɂ���
	 * 
	 * @param col �J�����C���f�b�N�X
	 */
	public void setRightColumn(int col) {
		this.setCellStyle(col, CellStyleModel.RIGHT);
	}

	/**
	 * �ꗗ�̃J�����ʒu��ݒ肷��
	 * 
	 * @param col �J�����C���f�b�N�X
	 * @param colStyle �J�����ʒu
	 */
	private void setCellStyle(int col, int colStyle) {
		CellStyleModel cellStyle = (CellStyleModel) ssJournal.getDefaultCellStyle().clone();
		cellStyle.setHorizontalAlignment(colStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, col, cellStyle);
	}

	/**
	 * �ꗗ�Ƀf�[�^�𔽉f����
	 * 
	 * @param cells �ꗗ�f�[�^
	 */
	public void setDataList(Vector<Vector> cells) {
		ssJournal.setDataList(cells);
	}

	/**
	 * �K�w�f�[�^��ݒ肷��
	 * 
	 * @param listDnDData �K�w�f�[�^
	 */
	public void setTreeData(List<DnDData> listDnDData) {
		ctrl.setTreeData(listDnDData);
	}

	/**
	 * �K�w�f�[�^���擾����
	 * 
	 * @return �K�w�f�[�^
	 */
	public List<DnDData> getTreeData() {
		return ctrl.getTreeData();
	}

	/**
	 * ���[�g�m�[�h���܂߂Ȃ��K�w�f�[�^���擾����
	 * 
	 * @return �K�w�f�[�^
	 */
	public List<DnDData> getTreeDataNoRoot() {
		return ctrl.getTreeDataNoRoot();
	}

	/**
	 * �K�w�f�[�^���폜���� <br>
	 * DELETE�L�[ �������̏����B�K�w�f�[�^����I���m�[�h���폜����B
	 * 
	 * @return �K�w�f�[�^����I���m�[�h(For Override)
	 */
	public List<DnDData> deleteTreeData() {
		return ctrl.doDeleteTreeData();
	}

	/**
	 * �X�v���b�h�V�[�g�ݒ�
	 */
	public void initSpreadSheet() {

		// "" , �R�[�h , ����
		String[] columnLabelMessageIDs = new String[] { "", "C00174", "C00518" };

		// �Z����
		int[] columnWidths = new int[3];
		columnWidths[0] = 0;
		columnWidths[1] = 7;
		columnWidths[2] = 11;

		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// �����s�I�����ǂ����̐ݒ�
		ssJournal.setSelectMaltiLine(true);
		ssJournal.setSelectMultiRange(true);

		ssJournal.setCharWidth(-1, 2);

		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// �����\���f�[�^�̍\�z
		setDataList(new Vector<Vector>());
	}

	/**
	 * �^�u�ړ����ԍ�
	 * 
	 * @param no
	 */
	public void setTabControlNo(int no) {
		ssJournal.setTabControlNo(no);
		tree.setTabControlNo(no);
	}

	/**
	 * �ꗗ
	 * 
	 * @return �ꗗ
	 */
	public TDnDTable getSsJournal() {
		return ssJournal;
	}

	/**
	 * �ꗗ
	 * 
	 * @param ssJournal �ꗗ�ݒ肷��
	 */
	public void setSsJournal(TDnDTable ssJournal) {
		this.ssJournal = ssJournal;
	}

	/**
	 * �K�w
	 * 
	 * @return �K�w
	 */
	public TDnDTree getTree() {
		return tree;
	}

	/**
	 * �K�w
	 * 
	 * @param tree �K�w�ݒ肷��
	 */
	public void setTree(TDnDTree tree) {
		this.tree = tree;
	}

}
