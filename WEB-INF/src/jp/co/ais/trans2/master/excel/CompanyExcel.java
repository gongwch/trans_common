package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社コントロールマスタマスタエクセル
 * 
 * @author AIS
 */
public class CompanyExcel extends TExcel {

	/** 検索条件 */
	protected CompanySearchCondition condition = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param condition
	 */
	public CompanyExcel(String lang, CompanySearchCondition condition) {
		super(lang);

		this.condition = condition;
	}

	/**
	 * 一覧をエクセルで返す
	 * 
	 * @param list
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(List<Company> list) throws TException {

		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param list
	 */
	public void createReport(List<Company> list) {

		// シート追加
		// 会社コントロールマスタ
		TExcelSheet sheet = addSheet(getWord("C00910"));

		// カラム設定
		// 会社コード
		sheet.addColumn(getWord("C00596"), 4200);
		// 会社名称
		sheet.addColumn(getWord("C00685"), 12600);
		// 会社略称
		sheet.addColumn(getWord("C00686"), 8400);
		// 住所1
		sheet.addColumn(getWord("C00687"), 12600);
		// 住所2
		sheet.addColumn(getWord("C00688"), 12600);
		// 住所カナ
		sheet.addColumn(getWord("C01152"), 12600);
		// 郵便番号
		sheet.addColumn(getWord("C00527"), 4200);
		// 電話番号
		sheet.addColumn(getWord("C00393"), 4200);
		// FAX番号
		sheet.addColumn(getWord("C00690"), 4200);
		if (condition.isInvoiceFlg()) {
			// 適格請求書発行事業者登録番号
			sheet.addColumn(getWord("C12171"), 8000);
		}
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);
		// 科目名称
		sheet.addColumn(getWord("C00700"), 8400);
		// 補助科目名称
		sheet.addColumn(getWord("C00701"), 8400);
		// 内訳科目名称
		sheet.addColumn(getWord("C00702"), 8400);
		// 管理区分1
		sheet.addColumn(getWord("C02359"), 8400);
		// 管理区分2
		sheet.addColumn(getWord("C02360"), 8400);
		// 管理区分3
		sheet.addColumn(getWord("C02361"), 8400);
		// 管理区分4
		sheet.addColumn(getWord("C02362"), 8400);
		// 管理区分5
		sheet.addColumn(getWord("C02363"), 8400);
		// 管理区分6
		sheet.addColumn(getWord("C02364"), 8400);
		// 非会計明細区分1
		sheet.addColumn(getWord("C00943"), 8400);
		// 非会計明細名称1
		sheet.addColumn(getWord("C00709"), 8400);
		// 非会計明細区分2
		sheet.addColumn(getWord("C00944"), 8400);
		// 非会計明細名称2
		sheet.addColumn(getWord("C00710"), 8400);
		// 非会計明細区分3
		sheet.addColumn(getWord("C00945"), 8400);
		// 非会計明細名称3
		sheet.addColumn(getWord("C00711"), 8400);
		// 自動採番部桁数
		sheet.addColumn(getWord("C00224"), 4200);
		// 自動設定項目1
		sheet.addColumn(getWord("C01110"), 4200);
		// 自動設定項目2
		sheet.addColumn(getWord("C01111"), 4200);
		// 自動設定項目3
		sheet.addColumn(getWord("C01112"), 4200);
		// レート換算端数処理
		sheet.addColumn(getWord("C00557"), 8400);
		// 仮受消費税端数処理
		sheet.addColumn(getWord("C00082"), 8400);
		// 仮払消費税端数処理
		sheet.addColumn(getWord("C00083"), 8400);
		// 伝票印刷区分
		sheet.addColumn(getWord("C01248"), 4200);
		// 印刷時の初期値
		sheet.addColumn(getWord("C01000"), 4200);
		// 換算区分
		sheet.addColumn(getWord("C00897"), 4200);
		// 決算段階区分
		sheet.addColumn(getWord("C00145"), 4200);
		// 決算伝票入力区分
		sheet.addColumn(getWord("C01056"), 8400);
		// 評価替レート区分
		sheet.addColumn(getWord("C00454"), 8400);
		// 中間決算繰越区分
		sheet.addColumn(getWord("C11141"), 8400);
		// 期首月
		sheet.addColumn(getWord("C00105"), 4200);
		// 現場承認
		sheet.addColumn(getWord("C00157"), 4200);
		// 経理承認
		sheet.addColumn(getWord("C01616"), 4200);
		// 基軸通貨
		sheet.addColumn(getWord("C00907"), 4200);
		// 機能通貨
		sheet.addColumn(getWord("C11084"), 4200);
		// グループ会計区分
		sheet.addColumn(getWord("C11142"), 8400);
		// IFRS区分
		sheet.addColumn(getWord("C11143"), 4200);
		if (condition.isShowARSignerEng()) {
			// AR：英分請求書SIGNER表示可否
			sheet.addColumn(getWord("C12175"), 12600);
		}

		if (condition.isInvoiceFlg()) {
			// インボイス制度
			sheet.addColumn(getWord("C12199"), 4500);
		}
		// 色設定
		sheet.addColumn(getWord("C11144"), 4200);

		if (condition.isShowInvoice()) {
			// INVOICE(会社基礎情報英語版)--追加

			StringBuilder sb = new StringBuilder();
			sb.append(getWord("COP988"));
			sb.append(" ");

			// 会社名称
			StringBuilder hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C00685"));
			hdrName.append(getWord("C11896"));
			sheet.addColumn(hdrName.toString(), 12600);
			// 会社略称
			hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C00686"));
			hdrName.append(getWord("C11896"));
			sheet.addColumn(hdrName.toString(), 8400);
			// 住所1
			hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C11893"));
			sheet.addColumn(hdrName.toString(), 12600);
			// 住所2
			hdrName = new StringBuilder();
			hdrName.append(sb.toString());
			hdrName.append(getWord("C11894"));
			sheet.addColumn(hdrName.toString(), 12600);
		}
		if (!condition.isNotShowSpc()) {
			// SPC会社情報--追加

			// 連結用会社コード
			sheet.addColumn(getWord("C11762"), 8400);
			// サイン者・役職名
			sheet.addColumn(getWord("C11763"), 8400);
			// 外国送金担当者
			sheet.addColumn(getWord("C11764"), 4200);
			// SPC連絡用電話番号
			sheet.addColumn(getWord("C11765"), 8400);
			// DEBIT NOTE会社住所1
			sheet.addColumn(getWord("C11767"), 8400);
			// DEBIT NOTE会社住所2
			sheet.addColumn(getWord("C11768"), 8400);
			// DEBIT NOTE会社住所3
			sheet.addColumn(getWord("C11769"), 8400);
			// DEBIT NOTEフッタ情報
			sheet.addColumn(getWord("C11770"), 8400);
		}

		// 明細描画
		for (Company bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getAddress1());
			newRow.addCell(bean.getAddress2());
			newRow.addCell(bean.getAddressKana());
			newRow.addCell(bean.getZip());
			newRow.addCell(bean.getPhoneNo());
			newRow.addCell(bean.getFaxNo());
			if (condition.isInvoiceFlg()) {
				newRow.addCell(bean.getInvRegNo());
			}
			newRow.addCell(bean.getDateFrom());
			newRow.addCell(bean.getDateTo());

			AccountConfig ac = bean.getAccountConfig();
			FiscalPeriod fp = bean.getFiscalPeriod();
			newRow.addCell(ac.getItemName());
			newRow.addCell(ac.getSubItemName());
			newRow.addCell(ac.getDetailItemName());
			newRow.addCell(ac.getManagement1Name());
			newRow.addCell(ac.getManagement2Name());
			newRow.addCell(ac.getManagement3Name());
			newRow.addCell(ac.getManagement4Name());
			newRow.addCell(ac.getManagement5Name());
			newRow.addCell(ac.getManagement6Name());

			newRow.addCell(getWord(NonAccountingDivision.getNonAccountName(ac.getNonAccounting1())),
				SwingConstants.CENTER);
			newRow.addCell(ac.getNonAccounting1Name());

			newRow.addCell(getWord(NonAccountingDivision.getNonAccountName(ac.getNonAccounting2())),
				SwingConstants.CENTER);
			newRow.addCell(ac.getNonAccounting2Name());

			newRow.addCell(getWord(NonAccountingDivision.getNonAccountName(ac.getNonAccounting3())),
				SwingConstants.CENTER);
			newRow.addCell(ac.getNonAccounting3Name());

			newRow.addCell(ac.getSlipNoDigit());
			newRow.addCell(getWord(SlipNoAdopt.getSlipNoAdoptName(ac.getSlipNoAdopt1())));

			newRow.addCell(getWord(SlipNoAdopt.getSlipNoAdoptName(ac.getSlipNoAdopt2())));

			newRow.addCell(getWord(SlipNoAdopt.getSlipNoAdoptName(ac.getSlipNoAdopt3())));

			if (ac.getExchangeFraction().getValue() == 0) {
				// 切り捨て
				newRow.addCell(getWord("C01207"), SwingConstants.CENTER);
			} else {
				// 四捨五入
				newRow.addCell(getWord("C00215"), SwingConstants.CENTER);
			}

			if (ac.getReceivingFunction().getValue() == 0) {
				// 切り捨て
				newRow.addCell(getWord("C01207"), SwingConstants.CENTER);
			} else if (ac.getReceivingFunction().getValue() == 1) {
				// 切り上げ
				newRow.addCell(getWord("C01208"), SwingConstants.CENTER);
			} else {
				// 四捨五入
				newRow.addCell(getWord("C00215"), SwingConstants.CENTER);
			}

			if (ac.getPaymentFunction().getValue() == 0) {
				// 切り捨て
				newRow.addCell(getWord("C01207"), SwingConstants.CENTER);
			} else {
				// 四捨五入
				newRow.addCell(getWord("C00215"), SwingConstants.CENTER);
			}

			if (ac.isSlipPrint() == true) {
				// 使用する
				newRow.addCell(getWord("C00281"), SwingConstants.CENTER);
			} else {
				// 使用しない
				newRow.addCell(getWord("C00282"), SwingConstants.CENTER);
			}

			if (ac.getSlipPrintDefault() == true) {
				// ON=印刷する
				newRow.addCell(getWord("C02368"), SwingConstants.CENTER);
			} else {
				// OFF=印刷停止
				newRow.addCell(getWord("C01001"), SwingConstants.CENTER);
			}

			if (ac.getConvertType().getValue() == 0) {
				// 掛算
				newRow.addCell(getWord("C00065"), SwingConstants.CENTER);
			} else {
				// 割算
				newRow.addCell(getWord("C00563"), SwingConstants.CENTER);
			}

			if (fp.getMaxSettlementStage() == 0) {
				// 0=行わない
				newRow.addCell(getWord("C00038"), SwingConstants.CENTER);
			} else {
				// >0=XX次決算(行う)
				String settlementLevel = FormatUtil.settlementLevelFormat(fp.getMaxSettlementStage(), lang, true);
				newRow.addCell(settlementLevel, SwingConstants.CENTER);
			}

			if (fp.getSettlementTerm().getValue() == 0) {
				// 年次
				newRow.addCell(getWord("C11145"), SwingConstants.CENTER);
			} else if (fp.getSettlementTerm().getValue() == 1) {
				// 半期
				newRow.addCell(getWord("C00435"), SwingConstants.CENTER);
			} else if (fp.getSettlementTerm().getValue() == 2) {
				// 四半期
				newRow.addCell(getWord("C10592"), SwingConstants.CENTER);
			} else {
				// 月次
				newRow.addCell(getWord("C00147"), SwingConstants.CENTER);
			}

			if (ac.getEvaluationRateDate().getValue() == 0) {
				// 当月末日
				newRow.addCell(getWord("C11146"), SwingConstants.CENTER);
			} else {
				// 翌月月初
				newRow.addCell(getWord("C11147"), SwingConstants.CENTER);
			}

			if (ac.isCarryJournalOfMidtermClosingForward()) {
				// 繰り越す
				newRow.addCell(getWord("C11148"), SwingConstants.CENTER);
			} else {
				// 繰り越さない
				newRow.addCell(getWord("C11218"), SwingConstants.CENTER);
			}

			// 月
			newRow.addCell(bean.getFiscalPeriod().getMonthBeginningOfPeriod() + getWord("C02782"),
				SwingConstants.CENTER);

			if (ac.isUseFieldApprove() == true) {
				// 承認する
				newRow.addCell(getWord("C00283"), SwingConstants.CENTER);
			} else {
				// 承認しない
				newRow.addCell(getWord("C11149"), SwingConstants.CENTER);
			}

			if (ac.isUseApprove() == true) {
				// 承認する
				newRow.addCell(getWord("C00283"), SwingConstants.CENTER);
			} else {
				// 承認しない
				newRow.addCell(getWord("C11149"), SwingConstants.CENTER);
			}

			newRow.addCell(ac.getKeyCurrency().getCode(), SwingConstants.CENTER);
			newRow.addCell(ac.getFunctionalCurrency().getCode(), SwingConstants.CENTER);

			if (ac.isUseGroupAccount() == true) {
				// 使用する
				newRow.addCell(getWord("C00281"), SwingConstants.CENTER);
			} else {
				// 使用しない
				newRow.addCell(getWord("C00282"), SwingConstants.CENTER);
			}

			if (ac.isUseIfrs() == true) {
				// 使用する
				newRow.addCell(getWord("C00281"), SwingConstants.CENTER);
			} else {
				// 使用しない
				newRow.addCell(getWord("C00282"), SwingConstants.CENTER);
			}
			if (condition.isShowARSignerEng()) {
				// AR：英分請求書SIGNER表示可否
				newRow.addCell(bean.getAR_SIGN_NAME());
			}

			if (condition.isInvoiceFlg()) {
				// インボイス制度
				newRow.addCell(bean.isCMP_INV_CHK_FLG() ? getWord("C00281") : getWord("C00282"), SwingConstants.CENTER);
			}

			newRow.addCell(Util.to16RGBColorCode(bean.getColor()));

			if (condition.isShowInvoice()) {
				// INVOICE(会社基礎情報英語版)--追加
				newRow.addCell(bean.getKAI_NAME_ENG());
				newRow.addCell(bean.getKAI_NAME_S_ENG());
				newRow.addCell(bean.getJYU_1_ENG());
				newRow.addCell(bean.getJYU_2_ENG());
			}
			if (!condition.isNotShowSpc()) {
				// SPC会社情報--追加
				newRow.addCell(bean.getConnCompanyCode());
				newRow.addCell(bean.getSignerPosition());
				newRow.addCell(bean.getRemitterName());
				newRow.addCell(bean.getConnPhoneNo());
				newRow.addCell(bean.getDebitNoteAddress1());
				newRow.addCell(bean.getDebitNoteAddress2());
				newRow.addCell(bean.getDebitNoteAddress3());
				newRow.addCell(bean.getDebitNoteInfo());
			}

		}

	}
}
