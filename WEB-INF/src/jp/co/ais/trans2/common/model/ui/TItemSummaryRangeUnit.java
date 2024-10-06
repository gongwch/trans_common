package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * 科目体系&科目範囲検索
 * 
 * @author AIS
 */
public class TItemSummaryRangeUnit extends TReferenceTripleRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** Ref Controller */
	public TItemSummaryRangeUnitController ctrl;

	/** 科目体系フィールド */
	public TItemOrganizationReference ctrlItemOrgRef;

	/** 開始フィールド */
	public TItemSummaryReference ctrlItemRefFrom;

	/** 終了フィールド */
	public TItemSummaryReference ctrlItemRefTo;

	/**
	 * 初期化
	 */
	@Override
	public void initComponents() {

		ctrlItemOrgRef = new TItemOrganizationReference();
		ctrlItemRefFrom = new TItemSummaryReference();
		ctrlItemRefTo = new TItemSummaryReference();

		ctrl = new TItemSummaryRangeUnitController(this);
	}

	@Override
	public TReference getFieldUp() {
		return ctrlItemOrgRef;
	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemRefFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemRefTo;
	}

	/**
	 * 開始フィールドで選択された科目Entityを返す
	 * 
	 * @return 開始フィールドで選択された科目Entity
	 */
	public ItemSummary getEntityFrom() {
		return ctrlItemRefFrom.getEntity();
	}

	/**
	 * 終了フィールドで選択された科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された科目Entity
	 */
	public ItemSummary getEntityTo() {
		return ctrlItemRefTo.getEntity();
	}
}