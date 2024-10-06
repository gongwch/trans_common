package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 内訳科目マスタ範囲検索のコンポーネント
 */
public class TDetailItemRange extends TPanel {

	/** 科目検索 */
	public TItemReference itemReference;

	/** 補助科目検索 */
	public TSubItemReference subItemReference;

	/** 内訳科目範囲検索 */
	public TDetailItemReferenceRange detailItemRange;

	/** コントローラ */
	public TDetailItemRangeController controller;

	/**
	 * コンストラクタ.
	 */
	public TDetailItemRange() {
		super();

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();
		controller = new TDetailItemRangeController(this);
	}

	/**
	 * コンポーネントの初期化。主にインスタンスの生成を行います。
	 */
	public void initComponents() {
		itemReference = new TItemReference();
		subItemReference = new TSubItemReference();
		detailItemRange = new TDetailItemReferenceRange();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return TReference
	 */
	public TDetailItemRangeController createController() {
		// コントローラ生成
		return new TDetailItemRangeController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public DetailItemSearchCondition getSearchCondition() {
		return controller.getDetailItemSearchCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Item getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセット
	 * 
	 * @param item Entity
	 */
	public void setEntity(Item item) {
		controller.setEntity(item);
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	public void allocateComponents() {

		setLayout(null);
		setSize(400, 135);

		// 科目検索
		itemReference.setLocation(0, 0);
		add(itemReference);

		// 補助科目検索
		subItemReference.setLocation(0, 30);
		add(subItemReference);

		// 内訳科目範囲検索
		detailItemRange.setLocation(0, 60);
		add(detailItemRange);
	}

	/**
	 * コンポーネントのタブ順の設定を行います。
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		itemReference.setTabControlNo(tabControlNo);
		subItemReference.setTabControlNo(tabControlNo);
		detailItemRange.setTabControlNo(tabControlNo);
	}
}