package jp.co.ais.trans.common.info;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * ログインユーザの会社情報
 */
public class TCompanyInfo implements Serializable {

	/** 非会計明細区分: 使用しない */
	public static final int NON_ACCOUNTING_DETAIL_NONE = 0;

	/** 非会計明細区分: 数値 */
	public static final int NON_ACCOUNTING_DETAIL_NUMBER = 1;

	/** 非会計明細区分: 文字列 */
	public static final int NON_ACCOUNTING_DETAIL_CHAR = 2;

	/** 非会計明細区分: 日付 */
	public static final int NON_ACCOUNTING_DETAIL_DATE = 3;

	/** レート換算端数処理区分: 切捨 */
	public static final int RATE_CF_DIV_ROUND_DOWN = 0;

	/** レート換算端数処理区分: 四捨五入 */
	public static final int RATE_CF_DIV_HALF_ADJUST = 2;

	/** 会社コード */
	private String kaiCode = "";

	/** 会社名 */
	private String kaiName = "";

	/** 会社略称 */
	private String kaiNameS = "";

	/** 会社背景色 */
	private String foreColor = "";

	/** 科目名称 */
	private String itemName = "";

	/** 補助科目名称 */
	private String subItemName = "";

	/** 内訳科目管理 使用判定 */
	private boolean isUseBreakDownItem;

	/** 内訳科目名称 */
	private String breakDownItemName = "";

	/** 管理区分1～6 使用判定 */
	private boolean[] isUseManageDivs = { true, true, true, true, true, true };

	/** 管理区分1～6 名称 */
	private String[] manageDivNames = { "", "", "", "", "", "" };

	/** 非会計明細区分1～3 */
	private int[] nonAccountingDetailDivs = { 1, 1, 1 };

	/** 非会計明細1～3 名称 */
	private String[] nonAccountingDetailNames = { "", "", "", };

	/** 期首月 */
	private int beginningOfPeriodMonth = 4;

	/** 基軸通貨コード */
	private String baseCurrencyCode = "";

	/** 元帳日別残高表示を表示するかどうか */
	private boolean isLedgerEachDayBalanceView;

	/** 通貨コード小数点桁数 */
	private Map<String, String> currencyMap = new TreeMap<String, String>();

	/** その他データ(カスタマイズ用) */
	private Map<String, String> otherData = new HashMap<String, String>(0);

	/** レート換算端数処理区分 */
	private int rateKbn;

	/** 印刷時の初期値 */
	private int printDef;

	/** 伝票印刷区分 */
	private int printKbn;

	/** 直接印刷区分 */
	private int directPrintKbn;

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return this.kaiCode;
	}

	/**
	 * 会社コード取得setter
	 * 
	 * @param kaiCode 会社コード
	 */
	public void setCompanyCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * 会社名取得
	 * 
	 * @return 会社名取得
	 */
	public String getCompanyName() {
		return this.kaiName;
	}

	/**
	 * 会社名取得setter
	 * 
	 * @param kaiName 会社名取得
	 */
	public void setCompanyName(String kaiName) {
		this.kaiName = kaiName;
	}

	/**
	 * 会社略称取得
	 * 
	 * @return 会社略称取得
	 */
	public String getCompanyNameS() {
		return this.kaiNameS;
	}

	/**
	 * 会社略称取得setter
	 * 
	 * @param kaiNameS 会社略称取得
	 */
	public void setCompanyNameS(String kaiNameS) {
		this.kaiNameS = kaiNameS;
	}

	/**
	 * 会社背景色
	 * 
	 * @return 会社背景色
	 */
	public String getForeColor() {
		return this.foreColor;
	}

	/**
	 * 会社背景色setter
	 * 
	 * @param foreColor 会社背景色
	 */
	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	/**
	 * 科目名称.<br>
	 * 「科目」以外の名称を使用する場合
	 * 
	 * @return 科目名称.
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 科目名称
	 * 
	 * @param itemName 科目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 補助科目名称<br>
	 * 補助以外の名称を使用する場合
	 * 
	 * @return 補助科目名称
	 */
	public String getSubItemName() {
		return this.subItemName;
	}

	/**
	 * 補助科目名称<br>
	 * 補助以外の名称を使用する場合
	 * 
	 * @param subItemName 補助科目名称
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * 内訳科目管理(true:使用する, false:使用しない)<br>
	 * 内訳科目ﾏｽﾀを使用しない場合、各伝票入力画面から入力フィールドを消し、帳票出力時の表示欄を消す。
	 * 
	 * @return 内訳科目判定
	 */
	public boolean isUseBreakDownItem() {
		return this.isUseBreakDownItem;
	}

	/**
	 * 内訳科目管理 0:使用しない, 1:使用する
	 * 
	 * @param breakDownItem 内訳科目管理
	 */
	public void setBreakDownItem(int breakDownItem) {
		isUseBreakDownItem = (0 != breakDownItem);
	}

	/**
	 * 内訳科目名称
	 * 
	 * @param breakDownItemName 内訳科目名称
	 */
	public void setBreakDownItemName(String breakDownItemName) {
		this.breakDownItemName = breakDownItemName;
	}

	/**
	 * 内訳科目名称<br>
	 * 内訳以外の名称を使用する場合
	 * 
	 * @return 内訳科目名称
	 */
	public String getBreakDownItemName() {
		return this.breakDownItemName;
	}

	/**
	 * 管理区分1～6 使用判定(0:使用しない, 1:使用する)
	 * 
	 * @param divs 管理区分1～6 使用判定
	 */
	public void setManageDivs(int[] divs) {
		for (int i = 0; i < divs.length; i++) {
			this.isUseManageDivs[i] = (0 != divs[i]);
		}
	}

	/**
	 * 管理区分1～6 使用判定 <br>
	 * 管理区分ﾏｽﾀを使用しない場合、各伝票入力画面から入力フィールドを消し、帳票出力時の表示欄を消す。
	 * 
	 * @return 管理区分1～6 使用判定
	 */
	public boolean[] isUseManageDivs() {
		return this.isUseManageDivs;
	}

	/**
	 * 管理名称1～6
	 * 
	 * @param names 管理名称1～6
	 */
	public void setManageDivNames(String[] names) {
		this.manageDivNames = names;
	}

	/**
	 * 管理名称1～6 <br>
	 * 各伝票入力画面の入力ﾌｨｰﾙﾄﾞに表示し、帳票出力時のｷｬﾌﾟｼｮﾝ名に表示する。
	 * 
	 * @return 管理名称1～6
	 */
	public String[] getManageDivNames() {
		return this.manageDivNames;
	}

	/**
	 * 非会計明細名区分1～3<br>
	 * 0:使用しない 1:数値 2:文字 3:日付
	 * 
	 * @return 非会計明細名区分1～3
	 */
	public int[] getNonAccountingDetailDivs() {
		return this.nonAccountingDetailDivs;
	}

	/**
	 * 非会計明細名区分1～3<br>
	 * 0:使用しない 1:数値 2:文字 3:日付
	 * 
	 * @param nonAccountingDetailDivs 非会計明細名区分1～3
	 */
	public void setNonAccountingDetailDivs(int[] nonAccountingDetailDivs) {
		this.nonAccountingDetailDivs = nonAccountingDetailDivs;
	}

	/**
	 * 非会計明細名称1～3
	 * 
	 * @param names 非会計明細名称1～3
	 */
	public void setNonAccountingDetailNames(String[] names) {
		this.nonAccountingDetailNames = names;
	}

	/**
	 * 非会計明細名称1～3<br>
	 * 各伝票入力画面の入力ﾌｨｰﾙﾄﾞに表示し、帳票出力時のｷｬﾌﾟｼｮﾝ名に表示する。
	 * 
	 * @return 非会計明細名称1～3
	 */
	public String[] getNonAccountingDetailNames() {
		return this.nonAccountingDetailNames;
	}

	/**
	 * 期首月
	 * 
	 * @param beginningOfPeriodMonth
	 */
	public void setBeginningOfPeriodMonth(Integer beginningOfPeriodMonth) {
		this.beginningOfPeriodMonth = beginningOfPeriodMonth.intValue();
	}

	/**
	 * 期首月<br>
	 * 各財務諸表の会計期を表示する際に、ｙｙｙｙ年mm月 - 会社設立年（会社設立日のyyyy）||期首月(mm) + 1年 +1ヶ月 =第yyyy期mm月度とする
	 * 
	 * @return 期首月
	 */
	public int getBeginningOfPeriodMonth() {
		return this.beginningOfPeriodMonth;
	}

	/**
	 * 基軸通貨コード
	 * 
	 * @param baseCurrencyCode 通貨コード
	 */
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	/**
	 * 基軸通貨コード
	 * 
	 * @return 基軸通貨コード
	 */
	public String getBaseCurrencyCode() {
		return this.baseCurrencyCode;
	}

	/**
	 * 基軸通貨コード小数点桁数
	 * 
	 * @return 基軸通貨コード小数点桁数
	 */
	public int getBaseCurrencyDigit() {
		return getCurrencyDigit(this.baseCurrencyCode);
	}

	/**
	 * 元帳日別残高表示の判定
	 * 
	 * @return true: 表示する、false: 表示しない
	 */
	public boolean isLedgerEachDayBalanceView() {
		return this.isLedgerEachDayBalanceView;
	}

	/**
	 * 元帳日別残高表示の判定
	 * 
	 * @param isView 表示判断
	 */
	public void setLedgerEachDayBalanceView(boolean isView) {
		this.isLedgerEachDayBalanceView = isView;
	}

	/**
	 * レート換算端数処理区分.
	 * 
	 * @param rateKbn 定数:RATE_CF_DIV_XXX(0:切捨 2:四捨五入)
	 */
	public void setRateConversionFraction(int rateKbn) {
		this.rateKbn = rateKbn;
	}

	/**
	 * レート換算端数処理区分
	 * 
	 * @return 0:切捨 2:四捨五入
	 */
	public int getRateConversionFraction() {
		return this.rateKbn;
	}

	/**
	 * 指定通貨に対する小数点桁数を返す.
	 * 
	 * @param currencyCode 通貨コード
	 * @return 小数点桁数
	 */
	public int getCurrencyDigit(String currencyCode) {

		if (!this.currencyMap.containsKey(currencyCode)) {
			return 0;
		}

		return Integer.parseInt(currencyMap.get(currencyCode));
	}

	/**
	 * 指定通貨に対する小数点桁数を設定
	 * 
	 * @param map key:通貨コード value:小数点桁数
	 */
	public void setCurrencyDigits(Map map) {
		this.currencyMap = map;
	}

	/**
	 * 印刷時の初期値
	 * 
	 * @return 印刷時の初期値
	 */
	public int getPrintDef() {
		return printDef;
	}

	/**
	 * @param printDef
	 */
	public void setPrintDef(int printDef) {
		this.printDef = printDef;
	}

	/**
	 * 伝票印刷区分
	 * 
	 * @return 伝票印刷区分
	 */
	public int getPrintKbn() {
		return printKbn;
	}

	/**
	 * 伝票印刷区分
	 * 
	 * @param printKbn 伝票印刷区分
	 */
	public void setPrintKbn(int printKbn) {
		this.printKbn = printKbn;
	}

	/**
	 * その他データの追加(カスタマイズ用)
	 * 
	 * @param key キー
	 * @param value 値
	 */
	public void addData(String key, String value) {
		this.otherData.put(key, value);
	}

	/**
	 * 文字列変換
	 * 
	 * @return 値の文字列表現
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder("[");
		buff.append(Util.safeNull(kaiCode)).append("/");
		buff.append(Util.safeNull(kaiName)).append("/");
		buff.append(Util.safeNull(foreColor)).append("/");
		buff.append(Util.safeNull(itemName)).append("/");
		buff.append(Util.safeNull(subItemName)).append("/");
		buff.append(Util.safeNull(isUseBreakDownItem)).append("/");
		buff.append(Util.safeNull(breakDownItemName)).append("/");
		buff.append(StringUtil.toCommaString(isUseManageDivs)).append("/");
		buff.append(StringUtil.toCommaString(nonAccountingDetailDivs)).append("/");
		buff.append(StringUtil.toCommaString(nonAccountingDetailNames)).append("/");
		buff.append(String.valueOf(beginningOfPeriodMonth)).append("/");
		buff.append(Util.safeNull(baseCurrencyCode)).append("/");
		buff.append(String.valueOf(getBaseCurrencyDigit())).append("/");
		buff.append(Util.safeNull(isLedgerEachDayBalanceView));
		buff.append(String.valueOf(rateKbn));
		buff.append(String.valueOf(printDef));
		buff.append(String.valueOf(printKbn));
		buff.append(String.valueOf(directPrintKbn));
		buff.append("]");

		return buff.toString();
	}

	/**
	 * データをStringのMap形式に変換
	 * 
	 * @return Map
	 */
	public Map<String, String> toStringMap() {

		Map<String, String> map = new TreeMap<String, String>();
		map.put("kaiCode", this.kaiCode); // 会社コード
		map.put("kaiName", this.kaiName); // 会社名
		map.put("kaiNameS", this.kaiNameS); // 会社略称
		map.put("foreColor", this.foreColor); // 背景色設定

		map.put("itemName", this.itemName); // 科目名
		map.put("subItemName", this.subItemName); // 補助科目名
		map.put("bdItemFlag", BooleanUtil.toString(this.isUseBreakDownItem)); // 内訳科目利用区分
		map.put("bdItemName", this.breakDownItemName); // 内訳科目名
		map.put("baseCurrency", this.baseCurrencyCode); // 基軸通貨

		// 管理区分1～6の使用判定
		map.put("mgDivs", StringUtil.toDelimitString(this.isUseManageDivs));

		// 管理区分1～6の名称
		map.put("mgDivNames", StringUtil.toHTMLEscapeString(this.manageDivNames));

		// 非会計明細区分1～3
		map.put("nonAcDetailDivs", StringUtil.toDelimitString(this.nonAccountingDetailDivs));

		// 非会計明細名称1～3
		map.put("nonAcDetailNames", StringUtil.toHTMLEscapeString(this.nonAccountingDetailNames));

		// 期首月
		map.put("beginningOfPeriodMonth", String.valueOf(this.beginningOfPeriodMonth));

		// 元帳日別残高表示
		map.put("isLedgerEDBView", BooleanUtil.toString(this.isLedgerEachDayBalanceView));

		// レート換算端数処理区分
		map.put("rateKbn", String.valueOf(this.rateKbn));

		// 印刷時の初期値
		map.put("printDef", String.valueOf(this.printDef));

		// 伝票印刷区分
		map.put("printKbn", String.valueOf(this.printKbn));

		// 直接印刷区分
		map.put("directKbn", String.valueOf(this.directPrintKbn));

		// その他データ
		map.putAll(otherData);

		return map;
	}

	/**
	 * データを反映する.
	 * 
	 * @param map データ
	 */
	public void reflect(Map<String, String> map) {
		this.otherData = map;

		this.kaiCode = map.get("kaiCode"); // 会社コード
		this.kaiName = map.get("kaiName"); // 会社名
		this.kaiNameS = map.get("kaiNameS"); // 会社略称
		this.foreColor = map.get("foreColor"); // 背景色

		this.itemName = map.get("itemName"); // 「科目」名称
		this.subItemName = map.get("subItemName"); // 「補助科目」名称
		this.isUseBreakDownItem = BooleanUtil.toBoolean(map.get("bdItemFlag")); // 内訳科目利用区分
		this.breakDownItemName = map.get("bdItemName"); // 「内訳科目」名称
		this.baseCurrencyCode = map.get("baseCurrency"); // 基軸通貨

		// 管理区分1～6の使用判定
		String mgDivs = map.get("mgDivs");
		this.isUseManageDivs = StringUtil.toBooleanArrayFromDelimitString(mgDivs);

		// 管理区分1～6の名称
		String mgDivNames = map.get("mgDivNames");
		this.manageDivNames = StringUtil.toArrayFromHTMLEscapeString(mgDivNames);

		// 非会計明細区分
		String nonAcDetailDivs = map.get("nonAcDetailDivs");
		this.nonAccountingDetailDivs = StringUtil.toIntArrayFromDelimitString(nonAcDetailDivs);

		// 非会計明細名称
		String nonAcDetailNames = map.get("nonAcDetailNames");
		this.nonAccountingDetailNames = StringUtil.toArrayFromHTMLEscapeString(nonAcDetailNames);

		// 期首月
		try {
			this.beginningOfPeriodMonth = Integer.parseInt(map.get("beginningOfPeriodMonth"));
		} catch (NumberFormatException e) {
			this.beginningOfPeriodMonth = 4;
		}

		// 元帳日別残高表示の判定
		this.isLedgerEachDayBalanceView = BooleanUtil.toBoolean(map.get("isLedgerEDBView"));

		// レート換算端数処理区分
		this.rateKbn = Integer.parseInt(map.get("rateKbn"));

		// 印刷時の初期値
		this.printDef = Integer.parseInt(map.get("printDef"));

		// 伝票印刷区分
		this.printKbn = Integer.parseInt(map.get("printKbn"));

		// 直接印刷区分
		this.directPrintKbn = Util.avoidNullAsInt(map.get("directKbn"));
	}

	/**
	 * 指定キーに対する会社情報を返す.(カスタマイズ用)
	 * 
	 * @param key キー
	 * @return 会社情報
	 */
	public String getData(String key) {
		return this.otherData.get(key);
	}

	/**
	 * 直接印刷区分 取得
	 * 
	 * @return 直接印刷区分
	 */
	public int getDirectPrintKbn() {
		return directPrintKbn;
	}

	/**
	 * 直接印刷区分 設定
	 * 
	 * @param directPrintKbn 直接印刷区分
	 */
	public void setDirectPrintKbn(int directPrintKbn) {
		this.directPrintKbn = directPrintKbn;
	}

}
