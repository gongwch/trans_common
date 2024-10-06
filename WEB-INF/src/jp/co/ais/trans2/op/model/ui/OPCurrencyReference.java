package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 通貨の検索コンポーネント
 * 
 * @author AIS
 */
public class OPCurrencyReference extends TCurrencyReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPCurrencyReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPCurrencyReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPCurrencyReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPCurrencyReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {

		// トラオペ共通対応
		if (isLabelMode()) {
			// 強制的にラベルにする
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {

		// トラオペ共通対応
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:ラベルモード
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * コントローラ
	 * 
	 * @return コントローラ
	 */
	@Override
	public OPCurrencyReferenceController createController() {
		return new OPCurrencyReferenceController(this);
	}

	@Override
	public OPCurrencyReferenceController getController() {
		return (OPCurrencyReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public CurrencySearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Currency getEntity() {
		return getController().getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	@Override
	public void setEntity(Currency bean) {
		getController().setEntity(bean);
	}

	/**
	 * 現在の入力値でエンティティを再設定する
	 */
	@Override
	public void refleshEntity() {
		getController().refleshEntity();
	}

	/**
	 * 基準日設定
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		getController().setTermDate(termDate);
	}

}
