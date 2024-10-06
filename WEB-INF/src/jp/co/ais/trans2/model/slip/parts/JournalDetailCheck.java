package jp.co.ais.trans2.model.slip.parts;

import static jp.co.ais.trans2.define.SlipError.*;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
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
import jp.co.ais.trans2.model.tax.*;

/**
 * �d�󖾍׃`�F�b�N�N���X
 */
public class JournalDetailCheck extends TModel {

	/** true:IFRS�敪��蔭�����͉Ȗڂ̔������t���O�ɂ�萧��@�\�L����Server�� */
	public static boolean allowOccurDateBlank = ServerConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** ��Џ�� */
	protected Map<String, Company> companys = new TreeMap<String, Company>();

	/** ��ʉ� */
	protected String keyCurrency;

	/** �G���[��� */
	protected List<Message> errorList = new LinkedList<Message>();

	/** �`�F�b�N�Ώ� */
	protected List<SWK_DTL> detailList;

	/** header��� */
	protected SWK_HDR header;

	/**
	 * �d�󖾍ׂ̗L������/�������`�F�b�N������
	 * 
	 * @param slip �`�[
	 * @return errList �G���[���b�Z�[�W���X�g
	 * @throws TException
	 */
	public List<Message> checkDetail(Slip slip) throws TException {
		errorList.clear();
		companys.clear();

		header = slip.getHeader();
		detailList = slip.getDetails();

		// ���ׂ����݂��Ȃ��ꍇ�́A�����I��
		if (Util.isNullOrEmpty(detailList) || detailList.size() == 0) {
			return errorList;
		}

		// ��ʉ�
		keyCurrency = getCompany().getAccountConfig().getKeyCurrency().getCode();

		// ���ׂ̗L�������`�F�b�N
		checkTerm();

		// ���ׂ̐������`�F�b�N
		checkConsistency();

		return errorList;
	}

	/**
	 * �d�󖾍ׂ̗L���������`�F�b�N����
	 * 
	 * @throws TException
	 */
	protected void checkTerm() throws TException {

		int count = 0;// �s���J�E���g

		for (SWK_DTL dtl : detailList) {
			count++;

			// �����d��̏ꍇ�͎��̖��ׂ�
			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue;
			}

			// ���E���c�������s�̏ꍇ�͎��̖��ׂ�
			if (isSkip(dtl)) {
				continue;
			}

			Company company = getCompany(dtl.getSWK_K_KAI_CODE());
			// �f�[�^��������Ȃ�
			if (company == null) {
				// �G���[���̐ݒ�
				addError(NOT_FOUND, K_KAI_CODE, count, "I00065", count, "C00570", dtl.getSWK_K_KAI_CODE());
				continue;

			}
			AccountConfig conf = company.getAccountConfig();

			// �d��W���[�i��.�`�[���t
			Date slipDate = dtl.getSWK_DEN_DATE();

			// �d��W���[�i��.������
			Date occurDate = dtl.getHAS_DATE();
			if (occurDate == null) {
				occurDate = slipDate;
			}

			// �d��W���[�i��.�v���ЃR�[�h
			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			// �d��W���[�i��.����R�[�h
			String depCode = dtl.getSWK_DEP_CODE();

			// �d��W���[�i��.�ȖڃR�[�h
			String kmkCode = dtl.getSWK_KMK_CODE();

			// �d��W���[�i��.�⏕�ȖڃR�[�h
			String hkmCode = dtl.getSWK_HKM_CODE();

			// �d��W���[�i��.����ȖڃR�[�h
			String ukmCode = dtl.getSWK_UKM_CODE();

			// �d��W���[�i��.�ʉ݃R�[�h
			String curCode = dtl.getSWK_CUR_CODE();

			// �d��W���[�i��.�s�E�v�R�[�h
			String tekCode = dtl.getSWK_GYO_TEK_CODE();

			// �d��W���[�i��.�f�[�^�敪
			String dataKbn = dtl.getSWK_DATA_KBN();

			// �d��W���[�i��.����ŃR�[�h
			String zeiCode = dtl.getSWK_ZEI_CODE();

			// �d��W���[�i��.�Ј��R�[�h
			String empCode = dtl.getSWK_EMP_CODE();

			// �d��W���[�i��.�����R�[�h
			String triCode = dtl.getSWK_TRI_CODE();

			// �d��W���[�i��.�Ǘ�1�R�[�h
			String knrCode1 = dtl.getSWK_KNR_CODE_1();

			// �d��W���[�i��.�Ǘ�2�R�[�h
			String knrCode2 = dtl.getSWK_KNR_CODE_2();

			// �d��W���[�i��.�Ǘ�3�R�[�h
			String knrCode3 = dtl.getSWK_KNR_CODE_3();

			// �d��W���[�i��.�Ǘ�4�R�[�h
			String knrCode4 = dtl.getSWK_KNR_CODE_4();

			// �d��W���[�i��.�Ǘ�5�R�[�h
			String knrCode5 = dtl.getSWK_KNR_CODE_5();

			// �d��W���[�i��.�Ǘ�6�R�[�h
			String knrCode6 = dtl.getSWK_KNR_CODE_6();

			// ���ݒ�}�X�^
			if (!Util.isNullOrEmpty(kKaiCode)) {
				// �L�������`�F�b�N
				Date from = company.getDateFrom();
				Date to = company.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
					|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {
					// �G���[���̐ݒ�
					addError(TERM_OUT, K_KAI_CODE, count, "I00060", count, "C00570", kKaiCode);
				}
			}

			// ����}�X�^
			if (!Util.isNullOrEmpty(depCode)) {
				DepartmentManager dao = (DepartmentManager) getComponent(DepartmentManager.class);

				// ����}�X�^�����i���݃`�F�b�N�j
				DepartmentSearchCondition condition = new DepartmentSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(depCode);
				List<Department> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, DEP_CODE, count, "I00065", count, "C00571", depCode);

				} else {
					Department bean = list.get(0);

					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, DEP_CODE, count, "I00060", count, "C00571", depCode);
					}
				}
			}

			ItemManager itemMn = (ItemManager) getComponent(ItemManager.class);
			// �Ȗڃ}�X�^
			if (!Util.isNullOrEmpty(kmkCode)) {
				Item bean = itemMn.getItem(kKaiCode, kmkCode, null, null);

				// �f�[�^��������Ȃ�
				if (bean == null) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KMK_CODE, count, "I00079", count, conf.getItemName(), kmkCode);

				} else {

					if (!Util.isNullOrEmpty(bean.getFixedDepartmentCode())
						&& !bean.getFixedDepartmentCode().equals(depCode)) {

						// �G���[���̐ݒ�
						// ����R�[�h���Ȗ�.�Œ蕔��ɂ��Ă��������B
						addError(ITEM_FIXED_OUT, KMK_CODE, count, "I00584", count, conf.getItemName(), kmkCode);

					} else {
						// �L�������`�F�b�N
						Date from = bean.getDateFrom();
						Date to = bean.getDateTo();

						if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
							|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

							// �G���[���̐ݒ�
							addError(TERM_OUT, KMK_CODE, count, "I00080", count, conf.getItemName(), kmkCode);
						}
					}
				}
			}

			// �⏕�Ȗڃ}�X�^
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode)) {

				// �⏕�Ȗڃ}�X�^����
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, null);

				// �f�[�^��������Ȃ�
				if (bean == null || bean.getSubItem() == null) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, HKM_CODE, count, "I00079", count, conf.getSubItemName(), hkmCode);

				} else {
					// �L�������`�F�b�N
					Date from = bean.getSubItem().getDateFrom();
					Date to = bean.getSubItem().getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {
						// �G���[���̐ݒ�
						addError(TERM_OUT, HKM_CODE, count, "I00080", count, conf.getSubItemName(), hkmCode);
					}
				}
			}

			// ����Ȗڃ}�X�^
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode) && !Util.isNullOrEmpty(ukmCode)) {

				// ����Ȗڃ}�X�^����
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, ukmCode);

				// �f�[�^��������Ȃ�
				if (bean == null || bean.getSubItem() == null || bean.getSubItem().getDetailItem() == null) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, UKM_CODE, count, "I00079", count, conf.getDetailItemName(), ukmCode);

				} else {
					// �L�������`�F�b�N
					Date from = bean.getSubItem().getDetailItem().getDateFrom();
					Date to = bean.getSubItem().getDetailItem().getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, UKM_CODE, count, "I00080", count, conf.getDetailItemName(), ukmCode);
					}
				}
			}

			// �ʉ݃}�X�^
			if (!Util.isNullOrEmpty(curCode)) {
				// �ʉ݃}�X�^����
				CurrencyManager dao = (CurrencyManager) getComponent(CurrencyManager.class);
				CurrencySearchCondition condition = new CurrencySearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(curCode);
				List<Currency> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, CUR_CODE, count, "I00065", count, "C00665", curCode);

				} else {
					Currency bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, CUR_CODE, count, "I00060", count, "C00665", curCode);
					}
				}
			}

			// �E�v�}�X�^
			if (!Util.isNullOrEmpty(tekCode)) {
				// �E�v�}�X�^����
				RemarkManager dao = (RemarkManager) getComponent(RemarkManager.class);
				RemarkSearchCondition condition = new RemarkSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(tekCode);
				condition.setDataType(dataKbn);
				condition.setSlipRemark(false);
				List<Remark> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, GYO_TEK_CODE, count, "I00065", count, "C00564", tekCode);

				} else {
					Remark bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, GYO_TEK_CODE, count, "I00060", count, "C00564", tekCode);
					}
				}
			}

			// ����Ń}�X�^
			if (!Util.isNullOrEmpty(zeiCode)) {
				ConsumptionTaxManager dao = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);
				ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(zeiCode);
				// ����Ń}�X�^����
				List<ConsumptionTax> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, ZEI_CODE, count, "I00065", count, "C00573", zeiCode);

				} else {
					ConsumptionTax bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, occurDate))
						|| (to != null && !Util.isSmallerThenByYMD(occurDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, ZEI_CODE, count, "I00060", count, "C00573", zeiCode);
					}
				}
			}

			// �Ј��}�X�^
			if (!Util.isNullOrEmpty(empCode)) {
				// �Ј��}�X�^Dao
				EmployeeManager dao = (EmployeeManager) getComponent(EmployeeManager.class);

				// �Ј��}�X�^�f�[�^������
				EmployeeSearchCondition condition = new EmployeeSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(empCode);
				List<Employee> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, EMP_CODE, count, "I00065", count, "C00697", empCode);

				} else {
					Employee bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, EMP_CODE, count, "I00060", count, "C00697", empCode);
					}
				}
			}

			// �����}�X�^
			if (!Util.isNullOrEmpty(triCode)) {
				// �����}�X�^Dao
				CustomerManager dao = (CustomerManager) getComponent(CustomerManager.class);

				// �����}�X�^�f�[�^������
				CustomerSearchCondition condition = new CustomerSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(triCode);
				List<Customer> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, TRI_CODE, count, "I00065", count, "C00786", triCode);

				} else {
					Customer bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, TRI_CODE, count, "I00060", count, "C00786", triCode);
					}
				}
			}

			// �Ǘ�1�}�X�^
			if (!Util.isNullOrEmpty(knrCode1)) {
				Management1Manager dao = (Management1Manager) getComponent(Management1Manager.class);
				Management1SearchCondition condition = new Management1SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode1);

				// �Ǘ�1�}�X�^����
				List<Management1> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KNR_CODE_1, count, "I00079", count, conf.getManagement1Name(), knrCode1);

				} else {
					// �L�������`�F�b�N
					Management1 bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, KNR_CODE_1, count, "I00080", count, conf.getManagement1Name(), knrCode1);
					}
				}
			}

			// �Ǘ�2�}�X�^
			if (!Util.isNullOrEmpty(knrCode2)) {
				Management2Manager dao = (Management2Manager) getComponent(Management2Manager.class);
				Management2SearchCondition condition = new Management2SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode2);

				// �Ǘ�2�}�X�^����
				List<Management2> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KNR_CODE_2, count, "I00079", count, conf.getManagement2Name(), knrCode2);

				} else {
					Management2 bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, KNR_CODE_2, count, "I00080", count, conf.getManagement2Name(), knrCode2);

					}
				}
			}

			// �Ǘ�3�}�X�^
			if (!Util.isNullOrEmpty(knrCode3)) {
				Management3Manager dao = (Management3Manager) getComponent(Management3Manager.class);
				Management3SearchCondition condition = new Management3SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode3);

				// �Ǘ�3�}�X�^����
				List<Management3> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KNR_CODE_3, count, "I00079", count, conf.getManagement3Name(), knrCode3);

				} else {
					Management3 bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, KNR_CODE_3, count, "I00080", count, conf.getManagement3Name(), knrCode3);
					}
				}
			}

			// �Ǘ�4�}�X�^
			if (!Util.isNullOrEmpty(knrCode4)) {
				Management4Manager dao = (Management4Manager) getComponent(Management4Manager.class);
				Management4SearchCondition condition = new Management4SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode4);

				// �Ǘ�4�}�X�^����
				List<Management4> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KNR_CODE_4, count, "I00079", count, conf.getManagement4Name(), knrCode4);

				} else {
					Management4 bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, KNR_CODE_4, count, "I00080", count, conf.getManagement4Name(), knrCode4);

					}
				}
			}

			// �Ǘ�5�}�X�^
			if (!Util.isNullOrEmpty(knrCode5)) {
				Management5Manager dao = (Management5Manager) getComponent(Management5Manager.class);
				Management5SearchCondition condition = new Management5SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode5);

				// �Ǘ�5�}�X�^����
				List<Management5> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KNR_CODE_5, count, "I00079", count, conf.getManagement5Name(), knrCode5);

				} else {
					Management5 bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, KNR_CODE_5, count, "I00080", count, conf.getManagement5Name(), knrCode5);

					}
				}
			}

			// �Ǘ�6�}�X�^
			if (!Util.isNullOrEmpty(knrCode6)) {
				Management6Manager dao = (Management6Manager) getComponent(Management6Manager.class);
				Management6SearchCondition condition = new Management6SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode6);

				// �Ǘ�6�}�X�^����
				List<Management6> list = dao.get(condition);

				// �f�[�^��������Ȃ�
				if (list == null || list.isEmpty()) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, KNR_CODE_6, count, "I00079", count, conf.getManagement6Name(), knrCode6);

				} else {
					Management6 bean = list.get(0);
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, KNR_CODE_6, count, "I00080", count, conf.getManagement6Name(), knrCode6);
					}
				}
			}
		}
	}

	/**
	 * �d�󖾍ׂ̐������`�F�b�N������
	 * 
	 * @throws TException
	 */
	protected void checkConsistency() throws TException {

		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG() && isSlipTypeInvoiceSys(header);
		// �e�ϐ�������
		int count = 0;// �s���J�E���g

		for (SWK_DTL dtl : detailList) {
			count++;

			// �����d��̏ꍇ�͎��̖��ׂ�
			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue;
			}

			// ���E���c�������s�̏ꍇ�͎��̖��ׂ�
			if (isSkip(dtl)) {
				continue;
			}

			Company company = getCompany(dtl.getSWK_K_KAI_CODE());
			AccountConfig conf = company.getAccountConfig();

			// �d��W���[�i��.�v���ЃR�[�h
			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			// �d��W���[�i��.����R�[�h
			String depCode = dtl.getSWK_DEP_CODE();

			// �d��W���[�i��.�ȖڃR�[�h
			String kmkCode = dtl.getSWK_KMK_CODE();

			// �d��W���[�i��.�⏕�ȖڃR�[�h
			String hkmCode = dtl.getSWK_HKM_CODE();

			// �d��W���[�i��.����ȖڃR�[�h
			String ukmCode = dtl.getSWK_UKM_CODE();

			// �d��W���[�i��.�ʉ݃R�[�h
			String curCode = dtl.getSWK_CUR_CODE();

			// �d��W���[�i��.����ŃR�[�h
			String zeiCode = dtl.getSWK_ZEI_CODE();

			// �d��W���[�i��.�Ј��R�[�h
			String empCode = dtl.getSWK_EMP_CODE();

			// �d��W���[�i��.�����R�[�h
			String triCode = dtl.getSWK_TRI_CODE();

			// �d��W���[�i��.�Ǘ�1�R�[�h
			String knrCode1 = dtl.getSWK_KNR_CODE_1();

			// �d��W���[�i��.�Ǘ�2�R�[�h
			String knrCode2 = dtl.getSWK_KNR_CODE_2();

			// �d��W���[�i��.�Ǘ�3�R�[�h
			String knrCode3 = dtl.getSWK_KNR_CODE_3();

			// �d��W���[�i��.�Ǘ�4�R�[�h
			String knrCode4 = dtl.getSWK_KNR_CODE_4();

			// �d��W���[�i��.�Ǘ�5�R�[�h
			String knrCode5 = dtl.getSWK_KNR_CODE_5();

			// �d��W���[�i��.�Ǘ�6�R�[�h
			String knrCode6 = dtl.getSWK_KNR_CODE_6();

			// ڰ�
			BigDecimal curRate = dtl.getSWK_CUR_RATE();

			// �M�݋��z
			BigDecimal kin = dtl.getSWK_KIN();

			// ������
			Date hasDate = dtl.getHAS_DATE();

			// �v���ЃR�[�h���u�����N
			if (Util.isNullOrEmpty(kKaiCode)) {
				// �G���[���̐ݒ�
				addError(NULL, K_KAI_CODE, count, "I00081", count, "C00570");
			}

			// �v�㕔��R�[�h���u�����N
			if (Util.isNullOrEmpty(depCode)) {
				// �G���[���̐ݒ�
				addError(NULL, DEP_CODE, count, "I00081", count, "C00571");
			}

			// �ʉ݃R�[�h���u�����N
			if (Util.isNullOrEmpty(curCode)) {
				// �G���[���̐ݒ�
				addError(NULL, CUR_CODE, count, "I00081", count, "C00665");
			}

			// ���[�g���u�����N�A�܂���0
			if (curRate == null || curRate == new BigDecimal(0)) {
				// �G���[���̐ݒ�
				addError(NULL, RATE, count, "I00081", count, "C00556");
			}

			// �M�݋��z���u�����N�A�܂���0
			if (kin == null || kin == new BigDecimal(0)) {
				// �G���[���̐ݒ�
				addError(NULL, BASE_AMOUNT, count, "I00081", count, "C00576");
			}

			// �������Ȃ�(������NULL�\�̏ꍇ�A�K�{�`�F�b�N�s�v)
			if (!isAllowOccurDateBlank() && Util.isNullOrEmpty(hasDate)) {
				// �G���[���̐ݒ�
				addError(NULL, HAS_DATE, count, "I00081", count, "C00431");
			}

			// �Ȗ�
			Item kmkMst = null;

			// �⏕�Ȗ�
			SubItem hkmMst = null;

			// ����Ȗڃ��X�g
			DetailItem ukmMst = null;

			ItemManager itemMn = (ItemManager) getComponent(ItemManager.class);

			// �Ȗڃ}�X�^
			if (!Util.isNullOrEmpty(kmkCode)) {
				kmkMst = itemMn.getItem(kKaiCode, kmkCode, null, null);
			}

			// �⏕�Ȗڃ}�X�^
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode)) {
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, null);
				hkmMst = bean.getSubItem();
			}

			// ����Ȗڃ}�X�^
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode) && !Util.isNullOrEmpty(ukmCode)) {
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, ukmCode);
				ukmMst = bean.getSubItem() != null ? bean.getSubItem().getDetailItem() : null;
			}

			// �ȖڃR�[�h���u�����N
			if (Util.isNullOrEmpty(kmkCode)) {
				// �G���[���̐ݒ�
				addError(NULL, KMK_CODE, count, "I00082", count, conf.getItemName());
			}

			// a.�Ȗڃ}�X�^.�⏕�敪 = 1�F���� AND �⏕�ȖڃR�[�h���u�����N
			if (kmkMst != null) {
				if (kmkMst.hasSubItem() && Util.isNullOrEmpty(hkmCode)) {
					// �G���[���̐ݒ�
					addError(NULL, HKM_CODE, count, "I00082", count, conf.getSubItemName());
				}
			}

			// a.�⏕�Ȗڃ}�X�^.����敪 = 1�F���� AND ����ȖڃR�[�h���u�����N
			if (conf.isUseDetailItem() && hkmMst != null) {
				if (hkmMst.hasDetailItem() && Util.isNullOrEmpty(ukmCode)) {
					// �G���[���̐ݒ�
					addError(NULL, UKM_CODE, count, "I00082", count, conf.getDetailItemName());
				}
			}

			// �Q�Ɛ�t���O
			String itemFlg = "";

			// �e�}�X�^ ���������ڍׂɏ]���A�ΏۉȖ�/�⏕/����R�[�h�̃}�X�^�f�[�^���擾����B�i�J�n���ƏI�����͎w�肵�Ȃ��j
			if (kmkMst != null && hkmMst != null && ukmMst != null) {

				// �Ȗڃ}�X�^.�⏕�敪 = 0
				if (!kmkMst.hasSubItem()) {
					// �Q�Ɛ�͉Ȗ�
					itemFlg = "0";

				} else if (kmkMst.hasSubItem() && !hkmMst.hasDetailItem()) {
					// �Q�Ɛ�͕⏕�Ȗ�
					itemFlg = "1";

				} else {
					// �Q�Ɛ�͓���Ȗ�
					itemFlg = "2";
				}

			} else if (kmkMst != null && hkmMst == null) {

				// �Ȗڃ}�X�^.�⏕�敪 = 0
				if (!kmkMst.hasSubItem()) {
					// �Q�Ɛ�͉Ȗ�
					itemFlg = "0";

				}
			} else if (kmkMst != null && hkmMst != null && ukmMst == null) {

				// �Ȗڃ}�X�^.�⏕�敪 = 0
				if (!kmkMst.hasSubItem()) {
					// �Q�Ɛ�͉Ȗ�
					itemFlg = "0";

				} else if (kmkMst.hasSubItem() && !hkmMst.hasDetailItem()) {
					// �Q�Ɛ�͕⏕�Ȗ�
					itemFlg = "1";
				}
			}

			// �Ȗ�/�⏕/����̎Q�Ɛ悪�ݒ肳��Ă��Ȃ��ꍇ�A���̃��[�v��
			if (Util.isNullOrEmpty(itemFlg)) {
				continue;
			}

			boolean mcrFlg = false; // ���ʉݓ��̓t���O
			boolean uriZeiFlg = false; // ����ېœ��̓t���O
			boolean sirZeiFlg = false;// �d���ېœ��̓t���O
			boolean triCodeFlg = false; // �������̓t���O
			boolean empCodeFlg = false; // �Ј����̓t���O
			boolean knr1Flg = false; // �Ǘ�1���̓t���O
			boolean knr2Flg = false; // �Ǘ�2���̓t���O
			boolean knr3Flg = false; // �Ǘ�3���̓t���O
			boolean knr4Flg = false; // �Ǘ�4���̓t���O
			boolean knr5Flg = false; // �Ǘ�5���̓t���O
			boolean knr6Flg = false; // �Ǘ�6���̓t���O

			// �Ȗڃ}�X�^�Q�Ƃ̏ꍇ
			if (itemFlg.equals("0")) {
				mcrFlg = kmkMst.isUseForeignCurrency(); // ���ʉݓ��̓t���O
				uriZeiFlg = kmkMst.isUseSalesTaxation(); // ����ېœ��̓t���O
				sirZeiFlg = kmkMst.isUsePurchaseTaxation();// �d���ېœ��̓t���O
				triCodeFlg = kmkMst.isUseCustomer(); // �������̓t���O
				empCodeFlg = kmkMst.isUseEmployee(); // �Ј����̓t���O
				knr1Flg = kmkMst.isUseManagement1(); // �Ǘ�1���̓t���O
				knr2Flg = kmkMst.isUseManagement2(); // �Ǘ�2���̓t���O
				knr3Flg = kmkMst.isUseManagement3(); // �Ǘ�3���̓t���O
				knr4Flg = kmkMst.isUseManagement4(); // �Ǘ�4���̓t���O
				knr5Flg = kmkMst.isUseManagement5(); // �Ǘ�5���̓t���O
				knr6Flg = kmkMst.isUseManagement6(); // �Ǘ�6���̓t���O
			}

			// �⏕�Ȗڃ}�X�^�Q�Ƃ̏ꍇ
			if (itemFlg.equals("1")) {
				mcrFlg = hkmMst.isUseForeignCurrency(); // ���ʉݓ��̓t���O
				uriZeiFlg = hkmMst.isUseSalesTaxation(); // ����ېœ��̓t���O
				sirZeiFlg = hkmMst.isUsePurchaseTaxation();// �d���ېœ��̓t���O
				triCodeFlg = hkmMst.isUseCustomer(); // �������̓t���O
				empCodeFlg = hkmMst.isUseEmployee(); // �Ј����̓t���O
				knr1Flg = hkmMst.isUseManagement1(); // �Ǘ�1���̓t���O
				knr2Flg = hkmMst.isUseManagement2(); // �Ǘ�2���̓t���O
				knr3Flg = hkmMst.isUseManagement3(); // �Ǘ�3���̓t���O
				knr4Flg = hkmMst.isUseManagement4(); // �Ǘ�4���̓t���O
				knr5Flg = hkmMst.isUseManagement5(); // �Ǘ�5���̓t���O
				knr6Flg = hkmMst.isUseManagement6(); // �Ǘ�6���̓t���O
			}

			// ����Ȗڃ}�X�^�Q�Ƃ̏ꍇ
			if (itemFlg.equals("2")) {
				mcrFlg = ukmMst.isUseForeignCurrency(); // ���ʉݓ��̓t���O
				uriZeiFlg = ukmMst.isUseSalesTaxation(); // ����ېœ��̓t���O
				sirZeiFlg = ukmMst.isUsePurchaseTaxation();// �d���ېœ��̓t���O
				triCodeFlg = ukmMst.isUseCustomer(); // �������̓t���O
				empCodeFlg = ukmMst.isUseEmployee(); // �Ј����̓t���O
				knr1Flg = ukmMst.isUseManagement1(); // �Ǘ�1���̓t���O
				knr2Flg = ukmMst.isUseManagement2(); // �Ǘ�2���̓t���O
				knr3Flg = ukmMst.isUseManagement3(); // �Ǘ�3���̓t���O
				knr4Flg = ukmMst.isUseManagement4(); // �Ǘ�4���̓t���O
				knr5Flg = ukmMst.isUseManagement5(); // �Ǘ�5���̓t���O
				knr6Flg = ukmMst.isUseManagement6(); // �Ǘ�6���̓t���O
			}

			// �C���{�C�X��p�B����łɔ�K�i���������s���Ǝ҃t���Otrue�̏ꍇ�����R�[�htrue
			if (isInvoice && dtl.getTax() != null && Util.isNullOrEmpty(triCode)) {
				if (dtl.getTax().isNO_INV_REG_FLG()) {
					// �G���[���̐ݒ�
					String rowMsg = count + getWord("C04288"); // X�s��
					addError(NULL, TRI_CODE, count, "I01125", "C00408", rowMsg);
				}
			}

			// ���ʉݓ��̓t���O = 0:���͕s�� AND �ʉ݃R�[�h <> ��ʉ�
			if (!mcrFlg && !curCode.equals(keyCurrency)) {
				// �G���[���̐ݒ�
				addError(NULL, CUR_CODE, count, "I00062", count);
			}

			// ����ېœ��̓t���O = 1:���͉� OR �d���ېœ��̓t���O = 1:���͉� AND ����ŃR�[�h���u�����N
			if ((uriZeiFlg || sirZeiFlg) && Util.isNullOrEmpty(zeiCode)) {
				// �G���[���̐ݒ�
				addError(NULL, ZEI_CODE, count, "I00081", count, "C00573");

			}

			// �������̓t���O = 1:���͕K�{ AND �����R�[�h���u�����N
			if (triCodeFlg && Util.isNullOrEmpty(triCode)) {
				// �G���[���̐ݒ�
				addError(NULL, TRI_CODE, count, "I00081", count, "C00786");
			}

			// �Ј����̓t���O = 1:���͕K�{ AND �Ј��R�[�h���u�����N
			if (empCodeFlg && Util.isNullOrEmpty(empCode)) {
				// �G���[���̐ݒ�
				addError(NULL, EMP_CODE, count, "I00081", count, "C00697");
			}

			// �Ǘ��P�`6�𗘗p���邩�ǂ���
			boolean isUseMng1 = conf.isUseManagement1();
			boolean isUseMng2 = conf.isUseManagement2();
			boolean isUseMng3 = conf.isUseManagement3();
			boolean isUseMng4 = conf.isUseManagement4();
			boolean isUseMng5 = conf.isUseManagement5();
			boolean isUseMng6 = conf.isUseManagement6();

			// �Ǘ��P���̓t���O = 1:���͕K�{ AND �Ǘ��P�R�[�h���u�����N
			if (isUseMng1 && knr1Flg && Util.isNullOrEmpty(knrCode1)) {
				// �G���[���̐ݒ�
				addError(NULL, KNR_CODE_1, count, "I00082", count, conf.getManagement1Name());
			}

			// �Ǘ��Q���̓t���O = 1:���͕K�{ AND �Ǘ��Q�R�[�h���u�����N
			if (isUseMng2 && knr2Flg && Util.isNullOrEmpty(knrCode2)) {
				// �G���[���̐ݒ�
				addError(NULL, KNR_CODE_2, count, "I00082", count, conf.getManagement2Name());
			}

			// �Ǘ��R���̓t���O = 1:���͕K�{ AND �Ǘ��R�R�[�h���u�����N
			if (isUseMng3 && knr3Flg && Util.isNullOrEmpty(knrCode3)) {
				// �G���[���̐ݒ�
				addError(NULL, KNR_CODE_3, count, "I00082", count, conf.getManagement3Name());
			}

			// �Ǘ��S���̓t���O = 1:���͕K�{ AND �Ǘ��S�R�[�h���u�����N
			if (isUseMng4 && knr4Flg && Util.isNullOrEmpty(knrCode4)) {
				// �G���[���̐ݒ�
				addError(NULL, KNR_CODE_4, count, "I00082", count, conf.getManagement4Name());
			}

			// �Ǘ��T���̓t���O = 1:���͕K�{ AND �Ǘ��T�R�[�h���u�����N
			if (isUseMng5 && knr5Flg && Util.isNullOrEmpty(knrCode5)) {
				// �G���[���̐ݒ�
				addError(NULL, KNR_CODE_5, count, "I00082", count, conf.getManagement5Name());
			}

			// �Ǘ��U���̓t���O = 1:���͕K�{ AND �Ǘ��U�R�[�h���u�����N
			if (isUseMng6 && knr6Flg && Util.isNullOrEmpty(knrCode6)) {
				// �G���[���̐ݒ�
				addError(NULL, KNR_CODE_6, count, "I00082", count, conf.getManagement6Name());
			}
		}
	}

	/**
	 * �G���[����ݒ肷��
	 * 
	 * @param errorType �G���[���
	 * @param dataType �G���[�f�[�^�^�C�v
	 * @param rowNo �s�ԍ�
	 * @param messageID
	 * @param bindIds
	 */
	protected void addError(SlipError errorType, SlipError dataType, int rowNo, String messageID, Object... bindIds) {
		Message msg = new Message(messageID, bindIds);
		msg.setErrorType(errorType);
		msg.setDataType(dataType);
		msg.setRowNo(rowNo);

		errorList.add(msg);
	}

	/**
	 * ��Џ��̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ��Џ��
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

	/**
	 * �ΏۊO�d�󂩂ǂ���
	 * 
	 * @param dtl
	 * @return true:�ΏۊO�d��
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

	/**
	 * �C���{�C�X�p�F�`�[��ʃC���{�C�X�`�F�b�N���邩�ǂ���
	 * 
	 * @param hdr
	 * @return �`�[��ʏ��
	 * @throws TException
	 */
	protected boolean isSlipTypeInvoiceSys(SWK_HDR hdr) throws TException {

		SlipTypeManager manager = (SlipTypeManager) getComponent(SlipTypeManager.class);
		SlipType slipType = manager.get(getCompanyCode(), hdr.getSWK_DEN_SYU());
		if (slipType == null) {
			return false;
		}
		// �`�[��ʍ�����Џ��K�i���������s���Ǝғo�^�ԍ��ԍ����Ȃ�
		if (Util.isNullOrEmpty(getCompany().getInvRegNo()) && "031".equals(hdr.getSWK_DEN_SYU())) {
			return false;
		}
		return slipType.isINV_SYS_FLG();
	}

}
