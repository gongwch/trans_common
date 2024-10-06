package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s���������R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TBankAccountReferenceController extends TReferenceController {

	/** �������� */
	protected BankAccountSearchCondition condition;

	/**
	 * @param field ��s�����R���|�[�l���g
	 */
	public TBankAccountReferenceController(TReference field) {
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
	protected BankAccountSearchCondition createSearchCondition() {
		return new BankAccountSearchCondition();
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
			getField().accountNo.setText(null);
			return true;
		}

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		entity = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (entity != null) {

			// ���������ɊY�����邩���`�F�b�N
			BankAccountSearchCondition condition_ = getCondition();

			// �Ј��x���`�F�b�N
			if (condition_.isUseEmployeePayment() && !getEntity().isUseEmployeePayment()) {
				if (!showConfirmMessage(field, "Q00052", "C01117")) {
					field.code.clearOldText();
					field.code.requestFocus();
					return false;
				}
			}

			// �ЊO�x���`�F�b�N
			if (condition_.isUseExPayment() && !getEntity().isUseExPayment()) {
				if (!showConfirmMessage(field, "Q00052", "C01122")) {
					field.code.clearOldText();
					field.code.requestFocus();
					return false;
				}
			}

			// �L�������`�F�b�N
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getCode());
			field.name.setText(getName(getEntity()));
			getField().accountNo.setText(getDepositKindAndAcountNoNo(getEntity()));
			return true;

		}

		field.name.setText(null);
		getField().accountNo.setText(null);

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
			BankAccountSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNameLike(dialog.names.getText());
			// �a������
			condition_.setDepositKind(((TBankAccountReferenceDialog) dialog).cmbDepositKind.getSelectedDepositKind());
			// �������̂����܂�
			condition_.setAccountNokLike(dialog.namek.getText());

			List<BankAccount> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (BankAccount bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), getName(bean),
						getWord(bean.getDepositKindName()), bean.getAccountNo() });
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
				field.name.setText(getName(getEntity()));
				getField().accountNo.setText(getDepositKindAndAcountNoNo(getEntity()));
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
		return BankAccountManager.class;
	}

	/**
	 * ���͂��ꂽ��s������Ԃ�
	 * 
	 * @return Entity
	 */
	protected BankAccount getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			BankAccountSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setUseEmployeePayment(false);
			searchCondition.setUseExPayment(false);
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<BankAccount> list = getList(searchCondition);

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
	 * ���������ɊY�������s�������X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY�������s�������X�g
	 */
	@SuppressWarnings("unchecked")
	protected List<BankAccount> getList(BankAccountSearchCondition condition_) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";
			}

			List<BankAccount> list = (List<BankAccount>) request(getModelClass(), method, condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public BankAccountSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �I�����ꂽ��s������Ԃ�
	 * 
	 * @return �I�����ꂽ��s����
	 */
	protected BankAccount getSelectedEntity() {
		return (BankAccount) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00857";// ��s����
	}

	/**
	 * ���̂̃L���v�V�������擾���܂�
	 * 
	 * @return ���̂̃L���v�V����
	 */
	@Override
	public String getNamesCaption() {
		return "C10361";// ��s��
	}

	/**
	 * �������̂̃L���v�V�������擾���܂�
	 * 
	 * @return �������̂̃L���v�V����
	 */
	@Override
	public String getNamekCaption() {
		return "C00794";// �����ԍ�
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00857";// ��s����
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
	public BankAccount getEntity() {
		return (BankAccount) super.getEntity();
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TBankAccountReference getField() {
		return (TBankAccountReference) field;
	}

	/**
	 * ����������
	 * 
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		getField().accountNo.setText("");
		getField().setDepCode("");
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param bankAccount ��s����
	 */
	public void setEntity(BankAccount bankAccount) {
		if (bankAccount == null) {
			clear();
			return;
		}

		field.code.setText(bankAccount.getCode());
		field.name.setText(getName(bankAccount));
		getField().accountNo.setText(getDepositKindAndAcountNoNo(bankAccount));
		entity = bankAccount;
	}

	/**
	 * ���̂̕\���l���擾����
	 * 
	 * @param bean
	 * @return ���̂̕\���l
	 */
	protected String getName(BankAccount bean) {

		if (bean == null) {
			return null;
		}

		if (ClientConfig.isFlagOn("trans.bankaccount.ref.name")) {
			return bean.getName();
		}

		return bean.getBankAndBranchName();
	}

	/**
	 * ������ʁ{�����ԍ���Ԃ�
	 * 
	 * @param bankAccount
	 * @return String
	 */
	protected String getDepositKindAndAcountNoNo(BankAccount bankAccount) {

		String account = getWord(DepositKind.getDepositKindName(bankAccount.getDepositKind())) + " "
			+ Util.avoidNull(bankAccount.getAccountNo());

		return account;
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		BankAccount bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
			return;
		}

		setEntity(bean);
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		BankAccount entity_ = new BankAccount();
		entity_.setCode(field.code.getText());
		entity_.setName(field.name.getText());
		return entity_;
	}

	/**
	 * �����_�C�A���O(��s�����p)�̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �����_�C�A���O
	 */
	@Override
	protected TBankAccountReferenceDialog createDialog() {
		return new TBankAccountReferenceDialog(this);
	}

	/**
	 * �v�㕔��R�[�h�̏����Z�b�g �A���J�[�J�X�^�}�C�Y
	 * 
	 * @param code ��s����
	 */
	public void setDepCode(String code) {
		condition.setDeptCode(code);
	}

}
