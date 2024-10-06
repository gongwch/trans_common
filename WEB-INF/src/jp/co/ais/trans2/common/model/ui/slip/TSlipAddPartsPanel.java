package jp.co.ais.trans2.common.model.ui.slip;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * 金額入力ヘッダ付き伝票パネル
 */
public abstract class TSlipAddPartsPanel extends TSlipPanel {

	/** 計上部門 */
	public TDepartmentReference ctrlDepartment;

	/** 科目 */
	public TItemGroup ctrlItem;

	/** 通貨 */
	public TCurrencyReference ctrlCurrency;

	/** レート */
	public TLabelNumericField ctrlRate;

	/** 入力金額 */
	public TLabelNumericField ctrlInputAmount;

	/** 邦貨金額 */
	public TLabelNumericField ctrlKeyAmount;

	@Override
	public void initComponents() {
		super.initComponents();

		// ヘッダ
		ctrlDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();

		ctrlCurrency = new TCurrencyReference();
		ctrlRate = new TLabelNumericField();
		ctrlInputAmount = new TLabelNumericField();
		ctrlKeyAmount = new TLabelNumericField();

		// 初期設定 --

		// 計上部門
		ctrlDepartment.btn.setLangMessageID("C00863");

		// 支払通貨
		ctrlCurrency.setEditable(false);
		ctrlCurrency.name.setVisible(false);
		ctrlCurrency.resize();

		// レート
		ctrlRate.setEditable(false);
		ctrlRate.setLangMessageID("C00556");
		ctrlRate.setMaxLength(13, 4);
		ctrlRate.setPositiveOnly(true);

		// 入力金額
		ctrlInputAmount.setLangMessageID("C00574");
		ctrlInputAmount.setMaxLength(13, 4);
		ctrlInputAmount.setChangeRedOfMinus(true);

		// 邦貨金額
		ctrlKeyAmount.setEditable(false);
		ctrlKeyAmount.setLangMessageID("C00576");
		ctrlKeyAmount.setMaxLength(13, 4);
		ctrlKeyAmount.setChangeRedOfMinus(true);
	}

	/**
	 * ヘッダー通貨をデフォルト通貨として使うか
	 * 
	 * @return true:ヘッダー通貨をデフォルトで明細行を初期表示する
	 */
	public boolean isUseHeaderDefaultCurreny() {
		return false;
	}
}
