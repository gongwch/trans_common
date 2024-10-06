package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ���匟���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TDepartmentReferenceController extends TReferenceController {

	/** �������� */
	protected DepartmentSearchCondition condition;

	/**
	 * @param field ����R���|�[�l���g
	 */
	public TDepartmentReferenceController(TReference field) {
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
	protected DepartmentSearchCondition createSearchCondition() {
		return new DepartmentSearchCondition();
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
			return true;
		}

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		entity = getInputtedEntity();

		// �l������Η��̂��Z�b�g
		if (entity != null) {

			// ���������ɊY�����邩���`�F�b�N
			DepartmentSearchCondition condition_ = getCondition();

			// �W�v��������O��������ŏW�v����̏ꍇ�G���[
			if (!condition_.isSumDepartment() && getEntity().isSumDepartment()) {
				showMessage(dialog, "I00154");// �W�v����͎w��ł��܂���B
				field.code.clearOldText();
				field.code.requestFocus();
				return false;
			}

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
			showMessage(field, "I00123");// �Y���R�[�h�͑��݂��܂���B
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
			DepartmentSearchCondition condition_ = getCondition().clone();
			// �R�[�h�O����v
			condition_.setCodeLike(dialog.code.getText());
			// ���̂����܂�
			condition_.setNamesLike(dialog.names.getText());
			// �������̂����܂�
			condition_.setNamekLike(dialog.namek.getText());

			List<Department> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Department bean : list) {
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
	 * Class��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * ���͂��ꂽ�����Ԃ�
	 * 
	 * @return Entity
	 */
	protected Department getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			DepartmentSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setSumDepartment(true);
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// �L�������؂��C�ӂőI�΂���ׂɏ�������͍폜
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<Department> list = getList(searchCondition);

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
	 * ���������ɊY�����镔�僊�X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY�����镔�僊�X�g
	 */
	@SuppressWarnings("unchecked")
	protected List<Department> getList(DepartmentSearchCondition condition_) {

		String nowCompanyCode = condition_.getCompanyCode();

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";
				condition_.setCompanyCode(null);

				// ��ЃR�[�h���擾
				List<String> companyCodeList = TCompanyClientUtil.getCodeList(this, getField().getCompanyOrgUnit());

				if (getField().getCompanyOrgUnit() != null) {
					if (companyCodeList == null || companyCodeList.isEmpty()) {
						// ��Ђ��擾�o���Ȃ�
						return null;
					}
				}

				condition_.setCompanyCodeList(companyCodeList);
			} else {
				condition_.setCompanyCodeList(null);
			}

			List<Department> list = (List<Department>) request(getModelClass(), method, condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		} finally {
			condition_.setCompanyCode(nowCompanyCode);
		}

		return null;
	}

	/**
	 * @return ����������߂��܂��B
	 */
	public DepartmentSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition ����������ݒ肵�܂��B
	 */
	public void setCondition(DepartmentSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �I�����ꂽ�����Ԃ�
	 * 
	 * @return �I�����ꂽ����
	 */
	protected Department getSelectedEntity() {
		return (Department) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00467"; // ����
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00467"; // ����
	}

	/**
	 * �`�[�̎Q�ƌ������Z�b�g����B<br>
	 * �����ɂ���ĕ���̃t�B�[���h���䂷��B
	 * 
	 * @param slipRole �`�[�Q�ƌ���
	 * @param department ���[�U�[�̏�������
	 */
	public void setSlipRole(SlipRole slipRole, Department department) {

		if (SlipRole.ALL == slipRole) {
			field.setEnabled(true);
		} else {
			setEntity(department);
			field.setEditable(false);
		}
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Department getEntity() {
		return (Department) super.getEntity();
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TDepartmentReference getField() {
		return (TDepartmentReference) field;
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param department ����
	 */
	public void setEntity(Department department) {
		if (department == null) {
			clear();
		} else {
			field.code.setText(department.getCode());
			field.name.setText(department.getNames());
			entity = department;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Department entity_ = createEntity();
		entity_.setCode(field.code.getText());
		entity_.setNames(field.name.getText());
		return entity_;
	}

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	@Override
	public Department createEntity() {
		return new Department();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		Department bean = getInputtedEntity();
		setEntity(bean);
	}

}
