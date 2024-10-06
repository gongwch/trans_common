package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * ���[�U�[���[���}�X�^�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TUserRoleReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8632104134307630490L;

	/** �J�n�t�B�[���h */
	public TUserRoleReference ctrlUserRollReferenceFrom;

	/** �I���t�B�[���h */
	public TUserRoleReference ctrlUserRollReferenceTo;

	@Override
	public void initComponents() {
		ctrlUserRollReferenceFrom = new TUserRoleReference();
		ctrlUserRollReferenceTo = new TUserRoleReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlUserRollReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlUserRollReferenceTo.getSearchCondition().setCodeFrom(ctrlUserRollReferenceFrom.getCode());
			}
		});

		ctrlUserRollReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlUserRollReferenceFrom.getSearchCondition().setCodeTo(ctrlUserRollReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlUserRollReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlUserRollReferenceTo;
	}

}
