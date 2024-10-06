package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 支払条件フィールド拡張コントロール
 */
public class TPaymentConditionEnhancingFieldCtrl extends TPaymentConditionFieldCtrl {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0155CustomerConditionMasterServlet";

	/** field */
	private TPaymentConditionEnhancingField field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TPaymentConditionEnhancingFieldCtrl(TPaymentConditionEnhancingField field) {
		super(field);
		this.field = field;
	}

	/**
	 * 支払先条件のローストフォーカス処理
	 */
	@Override
	public boolean setPaymentConditionNotice() {

		try {
			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(field.getValue())) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			Map<String, String> map = getPaymentConditionInfo();

			// 結果無しの場合
			if (Util.isNullOrEmpty(map.get("tjkcode"))) {
				showMessage(field, "W00081", "C00672");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				setParameter(map);
				return false;
			}
			// 有効期間のフラグ
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = null;
				Date strDate = null;
				Date endDate = null;

				termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				strDate = DateUtil.toYMDDate(Util.avoidNull(map.get("strDate")));
				endDate = DateUtil.toYMDDate(Util.avoidNull(map.get("endDate")));

				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(field, "Q00025", "C00238")) {
						// 有効期限フラグをfalse
						field.setIsTermBasisDate(false);
						field.requestTextFocus();
						return false;
					}
				}
			}

			// 有効期限フラグをtrue
			field.setIsTermBasisDate(true);
			field.requestTextFocus();

			setParameter(map);

			field.setNoticeValue(Util.avoidNull(map.get("bnkname")));

			return true;

		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}
	}

	/**
	 * 必要情報を設定
	 * 
	 * @param map 情報
	 */
	private void setParameter(Map<String, String> map) {
		PaymentInformationParameter param = field.getParameter();
		// 初期化
		param.clear();
		// 支払方法コード
		param.setSihaHouCode(Util.avoidNull(map.get("sihahouCode")));
		// 支払方法名称
		param.setSihaHouName(Util.avoidNull(map.get("sihahouName")));
		// 振出銀行コード
		param.setHuriCode(Util.avoidNull(map.get("huricode")));
		// 振出銀行名称
		param.setHuriName(Util.avoidNull(map.get("huriname")));
		// 支払区分
		param.setSihaKbn(Util.avoidNull(map.get("sihakbn")));
		// 支払日
		param.setSihaDate(Util.avoidNull(map.get("sihaDate")));
		// 支払内部コード
		param.setSihaNaiCode(Util.avoidNull(map.get("naicode")));
		// データ有りかの判定
		if ("0".equals(Util.avoidNull(map.get("flag")))) {
			// データなし
			param.setFlag(false);
		} else {
			// データ有り
			param.setFlag(true);
		}
	}

	/**
	 * デフォルトの支払い条件を取得
	 */
	public void setDefaultPaymentCondition() {
		// 支払条件マスタデータを検索
		Map<String, String> map = getDefaultPaymentCondition();

		field.setValue(Util.avoidNull(map.get("tjkCode")));
		field.setNoticeValue(Util.avoidNull(map.get("bnkname")));
		setPaymentConditionNotice();
	}

	/**
	 * 支払先銀行、口座番号を検索
	 * 
	 * @return 支払先銀行、口座番号
	 */
	private Map<String, String> getPaymentConditionInfo() {
		try {
			// 送信するパラメータを設定
			addSendValues("flag", "paymentConditionInfo");
			// 会社コード
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			// 支払先コード
			addSendValues("triCode", Util.avoidNull(field.getCustomerCode()));
			// 支払先条件コード
			addSendValues("tjkCode", Util.avoidNull(field.getValue()));
			// 伝票日付
			addSendValues("slipDate", DateUtil.toYMDString(field.getSlipDate()));

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(field);
			}

			return getResult();
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(field, e, "E00009");
			return null;
		}
	}

	/**
	 * 支払先銀行、口座番号を検索
	 * 
	 * @return 支払先銀行、口座番号
	 */
	private Map<String, String> getDefaultPaymentCondition() {
		try {
			// 送信するパラメータを設定
			addSendValues("flag", "defaultPaymentCondition");
			// 会社コード
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// 支払先コード
			addSendValues("triCode", Util.avoidNull(field.getCustomerCode()));
			// 支払先条件コード
			addSendValues("curCode", Util.avoidNull(field.getCurrencyCode()));

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(field);
			}

			return getResult();

		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(field, e, "E00009");
			return null;
		}
	}
}
