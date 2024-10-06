package jp.co.ais.trans2.model.payment;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�����@�}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class PaymentMethodManagerImpl extends TModel implements PaymentMethodManager {

	/**
	 * �w������ɊY������x�����@����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������x�����@���
	 * @throws TException
	 */
	public List<PaymentMethod> get(PaymentMethodSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<PaymentMethod> list = new ArrayList<PaymentMethod>();

		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT hoh.*");

			sql.add(" FROM AP_HOH_MST hoh");

			sql.add(" WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("   AND hoh.KAI_CODE = ?", condition.getCompanyCode());
			}

			// �R�[�h
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("   AND hoh.HOH_HOH_CODE = ?", condition.getCode());
			}

			// �J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("   AND hoh.HOH_HOH_CODE >= ?", condition.getCodeFrom());
			}

			// �I���R�[�h
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("   AND hoh.HOH_HOH_CODE <= ?", condition.getCodeTo());
			}
			// �R�[�h�O����v
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("AND");
				sql.addLikeFront("  hoh.HOH_HOH_CODE ?", condition.getCodeLike());
			}

			// ���̂����܂�
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.add("AND");
				sql.addNLikeAmbi("  hoh.HOH_HOH_NAME ?", condition.getNameLike());
			}

			// �������̂����܂�
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.add("AND");
				sql.addNLikeAmbi("  hoh.HOH_HOH_NAME_K ?", condition.getNamekLike());
			}

			// 0:�Ј��x��
			if (condition.isUseEmployeePayment()) {
				sql.add("   AND hoh.HOH_SIH_KBN = 0 ");
			}

			// 1:�ЊO�x��
			if (condition.isUseExPayment()) {
				sql.add("   AND hoh.HOH_SIH_KBN = 1 ");
			}

			// �x�����@�R�[�h IN�w��
			if (!condition.getCodeList().isEmpty()) {
				sql.add("   AND HOH_HOH_CODE IN ?", condition.getCodeList());
			}

			// �x�����@�����R�[�h IN�w��
			if (!condition.getNotPaymentKindList().isEmpty()) {
				sql.add("   AND HOH_NAI_CODE NOT IN ?", condition.getNotPaymentKinds());
			}

			// �x�����@�����R�[�h IN�w��
			if (!condition.getPaymentKindList().isEmpty()) {
				sql.add("   AND HOH_NAI_CODE IN ?", condition.getPaymentKinds());
			}

			// �L������
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("   AND hoh.STR_DATE <= ?", condition.getValidTerm());
				sql.add("   AND hoh.END_DATE >= ?", condition.getValidTerm());
			}

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (hoh.HOH_INP_DATE > ?", condition.getLastUpdateDate());
				sql.adt("    OR hoh.UPD_DATE > ?", condition.getLastUpdateDate());
				sql.add(")");
			}

			sql.add(" ORDER BY hoh.HOH_HOH_CODE ");

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
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(PaymentMethodSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   AP_HOH_MST hoh ");
			sql.add(" WHERE  hoh.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (hoh.HOH_INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR hoh.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR hoh.HOH_INP_DATE IS NULL AND hoh.UPD_DATE IS NULL) ");
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
	 * �x�����@��o�^����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void entry(PaymentMethod bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("  INSERT INTO  AP_HOH_MST (");
			sql.add("  KAI_CODE ");
			sql.add(" ,HOH_HOH_CODE ");
			sql.add(" ,HOH_HOH_NAME ");
			sql.add(" ,HOH_HOH_NAME_K ");
			sql.add(" ,HOH_KMK_CODE ");
			sql.add(" ,HOH_HKM_CODE ");
			sql.add(" ,HOH_UKM_CODE ");
			sql.add(" ,HOH_DEP_CODE ");
			sql.add(" ,HOH_SIH_KBN ");
			sql.add(" ,HOH_NAI_CODE ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(" ,HOH_INP_DATE");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");

			sql.add(") VALUES (");
			sql.add("     ?", bean.getCompanyCode());
			sql.add("    ,?", bean.getCode());
			sql.add("    ,?", bean.getName());
			sql.add("    ,?", bean.getNamek());
			sql.add("    ,?", bean.getItemCode());
			sql.add("    ,?", bean.getSubItemCode());
			sql.add("    ,?", bean.getDetailItemCode());
			sql.add("    ,?", bean.getDepartmentCode());
			sql.add("    ,?", bean.getPaymentDivision());
			sql.add("    ,?", bean.getPaymentKind().value);
			sql.adt("    ,?", bean.getDateFrom());
			sql.adt("    ,?", bean.getDateTo());
			sql.adt("    ,?", getNow());
			sql.add("    ,?", getProgramCode());
			sql.add("    ,?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �x�����@���C������B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void modify(PaymentMethod bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    AP_HOH_MST");
			sql.add("SET");
			sql.add("     HOH_HOH_CODE = ?", bean.getCode());
			sql.add("    ,HOH_HOH_NAME = ?", bean.getName());
			sql.add("    ,HOH_HOH_NAME_K = ?", bean.getNamek());
			sql.add("    ,HOH_KMK_CODE = ?", bean.getItemCode());
			sql.add("    ,HOH_HKM_CODE = ?", bean.getSubItemCode());
			sql.add("    ,HOH_UKM_CODE = ?", bean.getDetailItemCode());
			sql.add("    ,HOH_DEP_CODE = ?", bean.getDepartmentCode());
			sql.add("    ,HOH_SIH_KBN = ?", bean.getPaymentDivision());
			sql.add("    ,HOH_NAI_CODE = ?", bean.getPaymentKind().value);
			sql.adt("    ,STR_DATE = ?", bean.getDateFrom());
			sql.adt("    ,END_DATE = ?", bean.getDateTo());
			sql.adt("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    HOH_HOH_CODE = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �x�����@���폜����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void delete(PaymentMethod bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("DELETE FROM");
			sql.add("    AP_HOH_MST ");
			sql.add("WHERE");
			sql.add("    KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("AND");
			sql.add("    HOH_HOH_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected PaymentMethod mapping(ResultSet rs) throws Exception {

		PaymentMethod bean = new PaymentMethod();

		// ��ЃR�[�h
		bean.setCompanyCode(rs.getString("KAI_CODE"));

		// �R�[�h
		bean.setCode(rs.getString("HOH_HOH_CODE"));

		// ����
		bean.setName(rs.getString("HOH_HOH_NAME"));

		// ��������
		bean.setNamek(rs.getString("HOH_HOH_NAME_K"));

		// �ȖڃR�[�h
		bean.setItemCode(rs.getString("HOH_KMK_CODE"));

		// �⏕�ȖڃR�[�h
		bean.setSubItemCode(rs.getString("HOH_HKM_CODE"));

		// ����ȖڃR�[�h
		bean.setDetailItemCode(rs.getString("HOH_UKM_CODE"));

		// ����R�[�h
		bean.setDepartmentCode(rs.getString("HOH_DEP_CODE"));

		// �x���Ώۋ敪
		bean.setPaymentDivision(rs.getInt("HOH_SIH_KBN"));

		// �x�������R�[�h
		bean.setPaymentKind(PaymentKind.getPaymentKind(rs.getString("HOH_NAI_CODE")));

		// �L�����ԊJ�n
		bean.setDateFrom(rs.getDate("STR_DATE"));

		// �L�����ԏI��
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}

	/**
	 * �G�N�Z���擾
	 * 
	 * @param condition ��������
	 * @return byte ��������
	 * @throws TException
	 */
	public byte[] getExcel(PaymentMethodSearchCondition condition) throws TException {

		try {

			List<PaymentMethod> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PaymentMethodExcel exl = new PaymentMethodExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
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
}
