package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * タイトルあるパネル
 */
public class TTitlePanel extends TPanel {

	/** コンテンツPanel */
	public TPanel mainPanel = new TPanel();

	/** タイトルPanel */
	public TImagePanel titlePanel = new TImagePanel();

	/** タイトルLabel */
	public TLabel titleLabel = new TLabel();

	/** 表示するかどうか */
	public boolean isTitleVisible = false;

	/**
	 * コンストラクタ.
	 */
	public TTitlePanel() {
		super();
		initComponent();
	}

	/**
	 * 画面構築
	 */
	public void initComponent() {

		GridBagConstraints gridBagConstraints;

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.lightGray));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		titlePanel = new TImagePanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.setMaximumSize(new Dimension(0, 20));
		titlePanel.setMinimumSize(new Dimension(0, 20));
		titlePanel.setPreferredSize(new Dimension(0, 20));

		// 背景図を設定する
		setImage();

		add(titlePanel, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		titlePanel.add(titleLabel, gridBagConstraints);

		mainPanel = new TPanel();
		mainPanel.setMaximumSize(new Dimension(0, 0));
		mainPanel.setMinimumSize(new Dimension(0, 0));
		mainPanel.setPreferredSize(new Dimension(0, 0));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		add(mainPanel, gridBagConstraints);
		mainPanel.setLayout(null);
		mainPanel.setOpaque(true);
		mainPanel.setBackground(Color.WHITE);

	}

	@Override
	public void setLangMessageID(String langMessageID) {

		titleLabel.setLangMessageID("  "
			+ MessageUtil.getWord(TClientLoginInfo.getInstance().getUserLanguage(), langMessageID));

		setTitleVisible(true);

	}

	/**
	 * mainPanelを取得する
	 * 
	 * @return mainPanel
	 */
	public TPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * 背景図の設定
	 */
	public void setImage() {

		String prefix = "title_";
		if (TGradientPaint.isFlat) {
			prefix = "title_flat_";
		}

		try {
			LookAndFeelColor color = TLoginInfo.getTitleColor();

			// タイトル文字の色
			switch (color) {
				case White:
					titleLabel.setForeground(Color.black);
					break;

				default:
					titleLabel.setForeground(Color.white);
					break;
			}

			String fileName = "images/" + prefix + color.toString().toLowerCase() + ".png";
			titlePanel.setImageIcon(ResourceUtil.getImage(TTitlePanel.class, fileName));

		} catch (Exception e) {
			titlePanel.setImageIcon(ResourceUtil.getImage(TTitlePanel.class, "images/" + prefix + "white.png"));
		}
	}

	/**
	 * タイトル表示フラグ設定（L&F変更する用、true：タイトル変更 false：変更しない）
	 * 
	 * @param isTitleVisible
	 */
	public void setTitleVisible(boolean isTitleVisible) {
		this.isTitleVisible = isTitleVisible;
	}

	@Override
	public Component add(Component comp) {

		if (comp instanceof JComponent) {
			((JComponent) comp).setOpaque(false);
		}
		return mainPanel.add(comp);
	}
}
