package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 承認権限ロールグループマスタ範囲検索フィールド
 * 
 * @author AIS
 */
public class TAprvRoleGroupReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TAprvRoleGroupReference ctrlFrom;

	/** 終了フィールド */
	public TAprvRoleGroupReference ctrlTo;

	@Override
	public void initComponents() {
		ctrlFrom = new TAprvRoleGroupReference();
		ctrlTo = new TAprvRoleGroupReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlTo.getSearchCondition().setCodeFrom(ctrlFrom.getCode());
			}
		});

		ctrlTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlFrom.getSearchCondition().setCodeTo(ctrlTo.getCode());
			}
		});

	}

	@Override
	public TAprvRoleGroupReference getFieldFrom() {
		return ctrlFrom;
	}

	@Override
	public TAprvRoleGroupReference getFieldTo() {
		return ctrlTo;
	}

}
