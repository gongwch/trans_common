package jp.co.ais.trans2.common.excel;

import java.io.*;
import java.math.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;

/**
 * �捞�\�G�N�Z�������N���X
 * 
 * @param <EntityType> �捞���ɐ�������G���e�B�e�B�̌^
 * @param <ColumnType> �o�́E�捞�ɗ��p����G�N�Z�����ڒ�`
 */
public abstract class TExcelImportable<EntityType extends TransferBase, ColumnType extends ExcelImportableColumn>
	extends TExcel {

	/** �ҏW���V�[�g */
	protected TExcelSheet sheet;

	/** �捞�� �G���[�ꗗ */
	protected List<Message> errorList;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang
	 */
	public TExcelImportable(String lang) {
		super(lang);
		errorList = new ArrayList();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param file
	 * @throws TException
	 */
	public TExcelImportable(File file) throws TException {
		super(file);
		errorList = new ArrayList();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param type
	 */
	public TExcelImportable(String lang, ExcelType type) {
		super(lang, type);
		errorList = new ArrayList();
	}

	/**
	 * �G���[���X�g���擾����
	 * 
	 * @return �G���[���X�g
	 */
	public List<Message> getErrorList() {
		return errorList;
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param entityList
	 * @return �G�N�Z���o�C�i��
	 * @throws TException
	 */
	public byte[] getExcel(List<EntityType> entityList) throws TException {
		try {
			createReport(entityList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * �G�N�Z�����쐬����
	 * 
	 * @param entityList
	 */
	protected void createReport(List<EntityType> entityList) {
		// �V�[�g�ǉ�
		sheet = addSheet(getSheetName());

		// ���`��������
		initHeaderColumn();

		// ���ׂ��L�q
		drawDetail(entityList);

	}

	/**
	 * �o�͎��̃V�[�g���̂��擾
	 * 
	 * @return �V�[�g����
	 */
	protected abstract String getSheetName();

	/**
	 * ���`������������
	 */
	protected void initHeaderColumn() {
		for (ColumnType col : getAllColumns()) {
			if (isOutput(col)) {
				sheet.addColumn(getColumnName(col), col.getWidth());
			}
		}
	}

	/**
	 * ���ׂ��L�ڂ���
	 * 
	 * @param entityList
	 */
	protected void drawDetail(List<EntityType> entityList) {
		for (EntityType bean : entityList) {
			TExcelRow newRow = sheet.addRow();
			for (ColumnType col : getAllColumns()) {
				if (!isOutput(col)) {
					continue;
				}
				addRowCellValue(col, bean, newRow);
			}
		}
	}

	/**
	 * �s�̃Z����Entity�̒l��ǉ�
	 * 
	 * @param col
	 * @param bean
	 * @param newRow
	 */
	protected abstract void addRowCellValue(ColumnType col, EntityType bean, TExcelRow newRow);

	/**
	 * ��`���ꂽExcel�J���������ׂĕԋp
	 * 
	 * @return ��`���ꂽ�J����
	 */
	protected abstract ColumnType[] getAllColumns();

	/**
	 * �`�[�G�N�Z����ǂݍ��ݎd�󖾍׍s�ւƕϊ�
	 * 
	 * @param file
	 * @return �d�󖾍׍s���X�g
	 * @throws TException
	 */
	public List<EntityType> convertToEntityList(File file) throws TException {
		try {
			// excel�t�@�C����ǂݎ��
			TExcel excel = new TExcel(file);
			// �V�[�g���݃`�F�b�N
			if (!isExistsSheet(excel)) {
				// �捞�ΏۊO�̃t�@�C���ł��B�V�X�e������o�͂����t�@�C���𗘗p���Ă��������B
				throw new TWarningException(getMessage("I00775"));
			}
			sheet = excel.getSheet(0);
			// �s���݃`�F�b�N
			if (!isExistsRow()) {
				// �t�@�C���ɗL���ȍs������܂���B
				throw new TWarningException(getMessage("I00296"));
			}
			// �w�b�_�`�F�b�N(�J�����ύX���Ȃ����e���v���[�g�̐��������Ƃ�)
			if (!isRightTemplate()) {
				// �捞�ΏۊO�̃t�@�C���ł��B�V�X�e������o�͂����t�@�C���𗘗p���Ă��������B
				throw new TWarningException(getMessage("I00775"));
			}

			// �l�}�b�s���O
			List<EntityType> entityList = new ArrayList();
			for (int row = 1; row < sheet.getRowCount(); row++) {
				EntityType bean = createEntity();
				for (ColumnType col : getAllColumns()) {
					if (!isImportColumn(col)) {
						continue;
					}
					if (verifyCellValue(row, col)) {
						// �Z���l����������ۂꍇ�l���Z�b�g
						setColumnValue(row, col, bean);
					}
				}
				if (verifyRowValue(row)) {
					// �s����������ۂꍇbean��ǉ�
					entityList.add(bean);
				}
			}

			return entityList;

		} catch (TWarningException e) {
			throw e;
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			// �t�@�C���̓ǂݍ��݂Ɏ��s���܂����B
			throw new TException("E00021");
		}

	}

	/**
	 * �G�N�Z���t�@�C���̃G���[�`�F�b�N�����{<br>
	 * �G���[�񑶍݂̏ꍇ��̃��b�Z�[�W���X�g��ԋp
	 * 
	 * @param file
	 * @return �G�N�Z���G���[�ꗗ
	 * @throws TException
	 */
	public List<Message> verifyImportExcel(File file) throws TException {
		// excel�t�@�C����ǂݎ��
		TExcel excel = new TExcel(file);
		return verifyImportExcel(excel);
	}

	/**
	 * �G�N�Z���t�@�C���̃G���[�`�F�b�N�����{<br>
	 * �G���[�񑶍݂̏ꍇ��̃��b�Z�[�W���X�g��ԋp
	 * 
	 * @param excel
	 * @return �G�N�Z���G���[�ꗗ
	 */
	public List<Message> verifyImportExcel(TExcel excel) {
		errorList = new ArrayList();
		// �V�[�g���݃`�F�b�N
		if (!isExistsSheet(excel)) {
			// �捞�ΏۊO�̃t�@�C���ł��B�V�X�e������o�͂����t�@�C���𗘗p���Ă��������B
			errorList.add(new Message("I00775"));
			return errorList;
		}
		sheet = excel.getSheet(0);
		// �s���݃`�F�b�N
		if (!isExistsRow()) {
			// �t�@�C���ɗL���ȍs������܂���B
			errorList.add(new Message("I00296"));
			return errorList;
		}
		// �w�b�_�`�F�b�N(�J�����ύX���Ȃ����e���v���[�g�̐��������Ƃ�)
		if (!isRightTemplate()) {
			// �捞�ΏۊO�̃t�@�C���ł��B�V�X�e������o�͂����t�@�C���𗘗p���Ă��������B
			errorList.add(new Message("I00775"));
			return errorList;
		}
		// �V�[�g���e�l�̌���
		verifySheetValues();
		return errorList;
	}

	/**
	 * �V�[�g���̊e�l������
	 */
	protected void verifySheetValues() {
		for (int row = 1; row < sheet.getRowCount(); row++) {
			for (ColumnType col : getAllColumns()) {
				if (!isImportColumn(col)) {
					continue;
				}
				verifyCellValue(row, col);
			}
			verifyRowValue(row);
		}
	}

	/**
	 * �V�[�g���݃`�F�b�N
	 * 
	 * @param excel
	 * @return true: ���Ȃ�
	 */
	protected boolean isExistsSheet(TExcel excel) {
		if (excel.getSheetCount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * �V�[�g�s���݃`�F�b�N
	 * 
	 * @return true:���Ȃ�
	 */
	protected boolean isExistsRow() {
		if (sheet.getRowCount() == 1) {
			return false;
		}
		return true;
	}

	/**
	 * �捞�Ώۂ̃J������
	 * 
	 * @param col
	 * @return true:�捞�Ώ�
	 */
	protected boolean isImportColumn(ColumnType col) {
		if (!isOutput(col)) {
			// �o�͂���Ȃ��ꍇ�捞��Ώ�
			return false;
		}
		return col.isImportColumn();
	}

	/**
	 * Bean�𐶐�����
	 * 
	 * @return Bean
	 */
	protected abstract EntityType createEntity();

	/**
	 * �Z���P�ʂł͂Ȃ��A�s�P�ʂł̒l�̌��؂��s��<br>
	 * ����̒l�̏ꍇ�̂ݕK�{��I�������i������̂Ȃ�
	 * 
	 * @param row
	 * @return true:���Ȃ�
	 */
	@SuppressWarnings("unused")
	protected boolean verifyRowValue(int row) {
		// �����Ȃ� Override�p
		return true;
	}

	/**
	 * �e�Z���̒l�����؂���<br>
	 * �^�Ƃ��Ă̗L�����A�ő包���̃`�F�b�N�A�K�{�`�F�b�N
	 * 
	 * @param row
	 * @param col
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellValue(int row, ColumnType col) {
		boolean isOK = true;
		if (!col.isImportColumn()) {
			// �捞�ΏۃJ�����ł͂Ȃ��ꍇ�I��
			return true;
		}
		// �K�{�`�F�b�N
		if (col.isMandatory()) {
			isOK = isOK && verifyCellMandatory(row, col);
		}
		switch (col.getColumnType()) {
			case DATE:
				// �f�[�^�^�`�F�b�N ���t
				isOK = isOK && verifyCellIsDate(row, col);
				break;
			case ALPHANUMERIC:
				// ���p�p���`�F�b�N
				isOK = isOK && verifyCellAlphanumeric(row, col, false);
				// �����`�F�b�N
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case ALPHANUMERIC_SYMBOLS:
				// ���p�p���`�F�b�N
				isOK = isOK && verifyCellAlphanumeric(row, col, true);
				// �����`�F�b�N
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case STRING:
				// �����`�F�b�N
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case STRING_KANA:
				isOK = isOK && verifyCellIsKana(row, col);
				// �����`�F�b�N
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case INTEGER:
				// �����`�F�b�N
				isOK = isOK && verifyCellInteger(row, col);
				break;
			case DECIMAL:
				// �����`�F�b�N
				isOK = isOK && verifyCellDecimal(row, col);
				break;
		}
		return isOK;

	}

	/**
	 * �Z���̌^�`�F�b�N<br>
	 * ���p�ł݂̂����͂���Ă��邩
	 * 
	 * @param row
	 * @param col
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellIsKana(int row, ColumnType col) {
		String value = sheet.getString(row, getColumnIndex(col));
		if (!StringUtil.isHalfKana(value)) {
			// {0}�s�� {1}�J������:{2}�ɔ��p�ňȊO�̕������܂܂�Ă��܂��B{3}
			Message err = new Message("I00776", row, getColumnIndex(col), getColumnName(col), value);
			errorList.add(err);
			return false;
		}
		return true;
	}

	/**
	 * �Z���̌^�`�F�b�N<br>
	 * ���������͂���Ă��邩<br>
	 * �ő包�����ݒ肳��Ă���ꍇ�`�F�b�N���s��
	 * 
	 * @param row
	 * @param col
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellInteger(int row, ColumnType col) {
		// {0}�s�� {1}�J������:{2}��{3}�ɕϊ��ł��܂���B{4}
		String notNumber = "I00762";
		// ����
		String intName = "C11888";
		// {0}�s�� {1}�J������:{2}�̒l���ő包���𒴂��Ă��܂��B���́F{3}�A�ő�F{4}
		String numOver = "I00759";
		try {
			int intVal = sheet.getInt(row, getColumnIndex(col));
			if (col.getMaxLength() <= 0) {
				return true;
			}
			if (Math.pow(10, col.getMaxLength()) < intVal) {
				// �����I�[�o�[
				String len = Util.avoidNull(intVal);
				Message msg = new Message(numOver, row, getColumnIndex(col), getColumnName(col), len.length(),
					col.getMaxLength());
				errorList.add(msg);
				return false;
			}
		} catch (Exception e) {
			// int�ϊ����s���i�����_���܂ޏꍇ�Ȃǁj
			// �^�ϊ��̃~�X�z��
			Message msg = new Message(notNumber, row, getColumnIndex(col), getColumnName(col), intName);
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * �Z���̌^�`�F�b�N<br>
	 * Decimal�����͂���Ă��邩
	 * 
	 * @param row
	 * @param col
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellDecimal(int row, ColumnType col) {
		// {0}�s�� {1}�J������:{2}��{3}�ɕϊ��ł��܂���B{4}
		String notNumber = "I00762";
		// ���l
		String decimalItem = "C02160";
		// {0}�s�� {1}�J������:{2}�̒l���ő包���𒴂��Ă��܂��B���́F{3}�A�ő�F{4}
		String numOver = "I00759";

		int maxLength = col.getMaxLength();
		int decimalPoint = col.getDecimalPoint();
		try {
			BigDecimal value = getDecimal(row, getColumnIndex(col));
			value = DecimalUtil.avoidNull(value);
			value.stripTrailingZeros();
			String limit = "" + col.getMaxLength() + "," + col.getDecimalPoint();
			if (DecimalUtil.getIntLength(value) > maxLength - decimalPoint) {
				// �������������w��̌����𒴂���ꍇ
				Message msg = new Message(numOver, row, getColumnIndex(col), getColumnName(col),
					DecimalUtil.getIntLength(value), limit);
				errorList.add(msg);
				return false;
			}
			if (value.scale() > decimalPoint) {
				// �����_�ȉ��������w��̌����𒴂���ꍇ
				Message msg = new Message(numOver, row, getColumnIndex(col), getColumnName(col), value.scale(), limit);
				errorList.add(msg);
				return false;
			}
		} catch (Exception e) {
			// Decimal�ϊ����s��
			// �^�ϊ��̃~�X�z��
			Message msg = new Message(notNumber, row, getColumnIndex(col), getColumnName(col), decimalItem);
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * �Z���̒l�`�F�b�N �K�{���ڂ����͂���Ă��邩�𔻒�
	 * 
	 * @param row
	 * @param col
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellMandatory(int row, ColumnType col) {
		if (!col.isMandatory()) {
			return true;
		}
		String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));
		// {0}�s�� {1}�J������:�K�{���ڂɒl������܂���B{2}
		String mandatory = "I00758";
		if (Util.isNullOrEmpty(value)) {
			Message msg = new Message(mandatory, row, getColumnIndex(col), getColumnName(col));
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * �Z���l�������𒴂��Ă��Ȃ������`�F�b�N
	 * 
	 * @param row
	 * @param col
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellCharacterLength(int row, ColumnType col) {
		if (col.getMaxLength() <= 0) {
			// �ő啶�������ݒ�̏ꍇ�A���������{
			return true;
		}
		if (col.getColumnType() == ExcelColumnType.DATE) {
			// ���t�͌^�`�F�b�N�Ŗ��Ȃ�
			return true;
		}
		// {0}�s�� {1}�J������:{2}�̒l���ő包���𒴂��Ă��܂��B���́F{3}�A�ő�F{4}
		String lengthOver = "I00759";
		String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));

		// �Z���̃^�C�v�����p�p��or���p�p���L���̏ꍇ�͐��l�^�̉\��������̂ŏ����ቺ�폜����
		if (col.getColumnType() == ExcelColumnType.ALPHANUMERIC
			|| col.getColumnType() == ExcelColumnType.ALPHANUMERIC_SYMBOLS) {
			value = numeric4IntegerString(value);
		}

		if (value.length() > col.getMaxLength()) {
			Message msg = new Message(lengthOver, row, getColumnIndex(col), getColumnName(col), value,
				col.getMaxLength());
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * ���l�����`�F�b�N
	 * 
	 * @param row �s
	 * @param col �J����
	 * @param allowsSymbols
	 * @return true:���Ȃ�
	 */
	protected boolean verifyCellAlphanumeric(int row, ColumnType col, boolean allowsSymbols) {
		String value = numeric4IntegerString(Util.avoidNull(sheet.getString(row, getColumnIndex(col))));

		if (!Util.isNullOrEmpty(value)) {
			String regex = allowsSymbols ? "[^A-Za-z0-9 ./#&-_]" : "[^A-Za-z0-9]";

			BigDecimal bd = new BigDecimal(String.valueOf(Double.MIN_VALUE));
			try {
				bd = new BigDecimal(value);
			} catch (NumberFormatException ne) {
				// ��O�������͐��l�ł͂Ȃ��̂ŉ������Ȃ�
			}
			if (!bd.equals(new BigDecimal(String.valueOf(Double.MIN_VALUE)))) {
				// �Z�������l�^�̏ꍇ�����_�ȉ���������Ƃ��ē���̂ŏ���
				bd = bd.setScale(0, RoundingMode.DOWN);
				value = bd.toString();
			}

			String filtered = value.replaceAll(regex, "");
			if (!filtered.equals(value)) {
				// ���p�p���ȊO�̕������܂�ł��܂��B
				String errorMessage;
				if (allowsSymbols) {
					// {0}�s�� {1}�J������:{2}�ɔ��p�p���A�����ȊO�̕������܂܂�Ă��܂��B{3}
					errorMessage = "I00760";
				} else {
					// {0}�s�� {1}�J������:{2}�ɔ��p�p���ȊO�̕������܂܂�Ă��܂��B{3}
					errorMessage = "I00761";
				}
				Message msg = new Message(errorMessage, row, col, getColumnName(col), value);
				errorList.add(msg);
				return false;
			}
		}
		return true;
	}

	/**
	 * ���l�J�����ɓ��͂��ꂽ�R�[�h�l�������_�ȉ������������l�^������Ƃ��ĕԂ�
	 * 
	 * @param value
	 * @return ���l�������ꍇ�ɏ����_�ȉ���������������
	 */
	protected String numeric4IntegerString(String value) {

		BigDecimal bd = new BigDecimal(String.valueOf(Double.MIN_VALUE));
		String str = value;

		try {
			bd = new BigDecimal(value);

		} catch (NumberFormatException ne) {
			// ��O�������͐��l�ł͂Ȃ��̂ŉ������Ȃ�
		}

		if (bd.compareTo(new BigDecimal(String.valueOf(Double.MIN_VALUE))) != 0) {
			// �Z�������l�^�̏ꍇ�����_�ȉ���������Ƃ��ē���̂ŏ���O
			value = DecimalUtil.toBigDecimal(value).setScale(0, RoundingMode.DOWN).toPlainString();

			if (!value.equals("0") && "0".equals(String.valueOf(str.charAt(0)))) {
				int len = str.getBytes().length;
				value = StringUtil.fillLeft(value, len, '0');
			}
		}
		return value;
	}

	/**
	 * �Z���̒l�����t�Ƃ��ăp�[�X�ł��邩�m�F
	 * 
	 * @param row
	 * @param col
	 * @return true;���t
	 */
	protected boolean verifyCellIsDate(int row, ColumnType col) {
		if (col.getColumnType() != ExcelColumnType.DATE) {
			// ���t�ȊO�͑ΏۊO
			return true;
		}
		if (!col.isMandatory() && Util.isNullOrEmpty(sheet.getString(row, getColumnIndex(col)))) {
			// �K�{���ڈȊO�ŋ�̏ꍇ
			return true;
		}
		// {0}�s�� {1}�J������:{2}��{3}�ɕϊ��ł��܂���B{4}
		String invalidDate = "I00762";
		// ���t
		String dateItemName = "C00446";
		String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));
		Date date;
		try {
			// DECIMAL�l�Ŏ擾�����l��Parse�ł��邩�m�F
			date = DateUtil.toDateNE(sheet.getDecimal(row, getColumnIndex(col)));
			if (date == null) {
				date = sheet.getDate(row, getColumnIndex(col));
			}
		} catch (Exception ex) {
			// ���s�� String�ōĒ���
			try {
				date = DateUtil.toDateNE(value);
				if (date == null) {
					Message msg = new Message(invalidDate, row, getColumnIndex(col), getColumnName(col), dateItemName);
					errorList.add(msg);
					return false;
				}
			} catch (Exception e) {
				// �s:{0} ��:{1} ���ږ�:{2} �l:{3} �L���ȓ��t�ł͂���܂���B
				Message msg = new Message(invalidDate, row, getColumnIndex(col), getColumnName(col), dateItemName);
				errorList.add(msg);
				return false;
			}
		}
		return true;
	}

	/**
	 * �w��ʒu�Z���̓��t�l���擾����
	 * 
	 * @param row
	 * @param col
	 * @return �w��ʒu�Z���̓��t�l
	 */
	protected Date getDate(int row, ColumnType col) {
		Date date = null;
		try {
			// DECIMAL�l�Ŏ擾�����l��Parse�ł��邩�m�F
			date = DateUtil.toDateNE(sheet.getDecimal(row, getColumnIndex(col)));
			if (date == null) {
				date = sheet.getDate(row, getColumnIndex(col));
			}
		} catch (Exception ex) {
			// ���s�� String�ōĒ���
			try {
				String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));
				date = DateUtil.toDateNE(value);
			} catch (Exception e) {
				// �G���[�������͂Ȃ�
			}
		}
		return date;
	}

	/**
	 * �w��ʒu�Z����Decimal�l���擾����<br>
	 * ���l�Ƃ��ĕ]���ł��Ȃ��ꍇ�A�G���[��throw
	 * 
	 * @param row
	 * @param col
	 * @return �w��Z����Decimal�l
	 */
	protected BigDecimal getDecimal(int row, int col) {
		return DecimalUtil.toBigDecimal(getCellValue(row, col));
	}

	/**
	 * Excel���͒l���G���e�B�e�B�Ɋ��蓖�ĂĂ���
	 * 
	 * @param row
	 * @param col
	 * @param bean
	 */
	protected abstract void setColumnValue(int row, ColumnType col, EntityType bean);

	/**
	 * �e���v���[�g�̐������`�F�b�N
	 * 
	 * @return �e���v���[�g�̐�����
	 */
	protected boolean isRightTemplate() {
		try {
			int column = 0;
			while (true) {
				ColumnType defColumn = getOutputColumn(column);
				String fileCol = sheet.getString(0, column);
				if (Util.isNullOrEmpty(fileCol) && defColumn == null) {
					// ���傤�Ǔ����ʒu�ŗ񂪏I���̏ꍇ
					break;
				}
				if (!Util.equals(fileCol, getColumnName(defColumn))) {
					return false;
				}
				column++;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ��ʒu����o�͂���J�������擾
	 * 
	 * @param index
	 * @return �o�͂���J������`
	 */
	protected ColumnType getOutputColumn(int index) {
		int i = 0;
		for (ColumnType col : getAllColumns()) {
			if (!isOutput(col)) {
				continue;
			}
			if (i == index) {
				return col;
			}
			i++;
		}
		return null;
	}

	/**
	 * int�l����J�������擾
	 * 
	 * @param column
	 * @return �J����
	 */
	protected ColumnType getColumn(int column) {
		ColumnType[] defines = getAllColumns();
		return defines[column];
	}

	/**
	 * �J�����͏o�͑Ώۂ�<br>
	 * ��{�I�ɂ͒�`���ꂽ�J�����͂��ׂďo�͂���
	 * 
	 * @param col
	 * @return true:�o�͑Ώۂ̃J����
	 */
	protected boolean isOutput(@SuppressWarnings("unused") ColumnType col) {
		return true;
	}

	/**
	 * Enum�l����J�����ʒu�擾
	 * 
	 * @param col
	 * @return �J�����ʒu
	 */
	protected int getColumnIndex(ColumnType col) {
		int i = 0;
		for (ColumnType item : getAllColumns()) {
			if (col == item) {
				return i;
			}
			if (!isOutput(item)) {
				// �o�͂���Ȃ��J�������͈ʒu�����Z���Ȃ�
				continue;
			}
			i++;
		}
		return i;
	}

	/**
	 * ��̖��̂��擾����
	 * 
	 * @param col
	 * @return ��̖���
	 */
	protected String getColumnName(ColumnType col) {
		return col.getName();
	}

	/**
	 * @param row
	 * @param col
	 * @return �l
	 */
	protected Object getCellValue(int row, int col) {
		Row row_ = sheet.sheet.getRow(row);

		if (row_ == null) {
			return null;
		}

		Cell cell_ = row_.getCell(col);

		if (cell_ == null) {
			return null;
		}

		switch (cell_.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell_.getRichStringCellValue().getString();

			case Cell.CELL_TYPE_BOOLEAN:
				return cell_.getBooleanCellValue();

			case Cell.CELL_TYPE_NUMERIC:
				return cell_.getNumericCellValue();

			case Cell.CELL_TYPE_FORMULA:
				// �v�Z���̏ꍇ�A�L���b�V���l���g��

				switch (cell_.getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_STRING:
						return cell_.getRichStringCellValue().getString();

					case Cell.CELL_TYPE_BOOLEAN:
						return cell_.getBooleanCellValue();

					case Cell.CELL_TYPE_NUMERIC:
						return cell_.getNumericCellValue();

					case Cell.CELL_TYPE_ERROR:
						return cell_.getErrorCellValue();
					default:
						return null;
				}

			case Cell.CELL_TYPE_ERROR:
				return cell_.getErrorCellValue();

			case Cell.CELL_TYPE_BLANK:
			default:
				return null;
		}
	}

	/**
	 * 0���ߏ���
	 * @param str
	 * @return String
	 */

	protected String addZero(String str) {

		// �u�����N�͂��̂܂�
		if (Util.isNullOrEmpty(str)) {
			return str;
		}
		// ���l����Ȃ������炻�̂܂�
		try {
			new BigDecimal(str);

		} catch (NumberFormatException ne) {
			return str;
		}

		int z = 0;

		for (int j = 0; j < str.length(); j++) {
			if (!("0").equals(String.valueOf(str.charAt(j)))) {
				break;
			}
			z++;
		}
		str = DecimalUtil.toBigDecimal(str).setScale(0, RoundingMode.DOWN).toPlainString();
		if (str.equals("0")) {
			for (int j = 0; j < z - 1; j++) {
				str = "0" + str;
			}
		} else {
			for (int j = 0; j < z; j++) {
				str = "0" + str;
			}
		}
		return str;

	}
}
