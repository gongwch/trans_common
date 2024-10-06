package jp.co.ais.trans2.model.payment;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * �x�������}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class PaymentSettingManagerImpl extends TModel implements PaymentSettingManager {

	/** �V�����ړI�}�X�^���g�����ǂ����Atrue:�g�� */
	protected static final boolean isUseNewRemittance = ServerConfig.isFlagOn("trans.new.mp0100.use");

	/**
	 * �w������ɊY������x�����@����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������x�����@���
	 * @throws TException
	 */
	public List<PaymentSetting> get(PaymentSettingSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<PaymentSetting> list = new ArrayList<PaymentSetting>();

		try {

			VCreator sql = new VCreator();

			sql.add("SELECT");
			sql.add("   sj.KAI_CODE, "); // ��ЃR�[�h
			sql.add("   sj.TRI_CODE, "); // �����R�[�h
			sql.add("   sj.TJK_CODE, "); // ���������R�[�h
			sql.add("   sj.FURI_TESU_KBN, "); // �U���萔���敪
			sql.add("   sj.SJC_DATE, "); // �x���������ߓ�
			sql.add("   sj.SJR_MON, "); // �x���������ߌ㌎
			sql.add("   sj.SJP_DATE, "); // �x�������x����
			sql.add("   sj.SIHA_KBN, "); // �x���敪
			sql.add("   sj.SIHA_HOU_CODE, "); // �x�����@
			sql.add("   sj.FURI_CBK_CODE, "); // �U���U�o��s��������
			sql.add("   sj.BNK_CODE, "); // ��s�R�[�h
			sql.add("   sj.BNK_STN_CODE, "); // �x�X�R�[�h
			sql.add("   sj.YKN_KBN, "); // �a�����
			sql.add("   sj.YKN_NO, "); // �����ԍ�
			sql.add("   sj.YKN_NAME, "); // �������`
			sql.add("   sj.YKN_KANA, "); // �������`�J�i
			sql.add("   sj.GS_MKT_CODE, "); // �����ړI��
			sql.add("   sj.GS_STN_NAME, "); // ��d���x�X����
			sql.add("   sj.GS_BNK_NAME, "); // ��d����s����
			sql.add("   sj.GS_TESU_KBN, "); // �萔���敪
			sql.add("   sj.SIHA_BNK_NAME, "); // �x����s����
			sql.add("   sj.SIHA_STN_NAME, "); // �x���x�X����
			sql.add("   sj.SIHA_BNK_ADR, "); // �x����s�Z��
			sql.add("   sj.KEIYU_BNK_NAME, "); // �o�R��s����
			sql.add("   sj.KEIYU_STN_NAME, "); // �o�R�x�X����
			sql.add("   sj.KEIYU_BNK_ADR, "); // �o�R��s�Z��
			sql.add("   sj.UKE_ADR, "); // ���l�Z��
			sql.add("   sj.STR_DATE, "); // �J�n�N����
			sql.add("   sj.END_DATE, "); // �I���N����
			sql.add(" 	sj.COU_CODE,");
			sql.add(" 	sj.GS_BNK_COU_CODE,");
			sql.add(" 	sj.GS_BNK_SWIFT_CODE,");
			sql.add(" 	sj.SIHA_BNK_COU_CODE,");
			sql.add(" 	sj.SIHA_BNK_SWIFT_CODE,");
			sql.add(" 	sj.KEIYU_BNK_COU_CODE,");
			sql.add(" 	sj.KEIYU_BNK_SWIFT_CODE,");
			sql.add(" 	sj.BNK_CHG_KBN,");

			// �x�����@
			sql.add("   hoh.HOH_HOH_CODE, "); // �x�����@�R�[�h
			sql.add("   hoh.HOH_HOH_NAME, "); // �x�����@��
			sql.add("   hoh.HOH_HOH_NAME_K, "); // �x�����@��������
			sql.add("   hoh.HOH_KMK_CODE, "); // �ȖڃR�[�h
			sql.add("   hoh.HOH_HKM_CODE, "); // �⏕�ȖڃR�[�h
			sql.add("   hoh.HOH_UKM_CODE, "); // ����ȖڃR�[�h
			sql.add("   hoh.HOH_DEP_CODE, "); // �v�㕔��R�[�h
			sql.add("   hoh.HOH_SIH_KBN, "); // �x���Ώۋ敪
			sql.add("   hoh.HOH_NAI_CODE, "); // �x�������R�[�h

			// ��s����
			sql.add("   cbk.CBK_CBK_CODE, "); // ��s�����R�[�h
			sql.add("   cbk.CBK_NAME, "); // ��s��������
			sql.add("   cbk.CUR_CODE, "); // �ʉ݃R�[�h
			sql.add("   cbk.CBK_BNK_CODE, "); // ��s�R�[�h
			sql.add("   cbk.CBK_STN_CODE, "); // �x�X�R�[�h
			sql.add("   cbk.CBK_IRAI_EMP_CODE, "); // �U���˗��l�R�[�h
			sql.add("   cbk.CBK_IRAI_NAME, "); // �U���˗��l��
			sql.add("   cbk.CBK_IRAI_NAME_J, "); // �U���˗��l���i�����j
			sql.add("   cbk.CBK_IRAI_NAME_E, "); // �U���˗��l���i�p���j
			sql.add("   cbk.CBK_YKN_KBN, "); // �a�����
			sql.add("   cbk.CBK_YKN_NO, "); // �����ԍ�
			sql.add("   cbk.CBK_EMP_FB_KBN, "); // �Ј��e�a�敪
			sql.add("   cbk.CBK_OUT_FB_KBN, "); // �ЊO�e�a�敪
			sql.add("   cbk.CBK_DEP_CODE, "); // �v�㕔��R�[�h
			sql.add("   cbk.CBK_KMK_CODE, "); // �ȖڃR�[�h
			sql.add("   cbk.CBK_HKM_CODE, "); // �⏕�ȖڃR�[�h
			sql.add("   cbk.CBK_UKM_CODE, "); // ����ȖڃR�[�h

			// ��s���x�X��1
			sql.add(" CASE                                   ");
			sql.add(" WHEN bnk1.BNK_NAME_S IS NULL THEN      ");
			sql.add("  (SELECT MIN(BNK_NAME_S) FROM BNK_MST WHERE BNK_CODE = sj.BNK_CODE) ");
			sql.add(" ELSE ");
			sql.add("  bnk1.BNK_NAME_S                       ");
			sql.add(" END BNK_NAME,                          ");// ��s��1
			sql.add("   bnk1.BNK_STN_NAME_S  BNK_STN_NAME, "); // �x�X��1

			// ��s���x�X��2
			sql.add("   bnk2.BNK_NAME_S  CBK_BNK_NAME, "); // ��s��2
			sql.add("   bnk2.BNK_STN_NAME_S  CBK_BNK_STN_NAME, "); // �x�X��2

			// �����
			sql.add("   tri.TRI_NAME, "); // ����於��
			sql.add("   tri.TRI_NAME_S, "); // ����旪��
			sql.add("   tri.TRI_TYPE_PSN_FLG, "); // �������B��

			// �����ړI
			if (isUseNewRemittance) {
				sql.add(" 	rmt.MKT_CODE,");
				sql.add(" 	rmt.RMT_NAME GS_MKT_NAME,");
			} else {
				sql.add("    NULL MKT_CODE,");
				sql.add(" 	mkt.MKT_NAME GS_MKT_NAME,");
			}

			// ��
			sql.add(" 	cou1.COU_NAME COU_NAME,"); // ���l��
			sql.add(" 	cou2.COU_NAME GS_BNK_COU_NAME,"); // ��d����s��
			sql.add(" 	cou3.COU_NAME SIHA_BNK_COU_NAME,"); // �x����s��
			sql.add(" 	cou4.COU_NAME KEIYU_BNK_COU_NAME"); // �o�R��s��

			sql.add("FROM TRI_SJ_MST sj");
			sql.add(" LEFT OUTER JOIN  AP_HOH_MST hoh  ON hoh.KAI_CODE = ? ", getCompanyCode());
			sql.add("                                 AND hoh.HOH_HOH_CODE = sj.SIHA_HOU_CODE ");
			sql.add(" LEFT OUTER JOIN  AP_CBK_MST cbk  ON cbk.KAI_CODE = ? ", getCompanyCode());
			sql.add("                                 AND cbk.CBK_CBK_CODE = sj.FURI_CBK_CODE ");
			sql.add(" LEFT OUTER JOIN  BNK_MST bnk1    ON sj.BNK_CODE      = bnk1.BNK_CODE");
			sql.add("                                 AND sj.BNK_STN_CODE  = bnk1.BNK_STN_CODE");
			sql.add(" LEFT OUTER JOIN  BNK_MST bnk2    ON cbk.CBK_BNK_CODE = bnk2.BNK_CODE");
			sql.add("                                 AND cbk.CBK_STN_CODE = bnk2.BNK_STN_CODE");
			sql.add(" LEFT OUTER JOIN  TRI_MST tri     ON tri.KAI_CODE = ? ", getCompanyCode());
			sql.add("                                 AND tri.TRI_CODE = sj.TRI_CODE");
			if (isUseNewRemittance) {
				sql.add(" LEFT OUTER JOIN  AP_RMT_MST rmt  ON rmt.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                 AND rmt.RMT_CODE = sj.GS_MKT_CODE ");
			} else {
				sql.add(" LEFT OUTER JOIN  AP_MKT_MST mkt  ON sj.GS_MKT_CODE = mkt.MKT_CODE");
			}
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou1 ON sj.COU_CODE = cou1.COU_CODE");
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou2 ON sj.GS_BNK_COU_CODE = cou2.COU_CODE");
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou3 ON sj.SIHA_BNK_COU_CODE = cou3.COU_CODE");
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou4 ON sj.KEIYU_BNK_COU_CODE = cou4.COU_CODE");

			sql.add("WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("   AND sj.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// �����R�[�h
			if (!Util.isNullOrEmpty(condition.getCustomerCode())) {
				sql.add("   AND sj.TRI_CODE = ? ", condition.getCustomerCode());
			}

			// ����� �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCustomerCodeFrom())) {
				sql.add("   AND sj.TRI_CODE >= ? ", condition.getCustomerCodeFrom());
			}

			// ����� �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCustomerCodeTo())) {
				sql.add("   AND sj.TRI_CODE <= ? ", condition.getCustomerCodeTo());
			}

			// �R�[�h
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("   AND sj.TJK_CODE = ? ", condition.getCode());
			}

			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("   AND sj.TJK_CODE >= ? ", condition.getCodeFrom());
			}

			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("   AND sj.TJK_CODE <= ? ", condition.getCodeTo());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("   AND sj.TJK_CODE " + SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT));
			}

			// ���̂����܂�
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.add("   AND CONCAT(NVL(bnk1.BNK_NAME_S,''), CONCAT(' ', NVL(bnk1.BNK_STN_NAME_S,''))) "
					+ SQLUtil.getLikeStatement(condition.getNameLike(), SQLUtil.NCHAR_AMBI));
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("   AND sj.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()));
				sql.add("   AND sj.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()));
			}

			// �x�����������ԍ�
			if (!Util.isNullOrEmpty(condition.getYknNo())) {
				sql.add("   AND sj.YKN_NO = ? ", condition.getYknNo());
			}

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (sj.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR sj.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add(" ORDER BY sj.KAI_CODE, sj.TRI_CODE, sj.TJK_CODE");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected PaymentSetting mapping(ResultSet rs) throws Exception {

		PaymentSetting bean = createPaymentSetting();
		bean.setCompanyCode(rs.getString("KAI_CODE"));// ��ЃR�[�h
		bean.setCode(rs.getString("TJK_CODE"));// ���������R�[�h
		bean.setCashInFeeType(CashInFeeType.getCashInFeeType(rs.getInt("FURI_TESU_KBN")));// �U���萔���敪
		bean.setCloseDay(rs.getString("SJC_DATE"));// �x���������ߓ�
		bean.setNextMonth(rs.getString("SJR_MON"));// �x���������ߌ㌎
		bean.setCashDay(rs.getString("SJP_DATE"));// �x�������x����
		bean.setPaymentDateType(PaymentDateType.getPaymentDateType(rs.getString("SIHA_KBN"))); // �x���敪
		bean.setBankCode(rs.getString("BNK_CODE"));// ��s�R�[�h1
		bean.setBankName(rs.getString("BNK_NAME"));// ��s��
		bean.setBranchCode(rs.getString("BNK_STN_CODE"));// �x�X�R�[�h
		bean.setBranchName(rs.getString("BNK_STN_NAME"));// �x�X��
		bean.setDepositKind(DepositKind.getDepositKind(rs.getInt("YKN_KBN")));// �a�����
		bean.setDepositKindName(getWord(DepositKind.getDepositKindName(bean.getDepositKind())));// �a����ږ���
		bean.setAccountNo(rs.getString("YKN_NO"));// �����ԍ�
		bean.setAccountName(rs.getString("YKN_NAME")); // �������`
		bean.setAccountNameKana(rs.getString("YKN_KANA"));// �������`�J�i
		bean.setSendBranchName(rs.getString("GS_STN_NAME"));// ��d���x�X����
		bean.setSendBankName(rs.getString("GS_BNK_NAME"));// ��d����s����
		bean.setCommissionPaymentType(CommissionPaymentType.get(rs.getInt("GS_TESU_KBN")));// �萔���敪
		bean.setPayBankName(rs.getString("SIHA_BNK_NAME"));// �x����s����
		bean.setPayBranchName(rs.getString("SIHA_STN_NAME"));// �x���x�X����
		bean.setPayBankAddress(rs.getString("SIHA_BNK_ADR"));// �x����s�Z��
		bean.setViaBankName(rs.getString("KEIYU_BNK_NAME"));// �o�R��s����
		bean.setViaBranchName(rs.getString("KEIYU_STN_NAME"));// �o�R�x�X����
		bean.setViaBankAddress(rs.getString("KEIYU_BNK_ADR"));// �o�R��s�Z��
		bean.setReceiverAddress(rs.getString("UKE_ADR"));// ���l�Z��
		bean.setDateFrom(rs.getDate("STR_DATE"));// �L�����ԊJ�n
		bean.setDateTo(rs.getDate("END_DATE"));// �L�����ԏI��
		bean.setBankSwiftCode(rs.getString("GS_BNK_SWIFT_CODE")); // ��d����sSWIFT�R�[�h
		bean.setPaymentBankSwiftCode(rs.getString("SIHA_BNK_SWIFT_CODE")); // �x����sSWIFT�R�[�h
		bean.setViaBankSwiftCode(rs.getString("KEIYU_BNK_SWIFT_CODE")); // �o�R��sSWIFT�R�[�h
		bean.setBankChargeType(BankChargeType.get(rs.getInt("BNK_CHG_KBN"))); // �o���N�`���[�W�敪

		// �x�����@�}�X�^
		if (!Util.isNullOrEmpty(rs.getString("HOH_HOH_CODE"))) {
			PaymentMethod pm = createPaymentMethod();
			pm.setCompanyCode(rs.getString("KAI_CODE"));// ��ЃR�[�h
			pm.setCode(rs.getString("HOH_HOH_CODE"));// �R�[�h
			pm.setName(rs.getString("HOH_HOH_NAME"));// ����
			pm.setNamek(rs.getString("HOH_HOH_NAME_K"));// ��������
			pm.setItemCode(rs.getString("HOH_KMK_CODE"));// �ȖڃR�[�h
			pm.setSubItemCode(rs.getString("HOH_HKM_CODE"));// �⏕�ȖڃR�[�h
			pm.setDetailItemCode(rs.getString("HOH_UKM_CODE"));// ����ȖڃR�[�h
			pm.setDepartmentCode(rs.getString("HOH_DEP_CODE"));// �v�㕔��R�[�h
			pm.setPaymentDivision(rs.getInt("HOH_SIH_KBN"));// �x���Ώۋ敪
			pm.setPaymentKind(PaymentKind.getPaymentKind(rs.getString("HOH_NAI_CODE")));// �x�������R�[�h
			bean.setPaymentMethod(pm);
		}

		// ��s�����}�X�^
		if (!Util.isNullOrEmpty(rs.getString("CBK_CBK_CODE"))) {
			BankAccount bankAccount = createBankAccount();
			bankAccount.setCompanyCode(rs.getString("KAI_CODE"));// ��ЃR�[�h
			bankAccount.setCode(rs.getString("CBK_CBK_CODE"));// �R�[�h
			bankAccount.setName(rs.getString("CBK_NAME"));// ��s��������
			bankAccount.setCurrencyCode(rs.getString("CUR_CODE")); // �ʉ݃R�[�h
			bankAccount.setBankCode(rs.getString("CBK_BNK_CODE"));// ��s�R�[�h
			bankAccount.setBankName(Util.avoidNull(rs.getString("CBK_BNK_NAME")));// ��s����
			bankAccount.setBranchCode(rs.getString("CBK_STN_CODE"));// �x�X�R�[�h
			bankAccount.setBranchName(Util.avoidNull(rs.getString("CBK_BNK_STN_NAME")));// �x�X����
			bankAccount.setClientCode(rs.getString("CBK_IRAI_EMP_CODE"));// �U���˗��l�R�[�h
			bankAccount.setClientName(rs.getString("CBK_IRAI_NAME"));// �U���˗��l��
			bankAccount.setClientNameJ(rs.getString("CBK_IRAI_NAME_J"));// �U���˗��l���i�����j
			bankAccount.setClientNameE(rs.getString("CBK_IRAI_NAME_E"));// �U���˗��l���i�p���j
			bankAccount.setDepositKind(DepositKind.getDepositKind(rs.getInt("CBK_YKN_KBN")));// �a�����
			bankAccount.setAccountNo(rs.getString("CBK_YKN_NO"));// �����ԍ�
			bankAccount.setDepartmentCode(rs.getString("CBK_DEP_CODE"));// �v�㕔��R�[�h
			bankAccount.setItemCode(rs.getString("CBK_KMK_CODE"));// �ȖڃR�[�h
			bankAccount.setSubItemCode(rs.getString("CBK_HKM_CODE"));// �⏕�ȖڃR�[�h
			bankAccount.setDetailItemCode(rs.getString("CBK_UKM_CODE"));// ����ȖڃR�[�h
			bankAccount.setUseEmployeePayment(rs.getInt("CBK_EMP_FB_KBN") != 0);// �Ј��e�a�Ŏg�p���邩
			bankAccount.setUseExPayment(rs.getInt("CBK_OUT_FB_KBN") != 0);// �ЊO�e�a�Ŏg�p���邩
			bean.setBankAccount(bankAccount);
		}

		// �����
		Customer customer = createCustomer();
		customer.setCode(rs.getString("TRI_CODE"));
		customer.setName(Util.avoidNull(rs.getString("TRI_NAME")));
		customer.setNames(Util.avoidNull(rs.getString("TRI_NAME_S")));
		customer.setPersonalCustomer(BooleanUtil.toBoolean(rs.getInt("TRI_TYPE_PSN_FLG")));
		bean.setCustomer(customer);

		// �����ړI�}�X�^
		if (!Util.isNullOrEmpty(rs.getString("GS_MKT_CODE"))) {
			Remittance rmt = createRemittance();
			rmt.setCode(rs.getString("GS_MKT_CODE"));
			rmt.setBalanceCode(rs.getString("MKT_CODE")); // ���ێ��x�R�[�h
			rmt.setName(Util.avoidNull(rs.getString("GS_MKT_NAME")));
			bean.setRemittancePurpose(rmt);
		}

		// ���}�X�^
		if (!Util.isNullOrEmpty(rs.getString("COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("COU_CODE"));
			cou.setName(rs.getString("COU_NAME"));
			bean.setRecipientCountry(cou); // ���l��
		}

		if (!Util.isNullOrEmpty(rs.getString("GS_BNK_COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("GS_BNK_COU_CODE"));
			cou.setName(rs.getString("GS_BNK_COU_NAME"));
			bean.setBankCountry(cou); // ��d����s��
		}

		if (!Util.isNullOrEmpty(rs.getString("SIHA_BNK_COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("SIHA_BNK_COU_CODE"));
			cou.setName(rs.getString("SIHA_BNK_COU_NAME"));
			bean.setPaymentBankCountry(cou); // �x����s��
		}

		if (!Util.isNullOrEmpty(rs.getString("KEIYU_BNK_COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("KEIYU_BNK_COU_CODE"));
			cou.setName(rs.getString("KEIYU_BNK_COU_NAME"));
			bean.setViaBankCountry(cou); // �o�R��s��
		}

		return bean;
	}

	/**
	 * @return ��
	 */
	protected Country createCountry() {
		return new Country();
	}

	/**
	 * @return �����ړI
	 */
	protected Remittance createRemittance() {
		return new Remittance();
	}

	/**
	 * @return �����
	 */
	protected Customer createCustomer() {
		return new Customer();
	}

	/**
	 * @return ��s����
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * @return �x�����@
	 */
	protected PaymentMethod createPaymentMethod() {
		return new PaymentMethod();
	}

	/**
	 * @return �x������
	 */
	protected PaymentSetting createPaymentSetting() {
		return new PaymentSetting();
	}

	/**
	 * �x�����@��o�^����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void entry(PaymentSetting bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO TRI_SJ_MST( ");
			s.add("   KAI_CODE, ");
			s.add("   TRI_CODE, ");
			s.add("   TJK_CODE, ");
			s.add("   FURI_TESU_KBN, ");
			s.add("   SJC_DATE, ");
			s.add("   SJR_MON, ");
			s.add("   SJP_DATE, ");
			s.add("   SIHA_KBN, ");
			s.add("   SIHA_HOU_CODE, ");
			s.add("   FURI_CBK_CODE, ");
			s.add("   BNK_CODE, ");
			s.add("   BNK_STN_CODE, ");
			s.add("   YKN_KBN, ");
			s.add("   YKN_NO, ");
			s.add("   YKN_NAME, ");
			s.add("   YKN_KANA, ");
			s.add("   GS_MKT_CODE, ");
			s.add("   GS_STN_NAME, ");
			s.add("   GS_BNK_NAME, ");
			s.add("   GS_TESU_KBN, ");
			s.add("   SIHA_BNK_NAME, ");
			s.add("   SIHA_STN_NAME, ");
			s.add("   SIHA_BNK_ADR, ");
			s.add("   KEIYU_BNK_NAME, ");
			s.add("   KEIYU_STN_NAME, ");
			s.add("   KEIYU_BNK_ADR, ");
			s.add("   UKE_ADR, ");
			s.add("   STR_DATE, ");
			s.add("   END_DATE, ");
			s.add("   INP_DATE, ");
			s.add("   PRG_ID, ");
			s.add("   USR_ID, ");
			s.add("   COU_CODE, ");
			s.add("   GS_BNK_COU_CODE, ");
			s.add("   GS_BNK_SWIFT_CODE, ");
			s.add("   SIHA_BNK_COU_CODE, ");
			s.add("   SIHA_BNK_SWIFT_CODE, ");
			s.add("   KEIYU_BNK_COU_CODE, ");
			s.add("   KEIYU_BNK_SWIFT_CODE, ");
			s.add("   BNK_CHG_KBN ");
			s.add(") VALUES (");
			s.add("   ?,", bean.getCompanyCode());
			s.add("   ?,", bean.getCustomer().getCode());
			s.add("   ?,", bean.getCode());
			s.add("   ?,", bean.getCashInFeeType() != null ? bean.getCashInFeeType().value : 0);
			s.add("   ?,", bean.getCloseDay());
			s.add("   ?,", bean.getNextMonth());
			s.add("   ?,", bean.getCashDay());
			s.add("   ?,", bean.getPaymentDateType() != null ? bean.getPaymentDateType().value : "");
			s.add("   ?,", bean.getPaymentMethod().getCode());
			s.add("   ?,", bean.getBankAccount() != null ? bean.getBankAccount().getCode() : null);
			s.add("   ?,", bean.getBankCode());
			s.add("   ?,", bean.getBranchCode());
			s.add("   ?,", bean.getDepositKind() != null ? bean.getDepositKind().value : 0);
			s.add("   ?,", bean.getAccountNo());
			s.add("   ?,", bean.getAccountName());
			s.add("   ?,", bean.getAccountNameKana());
			s.add("   ?,", bean.getRemittancePurpose() != null ? bean.getRemittancePurpose().getCode() : "");
			s.add("   ?,", bean.getSendBranchName());
			s.add("   ?,", bean.getSendBankName());
			s.add("   ?,", bean.getCommissionPaymentType() != null ? bean.getCommissionPaymentType().value : 0);
			s.add("   ?,", bean.getPayBankName());
			s.add("   ?,", bean.getPayBranchName());
			s.add("   ?,", bean.getPayBankAddress());
			s.add("   ?,", bean.getViaBankName());
			s.add("   ?,", bean.getViaBranchName());
			s.add("   ?,", bean.getViaBankAddress());
			s.add("   ?,", bean.getReceiverAddress());
			s.add("   ?,", bean.getDateFrom());
			s.add("   ?,", bean.getDateTo());
			s.adt("   ?,", getNow());
			s.add("   ?,", getProgramCode());
			s.add("   ?,", getUserCode());
			s.add("   ?,", bean.getRecipientCountry() != null ? bean.getRecipientCountry().getCode() : ""); // ���l���R�[�h
			s.add("   ?,", bean.getBankCountry() != null ? bean.getBankCountry().getCode() : ""); // ��d����s���R�[�h
			s.add("   ?,", bean.getBankSwiftCode()); // ��d����sSWIFT�R�[�h
			s.add("   ?,", bean.getPaymentBankCountry() != null ? bean.getPaymentBankCountry().getCode() : ""); // �x����s���R�[�h
			s.add("   ?,", bean.getPaymentBankSwiftCode()); // �x����sSWIFT�R�[�h
			s.add("   ?,", bean.getViaBankCountry() != null ? bean.getViaBankCountry().getCode() : ""); // �o�R��s���R�[�h
			s.add("   ?,", bean.getViaBankSwiftCode()); // �o�R��sSWIFT�R�[�h
			s.add("   ? ", bean.getBankChargeType() != null ? bean.getBankChargeType().value : 0); // �o���N�`���[�W�敪
			s.add(") ");

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �x�����@���C������B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void modify(PaymentSetting bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();
			s.add("UPDATE TRI_SJ_MST ");
			s.add(" SET ");
			s.add("   TRI_CODE = ?,", bean.getCustomer().getCode());
			s.add("   TJK_CODE = ?,", bean.getCode());

			if (bean.getCashInFeeType() != null) {
				s.add("   FURI_TESU_KBN = ?,", bean.getCashInFeeType().value);
			} else {
				s.add("   FURI_TESU_KBN = NULL,");
			}

			s.add("   SJC_DATE = ?,", bean.getCloseDay());
			s.add("   SJR_MON = ?,", bean.getNextMonth());
			s.add("   SJP_DATE = ?,", bean.getCashDay());

			if (bean.getPaymentDateType() != null) {
				s.add("   SIHA_KBN = ?,", bean.getPaymentDateType().value);
			} else {
				s.add("   SIHA_KBN = NULL,");
			}

			s.add("   SIHA_HOU_CODE = ?,", bean.getPaymentMethod().getCode());
			s.add("   FURI_CBK_CODE = ?,", bean.getBankAccount() != null ? bean.getBankAccount().getCode() : null);
			s.add("   BNK_CODE = ?,", bean.getBankCode());
			s.add("   BNK_STN_CODE = ?,", bean.getBranchCode());

			if (bean.getDepositKind() != null) {
				s.add("   YKN_KBN = ?,", bean.getDepositKind().value);
			} else {
				s.add("   YKN_KBN = NULL,");
			}

			s.add("   YKN_NO = ?,", bean.getAccountNo());
			s.add("   YKN_NAME = ?,", bean.getAccountName());
			s.add("   YKN_KANA = ?,", bean.getAccountNameKana());

			if (bean.getRemittancePurpose() != null) {
				s.add("   GS_MKT_CODE = ?,", bean.getRemittancePurpose().getCode());
			} else {
				s.add("   GS_MKT_CODE = NULL,");
			}

			s.add("   GS_STN_NAME = ?,", bean.getSendBranchName());
			s.add("   GS_BNK_NAME = ?,", bean.getSendBankName());

			if (bean.getCommissionPaymentType() != null) {
				s.add("   GS_TESU_KBN = ?,", bean.getCommissionPaymentType().value);
			} else {
				s.add("   GS_TESU_KBN = NULL,");
			}

			s.add("   SIHA_BNK_NAME = ?,", bean.getPayBankName());
			s.add("   SIHA_STN_NAME = ?,", bean.getPayBranchName());
			s.add("   SIHA_BNK_ADR = ?,", bean.getPayBankAddress());
			s.add("   KEIYU_BNK_NAME = ?,", bean.getViaBankName());
			s.add("   KEIYU_STN_NAME = ?,", bean.getViaBranchName());
			s.add("   KEIYU_BNK_ADR = ?,", bean.getViaBankAddress());
			s.add("   UKE_ADR = ?,", bean.getReceiverAddress());
			s.add("   STR_DATE = ?,", bean.getDateFrom());
			s.add("   END_DATE = ?,", bean.getDateTo());
			s.adt("   UPD_DATE = ?,", getNow());
			s.add("   PRG_ID = ?,", getProgramCode());
			s.add("   USR_ID = ?,", getUserCode());

			if (bean.getRecipientCountry() != null) {
				s.add("   COU_CODE = ?,", bean.getRecipientCountry().getCode()); // ���l���R�[�h
			} else {
				s.add("   COU_CODE = NULL,");
			}

			if (bean.getBankCountry() != null) {
				s.add("   GS_BNK_COU_CODE = ?,", bean.getBankCountry().getCode()); // ��d����s���R�[�h
			} else {
				s.add("   GS_BNK_COU_CODE = NULL,");
			}

			s.add("   GS_BNK_SWIFT_CODE = ?,", bean.getBankSwiftCode()); // ��d����sSWIFT�R�[�h

			if (bean.getPaymentBankCountry() != null) {
				s.add("   SIHA_BNK_COU_CODE = ?,", bean.getPaymentBankCountry().getCode()); // �x����s���R�[�h
			} else {
				s.add("   SIHA_BNK_COU_CODE = NULL,");
			}

			s.add("   SIHA_BNK_SWIFT_CODE = ?,", bean.getPaymentBankSwiftCode()); // �x����sSWIFT�R�[�h

			if (bean.getViaBankCountry() != null) {
				s.add("   KEIYU_BNK_COU_CODE = ?,", bean.getViaBankCountry().getCode()); // �o�R��s���R�[�h
			} else {
				s.add("   KEIYU_BNK_COU_CODE = NULL,");
			}

			s.add("   KEIYU_BNK_SWIFT_CODE = ?,", bean.getViaBankSwiftCode()); // �o�R��sSWIFT�R�[�h

			if (bean.getBankChargeType() != null) {
				s.add("   BNK_CHG_KBN = ? ", bean.getBankChargeType().value); // �o���N�`���[�W�敪
			} else {
				s.add("   BNK_CHG_KBN = ? ", 0);
			}

			s.add("WHERE KAI_CODE = ? ", bean.getCompanyCode());
			s.add("  AND TRI_CODE = ? ", bean.getCustomer().getCode());
			s.add("  AND TJK_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �x�����@���폜����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void delete(PaymentSetting bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.PAYMENT_SETTING);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setCustomerCode(bean.getCustomer().getCode());
		condition.setPaymentSettingCode(bean.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE ");
			s.add("FROM TRI_SJ_MST ");
			s.add("WHERE KAI_CODE = ? ", bean.getCompanyCode());
			s.add("  AND TRI_CODE = ? ", bean.getCustomer().getCode());
			s.add("  AND TJK_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �x�����ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̈ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(PaymentSettingSearchCondition condition) throws TException {

		try {
			// ��Ѓf�[�^�̒��o
			List<PaymentSetting> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PaymentSettingExcel layout = new PaymentSettingExcel(getUser().getLanguage());
			return layout.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �x�����ꗗ���G�N�Z���`���ŕԂ�(�C�O�p)
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̈ꗗ
	 * @throws TException
	 */
	public byte[] getExcelForGlobal(PaymentSettingSearchCondition condition) throws TException {

		try {
			// ��Ѓf�[�^�̒��o
			List<PaymentSetting> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PaymentSettingGlobalExcel layout = new PaymentSettingGlobalExcel(getUser().getLanguage());
			return layout.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * SQL creator
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}
