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
import jp.co.ais.trans2.common.ui.*;

/**
 * 計上会社フィールドのコントロール
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyFieldCtrl extends TAppletClientBase {

	/** 計上会社フィールド */
	protected TAppropriateCompanyField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "TAppropriateCompanyFieldServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField
	 */
	public TAppropriateCompanyFieldCtrl(TAppropriateCompanyField itemField) {
		this.field = itemField;
	}

	/**
	 * 計上会社への値セット
	 * 
	 * @return boolean
	 */
	public boolean setCompanyInfo() {

		try {

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(field.getValue())) {
				field.setNoticeValue("");
				field.clearOldText();

				return true;
			}

			// 計上会社データ
			List<AppropriateCompany> list = getFieldDataList();

			// 結果無しの場合
			if (list.isEmpty()) {
				if (!field.isFocusOut()) {
					showMessage(field, "I00051");
					field.setNoticeValue("");
					field.clearOldText();
					field.requestTextFocus();

				}
				field.setBln(false);
				return false;
			}

			// 有効期間チェック
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate(list.get(0).getSTR_DATE());
				Date endDate = DateUtil.toYMDDate(list.get(0).getEND_DATE());
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!field.isFocusOut()) {
						if (!showConfirmMessage(field, "Q00046", "C01052")) {
							field.clearOldText();
							field.requestTextFocus();
							field.setBln(false);
							return false;
						}
					} else {
						field.setBln(false);
						return false;
					}
				}
			}

			field.setNoticeValue(list.get(0).getKAI_NAME_S());
			field.setBln(true);
			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @return 詳細リスト
	 * @throws TException
	 */
	public List<AppropriateCompany> getFieldDataList() throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "find"); // 区分flag

			AppropriateCompany param = new AppropriateCompany();
			// 本邦通貨コード
			param.setCUR_CODE(TLoginInfo.getCompany().getAccountConfig().getKeyCurrency().getCode());
			// 会社コード
			param.setKAI_CODE(field.getValue());
			// 操作区分
			param.setBlnOptKbn(false);
			// 開始会社コード
			param.setStrCompanyCode(field.getStrCompanyCode());
			// 開始会社コード
			param.setEndCompanyCode(field.getEndCompanyCode());
			// 通貨区分
			param.setCurKbn(field.isCurKbn());

			addSendDto(param);
			if (!request(TARGET_SERVLET)) {
				throw new TRequestException();
			}

			return this.getResultDtoList(AppropriateCompany.class);

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * ダイアログ表示
	 * 
	 * @return boolean
	 */
	public boolean showSearchDialog() {
		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.APP_KAI, initParam);
			searchdialog.setCurKbn(field.isCurKbn());
			searchdialog.setBeginCode(field.getStrCompanyCode());
			searchdialog.setEndCode(field.getEndCompanyCode());

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// 検索ダイアログを表示
			boolean isSettled = showDialog(searchdialog);

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
			return false;
		}

	}

	/**
	 * 検索dialog初期値のセット
	 * 
	 * @return param タイトル
	 */
	protected Map<String, String> getSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C01686"); // topのセット
		param.put("code", "C00596"); // コードのセット
		param.put("nameS", "C00686"); // 略称

		return param;
	}

	/**
	 * 検索ダイアログ表示<BR>
	 * 
	 * @param dialog ダイアログ
	 * @return true 確定の場合 false 取消の場合
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// 確定の場合
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			field.setValue(info[0]);
			field.getField().requestFocus();
			return true;
		}
		field.getField().requestFocus();
		return false;
	}

}
