package jp.co.ais.trans2.model.customer;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.define.TransUtil;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �������̎����N���X
 * 
 * @author AIS
 */
public class CustomerDaoImpl extends TModel implements CustomerDao {

	/** true:�O���[�v��Ћ敪�L�� */
	protected boolean isUseTRI_TYPE_GRP_FLG = ServerConfig.isFlagOn("trans.kt.MG0150.group.comp.flag");

	/** true:�O���[�v��Ћ敪�L�� */
	protected boolean isUseTRI_TYPE_PSN_FLG = ServerConfig.isFlagOn("trans.kt.MG0150.tri.person.flag");

	/** �����̌h��/�S������/�S���҂Ȃǂ̐ݒ��\�����邩�ǂ��� true:�\������ */
	protected boolean isUseCustomerManagementSet = ServerConfig.isFlagOn("trans.usr.customer.managementi.setting");

	public List<Customer> get(CustomerSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Customer> list = new ArrayList<Customer>();

		try {

			if (checkCount(conn, condition) == 0) {
				return list;
			}

			String sql = getSQL(condition);

			sql = sql +
					" ORDER BY " +
					" tri.TRI_CODE ";

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
	 * �w������ɊY�������������Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> getRef(CustomerSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Customer> list = new ArrayList<Customer>();

		try {

			if (checkCount(conn, condition) == 0) {
				return list;
			}

			String sql = getSQL(condition);

			if (condition.getCompanyCodeList() != null && !condition.getCompanyCodeList().isEmpty()) {
				sql += " AND tri.KAI_CODE IN " + SQLUtil.getInStatement(condition.getCompanyCodeList());
			}

			sql = sql + " ORDER BY tri.TRI_CODE ";
			sql = sql + "         ,CASE WHEN tri.KAI_CODE = " + SQLUtil.getParam(getCompanyCode());
			sql = sql + "               THEN 0 ELSE 1 END ";
			sql = sql + "         ,tri.KAI_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Customer bean = mapping(rs);

				if (Util.isNullOrEmpty(currentCode)) {
					// ������
					currentCode = bean.getCode();
				} else if (Util.equals(currentCode, bean.getCode())) {
					// �R�[�h�������ꍇ�ɏ����s�v
					continue;
				}

				// �R�[�h�ޔ�
				currentCode = bean.getCode();

				// �߂�l���X�g����
				list.add(bean);
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
	 * �`�F�b�N
	 * 
	 * @param conn
	 * @param condition
	 * @return ����
	 * @throws TException
	 */
	protected int checkCount(Connection conn, CustomerSearchCondition condition) throws TException {

		// �������`�F�b�N����
		SQLCreator sql = new VCreator();
		sql.add(" SELECT COUNT(1) FROM ");

		if (condition.isSearchSumCode()) {
			// �W�v����挟��
			sql.add(" ( SELECT DISTINCT ");
			sql.add("          tri1.KAI_CODE, ");
			sql.add("          tri1.SUM_CODE TRI_CODE, ");
			sql.add("          triSUM.NYU_TESU_KBN ,");
			sql.add("          triSUM.SPOT_DEN_NO,");
			sql.add("          triSUM.IRAI_NAME,");
			sql.add("          triSUM.TRI_KBN,");
			sql.add("          triSUM.SUM_CODE,");
			sql.add("          triSUM.FAX,");
			sql.add("          triSUM.TEL,");
			sql.add("          triSUM.JYU_1,");
			sql.add("          triSUM.JYU_2,");
			sql.add("          triSUM.JYU_KANA,");
			sql.add("          triSUM.ZIP,");
			sql.add("          triSUM.TRI_NAME, ");
			sql.add("          triSUM.TRI_NAME_S, ");
			sql.add("          triSUM.TRI_NAME_K, ");
			sql.add("          triSUM.JIG_NAME, ");
			sql.add("          triSUM.NJ_C_DATE, ");
			sql.add("          triSUM.NJ_R_MON, ");
			sql.add("          triSUM.NJ_P_DATE, ");
			sql.add("          triSUM.SIIRE_KBN, ");
			sql.add("          triSUM.TOKUI_KBN, ");
			sql.add("          triSUM.NKN_CBK_CODE, ");
			sql.add("          triSUM.STR_DATE, ");
			sql.add("          triSUM.END_DATE, ");
			sql.add("          triSUM.TRI_TYPE_CHTR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_ONR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_AGNT_FLG, ");
			sql.add("          triSUM.TRI_TYPE_SPLR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BRKR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BANK_FLG, "); // BANK
			sql.add("          triSUM.TRI_TYPE_OZR_FLG, ");
			sql.add("          triSUM.COU_CODE, "); // ���R�[�h
			sql.add("          triSUM.EMAIL_ADDRESS, ");
			sql.add("          triSUM.TRI_TYPE_SHPP_FLG, ");
			sql.add("          triSUM.TRI_TYPE_CONS_FLG, ");
			sql.add("          triSUM.TRI_TYPE_NTPT_FLG, ");
			sql.add("          triSUM.TRI_TYPE_FWD_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BKTR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BKSP_FLG ");

			if (isUseTRI_TYPE_GRP_FLG) {
				sql.add("          , triSUM.TRI_TYPE_GRP_FLG ");
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				sql.add("          , triSUM.TRI_TYPE_PSN_FLG ");
			}

			sql.add(" FROM ");
			sql.add(" TRI_MST tri1 ");
			sql.add(" LEFT JOIN TRI_MST triSUM ON tri1.KAI_CODE = triSUM.KAI_CODE ");
			sql.add("                         AND tri1.SUM_CODE = triSUM.TRI_CODE ");
			sql.add(" WHERE tri1.SUM_CODE IS NOT NULL) tri ");

		} else {
			// ����挟��
			sql.add(" TRI_MST tri ");
			sql.add(" LEFT OUTER JOIN TRI_MST tri2 ON tri.KAI_CODE = tri2.KAI_CODE");
			sql.add("                             AND tri.SUM_CODE = tri2.TRI_CODE");
		}

		sql.add(" WHERE 1 = 1 ");

		// WHERE�����ǉ�
		String chk = addWhere(condition, sql.toSQL());

		int count = DBUtil.selectOneInt(conn, chk);
		return count;
	}

	/**
	 * @param condition
	 * @return SQL
	 */
	protected String getSQL(CustomerSearchCondition condition) {

		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		SQLCreator sql = new VCreator();
		sql.add(" SELECT tri.KAI_CODE, ");
		sql.add("        tri.TRI_CODE, ");
		sql.add("        tri.TRI_NAME, ");
		sql.add("        tri.TRI_NAME_S, ");
		sql.add("        tri.TRI_NAME_K, ");
		sql.add("        tri.JIG_NAME, ");
		sql.add("        tri.ZIP, ");
		sql.add("        tri.JYU_KANA, ");
		sql.add("        tri.JYU_1, ");
		sql.add("        tri.JYU_2, ");
		sql.add("        tri.TEL, ");
		sql.add("        tri.FAX, ");
		sql.add("        tri.SUM_CODE, ");
		if (condition.isSearchSumCode()) {
			sql.add("        NULL SUM_NAME,");
		} else {
			sql.add("        tri2.TRI_NAME_S SUM_NAME,");
		}

		sql.add("        tri.SIIRE_KBN, ");
		sql.add("        tri.TOKUI_KBN, ");
		sql.add("        tri.NJ_C_DATE, ");
		sql.add("        tri.NJ_R_MON, ");
		sql.add("        tri.NJ_P_DATE, ");
		sql.add("        tri.NKN_CBK_CODE, ");
		sql.add("        tri.TRI_KBN, ");
		sql.add("        tri.IRAI_NAME, ");
		sql.add("        tri.SPOT_DEN_NO, ");
		sql.add("        tri.NYU_TESU_KBN, ");
		sql.add("        tri.STR_DATE, ");
		sql.add("        tri.END_DATE, ");
		sql.add("        tri.TRI_TYPE_CHTR_FLG, ");
		sql.add("        tri.TRI_TYPE_ONR_FLG, ");
		sql.add("        tri.TRI_TYPE_AGNT_FLG, ");
		sql.add("        tri.TRI_TYPE_SPLR_FLG, ");
		sql.add("        tri.TRI_TYPE_BRKR_FLG, ");
		sql.add("        tri.TRI_TYPE_BANK_FLG, "); // Bank Flag
		sql.add("        tri.TRI_TYPE_OZR_FLG, ");
		sql.add("        tri.EMAIL_ADDRESS, ");
		sql.add("        tri.TRI_TYPE_SHPP_FLG, ");
		sql.add("        tri.TRI_TYPE_CONS_FLG, ");
		sql.add("        tri.TRI_TYPE_NTPT_FLG, ");
		sql.add("        tri.TRI_TYPE_FWD_FLG, ");
		sql.add("        tri.TRI_TYPE_BKTR_FLG, ");
		sql.add("        tri.TRI_TYPE_BKSP_FLG, ");

		if (isUseTRI_TYPE_GRP_FLG) {
			sql.add("        tri.TRI_TYPE_GRP_FLG, ");
		}
		if (isUseTRI_TYPE_PSN_FLG) {
			sql.add("        tri.TRI_TYPE_PSN_FLG, ");
		}
		if (isUseCustomerManagementSet) {
			// AR�������̒S���E�h��
			sql.add(" tri.CHARGE_DEP_NAME, "); // �S��������
			sql.add(" tri.CHARGE_EMP_NAME, "); // �S���Җ�
			sql.add(" tri.TRI_TITLE_TYPE, "); // �����h��
			sql.add(" tri.EMP_TITLE_TYPE, "); // �S���Ҍh��
		}

		if (isInvoice) {
			sql.add(" tri.NO_INV_REG_FLG, "); // ��K�i�������t���O
			sql.add(" tri.NO_INV_REG_ZEI_CODE, "); // ��K�i����������ŃR�[�h
			sql.add(" zei.ZEI_NAME_S NO_INV_REG_ZEI_NAME, "); // ��K�i����������Ŗ���
			sql.add(" tri.INV_REG_NO, "); // �K�i���������s���Ǝғo�^�ԍ�
		}

		sql.add("        cou.COU_CODE, "); // ���R�[�h
		sql.add("        cou.COU_CODE2,"); // ���R�[�h2
		sql.add("        cou.COU_NO,"); // ���ԍ�
		sql.add("        cou.COU_NAME,"); // ����
		sql.add("        cou.COU_NAME2,"); // ����2
		// ��s����
		sql.add("        cbk.CBK_CBK_CODE, "); // ��s�����R�[�h
		sql.add("        cbk.CBK_NAME, "); // ��s��������
		sql.add("        cbk.CUR_CODE, "); // �ʉ݃R�[�h
		sql.add("        cbk.CBK_BNK_CODE, "); // ��s�R�[�h
		sql.add("        cbk.CBK_STN_CODE, "); // �x�X�R�[�h
		sql.add("        cbk.CBK_IRAI_EMP_CODE, "); // �U���˗��l�R�[�h
		sql.add("        cbk.CBK_IRAI_NAME, "); // �U���˗��l��
		sql.add("        cbk.CBK_IRAI_NAME_J, "); // �U���˗��l���i�����j
		sql.add("        cbk.CBK_IRAI_NAME_E, "); // �U���˗��l���i�p���j
		sql.add("        cbk.CBK_YKN_KBN, "); // �a�����
		sql.add("        cbk.CBK_YKN_NO, "); // �����ԍ�
		sql.add("        cbk.CBK_EMP_FB_KBN, "); // �Ј��e�a�敪
		sql.add("        cbk.CBK_OUT_FB_KBN, "); // �ЊO�e�a�敪
		sql.add("        cbk.CBK_DEP_CODE, "); // �v�㕔��R�[�h
		sql.add("        cbk.CBK_KMK_CODE, "); // �ȖڃR�[�h
		sql.add("        cbk.CBK_HKM_CODE, "); // �⏕�ȖڃR�[�h
		sql.add("        cbk.CBK_UKM_CODE, "); // ����ȖڃR�[�h
		// ��s���x�X��1
		sql.add("        bnk.BNK_NAME_S  BNK_NAME, "); // ��s��1
		sql.add("        bnk.BNK_STN_NAME_S  BNK_STN_NAME "); // �x�X��1
		sql.add(" FROM ");

		if (condition.isSearchSumCode()) {
			// �W�v����挟��
			sql.add(" ( SELECT DISTINCT ");
			sql.add("          tri1.KAI_CODE, ");
			sql.add("          tri1.SUM_CODE TRI_CODE, ");
			sql.add("          triSUM.NYU_TESU_KBN ,");
			sql.add("          triSUM.SPOT_DEN_NO,");
			sql.add("          triSUM.IRAI_NAME,");
			sql.add("          triSUM.TRI_KBN,");
			sql.add("          triSUM.SUM_CODE,");
			sql.add("          triSUM.FAX,");
			sql.add("          triSUM.TEL,");
			sql.add("          triSUM.JYU_1,");
			sql.add("          triSUM.JYU_2,");
			sql.add("          triSUM.JYU_KANA,");
			sql.add("          triSUM.ZIP,");
			sql.add("          triSUM.TRI_NAME, ");
			sql.add("          triSUM.TRI_NAME_S, ");
			sql.add("          triSUM.TRI_NAME_K, ");
			sql.add("          triSUM.JIG_NAME, ");
			sql.add("          triSUM.NJ_C_DATE, ");
			sql.add("          triSUM.NJ_R_MON, ");
			sql.add("          triSUM.NJ_P_DATE, ");
			sql.add("          triSUM.SIIRE_KBN, ");
			sql.add("          triSUM.TOKUI_KBN, ");
			sql.add("          triSUM.NKN_CBK_CODE, ");
			sql.add("          triSUM.STR_DATE, ");
			sql.add("          triSUM.END_DATE, ");
			sql.add("          triSUM.TRI_TYPE_CHTR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_ONR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_AGNT_FLG, ");
			sql.add("          triSUM.TRI_TYPE_SPLR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BRKR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BANK_FLG, "); // Bank Flag
			sql.add("          triSUM.TRI_TYPE_OZR_FLG, ");
			sql.add("          triSUM.COU_CODE, "); // ���R�[�h
			sql.add("          triSUM.EMAIL_ADDRESS, ");
			sql.add("          triSUM.TRI_TYPE_SHPP_FLG, ");
			sql.add("          triSUM.TRI_TYPE_CONS_FLG, ");
			sql.add("          triSUM.TRI_TYPE_NTPT_FLG, ");
			sql.add("          triSUM.TRI_TYPE_FWD_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BKTR_FLG, ");
			sql.add("          triSUM.TRI_TYPE_BKSP_FLG ");

			if (isUseTRI_TYPE_GRP_FLG) {
				sql.add("          , triSUM.TRI_TYPE_GRP_FLG ");
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				sql.add("          , triSUM.TRI_TYPE_PSN_FLG ");
			}
			if (isUseCustomerManagementSet) {
				// AR�������̒S���E�h��
				sql.add(" , triSUM.CHARGE_DEP_NAME "); // �S��������
				sql.add(" , triSUM.CHARGE_EMP_NAME "); // �S���Җ�
				sql.add(" , triSUM.TRI_TITLE_TYPE "); // �����h��
				sql.add(" , triSUM.EMP_TITLE_TYPE "); // �S���Ҍh��
			}
			if (isInvoice) {
				sql.add(" , triSUM.NO_INV_REG_FLG "); // ��K�i�������t���O
				sql.add(" , triSUM.NO_INV_REG_ZEI_CODE "); // ��K�i����������ŃR�[�h
				sql.add(" , triSUM.INV_REG_NO "); // �K�i���������s���Ǝғo�^�ԍ�
			}

			sql.add(" FROM ");
			sql.add(" TRI_MST tri1 ");
			sql.add(" LEFT JOIN TRI_MST triSUM ON tri1.KAI_CODE = triSUM.KAI_CODE ");
			sql.add("                         AND tri1.SUM_CODE = triSUM.TRI_CODE ");
			sql.add(" WHERE tri1.SUM_CODE IS NOT NULL) tri ");
		} else {
			// ����挟��
			sql.add(" TRI_MST tri ");
			sql.add(" LEFT OUTER JOIN TRI_MST tri2 ON tri.KAI_CODE = tri2.KAI_CODE");
			sql.add("                             AND tri.SUM_CODE = tri2.TRI_CODE");
		}

		sql.add(" LEFT OUTER JOIN  AP_CBK_MST cbk  ON tri.KAI_CODE     = cbk.KAI_CODE");
		sql.add("                                 AND tri.NKN_CBK_CODE = cbk.CBK_CBK_CODE");
		sql.add(" LEFT OUTER JOIN  BNK_MST bnk     ON cbk.CBK_BNK_CODE = bnk.BNK_CODE");
		sql.add("                                 AND cbk.CBK_STN_CODE = bnk.BNK_STN_CODE");
		sql.add(" LEFT OUTER JOIN  COUNTRY_MST cou ON tri.COU_CODE = cou.COU_CODE");

		if (isInvoice) {

			sql.add(" LEFT OUTER JOIN  SZEI_MST zei  ON tri.KAI_CODE = zei.KAI_CODE");
			sql.add("                               AND tri.NO_INV_REG_ZEI_CODE = zei.ZEI_CODE");
		}

		sql.add(" WHERE 1 = 1 ");

		// WHERE�����ǉ�
		String s = addWhere(condition, sql.toSQL());
		return s;
	}

	/**
	 * �w������ɊY�������������Ԃ�<br>
	 * �C���N�������g�T�[�`�p
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> getCustomerForSearch(CustomerSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<Customer> list = new ArrayList<Customer>();

		try {

			// �������`�F�b�N����
			VCreator chk = new VCreator();
			chk.add(" SELECT COUNT(1) FROM ");
			chk.add(" TRI_MST tri ");
			chk.add(" WHERE 1 = 1 ");
			chk.add(addWhere(condition, "")); // WHERE�����ǉ�

			int count = DBUtil.selectOneInt(conn, chk.toSQL());
			if (count == 0) {
				return list;
			}

			VCreator sql = new VCreator();
			sql.add(" SELECT tri.KAI_CODE ");
			sql.add("       ,tri.TRI_CODE ");
			sql.add("       ,tri.TRI_NAME ");
			sql.add("       ,tri.TRI_NAME_S ");
			sql.add("       ,tri.TRI_NAME_K ");
			sql.add("       ,tri.JIG_NAME ");
			sql.add("       ,tri.ZIP ");
			sql.add("       ,tri.JYU_KANA ");
			sql.add("       ,tri.JYU_1 ");
			sql.add("       ,tri.JYU_2 ");
			sql.add("       ,tri.TEL ");
			sql.add("       ,tri.FAX ");
			sql.add("       ,tri.SUM_CODE ");
			sql.add("       ,tri.SIIRE_KBN ");
			sql.add("       ,tri.TOKUI_KBN ");
			sql.add("       ,tri.NJ_C_DATE ");
			sql.add("       ,tri.NJ_R_MON ");
			sql.add("       ,tri.NJ_P_DATE ");
			sql.add("       ,tri.NKN_CBK_CODE ");
			sql.add("       ,tri.TRI_KBN ");
			sql.add("       ,tri.IRAI_NAME ");
			sql.add("       ,tri.SPOT_DEN_NO ");
			sql.add("       ,tri.NYU_TESU_KBN ");
			sql.add("       ,tri.STR_DATE ");
			sql.add("       ,tri.END_DATE ");
			sql.add("       ,tri.TRI_TYPE_CHTR_FLG ");
			sql.add("       ,tri.TRI_TYPE_ONR_FLG ");
			sql.add("       ,tri.TRI_TYPE_AGNT_FLG ");
			sql.add("       ,tri.TRI_TYPE_SPLR_FLG ");
			sql.add("       ,tri.TRI_TYPE_BRKR_FLG ");
			sql.add("       ,tri.TRI_TYPE_BANK_FLG ");// Bank Flag
			sql.add("       ,tri.TRI_TYPE_OZR_FLG ");
			sql.add("       ,tri.COU_CODE ");
			sql.add("       ,tri.NKN_CBK_CODE CBK_CBK_CODE ");
			sql.add("       ,tri.EMAIL_ADDRESS ");
			sql.add("       ,tri.TRI_TYPE_SHPP_FLG ");
			sql.add("       ,tri.TRI_TYPE_CONS_FLG ");
			sql.add("       ,tri.TRI_TYPE_NTPT_FLG ");
			sql.add("       ,tri.TRI_TYPE_FWD_FLG ");
			sql.add("       ,tri.TRI_TYPE_BKTR_FLG ");
			sql.add("       ,tri.TRI_TYPE_BKSP_FLG ");

			if (isUseTRI_TYPE_GRP_FLG) {
				sql.add("       ,tri.TRI_TYPE_GRP_FLG ");
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				sql.add("       ,tri.TRI_TYPE_PSN_FLG ");
			}
			if (isUseCustomerManagementSet) {
				// AR�������̒S���E�h��
				sql.add("       ,tri.CHARGE_DEP_NAME "); // �S��������
				sql.add("       ,tri.CHARGE_EMP_NAME "); // �S���Җ�
				sql.add("       ,tri.TRI_TITLE_TYPE "); // �����h��
				sql.add("       ,tri.EMP_TITLE_TYPE "); // �S���Ҍh��
			}

			sql.add(" FROM   TRI_MST tri ");
			sql.add(" WHERE 1 = 1 ");
			// WHERE�����ǉ�
			sql.add(addWhere(condition, ""));

			sql.add(" ORDER BY tri.TRI_CODE");

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
	 * WHERE�����ǉ�
	 * 
	 * @param condition
	 * @param sql
	 * @return sql
	 */
	protected String addWhere(CustomerSearchCondition condition, String sql) {
		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql = sql + " AND	tri.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql = sql + " AND	tri.TRI_CODE = " + SQLUtil.getParam(condition.getCode());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql = sql + " AND	tri.TRI_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
		}

		// ����
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql = sql + " AND	tri.TRI_NAME_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
		}

		// ��������
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql = sql + " AND	tri.TRI_NAME_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql = sql + " AND	tri.TRI_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql = sql + " AND	tri.TRI_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql = sql +
					" AND	tri.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) +
					" AND	tri.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
		}

		// ���Ӑ�A�d����敪
		if (condition.getType() != null) {
			switch (condition.getType()) {
			case CUSTOMER:
				sql = sql + " AND	tri.TOKUI_KBN = 1 ";
				break;

			case VENDOR:
				sql = sql + " AND	tri.SIIRE_KBN = 1 ";
				break;

			case BOTH:
				if (condition.isCustomerTypeBoth()) {
					sql = sql + " AND	(tri.TOKUI_KBN = 1 AND tri.SIIRE_KBN = 1) ";
				} else {
					sql = sql + " AND	(tri.TOKUI_KBN = 1 OR tri.SIIRE_KBN = 1) ";
				}
				break;
			}
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql = sql +
					" AND	tri.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) +
					" AND	tri.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
		}

		// �����R�[�h(����)
		if (condition.getCustomerCodeList() != null && condition.getCustomerCodeList().length != 0) {
			sql = sql + " AND	tri.TRI_CODE IN " + SQLUtil.getInStatement(condition.getCustomerCodeList());
		}
		// CHARTERER
		if (condition.isCharterer() != null) {
			sql = sql + " AND   tri.TRI_TYPE_CHTR_FLG = " + BooleanUtil.toInt(condition.isCharterer());
		}
		// OWNER
		if (condition.isOwner() != null) {
			sql = sql + " AND   tri.TRI_TYPE_ONR_FLG = " + BooleanUtil.toInt(condition.isOwner());
		}
		// PORT ANGENT
		if (condition.isPortAgent() != null) {
			sql = sql + " AND   tri.TRI_TYPE_AGNT_FLG = " + BooleanUtil.toInt(condition.isPortAgent());
		}
		// SUPPLIER
		if (condition.isSupplier() != null) {
			sql = sql + " AND   tri.TRI_TYPE_SPLR_FLG = " + BooleanUtil.toInt(condition.isSupplier());
		}
		// BROKER
		if (condition.isBroker() != null) {
			sql = sql + " AND   tri.TRI_TYPE_BRKR_FLG = " + BooleanUtil.toInt(condition.isBroker());
		}
		// BANK
		if (condition.isBank() != null) {
			sql = sql + " AND   tri.TRI_TYPE_BANK_FLG = " + BooleanUtil.toInt(condition.isBank());
		}
		// OTHER
		if (condition.isOther() != null) {
			sql = sql + " AND   tri.TRI_TYPE_OZR_FLG = " + BooleanUtil.toInt(condition.isOther());
		}
		// Shipper
		if (condition.isShipper() != null) {
			sql = sql + " AND   tri.TRI_TYPE_SHPP_FLG = " + BooleanUtil.toInt(condition.isShipper());
		}
		// Consignee
		if (condition.isConsignee() != null) {
			sql = sql + " AND   tri.TRI_TYPE_CONS_FLG = " + BooleanUtil.toInt(condition.isConsignee());
		}
		// Notify party
		if (condition.isNotifyParty() != null) {
			sql = sql + " AND   tri.TRI_TYPE_NTPT_FLG = " + BooleanUtil.toInt(condition.isNotifyParty());
		}
		// Fowarder
		if (condition.isFowarder() != null) {
			sql = sql + " AND   tri.TRI_TYPE_FWD_FLG = " + BooleanUtil.toInt(condition.isFowarder());
		}
		// Bunker Trader
		if (condition.isBunkerTrader() != null) {
			sql = sql + " AND   tri.TRI_TYPE_BKTR_FLG = " + BooleanUtil.toInt(condition.isBunkerTrader());
		}
		// Bunker Supplier
		if (condition.isBunkerSupplier() != null) {
			sql = sql + " AND   tri.TRI_TYPE_BKSP_FLG = " + BooleanUtil.toInt(condition.isBunkerSupplier());
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql += " AND  (tri.INP_DATE > " + SQLUtil.getYMDHMSParam(condition.getLastUpdateDate());
			sql += "    OR tri.UPD_DATE > " + SQLUtil.getYMDHMSParam(condition.getLastUpdateDate());
			sql += ")";
		}
		return sql;
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CustomerSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   TRI_MST tri ");
			sql.add(" WHERE  tri.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (tri.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR tri.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR tri.INP_DATE IS NULL AND tri.UPD_DATE IS NULL) ");
			}

			// �폜���聁���ݎ����Ă��錏����DB�̉ߋ��̌����ƕs��v
			hasDelete = DBUtil.selectOneInt(conn, sql.toSQL()) != condition.getNowCount();

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return hasDelete;
	}

	public void entry(Customer customer) throws TException {
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new VCreator();
			sql.add(" INSERT INTO TRI_MST ( ");
			sql.add(" KAI_CODE, ");
			sql.add(" TRI_CODE, ");
			sql.add(" TRI_NAME, ");
			sql.add(" TRI_NAME_S, ");
			sql.add(" TRI_NAME_K, ");
			sql.add(" ZIP, ");
			sql.add(" JYU_KANA, ");
			sql.add(" JYU_1, ");
			sql.add(" JYU_2, ");
			sql.add(" TEL, ");
			sql.add(" FAX, ");
			sql.add(" SUM_CODE, ");
			sql.add(" SIIRE_KBN, ");
			sql.add(" TOKUI_KBN, ");
			sql.add(" NJ_C_DATE, ");
			sql.add(" NJ_R_MON, ");
			sql.add(" NJ_P_DATE, ");
			sql.add(" NKN_CBK_CODE, ");
			sql.add(" JIG_NAME, ");
			sql.add(" IRAI_NAME, ");
			sql.add(" NYU_TESU_KBN, ");
			sql.add(" STR_DATE, ");
			sql.add(" END_DATE, ");
			sql.add(" INP_DATE, ");
			sql.add(" PRG_ID, ");
			sql.add(" USR_ID, ");
			sql.add(" TRI_TYPE_CHTR_FLG, ");
			sql.add(" TRI_TYPE_ONR_FLG, ");
			sql.add(" TRI_TYPE_AGNT_FLG, ");
			sql.add(" TRI_TYPE_SPLR_FLG, ");
			sql.add(" TRI_TYPE_BRKR_FLG, ");
			sql.add(" TRI_TYPE_BANK_FLG, "); // BANK Flag
			sql.add(" TRI_TYPE_OZR_FLG, ");
			sql.add(" COU_CODE, ");
			sql.add(" EMAIL_ADDRESS, ");
			sql.add(" TRI_TYPE_SHPP_FLG, ");
			sql.add(" TRI_TYPE_CONS_FLG, ");
			sql.add(" TRI_TYPE_NTPT_FLG, ");
			sql.add(" TRI_TYPE_FWD_FLG, ");
			sql.add(" TRI_TYPE_BKTR_FLG, ");
			sql.add(" TRI_TYPE_BKSP_FLG ");
			if (isUseTRI_TYPE_GRP_FLG) {
				sql.add(" , TRI_TYPE_GRP_FLG ");
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				sql.add(" , TRI_TYPE_PSN_FLG ");
			}
			if (isUseCustomerManagementSet) {
				// AR�������̒S���E�h��
				sql.add(" , CHARGE_DEP_NAME "); // �S��������
				sql.add(" , CHARGE_EMP_NAME "); // �S���Җ�
				sql.add(" , TRI_TITLE_TYPE "); // �����h��
				sql.add(" , EMP_TITLE_TYPE "); // �S���Ҍh��
			}

			if (isInvoice) {
				sql.add(" , NO_INV_REG_FLG "); // ��K�i�������t���O
				sql.add(" , NO_INV_REG_ZEI_CODE "); // ��K�i����������ŃR�[�h
				sql.add(" , INV_REG_NO "); // �K�i���������s���Ǝғo�^�ԍ�
			}
			sql.add(" ) VALUES (");
			sql.add("  ? ", customer.getCompanyCode());
			sql.add(" ,? ", customer.getCode());
			sql.add(" ,? ", customer.getName());
			sql.add(" ,? ", customer.getNames());
			sql.add(" ,? ", customer.getNamek());
			sql.add(" ,? ", customer.getZipCode());
			sql.add(" ,? ", customer.getAddressKana());
			sql.add(" ,? ", customer.getAddress());
			sql.add(" ,? ", customer.getAddress2());
			sql.add(" ,? ", customer.getTel());
			sql.add(" ,? ", customer.getFax());
			sql.add(" ,? ", customer.getSumCode());
			sql.add(" ,? ", customer.getPurchase());
			sql.add(" ,? ", customer.getClient());
			sql.add(" ,? ", customer.getCloseDay());
			sql.add(" ,? ", customer.getNextMonth());
			sql.add(" ,? ", customer.getCashDay());
			sql.add(" ,? ", customer.getBankAccountCode());
			sql.add(" ,? ", customer.getOfficeName());
			sql.add(" ,? ", customer.getClientName());
			sql.add(" ,? ", customer.getCashInFeeType().value);
			sql.adt(" ,? ", customer.getDateFrom());
			sql.adt(" ,? ", customer.getDateTo());
			sql.adt(" ,? ", getNow());
			sql.add(" ,? ", getProgramCode());
			sql.add(" ,? ", getUserCode());
			sql.add(" ,? ", BooleanUtil.toInt(customer.isCharterer()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isOwner()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isPortAgent()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isSupplier()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isBroker()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isBank()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isOther()));
			sql.add(" ,? ", customer.getCountryCode());
			sql.add(" ,? ", customer.getEmailAddress());
			sql.add(" ,? ", BooleanUtil.toInt(customer.isShipper()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isConsignee()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isNotifyParty()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isFowarder()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isBunkerTrader()));
			sql.add(" ,? ", BooleanUtil.toInt(customer.isBunkerSupplier()));

			if (isUseTRI_TYPE_GRP_FLG) {
				sql.add(" ,? ", BooleanUtil.toInt(customer.isGroupCompany()));
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				sql.add(" ,? ", BooleanUtil.toInt(customer.isPersonalCustomer()));
			}
			if (isUseCustomerManagementSet) {
				int cus = customer.getTRI_TITLE_TYPE() != null ? customer.getTRI_TITLE_TYPE().value : 0;
				int emp = customer.getEMP_TITLE_TYPE() != null ? customer.getEMP_TITLE_TYPE().value : 0;

				// AR�������̒S���E�h��
				sql.add(" ,? ", customer.getCHARGE_DEP_NAME()); // �S��������
				sql.add(" ,? ", customer.getCHARGE_EMP_NAME()); // �S���Җ�
				sql.add(" ,? ", cus); // �����h��
				sql.add(" ,? ", emp); // �S���Ҍh��
			}

			if (isInvoice) {
				sql.add(" ,? ", BooleanUtil.toInt(customer.isNO_INV_REG_FLG())); // ��K�i�������t���O
				sql.add(" ,? ", customer.getNO_INV_REG_ZEI_CODE()); // ��K�i����������ŃR�[�h
				sql.add(" ,? ", customer.getINV_REG_NO()); // �K�i���������s���Ǝғo�^�ԍ�
			}

			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Customer customer) throws TException {
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new VCreator();
			sql.add(" UPDATE TRI_MST ");
			sql.add(" SET ");
			sql.add("  TRI_NAME          = ? ", customer.getName());
			sql.add(" ,TRI_NAME_S        = ? ", customer.getNames());
			sql.add(" ,TRI_NAME_K        = ? ", customer.getNamek());
			sql.add(" ,JIG_NAME          = ? ", customer.getOfficeName());
			sql.add(" ,ZIP               = ? ", customer.getZipCode());
			sql.add(" ,TEL               = ? ", customer.getTel());
			sql.add(" ,FAX               = ? ", customer.getFax());
			sql.add(" ,JYU_1             = ? ", customer.getAddress());
			sql.add(" ,JYU_2             = ? ", customer.getAddress2());
			sql.add(" ,JYU_KANA          = ? ", customer.getAddressKana());
			sql.add(" ,NKN_CBK_CODE      = ? ", customer.getBankAccountCode());
			sql.add(" ,IRAI_NAME         = ? ", customer.getClientName());
			sql.add(" ,NJ_C_DATE         = ? ", customer.getCloseDay());
			sql.add(" ,NJ_R_MON          = ? ", customer.getNextMonth());
			sql.add(" ,NJ_P_DATE         = ? ", customer.getCashDay());
			sql.add(" ,SUM_CODE          = ? ", customer.getSumCode());
			sql.add(" ,NYU_TESU_KBN      = ? ", customer.getCashInFeeType().value);
			sql.add(" ,SIIRE_KBN         = ? ", customer.getPurchase());
			sql.add(" ,TOKUI_KBN         = ? ", customer.getClient());
			sql.add(" ,STR_DATE          = ? ", customer.getDateFrom());
			sql.add(" ,END_DATE          = ? ", customer.getDateTo());
			sql.adt(" ,UPD_DATE          = ? ", getNow());
			sql.add(" ,PRG_ID            = ? ", getProgramCode());
			sql.add(" ,USR_ID            = ? ", getUserCode());
			sql.add(" ,TRI_TYPE_CHTR_FLG = ? ", BooleanUtil.toInt(customer.isCharterer()));
			sql.add(" ,TRI_TYPE_ONR_FLG  = ? ", BooleanUtil.toInt(customer.isOwner()));
			sql.add(" ,TRI_TYPE_AGNT_FLG = ? ", BooleanUtil.toInt(customer.isPortAgent()));
			sql.add(" ,TRI_TYPE_SPLR_FLG = ? ", BooleanUtil.toInt(customer.isSupplier()));
			sql.add(" ,TRI_TYPE_BRKR_FLG = ? ", BooleanUtil.toInt(customer.isBroker()));
			sql.add(" ,TRI_TYPE_BANK_FLG = ? ", BooleanUtil.toInt(customer.isBank()));
			sql.add(" ,TRI_TYPE_OZR_FLG  = ? ", BooleanUtil.toInt(customer.isOther()));
			sql.add(" ,COU_CODE          = ? ", customer.getCountryCode());
			sql.add(" ,EMAIL_ADDRESS     = ? ", customer.getEmailAddress());
			sql.add(" ,TRI_TYPE_SHPP_FLG = ? ", BooleanUtil.toInt(customer.isShipper()));
			sql.add(" ,TRI_TYPE_CONS_FLG = ? ", BooleanUtil.toInt(customer.isConsignee()));
			sql.add(" ,TRI_TYPE_NTPT_FLG = ? ", BooleanUtil.toInt(customer.isNotifyParty()));
			sql.add(" ,TRI_TYPE_FWD_FLG  = ? ", BooleanUtil.toInt(customer.isFowarder()));
			sql.add(" ,TRI_TYPE_BKTR_FLG = ? ", BooleanUtil.toInt(customer.isBunkerTrader()));
			sql.add(" ,TRI_TYPE_BKSP_FLG = ? ", BooleanUtil.toInt(customer.isBunkerSupplier()));

			if (isUseTRI_TYPE_GRP_FLG) {
				sql.add(" ,TRI_TYPE_GRP_FLG = ? ", BooleanUtil.toInt(customer.isGroupCompany()));
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				sql.add(" ,TRI_TYPE_PSN_FLG = ? ", BooleanUtil.toInt(customer.isPersonalCustomer()));
			}
			if (isUseCustomerManagementSet) {
				int cus = customer.getTRI_TITLE_TYPE() != null ? customer.getTRI_TITLE_TYPE().value : 0;
				int emp = customer.getEMP_TITLE_TYPE() != null ? customer.getEMP_TITLE_TYPE().value : 0;

				// AR�������̒S���E�h��
				sql.add(" , CHARGE_DEP_NAME = ? ", customer.getCHARGE_DEP_NAME()); // �S��������
				sql.add(" , CHARGE_EMP_NAME = ? ", customer.getCHARGE_EMP_NAME()); // �S���Җ�
				sql.add(" , TRI_TITLE_TYPE = ? ", cus); // �����h��
				sql.add(" , EMP_TITLE_TYPE = ? ", emp); // �S���Ҍh��
			}

			if (isInvoice) {
				sql.add(" , NO_INV_REG_FLG = ? ", BooleanUtil.toInt(customer.isNO_INV_REG_FLG())); // ��K�i�������t���O
				sql.add(" , NO_INV_REG_ZEI_CODE = ? ", customer.getNO_INV_REG_ZEI_CODE()); // ��K�i����������ŃR�[�h
				sql.add(" , INV_REG_NO = ? ", customer.getINV_REG_NO()); // �K�i���������s���Ǝғo�^�ԍ�
			}

			sql.add(" WHERE KAI_CODE = ? ", customer.getCompanyCode());
			sql.add(" AND	TRI_CODE = ? ", customer.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Customer customer) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql = " DELETE FROM TRI_MST " +
					" WHERE KAI_CODE = " + SQLUtil.getParam(customer.getCompanyCode()) +
					" AND	TRI_CODE = " + SQLUtil.getParam(customer.getCode());

			DBUtil.execute(conn, sql);

			if (isUseCustomerManagementSet) {
				// AR�������̒S���E�h��
				sql = " DELETE FROM TRI_USR_MST " +
						" WHERE KAI_CODE = " + SQLUtil.getParam(customer.getCompanyCode()) +
						" AND	TRI_CODE = " + SQLUtil.getParam(customer.getCode());

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * O/R�}�b�s���O
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected Customer mapping(ResultSet rs) throws Exception {
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG();

		Customer bean = createCustomer();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("TRI_CODE"));
		bean.setName(rs.getString("TRI_NAME"));
		bean.setNames(rs.getString("TRI_NAME_S"));
		bean.setNamek(rs.getString("TRI_NAME_K"));
		bean.setOfficeName(rs.getString("JIG_NAME"));
		bean.setZipCode(rs.getString("ZIP"));
		bean.setAddressKana(rs.getString("JYU_KANA"));
		bean.setAddress(rs.getString("JYU_1"));
		bean.setAddress2(rs.getString("JYU_2"));
		bean.setTel(rs.getString("TEL"));
		bean.setFax(rs.getString("FAX"));
		bean.setSumCode(rs.getString("SUM_CODE"));
		bean.setSumName(rs.getString("SUM_NAME"));

		if (1 == rs.getInt("SIIRE_KBN") && 1 == rs.getInt("TOKUI_KBN")) {
			bean.setCustomerType(CustomerType.BOTH);
		} else if (0 == rs.getInt("SIIRE_KBN") && 1 == rs.getInt("TOKUI_KBN")) {
			bean.setCustomerType(CustomerType.CUSTOMER);
		} else if (1 == rs.getInt("SIIRE_KBN") && 0 == rs.getInt("TOKUI_KBN")) {
			bean.setCustomerType(CustomerType.VENDOR);
		} else {
			bean.setCustomerType(CustomerType.NONE);
		}
		bean.setPurchase(TransUtil.TRUE == rs.getInt("SIIRE_KBN"));
		bean.setCashInFeeType(CashInFeeType.getCashInFeeType(rs.getInt("NYU_TESU_KBN")));
		bean.setBankAccountCode(rs.getString("NKN_CBK_CODE"));
		bean.setClientName(rs.getString("IRAI_NAME"));
		bean.setCloseDay(rs.getInt("NJ_C_DATE"));
		bean.setNextMonth(rs.getInt("NJ_R_MON"));
		bean.setCashDay(rs.getInt("NJ_P_DATE"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setCharterer(TransUtil.TRUE == rs.getInt("TRI_TYPE_CHTR_FLG"));
		bean.setOwner(TransUtil.TRUE == rs.getInt("TRI_TYPE_ONR_FLG"));
		bean.setPortAgent(TransUtil.TRUE == rs.getInt("TRI_TYPE_AGNT_FLG"));
		bean.setSupplier(TransUtil.TRUE == rs.getInt("TRI_TYPE_SPLR_FLG"));
		bean.setBroker(TransUtil.TRUE == rs.getInt("TRI_TYPE_BRKR_FLG"));
		bean.setBank(TransUtil.TRUE == rs.getInt("TRI_TYPE_BANK_FLG")); // BANK Flag
		bean.setOther(TransUtil.TRUE == rs.getInt("TRI_TYPE_OZR_FLG"));
		bean.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
		bean.setShipper(TransUtil.TRUE == rs.getInt("TRI_TYPE_SHPP_FLG"));
		bean.setConsignee(TransUtil.TRUE == rs.getInt("TRI_TYPE_CONS_FLG"));
		bean.setNotifyParty(TransUtil.TRUE == rs.getInt("TRI_TYPE_NTPT_FLG"));
		bean.setFowarder(TransUtil.TRUE == rs.getInt("TRI_TYPE_FWD_FLG"));
		bean.setBunkerTrader(TransUtil.TRUE == rs.getInt("TRI_TYPE_BKTR_FLG"));
		bean.setBunkerSupplier(TransUtil.TRUE == rs.getInt("TRI_TYPE_BKSP_FLG"));

		if (isUseTRI_TYPE_GRP_FLG) {
			bean.setGroupCompany(TransUtil.TRUE == rs.getInt("TRI_TYPE_GRP_FLG"));
		}
		if (isUseTRI_TYPE_PSN_FLG) {
			bean.setPersonalCustomer(TransUtil.TRUE == rs.getInt("TRI_TYPE_PSN_FLG"));
		}
		if (isUseCustomerManagementSet) {
			// AR�������̒S���E�h��
			bean.setCHARGE_DEP_NAME(rs.getString("CHARGE_DEP_NAME")); // �S��������
			bean.setCHARGE_EMP_NAME(rs.getString("CHARGE_EMP_NAME")); // �S���Җ�
			bean.setTRI_TITLE_TYPE(HonorType.getHonorType(rs.getInt("TRI_TITLE_TYPE"))); // �����h��
			bean.setEMP_TITLE_TYPE(HonorType.getHonorType(rs.getInt("EMP_TITLE_TYPE"))); // �S���Ҍh��
		}

		if (isInvoice) {
			bean.setNO_INV_REG_FLG(TransUtil.TRUE == rs.getInt("NO_INV_REG_FLG")); // ��K�i�������t���O
			bean.setNO_INV_REG_ZEI_CODE(rs.getString("NO_INV_REG_ZEI_CODE")); // ��K�i����������ŃR�[�h
			bean.setNO_INV_REG_ZEI_NAME(rs.getString("NO_INV_REG_ZEI_NAME")); // ��K�i����������Ŗ���
			bean.setINV_REG_NO(rs.getString("INV_REG_NO")); // �K�i���������s���Ǝғo�^�ԍ�
		}

		// ��
		bean.setCountryCode(rs.getString("COU_CODE"));
		if (!Util.isNullOrEmpty(rs.getString("COU_CODE"))) {
			Country country = createCountry();
			country.setCode(rs.getString("COU_CODE"));
			country.setCode2(rs.getString("COU_CODE2"));
			country.setNumber(rs.getString("COU_NO"));
			country.setName(rs.getString("COU_NAME"));
			country.setName2(rs.getString("COU_NAME2"));
			bean.setCountry(country);
		}

		// ��s�����}�X�^
		BankAccount cbk = createBankAccount();
		cbk.setCompanyCode(rs.getString("KAI_CODE"));// ��ЃR�[�h
		cbk.setCode(rs.getString("CBK_CBK_CODE"));// �R�[�h
		cbk.setName(rs.getString("CBK_NAME"));// ��s��������
		cbk.setCurrencyCode(rs.getString("CUR_CODE")); // �ʉ݃R�[�h
		cbk.setBankCode(rs.getString("CBK_BNK_CODE"));// ��s�R�[�h
		cbk.setBankName(Util.avoidNull(rs.getString("BNK_NAME")));// ��s����
		cbk.setBranchCode(rs.getString("CBK_STN_CODE"));// �x�X�R�[�h
		cbk.setBranchName(Util.avoidNull(rs.getString("BNK_STN_NAME")));// �x�X����
		cbk.setClientCode(rs.getString("CBK_IRAI_EMP_CODE"));// �U���˗��l�R�[�h
		cbk.setClientName(rs.getString("CBK_IRAI_NAME"));// �U���˗��l��
		cbk.setClientNameJ(rs.getString("CBK_IRAI_NAME_J"));// �U���˗��l���i�����j
		cbk.setClientNameE(rs.getString("CBK_IRAI_NAME_E"));// �U���˗��l���i�p���j
		cbk.setDepositKind(DepositKind.getDepositKind(rs.getInt("CBK_YKN_KBN")));// �a�����
		cbk.setAccountNo(rs.getString("CBK_YKN_NO"));// �����ԍ�
		cbk.setDepartmentCode(rs.getString("CBK_DEP_CODE"));// �v�㕔��R�[�h
		cbk.setItemCode(rs.getString("CBK_KMK_CODE"));// �ȖڃR�[�h
		cbk.setSubItemCode(rs.getString("CBK_HKM_CODE"));// �⏕�ȖڃR�[�h
		cbk.setDetailItemCode(rs.getString("CBK_UKM_CODE"));// ����ȖڃR�[�h
		cbk.setUseEmployeePayment(rs.getInt("CBK_EMP_FB_KBN") != 0);// �Ј��e�a�Ŏg�p���邩
		cbk.setUseExPayment(rs.getInt("CBK_OUT_FB_KBN") != 0);// �ЊO�e�a�Ŏg�p���邩
		bean.setBankAccount(cbk);

		return bean;

	}

	/**
	 * @return ��s����
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * @return �����
	 */
	protected Customer createCustomer() {
		return new Customer();
	}

	/**
	 * @return ��
	 */
	protected Country createCountry() {
		return new Country();
	}

	/**
	 * �����̐U���˗��l�����C������B
	 * @param companyCode ��ЃR�[�h
	 * @param customerCode �����R�[�h
	 * @param clientName �U���˗��l��
	 * @throws TException
	 */
	public void modifyBankAccountClientName(
			String companyCode,
			String customerCode,
			String clientName) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql = " UPDATE	TRI_MST " +
					" SET " +
					" IRAI_NAME = " + SQLUtil.getParam(clientName) +
					" WHERE	KAI_CODE = " + SQLUtil.getParam(companyCode) +
					" AND	TRI_CODE = " + SQLUtil.getParam(customerCode);

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
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

	/**
	 * �����S���҃}�X�^�̏��擾
	 * 
	 * @param triCode
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MST(String triCode) throws TException {

		Connection conn = DBUtil.getConnection();

		List<CustomerUser> list = new ArrayList<CustomerUser>();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT  ");
			sql.add("    KAI_CODE ");
			sql.add("   ,TRI_CODE ");
			sql.add("   ,SYS_KBN ");
			sql.add("   ,USR_NAME ");
			sql.add("   ,DEP_NAME ");
			sql.add("   ,POSITION ");
			sql.add(" FROM TRI_USR_MST ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND   KAI_CODE = ?", getCompanyCode());
			sql.add(" AND   TRI_CODE = ?", triCode);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingTRI_USR_MST(rs, false));
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
	 * �����S���҃}�X�^�̏��擾�i�ꗗ�o�͗p�j
	 * 
	 * @param condition
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MSTExcel(CustomerSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<CustomerUser> list = new ArrayList<CustomerUser>();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT  ");
			sql.add("    tum.KAI_CODE ");
			sql.add("   ,tum.TRI_CODE ");
			sql.add("   ,tri.TRI_NAME ");
			sql.add("   ,tum.SYS_KBN ");
			sql.add("   ,tum.USR_NAME ");
			sql.add("   ,tum.DEP_NAME ");
			sql.add("   ,tum.POSITION ");
			sql.add(" FROM TRI_USR_MST tum ");
			sql.add("     LEFT JOIN TRI_MST tri ");
			sql.add("     ON tum.KAI_CODE = tri.KAI_CODE ");
			sql.add("    AND tum.TRI_CODE = tri.TRI_CODE ");

			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND   tum.KAI_CODE = ?", condition.getCompanyCode());
			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND	tum.TRI_CODE >= " + SQLUtil.getParam(condition.getCodeFrom()));
			}
			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND	tum.TRI_CODE <= " + SQLUtil.getParam(condition.getCodeTo()));
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingTRI_USR_MST(rs, true));
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
	 * @param isTRI_NAME
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected CustomerUser mappingTRI_USR_MST(ResultSet rs, boolean isTRI_NAME) throws Exception {

		CustomerUser bean = new CustomerUser();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setTRI_CODE(rs.getString("TRI_CODE"));
		bean.setSYS_KBN(rs.getString("SYS_KBN"));
		bean.setUSR_NAME(rs.getString("USR_NAME"));
		bean.setDEP_NAME(rs.getString("DEP_NAME"));
		bean.setPOSITION(rs.getString("POSITION"));
		if (isTRI_NAME) {
			bean.setTRI_NAME(rs.getString("TRI_NAME"));
		}

		return bean;

	}

	/**
	 * �����S���҃}�X�^�̏��擾
	 * 
	 * @param list
	 * @throws TException
	 */
	public void entryTRI_USR_MST(List<CustomerUser> list) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM TRI_USR_MST ");
			sql.add(" WHERE KAI_CODE = ?", list.get(0).getKAI_CODE());
			sql.add(" AND   TRI_CODE = ?", list.get(0).getTRI_CODE());

			DBUtil.execute(conn, sql);

			for (CustomerUser bean : list) {
				sql = new SQLCreator();
				sql.add(" INSERT INTO  TRI_USR_MST ( ");
				sql.add("    KAI_CODE ");
				sql.add("   ,TRI_CODE ");
				sql.add("   ,SYS_KBN ");
				sql.add("   ,USR_NAME ");
				sql.add("   ,DEP_NAME ");
				sql.add("   ,POSITION ");
				sql.add("   ,INP_DATE ");
				sql.add("   ,UPD_DATE ");
				sql.add("   ,PRG_ID ");
				sql.add("   ,USR_ID ");
				sql.add("    )  VALUES  (");
				sql.add("    ?  ", bean.getKAI_CODE());
				sql.add("   ,?  ", bean.getTRI_CODE());
				sql.add("   ,?  ", bean.getSYS_KBN());
				sql.add("   ,?  ", bean.getUSR_NAME());
				sql.add("   ,?  ", bean.getDEP_NAME());
				sql.add("   ,?  ", bean.getPOSITION());
				sql.addYMDHMS("  ,?", getNow());
				sql.add("   ,NULL");
				sql.add("   ,?", getProgramCode());
				sql.add("   ,?", getUserCode());
				sql.add("    )  ");
				DBUtil.execute(conn, sql);

			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

}
