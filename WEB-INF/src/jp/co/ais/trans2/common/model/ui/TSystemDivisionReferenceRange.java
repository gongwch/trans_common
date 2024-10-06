package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * �V�X�e���敪�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TSystemDivisionReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TSystemDivisionReference ctrlSysDivReferenceFrom;

	/** �I���t�B�[���h */
	public TSystemDivisionReference ctrlSysDivReferenceTo;

	@Override
	public void initComponents() {
		ctrlSysDivReferenceFrom = new TSystemDivisionReference();
		ctrlSysDivReferenceTo = new TSystemDivisionReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlSysDivReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSysDivReferenceTo.getSearchCondition().setCodeFrom(ctrlSysDivReferenceFrom.getCode());
			}
		});

		ctrlSysDivReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSysDivReferenceFrom.getSearchCondition().setCodeTo(ctrlSysDivReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlSysDivReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlSysDivReferenceTo;
	}

}
