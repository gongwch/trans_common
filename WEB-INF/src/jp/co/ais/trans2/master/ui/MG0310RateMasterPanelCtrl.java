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
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * ���[�g�}�X�^�̃R���g���[���[
 * 
 * @author AIS
 */
public class MG0310RateMasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0310RateMasterPanel mainView = null;

	/**
	 * �ҏW���
	 */
	protected MG0310RateMasterDialog editView = null;

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

			// �w����ʕ\��
			mainView.setVisible(true);

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
		mainView = new MG0310RateMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
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

		// �В背�[�g�A���Z�����[�g,�A�В背�[�g�i�@�\�ʉ݁j�A���Z�����[�g�i�@�\�ʉ݁j���ׂĂɃ`�F�b�N
		mainView.chkCompanyRate.setSelected(true);
		mainView.chkSettlementRate.setSelected(true);
		mainView.chkFuncCompanyRate.setSelected(true);
		mainView.chkFuncSettlementRate.setSelected(true);

		// �K�p�J�n���t�͋�
		mainView.dateFrom.setValue(null);
		mainView.dateTo.setValue(null);

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

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ���̓`�F�b�N
			if (!isInputCorrectWhenSearch()) {
				return;
			}

			// �����������擾
			RateSearchCondition condition = getSearchCondition();
			List<Rate> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (Rate rate : list) {
				mainView.tbl.addRow(getRowData(rate));
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

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̃f�[�^���擾����
			Rate rate = getSelectedRate();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃f�[�^���Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, rate);

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

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̃��[�g���擾����
			Rate rate = getSelectedRate();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃f�[�^���Z�b�g����
			createEditView();
			initEditView(Mode.COPY, rate);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^���擾����
			Rate rate = getSelectedRate();

			// �폜����
			doDelete(rate);

			// �폜�����s���ꗗ����폜
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
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			RateSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// �ʉ݃��[�g�}�X�^
			printer.preview(data, getWord("C11158") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0310RateMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param editMode ���샂�[�h�B
	 * @param rate �f�[�^�B�C���A���ʂ̏ꍇ�͓��Y�f�[�^��ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode editMode, Rate rate) {

		this.mode = editMode;

		// �V�K�̏ꍇ
		if (Mode.NEW == editMode) {

			// �V�K���
			editView.setTitle(getWord("C01698"));
			return;

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// �ҏW
			if (Mode.MODIFY == editMode) {
				// �ҏW���
				editView.setTitle(getWord("C00977"));
				editView.cboRateType.setEnabled(false);
				editView.ctrlCurrency.setEditable(false);
				editView.dtBeginDate.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				// ���ʉ��
				editView.setTitle(getWord("C01738"));
			}

			if (RateType.COMPANY == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(0);
			} else if (RateType.SETTLEMENT == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(1);
			} else if (RateType.FNC_COMPANY == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(2);
			} else if (RateType.FNC_SETTLEMENT == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(3);
			}
			editView.ctrlCurrency.setEntity(rate.getCurrency());
			editView.dtBeginDate.setValue(rate.getTermFrom());
			editView.ctrlRate.setNumber(rate.getCurrencyRate());

		}

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputCorrect()) {
				return;
			}

			// ���͂��ꂽ�f�[�^���擾
			Rate rate = getInputtedRate();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", rate);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(rate));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", rate);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(rate));

			}

			// �ҏW��ʂ����
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�f�[�^��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�f�[�^
	 */
	protected Rate getInputtedRate() {

		Rate rate = new Rate();

		rate.setCompanyCode(getCompanyCode());
		rate.setRateType(getSelectedRateType());
		rate.setCurrency(editView.ctrlCurrency.getEntity());
		rate.setTermFrom(editView.dtBeginDate.getValue());
		rate.setCurrencyRate(editView.ctrlRate.getBigDecimal());

		return rate;
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �w����ʂœ��͂��ꂽ�f�[�^�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected RateSearchCondition getSearchCondition() {

		RateSearchCondition condition = new RateSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCurrencyCodeFrom(mainView.ctrlCurrencyReferenceRange.getCodeFrom());
		condition.setCurrencyCodeTo(mainView.ctrlCurrencyReferenceRange.getCodeTo());
		condition.setCompanyRate(mainView.chkCompanyRate.isSelected());
		condition.setSettlementRate(mainView.chkSettlementRate.isSelected());
		condition.setFuncCompanyRate(mainView.chkFuncCompanyRate.isSelected());
		condition.setFuncSettlementRate(mainView.chkFuncSettlementRate.isSelected());
		condition.setDateFrom(mainView.dateFrom.getValue());
		condition.setDateTo(mainView.dateTo.getValue());

		return condition;

	}

	/**
	 * ���f����Ԃ�
	 * 
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return RateManager.class;
	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param rate �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�f�[�^
	 */
	protected List<Object> getRowData(Rate rate) {

		List<Object> list = new ArrayList<Object>();
		list.add(getWord(RateType.getName(rate.getRateType())));
		list.add(rate.getCurrency().getCode());
		list.add(DateUtil.toYMDString(rate.getTermFrom()));
		list.add(NumberFormatUtil.formatNumber(rate.getCurrencyRate(), RateFormat.getRateDecimalPoint()));
		list.add(rate);

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
	 * ���������ɊY�����郊�X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郊�X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Rate> getList(RateSearchCondition condition) throws Exception {

		List<Rate> list = (List<Rate>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă���f�[�^��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���f�[�^
	 * @throws Exception
	 */
	protected Rate getSelectedRate() throws Exception {

		Rate rate = (Rate) mainView.tbl.getSelectedRowValueAt(MG0310RateMasterPanel.SC.entity);

		RateSearchCondition condition = new RateSearchCondition();
		condition.setRateType(rate.getRateType());
		condition.setCompanyCode(rate.getCompanyCode());
		condition.setCurrencyCode(rate.getCurrency().getCode());
		condition.setTermFrom(rate.getTermFrom());

		List<Rate> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);

	}

	/**
	 * �w��̍s�f�[�^���폜����
	 * 
	 * @param rate �f�[�^
	 * @throws Exception
	 */
	protected void doDelete(Rate rate) throws Exception {
		request(getModelClass(), "delete", rate);
	}

	/**
	 * �������̓��̓`�F�b�N
	 * 
	 * @return �������̓��̓`�F�b�N�Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isInputCorrectWhenSearch() {

		// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
		if (Util.isSmallerThen(mainView.ctrlCurrencyReferenceRange.getCodeFrom(),
			mainView.ctrlCurrencyReferenceRange.getCodeTo()) == false) {
			showMessage(editView, "I00068");
			mainView.ctrlCurrencyReferenceRange.getFieldFrom().requestFocus();
			return false;
		}

		// ���[�g�敪���I���̏ꍇ�G���[
		if (!mainView.chkCompanyRate.isSelected() && !mainView.chkSettlementRate.isSelected()
			&& !mainView.chkFuncCompanyRate.isSelected() && !mainView.chkFuncSettlementRate.isSelected()) {
			// ���[�g�敪��I�����Ă��������B
			showMessage(editView, "I00096", "C00883");
			mainView.chkCompanyRate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �ʉ݂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrency.getEntity())) {
			// �ʉ݂���͂��Ă��������B
			showMessage(editView, "I00037", "C00371");
			editView.ctrlCurrency.requestTextFocus();
			return false;
		}

		// �K�p�J�n���t�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// �K�p�J�n������͂��Ă��������B
			showMessage(editView, "I00037", "C03741");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// ���[�g�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlRate.getValue())) {
			// ���[�g����͂��Ă��������B
			showMessage(editView, "I00037", "C00556");
			editView.ctrlRate.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&���ꃌ�[�g�敪&&����ʉ݃R�[�h&&����K�p�J�n���t�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			RateSearchCondition condition = new RateSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setRateType(getSelectedRateType());
			condition.setCurrencyCode(editView.ctrlCurrency.getCode());
			condition.setTermFrom(editView.dtBeginDate.getValue());

			List<Rate> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̃f�[�^�͊��ɑ��݂��܂��B
				showMessage(editView, "I00262");
				editView.cboRateType.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * �I�����ꂽRateType��Ԃ�
	 * 
	 * @return �I�����ꂽRateType
	 */
	protected RateType getSelectedRateType() {

		int selectedIndex = editView.cboRateType.getSelectedIndex();
		if (selectedIndex == 0) {
			return RateType.COMPANY;
		} else if (selectedIndex == 1) {
			return RateType.SETTLEMENT;
		} else if (selectedIndex == 2) {
			return RateType.FNC_COMPANY;
		} else if (selectedIndex == 3) {
			return RateType.FNC_SETTLEMENT;
		}
		return null;
	}
}
