package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 送金目的検索コンポーネント
 */
public class TRemittanceReference extends TReference {

	/** 略称(半角のみ) */
	public THalfAngleTextField halfName;

	/** 国際収支コード */
	public TTextField balanceCode;

	/** コントローラ */
	protected TRemittanceReferenceController controller;

	/** 新送金目的マスタを使うかどうか、true:使う */
	protected static final boolean isUseNewRemittance = ClientConfig.isFlagOn("trans.new.mp0100.use");

	/**
	 * コンストラクタ
	 */
	public TRemittanceReference() {
		this.controller = new TRemittanceReferenceController(this);
		this.resize();
	}

	/**
	 * コンポーネントを初期化する
	 */
	@Override
	protected void initComponents() {
		super.initComponents();
		halfName = new THalfAngleTextField();
		balanceCode = new TTextField();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {

		GridBagConstraints gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setOpaque(false);

		// ボタン
		TGuiUtil.setComponentSize(btn, new Dimension(80, 20));
		btn.setOpaque(false);

		// ラベル
		TGuiUtil.setComponentSize(lbl, new Dimension(80, 20));
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setOpaque(false);

		if (this.type == TYPE.BUTTON) {
			add(btn, gc);
			lbl.setVisible(false);
		} else {
			gc.insets = new Insets(0, 0, 0, 5);
			add(lbl, gc);
			btn.setVisible(false);
		}

		// 新送金目的コード
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(getCodeLength());
		gc.insets = new Insets(0, 0, 0, 0);
		add(code, gc);
		code.setAllowedSpace(false);
		code.setImeMode(false);

		if (isUseNewRemittance) {
			// 国際収支コード
			balanceCode.setEditable(false);
			TGuiUtil.setComponentSize(balanceCode, new Dimension(80, 20));
			gc.insets = new Insets(0, 0, 0, 0);
			add(balanceCode, gc);

			name = halfName;
			name.setMaxLength(22);
		}

		// 新送金目的名称
		name.setEditable(false);
		TGuiUtil.setComponentSize(name, new Dimension(150, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(name, gc);

		resize();
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	@Override
	public void setTabControlNo(int tabControlNo) {
		btn.setTabControlNo(tabControlNo);
		code.setTabControlNo(tabControlNo);
		balanceCode.setTabControlNo(tabControlNo);
		name.setTabControlNo(tabControlNo);
	}

	/**
	 * 表示された国際収支コードを返す
	 * 
	 * @return 国際収支コード
	 */
	public String getBalanceCode() {
		return balanceCode.getText();
	}

	/**
	 * 表示された国際収支コードをセットする
	 * 
	 * @param balance 国際収支コード
	 */
	public void setBalanceCode(String balance) {
		balanceCode.setText(balance);
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
		if (balanceCode.isVisible()) {
			width += (int) balanceCode.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (lbl.isVisible()) {
			width += (int) lbl.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	@Override
	public void setVisible(boolean bln) {
		btn.setVisible(bln);
		name.setVisible(bln);
		balanceCode.setVisible(bln);
		name.setVisible(bln);
		lbl.setVisible(bln);
		super.setVisible(bln);
	}

	/**
	 * 国際収支コード幅を変更する
	 * 
	 * @param width 幅
	 */
	public void setBalanceCodeSize(int width) {
		Dimension size = new Dimension(width, 20);
		balanceCode.setSize(size);
		balanceCode.setPreferredSize(size);
		balanceCode.setMinimumSize(size);
		balanceCode.setMaximumSize(size);
		resize();
	}

	/**
	 * コードの長さを返す。デフォルト長と異なる検索フィールドは 当該メソッドをOverrideする。
	 * 
	 * @return コード長
	 */
	@Override
	protected int getCodeLength() {
		if (isUseNewRemittance) {
			return 10;
		}
		return 4;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public RemittanceSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Remittance getEntity() {
		return controller.getEntity();
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * Entityセット
	 * 
	 * @param remittancePurpose Entity
	 */
	public void setEntity(Remittance remittancePurpose) {
		controller.setEntity(remittancePurpose);
	}
}
