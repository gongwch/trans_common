package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 銀行口座フィールド拡張コントロール
 */
public class TBankAccountEnhancingFieldCtrl extends TBankAccountFieldCtrl {

	private TBankAccountEnhancingField field;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MP0030BankAccountMasterServlet";

	/**
	 * コンストラクタ
	 * 
	 * @param field
	 */
	public TBankAccountEnhancingFieldCtrl(TBankAccountEnhancingField field) {
		super(field);
		this.field = field;
	}

	/**
	 * デフォルトの支払い条件を取得
	 */
	public void setDefaultReceivedAccount() {
		// 支払条件マスタデータを検索
		Map<String, String> map = getDefaultReceivedAccount();

		field.setValue(Util.avoidNull(map.get("cbkCode")));
		field.setNoticeValue(Util.avoidNull(map.get("cbkName")));

	}

	/**
	 * 支払先銀行、口座番号を検索
	 * 
	 * @return map 支払先銀行、口座番号
	 */
	private Map<String, String> getDefaultReceivedAccount() {
		try {
			// 送信するパラメータを設定
			addSendValues("flag", "defaultReceivedAccount");
			// 会社コード
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}

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
