package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * �v���O�������[���}�X�^�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TProgramRoleReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 8632104134307630490L;

	/** �J�n�t�B�[���h */
	public TProgramRoleReference ctrlProgramRollReferenceFrom;

	/** �I���t�B�[���h */
	public TProgramRoleReference ctrlProgramRollReferenceTo;

	@Override
	public void initComponents() {
		ctrlProgramRollReferenceFrom = new TProgramRoleReference();
		ctrlProgramRollReferenceTo = new TProgramRoleReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlProgramRollReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlProgramRollReferenceTo.getSearchCondition().setCodeFrom(ctrlProgramRollReferenceFrom.getCode());
			}
		});

		ctrlProgramRollReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlProgramRollReferenceFrom.getSearchCondition().setCodeTo(ctrlProgramRollReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlProgramRollReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlProgramRollReferenceTo;
	}

}
