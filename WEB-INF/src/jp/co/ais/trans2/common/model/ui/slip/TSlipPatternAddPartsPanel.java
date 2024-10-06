package jp.co.ais.trans2.common.model.ui.slip;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * ‹àŠz“ü—Íƒwƒbƒ_•t‚«ƒpƒ^[ƒ“ƒpƒlƒ‹
 */
public abstract class TSlipPatternAddPartsPanel extends TSlipPatternPanel {

	/** Œvã•”–å */
	public TDepartmentReference ctrlDepartment;

	/** ‰È–Ú */
	public TItemGroup ctrlItem;

	/** ’Ê‰İ */
	public TCurrencyReference ctrlCurrency;

	/** ƒŒ[ƒg */
	public TLabelNumericField ctrlRate;

	/** “ü—Í‹àŠz */
	public TLabelNumericField ctrlInputAmount;

	/** –M‰İ‹àŠz */
	public TLabelNumericField ctrlKeyAmount;

	@Override
	public void initComponents() {
		super.initComponents();

		// ƒwƒbƒ_
		ctrlDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();

		ctrlCurrency = new TCurrencyReference();
		ctrlRate = new TLabelNumericField();
		ctrlInputAmount = new TLabelNumericField();
		ctrlKeyAmount = new TLabelNumericField();

		// ‰Šúİ’è --

		// Œvã•”–å
		ctrlDepartment.btn.setLangMessageID("C00863");

		// x•¥’Ê‰İ
		ctrlCurrency.setEditable(false);
		ctrlCurrency.name.setVisible(false);
		ctrlCurrency.resize();

		// ƒŒ[ƒg
		ctrlRate.setEditable(false);
		ctrlRate.setLangMessageID("C00556");
		ctrlRate.setMaxLength(13, 4);
		ctrlRate.setPositiveOnly(true);

		// “ü—Í‹àŠz
		ctrlInputAmount.setLangMessageID("C00574");
		ctrlInputAmount.setMaxLength(13, 4);
		ctrlInputAmount.setChangeRedOfMinus(true);

		// –M‰İ‹àŠz
		ctrlKeyAmount.setEditable(false);
		ctrlKeyAmount.setLangMessageID("C00576");
		ctrlKeyAmount.setMaxLength(13, 4);
		ctrlKeyAmount.setChangeRedOfMinus(true);
	}
}
