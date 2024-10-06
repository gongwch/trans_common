package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * “`•[ƒƒWƒbƒNƒtƒ@ƒNƒgƒŠ[À‘•
 */
public class SlipLogicFactoryImpl extends TModel implements SlipLogicFactory {

	/** ƒƒWƒbƒN–¼ */
	protected Map<String, String> logicMap = new TreeMap<String, String>();

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^.
	 */
	public SlipLogicFactoryImpl() {
		initData();
	}

	/**
	 * “`•[ƒƒWƒbƒN‚ğ“à•”•Û.
	 */
	protected void initData() {

		// 011 “ü‹à“`•[
		logicMap.put("011", "InputCashFlowSlipLogic");
		// 012 o‹à“`•[
		logicMap.put("012", "OutputCashFlowSlipLogic");
		// 013 U‘Ö“`•[
		logicMap.put("013", "TransferSlipLogic");
		// 014 U–ß“`•[
		logicMap.put("014", "ReversingSlipLogic");
		// 015 U–ßæÁ“`•[
		logicMap.put("015", "ReversingSlipLogic");
		// 016 IFRS’ •ë’²®“`•[
		logicMap.put("016", "IfrsAdjustmentSlipLogic");
		// 017 ©‘’ •ë’²®“`•[
		logicMap.put("017", "IfrsAdjustmentSlipLogic");

		// 01C BSÁ“`•[
		logicMap.put("01C", "BsCalculateEraseSlip");
		// 01A ŠO‰İ•]‰¿‘Ö“`•[
		logicMap.put("01A", "EvaluationSlip");
		// 01B ŠO‰İ•]‰¿‘ÖæÁ“`•[
		logicMap.put("01B", "EvaluationCancelSlip");

		// 01D ŒŸ”ï—pŒvã“`•[
		logicMap.put("01D", "MonthlyPatternSlipLogic");

		// 021 Ğˆõ‰¼•¥
		logicMap.put("021", "TemporaryPaymentSlipLogic");
		// 022 Œo”ï¸Z
		logicMap.put("022", "SettlementSlipLogic");
		// 023 Â–±Œvã
		logicMap.put("023", "APSlipLogic");

		// 024 x•¥“`•[iĞˆõj
		logicMap.put("024", "TransferSlipLogic");
		// 025 x•¥“`•[i—Õj
		// 026 x•¥“`•[i’èj
		// 027 ŠO‘‘—‹à’²®

		// 028 28:x•¥“`•[(‹¤’Ê)
		logicMap.put("028", "ExPaymentSlipLogic");

		// 031 ÂŒ Œvã
		logicMap.put("031", "ARSlipLogic");
		// 032 ÂŒ Á
		logicMap.put("032", "ARErasingSlipLogic");
		// 03A “ü‹à©“®“`•[
		logicMap.put("03A", "CashInAutoSlip");

		// 061 ŒÅ’è‘YU‘Ö“`•[
		logicMap.put("061", "FaAutoTransferSlipLogic");
		logicMap.put("062", "FaAutoTransferSlipLogic");

		// ‘ŠE¸Z“`•[iU‘Öj
		logicMap.put("FSL", "OffsetClearanceSlip");
		// ‘ŠE¸Z“`•[iÂ–±j
		logicMap.put("FSP", "OffsetClearanceApSlip");
		// ‘ŠE¸Z“`•[iÂŒ j
		logicMap.put("FSR", "OffsetClearanceArSlip");

		try {
			// ’Ç‰Á•ª(U–ß“`•[)
			String[] reversingSlipTypes = ServerConfig.getProperties("trans.slip.reversing.addon.types");
			String[] reversingCancelSlipTypes = ServerConfig.getProperties("trans.slip.reversing.cancel.addon.types");
			for (String slipTypeCode : reversingSlipTypes) {
				// U–ß“`•[
				logicMap.put(slipTypeCode, "ReversingSlipLogic");
			}
			for (String slipTypeCode : reversingCancelSlipTypes) {
				// U–ßæÁ“`•[
				logicMap.put(slipTypeCode, "ReversingSlipLogic");
			}
		} catch (MissingResourceException e) {
			// İ’è‚È‚µˆ—‚È‚µ
		}
	}

	/**
	 * “`•[í•Ê‚ğŒ³‚ÉƒƒWƒbƒNƒNƒ‰ƒX‚ğ•Ô‚·.
	 * 
	 * @param slipType “`•[í•Ê
	 * @return ƒƒWƒbƒNƒNƒ‰ƒX
	 */
	public SlipLogic getLogic(String slipType) {

		try {

			// “`•[í•Êî•ñ‚ğæ“¾
			SlipTypeManager typeMgr = (SlipTypeManager) getComponent(SlipTypeManager.class);
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(slipType);
			List<SlipType> typeList = typeMgr.get(condition);
			if (typeList == null || typeList.isEmpty()) {
				throw new TRuntimeException(getMessage("I00128", slipType));// “`•[í•Ê[{0}]‚ªİ’è‚³‚ê‚Ä‚¢‚Ü‚¹‚ñB
			}

			String logicName = logicMap.get(slipType);
			if (logicName == null) {
				logicName = getDefaultLogicName(typeList.get(0));
				if (logicName == null) {
					throw new TRuntimeException(getMessage("I00128", slipType));// “`•[í•Ê[{0}]‚ªİ’è‚³‚ê‚Ä‚¢‚Ü‚¹‚ñB
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
	 * “`•[í•Ê‚ğŒ³‚ÉƒƒWƒbƒNƒNƒ‰ƒX‚ğ•Ô‚·.
	 * 
	 * @param slipType “`•[í•Ê
	 * @param dataType
	 * @return ƒƒWƒbƒNƒNƒ‰ƒX
	 */
	public SlipLogic getLogic(String slipType, String dataType) {
		try {

			// “`•[í•Êî•ñ‚ğæ“¾
			SlipTypeManager typeMgr = (SlipTypeManager) getComponent(SlipTypeManager.class);
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(slipType);
			List<SlipType> typeList = typeMgr.get(condition);
			if (typeList == null || typeList.isEmpty()) {
				throw new TRuntimeException(getMessage("I00128", slipType));// “`•[í•Ê[{0}]‚ªİ’è‚³‚ê‚Ä‚¢‚Ü‚¹‚ñB
			}

			String logicName = logicMap.get(slipType);
			if (logicName == null) {
				typeList.get(0).setDataType(dataType);
				logicName = getDefaultLogicName(typeList.get(0));
				if (logicName == null) {
					throw new TRuntimeException(getMessage("I00128", slipType));// “`•[í•Ê[{0}]‚ªİ’è‚³‚ê‚Ä‚¢‚Ü‚¹‚ñB
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
	 * ƒfƒtƒHƒ‹ƒg‚Ì“`•[ƒƒWƒbƒN‚ğ•Ô‚·
	 * 
	 * @param slipType “`•[í•Ê
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
