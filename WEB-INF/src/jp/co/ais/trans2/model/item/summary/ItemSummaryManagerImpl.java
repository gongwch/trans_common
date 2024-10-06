package jp.co.ais.trans2.model.item.summary;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.item.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - DAO Class
 * 
 * @author AIS
 */
public class ItemSummaryManagerImpl extends TModel implements ItemSummaryManager {

	/** �Ȗڑ̌n�R�[�h */
	protected static String baseKmtCode = "00";

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List ��������
	 * @throws TException
	 */
	public List<ItemSummary> get(ItemSummarySearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<ItemSummary> list = new ArrayList<ItemSummary>();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("SELECT");
			sql.add("    k_sum.KMT_CODE,");
			sql.add("    k_tk.KMT_NAME_S,");
			sql.add("    k_sum.KMK_CODE,");
			sql.add("    k_km_k.KMK_NAME_S KMK_NAME,");
			sql.add("    k_km_k.KMK_NAME_k,");
			sql.add("    k_sum.KOK_NAME,");
			sql.add("    k_sum.SUM_CODE,");
			sql.add("    k_km_s.KMK_NAME_S SUM_NAME,");
			sql.add("    k_sum.ODR,");
			sql.add("    k_sum.HYJ_KBN,");
			sql.add("    k_km_k.SUM_KBN,");
			sql.add("    k_km_k.KMK_SHU,");
			sql.add("    k_km_k.DC_KBN,");
			sql.add("    k_sum.STR_DATE,");
			sql.add("    k_sum.END_DATE");
			sql.add("FROM");
			sql.add("    KMK_SUM_MST k_sum");
			sql.add("LEFT OUTER JOIN KMK_TK_MST k_tk ON  k_sum.KAI_CODE = k_tk.KAI_CODE");
			sql.add("                                AND k_sum.KMT_CODE = k_tk.KMT_CODE");
			sql.add("LEFT OUTER JOIN KMK_MST k_km_k ON  k_sum.KAI_CODE = k_km_k.KAI_CODE");
			sql.add("                               AND k_sum.KMK_CODE = k_km_k.KMK_CODE");
			sql.add("LEFT OUTER JOIN KMK_MST k_km_s ON  k_sum.KAI_CODE = k_km_s.KAI_CODE");
			sql.add("                               AND k_sum.SUM_CODE = k_km_s.KMK_CODE");
			sql.add("WHERE");
			sql.add("    1 = 1");

			// ���������F��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND");
				sql.add("    k_sum.KAI_CODE = ?", condition.getCompanyCode());
			}
			// ���������F�Ȗڑ̌n�R�[�h
			if (!Util.isNullOrEmpty(condition.getKmtCode())) {
				sql.add("AND");
				sql.add("    k_sum.KMT_CODE = ?", condition.getKmtCode());
			}
			// ���������F�R�[�h
			if (!Util.isNullOrEmpty(condition.getKmkCode())) {
				sql.add("AND");
				sql.add("    k_sum.KMK_CODE = ?", condition.getKmkCode());
			}
			// ���������F�R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("AND");
				sql.addLikeFront("    k_sum.KMK_CODE ?", condition.getCodeLike());
			}

			// ���������F�J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("AND");
				sql.add("    k_sum.KMK_CODE >= ?", condition.getCodeFrom());
			}
			// ���������F�I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("AND");
				sql.add("    k_sum.KMK_CODE <= ?", condition.getCodeTo());
			}
			// ���������F���̂����܂���v
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.add("AND");
				sql.addLikeAmbi("    k_km_k.KMK_NAME_S ?", condition.getNamesLike());
			}
			// ���������F�������̂����܂���v
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.add("AND");
				sql.addLikeAmbi("    k_km_k.KMK_NAME_k ?", condition.getNamekLike());
			}
			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("AND");
				sql.addYMDHMS("    k_sum.STR_DATE <= ?", condition.getValidTerm());
				sql.add("AND");
				sql.addYMDHMS("    k_sum.END_DATE >= ?", condition.getValidTerm());
			}

			sql.add("ORDER BY");
			sql.add("    k_sum.KMT_CODE, k_sum.KMK_CODE");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	public List<ItemSummary> getRef(ItemSummarySearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		List<ItemSummary> list = new ArrayList<ItemSummary>();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("SELECT DISTINCT");
			sql.add("      k_sum.KMK_CODE,");
			sql.add("      k_km_k.KMK_NAME_S,");
			sql.add("      k_km_k.KMK_NAME_k");
			sql.add("FROM  KMK_SUM_MST k_sum");
			sql.add("LEFT OUTER JOIN KMK_MST k_km_k");
			sql.add("ON    k_sum.KAI_CODE = k_km_k.KAI_CODE");
			sql.add("AND   k_sum.KMK_CODE = k_km_k.KMK_CODE");
			sql.add("WHERE 1 = 1");

			// ���������F��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND k_sum.KAI_CODE = ?", condition.getCompanyCode());
			}

			// ���������F�Ȗڑ̌n�R�[�h
			if (!Util.isNullOrEmpty(condition.getKmtCode())) {
				sql.add("AND k_sum.KMT_CODE = ?", condition.getKmtCode());
			}

			// ���������F�ȖڃR�[�h
			if (!Util.isNullOrEmpty(condition.getKmkCode())) {
				sql.add("AND k_sum.KMK_CODE = ?", condition.getKmkCode());
			}

			// ���������F�R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND k_sum.KMK_CODE ?", condition.getCodeLike());
			}

			// ���������F�J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("AND k_sum.KMK_CODE >= ?", condition.getCodeFrom());
			}

			// ���������F�I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("AND k_sum.KMK_CODE <= ?", condition.getCodeTo());
			}

			// ���������F���̂����܂���v
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addLikeAmbi(" AND k_km_k.KMK_NAME_S ?", condition.getNamesLike());
			}

			// ���������F�������̂����܂���v
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addLikeAmbi(" AND k_km_k.KMK_NAME_k ?", condition.getNamekLike());
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.addYMDHMS(" AND k_sum.STR_DATE <= ?", condition.getValidTerm());
				sql.addYMDHMS(" AND k_sum.END_DATE >= ?", condition.getValidTerm());
			}

			sql.add("ORDER BY k_sum.KMK_CODE");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingRef(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(ItemSummary bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("INSERT INTO KMK_SUM_MST (");
			sql.add("    KAI_CODE,");
			sql.add("    KMT_CODE,");
			sql.add("    KMK_CODE,");
			sql.add("    KOK_NAME,");
			sql.add("    SUM_CODE,");
			sql.add("    ODR,");
			sql.add("    HYJ_KBN,");
			sql.add("    STR_DATE,");
			sql.add("    END_DATE,");
			sql.add("    INP_DATE,");
			sql.add("    PRG_ID,");
			sql.add("    USR_ID");
			sql.add(") VALUES (");
			sql.add("    ?,", getCompanyCode());
			sql.add("    ?,", bean.getKmtCode());
			sql.add("    ?,", bean.getKmkCode());
			sql.add("    ?,", bean.getKokName());
			sql.add("    ?,", bean.getSumCode());
			sql.add("    ?,", bean.getOdr());
			sql.add("    ?,", bean.getHyjKbn());
			sql.add("    ?,", bean.getDateFrom());
			sql.add("    ?,", bean.getDateTo());
			sql.addYMDHMS("    ?,", getNow());
			sql.add("    ?,", getProgramCode());
			sql.add("    ? ", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(ItemSummary bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    KMK_SUM_MST");
			sql.add("SET");
			sql.add("    KOK_NAME = ?,", bean.getKokName());
			sql.add("    SUM_CODE = ?,", bean.getSumCode());
			sql.add("    ODR = ?,", bean.getOdr());
			sql.add("    HYJ_KBN = ?,", bean.getHyjKbn());
			sql.add("    STR_DATE = ?,", bean.getDateFrom());
			sql.add("    END_DATE = ?,", bean.getDateTo());
			sql.addYMDHMS("    UPD_DATE = ?,", getNow());
			sql.add("    PRG_ID = ?,", getProgramCode());
			sql.add("    USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    KMT_CODE = ?", bean.getKmtCode());
			sql.add("AND");
			sql.add("    KMK_CODE = ?", bean.getKmkCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param bean �I�����
	 * @throws TException
	 */
	public void delete(ItemSummary bean) throws TException {

		if (bean.getKmtCode().equals(baseKmtCode) && isExist(getCompanyCode(), bean.getKmkCode(), bean.getSumCode())) {
			throw new TException("I00720", "C00077"); // �w��̉Ȗڂ͊��ɗ��p����Ă���ׁA�폜�ł��܂���B
		}

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("   KMK_SUM_MST");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    KMT_CODE = ?", bean.getKmtCode());
			sql.add("AND");
			sql.add("    KMK_CODE = ?", bean.getKmkCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ���Y�Ȗڃf�[�^���d��f�[�^�ɑ��݂��邩�`�F�b�N
	 * 
	 * @param companyCode
	 * @param kmkCode
	 * @param sumKmkCode
	 * @return ���݂���Ftrue�A���݂��Ȃ��Ffalse
	 * @throws TException
	 */
	public boolean isExist(String companyCode, String kmkCode, String sumKmkCode) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT KAI_CODE ");
			sql.add(" FROM  GL_SWK_HDR ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add("   AND SWK_KMK_CODE IN ( ? ", kmkCode);
			sql.add("                        ,? ", sumKmkCode);
			sql.add("                       ) ");

			sql.add(" UNION ALL ");

			sql.add(" SELECT KAI_CODE ");
			sql.add(" FROM AP_SWK_HDR ");
			sql.add(" WHERE KAI_CODE = ?", companyCode);
			sql.add("   AND SWK_KMK_CODE IN ( ? ", kmkCode);
			sql.add("                        ,? ", sumKmkCode);
			sql.add("                       ) ");

			sql.add(" UNION ALL ");

			sql.add(" SELECT KAI_CODE ");
			sql.add(" FROM AR_SWK_HDR ");
			sql.add(" WHERE KAI_CODE = ?", companyCode);
			sql.add("   AND SWK_KMK_CODE IN ( ? ", kmkCode);
			sql.add("                        ,? ", sumKmkCode);
			sql.add("                       ) ");

			sql.add(" UNION ALL ");

			sql.add(" SELECT KAI_CODE ");
			sql.add(" FROM SWK_DTL ");
			sql.add(" WHERE KAI_CODE = ?", companyCode);
			sql.add("   AND SWK_KMK_CODE IN ( ? ", kmkCode);
			sql.add("                        ,? ", sumKmkCode);
			sql.add("                       ) ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				return true;
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return false;
	}

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return byte ��������
	 * @throws TException
	 */
	public byte[] getExcel(ItemSummarySearchCondition condition) throws TException {

		try {

			List<ItemSummary> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			ItemSummaryExcel exl = new ItemSummaryExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �ȖڏW�v��o�^����B
	 * 
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 * @param list �ȖڏW�v
	 * @throws TException
	 */
	public void entryItemSummary(String kmtCode, List<ItemSummary> list) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE FROM KMK_SUM_MST");
			s.add("WHERE KAI_CODE = ?", getCompanyCode());
			s.add("  AND KMT_CODE = ?", kmtCode);

			DBUtil.execute(s);

			if (list != null && !list.isEmpty()) {

				for (ItemSummary bean : list) {

					s = new SQLCreator();

					s.add("INSERT INTO KMK_SUM_MST (");
					s.add("    KAI_CODE,");
					s.add("    KMT_CODE,");
					s.add("    KMK_CODE,");
					s.add("    KOK_NAME,");
					s.add("    SUM_CODE,");
					s.add("    ODR,");
					s.add("    HYJ_KBN,");
					s.add("    STR_DATE,");
					s.add("    END_DATE,");
					s.add("    INP_DATE,");
					s.add("    UPD_DATE,");
					s.add("    PRG_ID,");
					s.add("    USR_ID");
					s.add(") VALUES (");
					s.add("  ?,", getCompanyCode());
					s.add("  ?,", kmtCode);
					s.add("  ?,", bean.getKmkCode());
					s.add("  ?,", bean.getKokName());
					s.add("  ?,", bean.getSumCode());
					s.add("  ?,", bean.getOdr());
					s.add("  ?,", bean.getHyjKbn());
					s.add("  ?,", bean.getDateFrom());
					s.add("  ?,", bean.getDateTo());

					if (Util.isNullOrEmpty(bean.getInpDate())) {
						s.addYMDHMS("  ?,", getNow());
						s.addYMDHMS("  ?,", null);
					} else {
						s.addYMDHMS("  ?,", bean.getInpDate());
						s.addYMDHMS("  ?,", getNow());
					}

					s.add("  ?,", getProgramCode());
					s.add("  ?", getUserCode());
					s.add(")");

					DBUtil.execute(s);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �ȖڏW�v��o�^����B�i���ʎ��j
	 * 
	 * @param preKmtCode �O��Ȗڑ̌n�R�[�h
	 * @param kmtCode ����Ȗڑ̌n�R�[�h
	 * @throws TException
	 */
	public void entryCopyItemSummary(String preKmtCode, String kmtCode) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE FROM KMK_SUM_MST");
			s.add("WHERE KAI_CODE = ?", getCompanyCode());
			s.add("  AND KMT_CODE = ?", kmtCode);

			DBUtil.execute(s);

			s = new SQLCreator();

			s.add("INSERT INTO KMK_SUM_MST (");
			s.add("     KAI_CODE");
			s.add("    ,KMT_CODE");
			s.add("    ,KMK_CODE");
			s.add("    ,KOK_NAME");
			s.add("    ,SUM_CODE");
			s.add("    ,ODR");
			s.add("    ,HYJ_KBN");
			s.add("    ,STR_DATE");
			s.add("    ,END_DATE");
			s.add("    ,INP_DATE");
			s.add("    ,PRG_ID");
			s.add("    ,USR_ID");
			s.add(")");
			s.add("SELECT ");
			s.add("       ?", getCompanyCode());
			s.add("      ,?", kmtCode);
			s.add("      ,KMK_CODE");
			s.add("      ,KOK_NAME");
			s.add("      ,SUM_CODE");
			s.add("      ,ODR");
			s.add("      ,HYJ_KBN");
			s.add("      ,STR_DATE");
			s.add("      ,END_DATE");
			s.addYMDHMS(",?", getNow());
			s.add("      ,?", getProgramCode());
			s.add("      ,?", getUserCode());
			s.add("FROM  KMK_SUM_MST");
			s.add("WHERE KAI_CODE = ?", getCompanyCode());

			if (!Util.isNullOrEmpty(preKmtCode)) {
				s.add("  AND KMT_CODE = ?", preKmtCode);
			}

			DBUtil.execute(s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �������ʒl��Bean�ɃZ�b�g
	 * 
	 * @param rs ResultSet ��������
	 * @return ItemSummary �������ʒl���Z�b�g���ꂽBean
	 * @throws Exception
	 */
	protected ItemSummary mapping(ResultSet rs) throws Exception {

		ItemSummary bean = new ItemSummary();

		// DB�f�[�^�� Bean �ɃZ�b�g
		bean.setKmtCode(rs.getString("KMT_CODE"));
		bean.setKmtName(rs.getString("KMT_NAME_S"));
		bean.setKmkCode(rs.getString("KMK_CODE"));
		bean.setKmkName(rs.getString("KMK_NAME"));
		bean.setKmkNamek(rs.getString("KMK_NAME_K"));
		bean.setKokName(rs.getString("KOK_NAME"));
		bean.setSumCode(rs.getString("SUM_CODE"));
		bean.setSumName(rs.getString("SUM_NAME"));
		bean.setOdr(rs.getString("ODR"));
		bean.setHyjKbn(rs.getInt("HYJ_KBN"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setItemSumType(ItemSumType.get(rs.getInt("SUM_KBN")));
		bean.setItemType(ItemType.get(rs.getInt("KMK_SHU")));
		bean.setDc(Dc.getDc(rs.getInt("DC_KBN")));

		return bean;
	}

	/**
	 * �������ʒl��Bean�ɃZ�b�g
	 * 
	 * @param rs ResultSet ��������
	 * @return ItemSummary �������ʒl���Z�b�g���ꂽBean
	 * @throws Exception
	 */
	protected ItemSummary mappingRef(ResultSet rs) throws Exception {

		ItemSummary bean = new ItemSummary();

		// DB�f�[�^�� Bean �ɃZ�b�g
		bean.setKmkCode(rs.getString("KMK_CODE"));
		bean.setKmkName(rs.getString("KMK_NAME_S"));
		bean.setKmkNamek(rs.getString("KMK_NAME_K"));

		return bean;
	}

	/**
	 * �Ȗڃ}�X�^�ɑ��݁A�ȖڏW�v�}�X�^�ɑ��݂��Ȃ��Ȗڃ`�F�b�N<br>
	 * �Ȗڑ̌n�R�[�h��00�F��{��v�̂�<br>
	 * 
	 * @param orgCode
	 * @return boolean
	 * @throws TException
	 */
	public String getNotExistItemSum(String orgCode) throws TException {

		String kmk = null;
		Connection conn = DBUtil.getConnection();

		// �Ȗڑ̌n�R�[�h��00:��{��v�ȊO�̓`�F�b�N�s�v
		if (!"00".equals(orgCode)) {
			return null;
		}

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT k.KMK_CODE ");
			sql.add("       ,k.KMK_NAME ");
			sql.add(" FROM KMK_MST k ");
			sql.add(" WHERE KAI_CODE = ? ", getCompanyCode());
			sql.add("   AND CASE WHEN k.SUM_KBN IS NULL ");
			sql.add("            THEN 0 ");
			sql.add("            ELSE k.SUM_KBN ");
			sql.add("        END = 0 ");
			sql.add("   AND NOT EXISTS (SELECT 1 ");
			sql.add("                   FROM KMK_SUM_MST s ");
			sql.add("                   WHERE k.KAI_CODE = s.KAI_CODE ");
			sql.add("                     AND k.KMK_CODE = s.KMK_CODE ");
			sql.add("                     AND s.KMT_CODE = '00' ");
			sql.add("                  ) ");
			sql.add(" ORDER BY k.KMK_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append(Util.avoidNull(rs.getString("KMK_CODE")));
				sb.append(" ");
				sb.append(Util.avoidNull(rs.getString("KMK_NAME")));
				kmk = sb.toString();
				break;
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
		return kmk;
	}

	/**
	 * �Ȗڃ}�X�^�f�[�^�����݂��Ȃ��W�v�Ȗڃ`�F�b�N
	 * 
	 * @param orgCode
	 * @return boolean
	 * @throws TException
	 */
	public String getNotExistItem(String orgCode) throws TException {

		String kmk = null;
		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT s.SUM_CODE ");
			sql.add("       ,kmk.KMK_NAME ");
			sql.add(" FROM KMK_SUM_MST s ");
			sql.add(" LEFT OUTER JOIN KMK_MST kmk ON kmk.KAI_CODE = s.KAI_CODE ");
			sql.add("                            AND kmk.KMK_CODE = s.SUM_CODE ");
			sql.add(" WHERE s.KAI_CODE = ? ", getCompanyCode());
			sql.add("   AND s.KMT_CODE = ? ", orgCode);
			sql.add("   AND NOT EXISTS (SELECT 1 ");
			sql.add("                   FROM KMK_MST k ");
			sql.add("                   WHERE s.KAI_CODE = k.KAI_CODE ");
			sql.add("                     AND s.SUM_CODE = k.KMK_CODE ");
			sql.add("                  ) ");
			sql.add(" ORDER BY s.SUM_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append(Util.avoidNull(rs.getString("SUM_CODE")));
				sb.append(" ");
				sb.append(Util.avoidNull(rs.getString("KMK_NAME")));
				kmk = sb.toString();
				break;
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
		return kmk;
	}
}