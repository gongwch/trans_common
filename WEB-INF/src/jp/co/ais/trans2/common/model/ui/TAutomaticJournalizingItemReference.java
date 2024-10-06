package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 自動仕訳科目の検索コンポーネント
 * 
 * @author AIS
 */
public class TAutomaticJournalizingItemReference extends TReference {

	/** コントローラ */
	protected TAutomaticJournalizingItemReferenceController controller;

	/**
	 * コンストラクター
	 */
	public TAutomaticJournalizingItemReference() {

		// コントローラ生成
		controller = createController();

	}

	/**
	 * コントローラの作成
	 * 
	 * @return コントローラ
	 */
	protected TAutomaticJournalizingItemReferenceController createController() {
		return new TAutomaticJournalizingItemReferenceController(this);
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
	public AutomaticJournalItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public AutomaticJournalItem getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param autoJnlItem エンティティ
	 */
	public void setEntity(AutomaticJournalItem autoJnlItem) {
		controller.setEntity(autoJnlItem);
	}

}
