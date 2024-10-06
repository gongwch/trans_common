package jp.co.ais.trans2.model.code;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * �R�[�h�}�X�^���ʃ}�l�[�W������
 */
public class CodeManagerImpl extends TModel implements CodeManager {

	/**
	 * �R�[�h�}�X�^���X�g�̎擾
	 * 
	 * @param condition
	 * @return �R�[�h�}�X�^���X�g
	 * @throws TException
	 */
	public List<OP_CODE_MST> get(CodeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<OP_CODE_MST> list = new ArrayList<OP_CODE_MST>();
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT cm.* ");
			sql.add(" FROM   OP_CODE_MST cm ");
			sql.add(" WHERE  cm.KAI_CODE = ? ", condition.getKAI_CODE()); // ��ЃR�[�h

			if (!Util.isNullOrEmpty(condition.getCODE_DIV())) {
				sql.add(" AND    cm.CODE_DIV = ? ", condition.getCODE_DIV()); // �R�[�h�敪
			}

			if (!Util.isNullOrEmpty(condition.getCODE())) {
				sql.add(" AND    cm.CODE = ? ", condition.getCODE()); // �R�[�h
			}

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cm.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR cm.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			// ���q�E�O�q�ʎ擾
			if (condition.getLocal() != null) {
				sql.add(" AND cm.LCL_KBN = ? ", BooleanUtil.toInt(condition.getLocal()));
			}

			sql.add(" ORDER BY cm.KAI_CODE ");
			sql.add("         ,cm.CODE_DIV ");
			sql.add("         ,cm.DISP_ODR ");
			sql.add("         ,cm.CODE ");

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
	 * �}�b�s���O
	 * 
	 * @param rs
	 * @return ����
	 * @throws Exception
	 */
	protected OP_CODE_MST mapping(ResultSet rs) throws Exception {

		OP_CODE_MST bean = createEntity();

		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setCODE(rs.getString("CODE")); // �R�[�h
		bean.setCODE_DIV(rs.getString("CODE_DIV")); // �R�[�h�敪
		bean.setDISP_ODR(rs.getInt("DISP_ODR")); // �\����
		bean.setCODE_NAME(rs.getString("CODE_NAME")); // �R�[�h��
		bean.setLCL_KBN(rs.getInt("LCL_KBN")); // ���q�敪�i1:���q�j
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // �o�^�N����
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // �X�V�N����
		bean.setPRG_ID(rs.getString("PRG_ID")); // �v���O����ID
		bean.setUSR_ID(rs.getString("USR_ID")); // ���[�U�[ID

		return bean;
	}

	/**
	 * @return ����
	 */
	protected OP_CODE_MST createEntity() {
		return new OP_CODE_MST();
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CodeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   OP_CODE_MST cm ");
			sql.add(" WHERE  cm.KAI_CODE = ? ", condition.getKAI_CODE()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cm.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cm.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cm.INP_DATE IS NULL AND cm.UPD_DATE IS NULL) ");
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

	/**
	 * SQL�p
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
