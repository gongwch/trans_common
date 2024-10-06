package jp.co.ais.trans2.model.security;

import java.util.*;
import java.util.Map.Entry;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.cargo.*;
import jp.co.ais.trans2.model.code.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.port.*;
import jp.co.ais.trans2.model.tax.*;
import jp.co.ais.trans2.model.vessel.*;
import jp.co.ais.trans2.model.voyage.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * �V�X�e���}�l�[�W������
 */
public class SystemManagerImpl extends TModel implements SystemManager {

	/**
	 * ���[�U�G���g��(���O�C��)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @throws TException
	 */
	public void entryUser(String companyCode, String userCode) throws TException {

		if (ServerConfig.isLoginManagedMode()) {
			// ���O�C��
			UserExclusiveManager manager = (UserExclusiveManager) getComponent(UserExclusiveManager.class);

			if (!manager.canEntry(companyCode, userCode)) {
				throw new TException(getMessage("I00155"));// �w�胆�[�U�[�͊��Ƀ��O�C������Ă��܂��B
			}

			manager.exclude(companyCode, userCode);
		}

		// �����r������
		Lock lock = (Lock) getComponent(Lock.class);
		lock.setCode(companyCode, userCode);
		lock.unlockAll();
	}

	/**
	 * �w�肳���v���O�������J���邩�ǂ���(���j���[�N���b�N�^�C�~���O)
	 * 
	 * @param log ���O���
	 * @return true:�J����
	 * @throws TException
	 */
	public boolean canOpenProgram(Log log) throws TException {

		if (!ServerConfig.isSystemRegulatedNumberCheck()) {
			return true;
		}

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT SYS_CODE ");
		sql.add(" FROM   PRG_MST ");
		sql.add(" WHERE  KAI_CODE = ? ", log.getCompany().getCode());
		sql.add(" AND    PRG_CODE = ? ", log.getProgram().getCode());

		String sysCode = Util.avoidNull(DBUtil.selectOne(sql));

		int regulatedNumber = ServerConfig.getSystemRegulatedNumber(sysCode, log.getCompany().getCode());
		if (regulatedNumber <= 0) {
			// �����Ȃ�
			return true;
		}

		List<String> groupCompanyList = ServerConfig.getSystemRegulatedGroupList(log.getCompany().getCode());

		// ���菈��(�w���ЁA�����w�胆�[�U�ȊO)
		if (groupCompanyList == null || groupCompanyList.isEmpty()) {
			sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(*) ");
			sql.add(" FROM   (SELECT KAI_CODE ");
			sql.add("               ,SYS_CODE ");
			sql.add("               ,USR_ID ");
			sql.add("         FROM   PCI_SUB_CHK_CTL chk ");
			sql.add("         WHERE  KAI_CODE = ? ", log.getCompany().getCode());
			sql.add("         AND    SYS_CODE = ? ", sysCode);
			sql.add("         AND    USR_ID <> ? ", log.getUser().getCode());
			sql.add("         GROUP  BY KAI_CODE ");
			sql.add("                  ,SYS_CODE ");
			sql.add("                  ,USR_ID) s ");

		} else {

			sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(*) ");
			sql.add(" FROM   (SELECT SYS_CODE ");
			sql.add("               ,USR_ID ");
			sql.add("         FROM   PCI_SUB_CHK_CTL chk ");
			sql.add("         WHERE  KAI_CODE IN ? ", groupCompanyList);
			sql.add("         AND    SYS_CODE = ? ", sysCode);
			sql.add("         AND    USR_ID <> ? ", log.getUser().getCode());
			sql.add("         GROUP  BY SYS_CODE ");
			sql.add("                  ,USR_ID) s ");
		}

		int result = DBUtil.selectOneInt(sql);
		return result < regulatedNumber;
	}

	/**
	 * �w�肳���v���O�������J��(���j���[�N���b�N�^�C�~���O)
	 * 
	 * @param log ���O���
	 * @throws TException
	 */
	public void openProgram(Log log) throws TException {
		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" INSERT INTO PCI_SUB_CHK_CTL ( ");
		sql.add("    KAI_CODE ");
		sql.add("   ,SYS_CODE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add("   ,USR_NAME ");
		sql.add("   ,PCI_CLIENT_NAME ");
		sql.add("   ,PCI_CHECK_IN_TIME ");
		sql.add(" ) ");
		sql.add(" SELECT ");
		sql.add("    ? ", log.getCompany().getCode());
		sql.add("   ,SYS_CODE ");
		sql.add("   ,? ", log.getProgram().getCode());
		sql.add("   ,? ", log.getUser().getCode());
		sql.add("   ,? ", log.getUser().getName());
		sql.add("   ,? ", log.getIp());
		sql.addYMDHMS("   ,? ", log.getDate());
		sql.add(" FROM   PRG_MST ");
		sql.add(" WHERE  KAI_CODE = ? ", log.getCompany().getCode());
		sql.add(" AND    PRG_CODE = ? ", log.getProgram().getCode());

		DBUtil.execute(sql);
	}

	/**
	 * �w�肳��郆�[�U���J���Ă�v���O���������(�^�u����A�~����)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param programCode �v���O�����R�[�h
	 * @throws TException
	 */
	public void closeProgram(String companyCode, String userCode, String programCode) throws TException {
		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" DELETE FROM PCI_SUB_CHK_CTL ");
		sql.add(" WHERE  KAI_CODE = ? ", companyCode);
		sql.add(" AND    USR_ID = ? ", userCode);
		sql.add(" AND    PRG_ID = ? ", programCode);

		DBUtil.execute(sql);
	}

	/**
	 * �w�肳��郆�[�U���J���Ă�S�ăv���O���������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @throws TException
	 */
	public void closeAllProgram(String companyCode, String userCode) throws TException {
		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" DELETE FROM PCI_SUB_CHK_CTL ");
		sql.add(" WHERE  KAI_CODE = ? ", companyCode);
		sql.add(" AND    USR_ID = ? ", userCode);

		DBUtil.execute(sql);

	}

	/**
	 * OP�L���b�V�����̎擾
	 * 
	 * @param info
	 * @return OP�L���b�V�����
	 * @throws TException
	 */
	public OPLoginInfo getOPCache(OPLoginInfo info) throws TException {

		Date sysDate = getNow();

		for (Entry<OPLoginDataType, OPLoginCondition> entry : info.conditionMap.entrySet()) {
			OPLoginDataType dataType = entry.getKey();
			OPLoginCondition param = entry.getValue();
			OPLoginData bean = info.getData(dataType);

			if (bean == null) {
				bean = new OPLoginData();
				bean.setDataTypeValue(dataType.value);
				info.setData(dataType, bean);
			}

			ServerLogger.debug("current count[" + param.getNowCount() + "]:" + dataType);

			Date lastUpdateDate = param.getLastUpdateDate(); // �ޔ�
			param.setLastUpdateDate(null);
			boolean hasDelete = hasDelete(dataType, param);
			param.setLastUpdateDate(lastUpdateDate); // ����

			if (hasDelete) {
				// �폜���聨�S���Ď擾
				param.setLastUpdateDate(null);
				ServerLogger.debug("has delete data:" + dataType);
			} else {
				// �폜�Ȃ��������̂ݎ擾
				ServerLogger.debug("no delete data:" + dataType);
			}

			List list = getList(dataType, param);

			if (list != null && !list.isEmpty()) {

				ServerLogger.debug("got update data[" + list.size() + "]:" + dataType);
				bean.setLastUpdateDate(sysDate);

				bean.setList(list);

				if (!hasDelete) {

					// ������v�ADEL��NEW�̔��肪�K�v
					List<String> codeList = info.getCodeList(dataType);
					if (codeList != null) {
						for (Object obj : list) {
							String key = OPLoginDataType.getKey(dataType, obj);
							if (!codeList.contains(key)) {
								// �܂܂Ȃ�KEY�����݂���ꍇ��

								// �폜���聨�S���Ď擾
								param.setLastUpdateDate(null);
								list = getList(dataType, param);
								bean.setList(list);

								info.setResultType(dataType, OPLoginResultType.NEED_UPDATE);

								// ���̏�����
								continue;
							}
						}
					}

					// �Œ���X�V�̂�
					info.setResultType(dataType, OPLoginResultType.NEED_MERGE);
				} else {
					// �S���X�V
					info.setResultType(dataType, OPLoginResultType.NEED_UPDATE);
				}

			} else {

				ServerLogger.debug("no update data:" + dataType);
				info.setResultType(dataType, OPLoginResultType.NO_OPERATION);
			}

		}

		// ���X�|���X���M�s�v
		info.codeListMap.clear();

		return info;
	}

	// /**
	// * @param dataType
	// * @param bean
	// * @param beanClazz
	// * @param list
	// * @return �}�[�W����
	// */
	// @SuppressWarnings("unused")
	// protected <T> OPLoginData merge(OPLoginDataType dataType, OPLoginData bean, Class<T> beanClazz, List list) {
	//
	// OPLoginMerge m = new OPLoginMerge<T>();
	// bean = m.merge(dataType, bean, list);
	//
	// return bean;
	// }

	/**
	 * @param dataType
	 * @param param
	 * @return true:�폜����
	 * @throws TException
	 */
	protected boolean hasDelete(OPLoginDataType dataType, OPLoginCondition param) throws TException {
		if (dataType == null || param == null) {
			return false;
		}

		switch (dataType) {
			case OP_CODE_MST: { // 1�FOP�A�C�e���}�X�^
				CodeManager mgr = get(CodeManager.class);
				return mgr.hasDelete((CodeSearchCondition) param);
			}
			case CM_BNKR_TYPE_MST: { // 2�F����}�X�^
				BunkerTypeManager mgr = get(BunkerTypeManager.class);
				return mgr.hasDelete((BunkerTypeSearchCondition) param);
			}
			case CUR_MST: { // 3�F�ʉ݃}�X�^
				CurrencyManager mgr = get(CurrencyManager.class);
				return mgr.hasDelete((CurrencySearchCondition) param);
			}
			case TRI_MST: { // 4�F�����}�X�^
				CustomerManager mgr = get(CustomerManager.class);
				return mgr.hasDelete((CustomerSearchCondition) param);
			}
			case BMN_MST: { // 5�F����}�X�^
				DepartmentManager mgr = get(DepartmentManager.class);
				return mgr.hasDelete((DepartmentSearchCondition) param);
			}
			case EMP_MST: { // 6�F�Ј��}�X�^
				EmployeeManager mgr = get(EmployeeManager.class);
				return mgr.hasDelete((EmployeeSearchCondition) param);
			}
			case CM_VESSEL_MST: { // 7�F�D�}�X�^
				VesselManager mgr = get(VesselManager.class);
				return mgr.hasDelete((VesselSearchCondition) param);
			}
			case CM_PORT_MST: { // 8�F�`�}�X�^
				PortManager mgr = get(PortManager.class);
				return mgr.hasDelete((PortSearchCondition) param);
			}
			case CM_VOYAGE_MST: { // 9�F�q�C�}�X�^
				VoyageManager mgr = get(VoyageManager.class);
				return mgr.hasDelete((VoyageSearchCondition) param);
			}
			case SZEI_MST: { // 10�F����Ń}�X�^
				ConsumptionTaxManager mgr = get(ConsumptionTaxManager.class);
				return mgr.hasDelete((ConsumptionTaxSearchCondition) param);
			}
			case OP_CRG_MST: { // 11�F�J�[�S�}�X�^
				CargoManager mgr = get(CargoManager.class);
				return mgr.hasDelete((CargoSearchCondition) param);
			}
			case OP_ITEM_MST: { // 12�FOP�A�C�e���}�X�^
				OPItemManager mgr = get(OPItemManager.class);
				return mgr.hasDelete((OPItemSearchCondition) param);
			}
			case AP_HOH_MST: { // 13�F�x�����@�}�X�^
				PaymentMethodManager mgr = get(PaymentMethodManager.class);
				return mgr.hasDelete((PaymentMethodSearchCondition) param);
			}
			case AP_CBK_MST: { // 14�F��s�����}�X�^
				BankAccountManager mgr = get(BankAccountManager.class);
				return mgr.hasDelete((BankAccountSearchCondition) param);
			}
			case COUNTRY_MST: { // 15�F���}�X�^
				CountryManager mgr = get(CountryManager.class);
				return mgr.hasDelete((CountrySearchCondition) param);
			}
			default:
				return false;
		}
	}

	/**
	 * �f�[�^���X�g�擾
	 * 
	 * @param dataType
	 * @param param
	 * @return �f�[�^���X�g
	 * @throws TException
	 */
	protected List getList(OPLoginDataType dataType, OPLoginCondition param) throws TException {
		if (dataType == null || param == null) {
			return null;
		}

		switch (dataType) {
			case OP_CODE_MST: { // 1�FOP�A�C�e���}�X�^
				CodeManager mgr = get(CodeManager.class);
				return mgr.get((CodeSearchCondition) param);
			}
			case CM_BNKR_TYPE_MST: { // 2�F����}�X�^
				BunkerTypeManager mgr = get(BunkerTypeManager.class);
				return mgr.get((BunkerTypeSearchCondition) param);
			}
			case CUR_MST: { // 3�F�ʉ݃}�X�^
				CurrencyManager mgr = get(CurrencyManager.class);
				return mgr.get((CurrencySearchCondition) param);
			}
			case TRI_MST: { // 4�F�����}�X�^
				CustomerManager mgr = get(CustomerManager.class);
				return mgr.get((CustomerSearchCondition) param);
			}
			case BMN_MST: { // 5�F����}�X�^
				DepartmentManager mgr = get(DepartmentManager.class);
				return mgr.get((DepartmentSearchCondition) param);
			}
			case EMP_MST: { // 6�F�Ј��}�X�^
				EmployeeManager mgr = get(EmployeeManager.class);
				return mgr.get((EmployeeSearchCondition) param);
			}
			case CM_VESSEL_MST: { // 7�F�D�}�X�^
				VesselManager mgr = get(VesselManager.class);
				return mgr.getVesselForSearch((VesselSearchCondition) param);
			}
			case CM_PORT_MST: { // 8�F�`�}�X�^
				PortManager mgr = get(PortManager.class);
				return mgr.get((PortSearchCondition) param);
			}
			case CM_VOYAGE_MST: { // 9�F�q�C�}�X�^
				VoyageManager mgr = get(VoyageManager.class);
				return mgr.get((VoyageSearchCondition) param);
			}
			case SZEI_MST: { // 10�F����Ń}�X�^
				ConsumptionTaxManager mgr = get(ConsumptionTaxManager.class);
				return mgr.get((ConsumptionTaxSearchCondition) param);
			}
			case OP_CRG_MST: { // 11�F�J�[�S�}�X�^
				CargoManager mgr = get(CargoManager.class);
				return mgr.get((CargoSearchCondition) param);
			}
			case OP_ITEM_MST: { // 12�FOP�A�C�e���}�X�^
				OPItemManager mgr = get(OPItemManager.class);
				return mgr.get((OPItemSearchCondition) param);
			}
			case AP_HOH_MST: { // 13�F�x�����@�}�X�^
				PaymentMethodManager mgr = get(PaymentMethodManager.class);
				return mgr.get((PaymentMethodSearchCondition) param);
			}
			case AP_CBK_MST: { // 14�F��s�����}�X�^
				BankAccountManager mgr = get(BankAccountManager.class);
				return mgr.get((BankAccountSearchCondition) param);
			}
			case COUNTRY_MST: { // 15�F���}�X�^
				CountryManager mgr = get(CountryManager.class);
				return mgr.get((CountrySearchCondition) param);
			}
			default:
				return null;
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
