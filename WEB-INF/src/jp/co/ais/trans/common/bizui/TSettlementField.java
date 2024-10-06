package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 決算フィールド
 * 
 * @author moriya
 */
public class TSettlementField extends TPanel {

	private static final long serialVersionUID = 1L;

	/** コントロールクラス */
	private TSettlementFieldCtrl ctrl;

	/** ベースパネル */
	private TPanel pnlBase;

	/** 決算仕訳チェックボックス */
	private TCheckBox chkClosingEntry;

	/** 決算仕訳回数表示 */
	private TNumericField ctrlClosingAccountsStage;

	/** 伝票日付の取得 */
	private Date slipDate;

	/**
	 * コンストラクタ
	 */
	public TSettlementField() {
		super();
		this.ctrl = new TSettlementFieldCtrl(this);
		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		chkClosingEntry = new TCheckBox();
		ctrlClosingAccountsStage = new TNumericField();

		pnlBase.setLayout(new GridBagLayout());
		pnlBase.setMaximumSize(new Dimension(100, 25));
		pnlBase.setMinimumSize(new Dimension(100, 25));
		pnlBase.setPreferredSize(new Dimension(100, 25));
		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

		chkClosingEntry.setLangMessageID("C00143");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlBase.add(chkClosingEntry, gridBagConstraints);

		ctrlClosingAccountsStage.setEnabled(true);
		ctrlClosingAccountsStage.setEditable(false);
		ctrlClosingAccountsStage.setMaxLength(1);
		ctrlClosingAccountsStage.setMaximumSize(new Dimension(20, 20));
		ctrlClosingAccountsStage.setMinimumSize(new Dimension(20, 20));
		ctrlClosingAccountsStage.setPreferredSize(new Dimension(20, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlBase.add(ctrlClosingAccountsStage, gridBagConstraints);

		// 「決算仕訳」チェック時の処理
		chkClosingEntry.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (!ctrl.chkClosingEntryMouseClicked()) {
					chkClosingEntry.setSelected(false);
				}
			}
		});

	}

	/**
	 * 伝票日付setter
	 * 
	 * @return Date
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * 伝票日付getter
	 * 
	 * @param slipDate
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * パネルsetter
	 * 
	 * @return TPanel
	 */
	public TPanel getBasePanel() {
		return pnlBase;
	}

	/**
	 * パネルgetter
	 * 
	 * @param pnlBase
	 */
	public void setBasePanel(TPanel pnlBase) {
		this.pnlBase = pnlBase;
	}

	/**
	 * 決算仕訳チェックボックス取得
	 * 
	 * @return 決算仕訳チェックボックス
	 */
	public TCheckBox getClosingEntry() {
		return chkClosingEntry;
	}

	/**
	 * 決算仕訳回数表示フィールド取得
	 * 
	 * @return 決算仕訳回数表示フィールド
	 */
	public TNumericField getClosingAccountsStage() {
		return ctrlClosingAccountsStage;
	}

	/**
	 * チェックボックスが選択されているかどうか.
	 * 
	 * @return true:選択されている
	 */
	public boolean isSelected() {
		return chkClosingEntry.isSelected();
	}

	/**
	 * フィールドへ値を設定
	 * 
	 * @param value
	 */
	public void setValue(Integer value) {
		ctrlClosingAccountsStage.setValue(value);
	}

	/**
	 * 値の取得
	 * 
	 * @return 値
	 */
	public int getValue() {
		return ctrlClosingAccountsStage.getInt();
	}

	/**
	 * タブ移動順番号を設定
	 * 
	 * @param no タブ順番号
	 */
	public void setTabControlNo(int no) {
		chkClosingEntry.setTabControlNo(no);
	}
}
