package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * �Ȗڑ̌n&�Ȗڔ͈͌���
 * 
 * @author AIS
 */
public class TItemSummaryRangeUnit extends TReferenceTripleRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** Ref Controller */
	public TItemSummaryRangeUnitController ctrl;

	/** �Ȗڑ̌n�t�B�[���h */
	public TItemOrganizationReference ctrlItemOrgRef;

	/** �J�n�t�B�[���h */
	public TItemSummaryReference ctrlItemRefFrom;

	/** �I���t�B�[���h */
	public TItemSummaryReference ctrlItemRefTo;

	/**
	 * ������
	 */
	@Override
	public void initComponents() {

		ctrlItemOrgRef = new TItemOrganizationReference();
		ctrlItemRefFrom = new TItemSummaryReference();
		ctrlItemRefTo = new TItemSummaryReference();

		ctrl = new TItemSummaryRangeUnitController(this);
	}

	@Override
	public TReference getFieldUp() {
		return ctrlItemOrgRef;
	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemRefFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemRefTo;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public ItemSummary getEntityFrom() {
		return ctrlItemRefFrom.getEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public ItemSummary getEntityTo() {
		return ctrlItemRefTo.getEntity();
	}
}