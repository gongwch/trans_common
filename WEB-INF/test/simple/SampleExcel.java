package simple;

import java.awt.Color;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.excel.*;

/**
 * �T���v��
 * 
 * @author AIS
 */
public class SampleExcel extends TExcel {

	/** USD���� */
	protected CellStyle usdStyle;

	/** JPY���� */
	protected CellStyle jpyStyle;

	/**
	 * �R���X�g���N�^
	 * 
	 * @throws TException
	 */
	public SampleExcel() throws TException {
		super(new File("temp.xls"));
		// super("ja");
	}

	/**
	 * �e���v���[�g�t�@�C����ǂݍ���
	 * 
	 * @param cls Class
	 * @param path �t�@�C���p�X
	 * @throws TException
	 */
	@Override
	protected void readTempFile(Class cls, String path) throws TException {
		super.readTempFile(this.getClass(), path);
	}

	/**
	 * �G�N�Z���œn��
	 * 
	 * @param list �ꗗ�f�[�^�̔z��
	 * @exception TException
	 * @return byte Excel
	 */
	public byte[] getExcel(List<List<Object>> list) throws TException {
		try {
			for (int i = 0; i < 3; i++) {
				createReport(list, i);
			}

			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����C�A�E�g��` �e�J�����̃f�[�^���Z�b�g
	 * 
	 * @param list �ꗗ�f�[�^�̔z��
	 * @param index �V�[�g�ԍ�
	 */
	public void createReport(List<List<Object>> list, int index) {

		// �V�[�g�ǉ�(�e���v���[�g�V�[�g����R�s�[)
		TExcelSheet sheet = null;
		if (index == 0) {
			sheet = getSheet(0);
			this.setSheetName(0, "�T���v��" + index);
		} else {
			sheet = cloneSheet(0, "�T���v��" + index);// �T���v��
		}
		// TExcelSheet sheet = addSheet("�T���v��" + index, "");// �T���v��

		sheet.setZoom(85, 100);

		// �y�[�W�ݒ�F�]��
		sheet.setMargin(SwingConstants.TOP, convertCmToInch(1.9d));
		sheet.setMargin(SwingConstants.RIGHT, convertCmToInch(1.9d));
		sheet.setMargin(SwingConstants.LEFT, convertCmToInch(1.9d));
		sheet.setMargin(SwingConstants.BOTTOM, convertCmToInch(0.5d));

		// �y�[�W�ݒ�F�y�[�W�����F�����Ƀ`�F�b�N
		sheet.setHorizontallyCenterPrint();

		// �X�^�C���}�l�[�W��
		TExcelCellStyleManager csm = sheet.book.getCellStyleManager();

		int MAX_COL = 15;

		// �񕝐ݒ�(1�����Ŗ�256�P��)
		for (int i = 0; i < MAX_COL; i++) {
			sheet.setColumnWidth(i, 3350);
		}

		// �^�C�g��
		sheet.setRowNo(0);
		TExcelRow newRow = sheet.addRow();
		newRow.addCell("�e�D���ו\", csm.getCenterStyleBoldNoneBorder());
		for (int i = 0; i < MAX_COL; i++) {
			newRow.addEmptyCellWithStyle(csm.getCenterStyleBoldNoneBorder());
		}

		// �Ώ۔N��
		Date from = DateUtil.getDate(2013, 4, 1);
		Date to = DateUtil.getDate(2014, 3, 31);
		newRow = sheet.addRow();
		String titleYm = DateUtil.toJpYMString(from) + " �` " + DateUtil.toJpYMString(to);
		newRow.addCell(titleYm, csm.getCenterStyleNoneBorder());
		for (int i = 0; i < MAX_COL; i++) {
			newRow.addEmptyCellWithStyle(csm.getCenterStyleNoneBorder());
		}

		newRow = sheet.addRow();
		newRow.addCell("��Џ��", csm.getLeftStyleNoneBorder());

		// �G�N�Z���̗�Œ�@�\�ɂ��āA
		// ���L�̓�֐��Ŏ����ł��܂��B�i�W���@�\�j
		// �@������̌Œ�s���ݒ肷��(���C���f�b�N�X��0�`)
		{
			// �p�����[�^�[:
			// sheetIndex �V�[�gIndex
			// startColumn �J�n��
			// endColumn �I����
			// startRow �J�n�s
			// endRow �I���s
			setRepeatingRowsAndColumns(0, 0, 2, 0, 3);
		}

		// �A�����ڂ̌Œ�s���ݒ肷��
		{
			// �p�����[�^�[:
			// cols ���
			// row �s��
			sheet.sheet.createFreezePane(3, 4);
		}

		// ����͈͐ݒ�T���v��
		{
			// �V�[�g�ԍ�=index�A��J�n=0�A��I��=2�A�s�J�n=0�A�s�I��=10*index�̏�
			setPrintArea(index, 0, 2, 0, 11 + 10 * index);
		}

		// �s���y�[�W�w��
		{
			// row index 0�`
			sheet.sheet.setRowBreak(6);
		}

		// ����ݒ�(�w�b�_�E�t�b�^ XLS�o�[�W����)
		{
			sheet.sheet.getHeader().setCenter(HeaderFooter.date());
			sheet.sheet.getHeader().setRight(HeaderFooter.page() + "/" + HeaderFooter.numPages());
			sheet.sheet.getFooter().setLeft(HeaderFooter.font("�l�r �S�V�b�N", "Italic") + "AIS Excel Sample");
		}

		// �w�b�_�A�t�b�^�̗]���ݒ�
		{
			sheet.sheet.getPrintSetup().setHeaderMargin(convertCmToInch(0.9d));
			sheet.sheet.getPrintSetup().setFooterMargin(convertCmToInch(0.8d));
		}

		// CTSheetView view = ((XSSFSheet) sheet.sheet).getCTWorksheet().getSheetViews().getSheetViewArray(0);
		// view.setView(Enum.forString("pageBreakPreview"));

		// �w�b�_�[�^�C�g��
		newRow = sheet.addRow();

		// �J�����ݒ�
		newRow.addCell(getWord("�Œ��@"), csm.getHeadStyle());
		newRow.addCell(getWord("�Œ��A"), csm.getHeadStyle());
		newRow.addCell(getWord("�Œ��B"), csm.getHeadStyle());
		for (int i = 3; i < MAX_COL; i++) {
			newRow.addCell(getWord("�J����_" + (i + 1)), csm.getHeadStyle());
		}

		String usdFormat = "$#,##0.00;-$#,##0.00";
		String jpyFormat = "\"\\\"#,##0;\"\\-\"#,##0";

		CellStyle titleTextStyle = null;
		CellStyle titleUSDStyle = null;
		CellStyle titleJPYStyle = null;

		if (titleTextStyle == null) {
			titleTextStyle = createCellStyle(Color.red, Color.yellow, null);
		}
		if (titleUSDStyle == null) {
			titleUSDStyle = createCellStyle(Color.blue, Color.yellow, usdFormat);
		}
		if (titleJPYStyle == null) {
			titleJPYStyle = createCellStyle(Color.green, Color.black, jpyFormat);
		}

		// ���ו`��
		int i = 0;
		for (List<Object> data : list) {
			newRow = sheet.addRow();

			CellStyle f = i % 2 == 0 ? titleUSDStyle : titleJPYStyle;

			for (Object obj : data) {

				if (obj instanceof Date) {
					newRow.addCell((Date) obj);
				} else if (obj instanceof BigDecimal) {
					// �w�菑���Œl��ݒ肷��
					newRow.addNumberCell(((BigDecimal) obj).multiply(new BigDecimal(index + 1)), f);
				} else {
					newRow.addCell(Util.avoidNull(obj));
				}
			}

			i++;
		}
	}

	/**
	 * �Z���X�^�C���̍쐬
	 * 
	 * @param foreColor
	 * @param fillColor
	 * @param format ����(���l�̏ꍇ)
	 * @return �Z���X�^�C��
	 */
	protected CellStyle createCellStyle(Color foreColor, Color fillColor, String format) {
		CellStyle style = workBook.createCellStyle();
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Font font = cellStyleManager.getFontStyle((short) 9);
		if (foreColor != null) {
			setForeColor(font, foreColor);
		}
		style.setFont(font);

		if (fillColor != null) {
			setFillColor(style, fillColor);
		}

		if (!Util.isNullOrEmpty(format)) {
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			DataFormat df = cellStyleManager.createDataFormat();
			style.setDataFormat(df.getFormat(format));
		}

		return style;
	}

	/**
	 * �����F�ݒ�
	 * 
	 * @param font
	 * @param color
	 */
	protected void setForeColor(Font font, Color color) {
		TExcelOutput.setForeColor(workBook, font, color);
	}

	/**
	 * �w�i�F�ݒ�
	 * 
	 * @param style
	 * @param color
	 */
	protected void setFillColor(CellStyle style, Color color) {
		TExcelOutput.setFillColor(workBook, style, color);
	}

}