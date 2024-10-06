package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 取引先支払条件マスタREFダイアログ<br>
 * boolean TPaymentSettingReferenceController.IS_USE_ACCOUNT_KANA がONの場合使う
 */
public class TPaymentSettingReferenceDialog extends TReferenceDialog {

	/** 口座名義カナ */
	public TTextField accountKana;

	/** 検索ダイアログの列番号定義 */
	public enum SC {
		/** Bean */
		bean,
		/** コード */
		code,
		/** 略称 */
		names,
		/** 検索名称 */
		namek,
		/** 口座名義カナ */
		accountKana
	}

	/**
	 * @param controller コントローラ
	 */
	public TPaymentSettingReferenceDialog(TPaymentSettingReferenceController controller) {
		super(controller);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	protected void initComponents() {
		super.initComponents();

		accountKana = new TTextField();

		tbl = new TTable();
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.code, getWord(controller.getCodeCaption()), 100);
		tbl.addColumn(SC.names, getWord(controller.getNamesCaption()), 160);
		tbl.addColumn(SC.namek, getWord(controller.getNamekCaption()), 160);
		tbl.addColumn(SC.accountKana, getWord("C00168"), 160);
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();

		setPreferredSize(new Dimension(750, 520));

		// 検索名称
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		pnlSearch.add(namek, gc);

		// 受取人住所
		int length = 160;
		accountKana.setMinimumSize(new Dimension(length, 20));
		accountKana.setPreferredSize(new Dimension(length, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;

		pnlSearch.add(accountKana, gc);

		pack();
	}

	/**
	 * Tab順セット
	 */
	@Override
	protected void setTabIndex() {
		int i = 0;
		code.setTabControlNo(i++);
		names.setTabControlNo(i++);
		namek.setTabControlNo(i++);
		accountKana.setTabControlNo(i++);
		if (ClientConfig.isFlagOn("trans.ref.table.focusable")) {
			tbl.setTabControlNo(i++);
			tbl.setEnterToButton(true);
		}
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}

}
