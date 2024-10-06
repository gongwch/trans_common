package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * 科目集計検索コンポーネント
 */
public class TItemSummaryReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 5845285285425873551L;

	/** コントローラ */
	protected TItemSummaryReferenceController controller;
	
	/**
	 * コンストラクタ.
	 * 
	 */
	public TItemSummaryReference() {
		controller = new TItemSummaryReferenceController(this);

		this.resize();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {
		super.initComponents();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();
	}

	/**
	 * サイズの再反映
	 */
	@Override
	public void resize() {

		int width = (int) (btn.getPreferredSize().getWidth() + code.getPreferredSize().getWidth());
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public ItemSummarySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public ItemSummary getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param Item 科目集計
	 */
	public void setEntity(ItemSummary Item) {
		controller.setEntity(Item);
	}
}
