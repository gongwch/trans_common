package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択：メイン
 * 
 * @author AIS
 */
public class TManagementSelectUnit extends TTitlePanel {

	/** 管理選択コンボボックス */
	public TComboBox cbo;

	/** 管理範囲選択フィールド */
	public List<TReferenceRangeUnit> ctrlReferenceRangeUnits;

	/** コントローラ */
	protected TManagementSelectUnitController controller;

	/** ボーダーを表示するか */
	protected boolean border = false;

	/**
	 * コンストラクタ
	 */
	public TManagementSelectUnit() {
		this(false);
	}

	/**
	 * コンストラクタ(ボーダー表示)
	 * 
	 * @param border
	 */
	public TManagementSelectUnit(boolean border) {

		this.border = border;

		// コンポーネント初期化
		initComponents();

		// コンポーネント配置
		allocateComponents();

		// コントローラ生成
		controller = createController();

	}

	/**
	 * コントローラ生成
	 * 
	 * @return controller
	 */
	public TManagementSelectUnitController createController() {
		return new TManagementSelectUnitController(this);
	}

	/**
	 * コンポーネント初期化
	 */
	public void initComponents() {
		cbo = new TComboBox();
		ctrlReferenceRangeUnits = new ArrayList<TReferenceRangeUnit>();
	}

	/**
	 * コンポーネント配置
	 */
	public void allocateComponents() {

		setSize(330, 80);

		// コンボボックス
		cbo.setLocation(0, 0);
		cbo.setSize(100, 20);
		add(cbo);

		if (border) {
			setLangMessageID("C10834"); // 管理選択
			setSize(365, 115);
			cbo.setLocation(15, 5);
		} else {
			this.titlePanel.setVisible(false);
			this.setBorder(null);
		}
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		cbo.setTabControlNo(tabControlNo);
		for (TReferenceRangeUnit unit : ctrlReferenceRangeUnits) {
			unit.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * 選択されている管理を返す
	 * 
	 * @return 選択されている管理
	 */
	public ManagementAngle getManagementAngle() {
		return controller.getManagementAngle();
	}

	/**
	 * 指定された条件を纏めて返す。<br>
	 * ・管理として何が選択されているか<br>
	 * ・開始コード、終了コード<br>
	 * ・個別選択コード<br>
	 * を纏めて返す。
	 * 
	 * @return searchConditino
	 */
	public ManagementAngleSearchCondition getManagementAngleSearchCondition() {
		return controller.getManagementAngleSearchCondition();
	}

	/**
	 * 選択されている範囲検索フィールドを返す
	 * 
	 * @return 選択されている範囲検索フィールド
	 */
	public TReferenceRangeUnit getReferenceRangeUnit() {
		return controller.getReferenceRangeUnit();
	}

	/**
	 * 指定された条件を設定する
	 * 
	 * @param condition 指定された条件
	 */
	public void setManagementAngleSearchCondition(ManagementAngleSearchCondition condition) {
		controller.setManagementAngleSearchCondition(condition);
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getReferenceRangeUnit().getReferenceRange().getCodeFrom(), getReferenceRangeUnit()
			.getReferenceRange().getCodeTo()));
	}

	/**
	 * ボーダー確認
	 * 
	 * @return booean
	 */
	public boolean isBorder() {
		return border;
	}

	/**
	 * ボーダーセット
	 * 
	 * @param border
	 */
	public void setBorder(boolean border) {
		this.border = border;
	}
}