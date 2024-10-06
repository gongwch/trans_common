package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目+補助科目+内訳科目の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TItemGroupOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TItemGroupOptionalSelectorController controller;

	/**
	 * コンストラクタ.
	 */
	public TItemGroupOptionalSelector() {
		controller = createController();
	}

	/**
	 * コントローラを生成する
	 * 
	 * @return コントローラ
	 */
	protected TItemGroupOptionalSelectorController createController() {
		return new TItemGroupOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public ItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 補助科目検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return controller.getSubItemCondition();
	}

	/**
	 * 内訳科目検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return controller.getDetailItemCondition();
	}

	@Override
	public TItemGroupOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された科目Entity一覧を返す
	 * 
	 * @return 選択された科目Entity一覧
	 */
	public List<Item> getEntities() {
		return controller.getEntities();
	}

	/**
	 * 科目一覧にEntityをセットする
	 * @param item 
	 */
	public void setEntities(List<Item> item) {
		controller.setEntities(item);
	}

	/**
	 * 補助、内訳別に出力した際に、補助、内訳が無いものでも出力するかを設定する。
	 * 
	 * @param bln
	 */
	public void setGetAllItems(boolean bln) {
		controller.setGetAllItems(bln);
	}

	/**
	 * 科目レベルを固定する。
	 * 
	 * @param itemLevel 科目レベル
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		controller.setItemLevel(itemLevel);
	}

	/**
	 * 科目レベルを最大で固定する。<br>
	 * 内訳科目を使用する場合は内訳科目、使用しない場合は補助科目固定。
	 * 
	 * @param ac 会計設定
	 */
	public void setItemLevelMax(AccountConfig ac) {
		controller.setItemLevelMax(ac);
	}

}
