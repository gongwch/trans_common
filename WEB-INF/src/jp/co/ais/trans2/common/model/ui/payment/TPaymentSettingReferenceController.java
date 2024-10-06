package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.event.*;
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
 * �x�����������R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TPaymentSettingReferenceController extends TReferenceController {

	/** �������`�J�i���g�� */
	public static boolean IS_USE_ACCOUNT_KANA = ClientConfig.isFlagOn("trans.tri.sj.ref.use.account.kana");

	/** �������� */
	protected PaymentSettingSearchCondition condition;

	/**
	 * @param field �x�������R���|�[�l���g
	 */
	public TPaymentSettingReferenceController(TReference field) {
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
	protected PaymentSettingSearchCondition createSearchCondition() {
		return new PaymentSettingSearchCondition();
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
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

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

			// �L�������`�F�b�N
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getCode());
			field.name.setText(getBankAndBranchName(getEntity()));

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
			PaymentSettingSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNameLike(dialog.names.getText());

			List<PaymentSetting> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			} else {
				// �t�B���^�[���s��

				List<PaymentSetting> newList = new ArrayList<PaymentSetting>();

				String namek = dialog.namek.getText();
				String accountKana = "";

				if (IS_USE_ACCOUNT_KANA && dialog instanceof TPaymentSettingReferenceDialog) {
					accountKana = ((TPaymentSettingReferenceDialog) dialog).accountKana.getText();
				}

				for (PaymentSetting bean : list) {

					// �����ԍ��s��v
					if (!Util.isNullOrEmpty(namek) && !getDepositKindAndAcountNoNo(bean).contains(namek)) {
						continue;
					}

					// �������`�J�i�s��v
					if (IS_USE_ACCOUNT_KANA) {
						if (!Util.isNullOrEmpty(accountKana)
								&& !Util.avoidNullNT(bean.getAccountNameKana()).contains(accountKana)) {
							continue;
						}
					}

					newList.add(bean);
				}

				list = newList;

				if (list.isEmpty()) {
					if (isCheckExist()) {
						showMessage(dialog, "I00051");
					}
					return;
				}
			}

			// �ꗗ�ɃZ�b�g
			for (PaymentSetting bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), getBankAndBranchName(bean),
						getDepositKindAndAcountNoNo(bean), bean.getAccountNameKana() });
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
	 * �u��s���{�x�X���v�̎擾<br>
	 * �a����ځF�O�݂̏ꍇ�́A�u��d����s���{��d���x�X���v
	 * 
	 * @param bean �x������
	 * @return ��s���Ǝx�X��
	 */
	protected String getBankAndBranchName(PaymentSetting bean) {

		if (bean == null) {
			return "";
		}
		if (bean.getDepositKind() == DepositKind.FOREIGN_CURRENCY) {
			if (Util.isNullOrEmpty(bean.getSendBankName()) && Util.isNullOrEmpty(bean.getSendBranchName())) {
				// �p����s���A�x�X�����ݒ�̏ꍇ�͋�s���Ǝx�X�����g��
				return bean.getBankAndBranchName();
			}
			return Util.avoidNullNT(bean.getSendBankName()) + " " + Util.avoidNullNT(bean.getSendBranchName());
		}

		return bean.getBankAndBranchName();
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
				field.name.setText(getBankAndBranchName(getEntity()));
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
	 * ������ʁ{�����ԍ���Ԃ�
	 * 
	 * @param entity_f
	 * @return String
	 */
	protected String getDepositKindAndAcountNoNo(PaymentSetting entity_) {
		boolean isHide = false;
		try {
			isHide = entity_.getCustomer().isPersonalCustomer();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
		String accountNo = Util.avoidNull(entity_.getAccountNo());
		if (isHide) {
			accountNo = "*********";
		}
		String account = getWord(DepositKind.getDepositKindName(entity_.getDepositKind())) + " "
				+ accountNo;

		return account;
	}

	/**
	 * Class��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return PaymentSettingManager.class;
	}

	/**
	 * ���͂��ꂽ�x��������Ԃ�
	 * 
	 * @return Entity
	 */
	protected PaymentSetting getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			PaymentSettingSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<PaymentSetting> list = getList(searchCondition);

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
	 * ���������ɊY������x���������X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY������x���������X�g
	 */
	protected List<PaymentSetting> getList(PaymentSettingSearchCondition condition_) {

		try {

			List<PaymentSetting> list = (List<PaymentSetting>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public PaymentSettingSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �I�����ꂽ�x��������Ԃ�
	 * 
	 * @return �I�����ꂽ�x������
	 */
	protected PaymentSetting getSelectedEntity() {
		return (PaymentSetting) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C10756";// �����x������
	}

	/**
	 * �R�[�h�̃L���v�V�������擾���܂�
	 * 
	 * @return �R�[�h�̃L���v�V����
	 */
	@Override
	public String getCodeCaption() {
		return "C10757";// ��������
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
		return "C01185";// �U�������
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
	public PaymentSetting getEntity() {
		return (PaymentSetting) super.getEntity();
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TPaymentSettingReference getField() {
		return (TPaymentSettingReference) field;
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
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param paymentSetting �x������
	 */
	public void setEntity(PaymentSetting paymentSetting) {
		if (paymentSetting == null) {
			clear();
			return;
		}

		field.code.setText(paymentSetting.getCode());
		field.name.setText(getBankAndBranchName(paymentSetting));
		getField().accountNo.setText(getDepositKindAndAcountNoNo(paymentSetting));
		entity = paymentSetting;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		PaymentSetting entity_ = new PaymentSetting();
		entity_.setCode(field.code.getText());
		return entity_;
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		PaymentSetting bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * �����_�C�A���O�̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �����_�C�A���O
	 */
	@Override
	protected TReferenceDialog createDialog() {
		if (IS_USE_ACCOUNT_KANA) {
			return new TPaymentSettingReferenceDialog(this);
		} else {
			return super.createDialog();
		}
	}

	/**
	 * �����_�C�A���O�Ŕ�������C�x���g��ǉ�����
	 */
	@Override
	protected void addDialogEvent() {
		super.addDialogEvent();

		if (IS_USE_ACCOUNT_KANA && dialog instanceof TPaymentSettingReferenceDialog) {
			// �������`�J�i��Enter�Ō������s
			((TPaymentSettingReferenceDialog) dialog).accountKana.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					dialog_code_keyPressed(e);
				}
			});
		}
	}

}
