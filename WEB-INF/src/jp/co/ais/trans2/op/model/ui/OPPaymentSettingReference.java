package jp.co.ais.trans2.op.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 取引先条件検索コンポーネント
 * 
 * @author AIS
 */
public class OPPaymentSettingReference extends TPaymentSettingReference {

	/**
	 * コンストラクタ.
	 */
	public OPPaymentSettingReference() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param isVisibleAccountNo 口座番号を表示するかどうか
	 */
	public OPPaymentSettingReference(boolean isVisibleAccountNo) {
		super(isVisibleAccountNo);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPPaymentSettingReference(TYPE type) {
		super(type);
	}

	/**
	 * コンポーネントを初期化する
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
			allocateOPComponents();
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * 
	 */
	protected void allocateOPComponents() {

		OPGuiUtil.allocateComponents(this);

		GridBagConstraints gc = new GridBagConstraints();

		// 口座番号
		accountNo.setEditable(false);
		TGuiUtil.setComponentSize(accountNo, new Dimension(100, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(accountNo, gc);

		resize();
	}

	/**
	 * @return true:ラベルモード
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * コントローラの作成
	 */
	@Override
	protected void createController() {
		controller = new OPPaymentSettingReferenceController(this);
	}

	@Override
	public OPPaymentSettingReferenceController getController() {
		return (OPPaymentSettingReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public PaymentSettingSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public PaymentSetting getEntity() {
		return getController().getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	@Override
	public void setEntity(PaymentSetting bean) {
		getController().setEntity(bean);
	}

	@Override
	public void refleshAndShowEntity() {
		getController().refleshEntity();
	}

	/**
	 * サイズの再反映
	 */
	@Override
	public void resize() {

		int width = (int) code.getPreferredSize().getWidth();
		if (btn.isVisible()) {
			width += (int) btn.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (lbl.isVisible()) {
			width += (int) lbl.getPreferredSize().getWidth() + 5; // 右余白が計算するべき
		}
		if (accountNo.isVisible()) {
			width += (int) accountNo.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

}
