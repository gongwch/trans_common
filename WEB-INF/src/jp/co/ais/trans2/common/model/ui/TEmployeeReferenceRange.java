package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * �Ј��͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TEmployeeReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TEmployeeReference ctrlEmployeeReferenceFrom;

	/** �I���t�B�[���h */
	public TEmployeeReference ctrlEmployeeReferenceTo;

	@Override
	public void initComponents() {
		ctrlEmployeeReferenceFrom = new TEmployeeReference();
		ctrlEmployeeReferenceTo = new TEmployeeReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlEmployeeReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlEmployeeReferenceTo.getSearchCondition().setCodeFrom(ctrlEmployeeReferenceFrom.getCode());
			}
		});

		ctrlEmployeeReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlEmployeeReferenceFrom.getSearchCondition().setCodeTo(ctrlEmployeeReferenceTo.getCode());
			}
		});

	}

	@Override
	public TEmployeeReference getFieldFrom() {
		return ctrlEmployeeReferenceFrom;
	}

	@Override
	public TEmployeeReference getFieldTo() {
		return ctrlEmployeeReferenceTo;
	}

}
