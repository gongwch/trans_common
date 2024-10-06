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
 * �d��w�b�_�[�`�F�b�N
 */
public class HeaderCheck extends TModel {

	/** �G���[��� */
	protected List<Message> errorList = new LinkedList<Message>();

	/** ��Џ�� */
	protected Map<String, Company> companys = new TreeMap<String, Company>();

	/**
	 * �d��W���[�i���w�b�_�[�̍��ڂ��`�F�b�N����
	 * 
	 * @param slip �`�[�f�[�^
	 * @return errList �G���[���X�g
	 * @throws TException
	 */
	public List<Message> checkHeader(Slip slip) throws TException {
		errorList.clear();
		companys.clear();

		// ���ߊ֘A�`�F�b�N
		checkSlipDate(slip);

		// �L�������`�F�b�N
		checkTerm(slip);

		return errorList;
	}

	/**
	 * �`�[���t(���ߓ�)�A���Z�i�K�̍ă`�F�b�N���s��
	 * 
	 * @param slip �`�[�f�[�^
	 * @throws TException
	 */
	protected void checkSlipDate(Slip slip) throws TException {

		Company keyCompany = getCompany(slip.getCompanyCode());

		// ���߃`�F�b�N
		Date slipDate = slip.getSlipDate(); // �`�[���t
		int stage = slip.getHeader().getSWK_KSN_KBN(); // ���Z�i�K
		if (isClosed(keyCompany, slipDate, stage)) {
			addError(CLOSED, SLIP_DATE, "I00131");// �w��̓`�[���t�͒��߂��Ă��܂��B
		}

		// �v���̐�s�A���߃`�F�b�N
		for (SWK_DTL dtl : slip.getDetails()) {
			Company kcompany = getCompany(dtl.getSWK_K_KAI_CODE());
			if (keyCompany.getCode().equals(kcompany.getCode())) {
				continue;
			}

			// ���߃`�F�b�N
			if (isClosed(kcompany, slipDate, stage)) {
				addError(CLOSED, SLIP_DATE, "I00134", kcompany.getCode());// �w��̓`�[���t�͌v���А�[{0}]�Œ��߂��Ă��܂��B
			}
		}
	}

	/**
	 * �w���Ђ���ɁA���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * 
	 * @param company ���
	 * @param slipDate �`�[���t
	 * @param settlementStage ���Z�i�K
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
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
	 * �w�b�_�[�̗L���������`�F�b�N����
	 * 
	 * @param slip �`�[�f�[�^
	 */
	protected void checkTerm(Slip slip) {

		SWK_HDR hdr = slip.getHeader();

		// �ȖځA�⏕�ȖځA����Ȗڂ̖��͉̂�ЃR���g���[���}�X�^���擾
		Company cmp = null;
		try {
			cmp = getCompany(hdr.getKAI_CODE());
		} catch (TException e1) {
			cmp = getCompany();
		}
		AccountConfig ac = cmp.getAccountConfig();

		ItemManager itemMn = (ItemManager) getComponent(ItemManager.class);

		// ����}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE())) {
			DepartmentManager dao = (DepartmentManager) getComponent(DepartmentManager.class);

			// ����}�X�^�����i���݃`�F�b�N�j
			DepartmentSearchCondition condition = new DepartmentSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_DEP_CODE());
			List<Department> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_DEP_CODE, "I00066", "C00571", hdr.getSWK_DEP_CODE());

			} else {
				Department bean = list.get(0);

				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_DEP_CODE, "I00061", "C00571", hdr.getSWK_DEP_CODE());
				}
			}
		}

		// �Ȗڃ}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE())) {

			// �Ȗڃ}�X�^�����i���݃`�F�b�N�j
			Item bean = null;
			try {
				bean = itemMn.getItem(hdr.getKAI_CODE(), hdr.getSWK_KMK_CODE(), null, null);
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (bean == null) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_KMK_CODE, "I00077", ac.getItemName(), hdr.getSWK_KMK_CODE());

			} else {

				if (!Util.isNullOrEmpty(bean.getFixedDepartmentCode())
					&& !bean.getFixedDepartmentCode().equals(hdr.getSWK_DEP_CODE())) {

					// �G���[���̐ݒ�
					addError(NOT_FOUND, HDR_KMK_CODE, "I00077", ac.getItemName(), hdr.getSWK_KMK_CODE());

				} else {
					// �L�������`�F�b�N
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
						|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

						// �G���[���̐ݒ�
						addError(TERM_OUT, HDR_KMK_CODE, "I00078", ac.getItemName(), hdr.getSWK_KMK_CODE());
					}
				}
			}
		}

		// �⏕�Ȗڃ}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {

			// �⏕�Ȗڃ}�X�^����
			Item bean = null;
			try {
				bean = itemMn.getItem(hdr.getKAI_CODE(), hdr.getSWK_KMK_CODE(), hdr.getSWK_HKM_CODE(), null);
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (bean == null || bean.getSubItem() == null) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_HKM_CODE, "I00077", ac.getSubItemName(), hdr.getSWK_HKM_CODE());

			} else {
				// �L�������`�F�b�N
				Date from = bean.getSubItem().getDateFrom();
				Date to = bean.getSubItem().getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {
					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_HKM_CODE, "I00078", ac.getSubItemName(), hdr.getSWK_HKM_CODE());
				}
			}
		}

		// ����Ȗڃ}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_KMK_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())
			&& !Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {

			// ����Ȗڃ}�X�^����
			Item bean = null;
			try {
				bean = itemMn.getItem(hdr.getKAI_CODE(), hdr.getSWK_KMK_CODE(), hdr.getSWK_HKM_CODE(),
					hdr.getSWK_UKM_CODE());
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (bean == null || bean.getSubItem() == null || bean.getSubItem().getDetailItem() == null) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_HKM_CODE, "I00077", ac.getDetailItemName(), hdr.getSWK_UKM_CODE());

			} else {
				// �L�������`�F�b�N
				Date from = bean.getSubItem().getDetailItem().getDateFrom();
				Date to = bean.getSubItem().getDetailItem().getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_HKM_CODE, "I00078", ac.getDetailItemName(), hdr.getSWK_UKM_CODE());
				}
			}
		}

		// �Ј��}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_EMP_CODE())) {

			// �Ј��}�X�^Dao
			EmployeeManager dao = (EmployeeManager) getComponent(EmployeeManager.class);

			// �Ј��}�X�^�f�[�^������
			EmployeeSearchCondition condition = new EmployeeSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_EMP_CODE());
			List<Employee> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_EMP_CODE, "I00066", "C00697", hdr.getSWK_EMP_CODE());

			} else {
				Employee bean = list.get(0);
				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_EMP_CODE, "I00061", "C00697", hdr.getSWK_EMP_CODE());
				}
			}
		}

		// �����}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE())) {

			// �����}�X�^Dao
			CustomerManager dao = (CustomerManager) getComponent(CustomerManager.class);

			// �����}�X�^�f�[�^������
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_TRI_CODE());
			List<Customer> list = null;
			try {
				list = dao.get(condition);
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_TRI_CODE, "I00066", "C00786", hdr.getSWK_TRI_CODE());

			} else {
				Customer bean = list.get(0);
				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_TRI_CODE, "I00061", "C00786", hdr.getSWK_TRI_CODE());

				}

			}
		}

		// �����x�������}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE()) && !Util.isNullOrEmpty(hdr.getSWK_TJK_CODE())) {

			// �����x�������}�X�^Dao
			PaymentSettingManager dao = (PaymentSettingManager) getComponent(PaymentSettingManager.class);

			// �����x�������}�X�^�f�[�^������
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

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, TRI_SJ_CODE, "I00066", "C00672", hdr.getSWK_TJK_CODE());

			} else {
				PaymentSetting bean = list.get(0);
				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, TRI_SJ_CODE, "I00061", "C00672", hdr.getSWK_TJK_CODE());

				}

			}
		}

		// �x�����@�}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_HOH_CODE())) {

			// �x�����@�}�X�^Dao
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

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HOH_CODE, "I00066", "C00864", hdr.getSWK_HOH_CODE());

			} else {
				PaymentMethod bean = list.get(0);
				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HOH_CODE, "I00061", "C00864", hdr.getSWK_HOH_CODE());
				}
			}
		}

		// ��s�����}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE())) {

			// ��s�����}�X�^Dao
			BankAccountManager dao = (BankAccountManager) getComponent(BankAccountManager.class);

			// ��s�����}�X�^�f�[�^������
			BankAccountSearchCondition cond = new BankAccountSearchCondition();
			cond.setCompanyCode(hdr.getKAI_CODE());
			cond.setCode(hdr.getSWK_CBK_CODE());
			List<BankAccount> bankAccList = null;
			try {
				bankAccList = dao.get(cond);
			} catch (TException e) {
				//
			}

			// �f�[�^��������Ȃ�
			if (bankAccList == null || bankAccList.isEmpty()) {
				// �G���[���̐ݒ�
				addError(NOT_FOUND, CBK_CODE, "I00066", "C01879", hdr.getSWK_CBK_CODE());

			} else {

				BankAccount bankAcc = bankAccList.get(0);
				// �L�������`�F�b�N
				Date from = bankAcc.getDateFrom();
				Date to = bankAcc.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, CBK_CODE, "I00061", "C01879", hdr.getSWK_CBK_CODE());
				}
			}
		}

		// �����敪�}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_SEI_KBN())) {
			BillTypeManager logic = (BillTypeManager) getComponent(BillTypeManager.class);
			BillTypeSearchCondition param = new BillTypeSearchCondition();
			param.setCompanyCode(hdr.getKAI_CODE());
			param.setCode(hdr.getSWK_SEI_KBN());

			// ����
			List<BillType> list = null;
			try {
				list = logic.get(param);
			} catch (TException ex) {
				// �G���[�̏ꍇ��NULL����
			}

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_SEI_KBN, "I00066", "C10092", hdr.getSWK_SEI_KBN());// �����敪�̃f�[�^�����݂��܂���B

			} else {
				// �L�������`�F�b�N
				BillType bean = list.get(0);
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_CUR_CODE, "I00061", "C10092", hdr.getSWK_SEI_KBN());// �̗L���������؂�Ă��܂��B
				}
			}
		}

		// �ʉ݃}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_CUR_CODE())) {
			// �ʉ݃}�X�^����
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

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, HDR_CUR_CODE, "I00066", "C00665", hdr.getSWK_CUR_CODE());

			} else {
				Currency bean = list.get(0);
				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, HDR_CUR_CODE, "I00061", "C00665", hdr.getSWK_CUR_CODE());
				}
			}
		}

		// �E�v�}�X�^
		if (!Util.isNullOrEmpty(hdr.getSWK_TEK_CODE())) {
			// �E�v�}�X�^����
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

			// �f�[�^��������Ȃ�
			if (list == null || list.isEmpty()) {

				// �G���[���̐ݒ�
				addError(NOT_FOUND, TEK_CODE, "I00066", "C00564", hdr.getSWK_TEK_CODE());

			} else {
				Remark bean = list.get(0);
				// �L�������`�F�b�N
				Date from = bean.getDateFrom();
				Date to = bean.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, hdr.getSWK_DEN_DATE()))
					|| (to != null && !Util.isSmallerThenByYMD(hdr.getSWK_DEN_DATE(), to))) {

					// �G���[���̐ݒ�
					addError(TERM_OUT, TEK_CODE, "I00061", "C00564", hdr.getSWK_TEK_CODE());
				}
			}
		}
	}

	/**
	 * �G���[����ݒ肷��
	 * 
	 * @param errorType �G���[���
	 * @param dataType �G���[�f�[�^�^�C�v
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
}
