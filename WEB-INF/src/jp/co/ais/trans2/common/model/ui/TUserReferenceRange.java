package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * ���[�U�[�͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TUserReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -6373347408552212634L;

	/** �J�n�t�B�[���h */
	public TUserReference ctrlUserReferenceFrom;

	/** �I���t�B�[���h */
	public TUserReference ctrlUserReferenceTo;

	@Override
	public void initComponents() {
		ctrlUserReferenceFrom = new TUserReference();
		ctrlUserReferenceTo = new TUserReference();
	}

	/**
	 * ������
	 */
	protected void init() {

		ctrlUserReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlUserReferenceTo.getSearchCondition().setCodeFrom(
						ctrlUserReferenceFrom.getCode());
			}
		});

		ctrlUserReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlUserReferenceFrom.getSearchCondition().setCodeTo(
						ctrlUserReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlUserReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlUserReferenceTo;
	}

}
