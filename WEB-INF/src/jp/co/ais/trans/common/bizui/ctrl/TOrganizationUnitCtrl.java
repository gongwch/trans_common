package jp.co.ais.trans.common.bizui.ctrl;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 組織ユニットコントロール インターフェース<br>
 * 会社、部門、連結部門などのニーズに合わせてコントロールクラスを作成する.
 */
public abstract class TOrganizationUnitCtrl extends TAppletClientBase {

	/** ユニット */
	protected TOrganizationUnit unit;

	/** 階層レベルの表示 */
	protected String[] hierarchicalLabels = getWordList(new String[] { "C00722", "C01751", "C01752", "C01753",
			"C01754", "C01755", "C01756", "C01757", "C01758", "C01759" });

	/**
	 * コンストラクタ
	 * 
	 * @param unit 組織ユニット
	 */
	public TOrganizationUnitCtrl(TOrganizationUnit unit) {

		this.unit = unit;
	}

	/**
	 * 各コンポーネントのメッセージIDをセットする.
	 */
	public abstract void initLangMessageID();

	/**
	 * 画面初期状態の構築
	 */
	public void initPanel() {
		// 階層ﾚﾍﾞﾙ設定（固定）
		unit.getHierarchicalLevelComboBox().getComboBox().setModel(hierarchicalLabels);
	}

	/**
	 * 上位組織を制御する
	 */
	public abstract void changeUpper();

	/**
	 * ダイアログを表示する
	 * 
	 * @param tbuttonField
	 * @param flag
	 */
	public abstract void showRefDialog(TButtonField tbuttonField, String flag);

	/**
	 * 組織ｺｰﾄﾞコンボボックスEvent処理。<br>
	 * 
	 * @return 処理判別フラグ
	 */
	public abstract boolean changeOrgCode();

	/**
	 * 階層レベル変更
	 */
	public abstract void changeHierarchicalLevelItem();

	/**
	 * コードに対する名称を設定
	 * 
	 * @param argBtnField 対象フィールド
	 * @param flag 名称が存在するかどうか
	 * @return 処理判別フラグ
	 */
	public abstract boolean setupName(TButtonField argBtnField, String flag);

	/**
	 * 上位組織フラグ
	 * 
	 * @return 上位組織フラグ
	 */
	public abstract String getUpperOrgFlag();

	/**
	 * 組織フラグ
	 * 
	 * @return 組織フラグ
	 */
	public abstract String getOrgFlag();

	/**
	 * 科目体系コードのセット
	 * 
	 * @param code 科目体系コード
	 */
	public abstract void setItemSystemCode(String code);
}
