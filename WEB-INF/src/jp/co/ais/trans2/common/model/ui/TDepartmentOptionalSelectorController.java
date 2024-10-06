package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ����C�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TDepartmentOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected DepartmentSearchCondition condition;

	/**
	 * @param field
	 */
	public TDepartmentOptionalSelectorController(TDepartmentOptionalSelector field) {
		super(field);
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

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

	@Override
	protected void initTable() {

		dialog.tblList.addColumn(TDepartmentOptionalSelectorDialog.SC.code, getWord("C00698"), 100); // ����R�[�h
		dialog.tblList.addColumn(TDepartmentOptionalSelectorDialog.SC.names, getWord("C00724"), 200); // ���嗪��
		dialog.tblList.addColumn(TDepartmentOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TDepartmentOptionalSelectorDialog.SC.code, getWord("C00698"), 100); // ����R�[�h
		dialog.tblSelectedList.addColumn(TDepartmentOptionalSelectorDialog.SC.names, getWord("C00724"), 200); // ���嗪��
		dialog.tblSelectedList.addColumn(TDepartmentOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return "C10978"; // ����I��
	}

	/**
	 * �I�����e�[�u���̈ꗗ�Z�b�g
	 */
	@Override
	public void refresh() {

		try {

			// �m��{�^���������s�ɂ���
			dialog.btnSettle.setEnabled(false);

			// �ꗗ���N���A����
			dialog.tblSelectedList.removeRow();
			dialog.tblList.removeRow();
			field.cbo.removeAllItems();
			field.cbo.setEnabled(false);

			// �f�[�^�𒊏o����
			List<Department> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Department bean : list) {
				dialog.tblList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
			}

			// �m��{�^���������\�ɂ���
			dialog.btnSettle.setEnabled(true);

			// 1�s�ڂ�I��
			dialog.tblList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * @param param
	 * @return ������
	 */
	protected List<Department> getList(DepartmentSearchCondition param) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";

				// ��ЃR�[�h���擾
				List<String> companyCodeList = TCompanyClientUtil.getCodeList(this, getField().getCompanyOrgUnit());

				if (companyCodeList == null || companyCodeList.isEmpty()) {
					// ��Ђ��擾�o���Ȃ�
					return null;
				}

				param.setCompanyCodeList(companyCodeList);
			} else {
				param.setCompanyCodeList(null);
			}

			List<Department> list = (List<Department>) request(getModelClass(), method, param);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servlet��class��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public DepartmentSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(DepartmentSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.names);
			Department dep = (Department) dialog.tblList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, dep });
		}

		// �I��������폜
		dialog.tblList.removeSelectedRows();

	}

	/**
	 * [�ꗗ����I�������]�{�^������
	 */
	@Override
	protected void btnTableCancel_Click() {

		int selectedRows[] = dialog.tblSelectedList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.names);
			Department dep = (Department) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, dep });
		}

		// �I��悩��폜
		dialog.tblSelectedList.removeSelectedRows();

	}

	/**
	 * [�m��]�{�^������
	 */
	@Override
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// �I�����ꂽ����ꗗ���擾
		List<Department> list = new ArrayList<Department>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Department department = new Department();
			department.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TDepartmentOptionalSelector.SC.code));
			department.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TDepartmentOptionalSelector.SC.names));
			list.add(department);
		}

		// �Ăяo�����ɃZ�b�g
		for (Department department : list) {
			field.cbo.addItem(department.getCode() + " " + department.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TDepartmentOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ����Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ����Entity�ꗗ
	 */
	public List<Department> getDeptratmentEntities() {

		List<Department> list = new ArrayList<Department>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Department dep = (Department) dialog.tblSelectedList.getRowValueAt(i,
					TDepartmentOptionalSelectorDialog.SC.entity);
				list.add(dep);
			}
		}

		return list;
	}

	/**
	 * ����Entity�ꗗ��ݒ肷��
	 * 
	 * @param list ����Entity�ꗗ
	 */
	public void setDeptratmentEntities(List<Department> list) {

		// �ʑI���_�C�A���O�擾
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}
		for (Department department : list) {
			dialog.tblSelectedList.addRow(new Object[] { department.getCode(), department.getNames(), department });
		}
		// �G���e�B�e�B�Z�b�g���ċ^���m��
		btnSettle_Click();
		// Vector�ޔ�����
		saveSelectedListData();
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TDepartmentOptionalSelector getField() {
		return (TDepartmentOptionalSelector) field;
	}

	/**
	 * �I�����ꂽ�R�[�h���X�g��Ԃ�
	 * 
	 * @return �I�����ꂽ�R�[�h���X�g
	 */
	@Override
	public List<String> getCodeList() {

		List<String> list = new ArrayList<String>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Department dep = (Department) dialog.tblSelectedList.getRowValueAt(i,
					TDepartmentOptionalSelectorDialog.SC.entity);
				list.add(dep.getCode());
			}
		}

		return list;

	}

	/**
	 * ���������̃R�[�h���X�g���Z�b�g�i�����l�j
	 * 
	 * @param codeList
	 */
	@Override
	public void setCodeList(List<String> codeList) {
		// �ʑI���_�C�A���O�擾
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}
		if (codeList == null || codeList.isEmpty()) {
			return;
		}
		condition.setDepartmentCodeList(codeList.toArray(new String[0]));
		List<Department> list = null;
		try {
			list = (List<Department>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// �ꗗ�ɃZ�b�g
		for (Department bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// �^���I�Ɋm��
		btnSettle_Click();
		// Vector�ޔ�����
		saveSelectedListData();
	}

}
