package jp.co.ais.trans2.model.user;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * ���[�U�[���[���}�l�[�W���̎����N���X
 * 
 * @author AIS
 */
public class UserRoleManagerImpl extends TModel implements UserRoleManager {

	/**
	 * �w������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public List<UserRole> get(UserSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		List<UserRole> list = new ArrayList<UserRole>();
		try {
			String itemSql = " SELECT" + "\n"
				+ "   usr.KAI_CODE,     usr.ROLE_CODE,  usr.ROLE_NAME,  usr.ROLE_NAME_S,          " + "\n"
				+ "   usr.ROLE_NAME_K,  usr.STR_DATE,   usr.END_DATE,   usr.INP_DATE,             " + "\n"
				+ "   usr.UPD_DATE,     usr.PRG_ID,     usr.USR_ID,     kmk.KMK_CODE,             " + "\n"
				+ "   kmk.KMK_BMN_KBN                                                             " + "\n"
				+ " FROM USR_ROLE_MST usr                                                         " + "\n"
				+ " LEFT OUTER JOIN ROLE_KMK_KJL_MST kmk  ON usr.KAI_CODE  = kmk.KAI_CODE         " + "\n"
				+ "                                      AND usr.ROLE_CODE = kmk.ROLE_CODE        " + "\n"
				+ " WHERE 1 = 1                                                                   " + "\n";

			String deptSql = " SELECT" + "\n"
				+ "   usr.KAI_CODE,     usr.ROLE_CODE,  usr.ROLE_NAME,  usr.ROLE_NAME_S,          " + "\n"
				+ "   usr.ROLE_NAME_K,  usr.STR_DATE,   usr.END_DATE,   usr.INP_DATE,             " + "\n"
				+ "   usr.UPD_DATE,     usr.PRG_ID,     usr.USR_ID,     bmn.BMN_CODE,             " + "\n"
				+ "   bmn.BMN_KBN                                                                 " + "\n"
				+ " FROM USR_ROLE_MST usr                                                         " + "\n"
				+ " LEFT OUTER JOIN ROLE_BMN_KJL_MST bmn  ON usr.KAI_CODE  = bmn.KAI_CODE         " + "\n"
				+ "                                      AND usr.ROLE_CODE = bmn.ROLE_CODE        " + "\n"
				+ " WHERE 1 = 1                                                                   " + "\n";

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				itemSql = itemSql + "  AND usr.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode()) + "\n";
				deptSql = deptSql + "  AND usr.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode()) + "\n";
			}

			// �R�[�h��v
			if (!Util.isNullOrEmpty(condition.getCode())) {
				itemSql = itemSql + "  AND usr.ROLE_CODE = " + SQLUtil.getParam(condition.getCode()) + "\n";
				deptSql = deptSql + "  AND usr.ROLE_CODE = " + SQLUtil.getParam(condition.getCode()) + "\n";
			}

			// �R�[�h�J�n
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				itemSql = itemSql + "  AND usr.ROLE_CODE >= " + SQLUtil.getParam(condition.getCodeFrom()) + "\n";
				deptSql = deptSql + "  AND usr.ROLE_CODE >= " + SQLUtil.getParam(condition.getCodeFrom()) + "\n";
			}

			// �R�[�h�I��
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				itemSql = itemSql + "  AND usr.ROLE_CODE <= " + SQLUtil.getParam(condition.getCodeTo()) + "\n";
				deptSql = deptSql + "  AND usr.ROLE_CODE <= " + SQLUtil.getParam(condition.getCodeTo()) + "\n";
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				itemSql = itemSql + "  AND usr.ROLE_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT) + "\n";
				deptSql = deptSql + "  AND usr.ROLE_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT) + "\n";
			}

			// ���̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				itemSql = itemSql + "  AND usr.ROLE_CODE_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI) + "\n";
				deptSql = deptSql + "  AND usr.ROLE_CODE_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI) + "\n";
			}

			// �������̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				itemSql = itemSql + "  AND usr.ROLE_CODE_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI) + "\n";
				deptSql = deptSql + "  AND usr.ROLE_CODE_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI) + "\n";
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				itemSql = itemSql + "  AND usr.STR_DATE <=" + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) + "\n";
				itemSql = itemSql + "  AND usr.END_DATE >=" + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) + "\n";
				deptSql = deptSql + "  AND usr.STR_DATE <=" + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) + "\n";
				deptSql = deptSql + "  AND usr.END_DATE >=" + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) + "\n";
			}

			itemSql = itemSql + " ORDER BY usr.ROLE_CODE";
			deptSql = deptSql + " ORDER BY usr.ROLE_CODE";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, itemSql);

			// �擾�f�[�^�����X�g�`���ɕϊ�
			while (rs.next()) {
				UserRole tmpBean = mapping(rs);

				boolean isAdd = false;

				// ���[���R�[�h�P�ʂŃG���e�B�e�B���܂Ƃ߂�
				for (UserRole bean : list) {

					if (bean.getCode().equals(tmpBean.getCode())) {
						bean.addItemLvlList(mappingItem(rs));
						isAdd = true;
						break;
					}
				}

				if (!isAdd) {
					tmpBean.addItemLvlList(mappingItem(rs));
					list.add(tmpBean);
				}

			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			statement = DBUtil.getStatement(conn);
			rs = DBUtil.select(statement, deptSql);

			// �擾�f�[�^�����X�g�`���ɕϊ�
			while (rs.next()) {
				UserRole tmpBean = mapping(rs);

				// ���[���R�[�h�P�ʂŃG���e�B�e�B���܂Ƃ߂�
				for (UserRole bean : list) {

					if (bean.getCode().equals(tmpBean.getCode())) {
						bean.addDepLvlList(mappingDep(rs));
						break;
					}
				}

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
	 * @return bean
	 * @throws Exception
	 */
	protected UserRole mapping(ResultSet rs) throws Exception {

		UserRole bean = new UserRole();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("ROLE_CODE"));
		bean.setName(rs.getString("ROLE_NAME"));
		bean.setNames(rs.getString("ROLE_NAME_S"));
		bean.setNamek(rs.getString("ROLE_NAME_K"));
		bean.setTermFrom(rs.getDate("STR_DATE"));
		bean.setTermTo(rs.getDate("END_DATE"));
		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setUsrId(rs.getString("USR_ID"));
		bean.setPrgId(rs.getString("PRG_ID"));

		return bean;

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected RoleDepartmentLevel mappingDep(ResultSet rs) throws Exception {

		RoleDepartmentLevel bean = new RoleDepartmentLevel();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setRoleCode(rs.getString("ROLE_CODE"));
		bean.setDepCode(rs.getString("BMN_CODE"));
		bean.setDepDivision(rs.getInt("BMN_KBN"));
		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setUsrId(rs.getString("USR_ID"));
		bean.setPrgId(rs.getString("PRG_ID"));

		return bean;

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected RoleItemLevel mappingItem(ResultSet rs) throws Exception {

		RoleItemLevel bean = new RoleItemLevel();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setRoleCode(rs.getString("ROLE_CODE"));
		bean.setItemCode(rs.getString("KMK_CODE"));
		bean.setDepDivision(rs.getInt("KMK_BMN_KBN"));
		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setUsrId(rs.getString("USR_ID"));
		bean.setPrgId(rs.getString("PRG_ID"));

		return bean;

	}

	/**
	 * �o�^����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(UserRole bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �o�^�����̐ݒ�
			bean.setInpDate(Util.getCurrentDate());

			// �o�^�����̎��s
			insert(bean, conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �C������
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(UserRole bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �X�V�����̐ݒ�
			bean.setUpdDate(Util.getCurrentDate());

			// �Ώۃ��[���R�[�h�̃f�[�^����U�폜
			delete(bean, conn);

			// �o�^�����̎��s
			insert(bean, conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �폜����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(UserRole bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// �폜����
			delete(bean, conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �o�^����
	 * 
	 * @param bean
	 * @param conn
	 * @throws TException
	 */
	protected void insert(UserRole bean, Connection conn) throws TException {

		try {

			// ���[�U�[���[���}�X�^�̓o�^
			String sql = "INSERT INTO USR_ROLE_MST (" + "  KAI_CODE," + "  ROLE_CODE," + "  ROLE_NAME,"
				+ "  ROLE_NAME_S," + "  ROLE_NAME_K," + "  STR_DATE," + "  END_DATE," + "  INP_DATE," + "  UPD_DATE,"
				+ "  PRG_ID," + "  USR_ID" + "  ) VALUES (" + SQLUtil.getParam(bean.getCompanyCode()) + ", "
				+ SQLUtil.getParam(bean.getCode()) + ", " + SQLUtil.getParam(bean.getName()) + ", "
				+ SQLUtil.getParam(bean.getNames()) + ", " + SQLUtil.getParam(bean.getNamek()) + ", "
				+ SQLUtil.getYYYYMMDDParam(bean.getTermFrom()) + ", " + SQLUtil.getYYYYMMDDParam(bean.getTermTo())
				+ ", " + SQLUtil.getYMDHMSParam(bean.getInpDate()) + ", " + SQLUtil.getYMDHMSParam(bean.getUpdDate())
				+ ", " + SQLUtil.getParam(getProgramCode()) + ", " + SQLUtil.getParam(getUserCode()) + "  )";

			DBUtil.execute(conn, sql);

			// ����J�����x���̓o�^
			for (RoleDepartmentLevel dep : bean.getDepLvlList()) {

				sql = "INSERT INTO ROLE_BMN_KJL_MST (" + "  KAI_CODE," + "  ROLE_CODE," + "  BMN_CODE," + "  BMN_KBN,"
					+ "  INP_DATE," + "  UPD_DATE," + "  PRG_ID," + "  USR_ID" + "  ) VALUES ("
					+ SQLUtil.getParam(bean.getCompanyCode()) + ", " + SQLUtil.getParam(bean.getCode()) + ", "
					+ SQLUtil.getParam(dep.getDepCode()) + ", " + dep.getDepDivision() + ", "
					+ SQLUtil.getYMDHMSParam(bean.getInpDate()) + ", " + SQLUtil.getYMDHMSParam(bean.getUpdDate())
					+ ", " + SQLUtil.getParam(getProgramCode()) + ", " + SQLUtil.getParam(getUserCode()) + "  )";
				DBUtil.execute(conn, sql);

			}

			// �ȖڊJ�����x���̓o�^
			for (RoleItemLevel item : bean.getItemLvlList()) {

				sql = "INSERT INTO ROLE_KMK_KJL_MST (" + "  KAI_CODE," + "  ROLE_CODE," + "  KMK_CODE,"
					+ "  KMK_BMN_KBN," + "  INP_DATE," + "  UPD_DATE," + "  PRG_ID," + "  USR_ID" + "  ) VALUES ("
					+ SQLUtil.getParam(bean.getCompanyCode()) + ", " + SQLUtil.getParam(bean.getCode()) + ", "
					+ SQLUtil.getParam(item.getItemCode()) + ", " + item.getDepDivision() + ", "
					+ SQLUtil.getYMDHMSParam(bean.getInpDate()) + ", " + SQLUtil.getYMDHMSParam(bean.getUpdDate())
					+ ", " + SQLUtil.getParam(getProgramCode()) + ", " + SQLUtil.getParam(getUserCode()) + "  )";
				DBUtil.execute(conn, sql);

			}

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * �폜����
	 * 
	 * @param bean
	 * @param conn
	 * @throws TException
	 */
	public void delete(UserRole bean, Connection conn) throws TException {

		try {

			// ���[�U�[���[���}�X�^�̍폜
			String sql = " DELETE FROM USR_ROLE_MST " + " WHERE KAI_CODE = " + SQLUtil.getParam(bean.getCompanyCode())
				+ " AND ROLE_CODE = " + SQLUtil.getParam(bean.getCode());

			DBUtil.execute(conn, sql);

			// �ȖڊJ�����x���̍폜
			sql = " DELETE FROM ROLE_KMK_KJL_MST " + " WHERE KAI_CODE = " + SQLUtil.getParam(bean.getCompanyCode())
				+ " AND ROLE_CODE = " + SQLUtil.getParam(bean.getCode());

			DBUtil.execute(conn, sql);

			// ����J�����x���̍폜
			sql = " DELETE FROM ROLE_BMN_KJL_MST " + " WHERE KAI_CODE = " + SQLUtil.getParam(bean.getCompanyCode())
				+ " AND ROLE_CODE = " + SQLUtil.getParam(bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * ���[�U�[���[��(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public byte[] getExcel(UserSearchCondition condition) throws TException {
		List<UserRole> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		UserRoleExcel layout = new UserRoleExcel(condition.getLang());
		byte[] data = layout.getExcel(list);

		return data;

	}
}
