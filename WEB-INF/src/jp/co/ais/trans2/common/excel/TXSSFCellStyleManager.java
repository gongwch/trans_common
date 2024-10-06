package jp.co.ais.trans2.common.excel;

import java.lang.reflect.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.*;

/**
 * xls�G�N�Z���̃Z���X�^�C�����Ǘ�����
 */
public class TXSSFCellStyleManager extends TExcelCellStyleManager {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param workBook
	 * @param languageCode
	 */
	public TXSSFCellStyleManager(Workbook workBook, String languageCode) {
		super(workBook, languageCode);
	}

	/**
	 * �X�^�C��������������
	 */
	@Override
	protected void initStyleByType() {

		this.headStyle.setFillForegroundColor((short) 22);

		DataFormat df = workBook.createDataFormat();
		this.dateCellStyle.setDataFormat(df.getFormat("m/d/yy"));
	}

	/**
	 * �u�k�����đS�̂�\������v�̐ݒ�
	 * 
	 * @param style �Z���X�^�C��
	 */
	@Override
	public void setShrinkToFit(CellStyle style) {
		try {
			Field field = XSSFCellStyle.class.getDeclaredField("_cellAlignment");
			field.setAccessible(true);
			XSSFCellAlignment e = (XSSFCellAlignment) field.get(style);
			e.getCTCellAlignment().setShrinkToFit(true);
		} catch (Exception e1) {
			// �G���[�Ȃ�
		}
	}
}
