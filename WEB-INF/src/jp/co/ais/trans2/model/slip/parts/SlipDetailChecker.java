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
 * �d�󖾍׃`�F�b�N�N���X<BR>
 * �Â��o�[�W�����A����g���ꏊ�Ȃ�
 */
public class SlipDetailChecker extends TModel {

	/** �����ێ��p�Z�p���[�^ */
	protected static final String KEY_SEP = "<>";

	/** true:IFRS�敪��蔭�����͉Ȗڂ̔������t���O�ɂ�萧��@�\�L����Server�� */
	public static boolean allowOccurDateBlank = ServerConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** �G���[��� */
	protected List<SlipDetailError> errorList = new LinkedList<SlipDetailError>();

	/**
	 * �`�[���ׂ̗L������/�������`�F�b�N������
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void check(Slip slip) throws TException {
		List<Slip> list = new ArrayList<Slip>(1);
		list.add(slip);
		this.check(list);
	}

	/**
	 * �`�[���ׂ̗L������/�������`�F�b�N������
	 * 
	 * @param slipList �`�[���X�g
	 * @throws TException
	 */
	public void check(List<Slip> slipList) throws TException {
		errorList.clear();

		// �G���e�B�e�B�ݒ�
		setupEntity(slipList);

		for (Slip slip : slipList) {

			// �v���̓`�[���t�`�F�b�N
			checkSlipDate(slip);

			// �K�{�`�F�b�N
			checkNull(slip);

			// �f�[�^����/�L�������`�F�b�N
			checkTerm(slip);

			// �Ȗڃ`�F�b�N
			checkItem(slip);
		}
	}

	/**
	 * �`�[���׃G���e�B�e�B���\�z
	 * 
	 * @param slipList �`�[���X�g
	 * @throws TException
	 */
	protected void setupEntity(List<Slip> slipList) throws TException {

		CompanyManager compMng = (CompanyManager) getComponent(CompanyManager.class); // ���
		DepartmentManager deptMng = (DepartmentManager) getComponent(DepartmentManager.class);// ����
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class); // �Ȗ�
		RemarkManager remarksMng = (RemarkManager) getComponent(RemarkManager.class); // �E�v
		ConsumptionTaxManager taxMng = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);// �����
		CurrencyManager curMng = (CurrencyManager) getComponent(CurrencyManager.class);// �ʉ�
		EmployeeManager empMng = (EmployeeManager) getComponent(EmployeeManager.class); // �Ј�
		CustomerManager triMng = (CustomerManager) getComponent(CustomerManager.class); // �����
		Management1Manager knr1Mng = (Management1Manager) getComponent(Management1Manager.class); // �Ǘ�1
		Management2Manager knr2Mng = (Management2Manager) getComponent(Management2Manager.class); // �Ǘ�2
		Management3Manager knr3Mng = (Management3Manager) getComponent(Management3Manager.class); // �Ǘ�3
		Management4Manager knr4Mng = (Management4Manager) getComponent(Management4Manager.class); // �Ǘ�4
		Management5Manager knr5Mng = (Management5Manager) getComponent(Management5Manager.class); // �Ǘ�5
		Management6Manager knr6Mng = (Management6Manager) getComponent(Management6Manager.class); // �Ǘ�6

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

			// ����
			for (SWK_DTL dtl : slip.getDetails()) {

				// �v����
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

				// �v�㕔��
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

				// �Ȗ�
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

				// �ʉ�
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

				// �����
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

				// �E�v
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

				// �Ј�
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

				// �����
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

				// �Ǘ�1
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

				// �Ǘ�2
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

				// �Ǘ�3
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

				// �Ǘ�4
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

				// �Ǘ�5
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

				// �Ǘ�6
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
	 * �`�[���t(���ߓ�)�A���Z�i�K�̍ă`�F�b�N���s��
	 * 
	 * @param slip �`�[�f�[�^
	 */
	protected void checkSlipDate(Slip slip) {

		Company keyCompany = slip.getCompany();
		Date slipDate = slip.getSlipDate();
		int settlemtntStage = slip.getSettlementStage();

		int count = 0;// �s���J�E���g

		// �v���̐�s�A���߃`�F�b�N
		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			Company kcompany = dtl.getAppropriateCompany();
			if (keyCompany.getCode().equals(kcompany.getCode())) {
				continue;
			}

			// ���߃`�F�b�N
			if (kcompany.getFiscalPeriod().isClosed(slipDate, settlemtntStage)) {
				addError(CLOSED_SLIP_DATE, SLIP_DATE, count, dtl);
			}
		}
	}

	/**
	 * �K�{�`�F�b�N
	 * 
	 * @param slip �`�[
	 */
	protected void checkNull(Slip slip) {

		int count = 0;// �s���J�E���g

		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue; // �����d��
			}

			if (isSkip(dtl)) {
				continue; // ���E���c�������s
			}

			// �v����
			if (Util.isNullOrEmpty(dtl.getSWK_K_KAI_CODE())) {
				addError(NULL, TRANSFER_COMPANY, count, dtl);
			}

			// �v�㕔��
			if (Util.isNullOrEmpty(dtl.getSWK_DEP_CODE())) {
				addError(NULL, DEPARTMENT, count, dtl);
			}

			// �Ȗ�
			if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				addError(NULL, ITEM, count, dtl);
			}

			// �ʉ�
			if (Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
				addError(NULL, CURRENCY, count, dtl);
			}

			// �M�݋��z���u�����N�A�܂���0
			if (DecimalUtil.isNullOrZero(dtl.getSWK_KIN())) {
				addError(NULL, KEY_AMOUNT, count, dtl);
			}

			// �������Ȃ�
			if (!isAllowOccurDateBlank() && dtl.getHAS_DATE() == null) {
				addError(NULL, ACCRUAL_DATE, count, dtl);
			}
		}
	}

	/**
	 * �d�󖾍ׂ̑��݁A�L���������`�F�b�N����
	 * 
	 * @param slip �`�[
	 */
	protected void checkTerm(Slip slip) {

		int count = 0;// �s���J�E���g

		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue; // �����d��
			}

			if (isSkip(dtl)) {
				continue; // ���E���c�������s
			}

			// �`�[���t
			Date slipDate = dtl.getSWK_DEN_DATE();

			// ������
			Date occurDate = dtl.getHAS_DATE();
			
			if (occurDate == null) {
				occurDate = slipDate;
			}

			// �v����
			Company company = dtl.getAppropriateCompany();
			if (company == null) {
				// ���݃`�F�b�N
				addError(NOT_FOUND, TRANSFER_COMPANY, count, dtl);

			} else if (isTermOver(slipDate, company.getDateFrom(), company.getDateTo())) {
				// �L�������`�F�b�N
				addError(TERM_OUT, TRANSFER_COMPANY, count, dtl);
			}

			// ����
			Department department = dtl.getDepartment();
			if (department == null) {
				addError(NOT_FOUND, DEPARTMENT, count, dtl);

			} else if (isTermOver(slipDate, department.getDateFrom(), department.getDateTo())) {
				addError(TERM_OUT, DEPARTMENT, count, dtl);
			}

			// �Ȗ�
			if (!Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				Item item = dtl.getItem();

				if (item == null) {
					addError(NOT_FOUND, ITEM, count, dtl);

				} else if (!Util.isNullOrEmpty(item.getFixedDepartmentCode())
					&& !item.getFixedDepartmentCode().equals(dtl.getSWK_DEP_CODE())) {
					// �Œ蕔��`�F�b�N
					addError(ITEM_FIXED_OUT, ITEM, count, dtl);

				} else if (isTermOver(slipDate, item.getDateFrom(), item.getDateTo())) {
					addError(TERM_OUT, ITEM, count, dtl);
				}

				// �⏕�Ȗ�
				if (item != null && !Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					Item sub = dtl.getItem().getSubItem();

					if (sub == null) {
						addError(NOT_FOUND, SUB_ITEM, count, dtl);

					} else if (isTermOver(slipDate, sub.getDateFrom(), sub.getDateTo())) {
						addError(TERM_OUT, SUB_ITEM, count, dtl);
					}

					// ����Ȗ�
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

			// �ʉ�
			if (!Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
				Currency currency = dtl.getCurrency();

				if (currency == null) {
					addError(NOT_FOUND, CURRENCY, count, dtl);

				} else if (isTermOver(slipDate, currency.getDateFrom(), currency.getDateTo())) {
					addError(TERM_OUT, CURRENCY, count, dtl);
				}
			}

			// �E�v
			if (!Util.isNullOrEmpty(dtl.getSWK_GYO_TEK_CODE())) {
				Remark remark = dtl.getRemark();

				if (remark == null) {
					addError(NOT_FOUND, REMARKS, count, dtl);

				} else if (isTermOver(slipDate, remark.getDateFrom(), remark.getDateTo())) {
					addError(TERM_OUT, REMARKS, count, dtl);
				}
			}

			// �����
			if (!Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				ConsumptionTax tax = dtl.getTax();

				if (tax == null) {
					addError(NOT_FOUND, CONSUMPTION_TAX, count, dtl);

				} else if (isTermOver(occurDate, tax.getDateFrom(), tax.getDateTo())) {
					addError(TERM_OUT, CONSUMPTION_TAX, count, dtl);
				}
			}

			// �Ј�
			if (!Util.isNullOrEmpty(dtl.getSWK_EMP_CODE())) {
				Employee employee = dtl.getEmployee();

				if (employee == null) {
					addError(NOT_FOUND, EMPLOYEE, count, dtl);

				} else if (isTermOver(slipDate, employee.getDateFrom(), employee.getDateTo())) {
					addError(TERM_OUT, EMPLOYEE, count, dtl);
				}
			}

			// �����
			if (!Util.isNullOrEmpty(dtl.getSWK_TRI_CODE())) {
				Customer customer = dtl.getCustomer();

				if (customer == null) {
					addError(NOT_FOUND, CUSTOMER, count, dtl);

				} else if (isTermOver(slipDate, customer.getDateFrom(), customer.getDateTo())) {
					addError(TERM_OUT, CUSTOMER, count, dtl);
				}
			}

			// �Ǘ�1
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_1())) {
				Management1 mng1 = dtl.getManagement1();

				if (mng1 == null) {
					addError(NOT_FOUND, MANAGE1, count, dtl);

				} else if (isTermOver(slipDate, mng1.getDateFrom(), mng1.getDateTo())) {
					addError(TERM_OUT, MANAGE1, count, dtl);
				}
			}

			// �Ǘ�2
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_2())) {
				Management2 mng2 = dtl.getManagement2();

				if (mng2 == null) {
					addError(NOT_FOUND, MANAGE2, count, dtl);

				} else if (isTermOver(slipDate, mng2.getDateFrom(), mng2.getDateTo())) {
					addError(TERM_OUT, MANAGE2, count, dtl);
				}
			}

			// �Ǘ�3
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_3())) {
				Management3 mng3 = dtl.getManagement3();

				if (mng3 == null) {
					addError(NOT_FOUND, MANAGE3, count, dtl);

				} else if (isTermOver(slipDate, mng3.getDateFrom(), mng3.getDateTo())) {
					addError(TERM_OUT, MANAGE3, count, dtl);
				}
			}

			// �Ǘ�4
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_4())) {
				Management4 mng4 = dtl.getManagement4();

				if (mng4 == null) {
					addError(NOT_FOUND, MANAGE4, count, dtl);

				} else if (isTermOver(slipDate, mng4.getDateFrom(), mng4.getDateTo())) {
					addError(TERM_OUT, MANAGE4, count, dtl);
				}
			}

			// �Ǘ�5
			if (!Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_5())) {
				Management5 mng5 = dtl.getManagement5();

				if (mng5 == null) {
					addError(NOT_FOUND, MANAGE5, count, dtl);

				} else if (isTermOver(slipDate, mng5.getDateFrom(), mng5.getDateTo())) {
					addError(TERM_OUT, MANAGE5, count, dtl);
				}
			}

			// �Ǘ�6
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
	 * �L�������؂ꂩ�ǂ���
	 * 
	 * @param date ���
	 * @param from �J�n
	 * @param to �I��
	 * @return true:���Ԑ؂�
	 */
	protected boolean isTermOver(Date date, Date from, Date to) {
		return (from != null && !Util.isSmallerThenByYMD(from, date))
			|| (to != null && !Util.isSmallerThenByYMD(date, to));
	}

	/**
	 * �Ȗڊ֘A�`�F�b�N
	 * 
	 * @param slip �`�[
	 */
	protected void checkItem(Slip slip) {

		int count = 0;// �s���J�E���g

		for (SWK_DTL dtl : slip.getDetails()) {
			count++;

			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue; // �����d��
			}

			if (isSkip(dtl)) {
				continue; // ���E���c�������s
			}

			Item item = dtl.getItem();
			if (item == null) {
				return;
			}

			// �⏕
			if (item.hasSubItem() && Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
				addError(NULL_ON_ITEM, SUB_ITEM, count, dtl);
			}

			// ����
			if (item.getSubItem() != null && item.getSubItem().hasDetailItem()
				&& Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
				addError(NULL_ON_ITEM, DETAIL_ITEM, count, dtl);
			}

			item = item.getPreferedItem();

			if (item == null) {
				return;
			}

			String keyCurrency = dtl.getAppropriateCompany().getAccountConfig().getKeyCurrency().getCode();

			// ���ʉ�:���͕s�� AND �ʉ݃R�[�h <> ��ʉ�
			if (!Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
				if (!item.isUseForeignCurrency() && !keyCurrency.equals(dtl.getSWK_CUR_CODE())) {
					addError(NOT_KEY_CURRENCY, CURRENCY, count, dtl);
				}
			}

			// ����ې�:���͉� OR �d���ې�:���͉� AND ����ŃR�[�h���u�����N
			if ((item.isUseSalesTaxation() || item.isUsePurchaseTaxation())
				&& Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				addError(NULL_ON_ITEM, CONSUMPTION_TAX, count, dtl);
			}

			// �����
			if (item.isUseCustomer() && Util.isNullOrEmpty(dtl.getSWK_TRI_CODE())) {
				addError(NULL_ON_ITEM, CUSTOMER, count, dtl);
			}

			// �Ј�
			if (item.isUseEmployee() && Util.isNullOrEmpty(dtl.getSWK_EMP_CODE())) {
				addError(NULL_ON_ITEM, EMPLOYEE, count, dtl);
			}

			// �Ǘ�1
			if (item.isUseManagement1() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_1())) {
				addError(NULL_ON_ITEM, MANAGE1, count, dtl);
			}

			// �Ǘ�2
			if (item.isUseManagement2() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_2())) {
				addError(NULL_ON_ITEM, MANAGE2, count, dtl);
			}

			// �Ǘ�3
			if (item.isUseManagement3() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_3())) {
				addError(NULL_ON_ITEM, MANAGE3, count, dtl);
			}

			// �Ǘ�4
			if (item.isUseManagement4() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_4())) {
				addError(NULL_ON_ITEM, MANAGE4, count, dtl);
			}

			// �Ǘ�5
			if (item.isUseManagement5() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_5())) {
				addError(NULL_ON_ITEM, MANAGE5, count, dtl);
			}

			// �Ǘ�6
			if (item.isUseManagement6() && Util.isNullOrEmpty(dtl.getSWK_KNR_CODE_6())) {
				addError(NULL_ON_ITEM, MANAGE6, count, dtl);
			}
		}
	}

	/**
	 * �G���[����ݒ肷��
	 * 
	 * @param errorType �G���[���
	 * @param dataType �G���[�f�[�^�^�C�v
	 */
	protected void addError(ErrorType errorType, DataType dataType) {
		addError(errorType, dataType, 0, null);
	}

	/**
	 * �G���[����ݒ肷��
	 * 
	 * @param errorType �G���[���
	 * @param dataType �G���[�f�[�^�^�C�v
	 * @param rowNo �s�ԍ�
	 * @param dtl �Ώێd��
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
	 * �G���[���X�g
	 * 
	 * @return errorList �G���[���X�g
	 */
	public List<SlipDetailError> getErrorList() {
		return errorList;
	}

	/**
	 * �ΏۊO�d�󂩂ǂ���
	 * 
	 * @param dtl
	 * @return �ΏۊO�d��
	 */
	protected boolean isSkip(SWK_DTL dtl) {
		return dtl.isErasing();
	}

	/**
	 * @return true:�������u�����N�\
	 */
	protected boolean isAllowOccurDateBlank() {
		Company company = getCompany();
		return allowOccurDateBlank && !company.getAccountConfig().isUseIfrs();
	}
}
