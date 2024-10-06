package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * �v���O�����͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TProgramReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8632104134307630490L;

	/** �J�n�t�B�[���h */
	public TProgramReference ctrlProgramReferenceFrom;

	/** �I���t�B�[���h */
	public TProgramReference ctrlProgramReferenceTo;

	@Override
	public void initComponents() {
		ctrlProgramReferenceFrom = new TProgramReference();
		ctrlProgramReferenceTo = new TProgramReference();
	}

	/**
	 * ������
	 */
	protected void init() {

		ctrlProgramReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlProgramReferenceTo.getSearchCondition().setCodeFrom(
						ctrlProgramReferenceFrom.getCode());
			}
		});

		ctrlProgramReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlProgramReferenceFrom.getSearchCondition().setCodeTo(
						ctrlProgramReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlProgramReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlProgramReferenceTo;
	}

}
