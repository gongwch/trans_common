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
 * 補助科目フィールドコントロール
 */
public class TSubItemFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "TAccountItemUnitServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TSubItemField field;

	/** コード(文字) */
	protected String codeLabel = getWord("C00174");

	/**
	 * コンストラクタ
	 * 
	 * @param subItemField
	 */
	public TSubItemFieldCtrl(TSubItemField subItemField) {

		this.field = subItemField;
	}

	/**
	 * 補助科目マスタ検索処理
	 * 
	 * @return boolean
	 */
	public boolean search() {

		try {
			// 補助科目マスタ一覧の場合
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.HKM_MST);

			// 会社コード
			String companyCode;
			if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
				companyCode = super.getLoginUserCompanyCode();
			} else {
				companyCode = field.getInputParameter().getCompanyCode();
			}

			// 条件設定 Dialog
			setCondition(dialog, companyCode);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				dialog.setCode(String.valueOf(field.getValue()));
				dialog.searchData(false);
			}

			// 検索ダイアログを表示
			dialog.show();

			boolean isSettled = dialog.isSettle();

			// 確定の場合
			if (isSettled) {
				String[] info = dialog.getCurrencyInfo();

				// フィールドと同一コードが選ばれた場合は処理なし
				if (field.getValue().equals(info[0]) && !field.getNoticeValue().isEmpty()) {
					return false;
				}

				// 預金科目コード
				field.setValue(info[0]);

				// 預金科目略称
				field.setNoticeValue(info[1]);

				field.getField().pushOldText();

				// ロストフォーカス処理
				setupInfo();
			}

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");

			return false;
		} finally {
			field.getField().requestFocus();
		}
	}

	/**
	 * 補助科目ロストフォーカス処理<BR>
	 * 
	 * @return boolean
	 */
	public boolean setupInfo() {

		try {
			// OutputParameterの値をクリアする
			field.getOutputParameter().clear();

			// 会社コード
			String companyCode;
			if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
				companyCode = super.getLoginUserCompanyCode();
			} else {
				companyCode = field.getInputParameter().getCompanyCode();
			}

			// 補助科目ロコード
			String subItemCode = field.getValue();

			if (Util.isNullOrEmpty(subItemCode)) {
				// 科目コード名称ブランクでセットする。
				field.setNoticeValue("");

				return true;
			}

			// 補助科目マスタデータを検索
			Map<String, String> map = findInfo(companyCode, subItemCode);

			// 指定コードの存在チェック
			field.getOutputParameter().setExist(!"0".equals(map.get("existFlag")));

			// 補助科目コード存在の場合
			if (field.getOutputParameter().isExist()) {

				// 補助科目名称をセット
				field.setNoticeValue(Util.avoidNull(map.get("HKM_NAME_S")));

				// 取得した値をOutputParameterにセット
				setResultData(map);

				return true;

			} else {
				if (field.isCheckMode()) {
					// 存在しません。
					showMessage(field, "W00081", (Object) field.getButtonText() + codeLabel);
				}

				field.clearOldText();

				// 補助科目コード名称ブランクでセットする。
				field.setNoticeValue("");

				// 補助科目コードロストフォーカスを取得する。
				field.requestTextFocus();

				return !field.isCheckMode();
			}

		} catch (Exception e) {
			errorHandler(this.field);

			return false;
		}
	}

	/**
	 * 取得した値をOutputParameterにセット
	 * 
	 * @param resultMap 取得したデータ
	 */
	protected void setResultData(Map resultMap) {

		AccountItemOutputParameter param = field.getOutputParameter();

		// 科目略称
		param.setSubItemAbbrName(Util.avoidNull(resultMap.get("HKM_NAME_S")));
		// 内訳区分
		param.setBreakDownItemFlag(Util.avoidNull(resultMap.get("UKM_KBN")));
		// 売上課税入力フラグ
		param.setSalesTaxInputFlag(Util.avoidNull(resultMap.get("URI_ZEI_FLG")));
		// 仕入課税入力フラグ
		param.setPurchaseTaxationInputFlag(Util.avoidNull(resultMap.get("SIR_ZEI_FLG")));
		// 消費税区分
		param.setConsumptionTaxDivision(Util.avoidNull(resultMap.get("SZEI_KEI_KBN")));
		// 消費税コード
		param.setConsumptionTaxCode(Util.avoidNull(resultMap.get("ZEI_CODE")));
		// 消費税略称
		param.setConsumptionTaxAbbrName(Util.avoidNull(resultMap.get("ZEI_NAME_S")));
		// 取引先入力フラグ
		param.setCustomerInputFlag(Util.avoidNull(resultMap.get("TRI_CODE_FLG")));
		// 社員コード
		param.setEmployeeCode(Util.avoidNull(resultMap.get("EMP_CODE")));
		// 社員略称
		param.setEmployeeAbbrName(Util.avoidNull(resultMap.get("EMP_NAME_S")));
		// 社員入力フラグ
		param.setEmployeeInputFlag(Util.avoidNull(resultMap.get("EMP_CODE_FLG")));
		// 管理1入力フラグ
		param.setManagement1InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_1")));
		// 管理2入力フラグ
		param.setManagement2InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_2")));
		// 管理3入力フラグ
		param.setManagement3InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_3")));
		// 管理4入力フラグ
		param.setManagement4InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_4")));
		// 管理5入力フラグ
		param.setManagement5InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_5")));
		// 管理6入力フラグ
		param.setManagement6InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_6")));
		// 非会計明細1入力フラグ
		param.setNonAccountingDetail1Flag(Util.avoidNull(resultMap.get("HM_FLG_1")));
		// 非会計明細2入力フラグ
		param.setNonAccountingDetail2Flag(Util.avoidNull(resultMap.get("HM_FLG_2")));
		// 非会計明細3入力フラグ
		param.setNonAccountingDetail3Flag(Util.avoidNull(resultMap.get("HM_FLG_3")));
		// 発生日入力フラグ
		param.setAccrualDateInputFlag(Util.avoidNull(resultMap.get("HAS_FLG")));
		// 多通貨入力フラグ
		param.setMultipleCurrencyInputFlag(Util.avoidNull(resultMap.get("MCR_FLG")));
	}

	/**
	 * 条件設定 Dialog
	 * 
	 * @param dialog 検索ダイアログ
	 * @param companyCode 会社コード
	 */
	protected void setCondition(REFDialogMasterCtrl dialog, String companyCode) {
		// 会社コード
		dialog.setKaiCode(Util.avoidNull(companyCode));
		// 科目コード
		dialog.setKmkCode(Util.avoidNull(field.getInputParameter().getItemCode()));
		// 伝票日付
		dialog.setSlipDate(Util.avoidNull(field.getInputParameter().getSlipDate()));
		// 振替伝票入力フラグ
		dialog.setFurikaeFlg(Util.avoidNull(field.getInputParameter().getTransferSlipInputFlag()));
		// 入金伝票入力フラグ
		dialog.setNyuKin(Util.avoidNull(field.getInputParameter().getRecivingSlipInputFlag()));
		// 出金伝票入力フラグ
		dialog.setShutsuKin(Util.avoidNull(field.getInputParameter().getDrawingSlipInputFlag()));
		// 経費精算伝票入力フラグ
		dialog.setKeihiFlg(Util.avoidNull(field.getInputParameter().getExpenseInputFlag()));
		// 債務計上入力フラグ
		dialog.setSaimuFlg(Util.avoidNull(field.getInputParameter().getSaimuFlg()));
		// 債権計上入力フラグ
		dialog.setSaikenFlg(Util.avoidNull(field.getInputParameter().getSaikenFlg()));
		// 債権消込伝票入力フラグ
		dialog.setSaikesiFlg(Util.avoidNull(field.getInputParameter().getAccountsRecivableErasingSlipInputFlag()));
		// 資産計上伝票入力フラグ
		dialog.setSisanFlg(Util.avoidNull(field.getInputParameter().getAssetsAppropriatingSlipInputFlag()));
		// 支払依頼伝票入力フラグ
		dialog.setSiharaiFlg(Util.avoidNull(field.getInputParameter().getPaymentRequestSlipInputFlag()));

		// 開始コード
		dialog.setBeginCode(Util.avoidNull(field.getInputParameter().getBeginCode()));
		// 終了コード
		dialog.setEndCode(Util.avoidNull(field.getInputParameter().getEndCode()));

		// 評価替対象フラグ
		dialog.setExcFlg(Util.avoidNull(field.getInputParameter().getRevaluationObjectFlag()));
		// 内訳区分
		dialog.setUkmKbn(Util.avoidNull(this.field.getInputParameter().getBreakDownItemDivision()));
		// 消費税ｺｰﾄﾞ
		dialog.setZeiCode(Util.avoidNull(this.field.getInputParameter().getConsumptionTaxCode()));
		// 取引先入力ﾌﾗｸﾞ
		dialog.setTriCodeFlag(Util.avoidNull(this.field.getInputParameter().getCustomerInputFlag()));
		// 発生日入力ﾌﾗｸﾞ
		dialog.setHasFlg(Util.avoidNull(this.field.getInputParameter().getAccrualDateInputFlag()));
		// 社員入力ﾌﾗｸﾞ
		dialog.setEmpCodeFlg(Util.avoidNull(this.field.getInputParameter().getEmployeeInputFlag()));
		// 管理1
		dialog.setKnrFlg1(Util.avoidNull(this.field.getInputParameter().getManagement1InputFlag()));
		// 管理2
		dialog.setKnrFlg2(Util.avoidNull(this.field.getInputParameter().getManagement2InputFlag()));
		// 管理3
		dialog.setKnrFlg3(Util.avoidNull(this.field.getInputParameter().getManagement3InputFlag()));
		// 管理4
		dialog.setKnrFlg4(Util.avoidNull(this.field.getInputParameter().getManagement4InputFlag()));
		// 管理5
		dialog.setKnrFlg5(Util.avoidNull(this.field.getInputParameter().getManagement5InputFlag()));
		// 管理6
		dialog.setKnrFlg6(Util.avoidNull(this.field.getInputParameter().getManagement6InputFlag()));
		// 非会計1
		dialog.setHmFlg1(Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail1Flag()));
		// 非会計2
		dialog.setHmFlg2(Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail2Flag()));
		// 非会計3
		dialog.setHmFlg3(Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail3Flag()));
		// 売上課税入力ﾌﾗｸﾞ
		dialog.setUriZeiFlg(Util.avoidNull(this.field.getInputParameter().getSalesTaxInputFlag()));
		// 仕入課税入力ﾌﾗｸﾞ
		dialog.setSirZeiFlg(Util.avoidNull(this.field.getInputParameter().getPurchaseTaxationInputFlag()));
		// 多通貨入力ﾌﾗｸﾞ
		dialog.setMcrFlg(Util.avoidNull(this.field.getInputParameter().getMultipleCurrencyInputFlag()));
	}

	/**
	 * 補助科目マスタデータを検索
	 * 
	 * @param companyCode 会社コード
	 * @param subItemCode 補助科目コード
	 * @return 補助科目マスタデータ
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected Map<String, String> findInfo(String companyCode, String subItemCode) throws TRequestException,
		IOException {

		boolean isWaitCursor = super.isWaitCursorNow(field);

		if (!isWaitCursor) {
			setWaitCursor(field);
		}

		try {
			// 補助科目マスタ検索フラグ
			addSendValues("FLAG", "HKM_MST");
			// 会社コード
			addSendValues("KAI_CODE", companyCode);
			// 科目コード
			addSendValues("KMK_CODE", Util.avoidNull(field.getInputParameter().getItemCode()));
			// 補助科目コード
			addSendValues("HKM_CODE", subItemCode);
			// 伝票日付
			addSendValues("SLIP_DATE", Util.avoidNull(field.getInputParameter().getSlipDate()));
			// 入金伝票入力フラグ
			addSendValues("NYU_KIN", Util.avoidNull(field.getInputParameter().getRecivingSlipInputFlag()));
			// 出金伝票入力フラグ
			addSendValues("SHUTSU_KIN", Util.avoidNull(field.getInputParameter().getDrawingSlipInputFlag()));
			// 振替伝票入力フラグ
			addSendValues("FURIKAE_FLG", Util.avoidNull(field.getInputParameter().getTransferSlipInputFlag()));
			// 経費精算伝票入力フラグ
			addSendValues("KEIHI_FLG", Util.avoidNull(field.getInputParameter().getExpenseInputFlag()));
			// 債務計上入力フラグ
			addSendValues("SAIMU_FLG", Util.avoidNull(field.getInputParameter().getSaimuFlg()));
			// 債権計上入力フラグ
			addSendValues("SAIKEN_FLG", Util.avoidNull(field.getInputParameter().getSaikenFlg()));
			// 債権消込伝票入力フラグ
			addSendValues("SAIKESI_FLG",
				Util.avoidNull(field.getInputParameter().getAccountsRecivableErasingSlipInputFlag()));
			// 資産計上伝票入力フラグ
			addSendValues("SISAN_FLG", Util.avoidNull(field.getInputParameter().getAssetsAppropriatingSlipInputFlag()));
			// 支払依頼伝票入力フラグ
			addSendValues("SIHARAI_FLG", Util.avoidNull(field.getInputParameter().getPaymentRequestSlipInputFlag()));

			// 評価替対象フラグ
			addSendValues("EXC_FLG", Util.avoidNull(field.getInputParameter().getRevaluationObjectFlag()));
			// 内訳区分
			addSendValues("UKM_KBN", Util.avoidNull(this.field.getInputParameter().getBreakDownItemDivision()));
			// 消費税ｺｰﾄﾞ
			addSendValues("ZEI_CODE", Util.avoidNull(this.field.getInputParameter().getConsumptionTaxCode()));
			// 取引先入力ﾌﾗｸﾞ
			addSendValues("TRI_CODE_FLG", Util.avoidNull(this.field.getInputParameter().getCustomerInputFlag()));
			// 発生日入力ﾌﾗｸﾞ
			addSendValues("HAS_FLG", Util.avoidNull(this.field.getInputParameter().getAccrualDateInputFlag()));
			// 社員入力ﾌﾗｸﾞ
			addSendValues("EMP_CODE_FLG", Util.avoidNull(this.field.getInputParameter().getEmployeeInputFlag()));
			// 管理1
			addSendValues("KNR_FLG1", Util.avoidNull(this.field.getInputParameter().getManagement1InputFlag()));
			// 管理2
			addSendValues("KNR_FLG2", Util.avoidNull(this.field.getInputParameter().getManagement2InputFlag()));
			// 管理3
			addSendValues("KNR_FLG3", Util.avoidNull(this.field.getInputParameter().getManagement3InputFlag()));
			// 管理4
			addSendValues("KNR_FLG4", Util.avoidNull(this.field.getInputParameter().getManagement4InputFlag()));
			// 管理5
			addSendValues("KNR_FLG5", Util.avoidNull(this.field.getInputParameter().getManagement5InputFlag()));
			// 管理6
			addSendValues("KNR_FLG6", Util.avoidNull(this.field.getInputParameter().getManagement6InputFlag()));
			// 非会計1
			addSendValues("HM_FLG1", Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail1Flag()));
			// 非会計2
			addSendValues("HM_FLG2", Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail2Flag()));
			// 非会計3
			addSendValues("HM_FLG3", Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail3Flag()));
			// 売上課税入力ﾌﾗｸﾞ
			addSendValues("URI_ZEI_FLG", Util.avoidNull(this.field.getInputParameter().getSalesTaxInputFlag()));
			// 仕入課税入力ﾌﾗｸﾞ
			addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.field.getInputParameter().getPurchaseTaxationInputFlag()));
			// 多通貨入力ﾌﾗｸﾞ
			addSendValues("MCR_FLG", Util.avoidNull(this.field.getInputParameter().getMultipleCurrencyInputFlag()));

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				throw new TRequestException();
			}

			// データ取得
			return getResult();

		} finally {
			if (!isWaitCursor) {
				setDefaultCursor(field);
			}
		}
	}
}
