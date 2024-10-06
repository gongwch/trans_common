package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * �V�X�e���敪�͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TSystemClassificationReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -6373347408552212634L;

	/** �J�n�t�B�[���h */
	public TSystemClassificationReference ctrlSysReferenceFrom;

	/** �I���t�B�[���h */
	public TSystemClassificationReference ctrlSysReferenceTo;

	@Override
	public void initComponents() {
		ctrlSysReferenceFrom = new TSystemClassificationReference();
		ctrlSysReferenceTo = new TSystemClassificationReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlSysReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlSysReferenceTo.getSearchCondition().setCodeFrom(
						ctrlSysReferenceFrom.getCode());
			}
		});

		ctrlSysReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlSysReferenceFrom.getSearchCondition().setCodeTo(
						ctrlSysReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlSysReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlSysReferenceTo;
	}

}
