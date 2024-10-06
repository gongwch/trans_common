package jp.co.ais.trans2.model.security;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O�������[���}�l�[�W���̎����N���X
 * 
 * @author AIS
 */
public class ProgramRoleManagerImpl extends TModel implements ProgramRoleManager {

	/**
	 * �w������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public List<ProgramRole> get(ProgramRoleSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		List<ProgramRole> list = new ArrayList<ProgramRole>();
		try {
			String sql = " SELECT" + "\n"
				+ "   role.KAI_CODE, role.ROLE_CODE, role.ROLE_NAME, role.ROLE_NAME_S, role.ROLE_NAME_K,  " + "\n"
				+ "   role.PRG_CODE, role.STR_DATE,  role.END_DATE,  role.INP_DATE,    role.UPD_DATE,     " + "\n"
				+ "   role.PRG_ID,   role.USR_ID,    prg.SYS_CODE,   prg.PRG_NAME_S,   role.PROC_AUTH_KBN " + "\n"
				+ " FROM PRG_ROLE_MST role                                                                " + "\n"
				+ " INNER JOIN PRG_MST prg  ON role.KAI_CODE = prg.KAI_CODE                               " + "\n"
				+ "                        AND role.PRG_CODE = prg.PRG_CODE                               " + "\n"
				+ " WHERE 1 = 1                                                                           " + "\n";

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql + "  AND role.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode()) + "\n";
			}

			// �R�[�h��v
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql = sql + "  AND role.ROLE_CODE = " + SQLUtil.getParam(condition.getCode()) + "\n";
			}

			// �R�[�h�J�n
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql = sql + "  AND role.ROLE_CODE >= " + SQLUtil.getParam(condition.getCodeFrom()) + "\n";
			}

			// �R�[�h�I��
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql = sql + "  AND role.ROLE_CODE <= " + SQLUtil.getParam(condition.getCodeTo()) + "\n";
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql = sql + "  AND role.ROLE_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT) + "\n";
			}

			// ���̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql = sql + "  AND role.ROLE_NAME_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI) + "\n";
			}

			// �������̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql = sql + "  AND role.ROLE_NAME_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI) + "\n";
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql = sql + "  AND role.STR_DATE <=" + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) + "\n";
				sql = sql + "  AND role.END_DATE >=" + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()) + "\n";
			}

			sql = sql + " ORDER BY role.ROLE_CODE, prg.SYS_CODE, prg.PRG_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			// �擾�f�[�^�����X�g�`���ɕϊ�
			while (rs.next()) {
				ProgramRole tmpBean = mapping(rs);

				boolean isAdd = false;

				// ���[���R�[�h�P�ʂŃG���e�B�e�B���܂Ƃ߂�
				for (ProgramRole bean : list) {

					if (bean.getCode().equals(tmpBean.getCode())) {
						bean.addProgramCodeList(getPrgbean(tmpBean));
						isAdd = true;
					}
				}

				if (!isAdd) {
					tmpBean.addProgramCodeList(getPrgbean(tmpBean));
					list.add(tmpBean);
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
	 * �v���O�����}�X�^Bean���擾����
	 * 
	 * @param bean
	 * @return �v���O�����}�X�^
	 */
	private Program getPrgbean(ProgramRole bean) {

		Program resBean = new Program();
		SystemClassification systemClassification = new SystemClassification();
		systemClassification.setCode(bean.getSysKdn());
		resBean.setSystemClassification(systemClassification);
		resBean.setCode(bean.getPrgCode());
		resBean.setNames(bean.getPrgNameS());
		resBean.setProcAuthKbn(bean.getProcAuthKbn());
		return resBean;

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected ProgramRole mapping(ResultSet rs) throws Exception {

		ProgramRole bean = new ProgramRole();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("ROLE_CODE"));
		bean.setName(rs.getString("ROLE_NAME"));
		bean.setNames(rs.getString("ROLE_NAME_S"));
		bean.setNamek(rs.getString("ROLE_NAME_K"));
		bean.setPrgCode(rs.getString("PRG_CODE"));
		bean.setTermFrom(rs.getDate("STR_DATE"));
		bean.setTermTo(rs.getDate("END_DATE"));
		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setUsrId(rs.getString("USR_ID"));
		bean.setPrgId(rs.getString("PRG_ID"));
		bean.setSysKdn(rs.getString("SYS_CODE"));
		bean.setPrgNameS(rs.getString("PRG_NAME_S"));
		bean.setProcAuthKbn(rs.getInt("PROC_AUTH_KBN"));

		return bean;

	}

	/**
	 * �o�^����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(ProgramRole bean) throws TException {

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
	public void modify(ProgramRole bean) throws TException {

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
	public void delete(ProgramRole bean) throws TException {

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
	private void insert(ProgramRole bean, Connection conn) throws TException {

		try {
			for (Program prg : bean.getProgramList()) {

				String sql = "INSERT INTO PRG_ROLE_MST (" + "  KAI_CODE," + "  ROLE_CODE," + "  ROLE_NAME,"
					+ "  ROLE_NAME_S," + "  ROLE_NAME_K," + "  PRG_CODE," + "  STR_DATE," + "  END_DATE,"
					+ "  INP_DATE," + "  UPD_DATE," + "  PRG_ID," + "  USR_ID," + "  PROC_AUTH_KBN " + "  ) VALUES ("
					+ SQLUtil.getParam(bean.getCompanyCode()) + ", " + SQLUtil.getParam(bean.getCode()) + ", "
					+ SQLUtil.getParam(bean.getName()) + ", " + SQLUtil.getParam(bean.getNames()) + ", "
					+ SQLUtil.getParam(bean.getNamek()) + ", " + SQLUtil.getParam(prg.getCode()) + ", "
					+ SQLUtil.getYYYYMMDDParam(bean.getTermFrom()) + ", " + SQLUtil.getYYYYMMDDParam(bean.getTermTo())
					+ ", " + SQLUtil.getYMDHMSParam(bean.getInpDate()) + ", "
					+ SQLUtil.getYMDHMSParam(bean.getUpdDate()) + ", " + SQLUtil.getParam(getProgramCode()) + ", "
					+ SQLUtil.getParam(getUserCode()) + ", "
					+ SQLUtil.getParam(Integer.toString(prg.getProcAuthKbn()))
					+ "  )";

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
	public void delete(ProgramRole bean, Connection conn) throws TException {

		try {

			String sql = " DELETE FROM PRG_ROLE_MST " + " WHERE KAI_CODE = " + SQLUtil.getParam(bean.getCompanyCode())
				+ " AND ROLE_CODE = " + SQLUtil.getParam(bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �v���O�������[��(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public byte[] getExcel(ProgramRoleSearchCondition condition) throws TException {
		List<ProgramRole> list = get(condition);
		if (list == null || list.isEmpty()) {
			return null;
		}

		ProgramRoleExcel layout = new ProgramRoleExcel(condition.getLang());
		byte[] data = layout.getExcel(list);

		return data;

	}

}
