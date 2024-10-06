package jp.co.ais.trans2.model.slip;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * �`�[�tⳂ̎����N���X�ł��B
 * 
 * @author AIS
 */
public class SlipTagManagerImpl extends TModel implements SlipTagManager {

	/**
	 * ����̓`�[�̕tⳏ���ݒ肷��
	 * 
	 * @param slip �`�[
	 * @return �tⳏ��<�t�@�C����, �o�C�i��>
	 */
	public List<SWK_TAG> get(Slip slip) throws TException {
		return get(slip.getCompanyCode(), slip.getSlipNo());
	}

	/**
	 * ����̓`�[�̕tⳏ���ݒ肷��
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return �tⳏ��<�t�@�C����, �o�C�i��>
	 */
	public List<SWK_TAG> get(String companyCode, String slipNo) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			return get(conn, companyCode, slipNo);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ����̓`�[�̕tⳏ���ݒ肷��
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @return �tⳏ��<�t�@�C����, �o�C�i��>
	 * @throws Exception
	 */
	protected List<SWK_TAG> get(Connection conn, String companyCode, String slipNo) throws Exception {

		if (Util.isNullOrEmpty(slipNo)) {
			return null;
		}

		List<SWK_TAG> list = new ArrayList<SWK_TAG>();

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT KAI_CODE ");
		sql.add("       ,SWK_DEN_NO ");
		sql.add("       ,SEQ ");
		sql.add("       ,TAG_CODE ");
		sql.add("       ,TAG_COLOR ");
		sql.add("       ,TAG_TITLE ");
		sql.add("       ,INP_DATE ");
		sql.add("       ,UPD_DATE ");
		sql.add("       ,PRG_ID ");
		sql.add("       ,USR_ID ");
		sql.add(" FROM   SWK_TAG ");
		sql.add(" WHERE 1 = 1 ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(companyCode)) {
			sql.add(" AND   KAI_CODE = ? ", companyCode);
		}

		// �`�[�ԍ�
		if (!Util.isNullOrEmpty(slipNo)) {
			sql.add(" AND   SWK_DEN_NO = ? ", slipNo);
		}

		sql.add(" ORDER BY SEQ ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		while (rs.next()) {
			list.add(mapping(rs));
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;

	}

	/**
	 * �}�b�s���O����
	 * 
	 * @param rs
	 * @return �t�
	 * @throws Exception
	 */
	protected SWK_TAG mapping(ResultSet rs) throws Exception {

		SWK_TAG bean = createBean();
		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSEQ(rs.getInt("SEQ"));
		bean.setTAG_CODE(rs.getString("TAG_CODE"));
		int[] rgbCode = Util.toRGBColorCode(rs.getString("TAG_COLOR"));
		bean.setTAG_COLOR(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));
		bean.setTAG_TITLE(rs.getString("TAG_TITLE"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));

		return bean;
	}

	/**
	 * @return �G���e�B�e�B�쐬
	 */
	protected SWK_TAG createBean() {
		return new SWK_TAG();
	}

	/**
	 * �`�[�tⳂ̓o�^
	 * 
	 * @param slip �`�[
	 */
	public void entry(Slip slip) {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			// �r���`�F�b�N
			checkLock(conn, slip.getCompanyCode(), slip.getSlipNo(), slip.getHeader().getSWK_UPD_CNT());

			// �폜
			if (getCompany().getAccountConfig().isUseGroupAccount() && getCompanyCode().equals(slip.getCompanyCode())) {
				// �e��Ђ̓`�[�����O�C����Ђ��O���[�v��v�g���ꍇ�A�`�[�ԍ��ō폜
				delete(null, slip.getSlipNo(), null);
			} else if (getCompany().getAccountConfig().isUseGroupAccount()
				&& !getCompanyCode().equals(slip.getCompanyCode())) {
				// �q��Њ����O�C����Ђ��O���[�v��v�g���ꍇ�A�����s�v
				return;
			} else {
				delete(slip.getCompanyCode(), slip.getSlipNo(), null);
			}

			// Map<String, byte[]>
			List<SWK_TAG> swkTags = slip.getHeader().getSwkTags();

			if (swkTags == null || swkTags.size() == 0) {
				// �tⳂȂ��̏ꍇ�AINSERT�s�v
				return;
			}

			// �tⳂ̓o�^
			for (SWK_TAG entity : swkTags) {

				// �`�[�ԍ��ݒ�
				entity.setKAI_CODE(slip.getCompanyCode());
				entity.setSWK_DEN_NO(slip.getSlipNo());

				if (entity.getINP_DATE() == null) {
					entity.setINP_DATE(getNow());
				}
				if (Util.isNullOrEmpty(entity.getPRG_ID())) {
					entity.setPRG_ID(getProgramCode());
				}
				if (Util.isNullOrEmpty(entity.getUSR_ID())) {
					entity.setUSR_ID(getUserCode());
				}

				insert(conn, entity);
			}

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �tⳂ̓o�^
	 * 
	 * @param bean �t�
	 */
	public void entry(SWK_TAG bean) {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			// �r���`�F�b�N
			checkLock(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO());

			// �o�^����
			insert(conn, bean);

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * INSERT
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	protected void insert(Connection conn, SWK_TAG bean) throws TException {

		VCreator sql = new VCreator();

		sql.add(" INSERT INTO SWK_TAG ");
		sql.add("   (KAI_CODE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SEQ ");
		sql.add("   ,TAG_CODE ");
		sql.add("   ,TAG_COLOR ");
		sql.add("   ,TAG_TITLE ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID) ");
		sql.add(" VALUES ");
		sql.add("   (? ", bean.getKAI_CODE());
		sql.add("   ,? ", bean.getSWK_DEN_NO());
		sql.add("   ,? ", bean.getSEQ());
		sql.add("   ,? ", bean.getTAG_CODE());
		sql.add("   ,? ", Util.to16RGBColorCode(bean.getTAG_COLOR()));
		sql.add("   ,? ", bean.getTAG_TITLE());
		sql.adt("   ,? ", bean.getINP_DATE());
		sql.add("   ,? ", bean.getPRG_ID());
		sql.add("   ,? ", bean.getUSR_ID());
		sql.add("   ) ");

		DBUtil.execute(conn, sql);
	}

	/**
	 * �tⳂɑ΂���`�[�̔r���`�F�b�N
	 * 
	 * @param conn
	 * @param kaiCode
	 * @param slipNo
	 * @throws Exception
	 */
	protected void checkLock(Connection conn, String kaiCode, String slipNo) throws Exception {
		checkLock(conn, kaiCode, slipNo, -1);
	}

	/**
	 * �tⳂɑ΂���`�[�̔r���`�F�b�N
	 * 
	 * @param conn
	 * @param kaiCode
	 * @param slipNo
	 * @param slipUpdCnt
	 * @throws Exception
	 */
	protected void checkLock(Connection conn, String kaiCode, String slipNo, int slipUpdCnt) throws Exception {

		// �r���`�F�b�N
		SlipManager sm = (SlipManager) getComponent(SlipManager.class);
		sm.lockSlipTable();

		VCreator where = new VCreator();
		where.add("         AND    KAI_CODE = ? ", kaiCode);
		where.add("         AND    SWK_DEN_NO = ? ", slipNo);
		if (slipUpdCnt >= 0) {
			where.add("         AND    SWK_UPD_CNT = ? ", slipUpdCnt);
		}

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT SUM(CNT) ");
		sql.add(" FROM   (SELECT COUNT(*) cnt ");
		sql.add("         FROM   GL_SWK_HDR ");
		sql.add("         WHERE  SWK_SHR_KBN = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AP_SWK_HDR ");
		sql.add("         WHERE  SWK_SHR_KBN = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AR_SWK_HDR ");
		sql.add("         WHERE  SWK_SHR_KBN = 1 ");
		sql.add(where.toSQL());
		sql.add("        ) s ");

		int count = DBUtil.selectOneInt(conn, sql.toSQL());
		if (count != 0) {
			// �w��̓`�[�͑����[�U�[���ҏW���ł��B
			throw new TRuntimeException("W00123");
		}

		sql = new VCreator();
		sql.add(" SELECT SUM(CNT) ");
		sql.add(" FROM   (SELECT COUNT(*) cnt ");
		sql.add("         FROM   GL_SWK_HDR ");
		sql.add("         WHERE  1 = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AP_SWK_HDR ");
		sql.add("         WHERE  1 = 1 ");
		sql.add(where.toSQL());
		sql.add("         UNION ALL ");
		sql.add("         SELECT COUNT(*) cnt ");
		sql.add("         FROM   AR_SWK_HDR ");
		sql.add("         WHERE  1 = 1 ");
		sql.add(where.toSQL());
		sql.add("        ) s ");
		count = DBUtil.selectOneInt(conn, sql.toSQL());
		if (count == 0) {
			// �w��̓`�[�͑��[���ōX�V����Ă��܂��B{0}
			throw new TRuntimeException("I00070", slipNo);
		}

	}

	/**
	 * �tⳂɑ΂���`�[�̏��F�����̃`�F�b�N
	 * 
	 * @param conn
	 * @param bean �t�
	 * @param minDate �ŏ�����
	 * @throws Exception
	 */
	protected void checkApproveTimestamp(Connection conn, SWK_TAG bean, Date minDate) throws Exception {

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT KAI_CODE ");
		sql.add("       ,SWK_DEN_NO ");
		sql.add("       ,SWK_UPD_KBN ");
		sql.add("       ,INP_DATE ");
		sql.add(" FROM   SWK_SYO_RRK ");
		sql.add(" WHERE  KAI_CODE = ? ", bean.getKAI_CODE());
		sql.add(" AND    SWK_DEN_NO = ? ", bean.getSWK_DEN_NO());
		sql.add(" ORDER BY INP_DATE DESC ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		while (rs.next()) {
			// 1�s�ڂ̂ݔ���:���ꏳ�F�A�o�����F�A�X�V�̏ꍇ
			int updKbn = rs.getInt("SWK_UPD_KBN");

			switch (updKbn) {
				case 2:
				case 3:
				case 4:
					Date inpDate = rs.getTimestamp("INP_DATE");
					if (inpDate != null && Util.isSmallerThenByYMDHMS(minDate, inpDate)) {
						// ���F���̕tⳃt�@�C���͍폜�ł��܂���B
						throw new TRuntimeException("I00534");
					}
					break;
			}

			// 1�s�ڂ̂ݔ���
			break;
		}

		DBUtil.close(rs);
		DBUtil.close(statement);
	}

	/**
	 * �tⳏ��̍폜
	 * 
	 * @param list List<Tag>
	 */
	public void delete(List<SWK_TAG> list) {

		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			if (list == null || list.isEmpty()) {
				return;
			}

			SWK_TAG bean = list.get(0);

			// �r���`�F�b�N
			checkLock(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO());

			Date minDate = bean.getINP_DATE();
			for (SWK_TAG tag : list) {
				minDate = DateUtil.minYMDHMS(minDate, tag.getINP_DATE());
			}

			// �ŏ������ŏ��F�ς݂̃`�F�b�N���s��
			if (minDate != null) {
				checkApproveTimestamp(conn, bean, minDate);
			}

			for (SWK_TAG tag : list) {
				delete(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO(), tag.getSEQ());
			}

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ����̓`�[�̕tⳏ��̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo) {
		delete(companyCode, slipNo, null);
	}

	/**
	 * ����̓`�[�̕tⳏ��̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 */
	public void delete(String companyCode, String slipNo, Integer sEQ) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();

			// �폜����
			delete(conn, companyCode, slipNo, sEQ);

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ����̓`�[�̕tⳏ��̍폜
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 * @throws Exception
	 */
	protected void delete(Connection conn, String companyCode, String slipNo, Integer sEQ) throws Exception {

		// �폜
		VCreator sql = new VCreator();

		sql.add(" DELETE FROM SWK_TAG ");
		sql.add(" WHERE  SWK_DEN_NO = ? ", slipNo);

		if (!Util.isNullOrEmpty(companyCode)) {
			sql.add(" AND    KAI_CODE = ? ", companyCode);
		}
		if (sEQ != null) {
			sql.add(" AND    SEQ = ? ", sEQ);
		}

		DBUtil.execute(conn, sql);
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
