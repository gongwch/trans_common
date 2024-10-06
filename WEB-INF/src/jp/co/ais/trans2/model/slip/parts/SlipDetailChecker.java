package jp.co.ais.trans2.model.slip.parts;

import static jp.co.ais.trans2.model.slip.parts.SlipDetailError.DataType.*;
import static jp.co.ais.trans2.model.slip.parts.SlipDetailError.ErrorType.*;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.parts.SlipDetailError.DataType;
import jp.co.ais.trans2.model.slip.parts.SlipDetailError.ErrorType;
import jp.co.ais.trans2.model.tax.*;

/**
 * 仕訳明細チェッククラス<BR>
 * 古いバージョン、現状使う場所なし
 */
public class SlipDetailChecker extends TModel {

	/** 内部保持用セパレータ */
	protected static final String KEY_SEP = "<>";

	/** true:IFRS区分より発生日は科目の発生日フラグにより制御機能有効＜Server＞ */
	public static boolean allowOccurDateBlank = ServerConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** エラー情報 */
	protected List<SlipDetailError> errorList = new LinkedList<SlipDetailError>();

	/**
	 * 伝票明細の有効期限/整合性チェックをする
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
	 * 伝票明細の有効期限/整合性チェックをする
	 * 
	 * @param slipList 伝票リスト
	 * @throws TException
	 */
	public void check(List<Slip> slipList) throws TException {
		errorList.clear();

		// エンティティ設定
		setupEntity(slipList);

		for (Slip slip : slipList) {

			// 計上先の伝票日付チェック
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
	 * 伝票明細エンティティを構築
	 * 
	 * @param slipList 伝票リスト
	 * @throws TException
	 */
	protected void setupEntity(List<Slip> slipList) throws TException {

		CompanyManager compMng = (CompanyManager) getComponent(CompanyManager.class); // 会社
		DepartmentManager deptMng = (DepartmentManager) getComponent(DepartmentManager.class);// 部門
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class); // 科目
		RemarkManager remarksMng = (RemarkManager) getComponent(RemarkManager.class); // 摘要
		ConsumptionTaxManager taxMng = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);// 消費税
		CurrencyManager curMng = (CurrencyManager) getComponent(CurrencyManager.class);// 通貨
		EmployeeManager empMng = (EmployeeManager) getComponent(EmployeeManager.class); // 社員
		CustomerManager triMng = (CustomerManager) getComponent(CustomerManager.class); // 取引先
		Management1Manager knr1Mng = (Management1Manager) getComponent(Management1Manager.class); // 管理1
		Management2Manager knr2Mng = (Management2Manager) getComponent(Management2Manager.class); // 管理2
		Management3Manager knr3Mng = (Management3Manager) getComponent(Management3Manager.class); // 管理3
		Management4Manager knr4Mng = (Management4Manager) getComponent(Management4Manager.class); // 管理4
		Management5Manager knr5Mng = (Management5Manager) getComponent(Management5Manager.class); // 管理5
		Management6Manager knr6Mng = (Management6Manager) getComponent(Management6Manager.class); // 管理6

		CompanySearchCondition compCon = new CompanySearchCondition();
		DepartmentSearchCondition deptCon = new DepartmentSearchCondition();
		RemarkSearchCondition remCon = new RemarkSearchCondition();
		ConsumptionTaxSearchCondition taxCon = new ConsumptionTaxSearchCondition();
		CurrencySearchCondition curCon = new CurrencySearchCondition();
		EmployeeSearchCondition empCon = new EmployeeSearchCondition();
		CustomerSearchCondition triCon = new CustomerSearchCondition();
		Management1SearchCondition knr1Con = new Management1SearchCondition();
		Management2SearchCondition knr2Con = new Management2SearchCondition();
		Management3SearchCondition knr3Con = new Management3SearchCondition();
		Management4SearchCondition knr4Con = new Management4SearchCondition();
		Management5SearchCondition knr5Con = new Management5SearchCondition();
		Management6SearchCondition knr6Con = new Management6SearchCondition();

		Map<String, Company> compMap = new TreeMap<String, Company>();
		Map<String, Department> deptMap = new TreeMap<String, Department>();
		Map<String, Item> itemMap = new TreeMap<String, Item>();
		Map<String, Remark> remarksMap = new TreeMap<String, Remark>();
		Map<String, ConsumptionTax> taxMap = new TreeMap<String, ConsumptionTax>();
		Map<String, Currency> curMap = new TreeMap<String, Currency>();
		Map<String, Employee> empMap = new TreeMap<String, Employee>();
		Map<String, Customer> triMap = new TreeMap<String, Customer>();
		Map<String, Management1> knr1Map = new TreeMap<String, Management1>();
		Map<String, Management2> knr2Map = new TreeMap<String, Management2>();
		Map<String, Management3> knr3Map = new TreeMap<String, Management3>();
		Map<String, Management4> knr4Map = new TreeMap<String, Management4>();
		Map<String, Management5> knr5Map = new TreeMap<String, Management5>();
		Map<String, Management6> knr6Map = new TreeMap<String, Management6>();

		for (Slip slip : slipList) {

			// 明細
			for (SWK_DTL dtl : slip.getDetails()) {

				// 計上会社
				String kCompany = dtl.getSWK_K_KAI_CODE();

				Company company = compMap.get(kCompany);
				if (company == null) {
					compCon.setCode(kCompany);
					List<Company> companys = compMng.get(compCon);

					if (!companys.isEmpty()) {
						company = companys.get(0);
						compMap.put(kCompany, company);
					}
				}
				dtl.setAppropriateCompany(company);
				dtl.setSWK_K_KAI_CODE(kCompany);

				// 計上部門
				String dept = dtl.getSWK_DEP_CODE();

				Department department = deptMap.get(kCompany + KEY_SEP + dept);
				if (department == null) {
					deptCon.setCompanyCode(kCompany);
					deptCon.setCode(dept);
					List<Department> depts = deptMng.get(deptCon);
					if (!depts.isEmpty()) {
						department = depts.get(0);
						deptMap.put(kCompany + KEY_SEP + dept, department);
					}
				}
				dtl.setDepartment(department);
				dtl.setSWK_DEP_CODE(dept);

				// 科目
				String kmk = dtl.getSWK_KMK_CODE();
				String hkm = dtl.getSWK_HKM_CODE();
				String ukm = dtl.getSWK_UKM_CODE();

				Item item = itemMap.get(kCompany + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm);
				if (item == null) {
					item = itemMng.getItem(kCompany, kmk, hkm, ukm);
					itemMap.put(kCompany + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm, item);
				}
				dtl.setItem(item);
				dtl.setSWK_KMK_CODE(kmk);
				dtl.setSWK_HKM_CODE(hkm);
				dtl.setSWK_UKM_CODE(ukm);

				// 通貨
				String cur = dtl.getSWK_CUR_CODE();

				Currency currency = curMap.get(kCompany + KEY_SEP + cur);
				if (currency == null) {
					curCon.setCompanyCode(kCompany);
					curCon.setCode(cur);
					List<Currency> curs = curMng.get(curCon);
					if (!curs.isEmpty()) {
						currency = curs.get(0);
						curMap.put(kCompany + KEY_SEP + cur, currency);
					}
				}
				dtl.setCurrency(currency);
				dtl.setSWK_CUR_CODE(cur);

				// 消費税
				String zei = dtl.getSWK_ZEI_CODE();

				if (!Util.isNullOrEmpty(zei)) {
					ConsumptionTax tax = taxMap.get(kCompany + KEY_SEP + zei);
					if (tax == null) {
						taxCon.setCompanyCode(kCompany);
						taxCon.setCode(zei);
						List<ConsumptionTax> taxs = taxMng.get(taxCon);
						if (!taxs.isEmpty()) {
							tax = taxs.get(0);
							taxMap.put(kCompany + KEY_SEP + zei, tax);
						}
					}
					dtl.setTax(tax);
					dtl.setSWK_ZEI_CODE(zei);
				}

				// 摘要
				String tek = dtl.getSWK_GYO_TEK_CODE();
				if (!Util.isNullOrEmpty(tek)) {

					Remark remark = remarksMap.get(kCompany + KEY_SEP + tek);
					if (remark == null) {
						remCon.setCompanyCode(kCompany);
						remCon.setCode(tek);

						List<Remark> rems = remarksMng.get(remCon);
						if (!rems.isEmpty()) {
							remark = rems.get(0);
							remarksMap.put(kCompany + KEY_SEP + tek, remark);
						}
					}
					dtl.setRemark(remark);
					dtl.setSWK_GYO_TEK_CODE(tek);
				}

				// 社員
				String emp = dtl.getSWK_EMP_CODE();
				if (!Util.isNullOrEmpty(emp)) {
					Employee employee = empMap.get(kCompany + KEY_SEP + emp);
					if (employee == null) {
						empCon.setCompanyCode(kCompany);
						empCon.setCode(emp);

						List<Employee> emps = empMng.get(empCon);
						if (!emps.isEmpty()) {
							employee = emps.get(0);
							empMap.put(kCompany + KEY_SEP + emp, employee);
						}
					}
					dtl.setEmployee(employee);
					dtl.setSWK_EMP_CODE(emp);
				}

				// 取引先
				String tri = dtl.getSWK_TRI_CODE();
				if (!Util.isNullOrEmpty(tri)) {
					Customer customer = triMap.get(kCompany + KEY_SEP + tri);
					if (customer == null) {
						triCon.setCompanyCode(kCompany);
						triCon.setCode(tri);

						List<Customer> tris = triMng.get(triCon);
						if (!tris.isEmpty()) {
							customer = tris.get(0);
							triMap.put(kCompany + KEY_SEP + tri, customer);
						}
					}
					dtl.setCustomer(customer);
					dtl.setSWK_TRI_CODE(tri);
				}

				// 管理1
				String knr1 = dtl.getSWK_KNR_CODE_1();
				if (!Util.isNullOrEmpty(knr1)) {
					Management1 mng1 = knr1Map.get(kCompany + KEY_SEP + knr1);
					if (mng1 == null) {
						knr1Con.setCompanyCode(kCompany);
						knr1Con.setCode(knr1);

						List<Management1> knr1s = knr1Mng.get(knr1Con);
						if (!knr1s.isEmpty()) {
							mng1 = knr1s.get(0);
							knr1Map.put(kCompany + KEY_SEP + knr1, mng1);
						}
					}
					dtl.setManagement1(mng1);
					dtl.setSWK_KNR_CODE_1(knr1);
				}

				// 管理2
				String knr2 = dtl.getSWK_KNR_CODE_2();
				if (!Util.isNullOrEmpty(knr2)) {
					Management2 mng2 = knr2Map.get(kCompany + KEY_SEP + knr2);
					if (mng2 == null) {
						knr2Con.setCompanyCode(kCompany);
						knr2Con.setCode(knr2);

						List<Management2> knr2s = knr2Mng.get(knr2Con);
						if (!knr2s.isEmpty()) {
							mng2 = knr2s.get(0);
							knr2Map.put(kCompany + KEY_SEP + knr2, mng2);
						}
					}
					dtl.setManagement2(mng2);
					dtl.setSWK_KNR_CODE_2(knr2);
				}

				// 管理3
				String knr3 = dtl.getSWK_KNR_CODE_3();
				if (!Util.isNullOrEmpty(knr3)) {
					Management3 mng3 = knr3Map.get(kCompany + KEY_SEP + knr3);
					if (mng3 == null) {
						knr3Con.setCompanyCode(kCompany);
						knr3Con.setCode(knr3);

						List<Management3> knr3s = knr3Mng.get(knr3Con);
						if (!knr3s.isEmpty()) {
							mng3 = knr3s.get(0);
							knr3Map.put(kCompany + KEY_SEP + knr3, mng3);
						}
					}
					dtl.setManagement3(mng3);
					dtl.setSWK_KNR_CODE_3(knr3);
				}

				// 管理4
				String knr4 = dtl.getSWK_KNR_CODE_4();
				if (!Util.isNullOrEmpty(knr4)) {
					Management4 mng4 = knr4Map.get(kCompany + KEY_SEP + knr4);
					if (mng4 == null) {
						knr4Con.setCompanyCode(kCompany);
						knr4Con.setCode(knr4);

						List<Management4> knr4s = knr4Mng.get(knr4Con);
						if (!knr4s.isEmpty()) {
							mng4 = knr4s.get(0);
							knr4Map.put(kCompany + KEY_SEP + knr4, mng4);
						}
					}
					dtl.setManagement4(mng4);
					dtl.setSWK_KNR_CODE_4(knr4);
				}

				// 管理5
				String knr5 = dtl.getSWK_KNR_CODE_5();
				if (!Util.isNullOrEmpty(knr5)) {
					Management5 mng5 = knr5Map.get(kCompany + KEY_SEP + knr5);
					if (mng5 == null) {
						knr5Con.setCompanyCode(kCompany);
						knr5Con.setCode(knr5);

						List<Management5> knr5s = knr5Mng.get(knr5Con);
						if (!knr5s.isEmpty()) {
							mng5 = knr5s.get(0);
							knr5Map.put(kCompany + KEY_SEP + knr5, mng5);
						}
					}
					dtl.setManagement5(mng5);
					dtl.setSWK_KNR_CODE_5(knr5);
				}

				// 管理6
				String knr6 = dtl.getSWK_KNR_CODE_6();
				if (!Util.isNullOrEmpty(knr6)) {
					Management6 mng6 = knr6Map.get(kCompany + KEY_SEP + knr6);
					if (mng6 == null) {
						knr6Con.setCompanyCode(kCompany);
						knr6Con.setCode(knr6);

						List<Management6> knr6s = knr6Mng.get(knr6Con);
						if (!knr6s.isEmpty()) {
							mng6 = knr6s.get(0);
							knr6Map.put(kCompany + KEY_SEP + knr6, mng6);
						}
					}
					dtl.setManagement6(mng6);
					dtl.setSWK_KNR_CODE_6(knr6);
				}
			}
		}
	}

	/**
	 * 伝票日付(締め日)、決算段階の再チェックを行う
	 * 
	 * @param slip 伝票データ
	 */
	protected void checkSlipDate(Slip slip) {

		Company keyCompany = slip.getCompany();
		Date slipDate = slip.getSlipDate();
		int settlemtntStage = slip.getSettlementStage();

		int count = 0;// 行数カウント

		// 計上先の先行、締めチェック
		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			Company kcompany = dtl.getAppropriateCompany();
			if (keyCompany.getCode().equals(kcompany.getCode())) {
				continue;
			}

			// 締めチェック
			if (kcompany.getFiscalPeriod().isClosed(slipDate, settlemtntStage)) {
				addError(CLOSED_SLIP_DATE, SLIP_DATE, count, dtl);
			}
		}
	}

	/**
	 * 必須チェック
	 * 
	 * @param slip 伝票
	 */
	protected void checkNull(Slip slip) {

		int count = 0;// 行数カウント

		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue; // 自動仕訳
			}

			if (isSkip(dtl)) {
				continue; // 債権・債務残高消込行
			}

			// 計上会社
			if (Util.isNullOrEmpty(dtl.getSWK_K_KAI_CODE())) {
				addError(NULL, TRANSFER_COMPANY, count, dtl);
			}

			// 計上部門
			if (Util.isNullOrEmpty(dtl.getSWK_DEP_CODE())) {
				addError(NULL, DEPARTMENT, count, dtl);
			}

			// 科目
			if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				addError(NULL, ITEM, count, dtl);
			}

			// 通貨
			if (Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
				addError(NULL, CURRENCY, count, dtl);
			}

			// 邦貨金額がブランク、または0
			if (DecimalUtil.isNullOrZero(dtl.getSWK_KIN())) {
				addError(NULL, KEY_AMOUNT, count, dtl);
			}

			// 発生日なし
			if (!isAllowOccurDateBlank() && dtl.getHAS_DATE() == null) {
				addError(NULL, ACCRUAL_DATE, count, dtl);
			}
		}
	}

	/**
	 * 仕訳明細の存在、有効期限をチェックする
	 * 
	 * @param slip 伝票
	 */
	protected void checkTerm(Slip slip) {

		int count = 0;// 行数カウント

		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue; // 自動仕訳
			}

			if (isSkip(dtl)) {
				continue; // 債権・債務残高消込行
			}

			// 伝票日付
			Date slipDate = dtl.getSWK_DEN_DATE();

			// 発生日
			Date occurDate = dtl.getHAS_DATE();
			
			if (occurDate == null) {
				occurDate = slipDate;
			}

			// 計上会社
			Company company = dtl.getAppropriateCompany();
			if (company == null) {
				// 存在チェック
				addError(NOT_FOUND, TRANSFER_COMPANY, count, dtl);

			} else if (isTermOver(slipDate, company.getDateFrom(), company.getDateTo())) {
				// 有効期限チェック
				addError(TERM_OUT, TRANSFER_COMPANY, count, dtl);
			}

			// 部門
			Department department = dtl.getDepartment();
			if (department == null) {
				addError(NOT_FOUND, DEPARTMENT, count, dtl);

			} else if (isTermOver(slipDate, department.getDateFrom(), department.getDateTo())) {
				addError(TERM_OUT, DEPARTMENT, count, dtl);
			}

			// 科目
			if (!Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				Item item = dtl.getItem();

				if (item == null) {
					addError(NOT_FOUND, ITEM, count, dtl);

				} else if (!Util.isNullOrEmpty(item.getFixedDepartmentCode())
					&& !item.getFixedDepartmentCode().equals(dtl.getSWK_DEP_CODE())) {
					// 固定部門チェック
					addError(ITEM_FIXED_OUT, ITEM, count, dtl);

				} else if (isTermOver(slipDate, item.getDateFrom(), item.getDateTo())) {
					addError(TERM_OUT, ITEM, count, dtl);
				}

				// 補助科目
				if (item != null && !Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					Item sub = dtl.getItem().getSubItem();

					if (sub == null) {
						addError(NOT_FOUND, SUB_ITEM, count, dtl);

					} else if (isTermOver(slipDate, sub.getDateFrom(), sub.getDateTo())) {
						addError(TERM_OUT, SUB_ITEM, count, dtl);
					}

					// 内訳科目
					if (sub != null && !Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
						Item detail = dtl.getItem().getDetailItem();

						if (detail == null) {
							addError(NOT_FOUND, DETAIL_ITEM, count, dtl);

						} else if (isTermOver(slipDate, detail.getDateFrom(), detail.getDateTo())) {
							addError(TERM_OUT, DETAIL_ITEM, count, dtl);
						}
					}
				}
			}

			// 通貨
			if (!Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
				Currency currency = dtl.getCurrency();

				if (currency == null) {
					addError(NOT_FOUND, CURRENCY, count, dtl);

				} else if (isTermOver(slipDate, currency.getDateFrom(), currency.getDateTo())) {
					addError(TERM_OUT, CURRENCY, count, dtl);
				}
			}

			// 摘要
			if (!Util.isNullOrEmpty(dtl.getSWK_GYO_TEK_CODE())) {
				Remark remark = dtl.getRemark();

				if (remark == null) {
					addError(NOT_FOUND, REMARKS, count, dtl);

				} else if (isTermOver(slipDate, remark.getDateFrom(), remark.getDateTo())) {
					addError(TERM_OUT, REMARKS, count, dtl);
				}
			}

			// 消費税
			if (!Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				ConsumptionTax tax = dtl.getTax();

				if (tax == null) {
					addError(NOT_FOUND, CONSUMPTION_TAX, count, dtl);

				} else if (isTermOver(occurDate, tax.getDateFrom(), tax.getDateTo())) {
					addError(TERM_OUT, CONSUMPTION_TAX, count, dtl);
				}
			}

			// 社員
			if (!Util.isNullOrEmpty(dtl.getSWK_EMP_CODE())) {
				Employee employee = dtl.getEmployee();

				if (employee == null) {
					addError(NOT_FOUND, EMPLOYEE, count, dtl);

				} else if (isTermOver(slipDate, employee.getDateFrom(), employee.getDateTo())) {
					addError(TERM_OUT, EMPLOYEE, count, dtl);
				}
			}

			// 取引先
			if (!Util.isNullOrEmpty(dtl.getSWK_TRI_CODE())) {
				Customer customer = dtl.getCustomer();

				if (customer == null) {
					addError(NOT_FOUND, CUSTOMER, count, dtl);

				} else if (isTermOver(slipDate, customer.getDateFrom(), customer.getDateTo())) {
					addError(TERM_OUT, CUSTOMER, count, dtl);
				}
			}

			// 管理1
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_1())) {
				Management1 mng1 = dtl.getManagement1();

				if (mng1 == null) {
					addError(NOT_FOUND, MANAGE1, count, dtl);

				} else if (isTermOver(slipDate, mng1.getDateFrom(), mng1.getDateTo())) {
					addError(TERM_OUT, MANAGE1, count, dtl);
				}
			}

			// 管理2
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_2())) {
				Management2 mng2 = dtl.getManagement2();

				if (mng2 == null) {
					addError(NOT_FOUND, MANAGE2, count, dtl);

				} else if (isTermOver(slipDate, mng2.getDateFrom(), mng2.getDateTo())) {
					addError(TERM_OUT, MANAGE2, count, dtl);
				}
			}

			// 管理3
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_3())) {
				Management3 mng3 = dtl.getManagement3();

				if (mng3 == null) {
					addError(NOT_FOUND, MANAGE3, count, dtl);

				} else if (isTermOver(slipDate, mng3.getDateFrom(), mng3.getDateTo())) {
					addError(TERM_OUT, MANAGE3, count, dtl);
				}
			}

			// 管理4
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_4())) {
				Management4 mng4 = dtl.getManagement4();

				if (mng4 == null) {
					addError(NOT_FOUND, MANAGE4, count, dtl);

				} else if (isTermOver(slipDate, mng4.getDateFrom(), mng4.getDateTo())) {
					addError(TERM_OUT, MANAGE4, count, dtl);
				}
			}

			// 管理5
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_5())) {
				Management5 mng5 = dtl.getManagement5();

				if (mng5 == null) {
					addError(NOT_FOUND, MANAGE5, count, dtl);

				} else if (isTermOver(slipDate, mng5.getDateFrom(), mng5.getDateTo())) {
					addError(TERM_OUT, MANAGE5, count, dtl);
				}
			}

			// 管理6
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_6())) {
				Management6 mng6 = dtl.getManagement6();

				if (mng6 == null) {
					addError(NOT_FOUND, MANAGE6, count, dtl);

				} else if (isTermOver(slipDate, mng6.getDateFrom(), mng6.getDateTo())) {
					addError(TERM_OUT, MANAGE6, count, dtl);
				}
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

		int count = 0;// 行数カウント

		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue; // 自動仕訳
			}

			if (isSkip(dtl)) {
				continue; // 債権・債務残高消込行
			}

			Item item = dtl.getItem();
			if (item == null) {
				return;
			}

			// 補助
			if (item.hasSubItem() && Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
				addError(NULL_ON_ITEM, SUB_ITEM, count, dtl);
			}

			// 内訳
			if (item.getSubItem() != null && item.getSubItem().hasDetailItem()
				&& Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
				addError(NULL_ON_ITEM, DETAIL_ITEM, count, dtl);
			}

			item = item.getPreferedItem();

			if (item == null) {
				return;
			}

			String keyCurrency = dtl.getAppropriateCompany().getAccountConfig().getKeyCurrency().getCode();

			// 多通貨:入力不可 AND 通貨コード <> 基軸通貨
			if (!Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
				if (!item.isUseForeignCurrency() && !keyCurrency.equals(dtl.getSWK_CUR_CODE())) {
					addError(NOT_KEY_CURRENCY, CURRENCY, count, dtl);
				}
			}

			// 売上課税:入力可 OR 仕入課税:入力可 AND 消費税コードがブランク
			if ((item.isUseSalesTaxation() || item.isUsePurchaseTaxation())
				&& Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				addError(NULL_ON_ITEM, CONSUMPTION_TAX, count, dtl);
			}

			// 取引先
			if (item.isUseCustomer() && Util.isNullOrEmpty(dtl.getSWK_TRI_CODE())) {
				addError(NULL_ON_ITEM, CUSTOMER, count, dtl);
			}

			// 社員
			if (item.isUseEmployee() && Util.isNullOrEmpty(dtl.getSWK_EMP_CODE())) {
				addError(NULL_ON_ITEM, EMPLOYEE, count, dtl);
			}

			// 管理1
			if (item.isUseManagement1() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_1())) {
				addError(NULL_ON_ITEM, MANAGE1, count, dtl);
			}

			// 管理2
			if (item.isUseManagement2() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_2())) {
				addError(NULL_ON_ITEM, MANAGE2, count, dtl);
			}

			// 管理3
			if (item.isUseManagement3() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_3())) {
				addError(NULL_ON_ITEM, MANAGE3, count, dtl);
			}

			// 管理4
			if (item.isUseManagement4() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_4())) {
				addError(NULL_ON_ITEM, MANAGE4, count, dtl);
			}

			// 管理5
			if (item.isUseManagement5() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_5())) {
				addError(NULL_ON_ITEM, MANAGE5, count, dtl);
			}

			// 管理6
			if (item.isUseManagement6() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_6())) {
				addError(NULL_ON_ITEM, MANAGE6, count, dtl);
			}
		}
	}

	/**
	 * エラー情報を設定する
	 * 
	 * @param errorType エラー種類
	 * @param dataType エラーデータタイプ
	 */
	protected void addError(ErrorType errorType, DataType dataType) {
		addError(errorType, dataType, 0, null);
	}

	/**
	 * エラー情報を設定する
	 * 
	 * @param errorType エラー種類
	 * @param dataType エラーデータタイプ
	 * @param rowNo 行番号
	 * @param dtl 対象仕訳
	 */
	protected void addError(ErrorType errorType, DataType dataType, int rowNo, SWK_DTL dtl) {
		SlipDetailError error = new SlipDetailError();
		error.setErrorType(errorType);
		error.setDataType(dataType);
		error.setRowNo(rowNo);
		error.setDetail(dtl);

		if (dtl.getAppropriateCompany() != null) {
			error.setAccountConfig(dtl.getAppropriateCompany().getAccountConfig());
		}

		errorList.add(error);
	}

	/**
	 * エラーリスト
	 * 
	 * @return errorList エラーリスト
	 */
	public List<SlipDetailError> getErrorList() {
		return errorList;
	}

	/**
	 * 対象外仕訳かどうか
	 * 
	 * @param dtl
	 * @return 対象外仕訳
	 */
	protected boolean isSkip(SWK_DTL dtl) {
		return dtl.isErasing();
	}

	/**
	 * @return true:発生日ブランク可能
	 */
	protected boolean isAllowOccurDateBlank() {
		Company company = getCompany();
		return allowOccurDateBlank && !company.getAccountConfig().isUseIfrs();
	}
}
