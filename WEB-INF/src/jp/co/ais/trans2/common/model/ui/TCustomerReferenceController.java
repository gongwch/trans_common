package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * ����挟���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TCustomerReferenceController extends TReferenceController {

	/** �������� */
	protected CustomerSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TCustomerReferenceController(TReference field) {
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
	protected CustomerSearchCondition createSearchCondition() {
		return new CustomerSearchCondition();
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

		// �l�̕ύX���Ȃ����̓f�[�^���擾���Ȃ�
		if (!field.code.isValueChanged()) {
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

			field.code.setText(getEntity().getCode());
			field.name.setText(getEntity().getNames());
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
			CustomerSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNamesLike(dialog.names.getText());
			// �������̂����܂�
			condition_.setNamekLike(dialog.namek.getText());

			List<Customer> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Customer bean : list) {
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

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
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
	 * �t�B�[���h����R�[�h�𒼐ړ��͂����ꍇ�̌���
	 * 
	 * @return �������
	 */
	protected Customer getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			CustomerSearchCondition condition_ = condition.clone();
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Customer> list = getList(condition_);

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
	protected CustomerSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return �����Entity
	 * @throws TException
	 */
	protected List<Customer> getList(CustomerSearchCondition condition_) throws TException {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";

				if (getField().getCompanyOrgUnit() != null) {
					// ��ЃR�[�h���擾
					List<String> companyCodeList = TCompanyClientUtil.getCodeList(this, getField().getCompanyOrgUnit());

					if (companyCodeList == null || companyCodeList.isEmpty()) {
						// ��Ђ��擾�o���Ȃ�
						return null;
					}
					condition_.setCompanyCodeList(companyCodeList);
				}
			} else {
				condition_.setCompanyCodeList(null);
			}

			List<Customer> list = (List<Customer>) request(getModelClass(), method, condition_);
			return list;
		} catch (Exception ex) {
			throw new TException(ex);
		}
	}

	/**
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return CustomerManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return Entity
	 */
	protected Customer getSelectedEntity() {
		return (Customer) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00408"; // �����
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00408"; // �����
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Customer getEntity() {
		return (Customer) super.getEntity();
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TCustomerReference getField() {
		return (TCustomerReference) field;
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void setEntity(Customer entity) {
		if (entity == null) {
			clear();
		} else {
			field.code.setText(entity.getCode());
			field.name.setText(entity.getNames());
			this.entity = entity;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		Customer bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Customer entity1 = createEntity();
		entity1.setCode(field.code.getText());
		entity1.setNames(field.name.getText());
		return entity1;
	}

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	@Override
	public Customer createEntity() {
		return new Customer();
	}

}
