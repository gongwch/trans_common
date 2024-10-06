package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * �����C�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TCustomerOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected CustomerSearchCondition condition;

	/**
	 * @param field
	 */
	public TCustomerOptionalSelectorController(TCustomerOptionalSelector field) {
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
	protected CustomerSearchCondition createSearchCondition() {
		return new CustomerSearchCondition();
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

		// �����R�[�h
		dialog.tblList.addColumn(TCustomerOptionalSelectorDialog.SC.code, getWord("C00786"), 100);
		// ����旪��
		dialog.tblList.addColumn(TCustomerOptionalSelectorDialog.SC.names, getWord("C00787"), 200);
		dialog.tblList.addColumn(TCustomerOptionalSelectorDialog.SC.entity, "", -1);

		// �����R�[�h
		dialog.tblSelectedList.addColumn(TCustomerOptionalSelectorDialog.SC.code, getWord("C00786"), 100);
		// ����旪��
		dialog.tblSelectedList.addColumn(TCustomerOptionalSelectorDialog.SC.names, getWord("C00787"), 200);
		dialog.tblSelectedList.addColumn(TCustomerOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// �����I��
		return getWord("C02672");
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
			List<Customer> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Customer bean : list) {
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
	 * @return �������
	 */
	@SuppressWarnings("unchecked")
	protected List<Customer> getList(CustomerSearchCondition condition1) {

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

			List<Customer> list = (List<Customer>) request(getModelClass(), method, condition1);
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
		return CustomerManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public CustomerSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(CustomerSearchCondition condition) {
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
				TCustomerOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.names);
			Customer cus = (Customer) dialog.tblList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, cus });
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
				TCustomerOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.names);
			Customer cus = (Customer) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, cus });
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

		// �I�����ꂽ�����ꗗ���擾
		List<Customer> list = new ArrayList<Customer>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Customer customer = new Customer();
			customer.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TCustomerOptionalSelector.SC.code));
			customer.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TCustomerOptionalSelector.SC.names));
			list.add(customer);
		}

		// �Ăяo�����ɃZ�b�g
		for (Customer customer : list) {
			field.cbo.addItem(customer.getCode() + " " + customer.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TCustomerOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ�����Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�����Entity�ꗗ
	 */
	public List<Customer> getEntities() {

		List<Customer> list = new ArrayList<Customer>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Customer customer = (Customer) dialog.tblSelectedList.getRowValueAt(i,
					TCustomerOptionalSelectorDialog.SC.entity);
				list.add(customer);
			}
		}

		return list;
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TCustomerOptionalSelector getField() {
		return (TCustomerOptionalSelector) field;
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
				Customer customer = (Customer) dialog.tblSelectedList.getRowValueAt(i,
					TCustomerOptionalSelectorDialog.SC.entity);
				list.add(customer.getCode());
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
		condition.setCustomerCodeList(codeList.toArray(new String[0]));
		List<Customer> list = null;
		try {
			list = (List<Customer>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// �ꗗ�ɃZ�b�g
		for (Customer bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// �^���I�Ɋm��
		btnSettle_Click();
		// Vector�ޔ�����
		saveSelectedListData();
	}

}
