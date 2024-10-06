package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * 債務伝票結合 検索パラメータ
 */
public class ApSlipParameter extends TransferBase {

	/** ソート 支払日 、伝票番号、伝票日付 */
	public static final String SORT＿SIHA_DATE = "SWK_SIHA_DATE,DEN_NO";

	/** ソート(会社間付替) 会社コード 、伝票日付、伝票番号、行番号 */
	public static final String SORT_TUKEKAE = "KAI_CODE,DEN_DATE,DEN_NO,SWK_GYO_NO";

	/** データ区分 社員仮払 */
	public static final String DATA_KBN_EMP_KARI = "21";

	/** データ区分 経費精算 */
	public static final String DATA_KBN_ADJUSTMENT = "22";

	/** データ区分 交際費会議費 */
	public static final String DATA_KBN_ASSOCIATE_EXPENSE = "2E";

	/** 伝票種別コード 社員仮払 */
	public static final String DEN_SYU_EMP_KARI = "021";

	/** 伝票種別コード 経費精算 */
	public static final String DEN_SYU_ADJUSTMENT = "022";

	/** 仕訳インターフェイス区分 登録 */
	public static final String SWK_IN_KBN_REGIST = "0";

	/** 更新区分 登録 */
	public static final int UPD_KBN_REGIST = 1;

	/** 更新区分 仮承認 */
	public static final int UPD_KBN_TMP_APPROVE = 2;

	/** 更新区分 承認 */
	public static final int UPD_KBN_APPROVE = 3;

	/** 更新区分 更新 */
	public static final int UPD_KBN_UPDATE = 4;

	/** 更新区分 現場否認 */
	public static final int UPD_KBN_NOT_APPROVE = 11;

	/** 更新区分 経理否認 */
	public static final int UPD_KBN_NOT_ACOUNT_APPROVE = 12;

	/** 更新レベル 入力者 */
	public static final int UPD_LVL_EMP = 1;

	/** 更新レベル 部門 */
	public static final int UPD_LVL_DEPT = 2;

	/** 支払決済区分 通常 */
	public static final int KESAI_KBN_NOMAL = 0;

	/** 支払内部コード 未払振込 */
	public static final String NAI_CODE_EMP_UNPAID = "03";

	/** 排他区分 排他中 */
	public static final String HAITA_LOCK = "1";

	/** 排他区分 通常 */
	public static final String HAITA_UNLOCK = "0";

	/** 排他区分 通常、または排他中 */
	public static final String HAITA_ALL = "";

	/** 会社コード */
	private String kaiCode = "";

	/** 伝票日付 */
	private Date denDate;

	/** 支払日 */
	private Date sihaDate;

	/** 社員コード */
	private String empCode = "";

	/** 依頼者 */
	private String iraiEmpCode = "";

	/** 依頼者（orNull検索） */
	private String iraiEmpCodeAndNull = "";

	/** 依頼部門コード */
	private String iraiDepCode = "";

	/** データ区分 */
	private String[] dataKbn;

	/** 伝票種別 */
	private String[] denSyuCode;

	/** 会社間付替区分 */
	private String tukeKbn = "0";

	/** 仕訳インターフェイス区分 */
	private String swkInKbn = "";

	/** 伝票番号 */
	private String denNo = "";

	/** 受付番号 */
	private String utkNo = "";

	/** 更新区分 */
	private int[] updKbn;

	/** 支払金額 (NOT条件) */
	private BigDecimal notSihaKin;

	/** 排他区分 */
	private String shrKbn = "0";

	/** 仮払精算伝票番号(排他条件で設定) */
	private String notHaitaKariCrDenNo = "";

	/** 仮払申請伝票番号 */
	private String kariDrDenNo = "";

	/** 支払決済区分 */
	private int[] kesaiKbn;

	/** 支払方法マスタ.内部コード(配列) */
	private String[] hohNaiCodes;

	/** 支払方法マスタ.内部コード */
	private String hohNaiCode = "";

	/** 社員名(あいまい検索) */
	private String likeEmpName = "";

	/** 伝票番号(あいまい検索) */
	private String likeDenNo = "";

	/** 伝票摘要(あいまい検索) */
	private String likeDenTek = "";

	/** 支払金額(あいまい検索) */
	private String likeSihaKin = "";

	/** 支払方法コード */
	private String hohCode = "";

	/** 銀行口座コード */
	private String cbkCode = "";

	/** 計上部門コード */
	private String depCode = "";

	/** 伝票日付(以下) */
	private Date underDenDate;

	/** 精算予定日(以下) */
	private Date underSsyDate;

	/** ソート */
	private String orderBy = "DEN_DATE,DEN_NO,SWK_GYO_NO";

	/** 社員コード */
	private String empCodeLike = "";

	/** 部門コード */
	private String depCodeLike = "";

	/** 伝票日付（開始） */
	private Date denDateBegin;

	/** 伝票日付（終了） */
	private Date denDateEnd;

	/** 支払日（開始） */
	private Date sihaDateBegin;

	/** 支払日（終了） */
	private Date sihaDateEnd;

	/**
	 * データ区分を取得する
	 * 
	 * @return データ区分
	 */
	public String[] getDataKbn() {
		return dataKbn;
	}

	/**
	 * データ区分を設定する
	 * 
	 * @param data_kbn
	 */
	public void setDataKbn(String[] data_kbn) {
		dataKbn = data_kbn;
	}

	/**
	 * 伝票日付を取得する
	 * 
	 * @return 伝票日付
	 */
	public Date getDenDate() {
		return denDate;
	}

	/**
	 * 伝票データを設定する。
	 * 
	 * @param den_dete
	 */
	public void setDenDate(Date den_dete) {
		denDate = den_dete;
	}

	/**
	 * 伝票番号を取得する
	 * 
	 * @return 伝票番号
	 */
	public String getDenNo() {
		return denNo;
	}

	/**
	 * 伝票番号を設定する
	 * 
	 * @param den_no
	 */
	public void setDenNo(String den_no) {
		denNo = den_no;
	}

	/**
	 * 社員コードを取得する
	 * 
	 * @return 社員コード
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * 社員コードを設定する
	 * 
	 * @param emp_code
	 */
	public void setEmpCode(String emp_code) {
		empCode = emp_code;
	}

	/**
	 * 支払方法内部コードを設定する
	 * 
	 * @return 支払方法内部コード
	 */
	public String getHohNaiCode() {
		return hohNaiCode;
	}

	/**
	 * 支払方法内部コードを取得する
	 * 
	 * @param hoh_nai_code
	 */
	public void setHohNaiCode(String hoh_nai_code) {
		hohNaiCode = hoh_nai_code;
	}

	/**
	 * 会社コードを取得する
	 * 
	 * @return 会社コード
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	/**
	 * 会社コードを設定する
	 * 
	 * @param kai_code
	 */
	public void setKaiCode(String kai_code) {
		kaiCode = kai_code;
	}

	/**
	 * 支払決済区分を指定する。
	 * 
	 * @return 支払決済区分
	 */
	public int[] getKesaiKbn() {
		return kesaiKbn;
	}

	/**
	 * 支払決済区分を設定する
	 * 
	 * @param kesai_kbn
	 */
	public void setKesaiKbn(int[] kesai_kbn) {
		kesaiKbn = kesai_kbn;
	}

	/**
	 * 社員名を取得する
	 * 
	 * @return 社員名
	 */
	public String getLikeEmpName() {

		if (Util.isNullOrEmpty(likeEmpName)) {
			return "";
		}

		return DBUtil.getLikeStatement(likeEmpName, DBUtil.UNICODE_CHAR);
	}

	/**
	 * 社員名を設定する
	 * 
	 * @param emp_name
	 */
	public void setLikeEmpName(String emp_name) {
		likeEmpName = emp_name;
	}

	/**
	 * 伝票番号(あいまい検索)を取得する
	 * 
	 * @return 伝票番号(あいまい検索)
	 */
	public String getLikeDenNo() {

		if (Util.isNullOrEmpty(likeDenNo)) {
			return "";
		}

		return DBUtil.getLikeStatement(likeDenNo, DBUtil.NORMAL_CHAR);
	}

	/**
	 * 伝票番号(あいまい検索)を設定する
	 * 
	 * @param like_den_no
	 */
	public void setLikeDenNo(String like_den_no) {
		likeDenNo = like_den_no;
	}

	/**
	 * 摘要(あいまい検索)を取得する
	 * 
	 * @return 摘要(あいまい検索)
	 */
	public String getLikeDenTek() {

		if (Util.isNullOrEmpty(likeDenTek)) {
			return "";
		}

		return DBUtil.getLikeStatement(likeDenTek, DBUtil.UNICODE_CHAR);
	}

	/**
	 * 摘要(あいまい検索)を設定する
	 * 
	 * @param like_den_tek
	 */
	public void setLikeDenTek(String like_den_tek) {
		likeDenTek = like_den_tek;
	}

	/**
	 * 支払金額(あいまい検索)を設定する
	 * 
	 * @return 支払金額(あいまい検索)
	 */
	public String getLikeSihaKin() {

		if (Util.isNullOrEmpty(likeSihaKin)) {
			return "";
		}

		return "%" + likeSihaKin + "%";
	}

	/**
	 * @param likeSihaKin
	 */
	public void setLikeSihaKin(String likeSihaKin) {
		this.likeSihaKin = likeSihaKin;
	}

	/**
	 * 支払金額(あいまい検索)を取得する
	 * 
	 * @param kin
	 */
	public void setLikeSihaKinValue(BigDecimal kin) {
		likeSihaKin = kin.toPlainString();
	}

	/**
	 * 支払日を取得する
	 * 
	 * @return 支払日
	 */
	public Date getSihaDate() {
		return sihaDate;
	}

	/**
	 * 支払日を設定する
	 * 
	 * @param siha_date
	 */
	public void setSihaDate(Date siha_date) {
		sihaDate = siha_date;
	}

	/**
	 * 更新区分を取得する
	 * 
	 * @return 更新区分
	 */
	public int[] getUpdKbn() {
		return updKbn;
	}

	/**
	 * 更新区分を設定する
	 * 
	 * @param upd_kbn
	 */
	public void setUpdKbn(int[] upd_kbn) {
		updKbn = upd_kbn;
	}

	/**
	 * 排他区分を取得する
	 * 
	 * @return 排他区分
	 */
	public String getShrKbn() {
		return shrKbn;
	}

	/**
	 * 排他区分
	 * 
	 * @param shrKbn
	 */
	public void setShrKbn(String shrKbn) {
		this.shrKbn = shrKbn;
	}

	/**
	 * 排他区分
	 * 
	 * @param shrKbn
	 */
	public void setIsShrKbn(boolean shrKbn) {
		this.shrKbn = BooleanUtil.toString(shrKbn);
	}

	/**
	 * ソートを取得する
	 * 
	 * @return オーダー
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * ソートを設定する
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 支払方法マスタ.内部コード(配列)を取得する
	 * 
	 * @return 支払方法マスタ.内部コード(配列)
	 */
	public String[] getHohNaiCodes() {
		return hohNaiCodes;
	}

	/**
	 * 支払方法マスタ.内部コード(配列)を設定する
	 * 
	 * @param hohNaiCodes
	 */
	public void setHohNaiCodes(String[] hohNaiCodes) {
		this.hohNaiCodes = hohNaiCodes;
	}

	/**
	 * 仮払精算伝票番号を設定
	 * 
	 * @return 仮払精算伝票番号(排他条件で設定)
	 */
	public String getNotHaitaKariCrDenNo() {
		return notHaitaKariCrDenNo;
	}

	/**
	 * 仮払精算伝票番号を設定する
	 * 
	 * @param kariCrDenNo
	 */
	public void setNotHaitaKariCrDenNo(String kariCrDenNo) {
		this.notHaitaKariCrDenNo = kariCrDenNo;
	}

	/**
	 * 仮払申請伝票番号
	 * 
	 * @return 仮払申請伝票番号
	 */
	public String getKariDrDenNo() {
		return kariDrDenNo;
	}

	/**
	 * 仮払申請伝票番号
	 * 
	 * @param kariDrDenNo
	 */
	public void setKariDrDenNo(String kariDrDenNo) {
		this.kariDrDenNo = kariDrDenNo;
	}

	/**
	 * 計上部門コードを取得する
	 * 
	 * @return 計上部門コード
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * 計上部門コードを設定する
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * 伝票日付(以下)を取得する
	 * 
	 * @return 伝票日付（以下）
	 */
	public Date getUnderDenDate() {
		return underDenDate;
	}

	/**
	 * 伝票日付(以下)を設定する
	 * 
	 * @param underDenDate
	 */
	public void setUnderDenDate(Date underDenDate) {
		this.underDenDate = underDenDate;
	}

	/**
	 * 精算予定日(以下)を取得する
	 * 
	 * @return 伝票日付（以下）
	 */
	public Date getUnderSsyDate() {
		return underSsyDate;
	}

	/**
	 * 精算予定日(以下)を設定する
	 * 
	 * @param underSsyDate
	 */
	public void setUnderSsyDate(Date underSsyDate) {
		this.underSsyDate = underSsyDate;
	}

	/**
	 * 受付番号を取得する
	 * 
	 * @return 受付番号
	 */
	public String getUtkNo() {
		return utkNo;
	}

	/**
	 * 受付番号を設定する
	 * 
	 * @param utkNo
	 */
	public void setUtkNo(String utkNo) {
		this.utkNo = utkNo;
	}

	/**
	 * 銀行口座コードを取得する
	 * 
	 * @return 銀行口座
	 */
	public String getCbkCode() {
		return cbkCode;
	}

	/**
	 * 銀行口座コードを設定する
	 * 
	 * @param cbkCode
	 */
	public void setCbkCode(String cbkCode) {
		this.cbkCode = cbkCode;
	}

	/**
	 * 支払方法コードを取得する
	 * 
	 * @return 支払方法コード
	 */
	public String getHohCode() {
		return hohCode;
	}

	/**
	 * 支払方法コードを設定する
	 * 
	 * @param hohCode
	 */
	public void setHohCode(String hohCode) {
		this.hohCode = hohCode;
	}

	/**
	 * 支払金額 (NOT条件)を取得する
	 * 
	 * @return 支払金額 (NOT条件)
	 */
	public BigDecimal getNotSihaKin() {
		return notSihaKin;
	}

	/**
	 * 支払金額 (NOT条件)を設定する
	 * 
	 * @param notSihaKin
	 */
	public void setNotSihaKin(BigDecimal notSihaKin) {
		this.notSihaKin = notSihaKin;
	}

	/**
	 * 伝票種別コードを取得する
	 * 
	 * @return 伝票種別コード
	 */
	public String[] getDenSyuCode() {
		return denSyuCode;
	}

	/**
	 * 伝票種別コードを設定する
	 * 
	 * @param denSyuCode
	 */
	public void setDenSyuCode(String[] denSyuCode) {
		this.denSyuCode = denSyuCode;
	}

	/**
	 * 仕訳インターフェイス区分を取得する
	 * 
	 * @return 仕訳インターフェイス区分
	 */
	public String getSwkInKbn() {
		return swkInKbn;
	}

	/**
	 * 仕訳インターフェイス区分を設定する
	 * 
	 * @param swkInKbn
	 */
	public void setSwkInKbn(String swkInKbn) {
		this.swkInKbn = swkInKbn;
	}

	/**
	 * 会社間付替区分を取得する
	 * 
	 * @return 会社間付替区分
	 */
	public String getTukeKbn() {
		return tukeKbn;
	}

	/**
	 * 会社間付替区分を設定する
	 * 
	 * @param tukeKbn
	 */
	public void setTukeKbn(String tukeKbn) {
		this.tukeKbn = tukeKbn;
	}

	/**
	 * 依頼者コードを取得する
	 * 
	 * @return 依頼者コード
	 */
	public String getIraiEmpCode() {
		return iraiEmpCode;
	}

	/**
	 * 依頼者コードを設定する
	 * 
	 * @param iraiEmpCode
	 */
	public void setIraiEmpCode(String iraiEmpCode) {
		this.iraiEmpCode = iraiEmpCode;
	}

	/**
	 * 依頼部門コードを取得する
	 * 
	 * @return 依頼部門コード
	 */
	public String getIraiDepCode() {
		return iraiDepCode;
	}

	/**
	 * 依頼部門コードを設定する
	 * 
	 * @param iraiDepCode
	 */
	public void setIraiDepCode(String iraiDepCode) {
		this.iraiDepCode = iraiDepCode;
	}

	/**
	 * 依頼者（orNull検索）を取得する
	 * 
	 * @return 依頼者（orNull検索）
	 */
	public String getIraiEmpCodeAndNull() {
		return iraiEmpCodeAndNull;
	}

	/**
	 * 依頼者（orNull検索）を設定する
	 * 
	 * @param iraiEmpCodeAndNull
	 */
	public void setIraiEmpCodeAndNull(String iraiEmpCodeAndNull) {
		this.iraiEmpCodeAndNull = iraiEmpCodeAndNull;
	}

	/**
	 * depCodeLike取得
	 * 
	 * @return depCodeLike
	 */
	public String getDepCodeLike() {
		if (Util.isNullOrEmpty(depCodeLike)) {
			return "";
		}

		return DBUtil.getLikeStatement(depCodeLike, DBUtil.UNICODE_CHAR);
	}

	/**
	 * depCodeLike設定
	 * 
	 * @param depCodeLike
	 */
	public void setDepCodeLike(String depCodeLike) {
		this.depCodeLike = depCodeLike;
	}

	/**
	 * empCodeLike取得
	 * 
	 * @return empCodeLike
	 */
	public String getEmpCodeLike() {

		if (Util.isNullOrEmpty(empCodeLike)) {
			return "";
		}

		return DBUtil.getLikeStatement(empCodeLike, DBUtil.UNICODE_CHAR);
	}

	/**
	 * empCodeLike設定
	 * 
	 * @param empCodeLike
	 */
	public void setEmpCodeLike(String empCodeLike) {
		this.empCodeLike = empCodeLike;
	}

	/**
	 * denDateBegin取得
	 * 
	 * @return denDateBegin
	 */
	public Date getDenDateBegin() {
		return denDateBegin;
	}

	/**
	 * denDateBegin設定
	 * 
	 * @param denDateBegin
	 */
	public void setDenDateBegin(Date denDateBegin) {
		this.denDateBegin = denDateBegin;
	}

	/**
	 * denDateEnd取得
	 * 
	 * @return denDateEnd
	 */
	public Date getDenDateEnd() {
		return denDateEnd;
	}

	/**
	 * denDateEnd設定
	 * 
	 * @param denDateEnd
	 */
	public void setDenDateEnd(Date denDateEnd) {
		this.denDateEnd = denDateEnd;
	}

	/**
	 * sihaDateBegin取得
	 * 
	 * @return sihaDateBegin
	 */
	public Date getSihaDateBegin() {
		return sihaDateBegin;
	}

	/**
	 * sihaDateBegin設定
	 * 
	 * @param sihaDateBegin
	 */
	public void setSihaDateBegin(Date sihaDateBegin) {
		this.sihaDateBegin = sihaDateBegin;
	}

	/**
	 * sihaDateEnd取得
	 * 
	 * @return sihaDateEnd
	 */
	public Date getSihaDateEnd() {
		return sihaDateEnd;
	}

	/**
	 * sihaDateEnd設定
	 * 
	 * @param sihaDateEnd
	 */
	public void setSihaDateEnd(Date sihaDateEnd) {
		this.sihaDateEnd = sihaDateEnd;
	}

}
