package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 組織(会社階層)の出力単位ユニットコンポーネント
 * 
 * @author AIS
 */
public class TCompanyOrganizationUnit extends TTitlePanel {

	/** 組織名称表示 */
	public static int DISPLAY_NAME_FLG = 0;

	/** 組織コンボボックス */
	public TCompanyOrganizationComboBox cboCompanyOrganization;

	/** 階層レベル */
	public TLabelComboBox cboLevel;

	/** 配下会社パネル */
	public TPanel pnlLowCompany;

	/** 配下部門ラベル */
	public TLabel lblLowCompany;

	/** 含む */
	public TRadioButton rdoInclude;

	/** 含まない */
	public TRadioButton rdoNotInclude;

	/** ボタングループ */
	public ButtonGroup btnGroup;

	/** 上位会社 */
	public TCompanyReference ctrlSuperiorCompany;

	/** 会社 */
	public TCompanyReferenceRangeUnit ctrlCompany;

	/** コントローラ */
	public TCompanyOrganizationUnitController controller;

	/**
	 * 
	 *
	 */
	public TCompanyOrganizationUnit() {
		this(true);
	}

	/**
	 * @param title タイトル表示かどうか
	 */
	public TCompanyOrganizationUnit(boolean title) {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents(title);

		// コントローラ生成
		controller = createController();

	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	public TCompanyOrganizationUnitController createController() {
		return new TCompanyOrganizationUnitController(this);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		cboCompanyOrganization = new TCompanyOrganizationComboBox();
		cboLevel = new TLabelComboBox();
		pnlLowCompany = new TPanel();
		lblLowCompany = new TLabel();
		rdoInclude = new TRadioButton();
		rdoNotInclude = new TRadioButton();
		ctrlSuperiorCompany = new TCompanyReference();
		ctrlCompany = new TCompanyReferenceRangeUnit();
		btnGroup = new ButtonGroup();
	}

	static {
		try {
			DISPLAY_NAME_FLG = Util.avoidNullAsInt(ClientConfig.getProperty("trans.output.unit.display.name.flg"));
		} catch (Exception ex) {

			// エラーなし
		}
	}

	/**
	 * コンポーネントを配置する
	 * 
	 * @param title タイトル表示かどうか
	 */
	protected void allocateComponents(boolean title) {

		setSize(365, 165);

		if (title) {
			setLangMessageID("C11922"); // 出力単位(会社階層)
		} else {
			setLangMessageID("C11922"); // 出力単位(会社階層)
			this.titlePanel.setVisible(false);
		}
		switch (DISPLAY_NAME_FLG) {
			case 0:
				// 組織コンボボックス
				if (title) {
					cboCompanyOrganization.setLocation(30, 7);
				} else {
					cboCompanyOrganization.setLocation(30, 0);
				}
				add(cboCompanyOrganization);

				// 階層レベルコンボボックス
				cboLevel.setLangMessageID("C00060");
				cboLevel.setLabelSize(60);
				cboLevel.setSize(165, 20);
				cboLevel.setComboSize(100);
				if (title) {
					cboLevel.setLocation(30, 32);
				} else {
					cboLevel.setLocation(30, 25);
				}
				add(cboLevel);

				// 配下会社パネル
				pnlLowCompany.setLayout(null);
				pnlLowCompany.setLangMessageID("C01281"); // 配下会社
				pnlLowCompany.setSize(150, 50);
				if (title) {
					pnlLowCompany.setLocation(200, 5);
				} else {
					pnlLowCompany.setLocation(200, -2);
				}
				add(pnlLowCompany);

				// 含む
				rdoInclude.setLangMessageID("C00461");
				rdoInclude.setSize(65, 20);
				rdoInclude.setLocation(10, 20);
				rdoInclude.setOpaque(false);
				pnlLowCompany.add(rdoInclude);

				// 含まない
				rdoNotInclude.setLangMessageID("C00460");
				rdoNotInclude.setSize(80, 20);
				rdoNotInclude.setLocation(75, 20);
				rdoNotInclude.setOpaque(false);
				pnlLowCompany.add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;
			// 新レイアウト
			default:
				// 組織コンボボックス
				if (title) {
					cboCompanyOrganization.setLocation(4, 7);
				} else {
					cboCompanyOrganization.setLocation(4, 0);
				}
				cboCompanyOrganization.setLabelSize(25);
				cboCompanyOrganization.setComboSize(160);
				cboCompanyOrganization.setLangMessageID("C00334");
				add(cboCompanyOrganization);

				// 階層レベルコンボボックス
				cboLevel.setLangMessageID("");
				cboLevel.setLabelSize(0);
				cboLevel.setSize(165, 20);
				cboLevel.setComboSize(160);
				if (title) {
					cboLevel.setLocation(194, 7);
				} else {
					cboLevel.setLocation(194, 0);
				}
				add(cboLevel);

				// ラベル
				lblLowCompany.setSize(50, 20);
				lblLowCompany.setLangMessageID("C00904");

				// 含む
				rdoInclude.setLangMessageID("C00461");
				rdoInclude.setSize(65, 20);
				rdoInclude.setLocation(70, 33);
				rdoInclude.setOpaque(false);

				// 含まない
				rdoNotInclude.setLangMessageID("C00460");
				rdoNotInclude.setSize(80, 20);
				rdoNotInclude.setOpaque(false);

				if (title) {
					rdoInclude.setLocation(70, 33);
					rdoNotInclude.setLocation(145, 33);
					lblLowCompany.setLocation(6, 33);
				} else {
					lblLowCompany.setLocation(6, 25);
					rdoInclude.setLocation(70, 25);
					rdoNotInclude.setLocation(145, 25);
				}
				add(lblLowCompany);
				add(rdoInclude);
				add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;

		}

		// 上位会社
		ctrlSuperiorCompany.btn.setLangMessageID("C01487"); // 上位会社
		if (title) {
			ctrlSuperiorCompany.setLocation(15, 60);
		} else {
			ctrlSuperiorCompany.setLocation(15, 50);
		}
		add(ctrlSuperiorCompany);

		// 会社
		if (title) {
			ctrlCompany.setLocation(15, 80);
		} else {
			ctrlCompany.setLocation(15, 70);
		}
		ctrlCompany.getReferenceRange().getFieldFrom().btn.setLangMessageID("C11366"); // 開始会社
		ctrlCompany.getReferenceRange().getFieldTo().btn.setLangMessageID("C11367"); // 終了会社
		add(ctrlCompany);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		cboCompanyOrganization.setTabControlNo(tabControlNo);
		cboLevel.setTabControlNo(tabControlNo);
		rdoInclude.setTabControlNo(tabControlNo);
		rdoNotInclude.setTabControlNo(tabControlNo);
		ctrlSuperiorCompany.setTabControlNo(tabControlNo);
		ctrlCompany.setTabControlNo(tabControlNo);
	}

	/**
	 * 出力単位を返す
	 * 
	 * @return 出力単位
	 */
	public CompanyOutputCondition getCompanyOutputCondition() {
		return controller.getCompanyOutputCondition();
	}

	/**
	 * 出力単位を設定する
	 * 
	 * @param condition 出力単位
	 */
	public void setCompanyOutputCondition(CompanyOutputCondition condition) {
		controller.setCompanyOutputCondition(condition);
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isCompanySmallerFrom() {
		return controller.isCompanySmallerFrom();
	}

}
