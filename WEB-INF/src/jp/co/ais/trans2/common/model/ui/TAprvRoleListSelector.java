package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.util.List;

import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * �`�[��ʑI��(�e�[�u���`�F�b�N�`��)�R���|�[�l���g
 * 
 * @author AIS
 */
public class TAprvRoleListSelector extends TPanel {

	/** �ꗗ */
	protected TTable tbl;

	/** �t�B���^�[ */
	protected TTextField ctrlFilter;

	/** �e�[�u���\�[�g */
	protected TableRowSorter<? extends TableModel> sorter = null;

	/** �R���g���[�� */
	protected TAprvRoleListSelectorController controller;

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** �I�� */
		CHECK,
		/** �\���� */
		NAME,
		/** �l */
		VALUE
	}

	/**
	 * 
	 *
	 */
	public TAprvRoleListSelector() {
		this(true);
	}

	/**
	 * �����\���f�[�^�̂���/�Ȃ����w��B�����̏ꍇ�A�Ăяo������<br>
	 * �����������Z�b�g���Arefresh���\�b�h�����s���邱�ƂŌʂ̌���������<br>
	 * ��Â��ꗗ�̕\�����s��
	 * 
	 * @param isRefresh true(�����\���f�[�^���f�t�H���g�̌��������ŕ\������)
	 */
	public TAprvRoleListSelector(boolean isRefresh) {

		initComponents();

		allocateComponents();

		// �R���g���[������
		controller = new TAprvRoleListSelectorController(this, isRefresh);

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		tbl = new TTable();
		ctrlFilter = new TTextField();
		tbl.addColumn(SC.CHECK, "", 30, TCheckBox.class);
		tbl.addColumn(SC.NAME, "C12238", 100);
		tbl.addColumn(SC.VALUE, "", -1);
		tbl.setRowLabelNumber(false);
		tbl.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {
		GridBagConstraints gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setSize(135, 140);

		tbl.setSize(210, 140);
		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridx = 0;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(tbl, gc);

		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 20);
		gc.gridx = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		ctrlFilter.setMaxLength(20);
		ctrlFilter.setMaximumSize(new Dimension(140, 20));
		ctrlFilter.setMinimumSize(new Dimension(140, 20));
		ctrlFilter.setPreferredSize(new Dimension(140, 20));

		add(ctrlFilter, gc);

	}

	/**
	 * �`�F�b�N�����s����Ԃ�
	 * 
	 * @return �`�F�b�N�s��
	 */
	public int getCheckedRowCount() {
		return controller.getCheckedRowCount();
	}

	/**
	 * �`�F�b�N�������F�������[���R�[�h��Ԃ�
	 * 
	 * @return List<���F�������[���R�[�h>
	 */
	public List<String> getCheckedAprvRoleCode() {
		return controller.getCheckedAprvRoleCode();
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public AprvRoleSearchCondition getCondition() {
		return controller.getCondition();
	}

	/**
	 * �ݒ肳�ꂽ�������������ɓ`�[��ʈꗗ���ēǍ�����B
	 */
	public void refresh() {
		controller.refresh();
	}

	/**
	 * @param no
	 */
	public void setTabControlNo(int no) {
		tbl.table.setTabControlNo(no);
		ctrlFilter.setTabControlNo(++no);
	}

	/**
	 * �S�Ă̍s���폜����
	 */
	public void removeRow() {
		tbl.removeRow();
	}

	/**
	 * @param row
	 */
	public void setShowRowHeaderStar(int row) {
		tbl.setShowRowHeaderStar(row);
	}

	/**
	 * �s�ǉ� Object�^�̔z���ǉ�
	 * 
	 * @param data �f�[�^
	 */
	public void addRow(Object[] data) {
		tbl.addRow(data);
	}

	/**
	 * �\������Ă���s�����擾����
	 * 
	 * @return �s��
	 */
	public int getRowCount() {
		return tbl.getRowCount();
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getRowValueAt(int row, Enum column) {
		return tbl.getRowValueAt(row, column);
	}

	/**
	 * @param obj
	 * @param row
	 * @param column
	 * @see jp.co.ais.trans2.common.gui.TTable#setRowValueAt(java.lang.Object, int, java.lang.Enum)
	 */
	public void setRowValueAt(Object obj, int row, Enum column) {
		tbl.setRowValueAt(obj, row, column);
	}

	/**
	 * ��ʂɕ\������Ă��鏳�F���[�������X�g�Ŏ擾
	 * 
	 * @return ���F���[�����X�g
	 */
	public List<AprvRole> getEntityList() {
		return controller.getEntityList();
	}

}
