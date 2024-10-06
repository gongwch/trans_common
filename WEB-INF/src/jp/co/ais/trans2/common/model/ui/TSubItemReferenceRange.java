package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 補助科目範囲検索フィールド
 * 
 * @author AIS
 */
public class TSubItemReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 2543858197500132048L;

	/** 開始フィールド */
	public TSubItemReference ctrlSubItemReferenceFrom;

	/** 終了フィールド */
	public TSubItemReference ctrlSubItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlSubItemReferenceFrom = new TSubItemReference();
		ctrlSubItemReferenceTo = new TSubItemReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlSubItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSubItemReferenceTo.getSearchCondition().setCodeFrom(ctrlSubItemReferenceFrom.getCode());
			}
		});

		ctrlSubItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSubItemReferenceFrom.getSearchCondition().setCodeTo(ctrlSubItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlSubItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlSubItemReferenceTo;
	}

	/**
	 * 開始フィールドで選択された補助科目Entityを返す
	 * 
	 * @return 開始フィールドで選択された補助科目Entity
	 */
	public Item getEntityFrom() {
		return ctrlSubItemReferenceFrom.getEntity();
	}

	/**
	 * 終了フィールドで選択された補助科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された補助科目Entity
	 */
	public Item getEntityTo() {
		return ctrlSubItemReferenceTo.getEntity();
	}

}
