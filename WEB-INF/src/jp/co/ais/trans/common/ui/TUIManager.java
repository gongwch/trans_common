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
 * UI�ݒ�
 */
public class TUIManager {

	/** ����L&F */
	public static String initLookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/** ����L&F */
	public static String fixedLookAndFeel = "";

	/** Windows�t�H���g */
	public static Map fontMap = new TreeMap();

	/** �f�t�H���g�t�H���g */
	public static Font defaultFont = null;

	static {
		try {
			try {
				UIManager.setLookAndFeel(initLookAndFeel);
			} catch (Throwable ex) {
				// WindowsLF�����݂��ĂȂ��ꍇ�̓G���[�s�v
			}

			// fontMap������
			initDefaultFontMap();

			try {
				fixedLookAndFeel = ClientConfig.getProperty("trans.customize.lookandfeel");
			} catch (Throwable e) {
				// �G���[�Ȃ�
			}

		} catch (Exception e) {
			throw new TRuntimeException(e);
		}
	}

	/**
	 * fontMap������
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
			// �G���[�Ȃ�

			for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
				if (entry.getKey().toString().indexOf("font") != -1) {
					fontMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	/**
	 * �w��T�C�Y��fontMap������
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
			// �G���[�Ȃ�

			for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
				if (entry.getKey().toString().indexOf("font") != -1) {
					fontMap.put(entry.getKey(), new FontUIResource("Dialog", Font.PLAIN, fontSize));
				}
			}
		}
	}

	/**
	 * Look & Feel��ݒ肷��B
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
	 * ���݂�Look & Feel���擾����.
	 * 
	 * @return Look & Feel�N���X��
	 */
	public static LookAndFeelType getLookAndFeelType() {
		return LookAndFeelType.get(UIManager.getLookAndFeel().getClass().getName());
	}

	/**
	 * Look & Feel�̂��̑��X�V
	 */
	protected static void updateLookAndFeel() {

		// �p�X���[�h�t�B�[���h�\����*�ɕύX
		UIManager.put("PasswordField.echoChar", '*');

		// �g�p�s�̃R���{�{�b�N�X�̕����F�������ɂ���
		UIManager.put("ComboBox.disabledForeground", new Color(20, 20, 20));

		// �t�H���g��Windows�t�H���g���g�p
		for (Map.Entry entry : UIManager.getLookAndFeel().getDefaults().entrySet()) {
			if (entry.getKey().toString().indexOf("font") != -1) {
				UIManager.put(entry.getKey(), fontMap.get(entry.getKey()));
			}
		}
	}

	/**
	 * �V�X�e��UI�ݒ�
	 * 
	 * @param comp �R���|�[�l���g
	 * @throws Exception
	 */
	public static void setUI(Component comp) throws Exception {

		// �f�t�H���g
		setUI(comp, LookAndFeelType.MINT, LookAndFeelColor.White);
	}

	/**
	 * �V�X�e��UI�ݒ�
	 * 
	 * @param comp �R���|�[�l���g
	 * @param type LookAndFeel�^�C�v
	 * @param color �z�F
	 * @throws Exception
	 */
	public static void setUI(Component comp, LookAndFeelType type, LookAndFeelColor color) throws Exception {

		// ������NULL�̏ꍇ�̓f�t�H���g��Look And Feel��ݒ�
		if (type == null) {
			type = LookAndFeelType.MINT;
			color = LookAndFeelColor.White;
		}

		// �e�[�}������
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

			case MINT: // Mint�̓J���[�Ή�
				MintLookAndFeel.setTheme(color == null ? "Default" : color.name());
				break;

			default:
				break;
		}

		setLookAndFeel(type.value);
		updateLookAndFeel();

		updateComponentTreeUI(comp);

		// TMain�C���X�^���X���X�V����
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
	 * UI�X�V�i�w��L&F���g���j
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
	 * UI�X�V�i�w��L&F���g���j
	 * 
	 * @param c
	 */
	protected static void updateComponentTreeUI0(Component c) {

		// TextField��UI�X�V
		if (c instanceof TTextField) {
			TTextField comp = (TTextField) c;
			if (comp.getFont() != null) {
				comp.setFont(defaultFont);
			}
		}

		// ComboBox��UI�X�V
		if (c instanceof TComboBox) {
			TComboBox comp = (TComboBox) c;
			if (comp.getFont() != null) {
				comp.setFont(defaultFont);
			}
		}

		// TTable�͗�R���|��UI�X�V
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
	 * ���j���[�^�C�v�̐ؑ�
	 * 
	 * @param menuType ���j���[�^�C�v
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
	 * ���j���[�p�J���[UI
	 * 
	 * @param comp �R���|�[�l���g
	 * @return UI
	 */
	public static ComponentUI getMenuUI(JComponent comp) {
		return ColorTabbedPaneUI.createUI(comp);
	}

	/**
	 * �t�H���g�T�C�Y��ύX����
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param addSize �v���X�T�C�Y
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
	 * MONOSPACED(�������ψ�)�t�H���g���̐ݒ�
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 */
	public static void setMonospacedFont(JComponent comp) {
		setFontName(comp, Font.MONOSPACED);
	}

	/**
	 * �_���t�H���g�ݒ�
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 */
	public static void setLogicalFont(JComponent comp) {
		setFontName(comp, "Dialog");
	}

	/**
	 * �t�H���g���̐ݒ�
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param name �V�����ݒ薼��
	 */
	public static void setFontName(JComponent comp, String name) {
		Font cfont = comp.getFont();
		if (defaultFont != null) {
			name = defaultFont.getFontName();
		}
		comp.setFont(new Font(name, cfont.getStyle(), cfont.getSize()));
	}

	/**
	 * �R���|�[�l���g�̃��P�[���ݒ�
	 * 
	 * @param locale ���P�[��
	 */
	public static void setUILocale(Locale locale) {

		// �R���|�[�l���gLocale������.
		JComponent.setDefaultLocale(locale);
	}

	/**
	 * ���݂�Look & Feel�̕����F���擾����
	 * 
	 * @return �����F
	 */
	public static Color getTextDefaultForeground() {
		return UIManager.getColor("TextField.foreground");
	}

	/**
	 * JSplitPane�_�u���N���b�N���A�ő啝�ړ��ɕύX���郂�[�h.<br>
	 * ����AMINT�̂�
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