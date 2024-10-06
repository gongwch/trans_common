package jp.co.ais.trans2.common.model.ui.slip;

import jp.co.ais.trans2.common.client.*;

/**
 * 明細のコンバーター
 */
public abstract class TDetailLayoutController extends TController {

	/**
	 * 明細パネルのレイアウト調整
	 * 
	 * @param panel 明細パネル
	 */
	public abstract void allocateComponents(TSlipDetailPanel panel);

}
