package jp.co.ais.trans2.model.balance;

import java.math.*;
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
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �c���}�l�[�W������
 */
public class BalanceManagerImpl extends TModel implements BalanceManager {

	/** true:CM_FUND_DTL�o�^���s���� */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/** �v�Z���W�b�N */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 * AP/AR�c���X�V
	 * 
	 * @param slip �`�[
	 */
	public void updateBalance(Slip slip) {

		// �X�V�̏ꍇ�A�������N���A
		BalanceCondition condition = new BalanceCondition();
		condition.setCompanyCode(slip.getCompanyCode()); // ��ЃR�[�h
		condition.setEraseSlipNo(slip.getSlipNo()); // �`�[�ԍ�
		condition.setProgramCode(getProgramCode()); // �v���O����ID
		condition.setUserCode(getUserCode()); // ���[�U�[�R�[�h
		condition.setUpdateDate(getNow()); // �X�V���t

		// ���c���̍X�V(�x���ς݂�������)
		clearApBalance(condition);

		// ���c���̍폜(��ЃR�[�h,�`�[�ԍ�)
		clearArBalance(condition);

		// �c���X�V
		List<AP_ZAN> apList = new ArrayList<AP_ZAN>();
		List<AR_ZAN> arList = new ArrayList<AR_ZAN>();

		String dataKind = slip.getHeader().getSWK_DATA_KBN();

		for (SWK_DTL dtl : slip.getDetails()) {
			if (dtl.isFunctionalCurrency()) {
				continue;
			}

			AP_ZAN apZan = dtl.getAP_ZAN();
			if (apZan != null) {
				// apZan.setZAN_DEN_DATE(); // ���c���`�[���t
				// apZan.setZAN_DEN_NO(); // ���c���`�[�ԍ�
				// apZan.setZAN_GYO_NO(); // ���c���s�ԍ�
				apZan.setZAN_JSK_DATE(dtl.getSWK_DEN_DATE()); // �`�[���t
				apZan.setZAN_SIHA_DEN_NO(dtl.getSWK_DEN_NO()); // �`�[�ԍ�
				apZan.setZAN_SIHA_GYO_NO(dtl.getSWK_GYO_NO()); // �s�ԍ�

				apZan.setUPD_DATE(getNow()); // �X�V���t
				apZan.setPRG_ID(getProgramCode()); // �v���O�����h�c
				apZan.setUSR_ID(getUserCode()); // ���[�U�[�h�c
				apList.add(apZan);
			}

			AR_ZAN arZan = dtl.getAR_ZAN();
			if (arZan != null) {
				arZan.setZAN_DATA_KBN(dataKind); // �f�[�^�敪
				arZan.setZAN_SOUSAI_FLG(0); // ���E�t���O
				arZan.setZAN_KESI_DEN_DATE(dtl.getSWK_DEN_DATE()); // �������t
				arZan.setZAN_KESI_DEN_NO(dtl.getSWK_DEN_NO()); // �����`�[�ԍ�
				arZan.setZAN_KESI_GYO_NO(dtl.getSWK_GYO_NO()); // �����`�[�s�ԍ�
				arZan.setZAN_KESI_IN_KIN(dtl.getSWK_IN_KIN()); // �������z(�O��)
				arZan.setZAN_KESI_KIN(dtl.getSWK_KIN()); // �������z
				arZan.setZAN_SEI_IN_KIN(null); // �������z(�O��) �N���A
				arZan.setZAN_SEI_KIN(null); // �������z �N���A
				arZan.setUPD_DATE(getNow()); // �X�V���t
				arZan.setPRG_ID(getProgramCode()); // �v���O�����h�c
				arZan.setUSR_ID(getUserCode()); // ���[�U�[�h�c

				arList.add(arZan);
			}
		}

		// ���c��
		if (!apList.isEmpty()) {
			updateApBalance(apList);
		}

		// ���c��
		if (!arList.isEmpty()) {
			insertArBalance(arList);
		}

		if (isUseCmFund) {
			try {
				// �폜 && �`�[�ԍ����X�g���擾����
				List<String> apSlipNoList = deleteCmFundInfoAPAR(condition, true);
				List<String> arSlipNoList = deleteCmFundInfoAPAR(condition, false);

				if (apSlipNoList != null && apSlipNoList.size() > 0) {
					updateCmFundDtl(slip.getCompanyCode(), apSlipNoList, true);
				}
				if (arSlipNoList != null && arSlipNoList.size() > 0) {
					updateCmFundDtl(slip.getCompanyCode(), arSlipNoList, false);
				}
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * AP�c���Ƀ��b�N��������(�s�P��)
	 * 
	 * @param list �Ώێc��(����)���X�g
	 * @throws TException �r�����s
	 */
	public void lockApBalance(List<BalanceCondition> list) throws TException {

		if (isOtherApErased(list)) {
			throw new TException(getMessage("I00115"));// ���ɑ����[�U�ɂ���ď������܂ꂽ�f�[�^�����݂��܂��B�ēx�������s���Ă��������B
		}

		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);

		for (BalanceCondition condition : list) {
			String slipNo = condition.getSlipNo();
			String lineNo = String.valueOf(condition.getSlipLineNo());

			// 2�F���c������
			codeExManager.exclude("2", slipNo, lineNo);
		}
	}

	/**
	 * �����[�U�ɐ�ɏ������܂�Ă��Ȃ����`�F�b�N(AP)
	 * 
	 * @param list �Ώێc��(����)���X�g
	 * @return true:�������܂�Ă���
	 */
	protected boolean isOtherApErased(List<BalanceCondition> list) {

		// �`�F�b�N�p���X�g�ɃR�s�[
		List<BalanceCondition> checkList = new ArrayList<BalanceCondition>(list.size());
		checkList.addAll(list);

		// �`�[�ԍ���Z�߂čČ���
		BalanceCondition condition = new BalanceCondition();
		condition.setCompanyCode(getCompanyCode());

		for (BalanceCondition con : list) {
			condition.setNotIncledEraseSlipNo(con.getNotIncledEraseSlipNo());
			condition.addSlipNo(con.getSlipNo());
		}

		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);
		List<AP_ZAN> resList = apBalanceDao.findForExists(condition);

		for (AP_ZAN zan : resList) {
			String slipNo = zan.getZAN_DEN_NO();
			int slipLineNo = zan.getZAN_GYO_NO();

			for (BalanceCondition con : checkList) {
				if (slipNo.equals(con.getSlipNo()) && slipLineNo == con.getSlipLineNo()) {
					checkList.remove(con);
					break;
				}
			}
		}

		return !checkList.isEmpty();
	}

	/**
	 * AR�c���Ƀ��b�N��������(�s�P��)
	 * 
	 * @param list �Ώێc��(����)���X�g
	 * @throws TException �r�����s
	 */
	public void lockArBalance(List<BalanceCondition> list) throws TException {

		if (isOtherArErased(list)) {
			throw new TException(getMessage("I00115"));// ���ɑ����[�U�ɂ���ď������܂ꂽ�f�[�^�����݂��܂��B�ēx�������s���Ă��������B
		}

		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);

		for (BalanceCondition condition : list) {
			String slipNo = condition.getSlipNo();
			String lineNo = String.valueOf(condition.getSlipLineNo());

			// 1�F���c������
			codeExManager.exclude("1", slipNo, lineNo);
		}
	}

	/**
	 * �����[�U�ɐ�ɏ������܂�Ă��Ȃ����`�F�b�N(AR)
	 * 
	 * @param list �Ώێc��(����)���X�g
	 * @return true:�������܂�Ă���
	 */
	protected boolean isOtherArErased(List<BalanceCondition> list) {

		// �`�F�b�N�p���X�g�ɃR�s�[
		List<BalanceCondition> checkList = new ArrayList<BalanceCondition>(list.size());
		checkList.addAll(list);

		// �`�[�ԍ���Z�߂čČ���
		BalanceCondition condition = new BalanceCondition();
		condition.setCompanyCode(getCompanyCode());

		for (BalanceCondition con : list) {
			condition.setNotIncledEraseSlipNo(con.getNotIncledEraseSlipNo());
			condition.addSlipNo(con.getSlipNo());
		}

		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
		List<AR_ZAN> resList = arBalanceDao.findForExists(condition);

		for (AR_ZAN zan : resList) {
			String slipNo = zan.getZAN_SEI_DEN_NO();
			int slipLineNo = zan.getZAN_SEI_GYO_NO();
			BigDecimal kin = zan.getNON_KESI_KIN(); // �����Ώۋ��z

			for (BalanceCondition con : checkList) {
				if (slipNo.equals(con.getSlipNo()) && slipLineNo == con.getSlipLineNo()) {
					// �����Ώۋ��z
					if (kin.compareTo(con.getKin()) != 0) {
						return true;
					}

					checkList.remove(con);
					break;
				}
			}
		}

		return !checkList.isEmpty();
	}

	/**
	 * AP�c���̃��b�N����
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockApBalance(BalanceCondition condition) throws TException {
		String slipNo = condition.getSlipNo();
		String lineNo = String.valueOf(condition.getSlipLineNo());

		// 2�F���c������
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("2", slipNo, lineNo);
	}

	/**
	 * AP�c���̃��b�N����
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockApBalanceList(BalanceCondition condition) throws TException {

		// 2�F���c������
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("2", condition.getSlipNoList(), condition.getRowNoList());
	}

	/**
	 * AR�c���̃��b�N����(�s�P��)
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockArBalance(BalanceCondition condition) throws TException {
		String slipNo = condition.getSlipNo();
		String lineNo = String.valueOf(condition.getSlipLineNo());

		// 1�F���c������
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("1", slipNo, lineNo);
	}

	/**
	 * AR�c���̃��b�N����(�s�P��)
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockArBalanceList(BalanceCondition condition) throws TException {

		// 1�F���c������
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("1", condition.getSlipNoList(), condition.getRowNoList());
	}

	/**
	 * ���c���f�[�^�擾
	 * 
	 * @param conditon ����
	 * @return �c���f�[�^
	 */
	public List<AP_ZAN> getApBalance(BalanceCondition conditon) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);
		return apBalanceDao.findByCondition(conditon.toSQL());
	}

	/**
	 * ���c���폜(�������܂�Ă��Ȃ���Ԃɖ߂�)
	 * 
	 * @param condition ����
	 */
	public void clearApBalance(BalanceCondition condition) {
		try {
			List<String> slipNoList = null;
			if (isUseCmFund) {
				// ��ɍ폜
				slipNoList = deleteCmFundInfoAPAR(condition, true);
			}

			AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);
			// �x���ς݂�������
			apBalanceDao.updateCancelBalance(condition);

			if (isUseCmFund) {
				if (slipNoList != null && slipNoList.size() > 0) {
					updateCmFundDtl(condition.getCompanyCode(), slipNoList, false);
				}
			}
		} catch (TException e) {
			throw new TRuntimeException(e);
		}
	}

	/**
	 * ���c���X�V(�x���ς݂̏�Ԃɂ���)
	 * 
	 * @param list �c�����X�g
	 */
	public void updateApBalance(List<AP_ZAN> list) {

		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		BalanceCondition condition = new BalanceCondition();

		// �x���ς݂̏�ԂɍX�V
		for (AP_ZAN zan : list) {
			condition.setCompanyCode(zan.getKAI_CODE()); // ��ЃR�[�h
			condition.setSlipDate(zan.getZAN_DEN_DATE()); // ���c�� �`�[���t
			condition.setSlipNo(zan.getZAN_DEN_NO()); // ���c�� �`�[�ԍ�
			condition.setSlipLineNo(zan.getZAN_GYO_NO()); // ���c�� �s�ԍ�
			condition.setEraseSlipDate(zan.getZAN_JSK_DATE()); // �`�[���t(���ѓ�)
			condition.setEraseSlipNo(zan.getZAN_SIHA_DEN_NO()); // �`�[�ԍ�(�x���`�[�ԍ�)
			condition.setEraseSlipLineNo(zan.getZAN_SIHA_GYO_NO()); // �s�ԍ�(�x���s�ԍ�)
			condition.setProgramCode(getProgramCode()); // �v���O����ID
			condition.setUserCode(getUserCode()); // ���[�U�[�R�[�h
			condition.setUpdateDate(getNow()); // �X�V��

			// ���c���̍X�V()
			apBalanceDao.updateBalance(condition);
		}
	}

	/**
	 * ���c���f�[�^�擾(�T�}����)
	 * 
	 * @param conditon ����
	 * @return List �f�[�^
	 */
	public List<AR_ZAN> getSummaryArBalance(BalanceCondition conditon) {
		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
		return arBalanceDao.findSummry(conditon.toSQL());
	}

	/**
	 * ���c���f�[�^�擾
	 * 
	 * @param conditon ����
	 * @return �c���f�[�^
	 */
	public List<AR_ZAN> getArBalance(BalanceCondition conditon) {
		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
		return arBalanceDao.findByCondition(conditon.toSQL());
	}

	/**
	 * ���c���폜(�������݃f�[�^�̍폜)
	 * 
	 * @param condition ����
	 */
	public void clearArBalance(BalanceCondition condition) {

		try {
			List<String> slipNoList = null;
			if (isUseCmFund) {
				// ��ɍ폜
				slipNoList = deleteCmFundInfoAPAR(condition, false);
			}
			AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
			arBalanceDao.deleteBySlipNo(condition.getCompanyCode(), condition.getEraseSlipNo());

			if (isUseCmFund) {
				if (slipNoList != null && slipNoList.size() > 0) {
					updateCmFundDtl(condition.getCompanyCode(), slipNoList, false);
				}
			}
		} catch (TException e) {
			throw new TRuntimeException(e);
		}
	}

	/**
	 * ���c���X�V(�������݃f�[�^�̒ǉ�)<br>
	 * swkDtl��Beans�œn���A�c�����X�V
	 * 
	 * @param list �c�����X�g
	 */
	public void insertArBalance(List<AR_ZAN> list) {
		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);

		for (AR_ZAN zan : list) {
			arBalanceDao.insert(zan);
		}
	}

	/**
	 * ���c���X�V(�������݃f�[�^�̒ǉ�)<br>
	 * swkDtl��Beans�œn���A�c�����X�V
	 * 
	 * @param list �c�����X�g
	 */
	public void insertApBalance(List<AP_ZAN> list) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		for (AP_ZAN zan : list) {
			apBalanceDao.insert(zan);
		}
	}

	/**
	 * �o�^
	 * 
	 * @param bean �G���e�B�e�B
	 */
	public void insertByUpdateDate(AP_ZAN bean) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		apBalanceDao.insertByUpdateDate(bean);
	}

	/**
	 * �X�V
	 * 
	 * @param bean �c�����X�g
	 */
	public int updateByUpdateDate(AP_ZAN bean) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		return apBalanceDao.updateByUpdateDate(bean, getNow());
	}

	/**
	 * �폜
	 * 
	 * @param bean �c�����X�g
	 * @return int
	 */
	public int deleteByUpdateDate(AP_ZAN bean) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		return apBalanceDao.deleteByUpdateDate(bean);
	}

	/**
	 * ���c�� 1������(���̓L�[�̂�)
	 * 
	 * @param condition ����
	 * @return ���c��
	 */
	public AP_ZAN getAP(BalanceCondition condition) {
		AP_ZANDao dao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		String compCode = condition.getCompanyCode();
		String slipNo = condition.getSlipNo();
		int slipLineNo = condition.getSlipLineNo();

		return dao.findByPrimaryKey(compCode, slipNo, slipLineNo);
	}

	/**
	 * ���c���c�� 1������(���̓L�[�̂�)
	 * 
	 * @param condition ����
	 * @return ���c��
	 */
	public AR_ZAN getAR(BalanceCondition condition) {
		AR_ZANDao dao = (AR_ZANDao) getComponent(AR_ZANDao.class);

		String compCode = condition.getCompanyCode();
		String slipNo = condition.getSlipNo();
		int slipLineNo = condition.getSlipLineNo();

		return dao.findByPrimaryKey(compCode, slipNo, slipLineNo);
	}

	/**
	 * CM_FUND_DTL���̍폜
	 * 
	 * @param condition
	 * @param isAP
	 * @return List<String>
	 * @throws TException
	 */
	public List<String> deleteCmFundInfoAPAR(BalanceCondition condition, boolean isAP) throws TException {
		Connection conn = DBUtil.getConnection();

		List<String> slipNoList = new ArrayList<String>();

		try {
			// �c���擾
			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  KAI_CODE, ");
			if (isAP) {
				sql.add("  ZAN_DEN_DATE, ");
				sql.add("  ZAN_DEN_NO ");
				sql.add(" FROM AP_ZAN ");
				sql.add(" WHERE 1 = 1");
				if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
					sql.add(" AND KAI_CODE = ? ", condition.getCompanyCode());
				}
				sql.add(" AND   ZAN_SIHA_DEN_NO = ? ", condition.getEraseSlipNo());
			} else {
				sql.add("  ZAN_SEI_DEN_DATE, ");
				sql.add("  ZAN_SEI_DEN_NO, ");
				sql.add("  ZAN_SEI_GYO_NO, ");
				sql.add("  ZAN_SEI_NO, ");
				sql.add("  ZAN_DEP_CODE, ");
				sql.add("  ZAN_TRI_CODE ");
				sql.add(" FROM AR_ZAN ");
				sql.add(" WHERE 1 = 1");
				if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
					sql.add(" AND KAI_CODE = ? ", condition.getCompanyCode());
				}
				sql.add(" AND   ZAN_KESI_DEN_NO = ? ", condition.getEraseSlipNo());
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String kaiCode = null;
			String slipNo = null;
			Date slipDate = null;

			String seiNo = null;
			int gyoNo = -1;
			String depCode = null;
			String triCode = null;

			while (rs.next()) {

				kaiCode = rs.getString("KAI_CODE");
				if (isAP) {
					slipNo = rs.getString("ZAN_DEN_NO");
					slipDate = rs.getDate("ZAN_DEN_DATE");
				} else {
					slipNo = rs.getString("ZAN_SEI_DEN_NO");
					slipDate = rs.getDate("ZAN_SEI_DEN_DATE");

					seiNo = rs.getString("ZAN_SEI_NO");
					gyoNo = rs.getInt("ZAN_SEI_GYO_NO");
					depCode = rs.getString("ZAN_DEP_CODE");
					triCode = rs.getString("ZAN_TRI_CODE");
				}
				// �폜����
				sql = new VCreator();
				sql.add(" DELETE FROM CM_FUND_DTL ");
				sql.add(" WHERE KAI_CODE     = ? ", kaiCode);
				sql.add(" AND   KEY_DEN_DATE = ? ", slipDate);
				sql.add(" AND   KEY_DEN_NO   = ? ", slipNo);
				if (seiNo != null) {
					sql.add(" AND   KEY_SEI_NO   = ? ", seiNo);
				}
				if (gyoNo != -1) {
					sql.add(" AND   KEY_GYO_NO   = ? ", gyoNo);
				}
				if (depCode != null) {
					sql.add(" AND   KEY_DEP_CODE   = ? ", depCode);
				}
				if (triCode != null) {
					sql.add(" AND   KEY_TRI_CODE   = ? ", triCode);
				}
				sql.add(" AND   DATA_TYPE    = 2 ");

				DBUtil.execute(sql);

				slipNoList.add(slipNo);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			return slipNoList;

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
	 * @param kaiCode
	 * @param slipNoList
	 * @param isAP true:AP false:AR
	 * @throws TException
	 */
	public void updateCmFundDtl(String kaiCode, List<String> slipNoList, boolean isAP) throws TException {
		Connection conn = DBUtil.getConnection();
		VCreator sql = null;
		try {
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

			String curCode = null;
			BigDecimal inKin = BigDecimal.ZERO;
			BigDecimal kin = BigDecimal.ZERO;
			String keyCurCode = getCompany().getAccountConfig().getKeyCurrency().getCode();
			RateManager rateMn = get(RateManager.class);

			if (isAP) {
				// ���c������
				sql = new VCreator();
				sql.add(" SELECT ");
				sql.add("  zan.KAI_CODE, ");
				sql.add("  zan.ZAN_SIHA_DATE, ");
				sql.add("  zan.ZAN_DEN_DATE, ");
				sql.add("  zan.ZAN_DEN_NO, ");
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

				sql.add(" WHERE 1 = 1 ");
				if (!Util.isNullOrEmpty(kaiCode)) {
					sql.add(" AND zan.KAI_CODE = ?", kaiCode);
				}
				sql.add(" AND   zan.ZAN_DEN_NO IN ");
				sql.addInStatement(slipNoList);
				sql.add(" AND zan.ZAN_SIHA_DEN_NO IS NULL ");
				sql.add(" AND zan.ZAN_FURI_CBK_CODE IS NOT NULL ");

				sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_SIHA_DATE , zan.ZAN_DEN_NO ");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				while (rs.next()) {
					// ��s�����ʉ�
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
						inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), getCompany());
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
					sql.add("    ,NULL ");
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
			} else {
				// ���c������
				sql = new VCreator();
				sql.add(" SELECT ");
				sql.add("  zan.KAI_CODE, ");
				sql.add("  zan.ZAN_AR_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_NO, ");
				sql.add("  zan.ZAN_DEP_CODE, ");
				sql.add("  dep.DEP_NAME, ");
				sql.add("  swk.SWK_TEK, ");
				sql.add("  zan.ZAN_TRI_CODE, ");
				sql.add("  tri.TRI_NAME, ");
				sql.add("  SUM(NVL(zan.ZAN_SEI_KIN,0)) ZAN_SEI_KIN, ");
				sql.add("  SUM(NVL(zan.ZAN_SEI_IN_KIN,0)) ZAN_SEI_IN_KIN, ");
				sql.add("  zan.ZAN_CUR_CODE, ");
				sql.add("  cbk.CUR_CODE, ");
				sql.add("  cur.DEC_KETA, ");
				sql.add("  cur.RATE_POW, ");
				sql.add("  swk.SWK_SYS_KBN ZAN_SYS_KBN, ");
				sql.add("  swk.SWK_DEN_SYU, ");
				sql.add("  syu.DEN_SYU_NAME, ");
				sql.add("  org.ZAN_CBK_CODE, ");
				sql.add("  zan.ZAN_SEI_NO,");
				sql.add("  zan.ZAN_SEI_GYO_NO, ");
				sql.add("  org.INP_DATE, ");
				sql.add("  org.UPD_DATE, ");
				sql.add("  org.PRG_ID, ");
				sql.add("  org.USR_ID ");

				sql.add(" FROM ( ");
				sql.add("     SELECT ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO, ");
				sql.add("         SUM(NVL(sei.ZAN_SEI_KIN,0)) ZAN_SEI_KIN,");
				sql.add("         SUM(NVL(sei.ZAN_SEI_IN_KIN,0)) ZAN_SEI_IN_KIN ");
				sql.add("     FROM AR_ZAN sei ");
				sql.add(" WHERE 1 = 1 ");
				if (!Util.isNullOrEmpty(kaiCode)) {
					sql.add(" AND sei.KAI_CODE = ?", kaiCode);
				}
				sql.add("     AND   sei.ZAN_SEI_DEN_NO IN ");
				sql.addInStatement(slipNoList);
				sql.add("     GROUP BY ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO ");
				sql.add("     UNION ALL ");
				sql.add("     SELECT ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO, ");
				sql.add("         SUM(NVL(sei.ZAN_KESI_KIN,0)) * -1 ZAN_SEI_KIN,");
				sql.add("         SUM(NVL(sei.ZAN_KESI_IN_KIN,0))  * -1 ZAN_SEI_IN_KIN ");
				sql.add("     FROM AR_ZAN sei ");
				sql.add(" WHERE 1 = 1 ");
				if (!Util.isNullOrEmpty(kaiCode)) {
					sql.add(" AND sei.KAI_CODE = ?", kaiCode);
				}
				sql.add("     AND   sei.ZAN_SEI_DEN_NO IN ");
				sql.addInStatement(slipNoList);
				sql.add("     GROUP BY ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO ");
				sql.add(" ) zan ");

				sql.add(" INNER JOIN AR_ZAN org ");
				sql.add(" ON  zan.KAI_CODE = org.KAI_CODE ");
				sql.add(" AND zan.ZAN_DEP_CODE = org.ZAN_DEP_CODE ");
				sql.add(" AND zan.ZAN_TRI_CODE = org.ZAN_TRI_CODE ");
				sql.add(" AND NVL(zan.ZAN_SEI_NO, ' ') = NVL(org.ZAN_SEI_NO, ' ') ");
				sql.add(" AND zan.ZAN_SEI_DEN_DATE = org.ZAN_SEI_DEN_DATE ");
				sql.add(" AND zan.ZAN_SEI_DEN_NO = org.ZAN_SEI_DEN_NO  ");
				sql.add(" AND zan.ZAN_SEI_GYO_NO = org.ZAN_SEI_GYO_NO ");
				sql.add(" AND org.ZAN_DATA_KBN = '31' ");
				sql.add(" AND org.ZAN_KESI_DEN_NO IS NULL ");

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
				sql.add(" ON  org.KAI_CODE     = cbk.KAI_CODE ");
				sql.add(" AND org.ZAN_CBK_CODE = cbk.CBK_CBK_CODE ");

				sql.add(" INNER JOIN CUR_MST cur ");
				sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
				sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

				sql.add(" GROUP BY ");
				sql.add("  zan.KAI_CODE, ");
				sql.add("  zan.ZAN_AR_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_NO, ");
				sql.add("  zan.ZAN_DEP_CODE, ");
				sql.add("  dep.DEP_NAME, ");
				sql.add("  swk.SWK_TEK, ");
				sql.add("  zan.ZAN_TRI_CODE, ");
				sql.add("  tri.TRI_NAME, ");
				sql.add("  zan.ZAN_CUR_CODE, ");
				sql.add("  cbk.CUR_CODE, ");
				sql.add("  cur.DEC_KETA, ");
				sql.add("  cur.RATE_POW, ");
				sql.add("  swk.SWK_SYS_KBN, ");
				sql.add("  swk.SWK_DEN_SYU, ");
				sql.add("  syu.DEN_SYU_NAME, ");
				sql.add("  org.ZAN_CBK_CODE, ");
				sql.add("  zan.ZAN_SEI_NO,");
				sql.add("  zan.ZAN_SEI_GYO_NO, ");
				sql.add("  org.INP_DATE, ");
				sql.add("  org.UPD_DATE, ");
				sql.add("  org.PRG_ID, ");
				sql.add("  org.USR_ID ");
				sql.add("  HAVING SUM(NVL(zan.ZAN_SEI_KIN,0)) <> 0 OR SUM(NVL(zan.ZAN_SEI_IN_KIN,0)) <> 0 ");

				sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_AR_DATE , zan.ZAN_SEI_DEN_NO ");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				while (rs.next()) {
					// ��s�����ʉ�
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
						inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), getCompany());
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
					sql.add("    ,? ", rs.getString("ZAN_SYS_KBN"));
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

			}

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
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