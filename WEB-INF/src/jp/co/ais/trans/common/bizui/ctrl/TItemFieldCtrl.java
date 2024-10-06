package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 科目フィールドコントロール
 */
public class TItemFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "TAccountItemUnitServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TItemField field;

	/** コード(文字) */
	protected String codeLabel = getWord("C00174");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField フィールド
	 */
	public TItemFieldCtrl(TItemField itemField) {
		try {

			this.field = itemField;

		} catch (Exception e) {
			errorHandler(itemField, e, "E00010");
		}
	}

	// *************************************** ボタン押下時の処理

	/**
	 * 科目マスタ検索処理
	 * 
	 * @return true:正常処理
	 */
	public boolean search() {
		try {
			// 科目マスタ一覧の場合
			Container parent = TGuiUtil.getParentFrameOrDialog(field);

			REFDialogMasterCtrl dialog = (parent instanceof Dialog) ? new REFDialogMasterCtrl((Dialog) parent,
				REFDialogMasterCtrl.KMK_MST) : new REFDialogMasterCtrl((Frame) parent, REFDialogMasterCtrl.KMK_MST);

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

			field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			boolean isSettled = dialog.isSettle();

			// 確定の場合
			if (isSettled) {
				String[] info = dialog.getCurrencyInfo();

				// フィールドと同一コードが選ばれた場合は処理なし
				if (field.getValue().equals(info[0]) && !field.getNoticeValue().isEmpty()) {
					return false;
				}

				// 科目コード
				field.setValue(info[0]);
				// 科目略称
				field.setNoticeValue(info[1]);

				field.getField().pushOldText();

				// 科目情報設定
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
	 * 科目情報設定
	 * 
	 * @return true:正常処理
	 */
	public boolean setupInfo() {
		try {

			// Outputパラメータの値をクリアする
			field.getOutputParameter().clear();

			// 科目コード
			String strKmkCode = field.getField().getText();

			if (Util.isNullOrEmpty(strKmkCode)) {
				// 科目コード名称ブランクでセットする。
				field.setNoticeValue("");

				return true;
			}

			// 科目マスタ情報取得
			buildOutput(strKmkCode);

			AccountItemOutputParameter outparam = field.getOutputParameter();

			// 名称をセット
			field.setNoticeValue(outparam.getItemAbbrName());

			// 計上部門コード存在の場合
			if (!outparam.isExist()) {

				if (field.isCheckMode()) {
					// 存在しません。
					showMessage(field, "W00081", (Object) field.getButtonText() + codeLabel);
					field.requestTextFocus();
					field.clearOldText();
				}

				return !field.isCheckMode();
			}

			return true;

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

		AccountItemInputParameter inparam = field.getInputParameter();

		// 会社コード
		dialog.setKaiCode(Util.avoidNull(companyCode));
		// 伝票日付
		dialog.setSlipDate(Util.avoidNull(inparam.getSlipDate()));
		// BS勘定消込区分
		dialog.setKesiKbn(Util.avoidNull(inparam.getBsAccountErasingDivision()));
		// 集計区分
		dialog.setSumKbn(Util.avoidNull(inparam.getSummaryDivision()));
		// ＧＬ科目制御区分
		dialog.setKmkCntGl(Util.avoidNull(inparam.getGlItemCtrlDivision()));
		// AR制御区分
		dialog.setkmkCntAr(Util.avoidNull(inparam.getArItemCtrlDivision()));
		// AR制御区分(消込用)
		dialog.setkmkCntUnAr(Util.avoidNull(inparam.getUnArItemCtrlDivision()));
		// AP制御区分
		dialog.setkmkCntAp(Util.avoidNull(inparam.getApItemCtrlDivision()));
		// 部門コード
		dialog.setBmnCode(Util.avoidNull(inparam.getDepartmentCode()));
		// 評価替対象フラグ
		dialog.setExcFlg(Util.avoidNull(inparam.getRevaluationObjectFlag()));
		// 入金伝票入力フラグ
		dialog.setNyuKin(Util.avoidNull(inparam.getRecivingSlipInputFlag()));
		// 出金伝票入力フラグ
		dialog.setShutsuKin(Util.avoidNull(inparam.getDrawingSlipInputFlag()));
		// 振替伝票入力フラグ
		dialog.setFurikaeFlg(Util.avoidNull(inparam.getTransferSlipInputFlag()));
		// 経費精算伝票入力フラグ
		dialog.setKeihiFlg(Util.avoidNull(inparam.getExpenseInputFlag()));
		// 債務計上入力フラグ
		dialog.setSaimuFlg(Util.avoidNull(inparam.getSaimuFlg()));
		// 債権計上入力フラグ
		dialog.setSaikenFlg(Util.avoidNull(inparam.getSaikenFlg()));
		// 債権消込伝票入力フラグ
		dialog.setSaikesiFlg(Util.avoidNull(inparam.getAccountsRecivableErasingSlipInputFlag()));
		// 資産計上伝票入力フラグ
		dialog.setSisanFlg(Util.avoidNull(inparam.getAssetsAppropriatingSlipInputFlag()));
		// 支払依頼伝票入力フラグ
		dialog.setSiharaiFlg(Util.avoidNull(inparam.getPaymentRequestSlipInputFlag()));

		// 科目種別
		dialog.setKmkShu(Util.avoidNull(inparam.getItemType()));
		// 貸借区分
		dialog.setDcKbn(Util.avoidNull(inparam.getDebitAndCreditDivision()));
		// 補助区分
		dialog.setHkmKbn(Util.avoidNull(inparam.getSubItemDivision()));
		// 固定部門ｺｰﾄﾞ
		dialog.setKoteiDepCode(Util.avoidNull(inparam.getFixedDepartment()));
		// 消費税ｺｰﾄﾞ
		dialog.setZeiCode(Util.avoidNull(inparam.getConsumptionTaxCode()));
		// 取引先入力ﾌﾗｸﾞ
		dialog.setTriCodeFlag(Util.avoidNull(inparam.getCustomerInputFlag()));
		// 発生日入力ﾌﾗｸﾞ
		dialog.setHasFlg(Util.avoidNull(inparam.getAccrualDateInputFlag()));
		// 社員入力ﾌﾗｸﾞ
		dialog.setEmpCodeFlg(Util.avoidNull(inparam.getEmployeeInputFlag()));
		// 管理1
		dialog.setKnrFlg1(Util.avoidNull(inparam.getManagement1InputFlag()));
		// 管理2
		dialog.setKnrFlg2(Util.avoidNull(inparam.getManagement2InputFlag()));
		// 管理3
		dialog.setKnrFlg3(Util.avoidNull(inparam.getManagement3InputFlag()));
		// 管理4
		dialog.setKnrFlg4(Util.avoidNull(inparam.getManagement4InputFlag()));
		// 管理5
		dialog.setKnrFlg5(Util.avoidNull(inparam.getManagement5InputFlag()));
		// 管理6
		dialog.setKnrFlg6(Util.avoidNull(inparam.getManagement6InputFlag()));
		// 非会計1
		dialog.setHmFlg1(Util.avoidNull(inparam.getNonAccountingDetail1Flag()));
		// 非会計2
		dialog.setHmFlg2(Util.avoidNull(inparam.getNonAccountingDetail2Flag()));
		// 非会計3
		dialog.setHmFlg3(Util.avoidNull(inparam.getNonAccountingDetail3Flag()));
		// 売上課税入力ﾌﾗｸﾞ
		dialog.setUriZeiFlg(Util.avoidNull(inparam.getSalesTaxInputFlag()));
		// 仕入課税入力ﾌﾗｸﾞ
		dialog.setSirZeiFlg(Util.avoidNull(inparam.getPurchaseTaxationInputFlag()));
		// 多通貨入力ﾌﾗｸﾞ
		dialog.setMcrFlg(Util.avoidNull(inparam.getMultipleCurrencyInputFlag()));
		// BG科目制御区分
		dialog.setKmkCntBg(Util.avoidNull(inparam.getBgItemCtrlDivision()));
		// 相殺科目制御区分
		dialog.setKmkCntSousai(Util.avoidNull(inparam.getCounterbalanceAdjustmentCtrlDivision()));

		// 開始コード
		dialog.setBeginCode(Util.avoidNull(inparam.getBeginCode()));
		// 終了コード
		dialog.setEndCode(Util.avoidNull(inparam.getEndCode()));
		// 科目体系コード
		dialog.setKmtCode(Util.avoidNull(inparam.getItemSystemCode()));
		// 科目体系フラグ
		dialog.setKmtFlg(Util.avoidNull(inparam.getItemSystemFlg()));

	}

	/**
	 * 科目マスタデータを検索し、情報構築
	 * 
	 * @param strKmkCode 科目コード
	 * @throws IOException
	 * @throws TException
	 */
	protected void buildOutput(String strKmkCode) throws IOException, TException {

		// 会社コード
		String companyCode;
		if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
			companyCode = super.getLoginUserCompanyCode();
		} else {
			companyCode = field.getInputParameter().getCompanyCode();
		}

		AccountItemInputParameter inparam = field.getInputParameter();

		// 送信するパラメータを設定
		addSendValues("FLAG", "KMK_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(companyCode));
		// 科目コード
		addSendValues("KMK_CODE", (Util.avoidNull(strKmkCode)));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(inparam.getSlipDate()));
		// BS勘定消込区分
		addSendValues("KESI_KBN", Util.avoidNull(inparam.getBsAccountErasingDivision()));
		// 集計区分
		addSendValues("SUM_KBN", Util.avoidNull(inparam.getSummaryDivision()));
		// ＧＬ科目制御区分
		addSendValues("KMK_CNT_GL", Util.avoidNull(inparam.getGlItemCtrlDivision()));
		// AR制御区分
		addSendValues("KMK_CNT_AR", Util.avoidNull(inparam.getArItemCtrlDivision()));
		// AR制御区分(消込用)
		addSendValues("KMK_CNT_UN_AR", Util.avoidNull(inparam.getUnArItemCtrlDivision()));
		// AP制御区分
		addSendValues("KMK_CNT_AP", Util.avoidNull(inparam.getApItemCtrlDivision()));
		// 部門コード
		addSendValues("BMN_CODE", Util.avoidNull(inparam.getDepartmentCode()));
		// 評価替対象フラグ
		addSendValues("EXC_FLG", Util.avoidNull(inparam.getRevaluationObjectFlag()));
		// 入金伝票入力フラグ
		addSendValues("NYU_KIN", Util.avoidNull(inparam.getRecivingSlipInputFlag()));
		// 出金伝票入力フラグ
		addSendValues("SHUTSU_KIN", Util.avoidNull(inparam.getDrawingSlipInputFlag()));
		// 振替伝票入力フラグ
		addSendValues("FURIKAE_FLG", Util.avoidNull(inparam.getTransferSlipInputFlag()));
		// 経費精算伝票入力フラグ
		addSendValues("KEIHI_FLG", Util.avoidNull(inparam.getExpenseInputFlag()));
		// 債務計上入力フラグ
		addSendValues("SAIMU_FLG", Util.avoidNull(inparam.getSaimuFlg()));
		// 債権計上入力フラグ
		addSendValues("SAIKEN_FLG", Util.avoidNull(inparam.getSaikenFlg()));
		// 債権消込伝票入力フラグ
		addSendValues("SAIKESI_FLG", Util.avoidNull(inparam.getAccountsRecivableErasingSlipInputFlag()));
		// 資産計上伝票入力フラグ
		addSendValues("SISAN_FLG", Util.avoidNull(inparam.getAssetsAppropriatingSlipInputFlag()));
		// 支払依頼伝票入力フラグ
		addSendValues("SIHARAI_FLG", Util.avoidNull(inparam.getPaymentRequestSlipInputFlag()));

		// 科目種別
		addSendValues("KMK_SHU", Util.avoidNull(inparam.getItemType()));
		// 貸借区分
		addSendValues("DC_KBN", Util.avoidNull(inparam.getDebitAndCreditDivision()));
		// 補助区分
		addSendValues("HKM_KBN", Util.avoidNull(inparam.getSubItemDivision()));
		// 固定部門ｺｰﾄﾞ
		addSendValues("KOTEI_DEP_CODE", Util.avoidNull(inparam.getFixedDepartment()));
		// 消費税ｺｰﾄﾞ
		addSendValues("ZEI_CODE", Util.avoidNull(inparam.getConsumptionTaxCode()));
		// 取引先入力ﾌﾗｸﾞ
		addSendValues("TRI_CODE_FLG", Util.avoidNull(inparam.getCustomerInputFlag()));
		// 発生日入力ﾌﾗｸﾞ
		addSendValues("HAS_FLG", Util.avoidNull(inparam.getAccrualDateInputFlag()));
		// 社員入力ﾌﾗｸﾞ
		addSendValues("EMP_CODE_FLG", Util.avoidNull(inparam.getEmployeeInputFlag()));
		// 管理1
		addSendValues("KNR_FLG1", Util.avoidNull(inparam.getManagement1InputFlag()));
		// 管理2
		addSendValues("KNR_FLG2", Util.avoidNull(inparam.getManagement2InputFlag()));
		// 管理3
		addSendValues("KNR_FLG3", Util.avoidNull(inparam.getManagement3InputFlag()));
		// 管理4
		addSendValues("KNR_FLG4", Util.avoidNull(inparam.getManagement4InputFlag()));
		// 管理5
		addSendValues("KNR_FLG5", Util.avoidNull(inparam.getManagement5InputFlag()));
		// 管理6
		addSendValues("KNR_FLG6", Util.avoidNull(inparam.getManagement6InputFlag()));
		// 非会計1
		addSendValues("HM_FLG1", Util.avoidNull(inparam.getNonAccountingDetail1Flag()));
		// 非会計2
		addSendValues("HM_FLG2", Util.avoidNull(inparam.getNonAccountingDetail2Flag()));
		// 非会計3
		addSendValues("HM_FLG3", Util.avoidNull(inparam.getNonAccountingDetail3Flag()));
		// 売上課税入力ﾌﾗｸﾞ
		addSendValues("URI_ZEI_FLG", Util.avoidNull(inparam.getSalesTaxInputFlag()));
		// 仕入課税入力ﾌﾗｸﾞ
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(inparam.getPurchaseTaxationInputFlag()));
		// 多通貨入力ﾌﾗｸﾞ
		addSendValues("MCR_FLG", Util.avoidNull(inparam.getMultipleCurrencyInputFlag()));
		// BG科目制御区分
		addSendValues("KMK_CNT_BG", Util.avoidNull(inparam.getBgItemCtrlDivision()));
		// 相殺科目制御区分
		addSendValues("KMK_CNT_SOUSAI", Util.avoidNull(inparam.getCounterbalanceAdjustmentCtrlDivision()));
		// 科目体系コード
		addSendValues("KMK_TK_CODE", Util.avoidNull(inparam.getItemSystemCode()));
		// 科目体系フラグ
		addSendValues("KMK_TK_FLG", Util.avoidNull(inparam.getItemSystemFlg()));

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		Map<String, String> map = getResult();

		// 取得した値をOutputParameterにセット
		setResultData(map);
	}

	/**
	 * 取得した値をOutputパラメータにセット
	 * 
	 * @param resultMap 取得したデータ
	 */
	protected void setResultData(Map resultMap) {

		AccountItemOutputParameter outparam = field.getOutputParameter();

		// 指定コードの存在チェック
		outparam.setExist(!"0".equals(resultMap.get("existFlag")));

		// 科目略称
		outparam.setItemAbbrName(Util.avoidNull(resultMap.get("KMK_NAME_S")));
		// 補助区分
		outparam.setSubItemFlag(Util.avoidNull(resultMap.get("HKM_KBN")));
		// 売上課税入力フラグ
		outparam.setSalesTaxInputFlag(Util.avoidNull(resultMap.get("URI_ZEI_FLG")));
		// 仕入課税入力フラグ
		outparam.setPurchaseTaxationInputFlag(Util.avoidNull(resultMap.get("SIR_ZEI_FLG")));
		// 消費税コード
		outparam.setConsumptionTaxCode(Util.avoidNull(resultMap.get("ZEI_CODE")));
		// 消費税区分
		outparam.setConsumptionTaxDivision(Util.avoidNull(resultMap.get("SZEI_KEI_KBN")));
		// 消費税略称
		outparam.setConsumptionTaxAbbrName(Util.avoidNull(resultMap.get("ZEI_NAME_S")));
		// 取引先入力フラグ
		outparam.setCustomerInputFlag(Util.avoidNull(resultMap.get("TRI_CODE_FLG")));
		// 社員コード
		outparam.setEmployeeCode(Util.avoidNull(resultMap.get("EMP_CODE")));
		// 社員略称
		outparam.setEmployeeAbbrName(Util.avoidNull(resultMap.get("EMP_NAME_S")));
		// 社員入力フラグ
		outparam.setEmployeeInputFlag(Util.avoidNull(resultMap.get("EMP_CODE_FLG")));
		// 管理1入力フラグ
		outparam.setManagement1InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_1")));
		// 管理2入力フラグ
		outparam.setManagement2InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_2")));
		// 管理3入力フラグ
		outparam.setManagement3InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_3")));
		// 管理4入力フラグ
		outparam.setManagement4InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_4")));
		// 管理5入力フラグ
		outparam.setManagement5InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_5")));
		// 管理6入力フラグ
		outparam.setManagement6InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_6")));
		// 非会計明細1入力フラグ
		outparam.setNonAccountingDetail1Flag(Util.avoidNull(resultMap.get("HM_FLG_1")));
		// 非会計明細2入力フラグ
		outparam.setNonAccountingDetail2Flag(Util.avoidNull(resultMap.get("HM_FLG_2")));
		// 非会計明細3入力フラグ
		outparam.setNonAccountingDetail3Flag(Util.avoidNull(resultMap.get("HM_FLG_3")));
		// 発生日入力フラグ
		outparam.setAccrualDateInputFlag(Util.avoidNull(resultMap.get("HAS_FLG")));
		// 多通貨入力フラグ
		outparam.setMultipleCurrencyInputFlag(Util.avoidNull(resultMap.get("MCR_FLG")));
		// GL科目制御区分
		outparam.setKmkCntGl(Util.avoidNull(resultMap.get("KMK_CNT_GL")));
		// AP科目制御区分
		outparam.setKmkCntAp(Util.avoidNull(resultMap.get("KMK_CNT_AP")));
		// AR科目制御区分
		outparam.setKmkCntAr(Util.avoidNull(resultMap.get("KMK_CNT_AR")));

	}
}
