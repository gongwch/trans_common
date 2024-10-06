package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.user.*;

/**
 * 伝票 全ての伝票は当該クラスを継承する<br>
 * 伝票は、「伝票ヘッダー」と複数の「伝票明細」から構成される。
 */
public class Slip extends TransferBase implements Cloneable {

	/** 排他フラグ */
	public static class SHR_KBN {

		/** 0:通常 */
		public static final int NON_LOCKED = 0;

		/** 1:排他 */
		public static final int LOCKED = 1;
	}

	/** 決算区分 */
	public static class KSN_KBN {

		/** 0:通常 */
		public static final int NOMAL = 0;
	}

	/** 会社間付替伝票区分 */
	public static class TUKE_KBN {

		/** 0:通常 */
		public static final int NOMAL = 0;

		/** 1:付替先伝票 */
		public static final int TRANSFER_SLIP = 1;
	}

	/** 会社 */
	protected Company company = null;

	/** 伝票ヘッダー */
	protected SWK_HDR header;

	/** 明細行 */
	protected List<SWK_DTL> details;

	/** 振戻日付 */
	protected Date reverseDate = null;

	/** 振戻伝票かどうか */
	protected boolean reversingType = false;

	/** 船舶動静のチェックを行うか */
	protected boolean checksFleetMvmt = false;

	/** true:他システム受入データ */
	protected boolean fromOtherSystem = false;

	/**
	 * 消費税仕訳を起票済みか。<br>
	 * trueの場合、SlipManager.buildSlipにおいて、消費税仕訳を起票しない。
	 */
	protected boolean journalizedTax = false;

	/** true:必須チェック不要(buildBaseSlipの時) */
	protected boolean avoidReuiredItemNULL = false;

	/**
	 * コンストラクタ.
	 */
	public Slip() {
		this.header = createHeader();
		this.details = new ArrayList<SWK_DTL>();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param header ヘッダ
	 */
	public Slip(SWK_HDR header) {
		this.header = header;
		this.details = new ArrayList<SWK_DTL>();
	}

	/**
	 * ヘッダ作成
	 * 
	 * @return ヘッダ
	 */
	protected SWK_HDR createHeader() {
		return new SWK_HDR();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Slip clone() {
		try {
			Slip slip = (Slip) super.clone();
			slip.header = this.header.clone();
			slip.details = new ArrayList<SWK_DTL>();

			for (SWK_DTL dtl : details) {
				slip.addDetail(dtl.clone());
			}

			return slip;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("<");
		buff.append(header.getKAI_CODE()).append("/");
		buff.append(DateUtil.toYMDString(header.getSWK_DEN_DATE())).append("/");
		buff.append(header.getSWK_DEN_NO()).append("/");
		buff.append(header.getSWK_ADJ_KBN());
		buff.append(">\n");
		buff.append("-- header --").append("\n");
		buff.append(header).append("\n");
		buff.append("-- details --").append("\n");
		for (SWK_DTL dtl : getDetails()) {
			buff.append(dtl).append("\n");
		}

		return buff.toString();
	}

	/**
	 * 会社コードを設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		header.setKAI_CODE(companyCode);
	}

	/**
	 * 会社コードを取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return header.getKAI_CODE();
	}

	/**
	 * 伝票番号getter
	 * 
	 * @return 伝票番号
	 */
	public String getSlipNo() {
		return header.getSWK_DEN_NO();
	}

	/**
	 * 伝票番号setter
	 * 
	 * @param denNo 伝票番号
	 */
	public void setSlipNo(String denNo) {
		this.header.setSWK_DEN_NO(denNo);
	}

	/**
	 * 訂正前伝票番号getter
	 * 
	 * @return 訂正前伝票番号
	 */
	public String getReversingSlipNo() {
		return header.getSWK_BEFORE_DEN_NO();
	}

	/**
	 * 訂正前伝票番号setter
	 * 
	 * @param denNo 訂正前伝票番号
	 */
	public void setReversingSlipNo(String denNo) {
		this.header.setSWK_BEFORE_DEN_NO(denNo);
	}

	/**
	 * 振戻伝票かどうか
	 * 
	 * @param reversingType true:振戻伝票
	 */
	public void setReversing(boolean reversingType) {
		this.reversingType = reversingType;
	}

	/**
	 * 振戻伝票かどうか
	 * 
	 * @return true:振戻伝票
	 */
	public boolean isReversingSlip() {
		return this.reversingType;
	}

	/**
	 * 付替先伝票かどうか
	 * 
	 * @return true:付替先伝票
	 */
	public boolean isTransferSlip() {
		return header.getSWK_TUKE_KBN() == TUKE_KBN.TRANSFER_SLIP;
	}

	/**
	 * 決算伝票かどうか
	 * 
	 * @return true:決算、false:通常
	 */
	public boolean isClosingSlip() {
		return this.header.getSWK_KSN_KBN() > 0;
	}

	/**
	 * 伝票日付を取得
	 * 
	 * @return 伝票日付
	 */
	public Date getSlipDate() {
		return header.getSWK_DEN_DATE();
	}

	/**
	 * 伝票日付をセット
	 * 
	 * @param date 伝票日付
	 */
	public void setSlipDate(Date date) {
		this.header.setSWK_DEN_DATE(date);
	}

	/**
	 * 決算段階
	 * 
	 * @return 決算段階
	 */
	public int getSettlementStage() {
		return this.header.getSWK_KSN_KBN();
	}

	/**
	 * 摘要コードを返す
	 * 
	 * @return 摘要コード
	 */
	public String getRemarksCode() {
		return this.header.getSWK_TEK_CODE();
	}

	/**
	 * 摘要コードをセット
	 * 
	 * @param code 摘要コード
	 */
	public void setRemarksCode(String code) {
		this.header.setSWK_TEK_CODE(code);
	}

	/**
	 * 摘要
	 * 
	 * @return 摘要
	 */
	public String getRemarks() {
		return header.getSWK_TEK();
	}

	/**
	 * 伝票摘要を設定
	 * 
	 * @param remarks 伝票摘要
	 */
	public void setRemarks(String remarks) {
		header.setSWK_TEK(remarks);
	}

	/**
	 * 伝票種別の取得
	 * 
	 * @return 伝票種別
	 */
	public String getSlipType() {
		return this.header.getSWK_DEN_SYU();
	}

	/**
	 * 伝票ヘッダ
	 * 
	 * @return 伝票ヘッダ
	 */
	public SWK_HDR getHeader() {
		return header;
	}

	/**
	 * 伝票ヘッダのセット
	 * 
	 * @param hdr 伝票ヘッダ
	 */
	public void setHeader(SWK_HDR hdr) {
		this.header = hdr;
	}

	/**
	 * 明細行getter
	 * 
	 * @return 明細行
	 */
	public List<SWK_DTL> getDetails() {
		return details;
	}

	/**
	 * 帳簿を指定して明細行取得
	 * 
	 * @param type 帳簿
	 * @return 明細行
	 */
	public List<SWK_DTL> getDetails(CurrencyType type) {
		List<SWK_DTL> list = new ArrayList();

		for (SWK_DTL dtl : this.details) {
			if (type == dtl.getCurrencyType()) {
				list.add(dtl);
			}
		}

		return list;
	}

	/**
	 * 貸借を指定して明細行取得
	 * 
	 * @param dc 貸借
	 * @return 明細行
	 */
	public List<SWK_DTL> getDetails(Dc dc) {
		List<SWK_DTL> list = new ArrayList();

		for (SWK_DTL dtl : this.details) {
			if (dc == dtl.getDC()) {
				list.add(dtl);
			}
		}

		return list;
	}

	/**
	 * 帳簿、貸借を指定して明細行取得
	 * 
	 * @param type 帳簿
	 * @param dc 貸借
	 * @return 明細行
	 */
	public List<SWK_DTL> getDetails(CurrencyType type, Dc dc) {
		List<SWK_DTL> list = new ArrayList();

		for (SWK_DTL dtl : this.details) {
			if (type == dtl.getCurrencyType() && dc == dtl.getDC()) {
				list.add(dtl);
			}
		}

		return list;
	}

	/**
	 * 明細数
	 * 
	 * @return 明細数
	 */
	public int getDetailCount() {
		return details.size();
	}

	/**
	 * 伝票明細のセット
	 * 
	 * @param dlist 伝票明細
	 */
	public void setDetails(List<SWK_DTL> dlist) {
		this.details = dlist;
	}

	/**
	 * 伝票明細のファクトリ<br>
	 * 伝票明細クラスを生成して返す.
	 * 
	 * @return SWK_DTL
	 */
	public SWK_DTL createDetail() {
		SWK_DTL dtl = new SWK_DTL();
		dtl.setKAI_CODE(getCompanyCode()); // 会社コード
		dtl.setSWK_DEN_NO(getSlipNo()); // 伝票番号
		dtl.setSWK_DEN_DATE(getSlipDate()); // 伝票日付
		dtl.setDEN_SYU_CODE(header.getSWK_DEN_SYU()); // 伝票種別コード
		dtl.setSWK_DATA_KBN(header.getSWK_DATA_KBN()); // データ区分
		dtl.setSWK_UPD_KBN(header.getSWK_UPD_KBN()); // 更新区分
		dtl.setSWK_KSN_KBN(header.getSWK_KSN_KBN()); // 決算段階
		dtl.setSWK_ADJ_KBN(header.getSWK_ADJ_KBN()); // IFRS調整区分

		return dtl;
	}

	/**
	 * 伝票明細行を追加する
	 * 
	 * @param detail 伝票明細行
	 */
	public void addDetail(SWK_DTL detail) {
		details.add(detail);
	}

	/**
	 * 伝票明細行を追加する
	 * 
	 * @param list 伝票明細行
	 */
	public void addDetails(List<SWK_DTL> list) {
		details.addAll(list);
	}

	/**
	 * 伝票明細行を追加する
	 * 
	 * @param detail 伝票明細行
	 */
	public void removeDetail(SWK_DTL detail) {
		details.remove(detail);
	}

	/**
	 * 伝票明細行をクリアする.
	 */
	public void clearDetail() {
		details.clear();
	}

	/**
	 * 伝票の種類
	 * 
	 * @return 伝票の種類
	 */
	public SlipKind getSlipKind() {
		return SlipKind.get(header.getSWK_DATA_KBN());
	}

	/**
	 * 帳簿設定
	 * 
	 * @param book 帳簿
	 */
	public void setAccountBook(AccountBook book) {
		getHeader().setSWK_ADJ_KBN(book.value);
	}

	/**
	 * 帳簿取得
	 * 
	 * @return 帳簿
	 */
	public AccountBook getAccountBook() {
		return AccountBook.get(getHeader().getSWK_ADJ_KBN());
	}

	/**
	 * 自国通貨/機能通貨かを返す。<br>
	 * ただし、Slip内にどちらか一方しか存在しないことを前提とする。
	 * 
	 * @return 自国通貨/機能通貨か(明細の1行目の値)
	 */
	public CurrencyType getCurrencyType() {
		if (getDetails() == null || getDetails().isEmpty()) {
			return null;
		}
		return getDetails().get(0).getCurrencyType();
	}

	/**
	 * 伝票のタイトル
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return header.getSWK_DEN_SYU_NAME_K();
	}

	/**
	 * 借方の合計金額(基軸通貨)を返す。
	 * 
	 * @return 借方の合計金額(基軸通貨)
	 */
	public BigDecimal getDebitKeyAmount() {

		BigDecimal amount = BigDecimal.ZERO;
		for (SWK_DTL detail : this.details) {
			amount = amount.add(detail.getDebitKeyAmount());
		}

		return amount;
	}

	/**
	 * 貸方の合計金額(基軸通貨)を返す。
	 * 
	 * @return 貸方の合計金額(基軸通貨)
	 */
	public BigDecimal getCreditKeyAmount() {
		BigDecimal amount = BigDecimal.ZERO;
		for (SWK_DTL detail : this.details) {
			amount = amount.add(detail.getCreditKeyAmount());
		}

		return amount;
	}

	/**
	 * 借方の外貨合計金額を返す。<br>
	 * 発生順に通貨インデックスとし、指定インデックス値がない場合はnullが返る.
	 * 
	 * @param index インデックス
	 * @return 合計金額
	 */
	public BigDecimal getDebitForeignAmountAt(int index) {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getDebitInputAmount()));

			}

		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);

		if (array.length > index) {
			return array[index];

		}
		return null;
	}

	/**
	 * 借方の外貨合計金額の一覧を返す。
	 * 
	 * @return 合計金額
	 */
	public BigDecimal[] getDebitForeignAmountList() {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getDebitInputAmount()));

			}

		}

		if (map.size() == 0) {
			return null;
		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);

		return array;

	}

	/**
	 * 貸方の外貨合計金額を返す。<br>
	 * 発生順に通貨インデックスとし、指定インデックス値がない場合はnullが返る.
	 * 
	 * @param index インデックス
	 * @return 合計金額
	 */
	public BigDecimal getCreditForeignAmountAt(int index) {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getCreditInputAmount()));
			}
		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);

		if (array.length > index) {
			return array[index];

		}
		return null;
	}

	/**
	 * 貸方の外貨合計金額の一覧を返す。<br>
	 * 
	 * @return 合計金額
	 */
	public BigDecimal[] getCreditForeignAmountList() {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getCreditInputAmount()));
			}
		}

		if (map.size() == 0) {
			return null;
		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);
		return array;

	}

	/**
	 * 指定Index外貨を返す.(コードと桁数のみ)<br>
	 * 発生順に外貨インデックスとし、指定インデックス値がない場合はnullが返る.
	 * 
	 * @param index インデックス
	 * @return 外貨エンティティ
	 */
	public Currency getForeignCurrencyAt(int index) {

		Map<String, Currency> map = new LinkedHashMap<String, Currency>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String code = detail.getSWK_CUR_CODE();
				Currency currency = map.get(code);

				if (currency == null) {
					currency = new Currency();
					currency.setCode(code);
					currency.setDecimalPoint(detail.getCUR_DEC_KETA());
				}

				map.put(code, currency);
			}
		}

		Currency[] array = map.values().toArray(new Currency[map.size()]);

		if (array.length > index) {
			return array[index];

		}
		return null;
	}

	/**
	 * 伝票内にある外貨を発生順に並べたリストを返す
	 * 
	 * @return 外貨リスト
	 */
	public Currency[] getForeignCurrencyList() {

		Map<String, Currency> map = new LinkedHashMap<String, Currency>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String code = detail.getSWK_CUR_CODE();
				Currency currency = map.get(code);

				if (currency == null) {
					currency = new Currency();
					currency.setCode(code);
					currency.setDecimalPoint(detail.getCUR_DEC_KETA());
				}

				map.put(code, currency);
			}
		}

		if (map.size() == 0) {
			return null;
		}

		Currency[] array = map.values().toArray(new Currency[map.size()]);
		return array;
	}

	/**
	 * 相手科目・相手部門をセットする。
	 */
	public void matchingItem() {
		// 通常と機能通貨仕訳は別々で相手設定
		List<SWK_DTL> nomalList = new ArrayList<SWK_DTL>();
		List<SWK_DTL> funcList = new ArrayList<SWK_DTL>();

		for (SWK_DTL dtl : details) {
			(dtl.isFunctionalCurrency() ? funcList : nomalList).add(dtl);
		}

		matchingItem(nomalList);
		matchingItem(funcList);
	}

	/**
	 * 相手科目・相手部門をセットする。
	 * 
	 * @param list 対象明細リスト
	 */
	public void matchingItem(List<SWK_DTL> list) {
		// 対象を振り分け
		List<SWK_DTL> drList = new ArrayList<SWK_DTL>();
		List<SWK_DTL> crList = new ArrayList<SWK_DTL>();

		List<SWK_DTL> drTaxList = new ArrayList<SWK_DTL>(); // 消費税仕訳DR
		List<SWK_DTL> crTaxList = new ArrayList<SWK_DTL>(); // 消費税仕訳CR

		for (SWK_DTL dtl : list) {

			// 消費税仕訳
			if (dtl.isTaxJornal()) {
				(dtl.isDR() ? drTaxList : crTaxList).add(dtl);
				continue;
			}

			(dtl.isDR() ? drList : crList).add(dtl);
		}

		// 相手情報の取得
		String[] drKmk = new String[3];
		String[] crKmk = new String[3];
		String drDept = null;
		String crDept = null;

		// 借方科目が2つ以上ある場合はなし
		if (drList.size() == 1) {
			SWK_DTL drDtl = drList.get(0);
			drKmk = new String[] { drDtl.getSWK_KMK_CODE(), drDtl.getSWK_HKM_CODE(), drDtl.getSWK_UKM_CODE() };
			drDept = drDtl.getSWK_DEP_CODE();
		}

		// 貸方科目が2つ以上ある場合はなし
		if (crList.size() == 1) {
			SWK_DTL crDtl = crList.get(0);
			crKmk = new String[] { crDtl.getSWK_KMK_CODE(), crDtl.getSWK_HKM_CODE(), crDtl.getSWK_UKM_CODE() };
			crDept = crDtl.getSWK_DEP_CODE();
		}

		// 明細の設定
		for (SWK_DTL dtl : drList) {
			dtl.setSWK_AT_KMK_CODE(crKmk[0]); // 相手科目コード
			dtl.setSWK_AT_HKM_CODE(crKmk[1]); // 相手補助コード
			dtl.setSWK_AT_UKM_CODE(crKmk[2]); // 相手内訳コード
			dtl.setSWK_AT_DEP_CODE(crDept); // 相手部門コード
		}

		for (SWK_DTL dtl : crList) {
			dtl.setSWK_AT_KMK_CODE(drKmk[0]); // 相手科目コード
			dtl.setSWK_AT_HKM_CODE(drKmk[1]); // 相手補助コード
			dtl.setSWK_AT_UKM_CODE(drKmk[2]); // 相手内訳コード
			dtl.setSWK_AT_DEP_CODE(drDept); // 相手部門コード
		}

		// 消費税仕訳への設定
		for (SWK_DTL dtl : drTaxList) {
			dtl.setSWK_AT_KMK_CODE(crKmk[0]); // 相手科目コード
			dtl.setSWK_AT_HKM_CODE(crKmk[1]); // 相手補助コード
			dtl.setSWK_AT_UKM_CODE(crKmk[2]); // 相手内訳コード
			dtl.setSWK_AT_DEP_CODE(crDept); // 相手部門コード
		}

		for (SWK_DTL dtl : crTaxList) {
			dtl.setSWK_AT_KMK_CODE(drKmk[0]); // 相手科目コード
			dtl.setSWK_AT_HKM_CODE(drKmk[1]); // 相手補助コード
			dtl.setSWK_AT_UKM_CODE(drKmk[2]); // 相手内訳コード
			dtl.setSWK_AT_DEP_CODE(drDept); // 相手部門コード
		}
	}

	/**
	 * 各明細に情報をセットする
	 */
	public void synchDetails() {

		// 明細同期処理
		String compCode = getCompanyCode();
		Date slipDate = getSlipDate();
		String slipNo = getSlipNo();
		String seiNo = header.getSWK_SEI_NO();

		String denSyu = header.getSWK_DEN_SYU();
		String dataKbn = header.getSWK_DATA_KBN();
		int updKbn = header.getSWK_UPD_KBN();
		int ksnKbn = header.getSWK_KSN_KBN();

		Date inputDate = header.getINP_DATE();
		Date updateDate = header.getUPD_DATE();
		String userID = header.getUSR_ID();
		String prgID = header.getPRG_ID();

		for (SWK_DTL dtl : getDetails()) {

			// ヘッダより引継ぎ
			dtl.setKAI_CODE(compCode); // 会社コード
			dtl.setSWK_DEN_NO(slipNo); // 伝票番号
			dtl.setSWK_DEN_DATE(slipDate); // 伝票日付

			dtl.setDEN_SYU_CODE(denSyu); // 伝票種別コード
			dtl.setSWK_DATA_KBN(dataKbn); // データ区分
			dtl.setSWK_UPD_KBN(updKbn); // 更新区分
			dtl.setSWK_KSN_KBN(ksnKbn); // 決算段階

			dtl.setSWK_SEI_NO(seiNo); // 証憑番号

			dtl.setINP_DATE(inputDate); // 登録日付
			dtl.setUPD_DATE(updateDate); // 更新日付
			dtl.setUSR_ID(userID); // ユーザID
			dtl.setPRG_ID(prgID); // プログラムID
		}
	}

	/**
	 * 各明細に情報をセットする
	 * 
	 * @param fiscalYear 年度
	 * @param fiscalMonth 月度
	 */
	public void synchDetails(int fiscalYear, int fiscalMonth) {

		synchDetails();

		for (SWK_DTL dtl : getDetails()) {
			dtl.setSWK_NENDO(fiscalYear); // 年度
			dtl.setSWK_TUKIDO(fiscalMonth); // 月度
		}
	}

	/**
	 * 赤伝票にする(金額反転)
	 * 
	 * @return 赤伝票
	 */
	public Slip toCancelSlip() {
		Slip clone = this.clone();

		// 金額反転 ヘッダー
		SWK_HDR hdr = clone.getHeader();

		// 入力金額
		if (hdr.getSWK_IN_KIN() != null) {
			hdr.setSWK_IN_KIN(hdr.getSWK_IN_KIN().negate());
		}

		// 邦貨金額
		if (hdr.getSWK_KIN() != null) {
			hdr.setSWK_KIN(hdr.getSWK_KIN().negate());
		}

		// 支払入力金額
		if (hdr.getSWK_IN_SIHA_KIN() != null) {
			hdr.setSWK_IN_SIHA_KIN(hdr.getSWK_IN_SIHA_KIN().negate());
		}

		// 支払邦貨金額
		if (hdr.getSWK_SIHA_KIN() != null) {
			hdr.setSWK_SIHA_KIN(hdr.getSWK_SIHA_KIN().negate());
		}

		// 経費入力金額
		if (hdr.getSWK_IN_KEIHI_KIN() != null) {
			hdr.setSWK_IN_KEIHI_KIN(hdr.getSWK_IN_KEIHI_KIN().negate());
		}

		// 経費邦貨金額
		if (hdr.getSWK_KEIHI_KIN() != null) {
			hdr.setSWK_KEIHI_KIN(hdr.getSWK_KEIHI_KIN().negate());
		}

		for (SWK_DTL dtl : clone.getDetails()) {
			// 金額反転
			dtl.setSWK_KIN(dtl.getSWK_KIN().negate());
			dtl.setSWK_IN_KIN(dtl.getSWK_IN_KIN().negate());

			if (dtl.getSWK_ZEI_KIN() != null) {
				dtl.setSWK_ZEI_KIN(dtl.getSWK_ZEI_KIN().negate());
			}
			if (dtl.getSWK_IN_ZEI_KIN() != null) {
				dtl.setSWK_IN_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN().negate());
			}
		}

		return clone;
	}

	/**
	 * 逆伝票にする(貸借反転)
	 * 
	 * @return 逆伝票
	 */
	public Slip toReverseSlip() {
		Slip clone = this.clone();

		// 金額反転 ヘッダー
		SWK_HDR hdr = clone.getHeader();

		// 入力金額
		if (hdr.getSWK_IN_KIN() != null) {
			hdr.setSWK_IN_KIN(hdr.getSWK_IN_KIN().negate());
		}

		// 邦貨金額
		if (hdr.getSWK_KIN() != null) {
			hdr.setSWK_KIN(hdr.getSWK_KIN().negate());
		}

		// 支払入力金額
		if (hdr.getSWK_IN_SIHA_KIN() != null) {
			hdr.setSWK_IN_SIHA_KIN(hdr.getSWK_IN_SIHA_KIN().negate());
		}

		// 支払邦貨金額
		if (hdr.getSWK_SIHA_KIN() != null) {
			hdr.setSWK_SIHA_KIN(hdr.getSWK_SIHA_KIN().negate());
		}

		// 経費入力金額
		if (hdr.getSWK_IN_KEIHI_KIN() != null) {
			hdr.setSWK_IN_KEIHI_KIN(hdr.getSWK_IN_KEIHI_KIN().negate());
		}

		// 経費邦貨金額
		if (hdr.getSWK_KEIHI_KIN() != null) {
			hdr.setSWK_KEIHI_KIN(hdr.getSWK_KEIHI_KIN().negate());
		}

		for (SWK_DTL dtl : clone.getDetails()) {
			// 貸借反転
			dtl.setDC(dtl.getDC() == Dc.DEBIT ? Dc.CREDIT : Dc.DEBIT);
		}

		return clone;
	}

	/**
	 * 当該伝票の帳簿の決算通貨を返す。<br>
	 * 自国帳簿の場合、基軸通貨、機能通貨帳簿の場合は機能通貨。
	 * 
	 * @return 決算通貨
	 */
	public Currency getBaseCurrency() {

		List<SWK_DTL> list = getDetails();
		if (list == null || list.isEmpty()) {
			return null;
		}
		Currency currency = new Currency();
		if (list.get(0).isFunctionalCurrency()) {
			currency.setCode(header.getFUNC_CUR_CODE());
			currency.setDecimalPoint(header.getFUNC_CUR_DEC_KETA());
		} else {
			currency.setCode(header.getKEY_CUR_CODE());
			currency.setDecimalPoint(header.getKEY_CUR_DEC_KETA());
		}

		return currency;

	}

	/**
	 * 振戻日付を取得する。
	 * 
	 * @return Date 振戻日付
	 */
	public Date getReverseDate() {
		return reverseDate;
	}

	/**
	 * 振戻日付を設定する。
	 * 
	 * @param reverseDate 振戻日付
	 */
	public void setReverseDate(Date reverseDate) {
		this.reverseDate = reverseDate;
	}

	/**
	 * 消費税仕訳を起票済みかを返す。<br>
	 * trueの場合、SlipManager.buildSlipにおいて、消費税仕訳を起票しない。
	 * 
	 * @return 消費税仕訳を起票済みか
	 */
	public boolean isJournalizedTax() {
		return journalizedTax;
	}

	/**
	 * 消費税仕訳を起票済みかを設定する。<br>
	 * trueの場合、SlipManager.buildSlipにおいて、消費税仕訳を起票しない。
	 * 
	 * @param journalizedTax 消費税仕訳を起票済みか
	 */
	public void setJournalizedTax(boolean journalizedTax) {
		this.journalizedTax = journalizedTax;
	}

	/**
	 * 新規作成伝票かどうか
	 * 
	 * @return true:新規
	 */
	public boolean isNew() {
		if (header == null) {
			return true;
		}

		return header.getINP_DATE() == null && header.getSWK_INP_DATE() == null;
	}

	/**
	 * 会社getter
	 * 
	 * @return 会社
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 会社setter
	 * 
	 * @param company 会社
	 */
	public void setCompany(Company company) {
		this.company = company;
		getHeader().setKAI_CODE(company.getCode());

		AccountConfig config = company.getAccountConfig();
		header.setKEY_CUR_CODE(config.getKeyCurrency().getCode());
		header.setKEY_CUR_DEC_KETA(config.getKeyCurrency().getDecimalPoint());
		header.setFUNC_CUR_CODE(config.getFunctionalCurrency().getCode());
		header.setFUNC_CUR_DEC_KETA(config.getFunctionalCurrency().getDecimalPoint());
	}

	/**
	 * 明細に行番号をセット
	 */
	public void setRowNo() {
		if (details != null) {
			// 通常と機能通貨仕訳は別で
			int nNo = 1;
			int fNo = 1;
			for (SWK_DTL dtl : details) {
				dtl.setSWK_GYO_NO(dtl.isFunctionalCurrency() ? fNo++ : nNo++);
			}
		}
	}

	/**
	 * 会計期をセット
	 */
	public void setFiscalPeriod() {
		if (company != null) {
			setFiscalPeriod(company.getFiscalPeriod());
		}
	}

	/**
	 * 会計期をセット
	 * 
	 * @param fp 会計期間
	 */
	public void setFiscalPeriod(FiscalPeriod fp) {
		if (fp == null || getSlipDate() == null) {
			return;
		}

		int year = fp.getFiscalYear(getHeader().getSWK_DEN_DATE());
		int month = fp.getFiscalMonth(getHeader().getSWK_DEN_DATE());
		if (details != null) {
			for (SWK_DTL detail : details) {
				detail.setSWK_NENDO(year);
				detail.setSWK_TUKIDO(month);
			}
		}
	}

	/**
	 * 伝票摘要を、明細がブランクの場合に明細にセットする
	 */
	public void setHeaderRemarkToDetail() {
		SWK_HDR header_ = getHeader();
		if (header_ == null) {
			return;
		}
		if (details != null) {
			for (SWK_DTL detail : details) {
				if (Util.isNullOrEmpty(detail.getSWK_GYO_TEK())) {
					detail.setSWK_GYO_TEK(header_.getSWK_TEK());
				}
			}
		}
	}

	/**
	 * 起票者情報setter
	 * 
	 * @param user
	 */
	public void setIssuer(User user) {
		SWK_HDR header_ = getHeader();
		if (header_ != null) {
			// 受付部門
			header_.setSWK_UKE_DEP_CODE(user.getDepartment().getCode());
			header_.setSWK_UKE_DEP_NAME(user.getDepartment().getName());
			header_.setSWK_UKE_DEP_NAME_S(user.getDepartment().getNames());
			// 依頼部門
			header_.setSWK_IRAI_DEP_CODE(user.getDepartment().getCode());
			header_.setSWK_IRAI_DEP_NAME(user.getDepartment().getName());
			header_.setSWK_IRAI_DEP_NAME_S(user.getDepartment().getNames());
			// 依頼社員
			header_.setSWK_IRAI_EMP_CODE(user.getEmployee().getCode());
			header_.setSWK_IRAI_EMP_NAME(user.getEmployee().getName());
			header_.setSWK_IRAI_EMP_NAME_S(user.getEmployee().getNames());
		}
	}

	/**
	 * 伝票種別(データ区分含む)をセット
	 * 
	 * @param slipType 伝票種別
	 */
	public void setSlipType(SlipType slipType) {
		header.setSlipType(slipType);

		for (SWK_DTL dtl : details) {

			if (slipType == null) {
				dtl.setDEN_SYU_CODE(null);
				dtl.setSWK_DATA_KBN(null);
			} else {
				dtl.setDEN_SYU_CODE(slipType.getCode());
				dtl.setSWK_DATA_KBN(slipType.getDataType());
			}
		}
	}

	/**
	 * 伝票の成型
	 */
	public void buildSlip() {

		// 1.ヘッダー値を明細にシンクロ
		synchDetails();
		setHeaderRemarkToDetail();

		// 2.相手科目の設定
		matchingItem();

		// 3.行番号
		setRowNo();

		// 4.年度、月度のセット
		setFiscalPeriod();
	}

	/**
	 * 帳簿別に分割する.<br>
	 * 明細が無ければ空のリスト
	 * 
	 * @return 帳簿別Slipリスト
	 */
	public List<Slip> divide() {
		try {
			List<SWK_DTL> keycList = new ArrayList<SWK_DTL>();
			List<SWK_DTL> funcList = new ArrayList<SWK_DTL>();

			for (SWK_DTL dtl : details) {
				(dtl.isFunctionalCurrency() ? funcList : keycList).add(dtl);
			}

			List<Slip> slipList = new ArrayList<Slip>();

			// 基軸通貨
			if (!keycList.isEmpty()) {
				Slip slip = (Slip) super.clone();
				slip.header = this.header.clone();
				slip.details = keycList;
				slipList.add(slip);
			}

			// 機能通貨
			if (!funcList.isEmpty()) {
				Slip slip = (Slip) super.clone();
				slip.header = this.header.clone();
				slip.details = funcList;
				slipList.add(slip);
			}

			return slipList;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * Slipの中に複数帳簿が混在する場合、指定帳簿、指定通貨帳簿だけの伝票を返す。
	 * 
	 * @param accountBook 帳簿
	 * @param currencyType 通貨
	 * @return 帳簿別伝票
	 */
	public Slip getSlip(AccountBook accountBook, CurrencyType currencyType) {
		try {
			Slip slip = (Slip) super.clone();
			slip.setHeader(this.getHeader());

			List<SWK_DTL> list = new ArrayList<SWK_DTL>();

			for (SWK_DTL detail : this.details) {

				if (accountBook != detail.getAccountBook() && accountBook != AccountBook.BOTH) {
					continue;
				}

				if (currencyType != detail.getCurrencyType()) {
					continue;
				}

				list.add(detail);
			}

			slip.setDetails(list);

			return slip;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * true:必須チェック不要の取得
	 * 
	 * @return avoidReuiredItemNULL true:必須チェック不要
	 */
	public boolean isAvoidReuiredItemNULL() {
		return avoidReuiredItemNULL;
	}

	/**
	 * true:必須チェック不要の設定
	 * 
	 * @param avoidReuiredItemNULL true:必須チェック不要
	 */
	public void setAvoidReuiredItemNULL(boolean avoidReuiredItemNULL) {
		this.avoidReuiredItemNULL = avoidReuiredItemNULL;
	}

	/**
	 * 船舶動静のチェックを行うか
	 * 
	 * @return true:行う
	 */
	public boolean isChecksFleetMvmt() {
		return this.checksFleetMvmt;
	}

	/**
	 * 船舶動静のチェックを行うか
	 * 
	 * @param isCheckFleetMvmt true:行う
	 */
	public void setChecksFleetMvmt(boolean isCheckFleetMvmt) {
		this.checksFleetMvmt = isCheckFleetMvmt;
	}

	/**
	 * true:他システム受入データの取得
	 * 
	 * @return fromOtherSystem true:他システム受入データ
	 */
	public boolean isFromOtherSystem() {
		return fromOtherSystem;
	}

	/**
	 * true:他システム受入データの設定
	 * 
	 * @param fromOtherSystem true:他システム受入データ
	 */
	public void setFromOtherSystem(boolean fromOtherSystem) {
		this.fromOtherSystem = fromOtherSystem;
	}

}
