package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * 通貨換算フィールドコントロール
 */
public class TCurrencyExchangeFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** フィールド */
	protected TCurrencyExchangeField panel;

	/** 選択通貨の少数点以下桁数 */
	protected int digit = 0;

	/** 基軸通貨の少数点以下桁数 */
	protected int baseDigit = 0;

	/** 基軸通貨コード */
	protected String baseCurrencyCode = "";

	/** 通貨レート */
	protected String currencyRate = "";

	/** 通貨コードの桁数 */
	protected int currencyCodeDigit = 0;

	/**
	 * コンストラクタ
	 * 
	 * @param panel フィールド
	 */
	public TCurrencyExchangeFieldCtrl(TCurrencyExchangeField panel) {
		this.panel = panel;

		// 画面の初期化を行う
		initCtrlValue();
	}

	/**
	 * 画面の初期化を行う<BR>
	 * コンポーネントの制御、初期値の設定
	 */
	public void initCtrlValue() {
		// 会社情報取得
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// ログイン会社の基軸通貨を取得する
		baseCurrencyCode = compInfo.getBaseCurrencyCode();

		// ログイン会社の基軸通貨の小数点桁数を取得する
		digit = compInfo.getBaseCurrencyDigit();
		baseDigit = compInfo.getBaseCurrencyDigit();

		// 通貨コードに基軸通貨コードをセット
		panel.getCurrencyField().setValue(compInfo.getBaseCurrencyCode());

		// 入金金額を入力可
		panel.getNumInputAmount().setValue(NumberFormatUtil.formatNumber("0", digit));
		panel.getNumInputAmount().setEditable(true);

		// 基準金額は入力不可
		panel.getNumBaseCurrencyAmount().setValue(NumberFormatUtil.formatNumber("0", baseDigit));
		panel.getNumBaseCurrencyAmount().setEditable(false);

		panel.getCurrencyField().setTermBasisDate(DateUtil.toYMDString(panel.getSelectedDate()));
	}

	/**
	 * 通貨コードロストフォーカス処理<BR>
	 * 
	 * @param currencyCode
	 */
	public void currencyFieldFocusLost(String currencyCode) {
		try {
			// 通貨コードブランクの場合
			if (Util.isNullOrEmpty(currencyCode)) {
				// レートをブランクで設定する。
				panel.getNumRate().setValue("");
				// 基準金額はブランクでセットする。
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);

				return;
			}

			// 日付データ取得
			String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

			// 通貨コードとレート基準日付を元に通貨データを取得する
			Map<String, String> map = findCurData(currencyCode, rateBaseDate);

			if (map.isEmpty()) {
				// レートをブランクで設定する。
				panel.getNumRate().setValue("");
				// 基準金額はブランクでセットする。
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
			}

			// 操作フラグ
			map.put("OperationFlag", "currencyField");
			// 通貨データを元に各フィールドの値を取得
			setEachFieldByCurData(map);
			if (!"".equals(Util.avoidNull(map.get("DEC_KETA")))) {
				currencyCodeDigit = Integer.parseInt(Util.avoidNull(map.get("DEC_KETA")));
			}
		} catch (TRequestException ex) {
			errorHandler(panel);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * レートロストフォーカス<BR>
	 * 
	 * @return true:正常処理
	 */
	public boolean numRateLostFocus() {
		try {
			// 値が同じ時は何もしない
			if (!panel.getNumRate().isValueChanged()) {
				return true;
			}

			// 通貨コードを取得
			String currencyCode = panel.getCurrencyField().getValue();

			// レート、通貨コードがブランクの場合
			if (Util.isNullOrEmpty(panel.getNumRate().getValue()) || Util.isNullOrEmpty(currencyCode)) {
				// 基準金額はブランクでセットする。
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
				return true;
			}

			// 日付データ取得
			String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

			// 通貨コードとレート基準日付を元に通貨データを取得する
			Map<String, String> map = findCurData(currencyCode, rateBaseDate);

			if (map.isEmpty()) {
				// 基準金額はブランクでセットする。
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
			}

			// 通貨データを元に各フィールドの値を取得
			setEachFieldByCurData(map);

		} catch (TRequestException ex) {
			errorHandler(panel);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
		return true;
	}

	/**
	 * 入力金額ロストフォーカス時の合計金額計算
	 */
	public void numInputAmountLostFocus() {
		try {

			// 値が同じ時はフォーマットのみ行う
			if (!panel.getNumInputAmount().isValueChanged()) {
				String amount = panel.getNumInputAmount().getValue();
				if (!panel.getNumInputAmount().isFormatterExist()) {
					panel.getNumInputAmount().setValue(NumberFormatUtil.formatNumber(amount, currencyCodeDigit));
				} else {
					if (Util.isNullOrEmpty(amount)) {
						amount = "0";
					}
					panel.getNumInputAmount().setValue(amount);
				}

				return;
			}

			// 金額オーバーフローチェックをする
			checkOverAmount();

			// 通貨コード
			String currencyCode = Util.avoidNull(panel.getCurrencyField().getValue());

			// 日付データ取得
			String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

			// データ取得
			Map<String, String> map = findCurData(currencyCode, rateBaseDate);

			// 通貨データを元にフィールドの値を取得
			setEachFieldByCurData(map);

		} catch (TRequestException ex) {
			errorHandler(panel);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 基準金額フィールドロストフォーカス時
	 */
	public void numBaseCurrencyAmountLostFocus() {

		// 金額オーバーフローチェックをする
		checkOverAmount();
		// 基準金額を取得
		String text = panel.getNumBaseCurrencyAmount().getValue();
		// 邦貨金額をフォーマットする]
		if (!panel.getNumInputAmount().isFormatterExist()) {
			panel.getNumBaseCurrencyAmount().setValue(NumberFormatUtil.formatNumber(text, baseDigit));
		} else {
			if (Util.isNullOrEmpty(text)) {
				text = "0";
			}
			panel.getNumBaseCurrencyAmount().setValue(text);
		}

	}

	/**
	 * 金額オーバーフローチェックをする
	 */
	public void checkOverAmount() {

		// 入力金額オーバーフローチェック
		if (Util.isOverAmount(panel.getNumInputAmount().getValue())) {
			panel.getNumInputAmount().setValue(""); // 入力金額
			panel.getNumBaseCurrencyAmount().setValue(""); // 基準金額
			showMessage(panel, "W00211");
			panel.getNumInputAmount().clearOldText();
			panel.getNumInputAmount().requestFocus();
			return;
		}

		// 邦貨金額オーバーフローチェック
		if (Util.isOverAmount(panel.getNumBaseCurrencyAmount().getValue())) {
			panel.getNumBaseCurrencyAmount().setValue(""); // 基準金額
			showMessage(panel, "W00211");
			panel.getNumBaseCurrencyAmount().clearOldText();
			panel.getNumBaseCurrencyAmount().requestFocus();
			return;
		}

	}

	/**
	 * 通貨データを元に各フィールドの値を取得
	 * 
	 * @param map 通貨データ
	 * @throws IOException
	 * @throws TRequestException
	 * @throws NumberFormatException
	 */
	protected void setEachFieldByCurData(Map<String, String> map) throws IOException, NumberFormatException,
		TRequestException {

		// 小数点以下桁数
		String strDecKeta = Util.avoidNull(map.get("DEC_KETA"));
		// 操作フラグ
		String StrOperationFlag = Util.avoidNull(map.get("OperationFlag"));

		// 小数点以下桁数
		digit = 0;
		if (!"".equals(strDecKeta)) {
			digit = Integer.parseInt(strDecKeta);
		}

		// 小数点以下桁数をセット
		panel.setDigit(digit);

		// 通貨コード
		String currencyCode = Util.avoidNull(panel.getCurrencyField().getValue());
		// レート基準日付
		String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

		if (!Util.isNullOrEmpty(currencyCode)) {

			// レートの取得
			currencyRate = "";
			// レートを直接変更した場合
			if ("".equals(StrOperationFlag) && !Util.isNullOrEmpty(panel.getNumRate().getValue())) {
				currencyRate = Util.avoidNull(panel.getNumRate().getValue());
			} else {
				// 基軸通貨の場合
				currencyRate = "1";

				if (!baseCurrencyCode.equals(panel.getCurrencyField().getValue())) {
					// レート基準日付のレート取得
					currencyRate = getForeignRate(currencyCode, rateBaseDate);
				}
			}

			// レートが存在しない場合
			if ("-1.0".equals(currencyRate) || Util.isNullOrEmpty(panel.getCurrencyField().getValue())) {
				// レートをブランクで設定する
				panel.getNumRate().setValue("");
				panel.getNumRate().setEditable(true);
				// 基準金額はブランクでセットする
				panel.getNumBaseCurrencyAmount().setValue("");
				panel.getNumBaseCurrencyAmount().setEditable(false);
				return;
			}

			panel.getNumRate().setEditable(true);
			panel.getNumRate().setValue(currencyRate);
		}

		// 入力金額と基準金額を求める
		calcAmount();
	}

	/**
	 * 入力金額と邦貨金額のを求める
	 * 
	 * @throws IOException
	 * @throws TRequestException
	 * @throws NumberFormatException
	 */
	protected void calcAmount() throws NumberFormatException, TRequestException, IOException {

		// 通貨コード
		String currencyCode = panel.getCurrencyField().getValue();

		if (Util.isNullOrEmpty(currencyCode) || Util.isNullOrEmpty(panel.getNumRate().getValue())) {
			panel.getNumInputAmount().setValue(
				NumberFormatUtil.formatNumber(panel.getNumInputAmount().getValue(), currencyCodeDigit));
			return;
		}

		// レート基準日付
		String rateBaseDate = DateUtil.toYMDString(panel.getSelectedDate());

		// 入金金額フォーマットしてセットする
		String inputAmount = Util.avoidNull(panel.getNumInputAmount().getValue());
		if (!panel.getNumInputAmount().isFormatterExist()) {
			inputAmount = NumberFormatUtil.formatNumber(inputAmount, digit);
			panel.getNumInputAmount().setValue(inputAmount);
		} else {
			if (Util.isNullOrEmpty(inputAmount)) {
				inputAmount = "0";
			}
			panel.getNumInputAmount().setValue(inputAmount);
		}

		// 基準金額に対し、入力金額を基軸通貨換算した値をセットする
		String baseCurrencyAmount = inputAmount.replace(",", "");

		if (!baseCurrencyCode.equals(currencyCode)) {
			baseCurrencyAmount = convertForeign(baseCurrencyAmount, Double.valueOf(currencyRate), rateBaseDate,
				currencyCode);
		}

		panel.getNumBaseCurrencyAmount().setEditable(true);
		if (!panel.getNumInputAmount().isFormatterExist()) {
			panel.getNumBaseCurrencyAmount().setValue(NumberFormatUtil.formatNumber(baseCurrencyAmount, baseDigit));
		} else {
			if (Util.isNullOrEmpty(baseCurrencyAmount)) {
				baseCurrencyAmount = "0";
			}
			panel.getNumBaseCurrencyAmount().setValue(baseCurrencyAmount);
		}
		// 基軸通貨と入力された通貨コードが一致する場合
		if (baseCurrencyCode.equals(currencyCode)) {
			panel.getNumRate().setEditable(false);
			panel.getNumBaseCurrencyAmount().setEditable(false);
		}

		// 金額オーバーフローチェックをする
		checkOverAmount();
	}

	/**
	 * 通貨コードとレート基準日付を元に通貨データを取得する
	 * 
	 * @param curCode 通貨コード
	 * @param rateBaseDate 日付
	 * @return 通貨データ
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Map<String, String> findCurData(String curCode, String rateBaseDate) throws TRequestException,
		IOException {
		// 通貨フラグ
		addSendValues("FLAG", "FindCurData");
		// 通貨コード
		addSendValues("CUR_CODE", curCode);
		// レート基準日付
		addSendValues("DATE", rateBaseDate);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		// データ取得
		return getResult();
	}

	/**
	 * 外貨を基軸通貨に換算する
	 * 
	 * @param money 外貨金額
	 * @param rate 換算レート
	 * @param rateBaseDate
	 * @param curCode
	 * @return 換算金額
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected String convertForeign(String money, double rate, String rateBaseDate, String curCode)
		throws TRequestException, IOException {

		if (Util.isNullOrEmpty(rate) || baseCurrencyCode.equals(curCode)) {
			return money;
		}

		// 外貨を基軸通貨に換算フラグ
		addSendValues("FLAG", "convertForeign");
		// レート基準日付
		addSendValues("rateBaseDate", rateBaseDate);
		// 基軸通貨ｺｰﾄﾞ
		addSendValues("baseCurCode", baseCurrencyCode);
		// 通貨コード
		addSendValues("foreginCurCode", curCode);
		// 外貨金額
		addSendValues("money", String.valueOf(money));
		// 換算レート
		addSendValues("rate", String.valueOf(rate));

		// 送信
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		// データ取得
		Map<String, String> map = getResult();
		return Util.avoidNull(map.get("baseMoney"));
	}

	/**
	 * 外貨の場合、レートを取得する
	 * 
	 * @param curCode 通貨コード
	 * @param date 指定日付
	 * @return レート
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected String getForeignRate(String curCode, String date) throws IOException, TRequestException {
		// レート取得フラグ
		addSendValues("FLAG", "FindRate");
		// 通貨コード
		addSendValues("CUR_CODE", curCode);
		// 指定日付
		addSendValues("OCCUR_DATE", date);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		// データ取得
		Map<String, String> map = getResult();

		return Util.avoidNull(map.get("CUR_RATE"));
	}
}