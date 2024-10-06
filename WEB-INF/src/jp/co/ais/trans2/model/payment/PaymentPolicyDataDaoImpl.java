package jp.co.ais.trans2.model.payment;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�����j�f�[�^���oDao�����N���X
 */
public class PaymentPolicyDataDaoImpl extends TModel implements PaymentPolicyDataDao {

	/**
	 * �x�����j�f�[�^����
	 * 
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException {

		Connection conn = DBUtil.getConnection();

		PaymentPolicy bean = createPaymentPolicy();

		try {

			SQLCreator s = new SQLCreator();

			// �J���������݂����ꍇ

			s.add("SELECT ");
			s.add("  KAI_CODE,");
			s.add("  SHH_FB_PATH,");
			s.add("  SHH_REM_PATH,");
			s.add("  SHH_EMP_FB_PATH");
			s.add("FROM AP_SHH_MST");
			s.add("WHERE  KAI_CODE = ?", getCompanyCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				bean = mappingPaymentMethod(rs);
			}

		} catch (Exception e) {

			bean.setCompanyCode(getCompanyCode());

		} finally {
			DBUtil.close(conn);
		}

		return bean;
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected PaymentPolicy mappingPaymentMethod(ResultSet rs) throws Exception {

		PaymentPolicy bean = createPaymentPolicy();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setFbOutputPath(rs.getString("SHH_FB_PATH"));
		bean.setRemmitanceOutputPath(rs.getString("SHH_REM_PATH"));
		bean.setEmployeeFbOutputPath(rs.getString("SHH_EMP_FB_PATH"));

		return bean;
	}

	/**
	 * �x�����j�f�[�^����
	 * 
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException {

		Connection conn = DBUtil.getConnection();

		PaymentPolicy bean = createPaymentPolicy();

		try {

			SQLCreator s = new SQLCreator();

			// �J���������݂����ꍇ

			s.add("SELECT *");
			s.add("FROM AP_SHH_MST");
			s.add("WHERE  KAI_CODE = ?", getCompanyCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				bean = mapping(rs);
			}

		} catch (Exception e) {

			bean.setCompanyCode(getCompanyCode());

		} finally {
			DBUtil.close(conn);
		}

		return bean;
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected PaymentPolicy mapping(ResultSet rs) throws Exception {

		PaymentPolicy bean = createPaymentPolicy();

		bean.setCompanyCode(rs.getString("KAI_CODE"));

		bean.setSHH_SIHA_1(rs.getInt("SHH_SIHA_1")); // �莞�x���i�P���j
		bean.setSHH_SIHA_5(rs.getInt("SHH_SIHA_5")); // �莞�x���i�T���j
		bean.setSHH_SIHA_10(rs.getInt("SHH_SIHA_10")); // �莞�x���i10���j
		bean.setSHH_SIHA_15(rs.getInt("SHH_SIHA_15")); // �莞�x���i15���j
		bean.setSHH_SIHA_20(rs.getInt("SHH_SIHA_20")); // �莞�x���i20���j
		bean.setSHH_SIHA_25(rs.getInt("SHH_SIHA_25")); // �莞�x���i25���j
		bean.setSHH_SIHA_30(rs.getInt("SHH_SIHA_30")); // �莞�x���i�����j
		bean.setSHH_BNK_KBN_1(rs.getInt("SHH_BNK_KBN_1")); // ��s�x���敪�i1���j
		bean.setSHH_BNK_KBN_5(rs.getInt("SHH_BNK_KBN_5")); // ��s�x���敪�i5���j
		bean.setSHH_BNK_KBN_10(rs.getInt("SHH_BNK_KBN_10")); // ��s�x���敪�i10���j
		bean.setSHH_BNK_KBN_15(rs.getInt("SHH_BNK_KBN_15")); // ��s�x���敪�i15���j
		bean.setSHH_BNK_KBN_20(rs.getInt("SHH_BNK_KBN_20")); // ��s�x���敪�i20���j
		bean.setSHH_BNK_KBN_25(rs.getInt("SHH_BNK_KBN_25")); // ��s�x���敪�i25���j
		bean.setSHH_BNK_KBN_30(rs.getInt("SHH_BNK_KBN_30")); // ��s�x���敪�i�����j
		bean.setSHH_SIHA_MIN(rs.getBigDecimal("SHH_SIHA_MIN")); // �x�������z
		bean.setSHH_FURI_MIN(rs.getBigDecimal("SHH_FURI_MIN")); // �U���萔�������z
		bean.setSHH_TESU_KMK_CODE(rs.getString("SHH_TESU_KMK_CODE")); // �萔���ȖڃR�[�h
		bean.setSHH_TESU_HKM_CODE(rs.getString("SHH_TESU_HKM_CODE")); // �萔���⏕�ȖڃR�[�h
		bean.setSHH_TESU_UKM_CODE(rs.getString("SHH_TESU_UKM_CODE")); // �萔������ȖڃR�[�h
		bean.setSHH_TESU_DEP_CODE(rs.getString("SHH_TESU_DEP_CODE")); // �萔���v�㕔��R�[�h
		bean.setSHH_TESU_ZEI_CODE(rs.getString("SHH_TESU_ZEI_CODE")); // �萔������ŃR�[�h
		bean.setSHH_GS_PRINT_KBN(rs.getString("SHH_GS_PRINT_KBN")); // �O�������쐬�t���O
		bean.setSHH_SEI_NO_KBN(rs.getString("SHH_SEI_NO_KBN")); // �������ԍ��t���O

		bean.setFbOutputPath(rs.getString("SHH_FB_PATH"));
		bean.setRemmitanceOutputPath(rs.getString("SHH_REM_PATH"));
		bean.setEmployeeFbOutputPath(rs.getString("SHH_EMP_FB_PATH"));

		return bean;
	}

	/**
	 * @return �G���e�B�e�B
	 */
	protected PaymentPolicy createPaymentPolicy() {
		return new PaymentPolicy();
	}

}