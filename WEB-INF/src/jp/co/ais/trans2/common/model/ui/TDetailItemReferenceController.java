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
 * ����Ȗڌ����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TDetailItemReferenceController extends TReferenceController {

	/** �������� */
	protected DetailItemSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TDetailItemReferenceController(TReference field) {
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
	protected DetailItemSearchCondition createSearchCondition() {
		return new DetailItemSearchCondition();
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
		Item entity_ = getInputtedEntity();
		this.entity = entity_;

		// �l������Η��̂��Z�b�g
		if (entity_ != null && entity_.getSubItem() != null && entity_.getSubItem().getDetailItem() != null) {

			// �L�������`�F�b�N
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(entity_.getSubItem().getDetailItem().getCode());
			field.name.setText(entity_.getSubItem().getDetailItem().getNames());
			return true;
		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123");// �Y���R�[�h�͑��݂��܂���
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}
		return true;

	}

	/**
	 * �L�������`�F�b�N
	 * 
	 * @return true:�L�����ԓ��Afalse:�L�����ԊO
	 */
	protected boolean isInValidTerm() {
		if (getCompany().getAccountConfig().isSlipTermCheck()) {
			Date validTerm = condition.getValidTerm();

			if (validTerm != null) {
				Date from = getEntity().getDetailItem().getDateFrom();
				Date to = getEntity().getDetailItem().getDateTo();
				if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
					|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {

					if (!showConfirmMessage("Q00025", getEntity().getDetailItem().getCode())) {
						field.code.requestFocus();
						field.code.clearOldText();
						return false;
					}

					field.code.requestFocus();
				}
			}
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
			DetailItemSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNamesLike(dialog.names.getText());
			// �������̂����܂�
			condition_.setNamekLike(dialog.namek.getText());

			List<Item> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Item bean : list) {
				if (bean.getSubItem() != null && bean.getSubItem().getDetailItem() != null) {
					dialog.tbl
						.addRow(new Object[] { bean, bean.getSubItem().getDetailItem().getCode(),
								bean.getSubItem().getDetailItem().getNames(),
								bean.getSubItem().getDetailItem().getNamek() });
				}
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
			Item newEntity = getSelectedEntity();

			// ����̃R�[�h���I�����ꂽ�ꍇ�́A�����Ȃ�
			if (field.code.getText().equals(newEntity.getDetailItemCode())) {
				dialog.setVisible(false);
				dialog.dispose();
				field.code.requestFocus();
				return;
			}

			this.entity = newEntity;

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (newEntity != null && newEntity.getSubItem() != null && newEntity.getSubItem().getDetailItem() != null) {
				field.code.setText(newEntity.getSubItem().getDetailItem().getCode());
				field.name.setText(newEntity.getSubItem().getDetailItem().getNames());
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
	 * @return Entity
	 */
	protected Item getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			DetailItemSearchCondition condition_ = condition.clone();
			condition_.setItemCode(this.condition.getItemCode());
			condition_.setSubItemCode(this.condition.getSubItemCode());
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Item> list = getList(condition_);

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
	protected DetailItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return ����Ȗڏ��
	 */
	protected List<Item> getList(DetailItemSearchCondition condition_) {

		try {
			List<Item> list = (List<Item>) request(getModelClass(), "get", condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * ���f���N���X
	 * 
	 * @return ���f���N���X
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return Entity
	 */
	protected Item getSelectedEntity() {
		return (Item) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return getCompany().getAccountConfig().getDetailItemName();
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getCompany().getAccountConfig().getDetailItemName();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Item getEntity() {
		return (Item) super.getEntity();
	}

	/**
	 * Entity���Z�b�g
	 * 
	 * @param item Entity
	 */
	public void setEntity(Item item) {
		if (item == null) {
			clear();
			return;
		}

		field.code.setText(item.getDetailItemCode());
		field.name.setText(item.getDetailItemNames());
		entity = item;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Item entity_ = new Item();
		entity_.setCode(field.code.getText());
		entity_.setNames(field.name.getText());
		return entity_;
	}

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	@Override
	public TReferable createEntity() {
		return new DetailItem();
	}

	/**
	 * (����ȖڃR�[�h������)
	 * 
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#isValueChanged()
	 */
	@Override
	public boolean isValueChanged() {

		if ((oldEntity != null && entity == null) || (oldEntity == null && entity != null)) {
			return true;
		}

		Item oldItem = (Item) oldEntity;
		Item newItem = (Item) entity;

		// ���R�[�h
		String oldCode = "";
		if (oldItem != null && oldItem.getDetailItem() != null) {
			oldCode = Util.avoidNull(oldItem.getDetailItem().getCode());
		}

		String newCode = "";
		if (newItem != null && newItem.getDetailItem() != null) {
			newCode = Util.avoidNull(newItem.getDetailItem().getCode());
		}

		return !oldCode.equals(newCode);
	}
}
