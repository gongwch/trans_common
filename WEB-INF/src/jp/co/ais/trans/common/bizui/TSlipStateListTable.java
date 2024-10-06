package jp.co.ais.trans.common.bizui;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �X�V�敪�ꗗ�R���|�[�l���g
 * 
 * @author ookawara
 */
public class TSlipStateListTable extends TTable {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �o�^ */
	public static final int INSERT = 1;

	/** ���ꏳ�F */
	public static final int SPOT_ADMIT = 2;

	/** ����۔F */
	public static final int SPOT_DENY = 11;

	/** �o�����F */
	public static final int ACCOUNT_ADMIT = 3;

	/** �o���۔F */
	public static final int ACCOUNT_DENY = 12;

	/** �X�V */
	public static final int UPDATE = 4;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �X�V�敪 �\��/��\�� */
	private boolean isIncludeUpdate;

	/** �\�����ׂ��X�V�敪���ڃ��X�g */
	private List<Integer> visibleList;

	/** �\������Ȃ��X�V�敪���ڃ��X�g */
	private List<Integer> inVisibleList;

	/** Controller */
	private TSlipStateListTableCtrl ctrl;

	/**
	 * Constructor. �f�t�H���g
	 */
	public TSlipStateListTable() {
		this(TClientLoginInfo.getInstance().getCompanyCode());
	}

	/**
	 * Constructor. ��ЃR�[�h�w��
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public TSlipStateListTable(String companyCode) {
		this(companyCode, true);

	}

	/**
	 * Constructor. �X�V�\���w��
	 * 
	 * @param isIncludeUpdate �X�V true�F�\�� false�F��\��
	 */
	public TSlipStateListTable(boolean isIncludeUpdate) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), isIncludeUpdate);
	}

	/**
	 * Constructor. ��ЃR�[�h�E�X�V�\���w��
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param isIncludeUpdate �X�V true�F�\�� false�F��\��
	 */
	public TSlipStateListTable(String companyCode, boolean isIncludeUpdate) {

		this.companyCode = companyCode;
		this.isIncludeUpdate = isIncludeUpdate;

		// ������ʍ\�z
		initComponents();

		// �l���X�v���b�h�ɃZ�b�g
		ctrl = new TSlipStateListTableCtrl(this);

		// �X�V�敪�ꗗ������
		ctrl.setTypeUpdateInit();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {

		// �Z���� �����ݒ�
		setCharWidth(0, 2);
		setCharWidth(1, 5);

		setRowLabelDisplay(false);
	}

	/**
	 * ��ЃR�[�h��Ԃ�
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * �X�V���܂߂邩��Ԃ�
	 * 
	 * @return isIncludeUpdate �X�V���܂߂邩 true�F�܂߂� false:�܂߂Ȃ�
	 */
	public boolean getIncludeUpdate() {
		return this.isIncludeUpdate;
	}

	/**
	 * �Z�����ݒ� �`�F�b�N�{�b�N�X
	 * 
	 * @param size �T�C�Y
	 */
	public void setCheckColumn(int size) {
		setCharWidth(0, size);
	}

	/**
	 * �Z�����ݒ� �X�V�敪
	 * 
	 * @param size �T�C�Y
	 */
	public void setUpdKbnColumn(int size) {
		setCharWidth(1, size);
	}

	/**
	 * �^�C�g���ݒ�
	 * 
	 * @param title �^�C�g��
	 */
	public void setUpdKbnTitle(String[] title) {
		ctrl.cupdateDivisionlabel = title;
		ctrl.resetSpread(true);
	}

	/**
	 * �I������Ă���X�V�敪�R�[�h���擾����
	 * 
	 * @return �R�[�h���X�g
	 */
	public int[] getSelectedCodes() {

		List<Integer> list = getSelectedCodesList();

		int[] ints = new int[list.size()];
		Iterator iter = list.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			ints[i] = ((Integer) iter.next()).intValue();
		}

		return ints;
	}

	/**
	 * �I������Ă���X�V�敪�R�[�h���擾����
	 * 
	 * @return �R�[�h���X�g
	 */
	public List<Integer> getSelectedCodesList() {
		List<Integer> list = new LinkedList<Integer>();

		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();

		for (int row = 0; row < this.getNumRows(); row++) {

			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();

			if (isCheck) {
				Vector rows = (Vector) ds.getCells().get(row);
				list.add((Integer) rows.get(2));
			}
		}

		return list;
	}

	/**
	 * �I������Ă���X�V�敪�R�[�h�����邩
	 * 
	 * @return �I������Ă����true��Ԃ�
	 */
	public boolean isSelected() {
		for (int row = 0; row < this.getNumRows(); row++) {
			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();
			if (isCheck) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��\������鍀�ڂ̃��X�g
	 * 
	 * @return ��\������鍀�ڃ��X�g
	 */
	public List<Integer> getInVisibleList() {
		return inVisibleList;
	}

	/**
	 * ��\������鍀�ڂ̃��X�g
	 * 
	 * @param inVisibleList
	 */
	public void setInVisibleList(List<Integer> inVisibleList) {
		this.inVisibleList = inVisibleList;
		ctrl.resetSpread(true);
	}

	/**
	 * �\������鍀�ڂ̃��X�g�擾
	 * 
	 * @return �\������鍀�ڂ̃��X�g
	 */
	public List<Integer> getVisibleList() {
		return visibleList;
	}

	/**
	 * �\������鍀�ڂ̃��X�g�ݒ�
	 * 
	 * @param visibleList
	 */
	public void setVisibleList(List<Integer> visibleList) {
		this.visibleList = visibleList;
		ctrl.resetSpread(false);
	}

}
