package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 科目組織レベルの範囲ユニット<br>
 * (表示レベル+科目体系+科目範囲+科目任意指定)
 * 
 * @author AIS
 */
public class TItemOrganizationUnit extends TTitlePanel {

	/** 表示レベル */
	public TItemLevelChooser ctrlItemLevelChooser;

	/** 科目体系 */
	public TItemOrganizationReference ctrlItemOrganization;

	/** 科目範囲 */
	public TItemGroupRangeUnit ctrlItemRange;

	/** コントローラ */
	public TItemOrganizationUnitController controller;

	/** タイトル default:科目範囲 */
	protected String title = "C01009";

	/**
	 * コンストラクタ
	 */
	public TItemOrganizationUnit() {
		this(true);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isTitle タイトル表示かどうか
	 */
	public TItemOrganizationUnit(boolean isTitle) {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents(isTitle);

		// コントローラ生成
		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	public TItemOrganizationUnitController createController() {
		return new TItemOrganizationUnitController(this);
	}

	/**
	 * コンポーネントを初期化する
	 */
	protected void initComponents() {
		ctrlItemLevelChooser = new TItemLevelChooser(SwingConstants.HORIZONTAL);
		ctrlItemOrganization = new TItemOrganizationReference();
		ctrlItemRange = new TItemGroupRangeUnit();
	}

	/**
	 * コンポーネントを配置する
	 * 
	 * @param isTitle タイトル表示かどうか
	 */
	protected void allocateComponents(boolean isTitle) {

		int x = 15;
		int y = 5;
		int yy = 0;

		setSize(365, isTitle ? 260 : 240);

		if (isTitle) {
			setLangMessageID(title);
		} else {
			setLangMessageID(title);
			this.titlePanel.setVisible(false);
		}

		// 表示レベル
		if (isTitle) {
			ctrlItemLevelChooser.setLocation(x, y);
		} else {
			ctrlItemLevelChooser.setLocation(x, yy + y);
		}
		add(ctrlItemLevelChooser);

		yy = ctrlItemLevelChooser.getY() + ctrlItemLevelChooser.getHeight() + y;

		// 科目体系
		if (isTitle) {
			ctrlItemOrganization.setLocation(x, yy);
		} else {
			ctrlItemOrganization.setLocation(x, yy);
		}
		add(ctrlItemOrganization);

		yy = ctrlItemOrganization.getY() + ctrlItemOrganization.getHeight() + y;

		// 科目範囲
		if (isTitle) {
			ctrlItemRange.setLocation(x, yy);
		} else {
			ctrlItemRange.setLocation(x, yy);
		}
		add(ctrlItemRange);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemLevelChooser.setTabControlNo(tabControlNo);
		ctrlItemOrganization.setTabControlNo(tabControlNo);
		ctrlItemRange.setTabControlNo(tabControlNo);
	}

	/**
	 * タイトルを設定する
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * タイトルを取得する
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

}
