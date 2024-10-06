package jp.co.ais.trans2.common.model.ui;

import java.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �ȖڔC�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TItemOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected ItemSearchCondition condition;

	/**
	 * @param field
	 */
	public TItemOptionalSelectorController(TItemOptionalSelector field) {
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
	protected ItemSearchCondition createSearchCondition() {
		return new ItemSearchCondition();
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

		dialog.tblList.addColumn(TItemOptionalSelectorDialog.SC.code,
		// �R�[�h
			getCompany().getAccountConfig().getItemName() + getWord("C00174"), 100);
		dialog.tblList.addColumn(TItemOptionalSelectorDialog.SC.names,
		// ����
			getCompany().getAccountConfig().getItemName() + getWord("C00548"), 200);
		dialog.tblList.addColumn(TItemOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TItemOptionalSelectorDialog.SC.code,
		// �R�[�h
			getCompany().getAccountConfig().getItemName() + getWord("C00174"), 100);
		dialog.tblSelectedList.addColumn(TItemOptionalSelectorDialog.SC.names,
		// ����
			getCompany().getAccountConfig().getItemName() + getWord("C00548"), 200);
		dialog.tblSelectedList.addColumn(TItemOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// �I��
		return getCompany().getAccountConfig().getItemName() + getWord("C00324");
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
			List<Item> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Item bean : list) {
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
	protected List<Item> getList(ItemSearchCondition condition) {

		try {
			List<Item> list = (List<Item>) request(getModelClass(), "get", condition);
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
		return ItemManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public ItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(ItemSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i], TItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblList.getRowValueAt(selectedRows[i], TItemOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, item });
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
				TItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, item });
		}

		// �I��悩��폜
		dialog.tblSelectedList.removeSelectedRows();

	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// �I�����ꂽ�Ȗڈꗗ���擾
		List<Item> list = new ArrayList<Item>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Item item = new Item();
			item.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelector.SC.code));
			item.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelector.SC.names));
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
		return new TItemOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ�Ȗ�Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ȗ�Entity�ꗗ
	 */
	public List<Item> getEntities() {

		List<Item> list = new ArrayList<Item>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelectorDialog.SC.entity);
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
	public List<String> getCodeList() {

		List<String> list = new ArrayList<String>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelectorDialog.SC.entity);
				list.add(item.getCode());
			}
		}

		return list;

	}

}
