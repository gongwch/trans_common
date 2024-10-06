package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 内訳科目フィールドコントロール
 */
public class TBreakDownItemFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "TAccountItemUnitServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TBreakDownItemField field;

	/** コード(文字) */
	protected String codeLabel = getWord("C00174");

	/** 会社コード */
	protected String companyCode = "";

	/**
	 * コンストラクタ
	 * 
	 * @param breakDownItemField
	 */
	public TBreakDownItemFieldCtrl(TBreakDownItemField breakDownItemField) {
		try {

			this.field = breakDownItemField;
		} catch (Exception e) {
			errorHandler(breakDownItemField, e, "E00010");
		}
	}

	// *************************************** ボタン押下時の処理

	/**
	 * 内訳科目マスタ検索処理
	 * 
	 * @return boolean
	 */
	public boolean search() {
		try {
			// 内訳科目マスタ一覧の場合
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.UKM_MST);

			// 会社コード
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

			field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			boolean isSettled = dialog.isSettle();

			// 確定の場合
			if (isSettled) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				String[] info = dialog.getCurrencyInfo();

				// フィールドと同一コードが選ばれた場合は処理なし
				if (field.getValue().equals(info[0]) && !field.getNoticeValue().isEmpty()) {
					return false;
				}

				// 内訳科目コード
				field.setValue(info[0]);
				// 内訳科目略称
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

			field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	// *************************************** LostFocus時の処理

	/**
	 * 内訳科目ロストフォーカス処理
	 * 
	 * @return boolean
	 */
	public boolean setupInfo() {
		try {
			// OutputParameterの値をクリアする
			field.getOutputParameter().clear();

			// 会社コード
			if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
				companyCode = super.getLoginUserCompanyCode();
			} else {
				companyCode = field.getInputParameter().getCompanyCode();
			}

			// 科目コード
			String breakDownItemCode = field.getValue();

			if (Util.isNullOrEmpty(breakDownItemCode)) {

				// 内訳科目コード名称ブランクでセットする。
				field.setNoticeValue("");

				return true;
			}

			// 内訳科目マスタデータを検索
			Map<String, String> map = findInfo(breakDownItemCode);

			// 指定コードの存在チェック
			field.getOutputParameter().setExist(!"0".equals(map.get("existFlag")));

			// 内訳科目コード存在の場合
			if (field.getOutputParameter().isExist()) {

				// 補助科目名称をセット
				field.setNoticeValue(Util.avoidNull(map.get("UKM_NAME_S")));

				// 取得した値をOutputParameterにセット
				setResultData(map);

				return true;

			} else {
				if (field.isChekcMode()) {
					// 存在しません。
					showMessage(field, "W00081", field.getButtonText() + codeLabel);
				}

				field.clearOldText();

				// 内訳科目コード名称ブランクでセットする。
				field.setNoticeValue("");

				// 内訳科目コードロストフォーカスを取得する。
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
	 * 取得した値をOutputParameterにセット
	 * 
	 * @param resultMap 取得したデータ
	 */
	protected void setResultData(Map resultMap) {
		AccountItemOutputParameter param = field.getOutputParameter();

		// 内訳科目名称
		param.setBreakDownItemAbbrName(Util.avoidNull(resultMap.get("UKM_NAME_S")));
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
	 * @param dialog
	 * @param companyCode
	 */
	protected void setCondition(REFDialogMasterCtrl dialog, String companyCode) {
		// 会社コード
		dialog.setKaiCode(Util.avoidNull(companyCode));
		// 科目コード
		dialog.setKmkCode(Util.avoidNull(field.getInputParameter().getItemCode()));
		// 補助科目コード
		dialog.setHkmCode(Util.avoidNull(field.getInputParameter().getSubItemCode()));
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
	 * 内訳科目マスタデータを検索
	 * 
	 * @param breakDownItemCode 内訳科目コード
	 * @return 内訳科目マスタデータ
	 * @throws TException
	 * @throws IOException
	 */
	protected Map<String, String> findInfo(String breakDownItemCode) throws TException, IOException {
		// 内訳科目マスタ検索フラグ
		addSendValues("FLAG", "UKM_MST");
		// 会社コード
		addSendValues("KAI_CODE", companyCode);
		// 科目コード
		addSendValues("KMK_CODE", Util.avoidNull(field.getInputParameter().getItemCode()));
		// 補助科目コード
		addSendValues("HKM_CODE", Util.avoidNull(field.getInputParameter().getSubItemCode()));
		// 内訳科目コード
		addSendValues("UKM_CODE", Util.avoidNull(breakDownItemCode));
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
			throw new TException(getErrorMessage());
		}

		// データ取得
		return getResult();
	}
}
