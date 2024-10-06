package jp.co.ais.trans2.model.slip.parts;

import static jp.co.ais.trans2.model.slip.parts.SlipHeaderError.DataType.*;
import static jp.co.ais.trans2.model.slip.parts.SlipHeaderError.ErrorType.*;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
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
import jp.co.ais.trans2.model.slip.parts.SlipHeaderError.*;

/**
 * 伝票ヘッダーチェック<BR>
 * 古いバージョン、現状使う場所なし
 */
public class SlipHeaderChecker extends TModel {

	/** 内部保持用セパレータ */
	protected static final String KEY_SEP = "<>";

	/** エラー情報 */
	protected List<SlipHeaderError> errorList = new LinkedList<SlipHeaderError>();

	/**
	 * 伝票ヘッダー不整合チェック
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void check(Slip slip) throws TException {
		List<Slip> list = new ArrayList<Slip>(1);
		list.add(slip);
		this.check(list);
	}

	/**
	 * 伝票ヘッダー不整合チェック
	 * 
	 * @param slipList 伝票リスト
	 * @throws TException
	 */
	public void check(List<Slip> slipList) throws TException {
		// クリア
		errorList.clear();

		// エンティティ設定
		setupEntity(slipList);

		for (Slip slip : slipList) {

			// 基本チェック
			checkBase(slip);

			// 伝票日付チェック
			checkSlipDate(slip);

			// 必須チェック
			checkNull(slip);

			// データ存在/有効期限チェック
			checkTerm(slip);

			// 科目チェック
			checkItem(slip);
		}
	}

	/**
	 * エンティティを構築
	 * 
	 * @param slipList 伝票リスト
	 * @throws TException
	 */
	protected void setupEntity(List<Slip> slipList) throws TException {

		CompanyManager compMng = (CompanyManager) getComponent(CompanyManager.class); // 会社
		DepartmentManager deptMng = (DepartmentManager) getComponent(DepartmentManager.class);// 部門
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class); // 科目
		RemarkManager remarksMng = (RemarkManager) getComponent(RemarkManager.class); // 摘要
		CurrencyManager curMng = (CurrencyManager) getComponent(CurrencyManager.class);// 通貨
		EmployeeManager empMng = (EmployeeManager) getComponent(EmployeeManager.class); // 社員
		CustomerManager triMng = (CustomerManager) getComponent(CustomerManager.class); // 取引先
		PaymentSettingManager tjkMng = (PaymentSettingManager) getComponent(PaymentSettingManager.class); // 取引先条件
		PaymentMethodManager hohMng = (PaymentMethodManager) getComponent(PaymentMethodManager.class); // 支払方法
		BankAccountManager cbkMng = (BankAccountManager) getComponent(BankAccountManager.class); // 銀行口座
		BillTypeManager seiMng = (BillTypeManager) getComponent(BillTypeManager.class); // 請求区分

		CompanySearchCondition compCon = new CompanySearchCondition();
		DepartmentSearchCondition deptCon = new DepartmentSearchCondition();
		RemarkSearchCondition remCon = new RemarkSearchCondition();
		CurrencySearchCondition curCon = new CurrencySearchCondition();
		EmployeeSearchCondition empCon = new EmployeeSearchCondition();
		CustomerSearchCondition triCon = new CustomerSearchCondition();
		PaymentSettingSearchCondition tjkCon = new PaymentSettingSearchCondition();
		PaymentMethodSearchCondition hohCon = new PaymentMethodSearchCondition();
		BankAccountSearchCondition cbkCon = new BankAccountSearchCondition();
		BillTypeSearchCondition seiCon = new BillTypeSearchCondition();

		Map<String, Company> compMap = new TreeMap<String, Company>();
		Map<String, Department> deptMap = new TreeMap<String, Department>();
		Map<String, Item> itemMap = new TreeMap<String, Item>();
		Map<String, Remark> remarksMap = new TreeMap<String, Remark>();
		Map<String, Currency> curMap = new TreeMap<String, Currency>();
		Map<String, Employee> empMap = new TreeMap<String, Employee>();
		Map<String, Customer> triMap = new TreeMap<String, Customer>();
		Map<String, PaymentSetting> tjkMap = new TreeMap<String, PaymentSetting>();
		Map<String, PaymentMethod> hohMap = new TreeMap<String, PaymentMethod>();
		Map<String, BankAccount> cbkMap = new TreeMap<String, BankAccount>();
		Map<String, BillType> seiMap = new TreeMap<String, BillType>();

		for (Slip slip : slipList) {

			SWK_HDR hdr = slip.getHeader(); // ヘッダ
			String kaiCode = hdr.getKAI_CODE(); // 会社コード

			Company company = compMap.get(kaiCode);
			if (company == null) {
				compCon.setCode(kaiCode);
				List<Company> companys = compMng.get(compCon);

				if (!companys.isEmpty()) {
					company = companys.get(0);
					compMap.put(kaiCode, company);
				}
			}
			slip.setCompany(company);
			hdr.setKAI_CODE(kaiCode);

			// 部門
			String dept = hdr.getSWK_DEP_CODE();

			Department department = deptMap.get(kaiCode + KEY_SEP + dept);
			if (department == null) {
				deptCon.setCompanyCode(kaiCode);
				deptCon.setCode(dept);
				List<Department> depts = deptMng.get(deptCon);
				if (!depts.isEmpty()) {
					department = depts.get(0);
					deptMap.put(kaiCode + KEY_SEP + dept, department);
				}
			}
			hdr.setDepartment(department);
			hdr.setSWK_DEP_CODE(dept);

			// 科目
			if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE())) {

				String kmk = hdr.getSWK_KMK_CODE();
				String hkm = hdr.getSWK_HKM_CODE();
				String ukm = hdr.getSWK_UKM_CODE();

				Item item = itemMap.get(kaiCode + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm);
				if (item == null) {
					item = itemMng.getItem(kaiCode, kmk, hkm, ukm);
					itemMap.put(kaiCode + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm, item);
				}
				hdr.setItem(item);
				hdr.setSWK_KMK_CODE(kmk);
				hdr.setSWK_HKM_CODE(hkm);
				hdr.setSWK_UKM_CODE(ukm);
			}

			// 社員
			String emp = hdr.getSWK_EMP_CODE();
			if (!Util.isNullOrEmpty(emp)) {
				Employee employee = empMap.get(kaiCode + KEY_SEP + emp);
				if (employee == null) {
					empCon.setCompanyCode(kaiCode);
					empCon.setCode(emp);

					List<Employee> emps = empMng.get(empCon);
					if (!emps.isEmpty()) {
						employee = emps.get(0);
						empMap.put(kaiCode + KEY_SEP + emp, employee);
					}
				}
				hdr.setEmployee(employee);
				hdr.setSWK_EMP_CODE(emp);
			}

			// 取引先
			String tri = hdr.getSWK_TRI_CODE();
			if (!Util.isNullOrEmpty(tri)) {
				Customer customer = triMap.get(kaiCode + KEY_SEP + tri);
				if (customer == null) {
					triCon.setCompanyCode(kaiCode);
					triCon.setCode(tri);

					List<Customer> tris = triMng.get(triCon);
					if (!tris.isEmpty()) {
						customer = tris.get(0);
						triMap.put(kaiCode + KEY_SEP + tri, customer);
					}
				}
				hdr.setCustomer(customer);
				hdr.setSWK_TRI_CODE(tri);
			}

			// 取引先支払条件
			String tjk = hdr.getSWK_TJK_CODE();
			if (!Util.isNullOrEmpty(tri) && !Util.isNullOrEmpty(tjk)) {
				PaymentSetting pset = tjkMap.get(kaiCode + KEY_SEP + tjk);
				if (pset == null) {
					tjkCon.setCompanyCode(kaiCode);
					tjkCon.setCustomerCode(tri);
					tjkCon.setCode(tjk);

					List<PaymentSetting> tjks = tjkMng.get(tjkCon);
					if (!tjks.isEmpty()) {
						pset = tjks.get(0);
						tjkMap.put(kaiCode + KEY_SEP + tjk, pset);
					}
				}
				hdr.setPaymentSetting(pset);
				hdr.setSWK_TJK_CODE(tjk);
			}

			// 支払方法
			String hoh = hdr.getSWK_HOH_CODE();
			if (!Util.isNullOrEmpty(hoh)) {
				PaymentMethod pmethod = hohMap.get(kaiCode + KEY_SEP + hoh);
				if (pmethod == null) {
					hohCon.setCompanyCode(kaiCode);
					hohCon.setCode(hoh);

					List<PaymentMethod> hohs = hohMng.get(hohCon);
					if (!hohs.isEmpty()) {
						pmethod = hohs.get(0);
						hohMap.put(kaiCode + KEY_SEP + hoh, pmethod);
					}
				}
				hdr.setPaymentMethod(pmethod);
				hdr.setSWK_HOH_CODE(hoh);
			}

			// 銀行口座
			String cbk = hdr.getSWK_CBK_CODE();
			if (!Util.isNullOrEmpty(cbk)) {
				BankAccount bank = cbkMap.get(kaiCode + KEY_SEP + cbk);
				if (bank == null) {
					cbkCon.setCompanyCode(kaiCode);
					cbkCon.setCode(cbk);

					List<BankAccount> cbks = cbkMng.get(cbkCon);
					if (!cbks.isEmpty()) {
						bank = cbks.get(0);
						cbkMap.put(kaiCode + KEY_SEP + cbk, bank);
					}
				}
				hdr.setBankAccount(bank);
				hdr.setSWK_CBK_CODE(cbk);
			}

			// 請求区分
			String sei = hdr.getSWK_SEI_KBN();
			if (!Util.isNullOrEmpty(sei)) {
				BillType billType = seiMap.get(kaiCode + KEY_SEP + sei);
				if (billType == null) {
					seiCon.setCompanyCode(kaiCode);
					seiCon.setCode(sei);

					List<BillType> seis = seiMng.get(seiCon);
					if (!seis.isEmpty()) {
						billType = seis.get(0);
						seiMap.put(kaiCode + KEY_SEP + sei, billType);
					}
				}
				hdr.setBillType(billType);
				hdr.setSWK_SEI_KBN(sei);
			}

			// 通貨
			String cur = hdr.getSWK_CUR_CODE();

			Currency currency = curMap.get(kaiCode + KEY_SEP + cur);
			if (currency == null) {
				curCon.setCompanyCode(kaiCode);
				curCon.setCode(cur);
				List<Currency> curs = curMng.get(curCon);
				if (!curs.isEmpty()) {
					currency = curs.get(0);
					curMap.put(kaiCode + KEY_SEP + cur, currency);
				}
			}
			hdr.setCurrency(currency);
			hdr.setSWK_CUR_CODE(cur);

			// 摘要
			String tek = hdr.getSWK_TEK_CODE();
			if (!Util.isNullOrEmpty(tek)) {

				Remark remark = remarksMap.get(kaiCode + KEY_SEP + tek);
				if (remark == null) {
					remCon.setCompanyCode(kaiCode);
					remCon.setCode(tek);

					List<Remark> rems = remarksMng.get(remCon);
					if (!rems.isEmpty()) {
						remark = rems.get(0);
						remarksMap.put(kaiCode + KEY_SEP + tek, remark);
					}
				}
				hdr.setRemark(remark);
				hdr.setSWK_TEK_CODE(tek);
			}
		}
	}

	/**
	 * 基本チェック
	 * 
	 * @param slip 伝票
	 */
	protected void checkBase(Slip slip) {

		if (slip.getDetails().isEmpty()) {
			addError(EMPTY_DETAIL, SLIP, slip);
			return;
		}

		// バランスチェック
		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;

		for (SWK_DTL dtl : slip.getDetails()) {
			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());
		}

		if (dr.compareTo(cr) != 0) {
			addError(UNBALANCE_AMOUNT, SLIP, slip);
		}
	}

	/**
	 * 伝票日付(締め日)、決算段階のチェック
	 * 
	 * @param slip 伝票データ
	 */
	protected void checkSlipDate(Slip slip) {

		// 締めチェック
		Date slipDate = slip.getSlipDate(); // 伝票日付
		int stage = slip.getHeader().getSWK_KSN_KBN(); // 決算段階

		if (slip.getCompany().getFiscalPeriod().isClosed(slipDate, stage)) {
			addError(CLOSED_SLIP_DATE, SLIP_DATE, slip);
		}
	}

	/**
	 * 必須チェック
	 * 
	 * @param slip 伝票
	 */
	protected void checkNull(Slip slip) {

		SWK_HDR hdr = slip.getHeader();

		// 摘要
		if (Util.isNullOrEmpty(hdr.getSWK_TEK())) {
			addError(NULL, REMARKS, slip);
		}
	}

	/**
	 * ヘッダーの有効期限をチェックする
	 * 
	 * @param slip 伝票データ
	 */
	protected void checkTerm(Slip slip) {

		SWK_HDR hdr = slip.getHeader();

		// 伝票日付
		Date slipDate = slip.getSlipDate();

		// 部門
		if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE())) {

			Department department = hdr.getDepartment();
			if (department == null) {
				// 存在チェック
				addError(NOT_FOUND, DEPARTMENT, slip);

			} else if (isTermOver(slipDate, department.getDateFrom(), department.getDateTo())) {
				// 有効期限チェック
				addError(TERM_OUT, DEPARTMENT, slip);
			}
		}

		// 科目
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE())) {
			Item item = hdr.getItem();

			if (item == null) {
				addError(NOT_FOUND, ITEM, slip);

			} else if (!Util.isNullOrEmpty(item.getFixedDepartmentCode())
				&& !item.getFixedDepartmentCode().equals(hdr.getSWK_DEP_CODE())) {
				// TODO固定部門チェック
				addError(ITEM_FIXED_OUT, ITEM, slip);

			} else if (isTermOver(slipDate, item.getDateFrom(), item.getDateTo())) {
				addError(TERM_OUT, ITEM, slip);
			}

			// 補助科目
			if (item != null && !Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {
				Item sub = hdr.getItem().getSubItem();

				if (sub == null) {
					addError(NOT_FOUND, SUB_ITEM, slip);

				} else if (isTermOver(slipDate, sub.getDateFrom(), sub.getDateTo())) {
					addError(TERM_OUT, SUB_ITEM, slip);
				}

				// 内訳科目
				if (sub != null && !Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {
					Item detail = hdr.getItem().getDetailItem();

					if (detail == null) {
						addError(NOT_FOUND, DETAIL_ITEM, slip);

					} else if (isTermOver(slipDate, detail.getDateFrom(), detail.getDateTo())) {
						addError(TERM_OUT, DETAIL_ITEM, slip);
					}
				}
			}
		}

		// 社員
		if (!Util.isNullOrEmpty(hdr.getSWK_EMP_CODE())) {
			Employee employee = hdr.getEmployee();

			if (employee == null) {
				addError(NOT_FOUND, EMPLOYEE, slip);

			} else if (isTermOver(slipDate, employee.getDateFrom(), employee.getDateTo())) {
				addError(TERM_OUT, EMPLOYEE, slip);
			}
		}

		// 取引先
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE())) {
			Customer customer = hdr.getCustomer();

			if (customer == null) {
				addError(NOT_FOUND, CUSTOMER, slip);

			} else if (isTermOver(slipDate, customer.getDateFrom(), customer.getDateTo())) {
				addError(TERM_OUT, CUSTOMER, slip);
			}
		}

		// 取引先支払条件
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_TJK_CODE())) {
			PaymentSetting setting = hdr.getPaymentSetting();

			if (setting == null) {
				addError(NOT_FOUND, PAY_SETTING, slip);

			} else if (isTermOver(slipDate, setting.getDateFrom(), setting.getDateTo())) {
				addError(TERM_OUT, PAY_SETTING, slip);
			}
		}

		// 支払方法
		if (!Util.isNullOrEmpty(hdr.getSWK_HOH_CODE())) {
			PaymentMethod method = hdr.getPaymentMethod();

			if (method == null) {
				addError(NOT_FOUND, PAY_METHOD, slip);

			} else if (isTermOver(slipDate, method.getDateFrom(), method.getDateTo())) {
				addError(TERM_OUT, PAY_METHOD, slip);
			}
		}

		// 銀行口座
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE())) {
			BankAccount bank = hdr.getBankAccount();

			if (bank == null) {
				addError(NOT_FOUND, BANK_ACCOUNT, slip);

			} else if (isTermOver(slipDate, bank.getDateFrom(), bank.getDateTo())) {
				addError(TERM_OUT, BANK_ACCOUNT, slip);
			}
		}

		// 請求区分
		if (!Util.isNullOrEmpty(hdr.getSWK_SEI_KBN())) {
			BillType billType = hdr.getBillType();

			if (billType == null) {
				addError(NOT_FOUND, BILL_TYPE, slip);

			} else if (isTermOver(slipDate, billType.getDateFrom(), billType.getDateTo())) {
				addError(TERM_OUT, BILL_TYPE, slip);
			}
		}

		// 通貨
		if (!Util.isNullOrEmpty(hdr.getSWK_CUR_CODE())) {
			Currency currency = hdr.getCurrency();

			if (currency == null) {
				addError(NOT_FOUND, CURRENCY, slip);

			} else if (isTermOver(slipDate, currency.getDateFrom(), currency.getDateTo())) {
				addError(TERM_OUT, CURRENCY, slip);
			}
		}

		// 摘要
		if (!Util.isNullOrEmpty(hdr.getSWK_TEK_CODE())) {
			Remark remark = hdr.getRemark();

			if (remark == null) {
				addError(NOT_FOUND, REMARKS, slip);

			} else if (isTermOver(slipDate, remark.getDateFrom(), remark.getDateTo())) {
				addError(TERM_OUT, REMARKS, slip);
			}
		}
	}

	/**
	 * 有効期限切れかどうか
	 * 
	 * @param date 基準日
	 * @param from 開始
	 * @param to 終了
	 * @return true:期間切れ
	 */
	protected boolean isTermOver(Date date, Date from, Date to) {
		return (from != null && !Util.isSmallerThenByYMD(from, date))
			|| (to != null && !Util.isSmallerThenByYMD(date, to));
	}

	/**
	 * 科目関連チェック
	 * 
	 * @param slip 伝票
	 */
	protected void checkItem(Slip slip) {

		SWK_HDR hdr = slip.getHeader();

		// 基軸通貨
		String keyCurrency = slip.getBaseCurrency().getCode();

		Item item = hdr.getItem();
		if (item == null) {
			return;
		}

		// 補助
		if (item.hasSubItem() && Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {
			addError(NULL_ON_ITEM, SUB_ITEM, slip);
		}

		// 内訳
		if (item.getSubItem() != null && item.getSubItem().hasDetailItem() && Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {
			addError(NULL_ON_ITEM, DETAIL_ITEM, slip);
		}

		item = item.getPreferedItem();

		if (item == null) {
			return;
		}

		// 多通貨:入力不可 AND 通貨コード <> 基軸通貨
		if (!Util.isNullOrEmpty(hdr.getSWK_CUR_CODE())) {
			if (!item.isUseForeignCurrency() && !keyCurrency.equals(hdr.getSWK_CUR_CODE())) {
				addError(NOT_KEY_CURRENCY, CURRENCY, slip);
			}
		}
	}

	/**
	 * エラー情報を設定する
	 * 
	 * @param errorType エラー種類
	 * @param dataType エラーデータタイプ
	 * @param slip 対象伝票
	 */
	protected void addError(ErrorType errorType, DataType dataType, Slip slip) {
		SlipHeaderError error = new SlipHeaderError();
		error.setErrorType(errorType);
		error.setDataType(dataType);
		error.setHeader(slip.getHeader());
		if (slip.getCompany() != null) {
			error.setConfig(slip.getCompany().getAccountConfig());
		}

		errorList.add(error);
	}

	/**
	 * エラーリスト
	 * 
	 * @return errorList エラーリスト
	 */
	public List<SlipHeaderError> getErrorList() {
		return errorList;
	}
}
