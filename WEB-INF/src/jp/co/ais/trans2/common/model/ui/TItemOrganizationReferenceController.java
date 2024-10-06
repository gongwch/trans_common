package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڑ̌n�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TItemOrganizationReferenceController extends TReferenceController {

	/** �������� */
	protected ItemOrganizationSearchCondition condition;

	/**
	 * @param field �Ȗڑ̌n�R���|�[�l���g
	 */
	public TItemOrganizationReferenceController(TReference field) {
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
	protected ItemOrganizationSearchCondition createSearchCondition() {
		return new ItemOrganizationSearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * �t�B�[���h[�R�[�h]��verify
	 * 
	 * @param comp �R���|�[�l���g
	 * @return flag
	 */
	@Override
	public boolean code_Verify(JComponent comp) {

		// �l���u�����N�̏ꍇ�A���̂�������
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			return true;
		}

		// �l�̕ύX���Ȃ����̓f�[�^���擾���Ȃ�
		if (!field.code.isValueChanged()) {
			return true;
		}

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		entity = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (entity != null) {
			field.name.setText(getEntity().getNames());
			return true;

		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123"); // �Y���R�[�h�͑��݂��܂���
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
			ItemOrganizationSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNamesLike(dialog.names.getText());
			// �������̂����܂�
			condition_.setNamekLike(dialog.namek.getText());

			List<ItemOrganization> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (ItemOrganization bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getNames(), bean.getNamek() });
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

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.before();
				}
			}

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
				field.code.pushOldText();
				field.code.getInputVerifier().verify(field.code);
			}

			// �_�C�A���O�����
			dialog.setVisible(false);
			dialog.dispose();

			// �Ăяo�����̃R�[�h�t�B�[���h�Ƀt�H�[�J�X
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(true);
					listener.afterVerify(true);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servlet��Class��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ����������Ԃ�
	 */
	protected ItemOrganizationSearchCondition getSearchCondition() {
		return condition;
	}

	/**
	 * ���͂��ꂽ�Ȗڑ̌n��Ԃ�
	 * 
	 * @return �Ȗڑ̌n
	 */
	protected ItemOrganization getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			ItemOrganizationSearchCondition param = createSearchCondition();
			param.setCompanyCode(this.condition.getCompanyCode());
			param.setCode(field.getCode());

			List<ItemOrganization> list = getList(param);

			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * ���������ɊY������Ȗڑ̌n���X�g��Ԃ�
	 * 
	 * @param param ��������
	 * @return ���������ɊY������Ȗڑ̌n���X�g
	 */
	protected List<ItemOrganization> getList(ItemOrganizationSearchCondition param) {

		try {

			List<ItemOrganization> list = (List<ItemOrganization>) request(getModelClass(), "getItemOrganization",
				param);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public ItemOrganizationSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition ����������ݒ肵�܂��B
	 */
	public void setCondition(ItemOrganizationSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �I�����ꂽ�Ȗڑ̌n��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ȗڑ̌n
	 */
	protected ItemOrganization getSelectedEntity() {
		return (ItemOrganization) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00609"; // �Ȗڑ̌n
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00609"; // �Ȗڑ̌n
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public ItemOrganization getEntity() {
		return (ItemOrganization) super.getEntity();
	}

	/**
	 * ��{�Ȗڑ̌n���Z�b�g����
	 */
	public void setBaseItemOrganization() {

		try {

			ItemOrganization itemOrganization = (ItemOrganization) request(getModelClass(), "getBaseItemOrganization",
				condition.getCompanyCode());

			setEntity(itemOrganization);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param itemOrganization �Ȗڑ̌n
	 */
	public void setEntity(ItemOrganization itemOrganization) {
		if (itemOrganization == null) {
			clear();
			return;
		}

		entity = itemOrganization;
		field.code.setText(itemOrganization.getCode());
		field.name.setText(itemOrganization.getNames());
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		ItemOrganization bean = new ItemOrganization();
		bean.setCode(field.code.getText());
		bean.setNames(field.name.getText());
		return bean;
	}

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	@Override
	public TReferable createEntity() {
		return new ItemOrganization();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		ItemOrganization item = getInputtedEntity();
		if (item == null) {
			this.clear();
		} else {
			entity = item;
		}
	}
}
