package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�������[���}�X�^�����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TAprvRoleReferenceController extends TReferenceController {

	/** �������� */
	protected AprvRoleSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TAprvRoleReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// ��������������
		initSearchCondition();

	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected AprvRoleSearchCondition createSearchCondition() {
		return new AprvRoleSearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// �l���u�����N�̏ꍇ�A���̂�������
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			return true;
		}

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		entity = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (entity != null) {
			field.code.setText(getEntity().getAPRV_ROLE_CODE());
			field.name.setText(getEntity().getAPRV_ROLE_NAME_S());
			return true;

		}
		field.name.setText(null);
		if (checkExist) {
			// �Y���R�[�h�͑��݂��܂���
			showMessage(field, "I00123");
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}
		return true;

	}

	/**
	 * �����_�C�A���O[����]�{�^������
	 */
	@Override
	public void btnSearch_Click() {

		try {

			// �m��{�^���������s�ɂ���
			dialog.btnSettle.setEnabled(false);

			// �ꗗ���N���A����
			dialog.tbl.removeRow();

			// �f�[�^�𒊏o����
			AprvRoleSearchCondition param = getCondition().clone();
			param.setCodeLike(dialog.code.getText());
			param.setNamesLike(dialog.names.getText());
			param.setNamekLike(dialog.namek.getText());

			List<AprvRole> list = getList(param);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (AprvRole bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getAPRV_ROLE_CODE(), bean.getAPRV_ROLE_NAME_S(),
						bean.getAPRV_ROLE_NAME_K() });
			}

			// �m��{�^���������\�ɂ���
			dialog.btnSettle.setEnabled(true);

			// 1�s�ڂ�I��
			dialog.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �����_�C�A���O[�m��]�{�^������
	 */
	@Override
	public void btnSettle_Click() {

		try {

			// �ꗗ�őI�����ꂽEntity���擾����
			entity = getSelectedEntity();

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				field.code.setText(getEntity().getAPRV_ROLE_CODE());
				field.name.setText(getEntity().getAPRV_ROLE_NAME_S());
				field.code.pushOldText();
			}

			// �_�C�A���O�����
			dialog.setVisible(false);
			dialog.dispose();

			// �Ăяo�����̃R�[�h�t�B�[���h�Ƀt�H�[�J�X
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(entity != null);
					listener.afterVerify(entity != null);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �t�B�[���h����R�[�h�𒼐ړ��͂����ꍇ�̌���
	 * 
	 * @return �G���e�B�e�B
	 */
	protected AprvRole getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			AprvRoleSearchCondition param = new AprvRoleSearchCondition();
			param.setCompanyCode(this.condition.getCompanyCode());
			param.setCode(field.getCode());

			List<AprvRole> list = getList(param);

			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @return �_�C�A���O��ʂł̌���������ݒ�
	 */
	protected AprvRoleSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param param
	 * @return ���[�����
	 */
	protected List<AprvRole> getList(AprvRoleSearchCondition param) {

		try {
			List<AprvRole> list = (List<AprvRole>) request(getModelClass(), "get", param);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return ���f���N���X
	 */
	protected Class getModelClass() {
		return AprvRoleManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return �G���e�B�e�B
	 */
	protected AprvRole getSelectedEntity() {
		return (AprvRole) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// ���F�������[��
		return getWord("C12238");
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// ���F�������[��
		return getWord("C12238");
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public AprvRole getEntity() {
		return (AprvRole) super.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param aprvRole
	 */
	public void setEntity(AprvRole aprvRole) {
		if (aprvRole == null) {
			clear();
			return;
		}

		field.code.setText(aprvRole.getAPRV_ROLE_CODE());
		field.name.setText(aprvRole.getAPRV_ROLE_NAME_S());

		entity = aprvRole;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		AprvRole entity = new AprvRole();
		entity.setAPRV_ROLE_CODE(field.code.getText());
		entity.setAPRV_ROLE_NAME_S(field.name.getText());
		return entity;
	}

}
