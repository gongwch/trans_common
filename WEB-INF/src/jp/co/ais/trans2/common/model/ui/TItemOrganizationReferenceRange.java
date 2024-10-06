package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * �Ȗڑ̌n���͈̔͌����t�B�[���h
 * 
 * @author AIS
 */
public class TItemOrganizationReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TItemOrganizationReference ctrlItemOrReferenceFrom;

	/** �I���t�B�[���h */
	public TItemOrganizationReference ctrlItemOrgReferenceTo;

	@Override
	public void initComponents() {
		ctrlItemOrReferenceFrom = new TItemOrganizationReference();
		ctrlItemOrgReferenceTo = new TItemOrganizationReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlItemOrReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemOrgReferenceTo.getSearchCondition().setCodeFrom(ctrlItemOrReferenceFrom.getCode());
			}
		});

		ctrlItemOrgReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemOrReferenceFrom.getSearchCondition().setCodeTo(ctrlItemOrgReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemOrReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemOrgReferenceTo;
	}

}
