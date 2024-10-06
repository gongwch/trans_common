package jp.co.ais.trans2.common.model.ui.payment;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払方法検索コンポーネント
 * 
 * @author AIS
 */
public class TPaymentMethodReference extends TReference {

	/** コントローラ */
	protected TPaymentMethodReferenceController controller;

	/** 銀行口座フィールド（支払方法内部コード 連動用） */
	protected TBankAccountReference ctrlBankAccount = null;

	/** エラー支払方法内部コード */
	protected PaymentKind[] errPaymentKinds = null;

	/**
	 * 
	 *
	 */
	public TPaymentMethodReference() {
		createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TPaymentMethodReference(TYPE type) {
		super(type);
		createController();
	}

	/**
	 * コントローラの作成
	 */
	protected void createController() {
		controller = new TPaymentMethodReferenceController(this);
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public PaymentMethodSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public PaymentMethod getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param PaymentMethod 支払方法
	 */
	public void setEntity(PaymentMethod PaymentMethod) {
		controller.setEntity(PaymentMethod);
		controller.checkPaymentKind();
	}

	/**
	 * 銀行口座の入力が必要な支払方法かどうか
	 * 
	 * @return 銀行口座の入力が必要な支払方法かどうか
	 */
	public boolean isNeedInputAccount() {
		return controller.isNeedInputAccount();
	}

	/**
	 * 初期化処理
	 */
	@Override
	public void clear() {
		super.clear();
		controller.checkPaymentKind();
	}

	/**
	 * 銀行口座フィールドを設定する。
	 * 
	 * @param ctrlBankAccount
	 */
	public void setBankAccountReference(TBankAccountReference ctrlBankAccount) {
		this.ctrlBankAccount = ctrlBankAccount;
	}

	/**
	 * エラー支払方法内部コードを設定する。<br>
	 * 入力値が指定の支払方法内部コードのいずれかと一致する場合、連携する銀行口座フィールドを入力不可にする。
	 * 
	 * @param paymentKinds
	 */
	public void setErrPaymentKinds(PaymentKind... paymentKinds) {
		this.errPaymentKinds = paymentKinds;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}
