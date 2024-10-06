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
 * 取引先フィールドのコントロール
 * 
 * @author roh
 */
public class TCustomerFieldCtrl extends TAppletClientBase {

	/** 取引先フィールド */
	protected TCustomerField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0150CustomerMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField
	 */
	public TCustomerFieldCtrl(TCustomerField itemField) {
		this.field = itemField;
	}

	/**
	 * 取引先への値セット
	 * 
	 * @return boolean
	 */
	public boolean setCustomerNotice() {

		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// 取引先名称の習得
			TRI_MST bean = findData(code);

			// チェックモードオンの場合
			if (field.isCheckMode()) {

				// 結果無しの場合
				if (bean == null) {
					// 借入先と取引先の表示切替
					if (field.isLoanCustomer()) {
						showMessage(field, "W00081", "C02689");
					} else {
						showMessage(field, "W00081", "C00786");
					}
					return returnFalse();
				}

				// 有効期間チェック
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());

					if (termDate.after(bean.getEND_DATE()) || bean.getSTR_DATE().after(termDate)) {
						if (!showConfirmMessage(field, "Q00025", "C00408")) {
							return returnFalse();
						} else {
							field.requestTextFocus();
						}
					}
				}

				// 仕入れフラグ
				if (field.isSiire() && !field.isTokui()) {
					if (0 == bean.getSIIRE_KBN()) {
						if (!showConfirmMessage(field.getParentFrame(), "Q00026", "C01089")) {
							return returnFalse();
						}
					}
				}

				// 得意先フラグ
				if (field.isTokui() && !field.isSiire()) {
					if (0 == bean.getTOKUI_KBN()) {
						if (!showConfirmMessage(field.getParentFrame(), "Q00026", "C01261")) {
							return returnFalse();
						}
					}
				}

				// 仕入れフラグOR得意先フラグ
				if (field.isTokui() && field.isSiire()) {
					if (0 == bean.getTOKUI_KBN() && 0 == bean.getSIIRE_KBN()) {
						if (!showConfirmMessage(field.getParentFrame(), "Q00026", getWord("C01089") + ","
							+ getWord("C01261"))) {
							return returnFalse();
						}
					}
				}
			}
			// 名称セット
			field.setNoticeValue(bean != null ? bean.getTRI_NAME_S() : "");

			return true;

		} catch (TRequestException e) {
			errorHandler(field);
			return returnFalse();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
			return returnFalse();
		}
	}

	/**
	 * 略称をクリアしてfalseを返す
	 * 
	 * @return false
	 */
	protected boolean returnFalse() {
		field.setNoticeValue("");
		field.clearOldText();
		field.requestTextFocus();
		return false;
	}

	/**
	 * フィールドデータ取得
	 * 
	 * @param code 取引先コード
	 * @return 詳細
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected TRI_MST findData(String code) throws TRequestException, IOException {

		addSendValues("flag", "CustomerInfo"); // 区分flag

		if (Util.isNullOrEmpty(field.getCompanyCode())) {
			field.setCompanyCode(getLoginUserCompanyCode());
		}
		addSendValues("companyCode", field.getCompanyCode());
		addSendValues("customerCode", code);

		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		return (TRI_MST) getResultObject();
	}

	/**
	 * ダイアログ表示
	 */
	public void showSearchDialog() {
		try {

			Map<String, String> initParam = new HashMap<String, String>();

			if (field.isLoanCustomer()) {
				initParam = getLoanDialogParam();
			} else {
				initParam = getSearchDialogParam();
			}

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.CUSTOMER_MST,
				initParam);

			searchdialog.setStartCode(field.getBeginCode()); // 開始コード
			searchdialog.setEndCode(field.getEndCode()); // 終了コード
			searchdialog.setServerName("refCustomer");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			// 仕入れ、得意Setterのboolean値を設定。
			otherParam.put("siire", BooleanUtil.toString(field.isSiire()));
			otherParam.put("tokui", BooleanUtil.toString(field.isTokui()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("beginCode", field.getBeginCode());// 開始コード
			otherParam.put("endCode", field.getEndCode()); // 終了コード
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// 検索ダイアログを表示

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

		param.put("top", "C01676"); // topのセット
		param.put("code", "C00786"); // コードのセット
		param.put("nameS", "C00787"); // 略称
		param.put("nameK", "C00836"); // 索引略称

		return param;
	}

	/**
	 * 借入先検索dialog初期値のセット
	 * 
	 * @return param タイトル
	 */
	protected Map<String, String> getLoanDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", (getWord("C02690") + getWord("C02406"))); // topのセット //マスタ一覧
		param.put("code", "C02689"); // コードのセット
		param.put("nameS", getWord("C02690") + getWord("C00548")); // 略称
		param.put("nameK", getWord("C02690") + getWord("C00160")); // 索引名称

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
			field.setValue(info[0]); // コードをセットした
			field.setNoticeValue(info[1]); // 名称をセットした
			field.getField().requestFocus();
			return true;
		}

		field.getField().requestFocus();

		return false;
	}

	/**
	 * Noticeフィールド：ロストフォーカス処理
	 * 
	 * @return boolean
	 */
	public boolean noticeLostFocus() {
		// 実装無し
		return true;
	}
}
