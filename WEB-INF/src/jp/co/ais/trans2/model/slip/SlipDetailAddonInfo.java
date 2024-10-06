package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * 伝票追加情報
 */
public class SlipDetailAddonInfo extends TransferBase {

	/**
	 * 文字列出力
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(map);
		return sb.toString();
	}

	/** マップ(キー, 値) */
	protected Map<String, Object> map;

	/** true:概算、false:実績 */
	public static final String KEY_ESTIMATE_FLAG = "estimateFlag";

	/** 貨物コード(CARGO) */
	public static final String KEY_CARGO_CODE = "cargoCode";

	/** 貨物名称(CARGO) */
	public static final String KEY_CARGO_NAME = "cargoName";

	/** 荷主コード(CARGO) */
	public static final String KEY_CHTR_CODE = "chtrCode";

	/** 期間FROM開始日時 */
	public static final String KEY_PERIOD_FROM = "periodFrom";

	/** 期間TO終了日時 */
	public static final String KEY_PERIOD_TO = "periodTo";

	/** 期間日数(FROM-TO)小数点以下5桁(9,5) */
	public static final String KEY_DURATION = "duration";

	/** VC契約UID */
	public static final String KEY_VCC_UID = "vccUID";

	/** VC契約番号 */
	public static final String KEY_VCC_CTRT_NO = "vccContractNo";

	/** TC契約UID */
	public static final String KEY_TCC_UID = "tccUID";

	/** TC契約番号 */
	public static final String KEY_TCC_CTRT_NO = "tccContractNo";

	/** 航海タイプ */
	public static final String KEY_VOY_TYPE = "voyType";

	/** 運賃数量 */
	public static final String KEY_FRT_QTY = "frtQty";

	/** 数量単位コード */
	public static final String KEY_FRT_QTY_UNIT_CODE = "frtQtyUnitCode";

	/** 油種コード */
	public static final String KEY_BUNKER_TYPE_CODE = "bunkerTypeCode";

	/** 燃料数量 */
	public static final String KEY_BUNKER_QTY = "bunkerQty";

	/** 燃料単価 */
	public static final String KEY_BUNKER_PRICE = "bunkerPrice";

	/** 燃料補油日 */
	public static final String KEY_BUNKER_SUPPLY_DATE = "bunkerSupplyDate";

	/** 燃料補油先 */
	public static final String KEY_BUNKER_SUPPLIER_CODE = "bunkerSupplierCode";

	/** VOY_UID */
	public static final String KEY_VOY_UID = "voyUID";

	/**
	 * コンストラクター
	 */
	public SlipDetailAddonInfo() {
		map = new HashMap<String, Object>();
	}

	/**
	 * 設定する<キー, 値>
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * 設定値を取得する
	 * 
	 * @param key
	 * @return 値
	 */
	public Object getValue(String key) {
		return map.get(key);
	}

	/**
	 * 設定値を取得する(文字列モード)
	 * 
	 * @param key
	 * @return 値
	 */
	public String getString(String key) {
		Object obj = map.get(key);
		return Util.avoidNull(obj);
	}

	/**
	 * 設定値を取得する(文字列モード)
	 * 
	 * @param key
	 * @return 値
	 */
	public BigDecimal getNumber(String key) {
		String str = getString(key);

		if (!Util.isNumber(str)) {
			return null;
		}

		return DecimalUtil.toBigDecimalNULL(str);
	}

	/**
	 * true:概算、false:実績の取得
	 * 
	 * @return estimateFlag true:概算、false:実績
	 */
	public Boolean getEstimateFlag() {
		return (Boolean) getValue(KEY_ESTIMATE_FLAG);
	}

	/**
	 * true:概算、false:実績の設定
	 * 
	 * @param estimateFlag true:概算、false:実績
	 */
	public void setEstimateFlag(Boolean estimateFlag) {
		setValue(KEY_ESTIMATE_FLAG, estimateFlag);
	}

	/**
	 * 貨物コード(CARGO)の取得
	 * 
	 * @return cargoCode 貨物コード(CARGO)
	 */
	public String getCargoCode() {
		return (String) getValue(KEY_CARGO_CODE);
	}

	/**
	 * 貨物コード(CARGO)の設定
	 * 
	 * @param cargoCode 貨物コード(CARGO)
	 */
	public void setCargoCode(String cargoCode) {
		setValue(KEY_CARGO_CODE, cargoCode);
	}

	/**
	 * 貨物名称(CARGO)の取得
	 * 
	 * @return cargoName 貨物名称(CARGO)
	 */
	public String getCargoName() {
		return (String) getValue(KEY_CARGO_NAME);
	}

	/**
	 * 貨物名称(CARGO)の設定
	 * 
	 * @param cargoName 貨物名称(CARGO)
	 */
	public void setCargoName(String cargoName) {
		setValue(KEY_CARGO_NAME, cargoName);
	}

	/**
	 * 荷主コード(VCC CHTR)の取得
	 * 
	 * @return chtrCode 荷主コード(VCC CHTR)
	 */
	public String getChtrCode() {
		return (String) getValue(KEY_CHTR_CODE);
	}

	/**
	 * 荷主コード(VCC CHTR)の設定
	 * 
	 * @param chtrCode 荷主コード(VCC CHTR)
	 */
	public void setChtrCode(String chtrCode) {
		setValue(KEY_CHTR_CODE, chtrCode);
	}

	/**
	 * 期間FROM開始日時の取得
	 * 
	 * @return periodFrom 期間FROM開始日時
	 */
	public Date getPeriodFrom() {
		return (Date) getValue(KEY_PERIOD_FROM);
	}

	/**
	 * 期間FROM開始日時の設定
	 * 
	 * @param periodFrom 期間FROM開始日時
	 */
	public void setPeriodFrom(Date periodFrom) {
		setValue(KEY_PERIOD_FROM, periodFrom);
	}

	/**
	 * 期間TO終了日時の取得
	 * 
	 * @return periodTo 期間TO終了日時
	 */
	public Date getPeriodTo() {
		return (Date) getValue(KEY_PERIOD_TO);
	}

	/**
	 * 期間TO終了日時の設定
	 * 
	 * @param periodTo 期間TO終了日時
	 */
	public void setPeriodTo(Date periodTo) {
		setValue(KEY_PERIOD_TO, periodTo);
	}

	/**
	 * 期間日数(FROM-TO)小数点以下5桁(9,5)の取得
	 * 
	 * @return duration 期間日数(FROM-TO)小数点以下5桁(9,5)
	 */
	public BigDecimal getDuration() {
		return (BigDecimal) getValue(KEY_DURATION);
	}

	/**
	 * 期間日数(FROM-TO)小数点以下5桁(9,5)の設定
	 * 
	 * @param duration 期間日数(FROM-TO)小数点以下5桁(9,5)
	 */
	public void setDuration(BigDecimal duration) {
		setValue(KEY_DURATION, duration);
	}

	/**
	 * VC契約UIDの取得
	 * 
	 * @return vccUID VC契約UID
	 */
	public String getVccUID() {
		return (String) getValue(KEY_VCC_UID);
	}

	/**
	 * VC契約UIDの設定
	 * 
	 * @param vccUID VC契約UID
	 */
	public void setVccUID(String vccUID) {
		setValue(KEY_VCC_UID, vccUID);
	}

	/**
	 * VC契約番号の取得
	 * 
	 * @return vccContractNo VC契約番号
	 */
	public String getVccContractNo() {
		return (String) getValue(KEY_VCC_CTRT_NO);
	}

	/**
	 * VC契約番号の設定
	 * 
	 * @param vccContractNo VC契約番号
	 */
	public void setVccContractNo(String vccContractNo) {
		setValue(KEY_VCC_CTRT_NO, vccContractNo);
	}

	/**
	 * TC契約UIDの取得
	 * 
	 * @return tccContractNo TC契約番号
	 */
	public String getTccUID() {
		return (String) getValue(KEY_TCC_UID);
	}

	/**
	 * TC契約UIDの設定
	 * 
	 * @param tccUID TC契約UID
	 */
	public void setTccUID(String tccUID) {
		setValue(KEY_TCC_UID, tccUID);
	}

	/**
	 * TC契約番号の取得
	 * 
	 * @return tccContractNo TC契約番号
	 */
	public String getTccContractNo() {
		return (String) getValue(KEY_TCC_CTRT_NO);
	}

	/**
	 * TC契約番号の設定
	 * 
	 * @param tccContractNo TC契約番号
	 */
	public void setTccContractNo(String tccContractNo) {
		setValue(KEY_TCC_CTRT_NO, tccContractNo);
	}

	/**
	 * 航海タイプの取得
	 * 
	 * @return voyType 航海タイプ
	 */
	public String getVoyType() {
		return (String) getValue(KEY_VOY_TYPE);
	}

	/**
	 * 航海タイプの設定
	 * 
	 * @param voyType 航海タイプ
	 */
	public void setVoyType(String voyType) {
		setValue(KEY_VOY_TYPE, voyType);
	}

	/**
	 * 運賃数量の取得
	 * 
	 * @return frtQty 運賃数量
	 */
	public BigDecimal getFrtQty() {
		return (BigDecimal) getValue(KEY_FRT_QTY);
	}

	/**
	 * 運賃数量の設定
	 * 
	 * @param frtQty 運賃数量
	 */
	public void setFrtQty(BigDecimal frtQty) {
		setValue(KEY_FRT_QTY, frtQty);
	}

	/**
	 * 数量単位コードの取得
	 * 
	 * @return frtQtyUnitCode 数量単位コード
	 */
	public String getFrtQtyUnitCode() {
		return (String) getValue(KEY_FRT_QTY_UNIT_CODE);
	}

	/**
	 * 数量単位コードの設定
	 * 
	 * @param frtQtyUnitCode 数量単位コード
	 */
	public void setFrtQtyUnitCode(String frtQtyUnitCode) {
		setValue(KEY_FRT_QTY_UNIT_CODE, frtQtyUnitCode);
	}

	/**
	 * 油種コードの取得
	 * 
	 * @return bunkerTypeCode 油種コード
	 */
	public String getBunkerTypeCode() {
		return (String) getValue(KEY_BUNKER_TYPE_CODE);
	}

	/**
	 * 油種コードの設定
	 * 
	 * @param bunkerTypeCode 油種コード
	 */
	public void setBunkerTypeCode(String bunkerTypeCode) {
		setValue(KEY_BUNKER_TYPE_CODE, bunkerTypeCode);
	}

	/**
	 * 燃料数量の取得
	 * 
	 * @return bunkerQty 燃料数量
	 */
	public BigDecimal getBunkerQty() {
		return (BigDecimal) getValue(KEY_BUNKER_QTY);
	}

	/**
	 * 燃料数量の設定
	 * 
	 * @param bunkerQty 燃料数量
	 */
	public void setBunkerQty(BigDecimal bunkerQty) {
		setValue(KEY_BUNKER_QTY, bunkerQty);
	}

	/**
	 * 燃料単価の取得
	 * 
	 * @return bunkerPrice 燃料単価
	 */
	public BigDecimal getBunkerPrice() {
		return (BigDecimal) getValue(KEY_BUNKER_PRICE);
	}

	/**
	 * 燃料単価の設定
	 * 
	 * @param bunkerPrice 燃料単価
	 */
	public void setBunkerPrice(BigDecimal bunkerPrice) {
		setValue(KEY_BUNKER_PRICE, bunkerPrice);
	}

	/**
	 * 燃料補油日の取得
	 * 
	 * @return bunkerSupplyDate 燃料補油日
	 */
	public Date getBunkerSupplyDate() {
		return (Date) getValue(KEY_BUNKER_SUPPLY_DATE);
	}

	/**
	 * 燃料補油日の設定
	 * 
	 * @param bunkerSupplyDate 燃料補油日
	 */
	public void setBunkerSupplyDate(Date bunkerSupplyDate) {
		setValue(KEY_BUNKER_SUPPLY_DATE, bunkerSupplyDate);
	}

	/**
	 * 燃料補油先の取得
	 * 
	 * @return bunkerSupplier 燃料補油先
	 */
	public String getBunkerSupplierCode() {
		return (String) getValue(KEY_BUNKER_SUPPLIER_CODE);
	}

	/**
	 * 燃料補油先の設定
	 * 
	 * @param bunkerSupplierCode 燃料補油先
	 */
	public void setBunkerSupplierCode(String bunkerSupplierCode) {
		setValue(KEY_BUNKER_SUPPLIER_CODE, bunkerSupplierCode);
	}

	/**
	 * 航海UIDの取得
	 * 
	 * @return voyUID 航海UID
	 */
	public String getVoyUID() {
		return (String) getValue(KEY_VOY_UID);
	}

	/**
	 * 航海UIDの設定
	 * 
	 * @param voyUID 航海UID
	 */
	public void setVoyUID(String voyUID) {
		setValue(KEY_VOY_UID, voyUID);
	}

}
