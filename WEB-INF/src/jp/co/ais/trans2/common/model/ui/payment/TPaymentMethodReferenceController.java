package jp.co.ais.trans2.common.model.ui.payment;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �x�����@�����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TPaymentMethodReferenceController extends TReferenceController {

	/** �������� */
	protected PaymentMethodSearchCondition condition;

	/**
	 * @param field �x�����@�R���|�[�l���g
	 */
	public TPaymentMethodReferenceController(TReference field) {
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
	protected PaymentMethodSearchCondition createSearchCondition() {
		return new PaymentMethodSearchCondition();
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

			// ��s�����A���`�F�b�N
			checkPaymentKind();
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
			field.name.setText(getEntity().getName());

			// ��s�����A���`�F�b�N
			checkPaymentKind();
			return true;

		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123");// �Y���R�[�h�͑��݂��܂���
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}

		// ��s�����A���`�F�b�N
		checkPaymentKind();
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
			PaymentMethodSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNameLike(dialog.names.getText());
			// �������̂����܂�
			condition_.setNamekLike(dialog.namek.getText());

			List<PaymentMethod> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (PaymentMethod bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getName(), bean.getNamek() });
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
				field.name.setText(getEntity().getName());
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

			// ��s�����A���`�F�b�N
			checkPaymentKind();

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
		return PaymentMethodManager.class;
	}

	/**
	 * ���͂��ꂽ�x�����@��Ԃ�
	 * 
	 * @return Entity
	 */
	protected PaymentMethod getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			PaymentMethodSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<PaymentMethod> list = getList(searchCondition);

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
	 * ���������ɊY������x�����@���X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY������x�����@���X�g
	 */
	protected List<PaymentMethod> getList(PaymentMethodSearchCondition condition_) {

		try {

			List<PaymentMethod> list = (List<PaymentMethod>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public PaymentMethodSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �I�����ꂽ�x�����@��Ԃ�
	 * 
	 * @return �I�����ꂽ�x�����@
	 */
	protected PaymentMethod getSelectedEntity() {
		return (PaymentMethod) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00233";// �x�����@
	}

	/**
	 * ���̂̃L���v�V�������擾���܂�
	 * 
	 * @return ���̂̃L���v�V����
	 */
	@Override
	public String getNamesCaption() {
		return "C00865";// �x�����@��
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00233";// �x�����@
	}

	/**
	 * ��s�����A���`�F�b�N
	 */
	protected void checkPaymentKind() {

		// ��s�����t�B�[���h
		TBankAccountReference ref = ((TPaymentMethodReference) field).ctrlBankAccount;
		// �G���[�̎x�����@�����R�[�h
		PaymentKind[] errlist = ((TPaymentMethodReference) field).errPaymentKinds;

		// �A���̐ݒ肪�Ȃ��ꍇ�͏����I��
		if (ref == null || errlist == null) {
			return;
		}

		// ��s�����t�B�[���h ������
		ref.clear();
		ref.setEditable(true);

		// �G���e�B�e�B���Ȃ��ꍇ�͏����I��
		if (getEntity() == null) {
			ref.setEditable(false);
			return;
		}

		// �ێ����Ă�������R�[�h�Ɠ��͂��������R�[�h���r
		PaymentKind kind = getEntity().getPaymentKind();
		for (int i = 0; i < errlist.length; i++) {

			// ��v����ꍇ�͋�s�����t�B�[���h��ҏW�s��
			if (kind.equals(errlist[i])) {
				ref.setEditable(false);
				break;
			}
		}

		// �x�����@�����R�[�h�ɂ��A��s�����̌���������ύX����
		switch (kind) {
			case EX_PAY_FB:
				ref.getSearchCondition().setUseEmployeePayment(false);
				ref.getSearchCondition().setUseExPayment(true);
				break;

			case EMP_PAY_ACCRUED:
			case EMP_PAY_UNPAID:
				ref.getSearchCondition().setUseEmployeePayment(true);
				ref.getSearchCondition().setUseExPayment(false);
				break;

			default:
				ref.getSearchCondition().setUseEmployeePayment(false);
				ref.getSearchCondition().setUseExPayment(false);
				break;
		}
	}

	/**
	 * ��s�����̓��͂��K�v�Ȏx�����@���ǂ���
	 * 
	 * @return ��s�����̓��͂��K�v�Ȏx�����@���ǂ���
	 */
	protected boolean isNeedInputAccount() {

		// ��s�����t�B�[���h
		TBankAccountReference ref = ((TPaymentMethodReference) field).ctrlBankAccount;
		// �G���[�̎x�����@�����R�[�h
		PaymentKind[] errlist = ((TPaymentMethodReference) field).errPaymentKinds;

		// �A���̐ݒ肪�Ȃ��ꍇ��true
		if (ref == null || errlist == null) {
			return true;
		}

		// �G���e�B�e�B���Ȃ��ꍇ��false
		if (getEntity() == null) {
			return false;
		}

		// �ێ����Ă�������R�[�h�Ɠ��͂��������R�[�h���r
		PaymentKind kind = getEntity().getPaymentKind();
		for (int i = 0; i < errlist.length; i++) {

			// ��v����ꍇ�͋�s�����t�B�[���h��ҏW�s��
			if (kind.equals(errlist[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public PaymentMethod getEntity() {
		return (PaymentMethod) super.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param PaymentMethod �x�����@
	 */
	public void setEntity(PaymentMethod PaymentMethod) {
		if (PaymentMethod == null) {
			clear();
			return;
		}

		field.code.setText(PaymentMethod.getCode());
		field.name.setText(PaymentMethod.getName());
		entity = PaymentMethod;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		PaymentMethod entity_ = new PaymentMethod();
		entity_.setCode(field.code.getText());
		entity_.setName(field.name.getText());
		return entity_;
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		PaymentMethod entity_ = getInputtedEntity();
		if (entity_ == null) {
			this.clear();
		} else {
			entity = entity_;
		}
	}

}
