package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 取引先一覧エクセル
 */
public class CustomerExcel extends TExcel {

	/** true:グループ会社区分有効 */
	public static boolean isUseTRI_TYPE_GRP_FLG = ServerConfig.isFlagOn("trans.kt.MG0150.group.comp.flag");

	/** true:グループ会社区分有効 */
	protected boolean isUseTRI_TYPE_PSN_FLG = ServerConfig.isFlagOn("trans.kt.MG0150.tri.person.flag");

	/** 取引先区分を表示しないかどうか true:表示しない */
	public static boolean isNoVisibleTriDivison = ServerConfig.isFlagOn("trans.MG0150.no.visible.tri.division");

	/** 取引先の敬称/担当部署/担当者などの設定を表示するかどうか true:表示する */
	protected static boolean isUseCustomerManagementSet = ServerConfig
		.isFlagOn("trans.usr.customer.managementi.setting");

	/** invoiceの設定を表示するかどうか true:表示する */
	protected boolean isInvoice = false;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param condition
	 */
	public CustomerExcel(String lang, CustomerSearchCondition condition) {
		super(lang);
		isInvoice = condition.isInvoiceFlg();
	}

	/**
	 * 取引先一覧をエクセルで返す
	 * 
	 * @param customerList 取引先一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Customer> customerList) throws TException {

		try {
			createReport(customerList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param customerList
	 */
	public void createReport(List<Customer> customerList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02326"));// 取引先マスタ

		// カラム設定
		sheet.addColumn(getWord("C00786"), 4200);// 取引先コード
		sheet.addColumn(getWord("C00830"), 8400);// 取引先名称
		sheet.addColumn(getWord("C00787"), 8400);// 取引先略称
		sheet.addColumn(getWord("C00836"), 8400);// 取引先検索名称
		if (!isNoVisibleTriDivison) {
			sheet.addColumn(getWord("C03344"), 15000);// 取引先区分
		}
		if (isUseTRI_TYPE_GRP_FLG) {
			sheet.addColumn(getWord("C04294"), 4200);// グループ会社区分
		}
		if (isUseTRI_TYPE_PSN_FLG) {
			sheet.addColumn(getWord("C12300"), 4200);// 銀行口座を表示しない
		}
		sheet.addColumn(getWord("C02487"), 8400);// 事業所名
		sheet.addColumn(getWord("C00527"), 4200);// 郵便番号
		sheet.addColumn(getWord("C11889"), 4200);// 国コード
		sheet.addColumn(getWord("C11890"), 4200);// 国名
		sheet.addColumn(getWord("C01152"), 8400);// 住所カナ
		sheet.addColumn(getWord("C01150"), 8400);// 住所1
		sheet.addColumn(getWord("C01151"), 8400);// 住所2
		sheet.addColumn(getWord("CBL401"), 8400);// EMail Address
		if (isUseCustomerManagementSet) {
			sheet.addColumn(getWord("C12184"), 4200);// 取引先敬称
			sheet.addColumn(getWord("C12187"), 4200);// 担当者敬称
			sheet.addColumn(getWord("C12185"), 8400);// 担当部署名
			sheet.addColumn(getWord("C12186"), 8400);// 担当者名
		}

		sheet.addColumn(getWord("C00393"), 4200);// 電話番号
		sheet.addColumn(getWord("C00690"), 4200);// FAX番号
		sheet.addColumn(getWord("C00871"), 4200);// 集計相手先コード
		sheet.addColumn(getWord("C11085"), 8400);// 集計相手先名称
		sheet.addColumn(getWord("C01089"), 4200);// 仕入先区分
		sheet.addColumn(getWord("C01261"), 4200);// 得意先区分
		sheet.addColumn(getWord("C02038"), 4200);// 入金条件締め日
		sheet.addColumn(getWord("C02039"), 4200);// 入金条件締め後月
		sheet.addColumn(getWord("C00870"), 4200);// 入金条件入金日
		sheet.addColumn(getWord("C02040"), 8400);// 入金銀行口座コード
		sheet.addColumn(getWord("C11087"), 8400);// 入金銀行口座名称
		sheet.addColumn(getWord("C10133"), 4200);// 振込依頼人名
		sheet.addColumn(getWord("C02042"), 4200);// 入金手数料区分

		if (isInvoice) {
			sheet.addColumn(getWord("C12197"), 6000);// 非適格請求書発行事業者
			sheet.addColumn(getWord("C00286"), 6000);// 消費税
			sheet.addColumn(getWord("C12171"), 7500);// 適格請求書発行事業者登録番号
		}

		sheet.addColumn(getWord("C00055"), 4200);// 開始年月日
		sheet.addColumn(getWord("C00261"), 4200);// 終了年月日

		// 明細描画
		for (Customer customer : customerList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(customer.getCode());
			newRow.addCell(customer.getName());
			newRow.addCell(customer.getNames());
			newRow.addCell(customer.getNamek());
			if (!isNoVisibleTriDivison) {
				newRow.addCell(getTriType(customer));
			}
			if (isUseTRI_TYPE_GRP_FLG) {
				newRow.addCell(customer.isGroupCompany() ? getWord("C10583") : "");// グループ会社区分
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				newRow.addCell(customer.isPersonalCustomer() ? getWord("C10794") : getWord("C10795")); // はい/いいえ
			}
			newRow.addCell(customer.getOfficeName());
			newRow.addCell(customer.getZipCode());
			newRow.addCell(customer.getCountryCode());// 国コード
			newRow.addCell(customer.getCountry() != null ? customer.getCountry().getName() : "");// 国名
			newRow.addCell(customer.getAddressKana());
			newRow.addCell(customer.getAddress());
			newRow.addCell(customer.getAddress2());
			newRow.addCell(customer.getEmailAddress());
			if (isUseCustomerManagementSet) {
				newRow.addCell(getWord(HonorType.getName(customer.getTRI_TITLE_TYPE())), SwingConstants.CENTER); // 取引先敬称
				newRow.addCell(getWord(HonorType.getName(customer.getEMP_TITLE_TYPE())), SwingConstants.CENTER); // 担当者敬称
				newRow.addCell(customer.getCHARGE_DEP_NAME()); // 担当部署名
				newRow.addCell(customer.getCHARGE_EMP_NAME()); // 担当者名
			}

			newRow.addCell(customer.getTel());
			newRow.addCell(customer.getFax());
			newRow.addCell(customer.getSumCode());
			newRow.addCell(customer.getSumName());
			if (CustomerType.NONE == customer.getCustomerType()) {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.NONE)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.NONE)), SwingConstants.CENTER);
			} else if (CustomerType.VENDOR == customer.getCustomerType()) {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.VENDOR)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.NONE)), SwingConstants.CENTER);
			} else if (CustomerType.CUSTOMER == customer.getCustomerType()) {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.NONE)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.CUSTOMER)), SwingConstants.CENTER);
			} else {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.BOTH)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.BOTH)), SwingConstants.CENTER);
			}
			newRow.addCell(customer.getCloseDay());
			newRow.addCell(customer.getNextMonth());
			newRow.addCell(customer.getCashDay());
			newRow.addCell(customer.getBankAccount().getCode());
			newRow.addCell(customer.getBankAccount().getName());
			newRow.addCell(customer.getClientName());
			newRow.addCell(getWord(CashInFeeType.getCashInFeeTypeName(customer.getCashInFeeType())),
				SwingConstants.CENTER);

			if (isInvoice) {
				newRow.addCell(customer.isNO_INV_REG_FLG() ? getWord("C12198") : "", SwingConstants.CENTER);// 非適格請求書発行事業者
				newRow.addCell(customer.getNO_INV_REG_ZEI_NAME());// 消費税
				newRow.addCell(customer.getINV_REG_NO());// 適格請求書発行事業者登録番号
			}
			newRow.addCell(customer.getDateFrom());
			newRow.addCell(customer.getDateTo());
		}

	}

	/**
	 * 選択されている取引先区分を連結
	 * 
	 * @param customer
	 * @return 選択された取引先区分
	 */
	protected String getTriType(Customer customer) {

		// 取引先区分
		StringBuilder sb = new StringBuilder();
		if (customer.isCharterer()) {
			sb.append(getWord("CTC0058"));
		}
		if (customer.isOwner()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0059"));
		}
		if (customer.isPortAgent()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0115"));
		}
		if (customer.isSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0116"));
		}
		if (customer.isBroker()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0078"));
		}
		// BANK
		if (customer.isBank()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("COP532"));
		}
		if (customer.isOther()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0117"));
		}
		if (customer.isShipper()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL206"));
		}
		if (customer.isConsignee()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL209"));
		}
		if (customer.isNotifyParty()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL320"));
		}
		if (customer.isFowarder()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL833"));
		}
		if (customer.isBunkerTrader()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL834"));
		}
		if (customer.isBunkerSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL835"));
		}

		return sb.toString();
	}

}
