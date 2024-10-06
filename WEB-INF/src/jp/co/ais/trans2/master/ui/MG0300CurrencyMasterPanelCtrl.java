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
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * �ʉ݃}�X�^ ��ʃR���g���[��
 */
public class MG0300CurrencyMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0300CurrencyMasterPanel mainView;

	/** �ҏW��� */
	protected MG0300CurrencyMasterDialog editView = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
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
		mainView = new MG0300CurrencyMasterPanel();
		addMainViewEvent();
	}

	/**
	 * ���C����ʃ{�^���������̃C�x���g
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
	 * ���C����ʂ̏�����
	 */
	protected void initMainView() {
		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

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
			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.currencyRange.getCodeFrom(), mainView.currencyRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.currencyRange.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �ʉݏ����擾
			CurrencySearchCondition condition = getSearchCondition();
			List<Currency> list = getCurrency(condition);

			// ���������ɊY������ʉ݂����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {

				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �ʉݏ����ꗗ�ɕ\������
			for (Currency currency : list) {
				mainView.tbl.addRow(getRowData(currency));
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
			// �ҏW�Ώۂ̒ʉ݂��擾����
			Currency currency = getSelectedCurrency();

			// �߂�l��NULL�̏ꍇ
			if (currency == null) {
				return;
			}

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̒ʉݏ����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, currency);

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

			// ���ʑΏۂ̒ʉ݂��擾����
			Currency currency = getSelectedCurrency();

			// �߂�l��NULL�̏ꍇ
			if (currency == null) {
				return;
			}

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̒ʉݏ����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, currency);

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

			// �폜�Ώۂ̒ʉ݂��擾����
			Currency currency = getSelectedCurrency();

			// �߂�l��NULL�̏ꍇ
			if (currency == null) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteCurrency(currency);

			// �폜�����ʉ݂��ꗗ����폜
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
			if (Util.isSmallerThen(mainView.currencyRange.getCodeFrom(), mainView.currencyRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.currencyRange.getFieldFrom().requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o
			CurrencySearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C01985") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0300CurrencyMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param mode_ ���샂�[�h�B
	 * @param bean  �ʉ݁B�C���A���ʂ̏ꍇ�͓��Y�ʉݏ���ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Currency bean) {

		this.mode = mode_;
		// �V�K
		switch (mode) {
		// �V�K
		case NEW:
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editView.ctrlCurrencyCode.setRegex("[A-Z,0-9]");

			break;

		case COPY:
		case MODIFY:
			// �ҏW
			if (Mode.MODIFY == mode_) {
				editView.setTitle(getWord("C00977"));
				editView.ctrlCurrencyCode.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlCurrencyCode.setRegex("[A-Z,0-9]");
			editView.ctrlCurrencyCode.setValue(bean.getCode());
			editView.ctrlCurrencyName.setValue(bean.getName());
			editView.ctrlCurrencyNames.setValue(bean.getNames());
			editView.ctrlCurrencyNamek.setValue(bean.getNamek());
			editView.ctrlCurrencyRate.setNumber(bean.getRatePow());
			editView.ctrlCurrencyDecimalPoint.setNumber(bean.getDecimalPoint());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());
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

			// ���͂��ꂽ�ʉݏ����擾
			Currency currency = getInputedCurrency();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", currency);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(currency));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", currency);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(currency));

			}

			// �ҏW��ʂ����
			btnClose_Click();

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
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return DepartmentManager
	 */
	protected Class getModelClass() {
		return CurrencyManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�ʉ݂�Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�ʉ�
	 */
	protected Currency getInputedCurrency() {

		Currency currency = createCurrency();
		currency.setCompanyCode(getCompany().getCode());
		currency.setCode(editView.ctrlCurrencyCode.getValue());
		currency.setName(editView.ctrlCurrencyName.getValue());
		currency.setNames(editView.ctrlCurrencyNames.getValue());
		currency.setNamek(editView.ctrlCurrencyNamek.getValue());
		currency.setRatePow(editView.ctrlCurrencyRate.getIntValue());
		currency.setDecimalPoint(editView.ctrlCurrencyDecimalPoint.getIntValue());
		currency.setDateFrom(editView.dtBeginDate.getValue());
		currency.setDateTo(editView.dtEndDate.getValue());

		return currency;

	}

	/**
	 * @return �ʉ�
	 */
	protected Currency createCurrency() {
		return new Currency();
	}

	/**
	 * �ʉݏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param currency �ʉݏ��
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�ʉݏ��
	 */
	protected List<Object> getRowData(Currency currency) {
		List<Object> list = new ArrayList<Object>();

		list.add(currency.getCode()); // �ʉ݃R�[�h
		list.add(currency.getName()); // �ʉݖ���
		list.add(currency.getNames()); // �ʉݗ���
		list.add(currency.getNamek()); // �ʉ݌�������
		list.add(NumberFormatUtil.formatNumber(currency.getRatePow())); // ���[�g�W��
		list.add(NumberFormatUtil.formatNumber(currency.getDecimalPoint())); // �����_�ȉ�����
		list.add(DateUtil.toYMDString(currency.getDateFrom())); // �J�n�N����
		list.add(DateUtil.toYMDString(currency.getDateTo())); // �I���N����

		return list;
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * ���������ɊY������ʉ݂̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������ʉ݂̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Currency> getCurrency(CurrencySearchCondition condition) throws Exception {

		List<Currency> list = (List<Currency>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ�ʉ݂̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected CurrencySearchCondition getSearchCondition() {

		CurrencySearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.currencyRange.getCodeFrom());
		condition.setCodeTo(mainView.currencyRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * @return ��������
	 */
	protected CurrencySearchCondition createCondition() {
		return new CurrencySearchCondition();
	}

	/**
	 * �ꗗ�őI������Ă���ʉ݂�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���ʉ�
	 * @throws Exception
	 */
	protected Currency getSelectedCurrency() throws Exception {

		CurrencySearchCondition condition = createCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0300CurrencyMasterPanel.SC.code));

		List<Currency> list = getCurrency(condition);

		if (list == null || list.isEmpty()) {
			showMessage("I00193");// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			return null;
		}
		return list.get(0);

	}

	/**
	 * �w��̒ʉ݂��폜����
	 * 
	 * @param currency �ʉ�
	 * @throws TException
	 */
	protected void deleteCurrency(Currency currency) throws TException {
		request(getModelClass(), "delete", currency);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �ʉ݃R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrencyCode.getValue())) {
			showMessage(editView, "I00037", "C00665"); // �ʉ݃R�[�h����͂��Ă��������B
			editView.ctrlCurrencyCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���ʉ݂����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			CurrencySearchCondition condition = createCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCurrencyCode.getValue());

			List<Currency> list = getCurrency(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00371"); // �w��̒ʉ݂͊��ɑ��݂��܂��B
				editView.ctrlCurrencyCode.requestTextFocus();
				return false;
			}
		}

		// �ʉݖ��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrencyName.getValue())) {
			showMessage(editView, "I00037", "C00880"); // �ʉݖ��̂���͂��Ă��������B
			editView.ctrlCurrencyName.requestTextFocus();
			return false;
		}

		// �ʉݗ��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrencyNames.getValue())) {
			showMessage(editView, "I00037", "C00881"); // �ʉݗ��̂���͂��Ă��������B
			editView.ctrlCurrencyNames.requestTextFocus();
			return false;
		}

		// �ʉ݌������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrencyNamek.getValue())) {
			showMessage(editView, "I00037", "C00882"); // �ʉ݌������̂���͂��Ă��������B
			editView.ctrlCurrencyNamek.requestTextFocus();
			return false;
		}

		// ���[�g�W���������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrencyRate.getValue())) {
			showMessage(editView, "I00037", "C00896"); // ���[�g�W������͂��Ă��������B
			editView.ctrlCurrencyRate.requestTextFocus();
			return false;
		}

		// �����_�ȉ������������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCurrencyDecimalPoint.getValue())) {
			showMessage(editView, "I00037", "C00884"); // �����_�ȉ���������͂��Ă��������B
			editView.ctrlCurrencyDecimalPoint.requestTextFocus();
			return false;
		}

		// �����_�ȉ�������0�`4�ȊO�����͂��ꂽ�ꍇ�G���[
		if (editView.ctrlCurrencyDecimalPoint.isEditable() && editView.ctrlCurrencyDecimalPoint.getIntValue() >= 5
				|| (editView.ctrlCurrencyDecimalPoint.isEditable()
						&& editView.ctrlCurrencyDecimalPoint.getIntValue() <= -1)) {
			showMessage(editView, "I00247", "C00884", "0", "4"); // �����_�ȉ�������0�`4�͈̔͂Ŏw�肵�Ă��������B
			editView.ctrlCurrencyDecimalPoint.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // �J�n�N��������͂��Ă��������B
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // �I���N��������͂��Ă��������B
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		return true;

	}
}
