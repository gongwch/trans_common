package jp.co.ais.trans2.common.model.ui;

import java.util.List;

import javax.swing.JComponent;

import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.TReferenceController;
import jp.co.ais.trans2.common.gui.TReferenceDialog;
import jp.co.ais.trans2.common.ui.TLoginInfo;
import jp.co.ais.trans2.model.program.SystemDivision;
import jp.co.ais.trans2.model.program.SystemDivisionManager;
import jp.co.ais.trans2.model.program.SystemDivisionSearchCondition;

/**
 * �V�X�e���敪�����R���|�[�l���g �R���g���[��
 */
public class TSystemDivisionReferenceController extends TReferenceController {

	/** �������� */
	protected SystemDivisionSearchCondition condition;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field
	 */
	public TSystemDivisionReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// ��������������
		initSearchCondition();

	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected SystemDivisionSearchCondition createSearchCondition() {
		return new SystemDivisionSearchCondition();
	}

	/**
	 * �R�[�h�����X�g�t�H�[�J�X�C�x���g
	 */
	@Override
	public boolean code_Verify(JComponent comp) {

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
		SystemDivision system = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (system != null) {
			field.code.setText(system.getCode());
			field.name.setText(system.getName());
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
			SystemDivisionSearchCondition condition_ = getCondition().clone();
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			condition_.setNamekLike(dialog.namek.getText());
			List<SystemDivision> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (SystemDivision bean : list) {
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
				setEntity((SystemDivision) entity);
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
	 * @return Entity
	 */
	protected SystemDivision getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			SystemDivisionSearchCondition inputCondition = new SystemDivisionSearchCondition();
			inputCondition.setCompanyCode(this.condition.getCompanyCode());
			inputCondition.setCode(field.getCode());

			List<SystemDivision> list = getList(inputCondition);

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
	protected SystemDivisionSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return �V�X�e�����
	 */
	protected List<SystemDivision> getList(SystemDivisionSearchCondition condition_) {

		try {
			List<SystemDivision> list = (List<SystemDivision>) request(getModelClass(), "get", condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return Class
	 */
	protected Class getModelClass() {
		return SystemDivisionManager.class;
	}

	/**
	 * �����_�C�A���O�̈ꗗ����I�����ꂽ�s��Entity��Ԃ�
	 * 
	 * @return Entity
	 */
	protected SystemDivision getSelectedEntity() {
		return (SystemDivision) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// �V�X�e���敪
		return "C00980";
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// �V�X�e���敪
		return "C00980";
	}

	/**
	 * Entity���Z�b�g
	 * 
	 * @param entity
	 */
	public void setEntity(SystemDivision entity) {
		this.entity = entity;
		if (entity == null) {
			field.code.setText(null);
			field.name.setText(null);
		} else {
			field.code.setText(entity.getCode());
			field.name.setText(entity.getName());
		}
	}

	/**
	 * Entity��Ԃ�
	 * 
	 * @return Entity
	 */
	@Override
	public SystemDivision getEntity() {
		return (SystemDivision) super.getEntity();
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		SystemDivision entity_ = new SystemDivision();
		entity_.setCode(field.code.getText());
		entity_.setNames(field.name.getText());
		return entity_;
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		SystemDivision bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
		} else {
			entity = bean;
		}
	}

	/**
	 * �R�[�h�\���J�����̕���ݒ�
	 */
	@Override
	public int getCodeColumnSize() {
		return 130;
	}

}
