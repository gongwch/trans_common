package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Portの検索コンポーネント
 * 
 * @author AIS
 */
public class TPortReference extends TReference {

	/** コントローラ */
	protected TPortReferenceController controller;

	/** 会社コード */
	protected String companyCode;

	/**
	 * コンストラクタ
	 */
	public TPortReference() {
		super();
		initController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 */
	public TPortReference(String companyCode) {
		this.companyCode = companyCode;
		initController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TPortReference(TYPE type) {
		super(type);
		initController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TPortReference(TYPE type, String title) {
		super(type, title);
		initController();
	}

	/**
	 * コントローラ生成
	 */
	protected void initController() {
		// コントローラ生成
		if (controller == null) {
			controller = new TPortReferenceController(this);
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
	public PortSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public Port getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param port エンティティ
	 */
	public void setEntity(Port port) {
		controller.setEntity(port);
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
