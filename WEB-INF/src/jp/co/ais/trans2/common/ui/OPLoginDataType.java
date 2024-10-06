package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.util.*;
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
 * OP�e��f�[�^
 */
public enum OPLoginDataType {

	/** 1�FOP�A�C�e���}�X�^ */
	OP_CODE_MST(1),

	/** 2�F����}�X�^ */
	CM_BNKR_TYPE_MST(2),

	/** 3�F�ʉ݃}�X�^ */
	CUR_MST(3),

	/** 4�F�����}�X�^ */
	TRI_MST(4),

	/** 5�F����}�X�^ */
	BMN_MST(5),

	/** 6�F�Ј��}�X�^ */
	EMP_MST(6),

	/** 7�F�D�}�X�^ */
	CM_VESSEL_MST(7),

	/** 8�F�`�}�X�^ */
	CM_PORT_MST(8),

	/** 9�F�q�C�}�X�^ */
	CM_VOYAGE_MST(9),

	/** 10�F����Ń}�X�^ */
	SZEI_MST(10),

	/** 11�F�J�[�S�}�X�^ */
	OP_CRG_MST(11),

	/** 12�FOP�A�C�e���}�X�^ */
	OP_ITEM_MST(12),

	/** 13�F�x�����@�}�X�^ */
	AP_HOH_MST(13),

	/** 14�F��s�����}�X�^ */
	AP_CBK_MST(14),

	/** 15�F���}�X�^ */
	COUNTRY_MST(15);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value
	 */
	OPLoginDataType(int value) {
		this.value = value;
	}

	/**
	 * @param value
	 * @return Enum
	 */
	public static OPLoginDataType get(int value) {
		for (OPLoginDataType e : values()) {
			if (e.value == value) {
				return e;
			}
		}

		return null;
	}

	/**
	 * @param e �f�[�^�敪
	 * @return �L���b�V���t�@�C����
	 */
	public static String getFileName(OPLoginDataType e) {
		if (e == null) {
			return null;
		}

		return e.value + "_" + e.toString() + ".op";
	}

	/**
	 * �\�[�g�L�[�̎擾(��{�͎��ʃL�[��Ԃ��B���킾������)
	 * 
	 * @param e
	 * @param obj
	 * @return �\�[�g�L�[
	 */
	public static String getSortKey(OPLoginDataType e, Object obj) {
		if (e == null || obj == null) {
			return "";
		}

		if (e == CM_BNKR_TYPE_MST) {
			// �������
			CM_BNKR_TYPE_MST entity = (CM_BNKR_TYPE_MST) obj;
			StringBuilder sb = new StringBuilder();
			sb.append(entity.getKAI_CODE());
			sb.append("<>");
			sb.append(StringUtil.fillLeft(Integer.toString(entity.getDISP_ODR()), 5, '0'));
			sb.append("<>");
			sb.append(entity.getBNKR_TYPE_CODE());
			return sb.toString();

		} else if (e == OP_CODE_MST) {
			// CODE����
			OP_CODE_MST entity = (OP_CODE_MST) obj;
			StringBuilder sb = new StringBuilder();
			sb.append(entity.getKAI_CODE());
			sb.append("<>");
			sb.append(entity.getCODE_DIV());
			sb.append("<>");
			sb.append(StringUtil.fillLeft(Integer.toString(entity.getDISP_ODR()), 5, '0'));
			sb.append("<>");
			sb.append(entity.getCODE());
			return sb.toString();
		}

		return getKey(e, obj);
	}

	/**
	 * @param e
	 * @param obj
	 * @return ���ʃL�[
	 */
	public static String getKey(OPLoginDataType e, Object obj) {
		if (e == null || obj == null) {
			return "";
		}

		switch (e) {

			case OP_CODE_MST:
			// 1�FOP�A�C�e��
			{
				OP_CODE_MST entity = (OP_CODE_MST) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getCODE_DIV());
				sb.append("<>");
				sb.append(entity.getCODE());
				sb.append("<>");
				sb.append(entity.getLCL_KBN());
				return sb.toString();
			}

			case CM_BNKR_TYPE_MST:
			// 2�F����
			{
				CM_BNKR_TYPE_MST entity = (CM_BNKR_TYPE_MST) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getBNKR_TYPE_CODE());
				return sb.toString();
			}

			case CUR_MST:
			// 3�F�ʉ�
			{
				Currency entity = (Currency) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case TRI_MST:
			// 4�F�����
			{
				Customer entity = (Customer) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case BMN_MST:
			// 5�F����
			{
				Department entity = (Department) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case EMP_MST:
			// 6�F�Ј�
			{
				Employee entity = (Employee) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCode());
				return sb.toString();
			}

			case CM_VESSEL_MST:
			// 7�F�D
			{
				Vessel entity = (Vessel) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case CM_PORT_MST:
			// 8�F�`
			{
				Port entity = (Port) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case CM_VOYAGE_MST:
			// 9�F�q�C
			{
				Voyage entity = (Voyage) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case SZEI_MST:
			// 10�F�����
			{
				ConsumptionTax entity = (ConsumptionTax) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case OP_CRG_MST:
			// 11�F�J�[�S
			{
				Cargo entity = (Cargo) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case OP_ITEM_MST:
			// 12�FOP�A�C�e��
			{
				OPItem entity = (OPItem) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getITEM_CODE());
				return sb.toString();
			}

			case AP_HOH_MST:
			// 13�F�x�����@
			{
				PaymentMethod entity = (PaymentMethod) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case AP_CBK_MST:
			// 14�F��s����
			{
				BankAccount entity = (BankAccount) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case COUNTRY_MST:
			// 15�F��
			{
				Country entity = (Country) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCode());
				return sb.toString();
			}
		}

		return "";
	}

	/**
	 * @param e
	 * @return �N���X�擾
	 */
	public static Class getEntityClass(OPLoginDataType e) {
		if (e == null) {
			return null;
		}

		switch (e) {

			case OP_CODE_MST:
			// 1�FOP�A�C�e��
			{
				return OP_CODE_MST.class;
			}

			case CM_BNKR_TYPE_MST:
			// 2�F����
			{
				return CM_BNKR_TYPE_MST.class;

			}

			case CUR_MST:
			// 3�F�ʉ�
			{
				return Currency.class;

			}

			case TRI_MST:
			// 4�F�����
			{
				return Customer.class;

			}

			case BMN_MST:
			// 5�F����
			{
				return Department.class;

			}

			case EMP_MST:
			// 6�F�Ј�
			{
				return Employee.class;

			}

			case CM_VESSEL_MST:
			// 7�F�D
			{
				return Vessel.class;

			}

			case CM_PORT_MST:
			// 8�F�`
			{
				return Port.class;

			}

			case CM_VOYAGE_MST:
			// 9�F�q�C
			{
				return Voyage.class;

			}

			case SZEI_MST:
			// 10�F�����
			{
				return ConsumptionTax.class;

			}

			case OP_CRG_MST:
			// 11�F�J�[�S
			{
				return Cargo.class;

			}

			case OP_ITEM_MST:
			// 12�FOP�A�C�e��
			{
				return OPItem.class;

			}

			case AP_HOH_MST:
			// 13�F�x�����@
			{
				return PaymentMethod.class;

			}

			case AP_CBK_MST:
			// 14�F��s����
			{
				return BankAccount.class;

			}

			case COUNTRY_MST:
			// 15�F��
			{
				return Country.class;

			}
		}

		return null;
	}
}
