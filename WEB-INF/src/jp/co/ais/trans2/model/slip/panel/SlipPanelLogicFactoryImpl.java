package jp.co.ais.trans2.model.slip.panel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[���W�b�N�t�@�N�g���[����
 */
public class SlipPanelLogicFactoryImpl extends TModel implements SlipPanelLogicFactory {

	/** ���W�b�N�� */
	protected Map<String, String> logicMap = new TreeMap<String, String>();

	/**
	 * �R���X�g���N�^.
	 */
	public SlipPanelLogicFactoryImpl() {
		initData();
	}

	/**
	 * �`�[���W�b�N������ێ�.
	 */
	protected void initData() {

		// 011 �����`�[
		logicMap.put("011", "GLSlipPanelLogic");
		// 012 �o���`�[
		logicMap.put("012", "GLSlipPanelLogic");
		// 013 �U�֓`�[
		logicMap.put("013", "GLSlipPanelLogic");
		// 014 �U�ߓ`�[
		logicMap.put("014", "GLSlipPanelLogic");
		// 023 ���v��
		logicMap.put("023", "APSlipPanelLogic");
		// 031 ���v��
		logicMap.put("031", "ARSlipPanelLogic");
	}

	/**
	 * �`�[��ʂ����Ƀ��W�b�N�N���X��Ԃ�.
	 * 
	 * @param slipType �`�[���
	 * @return ���W�b�N�N���X
	 */
	public SlipPanelLogic getLogic(String slipType) {

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

			SlipPanelLogic logic = (SlipPanelLogic) getComponent(logicName);
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
