package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;
import jp.co.ais.trans2.model.item.Item;

/**
 * �Ȗڔ͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TItemReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 2543858197500132048L;

	/** �J�n�t�B�[���h */
	public TItemReference ctrlItemReferenceFrom;

	/** �I���t�B�[���h */
	public TItemReference ctrlItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlItemReferenceFrom = new TItemReference();
		ctrlItemReferenceTo = new TItemReference();
	}
	/**
	 * ������
	 */
	protected void init() {

		ctrlItemReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlItemReferenceTo.getSearchCondition().setCodeFrom(
						ctrlItemReferenceFrom.getCode());
			}
		});

		ctrlItemReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlItemReferenceFrom.getSearchCondition().setCodeTo(
						ctrlItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemReferenceTo;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * @return �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public Item getEntityFrom() {
		return ctrlItemReferenceFrom.getEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * @return �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public Item getEntityTo() {
		return ctrlItemReferenceTo.getEntity();
	}

}
