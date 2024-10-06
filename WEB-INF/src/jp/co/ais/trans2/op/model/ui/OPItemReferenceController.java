/*
 * 
 */
package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * Item reference controller.
 * 
 * @author Ngoc Nguyen
 */
public class OPItemReferenceController extends TReferenceController {

	/** The condition. */
	protected OPItemSearchCondition condition;

	/**
	 * Instantiates a new @link {@link OPItemReferenceController}
	 * 
	 * @param field the field
	 */
	public OPItemReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#init()
	 */
	@Override
	public void init() {
		super.init();

		// Inits search condition
		initSearchCondition();
	}

	/**
	 * Creates the search condition.
	 * 
	 * @return search condition
	 */
	protected OPItemSearchCondition createSearchCondition() {
		return new OPItemSearchCondition();
	}

	/**
	 * Inits the search condition.
	 */
	protected void initSearchCondition() {
		this.condition = createSearchCondition();
		this.condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#code_Verify(javax.swing.JComponent)
	 */
	@Override
	public boolean code_Verify(JComponent comp) {
		if (Util.isNullOrEmpty(this.field.getCode())) {
			this.field.name.setText(null);
			return true;

		}

		if (!(this.field.code.isValueChanged())) {
			return true;
		}

		OPItem item = getInputtedEntity();

		if (item != null) {
			this.field.code.setText(item.getCode());
			this.field.name.setText(item.getNames());
			return true;
		}

		this.field.name.setText(null);
		if (this.checkExist) {
			showMessage(this.field, "I00123", new Object[0]);
			this.field.code.requestFocus();
			this.field.code.clearOldText();
			return false;
		}
		return true;
	}

	/**
	 * [����]�{�^������
	 */
	@Override
	public void btnSearch_Click() {
		try {
			this.dialog.btnSettle.setEnabled(false);
			this.dialog.tbl.removeRow();

			OPItemSearchCondition condition_ = getCondition().clone();
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			condition_.setNamekLike(dialog.namek.getText());

			List<OPItem> list = getList(condition_);
			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (OPItem bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getNames(), bean.getNamek() });
			}

			// �m��{�^�������\
			dialog.btnSettle.setEnabled(true);
			// ��s�ڃt�H�[�J�X
			dialog.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�m��]�{�^������
	 */
	@Override
	public void btnSettle_Click() {
		try {
			// �ꗗ��n�I�����ꂽEntity���擾����
			OPItem bean = getSelectedEntity();
			entity = bean;

			// Entity�����݂���΁A�R�[�h�Ɩ��̂������_�C�A���O�Ăяo�����ɃZ�b�g
			if (entity != null) {
				this.field.code.setText(bean.getCode());
				this.field.name.setText(bean.getNames());
				this.field.code.pushOldText();
			}

			// �_�C�A���O�����
			dialog.setVisible(false);
			dialog.dispose();

			// �Ăяo�����̃R�[�h�t�B�[���h�Ƀt�H�[�J�X
			this.field.code.requestFocus();

			if ((this.callBackListenerList != null) && (!(this.callBackListenerList.isEmpty()))) {
				for (TCallBackListener listener : this.callBackListenerList) {
					listener.after();
					listener.after(entity != null);
					listener.afterVerify(entity != null);
				}
			}

			this.field.code.pushOldText();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �t�B�[���h����R�[�h�𒼐ړ��͂����ꍇ�̌���
	 * 
	 * @return the inputted entity
	 */
	protected OPItem getInputtedEntity() {
		try {
			if (Util.isNullOrEmpty(this.field.getCode())) {
				return null;
			}

			OPItemSearchCondition cond = new OPItemSearchCondition();
			cond.setCompanyCode(this.condition.getCompanyCode());
			cond.setCode(this.field.getCode());

			List list = getList(cond);
			if (list == null || list.isEmpty()) {
				return null;
			}

			return (OPItem) list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets the condition.
	 * 
	 * @return the condition
	 */
	protected OPItemSearchCondition getCondition() {
		return this.condition;
	}

	/**
	 * Gets the list.
	 * 
	 * @param cond the condition
	 * @return the list
	 */
	protected List<OPItem> getList(OPItemSearchCondition cond) {
		try {
			List list = (List) request(getModelClass(), "get", new Object[] { cond });
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets the model class.
	 * 
	 * @return the model class
	 */
	protected Class getModelClass() {
		return OPItemManager.class;
	}

	/**
	 * Gets the selected entity.
	 * 
	 * @return the selected entity
	 */
	protected OPItem getSelectedEntity() {
		return ((OPItem) this.dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean));
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getDialogCaption()
	 */
	@Override
	public String getDialogCaption() {
		return getWord("COP721");
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getTableKeyName()
	 */
	@Override
	public String getTableKeyName() {
		return this.field.getClass().getSimpleName() + getSelectedEntity().getCode();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getButtonCaption()
	 */
	@Override
	public String getButtonCaption() {
		return getWord("COP721");
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getUnspecifiedEntity()
	 */
	@Override
	public Object getUnspecifiedEntity() {
		OPItem obj = new OPItem();
		obj.setCode(this.field.code.getText());
		obj.setNames(this.field.name.getText());
		return obj;
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public OPItem getEntity() {
		return (OPItem) super.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	public void setEntity(OPItem bean) {
		if (bean == null) {
			clear();
		} else {
			field.code.setText(bean.getCode());
			field.name.setText(bean.getNames());
			entity = bean;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		OPItem bean = getInputtedEntity();
		setEntity(bean);
	}
}
