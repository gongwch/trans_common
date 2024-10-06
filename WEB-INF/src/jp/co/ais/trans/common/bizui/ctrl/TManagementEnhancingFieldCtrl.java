package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 管理マスタ検索フィールド拡張コントロール
 */
public class TManagementEnhancingFieldCtrl extends TManagementFieldCtrl {

	/** 処理サーブレット */
	protected static final String CUSTOMER_TARGET_SERVLET = "MG0150CustomerMasterServlet";

	/** 処理サーブレット */
	protected static final String EMPLOYEE_SERVLET = "MG0160EmployeeMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TManagementEnhancingField panel;

	/**  */
	protected boolean sts;

	/**
	 * コンストラクタ
	 * 
	 * @param panel フィールド
	 */
	public TManagementEnhancingFieldCtrl(TManagementEnhancingField panel) {
		super(panel);
		this.panel = panel;
	}

	/**
	 * 管理マスタ検索処理
	 */
	@Override
	public void managementMouseClicked() {
		if (panel.getManagementType() == TManagementEnhancingField.TYPE_CUSTOMER) {
			customerMouseClicked();
		} else if (panel.getManagementType() == TManagementEnhancingField.TYPE_EMP) {
			empMouseClicked();
		} else {
			super.managementMouseClicked();
		}
	}

	/**
	 * ロストフォーカス処理<BR>
	 */
	@Override
	public boolean managementLostFocus() {
		if (panel.getManagementType() == TManagementEnhancingField.TYPE_CUSTOMER) {
			sts = customerLostFocus();
		} else if (panel.getManagementType() == TManagementEnhancingField.TYPE_EMP) {
			sts = employeeLostFocus();
		} else {
			sts = super.managementLostFocus();
		}
		return sts;
	}

	/**
	 * 取引先ロストフォーカス処理
	 * 
	 * @return true:正常処理
	 */
	public boolean customerLostFocus() {
		try {
			String code = panel.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				panel.setNoticeValue("");
				panel.clearOldText();
				return true;
			}

			TRI_MST triMst = getCustomerFieldDataList(code);

			// 結果無しの場合
			if (triMst == null) {
				if (panel.isCheckMode()) {
					if (panel.isLoanCustomer()) {
						showMessage(panel, "W00081", "C02689");
					} else {
						showMessage(panel, "W00081", "C00786");
					}
				}
				return returnFalse();
			}
			if (!Util.isNullOrEmpty(panel.getTermBasisDate())) {
				Date termDate = null;
				Date strDate = null;
				Date endDate = null;
				try {
					termDate = DateUtil.toYMDDate(panel.getTermBasisDate());
					strDate = DateUtil.toYMDDate(triMst.getSTR_DATE());
					endDate = DateUtil.toYMDDate(triMst.getEND_DATE());
				} catch (ParseException e) {
					// 自動生成された catch ブロック
					errorHandler(panel, e);
					return false;
				}
				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(panel, "Q00025", "C00408")) {
						return false;
					}
				}

			}
			// 名称セット
			panel.setNoticeValue(Util.avoidNull(triMst.getTRI_NAME_S()));

			return true;
		} catch (TRequestException e) {
			errorHandler(panel);
			return returnFalse();

		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
			return returnFalse();
		}
	}

	/**
	 * 略称をクリアしてfalseを返す
	 * 
	 * @return false
	 */
	protected boolean returnFalse() {
		panel.setNoticeValue("");
		panel.clearOldText();
		panel.requestTextFocus();
		return false;
	}

	/**
	 * 社員ローストフォーカス処理
	 * 
	 * @return true:正常処理
	 */
	public boolean employeeLostFocus() {
		try {
			String code = panel.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				panel.setNoticeValue("");
				panel.clearOldText();
				return true;
			}

			List list = getEmployeeFieldDataList(code);

			// 結果無しの場合
			if (list.isEmpty()) {
				if (panel.isCheckMode()) {
					showMessage(panel, "W00081", "C00697");
				}
				panel.setNoticeValue("");
				panel.clearOldText();
				panel.requestTextFocus();
				return !panel.isCheckMode();
			}

			// 有効期間のフラグ
			if (!Util.isNullOrEmpty(panel.getTermBasisDate())) {
				Date termDate = null;
				Date strDate = null;
				Date endDate = null;

				termDate = DateUtil.toYMDDate(panel.getTermBasisDate());
				strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(2));
				endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));

				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(panel, "Q00025", "C00246")) {
						return false;
					}
				}

			}

			StringBuilder bfieldName = new StringBuilder();
			bfieldName.append((String) ((List) list.get(0)).get(1));

			panel.setNoticeValue(bfieldName.toString());

			return true;
		} catch (Exception e) {
			errorHandler(panel, e);
			return false;
		}

	}

	/**
	 * 取引先ダイアログ表示
	 */
	public void customerMouseClicked() {
		try {
			Map<String, String> initParam = new HashMap<String, String>();

			if (panel.isLoanCustomer()) {
				initParam = getLoanDialogParam();
			} else {
				initParam = getCustomerSearchDialogParam();
			}
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.CUSTOMER_MST,
				initParam);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refCustomer");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
				otherParam.put("companyCode", panel.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}
			otherParam.put("termBasisDate", panel.getTermBasisDate());
			otherParam.put("beginCode", panel.getBeginCode());
			otherParam.put("endCode", panel.getEndCode());
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
				searchdialog.setCode(String.valueOf(panel.getField().getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// 検索ダイアログを表示

			showDialog(searchdialog);

		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 社員ダイアログ構成
	 */
	public void empMouseClicked() {
		if (panel.isEnabled()) {
			try {

				Map<String, String> initParam = getEmployeeSearchDialogParam();
				REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.EMP_SEARCH_MST,
					initParam);

				searchdialog.setStartCode(""); // 開始コード
				searchdialog.setEndCode(""); // 終了コード
				searchdialog.setServerName("refEmployee");
				Map<String, String> otherParam = new HashMap<String, String>();

				if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
					otherParam.put("companyCode", panel.getCompanyCode());
				} else {
					otherParam.put("companyCode", getLoginUserCompanyCode());
				}
				otherParam.put("termBasisDate", panel.getTermBasisDate());
				otherParam.put("beginCode", panel.getBeginCode());
				otherParam.put("endCode", panel.getEndCode());
				searchdialog.setParamMap(otherParam);

				// ｺｰﾄﾞ設定、自動検索
				if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
					searchdialog.setCode(String.valueOf(panel.getField().getValue()));
					searchdialog.searchData(false);
				}

				searchdialog.show();

				showDialog(searchdialog);

			} catch (Exception e) {
				errorHandler(panel, e, "E00009");
			}

		}

	}

	/**
	 * 取引先フィールドデータリスト取得
	 * 
	 * @param code コード
	 * @return 詳細リスト
	 * @throws TException
	 */
	public TRI_MST getCustomerFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "CustomerInfo"); // 区分flag
			if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
				addSendValues("companyCode", panel.getCompanyCode());
			} else {
				addSendValues("companyCode", getLoginUserCompanyCode());
			}
			addSendValues("customerCode", code);
			if (!request(CUSTOMER_TARGET_SERVLET)) {
				throw new TRequestException();
			}

			return (TRI_MST) getResultObject();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * 社員フィールドデータリスト取得
	 * 
	 * @param code コード
	 * @return 詳細リスト
	 * @throws TException
	 */
	protected List getEmployeeFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "refEmployee"); // 区分flag
			if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
				addSendValues("kaiCode", panel.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			addSendValues("empCode", code);
			if (!request(EMPLOYEE_SERVLET)) {
				// 正常に処理されませんでした
				throw new TRequestException();
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * 取引先検索dialog初期値のセット
	 * 
	 * @return param
	 */
	protected Map<String, String> getCustomerSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C01676"); // topのセット
		param.put("code", "C00786"); // コードのセット
		param.put("nameS", "C00787"); // 略称
		param.put("nameK", "C00836"); // 索引略称

		return param;
	}

	/**
	 * 社員検索dialog初期値のセット
	 * 
	 * @return param
	 */
	protected Map<String, String> getEmployeeSearchDialogParam() {
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
	 * @return true 確定の場合 false 取消の場合
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// 確定の場合
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			panel.setValue(info[0]); // コードをセットした
			panel.setNoticeValue(info[1]); // 名称をセットした
			panel.getField().requestFocus();
			return true;
		}
		panel.getField().requestFocus();
		return false;
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
		param.put("nameS", getWord("C02690") + getWord("C00518")); // 略称
		param.put("nameK", getWord("C02690") + getWord("C00160")); // 索引略称

		return param;
	}
}
