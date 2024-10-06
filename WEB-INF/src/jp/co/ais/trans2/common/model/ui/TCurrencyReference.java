package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 通貨の検索コンポーネント
 * 
 * @author AIS
 */
public class TCurrencyReference extends TReference {

	/** コントローラ */
	protected TCurrencyReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TCurrencyReference() {
		this(TYPE.BUTTON, "");
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TCurrencyReference(String title) {
		this(TYPE.BUTTON, title);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 */
	public TCurrencyReference(TYPE type) {
		this(type, "");
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 * @param title 外から設定したい場合のキャプション
	 */
	public TCurrencyReference(TYPE type, String title) {
		super(type, title);

		controller = createController();
	}

	/**
	 * コントローラ
	 * 
	 * @return コントローラ
	 */
	public TCurrencyReferenceController createController() {
		return new TCurrencyReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public CurrencySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Currency getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param currency エンティティ
	 */
	public void setEntity(Currency currency) {
		controller.setEntity(currency);
	}

	/**
	 * コードの長さを返す。デフォルト長と異なる検索フィールドは<br>
	 * 当該メソッドをOverrideする。
	 * 
	 * @return コード長
	 */
	@Override
	protected int getCodeLength() {
		return TransUtil.CURRENCY_CODE_LENGTH;
	}

	/**
	 * 現在の入力値でエンティティを再設定する
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.<br>
	 * 表示を更新する
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}
}
