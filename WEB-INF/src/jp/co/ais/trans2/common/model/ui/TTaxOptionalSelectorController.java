package jp.co.ais.trans2.common.model.ui;

import java.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.tax.ConsumptionTax;
import jp.co.ais.trans2.model.tax.ConsumptionTaxManager;
import jp.co.ais.trans2.model.tax.ConsumptionTaxSearchCondition;

/**
 * ����ŔC�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TTaxOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected ConsumptionTaxSearchCondition condition;

	/**
	 * @param field
	 */
	public TTaxOptionalSelectorController(TTaxOptionalSelector field) {
		super(field);
	}

	/**
	 * ������
	 */
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
	protected ConsumptionTaxSearchCondition createSearchCondition() {
		return new ConsumptionTaxSearchCondition();
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

		// ����ŃR�[�h
		dialog.tblList.addColumn(TTaxOptionalSelectorDialog.SC.code, getWord("C00573"), 100);
		// ����ŗ���
		dialog.tblList.addColumn(TTaxOptionalSelectorDialog.SC.names, getWord("C00775"), 200);
		dialog.tblList.addColumn(TTaxOptionalSelectorDialog.SC.entity, "", -1);

		// ����ŃR�[�h
		dialog.tblSelectedList.addColumn(TTaxOptionalSelectorDialog.SC.code, getWord("C00573"), 100);
		// ����ŗ���
		dialog.tblSelectedList.addColumn(TTaxOptionalSelectorDialog.SC.names, getWord("C00775"), 200);
		dialog.tblSelectedList.addColumn(TTaxOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// ����őI��
		return getWord("C11135");
	}

	/**
	 * �I�����e�[�u���̈ꗗ�����t���b�V��(�Ď擾)
	 */
	public void refresh() {

		try {

			// �m��{�^���������s�ɂ���
			dialog.btnSettle.setEnabled(false);

			// �ꗗ���N���A����
			dialog.tblList.removeRow();
			dialog.tblSelectedList.removeRow();
			field.cbo.removeAllItems();
			field.cbo.setEnabled(false);

			// �f�[�^�𒊏o����
			List<ConsumptionTax> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (ConsumptionTax bean : list) {
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
	 * @param condition
	 * @return �Ȗڏ��
	 */
	@SuppressWarnings("unchecked")
	protected List<ConsumptionTax> getList(ConsumptionTaxSearchCondition condition) {

		try {
			List<ConsumptionTax> list = (List<ConsumptionTax>) request(getModelClass(), "get", condition);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servlet��class��Ԃ�
	 * 
	 * @return
	 */
	protected Class getModelClass() {
		return ConsumptionTaxManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public ConsumptionTaxSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(ConsumptionTaxSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TTaxOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i], TTaxOptionalSelectorDialog.SC.names);
			ConsumptionTax tax = (ConsumptionTax) dialog.tblList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, tax });
		}

		// �I��������폜
		dialog.tblList.removeSelectedRows();

	}

	/**
	 * [�ꗗ����I�������]�{�^������
	 */
	protected void btnTableCancel_Click() {

		int selectedRows[] = dialog.tblSelectedList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.names);
			ConsumptionTax tax = (ConsumptionTax) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, tax });
		}

		// �I��悩��폜
		dialog.tblSelectedList.removeSelectedRows();

	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// �I�����ꂽ����ňꗗ���擾
		List<ConsumptionTax> list = new ArrayList<ConsumptionTax>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			ConsumptionTax tax = new ConsumptionTax();
			tax.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TTaxOptionalSelector.SC.code));
			tax.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TTaxOptionalSelector.SC.names));
			list.add(tax);
		}

		// �Ăяo�����ɃZ�b�g
		for (ConsumptionTax tax : list) {
			field.cbo.addItem(tax.getCode() + " " + tax.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TTaxOptionalSelectorDialog(field.getParentFrame(), true);
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
	public List<ConsumptionTax> getEntities() {

		List<ConsumptionTax> list = new ArrayList<ConsumptionTax>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				ConsumptionTax tax = (ConsumptionTax) dialog.tblSelectedList.getRowValueAt(i,
					TTaxOptionalSelectorDialog.SC.entity);
				list.add(tax);
			}
		}

		return list;
	}

	/**
	 * �I�����ꂽ�R�[�h���X�g��Ԃ�
	 * 
	 * @return �I�����ꂽ�R�[�h���X�g
	 */
	public List<String> getCodeList() {

		List<String> list = new ArrayList<String>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				ConsumptionTax tax = (ConsumptionTax) dialog.tblSelectedList.getRowValueAt(i,
					TTaxOptionalSelectorDialog.SC.entity);
				list.add(tax.getCode());
			}
		}

		return list;

	}

	/**
	 * ����ňꗗ��Entity���Z�b�g����
	 */
	public void setEntities(List<ConsumptionTax> taxs) {

		// �_�C�A���O������
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}

		List<Integer> matchRowNo = new ArrayList<Integer>();
		for (ConsumptionTax tax : taxs) {

			// �w��̃R�[�h�������
			for (int i = 0; i < dialog.tblList.getRowCount(); i++) {

				String code = (String) dialog.tblList.getRowValueAt(i, TTaxOptionalSelectorDialog.SC.code);
				String names = (String) dialog.tblList.getRowValueAt(i, TTaxOptionalSelectorDialog.SC.names);
				ConsumptionTax tTax = (ConsumptionTax) dialog.tblList.getRowValueAt(i,
					TTaxOptionalSelectorDialog.SC.entity);

				// ����ŃR�[�h����v����΃}�b�`
				if (tax.getCode().equals(tTax.getCode())) {
					dialog.tblSelectedList.addRow(new Object[] { code, names, tTax });
					matchRowNo.add(i);
				}

			}

		}

		// �I�����ꂽ�s���폜
		for (int i = matchRowNo.size() - 1; i >= 0; i--) {
			dialog.tblList.removeRow(matchRowNo.get(i));
		}

		btnSettle_Click(null);

	}

}
