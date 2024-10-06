package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 補助科目範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TSubItemReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** 範囲フィールド */
	public TSubItemReferenceRange ctrlSubItemReferenceRange;

	/** 補助科目個別選択 */
	public TSubItemOptionalSelector ctrlSubItemOptionalSelector;

	/** */
	public TSubItemReferenceRangeUnit() {
		super(false);
	}

	/**
	 * @param border ボーダーを表示する場合true
	 */
	public TSubItemReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * @param border ボーダーを表示する場合true
	 * @param title タイトル表示かどうか
	 */
	public TSubItemReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	/**
	 * コンポーネントの初期化
	 */
	@Override
	public void initComponents() {
		ctrlSubItemReferenceRange = new TSubItemReferenceRange();
		ctrlSubItemOptionalSelector = new TSubItemOptionalSelector();
	}

	/**
	 * 
	 */
	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlSubItemOptionalSelector;
	}

	/**
	 * 
	 */
	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlSubItemReferenceRange;
	}

	/**
	 * 開始フィールドで選択された補助科目Entityを返す
	 * 
	 * @return 開始フィールドで選択された補助科目Entity
	 */
	public Item getEntityFrom() {
		return ctrlSubItemReferenceRange.getEntityFrom();
	}

	/**
	 * 終了フィールドで選択された補助科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された補助科目Entity
	 */
	public Item getEntityTo() {
		return ctrlSubItemReferenceRange.getEntityTo();
	}

	@Override
	public String getBorderTitle() {
		return TLoginInfo.getCompany().getAccountConfig().getSubItemName() + TModelUIUtil.getWord("C00324"); // 選択
	}

}
