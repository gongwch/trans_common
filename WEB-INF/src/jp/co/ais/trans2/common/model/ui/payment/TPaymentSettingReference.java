package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払条件検索コンポーネント
 * 
 * @author AIS
 */
public class TPaymentSettingReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 5845285285425873551L;

	/** コントローラ */
	protected TPaymentSettingReferenceController controller;

	/** 略称 */
	public TTextField accountNo;

	/**
	 * コンストラクタ.
	 */
	public TPaymentSettingReference() {
		this(false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param isVisibleAccountNo 口座番号を表示するかどうか
	 */
	public TPaymentSettingReference(boolean isVisibleAccountNo) {
		createController();
		accountNo.setVisible(isVisibleAccountNo);

		if (isVisibleAccountNo) {
			name.setMaximumSize(new Dimension(120, 20));
			name.setMinimumSize(new Dimension(120, 20));
			name.setPreferredSize(new Dimension(120, 20));
			this.resize();
		}

	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TPaymentSettingReference(TYPE type) {
		super(type);
		createController();
	}

	/**
	 * コントローラの作成
	 */
	protected void createController() {
		controller = new TPaymentSettingReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {

		super.initComponents();
		accountNo = new TTextField();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {

		super.allocateComponents();

		GridBagConstraints gc = new GridBagConstraints();

		// 口座番号
		accountNo.setEditable(false);
		TGuiUtil.setComponentSize(accountNo, new Dimension(100, 20));

		gc.insets = new Insets(0, 0, 0, 0);
		add(accountNo, gc);

		resize();
	}

	/**
	 * サイズの再反映
	 */
	@Override
	public void resize() {

		int width = (int) (btn.getPreferredSize().getWidth() + code.getPreferredSize().getWidth());
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (accountNo.isVisible()) {
			width += (int) accountNo.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public PaymentSettingSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public PaymentSetting getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param PaymentSetting 支払条件
	 */
	public void setEntity(PaymentSetting PaymentSetting) {
		controller.setEntity(PaymentSetting);
	}

	/**
	 * 口座番号の幅変更
	 * 
	 * @param width 幅
	 */
	public void setAccountNoSize(int width) {

		// 口座番号
		Dimension size = new Dimension(width, 20);
		accountNo.setSize(size);
		accountNo.setPreferredSize(size);
		accountNo.setMinimumSize(size);
		accountNo.setMaximumSize(size);
		resize();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.<br>
	 * 表示を更新する
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
	}

}
