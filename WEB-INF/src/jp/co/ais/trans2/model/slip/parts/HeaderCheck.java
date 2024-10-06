package jp.co.ais.trans2.model.slip.parts;

import static jp.co.ais.trans2.define.SlipError.*;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bill.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 仕訳ヘッダーチェック
 */
public class HeaderCheck extends TModel {

	/** エラー情報 */
	protected List<Message> errorList = new LinkedList<Message>();

	/** 会社情報 */
	protected Map<String, Company> companys = new TreeMap<String, Company>();

	/**
	 * 仕訳ジャーナルヘッダーの項目をチェックする
	 * 
	 * @param slip 伝票データ
	 * @return errList エラーリスト
	 * @throws TException
	 */
	public List<Message> checkHeader(Slip slip) throws TException {
		errorList.clear();
		companys.clear();

		// 締め関連チェック
		checkSlipDate(slip);

		// 有効期限チェック
		checkTerm(slip);

		return errorList;
	}

	/**
	 * 伝票日付(締め日)、決算段階の再チェックを行う
	 * 
	 * @param slip 伝票データ
	 * @throws TException
	 */
	protected void checkSlipDate(Slip slip) throws TException {

		Company keyCompany = getCompany(slip.getCompanyCode());

		// 締めチェック
		Date slipDate = slip.getSlipDate(); // 伝票日付
		int stage = slip.getHeader().getSWK_KSN_KBN(); // 決算段階
		if (isClosed(keyCompany, slipDate, stage)) {
			addError(CLOSED, SLIP_DATE, "I00131");// 指定の伝票日付は締められています。
		}

		// 計上先の先行、締めチェック
		for (SWK_DTL dtl : slip.getDetails()) {
			Company kcompany = getCompany(dtl.getSWK_K_KAI_CODE());
			if (keyCompany.getCode().equals(kcompany.getCode())) {
				continue;
			}

			// 締めチェック
			if (isClosed(kcompany, slipDate, stage)) {
				addError(CLOSED, SLIP_DATE, "I00134", kcompany.getCode());// 指定の伝票日付は計上会社先[{0}]で締められています。
			}
		}
	}

	/**
	 * 指定会社を基準に、締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 
	 * @param company 会社
	 * @param slipDate 伝票日付
	 * @param settlementStage 決算段階
	 * @return 締められている場合true、締められていない場合false
	 */
	protected boolean isClosed(Company company, Date slipDate, int settlementStage) {

		Date closedDate = company.getFiscalPeriod().getLastDateOfClosedPeriod();
		if (closedDate.compareTo(slipDate) > 0) {
			return true;

		} else if (closedDate.compareTo(slipDate) == 0
			&& settlementStage <= company.getFiscalPeriod().getSettlementStage()) {
			return true;
		}

		return false;
	}

	/**
	 * ヘッダーの有効期限をチェックする
	 * 
	 * @param slip 伝票データ
	 */
	protected void checkTerm(Slip slip) {

		SWK_HDR hdr = slip.getHeader();

		// 科目、補助科目、内訳科目の名称は会社コントロールマスタより取得
		Company cmp = null;
		try {
			cmp = getCompany(hdr.getKAI_CODE());
		} catch (TException e1) {
			cmp = getCompany();
		}
		AccountConfig ac = cmp.getAccountConfig();

		ItemManager itemMn = (ItemManager) getComponent(ItemManager.class);

		// 部門マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE())) {
			DepartmentManager dao = (DepartmentManager) getComponent(DepartmentManager.class);

			// 部門マスタ検索（存在チェック）
			DepartmentSearchCondition condition = new DepartmentSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_DEP_CODE());
			List<Department> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_DEP_CODE, "I00066", "C00571", hdr.getSWK_DEP_CODE());

			} else {
				Department bean = list.get(0);

				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HDR_DEP_CODE, "I00061", "C00571", hdr.getSWK_DEP_CODE());
				}
			}
		}

		// 科目マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE())) {

			// 科目マスタ検索（存在チェック）
			Item bean = null;
			try {
				bean = itemMn.getItem(hdr.getKAI_CODE(), hdr.getSWK_KMK_CODE(), null, null);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (bean == null) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_KMK_CODE, "I00077", ac.getItemName(), hdr.getSWK_KMK_CODE());

			} else {

				if (!Util.isNullOrEmpty(bean.getFixedDepartmentCode())
					&& !bean.getFixedDepartmentCode().equals(hdr.getSWK_DEP_CODE())) {

					// エラー情報の設定
					addError(NOT_FOUND, HDR_KMK_CODE, "I00077", ac.getItemName(), hdr.getSWK_KMK_CODE());

				} else {
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
						|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

						// エラー情報の設定
						addError(TERM_OUT, HDR_KMK_CODE, "I00078", ac.getItemName(), hdr.getSWK_KMK_CODE());
					}
				}
			}
		}

		// 補助科目マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {

			// 補助科目マスタ検索
			Item bean = null;
			try {
				bean = itemMn.getItem(hdr.getKAI_CODE(), hdr.getSWK_KMK_CODE(), hdr.getSWK_HKM_CODE(), null);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (bean == null || bean.getSubItem() == null) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_HKM_CODE, "I00077", ac.getSubItemName(), hdr.getSWK_HKM_CODE());

			} else {
				// 有効期限チェック
				Date from = bean.getSubItem().getDateFrom();
				Date to = bean.getSubItem().getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {
					// エラー情報の設定
					addError(TERM_OUT, HDR_HKM_CODE, "I00078", ac.getSubItemName(), hdr.getSWK_HKM_CODE());
				}
			}
		}

		// 内訳科目マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())
			&& !Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {

			// 内訳科目マスタ検索
			Item bean = null;
			try {
				bean = itemMn.getItem(hdr.getKAI_CODE(), hdr.getSWK_KMK_CODE(), hdr.getSWK_HKM_CODE(),
					hdr.getSWK_UKM_CODE());
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (bean == null || bean.getSubItem() == null || bean.getSubItem().getDetailItem() == null) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_HKM_CODE, "I00077", ac.getDetailItemName(), hdr.getSWK_UKM_CODE());

			} else {
				// 有効期限チェック
				Date from = bean.getSubItem().getDetailItem().getDateFrom();
				Date to = bean.getSubItem().getDetailItem().getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HDR_HKM_CODE, "I00078", ac.getDetailItemName(), hdr.getSWK_UKM_CODE());
				}
			}
		}

		// 社員マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_EMP_CODE())) {

			// 社員マスタDao
			EmployeeManager dao = (EmployeeManager) getComponent(EmployeeManager.class);

			// 社員マスタデータを検索
			EmployeeSearchCondition condition = new EmployeeSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_EMP_CODE());
			List<Employee> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_EMP_CODE, "I00066", "C00697", hdr.getSWK_EMP_CODE());

			} else {
				Employee bean = list.get(0);
				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HDR_EMP_CODE, "I00061", "C00697", hdr.getSWK_EMP_CODE());
				}
			}
		}

		// 取引先マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE())) {

			// 取引先マスタDao
			CustomerManager dao = (CustomerManager) getComponent(CustomerManager.class);

			// 取引先マスタデータを検索
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_TRI_CODE());
			List<Customer> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_TRI_CODE, "I00066", "C00786", hdr.getSWK_TRI_CODE());

			} else {
				Customer bean = list.get(0);
				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HDR_TRI_CODE, "I00061", "C00786", hdr.getSWK_TRI_CODE());

				}

			}
		}

		// 取引先支払条件マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_TJK_CODE())) {

			// 取引先支払条件マスタDao
			PaymentSettingManager dao = (PaymentSettingManager) getComponent(PaymentSettingManager.class);

			// 取引先支払条件マスタデータを検索
			PaymentSettingSearchCondition condition = new PaymentSettingSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCustomerCode(hdr.getSWK_TRI_CODE());
			condition.setCode(hdr.getSWK_TJK_CODE());
			List<PaymentSetting> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, TRI_SJ_CODE, "I00066", "C00672", hdr.getSWK_TJK_CODE());

			} else {
				PaymentSetting bean = list.get(0);
				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, TRI_SJ_CODE, "I00061", "C00672", hdr.getSWK_TJK_CODE());

				}

			}
		}

		// 支払方法マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_HOH_CODE())) {

			// 支払方法マスタDao
			PaymentMethodManager dao = (PaymentMethodManager) getComponent(PaymentMethodManager.class);
			PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_HOH_CODE());
			List<PaymentMethod> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, HOH_CODE, "I00066", "C00864", hdr.getSWK_HOH_CODE());

			} else {
				PaymentMethod bean = list.get(0);
				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HOH_CODE, "I00061", "C00864", hdr.getSWK_HOH_CODE());
				}
			}
		}

		// 銀行口座マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE())) {

			// 銀行口座マスタDao
			BankAccountManager dao = (BankAccountManager) getComponent(BankAccountManager.class);

			// 銀行口座マスタデータを検索
			BankAccountSearchCondition cond = new BankAccountSearchCondition();
			cond.setCompanyCode(hdr.getKAI_CODE());
			cond.setCode(hdr.getSWK_CBK_CODE());
			List<BankAccount> bankAccList = null;
			try {
				bankAccList = dao.get(cond);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (bankAccList == null || bankAccList.isEmpty()) {
				// エラー情報の設定
				addError(NOT_FOUND, CBK_CODE, "I00066", "C01879", hdr.getSWK_CBK_CODE());

			} else {

				BankAccount bankAcc = bankAccList.get(0);
				// 有効期限チェック
				Date from = bankAcc.getDateFrom();
				Date to = bankAcc.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, CBK_CODE, "I00061", "C01879", hdr.getSWK_CBK_CODE());
				}
			}
		}

		// 請求区分マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_SEI_KBN())) {
			BillTypeManager logic = (BillTypeManager) getComponent(BillTypeManager.class);
			BillTypeSearchCondition param = new BillTypeSearchCondition();
			param.setCompanyCode(hdr.getKAI_CODE());
			param.setCode(hdr.getSWK_SEI_KBN());

			// 検索
			List<BillType> list = null;
			try {
				list = logic.get(param);
			} catch (TException ex) {
				// エラーの場合はNULL扱い
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_SEI_KBN, "I00066", "C10092", hdr.getSWK_SEI_KBN());// 請求区分のデータが存在しません。

			} else {
				// 有効期限チェック
				BillType bean = list.get(0);
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HDR_CUR_CODE, "I00061", "C10092", hdr.getSWK_SEI_KBN());// の有効期限が切れています。
				}
			}
		}

		// 通貨マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_CUR_CODE())) {
			// 通貨マスタ検索
			CurrencyManager dao = (CurrencyManager) getComponent(CurrencyManager.class);
			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_CUR_CODE());
			List<Currency> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, HDR_CUR_CODE, "I00066", "C00665", hdr.getSWK_CUR_CODE());

			} else {
				Currency bean = list.get(0);
				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, HDR_CUR_CODE, "I00061", "C00665", hdr.getSWK_CUR_CODE());
				}
			}
		}

		// 摘要マスタ
		if (!Util.isNullOrEmpty(hdr.getSWK_TEK_CODE())) {
			// 摘要マスタ検索
			RemarkManager dao = (RemarkManager) getComponent(RemarkManager.class);
			RemarkSearchCondition condition = new RemarkSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_TEK_CODE());
			condition.setDataType(hdr.getSWK_DATA_KBN());
			condition.setSlipRowRemark(false);
			List<Remark> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// データが見つからない
			if (list == null || list.isEmpty()) {

				// エラー情報の設定
				addError(NOT_FOUND, TEK_CODE, "I00066", "C00564", hdr.getSWK_TEK_CODE());

			} else {
				Remark bean = list.get(0);
				// 有効期限チェック
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// エラー情報の設定
					addError(TERM_OUT, TEK_CODE, "I00061", "C00564", hdr.getSWK_TEK_CODE());
				}
			}
		}
	}

	/**
	 * エラー情報を設定する
	 * 
	 * @param errorType エラー種類
	 * @param dataType エラーデータタイプ
	 * @param messageID
	 * @param bindIds
	 */
	protected void addError(SlipError errorType, SlipError dataType, String messageID, Object... bindIds) {

		Message msg = new Message(messageID, bindIds);
		msg.setErrorType(errorType);
		msg.setDataType(dataType);

		errorList.add(msg);
	}

	/**
	 * 会社情報の取得
	 * 
	 * @param companyCode 会社コード
	 * @return 会社情報
	 * @throws TException
	 */
	protected Company getCompany(String companyCode) throws TException {
		CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);

		Company company = companys.get(companyCode);

		if (company == null) {
			company = manager.get(companyCode);
			companys.put(companyCode, company);
		}

		return company;
	}
}
