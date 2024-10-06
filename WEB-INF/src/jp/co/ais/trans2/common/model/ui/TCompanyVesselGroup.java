package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 会社検索、船検索グループコンポーネント
 * 
 * @author AIS
 */
public class TCompanyVesselGroup extends TPanel {

	/** Companyフィールド */
	public TCompanyReference ctrlCompany;

	/** Vesselフィールド */
	public TVesselReference ctrlVessel;

	/** コントローラ */
	public TCompanyVesselGroupController controller;

	/**
	 * コンストラクタ
	 */
	public TCompanyVesselGroup() {
		this(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isShipBuildEntry 造船契約登録済み対象かどうか<br>
	 *            true:造船契約登録済みの船のみ対象、false:全データ対象
	 */
	public TCompanyVesselGroup(boolean isShipBuildEntry) {
		this(isShipBuildEntry, false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isShipBuildEntry 造船契約登録済み対象かどうか<br>
	 *            true:造船契約登録済みの船のみ対象、false:全データ対象
	 * @param useDefaultSize true:標準サイズをそのまま利用
	 */
	public TCompanyVesselGroup(boolean isShipBuildEntry, boolean useDefaultSize) {

		// コンポーネントを初期化する
		initComponents(isShipBuildEntry);

		// コンポーネントを配置する
		allocateComponents(useDefaultSize);
		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return TOwnerVesselGroupController
	 */
	public TCompanyVesselGroupController createController() {
		// コントローラ生成
		return new TCompanyVesselGroupController(this);
	}

	/**
	 * コンポーネントを初期化する
	 * 
	 * @param isShipBuildEntry 造船契約登録済みのみ対象か
	 */
	protected void initComponents(boolean isShipBuildEntry) {
		ctrlCompany = new TCompanyReference();
		ctrlVessel = new TVesselReference(isShipBuildEntry);
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {
		allocateComponents(false);
	}

	/**
	 * コンポーネントを配置する
	 * 
	 * @param useDefaultSize true:標準サイズをそのまま利用
	 */
	protected void allocateComponents(boolean useDefaultSize) {

		setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();

		if (!useDefaultSize) {
			setButtonSize(125);
			setCodeSize(125);
			setNameSize(230);
		}

		// 会社
		ctrlCompany.btn.setLangMessageID("C00053");
		gb.gridx = 0;
		gb.gridy = 0;
		gb.anchor = GridBagConstraints.WEST;
		gb.insets = new Insets(0, 0, 0, 0);
		add(ctrlCompany, gb);

		// Vessel
		ctrlVessel.btn.setLangMessageID("C00466");
		gb = new GridBagConstraints();
		gb.gridx = 0;
		gb.gridy = 1;
		gb.anchor = GridBagConstraints.WEST;
		gb.insets = new Insets(0, 0, 0, 0);
		add(ctrlVessel, gb);

		setSize(
			ctrlCompany.getWidth(),
			ctrlCompany.getHeight() + ctrlCompany.getInsets().top + ctrlCompany.getInsets().bottom
				+ ctrlVessel.getHeight() + ctrlVessel.getInsets().top + ctrlVessel.getInsets().bottom);

	}

	/**
	 * ボタン幅を設定する
	 * 
	 * @param width
	 */
	public void setButtonSize(int width) {
		ctrlCompany.setButtonSize(width);
		ctrlVessel.setButtonSize(width);
	}

	/**
	 * コードの幅を設定する
	 * 
	 * @param width
	 */
	public void setCodeSize(int width) {
		ctrlCompany.setCodeSize(width);
		ctrlVessel.setCodeSize(width);
	}

	/**
	 * 名称の幅を設定する
	 * 
	 * @param width
	 */
	public void setNameSize(int width) {
		ctrlCompany.setNameSize(width);
		ctrlVessel.setNameSize(width);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlCompany.setTabControlNo(tabControlNo);
		ctrlVessel.setTabControlNo(tabControlNo);
	}

	/**
	 * 選択された船を返す
	 * 
	 * @return 船情報
	 */
	public Vessel getVesselEntity() {
		return controller.getVesselEntity();
	}

	/**
	 * 選択されたCompanyを返す
	 * 
	 * @return 選択されたCompany
	 */
	public Company getCompanyEntity() {
		return controller.getCompanyEntity();
	}

	/**
	 * 船を設定する
	 * 
	 * @param bean
	 */
	public void setVesselEntity(Vessel bean) {
		controller.setVesselEntity(bean);
	}

	/**
	 * 会社を設定する
	 * 
	 * @param bean Company
	 */
	public void setCompanyEntity(Company bean) {
		controller.setCompanyEntity(bean);
	}

	/**
	 * 検索条件のgetter
	 * 
	 * @return 検索条件
	 */
	public CompanySearchCondition getCompanySearchCondition() {
		return controller.getCompanySearchCondition();
	}

	/**
	 * 船の検索条件getter
	 * 
	 * @return 検索条件
	 */
	public VesselSearchCondition getVesselSearchCondition() {
		return controller.getVesselSearchCondition();
	}

	/**
	 * クリア
	 */
	public void clear() {
		controller.clear();
	}

	/**
	 * コールバックリスナー設定
	 * 
	 * @param listener コールバックリスナー
	 */
	public void addCallBackListener(TCallBackListener listener) {
		ctrlCompany.addCallBackListener(listener);
		ctrlVessel.addCallBackListener(listener);
	}

	@Override
	public void requestFocus() {
		ctrlCompany.requestTextFocus();
	}

	/**
	 * 会社コードを取得する
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return ctrlCompany.getCode();
	}

	/**
	 * 船コードを取得する
	 * 
	 * @return Vesselコード
	 */
	public String getVesselCode() {
		return ctrlVessel.getCode();
	}

	/**
	 * 船名称を取得する
	 * 
	 * @return Vessel名称
	 */
	public String getVesselName() {
		return ctrlVessel.getNames();
	}
}
