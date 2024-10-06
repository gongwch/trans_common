package jp.co.ais.trans2.common.model.excel;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票エクスポート処理 エクスポート用エクセル
 * 
 * @param <E>
 * @param <T>
 */
public class SlipInstructionExternalExcel<E extends SlipInstructionDtl, T extends ExcelImportableColumn> extends
	TExcelImportable<SlipInstructionDtl, ExcelImportableColumn> {

	/** 会社情報エンティティ */
	protected Company company;

	/** 会計関連情報設定 */
	protected AccountConfig ac;

	/** 会社情報一覧 */
	protected List<Company> companyList;

	/** 基軸通貨 端数 */
	protected int keyDec = 0;

	/** 受入日 */
	protected String ukeDate = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang
	 * @param company
	 */
	public SlipInstructionExternalExcel(String lang, Company company) {
		super(lang);
		this.company = company;
		this.ac = company.getAccountConfig();
		this.keyDec = ac.getKeyCurrency().getDecimalPoint();
		this.ukeDate = DateUtil.toYMDPlainString(Util.getCurrentDate());
	}

	/**
	 * 行の値マッピング
	 * 
	 * @param bean
	 */
	@Override
	protected void addRowCellValue(ExcelImportableColumn column, SlipInstructionDtl bean, TExcelRow newRow) {

		SlipInstructionExcelColumn col = (SlipInstructionExcelColumn) column;

		switch (col) {
			case KAI_CODE:
				newRow.addCell(bean.getKAI_CODE());
				break;
			case SLIP_DATE:
				newRow.addCell(bean.getSWK_DEN_DATE());
				break;
			case SLIP_NO:
				newRow.addCell(bean.getSWK_DEN_NO());
				break;
			case GYO_NO:
				newRow.addCell(bean.getSWK_GYO_NO());
				break;
			case U_DEP_CODE:
				newRow.addCell(bean.getSWK_UKE_DEP_CODE());
				break;
			case TEK_CODE:
				newRow.addCell(bean.getSWK_TEK_CODE());
				break;
			case TEK:
				newRow.addCell(bean.getSWK_TEK());
				break;
			case SYO_EMP_CODE:
				newRow.addCell(bean.getSWK_SYO_EMP_CODE());
				break;
			case SYO_DATE:
				newRow.addCell(bean.getSWK_SYO_DATE());
				break;
			case IRAI_EMP_CODE:
				newRow.addCell(bean.getSWK_IRAI_EMP_CODE());
				break;
			case IRAI_DEP_CODE:
				newRow.addCell(bean.getSWK_IRAI_DEP_CODE());
				break;
			case IRAI_DATE:
				newRow.addCell(bean.getSWK_IRAI_DATE());
				break;
			case SYS_KBN:
				newRow.addCell(bean.getSWK_SYS_KBN());
				break;
			case DEN_SYU_CODE:
				newRow.addCell(bean.getSWK_DEN_SYU());
				break;
			case UPD_KBN:
				newRow.addCell(bean.getSWK_UPD_KBN());
				break;
			case KSN_KBN:
				newRow.addCell(bean.getSWK_KSN_KBN());
				break;
			case KMK_CODE:
				newRow.addCell(bean.getSWK_KMK_CODE());
				break;
			case HKM_CODE:
				newRow.addCell(bean.getSWK_HKM_CODE());
				break;
			case UKM_CODE:
				newRow.addCell(bean.getSWK_UKM_CODE());
				break;
			case DEP_CODE:
				newRow.addCell(bean.getSWK_DEP_CODE());
				break;
			case TRI_CODE:
				newRow.addCell(bean.getSWK_TRI_CODE());
				break;
			case EMP_CODE:
				newRow.addCell(bean.getSWK_EMP_CODE());
				break;
			case CUR_CODE:
				newRow.addCell(bean.getSWK_CUR_CODE());
				break;
			case CUR_RATE:
				newRow.addCell(bean.getSWK_CUR_RATE(), 4);
				break;
			case DC_KBN:
				newRow.addCell(bean.getSWK_DC_KBN());
				break;
			case ZEI_KBN:
				newRow.addCell(bean.getSWK_ZEI_KBN());
				break;
			case SZEI_KIN:
				newRow.addCell(bean.getSWK_ZEI_KIN(), keyDec);
				break;
			case SZEI_CODE:
				newRow.addCell(bean.getSWK_ZEI_CODE());
				break;
			case GYO_TEK_CODE:
				newRow.addCell(bean.getSWK_GYO_TEK_CODE());
				break;
			case GYO_TEK:
				newRow.addCell(bean.getSWK_GYO_TEK());
				break;
			case KNR1_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_1());
				break;
			case KNR2_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_2());
				break;
			case KNR3_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_3());
				break;
			case KNR4_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_4());
				break;
			case KNR5_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_5());
				break;
			case KNR6_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_6());
				break;
			case SWK_HM1:
				newRow.addCell(bean.getSWK_HM_1());
				break;
			case SWK_HM2:
				newRow.addCell(bean.getSWK_HM_2());
				break;
			case SWK_HM3:
				newRow.addCell(bean.getSWK_HM_3());
				break;
			case AUTO_KBN:
				newRow.addCell(bean.getSWK_AUTO_KBN());
				break;
			case HAS_DATE:
				newRow.addCell(bean.getHAS_DATE());
				break;
			case AT_KMK_CODE:
				newRow.addCell(bean.getSWK_AT_KMK_CODE());
				break;
			case AT_HKM_CODE:
				newRow.addCell(bean.getSWK_AT_HKM_CODE());
				break;
			case AT_UKM_CODE:
				newRow.addCell(bean.getSWK_AT_UKM_CODE());
				break;
			case AT_DEP_CODE:
				newRow.addCell(bean.getSWK_AT_DEP_CODE());
				break;
			case K_KAI_CODE:
				newRow.addCell(bean.getSWK_K_KAI_CODE());
				break;
			case SEI_NO:
				newRow.addCell(bean.getSWK_SEI_NO());
				break;
			case SWK_KIN:
				newRow.addCell(bean.getSWK_KIN(), keyDec);
				break;
			case SWK_IN_KIN:
				newRow.addCell(bean.getSWK_IN_KIN(), bean.getCUR_DEC_KETA());
				break;
			case SIHA_KBN:
				newRow.addCell(bean.getSWK_SIHA_KBN());
				break;
			case SIHA_DATE:
				newRow.addCell(bean.getSWK_SIHA_DATE());
				break;
			case SIHA_HOU:
				newRow.addCell(bean.getSWK_HOH_CODE());
				break;
			case HORYU_KBN:
				newRow.addCell(bean.getSWK_HORYU_KBN());
				break;
			case TJK_CODE:
				newRow.addCell(bean.getSWK_TJK_CODE());
				break;
			case NYU_DATE:
				newRow.addCell(bean.getSWK_AR_DATE());
				break;
			case UKE_DATE:
				newRow.addCell(ukeDate);
				break;
			case CBK_CODE:
				newRow.addCell(bean.getSWK_CBK_CODE());
				break;
			case TUKE_KBN:
				newRow.addCell(bean.getSWK_TUKE_KBN());
				break;
			case SZEI_IN_KIN:
				newRow.addCell(bean.getSWK_IN_ZEI_KIN(), bean.getCUR_DEC_KETA());
				break;
		}

	}

	/**
	 * 出力時のシート名称を取得
	 * 
	 * @return シート名称
	 */
	@Override
	protected String getSheetName() {
		return getWord("C04293");
	}

	/**
	 * カラム名を取得する
	 * 
	 * @param column
	 * @return カラム名
	 */
	@Override
	protected String getColumnName(ExcelImportableColumn column) {
		SlipInstructionExcelColumn col = (SlipInstructionExcelColumn) column;
		switch (col) {
			case KMK_CODE:
				return ac.getItemName() + getWord("C00174");
			case HKM_CODE:
				return ac.getSubItemName() + getWord("C00174");
			case UKM_CODE: {
				String name = ac.getDetailItemName();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C00024");
				}
				return name + getWord("C00174");
			}
			case KNR1_CODE: {
				String name = ac.getManagement1Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01025");
				}
				return name + getWord("C00174");
			}
			case KNR2_CODE: {
				String name = ac.getManagement2Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01027");
				}
				return name + getWord("C00174");
			}
			case KNR3_CODE: {
				String name = ac.getManagement3Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01029");
				}
				return name + getWord("C00174");
			}
			case KNR4_CODE: {
				String name = ac.getManagement4Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01031");
				}
				return name + getWord("C00174");
			}
			case KNR5_CODE: {
				String name = ac.getManagement5Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01033");
				}
				return name + getWord("C00174");
			}
			case KNR6_CODE: {
				String name = ac.getManagement6Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01035");
				}
				return name + getWord("C00174");
			}
			case SWK_HM1: {
				String name = ac.getNonAccounting1Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01291");
				}
				return name;
			}
			case SWK_HM2: {
				String name = ac.getNonAccounting2Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01292");
				}
				return name;
			}
			case SWK_HM3: {
				String name = ac.getNonAccounting3Name();
				if (Util.isNullOrEmpty(name)) {
					name = getWord("C01293");
				}
				return name;
			}
		}
		return getWord(col.getName());
	}

	/**
	 * 仕訳の会計設定を取得する
	 * 
	 * @param kai_CODE
	 * @return 会計設定
	 */
	protected AccountConfig getAccountConfig(String kai_CODE) {
		if (Util.isNullOrEmpty(kai_CODE)) {
			return ac;
		}
		if (companyList == null || companyList.isEmpty()) {
			return ac;
		}
		for (Company com : companyList) {
			if (Util.equals(kai_CODE, com.getCode())) {
				return com.getAccountConfig();
			}
		}
		return ac;
	}

	@Override
	protected ExcelImportableColumn[] getAllColumns() {
		return SlipInstructionExcelColumn.values();
	}

	@Override
	protected SlipInstructionDtl createEntity() {
		return new SlipInstructionDtl();
	}

	/**
	 * 
	 */
	@Override
	protected void setColumnValue(int row, ExcelImportableColumn column, SlipInstructionDtl bean) {
		SlipInstructionExcelColumn col = (SlipInstructionExcelColumn) column;
		if (!col.isImportColumn()) {
			// 取込非対象カラムはマッピングしない
			return;
		}
		try {
			AccountConfig conf = getAccountConfig(bean.getKAI_CODE());
			switch (col) {
				case KAI_CODE:
					bean.setKAI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SLIP_DATE:
					bean.setSWK_DEN_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SLIP_NO:
					bean.setSWK_DEN_NO(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case GYO_NO:
					bean.setSWK_GYO_NO(sheet.getInt(row, getColumnIndex(col)));
					break;
				case U_DEP_CODE:
					bean.setSWK_UKE_DEP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case TEK_CODE:
					bean.setSWK_TEK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case TEK:
					bean.setSWK_TEK(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SYO_EMP_CODE:
					bean.setSWK_SYO_EMP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SYO_DATE:
					bean.setSWK_SYO_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case IRAI_EMP_CODE:
					bean.setSWK_IRAI_EMP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case IRAI_DEP_CODE:
					bean.setSWK_IRAI_DEP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case IRAI_DATE:
					bean.setSWK_IRAI_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SYS_KBN:
					bean.setSWK_SYS_KBN(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case DEN_SYU_CODE:
					bean.setSWK_DEN_SYU(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case UPD_KBN:
					bean.setSWK_UPD_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case KSN_KBN:
					bean.setSWK_KSN_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case KMK_CODE:
					bean.setSWK_KMK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case HKM_CODE:
					bean.setSWK_HKM_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case UKM_CODE:
					bean.setSWK_UKM_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case DEP_CODE:
					bean.setSWK_DEP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case TRI_CODE:
					bean.setSWK_TRI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case EMP_CODE:
					bean.setSWK_EMP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case CUR_CODE:
					bean.setSWK_CUR_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case CUR_RATE:
					bean.setSWK_CUR_RATE(DecimalUtil.avoidNull(sheet.getDecimal(row, getColumnIndex(col))));
					break;
				case DC_KBN:
					bean.setSWK_DC_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case ZEI_KBN:
					bean.setSWK_ZEI_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case SZEI_KIN:
					bean.setSWK_ZEI_KIN(DecimalUtil.avoidNull(sheet.getDecimal(row, getColumnIndex(col))));
					break;
				case SZEI_CODE:
					bean.setSWK_ZEI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case GYO_TEK_CODE:
					bean.setSWK_GYO_TEK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case GYO_TEK:
					bean.setSWK_GYO_TEK(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case KNR1_CODE:
					bean.setSWK_KNR_CODE_1(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case KNR2_CODE:
					bean.setSWK_KNR_CODE_2(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case KNR3_CODE:
					bean.setSWK_KNR_CODE_3(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case KNR4_CODE:
					bean.setSWK_KNR_CODE_4(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case KNR5_CODE:
					bean.setSWK_KNR_CODE_5(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case KNR6_CODE:
					bean.setSWK_KNR_CODE_6(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SWK_HM1: {
					NonAccountingDivision div = conf == null ? NonAccountingDivision.NONE : conf.getNonAccounting1();
					String value = null;
					switch (div) {
						case NONE:
							break;
						case CHAR:
							value = sheet.getString(row, getColumnIndex(col));
							break;
						case NUMBER:
							value = Util.avoidNull(sheet.getDecimal(row, getColumnIndex(col)));
							break;
						case YMD_DATE:
							value = DateUtil.toYMDPlainString(sheet.getDate(row, getColumnIndex(col)));
							break;
						case YMDHM_DATE:
							value = DateUtil.toYMDHMPlainString(sheet.getDate(row, getColumnIndex(col)));
							break;
					}
					bean.setSWK_HM_1(value);
					break;
				}
				case SWK_HM2: {
					NonAccountingDivision div = conf == null ? NonAccountingDivision.NONE : conf.getNonAccounting1();
					String value = null;
					switch (div) {
						case NONE:
							break;
						case CHAR:
							value = sheet.getString(row, getColumnIndex(col));
							break;
						case NUMBER:
							value = Util.avoidNull(sheet.getDecimal(row, getColumnIndex(col)));
							break;
						case YMD_DATE:
							value = DateUtil.toYMDPlainString(sheet.getDate(row, getColumnIndex(col)));
							break;
						case YMDHM_DATE:
							value = DateUtil.toYMDHMPlainString(sheet.getDate(row, getColumnIndex(col)));
							break;
					}
					bean.setSWK_HM_2(value);
					break;
				}
				case SWK_HM3: {
					NonAccountingDivision div = conf == null ? NonAccountingDivision.NONE : conf.getNonAccounting1();
					String value = null;
					switch (div) {
						case NONE:
							break;
						case CHAR:
							value = sheet.getString(row, getColumnIndex(col));
							break;
						case NUMBER:
							value = Util.avoidNull(sheet.getDecimal(row, getColumnIndex(col)));
							break;
						case YMD_DATE:
							value = DateUtil.toYMDPlainString(sheet.getDate(row, getColumnIndex(col)));
							break;
						case YMDHM_DATE:
							value = DateUtil.toYMDHMPlainString(sheet.getDate(row, getColumnIndex(col)));
							break;
					}
					bean.setSWK_HM_3(value);
					break;
				}
				case AUTO_KBN:
					bean.setSWK_AUTO_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case HAS_DATE:
					bean.setHAS_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case AT_KMK_CODE:
					bean.setSWK_AT_KMK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case AT_HKM_CODE:
					bean.setSWK_AT_HKM_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case AT_UKM_CODE:
					bean.setSWK_AT_UKM_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case AT_DEP_CODE:
					bean.setSWK_AT_DEP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case K_KAI_CODE:
					bean.setSWK_K_KAI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SEI_NO:
					bean.setSWK_SEI_NO(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SWK_KIN:
					bean.setSWK_KIN(DecimalUtil.avoidNull(sheet.getDecimal(row, getColumnIndex(col))));
					break;
				case SWK_IN_KIN:
					bean.setSWK_IN_KIN(DecimalUtil.avoidNull(sheet.getDecimal(row, getColumnIndex(col))));
					break;
				case SIHA_KBN:
					bean.setSWK_SIHA_KBN(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SIHA_DATE:
					bean.setSWK_SIHA_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case SIHA_HOU:
					bean.setSWK_HOH_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case HORYU_KBN:
					bean.setSWK_HORYU_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case TJK_CODE:
					bean.setSWK_TJK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case NYU_DATE:
					bean.setSWK_AR_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case UKE_DATE:
					bean.setSWK_UKE_DATE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case CBK_CODE:
					bean.setSWK_CBK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case TUKE_KBN:
					bean.setSWK_TUKE_KBN(sheet.getInt(row, getColumnIndex(col)));
					break;
				case SZEI_IN_KIN:
					bean.setSWK_IN_ZEI_KIN(DecimalUtil.avoidNull(sheet.getDecimal(row, getColumnIndex(col))));
					break;

			}
		} catch (Exception e) {
			// エラー発生時、スキップ
		}
	}

	/**
	 * 伝票エクセルを読み込み仕訳明細行へと変換
	 * 
	 * @param file
	 * @param companys
	 * @return 仕訳明細行リスト
	 * @throws TException
	 */
	public List<SlipInstructionDtl> convertToEntityList(File file, List<Company> companys) throws TException {
		this.companyList = companys;
		return convertToEntityList(file);
	}

}
