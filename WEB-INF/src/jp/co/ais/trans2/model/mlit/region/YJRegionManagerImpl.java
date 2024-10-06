package jp.co.ais.trans2.model.mlit.region;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �A�����э��E�n��}�l�[�W�������N���X
 */
public class YJRegionManagerImpl extends TModel implements YJRegionManager {

	/**
	 * �A�����э����擾����
	 * 
	 * @return �A�����э����X�g
	 * @throws TException
	 */
	public List<YJRegion> getRegion(YJRegionSearchCondition condition) throws TException {

		List<YJRegion> list = new ArrayList<YJRegion>();

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");
			sql.add("  ,region.REGION_NAME ");
			sql.add("  ,NULL COUNTRY_CODE ");
			sql.add("  ,NULL COUNTRY_NAME ");
			sql.add("  ,region.REMARK ");
			sql.add("  ,NULL COUNTRY_REMARK ");
			sql.add("  ,region.INP_DATE ");
			sql.add("  ,region.UPD_DATE ");
			sql.add("  ,region.PRG_ID ");
			sql.add("  ,region.USR_ID ");
			sql.add("  ,NULL COUNTRY_INP_DATE ");
			sql.add("  ,NULL COUNTRY_UPD_DATE ");
			sql.add("  ,NULL COUNTRY_PRG_ID ");
			sql.add("  ,NULL COUNTRY_PRG_ID ");
			sql.add(" FROM YJ_REGION_MST region ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));
			sql.add(" ORDER BY ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");

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
	 * �A�����э��}�X�^�̓o�^
	 * 
	 * @param bean �A�����э��}�X�^
	 * @throws TException
	 */
	public void entryRegion(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add(" INSERT INTO YJ_REGION_MST ( ");
		sql.add("   KAI_CODE ");
		sql.add("  ,REGION_CODE ");
		sql.add("  ,REGION_NAME ");
		sql.add("  ,REMARK ");
		sql.add("  ,INP_DATE ");
		sql.add("  ,UPD_DATE ");
		sql.add("  ,PRG_ID ");
		sql.add("  ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("        ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("       ,? ", bean.getREGION_CODE()); // �R�[�h
		sql.add("       ,? ", bean.getREGION_NAME()); // ����
		sql.add("       ,? ", bean.getREGION_REMARK()); // Remark
		sql.addYMDHMS(" ,? ", bean.getINP_DATE()); // �o�^���t
		sql.addYMDHMS(" ,? ", bean.getUPD_DATE()); // �X�V���t
		sql.add("       ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("       ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" ) ");

		DBUtil.execute(sql);

	}

	/**
	 * �A�����э��}�X�^�̍폜
	 * 
	 * @param bean �A�����э��}�X�^
	 * @throws TException
	 */
	public void deleteRegion(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add("DELETE FROM YJ_REGION_MST ");
		sql.add(" WHERE KAI_CODE  = ? ", bean.getKAI_CODE());
		sql.add("   AND REGION_CODE = ? ", bean.getREGION_CODE());

		DBUtil.execute(sql);

	}

	/**
	 * �A�����э��}�X�^�̓o�^
	 * 
	 * @param bean �A�����э��}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJRegion entryRegionMaster(YJRegion bean) throws TException {

		// �폜
		deleteRegion(bean);

		// ���ݒ�
		if (Util.isNullOrEmpty(bean.getINP_DATE())) {
			bean.setINP_DATE(getNow());
		} else {
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		// �o�^
		entryRegion(bean);

		return bean;
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcelRegion(YJRegionSearchCondition condition) throws TException {

		try {
			// �f�[�^�𒊏o
			List<YJRegion> list = getRegion(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			MLITRegionExcel excel = new MLITRegionExcel(getUser().getLanguage(), getCompany());
			return excel.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �A�����ђn����擾����
	 * 
	 * @return �A�����ђn�惊�X�g
	 * @throws TException
	 */
	public List<YJRegion> getCountry(YJRegionSearchCondition condition) throws TException {

		List<YJRegion> list = new ArrayList<YJRegion>();

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");
			sql.add("  ,region.REGION_NAME ");
			sql.add("  ,cou.COUNTRY_CODE ");
			sql.add("  ,cou.COUNTRY_NAME ");
			sql.add("  ,region.REMARK ");
			sql.add("  ,cou.REMARK  COUNTRY_REMARK ");
			sql.add("  ,region.INP_DATE ");
			sql.add("  ,region.UPD_DATE ");
			sql.add("  ,region.PRG_ID ");
			sql.add("  ,region.USR_ID ");
			sql.add("  ,cou.INP_DATE COUNTRY_INP_DATE ");
			sql.add("  ,cou.UPD_DATE COUNTRY_UPD_DATE ");
			sql.add("  ,cou.PRG_ID   COUNTRY_PRG_ID ");
			sql.add("  ,cou.USR_ID   COUNTRY_PRG_ID ");
			sql.add("FROM YJ_REGION_MST region ");
			sql.add("INNER JOIN YJ_COUNTRY_MST cou ON region.KAI_CODE    = cou.KAI_CODE ");
			sql.add("                             AND region.REGION_CODE = cou.REGION_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));
			sql.add(" ORDER BY ");
			sql.add("   region.KAI_CODE ");
			sql.add("  ,region.REGION_CODE ");
			sql.add("  ,cou.COUNTRY_CODE ");

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
	 * �A�����ђn��}�X�^�̓o�^
	 * 
	 * @param bean �A�����ђn��}�X�^
	 * @throws TException
	 */
	public void entryCountry(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add(" INSERT INTO YJ_COUNTRY_MST ( ");
		sql.add("   KAI_CODE ");
		sql.add("  ,REGION_CODE ");
		sql.add("  ,COUNTRY_CODE ");
		sql.add("  ,COUNTRY_NAME ");
		sql.add("  ,REMARK ");
		sql.add("  ,INP_DATE ");
		sql.add("  ,UPD_DATE ");
		sql.add("  ,PRG_ID ");
		sql.add("  ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("        ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("       ,? ", bean.getREGION_CODE()); // ���R�[�h
		sql.add("       ,? ", bean.getCOUNTRY_CODE()); // �n��R�[�h
		sql.add("       ,? ", bean.getCOUNTRY_NAME()); // �n�於��
		sql.add("       ,? ", bean.getCOUNTRY_REMARK()); // Remark
		sql.addYMDHMS(" ,? ", bean.getINP_DATE()); // �o�^���t
		sql.addYMDHMS(" ,? ", bean.getUPD_DATE()); // �X�V���t
		sql.add("       ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("       ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" ) ");

		DBUtil.execute(sql);

	}

	/**
	 * �A�����ђn��}�X�^�̍폜
	 * 
	 * @param bean �A�����ђn��}�X�^
	 * @throws TException
	 */
	public void deleteCountry(YJRegion bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add("DELETE FROM YJ_COUNTRY_MST ");
		sql.add(" WHERE KAI_CODE     = ? ", bean.getKAI_CODE());
		sql.add("   AND REGION_CODE  = ? ", bean.getREGION_CODE());
		sql.add("   AND COUNTRY_CODE = ? ", bean.getCOUNTRY_CODE());

		DBUtil.execute(sql);

	}

	/**
	 * �A�����ђn��}�X�^�̓o�^
	 * 
	 * @param bean �A�����ђn��}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJRegion entryCountryMaster(YJRegion bean) throws TException {

		// �폜
		deleteCountry(bean);

		// ���ݒ�
		if (Util.isNullOrEmpty(bean.getINP_DATE())) {
			bean.setINP_DATE(getNow());
		} else {
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		// �o�^
		entryCountry(bean);

		return bean;
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcelCountry(YJRegionSearchCondition condition) throws TException {

		try {
			// �f�[�^�𒊏o
			List<YJRegion> list = getCountry(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			MLITCountryExcel excel = new MLITCountryExcel(getUser().getLanguage(), getCompany());
			return excel.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �������̍쐬
	 * 
	 * @param condition
	 * @return ������
	 */
	protected String createSelectWhereSql(YJRegionSearchCondition condition) {
		SQLCreator sql = new SQLCreator();

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add(" AND region.KAI_CODE  = ?", condition.getCompanyCode());
		}

		// ���R�[�h
		if (!Util.isNullOrEmpty(condition.getRegionCode())) {
			sql.add(" AND region.REGION_CODE  = ? ", condition.getRegionCode());
		}

		// �J�n���R�[�h
		if (!Util.isNullOrEmpty(condition.getRegionCodeFrom())) {
			sql.add(" AND region.REGION_CODE >= ? ", condition.getRegionCodeFrom());
		}

		// �I�����R�[�h
		if (!Util.isNullOrEmpty(condition.getRegionCodeTo())) {
			sql.add(" AND region.REGION_CODE <= ? ", condition.getRegionCodeTo());
		}

		// ���R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getRegionCodeLike())) {
			sql.addLikeFront(" AND region.REGION_CODE ? ", condition.getRegionCodeLike());
		}

		// �����̂����܂�
		if (!Util.isNullOrEmpty(condition.getRegionNameLike())) {
			sql.addNLikeAmbi(" AND region.REGION_NAME ? ", condition.getRegionNameLike());
		}

		// �n��R�[�h
		if (!Util.isNullOrEmpty(condition.getCountryCode())) {
			sql.add(" AND cou.COUNTRY_CODE  = ? ", condition.getCountryCode());
		}

		// �J�n�n��R�[�h
		if (!Util.isNullOrEmpty(condition.getCountryCodeFrom())) {
			sql.add(" AND cou.COUNTRY_CODE >= ? ", condition.getCountryCodeFrom());
		}

		// �I���n��R�[�h
		if (!Util.isNullOrEmpty(condition.getCountryCodeTo())) {
			sql.add(" AND cou.COUNTRY_CODE <= ? ", condition.getCountryCodeTo());
		}

		// �n��R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCountryCodeLike())) {
			sql.addLikeFront(" AND cou.COUNTRY_CODE ? ", condition.getCountryCodeLike());
		}

		// �n�於�̂����܂�
		if (!Util.isNullOrEmpty(condition.getCountryNameLike())) {
			sql.addNLikeAmbi(" AND cou.COUNTRY_NAME ? ", condition.getCountryNameLike());
		}
		return sql.toSQL();
	}

	/**
	 * �}�b�s���O����
	 * 
	 * @param rs
	 * @return �A�����э�
	 * @throws SQLException
	 */
	protected YJRegion mapping(ResultSet rs) throws SQLException {
		YJRegion item = new YJRegion();

		item.setKAI_CODE(rs.getString("KAI_CODE"));
		item.setREGION_CODE(rs.getString("REGION_CODE"));
		item.setREGION_NAME(rs.getString("REGION_NAME"));
		item.setREGION_REMARK(rs.getString("REMARK"));
		item.setCOUNTRY_CODE(rs.getString("COUNTRY_CODE"));
		item.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
		item.setCOUNTRY_REMARK(rs.getString("COUNTRY_REMARK"));
		item.setINP_DATE(rs.getTimestamp("INP_DATE"));
		item.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		item.setPRG_ID(rs.getString("PRG_ID"));
		item.setUSR_ID(rs.getString("USR_ID"));

		return item;
	}
}
