package jp.co.ais.trans2.model.currency;

import java.io.*;
import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;

/**
 * ���[�g�捞�f�[�^
 */
public class RateConverterImpl implements RateConverter {

	/** ������ */
	protected List<Rate> list = new ArrayList<Rate>();

	/** �捞���� */
	protected Date impDate = null;

	/** �t�@�C���� */
	protected String fileName = null;

	/** Excel */
	protected TExcel excel;

	/** �G���[ */
	protected TErroneousRateDataException err = new TErroneousRateDataException();

	/**
	 * Rate�f�[�^�擾
	 * 
	 * @param file
	 * @param companyCode
	 * @return ���[�g�捞�f�[�^
	 * @throws TErroneousRateDataException
	 */
	public List<Rate> toRateData(File file, String companyCode) throws TErroneousRateDataException {

		try {

			// �捞�t�@�C����
			fileName = file.getName();

			// �捞����
			impDate = Util.getCurrentDate();

			// �捞�t�@�C���`���G���[�`�F�b�N
			if (!ExtensionType.XLS.value.equals(ResourceUtil.getExtension(file))
				&& !ExtensionType.XLSX.value.equals(ResourceUtil.getExtension(file))) {

				// �G���[
				err.setError(err.new ErrorContent(TErroneousRateDataException.RateError.FILE, null, 0));

				throw err;
			}

			// �t�@�C����ǂݎ��

			excel = new TExcel(file);

			TExcelSheet sheet = excel.getSheet(0);

			// �V�[�g�̓��e���`�F�b�N
			excelCheck(sheet);

			for (int i = 2; i < sheet.getRowCount() + 1; i++) {
				Rate bean = new Rate();

				// ��ЃR�[�h
				bean.setCompanyCode(companyCode);

				String kbn = sheet.getString(i - 1, 0);

				if (kbn.equals("M")) {
					bean.setRateType(RateType.COMPANY);
				} else if (kbn.equals("Y")) {
					bean.setRateType(RateType.SETTLEMENT);
				} else {
					bean.setRateType(null);
				}

				bean.setTermFrom(sheet.getDate(i - 1, 1));

				Currency cur = new Currency();
				cur.setCode(sheet.getString(i - 1, 2));

				bean.setCurrency(cur);
				bean.setCurrencyRate(DecimalUtil.toBigDecimal(sheet.getDecimal(i - 1, 3)));

				list.add(bean);

			}
		} catch (Exception e) {
			throw err;
		}

		return list;

	}

	/**
	 * �V�[�g�̕s���`�F�b�N
	 * 
	 * @param sheet
	 * @throws TErroneousRateDataException
	 */
	protected void excelCheck(TExcelSheet sheet) throws TErroneousRateDataException {

		err = new TErroneousRateDataException();

		for (int i = 2; i < sheet.getRowCount() + 1; i++) {

			// �敪�̒l�`�F�b�N
			try {
				String kbn = sheet.getString(i - 1, 0);
				if (!kbn.equals("M") && !kbn.equals("Y")) {
					err = error(TErroneousRateDataException.RateError.VALUE,
						TErroneousRateDataException.RateError.DIVISION, i);
					throw err;
				}

				// ���t�̒l�`�F�b�N
				sheet.getString(i - 1, 1);
				sheet.getDate(0, 1);

				if (sheet.getDate(i - 1, 1) == null) {
					err = error(TErroneousRateDataException.RateError.VALUE,
						TErroneousRateDataException.RateError.TERM_FROM, i);
					throw err;
				}

				// �ʉ݂̒l�`�F�b�N
				if (Util.isNullOrEmpty(sheet.getString(i - 1, 2))) {
					err = error(TErroneousRateDataException.RateError.NULL, null, i);
					throw err;

				}

			} catch (Exception e) {
				throw err;
			}

			// ���[�g�̒l�`�F�b�N
			try {
				if (Util.isNullOrEmpty(sheet.getString(i - 1, 3)) || sheet.getDecimal(i - 1, 3) == BigDecimal.ZERO) {
					err = error(TErroneousRateDataException.RateError.VALUE,
						TErroneousRateDataException.RateError.RATE, i);
					throw err;
				}
			} catch (Exception e) {
				if (Util.isNullOrEmpty(err.getError())) {
					err = error(TErroneousRateDataException.RateError.NULL, null, i);
				}
				// �^��������ꍇ
				throw err;
			}

			// ���ڂ̉ߕs���`�F�b�N
			if (!Util.isNullOrEmpty(sheet.getString(i - 1, 4))) {
				err = error(TErroneousRateDataException.RateError.NULL, null, i);
				throw err;
			}

		}

		// �E�v�J�n��
		Date beginDate = null;

		// �ʉ�
		String code = "";

		// �t�@�C�����ɓ���̃f�[�^�����݂��邩�`�F�b�N
		for (int i = 2; i < sheet.getRowCount() + 1; i++) {

			if (i == 2) {
				beginDate = sheet.getDate(i - 1, 1);
				code = sheet.getString(i - 1, 2);
			}

			for (int j = 2; j < sheet.getRowCount() + 1; j++) {
				if (i != j) {
					if (beginDate.compareTo(sheet.getDate(j - 1, 1)) == 0 && code.equals(sheet.getString(j - 1, 2))) {
						err = error(TErroneousRateDataException.RateError.EXISTENCE_FILE, null, i);
						throw err;
					}

				}
			}

			if (i != sheet.getRowCount()) {
				beginDate = sheet.getDate(i, 1);
				code = sheet.getString(i, 2);
			}

		}

	}

	/**
	 * �\������G���[��Ԃ�
	 * 
	 * @param errorType
	 * @param detaType
	 * @param rows
	 * @return err
	 */
	protected TErroneousRateDataException error(TErroneousRateDataException.RateError errorType,
		TErroneousRateDataException.RateError detaType, int rows) {
		err.setError(err.new ErrorContent(errorType, detaType, rows));
		return err;
	}

}