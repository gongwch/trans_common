package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.model.*;

/**
 * �`�[�Y�t�̎����N���X�ł��B
 * 
 * @author AIS
 */
public class SlipAttachmentManagerImpl extends TModel implements SlipAttachmentManager {

	/**
	 * ����̓`�[�̓Y�t����ݒ肷��
	 * 
	 * @param slip �`�[
	 * @return �Y�t���<�t�@�C����, �o�C�i��>
	 */
	public List<SWK_ATTACH> get(Slip slip) throws TException {
		return get(slip.getCompanyCode(), slip.getSlipNo());
	}

	/**
	 * ����̓`�[�̓Y�t����ݒ肷��
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return �Y�t���<�t�@�C����, �o�C�i��>
	 */
	public List<SWK_ATTACH> get(String companyCode, String slipNo) throws TException {

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
	 * ����̓`�[�̓Y�t����ݒ肷��
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @return �Y�t���<�t�@�C����, �o�C�i��>
	 * @throws Exception
	 */
	protected List<SWK_ATTACH> get(Connection conn, String companyCode, String slipNo) throws Exception {

		if (Util.isNullOrEmpty(slipNo)) {
			return null;
		}

		List<SWK_ATTACH> list = new ArrayList<SWK_ATTACH>();

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT att.KAI_CODE ");
		sql.add("       ,att.SWK_DEN_NO ");
		sql.add("       ,att.SEQ ");
		sql.add("       ,att.EMP_CODE ");
		sql.add("       ,emp.EMP_NAME ");
		sql.add("       ,emp.EMP_NAME_S ");
		sql.add("       ,att.FILE_NAME ");
		sql.add("       ,att.SRV_FILE_NAME ");
		sql.add("       ,att.INP_DATE ");
		sql.add("       ,att.PRG_ID ");
		sql.add("       ,att.USR_ID ");
		sql.add(" FROM   SWK_ATTACH att ");
		sql.add(" LEFT JOIN EMP_MST emp ON  emp.KAI_CODE = att.KAI_CODE ");
		sql.add("                       AND emp.EMP_CODE = att.EMP_CODE ");
		sql.add(" WHERE 1 = 1 ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(companyCode)) {
			sql.add(" AND   att.KAI_CODE = ? ", companyCode);
		}

		// �`�[�ԍ�
		if (!Util.isNullOrEmpty(slipNo)) {
			sql.add(" AND   att.SWK_DEN_NO = ? ", slipNo);
		}

		sql.add(" ORDER BY att.SEQ ");

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
	 * @return �Y�t
	 * @throws Exception
	 */
	protected SWK_ATTACH mapping(ResultSet rs) throws Exception {

		String fileName = rs.getString("FILE_NAME");
		String serverFileName = rs.getString("SRV_FILE_NAME");

		SWK_ATTACH bean = createBean();
		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSEQ(rs.getInt("SEQ"));
		bean.setEMP_CODE(rs.getString("EMP_CODE"));
		bean.setEMP_NAME(rs.getString("EMP_NAME"));
		bean.setEMP_NAME_S(rs.getString("EMP_NAME_S"));
		bean.setFILE_NAME(fileName);
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));

		// �t�@�C���o�C�i���擾
		bean.setFILE_DATA(FileTransferUtil.getServerFile(fileName, serverFileName));

		return bean;
	}

	/**
	 * @return �G���e�B�e�B�쐬
	 */
	protected SWK_ATTACH createBean() {
		return new SWK_ATTACH();
	}

	/**
	 * �`�[�Y�t�̓o�^
	 * 
	 * @param slip �`�[
	 */
	public void entry(Slip slip) {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

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
			List<SWK_ATTACH> attachments = slip.getHeader().getAttachments();

			if (attachments == null || attachments.isEmpty()) {
				// �Y�t�Ȃ��̏ꍇ�AINSERT�s�v
				return;
			}

			int seq = 1;

			// �Y�t�̓o�^
			for (SWK_ATTACH entity : attachments) {

				// �`�[�ԍ��ݒ�
				entity.setKAI_CODE(slip.getCompanyCode());
				entity.setSWK_DEN_NO(slip.getSlipNo());
				entity.setSEQ(seq++);

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
	 * �Y�t�̓o�^
	 * 
	 * @param bean �Y�t
	 */
	public void entry(SWK_ATTACH bean) {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();

			// �r���`�F�b�N
			checkLock(conn, bean);

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
	protected void insert(Connection conn, SWK_ATTACH bean) throws TException {

		if (!Util.isNullOrEmpty(bean.getFile())) {
			// �T�[�o�T�C�g�ꎞ�t�@�C�����w��t�H���_�֕ۑ�
			String serverFileName = FileTransferUtil.createServerFile(bean.getFile());
			bean.setServerFileName(serverFileName);
		}

		// �폜
		VCreator sql = new VCreator();

		sql.add(" INSERT INTO SWK_ATTACH ");
		sql.add("   (KAI_CODE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SEQ ");
		sql.add("   ,EMP_CODE ");
		sql.add("   ,FILE_NAME ");
		sql.add("   ,SRV_FILE_NAME ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID) ");
		sql.add(" VALUES ");
		sql.add("   (? ", bean.getKAI_CODE());
		sql.add("   ,? ", bean.getSWK_DEN_NO());
		sql.add("   ,? ", bean.getSEQ());
		sql.add("   ,? ", bean.getEMP_CODE());
		sql.add("   ,? ", bean.getFILE_NAME());
		sql.add("   ,? ", bean.getServerFileName());
		sql.adt("   ,? ", bean.getINP_DATE());
		sql.add("   ,? ", bean.getPRG_ID());
		sql.add("   ,? ", bean.getUSR_ID());
		sql.add("   ) ");

		DBUtil.execute(conn, sql);
	}

	/**
	 * �Y�t�ɑ΂���`�[�̔r���`�F�b�N
	 * 
	 * @param conn
	 * @param bean �Y�t
	 * @throws Exception
	 */
	protected void checkLock(Connection conn, SWK_ATTACH bean) throws Exception {

		// �r���`�F�b�N
		SlipManager sm = (SlipManager) getComponent(SlipManager.class);
		sm.lockSlipTable();

		VCreator where = new VCreator();
		where.add("         AND    KAI_CODE = ? ", bean.getKAI_CODE());
		where.add("         AND    SWK_DEN_NO = ? ", bean.getSWK_DEN_NO());

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
	}

	/**
	 * �Y�t�ɑ΂���`�[�̏��F�����̃`�F�b�N
	 * 
	 * @param conn
	 * @param bean �Y�t
	 * @param minDate �ŏ�����
	 * @throws Exception
	 */
	protected void checkApproveTimestamp(Connection conn, SWK_ATTACH bean, Date minDate) throws Exception {

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
						// ���F���̓Y�t�t�@�C���͍폜�ł��܂���B
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
	 * �Y�t���̍폜
	 * 
	 * @param list List<SWK_ATTACH>
	 */
	public void delete(List<SWK_ATTACH> list) {

		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			if (list == null || list.isEmpty()) {
				return;
			}

			SWK_ATTACH bean = list.get(0);

			// �r���`�F�b�N
			checkLock(conn, bean);

			Date minDate = bean.getINP_DATE();
			for (SWK_ATTACH att : list) {
				minDate = DateUtil.minYMDHMS(minDate, att.getINP_DATE());
			}

			// �ŏ������ŏ��F�ς݂̃`�F�b�N���s��
			if (minDate != null) {
				checkApproveTimestamp(conn, bean, minDate);
			}

			for (SWK_ATTACH att : list) {
				delete(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO(), att.getSEQ());
			}

			// SEQ����
			updateSeq(conn, bean.getKAI_CODE(), bean.getSWK_DEN_NO());

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * SEQ����
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @throws Exception
	 */
	protected void updateSeq(Connection conn, String companyCode, String slipNo) throws Exception {

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" UPDATE SWK_ATTACH o ");

		if (DBUtil.isUseMySQL) {
			sql.add(" INNER JOIN (SELECT KAI_CODE ");
			sql.add("                   ,SWK_DEN_NO ");
			sql.add("                   ,SEQ AS OLD_SEQ ");
			sql.add("                   ,@rownum := @rownum+1 AS SEQ ");
			sql.add("             FROM (SELECT @rownum:=0) r ");
			sql.add("                 ,SWK_ATTACH ");
			sql.add("             WHERE KAI_CODE   = ? ", companyCode);
			sql.add("               AND SWK_DEN_NO = ? ", slipNo);
			sql.add("             ORDER BY SEQ ");
			sql.add("                     ,INP_DATE ");
			sql.add("            ) p ");
			sql.add(" SET o.SEQ = p.SEQ ");
			sql.add(" WHERE o.KAI_CODE   = ? ", companyCode);
			sql.add("   AND o.SWK_DEN_NO = ? ", slipNo);
			sql.add("   AND o.SEQ        = p.OLD_SEQ ");

		} else {
			sql.add(" SET    SEQ = (SELECT SEQ ");
			sql.add(" 			  FROM   (SELECT KAI_CODE ");
			sql.add(" 							,SWK_DEN_NO ");
			sql.add(" 							,SEQ AS OLD_SEQ ");
			sql.add(" 							,ROWNUM AS SEQ ");
			sql.add(" 					  FROM   SWK_ATTACH ");
			sql.add(" 					  WHERE  KAI_CODE = ? ", companyCode);
			sql.add(" 					  AND    SWK_DEN_NO = ? ", slipNo);
			sql.add(" 					  ORDER  BY SEQ, INP_DATE) p ");
			sql.add(" 			  WHERE  p.KAI_CODE = o.KAI_CODE ");
			sql.add(" 			  AND    p.SWK_DEN_NO = o.SWK_DEN_NO ");
			sql.add(" 			  AND    p.OLD_SEQ = o.SEQ ");
			sql.add(" 					 ) ");
			sql.add(" WHERE  KAI_CODE = ? ", companyCode);
			sql.add(" AND    SWK_DEN_NO = ? ", slipNo);
		}

		DBUtil.execute(conn, sql);
	}

	/**
	 * ����̓`�[�̓Y�t���̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo) {
		delete(companyCode, slipNo, null);
	}

	/**
	 * ����̓`�[�̓Y�t���̍폜
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
	 * ����̓`�[�̓Y�t���̍폜
	 * 
	 * @param conn
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 * @throws Exception
	 */
	protected void delete(Connection conn, String companyCode, String slipNo, Integer sEQ) throws Exception {

		List<String> serverFileNameList = new ArrayList<String>();

		{
			// �擾
			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT SRV_FILE_NAME ");
			sql.add(" FROM   SWK_ATTACH att ");
			sql.add(" WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(companyCode)) {
				sql.add(" AND   att.KAI_CODE = ? ", companyCode);
			}

			// �`�[�ԍ�
			if (!Util.isNullOrEmpty(slipNo)) {
				sql.add(" AND   att.SWK_DEN_NO = ? ", slipNo);
			}
			if (sEQ != null) {
				sql.add(" AND   att.SEQ = ? ", sEQ);
			}

			sql.add(" ORDER BY att.SEQ ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				serverFileNameList.add(rs.getString("SRV_FILE_NAME"));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);
		}

		{
			// �폜
			VCreator sql = new VCreator();

			sql.add(" DELETE FROM SWK_ATTACH ");
			sql.add(" WHERE  SWK_DEN_NO = ? ", slipNo);

			if (!Util.isNullOrEmpty(companyCode)) {
				sql.add(" AND    KAI_CODE = ? ", companyCode);
			}
			if (sEQ != null) {
				sql.add(" AND    SEQ = ? ", sEQ);
			}

			DBUtil.execute(conn, sql);
		}

		if (!serverFileNameList.isEmpty()) {
			// �t�@�C���폜

			for (String serverFileName : serverFileNameList) {
				FileTransferUtil.deleteServerFile(serverFileName);
			}

		}

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
