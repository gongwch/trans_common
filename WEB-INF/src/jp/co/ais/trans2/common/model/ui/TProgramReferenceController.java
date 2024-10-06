package jp.co.ais.trans2.common.model.ui;

import java.util.*;
import javax.swing.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O���������R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TProgramReferenceController extends TReferenceController {

	/** �������� */
	protected ProgramSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TProgramReferenceController(TReference field) {
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
	protected ProgramSearchCondition createSearchCondition() {
		return new ProgramSearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setExecutable(true);
		condition.setNotExecutable(false);
	}

	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// �l���u�����N�̏ꍇ�A���̂�������
		if (Util.isNullOrEmpty(field.getCode())) {
			field.name.setText(null);
			return true;
		}

		// �l�̕ύX���Ȃ����̓f�[�^���擾���Ȃ�
		if (!field.code.isValueChanged()) {
			return true;
		}

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		Program program = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (program != null) {
			field.code.setText(program.getCode());
			field.name.setText(program.getNames());
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
			ProgramSearchCondition condition_ = getCondition().clone();
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			condition_.setNamekLike(dialog.namek.getText());

			List<Program> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Program bean : list) {
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
			Program program = getSelectedEntity();

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (program != null) {
				field.code.setText(program.getCode());
				field.name.setText(program.getNames());
				field.code.pushOldText();
			}

			// �_�C�A���O�����
			dialog.setVisible(false);
			dialog.dispose();

			// �Ăяo�����̃R�[�h�t�B�[���h�Ƀt�H�[�J�X
			field.code.requestFocus();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �t�B�[���h����R�[�h�𒼐ړ��͂����ꍇ�̌���
	 * 
	 * @return
	 */
	protected Program getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			ProgramSearchCondition condition = new ProgramSearchCondition();
			condition.setCompanyCode(this.condition.getCompanyCode());
			condition.setCode(field.getCode());

			List<Program> list = getList(condition);

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
	protected ProgramSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 * @return �v���O�������
	 */
	@SuppressWarnings("unchecked")
	protected List<Program> getList(ProgramSearchCondition condition) {

		try {
			List<Program> list = (List<Program>) request(getModelClass(), "get", condition);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	protected Class getModelClass() {
		return ProgramManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return
	 */
	protected Program getSelectedEntity() {
		return (Program) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// �v���O����
		return "C00477";
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// �v���O����
		return "C00477";
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	public Object getUnspecifiedEntity() {
		Program entity = new Program();
		entity.setCode(field.code.getText());
		entity.setNames(field.name.getText());
		return entity;
	}

}
