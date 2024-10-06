package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vesselの検索コンポーネント
 * 
 * @author AIS
 */
public class TVesselReference extends TReference {

	/** コントローラ */
	protected TVesselReferenceController controller;

	/** 会社コード */
	protected String companyCode;

	/** 造船契約情報に登録済み true:造船契約情報に存在するVesselのみ検索対象 */
	protected boolean isShipBuildEntry = false;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクタ
	 */
	public TVesselReference() {
		super();
		initController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 */
	public TVesselReference(String companyCode) {
		this(companyCode, false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isShipBuildEntry 造船契約登録用か<br/>
	 *            true：造船契約情報に存在するVesselのみ検索の対象となる
	 */
	public TVesselReference(boolean isShipBuildEntry) {
		this(null, isShipBuildEntry);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 * @param isShipBuildEntry 造船契約登録用か<br/>
	 *            true：造船契約情報に存在するVesselのみ検索の対象となる
	 */
	public TVesselReference(String companyCode, boolean isShipBuildEntry) {
		this();
		this.isShipBuildEntry = isShipBuildEntry;
		this.companyCode = companyCode;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TVesselReference(TYPE type) {
		super(type);
		initController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TVesselReference(TYPE type, String title) {
		super(type, title);
		initController();
	}

	/**
	 * コントローラ生成
	 */
	protected void initController() {
		// コントローラ生成
		if (controller == null) {
			controller = new TVesselReferenceController(this);
		}
	}

	/**
	 * コントローラのファクトリ
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
	public VesselSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public Vessel getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param vessel エンティティ
	 */
	public void setEntity(Vessel vessel) {
		controller.setEntity(vessel);
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
	 * 造船契約情報に存在するVesselのみ検索対象とするか
	 * 
	 * @return boolean
	 */
	public boolean isShipBuildEntry() {
		return this.isShipBuildEntry;
	}

	/**
	 * 造船契約情報に存在するVesselのみ検索対象とするか
	 * 
	 * @param isShipBuildEntry true：する
	 */
	public void setShipBuildEntry(boolean isShipBuildEntry) {
		this.isShipBuildEntry = isShipBuildEntry;
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
