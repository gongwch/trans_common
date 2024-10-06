package jp.co.ais.trans2.model.tag;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * MG0460TagMaster - �tⳃ}�X�^ - Model Class
 * 
 * @author AIS
 */
public class TagManagerImpl extends TModel implements TagManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List ��������
	 * @throws TException
	 */
	public List<Tag> get(TagSearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<Tag> list = new ArrayList<Tag>();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("SELECT");
			sql.add("    KAI_CODE");
			sql.add("    ,TAG_CODE");
			sql.add("    ,TAG_COLOR");
			sql.add("    ,TAG_TITLE");
			sql.add("    ,INP_DATE");
			sql.add("    ,UPD_DATE");
			sql.add("    ,PRG_ID");
			sql.add("    ,USR_ID");
			sql.add("FROM TAG_MST");

			sql.add("WHERE KAI_CODE = ?", condition.getCompanyCode());
			if (!Util.isNullOrEmpty(condition.getTagCode())) {
				sql.add("AND TAG_CODE = ?", condition.getTagCode());
			}
			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addNLikeFront("  AND TAG_CODE ?", condition.getCodeLike());
			}
			// �^�C�g�������܂�
			if (!Util.isNullOrEmpty(condition.getTitleLike())) {
				sql.addLikeAmbi("  AND TAG_TITLE ?", condition.getTitleLike());
			}
			sql.add("ORDER BY TAG_CODE ");

			// DB���[�e�B���e�B
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
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(Tag bean) throws TException {
		bean.setInpDate(getNow());

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("INSERT INTO TAG_MST (");
			sql.add("    KAI_CODE");
			sql.add("    ,TAG_CODE");
			sql.add("    ,TAG_COLOR");
			sql.add("    ,TAG_TITLE");
			sql.add("    ,INP_DATE");
			sql.add("    ,PRG_ID");
			sql.add("    ,USR_ID");
			sql.add(") VALUES (");
			sql.add("    ?,", getCompanyCode());
			sql.add("    ?,", bean.getCode());

			if (!Util.isNullOrEmpty(bean.getColor())) {
				sql.add("     ?,", Util.to16RGBColorCode(bean.getColor()));
			} else {
				sql.add("     ?,", Util.to16RGBColorCode(Color.WHITE));
			}
			sql.add("    ?,", bean.getTitle());
			sql.adt("    ?,", getNow());
			sql.add("    ?,", getProgramCode());
			sql.add("    ?", getUserCode());
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
	public void modify(Tag bean) throws TException {
		bean.setUpdDate(getNow());

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE  TAG_MST SET");
			sql.add("    TAG_CODE = ?,", bean.getCode());
			sql.add("    TAG_COLOR = ?,", Util.to16RGBColorCode(bean.getColor()));
			sql.add("    TAG_TITLE = ?,", bean.getTitle());
			sql.adt("    UPD_DATE = ?,", getNow());
			sql.add("    PRG_ID = ?,", getProgramCode());
			sql.add("    USR_ID = ?", getUserCode());
			sql.add("WHERE KAI_CODE = ?", getCompanyCode());
			sql.add("AND   TAG_CODE = ? ", bean.getCode());

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
	public void delete(Tag bean) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM TAG_MST ");
			sql.add("WHERE KAI_CODE = ? ", getCompanyCode());
			sql.add("AND   TAG_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

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
	 * @return been �������ʒl���Z�b�g���ꂽBean
	 * @throws Exception
	 */
	protected Tag mapping(ResultSet rs) throws Exception {

		Tag bean = new Tag();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("TAG_CODE"));
		bean.setTitle(rs.getString("TAG_TITLE"));
		int[] rgbCode = Util.toRGBColorCode(rs.getString("TAG_COLOR"));
		bean.setColor(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));
		bean.setInpDate(rs.getTimestamp("INP_DATE"));
		bean.setUpdDate(rs.getTimestamp("UPD_DATE"));
		bean.setProgramCode(rs.getString("PRG_ID"));
		bean.setUserCode(rs.getString("USR_ID"));

		return bean;

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