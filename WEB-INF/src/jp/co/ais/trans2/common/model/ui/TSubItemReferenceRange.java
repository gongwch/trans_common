package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �⏕�Ȗڔ͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TSubItemReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 2543858197500132048L;

	/** �J�n�t�B�[���h */
	public TSubItemReference ctrlSubItemReferenceFrom;

	/** �I���t�B�[���h */
	public TSubItemReference ctrlSubItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlSubItemReferenceFrom = new TSubItemReference();
		ctrlSubItemReferenceTo = new TSubItemReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlSubItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSubItemReferenceTo.getSearchCondition().setCodeFrom(ctrlSubItemReferenceFrom.getCode());
			}
		});

		ctrlSubItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSubItemReferenceFrom.getSearchCondition().setCodeTo(ctrlSubItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlSubItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlSubItemReferenceTo;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity��Ԃ�
	 * 
	 * @return �J�n�t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity
	 */
	public Item getEntityFrom() {
		return ctrlSubItemReferenceFrom.getEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity
	 */
	public Item getEntityTo() {
		return ctrlSubItemReferenceTo.getEntity();
	}

}
