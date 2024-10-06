package jp.co.ais.trans.common.gui;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * GUI�̂��߂̃��[�e�B���e�B.
 */
public class TGuiUtil {

	/** �V���[�g�J�b�g�L�[���� */
	public static final int SKEY_NONE = -1;

	/** �L�[�̂� */
	public static final int WITH_NO_KEY = 0;

	/** Shift + �L�[ */
	public static final int WITH_SHIFT_KEY = KeyEvent.VK_SHIFT;

	/** Ctrl + �L�[ */
	public static final int WITH_CTRL_KEY = KeyEvent.VK_CONTROL;

	/** Alt + �L�[ */
	public static final int WITH_ALT_KEY = KeyEvent.VK_ALT;

	/** �{�^���^�C�v */
	public static final String TYPE_BUTTON = "button";

	/** �e�L�X�g�t�B�[���h�^�C�v */
	public static final String TYPE_TEXTFIELD = "text";

	/** ���x���^�C�v */
	public static final String TYPE_LABEL = "label";

	/** �J�����_�[�^�C�v */
	public static final String TYPE_CALENDER = "calender";

	/** �{�^���t�B�[���h�^�C�v */
	public static final String TYPE_BUTTON_FIELD = "buttonfield";

	/** �������[�v����̂��߂́A�ċA���x�����x */
	protected static int LOOP_CHECK_NUM = 100;

	/** �E�N���b�N���j���[�̒P��(�e�L�X�g�t�B�[���h�p) */
	protected static String[] popupWords = { "cut", "copy", "paste", "select all" };

	/**
	 * J�R���|�[�l���g��setHorizontalAlignment(int)���g������
	 * 
	 * @deprecated
	 * @param comp �{�^��
	 * @param align �ꏊ 0:���A1:�����A2:�E
	 */
	public static void setButtonHorizonalAlignment(JButton comp, int align) {
		if (align == 0) {
			comp.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (align == 1) {
			comp.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			comp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}

	/**
	 * J�R���|�[�l���g��setHorizontalAlignment(int)���g������
	 * 
	 * @deprecated
	 * @param comp �e�L�X�g
	 * @param align �ꏊ 0:���A1:�����A2:�E
	 */
	public static void setTextFieldHorizonalAlignment(JTextField comp, int align) {
		if (align == 0) {
			comp.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (align == 1) {
			comp.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			comp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}

	/**
	 * J�R���|�[�l���g��setHorizontalAlignment(int)���g������
	 * 
	 * @deprecated
	 * @param comp ���x��
	 * @param align �ꏊ 0:���A1:�����A2:�E
	 */
	public static void setLabelHorizonalAlignment(JLabel comp, int align) {
		if (align == 0) {
			comp.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (align == 1) {
			comp.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			comp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}

	/**
	 * �R���|�[�l���g�̉�����ݒ肷��.
	 * 
	 * @param comp �R���|�[�l���g
	 * @param size �ݒ�T�C�Y
	 */
	public static void setComponentSize(JComponent comp, Dimension size) {
		comp.setPreferredSize(size);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setSize(size);
	}

	/**
	 * �����w�肷��.MIN,MAX,PER
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param width ��
	 */
	public static void setComponentWidth(JComponent comp, int width) {
		setComponentSize(comp, new Dimension(width, comp.getPreferredSize().height));
	}

	/**
	 * �������w�肷��.MIN,MAX,PER
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param height ����
	 */
	public static void setComponentHeight(JComponent comp, int height) {
		setComponentSize(comp, new Dimension(comp.getPreferredSize().width, height));
	}

	/**
	 * �T�C�Y���w�肷��.MIN,MAX,PER
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @param width ��
	 * @param height ����
	 */
	public static void setComponentSize(JComponent comp, int width, int height) {
		setComponentSize(comp, new Dimension(width, height));
	}

	/**
	 * �e�R���e�i�����ǂ�Ajava.awt.Window, java.awt.Applet��T��.
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public static Container getParentRoot(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Window || p instanceof Applet) {
				return p;
			}
		}
		return p;
	}

	/**
	 * �e�R���e�i�����ǂ�Ajava.awt.Window��T��.
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public static Window getParentWindow(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Window) {
				return (Window) p;
			}
		}
		return null;
	}

	/**
	 * �e�R���e�i�����ǂ�ATDialog��T��.
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public static TDialog getParentTDialog(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof TDialog) {
				return (TDialog) p;
			}
		}
		return null;
	}

	/**
	 * �e�R���e�i�����ǂ�Ajava.awt.Frame, java.awt.Dialog��T��.<br>
	 * �i������Container�Łj
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public static Container getParentFrameOrDialog(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Frame || p instanceof Dialog) {
				return p;
			}
		}
		return p;
	}

	/**
	 * �e�R���e�i�����ǂ�Ajava.awt.Frame, java.awt.Dialog��T��.<br>
	 * �i������Component�Łj
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public static Component getParentFrameOrDialog2(Component p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Frame || p instanceof Dialog) {
				return p;
			}
		}
		return p;
	}

	/**
	 * �e�R���e�i�����ǂ�ATPanelBusiness, TDialog, TFrame��T��.
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public static Container getPanelBusiness(Container p) {

		if (p == null) {
			return null;
		}

		for (; p != null; p = p.getParent()) {
			if (p instanceof TPanelBusiness || p instanceof TDialog || p instanceof TFrame) {
				return p;
			}
		}
		return null;
	}

	/**
	 * �t�@���N�V�����L�[�̏�����TPanelBusiness�ɑ�s������.
	 * 
	 * @param cont
	 * @param evt
	 */
	public static void dispatchPanelBusinessFunctionKey(Container cont, KeyEvent evt) {

		if (isDispatchPanelBusinessFunctionKey(cont, evt)) {
			Container busi = TGuiUtil.getPanelBusiness(cont);
			busi.dispatchEvent(evt);
		}
	}

	/**
	 * �t�@���N�V�����L�[�̏�����TPanelBusiness�ɑ�s�����邱�Ƃ��ł��邩�`�F�b�N����.
	 * 
	 * @param cont
	 * @param evt
	 * @return true:������s
	 */
	public static boolean isDispatchPanelBusinessFunctionKey(Container cont, KeyEvent evt) {

		Container busi = TGuiUtil.getPanelBusiness(cont);
		if (busi == null) {
			return false;
		}

		int fkey = TGuiUtil.checkFunctionKeyEvent(evt);

		if (fkey <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Enter�L�[�̏���������.
	 * 
	 * @param cont
	 * @param evt
	 */
	public static void transferFocusByEnterKey(Container cont, KeyEvent evt) {

		if (cont instanceof TTableComponent) {
			// �e�[�u���Z���G�f�B�^���p�̓t�H�[�J�X����
			if (((TTableComponent) cont).isTableCellEditor()) {
				return;
			}
		}

		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

			Container root = getPanelBusiness(cont);
			if (root == null) {
				cont.transferFocus();
				return;
			}

			// �_�C�A���O��Active�łȂ��ꍇ�A�������Ȃ�
			if (root instanceof TDialog && !((TDialog) root).isActive()) {
				return;
			}

			TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;

			boolean mode = tp.getModeEnter();
			tp.setModeEnter(true);

			if (evt.isShiftDown()) {
				cont.transferFocusBackward();
			} else {
				cont.transferFocus();
			}

			tp.setModeEnter(mode);

			// Enter�L�[��Event�łȂ���ΐݒ�
			if (evt.getKeyChar() != '0') {
				tp.setEventBefore(new KeyEvent((Component) evt.getSource(), evt.getID(), evt.getWhen(), evt
					.getModifiers(), evt.getKeyCode(), '0'));
			} else {
				tp.setEventBefore(null);
			}

		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {

			Container root = getPanelBusiness(cont);
			if (root == null) {
				cont.transferFocus();
				return;
			}

			// �_�C�A���O��Active�łȂ��ꍇ�A�������Ȃ�
			if (root instanceof TDialog && !((TDialog) root).isActive()) {
				return;
			}

			TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;

			boolean mode = tp.getModeEnter();
			tp.setModeEnter(false); // tab���[�h

			if (evt.isShiftDown()) {
				cont.transferFocusBackward();
			} else {
				cont.transferFocus();
			}

			tp.setModeEnter(mode);

			// Tab�L�[��Event�łȂ���ΐݒ�
			if (evt.getKeyChar() != '0') {
				tp.setEventBefore(new KeyEvent((Component) evt.getSource(), evt.getID(), evt.getWhen(), evt
					.getModifiers(), evt.getKeyCode(), '0'));
			} else {
				tp.setEventBefore(null);
			}

		} else {

			Container root = getPanelBusiness(cont);
			if (root == null) {
				return;
			}

			TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;
			tp.setEventBefore(null);
		}
	}

	/**
	 * Enter�L�[�̏������ēx�s��.
	 * 
	 * @param cont
	 */
	public static void transferBeforeFocusByEnterKey(Container cont) {

		Container root = getPanelBusiness(cont);
		if (root == null) {
			return;
		}

		TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;

		// �O��̃C�x���g
		KeyEvent beforeEvent = tp.getEventBefore();
		if (beforeEvent == null) {
			return;
		}

		if (beforeEvent.getSource() instanceof JTextComponent) {

			SwingUtilities.invokeLater(new ExecLater(cont, beforeEvent));
			tp.setEventBefore(null);
		}
	}

	// message ID�֘A

	/**
	 * �ċA�I�Ɏq��Component��messageID�ɑΉ����郁�b�Z�[�W��o�^����.
	 * 
	 * @param cont �J�����g�R���e�i.
	 * @param lang ����R�[�h
	 */
	public static void translateLangMessageID(Container cont, String lang) {

		TGuiUtil.recursiveTranslateLangMessageID(TGuiUtil.getParentRoot(cont), lang, 0);
	}

	/**
	 * �ċA�I�Ɏq��Component��messageID�ɑΉ����郁�b�Z�[�W��o�^����.
	 * 
	 * @param cont �J�����g�R���e�i.
	 * @param lang ����R�[�h
	 * @param level ���[�v���E�K�w.
	 */
	public static void recursiveTranslateLangMessageID(Container cont, String lang, int level) {

		if (LOOP_CHECK_NUM < level) {
			return;
		}
		if (cont == null) {
			return;
		}

		// �p�l��.
		if (cont instanceof JPanel) {
			// border����ŁABorder��messageID��ݒ�ł���A�C�e���Ȃ� MessageID��Map�ɓo�^.
			translatePanelLangMessageID((JPanel) cont, lang);

		} else if (cont instanceof TInterfaceLangMessageID) {
			// �p�l���ȊO�ŁAMessegeID��ݒ�ł���A�C�e��.
			translateContainerLangMessageID(cont, lang);

		} else if (cont instanceof TInterfaceTableLangMessageID) {
			// Table�A�C�e��.
			translateTableLangMessageID(cont, lang);
		}

		// �q�̃R���|�[�l���g������.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// ���ׂĂ̎q�ɂ��āA�ċA�I�Ɍ�������.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				recursiveTranslateLangMessageID((Container) arrComp[i], lang, level + 1);
			}
		}

		return;
	}

	/**
	 * panel��message ID��message�ɕϊ�����. <br>
	 * border����ŁABorder��TTitledBorder class�Ȃ�A Message��ݒ肷��.
	 * 
	 * @param panel
	 * @param lang
	 */
	protected static void translatePanelLangMessageID(JPanel panel, String lang) {

		if (panel.getBorder() == null) {
			return;
		}

		// message ID��interface�������Ă���.
		if (panel.getBorder() instanceof TTitledBorder) {
			TTitledBorder tborder = (TTitledBorder) panel.getBorder();

			if (tborder.getLangMessageID() != null) {
				tborder.setTitle(MessageUtil.getWord(lang, tborder.getLangMessageID()));
			}

		} else if (panel instanceof TPanel && panel.getBorder() instanceof TitledBorder) {
			TitledBorder tborder = (TitledBorder) panel.getBorder();

			TPanel tpanel = (TPanel) panel;
			if (tpanel.getLangMessageID() != null) {
				tborder.setTitle(MessageUtil.getWord(lang, tpanel.getLangMessageID()));
			}
		}
	}

	/**
	 * Container��message ID��message�ɕϊ�����.
	 * 
	 * @param cont
	 * @param lang
	 */
	protected static void translateContainerLangMessageID(Container cont, String lang) {

		TInterfaceLangMessageID ifMessage = (TInterfaceLangMessageID) cont;
		String wordId = ifMessage.getLangMessageID();

		if (Util.isNullOrEmpty(wordId)) {
			return;
		}

		boolean isShortWord = Util.isShortLanguage(lang);

		if (cont instanceof JTabbedPane) {
			// JTabbedPane
			JTabbedPane tab = (JTabbedPane) cont;

			// �^�u�̕�ID������̂ŁA����
			String[] msgIDs = StringUtil.toArrayFromDelimitString(wordId);

			for (int i = 0; i < tab.getTabCount(); i++) {
				String wordID = (i < msgIDs.length) ? msgIDs[i] : "";

				if (isShortWord) {
					// ����̏ꍇ
					tab.setTitleAt(i, MessageUtil.getShortWord(lang, wordID));
					tab.setToolTipTextAt(i, MessageUtil.getWord(lang, wordID));

				} else {
					tab.setTitleAt(i, MessageUtil.getWord(lang, wordID));
				}
			}

			return;

		} else if (cont instanceof JDialog) {
			// Dialog
			((JDialog) cont).setTitle(MessageUtil.getWord(lang, wordId));

			return;
		}

		// ���̑�

		String text;
		String tip;

		if (isShortWord) {
			// �p��̏ꍇ
			text = MessageUtil.getShortWord(lang, wordId);
			tip = MessageUtil.getWord(lang, wordId);

		} else {
			text = MessageUtil.getWord(lang, wordId);
			tip = text;
		}

		if (cont instanceof JLabel) {
			// label.
			((JLabel) cont).setText(text);
			((JLabel) cont).setToolTipText(tip);

		} else if (cont instanceof AbstractButton) {
			// button, checkbutton, radiobutton.
			((AbstractButton) cont).setText(text);
			((AbstractButton) cont).setToolTipText(tip);

		} else if (cont instanceof JTextArea) {
			// TextArea
			((JTextArea) cont).setText(text);
			((JTextArea) cont).setToolTipText(tip);
		}
	}

	/**
	 * Table��message ID��message�ɕϊ�����.
	 * 
	 * @param cont
	 * @param lang
	 */
	protected static void translateTableLangMessageID(Container cont, String lang) {

		if (!(cont instanceof TTable)) {
			return;
		}

		TTable table = (TTable) cont;

		// �s�^�C�g��.
		if (table.getRowTitleMessageID() != null) {
			String[] arr = table.getRowTitleMessageID();
			String[] title = new String[arr.length];

			for (int i = 0; i < arr.length; i++) {
				title[i] = MessageUtil.getWord(lang, arr[i]);
			}

			table.setRowLabels(title);

			if (table.getDataSource() instanceof JCVectorDataSource) {
				((JCVectorDataSource) table.getDataSource()).setRowLabels(title);
			}
		}

		// ��^�C�g��.
		if (table.getColumnTitleMessageID() != null) {
			String[] arr = table.getColumnTitleMessageID();
			String[] title = new String[arr.length];

			for (int i = 0; i < arr.length; i++) {
				title[i] = MessageUtil.getWord(lang, arr[i]);
			}

			table.setColumnLabels(title);

			if (table.getDataSource() instanceof JCVectorDataSource) {
				((JCVectorDataSource) table.getDataSource()).setColumnLabels(title);
			}

			if (table.isAutoColumnWidthSet()) {
				for (int i = 0; i < title.length; i++) {
					if (table.getCharWidth(i) <= 0) {
						continue;
					}

					int width = title[i].getBytes().length * 2 / 3;

					if (table.getCharWidth(i) >= width) {
						continue;
					}

					table.setCharWidth(i, width);
				}
			}
		}
	}

	// tab control No.�֘A

	/**
	 * Component����tabControlNo�����W����.
	 * 
	 * @param cont �J�����g�R���e�i.
	 * @param lstTab Label��key�ɐݒ�
	 */
	static void collectTabControlNo(Container cont, List<TNameComponent> lstTab) {

		TGuiUtil.recursiveCollectTabControlNo(cont, lstTab, 0);
	}

	/**
	 * �ċA�I�Ɏq��Component����tabControlNo�����W����.
	 * 
	 * @param cont �J�����g�R���e�i.
	 * @param lstTab Label��key�ɐݒ�
	 * @param level ���[�v���E�K�w.
	 */
	protected static void recursiveCollectTabControlNo(Container cont, List<TNameComponent> lstTab, int level) {

		if (LOOP_CHECK_NUM < level) {
			return;
		}
		if (cont == null) {
			return;
		}

		// �p�l��.

		if (cont instanceof JPanel) {
			// �p�l���̂Ƃ��Ȃɂ����Ȃ�.

			// �p�l���ȊO�ŁAtabNo��ݒ�ł���A�C�e��.
		} else if (cont instanceof TInterfaceTabControl) {
			// tabNo��List�ɓo�^.
			collectCompnentTabControlNo(cont, lstTab);
		}

		// �q�̃R���|�[�l���g������.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// ���ׂĂ̎q�ɂ��āA�ċA�I�Ɍ�������.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				recursiveCollectTabControlNo((Container) arrComp[i], lstTab, level + 1);
			}
		}

		return;
	}

	/**
	 * �ċA�I�Ɏq��Component(TButton)����V���[�g�J�b�g�L�[�����W����.
	 * 
	 * @param cont �J�����g�R���e�i.
	 * @param fkyeMap �t�@���N�V�����L�[
	 * @param level ���[�v���E�K�w.
	 */
	static void recursiveCollectFunctionKey(Container cont, Map<Integer, JButton[]> fkyeMap, int level) {

		if (LOOP_CHECK_NUM < level) {
			return;
		}
		if (cont == null) {
			return;
		}

		if (cont instanceof TButton) {
			TButton button = (TButton) cont;
			int type = button.getShortcutType();

			if (type != SKEY_NONE) {

				int index = getFKeyNo(button.getShortcutKey());

				if (button.isAutoFkeyWord()) {
					// (�V���[�g�J�b�g�L�[)�����������I�ɑ}��
					StringBuilder keyStr = new StringBuilder("(");
					switch (type) {
						case KeyEvent.VK_SHIFT:
							keyStr.append("Shift+");
							break;
						case KeyEvent.VK_CONTROL:
							keyStr.append("Ctrl+");
							break;
						case KeyEvent.VK_ALT:
							keyStr.append("Alt+");
							break;
					}

					if (index == 13) {
						keyStr.append("PageUp");

					} else if (index == 14) {
						keyStr.append("PageDn");

					} else if (index == 15) {
						keyStr.append("Esc");

					} else {
						keyStr.append("F").append(index);
					}

					keyStr.append(")");

					button.setText(button.getText() + keyStr.toString());
				}

				JButton[] fbuttons = fkyeMap.get(type);
				fbuttons[index - 1] = button;
			}
		}

		// �q�̃R���|�[�l���g������.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// ���ׂĂ̎q�ɂ��āA�ċA�I�Ɍ�������.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				recursiveCollectFunctionKey((Container) arrComp[i], fkyeMap, level + 1);
			}
		}
	}

	/**
	 * F�L�[�C�x���g�ɑΉ�����ԍ���Ԃ�
	 * 
	 * @param eventKey
	 * @return F�L�[�Ή�����
	 */
	protected static int getFKeyNo(int eventKey) {
		switch (eventKey) {
			case KeyEvent.VK_F1:
				return 1;
			case KeyEvent.VK_F2:
				return 2;
			case KeyEvent.VK_F3:
				return 3;
			case KeyEvent.VK_F4:
				return 4;
			case KeyEvent.VK_F5:
				return 5;
			case KeyEvent.VK_F6:
				return 6;
			case KeyEvent.VK_F7:
				return 7;
			case KeyEvent.VK_F8:
				return 8;
			case KeyEvent.VK_F9:
				return 9;
			case KeyEvent.VK_F10:
				return 10;
			case KeyEvent.VK_F11:
				return 11;
			case KeyEvent.VK_F12:
				return 12;
			case KeyEvent.VK_PAGE_UP:
				return 13;
			case KeyEvent.VK_PAGE_DOWN:
				return 14;
			case KeyEvent.VK_ESCAPE:
				return 15;
			default:
				return 0;
		}
	}

	/**
	 * tab���o�^.
	 * 
	 * @param listTabControlNo �^�u���ԍ���component�̃��X�g
	 * @param listTabComponent tab��component���X�g
	 */
	public static void changeTabControlNo(List<TNameComponent> listTabControlNo, List<Component> listTabComponent) {

		listTabComponent.clear();

		if (listTabControlNo == null) {
			return;
		}

		TNameComponent nv;
		while (0 < listTabControlNo.size()) {
			int minPos = 0;
			int minVal = -1;
			for (int i = 0; i < listTabControlNo.size(); i++) {
				nv = listTabControlNo.get(i);
				if (nv == null) continue;

				if (minVal <= nv.getName()) {
					minVal = nv.getName();
					minPos = i;
				}
			}
			nv = listTabControlNo.get(minPos);
			listTabComponent.add(0, nv.getValue());
			listTabControlNo.remove(minPos);
		}
	}

	/**
	 * panel��message ID�����W����. <br>
	 * border����ŁABorder��TTitledBorder class�Ȃ�A Message�����W����.
	 * 
	 * @param cont
	 * @param list
	 */
	protected static void collectCompnentTabControlNo(Container cont, List<TNameComponent> list) {
		// tabNo��List�ɓo�^.
		TInterfaceTabControl ifTab = (TInterfaceTabControl) cont;
		if (0 <= ifTab.getTabControlNo()) {
			list.add(new TNameComponent(ifTab.getTabControlNo(), cont));
		}
	}

	/**
	 * key���͂̔���.
	 * 
	 * @param e �C�x���g
	 * @return 0:Function-key�łȂ� >1:Function-key�ł���
	 */
	static public int checkFunctionKeyEvent(KeyEvent e) {

		if (e == null) {
			return 0;
		}

		return getFKeyNo(e.getKeyCode());
	}

	/**
	 * ��������key���͂̔���.
	 * 
	 * @param e �C�x���g
	 * @return WITH_SHIFT_KEY | WITH_CTRL_KEY | WIDTH_ALT_KEY�̑g�ݍ��킹
	 */
	static public int checkWithKeyEvent(KeyEvent e) {

		if (e == null) {
			return 0;
		}

		int code = e.getKeyCode();
		String name = KeyEvent.getKeyText(code);

		if (name == null) {
			return 0;
		}

		int mkey = 0;

		if (e.isShiftDown()) {
			mkey |= WITH_SHIFT_KEY;
		}

		if (e.isControlDown()) {
			mkey |= WITH_CTRL_KEY;
		}

		if (e.isAltDown()) {
			mkey |= WITH_ALT_KEY;
		}

		return mkey;
	}

	/**
	 * �����ɓ���L�[��������Ă������̃`�F�b�N
	 * 
	 * @param e �L�[�C�x���g
	 * @return true:��������
	 */
	static public boolean isSpecialKeyDown(KeyEvent e) {
		if (e.isAltDown() || e.isAltGraphDown() || e.isControlDown() || e.isMetaDown() || e.isShiftDown()) {
			return true;
		}
		return false;
	}

	/**
	 * ���͉\�ȏ�Ԃɂ��邩�̃`�F�b�N
	 * 
	 * @param comp �]������R���|�[�l���g
	 * @return true:���͉\
	 */
	static public boolean isActive(Component comp) {

		if (comp == null) {
			return false;
		}

		if (!comp.isShowing()) return false;
		if (!comp.isEnabled()) return false;

		if (comp instanceof JTextField && !((JTextField) comp).isEditable()) return false;
		if (comp instanceof JComboBox && !((JComboBox) comp).isEditable()) return false;

		return true;
	}

	/**
	 * �R���|�[�l���g�̓��̓`�F�b�N
	 * 
	 * @param comp
	 * @return false:���̓`�F�b�NNG true:���̓`�F�b�NOK�܂��͓��̓A�C�e���łȂ�
	 */
	static public boolean isInputVerifyOk(Object comp) {
		if (TGuiUtil.getInpVerifier(comp) != null) {
			if (!TGuiUtil.getInpVerifier(comp).verify((JComponent) comp)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �R���|�[�l���g�̓��̓`�F�b�N�N���X�擾
	 * 
	 * @param comp
	 * @return false:���̓`�F�b�NNG true:���̓`�F�b�NOK�܂��͓��̓A�C�e���łȂ�
	 */
	static public InputVerifier getInpVerifier(Object comp) {
		if (comp instanceof JComponent) {
			JComponent jcomp = (JComponent) comp;
			return (jcomp.getInputVerifier());
		}
		return null;
	}

	/**
	 * �R���|�[�l���g�̓��̓`�F�b�N�N���X�ݒ�
	 * 
	 * @param comp
	 * @param veri
	 */
	static public void setInpVerifier(Object comp, InputVerifier veri) {
		if (comp instanceof JComponent) {
			JComponent jcomp = (JComponent) comp;
			jcomp.setInputVerifier(veri);
		}
	}

	/**
	 * �R���|�[�l���g�K�w�̏�ʂ�TButtonField�I�u�W�F�N�g��Ԃ�
	 * 
	 * @param cont �A�C�e���R���e�i
	 * @return ButtonField
	 */
	static TButtonField getUpperButtonField(Container cont) {

		for (; cont != null; cont = cont.getParent()) {

			if (cont instanceof TButtonField) {
				return (TButtonField) cont;
			}
		}

		return null;
	}

	/**
	 * �q�R���|�[�l���g��TInputVerifier�L���^������
	 * 
	 * @param win
	 * @param flag
	 */
	static public void setComponentsVerifierEnabled(Window win, boolean flag) {
		if (win == null) {
			return;
		}
		// �C���X�^���X���g���ă��b�N�������ATInputVerifier�Ƃ̋������Ȃ����B
		synchronized (win) {
			TInputVerifier.setActiveChildren(win, flag);
		}
	}

	/**
	 * �w�肳�ꂽ�^�C�v�ɍ��킹�A�c���T�C�Y�␳���s���Ԃ�.(�t�H���g�T�C�Y�ύX�p)
	 * 
	 * @param type �^�C�v
	 * @param d ���T�C�Y
	 * @return �␳��̃T�C�Y
	 */
	public static Dimension correctSize(String type, Dimension d) {

		double wdeff = ClientConfig.getDeffWidth(type);
		double hdeff = ClientConfig.getDeffHeight(type);

		if (wdeff == 0.0d && hdeff == 0.0d) {
			return d;
		}

		Dimension dim = new Dimension();
		dim.setSize(d.width + wdeff, d.height + hdeff);

		return dim;
	}

	/**
	 * UNDO/REDO�o�^
	 * 
	 * @param tc �Ώۃe�L�X�g�R���|�[�l���g
	 */
	public static void initUndoRedo(final JTextComponent tc) {

		final UndoManager manager = new UndoManager();
		tc.getDocument().addUndoableEditListener(manager);
		tc.getActionMap().put("undo", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (manager.canUndo() && tc.isEditable()) {
					manager.undo();
				}
			}
		});

		tc.getActionMap().put("redo", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (manager.canRedo() && tc.isEditable()) {
					manager.redo();
				}
			}
		});

		InputMap imap = tc.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK), "undo");
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK), "redo");
	}

	/**
	 * �E�N���b�N�J�b�g���y�[�X�g�p�}�E�X���X�i�[�𐶐�
	 * 
	 * @param tc �Ώۃe�L�X�g�R���|�[�l���g
	 * @return ���X�i�[
	 */
	public static MouseListener createCnPListener(final JTextComponent tc) {

		MouseListener listener = new MouseAdapter() {

			/** ���j���[ */
			protected JPopupMenu popup = new JPopupMenu();

			/** TAB�L�[�p���X�i */
			protected FocusListener flistener = new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
					popup.setVisible(false);
				}
			};

			/** �N���b�N�C�x���g */
			@Override
			public void mouseClicked(MouseEvent e) {

				if (!tc.isShowing() || !tc.isEnabled()) {
					return;
				}

				if (tc.isFocusOwner() && SwingUtilities.isRightMouseButton(e)) {
					popup.removeAll();

					boolean isEditable = tc.isEditable();

					if (Util.isNullOrEmpty(tc.getSelectedText())) {
						makeAnP(isEditable);

					} else {
						makeCnP(isEditable);
					}

					// TAB�L�[�Ή�
					popup.addPopupMenuListener(new PopupMenuListener() {

						@SuppressWarnings("hiding")
						public void popupMenuCanceled(PopupMenuEvent e) {
							// �Ȃ�
						}

						@SuppressWarnings("hiding")
						public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
							tc.removeFocusListener(flistener);
						}

						@SuppressWarnings("hiding")
						public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
							tc.addFocusListener(flistener);
						}
					});

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			/**
			 * �J�b�g�A�R�s�[�A�y�[�X�g����
			 */
			protected void makeCnP(boolean isEditable) {

				// �J�b�g
				JMenuItem cut = new JMenuItem(popupWords[0]);
				cut.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.cut();
					}
				});

				// �R�s�[
				JMenuItem copy = new JMenuItem(popupWords[1]);
				copy.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.copy();
					}
				});

				// �y�[�X�g
				JMenuItem past = new JMenuItem(popupWords[2]);
				past.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.paste();
					}
				});

				popup.setFocusable(false);
				cut.setEnabled(isEditable);
				past.setEnabled(isEditable);
				popup.add(cut);
				popup.add(copy);
				popup.add(past);
			}

			/**
			 * �S�đI���A�y�[�X�g����
			 */
			protected void makeAnP(boolean isEditable) {

				// �S�đI��
				JMenuItem select = new JMenuItem(popupWords[3]);
				select.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.selectAll();
					}
				});

				// �y�[�X�g
				JMenuItem past = new JMenuItem(popupWords[2]);
				past.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.paste();
					}
				});

				popup.setFocusable(false);
				past.setEnabled(isEditable);
				popup.add(select);
				popup.add(past);
			}
		};

		return listener;
	}

	/**
	 * �e�L�X�g �E�N���b�N���j���[�̒P��
	 * 
	 * @param wordList �E�N���b�N���j���[�P�ꃊ�X�g
	 */
	public static void setLightPopupMenuWords(String[] wordList) {
		popupWords = wordList;
	}

	/**
	 *
	 */
	protected static class ExecLater implements Runnable {

		/***/
		protected KeyEvent evt;

		/***/
		protected Container cont;

		/**
		 * @param cont
		 * @param evt
		 */
		public ExecLater(Container cont, KeyEvent evt) {
			this.evt = evt;
			this.cont = cont;
		}

		public void run() {
			transferFocusByEnterKey(this.cont, this.evt);
		}
	}
}
