package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * 摘要検索コンポーネント
 * 
 * @author AIS
 */
public class TRemarkReference extends TReference {

	/** コントローラ */
	protected TRemarkReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TRemarkReference() {
		controller = new TRemarkReferenceController(this);

		this.name.setEditable(true);
		this.name.setMaxLength(80);
		TGuiUtil.setComponentWidth(name, 500);
		resize();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isSlipRemark true:伝票摘要 false:行摘要
	 */
	public TRemarkReference(boolean isSlipRemark) {
		this();

		if (isSlipRemark) {
			getSearchCondition().setSlipRowRemark(false);
			btn.setLangMessageID("C00569");

		} else {
			getSearchCondition().setSlipRemark(false);
			btn.setLangMessageID("C00119");
		}
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public RemarkSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Remark getEntity() {
		return controller.getEntity();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param remark 摘要
	 */
	public void setEntity(Remark remark) {
		controller.setEntity(remark);
	}

}
