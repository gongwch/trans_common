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
public class TAprvRoleGroupReferenceController extends TReferenceController {

	/** �������� */
	protected AprvRoleGroupSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TAprvRoleGroupReferenceController(TReference field) {
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
	protected AprvRoleGroupSearchCondition createSearchCondition() {
		return new AprvRoleGroupSearchCondition();
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
			field.code.setText(getEntity().getAPRV_ROLE_GRP_CODE());
			field.name.setText(getEntity().getAPRV_ROLE_GRP_NAME_S());
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
			AprvRoleGroupSearchCondition param = getCondition().clone();
			param.setCodeLike(dialog.code.getText());
			param.setNamesLike(dialog.names.getText());
			param.setNamekLike(dialog.namek.getText());

			List<AprvRoleGroup> list = getList(param);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (AprvRoleGroup bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getAPRV_ROLE_GRP_CODE(), bean.getAPRV_ROLE_GRP_NAME_S(),
						bean.getAPRV_ROLE_GRP_NAME_K() });
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
				field.code.setText(getEntity().getAPRV_ROLE_GRP_CODE());
				field.name.setText(getEntity().getAPRV_ROLE_GRP_NAME_S());
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
	protected AprvRoleGroup getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			AprvRoleGroupSearchCondition param = createSearchCondition();
			param.setCompanyCode(this.condition.getCompanyCode());
			param.setCode(field.getCode());

			List<AprvRoleGroup> list = getList(param);

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
	protected AprvRoleGroupSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param param
	 * @return ���[�����
	 */
	protected List<AprvRoleGroup> getList(AprvRoleGroupSearchCondition param) {

		try {
			List<AprvRoleGroup> list = (List<AprvRoleGroup>) request(getModelClass(), "get", param);
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
		return AprvRoleGroupManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return �G���e�B�e�B
	 */
	protected AprvRoleGroup getSelectedEntity() {
		return (AprvRoleGroup) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// ���F�O���[�v
		return getWord("C12229");
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// ���F�O���[�v
		return getWord("C12229");
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public AprvRoleGroup getEntity() {
		return (AprvRoleGroup) super.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param aprvRole
	 */
	public void setEntity(AprvRoleGroup aprvRole) {
		if (aprvRole == null) {
			clear();
			return;
		}

		field.code.setText(aprvRole.getAPRV_ROLE_GRP_CODE());
		field.name.setText(aprvRole.getAPRV_ROLE_GRP_NAME_S());

		entity = aprvRole;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		AprvRoleGroup bean = new AprvRoleGroup();
		bean.setAPRV_ROLE_GRP_CODE(field.code.getText());
		bean.setAPRV_ROLE_GRP_NAME_S(field.name.getText());
		return bean;
	}

}
