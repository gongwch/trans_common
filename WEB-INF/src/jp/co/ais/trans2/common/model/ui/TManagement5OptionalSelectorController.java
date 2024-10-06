package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�5�̔C�ӑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TManagement5OptionalSelectorController extends TOptionalSelectorController {

	/** �������� */
	protected Management5SearchCondition condition;

	/**
	 * @param field
	 */
	public TManagement5OptionalSelectorController(TManagement5OptionalSelector field) {
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
	protected Management5SearchCondition createSearchCondition() {
		return new Management5SearchCondition();
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

		// �R�[�h
		dialog.tblList.addColumn(TManagement5OptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getManagement5Name() + getWord("C00174"), 100);
		// ����
		dialog.tblList.addColumn(TManagement5OptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getManagement5Name() + getWord("C00548"), 200);
		dialog.tblList.addColumn(TManagement5OptionalSelectorDialog.SC.entity, "", -1);

		// �R�[�h
		dialog.tblSelectedList.addColumn(TManagement5OptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getManagement5Name() + getWord("C00174"), 100);
		// ����
		dialog.tblSelectedList.addColumn(TManagement5OptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getManagement5Name() + getWord("C00548"), 200);
		dialog.tblSelectedList.addColumn(TManagement5OptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// �I��
		return getCompany().getAccountConfig().getManagement5Name() + getWord("C00324");
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
			List<Management5> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Management5 bean : list) {
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
	 * @return �Ǘ�5���
	 */
	@SuppressWarnings("unchecked")
	protected List<Management5> getList(Management5SearchCondition condition1) {

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

			List<Management5> list = (List<Management5>) request(getModelClass(), method, condition1);
			return list;
		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * Servlet��class��Ԃ�
	 * 
	 * @return class
	 */
	protected Class getModelClass() {
		return Management5Manager.class;
	}

	/**
	 * @return condition��߂��܂��B
	 */
	public Management5SearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition condition��ݒ肵�܂��B
	 */
	public void setCondition(Management5SearchCondition condition) {
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
				TManagement5OptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TManagement5OptionalSelectorDialog.SC.names);
			Management5 ma = (Management5) dialog.tblList.getRowValueAt(selectedRows[i],
				TManagement5OptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, ma });
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
				TManagement5OptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TManagement5OptionalSelectorDialog.SC.names);
			Management5 ma = (Management5) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TManagement5OptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, ma });
		}

		// �I��悩��폜
		dialog.tblSelectedList.removeSelectedRows();

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TManagement5OptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * [�m��]�{�^������
	 */
	@Override
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// �I�����ꂽ�Ǘ�5�ꗗ���擾
		List<Management5> list = new ArrayList<Management5>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Management5 management5 = new Management5();
			management5.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TManagement5OptionalSelector.SC.code));
			management5.setNames((String) dialog.tblSelectedList
				.getRowValueAt(i, TManagement5OptionalSelector.SC.names));
			list.add(management5);
		}

		// �Ăяo�����ɃZ�b�g
		for (Management5 management5 : list) {
			field.cbo.addItem(management5.getCode() + " " + management5.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	/**
	 * �I�����ꂽ�Ǘ�5Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ǘ�5Entity�ꗗ
	 */
	public List<Management5> getEntities() {

		List<Management5> list = new ArrayList<Management5>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Management5 ma = (Management5) dialog.tblSelectedList.getRowValueAt(i,
					TManagement5OptionalSelectorDialog.SC.entity);
				list.add(ma);
			}
		}

		return list;
	}

	/**
	 * �t�B�[���h��Ԃ�
	 * 
	 * @return �t�B�[���h
	 */
	public TManagement5OptionalSelector getField() {
		return (TManagement5OptionalSelector) field;
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
				Management5 ma = (Management5) dialog.tblSelectedList.getRowValueAt(i,
					TManagement5OptionalSelectorDialog.SC.entity);
				list.add(ma.getCode());
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
		condition.setManagement5CodeList(codeList.toArray(new String[0]));
		List<Management5> list = null;
		try {
			list = (List<Management5>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// �ꗗ�ɃZ�b�g
		for (Management5 bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// �^���I�Ɋm��
		btnSettle_Click();
		// Vector�ޔ�����
		saveSelectedListData();
	}
}