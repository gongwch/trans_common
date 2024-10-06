package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уA�C�e���}�X�^�����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TMlitItemReferenceController extends TReferenceController {

	/** �������� */
	protected YJItemSearchCondition condition;

	/**
	 * @param field �A�����уA�C�e���}�X�^�R���|�[�l���g
	 */
	public TMlitItemReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// ��������������
		initSearchCondition();
		setShow3rdColumn(false);
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected YJItemSearchCondition createSearchCondition() {
		return new YJItemSearchCondition();
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
	 * @return false:NG
	 */
	@Override
	public boolean code_Verify(JComponent comp) {

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

			field.name.setText(getEntity().getITEM_NAME());
			return true;
		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "W00081");// �Y���R�[�h�͑��݂��܂���
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
			YJItemSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setItemCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setItemNameLike(dialog.names.getText());

			List<YJItem> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (YJItem bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getITEM_CODE(), bean.getITEM_NAME() });
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
				field.code.setText(getEntity().getITEM_CODE());
				field.name.setText(getEntity().getITEM_NAME());
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
	 * Class��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return YJItemManager.class;
	}

	/**
	 * ���͂��ꂽ�A�����уA�C�e���}�X�^��Ԃ�
	 * 
	 * @return Entity
	 */
	protected YJItem getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			YJItemSearchCondition searchCondition = condition.clone();
			searchCondition.setItemCode(field.getCode());

			List<YJItem> list = getList(searchCondition);

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
	 * ���������ɊY������A�����уA�C�e���}�X�^���X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY������A�����уA�C�e���}�X�^���X�g
	 */
	protected List<YJItem> getList(YJItemSearchCondition condition_) {
		try {
			List<YJItem> list = (List<YJItem>) request(getModelClass(), "getItems", condition_);
			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public YJItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �I�����ꂽ�A�����уA�C�e���}�X�^��Ԃ�
	 * 
	 * @return �I�����ꂽ�A�����уA�C�e���}�X�^
	 */
	protected YJItem getSelectedEntity() {
		return (YJItem) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return field.btn.getText() + " ";
	}

	/**
	 * �R�[�h�̃L���v�V�������擾���܂�
	 * 
	 * @return �R�[�h�̃L���v�V����
	 */
	@Override
	public String getCodeCaption() {
		return getWord("Item Code"); // Item Code
	}

	/**
	 * ���̂̃L���v�V�������擾���܂�
	 * 
	 * @return ���̂̃L���v�V����
	 */
	@Override
	public String getNameCaption() {
		return getWord("Item Name"); // Item Name
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getWord("Item Code"); // Item Code
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public YJItem getEntity() {
		return (YJItem) entity;
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param bean �A�����уA�C�e���}�X�^
	 */
	public void setEntity(YJItem bean) {
		if (bean == null) {
			clear();
			return;
		}

		field.code.setText(bean.getITEM_CODE());
		field.name.setText(bean.getITEM_NAME());
		entity = bean;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		YJItem bean = new YJItem();
		bean.setITEM_CODE(field.code.getText());
		bean.setITEM_NAME(field.name.getText());
		return bean;
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		YJItem bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
			return;
		}
		setEntity(bean);
	}
}
