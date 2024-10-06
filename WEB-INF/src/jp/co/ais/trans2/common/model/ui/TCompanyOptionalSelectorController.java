package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.TCompanyOptionalSelectorDialog.CMP_SEL_SC;
import jp.co.ais.trans2.model.company.*;

/**
 * ��ДC�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TCompanyOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected CompanySearchCondition condition;

	/**
	 * @param field
	 */
	public TCompanyOptionalSelectorController(TCompanyOptionalSelector field) {
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
	protected CompanySearchCondition createSearchCondition() {
		return new CompanySearchCondition();
	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

	@Override
	protected void initTable() {

		dialog.tblList.addColumn(CMP_SEL_SC.entity, "", -1);
		dialog.tblList.addColumn(CMP_SEL_SC.code, getWord("C00596"), 100); // ��ЃR�[�h
		dialog.tblList.addColumn(CMP_SEL_SC.name, getWord("C00685"), 200); // ��Ж���

		dialog.tblSelectedList.addColumn(CMP_SEL_SC.entity, "", -1);
		dialog.tblSelectedList.addColumn(CMP_SEL_SC.code, getWord("C00596"), 100); // ��ЃR�[�h
		dialog.tblSelectedList.addColumn(CMP_SEL_SC.name, getWord("C00685"), 200); // ��Ж���

	}

	@Override
	protected String getDialogCaption() {
		return "C11365"; // ��БI��
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
			List<Company> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Company bean : list) {
				dialog.tblList.addRow(new Object[] { bean, bean.getCode(), bean.getName() });
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
	 * @return ��Џ��
	 */
	protected List<Company> getList(CompanySearchCondition param) {

		try {
			List<Company> list = (List<Company>) request(getModelClass(), "get", param);
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
		return CompanyManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public CompanySearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(CompanySearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			Company bean = (Company) dialog.tblList.getRowValueAt(selectedRows[i], CMP_SEL_SC.entity);
			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], CMP_SEL_SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i], CMP_SEL_SC.name);

			dialog.tblSelectedList.addRow(new Object[] { bean, code, names });
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

			Company bean = (Company) dialog.tblSelectedList.getRowValueAt(selectedRows[i], CMP_SEL_SC.entity);
			String code = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i], CMP_SEL_SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i], CMP_SEL_SC.name);

			dialog.tblList.addRow(new Object[] { bean, code, names });
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

		// �I�����ꂽ��Јꗗ���擾
		List<Company> list = new ArrayList<Company>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Company bean = new Company();
			bean.setCode((String) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.code));
			bean.setNames((String) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.name));
			list.add(bean);
		}

		// �Ăяo�����ɃZ�b�g
		for (Company bean : list) {
			field.cbo.addItem(bean.getCode() + " " + bean.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TCompanyOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ���Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ���Entity�ꗗ
	 */
	public List<Company> getEntities() {

		List<Company> list = new ArrayList<Company>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Company dep = (Company) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.entity);
				list.add(dep);
			}
		}

		return list;
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
				Company cmp = (Company) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.entity);
				list.add(cmp.getCode());
			}
		}

		return list;

	}

}
