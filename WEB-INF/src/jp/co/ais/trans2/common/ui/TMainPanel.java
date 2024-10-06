package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import jp.co.ais.plaf.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * Mainパネル
 * 
 * @author AIS
 */
public abstract class TMainPanel extends TPanelBusiness {

	/** serialVersionUID */
	private static final long serialVersionUID = -3260639567895368281L;

	/** ヘッダー */
	protected TPanel pnlHeader = null;

	/** 境界線 */
	protected JSeparator sep;

	/** 作業領域 */
	protected TPanel pnlBody = null;

	/**
	 * 各画面インスタンスが持つキー情報。<br>
	 * 保存、復元等に使用する。
	 */
	protected String keyName = null;

	/** GridBagConstraints */
	protected GridBagConstraints gc = null;

	/** 会社情報 */
	protected Company company = null;

	protected final int HEADER_LEFT_X = 30;

	protected final int HEADER_Y = 10;

	protected final int HEADER_MARGIN_X = 5;

	public TMainPanel() {
		this(null);
	}

	public TMainPanel(Company company) {
		this.company = company;
		init();
		initComponents();
		allocateComponents();
		setTabIndex();
		initPanel();
	}

	/**
	 * ヘッダパネル背景のグラデーション
	 */
	class TPanelHeader extends TPanel {

		/** グラデーション始点色 */
		protected Color startColor = Color.white;

		/** グラデーション終点色 */
		protected Color endColor = new Color(210, 210, 202);

		@Override
		public void paintComponent(Graphics g) {

			if (TGradientPaint.isFlat) {
				endColor = ColorHelper.brighter(getBackground(), 25);
			}

			Graphics2D g2 = (Graphics2D) g;

			TGradientPaint gradient = new TGradientPaint(getWidth() / 2, 0.0f, startColor, getWidth() / 2, getHeight(),
				endColor);
			g2.setPaint(gradient);

			g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		}

	}

	/**
	 * メインパネル背景のグラデーション
	 */
	class TPanelBody extends TPanel {

		/** グラデーション始点色 */
		protected Color startColor = Color.white;

		/** グラデーション始点色 */
		protected Color endColor;

		@Override
		public void paintComponent(Graphics g) {

			if (TGradientPaint.isFlat) {
				super.paintComponent(g);
				return;
			}

			endColor = getBackground();

			Graphics2D g2 = (Graphics2D) g;

			// 上(白)⇒下(色)
			TGradientPaint gradient = new TGradientPaint(getWidth() / 4, 0.0f, startColor, getWidth() / 4,
				getHeight() / 4, endColor);
			g2.setPaint(gradient);

			g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

		}

	}

	/**
	 * 初期化
	 */
	protected void init() {

		gc = new GridBagConstraints();

		setLayout(new GridBagLayout());

		// ヘッダー領域
		pnlHeader = new TPanelHeader();
		pnlHeader.setLayout(null);
		pnlHeader.setMaximumSize(new Dimension(0, 40));
		pnlHeader.setMinimumSize(new Dimension(0, 40));
		pnlHeader.setPreferredSize(new Dimension(0, 40));

		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlHeader, gc);

		// 境界線
		sep = new JSeparator();
		sep.setMaximumSize(new Dimension(800, 3));
		sep.setMinimumSize(new Dimension(800, 3));
		sep.setPreferredSize(new Dimension(800, 3));
		gc.gridy = 1;
		add(sep, gc);

		// ボディ領域
		pnlBody = new TPanelBody();
		pnlBody.setLayout(new GridBagLayout());
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);

		gc = new GridBagConstraints();
	}

	/**
	 * コンポーネントの初期化。主にインスタンスの生成を行います。
	 */
	public abstract void initComponents();

	/**
	 * コンポーネントの配置を行います。
	 */
	public abstract void allocateComponents();

	/**
	 * コンポーネントにタブ順を設定します。
	 */
	public abstract void setTabIndex();

	/**
	 * @return keyNameを戻します。
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName keyNameを設定します。
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

}
