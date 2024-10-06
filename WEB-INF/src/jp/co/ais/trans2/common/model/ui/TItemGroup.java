package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TReference.TYPE;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目・補助・内訳のユニットコンポーネント
 * 
 * @author AIS
 */
public class TItemGroup extends TPanel {

	/** 科目フィールド */
	public TItemReference ctrlItemReference;

	/** 補助科目フィールド */
	public TSubItemReference ctrlSubItemReference;

	/** 内訳科目フィールド */
	public TDetailItemReference ctrlDetailItemReference;

	/** コントローラ */
	public TItemGroupController controller;

	/**
	 * コンストラクタ
	 */
	public TItemGroup() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TItemGroup(TYPE type) {

		// コンポーネントを初期化する
		initComponents(type);

		// コンポーネントを配置する
		allocateComponents();
		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return TItemGroupController
	 */
	public TItemGroupController createController() {
		// コントローラ生成
		return new TItemGroupController(this);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		initComponents(TYPE.BUTTON);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 * 
	 * @param type タイプ
	 */
	protected void initComponents(TYPE type) {
		ctrlItemReference = new TItemReference(type);
		ctrlSubItemReference = new TSubItemReference(type);
		ctrlDetailItemReference = new TDetailItemReference(type);
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setSize(ctrlItemReference.getWidth(), 60);

		setLayout(null);

		// 科目
		ctrlItemReference.setLocation(0, 0);
		add(ctrlItemReference);

		// 補助科目
		ctrlSubItemReference.setLocation(0, 20);
		add(ctrlSubItemReference);

		// 内訳科目
		ctrlDetailItemReference.setLocation(0, 40);
		add(ctrlDetailItemReference);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemReference.setTabControlNo(tabControlNo);
		ctrlSubItemReference.setTabControlNo(tabControlNo);
		ctrlDetailItemReference.setTabControlNo(tabControlNo);
	}

	/**
	 * 選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getEntity() {
		return controller.getEntity();
	}

	/**
	 * 選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)<br>
	 *         <b>※入力途中</b>
	 */
	public Item getInputtedEntity() {
		return controller.getInputtedEntity();
	}

	/**
	 * 科目・補助・内訳を設定する.
	 * 
	 * @param item 科目・補助・内訳
	 */
	public void setEntity(Item item) {
		controller.setEntity(item);
	}

	/**
	 * 検索条件のgetter
	 * 
	 * @return 検索条件
	 */
	public ItemGroupSearchCondition getSearchCondition() {
		return controller.getSearchCondition();
	}

	/**
	 * 科目の検索条件getter
	 * 
	 * @return 科目の検索条件
	 */
	public ItemSearchCondition getItemSearchCondition() {
		return controller.getItemSearchCondition();
	}

	/**
	 * 補助科目の検索条件getter
	 * 
	 * @return 補助科目の検索条件
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return controller.getSubItemSearchCondition();
	}

	/**
	 * 内訳科目の検索条件getter
	 * 
	 * @return 内訳科目の検索条件
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return controller.getDetailItemSearchCondition();
	}

	/**
	 * クリア
	 */
	public void clear() {
		controller.clear();
	}

	/**
	 * コールバックリスナー設定
	 * 
	 * @param listener コールバックリスナー
	 */
	public void addCallBackListener(TCallBackListener listener) {
		ctrlItemReference.addCallBackListener(listener);
		ctrlSubItemReference.addCallBackListener(listener);
		ctrlDetailItemReference.addCallBackListener(listener);
	}

	/**
	 * 未入力があるかどうか
	 * 
	 * @return true:未入力あり
	 */
	public boolean isEmpty() {
		return controller.isEmpty();
	}

	@Override
	public void requestFocus() {
		ctrlItemReference.requestTextFocus();
	}

	/**
	 * 新しい条件で科目再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * 新しい条件で科目再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshGroupEntity() {
		controller.refleshGroupEntity();
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	public boolean checkInput() {
		return controller.checkInput();
	}

}
