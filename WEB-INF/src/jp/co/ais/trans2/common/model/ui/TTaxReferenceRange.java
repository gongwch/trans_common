package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;
import jp.co.ais.trans2.model.tax.*;

/**
 * ����Ŕ͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TTaxReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TTaxReference ctrlTaxReferenceFrom;

	/** �I���t�B�[���h */
	public TTaxReference ctrlTaxReferenceTo;

	@Override
	public void initComponents() {
		ctrlTaxReferenceFrom = new TTaxReference();
		ctrlTaxReferenceTo = new TTaxReference();
	}
	/**
	 * ������
	 */
	protected void init() {

		ctrlTaxReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlTaxReferenceTo.getSearchCondition().setCodeFrom(
						ctrlTaxReferenceFrom.getCode());
			}
		});

		ctrlTaxReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlTaxReferenceFrom.getSearchCondition().setCodeTo(
						ctrlTaxReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlTaxReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlTaxReferenceTo;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * @return �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public ConsumptionTax getEntityFrom() {
		return ctrlTaxReferenceFrom.getEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public ConsumptionTax getEntityTo() {
		return ctrlTaxReferenceTo.getEntity();
	}

}
