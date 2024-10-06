package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.voyage.*;

/**
 * Voyageの検索コンポーネント
 * 
 * @author AIS
 */
public class TVoyageReference extends TReference {

	/** コントローラ */
	protected TVoyageReferenceController controller;

	/** 会社コード */
	protected String companyCode;

	/**
	 * コンストラクタ
	 */
	public TVoyageReference() {
		super();
		initController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 */
	public TVoyageReference(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * コントローラ生成
	 */
	protected void initController() {
		// コントローラ生成
		if (controller == null) {
			controller = new TVoyageReferenceController(this);
		}
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
	public VoyageSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public Voyage getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param voyage エンティティ
	 */
	public void setEntity(Voyage voyage) {
		controller.setEntity(voyage);
	}

	/**
	 * 会社コードを設定します。
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 会社コードを取得します。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 会社コード検証するかの取得
	 * 
	 * @return verifyCompanyCode 会社コード検証するか
	 */
	public boolean isVerifyCompanyCode() {
		return controller.isVerifyCompanyCode();
	}

	/**
	 * 会社コード検証するかの設定
	 * 
	 * @param verifyCompanyCode 会社コード検証するか
	 */
	public void setVerifyCompanyCode(boolean verifyCompanyCode) {
		controller.setVerifyCompanyCode(verifyCompanyCode);
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
