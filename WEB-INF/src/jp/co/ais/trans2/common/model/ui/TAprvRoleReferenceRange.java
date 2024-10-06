package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 承認権限ロールマスタ範囲検索フィールド
 * 
 * @author AIS
 */
public class TAprvRoleReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8632104134307630490L;

	/** 開始フィールド */
	public TAprvRoleReference ctrlAprvRollReferenceFrom;

	/** 終了フィールド */
	public TAprvRoleReference ctrlAprvRollReferenceTo;

	@Override
	public void initComponents() {
		ctrlAprvRollReferenceFrom = new TAprvRoleReference();
		ctrlAprvRollReferenceTo = new TAprvRoleReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlAprvRollReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlAprvRollReferenceTo.getSearchCondition().setCodeFrom(ctrlAprvRollReferenceFrom.getCode());
			}
		});

		ctrlAprvRollReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlAprvRollReferenceFrom.getSearchCondition().setCodeTo(ctrlAprvRollReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlAprvRollReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlAprvRollReferenceTo;
	}

}
