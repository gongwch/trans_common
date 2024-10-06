package jp.co.ais.trans2.model.bank;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;

/**
 * ��s���p���`�F�b�N
 */
public class BankMergeErrorCheck extends TController {

	/** ������ */
	protected List<BankMerge> list = new ArrayList<BankMerge>();

	/** �G���[���X�g */
	protected List<String> errorList = new LinkedList<String>();

	/** �s */
	protected List<String> errorLine = new LinkedList<String>();

	/** �t�@�C���� */
	protected String fileName = null;

	/** Excel */
	protected TExcel excel;

	/**
	 * @param file
	 * @return list
	 * @throws BankMergeError
	 */
	public List<BankMerge> toBankMergeData(File file) throws BankMergeError {

		fileName = file.getName();

		// �捞�t�@�C���`���G���[�`�F�b�N
		if (!ExtensionType.XLS.value.equals(fileName.substring(fileName.lastIndexOf(".") + 1))
			&& !ExtensionType.XLSX.value.equals(fileName.substring(fileName.lastIndexOf(".") + 1))) {
			errorList.add(getMessage("I00389"));
			errorLine.add("");
			throw error(errorList);

		}

		// �t�@�C����ǂݎ��
		try {
			excel = new TExcel(file);
		} catch (Exception e) {
			errorList.add(getMessage("I00389"));
			errorLine.add("");
			throw error(errorList);
		}

		TExcelSheet sheet = excel.getSheet(0);

		sheetCheck(sheet);

		for (int i = 1; i < sheet.getRowCount(); i++) {
			try {
				BankMerge bankMerge = new BankMerge();

				bankMerge.setOldBankCode(sheet.getString(i, 0));
				bankMerge.setOldBankOffCode(sheet.getString(i, 1));
				bankMerge.setNewBankCode(sheet.getString(i, 2));
				bankMerge.setNewBankName(sheet.getString(i, 3));
				bankMerge.setNewBankKanaFb(sheet.getString(i, 4));
				bankMerge.setNewBankKana(sheet.getString(i, 5));
				bankMerge.setNewBankOffCode(sheet.getString(i, 6));
				bankMerge.setNewBankOffName(sheet.getString(i, 7));
				bankMerge.setNewBankOffKanaFb(sheet.getString(i, 8));
				bankMerge.setNewBankOffKana(sheet.getString(i, 9));
				bankMerge.setDateFrom(DateUtil.toYMDDate(sheet.getString(i, 10)));
				bankMerge.setDateTo(DateUtil.toYMDDate(sheet.getString(i, 11)));

				list.add(bankMerge);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * @param sheet
	 * @throws BankMergeError
	 */
	protected void sheetCheck(TExcelSheet sheet) throws BankMergeError {

		String[] name = { "����s�R�[�h", "���x�X�R�[�h", "�V��s�R�[�h", "�V��s��", "�V��s�J�i(FB�p)", "�V��s�J�i", "�V�x�X�R�[�h", "�V�x�X��", "�V�x�X�J�i(FB�p)",
				"�V�x�X�J�i", "�J�n�N����", "�I���N����" };

		// ���ڃ`�F�b�N
		if (!sheet.getString(0, 0).equals(name[0]) || !sheet.getString(0, 1).equals(name[1])
			|| !sheet.getString(0, 2).equals(name[2]) || !sheet.getString(0, 3).equals(name[3])
			|| !sheet.getString(0, 4).equals(name[4]) || !sheet.getString(0, 5).equals(name[5])
			|| !sheet.getString(0, 6).equals(name[6]) || !sheet.getString(0, 7).equals(name[7])
			|| !sheet.getString(0, 8).equals(name[8]) || !sheet.getString(0, 9).equals(name[9])
			|| !sheet.getString(0, 10).equals(name[10]) || !sheet.getString(0, 11).equals(name[11])
			|| !Util.isNullOrEmpty(sheet.getString(0, 12))) {

			String message = "���ڂ̐�����v���Ă��܂���B";
			errorList.add(message);
			errorLine.add("");

		}

		// �d���`�F�b�N
		for (int i = 1; i < sheet.getRowCount(); i++) {

			String newBankCode = "";
			String newBankOffCode = "";

			newBankCode = sheet.getString(i, 2);
			newBankOffCode = sheet.getString(i, 6);

			for (int j = i; j < sheet.getRowCount(); j++) {
				if (i != j) {
					if (newBankCode.equals(sheet.getString(j, 2)) && newBankOffCode.equals(sheet.getString(j, 6))) {
						String message = j + " �s�ڂ�" + name[3] + "," + name[6] + "�͏d�����Ă��܂��B";
						String line = Integer.toString(i);

						errorList.add(message);
						errorLine.add(line);
					}
				}

			}
		}

		// NULL�`�F�b�N
		for (int i = 1; i < sheet.getRowCount(); i++) {

			if (!Util.isNullOrEmpty(sheet.getString(i, 0))) { // ����s�R�[�h���󗓂���Ȃ�������
				if (!Util.isNullOrEmpty(sheet.getString(i, 1))) { // ���x�X�R�[�h���󗓂���Ȃ�������
					if (!Util.isNullOrEmpty(sheet.getString(i, 2))) { // �V��s�R�[�h���󗓂���Ȃ�������

						for (int j = 3; j < 12; j++) {
							if (Util.isNullOrEmpty(sheet.getString(i, j))) {
								String message = name[j] + " ����͂��Ă��������B";
								String line = Integer.toString(i);

								errorList.add(message);
								errorLine.add(line);
							}
						}

					} else { // C���󗓂̏ꍇ�B

						for (int j = 3; j < 11; j++) {
							if (!Util.isNullOrEmpty(sheet.getString(i, j))) {
								String message = name[j] + " �͓��͂��Ȃ��ł��������B";
								String line = Integer.toString(i);

								errorList.add(message);
								errorLine.add(line);
							}
						}

					}
				} else {// B���󗓂̏ꍇ

					for (int j = 3; j < 13; j++) {
						if (Util.isNullOrEmpty(sheet.getString(i, j))) {
							String message = name[j] + " ����͂��Ă��������B";
							String line = Integer.toString(i);

							errorList.add(message);
							errorLine.add(line);
						}
					}

				}
			} else {
				for (int j = 2; j < 12; j++) {
					if (Util.isNullOrEmpty(sheet.getString(i, j))) {
						String message = name[j] + "����͂��Ă��������B";
						String line = Integer.toString(i);

						errorList.add(message);
						errorLine.add(line);
					}
				}

			}

		}

		// ���t�̃f�[�^�`�F�b�N
		for (int i = 1; i < sheet.getRowCount(); i++) {

			boolean startDate = DateUtil.isYMDDate(sheet.getString(i, 10));
			boolean endDate = DateUtil.isYMDDate(sheet.getString(i, 11));

			if (!startDate) {

				String message = name[10] + "��YYYY/MM/DD�̌`�œ��͂��Ă��������B";
				String line = Integer.toString(i);

				errorList.add(message);
				errorLine.add(line);
			}

			if (!endDate) {
				String message = name[11] + "��YYYY/MM/DD�̌`�œ��͂��Ă��������B";
				String line = Integer.toString(i);

				errorList.add(message);
				errorLine.add(line);
			}

		}

		// �V��s�R�[�h�������œ����Ⴄ�V��s���������Ă邩�`�F�b�N�B
		for (int i = 1; i < sheet.getRowCount(); i++) {

			String newBankCode = "";
			String newBankName = "";

			newBankCode = sheet.getString(i, 2);
			newBankName = sheet.getString(i, 3);

			for (int j = i + 1; j < sheet.getRowCount(); j++) {
				if (i != j) {
					if (newBankCode.equals(sheet.getString(j, 2)) && !newBankName.equals(sheet.getString(j, 3))) {

						String message = name[2] + "�Ɠ����� { " + j + 1 + " }�s�ڂ�" + name[3] + "���Ⴂ�܂��B";
						String line = Integer.toString(i);

						errorList.add(message);
						errorLine.add(line);
					}
				}
			}

		}

		// �V��s�x�X�R�[�h�������ňႤ�V��s�x�X���������Ă邩�`�F�b�N�B
		for (int i = 1; i < sheet.getRowCount(); i++) {

			String newBankOffCode = "";
			String newBankOffName = "";

			newBankOffCode = sheet.getString(i, 6);
			newBankOffName = sheet.getString(i, 7);

			for (int j = i + 1; j < sheet.getRowCount(); j++) {
				if (i != j) {
					if (newBankOffCode.equals(sheet.getString(j, 6)) && !newBankOffName.equals(sheet.getString(j, 7))) {

						String message = name[6] + "�Ɠ����� { " + j + " }�ڂ�" + name[7] + "���Ⴂ�܂��B";
						String line = Integer.toString(i);

						errorList.add(message);
						errorLine.add(line);

					}
				}
			}

		}

		// �J�n�N�������I���N�����`�F�b�N
		for (int i = 1; i < sheet.getRowCount(); i++) {

			if (!Util.isNullOrEmpty(Util.avoidNull(sheet.getString(i, 0)))) {

				try {
					BranchSearchCondition bankCondition = new BranchSearchCondition();

					bankCondition.setBankCode(sheet.getString(i, 0));
					bankCondition.setCode(sheet.getString(i, 1));
					List<Bank> bank = (List<Bank>) request(BankManager.class, "get", bankCondition);

					if (bank.size() == 0) {

						String message = "�����̃f�[�^�����݂��܂���B";
						String line = Integer.toString(i);
						errorList.add(message);
						errorLine.add(line);

					} else {

						try {
							if (!Util.isSmallerThenByYMD(bank.get(0).getDateFrom(), DateUtil.toYMDDate(sheet.getString(i, 11)))) {
								String message = name[10] + "��" + name[11] + "�ɂ��Ă��������B";
								String line = Integer.toString(i);
								errorList.add(message);
								errorLine.add(line);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
				} catch (TException e) {
					e.printStackTrace();
				}

			} else {
				if (Util.isNullOrEmpty(Util.avoidNull(sheet.getString(i, 2)))) {
					try {
						if (!Util.isSmallerThenByYMD(DateUtil.toYMDDate(sheet.getString(i, 10)), DateUtil.toYMDDate(sheet.getString(i, 11)))) {
							String message = name[10] + "��" + name[11] + "�ɂ��Ă��������B";
							String line = Integer.toString(i);
							errorList.add(message);
							errorLine.add(line);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
		}

		// �J�^�J�i�̂Ƃ���ɐ����A�p�ꂾ���ŏ����ꂽ�ꍇ�A�`�F�b�N�B
		for (int i = 1; i < sheet.getRowCount(); i++) {

			if (!Util.isNullOrEmpty(sheet.getString(i, 2))) {
				if (!StringUtil.containsFullSize(sheet.getString(i, 4))) {
					String message = name[4] + "���J�^�J�i�œ��͂��Ă��������B";
					String line = Integer.toString(i);
					errorList.add(message);
					errorLine.add(line);
				}
				if (!StringUtil.containsFullSize(sheet.getString(i, 5))) {
					String message = name[5] + "���J�^�J�i�œ��͂��Ă��������B";
					String line = Integer.toString(i);
					errorList.add(message);
					errorLine.add(line);
				}
				if (!StringUtil.containsFullSize(sheet.getString(i, 8))) {
					String message = name[8] + "���J�^�J�i�œ��͂��Ă��������B";
					String line = Integer.toString(i);
					errorList.add(message);
					errorLine.add(line);
				}
				if (!StringUtil.containsFullSize(sheet.getString(i, 9))) {
					String message = name[9] + "���J�^�J�i�œ��͂��Ă��������B";
					String line = Integer.toString(i);
					errorList.add(message);
					errorLine.add(line);
				}
			}

		}

		if (!errorList.isEmpty()) {
			throw error(errorList);
		}
	}

	/**
	 * �ꗗ�ɕ\������G���[��Ԃ�
	 * 
	 * @param strlist
	 * @return err
	 */
	protected BankMergeError error(List<String> strlist) {
		BankMergeError err = new BankMergeError();
		int i = 0;
		for (String str : strlist) {
			err.addErrorContent(err.new ErrorContent(errorLine.get(i), str));
			i++;
		}
		return err;
	}
}
