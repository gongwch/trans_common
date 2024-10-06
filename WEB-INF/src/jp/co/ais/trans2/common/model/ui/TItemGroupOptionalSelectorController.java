package jp.co.ais.trans2.common.model.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.ItemLevel;
import jp.co.ais.trans2.model.company.AccountConfig;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗ�+�⏕�Ȗ�+����ȖڔC�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TItemGroupOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected ItemSearchCondition condition;

	/** �⏕�Ȗڌ������� */
	protected SubItemSearchCondition subItemCondition;

	/** ����Ȗڌ������� */
	protected DetailItemSearchCondition detailItemCondition;

	/** �Ȗڃ��x�� */
	protected ItemLevel itemLevel = null;

	/**
	 * @param field
	 */
	public TItemGroupOptionalSelectorController(TItemGroupOptionalSelector field) {
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
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = new ItemSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		subItemCondition = new SubItemSearchCondition();
		subItemCondition.setCompanyCode(TLoginInfo.getCompany().getCode());
		detailItemCondition = new DetailItemSearchCondition();
		detailItemCondition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	protected void initTable() {

		dialog.tblList.addColumn(TItemGroupOptionalSelectorDialog.SC.code, getWord("C00174"), 100); // �R�[�h
		dialog.tblList.addColumn(TItemGroupOptionalSelectorDialog.SC.names, getWord("C00548"), 200); // ����
		dialog.tblList.addColumn(TItemGroupOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TItemGroupOptionalSelectorDialog.SC.code, getWord("C00174"), 100); // �R�[�h
		dialog.tblSelectedList.addColumn(TItemGroupOptionalSelectorDialog.SC.names, getWord("C00548"), 200); // ����
		dialog.tblSelectedList.addColumn(TItemGroupOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return getCompany().getAccountConfig().getItemName() + getWord("C00324"); // �I��
	}

	/**
	 * �I�����e�[�u���̈ꗗ�����t���b�V��(�Ď擾)
	 */
	public void refresh() {

		try {

			// �m��{�^���������s�ɂ���
			dialog.btnSettle.setEnabled(false);

			// �ꗗ���N���A����
			dialog.tblSelectedList.removeRow();
			dialog.tblList.removeRow();

			// �f�[�^�𒊏o����
			List<Item> list = null;
			ItemLevel itemLevel = ((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.getItemLevel();

			// �Ȗڃ��x���̏ꍇ
			if (ItemLevel.ITEM == itemLevel) {
				list = getList(getCondition());
			}

			// �⏕�Ȗڃ��x���̏ꍇ
			else if (ItemLevel.SUBITEM == itemLevel) {
				list = getList(getSubItemCondition());
			}

			// ����Ȗڃ��x���̏ꍇ
			else if (ItemLevel.DETAILITEM == itemLevel) {
				list = getList(getDetailItemCondition());
			}

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Item bean : list) {

				// �Ȗڃ��x���̏ꍇ
				if (ItemLevel.ITEM == itemLevel) {
					dialog.tblList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
				}

				// �⏕�Ȗڃ��x���̏ꍇ
				else if (ItemLevel.SUBITEM == itemLevel) {

					String code = bean.getCode();
					String names = bean.getNames();
					if (bean.getSubItem() != null && !Util.isNullOrEmpty(bean.getSubItem().getCode())) {
						code = code + " - " + bean.getSubItem().getCode();
						names = names + " - " + bean.getSubItem().getNames();
					}

					dialog.tblList.addRow(new Object[] { code, names, bean });
				}

				// ����Ȗڃ��x���̏ꍇ
				else if (ItemLevel.DETAILITEM == itemLevel) {

					String code = bean.getCode();
					String names = bean.getNames();
					if (bean.getSubItem() != null && !Util.isNullOrEmpty(bean.getSubItem().getCode())) {
						code = code + " - " + bean.getSubItem().getCode();
						names = names + " - " + bean.getSubItem().getNames();
						if (bean.getSubItem().getDetailItem() != null && !Util.isNullOrEmpty(bean.getDetailItemCode())) {
							code = code + " - " + bean.getSubItem().getDetailItem().getCode();
							names = names + " - " + bean.getSubItem().getDetailItem().getNames();
						}
					}

					dialog.tblList.addRow(new Object[] { code, names, bean });
				}

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

	public SubItemSearchCondition getSubItemCondition() {
		return subItemCondition;
	}

	public DetailItemSearchCondition getDetailItemCondition() {
		return detailItemCondition;
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

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblList
				.getRowValueAt(selectedRows[i], TItemGroupOptionalSelectorDialog.SC.entity);
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
				TItemGroupOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.entity);
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
			item.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.code));
			item.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.names));
			list.add(item);
		}

		// �Ăяo�����ɃZ�b�g
		for (Item item : list) {
			field.cbo.addItem(item.getCode() + " " + item.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		saveSelectedListData();

		dialog.setVisible(false);

	}

	@Override
	protected TItemGroupOptionalSelectorDialog getSelectorDialog() {
		TItemGroupOptionalSelectorDialog dialog = new TItemGroupOptionalSelectorDialog(field.getParentFrame(), true);
		addDialogEvent(dialog);

		return dialog;
	}

	/**
	 * �_�C�A���O�̃C�x���g��`
	 * 
	 * @param dialog
	 */
	protected void addDialogEvent(TItemGroupOptionalSelectorDialog dialog) {

		dialog.ctrlItemLevelChooser.rdoItem.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoItem_Click();
			}
		});

		dialog.ctrlItemLevelChooser.rdoSubItem.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoSubItem_Click();
			}
		});

		dialog.ctrlItemLevelChooser.rdoDetailItem.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoSubItem_Click();
			}
		});

	}

	protected void rdoItem_Click() {
		dialog.tblSelectedList.removeRow();
		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		refresh();
	}

	protected void rdoSubItem_Click() {
		dialog.tblSelectedList.removeRow();
		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		refresh();
	}

	protected void rdoDetailItem_Click() {
		dialog.tblSelectedList.removeRow();
		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		refresh();
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
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.entity);
				list.add(item);
			}
		}

		return list;

	}

	/**
	 * �Ȗڈꗗ��Entity���Z�b�g����
	 */
	public void setEntities(List<Item> items) {

		// �_�C�A���O������
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}

		ItemLevel itemLevel = ItemLevel.ITEM;
		for (Item item : items) {
			if (item.getDetailItem() != null) {
				itemLevel = ItemLevel.DETAILITEM;
				break;
			}
			if (item.getSubItem() != null) {
				itemLevel = ItemLevel.SUBITEM;
			}
		}

		if (this.itemLevel != null) {
			itemLevel = this.itemLevel;
		}

		// �Ȗڃ��x���ł͂Ȃ��ꍇ���t���b�V��
		if (itemLevel != ItemLevel.ITEM) {
			((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setItemLevel(itemLevel);
			refresh();
		}

		List<Integer> matchRowNo = new ArrayList<Integer>();
		for (Item item : items) {

			// �w��̃R�[�h�������
			for (int i = 0; i < dialog.tblList.getRowCount(); i++) {

				String code = (String) dialog.tblList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.code);
				String names = (String) dialog.tblList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.names);
				Item tItem = (Item) dialog.tblList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.entity);

				boolean match = false;

				// �Ȗڃ��x���̏ꍇ�A�ȖڃR�[�h����v����΃}�b�`
				if (itemLevel == ItemLevel.ITEM && item.getCode().equals(tItem.getCode())) {
					match = true;
				}

				// �⏕���x���̏ꍇ�A�ȖڃR�[�h�A�⏕�ȖڃR�[�h����v����΃}�b�`
				if (itemLevel == ItemLevel.SUBITEM) {
					String subItemCode = item.getSubItemCode();
					String tableSubItemCode = tItem.getSubItemCode();
					if (item.getCode().equals(tItem.getCode())
						&& Util.avoidNull(subItemCode).equals(Util.avoidNull(tableSubItemCode))) {
						match = true;
					}
				}

				// ���󃌃x���̏ꍇ�A�ȖڃR�[�h�A�⏕�ȖڃR�[�h�A����Ȗڂ���v����΃}�b�`
				if (itemLevel == ItemLevel.DETAILITEM) {
					String subItemCode = item.getSubItemCode();
					String detailItemCode = item.getDetailItemCode();
					String tableSubItemCode = tItem.getSubItemCode();
					String tableDetailItemCode = tItem.getDetailItemCode();

					if (item.getCode().equals(tItem.getCode())
						&& Util.avoidNull(subItemCode).equals(Util.avoidNull(tableSubItemCode))
						&& Util.avoidNull(detailItemCode).equals(Util.avoidNull(tableDetailItemCode))) {
						match = true;
					}
				}

				if (match) {
					dialog.tblSelectedList.addRow(new Object[] { code, names, tItem });
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

	/**
	 * �I�����ꂽ�R�[�h���X�g��Ԃ�
	 * 
	 * @return �I�����ꂽ�R�[�h���X�g
	 */
	public List<String> getCodeList() {

		List<String> list = new ArrayList<String>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.entity);
				list.add(item.getCode());
			}
		}

		return list;

	}

	/**
	 * �⏕�A����ʂɏo�͂����ۂɁA�⏕�A���󂪖������̂ł��o�͂��邩��ݒ肷��B
	 * 
	 * @param bln
	 */
	public void setGetAllItems(boolean bln) {
		subItemCondition.setGetNotExistSubItem(bln);
		detailItemCondition.setGetNotExistDetailItem(bln);
	}

	/**
	 * �Ȗڃ��x�����Œ肷��B
	 * 
	 * @param itemLevel �Ȗڃ��x��
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		this.itemLevel = itemLevel;
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}
		((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setItemLevel(itemLevel);
		((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setEnabled(false);
		refresh();
	}

	/**
	 * �Ȗڃ��x�����ő�ŌŒ肷��B<br>
	 * ����Ȗڂ��g�p����ꍇ�͓���ȖځA�g�p���Ȃ��ꍇ�͕⏕�ȖڌŒ�B
	 * 
	 * @param ac ��v�ݒ�
	 */
	public void setItemLevelMax(AccountConfig ac) {
		if (ac.isUseDetailItem()) {
			setItemLevel(ItemLevel.DETAILITEM);
		} else {
			setItemLevel(ItemLevel.SUBITEM);
		}
	}

	/**
	 * �������X�g�̑ޔ�
	 */
	@Override
	public void saveListData() {

		// �Ȗڃ��x����ޔ�
		itemLevel = ((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.getItemLevel();
		
//		// �f�[�^��ޔ�
//		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
//		dataWhenOpened = (Vector) tableModel.getDataVector().clone();
//
//		if (dialog.tblList.getRowCount() > 0) {
//			dialog.tblList.setRowSelectionInterval(0, 0);
//		}

	}

	/**
	 * �������X�g�̑ޔ�
	 */
	@Override
	public void saveSelectedListData() {

		// �Ȗڃ��x����ޔ�
		itemLevel = ((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.getItemLevel();
		
		// �f�[�^��ޔ�
		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		dataSelectedWhenOpened = (Vector) tableSelectedModel.getDataVector().clone();

		if (dialog.tblSelectedList.getRowCount() > 0) {
			dialog.tblSelectedList.setRowSelectionInterval(0, 0);
		}

	}

	/**
	 * �Ȗڃ��x���̖߂�
	 */
	@Override
	public void loadItemLevel() {
		
		// �Ȗڃ��x����߂�
		((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setItemLevel(itemLevel);

	}
	
	/**
	 * �������X�g�̖߂�
	 */
	@Override
	public void loadListData() {

//		// �ޔ�����߂�
//		dialog.tblList.removeRow();
//		dialog.tblSelectedList.removeRow();
//
//		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
//		if (dataWhenOpened != null) {
//			for (int i = 0; i < dataWhenOpened.size(); i++) {
//				tableModel.addRow((Vector) dataWhenOpened.get(i));
//			}
//		}
		refresh();

		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		if (dataSelectedWhenOpened != null) {
			for (int i = 0; i < dataSelectedWhenOpened.size(); i++) {
				tableSelectedModel.addRow((Vector) dataSelectedWhenOpened.get(i));
			}
		}

	}
}
