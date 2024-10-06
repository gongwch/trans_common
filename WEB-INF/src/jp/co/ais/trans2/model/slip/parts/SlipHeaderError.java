package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[�w�b�_�[�G���[
 */
public class SlipHeaderError extends TransferBase {

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
		
		/** ��ʉ݂���Ȃ� */
		NOT_KEY_CURRENCY,

		/** ���׍s������ */
		EMPTY_DETAIL,

		/** ���Ж��ׂ����� */
		NONE_OWN_DETAIL,

		/** ���z���A���o�����X */
		UNBALANCE_AMOUNT,
	}

	/** �f�[�^��� */
	public static enum DataType {

		/** �`�[ */
		SLIP,
		
		/** �`�[���t */
		SLIP_DATE,

		/** ���� */
		DEPARTMENT,
		
		/** �Ȗ� */
		ITEM,

		/** �⏕�Ȗ� */
		SUB_ITEM,

		/** ����Ȗ� */
		DETAIL_ITEM,

		/** �ʉ� */
		CURRENCY,

		/** �����敪 */
		BILL_TYPE,
		
		/** �`�[�E�v */
		REMARKS,
		
		/** �Ј� */
		EMPLOYEE,
		
		/** ����� */
		CUSTOMER,
		
		/** �x�����@ */
		PAY_METHOD,
		
		/** �����x������ */
		PAY_SETTING,
		
		/** ��s���� */
		BANK_ACCOUNT,
		
		/** �ʉ݃��[�g */
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

	/** �w�b�_ */
	protected SWK_HDR header;

	/** ��Џ�� */
	protected AccountConfig config;

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
	 * �w�b�_
	 * 
	 * @return �w�b�_
	 */
	public SWK_HDR getHeader() {
		return header;
	}

	/**
	 * �w�b�_
	 * 
	 * @param header �w�b�_
	 */
	public void setHeader(SWK_HDR header) {
		this.header = header;
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

			case DEPARTMENT: // ����
				return "C00467";

			case ITEM: // �Ȗ�
				return config != null ? config.getItemName() : "C00077";

			case SUB_ITEM: // �⏕�Ȗ�
				return config != null ? config.getSubItemName() : "C00488";

			case DETAIL_ITEM: // ����Ȗ�
				return config != null ? config.getDetailItemName() : "C00025";

			case CURRENCY: // �ʉ�
				return "C00371";

			case BILL_TYPE: // �����敪
				return "C10092";

			case REMARKS: // �`�[�E�v
				return "C00569";

			case EMPLOYEE: // �Ј�
				return "C00246";

			case CUSTOMER: // �����
				return "C00408";

			case PAY_METHOD: // �x�����@
				return "C00233";

			case PAY_SETTING: // �����x������
				return "C10756";

			case BANK_ACCOUNT: // ��s����
				return "C00857";

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
	 * �l��Ԃ�
	 * 
	 * @return �l
	 */
	public String getValue() {

		switch (getDataType()) {
			case SLIP_DATE: // �`�[���t
				return DateUtil.toYMDString(header.getSWK_DEN_DATE());

			case DEPARTMENT: // ����
				return header.getSWK_DEP_CODE();

			case ITEM: // �Ȗ�
				return header.getSWK_KMK_CODE();

			case SUB_ITEM: // �⏕�Ȗ�
				return header.getSWK_HKM_CODE();

			case DETAIL_ITEM: // ����Ȗ�
				return header.getSWK_UKM_CODE();

			case CURRENCY: // �ʉ�
				return header.getSWK_CUR_CODE();

			case BILL_TYPE: // �����敪
				return header.getSWK_SEI_KBN();

			case REMARKS: // �`�[�E�v
				return header.getSWK_TEK_CODE();

			case EMPLOYEE: // �Ј�
				return header.getSWK_EMP_CODE();

			case CUSTOMER: // �����
				return header.getSWK_TRI_CODE();

			case PAY_METHOD: // �x�����@
				return header.getSWK_HOH_CODE();

			case PAY_SETTING: // �����x������
				return header.getSWK_TJK_CODE();

			case BANK_ACCOUNT: // ��s����
				return header.getSWK_CBK_CODE();

			case CURRENCY_RATE: // �ʉ݃��[�g
				return Util.avoidNull(header.getSWK_CUR_RATE());

			case IN_AMOUNT: // ���͋��z
				return Util.avoidNull(header.getSWK_IN_KIN());

			case KEY_AMOUNT: // �M�݋��z
				return Util.avoidNull(header.getSWK_KIN());

			default:
				return "";
		}
	}

	/**
	 * ��Џ��
	 * 
	 * @param config ��Џ��
	 */
	public void setConfig(AccountConfig config) {
		this.config = config;
	}
}
