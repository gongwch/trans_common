package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * �D�C�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TVesselOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected VesselSearchCondition condition;

	/**
	 * @param field
	 */
	public TVesselOptionalSelectorController(TVesselOptionalSelector field) {
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
	protected VesselSearchCondition createSearchCondition() {
		return new VesselSearchCondition();
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

		dialog.tblList.addColumn(TVesselOptionalSelectorDialog.SC.code, getWord("C11758"), 100); // �D�R�[�h
		dialog.tblList.addColumn(TVesselOptionalSelectorDialog.SC.names, getWord("C11759"), 200); // �D����
		dialog.tblList.addColumn(TVesselOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TVesselOptionalSelectorDialog.SC.code, getWord("C11758"), 100); // �D�R�[�h
		dialog.tblSelectedList.addColumn(TVesselOptionalSelectorDialog.SC.names, getWord("C11759"), 200); // �D����
		dialog.tblSelectedList.addColumn(TVesselOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return getWord("C11760"); // �D�I��
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
			List<Vessel> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Vessel bean : list) {
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
	 * @return �D���
	 */
	@SuppressWarnings("unchecked")
	protected List<Vessel> getList(VesselSearchCondition condition) {

		try {
			List<Vessel> list = (List<Vessel>) request(getModelClass(), "get", condition);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servlet��class��Ԃ�
	 * 
	 * @return VesselManager
	 */
	protected Class getModelClass() {
		return VesselManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public VesselSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(VesselSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TVesselOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TVesselOptionalSelectorDialog.SC.names);
			Vessel dep = (Vessel) dialog.tblList
				.getRowValueAt(selectedRows[i], TVesselOptionalSelectorDialog.SC.entity);

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
				TVesselOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TVesselOptionalSelectorDialog.SC.names);
			Vessel dep = (Vessel) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TVesselOptionalSelectorDialog.SC.entity);

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

		// �I�����ꂽ�D�ꗗ���擾
		List<Vessel> list = new ArrayList<Vessel>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Vessel vessel = new Vessel();
			vessel.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelector.SC.code));
			vessel.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelector.SC.names));
			list.add(vessel);
		}

		// �Ăяo�����ɃZ�b�g
		for (Vessel vessel : list) {
			field.cbo.addItem(vessel.getCode() + " " + vessel.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TVesselOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * �I�����ꂽ�DEntity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�DEntity�ꗗ
	 */
	public List<Vessel> getEntities() {

		List<Vessel> list = new ArrayList<Vessel>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Vessel dep = (Vessel) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelectorDialog.SC.entity);
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
				Vessel dep = (Vessel) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelectorDialog.SC.entity);
				list.add(dep.getCode());
			}
		}

		return list;

	}

}
