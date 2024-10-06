package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * �`�[��ʑI��(�e�[�u���`�F�b�N�`��)�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TAprvRoleListSelectorController extends TController {

	/** �t�B�[���h */
	protected TAprvRoleListSelector field;

	/** �������� */
	protected AprvRoleSearchCondition condition;

	/** �`�[��ʃX�v���b�h�S�f�[�^���X�g */
	protected List<AprvRole> roleList;

	/** �`�[��ʃX�v���b�h�Fmap */
	protected Map<String, Boolean> map = new HashMap<String, Boolean>();

	/**
	 * �R���X�g���N�^(�ꗗ�f�[�^�������\������)
	 * 
	 * @param field �t�B�[���h
	 */
	public TAprvRoleListSelectorController(TAprvRoleListSelector field) {
		this(field, true);
	}

	/**
	 * �R���X�g���N�^(�ꗗ�f�[�^�������\�����Ȃ�)
	 * 
	 * @param field
	 * @param isRefresh
	 */
	public TAprvRoleListSelectorController(TAprvRoleListSelector field, boolean isRefresh) {

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
		condition = new AprvRoleSearchCondition();
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
	protected void init(AprvRoleSearchCondition condition1) throws TException {

		field.removeRow();
		roleList = getAprvRole(condition1);

		boolean isChecked = ClientConfig.isFlagOn("trans.aprvrole.init.checkall");

		for (AprvRole role : roleList) {
			field.addRow(new Object[] { isChecked, role.getAPRV_ROLE_CODE() + " : " + role.getAPRV_ROLE_NAME(),
					role.getAPRV_ROLE_CODE() });
			map.put(role.getAPRV_ROLE_CODE(), isChecked);

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

		for (AprvRole role : roleList) {
			String name = role.getAPRV_ROLE_CODE() + ":" + role.getAPRV_ROLE_NAME();

			if (name.contains(field.ctrlFilter.getInputText())) {
				field.addRow(new Object[] { map.get(role.getAPRV_ROLE_CODE()),
						role.getAPRV_ROLE_CODE() + " : " + role.getAPRV_ROLE_NAME(), role.getAPRV_ROLE_CODE() });
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
	protected List<AprvRole> getAprvRole(AprvRoleSearchCondition searchCondition) throws TException {

		List<AprvRole> list = (List<AprvRole>) request(getManagerClass(), "get", searchCondition);

		return list;
	}

	/**
	 * �`�[��ʒ��o�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getManagerClass() {
		return AprvRoleManager.class;
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
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TAprvRoleListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TAprvRoleListSelector.SC.VALUE);
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
	public AprvRoleSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition ��������
	 */
	public void setCondition(AprvRoleSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * ��ʂɕ\������Ă��鏳�F���[�����X�g���擾
	 * 
	 * @return ���F���[�����X�g
	 */
	public List<AprvRole> getEntityList() {
		return roleList;
	}

}
