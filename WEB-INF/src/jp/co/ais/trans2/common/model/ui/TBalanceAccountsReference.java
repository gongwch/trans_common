package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 国際収支検索コンポーネント
 * 
 * @author AIS
 */
public class TBalanceAccountsReference extends TReference {

	/** コントローラ */
	protected TBalanceAccountsReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TBalanceAccountsReference() {
		this.controller = new TBalanceAccountsReferenceController(this);
		this.resize();
	}

	/**
	 * コードの長さを返す。デフォルト長と異なる検索フィールドは 当該メソッドをOverrideする。
	 * 
	 * @return コード長
	 */
	@Override
	protected int getCodeLength() {
		return 4;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public RemittanceSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Remittance getEntity() {
		return controller.getEntity();
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * Entityセット
	 * 
	 * @param remittance Entity
	 */
	public void setEntity(Remittance remittance) {
		controller.setEntity(remittance);
	}
}
