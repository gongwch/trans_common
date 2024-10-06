package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 摘要共通フィールドのコントロール
 * 
 * @author roh
 */
public class TSlipMemoFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0120MemoMasterServlet";

	/** field */
	private TSlipMemoField field;

	/**
	 * コンストラクタ
	 * 
	 * @param itemField
	 */
	public TSlipMemoFieldCtrl(TSlipMemoField itemField) {
		this.field = itemField;
	}

	/**
	 * ローストフォーカスの対応
	 * 
	 * @return boolean
	 */
	public boolean slipMomoLostFocus() {
		try {
			// 入力された値を取得
			String code = field.getValue();

			int type = field.getMemoType();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.clearOldText();
				return true;
			}

			// 摘要データ習得
			List list = getFieldDataList(code, type);

			// 結果無しの場合
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00564");
				field.clearOldText();
				return false;
			}

			// 有効期間のフラグ
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));
				Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(4));

				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(field, "Q00025", "C00384")) {
						return false;
					}
				}
			}

			// 値を表示する。
			field.setNoticeValue((String) ((List) list.get(0)).get(1));

			return true;

		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}
	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param type 適用タイプ
	 * @param code コード
	 * @return 詳細リスト
	 * @throws IOException
	 */
	private List getFieldDataList(String code, int type) throws IOException {

		// 送信するパラメータを設定
		addSendValues("flag", "refMemo"); // 区分flag

		if (!Util.isNullOrEmpty(field.getCompanyCode())) {
			addSendValues("kaiCode", field.getCompanyCode());
		} else {
			addSendValues("kaiCode", getLoginUserCompanyCode());
		}

		// 摘要コード ：入力値
		addSendValues("tekCode", code);
		// 摘要区分
		addSendValues("tekKbn", String.valueOf(type));

		if (!request(TARGET_SERVLET)) {
			// クライアント 受信解析エラー。
			errorHandler(field, "S00002");
		}

		return getResultList();
	}

	/**
	 * ダイアログ表示設定
	 */
	public void btnActionPerformed() {
		try {
			// パラメーターのセット
			Map<String, String> initParam = getSearchDialogParam(field.getMemoType());

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.MEMO_MST, initParam);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refMemo");

			Map<String, String> otherParam = new HashMap<String, String>();

			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}

			otherParam.put("companyCode", field.getCompanyCode());

			// データタイプ
			otherParam.put("slipType", field.getSlipDataType());

			// 摘要区分
			otherParam.put("memoType", String.valueOf(field.getMemoType()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (!Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			// 検索ダイアログを表示
			searchdialog.show();

			// 確定の場合
			if (searchdialog.isSettle()) {
				String[] info = searchdialog.getCurrencyInfo();
				// コードをセットする
				field.setValue(info[0]);
				// 名称をセットする
				field.setNoticeValue(info[1]);
			}

			field.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * ダイアログ画面のタイトル名設定
	 * 
	 * @param type 摘要タイプ
	 * @return Map タイトル
	 */
	private Map<String, String> getSearchDialogParam(int type) {
		Map<String, String> param = new HashMap<String, String>();

		// 伝票摘要
		if (type == TSlipMemoField.SLIP_DATA) {
			param.put("top", "C01672"); // topのセット
			param.put("code", "C01652"); // コードのセット
			param.put("nameS", "C01653"); // 略称
			param.put("nameK", "C01654"); // 索引略称
			// 行摘要
		} else if (type == TSlipMemoField.MEMO_DATA) {
			param.put("top", "C01673"); // topのセット
			param.put("code", "C01560"); // コードのセット
			param.put("nameS", "C01655"); // 略称
			param.put("nameK", "C01656"); // 索引略称
		}

		return param;
	}
}
