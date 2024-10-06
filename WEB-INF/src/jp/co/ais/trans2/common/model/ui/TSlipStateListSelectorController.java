package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;

/**
 * �`�[�̃X�e�[�^�X�I��(�e�[�u���`�F�b�N�`��)�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TSlipStateListSelectorController extends TController {

	/** �t�B�[���h */
	protected TSlipStateListSelector field;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param field
	 * @param nonDisplayList ��\���敪
	 */
	public TSlipStateListSelectorController(TSlipStateListSelector field, SlipState[] nonDisplayList) {

		this.field = field;

		init(nonDisplayList);
	}

	/**
	 * Start
	 */
	@Override
	public void start() {
		//
	}

	/**
	 * �p�l���擾
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * ������
	 * 
	 * @param nonDisplayList ��\���敪
	 */
	protected void init(SlipState[] nonDisplayList) {

		boolean isChecked = ClientConfig.isFlagOn("trans.slipstate.init.checkall");

		// �o�^
		if (isVisible(nonDisplayList, SlipState.ENTRY)) {
			field.addRow(new Object[] { isChecked, getWord("C01258"), SlipState.ENTRY });// �o�^
		}

		// ���ꏳ�F
		if (isVisible(nonDisplayList, SlipState.FIELD_APPROVE) && getCompany().getAccountConfig().isUseFieldApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C00157"), SlipState.FIELD_APPROVE });// ���ꏳ�F
		}

		// �o�����F
		if (isVisible(nonDisplayList, SlipState.APPROVE) && getCompany().getAccountConfig().isUseApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C01616"), SlipState.APPROVE });// �o�����F
		}

		// ����۔F
		if (isVisible(nonDisplayList, SlipState.FIELD_DENY) && getCompany().getAccountConfig().isUseFieldApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C01617"), SlipState.FIELD_DENY });// ����۔F
		}

		// �o���۔F
		if (isVisible(nonDisplayList, SlipState.DENY) && getCompany().getAccountConfig().isUseApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C01615"), SlipState.DENY });// �o���۔F
		}

		// �X�V
		if (isVisible(nonDisplayList, SlipState.UPDATE)) {
			field.addRow(new Object[] { isChecked, getWord("C00169"), SlipState.UPDATE });// �X�V
		}

		field.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		field.setReorderingAllowed(false);

		if (field.getRowCount() > 4) {
			field.setSize(150, 140);
		}
	}

	/**
	 * �ΏۍX�V�敪��\�����邩�ǂ���
	 * 
	 * @param stateArray ��\�����X�g
	 * @param state �ΏۍX�V�敪
	 * @return True�F�\��
	 */
	protected boolean isVisible(SlipState[] stateArray, SlipState state) {

		for (int i = 0; i < stateArray.length; i++) {

			// ���X�g�Ɉ�v����X�V�敪������ꍇ��False��Ԃ�
			if (stateArray[i].equals(state)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �`�F�b�N�����s����Ԃ�
	 * 
	 * @return �`�F�b�N�s��
	 */
	public int getCheckedRowCount() {

		// �`�F�b�N�s�̎擾
		int checkedCount = 0;
		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipStateListSelector.SC.CHECK);

			// �`�F�b�N����Ă���ꍇ�A�J�E���g�𑝉�
			if (isChecked) {
				checkedCount += 1;
			}

		}

		return checkedCount;
	}

	/**
	 * �`�F�b�N�����X�V�敪��Ԃ�
	 * 
	 * @return List<�X�V�敪>
	 */
	public List<SlipState> getCheckedSlipState() {

		List<SlipState> list = new LinkedList<SlipState>();

		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipStateListSelector.SC.CHECK);

			// �`�F�b�N����Ă���ꍇ�A���X�g�ɑΏۍX�V��ǉ�
			if (isChecked) {
				SlipState state = (SlipState) field.getRowValueAt(i, TSlipStateListSelector.SC.SLIPSTATE_VALUE);
				list.add(state);
			}
		}

		return list;
	}
}