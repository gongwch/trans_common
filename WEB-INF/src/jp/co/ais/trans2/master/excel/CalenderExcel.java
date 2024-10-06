package jp.co.ais.trans2.master.excel;

import java.awt.Color;
import java.util.List;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.calendar.*;

/**
 * ��s�x���J�����_�[�}�X�^�G�N�Z��
 * 
 * @author wangjing
 */
public class CalenderExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public CalenderExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param list
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<CalendarEntity> list) throws TException {

		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param list
	 */
	public void createReport(List<CalendarEntity> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C00085") + getWord("C00500"));
		// �J�����ݒ�
		sheet.addColumn(getWord("C00446"), 3000); // ���t
		sheet.addColumn(getWord("�j��"), 2000); // �j��
		sheet.addColumn(getWord("C01119") + getWord("C03282"), 4000); // �Ј��x���Ώۓ�
		sheet.addColumn(getWord("C00125"), 4000); // ��s�x�Ɠ�
		sheet.addColumn(getWord("C10330") + getWord("C03282"), 4000); // �Վ��x���Ώۓ�

		String formatLang = "ddd";
		if (lang.equals("ja")) {
			formatLang = "aaa";
		}

		// �j��
		CellStyle style = workBook.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		Font font = workBook.createFont();
		font.setFontName("Meiryo UI");
		style.setFont(font);

		// �x���t���O
		CellStyle holStyle = workBook.createCellStyle();
		holStyle.setBorderTop(CellStyle.BORDER_THIN);
		holStyle.setBorderBottom(CellStyle.BORDER_THIN);
		holStyle.setBorderLeft(CellStyle.BORDER_THIN);
		holStyle.setBorderRight(CellStyle.BORDER_THIN);
		holStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font holFont = workBook.createFont();
		holFont.setFontName("Meiryo UI");
		TExcelOutput.setForeColor(workBook, holFont, Color.RED);
		holFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		holStyle.setFont(holFont);

		// ���ו`��
		for (int i = 0; i < list.size(); i++) {

			CalendarEntity bean = list.get(i);

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCalDate());
			newRow.addCell(""); // �j���͊֐����ߍ���

			StringBuilder sb = new StringBuilder();
			sb.append("TEXT(");
			sb.append("A" + (i + 2));
			sb.append(", \"" + formatLang + "\"");
			sb.append(")");
			setCellFormula(sheet, i + 1, 1, sb.toString(), style);
			newRow.addCell(getSihaKbn(Integer.parseInt(bean.getCalSha())), SwingConstants.CENTER);
			newRow.addCell(getBankKbn(Integer.parseInt(bean.getCalBank())), holStyle);
			newRow.addCell(getSihaKbn(Integer.parseInt(bean.getCalRinji())), SwingConstants.CENTER);
		}
	}

	/**
	 * �Z���֐��ݒ�
	 * 
	 * @param sheet
	 * @param row
	 * @param col
	 * @param value
	 * @param style
	 */
	protected void setCellFormula(TExcelSheet sheet, int row, int col, String value, CellStyle style) {

		TExcelRow r = sheet.getRow(row);
		Cell cell = r.row.getCell(col);
		if (cell == null) {
			cell = r.row.createCell(col);
		}

		if (value != null) {
			cell.setCellFormula(value);
		}

		if (style != null) {
			cell.setCellStyle(style);
		}
	}

	/**
	 * @param bankKbn
	 * @return 1:�x�A0:�u�����N
	 */
	public String getSihaKbn(int bankKbn) {

		String kbn = "";

		if (bankKbn == 1) {
			kbn = getWord("C00347"); // �Ώ�
		}
		return kbn;
	}

	/**
	 * @param bankKbn
	 * @return 1:�x�A0:�u�����N
	 */
	public String getBankKbn(int bankKbn) {

		String kbn = "";

		if (bankKbn == 1) {
			kbn = "�x";
		}
		return kbn;
	}
}
