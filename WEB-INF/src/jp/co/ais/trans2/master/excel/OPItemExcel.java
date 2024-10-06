package jp.co.ais.trans2.master.excel;

import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * SA�A�C�e���ꗗ�G�N�Z��
 */
public class OPItemExcel extends TExcel {

	/** ��Џ�� */
	protected Company company = null;

	/** CODE */
	protected static String CODE = "";

	/** ABBREVIATION */
	protected static String ABBR = "";

	/** TAX */
	protected static String TAX = "";

	/** CONTRA */
	protected static String CONTRA = "";

	/** ESTIMATE */
	protected static String ESTIMATE = "";

	/** BLANK */
	protected static String BLANK = "";

	/** BRACKET_OPEN */
	protected static String BRACKET_OPEN = "";

	/** BRACKET_CLOSE */
	protected static String BRACKET_CLOSE = "";

	/** DEFERRAL */
	protected static String DEFERRAL = "";

	/** OWNR_SHIP_CODE ���p���� */
	public static boolean isUseOwnrShip = ServerConfig.isFlagOn("trans.op.use.item.ownr.ship");

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param company
	 */
	public OPItemExcel(String lang, Company company) {
		super(lang);
		this.company = company;
		initWords();

	}

	/**
	 * Initial value of label
	 */
	protected void initWords() {
		CODE = getWord("COP032");
		ABBR = getWord("COP196");
		TAX = getWord("CTC0036");
		CONTRA = getWord("COP183");
		ESTIMATE = getWord("COP818");
		BLANK = " ";
		BRACKET_OPEN = "(";
		BRACKET_CLOSE = ")";
		DEFERRAL = getWord("COP184");
	}

	/**
	 * SA�A�C�e���ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param ItemList �Ȗڈꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<OPItem> ItemList) throws TException {

		try {
			createReport(ItemList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param itemList
	 */
	public void createReport(List<OPItem> itemList) {
		AccountConfig ac = company.getAccountConfig();

		boolean isUseDetailItem = ac.isUseDetailItem();

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C00173") + " " + getWord("C00500"));

		sheet.setZoom(85, 100);

		// �A�����ڂ̌Œ�s���ݒ肷��
		{
			// �p�����[�^�[:
			// cols ���
			// row �s��
			sheet.sheet.createFreezePane(2, 1);
		}

		TExcelCellStyleManager cm = getCellStyleManager();
		cm.getHeadStyle().setAlignment(CellStyle.ALIGN_LEFT);

		// �J�����ݒ�
		sheet.addColumn(getWord("COP032"), 2400); // CODE

		sheet.addColumn(getWord("C11496"), 3200); // �A�C�e������
		sheet.addColumn(getWord("COP171"), 2800); // ITEM ABBREVIATION
		sheet.addColumn(getWord("COP172"), 2800); // ITEM RETRIEVAL NAME
		sheet.addColumn(getWord("COP1390"), 2800); // INVOICE NAME
		sheet.addColumn(getWord("C01367"), 2400); // CONTRACT TYPE
		sheet.addColumn(getWord("COP173"), 2400); // PROCESS TYPE
		sheet.addColumn(getWord("COP174"), 2400); // DR/CR
		sheet.addColumn(getWord("COP175"), 2400); // ITEM CONTROL TYPE
		sheet.addColumn(getWord("COP176"), 2800); // SUB ITEM CONTROL TYPE
		sheet.addColumn(getWord("COP1389"), 2400); // ���v�F���
		if (isUseOwnrShip) {
			sheet.addColumn(getWord("COP009"), 2400); // // OWNER SHIP
		}
		sheet.addColumn(getWord("COP177"), 2400); // ADDRESS COMMISSION
		sheet.addColumn(getWord("C01351"), 2400); // BROKERAGE
		sheet.addColumn(getWord("C00932"), 2400); // REMARKS

		int w1 = 2400;
		int w2 = 3800;
		int w3 = 1400;
		int w4 = 2800;

		String itemName = ac.getItemName();
		String subItemName = ac.getSubItemName();
		String detailItemName = ac.getDetailItemName();

		{

			// �Ȗ�
			sheet.addColumn(itemName + BLANK + CODE, w1);
			sheet.addColumn(itemName + BLANK + ABBR, w2);
			sheet.addColumn(subItemName + BLANK + CODE, w1);
			sheet.addColumn(subItemName + BLANK + ABBR, w2);
			if (isUseDetailItem) {
				sheet.addColumn(detailItemName + BLANK + CODE, w1);
				sheet.addColumn(detailItemName + BLANK + ABBR, w2);
			}
			sheet.addColumn(TAX + BLANK + CODE + BRACKET_OPEN + itemName + BRACKET_CLOSE, w3);
			sheet.addColumn(TAX + BLANK + ABBR + BRACKET_OPEN + itemName + BRACKET_CLOSE, w4);

			// ����
			sheet.addColumn(CONTRA + BLANK + itemName + BLANK + CODE, w1);
			sheet.addColumn(CONTRA + BLANK + itemName + BLANK + ABBR, w2);
			sheet.addColumn(CONTRA + BLANK + subItemName + BLANK + CODE, w1);
			sheet.addColumn(CONTRA + BLANK + subItemName + BLANK + ABBR, w2);
			if (isUseDetailItem) {
				sheet.addColumn(CONTRA + BLANK + detailItemName + BLANK + CODE, w1);
				sheet.addColumn(CONTRA + BLANK + detailItemName + BLANK + ABBR, w2);
			}
			sheet.addColumn(TAX + BLANK + CODE + BRACKET_OPEN + CONTRA + BLANK + itemName + BRACKET_CLOSE, w3);
			sheet.addColumn(TAX + BLANK + ABBR + BRACKET_OPEN + CONTRA + BLANK + itemName + BRACKET_CLOSE, w4);
		}

		{

			// �J��
			sheet.addColumn(DEFERRAL + BLANK + itemName + BLANK + CODE, w1);
			sheet.addColumn(DEFERRAL + BLANK + itemName + BLANK + ABBR, w2);
			sheet.addColumn(DEFERRAL + BLANK + subItemName + BLANK + CODE, w1);
			sheet.addColumn(DEFERRAL + BLANK + subItemName + BLANK + ABBR, w2);
			if (isUseDetailItem) {
				sheet.addColumn(DEFERRAL + BLANK + detailItemName + BLANK + CODE, w1);
				sheet.addColumn(DEFERRAL + BLANK + detailItemName + BLANK + ABBR, w2);
			}
			sheet.addColumn(TAX + BLANK + CODE + BRACKET_OPEN + DEFERRAL + BLANK + itemName + BRACKET_CLOSE, w3);
			sheet.addColumn(TAX + BLANK + ABBR + BRACKET_OPEN + DEFERRAL + BLANK + itemName + BRACKET_CLOSE, w4);

			// �J������
			sheet.addColumn(CONTRA + BLANK + DEFERRAL + BLANK + itemName + BLANK + CODE, w1);
			sheet.addColumn(CONTRA + BLANK + DEFERRAL + BLANK + itemName + BLANK + ABBR, w2);
			sheet.addColumn(CONTRA + BLANK + DEFERRAL + BLANK + subItemName + BLANK + CODE, w1);
			sheet.addColumn(CONTRA + BLANK + DEFERRAL + BLANK + subItemName + BLANK + ABBR, w2);
			if (isUseDetailItem) {
				sheet.addColumn(CONTRA + BLANK + DEFERRAL + BLANK + detailItemName + BLANK + CODE, w1);
				sheet.addColumn(CONTRA + BLANK + DEFERRAL + BLANK + detailItemName + BLANK + ABBR, w2);
			}
			sheet.addColumn(TAX + BLANK + CODE + BRACKET_OPEN + CONTRA + BLANK + DEFERRAL + BRACKET_CLOSE, w3);
			sheet.addColumn(TAX + BLANK + ABBR + BLANK + BRACKET_OPEN + CONTRA + BLANK + DEFERRAL + BRACKET_CLOSE, w4);
		}

		{

			// �T�Z
			sheet.addColumn(ESTIMATE + BLANK + itemName + BLANK + CODE, w1);
			sheet.addColumn(ESTIMATE + BLANK + itemName + BLANK + ABBR, w2);
			sheet.addColumn(ESTIMATE + BLANK + subItemName + BLANK + CODE, w1);
			sheet.addColumn(ESTIMATE + BLANK + subItemName + BLANK + ABBR, w2);
			if (isUseDetailItem) {
				sheet.addColumn(ESTIMATE + BLANK + detailItemName + BLANK + CODE, w1);
				sheet.addColumn(ESTIMATE + BLANK + detailItemName + BLANK + ABBR, w2);
			}
			sheet.addColumn(TAX + BLANK + CODE + BRACKET_OPEN + ESTIMATE + BLANK + itemName + BRACKET_CLOSE, w3);
			sheet.addColumn(TAX + BLANK + ABBR + BRACKET_OPEN + ESTIMATE + BLANK + itemName + BRACKET_CLOSE, w4);

			// �T�Z����
			sheet.addColumn(CONTRA + BLANK + ESTIMATE + BLANK + itemName + BLANK + CODE, w1);
			sheet.addColumn(CONTRA + BLANK + ESTIMATE + BLANK + itemName + BLANK + ABBR, w2);
			sheet.addColumn(CONTRA + BLANK + ESTIMATE + BLANK + subItemName + BLANK + CODE, w1);
			sheet.addColumn(CONTRA + BLANK + ESTIMATE + BLANK + subItemName + BLANK + ABBR, w2);
			if (isUseDetailItem) {
				sheet.addColumn(CONTRA + BLANK + ESTIMATE + BLANK + detailItemName + BLANK + CODE, w1);
				sheet.addColumn(CONTRA + BLANK + ESTIMATE + BLANK + detailItemName + BLANK + ABBR, w2);
			}
			sheet.addColumn(TAX + BLANK + CODE + BRACKET_OPEN + CONTRA + BLANK + ESTIMATE + BRACKET_CLOSE, w3);
			sheet.addColumn(TAX + BLANK + ABBR + BLANK + BRACKET_OPEN + CONTRA + BLANK + ESTIMATE + BRACKET_CLOSE, w4);
		}

		// ���ו`��
		for (OPItem item : itemList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(item.getITEM_CODE());
			newRow.addCell(item.getITEM_NAME());
			newRow.addCell(item.getITEM_NAME_S());
			newRow.addCell(item.getITEM_NAME_K());
			newRow.addCell(item.getITEM_INV_NAME());
			newRow.addCell(getWord(OPContractType.getName(OPContractType.get(item.getCTRT_TYPE()))));
			newRow.addCell(getWord(OPSaKbn.getName(OPSaKbn.get(item.getSA_KBN()))));
			newRow.addCell(getWord(Dc.getName(Dc.getDc(item.getDC_KBN()))));
			newRow.addCell(getWord(OPItemControlDivision.getName(OPItemControlDivision.get(item.getITEM_CTL_KBN()))));

			if (OPItemControlDivision.BUNKER_EXPENSE.value.equals(item.getITEM_CTL_KBN())
				|| OPItemControlDivision.BUNKER_STOCK.value.equals(item.getITEM_CTL_KBN())
				|| OPItemControlDivision.BUNKER_STOCK_ESTIMATE.value.equals(item.getITEM_CTL_KBN())
				|| OPItemControlDivision.BUNKER_STOCK_BOR.value.equals(item.getITEM_CTL_KBN())
				|| OPItemControlDivision.OH_1_NO_SOBC.value.equals(item.getITEM_CTL_KBN())
				|| OPItemControlDivision.OH_2_DONE_SOBC.value.equals(item.getITEM_CTL_KBN())
				|| OPItemControlDivision.BUNKER_SUPPLY.value.equals(item.getITEM_CTL_KBN())) {
				newRow.addCell(item.getBNKR_TYPE_CODE());
			} else {
				newRow.addCell(getWord(OPItemSubDivision.getName(OPItemSubDivision.get(item.getITEM_SUB_KBN()))));
			}
			newRow.addCell(getWord(OPMonthlyBasisDivision.getName(item.getOCCUR_BASE_KBN())));
			if (isUseOwnrShip) {
				newRow.addCell(item.getOWNR_SHIP_NAME());
			}
			newRow.addCell(convertAddressCommission(item.getADCOM_KBN()));
			newRow.addCell(convertAddressCommission(item.getBRKR_KBN()));
			newRow.addCell(item.getROW_OUTLINE());

			newRow.addCell(item.getKMK_CODE());
			newRow.addCell(item.getKMK_NAME_S());
			newRow.addCell(item.getHKM_CODE());
			newRow.addCell(item.getHKM_NAME_S());
			if (isUseDetailItem) {
				newRow.addCell(item.getUKM_CODE());
				newRow.addCell(item.getUKM_NAME_S());
			}
			newRow.addCell(item.getZEI_CODE());
			newRow.addCell(item.getZEI_NAME_S());

			newRow.addCell(item.getAT_KMK_CODE());
			newRow.addCell(item.getAT_KMK_NAME_S());
			newRow.addCell(item.getAT_HKM_CODE());
			newRow.addCell(item.getAT_HKM_NAME_S());
			if (isUseDetailItem) {
				newRow.addCell(item.getAT_UKM_CODE());
				newRow.addCell(item.getAT_UKM_NAME_S());
			}
			newRow.addCell(item.getAT_ZEI_CODE());
			newRow.addCell(item.getAT_ZEI_NAME_S());

			newRow.addCell(item.getKURI_KMK_CODE());
			newRow.addCell(item.getKURI_KMK_NAME_S());
			newRow.addCell(item.getKURI_HKM_CODE());
			newRow.addCell(item.getKURI_HKM_NAME_S());
			if (isUseDetailItem) {
				newRow.addCell(item.getKURI_UKM_CODE());
				newRow.addCell(item.getKURI_UKM_NAME_S());
			}
			newRow.addCell(item.getKURI_ZEI_CODE());
			newRow.addCell(item.getKURI_ZEI_NAME_S());

			newRow.addCell(item.getAT_KURI_KMK_CODE());
			newRow.addCell(item.getAT_KURI_KMK_NAME_S());
			newRow.addCell(item.getAT_KURI_HKM_CODE());
			newRow.addCell(item.getAT_KURI_HKM_NAME_S());
			if (isUseDetailItem) {
				newRow.addCell(item.getAT_KURI_UKM_CODE());
				newRow.addCell(item.getAT_KURI_UKM_NAME_S());
			}
			newRow.addCell(item.getAT_KURI_ZEI_CODE());
			newRow.addCell(item.getAT_KURI_ZEI_NAME_S());

			newRow.addCell(item.getEST_KMK_CODE());
			newRow.addCell(item.getEST_KMK_NAME_S());
			newRow.addCell(item.getEST_HKM_CODE());
			newRow.addCell(item.getEST_HKM_NAME_S());
			if (isUseDetailItem) {
				newRow.addCell(item.getEST_UKM_CODE());
				newRow.addCell(item.getEST_UKM_NAME_S());
			}
			newRow.addCell(item.getEST_ZEI_CODE());
			newRow.addCell(item.getEST_ZEI_NAME_S());

			newRow.addCell(item.getAT_EST_KMK_CODE());
			newRow.addCell(item.getAT_EST_KMK_NAME_S());
			newRow.addCell(item.getAT_EST_HKM_CODE());
			newRow.addCell(item.getAT_EST_HKM_NAME_S());
			if (isUseDetailItem) {
				newRow.addCell(item.getAT_EST_UKM_CODE());
				newRow.addCell(item.getAT_EST_UKM_NAME_S());
			}
			newRow.addCell(item.getAT_EST_ZEI_CODE());
			newRow.addCell(item.getAT_EST_ZEI_NAME_S());

		}

	}

	/**
	 * Convert address commission to string value.
	 * 
	 * @param value the value
	 * @return the string
	 */
	public String convertAddressCommission(int value) {
		switch (value) {
			case 1:
				return "ON";
			case 0:
				return "OFF";
			default:
				return "";
		}
	}

}