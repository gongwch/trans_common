package jp.co.ais.trans2.model.currency;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �ʉݏ��̎����N���X
 * 
 * @author AIS
 */
public class CurrencyManagerImpl extends TModel implements CurrencyManager {

	public List<Currency> get(CurrencySearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();
		// �������ʊi�[�p�z�񐶐�
		List<Currency> list = new ArrayList<Currency>();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("SELECT");
			sql.add(" cur.KAI_CODE ");
			sql.add(" ,cur.CUR_CODE ");
			sql.add(" ,cur.CUR_NAME ");
			sql.add(" ,cur.CUR_NAME_S ");
			sql.add(" ,cur.CUR_NAME_K ");
			sql.add(" ,cur.DEC_KETA ");
			sql.add(" ,cur.RATE_POW ");
			sql.add(" ,cur.STR_DATE ");
			sql.add(" ,cur.END_DATE ");
			sql.add("FROM CUR_MST cur");
			sql.add(" WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND");
				sql.add("	cur.KAI_CODE = ?", condition.getCompanyCode());
			}

			// �R�[�h
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("AND");
				sql.add("    cur.CUR_CODE = ?", condition.getCode());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("AND");
				sql.addLikeFront("    cur.CUR_CODE ?", condition.getCodeLike());
			}

			// ���̞B��
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.add("AND");
				sql.addNLikeAmbi("    cur.CUR_NAME_S ?", condition.getNamesLike());
			}

			// �������̞B��
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.add("AND");
				sql.addNLikeAmbi("    cur.CUR_NAME_K ?", condition.getNamekLike());
			}

			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("AND");
				sql.add("    cur.CUR_CODE >= ?", condition.getCodeFrom());
			}

			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("AND");
				sql.add("    cur.CUR_CODE <= ?", condition.getCodeTo());
			}

			// �R�[�h�z��
			if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
				sql.add(" AND   cur.CUR_CODE IN ");
				sql.addInStatement(condition.getCodeList());
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("AND");
				sql.addYMDHMS("    cur.STR_DATE <= ?", condition.getValidTerm());
				sql.add("AND");
				sql.addYMDHMS("    cur.END_DATE >= ?", condition.getValidTerm());
			}

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cur.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR cur.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add("ORDER BY");
			sql.add("    cur.CUR_CODE ");

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
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CurrencySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   CUR_MST cur ");
			sql.add(" WHERE  cur.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cur.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cur.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cur.INP_DATE IS NULL AND cur.UPD_DATE IS NULL) ");
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

	public void entry(Currency bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("INSERT INTO CUR_MST ( ");
			sql.add(" KAI_CODE ");
			sql.add(" ,CUR_CODE ");
			sql.add(" ,CUR_NAME ");
			sql.add(" ,CUR_NAME_S ");
			sql.add(" ,CUR_NAME_K ");
			sql.add(" ,DEC_KETA ");
			sql.add(" ,RATE_POW ");
			sql.add(" ,CONV_KBN ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(" ,INP_DATE");
			sql.add(" ,UPD_DATE");
			sql.add(" ,PRG_ID");
			sql.add(" ,USR_ID");
			sql.add(") VALUES (");
			sql.add("	 ?", getCompanyCode());
			sql.add("	,?", bean.getCode());
			sql.add("	,?", bean.getName());
			sql.add("   ,?", bean.getNames());
			sql.add("   ,?", bean.getNamek());
			sql.add("	,?", bean.getDecimalPoint());
			sql.add("	,?", bean.getRatePow());
			sql.add("	,0");
			sql.add("   ,?", bean.getDateFrom());
			sql.add("   ,?", bean.getDateTo());
			sql.addYMDHMS("    ,?", getNow());
			sql.add("	,NULL");
			sql.add("   ,?", getProgramCode());
			sql.add("   ,?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Currency bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    CUR_MST");
			sql.add("SET");
			sql.add("	 KAI_CODE =?", getCompanyCode());
			sql.add("	,CUR_CODE =?", bean.getCode());
			sql.add("	,CUR_NAME =?", bean.getName());
			sql.add("   ,CUR_NAME_S =?", bean.getNames());
			sql.add("   ,CUR_NAME_K =?", bean.getNamek());
			sql.add("	,DEC_KETA =?", bean.getDecimalPoint());
			sql.add("	,RATE_POW =?", bean.getRatePow());
			sql.add("   ,STR_DATE =?", bean.getDateFrom());
			sql.add("   ,END_DATE =?", bean.getDateTo());
			sql.addYMDHMS("    ,UPD_DATE=?", getNow());
			sql.add("   ,PRG_ID=?", getProgramCode());
			sql.add("   ,USR_ID=?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    CUR_CODE = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Currency bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    CUR_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", getCompanyCode());
			sql.add("AND");
			sql.add("    CUR_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �ʉ݈ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̒ʉ݈ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(CurrencySearchCondition condition) throws TException {

		try {

			// �f�[�^�𒊏o
			List<Currency> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			CurrencyExcel exl = new CurrencyExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return Currency
	 * @throws Exception
	 */
	protected Currency mapping(ResultSet rs) throws Exception {

		Currency bean = new Currency();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("CUR_CODE"));
		bean.setName(rs.getString("CUR_NAME"));
		bean.setNames(rs.getString("CUR_NAME_S"));
		bean.setNamek(rs.getString("CUR_NAME_K"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setDecimalPoint(rs.getInt("DEC_KETA"));
		bean.setRatePow(rs.getInt("RATE_POW"));

		return bean;
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
