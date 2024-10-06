package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 組織(部門階層)の出力単位ユニットコンポーネント TODO 個別選択は未対応
 * 
 * @author AIS
 */
public class TDepartmentOrganizationUnit extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -1175300671844325183L;

	/** 組織名称表示 */
	public static int DISPLAY_NAME_FLG = 0;

	/** 組織コンボボックス */
	public TDepartmentOrganizationComboBox cboDepartmentOrganization;

	/** 階層レベル名称表示コンボボックス */
	public TLabelComboBox cboLevel;

	/** 配下部門パネル */
	public TPanel pnlLowDepartment;

	/** 配下部門ラベル */
	public TLabel lblLowDepartment;

	/** 含む */
	public TRadioButton rdoInclude;

	/** 含まない */
	public TRadioButton rdoNotInclude;

	/** ボタングループ */
	public ButtonGroup btnGroup;

	/** 上位部門 */
	public TDepartmentReference ctrlSuperiorDepartment;

	/** 部門 */
	public TDepartmentReferenceRangeUnit ctrlDepartment;

	/** コントローラ */
	public TDepartmentOrganizationUnitController controller;

	/** 保持キー */
	protected String saveKey = null;

	/**
	 * 
	 *
	 */
	public TDepartmentOrganizationUnit() {
		this(true);
	}

	/**
	 * @param title タイトル表示かどうか
	 */
	public TDepartmentOrganizationUnit(boolean title) {

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
	public TDepartmentOrganizationUnitController createController() {
		return new TDepartmentOrganizationUnitController(this);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		cboDepartmentOrganization = new TDepartmentOrganizationComboBox();
		cboLevel = new TLabelComboBox();
		pnlLowDepartment = new TPanel();
		lblLowDepartment = new TLabel();
		rdoInclude = new TRadioButton();
		rdoNotInclude = new TRadioButton();
		ctrlSuperiorDepartment = new TDepartmentReference();
		ctrlDepartment = new TDepartmentReferenceRangeUnit();
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
			setLangMessageID("C01159");
		} else {
			setLangMessageID("C01159");
			this.titlePanel.setVisible(false);
		}
		switch (DISPLAY_NAME_FLG) {
			case 0:
				// 組織コンボボックス
				if (title) {
					cboDepartmentOrganization.setLocation(30, 7);
				} else {
					cboDepartmentOrganization.setLocation(30, 0);
				}
				add(cboDepartmentOrganization);

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

				// 配下部門パネル
				pnlLowDepartment.setLayout(null);
				pnlLowDepartment.setLangMessageID("C00904");
				pnlLowDepartment.setSize(150, 50);
				if (title) {
					pnlLowDepartment.setLocation(200, 5);
				} else {
					pnlLowDepartment.setLocation(200, -2);
				}
				add(pnlLowDepartment);

				// 含む
				rdoInclude.setLangMessageID("C00461");
				rdoInclude.setSize(65, 20);
				rdoInclude.setLocation(10, 20);
				rdoInclude.setOpaque(false);
				pnlLowDepartment.add(rdoInclude);

				// 含まない
				rdoNotInclude.setLangMessageID("C00460");
				rdoNotInclude.setSize(80, 20);
				rdoNotInclude.setLocation(75, 20);
				rdoNotInclude.setOpaque(false);
				pnlLowDepartment.add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;

			// 新レイアウト
			default:
				// 組織コンボボックス
				if (title) {
					cboDepartmentOrganization.setLocation(4, 7);
				} else {
					cboDepartmentOrganization.setLocation(4, 0);
				}
				cboDepartmentOrganization.setLabelSize(25);
				cboDepartmentOrganization.setComboSize(160);
				cboDepartmentOrganization.setLangMessageID("C00334");
				add(cboDepartmentOrganization);

				// 階層レベルコンボボックス
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
				lblLowDepartment.setSize(50, 20);
				lblLowDepartment.setLangMessageID("C00904");

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
					lblLowDepartment.setLocation(6, 33);
				} else {
					lblLowDepartment.setLocation(6, 25);
					rdoInclude.setLocation(70, 25);
					rdoNotInclude.setLocation(145, 25);
				}
				add(lblLowDepartment);
				add(rdoInclude);
				add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;
		}

		// 上位部門
		ctrlSuperiorDepartment.btn.setLangMessageID("C00719");
		if (title) {
			ctrlSuperiorDepartment.setLocation(15, 60);
		} else {
			ctrlSuperiorDepartment.setLocation(15, 50);
		}
		add(ctrlSuperiorDepartment);

		// 部門
		if (title) {
			ctrlDepartment.setLocation(15, 80);
		} else {
			ctrlDepartment.setLocation(15, 70);
		}
		ctrlDepartment.getReferenceRange().getFieldFrom().btn.setLangMessageID("C10347");
		ctrlDepartment.getReferenceRange().getFieldTo().btn.setLangMessageID("C10169");
		add(ctrlDepartment);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {

		cboDepartmentOrganization.setTabControlNo(tabControlNo);
		cboDepartmentOrganization.setTabControlNo(tabControlNo);
		cboLevel.setTabControlNo(tabControlNo);
		rdoInclude.setTabControlNo(tabControlNo);
		rdoNotInclude.setTabControlNo(tabControlNo);
		ctrlSuperiorDepartment.setTabControlNo(tabControlNo);
		ctrlDepartment.setTabControlNo(tabControlNo);
	}

	/**
	 * 出力単位を返す
	 * 
	 * @return 出力単位
	 */
	public DepartmentOutputCondition getDepartmentOutputCondition() {
		return controller.getDepartmentOutputCondition();
	}

	/**
	 * 出力単位を設定する
	 * 
	 * @param condition 出力単位
	 */
	public void setDepartmentOutputCondition(DepartmentOutputCondition condition) {
		controller.setDepartmentOutputCondition(condition);
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isDepartmentSmallerFrom() {
		return controller.isDepartmentSmallerFrom();
	}

	/**
	 * 保持キーの取得
	 * 
	 * @return saveKey 保持キー
	 */
	public String getSaveKey() {
		return saveKey;
	}

	/**
	 * 保持キーの設定
	 * 
	 * @param saveKey 保持キー
	 */
	public void setSaveKey(String saveKey) {
		this.saveKey = saveKey;
	}

	/**
	 * 出力単位条件保持の設定により復旧
	 */
	public void restoreDepartmentSetting() {
		controller.restoreSetting();
	}

	/**
	 * 出力単位条件 設定保持
	 */
	public void saveDepartmentSetting() {
		// 解消される時に、保持キーがあれば、当該条件をクライアントに持つ
		controller.saveSetting();
	}
}
