package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ����Ȗڔ͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TDetailItemReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 2543858197500132048L;

	/** �J�n�t�B�[���h */
	public TDetailItemReference ctrlDetailItemReferenceFrom;

	/** �I���t�B�[���h */
	public TDetailItemReference ctrlDetailItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlDetailItemReferenceFrom = new TDetailItemReference();
		ctrlDetailItemReferenceTo = new TDetailItemReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlDetailItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlDetailItemReferenceTo.getSearchCondition().setCodeFrom(ctrlDetailItemReferenceFrom.getCode());
			}
		});

		ctrlDetailItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlDetailItemReferenceFrom.getSearchCondition().setCodeTo(ctrlDetailItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlDetailItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlDetailItemReferenceTo;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ����Ȗ�Entity��Ԃ�
	 * 
	 * @return �J�n�t�B�[���h�őI�����ꂽ����Ȗ�Entity
	 */
	public Item getEntityFrom() {
		return ctrlDetailItemReferenceFrom.getEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ����Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ����Ȗ�Entity
	 */
	public Item getEntityTo() {
		return ctrlDetailItemReferenceTo.getEntity();
	}

}
