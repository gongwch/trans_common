package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * 銀行支店範囲検索フィールド
 * @author AIS
 *
 */
public class TBranchReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TBranchReference ctrlBranchReferenceFrom;

	/** 終了フィールド */
	public TBranchReference ctrlBranchReferenceTo;

	@Override
	public void initComponents() {
		ctrlBranchReferenceFrom = new TBranchReference();
		ctrlBranchReferenceTo = new TBranchReference();
	}

	/**
	 * 初期化
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
	 * 銀行コードをセットする
	 * @param bankCode 銀行コード
	 */
	public void setBankCode(String bankCode) {
		ctrlBranchReferenceFrom.getSearchCondition().setBankCode(bankCode);
		ctrlBranchReferenceTo.getSearchCondition().setBankCode(bankCode);
	}

}
