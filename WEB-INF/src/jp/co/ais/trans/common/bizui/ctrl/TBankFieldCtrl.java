package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 銀行検索フィールドのコントロール
 * 
 * @author roh
 */
public class TBankFieldCtrl extends TAppletClientBase {

	/** 銀行検索 フィールド */
	protected TBankField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0140BankMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** 有効期間フラグ */
	public boolean termBasisDateFlag = true;

	/**
	 * コンストラクタ
	 * 
	 * @param itemField (画面）
	 */
	public TBankFieldCtrl(TBankField itemField) {
		this.field = itemField;
	}

	/**
	 * ローストフォーカスの対応
	 * 
	 * @return boolean
	 */
	public boolean setBankNotice() {

		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// 社員コードでデータ取得
			BNK_MST bnkMst = getFieldDataList(code);

			// 結果無しの場合
			if (bnkMst == null) {
				showMessage(field, "W00081", "C00779");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}

			if (termBasisDateFlag) {
				// 有効期間のフラグ
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					Date strDate = bnkMst.getSTR_DATE();
					Date endDate = bnkMst.getEND_DATE();

					if (termDate.after(endDate) || strDate.after(termDate)) {
						// 対話ダイアログを開き、続行の可否を問う
						if (!showConfirmMessage(field, "Q00025", "C00246")) {
							field.requestTextFocus();
							return false;
						}
					}
				}
			}

			// 詳細フィールドに値を設定
			field.setNoticeValue(bnkMst.getBNK_NAME_S());

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param code 社員コード
	 * @return 詳細リスト
	 * @throws TException
	 */
	protected BNK_MST getFieldDataList(String code) throws TException {

		try {
			BnkMstParameter param = new BnkMstParameter();
			param.setBnkCode(code);

			// 送信するパラメータを設定
			addSendValues("flag", "nameBank"); // 区分flag
			addSendObject(param); // 検索パラメータ
			if (!request(TARGET_SERVLET)) {
				// クライアント 受信解析エラー。
				throw new TException("S00002");
			}

			BNK_MST bnkmst = (BNK_MST) getResultObject();
			if (bnkmst == null) {
				return null;
			}

			return bnkmst;

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * ダイアログ構成
	 */
	public void showSearchDialog() {

		try {
			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BNK_BNK, initParam);
			searchdialog.setServerName("BNK_BNK_MST"); // 検索Flag

			// 検索パラメータ
			BnkMstParameter param = new BnkMstParameter();
			param.setBnkCodeBegin(field.getBeginCode());
			param.setBnkCodeEnd(field.getEndCode());
			param.setTermBasisDate(DateUtil.toYMDDate(field.getTermBasisDate()));
			searchdialog.setParamObject(param);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			showDialog(searchdialog);

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}

	}

	/**
	 * 検索dialog初期値のセット
	 * 
	 * @return param タイトル
	 */
	protected Map<String, String> getSearchDialogParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("top", getWord("C00124") + getWord("C00010")); // 銀行一覧
		param.put("code", "C00779"); // コードのセット
		param.put("nameS", "C00781"); // 銀行名称
		param.put("nameK", "C00829"); // 銀行検索名称

		return param;
	}

	/**
	 * 検索ダイアログ表示<BR>
	 * 
	 * @param dialog ダイアログ
	 * @return boolean : true 確定の場合 false 取消の場合
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// 確定の場合
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			field.setValue(info[0]); // コードをセットする
			field.setNoticeValue(info[1]); // 名称をセットする
			field.getField().requestFocus();
			return true;
		}
		field.getField().requestFocus();
		return false;
	}

}
