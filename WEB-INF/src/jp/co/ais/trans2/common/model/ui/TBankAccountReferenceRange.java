package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * ��s�����͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TBankAccountReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TBankAccountReference ctrlBankAccountReferenceFrom;

	/** �I���t�B�[���h */
	public TBankAccountReference ctrlBankAccountReferenceTo;

	/**
	 * @param isVisibleAccountNo
	 */
	public TBankAccountReferenceRange(boolean isVisibleAccountNo) {
		super();
		ctrlBankAccountReferenceFrom.accountNo.setVisible(isVisibleAccountNo);
		ctrlBankAccountReferenceTo.accountNo.setVisible(isVisibleAccountNo);
		
		ctrlBankAccountReferenceFrom.resize();
		ctrlBankAccountReferenceTo.resize();
		
		reSize();
	}

	/**
	 * 
	 */
	public TBankAccountReferenceRange() {
		this(false);
	}

	@Override
	public void initComponents() {
		ctrlBankAccountReferenceFrom = new TBankAccountReference();
		ctrlBankAccountReferenceTo = new TBankAccountReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlBankAccountReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlBankAccountReferenceTo.getSearchCondition().setCodeFrom(ctrlBankAccountReferenceFrom.getCode());
			}
		});

		ctrlBankAccountReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlBankAccountReferenceFrom.getSearchCondition().setCodeTo(ctrlBankAccountReferenceTo.getCode());
			}
		});

	}

	@Override
	public TBankAccountReference getFieldFrom() {
		return ctrlBankAccountReferenceFrom;
	}

	@Override
	public TBankAccountReference getFieldTo() {
		return ctrlBankAccountReferenceTo;
	}

}
