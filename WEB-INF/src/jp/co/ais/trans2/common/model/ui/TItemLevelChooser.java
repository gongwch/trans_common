package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 科目の表示レベル選択コンポーネント
 * 
 * @author AIS
 */
public class TItemLevelChooser extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 197804963583733563L;

	/** 科目 */
	public TRadioButton rdoItem;

	/** 補助科目 */
	public TRadioButton rdoSubItem;

	/** 内訳科目 */
	public TRadioButton rdoDetailItem;

	/** 表示方向 */
	protected int direction = SwingConstants.VERTICAL;

	/** タイトル表示 */
	protected boolean title = false;

	/** コントローラ */
	protected TItemLevelChooserController controller;

	/**
	 * 
	 *
	 */
	public TItemLevelChooser() {
		this(SwingConstants.VERTICAL);
	}

	/**
	 * @param direction タイプ
	 */
	public TItemLevelChooser(int direction) {

		this(direction, false);

	}

	/**
	 * @param title タイトルが表示かどうか
	 */
	public TItemLevelChooser(boolean title) {
		this(SwingConstants.VERTICAL, title);
	}

	/**
	 * @param direction 表示方向
	 * @param title タイトルが表示かどうか
	 */
	public TItemLevelChooser(int direction, boolean title) {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents(direction, title);

		// 方向設定
		this.direction = direction;

		// タイトル表示
		this.title = title;

		// コントローラ生成
		controller = new TItemLevelChooserController(this);

	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		rdoItem = new TRadioButton();
		rdoSubItem = new TRadioButton();
		rdoDetailItem = new TRadioButton();
	}

	/**
	 * コンポーネントを配置する
	 * 
	 * @param direction1 配置方向
	 * @param title1 タイトル表示かどか
	 */
	protected void allocateComponents(int direction1, boolean title1) {

		if (title1) {
			setLangMessageID("C00906"); // 表示レベル
		} else {
			setBorder(TBorderFactory.createTitledBorder(TModelUIUtil.getWord("C00906"))); // 表示レベル
			this.titlePanel.setSize(0, 0);
			this.titlePanel.setVisible(false);
		}

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoItem);
		bg.add(rdoSubItem);
		bg.add(rdoDetailItem);

		if (direction1 == SwingConstants.VERTICAL) {

			setSize(130, 85);

			// 科目
			rdoItem.setSize(110, 20);
			rdoItem.setLangMessageID("C00077"); // 科目
			if (title1) {
				rdoItem.setLocation(15, 5);
			} else {
				rdoItem.setLocation(15, 15);
			}
			add(rdoItem);

			// 補助科目
			rdoSubItem.setSize(110, 20);
			rdoSubItem.setLangMessageID("C00488"); // 補助科目
			if (title1) {
				rdoSubItem.setLocation(15, 25);
			} else {
				rdoSubItem.setLocation(15, 35);
			}
			add(rdoSubItem);

			// 内訳科目
			rdoDetailItem.setSize(110, 20);
			rdoDetailItem.setLangMessageID("C00025"); // 内訳科目
			if (title1) {
				rdoDetailItem.setLocation(15, 45);
			} else {
				rdoDetailItem.setLocation(15, 55);
			}
			add(rdoDetailItem);

		} else {

			this.setLayout(new GridBagLayout());

			GridBagConstraints gc = new GridBagConstraints();
			int top = 5;

			this.mainPanel.setLayout(null);
			setSize(310, 50);

			// 科目
			TGuiUtil.setComponentSize(rdoItem, 90, 20);
			rdoItem.setLangMessageID("C00077"); // 科目
			if (!title1) {
				top = 0;
			}
			gc.insets = new Insets(top, 0, 0, 0);
			gc.gridx = 0;
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
			add(rdoItem, gc);

			// 補助科目
			TGuiUtil.setComponentSize(rdoSubItem, 90, 20);
			rdoSubItem.setLangMessageID("C00488"); // 補助科目
			gc.insets = new Insets(top, 0, 0, 0);
			gc.gridx = 1;
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
			add(rdoSubItem, gc);

			// 内訳科目
			TGuiUtil.setComponentSize(rdoDetailItem, 80, 20);
			rdoDetailItem.setLangMessageID("C00025"); // 内訳科目
			gc.insets = new Insets(top, 0, 0, 0);
			gc.gridx = 2;
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
			add(rdoDetailItem, gc);
		}

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoItem.setTabControlNo(tabControlNo);
		rdoSubItem.setTabControlNo(tabControlNo);
		rdoDetailItem.setTabControlNo(tabControlNo);
	}

	/**
	 * 表示の方向を返す(縦/横)
	 * 
	 * @return 表示の方向(縦/横)
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * 選択された科目レベルを返す
	 * 
	 * @return 選択された科目レベル
	 */
	public ItemLevel getItemLevel() {
		return controller.getItemLevel();
	}

	/**
	 * 科目レベルをセットする
	 * 
	 * @param itemLevel 科目レベル
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		controller.setItemLevel(itemLevel);
	}

	/**
	 * タイトル表示かどうかを返す
	 * 
	 * @return 表示かどうか
	 */
	public boolean getTitle() {
		return title;
	}

	/**
	 * Enabled制御
	 */
	@Override
	public void setEnabled(boolean bln) {
		rdoItem.setEnabled(bln);
		rdoSubItem.setEnabled(bln);
		rdoDetailItem.setEnabled(bln);
	}

}
