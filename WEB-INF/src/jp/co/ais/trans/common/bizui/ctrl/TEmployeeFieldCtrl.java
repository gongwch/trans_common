package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 社員検索フィールドのコントロール
 * 
 * @author roh
 */
public class TEmployeeFieldCtrl extends TAppletClientBase {

	/** 社員検索 フィールド */
	protected TEmployeeField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0160EmployeeMasterServlet";

	/** 処理サーブレット（ユーザロジック対応） */
	protected static final String TARGET_SERVLET_USER = "MG0020UserMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** 有効期間フラグ */
	public boolean termBasisDateFlag = true;

	/**
	 * コンストラクタ
	 * 
	 * @param itemField (画面）
	 */
	public TEmployeeFieldCtrl(TEmployeeField itemField) {
		this.field = itemField;
	}

	/**
	 * ローストフォーカスの対応
	 * 
	 * @return boolean
	 */
	public boolean setEmployeeNotice() {

		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// 社員コードでデータ取得
			List list = getFieldDataList(code);

			// 結果無しの場合
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00697");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}

			if (termBasisDateFlag) {
				// 有効期間のフラグ
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(2));
					Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));

					if (termDate.after(endDate) || strDate.after(termDate)) {
						// 対話ダイアログを開き、続行の可否を問う
						if (!showConfirmMessage(field, "Q00025", "C00246")) {
							field.requestTextFocus();
							return false;
						}
					}
				}
			}

			// ユーザ登録フラグが存在するとき
			if (field.isUser()) {
				// ユーザデータを習得
				List userList = getFieldUserDataList(code);
				if (userList.isEmpty()) {
					showMessage(field, "W01006");
					return false;
				}

				// ユーザ登録された人の部門設定があるとき
				if (!Util.isNullOrEmpty(field.getDepartmentCode())) {
					List userRow = (List) userList.get(0);
					if (!field.getDepartmentCode().equals(userRow.get(1))) {
						showMessage(field, "W01007");
						return false;
					}
				}
			}

			StringBuilder bfieldName = new StringBuilder();
			bfieldName.append((String) ((List) list.get(0)).get(1));

			// 詳細フィールドに値を設定
			field.setNoticeValue(bfieldName.toString());

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
	protected List getFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "refEmployee"); // 区分flag
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			addSendValues("kaiCode", field.getCompanyCode());
			addSendValues("empCode", code);
			if (!request(TARGET_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}
			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * フィールドデータリスト取得(ユーザマスタ対応）
	 * 
	 * @param code ユーザコード
	 * @return 詳細リスト
	 * @throws TException
	 */
	protected List getFieldUserDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "refEmployee"); // 区分flag
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			addSendValues("kaiCode", field.getCompanyCode());
			addSendValues("usrCode", code);
			if (!request(TARGET_SERVLET_USER)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}
			return getResultList();

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
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.EMP_SEARCH_MST,
				initParam);
			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refEmployee");

			// 検索用パラメータをダイアログに設定する。
			Map<String, String> otherParam = new HashMap<String, String>();

			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());
			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("user", String.valueOf(field.isUser()));
			otherParam.put("depCode", field.getDepartmentCode());
			otherParam.put("beginCode", field.getBeginCode());// 開始コード
			otherParam.put("endCode", field.getEndCode()); // 終了コード
			searchdialog.setParamMap(otherParam);

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

		param.put("top", "C01677"); // topのセット
		param.put("code", "C00697"); // コードのセット
		param.put("nameS", "C00808"); // 略称
		param.put("nameK", "C00809"); // 索引略称

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
