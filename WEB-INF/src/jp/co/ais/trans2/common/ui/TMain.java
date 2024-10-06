package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;

/**
 * メイン画面
 * 
 * @author AIS
 */
public class TMain extends TFrame {

	/** ヘッダー領域 */
	public TPanel pnlHeader = null;

	/** 会社を表す線 */
	public TColorPanel pnlCompanyColor = null;

	/** ロゴ */
	public TLabel logo;

	/** LF&時刻領域 */
	public TPanel pnlLfAndTime;

	/** 会社名 */
	public TLabel lblCompanyName;

	/** ログインユーザーの所属部門とユーザー名 */
	public TLabel lblUserName;

	/** 時刻表示ラベル */
	public TLabel lblTime = null;

	/** LookAndFeelパネル */
	public TPanel pnlLf = null;

	/** プリンター設定ボタン */
	protected TButton btnPrintSetup = null;

	/** パスワード変更ボタン */
	public TButton btnPassword = null;

	/** バージョンボタン */
	public TButton btnVersion = null;

	/** ログアウトボタン */
	public TButton btnLogOut = null;

	/** L&Fボタン（Blue） */
	public TButton btnBlue = null;

	/** L&Fボタン（Pink） */
	public TButton btnPink = null;

	/** L&Fボタン（Orange） */
	public TButton btnOrange = null;

	/** L&Fボタン（Green） */
	public TButton btnGreen = null;

	/** L&Fボタン（Gray） */
	public TButton btnGray = null;

	/** L&Fボタン（White） */
	public TButton btnWhite = null;

	/** L&Fボタン（Type1） */
	public TButton btnType1 = null;

	/** L&Fボタン（Type2） */
	public TButton btnType2 = null;

	/** L&Fボタン（Type3） */
	public TButton btnType3 = null;

	/** フォントサイズ指定コンボ */
	public TLabelComboBox ctrlFontSize = null;

	/** ボディ領域 */
	public TPanel pnlBody = null;

	/** フッター領域 */
	public TPanel pnlFooter = null;

	/** ボディ領域のメニューと作業領域分割 */
	public TSplitPane spBody = null;

	/** メニュー領域 */
	public TPanel pnlMenu = null;

	/** メニュータブ */
	public TLeftColorTabbedPane menuTab = null;

	/** サブシステム毎のタブに表示するパネル */
	public List<TPanel> menuPanels = null;

	/** サブシステム毎のメニューツリー */
	public List<TTree> menuTrees = null;

	/** メイン領域 */
	public TPanel pnlMain = null;

	/** メインタブ(プログラム別のタブ) */
	public TTabbedPane mainTab = null;

	/** メイン画面初期化用INSTANCEリスト */
	public List<TMainInitialInterface> instanceList;

	/**
	 * コンストラクター
	 */
	public TMain() {

		// 初期化
		this(null);

	}

	/**
	 * コンストラクター
	 * 
	 * @param instanceList
	 */
	public TMain(List<TMainInitialInterface> instanceList) {
		this.instanceList = instanceList;

		// 初期化
		init();

	}

	/**
	 * 初期処理
	 */
	public void init() {

		try {
			// 指示画面生成
			initComponents();

			allocateComponents();

			initFrame();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		pnlHeader = new TPanel();
		lblTime = new TLabel();
		btnPrintSetup = new TButton();
		btnPassword = new TButton();
		btnVersion = new TButton();
		btnLogOut = new TButton();
		pnlLf = new TPanel();
		btnBlue = new TButton();
		btnPink = new TButton();
		btnOrange = new TButton();
		btnGreen = new TButton();
		btnGray = new TButton();
		btnWhite = new TButton();
		btnType1 = new TButton();
		btnType2 = new TButton();
		btnType3 = new TButton();
		ctrlFontSize = new TLabelComboBox();

		pnlCompanyColor = new TColorPanel();
		pnlBody = new TPanel();
		pnlFooter = new TPanel();
		pnlMenu = new TPanel();
		pnlMain = new TPanel();
		spBody = new TSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlMenu, pnlMain);
		menuTab = new TLeftColorTabbedPane();

		menuPanels = new ArrayList<TPanel>();
		menuTrees = new ArrayList<TTree>();
		mainTab = new TTabbedPane();
	}

	/**
	 * コンポーネントを配置
	 */
	public void allocateComponents() {

		setLayout(new GridBagLayout());

		// ヘッダー配置
		allocateHeader();

		// 作業領域の配置
		allocateBody();

		// フッター領域の配置
		allocateFooter();
	}

	/**
	 * ヘッダー領域の配置
	 */
	public void allocateHeader() {

		GridBagConstraints gc = new GridBagConstraints();
		pnlCompanyColor.setLayout(new GridBagLayout());
		pnlCompanyColor.setMinimumSize(new Dimension(0, 50));
		pnlCompanyColor.setPreferredSize(new Dimension(0, 50));
		pnlCompanyColor.setBackground(Color.white);
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlCompanyColor, gc);

		pnlHeader.setLayout(new GridBagLayout());
		pnlHeader.setOpaque(false);
		pnlHeader.setBackground(Color.white);
		pnlHeader.setPreferredSize(new Dimension(0, 50));
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlCompanyColor.add(pnlHeader, gc);

		pnlHeader.setLayout(new GridBagLayout());

		// ロゴ
		logo = new TLabel();

		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.WEST;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 20, 0, 0);
		pnlHeader.add(logo, gc);

		// 会社名
		lblCompanyName = new TLabel();
		TUIManager.addFontSize(lblCompanyName, 2);
		lblCompanyName.setPreferredSize(new Dimension(400, 20));
		lblCompanyName.setForeground(Color.BLACK);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.insets = new Insets(10, 20, 2, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlHeader.add(lblCompanyName, gc);

		// ログインユーザーの所属部門とユーザー名
		lblUserName = new TLabel();
		TUIManager.addFontSize(lblUserName, 2);
		lblUserName.setPreferredSize(new Dimension(400, 20));
		lblUserName.setForeground(Color.BLACK);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(2, 20, 2, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlHeader.add(lblUserName, gc);

		pnlLfAndTime = new TPanel();
		pnlLfAndTime.setOpaque(false);
		pnlLfAndTime.setBackground(Color.white);
		pnlLfAndTime.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridheight = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(pnlLfAndTime, gc);

		// タイム
		lblTime.setForeground(Color.BLACK);
		TUIManager.addFontSize(lblTime, 2);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(5, 0, 3, 0);
		pnlLfAndTime.add(lblTime, gc);

		pnlLf.setLayout(new GridBagLayout());
		pnlLf.setOpaque(false);
		pnlLf.setBackground(Color.white);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLfAndTime.add(pnlLf, gc);

		// L&Fボタン（Blue）
		btnBlue.setPreferredSize(new Dimension(15, 15));
		btnBlue.setMinimumSize(new Dimension(20, 20));
		btnBlue.setMaximumSize(new Dimension(20, 20));
		btnBlue.setBorderPainted(false);
		btnBlue.setOpaque(false);
		btnBlue.setBackground(new Color(176, 196, 222));
		btnBlue.setToolTipText("blue");
		btnBlue.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnBlue, gc);

		// L&Fボタン（Pink）
		btnPink.setPreferredSize(new Dimension(15, 15));
		btnPink.setMinimumSize(new Dimension(20, 20));
		btnPink.setMaximumSize(new Dimension(20, 20));
		btnPink.setBorderPainted(false);
		btnPink.setOpaque(false);
		btnPink.setBackground(new Color(255, 192, 203));
		btnPink.setToolTipText("pink");
		btnPink.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnPink, gc);

		// L&Fボタン（Orange）
		btnOrange.setPreferredSize(new Dimension(15, 15));
		btnOrange.setMinimumSize(new Dimension(20, 20));
		btnOrange.setMaximumSize(new Dimension(20, 20));
		btnOrange.setBorderPainted(false);
		btnOrange.setOpaque(false);
		btnOrange.setBackground(new Color(255, 222, 173));
		btnOrange.setToolTipText("orange");
		btnOrange.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnOrange, gc);

		// L&Fボタン（Green）
		btnGreen.setPreferredSize(new Dimension(15, 15));
		btnGreen.setMinimumSize(new Dimension(20, 20));
		btnGreen.setMaximumSize(new Dimension(20, 20));
		btnGreen.setBorderPainted(false);
		btnGreen.setOpaque(false);
		btnGreen.setBackground(new Color(152, 251, 152));
		btnGreen.setToolTipText("green");
		btnGreen.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 3;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnGreen, gc);

		// L&Fボタン（Gray）
		btnGray.setPreferredSize(new Dimension(15, 15));
		btnGray.setMinimumSize(new Dimension(20, 20));
		btnGray.setMaximumSize(new Dimension(20, 20));
		btnGray.setBorderPainted(false);
		btnGray.setOpaque(false);
		btnGray.setBackground(new Color(211, 211, 211));
		btnGray.setToolTipText("gray");
		btnGray.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 4;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnGray, gc);

		// L&Fボタン（White）
		btnWhite.setPreferredSize(new Dimension(15, 15));
		btnWhite.setMinimumSize(new Dimension(20, 20));
		btnWhite.setMaximumSize(new Dimension(20, 20));
		btnWhite.setBorderPainted(false);
		btnWhite.setOpaque(false);
		btnWhite.setBackground(new Color(224, 255, 255));
		btnWhite.setToolTipText("white");
		btnWhite.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 5;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnWhite, gc);

		// L&Fボタン（メニューType1）
		btnType1.setIcon(ResourceUtil.getImage(this.getClass(), "images/menuType1.png"));
		btnType1.setPreferredSize(new Dimension(16, 16));
		btnType1.setMinimumSize(new Dimension(20, 20));
		btnType1.setMaximumSize(new Dimension(20, 20));
		btnType1.setBorderPainted(false);
		btnType1.setOpaque(false);
		btnType1.setBackground(new Color(224, 255, 255));
		btnType1.setToolTipText("type1");
		btnType1.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 6;
		gc.gridy = 0;
		gc.insets = new Insets(0, 15, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnType1, gc);

		// L&Fボタン（メニューType2）
		btnType2.setIcon(ResourceUtil.getImage(this.getClass(), "images/menuType2.png"));
		btnType2.setPreferredSize(new Dimension(16, 16));
		btnType2.setMinimumSize(new Dimension(20, 20));
		btnType2.setMaximumSize(new Dimension(20, 20));
		btnType2.setBorderPainted(false);
		btnType2.setOpaque(false);
		btnType2.setBackground(new Color(224, 255, 255));
		btnType2.setToolTipText("type2");
		btnType2.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 7;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnType2, gc);

		// L&Fボタン（メニューType3）
		btnType3.setIcon(ResourceUtil.getImage(this.getClass(), "images/menuType3.png"));
		btnType3.setPreferredSize(new Dimension(16, 16));
		btnType3.setMinimumSize(new Dimension(20, 20));
		btnType3.setMaximumSize(new Dimension(20, 20));
		btnType3.setBorderPainted(false);
		btnType3.setOpaque(false);
		btnType3.setBackground(new Color(224, 255, 255));
		btnType3.setToolTipText("type3");
		btnType3.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 8;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnType3, gc);

		int gridx = getIconStartIndex();

		{
			if (ClientConfig.isFlagOn("trans.ui.change.font.size.enabled")) {
				// Header UI Font Size

				ctrlFontSize.setLangMessageID("SIZE");
				ctrlFontSize.setEditable(false);
				ctrlFontSize.setLabelSize(35);
				ctrlFontSize.setComboSize(55);
				{
					// 変更可能サイズ
					ctrlFontSize.addTextValueItem(10, "10pt");
					ctrlFontSize.addTextValueItem(11, "11pt");
					ctrlFontSize.addTextValueItem(12, "12pt");
					ctrlFontSize.addTextValueItem(13, "13pt");
					ctrlFontSize.addTextValueItem(14, "14pt");
					ctrlFontSize.addTextValueItem(15, "15pt");
				}
				ctrlFontSize.setFocusable(false);
				ctrlFontSize.setToolTipText("UI FONT SIZE");
				for (int i = 0; i < ctrlFontSize.getComboBox().getMouseWheelListeners().length; i++) {
					MouseWheelListener l = ctrlFontSize.getComboBox().getMouseWheelListeners()[i];
					ctrlFontSize.getComboBox().removeMouseWheelListener(l);
				}

				int fontSize = getUIFontSize();
				ctrlFontSize.setSelectedItemValue(fontSize);

				gc = new GridBagConstraints();
				gc.gridx = gridx++;
				gc.gridheight = 2;
				gc.insets = new Insets(0, 10, 2, 0);
				gc.anchor = GridBagConstraints.SOUTHEAST;
				pnlHeader.add(ctrlFontSize, gc);
			}
		}

		// プリンター設定ボタン
		btnPrintSetup.setIcon(ResourceUtil.getImage(this.getClass(), "images/printer.png"));
		btnPrintSetup.setPreferredSize(new Dimension(30, 30));
		btnPrintSetup.setMinimumSize(new Dimension(30, 30));
		btnPrintSetup.setMaximumSize(new Dimension(30, 30));
		btnPrintSetup.setBorderPainted(false);
		btnPrintSetup.setOpaque(false);
		btnPrintSetup.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 10, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnPrintSetup, gc);

		{
			// カスタマイズ用ボタン追加

			if (this.instanceList != null) {
				for (TMainInitialInterface clazz : this.instanceList) {
					List<TButton> btnList = clazz.getAddonButtonList();
					if (btnList == null || btnList.isEmpty()) {
						continue;
					}

					for (TButton btn : btnList) {

						gc = new GridBagConstraints();
						gc.gridx = gridx++;
						gc.gridheight = 2;
						gc.insets = new Insets(0, 5, 2, 0);
						gc.anchor = GridBagConstraints.SOUTHEAST;
						pnlHeader.add(btn, gc);

					}
				}
			}
		}

		// パスワード変更ボタン
		btnPassword.setIcon(ResourceUtil.getImage(this.getClass(), "images/pass.png"));
		btnPassword.setPreferredSize(new Dimension(30, 30));
		btnPassword.setMinimumSize(new Dimension(30, 30));
		btnPassword.setMaximumSize(new Dimension(30, 30));
		btnPassword.setBorderPainted(false);
		btnPassword.setOpaque(false);
		btnPassword.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 5, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnPassword, gc);

		// パスワード変更ボタン
		btnVersion.setIcon(ResourceUtil.getImage(this.getClass(), "images/version.png"));
		btnVersion.setPreferredSize(new Dimension(30, 30));
		btnVersion.setMinimumSize(new Dimension(30, 30));
		btnVersion.setMaximumSize(new Dimension(30, 30));
		btnVersion.setBorderPainted(false);
		btnVersion.setOpaque(false);
		btnVersion.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 5, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnVersion, gc);

		// ログアウトボタン
		btnLogOut.setIcon(ResourceUtil.getImage(this.getClass(), "images/logout.png"));
		btnLogOut.setPreferredSize(new Dimension(30, 30));
		btnLogOut.setMinimumSize(new Dimension(30, 30));
		btnLogOut.setMaximumSize(new Dimension(30, 30));
		btnLogOut.setBorderPainted(false);
		btnLogOut.setOpaque(false);
		btnLogOut.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 5, 2, 20);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnLogOut, gc);
	}

	/**
	 * @return UIデフォルトフォントサイズ
	 */
	protected int getUIFontSize() {
		return (int) ClientConfig.getClientFontSize();
	}

	/**
	 * @return 右上のアイコン表示GridX
	 */
	protected int getIconStartIndex() {
		return 9;
	}

	/**
	 * 作業領域の配置
	 */
	public void allocateBody() {

		// 作業領域配置
		pnlBody.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);

		// 作業領域をメニュー領域とメイン領域に分割
		pnlMenu.setMinimumSize(new Dimension(0, 0));
		pnlMain.setMinimumSize(new Dimension(0, 0));
		spBody.setContinuousLayout(true);
		spBody.setDividerLocation(200);
		InputMap im = spBody.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), null);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), null);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(spBody, gc);

		// メニュータブの幅設定
		pnlMenu.setLayout(new GridBagLayout());

		menuTab.switchMode();

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 5, 0, 0);
		pnlMenu.add(menuTab, gc);

		// メインタブの幅設定
		mainTab.switchMode();

		pnlMain.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlMain.add(mainTab, gc);
	}

	/**
	 * フッター領域の配置
	 */
	public void allocateFooter() {

		GridBagConstraints gc = new GridBagConstraints();
		pnlFooter.setPreferredSize(new Dimension(0, 10));
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlFooter, gc);

	}

}