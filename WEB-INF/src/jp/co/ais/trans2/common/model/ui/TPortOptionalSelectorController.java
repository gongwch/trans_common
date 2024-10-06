package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * �`�C�ӑI���R���|�[�l���g�̃R���g���[��
 *
 * @author AIS
 */
public class TPortOptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected PortSearchCondition condition;

	/**
	 * @param field
	 */
	public TPortOptionalSelectorController(TPortOptionalSelector field) {
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
	protected PortSearchCondition createSearchCondition() {
		return new PortSearchCondition();
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
		dialog.tblList.addColumn(TPortOptionalSelectorDialog.SC.code, getWord("C00584"), 100); // �`�R�[�h
		dialog.tblList.addColumn(TPortOptionalSelectorDialog.SC.names, getWord("C00586"), 200); // �`����
		dialog.tblList.addColumn(TPortOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TPortOptionalSelectorDialog.SC.code, getWord("C00584"), 100); // �`�R�[�h
		dialog.tblSelectedList.addColumn(TPortOptionalSelectorDialog.SC.names, getWord("C00586"), 200); // �`����
		dialog.tblSelectedList.addColumn(TPortOptionalSelectorDialog.SC.entity, "", -1);

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
			List<Port> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Port bean : list) {
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
	 * @param cond
	 * @return �D���
	 */
	@SuppressWarnings("unchecked")
	protected List<Port> getList(PortSearchCondition cond) {

		try {
			List<Port> list = (List<Port>) request(getModelClass(), "get", cond);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servlet��class��Ԃ�
	 *
	 * @return PortManager
	 */
	protected Class getModelClass() {
		return PortManager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public PortSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(PortSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TPortOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TPortOptionalSelectorDialog.SC.names);
			Port dep = (Port) dialog.tblList
				.getRowValueAt(selectedRows[i], TPortOptionalSelectorDialog.SC.entity);

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
				TPortOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TPortOptionalSelectorDialog.SC.names);
			Port dep = (Port) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TPortOptionalSelectorDialog.SC.entity);

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
		List<Port> list = new ArrayList<Port>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Port port = new Port();
			port.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelector.SC.code));
			port.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelector.SC.names));
			list.add(port);
		}

		// �Ăяo�����ɃZ�b�g
		for (Port port: list) {
			field.cbo.addItem(port.getCode() + " " + port.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TPortOptionalSelectorDialog(field.getParentFrame(), true);
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
	public List<Port> getEntities() {

		List<Port> list = new ArrayList<Port>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Port dep = (Port) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelectorDialog.SC.entity);
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
				Port dep = (Port) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelectorDialog.SC.entity);
				list.add(dep.getCode());
			}
		}

		return list;

	}

}
