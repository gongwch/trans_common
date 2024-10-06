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
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ��s�����}�l�[�W���̎����N���X�ł��B
 * 
 * @author AIS
 */
public class BankAccountManagerImpl extends TModel implements BankAccountManager {

	/**
	 * �w������ɊY�������s��������Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�������
	 * @throws TException
	 */
	public List<BankAccount> get(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<BankAccount> list = new ArrayList<BankAccount>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY cbk.CBK_CBK_CODE ");

			// ��s�����R�[�h�ŏ��l�擾�敪(MySQL�p)
			if (condition.isMinimumOnly() && DBUtil.isUseMySQL) {
				sql.add(" LIMIT 1 ");
			}

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
	 * �w������ɊY�������s��������Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�������
	 * @throws TException
	 */
	public List<BankAccount> getRef(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<BankAccount> list = new ArrayList<BankAccount>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY  ");
			sql.add("   cbk.CBK_CBK_CODE ");
			sql.add("  ,CASE WHEN cbk.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // �����R�[�h�̏ꍇ�Ƀ��O�C����ЗD��
			sql.add("  ,cbk.KAI_CODE ");

			// ��s�����R�[�h�ŏ��l�擾�敪(MySQL�p)
			if (condition.isMinimumOnly() && DBUtil.isUseMySQL) {
				sql.add(" LIMIT 1 ");
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				BankAccount bean = mapping(rs);

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
	 * @return SQL
	 */
	protected VCreator getSQL(BankAccountSearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("SELECT");
		sql.add("	cbk.KAI_CODE,");
		sql.add("	cbk.CBK_CBK_CODE,");
		sql.add("	cbk.CBK_NAME,");
		sql.add("	cbk.CUR_CODE,");
		sql.add("	cur.CUR_NAME,");
		sql.add("	cur.CUR_NAME_S,");
		sql.add("	cur.CUR_NAME_K,");
		sql.add("	cur.DEC_KETA,");
		sql.add("	cbk.CBK_BNK_CODE,");
		sql.add("	cbk.CBK_STN_CODE,");
		sql.add("	cbk.CBK_IRAI_EMP_CODE,");
		sql.add("	cbk.CBK_IRAI_NAME,");
		sql.add("   cbk.CBK_IRAI_NAME_J,");
		sql.add("	cbk.CBK_IRAI_NAME_E,");
		sql.add("	cbk.CBK_YKN_KBN,");
		sql.add("	cbk.CBK_YKN_NO,");
		sql.add("	cbk.CBK_OUT_FB_KBN,");
		sql.add("	cbk.CBK_EMP_FB_KBN,");
		sql.add("	cbk.CBK_DEP_CODE,");
		sql.add("	dep.DEP_NAME,"); // ���喼��
		sql.add("	dep.DEP_NAME_S,"); // ���嗪��
		sql.add("	dep.DEP_NAME_K,"); // ���匟������
		sql.add("	cbk.CBK_KMK_CODE,");
		sql.add("	kmk.KMK_NAME,"); // �Ȗږ���
		sql.add("	kmk.KMK_NAME_S,"); // �Ȗڗ���
		sql.add("	kmk.KMK_NAME_K,"); // �Ȗڌ�������
		sql.add("	cbk.CBK_HKM_CODE,");
		sql.add("	hkm.HKM_NAME,"); // �⏕�Ȗږ���
		sql.add("	hkm.HKM_NAME_S,"); // �⏕�Ȗڗ���
		sql.add("	hkm.HKM_NAME_K,"); // �⏕�Ȗڌ�������
		sql.add("	cbk.CBK_UKM_CODE,");
		sql.add("	ukm.UKM_NAME,"); // ����Ȗږ���
		sql.add("	ukm.UKM_NAME_S,"); // ����Ȗڗ���
		sql.add("	ukm.UKM_NAME_K,"); // ����Ȗڌ�������
		sql.add("	cbk.IBAN_NO,"); // IBAN�R�[�h
		sql.add("	cbk.BANK_IDF,"); // ��s���ʎq
		sql.add("	cbk.ACCOUNT_IDF,"); // �������ʎq
		sql.add("	cbk.SWIFT_CODE,"); // SWIFT�R�[�h
		sql.add("	cbk.CBK_COU_CODE,"); // BANK COUNTRY
		sql.add("	cbk.BNK_ADR_1,"); // �Z��1
		sql.add("	cbk.BNK_ADR_2,"); // �Z��2
		sql.add("	cbk.STR_DATE,");
		sql.add("	cbk.END_DATE,");
		sql.add("	bnk.BNK_NAME_S,");
		sql.add("	bnk.BNK_KANA, ");
		sql.add("	bnk.BNK_NAME_K, ");
		sql.add("	bnk.BNK_STN_NAME_S, ");
		sql.add("	bnk.BNK_STN_KANA, ");
		sql.add("	bnk.BNK_STN_NAME_K, ");
		sql.add("	cbk.CBK_BANK_NAME_E,"); // ��s���i�p���j
		sql.add("	cbk.CBK_STN_NAME_E,"); // �x�X���i�p���j
		sql.add("	cbk.BNK_ADR_1_E,"); // �Z��1�i�p���j
		sql.add("	cbk.BNK_ADR_2_E "); // �Z��2�i�p���j
		sql.add(" FROM AP_CBK_MST cbk ");
		sql.add("LEFT OUTER JOIN BNK_MST bnk ON cbk.CBK_BNK_CODE = bnk.BNK_CODE ");
		sql.add("	                     AND    cbk.CBK_STN_CODE = bnk.BNK_STN_CODE ");
		sql.add("LEFT OUTER JOIN BMN_MST dep ON dep.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    dep.DEP_CODE = cbk.CBK_DEP_CODE");
		sql.add("LEFT OUTER JOIN KMK_MST kmk ON kmk.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    kmk.KMK_CODE = cbk.CBK_KMK_CODE");
		sql.add("LEFT OUTER JOIN HKM_MST hkm ON hkm.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    hkm.KMK_CODE = cbk.CBK_KMK_CODE");
		sql.add("	                     AND    hkm.HKM_CODE = cbk.CBK_HKM_CODE");
		sql.add("LEFT OUTER JOIN UKM_MST ukm ON ukm.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    ukm.KMK_CODE = cbk.CBK_KMK_CODE");
		sql.add("	                     AND    ukm.HKM_CODE = cbk.CBK_HKM_CODE");
		sql.add("	                     AND    ukm.UKM_CODE = cbk.CBK_UKM_CODE");
		sql.add("LEFT OUTER JOIN CUR_MST cur ON cur.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    cur.CUR_CODE = cbk.CUR_CODE");
		sql.add(" WHERE 1 = 1 ");

		// ��ЃR�[�h
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("   AND cbk.KAI_CODE = ?", condition.getCompanyCode());
		}

		// �R�[�h
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("   AND cbk.CBK_CBK_CODE = ?", condition.getCode());
		}

		// �J�n�R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("   AND cbk.CBK_CBK_CODE >= ?", condition.getCodeFrom());
		}

		// �I���R�[�h
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("   AND cbk.CBK_CBK_CODE <= ?", condition.getCodeTo());
		}

		// �R�[�h�O����v
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("   AND cbk.CBK_CBK_CODE ?", condition.getCodeLike());
		}

		// ��s���i��s�� + �x�X���j�����܂�
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("   AND CONCAT( NVL(bnk.BNK_NAME_S,''), CONCAT(' ', NVL(bnk.BNK_STN_NAME_S,''))) ?",
				condition.getNameLike());
		}

		// �����ԍ������܂�
		if (!Util.isNullOrEmpty(condition.getAccountNokLike())) {
			sql.addLikeAmbi("   AND cbk.CBK_YKN_NO ?", condition.getAccountNokLike());
		}

		// �����ԍ�
		if (!Util.isNullOrEmpty(condition.getAccountNo())) {
			sql.add("   AND cbk.CBK_YKN_NO = ?", condition.getAccountNo());
		}

		// �Ј��e�a�敪
		if (condition.isUseEmployeePayment()) {
			sql.add("   AND cbk.CBK_EMP_FB_KBN = 1 ");
		}

		// �ЊO�e�a�敪
		if (condition.isUseExPayment()) {
			sql.add("   AND cbk.CBK_OUT_FB_KBN = 1 ");
		}

		// �L������
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("   AND cbk.STR_DATE <= ?", condition.getValidTerm());
			sql.add("   AND cbk.END_DATE >= ?", condition.getValidTerm());
		}

		// ��s�R�[�h
		if (!Util.isNullOrEmpty(condition.getBankCode())) {
			sql.add("   AND cbk.CBK_BNK_CODE = ?", condition.getBankCode());
		}

		// �x�X�R�[�h
		if (!Util.isNullOrEmpty(condition.getBranchCode())) {
			sql.add("   AND cbk.CBK_STN_CODE = ?", condition.getBranchCode());
		}

		// �a�����
		if (condition.getDepositKind() != null) {
			sql.add("   AND cbk.CBK_YKN_KBN = ?", condition.getDepositKind().value);
		}

		// �����ԍ�(10��0����)
		if (!Util.isNullOrEmpty(condition.getAccountNoFillZero())) {
			sql.add(
				"   AND SUBSTR(CONCAT('0000000000', NVL(cbk.CBK_YKN_NO,'')), LENGTH(NVL(cbk.CBK_YKN_NO,'')) + 1, 10) = ?",
				StringUtil.rightBX("0000000000" + condition.getAccountNoFillZero(), 10));
		}

		// �ʉ݃R�[�h
		if (!Util.isNullOrEmpty(condition.getCurrencyCode())) {
			sql.add("   AND cbk.CUR_CODE = ?", condition.getCurrencyCode());
		}

		// ��s�����R�[�h�ŏ��l�擾�敪(Oracle�p)
		if (condition.isMinimumOnly() && !DBUtil.isUseMySQL) {
			sql.add("   AND ROWNUM = 1 ");
		}

		// ��t�ԍ�
		if (!Util.isNullOrEmpty(condition.getAcceptNo())) {
			sql.add(" AND EXISTS (");
			sql.add("   SELECT 1 FROM AP_DEN_DAT den");
			sql.add("   WHERE den.KAI_CODE = cbk.KAI_CODE ");
			sql.add("   AND   den.DEN_CBK_CODE = cbk.CBK_CBK_CODE ");
			sql.add("   AND   den.DEN_UTK_NO = ?) ", condition.getAcceptNo());
		}

		// �ŏI�X�V����
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (cbk.CBK_INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR cbk.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

		// �v�㕔��
		if (!Util.isNullOrEmpty(condition.getDeptCode())) {
			sql.add("   AND   cbk.CBK_DEP_CODE = ? ", condition.getDeptCode());
		}

		// ��s�����R�[�h
		if (!condition.getCodeList().isEmpty()) {
			sql.add(" AND	cbk.CBK_CBK_CODE IN " + SQLUtil.getInStatement(condition.getCodeList()));
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
	public boolean hasDelete(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   AP_CBK_MST cbk ");
			sql.add(" WHERE  cbk.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �ŏI�X�V����
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cbk.CBK_INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cbk.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cbk.CBK_INP_DATE IS NULL AND cbk.UPD_DATE IS NULL) ");
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
	 * �w������ɊY�������s�����R�[�h��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�����R�[�h
	 * @throws TException
	 */
	public BankAccount getBankAccount(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		BankAccount bankAccount = null;

		try {

			VCreator sql = new VCreator();

			sql.add("SELECT");
			sql.add("	cbk.KAI_CODE,");
			sql.add("	cbk.CBK_CBK_CODE,");
			sql.add("	cbk.CBK_NAME,");
			sql.add("	cbk.CUR_CODE,");
			sql.add("	cur.CUR_NAME,");
			sql.add("	cur.CUR_NAME_S,");
			sql.add("	cur.CUR_NAME_K,");
			sql.add("	cur.DEC_KETA,");
			sql.add("	cbk.CBK_BNK_CODE,");
			sql.add("	cbk.CBK_STN_CODE,");
			sql.add("	cbk.CBK_IRAI_EMP_CODE,");
			sql.add("	cbk.CBK_IRAI_NAME,");
			sql.add("   cbk.CBK_IRAI_NAME_J,");
			sql.add("	cbk.CBK_IRAI_NAME_E,");
			sql.add("	cbk.CBK_YKN_KBN,");
			sql.add("	cbk.CBK_YKN_NO,");
			sql.add("	cbk.CBK_OUT_FB_KBN,");
			sql.add("	cbk.CBK_EMP_FB_KBN,");
			sql.add("	cbk.CBK_DEP_CODE,");
			sql.add("	dep.DEP_NAME,"); // ���喼��
			sql.add("	dep.DEP_NAME_S,"); // ���嗪��
			sql.add("	dep.DEP_NAME_K,"); // ���匟������
			sql.add("	cbk.CBK_KMK_CODE,");
			sql.add("	kmk.KMK_NAME,"); // �Ȗږ���
			sql.add("	kmk.KMK_NAME_S,"); // �Ȗڗ���
			sql.add("	kmk.KMK_NAME_K,"); // �Ȗڌ�������
			sql.add("	cbk.CBK_HKM_CODE,");
			sql.add("	hkm.HKM_NAME,"); // �⏕�Ȗږ���
			sql.add("	hkm.HKM_NAME_S,"); // �⏕�Ȗڗ���
			sql.add("	hkm.HKM_NAME_K,"); // �⏕�Ȗڌ�������
			sql.add("	cbk.CBK_UKM_CODE,");
			sql.add("	ukm.UKM_NAME,"); // ����Ȗږ���
			sql.add("	ukm.UKM_NAME_S,"); // ����Ȗڗ���
			sql.add("	ukm.UKM_NAME_K,"); // ����Ȗڌ�������
			sql.add("	cbk.IBAN_NO,"); // IBAN�R�[�h
			sql.add("	cbk.BANK_IDF,"); // ��s���ʎq
			sql.add("	cbk.ACCOUNT_IDF,"); // �������ʎq
			sql.add("	cbk.SWIFT_CODE,"); // SWIFT�R�[�h
			sql.add("	cbk.CBK_COU_CODE,"); // BANK COUNTRY
			sql.add("	cbk.BNK_ADR_1,"); // �Z��1
			sql.add("	cbk.BNK_ADR_2,"); // �Z��2
			sql.add("	cbk.STR_DATE,");
			sql.add("	cbk.END_DATE,");
			sql.add("	bnk.BNK_NAME_S,");
			sql.add("	bnk.BNK_KANA, ");
			sql.add("	bnk.BNK_NAME_K, ");
			sql.add("	bnk.BNK_STN_NAME_S, ");
			sql.add("	bnk.BNK_STN_KANA, ");
			sql.add("	bnk.BNK_STN_NAME_K, ");
			sql.add("	cbk.CBK_BANK_NAME_E,"); // ��s���i�p���j
			sql.add("	cbk.CBK_STN_NAME_E,"); // �x�X���i�p���j
			sql.add("	cbk.BNK_ADR_1_E,"); // �Z��1�i�p���j
			sql.add("	cbk.BNK_ADR_2_E "); // �Z��2�i�p���j
			sql.add(" FROM AP_CBK_MST cbk");

			sql.add("LEFT OUTER JOIN BNK_MST bnk ON cbk.CBK_BNK_CODE = bnk.BNK_CODE ");
			sql.add("	                     AND    cbk.CBK_STN_CODE = bnk.BNK_STN_CODE ");
			sql.add("LEFT OUTER JOIN BMN_MST dep ON dep.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    dep.DEP_CODE = cbk.CBK_DEP_CODE");
			sql.add("LEFT OUTER JOIN KMK_MST kmk ON kmk.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    kmk.KMK_CODE = cbk.CBK_KMK_CODE");
			sql.add("LEFT OUTER JOIN HKM_MST hkm ON hkm.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    hkm.KMK_CODE = cbk.CBK_KMK_CODE");
			sql.add("	                     AND    hkm.HKM_CODE = cbk.CBK_HKM_CODE");
			sql.add("LEFT OUTER JOIN UKM_MST ukm ON ukm.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    ukm.KMK_CODE = cbk.CBK_KMK_CODE");
			sql.add("	                     AND    ukm.HKM_CODE = cbk.CBK_HKM_CODE");
			sql.add("	                     AND    ukm.UKM_CODE = cbk.CBK_UKM_CODE");
			sql.add("LEFT OUTER JOIN CUR_MST cur ON cur.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    cur.CUR_CODE = cbk.CUR_CODE");

			sql.add(" WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("   AND cbk.KAI_CODE = ?", condition.getCompanyCode());
			}

			// �R�[�h
			if (!Util.isNullOrEmpty(condition.getIBanCode())) {
				sql.add("AND (cbk.IBAN_NO = ? ", condition.getIBanCode());
				sql.add(" OR CONCAT(NVL(cbk.BANK_IDF,''), NVL(cbk.ACCOUNT_IDF,'')) = ?", condition.getIBanCode()
					.replaceAll("/", ""));
				sql.add("    ) ");
			}

			// �ȖڃR�[�h
			if (!Util.isNullOrEmpty(condition.getItemCode())) {
				sql.add("   AND cbk.CBK_KMK_CODE = ?", condition.getItemCode());
			}

			// �⏕�ȖڃR�[�h
			if (!Util.isNullOrEmpty(condition.getSubItemCode())) {
				sql.add("   AND cbk.CBK_HKM_CODE = ?", condition.getSubItemCode());
			}

			// ����ȖڃR�[�h
			if (!Util.isNullOrEmpty(condition.getDetailItemCode())) {
				sql.add("   AND cbk.CBK_UKM_CODE = ?", condition.getDetailItemCode());
			}

			// �a���ԍ�
			if (!Util.isNullOrEmpty(condition.getAccountNo())) {
				sql.add("   AND cbk.CBK_YKN_NO = ?", condition.getAccountNo());
			}

			sql.add(" ORDER BY ");
			sql.add("	cbk.KAI_CODE,");
			sql.add("	cbk.CBK_CBK_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				bankAccount = mapping(rs);
				break;
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return bankAccount;
	}

	/**
	 * ��s������o�^����B
	 * 
	 * @param bean ��s����
	 * @throws TException
	 */
	public void entry(BankAccount bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("INSERT INTO AP_CBK_MST (");
			s.add("	KAI_CODE,");
			s.add("	CBK_CBK_CODE,");
			s.add("	CBK_NAME,");
			s.add("	CUR_CODE,");
			s.add("	CBK_BNK_CODE,");
			s.add("	CBK_STN_CODE,");
			s.add("	CBK_IRAI_EMP_CODE,");
			s.add("	CBK_IRAI_NAME,");
			s.add("	CBK_IRAI_NAME_J,");
			s.add("	CBK_IRAI_NAME_E,");
			s.add("	CBK_YKN_KBN,");
			s.add("	CBK_YKN_NO,");
			s.add("	CBK_EMP_FB_KBN,");
			s.add("	CBK_OUT_FB_KBN,");
			s.add("	CBK_DEP_CODE,");
			s.add("	CBK_KMK_CODE,");
			s.add("	CBK_HKM_CODE,");
			s.add("	CBK_UKM_CODE,");
			s.add("	IBAN_NO,"); // IBAN�R�[�h
			s.add("	BANK_IDF,"); // ��s���ʎq
			s.add("	ACCOUNT_IDF,"); // �������ʎq
			s.add("	SWIFT_CODE,"); // SWIFT�R�[�h
			s.add("	CBK_COU_CODE,"); // BANK COUNTRY
			s.add("	BNK_ADR_1,"); // �Z��1
			s.add("	BNK_ADR_2,"); // �Z��2
			s.add("	CBK_BANK_NAME_E,"); // ��s���i�p���j
			s.add("	CBK_STN_NAME_E,"); // �x�X���i�p���j
			s.add("	BNK_ADR_1_E,"); // �Z��1�i�p���j
			s.add("	BNK_ADR_2_E,"); // �Z��2�i�p���j
			s.add("	STR_DATE,");
			s.add("	END_DATE,");
			s.add("	CBK_INP_DATE,");
			s.add("	PRG_ID,");
			s.add("	USR_ID");
			s.add(") VALUES (");
			s.add("	?,", bean.getCompanyCode());
			s.add("	?,", bean.getCode());
			s.add("	?,", bean.getName());
			s.add("	?,", bean.getCurrencyCode());
			s.add("	?,", bean.getBankCode());
			s.add("	?,", bean.getBranchCode());
			s.add("	?,", bean.getClientCode());
			s.add("	?,", bean.getClientName());
			s.add("	?,", bean.getClientNameJ());
			s.add("	?,", bean.getClientNameE());
			s.add("	?,", bean.getDepositKind().value);
			s.add("	?,", bean.getAccountNo());
			s.add("	?,", bean.isUseEmployeePayment() ? 1 : 0);
			s.add("	?,", bean.isUseExPayment() ? 1 : 0);
			s.add("	?,", bean.getDepartmentCode());
			s.add("	?,", bean.getItemCode());
			s.add("	?,", bean.getSubItemCode());
			s.add("	?,", bean.getDetailItemCode());
			s.add("	?,", bean.getIBan()); // IBAN�R�[�h
			s.add("	?,", bean.getBankIndentify()); // ��s���ʎq
			s.add("	?,", bean.getBankAccountIndentify()); // �������ʎq
			s.add("	?,", bean.getSwift()); // SWIFT�R�[�h
			s.add("	?,", bean.getBankCountry()); // BANK COUNTRY
			s.add("	?,", bean.getBnkAdr1()); // �Z��1
			s.add("	?,", bean.getBnkAdr2()); // �Z��2
			s.add("	?,", bean.getBankNameE()); // ��s���i�p���j
			s.add("	?,", bean.getBranchNameE()); // �x�X���i�p���j
			s.add("	?,", bean.getBnkAdr1E()); // �Z��1
			s.add("	?,", bean.getBnkAdr2E()); // �Z��2
			s.add("	?,", bean.getDateFrom());
			s.add("	?,", bean.getDateTo());
			s.adt("	?,", getNow());
			s.add("	?,", getProgramCode());
			s.add("	?", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * ��s�������C������B
	 * 
	 * @param bean ��s����
	 * @throws TException
	 */
	public void modify(BankAccount bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("UPDATE AP_CBK_MST SET");
			s.add("	CBK_NAME = ?,", bean.getName());
			s.add("	CUR_CODE = ?,", bean.getCurrencyCode());
			s.add("	CBK_BNK_CODE = ?,", bean.getBankCode());
			s.add("	CBK_STN_CODE = ?,", bean.getBranchCode());
			s.add("	CBK_IRAI_EMP_CODE = ?,", bean.getClientCode());
			s.add("	CBK_IRAI_NAME = ?,", bean.getClientName());
			s.add("	CBK_IRAI_NAME_J = ?,", bean.getClientNameJ());
			s.add("	CBK_IRAI_NAME_E = ?,", bean.getClientNameE());
			s.add("	CBK_YKN_KBN = ?,", bean.getDepositKind().value);
			s.add("	CBK_YKN_NO = ?,", bean.getAccountNo());
			s.add("	CBK_EMP_FB_KBN = ?,", bean.isUseEmployeePayment() ? 1 : 0);
			s.add("	CBK_OUT_FB_KBN = ?,", bean.isUseExPayment() ? 1 : 0);
			s.add("	CBK_DEP_CODE = ?,", bean.getDepartmentCode());
			s.add("	CBK_KMK_CODE = ?,", bean.getItemCode());
			s.add("	CBK_HKM_CODE = ?,", bean.getSubItemCode());
			s.add("	CBK_UKM_CODE = ?,", bean.getDetailItemCode());
			s.add("	IBAN_NO = ? ,", bean.getIBan()); // IBAN�R�[�h
			s.add("	BANK_IDF = ? ,", bean.getBankIndentify()); // ��s���ʎq
			s.add("	ACCOUNT_IDF = ? ,", bean.getBankAccountIndentify()); // �������ʎq
			s.add("	SWIFT_CODE = ? ,", bean.getSwift()); // SWIFT�R�[�h
			s.add("	CBK_COU_CODE = ? ,", bean.getBankCountry()); // BANK COUNTRY
			s.add("	BNK_ADR_1 = ? ,", bean.getBnkAdr1()); // �Z��1
			s.add("	BNK_ADR_2 = ? ,", bean.getBnkAdr2()); // �Z��2
			s.add(" CBK_BANK_NAME_E = ? ,", bean.getBankNameE()); // ��s���i�p���j
			s.add("	CBK_STN_NAME_E = ? ,", bean.getBranchNameE()); // �x�X���i�p���j
			s.add("	BNK_ADR_1_E = ? ,", bean.getBnkAdr1E()); // �Z��1�i�p���j
			s.add("	BNK_ADR_2_E = ? ,", bean.getBnkAdr2E()); // �Z��2�i�p���j
			s.add("	STR_DATE = ?,", bean.getDateFrom());
			s.add("	END_DATE = ?,", bean.getDateTo());
			s.adt("	UPD_DATE = ?,", getNow());
			s.add("	PRG_ID = ?,", getProgramCode());
			s.add("	USR_ID = ?", getUserCode());

			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("AND CBK_CBK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * ��s�������폜����B
	 * 
	 * @param bean ��s����
	 * @throws TException
	 */
	public void delete(BankAccount bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("DELETE FROM AP_CBK_MST");
			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("AND CBK_CBK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

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
	protected BankAccount mapping(ResultSet rs) throws Exception {

		BankAccount bean = createBankAccount();

		// ��ЃR�[�h
		bean.setCompanyCode(rs.getString("KAI_CODE"));

		// �R�[�h
		bean.setCode(rs.getString("CBK_CBK_CODE"));

		// ��s��������
		bean.setName(rs.getString("CBK_NAME"));

		// �ʉ݃R�[�h
		bean.setCurrencyCode(rs.getString("CUR_CODE"));

		// �ʉݏ��
		Currency currency = createCurrency();
		currency.setCompanyCode(rs.getString("KAI_CODE"));
		currency.setCode(rs.getString("CUR_CODE"));
		currency.setName(rs.getString("CUR_NAME"));
		currency.setNames(rs.getString("CUR_NAME_S"));
		currency.setNamek(rs.getString("CUR_NAME_K"));
		currency.setDecimalPoint(rs.getInt("DEC_KETA"));
		bean.setCurrency(currency);

		// ��s���
		bean.setBankCode(rs.getString("CBK_BNK_CODE")); // ��s�R�[�h
		bean.setBankName(Util.avoidNull(rs.getString("BNK_NAME_S"))); // ��s����
		bean.setBankKana(Util.avoidNull(rs.getString("BNK_KANA"))); // ��s�J�i
		bean.setBankNamek(Util.avoidNull(rs.getString("BNK_NAME_K"))); // ��s��������
		bean.setBranchCode(rs.getString("CBK_STN_CODE")); // �x�X�R�[�h
		bean.setBranchName(Util.avoidNull(rs.getString("BNK_STN_NAME_S"))); // �x�X����
		bean.setBranchKana(Util.avoidNull(rs.getString("BNK_STN_KANA"))); // �x�X�J�i
		bean.setBranchNamek(Util.avoidNull(rs.getString("BNK_STN_NAME_K"))); // �x�X��������

		// ��s���i�p���j
		bean.setBankNameE(rs.getString("CBK_BANK_NAME_E"));

		// �x�X���i�p���j
		bean.setBranchNameE(rs.getString("CBK_STN_NAME_E"));

		// �U���˗��l�R�[�h
		bean.setClientCode(rs.getString("CBK_IRAI_EMP_CODE"));

		// �U���˗��l��
		bean.setClientName(rs.getString("CBK_IRAI_NAME"));

		// �U���˗��l���i�����j
		bean.setClientNameJ(rs.getString("CBK_IRAI_NAME_J"));

		// �U���˗��l���i�p���j
		bean.setClientNameE(rs.getString("CBK_IRAI_NAME_E"));

		// �a�����
		bean.setDepositKind(DepositKind.getDepositKind(rs.getInt("CBK_YKN_KBN")));

		// �����ԍ�
		bean.setAccountNo(rs.getString("CBK_YKN_NO"));

		// �v�㕔��R�[�h
		bean.setDepartmentCode(rs.getString("CBK_DEP_CODE"));
		bean.setDepartmentName(rs.getString("DEP_NAME"));
		bean.setDepartmentNames(rs.getString("DEP_NAME_S"));
		bean.setDepartmentNamek(rs.getString("DEP_NAME_K"));

		// �ȖڃR�[�h
		bean.setItemCode(rs.getString("CBK_KMK_CODE"));
		bean.setItemName(rs.getString("KMK_NAME"));
		bean.setItemNames(rs.getString("KMK_NAME_S"));
		bean.setItemNamek(rs.getString("KMK_NAME_K"));

		// �⏕�ȖڃR�[�h
		bean.setSubItemCode(rs.getString("CBK_HKM_CODE"));
		bean.setSubItemName(rs.getString("HKM_NAME"));
		bean.setSubItemNames(rs.getString("HKM_NAME_S"));
		bean.setSubItemNamek(rs.getString("HKM_NAME_K"));

		// ����ȖڃR�[�h
		bean.setDetailItemCode(rs.getString("CBK_UKM_CODE"));
		bean.setDetailItemName(rs.getString("UKM_NAME"));
		bean.setDetailItemNames(rs.getString("UKM_NAME_S"));
		bean.setDetailItemNamek(rs.getString("UKM_NAME_K"));

		// IBAN�R�[�h
		bean.setIBan(rs.getString("IBAN_NO"));

		// ��s���ʎq
		bean.setBankIndentify(rs.getString("BANK_IDF"));

		// �������ʎq
		bean.setBankAccountIndentify(rs.getString("ACCOUNT_IDF"));

		// SWIFT�R�[�h
		bean.setSwift(rs.getString("SWIFT_CODE"));

		// BANK COUNTRY
		bean.setBankCountry(rs.getString("CBK_COU_CODE"));

		// �Z��1
		bean.setBnkAdr1(rs.getString("BNK_ADR_1"));

		// �Z��2
		bean.setBnkAdr2(rs.getString("BNK_ADR_2"));

		// �Z��1�i�p���j
		bean.setBnkAdr1E(rs.getString("BNK_ADR_1_E"));

		// �Z��2�i�p���j
		bean.setBnkAdr2E(rs.getString("BNK_ADR_2_E"));

		// �L�����ԊJ�n
		bean.setDateFrom(rs.getDate("STR_DATE"));

		// �L�����ԏI��
		bean.setDateTo(rs.getDate("END_DATE"));

		// �Ј��e�a�Ŏg�p���邩
		bean.setUseEmployeePayment(rs.getInt("CBK_EMP_FB_KBN") != 0);

		// �ЊO�e�a�Ŏg�p���邩
		bean.setUseExPayment(rs.getInt("CBK_OUT_FB_KBN") != 0);

		return bean;

	}

	/**
	 * @return �ʉ�
	 */
	protected Currency createCurrency() {
		return new Currency();
	}

	/**
	 * @return ��s����
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(BankAccountSearchCondition condition) throws TException {

		try {

			// �f�[�^�𒊏o
			List<BankAccount> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			BankAccountExcel exl = new BankAccountExcel(getUser().getLanguage());
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
