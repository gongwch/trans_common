package jp.co.ais.trans.common.report;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.util.*;

/**
 * �G�N�Z�����X�g�o�͗p���C�A�E�g��ʃN���X
 */
public class ExcelList extends ExcelReportLayout {

	/** �W���w�b�_�X�^�C�� */
	public static final int TYPE_HEADER_STYLE = 11;

	/** ���񂹃w�b�_�X�^�C�� */
	public static final int TYPE_LEFT_HEADER_STYLE = 12;

	/** ���[�^�C�g���̃t�H�[�}�b�g */
	protected HSSFCellStyle headStyle;

	/** ���[�^�C�g��(����)�̃t�H�[�}�b�g */
	protected HSSFCellStyle headLeftStyle;

	/** �J�����J�n */
	protected int columnBegin = -1;

	/** �J�����I�� */
	protected int columnEnd = -1;

	/** �s�J�n */
	protected int rowBegin = 0;

	/** �s�I�� */
	protected int rowEnd = 0;

	/**
	 * �R���X�g���N�^
	 *
	 * @param lang ����R�[�h
	 */
	public ExcelList(String lang) {
		super(lang);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����
	 *
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @return �V�[�g
	 */
	@Override
	protected HSSFSheet addSheet(String sheetName, String headerWord) {
		return addSheet(sheetName, headerWord, true);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����.<br>
	 * 1�s�ڂɃJ�����^�C�g����}�����Ȃ�
	 *
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @return �V�[�g
	 */
	protected HSSFSheet addSheetNonTitle(String sheetName, String headerWord) {
		return addSheet(sheetName, headerWord, false);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����
	 *
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @param hasColoumnTitle 1�s�ڃJ�����^�C�g������(true:�t�^)
	 * @return �V�[�g
	 */
	protected HSSFSheet addSheet(String sheetName, String headerWord, boolean hasColoumnTitle) {

		// �G�N�Z���֑������Ή� �R����(:)�A�~(\)�A�X��(/)�A�^�╄(?)�A�A�X�^(*)�A���E����([)(])
		String name = sheetName.replace(":", "").replace("\\", "").replace("/", "").replace("?", "").replace("*", "")
			.replace("[", "").replace("]", "");

		HSSFSheet sheet = super.addSheet(StringUtil.leftBX(name, 31), headerWord);

		// �v�����^�ݒ�
		setPrinterSytle(sheet);

		if (hasColoumnTitle) {
			// �s�̃^�C�g��(��s�ڂ̃J��������)�w��
			int sheetIndex = workBook.getNumberOfSheets() - 1;
			workBook.setRepeatingRowsAndColumns(sheetIndex, columnBegin, columnEnd, rowBegin, rowEnd);
		}

		return sheet;
	}

	/**
	 * �^�C�g���̐ݒ肷��B
	 *
	 * @param cBegin �J�����J�n
	 * @param cEnd �J�����I��
	 * @param rBegin �s�J�n
	 * @param rEnd �s�I��
	 */
	protected void setRowAndColumn(int cBegin, int cEnd, int rBegin, int rEnd) {
		columnBegin = cBegin;
		columnEnd = cEnd;
		rowBegin = rBegin;
		rowEnd = rEnd;
	}

	/**
	 * Excel�p�v�����^�̐ݒ�(�f�t�H���g:A4��)
	 *
	 * @param sheet �ΏۃV�[�g
	 */
	protected void setPrinterSytle(HSSFSheet sheet) {
		super.setPrinterSytle(sheet, PrintSetup.A4_PAPERSIZE, true);
	}

	/**
	 * ���[�^�C�g���̃X�^�C�����쐬����
	 */
	@Override
	protected void initStyle() {
		super.initStyle();

		// �w�b�_�[�̃X�^�C��
		this.headStyle = workBook.createCellStyle();
		this.headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		this.headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.headStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headStyle.setFont(this.getFontStyle());

		// ���񂹃w�b�_�[�̃X�^�C��
		this.headLeftStyle = workBook.createCellStyle();
		this.headLeftStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		this.headLeftStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headLeftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.headLeftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headLeftStyle.setFont(this.getFontStyle());
	}

	/**
	 * �X�^�C����Ԃ�.
	 *
	 * @param styleType �^�C�v
	 * @return �X�^�C��
	 */
	@Override
	protected CellStyle getStyle(int styleType) {
		switch (styleType) {
			case TYPE_HEADER_STYLE:
				return headStyle;

			case TYPE_LEFT_HEADER_STYLE:
				return headLeftStyle;

			default:
				return super.getStyle(styleType);
		}
	}

	/**
	 * �Y���s�̎w��Z���ԍ��Ƀw�b�_��ݒ肷��
	 *
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addHeaderCell(HSSFRow hssfRow, short cellIndex, String value) {
		HSSFCell newcell = createCell(hssfRow, cellIndex, TYPE_HEADER_STYLE);

		newcell.setCellValue(value);
	}

	/**
	 * �w�肳�ꂽ�P��ID���X�g��ϊ����ăw�b�_�Z����ݒ肷��.
	 *
	 * @param hssfRow �Y���s
	 * @param wordIDs �P��ID���X�g
	 */
	protected void addHeaderCells(HSSFRow hssfRow, String[] wordIDs) {

		// �w�b�_�����̎擾
		String[] headers = getWordList(wordIDs);

		for (short index = 0; index < headers.length; index++) {

			// �J�������ݒ�
			addHeaderCell(hssfRow, index, headers[index]);
		}
	}

	/**
	 * �Y���s�̎w��Z���ԍ��Ƀw�b�_��ݒ肷��
	 *
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addHeaderCell(HSSFRow hssfRow, int cellIndex, String value) {
		addHeaderCell(hssfRow, (short) cellIndex, value);
	}

	/**
	 * �Y���s�̎w��Z���ԍ��ɍ��񂹃w�b�_��ݒ肷��
	 *
	 * @param hssfRow �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param value �l
	 */
	protected void addLeftHeaderCell(HSSFRow hssfRow, int cellIndex, String value) {
		HSSFCell newcell = createCell(hssfRow, (short) cellIndex, TYPE_LEFT_HEADER_STYLE);

		newcell.setCellValue(value);
	}
}
