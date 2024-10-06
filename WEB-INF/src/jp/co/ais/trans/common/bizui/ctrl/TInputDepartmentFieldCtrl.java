package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 入力部門フィールドコントロール
 */
public class TInputDepartmentFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "TReferenceAuthorityServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TInputDepartmentField field;

	/** コード(文字) */
	protected String codeLabel = getWord("C00174");

	/**
	 * コンストラクタ
	 * 
	 * @param inputDepartmentField フィールド
	 */
	public TInputDepartmentFieldCtrl(TInputDepartmentField inputDepartmentField) {

		this.field = inputDepartmentField;
	}

	/**
	 * 初期情報セット
	 */
	public void setInit() {
		try {
			Map map = new HashMap();
			map = searchInit();

			field.setValue(Util.avoidNull(map.get("bmnCode")));
			field.setNoticeValue(Util.avoidNull(map.get("bmnNameS")));
			int updKen = Integer.valueOf(Util.avoidNull(map.get("updKen")));
			// ユーザーマスタ.更新権限レベル <> 0(※ALL)の場合
			if (updKen != 0) {
				// 入力部門ボタン押下不可
				field.getButton().setEnabled(false);
				// 入力部門コードテキストボックス入力不可
				field.getField().setEditable(false);
			} else {
				// 入力部門ボタン押下可
				field.getButton().setEnabled(true);
				field.setValue("");
				// 入力部門コードテキストボックス入力可
				field.getField().setEditable(true);
				field.setNoticeValue("");

			}

		} catch (TException e) {
			errorHandler(field, e);
		}
	}

	/**
	 * 初期情報取得
	 * 
	 * @return 結果
	 * @throws TException
	 */
	protected Map searchInit() throws TException {
		try {
			// 送信するパラメータを設定
			addSendValues("FLAG", "INIT");

			if (!request(TARGET_SERVLET)) {
				// 正常に処理されませんでした
				throw new TException(getErrorMessage());
			}

			return getResult();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	// ボタン押下時の処理

	/**
	 * 部門マスタ検索処理
	 * 
	 * @return true:正常処理
	 */
	public boolean search() {
		try {
			// 部門マスタ一覧の場合
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BMN_MST);

			// 条件設定 Dialog
			setCondition(dialog, getLoginUserCompanyCode());

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				dialog.setCode(String.valueOf(field.getValue()));
				dialog.searchData(false);
			}

			// 検索ダイアログを表示
			dialog.show();

			field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			boolean isSettled = dialog.isSettle();

			// 確定の場合
			if (isSettled) {
				String[] info = dialog.getCurrencyInfo();

				// フィールドと同一コードが選ばれた場合は処理なし
				if (field.getValue().equals(info[0])) {
					return false;
				}

				// 部門コード
				field.setValue(info[0]);
				// 部門略称
				field.setNoticeValue(info[1]);

				// 部門情報設定
				setupInfo();
			}

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");

			return false;
		} finally {
			field.getField().requestFocus();

			field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	// LostFocus時の処理

	/**
	 * 部門ロストフォーカス処理<BR>
	 * 
	 * @return true:正常処理
	 */
	public boolean setupInfo() {
		try {
			// 入力部門コード
			String strBmnCode = field.getField().getText();

			if (Util.isNullOrEmpty(strBmnCode)) {

				// 入力部門コード名称ブランクでセットする。
				field.setNoticeValue("");

				return true;
			}

			// 入力部門マスタ情報取得
			Map<String, String> map = findBmnMstInfo(getLoginUserCompanyCode(), strBmnCode);

			// 入力部門コード存在の場合
			if (!"0".equals(map.get("existFlag"))) {
				// 名称をセット
				field.setNoticeValue(Util.avoidNull(map.get("DEP_NAME_S")));

				return true;
			} else {

				if (field.isChekcMode()) {
					// 存在しません。
					showMessage(field, "W00081", (Object) field.getButtonText() + codeLabel);
				}

				field.clearOldText();

				// 入力部門コード名称ブランクでセットする。
				field.setNoticeValue("");

				// ロストフォーカスを取得する。
				field.requestTextFocus();

				return !field.isChekcMode();
			}
		} catch (TException e) {
			errorHandler(this.field, e, e.getMessageID(), e.getMessageArgs());

			return false;
		} catch (Exception e) {
			errorHandler(this.field);

			return false;
		}
	}

	/**
	 * 条件設定 Dialog
	 * 
	 * @param dialog
	 * @param companyCode 会社コード
	 */
	protected void setCondition(REFDialogMasterCtrl dialog, String companyCode) {

		// 会社コード
		dialog.setKaiCode(Util.avoidNull(companyCode));
	}

	/**
	 * 部門マスタデータを検索
	 * 
	 * @param companyCode 会社コード
	 * @param strBmnCode 入力部門コード
	 * @return 入力部門マスタデータ
	 * @throws IOException
	 * @throws TException
	 */
	protected Map<String, String> findBmnMstInfo(String companyCode, String strBmnCode) throws IOException, TException {

		// 送信するパラメータを設定
		addSendValues("FLAG", "DEPARTMENT");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(companyCode));
		// 入力部門コード
		addSendValues("BMN_CODE", (Util.avoidNull(strBmnCode)));

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TException(getErrorMessage());
		}

		return getResult();
	}
}
