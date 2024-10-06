package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 補助科目マスタ範囲検索のコンポーネント
 */
public class TSubItemRange extends TPanel {

	/** 科目検索 */
	public TItemReference itemReference;

	/** 補助科目範囲検索 */
	public TSubItemReferenceRange subItemRange;

	/**
	 * コンストラクタ.
	 */
	public TSubItemRange() {
		super();
		initComponents();
		allocateComponents();
	}
	
	/**
	 * コンポーネントの初期化。主にインスタンスの生成を行います。
	 */
	public void initComponents() {
		itemReference = new TItemReference();
		subItemRange = new TSubItemReferenceRange();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	public void allocateComponents() {

		setLayout(null);
		setSize(400, 100);

		// 科目検索
		itemReference.setLocation(0, 0);
		add(itemReference);

		// 補助科目範囲検索
		subItemRange.setLocation(0, 30);
		add(subItemRange);
	}

	/**
	 * コンポーネントのタブ順の設定を行います。
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		itemReference.setTabControlNo(tabControlNo);
		subItemRange.setTabControlNo(tabControlNo);
	}
}