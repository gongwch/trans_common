package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * SlipDao�̎���
 * 
 * @author AIS
 */
public class SlipDaoImpl extends TModel implements SlipDao {

	/** true:BS��������@�\�L�� */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:AP��������@�\���� */
	public static boolean isNotUseAP = ServerConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR��������@�\���� */
	public static boolean isNotUseAR = ServerConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:�`�[�ԍ�������v�L�� */
	public static boolean isUseSlipNoAmbiLike = ServerConfig.isFlagOn("trans.slip.use.slip.no.ambi.like");

	/** true:�t�@�C���Y�t�@�\�L�� */
	public static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** KMK_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKMK_MST = ServerConfig.isFlagOn("trans.slip.dao.KMK_MST.table");

	/** HKM_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseHKM_MST = ServerConfig.isFlagOn("trans.slip.dao.HKM_MST.table");

	/** UKM_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseUKM_MST = ServerConfig.isFlagOn("trans.slip.dao.UKM_MST.table");

	/** BMN_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseBMN_MST = ServerConfig.isFlagOn("trans.slip.dao.BMN_MST.table");

	/** TRI_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseTRI_MST = ServerConfig.isFlagOn("trans.slip.dao.TRI_MST.table");

	/** EMP_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseEMP_MST = ServerConfig.isFlagOn("trans.slip.dao.EMP_MST.table");

	/** DEN_SYU_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseDEN_SYU_MST = ServerConfig.isFlagOn("trans.slip.dao.DEN_SYU_MST.table");

	/** CUR_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseCUR_MST = ServerConfig.isFlagOn("trans.slip.dao.CUR_MST.table");

	/** AP_HOH_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseAP_HOH_MST = ServerConfig.isFlagOn("trans.slip.dao.AP_HOH_MST.table");

	/** AP_CBK_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseAP_CBK_MST = ServerConfig.isFlagOn("trans.slip.dao.AP_CBK_MST.table");

	/** TRI_SJ_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseTRI_SJ_MST = ServerConfig.isFlagOn("trans.slip.dao.TRI_SJ_MST.table");

	/** KNR1_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKNR1_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR1_MST.table");

	/** KNR2_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKNR2_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR2_MST.table");

	/** KNR3_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKNR3_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR3_MST.table");

	/** KNR4_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKNR4_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR4_MST.table");

	/** KNR5_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKNR5_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR5_MST.table");

	/** KNR6_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseKNR6_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR6_MST.table");

	/** SZEI_MST���� true:hdr.KAI_CODE�Afalse:getCompanyCode() */
	public static boolean isUseSZEI_MST = ServerConfig.isFlagOn("trans.slip.dao.SZEI_MST.table");

	/** true:�tⳋ@�\�L�� */
	public static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/** true: 2023INVOICE���x�Ή����g�p���� */
	public static boolean isUseInvoice = ServerConfig.isFlagOn("trans.slip.use.invoice.tax");

	/**
	 * �w�b�_�[�e�[�u���萔
	 */
	protected static final class HDR_TABLE {

		/** GL */
		public static final String GL = "GL";

		/** AP */
		public static final String AP = "AP";

		/** AR */
		public static final String AR = "AR";
	}

	/** �����Ώ� */
	protected enum SearchTable {
		/** �`�[ */
		Slip,

		/** �p�^�[�� */
		Pattern,

		/** ���� */
		History
	}

	/** �`�[�������� */
	protected SlipCondition param_ = null;

	/** �����Ώۃe�[�u�� */
	protected SearchTable searchTable_ = null;

	/** ���ג��o���邩�ǂ��� */
	protected boolean isDetail_ = false;

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @param searchTable �����Ώ�
	 * @return ���ʃ��X�g
	 */
	public List<SWK_HDR> findByCondition(SlipCondition param, SearchTable searchTable) {

		Connection conn = null;

		List<SWK_HDR> list = new ArrayList<SWK_HDR>();

		try {

			conn = DBUtil.getConnection();

			String sql = getDefaultSqlIncludeDetails(conn, param, searchTable, false);
			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingHeader(rs));
			}

			DBUtil.close(rs);
			if (param.isSearchWorkFlow()) {
				sql = getWorkflowApproveSQL(conn, param);
				rs = DBUtil.select(statement, sql);
				// ���F�����ێ����݂̂��擾
				List<SWK_SYO_CTL> ctlList = new ArrayList();
				while (rs.next()) {
					SWK_SYO_CTL ctl = new SWK_SYO_CTL();
					ctl.setKAI_CODE(rs.getString("KAI_CODE"));
					ctl.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
					ctl.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
					ctl.setSEQ(rs.getInt("SEQ"));
					ctl.setUPD_KBN(rs.getInt("UPD_KBN"));
					ctl.setAPRV_ROLE_GRP_CODE(rs.getString("APRV_ROLE_GRP_CODE"));
					ctl.setPREV_UPD_KBN(rs.getInt("PREV_UPD_KBN"));
					ctl.setAPRV_ROLE_CODE(rs.getString("APRV_ROLE_CODE"));
					ctl.setNEXT_APRV_ROLE_CODE(rs.getString("NEXT_APRV_ROLE_CODE"));
					ctl.setINP_DATE(rs.getDate("INP_DATE"));
					ctl.setPRG_ID(rs.getString("PRG_ID"));
					ctl.setUSR_ID(rs.getString("USR_ID"));

					ctl.setAprvRoleGroupName(rs.getString("APRV_ROLE_GRP_NAME"));
					ctl.setAprvRoleName(rs.getString("APRV_ROLE_NAME"));
					ctl.setNextAprvRoleName(rs.getString("NEXT_APRV_ROLE_NAME"));

					ctl.setAprvRoleSkippable(rs.getInt("APRV_SKIP_FLG") == 1);
					ctl.setNextAprvRoleSkippable(rs.getInt("NEXT_APRV_SKIP_FLG") == 1);

					ctlList.add(ctl);

				}
				DBUtil.close(rs);
				HDR: for (SWK_HDR hdr : list) {
					for (SWK_SYO_CTL ctl : ctlList) {
						if (Util.equals(ctl.getKAI_CODE(), hdr.getKAI_CODE())
							&& Util.equals(ctl.getSWK_DEN_NO(), hdr.getSWK_DEN_NO())
							&& DateUtil.equals(ctl.getSWK_DEN_DATE(), hdr.getSWK_DEN_DATE())) {
							hdr.setSyoCtl(ctl);
							// �ǉ����Ď��̃w�b�_��
							continue HDR;
						}
					}
				}
			}
			DBUtil.close(statement);
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// ���[�N�t���[���F���p��
				setupApproveGroup(list);
			}

		} catch (SlipNoDataException e) {
			return list;
		} catch (Exception e) {
			throw new TRuntimeException(e);

		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * @param list
	 * @throws TException
	 */
	protected void setupApproveGroup(List<SWK_HDR> list) throws TException {
		AprvRoleGroupManager mana = get(AprvRoleGroupManager.class);
		AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
		List<AprvRoleGroup> grpList = mana.get(con);
		if (grpList == null || grpList.isEmpty()) {
			// �}�X�^�񑶍ݎ��I��
			return;
		}
		HDR: for (SWK_HDR h : list) {
			if (Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
				continue;
			}
			for (AprvRoleGroup g : grpList) {
				if (Util.equals(g.getKAI_CODE(), h.getKAI_CODE()) && //
					Util.equals(g.getAPRV_ROLE_GRP_CODE(), h.getSWK_APRV_GRP_CODE())) {
					h.setAprRoleGroup(g);
					continue HDR;
				}
			}
		}

	}

	/**
	 * @param list
	 * @throws TException
	 */
	protected void setupSlipApproveGroup(List<Slip> list) throws TException {
		AprvRoleGroupManager mana = get(AprvRoleGroupManager.class);
		AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
		List<AprvRoleGroup> grpList = mana.get(con);
		if (grpList == null || grpList.isEmpty()) {
			// �}�X�^�񑶍ݎ��I��
			return;
		}
		HDR: for (Slip s : list) {
			SWK_HDR h = s.getHeader();
			if (Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
				continue;
			}
			for (AprvRoleGroup g : grpList) {
				if (Util.equals(g.getKAI_CODE(), h.getKAI_CODE()) && //
					Util.equals(g.getAPRV_ROLE_GRP_CODE(), h.getSWK_APRV_GRP_CODE())) {
					h.setAprRoleGroup(g);
					continue HDR;
				}
			}
		}

	}


	/**
	 * ���[�N�t���[���F�̍ŐV�󋵂��擾
	 * 
	 * @param conn
	 * @param param
	 * @return ���[�N�t���[���F�̍ŐV��
	 */
	protected String getWorkflowApproveSQL(Connection conn, SlipCondition param) {
		SQLCreator sql = new SQLCreator();
		sql.add(getWorkflowApproveSQL(conn, HDR_TABLE.GL, param));
		sql.add(" UNION ");
		sql.add(getWorkflowApproveSQL(conn, HDR_TABLE.AP, param));
		sql.add(" UNION ");
		sql.add(getWorkflowApproveSQL(conn, HDR_TABLE.AR, param));
		return sql.toString();
	}

	/**
	 * ���[�N�t���[���F�̍ŐV�󋵂��擾
	 * 
	 * @param conn
	 * @param targetHdr
	 * @param param
	 * @return ���[�N�t���[���F�̍ŐV��
	 */
	protected String getWorkflowApproveSQL(Connection conn, String targetHdr, SlipCondition param) {
		SQLCreator sql = new SQLCreator();
		sql.add("SELECT ctl.KAI_CODE ");
		sql.add("      ,ctl.SWK_DEN_DATE ");
		sql.add("      ,ctl.SWK_DEN_NO ");
		sql.add("      ,ctl.SEQ ");
		sql.add("      ,ctl.UPD_KBN ");
		sql.add("      ,ctl.PREV_UPD_KBN ");
		sql.add("      ,ctl.APRV_ROLE_CODE ");
		sql.add("      ,ctl.NEXT_APRV_ROLE_CODE ");
		sql.add("      ,ctl.INP_DATE ");
		sql.add("      ,ctl.PRG_ID ");
		sql.add("      ,ctl.USR_ID ");

		sql.add("      ,hdr.SWK_APRV_GRP_CODE APRV_ROLE_GRP_CODE ");
		sql.add("      ,grp.APRV_ROLE_GRP_NAME ");
		sql.add("      ,rol.APRV_ROLE_NAME ");
		sql.add("      ,rol.APRV_SKIP_FLG ");
		sql.add("      ,nex.APRV_ROLE_NAME NEXT_APRV_ROLE_NAME ");
		sql.add("      ,nex.APRV_SKIP_FLG NEXT_APRV_SKIP_FLG ");

		sql.add(" FROM SWK_SYO_CTL ctl ");
		sql.add(" INNER JOIN " + targetHdr + "_SWK_HDR hdr ON hdr.KAI_CODE     = ctl.KAI_CODE ");
		sql.add("                       AND hdr.SWK_DEN_NO   = ctl.SWK_DEN_NO ");
		sql.add("                       AND hdr.SWK_DEN_DATE = ctl.SWK_DEN_DATE ");
		sql.add(" LEFT JOIN (SELECT DISTINCT KAI_CODE ");
		sql.add("                           ,APRV_ROLE_GRP_CODE ");
		sql.add("                           ,APRV_ROLE_GRP_NAME ");
		sql.add("            FROM APRV_ROLE_GRP_MST ");
		sql.add("           ) grp ON grp.KAI_CODE           = ctl.KAI_CODE ");
		sql.add("                AND grp.APRV_ROLE_GRP_CODE = hdr.SWK_APRV_GRP_CODE ");
		sql.add(" LEFT JOIN (SELECT DISTINCT r.KAI_CODE ");
		sql.add("                           ,g.APRV_ROLE_GRP_CODE ");
		sql.add("                           ,g.APRV_SKIP_FLG ");
		sql.add("                           ,r.APRV_ROLE_CODE ");
		sql.add("                           ,r.APRV_ROLE_NAME ");
		sql.add("            FROM APRV_ROLE_MST r ");
		sql.add("            INNER JOIN APRV_ROLE_GRP_MST g ON g.KAI_CODE = r.KAI_CODE ");
		sql.add("                                          AND g.APRV_ROLE_CODE = r.APRV_ROLE_CODE ");
		sql.add("           ) rol ON rol.KAI_CODE       = ctl.KAI_CODE ");
		sql.add("                AND rol.APRV_ROLE_GRP_CODE = hdr.SWK_APRV_GRP_CODE ");
		sql.add("                AND rol.APRV_ROLE_CODE     = ctl.APRV_ROLE_CODE ");
		sql.add(" LEFT JOIN (SELECT DISTINCT r.KAI_CODE ");
		sql.add("                           ,g.APRV_ROLE_GRP_CODE ");
		sql.add("                           ,g.APRV_SKIP_FLG ");
		sql.add("                           ,r.APRV_ROLE_CODE ");
		sql.add("                           ,r.APRV_ROLE_NAME ");
		sql.add("            FROM APRV_ROLE_MST r ");
		sql.add("            INNER JOIN APRV_ROLE_GRP_MST g ON g.KAI_CODE = r.KAI_CODE ");
		sql.add("                                          AND g.APRV_ROLE_CODE = r.APRV_ROLE_CODE ");
		sql.add("           ) nex ON nex.KAI_CODE           = ctl.KAI_CODE ");
		sql.add("                AND nex.APRV_ROLE_GRP_CODE = hdr.SWK_APRV_GRP_CODE ");
		sql.add("                AND nex.APRV_ROLE_CODE     = ctl.NEXT_APRV_ROLE_CODE ");
		sql.add(" WHERE 1=1 ");
		sql.add(" AND NOT EXISTS (SELECT 1 ");
		sql.add("                 FROM SWK_SYO_CTL ctl2 ");
		sql.add("                 WHERE ctl2.KAI_CODE     = ctl.KAI_CODE ");
		sql.add("                 AND   ctl2.SWK_DEN_NO   = ctl.SWK_DEN_NO ");
		sql.add("                 AND   ctl2.SWK_DEN_DATE = ctl.SWK_DEN_DATE ");
		sql.add("                 AND   ctl2.SEQ          > ctl.SEQ ");
		sql.add("                ) ");
		sql.add(getWhereSql(param, targetHdr));

		return sql.toString();
	}

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	public List<SWK_HDR> findByCondition(SlipCondition param) {
		return findByCondition(param, SearchTable.Slip);
	}

	/**
	 * �����ɂ��p�^�[������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	public List<SWK_HDR> findPatternByCondition(SlipCondition param) {
		return findByCondition(param, SearchTable.Pattern);
	}

	/**
	 * �����ɂ�闚������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	public List<SWK_HDR> findHistoryByCondition(SlipCondition param) {
		return findByCondition(param, SearchTable.History);
	}

	/**
	 * �����f�t�H���gSQL
	 * 
	 * @param conn
	 * @param param ����
	 * @param searchTable �����Ώ�
	 * @param isDetail true:���׊܂� false:�w�b�_�[�̂�
	 * @return SQL
	 * @throws TException
	 */
	protected String getDefaultSqlIncludeDetails(Connection conn, SlipCondition param, SearchTable searchTable,
		boolean isDetail) throws TException {

		// �����Ώۂ̃e�[�u��
		String swkHeaderTableName = "_SWK_HDR hdr";
		String swkDetailTableName = "SWK_DTL swk";

		if (SearchTable.Pattern == searchTable) {
			swkHeaderTableName = "_SWK_PTN_HDR hdr";
			swkDetailTableName = "SWK_PTN_DTL swk";

		} else if (SearchTable.History == searchTable) {
			swkHeaderTableName = "_SWK_DEL_HDR hdr";
			swkDetailTableName = "SWK_DEL_DTL swk";
		}

		// SQL�쐬�p�v���p�e�B�ۑ�
		param_ = param;
		searchTable_ = searchTable;
		isDetail_ = isDetail;

		// �J������
		String swkAdjKbnColumnName = "hdr.SWK_ADJ_KBN";
		String swkBookNoColumnName = "swk.SWK_BOOK_NO";

		String addonFinallyHeaderColumn = getAddonFinallyHeaderColumn(); // �ŏI���o�p�̃w�b�_�[�ǉ�����
		String addonFinallyHeaderJoinSql = getAddonFinallyHeaderJoinSql(); // �ŏI���o�p�̃w�b�_�[�ǉ�JOIN��

		// �d��W���[�i��(����)����������ꍇ
		String addonDetailColumn = getAddonDetailColumn(); // ���גǉ�����
		String addonFinallyDetailColumn = getAddonFinallyDetailColumn(); // �ŏI���o�p�̖��גǉ�����
		String detailJoin = "";
		VCreator sqlDetail = new VCreator();
		VCreator sqlDetail2 = new VCreator();
		VCreator sqlWhere = new VCreator();
		VCreator aprv = new VCreator();
		VCreator aprvWhere = new VCreator();

		String companyCode = getCompanyCode();
		// ��ЃR�[�h
		String companyCodeKMK_MST = isUseKMK_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeHKM_MST = isUseHKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeUKM_MST = isUseUKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeBMN_MST = isUseBMN_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeTRI_MST = isUseTRI_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeEMP_MST = isUseEMP_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeDEN_SYU_MST = isUseDEN_SYU_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeCUR_MST = isUseCUR_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeAP_HOH_MST = isUseAP_HOH_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeAP_CBK_MST = isUseAP_CBK_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeTRI_SJ_MST = isUseTRI_SJ_MST ? "hdr.KAI_CODE" : "?";

		// ���F���[���}�X�^�A��
		if (param.isSearchWorkFlow()) {
			// ���F���[���}�X�^�A��
			aprv.add(" LEFT JOIN SWK_SYO_CTL syo ON syo.KAI_CODE     = hdr.KAI_CODE ");
			aprv.add("                          AND hdr.SWK_DEN_NO   = syo.SWK_DEN_NO ");
			aprv.add("                          AND hdr.SWK_DEN_DATE = syo.SWK_DEN_DATE ");
			aprv.add("                          AND NOT EXISTS (SELECT 1 ");
			aprv.add("                              FROM SWK_SYO_CTL ");
			aprv.add("                              WHERE KAI_CODE     = syo.KAI_CODE ");
			aprv.add("                              AND   SWK_DEN_DATE = syo.SWK_DEN_DATE ");
			aprv.add("                              AND   SWK_DEN_NO   = syo.SWK_DEN_NO ");
			aprv.add("                              AND   SEQ          > syo.SEQ ");
			aprv.add("                                          ) ");

		}

		// ���F���[���}�X�^����
		if (param.getAprvRoleList() != null && !param.getAprvRoleList().isEmpty()) {
			aprvWhere.add(" AND (1 = 2 ");
			aprvWhere.add(" OR syo.APRV_ROLE_CODE IN ? ", param.getAprvRoleList());
			aprvWhere.add(" OR syo.NEXT_APRV_ROLE_CODE IN ? ", param.getAprvRoleList());
			aprvWhere.add("     ) ");
		}

		if (param.isIncludeOtherSystem()) {
			sqlWhere.add(" INNER JOIN DEN_SYU_MST syu ON " + companyCodeDEN_SYU_MST + " = syu.KAI_CODE ", companyCode);
			sqlWhere.add("                           AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
		}

		if (isDetail) {
			sqlDetail.add(" ," + swkBookNoColumnName);
			sqlDetail.add(" ," + swkBookNoColumnName + " D_SWK_BOOK_NO ");
			sqlDetail.add(" ,swk.SWK_GYO_NO ");
			sqlDetail.add(" ,swk.SWK_NENDO         D_SWK_NENDO ");
			sqlDetail.add(" ,swk.SWK_TUKIDO        D_SWK_TUKIDO ");
			sqlDetail.add(" ,swk.SWK_DC_KBN        D_SWK_DC_KBN ");
			sqlDetail.add(" ,swk.SWK_KMK_CODE      D_SWK_KMK_CODE ");
			sqlDetail.add(" ,swk.SWK_HKM_CODE      D_SWK_HKM_CODE ");
			sqlDetail.add(" ,swk.SWK_UKM_CODE      D_SWK_UKM_CODE ");
			sqlDetail.add(" ,swk.SWK_DEP_CODE      D_SWK_DEP_CODE ");
			sqlDetail.add(" ,swk.SWK_ZEI_KBN       D_SWK_ZEI_KBN ");
			sqlDetail.add(" ,swk.SWK_KIN           D_SWK_KIN ");
			sqlDetail.add(" ,swk.SWK_ZEI_KIN       D_SWK_ZEI_KIN ");
			sqlDetail.add(" ,swk.SWK_ZEI_CODE      D_SWK_ZEI_CODE ");
			sqlDetail.add(" ,swk.SWK_ZEI_RATE      D_SWK_ZEI_RATE ");
			sqlDetail.add(" ,swk.SWK_GYO_TEK_CODE  D_SWK_GYO_TEK_CODE ");
			sqlDetail.add(" ,swk.SWK_GYO_TEK       D_SWK_GYO_TEK ");
			sqlDetail.add(" ,swk.SWK_TRI_CODE      D_SWK_TRI_CODE ");
			sqlDetail.add(" ,swk.SWK_EMP_CODE      D_SWK_EMP_CODE ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_1    D_SWK_KNR_CODE_1 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_2    D_SWK_KNR_CODE_2 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_3    D_SWK_KNR_CODE_3 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_4    D_SWK_KNR_CODE_4 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_5    D_SWK_KNR_CODE_5 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_6    D_SWK_KNR_CODE_6 ");
			sqlDetail.add(" ,swk.SWK_HM_1          D_SWK_HM_1 ");
			sqlDetail.add(" ,swk.SWK_HM_2          D_SWK_HM_2 ");
			sqlDetail.add(" ,swk.SWK_HM_3          D_SWK_HM_3 ");
			sqlDetail.add(" ,swk.SWK_AUTO_KBN      D_SWK_AUTO_KBN ");
			sqlDetail.add(" ,swk.SWK_AT_KMK_CODE   D_SWK_AT_KMK_CODE ");
			sqlDetail.add(" ,swk.SWK_AT_HKM_CODE   D_SWK_AT_HKM_CODE ");
			sqlDetail.add(" ,swk.SWK_AT_UKM_CODE   D_SWK_AT_UKM_CODE ");
			sqlDetail.add(" ,swk.SWK_AT_DEP_CODE   D_SWK_AT_DEP_CODE ");
			sqlDetail.add(" ,swk.INP_DATE          D_INP_DATE ");
			sqlDetail.add(" ,swk.UPD_DATE          D_UPD_DATE ");
			sqlDetail.add(" ,swk.PRG_ID            D_PRG_ID ");
			sqlDetail.add(" ,swk.USR_ID            D_USR_ID ");
			sqlDetail.add(" ,swk.SWK_K_KAI_CODE    D_SWK_K_KAI_CODE ");
			sqlDetail.add(" ,swk.SWK_CUR_CODE      D_SWK_CUR_CODE ");
			sqlDetail.add(" ,swk.SWK_CUR_RATE      D_SWK_CUR_RATE ");
			sqlDetail.add(" ,swk.SWK_IN_KIN        D_SWK_IN_KIN ");
			sqlDetail.add(" ,swk.SWK_TUKE_KBN      D_SWK_TUKE_KBN ");
			sqlDetail.add(" ,swk.SWK_IN_ZEI_KIN    D_SWK_IN_ZEI_KIN ");
			sqlDetail.add(" ,swk.SWK_KESI_KBN      D_SWK_KESI_KBN ");
			sqlDetail.add(" ,swk.SWK_KESI_DATE     D_SWK_KESI_DATE ");
			sqlDetail.add(" ,swk.SWK_KESI_DEN_NO   D_SWK_KESI_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_SOUSAI_DATE   D_SWK_SOUSAI_DATE ");
			sqlDetail.add(" ,swk.SWK_SOUSAI_DEN_NO D_SWK_SOUSAI_DEN_NO ");
			sqlDetail.add(" ,swk.HAS_DATE          D_HAS_DATE ");
			// --PE�ǉ���
			sqlDetail.add(" ,swk.SWK_EST_DEN_NO    D_EST_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_STL_DEN_NO    D_STL_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_MAE_DEN_NO    D_MAE_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_MAE_GYO_NO    D_MAE_GYO_NO ");

			// ���R�敪
			if (isUseInvoice) {
				sqlDetail.add(" ,swk.SWK_FREE_KBN      D_SWK_FREE_KBN ");
				sqlDetail.add(" ,swk.SWK_ORI_GYO_NO    D_SWK_ORI_GYO_NO ");
			}

			// BS��������@�\��ON�̏ꍇ
			if (isUseBS) {
				if (SearchTable.Slip.equals(getSearchTable())) {
					sqlDetail.add(" ,swk.SWK_SOUSAI_GYO_NO D_SWK_SOUSAI_GYO_NO ");
				} else {
					sqlDetail.add(" ,NULL                  D_SWK_SOUSAI_GYO_NO ");
				}
			}

			// AP/AR�����@�\��ON�̏ꍇ
			if (!isNotUseAP || !isNotUseAR) {
				if (SearchTable.Slip.equals(getSearchTable())) {
					sqlDetail.add(" ,swk.SWK_APAR_KESI_KBN ");
					sqlDetail.add(" ,swk.SWK_APAR_DEN_NO ");
					sqlDetail.add(" ,swk.SWK_APAR_GYO_NO ");
				} else {
					sqlDetail.add(" ,0    SWK_APAR_KESI_KBN ");
					sqlDetail.add(" ,NULL SWK_APAR_DEN_NO ");
					sqlDetail.add(" ,NULL SWK_APAR_GYO_NO ");
				}
			}

			// ���גǉ�����
			sqlDetail.add(" " + addonDetailColumn);

			sqlDetail2.add(" ,hdr.D_SWK_BOOK_NO ");
			sqlDetail2.add(" ,hdr.SWK_GYO_NO ");
			sqlDetail2.add(" ,hdr.D_SWK_NENDO ");
			sqlDetail2.add(" ,hdr.D_SWK_TUKIDO ");
			sqlDetail2.add(" ,hdr.D_SWK_DC_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_KMK_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_HKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_UKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_DEP_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_RATE ");
			sqlDetail2.add(" ,hdr.D_SWK_GYO_TEK_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_GYO_TEK ");
			sqlDetail2.add(" ,hdr.D_SWK_TRI_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_EMP_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_1 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_2 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_3 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_4 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_5 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_6 ");
			sqlDetail2.add(" ,hdr.D_SWK_HM_1 ");
			sqlDetail2.add(" ,hdr.D_SWK_HM_2 ");
			sqlDetail2.add(" ,hdr.D_SWK_HM_3 ");
			sqlDetail2.add(" ,hdr.D_SWK_AUTO_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_KMK_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_HKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_UKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_DEP_CODE ");
			sqlDetail2.add(" ,hdr.D_INP_DATE ");
			sqlDetail2.add(" ,hdr.D_UPD_DATE ");
			sqlDetail2.add(" ,hdr.D_PRG_ID ");
			sqlDetail2.add(" ,hdr.D_USR_ID ");
			sqlDetail2.add(" ,hdr.D_SWK_K_KAI_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_CUR_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_CUR_RATE ");
			sqlDetail2.add(" ,hdr.D_SWK_IN_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_TUKE_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_IN_ZEI_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_KESI_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_KESI_DATE ");
			sqlDetail2.add(" ,hdr.D_SWK_KESI_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_SWK_SOUSAI_DATE ");
			sqlDetail2.add(" ,hdr.D_SWK_SOUSAI_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_HAS_DATE ");
			// --PE�ǉ���
			sqlDetail2.add(" ,hdr.D_EST_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_STL_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_MAE_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_MAE_GYO_NO ");
			sqlDetail2.add(" ,demp.EMP_NAME      D_EMP_NAME ");
			sqlDetail2.add(" ,demp.EMP_NAME_S    D_EMP_NAME_S ");
			sqlDetail2.add(" ,ddep.DEP_NAME      D_DEP_NAME ");
			sqlDetail2.add(" ,ddep.DEP_NAME_S    D_DEP_NAME_S ");
			sqlDetail2.add(" ,dkmk.KMK_NAME      D_KMK_NAME ");
			sqlDetail2.add(" ,dkmk.KMK_NAME_S    D_KMK_NAME_S ");
			sqlDetail2.add(" ,dkmk.HAS_FLG       D_KMK_HAS_FLG ");
			sqlDetail2.add(" ,dhkm.HKM_NAME      D_HKM_NAME ");
			sqlDetail2.add(" ,dhkm.HKM_NAME_S    D_HKM_NAME_S ");
			sqlDetail2.add(" ,dhkm.HAS_FLG       D_HKM_HAS_FLG ");
			sqlDetail2.add(" ,dukm.UKM_NAME      D_UKM_NAME ");
			sqlDetail2.add(" ,dukm.UKM_NAME_S    D_UKM_NAME_S ");
			sqlDetail2.add(" ,dukm.HAS_FLG       D_UKM_HAS_FLG ");
			sqlDetail2.add(" ,dcur.DEC_KETA      D_DEC_KETA ");
			sqlDetail2.add(" ,dzei.ZEI_NAME      D_ZEI_NAME ");
			sqlDetail2.add(" ,dzei.ZEI_NAME_S    D_ZEI_NAME_S ");
			sqlDetail2.add(" ,dtri.TRI_NAME      D_TRI_NAME ");
			sqlDetail2.add(" ,dtri.TRI_NAME_S    D_TRI_NAME_S ");
			sqlDetail2.add(" ,dknr1.KNR_NAME_1   D_KNR_NAME_1 ");
			sqlDetail2.add(" ,dknr1.KNR_NAME_S_1 D_KNR_NAME_S_1 ");
			sqlDetail2.add(" ,dknr2.KNR_NAME_2   D_KNR_NAME_2 ");
			sqlDetail2.add(" ,dknr2.KNR_NAME_S_2 D_KNR_NAME_S_2 ");
			sqlDetail2.add(" ,dknr3.KNR_NAME_3   D_KNR_NAME_3 ");
			sqlDetail2.add(" ,dknr3.KNR_NAME_S_3 D_KNR_NAME_S_3 ");
			sqlDetail2.add(" ,dknr4.KNR_NAME_4   D_KNR_NAME_4 ");
			sqlDetail2.add(" ,dknr4.KNR_NAME_S_4 D_KNR_NAME_S_4 ");
			sqlDetail2.add(" ,dknr5.KNR_NAME_5   D_KNR_NAME_5 ");
			sqlDetail2.add(" ,dknr5.KNR_NAME_S_5 D_KNR_NAME_S_5 ");
			sqlDetail2.add(" ,dknr6.KNR_NAME_6   D_KNR_NAME_6 ");
			sqlDetail2.add(" ,dknr6.KNR_NAME_S_6 D_KNR_NAME_S_6 ");

			// ���R�敪
			if (isUseInvoice) {
				sqlDetail2.add(" ,hdr.D_SWK_FREE_KBN ");
				sqlDetail2.add(" ,hdr.D_SWK_ORI_GYO_NO ");
			}

			// BS��������@�\��ON�̏ꍇ
			if (isUseBS) {
				sqlDetail2.add(" ,hdr.D_SWK_SOUSAI_GYO_NO ");
			}

			// AP/AR�����@�\��ON�̏ꍇ
			if (!isNotUseAP || !isNotUseAR) {
				sqlDetail2.add(" ,hdr.SWK_APAR_KESI_KBN ");
				sqlDetail2.add(" ,hdr.SWK_APAR_DEN_NO ");
				sqlDetail2.add(" ,hdr.SWK_APAR_GYO_NO ");
			}

			// �ŏI���o�p�̖��גǉ�����
			sqlDetail2.add(" " + addonFinallyDetailColumn);

			sqlWhere.add(" INNER JOIN " + swkDetailTableName + " ON	hdr.KAI_CODE   = swk.KAI_CODE ");
			sqlWhere.add("                                      AND hdr.SWK_DEN_NO = swk.SWK_DEN_NO ");

			// �p�^�[���f�[�^���o���A�`�[���tNULL���l����
			if (SearchTable.Pattern == searchTable) {

				sqlWhere.add(" AND (hdr.SWK_DEN_DATE = swk.SWK_DEN_DATE ");
				sqlWhere.add("      OR (    hdr.SWK_DEN_DATE IS NULL ");
				sqlWhere.add("          AND swk.SWK_DEN_DATE IS NULL ");
				sqlWhere.add("         ) ");
				sqlWhere.add("     ) ");

			} else {
				sqlWhere.add(" AND hdr.SWK_DEN_DATE = swk.SWK_DEN_DATE ");
			}

			if (SearchTable.History == searchTable) {
				sqlWhere.add(" AND hdr.SWK_UPD_CNT = swk.SWK_UPD_CNT ");
			}

			// �����敪
			if (param.getAdjDivisionList() != null && param.getAdjDivisionList().length != 0) {
				sqlWhere.add(" AND swk.SWK_ADJ_KBN IN " + SQLUtil.getIntInStatement(param.getAdjDivisionList()));

			}

			// �����d��敪
			if (param.getAutoDivision() != -1) {
				sqlWhere.add(" AND swk.SWK_AUTO_KBN = ? ", param.getAutoDivision());

			}

			// ����
			if (param.getBookNo() != 0) {
				sqlWhere.add(" AND swk.SWK_BOOK_NO = ? ", param.getBookNo());
			}

			detailJoin = getDetailJoinSql();
		}

		// GL/AP/AR����
		// �w�b�_�[�����������K�v�̏ꍇ�̓e�[�u������
		if (!Util.isNullOrEmpty(param.getDepartmentNamesLike())) {
			sqlWhere.add(" LEFT OUTER JOIN BMN_MST dep ");
			sqlWhere.add(" ON  hdr.KAI_CODE     = dep.KAI_CODE ");
			sqlWhere.add(" AND hdr.SWK_DEP_CODE = dep.DEP_CODE ");
		}
		// �w�b�_�[�����������K�v�̏ꍇ�̓e�[�u������
		if (!Util.isNullOrEmpty(param.getEmployeeNamesLike())) {
			sqlWhere.add(" LEFT OUTER JOIN EMP_MST emp ");
			sqlWhere.add(" ON  hdr.KAI_CODE     = emp.KAI_CODE ");
			sqlWhere.add(" AND hdr.SWK_EMP_CODE = emp.EMP_CODE ");
		}

		// �ꎞ�e�[�u����
		TempTableNameGetterSetting bgs = get(TempTableNameGetterSetting.class);
		String tempTableName = bgs.getTemporaryWorkTableName(param, "T_SLIP_DAO");
		{
			// �ꎞ�e�[�u���N���A
			DBUtil.execute(conn, " TRUNCATE TABLE " + tempTableName);
			if (DBUtil.isUseMySQL) {
				DBUtil.execute(conn, " DELETE FROM " + tempTableName);
			}

			int maxHeaderCount = param.getMaxHeaderCount(); // �ő�w�b�_�[����
			int accuHeaderCount = 0;

			VCreator sql = new VCreator();

			// GL
			if (Util.isNullOrEmpty(param.getPaymentDayFrom()) && Util.isNullOrEmpty(param.getPaymentDayTo())
				&& Util.isNullOrEmpty(param.getReceiveDayFrom()) && Util.isNullOrEmpty(param.getReceiveDayTo())
				&& Util.isNullOrEmpty(param.getCashDayFrom()) && Util.isNullOrEmpty(param.getCashDayTo())) {

				// SELECT�����擾
				int glCount = getSwkCount(conn, param, isDetail, sqlWhere, swkHeaderTableName, HDR_TABLE.GL);
				accuHeaderCount += glCount;

				if (maxHeaderCount > 0 && accuHeaderCount > maxHeaderCount) {
					// I00755">���ʌ���[{0}]���ő匏��[{1}]�𒴂������߂Ɍ����ł��܂���B\n�i�荞�ݏ�����ύX���čēx�������Ă��������B
					String msg = "I00755";
					throw new TWarningException(getMessage(msg, accuHeaderCount, maxHeaderCount));
				}

				if (glCount > 0) {
					// INSERT INTO
					sql = new VCreator();
					sql.add(getTemporaryHeaderColumns(isDetail, tempTableName));
					sql.add("        SELECT hdr.KAI_CODE ");
					sql.add("              ,hdr.SWK_DEN_DATE ");
					sql.add("              ,hdr.SWK_DEN_NO ");
					sql.add("              ,hdr.SWK_SEI_NO ");
					sql.add("              ,hdr.SWK_KMK_CODE ");
					sql.add("              ,hdr.SWK_HKM_CODE ");
					sql.add("              ,hdr.SWK_UKM_CODE ");
					sql.add("              ,hdr.SWK_DEP_CODE ");
					sql.add("              ,hdr.SWK_TRI_CODE ");
					sql.add("              ,hdr.SWK_EMP_CODE ");
					sql.add("              ,hdr.SWK_KIN ");
					sql.add("              ,hdr.SWK_DATA_KBN ");
					sql.add("              ,hdr.SWK_UKE_DEP_CODE ");
					sql.add("              ,hdr.SWK_TEK_CODE ");
					sql.add("              ,hdr.SWK_TEK ");
					sql.add("              ,hdr.SWK_BEFORE_DEN_NO ");
					sql.add("              ,hdr.SWK_UPD_KBN ");
					sql.add("              ,hdr.SWK_SYO_EMP_CODE ");
					sql.add("              ,hdr.SWK_SYO_DATE ");
					sql.add("              ,hdr.SWK_IRAI_EMP_CODE ");
					sql.add("              ,hdr.SWK_IRAI_DEP_CODE ");
					sql.add("              ,hdr.SWK_IRAI_DATE ");
					sql.add("              ,hdr.SWK_SHR_KBN ");
					sql.add("              ,hdr.SWK_KSN_KBN ");
					sql.add("              ,hdr.INP_DATE ");
					sql.add("              ,hdr.UPD_DATE ");
					sql.add("              ,hdr.PRG_ID ");
					sql.add("              ,hdr.USR_ID ");
					sql.add("              ,hdr.SWK_CUR_CODE ");
					sql.add("              ,hdr.SWK_CUR_RATE ");
					sql.add("              ,hdr.SWK_IN_KIN ");
					sql.add("              ,hdr.SWK_SYS_KBN ");
					sql.add("              ,hdr.SWK_DEN_SYU ");
					sql.add("              ,hdr.SWK_TUKE_KBN ");
					sql.add("              ,hdr.SWK_UPD_CNT ");
					sql.add("              ,NULL SWK_SIHA_KBN ");
					sql.add("              ,NULL SWK_SIHA_DATE ");
					sql.add("              ,NULL SWK_HOH_CODE ");
					sql.add("              ,NULL SWK_HORYU_KBN ");
					sql.add("              ,NULL SWK_KARI_KIN ");
					sql.add("              ,NULL SWK_KEIHI_KIN ");
					sql.add("              ,NULL SWK_SIHA_KIN ");
					sql.add("              ,NULL SWK_KARI_DR_DEN_NO ");
					sql.add("              ,NULL SWK_KARI_CR_DEN_NO ");
					sql.add("              ,NULL SWK_KESAI_KBN ");
					sql.add("              ,NULL SWK_KARI_DEP_CODE ");
					sql.add("              ,NULL SWK_INP_DATE ");
					sql.add("              ,NULL SWK_TJK_CODE ");
					sql.add("              ,NULL SWK_IN_KARI_KIN ");
					sql.add("              ,NULL SWK_IN_KEIHI_KIN ");
					sql.add("              ,NULL SWK_IN_SIHA_KIN ");
					sql.add("              ,NULL SWK_INV_CUR_CODE ");
					sql.add("              ,NULL SWK_INV_CUR_RATE ");
					sql.add("              ,NULL SWK_INV_KIN ");
					sql.add("              ,NULL SWK_CBK_CODE ");
					sql.add("              ,NULL SWK_SSY_DATE ");
					sql.add("              ,NULL SWK_UTK_NO ");
					sql.add("              ,NULL SWK_KARI_CUR_CODE ");
					sql.add("              ,NULL SWK_KARI_CUR_RATE ");
					sql.add("              ,NULL SWK_SEI_KBN ");
					sql.add("              ,NULL SWK_AR_DATE ");
					// --PE�ǉ���
					sql.add("              ,hdr.SWK_EST_CANCEL_DEN_NO ");
					sql.add("              ,hdr.SWK_BASE_EST_DEN_NO ");
					sql.add("              ,hdr.SWK_APRV_GRP_CODE ");
					sql.add("              ," + swkAdjKbnColumnName);
					sql.add(sqlDetail);
					sql.add(getAddonHeaderColumn(HDR_TABLE.GL)); // �w�b�_�[�ǉ�����
					sql.add("              ,? AS ROW_TYPE ", HDR_TABLE.GL);
					sql.add("        FROM GL" + swkHeaderTableName);
					sql.add(aprv);
					sql.add(sqlWhere);

					// �w�b�_�[����������
					// VCreator where = getWhereSql(param, true, HDR_TABLE.GL);
					VCreator where = getWhereSql(param, HDR_TABLE.GL);
					if (!isDetail) {
						sql.add(" WHERE 1 = 1 ");
						sql.add(aprvWhere);
					}
					sql.add(where);

					if (DBUtil.isUseMySQL) {
						sql.add(" LIMIT ? ", glCount);
					} else {
						sql.add(" AND ROWNUM <= ? ", glCount);
					}

					DBUtil.execute(conn, sql);
				}
			}

			// AP/AR�̂�
			// �w�b�_�[�����������K�v�̏ꍇ�̓e�[�u������
			if (!Util.isNullOrEmpty(param.getCustmorNamesLike())) {
				sqlWhere.add(" LEFT OUTER JOIN TRI_MST tri ");
				sqlWhere.add(" ON  hdr.KAI_CODE     = tri.KAI_CODE ");
				sqlWhere.add(" AND hdr.SWK_TRI_CODE = tri.TRI_CODE ");
			}

			// AP
			sql = new VCreator();

			// SELECT�����擾
			int apCount = getSwkCount(conn, param, isDetail, sqlWhere, swkHeaderTableName, HDR_TABLE.AP);
			accuHeaderCount += apCount;

			if (maxHeaderCount > 0 && accuHeaderCount > maxHeaderCount) {
				// I00755">���ʌ���[{0}]���ő匏��[{1}]�𒴂������߂Ɍ����ł��܂���B\n�i�荞�ݏ�����ύX���čēx�������Ă��������B
				String msg = "I00755";
				throw new TWarningException(getMessage(msg, accuHeaderCount, maxHeaderCount));
			}

			if (apCount > 0) {
				// INSERT INTO
				sql.add(getTemporaryHeaderColumns(isDetail, tempTableName));
				sql.add("        SELECT hdr.KAI_CODE ");
				sql.add("              ,hdr.SWK_DEN_DATE ");
				sql.add("              ,hdr.SWK_DEN_NO ");
				sql.add("              ,hdr.SWK_SEI_NO ");
				sql.add("              ,hdr.SWK_KMK_CODE ");
				sql.add("              ,hdr.SWK_HKM_CODE ");
				sql.add("              ,hdr.SWK_UKM_CODE ");
				sql.add("              ,hdr.SWK_DEP_CODE ");
				sql.add("              ,hdr.SWK_TRI_CODE ");
				sql.add("              ,hdr.SWK_EMP_CODE ");
				sql.add("              ,NULL SWK_KIN ");
				sql.add("              ,hdr.SWK_DATA_KBN ");
				sql.add("              ,hdr.SWK_UKE_DEP_CODE ");
				sql.add("              ,hdr.SWK_TEK_CODE ");
				sql.add("              ,hdr.SWK_TEK ");
				sql.add("              ,hdr.SWK_BEFORE_DEN_NO ");
				sql.add("              ,hdr.SWK_UPD_KBN ");
				sql.add("              ,hdr.SWK_SYO_EMP_CODE ");
				sql.add("              ,hdr.SWK_SYO_DATE ");
				sql.add("              ,hdr.SWK_IRAI_EMP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DEP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DATE ");
				sql.add("              ,hdr.SWK_SHR_KBN ");
				sql.add("              ,hdr.SWK_KSN_KBN ");
				sql.add("              ,hdr.SWK_INP_DATE INP_DATE ");
				sql.add("              ,hdr.UPD_DATE ");
				sql.add("              ,hdr.PRG_ID ");
				sql.add("              ,hdr.USR_ID ");
				sql.add("              ,hdr.SWK_CUR_CODE ");
				sql.add("              ,hdr.SWK_CUR_RATE ");
				sql.add("              ,NULL SWK_IN_KIN ");
				sql.add("              ,hdr.SWK_SYS_KBN ");
				sql.add("              ,hdr.SWK_DEN_SYU ");
				sql.add("              ,hdr.SWK_TUKE_KBN ");
				sql.add("              ,hdr.SWK_UPD_CNT ");
				sql.add("              ,hdr.SWK_SIHA_KBN ");
				sql.add("              ,hdr.SWK_SIHA_DATE ");
				sql.add("              ,hdr.SWK_HOH_CODE ");
				sql.add("              ,hdr.SWK_HORYU_KBN ");
				sql.add("              ,hdr.SWK_KARI_KIN ");
				sql.add("              ,hdr.SWK_KEIHI_KIN ");
				sql.add("              ,hdr.SWK_SIHA_KIN ");
				sql.add("              ,hdr.SWK_KARI_DR_DEN_NO ");
				sql.add("              ,hdr.SWK_KARI_CR_DEN_NO ");
				sql.add("              ,hdr.SWK_KESAI_KBN ");
				sql.add("              ,hdr.SWK_KARI_DEP_CODE ");
				sql.add("              ,hdr.SWK_INP_DATE ");
				sql.add("              ,hdr.SWK_TJK_CODE ");
				sql.add("              ,hdr.SWK_IN_KARI_KIN ");
				sql.add("              ,hdr.SWK_IN_KEIHI_KIN ");
				sql.add("              ,hdr.SWK_IN_SIHA_KIN ");
				sql.add("              ,hdr.SWK_INV_CUR_CODE ");
				sql.add("              ,hdr.SWK_INV_CUR_RATE ");
				sql.add("              ,hdr.SWK_INV_KIN ");
				sql.add("              ,hdr.SWK_CBK_CODE ");
				sql.add("              ,hdr.SWK_SSY_DATE ");
				sql.add("              ,hdr.SWK_UTK_NO ");
				sql.add("              ,hdr.SWK_KARI_CUR_CODE ");
				sql.add("              ,hdr.SWK_KARI_CUR_RATE ");
				sql.add("              ,NULL SWK_SEI_KBN ");
				sql.add("              ,NULL SWK_AR_DATE ");
				// --PE�ǉ���
				sql.add("              ,hdr.SWK_EST_CANCEL_DEN_NO ");
				sql.add("              ,hdr.SWK_BASE_EST_DEN_NO ");
				sql.add("              ,hdr.SWK_APRV_GRP_CODE ");
				sql.add("              ," + swkAdjKbnColumnName);
				sql.add(sqlDetail);
				sql.add(getAddonHeaderColumn(HDR_TABLE.AP)); // �w�b�_�[�ǉ�����
				sql.add("              ,? AS ROW_TYPE ", HDR_TABLE.AP);
				sql.add("        FROM AP" + swkHeaderTableName);
				sql.add(aprv);
				sql.add(sqlWhere);

				// �w�b�_�[����������
				// VCreator where = getWhereSql(param, true, HDR_TABLE.AP);
				VCreator where = getWhereSql(param, HDR_TABLE.AP);
				if (!isDetail) {
					sql.add(" WHERE 1 = 1 ");
					sql.add(aprvWhere);
				}
				sql.add(where);

				if (DBUtil.isUseMySQL) {
					sql.add(" LIMIT ? ", apCount);
				} else {
					sql.add(" AND ROWNUM <= ? ", apCount);
				}

				DBUtil.execute(conn, sql);
			}

			// AR
			sql = new VCreator();

			// SELECT�����擾
			int arCount = getSwkCount(conn, param, isDetail, sqlWhere, swkHeaderTableName, HDR_TABLE.AR);
			accuHeaderCount += arCount;

			if (maxHeaderCount > 0 && accuHeaderCount > maxHeaderCount) {
				// I00755">���ʌ���[{0}]���ő匏��[{1}]�𒴂������߂Ɍ����ł��܂���B\n�i�荞�ݏ�����ύX���čēx�������Ă��������B
				String msg = "I00755";
				throw new TWarningException(getMessage(msg, accuHeaderCount, maxHeaderCount));
			}

			if (arCount > 0) {
				// INSERT INTO
				sql.add(getTemporaryHeaderColumns(isDetail, tempTableName));
				sql.add("        SELECT hdr.KAI_CODE ");
				sql.add("              ,hdr.SWK_DEN_DATE ");
				sql.add("              ,hdr.SWK_DEN_NO ");
				sql.add("              ,hdr.SWK_SEI_NO ");
				sql.add("              ,hdr.SWK_KMK_CODE ");
				sql.add("              ,hdr.SWK_HKM_CODE ");
				sql.add("              ,hdr.SWK_UKM_CODE ");
				sql.add("              ,hdr.SWK_DEP_CODE ");
				sql.add("              ,hdr.SWK_TRI_CODE ");
				sql.add("              ,hdr.SWK_EMP_CODE ");
				sql.add("              ,hdr.SWK_KIN ");
				sql.add("              ,hdr.SWK_DATA_KBN ");
				sql.add("              ,hdr.SWK_UKE_DEP_CODE ");
				sql.add("              ,hdr.SWK_TEK_CODE ");
				sql.add("              ,hdr.SWK_TEK ");
				sql.add("              ,hdr.SWK_BEFORE_DEN_NO ");
				sql.add("              ,hdr.SWK_UPD_KBN ");
				sql.add("              ,hdr.SWK_SYO_EMP_CODE ");
				sql.add("              ,hdr.SWK_SYO_DATE ");
				sql.add("              ,hdr.SWK_IRAI_EMP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DEP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DATE ");
				sql.add("              ,hdr.SWK_SHR_KBN ");
				sql.add("              ,hdr.SWK_KSN_KBN ");
				sql.add("              ,hdr.INP_DATE ");
				sql.add("              ,hdr.UPD_DATE ");
				sql.add("              ,hdr.PRG_ID ");
				sql.add("              ,hdr.USR_ID ");
				sql.add("              ,hdr.SWK_CUR_CODE ");
				sql.add("              ,hdr.SWK_CUR_RATE ");
				sql.add("              ,hdr.SWK_IN_KIN ");
				sql.add("              ,hdr.SWK_SYS_KBN ");
				sql.add("              ,hdr.SWK_DEN_SYU ");
				sql.add("              ,hdr.SWK_TUKE_KBN ");
				sql.add("              ,hdr.SWK_UPD_CNT ");
				sql.add("              ,NULL SWK_SIHA_KBN ");
				sql.add("              ,NULL SWK_SIHA_DATE ");
				sql.add("              ,NULL SWK_HOH_CODE ");
				sql.add("              ,NULL SWK_HORYU_KBN ");
				sql.add("              ,NULL SWK_KARI_KIN ");
				sql.add("              ,NULL SWK_KEIHI_KIN ");
				sql.add("              ,NULL SWK_SIHA_KIN ");
				sql.add("              ,NULL SWK_KARI_DR_DEN_NO ");
				sql.add("              ,NULL SWK_KARI_CR_DEN_NO ");
				sql.add("              ,NULL SWK_KESAI_KBN ");
				sql.add("              ,NULL SWK_KARI_DEP_CODE ");
				sql.add("              ,NULL SWK_INP_DATE ");
				sql.add("              ,NULL SWK_TJK_CODE ");
				sql.add("              ,NULL SWK_IN_KARI_KIN ");
				sql.add("              ,NULL SWK_IN_KEIHI_KIN ");
				sql.add("              ,NULL SWK_IN_SIHA_KIN ");
				sql.add("              ,NULL SWK_INV_CUR_CODE ");
				sql.add("              ,NULL SWK_INV_CUR_RATE ");
				sql.add("              ,NULL SWK_INV_KIN ");
				sql.add("              ,hdr.SWK_CBK_CODE ");
				sql.add("              ,NULL SWK_SSY_DATE ");
				sql.add("              ,NULL SWK_UTK_NO ");
				sql.add("              ,NULL SWK_KARI_CUR_CODE ");
				sql.add("              ,NULL SWK_KARI_CUR_RATE ");
				sql.add("              ,hdr.SWK_SEI_KBN ");
				sql.add("              ,hdr.SWK_AR_DATE ");
				// --PE�ǉ���
				sql.add("              ,hdr.SWK_EST_CANCEL_DEN_NO ");
				sql.add("              ,hdr.SWK_BASE_EST_DEN_NO ");
				sql.add("              ,hdr.SWK_APRV_GRP_CODE ");

				sql.add("              ," + swkAdjKbnColumnName);
				sql.add(sqlDetail);
				sql.add(getAddonHeaderColumn(HDR_TABLE.AR)); // �w�b�_�[�ǉ�����
				sql.add("              ,? AS ROW_TYPE ", HDR_TABLE.AR);
				sql.add("        FROM AR" + swkHeaderTableName);
				sql.add(aprv);
				sql.add(sqlWhere);

				// �w�b�_�[����������
				// VCreator where = getWhereSql(param, true, HDR_TABLE.AR);
				VCreator where = getWhereSql(param, HDR_TABLE.AR);
				if (!isDetail) {
					sql.add(" WHERE 1 = 1 ");
					sql.add(aprvWhere);
				}
				sql.add(where);

				if (DBUtil.isUseMySQL) {
					sql.add(" LIMIT ? ", arCount);
				} else {
					sql.add(" AND ROWNUM <= ? ", arCount);
				}
				DBUtil.execute(conn, sql);
			}
		}

		// ���ԃe�[�u���������`�F�b�N
		int daoCount = DBUtil.selectOneInt(conn, " SELECT COUNT(1) FROM " + tempTableName);
		if (daoCount == 0) {
			throw new SlipNoDataException();
		}

		// �{SQL
		VCreator sql = new VCreator();

		sql.add(getSelectKeyWord());

		if (isUseAttachment) {
			// �Y�t���̗L�����擾���邽�߂�SQL
			sql.add(" ( SELECT COUNT(*) ");
			sql.add("   FROM SWK_ATTACH atch ");
			sql.add("   WHERE atch.KAI_CODE   = hdr.KAI_CODE ");
			sql.add("     AND atch.SWK_DEN_NO = hdr.SWK_DEN_NO ");
			sql.add(" ) AS ATTACH_COUNT ");

		} else {
			sql.add(" 0    ATTACH_COUNT ");
		}
		if (isUseTag) {
			// �tⳏ��̗L�����擾���邽�߂�SQL
			sql.add(" , ( SELECT COUNT(*) ");
			sql.add("   FROM SWK_TAG tag ");
			sql.add("   WHERE tag.KAI_CODE   = hdr.KAI_CODE ");
			sql.add("     AND tag.SWK_DEN_NO = hdr.SWK_DEN_NO ");
			sql.add(" ) AS TAG_COUNT ");

		} else {
			sql.add(" , 0    TAG_COUNT ");
		}
		sql.add(" ,hdr.KAI_CODE ");
		sql.add(" ,env.KAI_NAME ");
		sql.add(" ,env.KAI_NAME_S ");
		sql.add(" ,hdr.SWK_DEN_DATE ");
		sql.add(" ,hdr.SWK_DEN_NO ");
		sql.add(" ,hdr.SWK_SEI_NO ");
		sql.add(" ,hdr.SWK_KMK_CODE ");
		sql.add(" ,kmk.KMK_NAME   SWK_KMK_NAME ");
		sql.add(" ,kmk.KMK_NAME_S SWK_KMK_NAME_S ");
		sql.add(" ,hdr.SWK_HKM_CODE ");
		sql.add(" ,hkm.HKM_NAME   SWK_HKM_NAME ");
		sql.add(" ,hkm.HKM_NAME_S SWK_HKM_NAME_S ");
		sql.add(" ,hdr.SWK_UKM_CODE ");
		sql.add(" ,ukm.UKM_NAME   SWK_UKM_NAME ");
		sql.add(" ,ukm.UKM_NAME_S SWK_UKM_NAME_S ");
		sql.add(" ,hdr.SWK_DEP_CODE ");
		sql.add(" ,dep.DEP_NAME   SWK_DEP_NAME ");
		sql.add(" ,dep.DEP_NAME_S SWK_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_TRI_CODE ");
		sql.add(" ,tri.TRI_NAME   SWK_TRI_NAME ");
		sql.add(" ,tri.TRI_NAME_S SWK_TRI_NAME_S ");
		sql.add(" ,hdr.SWK_EMP_CODE ");
		sql.add(" ,emp.EMP_NAME   SWK_EMP_NAME ");
		sql.add(" ,emp.EMP_NAME_S SWK_EMP_NAME_S ");
		sql.add(" ,hdr.SWK_KIN ");
		sql.add(" ,hdr.SWK_DATA_KBN ");
		sql.add(" ,hdr.SWK_UKE_DEP_CODE ");
		sql.add(" ,depuke.DEP_NAME   SWK_UKE_DEP_NAME ");
		sql.add(" ,depuke.DEP_NAME_S SWK_UKE_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_TEK_CODE ");
		sql.add(" ,hdr.SWK_TEK ");
		sql.add(" ,hdr.SWK_BEFORE_DEN_NO ");
		sql.add(" ,hdr.SWK_UPD_KBN ");
		sql.add(" ,hdr.SWK_SYO_EMP_CODE ");
		sql.add(" ,empsyo.EMP_NAME   SWK_SYO_EMP_NAME ");
		sql.add(" ,empsyo.EMP_NAME_S SWK_SYO_EMP_NAME_S ");
		sql.add(" ,hdr.SWK_SYO_DATE ");
		sql.add(" ,hdr.SWK_IRAI_EMP_CODE ");
		sql.add(" ,empirai.EMP_NAME   SWK_IRAI_EMP_NAME ");
		sql.add(" ,empirai.EMP_NAME_S SWK_IRAI_EMP_NAME_S ");
		sql.add(" ,hdr.SWK_IRAI_DEP_CODE ");
		sql.add(" ,depirai.DEP_NAME   SWK_IRAI_DEP_NAME ");
		sql.add(" ,depirai.DEP_NAME_S SWK_IRAI_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_IRAI_DATE ");
		sql.add(" ,hdr.SWK_SHR_KBN ");
		sql.add(" ,hdr.SWK_KSN_KBN ");
		sql.add(" ,hdr.INP_DATE ");
		sql.add(" ,hdr.UPD_DATE ");
		sql.add(" ,hdr.PRG_ID ");
		sql.add(" ,hdr.USR_ID ");
		sql.add(" ,hdr.SWK_CUR_CODE ");
		sql.add(" ,cur.DEC_KETA SWK_CUR_DEC_KETA ");
		sql.add(" ,hdr.SWK_CUR_RATE ");
		sql.add(" ,hdr.SWK_IN_KIN ");
		sql.add(" ,hdr.SWK_SYS_KBN ");
		sql.add(" ,hdr.SWK_DEN_SYU ");
		sql.add(" ,syu.DEN_SYU_NAME   SWK_DEN_SYU_NAME ");
		sql.add(" ,syu.DEN_SYU_NAME_S SWK_DEN_SYU_NAME_S ");
		sql.add(" ,syu.DEN_SYU_NAME_K SWK_DEN_SYU_NAME_K ");
		sql.add(" ,hdr.SWK_TUKE_KBN ");
		sql.add(" ,hdr.SWK_UPD_CNT ");
		sql.add(" ,hdr.SWK_SIHA_KBN ");
		sql.add(" ,hdr.SWK_SIHA_DATE ");
		sql.add(" ,hdr.SWK_HOH_CODE ");
		sql.add(" ,hoh.HOH_HOH_NAME SWK_HOH_NAME ");
		sql.add(" ,hoh.HOH_NAI_CODE SWK_HOH_NAI_CODE ");
		sql.add(" ,hdr.SWK_HORYU_KBN ");
		sql.add(" ,hdr.SWK_KARI_KIN ");
		sql.add(" ,hdr.SWK_KEIHI_KIN ");
		sql.add(" ,hdr.SWK_SIHA_KIN ");
		sql.add(" ,hdr.SWK_KARI_DR_DEN_NO ");
		sql.add(" ,hdr.SWK_KARI_CR_DEN_NO ");
		sql.add(" ,hdr.SWK_KESAI_KBN ");
		sql.add(" ,hdr.SWK_KARI_DEP_CODE ");
		sql.add(" ,depkari.DEP_NAME   SWK_KARI_DEP_NAME ");
		sql.add(" ,depkari.DEP_NAME_S SWK_KARI_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_INP_DATE ");
		sql.add(" ,hdr.SWK_TJK_CODE ");
		sql.add(" ,trisj.YKN_KBN         TJK_YKN_KBN ");
		sql.add(" ,trisj.YKN_NO          TJK_YKN_NO ");
		sql.add(" ,trisj.YKN_KANA        TJK_YKN_KANA ");
		sql.add(" ,trisj.GS_BNK_NAME     SWK_TJK_GS_BNK_NAME ");
		sql.add(" ,trisj.GS_STN_NAME     SWK_TJK_GS_STN_NAME ");
		sql.add(" ,tjkbnk.BNK_NAME_S     SWK_TJK_BNK_NAME_S ");
		sql.add(" ,tjkbnk.BNK_STN_NAME_S SWK_TJK_BNK_STN_NAME_S ");
		sql.add(" ,hdr.SWK_IN_KARI_KIN ");
		sql.add(" ,hdr.SWK_IN_KEIHI_KIN ");
		sql.add(" ,hdr.SWK_IN_SIHA_KIN ");
		sql.add(" ,hdr.SWK_INV_CUR_CODE ");
		sql.add(" ,hdr.SWK_INV_CUR_RATE ");
		sql.add(" ,hdr.SWK_INV_KIN ");
		sql.add(" ,hdr.SWK_CBK_CODE ");
		sql.add(" ,cbk.CBK_NAME       SWK_CBK_NAME ");
		sql.add(" ,cbk.CBK_STN_CODE   SWK_CBK_STN_CODE ");
		sql.add(" ,cbk.CBK_YKN_KBN    SWK_CBK_YKN_KBN ");
		sql.add(" ,cbk.CBK_YKN_NO     SWK_CBK_YKN_NO ");
		sql.add(" ,bnk.BNK_NAME_S     SWK_BNK_NAME_S ");
		sql.add(" ,bnk.BNK_STN_NAME_S SWK_BNK_STN_NAME_S ");
		sql.add(" ,hdr.SWK_SSY_DATE ");
		sql.add(" ,hdr.SWK_UTK_NO ");
		sql.add(" ,hdr.SWK_KARI_CUR_CODE ");
		sql.add(" ,hdr.SWK_KARI_CUR_RATE ");
		sql.add(" ,hdr.SWK_SEI_KBN ");
		sql.add(" ,hdr.SWK_AR_DATE ");
		sql.add(" ,hdr.SWK_ADJ_KBN ");
		sql.add(" ,keyCur.CUR_CODE  KEY_CUR_CODE ");
		sql.add(" ,keyCur.DEC_KETA  KEY_CUR_DEC_KETA ");
		sql.add(" ,funcCur.CUR_CODE FUNC_CUR_CODE ");
		sql.add(" ,funcCur.DEC_KETA FUNC_CUR_DEC_KETA ");
		// --PE�ǉ���
		sql.add(" ,hdr.SWK_EST_CANCEL_DEN_NO ");
		sql.add(" ,hdr.SWK_BASE_EST_DEN_NO ");
		// --���F�O���[�v
		sql.add(" ,hdr.SWK_APRV_GRP_CODE ");
		sql.add(sqlDetail2);
		sql.add(" " + addonFinallyHeaderColumn); // �ŏI���o�p�̃w�b�_�[�Ɩ��גǉ�����

		sql.add(" FROM " + tempTableName + " hdr ");
		sql.add(" LEFT OUTER JOIN KMK_MST kmk ON " + companyCodeKMK_MST + " = kmk.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_KMK_CODE = kmk.KMK_CODE ");
		sql.add(" LEFT OUTER JOIN HKM_MST hkm ON " + companyCodeHKM_MST + " = hkm.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_KMK_CODE = hkm.KMK_CODE ");
		sql.add("                            AND hdr.SWK_HKM_CODE = hkm.HKM_CODE ");
		sql.add(" LEFT OUTER JOIN UKM_MST ukm ON " + companyCodeUKM_MST + " = ukm.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_KMK_CODE = ukm.KMK_CODE ");
		sql.add("                            AND hdr.SWK_HKM_CODE = ukm.HKM_CODE ");
		sql.add("                            AND hdr.SWK_UKM_CODE = ukm.UKM_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST dep ON " + companyCodeBMN_MST + " = dep.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_DEP_CODE = dep.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN TRI_MST tri ON " + companyCodeTRI_MST + " = tri.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_TRI_CODE = tri.TRI_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST emp ON " + companyCodeEMP_MST + " = emp.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_EMP_CODE = emp.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST depuke ON " + companyCodeBMN_MST + " = depuke.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_UKE_DEP_CODE = depuke.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST empsyo ON " + companyCodeEMP_MST + " = empsyo.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_SYO_EMP_CODE = empsyo.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST empirai ON " + companyCodeEMP_MST + " = empirai.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_IRAI_EMP_CODE = empirai.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST depirai ON " + companyCodeBMN_MST + " = depirai.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_IRAI_DEP_CODE = depirai.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ON " + companyCodeDEN_SYU_MST + " = syu.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST cur ON " + companyCodeCUR_MST + " = cur.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_CUR_CODE = cur.CUR_CODE ");
		sql.add(" LEFT OUTER JOIN AP_HOH_MST hoh ON " + companyCodeAP_HOH_MST + " = hoh.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_HOH_CODE = hoh.HOH_HOH_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST depkari ON " + companyCodeBMN_MST + " = depkari.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_KARI_DEP_CODE = depkari.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN AP_CBK_MST cbk ON " + companyCodeAP_CBK_MST + " = cbk.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_CBK_CODE = cbk.CBK_CBK_CODE ");
		sql.add(" LEFT OUTER JOIN BNK_MST bnk ON cbk.CBK_BNK_CODE = bnk.BNK_CODE ");
		sql.add("                            AND cbk.CBK_STN_CODE = bnk.BNK_STN_CODE ");
		sql.add(" LEFT OUTER JOIN TRI_SJ_MST trisj ON " + companyCodeTRI_SJ_MST + " = trisj.KAI_CODE ", companyCode);
		sql.add("                                 AND hdr.SWK_TRI_CODE = trisj.TRI_CODE ");
		sql.add("                                 AND hdr.SWK_TJK_CODE = trisj.TJK_CODE ");
		sql.add(" LEFT OUTER JOIN BNK_MST tjkbnk ON trisj.BNK_CODE     = tjkbnk.BNK_CODE ");
		sql.add("                               AND trisj.BNK_STN_CODE = tjkbnk.BNK_STN_CODE ");
		sql.add(" LEFT OUTER JOIN CMP_MST cmp ON hdr.KAI_CODE = cmp.KAI_CODE ");
		sql.add(" LEFT OUTER JOIN ENV_MST env ON hdr.KAI_CODE = env.KAI_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST keyCur ON cmp.KAI_CODE = keyCur.KAI_CODE ");
		sql.add("                               AND cmp.CUR_CODE = keyCur.CUR_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST funcCur ON cmp.KAI_CODE     = funcCur.KAI_CODE ");
		sql.add("                                AND cmp.FNC_CUR_CODE = funcCur.CUR_CODE ");
		sql.add(addonFinallyHeaderJoinSql);
		sql.add(detailJoin);

		sql.add(" WHERE 1 = 1 ");

		// ���������擾
		// VCreator where = getWhereSql(param, false, null);
		VCreator where = getWhereSql(param, null);
		sql.add(where);

		// �ǉ���������
		sql.add(getAddonSearchSQL());

		// �\�[�g��
		sql.add(" ORDER BY ");
		if (!Util.isNullOrEmpty(param.getOrder())) {
			sql.add(param.getOrder());

		} else {
			if (SearchTable.History == searchTable) {
				sql.add("  hdr.KAI_CODE ");
				sql.add(" ,hdr.SWK_DEN_NO ");
				sql.add(" ,hdr.SWK_UPD_CNT ");
				sql.add(" ,hdr.SWK_DEN_DATE ");

			} else {

				if (ServerConfig.isFlagOn("trans.slip.transfer.page.no")) {
					// ���O�C����Ђ͍ŗD��
					sql.add("  CASE WHEN hdr.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode());
					sql.add(" ,hdr.KAI_CODE ");
				} else {
					sql.add("  hdr.KAI_CODE ");
				}

				sql.add(" ,hdr.SWK_DEN_DATE ");
				sql.add(" ,hdr.SWK_DEN_NO ");

				if (isDetail) {
					sql.add(" ,hdr.SWK_GYO_NO ");
				}
			}
		}
		return sql.toSQL();
	}

	/**
	 * @return SELECT
	 */
	protected String getSelectKeyWord() {
		return " SELECT ";
	}

	/**
	 * SELECT�����擾
	 * 
	 * @param conn
	 * @param param
	 * @param isDetail
	 * @param sqlWhere
	 * @param swkHeaderTableName
	 * @param headerTableInitial
	 * @return ����
	 * @throws TException
	 */
	protected int getSwkCount(Connection conn, SlipCondition param, boolean isDetail, VCreator sqlWhere,
		String swkHeaderTableName, String headerTableInitial) throws TException {

		int count = 0;

		try {
			VCreator sql = new VCreator();
			sql.add(" SELECT COUNT(hdr.KAI_CODE) COUNT ");
			sql.add(" FROM " + headerTableInitial + swkHeaderTableName);
			sql.add(sqlWhere);

			// VCreator where = getWhereSql(param, true, headerTableInitial);
			VCreator where = getWhereSql(param, headerTableInitial);
			if (!isDetail) {
				sql.add(" WHERE 1 = 1 ");
			}
			sql.add(where);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				count = rs.getInt("COUNT");
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (SQLException e) {
			throw new TException(e);
		}

		return count;
	}

	/**
	 * @param isDetail
	 * @param tempTableName
	 * @return �ꎞ�e�[�u���p��
	 */
	protected String getTemporaryHeaderColumns(boolean isDetail, String tempTableName) {
		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tempTableName);
		sql.add("   (KAI_CODE ");
		sql.add("   ,SWK_DEN_DATE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SWK_SEI_NO ");
		sql.add("   ,SWK_KMK_CODE ");
		sql.add("   ,SWK_HKM_CODE ");
		sql.add("   ,SWK_UKM_CODE ");
		sql.add("   ,SWK_DEP_CODE ");
		sql.add("   ,SWK_TRI_CODE ");
		sql.add("   ,SWK_EMP_CODE ");
		sql.add("   ,SWK_KIN ");
		sql.add("   ,SWK_DATA_KBN ");
		sql.add("   ,SWK_UKE_DEP_CODE ");
		sql.add("   ,SWK_TEK_CODE ");
		sql.add("   ,SWK_TEK ");
		sql.add("   ,SWK_BEFORE_DEN_NO ");
		sql.add("   ,SWK_UPD_KBN ");
		sql.add("   ,SWK_SYO_EMP_CODE ");
		sql.add("   ,SWK_SYO_DATE ");
		sql.add("   ,SWK_IRAI_EMP_CODE ");
		sql.add("   ,SWK_IRAI_DEP_CODE ");
		sql.add("   ,SWK_IRAI_DATE ");
		sql.add("   ,SWK_SHR_KBN ");
		sql.add("   ,SWK_KSN_KBN ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,UPD_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add("   ,SWK_CUR_CODE ");
		sql.add("   ,SWK_CUR_RATE ");
		sql.add("   ,SWK_IN_KIN ");
		sql.add("   ,SWK_SYS_KBN ");
		sql.add("   ,SWK_DEN_SYU ");
		sql.add("   ,SWK_TUKE_KBN ");
		sql.add("   ,SWK_UPD_CNT ");
		sql.add("   ,SWK_SIHA_KBN ");
		sql.add("   ,SWK_SIHA_DATE ");
		sql.add("   ,SWK_HOH_CODE ");
		sql.add("   ,SWK_HORYU_KBN ");
		sql.add("   ,SWK_KARI_KIN ");
		sql.add("   ,SWK_KEIHI_KIN ");
		sql.add("   ,SWK_SIHA_KIN ");
		sql.add("   ,SWK_KARI_DR_DEN_NO ");
		sql.add("   ,SWK_KARI_CR_DEN_NO ");
		sql.add("   ,SWK_KESAI_KBN ");
		sql.add("   ,SWK_KARI_DEP_CODE ");
		sql.add("   ,SWK_INP_DATE ");
		sql.add("   ,SWK_TJK_CODE ");
		sql.add("   ,SWK_IN_KARI_KIN ");
		sql.add("   ,SWK_IN_KEIHI_KIN ");
		sql.add("   ,SWK_IN_SIHA_KIN ");
		sql.add("   ,SWK_INV_CUR_CODE ");
		sql.add("   ,SWK_INV_CUR_RATE ");
		sql.add("   ,SWK_INV_KIN ");
		sql.add("   ,SWK_CBK_CODE ");
		sql.add("   ,SWK_SSY_DATE ");
		sql.add("   ,SWK_UTK_NO ");
		sql.add("   ,SWK_KARI_CUR_CODE ");
		sql.add("   ,SWK_KARI_CUR_RATE ");
		sql.add("   ,SWK_SEI_KBN ");
		sql.add("   ,SWK_AR_DATE ");
		sql.add("   ,SWK_EST_CANCEL_DEN_NO ");
		sql.add("   ,SWK_BASE_EST_DEN_NO ");
		sql.add("   ,SWK_APRV_GRP_CODE ");
		sql.add("   ,SWK_ADJ_KBN ");

		if (isDetail) {
			sql.add("   ,SWK_BOOK_NO ");
			sql.add("   ,D_SWK_BOOK_NO ");
			sql.add("   ,SWK_GYO_NO ");
			sql.add("   ,D_SWK_NENDO ");
			sql.add("   ,D_SWK_TUKIDO ");
			sql.add("   ,D_SWK_DC_KBN ");
			sql.add("   ,D_SWK_KMK_CODE ");
			sql.add("   ,D_SWK_HKM_CODE ");
			sql.add("   ,D_SWK_UKM_CODE ");
			sql.add("   ,D_SWK_DEP_CODE ");
			sql.add("   ,D_SWK_ZEI_KBN ");
			sql.add("   ,D_SWK_KIN ");
			sql.add("   ,D_SWK_ZEI_KIN ");
			sql.add("   ,D_SWK_ZEI_CODE ");
			sql.add("   ,D_SWK_ZEI_RATE ");
			sql.add("   ,D_SWK_GYO_TEK_CODE ");
			sql.add("   ,D_SWK_GYO_TEK ");
			sql.add("   ,D_SWK_TRI_CODE ");
			sql.add("   ,D_SWK_EMP_CODE ");
			sql.add("   ,D_SWK_KNR_CODE_1 ");
			sql.add("   ,D_SWK_KNR_CODE_2 ");
			sql.add("   ,D_SWK_KNR_CODE_3 ");
			sql.add("   ,D_SWK_KNR_CODE_4 ");
			sql.add("   ,D_SWK_KNR_CODE_5 ");
			sql.add("   ,D_SWK_KNR_CODE_6 ");
			sql.add("   ,D_SWK_HM_1 ");
			sql.add("   ,D_SWK_HM_2 ");
			sql.add("   ,D_SWK_HM_3 ");
			sql.add("   ,D_SWK_AUTO_KBN ");
			sql.add("   ,D_SWK_AT_KMK_CODE ");
			sql.add("   ,D_SWK_AT_HKM_CODE ");
			sql.add("   ,D_SWK_AT_UKM_CODE ");
			sql.add("   ,D_SWK_AT_DEP_CODE ");
			sql.add("   ,D_INP_DATE ");
			sql.add("   ,D_UPD_DATE ");
			sql.add("   ,D_PRG_ID ");
			sql.add("   ,D_USR_ID ");
			sql.add("   ,D_SWK_K_KAI_CODE ");
			sql.add("   ,D_SWK_CUR_CODE ");
			sql.add("   ,D_SWK_CUR_RATE ");
			sql.add("   ,D_SWK_IN_KIN ");
			sql.add("   ,D_SWK_TUKE_KBN ");
			sql.add("   ,D_SWK_IN_ZEI_KIN ");
			sql.add("   ,D_SWK_KESI_KBN ");
			sql.add("   ,D_SWK_KESI_DATE ");
			sql.add("   ,D_SWK_KESI_DEN_NO ");
			sql.add("   ,D_SWK_SOUSAI_DATE ");
			sql.add("   ,D_SWK_SOUSAI_DEN_NO ");
			sql.add("   ,D_HAS_DATE ");
			sql.add("   ,D_EST_DEN_NO ");
			sql.add("   ,D_STL_DEN_NO ");
			sql.add("   ,D_MAE_DEN_NO ");
			sql.add("   ,D_MAE_GYO_NO ");

			// ���R�敪
			if (isUseInvoice) {
				sql.add("   ,D_SWK_FREE_KBN ");
				sql.add("   ,D_SWK_ORI_GYO_NO ");
			}

			// BS��������@�\��ON�̏ꍇ
			if (isUseBS) {
				sql.add("   ,D_SWK_SOUSAI_GYO_NO ");
			}

			// AP/AR�����@�\��ON�̏ꍇ
			if (!isNotUseAP || !isNotUseAR) {
				sql.add("   ,SWK_APAR_KESI_KBN ");
				sql.add("   ,SWK_APAR_DEN_NO ");
				sql.add("   ,SWK_APAR_GYO_NO ");
			}
		}

		sql.add("   ,ROW_TYPE) ");

		return sql.toSQL();
	}

	/**
	 * ���������擾
	 * 
	 * @param param �������� // * @param isForHeaderOnly true:HDR�p�̂�
	 * @param initial
	 * @return ��������
	 */
	protected VCreator getWhereSql(SlipCondition param, String initial) {

		VCreator sql = new VCreator();

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(param.getCompanyCode())) {
			sql.add(" AND hdr.KAI_CODE = ? ", param.getCompanyCode());
		}

		// �`�[�ԍ�
		if (!Util.isNullOrEmpty(param.getSlipNo())) {
			sql.add(" AND hdr.SWK_DEN_NO = ? ", param.getSlipNo());
		}

		// �����敪
		if (param.getAdjDivisionList() != null && param.getAdjDivisionList().length != 0) {

			sql.add(" AND hdr.SWK_ADJ_KBN IN " + SQLUtil.getIntInStatement(param.getAdjDivisionList()));
		}

		// �E�v�R�[�h
		if (!Util.isNullOrEmpty(param.getRemarkCode())) {
			sql.add(" AND hdr.SWK_TEK_CODE = ? ", param.getRemarkCode());
		}

		// �`�[�ԍ�LIKE
		if (!Util.isNullOrEmpty(param.getSlipNoLike())) {
			if (isUseSlipNoAmbiLike) {
				// ������v
				sql.addLikeAmbi(" AND hdr.SWK_DEN_NO ? ", param.getSlipNoLike());
			} else {
				// �O����v
				sql.addLikeFront(" AND hdr.SWK_DEN_NO ? ", param.getSlipNoLike());
			}
		}

		// �`�[�ԍ�From
		if (!Util.isNullOrEmpty(param.getSlipNoFrom())) {
			sql.add(" AND hdr.SWK_DEN_NO >= ? ", param.getSlipNoFrom());
		}

		// �`�[�ԍ�To
		if (!Util.isNullOrEmpty(param.getSlipNoTo())) {
			sql.add(" AND hdr.SWK_DEN_NO <= ? ", param.getSlipNoTo());
		}

		// �f�[�^�敪
		if (param.getDataKindList() != null && param.getDataKindList().length != 0) {
			sql.add(" AND hdr.SWK_DATA_KBN IN " + SQLUtil.getInStatement(param.getDataKindList()));
		}

		// �`�[���
		if (param.getSlipTypeList() != null && param.getSlipTypeList().length != 0) {
			if (param.isIncludeOtherSystem()) {
				// ���V�X�܂�
				sql.add(" AND (   hdr.SWK_DEN_SYU IN " + SQLUtil.getInStatement(param.getSlipTypeList()));
				sql.add("      OR syu.TA_SYS_KBN = 1 "); // ���V�X�敪
				sql.add("     ) ");

			} else {
				sql.add(" AND hdr.SWK_DEN_SYU IN " + SQLUtil.getInStatement(param.getSlipTypeList()));
			}
		}

		// �X�V�敪
		if (param.getSlipStateList() != null && param.getSlipStateList().length != 0) {
			sql.add(" AND hdr.SWK_UPD_KBN IN " + SQLUtil.getIntInStatement(param.getSlipStateList()));
		}

		// �`�[���t
		if (!Util.isNullOrEmpty(param.getSlipDate())) {
			sql.add(" AND hdr.SWK_DEN_DATE = ? ", param.getSlipDate());
		}

		// �`�[���tFROM
		if (!Util.isNullOrEmpty(param.getSlipDateFrom())) {
			sql.add(" AND hdr.SWK_DEN_DATE >= ? ", param.getSlipDateFrom());
		}

		// �`�[���tTO
		if (!Util.isNullOrEmpty(param.getSlipDateTo())) {
			sql.add(" AND hdr.SWK_DEN_DATE <= ? ", param.getSlipDateTo());
		}

		// �˗���FROM
		if (!Util.isNullOrEmpty(param.getRequestDateFrom())) {
			sql.add(" AND hdr.SWK_IRAI_DATE >= ? ", param.getRequestDateFrom());
		}

		// �˗���TO
		if (!Util.isNullOrEmpty(param.getRequestDateTo())) {
			sql.add(" AND hdr.SWK_IRAI_DATE < ? ", DateUtil.addDay(param.getRequestDateTo(), 1));
		}

		// �˗�����R�[�h
		if (param.isRequestDeptCodeIncledNull()) {
			if (!Util.isNullOrEmpty(param.getRequestDeptCode())) {
				sql.add(" AND (   hdr.SWK_IRAI_DEP_CODE = ? ", param.getRequestDeptCode());
				sql.add("      OR hdr.SWK_IRAI_DEP_CODE IS NULL ");
				sql.add("     ) ");
			}
		} else {
			if (!Util.isNullOrEmpty(param.getRequestDeptCode())) {
				sql.add(" AND hdr.SWK_IRAI_DEP_CODE = ? ", param.getRequestDeptCode());
			}
		}

		// �˗��҃R�[�h
		if (param.isRequestEmpCodeIncledNull()) {
			if (!Util.isNullOrEmpty(param.getRequestEmpCode())) {
				sql.add(" AND (   hdr.SWK_IRAI_EMP_CODE = ? ", param.getRequestEmpCode());
				sql.add("      OR hdr.SWK_IRAI_EMP_CODE IS NULL ");
				sql.add("     ) ");
			}
		} else {
			if (!Util.isNullOrEmpty(param.getRequestEmpCode())) {
				sql.add(" AND hdr.SWK_IRAI_EMP_CODE = ? ", param.getRequestEmpCode());
			}
		}

		if (Util.isNullOrEmpty(initial) || Util.equals(HDR_TABLE.AP, initial)) {
			// �x����FROM
			if (!Util.isNullOrEmpty(param.getPaymentDayFrom())) {
				sql.add(" AND hdr.SWK_SIHA_DATE >= ? ", param.getPaymentDayFrom());
			}

			// �x����TO
			if (!Util.isNullOrEmpty(param.getPaymentDayTo())) {
				sql.add(" AND hdr.SWK_SIHA_DATE <= ? ", param.getPaymentDayTo());
			}
		}
		if (Util.equals(HDR_TABLE.AP, initial)) {
			// �o�[��FROM
			if (!Util.isNullOrEmpty(param.getCashDayFrom())) {
				sql.add(" AND hdr.SWK_SIHA_DATE >= ? ", param.getCashDayFrom());
			}

			// �o�[��TO
			if (!Util.isNullOrEmpty(param.getCashDayTo())) {
				sql.add(" AND hdr.SWK_SIHA_DATE <= ? ", param.getCashDayTo());
			}
		}

		if (Util.isNullOrEmpty(initial) || Util.equals(HDR_TABLE.AR, initial)) {
			// ������FROM
			if (!Util.isNullOrEmpty(param.getReceiveDayFrom())) {
				sql.add(" AND hdr.SWK_AR_DATE >= ? ", param.getReceiveDayFrom());
			}

			// ������TO
			if (!Util.isNullOrEmpty(param.getReceiveDayTo())) {
				sql.add(" AND hdr.SWK_AR_DATE <= ? ", param.getReceiveDayTo());
			}
		}
		if (Util.equals(HDR_TABLE.AR, initial)) {
			// �o�[��FROM
			if (!Util.isNullOrEmpty(param.getCashDayFrom())) {
				sql.add(" AND hdr.SWK_AR_DATE   >= ? ", param.getCashDayFrom());
			}

			// �o�[��TO
			if (!Util.isNullOrEmpty(param.getCashDayTo())) {
				sql.add(" AND hdr.SWK_AR_DATE   <= ? ", param.getCashDayTo());
			}
		}

		if (Util.isNullOrEmpty(initial)) {
			// �o�[��FROM
			if (!Util.isNullOrEmpty(param.getCashDayFrom())) {
				sql.add(" AND (   hdr.SWK_SIHA_DATE >= ? ", param.getCashDayFrom());
				sql.add("      OR hdr.SWK_AR_DATE   >= ? ", param.getCashDayFrom());
				sql.add("     ) ");
			}

			// �o�[��TO
			if (!Util.isNullOrEmpty(param.getCashDayTo())) {
				sql.add(" AND (   hdr.SWK_SIHA_DATE <= ? ", param.getCashDayTo());
				sql.add("      OR hdr.SWK_AR_DATE   <= ? ", param.getCashDayTo());
				sql.add("     ) ");
			}
		}

		// ��Њԕt�֓`�[�敪
		if (param.getGroupAccountDivision() != -1) {
			sql.add(" AND hdr.SWK_TUKE_KBN = ? ", param.getGroupAccountDivision());
		}

		// �r���t���O
		if (param.getLockState() != -1) {
			sql.add(" AND hdr.SWK_SHR_KBN = ? ", param.getLockState());
		}

		// �`�[�ԍ�(����)
		if (param.getSlipNoList() != null && param.getSlipNoList().length != 0) {
			sql.add(" AND (  1 = 2 ");
			for (String slipNo : param.getSlipNoList()) {
				sql.add("  OR hdr.SWK_DEN_NO = ? ", slipNo);
			}
			sql.add("     ) ");
		}

		// �Ј�LIKE
		// if (!isForHeaderOnly && !Util.isNullOrEmpty(param.getEmployeeNamesLike())) {
		if (!Util.isNullOrEmpty(param.getEmployeeNamesLike())) {
			sql.addNLikeAmbi(" AND emp.EMP_NAME_S ? ", param.getEmployeeNamesLike());
		}

		// �E�vLIKE
		if (!Util.isNullOrEmpty(param.getRemarksLike())) {
			sql.addNLikeAmbi(" AND hdr.SWK_TEK ? ", param.getRemarksLike());
		}

		// ����旪��LIKE
		// if (!isForHeaderOnly && !Util.isNullOrEmpty(param.getCustmorNamesLike())) {
		if (!Util.equals(HDR_TABLE.GL, initial) && !Util.isNullOrEmpty(param.getCustmorNamesLike())) {
			sql.addNLikeAmbi(" AND tri.TRI_NAME_S ? ", param.getCustmorNamesLike());
		}

		// ����R�[�h
		if (!Util.isNullOrEmpty(param.getDepartmentCode())) {
			sql.add(" AND hdr.SWK_DEP_CODE = ? ", param.getDepartmentCode());
		}

		// ����R�[�hLIKE
		if (!Util.isNullOrEmpty(param.getDepartmentCodeLike())) {
			sql.addLikeFront(" AND hdr.SWK_DEP_CODE ? ", param.getDepartmentCodeLike());
		}

		// ���嗪��LIKE
		// if (!isForHeaderOnly && !Util.isNullOrEmpty(param.getDepartmentNamesLike())) {
		if (!Util.isNullOrEmpty(param.getDepartmentNamesLike())) {
			sql.addNLikeAmbi(" AND dep.DEP_NAME_S ? ", param.getDepartmentNamesLike());
		}

		// �����R�[�h
		if (!Util.isNullOrEmpty(param.getCustmorCode())) {
			sql.add(" AND hdr.SWK_TRI_CODE = ? ", param.getCustmorCode());
		}

		// �X�V���tFrom
		if (param.getUpdateDateFrom() != null) {
			if (Util.isNullOrEmpty(initial)) {
				sql.add(" AND (   NVL(hdr.SWK_INP_DATE, hdr.INP_DATE) >= ? ", param.getUpdateDateFrom());
				sql.add("      OR hdr.UPD_DATE                        >= ? ", param.getUpdateDateFrom());
				sql.add("     ) ");

			} else if (Util.equals(HDR_TABLE.AP, initial)) {
				sql.add(" AND (   hdr.SWK_INP_DATE >= ? ", param.getUpdateDateFrom());
				sql.add("      OR hdr.UPD_DATE                        >= ? ", param.getUpdateDateFrom());
				sql.add("     ) ");

			} else {
				sql.add(" AND (   hdr.INP_DATE >= ? ", param.getUpdateDateFrom());
				sql.add("      OR hdr.UPD_DATE                        >= ? ", param.getUpdateDateFrom());
				sql.add("     ) ");
			}

		} else {
			// �o�^���tFROM
			if (param.getEntryDateFrom() != null) {
				if (Util.isNullOrEmpty(initial)) {
					sql.add(" AND (   hdr.INP_DATE IS NULL ");
					sql.add("      OR hdr.INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");
					sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
					sql.add("      OR hdr.SWK_INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");

				} else if (Util.equals(HDR_TABLE.AP, initial)) {
					sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
					sql.add("      OR hdr.SWK_INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");

				} else {
					sql.add(" AND (   hdr.INP_DATE IS NULL ");
					sql.add("      OR hdr.INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");
				}
			}
		}

		// �o�^���tTO
		if (param.getEntryDateTo() != null) {

			if (Util.isNullOrEmpty(initial)) {
				sql.add(" AND (   hdr.INP_DATE IS NULL ");
				sql.add("      OR hdr.INP_DATE< ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");
				sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
				sql.add("      OR hdr.SWK_INP_DATE < ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");

			} else if (Util.equals(HDR_TABLE.AP, initial)) {
				sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
				sql.add("      OR hdr.SWK_INP_DATE < ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");

			} else {
				sql.add(" AND (   hdr.INP_DATE IS NULL ");
				sql.add("      OR hdr.INP_DATE< ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");
			}
		}

		// �T�Z����t���O
		if (param.isEstimateCancelFlg()) {
			sql.add(" AND hdr.SWK_EST_CANCEL_DEN_NO IS NULL ");
		}

		return sql;
	}

	/**
	 * �ŏI���o�p�̃w�b�_�[�ǉ�JOIN���̎擾
	 * 
	 * @return �ŏI���o�p�̃w�b�_�[�ǉ�JOIN��
	 */
	protected String getAddonFinallyHeaderJoinSql() {
		return "";
	}

	/**
	 * �ŏI�擾����JOIN���̎擾
	 * 
	 * @return �ŏI�擾����JOIN��
	 */
	protected String getDetailJoinSql() {

		String companyCode = getCompanyCode();
		// ��ЃR�[�h
		String companyCodeKMK_MST = isUseKMK_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeHKM_MST = isUseHKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeUKM_MST = isUseUKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeBMN_MST = isUseBMN_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeSZEI_MST = isUseSZEI_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeTRI_MST = isUseTRI_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeEMP_MST = isUseEMP_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeCUR_MST = isUseCUR_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR1_MST = isUseKNR1_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR2_MST = isUseKNR2_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR3_MST = isUseKNR3_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR4_MST = isUseKNR4_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR5_MST = isUseKNR5_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR6_MST = isUseKNR6_MST ? "hdr.KAI_CODE" : "?";

		VCreator sql = new VCreator();
		sql.add(" LEFT OUTER JOIN KMK_MST dkmk ON " + companyCodeKMK_MST + " = dkmk.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_KMK_CODE = dkmk.KMK_CODE ");
		sql.add(" LEFT OUTER JOIN HKM_MST dhkm ON " + companyCodeHKM_MST + " = dhkm.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_KMK_CODE = dhkm.KMK_CODE ");
		sql.add("                             AND hdr.D_SWK_HKM_CODE = dhkm.HKM_CODE ");
		sql.add(" LEFT OUTER JOIN UKM_MST dukm ON " + companyCodeUKM_MST + " = dukm.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_KMK_CODE = dukm.KMK_CODE ");
		sql.add("                             AND hdr.D_SWK_HKM_CODE = dukm.HKM_CODE ");
		sql.add("                             AND hdr.D_SWK_UKM_CODE = dukm.UKM_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST ddep ON " + companyCodeBMN_MST + " = ddep.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_DEP_CODE = ddep.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN SZEI_MST dzei ON " + companyCodeSZEI_MST + " = dzei.KAI_CODE ", companyCode);
		sql.add("                              AND hdr.D_SWK_ZEI_CODE = dzei.ZEI_CODE ");
		sql.add(" LEFT OUTER JOIN TRI_MST dtri ON " + companyCodeTRI_MST + " = dtri.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_TRI_CODE = dtri.TRI_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST demp ON " + companyCodeEMP_MST + " = demp.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_EMP_CODE = demp.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST dcur ON " + companyCodeCUR_MST + " = dcur.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_CUR_CODE = dcur.CUR_CODE ");
		sql.add(" LEFT OUTER JOIN KNR1_MST dknr1 ON " + companyCodeKNR1_MST + " = dknr1.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_1 = dknr1.KNR_CODE_1 ");
		sql.add(" LEFT OUTER JOIN KNR2_MST dknr2 ON " + companyCodeKNR2_MST + " = dknr2.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_2 = dknr2.KNR_CODE_2 ");
		sql.add(" LEFT OUTER JOIN KNR3_MST dknr3 ON " + companyCodeKNR3_MST + " = dknr3.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_3 = dknr3.KNR_CODE_3 ");
		sql.add(" LEFT OUTER JOIN KNR4_MST dknr4 ON " + companyCodeKNR4_MST + " = dknr4.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_4 = dknr4.KNR_CODE_4 ");
		sql.add(" LEFT OUTER JOIN KNR5_MST dknr5 ON " + companyCodeKNR5_MST + " = dknr5.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_5 = dknr5.KNR_CODE_5 ");
		sql.add(" LEFT OUTER JOIN KNR6_MST dknr6 ON " + companyCodeKNR6_MST + " = dknr6.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_6 = dknr6.KNR_CODE_6 ");
		return sql.toSQL();
	}

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @param searchTable �����Ώ�
	 * @return �`�[���X�g
	 */
	public List<Slip> findSlipByCondition(SlipCondition param, SearchTable searchTable) {

		Connection conn = null;

		List<Slip> list = new ArrayList<Slip>();

		try {
			conn = DBUtil.getConnection();

			String sql = getDefaultSqlIncludeDetails(conn, param, searchTable, true);
			Statement statement = DBUtil.getStatement(conn);

			ResultSet rs = DBUtil.select(statement, sql);

			String companyCode = null;
			String slipNo = null;
			int slipUpdateCount = -1;
			Slip slip = null;

			while (rs.next()) {

				// ��ЃR�[�h�A�`�[�ԍ��A�`�[�C���񐔂̂����ꂩ���ς������V�K�`�[
				if (!Util.avoidNull(rs.getString("KAI_CODE")).equals(companyCode)
					|| !Util.avoidNull(rs.getString("SWK_DEN_NO")).equals(slipNo)
					|| rs.getInt("SWK_UPD_CNT") != slipUpdateCount) {

					if (companyCode != null) {
						list.add(slip);
					}

					SWK_HDR hdr = mappingHeader(rs);
					slip = new Slip(hdr);
				}

				// ����
				slip.addDetail(mappingDetail(rs));

				// �L�[�ޔ�
				companyCode = Util.avoidNull(rs.getString("KAI_CODE"));
				slipNo = Util.avoidNull(rs.getString("SWK_DEN_NO"));
				slipUpdateCount = rs.getInt("SWK_UPD_CNT");
			}

			if (slip != null && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
				list.add(slip);
			}

			DBUtil.close(rs);
			if (param.isSearchWorkFlow() && !Util.isNullOrEmpty(param.getSlipNo())) {
				sql = getWorkflowApproveSQL(conn, param);
				rs = DBUtil.select(statement, sql);
				// ���F�����ێ����݂̂��擾
				List<SWK_SYO_CTL> ctlList = new ArrayList();
				while (rs.next()) {
					SWK_SYO_CTL ctl = new SWK_SYO_CTL();
					ctl.setKAI_CODE(rs.getString("KAI_CODE"));
					ctl.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
					ctl.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
					ctl.setSEQ(rs.getInt("SEQ"));
					ctl.setUPD_KBN(rs.getInt("UPD_KBN"));
					ctl.setAPRV_ROLE_GRP_CODE(rs.getString("APRV_ROLE_GRP_CODE"));
					ctl.setPREV_UPD_KBN(rs.getInt("PREV_UPD_KBN"));
					ctl.setAPRV_ROLE_CODE(rs.getString("APRV_ROLE_CODE"));
					ctl.setNEXT_APRV_ROLE_CODE(rs.getString("NEXT_APRV_ROLE_CODE"));
					ctl.setINP_DATE(rs.getDate("INP_DATE"));
					ctl.setPRG_ID(rs.getString("PRG_ID"));
					ctl.setUSR_ID(rs.getString("USR_ID"));

					ctl.setAprvRoleGroupName(rs.getString("APRV_ROLE_GRP_NAME"));
					ctl.setAprvRoleName(rs.getString("APRV_ROLE_NAME"));
					ctl.setNextAprvRoleName(rs.getString("NEXT_APRV_ROLE_NAME"));

					ctl.setAprvRoleSkippable(rs.getInt("APRV_SKIP_FLG") == 1);
					ctl.setNextAprvRoleSkippable(rs.getInt("NEXT_APRV_SKIP_FLG") == 1);

					ctlList.add(ctl);

				}
				DBUtil.close(rs);
				HDR: for (Slip s : list) {
					for (SWK_SYO_CTL ctl : ctlList) {
						if (Util.equals(ctl.getKAI_CODE(), s.getHeader().getKAI_CODE()) && Util.equals(ctl.getSWK_DEN_NO(), s.getHeader().getSWK_DEN_NO()) && DateUtil.equals(ctl.getSWK_DEN_DATE(), s.getHeader().getSWK_DEN_DATE())) {
							s.getHeader().setSyoCtl(ctl);
							// �ǉ����Ď��̃w�b�_��
							continue HDR;
						}
					}
				}
			}
			DBUtil.close(statement);
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// ���[�N�t���[���F���p��
				setupSlipApproveGroup(list);
			}

		} catch (SlipNoDataException e) {
			return list;

		} catch (Exception e) {
			throw new TRuntimeException(e);

		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @return �`�[���X�g
	 */
	public List<Slip> findSlipByCondition(SlipCondition param) {
		return findSlipByCondition(param, SearchTable.Slip);
	}

	/**
	 * �����ɂ��p�^�[������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	public List<Slip> findSlipPatternByCondition(SlipCondition param) {
		return findSlipByCondition(param, SearchTable.Pattern);
	}

	/**
	 * �����ɂ�闚������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	public List<Slip> findSlipHistoryByCondition(SlipCondition param) {
		return findSlipByCondition(param, SearchTable.History);
	}

	/**
	 * �w�b�_�[�}�b�s���O
	 * 
	 * @param rs
	 * @return �w�b�_
	 * @throws Exception
	 */
	protected SWK_HDR mappingHeader(ResultSet rs) throws Exception {

		SWK_HDR hdr = newHeader();
		hdr.setExistAttachment(rs.getInt("ATTACH_COUNT") != 0);
		hdr.setKAI_CODE(rs.getString("KAI_CODE"));
		hdr.setKAI_NAME(rs.getString("KAI_NAME"));
		hdr.setKAI_NAME_S(rs.getString("KAI_NAME_S"));
		hdr.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		hdr.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		hdr.setSWK_SEI_NO(rs.getString("SWK_SEI_NO"));
		hdr.setSWK_KMK_CODE(rs.getString("SWK_KMK_CODE"));
		hdr.setSWK_KMK_NAME(rs.getString("SWK_KMK_NAME"));
		hdr.setSWK_KMK_NAME_S(rs.getString("SWK_KMK_NAME_S"));
		hdr.setSWK_HKM_CODE(rs.getString("SWK_HKM_CODE"));
		hdr.setSWK_HKM_NAME(rs.getString("SWK_HKM_NAME"));
		hdr.setSWK_HKM_NAME_S(rs.getString("SWK_HKM_NAME_S"));
		hdr.setSWK_UKM_CODE(rs.getString("SWK_UKM_CODE"));
		hdr.setSWK_UKM_NAME(rs.getString("SWK_UKM_NAME"));
		hdr.setSWK_UKM_NAME_S(rs.getString("SWK_UKM_NAME_S"));
		hdr.setSWK_DEP_CODE(rs.getString("SWK_DEP_CODE"));
		hdr.setSWK_DEP_NAME(rs.getString("SWK_DEP_NAME"));
		hdr.setSWK_DEP_NAME_S(rs.getString("SWK_DEP_NAME_S"));
		hdr.setSWK_TRI_CODE(rs.getString("SWK_TRI_CODE"));
		hdr.setSWK_TRI_NAME(rs.getString("SWK_TRI_NAME"));
		hdr.setSWK_TRI_NAME_S(rs.getString("SWK_TRI_NAME_S"));
		hdr.setSWK_EMP_CODE(rs.getString("SWK_EMP_CODE"));
		hdr.setSWK_EMP_NAME(rs.getString("SWK_EMP_NAME"));
		hdr.setSWK_EMP_NAME_S(rs.getString("SWK_EMP_NAME_S"));
		hdr.setSWK_KIN(rs.getBigDecimal("SWK_KIN"));
		hdr.setSWK_DATA_KBN(rs.getString("SWK_DATA_KBN"));
		hdr.setSWK_UKE_DEP_CODE(rs.getString("SWK_UKE_DEP_CODE"));
		hdr.setSWK_UKE_DEP_NAME(rs.getString("SWK_UKE_DEP_NAME"));
		hdr.setSWK_UKE_DEP_NAME_S(rs.getString("SWK_UKE_DEP_NAME_S"));
		hdr.setSWK_TEK_CODE(rs.getString("SWK_TEK_CODE"));
		hdr.setSWK_TEK(rs.getString("SWK_TEK"));
		hdr.setSWK_BEFORE_DEN_NO(rs.getString("SWK_BEFORE_DEN_NO"));
		hdr.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		hdr.setSWK_SYO_EMP_CODE(rs.getString("SWK_SYO_EMP_CODE"));
		hdr.setSWK_SYO_EMP_NAME(rs.getString("SWK_SYO_EMP_NAME"));
		hdr.setSWK_SYO_EMP_NAME_S(rs.getString("SWK_SYO_EMP_NAME_S"));
		hdr.setSWK_SYO_DATE(rs.getTimestamp("SWK_SYO_DATE"));
		hdr.setSWK_IRAI_EMP_CODE(rs.getString("SWK_IRAI_EMP_CODE"));
		hdr.setSWK_IRAI_EMP_NAME(rs.getString("SWK_IRAI_EMP_NAME"));
		hdr.setSWK_IRAI_EMP_NAME_S(rs.getString("SWK_IRAI_EMP_NAME_S"));
		hdr.setSWK_IRAI_DEP_CODE(rs.getString("SWK_IRAI_DEP_CODE"));
		hdr.setSWK_IRAI_DEP_NAME(rs.getString("SWK_IRAI_DEP_NAME"));
		hdr.setSWK_IRAI_DEP_NAME_S(rs.getString("SWK_IRAI_DEP_NAME_S"));
		hdr.setSWK_IRAI_DATE(rs.getTimestamp("SWK_IRAI_DATE"));
		hdr.setSWK_SHR_KBN(rs.getInt("SWK_SHR_KBN"));
		hdr.setSWK_KSN_KBN(rs.getInt("SWK_KSN_KBN"));
		hdr.setINP_DATE(rs.getTimestamp("INP_DATE"));
		hdr.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		hdr.setPRG_ID(rs.getString("PRG_ID"));
		hdr.setUSR_ID(rs.getString("USR_ID"));
		hdr.setSWK_CUR_CODE(rs.getString("SWK_CUR_CODE"));
		hdr.setSWK_CUR_DEC_KETA(rs.getInt("SWK_CUR_DEC_KETA"));
		hdr.setSWK_CUR_RATE(rs.getBigDecimal("SWK_CUR_RATE"));
		hdr.setSWK_IN_KIN(rs.getBigDecimal("SWK_IN_KIN"));
		hdr.setSWK_SYS_KBN(rs.getString("SWK_SYS_KBN"));
		hdr.setSWK_DEN_SYU(rs.getString("SWK_DEN_SYU"));
		hdr.setSWK_DEN_SYU_NAME(rs.getString("SWK_DEN_SYU_NAME"));
		hdr.setSWK_DEN_SYU_NAME_S(rs.getString("SWK_DEN_SYU_NAME_S"));
		hdr.setSWK_DEN_SYU_NAME_K(rs.getString("SWK_DEN_SYU_NAME_K"));
		hdr.setSWK_TUKE_KBN(rs.getInt("SWK_TUKE_KBN"));
		hdr.setSWK_UPD_CNT(rs.getInt("SWK_UPD_CNT"));
		hdr.setSWK_SIHA_KBN(rs.getString("SWK_SIHA_KBN"));
		hdr.setSWK_SIHA_DATE(rs.getDate("SWK_SIHA_DATE"));
		hdr.setSWK_HOH_CODE(rs.getString("SWK_HOH_CODE"));
		hdr.setSWK_HOH_NAME(rs.getString("SWK_HOH_NAME"));
		hdr.setSWK_HOH_NAI_CODE(rs.getString("SWK_HOH_NAI_CODE")); // �x�������R�[�h
		hdr.setSWK_HORYU_KBN(rs.getInt("SWK_HORYU_KBN"));
		hdr.setSWK_KARI_KIN(rs.getBigDecimal("SWK_KARI_KIN"));
		hdr.setSWK_KEIHI_KIN(rs.getBigDecimal("SWK_KEIHI_KIN"));
		hdr.setSWK_SIHA_KIN(rs.getBigDecimal("SWK_SIHA_KIN"));
		hdr.setSWK_KARI_DR_DEN_NO(rs.getString("SWK_KARI_DR_DEN_NO"));
		hdr.setSWK_KARI_CR_DEN_NO(rs.getString("SWK_KARI_CR_DEN_NO"));
		hdr.setSWK_KESAI_KBN(rs.getInt("SWK_KESAI_KBN"));
		hdr.setSWK_KARI_DEP_CODE(rs.getString("SWK_KARI_DEP_CODE"));
		hdr.setSWK_KARI_DEP_NAME(rs.getString("SWK_KARI_DEP_NAME"));
		hdr.setSWK_KARI_DEP_NAME_S(rs.getString("SWK_KARI_DEP_NAME_S"));
		hdr.setSWK_INP_DATE(rs.getTimestamp("SWK_INP_DATE"));
		hdr.setSWK_TJK_CODE(rs.getString("SWK_TJK_CODE"));
		hdr.setSWK_TJK_YKN_KBN(rs.getInt("TJK_YKN_KBN"));
		hdr.setSWK_TJK_YKN_NO(rs.getString("TJK_YKN_NO"));
		hdr.setSWK_TJK_YKN_KANA(rs.getString("TJK_YKN_KANA"));
		hdr.setSWK_TJK_GS_BNK_NAME(rs.getString("SWK_TJK_GS_BNK_NAME")); // ��d�����E�p����s
		hdr.setSWK_TJK_GS_STN_NAME(rs.getString("SWK_TJK_GS_STN_NAME")); // ��d�����E�p���x�X
		hdr.setSWK_TJK_BNK_NAME_S(rs.getString("SWK_TJK_BNK_NAME_S"));
		hdr.setSWK_TJK_BNK_STN_NAME_S(rs.getString("SWK_TJK_BNK_STN_NAME_S"));
		hdr.setSWK_IN_KARI_KIN(rs.getBigDecimal("SWK_IN_KARI_KIN"));
		hdr.setSWK_IN_KEIHI_KIN(rs.getBigDecimal("SWK_IN_KEIHI_KIN"));
		hdr.setSWK_IN_SIHA_KIN(rs.getBigDecimal("SWK_IN_SIHA_KIN"));
		hdr.setSWK_INV_CUR_CODE(rs.getString("SWK_INV_CUR_CODE"));
		hdr.setSWK_INV_CUR_RATE(rs.getBigDecimal("SWK_INV_CUR_RATE"));
		hdr.setSWK_INV_KIN(rs.getBigDecimal("SWK_INV_KIN"));
		hdr.setSWK_CBK_CODE(rs.getString("SWK_CBK_CODE"));
		hdr.setSWK_CBK_NAME(rs.getString("SWK_CBK_NAME"));
		hdr.setSWK_CBK_STN_CODE(rs.getString("SWK_CBK_STN_CODE"));
		hdr.setSWK_CBK_YKN_KBN(rs.getInt("SWK_CBK_YKN_KBN"));
		hdr.setSWK_CBK_YKN_NO(rs.getString("SWK_CBK_YKN_NO"));
		hdr.setSWK_BNK_NAME_S(rs.getString("SWK_BNK_NAME_S"));
		hdr.setSWK_BNK_STN_NAME_S(rs.getString("SWK_BNK_STN_NAME_S"));
		hdr.setSWK_SSY_DATE(rs.getDate("SWK_SSY_DATE"));
		hdr.setSWK_UTK_NO(rs.getString("SWK_UTK_NO"));
		hdr.setSWK_KARI_CUR_CODE(rs.getString("SWK_KARI_CUR_CODE"));
		hdr.setSWK_KARI_CUR_RATE(rs.getBigDecimal("SWK_KARI_CUR_RATE"));
		hdr.setSWK_SEI_KBN(rs.getString("SWK_SEI_KBN"));
		hdr.setSWK_AR_DATE(rs.getDate("SWK_AR_DATE"));
		hdr.setSWK_ADJ_KBN(rs.getInt("SWK_ADJ_KBN"));
		hdr.setKEY_CUR_CODE(rs.getString("KEY_CUR_CODE"));
		hdr.setKEY_CUR_DEC_KETA(rs.getInt("KEY_CUR_DEC_KETA"));
		hdr.setFUNC_CUR_CODE(rs.getString("FUNC_CUR_CODE"));
		hdr.setFUNC_CUR_DEC_KETA(rs.getInt("FUNC_CUR_DEC_KETA"));

		// --PE�ǉ���
		hdr.setSWK_EST_CANCEL_DEN_NO(rs.getString("SWK_EST_CANCEL_DEN_NO"));// �T�Z����`�[�ԍ�
		hdr.setSWK_BASE_EST_DEN_NO(rs.getString("SWK_BASE_EST_DEN_NO"));// ���T�Z�`�[�ԍ�
		// ���F�����O���[�v
		hdr.setSWK_APRV_GRP_CODE(rs.getString("SWK_APRV_GRP_CODE"));

		if (isUseTag && rs.getInt("TAG_COUNT") != 0) {
			SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
			hdr.setSwkTags(tag.get(hdr.getKAI_CODE(), hdr.getSWK_DEN_NO()));
		} else {
			hdr.setSwkTags(new ArrayList<SWK_TAG>());
		}

		return hdr;

	}

	/**
	 * ���׃}�b�s���O
	 * 
	 * @param rs
	 * @return ����
	 * @throws Exception
	 */
	protected SWK_DTL mappingDetail(ResultSet rs) throws Exception {

		SWK_DTL dtl = newDetail();
		dtl.setKAI_CODE(rs.getString("KAI_CODE"));
		dtl.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		dtl.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		dtl.setSWK_BOOK_NO(rs.getInt("D_SWK_BOOK_NO"));
		dtl.setSWK_GYO_NO(rs.getInt("SWK_GYO_NO"));
		dtl.setSWK_ADJ_KBN(rs.getInt("SWK_ADJ_KBN"));
		dtl.setSWK_NENDO(rs.getInt("D_SWK_NENDO"));
		dtl.setSWK_TUKIDO(rs.getInt("D_SWK_TUKIDO"));
		dtl.setSWK_SEI_NO(rs.getString("SWK_SEI_NO"));
		dtl.setSWK_DC_KBN(rs.getInt("D_SWK_DC_KBN"));
		dtl.setSWK_KMK_CODE(rs.getString("D_SWK_KMK_CODE"));
		dtl.setSWK_HKM_CODE(rs.getString("D_SWK_HKM_CODE"));
		dtl.setSWK_UKM_CODE(rs.getString("D_SWK_UKM_CODE"));
		dtl.setSWK_DEP_CODE(rs.getString("D_SWK_DEP_CODE"));
		dtl.setSWK_ZEI_KBN(rs.getInt("D_SWK_ZEI_KBN"));
		dtl.setSWK_KIN(rs.getBigDecimal("D_SWK_KIN"));
		dtl.setSWK_ZEI_KIN(rs.getBigDecimal("D_SWK_ZEI_KIN"));
		dtl.setSWK_ZEI_CODE(rs.getString("D_SWK_ZEI_CODE"));
		dtl.setSWK_ZEI_RATE(rs.getBigDecimal("D_SWK_ZEI_RATE"));
		dtl.setSWK_GYO_TEK_CODE(rs.getString("D_SWK_GYO_TEK_CODE"));
		dtl.setSWK_GYO_TEK(rs.getString("D_SWK_GYO_TEK"));
		dtl.setSWK_TRI_CODE(rs.getString("D_SWK_TRI_CODE"));
		dtl.setSWK_EMP_CODE(rs.getString("D_SWK_EMP_CODE"));
		dtl.setSWK_KNR_CODE_1(rs.getString("D_SWK_KNR_CODE_1"));
		dtl.setSWK_KNR_CODE_2(rs.getString("D_SWK_KNR_CODE_2"));
		dtl.setSWK_KNR_CODE_3(rs.getString("D_SWK_KNR_CODE_3"));
		dtl.setSWK_KNR_CODE_4(rs.getString("D_SWK_KNR_CODE_4"));
		dtl.setSWK_KNR_CODE_5(rs.getString("D_SWK_KNR_CODE_5"));
		dtl.setSWK_KNR_CODE_6(rs.getString("D_SWK_KNR_CODE_6"));
		dtl.setSWK_HM_1(rs.getString("D_SWK_HM_1"));
		dtl.setSWK_HM_2(rs.getString("D_SWK_HM_2"));
		dtl.setSWK_HM_3(rs.getString("D_SWK_HM_3"));
		dtl.setSWK_AUTO_KBN(rs.getInt("D_SWK_AUTO_KBN"));
		dtl.setSWK_DATA_KBN(rs.getString("SWK_DATA_KBN"));
		dtl.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		dtl.setSWK_KSN_KBN(rs.getInt("SWK_KSN_KBN"));
		dtl.setSWK_AT_KMK_CODE(rs.getString("D_SWK_AT_KMK_CODE"));
		dtl.setSWK_AT_HKM_CODE(rs.getString("D_SWK_AT_HKM_CODE"));
		dtl.setSWK_AT_UKM_CODE(rs.getString("D_SWK_AT_UKM_CODE"));
		dtl.setSWK_AT_DEP_CODE(rs.getString("D_SWK_AT_DEP_CODE"));
		dtl.setINP_DATE(rs.getDate("D_INP_DATE"));
		dtl.setUPD_DATE(rs.getDate("D_UPD_DATE"));
		dtl.setPRG_ID(rs.getString("D_PRG_ID"));
		dtl.setUSR_ID(rs.getString("D_USR_ID"));
		dtl.setSWK_K_KAI_CODE(rs.getString("D_SWK_K_KAI_CODE"));
		dtl.setSWK_CUR_CODE(rs.getString("D_SWK_CUR_CODE"));
		dtl.setSWK_CUR_RATE(rs.getBigDecimal("D_SWK_CUR_RATE"));
		dtl.setSWK_IN_KIN(rs.getBigDecimal("D_SWK_IN_KIN"));
		dtl.setSWK_TUKE_KBN(rs.getInt("D_SWK_TUKE_KBN"));
		dtl.setSWK_IN_ZEI_KIN(rs.getBigDecimal("D_SWK_IN_ZEI_KIN"));
		dtl.setSWK_KESI_KBN(rs.getInt("D_SWK_KESI_KBN"));
		dtl.setSWK_KESI_DATE(rs.getDate("D_SWK_KESI_DATE"));
		dtl.setSWK_KESI_DEN_NO(rs.getString("D_SWK_KESI_DEN_NO"));
		dtl.setSWK_SOUSAI_DATE(rs.getDate("D_SWK_SOUSAI_DATE"));
		dtl.setSWK_SOUSAI_DEN_NO(rs.getString("D_SWK_SOUSAI_DEN_NO"));

		// ���R�敪
		if (isUseInvoice) {
			dtl.setSWK_FREE_KBN(rs.getInt("D_SWK_FREE_KBN"));
			dtl.setSWK_ORI_GYO_NO(rs.getInt("D_SWK_ORI_GYO_NO"));
		}

		// BS��������@�\��ON�̏ꍇ
		if (isUseBS && !Util.isNullOrEmpty(rs.getString("D_SWK_SOUSAI_GYO_NO"))) {
			dtl.setSWK_SOUSAI_GYO_NO(rs.getInt("D_SWK_SOUSAI_GYO_NO"));
		}

		// AP/AR�����@�\��ON�̏ꍇ
		if (!isNotUseAP || !isNotUseAR) {
			dtl.setSWK_APAR_KESI_KBN(rs.getInt("SWK_APAR_KESI_KBN"));
			dtl.setSWK_APAR_DEN_NO(rs.getString("SWK_APAR_DEN_NO"));

			if (!Util.isNullOrEmpty(rs.getString("SWK_APAR_GYO_NO"))) {
				dtl.setSWK_APAR_GYO_NO(rs.getInt("SWK_APAR_GYO_NO"));
			}
		}

		dtl.setHAS_DATE(rs.getDate("D_HAS_DATE"));
		dtl.setDEN_SYU_CODE(rs.getString("SWK_DEN_SYU"));
		dtl.setSWK_K_KAI_NAME(rs.getString("KAI_NAME"));
		dtl.setSWK_K_KAI_NAME_S(rs.getString("KAI_NAME_S"));
		dtl.setSWK_EMP_NAME(rs.getString("D_EMP_NAME"));
		dtl.setSWK_EMP_NAME_S(rs.getString("D_EMP_NAME_S"));
		dtl.setSWK_DEP_NAME(rs.getString("D_DEP_NAME"));
		dtl.setSWK_DEP_NAME_S(rs.getString("D_DEP_NAME_S"));
		dtl.setSWK_KMK_NAME(rs.getString("D_KMK_NAME"));
		dtl.setSWK_KMK_NAME_S(rs.getString("D_KMK_NAME_S"));
		dtl.setSWK_HKM_NAME(rs.getString("D_HKM_NAME"));
		dtl.setSWK_HKM_NAME_S(rs.getString("D_HKM_NAME_S"));
		dtl.setSWK_UKM_NAME(rs.getString("D_UKM_NAME"));
		dtl.setSWK_UKM_NAME_S(rs.getString("D_UKM_NAME_S"));
		dtl.setCUR_DEC_KETA(rs.getInt("D_DEC_KETA"));
		dtl.setSWK_ZEI_NAME(rs.getString("D_ZEI_NAME"));
		dtl.setSWK_ZEI_NAME_S(rs.getString("D_ZEI_NAME_S"));
		dtl.setSWK_TRI_NAME(rs.getString("D_TRI_NAME"));
		dtl.setSWK_TRI_NAME_S(rs.getString("D_TRI_NAME_S"));
		dtl.setSWK_KNR_NAME_1(rs.getString("D_KNR_NAME_1"));
		dtl.setSWK_KNR_NAME_S_1(rs.getString("D_KNR_NAME_S_1"));
		dtl.setSWK_KNR_NAME_2(rs.getString("D_KNR_NAME_2"));
		dtl.setSWK_KNR_NAME_S_2(rs.getString("D_KNR_NAME_S_2"));
		dtl.setSWK_KNR_NAME_3(rs.getString("D_KNR_NAME_3"));
		dtl.setSWK_KNR_NAME_S_3(rs.getString("D_KNR_NAME_S_3"));
		dtl.setSWK_KNR_NAME_4(rs.getString("D_KNR_NAME_4"));
		dtl.setSWK_KNR_NAME_S_4(rs.getString("D_KNR_NAME_S_4"));
		dtl.setSWK_KNR_NAME_5(rs.getString("D_KNR_NAME_5"));
		dtl.setSWK_KNR_NAME_S_5(rs.getString("D_KNR_NAME_S_5"));
		dtl.setSWK_KNR_NAME_6(rs.getString("D_KNR_NAME_6"));
		dtl.setSWK_KNR_NAME_S_6(rs.getString("D_KNR_NAME_S_6"));
		dtl.setKEY_CUR_CODE(rs.getString("KEY_CUR_CODE"));
		dtl.setKEY_CUR_DEC_KETA(rs.getInt("KEY_CUR_DEC_KETA"));
		dtl.setFUNC_CUR_CODE(rs.getString("FUNC_CUR_CODE"));
		dtl.setFUNC_CUR_DEC_KETA(rs.getInt("FUNC_CUR_DEC_KETA"));

		// --PE�ǉ���
		dtl.setSWK_EST_DEN_NO(rs.getString("D_EST_DEN_NO"));
		dtl.setSWK_STL_DEN_NO(rs.getString("D_STL_DEN_NO"));
		dtl.setSWK_MAE_DEN_NO(rs.getString("D_MAE_DEN_NO"));
		dtl.setSWK_MAE_GYO_NO(rs.getInt("D_MAE_GYO_NO"));

		// �������t���O
		if (!Util.isNullOrEmpty(rs.getString("D_UKM_HAS_FLG"))) {
			dtl.setUseItemOccurDate(BooleanUtil.toBoolean(rs.getInt("D_UKM_HAS_FLG")));
		} else if (!Util.isNullOrEmpty(rs.getString("D_HKM_HAS_FLG"))) {
			dtl.setUseItemOccurDate(BooleanUtil.toBoolean(rs.getInt("D_HKM_HAS_FLG")));
		} else {
			dtl.setUseItemOccurDate(BooleanUtil.toBoolean(rs.getInt("D_KMK_HAS_FLG")));
		}

		return dtl;
	}

	/**
	 * �w�b�_����
	 * 
	 * @return �w�b�_
	 */
	protected SWK_HDR newHeader() {
		return new SWK_HDR();
	}

	/**
	 * ���א���
	 * 
	 * @return ����
	 */
	protected SWK_DTL newDetail() {
		return new SWK_DTL();
	}

	/**
	 * �w�b�_�[�ǉ����ڂ̎擾<br>
	 * <b>�擪�u,�v�K�v</b>
	 * 
	 * @param tableName
	 * @return �w�b�_�[�ǉ�����
	 */
	@SuppressWarnings("unused")
	protected String getAddonHeaderColumn(String tableName) {
		return "";
	}

	/**
	 * �ŏI���o�p�̃w�b�_�[�ǉ����ڂ̎擾<br>
	 * <b>�擪�u,�v�K�v</b>
	 * 
	 * @return �ŏI���o�p�̃w�b�_�[�ǉ�����
	 */
	protected String getAddonFinallyHeaderColumn() {
		return "";
	}

	/**
	 * ���גǉ����ڂ̎擾<br>
	 * <b>�擪�u,�v�K�v</b>
	 * 
	 * @return ���גǉ�����
	 */
	protected String getAddonDetailColumn() {
		return "";
	}

	/**
	 * �ŏI���o�p�̖��גǉ����ڂ̎擾<br>
	 * <b>�擪�u,�v�K�v</b>
	 * 
	 * @return �ŏI���o�p�̖��גǉ�����
	 */
	protected String getAddonFinallyDetailColumn() {
		return "";
	}

	/**
	 * �ǉ��`�[��������SQL�̎擾
	 * 
	 * @return �ǉ��`�[��������SQL
	 */
	protected String getAddonSearchSQL() {
		return "";
	}

	/**
	 * �`�[���������̎擾
	 * 
	 * @return param �`�[��������
	 */
	public SlipCondition getParam() {
		return param_;
	}

	/**
	 * �`�[���������̐ݒ�
	 * 
	 * @param param �`�[��������
	 */
	public void setParam(SlipCondition param) {
		this.param_ = param;
	}

	/**
	 * �����Ώۃe�[�u���̎擾
	 * 
	 * @return searchTable �����Ώۃe�[�u��
	 */
	public SearchTable getSearchTable() {
		return searchTable_;
	}

	/**
	 * �����Ώۃe�[�u���̐ݒ�
	 * 
	 * @param searchTable �����Ώۃe�[�u��
	 */
	public void setSearchTable(SearchTable searchTable) {
		this.searchTable_ = searchTable;
	}

	/**
	 * ���ג��o���邩�ǂ����̎擾
	 * 
	 * @return isDetail ���ג��o���邩�ǂ���
	 */
	public boolean isDetail() {
		return isDetail_;
	}

	/**
	 * ���ג��o���邩�ǂ����̐ݒ�
	 * 
	 * @param isDetail ���ג��o���邩�ǂ���
	 */
	public void setDetail(boolean isDetail) {
		this.isDetail_ = isDetail;
	}

	/**
	 * �����Ώۃf�[�^�Ȃ�Exception
	 */
	protected class SlipNoDataException extends TException {
		// ���ʂ̂�
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
