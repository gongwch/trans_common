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
 * 部門フィールドのコントロー
 */
public class TDepartmentFieldCtrl extends TAppletClientBase {

	/** 部門フィールド */
	protected TDepartmentField field;

	/** 処理サーブレット 部門のみ */
	protected static final String TARGET_SERVLET = "MG0060DepartmentMasterServlet";

	/** 処理サーブレット 部門と組織 */
	protected static final String TARGET_SERVLET_DPK = "MG0050DepartmentHierarchyMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField 部門フィールド
	 */
	public TDepartmentFieldCtrl(TDepartmentField itemField) {
		this.field = itemField;
	}

	/**
	 * 部門への値セット（ローストフォーカス対応）
	 * 
	 * @return boolean
	 */
	public boolean setDepartNotice() {

		try {
			String code = field.getValue();
			String dpkCode = field.getOrganization();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// 部門名称の習得
			BMN_MST bean = findData(code);

			// チェックモードオンの場合
			if (field.isChekcMode()) {

				// 結果無しの場合
				if (bean == null) {
					showMessage(field, "W00081", "C00698");
					return this.returnFalse();
				}

				// 有効期間チェック
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					if (termDate.after(bean.getEND_DATE()) || bean.getSTR_DATE().after(termDate)) {
						if (!showConfirmMessage(field, "Q00025", "C00467")) {

							return this.returnFalse();
						}
					}
				}

				if (!field.isSumDepartment() && 1 == bean.getDEP_KBN()) {
					// 集計部門は入力できません。
					String word = getWord("C00255") + getWord("C00467");
					showMessage(field, "W00280", word);

					return this.returnFalse();
				}

				if (!field.isInputDepartment() && 0 == bean.getDEP_KBN()) {
					// 入力部門は入力できません
					showMessage(field, "W00280", "C01280");

					return this.returnFalse();
				}

				// 組織コードが設定されている。
				if (!Util.isNullOrEmpty(dpkCode)) {

					// 部門コードと組織コードをあわせる。
					List listDpk = getFieldDataListDpk(code, dpkCode);

					if (listDpk.isEmpty()) {

						// 指定の部門は指定の組織に存在しません。
						showMessage(field, "W00281");
						return this.returnFalse();
					}

					// 指定の組織が存在する場合。
					if (field.getDepartmentLevel() != 0 && field.getDepartmentLevel() != TDepartmentField.NOT_SET) {

						// 上位レベルが設定されている場合。レベルを元の値に戻す（－１）
						int getLevel = (field.getDepartmentLevel()) - 1;
						int searchLevel = Integer.parseInt((String) ((List) listDpk.get(0)).get(0));

						// 設定レベルと検索レベルが違う場合エラーを出す
						if (getLevel != searchLevel) {
							// 指定の部門は部門階層レベル@DPK_LVLです
							showMessage(field, "W00282", String.valueOf(searchLevel));

							return this.returnFalse();

						} else if (getLevel == searchLevel) {

							// 設定上位部門レベルが一致し上位部門コードが設定されている場合
							if (!Util.isNullOrEmpty(field.getUpperDepartment())) {
								String upperLevelCode = (String) ((List) listDpk.get(0)).get(getLevel + 1);

								// 指定の上位部門と違う場合エラーを出す。
								if (!field.getUpperDepartment().equals(upperLevelCode)) {
									showMessage(field, "W01008", String.valueOf(searchLevel));

									return this.returnFalse();
								}
							}
						}
					}
				}

			} else {

				if (bean != null) {
					if (!field.isSumDepartment() && 1 == bean.getDEP_KBN()) {
						// 集計区分 = False かつ 集計部門コードの場合 略称を表示しない
						field.setNoticeValue("");
						return true;
					}

					if (!field.isInputDepartment() && 0 == bean.getDEP_KBN()) {
						// 入力区分 = False かつ 入力部門コードの場合 略称を表示しない
						field.setNoticeValue("");
						return true;
					}
				}
			}

			field.setNoticeValue(bean != null ? bean.getDEP_NAME_S() : "");

			return true;

		} catch (TRequestException e) {
			errorHandler(field);
			return this.returnFalse();

		} catch (Exception e) {
			errorHandler(field, e);
			return this.returnFalse();
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
	 * @param code 部門コード
	 * @return 詳細
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected BMN_MST findData(String code) throws IOException, TRequestException {

		// 送信するパラメータを設定
		addSendValues("flag", "DepartmentInfo"); // 区分flag
		if (Util.isNullOrEmpty(field.getCompanyCode())) {
			field.setCompanyCode(super.getLoginUserCompanyCode());
		}

		addSendValues("companyCode", field.getCompanyCode());
		addSendValues("deptCode", code);

		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		return (BMN_MST) getResultDto(BMN_MST.class);
	}

	/**
	 * フィールドデータリスト取得 （部門と組織で指定のレベルを習得）
	 * 
	 * @param depCode 部門コード
	 * @param dpkCode 組織コード
	 * @return 詳細リスト
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected List getFieldDataListDpk(String depCode, String dpkCode) throws IOException, TRequestException {

		// 送信するパラメータを設定
		addSendValues("flag", "refHierarchy"); // 区分flag

		if (Util.isNullOrEmpty(field.getCompanyCode())) {
			field.setCompanyCode(super.getLoginUserCompanyCode());
		}

		addSendValues("kaiCode", field.getCompanyCode());
		addSendValues("dpkDepCode", depCode);
		addSendValues("dpkSsk", dpkCode);

		if (!request(TARGET_SERVLET_DPK)) {
			throw new TRequestException();
		}

		return getResultList();
	}

	/**
	 * ダイアログ構成
	 */
	public void showSearchDialog() {

		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.DEPARTMENT_MST,
				initParam);

			searchdialog.setStartCode(field.getBeginCode()); // 開始コード
			searchdialog.setEndCode(field.getEndCode()); // 終了コード
			searchdialog.setServerName("refDepartment");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(super.getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());
			// 部門レベル指定
			if (field.getDepartmentLevel() == 0) {
				otherParam.put("level", String.valueOf(TDepartmentField.NOT_SET));
			} else {
				otherParam.put("level", String.valueOf(field.getDepartmentLevel()));
			}

			otherParam.put("sum", BooleanUtil.toString(field.isSumDepartment()));
			otherParam.put("input", BooleanUtil.toString(field.isInputDepartment()));
			otherParam.put("organization", field.getOrganization());
			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("upper", field.getUpperDepartment());
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
		param.put("top", "C01687"); // topのセット
		param.put("code", "C00698"); // コードのセット
		param.put("nameS", "C00724"); // 略称
		param.put("nameK", "C00725"); // 索引略称

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
			field.setValue(info[0]); // コードをセットした
			field.setNoticeValue(info[1]); // 名称をセットした
			field.getField().requestFocus();
			return true;
		}

		field.getField().requestFocus();

		return false;
	}

}
