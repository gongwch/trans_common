package jp.co.ais.trans2.model.tax;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.define.TransUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ����ŏ��̎����B
 * 
 * @author AIS
 */
public class ConsumptionTaxManagerImpl extends TModel implements ConsumptionTaxManager {

	public List<ConsumptionTax> get(ConsumptionTaxSearchCondition condition) throws TException {

		// �C���{�C�X�g�p���邩�ǂ���
		boolean isInvoice = initInvoiceFlg(condition.getCompanyCode());

		Connection conn = DBUtil.getConnection();

		List<ConsumptionTax> list = new ArrayList<ConsumptionTax>();

		try {
			VCreator sql = new VCreator();

			sql.add(" SELECT ");
			sql.add("  zei.KAI_CODE ");
			sql.add(" ,zei.ZEI_CODE ");
			sql.add(" ,zei.ZEI_NAME ");
			sql.add(" ,zei.ZEI_NAME_S ");
			sql.add(" ,zei.ZEI_NAME_K ");
			sql.add(" ,zei.US_KBN ");
			sql.add(" ,zei.ZEI_RATE ");
			sql.add(" ,zei.SZEI_KEI_KBN ");
			sql.add(" ,zei.STR_DATE ");
			sql.add(" ,zei.END_DATE ");

			if (isInvoice) {
				sql.add(" ,zei.NO_INV_REG_FLG ");
				sql.add(" ,zei.KEKA_SOTI_RATE ");
			}

			sql.add(" FROM ");
			sql.add(" SZEI_MST zei ");
			sql.add(" WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND	zei.KAI_CODE = ?", condition.getCompanyCode());
			}

			// �R�[�h
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND	zei.ZEI_CODE = ?", condition.getCode());
			}

			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND  zei.ZEI_CODE  ?", condition.getCodeLike());
			}

			// ���̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi(" AND  zei.ZEI_NAME_S  ?", condition.getNamesLike());
			}

			// �������̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi("AND  zei.ZEI_NAME_K  ?", condition.getNamekLike());
			}

			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND	zei.ZEI_CODE >= ?", condition.getCodeFrom());
			}

			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND	zei.ZEI_CODE <= ?", condition.getCodeTo());
			}
			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add(" AND	zei.STR_DATE <= ?", condition.getValidTerm());
				sql.add(" AND	zei.END_DATE >= ?", condition.getValidTerm());
			}

			if (condition.isPurcharseOnliy()) {
				// �d���̂ݎ擾
				sql.add("  AND zei.US_KBN = 2 ");
			} else {
				// �ŋ敪
				if (condition.isHasSales() || condition.isHasPurcharse()) {
					sql.add("  AND (zei.US_KBN = 0 ");

					// ����ې�
					if (condition.isHasSales()) {
						sql.add("  OR zei.US_KBN = 1 ");
					}

					// �d���ې�
					if (condition.isHasPurcharse()) {
						sql.add("  OR zei.US_KBN = 2 ");
					}
					sql.add("  ) ");
				}
			}

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (zei.INP_DATE > ?", condition.getLastUpdateDate());
				sql.adt(" OR	zei.UPD_DATE > ? )", condition.getLastUpdateDate());
			}

			if (isInvoice) {
				// invoice �K�i���������s���Ǝ�ON�̏ꍇ�o��
				if (condition.isNoInvRegFlg()) {
					sql.add(" AND zei.NO_INV_REG_FLG = 1 ");
				}
			}

			sql.add(" ORDER BY ");
			sql.add(" zei.ZEI_CODE ");

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
	public void entry(ConsumptionTax bean) throws TException {

		// �C���{�C�X�g�p���邩�ǂ���
		boolean isInvoice = initInvoiceFlg(bean.getCompanyCode());

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("INSERT INTO  SZEI_MST (");
			sql.add("  KAI_CODE ");
			sql.add(" ,ZEI_CODE ");
			sql.add(" ,ZEI_NAME ");
			sql.add(" ,ZEI_NAME_S ");
			sql.add(" ,ZEI_NAME_K ");
			sql.add(" ,US_KBN ");
			sql.add(" ,SZEI_KEI_KBN ");
			sql.add(" ,ZEI_RATE ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(" ,INP_DATE ");
			sql.add(" ,UPD_DATE ");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");

			if (isInvoice) {

				sql.add(" ,NO_INV_REG_FLG ");
				sql.add(" ,KEKA_SOTI_RATE ");
			}

			sql.add(") VALUES (");
			sql.add("     ? ", bean.getCompanyCode());
			sql.add("    ,? ", bean.getCode());
			sql.add("    ,? ", bean.getName());
			sql.add("    ,? ", bean.getNames());
			sql.add("    ,? ", bean.getNamek());
			sql.add("    ,? ", bean.getTaxType().value);

			if (bean.isTaxConsumption() == true) {
				sql.add("    ,? ", bean.getOdr());
			} else if (bean.isTaxConsumption() == false) {
				sql.add(" ,null ");
			}
			sql.add("    ,? ", bean.getRate());
			sql.add("    ,? ", bean.getDateFrom());
			sql.add("    ,? ", bean.getDateTo());
			sql.adt("    ,? ", getNow());
			sql.add("    ,NULL ");
			sql.add("    ,? ", getProgramCode());
			sql.add("    ,? ", getUserCode());

			if (isInvoice) {
				sql.add(", ? ", BooleanUtil.toInt(bean.isNO_INV_REG_FLG()));
				sql.add(", ? ", bean.getKEKA_SOTI_RATE());
			}

			sql.add(")");

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
	public void delete(ConsumptionTax bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.EMPLOYEE);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setEmployeeCode(bean.getCode());

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    SZEI_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("AND");
			sql.add("    ZEI_CODE = ? ", bean.getCode());

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
	public void modify(ConsumptionTax bean, boolean flg) throws TException {

		// �C���{�C�X�g�p���邩�ǂ���
		boolean isInvoice = initInvoiceFlg(bean.getCompanyCode());

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add("UPDATE  ");
			sql.add(" SZEI_MST");
			sql.add("SET");
			sql.add(" ZEI_CODE = ? ", bean.getCode());
			sql.add(" ,ZEI_NAME = ? ", bean.getName());
			sql.add(" ,ZEI_NAME_S = ? ", bean.getNames());
			sql.add(" ,ZEI_NAME_K = ? ", bean.getNamek());
			sql.add(" ,US_KBN = ? ", bean.getTaxType().value);
			if (bean.isTaxConsumption() == true) {
				sql.add(" ,SZEI_KEI_KBN =  ? ", bean.getOdr());
			} else if (bean.isTaxConsumption() == false) {
				sql.add(" ,SZEI_KEI_KBN = null ");
			}
			sql.add(" ,ZEI_RATE = ? ", bean.getRate());
			sql.add(" ,STR_DATE = ? ", bean.getDateFrom());
			sql.add(" ,END_DATE = ? ", bean.getDateTo());
			sql.adt(" ,UPD_DATE = ? ", getNow());
			sql.add(" ,PRG_ID = ? ", getProgramCode());
			sql.add(" ,USR_ID = ? ", getUserCode());

			if (isInvoice) {
				sql.add(" ,NO_INV_REG_FLG = ? ", BooleanUtil.toInt(bean.isNO_INV_REG_FLG()));
				sql.add(" ,KEKA_SOTI_RATE = ? ", bean.getKEKA_SOTI_RATE());
			}

			sql.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
			sql.add(" AND ZEI_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

			if (flg) {
				// �C���{�C�X�p�����}�X�^�̏���ŃR�[�h��UPDATE
				sql = new VCreator();
				sql.add("");
				sql.add("UPDATE  ");
				sql.add(" TRI_MST");
				sql.add("SET");
				sql.add(" NO_INV_REG_ZEI_CODE = NULL ");

				sql.add(" WHERE KAI_CODE = ? ", bean.getCompanyCode());
				sql.add(" AND NO_INV_REG_ZEI_CODE = ? ", bean.getCode());

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �G�N�Z���擾
	 * 
	 * @param condition ��������
	 * @return byte ��������
	 * @throws TException
	 */
	public byte[] getExcel(ConsumptionTaxSearchCondition condition) throws TException {
		try {

			List<ConsumptionTax> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			ConsumptionTaxExcel exl = new ConsumptionTaxExcel(getUser().getLanguage(), condition);

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(ConsumptionTaxSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();

			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   SZEI_MST zei ");
			sql.add(" WHERE  zei.KAI_CODE = ? ", condition.getCompanyCode());// ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (zei.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR zei.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR zei.INP_DATE IS NULL AND zei.UPD_DATE IS NULL) ");
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
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return ConsumptionTax
	 * @throws Exception
	 */
	protected ConsumptionTax mapping(ResultSet rs) throws Exception {

		ConsumptionTax bean = new ConsumptionTax();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("ZEI_CODE"));
		bean.setName(rs.getString("ZEI_NAME"));
		bean.setNames(rs.getString("ZEI_NAME_S"));
		bean.setNamek(rs.getString("ZEI_NAME_K"));
		bean.setTaxType(TaxType.get(rs.getInt("US_KBN")));
		bean.setTaxConsumption(rs.getInt("SZEI_KEI_KBN") != 0);
		bean.setOdr(rs.getString("SZEI_KEI_KBN"));
		bean.setRate(rs.getBigDecimal("ZEI_RATE"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		// �C���{�C�X�g�p���邩�ǂ���
		boolean isInvoice = initInvoiceFlg(bean.getCompanyCode());
		if (isInvoice) {
			bean.setNO_INV_REG_FLG(TransUtil.TRUE == rs.getInt("NO_INV_REG_FLG"));
			bean.setKEKA_SOTI_RATE(rs.getBigDecimal("KEKA_SOTI_RATE"));
		}

		return bean;

	}

	/**
	 * SQL creator
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
	 * invoice�g�p���邩�ǂ���
	 * 
	 * @param cmpCode
	 * @return ��Џ��
	 * @throws TException
	 */
	protected boolean initInvoiceFlg(String cmpCode) throws TException {
		Company company = new Company();

		if (getCompany() == null) {
			CompanyManager dao = (CompanyManager) getComponent(CompanyManager.class);
			company = dao.get(cmpCode);
		} else {
			company = getCompany();
		}

		return company.isCMP_INV_CHK_FLG();
	}

}
