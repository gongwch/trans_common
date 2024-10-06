package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 取引先検索コンポーネント
 * 
 * @author AIS
 */
public class TCustomerReference extends TReference {

	/** コントローラ */
	protected TCustomerReferenceController controller;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TCustomerReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 */
	public TCustomerReference() {
		super();
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TCustomerReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TCustomerReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	protected TCustomerReferenceController createController() {
		return new TCustomerReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public CustomerSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Customer getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param customer エンティティ
	 */
	public void setEntity(Customer customer) {
		controller.setEntity(customer);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
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

	/**
	 * 検索条件の初期化
	 */
	public void initSearchCondition() {
		controller.initSearchCondition();
	}

	/**
	 * true:全SPCモードの取得
	 * 
	 * @return allCompanyMode true:全SPCモード
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:全SPCモードの設定
	 * 
	 * @param allCompanyMode true:全SPCモード
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

}
