package jp.co.ais.trans2.model.close;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �������ߏ����̎���
 * @author AIS
 *
 */
public class DailyCloseImpl extends TModel implements DailyClose {
	
	/** CF�f�[�^�쐬�s�v */
	public static boolean isNotCreateCF = ServerConfig.isFlagOn("trans.GL0060.not.create.cf.data");

	/** true:���F�����@�\�L�� */
	public static boolean isUseApproveHistory = ServerConfig.isFlagOn("trans.slip.use.approve.history");

	/** ����P�ʂőO���J�z���v�𐶐� */
	public static boolean isDepCreate = ServerConfig.isFlagOn("trans.GL0060.dep.create.retained.earnings");

	/** true:CM_FUND_DTL�o�^���s���� */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/** �O�ݕ]���֊T�Z�̓`�[��� */
	public static String code01Z = "01Z";

	/** �v�Z���W�b�N */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 * �Ō�ɓ����X�V�������t��Ԃ�
	 * @param company
	 * @return Date �Ō�ɓ����X�V�������t
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException {

		Connection conn = DBUtil.getConnection();
		Date date = null;

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("     NITIJI_DATE ");
			sql.add(" FROM SIM_CTL ");
			sql.add(" WHERE	KAI_CODE = ? ",company.getCode());
			
			date = DBUtil.selectOneDate(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return date;

	}

	/**
	 * �Ō�ɓ����X�V�������t���X�V����
	 * @param company ���
	 * @param date ���t
	 * @throws TException
	 */
	public void updateLastDailyClosedDate(Company company, Date date) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" UPDATE SIM_CTL ");
			sql.add(" SET NITIJI_DATE = ? ",date);
			sql.adt("    ,UPD_DATE = ? ", getNow());
			sql.add("    ,PRG_ID = ? ", getProgramCode());
			sql.add("    ,USR_ID = ? ", getUserCode());
			sql.add(" WHERE	KAI_CODE = ? ", company.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �����̒��߂��s��
	 * @param date �`�[���t
	 * @param company ���
	 * @throws TException
	 */
	public void closeDaily(Date date, Company company) throws TException {

		Connection conn = DBUtil.getConnection();

		// MySQL�̏ꍇ�͍Ō��T_DAILY_CLOSE���폜���邽�߁A����t���O��ǉ�
		boolean isFlush = false;
		String slipUpdateTable = "T_DAILY_CLOSE";
		
		try {

			// 1.��������

			// �O���J�z���v�ȖڂƂ��̌v�㕔����擾����
			SlipManager slipManager = (SlipManager)getComponent(SlipManager.class);
			AutoJornalAccount retainedEarningsItemJornal =
				slipManager.getAutoJornalAccount(
						company.getCode(),
						AutoJornalAccountType.PREVIOUS_EARNING_CARRIED_FORWARD);

			if (retainedEarningsItemJornal == null) {
				// �O���J�z���v�̎擾�Ɏ��s���܂����B
				throw new TException("I00201", "C02113");
			}

			// ���ʏ����̐���
			// ���
			String sqlSwkWhere = " WHERE swk.KAI_CODE = " + SQLUtil.getParam(company.getCode());

			// �`�[���t
			sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date);

			// �X�V�敪
			if (company.getAccountConfig().isUseApprove()) {
				sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_UPD_KBN = 3 ";
			} else if (company.getAccountConfig().isUseFieldApprove()) {
				sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_UPD_KBN = 2 ";
			} else {
				sqlSwkWhere = sqlSwkWhere + " AND	swk.SWK_UPD_KBN = 1 ";
			}

			// �X�V�Ώۃf�[�^�ꎞ�i�[�e�[�u��

			if (DBUtil.isUseMySQL || !isNotCreateCF) {

				SQLCreator sqlCnt = new SQLCreator();
				sqlCnt.add("  SELECT COUNT(*) FROM " + slipUpdateTable);
				int tblCnt = DBUtil.selectOneInt(conn, sqlCnt.toSQL());
				if (tblCnt > 0) {
					sqlCnt = new SQLCreator();
					sqlCnt.add("  SELECT DISTINCT KAI_CODE FROM " + slipUpdateTable);

					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sqlCnt);

					String kaiCode = null;
					while (rs.next()) {
						kaiCode = rs.getString("KAI_CODE");
					}
					rs.close();
					statement.close();

					
					throw new TException("I01140", kaiCode); // ��ЁF{0} �œ����X�V���s���ł��B���΂炭�����Ă���ēx���s���Ă��������B
				}
				
				SQLCreator sqlUd = new SQLCreator();
				sqlUd.add("  INSERT INTO " + slipUpdateTable);
				sqlUd.add("  ( KAI_CODE ");
				sqlUd.add("   ,SWK_DEN_NO ");
				sqlUd.add("  ) ");
				sqlUd.add("  SELECT DISTINCT swk.KAI_CODE ");
				sqlUd.add("                 ,swk.SWK_DEN_NO ");
				sqlUd.add("  FROM SWK_DTL swk ");
				sqlUd.add(sqlSwkWhere);
				sqlUd.add("  AND swk.DEN_SYU_CODE <> ? ", code01Z);

				int count = DBUtil.execute(conn, sqlUd);

				if (count == 0) {
					throw new TException("I00022"); // �ΏۂƂȂ�f�[�^��������܂���B
				}
				
				// �����������true���Ō�ɍ폜
				isFlush = true;

				if (DBUtil.isUseMySQL) {
					// ���b�N��MySQL�̂�
					sqlUd = new SQLCreator();
					sqlUd.add(" SELECT SWK_DEN_NO ");
					sqlUd.add(" FROM " + slipUpdateTable);
					sqlUd.add(" WHERE KAI_CODE = ? ", company.getCode());

					sqlSwkWhere = sqlSwkWhere + "AND swk.SWK_DEN_NO IN (" + sqlUd.toSQL() + ")";
					
					{
						// �`�[�����b�N�ɂ���(�����X�V����)
						String sqlUpdate = " SET PRG_ID = 'BATCH' " + sqlSwkWhere;
						
						DBUtil.execute(conn, " UPDATE GL_SWK_HDR swk " + sqlUpdate);
						DBUtil.execute(conn, " UPDATE AP_SWK_HDR swk " + sqlUpdate);
						DBUtil.execute(conn, " UPDATE AR_SWK_HDR swk " + sqlUpdate);
						DBUtil.execute(conn, " UPDATE SWK_DTL swk " + sqlUpdate);
					}
				}
				
			}

			// �X�V�pSQL����(�X�V�ΏۂƎc���]�L�Ώۂ��قȂ�\(���Ԍ��Z�J��z���Ȃ��ꍇ))
			String updateSqlSwkWhere = sqlSwkWhere;
			String updateSqlSwkWhereLast = sqlSwkWhere; // �ŏI�X�V�p�ɑޔ�
			
			// ���c���A���c���A������f�[�^�]�L�p����
			String balanceSqlSwkWhere = sqlSwkWhere;

			// ���Ԍ��Z�J��z��WHERE�����ݒ�
			sqlSwkWhere += getCarryJournalOfMidtermClosingForward(company);

			if (!isCarryJournalOfMidtermClosingForward(company)) {
				// ���ԌJ��z���Ȃ��ꍇ�̂݁A���������v�Z���Đݒ�
				int beginMonth = company.getFiscalPeriod().getMonthBeginningOfPeriod();
				String endMonth = StringUtil.rightBX("0" + ((beginMonth + 10) % 12 + 1), 2);

				if (!Util.isNullOrEmpty(endMonth)) {
					// �������ȊO�̏ꍇ�͌��Z�`�[��]�L���Ȃ�
					balanceSqlSwkWhere += " AND (swk.SWK_KSN_KBN = 0 OR TO_CHAR(swk.SWK_DEN_DATE, 'MM') = " + SQLUtil.getParam(endMonth) + ") ";
				}
			}
			
			if (!DBUtil.isUseMySQL) {
				// �O�ݕ]���ւ̓`�[��ʂ�����
				sqlSwkWhere += " AND    swk.DEN_SYU_CODE <> '" + code01Z + "' ";
			}

			// �X�V���
			String sqlUpdateInfoColumn =
				SQLUtil.getYMDHMSParam(getNow()) + ", " +
				" NULL, " +
				SQLUtil.getParam(getProgramCode()) + ", " +
				SQLUtil.getParam(getUserCode()) + ", ";

			// �Ǘ��c���̃J����
			String krzZanColumn =
				" KAI_CODE, " +
				" KRZ_NENDO, " +
				" KRZ_DEP_CODE, " +
				" KRZ_KMK_CODE, " +
				" KRZ_HKM_CODE, " +
				" KRZ_UKM_CODE, " +
				" KRZ_TRI_CODE, " +
				" KRZ_EMP_CODE, " +
				" KRZ_KNR_CODE_1, " +
				" KRZ_KNR_CODE_2, " +
				" KRZ_KNR_CODE_3, " +
				" KRZ_KNR_CODE_4, " +
				" KRZ_KNR_CODE_5, " +
				" KRZ_KNR_CODE_6, " +
				" KRZ_KSN_KBN, " +
				" KRZ_CUR_CODE, " +
				" KRZ_STR_SUM, " +
				" KRZ_STR_SUM_G, ";
			for (int month = 1; month <= 12; month++) {
				krzZanColumn = krzZanColumn +
					" KRZ_DR_" + month + ", " +
					" KRZ_CR_" + month + ", " +
					" KRZ_ZAN_" + month + ", " +
					" KRZ_DR_G_" + month + ", " +
					" KRZ_CR_G_" + month + ", " +
					" KRZ_ZAN_G_" + month + ", ";
			}
			krzZanColumn = krzZanColumn +
				" INP_DATE, " +
				" UPD_DATE, " +
				" PRG_ID, " +
				" USR_ID, " +
				" KRZ_BOOK_NO, " +
				" KRZ_ADJ_KBN ";

			// 2.�e�[�u�����b�N�ɂ�葼�g�����U�N�V�������u���b�N
			try {
				DBUtil.execute(conn, "LOCK TABLE GL_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
				DBUtil.execute(conn, "LOCK TABLE AP_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
				DBUtil.execute(conn, "LOCK TABLE AR_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
				DBUtil.execute(conn, "LOCK TABLE SWK_DTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TException("W01133");
			}

			// 3.�X�V�Ώێd��̃A���o�����X�`�F�b�N
			String sql =
				" SELECT " +
					" SWK_BOOK_NO, " +
					" SWK_DEN_NO " +
				" FROM " +
					" SWK_DTL swk " + updateSqlSwkWhere +
				" GROUP BY " +
				    " SWK_BOOK_NO, " +
				    " SWK_DEN_NO " +
				" HAVING SUM(CASE WHEN SWK_DC_kBN = 0 THEN SWK_KIN ELSE -SWK_KIN END) <> 0 ";

			String unbalanceSlipNo = (String)DBUtil.select(conn, sql, new ORMapper() {

				public Object mapping(ResultSet rs) throws Exception {

					String result = "";
					while (rs.next()) {
						result = result + rs.getString("SWK_DEN_NO") + ";";
					}
					return result;
				}
			});

			if (!Util.isNullOrEmpty(unbalanceSlipNo)) {
				// �X�V�Ώۂ̎d�󂪑ݎؕs��v�ł��B�`�[�ԍ��F
				throw new TException("W01141" , unbalanceSlipNo);
			}

			// 4.�c���J�z����
			// 4.1 �O���J�z���v���ǂ̔N�x�܂ō쐬����Ă��邩���擾
			sql =
				" SELECT	MAX(KRZ_NENDO) NENDO " +
			    " FROM		KRZ_ZAN "+
			    " WHERE		KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
			    " AND		KRZ_KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode());

			int krzMaxNendo = DBUtil.selectOneInt(conn, sql);

			// 4.2 ����Ώێd��̍ő�̔N�x���擾
			sql =
				" SELECT	MAX(SWK_NENDO) MAX_NENDO " +
			    " FROM		SWK_DTL swk " + updateSqlSwkWhere;

			int swkMaxNendo = DBUtil.selectOneInt(conn, sql);

			sql =
				" SELECT	MIN(SWK_NENDO) MIN_NENDO " +
			    " FROM		SWK_DTL swk " + updateSqlSwkWhere;

			int swkMinNendo = DBUtil.selectOneInt(conn, sql);

			// �Ώۃf�[�^����
			if (swkMaxNendo == 0) {
				// �ΏۂƂȂ�f�[�^��������܂���B
				throw new TException("I00022");
			}

			// �Ǘ��c�����S�������ꍇ�́A����X�V�Ώۂ̓`�[�̔N�x�Ƃ���
			if (krzMaxNendo == 0) {
				krzMaxNendo = swkMinNendo;
			}

			// 4.3 �J�z���v�̍ŏI�N�x�����߂�B
			int lastNendo = krzMaxNendo;
			if (krzMaxNendo <= swkMaxNendo) {
				lastNendo = swkMaxNendo + 1;
			}

			// 4.4 �Ǘ��c���̌���B/S���J��z����Ă���N�x������̌J�z�N�x���������ꍇ�A�J�z����������
			if (lastNendo > krzMaxNendo) {

				// �J�z�́A�����݂̔N�x���獡��Ώۂ̍ő�N�x�܂�
				for (int i = krzMaxNendo; i < lastNendo; i++) {

					// �O���J�z���v�s�쐬
					// ���O���J�z�s�͕K��1�N��܂ō��
					sql =
						" INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) " +
						" SELECT " +
							" krz.KAI_CODE, " +
							Integer.toString(i + 1) + ", ";
					if (isDepCreate) {
						// ����P�ʂŐ���
						sql += " krz.KRZ_DEP_CODE, ";
					} else {
						// �����d��Ȗڃ}�X�^
						sql += SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode()) + ", ";
					}
					sql +=
							SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
							SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
							SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" NULL, " +
							" 0, " +
							" krz.KRZ_CUR_CODE, " +
							" SUM(krz.KRZ_ZAN_12), " +
							" SUM(krz.KRZ_ZAN_G_12), ";
					for (int month = 1; month <= 12; month++) {
						sql = sql +
							" 0, 0, SUM(krz.KRZ_ZAN_12), " +
							" 0, 0, SUM(krz.KRZ_ZAN_G_12), ";
					}
					sql = sql + sqlUpdateInfoColumn +
							" krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN " +
						" FROM " +
							" KRZ_ZAN krz " +
						" WHERE krz.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
						" AND	krz.KRZ_NENDO = " + Integer.toString(i) +
						" AND	krz.KRZ_KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
						" GROUP BY " +
							" krz.KAI_CODE, " +
							" krz.KRZ_CUR_CODE, ";
					if (isDepCreate) {
						// ����P�ʂŐ���
						sql += " krz.KRZ_DEP_CODE, ";
					}
					sql += " krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN ";

					int count = DBUtil.execute(conn, sql);

					// �O���J�z���v�̃��R�[�h��������Γo�^����B
					// ���J�z���v�s�̑��݃`�F�b�N�͏�ɓ��{�������w�����Ƃɂ���B
					if (count == 0) {

						if (isDepCreate) {
							// ����P�ʂŐ���
							sql =
								"INSERT INTO KRZ_ZAN " +
								"SELECT " +
								    " dtl.KAI_CODE, " +
									Integer.toString(i + 1) + ", " +
									" dtl.SWK_DEP_CODE, " +
									SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" 0, " +
									SQLUtil.getParam(company.getAccountConfig().getKeyCurrency().getCode()) + ", " +
									" 0, ";//KRZ_STR_SUM
							for (int month = 1; month <= 12; month++) {
								sql = sql + " 0, 0, 0, ";
							}
							sql = sql + sqlUpdateInfoColumn;
							sql = sql + " 0, ";//KRZ_STR_SUM_G
							for (int month = 1; month <= 12; month++) {
								sql = sql + " 0, 0, 0, ";
							}
							sql = sql + " 1, " +
									" 0 " +
								" FROM SWK_DTL dtl "+
								" LEFT OUTER JOIN KMK_MST kmk " +
								" ON  dtl.KAI_CODE = kmk.KAI_CODE " +
								" AND dtl.SWK_KMK_CODE = kmk.KMK_CODE " +
								" WHERE dtl.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
								" AND kmk.KMK_SHU = 1 ";
								// �X�V�敪
								if (company.getAccountConfig().isUseApprove()) {
									sql += " AND dtl.SWK_UPD_KBN = 3 ";
								} else if (company.getAccountConfig().isUseFieldApprove()) {
									sql += " AND dtl.SWK_UPD_KBN = 2 ";
								} else {
									sql += " AND dtl.SWK_UPD_KBN = 1 ";
								}
							sql += 
								" AND dtl.SWK_NENDO = " + Integer.toString(i) +
								" GROUP BY dtl.KAI_CODE, dtl.SWK_NENDO, dtl.SWK_DEP_CODE " +
								" ORDER BY dtl.KAI_CODE, dtl.SWK_NENDO, dtl.SWK_DEP_CODE ";
						} else {
							// �����d��Ȗڃ}�X�^�Ő���
							sql =
								" INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) " +
								" VALUES ( " +
									SQLUtil.getParam(company.getCode()) + ", " +
									Integer.toString(i + 1) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
									SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" NULL, " +
									" 0, " +
									SQLUtil.getParam(company.getAccountConfig().getKeyCurrency().getCode()) + ", " +
									" 0, " +
									" 0, ";
							for (int month = 1; month <= 12; month++) {
								sql = sql + " 0, 0, 0, 0, 0, 0, ";
							}
							sql = sql + sqlUpdateInfoColumn +
									" 1, " +
									" 0 " +
								" ) ";
						}

						DBUtil.execute(conn, sql);

					}

					// B/S�J�z
					sql =
						" INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) " +
						" SELECT /*+RULE*/ " +
							" krz.KAI_CODE, " +
							Integer.toString(i) + ", " +
							" krz.KRZ_DEP_CODE, " +
							" krz.KRZ_KMK_CODE, " +
							" krz.KRZ_HKM_CODE, " +
							" krz.KRZ_UKM_CODE, " +
							" krz.KRZ_TRI_CODE, " +
							" krz.KRZ_EMP_CODE, " +
							" krz.KRZ_KNR_CODE_1, " +
							" krz.KRZ_KNR_CODE_2, " +
							" krz.KRZ_KNR_CODE_3, " +
							" krz.KRZ_KNR_CODE_4, " +
							" krz.KRZ_KNR_CODE_5, " +
							" krz.KRZ_KNR_CODE_6, " +
							" 0, " +
							" krz.KRZ_CUR_CODE, " +
							" SUM(krz.KRZ_ZAN_12), " +
							" SUM(krz.KRZ_ZAN_G_12), ";
					for (int month = 1; month <= 12; month++) {
						sql = sql +
							" 0, 0, SUM(krz.KRZ_ZAN_12), " +
							" 0, 0, SUM(krz.KRZ_ZAN_G_12), ";
					}
					sql = sql + sqlUpdateInfoColumn +
							" krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN " +
						" FROM " +
							" KRZ_ZAN krz " +
							" INNER JOIN KMK_MST kmk " +
							" ON	krz.KAI_CODE = kmk.KAI_CODE " +
							" AND	krz.KRZ_KMK_CODE = kmk.KMK_CODE " +
							" AND	kmk.KMK_SHU <> '1' " +
						" WHERE krz.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
						" AND	krz.KRZ_NENDO = " + Integer.toString(i - 1) +
						" AND	krz.KRZ_KMK_CODE <> " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
						" GROUP BY " +
							" krz.KAI_CODE, " +
							" krz.KRZ_DEP_CODE, " +
							" krz.KRZ_KMK_CODE, " +
							" krz.KRZ_HKM_CODE, " +
							" krz.KRZ_UKM_CODE, " +
							" krz.KRZ_TRI_CODE, " +
							" krz.KRZ_EMP_CODE, " +
							" krz.KRZ_KNR_CODE_1, " +
							" krz.KRZ_KNR_CODE_2, " +
							" krz.KRZ_KNR_CODE_3, " +
							" krz.KRZ_KNR_CODE_4, " +
							" krz.KRZ_KNR_CODE_5, " +
							" krz.KRZ_KNR_CODE_6, " +
							" krz.KRZ_CUR_CODE, " +
							" krz.KRZ_BOOK_NO, " +
							" krz.KRZ_ADJ_KBN " +
						" HAVING 	SUM(krz.KRZ_ZAN_12) <> 0 " +
						" OR		SUM(krz.KRZ_ZAN_G_12) <> 0 ";

					DBUtil.execute(conn, sql);

				}

			}

			// 5 �c���]�L(���N�x)
			updateKrzZanCurrentYear(sqlSwkWhere, krzZanColumn);

			// 5 �c���]�L(���N�x�ȍ~)

			// 5.1 B/S�]�L
			updateKrzZanBS(sqlSwkWhere, krzZanColumn, retainedEarningsItemJornal, swkMinNendo, swkMaxNendo, lastNendo);

			// 5.2 P/L�]�L(���N�̑O���J�z���v�ɉ��Z)
			updateKrzZanPL(sqlSwkWhere, krzZanColumn, retainedEarningsItemJornal, swkMinNendo, swkMaxNendo, lastNendo, company);

			// 6.�c���o�����X�`�F�b�N
			for (int nendo = swkMinNendo; nendo < lastNendo; nendo++) {

				sql =
					" SELECT " +
						" SUM(CASE WHEN kmk.DC_KBN = 0 THEN krz.KRZ_ZAN_12 ELSE -krz.KRZ_ZAN_12 END) BALANCE ";
						if (isDepCreate) {
							// ����P�ʂŃ`�F�b�N
							sql += " ,krz.KRZ_DEP_CODE ,dep.DEP_NAME ";
						}
						sql = sql +
			        " FROM " +
			        	" KRZ_ZAN krz " +
						" LEFT OUTER JOIN KMK_MST kmk " +
						" ON	krz.KAI_CODE = kmk.KAI_CODE " +
						" AND	krz.KRZ_KMK_CODE = kmk.KMK_CODE " +
						" LEFT OUTER JOIN BMN_MST dep " +
						" ON	krz.KAI_CODE     = dep.KAI_CODE " +
						" AND	krz.KRZ_DEP_CODE = dep.DEP_CODE " +
					" WHERE 	krz.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND		krz.KRZ_NENDO = " + Integer.toString(nendo);
						if (isDepCreate) {
							// ����P�ʂŃ`�F�b�N
							sql += " GROUP BY krz.KRZ_DEP_CODE ,dep.DEP_NAME ";
						}

				if (isDepCreate) {
					// �v�㕔�喈�Ƀ`�F�b�N �� ���傲�Ƃɋ��z��\��
					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sql);

					String result = "";
					BigDecimal balance = null;
					String msg = null;
					while (rs.next()) {
						if (!Util.isNullOrEmpty(result)) {
							result = result + "\n";
						}
						balance = rs.getBigDecimal("BALANCE");
						if (balance != null && balance.compareTo(BigDecimal.ZERO) != 0) {
							// �Ǘ��c�����ݎؕs��v�ł��B �A���o�����X�z�F �A�A���o�����X�N�x�F�A�A���o�����X�v�㕔��F
							msg = getMessage("W00091", getWord("C11228") + balance.toString() + "�A" + getWord("C11229") + Integer.toString(nendo) + "�A" + getWord("CAC002") + rs.getString("KRZ_DEP_CODE") + ":" + rs.getString("DEP_NAME"));
							
							result = result + msg;
						}

					}
					rs.close();
					statement.close();
					if (!Util.isNullOrEmpty(result)) {
						// �\�z�����G���[������ΏI��
						throw new TException(result);
					}

				} else {
					BigDecimal balance = DecimalUtil.toBigDecimalNVL(DBUtil.selectOne(conn, sql));
					if (balance != null && balance.compareTo(BigDecimal.ZERO) != 0) {
						// �Ǘ��c�����ݎؕs��v�ł��B �A���o�����X�z�F �A�A���o�����X�N�x�F
						throw new TException(getMessage("W00091", getWord("C11228") + balance.toString() + "�A"
							+ getWord("C11229") + Integer.toString(nendo)));
					}
				}

			}

			// 7.���c���X�V
			sql =
				" INSERT INTO AP_ZAN ( " +
					" KAI_CODE, " +
					" ZAN_DEN_DATE, " +
					" ZAN_DEN_NO, " +
					" ZAN_GYO_NO, " +
					" ZAN_DC_KBN, " +
					" ZAN_KMK_CODE, " +
					" ZAN_HKM_CODE, " +
					" ZAN_UKM_CODE, " +
					" ZAN_DEP_CODE, " +
					" ZAN_UKE_DEP_CODE, " +
					" ZAN_TEK_CODE, " +
					" ZAN_TEK, " +
					" ZAN_SIHA_CODE, " +
					" ZAN_KIN, " +
					" ZAN_SIHA_DATE, " +
					" ZAN_SIHA_KBN, " +
					" ZAN_HOH_CODE, " +
					" ZAN_HORYU_KBN, " +
					" ZAN_KESAI_KBN, " +
					" ZAN_SEI_NO, " +
					" ZAN_DATA_KBN, " +
					" ZAN_NAI_CODE, " +
					" ZAN_LIST_KBN, " +
					" ZAN_SIHA_KAGEN_KBN, " +
					" ZAN_TEG_KAGEN_KBN, " +
					" ZAN_FURI_KAGEN_KBN, " +
					" ZAN_INP_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" ZAN_TJK_CODE, " +
					" ZAN_CUR_CODE, " +
					" ZAN_CUR_RATE, " +
					" ZAN_IN_SIHA_KIN, " +
					" ZAN_SYS_KBN, " +
					" ZAN_DEN_SYU, " +
					" ZAN_FURI_CBK_CODE, " +
					" ZAN_FURI_BNK_CODE, " +
					" ZAN_FURI_STN_CODE, " +
					" ZAN_TEG_CBK_CODE, " +
					" ZAN_TEG_BNK_CODE, " +
					" ZAN_TEG_STN_CODE, " +
					" ZAN_SWK_GYO_NO " +
				" ) " +
				" SELECT /*+RULE*/ " +
					" swk.KAI_CODE, " +
					" swk.SWK_DEN_DATE, " +
					" swk.SWK_DEN_NO, " +
					" 1, " +
					" 1, " +
					" swk.SWK_KMK_CODE, " +
					" swk.SWK_HKM_CODE, " +
					" swk.SWK_UKM_CODE, " +
					" swk.SWK_DEP_CODE, " +
					" swk.SWK_UKE_DEP_CODE, " +
					" swk.SWK_TEK_CODE, " +
					" swk.SWK_TEK, " +
					" swk.SWK_TRI_CODE, " +
					" swk.SWK_SIHA_KIN, " +
					" swk.SWK_SIHA_DATE, " +
					" swk.SWK_SIHA_KBN, " +
					" swk.SWK_HOH_CODE, " +
					" swk.SWK_HORYU_KBN, " +
					" 0, " +
					" swk.SWK_SEI_NO, " +
					" swk.SWK_DATA_KBN, " +
					" hoh.HOH_NAI_CODE, " +
					" 0, " +
					" 0, " +
					" 0, " +
					" 0, " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" swk.SWK_TJK_CODE, " +
					" swk.SWK_CUR_CODE, " +
					" swk.SWK_CUR_RATE, " +
					" swk.SWK_IN_SIHA_KIN, " +
					" swk.SWK_SYS_KBN, " +
					" swk.SWK_DEN_SYU, " +
					" swk.SWK_CBK_CODE, " +
					" cbk.CBK_BNK_CODE, " +
					" cbk.CBK_STN_CODE, " +
					" swk.SWK_CBK_CODE, " +
					" cbk.CBK_BNK_CODE, " +
					" cbk.CBK_STN_CODE, " +
					" dtl.SWK_GYO_NO " +
			    " FROM /*+RULE*/ " +
			    	" AP_SWK_HDR swk " +
					" INNER JOIN SWK_DTL dtl " +
					" ON	swk.KAI_CODE = dtl.KAI_CODE " +
					" AND 	swk.SWK_DEN_NO = dtl.SWK_DEN_NO " +
					" AND 	swk.SWK_KMK_CODE = dtl.SWK_KMK_CODE " +
					" AND	NVL(swk.SWK_HKM_CODE, ' ') = NVL(dtl.SWK_HKM_CODE, ' ') " +
					" AND	NVL(swk.SWK_UKM_CODE, ' ') = NVL(dtl.SWK_UKM_CODE, ' ') " +
					" AND 	swk.SWK_SIHA_KIN = CASE WHEN dtl.SWK_DC_KBN = 1 THEN dtl.SWK_KIN ELSE dtl.SWK_KIN * -1 END " +
					" AND 	dtl.SWK_AUTO_KBN = 1 " +
					" AND 	dtl.SWK_BOOK_NO = 1 " +
					" AND 	dtl.SWK_ADJ_KBN IN (0, 1) " +
					" INNER JOIN KMK_MST kmk " +
					" ON	swk.KAI_CODE = kmk.KAI_CODE " +
					" AND 	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
					" AND 	kmk.KMK_CNT_AP = '01' " +
					" INNER JOIN AP_HOH_MST hoh " +
					" ON	swk.KAI_CODE = hoh.KAI_CODE " +
					" AND 	swk.SWK_HOH_CODE = hoh.HOH_HOH_CODE " +
					" LEFT OUTER JOIN AP_CBK_MST cbk " +
					" ON	swk.KAI_CODE = cbk.KAI_CODE " +
					" AND 	swk.SWK_CBK_CODE = cbk.CBK_CBK_CODE " +
					balanceSqlSwkWhere +
					" AND swk.SWK_DATA_KBN IN ('23', '2T') ";

			DBUtil.execute(conn, sql);
			
			// 8.���c���X�V
			sql =
				" INSERT INTO AR_ZAN ( " +
					" KAI_CODE, " +
					" ZAN_DEP_CODE, " +
					" ZAN_TRI_CODE, " +
					" ZAN_SEI_NO, " +
					" ZAN_SEI_DEN_DATE, " +
					" ZAN_SEI_DEN_NO, " +
					" ZAN_SEI_GYO_NO, " +
					" ZAN_DATA_KBN, " +
					" ZAN_KESI_DEN_DATE, " +
					" ZAN_KESI_DEN_NO, " +
					" ZAN_KESI_GYO_NO, " +
					" ZAN_KMK_CODE, " +
					" ZAN_HKM_CODE, " +
					" ZAN_UKM_CODE, " +
					" ZAN_AR_DATE, " +
					" ZAN_SEI_KIN, " +
					" ZAN_SEI_IN_KIN, " +
					" ZAN_KESI_KIN, " +
					" ZAN_KESI_IN_KIN, " +
					" ZAN_TRI_NAME, " +
					" INP_DATE, " +
					" UPD_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" ZAN_CBK_CODE, " +
					" ZAN_CUR_CODE, " +
					" ZAN_CUR_RATE " +
		        " ) " +
		        " SELECT /*+RULE*/ " +
					" swk.KAI_CODE, " +
					" swk.SWK_DEP_CODE, " +
					" swk.SWK_TRI_CODE, " +
					" swk.SWK_SEI_NO, " +
					" swk.SWK_DEN_DATE, " +
					" swk.SWK_DEN_NO, " +
					" dtl.SWK_GYO_NO, " +
					" swk.SWK_DATA_KBN, " +
					" NULL, " +
					" NULL, " +
					" NULL, " +
					" swk.SWK_KMK_CODE, " +
					" swk.SWK_HKM_CODE, " +
					" swk.SWK_UKM_CODE, " +
					" swk.SWK_AR_DATE, " +
					" swk.SWK_KIN, " +
					" swk.SWK_IN_KIN, " +
					" NULL, " +
					" NULL, " +
					" tri.TRI_NAME_S, " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" NULL, " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" swk.SWK_CBK_CODE, " +
					" swk.SWK_CUR_CODE, " +
					" swk.SWK_CUR_RATE " +
		        " FROM " +
		        	" AR_SWK_HDR swk " +
					" INNER JOIN SWK_DTL dtl " +
					" ON	swk.KAI_CODE = dtl.KAI_CODE " +
					" AND 	swk.SWK_DEN_NO = dtl.SWK_DEN_NO " +
					" AND 	swk.SWK_KMK_CODE = dtl.SWK_KMK_CODE " +
					" AND	NVL(swk.SWK_HKM_CODE, ' ') = NVL(dtl.SWK_HKM_CODE, ' ') " +
					" AND	NVL(swk.SWK_UKM_CODE, ' ') = NVL(dtl.SWK_UKM_CODE, ' ') " +
					" AND 	swk.SWK_KIN = CASE WHEN dtl.SWK_DC_KBN = 0 THEN dtl.SWK_KIN ELSE dtl.SWK_KIN * -1 END " +
					" AND 	dtl.SWK_AUTO_KBN = 1 " +
					" AND 	dtl.SWK_BOOK_NO = 1 " +
					" AND 	dtl.SWK_ADJ_KBN IN (0, 1) " +
					" INNER JOIN KMK_MST kmk " +
					" ON		swk.KAI_CODE = kmk.KAI_CODE " +
					" AND		swk.SWK_KMK_CODE = kmk.KMK_CODE " +
					" AND		kmk.KMK_CNT_AR = '01' " +
					" INNER JOIN TRI_MST tri " +
					" ON		swk.KAI_CODE = tri.KAI_CODE " +
					" AND		swk.SWK_TRI_CODE = tri.TRI_CODE " +
					balanceSqlSwkWhere +
					" AND swk.SWK_DATA_KBN IN ('31', '33') ";

			DBUtil.execute(conn, sql);

			// 9.������f�[�^�o�^
			sql =
				" INSERT INTO AR_KAI_DAT ( " +
					" KAI_CODE, " +
					" KAI_DEN_DATE, " +
					" KAI_DEN_NO, " +
					" KAI_GYO_NO, " +
					" KAI_DC_KBN, " +
					" KAI_KMK_CODE, " +
					" KAI_HKM_CODE, " +
					" KAI_UKM_CODE, " +
					" KAI_DEP_CODE, " +
					" KAI_KIN, " +
					" KAI_TRI_CODE, " +
					" KAI_TRI_NAME, " +
					" KAI_DATA_KBN, " +
					" KAI_KESI_DEN_DATE, " +
					" KAI_KESI_DEN_NO, " +
					" KAI_INP_DATE, " +
					" UPD_DATE, " +
					" PRG_ID, " +
					" USR_ID, " +
					" KAI_IN_KIN, " +
					" KAI_CUR_CODE, " +
					" KAI_CUR_RATE, " +
					" KAI_GYO_TEK_CODE, " +
					" KAI_GYO_TEK " +
				" ) " +
				" SELECT /*+RULE*/ " +
					" swk.KAI_CODE, " +
					" swk.SWK_DEN_DATE, " +
					" swk.SWK_DEN_NO, " +
					" swk.SWK_GYO_NO, " +
					" swk.SWK_DC_KBN, " +
					" swk.SWK_KMK_CODE, " +
					" swk.SWK_HKM_CODE, " +
					" swk.SWK_UKM_CODE, " +
					" swk.SWK_DEP_CODE, " +
					" CASE WHEN swk.SWK_DC_KBN = 0 THEN -swk.SWK_KIN ELSE swk.SWK_KIN END, " +
					" swk.SWK_TRI_CODE, " +
					" tri.TRI_NAME_S, " +
					" swk.SWK_DATA_KBN, " +
					" NULL, " +
					" NULL, " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" NULL, " +
					SQLUtil.getParam(getProgramCode()) + ", " +
					SQLUtil.getParam(getUserCode()) + ", " +
					" CASE WHEN swk.SWK_DC_KBN = 0 THEN -swk.SWK_IN_KIN ELSE swk.SWK_IN_KIN END, " +
					" swk.SWK_CUR_CODE, " +
					" swk.SWK_CUR_RATE, " +
					" swk.SWK_GYO_TEK_CODE, " +
					" swk.SWK_GYO_TEK " +
			    " FROM " +
					" SWK_DTL swk " +
					" INNER JOIN SWK_KMK_MST kmk " +
					" ON	swk.KAI_CODE = kmk.KAI_CODE " +
					" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
					" AND	NVL(swk.SWK_HKM_CODE,' ') = NVL(kmk.HKM_CODE,' ') " +
					" AND	NVL(swk.SWK_UKM_CODE,' ') = NVL(kmk.UKM_CODE,' ') " +
					" AND 	kmk.KMK_CNT = 7 " +
					" LEFT OUTER JOIN TRI_MST tri " +
					" ON   	swk.KAI_CODE = tri.KAI_CODE " +
					" AND 	swk.SWK_TRI_CODE = tri.TRI_CODE " +
					balanceSqlSwkWhere +
					" AND	swk.SWK_KESI_KBN = 0 " + // �����敪=0
					" AND	swk.SWK_DATA_KBN NOT IN ('14', '15') " + // ���ϓ`�[���O
					" AND	(swk.SWK_DC_KBN = 1 " +
					" OR 	(swk.SWK_DC_KBN = 0 " +
					" AND 	swk.SWK_DATA_KBN NOT IN ('13', '32'))) " +
					" AND	swk.SWK_ADJ_KBN IN (0, 1) " +
					" AND	swk.SWK_BOOK_NO = 1 ";

			DBUtil.execute(conn, sql);

			if (!isNotCreateCF) {
				// CF�f�[�^�쐬�̓v���p�e�B�ɂ��A�쐬�s�v�̏ꍇ�A�����s�v
				
				// 10.�a���̑��薾�דo�^
				// 10.1 ���a�����������Ă���`�[�ŁA���a���̑���Ȗڂ�o�^����B
				sql =
					" INSERT INTO CFL_DTL ( " +
						" KAI_CODE, " +
						" CFL_SWK_DEN_DATE, " +
						" CFL_SWK_DEN_NO, " +
						" SWK_DEN_DATE, " +
						" SWK_DEN_NO, " +
						" SWK_GYO_NO, " +
						" SWK_GYO_TEK, " +
						" SWK_DEP_CODE, " +
						" SWK_KMK_CODE, " +
						" SWK_HKM_CODE, " +
						" SWK_UKM_CODE, " +
						" SWK_TRI_CODE, " +
						" SWK_EMP_CODE, " +
						" SWK_KNR_CODE_1, " +
						" SWK_KNR_CODE_2, " +
						" SWK_KNR_CODE_3, " +
						" SWK_KNR_CODE_4, " +
						" SWK_KNR_CODE_5, " +
						" SWK_KNR_CODE_6, " +
						" SWK_CUR_CODE, " +
						" SWK_DC_KBN, " +
						" SWK_KIN, " +
						" SWK_IN_KIN, " +
						" DEN_SYU_CODE, " +
						" SWK_BOOK_NO, " +
						" SWK_ADJ_KBN " +
					" ) " +
					" SELECT /*+RULE*/ " +
						" swk.KAI_CODE, " +
						" swk.SWK_DEN_DATE, " +
						" swk.SWK_DEN_NO, " +
						" swk.SWK_DEN_DATE, " +
						" swk.SWK_DEN_NO, " +
						" swk.SWK_GYO_NO, " +
						" swk.SWK_GYO_TEK, " +
						" swk.SWK_DEP_CODE, " +
						" swk.SWK_KMK_CODE, " +
						" swk.SWK_HKM_CODE, " +
						" swk.SWK_UKM_CODE, " +
						" swk.SWK_TRI_CODE, " +
						" swk.SWK_EMP_CODE, " +
						" swk.SWK_KNR_CODE_1, " +
						" swk.SWK_KNR_CODE_2, " +
						" swk.SWK_KNR_CODE_3, " +
						" swk.SWK_KNR_CODE_4, " +
						" swk.SWK_KNR_CODE_5, " +
						" swk.SWK_KNR_CODE_6, " +
						" swk.SWK_CUR_CODE, " +
						" swk.SWK_DC_KBN, " +
						" swk.SWK_KIN, " +
						" swk.SWK_IN_KIN, " +
						" swk.DEN_SYU_CODE, " +
						" swk.SWK_BOOK_NO, " +
						" swk.SWK_ADJ_KBN " +
					" FROM " +
					    " SWK_DTL swk " +
					    " INNER JOIN KMK_MST kmk " +
						" ON	swk.KAI_CODE = kmk.KAI_CODE " +
						" AND 	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
						" AND 	kmk.KMK_CNT_GL <> '04' " +

						// GL�Ȗڋ敪�F�����Ȗڂ̎d�󂪑��݂��Ă���`�[ ���Ώ�
						" INNER JOIN ( " +
							" SELECT   swk2.KAI_CODE, swk2.SWK_DEN_DATE, swk2.SWK_DEN_NO, swk2.SWK_BOOK_NO, swk2.SWK_ADJ_KBN " +
							" FROM SWK_DTL swk2 " +
							" INNER JOIN KMK_MST kmk2 " +
							" ON  swk2.KAI_CODE     = kmk2.KAI_CODE " +
							" AND swk2.SWK_KMK_CODE = kmk2.KMK_CODE " +
							" AND kmk2.KMK_CNT_GL   = '04' " +
							" GROUP BY swk2.KAI_CODE, swk2.SWK_DEN_DATE, swk2.SWK_DEN_NO, swk2.SWK_BOOK_NO, swk2.SWK_ADJ_KBN " +
						" ) swk2" + 
						" ON  swk.KAI_CODE     = swk2.KAI_CODE " +
						" AND swk.SWK_DEN_DATE = swk2.SWK_DEN_DATE " +
						" AND swk.SWK_DEN_NO   = swk2.SWK_DEN_NO " +
						" AND swk.SWK_BOOK_NO  = swk2.SWK_BOOK_NO " +
						" AND swk.SWK_ADJ_KBN  = swk2.SWK_ADJ_KBN " +
						sqlSwkWhere + 
						" AND NVL(swk.SWK_KIN ,0) <> 0 ";	
				DBUtil.execute(conn, sql);
	
				// 10.2 ���̏����`�[�������ꍇ�A����Ȗڂ𖢕�������v�㎞�̑���ɕύX
				// �� �y���v��`�[�z��p / �����̔�p���܂ők��
				// �o���`�[�̖����Ȗڂ��폜
				sql =
					" DELETE " +
					" FROM	CFL_DTL " +
					" WHERE EXISTS ( " +
						" SELECT	1 " +
						" FROM " +
							" AP_ZAN apzan " +
	        			" WHERE	CFL_DTL.KAI_CODE = apzan.KAI_CODE " +
	        			" AND	CFL_DTL.SWK_DEN_NO = apzan.ZAN_SIHA_DEN_NO " +
	            		// TODO �ȉ��̏����s�v�B
	            		// ���O��INSERT�Ώۂ̒��o�����Əd��&&�o���`�[�E���v��`�[�̕R�t����AP_ZAN�݂̂ŉ\
	        			" AND	CFL_DTL.SWK_KMK_CODE = apzan.ZAN_KMK_CODE " +
	        			" AND	NVL(CFL_DTL.SWK_HKM_CODE, ' ') = NVL(apzan.ZAN_HKM_CODE, ' ') " +
	        			" AND	NVL(CFL_DTL.SWK_UKM_CODE, ' ') = NVL(apzan.ZAN_UKM_CODE, ' ') " +

	        			" AND apzan.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +  // ���X�|���X����ׁ̈A�����ǉ�

        			" ) " +
	        		" AND	EXISTS ( " +
	        			" SELECT	1 " +
	        			" FROM " +
	        				" SWK_DTL swk " +
							" INNER JOIN KMK_MST kmk " +
							" ON	swk.KAI_CODE = kmk.KAI_CODE " +
							" AND 	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
							" AND 	kmk.KMK_CNT_GL = '04' " +
	        			sqlSwkWhere +
	        				" AND	CFL_DTL.KAI_CODE = swk.KAI_CODE " +
	        				" AND	CFL_DTL.SWK_DEN_DATE = swk.SWK_DEN_DATE " +
	        				" AND	CFL_DTL.SWK_DEN_NO = swk.SWK_DEN_NO " +
	        		" ) " +
				" AND KAI_CODE = " + SQLUtil.getParam(company.getCode()); // ���X�|���X����ׁ̈A�����ǉ�
	
				// �����ǉ��F-- �ꕔ�x���i�����j�͑ΏۊO
				sql +=
					" AND NOT EXISTS (  " +
					"     SELECT 1  FROM ( " +
					"         SELECT " +
					"             apzan1.KAI_CODE" +
					"           , apzan1.ZAN_JSK_DATE" +
					"           , apzan1.ZAN_SIHA_DEN_NO" +
					"         FROM AP_ZAN apzan1" +
					"             INNER JOIN ( " +
					"                 SELECT" +
					"                     KAI_CODE" +
					"                   , ZAN_DEN_DATE  " +
					"                   , ZAN_DEN_NO" +
					"                   , COUNT(*)" +
					"                 FROM AP_ZAN" +
					"                 GROUP BY " +
					"                     KAI_CODE" +
					"                   , ZAN_DEN_DATE  " +
					"                   , ZAN_DEN_NO" +
					"                 HAVING COUNT(*) <> 1" +
					"             ) apzan3" +
					"             ON  apzan1.KAI_CODE = apzan3.KAI_CODE " +
					"             AND apzan1.ZAN_DEN_DATE = apzan3.ZAN_DEN_DATE " +
					"             AND apzan1.ZAN_DEN_NO = apzan3.ZAN_DEN_NO " +
					"         WHERE apzan1.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"         AND   apzan1.ZAN_SIHA_DEN_NO IN ( " +
					"             SELECT SWK_DEN_NO " +
					"             FROM T_DAILY_CLOSE " +
					"             WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"         ) " +
					"     ) apzan2" +
					"     WHERE apzan2.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     AND   apzan2.ZAN_JSK_DATE <= " + SQLUtil.getYYYYMMDDParam(date) + 
					"     AND   CFL_DTL.KAI_CODE   = apzan2.KAI_CODE  " +
					"     AND   CFL_DTL.SWK_DEN_NO = apzan2.ZAN_SIHA_DEN_NO " +
					" )  " +
					" AND KAI_CODE = " + SQLUtil.getParam(company.getCode());

				DBUtil.execute(conn, sql);
	
				// ���v��̖����ȖڈȊO��o�^
				sql =
					" INSERT INTO CFL_DTL ( " +
						" KAI_CODE, " +
						" CFL_SWK_DEN_DATE, " +
						" CFL_SWK_DEN_NO, " +
						" SWK_DEN_DATE, " +
						" SWK_DEN_NO, " +
						" SWK_GYO_NO, " +
						" SWK_GYO_TEK, " +
						" SWK_DEP_CODE, " +
						" SWK_KMK_CODE, " +
						" SWK_HKM_CODE, " +
						" SWK_UKM_CODE, " +
						" SWK_TRI_CODE, " +
						" SWK_EMP_CODE, " +
						" SWK_KNR_CODE_1, " +
						" SWK_KNR_CODE_2, " +
						" SWK_KNR_CODE_3, " +
						" SWK_KNR_CODE_4, " +
						" SWK_KNR_CODE_5, " +
						" SWK_KNR_CODE_6, " +
						" SWK_CUR_CODE, " +
						" SWK_DC_KBN, " +
						" SWK_KIN, " +
						" SWK_IN_KIN, " +
						" DEN_SYU_CODE, " +
						" SWK_BOOK_NO, " +
						" SWK_ADJ_KBN " +
					" ) " +
					" SELECT /*+RULE*/ " +
						" swk2.KAI_CODE, " +
						" apzan2.ZAN_JSK_DATE, " +
						" apzan2.ZAN_SIHA_DEN_NO, " + // �x���`�[�ԍ�
						" swk2.SWK_DEN_DATE, " +
						" swk2.SWK_DEN_NO, " + // ���v��`�[�ԍ�
						" swk2.SWK_GYO_NO, " +
						" swk2.SWK_GYO_TEK, " +
						" swk2.SWK_DEP_CODE, " +
						" swk2.SWK_KMK_CODE, " +
						" swk2.SWK_HKM_CODE, " +
						" swk2.SWK_UKM_CODE, " +
						" swk2.SWK_TRI_CODE, " +
						" swk2.SWK_EMP_CODE, " +
						" swk2.SWK_KNR_CODE_1, " +
						" swk2.SWK_KNR_CODE_2, " +
						" swk2.SWK_KNR_CODE_3, " +
						" swk2.SWK_KNR_CODE_4, " +
						" swk2.SWK_KNR_CODE_5, " +
						" swk2.SWK_KNR_CODE_6, " +
						" swk2.SWK_CUR_CODE, " +
						" swk2.SWK_DC_KBN, " +
						" swk2.SWK_KIN, " +
						" swk2.SWK_IN_KIN, " +
						" swk2.DEN_SYU_CODE, " +
						" swk2.SWK_BOOK_NO, " +
						" swk2.SWK_ADJ_KBN " +
					" FROM " +
					    " SWK_DTL swk2 " +
					    " INNER JOIN KMK_MST kmk " +
						" ON	swk2.KAI_CODE = kmk.KAI_CODE " +
						" AND 	swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
						" INNER JOIN AP_ZAN apzan2 " +
						" ON	swk2.KAI_CODE = apzan2.KAI_CODE " +
						" AND	swk2.SWK_DEN_NO = apzan2.ZAN_DEN_NO " +
						" AND	NOT (swk2.SWK_KMK_CODE = apzan2.ZAN_KMK_CODE " +
						" AND	NVL(swk2.SWK_HKM_CODE, ' ') = NVL(apzan2.ZAN_HKM_CODE, ' ') " +
						" AND	NVL(swk2.SWK_UKM_CODE, ' ') = NVL(apzan2.ZAN_UKM_CODE, ' ') " +
						" AND 	swk2.SWK_AUTO_KBN = 1) " +

						// �x���`�[�쐬�� �̓`�[���Ώ�
						" INNER JOIN ( " +
							"SELECT DISTINCT apzan.KAI_CODE, apzan.ZAN_DEN_NO " +
							" FROM SWK_DTL swk " +
							" INNER JOIN AP_ZAN apzan " +
							" ON  swk.KAI_CODE   = apzan.KAI_CODE " +
							" AND swk.SWK_DEN_NO = apzan.ZAN_SIHA_DEN_NO " +
							// GL�Ȗڋ敪�F�����Ȗڂ̎d�󂪑��݂��Ă���`�[ ���Ώ�
							" INNER JOIN ( " +
								" SELECT   swk3.KAI_CODE, swk3.SWK_DEN_DATE, swk3.SWK_DEN_NO, swk3.SWK_BOOK_NO, swk3.SWK_ADJ_KBN " +
								" FROM SWK_DTL swk3 " +
								" INNER JOIN KMK_MST kmk3 " +
								" ON  swk3.KAI_CODE     = kmk3.KAI_CODE " +
								" AND swk3.SWK_KMK_CODE = kmk3.KMK_CODE " +
								" AND kmk3.KMK_CNT_GL   = '04' " +
								" GROUP BY swk3.KAI_CODE, swk3.SWK_DEN_DATE, swk3.SWK_DEN_NO, swk3.SWK_BOOK_NO, swk3.SWK_ADJ_KBN " +
							" ) swk3" + 
							" ON  swk.KAI_CODE     = swk3.KAI_CODE " +
							" AND swk.SWK_DEN_DATE = swk3.SWK_DEN_DATE " +
							" AND swk.SWK_DEN_NO   = swk3.SWK_DEN_NO " +
							" AND swk.SWK_BOOK_NO  = swk3.SWK_BOOK_NO " +
							" AND swk.SWK_ADJ_KBN  = swk3.SWK_ADJ_KBN " +

							sqlSwkWhere +
						" ) swk" + 
						" ON  swk2.KAI_CODE   = swk.KAI_CODE " +
						" AND swk2.SWK_DEN_NO = swk.ZAN_DEN_NO ";
				// �����ǉ��F-- �ꕔ�x���i�����j�͑ΏۊO
				sql +=
					" INNER JOIN ( " +
					"     SELECT  " +
					"         apzan2.KAI_CODE " +
					"       , apzan2.ZAN_DEN_DATE " +
					"       , apzan2.ZAN_DEN_NO " +
					"     FROM AP_ZAN apzan2 " +
					"     WHERE apzan2.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     AND apzan2.ZAN_SIHA_DEN_NO IN ( " +
					"         SELECT SWK_DEN_NO " +
					"         FROM T_DAILY_CLOSE " +
					"         WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     ) " +
					"     AND NOT EXISTS ( " +
					"         SELECT 1 FROM ( " +
					"             SELECT " +
					"                 apzan1.KAI_CODE " +
					"               , apzan1.ZAN_JSK_DATE " +
					"               , apzan1.ZAN_SIHA_DEN_NO " +
					"             FROM AP_ZAN apzan1 " +
					"             INNER JOIN ( " +
					"                 SELECT " +
					"                     KAI_CODE " +
					"                   , ZAN_DEN_DATE " +
					"                   , ZAN_DEN_NO " +
					"                   , COUNT(*) " +
					"                 FROM AP_ZAN " +
					"                 GROUP BY  " +
					"                     KAI_CODE " +
					"                   , ZAN_DEN_DATE " +
					"                   , ZAN_DEN_NO " +
					"                 HAVING COUNT(*) <> 1 " +
					"             ) apzan3 " +
					"             ON  apzan1.KAI_CODE = apzan3.KAI_CODE " +
					"             AND apzan1.ZAN_DEN_DATE = apzan3.ZAN_DEN_DATE " +
					"             AND apzan1.ZAN_DEN_NO = apzan3.ZAN_DEN_NO " +
					"             WHERE apzan1.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"             AND apzan1.ZAN_SIHA_DEN_NO IN ( " +
					"                 SELECT SWK_DEN_NO " +
					"                 FROM T_DAILY_CLOSE " +
					"                 WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"             ) " +
					"         ) apzan4 " +
					"         WHERE apzan2.KAI_CODE = apzan4.KAI_CODE " +
					"         AND   apzan2.ZAN_JSK_DATE = apzan4.ZAN_JSK_DATE " +
					"         AND   apzan2.ZAN_SIHA_DEN_NO = apzan4.ZAN_SIHA_DEN_NO " +
					"     ) " +
					" ) apzan3 " +
					" ON  apzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND apzan3.ZAN_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) + 
					" AND swk2.KAI_CODE   = apzan3.KAI_CODE " +
					" AND swk2.SWK_DEN_NO = apzan3.ZAN_DEN_NO ";
				
				DBUtil.execute(conn, sql);
	
				// 10.3 �Ј��x���̏����`�[�������ꍇ�A����Ȗڂ𖢕�����Ј��x�����̑���ɕύX
				// �� �y�Ј��x���`�[�z��p / �����̔�p���܂ők��
				sql =
					" DELETE " +
					" FROM	CFL_DTL " +
					" WHERE EXISTS ( " +
						" SELECT	1 " +
						" FROM " +
							" AP_SWK_HDR hdr " +
	        			" WHERE	CFL_DTL.KAI_CODE = hdr.KAI_CODE " +
	        			" AND	CFL_DTL.SWK_DEN_NO = hdr.SWK_SIHA_DEN_NO " +
	        			" AND	CFL_DTL.SWK_KMK_CODE = hdr.SWK_KMK_CODE " +
	        			" AND	NVL(CFL_DTL.SWK_HKM_CODE, ' ') = NVL(hdr.SWK_HKM_CODE, ' ') " +
	        			" AND	NVL(CFL_DTL.SWK_UKM_CODE, ' ') = NVL(hdr.SWK_UKM_CODE, ' ') " +
						" AND   hdr.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + // ���X�|���X����ׁ̈A�����ǉ�
	        		" ) " +
        		" AND KAI_CODE = " + SQLUtil.getParam(company.getCode()); // ���X�|���X����ׁ̈A�����ǉ�
				// -- ���a���Ȗڂ��܂ޓ`�[��ΏۂƂ��������ǉ�				
				sql +=
					" AND EXISTS ( " +
					"     SELECT 1 FROM  SWK_DTL swk  " +
					"         INNER JOIN KMK_MST kmk  " +
					"         ON  swk.KAI_CODE = kmk.KAI_CODE  " +
					"         AND swk.SWK_KMK_CODE = kmk.KMK_CODE  " +
					"         AND kmk.KMK_CNT_GL = '04'  " +
					"     WHERE swk.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     AND   swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
					"     AND   swk.SWK_UPD_KBN = 3 " +
					"     AND   swk.SWK_DEN_NO IN ( " +
					"         SELECT SWK_DEN_NO " +
					"         FROM T_DAILY_CLOSE" +
					"         WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					"     ) " +
					"     AND CFL_DTL.KAI_CODE = swk.KAI_CODE  " +
					"     AND CFL_DTL.SWK_DEN_DATE = swk.SWK_DEN_DATE  " +
					"     AND CFL_DTL.SWK_DEN_NO = swk.SWK_DEN_NO  " +
					" ) " +
					" AND KAI_CODE = " + SQLUtil.getParam(company.getCode());
				
				DBUtil.execute(conn, sql);
				
				// �Ј��x���̖����ȖڈȊO��o�^
				sql =
					" INSERT INTO CFL_DTL ( " +
						" KAI_CODE, " +
						" CFL_SWK_DEN_DATE, " +
						" CFL_SWK_DEN_NO, " +
						" SWK_DEN_DATE, " +
						" SWK_DEN_NO, " +
						" SWK_GYO_NO, " +
						" SWK_GYO_TEK, " +
						" SWK_DEP_CODE, " +
						" SWK_KMK_CODE, " +
						" SWK_HKM_CODE, " +
						" SWK_UKM_CODE, " +
						" SWK_TRI_CODE, " +
						" SWK_EMP_CODE, " +
						" SWK_KNR_CODE_1, " +
						" SWK_KNR_CODE_2, " +
						" SWK_KNR_CODE_3, " +
						" SWK_KNR_CODE_4, " +
						" SWK_KNR_CODE_5, " +
						" SWK_KNR_CODE_6, " +
						" SWK_CUR_CODE, " +
						" SWK_DC_KBN, " +
						" SWK_KIN, " +
						" SWK_IN_KIN, " +
						" DEN_SYU_CODE, " +
						" SWK_BOOK_NO, " +
						" SWK_ADJ_KBN " +
					" ) " +
					" SELECT /*+RULE*/ " +
						" swk2.KAI_CODE, " +
						" hdr2.SWK_JSK_DATE, " +
						" hdr2.SWK_SIHA_DEN_NO, " + // �x���`�[�ԍ�
						" swk2.SWK_DEN_DATE, " +
						" swk2.SWK_DEN_NO, " + // ���v��`�[�ԍ�
						" swk2.SWK_GYO_NO, " +
						" swk2.SWK_GYO_TEK, " +
						" swk2.SWK_DEP_CODE, " +
						" swk2.SWK_KMK_CODE, " +
						" swk2.SWK_HKM_CODE, " +
						" swk2.SWK_UKM_CODE, " +
						" swk2.SWK_TRI_CODE, " +
						" swk2.SWK_EMP_CODE, " +
						" swk2.SWK_KNR_CODE_1, " +
						" swk2.SWK_KNR_CODE_2, " +
						" swk2.SWK_KNR_CODE_3, " +
						" swk2.SWK_KNR_CODE_4, " +
						" swk2.SWK_KNR_CODE_5, " +
						" swk2.SWK_KNR_CODE_6, " +
						" swk2.SWK_CUR_CODE, " +
						" swk2.SWK_DC_KBN, " +
						" swk2.SWK_KIN, " +
						" swk2.SWK_IN_KIN, " +
						" swk2.DEN_SYU_CODE, " +
						" swk2.SWK_BOOK_NO, " +
						" swk2.SWK_ADJ_KBN " +
					" FROM " +
					    " SWK_DTL swk2 " +
					    " INNER JOIN KMK_MST kmk " +
						" ON	swk2.KAI_CODE = kmk.KAI_CODE " +
						" AND 	swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
						" INNER JOIN AP_SWK_HDR hdr2 " +
						" ON	swk2.KAI_CODE = hdr2.KAI_CODE " +
						" AND	swk2.SWK_DEN_NO = hdr2.SWK_DEN_NO " +
						" AND	NOT (swk2.SWK_KMK_CODE = hdr2.SWK_KMK_CODE " +
						" AND	NVL(swk2.SWK_HKM_CODE, ' ') = NVL(hdr2.SWK_HKM_CODE, ' ') " +
						" AND	NVL(swk2.SWK_UKM_CODE, ' ') = NVL(hdr2.SWK_UKM_CODE, ' ') " +
						" ) " +
						// ����̓����X�V�Ώۂ̂����A�Ј��x���`�[�쐬�� ���Ώ�
						" INNER JOIN ( " +
							" SELECT   hdr.KAI_CODE, hdr.SWK_DEN_NO " +
							" FROM SWK_DTL swk " +
							" INNER JOIN AP_SWK_HDR hdr " +
							" ON  swk.KAI_CODE   = hdr.KAI_CODE " +
							" AND swk.SWK_DEN_NO = hdr.SWK_SIHA_DEN_NO " +
							
							// ���a���Ȗڂ��܂ޓ`�[��ΏۂƂ��������ǉ�
							"            INNER JOIN ( " + 
							"                SELECT " + 
							"                  swk3.KAI_CODE " + 
							"                , swk3.SWK_DEN_DATE " + 
							"                , swk3.SWK_DEN_NO " + 
							"                , swk3.SWK_BOOK_NO " + 
							"                , swk3.SWK_ADJ_KBN " + 
							"                FROM SWK_DTL swk3 " + 
							"                    INNER JOIN KMK_MST kmk3 " + 
							"                    ON  swk3.KAI_CODE     = kmk3.KAI_CODE " + 
							"                    AND swk3.SWK_KMK_CODE = kmk3.KMK_CODE " + 
							"                    AND kmk3.KMK_CNT_GL   = '04' " + 
							"                    GROUP BY swk3.KAI_CODE " + 
							"                    , swk3.SWK_DEN_DATE " + 
							"                    , swk3.SWK_DEN_NO " + 
							"                    , swk3.SWK_BOOK_NO " + 
							"                    , swk3.SWK_ADJ_KBN " + 
							"            ) swk3 " + 
							"            ON  swk.KAI_CODE     = swk3.KAI_CODE " + 
							"            AND swk.SWK_DEN_DATE = swk3.SWK_DEN_DATE " + 
							"            AND swk.SWK_DEN_NO   = swk3.SWK_DEN_NO " + 
							"            AND swk.SWK_BOOK_NO  = swk3.SWK_BOOK_NO " + 
							"            AND swk.SWK_ADJ_KBN  = swk3.SWK_ADJ_KBN " + 
							sqlSwkWhere +
							" GROUP BY hdr.KAI_CODE, hdr.SWK_DEN_NO " +
						" ) hdr" + 
						" ON  swk2.KAI_CODE   = hdr.KAI_CODE " +
						" AND swk2.SWK_DEN_NO = hdr.SWK_DEN_NO ";
	
				DBUtil.execute(conn, sql);
	
				// -- 10.4 ���̏����`�[�������ꍇ�A����Ȗڂ𖢎�������v�㎞�̑���ɕύX
				// -- �� �y���v��`�[�z���� / ���v�̎��v���܂ők��
				// -- �����`�[�̖����Ȗڂ��폜
				sql =
				" DELETE FROM CFL_DTL " + 
				" WHERE  EXISTS" + 
				" (SELECT 1" + 
				"       FROM   AR_ZAN arzan" + 
				"       WHERE  CFL_DTL.KAI_CODE = arzan.KAI_CODE" + 
				"       AND    CFL_DTL.SWK_DEN_NO = arzan.ZAN_KESI_DEN_NO" + 
				"       AND    CFL_DTL.SWK_GYO_NO = arzan.ZAN_KESI_GYO_NO" + 
				"       AND    CFL_DTL.SWK_KMK_CODE = arzan.ZAN_KMK_CODE" + 
				"       AND    NVL(CFL_DTL.SWK_HKM_CODE, ' ') = NVL(arzan.ZAN_HKM_CODE, ' ')" + 
				"       AND    NVL(CFL_DTL.SWK_UKM_CODE, ' ') = NVL(arzan.ZAN_UKM_CODE, ' ')" + 
				"       AND    arzan.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + " )" + 
				" AND    EXISTS" + 
				" (SELECT 1" + 
				"       FROM   SWK_DTL swk" + 
				"       INNER  JOIN KMK_MST kmk ON swk.KAI_CODE = kmk.KAI_CODE" + 
				"                              AND swk.SWK_KMK_CODE = kmk.KMK_CODE" + 
				"                              AND kmk.KMK_CNT_GL = '04'" + 
				"       WHERE  swk.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + 
				"       AND    swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) + 
				"       AND    swk.SWK_UPD_KBN = 3" + 
				"       AND swk.SWK_DEN_NO IN ( " + 
				"             SELECT SWK_DEN_NO " + 
				"             FROM T_DAILY_CLOSE" + 
				"             WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +  
				"       ) " + 
				"       AND    CFL_DTL.KAI_CODE = swk.KAI_CODE" + 
				"       AND    CFL_DTL.SWK_DEN_DATE = swk.SWK_DEN_DATE" + 
				"       AND    CFL_DTL.SWK_DEN_NO = swk.SWK_DEN_NO)" + 
				// -- �ꕔ�����i�����j�͑ΏۊO" + 
				" AND    EXISTS" + 
				" (SELECT 1" + 
				"         FROM (" + 
				"                 SELECT" + 
				"                     kesi.KAI_CODE" + 
				"                     , sei.ZAN_SEI_DEN_DATE" + 
				"                     , sei.ZAN_SEI_DEN_NO" + 
				"                     , kesi.ZAN_KESI_DEN_DATE" + 
				"                     , kesi.ZAN_KESI_DEN_NO" + 
				"                     , kesi.ZAN_KESI_GYO_NO" + 
				"                     , sei.ZAN_SEI_IN_KIN" + 
				"                     , kesi.ZAN_KESI_IN_KIN" + 
				"                 FROM AR_ZAN kesi" + 
				"                     INNER JOIN AR_ZAN sei" + 
				"                         ON kesi.KAI_CODE = sei.KAI_CODE" + 
				"                         AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE" + 
				"                         AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO" + 
				"                         AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN" + 
				"                         AND sei.ZAN_DATA_KBN = '31'" + 
				"                 WHERE " + 
				"                     kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) + 
				"                     AND kesi.ZAN_DATA_KBN <> '31'    " + 
				"                 ) arzan2" + 
				"       WHERE  CFL_DTL.KAI_CODE = arzan2.KAI_CODE" + 
				"       AND    CFL_DTL.SWK_DEN_DATE = arzan2.ZAN_KESI_DEN_DATE" + 
				"       AND    CFL_DTL.SWK_DEN_NO = arzan2.ZAN_KESI_DEN_NO " +
				"       AND    CFL_DTL.SWK_GYO_NO = arzan2.ZAN_KESI_GYO_NO)";
				DBUtil.execute(conn, sql);

				// -- ���v��̖����ȖڈȊO��o�^
				sql =
				" INSERT INTO CFL_DTL ( " +
				"    KAI_CODE " +
				" ,  CFL_SWK_DEN_DATE " +
				" ,  CFL_SWK_DEN_NO " +
				" ,  SWK_DEN_DATE " +
				" ,  SWK_DEN_NO " +
				" ,  SWK_GYO_NO " +
				" ,  SWK_GYO_TEK " +
				" ,  SWK_DEP_CODE " +
				" ,  SWK_KMK_CODE " +
				" ,  SWK_HKM_CODE " +
				" ,  SWK_UKM_CODE " +
				" ,  SWK_TRI_CODE " +
				" ,  SWK_EMP_CODE " +
				" ,  SWK_KNR_CODE_1 " +
				" ,  SWK_KNR_CODE_2 " +
				" ,  SWK_KNR_CODE_3 " +
				" ,  SWK_KNR_CODE_4 " +
				" ,  SWK_KNR_CODE_5 " +
				" ,  SWK_KNR_CODE_6 " +
				" ,  SWK_CUR_CODE " +
				" ,  SWK_DC_KBN " +
				" ,  SWK_KIN " +
				" ,  SWK_IN_KIN " +
				" ,  DEN_SYU_CODE " +
				" ,  SWK_BOOK_NO " +
				" ,  SWK_ADJ_KBN   " +
				" )   " +
				" SELECT " +
				"    swk2.KAI_CODE " +
				" ,  arzan2.ZAN_KESI_DEN_DATE " +
				" ,  arzan2.ZAN_KESI_DEN_NO " +
				" ,  swk2.SWK_DEN_DATE " +
				" ,  swk2.SWK_DEN_NO " +
				" ,  swk2.SWK_GYO_NO " +
				" ,  swk2.SWK_GYO_TEK " +
				" ,  swk2.SWK_DEP_CODE " +
				" ,  swk2.SWK_KMK_CODE " +
				" ,  swk2.SWK_HKM_CODE " +
				" ,  swk2.SWK_UKM_CODE " +
				" ,  swk2.SWK_TRI_CODE " +
				" ,  swk2.SWK_EMP_CODE " +
				" ,  swk2.SWK_KNR_CODE_1 " +
				" ,  swk2.SWK_KNR_CODE_2 " +
				" ,  swk2.SWK_KNR_CODE_3 " +
				" ,  swk2.SWK_KNR_CODE_4 " +
				" ,  swk2.SWK_KNR_CODE_5 " +
				" ,  swk2.SWK_KNR_CODE_6 " +
				" ,  swk2.SWK_CUR_CODE " +
				" ,  swk2.SWK_DC_KBN " +
				" ,  swk2.SWK_KIN " +
				" ,  swk2.SWK_IN_KIN " +
				" ,  swk2.DEN_SYU_CODE " +
				" ,  swk2.SWK_BOOK_NO " +
				" ,  swk2.SWK_ADJ_KBN " +
				" FROM   SWK_DTL swk2 " +
				" INNER  JOIN KMK_MST kmk ON swk2.KAI_CODE = kmk.KAI_CODE " +
				"                        AND swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
				" INNER  JOIN AR_ZAN arzan2 ON swk2.KAI_CODE = arzan2.KAI_CODE " +
				"                          AND swk2.SWK_DEN_NO = arzan2.ZAN_SEI_DEN_NO " +
				"                          AND arzan2.ZAN_DATA_KBN <> '31' " +
				"                          AND NOT (swk2.SWK_KMK_CODE = arzan2.ZAN_KMK_CODE " +
				"                              AND NVL(swk2.SWK_HKM_CODE, ' ') = NVL(arzan2.ZAN_HKM_CODE, ' ') " +
				"                              AND NVL(swk2.SWK_UKM_CODE, ' ') = NVL(arzan2.ZAN_UKM_CODE, ' ') " +
				"                              AND swk2.SWK_AUTO_KBN = 1) " +
				" INNER JOIN (  " +
				" SELECT DISTINCT " +
				"   arzan.KAI_CODE " +
				" , arzan.ZAN_SEI_DEN_NO   " +
				" FROM SWK_DTL swk   " +
				"     INNER JOIN AR_ZAN arzan   " +
				"     ON  swk.KAI_CODE   = arzan.KAI_CODE   " +
				"     AND swk.SWK_DEN_NO = arzan.ZAN_KESI_DEN_NO   " +
				"     INNER JOIN (   " +
				"         SELECT " +
				"           swk3.KAI_CODE " +
				"         , swk3.SWK_DEN_DATE " +
				"         , swk3.SWK_DEN_NO " +
				"         , swk3.SWK_BOOK_NO " +
				"         , swk3.SWK_ADJ_KBN   " +
				"         FROM SWK_DTL swk3   " +
				"             INNER JOIN KMK_MST kmk3   " +
				"             ON  swk3.KAI_CODE     = kmk3.KAI_CODE   " +
				"             AND swk3.SWK_KMK_CODE = kmk3.KMK_CODE   " +
				"             AND kmk3.KMK_CNT_GL   = '04'   " +
				"             GROUP BY swk3.KAI_CODE " +
				"             , swk3.SWK_DEN_DATE " +
				"             , swk3.SWK_DEN_NO " +
				"             , swk3.SWK_BOOK_NO " +
				"             , swk3.SWK_ADJ_KBN   " +
				"     ) swk3  " +
				"     ON  swk.KAI_CODE     = swk3.KAI_CODE   " +
				"     AND swk.SWK_DEN_DATE = swk3.SWK_DEN_DATE   " +
				"     AND swk.SWK_DEN_NO   = swk3.SWK_DEN_NO   " +
				"     AND swk.SWK_BOOK_NO  = swk3.SWK_BOOK_NO   " +
				"     AND swk.SWK_ADJ_KBN  = swk3.SWK_ADJ_KBN   " +
				" WHERE swk.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				" AND    swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"  AND    swk.SWK_UPD_KBN = 3  " +
				" AND swk.SWK_DEN_NO IN (  " +
				"     SELECT SWK_DEN_NO  " +
				"     FROM T_DAILY_CLOSE " +
				"     WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				" )  " +
				" ) swk  " +
				" ON  swk2.KAI_CODE   = swk.KAI_CODE   " +
				" AND swk2.SWK_DEN_NO = swk.ZAN_SEI_DEN_NO  " +
				" INNER JOIN (  " +
				"             SELECT " +
				"                 kesi.KAI_CODE " +
				"                 , sei.ZAN_SEI_DEN_DATE " +
				"                 , sei.ZAN_SEI_DEN_NO " +
				"                 , kesi.ZAN_KESI_DEN_DATE " +
				"                 , kesi.ZAN_KESI_DEN_NO " +
				"                 , sei.ZAN_SEI_IN_KIN " +
				"                 , kesi.ZAN_KESI_IN_KIN " +
				"             FROM AR_ZAN kesi " +
				"                 INNER JOIN AR_ZAN sei " +
				"                     ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                     AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                     AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                     AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                     AND sei.ZAN_DATA_KBN = '31' " +
				"             WHERE  " +
				"                 kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                 AND kesi.ZAN_DATA_KBN <> '31'     " +
				"           ) arzan3 " +
				"         ON  arzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arzan3.ZAN_SEI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"         AND    swk2.KAI_CODE = arzan3.KAI_CODE " +
				"         AND    swk2.SWK_DEN_NO = arzan3.ZAN_SEI_DEN_NO ";
				DBUtil.execute(conn, sql);
				
				// -- 10.5 �����������ɂ����̏����`�[�������ꍇ�A����Ȗڂ𖢎�������v�㎞�̑���ɕύX
				// -- �� �y���v��`�[�z���� / ���v�̎��v���܂ők��
				// -- �����`�[�̖����Ȗڂ��폜
				sql = 
				" DELETE FROM CFL_DTL  " +
				" WHERE " +
				"     EXISTS (  " +
				"         SELECT " +
				"             1  " +
				"         FROM " +
				"             SWK_DTL swk  " +
				"             INNER JOIN AR_ZAN arzan  " +
				"                 ON swk.KAI_CODE = arzan.KAI_CODE  " +
				"                 AND swk.SWK_DEN_DATE = arzan.ZAN_KESI_DEN_DATE  " +
				"                 AND swk.SWK_DEN_NO = arzan.ZAN_KESI_DEN_NO  " +
				"             INNER JOIN AR_KAI_DAT arkai  " +
				"                 ON arzan.KAI_CODE = arkai.KAI_CODE  " +
				"                 AND arzan.ZAN_KESI_DEN_DATE = arkai.KAI_KESI_DEN_DATE  " +
				"                 AND arzan.ZAN_KESI_DEN_NO = arkai.KAI_KESI_DEN_NO  " +
				"                 AND arzan.ZAN_KESI_IN_KIN = arkai.KAI_IN_KIN  " +
				// -- �ꕔ�����i�����j�͑ΏۊO
				"             INNER JOIN (  " +
				"                         SELECT " +
				"                             kesi.KAI_CODE " +
				"                             , sei.ZAN_SEI_DEN_DATE " +
				"                             , sei.ZAN_SEI_DEN_NO " +
				"                             , kesi.ZAN_KESI_DEN_DATE " +
				"                             , kesi.ZAN_KESI_DEN_NO " +
				"                             , sei.ZAN_SEI_IN_KIN " +
				"                             , kesi.ZAN_KESI_IN_KIN " +
				"                         FROM " +
				"                             AR_ZAN kesi " +
				"                             INNER JOIN AR_ZAN sei " +
				"                                 ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                                 AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                                 AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                                 AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                                 AND sei.ZAN_DATA_KBN = '31' " +
				"                         WHERE  " +
				"                             kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                             AND kesi.ZAN_DATA_KBN <> '31'     " +
				"             ) kesi  " +
				"                 ON arkai.KAI_CODE = kesi.KAI_CODE  " +
				"                 AND arkai.KAI_KESI_DEN_DATE = kesi.ZAN_KESI_DEN_DATE  " +
				"                 AND arkai.KAI_KESI_DEN_NO = kesi.ZAN_KESI_DEN_NO  " +
				"         WHERE " +
				"             CFL_DTL.KAI_CODE = arkai.KAI_CODE  " +
				"             AND CFL_DTL.SWK_DEN_DATE = arkai.KAI_DEN_DATE  " +
				"             AND CFL_DTL.SWK_DEN_NO = arkai.KAI_DEN_NO  " +
				"             AND arkai.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             AND swk.SWK_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"             AND swk.SWK_UPD_KBN = 3  " +
				"             AND swk.SWK_DEN_NO IN (  " +
				"                 SELECT " +
				"                     SWK_DEN_NO  " +
				"                 FROM " +
				"                     T_DAILY_CLOSE  " +
				"                 WHERE " +
				"                     KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             ) " +
				"     ) ";
				DBUtil.execute(conn, sql);

				// -- ���v��̖����ȖڈȊO��o�^
				sql = 
				" INSERT INTO CFL_DTL ( " +
				"    KAI_CODE " +
				" ,  CFL_SWK_DEN_DATE " +
				" ,  CFL_SWK_DEN_NO " +
				" ,  SWK_DEN_DATE " +
				" ,  SWK_DEN_NO " +
				" ,  SWK_GYO_NO " +
				" ,  SWK_GYO_TEK " +
				" ,  SWK_DEP_CODE " +
				" ,  SWK_KMK_CODE " +
				" ,  SWK_HKM_CODE " +
				" ,  SWK_UKM_CODE " +
				" ,  SWK_TRI_CODE " +
				" ,  SWK_EMP_CODE " +
				" ,  SWK_KNR_CODE_1 " +
				" ,  SWK_KNR_CODE_2 " +
				" ,  SWK_KNR_CODE_3 " +
				" ,  SWK_KNR_CODE_4 " +
				" ,  SWK_KNR_CODE_5 " +
				" ,  SWK_KNR_CODE_6 " +
				" ,  SWK_CUR_CODE " +
				" ,  SWK_DC_KBN " +
				" ,  SWK_KIN " +
				" ,  SWK_IN_KIN " +
				" ,  DEN_SYU_CODE " +
				" ,  SWK_BOOK_NO " +
				" ,  SWK_ADJ_KBN   " +
				" )   " +
				" SELECT " +
				"    swk2.KAI_CODE " +
				" ,  kai.KAI_DEN_DATE " +
				" ,  kai.KAI_DEN_NO " +
				" ,  swk2.SWK_DEN_DATE " +
				" ,  swk2.SWK_DEN_NO " +
				" ,  swk2.SWK_GYO_NO " +
				" ,  swk2.SWK_GYO_TEK " +
				" ,  swk2.SWK_DEP_CODE " +
				" ,  swk2.SWK_KMK_CODE " +
				" ,  swk2.SWK_HKM_CODE " +
				" ,  swk2.SWK_UKM_CODE " +
				" ,  swk2.SWK_TRI_CODE " +
				" ,  swk2.SWK_EMP_CODE " +
				" ,  swk2.SWK_KNR_CODE_1 " +
				" ,  swk2.SWK_KNR_CODE_2 " +
				" ,  swk2.SWK_KNR_CODE_3 " +
				" ,  swk2.SWK_KNR_CODE_4 " +
				" ,  swk2.SWK_KNR_CODE_5 " +
				" ,  swk2.SWK_KNR_CODE_6 " +
				" ,  swk2.SWK_CUR_CODE " +
				" ,  swk2.SWK_DC_KBN " +
				" ,  swk2.SWK_KIN " +
				" ,  swk2.SWK_IN_KIN " +
				" ,  swk2.DEN_SYU_CODE " +
				" ,  swk2.SWK_BOOK_NO " +
				" ,  swk2.SWK_ADJ_KBN " +
				" FROM   SWK_DTL swk2 " +
				" INNER  JOIN KMK_MST kmk ON swk2.KAI_CODE = kmk.KAI_CODE " +
				"                        AND swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
				" INNER  JOIN AR_ZAN arzan2 ON swk2.KAI_CODE = arzan2.KAI_CODE " +
				"                          AND swk2.SWK_DEN_NO = arzan2.ZAN_SEI_DEN_NO " +
				"                          AND arzan2.ZAN_DATA_KBN <> '31' " +
				"                          AND NOT (swk2.SWK_KMK_CODE = arzan2.ZAN_KMK_CODE " +
				"                              AND NVL(swk2.SWK_HKM_CODE, ' ') = NVL(arzan2.ZAN_HKM_CODE, ' ') " +
				"                              AND NVL(swk2.SWK_UKM_CODE, ' ') = NVL(arzan2.ZAN_UKM_CODE, ' ') " +
				"                              AND swk2.SWK_AUTO_KBN = 1) " +
				" INNER  JOIN AR_KAI_DAT kai ON arzan2.KAI_CODE = kai.KAI_CODE " +
				"                          AND arzan2.ZAN_KESI_DEN_NO = kai.KAI_KESI_DEN_NO " +
				"                          AND arzan2.ZAN_KESI_IN_KIN = kai.KAI_IN_KIN  " +
				" INNER JOIN (  " +
				"             SELECT DISTINCT " +
				"               swk3.KAI_CODE " +
				"             , swk3.SWK_DEN_DATE  " +
				"             , swk3.SWK_DEN_NO  " +
				"             , arkai.KAI_DEN_DATE  " +
				"             , arkai.KAI_DEN_NO  " +
				"             FROM SWK_DTL swk3   " +
				"                 INNER JOIN AR_KAI_DAT arkai   " +
				"                 ON  swk3.KAI_CODE   = arkai.KAI_CODE   " +
				"                 AND swk3.SWK_DEN_NO = arkai.KAI_KESI_DEN_NO   " +
				"             WHERE arkai.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             AND    arkai.KAI_KESI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"              AND    swk3.SWK_UPD_KBN = 3  " +
				"             AND swk3.SWK_DEN_NO IN (  " +
				"                 SELECT SWK_DEN_NO  " +
				"                 FROM T_DAILY_CLOSE " +
				"                 WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"             )  " +
				"         ) arkai  " +
				" ON  arzan2.KAI_CODE   = arkai.KAI_CODE   " +
				" AND arzan2.ZAN_KESI_DEN_NO = arkai.SWK_DEN_NO  " +
				" INNER JOIN (  " +
				"             SELECT " +
				"                 kesi.KAI_CODE " +
				"                 , sei.ZAN_SEI_DEN_DATE " +
				"                 , sei.ZAN_SEI_DEN_NO " +
				"                 , kesi.ZAN_KESI_DEN_DATE " +
				"                 , kesi.ZAN_KESI_DEN_NO " +
				"                 , sei.ZAN_SEI_IN_KIN " +
				"                 , kesi.ZAN_KESI_IN_KIN " +
				"             FROM " +
				"                 AR_ZAN kesi " +
				"                 INNER JOIN AR_ZAN sei " +
				"                     ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                     AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                     AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                     AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                     AND sei.ZAN_DATA_KBN = '31' " +
				"             WHERE  " +
				"                 kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                 AND kesi.ZAN_DATA_KBN <> '31'     " +
				"           ) arzan3 " +
				"         ON  arzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arzan3.ZAN_SEI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"         AND    swk2.KAI_CODE = arzan3.KAI_CODE " +
				"         AND    swk2.SWK_DEN_NO = arzan3.ZAN_SEI_DEN_NO ";
				DBUtil.execute(conn, sql);

				// -- �������̖����Ȗڂ���щ��������ȊO��o�^
				sql =
				" INSERT INTO CFL_DTL ( " +
				"    KAI_CODE " +
				" ,  CFL_SWK_DEN_DATE " +
				" ,  CFL_SWK_DEN_NO " +
				" ,  SWK_DEN_DATE " +
				" ,  SWK_DEN_NO " +
				" ,  SWK_GYO_NO " +
				" ,  SWK_GYO_TEK " +
				" ,  SWK_DEP_CODE " +
				" ,  SWK_KMK_CODE " +
				" ,  SWK_HKM_CODE " +
				" ,  SWK_UKM_CODE " +
				" ,  SWK_TRI_CODE " +
				" ,  SWK_EMP_CODE " +
				" ,  SWK_KNR_CODE_1 " +
				" ,  SWK_KNR_CODE_2 " +
				" ,  SWK_KNR_CODE_3 " +
				" ,  SWK_KNR_CODE_4 " +
				" ,  SWK_KNR_CODE_5 " +
				" ,  SWK_KNR_CODE_6 " +
				" ,  SWK_CUR_CODE " +
				" ,  SWK_DC_KBN " +
				" ,  SWK_KIN " +
				" ,  SWK_IN_KIN " +
				" ,  DEN_SYU_CODE " +
				" ,  SWK_BOOK_NO " +
				" ,  SWK_ADJ_KBN   " +
				" )   " +
				" SELECT " +
				"    swk2.KAI_CODE " +
				" ,  arkai.KAI_DEN_DATE " +
				" ,  arkai.KAI_DEN_NO " +
				" ,  swk2.SWK_DEN_DATE " +
				" ,  swk2.SWK_DEN_NO " +
				" ,  swk2.SWK_GYO_NO " +
				" ,  swk2.SWK_GYO_TEK " +
				" ,  swk2.SWK_DEP_CODE " +
				" ,  swk2.SWK_KMK_CODE " +
				" ,  swk2.SWK_HKM_CODE " +
				" ,  swk2.SWK_UKM_CODE " +
				" ,  swk2.SWK_TRI_CODE " +
				" ,  swk2.SWK_EMP_CODE " +
				" ,  swk2.SWK_KNR_CODE_1 " +
				" ,  swk2.SWK_KNR_CODE_2 " +
				" ,  swk2.SWK_KNR_CODE_3 " +
				" ,  swk2.SWK_KNR_CODE_4 " +
				" ,  swk2.SWK_KNR_CODE_5 " +
				" ,  swk2.SWK_KNR_CODE_6 " +
				" ,  swk2.SWK_CUR_CODE " +
				" ,  swk2.SWK_DC_KBN " +
				" ,  swk2.SWK_KIN " +
				" ,  swk2.SWK_IN_KIN " +
				" ,  swk2.DEN_SYU_CODE " +
				" ,  swk2.SWK_BOOK_NO " +
				" ,  swk2.SWK_ADJ_KBN " +
				" FROM   SWK_DTL swk2 " +
				" INNER  JOIN KMK_MST kmk ON swk2.KAI_CODE = kmk.KAI_CODE " +
				"                        AND swk2.SWK_KMK_CODE = kmk.KMK_CODE " +
				"                        AND kmk.KMK_CODE NOT IN (" +
				"                            SELECT KMK_CODE FROM SWK_KMK_MST " +
				"                            WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                            AND KMK_CNT = 7) " +
				" INNER JOIN (  " +
				"         SELECT DISTINCT " +
				"           swk3.KAI_CODE " +
				"         , swk3.SWK_DEN_DATE  " +
				"         , swk3.SWK_DEN_NO  " +
				"         , arkai.KAI_DEN_DATE  " +
				"         , arkai.KAI_DEN_NO  " +
				"         FROM SWK_DTL swk3   " +
				"             INNER JOIN AR_KAI_DAT arkai   " +
				"             ON  swk3.KAI_CODE   = arkai.KAI_CODE   " +
				"             AND swk3.SWK_DEN_NO = arkai.KAI_KESI_DEN_NO   " +
				"         WHERE arkai.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arkai.KAI_KESI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"          AND    swk3.SWK_UPD_KBN = 3  " +
				"         AND swk3.SWK_DEN_NO IN (  " +
				"             SELECT SWK_DEN_NO  " +
				"             FROM T_DAILY_CLOSE " +
				"             WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         )  " +
				" ) arkai  " +
				" ON  swk2.KAI_CODE   = arkai.KAI_CODE   " +
				" AND swk2.SWK_DEN_NO = arkai.SWK_DEN_NO  " +
				"  " +
				" INNER JOIN (  " +
				"             SELECT " +
				"                 kesi.KAI_CODE " +
				"                 , sei.ZAN_SEI_DEN_DATE " +
				"                 , sei.ZAN_SEI_DEN_NO " +
				"                 , kesi.ZAN_KESI_DEN_DATE " +
				"                 , kesi.ZAN_KESI_DEN_NO " +
				"                 , kesi.ZAN_KESI_GYO_NO " +
				"                 , sei.ZAN_SEI_IN_KIN " +
				"                 , kesi.ZAN_KESI_IN_KIN " +
				"             FROM " +
				"                 AR_ZAN kesi " +
				"                 INNER JOIN AR_ZAN sei " +
				"                     ON kesi.KAI_CODE = sei.KAI_CODE " +
				"                     AND kesi.ZAN_SEI_DEN_DATE = sei.ZAN_SEI_DEN_DATE " +
				"                     AND kesi.ZAN_SEI_DEN_NO = sei.ZAN_SEI_DEN_NO " +
				"                     AND kesi.ZAN_KESI_IN_KIN = sei.ZAN_SEI_IN_KIN " +
				"                     AND sei.ZAN_DATA_KBN = '31' " +
				"             WHERE  " +
				"                 kesi.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"                 AND kesi.ZAN_DATA_KBN <> '31'     " +
				"           ) arzan3 " +
				"         ON  arzan3.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				"         AND    arzan3.ZAN_SEI_DEN_DATE <= " + SQLUtil.getYYYYMMDDParam(date) +
				"         AND    swk2.KAI_CODE = arzan3.KAI_CODE " +
				"         AND    swk2.SWK_DEN_NO = arzan3.ZAN_KESI_DEN_NO " +
				"         AND    swk2.SWK_GYO_NO <> arzan3.ZAN_KESI_GYO_NO ";
				DBUtil.execute(conn, sql);

				// �L���b�V���t���[�R�[�h�̏����l���Z�b�g����
				// ���z
				sql =
					" UPDATE CFL_DTL cfd " +
					" SET " +
						" CFL_CODE = ( " +
							" SELECT " +
								" CFL_CODE " +
							" FROM " +
								" CFL_MST cfm " +
							" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
							" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
							" AND	cfm.GAKU_HYG_KBN = 0 " +
							" AND	cfm.CFL_KBN = 0 " +
						" ) " +
					" WHERE	cfd.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND	cfd.CFL_CODE IS NULL " +
					" AND	EXISTS ( " +
						" SELECT 1 " +
						" FROM " +
							" CFL_MST cfm " +
						" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
						" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
						" AND	cfm.GAKU_HYG_KBN = 0 " +
						" AND	cfm.CFL_KBN = 0 " +
						" HAVING COUNT(*) = 1" +
					" ) ";
	
				DBUtil.execute(conn, sql);
	
				// ���z(���Z)
				sql =
					" UPDATE CFL_DTL cfd " +
					" SET " +
						" CFL_CODE = ( " +
							" SELECT " +
								" CFL_CODE " +
							" FROM " +
								" CFL_MST cfm " +
							" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
							" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
							" AND	cfm.GAKU_HYG_KBN = 1 " +
							" AND	cfm.CFL_KBN = 0 " +
						" ) " +
					" WHERE	cfd.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND	cfd.CFL_CODE IS NULL " +
					" AND 	cfd.SWK_DC_KBN = 1 " +
					" AND	EXISTS ( " +
						" SELECT 1 " +
						" FROM " +
							" CFL_MST cfm " +
						" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
						" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
						" AND	cfm.GAKU_HYG_KBN = 1 " +
						" AND	cfm.CFL_KBN = 0 " +
						" HAVING COUNT(*) = 1" +
					" ) ";
	
				DBUtil.execute(conn, sql);
	
				// ���z(���Z)
				sql =
					" UPDATE CFL_DTL cfd " +
					" SET " +
						" CFL_CODE = ( " +
							" SELECT " +
								" CFL_CODE " +
							" FROM " +
								" CFL_MST cfm " +
							" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
							" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
							" AND	cfm.GAKU_HYG_KBN = 1 " +
							" AND	cfm.CFL_KBN = 1 " +
						" ) " +
					" WHERE	cfd.KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
					" AND	cfd.CFL_CODE IS NULL " +
					" AND 	cfd.SWK_DC_KBN = 0 " +
					" AND	EXISTS ( " +
						" SELECT 1 " +
						" FROM " +
							" CFL_MST cfm " +
						" WHERE cfd.KAI_CODE = cfm.KAI_CODE " +
						" AND	cfd.SWK_KMK_CODE = cfm.KMK_CODE " +
						" AND	cfm.GAKU_HYG_KBN = 1 " +
						" AND	cfm.CFL_KBN = 1 " +
						" HAVING COUNT(*) = 1" +
					" ) ";
	
				DBUtil.execute(conn, sql);
				
			}
			
			// ���F�������K�v�̏ꍇ�A���F�������o�^����
			if (isUseApproveHistory) {
				insertHistory(conn, updateSqlSwkWhere);
			}

			// 11.�`�[���X�V��Ԃɂ���
			String sqlUpdate =
				" SET " +
					" SWK_UPD_KBN = 4, " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) + updateSqlSwkWhereLast;

			String sqlUpdateHdr = sqlUpdate + (!DBUtil.isUseMySQL ? " AND    swk.SWK_DEN_SYU <> '" + code01Z + "' " : "");
			String sqlUpdateDtl = sqlUpdate + (!DBUtil.isUseMySQL ? " AND    swk.DEN_SYU_CODE <> '" + code01Z + "' " : "");

			DBUtil.execute(conn, " UPDATE GL_SWK_HDR swk " + sqlUpdateHdr);
			DBUtil.execute(conn, " UPDATE AP_SWK_HDR swk " + sqlUpdateHdr);
			DBUtil.execute(conn, " UPDATE AR_SWK_HDR swk " + sqlUpdateHdr);
			DBUtil.execute(conn, " UPDATE SWK_DTL swk " + sqlUpdateDtl);

			// CM_FUND_DTL�o�^���s����
			if (isUseCmFund) {
				// CM_FUND_DTL�o�^����
				entryCmFundInfo(company, conn);
			}
			
			// 12.�����X�V���t���X�V
			updateLastDailyClosedDate(company, getNow());

		} catch (TException e) {
			ServerErrorHandler.handledException(e);
			throw e;
		} catch (Exception e) {
			ServerErrorHandler.handledException(e);
			throw new TException(e);
		} finally {
			
			if(isFlush) {
				// �ŏIT_DAILY_CLOSE���폜
				if (DBUtil.isUseMySQL || !isNotCreateCF) {
					DBUtil.execute(conn, " TRUNCATE TABLE " + slipUpdateTable);
					if (DBUtil.isUseMySQL) {
						DBUtil.execute(conn, " DELETE FROM " + slipUpdateTable);
					}
				}
			}
			
			DBUtil.close(conn);
		}

	}

	/**
	 * ���Ԍ��Z�J��z���Ȃ��ꍇ��WHERE�����̎擾
	 * 
	 * @param company
	 * @return ���Ԍ��Z�J��z���Ȃ��ꍇ��WHERE����
	 */
	protected String getCarryJournalOfMidtermClosingForward(Company company) {
		if (isCarryJournalOfMidtermClosingForward(company)) {
			// ���Ԍ��Z�J��z��
			return "";
		} else {
			// ���Ԍ��Z�J��z���Ȃ�
			return " AND (swk.SWK_KSN_KBN = 0 OR swk.SWK_TUKIDO = 12) "; // �ʏ�`�[�A�܂��͊����̌��Z�`�[�̂ݎc���]�L
		}
	}

	/**
	 * ��v�J��z���@�\�t���O�̎擾
	 * 
	 * @param company �w���Џ��
	 * @return true:���Ԍ��Z�J��z���Afalse:���Ԍ��Z�J��z���Ȃ�
	 */
	protected boolean isCarryJournalOfMidtermClosingForward(Company company) {
		return company.getAccountConfig().isCarryJournalOfMidtermClosingForward();
	}
	
	/**
	 * 5 �c���]�L(���N�x)����
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @throws TException 
	 */
	protected void updateKrzZanCurrentYear(String sqlSwkWhere, String krzZanColumn) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			
			ServerLogger.debug(" 5 �c���]�L(���N�x)����");

			// �X�V�Ώۃf�[�^�̎擾
			String sql = " SELECT /*+RULE*/ " +
							" swk.KAI_CODE, " +
							" swk.SWK_NENDO, " +
							" swk.SWK_DEP_CODE, " +
							" swk.SWK_KMK_CODE, " +
							" swk.SWK_HKM_CODE, " +
							" swk.SWK_UKM_CODE, " +
							" swk.SWK_TRI_CODE, " +
							" swk.SWK_EMP_CODE, " +
							" swk.SWK_KNR_CODE_1, " +
							" swk.SWK_KNR_CODE_2, " +
							" swk.SWK_KNR_CODE_3, " +
							" swk.SWK_KNR_CODE_4, " +
							" swk.SWK_KNR_CODE_5, " +
							" swk.SWK_KNR_CODE_6, " +
							" swk.SWK_KSN_KBN, " +
							" swk.SWK_CUR_CODE, ";
			for (int month = 1; month <= 12; month++) {
				sql = sql +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 0 " +
								" THEN swk.SWK_KIN ELSE 0 END) SWK_DR_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 1 " +
								" THEN swk.SWK_KIN ELSE 0 END) SWK_CR_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO <= " + month + " THEN " +
								" CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_KIN ELSE -swk.SWK_KIN END " +
								" ELSE 0 END) SWK_ZAN_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 0 " +
								" THEN swk.SWK_IN_KIN ELSE 0 END) SWK_DR_G_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO = " + month + " AND swk.SWK_DC_KBN = 1 " +
								" THEN swk.SWK_IN_KIN ELSE 0 END) SWK_CR_G_" + month + ", " +
							" SUM(CASE WHEN swk.SWK_TUKIDO <= " + month + " THEN " +
								" CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_IN_KIN ELSE -swk.SWK_IN_KIN END " +
								" ELSE 0 END) SWK_ZAN_G_" + month + ", ";
			}
			sql = sql +
						" swk.SWK_BOOK_NO, " +
						" swk.SWK_ADJ_KBN " +
					" FROM " +
						" SWK_DTL swk " +
						" INNER JOIN KMK_MST kmk " +
						" ON	swk.KAI_CODE = kmk.KAI_CODE " +
						" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
						sqlSwkWhere +
					" GROUP BY " +
						" swk.KAI_CODE, " +
						" swk.SWK_NENDO, " +
						" swk.SWK_DEP_CODE, " +
						" swk.SWK_KMK_CODE, " +
						" swk.SWK_HKM_CODE, " +
						" swk.SWK_UKM_CODE, " +
						" swk.SWK_TRI_CODE, " +
						" swk.SWK_EMP_CODE, " +
						" swk.SWK_KNR_CODE_1, " +
						" swk.SWK_KNR_CODE_2, " +
						" swk.SWK_KNR_CODE_3, " +
						" swk.SWK_KNR_CODE_4, " +
						" swk.SWK_KNR_CODE_5, " +
						" swk.SWK_KNR_CODE_6, " +
						" swk.SWK_KSN_KBN, " +
						" swk.SWK_CUR_CODE, " +
						" swk.SWK_BOOK_NO, " +
						" swk.SWK_ADJ_KBN ";

			Statement statement = conn.createStatement();
			ResultSet rs = DBUtil.select(statement, sql);

			Date now = getNow();
			while (rs.next()) {
				// �Ǘ��c���X�V����
				sql = "UPDATE KRZ_ZAN krz SET ";
				for (int month = 1; month <= 12; month++) {
					sql = sql +
						" KRZ_DR_" + month + " = krz.KRZ_DR_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_DR_" + month)) + ", " +
						" KRZ_CR_" + month + " = krz.KRZ_CR_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_CR_" + month)) + ", " +
						" KRZ_ZAN_" + month + " = krz.KRZ_ZAN_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN_" + month)) + ", " +
						" KRZ_DR_G_" + month + " = krz.KRZ_DR_G_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_DR_G_" + month)) + ", " +
						" KRZ_CR_G_" + month + " = krz.KRZ_CR_G_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_CR_G_" + month)) + ", " +
						" KRZ_ZAN_G_" + month + " = krz.KRZ_ZAN_G_" + month + " + " + DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN_G_" + month)) + ", ";
				}
				sql = sql +
							" UPD_DATE = " + SQLUtil.getYMDHMSParam(now) + ", " +
							" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
							" USR_ID = " + SQLUtil.getParam(getUserCode()) + " " +
						"WHERE krz.KAI_CODE = " + SQLUtil.getParam(rs.getString("KAI_CODE")) +
						"  AND krz.KRZ_NENDO = " + rs.getInt("SWK_NENDO") +
						"  AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) +
						"  AND krz.KRZ_KMK_CODE = " + SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) +
						"  AND krz.KRZ_HKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_HKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_HKM_CODE"))) +
						"  AND krz.KRZ_UKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_UKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_UKM_CODE"))) +
						"  AND krz.KRZ_TRI_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_TRI_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_TRI_CODE"))) +
						"  AND krz.KRZ_EMP_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_EMP_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_EMP_CODE"))) +
						"  AND krz.KRZ_KNR_CODE_1 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_1")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1"))) +
						"  AND krz.KRZ_KNR_CODE_2 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_2")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2"))) +
						"  AND krz.KRZ_KNR_CODE_3 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_3")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3"))) +
						"  AND krz.KRZ_KNR_CODE_4 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_4")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4"))) +
						"  AND krz.KRZ_KNR_CODE_5 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_5")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5"))) +
						"  AND krz.KRZ_KNR_CODE_6 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_6")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6"))) +
						"  AND krz.KRZ_KSN_KBN = " + rs.getInt("SWK_KSN_KBN") +
						"  AND krz.KRZ_CUR_CODE = " + SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) +
						"  AND krz.KRZ_BOOK_NO = " + rs.getInt("SWK_BOOK_NO") +
						"  AND krz.KRZ_ADJ_KBN = " + rs.getInt("SWK_ADJ_KBN");

				// �Ǘ��c���X�V�����ōX�V������0���̏ꍇ
				if (DBUtil.execute(conn, sql) == 0) {
					// �Ǘ��c���o�^����
					sql = "INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) VALUES (" +
						SQLUtil.getParam(rs.getString("KAI_CODE")) + ", " +
						rs.getInt("SWK_NENDO") + ", " +
						SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_HKM_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_UKM_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_TRI_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_EMP_CODE")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5")) + ", " +
						SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6")) + ", " +
						rs.getInt("SWK_KSN_KBN") + ", " +
						SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) + ", " +
						"0, 0, ";
					String sql_kin = "";
					for (int month = 1; month <= 12; month++) {
						sql_kin += SQLUtil.getParam(rs.getBigDecimal("SWK_DR_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_CR_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_ZAN_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_DR_G_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_CR_G_" + month)) + ", " +
							       SQLUtil.getParam(rs.getBigDecimal("SWK_ZAN_G_" + month)) + ", ";
					}
					sql = sql + sql_kin +
						SQLUtil.getYMDHMSParam(now) + ", " +
						" NULL, " +
						SQLUtil.getParam(getProgramCode()) + ", " +
						SQLUtil.getParam(getUserCode()) + ", " +
						rs.getInt("SWK_BOOK_NO") + ", " +
						rs.getInt("SWK_ADJ_KBN") + ")";

					DBUtil.execute(conn, sql);
				}
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 5.1 B/S�]�L
	 * 
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @param retainedEarningsItemJornal 
	 * @param swkMinNendo 
	 * @param swkMaxNendo 
	 * @param lastNendo 
	 * @throws TException
	 */
	protected void updateKrzZanBS(String sqlSwkWhere, String krzZanColumn, AutoJornalAccount retainedEarningsItemJornal, int swkMinNendo, int swkMaxNendo, int lastNendo) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			
			ServerLogger.debug("5.1 B/S�]�L");

			for (int nendo = swkMinNendo; nendo <= swkMaxNendo; nendo++) {

				for (int toNendo = nendo; toNendo < lastNendo - 1; toNendo++) {

					// �X�V�Ώۃf�[�^�̎擾
					String sql = 
						" SELECT /*+RULE*/ " +
							" swk.KAI_CODE, " +
							" swk.SWK_DEP_CODE, " +
							" swk.SWK_KMK_CODE, " +
							" swk.SWK_HKM_CODE, " +
							" swk.SWK_UKM_CODE, " +
							" swk.SWK_TRI_CODE, " +
							" swk.SWK_EMP_CODE, " +
							" swk.SWK_KNR_CODE_1, " +
							" swk.SWK_KNR_CODE_2, " +
							" swk.SWK_KNR_CODE_3, " +
							" swk.SWK_KNR_CODE_4, " +
							" swk.SWK_KNR_CODE_5, " +
							" swk.SWK_KNR_CODE_6, " +
							" swk.SWK_CUR_CODE, " +
							" SUM(CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_KIN ELSE -swk.SWK_KIN END) SWK_ZAN, " +
							" SUM(CASE WHEN swk.SWK_DC_KBN = kmk.DC_KBN THEN swk.SWK_IN_KIN ELSE -swk.SWK_IN_KIN END) SWK_ZAN_G, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN " +
						" FROM " +
							" SWK_DTL swk " +
							" INNER JOIN KMK_MST kmk " +
							" ON	swk.KAI_CODE = kmk.KAI_CODE " +
							" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
							" AND	kmk.KMK_SHU <> '1' " +
							" AND	kmk.KMK_CODE <> " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
						sqlSwkWhere +
						" AND	swk.SWK_NENDO = " + Integer.toString(nendo) +
						" GROUP BY " +
							" swk.KAI_CODE, " +
							" swk.SWK_DEP_CODE, " +
							" swk.SWK_KMK_CODE, " +
							" swk.SWK_HKM_CODE, " +
							" swk.SWK_UKM_CODE, " +
							" swk.SWK_TRI_CODE, " +
							" swk.SWK_EMP_CODE, " +
							" swk.SWK_KNR_CODE_1, " +
							" swk.SWK_KNR_CODE_2, " +
							" swk.SWK_KNR_CODE_3, " +
							" swk.SWK_KNR_CODE_4, " +
							" swk.SWK_KNR_CODE_5, " +
							" swk.SWK_KNR_CODE_6, " +
							" swk.SWK_CUR_CODE, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN ";

					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sql);

					Date now = getNow();
					while (rs.next()) {

						BigDecimal zan = DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN"));
						BigDecimal zanG = DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN_G"));
						
						// �Ǘ��c���X�V����
						sql = "UPDATE KRZ_ZAN krz SET " +
						        " krz.KRZ_STR_SUM = krz.KRZ_STR_SUM + " + SQLUtil.getParam(zan) + ", " +
						        " krz.KRZ_STR_SUM_G = krz.KRZ_STR_SUM_G + " + SQLUtil.getParam(zanG) + ", ";
						for (int month = 1; month <= 12; month++) {
							sql = sql +
								" KRZ_ZAN_" + month + " = krz.KRZ_ZAN_" + month + " + " + SQLUtil.getParam(zan) + ", " +
								" KRZ_ZAN_G_" + month + " = krz.KRZ_ZAN_G_" + month + " + " + SQLUtil.getParam(zanG) + ", ";
						}
						sql = sql +
									" UPD_DATE = " + SQLUtil.getYMDHMSParam(now) + ", " +
									" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
									" USR_ID = " + SQLUtil.getParam(getUserCode()) + " " +
								"WHERE krz.KAI_CODE = " + SQLUtil.getParam(rs.getString("KAI_CODE")) +
								"  AND krz.KRZ_NENDO = " + Integer.toString(toNendo + 1) +
								"  AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) +
								"  AND krz.KRZ_KMK_CODE = " + SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) +
								"  AND krz.KRZ_HKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_HKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_HKM_CODE"))) +
								"  AND krz.KRZ_UKM_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_UKM_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_UKM_CODE"))) +
								"  AND krz.KRZ_TRI_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_TRI_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_TRI_CODE"))) +
								"  AND krz.KRZ_EMP_CODE " + (Util.isNullOrEmpty(rs.getString("SWK_EMP_CODE")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_EMP_CODE"))) +
								"  AND krz.KRZ_KNR_CODE_1 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_1")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1"))) +
								"  AND krz.KRZ_KNR_CODE_2 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_2")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2"))) +
								"  AND krz.KRZ_KNR_CODE_3 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_3")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3"))) +
								"  AND krz.KRZ_KNR_CODE_4 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_4")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4"))) +
								"  AND krz.KRZ_KNR_CODE_5 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_5")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5"))) +
								"  AND krz.KRZ_KNR_CODE_6 " + (Util.isNullOrEmpty(rs.getString("SWK_KNR_CODE_6")) ? "IS NULL" : "= " + SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6"))) +
								"  AND krz.KRZ_KSN_KBN = 0 " +
								"  AND krz.KRZ_CUR_CODE = " + SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) +
								"  AND krz.KRZ_BOOK_NO = " + rs.getInt("SWK_BOOK_NO") +
								"  AND krz.KRZ_ADJ_KBN = " + rs.getInt("SWK_ADJ_KBN");

						// �Ǘ��c���X�V�����ōX�V������0���̏ꍇ
						if (DBUtil.execute(conn, sql) == 0) {
							// �Ǘ��c���o�^����
							sql = "INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) VALUES (" +
								SQLUtil.getParam(rs.getString("KAI_CODE")) + ", " +
								Integer.toString(toNendo + 1) + ", " +
								SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KMK_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_HKM_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_UKM_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_TRI_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_EMP_CODE")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_1")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_2")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_3")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_4")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_5")) + ", " +
								SQLUtil.getParam(rs.getString("SWK_KNR_CODE_6")) + ", " +
								"0, " + // ���Z�敪
								SQLUtil.getParam(rs.getString("SWK_CUR_CODE")) + ", " +
								SQLUtil.getParam(zan) + ", " + // ����c
								SQLUtil.getParam(zanG) + ", "; // ����O�ݎc
							String sql_kin = "";
							for (int month = 1; month <= 12; month++) {
								sql_kin += " 0, " +
										   " 0, " +
										   SQLUtil.getParam(zan) + ", " +
										   " 0, " +
										   " 0, " +
										   SQLUtil.getParam(zanG) + ", ";
							}
							sql = sql + sql_kin +
								SQLUtil.getYMDHMSParam(now) + ", " +
								" NULL, " +
								SQLUtil.getParam(getProgramCode()) + ", " +
								SQLUtil.getParam(getUserCode()) + ", " +
								rs.getInt("SWK_BOOK_NO") + ", " +
								rs.getInt("SWK_ADJ_KBN") + ")";

							DBUtil.execute(conn, sql);
						}
					}

					DBUtil.close(statement);
					DBUtil.close(rs);

				}
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 5.2 P/L�]�L(���N�̑O���J�z���v�ɉ��Z)
	 * 
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @param retainedEarningsItemJornal
	 * @param swkMinNendo
	 * @param swkMaxNendo
	 * @param lastNendo
	 * @throws TException
	 */
	protected void updateKrzZanPL(String sqlSwkWhere, String krzZanColumn,
		AutoJornalAccount retainedEarningsItemJornal, int swkMinNendo, int swkMaxNendo, int lastNendo)
		throws TException {
		updateKrzZanPL(sqlSwkWhere, krzZanColumn, retainedEarningsItemJornal, swkMinNendo, swkMaxNendo, lastNendo, getCompany());
	}

	/**
	 * 5.2 P/L�]�L(���N�̑O���J�z���v�ɉ��Z)
	 * 
	 * @param sqlSwkWhere
	 * @param krzZanColumn
	 * @param retainedEarningsItemJornal
	 * @param swkMinNendo
	 * @param swkMaxNendo
	 * @param lastNendo
	 * @param company ��Џ��
	 * @throws TException
	 */
	protected void updateKrzZanPL(String sqlSwkWhere, String krzZanColumn,
		AutoJornalAccount retainedEarningsItemJornal, int swkMinNendo, int swkMaxNendo, int lastNendo, Company company)
		throws TException {
		Connection conn = DBUtil.getConnection();
		try {

			ServerLogger.debug("5.2 P/L�]�L(���N�̑O���J�z���v�ɉ��Z)");
			
			String keyCur = SQLUtil.getParam(company.getAccountConfig().getKeyCurrency().getCode());
			String fncCur = SQLUtil.getParam(company.getAccountConfig().getFunctionalCurrency().getCode());

			for (int nendo = swkMinNendo; nendo <= swkMaxNendo; nendo++) {

				for (int toNendo = nendo; toNendo < lastNendo; toNendo++) {
					
					// �X�V�Ώۃf�[�^�̎擾
					String sql = 
						" SELECT /*+RULE*/ " +
							" swk.KAI_CODE, " +
							" SUM(CASE WHEN swk.SWK_DC_KBN = 1 THEN swk.SWK_KIN ELSE -swk.SWK_KIN END) SWK_ZAN, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN ";

					if (isDepCreate) {
						// ����P�ʂŐ���
						sql += " , swk.SWK_DEP_CODE ";
					}
					sql += " FROM " +
							" SWK_DTL swk " +
							" INNER JOIN KMK_MST kmk " +
							" ON	swk.KAI_CODE = kmk.KAI_CODE " +
							" AND	swk.SWK_KMK_CODE = kmk.KMK_CODE " +
							" AND	(kmk.KMK_SHU = '1' " +
							" OR	kmk.KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ") " +
						sqlSwkWhere +
						" AND	swk.SWK_NENDO = " + Integer.toString(nendo) +
						" GROUP BY " +
							" swk.KAI_CODE, " +
							" swk.SWK_BOOK_NO, " +
							" swk.SWK_ADJ_KBN ";
					if (isDepCreate) {
						// ����P�ʂŐ���
						sql += " , swk.SWK_DEP_CODE ";
					}
					
					Statement statement = conn.createStatement();
					ResultSet rs = DBUtil.select(statement, sql);
					
					Date now = getNow();
					while (rs.next()) {
						
						BigDecimal zan = DecimalUtil.avoidNull(rs.getBigDecimal("SWK_ZAN"));
						
						// �Ǘ��c���X�V����
						sql = "UPDATE KRZ_ZAN krz SET " +
							" krz.KRZ_STR_SUM = krz.KRZ_STR_SUM + " + SQLUtil.getParam(zan) + ", " +
							" krz.KRZ_STR_SUM_G = krz.KRZ_STR_SUM_G + " + SQLUtil.getParam(zan) + ", ";
						for (int month = 1; month <= 12; month++) {
							sql = sql +
								" KRZ_ZAN_" + month + " = krz.KRZ_ZAN_" + month + " + " + SQLUtil.getParam(zan) + ", " +
								" KRZ_ZAN_G_" + month + " = krz.KRZ_ZAN_G_" + month + " + " + SQLUtil.getParam(zan) + ", ";
						}
						sql = sql +
							" UPD_DATE = " + SQLUtil.getYMDHMSParam(now) + ", " +
							" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
							" USR_ID = " + SQLUtil.getParam(getUserCode()) + " " +
							"WHERE krz.KAI_CODE = " + SQLUtil.getParam(rs.getString("KAI_CODE")) +
							"  AND krz.KRZ_NENDO = " + Integer.toString(toNendo + 1);
						if (isDepCreate) {
							// ����P�ʂŐ���
							sql += " AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(rs.getString("SWK_DEP_CODE"));
						} else {
							// �����d��Ȗڃ}�X�^�Ő���
							sql += "  AND krz.KRZ_DEP_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode());
						}
						sql +=
							"  AND krz.KRZ_KMK_CODE = " + SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) +
							"  AND krz.KRZ_KSN_KBN = 0 " +
							"  AND krz.KRZ_CUR_CODE = CASE WHEN krz.KRZ_BOOK_NO = 1 THEN " + keyCur + " ELSE " + fncCur + " END " +
							"  AND krz.KRZ_BOOK_NO = " + rs.getInt("SWK_BOOK_NO") +
							"  AND krz.KRZ_ADJ_KBN = " + rs.getInt("SWK_ADJ_KBN");
						
						// �Ǘ��c���X�V�����ōX�V������0���̏ꍇ
						if (DBUtil.execute(conn, sql) == 0) {
							// �Ǘ��c���o�^����
							sql = "INSERT INTO KRZ_ZAN ( " + krzZanColumn + " ) VALUES (" +
								SQLUtil.getParam(rs.getString("KAI_CODE")) + ", " +
								Integer.toString(toNendo + 1) + ", ";
							if (isDepCreate) {
								// ����P�ʂŐ���
								sql += SQLUtil.getParam(rs.getString("SWK_DEP_CODE")) + ", ";
							} else {
								// �����d��Ȗڃ}�X�^�Ő���
								sql += SQLUtil.getParam(retainedEarningsItemJornal.getDepertmentCode()) + ", ";
							}
							sql +=
								SQLUtil.getParam(retainedEarningsItemJornal.getItemCode()) + ", " +
								SQLUtil.getParam(retainedEarningsItemJornal.getSubItemCode()) + ", " +
								SQLUtil.getParam(retainedEarningsItemJornal.getDetailItemCode()) + ", " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								" NULL, " +
								"0, " + // ���Z�敪
							    ((rs.getInt("SWK_BOOK_NO") == 1) ? keyCur : fncCur) + ", " +
								SQLUtil.getParam(zan) + ", " + // ����c
								SQLUtil.getParam(zan) + ", ";  // ����O�ݎc
							String sql_kin = "";
							for (int month = 1; month <= 12; month++) {
								sql_kin += " 0, " +
									" 0, " +
									SQLUtil.getParam(zan) + ", " +
									" 0, " +
									" 0, " +
									SQLUtil.getParam(zan) + ", ";
							}
							sql = sql + sql_kin +
								SQLUtil.getYMDHMSParam(now) + ", " +
								" NULL, " +
								SQLUtil.getParam(getProgramCode()) + ", " +
								SQLUtil.getParam(getUserCode()) + ", " +
								rs.getInt("SWK_BOOK_NO") + ", " +
								rs.getInt("SWK_ADJ_KBN") + ")";
							
							DBUtil.execute(conn, sql);
						}
					}
					
					DBUtil.close(statement);
					DBUtil.close(rs);
					
				}
			}
			
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ����o�^
	 * 
	 * @param conn
	 * @param sqlSwkWhere
	 * @throws TException 
	 */
	protected void insertHistory(Connection conn, String sqlSwkWhere) throws TException {
		String empCode = "";
		if (getUser().getEmployee() != null) {
			empCode = getUser().getEmployee().getCode();
		}

		SQLCreator sql = new SQLCreator();
		sql.add("");
		sql.add(" INSERT INTO SWK_SYO_RRK ");
		sql.add(" ( ");
		sql.add("    KAI_CODE ");
		sql.add("   ,SWK_DEN_DATE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SWK_UPD_KBN ");
		sql.add("   ,EMP_CODE ");
		sql.add("   ,SYO_FLG ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add(" ) ");
		sql.add(" SELECT hdr.KAI_CODE ");
		sql.add("       ,hdr.SWK_DEN_DATE ");
		sql.add("       ,hdr.SWK_DEN_NO ");
		sql.add("       ,? SWK_UPD_KBN ", SlipState.UPDATE.value);
		sql.add("       ,? EMP_CODE ", empCode);
		sql.add("       ,? SYO_FLG ", ApproveHistory.SYO_FLG.UPDATE);
		sql.addYMDHMS("       ,? INP_DATE ", getNow());
		sql.add("       ,? PRG_ID ", getProgramCode());
		sql.add("       ,? USR_ID ", getUserCode());
		sql.add(" FROM   (SELECT KAI_CODE ");
		sql.add("               ,SWK_DEN_DATE ");
		sql.add("               ,SWK_DEN_NO ");
		sql.add("         FROM   GL_SWK_HDR swk ");
		sql.add(sqlSwkWhere);
		sql.add("         UNION ALL ");
		sql.add("         SELECT KAI_CODE ");
		sql.add("               ,SWK_DEN_DATE ");
		sql.add("               ,SWK_DEN_NO ");
		sql.add("         FROM   AP_SWK_HDR swk ");
		sql.add(sqlSwkWhere);
		sql.add("         UNION ALL ");
		sql.add("         SELECT KAI_CODE ");
		sql.add("               ,SWK_DEN_DATE ");
		sql.add("               ,SWK_DEN_NO ");
		sql.add("         FROM   AR_SWK_HDR swk ");
		sql.add(sqlSwkWhere);
		sql.add("         ) hdr ");

		DBUtil.execute(conn, sql);
	}

	/****************************************************************************************************************
	 * �ȉ�������ДŃ��\�b�h
	 ****************************************************************************************************************/

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException {

		Connection conn = DBUtil.getConnection();
		List<DailyData> list = new ArrayList<DailyData>();

		try {

			// ��БS�f�[�^���o
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT env.KAI_CODE ");
			sql.add("       ,env.KAI_NAME ");
			sql.add("       ,sim.NITIJI_DATE ");
			sql.add(" FROM   ENV_MST env ");
			sql.add(" INNER  JOIN SIM_CTL sim ON sim.KAI_CODE = env.KAI_CODE ");
			sql.add(" ORDER BY CASE WHEN env.KAI_CODE = ? THEN 1 ELSE 2 END ", getCompanyCode());
			sql.add("         ,env.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				DailyData bean = new DailyData();
				bean.setCompanyCode(rs.getString("KAI_CODE"));
				bean.setCompanyName(rs.getString("KAI_NAME"));
				bean.setLastUpdateDate(rs.getTimestamp("NITIJI_DATE"));
				list.add(bean);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);
			
			return list;

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}
	
	/**
	 * CM_FUND_DTL���̍X�V
	 * 
	 * @param company
	 * @param conn 
	 * @throws TException
	 */
	public void entryCmFundInfo(Company company, Connection conn) throws TException {

		try {
			// �X�V�Ώۃf�[�^�ꎞ�i�[�e�[�u��
			String slipUpdateTable = "T_DAILY_CLOSE";

			// �����X�V�Ώۂ̃w�b�_�[���R�[�h�͑S�č폜
			VCreator sql = new VCreator();
			sql.add(" DELETE FROM CM_FUND_DTL ");
			sql.add(" WHERE (KAI_CODE , KEY_DEN_NO) IN ( ");
			sql.add("     SELECT KAI_CODE , SWK_DEN_NO FROM " + slipUpdateTable);
			sql.add(" ) ");
			sql.add(" AND DATA_TYPE = 0 "); // 0 �w�b�_�[
			DBUtil.execute(conn, sql);

			// ���ʕ��\�z
			VCreator sqlIns = new VCreator();
			sqlIns.add(" INSERT INTO CM_FUND_DTL ( ");
			sqlIns.add("      KAI_CODE ");
			sqlIns.add("     ,DEN_DATE ");
			sqlIns.add("     ,DEN_NO ");
			sqlIns.add("     ,TRI_CODE ");
			sqlIns.add("     ,TRI_NAME ");
			sqlIns.add("     ,KNR_CODE ");
			sqlIns.add("     ,KNR_NAME ");
			sqlIns.add("     ,TEK ");
			sqlIns.add("     ,DEN_SYU_CODE ");
			sqlIns.add("     ,DEN_SYU_NAME ");
			sqlIns.add("     ,CUR_CODE ");
			sqlIns.add("     ,ZAN_KIN ");
			sqlIns.add("     ,ZAN_IN_KIN ");
			sqlIns.add("     ,CBK_CODE ");
			sqlIns.add("     ,DATA_KBN ");
			sqlIns.add("     ,SYS_KBN ");
			sqlIns.add("     ,DATA_TYPE ");
			sqlIns.add("     ,KEY_DEN_DATE ");
			sqlIns.add("     ,KEY_DEN_NO ");
			sqlIns.add("     ,KEY_GYO_NO ");
			sqlIns.add("     ,KEY_SEI_NO ");
			sqlIns.add("     ,KEY_DEP_CODE ");
			sqlIns.add("     ,KEY_TRI_CODE ");
			sqlIns.add("     ,INP_DATE ");
			sqlIns.add("     ,UPD_DATE ");
			sqlIns.add("     ,PRG_ID ");
			sqlIns.add("     ,USR_ID ");
			sqlIns.add(" ) VALUES ( ");

			// ���c������
			sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  zan.KAI_CODE, ");
			sql.add("  zan.ZAN_SIHA_DATE, ");
			sql.add("  zan.ZAN_DEN_DATE, ");
			sql.add("  zan.ZAN_DEN_NO, ");
			sql.add("  zan.ZAN_GYO_NO, ");
			sql.add("  zan.ZAN_DEP_CODE, ");
			sql.add("  dep.DEP_NAME, ");
			sql.add("  zan.ZAN_TEK, ");
			sql.add("  zan.ZAN_SIHA_CODE, ");
			sql.add("  tri.TRI_NAME, ");
			sql.add("  zan.ZAN_KIN, ");
			sql.add("  zan.ZAN_IN_SIHA_KIN, ");
			sql.add("  zan.ZAN_CUR_CODE, ");
			sql.add("  cbk.CUR_CODE, ");
			sql.add("  cur.DEC_KETA, ");
			sql.add("  cur.RATE_POW, ");
			sql.add("  zan.ZAN_SYS_KBN, ");
			sql.add("  zan.ZAN_DEN_SYU, ");
			sql.add("  syu.DEN_SYU_NAME, ");
			sql.add("  zan.ZAN_FURI_CBK_CODE, ");

			sql.add("  zan.ZAN_INP_DATE, ");
			sql.add("  zan.UPD_DATE, ");
			sql.add("  zan.PRG_ID, ");
			sql.add("  zan.USR_ID ");

			sql.add(" FROM AP_ZAN zan ");

			sql.add(" LEFT OUTER JOIN TRI_MST tri ");
			sql.add(" ON  zan.KAI_CODE      = tri.KAI_CODE ");
			sql.add(" AND zan.ZAN_SIHA_CODE = tri.TRI_CODE ");

			sql.add(" LEFT OUTER JOIN BMN_MST dep ");
			sql.add(" ON  zan.KAI_CODE     = dep.KAI_CODE ");
			sql.add(" AND zan.ZAN_DEP_CODE = dep.DEP_CODE ");

			sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ");
			sql.add(" ON  zan.KAI_CODE    = syu.KAI_CODE ");
			sql.add(" AND zan.ZAN_DEN_SYU = syu.DEN_SYU_CODE ");

			sql.add(" INNER JOIN AP_CBK_MST cbk ");
			sql.add(" ON  zan.KAI_CODE          = cbk.KAI_CODE ");
			sql.add(" AND zan.ZAN_FURI_CBK_CODE = cbk.CBK_CBK_CODE ");

			sql.add(" INNER JOIN CUR_MST cur ");
			sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
			sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

			sql.add(" WHERE (zan.KAI_CODE , zan.ZAN_DEN_NO) IN ( ");
			sql.add("     SELECT KAI_CODE , SWK_DEN_NO FROM " + slipUpdateTable);
			sql.add(" ) ");

			sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_SIHA_DATE , zan.ZAN_DEN_NO ");
			
			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String curCode = null;
			BigDecimal inKin = BigDecimal.ZERO;
			BigDecimal kin = BigDecimal.ZERO;
			String keyCurCode = company.getAccountConfig().getKeyCurrency().getCode();
			RateManager rateMn = get(RateManager.class);
	
			while (rs.next()) {
				//��s�����ʉ�
				curCode = rs.getString("CUR_CODE");
				// ���z�Z�b�g
				inKin = rs.getBigDecimal("ZAN_IN_SIHA_KIN");
				kin = rs.getBigDecimal("ZAN_KIN");
				
				if (Util.equals(curCode, rs.getString("ZAN_CUR_CODE"))) {
					// �����ʉ݂Ǝ���ʉ݂����� �͂��̂܂�

				} else if (Util.equals(curCode, keyCurCode)) {
					// ����ʉ݂Ɗ�ʉ݂�����
					inKin = kin;
				} else {
					// �ȊO�͌v�Z��苁�߂�
					BigDecimal rate = rateMn.getRate(curCode, rs.getDate("ZAN_SIHA_DATE"));
					inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), company);
				}

				// �o�^����
				sql = new VCreator();
				sql.add(sqlIns);
				sql.add("     ? ", rs.getString("KAI_CODE"));
				sql.add("    ,? ", rs.getDate("ZAN_SIHA_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_DEN_NO"));
				sql.add("    ,? ", rs.getString("ZAN_SIHA_CODE"));
				sql.add("    ,? ", rs.getString("TRI_NAME"));
				sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
				sql.add("    ,? ", rs.getString("DEP_NAME"));
				sql.add("    ,? ", rs.getString("ZAN_TEK"));
				sql.add("    ,? ", rs.getString("ZAN_DEN_SYU"));
				sql.add("    ,? ", rs.getString("DEN_SYU_NAME"));
				sql.add("    ,? ", curCode);
				sql.add("    ,? ", kin.negate()); // �������]
				sql.add("    ,? ", inKin.negate()); // �������]
				sql.add("    ,? ", rs.getString("ZAN_FURI_CBK_CODE"));
				sql.add("    ,0 "); // DATA_KBN
				sql.add("    ,? ", rs.getString("ZAN_SYS_KBN"));
				sql.add("    ,2 "); // DATA_TYPE
				sql.add("    ,? ", rs.getDate("ZAN_DEN_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_DEN_NO"));
				sql.add("    ,? ", rs.getInt("ZAN_GYO_NO"));
				sql.add("    ,NULL ");
				sql.add("    ,NULL ");
				sql.add("    ,NULL ");
				sql.adt("    ,? ", rs.getTimestamp("ZAN_INP_DATE"));
				sql.adt("    ,? ", rs.getTimestamp("UPD_DATE"));
				sql.add("    ,? ", rs.getString("PRG_ID"));
				sql.add("    ,? ", rs.getString("USR_ID"));
				sql.add(" ) ");

				DBUtil.execute(sql);
			}
			
			DBUtil.close(rs);
			DBUtil.close(statement);
			
			// ���c������
			sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  zan.KAI_CODE, ");
			sql.add("  zan.ZAN_AR_DATE, ");
			sql.add("  zan.ZAN_SEI_DEN_DATE, ");
			sql.add("  zan.ZAN_SEI_DEN_NO, ");
			sql.add("  zan.ZAN_SEI_GYO_NO, ");
			sql.add("  zan.ZAN_SEI_NO, ");
			sql.add("  zan.ZAN_DEP_CODE, ");
			sql.add("  dep.DEP_NAME, ");
			sql.add("  swk.SWK_TEK, "); // 
			sql.add("  zan.ZAN_TRI_CODE, ");
			sql.add("  tri.TRI_NAME, ");
			sql.add("  zan.ZAN_SEI_KIN, ");
			sql.add("  zan.ZAN_SEI_IN_KIN, ");
			sql.add("  zan.ZAN_CUR_CODE, ");
			sql.add("  cbk.CUR_CODE, ");
			sql.add("  cur.DEC_KETA, ");
			sql.add("  cur.RATE_POW, ");
			sql.add("  swk.SWK_SYS_KBN, ");
			sql.add("  swk.SWK_DEN_SYU, ");
			sql.add("  syu.DEN_SYU_NAME, ");
			sql.add("  zan.ZAN_CBK_CODE, ");
			sql.add("  zan.INP_DATE, ");
			sql.add("  zan.UPD_DATE, ");
			sql.add("  zan.PRG_ID, ");
			sql.add("  zan.USR_ID ");

			sql.add(" FROM AR_ZAN zan ");

			sql.add(" INNER JOIN AR_SWK_HDR swk ");
			sql.add(" ON  zan.KAI_CODE         = swk.KAI_CODE ");
			sql.add(" AND zan.ZAN_SEI_DEN_DATE = swk.SWK_DEN_DATE ");
			sql.add(" AND zan.ZAN_SEI_DEN_NO   = swk.SWK_DEN_NO ");

			sql.add(" LEFT OUTER JOIN TRI_MST tri ");
			sql.add(" ON  zan.KAI_CODE     = tri.KAI_CODE ");
			sql.add(" AND zan.ZAN_TRI_CODE = tri.TRI_CODE ");

			sql.add(" LEFT OUTER JOIN BMN_MST dep ");
			sql.add(" ON  zan.KAI_CODE     = dep.KAI_CODE ");
			sql.add(" AND zan.ZAN_DEP_CODE = dep.DEP_CODE ");

			sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ");
			sql.add(" ON  swk.KAI_CODE    = syu.KAI_CODE ");
			sql.add(" AND swk.SWK_DEN_SYU = syu.DEN_SYU_CODE ");

			sql.add(" INNER JOIN AP_CBK_MST cbk ");
			sql.add(" ON  zan.KAI_CODE     = cbk.KAI_CODE ");
			sql.add(" AND zan.ZAN_CBK_CODE = cbk.CBK_CBK_CODE ");

			sql.add(" INNER JOIN CUR_MST cur ");
			sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
			sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

			sql.add(" WHERE (zan.KAI_CODE , zan.ZAN_SEI_DEN_NO) IN ( ");
			sql.add("     SELECT KAI_CODE , SWK_DEN_NO FROM " + slipUpdateTable);
			sql.add(" ) ");

			sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_AR_DATE , zan.ZAN_SEI_DEN_NO ");
			
			statement = DBUtil.getStatement(conn);
			rs = DBUtil.select(statement, sql);

			// ������
			curCode = null;
			inKin = BigDecimal.ZERO;
			kin = BigDecimal.ZERO;
			
			while (rs.next()) {
				//��s�����ʉ�
				curCode = rs.getString("CUR_CODE");
				// ���z�Z�b�g
				inKin = rs.getBigDecimal("ZAN_SEI_IN_KIN");
				kin = rs.getBigDecimal("ZAN_SEI_KIN");
				
				if (Util.equals(curCode, rs.getString("ZAN_CUR_CODE"))) {
					// �����ʉ݂Ǝ���ʉ݂����� �͂��̂܂�

				} else if (Util.equals(curCode, keyCurCode)) {
					// ����ʉ݂Ɗ�ʉ݂�����
					inKin = kin;
				} else {
					// �ȊO�͌v�Z��苁�߂�
					BigDecimal rate = rateMn.getRate(curCode, rs.getDate("ZAN_AR_DATE"));
					inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), company);
				}

				// �o�^����
				sql = new VCreator();
				sql.add(sqlIns);
				sql.add("     ? ", rs.getString("KAI_CODE"));
				sql.add("    ,? ", rs.getDate("ZAN_AR_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_SEI_DEN_NO"));
				sql.add("    ,? ", rs.getString("ZAN_TRI_CODE"));
				sql.add("    ,? ", rs.getString("TRI_NAME"));
				sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
				sql.add("    ,? ", rs.getString("DEP_NAME"));
				sql.add("    ,? ", rs.getString("SWK_TEK"));
				sql.add("    ,? ", rs.getString("SWK_DEN_SYU"));
				sql.add("    ,? ", rs.getString("DEN_SYU_NAME"));
				sql.add("    ,? ", curCode);
				sql.add("    ,? ", kin); // �������̂܂�
				sql.add("    ,? ", inKin); // �������̂܂�
				sql.add("    ,? ", rs.getString("ZAN_CBK_CODE"));
				sql.add("    ,0 "); // DATA_KBN
				sql.add("    ,? ", rs.getString("SWK_SYS_KBN"));
				sql.add("    ,2 "); // DATA_TYPE
				sql.add("    ,? ", rs.getDate("ZAN_SEI_DEN_DATE"));
				sql.add("    ,? ", rs.getString("ZAN_SEI_DEN_NO"));
				sql.add("    ,? ", rs.getInt("ZAN_SEI_GYO_NO"));
				sql.add("    ,? ", rs.getString("ZAN_SEI_NO"));
				sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
				sql.add("    ,? ", rs.getString("ZAN_TRI_CODE"));
				sql.adt("    ,? ", rs.getTimestamp("INP_DATE"));
				sql.adt("    ,? ", rs.getTimestamp("UPD_DATE"));
				sql.add("    ,? ", rs.getString("PRG_ID"));
				sql.add("    ,? ", rs.getString("USR_ID"));
				sql.add(" ) ");

				DBUtil.execute(sql);
			}
			
			DBUtil.close(rs);
			DBUtil.close(statement);
			
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * ��ʉ݁����͋��z
	 * 
	 * @param keyAmount ��ʉ݋��z
	 * @param rate ���[�g
	 * @param ratePow �O�ݒʉ݃��[�g�W��
	 * @param decimalPoints �O�ݒʉݏ����_�ȉ�����
	 * @param company
	 * @return ���͋��z
	 */
	public BigDecimal convertToForeign(BigDecimal keyAmount, BigDecimal rate, int ratePow, int decimalPoints,
		Company company) {

		if (rate == null) {
			return null;
		}

		if (keyAmount == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = company.getAccountConfig();
		ExchangeFraction frac = conf.getExchangeFraction();

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(frac);
		param.setConvertType(conf.getConvertType());
		param.setDigit(decimalPoints);
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(ratePow);

		return calculator.exchangeForeignAmount(param);
	}

	/**
	 * REVALUATION_CTL��STATUS_KBN���X�V����
	 * 
	 * @param date
	 * @param companyCode
	 * @throws TException
	 */
	public void checkStatus(Date date, String companyCode) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();
			Date proc_ym = null;
			boolean isChk = false;
			List<String> list = new ArrayList<String>();
			list.add("01A");
			list.add("01Z");

			String baseCurrencyCode = getCompany().getAccountConfig().getKeyCurrency().getCode();

			sql.add(" SELECT ");
			sql.add("    hdr.KAI_CODE ");
			sql.add("	,PKG_DATE_UTIL_getTukidoEnd(hdr.SWK_DEN_DATE) AS END_MONTH ");
			sql.add("	,CASE WHEN COUNT(*) <> 0 THEN 1 ELSE 0 END ");
			sql.add(" FROM GL_SWK_HDR hdr ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND hdr.KAI_CODE = ? ", companyCode);
			sql.add(" AND hdr.SWK_DEN_SYU IN ? ", list);

			if (DBUtil.isUseMySQL) {
				sql.add("AND DATE_FORMAT(hdr.SWK_DEN_DATE,'%Y/%m') ");
			} else {
				sql.add("AND TO_CHAR(hdr.SWK_DEN_DATE,'YYYY/MM') ");
			}

			sql.add(" IN ( ");
			sql.add(" SELECT DISTINCT dtl.SWK_DEN_YM FROM ( ");

			if (DBUtil.isUseMySQL) {
				sql.add(" SELECT DISTINCT DATE_FORMAT(swk.SWK_DEN_DATE,'%Y/%m') SWK_DEN_YM");
			} else {
				sql.add(" SELECT DISTINCT TO_CHAR(swk.SWK_DEN_DATE,'YYYY/MM') SWK_DEN_YM");
			}

			sql.add(" FROM SWK_DTL swk ");
			sql.add("     INNER JOIN SIM_CTL sim ");
			sql.add("     ON swk.KAI_CODE = sim.KAI_CODE ");
			sql.add("     INNER JOIN KMK_MST kmk ");
			sql.add("     ON  swk.KAI_CODE     = kmk.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = kmk.KMK_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND swk.KAI_CODE = ? ", companyCode);
			sql.add(" AND swk.DEN_SYU_CODE NOT IN ? ", list);
			sql.add(" AND swk.SWK_UPD_KBN = ? ", SlipState.APPROVE.value);
			sql.add(" AND swk.SWK_CUR_CODE <> ? ", baseCurrencyCode);
			sql.add(" AND NVL(kmk.EXC_FLG,0) <> 0 ");
			sql.add(" AND NVL(kmk.HKM_KBN,0) = 0 ");

			if (DBUtil.isUseMySQL) {
				sql.add(" AND swk.SWK_DEN_DATE >= TIMESTAMPADD(MONTH,sim.SIM_MON,sim.SIM_STR_DATE) ");
			} else {
				sql.add(" AND swk.SWK_DEN_DATE >= ADD_MONTHS(sim.SIM_STR_DATE, sim.SIM_MON) ");
			}

			sql.add(" AND swk.SWK_DEN_DATE <= PKG_DATE_UTIL_getTukidoEnd(?) ", date);
			sql.add(" UNION ALL ");

			if (DBUtil.isUseMySQL) {
				sql.add(" SELECT DISTINCT DATE_FORMAT(swk.SWK_DEN_DATE,'%Y/%m') SWK_DEN_YM ");
			} else {
				sql.add(" SELECT DISTINCT TO_CHAR(swk.SWK_DEN_DATE,'YYYY/MM') SWK_DEN_YM ");
			}

			sql.add(" FROM SWK_DTL swk ");
			sql.add("     INNER JOIN SIM_CTL sim ");
			sql.add("     ON swk.KAI_CODE = sim.KAI_CODE ");
			sql.add("     INNER JOIN KMK_MST kmk ");
			sql.add("     ON  swk.KAI_CODE     = kmk.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = kmk.KMK_CODE ");
			sql.add("     INNER JOIN HKM_MST hkm ");
			sql.add("     ON  swk.KAI_CODE     = hkm.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = hkm.KMK_CODE ");
			sql.add("     AND swk.SWK_HKM_CODE = hkm.HKM_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND swk.KAI_CODE = ? ", companyCode);
			sql.add(" AND swk.DEN_SYU_CODE NOT IN ? ", list);
			sql.add(" AND swk.SWK_UPD_KBN = ? ", SlipState.APPROVE.value);
			sql.add(" AND swk.SWK_CUR_CODE <> ? ", baseCurrencyCode);
			sql.add(" AND NVL(kmk.HKM_KBN,0) = 1 ");
			sql.add(" AND NVL(hkm.UKM_KBN,0) = 0 ");
			sql.add(" AND NVL(hkm.EXC_FLG,0) <> 0 ");

			if (DBUtil.isUseMySQL) {
				sql.add(" AND swk.SWK_DEN_DATE >= TIMESTAMPADD(MONTH,sim.SIM_MON,sim.SIM_STR_DATE) ");
			} else {
				sql.add(" AND swk.SWK_DEN_DATE >= ADD_MONTHS(sim.SIM_STR_DATE, sim.SIM_MON) ");
			}

			sql.add(" AND swk.SWK_DEN_DATE <= PKG_DATE_UTIL_getTukidoEnd(?) ", date);
			sql.add(" UNION ALL ");

			if (DBUtil.isUseMySQL) {
				sql.add(" SELECT DISTINCT DATE_FORMAT(swk.SWK_DEN_DATE,'%Y/%m') SWK_DEN_YM ");
			} else {
				sql.add(" SELECT DISTINCT TO_CHAR(swk.SWK_DEN_DATE,'YYYY/MM') SWK_DEN_YM ");
			}

			sql.add(" FROM SWK_DTL swk ");
			sql.add("     INNER JOIN SIM_CTL sim ");
			sql.add("     ON swk.KAI_CODE = sim.KAI_CODE ");
			sql.add("     INNER JOIN KMK_MST kmk ");
			sql.add("     ON  swk.KAI_CODE     = kmk.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = kmk.KMK_CODE ");
			sql.add("     INNER JOIN HKM_MST hkm ");
			sql.add("     ON  swk.KAI_CODE     = hkm.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = hkm.KMK_CODE ");
			sql.add("     AND swk.SWK_HKM_CODE = hkm.HKM_CODE ");
			sql.add("     INNER JOIN UKM_MST ukm ");
			sql.add("     ON  swk.KAI_CODE     = ukm.KAI_CODE ");
			sql.add("     AND swk.SWK_KMK_CODE = ukm.KMK_CODE ");
			sql.add("     AND swk.SWK_HKM_CODE = ukm.HKM_CODE ");
			sql.add("     AND swk.SWK_UKM_CODE = ukm.UKM_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND swk.KAI_CODE = ? ", companyCode);
			sql.add(" AND swk.DEN_SYU_CODE NOT IN ? ", list);
			sql.add(" AND swk.SWK_UPD_KBN = ? ", SlipState.APPROVE.value);
			sql.add(" AND swk.SWK_CUR_CODE <> ? ", baseCurrencyCode);
			sql.add(" AND NVL(kmk.HKM_KBN,0) = 1 ");
			sql.add(" AND NVL(hkm.UKM_KBN,0) = 1 ");
			sql.add(" AND NVL(ukm.EXC_FLG,0) <> 0 ");

			if (DBUtil.isUseMySQL) {
				sql.add(" AND swk.SWK_DEN_DATE >= TIMESTAMPADD(MONTH,sim.SIM_MON,sim.SIM_STR_DATE) ");
			} else {
				sql.add(" AND swk.SWK_DEN_DATE >= ADD_MONTHS(sim.SIM_STR_DATE, sim.SIM_MON) ");
			}

			sql.add(" AND swk.SWK_DEN_DATE <= PKG_DATE_UTIL_getTukidoEnd(?) ", date);
			sql.add(" ) ");
			sql.add(" dtl ");
			sql.add(" ) ");
			sql.add(" GROUP BY hdr.KAI_CODE, PKG_DATE_UTIL_getTukidoEnd(hdr.SWK_DEN_DATE) ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				isChk = true;
				proc_ym = rs.getDate("END_MONTH");
			}

			// �O�ݕ]���Ώۂ̓`�[�̍X�V���Ɏ��s
			if (isChk) {

				isChk = false;

				sql = new VCreator();

				sql.add("");
				sql.add(" SELECT PROC_YM ");
				sql.add(" FROM REVALUATION_CTL ");
				sql.add(" WHERE KAI_CODE = ? ", companyCode);
				sql.add(" AND   PROC_YM = PKG_DATE_UTIL_getTukidoEnd(?) ", proc_ym);

				statement = DBUtil.getStatement(conn);
				rs = DBUtil.select(statement, sql);

				isChk = rs.next();

				if (isChk) {
					// ���R�[�h�����݂���ꍇ�͢UPDATE�
					sql = new VCreator();

					sql.add("");
					sql.add(" UPDATE REVALUATION_CTL SET ");
					sql.add("     STATUS_KBN = 1 ");
					sql.addYMDHMS("    ,UPD_DATE = ? ", getNow());
					sql.add("    ,PRG_ID = ? ", getProgramCode());
					sql.add("    ,USR_ID = ? ", getUserCode());
					sql.add(" WHERE  KAI_CODE = ? ", companyCode);
					sql.add(" AND PROC_YM = PKG_DATE_UTIL_getTukidoEnd(?) ", proc_ym);

					DBUtil.execute(conn, sql);

				} else {
					// ���R�[�h�����݂��Ȃ��ꍇ�͢INSERT�
					sql = new VCreator();

					sql.add("");
					sql.add(" INSERT INTO REVALUATION_CTL ( ");
					sql.add("        KAI_CODE ");
					sql.add("       ,PROC_YM ");
					sql.add("       ,STATUS_KBN ");
					sql.add("       ,INP_DATE ");
					sql.add("       ,PRG_ID ");
					sql.add("       ,USR_ID ");
					sql.add(" ) VALUES ( ");
					sql.add("        ? ", companyCode);
					sql.add("		,PKG_DATE_UTIL_getTukidoEnd(?) ", proc_ym);
					sql.add("       ,1 ");
					sql.addYMDHMS("	,? ", getNow());
					sql.add("       ,? ", getProgramCode());
					sql.add("       ,? ", getUserCode());
					sql.add(" ) ");

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
	 * �O�ݕ]���֓`�[�̑��݃`�F�b�N
	 * 
	 * @param companyCode
	 * @throws TException
	 */
	public List<Date> existSlip(String companyCode) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Date> list = new ArrayList<Date>();

		try {

			VCreator sql = new VCreator();

			sql.add(" SELECT PROC_YM ");
			sql.add(" FROM REVALUATION_CTL ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND KAI_CODE = ? ", companyCode);
			sql.add(" AND STATUS_KBN = 1 ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(rs.getDate("PROC_YM"));
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
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