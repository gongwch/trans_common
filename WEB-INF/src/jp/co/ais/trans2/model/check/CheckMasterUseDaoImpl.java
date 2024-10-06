package jp.co.ais.trans2.model.check;

import java.sql.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.CheckCondition.CHECK_TYPE;
import jp.co.ais.trans2.model.company.*;

/**
 * �}�X�^�g�p�ς݃`�F�b�N����
 */
public class CheckMasterUseDaoImpl extends TModel implements CheckMasterUseDao {

	/**
	 * �}�X�^�g�p�ς݃`�F�b�N
	 * 
	 * @param condition �`�F�b�N����
	 * @throws TException
	 */
	public void check(CheckCondition condition) throws TException {

		if (!ServerConfig.isFlagOn("trans.master.check.used")) {
			return;
		}

		boolean result = false;
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			// �����x�������ȊO�͎d�󖾍ׂŃ`�F�b�N
			if (condition.getType() != CheckCondition.CHECK_TYPE.PAYMENT_SETTING && checkUseSlipDetail(conn, condition)) {
				result = true;
			}

			// �Ȗڂ̏ꍇ�͉ȖڏW�v�}�X�^���g�p�ς݃`�F�b�N
			else if (condition.getType() == CheckCondition.CHECK_TYPE.ITEM && checkUseItemSummary(conn, condition)) {
				result = true;
			}

			// �����x������
			else if (condition.getType() == CheckCondition.CHECK_TYPE.PAYMENT_SETTING
				&& checkUseAPHeader(conn, condition)) {
				result = true;
			}

			if (result) {
				// true:�g�p�ς݁Afalse:���g�p
				String msg = getMessage(condition.getType());
				throw new TException(msg);
			}

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �\�����b�Z�[�W�̎擾
	 * 
	 * @param type
	 * @return �\�����b�Z�[�W
	 */
	protected String getMessage(int type) {

		// I00720">�w���{0}�͊��ɗ��p����Ă���ׁA�폜�ł��܂���B

		AccountConfig conf = getCompany().getAccountConfig();

		switch (type) {
			case CHECK_TYPE.ITEM:
				return getMessage("I00720", conf.getItemName());

			case CHECK_TYPE.SUB_ITEM:
				return getMessage("I00720", conf.getSubItemName());

			case CHECK_TYPE.DETAIL_ITEM:
				return getMessage("I00720", conf.getDetailItemName());

			case CHECK_TYPE.DEPARTMENT:
				return getMessage("I00720", "C00863"); // �v�㕔��

			case CHECK_TYPE.CUSTOMER:
				return getMessage("I00720", "C00408"); // �����

			case CHECK_TYPE.PAYMENT_SETTING:
				return getMessage("I00720", "C10756"); // �����x������

			case CHECK_TYPE.EMPLOYEE:
				return getMessage("I00720", "C00246"); // �Ј�

			case CHECK_TYPE.MANAGEMENT_1:
				return getMessage("I00720", conf.getManagement2Name());

			case CHECK_TYPE.MANAGEMENT_2:
				return getMessage("I00720", conf.getManagement2Name());

			case CHECK_TYPE.MANAGEMENT_3:
				return getMessage("I00720", conf.getManagement3Name());

			case CHECK_TYPE.MANAGEMENT_4:
				return getMessage("I00720", conf.getManagement4Name());

			case CHECK_TYPE.MANAGEMENT_5:
				return getMessage("I00720", conf.getManagement5Name());

			case CHECK_TYPE.MANAGEMENT_6:
				return getMessage("I00720", conf.getManagement6Name());

		}

		return "";
	}

	/**
	 * �d��W���[�i��(SWK_DTL)�g�p�`�F�b�N
	 * 
	 * @param conn
	 * @param condition
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean checkUseSlipDetail(Connection conn, CheckCondition condition) throws Exception {
		boolean isUse = false;

		SQLCreator sql = new SQLCreator();
		sql.add("SELECT 1 FROM SWK_DTL");

		sql.add("WHERE KAI_CODE = ?", condition.getCompanyCode());

		if (!Util.isNullOrEmpty(condition.getItemCode())) {
			sql.add("  AND SWK_KMK_CODE = ?", condition.getItemCode());
		}

		if (!Util.isNullOrEmpty(condition.getSubItemCode())) {
			sql.add("  AND SWK_HKM_CODE = ?", condition.getSubItemCode());
		}

		if (!Util.isNullOrEmpty(condition.getDetailItemCode())) {
			sql.add("  AND SWK_UKM_CODE = ?", condition.getDetailItemCode());
		}

		if (!Util.isNullOrEmpty(condition.getDepartmentCode())) {
			sql.add("  AND SWK_DEP_CODE = ?", condition.getDepartmentCode());
		}

		if (!Util.isNullOrEmpty(condition.getCustomerCode())) {
			sql.add("  AND SWK_TRI_CODE = ?", condition.getCustomerCode());
		}

		if (!Util.isNullOrEmpty(condition.getEmployeeCode())) {
			sql.add("  AND SWK_EMP_CODE = ?", condition.getEmployeeCode());
		}

		if (!Util.isNullOrEmpty(condition.getManagement1Code())) {
			sql.add("  AND SWK_KNR_CODE_1 = ?", condition.getManagement1Code());
		}

		if (!Util.isNullOrEmpty(condition.getManagement2Code())) {
			sql.add("  AND SWK_KNR_CODE_2 = ?", condition.getManagement2Code());
		}

		if (!Util.isNullOrEmpty(condition.getManagement3Code())) {
			sql.add("  AND SWK_KNR_CODE_3 = ?", condition.getManagement3Code());
		}

		if (!Util.isNullOrEmpty(condition.getManagement4Code())) {
			sql.add("  AND SWK_KNR_CODE_4 = ?", condition.getManagement4Code());
		}

		if (!Util.isNullOrEmpty(condition.getManagement5Code())) {
			sql.add("  AND SWK_KNR_CODE_5 = ?", condition.getManagement5Code());
		}

		if (!Util.isNullOrEmpty(condition.getManagement6Code())) {
			sql.add("  AND SWK_KNR_CODE_6 = ?", condition.getManagement6Code());
		}

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.selectLimit(statement, sql, 1);

		if (rs.next()) {
			isUse = true;
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return isUse;
	}

	/**
	 * �ȖڏW�v�}�X�^(KMK_SUM_MST)�g�p�`�F�b�N
	 * 
	 * @param conn
	 * @param condition
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean checkUseItemSummary(Connection conn, CheckCondition condition) throws Exception {
		boolean isUse = false;

		SQLCreator sql = new SQLCreator();
		sql.add("SELECT 1 FROM KMK_SUM_MST");
		sql.add("WHERE  KAI_CODE = ?", condition.getCompanyCode());
		sql.add("AND   (KMK_CODE = ?   ", condition.getItemCode());
		sql.add("    OR SUM_CODE = ? ) ", condition.getItemCode());

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.selectLimit(statement, sql, 1);

		if (rs.next()) {
			isUse = true;
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return isUse;
	}

	/**
	 * AP�d��w�b�_�[(AP_SWK_HDR)�g�p�`�F�b�N
	 * 
	 * @param conn
	 * @param condition
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean checkUseAPHeader(Connection conn, CheckCondition condition) throws Exception {
		boolean isUse = false;

		SQLCreator sql = new SQLCreator();
		sql.add("SELECT 1 FROM AP_SWK_HDR");
		sql.add(" WHERE KAI_CODE = ? ", condition.getCompanyCode());
		sql.add(" AND   SWK_TRI_CODE = ? ", condition.getCustomerCode());
		sql.add(" AND   SWK_TJK_CODE = ? ", condition.getPaymentSettingCode());

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.selectLimit(statement, sql, 1);

		if (rs.next()) {
			isUse = true;
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return isUse;
	}
}
