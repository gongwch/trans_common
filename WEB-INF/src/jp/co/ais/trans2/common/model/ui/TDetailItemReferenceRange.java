package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 内訳科目範囲検索フィールド
 * 
 * @author AIS
 */
public class TDetailItemReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 2543858197500132048L;

	/** 開始フィールド */
	public TDetailItemReference ctrlDetailItemReferenceFrom;

	/** 終了フィールド */
	public TDetailItemReference ctrlDetailItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlDetailItemReferenceFrom = new TDetailItemReference();
		ctrlDetailItemReferenceTo = new TDetailItemReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlDetailItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlDetailItemReferenceTo.getSearchCondition().setCodeFrom(ctrlDetailItemReferenceFrom.getCode());
			}
		});

		ctrlDetailItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlDetailItemReferenceFrom.getSearchCondition().setCodeTo(ctrlDetailItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlDetailItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlDetailItemReferenceTo;
	}

	/**
	 * 開始フィールドで選択された内訳科目Entityを返す
	 * 
	 * @return 開始フィールドで選択された内訳科目Entity
	 */
	public Item getEntityFrom() {
		return ctrlDetailItemReferenceFrom.getEntity();
	}

	/**
	 * 終了フィールドで選択された内訳科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された内訳科目Entity
	 */
	public Item getEntityTo() {
		return ctrlDetailItemReferenceTo.getEntity();
	}

}
