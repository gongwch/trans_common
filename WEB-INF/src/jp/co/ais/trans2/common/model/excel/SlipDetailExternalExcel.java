package jp.co.ais.trans2.common.model.excel;

import java.io.*;
import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票明細行エクスポート用エクセル
 * 
 * @param <E>
 * @param <T>
 */
public class SlipDetailExternalExcel<E extends SWK_DTL, T extends ExcelImportableColumn> extends
	TExcelImportable<SWK_DTL, ExcelImportableColumn> {

	/** 会社情報エンティティ */
	protected Company company;

	/** 会計関連情報設定 */
	protected AccountConfig ac;

	/** 会社情報一覧 */
	protected List<Company> companyList;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang
	 * @param company
	 */
	public SlipDetailExternalExcel(String lang, Company company) {
		super(lang);
		this.company = company;
		this.ac = company.getAccountConfig();
	}

	/**
	 * 行の値マッピング
	 * 
	 * @param column
	 * @param bean
	 * @param newRow
	 */
	@Override
	protected void addRowCellValue(ExcelImportableColumn column, SWK_DTL bean, TExcelRow newRow) {
		SlipDetailExcelColumn col = (SlipDetailExcelColumn) column;
		if (!isOutput(column)) {
			return;
		}
		switch (col) {
			case CR_KIN:
				if (bean.getDC() == Dc.CREDIT) {
					newRow.addCell(bean.getSWK_IN_KIN(), bean.getCUR_DEC_KETA());
				} else {
					newRow.addCell(BigDecimal.ZERO, bean.getCUR_DEC_KETA());
				}
				break;
			case DR_KIN:
				if (bean.getDC() == Dc.DEBIT) {
					newRow.addCell(bean.getSWK_IN_KIN(), bean.getCUR_DEC_KETA());
				} else {
					newRow.addCell(BigDecimal.ZERO, bean.getCUR_DEC_KETA());
				}
				break;
			case CR_TAX:
				if (bean.getDC() == Dc.CREDIT) {
					newRow.addCell(bean.getSWK_IN_ZEI_KIN(), bean.getCUR_DEC_KETA());
				} else {
					newRow.addCell(BigDecimal.ZERO, bean.getCUR_DEC_KETA());
				}
				break;
			case DR_TAX:
				if (bean.getDC() == Dc.DEBIT) {
					newRow.addCell(bean.getSWK_IN_ZEI_KIN(), bean.getCUR_DEC_KETA());
				} else {
					newRow.addCell(BigDecimal.ZERO, bean.getCUR_DEC_KETA());
				}
				break;
			case ZEI:
				String zeiNameCode = TaxCalcType.getTaxCalcTypeName(TaxCalcType.get(bean.getSWK_ZEI_KBN()));
				if (!Util.isNullOrEmpty(zeiNameCode)) {
					newRow.addCell(getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.get(bean.getSWK_ZEI_KBN()))));
				}
				break;
			case CUR_CODE:
				newRow.addCell(bean.getSWK_CUR_CODE());
				break;
			case EMP_CODE:
				newRow.addCell(bean.getSWK_EMP_CODE());
				break;
			case EMP_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_EMP_NAME()) ? bean.getSWK_EMP_NAME_S() : bean
					.getSWK_EMP_NAME());
				break;
			case GYO_TEK_CODE:
				newRow.addCell(bean.getSWK_GYO_TEK_CODE());
				break;
			case GYO_TEK:
				newRow.addCell(bean.getSWK_GYO_TEK());
				break;
			case HAS_DATE:
				newRow.addCell(bean.getHAS_DATE());
				break;
			case HKM_CODE:
				newRow.addCell(bean.getSWK_HKM_CODE());
				break;
			case HKM_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_HKM_NAME()) ? bean.getSWK_HKM_NAME_S() : bean
					.getSWK_HKM_NAME());
				break;
			case K_DEP_CODE:
				newRow.addCell(bean.getSWK_DEP_CODE());
				break;
			case K_DEP_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_DEP_NAME()) ? bean.getSWK_DEP_NAME_S() : bean
					.getSWK_DEP_NAME());
				break;
			case K_KAI_CODE:
				newRow.addCell(bean.getSWK_K_KAI_CODE());
				break;
			case K_KAI_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_K_KAI_NAME()) ? bean.getSWK_K_KAI_NAME_S() : bean
					.getSWK_K_KAI_NAME());
				break;
			case KMK_CODE:
				newRow.addCell(bean.getSWK_KMK_CODE());
				break;
			case KMK_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KMK_NAME()) ? bean.getSWK_KMK_NAME_S() : bean
					.getSWK_KMK_NAME());
				break;
			case KNR1_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_1());
				break;
			case KNR1_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KNR_NAME_1()) ? bean.getSWK_KNR_NAME_S_1() : bean
					.getSWK_KNR_NAME_1());
				break;
			case KNR2_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_2());
				break;
			case KNR2_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KNR_NAME_2()) ? bean.getSWK_KNR_NAME_S_2() : bean
					.getSWK_KNR_NAME_2());
				break;
			case KNR3_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_3());
				break;
			case KNR3_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KNR_NAME_3()) ? bean.getSWK_KNR_NAME_S_3() : bean
					.getSWK_KNR_NAME_3());
				break;
			case KNR4_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_4());
				break;
			case KNR4_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KNR_NAME_4()) ? bean.getSWK_KNR_NAME_S_4() : bean
					.getSWK_KNR_NAME_4());
				break;
			case KNR5_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_5());
				break;
			case KNR5_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KNR_NAME_5()) ? bean.getSWK_KNR_NAME_S_5() : bean
					.getSWK_KNR_NAME_5());
				break;
			case KNR6_CODE:
				newRow.addCell(bean.getSWK_KNR_CODE_6());
				break;
			case KNR6_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_KNR_NAME_6()) ? bean.getSWK_KNR_NAME_S_6() : bean
					.getSWK_KNR_NAME_6());
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
			case SZEI_CODE:
				newRow.addCell(bean.getSWK_ZEI_CODE());
				break;
			case SZEI_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_ZEI_NAME()) ? bean.getSWK_ZEI_NAME_S() : bean
					.getSWK_ZEI_NAME());
				break;
			case TRI_CODE:
				newRow.addCell(bean.getSWK_TRI_CODE());
				break;
			case TRI_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_TRI_NAME()) ? bean.getSWK_TRI_NAME_S() : bean
					.getSWK_TRI_NAME());
				break;
			case UKM_CODE:
				newRow.addCell(bean.getSWK_UKM_CODE());
				break;
			case UKM_NAME:
				newRow.addCell(Util.isNullOrEmpty(bean.getSWK_UKM_NAME()) ? bean.getSWK_UKM_NAME_S() : bean
					.getSWK_UKM_NAME());
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
	 * エクセルに出力するか否か
	 * 
	 * @param column
	 * @return true:出力する
	 */
	@Override
	public boolean isOutput(ExcelImportableColumn column) {
		SlipDetailExcelColumn col = (SlipDetailExcelColumn) column;
		switch (col) {
			case UKM_CODE:
			case UKM_NAME:
				return ac.isUseDetailItem();
			case KNR1_CODE:
			case KNR1_NAME:
				return ac.isUseManagement1();
			case KNR2_CODE:
			case KNR2_NAME:
				return ac.isUseManagement2();
			case KNR3_CODE:
			case KNR3_NAME:
				return ac.isUseManagement3();
			case KNR4_CODE:
			case KNR4_NAME:
				return ac.isUseManagement4();
			case KNR5_CODE:
			case KNR5_NAME:
				return ac.isUseManagement5();
			case KNR6_CODE:
			case KNR6_NAME:
				return ac.isUseManagement6();
			case SWK_HM1:
				return ac.isUseNotAccounting1();
			case SWK_HM2:
				return ac.isUseNotAccounting2();
			case SWK_HM3:
				return ac.isUseNotAccounting3();
		}
		return true;
	}

	/**
	 * カラム名を取得する
	 * 
	 * @param column
	 * @return カラム名
	 */
	@Override
	protected String getColumnName(ExcelImportableColumn column) {
		SlipDetailExcelColumn col = (SlipDetailExcelColumn) column;
		switch (col) {
			case KMK_CODE:
				return ac.getItemName() + " " + getWord("C00174");
			case KMK_NAME:
				return ac.getItemName();
			case HKM_CODE:
				return ac.getSubItemName() + " " + getWord("C00174");
			case HKM_NAME:
				return ac.getSubItemName();
			case UKM_CODE:
				return ac.getDetailItemName() + " " + getWord("C00174");
			case UKM_NAME:
				return ac.getDetailItemName();
			case KNR1_CODE:
				return ac.getManagement1Name() + " " + getWord("C00174");
			case KNR1_NAME:
				return ac.getManagement1Name();
			case KNR2_CODE:
				return ac.getManagement2Name() + " " + getWord("C00174");
			case KNR2_NAME:
				return ac.getManagement2Name();
			case KNR3_CODE:
				return ac.getManagement3Name() + " " + getWord("C00174");
			case KNR3_NAME:
				return ac.getManagement3Name();
			case KNR4_CODE:
				return ac.getManagement4Name() + " " + getWord("C00174");
			case KNR4_NAME:
				return ac.getManagement4Name();
			case KNR5_CODE:
				return ac.getManagement5Name() + " " + getWord("C00174");
			case KNR5_NAME:
				return ac.getManagement5Name();
			case KNR6_CODE:
				return ac.getManagement6Name() + " " + getWord("C00174");
			case KNR6_NAME:
				return ac.getManagement6Name();
			case SWK_HM1:
				return ac.getNonAccounting1Name();
			case SWK_HM2:
				return ac.getNonAccounting2Name();
			case SWK_HM3:
				return ac.getNonAccounting3Name();
		}
		return col.getName();
	}

	/**
	 * 仕訳明細に値設定
	 * 
	 * @param row
	 * @param column
	 * @param bean
	 */
	@Override
	protected void setColumnValue(int row, ExcelImportableColumn column, SWK_DTL bean) {
		SlipDetailExcelColumn col = (SlipDetailExcelColumn) column;
		if (!col.isImportColumn()) {
			// 取込非対象カラムはマッピングしない
			return;
		}
		try {
			AccountConfig conf = getAccountConfig(bean.getKAI_CODE());
			switch (col) {
				case K_KAI_CODE:
					bean.setSWK_K_KAI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case K_DEP_CODE:
					bean.setSWK_DEP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case CUR_CODE:
					bean.setSWK_CUR_CODE(sheet.getString(row, getColumnIndex(col)));
					break;
				case CR_KIN: {
					BigDecimal amt = sheet.getDecimal(row, getColumnIndex(col));
					if (DecimalUtil.isNullOrZero(amt)) {
						// ゼロの場合相手側に金額があると仮定
						bean.setDC(Dc.DEBIT);
					} else {
						bean.setDC(Dc.CREDIT);
						bean.setSWK_IN_KIN(amt);
					}
					break;
				}
				case DR_KIN: {
					BigDecimal amt = sheet.getDecimal(row, getColumnIndex(col));
					if (DecimalUtil.isNullOrZero(amt)) {
						// ゼロの場合相手側に金額があると仮定
						bean.setDC(Dc.CREDIT);
					} else {
						bean.setDC(Dc.DEBIT);
						bean.setSWK_IN_KIN(amt);
					}
					break;
				}
				case CR_TAX: {
					BigDecimal amt = sheet.getDecimal(row, getColumnIndex(col));
					if (!DecimalUtil.isNullOrZero(amt)) {
						bean.setSWK_ZEI_KIN(amt);
					}
					break;
				}
				case DR_TAX: {
					BigDecimal amt = sheet.getDecimal(row, getColumnIndex(col));
					if (!DecimalUtil.isNullOrZero(amt)) {
						bean.setSWK_ZEI_KIN(amt);
					}
					break;
				}
				case ZEI: {
					String name = sheet.getString(row, getColumnIndex(col));
					bean.setSWK_ZEI_KBN(TaxCalcType.getCode4Name(name, lang));
					break;
				}
				case EMP_CODE:
					bean.setSWK_EMP_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case GYO_TEK:
					bean.setSWK_GYO_TEK(sheet.getString(row, getColumnIndex(col)));
					break;
				case GYO_TEK_CODE:
					bean.setSWK_GYO_TEK_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case HAS_DATE:
					bean.setHAS_DATE(sheet.getDate(row, getColumnIndex(col)));
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
							// カンマ区切を除外
							String dec = sheet.getString(row, getColumnIndex(col));
							dec = dec.replace(",", "");
							// ブランクじゃなかったらセット
							if (!Util.isNullOrEmpty(dec)) {
								value = Util.avoidNull(DecimalUtil.toBigDecimal(dec));
							}
							break;
						case YMD_DATE:
							value = DateUtil.toYMDPlainString(getDate(row, col));
							break;
						case YMDHM_DATE:
							value = DateUtil.toYMDHMPlainString(getDate(row, col));
							break;
					}
					bean.setSWK_HM_1(value);
					break;
				}
				case SWK_HM2: {
					NonAccountingDivision div = conf == null ? NonAccountingDivision.NONE : conf.getNonAccounting2();
					String value = null;
					switch (div) {
						case NONE:
							break;
						case CHAR:
							value = sheet.getString(row, getColumnIndex(col));
							break;
						case NUMBER:
							// カンマ区切を除外
							String dec = sheet.getString(row, getColumnIndex(col));
							dec = dec.replace(",", "");
							// ブランクじゃなかったらセット
							if (!Util.isNullOrEmpty(dec)) {
								value = Util.avoidNull(DecimalUtil.toBigDecimal(dec));
							}
							break;
						case YMD_DATE:
							value = DateUtil.toYMDPlainString(getDate(row, col));
							break;
						case YMDHM_DATE:
							value = DateUtil.toYMDHMPlainString(getDate(row, col));
							break;
					}
					bean.setSWK_HM_2(value);
					break;
				}
				case SWK_HM3: {
					NonAccountingDivision div = conf == null ? NonAccountingDivision.NONE : conf.getNonAccounting3();
					String value = null;
					switch (div) {
						case NONE:
							break;
						case CHAR:
							value = sheet.getString(row, getColumnIndex(col));
							break;
						case NUMBER:
							// カンマ区切を除外
							String dec = sheet.getString(row, getColumnIndex(col));
							dec = dec.replace(",", "");
							// ブランクじゃなかったらセット
							if (!Util.isNullOrEmpty(dec)) {
								value = Util.avoidNull(DecimalUtil.toBigDecimal(dec));
							}
							break;
						case YMD_DATE:
							value = DateUtil.toYMDPlainString(getDate(row, col));
							break;
						case YMDHM_DATE:
							value = DateUtil.toYMDHMPlainString(getDate(row, col));
							break;
					}
					bean.setSWK_HM_3(value);
					break;
				}
				case SZEI_CODE:
					bean.setSWK_ZEI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
				case TRI_CODE:
					bean.setSWK_TRI_CODE(addZero(sheet.getString(row, getColumnIndex(col))));
					break;
			}
		} catch (Exception e) {
			// エラー発生時、スキップ
		}

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
		return SlipDetailExcelColumn.values();
	}

	@Override
	protected SWK_DTL createEntity() {
		return new SWK_DTL();
	}

	/**
	 * 伝票エクセルを読み込み仕訳明細行へと変換
	 * 
	 * @param file
	 * @param companys
	 * @return 仕訳明細行リスト
	 * @throws TException
	 */
	public List<SWK_DTL> convertToEntityList(File file, List<Company> companys) throws TException {
		this.companyList = companys;
		List<SWK_DTL> list = convertToEntityList(file);

		if (list != null && !list.isEmpty()) {
			for (SWK_DTL dtl : list) {
				// 会社間付け替え明細をメンテする
				if (!Util.isNullOrEmpty(dtl.getSWK_K_KAI_CODE())
					&& !Util.equals(dtl.getKAI_CODE(), dtl.getSWK_K_KAI_CODE())) {
					// bugfix #64874: 伝票取込機能：会社間付け替え有効対応
					// 計上会社によって、異なるマスタデータの場合は取込結果がログイン会社で判定してしまう。
					// 原因はSWK_TUKE_KBN＝２の条件漏れ
					// ↓
					// エクセル取込時に、計上会社あり、且つログイン会社と異なる場合にSWK_TUKE_KBNを２固定にする
					dtl.setSWK_TUKE_KBN(2);
				}
			}
		}

		return list;
	}

}
