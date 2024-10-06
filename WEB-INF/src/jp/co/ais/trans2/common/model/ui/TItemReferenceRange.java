package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;
import jp.co.ais.trans2.model.item.Item;

/**
 * 科目範囲検索フィールド
 * @author AIS
 *
 */
public class TItemReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 2543858197500132048L;

	/** 開始フィールド */
	public TItemReference ctrlItemReferenceFrom;

	/** 終了フィールド */
	public TItemReference ctrlItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlItemReferenceFrom = new TItemReference();
		ctrlItemReferenceTo = new TItemReference();
	}
	/**
	 * 初期化
	 */
	protected void init() {

		ctrlItemReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlItemReferenceTo.getSearchCondition().setCodeFrom(
						ctrlItemReferenceFrom.getCode());
			}
		});

		ctrlItemReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlItemReferenceFrom.getSearchCondition().setCodeTo(
						ctrlItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemReferenceTo;
	}

	/**
	 * 開始フィールドで選択された科目Entityを返す
	 * @return 開始フィールドで選択された科目Entity
	 */
	public Item getEntityFrom() {
		return ctrlItemReferenceFrom.getEntity();
	}

	/**
	 * 終了フィールドで選択された科目Entityを返す
	 * @return 終了フィールドで選択された科目Entity
	 */
	public Item getEntityTo() {
		return ctrlItemReferenceTo.getEntity();
	}

}
