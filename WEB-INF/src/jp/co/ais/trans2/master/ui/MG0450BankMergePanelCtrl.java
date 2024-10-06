package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0450BankMergePanel.IN;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.bank.BankMergeError.ErrorContent;

/**
 * ��s���p���̃R���g���[��
 */
public class MG0450BankMergePanelCtrl extends TController {

	/** �w����� */
	protected MG0450BankMergePanel mainView;

	/**
	 * �R���X�g���N�^
	 */
	@Override
	public void start() {
		try {
			// �w����ʐ���
			createMainView();

			// �w����ʂ�������
			initMainView();

			// �w����ʕ\��
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {

		return mainView;
	}

	/**
	 * �V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0450BankMergePanel();
		addMainViewEvent();
	}

	/**
	 * �{�^���̃C�x���g����
	 */
	protected void addMainViewEvent() {

		// �G�N�Z���捞
		mainView.btnImportExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doImportExcel();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ���s�{�^��
		mainView.btnExecute.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doExecute();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {

		mainView.btnExecute.setEnabled(false);

	}

	/**
	 * �G�N�Z���捞�{�^������
	 */
	protected void doImportExcel() {

		try {

			TFileChooser fc = new TFileChooser();

			TFileFilter filter = new TFileFilter(getWord("C11432"), ExtensionType.XLS, ExtensionType.XLSX); // MS-Excel�t�@�C��

			fc.setFileFilter(filter);
			fc.setMultiSelectionEnabled(true);

			if (TFileChooser.FileStatus.Selected != fc.show(mainView)) {
				return;
			}

			// File�`���Ŏ擾
			File[] file = fc.getSelectedFiles();

			BankMergeErrorCheck check = new BankMergeErrorCheck();

			for (File checkFile : file) {

				try {
					List<BankMerge> bank = check.toBankMergeData(checkFile);

					for (int i = 0; i < bank.size(); i++) {
						BankMerge bankDate = new BankMerge();

						int j = 0;
						if (i == 0) {
							j = 0;
						} else {
							j = 1;
						}
						bankDate.setOldBankCode(bank.get(i).getOldBankCode());
						bankDate.setOldBankName(bank.get(i).getOldBankName());
						bankDate.setOldBankOffCode(bank.get(i).getOldBankOffCode());
						bankDate.setNewBankCode(bank.get(i).getNewBankCode());
						bankDate.setNewBankKana(bank.get(i).getNewBankKana());
						bankDate.setNewBankKanaFb(bank.get(i).getNewBankKanaFb());
						bankDate.setNewBankName(bank.get(i).getNewBankName());
						bankDate.setNewBankOffCode(bank.get(i).getNewBankOffCode());
						bankDate.setNewBankOffKana(bank.get(i).getNewBankOffKana());
						bankDate.setNewBankOffKanaFb(bank.get(i).getNewBankOffKanaFb());
						bankDate.setNewBankOffName(bank.get(i).getNewBankOffName());
						bankDate.setDateFrom(bank.get(i).getDateFrom());
						bankDate.setDateTo(bank.get(i).getDateTo());

						request(getManagerClass(), "entry", bankDate, j);
					}

					// �G���[���Ȃ��ꍇ�A�X�V�Ώۃf�[�^��\��
					List<BankMerge> list = getBankData();

					if (list == null || list.isEmpty()) {
						return;
					} else {
						mainView.btnExecute.setEnabled(true);
					}

					// �X�V��s�ꗗ�Ƀf�[�^�\��
					mainView.tblErrList.removeRow();
					setImportList(list);

				} catch (BankMergeError e) {

					mainView.tblImportList.removeRow();
					mainView.tblErrList.removeRow();
					setErrorContents(e.getErrorList());
					return;

				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return list
	 * @throws TException
	 */
	protected List<BankMerge> getBankData() throws TException {

		List<BankMerge> list = (List<BankMerge>) request(getManagerClass(), "get");
		return list;
	}

	/**
	 * ���s�{�^������
	 */
	protected void doExecute() {

		try {

			// �Ώۋ�s�ꗗ���牽���I������ĂȂ��΂����B
			if (mainView.tblImportList.getCheckedRowCount(IN.CHK_BOX) == 0) {
				// I00596 �Ώۋ�s�ꗗ����f�[�^��I�����Ă��������B
				showMessage(mainView.getParentFrame(), "I00596");
				return;
			}

			// �����s�I���̏ꍇ
			else if (mainView.tblImportList.getCheckedRowCount(IN.CHK_BOX) > 1) {
				// ��s�̃f�[�^��I��ł�������
				showMessage(mainView.getParentFrame(), "I00044");
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �V��s�A�x�X�R�[�h����ɋ�s�}�X�^���̏d�Ȃ�f�[�^���폜

			List<BankMerge> countList = new ArrayList<BankMerge>();
			int checkIndex = mainView.tblImportList.getFirstCheckedRowIndex(MG0450BankMergePanel.IN.CHK_BOX);

			List<BankMerge> bankMergeList = (List<BankMerge>) mainView.tblImportList.getRowValueAt(checkIndex,
				MG0450BankMergePanel.IN.ENTITY);

			countList = (List<BankMerge>) request(getManagerClass(), "seachcount", bankMergeList);
			request(getManagerClass(), "updata", bankMergeList);

			// �������b�Z�[�W��\��
			showMessage(mainView.getParentFrame(), "I00013");

			// �X�V�������e��\���B
			CompList(countList);

		} catch (Exception e) {

			errorHandler(e);
		}

	}

	/**
	 * @param countList
	 */
	protected void CompList(List<BankMerge> countList) {

		try {
			mainView.tblImportList.removeRow();

			// �G���[���Ȃ��ꍇ�A�X�V�Ώۃf�[�^��\��

			List<BankMerge> list = getBankData();

			if (list == null || list.isEmpty()) {
				mainView.btnExecute.setEnabled(false);
			}

			// �X�V��s�ꗗ�Ƀf�[�^�\��
			setImportList(list);

			// �ꗗ�\��
			for (int i = 0; i < 8; i++) {

				mainView.tblCompList.addRow(getExRowData(countList, i));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * @param countList
	 * @param i
	 * @return rowData;
	 */
	protected List<Object> getExRowData(List<BankMerge> countList, int i) {

		List<Object> rowData = new ArrayList<Object>();

		String[] master = { getWord("C02323"),// ��s�}�X�^
				getWord("C02322"),// ��s�����}�X�^
				getWord("C00913"),// �Ј��}�X�^
				getWord("C02325"),// �����x�������}�X�^
				getWord("C11844"),// �ЊO�U���f�[�^
				getWord("C11845"),// �x���W�v�f�[�^
				getWord("C11846"),// �x����`�f�[�^
				getWord("C11847") // �x���U���f�[�^
		};

		if (i == 0) {
			mainView.tblCompList.removeRow();
		}
		rowData.add(master[i]);
		rowData.add(countList.get(i).getAdd());
		rowData.add(countList.get(i).getRenew());
		rowData.add(countList);

		return rowData;
	}

	/**
	 * @return BankMergeManager.class;
	 */
	protected Class getManagerClass() {
		return BankMergeManager.class;
	}

	/**
	 * �Ώۋ�s�ꗗ
	 * 
	 * @param list
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void setImportList(List<BankMerge> list) throws TException {

		mainView.tblImportList.removeRow();

		List<BankMerge> bankDataList = new ArrayList<BankMerge>();
		BankMerge bankData = list.get(0);

		for (BankMerge bankMerge : list) {
			if (!Util.avoidNull(bankMerge.getNewBankName()).equals(Util.avoidNull(bankData.getNewBankName()))) {
				mainView.tblImportList.addRow(getRowData(bankDataList));
				bankDataList = new ArrayList<BankMerge>();
			}
			bankDataList.add(bankMerge);
			bankData = bankMerge;
		}

		mainView.tblImportList.addRow(getRowData(bankDataList));

	}

	/**
	 * @param bankDataList
	 * @return rowData
	 */
	protected List<Object> getRowData(List<BankMerge> bankDataList) {

		List<Object> rowData = new ArrayList<Object>();
		rowData.add(false);
		rowData.add(bankDataList.get(0).getNewBankName());
		rowData.add(bankDataList.get(0).getOldBankName());
		rowData.add(bankDataList.size());
		rowData.add(bankDataList);

		return rowData;
	}

	/**
	 * �Č���
	 */
	protected void listSeach() {

		try {
			mainView.tblImportList.removeRow();

			// �G���[���Ȃ��ꍇ�A�X�V�Ώۃf�[�^��\��

			List<BankMerge> list = getBankData();

			if (list == null || list.isEmpty()) {
				return;
			} else {
				mainView.btnExecute.setEnabled(true);
			}

			// �ꗗ�Ƀf�[�^�\��
			setImportList(list);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �G���[���X�g��\��
	 * 
	 * @param list
	 */
	protected void setErrorContents(List<ErrorContent> list) {

		for (ErrorContent content : list) {
			List<Object> objList = new ArrayList<Object>();
			objList.add(content.getLine());
			objList.add(content.getError());

			mainView.tblErrList.addRow(objList);
		}
	}

}
