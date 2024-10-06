package jp.co.ais.trans.master.common;

import java.util.*;

import org.apache.poi.hssf.usermodel.*;

import jp.co.ais.trans.common.report.*;

/**
 * �O���o��
 */
public class OutputUtil extends ExcelList {

	/** �G�N�Z�����[�N�u�b�N */
	private ReportExcelDefine reportDefine;

	/** �V�[�g */
	private HSSFSheet sheet;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param reportDefine
	 * @param langCode ����R�[�h
	 */
	public OutputUtil(ReportExcelDefine reportDefine, String langCode) {
		super(langCode);

		this.reportDefine = reportDefine;

		// �V�[�g�� (����+�����e�i���X)
		String sheetNameID = this.reportDefine.getSheetName();
		String sheetName = getWord(sheetNameID);

		// �w�b�_�[����
		String headerTitle = getWord(sheetNameID);

		// �V�K���[�N�V�[�g���쐬����
		sheet = addSheet(sheetName, headerTitle);

		// �w�b�_(�J������)�̐ݒ�
		HSSFRow newrow = sheet.createRow(0);
		String[] headerTexts = this.reportDefine.getHeaderTexts(); // �J�������e�L�X�g
		short[] columnWidths = this.reportDefine.getColumnWidths(); // �e�J������
		short index = 0;

		// Column width �̐ݒ�
		for (short i = 0; i < headerTexts.length; i++) {
			short columnWidth = 0;

			if (columnWidths != null && columnWidths.length > i) {
				columnWidth = columnWidths[i];
			}
			if (columnWidth > 0) {
				String headerText = getWord(headerTexts[i]);

				// �J�������ݒ�
				addHeaderCell(newrow, index, headerText);

				// �e�J��������ݒ�
				sheet.setColumnWidth(index, (short) ((columnWidths[i] + 7) * 256));
				index++;
			}
		}
	}

	/**
	 * �G�N�Z������
	 * 
	 * @param entities
	 */
	public void createExcel(List entities) {
		int nextRowIndex = sheet.getLastRowNum() + 1;
		short[] columnWidths = reportDefine.getColumnWidths();

		for (int i = 0; i < entities.size(); i++) {
			// �s�I�u�W�F�N�g�̍쐬
			HSSFRow newrow = sheet.createRow(nextRowIndex + i);

			// �G���e�B�e�B �� List
			Object entity = entities.get(i);
			List list = reportDefine.convertDataToList(entity, getLanguageCode());
			short index = 0;

			for (short j = 0; j < list.size(); j++) {
				Object obj = list.get(j);
				short columnWidth = 0;

				if (columnWidths != null && columnWidths.length > j) {
					columnWidth = columnWidths[j];
				}

				if (columnWidth > 0) {

					if (obj == null) {
						addEmptyCell(newrow, index);
					} else if (obj instanceof FormatDecimal) {
						FormatDecimal fd = (FormatDecimal) obj;
						addCell(newrow, index, fd.getValue(), fd.getFormat());
					} else if (obj instanceof Number) {
						Number num = (Number) obj;
						addCell(newrow, index, num.doubleValue());
					} else if (obj instanceof Date) {
						Date date = (Date) obj;
						addDateCell(newrow, index, date);
					} else if (obj instanceof AlignString) {
						AlignString str = (AlignString) obj;

						switch (str.getAlign()) {
							case AlignString.RIGHT:
								addRightCell(newrow, index, str.getString());
								break;
							case AlignString.CENTER:
								addCenterCell(newrow, index, str.getString());
								break;
							default:
								addCell(newrow, index, str.getString());
								break;
						}
					} else {
						addCell(newrow, index, String.valueOf(obj));
					}
					index++;
				}
			}
		}
	}

	/**
	 * �V�[�g��Ԃ�
	 * 
	 * @return �V�[�g
	 */
	public HSSFSheet getSheet() {
		return this.sheet;
	}
}
