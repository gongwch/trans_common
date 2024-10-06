package jp.co.ais.trans2.master.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座マスタ画面コントロール<br>
 * IBAN等情報追加版
 * 
 * @author AIS
 */
public class MP0031BankAccountMasterPanelCtrl extends MP0030BankAccountMasterPanelCtrl {

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	@Override
	protected void createMainView() {
		mainView = new MP0031BankAccountMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	@Override
	protected void createEditView() {

		// 編集画面を生成
		editView = new MP0031BankAccountMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * 編集画面で入力された銀行口座を返す
	 * 
	 * @return 編集画面で入力された銀行口座
	 */
	@Override
	protected BankAccount getInputedData() {
		BankAccount bean = super.getInputedData();

		MP0031BankAccountMasterDialog dialog = (MP0031BankAccountMasterDialog) editView;

		// 銀行名（英字）
		bean.setBankNameE(dialog.ctrlBankNameE.getValue());

		// 支店名（英字）
		bean.setBranchNameE(dialog.ctrlBranchNameE.getValue());

		// IBANコード
		bean.setIBan(dialog.ctrlIBan.getValue());

		// 銀行識別子
		bean.setBankIndentify(dialog.ctrlBankIndentify.getValue());

		// 口座識別子
		bean.setBankAccountIndentify(dialog.ctrlBankAccountIndentify.getValue());

		// SWIFTコード
		bean.setSwift(dialog.ctrlSwift.getValue());
		
		// Bank Country
		bean.setBankCountry(dialog.ctrlBankCountry.getCode());

		// 住所1
		bean.setBnkAdr1(dialog.ctrlBnkAdr1.getValue());

		// 住所1（英字）
		bean.setBnkAdr1E(dialog.ctrlBnkAdr1E.getValue());

		// 住所2
		bean.setBnkAdr2(dialog.ctrlBnkAdr2.getValue());

		// 住所2（英字）
		bean.setBnkAdr2E(dialog.ctrlBnkAdr2E.getValue());

		return bean;
	}

	/**
	 * 画面に銀行口座情報をセットする
	 * 
	 * @param bean
	 */
	@Override
	protected void setEditDialog(BankAccount bean) {
		super.setEditDialog(bean);

		MP0031BankAccountMasterDialog dialog = (MP0031BankAccountMasterDialog) editView;

		// 銀行名（英字）
		dialog.ctrlBankNameE.setValue(bean.getBankNameE());

		// 支店名（英字）
		dialog.ctrlBranchNameE.setValue(bean.getBranchNameE());

		// IBANコード
		dialog.ctrlIBan.setValue(bean.getIBan());

		// 銀行識別子
		dialog.ctrlBankIndentify.setValue(bean.getBankIndentify());

		// 口座識別子
		dialog.ctrlBankAccountIndentify.setValue(bean.getBankAccountIndentify());

		// SWIFTコード
		dialog.ctrlSwift.setValue(bean.getSwift());
		
		// BANK COUNTRY
		dialog.ctrlBankCountry.code.setValue(bean.getBankCountry());
		dialog.ctrlBankCountry.refleshEntity();

		// 住所1
		dialog.ctrlBnkAdr1.setValue(bean.getBnkAdr1());

		// 住所1（英字）
		dialog.ctrlBnkAdr1E.setValue(bean.getBnkAdr1E());

		// 住所2
		dialog.ctrlBnkAdr2.setValue(bean.getBnkAdr2());

		// 住所2（英字）
		dialog.ctrlBnkAdr2E.setValue(bean.getBnkAdr2E());
	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param bean データ
	 * @return 一覧に表示する形式に変換された銀行口座
	 */
	@Override
	protected String[] getRowData(BankAccount bean) {

		List<String> list = new ArrayList<String>();

		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getCurrencyCode());
		list.add(bean.getBankCode());
		list.add(bean.getBankName());
		list.add(bean.getBankNameE()); // 銀行名（英字）
		list.add(bean.getBranchCode());
		list.add(bean.getBranchName());
		list.add(bean.getBranchNameE()); // 支店名（英字）
		list.add(bean.getClientCode());
		list.add(bean.getClientName());
		list.add(bean.getClientNameJ());
		list.add(bean.getClientNameE());
		list.add(getWord(DepositKind.getDepositKindName(bean.getDepositKind())));
		list.add(bean.getAccountNo());
		list.add(bean.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"));
		list.add(bean.isUseExPayment() ? getWord("C02151") : getWord("C02150"));
		list.add(bean.getDepartmentCode());
		list.add(bean.getDepartmentNames());
		list.add(bean.getItemCode());
		list.add(bean.getItemNames());
		list.add(bean.getSubItemCode());
		list.add(bean.getSubItemNames());
		list.add(bean.getDetailItemCode());
		list.add(bean.getDetailItemNames());
		list.add(bean.getBankIndentify()); // 銀行識別子
		list.add(bean.getBankAccountIndentify()); // 口座識別子
		list.add(bean.getIBan()); // IBANコード
		list.add(bean.getSwift()); // SWIFTコード
		list.add(bean.getBankCountry()); // BANK COUNTRY
		list.add(bean.getBnkAdr1()); // 住所1
		list.add(bean.getBnkAdr1E()); // 住所1（英字）
		list.add(bean.getBnkAdr2()); // 住所2
		list.add(bean.getBnkAdr2E()); // 住所2（英字）
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));

		return list.toArray(new String[list.size()]);
	}
}
