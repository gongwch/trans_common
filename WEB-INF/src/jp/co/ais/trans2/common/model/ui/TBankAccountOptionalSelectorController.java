package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s�����C�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TBankAccountOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected BankAccountSearchCondition condition;

	/**
	 * @param field
	 */
	public TBankAccountOptionalSelectorController(TBankAccountOptionalSelector field) {
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
	protected BankAccountSearchCondition createSearchCondition() {
		return new BankAccountSearchCondition();
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

		// ��s�����R�[�h
		dialog.tblList.addColumn(TBankAccountOptionalSelectorDialog.SC.code, getWord("C01879"), 100);
		// ��s��������
		dialog.tblList.addColumn(TBankAccountOptionalSelectorDialog.SC.names, getWord("C02145"), 200);
		dialog.tblList.addColumn(TBankAccountOptionalSelectorDialog.SC.entity, "", -1);

		// ��s�����R�[�h
		dialog.tblSelectedList.addColumn(TBankAccountOptionalSelectorDialog.SC.code, getWord("C01879"), 100);
		// ��s��������
		dialog.tblSelectedList.addColumn(TBankAccountOptionalSelectorDialog.SC.names, getWord("C02145"), 200);
		dialog.tblSelectedList.addColumn(TBankAccountOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// ��s�����I��
		return getWord("C11102");
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
			List<BankAccount> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (BankAccount bean : list) {
				dialog.tblList.addRow(new Object[] { bean.getCode(), bean.getName(), bean });
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
	 * @return ��s�������
	 */
	@SuppressWarnings("unchecked")
	protected List<BankAccount> getList(BankAccountSearchCondition condition1) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";
			}

			List<BankAccount> list = (List<BankAccount>) request(getModelClass(), method, condition1);
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
		return BankAccountManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public BankAccountSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(BankAccountSearchCondition condition) {
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
				TBankAccountOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.names);
			BankAccount cus = (BankAccount) dialog.tblList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.entity);

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
				TBankAccountOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.names);
			BankAccount bankAccount = (BankAccount) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, bankAccount });
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

		// �I�����ꂽ��s�����ꗗ���擾
		List<BankAccount> list = new ArrayList<BankAccount>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			BankAccount bankAccount = new BankAccount();
			bankAccount.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TBankAccountOptionalSelector.SC.code));
			bankAccount
				.setName((String) dialog.tblSelectedList.getRowValueAt(i, TBankAccountOptionalSelector.SC.names));
			list.add(bankAccount);
		}

		// �Ăяo�����ɃZ�b�g
		for (BankAccount bankAccount : list) {
			field.cbo.addItem(bankAccount.getCode() + " " + bankAccount.getName());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TBankAccountOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ��s����Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ��s����Entity�ꗗ
	 */
	public List<BankAccount> getEntities() {

		List<BankAccount> list = new ArrayList<BankAccount>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				BankAccount bankAccount = (BankAccount) dialog.tblSelectedList.getRowValueAt(i,
					TBankAccountOptionalSelectorDialog.SC.entity);
				list.add(bankAccount);
			}
		}

		return list;
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TBankAccountOptionalSelector getField() {
		return (TBankAccountOptionalSelector) field;
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
				BankAccount bankAccount = (BankAccount) dialog.tblSelectedList.getRowValueAt(i,
					TBankAccountOptionalSelectorDialog.SC.entity);
				list.add(bankAccount.getCode());
			}
		}

		return list;

	}

}
