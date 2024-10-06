package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TItemReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** 範囲フィールド */
	public TItemReferenceRange ctrlItemReferenceRange;

	/** 科目個別選択 */
	public TItemOptionalSelector ctrlItemOptionalSelector;

	/** */
	public TItemReferenceRangeUnit() {
		super(false);
	}

	/**
	 * @param border ボーダーを表示する場合true
	 */
	public TItemReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * @param border ボーダーを表示する場合true
	 * @param title タイトル表示かどうか
	 */
	public TItemReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	/**
	 * コンポーネントの初期化
	 */
	@Override
	public void initComponents() {
		ctrlItemReferenceRange = new TItemReferenceRange();
		ctrlItemOptionalSelector = new TItemOptionalSelector();
	}

	/**
	 * 
	 */
	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlItemOptionalSelector;
	}

	/**
	 * 
	 */
	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlItemReferenceRange;
	}

	/**
	 * 開始フィールドで選択された科目Entityを返す
	 * 
	 * @return 開始フィールドで選択された科目Entity
	 */
	public Item getEntityFrom() {
		return ctrlItemReferenceRange.getEntityFrom();
	}

	/**
	 * 終了フィールドで選択された科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された科目Entity
	 */
	public Item getEntityTo() {
		return ctrlItemReferenceRange.getEntityTo();
	}

	@Override
	public String getBorderTitle() {
		// 選択
		return TLoginInfo.getCompany().getAccountConfig().getItemName() + TModelUIUtil.getWord("C00324");
	}

}
