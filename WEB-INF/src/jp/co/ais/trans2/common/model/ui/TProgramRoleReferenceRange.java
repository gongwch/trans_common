package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * プログラムロールマスタ範囲検索フィールド
 * 
 * @author AIS
 */
public class TProgramRoleReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8632104134307630490L;

	/** 開始フィールド */
	public TProgramRoleReference ctrlProgramRollReferenceFrom;

	/** 終了フィールド */
	public TProgramRoleReference ctrlProgramRollReferenceTo;

	@Override
	public void initComponents() {
		ctrlProgramRollReferenceFrom = new TProgramRoleReference();
		ctrlProgramRollReferenceTo = new TProgramRoleReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlProgramRollReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlProgramRollReferenceTo.getSearchCondition().setCodeFrom(ctrlProgramRollReferenceFrom.getCode());
			}
		});

		ctrlProgramRollReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlProgramRollReferenceFrom.getSearchCondition().setCodeTo(ctrlProgramRollReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlProgramRollReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlProgramRollReferenceTo;
	}

}
