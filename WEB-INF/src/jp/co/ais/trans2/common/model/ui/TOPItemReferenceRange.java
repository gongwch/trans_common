package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * SA�A�C�e���͈�
 */
public class TOPItemReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TOPItemReference from;

	/** �I���t�B�[���h */
	public TOPItemReference to;

	@Override
	public void initComponents() {
		from = new TOPItemReference();
		to = new TOPItemReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		from.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				to.getSearchCondition().setCodeFrom(from.getCode());
			}
		});

		to.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				from.getSearchCondition().setCodeTo(to.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return from;
	}

	@Override
	public TReference getFieldTo() {
		return to;
	}

}
