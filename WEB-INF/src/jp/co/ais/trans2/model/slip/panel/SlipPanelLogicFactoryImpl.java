package jp.co.ais.trans2.model.slip.panel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票ロジックファクトリー実装
 */
public class SlipPanelLogicFactoryImpl extends TModel implements SlipPanelLogicFactory {

	/** ロジック名 */
	protected Map<String, String> logicMap = new TreeMap<String, String>();

	/**
	 * コンストラクタ.
	 */
	public SlipPanelLogicFactoryImpl() {
		initData();
	}

	/**
	 * 伝票ロジックを内部保持.
	 */
	protected void initData() {

		// 011 入金伝票
		logicMap.put("011", "GLSlipPanelLogic");
		// 012 出金伝票
		logicMap.put("012", "GLSlipPanelLogic");
		// 013 振替伝票
		logicMap.put("013", "GLSlipPanelLogic");
		// 014 振戻伝票
		logicMap.put("014", "GLSlipPanelLogic");
		// 023 債務計上
		logicMap.put("023", "APSlipPanelLogic");
		// 031 債権計上
		logicMap.put("031", "ARSlipPanelLogic");
	}

	/**
	 * 伝票種別を元にロジッククラスを返す.
	 * 
	 * @param slipType 伝票種別
	 * @return ロジッククラス
	 */
	public SlipPanelLogic getLogic(String slipType) {

		try {

			// 伝票種別情報を取得
			SlipTypeManager typeMgr = (SlipTypeManager) getComponent(SlipTypeManager.class);
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(slipType);
			List<SlipType> typeList = typeMgr.get(condition);
			if (typeList == null || typeList.isEmpty()) {
				throw new TRuntimeException(getMessage("I00128", slipType));// 伝票種別[{0}]が設定されていません。
			}

			String logicName = logicMap.get(slipType);
			if (logicName == null) {
				logicName = getDefaultLogicName(typeList.get(0));
				if (logicName == null) {
					throw new TRuntimeException(getMessage("I00128", slipType));// 伝票種別[{0}]が設定されていません。
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
	 * デフォルトの伝票ロジックを返す
	 * 
	 * @param slipType 伝票種別
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
