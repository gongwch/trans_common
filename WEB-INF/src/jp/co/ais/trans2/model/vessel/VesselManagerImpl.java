package jp.co.ais.trans2.model.vessel;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bunkertype.*;

/**
 * Vessel�Ǘ��̎����N���X
 * 
 * @author AIS
 */
public class VesselManagerImpl extends TModel implements VesselManager {

	/** OP�@�\�s�g�p */
	public static boolean isNotUseOP = ServerConfig.isFlagOn("trans.op.not.use");

	/** OP VESSEL SHORT NAME ���p���� */
	public static boolean isUseShortName = ServerConfig.isFlagOn("trans.op.use.vessel.short.name");

	/**
	 * �w������ɊY������Vessel����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������PEVessel���
	 * @throws TException
	 */
	public List<Vessel> get(VesselSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();
		boolean isUseBM = isUseBM();

		List<Vessel> list = new ArrayList<Vessel>();
		try {

			VCreator sql = getSQL(condition, isUseBM);

			sql.add("ORDER BY ");
			sql.add("  vessel.KAI_CODE ");
			sql.add(" ,vessel.VESSEL_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				Vessel bean = mapping(rs, isUseBM);
				list.add(bean);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			if (!isNotUseOP && condition.isIncludeDetail() && !list.isEmpty()) {

				List<String> codeList = new ArrayList<String>();

				for (Vessel bean : list) {
					codeList.add(bean.getCode());
				}

				VesselSearchCondition param = new VesselSearchCondition();
				param.setCompanyCode(condition.getCompanyCode());
				param.setCodeList(codeList);

				List<VesselSpeed> speedList = getSpeedList(conn, param);
				List<VesselPortCons> portConsList = getPortConsList(conn, param);
				List<VesselSeaCons> seaConsList = getSeaConsList(conn, param);
				List<VesselHold> holdList = getHoldList(conn, param);
				List<VesselBunkerType> bunkerTypeList = getBunkerTypeList(conn, param);
				List<CM_BNKR_TYPE_MST> defaultBunkerTypeList = getDefaultBunkerTypeList();

				// �ڍ׏�������
				setupDetailList(list, speedList, portConsList, seaConsList, holdList, bunkerTypeList,
					defaultBunkerTypeList);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * �w������ɊY������Vessel����Ԃ��B(���ӁF�C���N�������g�T�[�`�p���̂�)
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Vessel���
	 * @throws TException
	 */
	public List<Vessel> getVesselForSearch(VesselSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<Vessel> list = new ArrayList<Vessel>();
		try {

			// �������`�F�b�N����
			VCreator chk = new VCreator();
			chk.add("SELECT COUNT(1) ");
			chk.add("FROM CM_VESSEL_MST vessel");
			chk.add("WHERE 1 = 1 ");
			setupWhere(condition, chk);

			int count = DBUtil.selectOneInt(conn, chk.toSQL());
			if (count == 0) {
				return list;
			}

			VCreator sql = new VCreator();
			String ownrship = OPCodeDivision.OWRSHIP.toString();

			sql.add(" SELECT ");
			sql.add("  vessel.KAI_CODE ");
			sql.add(" ,vessel.VESSEL_CODE ");
			sql.add(" ,vessel.VESSEL_NAME ");
			sql.add(" ,vessel.VESSEL_NAME_S ");
			sql.add(" ,vessel.VESSEL_NAME_K ");
			if (isUseShortName) {
				sql.add(" ,vessel.VESSEL_SHORT_NAME ");
			}
			sql.add(" ,vessel.OWNR_CODE ");
			sql.add(" ,vessel.IMO_NO ");
			sql.add(" ,vessel.OWNR_SHIP_CODE ");
			sql.add(" ,ownrship.CODE_NAME AS OWNR_SHIP_NAME ");
			sql.add(" ,vessel.SHIP_TYPE ");
			sql.add(" ,vessel.SHIP_FORM ");
			sql.add(" ,vessel.CALL_SIGN ");
			sql.add(" ,vessel.STR_DATE ");
			sql.add(" ,vessel.END_DATE ");
			sql.add(" ,vessel.EST_FLG ");
			sql.add(" ,vessel.RELET_FLG ");
			sql.add(" ,vessel.SUSPENDED_FLG ");
			sql.add("FROM CM_VESSEL_MST vessel");
			sql.add(" LEFT OUTER JOIN OP_CODE_MST ownrship ON ownrship.KAI_CODE = ? ", getCompanyCode());
			sql.add("  	                          AND         ownrship.CODE = vessel.OWNR_SHIP_CODE ");
			sql.add("  	                          AND         ownrship.CODE_DIV = ? ", ownrship);
			sql.add("                             AND         NVL(ownrship.LCL_KBN,0) = 0 ");
			sql.add("WHERE 1 = 1 ");
			setupWhere(condition, sql);

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (vessel.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR vessel.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add("ORDER BY ");
			sql.add("  vessel.KAI_CODE ");
			sql.add(" ,vessel.VESSEL_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				Vessel bean = createVessel();
				bean.setCompanyCode(rs.getString("KAI_CODE"));
				bean.setCode(rs.getString("VESSEL_CODE"));
				bean.setName(rs.getString("VESSEL_NAME"));
				bean.setNames(rs.getString("VESSEL_NAME_S"));
				bean.setNamek(rs.getString("VESSEL_NAME_K"));
				if (isUseShortName) {
					bean.setSHORT_NAME(rs.getString("VESSEL_SHORT_NAME"));
				}
				bean.setOWNR_CODE(rs.getString("OWNR_CODE")); // OWNR_CODE
				bean.setIMO_NO(rs.getString("IMO_NO")); // IMO_NO
				bean.setOWNR_SHIP_CODE(rs.getString("OWNR_SHIP_CODE")); // OWNERSHIP_CODE
				bean.setOWNR_SHIP_NAME(rs.getString("OWNR_SHIP_NAME")); // OWNR_SHIP_NAME
				bean.setSHIP_TYPE(rs.getString("SHIP_TYPE")); // VESSEL_TYPE
				bean.setSHIP_FORM(rs.getString("SHIP_FORM")); // VESSEL_SIZE
				bean.setCALL_SIGN(rs.getString("CALL_SIGN")); // CALL_SIGN
				bean.setDateFrom(rs.getDate("STR_DATE"));
				bean.setDateTo(rs.getDate("END_DATE"));
				bean.setSTR_DATE(rs.getTimestamp("STR_DATE"));
				bean.setEND_DATE(rs.getTimestamp("END_DATE"));
				bean.setEST_FLG(rs.getInt("EST_FLG")); // ESTIMATE ONLY
				bean.setRELET_FLG(rs.getInt("RELET_FLG")); // RELET ONLY
				bean.setSUSPENDED_FLG(rs.getInt("SUSPENDED_FLG")); // SUSPENDED VESSEL FLG

				list.add(bean);
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
	 * �w������ɊY������Vessel����Ԃ� �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Vessel
	 * @throws TException
	 */
	public List<Vessel> getRef(VesselSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<Vessel> list = new ArrayList<Vessel>();
		boolean isUseBM = isUseBM();

		try {

			VCreator sql = getSQL(condition, isUseBM);

			sql.add(" ORDER BY  ");
			sql.add("   vessel.VESSEL_CODE ");
			sql.add("  ,CASE WHEN vessel.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,vessel.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				Vessel bean = mapping(rs, isUseBM);

				if (Util.isNullOrEmpty(currentCode)) {
					// ������
					currentCode = bean.getCode();
				} else if (Util.equals(currentCode, bean.getCode())) {
					// �R�[�h�������ꍇ�ɏ����s�v
					continue;
				}

				// �R�[�h�ޔ�
				currentCode = bean.getCode();

				// �߂�l���X�g����
				list.add(bean);
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
	 * @param condition
	 * @param isUseBM
	 * @return SQL
	 */
	protected VCreator getSQL(VesselSearchCondition condition, boolean isUseBM) {

		VCreator sql = new VCreator();

		sql.add(" SELECT ");
		sql.add("  vessel.* ");

		if (!isNotUseOP) {
			sql.add(" ,ownrship.CODE_NAME AS OWNR_SHIP_NAME ");
			sql.add(" ,vsltype.CODE_NAME  AS VESSEL_TYPE_NAME ");
			sql.add(" ,vslsize.CODE_NAME  AS VESSEL_SIZE_NAME ");
			sql.add(" ,tri.TRI_NAME       AS TRI_NAME ");
			sql.add(" ,usr.USR_NAME       AS FLEET_USR_NAME ");
			sql.add(" ,usr.USR_NAME_S     AS FLEET_USR_NAME_S ");
			sql.add(" ,dpt.DEP_NAME       AS DEPT_NAME ");
			sql.add(" ,dpt.DEP_NAME_S     AS DEPT_NAME_S ");
		}

		if (isUseBM) {
			sql.add(" ,bmvessel.STOCK_DEP_CODE ");
			sql.add(" ,bmn1.DEP_NAME_S STOCK_DEP_NAME");
			sql.add(" ,bmvessel.BUNKER_DEP_CODE ");
			sql.add(" ,bmn2.DEP_NAME_S BUNKER_DEP_NAME");
		}
		if (condition.isShipBuild()) {
			sql.add("  ,build.CONTRACT_TYPE");
		} else {
			sql.add("  ,NULL CONTRACT_TYPE");
		}
		sql.add("FROM CM_VESSEL_MST vessel");
		if (condition.isShipBuild()) {
			sql.add(" INNER JOIN OW_SHIPBUILD_HDR build ON build.KAI_CODE = vessel.KAI_CODE ");
			sql.add("                             AND      build.VESSEL_CODE = vessel.VESSEL_CODE ");
		}
		if (isUseBM) {
			sql.add(" LEFT OUTER JOIN BM_VESSEL_MST bmvessel ON bmvessel.KAI_CODE = ? ", getCompanyCode());
			sql.add("                               AND         bmvessel.VESSEL_CODE = vessel.VESSEL_CODE");
			sql.add(" LEFT OUTER JOIN  BMN_MST bmn1 ON bmn1.KAI_CODE = ? ", getCompanyCode());
			sql.add("                          AND     bmn1.DEP_CODE = bmvessel.STOCK_DEP_CODE");
			sql.add(" LEFT OUTER JOIN  BMN_MST bmn2 ON bmn2.KAI_CODE = ? ", getCompanyCode());
			sql.add("                          AND     bmn2.DEP_CODE = bmvessel.BUNKER_DEP_CODE");
		}

		if (!isNotUseOP) {
			String ownrship = OPCodeDivision.OWRSHIP.toString();
			String vsltype = OPCodeDivision.VSL_TYPE.toString();
			String vslsize = OPCodeDivision.VSL_SIZE.toString();

			sql.add(" LEFT OUTER JOIN OP_CODE_MST ownrship ON ownrship.KAI_CODE = ? ", getCompanyCode());
			sql.add("  	                          AND         ownrship.CODE = vessel.OWNR_SHIP_CODE ");
			sql.add("  	                          AND         ownrship.CODE_DIV = ? ", ownrship);
			sql.add("                             AND         NVL(ownrship.LCL_KBN,0) = 0 ");
			sql.add(" LEFT OUTER JOIN OP_CODE_MST vsltype ON vsltype.KAI_CODE = ? ", getCompanyCode());
			sql.add("  	                          AND        vsltype.CODE = vessel.SHIP_TYPE ");
			sql.add("  	                          AND        vsltype.CODE_DIV = ? ", vsltype);
			sql.add("                             AND        NVL(vsltype.LCL_KBN,0) = 0 ");
			sql.add(" LEFT OUTER JOIN OP_CODE_MST vslsize ON vslsize.KAI_CODE = ? ", getCompanyCode());
			sql.add("  	                          AND        vslsize.CODE = vessel.SHIP_FORM ");
			sql.add("  	                          AND        vslsize.CODE_DIV = ? ", vslsize);
			sql.add("                             AND        NVL(vslsize.LCL_KBN,0) = 0 ");
			sql.add(" LEFT OUTER JOIN TRI_MST tri ON tri.KAI_CODE = ? ", getCompanyCode());
			sql.add(" 	                      AND    tri.TRI_CODE = vessel.OWNR_CODE ");
			sql.add(" LEFT OUTER JOIN USR_MST usr ON usr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" 	                      AND    usr.USR_CODE = vessel.FLEET_USR_CODE ");
			sql.add(" LEFT OUTER JOIN BMN_MST dpt ON dpt.KAI_CODE = ? ", getCompanyCode());
			sql.add(" 	                      AND    dpt.DEP_CODE = vessel.DEPT_CODE ");
		}

		sql.add("WHERE 1 = 1 ");
		setupWhere(condition, sql);

		// �R���Ǘ��p
		if (isUseBM && condition.isForBM()) {
			sql.add(" AND   bmvessel.KAI_CODE IS NOT NULL ");
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (vessel.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR vessel.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

		return sql;
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(VesselSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   CM_VESSEL_MST vessel ");
			sql.add(" WHERE  vessel.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (vessel.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR vessel.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR vessel.INP_DATE IS NULL AND vessel.UPD_DATE IS NULL) ");
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
	 * @return �f�t�H���g������
	 * @throws TException
	 */
	protected List<CM_BNKR_TYPE_MST> getDefaultBunkerTypeList() throws TException {
		BunkerTypeManager btm = get(BunkerTypeManager.class);
		BunkerTypeSearchCondition btmCondition = new BunkerTypeSearchCondition();
		btmCondition.setKAI_CODE(getCompanyCode());
		return btm.get(btmCondition);
	}

	/**
	 * Gets the hold consumption list.
	 * 
	 * @param conn db conneciton
	 * @param condition the condition
	 * @return the list
	 * @throws Exception
	 */
	public List<VesselHold> getHoldList(Connection conn, VesselSearchCondition condition) throws Exception {

		String tableName = "CM_VESSEL_HOLD_MST";

		VCreator where = new VCreator();
		where.add(" WHERE t.KAI_CODE = ? ", condition.getCompanyCode());

		// �D�w��
		if (!Util.isNullOrEmpty(condition.getCode())) {
			where.add(" AND   t.VESSEL_CODE = ? ", condition.getCode());
		}
		// �D���X�g�w��
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			where.add(" AND ( 1 = 2 ");
			for (String code : condition.getCodeList()) {
				where.add(" OR t.VESSEL_CODE = ? ", code);
			}
			where.add(" ) ");
		}

		VCreator sql = new VCreator();
		sql.add(" SELECT  t.* ");
		sql.add(" FROM    " + tableName + " t ");
		sql.add(where);
		sql.add(" ORDER BY ");
		sql.add("    t.KAI_CODE ");
		sql.add("   ,t.VESSEL_CODE ");
		sql.add("   ,t.HOLD_NO ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		List<VesselHold> list = new ArrayList<VesselHold>();

		while (rs.next()) {
			VesselHold bean = mappingVesselHold(rs);
			list.add(bean);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;
	}

	/**
	 * Mapping vessel hold comsumption.
	 * 
	 * @param rs the rs
	 * @return bean
	 * @throws Exception
	 */
	public VesselHold mappingVesselHold(ResultSet rs) throws Exception {
		VesselHold bean = createVesselHold();
		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setVESSEL_CODE(rs.getString("VESSEL_CODE")); // VESSEL_CODE
		bean.setHOLD_NO(rs.getString("HOLD_NO"));
		bean.setDISP_ODR(rs.getInt("DISP_ODR"));
		bean.setHOLD_NAME(rs.getString("HOLD_NAME"));
		bean.setCENTER_GRN(rs.getBigDecimal("CENTER_GRN"));
		bean.setCENTER_BALE(rs.getBigDecimal("CENTER_BALE"));
		bean.setLEN_BRE(rs.getString("LEN_BRE"));
		bean.setTYPE_SET(rs.getString("TYPE_SET"));
		bean.setSTR_DATE(rs.getTimestamp("STR_DATE")); // �J�n�N����
		bean.setEND_DATE(rs.getTimestamp("END_DATE")); // �I���N����
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // �o�^�N����
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // �X�V�N����
		bean.setPRG_ID(rs.getString("PRG_ID")); // �v���O����ID
		bean.setUSR_ID(rs.getString("USR_ID")); // ���[�U�[ID
		return bean;
	}

	/**
	 * Create new {@link VesselHold}
	 * 
	 * @return {@link VesselHold}
	 */
	protected VesselHold createVesselHold() {
		return new VesselHold();
	}

	/**
	 * �D��������̎擾
	 * 
	 * @param conn db conneciton
	 * @param condition the condition
	 * @return the list
	 * @throws Exception
	 */
	public List<VesselBunkerType> getBunkerTypeList(Connection conn, VesselSearchCondition condition) throws Exception {

		String tableName = "CM_VESSEL_BNKR_TYPE_MST";

		VCreator where = new VCreator();
		where.add(" WHERE t.KAI_CODE = ? ", condition.getCompanyCode());

		// �D�w��
		if (!Util.isNullOrEmpty(condition.getCode())) {
			where.add(" AND   t.VESSEL_CODE = ? ", condition.getCode());
		}
		// �D���X�g�w��
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			where.add(" AND ( 1 = 2 ");
			for (String code : condition.getCodeList()) {
				where.add(" OR t.VESSEL_CODE = ? ", code);
			}
			where.add(" ) ");
		}

		String grade = OPCodeDivision.BNKR_SPLY_GRADE.toString();

		VCreator sql = new VCreator();
		sql.add(" SELECT  t.* ");
		sql.add("        ,grade.CODE_NAME AS GRADE_NAME ");
		sql.add(" FROM    " + tableName + " t ");

		sql.add(" LEFT OUTER JOIN OP_CODE_MST grade ON grade.KAI_CODE = ? ", getCompanyCode());
		sql.add("  	                          AND      grade.CODE = t.GRADE_CODE ");
		sql.add("  	                          AND      grade.CODE_DIV = ? ", grade);
		sql.add("                             AND      NVL(grade.LCL_KBN,0) = 0 ");

		sql.add(where);
		sql.add(" ORDER BY ");
		sql.add("    t.KAI_CODE ");
		sql.add("   ,t.VESSEL_CODE ");
		sql.add("   ,t.DISP_ODR ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		List<VesselBunkerType> list = new ArrayList<VesselBunkerType>();

		while (rs.next()) {
			VesselBunkerType bean = mappingBunkerType(rs);
			list.add(bean);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;
	}

	/**
	 * �}�b�s���O
	 * 
	 * @param rs
	 * @return ����
	 * @throws Exception
	 */
	protected VesselBunkerType mappingBunkerType(ResultSet rs) throws Exception {

		VesselBunkerType bean = createBunkerType();

		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setVESSEL_CODE(rs.getString("VESSEL_CODE"));
		bean.setBNKR_TYPE_CODE(rs.getString("BNKR_TYPE_CODE")); // BUNKER�^�C�v�R�[�h
		bean.setGRADE_CODE(rs.getString("GRADE_CODE")); // GRADE_CODE
		bean.setGRADE_NAME(rs.getString("GRADE_NAME")); // GRADE_NAME
		bean.setENG_PRI_NOR(rs.getInt("ENG_PRI_NOR"));
		bean.setAUX_PRI_NOR(rs.getInt("AUX_PRI_NOR"));
		bean.setENG_PRI_ECA(rs.getInt("ENG_PRI_ECA"));
		bean.setAUX_PRI_ECA(rs.getInt("AUX_PRI_ECA"));
		bean.setSC_ENG_PRI_NOR(rs.getInt("SC_ENG_PRI_NOR"));
		bean.setSC_AUX_PRI_NOR(rs.getInt("SC_AUX_PRI_NOR"));
		bean.setSC_ENG_PRI_ECA(rs.getInt("SC_ENG_PRI_ECA"));
		bean.setSC_AUX_PRI_ECA(rs.getInt("SC_AUX_PRI_ECA"));
		bean.setDISP_ODR(rs.getInt("DISP_ODR")); // ORDER
		bean.setSTR_DATE(rs.getDate("STR_DATE")); // �J�n�N����
		bean.setEND_DATE(rs.getDate("END_DATE")); // �I���N����
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // �o�^�N����
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // �X�V�N����
		bean.setPRG_ID(rs.getString("PRG_ID")); // �v���O����ID
		bean.setUSR_ID(rs.getString("USR_ID")); // ���[�U�[ID
		// �D���A���p������킪�w��ł��邩
		boolean isUseBnkrUsing = ServerConfig.isFlagOn("trans.op.use.vessel.bunker.using." + getCompanyCode());
		if (isUseBnkrUsing) {
			bean.setUSE_FLG(rs.getInt("USE_FLG")); // �g�p�t���O
		}

		return bean;
	}

	/**
	 * @return ����
	 */
	protected VesselBunkerType createBunkerType() {
		return new VesselBunkerType();
	}

	/**
	 * ���אU�蕪��
	 * 
	 * @param list
	 * @param speedList
	 * @param portConsList
	 * @param seaConsList
	 * @param holdList
	 * @param bunkerTypeList �D������ݒ�
	 * @param defaultBunkerTypeList �S�̃f�t�H���g�ݒ�
	 */
	protected void setupDetailList(List<Vessel> list, List<VesselSpeed> speedList, List<VesselPortCons> portConsList,
		List<VesselSeaCons> seaConsList, List<VesselHold> holdList, List<VesselBunkerType> bunkerTypeList,
		List<CM_BNKR_TYPE_MST> defaultBunkerTypeList) {

		// �}�b�v�ɐU�蕪��
		Map<String, List<VesselSpeed>> speedMap = new HashMap<String, List<VesselSpeed>>();
		if (speedList != null) {
			for (VesselSpeed bean : speedList) {
				String key = bean.getKAI_CODE() + "<>" + bean.getVESSEL_CODE();
				List<VesselSpeed> subList = speedMap.get(key);
				if (subList == null) {
					subList = new ArrayList<VesselSpeed>();
					speedMap.put(key, subList);
				}

				subList.add(bean);
			}
		}
		Map<String, List<VesselPortCons>> portConsMap = new HashMap<String, List<VesselPortCons>>();
		if (portConsList != null) {
			for (VesselPortCons bean : portConsList) {
				String key = bean.getKAI_CODE() + "<>" + bean.getVESSEL_CODE();
				List<VesselPortCons> subList = portConsMap.get(key);
				if (subList == null) {
					subList = new ArrayList<VesselPortCons>();
					portConsMap.put(key, subList);
				}

				subList.add(bean);
			}
		}
		Map<String, List<VesselSeaCons>> seaConsMap = new HashMap<String, List<VesselSeaCons>>();
		Map<String, List<VesselSeaCons>> auxMap = new HashMap<String, List<VesselSeaCons>>();
		if (seaConsList != null) {
			for (VesselSeaCons bean : seaConsList) {
				if (bean.getUSING_PU_KBN() == 1) {
					// 1:AUX(�⏕)
					String key = bean.getKAI_CODE() + "<>" + bean.getVESSEL_CODE();
					List<VesselSeaCons> auxList = auxMap.get(key);
					if (auxList == null) {
						auxList = new ArrayList<VesselSeaCons>();
						auxMap.put(key, auxList);
					}

					auxList.add(bean);
				} else {
					// MAIN ENGINE

					String spdTypeCode = Util.avoidNull(bean.getSPD_TYPE_CODE());
					String key = bean.getKAI_CODE() + "<>" + bean.getVESSEL_CODE() + "<>" + spdTypeCode;
					List<VesselSeaCons> subList = seaConsMap.get(key);
					if (subList == null) {
						subList = new ArrayList<VesselSeaCons>();
						seaConsMap.put(key, subList);
					}

					subList.add(bean);
				}
			}
		}

		Map<String, List<VesselHold>> holdMap = new HashMap<String, List<VesselHold>>();
		if (holdList != null) {
			for (VesselHold bean : holdList) {
				String key = bean.getKAI_CODE() + "<>" + bean.getVESSEL_CODE();
				List<VesselHold> subList = holdMap.get(key);
				if (subList == null) {
					subList = new ArrayList<VesselHold>();
					holdMap.put(key, subList);
				}

				subList.add(bean);
			}
		}

		Map<String, List<CM_BNKR_TYPE_MST>> bunkerTypeMap = new HashMap<String, List<CM_BNKR_TYPE_MST>>();
		if (bunkerTypeList != null) {
			for (VesselBunkerType bean : bunkerTypeList) {
				String key = bean.getKAI_CODE() + "<>" + bean.getVESSEL_CODE();
				List<CM_BNKR_TYPE_MST> subList = bunkerTypeMap.get(key);
				if (subList == null) {
					subList = new ArrayList<CM_BNKR_TYPE_MST>();
					bunkerTypeMap.put(key, subList);
				}

				subList.add(bean);
			}
		}

		for (Vessel bean : list) {
			String key = bean.getCompanyCode() + "<>" + bean.getCode();

			// SPEED ���
			bean.setSpeedList(speedMap.get(key));

			// PORT CONS
			bean.setPortConsList(portConsMap.get(key));

			// HOLD
			bean.setHoldConsList(holdMap.get(key));

			// SEA CONS
			if (bean.getSpeedList() != null) {
				for (VesselSpeed speed : bean.getSpeedList()) {
					String spdTypeCode = Util.avoidNull(speed.getSPD_TYPE_CODE());
					String speedKey = speed.getKAI_CODE() + "<>" + speed.getVESSEL_CODE() + "<>" + spdTypeCode;
					speed.setSeaConsList(seaConsMap.get(speedKey));
				}
			}

			// AUX SEA CONS
			{
				bean.setAuxSeaConsList(auxMap.get(key));
			}

			List<CM_BNKR_TYPE_MST> filterList = bunkerTypeMap.get(key);

			// �D������ݒ�
			if (BooleanUtil.toBoolean(bean.getUSE_DEFAULT_BNKR_TYPE())) {
				// �f�t�H���g���g�키�A�܂��͌ʐݒ�Ȃ��ꍇ�Ƀf�t�H���g���Z�b�g

				if (filterList == null || filterList.isEmpty()) {
					filterList = defaultBunkerTypeList;

				} else if (defaultBunkerTypeList != null && !defaultBunkerTypeList.isEmpty()) {
					// GRADE���}�[�W����
					for (CM_BNKR_TYPE_MST bnkrType : defaultBunkerTypeList) {
						String bunkerTypeCode = Util.avoidNull(bnkrType.getBNKR_TYPE_CODE());

						for (CM_BNKR_TYPE_MST vslBnkrType : filterList) {
							String vslBunkerTypeCode = Util.avoidNull(vslBnkrType.getBNKR_TYPE_CODE());

							if (bunkerTypeCode.equals(vslBunkerTypeCode)) {
								bnkrType.setGRADE_CODE(vslBnkrType.getGRADE_CODE());
								bnkrType.setGRADE_NAME(vslBnkrType.getGRADE_NAME());
								break;
							}
						}
					}
				}
			}

			bean.setBunkerTypeList(filterList);
		}

	}

	/**
	 * WHERE�����ǉ�
	 * 
	 * @param condition
	 * @param sql
	 */
	protected void setupWhere(VesselSearchCondition condition, SQLCreator sql) {
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("  AND vessel.KAI_CODE = ? ", condition.getCompanyCode());
		}
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("  AND vessel.VESSEL_CODE = ? ", condition.getCode());
		}
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("  AND vessel.VESSEL_CODE >= ?", condition.getCodeFrom());
		}
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("  AND vessel.VESSEL_CODE <= ?", condition.getCodeTo());
		}
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("  AND vessel.VESSEL_CODE ? ", condition.getCodeLike());
		}
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.addNLikeAmbi("  AND vessel.VESSEL_NAME_S ? ", condition.getNamesLike());
		}
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi("  AND vessel.VESSEL_NAME_K ? ", condition.getNamekLike());
		}
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			sql.add("  AND vessel.VESSEL_CODE IN ? ", condition.getCodeList());
		}
		if (!Util.isNullOrEmpty(condition.getFleetUsrCode())) {
			// Fleet Movement ���[�U�[�R�[�h
			sql.add("  AND vessel.FLEET_USR_CODE = ?", condition.getFleetUsrCode());
		}

		if (!Util.isNullOrEmpty(condition.getShipOwnerCode())) {
			sql.add(" AND OWNR_SHIP_CODE =  ? ", condition.getShipOwnerCode());
		}

		if (!Util.isNullOrEmpty(condition.getShipTypeCode())) {
			sql.add(" AND SHIP_TYPE=  ? ", condition.getShipTypeCode());
		}

		if (!Util.isNullOrEmpty(condition.getShipFormCode())) {
			sql.add(" AND SHIP_FORM=  ? ", condition.getShipFormCode());
		}

		if (Util.isNullOrEmpty(condition.getCode()) && !condition.isIncludeSuspended()) {
			sql.add(" AND (SUSPENDED_FLG IS NULL OR SUSPENDED_FLG <> 1) ");
		}

		if (!Util.isNullOrEmpty(condition.getOwnerNameLike())) {
			sql.add(" AND vessel.OWNR_CODE IN  ");
			sql.add("         (");
			sql.add(" SELECT tri.TRI_CODE  ");
			sql.add(" FROM TRI_MST tri ");
			sql.add(" WHERE ");
			sql.add(" tri.KAI_CODE = ? ", condition.getCompanyCode());
			sql.addLikeAmbi("  AND tri.TRI_NAME ? ", condition.getOwnerNameLike());

			sql.add("         )");
		}

		// �L������
		if (condition.getValidTerm() != null) {
			sql.add(" AND	vessel.STR_DATE <= ? ", condition.getValidTerm());
			sql.add(" AND	vessel.END_DATE >= ? ", condition.getValidTerm());
		}

		// CALL SIGN
		if (!Util.isNullOrEmpty(condition.getCallsign())) {
			sql.add(" AND   TRIM(vessel.CALL_SIGN) = ? ", Util.avoidNull(condition.getCallsign()));
		}

		// RELET �܂�
		if (Util.isNullOrEmpty(condition.getCode()) && !condition.isIncludeRelet()) {
			sql.add(" AND (RELET_FLG IS NULL OR RELET_FLG <> 1) ");
		}

	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @param isUseBM
	 * @return entity
	 * @throws SQLException
	 */
	protected Vessel mapping(ResultSet rs, boolean isUseBM) throws SQLException {
		Vessel bean = createVessel();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("VESSEL_CODE"));
		bean.setName(rs.getString("VESSEL_NAME"));
		bean.setNames(rs.getString("VESSEL_NAME_S"));
		bean.setNamek(rs.getString("VESSEL_NAME_K"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setSTR_DATE(rs.getTimestamp("STR_DATE"));
		bean.setEND_DATE(rs.getTimestamp("END_DATE"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));

		if (isUseBM) {
			bean.setStockCode(rs.getString("STOCK_DEP_CODE"));
			bean.setStockName(rs.getString("STOCK_DEP_NAME"));
			bean.setFuelCode(rs.getString("BUNKER_DEP_CODE"));
			bean.setFuelName(rs.getString("BUNKER_DEP_NAME"));
		}

		if (!isNotUseOP) {

			bean.setContractType(rs.getString("CONTRACT_TYPE"));
			bean.setOWNR_CODE(rs.getString("OWNR_CODE")); // OWNR_CODE
			bean.setOWNR_SHIP_CODE(rs.getString("OWNR_SHIP_CODE")); // OWNERSHIP_CODE
			bean.setOWNR_SHIP_NAME(rs.getString("OWNR_SHIP_NAME")); // OWNR_SHIP_NAME
			bean.setSHIP_TYPE(rs.getString("SHIP_TYPE")); // VESSEL_TYPE
			bean.setSHIP_FORM(rs.getString("SHIP_FORM")); // VESSEL_SIZE
			bean.setObjectType(rs.getInt("OBJ_TYPE"));
			bean.setCALL_SIGN(rs.getString("CALL_SIGN")); // CALL SIGN
			bean.setFAX(rs.getString("FAX")); // FAX
			bean.setE_MAIL(rs.getString("E_MAIL")); // E-MAIL
			bean.setTELEX(rs.getString("TELEX")); // TELEX
			bean.setDEPT_CODE(rs.getString("DEPT_CODE")); // DEPT_CODE
			bean.setDEPT_NAME(rs.getString("DEPT_NAME")); // DEPT_NAME
			bean.setDEPT_NAME_S(rs.getString("DEPT_NAME_S")); // DEPT_NAME_S
			bean.setCHTR_PI_NAME(rs.getString("CHTR_PI_NAME")); // CHARTERERS P&I CLUB NAME
			bean.setOWR_PI_NAME(rs.getString("OWR_PI_NAME")); // OWNERS P&I CLUB NAME
			bean.setNEXT_DRY_DOCK_DATE(rs.getTimestamp("NEXT_DRY_DOCK_DATE")); // NEXT DRY DOCK DATE
			bean.setDWT_SMR_MT(rs.getBigDecimal("DWT_SMR_MT"));
			bean.setDWT_SMR_LT(rs.getBigDecimal("DWT_SMR_LT"));
			bean.setDWT_WTR_MT(rs.getBigDecimal("DWT_WTR_MT"));
			bean.setDWT_WTR_LT(rs.getBigDecimal("DWT_WTR_LT"));
			bean.setDWT_TRP_MT(rs.getBigDecimal("DWT_TRP_MT"));
			bean.setDWT_TRP_LT(rs.getBigDecimal("DWT_TRP_LT"));
			bean.setDRAFT_SMR_MT(rs.getBigDecimal("DRAFT_SMR_MT"));
			bean.setDRAFT_SMR_LT(rs.getBigDecimal("DRAFT_SMR_LT"));
			bean.setDRAFT_WTR_MT(rs.getBigDecimal("DRAFT_WTR_MT"));
			bean.setDRAFT_WTR_LT(rs.getBigDecimal("DRAFT_WTR_LT"));
			bean.setDRAFT_TRP_MT(rs.getBigDecimal("DRAFT_TRP_MT"));
			bean.setDRAFT_TRP_LT(rs.getBigDecimal("DRAFT_TRP_LT"));
			bean.setLIGHT_WEIGHT_MT(rs.getBigDecimal("LIGHT_WEIGHT_MT"));
			bean.setLIGHT_WEIGHT_LT(rs.getBigDecimal("LIGHT_WEIGHT_LT"));
			bean.setLIGHT_WEIGHT_TRP(rs.getBigDecimal("LIGHT_WEIGHT_TRP"));
			bean.setBUILDER(rs.getString("BUILDER")); // BUILDER
			bean.setLAUNCH_DATE(rs.getTimestamp("LAUNCH_DATE"));
			bean.setBUILD_DATE(rs.getTimestamp("BUILD_DATE"));
			bean.setCLASS(rs.getString("CLASS")); // CLASS
			bean.setFLAG(rs.getString("FLAG")); // FLAG
			bean.setIMO_NO(rs.getString("IMO_NO")); // IMO_NO
			bean.setOFCL_NO(rs.getString("OFCL_NO")); // OFCL_NO
			bean.setCRN_CAP(rs.getBigDecimal("CRN_CAP"));
			bean.setE_MAIL(rs.getString("E_MAIL"));
			bean.setPHONE_1(rs.getString("PHONE_1"));
			bean.setPHONE_2(rs.getString("PHONE_2"));
			bean.setLOA_M(rs.getBigDecimal("LOA_M"));
			bean.setLOA_FT(rs.getBigDecimal("LOA_FT"));
			bean.setLREG_M(rs.getBigDecimal("LREG_M"));
			bean.setLREG_FT(rs.getBigDecimal("LREG_FT"));
			bean.setLPP_M(rs.getBigDecimal("LPP_M"));
			bean.setLPP_FT(rs.getBigDecimal("LPP_FT"));
			bean.setBEAM_M(rs.getBigDecimal("BEAM_M"));
			bean.setBEAM_FT(rs.getBigDecimal("BEAM_FT"));
			bean.setDEPTH_M(rs.getBigDecimal("DEPTH_M"));
			bean.setDEPTH_FT(rs.getBigDecimal("DEPTH_FT"));
			bean.setAIR_DRAFT_M(rs.getBigDecimal("AIR_DRAFT_M"));
			bean.setAIR_DRAFT_FT(rs.getBigDecimal("AIR_DRAFT_FT"));
			bean.setGROSS(rs.getBigDecimal("GROSS"));
			bean.setNET(rs.getBigDecimal("NET"));
			bean.setPANAMA_GROSS(rs.getBigDecimal("PANAMA_GROSS"));
			bean.setPANAMA_NET(rs.getBigDecimal("PANAMA_NET"));
			bean.setSUEZ_GROSS(rs.getBigDecimal("SUEZ_GROSS"));
			bean.setSUEZ_NET(rs.getBigDecimal("SUEZ_NET"));
			bean.setEST_FLG(rs.getInt("EST_FLG")); // ESTIMATE ONLY
			bean.setRELET_FLG(rs.getInt("RELET_FLG")); // RELET ONLY
			bean.setSUSPENDED_FLG(rs.getInt("SUSPENDED_FLG")); // SUSPENDED VESSEL FLG
			bean.setSCRUBBER_KBN(rs.getInt("SCRUBBER_KBN")); // SCRUBBER_KBN
			bean.setUSE_DEFAULT_BNKR_TYPE(rs.getInt("USE_DEFAULT_BNKR_TYPE")); // USE_DEFAULT_BNKR_TYPE
			bean.setREMARKS(rs.getString("REMARKS")); // REMARKS
			bean.setFLEET_USR_CODE(rs.getString("FLEET_USR_CODE")); // FLEET MV USER
			bean.setFLEET_USR_NAME(rs.getString("FLEET_USR_NAME"));
			bean.setFLEET_USR_NAME_S(rs.getString("FLEET_USR_NAME_S"));
			bean.setENGN_MAKER(rs.getString("ENGN_MAKER"));
			bean.setENGN_TYPE(rs.getString("ENGN_TYPE"));
			bean.setNOR_BHP(rs.getString("NOR_BHP"));
			bean.setNOR_NW(rs.getString("NOR_NW"));
			bean.setNOR_RPM(rs.getString("NOR_RPM"));
			bean.setMCR_BHP(rs.getString("MCR_BHP"));
			bean.setMCR_NW(rs.getString("MCR_NW"));
			bean.setMCR_RPM(rs.getString("MCR_RPM"));
			bean.setYARD_NO(rs.getString("YARD_NO"));
			bean.setMNG_CMP(rs.getString("MNG_CMP"));
			bean.setREG_OWN(rs.getString("REG_OWN"));
			bean.setVESSEL_TYPE_NAME(rs.getString("VESSEL_TYPE_NAME"));
			bean.setVESSEL_SIZE_NAME(rs.getString("VESSEL_SIZE_NAME"));
			bean.setTRI_NAME(rs.getString("TRI_NAME"));
		}
		if (isUseShortName) {
			bean.setSHORT_NAME(rs.getString("VESSEL_SHORT_NAME"));
		}
		return bean;
	}

	/**
	 * @return �D
	 */
	protected Vessel createVessel() {
		return new Vessel();
	}

	/**
	 * �w��R�[�h�ɊY������Vessel����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param vesselCode Vessel�R�[�h
	 * @return �w��R�[�h�ɊY������Vessel���
	 * @throws TException
	 */
	public Vessel get(String companyCode, String vesselCode) throws TException {

		VesselSearchCondition condition = new VesselSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(vesselCode);
		condition.setIncludeDetail(true);

		List<Vessel> list = get(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * Get an object vessel
	 * 
	 * @param oldBean
	 * @param bean
	 * @return Vessel
	 * @throws TException
	 */
	@Override
	public Vessel entry(Vessel bean, Vessel oldBean) throws TException {

		// New or copy
		if (oldBean == null) {
			// �V�K/�R�s�[�Ŋ������̔�"ON"�̏ꍇ�͎����̔Ԃ���Vessel Code��bean�ɐݒ肷��
			if (bean.isAutoNumber()) {
				setupAutoVesselCode(bean);
			}
			bean.setINP_DATE(getNow());
			bean.setUPD_DATE(null);
			// Modify
		} else {
			bean.setINP_DATE(oldBean.getINP_DATE());
			bean.setUPD_DATE(getNow());
		}

		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		if (oldBean == null) {
			insert(bean);
		} else {
			modify(bean);
		}

		return get(bean.getCompanyCode(), bean.getCode());
	}

	/**
	 * Vessel��o�^����B
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void insert(Vessel bean) throws TException {
		Connection conn = null;
		bean.setINP_DATE(getNow());

		try {
			boolean isUseBM = isUseBM();
			conn = DBUtil.getConnection();

			String tableName = "CM_VESSEL_MST";

			VCreator sql = new VCreator();
			sql.add(" INSERT INTO " + tableName + " ( ");
			sql.add("    KAI_CODE ");
			sql.add("   ,VESSEL_CODE ");
			sql.add("   ,VESSEL_NAME ");
			sql.add("   ,VESSEL_NAME_S ");
			sql.add("   ,VESSEL_NAME_K ");
			if (isUseShortName) {
				sql.add("   ,VESSEL_SHORT_NAME ");
			}
			sql.add("   ,OWNR_CODE ");
			sql.add("   ,OWNR_SHIP_CODE ");
			sql.add("   ,SHIP_TYPE ");
			sql.add("   ,SHIP_FORM ");
			sql.add("   ,OBJ_TYPE ");
			sql.add("   ,CALL_SIGN ");
			sql.add("   ,FLAG ");
			sql.add("   ,CLASS ");
			sql.add("   ,IMO_NO ");
			sql.add("   ,OFCL_NO ");
			sql.add("   ,CRN_CAP ");
			sql.add("   ,E_MAIL ");
			sql.add("   ,PHONE_1 ");
			sql.add("   ,PHONE_2 ");
			sql.add("   ,FAX ");
			sql.add("   ,TELEX ");
			sql.add("   ,DEPT_CODE ");
			sql.add("   ,CHTR_PI_NAME ");
			sql.add("   ,OWR_PI_NAME ");
			sql.add("   ,NEXT_DRY_DOCK_DATE ");
			sql.add("   ,EST_FLG ");
			sql.add("   ,SUSPENDED_FLG ");
			sql.add("   ,RELET_FLG ");
			sql.add("   ,SCRUBBER_KBN ");
			sql.add("   ,USE_DEFAULT_BNKR_TYPE ");
			sql.add("   ,DWT_SMR_MT ");
			sql.add("   ,DWT_TRP_MT ");
			sql.add("   ,DWT_WTR_MT ");
			sql.add("   ,DWT_SMR_LT ");
			sql.add("   ,DWT_TRP_LT ");
			sql.add("   ,DWT_WTR_LT ");
			sql.add("   ,DRAFT_SMR_MT ");
			sql.add("   ,DRAFT_TRP_MT ");
			sql.add("   ,DRAFT_WTR_MT ");
			sql.add("   ,DRAFT_SMR_LT ");
			sql.add("   ,DRAFT_TRP_LT ");
			sql.add("   ,DRAFT_WTR_LT ");
			sql.add("   ,LIGHT_WEIGHT_MT ");
			sql.add("   ,LIGHT_WEIGHT_LT ");
			sql.add("   ,LIGHT_WEIGHT_TRP ");
			sql.add("   ,GROSS ");
			sql.add("   ,NET ");
			sql.add("   ,PANAMA_GROSS ");
			sql.add("   ,PANAMA_NET ");
			sql.add("   ,SUEZ_GROSS ");
			sql.add("   ,SUEZ_NET ");
			sql.add("   ,LOA_M ");
			sql.add("   ,LOA_FT ");
			sql.add("   ,LREG_M ");
			sql.add("   ,LREG_FT ");
			sql.add("   ,LPP_M ");
			sql.add("   ,LPP_FT ");
			sql.add("   ,BEAM_M ");
			sql.add("   ,BEAM_FT");
			sql.add("   ,DEPTH_M ");
			sql.add("   ,DEPTH_FT ");
			sql.add("   ,AIR_DRAFT_M ");
			sql.add("   ,AIR_DRAFT_FT ");
			sql.add("   ,BUILDER ");
			sql.add("   ,LAUNCH_DATE ");
			sql.add("   ,BUILD_DATE ");
			sql.add("   ,ENGN_MAKER ");
			sql.add("   ,ENGN_TYPE ");
			sql.add("   ,NOR_BHP ");
			sql.add("   ,NOR_NW ");
			sql.add("   ,NOR_RPM ");
			sql.add("   ,MCR_BHP ");
			sql.add("   ,MCR_NW ");
			sql.add("   ,MCR_RPM ");
			sql.add("   ,YARD_NO ");
			sql.add("   ,MNG_CMP ");
			sql.add("   ,REG_OWN ");
			sql.add("   ,FLEET_USR_CODE ");
			sql.add("   ,REMARKS ");
			sql.add("   ,STR_DATE ");
			sql.add("   ,END_DATE ");
			sql.add("   ,INP_DATE ");
			sql.add("   ,UPD_DATE ");
			sql.add("   ,PRG_ID ");
			sql.add("   ,USR_ID ");
			sql.add(") VALUES ( ");
			sql.add("    ? ", bean.getCompanyCode());
			sql.add("   ,? ", bean.getCode());
			sql.add("   ,? ", bean.getName());
			sql.add("   ,? ", bean.getNames());
			sql.add("   ,? ", bean.getNamek());
			if (isUseShortName) {
				sql.add("   ,? ", bean.getSHORT_NAME());
			}
			sql.add("   ,? ", bean.getOWNR_CODE());
			sql.add("   ,? ", bean.getOWNR_SHIP_CODE());
			sql.add("   ,? ", bean.getSHIP_TYPE());
			sql.add("   ,? ", bean.getSHIP_FORM());
			sql.add("   ,? ", bean.getObjectType());
			sql.add("   ,? ", bean.getCALL_SIGN());
			sql.add("   ,? ", bean.getFLAG());
			sql.add("   ,? ", bean.getCLASS());
			sql.add("   ,? ", bean.getIMO_NO());
			sql.add("   ,? ", bean.getOFCL_NO());
			sql.add("   ,? ", bean.getCRN_CAP());
			sql.add("   ,? ", bean.getE_MAIL());
			sql.add("   ,? ", bean.getPHONE_1());
			sql.add("   ,? ", bean.getPHONE_2());
			sql.add("   ,? ", bean.getFAX());
			sql.add("   ,? ", bean.getTELEX());
			sql.add("   ,? ", bean.getDEPT_CODE());
			sql.add("   ,? ", bean.getCHTR_PI_NAME());
			sql.add("   ,? ", bean.getOWR_PI_NAME());
			sql.addYMDHMS("   ,? ", bean.getNEXT_DRY_DOCK_DATE());
			sql.add("   ,? ", bean.getEST_FLG());
			sql.add("   ,? ", bean.getSUSPENDED_FLG());
			sql.add("   ,? ", bean.getRELET_FLG());
			sql.add("   ,? ", bean.getSCRUBBER_KBN());
			sql.add("   ,? ", bean.getUSE_DEFAULT_BNKR_TYPE());
			sql.add("   ,? ", bean.getDWT_SMR_MT());
			sql.add("   ,? ", bean.getDWT_TRP_MT());
			sql.add("   ,? ", bean.getDWT_WTR_MT());
			sql.add("   ,? ", bean.getDWT_SMR_LT());
			sql.add("   ,? ", bean.getDWT_TRP_LT());
			sql.add("   ,? ", bean.getDWT_WTR_LT());
			sql.add("   ,? ", bean.getDRAFT_SMR_MT());
			sql.add("   ,? ", bean.getDRAFT_TRP_MT());
			sql.add("   ,? ", bean.getDRAFT_WTR_MT());
			sql.add("   ,? ", bean.getDRAFT_SMR_LT());
			sql.add("   ,? ", bean.getDRAFT_TRP_LT());
			sql.add("   ,? ", bean.getDRAFT_WTR_LT());
			sql.add("   ,? ", bean.getLIGHT_WEIGHT_MT());
			sql.add("   ,? ", bean.getLIGHT_WEIGHT_LT());
			sql.add("   ,? ", bean.getLIGHT_WEIGHT_TRP());
			sql.add("   ,? ", bean.getGROSS());
			sql.add("   ,? ", bean.getNET());
			sql.add("   ,? ", bean.getPANAMA_GROSS());
			sql.add("   ,? ", bean.getPANAMA_NET());
			sql.add("   ,? ", bean.getSUEZ_GROSS());
			sql.add("   ,? ", bean.getSUEZ_NET());
			sql.add("   ,? ", bean.getLOA_M());
			sql.add("   ,? ", bean.getLOA_FT());
			sql.add("   ,? ", bean.getLREG_M());
			sql.add("   ,? ", bean.getLREG_FT());
			sql.add("   ,? ", bean.getLPP_M());
			sql.add("   ,? ", bean.getLPP_FT());
			sql.add("   ,? ", bean.getBEAM_M());
			sql.add("   ,? ", bean.getBEAM_FT());
			sql.add("   ,? ", bean.getDEPTH_M());
			sql.add("   ,? ", bean.getDEPTH_FT());
			sql.add("   ,? ", bean.getAIR_DRAFT_M());
			sql.add("   ,? ", bean.getAIR_DRAFT_FT());
			sql.add("    ,? ", bean.getBUILDER());
			sql.addYMDHMS("     ,? ", bean.getLAUNCH_DATE());
			sql.addYMDHMS("     ,? ", bean.getBUILD_DATE());
			sql.add("   ,? ", bean.getENGN_MAKER());
			sql.add("   ,? ", bean.getENGN_TYPE());
			sql.add("   ,? ", bean.getNOR_BHP());
			sql.add("   ,? ", bean.getNOR_NW());
			sql.add("   ,? ", bean.getNOR_RPM());
			sql.add("   ,? ", bean.getMCR_BHP());
			sql.add("   ,? ", bean.getMCR_NW());
			sql.add("   ,? ", bean.getMCR_RPM());
			sql.add("   ,? ", bean.getYARD_NO());
			sql.add("   ,? ", bean.getMNG_CMP());
			sql.add("   ,? ", bean.getREG_OWN());
			sql.add("   ,? ", bean.getFLEET_USR_CODE());
			sql.add("   ,? ", bean.getREMARKS());
			sql.addYMDHMS("   ,? ", bean.getDateFrom());
			sql.addYMDHMS("   ,? ", bean.getDateTo());
			sql.addYMDHMS("   ,? ", bean.getINP_DATE());
			sql.addYMDHMS("   ,? ", null);
			sql.add("   ,? ", getProgramCode());
			sql.add("   ,? ", getUserCode());

			sql.add(")  ");

			DBUtil.execute(conn, sql);

			if (!isNotUseOP) {
				// SPEED������
				deleteVesselSpeed(conn, bean);

				// SPEED���o�^
				if (bean.getSpeedList() != null) {
					for (VesselSpeed dtl : bean.getSpeedList()) {
						entryVesselSpeed(conn, dtl);
					}
				}

				// AUX SEA CONS���o�^
				if (bean.getAuxSeaConsList() != null) {
					for (VesselSeaCons dtl : bean.getAuxSeaConsList()) {
						entryVesselSeaCons(conn, dtl);
					}
				}

				// PORT CONS������
				deleteVesselPortCons(conn, bean);

				// PORT CONS���o�^
				if (bean.getPortConsList() != null) {
					for (VesselPortCons dtl : bean.getPortConsList()) {
						entryVesselPortCons(conn, dtl);
					}
				}

				// Hold and tank capacity information erase
				deleteVesselHold(conn, bean);
				if (bean.getHoldConsList() != null) {
					for (VesselHold dtl : bean.getHoldConsList()) {
						entryVesselHoldCons(conn, dtl);
					}
				}

				// ������
				deleteVesselBunkerType(conn, bean);
				if (bean.getBunkerTypeList() != null) {
					// �f�t�H���g���g��Ȃ��ꍇ�ł��o�^�ŕێ��A�擾���ɏ�������
					for (CM_BNKR_TYPE_MST dtl : bean.getBunkerTypeList()) {
						entryVesselBunkerType(conn, bean.getCode(), dtl);
					}
				}

			}

			if (isUseBM) {
				sql = new VCreator();

				sql.add("INSERT  ");
				sql.add("INTO BM_VESSEL_MST(  ");
				sql.add("  KAI_CODE ");
				sql.add(" ,VESSEL_CODE ");
				sql.add(" ,STOCK_DEP_CODE ");
				sql.add(" ,BUNKER_DEP_CODE ");
				sql.add(" ,INP_DATE ");
				sql.add(" ,UPD_DATE ");
				sql.add(" ,PRG_ID ");
				sql.add(" ,USR_ID ");
				sql.add(")  ");
				sql.add("VALUES (  ");
				sql.add("  ? ", bean.getCompanyCode());
				sql.add(" ,? ", bean.getCode());
				sql.add(" ,? ", bean.getStockCode());
				sql.add(" ,? ", bean.getFuelCode());
				sql.adt(" ,? ", bean.getINP_DATE());
				sql.adt(" ,? ", null);
				sql.add(" ,? ", getProgramCode());
				sql.add(" ,? ", getUserCode());
				sql.add(")  ");

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Entry vessel hold cons.
	 * 
	 * @param conn db connection
	 * @param dtl detail
	 * @throws TException
	 */
	protected void entryVesselHoldCons(Connection conn, VesselHold dtl) throws TException {
		String tableName = "CM_VESSEL_HOLD_MST";

		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tableName + " ( ");
		sql.add("    KAI_CODE ");
		sql.add("   ,VESSEL_CODE ");
		sql.add("   ,HOLD_NO ");
		sql.add("   ,DISP_ODR ");
		sql.add("   ,HOLD_NAME ");
		sql.add("   ,CENTER_GRN ");
		sql.add("   ,CENTER_BALE ");
		sql.add("   ,LEN_BRE ");
		sql.add("   ,TYPE_SET ");
		sql.add("   ,STR_DATE ");
		sql.add("   ,END_DATE ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,UPD_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("    ? ", dtl.getKAI_CODE());
		sql.add("   ,? ", dtl.getVESSEL_CODE());
		sql.add("   ,? ", dtl.getHOLD_NO());
		sql.add("   ,? ", dtl.getDISP_ODR());
		sql.add("   ,? ", dtl.getHOLD_NAME());
		sql.add("   ,? ", dtl.getCENTER_GRN());
		sql.add("   ,? ", dtl.getCENTER_BALE());
		sql.add("   ,? ", dtl.getLEN_BRE());
		sql.add("   ,? ", dtl.getTYPE_SET());
		sql.adt("   ,? ", dtl.getSTR_DATE());
		sql.adt("   ,? ", dtl.getEND_DATE());
		sql.adt("   ,? ", dtl.getINP_DATE());
		sql.adt("   ,? ", dtl.getUPD_DATE());
		sql.add("   ,? ", dtl.getPRG_ID());
		sql.add("   ,? ", dtl.getUSR_ID());
		sql.add(" )");

		DBUtil.execute(conn, sql);
	}

	/**
	 * Delete vessel hold.
	 * 
	 * @param conn db connection
	 * @param bean detail
	 * @throws TException
	 */
	protected void deleteVesselHold(Connection conn, Vessel bean) throws TException {
		String tableName = "CM_VESSEL_HOLD_MST";

		VCreator sql = new VCreator();
		sql.add(" DELETE FROM " + tableName);
		sql.add(" WHERE  1 = 1 ");
		sql.add(" AND    KAI_CODE = ? ", bean.getCompanyCode());
		sql.add(" AND    VESSEL_CODE = ? ", bean.getCode());

		DBUtil.execute(conn, sql);
	}

	/**
	 * �D������o�^
	 * 
	 * @param conn db connection
	 * @param vesselCode �D�R�[�h
	 * @param dtl ������
	 * @throws TException
	 */
	protected void entryVesselBunkerType(Connection conn, String vesselCode, CM_BNKR_TYPE_MST dtl) throws TException {
		String tableName = "CM_VESSEL_BNKR_TYPE_MST";

		// �D���A���p������킪�w��ł��邩
		boolean isUseBnkrUsing = ServerConfig.isFlagOn("trans.op.use.vessel.bunker.using." + getCompanyCode());

		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tableName + " ( ");
		sql.add("  KAI_CODE ");
		sql.add(" ,VESSEL_CODE ");
		sql.add(" ,BNKR_TYPE_CODE ");
		sql.add(" ,GRADE_CODE ");
		sql.add(" ,DISP_ODR ");
		sql.add(" ,ENG_PRI_NOR ");
		sql.add(" ,AUX_PRI_NOR ");
		sql.add(" ,ENG_PRI_ECA ");
		sql.add(" ,AUX_PRI_ECA ");
		sql.add(" ,SC_ENG_PRI_NOR ");
		sql.add(" ,SC_AUX_PRI_NOR ");
		sql.add(" ,SC_ENG_PRI_ECA ");
		sql.add(" ,SC_AUX_PRI_ECA ");
		sql.add(" ,INP_DATE ");
		sql.add(" ,PRG_ID ");
		sql.add(" ,USR_ID ");
		sql.add(" ,USE_FLG ");
		sql.add(")  ");
		sql.add("VALUES (  ");
		sql.add("  ? ", dtl.getKAI_CODE());
		sql.add(" ,? ", vesselCode);
		sql.add(" ,? ", dtl.getBNKR_TYPE_CODE());
		sql.add(" ,? ", dtl.getGRADE_CODE());
		sql.add(" ,? ", dtl.getDISP_ODR());
		sql.add(" ,? ", dtl.getENG_PRI_NOR());
		sql.add(" ,? ", dtl.getAUX_PRI_NOR());
		sql.add(" ,? ", dtl.getENG_PRI_ECA());
		sql.add(" ,? ", dtl.getAUX_PRI_ECA());
		sql.add(" ,? ", dtl.getSC_ENG_PRI_NOR());
		sql.add(" ,? ", dtl.getSC_AUX_PRI_NOR());
		sql.add(" ,? ", dtl.getSC_ENG_PRI_ECA());
		sql.add(" ,? ", dtl.getSC_AUX_PRI_ECA());
		sql.adt(" ,? ", getNow());
		sql.add(" ,? ", getProgramCode());
		sql.add(" ,? ", getUserCode());
		sql.add(" ,? ", !isUseBnkrUsing ? 1 : dtl.getUSE_FLG()); // �v���O�������ݒ�̏ꍇ��1�Œ�
		sql.add(")  ");

		DBUtil.execute(conn, sql);
	}

	/**
	 * �D�̖�����폜
	 * 
	 * @param conn db connection
	 * @param bean detail
	 * @throws TException
	 */
	protected void deleteVesselBunkerType(Connection conn, Vessel bean) throws TException {
		String tableName = "CM_VESSEL_BNKR_TYPE_MST";

		VCreator sql = new VCreator();
		sql.add(" DELETE FROM " + tableName);
		sql.add(" WHERE  KAI_CODE = ? ", bean.getCompanyCode());
		sql.add(" AND    VESSEL_CODE = ? ", bean.getCode());

		DBUtil.execute(conn, sql);
	}

	/**
	 * Vessel���C������
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void modify(Vessel bean) throws TException {
		Connection conn = null;
		bean.setINP_DATE(getNow());
		bean.setUPD_DATE(getNow());

		try {
			boolean isUseBM = isUseBM();
			conn = DBUtil.getConnection();

			String tableName = "CM_VESSEL_MST";

			VCreator sql = new VCreator();
			sql.add(" UPDATE " + tableName);
			sql.add(" SET ");
			sql.add("    VESSEL_NAME= ? ", bean.getName());
			sql.add("   ,VESSEL_NAME_S =? ", bean.getNames());
			sql.add("   ,VESSEL_NAME_K =? ", bean.getNamek());
			if (isUseShortName) {
				sql.add("   ,VESSEL_SHORT_NAME =? ", bean.getSHORT_NAME());
			}
			sql.add("   ,OWNR_CODE = ? ", bean.getOWNR_CODE());
			sql.add("   ,OWNR_SHIP_CODE =? ", bean.getOWNR_SHIP_CODE());
			sql.add("   ,SHIP_TYPE =? ", bean.getSHIP_TYPE());
			sql.add("   ,SHIP_FORM =?  ", bean.getSHIP_FORM());
			sql.add("   ,OBJ_TYPE =?  ", bean.getObjectType());
			sql.add("   ,CALL_SIGN =? ", bean.getCALL_SIGN());
			sql.add("   ,FLAG =? ", bean.getFLAG());
			sql.add("   ,CLASS =? ", bean.getCLASS());
			sql.add("   ,IMO_NO =? ", bean.getIMO_NO());
			sql.add("   ,OFCL_NO =? ", bean.getOFCL_NO());
			sql.add("   ,CRN_CAP =? ", bean.getCRN_CAP());
			sql.add("   ,E_MAIL =? ", bean.getE_MAIL());
			sql.add("   ,PHONE_1 = ? ", bean.getPHONE_1());
			sql.add("   ,PHONE_2 = ? ", bean.getPHONE_2());
			sql.add("   ,FAX =? ", bean.getFAX());
			sql.add("   ,TELEX = ?  ", bean.getTELEX());
			sql.add("   ,DEPT_CODE = ?  ", bean.getDEPT_CODE());
			sql.add("   ,CHTR_PI_NAME = ? ", bean.getCHTR_PI_NAME());
			sql.add("   ,OWR_PI_NAME =? ", bean.getOWR_PI_NAME());
			sql.add("   ,NEXT_DRY_DOCK_DATE=? ", bean.getNEXT_DRY_DOCK_DATE());
			sql.add("   ,EST_FLG = ? ", bean.getEST_FLG());
			sql.add("   ,SUSPENDED_FLG = ?  ", bean.getSUSPENDED_FLG());
			sql.add("   ,RELET_FLG = ?  ", bean.getRELET_FLG());
			sql.add("   ,SCRUBBER_KBN = ?  ", bean.getSCRUBBER_KBN());
			sql.add("   ,USE_DEFAULT_BNKR_TYPE = ?  ", bean.getUSE_DEFAULT_BNKR_TYPE());
			sql.add("   ,DWT_SMR_MT =? ", bean.getDWT_SMR_MT());
			sql.add("   ,DWT_TRP_MT =? ", bean.getDWT_TRP_MT());
			sql.add("   ,DWT_WTR_MT =? ", bean.getDWT_WTR_MT());
			sql.add("   ,DWT_SMR_LT =?  ", bean.getDWT_SMR_LT());
			sql.add("   ,DWT_TRP_LT =? ", bean.getDWT_TRP_LT());
			sql.add("   ,DWT_WTR_LT =? ", bean.getDWT_WTR_LT());
			sql.add("   ,DRAFT_SMR_MT=? ", bean.getDRAFT_SMR_MT());
			sql.add("   ,DRAFT_TRP_MT =? ", bean.getDRAFT_TRP_MT());
			sql.add("   ,DRAFT_WTR_MT =? ", bean.getDRAFT_WTR_MT());
			sql.add("   ,DRAFT_SMR_LT =? ", bean.getDRAFT_SMR_LT());
			sql.add("   ,DRAFT_TRP_LT =? ", bean.getDRAFT_TRP_LT());
			sql.add("   ,DRAFT_WTR_LT =? ", bean.getDRAFT_WTR_LT());
			sql.add("   ,LIGHT_WEIGHT_MT =? ", bean.getLIGHT_WEIGHT_MT());
			sql.add("   ,LIGHT_WEIGHT_LT =? ", bean.getLIGHT_WEIGHT_LT());
			sql.add("   ,LIGHT_WEIGHT_TRP =? ", bean.getLIGHT_WEIGHT_TRP());
			sql.add("   ,GROSS =? ", bean.getGROSS());
			sql.add("   ,NET =? ", bean.getNET());
			sql.add("   ,PANAMA_GROSS =? ", bean.getPANAMA_GROSS());
			sql.add("   ,PANAMA_NET=? ", bean.getPANAMA_NET());
			sql.add("   ,SUEZ_GROSS=? ", bean.getSUEZ_GROSS());
			sql.add("   ,SUEZ_NET=? ", bean.getSUEZ_NET());
			sql.add("   ,LOA_M =?", bean.LOA_M);
			sql.add("   ,LOA_FT=? ", bean.getLOA_FT());
			sql.add("   ,LREG_M =? ", bean.getLREG_M());
			sql.add("   ,LREG_FT=? ", bean.getLREG_FT());
			sql.add("   ,LPP_M =? ", bean.getLPP_M());
			sql.add("   ,LPP_FT=? ", bean.getLPP_FT());
			sql.add("   ,BEAM_M =? ", bean.getBEAM_M());
			sql.add("   ,BEAM_FT=? ", bean.getBEAM_FT());
			sql.add("   ,DEPTH_M =? ", bean.getDEPTH_M());
			sql.add("   ,DEPTH_FT=? ", bean.getDEPTH_FT());
			sql.add("   ,AIR_DRAFT_M=? ", bean.getAIR_DRAFT_M());
			sql.add("   ,AIR_DRAFT_FT=? ", bean.getAIR_DRAFT_FT());
			sql.add("   ,BUILDER=? ", bean.getBUILDER());
			sql.add("   ,LAUNCH_DATE=? ", bean.getLAUNCH_DATE());
			sql.add("   ,BUILD_DATE=? ", bean.getBUILD_DATE());
			sql.add("   ,ENGN_MAKER=? ", bean.getENGN_MAKER());
			sql.add("   ,ENGN_TYPE=? ", bean.getENGN_TYPE());
			sql.add("   ,NOR_BHP =? ", bean.getNOR_BHP());
			sql.add("   ,NOR_NW =? ", bean.getNOR_NW());
			sql.add("   ,NOR_RPM=? ", bean.getNOR_RPM());
			sql.add("   ,MCR_BHP=? ", bean.getMCR_BHP());
			sql.add("   ,MCR_NW=? ", bean.getMCR_NW());
			sql.add("   ,MCR_RPM=? ", bean.getMCR_RPM());
			sql.add("   ,YARD_NO=? ", bean.getYARD_NO());
			sql.add("   ,MNG_CMP=? ", bean.getMNG_CMP());
			sql.add("   ,REG_OWN=? ", bean.getREG_OWN());
			sql.add("   ,FLEET_USR_CODE=? ", bean.getFLEET_USR_CODE());
			sql.add("   ,REMARKS=? ", bean.getREMARKS());
			sql.adt("   ,STR_DATE=? ", bean.getSTR_DATE());
			sql.adt("   ,END_DATE=? ", bean.getEND_DATE());
			sql.adt("   ,INP_DATE=? ", bean.getINP_DATE());
			sql.adt("   ,UPD_DATE=? ", bean.getUPD_DATE());
			sql.add("   ,PRG_ID=? ", getProgramCode());
			sql.add("   ,USR_ID=? ", getUserCode());
			sql.add("WHERE 1 = 1 ");
			sql.add("AND KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("AND VESSEL_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

			if (!isNotUseOP) {
				// SPEED������
				deleteVesselSpeed(conn, bean);

				// SPEED���o�^
				if (bean.getSpeedList() != null) {
					for (VesselSpeed dtl : bean.getSpeedList()) {
						entryVesselSpeed(conn, dtl);
					}
				}

				// AUX SEA CONS���o�^
				if (bean.getAuxSeaConsList() != null) {
					for (VesselSeaCons dtl : bean.getAuxSeaConsList()) {
						entryVesselSeaCons(conn, dtl);
					}
				}

				// PORT CONS������
				deleteVesselPortCons(conn, bean);

				// PORT CONS���o�^
				if (bean.getPortConsList() != null) {
					for (VesselPortCons dtl : bean.getPortConsList()) {
						entryVesselPortCons(conn, dtl);
					}
				}

				// Hold and tank capacity information erase
				deleteVesselHold(conn, bean);
				if (bean.getHoldConsList() != null) {
					for (VesselHold dtl : bean.getHoldConsList()) {
						entryVesselHoldCons(conn, dtl);
					}
				}

				// ������
				deleteVesselBunkerType(conn, bean);
				if (bean.getBunkerTypeList() != null) {
					// �f�t�H���g���g��Ȃ��ꍇ�ł��o�^�ŕێ��A�擾���ɏ�������
					for (CM_BNKR_TYPE_MST dtl : bean.getBunkerTypeList()) {
						entryVesselBunkerType(conn, bean.getCode(), dtl);
					}
				}

			}

			if (isUseBM) {

				sql = new VCreator();

				sql.add("UPDATE BM_VESSEL_MST  ");
				sql.add("SET ");
				sql.add("	 KAI_CODE = ? ", bean.getCompanyCode());
				sql.add("	,VESSEL_CODE = ? ", bean.getCode());
				sql.add("	,STOCK_DEP_CODE = ? ", bean.getStockCode());
				sql.add("	,BUNKER_DEP_CODE = ? ", bean.getFuelCode());
				sql.addYMDHMS("   , UPD_DATE = ? ", bean.getUPD_DATE());
				sql.add("	,PRG_ID = ? ", getProgramCode());
				sql.add("	,USR_ID = ? ", getUserCode());
				sql.add("WHERE 1 = 1 ");
				sql.add("AND KAI_CODE = ? ", bean.getCompanyCode());
				sql.add("AND VESSEL_CODE = ? ", bean.getCode());

				int count = DBUtil.execute(conn, sql);

				if (count == 0) {
					sql = new VCreator();

					sql.add("INSERT  ");
					sql.add("INTO BM_VESSEL_MST(  ");
					sql.add("  KAI_CODE ");
					sql.add(" ,VESSEL_CODE ");
					sql.add(" ,STOCK_DEP_CODE ");
					sql.add(" ,BUNKER_DEP_CODE ");
					sql.add(" ,INP_DATE ");
					sql.add(" ,UPD_DATE ");
					sql.add(" ,PRG_ID ");
					sql.add(" ,USR_ID ");
					sql.add(")  ");
					sql.add("VALUES (  ");
					sql.add("  ? ", bean.getCompanyCode());
					sql.add(" ,? ", bean.getCode());
					sql.add(" ,? ", bean.getStockCode());
					sql.add(" ,? ", bean.getFuelCode());
					sql.addYMDHMS(" ,? ", bean.getINP_DATE());
					sql.addYMDHMS(" ,? ", null);
					sql.add(" ,? ", getProgramCode());
					sql.add(" ,? ", getUserCode());
					sql.add(")  ");

					DBUtil.execute(conn, sql);
				}

			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Vessel���폜����
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void delete(Vessel bean) throws TException {
		Connection conn = null;

		try {
			boolean isUseBM = isUseBM();
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql.add("DELETE FROM CM_VESSEL_MST ");
			sql.add("  WHERE KAI_CODE  = ? ", bean.getCompanyCode());
			sql.add("  AND VESSEL_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

			if (!isNotUseOP) {
				// SPEED������
				deleteVesselSpeed(conn, bean);

				// SEA CONS������
				deleteVesselSeaCons(conn, bean);

				// PORT CONS������
				deleteVesselPortCons(conn, bean);

				// HOLD CONS information erase
				deleteVesselHold(conn, bean);

				// �R���ăv�b�V��
				deleteVesselBunkerType(conn, bean);
			}

			if (isUseBM) {
				sql = new VCreator();

				sql.add("DELETE FROM BM_VESSEL_MST ");
				sql.add("  WHERE KAI_CODE  = ? ", bean.getCompanyCode());
				sql.add("  AND VESSEL_CODE = ? ", bean.getCode());

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �R���Ǘ����g�����ǂ���
	 * 
	 * @return true:�g���Afalse:�g��Ȃ�
	 * @throws TException
	 */
	public boolean isUseBM() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			DBUtil.selectOneInt(conn, " SELECT COUNT(*) FROM BM_VESSEL_MST");
			return true;

		} catch (Exception e) {
			return false;

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̎擾
	 * 
	 * @param condition
	 * @return VESSEL�X�s�[�h�}�X�^
	 * @throws TException
	 */
	public List<VesselSpeed> getSpeedList(VesselSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			return getSpeedList(conn, condition);
		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̎擾
	 * 
	 * @param conn
	 * @param condition
	 * @return VESSEL�X�s�[�h�}�X�^
	 * @throws Exception
	 */
	public List<VesselSpeed> getSpeedList(Connection conn, VesselSearchCondition condition) throws Exception {

		String tableName = "CM_VESSEL_SPD_MST";

		VCreator where = new VCreator();
		where.add(" WHERE t.KAI_CODE = ? ", condition.getCompanyCode());

		// �D�w��
		if (!Util.isNullOrEmpty(condition.getCode())) {
			where.add(" AND   t.VESSEL_CODE = ? ", condition.getCode());
		}
		// �D���X�g�w��
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			where.add(" AND ( 1 = 2 ");
			for (String code : condition.getCodeList()) {
				where.add(" OR t.VESSEL_CODE = ? ", code);
			}
			where.add(" ) ");
		}

		VCreator sql = new VCreator();
		sql.add(" SELECT  t.* ");
		sql.add(" FROM    " + tableName + " t ");
		sql.add(where);
		sql.add(" ORDER BY ");
		sql.add("    t.KAI_CODE ");
		sql.add("   ,t.VESSEL_CODE ");
		sql.add("   ,t.DISP_ODR ");
		sql.add("   ,t.SPD_TYPE_CODE ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		List<VesselSpeed> list = new ArrayList<VesselSpeed>();

		while (rs.next()) {
			VesselSpeed bean = mappingVesselSpeed(rs);
			list.add(bean);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;
	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̃}�b�s���O
	 * 
	 * @param rs
	 * @return VESSEL�X�s�[�h�}�X�^
	 * @throws Exception
	 */
	protected VesselSpeed mappingVesselSpeed(ResultSet rs) throws Exception {
		VesselSpeed bean = createVesselSpeed();
		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setVESSEL_CODE(rs.getString("VESSEL_CODE")); // VESSEL�R�[�h
		bean.setSPD_TYPE_CODE(rs.getString("SPD_TYPE_CODE")); // SPEED�^�C�v�R�[�h
		bean.setDISP_ODR(rs.getInt("DISP_ODR")); // �\����
		bean.setSPD_BAL(rs.getBigDecimal("SPD_BAL")); // BALLAST�X�s�[�h
		bean.setSPD_LAD(rs.getBigDecimal("SPD_LAD")); // LADEN�X�s�[�h
		bean.setSTR_DATE(rs.getTimestamp("STR_DATE")); // �J�n�N����
		bean.setEND_DATE(rs.getTimestamp("END_DATE")); // �I���N����
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // �o�^�N����
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // �X�V�N����
		bean.setPRG_ID(rs.getString("PRG_ID")); // �v���O����ID
		bean.setUSR_ID(rs.getString("USR_ID")); // ���[�U�[ID
		return bean;
	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̍쐬
	 * 
	 * @return VesselSpeed
	 */
	protected VesselSpeed createVesselSpeed() {
		return new VesselSpeed();
	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̓o�^
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryVesselSpeed(VesselSpeed bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			// �o�^�������ďo��
			entryVesselSpeed(conn, bean);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̓o�^
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void entryVesselSpeed(Connection conn, VesselSpeed bean) throws TException {
		String tableName = "CM_VESSEL_SPD_MST";

		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tableName + " ( ");
		sql.add("    KAI_CODE "); // ��ЃR�[�h
		sql.add("   ,VESSEL_CODE "); // VESSEL�R�[�h
		sql.add("   ,SPD_TYPE_CODE "); // SPEED�^�C�v�R�[�h
		sql.add("   ,DISP_ODR "); // �\����
		sql.add("   ,SPD_BAL "); // BALLAST�X�s�[�h
		sql.add("   ,SPD_LAD "); // LADEN�X�s�[�h
		sql.add("   ,STR_DATE "); // �J�n�N����
		sql.add("   ,END_DATE "); // �I���N����
		sql.add("   ,INP_DATE "); // �o�^�N����
		sql.add("   ,UPD_DATE "); // �X�V�N����
		sql.add("   ,PRG_ID "); // �v���O����ID
		sql.add("   ,USR_ID "); // ���[�U�[ID
		sql.add(" ) VALUES ( ");
		sql.add("    ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("   ,? ", bean.getVESSEL_CODE()); // VESSEL�R�[�h
		sql.add("   ,? ", bean.getSPD_TYPE_CODE()); // SPEED�^�C�v�R�[�h
		sql.add("   ,? ", bean.getDISP_ODR()); // �\����
		sql.add("   ,? ", bean.getSPD_BAL()); // BALLAST�X�s�[�h
		sql.add("   ,? ", bean.getSPD_LAD()); // LADEN�X�s�[�h
		sql.adt("   ,? ", bean.getSTR_DATE()); // �J�n�N����
		sql.adt("   ,? ", bean.getEND_DATE()); // �I���N����
		sql.adt("   ,? ", bean.getINP_DATE()); // �o�^�N����
		sql.adt("   ,? ", bean.getUPD_DATE()); // �X�V�N����
		sql.add("   ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("   ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" ) ");

		DBUtil.execute(conn, sql);

		// SEA CONS���o�^
		if (bean.getSeaConsList() != null) {
			for (VesselSeaCons dtl : bean.getSeaConsList()) {
				entryVesselSeaCons(conn, dtl);
			}
		}

	}

	/**
	 * VESSEL�X�s�[�h�}�X�^�̍폜
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void deleteVesselSpeed(Connection conn, Vessel bean) throws TException {
		String tableName = "CM_VESSEL_SPD_MST";

		VCreator sql = new VCreator();
		sql.add(" DELETE FROM " + tableName);
		sql.add(" WHERE  1 = 1 ");
		sql.add(" AND    KAI_CODE = ? ", bean.getCompanyCode()); // ��ЃR�[�h
		sql.add(" AND    VESSEL_CODE = ? ", bean.getCode()); // VESSEL�R�[�h

		DBUtil.execute(conn, sql);

		// SEA CONS������
		deleteVesselSeaCons(conn, bean);
	}

	/**
	 * PORT�R������ʃ}�X�^�̎擾
	 * 
	 * @param condition
	 * @return PORT�R������ʃ}�X�^
	 * @throws TException
	 */
	public List<VesselPortCons> getPortConsList(VesselSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			return getPortConsList(conn, condition);
		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * PORT�R������ʃ}�X�^�̎擾
	 * 
	 * @param conn
	 * @param condition
	 * @return PORT�R������ʃ}�X�^
	 * @throws Exception
	 */
	public List<VesselPortCons> getPortConsList(Connection conn, VesselSearchCondition condition) throws Exception {

		String tableName = "CM_VESSEL_PORT_CONS_MST";

		VCreator where = new VCreator();
		where.add(" WHERE t.KAI_CODE = ? ", condition.getCompanyCode());

		// �D�w��
		if (!Util.isNullOrEmpty(condition.getCode())) {
			where.add(" AND   t.VESSEL_CODE = ? ", condition.getCode());
		}
		// �D���X�g�w��
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			where.add(" AND ( 1 = 2 ");
			for (String code : condition.getCodeList()) {
				where.add(" OR t.VESSEL_CODE = ? ", code);
			}
			where.add(" ) ");
		}

		VCreator sql = new VCreator();
		sql.add(" SELECT  t.* ");
		sql.add(" FROM    " + tableName + " t ");
		sql.add(where);
		sql.add(" ORDER BY ");
		sql.add("    t.KAI_CODE ");
		sql.add("   ,t.VESSEL_CODE ");
		sql.add("   ,t.USING_PU_KBN ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		List<VesselPortCons> list = new ArrayList<VesselPortCons>();

		while (rs.next()) {
			VesselPortCons bean = mappingVesselPortCons(rs);
			list.add(bean);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;
	}

	/**
	 * PORT�R������ʃ}�X�^�̃}�b�s���O
	 * 
	 * @param rs
	 * @return PORT�R������ʃ}�X�^
	 * @throws Exception
	 */
	protected VesselPortCons mappingVesselPortCons(ResultSet rs) throws Exception {
		VesselPortCons bean = createVesselPortCons();
		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setVESSEL_CODE(rs.getString("VESSEL_CODE")); // VESSEL�R�[�h
		bean.setUSING_PU_KBN(rs.getInt("USING_PU_KBN")); // �g�p�ړI�敪
		bean.setPORT_LD_CONS(rs.getBigDecimal("PORT_LD_CONS")); // LOADING�����
		bean.setPORT_DISC_CONS(rs.getBigDecimal("PORT_DISC_CONS")); // DISCHARGING�����
		bean.setPORT_IDLE_CONS(rs.getBigDecimal("PORT_IDLE_CONS")); // IDLE�����
		bean.setSTR_DATE(rs.getTimestamp("STR_DATE")); // �J�n�N����
		bean.setEND_DATE(rs.getTimestamp("END_DATE")); // �I���N����
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // �o�^�N����
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // �X�V�N����
		bean.setPRG_ID(rs.getString("PRG_ID")); // �v���O����ID
		bean.setUSR_ID(rs.getString("USR_ID")); // ���[�U�[ID
		return bean;
	}

	/**
	 * PORT�R������ʃ}�X�^�̍쐬
	 * 
	 * @return VesselPortCons
	 */
	protected VesselPortCons createVesselPortCons() {
		return new VesselPortCons();
	}

	/**
	 * PORT�R������ʃ}�X�^�̓o�^
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryVesselPortCons(VesselPortCons bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			// �o�^�������ďo��
			entryVesselPortCons(conn, bean);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * PORT�R������ʃ}�X�^�̓o�^
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void entryVesselPortCons(Connection conn, VesselPortCons bean) throws TException {
		String tableName = "CM_VESSEL_PORT_CONS_MST";

		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tableName + " ( ");
		sql.add("    KAI_CODE "); // ��ЃR�[�h
		sql.add("   ,VESSEL_CODE "); // VESSEL�R�[�h
		sql.add("   ,USING_PU_KBN "); // �g�p�ړI�敪
		sql.add("   ,PORT_LD_CONS "); // LOADING�����
		sql.add("   ,PORT_DISC_CONS "); // DISCHARGING�����
		sql.add("   ,PORT_IDLE_CONS "); // IDLE�����
		sql.add("   ,STR_DATE "); // �J�n�N����
		sql.add("   ,END_DATE "); // �I���N����
		sql.add("   ,INP_DATE "); // �o�^�N����
		sql.add("   ,UPD_DATE "); // �X�V�N����
		sql.add("   ,PRG_ID "); // �v���O����ID
		sql.add("   ,USR_ID "); // ���[�U�[ID
		sql.add(" ) VALUES ( ");
		sql.add("    ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("   ,? ", bean.getVESSEL_CODE()); // VESSEL�R�[�h
		sql.add("   ,? ", bean.getUSING_PU_KBN()); // �g�p�ړI�敪
		sql.add("   ,? ", bean.getPORT_LD_CONS()); // LOADING�����
		sql.add("   ,? ", bean.getPORT_DISC_CONS()); // DISCHARGING�����
		sql.add("   ,? ", bean.getPORT_IDLE_CONS()); // IDLE�����
		sql.adt("   ,? ", bean.getSTR_DATE()); // �J�n�N����
		sql.adt("   ,? ", bean.getEND_DATE()); // �I���N����
		sql.adt("   ,? ", bean.getINP_DATE()); // �o�^�N����
		sql.adt("   ,? ", bean.getUPD_DATE()); // �X�V�N����
		sql.add("   ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("   ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" )");

		DBUtil.execute(conn, sql);

	}

	/**
	 * PORT�R������ʃ}�X�^�̍폜
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void deleteVesselPortCons(Connection conn, Vessel bean) throws TException {
		String tableName = "CM_VESSEL_PORT_CONS_MST";

		VCreator sql = new VCreator();
		sql.add(" DELETE FROM " + tableName);
		sql.add(" WHERE  1 = 1 ");
		sql.add(" AND    KAI_CODE = ? ", bean.getCompanyCode()); // ��ЃR�[�h
		sql.add(" AND    VESSEL_CODE = ? ", bean.getCode()); // VESSEL�R�[�h

		DBUtil.execute(conn, sql);

	}

	/**
	 * �C��R������ʃ}�X�^�̎擾
	 * 
	 * @param condition
	 * @return �C��R������ʃ}�X�^
	 * @throws TException
	 */
	public List<VesselSeaCons> getSeaConsList(VesselSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			return getSeaConsList(conn, condition);
		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �C��R������ʃ}�X�^�̎擾
	 * 
	 * @param conn
	 * @param condition
	 * @return �C��R������ʃ}�X�^
	 * @throws Exception
	 */
	public List<VesselSeaCons> getSeaConsList(Connection conn, VesselSearchCondition condition) throws Exception {

		String tableName = "CM_VESSEL_SEA_CONS_MST";

		VCreator where = new VCreator();
		where.add(" WHERE t.KAI_CODE = ? ", condition.getCompanyCode());

		// �D�w��
		if (!Util.isNullOrEmpty(condition.getCode())) {
			where.add(" AND   t.VESSEL_CODE = ? ", condition.getCode());
		}
		// �D���X�g�w��
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			where.add(" AND ( 1 = 2 ");
			for (String code : condition.getCodeList()) {
				where.add(" OR t.VESSEL_CODE = ? ", code);
			}
			where.add(" ) ");
		}

		VCreator sql = new VCreator();
		sql.add(" SELECT  t.* ");
		sql.add(" FROM    " + tableName + " t ");
		sql.add(where);
		sql.add(" ORDER BY ");
		sql.add("    t.KAI_CODE ");
		sql.add("   ,t.VESSEL_CODE ");
		sql.add("   ,t.SPD_TYPE_CODE ");
		sql.add("   ,t.USING_PU_KBN ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		List<VesselSeaCons> list = new ArrayList<VesselSeaCons>();

		while (rs.next()) {
			VesselSeaCons bean = mappingVesselSeaCons(rs);
			list.add(bean);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;
	}

	/**
	 * �C��R������ʃ}�X�^�̃}�b�s���O
	 * 
	 * @param rs
	 * @return �C��R������ʃ}�X�^
	 * @throws Exception
	 */
	public VesselSeaCons mappingVesselSeaCons(ResultSet rs) throws Exception {
		VesselSeaCons bean = createVesselSeaCons();
		bean.setKAI_CODE(rs.getString("KAI_CODE")); // ��ЃR�[�h
		bean.setVESSEL_CODE(rs.getString("VESSEL_CODE")); // VESSEL_CODE
		bean.setSPD_TYPE_CODE(rs.getString("SPD_TYPE_CODE")); // SPEED�^�C�vID
		bean.setUSING_PU_KBN(rs.getInt("USING_PU_KBN")); // �g�p�ړI�敪
		bean.setSEA_CONS_BAL(rs.getBigDecimal("SEA_CONS_BAL")); // �C��R�������(BALLAST)
		bean.setSEA_CONS_LAD(rs.getBigDecimal("SEA_CONS_LAD")); // �C��R�������(LADEN)
		bean.setSTR_DATE(rs.getTimestamp("STR_DATE")); // �J�n�N����
		bean.setEND_DATE(rs.getTimestamp("END_DATE")); // �I���N����
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // �o�^�N����
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // �X�V�N����
		bean.setPRG_ID(rs.getString("PRG_ID")); // �v���O����ID
		bean.setUSR_ID(rs.getString("USR_ID")); // ���[�U�[ID
		return bean;
	}

	/**
	 * �C��R������ʃ}�X�^�̍쐬
	 * 
	 * @return VesselSeaCons
	 */
	protected VesselSeaCons createVesselSeaCons() {
		return new VesselSeaCons();
	}

	/**
	 * �C��R������ʃ}�X�^�̓o�^
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryVesselSeaCons(VesselSeaCons bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			// �o�^�������ďo��
			entryVesselSeaCons(conn, bean);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �C��R������ʃ}�X�^�̓o�^
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void entryVesselSeaCons(Connection conn, VesselSeaCons bean) throws TException {
		String tableName = "CM_VESSEL_SEA_CONS_MST";

		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tableName + " ( ");
		sql.add("    KAI_CODE "); // ��ЃR�[�h
		sql.add("   ,VESSEL_CODE "); // VESSEL_CODE
		sql.add("   ,SPD_TYPE_CODE "); // SPEED�^�C�vID
		sql.add("   ,USING_PU_KBN "); // �g�p�ړI�敪
		sql.add("   ,SEA_CONS_BAL "); // �C��R�������(BALLAST)
		sql.add("   ,SEA_CONS_LAD "); // �C��R�������(LADEN)
		sql.add("   ,STR_DATE "); // �J�n�N����
		sql.add("   ,END_DATE "); // �I���N����
		sql.add("   ,INP_DATE "); // �o�^�N����
		sql.add("   ,UPD_DATE "); // �X�V�N����
		sql.add("   ,PRG_ID "); // �v���O����ID
		sql.add("   ,USR_ID "); // ���[�U�[ID
		sql.add(" ) VALUES ( ");
		sql.add("    ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("   ,? ", bean.getVESSEL_CODE()); // VESSEL_CODE
		sql.add("   ,? ", bean.getSPD_TYPE_CODE()); // SPEED�^�C�vID
		sql.add("   ,? ", bean.getUSING_PU_KBN()); // �g�p�ړI�敪
		sql.add("   ,? ", bean.getSEA_CONS_BAL()); // �C��R�������(BALLAST)
		sql.add("   ,? ", bean.getSEA_CONS_LAD()); // �C��R�������(LADEN)
		sql.adt("   ,? ", bean.getSTR_DATE()); // �J�n�N����
		sql.adt("   ,? ", bean.getEND_DATE()); // �I���N����
		sql.adt("   ,? ", bean.getINP_DATE()); // �o�^�N����
		sql.adt("   ,? ", bean.getUPD_DATE()); // �X�V�N����
		sql.add("   ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("   ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" )  ");

		DBUtil.execute(conn, sql);

	}

	/**
	 * �C��R������ʃ}�X�^�̍폜
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void deleteVesselSeaCons(Connection conn, Vessel bean) throws TException {
		String tableName = "CM_VESSEL_SEA_CONS_MST";

		VCreator sql = new VCreator();
		sql.add(" DELETE FROM " + tableName);
		sql.add(" WHERE  1 = 1 ");
		sql.add(" AND    KAI_CODE = ? ", bean.getCompanyCode()); // ��ЃR�[�h
		sql.add(" AND    VESSEL_CODE = ? ", bean.getCode()); // VESSEL�R�[�h

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

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(VesselSearchCondition condition) throws TException {

		try {

			// �D���f�[�^�𒊏o
			List<Vessel> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			VesselExcel exl = new VesselExcel(getUser().getLanguage());
			return exl.getExcel(list, isUseBM());

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @param vessel
	 * @throws TException
	 */
	protected void setupAutoVesselCode(Vessel vessel) throws TException {

		String newCode = getNewVesselCode(vessel);

		// Vessel�G���e�B�e�B�ɍ̔Ԃ��ꂽVessel Code��ݒ�
		vessel.setCode(newCode);

		// �X�s�[�h�G���e�B�e�B�̃��X�g�ɍ̔Ԃ��ꂽVessel Code��ݒ�
		if (vessel.getSpeedList() != null) {
			for (VesselSpeed dtl : vessel.getSpeedList()) {
				dtl.setVESSEL_CODE(newCode);
				for (VesselSeaCons cons : dtl.getSeaConsList()) {
					cons.setVESSEL_CODE(newCode);
				}
			}
		}

		// AUX�C��R������̃��X�g�ɍ̔Ԃ��ꂽVessel Code��ݒ�
		if (vessel.getAuxSeaConsList() != null) {
			for (VesselSeaCons dtl : vessel.getAuxSeaConsList()) {
				dtl.setVESSEL_CODE(newCode);
			}
		}

		// �`�R������̃��X�g�ɍ̔Ԃ��ꂽVessel Code��ݒ�
		if (vessel.getPortConsList() != null) {
			for (VesselPortCons dtl : vessel.getPortConsList()) {
				dtl.setVESSEL_CODE(newCode);
			}
		}

		// Hold and tank�̃��X�g�ɍ̔Ԃ��ꂽVessel Code��ݒ�
		if (vessel.getHoldConsList() != null) {
			for (VesselHold dtl : vessel.getHoldConsList()) {
				dtl.setVESSEL_CODE(newCode);
			}
		}
	}

	/**
	 * @param vessel
	 * @return newVesselCode
	 * @throws TException
	 */
	protected String getNewVesselCode(Vessel vessel) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();
			sql.add(" SELECT VESSEL_CODE ");
			sql.add(" FROM   CM_VESSEL_MST ");
			sql.add(" WHERE  KAI_CODE = ? ", vessel.getCompanyCode());
			sql.add(" AND    LENGTH(VESSEL_CODE) = ? ", vessel.getDigits());

			if (!Util.isNullOrEmpty(vessel.getAutoNumberStartCode())) {
				// PREFIX�w�肠��
				sql.add(" AND    VESSEL_CODE >= ? ", vessel.getAutoNumberStartCode());
			}

			sql.add(" ORDER BY VESSEL_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<String> list = new ArrayList<String>();

			while (rs.next()) {
				String vesselcode = rs.getString(1);
				list.add(vesselcode);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			return getNextCode(list, vessel);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @param existCodes
	 * @param vessel
	 * @return current
	 */
	protected String getNextCode(List<String> existCodes, Vessel vessel) {

		// ��ԎႢ�ԍ����擾���邽�߂̊J�n���̕�����
		String currentCode = StringUtil.fill("", vessel.getDigits(), '0');

		// �J�n�R�[�h�w�肳�ꂽ�ꍇ�ɁA���菈����������
		if (!Util.isNullOrEmpty(vessel.getAutoNumberStartCode())) {
			currentCode = StringUtil.leftBX(vessel.getAutoNumberStartCode() + currentCode, vessel.getDigits());
		}

		List<String> vesselCodePattern = getVesselCodePattern();

		for (int codeIdx = 0; codeIdx < existCodes.size(); codeIdx++) {
			String nextCode = existCodes.get(codeIdx);
			if (!Util.equals(currentCode, nextCode) //
				&& !existCodes.contains(currentCode)) {
				// ���ԍ��ƕs��v�A���̔ԗ\��ԍ������݂��ĂȂ��ꍇ�A�Ԃ�
				return currentCode;
			}
			currentCode = createNextCode(vesselCodePattern, currentCode, vessel.getDigits());
		}
		return currentCode;
	}

	/**
	 * @param vesselCodePattern
	 * @param currentCode
	 * @param digits
	 * @return New Vessel Code
	 */
	protected String createNextCode(List<String> vesselCodePattern, String currentCode, int digits) {
		//
		int[] idxs = new int[digits];
		for (int chrIdx = 0; chrIdx < digits; chrIdx++) {
			String s = currentCode.substring(chrIdx, chrIdx + 1);
			int ptnIdx = vesselCodePattern.indexOf(s);
			if (ptnIdx == -1) {
				// �Y�������񂪑��݂��Ȃ� // �p�^�[���O�̕�����
				break;
			}
			idxs[chrIdx] = ptnIdx;
		}
		idxs[digits - 1] += 1;

		// �����Z�L�����e�B
		for (int i = digits - 1; i >= 0; i--) {
			if (idxs[i] > vesselCodePattern.size() - 1) {
				if (i == 0) {
					// �w�茅���̂��ׂẴR�[�h�����p����Ă���ꍇ
					return null;
				}
				idxs[i - 1] += 1;
				idxs[i] = idxs[i] % (vesselCodePattern.size());
			}
		}
		// ������č\�z
		StringBuilder str = new StringBuilder();
		for (int i : idxs) {
			str.append(vesselCodePattern.get(i));
		}
		return str.toString();
	}

	/**
	 * Vessel Master �ɓo�^�ł��镶��
	 * 
	 * @return �o�^�ł��镶��
	 */
	protected List<String> getVesselCodePattern() {
		return new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
	}

}
