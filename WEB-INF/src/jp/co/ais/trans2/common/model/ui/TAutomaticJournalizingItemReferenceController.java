package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �����d��Ȗڂ̌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TAutomaticJournalizingItemReferenceController extends TReferenceController {

	/** �������� */
	protected AutomaticJournalItemSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TAutomaticJournalizingItemReferenceController(TReference field) {
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
	protected AutomaticJournalItemSearchCondition createSearchCondition() {
		return new AutomaticJournalItemSearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

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

			field.code.setText(getEntity().getKMK_CNT());
			field.name.setText(getEntity().getKMK_CNT_NAME());
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
	 * �t�B�[���h����R�[�h�𒼐ړ��͂����ꍇ�̌���
	 * 
	 * @return entity
	 */
	protected AutomaticJournalItem getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			AutomaticJournalItemSearchCondition condition_ = condition.clone();
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			List<AutomaticJournalItem> list = getList(condition_);

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
			AutomaticJournalItemSearchCondition con = getCondition().clone();
			// �R�[�h�O����v
			con.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			con.setNamesLike(dialog.names.getText());

			List<AutomaticJournalItem> list = getList(con);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (AutomaticJournalItem bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getKMK_CNT(), bean.getKMK_CNT_NAME() });
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
				field.code.setText(getEntity().getKMK_CNT());
				field.name.setText(getEntity().getKMK_CNT_NAME());
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
	 * @return �_�C�A���O��ʂł̌���������ݒ�
	 */
	protected AutomaticJournalItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param con
	 * @return ���lEntity
	 */
	protected List<AutomaticJournalItem> getList(AutomaticJournalItemSearchCondition con) {

		try {
			List<AutomaticJournalItem> list = (List<AutomaticJournalItem>) request(getModelClass(), "get", con);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return AutomaticJournalItemManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return Remark
	 */
	protected AutomaticJournalItem getSelectedEntity() {
		return (AutomaticJournalItem) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00911"; // �����d��Ȗڃ}�X�^
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C01008"; // �Ȗڐ���敪
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public AutomaticJournalItem getEntity() {
		return (AutomaticJournalItem) super.getEntity();
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		AutomaticJournalItem entity_ = new AutomaticJournalItem();
		entity_.setKMK_CNT(field.code.getText());
		entity_.setKMK_CNT_NAME(field.name.getText());
		return entity_;
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void setEntity(AutomaticJournalItem entity) {
		if (entity == null) {
			clear();
			return;
		}

		field.code.setText(entity.getKMK_CNT());
		field.name.setText(entity.getKMK_CNT_NAME());
		this.entity = entity;
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public AutomaticJournalItem createEntity() {
		return new AutomaticJournalItem();
	}

}
