package jp.co.ais.trans.common.bizui;

import java.awt.*;
import javax.swing.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.TransUtil;

/**
 * �C�ӊǗ��R���|�[�l���g
 * 
 * @author ookawara
 */
public class TManagementOptionRangeUnit extends TPanel implements TInterfaceLangMessageID {

	/** �V���A��UID */
	protected static final long serialVersionUID = 6975993877695662983L;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementOptionRangeUnit() {
		super();

		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		managementOption1 = new TManagementOptionRangeField();
		managementOption2 = new TManagementOptionRangeField();
		managementOption3 = new TManagementOptionRangeField();

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		basePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		basePanel.setLangMessageID("C00814");
		basePanel.setMaximumSize(new Dimension(325, 235));
		basePanel.setMinimumSize(new Dimension(325, 235));
		basePanel.setPreferredSize(new Dimension(325, 235));
		add(basePanel, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(-10, 0, 0, 10);
		basePanel.add(managementOption1, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 10);
		basePanel.add(managementOption2, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 10);
		basePanel.add(managementOption3, gridBagConstraints);
	}

	/**
	 * �u�C�ӊǗ��t�B�[���h�P�v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �C�ӊǗ��t�B�[���h�P�t�B�[���h
	 */
	public TManagementOptionRangeField getTManagementOption1() {
		return this.managementOption1;
	}

	/**
	 * �u�C�ӊǗ��t�B�[���h�Q�v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �C�ӊǗ��t�B�[���h�Q�t�B�[���h
	 */
	public TManagementOptionRangeField getTManagementOption2() {
		return this.managementOption2;
	}

	/**
	 * �u�C�ӊǗ��t�B�[���h�R�v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �C�ӊǗ��t�B�[���h�R�t�B�[���h
	 */
	public TManagementOptionRangeField getTManagementOption3() {
		return this.managementOption3;
	}

	/**
	 * �u�p�l���v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �p�l��
	 */
	public TPanel getTBasePanel() {
		return this.basePanel;
	}

	/**
	 * �p�l���S�� �^�u�ړ����ԍ���ݒ肷��.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		managementOption1.setTabControlNo(no);
		managementOption2.setTabControlNo(no);
		managementOption3.setTabControlNo(no);
	}

	/**
	 * �R�[�h�̑��݂̃`�F�b�N���s��Ȃ����[�h�֕ύX
	 */
	public void setNonCheckMode() {
		managementOption1.setNonCheckMode();
		managementOption2.setNonCheckMode();
		managementOption3.setNonCheckMode();
	}

	protected TManagementOptionRangeField managementOption1;

	protected TManagementOptionRangeField managementOption2;

	protected TManagementOptionRangeField managementOption3;

	protected TPanel basePanel;

	/**
	 * �w��Ԗڂ̊Ǘ��I���R���|�[�l���g��Ԃ�
	 * @param index �ォ�牽�Ԗڂ�
	 * @return �w��Ԗڂ̊Ǘ��I���R���|�[�l���g
	 */
	public TManagementOptionRangeField getTManagementOptionRangeFieldAt(int index) {
		if (index == 1) {
			return managementOption1;
		} else if (index == 2) {
			return managementOption2;
		} else if (index == 3) {
			return managementOption3;
		}
		return null;
	}

	/**
	 * �w��Ԗڂ̊Ǘ��I���R���|�[�l���g�őI�����ꂽ�W�v�P�ʂ�Ԃ��B
	 * return �w��Ԗڂ̊Ǘ��I���R���|�[�l���g�őI�����ꂽ�W�v�P��
	 */
	public TransUtil.SumGroup getSelectedSumGroupAt(int index) {
		return getTManagementOptionRangeFieldAt(index).getSelectedSumGroup();
	}

	/**
	 * �ォ�珇�ɓ��͂���Ă��邩��Ԃ�
	 * @return �ォ�珇�ɓ��͂���Ă���ꍇtrue�A���͂���Ă��Ȃ��ꍇfalse 
	 */
	public boolean isEnteredAtTheTop() {

		int noneOrdinal = TransUtil.SumGroup.None.ordinal();
		int knrKbn1 = getSelectedSumGroupAt(1).ordinal();
		int knrKbn2 = getSelectedSumGroupAt(2).ordinal();
		int knrKbn3 = getSelectedSumGroupAt(3).ordinal();

		if (knrKbn1 == noneOrdinal && (knrKbn2 != noneOrdinal || knrKbn3 != noneOrdinal)) {
			return false;
		}

		if (knrKbn2 == noneOrdinal && knrKbn3 != noneOrdinal) {
			return false;
		}

		return true;
	}

	/**
	 * �Ǘ��w��ɏd��������΁A�d�����Ă���t�B�[���h�̔ԍ���Ԃ��܂��B<br>
	 * 0�̏ꍇ�A�d���͂���܂���B
	 * @return 0(�d���Ȃ�) ����ȊO�͏d�����������t�B�[���h�̔ԍ�
	 */
	public int getSameKnrSelectedIndex() {

		int noneOrdinal = TransUtil.SumGroup.None.ordinal();;
		int knrKbn1 = getSelectedSumGroupAt(1).ordinal();
		int knrKbn2 = getSelectedSumGroupAt(2).ordinal();
		int knrKbn3 = getSelectedSumGroupAt(3).ordinal();
		if (knrKbn1 == knrKbn2 && knrKbn1 != noneOrdinal) {
			return 1;
		}
		if (knrKbn1 == knrKbn3 && knrKbn1 != noneOrdinal) {
			return 1;
		}
		if (knrKbn2 == knrKbn3 && knrKbn2 != noneOrdinal) {
			return 2;
		}
		return 0;

	}

}
