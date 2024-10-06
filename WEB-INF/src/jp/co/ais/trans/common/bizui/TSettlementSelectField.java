package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 決算段階フィールド
 * 
 * @author moriya
 */
public class TSettlementSelectField extends TPanel {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** ボタンレイアウト */
	private int layoutType = HORIZONTAL;

	/** 横 */
	public static final int HORIZONTAL = 1;

	/** 縦 */
	public static final int VERTICAL = 2;

	/** コントロールクラス */
	protected TSettlementSelectFieldCtrl ctrl;

	/** ベースパネル */
	protected TPanel pnlBase;

	/** 通常 */
	public TRadioButton rdoNormally;

	/** 決算 */
	public TRadioButton rdoSettlement;

	/** ラベル（次迄） */
	public TLabel lblSettlementDivision;

	/** 決算段階表示 */
	public TNumericField numSettlementDivision;

	/** ボタングループ */
	public ButtonGroup btngrpSettlementDivision;

	/**
	 * コンストラクタ
	 */
	public TSettlementSelectField() {
		super();
		initComponents();
		this.ctrl = new TSettlementSelectFieldCtrl(this);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 */
	public TSettlementSelectField(int type) {
		super();
		layoutType = type;
		initComponents();
		this.ctrl = new TSettlementSelectFieldCtrl(this);
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		rdoNormally = new TRadioButton();
		rdoSettlement = new TRadioButton();
		lblSettlementDivision = new TLabel();
		numSettlementDivision = new TNumericField();
		btngrpSettlementDivision = new ButtonGroup();

		pnlBase.setLangMessageID("C00610");
		rdoNormally.setLangMessageID("C00372");
		btngrpSettlementDivision.add(rdoNormally);
		btngrpSettlementDivision.add(rdoSettlement);
		rdoSettlement.setSelected(true);
		rdoSettlement.setLangMessageID("C00142");
		lblSettlementDivision.setLangMessageID("C00374");
		numSettlementDivision.setMaxLength(1);
		numSettlementDivision.setPositiveOnly(true);

		switch (layoutType) {
			// 横タイプ
			case HORIZONTAL:

				pnlBase.setLayout(new GridBagLayout());

				pnlBase.setMaximumSize(new Dimension(210, 45));
				pnlBase.setMinimumSize(new Dimension(210, 45));
				pnlBase.setPreferredSize(new Dimension(210, 45));

				rdoNormally.setMargin(new Insets(0, 0, 0, 0));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 5, 0);
				pnlBase.add(rdoNormally, gridBagConstraints);

				rdoSettlement.setMargin(new Insets(0, 0, 0, 0));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 15, 5, 0);
				pnlBase.add(rdoSettlement, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 3;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 5, 5, 0);
				pnlBase.add(lblSettlementDivision, gridBagConstraints);

				numSettlementDivision.setMaximumSize(new Dimension(20, 20));
				numSettlementDivision.setMinimumSize(new Dimension(20, 20));
				numSettlementDivision.setPreferredSize(new Dimension(20, 20));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 2;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 5, 5, 0);
				pnlBase.add(numSettlementDivision, gridBagConstraints);
				break;
			case VERTICAL:

				pnlBase.setLayout(new GridBagLayout());

				pnlBase.setMaximumSize(new Dimension(150, 75));
				pnlBase.setMinimumSize(new Dimension(150, 75));
				pnlBase.setPreferredSize(new Dimension(150, 75));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(5, 0, 0, 3);
				pnlBase.add(rdoNormally, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.insets = new Insets(5, 0, 0, 3);
				pnlBase.add(rdoSettlement, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 2;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.insets = new Insets(5, 5, 1, 5);
				pnlBase.add(lblSettlementDivision, gridBagConstraints);

				numSettlementDivision.setMaximumSize(new Dimension(20, 20));
				numSettlementDivision.setMinimumSize(new Dimension(20, 20));
				numSettlementDivision.setPreferredSize(new Dimension(20, 20));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 1;
				pnlBase.add(numSettlementDivision, gridBagConstraints);
				break;
		}

		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

		// 更新区分を変更する
		rdoNormally.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				// CTRLがインスタンス化される前に呼び出された場合は処理しない
				if (Util.isNullOrEmpty(ctrl)) {
					return;
				}

				ctrl.radioSettlementChange();
			}
		});

		// 決算区分を変更する
		rdoSettlement.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				// CTRLがインスタンス化される前に呼び出された場合は処理しない
				if (Util.isNullOrEmpty(ctrl)) {
					return;
				}
				ctrl.radioSettlementChange();
			}
		});
	}

	/**
	 * タブ移動順番号を設定
	 * 
	 * @param no タブ順番号
	 */
	public void setTabControlNo(int no) {
		rdoNormally.setTabControlNo(no);
		rdoSettlement.setTabControlNo(no);
		numSettlementDivision.setTabControlNo(no);
	}

	/**
	 * パネルの取得
	 * 
	 * @return TPanel
	 */
	public TPanel getPnlBase() {
		return pnlBase;
	}

	/**
	 * ラジオボタン（通常）の取得
	 * 
	 * @return TRadioButton
	 */
	public TRadioButton getRdoNormally() {
		return rdoNormally;
	}

	/**
	 * ラジオボタン（決算）の取得
	 * 
	 * @return TRadioButton
	 */
	public TRadioButton getRdoSettlement() {
		return rdoSettlement;
	}

	/**
	 * 決算段階フィールドの取得
	 * 
	 * @return 決算段階フィールド
	 */
	public TNumericField getNumSettlementDivision() {
		return numSettlementDivision;
	}

	/**
	 * 通常が選択されているかどうか
	 * 
	 * @return true:通常、false:決算
	 */
	public boolean isNormallySelected() {
		return rdoNormally.isSelected();
	}

	/**
	 * 決算段階を取得
	 * 
	 * @return 決算段階
	 */
	public int getSettlementDivision() {
		return numSettlementDivision.getInt();
	}

	/**
	 * ラベル部の取得
	 * 
	 * @return 次迄
	 */
	public TLabel getLblSettlementDivision() {
		return lblSettlementDivision;
	}

}
