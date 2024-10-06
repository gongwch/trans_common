package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * Voyage�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TVoyageReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TVoyageReference ctrlVoyageReferenceFrom;

	/** �I���t�B�[���h */
	public TVoyageReference ctrlVoyageReferenceTo;

	@Override
	public void initComponents() {
		ctrlVoyageReferenceFrom = new TVoyageReference();
		ctrlVoyageReferenceTo = new TVoyageReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlVoyageReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVoyageReferenceTo.getSearchCondition().setCodeFrom(ctrlVoyageReferenceFrom.getCode());
			}
		});

		ctrlVoyageReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVoyageReferenceFrom.getSearchCondition().setCodeTo(ctrlVoyageReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlVoyageReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlVoyageReferenceTo;
	}

}
