package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �A�����э��͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TMlitRegionReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TMlitRegionReference ctrlRegionReferenceFrom;

	/** �I���t�B�[���h */
	public TMlitRegionReference ctrlRegionReferenceTo;

	@Override
	public void initComponents() {
		ctrlRegionReferenceFrom = new TMlitRegionReference();
		ctrlRegionReferenceTo = new TMlitRegionReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlRegionReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRegionReferenceTo.getSearchCondition().setRegionCodeFrom(ctrlRegionReferenceFrom.getCode());
			}
		});

		ctrlRegionReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRegionReferenceFrom.getSearchCondition().setRegionCodeTo(ctrlRegionReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlRegionReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlRegionReferenceTo;
	}
}
