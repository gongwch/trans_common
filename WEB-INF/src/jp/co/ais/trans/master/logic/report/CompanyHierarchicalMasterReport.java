package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.apache.poi.hssf.usermodel.*;

import jp.co.ais.trans.common.report.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ��ЊK�w�}�X�^ ���X�g���C�A�E�g
 */
public class CompanyHierarchicalMasterReport extends ExcelList {

	private static final String[] HEADER_IDS = { "C00596", "C00335", "C01826", "C01739", "C00722", "C01751", "C01752",
			"C01753", "C01754", "C01755", "C01756", "C01757", "C01758", "C01759" };

	/** �V�[�g */
	private HSSFSheet sheet;

	/**
	 * �R���X�g���N�^. �w�b�_���܂�Excel���X�g���쐬����
	 * 
	 * @param lang ����R�[�h
	 */
	public CompanyHierarchicalMasterReport(String lang) {
		super(lang);

		// ��ЊK�w�}�X�^ �{ �����e�i���X
		String sheetName = getWord("C02220") + getWord("C02419");

		// ��ЊK�w�}�X�^ �{ ���X�g
		String headerTitle = getWord("C02220") + getWord("C00545");

		sheet = addSheet(sheetName, headerTitle);

		// �w�b�_(�J������)�̐ݒ�
		addHeaderCells(createRow(sheet, 0), HEADER_IDS);
	}

	/**
	 * @see jp.co.ais.trans.common.report.ExcelReportLayout#getColumnWidths()
	 */
	@Override
	protected int[] getColumnWidths() {
		return new int[] { 3000, 3000, 5000, 2000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
	}

	/**
	 * �G�N�Z�����쐬����
	 * 
	 * @param dataList �Ώۃf�[�^���X�g
	 */
	public void createReport(List dataList) {
		// �s�I�u�W�F�N�g�̍쐬
		int nextRowIndex = sheet.getLastRowNum() + 1;

		Iterator ite = dataList.iterator();
		for (int i = 0; ite.hasNext(); i++) {
			HSSFRow newrow = sheet.createRow(nextRowIndex + i);

			CompanyHLvlList data = (CompanyHLvlList) ite.next();

			// ��ЃR�[�h
			addCell(newrow, (short) 0, Util.avoidNull(data.getKAI_CODE()));

			// ��Ж�
			String compName = Util.avoidNull(data.getKAI_NAME_S());

			// �g�D�R�[�h
			addCell(newrow, (short) 1, Util.avoidNull(data.getDPK_SSK()));

			// �Ǘ���ЃR�[�h
			addCell(newrow, (short) 2, Util.avoidNull(data.getDPK_DEP_CODE()));

			int lvlnum = data.getDPK_LVL();
			addCell(newrow, (short) 3, lvlnum); // lvl
			addCell(newrow, (short) 4, lvlnum == 0 ? compName : ""); // lvl0
			addCell(newrow, (short) 5, lvlnum == 1 ? compName : ""); // lvl1
			addCell(newrow, (short) 6, lvlnum == 2 ? compName : ""); // lvl2
			addCell(newrow, (short) 7, lvlnum == 3 ? compName : ""); // lvl3
			addCell(newrow, (short) 8, lvlnum == 4 ? compName : ""); // lvl4
			addCell(newrow, (short) 9, lvlnum == 5 ? compName : ""); // lvl5
			addCell(newrow, (short) 10, lvlnum == 6 ? compName : ""); // lvl6
			addCell(newrow, (short) 11, lvlnum == 7 ? compName : ""); // lvl7
			addCell(newrow, (short) 12, lvlnum == 8 ? compName : ""); // lvl8
			addCell(newrow, (short) 13, lvlnum == 9 ? compName : ""); // lvl9
		}
	}
}
