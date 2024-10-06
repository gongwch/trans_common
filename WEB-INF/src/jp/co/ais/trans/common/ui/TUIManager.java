package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.*;

import jp.co.ais.plaf.*;
import jp.co.ais.plaf.acryl.*;
import jp.co.ais.plaf.aero.*;
import jp.co.ais.plaf.hifi.*;
import jp.co.ais.plaf.mint.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * UI設定
 */
public class TUIManager {

	/** 初期L&F */
	public static String initLookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/** 特定L&F */
	public static String fixedLookAndFeel = "";

	/** Windowsフォント */
	public static Map fontMap = new TreeMap();

	/** デフォルトフォント */
	public static Font defaultFont = null;

	static {
		try {
			try {
				UIManager.setLookAndFeel(initLookAndFeel);
			} catch (Throwable ex) {
				// WindowsLFが存在してない場合はエラー不要
			}

			// fontMap初期化
			initDefaultFontMap();

			try {
				fixedLookAndFeel = ClientConfig.getProperty("trans.customize.lookandfeel");
			} catch (Throwable e) {
				// エラーなし
			}

		} catch (Exception e) {
			throw new TRuntimeException(e);
		}
	}

	/**
	 * fontMap初期化
	 */
	public static void initDefaultFontMap() {

		int fontSize = (int) ClientConfig.getClientFontSize();

		try {

			String fontName = ClientConfig.getProperty("trans.default.font.name"); // "Arial"
			defaultFont = new Font(fontName, Font.PLAIN, fontSize);

			for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
				if (entry.getKey().toString().indexOf("font") != -1) {
					fontMap.put(entry.getKey(), new FontUIResource(fontName, Font.PLAIN, fontSize));
				}
			}
		} catch (Throwable e) {
			// エラーなし

			for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
				if (entry.getKey().toString().indexOf("font") != -1) {
					fontMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	/**
	 * 指定サイズでfontMap初期化
	 * 
	 * @param fontSize
	 */
	public static void initDefaultFontMap(int fontSize) {

		try {
			String fontName = ClientConfig.getProperty("trans.default.font.name"); // "Arial"
			defaultFont = new Font(fontName, Font.PLAIN, fontSize);

			for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
				if (entry.getKey().toString().indexOf("font") != -1) {
					fontMap.put(entry.getKey(), new FontUIResource(fontName, Font.PLAIN, fontSize));
				}
			}
		} catch (Throwable e) {
			// エラーなし

			for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
				if (entry.getKey().toString().indexOf("font") != -1) {
					fontMap.put(entry.getKey(), new FontUIResource("Dialog", Font.PLAIN, fontSize));
				}
			}
		}
	}

	/**
	 * Look & Feelを設定する。
	 * 
	 * @param lookAndFeel
	 * @throws Exception
	 */
	public static void setLookAndFeel(String lookAndFeel) throws Exception {
		if (!initLookAndFeel.equals(lookAndFeel) && !Util.isNullOrEmpty(fixedLookAndFeel)) {
			UIManager.setLookAndFeel(fixedLookAndFeel);
		} else {
			UIManager.setLookAndFeel(lookAndFeel);
		}
		updateLookAndFeel();
	}

	/**
	 * 現在のLook & Feelを取得する.
	 * 
	 * @return Look & Feelクラス名
	 */
	public static LookAndFeelType getLookAndFeelType() {
		return LookAndFeelType.get(UIManager.getLookAndFeel().getClass().getName());
	}

	/**
	 * Look & Feelのその他更新
	 */
	protected static void updateLookAndFeel() {

		// パスワードフィールド表示を*に変更
		UIManager.put("PasswordField.echoChar", '*');

		// 使用不可のコンボボックスの文字色を黒字にする
		UIManager.put("ComboBox.disabledForeground", new Color(20, 20, 20));

		// フォントはWindowsフォントを使用
		for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
			if (entry.getKey().toString().indexOf("font") != -1) {
				UIManager.put(entry.getKey(), fontMap.get(entry.getKey()));
			}
		}
	}

	/**
	 * システムUI設定
	 * 
	 * @param comp コンポーネント
	 * @throws Exception
	 */
	public static void setUI(Component comp) throws Exception {

		// デフォルト
		setUI(comp, LookAndFeelType.MINT, LookAndFeelColor.White);
	}

	/**
	 * システムUI設定
	 * 
	 * @param comp コンポーネント
	 * @param type LookAndFeelタイプ
	 * @param color 配色
	 * @throws Exception
	 */
	public static void setUI(Component comp, LookAndFeelType type, LookAndFeelColor color) throws Exception {

		// 引数がNULLの場合はデフォルトのLook And Feelを設定
		if (type == null) {
			type = LookAndFeelType.MINT;
			color = LookAndFeelColor.White;
		}

		// テーマ初期化
		switch (type) {
			case ACRYL:
				AcrylLookAndFeel.setTheme("Default");
				break;

			case AERO:
				AeroLookAndFeel.setTheme("Default");
				break;

			case HIFI:
				HiFiLookAndFeel.setTheme("Default");
				break;

			case MINT: // Mintはカラー対応
				MintLookAndFeel.setTheme(color == null ? "Default" : color.name());
				break;

			default:
				break;
		}

		setLookAndFeel(type.value);
		updateLookAndFeel();

		updateComponentTreeUI(comp);

		// TMainインスタンスも更新する
		if (TMainCtrl.getInstance() != null) {
			TMainCtrl ctrl = TMainCtrl.getInstance();
			if (ctrl.frames != null) {
				for (Entry<String, TFrame> entry : ctrl.frames.entrySet()) {
					TFrame c = entry.getValue();
					if (c != null) {
						updateComponentTreeUI0(c);
					}
				}
			}

			if (ctrl.customerizeFrames != null) {
				for (int i = 0; i < ctrl.customerizeFrames.size(); i++) {
					TFrame c = ctrl.customerizeFrames.get(i);
					if (c != null) {
						updateComponentTreeUI0(c);
					}
				}
			}
		}
	}

	/**
	 * UI更新（指定L&Fを使う）
	 * 
	 * @param c
	 */
	public static void updateComponentTreeUI(Component c) {
		updateComponentTreeUI0(c);
		c.invalidate();
		c.validate();
		c.repaint();
	}

	/**
	 * UI更新（指定L&Fを使う）
	 * 
	 * @param c
	 */
	protected static void updateComponentTreeUI0(Component c) {

		// TextFieldをUI更新
		if (c instanceof TTextField) {
			TTextField comp = (TTextField) c;
			if (comp.getFont() != null) {
				comp.setFont(defaultFont);
			}
		}

		// ComboBoxをUI更新
		if (c instanceof TComboBox) {
			TComboBox comp = (TComboBox) c;
			if (comp.getFont() != null) {
				comp.setFont(defaultFont);
			}
		}

		// TTableは列コンポをUI更新
		if (c instanceof TTable) {
			TTable tbl = (TTable) c;
			List<TTableColumn> list = tbl.getTableColumn();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					TTableColumn col = list.get(i);

					if (col == null) {
						continue;
					}

					if (col.getComponent() == null) {
						continue;
					}

					if (col.getComponent() instanceof Component) {
						updateComponentTreeUI0((Component) col.getComponent());
					}
				}
			}
		}

		if (c instanceof JComponent) {
			JComponent jc = (JComponent) c;
			jc.updateUI();
			JPopupMenu jpm = jc.getComponentPopupMenu();
			if (jpm != null && jpm.isVisible() && jpm.getInvoker() == jc) {
				updateComponentTreeUI(jpm);
			}
		}
		Component[] children = null;
		if (c instanceof JMenu) {
			children = ((JMenu) c).getMenuComponents();
		} else if (c instanceof Container) {
			children = ((Container) c).getComponents();
		}
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				updateComponentTreeUI0(children[i]);
			}
		}
	}

	/**
	 * メニュータイプの切替
	 * 
	 * @param menuType メニュータイプ
	 */
	public static void setMenuType(MenuType menuType) {
		switch (menuType) {
			case TYPE1:
				ColorTabbedPaneUI.setType(ColorTabbedPaneUI.TYPE1);
				break;

			case TYPE2:
				ColorTabbedPaneUI.setType(ColorTabbedPaneUI.TYPE2);
				break;

			case TYPE3:
				ColorTabbedPaneUI.setType(ColorTabbedPaneUI.TYPE3);
				break;
		}
	}

	/**
	 * メニュー用カラーUI
	 * 
	 * @param comp コンポーネント
	 * @return UI
	 */
	public static ComponentUI getMenuUI(JComponent comp) {
		return ColorTabbedPaneUI.createUI(comp);
	}

	/**
	 * フォントサイズを変更する
	 * 
	 * @param comp 対象コンポーネント
	 * @param addSize プラスサイズ
	 */
	public static void addFontSize(JComponent comp, float addSize) {
		Font cfont = comp.getFont();
		if (defaultFont != null) {
			comp.setFont(defaultFont.deriveFont(cfont.getSize() + addSize));
		} else {
			comp.setFont(cfont.deriveFont(cfont.getSize() + addSize));
		}
	}

	/**
	 * MONOSPACED(文字幅均一)フォント名称設定
	 * 
	 * @param comp 対象コンポーネント
	 */
	public static void setMonospacedFont(JComponent comp) {
		setFontName(comp, Font.MONOSPACED);
	}

	/**
	 * 論理フォント設定
	 * 
	 * @param comp 対象コンポーネント
	 */
	public static void setLogicalFont(JComponent comp) {
		setFontName(comp, "Dialog");
	}

	/**
	 * フォント名称設定
	 * 
	 * @param comp 対象コンポーネント
	 * @param name 新しい設定名称
	 */
	public static void setFontName(JComponent comp, String name) {
		Font cfont = comp.getFont();
		if (defaultFont != null) {
			name = defaultFont.getFontName();
		}
		comp.setFont(new Font(name, cfont.getStyle(), cfont.getSize()));
	}

	/**
	 * コンポーネントのロケール設定
	 * 
	 * @param locale ロケール
	 */
	public static void setUILocale(Locale locale) {

		// コンポーネントLocale初期化.
		JComponent.setDefaultLocale(locale);
	}

	/**
	 * 現在のLook & Feelの文字色を取得する
	 * 
	 * @return 文字色
	 */
	public static Color getTextDefaultForeground() {
		return UIManager.getColor("TextField.foreground");
	}

	/**
	 * JSplitPaneダブルクリックを、最大幅移動に変更するモード.<br>
	 * 現状、MINTのみ
	 * 
	 * @param splitPane JSplitPane
	 */
	public static void switchMaximumMode(JSplitPane splitPane) {
		if (TUIManager.getLookAndFeelType() == LookAndFeelType.MINT && splitPane.getUI() instanceof MintSplitPaneUI) {
			MintSplitPaneUI ui = (MintSplitPaneUI) splitPane.getUI();
			ui.swichMaxMode();
		}
	}
}