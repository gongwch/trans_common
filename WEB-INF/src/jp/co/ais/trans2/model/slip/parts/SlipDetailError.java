package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[���׃G���[
 */
public class SlipDetailError extends TransferBase {

	/** �G���[��� */
	public static enum ErrorType {

		/** �f�[�^���Z�b�g����Ă��Ȃ� */
		NULL,

		/** �ȖڂɕR�t���K�v�ȃf�[�^���Z�b�g����Ă��Ȃ� */
		NULL_ON_ITEM,

		/** �Y���f�[�^�����݂��Ȃ� */
		NOT_FOUND,

		/** ����OUT */
		TERM_OUT,

		/** ���߂�ꂽ�`�[���t */
		CLOSED_SLIP_DATE,

		/** �Ȗڂ̌Œ蕔��O */
		ITEM_FIXED_OUT,

		/** ��ʉ݈ȊO */
		NOT_KEY_CURRENCY,
	}

	/** �f�[�^��� */
	public static enum DataType {

		/** �`�[���t */
		SLIP_DATE,

		/** �v���ЃR�[�h */
		TRANSFER_COMPANY,

		/** ����R�[�h */
		DEPARTMENT,

		/** �ȖڃR�[�h */
		ITEM,

		/** �⏕�ȖڃR�[�h */
		SUB_ITEM,

		/** ����ȖڃR�[�h */
		DETAIL_ITEM,

		/** �ʉ݃R�[�h */
		CURRENCY,

		/** �s�E�v�R�[�h */
		REMARKS,

		/** �Ј��R�[�h */
		EMPLOYEE,

		/** �����R�[�h */
		CUSTOMER,

		/** ����ŃR�[�h */
		CONSUMPTION_TAX,

		/** �Ǘ�1�R�[�h */
		MANAGE1,

		/** �Ǘ�2�R�[�h */
		MANAGE2,

		/** �Ǘ�3�R�[�h */
		MANAGE3,

		/** �Ǘ�4�R�[�h */
		MANAGE4,

		/** �Ǘ�5�R�[�h */
		MANAGE5,

		/** �Ǘ�6�R�[�h */
		MANAGE6,

		/** ������ */
		ACCRUAL_DATE,

		/** ���[�g */
		CURRENCY_RATE,

		/** ���͋��z */
		IN_AMOUNT,

		/** �M�݋��z */
		KEY_AMOUNT,
	}

	/** �G���[�^�C�v */
	protected ErrorType errorType = null;

	/** �f�[�^�^�C�v */
	protected DataType dataType = null;

	/** �s�ԍ� */
	protected int rowNo = -1;

	/** �Ώۃf�[�^ */
	protected SWK_DTL detail;

	/** ��Џ�� */
	protected AccountConfig accountConfig;

	/**
	 * �f�[�^�^�C�v
	 * 
	 * @return �f�[�^�^�C�v
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * �f�[�^�^�C�v
	 * 
	 * @param dataType �f�[�^�^�C�v
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * �Ώۃf�[�^
	 * 
	 * @return �Ώۃf�[�^
	 */
	public SWK_DTL getDetail() {
		return detail;
	}

	/**
	 * �Ώۃf�[�^
	 * 
	 * @param dtl
	 */
	public void setDetail(SWK_DTL dtl) {
		this.detail = dtl;
	}

	/**
	 * �G���[�^�C�v
	 * 
	 * @return �G���[�^�C�v
	 */
	public ErrorType getErrorType() {
		return errorType;
	}

	/**
	 * �G���[�^�C�v
	 * 
	 * @param errorType �G���[�^�C�v
	 */
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	/**
	 * �s�ԍ�
	 * 
	 * @return �s�ԍ�
	 */
	public int getRowNo() {
		return rowNo;
	}

	/**
	 * �s�ԍ�
	 * 
	 * @param rowNo �s�ԍ�
	 */
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	/**
	 * ������擾
	 * 
	 * @return ������
	 */
	public String getDataWord() {

		switch (getDataType()) {
			case SLIP_DATE: // �`�[���t
				return "C00599";

			case TRANSFER_COMPANY: // �v����
				return "C01052";

			case DEPARTMENT: // ����
				return "C00467";

			case ITEM: // �Ȗ�
				return getItemName();

			case SUB_ITEM: // �⏕�Ȗ�
				return getSubItemName();

			case DETAIL_ITEM: // ����Ȗ�
				return getDetailItemName();

			case CURRENCY: // �ʉ�
				return "C00371";

			case REMARKS: // �s�E�v
				return "C00119";

			case EMPLOYEE: // �Ј�
				return "C00246";

			case CUSTOMER: // �����
				return "C00408";

			case CONSUMPTION_TAX: // �����
				return "C00286";

			case MANAGE1: // �Ǘ�1
				return getManagement1Name();

			case MANAGE2: // �Ǘ�2
				return getManagement2Name();

			case MANAGE3: // �Ǘ�3
				return getManagement3Name();

			case MANAGE4: // �Ǘ�4
				return getManagement4Name();

			case MANAGE5: // �Ǘ�5
				return getManagement5Name();

			case MANAGE6: // �Ǘ�6
				return getManagement6Name();

			case ACCRUAL_DATE: // ������
				return "C00431";

			case CURRENCY_RATE: // �ʉ݃��[�g
				return "C01555";

			case IN_AMOUNT: // ���͋��z
				return "C00574";

			case KEY_AMOUNT: // �M�݋��z
				return "C00576";

			default:
				return "";
		}
	}

	/**
	 * �Ȗڕ\����
	 * 
	 * @return �Ȗڕ\����
	 */
	protected String getItemName() {
		String name = accountConfig != null ? accountConfig.getItemName() : "C00077";
		return Util.isNullOrEmpty(name) ? "C00077" : name;
	}

	/**
	 * �⏕�Ȗڕ\����
	 * 
	 * @return �⏕�Ȗڕ\����
	 */
	protected String getSubItemName() {
		String name = accountConfig != null ? accountConfig.getSubItemName() : "C00488";
		return Util.isNullOrEmpty(name) ? "C00488" : name;
	}

	/**
	 * ����Ȗڕ\����
	 * 
	 * @return ����Ȗڕ\����
	 */
	protected String getDetailItemName() {
		String name = accountConfig != null ? accountConfig.getDetailItemName() : "C00025";
		return Util.isNullOrEmpty(name) ? "C00025" : name;
	}

	/**
	 * �Ǘ�1�\����
	 * 
	 * @return �Ǘ�1�\����
	 */
	protected String getManagement1Name() {
		String name = accountConfig != null ? accountConfig.getManagement1Name() : "C01025";
		return Util.isNullOrEmpty(name) ? "C01025" : name;
	}

	/**
	 * �Ǘ�2�\����
	 * 
	 * @return �Ǘ�2�\����
	 */
	protected String getManagement2Name() {
		String name = accountConfig != null ? accountConfig.getManagement2Name() : "C01027";
		return Util.isNullOrEmpty(name) ? "C01027" : name;
	}

	/**
	 * �Ǘ�3�\����
	 * 
	 * @return �Ǘ�3�\����
	 */
	protected String getManagement3Name() {
		String name = accountConfig != null ? accountConfig.getManagement3Name() : "C01029";
		return Util.isNullOrEmpty(name) ? "C01029" : name;
	}

	/**
	 * �Ǘ�4�\����
	 * 
	 * @return �Ǘ�4�\����
	 */
	protected String getManagement4Name() {
		String name = accountConfig != null ? accountConfig.getManagement4Name() : "C01031";
		return Util.isNullOrEmpty(name) ? "C01031" : name;
	}

	/**
	 * �Ǘ�5�\����
	 * 
	 * @return �Ǘ�5�\����
	 */
	protected String getManagement5Name() {
		String name = accountConfig != null ? accountConfig.getManagement5Name() : "C01033";
		return Util.isNullOrEmpty(name) ? "C01033" : name;
	}

	/**
	 * �Ǘ�6�\����
	 * 
	 * @return �Ǘ�6�\����
	 */
	protected String getManagement6Name() {
		String name = accountConfig != null ? accountConfig.getManagement6Name() : "C01035";
		return Util.isNullOrEmpty(name) ? "C01035" : name;
	}

	/**
	 * �l��Ԃ�
	 * 
	 * @return �l
	 */
	public String getValue() {

		switch (getDataType()) {
			case SLIP_DATE: // �`�[���t
				return DateUtil.toYMDString(detail.getSWK_DEN_DATE());

			case TRANSFER_COMPANY: // �v����
				return detail.getSWK_K_KAI_CODE();

			case DEPARTMENT: // ����
				return detail.getSWK_DEP_CODE();

			case ITEM: // �Ȗ�
				return detail.getSWK_KMK_CODE();

			case SUB_ITEM: // �⏕�Ȗ�
				return detail.getSWK_HKM_CODE();

			case DETAIL_ITEM: // ����Ȗ�
				return detail.getSWK_UKM_CODE();

			case CURRENCY: // �ʉ�
				return detail.getSWK_CUR_CODE();

			case REMARKS: // �s�E�v
				return detail.getSWK_GYO_TEK_CODE();

			case EMPLOYEE: // �Ј�
				return detail.getSWK_EMP_CODE();

			case CUSTOMER: // �����
				return detail.getSWK_TRI_CODE();

			case CONSUMPTION_TAX: // �����
				return detail.getSWK_ZEI_CODE();

			case MANAGE1: // �Ǘ�1
				return detail.getSWK_KNR_CODE_1();

			case MANAGE2: // �Ǘ�2
				return detail.getSWK_KNR_CODE_2();

			case MANAGE3: // �Ǘ�3
				return detail.getSWK_KNR_CODE_3();

			case MANAGE4: // �Ǘ�4
				return detail.getSWK_KNR_CODE_4();

			case MANAGE5: // �Ǘ�5
				return detail.getSWK_KNR_CODE_5();

			case MANAGE6: // �Ǘ�6
				return detail.getSWK_KNR_CODE_6();

			case ACCRUAL_DATE: // ������
				return DateUtil.toYMDString(detail.getHAS_DATE());

			case CURRENCY_RATE: // �ʉ݃��[�g
				return Util.avoidNull(detail.getSWK_CUR_RATE());

			case IN_AMOUNT: // ���͋��z
				return Util.avoidNull(detail.getSWK_IN_KIN());

			case KEY_AMOUNT: // �M�݋��z
				return Util.avoidNull(detail.getSWK_KIN());

			default:
				return "";
		}
	}

	/**
	 * ��Џ��
	 * 
	 * @param config ��Џ��
	 */
	public void setAccountConfig(AccountConfig config) {
		this.accountConfig = config;
	}
}
