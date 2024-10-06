package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �⏕�ȖڔC�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TSubItemOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected SubItemSearchCondition condition;

	/**
	 * @param field
	 */
	public TSubItemOptionalSelectorController(TSubItemOptionalSelector field) {
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
	protected SubItemSearchCondition createSearchCondition() {
		return new SubItemSearchCondition();
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

		dialog.tblList.addColumn(TSubItemOptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00174"), 100); // �R�[�h
		dialog.tblList.addColumn(TSubItemOptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00548"), 200); // ����
		dialog.tblList.addColumn(TSubItemOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TSubItemOptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00174"), 100); // �R�[�h
		dialog.tblSelectedList.addColumn(TSubItemOptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00548"), 200); // ����
		dialog.tblSelectedList.addColumn(TSubItemOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return getCompany().getAccountConfig().getSubItemName() + getWord("C00324"); // �I��
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
			dialog.tblList.removeRow();
			dialog.tblSelectedList.removeRow();
			field.cbo.removeAllItems();
			field.cbo.setEnabled(false);

			// �f�[�^�𒊏o����
			List<Item> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Item bean : list) {
				dialog.tblList.addRow(new Object[] { bean.getSubItemCode(), bean.getSubItemNames(), bean });
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
	 * @param con
	 * @return �⏕�Ȗڏ��
	 */
	protected List<Item> getList(SubItemSearchCondition con) {

		try {
			List<Item> list = (List<Item>) request(getModelClass(), "get", con);
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
		return ItemManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public SubItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(SubItemSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList
				.getRowValueAt(selectedRows[i], TSubItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TSubItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblList.getRowValueAt(selectedRows[i], TSubItemOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, item });
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
				TSubItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TSubItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TSubItemOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, item });
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

		// �I�����ꂽ�⏕�Ȗڈꗗ���擾
		List<Item> list = new ArrayList<Item>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Item item = new Item();
			item.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelector.SC.code));
			item.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelector.SC.names));
			list.add(item);
		}

		// �Ăяo�����ɃZ�b�g
		for (Item item : list) {
			field.cbo.addItem(item.getCode() + " " + item.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TSubItemOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ�⏕�Ȗ�Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�⏕�Ȗ�Entity�ꗗ
	 */
	public List<Item> getEntities() {

		List<Item> list = new ArrayList<Item>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelectorDialog.SC.entity);
				list.add(item);
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
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelectorDialog.SC.entity);
				list.add(item.getSubItemCode());
			}
		}

		return list;

	}

}
