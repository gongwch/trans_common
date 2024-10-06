package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * ���F�������[���}�X�^�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TAprvRoleReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8632104134307630490L;

	/** �J�n�t�B�[���h */
	public TAprvRoleReference ctrlAprvRollReferenceFrom;

	/** �I���t�B�[���h */
	public TAprvRoleReference ctrlAprvRollReferenceTo;

	@Override
	public void initComponents() {
		ctrlAprvRollReferenceFrom = new TAprvRoleReference();
		ctrlAprvRollReferenceTo = new TAprvRoleReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlAprvRollReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlAprvRollReferenceTo.getSearchCondition().setCodeFrom(ctrlAprvRollReferenceFrom.getCode());
			}
		});

		ctrlAprvRollReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlAprvRollReferenceFrom.getSearchCondition().setCodeTo(ctrlAprvRollReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlAprvRollReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlAprvRollReferenceTo;
	}

}
