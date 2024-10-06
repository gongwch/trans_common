package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * �Ј��C�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TEmployeeOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected EmployeeSearchCondition condition;

	/**
	 * @param field
	 */
	public TEmployeeOptionalSelectorController(TEmployeeOptionalSelector field) {
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
	protected EmployeeSearchCondition createSearchCondition() {
		return new EmployeeSearchCondition();
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

		// �Ј��R�[�h
		dialog.tblList.addColumn(TEmployeeOptionalSelectorDialog.SC.code, getWord("C00697"), 100);
		// �Ј�����
		dialog.tblList.addColumn(TEmployeeOptionalSelectorDialog.SC.names, getWord("C00808"), 200);
		dialog.tblList.addColumn(TEmployeeOptionalSelectorDialog.SC.entity, "", -1);

		// �Ј��R�[�h
		dialog.tblSelectedList.addColumn(TEmployeeOptionalSelectorDialog.SC.code, getWord("C00697"), 100);
		// �Ј�����
		dialog.tblSelectedList.addColumn(TEmployeeOptionalSelectorDialog.SC.names, getWord("C00808"), 200);
		dialog.tblSelectedList.addColumn(TEmployeeOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// �Ј��I��
		return getWord("C11130");
	}

	/**
	 * �I�����e�[�u���̈ꗗ�����t���b�V��(�Ď擾)
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
			List<Employee> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Employee bean : list) {
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
	 * @param condition1
	 * @return �Ј����
	 */
	@SuppressWarnings("unchecked")
	protected List<Employee> getList(EmployeeSearchCondition condition1) {

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

				condition1.setCompanyCodeList(companyCodeList);
			} else {
				condition1.setCompanyCodeList(null);
			}

			List<Employee> list = (List<Employee>) request(getModelClass(), method, condition1);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servlet��class��Ԃ�
	 * 
	 * @return class
	 */
	protected Class getModelClass() {
		return EmployeeManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public EmployeeSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(EmployeeSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * �ꗗ����I����
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.names);
			Employee emp = (Employee) dialog.tblList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, emp });
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
				TEmployeeOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.names);
			Employee emp = (Employee) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, emp });
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

		// �I�����ꂽ�Ј��ꗗ���擾
		List<Employee> list = new ArrayList<Employee>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Employee employee = new Employee();
			employee.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TEmployeeOptionalSelector.SC.code));
			employee.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TEmployeeOptionalSelector.SC.names));
			list.add(employee);
		}

		// �Ăяo�����ɃZ�b�g
		for (Employee employee : list) {
			field.cbo.addItem(employee.getCode() + " " + employee.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TEmployeeOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ�Ј�Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ј�Entity�ꗗ
	 */
	public List<Employee> getEntities() {

		List<Employee> list = new ArrayList<Employee>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Employee emp = (Employee) dialog.tblSelectedList.getRowValueAt(i,
					TEmployeeOptionalSelectorDialog.SC.entity);
				list.add(emp);
			}
		}

		return list;
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TEmployeeOptionalSelector getField() {
		return (TEmployeeOptionalSelector) field;
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
				Employee emp = (Employee) dialog.tblSelectedList.getRowValueAt(i,
					TEmployeeOptionalSelectorDialog.SC.entity);
				list.add(emp.getCode());
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
		condition.setEmployeeCodeList(codeList.toArray(new String[0]));
		List<Employee> list = null;
		try {
			list = (List<Employee>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// �ꗗ�ɃZ�b�g
		for (Employee bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// �^���I�Ɋm��
		btnSettle_Click();
		// Vector�ޔ�����
		saveSelectedListData();
	}
}
