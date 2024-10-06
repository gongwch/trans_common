package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Port�̌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TPortReferenceController extends TReferenceController {

	/** �������� */
	protected PortSearchCondition condition;

	/** �t�B�[���h */
	protected TPortReference ctrl;

	/** ��ЃR�[�h���؂��邩 */
	protected boolean verifyCompanyCode = true;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TPortReferenceController(TReference field) {
		super(field);
		this.ctrl = (TPortReference) field;
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
	protected PortSearchCondition createSearchCondition() {
		return new PortSearchCondition();
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
			clear();
			return true;
		}

		// �l�̕ύX���Ȃ����̓f�[�^���擾���Ȃ�
		if (!field.code.isValueChanged()) {
			return true;
		}

		// ��ЃR�[�h�`�F�b�N
		if (!companyCode_Verify()) {
			clear();
			return false;
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

		// �G���e�B�e�B�����݂��Ȃ��ꍇ�͗��̃N���A
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
	 * ��ЃR�[�h���Z�b�g����Ă��邩�m�F����
	 * 
	 * @return ��ЃR�[�h�`�F�b�N����(True:�G���[�Ȃ��AFalse�F�G���[����)
	 */
	protected boolean companyCode_Verify() {
		if (!verifyCompanyCode) {
			// ���ؕs�v�̏ꍇ�A�G���[�Ȃ�
			return true;
		}

		// ��ЃR�[�h���ݒ肳��Ă��Ȃ��ꍇ�̓��b�Z�[�W�\��
		if (Util.isNullOrEmpty(this.ctrl.companyCode)) {
			showMessage(field, "I00003", "C00596"); // �w�肵�Ă��������B��ЃR�[�h
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
			PortSearchCondition condition_ = getCondition().clone();

			if (Util.isNullOrEmpty(this.ctrl.companyCode)) {
				condition_.setCompanyCode(getCompanyCode());
			} else {
				condition_.setCompanyCode(this.ctrl.companyCode);
			}

			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			condition_.setNamekLike(dialog.namek.getText());

			List<Port> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Port bean : list) {
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
			Port newEntity = getSelectedEntity();

			// ����̃R�[�h���I�����ꂽ�ꍇ�́A�����Ȃ�
			if (field.code.getText().equals(newEntity.getCode())) {
				dialog.setVisible(false);
				dialog.dispose();
				field.code.requestFocus();
				return;
			}

			entity = getSelectedEntity();

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
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
	 * @return entity
	 */
	protected Port getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			PortSearchCondition condition_ = getCondition().clone();

			if (Util.isNullOrEmpty(this.ctrl.companyCode)) {
				condition_.setCompanyCode(getCompanyCode());
			} else {
				condition_.setCompanyCode(this.ctrl.companyCode);
			}
			condition_.setCode(field.getCode());
			// ���ړ��͂̏ꍇ�͔͈͎w����l�������Ȃ�
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);
			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Port> list = getList(condition_);

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
	 * @return ����������Ԃ�
	 */
	protected PortSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return Port���
	 */
	protected List<Port> getList(PortSearchCondition condition_) {
		try {
			List<Port> list = (List<Port>) request(getManager(), "get", condition_);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * �}�l�[�W����Ԃ�
	 * 
	 * @return PortManager
	 */
	protected Class getManager() {
		return PortManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return entity
	 */
	protected Port getSelectedEntity() {
		return (Port) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return getWord("C00510"); // Port
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getWord("C00510"); // Port
	}

	/**
	 * �����_�C�A���O(Port�p)�̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �����_�C�A���O
	 */
	@Override
	protected TReferenceDialog createDialog() {
		return new TReferenceDialog(this);
	}

	/**
	 * �t�B�[���h[�{�^��]����
	 */
	@Override
	public void btn_Click() {

		// ��ЃR�[�h���ݒ肳��Ă��Ȃ��ꍇ�̓��b�Z�[�W�\�����ȍ~�̏����͍s��Ȃ�
		if (!companyCode_Verify()) {
			return;
		}

		super.btn_Click();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public Port getEntity() {
		return (Port) super.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param pePort �G���e�B�e�B
	 */
	public void setEntity(Port pePort) {
		if (pePort == null) {
			clear();
		} else {
			field.code.setText(pePort.getCode());
			field.name.setText(pePort.getNames());
			entity = pePort;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		Port bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Port bean = new Port();
		bean.setCode(field.code.getText());
		bean.setNames(field.name.getText());
		return bean;
	}

	/**
	 * ��ЃR�[�h���؂��邩�̎擾
	 * 
	 * @return verifyCompanyCode ��ЃR�[�h���؂��邩
	 */
	public boolean isVerifyCompanyCode() {
		return verifyCompanyCode;
	}

	/**
	 * ��ЃR�[�h���؂��邩�̐ݒ�
	 * 
	 * @param verifyCompanyCode ��ЃR�[�h���؂��邩
	 */
	public void setVerifyCompanyCode(boolean verifyCompanyCode) {
		this.verifyCompanyCode = verifyCompanyCode;
	}

}
