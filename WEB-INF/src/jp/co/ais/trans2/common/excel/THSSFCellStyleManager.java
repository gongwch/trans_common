package jp.co.ais.trans2.common.excel;

import java.lang.reflect.*;

import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;

/**
 * xlsx�G�N�Z���̃Z���X�^�C�����Ǘ�����
 */
public class THSSFCellStyleManager extends TExcelCellStyleManager {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param workBook
	 * @param languageCode
	 */
	public THSSFCellStyleManager(Workbook workBook, String languageCode) {
		super(workBook, languageCode);
	}

	/**
	 * �X�^�C��������������
	 */
	@Override
	protected void initStyleByType() {
		this.headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		this.dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
	}

	/**
	 * �u�k�����đS�̂�\������v�̐ݒ�
	 * 
	 * @param style �Z���X�^�C��
	 */
	@Override
	public void setShrinkToFit(CellStyle style) {
		try {
			Field field = HSSFCellStyle.class.getDeclaredField("_format");
			field.setAccessible(true);
			ExtendedFormatRecord e = (ExtendedFormatRecord) field.get(style);
			e.setShrinkToFit(true);
		} catch (Exception e1) {
			// �G���[�Ȃ�
		}
	}
}
