package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �`�[����
 */
public class SlipCondition extends TransferBase implements Cloneable {

	/** �\�[�g�F��ЃR�[�h */
	public static final String SORT_COMPANY_CODE = "KAI_CODE";

	/** �\�[�g�F�`�[���t */
	public static final String SORT_SLIP_DATE = "SWK_DEN_DATE";

	/** �\�[�g�F�`�[�ԍ� */
	public static final String SORT_SLIP_NO = "SWK_DEN_NO";

	/** �\�[�g�F�`�[�X�V�� */
	public static final String SORT_SLIP_UPDATE_COUNT = "SWK_UPD_CNT";

	/** �\�[�g�F�s�ԍ� */
	public static final String SORT_LINE_NO = "SWK_GYO_NO";

	/** �\�[�g�F����R�[�h */
	public static final String SORT_DEPARTMENT_CODE = "SWK_DEP_CODE";

	/** �\�[�g�F���嗪�� */
	public static final String SORT_DEPARTMENT_NAME_S = "SWK_DEP_NAME_S";

	/** �\�[�g�F�����R�[�h */
	public static final String SORT_CUSTOMER_CODE = "SWK_TRI_CODE";

	/** �\�[�g�F����旪�� */
	public static final String SORT_CUSTOMER_NAME_S = "SWK_TRI_NAME_S";

	/** �\�[�g�F�⏕�Ȗڗ��� */
	public static final String SORT_SUB_ITEM_NAME_S = "SWK_HKM_NAME_S";

	/** �\�[�g�F�⏕�ȖڃR�[�h */
	public static final String SORT_SUB_ITEM_CODE = "SWK_HKM_CODE";

	/** �\�[�g�F�Ǘ�1���� */
	public static final String SORT_MANEGEMENT1_NAME_S = "SWK_KNR_NAME_S_1";

	/** �\�[�g�F�Ǘ�1�R�[�h */
	public static final String SORT_MANEGEMENT1_CODE = "SWK_KNR_CODE_1";

	/** �\�[�g�F�Ǘ�2���� */
	public static final String SORT_MANEGEMENT2_NAME_S = "SWK_KNR_NAME_S_2";

	/** �\�[�g�F�Ǘ�2�R�[�h */
	public static final String SORT_MANEGEMENT2_CODE = "SWK_KNR_CODE_2";

	/** �\�[�g�F�Ǘ�3���� */
	public static final String SORT_MANEGEMENT3_NAME_S = "SWK_KNR_NAME_S_3";

	/** �\�[�g�F�Ǘ�3�R�[�h */
	public static final String SORT_MANEGEMENT3_CODE = "SWK_KNR_CODE_3";

	/** �\�[�g�F�Ǘ�4���� */
	public static final String SORT_MANEGEMENT4_NAME_S = "SWK_KNR_NAME_S_4";

	/** �\�[�g�F�Ǘ�4�R�[�h */
	public static final String SORT_MANEGEMENT4_CODE = "SWK_KNR_CODE_4";

	/** �\�[�g�F�Ǘ�5���� */
	public static final String SORT_MANEGEMENT5_NAME_S = "SWK_KNR_NAME_S_5";

	/** �\�[�g�F�Ǘ�5�R�[�h */
	public static final String SORT_MANEGEMENT5_CODE = "SWK_KNR_CODE_5";

	/** �\�[�g�F�Ǘ�6���� */
	public static final String SORT_MANEGEMENT6_NAME_S = "SWK_KNR_NAME_S_6";

	/** �\�[�g�F�Ǘ�6�R�[�h */
	public static final String SORT_MANEGEMENT6_CODE = "SWK_KNR_CODE_6";

	/** �\�[�g�F���v���ׂP */
	public static final String SORT_HM_1 = "SWK_HM_1";

	/** �\�[�g�F�ʉ݃R�[�h */
	public static final String SORT_CUR_CODE = "SWK_CUR_CODE";

	/** �\�[�g�FDC�敪 */
	public static final String SORT_DC_KBN = "SWK_DC_KBN";

	/** �\�[�g�F���[�g */
	public static final String SORT_RATE = "SWK_CUR_RATE";

	/** �\�[�g�F���͋��z */
	public static final String SORT_INPUT_AMOUNT = "SWK_IN_KIN";

	/** �\�[�g�F�ؕ��M�݋��z */
	public static final String SORT_DEBIT_AMOUNT = "CASE WHEN SWK_DC_KBN = 0 THEN SWK_KIN ELSE 0 END";

	/** �\�[�g�F�ݕ��M�݋��z */
	public static final String SORT_CREDIT_AMOUNT = "CASE WHEN SWK_DC_KBN = 1 THEN SWK_KIN ELSE 0 END";

	/** �\�[�g�F�˗�����R�[�h */
	public static final String SORT_INPUT_DEPARTMENT_CODE = "SWK_IRAI_DEP_CODE";

	/** �\�[�g�F�˗��҃R�[�h */
	public static final String SORT_INPUT_EMPLOYEE_CODE = "SWK_IRAI_EMP_CODE";

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ��� */
	protected Company company;

	/** �`�[�ԍ� */
	protected String slipNo;

	/** BOOK No.(1:�ʏ�d�� 2:�@�\�ʉݎd��) */
	protected int bookNo = 0;

	/** IFRS�����敪(0:���� 1:�����̂� 2:IFRS�̂�) */
	protected List<Integer> adjDivisionList = new LinkedList<Integer>();

	/** �r���t���O(�O�F�ʏ� �P�F�X�V��) */
	protected int lockState = -1;

	/** ��Њԕt�֓`�[�敪(0:�ʏ� 1:�t�֐�`�[) */
	protected int groupAccountDivision = -1;

	/** �`�[�ԍ�(����) */
	protected List<String> slipNoList = new LinkedList<String>();

	/** �`�[�ԍ�(���C�N) */
	protected String slipNoLike;

	/** �`�[�ԍ��i�J�n�j */
	protected String slipNoFrom;

	/** �`�[�ԍ��i�I���j */
	protected String slipNoTo;

	/** �f�[�^�敪(����) */
	protected List<String> dataKindList = new LinkedList<String>();

	/** �`�[���(����) */
	protected List<String> slipTypeList = new LinkedList<String>();

	/** �X�V�敪(����) */
	protected List<Integer> slipStateList = new LinkedList<Integer>();

	/** �`�[���t */
	protected Date slipDate;

	/** �`�[���tFROM */
	protected Date slipDateFrom;

	/** �`�[���tTO */
	protected Date slipDateTo;

	/** �˗���FROM */
	protected Date requestDateFrom;

	/** �˗���TO */
	protected Date requestDateTo;

	/** �˗�����R�[�h */
	protected String requestDeptCode;

	/** �˗��҃R�[�h */
	protected String requestEmpCode;

	/** �x����FROM */
	protected Date paymentDayFrom;

	/** �x����TO */
	protected Date paymentDayTo;

	/** ������FROM */
	protected Date receiveDayFrom;

	/** ������TO */
	protected Date receiveDayTo;

	/** �o�[��FROM */
	protected Date cashDayFrom;

	/** �o�[��TO */
	protected Date cashDayTo;

	/** �Ј�(���C�N) */
	protected String employeeNamesLike;

	/** �E�v�R�[�h */
	protected String remarkCode;

	/** �E�v(���C�N) */
	protected String remarksLike;

	/** �ȖڃR�[�h */
	protected String itemCode;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode;

	/** ����ȖڃR�[�h */
	protected String detailItemCode;

	/** �v�㕔��R�[�h */
	protected String departmentCode = null;

	/** �v�㕔��R�[�h(���C�N) */
	protected String departmentCodeLike = null;

	/** �v�㕔�嗪��(���C�N) */
	protected String departmentNamesLike = null;

	/** �ʉ݃R�[�h */
	protected String currencyCode;

	/** �ʉ݃R�[�h���X�g */
	protected List<String> currencyCodeList = new LinkedList<String>();

	/** �����R�[�h */
	protected String custmorCode;

	/** �Ǘ�1�R�[�h */
	protected String manegement1Code;

	/** �Ǘ�2�R�[�h */
	protected String manegement2Code;

	/** �Ǘ�3�R�[�h */
	protected String manegement3Code;

	/** �Ǘ�4�R�[�h */
	protected String manegement4Code;

	/** �Ǘ�5�R�[�h */
	protected String manegement5Code;

	/** �Ǘ�6�R�[�h */
	protected String manegement6Code;

	/** ����旪�̃��C�N */
	protected String custmorNamesLike;

	/** �����ԍ� */
	protected String eraseNo;

	/** �����ԍ����C�N */
	protected String eraseNoLike;

	/** ������FROM */
	protected Date eraseDayFrom;

	/** ������TO */
	protected Date eraseDayTo;

	/** ���E�`�[�ԍ����C�N */
	protected String offsetSlipNoLike;

	/** �v���O����ID */
	protected String programCode;

	/** ���[�U�[ID */
	protected String userCode;

	/** �\�[�g�� */
	protected List<String> order = new LinkedList<String>();

	/** �����d��敪 */
	protected int autoDivision = -1;

	/** �d��C���^�[�t�F�[�X�敪 */
	protected int ifDivision = -1;

	/** NULL�̈˗��]�ƈ��R�[�h���܂߂邩�ǂ��� */
	protected boolean requestEmpCodeIncledNull = false;

	/** NULL�̈˗�����R�[�h���܂߂邩�ǂ��� */
	protected boolean requestDeptCodeIncledNull = false;

	/** �W�v�����Ō������邩�ǂ��� */
	protected boolean searchSumCustomer = false;

	/** ���V�X�A�g�`�[���܂߂� */
	protected boolean includeOtherSystem = false;

	/** �o�^��FROM */
	protected Date entryDateFrom;

	/** �o�^��TO */
	protected Date entryDateTo;

	/** �X�V��FROM */
	protected Date updateDateFrom;

	/** �T�Z����t���O */
	protected boolean estimateCancelFlg = false;

	/** �ő�w�b�_�[�擾���� */
	protected int maxHeaderCount = -1;

	/** ���[�N�t���[���F�p�̌������ǂ��� */
	protected boolean searchWorkFlow = false;

	/** ���[�N�t���[���F ���F�O���[�v */
	protected List<String> aprvRoleGroupList;

	/** ���[�N�t���[���F ���F���[�� */
	protected List<String> aprvRoleList;

	/**
	 * �N���[��
	 */
	@Override
	public SlipCondition clone() {
		try {
			return (SlipCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * LIKE���ϊ����������ɃR���o�[�g
	 * 
	 * @return �����N���X
	 */
	public SlipCondition toSQL() {
		SlipCondition clone = this.clone();
		clone.setSlipNoLike(SQLUtil.getLikeStatement(slipNoLike, SQLUtil.CHAR_FRONT));
		clone.setEraseNoLike(SQLUtil.getLikeStatement(eraseNoLike, SQLUtil.CHAR_FRONT));
		clone.setOffsetSlipNoLike(SQLUtil.getLikeStatement(offsetSlipNoLike, SQLUtil.CHAR_FRONT));
		clone.setRemarksLike(SQLUtil.getLikeStatement(remarksLike, SQLUtil.NCHAR_AMBI));
		clone.setDepartmentCodeLike(SQLUtil.getLikeStatement(departmentCodeLike, SQLUtil.CHAR_FRONT));
		clone.setDepartmentNamesLike(SQLUtil.getLikeStatement(departmentNamesLike, SQLUtil.NCHAR_AMBI));

		return clone;
	}

	/**
	 * �`�[�ԍ�(����)
	 * 
	 * @return �`�[�ԍ�(����)
	 */
	public String[] getSlipNoList() {
		if (slipNoList.isEmpty()) {
			return null;
		}

		return slipNoList.toArray(new String[slipNoList.size()]);
	}

	/**
	 * �`�[�ԍ�(����)
	 * 
	 * @param slipNoList �`�[�ԍ�(����)
	 */
	public void setSlipNoList(String[] slipNoList) {
		addSlipNo(slipNoList);
	}

	/**
	 * �`�[�ԍ�(����)
	 * 
	 * @param slipno �`�[�ԍ�
	 */
	public void addSlipNo(String... slipno) {
		for (String no : slipno) {
			this.slipNoList.add(no);
		}
	}

	/**
	 * �`�[�ԍ�(���C�N)
	 * 
	 * @return �`�[�ԍ�(���C�N)
	 */
	public String getSlipNoLike() {
		return slipNoLike;
	}

	/**
	 * �`�[�ԍ�(���C�N)
	 * 
	 * @param likeSlipNo �`�[�ԍ�(���C�N)
	 */
	public void setSlipNoLike(String likeSlipNo) {
		this.slipNoLike = likeSlipNo;
	}

	/**
	 * �f�[�^�敪(����)
	 * 
	 * @return �`�[�ԍ�(����)
	 */
	public String[] getDataKindList() {
		if (dataKindList.isEmpty()) {
			return null;
		}

		return dataKindList.toArray(new String[dataKindList.size()]);
	}

	/**
	 * �f�[�^�敪(����)
	 * 
	 * @param dataKindList �f�[�^�敪(����)
	 */
	public void setDataKindList(String[] dataKindList) {
		addDataKind(dataKindList);
	}

	/**
	 * �f�[�^�敪(����)
	 * 
	 * @param dataKind �f�[�^�敪(����)
	 */
	public void addDataKind(String... dataKind) {
		for (String kind : dataKind) {
			this.dataKindList.add(kind);
		}
	}

	/**
	 * �`�[���(����)
	 * 
	 * @return �`�[���(����)
	 */
	public String[] getSlipTypeList() {
		if (slipTypeList.isEmpty()) {
			return null;
		}

		return slipTypeList.toArray(new String[slipTypeList.size()]);
	}

	/**
	 * �`�[���(����)
	 * 
	 * @param slipTypeList �`�[���(����)
	 */
	public void setSlipTypeList(String[] slipTypeList) {
		addSlipType(slipTypeList);
	}

	/**
	 * �`�[���(����)
	 * 
	 * @param slipTypeList �`�[���(����)
	 */
	public void setSlipTypeList(List<String> slipTypeList) {
		this.slipTypeList = slipTypeList;
	}

	/**
	 * �`�[���(����)
	 * 
	 * @param slipType �`�[���(����)
	 */
	public void addSlipType(String... slipType) {
		for (String no : slipType) {
			this.slipTypeList.add(no);
		}
	}

	/**
	 * �X�V�敪(����)
	 * 
	 * @return �X�V�敪(����)
	 */
	public int[] getSlipStateList() {
		if (slipStateList.isEmpty()) {
			return null;
		}

		int[] array = new int[slipStateList.size()];
		for (int i = 0; i < slipStateList.size(); i++) {
			array[i] = slipStateList.get(i);
		}

		return array;
	}

	/**
	 * �X�V�敪(����)
	 * 
	 * @param slipStateList �X�V�敪(����)
	 */
	public void setSlipStateList(int[] slipStateList) {
		addSlipState(slipStateList);
	}

	/**
	 * �X�V�敪(����)
	 * 
	 * @param slipState �X�V�敪(����)
	 */
	public void addSlipState(int... slipState) {
		for (int state : slipState) {
			this.slipStateList.add(state);
		}
	}

	/**
	 * �X�V�敪(����)
	 * 
	 * @param slipState �X�V�敪(����)
	 */
	public void addSlipState(SlipState... slipState) {
		for (SlipState state : slipState) {
			this.slipStateList.add(state.value);
		}
	}

	/**
	 * �X�V�敪(����)
	 * 
	 * @param slipState �X�V�敪(����)
	 */
	public void setSlipStateList(List<SlipState> slipState) {
		for (SlipState state : slipState) {
			this.slipStateList.add(state.value);
		}
	}

	/**
	 * �`�[���tFROM
	 * 
	 * @return �`�[���tFROM
	 */
	public Date getSlipDateFrom() {
		return slipDateFrom;
	}

	/**
	 * �`�[���tFROM
	 * 
	 * @param fromSlipDate �`�[���tFROM
	 */
	public void setSlipDateFrom(Date fromSlipDate) {
		this.slipDateFrom = fromSlipDate;
	}

	/**
	 * �`�[���tTO
	 * 
	 * @return �`�[���tTO
	 */
	public Date getSlipDateTo() {
		return slipDateTo;
	}

	/**
	 * �`�[���tTO
	 * 
	 * @param toSlipDate �`�[���tTO
	 */
	public void setSlipDateTo(Date toSlipDate) {
		this.slipDateTo = toSlipDate;
	}

	/**
	 * �\�[�g���擾����
	 * 
	 * @return �\�[�g
	 */
	public String getOrder() {

		String sort = "";

		for (String column : order) {

			if (Util.isNullOrEmpty(sort)) {
				sort = column;

			} else {
				sort += "," + column;
			}
		}

		return sort;
	}

	/**
	 * �\�[�g��ݒ肷��
	 * 
	 * @param orderBy
	 */
	public void setOrder(String orderBy) {
		addOrder(orderBy);
	}

	/**
	 * �\�[�g�̒ǉ�
	 * 
	 * @param sorts �\�[�g
	 */
	public void addOrder(String... sorts) {
		for (String sort : sorts) {
			this.order.add(sort);
		}
	}

	/**
	 * �\�[�g���̃N���A
	 */
	public void clearOrder() {
		this.order.clear();
	}

	/**
	 * �˗���FROM
	 * 
	 * @return �˗���FROM
	 */
	public Date getRequestDateFrom() {
		return requestDateFrom;
	}

	/**
	 * �˗���FROM
	 * 
	 * @param requestDateFrom �˗���FROM
	 */
	public void setRequestDateFrom(Date requestDateFrom) {
		this.requestDateFrom = requestDateFrom;
	}

	/**
	 * �˗���TO
	 * 
	 * @return �˗���TO
	 */
	public Date getRequestDateTo() {
		return requestDateTo;
	}

	/**
	 * �˗���TO
	 * 
	 * @param requestDateTo �˗���TO
	 */
	public void setRequestDateTo(Date requestDateTo) {
		this.requestDateTo = requestDateTo;
	}

	/**
	 * �˗�����R�[�h
	 * 
	 * @return �˗�����R�[�h
	 */
	public String getRequestDeptCode() {
		return requestDeptCode;
	}

	/**
	 * �˗�����R�[�h
	 * 
	 * @param requestDeptCode �˗�����R�[�h
	 */
	public void setRequestDeptCode(String requestDeptCode) {
		this.requestDeptCode = requestDeptCode;
	}

	/**
	 * �˗��҃R�[�h
	 * 
	 * @return �˗��҃R�[�h
	 */
	public String getRequestEmpCode() {
		return requestEmpCode;
	}

	/**
	 * �˗��҃R�[�h
	 * 
	 * @param requestEmpCode �˗��҃R�[�h
	 */
	public void setRequestEmpCode(String requestEmpCode) {
		this.requestEmpCode = requestEmpCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * BOOK No.
	 * 
	 * @return BOOK No.
	 */
	public int getBookNo() {
		return bookNo;
	}

	/**
	 * BOOK No.
	 * 
	 * @param bookNo BOOK No.
	 */
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	/**
	 * �ʉ݃^�C�v
	 * 
	 * @param type �ʉ݃^�C�v
	 */
	public void setCurrencyType(CurrencyType type) {
		this.bookNo = type.value;
	}

	/**
	 * �`�[�ԍ�
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * �`�[�ԍ�
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * �x��/������FROM
	 * 
	 * @return �x��/������FROM
	 */
	public Date getPaymentDayFrom() {
		return paymentDayFrom;
	}

	/**
	 * �x����FROM
	 * 
	 * @param paymentDayFrom �x����FROM
	 */
	public void setPaymentDayFrom(Date paymentDayFrom) {
		this.paymentDayFrom = paymentDayFrom;
	}

	/**
	 * �x����TO
	 * 
	 * @return �x����TO
	 */
	public Date getPaymentDayTo() {
		return paymentDayTo;
	}

	/**
	 * �x��/������TO
	 * 
	 * @param paymentDayTo �x��/������TO
	 */
	public void setPaymentDayTo(Date paymentDayTo) {
		this.paymentDayTo = paymentDayTo;
	}

	/**
	 * �`�[�ԍ��i�J�n�j
	 * 
	 * @return �`�[�ԍ��i�J�n�j
	 */
	public String getSlipNoFrom() {
		return slipNoFrom;
	}

	/**
	 * �`�[�ԍ��i�J�n�j
	 * 
	 * @param slipNoFrom �`�[�ԍ��i�J�n�j
	 */
	public void setSlipNoFrom(String slipNoFrom) {
		this.slipNoFrom = slipNoFrom;
	}

	/**
	 * �`�[�ԍ��i�I���j
	 * 
	 * @return �`�[�ԍ��i�I���j
	 */
	public String getSlipNoTo() {
		return slipNoTo;
	}

	/**
	 * �`�[�ԍ��i�I���j
	 * 
	 * @param slipNoTo �`�[�ԍ��i�I���j
	 */
	public void setSlipNoTo(String slipNoTo) {
		this.slipNoTo = slipNoTo;
	}

	/**
	 * IFRS�����敪
	 * 
	 * @return IFRS�����敪
	 */
	public int[] getAdjDivisionList() {

		if (adjDivisionList.isEmpty()) {
			return null;
		}

		int[] array = new int[adjDivisionList.size()];
		for (int i = 0; i < adjDivisionList.size(); i++) {
			array[i] = adjDivisionList.get(i);
		}

		return array;
	}

	/**
	 * IFRS�����敪
	 * 
	 * @param adjDivisionList IFRS�����敪
	 */
	public void setAdjDivisionList(int[] adjDivisionList) {
		this.adjDivisionList.clear();
		for (int adjDivision : adjDivisionList) {
			this.adjDivisionList.add(adjDivision);
		}
	}

	/**
	 * ����^�C�v
	 * 
	 * @param types �^�C�v
	 */
	public void setAccountBook(AccountBook... types) {
		for (AccountBook type : types) {
			this.adjDivisionList.add(type.value);
		}
	}

	/**
	 * groupAccountDivision���擾����B
	 * 
	 * @return int groupAccountDivision
	 */
	public int getGroupAccountDivision() {
		return groupAccountDivision;
	}

	/**
	 * groupAccountDivision��ݒ肷��B
	 * 
	 * @param groupAccountDivision
	 */
	public void setGroupAccountDivision(int groupAccountDivision) {
		this.groupAccountDivision = groupAccountDivision;
	}

	/**
	 * �t�֓`�[�͏���
	 */
	public void setGroupAccountOff() {
		this.groupAccountDivision = 0;
	}

	/**
	 * �r���敪���擾����B
	 * 
	 * @return �r���敪
	 */
	public int getLockState() {
		return lockState;
	}

	/**
	 * �r���敪��ݒ肷��B
	 * 
	 * @param lockState �r���敪
	 */
	public void setLockState(int lockState) {
		this.lockState = lockState;
	}

	/**
	 * �r������Ă��Ȃ�
	 */
	public void setNotLocked() {
		this.lockState = Slip.SHR_KBN.NON_LOCKED;
	}

	/**
	 * �Ј�(���C�N)
	 * 
	 * @param text �Ј�(���C�N)
	 */
	public void setEmployeeNamesLike(String text) {
		this.employeeNamesLike = text;
	}

	/**
	 * �Ј�(���C�N)
	 * 
	 * @return �Ј�(���C�N)
	 */
	public String getEmployeeNamesLike() {
		return this.employeeNamesLike;
	}

	/**
	 * �E�v�R�[�h���擾����B
	 * 
	 * @return remarkCode �E�v�R�[�h
	 */
	public String getRemarkCode() {
		return remarkCode;
	}

	/**
	 * �E�v�R�[�h��ݒ肷��B
	 * 
	 * @param remarkCode �E�v�R�[�h
	 */
	public void setRemarkCode(String remarkCode) {
		this.remarkCode = remarkCode;
	}

	/**
	 * �E�v(���C�N)
	 * 
	 * @param text �E�v(���C�N)
	 */
	public void setRemarksLike(String text) {
		this.remarksLike = text;
	}

	/**
	 * �E�v(���C�N)
	 * 
	 * @return �E�v(���C�N)
	 */
	public String getRemarksLike() {
		return this.remarksLike;
	}

	/**
	 * �����d��敪
	 * 
	 * @param auto �����d��敪
	 */
	public void setAutoDivision(int auto) {
		this.autoDivision = auto;
	}

	/**
	 * �����d��敪
	 * 
	 * @return �����d��敪
	 */
	public int getAutoDivision() {
		return autoDivision;
	}

	/**
	 * custmorCode���擾����B
	 * 
	 * @return String custmorCode
	 */
	public String getCustmorCode() {
		return custmorCode;
	}

	/**
	 * custmorCode��ݒ肷��B
	 * 
	 * @param custmorCode
	 */
	public void setCustmorCode(String custmorCode) {
		this.custmorCode = custmorCode;
	}

	/**
	 * detailItemCode���擾����B
	 * 
	 * @return String detailItemCode
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * detailItemCode��ݒ肷��B
	 * 
	 * @param detailItemCode
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * itemCode���擾����B
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCode��ݒ肷��B
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * manegement1Code���擾����B
	 * 
	 * @return String manegement1Code
	 */
	public String getManegement1Code() {
		return manegement1Code;
	}

	/**
	 * manegement1Code��ݒ肷��B
	 * 
	 * @param manegement1Code
	 */
	public void setManegement1Code(String manegement1Code) {
		this.manegement1Code = manegement1Code;
	}

	/**
	 * manegement2Code���擾����B
	 * 
	 * @return String manegement2Code
	 */
	public String getManegement2Code() {
		return manegement2Code;
	}

	/**
	 * manegement2Code��ݒ肷��B
	 * 
	 * @param manegement2Code
	 */
	public void setManegement2Code(String manegement2Code) {
		this.manegement2Code = manegement2Code;
	}

	/**
	 * manegement3Code���擾����B
	 * 
	 * @return String manegement3Code
	 */
	public String getManegement3Code() {
		return manegement3Code;
	}

	/**
	 * manegement3Code��ݒ肷��B
	 * 
	 * @param manegement3Code
	 */
	public void setManegement3Code(String manegement3Code) {
		this.manegement3Code = manegement3Code;
	}

	/**
	 * manegement4Code���擾����B
	 * 
	 * @return String manegement4Code
	 */
	public String getManegement4Code() {
		return manegement4Code;
	}

	/**
	 * manegement4Code��ݒ肷��B
	 * 
	 * @param manegement4Code
	 */
	public void setManegement4Code(String manegement4Code) {
		this.manegement4Code = manegement4Code;
	}

	/**
	 * manegement5Code���擾����B
	 * 
	 * @return String manegement5Code
	 */
	public String getManegement5Code() {
		return manegement5Code;
	}

	/**
	 * manegement5Code��ݒ肷��B
	 * 
	 * @param manegement5Code
	 */
	public void setManegement5Code(String manegement5Code) {
		this.manegement5Code = manegement5Code;
	}

	/**
	 * manegement6Code���擾����B
	 * 
	 * @return String manegement6Code
	 */
	public String getManegement6Code() {
		return manegement6Code;
	}

	/**
	 * manegement6Code��ݒ肷��B
	 * 
	 * @param manegement6Code
	 */
	public void setManegement6Code(String manegement6Code) {
		this.manegement6Code = manegement6Code;
	}

	/**
	 * subItemCode���擾����B
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCode��ݒ肷��B
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * dataKindList��ݒ肷��B
	 * 
	 * @param dataKindList
	 */
	public void setDataKindList(List<String> dataKindList) {
		this.dataKindList = dataKindList;
	}

	/**
	 * �d��C���^�[�t�F�[�X�敪
	 * 
	 * @param ifDivision �X�V�敪
	 */
	public void setIfType(JornalIfType ifDivision) {
		this.ifDivision = ifDivision.value;
	}

	/**
	 * �d��C���^�[�t�F�[�X�敪
	 * 
	 * @param ifDivision �d��C���^�[�t�F�[�X�敪
	 */
	public void setIfDivision(int ifDivision) {
		this.ifDivision = ifDivision;
	}

	/**
	 * �d��C���^�[�t�F�[�X�敪
	 * 
	 * @return �d��C���^�[�t�F�[�X�敪
	 */
	public int getIfDivision() {
		return this.ifDivision;
	}

	/**
	 * NULL�̈˗��]�ƈ��R�[�h���܂߂邩�ǂ���
	 * 
	 * @param requestEmpCodeIncledNull true:�܂߂�
	 */
	public void setRequestEmpCodeIncledNull(boolean requestEmpCodeIncledNull) {
		this.requestEmpCodeIncledNull = requestEmpCodeIncledNull;
	}

	/**
	 * NULL�̈˗��]�ƈ��R�[�h���܂߂邩�ǂ���
	 * 
	 * @return true:�܂߂�
	 */
	public boolean isRequestEmpCodeIncledNull() {
		return requestEmpCodeIncledNull;
	}

	/**
	 * NULL�̈˗�����R�[�h���܂߂邩�ǂ���
	 * 
	 * @param requestDeptCodeIncledNull true:�܂߂�
	 */
	public void setRequestDeptCodeIncledNull(boolean requestDeptCodeIncledNull) {
		this.requestDeptCodeIncledNull = requestDeptCodeIncledNull;
	}

	/**
	 * NULL�̈˗�����R�[�h���܂߂邩�ǂ���
	 * 
	 * @return true:�܂߂�
	 */
	public boolean isRequestDeptCodeIncledNull() {
		return requestDeptCodeIncledNull;
	}

	/**
	 * �����ԍ����擾����B
	 * 
	 * @return String �����ԍ�
	 */
	public String getEraseNo() {
		return eraseNo;
	}

	/**
	 * �����ԍ���ݒ肷��B
	 * 
	 * @param eraceSlipNo �����ԍ�
	 */
	public void setEraseNo(String eraceSlipNo) {
		this.eraseNo = eraceSlipNo;
	}

	/**
	 * �����ԍ����C�N
	 * 
	 * @return �����ԍ����C�N
	 */
	public String getEraseNoLike() {
		return this.eraseNoLike;
	}

	/**
	 * �����ԍ����C�N
	 * 
	 * @param eraseNoLike �����ԍ����C�N
	 */
	public void setEraseNoLike(String eraseNoLike) {
		this.eraseNoLike = eraseNoLike;
	}

	/**
	 * ���E�`�[�ԍ����C�N
	 * 
	 * @return ���E�`�[�ԍ����C�N
	 */
	public String getOffsetSlipNoLike() {
		return this.offsetSlipNoLike;
	}

	/**
	 * ���E�`�[�ԍ����C�N
	 * 
	 * @param offsetSlipNoLike ���E�`�[�ԍ����C�N
	 */
	public void setOffsetSlipNoLike(String offsetSlipNoLike) {
		this.offsetSlipNoLike = offsetSlipNoLike;
	}

	/**
	 * programCode���擾����B
	 * 
	 * @return String programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * programCode��ݒ肷��B
	 * 
	 * @param programCode
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * userCode���擾����B
	 * 
	 * @return String userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * userCode��ݒ肷��B
	 * 
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * searchSumCustomer���擾����B
	 * 
	 * @return boolean searchSumCustomer
	 */
	public boolean isSearchSumCustomer() {
		return searchSumCustomer;
	}

	/**
	 * searchSumCustomer��ݒ肷��B
	 * 
	 * @param searchSumCustomer
	 */
	public void setSearchSumCustomer(boolean searchSumCustomer) {
		this.searchSumCustomer = searchSumCustomer;
	}

	/**
	 * currencyCode���擾����B
	 * 
	 * @return String currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * currencyCode��ݒ肷��B
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * �v�㕔��R�[�h�̎擾
	 * 
	 * @return departmentCode �v�㕔��R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * �v�㕔��R�[�h�̐ݒ�
	 * 
	 * @param departmentCode �v�㕔��R�[�h
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * �v�㕔��R�[�h(���C�N)
	 * 
	 * @return �v�㕔��R�[�h(���C�N)
	 */
	public String getDepartmentCodeLike() {
		return departmentCodeLike;
	}

	/**
	 * �v�㕔��R�[�h(���C�N)
	 * 
	 * @param departmentCodeLike �v�㕔��R�[�h(���C�N)
	 */
	public void setDepartmentCodeLike(String departmentCodeLike) {
		this.departmentCodeLike = departmentCodeLike;
	}

	/**
	 * �v�㕔�嗪��(���C�N)
	 * 
	 * @return �v�㕔�嗪��(���C�N)
	 */
	public String getDepartmentNamesLike() {
		return departmentNamesLike;
	}

	/**
	 * �v�㕔�嗪��(���C�N)
	 * 
	 * @param departmentNamesLike �v�㕔�嗪��(���C�N)
	 */
	public void setDepartmentNamesLike(String departmentNamesLike) {
		this.departmentNamesLike = departmentNamesLike;
	}

	/**
	 * �o�[��FROM
	 * 
	 * @return �o�[��FROM
	 */
	public Date getCashDayFrom() {
		return cashDayFrom;
	}

	/**
	 * �o�[��FROM
	 * 
	 * @param cashDayFrom �o�[��FROM
	 */
	public void setCashDayFrom(Date cashDayFrom) {
		this.cashDayFrom = cashDayFrom;
	}

	/**
	 * �o�[��TO
	 * 
	 * @return �o�[��TO
	 */
	public Date getCashDayTo() {
		return cashDayTo;
	}

	/**
	 * �o�[��TO
	 * 
	 * @param cashDayTo �o�[��TO
	 */
	public void setCashDayTo(Date cashDayTo) {
		this.cashDayTo = cashDayTo;
	}

	/**
	 * ����旪�̃��C�N
	 * 
	 * @return ����旪�̃��C�N
	 */
	public String getCustmorNamesLike() {
		return custmorNamesLike;
	}

	/**
	 * ����旪�̃��C�N
	 * 
	 * @param custmorNameLike ����旪�̃��C�N
	 */
	public void setCustmorNamesLike(String custmorNameLike) {
		this.custmorNamesLike = custmorNameLike;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param company ��ЃR�[�h
	 */
	public void setCompany(Company company) {
		this.company = company;
		this.companyCode = company.getCode();
	}

	/**
	 * receiveDayFrom���擾����B
	 * 
	 * @return Date receiveDayFrom
	 */
	public Date getReceiveDayFrom() {
		return this.receiveDayFrom;
	}

	/**
	 * receiveDayFrom��ݒ肷��B
	 * 
	 * @param receiveDayFrom
	 */
	public void setReceiveDayFrom(Date receiveDayFrom) {
		this.receiveDayFrom = receiveDayFrom;
	}

	/**
	 * receiveDayTo���擾����B
	 * 
	 * @return Date receiveDayTo
	 */
	public Date getReceiveDayTo() {
		return this.receiveDayTo;
	}

	/**
	 * receiveDayTo��ݒ肷��B
	 * 
	 * @param receiveDayTo
	 */
	public void setReceiveDayTo(Date receiveDayTo) {
		this.receiveDayTo = receiveDayTo;
	}

	/**
	 * ���V�X�A�g�`�[���܂߂邩�ǂ���
	 * 
	 * @return true:�܂߂�
	 */
	public boolean isIncludeOtherSystem() {
		return this.includeOtherSystem;
	}

	/**
	 * ���V�X�A�g�`�[���܂߂邩�ǂ���
	 * 
	 * @param includeOtherSyste true:�܂߂�
	 */
	public void setIncludeOtherSystem(boolean includeOtherSyste) {
		this.includeOtherSystem = includeOtherSyste;
	}

	/**
	 * �`�[���t
	 * 
	 * @return �`�[���t
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * �`�[���t
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * �o�^��FROM
	 * 
	 * @return �o�^��FROM
	 */
	public Date getEntryDateFrom() {
		return entryDateFrom;
	}

	/**
	 * �o�^��FROM
	 * 
	 * @param entryDateFrom �o�^��FROM
	 */
	public void setEntryDateFrom(Date entryDateFrom) {
		this.entryDateFrom = entryDateFrom;
	}

	/**
	 * �o�^��TO
	 * 
	 * @return �o�^��TO
	 */
	public Date getEntryDateTo() {
		return entryDateTo;
	}

	/**
	 * �o�^��TO
	 * 
	 * @param entryDateTo �o�^��TO
	 */
	public void setEntryDateTo(Date entryDateTo) {
		this.entryDateTo = entryDateTo;
	}

	/**
	 * �X�V��FROM
	 * 
	 * @return �X�V��FROM
	 */
	public Date getUpdateDateFrom() {
		return updateDateFrom;
	}

	/**
	 * �X�V��FROM
	 * 
	 * @param updateDateFrom �X�V��FROM
	 */
	public void setUpdateDateFrom(Date updateDateFrom) {
		this.updateDateFrom = updateDateFrom;
	}

	/**
	 * �ʉ݃R�[�h���X�g
	 * 
	 * @return �ʉ݃R�[�h���X�g
	 */
	public List<String> getCurrencyCodeList() {
		return currencyCodeList;
	}

	/**
	 * �ʉ݃R�[�h���X�g
	 * 
	 * @param currencyCodeList �ʉ݃R�[�h���X�g
	 */
	public void setCurrencyCodeList(List<String> currencyCodeList) {
		this.currencyCodeList = currencyCodeList;
	}

	/**
	 * ������FROM
	 * 
	 * @return ������FROM
	 */
	public Date getEraseDayFrom() {
		return this.eraseDayFrom;
	}

	/**
	 * ������FROM
	 * 
	 * @param eraseDayFrom ������FROM
	 */
	public void setEraseDayFrom(Date eraseDayFrom) {
		this.eraseDayFrom = eraseDayFrom;
	}

	/**
	 * ������TO
	 * 
	 * @return ������TO
	 */
	public Date getEraseDayTo() {
		return this.eraseDayTo;
	}

	/**
	 * ������TO
	 * 
	 * @param eraseDayTo ������TO
	 */
	public void setEraseDayTo(Date eraseDayTo) {
		this.eraseDayTo = eraseDayTo;
	}

	/**
	 * �T�Z����t���O�̎擾
	 * 
	 * @return �T�Z����t���O
	 */
	public boolean isEstimateCancelFlg() {
		return estimateCancelFlg;
	}

	/**
	 * �T�Z����t���O�̐ݒ�
	 * 
	 * @param estimateCancelFlg �T�Z����t���O
	 */
	public void setEstimateCancelFlg(boolean estimateCancelFlg) {
		this.estimateCancelFlg = estimateCancelFlg;
	}

	/**
	 * �ő�w�b�_�[�擾�����̎擾
	 * 
	 * @return maxHeaderCount �ő�w�b�_�[�擾����
	 */
	public int getMaxHeaderCount() {
		return maxHeaderCount;
	}

	/**
	 * �ő�w�b�_�[�擾�����̐ݒ�
	 * 
	 * @param maxHeaderCount �ő�w�b�_�[�擾����
	 */
	public void setMaxHeaderCount(int maxHeaderCount) {
		this.maxHeaderCount = maxHeaderCount;
	}

	/**
	 * ���[�N�t���[���F�p�̌������ǂ���
	 * 
	 * @return boolean searchWorkFlow
	 */
	public boolean isSearchWorkFlow() {
		return searchWorkFlow;
	}

	/**
	 * ���[�N�t���[���F�p�̌������ǂ���
	 * 
	 * @param searchWorkFlow
	 */
	public void setSearchWorkFlow(boolean searchWorkFlow) {
		this.searchWorkFlow = searchWorkFlow;
	}

	/**
	 * ���[�N�t���[���F ���F�O���[�v���擾
	 * 
	 * @return aprvRoleGroupList
	 */
	public List<String> getAprvRoleGroupList() {
		return aprvRoleGroupList;
	}

	/**
	 * ���[�N�t���[���F ���F�O���[�v���Z�b�g����
	 * 
	 * @param aprvRoleGroupList aprvRoleGroupList
	 */
	public void setAprvRoleGroupList(List<String> aprvRoleGroupList) {
		this.aprvRoleGroupList = aprvRoleGroupList;
	}

	/**
	 * ���[�N�t���[���F �Ώۂ̏��F���[��
	 * 
	 * @return aprvRoleList
	 */
	public List<String> getAprvRoleList() {
		return aprvRoleList;
	}

	/**
	 * ���[�N�t���[���F �Ώۂ̏��F���[��
	 * 
	 * @param aprvRoleList
	 */
	public void setAprvRoleList(List<String> aprvRoleList) {
		this.aprvRoleList = aprvRoleList;
	}

}
