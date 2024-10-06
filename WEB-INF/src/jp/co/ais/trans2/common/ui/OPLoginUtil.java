package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.cargo.*;
import jp.co.ais.trans2.model.code.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
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
 * �g���I�y���O�C������
 */
public class OPLoginUtil {

	/** �e�탍�O�C���f�[�^�̃}�b�v<�L�[,�f�[�^> */
	protected static Map<OPLoginDataType, OPLoginData> map = null;

	/** �R�[�h�}�X�^���X�g */
	protected static List<OP_CODE_MST> codeMstList = null;

	/** �敪���ƂɐU�蕪�����R�[�h�}�X�^���X�g */
	protected static Map<String, List<OP_CODE_MST>> codeMstMap = null;

	/** ���탊�X�g */
	protected static List<CM_BNKR_TYPE_MST> bunkerTypeList = null;

	/** �R������R�[�h���X�g */
	protected static List<String> bunkerTypeCodeList = null;

	/** �ʉ݃��X�g */
	protected static List<Currency> currencyList = null;

	/** ����惊�X�g */
	protected static List<Customer> customerList = null;

	/** ���僊�X�g */
	protected static List<Department> departmentList = null;

	/** �Ј����X�g */
	protected static List<Employee> employeeList = null;

	/** �D���X�g */
	protected static List<Vessel> vesselList = null;

	/** �`���X�g */
	protected static List<Port> portList = null;

	/** �`���X�g */
	protected static List<Port> lclPortList = null;

	/** �q�C���X�g */
	protected static List<Voyage> voyageList = null;

	/** ����Ń��X�g */
	protected static List<ConsumptionTax> taxList = null;

	/** �J�[�S���X�g */
	protected static List<Cargo> cargoList = null;

	/** OP�A�C�e�����X�g */
	protected static List<OPItem> opItemList = null;

	// /** �x���������X�g */
	// protected static List<PaymentSetting> paySettingList = null;

	/** �x�����@���X�g */
	protected static List<PaymentMethod> payMethodList = null;

	/** ��s�������X�g */
	protected static List<BankAccount> bankAccountList = null;

	/** �����X�g */
	protected static List<Country> countryList = null;

	/** �������N���X */
	protected static TLoginInitialInterface initClazz = null;

	/**
	 * �������N���X��ݒ肷��
	 * 
	 * @param clazz
	 */
	public static void setInitialClass(TLoginInitialInterface clazz) {
		initClazz = clazz;
	}

	/**
	 * �������N���X���擾����
	 * 
	 * @return clazz
	 */
	public static TLoginInitialInterface getInitialClass() {
		return initClazz;
	}

	/**
	 * �������N���X���擾����
	 * 
	 * @return clazz
	 */
	protected static OPLoginInitial getOPInitialClass() {
		if (initClazz instanceof OPLoginInitial) {
			return (OPLoginInitial) initClazz;
		}
		return null;
	}

	/**
	 * ��������(TYPE�w��A�o�b�`�p)<br>
	 * ����
	 * 
	 * @param types
	 */
	public static void init(OPLoginDataType... types) {
		if (types == null || types.length == 0) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.init(types);

	}

	/**
	 * ��������(TYPE�w��A�o�b�`�p)<br>
	 * ASYNC
	 * 
	 * @param types
	 */
	public static void initAsync(OPLoginDataType... types) {
		if (types == null || types.length == 0) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.initAsync(types);
	}

	/**
	 * @param dataType
	 */
	public static void refreshAsync(OPLoginDataType dataType) {
		if (dataType == null) {
			return;
		}
		refreshAsync(dataType.value);
	}

	/**
	 * @param dataTypeValue
	 */
	public static void refreshAsync(int dataTypeValue) {
		OPLoginDataType dataType = OPLoginDataType.get(dataTypeValue);
		if (dataType == null) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.refreshAsync(dataType);

	}

	/**
	 * @param dataType
	 */
	public static void refresh(OPLoginDataType dataType) {
		if (dataType == null) {
			return;
		}
		refresh(dataType.value);
	}

	/**
	 * @param dataTypeValue
	 */
	public static void refresh(int dataTypeValue) {
		OPLoginDataType dataType = OPLoginDataType.get(dataTypeValue);
		if (dataType == null) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.refresh(dataType);

	}

	/**
	 * �������ς݂̋L��
	 * 
	 * @param dataType
	 * @param bean
	 */
	public static void saveCache(OPLoginDataType dataType, OPLoginData bean) {
		if (map == null) {
			map = new LinkedHashMap<OPLoginDataType, OPLoginData>();
		}
		map.put(dataType, bean);
	}

	/**
	 * @param dataType
	 * @return �����Ă���f�[�^
	 */
	public static OPLoginData getCache(OPLoginDataType dataType) {
		if (map == null) {
			map = new LinkedHashMap<OPLoginDataType, OPLoginData>();
		}
		return map.get(dataType);
	}

	/**
	 * �R�[�h�}�X�^���X�g�̎擾
	 * 
	 * @return codeMstList �R�[�h�}�X�^���X�g
	 */
	public static List<OP_CODE_MST> getCodeMstList() {
		return codeMstList;
	}

	/**
	 * �w��敪�̃R�[�h�}�X�^���X�g��Ԃ�
	 * 
	 * @param div
	 * @param codes �R�[�h�w��
	 * @return �w��敪�̃R�[�h�}�X�^���X�g
	 */
	public static List<OP_CODE_MST> getCodeMstList(OPCodeDivision div, String... codes) {
		return getCodeMstList(false, div, codes);
	}

	/**
	 * �w��敪�̃R�[�h�}�X�^���X�g��Ԃ�
	 * 
	 * @param div
	 * @param codes �R�[�h�w��
	 * @return �w��敪�̃R�[�h�}�X�^���X�g
	 */
	public static List<OP_CODE_MST> getCodeMstListLocal(OPCodeDivision div, String... codes) {
		return getCodeMstList(true, div, codes);
	}

	/**
	 * �w��敪�̃R�[�h�}�X�^���X�g��Ԃ�
	 * 
	 * @param isLocal true:���q���[�h�Afalse:�O�q���[�h
	 * @param div
	 * @param codes �R�[�h�w��
	 * @return �w��敪�̃R�[�h�}�X�^���X�g
	 */
	public static List<OP_CODE_MST> getCodeMstList(boolean isLocal, OPCodeDivision div, String... codes) {
		if (codeMstMap == null) {
			return null;
		}

		if (div == null) {
			return null;
		}
		List<OP_CODE_MST> list = codeMstMap.get((isLocal ? 1 : 0) + div.toString());
		if (list == null) {
			return null;
		}

		if (codes == null || codes.length == 0) {
			// �R�[�h���w�聁�S�Ă̂��̂Ńt�B���^�[���s��
			List<OP_CODE_MST> filter = new ArrayList<OP_CODE_MST>();
			for (OP_CODE_MST bean : list) {
				filter.add(bean);
			}
			return filter;
		}

		List<OP_CODE_MST> filter = new ArrayList<OP_CODE_MST>();
		for (OP_CODE_MST bean : list) {
			for (String code : codes) { // �R�[�h�ʎw�肵���ꍇ�ɕ\�����������Ȃ��Ȃ��Q�Ή�
				if (code.equals(bean.getCODE())) {
					filter.add(bean);
				}
			}
		}

		return filter;
	}

	/**
	 * �R�[�h�}�X�^�ݒ��Ԃ�
	 * 
	 * @param div
	 * @param code
	 * @return �R�[�h�}�X�^�ݒ�
	 */
	public static OP_CODE_MST getCodeMst(OPCodeDivision div, String code) {
		return getCodeMst(false, div, code);
	}

	/**
	 * �R�[�h�}�X�^�ݒ��Ԃ�
	 * 
	 * @param div
	 * @param code
	 * @return �R�[�h�}�X�^�ݒ�
	 */
	public static OP_CODE_MST getCodeMstLocal(OPCodeDivision div, String code) {
		return getCodeMst(true, div, code);
	}

	/**
	 * �R�[�h�}�X�^�ݒ��Ԃ�
	 * 
	 * @param isLocal true:���q���[�h�Afalse:�O�q���[�h
	 * @param div
	 * @param code
	 * @return �R�[�h�}�X�^�ݒ�
	 */
	public static OP_CODE_MST getCodeMst(boolean isLocal, OPCodeDivision div, String code) {
		if (codeMstMap == null) {
			return null;
		}

		List<OP_CODE_MST> list = getCodeMstList(isLocal, div);
		if (list == null) {
			return null;
		}

		if (Util.isNullOrEmpty(code)) {
			return null;
		}

		for (OP_CODE_MST bean : list) {
			if (code.equals(bean.getCODE())) {
				return bean;
			}
		}

		return null;
	}

	/**
	 * �R�[�h�}�X�^���X�g�̐ݒ�
	 * 
	 * @param list �R�[�h�}�X�^���X�g
	 */
	public static void setCodeMstList(List<OP_CODE_MST> list) {
		codeMstList = list;

		// �}�b�v������
		if (list == null) {
			codeMstMap = null;
			return;
		}

		codeMstMap = new LinkedHashMap<String, List<OP_CODE_MST>>();
		for (OPCodeDivision div : OPCodeDivision.values()) {
			List<OP_CODE_MST> values1 = new ArrayList<OP_CODE_MST>();
			List<OP_CODE_MST> values2 = new ArrayList<OP_CODE_MST>();
			codeMstMap.put("0" + div.toString(), values1);
			codeMstMap.put("1" + div.toString(), values2);
		}

		for (OP_CODE_MST bean : list) {
			OPCodeDivision div = OPCodeDivision.get(bean.getCODE_DIV());
			if (div == null) {
				continue;
			}

			int local = bean.getLCL_KBN();
			if (local != 1) {
				local = 0;
			}

			List<OP_CODE_MST> values = codeMstMap.get(local + div.toString());
			if (values != null) {
				values.add(bean);
			}
		}
	}

	/**
	 * ���탊�X�g�̎擾
	 * 
	 * @return bunkerTypeList ���탊�X�g
	 */
	public static List<CM_BNKR_TYPE_MST> getBunkerTypeList() {
		return bunkerTypeList;
	}

	/**
	 * �R������R�[�h���X�g��Ԃ�
	 * 
	 * @return �R������R�[�h���X�g
	 */
	public static List<String> getBunkerTypeCodeList() {
		return bunkerTypeCodeList;
	}

	/**
	 * ���탊�X�g�̐ݒ�
	 * 
	 * @param list ���탊�X�g
	 */
	public static void setBunkerTypeList(List<CM_BNKR_TYPE_MST> list) {
		bunkerTypeList = list;

		// ������
		bunkerTypeCodeList = new ArrayList<String>();

		if (bunkerTypeList != null) {
			for (CM_BNKR_TYPE_MST bean : bunkerTypeList) {
				bunkerTypeCodeList.add(bean.getBNKR_TYPE_CODE());
			}
		}
	}

	/**
	 * ���YEnum�̕\������Ԃ�
	 * 
	 * @param e
	 * @return �\����
	 */
	public static String getName(OPEnum e) {
		return getName(false, e);
	}

	/**
	 * ���YEnum�̕\������Ԃ�
	 * 
	 * @param isLocal true:���q���[�h�Afalse:�O�q���[�h
	 * @param e
	 * @return �\����
	 */
	public static String getName(boolean isLocal, OPEnum e) {
		if (e == null) {
			return null;
		}

		OP_CODE_MST bean = getCodeMst(isLocal, e.getCodeDivision(), e.getCode());
		if (bean != null) {
			return bean.getCODE_NAME();
		}

		return null;
	}

	/**
	 * �ʉ݃��X�g�̎擾
	 * 
	 * @return currencyList �ʉ݃��X�g
	 */
	public static List<Currency> getCurrencyList() {
		return currencyList;
	}

	/**
	 * �ʉ݃��X�g�̎擾
	 * 
	 * @param condition
	 * @return currencyList �ʉ݃��X�g
	 */
	public static List<Currency> getCurrencyList(CurrencySearchCondition condition) {
		return getFilterList(currencyList, condition);
	}

	/**
	 * �ʉ݃��X�g�̐ݒ�
	 * 
	 * @param list �ʉ݃��X�g
	 */
	public static void setCurrencyList(List<Currency> list) {
		currencyList = list;
	}

	/**
	 * ����惊�X�g�̎擾
	 * 
	 * @return customerList ����惊�X�g
	 */
	public static List<Customer> getCustomerList() {
		return customerList;
	}

	/**
	 * ����惊�X�g�̎擾
	 * 
	 * @param condition
	 * @return customerList ����惊�X�g
	 */
	public static List<Customer> getCustomerList(CustomerSearchCondition condition) {
		return getFilterList(customerList, condition);
	}

	/**
	 * ����惊�X�g�̐ݒ�
	 * 
	 * @param list ����惊�X�g
	 */
	public static void setCustomerList(List<Customer> list) {
		customerList = list;
	}

	/**
	 * ���僊�X�g�̎擾
	 * 
	 * @return departmentList ���僊�X�g
	 */
	public static List<Department> getDepartmentList() {
		return departmentList;
	}

	/**
	 * ���僊�X�g�̎擾
	 * 
	 * @param condition
	 * @return departmentList ���僊�X�g
	 */
	public static List<Department> getDepartmentList(DepartmentSearchCondition condition) {
		return getFilterList(departmentList, condition);
	}

	/**
	 * ���僊�X�g�̐ݒ�
	 * 
	 * @param list ���僊�X�g
	 */
	public static void setDepartmentList(List<Department> list) {
		departmentList = list;
	}

	/**
	 * �Ј����X�g�̎擾
	 * 
	 * @return employeeList �Ј����X�g
	 */
	public static List<Employee> getEmployeeList() {
		return employeeList;
	}

	/**
	 * �Ј����X�g�̎擾
	 * 
	 * @param condition
	 * @return employeeList �Ј����X�g
	 */
	public static List<Employee> getEmployeeList(EmployeeSearchCondition condition) {
		return getFilterList(employeeList, condition);
	}

	/**
	 * �Ј����X�g�̐ݒ�
	 * 
	 * @param list �Ј����X�g
	 */
	public static void setEmployeeList(List<Employee> list) {
		employeeList = list;
	}

	/**
	 * �D���X�g�̎擾<br>
	 * SUSPENDDED�D�͑ΏۊO
	 * 
	 * @return vesselList �D���X�g
	 */
	public static List<Vessel> getVesselList() {
		VesselSearchCondition condition = new VesselSearchCondition();
		condition.setIncludeSuspended(false);
		condition.setIncludeRelet(true);
		return getFilterList(vesselList, condition);
	}

	/**
	 * �D���X�g�̎擾
	 * 
	 * @param condition
	 * @return vesselList �D���X�g
	 */
	public static List<Vessel> getVesselList(VesselSearchCondition condition) {
		return getFilterList(vesselList, condition);
	}

	/**
	 * �D���X�g�̐ݒ�
	 * 
	 * @param list �D���X�g
	 */
	public static void setVesselList(List<Vessel> list) {
		vesselList = list;

		// �D�⑫���
		if (vesselList != null) {
			for (Vessel vsl : vesselList) {
				OP_CODE_MST mst = getCodeMst(OPCodeDivision.VSL_TYPE, vsl.getSHIP_TYPE());
				if (mst != null) {
					vsl.setSHIP_TYPE_NAME(mst.getCODE_NAME());
				} else {
					vsl.setSHIP_TYPE_NAME("");
				}
			}
		}
	}

	/**
	 * �`���X�g�̎擾
	 * 
	 * @return portList �`���X�g
	 */
	public static List<Port> getPortList() {
		return portList;
	}

	/**
	 * �`���X�g�̎擾
	 * 
	 * @param isLocal true:���q
	 * @return portList �`���X�g
	 */
	public static List<Port> getPortList(boolean isLocal) {
		if (isLocal) {
			return lclPortList;
		} else {
			return portList;
		}
	}

	/**
	 * �`���X�g�̎擾
	 * 
	 * @param condition
	 * @return portList �`���X�g
	 */
	public static List<Port> getPortList(PortSearchCondition condition) {

		List<Port> oriList = condition.isLocal() ? lclPortList : portList;
		List<Port> list = getFilterList(oriList, condition);

		if (list == null) {
			return null;
		}

		List filter = new ArrayList();

		for (Port bean : list) {

			// ���R�[�h
			if (!Util.isNullOrEmpty(condition.getCOU_CODE())) {
				if (!Util.equals(condition.getCOU_CODE(), bean.getCOU_CODE())) {
					continue;
				}
			}

			// (S)ECA�敪
			if (condition.getS_ECA_FLG() != -1) {
				if (condition.getS_ECA_FLG() != bean.getS_ECA_FLG()) {
					continue;
				}
			}

			filter.add(bean);
		}

		return filter;
	}

	/**
	 * �`���X�g�̐ݒ�
	 * 
	 * @param list �`���X�g
	 */
	public static void setPortList(List<Port> list) {
		portList = list;

		lclPortList = new ArrayList<Port>();
		for (Port p : portList) {
			Port port = p.clone();
			port.setName(Util.nvl(port.getName_N(), port.getName())); // ���q�p
			lclPortList.add(port);
		}
	}

	/**
	 * �q�C���X�g�̎擾
	 * 
	 * @return voyageList �q�C���X�g
	 */
	public static List<Voyage> getVoyageList() {
		return voyageList;
	}

	/**
	 * �q�C���X�g�̎擾
	 * 
	 * @param condition
	 * @return voyageList �q�C���X�g
	 */
	public static List<Voyage> getVoyageList(VoyageSearchCondition condition) {
		return getFilterList(voyageList, condition);
	}

	/**
	 * �q�C���X�g�̐ݒ�
	 * 
	 * @param list �q�C���X�g
	 */
	public static void setVoyageList(List<Voyage> list) {
		voyageList = list;
	}

	/**
	 * ����Ń��X�g�̎擾
	 * 
	 * @return portList ����Ń��X�g
	 */
	public static List<ConsumptionTax> getConsumptionTaxList() {
		return taxList;
	}

	/**
	 * ����Ń��X�g�̎擾
	 * 
	 * @param condition
	 * @return portList ����Ń��X�g
	 */
	public static List<ConsumptionTax> getConsumptionTaxList(ConsumptionTaxSearchCondition condition) {
		return getFilterList(taxList, condition);
	}

	/**
	 * ����Ń��X�g�̐ݒ�
	 * 
	 * @param list ����Ń��X�g
	 */
	public static void setConsumptionTaxList(List<ConsumptionTax> list) {
		taxList = list;
	}

	/**
	 * �J�[�S���X�g�̎擾
	 * 
	 * @return cargoList �J�[�S���X�g
	 */
	public static List<Cargo> getCargoList() {
		return cargoList;
	}

	/**
	 * �J�[�S���X�g�̎擾
	 * 
	 * @param condition
	 * @return cargoList �J�[�S���X�g
	 */
	public static List<Cargo> getCargoList(CargoSearchCondition condition) {
		return getFilterList(cargoList, condition);
	}

	/**
	 * �J�[�S���X�g�̐ݒ�
	 * 
	 * @param list �J�[�S���X�g
	 */
	public static void setCargoList(List<Cargo> list) {
		cargoList = list;
	}

	/**
	 * OP�A�C�e�����X�g�̎擾
	 * 
	 * @return opItemList OP�A�C�e�����X�g
	 */
	public static List<OPItem> getOPItemList() {
		return opItemList;
	}

	/**
	 * OP�A�C�e���̎擾
	 * 
	 * @param itemCode
	 * @return opItem OP�A�C�e��
	 */
	public static OPItem getOPItem(String itemCode) {
		if (opItemList == null || Util.isNullOrEmpty(itemCode)) {
			return null;
		}

		for (OPItem item : opItemList) {
			if (item.getITEM_CODE().equals(itemCode)) {
				return item;
			}
		}

		return null;
	}

	/**
	 * OP�A�C�e�����X�g�̎擾
	 * 
	 * @param condition
	 * @return opItemList OP�A�C�e�����X�g
	 */
	public static List<OPItem> getOPItemList(OPItemSearchCondition condition) {
		List<OPItem> list = getFilterList(opItemList, condition);

		if (list == null) {
			return null;
		}

		List filter = new ArrayList();

		for (OPItem bean : list) {

			// �_��^�C�v
			if (condition.getContractType() != null) {
				if (condition.getContractType().value != bean.getCTRT_TYPE()) {
					continue;
				}
			}

			// SA�敪
			if (condition.getSa() != null) {
				if (condition.getSa().value != bean.getSA_KBN()) {
					continue;
				}
			}

			// DC�敪
			if (condition.getDc() != null) {
				if (condition.getDc().value != bean.getDC_KBN()) {
					continue;
				}
			}

			// ADDCOM�敪
			if (condition.getADCOM_KBN() != -1) {
				if (condition.getADCOM_KBN() != bean.getADCOM_KBN()) {
					continue;
				}
			}

			// BRKRG�敪
			if (condition.getBRKR_KBN() != -1) {
				if (condition.getBRKR_KBN() != bean.getBRKR_KBN()) {
					continue;
				}
			}

			// ����敪
			if (condition.getItemControlDivision() != null) {
				if (!condition.getItemControlDivision().value.equals(bean.getITEM_CTL_KBN())) {
					continue;
				}
			}

			// SUB �敪
			if (condition.getItemSubDivision() != null) {
				if (!condition.getItemSubDivision().value.equals(bean.getITEM_SUB_KBN())) {
					continue;
				}
			}

			// SUB �敪����
			if (condition.getItemSubDivisions() != null) {
				OPItemSubDivision sub = OPItemSubDivision.get(bean.getITEM_SUB_KBN());
				if (!condition.getItemSubDivisions().contains(sub)) {
					continue;
				}
			}

			// �A�C�e��SUB�敪(BUNKER_TYPE)
			if (condition.getBrkrTypeCode() != null) {
				if (!condition.getBrkrTypeCode().equals(bean.getITEM_SUB_KBN())) {
					continue;
				}
			}

			if (ClientConfig.isFlagOn("trans.op.use.item.ownr.ship")) {
				// OWNR_SHIP_CODE
				if (condition.getOwnrShipCode() != null) {
					if (!condition.getOwnrShipCode().equals(bean.getOWNR_SHIP_CODE())) {
						continue;
					}
				}
			}

			filter.add(bean);
		}

		return filter;
	}

	/**
	 * OP�A�C�e�����X�g�̐ݒ�
	 * 
	 * @param list OP�A�C�e�����X�g
	 */
	public static void setOPItemList(List<OPItem> list) {
		opItemList = list;
	}

	// /**
	// * �x���������X�g�̎擾
	// *
	// * @return paySettingList �x���������X�g
	// */
	// public static List<PaymentSetting> getPaySettingList() {
	// return paySettingList;
	// }
	//
	// /**
	// * �x���������X�g�̎擾
	// *
	// * @param condition
	// * @return paySettingList �x���������X�g
	// */
	// public static List<PaymentSetting> getPaySettingList(PaymentSettingSearchCondition condition) {
	// return getFilterList(paySettingList, condition);
	// }
	//
	// /**
	// * �x���������X�g�̐ݒ�
	// *
	// * @param list �x���������X�g
	// */
	// public static void setPaySettingList(List<PaymentSetting> list) {
	// paySettingList = list;
	// }

	/**
	 * �x�����@���X�g�̎擾
	 * 
	 * @return payMethodList �x�����@���X�g
	 */
	public static List<PaymentMethod> getPayMethodList() {
		return payMethodList;
	}

	/**
	 * �x�����@���X�g�̎擾
	 * 
	 * @param condition
	 * @return payMethodList �x�����@���X�g
	 */
	public static List<PaymentMethod> getPayMethodList(PaymentMethodSearchCondition condition) {
		return getFilterList(payMethodList, condition);
	}

	/**
	 * �x�����@���X�g�̐ݒ�
	 * 
	 * @param list �x�����@���X�g
	 */
	public static void setPayMethodList(List<PaymentMethod> list) {
		payMethodList = list;
	}

	/**
	 * ��s�������X�g�̎擾
	 * 
	 * @return bankAccountList ��s�������X�g
	 */
	public static List<BankAccount> getBankAccountList() {
		return bankAccountList;
	}

	/**
	 * ��s�������X�g�̎擾
	 * 
	 * @param condition
	 * @return bankAccountList ��s�������X�g
	 */
	public static List<BankAccount> getBankAccountList(BankAccountSearchCondition condition) {
		return getFilterList(bankAccountList, condition);
	}

	/**
	 * ��s�������X�g�̐ݒ�
	 * 
	 * @param list ��s�������X�g
	 */
	public static void setBankAccountList(List<BankAccount> list) {
		bankAccountList = list;
	}

	/**
	 * �����X�g�̎擾
	 * 
	 * @return countryList �����X�g
	 */
	public static List<Country> getCountryList() {
		return countryList;
	}

	/**
	 * �����X�g�̎擾
	 * 
	 * @param condition
	 * @return countryList �����X�g
	 */
	public static List<Country> getCountryList(CountrySearchCondition condition) {
		return getFilterList(countryList, condition);
	}

	/**
	 * �����X�g�̐ݒ�
	 * 
	 * @param list �����X�g
	 */
	public static void setCountryList(List<Country> list) {
		countryList = list;
	}

	/**
	 * �t�B���^�[���X�g�̎擾
	 * 
	 * @param list
	 * @param condition
	 * @return list �t�B���^�[���X�g
	 */
	public static List getFilterList(List<? extends FilterableEntity> list, FilterableCondition condition) {
		if (list == null) {
			return null;
		}

		List filter = new ArrayList();

		for (FilterableEntity bean : list) {
			// ��ЃR�[�h

			// �R�[�h
			if (condition.getCode() != null) {
				if (!isEquals(bean.getCode(), condition.getCode())) {
					continue;
				}
			}

			// �R�[�h�O����v
			if (condition.getCodeLike() != null) {
				if (!isStartsWith(bean.getCode(), condition.getCodeLike())) {
					continue;
				}
			}

			// ����
			if (condition.getNamesLike() != null) {
				if (!isContains(bean.getNames(), condition.getNamesLike())) {
					continue;
				}
			}

			// ��������
			if (condition.getNamekLike() != null) {
				if (!isContains(bean.getNamek(), condition.getNamekLike())) {
					continue;
				}
			}

			// �J�n�R�[�h
			if (condition.getCodeFrom() != null) {
				if (isSmaller(bean.getCode(), condition.getCodeFrom())) {
					continue;
				}
			}

			// �I���R�[�h
			if (condition.getCodeTo() != null) {
				if (isBigger(bean.getCode(), condition.getCodeTo())) {
					continue;
				}
			}

			// �R�[�h�z��
			if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
				if (!isContains(condition.getCodeList(), bean.getCode())) {
					continue;
				}
			}

			// �L������
			if (condition.getValidTerm() != null) {
				if (!isBetween(condition.getValidTerm(), bean.getDateFrom(), bean.getDateTo())) {
					continue;
				}
			}

			// �J�X�^�}�C�Y�t�B���^�[
			if (!isEquals(bean, condition)) {
				continue;
			}

			filter.add(bean);
		}

		if (filter.isEmpty()) {
			return null;
		}

		return filter;
	}

	/**
	 * @param bean
	 * @param condition
	 * @return true:������
	 */
	public static boolean isEquals(FilterableEntity bean, FilterableCondition condition) {

		// �ʑΉ�
		if (bean instanceof ConsumptionTax && condition instanceof ConsumptionTaxSearchCondition) {
			// ����ł̏ꍇ�A�ŋ敪���t�B���^�[����

			ConsumptionTax tax = (ConsumptionTax) bean;
			ConsumptionTaxSearchCondition param = (ConsumptionTaxSearchCondition) condition;

			if (TaxType.SALES != tax.getTaxType() && param.isHasSales() // ����
				|| TaxType.PURCHAESE != tax.getTaxType() && param.isHasPurcharse()) // �d��
			{
				return false;
			}
		} else if (bean instanceof Customer && condition instanceof CustomerSearchCondition) {
			// �����̓t���O�ɂ��ڂ���������

			Customer customer = (Customer) bean;
			CustomerSearchCondition param = (CustomerSearchCondition) condition;

			if (CustomerType.BOTH == param.getType()) {
				if (!customer.isClient() && !customer.isPurchase()) {
					return false;
				}
			} else if (CustomerType.CUSTOMER == param.getType()) {
				if (!customer.isClient()) {
					return false;
				}
			} else if (CustomerType.VENDOR == param.getType()) {
				if (!customer.isPurchase()) {
					return false;
				}
			}

			// �����敪
			if (param.isCharterer() != null && param.isCharterer() != customer.isCharterer()) {
				return false;
			}
			if (param.isOwner() != null && param.isOwner() != customer.isOwner()) {
				return false;
			}
			if (param.isPortAgent() != null && param.isPortAgent() != customer.isPortAgent()) {
				return false;
			}
			if (param.isSupplier() != null && param.isSupplier() != customer.isSupplier()) {
				return false;
			}
			if (param.isBroker() != null && param.isBroker() != customer.isBroker()) {
				return false;
			}
			if (param.isOther() != null && param.isOther() != customer.isOther()) {
				return false;
			}
			if (param.isShipper() != null && param.isShipper() != customer.isShipper()) {
				return false;
			}
			if (param.isConsignee() != null && param.isConsignee() != customer.isConsignee()) {
				return false;
			}
			if (param.isNotifyParty() != null && param.isNotifyParty() != customer.isNotifyParty()) {
				return false;
			}
			if (param.isFowarder() != null && param.isFowarder() != customer.isFowarder()) {
				return false;
			}
			if (param.isBunkerTrader() != null && param.isBunkerTrader() != customer.isBunkerTrader()) {
				return false;
			}
			if (param.isBunkerSupplier() != null && param.isBunkerSupplier() != customer.isBunkerSupplier()) {
				return false;
			}

		} else if (bean instanceof PaymentSetting && condition instanceof PaymentSettingSearchCondition) {
			// �x�������̏ꍇ
			PaymentSetting ps = (PaymentSetting) bean;
			PaymentSettingSearchCondition param = (PaymentSettingSearchCondition) condition;

			// �����R�[�h����������ꍇ�́A�Y�����Ȃ��f�[�^�͏��O
			if (!Util.isNullOrEmpty(param.getCustomerCode())) {
				if (ps.getCustomer() == null || !Util.equals(ps.getCustomer().getCode(), param.getCustomerCode())) {
					return false;
				}
			}

		} else if (bean instanceof PaymentMethod && condition instanceof PaymentMethodSearchCondition) {
			// �x�����@�̏ꍇ
			PaymentMethod paymentMethod = (PaymentMethod) bean;
			PaymentMethodSearchCondition param = (PaymentMethodSearchCondition) condition;

			// 0:�Ј��x��
			if ((!paymentMethod.isUseEmployeePayment() && param.isUseEmployeePayment())
				|| (paymentMethod.isUseEmployeePayment() && !param.isUseEmployeePayment())) {
				return false;
			}

			// 1:�ЊO�x��
			if ((!paymentMethod.isUseExPayment() && param.isUseExPayment())
				|| (paymentMethod.isUseExPayment() && !param.isUseExPayment())) {
				return false;
			}
		} else if (bean instanceof BankAccount && condition instanceof BankAccountSearchCondition) {
			// ��s�����̏ꍇ
			BankAccount bankAccount = (BankAccount) bean;
			BankAccountSearchCondition param = (BankAccountSearchCondition) condition;

			// �Ј��e�a�敪
			if ((!bankAccount.isUseEmployeePayment() && param.isUseEmployeePayment())
				|| (bankAccount.isUseEmployeePayment() && !param.isUseEmployeePayment())) {
				return false;
			}

			// �ЊO�e�a�敪
			if ((!bankAccount.isUseExPayment() && param.isUseExPayment())
				|| (bankAccount.isUseExPayment() && !param.isUseExPayment())) {
				return false;
			}
		} else if (bean instanceof Vessel && condition instanceof VesselSearchCondition) {

			Vessel vsl = (Vessel) bean;
			VesselSearchCondition param = (VesselSearchCondition) condition;

			// SUSPENDDED
			if (BooleanUtil.toBoolean(vsl.getSUSPENDED_FLG()) && !param.isIncludeSuspended()) {
				// �D��SUSPENDDED�������͊܂܂Ȃ��ꍇ�A�ΏۊO
				return false;
			}

			// RELET
			if (BooleanUtil.toBoolean(vsl.getRELET_FLG()) && !param.isIncludeRelet()) {
				// �D��RELET�������͊܂܂Ȃ��ꍇ�A�ΏۊO
				return false;
			}
		} else if (bean instanceof Cargo && condition instanceof CargoSearchCondition) {
			// �ݕ�
			Cargo crg = (Cargo) bean;
			CargoSearchCondition param = (CargoSearchCondition) condition;
			if (!Util.isNullOrEmpty(param.getCategory())) {
				if (!isEquals(crg.getCATEGORY(), param.getCategory())) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * @param a
	 * @param b
	 * @return true: a == b
	 */
	public static boolean isEquals(String a, String b) {
		return Util.avoidNullNT(a).equals(Util.avoidNullNT(b));
	}

	/**
	 * @param a
	 * @param str
	 * @return true: a start with str
	 */
	public static boolean isStartsWith(String a, String str) {
		return Util.avoidNullNT(a).startsWith(Util.avoidNullNT(str));
	}

	/**
	 * @param a
	 * @param b
	 * @return true: a < b
	 */
	public static boolean isSmaller(String a, String b) {
		return Util.avoidNullNT(a).compareTo(Util.avoidNullNT(b)) < 0;
	}

	/**
	 * @param a
	 * @param b
	 * @return true: a > b
	 */
	public static boolean isBigger(String a, String b) {
		return Util.avoidNullNT(a).compareTo(Util.avoidNullNT(b)) > 0;
	}

	/**
	 * @param a
	 * @param str
	 * @return true: a contains str
	 */
	public static boolean isContains(String a, String str) {
		return Util.avoidNullNT(a).contains(Util.avoidNullNT(str));
	}

	/**
	 * @param list
	 * @param str
	 * @return true: list contains str
	 */
	public static boolean isContains(List<String> list, String str) {
		return list.contains(Util.avoidNullNT(str));
	}

	/**
	 * @param a
	 * @param from
	 * @param to
	 * @return a between from and to
	 */
	public static boolean isBetween(Date a, Date from, Date to) {
		if (a == null) {
			return false;
		}

		if (from != null) {
			if (!Util.isSmallerThenByYMDHMS(from, a)) {
				// not from <= a
				return false;
			}
		}

		if (to != null) {
			if (!Util.isSmallerThenByYMDHMS(a, to)) {
				// not a <= to
				return false;
			}
		}

		return true;
	}
}
