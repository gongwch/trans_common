package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 伝票条件
 */
public class SlipCondition extends TransferBase implements Cloneable {

	/** ソート：会社コード */
	public static final String SORT_COMPANY_CODE = "KAI_CODE";

	/** ソート：伝票日付 */
	public static final String SORT_SLIP_DATE = "SWK_DEN_DATE";

	/** ソート：伝票番号 */
	public static final String SORT_SLIP_NO = "SWK_DEN_NO";

	/** ソート：伝票更新回数 */
	public static final String SORT_SLIP_UPDATE_COUNT = "SWK_UPD_CNT";

	/** ソート：行番号 */
	public static final String SORT_LINE_NO = "SWK_GYO_NO";

	/** ソート：部門コード */
	public static final String SORT_DEPARTMENT_CODE = "SWK_DEP_CODE";

	/** ソート：部門略称 */
	public static final String SORT_DEPARTMENT_NAME_S = "SWK_DEP_NAME_S";

	/** ソート：取引先コード */
	public static final String SORT_CUSTOMER_CODE = "SWK_TRI_CODE";

	/** ソート：取引先略称 */
	public static final String SORT_CUSTOMER_NAME_S = "SWK_TRI_NAME_S";

	/** ソート：補助科目略称 */
	public static final String SORT_SUB_ITEM_NAME_S = "SWK_HKM_NAME_S";

	/** ソート：補助科目コード */
	public static final String SORT_SUB_ITEM_CODE = "SWK_HKM_CODE";

	/** ソート：管理1略称 */
	public static final String SORT_MANEGEMENT1_NAME_S = "SWK_KNR_NAME_S_1";

	/** ソート：管理1コード */
	public static final String SORT_MANEGEMENT1_CODE = "SWK_KNR_CODE_1";

	/** ソート：管理2略称 */
	public static final String SORT_MANEGEMENT2_NAME_S = "SWK_KNR_NAME_S_2";

	/** ソート：管理2コード */
	public static final String SORT_MANEGEMENT2_CODE = "SWK_KNR_CODE_2";

	/** ソート：管理3略称 */
	public static final String SORT_MANEGEMENT3_NAME_S = "SWK_KNR_NAME_S_3";

	/** ソート：管理3コード */
	public static final String SORT_MANEGEMENT3_CODE = "SWK_KNR_CODE_3";

	/** ソート：管理4略称 */
	public static final String SORT_MANEGEMENT4_NAME_S = "SWK_KNR_NAME_S_4";

	/** ソート：管理4コード */
	public static final String SORT_MANEGEMENT4_CODE = "SWK_KNR_CODE_4";

	/** ソート：管理5略称 */
	public static final String SORT_MANEGEMENT5_NAME_S = "SWK_KNR_NAME_S_5";

	/** ソート：管理5コード */
	public static final String SORT_MANEGEMENT5_CODE = "SWK_KNR_CODE_5";

	/** ソート：管理6略称 */
	public static final String SORT_MANEGEMENT6_NAME_S = "SWK_KNR_NAME_S_6";

	/** ソート：管理6コード */
	public static final String SORT_MANEGEMENT6_CODE = "SWK_KNR_CODE_6";

	/** ソート：非会計明細１ */
	public static final String SORT_HM_1 = "SWK_HM_1";

	/** ソート：通貨コード */
	public static final String SORT_CUR_CODE = "SWK_CUR_CODE";

	/** ソート：DC区分 */
	public static final String SORT_DC_KBN = "SWK_DC_KBN";

	/** ソート：レート */
	public static final String SORT_RATE = "SWK_CUR_RATE";

	/** ソート：入力金額 */
	public static final String SORT_INPUT_AMOUNT = "SWK_IN_KIN";

	/** ソート：借方邦貨金額 */
	public static final String SORT_DEBIT_AMOUNT = "CASE WHEN SWK_DC_KBN = 0 THEN SWK_KIN ELSE 0 END";

	/** ソート：貸方邦貨金額 */
	public static final String SORT_CREDIT_AMOUNT = "CASE WHEN SWK_DC_KBN = 1 THEN SWK_KIN ELSE 0 END";

	/** ソート：依頼部門コード */
	public static final String SORT_INPUT_DEPARTMENT_CODE = "SWK_IRAI_DEP_CODE";

	/** ソート：依頼者コード */
	public static final String SORT_INPUT_EMPLOYEE_CODE = "SWK_IRAI_EMP_CODE";

	/** 会社コード */
	protected String companyCode;

	/** 会社 */
	protected Company company;

	/** 伝票番号 */
	protected String slipNo;

	/** BOOK No.(1:通常仕訳 2:機能通貨仕訳) */
	protected int bookNo = 0;

	/** IFRS調整区分(0:両方 1:自国のみ 2:IFRSのみ) */
	protected List<Integer> adjDivisionList = new LinkedList<Integer>();

	/** 排他フラグ(０：通常 １：更新中) */
	protected int lockState = -1;

	/** 会社間付替伝票区分(0:通常 1:付替先伝票) */
	protected int groupAccountDivision = -1;

	/** 伝票番号(複数) */
	protected List<String> slipNoList = new LinkedList<String>();

	/** 伝票番号(ライク) */
	protected String slipNoLike;

	/** 伝票番号（開始） */
	protected String slipNoFrom;

	/** 伝票番号（終了） */
	protected String slipNoTo;

	/** データ区分(複数) */
	protected List<String> dataKindList = new LinkedList<String>();

	/** 伝票種別(複数) */
	protected List<String> slipTypeList = new LinkedList<String>();

	/** 更新区分(複数) */
	protected List<Integer> slipStateList = new LinkedList<Integer>();

	/** 伝票日付 */
	protected Date slipDate;

	/** 伝票日付FROM */
	protected Date slipDateFrom;

	/** 伝票日付TO */
	protected Date slipDateTo;

	/** 依頼日FROM */
	protected Date requestDateFrom;

	/** 依頼日TO */
	protected Date requestDateTo;

	/** 依頼部門コード */
	protected String requestDeptCode;

	/** 依頼者コード */
	protected String requestEmpCode;

	/** 支払日FROM */
	protected Date paymentDayFrom;

	/** 支払日TO */
	protected Date paymentDayTo;

	/** 入金日FROM */
	protected Date receiveDayFrom;

	/** 入金日TO */
	protected Date receiveDayTo;

	/** 出納日FROM */
	protected Date cashDayFrom;

	/** 出納日TO */
	protected Date cashDayTo;

	/** 社員(ライク) */
	protected String employeeNamesLike;

	/** 摘要コード */
	protected String remarkCode;

	/** 摘要(ライク) */
	protected String remarksLike;

	/** 科目コード */
	protected String itemCode;

	/** 補助科目コード */
	protected String subItemCode;

	/** 内訳科目コード */
	protected String detailItemCode;

	/** 計上部門コード */
	protected String departmentCode = null;

	/** 計上部門コード(ライク) */
	protected String departmentCodeLike = null;

	/** 計上部門略称(ライク) */
	protected String departmentNamesLike = null;

	/** 通貨コード */
	protected String currencyCode;

	/** 通貨コードリスト */
	protected List<String> currencyCodeList = new LinkedList<String>();

	/** 取引先コード */
	protected String custmorCode;

	/** 管理1コード */
	protected String manegement1Code;

	/** 管理2コード */
	protected String manegement2Code;

	/** 管理3コード */
	protected String manegement3Code;

	/** 管理4コード */
	protected String manegement4Code;

	/** 管理5コード */
	protected String manegement5Code;

	/** 管理6コード */
	protected String manegement6Code;

	/** 取引先略称ライク */
	protected String custmorNamesLike;

	/** 消込番号 */
	protected String eraseNo;

	/** 消込番号ライク */
	protected String eraseNoLike;

	/** 消込日FROM */
	protected Date eraseDayFrom;

	/** 消込日TO */
	protected Date eraseDayTo;

	/** 相殺伝票番号ライク */
	protected String offsetSlipNoLike;

	/** プログラムID */
	protected String programCode;

	/** ユーザーID */
	protected String userCode;

	/** ソート順 */
	protected List<String> order = new LinkedList<String>();

	/** 自動仕訳区分 */
	protected int autoDivision = -1;

	/** 仕訳インターフェース区分 */
	protected int ifDivision = -1;

	/** NULLの依頼従業員コードも含めるかどうか */
	protected boolean requestEmpCodeIncledNull = false;

	/** NULLの依頼部門コードも含めるかどうか */
	protected boolean requestDeptCodeIncledNull = false;

	/** 集計取引先で検索するかどうか */
	protected boolean searchSumCustomer = false;

	/** 他シス連携伝票を含める */
	protected boolean includeOtherSystem = false;

	/** 登録日FROM */
	protected Date entryDateFrom;

	/** 登録日TO */
	protected Date entryDateTo;

	/** 更新日FROM */
	protected Date updateDateFrom;

	/** 概算取消フラグ */
	protected boolean estimateCancelFlg = false;

	/** 最大ヘッダー取得件数 */
	protected int maxHeaderCount = -1;

	/** ワークフロー承認用の検索かどうか */
	protected boolean searchWorkFlow = false;

	/** ワークフロー承認 承認グループ */
	protected List<String> aprvRoleGroupList;

	/** ワークフロー承認 承認ロール */
	protected List<String> aprvRoleList;

	/**
	 * クローン
	 */
	@Override
	public SlipCondition clone() {
		try {
			return (SlipCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * LIKE文変換した条件にコンバート
	 * 
	 * @return 条件クラス
	 */
	public SlipCondition toSQL() {
		SlipCondition clone = this.clone();
		clone.setSlipNoLike(SQLUtil.getLikeStatement(slipNoLike, SQLUtil.CHAR_FRONT));
		clone.setEraseNoLike(SQLUtil.getLikeStatement(eraseNoLike, SQLUtil.CHAR_FRONT));
		clone.setOffsetSlipNoLike(SQLUtil.getLikeStatement(offsetSlipNoLike, SQLUtil.CHAR_FRONT));
		clone.setRemarksLike(SQLUtil.getLikeStatement(remarksLike, SQLUtil.NCHAR_AMBI));
		clone.setDepartmentCodeLike(SQLUtil.getLikeStatement(departmentCodeLike, SQLUtil.CHAR_FRONT));
		clone.setDepartmentNamesLike(SQLUtil.getLikeStatement(departmentNamesLike, SQLUtil.NCHAR_AMBI));

		return clone;
	}

	/**
	 * 伝票番号(複数)
	 * 
	 * @return 伝票番号(複数)
	 */
	public String[] getSlipNoList() {
		if (slipNoList.isEmpty()) {
			return null;
		}

		return slipNoList.toArray(new String[slipNoList.size()]);
	}

	/**
	 * 伝票番号(複数)
	 * 
	 * @param slipNoList 伝票番号(複数)
	 */
	public void setSlipNoList(String[] slipNoList) {
		addSlipNo(slipNoList);
	}

	/**
	 * 伝票番号(複数)
	 * 
	 * @param slipno 伝票番号
	 */
	public void addSlipNo(String... slipno) {
		for (String no : slipno) {
			this.slipNoList.add(no);
		}
	}

	/**
	 * 伝票番号(ライク)
	 * 
	 * @return 伝票番号(ライク)
	 */
	public String getSlipNoLike() {
		return slipNoLike;
	}

	/**
	 * 伝票番号(ライク)
	 * 
	 * @param likeSlipNo 伝票番号(ライク)
	 */
	public void setSlipNoLike(String likeSlipNo) {
		this.slipNoLike = likeSlipNo;
	}

	/**
	 * データ区分(複数)
	 * 
	 * @return 伝票番号(複数)
	 */
	public String[] getDataKindList() {
		if (dataKindList.isEmpty()) {
			return null;
		}

		return dataKindList.toArray(new String[dataKindList.size()]);
	}

	/**
	 * データ区分(複数)
	 * 
	 * @param dataKindList データ区分(複数)
	 */
	public void setDataKindList(String[] dataKindList) {
		addDataKind(dataKindList);
	}

	/**
	 * データ区分(複数)
	 * 
	 * @param dataKind データ区分(複数)
	 */
	public void addDataKind(String... dataKind) {
		for (String kind : dataKind) {
			this.dataKindList.add(kind);
		}
	}

	/**
	 * 伝票種別(複数)
	 * 
	 * @return 伝票種別(複数)
	 */
	public String[] getSlipTypeList() {
		if (slipTypeList.isEmpty()) {
			return null;
		}

		return slipTypeList.toArray(new String[slipTypeList.size()]);
	}

	/**
	 * 伝票種別(複数)
	 * 
	 * @param slipTypeList 伝票種別(複数)
	 */
	public void setSlipTypeList(String[] slipTypeList) {
		addSlipType(slipTypeList);
	}

	/**
	 * 伝票種別(複数)
	 * 
	 * @param slipTypeList 伝票種別(複数)
	 */
	public void setSlipTypeList(List<String> slipTypeList) {
		this.slipTypeList = slipTypeList;
	}

	/**
	 * 伝票種別(複数)
	 * 
	 * @param slipType 伝票種別(複数)
	 */
	public void addSlipType(String... slipType) {
		for (String no : slipType) {
			this.slipTypeList.add(no);
		}
	}

	/**
	 * 更新区分(複数)
	 * 
	 * @return 更新区分(複数)
	 */
	public int[] getSlipStateList() {
		if (slipStateList.isEmpty()) {
			return null;
		}

		int[] array = new int[slipStateList.size()];
		for (int i = 0; i < slipStateList.size(); i++) {
			array[i] = slipStateList.get(i);
		}

		return array;
	}

	/**
	 * 更新区分(複数)
	 * 
	 * @param slipStateList 更新区分(複数)
	 */
	public void setSlipStateList(int[] slipStateList) {
		addSlipState(slipStateList);
	}

	/**
	 * 更新区分(複数)
	 * 
	 * @param slipState 更新区分(複数)
	 */
	public void addSlipState(int... slipState) {
		for (int state : slipState) {
			this.slipStateList.add(state);
		}
	}

	/**
	 * 更新区分(複数)
	 * 
	 * @param slipState 更新区分(複数)
	 */
	public void addSlipState(SlipState... slipState) {
		for (SlipState state : slipState) {
			this.slipStateList.add(state.value);
		}
	}

	/**
	 * 更新区分(複数)
	 * 
	 * @param slipState 更新区分(複数)
	 */
	public void setSlipStateList(List<SlipState> slipState) {
		for (SlipState state : slipState) {
			this.slipStateList.add(state.value);
		}
	}

	/**
	 * 伝票日付FROM
	 * 
	 * @return 伝票日付FROM
	 */
	public Date getSlipDateFrom() {
		return slipDateFrom;
	}

	/**
	 * 伝票日付FROM
	 * 
	 * @param fromSlipDate 伝票日付FROM
	 */
	public void setSlipDateFrom(Date fromSlipDate) {
		this.slipDateFrom = fromSlipDate;
	}

	/**
	 * 伝票日付TO
	 * 
	 * @return 伝票日付TO
	 */
	public Date getSlipDateTo() {
		return slipDateTo;
	}

	/**
	 * 伝票日付TO
	 * 
	 * @param toSlipDate 伝票日付TO
	 */
	public void setSlipDateTo(Date toSlipDate) {
		this.slipDateTo = toSlipDate;
	}

	/**
	 * ソートを取得する
	 * 
	 * @return ソート
	 */
	public String getOrder() {

		String sort = "";

		for (String column : order) {

			if (Util.isNullOrEmpty(sort)) {
				sort = column;

			} else {
				sort += "," + column;
			}
		}

		return sort;
	}

	/**
	 * ソートを設定する
	 * 
	 * @param orderBy
	 */
	public void setOrder(String orderBy) {
		addOrder(orderBy);
	}

	/**
	 * ソートの追加
	 * 
	 * @param sorts ソート
	 */
	public void addOrder(String... sorts) {
		for (String sort : sorts) {
			this.order.add(sort);
		}
	}

	/**
	 * ソート順のクリア
	 */
	public void clearOrder() {
		this.order.clear();
	}

	/**
	 * 依頼日FROM
	 * 
	 * @return 依頼日FROM
	 */
	public Date getRequestDateFrom() {
		return requestDateFrom;
	}

	/**
	 * 依頼日FROM
	 * 
	 * @param requestDateFrom 依頼日FROM
	 */
	public void setRequestDateFrom(Date requestDateFrom) {
		this.requestDateFrom = requestDateFrom;
	}

	/**
	 * 依頼日TO
	 * 
	 * @return 依頼日TO
	 */
	public Date getRequestDateTo() {
		return requestDateTo;
	}

	/**
	 * 依頼日TO
	 * 
	 * @param requestDateTo 依頼日TO
	 */
	public void setRequestDateTo(Date requestDateTo) {
		this.requestDateTo = requestDateTo;
	}

	/**
	 * 依頼部門コード
	 * 
	 * @return 依頼部門コード
	 */
	public String getRequestDeptCode() {
		return requestDeptCode;
	}

	/**
	 * 依頼部門コード
	 * 
	 * @param requestDeptCode 依頼部門コード
	 */
	public void setRequestDeptCode(String requestDeptCode) {
		this.requestDeptCode = requestDeptCode;
	}

	/**
	 * 依頼者コード
	 * 
	 * @return 依頼者コード
	 */
	public String getRequestEmpCode() {
		return requestEmpCode;
	}

	/**
	 * 依頼者コード
	 * 
	 * @param requestEmpCode 依頼者コード
	 */
	public void setRequestEmpCode(String requestEmpCode) {
		this.requestEmpCode = requestEmpCode;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * BOOK No.
	 * 
	 * @return BOOK No.
	 */
	public int getBookNo() {
		return bookNo;
	}

	/**
	 * BOOK No.
	 * 
	 * @param bookNo BOOK No.
	 */
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	/**
	 * 通貨タイプ
	 * 
	 * @param type 通貨タイプ
	 */
	public void setCurrencyType(CurrencyType type) {
		this.bookNo = type.value;
	}

	/**
	 * 伝票番号
	 * 
	 * @return 伝票番号
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * 伝票番号
	 * 
	 * @param slipNo 伝票番号
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * 支払/入金日FROM
	 * 
	 * @return 支払/入金日FROM
	 */
	public Date getPaymentDayFrom() {
		return paymentDayFrom;
	}

	/**
	 * 支払日FROM
	 * 
	 * @param paymentDayFrom 支払日FROM
	 */
	public void setPaymentDayFrom(Date paymentDayFrom) {
		this.paymentDayFrom = paymentDayFrom;
	}

	/**
	 * 支払日TO
	 * 
	 * @return 支払日TO
	 */
	public Date getPaymentDayTo() {
		return paymentDayTo;
	}

	/**
	 * 支払/入金日TO
	 * 
	 * @param paymentDayTo 支払/入金日TO
	 */
	public void setPaymentDayTo(Date paymentDayTo) {
		this.paymentDayTo = paymentDayTo;
	}

	/**
	 * 伝票番号（開始）
	 * 
	 * @return 伝票番号（開始）
	 */
	public String getSlipNoFrom() {
		return slipNoFrom;
	}

	/**
	 * 伝票番号（開始）
	 * 
	 * @param slipNoFrom 伝票番号（開始）
	 */
	public void setSlipNoFrom(String slipNoFrom) {
		this.slipNoFrom = slipNoFrom;
	}

	/**
	 * 伝票番号（終了）
	 * 
	 * @return 伝票番号（終了）
	 */
	public String getSlipNoTo() {
		return slipNoTo;
	}

	/**
	 * 伝票番号（終了）
	 * 
	 * @param slipNoTo 伝票番号（終了）
	 */
	public void setSlipNoTo(String slipNoTo) {
		this.slipNoTo = slipNoTo;
	}

	/**
	 * IFRS調整区分
	 * 
	 * @return IFRS調整区分
	 */
	public int[] getAdjDivisionList() {

		if (adjDivisionList.isEmpty()) {
			return null;
		}

		int[] array = new int[adjDivisionList.size()];
		for (int i = 0; i < adjDivisionList.size(); i++) {
			array[i] = adjDivisionList.get(i);
		}

		return array;
	}

	/**
	 * IFRS調整区分
	 * 
	 * @param adjDivisionList IFRS調整区分
	 */
	public void setAdjDivisionList(int[] adjDivisionList) {
		this.adjDivisionList.clear();
		for (int adjDivision : adjDivisionList) {
			this.adjDivisionList.add(adjDivision);
		}
	}

	/**
	 * 帳簿タイプ
	 * 
	 * @param types タイプ
	 */
	public void setAccountBook(AccountBook... types) {
		for (AccountBook type : types) {
			this.adjDivisionList.add(type.value);
		}
	}

	/**
	 * groupAccountDivisionを取得する。
	 * 
	 * @return int groupAccountDivision
	 */
	public int getGroupAccountDivision() {
		return groupAccountDivision;
	}

	/**
	 * groupAccountDivisionを設定する。
	 * 
	 * @param groupAccountDivision
	 */
	public void setGroupAccountDivision(int groupAccountDivision) {
		this.groupAccountDivision = groupAccountDivision;
	}

	/**
	 * 付替伝票は除く
	 */
	public void setGroupAccountOff() {
		this.groupAccountDivision = 0;
	}

	/**
	 * 排他区分を取得する。
	 * 
	 * @return 排他区分
	 */
	public int getLockState() {
		return lockState;
	}

	/**
	 * 排他区分を設定する。
	 * 
	 * @param lockState 排他区分
	 */
	public void setLockState(int lockState) {
		this.lockState = lockState;
	}

	/**
	 * 排他されていない
	 */
	public void setNotLocked() {
		this.lockState = Slip.SHR_KBN.NON_LOCKED;
	}

	/**
	 * 社員(ライク)
	 * 
	 * @param text 社員(ライク)
	 */
	public void setEmployeeNamesLike(String text) {
		this.employeeNamesLike = text;
	}

	/**
	 * 社員(ライク)
	 * 
	 * @return 社員(ライク)
	 */
	public String getEmployeeNamesLike() {
		return this.employeeNamesLike;
	}

	/**
	 * 摘要コードを取得する。
	 * 
	 * @return remarkCode 摘要コード
	 */
	public String getRemarkCode() {
		return remarkCode;
	}

	/**
	 * 摘要コードを設定する。
	 * 
	 * @param remarkCode 摘要コード
	 */
	public void setRemarkCode(String remarkCode) {
		this.remarkCode = remarkCode;
	}

	/**
	 * 摘要(ライク)
	 * 
	 * @param text 摘要(ライク)
	 */
	public void setRemarksLike(String text) {
		this.remarksLike = text;
	}

	/**
	 * 摘要(ライク)
	 * 
	 * @return 摘要(ライク)
	 */
	public String getRemarksLike() {
		return this.remarksLike;
	}

	/**
	 * 自動仕訳区分
	 * 
	 * @param auto 自動仕訳区分
	 */
	public void setAutoDivision(int auto) {
		this.autoDivision = auto;
	}

	/**
	 * 自動仕訳区分
	 * 
	 * @return 自動仕訳区分
	 */
	public int getAutoDivision() {
		return autoDivision;
	}

	/**
	 * custmorCodeを取得する。
	 * 
	 * @return String custmorCode
	 */
	public String getCustmorCode() {
		return custmorCode;
	}

	/**
	 * custmorCodeを設定する。
	 * 
	 * @param custmorCode
	 */
	public void setCustmorCode(String custmorCode) {
		this.custmorCode = custmorCode;
	}

	/**
	 * detailItemCodeを取得する。
	 * 
	 * @return String detailItemCode
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * detailItemCodeを設定する。
	 * 
	 * @param detailItemCode
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * itemCodeを取得する。
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCodeを設定する。
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * manegement1Codeを取得する。
	 * 
	 * @return String manegement1Code
	 */
	public String getManegement1Code() {
		return manegement1Code;
	}

	/**
	 * manegement1Codeを設定する。
	 * 
	 * @param manegement1Code
	 */
	public void setManegement1Code(String manegement1Code) {
		this.manegement1Code = manegement1Code;
	}

	/**
	 * manegement2Codeを取得する。
	 * 
	 * @return String manegement2Code
	 */
	public String getManegement2Code() {
		return manegement2Code;
	}

	/**
	 * manegement2Codeを設定する。
	 * 
	 * @param manegement2Code
	 */
	public void setManegement2Code(String manegement2Code) {
		this.manegement2Code = manegement2Code;
	}

	/**
	 * manegement3Codeを取得する。
	 * 
	 * @return String manegement3Code
	 */
	public String getManegement3Code() {
		return manegement3Code;
	}

	/**
	 * manegement3Codeを設定する。
	 * 
	 * @param manegement3Code
	 */
	public void setManegement3Code(String manegement3Code) {
		this.manegement3Code = manegement3Code;
	}

	/**
	 * manegement4Codeを取得する。
	 * 
	 * @return String manegement4Code
	 */
	public String getManegement4Code() {
		return manegement4Code;
	}

	/**
	 * manegement4Codeを設定する。
	 * 
	 * @param manegement4Code
	 */
	public void setManegement4Code(String manegement4Code) {
		this.manegement4Code = manegement4Code;
	}

	/**
	 * manegement5Codeを取得する。
	 * 
	 * @return String manegement5Code
	 */
	public String getManegement5Code() {
		return manegement5Code;
	}

	/**
	 * manegement5Codeを設定する。
	 * 
	 * @param manegement5Code
	 */
	public void setManegement5Code(String manegement5Code) {
		this.manegement5Code = manegement5Code;
	}

	/**
	 * manegement6Codeを取得する。
	 * 
	 * @return String manegement6Code
	 */
	public String getManegement6Code() {
		return manegement6Code;
	}

	/**
	 * manegement6Codeを設定する。
	 * 
	 * @param manegement6Code
	 */
	public void setManegement6Code(String manegement6Code) {
		this.manegement6Code = manegement6Code;
	}

	/**
	 * subItemCodeを取得する。
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCodeを設定する。
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * dataKindListを設定する。
	 * 
	 * @param dataKindList
	 */
	public void setDataKindList(List<String> dataKindList) {
		this.dataKindList = dataKindList;
	}

	/**
	 * 仕訳インターフェース区分
	 * 
	 * @param ifDivision 更新区分
	 */
	public void setIfType(JornalIfType ifDivision) {
		this.ifDivision = ifDivision.value;
	}

	/**
	 * 仕訳インターフェース区分
	 * 
	 * @param ifDivision 仕訳インターフェース区分
	 */
	public void setIfDivision(int ifDivision) {
		this.ifDivision = ifDivision;
	}

	/**
	 * 仕訳インターフェース区分
	 * 
	 * @return 仕訳インターフェース区分
	 */
	public int getIfDivision() {
		return this.ifDivision;
	}

	/**
	 * NULLの依頼従業員コードも含めるかどうか
	 * 
	 * @param requestEmpCodeIncledNull true:含める
	 */
	public void setRequestEmpCodeIncledNull(boolean requestEmpCodeIncledNull) {
		this.requestEmpCodeIncledNull = requestEmpCodeIncledNull;
	}

	/**
	 * NULLの依頼従業員コードも含めるかどうか
	 * 
	 * @return true:含める
	 */
	public boolean isRequestEmpCodeIncledNull() {
		return requestEmpCodeIncledNull;
	}

	/**
	 * NULLの依頼部門コードも含めるかどうか
	 * 
	 * @param requestDeptCodeIncledNull true:含める
	 */
	public void setRequestDeptCodeIncledNull(boolean requestDeptCodeIncledNull) {
		this.requestDeptCodeIncledNull = requestDeptCodeIncledNull;
	}

	/**
	 * NULLの依頼部門コードも含めるかどうか
	 * 
	 * @return true:含める
	 */
	public boolean isRequestDeptCodeIncledNull() {
		return requestDeptCodeIncledNull;
	}

	/**
	 * 消込番号を取得する。
	 * 
	 * @return String 消込番号
	 */
	public String getEraseNo() {
		return eraseNo;
	}

	/**
	 * 消込番号を設定する。
	 * 
	 * @param eraceSlipNo 消込番号
	 */
	public void setEraseNo(String eraceSlipNo) {
		this.eraseNo = eraceSlipNo;
	}

	/**
	 * 消込番号ライク
	 * 
	 * @return 消込番号ライク
	 */
	public String getEraseNoLike() {
		return this.eraseNoLike;
	}

	/**
	 * 消込番号ライク
	 * 
	 * @param eraseNoLike 消込番号ライク
	 */
	public void setEraseNoLike(String eraseNoLike) {
		this.eraseNoLike = eraseNoLike;
	}

	/**
	 * 相殺伝票番号ライク
	 * 
	 * @return 相殺伝票番号ライク
	 */
	public String getOffsetSlipNoLike() {
		return this.offsetSlipNoLike;
	}

	/**
	 * 相殺伝票番号ライク
	 * 
	 * @param offsetSlipNoLike 相殺伝票番号ライク
	 */
	public void setOffsetSlipNoLike(String offsetSlipNoLike) {
		this.offsetSlipNoLike = offsetSlipNoLike;
	}

	/**
	 * programCodeを取得する。
	 * 
	 * @return String programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * programCodeを設定する。
	 * 
	 * @param programCode
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * userCodeを取得する。
	 * 
	 * @return String userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * userCodeを設定する。
	 * 
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * searchSumCustomerを取得する。
	 * 
	 * @return boolean searchSumCustomer
	 */
	public boolean isSearchSumCustomer() {
		return searchSumCustomer;
	}

	/**
	 * searchSumCustomerを設定する。
	 * 
	 * @param searchSumCustomer
	 */
	public void setSearchSumCustomer(boolean searchSumCustomer) {
		this.searchSumCustomer = searchSumCustomer;
	}

	/**
	 * currencyCodeを取得する。
	 * 
	 * @return String currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * currencyCodeを設定する。
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 計上部門コードの取得
	 * 
	 * @return departmentCode 計上部門コード
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 計上部門コードの設定
	 * 
	 * @param departmentCode 計上部門コード
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * 計上部門コード(ライク)
	 * 
	 * @return 計上部門コード(ライク)
	 */
	public String getDepartmentCodeLike() {
		return departmentCodeLike;
	}

	/**
	 * 計上部門コード(ライク)
	 * 
	 * @param departmentCodeLike 計上部門コード(ライク)
	 */
	public void setDepartmentCodeLike(String departmentCodeLike) {
		this.departmentCodeLike = departmentCodeLike;
	}

	/**
	 * 計上部門略称(ライク)
	 * 
	 * @return 計上部門略称(ライク)
	 */
	public String getDepartmentNamesLike() {
		return departmentNamesLike;
	}

	/**
	 * 計上部門略称(ライク)
	 * 
	 * @param departmentNamesLike 計上部門略称(ライク)
	 */
	public void setDepartmentNamesLike(String departmentNamesLike) {
		this.departmentNamesLike = departmentNamesLike;
	}

	/**
	 * 出納日FROM
	 * 
	 * @return 出納日FROM
	 */
	public Date getCashDayFrom() {
		return cashDayFrom;
	}

	/**
	 * 出納日FROM
	 * 
	 * @param cashDayFrom 出納日FROM
	 */
	public void setCashDayFrom(Date cashDayFrom) {
		this.cashDayFrom = cashDayFrom;
	}

	/**
	 * 出納日TO
	 * 
	 * @return 出納日TO
	 */
	public Date getCashDayTo() {
		return cashDayTo;
	}

	/**
	 * 出納日TO
	 * 
	 * @param cashDayTo 出納日TO
	 */
	public void setCashDayTo(Date cashDayTo) {
		this.cashDayTo = cashDayTo;
	}

	/**
	 * 取引先略称ライク
	 * 
	 * @return 取引先略称ライク
	 */
	public String getCustmorNamesLike() {
		return custmorNamesLike;
	}

	/**
	 * 取引先略称ライク
	 * 
	 * @param custmorNameLike 取引先略称ライク
	 */
	public void setCustmorNamesLike(String custmorNameLike) {
		this.custmorNamesLike = custmorNameLike;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 会社コード
	 * 
	 * @param company 会社コード
	 */
	public void setCompany(Company company) {
		this.company = company;
		this.companyCode = company.getCode();
	}

	/**
	 * receiveDayFromを取得する。
	 * 
	 * @return Date receiveDayFrom
	 */
	public Date getReceiveDayFrom() {
		return this.receiveDayFrom;
	}

	/**
	 * receiveDayFromを設定する。
	 * 
	 * @param receiveDayFrom
	 */
	public void setReceiveDayFrom(Date receiveDayFrom) {
		this.receiveDayFrom = receiveDayFrom;
	}

	/**
	 * receiveDayToを取得する。
	 * 
	 * @return Date receiveDayTo
	 */
	public Date getReceiveDayTo() {
		return this.receiveDayTo;
	}

	/**
	 * receiveDayToを設定する。
	 * 
	 * @param receiveDayTo
	 */
	public void setReceiveDayTo(Date receiveDayTo) {
		this.receiveDayTo = receiveDayTo;
	}

	/**
	 * 他シス連携伝票を含めるかどうか
	 * 
	 * @return true:含める
	 */
	public boolean isIncludeOtherSystem() {
		return this.includeOtherSystem;
	}

	/**
	 * 他シス連携伝票を含めるかどうか
	 * 
	 * @param includeOtherSyste true:含める
	 */
	public void setIncludeOtherSystem(boolean includeOtherSyste) {
		this.includeOtherSystem = includeOtherSyste;
	}

	/**
	 * 伝票日付
	 * 
	 * @return 伝票日付
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * 伝票日付
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * 登録日FROM
	 * 
	 * @return 登録日FROM
	 */
	public Date getEntryDateFrom() {
		return entryDateFrom;
	}

	/**
	 * 登録日FROM
	 * 
	 * @param entryDateFrom 登録日FROM
	 */
	public void setEntryDateFrom(Date entryDateFrom) {
		this.entryDateFrom = entryDateFrom;
	}

	/**
	 * 登録日TO
	 * 
	 * @return 登録日TO
	 */
	public Date getEntryDateTo() {
		return entryDateTo;
	}

	/**
	 * 登録日TO
	 * 
	 * @param entryDateTo 登録日TO
	 */
	public void setEntryDateTo(Date entryDateTo) {
		this.entryDateTo = entryDateTo;
	}

	/**
	 * 更新日FROM
	 * 
	 * @return 更新日FROM
	 */
	public Date getUpdateDateFrom() {
		return updateDateFrom;
	}

	/**
	 * 更新日FROM
	 * 
	 * @param updateDateFrom 更新日FROM
	 */
	public void setUpdateDateFrom(Date updateDateFrom) {
		this.updateDateFrom = updateDateFrom;
	}

	/**
	 * 通貨コードリスト
	 * 
	 * @return 通貨コードリスト
	 */
	public List<String> getCurrencyCodeList() {
		return currencyCodeList;
	}

	/**
	 * 通貨コードリスト
	 * 
	 * @param currencyCodeList 通貨コードリスト
	 */
	public void setCurrencyCodeList(List<String> currencyCodeList) {
		this.currencyCodeList = currencyCodeList;
	}

	/**
	 * 消込日FROM
	 * 
	 * @return 消込日FROM
	 */
	public Date getEraseDayFrom() {
		return this.eraseDayFrom;
	}

	/**
	 * 消込日FROM
	 * 
	 * @param eraseDayFrom 消込日FROM
	 */
	public void setEraseDayFrom(Date eraseDayFrom) {
		this.eraseDayFrom = eraseDayFrom;
	}

	/**
	 * 消込日TO
	 * 
	 * @return 消込日TO
	 */
	public Date getEraseDayTo() {
		return this.eraseDayTo;
	}

	/**
	 * 消込日TO
	 * 
	 * @param eraseDayTo 消込日TO
	 */
	public void setEraseDayTo(Date eraseDayTo) {
		this.eraseDayTo = eraseDayTo;
	}

	/**
	 * 概算取消フラグの取得
	 * 
	 * @return 概算取消フラグ
	 */
	public boolean isEstimateCancelFlg() {
		return estimateCancelFlg;
	}

	/**
	 * 概算取消フラグの設定
	 * 
	 * @param estimateCancelFlg 概算取消フラグ
	 */
	public void setEstimateCancelFlg(boolean estimateCancelFlg) {
		this.estimateCancelFlg = estimateCancelFlg;
	}

	/**
	 * 最大ヘッダー取得件数の取得
	 * 
	 * @return maxHeaderCount 最大ヘッダー取得件数
	 */
	public int getMaxHeaderCount() {
		return maxHeaderCount;
	}

	/**
	 * 最大ヘッダー取得件数の設定
	 * 
	 * @param maxHeaderCount 最大ヘッダー取得件数
	 */
	public void setMaxHeaderCount(int maxHeaderCount) {
		this.maxHeaderCount = maxHeaderCount;
	}

	/**
	 * ワークフロー承認用の検索かどうか
	 * 
	 * @return boolean searchWorkFlow
	 */
	public boolean isSearchWorkFlow() {
		return searchWorkFlow;
	}

	/**
	 * ワークフロー承認用の検索かどうか
	 * 
	 * @param searchWorkFlow
	 */
	public void setSearchWorkFlow(boolean searchWorkFlow) {
		this.searchWorkFlow = searchWorkFlow;
	}

	/**
	 * ワークフロー承認 承認グループを取得
	 * 
	 * @return aprvRoleGroupList
	 */
	public List<String> getAprvRoleGroupList() {
		return aprvRoleGroupList;
	}

	/**
	 * ワークフロー承認 承認グループをセットする
	 * 
	 * @param aprvRoleGroupList aprvRoleGroupList
	 */
	public void setAprvRoleGroupList(List<String> aprvRoleGroupList) {
		this.aprvRoleGroupList = aprvRoleGroupList;
	}

	/**
	 * ワークフロー承認 対象の承認ロール
	 * 
	 * @return aprvRoleList
	 */
	public List<String> getAprvRoleList() {
		return aprvRoleList;
	}

	/**
	 * ワークフロー承認 対象の承認ロール
	 * 
	 * @param aprvRoleList
	 */
	public void setAprvRoleList(List<String> aprvRoleList) {
		this.aprvRoleList = aprvRoleList;
	}

}
