package jp.co.ais.trans2.model.mlit.item;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �A�����уA�C�e���}�l�[�W�������N���X
 */
public class YJItemManagerImpl extends TModel implements YJItemManager {

	/**
	 * �A�����уA�C�e�����擾����
	 * 
	 * @return �A�����уA�C�e�����X�g
	 * @throws TException
	 */
	public List<YJItem> getItems(YJItemSearchCondition condition) throws TException {

		List<YJItem> list = new ArrayList<YJItem>();

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("   item.KAI_CODE ");
			sql.add("  ,item.ITEM_CODE ");
			sql.add("  ,item.ITEM_NAME ");
			sql.add("  ,NULL ITEM_SUB_CODE ");
			sql.add("  ,NULL ITEM_SUB_NAME ");
			sql.add("  ,item.REMARKS ");
			sql.add("  ,NULL SUB_REMARK ");
			sql.add("  ,item.INP_DATE ");
			sql.add("  ,item.UPD_DATE ");
			sql.add("  ,item.PRG_ID ");
			sql.add("  ,item.USR_ID ");
			sql.add("  ,NULL SUB_INP_DATE ");
			sql.add("  ,NULL SUB_UPD_DATE ");
			sql.add("  ,NULL SUB_PRG_ID ");
			sql.add("  ,NULL SUB_PRG_ID ");
			sql.add(" FROM YJ_ITEM_MST item ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));
			sql.add(" ORDER BY ");
			sql.add("   item.KAI_CODE ");
			sql.add("  ,item.ITEM_CODE ");

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
	 * �A�����уA�C�e���}�X�^�̓o�^
	 * 
	 * @param bean �A�����уA�C�e���}�X�^
	 * @throws TException
	 */
	public void entry(YJItem bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add(" INSERT INTO YJ_ITEM_MST ( ");
		sql.add("   KAI_CODE ");
		sql.add("  ,ITEM_CODE ");
		sql.add("  ,ITEM_NAME ");
		sql.add("  ,REMARKS ");
		sql.add("  ,INP_DATE ");
		sql.add("  ,UPD_DATE ");
		sql.add("  ,PRG_ID ");
		sql.add("  ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("        ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("       ,? ", bean.getITEM_CODE()); // �R�[�h
		sql.add("       ,? ", bean.getITEM_NAME()); // ����
		sql.add("       ,? ", bean.getREMARK()); // Remark
		sql.addYMDHMS(" ,? ", bean.getINP_DATE()); // �o�^���t
		sql.addYMDHMS(" ,? ", bean.getUPD_DATE()); // �X�V���t
		sql.add("       ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("       ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" ) ");

		DBUtil.execute(sql);

	}

	/**
	 * �A�����уA�C�e���}�X�^�̍폜
	 * 
	 * @param bean �A�����уA�C�e���}�X�^
	 * @throws TException
	 */
	public void delete(YJItem bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add("DELETE FROM YJ_ITEM_MST ");
		sql.add(" WHERE KAI_CODE  = ? ", bean.getKAI_CODE());
		sql.add("   AND ITEM_CODE = ? ", bean.getITEM_CODE());

		DBUtil.execute(sql);

	}

	/**
	 * �A�����уA�C�e���}�X�^�̓o�^
	 * 
	 * @param bean �A�����уA�C�e���}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJItem entryMaster(YJItem bean) throws TException {

		// �폜
		delete(bean);

		// ���ݒ�
		if (Util.isNullOrEmpty(bean.getINP_DATE())) {
			bean.setINP_DATE(getNow());
		} else {
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

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
	public byte[] getExcel(YJItemSearchCondition condition) throws TException {

		try {
			// �f�[�^�𒊏o
			List<YJItem> list = getItems(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			MLITItemExcel excel = new MLITItemExcel(getUser().getLanguage(), getCompany());
			return excel.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �A�����уT�u�A�C�e�����擾����
	 * 
	 * @return �A�����уT�u�A�C�e�����X�g
	 * @throws TException
	 */
	public List<YJItem> getSubItems(YJItemSearchCondition condition) throws TException {

		List<YJItem> list = new ArrayList<YJItem>();

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("   item.KAI_CODE ");
			sql.add("  ,item.ITEM_CODE ");
			sql.add("  ,item.ITEM_NAME ");
			sql.add("  ,sub.ITEM_SUB_CODE ");
			sql.add("  ,sub.ITEM_SUB_NAME ");
			sql.add("  ,item.REMARKS ");
			sql.add("  ,sub.REMARKS SUB_REMARK ");
			sql.add("  ,item.INP_DATE ");
			sql.add("  ,item.UPD_DATE ");
			sql.add("  ,item.PRG_ID ");
			sql.add("  ,item.USR_ID ");
			sql.add("  ,sub.INP_DATE SUB_INP_DATE ");
			sql.add("  ,sub.UPD_DATE SUB_UPD_DATE ");
			sql.add("  ,sub.PRG_ID SUB_PRG_ID ");
			sql.add("  ,sub.USR_ID SUB_PRG_ID ");
			sql.add("FROM YJ_ITEM_MST item ");
			sql.add("LEFT OUTER JOIN YJ_ITEM_SUB_MST sub ");
			sql.add("  ON item.KAI_CODE = sub.KAI_CODE ");
			sql.add(" AND item.ITEM_CODE = sub.ITEM_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(createSelectWhereSql(condition));
			sql.add(" ORDER BY ");
			sql.add("   item.KAI_CODE ");
			sql.add("  ,item.ITEM_CODE ");
			sql.add("  ,sub.ITEM_SUB_CODE ");

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
	 * �A�����уT�u�A�C�e���}�X�^�̓o�^
	 * 
	 * @param bean �A�����уT�u�A�C�e���}�X�^
	 * @throws TException
	 */
	public void entrySub(YJItem bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add(" INSERT INTO YJ_ITEM_SUB_MST ( ");
		sql.add("   KAI_CODE ");
		sql.add("  ,ITEM_CODE ");
		sql.add("  ,ITEM_SUB_CODE ");
		sql.add("  ,ITEM_SUB_NAME ");
		sql.add("  ,REMARKS ");
		sql.add("  ,INP_DATE ");
		sql.add("  ,UPD_DATE ");
		sql.add("  ,PRG_ID ");
		sql.add("  ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("        ? ", bean.getKAI_CODE()); // ��ЃR�[�h
		sql.add("       ,? ", bean.getITEM_CODE()); // �A�C�e���R�[�h
		sql.add("       ,? ", bean.getITEM_SUB_CODE()); // �T�u�A�C�e���R�[�h
		sql.add("       ,? ", bean.getITEM_SUB_NAME()); // �T�u�A�C�e������
		sql.add("       ,? ", bean.getSUB_REMARK()); // Remark
		sql.addYMDHMS(" ,? ", bean.getINP_DATE()); // �o�^���t
		sql.addYMDHMS(" ,? ", bean.getUPD_DATE()); // �X�V���t
		sql.add("       ,? ", bean.getPRG_ID()); // �v���O����ID
		sql.add("       ,? ", bean.getUSR_ID()); // ���[�U�[ID
		sql.add(" ) ");

		DBUtil.execute(sql);

	}

	/**
	 * �A�����уT�u�A�C�e���}�X�^�̍폜
	 * 
	 * @param bean �A�����уT�u�A�C�e���}�X�^
	 * @throws TException
	 */
	public void deleteSub(YJItem bean) throws TException {

		SQLCreator sql = new SQLCreator();

		sql.add("DELETE FROM YJ_ITEM_SUB_MST ");
		sql.add(" WHERE KAI_CODE      = ? ", bean.getKAI_CODE());
		sql.add("   AND ITEM_CODE     = ? ", bean.getITEM_CODE());
		sql.add("   AND ITEM_SUB_CODE = ? ", bean.getITEM_SUB_CODE());

		DBUtil.execute(sql);

	}

	/**
	 * �A�����уT�u�A�C�e���}�X�^�̓o�^
	 * 
	 * @param bean �A�����уT�u�A�C�e���}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJItem entrySubMaster(YJItem bean) throws TException {

		// �폜
		deleteSub(bean);

		// ���ݒ�
		if (Util.isNullOrEmpty(bean.getINP_DATE())) {
			bean.setINP_DATE(getNow());
		} else {
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		// �o�^
		entrySub(bean);

		return bean;
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcelSub(YJItemSearchCondition condition) throws TException {

		try {
			// �f�[�^�𒊏o
			List<YJItem> list = getSubItems(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			MLITSubItemExcel excel = new MLITSubItemExcel(getUser().getLanguage(), getCompany());
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
	protected String createSelectWhereSql(YJItemSearchCondition condition) {
		SQLCreator sql = new SQLCreator();

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add(" AND item.KAI_CODE  = ?", condition.getCompanyCode());
		}

		// �A�C�e���R�[�h
		if (!Util.isNullOrEmpty(condition.getItemCode())) {
			sql.add(" AND item.ITEM_CODE  = ? ", condition.getItemCode());
		}

		// �J�n�A�C�e���R�[�h
		if (!Util.isNullOrEmpty(condition.getItemCodeFrom())) {
			sql.add(" AND item.ITEM_CODE >= ? ", condition.getItemCodeFrom());
		}

		// �I���A�C�e���R�[�h
		if (!Util.isNullOrEmpty(condition.getItemCodeTo())) {
			sql.add(" AND item.ITEM_CODE <= ? ", condition.getItemCodeTo());
		}

		// �A�C�e���R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getItemCodeLike())) {
			sql.addLikeFront(" AND item.ITEM_CODE ? ", condition.getItemCodeLike());
		}

		// �A�C�e�����̂����܂�
		if (!Util.isNullOrEmpty(condition.getItemNameLike())) {
			sql.addNLikeAmbi(" AND item.ITEM_NAME ? ", condition.getItemNameLike());
		}

		// �T�u�A�C�e���R�[�h
		if (!Util.isNullOrEmpty(condition.getSubItemCode())) {
			sql.add(" AND sub.ITEM_SUB_CODE  = ? ", condition.getSubItemCode());
		}

		// �J�n�T�u�A�C�e���R�[�h
		if (!Util.isNullOrEmpty(condition.getSubItemCodeFrom())) {
			sql.add(" AND sub.ITEM_SUB_CODE >= ? ", condition.getSubItemCodeFrom());
		}

		// �I���T�u�A�C�e���R�[�h
		if (!Util.isNullOrEmpty(condition.getSubItemCodeTo())) {
			sql.add(" AND sub.ITEM_SUB_CODE <= ? ", condition.getSubItemCodeTo());
		}

		// �T�u�A�C�e���R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getSubItemCodeLike())) {
			sql.addLikeFront(" AND sub.ITEM_SUB_CODE ? ", condition.getSubItemCodeLike());
		}

		// �T�u�A�C�e�����̂����܂�
		if (!Util.isNullOrEmpty(condition.getSubItemNameLike())) {
			sql.addNLikeAmbi(" AND sub.ITEM_SUB_NAME ? ", condition.getSubItemNameLike());
		}
		return sql.toSQL();
	}

	/**
	 * �}�b�s���O����
	 * 
	 * @param rs
	 * @return �A�����уA�C�e��
	 * @throws SQLException
	 */
	protected YJItem mapping(ResultSet rs) throws SQLException {
		YJItem item = new YJItem();

		item.setKAI_CODE(rs.getString("KAI_CODE"));
		item.setITEM_CODE(rs.getString("ITEM_CODE"));
		item.setITEM_NAME(rs.getString("ITEM_NAME"));
		item.setREMARK(rs.getString("REMARKS"));
		item.setITEM_SUB_CODE(rs.getString("ITEM_SUB_CODE"));
		item.setITEM_SUB_NAME(rs.getString("ITEM_SUB_NAME"));
		item.setSUB_REMARK(rs.getString("SUB_REMARK"));
		item.setINP_DATE(rs.getTimestamp("INP_DATE"));
		item.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		item.setPRG_ID(rs.getString("PRG_ID"));
		item.setUSR_ID(rs.getString("USR_ID"));

		return item;
	}
}
