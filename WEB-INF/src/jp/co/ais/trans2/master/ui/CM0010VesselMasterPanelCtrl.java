package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.master.ui.CM0010VesselMasterPanel.SC;
import jp.co.ais.trans2.model.vessel.*;

/**
 * �D�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class CM0010VesselMasterPanelCtrl extends TController {

	/** �w����� */
	protected CM0010VesselMasterPanel mainView = null;

	/** �ҏW��� */
	protected CM0010VesselMasterDialog editView = null;

	/** �R���Ǘ����g�p���邩 true:�g�p���� */
	protected boolean isUseBM = false;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
	 * 
	 * @author AIS
	 */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

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

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new CM0010VesselMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

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

		this.isUseBM = isUseBM();

		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

		// ���O�C����ЃR�[�h���Z�b�g����
		mainView.ctrlVesselRange.ctrlVesselReferenceFrom.setCompanyCode(getCompany().getCode());
		mainView.ctrlVesselRange.ctrlVesselReferenceTo.setCompanyCode(getCompany().getCode());

		if (isUseBM) {

			// �R���Ǘ��R���g���[���}�X�^����
			mainView.tbl.addColumn(SC.stock, getWord("C11775"), 130); // �����i����R�[�h
			mainView.tbl.addColumn(SC.stockName, getWord("C11776"), 200); // �����i���喼��
			mainView.tbl.addColumn(SC.fuel, getWord("C11777"), 130); // �R�����R�[�h
			mainView.tbl.addColumn(SC.fuelName, getWord("C11778"), 200); // �R����喼��
		}

		// �D�R�[�h�J�n�Ƀt�H�[�J�X
		mainView.ctrlVesselRange.ctrlVesselReferenceFrom.code.requestFocus();

	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {

		try {

			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null);

			if (!isUseBM) {

				// �R���Ǘ��R���g���[���}�X�^�Ȃ�
				editView.tab.setEnabledAt(0, false);

			} else {

				// �R���Ǘ��R���g���[���}�X�^����
				editView.tab.setEnabledAt(0, true);

			}

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlVesselRange.getCodeFrom(), mainView.ctrlVesselRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlVesselRange.getFieldFrom().requestFocus();
				return;

			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �D�����擾
			VesselSearchCondition condition = getSearchCondition();
			List<Vessel> list = getVessel(condition);

			// ���������ɊY������D��񂪑��݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �D�����ꗗ�ɕ\������
			for (Vessel vessel : list) {
				mainView.tbl.addRow(getRowData(vessel));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̑D�����擾����
			Vessel vessel = getSelectedVessel();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̑D�����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, vessel);

			if (!isUseBM) {

				// �R���Ǘ��R���g���[���}�X�^�Ȃ�
				editView.tab.setEnabledAt(0, false);

			} else {

				// �R���Ǘ��R���g���[���}�X�^����
				editView.tab.setEnabledAt(0, true);

			}

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̑D���擾����
			Vessel vessel = getSelectedVessel();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̑D�����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, vessel);

			if (!isUseBM) {

				// �R���Ǘ��R���g���[���}�X�^�Ȃ�
				editView.tab.setEnabledAt(0, false);

			} else {

				// �R���Ǘ��R���g���[���}�X�^����
				editView.tab.setEnabledAt(0, true);

			}

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̑D�����擾����
			Vessel vessel = getSelectedVessel();

			// �폜����
			deleteVessel(vessel);

			// �폜�����D�����ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkMainView() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlVesselRange.getCodeFrom(), mainView.ctrlVesselRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlVesselRange.getFieldFrom().requestFocus();
				return;

			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o

			VesselSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11772") + ".xls");// �D�}�X�^

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new CM0010VesselMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean �D���B�C���A���ʂ̏ꍇ�͓��Y�D����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Vessel bean) {

		mode = mode_;

		// �V�K�̏ꍇ
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));// �V�K���

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			if (!isUseBM) {

				editView.tab.setVisible(false);
				editView.setSize(editView.getWidth(), editView.getHeight() - 130);
			}

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// �ҏW
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977"));// �ҏW���
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				editView.setTitle(getWord("C01738"));// ���ʉ��
			}

			if (!isUseBM) {

				editView.tab.setVisible(false);
				editView.setSize(editView.getWidth(), editView.getHeight() - 130);
			}

			// �D���
			editView.ctrlCode.setValue(bean.getCode());
			editView.ctrlName.setValue(bean.getName());
			editView.ctrlNames.setValue(bean.getNames());
			editView.ctrlNamek.setValue(bean.getNamek());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());
			editView.ctrlStockReference.code.setValue(bean.getStockCode());
			editView.ctrlStockReference.name.setValue(bean.getStockName());
			editView.ctrlFuelReference.code.setValue(bean.getFuelCode());
			editView.ctrlFuelReference.name.setValue(bean.getFuelName());
		}

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽ�D�����擾
			Vessel vessel = getInputedVessel();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^

				request(getModelClass(), "insert", vessel);
				// �ҏW��ʂ����
				btnClose_Click();

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(vessel));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", vessel);
				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(vessel));

				// �ҏW��ʂ����
				btnClose_Click();

			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �w����ʂœ��͂��ꂽ�D���̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected VesselSearchCondition getSearchCondition() {

		VesselSearchCondition condition = createVesselSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlVesselRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlVesselRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * ���f���C���^�[�t�F�[�X��Ԃ�
	 * 
	 * @return ���f���C���^�[�t�F�[�X
	 */
	protected Class getModelClass() {
		return VesselManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�D����Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�D���
	 */
	protected Vessel getInputedVessel() {

		// �D���
		Vessel vessel = createVessel();
		vessel.setCompanyCode(getCompany().getCode());
		vessel.setCode(editView.ctrlCode.getValue());
		vessel.setName(editView.ctrlName.getValue());
		vessel.setNames(editView.ctrlNames.getValue());
		vessel.setNamek(editView.ctrlNamek.getValue());
		vessel.setDateFrom(editView.dtBeginDate.getValue());
		vessel.setDateTo(editView.dtEndDate.getValue());
		vessel.setStockCode(editView.ctrlStockReference.getCode());
		vessel.setStockName(editView.ctrlStockReference.getNames());
		vessel.setFuelCode(editView.ctrlFuelReference.getCode());
		vessel.setFuelName(editView.ctrlFuelReference.getNames());

		return vessel;

	}

	/**
	 * @return �D���̌�������
	 */
	protected VesselSearchCondition createVesselSearchCondition() {
		return new VesselSearchCondition();
	}

	/**
	 * @return �D���
	 */
	protected Vessel createVessel() {
		return new Vessel();
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Vessel bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean.getStockCode());
		list.add(bean.getStockName());
		list.add(bean.getFuelCode());
		list.add(bean.getFuelName());
		list.add(bean);
		return list;
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
	 * ���������ɊY������D�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������D���̃��X�g
	 * @throws Exception
	 */
	protected List<Vessel> getVessel(VesselSearchCondition condition) throws Exception {

		List<Vessel> list = (List<Vessel>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă���D�f�[�^��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���D���
	 * @throws Exception
	 */
	protected Vessel getSelectedVessel() throws Exception {

		VesselSearchCondition condition = createVesselSearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(CM0010VesselMasterPanel.SC.code));
		condition.setCompanyCode(getCompany().getCode());

		List<Vessel> list = getVessel(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̑D�����폜����
	 * 
	 * @param vessel �D���
	 * @throws Exception
	 */
	protected void deleteVessel(Vessel vessel) throws Exception {

		request(getModelClass(), "delete", vessel);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {
		int tabIndex = 0;
		// -- ��{�^�u --
		// �D�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C11758");// �D�R�[�h
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���D�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			VesselSearchCondition condition = createVesselSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Vessel> list = getVessel(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̑D�R�[�h�͊��ɑ��݂��܂�
				showMessage(editView, "I00148", "C11758");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// �D���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C11773");// �D����
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// �D���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C11759");// �D����
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// �D�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C11774");// �D��������
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNamek.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");// �J�n�N����
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");// �I���N����
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		if (isUseBM) {
			// �����i����R�[�h�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.ctrlStockReference.getCode())) {
				showMessage(editView, "I00037", "C11775");// �����i����R�[�h
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlStockReference.requestTextFocus();
				return false;

			}
			// �R�����R�[�h�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.ctrlFuelReference.getCode())) {
				showMessage(editView, "I00037", "C11777");// �R�����R�[�h
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlFuelReference.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * Entity�����݂��邩�ǂ������`�F�b�N����
	 * 
	 * @return true:���݂��� false:���݂��Ȃ�
	 */
	protected boolean isUseBM() {
		try {
			if ((Boolean) request(getModelClass(), "isUseBM")) {

				return true;
			}
		} catch (Exception e) {
			errorHandler(e);
		}
		return false;
	}

}
