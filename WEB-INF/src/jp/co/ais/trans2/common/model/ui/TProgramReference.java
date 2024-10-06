package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.program.ProgramSearchCondition;

/**
 * プログラムの検索コンポーネント
 * @author AIS
 *
 */
public class TProgramReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** コントローラ */
	protected TProgramReferenceController controller;

	/**
	 * 
	 *
	 */
	public TProgramReference() {

		// コントローラ生成
		controller = new TProgramReferenceController(this);
		
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * @return 検索条件
	 */
	public ProgramSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

}
