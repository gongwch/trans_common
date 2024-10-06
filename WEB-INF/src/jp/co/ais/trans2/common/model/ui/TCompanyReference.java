package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社の検索コンポーネント
 * 
 * @author AIS
 */
public class TCompanyReference extends TReference {

	/** コントローラ */
	protected TCompanyReferenceController controller;

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TCompanyReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクター
	 */
	public TCompanyReference() {

		// コントローラ生成
		controller = createController();
	}

	/**
	 * コントローラの作成
	 * 
	 * @return コントローラ
	 */
	protected TCompanyReferenceController createController() {
		return new TCompanyReferenceController(this);
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
	public CompanySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public Company getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param company エンティティ
	 */
	public void setEntity(Company company) {
		controller.setEntity(company);
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
}
