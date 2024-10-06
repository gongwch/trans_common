package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.master.ui.CM0040MLITItemMasterPanel.SC;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уA�C�e���}�X�^��ʃR���g���[��
 * 
 * @author AIS
 */
public class CM0040MLITItemMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/** �p�l�� */
	protected CM0040MLITItemMasterPanel mainView;

	/** �ҏW��� */
	protected CM0040MLITItemMasterDialog editView;

	/** �C���O�̃f�[�^ */
	protected YJItem editBean = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �A�����уA�C�e���}�X�^�p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new CM0040MLITItemMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		mainView.btnNew.addActionListener(new ActionListener() {

			// �V�K�{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnSearch.addActionListener(new ActionListener() {

			// �����{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnModify.addActionListener(new ActionListener() {

			// �ҏW�{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnCopy.addActionListener(new ActionListener() {

			// ���ʃ{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnDelete.addActionListener(new ActionListener() {

			// �폜�{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnListOutput.addActionListener(new ActionListener() {

			// ���X�g�o�̓{�^��������
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * �V�K�o�^����
	 */
	protected void btnNew_Click() {

		try {
			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ��������
	 */
	protected void btnSearch_Click() {

		try {
			// ���������`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// YJItem�����擾
			YJItemSearchCondition condition = getSearchCondition();
			List<YJItem> list = getList(condition);

			// ���������ɊY������YJItem�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView, "I00022");
				return;
			}

			// YJItem�����ꗗ�ɕ\������
			for (YJItem bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}
			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {
			if (!checkSelectedRow()) {
				return;
			}

			// �ҏW�Ώۂ�YJItem���擾����
			YJItem bean = getSelectedRowData();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ�YJItem�����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, bean);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {
			if (!checkSelectedRow()) {
				return;
			}

			// ���ʑΏۂ�YJItem���擾����
			YJItem bean = getSelectedRowData();

			// �ҏW�O�f�[�^���폜
			editBean = null;

			// ���ʉ�ʂ𐶐����A���ʑΏۂ�YJItem�����Z�b�g����
			createEditView();

			initEditView(Mode.COPY, bean);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new CM0040MLITItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {
			if (!checkSelectedRow()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00007")) {
				return;
			}

			// �폜�Ώۂ�YJItem���擾����
			YJItem bean = getSelectedRowData();

			// �폜����
			request(getModelClass(), "delete", bean);

			// �폜�����s���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkSelectedRow() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}
		return true;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */
	protected void btnExcel_Click() {

		try {
			// ���������`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			YJItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("CBL302") + ".xls"); // YJItem�}�X�^.xls

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���C����ʃ`�F�b�N
	 * 
	 * @return true:���Ȃ�
	 */
	protected boolean checkMainView() {

		// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
		if (!mainView.ctrlRange.isSmallerFrom()) {
			showMessage(mainView, "I00068");
			mainView.ctrlRange.getFieldFrom().code.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditView_Settle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditView_Close_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnEditView_Settle_Click() {

		try {
			// ���̓`�F�b�N
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽYJItem�����擾
			reflectEditBean();

			// �폜���o�^
			editBean = (YJItem) request(getModelClass(), "entryMaster", editBean);

			if (Mode.NEW == mode || Mode.COPY == mode) {
				// �V�K�o�^�̏ꍇ

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(editBean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

			} else if (Mode.MODIFY == mode) {
				// �C���̏ꍇ

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(editBean));

			}
			// �ҏW��ʂ����
			btnEditView_Close_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlItemCode.getValue())) {
			// �G���[���b�Z�[�W�o��
			showMessage(editView, "I00002", "Item Code"); // �A�C�e���R�[�h
			editView.ctrlItemCode.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���YJItem�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			YJItemSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setItemCode(editView.ctrlItemCode.getValue());

			List<YJItem> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "Item Code"); // �A�C�e���R�[�h
				editView.ctrlItemCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽYJItem��Ԃ�
	 */
	protected void reflectEditBean() {

		if (editBean == null) {
			editBean = createEntity();
		}

		editBean.setKAI_CODE(getCompanyCode()); // ��ЃR�[�h
		editBean.setITEM_CODE(editView.ctrlItemCode.getValue()); // �R�[�h
		editBean.setITEM_NAME(editView.ctrlItemName.getValue()); // ����
		editBean.setREMARK(editView.ctrlRemark.getValue()); // Remark
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnEditView_Close_Click() {
		editView.setVisible(false);
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param editMode ���샂�[�h
	 * @param bean
	 */
	protected void initEditView(Mode editMode, YJItem bean) {
		this.mode = editMode;

		switch (editMode) {
			case NEW:
				// �V�K
				editView.setTitle(getWord("Item") + " " + getWord("C01698")); // �V�K���

				editBean = null;// �ҏW�O�f�[�^���폜
				break;

			case MODIFY:
				// �ҏW
				editView.setTitle(getWord("Item") + " " + getWord("C00977")); // �ҏW���
				editView.ctrlItemCode.setEditable(false);
				editView.setEditMode();
				editBean = bean;// �ҏW�O�f�[�^��ێ�

				break;

			case COPY:
				// ����
				editView.setTitle(getWord("Item") + " " + getWord("C01738")); // ���ʉ��
				editBean = null;// �ҏW�O�f�[�^���N���A
				break;
		}
		setEditDialog(bean);
	}

	/**
	 * ��ʂ�YJItem�����Z�b�g����
	 * 
	 * @param bean
	 */
	protected void setEditDialog(YJItem bean) {

		if (bean == null) {
			return;
		}

		editView.ctrlItemCode.setValue(bean.getITEM_CODE()); // �R�[�h
		editView.ctrlItemName.setValue(bean.getITEM_NAME()); // ����
		editView.ctrlRemark.setValue(bean.getREMARK()); // Remark
	}

	/**
	 * �ꗗ�őI������Ă���YJItem��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���YJItem
	 * @throws Exception
	 */
	protected YJItem getSelectedRowData() throws Exception {

		YJItemSearchCondition condition = createCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setItemCode((String) mainView.tbl.getSelectedRowValueAt(SC.code));

		List<YJItem> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �{�^��(�V�K�A�폜�A�ҏW�A���ʁA���X�g�o��)���b�N
	 * 
	 * @param boo
	 */
	protected void lockBtn(boolean boo) {
		mainView.btnDelete.setEnabled(boo);
		mainView.btnModify.setEnabled(boo);
		mainView.btnCopy.setEnabled(boo);
		mainView.btnListOutput.setEnabled(boo);
	}

	/**
	 * �w����ʂœ��͂��ꂽYJItem�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected YJItemSearchCondition getSearchCondition() {
		YJItemSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setItemCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setItemCodeTo(mainView.ctrlRange.getCodeTo());

		return condition;
	}

	/**
	 * @return YJItem
	 */
	protected YJItem createEntity() {
		return new YJItem();
	}

	/**
	 * @return ��������
	 */
	protected YJItemSearchCondition createCondition() {
		return new YJItemSearchCondition();
	}

	/**
	 * ���������ɊY�����郊�X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郊�X�g
	 * @throws Exception
	 */
	protected List<YJItem> getList(YJItemSearchCondition condition) throws Exception {
		List<YJItem> list = (List<YJItem>) request(getModelClass(), "getItems", condition);
		return list;
	}

	/**
	 * Manager�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return YJItemManager.class;
	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽYJItem
	 */
	protected List<String> getRowData(YJItem bean) {
		List<String> list = new ArrayList<String>();

		list.add(bean.getITEM_CODE()); // �R�[�h
		list.add(bean.getITEM_NAME()); // ����
		list.add(bean.getREMARK()); // Remark

		return list;
	}
}
