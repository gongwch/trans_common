package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.TAprvRoleGroupListSelector.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�O���[�v�I��(�e�[�u���`�F�b�N�`��)�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TAprvRoleGroupListSelectorController extends TController {

	/** �t�B�[���h */
	protected TAprvRoleGroupListSelector field;

	/** �������� */
	protected AprvRoleGroupSearchCondition condition;

	/** ���F�O���[�v�S�f�[�^���X�g */
	protected List<AprvRoleGroup> grpList;

	/** �`�[��ʃX�v���b�h�Fmap */
	protected Map<String, Boolean> map = new HashMap<String, Boolean>();

	/**
	 * �R���X�g���N�^(�ꗗ�f�[�^�������\������)
	 * 
	 * @param field �t�B�[���h
	 */
	public TAprvRoleGroupListSelectorController(TAprvRoleGroupListSelector field) {
		this(field, true);
	}

	/**
	 * �R���X�g���N�^(�ꗗ�f�[�^�������\�����Ȃ�)
	 * 
	 * @param field
	 * @param isRefresh
	 */
	public TAprvRoleGroupListSelectorController(TAprvRoleGroupListSelector field, boolean isRefresh) {

		try {

			this.field = field;

			initSearchCondition();

			if (isRefresh) {
				init(condition);
			}

		} catch (TException e) {
			errorHandler(e);
		}
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * ��������������
	 */
	protected void initSearchCondition() {
		condition = new AprvRoleGroupSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
	}

	/**
	 * �ݒ肳�ꂽ�������������ɓ`�[��ʈꗗ���ēǍ�����B
	 */
	protected void refresh() {
		try {
			init(condition);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * ������
	 * 
	 * @param condition1
	 * @throws TException
	 */
	protected void init(AprvRoleGroupSearchCondition condition1) throws TException {

		field.removeRow();
		grpList = getRoleGroup(condition1);

		boolean isChecked = ClientConfig.isFlagOn("trans.aprvrole.grp.init.checkall");

		for (AprvRoleGroup role : grpList) {
			field.addRow(new Object[] { isChecked, role.getAPRV_ROLE_GRP_CODE() + " : " + role.getAPRV_ROLE_GRP_NAME(),
					role.getAPRV_ROLE_GRP_CODE() });
			map.put(role.getAPRV_ROLE_GRP_CODE(), isChecked);

		}
		filterEvent();

	}

	/**
	 * �t�B���^�[���̓C�x���g��`
	 */
	protected void filterEvent() {
		field.ctrlFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				filter_Input(e);

			}

		});
	}

	/**
	 * ���F���[���ꗗ[�t�B���^�[����]
	 * 
	 * @param e
	 */
	protected void filter_Input(KeyEvent e) {

		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TAprvRoleListSelector.SC.CHECK);

			// �`�F�b�N����Ă���ꍇ�A���X�g�ɑΏۍX�V��ǉ�
			String key = (String) field.getRowValueAt(i, TAprvRoleListSelector.SC.VALUE);
			map.put(key, isChecked);

		}

		field.removeRow();

		for (AprvRoleGroup role : grpList) {
			String name = role.getAPRV_ROLE_GRP_CODE() + ":" + role.getAPRV_ROLE_GRP_NAME();

			if (name.contains(field.ctrlFilter.getInputText())) {
				field.addRow(new Object[] { map.get(role.getAPRV_ROLE_GRP_CODE()),
						role.getAPRV_ROLE_GRP_CODE() + " : " + role.getAPRV_ROLE_GRP_NAME(), role.getAPRV_ROLE_GRP_CODE() });
			}

		}
	}

	/**
	 * �`�[��ʂ��擾����
	 * 
	 * @param searchCondition
	 * @return List<�`�[���>
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	protected List<AprvRoleGroup> getRoleGroup(AprvRoleGroupSearchCondition searchCondition) throws TException {

		List<AprvRoleGroup> list = (List<AprvRoleGroup>) request(getManagerClass(), "get", searchCondition);

		return list;
	}

	/**
	 * �`�[��ʒ��o�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getManagerClass() {
		return AprvRoleGroupManager.class;
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

			// �`�F�b�N�l�̎擾�y��map�X�V
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TAprvRoleListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TAprvRoleListSelector.SC.VALUE);
			map.put(value, isChecked);

		}
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue()) {
				checkedCount++;
			}
		}

		return checkedCount;

	}

	/**
	 * �`�F�b�N�������F�����R�[�h��Ԃ�
	 * 
	 * @return List<�`�[���>
	 */
	public List<String> getCheckedAprvRoleCode() {

		List<String> list = new LinkedList<String>();
		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾�y��map�X�V
			Boolean isChecked = (Boolean) field.getRowValueAt(i, SC.CHECK);
			String value = (String) field.getRowValueAt(i, SC.VALUE);
			map.put(value, isChecked);

		}
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue()) {
				list.add(entry.getKey());
			}
		}

		return list;
	}

	/**
	 * @return ��������
	 */
	public AprvRoleGroupSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition ��������
	 */
	public void setCondition(AprvRoleGroupSearchCondition condition) {
		this.condition = condition;
	}

}
