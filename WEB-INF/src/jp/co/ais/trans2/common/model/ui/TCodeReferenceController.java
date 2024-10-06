package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.code.*;

/**
 * Code�̌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TCodeReferenceController extends TReferenceController {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TCodeReferenceController(TCodeReference field) {
		super(field);
	}

	/**
	 * �����t�B�[���h�Ŕ�������C�x���g��ǉ�����
	 */
	@Override
	protected void addEvent() {
		super.addEvent();

		List<OP_CODE_MST> list = getList(null, null);
		AutoCompleteUtil.decorate(field, list);
	}

	/**
	 * @return REF
	 */
	protected TCodeReference getField() {
		return (TCodeReference) field;
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

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		entity = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (entity != null) {
			field.code.setText(getEntity().getCODE());
			field.name.setText(getEntity().getCODE_NAME());
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
	 * �����_�C�A���O[����]�{�^������
	 */
	@Override
	public void btnSearch_Click() {
		try {
			// �m��{�^���������s�ɂ���
			dialog.btnSettle.setEnabled(false);

			// �ꗗ���N���A����
			dialog.tbl.removeRow();

			List<OP_CODE_MST> list = getList(dialog.code.getText(), dialog.names.getText());

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (OP_CODE_MST bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCODE(), bean.getCODE_NAME() });
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
			OP_CODE_MST newEntity = getSelectedEntity();

			// ����̃R�[�h���I�����ꂽ�ꍇ�́A�����Ȃ�
			if (field.code.getText().equals(newEntity.getCODE())) {
				dialog.setVisible(false);
				dialog.dispose();
				field.code.requestFocus();
				return;
			}

			entity = getSelectedEntity();

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				field.code.setText(getEntity().getCODE());
				field.name.setText(getEntity().getCODE_NAME());
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
	protected OP_CODE_MST getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			return OPLoginUtil.getCodeMst(getField().isLocal(), getField().getCodeDivision(), getField().getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @param codeLike
	 * @param namesLike
	 * @return Code���
	 */
	protected List<OP_CODE_MST> getList(String codeLike, String namesLike) {
		try {
			List<OP_CODE_MST> list = OPLoginUtil.getCodeMstList(getField().isLocal(), getField().getCodeDivision(),
				getField().getCodes());

			if (list == null) {
				return null;
			}

			List<OP_CODE_MST> filter = new ArrayList<OP_CODE_MST>();

			for (OP_CODE_MST bean : list) {
				boolean doAdd = true;

				if (!Util.isNullOrEmpty(codeLike)) {
					doAdd &= bean.getCODE().indexOf(codeLike) != -1;
				}
				if (!Util.isNullOrEmpty(namesLike)) {
					doAdd &= bean.getCODE_NAME().indexOf(namesLike) != -1;
				}

				if (doAdd) {
					filter.add(bean);
				}
			}

			return filter;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * �}�l�[�W����Ԃ�
	 * 
	 * @return CodeManager
	 */
	protected Class getManager() {
		return CodeManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return entity
	 */
	protected OP_CODE_MST getSelectedEntity() {
		return (OP_CODE_MST) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return getWord(getField().getCodeDivision().getCaption()); // Code
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getWord(getField().getCodeDivision().getName()); // Code
	}

	/**
	 * �����_�C�A���O(Code�p)�̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �����_�C�A���O
	 */
	@Override
	protected TReferenceDialog createDialog() {
		return new TCodeReferenceDialog(this);
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public OP_CODE_MST getEntity() {
		return (OP_CODE_MST) super.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	public void setEntity(OP_CODE_MST bean) {
		if (bean == null) {
			clear();
			return;
		}

		field.code.setText(bean.getCODE());
		field.name.setText(bean.getCODE_NAME());
		entity = bean;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		OP_CODE_MST bean = new OP_CODE_MST();
		bean.setCODE(getField().code.getText());
		bean.setCODE_DIV(getField().getCodeDivision().getValue());
		bean.setCODE_NAME(getField().name.getText());
		return bean;
	}

}
