package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * �`�[���W�b�N�t�@�N�g���[����
 */
public class SlipLogicFactoryImpl extends TModel implements SlipLogicFactory {

	/** ���W�b�N�� */
	protected Map<String, String> logicMap = new TreeMap<String, String>();

	/**
	 * �R���X�g���N�^.
	 */
	public SlipLogicFactoryImpl() {
		initData();
	}

	/**
	 * �`�[���W�b�N������ێ�.
	 */
	protected void initData() {

		// 011 �����`�[
		logicMap.put("011", "InputCashFlowSlipLogic");
		// 012 �o���`�[
		logicMap.put("012", "OutputCashFlowSlipLogic");
		// 013 �U�֓`�[
		logicMap.put("013", "TransferSlipLogic");
		// 014 �U�ߓ`�[
		logicMap.put("014", "ReversingSlipLogic");
		// 015 �U�ߎ���`�[
		logicMap.put("015", "ReversingSlipLogic");
		// 016 IFRS���뒲���`�[
		logicMap.put("016", "IfrsAdjustmentSlipLogic");
		// 017 �������뒲���`�[
		logicMap.put("017", "IfrsAdjustmentSlipLogic");

		// 01C BS�����`�[
		logicMap.put("01C", "BsCalculateEraseSlip");
		// 01A �O�ݕ]���֓`�[
		logicMap.put("01A", "EvaluationSlip");
		// 01B �O�ݕ]���֎���`�[
		logicMap.put("01B", "EvaluationCancelSlip");

		// 01D ������p�v��`�[
		logicMap.put("01D", "MonthlyPatternSlipLogic");

		// 021 �Ј�����
		logicMap.put("021", "TemporaryPaymentSlipLogic");
		// 022 �o��Z
		logicMap.put("022", "SettlementSlipLogic");
		// 023 ���v��
		logicMap.put("023", "APSlipLogic");

		// 024 �x���`�[�i�Ј��j
		logicMap.put("024", "TransferSlipLogic");
		// 025 �x���`�[�i�Վ��j
		// 026 �x���`�[�i�莞�j
		// 027 �O����������

		// 028 28:�x���`�[(����)
		logicMap.put("028", "ExPaymentSlipLogic");

		// 031 ���v��
		logicMap.put("031", "ARSlipLogic");
		// 032 ������
		logicMap.put("032", "ARErasingSlipLogic");
		// 03A ���������`�[
		logicMap.put("03A", "CashInAutoSlip");

		// 061 �Œ莑�Y�U�֓`�[
		logicMap.put("061", "FaAutoTransferSlipLogic");
		logicMap.put("062", "FaAutoTransferSlipLogic");

		// ���E���Z�`�[�i�U�ցj
		logicMap.put("FSL", "OffsetClearanceSlip");
		// ���E���Z�`�[�i���j
		logicMap.put("FSP", "OffsetClearanceApSlip");
		// ���E���Z�`�[�i���j
		logicMap.put("FSR", "OffsetClearanceArSlip");

		try {
			// �ǉ���(�U�ߓ`�[)
			String[] reversingSlipTypes = ServerConfig.getProperties("trans.slip.reversing.addon.types");
			String[] reversingCancelSlipTypes = ServerConfig.getProperties("trans.slip.reversing.cancel.addon.types");
			for (String slipTypeCode : reversingSlipTypes) {
				// �U�ߓ`�[
				logicMap.put(slipTypeCode, "ReversingSlipLogic");
			}
			for (String slipTypeCode : reversingCancelSlipTypes) {
				// �U�ߎ���`�[
				logicMap.put(slipTypeCode, "ReversingSlipLogic");
			}
		} catch (MissingResourceException e) {
			// �ݒ�Ȃ��������Ȃ�
		}
	}

	/**
	 * �`�[��ʂ����Ƀ��W�b�N�N���X��Ԃ�.
	 * 
	 * @param slipType �`�[���
	 * @return ���W�b�N�N���X
	 */
	public SlipLogic getLogic(String slipType) {

		try {

			// �`�[��ʏ����擾
			SlipTypeManager typeMgr = (SlipTypeManager) getComponent(SlipTypeManager.class);
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(slipType);
			List<SlipType> typeList = typeMgr.get(condition);
			if (typeList == null || typeList.isEmpty()) {
				throw new TRuntimeException(getMessage("I00128", slipType));// �`�[���[{0}]���ݒ肳��Ă��܂���B
			}

			String logicName = logicMap.get(slipType);
			if (logicName == null) {
				logicName = getDefaultLogicName(typeList.get(0));
				if (logicName == null) {
					throw new TRuntimeException(getMessage("I00128", slipType));// �`�[���[{0}]���ݒ肳��Ă��܂���B
				}
			}

			SlipLogic logic = (SlipLogic) getComponent(logicName);
			logic.setSlipType(slipType);

			if (!typeList.isEmpty()) {
				logic.setDataType(typeList.get(0).getDataType());
			}

			return logic;

		} catch (TException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �`�[��ʂ����Ƀ��W�b�N�N���X��Ԃ�.
	 * 
	 * @param slipType �`�[���
	 * @param dataType
	 * @return ���W�b�N�N���X
	 */
	public SlipLogic getLogic(String slipType, String dataType) {
		try {

			// �`�[��ʏ����擾
			SlipTypeManager typeMgr = (SlipTypeManager) getComponent(SlipTypeManager.class);
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(slipType);
			List<SlipType> typeList = typeMgr.get(condition);
			if (typeList == null || typeList.isEmpty()) {
				throw new TRuntimeException(getMessage("I00128", slipType));// �`�[���[{0}]���ݒ肳��Ă��܂���B
			}

			String logicName = logicMap.get(slipType);
			if (logicName == null) {
				typeList.get(0).setDataType(dataType);
				logicName = getDefaultLogicName(typeList.get(0));
				if (logicName == null) {
					throw new TRuntimeException(getMessage("I00128", slipType));// �`�[���[{0}]���ݒ肳��Ă��܂���B
				}
			}

			SlipLogic logic = (SlipLogic) getComponent(logicName);

			logic.setSlipType(slipType);
			if (!typeList.isEmpty()) {
				logic.setDataType(typeList.get(0).getDataType());
			}

			return logic;

		} catch (TException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �f�t�H���g�̓`�[���W�b�N��Ԃ�
	 * 
	 * @param slipType �`�[���
	 * @return SlipType
	 */
	protected String getDefaultLogicName(SlipType slipType) {

		SlipKind slipKind = SlipKind.get(slipType.getDataType());

		if (SlipKind.INPUT_CASH_FLOW == slipKind) {
			return logicMap.get("011");
		}

		if (SlipKind.OUTPUT_CASH_FLOW == slipKind) {
			return logicMap.get("012");
		}

		if (SlipKind.TRANSFER == slipKind) {
			return logicMap.get("013");
		}

		if (SlipKind.IFRS_TRANSFER == slipKind) {
			return logicMap.get("016");
		}

		if (SlipKind.SALES == slipKind) {
			return logicMap.get("031");
		}

		if (SlipKind.PURCHASE == slipKind) {
			return logicMap.get("023");
		}

		return null;

	}

}
