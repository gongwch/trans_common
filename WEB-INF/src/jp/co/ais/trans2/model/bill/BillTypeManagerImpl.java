package jp.co.ais.trans2.model.bill;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �����敪�}�X�^�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class BillTypeManagerImpl extends TModel implements BillTypeManager {

	/**
	 * �w������ɊY�����鐿���敪�}�X�^����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����鐿���敪�}�X�^���
	 * @throws TException
	 */
	public List<BillType> get(BillTypeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<BillType> list = new ArrayList<BillType>();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT sei.KAI_CODE, ");
			sql.add("        sei.SEI_KBN, ");
			sql.add("        sei.SEI_NAME, ");
			sql.add("        sei.SEI_NAME_K, ");
			sql.add("        sei.SEI_FORM_DIR, ");
			sql.add("        sei.SEI_DTL_CNT, ");
			sql.add("        sei.STR_DATE, ");
			sql.add("        sei.END_DATE, ");
			sql.add("        sei.INP_DATE, ");
			sql.add("        sei.UPD_DATE, ");
			sql.add("        sei.PRG_ID, ");
			sql.add("        sei.USR_ID ");
			sql.add("   FROM AR_SEI_MST sei");
			sql.add("  WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));

			sql.add(" ORDER BY sei.SEI_KBN ");

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
	 * �������̍쐬
	 * 
	 * @param condition
	 * @return ������
	 */
	protected String createSelectWhereSql(BillTypeSearchCondition condition) {

		SQLCreator sql = new SQLCreator();

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("   AND sei.KAI_CODE = ?", condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("   AND sei.SEI_KBN = ? ", condition.getCode());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("   AND sei.SEI_KBN >= ? ", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("   AND sei.SEI_KBN <= ? ", condition.getCodeTo());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("   AND sei.SEI_KBN ? ", condition.getCodeLike());
		}

		// ���̂����܂�
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("   AND sei.SEI_NAME ? ", condition.getNameLike());
		}

		// �������̂����܂�
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi("   AND sei.SEI_NAME_K ? ", condition.getNamekLike());
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("   AND sei.STR_DATE <= ? ", condition.getValidTerm());
			sql.add("   AND sei.END_DATE >= ? ", condition.getValidTerm());
		}

		return sql.toSQL();
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected BillType mapping(ResultSet rs) throws Exception {

		BillType bean = new BillType();

		bean.setCompanyCode(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setCode(rs.getString("SEI_KBN")); // �����敪
		bean.setName(rs.getString("SEI_NAME")); // ����
		bean.setNamek(rs.getString("SEI_NAME_K")); // ��������
		bean.setFormat(rs.getString("SEI_FORM_DIR")); // �������t�H�[�}�b�g
		bean.setDetailCount(rs.getInt("SEI_DTL_CNT")); // ���׌���
		bean.setDateFrom(rs.getDate("STR_DATE")); // �L�����ԊJ�n
		bean.setDateTo(rs.getDate("END_DATE")); // �L�����ԏI��

		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setPrgId(rs.getString("PRG_ID"));
		bean.setUsrId(rs.getString("USR_ID"));

		return bean;

	}

	/**
	 * �����敪�}�X�^��o�^����B
	 * 
	 * @param bean �����敪�}�X�^
	 * @throws TException
	 */
	public void entry(BillType bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("INSERT INTO AR_SEI_MST (");
			sql.add("  KAI_CODE, ");
			sql.add("  SEI_KBN, ");
			sql.add("  SEI_NAME, ");
			sql.add("  SEI_NAME_K, ");
			sql.add("  SEI_FORM_DIR, ");
			sql.add("  SEI_DTL_CNT, ");
			sql.add("  STR_DATE, ");
			sql.add("  END_DATE, ");
			sql.add("  INP_DATE, ");
			sql.add("  UPD_DATE, ");
			sql.add("  PRG_ID, ");
			sql.add("  USR_ID ");
			sql.add(") VALUES (");
			sql.add("  ?, ", bean.getCompanyCode()); // ��ЃR�[�h
			sql.add("  ?, ", bean.getCode()); // �����敪
			sql.add("  ?, ", bean.getName()); // ��������
			sql.add("  ?, ", bean.getNamek()); // ��������
			sql.add("  ?, ", bean.getFormat()); // �������t�H�[�}�b�g
			sql.add("  ?, ", bean.getDetailCount()); // ���׌���
			sql.add("  ?, ", bean.getDateFrom()); // �J�n�N����
			sql.add("  ?, ", bean.getDateTo()); // �I���N����
			sql.addYMDHMS("  ?, ", bean.getInpDate()); // �o�^���t
			sql.addYMDHMS("  ?, ", bean.getUpdDate()); // �X�V���t
			sql.add("  ?, ", bean.getPrgId()); // �v���O�����h�c
			sql.add("  ?  ", bean.getUsrId()); // ���[�U�[�h�c
			sql.add(")");

			DBUtil.execute(sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �����敪�}�X�^���폜����B
	 * 
	 * @param bean �����敪�}�X�^
	 * @throws TException
	 */
	public void delete(BillType bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("DELETE FROM AR_SEI_MST");
			sql.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("   AND SEI_KBN = ? ", bean.getCode());

			DBUtil.execute(sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �����敪�}�X�^��o�^����B
	 * 
	 * @param bean �����敪�}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public BillType entryMaster(BillType bean) throws TException {

		// �폜
		delete(bean);

		// ���ݒ�
		if (Util.isNullOrEmpty(bean.getInpDate())) {
			bean.setInpDate(getNow());
		} else {
			bean.setUpdDate(getNow());
		}
		bean.setPrgId(getProgramCode());
		bean.setUsrId(getUserCode());

		// �o�^
		entry(bean);

		return bean;
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(BillTypeSearchCondition condition) throws TException {

		try {

			// ��Ѓf�[�^�𒊏o
			List<BillType> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			BillTypeExcel exl = new BillTypeExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}
}