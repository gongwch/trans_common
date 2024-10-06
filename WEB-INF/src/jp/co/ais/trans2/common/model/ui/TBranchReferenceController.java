package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bank.*;

/**
 * �x�X�����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TBranchReferenceController extends TReferenceController {

	/** �������� */
	protected BranchSearchCondition condition;

	/**
	 * @param field �x�X�R���|�[�l���g
	 */
	public TBranchReferenceController(TReference field) {
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
	protected BranchSearchCondition createSearchCondition() {
		return new BranchSearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

	/**
	 * �t�B�[���h[�R�[�h]��verify
	 * 
	 * @param comp �R���|�[�l���g
	 * @return false:NG
	 */
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

			// �L�������`�F�b�N
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getBranch().getCode());
			field.name.setText(getEntity().getBranch().getNames());
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
				Date from = getEntity().getDateFrom();
				Date to = getEntity().getDateTo();
				if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
					|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {

					if (!showConfirmMessage("Q00025", getEntity().getCode())) {
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
			BranchSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNamesLike(dialog.names.getText());
			// �������̂����܂�
			condition_.setNamekLike(dialog.namek.getText());

			List<Bank> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Bank bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getBranch().getCode(), bean.getBranch().getNames(),
						bean.getBranch().getNamek() });
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
				field.code.setText(getEntity().getBranch().getCode());
				field.name.setText(getEntity().getBranch().getNames());
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

			field.code.pushOldText();

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
		return BankManager.class;
	}

	/**
	 * ���͂��ꂽ��s������Ԃ�
	 * 
	 * @return Entity
	 */
	protected Bank getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			BranchSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<Bank> list = getList(searchCondition);

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
	 * ���������ɊY������x�X���X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY������x�X���X�g
	 */
	@SuppressWarnings("unchecked")
	protected List<Bank> getList(BranchSearchCondition condition_) {

		try {

			List<Bank> list = (List<Bank>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public BranchSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �I�����ꂽ��s�x�X��Ԃ�
	 * 
	 * @return �I�����ꂽ��s�x�X
	 */
	protected Bank getSelectedEntity() {
		return (Bank) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00778";// ��s�x�X
	}

	/**
	 * ���̂̃L���v�V�������擾���܂�
	 * 
	 * @return ���̂̃L���v�V����
	 */
	@Override
	public String getNamesCaption() {
		return "C00783";// ��s�x�X����
	}

	/**
	 * �������̂̃L���v�V�������擾���܂�
	 * 
	 * @return �������̂̃L���v�V����
	 */
	@Override
	public String getNamekCaption() {
		return "C00785";// ��s�x�X��������
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00222";// �x�X
	}

	/**
	 * ���̂̃J�����T�C�Y���擾���܂�
	 * 
	 * @return ���̂̃J�����T�C�Y
	 */
	@Override
	public int getNamesColumnSize() {
		return 300;
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Bank getEntity() {
		return (Bank) super.getEntity();
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TBranchReference getField() {
		return (TBranchReference) field;
	}

	/**
	 * ����������
	 * 
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#clear()
	 */
	@Override
	public void clear() {
		super.clear();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param bank �x�X
	 */
	public void setEntity(Bank bank) {
		if (bank == null) {
			clear();
			return;
		}

		field.code.setText(bank.getBranch().getCode());
		field.name.setText(bank.getBranch().getNames());
		entity = bank;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Bank entity1 = new Bank();
		entity1.getBranch().setCode(field.code.getText());
		entity1.getBranch().setNames(field.name.getText());
		return entity1;
	}

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	@Override
	public TReferable createEntity() {
		return new Bank();
	}

}
