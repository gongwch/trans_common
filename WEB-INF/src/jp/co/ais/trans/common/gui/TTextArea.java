package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.Character.Subset;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JTextArea�ɁA�^�u���C���^�[�t�F�C�X��ǉ�����TextArea. <br>
 * ���͍ő包�� setMaxLength(), getMaxLength()��ǉ�.
 */
public class TTextArea extends JTextArea implements TInterfaceTabControl, TTableComponent {

	/** ���s���� */
	protected static final String ENTER_TEXT = System.getProperty("line.separator");

	/** �t�H�[�J�X�ԍ� */
	protected int tabControlNo = -1;

	/** �^�u�ړ��\���ǂ��� */
	protected boolean isTabEnabled = true;

	/** �S�p�E���p�̐ؑւ��s�����ǂ��� */
	protected boolean isChangeCharacterSubsets = true;

	/** IME���� */
	protected boolean imeFlag;

	/** ���͐���h�L�������g */
	protected PlainDocument document = createPlainDocument();

	/** ENTER�L�[�ŉ��s 0:���Ȃ��A1:ENTER�A2:ALT+ENTER */
	protected int enterType = 1;

	/** �ėpINDEX�L�[ */
	protected int index;

	/** TableCellEditor���p�� */
	protected boolean tableCellEditor = false;

	/** �X�v���b�h������������ */
	protected boolean allowAutoAdjustTableHeight = false;

	/** ���LRenderer */
	protected TTextArea renderer = null;

	/** true:IME������s��Ȃ� */
	public static final boolean isNoImeControl = ClientConfig.isFlagOn("trans.textfield.no.ime.control");

	/** true:�t�H�[�J�X���Ă鎞���邢�� */
	public static final boolean isHighLight = ClientConfig.isFlagOn("trans.textfield.focus.highlight");

	/** �w�i�J���[ */
	protected Color backColor = getBackground();

	/**
	 * �R���X�g���N�^.
	 */
	public TTextArea() {
		super();

		this.setLineWrap(true);
		this.setImeMode(true);

		this.setDocument(document);

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {

				handleKeyPressed(evt);
			}
		});

		// �t�H�[�J�X�p���X�i
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				focusGainedActionPerformed(e);
			}

			@Override
			@SuppressWarnings("unused")
			public void focusLost(FocusEvent e) {
				focusLostActionPerformed();
			}
		});

		this.initTextHelper();
	}

	/**
	 * ���͐���
	 * 
	 * @return PlainDocument
	 */
	protected PlainDocument createPlainDocument() {
		return new TStringPlainDocument(this);
	}

	/**
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	@SuppressWarnings("deprecation")
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);

		switch (evt.getKeyChar()) {
			case KeyEvent.VK_ENTER:

				switch (enterType) {
					case 0: // ���s�Ȃ�
						evt.consume(); // Enter�Ńt�H�[�J�X�ړ�
						break;

					case 1: // Enter
						if (!evt.isControlDown()) {
							return; // Ctrl+Enter�Ńt�H�[�J�X�ړ�(���s�̓f�t�H���g)
						}

						evt.consume();
						break;

					case 2: // Alt+Enter
						if (!evt.isAltDown()) {
							evt.consume();
							break;
						}

						evt.setModifiers(0);

						return;
				}

				break;

			case KeyEvent.VK_TAB:
				evt.consume(); // Tab�Ńt�H�[�J�X�ړ�
				break;

			default:
				break;
		}

		// Enter�L�[
		TGuiUtil.transferFocusByEnterKey(TTextArea.this, evt);
	}

	/**
	 * �e�L�X�g�⏕�@�\
	 */
	protected void initTextHelper() {
		// UNDO/REDO
		TGuiUtil.initUndoRedo(this);

		// �J�b�g�A���h�y�[�X�g
		addMouseListener(TGuiUtil.createCnPListener(this));
	}

	/**
	 * FocusGained�C�x���g�����m���AOldText��}��.
	 * 
	 * @param e �C�x���g
	 */
	@SuppressWarnings("unused")
	protected void focusGainedActionPerformed(FocusEvent e) {

		if (enterType != 1) {
			this.selectAll();
		}

		// IME���̓��[�h����
		if (isChangeCharacterSubsets && this.getInputContext() != null) {
			if (!isNoImeControl()) {
				// IME������s���ꍇ�̂�
				Subset[] subsets = isImeMode() ? TClientLoginInfo.getInstance().getCharacterSubsets() : null;
				this.getInputContext().setCharacterSubsets(subsets);
			}
		}

		// �t�H�[�J�X���Ă鎞�̃o�b�N�J���[�ݒ�
		focusGainedBackColor();
	}

	/**
	 * �ēx�A�C�x���g���N���AlostFocus���ɁA�ړ����enabled���ς��ꍇ�ɑΉ�����B
	 */
	protected void focusLostActionPerformed() {

		this.setCaretPosition(0);

		// Enter�L�[,Tab�L�[�̍ăC�x���g���K�v����΃C�x���g���N���B
		TGuiUtil.transferBeforeFocusByEnterKey(this);

		// IME���͐���͉���
		if (isChangeCharacterSubsets && getInputContext() != null) {
			getInputContext().endComposition();
			if (!isNoImeControl()) {
				// IME������s���ꍇ�̂�
				getInputContext().setCharacterSubsets(null);
			}
		}

		// ���X�g�t�H�[�J�X���̃o�b�N�J���[�ݒ�
		focusLostBackColor();
	}

	/**
	 * @return true:IME����Ȃ�
	 */
	public boolean isNoImeControl() {
		return isNoImeControl;
	}

	/**
	 * �t�H�[�J�X���Ă鎞�̃o�b�N�J���[�ݒ�
	 */
	public void focusGainedBackColor() {
		if (isHighLight) {
			// ���邢�΃��[�h
			backColor = getBackground();
			if (this.isEditable()) {
				setBackground(TTextBGColor.getHighLightFocusInColor());
			}
		}
	}

	/**
	 * ���X�g�t�H�[�J�X���̃o�b�N�J���[�ݒ�
	 */
	public void focusLostBackColor() {
		if (isHighLight) {
			// ���邢�΃��[�h����
			if (this.isEditable()) {
				setBackground(backColor);
			}
		}
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * �^�u�t�H�[�J�X��
	 * 
	 * @return �^�u�t�H�[�J�X��
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * �ő包��
	 * 
	 * @return �ő包��
	 */
	public int getMaxLength() {
		return ((TStringPlainDocument) document).getMaxLength();
	}

	/**
	 * �ő包��
	 * 
	 * @param maxLength �ő包��
	 */
	public void setMaxLength(int maxLength) {
		((TStringPlainDocument) document).setMaxLength(maxLength);
	}

	/**
	 * �ҏW�E�s����
	 */
	@Override
	public void setEditable(boolean edit) {
		super.setEditable(edit);

		this.setImeMode(this.imeFlag);
	}

	/**
	 * IME���[�h�t���O
	 * 
	 * @param flag true: IME���[�h(�S�p)
	 */
	public void setImeMode(boolean flag) {
		this.imeFlag = flag;

		if (document != null && document instanceof TStringPlainDocument) {
			((TStringPlainDocument) document).setImeMode(flag);
		}

		enableInputMethods(flag);
	}

	/**
	 * IME���[�h�t���O
	 * 
	 * @return true: IME���[�h(�S�p)
	 */
	public boolean isImeMode() {
		return this.imeFlag;
	}

	/**
	 * ENTER�L�[�ŉ��s���邩�ǂ���
	 * 
	 * @param isEnter true:���s����Afalse:���s���Ȃ�(�f�t�H���g)
	 */
	public void setEnterToChangingLine(boolean isEnter) {
		enterType = isEnter ? 1 : 0;
	}

	/**
	 * ALT+ENTER�L�[�ŉ��s���邩�ǂ���
	 * 
	 * @param isAltEnter true:���s����Afalse:���s���Ȃ�(�f�t�H���g)
	 */
	public void setAltEnterToChangingLine(boolean isAltEnter) {
		enterType = isAltEnter ? 2 : 0;
	}

	/**
	 * ���͒l�̎擾.<br>
	 * �l��MaxLength���傫���ꍇ�A�E������폜���Ē񋟂���.
	 */
	@Override
	public String getText() {

		String str = super.getText();

		if (str.getBytes().length > getMaxLength()) {
			str = StringUtil.leftBX(str, getMaxLength());
		}

		return str;
	}

	/**
	 * ���͂��ꂽ�l�����̂܂ܕԂ�.
	 * 
	 * @return ���͂��ꂽ�l
	 */
	public String getInputText() {
		return super.getText();
	}

	/**
	 * ���͒l���L�邩�ǂ���
	 * 
	 * @return true:���͒l�L��
	 */
	public boolean isEmpty() {
		return Util.isNullOrEmpty(getText());
	}

	/**
	 * �S�p�E���p�̃t���O��ێ����邩�ǂ���.<br>
	 * false�ɐݒ肷��ƁA�����t�H�[�J�X�C�����AIME:OFF�ɂȂ�.
	 * 
	 * @param bol true:�ێ����Ȃ��B false:�ێ�����B
	 */
	public void setIsChangeCharacterSubsets(boolean bol) {
		this.isChangeCharacterSubsets = bol;
	}

	/**
	 * �֑�����(���͂����Ȃ�����)�̐ݒ�
	 * 
	 * @param words �֑��������X�g
	 */
	public void setProhibitionWords(String... words) {
		((TStringPlainDocument) document).setProhibitionWords(words);
	}

	/**
	 * ���͉\�����𐳋K�\���Ŏw�肷��.
	 * 
	 * @param regex ���K�\������
	 */
	public void setRegex(String regex) {
		((TStringPlainDocument) document).setRegex(regex);
	}

	/**
	 * ���͒l���N���A����.
	 */
	public void clear() {
		this.setText("");
	}

	/**
	 * ���s�t���̃e�L�X�g�ǉ�
	 * 
	 * @param txt �e�L�X�g
	 */
	public void addText(String txt) {
		if (isEmpty()) {
			setText(txt);
			return;
		}

		setText(getText() + SystemUtil.LINE_SEP + txt);
	}

	/**
	 * ��؂���s�Ńe�L�X�g�ǉ�
	 * 
	 * @param txt �e�L�X�g
	 */
	public void setTextList(String... txt) {
		setTextList(Arrays.asList(txt));
	}

	/**
	 * ��؂���s�Ńe�L�X�g�ǉ�
	 * 
	 * @param list �e�L�X�g
	 */
	public void setTextList(List<String> list) {
		clear();

		for (String txt : list) {
			addText(txt);
		}
	}

	/**
	 * �C���f�b�N�X�ԍ�
	 * 
	 * @return �C���f�b�N�X�ԍ�
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {

		TTextArea area = renderer != null ? renderer : new TTextArea();
		area.setAllowAutoAdjustTableHeight(this.isAllowAutoAdjustTableHeight()); // ������������
		return new TTextAreaRenderer(area, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		tableCellEditor = true;
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return getIndex();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		this.index = rowIndex;
	}

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}

	/**
	 * �X�v���b�h�������������̎擾
	 * 
	 * @return allowAutoAdjustTableHeight �X�v���b�h������������
	 */
	public boolean isAllowAutoAdjustTableHeight() {
		return allowAutoAdjustTableHeight;
	}

	/**
	 * �X�v���b�h�������������̐ݒ�
	 * 
	 * @param allowAutoAdjustTableHeight �X�v���b�h������������
	 */
	public void setAllowAutoAdjustTableHeight(boolean allowAutoAdjustTableHeight) {
		this.allowAutoAdjustTableHeight = allowAutoAdjustTableHeight;
	}

	/**
	 * ���LRenderer�̎擾
	 * 
	 * @return renderer ���LRenderer
	 */
	public TTextArea getRenderer() {
		return renderer;
	}

	/**
	 * ���LRenderer�̐ݒ�
	 * 
	 * @param renderer ���LRenderer
	 */
	public void setRenderer(TTextArea renderer) {
		this.renderer = renderer;
	}

}
