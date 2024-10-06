package jp.co.ais.trans2.model.close;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �������ߏ����̎���
 * 
 * @author AIS
 */
public class MonthlyCloseImpl extends TModel implements MonthlyClose {

	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TException {

		// ���߂錎�����Z�����𔻒f
		boolean isSettltmentMonth = fp.isSettlementMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(),
			fp.getSettlementTerm(), fp.getMonthBeginningOfPeriod());

		// �ʏ팎�̏ꍇ�͌������Z
		if (!isSettltmentMonth) {
			fp.setClosedMonth(fp.getClosedMonth() + 1);
			fp.setSettlementStage(0);

			// ���Z���̏ꍇ
		} else {

			// �O�����ő�܂Œ��܂��Ă���Ό������Z
			if (fp.getFiscalMonth(fp.getFirstDateOfCurrentPeriodOfSettlement()) != fp.getClosedMonth()) {
				fp.setClosedMonth(fp.getClosedMonth() + 1);
				fp.setSettlementStage(0);
			} else if (fp.getClosedMonth() == 0 && fp.getSettlementStage() == 0) {
				fp.setClosedMonth(fp.getClosedMonth() + 1);
				fp.setSettlementStage(0);

			} else {
				fp.setSettlementStage(fp.getSettlementStage() + 1);
			}

		}

		// �N�x�J�z
		if (fp.getClosedMonth() == 12 && fp.getSettlementStage() == fp.getMaxSettlementStage()) {
			fp.setClosedMonth(0);
			fp.setSettlementStage(0);
			fp.setFiscalYear(fp.getFiscalYear() + 1);
			fp.setDateBeginningOfPeriod(DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), 1)));
			fp.setDateEndOfPeriod(DateUtil.addDay(
				DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), 1)), -1));

		}

		CloseDao dao = (CloseDao) getComponent(CloseDao.class);
		dao.update(companyCode, fp);

		// ���ߏ���蒼��
		CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
		Company company = cm.get(companyCode);

		return company.getFiscalPeriod();

	}

	public boolean isExistsNotClosedSlip(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();
		list.add(SlipState.FIELD_DENY.value);
		list.add(SlipState.FIELD_APPROVE.value);

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("   COUNT(1) ");
			sql.add(" FROM  SWK_DTL ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);

			// ���ꏳ�F���g�p���Ȃ��ꍇ�����۔F��A����ꏳ�F��̃`�F�b�N�s�v
			if (!getCompany().getAccountConfig().isUseFieldApprove()) {
				sql.adi(" AND   SWK_UPD_KBN NOT IN ?", list);
			}

			sql.add(" AND   SWK_UPD_KBN <> ? ", SlipState.UPDATE.value);
			sql.add(" AND   SWK_DEN_DATE <= ? ", lastDate);
			sql.add(" AND   CASE WHEN SWK_DEN_DATE = ? THEN SWK_KSN_KBN ", lastDate);
			sql.add("         ELSE 0 END <= ? ", fp.getCurrentSettlementStage());

			count = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (count != 0);
	}

	public boolean isExistsForeignCurrencyRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int count = 0;

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("   COUNT(*) ");
			sql.add(" FROM  GL_SWK_HDR ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add(" AND   SWK_DEN_DATE <= ? ", lastDate);
			sql.add(" AND   CASE WHEN SWK_DEN_DATE = ? THEN SWK_KSN_KBN ", lastDate);
			sql.add("         ELSE 0 END <= ? ", fp.getCurrentSettlementStage());
			sql.add(" AND   SWK_DEN_SYU = '01Z' "); // �T�Z�`�[���

			count = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (count != 0);
	}

	public boolean isUpdateRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int statusKbn = 0;

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("   STATUS_KBN ");
			sql.add(" FROM  REVALUATION_CTL ");
			sql.add(" WHERE KAI_CODE = ? ", companyCode);
			sql.add(" AND   PROC_YM  = ? ", lastDate);

			statusKbn = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (statusKbn == 1);
	}

	/**
	 * �x���������������Ă��Ȃ��f�[�^���擾����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return �x���������������Ă��Ȃ��f�[�^
	 * @throws TException
	 */
	public List<Message> getNotPaidData(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		List<Message> list = new ArrayList<Message>();

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			// �x���\��������߂�ꍇ�A�G���[
			// �擾����
			// �x���`�[�N������NULL�A������x����<=���ߓ�
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT DISTINCT ");
			sql.add("     zan.ZAN_DEN_NO, ");
			sql.add("     zan.ZAN_SIHA_CODE, ");
			sql.add("     tri.TRI_NAME_S, ");
			sql.add("     zan.ZAN_SIHA_DATE ");
			sql.add(" FROM AP_CTL ctl  ");
			sql.add("INNER JOIN AP_ZAN zan ON ctl.KAI_CODE = zan.KAI_CODE  ");
			sql.add("                     AND ctl.CNT_UTK_NO = zan.ZAN_UTK_NO  ");
			sql.add(" LEFT JOIN TRI_MST tri ON tri.KAI_CODE = zan.KAI_CODE  ");
			sql.add("                      AND tri.TRI_CODE = zan.ZAN_SIHA_CODE  ");
			sql.add(" WHERE ctl.KAI_CODE = ? ", companyCode);
			sql.add(" AND   ctl.CNT_STR_AUT_DATE IS NULL ");
			sql.add(" AND   ctl.CNT_CUR_SIHA_DATE <= ? ", lastDate);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingNotPaidData(rs));
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
	 * @param rs
	 * @return ���b�Z�[�W
	 * @throws Exception
	 */
	protected Message mappingNotPaidData(ResultSet rs) throws Exception {
		// I00427�F�`�[�ԍ��F{0}�A�x����F{1} {2}�A�x���\����F{3}
		Message msg = new Message("I00427", rs.getString("ZAN_DEN_NO"), rs.getString("ZAN_SIHA_CODE"),
			rs.getString("TRI_NAME_S"), DateUtil.toYMDString(rs.getDate("ZAN_SIHA_DATE")));
		return msg;
	}

	/**
	 * �R���Ǘ������擾����<br>
	 * �c�����c���Ă���D�E����敪�̓`�[���t
	 * 
	 * @return list: �R�����ߏ��bean���X�g<br>
	 *         vessel_code: �D�R�[�h<br>
	 *         oil_type_kbn: ����敪�R�[�h<br>
	 *         den_date: �`�[���t�̔N���̍ŏI���t
	 * @throws TException
	 */
	public List<BMCloseInfo> getBM() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT   ");
			sql.add("      ctl.VESSEL_CODE  ");
			sql.add("     ,vsl.VESSEL_NAME  ");
			sql.add("     ,ctl.OIL_TYPE_KBN  ");
			sql.add("     ,oil.OIL_TYPE_NAME ");
			sql.add("     ,MIN(ctl.DEN_DATE) DEN_DATE ");
			sql.add("FROM BM_CTL ctl   ");
			sql.add("LEFT OUTER JOIN BM_BOM_DTL bom ON  ctl.KAI_CODE    = bom.KAI_CODE  ");
			sql.add("                              AND ctl.DEN_DATE     = bom.SWK_DEN_DATE  ");
			sql.add("                              AND ctl.VESSEL_CODE  = bom.VESSEL_CODE  ");
			sql.add("                              AND ctl.OIL_TYPE_KBN = bom.OIL_TYPE_KBN  ");
			sql.add("LEFT OUTER JOIN CM_VESSEL_MST vsl   ON vsl.KAI_CODE    = ctl.KAI_CODE ");
			sql.add("                                   AND vsl.VESSEL_CODE = ctl.VESSEL_CODE ");
			sql.add("LEFT OUTER JOIN BM_OIL_TYPE_MST oil ON oil.KAI_CODE    = ctl.KAI_CODE  ");
			sql.add("                                   AND oil.OIL_TYPE    = ctl.OIL_TYPE_KBN ");
			sql.add("WHERE ctl.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND   oil.TATE_KBN = 0 ");
			sql.add(" AND   bom.BOM <> 0 ");
			sql.add("GROUP BY ctl.VESSEL_CODE  ");
			sql.add("        ,vsl.VESSEL_NAME  ");
			sql.add("        ,ctl.OIL_TYPE_KBN  ");
			sql.add("        ,oil.OIL_TYPE_NAME ");
			sql.add("ORDER BY ctl.VESSEL_CODE  ");
			sql.add("        ,ctl.OIL_TYPE_KBN  ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<BMCloseInfo> list = mappingBM(rs);

			DBUtil.close(rs);
			DBUtil.close(statement);

			return list;

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �߂�l���}�b�s���O<br>
	 * list: �R�����ߏ��bean���X�g<br>
	 * vessel_code: �D�R�[�h<br>
	 * oil_type_kbn: ����敪�R�[�h<br>
	 * den_date: �`�[���t�̔N���̍ŏI���t
	 * 
	 * @param rs
	 * @return BM���
	 * @throws Exception
	 */
	protected List<BMCloseInfo> mappingBM(ResultSet rs) throws Exception {
		List<BMCloseInfo> list = new ArrayList();
		while (rs.next()) {
			BMCloseInfo info = new BMCloseInfo();
			info.setVESSEL_CODE(rs.getString("VESSEL_CODE"));
			info.setVESSEL_NAME(rs.getString("VESSEL_NAME"));
			info.setOIL_TYPE_KBN(rs.getString("OIL_TYPE_KBN"));
			info.setOIL_TYPE_NAME(rs.getString("OIL_TYPE_NAME"));
			info.setDEN_DATE(DateUtil.getLastDate(rs.getDate("DEN_DATE")));
			list.add(info);
		}
		return list;
	}

	/**
	 * �R���Ǘ������擾����<br>
	 * �c�����c���Ă���D�E����敪�̓`�[���t
	 * 
	 * @param fp
	 * @return list: �R�����ߏ��bean���X�g<br>
	 *         vessel_code: �D�R�[�h<br>
	 *         oil_type_kbn: ����敪�R�[�h<br>
	 *         den_date: �`�[���t�̔N���̍ŏI���t
	 * @throws TException
	 */
	public List<BunkerInfo> getBunker(FiscalPeriod fp) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT   ");
			sql.add("      bnkr.GYO_NO ");
			sql.add("     ,bnkr.DEN_DATE  ");
			sql.add("     ,bnkr.DEN_YM  ");
			sql.add("     ,bnkr.DEN_NO  ");
			sql.add("     ,bnkr.SEISAN_DEN_NO  ");
			sql.add("     ,bnkr.DATA_KBN  ");
			sql.add("     ,bnkr.VESSEL_CODE  ");
			sql.add("     ,vsl.VESSEL_NAME  ");
			sql.add("     ,bnkr.OIL_TYPE_KBN  ");
			sql.add("     ,oil.OIL_TYPE_NAME ");
			sql.add("FROM BM_BUNKER_DTL bnkr   ");
			sql.add("LEFT OUTER JOIN CM_VESSEL_MST vsl   ON vsl.KAI_CODE    = bnkr.KAI_CODE ");
			sql.add("                                   AND vsl.VESSEL_CODE = bnkr.VESSEL_CODE ");
			sql.add("LEFT OUTER JOIN BM_OIL_TYPE_MST oil ON oil.KAI_CODE    = bnkr.KAI_CODE  ");
			sql.add("                                   AND oil.OIL_TYPE    = bnkr.OIL_TYPE_KBN ");
			sql.add("WHERE bnkr.KAI_CODE = ? ", getCompanyCode());
			sql.add(" AND    bnkr.DEN_DATE >= ?", fp.getFirstDateOfCurrentPeriodOfSettlement());
			sql.add(" AND    bnkr.DEN_DATE <= ?",
				DateUtil.getLastDate(DateUtil.addMonth(fp.getFirstDateOfClosedPeriodOfSettlement(), 1)));
			sql.add(" AND    bnkr.SEISAN_DEN_NO IS NULL ");
			sql.add(" AND    oil.TATE_KBN <> 1 "); // ���ւ̏ꍇ�͑ΏۊO
			sql.add(" AND    bnkr.DATA_KBN = 1");
			sql.add(" AND    EXISTS ( SELECT 1 FROM ( ");
			sql.add("                         SELECT DISTINCT ");
			sql.add("                             bnk.KAI_CODE ");
			sql.add("                            ,bnk.VESSEL_CODE ");
			sql.add("                            ,bnk.OIL_TYPE_KBN ");
			sql.add("                         FROM BM_BUNKER_DTL bnk ");
			sql.add("                         WHERE KAI_CODE = ? ", getCompanyCode());
			sql.add("                         AND   NOT EXISTS (SELECT 1 FROM BM_BOM_DTL bom ");
			sql.add("                                           WHERE bnk.KAI_CODE     = bom.KAI_CODE ");
			sql.add("                                           AND   bnk.VESSEL_CODE  = bom.VESSEL_CODE ");
			sql.add("                                           AND   bnk.OIL_TYPE_KBN = bom.OIL_TYPE_KBN ");
			sql.add("                                           AND   bom.BOM = 0 ");
			sql.add("                                           AND   bom.KAI_CODE =  ? ", getCompanyCode());
			sql.add("                                           AND   bom.SWK_DEN_DATE >= ?",
				fp.getFirstDateOfCurrentPeriodOfSettlement());
			sql.add("                                           AND   bom.SWK_DEN_DATE <= ?",
				DateUtil.getLastDate(DateUtil.addMonth(fp.getFirstDateOfClosedPeriodOfSettlement(), 1)));
			sql.add("                    )) bnk ");
			sql.add("                    WHERE bnk.KAI_CODE     = bnkr.KAI_CODE ");
			sql.add("                    AND   bnk.VESSEL_CODE  = bnkr.VESSEL_CODE ");
			sql.add("                    AND   bnk.OIL_TYPE_KBN = bnkr.OIL_TYPE_KBN ");
			sql.add("                   ) ");
			sql.add(" AND    bnkr.DEN_NO NOT IN (");
			sql.add("                SELECT   ");
			sql.add("                    bnkr.DEN_NO  ");
			sql.add("                FROM BM_BUNKER_DTL bnkr   ");
			sql.add("                WHERE bnkr.DEN_NO IN (SELECT SEISAN_DEN_NO FROM BM_BUNKER_DTL)");

			sql.add("             )");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			List<BunkerInfo> list = mappingBunker(rs);

			DBUtil.close(rs);
			DBUtil.close(statement);

			return list;

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �߂�l���}�b�s���O<br>
	 * list: �R�����ߏ��bean���X�g<br>
	 * vessel_code: �D�R�[�h<br>
	 * oil_type_kbn: ����敪�R�[�h<br>
	 * den_date: �`�[���t�̔N���̍ŏI���t
	 * 
	 * @param rs
	 * @return Bunker���
	 * @throws Exception
	 */
	protected List<BunkerInfo> mappingBunker(ResultSet rs) throws Exception {
		List<BunkerInfo> list = new ArrayList();
		while (rs.next()) {
			BunkerInfo info = new BunkerInfo();
			info.setVESSEL_CODE(rs.getString("VESSEL_CODE"));
			info.setVESSEL_NAME(rs.getString("VESSEL_NAME"));
			info.setOIL_TYPE_KBN(rs.getString("OIL_TYPE_KBN"));
			info.setOIL_TYPE_NAME(rs.getString("OIL_TYPE_NAME"));
			info.setDEN_DATE(DateUtil.getLastDate(rs.getDate("DEN_DATE")));
			list.add(info);
		}
		return list;
	}

	/**
	 * �����X�V���������
	 * 
	 * @return �����X�V�̎��
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TException {

		// ���߂錎�����Z�����𔻒f
		boolean isSettltmentMonth = fp.isSettlementMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(),
			fp.getSettlementTerm(), fp.getMonthBeginningOfPeriod());

		// ���߂錎�̑O�������Z�����𔻒f
		boolean isBeforeSettltmentMonth = fp.isSettlementMonth(
			DateUtil.addMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(), -1), fp.getSettlementTerm(),
			fp.getMonthBeginningOfPeriod());

		// ���߂錎�̑O�X�������Z�����𔻒f
		boolean isBeforeSettltment2Month = fp.isSettlementMonth(
			DateUtil.addMonth(fp.getFirstDateOfCurrentPeriodOfSettlement(), -2), fp.getSettlementTerm(),
			fp.getMonthBeginningOfPeriod());

		// ���Z�i�K�g�p
		boolean isUseSettlement = (fp.getMaxSettlementStage() != 0);

		if (fp.getClosedMonth() == 1 && fp.getSettlementStage() == 0) {
			// ������s���f�[�^�����ߌ� = �P�A���Z�i�K = �O�i���ߌ����O�A���Z�i�K���O�j
			fp.setClosedMonth(0);
			fp.setSettlementStage(0);

		} else if (fp.getClosedMonth() == 0 && fp.getSettlementStage() == 0) {
			// ������s���f�[�^�����ߌ� = �O�A���Z�i�K = �O

			if (isUseSettlement) {
				// ���Z�i�K���s���ꍇ�i���ߌ����P�Q�A���Z�i�K���ő匈�Z�i�K�|�P�j
				fp.setClosedMonth(12);
				fp.setSettlementStage(fp.getMaxSettlementStage() - 1);
			} else {
				// ���Z�i�K���s��Ȃ��ꍇ�i���ߌ����P�P�A���Z�i�K���O�j
				fp.setClosedMonth(11);
				fp.setSettlementStage(0);
			}
			fp.setFiscalYear(fp.getFiscalYear() - 1);
			fp.setDateBeginningOfPeriod(DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), -1)));
			fp.setDateEndOfPeriod(DateUtil.addDay(
				DateUtil.getFirstDate(DateUtil.addYear(fp.getDateBeginningOfPeriod(), 1)), -1));

		} else if (isSettltmentMonth) {
			// ������s���f�[�^�����Z���i���Z�i�K���s���ꍇ�j

			if (fp.getSettlementTerm().equals(SettlementTerm.MONTH)) {
				// ���Z�`�[���͋敪 = ����

				if (fp.getSettlementStage() == 0) {
					// ���Z�i�K = �O�i���ߌ������ߌ��|1�A���Z�i�K���ő匈�Z�i�K�j
					fp.setClosedMonth(fp.getClosedMonth() - 1);
					fp.setSettlementStage(fp.getMaxSettlementStage());
				} else {
					// ���Z�i�K = �O�ȊO�i���ߌ������̂܂܁A���Z�i�K�����Z�i�K�|�P�j
					fp.setSettlementStage(fp.getSettlementStage() - 1);
				}
			} else {
				// ���Z�`�[���͋敪 = �l�����A�����A��N

				if (fp.getSettlementStage() == 0) {
					// ���Z�i�K = �O�i���ߌ������ߌ��|�P�A���Z�i�K���O�j
					fp.setClosedMonth(fp.getClosedMonth() - 1);
					fp.setSettlementStage(0);
				} else {
					// ���Z�i�K = �O�ȊO�i���ߌ������̂܂܁A���Z�i�K�����Z�i�K�|�P�j
					fp.setSettlementStage(fp.getSettlementStage() - 1);
				}
			}

		} else if (!isSettltmentMonth) {
			// ������s���f�[�^���ʏ팎

			if (isUseSettlement) {
				// ���Z�i�K���s���ꍇ

				if (isBeforeSettltmentMonth) {
					// �O�������Z���i���ߌ������̂܂܁A���Z�i�K�����Z�i�K�|�P�j
					fp.setSettlementStage(fp.getSettlementStage() - 1);
				} else {
					// �O�����ʏ팎

					if (fp.getSettlementTerm().equals(SettlementTerm.QUARTER)) {
						// �l�����i���ߌ������ߌ��|�P�A���Z�i�K���ő匈�Z�i�K�j
						fp.setClosedMonth(fp.getClosedMonth() - 1);
						fp.setSettlementStage(fp.getMaxSettlementStage());

					} else if (fp.getSettlementTerm().equals(SettlementTerm.HALF)) { // ����

						if (isBeforeSettltment2Month) {
							// �O�X�������Z���i���ߌ������ߌ��|�P�A���Z�i�K���ő匈�Z�i�K�j
							fp.setClosedMonth(fp.getClosedMonth() - 1);
							fp.setSettlementStage(fp.getMaxSettlementStage());
						} else {
							// �O�X�����ʏ팎�i���ߌ������ߌ��|�P�A���Z�i�K�����̂܂܁j
							fp.setClosedMonth(fp.getClosedMonth() - 1);
						}

					} else if (fp.getSettlementTerm().equals(SettlementTerm.YEAR)) {
						// ��N�i���ߌ������ߌ��|�P�A���Z�i�K���O�j
						fp.setClosedMonth(fp.getClosedMonth() - 1);
						fp.setSettlementStage(0);
					}
				}
			} else {
				// ���Z�i�K���s��Ȃ��ꍇ�i���ߌ������ߌ��|�P�A���Z�i�K���O�j
				fp.setClosedMonth(fp.getClosedMonth() - 1);
				fp.setSettlementStage(0);
			}
		}

		CloseDao dao = (CloseDao) getComponent(CloseDao.class);
		dao.update(companyCode, fp);

		// ���ߏ���蒼��
		CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
		Company company = cm.get(companyCode);

		return company.getFiscalPeriod();

	}

	/****************************************************************************************************************
	 * �ȉ�������ДŃ��\�b�h
	 ****************************************************************************************************************/

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @param condition ��������
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		List<MonthData> list = new ArrayList<MonthData>();

		try {

			// ��БS�f�[�^���o
			CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
			CompanySearchCondition cmCondition = new CompanySearchCondition();

			// ��ЃR�[�h�����w�肵���ꍇ
			if (condition.getCompanyCodeList() != null) {
				for (String code_ : condition.getCompanyCodeList()) {
					cmCondition.addCode(code_);
				}
			}
			List<Company> companyList = cm.get(cmCondition);

			// ��Ђ�0���̏ꍇ�A�����Ȃ�
			if (companyList == null || companyList.isEmpty()) {
				return null;
			}

			{
				// ���O�C����ЍŗD��

				Company self = null;
				List<Company> newList = new ArrayList<Company>();
				for (Company cmp : companyList) {
					if (Util.equals(cmp.getCode(), getCompanyCode())) {
						// ���O�C����Ђ̏ꍇ�A��U����
						self = cmp;
					} else {
						newList.add(cmp);
					}
				}

				if (self != null) {
					newList.add(0, self);
				}

				companyList = newList;
			}

			// ��Џ��ɂ���āA�J��Ԃ��������s��
			for (Company company : companyList) {

				// ���ߏ��NULL�̏ꍇ�A�����s�v
				if (company.getFiscalPeriod() == null
					|| company.getFiscalPeriod().getFirstDateOfCurrentPeriodOfSettlement() == null) {
					continue;
				}

				// ���������̃f�[�^�쐬
				MonthData bean = new MonthData();

				SQLCreator sql = new SQLCreator();

				sql.add("SELECT ");
				sql.add("   t.KAI_CODE ");
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 1 THEN 1 ELSE 0 END) entry "); // �o�^�̌�
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 11 THEN 1 ELSE 0 END) fieldDeny "); // ����۔F�̌�
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 2 THEN 1 ELSE 0 END) fieldApprove "); // ���ꏳ�F�̌�
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 12 THEN 1 ELSE 0 END) deny "); // �o���۔F�̌�
				sql.add("  ,SUM(CASE WHEN t.SWK_UPD_KBN = 3 THEN 1 ELSE 0 END) approve "); // �o�����F�̌�
				sql.add(" FROM ( ");

				sql.add(" ( SELECT hdr.KAI_CODE, hdr.SWK_UPD_KBN");
				sql.add("     FROM GL_SWK_HDR hdr");
				sql.add(getWhereSql(company));
				sql.add(" ) ");

				sql.add(" UNION ALL ");

				sql.add(" ( SELECT hdr.KAI_CODE, hdr.SWK_UPD_KBN");
				sql.add("     FROM AP_SWK_HDR hdr");
				sql.add(getWhereSql(company));
				sql.add(" ) ");

				sql.add(" UNION ALL ");

				sql.add(" ( SELECT hdr.KAI_CODE, hdr.SWK_UPD_KBN");
				sql.add("     FROM AR_SWK_HDR hdr");
				sql.add(getWhereSql(company));
				sql.add(" ) ");
				sql.add("      ) t ");
				sql.add(" GROUP BY KAI_CODE ");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				bean.setCompany(company);
				bean.setCompanyCode(company.getCode());

				if (rs.next()) {

					bean.setEntry(rs.getInt("entry")); // �o�^�̌�
					bean.setFieldDeny(rs.getInt("fieldDeny")); // ����۔F�̌�
					bean.setFieldApprove(rs.getInt("fieldApprove")); // ���ꏳ�F�̌�
					bean.setDeny(rs.getInt("deny")); // �o���۔F�̌�
					bean.setApprove(rs.getInt("approve")); // �o�����F�̌�

				} else {

					bean.setEntry(0); // �o�^�̌�
					bean.setFieldDeny(0); // ����۔F�̌�
					bean.setFieldApprove(0); // ���ꏳ�F�̌�
					bean.setDeny(0); // �o���۔F�̌�
					bean.setApprove(0); // �o�����F�̌�
				}

				DBUtil.close(rs);
				DBUtil.close(statement);

				list.add(bean);
			}

		} catch (Exception e) {
			throw new TException("E00009", e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * SQL����WHERE�̎擾
	 * 
	 * @param company ��Џ��
	 * @return SQL����WHERE
	 */
	protected String getWhereSql(Company company) {
		SQLCreator sql = new SQLCreator();
		FiscalPeriod fp = company.getFiscalPeriod();

		Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

		// ��ЃR�[�h �� ��Џ��̉�ЃR�[�h
		// �X�V�敪 <> 4�i�X�V�j
		// �`�[���t <= ��Џ��̒��ߓ��i���ߌ��̖����j
		// �`�[���t �� ��Џ��̒��ߓ��i���ߌ��̖����j 0 <= ��Џ��̌��Z�i�K
		// �`�[���t <> ��Џ��̒��ߓ��i���ߌ��̖����j ���Z�敪 <= ��Џ��̌��Z�i�K

		sql.add("WHERE hdr.KAI_CODE = ? ", company.getCode());
		sql.add("  AND hdr.SWK_UPD_KBN <> 4 ");
		sql.add("  AND hdr.SWK_DEN_DATE <= ? ", lastDate);
		sql.add("  AND CASE WHEN hdr.SWK_DEN_DATE = ?  ", lastDate);
		sql.add("           THEN hdr.SWK_KSN_KBN ELSE 0 END ");
		sql.add("      <= ? ", fp.getCurrentSettlementStage());

		return sql.toSQL();
	}

	/**
	 * �`�[���쐬��CM_FUND_DTL�����݂�����G���[
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return �`�[���쐬��CM_FUND_DTL�����݂���
	 * @throws TException
	 */
	public boolean isExistsNotSlipByCmFund(String companyCode, FiscalPeriod fp) throws TException {

		Connection conn = DBUtil.getConnection();
		int count = 0;
		String[] checkSys = null;

		try {
			checkSys = ServerConfig.getProperties("trans.check.sys.cm.fund.monthly");
		} catch (Exception e) {
			// �����Ȃ�

		}

		if (checkSys == null || checkSys.length == 0) {
			// �ݒ肪����������߂�
			return false;
		}

		try {

			Date lastDate = DateUtil.getLastDate(fp.getFirstDateOfCurrentPeriodOfSettlement());

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT COUNT(*) FROM CM_FUND_DTL ");
			sql.add(" WHERE  KAI_CODE  = ? ", companyCode);
			sql.add(" AND    DEN_DATE <= ? ", lastDate);
			sql.add(" AND    DEN_NO IS NULL ");
			sql.add(" AND    SYS_KBN IN ");
			sql.addInStatement(checkSys);

			count = DBUtil.selectOneInt(conn, sql.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return (count != 0);
	}
}
