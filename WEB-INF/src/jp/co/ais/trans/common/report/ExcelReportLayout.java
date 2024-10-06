package jp.co.ais.trans.common.report;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;

/**
 * �G�N�Z�����[�x�[�X�N���X. �T�[�o�T�C�h�Ŏg�p.
 */
public class ExcelReportLayout extends LayoutBase {

	/** �G�N�Z���̃t�H���g�T�C�Y(�f�t�H���g) */
	protected static final short DEFAULT_FONT_SIZE = 11;

	/** ���񂹃X�^�C���^�C�v */
	public static final int TYPE_LEFT_STYLE = 1;

	/** �E�񂹃X�^�C���^�C�v */
	public static final int TYPE_RIGHT_STYLE = 2;

	/** �����X�^�C���^�C�v */
	public static final int TYPE_CENTER_STYLE = 3;

	/** ���t�p�X�^�C���^�C�v */
	public static final int TYPE_DATE_STYLE = 4;

	/** �Z���X�^�C��(����) */
	protected CellStyle leftStyle = null;

	/** �Z���X�^�C��(�E��) */
	protected CellStyle rightStyle = null;

	/** �Z���X�^�C��(����) */
	protected CellStyle centerStyle = null;

	/** �Z���X�^�C��(���t) */
	protected CellStyle dateCellStyle = null;

	/** �Z���X�^�C��(���l) */
	protected Map<String, CellStyle> decimalStyles = new TreeMap<String, CellStyle>();

	/** �t�H���g */
	private String font;

	/** WorkBook */
	protected HSSFWorkbook workBook;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ExcelReportLayout(String lang) {
		super(lang);

		this.font = ReportConfig.getExcelFontName(getLanguageCode());

		// �V�K���[�N�u�b�N���쐬����
		this.workBook = new HSSFWorkbook();

		this.initStyle();
	}

	/**
	 * ���[�^�C�g���̃X�^�C�����쐬����
	 */
	protected void initStyle() {

		// ���Ԃ̃X�^�C��
		this.centerStyle = workBook.createCellStyle();
		this.centerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.centerStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyle.setFont(this.getFontStyle());

		// �����̃X�^�C��
		this.leftStyle = workBook.createCellStyle();
		this.leftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.leftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.leftStyle.setFont(this.getFontStyle());

		// �E���̃X�^�C��
		this.rightStyle = workBook.createCellStyle();
		this.rightStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		this.rightStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.rightStyle.setFont(this.getFontStyle());

		// ���t�`���Z���쐬
		this.dateCellStyle = workBook.createCellStyle();
		this.dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		this.dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.dateCellStyle.setFont(this.getFontStyle());
	}

	/**
	 * Excel�p�t�H���g
	 * 
	 * @return �t�H���g��
	 */
	@Override
	protected String getFont() {
		return font;
	}

	/**
	 * Excel�p�f�t�H���g�t�H���g�X�^�C��
	 * 
	 * @return �t�H���g�X�^�C��
	 */
	protected HSSFFont getFontStyle() {
		return getFontStyle(DEFAULT_FONT_SIZE);
	}

	/**
	 * Excel�p�t�H���g�X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �t�H���g�X�^�C��
	 */
	protected HSSFFont getFontStyle(short fontSize) {
		HSSFFont hssfFont = this.workBook.createFont();
		hssfFont.setFontName(this.font);
		hssfFont.setFontHeightInPoints(fontSize);

		return hssfFont;
	}

	/**
	 * �v�����^�̐ݒ�
	 * 
	 * @param sheet �ΏۃV�[�g
	 * @param printerSize �v�����^�T�C�Y
	 * @param landscape ������y�[�W�ݒ� true:��
	 */
	protected void setPrinterSytle(HSSFSheet sheet, short printerSize, boolean landscape) {
		// �v�����^�ݒ�p��
		HSSFPrintSetup setup = sheet.getPrintSetup();

		// ��������ݒ肷��
		setup.setLandscape(landscape);

		// �T�C�Y��A4�ɐݒ肷��
		setup.setPaperSize(printerSize);

		// �y�[�W�����ݒ�
		setup.setLeftToRight(true);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����
	 * 
	 * @param sheetName �V�[�g��
	 * @return �V�[�g
	 */
	protected HSSFSheet addSheet(String sheetName) {
		HSSFSheet sheet = workBook.createSheet();
		int sheetIndex = workBook.getNumberOfSheets() - 1;

		// �V�[�g���ݒ�
		setSheetName(sheetIndex, sheetName);

		// �V�[�g�̕���ݒ肷��
		int[] columnWidths = getColumnWidths();
		if (columnWidths.length != 0) {
			setSheetWidth(sheet, columnWidths);
		}

		return sheet;
	}

	/**
	 * Book�ɃV�[�g��ǉ�����.<br>
	 * �w�肳�ꂽ�P���\���S�㕔�ɕ\��������B
	 * 
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @return �V�[�g
	 */
	protected HSSFSheet addSheet(String sheetName, String headerWord) {

		HSSFSheet sheet = addSheet(sheetName);

		// �w�b�_�[�ݒ�
		sheet.getHeader().setCenter(
			HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11) + headerWord);

		return sheet;
	}

	/**
	 * �J�������̎w��(Orverride�p)
	 * 
	 * @return �J���������X�g
	 */
	protected int[] getColumnWidths() {
		return new int[0];
	}

	/**
	 * �s�ǉ�.<br>
	 * �ő�s�̃`�F�b�N���s��.
	 * 
	 * @param sheet �ΏۃV�[�g
	 * @param rownum �Ώۍs
	 * @return �ǉ��s
	 */
	protected HSSFRow createRow(HSSFSheet sheet, int rownum) {

		// �s���`�F�b�N
		if (65536 < rownum) {
			throw new TRuntimeException("W00213");
		}

		return sheet.createRow(rownum);
	}

	/**
	 * �V�[�g���ݒ�<br>
	 * �����I��31�����Ő؂�
	 * 
	 * @param sheetIndex �ΏۃV�[�gIndex
	 * @param sheetName ����
	 */
	protected void setSheetName(int sheetIndex, String sheetName) {
		if (sheetName.length() > 31) {
			sheetName = sheetName.substring(0, 31);
		}

		// �V�[�g����ύX
		workBook.setSheetName(sheetIndex, sheetName);
	}

	/**
	 * �V�[�g����ݒ肷��
	 * 
	 * @param sheet �ΏۃV�[�g
	 * @param widths ���l���X�g
	 */
	protected void setSheetWidth(HSSFSheet sheet, int[] widths) {

		for (int i = 0; i < widths.length; i++) {
			// ��ЃR�[�h
			sheet.setColumnWidth(i, widths[i]);
		}
	}

	/**
	 * �Y���s�̎w��Z���ԍ��ɋ�l��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 */
	protected void addEmptyCell(HSSFRow hssfRow, short cellIndex) {

		this.addCell(hssfRow, cellIndex, "");
	}

	/**
	 * �Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, String value) {

		this.addCell(hssfRow, cellIndex, value, TYPE_LEFT_STYLE);
	}

	/**
	 * ���l�iDouble�j�^�F�Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, double value) {
		this.addCell(hssfRow, cellIndex, value, NumberFormatUtil.makeNumberFormat(0));
	}

	/**
	 * ���l�iDouble�j�^�F�Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 * @param digit �����_����
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, double value, int digit) {
		this.addCell(hssfRow, cellIndex, value, NumberFormatUtil.makeNumberFormat(digit));
	}

	/**
	 * ���l�idouble�j�^�F�Y���s�̎w��Z���ԍ��ɒl��ݒ肷��<br>
	 * �t�H�[�}�b�g�F�l�̃t�H�[�}�b�g��ݒ肷�� �t�H�[�}�b�g(format)�F�����Ƀt�H�[�}�b�g��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 * @param format �t�H�[�}�b�g
	 */
	protected void addCell(HSSFRow hssfRow, int cellIndex, double value, String format) {

		HSSFCell newcell = hssfRow.createCell(cellIndex);

		// �X�^�C�����쐬����

		if (!decimalStyles.containsKey(format)) {
			CellStyle decimalCellStyle = workBook.createCellStyle();
			decimalCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			decimalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			decimalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			decimalCellStyle.setFont(this.getFontStyle());

			// �t�H�[�}�b�g���쐬����
			HSSFDataFormat df = this.workBook.createDataFormat();
			decimalCellStyle.setDataFormat(df.getFormat(format));

			decimalStyles.put(format, decimalCellStyle);
		}

		newcell.setCellStyle(decimalStyles.get(format));

		newcell.setCellValue(value);
	}

	/**
	 * ���l�iint�j�^�F�Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addCell(HSSFRow hssfRow, short cellIndex, int value) {
		HSSFCell newcell = createCell(hssfRow, cellIndex, TYPE_RIGHT_STYLE);

		newcell.setCellValue(value);
	}

	/**
	 * �Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addRightCell(HSSFRow hssfRow, short cellIndex, String value) {
		this.addCell(hssfRow, cellIndex, value, TYPE_RIGHT_STYLE);
	}

	/**
	 * �Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addCenterCell(HSSFRow hssfRow, short cellIndex, String value) {
		this.addCell(hssfRow, cellIndex, value, TYPE_CENTER_STYLE);
	}

	/**
	 * �Z���l�ݒ�
	 * 
	 * @param hssfRow �Ώ�Row
	 * @param cellIndex ��Index
	 * @param value �l
	 * @param type �^�C�v
	 */
	private void addCell(HSSFRow hssfRow, short cellIndex, String value, int type) {
		HSSFCell newcell = createCell(hssfRow, cellIndex, type);

		// �l��Null�̏ꍇ�́A�󕶎�������
		if (value == null) {
			value = "";
		}

		// 1�Z���ł̍ő啶����(32767)�𒴂���ꍇ�A����؂�i1�o�C�g����j
		if (32767 < value.length()) {
			value = StringUtil.leftBX(value, 32767);
		}
		newcell.setCellValue(value);
	}

	/**
	 * �Y���s�̎w��Z���ԍ��ɒl��ݒ肷��
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l(���t)
	 */
	protected void addDateCell(HSSFRow hssfRow, short cellIndex, Date value) {
		// ������ɕϊ�
		HSSFCell newcell = createCell(hssfRow, cellIndex, TYPE_DATE_STYLE);
		newcell.setCellValue(value);
	}

	/**
	 * �w��^�C�v�̐ݒ���s���A�Z���𐶐�����.
	 * 
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param styleType �^�C�v
	 * @return �������ꂽ�Z��
	 */
	protected HSSFCell createCell(HSSFRow hssfRow, int cellIndex, int styleType) {
		HSSFCell newcell = hssfRow.createCell(cellIndex);

		// �X�^�C�����쐬����
		CellStyle style;
		switch (styleType) {

			case TYPE_LEFT_STYLE:
				style = this.leftStyle;
				break;
			case TYPE_RIGHT_STYLE:
				style = this.rightStyle;
				break;
			case TYPE_CENTER_STYLE:
				style = this.centerStyle;
				break;
			case TYPE_DATE_STYLE:
				style = this.dateCellStyle;
				break;
			default:
				style = getStyle(styleType);
				break;
		}

		newcell.setCellStyle(style);

		return newcell;
	}

	/**
	 * ���ʌp���p
	 * 
	 * @param styleType �^�C�v
	 * @return �X�^�C��
	 */
	protected CellStyle getStyle(@SuppressWarnings("unused") int styleType) {
		return leftStyle;
	}

	/**
	 * getBinary()���g���ĉ�����
	 * 
	 * @param wb
	 * @return �o�C�i��
	 * @throws IOException
	 * @deprecated getBinary()���g���ĉ�����
	 */
	protected byte[] getBinary(HSSFWorkbook wb) throws IOException {
		ByteArrayOutputStream fileOut = null;
		try {

			fileOut = new ByteArrayOutputStream();

			// ���[�N�u�b�N�̎擾
			Workbook newWb = TExcelOutput.getWorkbook(wb);

			// �쐬�������[�N�u�b�N��ۑ�����
			newWb.write(fileOut);

			fileOut.flush();

			return fileOut.toByteArray();

		} finally {

			ResourceUtil.closeOutputStream(fileOut);
		}
	}

	/**
	 * Excel�̃o�C�i���ϊ�
	 * 
	 * @return �o�C�i��
	 * @throws IOException
	 */
	public byte[] getBinary() throws IOException {
		ByteArrayOutputStream fileOut = null;
		try {

			fileOut = new ByteArrayOutputStream();

			// ���[�N�u�b�N�̎擾
			Workbook newWb = TExcelOutput.getWorkbook(workBook);

			// �쐬�������[�N�u�b�N��ۑ�����
			newWb.write(fileOut);

			fileOut.flush();

			return fileOut.toByteArray();
		} finally {
			ResourceUtil.closeOutputStream(fileOut);
		}
	}
}
