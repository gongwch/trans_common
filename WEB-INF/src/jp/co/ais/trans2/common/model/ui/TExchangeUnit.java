package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 換算ユニットコンポーネント<br>
 * 通貨、レート、外貨金額、邦貨金額で構成される。
 * @author AIS
 *
 */
public class TExchangeUnit extends TPanel {

	/** 通貨フィールド */
	public TCurrencyReference ctrlCurrencyReference; 

	/** レートフィールド */
	public TRate ctrlRate;

	/** 外貨金額フィールド */
	public TForeignAmount ctrlForeignAmount;

	/** 基軸金額フィールド */
	public TKeyAmount ctrlKeyAmount;

	/** コントローラ */
	public TExchangeUnitController controller;

	/**
	 * 
	 *
	 */
	public TExchangeUnit() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// コントローラ生成
		controller = new TExchangeUnitController(this);

	}

	/**
	 * コンポーネントを初期化する<BR>
	 *
	 */
	protected void initComponents() {
		ctrlCurrencyReference = new TCurrencyReference();
		ctrlRate = new TRate();
		ctrlForeignAmount = new TForeignAmount();
		ctrlKeyAmount = new TKeyAmount();
	}

	/**
	 * コンポーネントを配置する
	 *
	 */
	protected void allocateComponents() {
		// for override またはいずれ使う場合は実装して下さい。
	}

	/**
     * Tab順の設定
     * @param tabControlNo タブ順
     */
    public void setTabControlNo(int tabControlNo) {
    	ctrlCurrencyReference.setTabControlNo(tabControlNo);
    }

    /**
     * 通貨をセットする。<br>
     * 外貨フィールドは当該通貨の小数点桁数でフォーマットされる
     * @param currency 通貨
     */
    public void setCurrency(Currency currency) {
    	controller.setCurrency(currency);
    }

}
