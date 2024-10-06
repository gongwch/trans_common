package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目+補助科目+内訳科目範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TItemGroupRangeUnit extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** 範囲フィールド */
	public TItemGroupRange ctrlItemGroupRange;

	/** 科目個別選択 */
	public TItemGroupOptionalSelector ctrlItemGroupOptionalSelector;

	/**
	 * 
	 *
	 */
	public TItemGroupRangeUnit() {

		initComponents();

		allocateComponents();

	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		ctrlItemGroupRange = new TItemGroupRange();
		ctrlItemGroupOptionalSelector = new TItemGroupOptionalSelector();
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {

		setLayout(null);

		setSize(ctrlItemGroupOptionalSelector.getWidth(), ctrlItemGroupRange.getHeight()
			+ ctrlItemGroupOptionalSelector.getHeight());

		ctrlItemGroupRange.setLocation(0, 0);
		add(ctrlItemGroupRange);

		ctrlItemGroupOptionalSelector.setLocation(0, ctrlItemGroupRange.getHeight());
		add(ctrlItemGroupOptionalSelector);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemGroupRange.setTabControlNo(tabControlNo);
		ctrlItemGroupOptionalSelector.setTabControlNo(tabControlNo);
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(正常) / false(エラー)
	 */
	public boolean isSmallerFrom() {
		return ctrlItemGroupRange.isSmallerFrom();
	}

	/**
	 * 開始フィールドで選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getFromEntity() {
		return ctrlItemGroupRange.getFromEntity();
	}

	/**
	 * 終了フィールドで選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getToEntity() {
		return ctrlItemGroupRange.getToEntity();
	}

	/**
	 * 選択された科目Entity一覧を返す
	 * 
	 * @return 選択された科目Entity一覧
	 */
	public List<Item> getEntities() {
		return ctrlItemGroupOptionalSelector.getEntities();
	}

	/**
	 * 開始科目コードにフォーカスをあわせる
	 */
	public void requestFromFocus() {
		ctrlItemGroupRange.ctrlItemGroupFrom.ctrlItemReference.requestTextFocus();
	}

	/**
	 * 値をクリアする
	 */
	public void clear() {
		ctrlItemGroupRange.clear();
		ctrlItemGroupOptionalSelector.clear();
	}
}
