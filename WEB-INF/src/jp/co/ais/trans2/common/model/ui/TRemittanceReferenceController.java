package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * ���ێ��x�����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TRemittanceReferenceController extends TReferenceController {

	/** �������� */
	protected RemittanceSearchCondition condition;

	/** �V�����ړI�}�X�^���g�����ǂ����Atrue:�g�� */
	protected static final boolean isUseNewRemittance = ClientConfig.isFlagOn("trans.new.mp0100.use");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TRemittanceReferenceController(TRemittanceReference field) {
		super(field);
	}

	/**
	 * @return �V�����ړI���t�@�����X
	 */
	public TRemittanceReference getField() {
		return (TRemittanceReference) field;
	}

	@Override
	public void init() {

		super.init();

		if (isUseNewRemittance) {
			setShow3rdColumn(true);
		} else {
			setShow3rdColumn(false);
		}

		// ��������������
		initSearchCondition();
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected RemittanceSearchCondition createSearchCondition() {
		return new RemittanceSearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(getCompanyCode());
	}

	@Override
	public boolean code_Verify(JComponent comp) {

		// �l���u�����N�̏ꍇ�A���̂�������
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			if (isUseNewRemittance) {
				getField().balanceCode.setText(null);
			}
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
			field.code.setText(getEntity().getCode());
			if (isUseNewRemittance) {
				getField().balanceCode.setText(getEntity().getBalanceCode());
			}
			field.name.setText(getEntity().getName());
			return true;

		}

		if (isUseNewRemittance) {
			getField().balanceCode.setText(null);
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
	 * @return �V�����ړI
	 */
	protected Remittance getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			RemittanceSearchCondition remittanceCondition = condition.clone();
			remittanceCondition.setCompanyCode(getCompanyCode());
			remittanceCondition.setCode(field.getCode());

			List<Remittance> list = getList(remittanceCondition);

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
			RemittanceSearchCondition con = getCondition();
			// �R�[�h�O����v
			con.setCodeLike(dialog.code.getText());
			// ���ێ��x�R�[�h�����܂�
			con.setKanaLike(dialog.names.getText());
			// �����ړI���̂����܂�
			con.setNamekLike(dialog.namek.getText());

			List<Remittance> list = getList(con);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Remittance bean : list) {
				if (isUseNewRemittance) {
					dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getBalanceCode(), bean.getName() });
				} else {
					dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getName() });
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
			entity = getSelectedEntity();

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				if (isUseNewRemittance) {
					getField().balanceCode.setText(getEntity().getBalanceCode());
				}
				field.name.setText(getEntity().getName());
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
	protected RemittanceSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param con
	 * @return ���lEntity
	 */
	protected List<Remittance> getList(RemittanceSearchCondition con) {

		try {
			List<Remittance> list;

			if (isUseNewRemittance) {
				list = (List<Remittance>) request(getModelClass(), "getRemittance", con);
			} else {
				list = (List<Remittance>) request(getModelClass(), "get", con);
			}
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
		return RemittanceManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return Remark
	 */
	protected Remittance getSelectedEntity() {
		return (Remittance) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00947"; // �����ړI
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00947"; // �����ړI
	}

	@Override
	public String getNamesCaption() {
		return getWord("C11839"); // ���ێ��x�R�[�h
	}

	@Override
	public String getNamekCaption() {
		return getWord("C11840"); // �����ړI����
	}

	/**
	 * ���ێ��x�R�[�h�̃J�����T�C�Y���擾���܂�
	 * 
	 * @return ���ێ��x�R�[�h�̃J�����T�C�Y
	 */
	@Override
	public int getNamesColumnSize() {
		if (isUseNewRemittance) {
			return 100;
		}
		return 160;
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Remittance getEntity() {
		return (Remittance) super.getEntity();
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Remittance entity_ = new Remittance();
		entity_.setCode(field.code.getText());
		if (isUseNewRemittance) {
			entity_.setBalanceCode(getField().balanceCode.getText());
		}
		entity_.setName(field.name.getText());
		return entity_;
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void setEntity(Remittance entity) {
		if (entity == null) {
			clear();
			return;
		}

		field.code.setText(entity.getCode());
		if (isUseNewRemittance) {
			getField().balanceCode.setText(entity.getBalanceCode());
		}
		field.name.setText(entity.getName());
		this.entity = entity;
	}
}
