package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * ��s�x�X�͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TBranchReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TBranchReference ctrlBranchReferenceFrom;

	/** �I���t�B�[���h */
	public TBranchReference ctrlBranchReferenceTo;

	@Override
	public void initComponents() {
		ctrlBranchReferenceFrom = new TBranchReference();
		ctrlBranchReferenceTo = new TBranchReference();
	}

	/**
	 * ������
	 */
	protected void init() {

		ctrlBranchReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlBranchReferenceTo.getSearchCondition().setCodeFrom(
						ctrlBranchReferenceFrom.getCode());
			}
		});

		ctrlBranchReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlBranchReferenceFrom.getSearchCondition().setCodeTo(
						ctrlBranchReferenceTo.getCode());
			}
		});

	}

	@Override
	public TBranchReference getFieldFrom() {
		return ctrlBranchReferenceFrom;
	}

	@Override
	public TBranchReference getFieldTo() {
		return ctrlBranchReferenceTo;
	}

	/**
	 * ��s�R�[�h���Z�b�g����
	 * @param bankCode ��s�R�[�h
	 */
	public void setBankCode(String bankCode) {
		ctrlBranchReferenceFrom.getSearchCondition().setBankCode(bankCode);
		ctrlBranchReferenceTo.getSearchCondition().setBankCode(bankCode);
	}

}
