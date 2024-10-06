package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * 決算仕訳チェック
 */
public class TClosingEntryCheck extends TPanel {

	/** チェック */
	public TCheckBox chk;

	/** 決算段階 */
	public TNumericField num;

	/** 次 */
	public TLabel lblStage;

	/** 段階 */
	public int stage;

	/** 伝票日付コンポーネント */
	public TSlipDate ctrlSlipDate;

	/** コントローラ */
	public TClosingEntryCheckController controller;

	/**
	 * コンストラクタ.
	 * 
	 * @param tSlipDate 伝票日付コンポーネント
	 */
	public TClosingEntryCheck(TSlipDate tSlipDate) {
		super();

		this.ctrlSlipDate = tSlipDate;

		initComponents();
		allocateComponents();

		controller = createController();
	}

	/**
	 * @return コントローラ
	 */
	protected TClosingEntryCheckController createController() {
		return new TClosingEntryCheckController(this);
	}

	/**
	 * 初期化
	 */
	protected void initComponents() {
		chk = new TCheckBox();
		num = new TNumericField();
		lblStage = new TLabel();
	}

	/**
	 * 配置
	 */
	protected void allocateComponents() {
		this.setLayout(null);

		TGuiUtil.setComponentSize(this, new Dimension(125, 20));

		// チェック
		chk.setLangMessageID("C00142");
		TGuiUtil.setComponentSize(chk, new Dimension(50, 20));
		chk.setLocation(0, 0);
		add(chk);

		// 段階
		num.setEditable(false);
		num.setNumericFormat("#");
		num.setMaxLength(1);
		num.setPositiveOnly(true);
		TGuiUtil.setComponentSize(num, new Dimension(30, 20));
		num.setLocation(chk.getX() + chk.getWidth(), 0);
		add(num);

		// 段階
		lblStage.setLangMessageID("C00373");
		lblStage.setHorizontalAlignment(SwingConstants.LEFT);
		TGuiUtil.setComponentSize(lblStage, new Dimension(45, 20));
		lblStage.setLocation(num.getX() + num.getWidth() + 2, 0);
		add(lblStage);
	}

	/**
	 * タブ順設定
	 * 
	 * @param no タブ順
	 */
	public void setTabControlNo(int no) {
		chk.setTabControlNo(no);
		num.setTabControlNo(no);
	}

	/**
	 * 決算段階が入力可能かどうか(チェック時)
	 * 
	 * @param isEdit true:可能
	 */
	public void setEditMode(boolean isEdit) {
		controller.setEditMode(isEdit);
	}

	/**
	 * チェックを表示しない
	 */
	public void setNotVisibleCheckBox() {
		chk.setVisible(false);

		num.setLocation(0, 0);
		lblStage.setLocation(num.getX() + num.getWidth() + 2, 0);
		TGuiUtil.setComponentSize(this, new Dimension(num.getWidth() + lblStage.getWidth(), 20));
	}

	/**
	 * 段階を表示しない
	 */
	public void setNotVisibleStage() {
		num.setVisible(false);
		lblStage.setVisible(false);

		TGuiUtil.setComponentSize(this, new Dimension(chk.getWidth(), 20));
	}

	/**
	 * 初期化処理
	 */
	public void clear() {
		chk.setSelected(false);
	}

	/**
	 * チェックボックスが選択されているかどうか
	 * 
	 * @return true:選択
	 */
	public boolean isSelected() {
		return chk.isSelected();
	}

	/**
	 * チェックボックスのON/OFF
	 * 
	 * @param isSelected true:ON
	 */
	public void setSelected(boolean isSelected) {
		chk.setSelected(isSelected);
	}

	/**
	 * 決算段階の取得
	 * 
	 * @return 決算段階
	 */
	public int getStage() {
		return num.getInt();
	}

	/**
	 * 決算段階の再設定
	 */
	public void resetStage() {
		controller.resetStage();
	}

	/**
	 * チェックボックスへのリスナー設定
	 * 
	 * @param listener リスナー
	 */
	public void addItemListener(ItemListener listener) {
		chk.addItemListener(listener);
	}
}
