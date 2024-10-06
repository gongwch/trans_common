package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.Character.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import com.klg.jclass.field.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * �W��������TextField
 */
public class TTextField extends JCTextField implements TInterfaceTabControl, TTableComponent {

	/** �L�[�C�x���g�R�[�h */
	public int beforeKeyCode;

	/** �^�u�� */
	protected int tabControlNo = -1;

	/** �^�u�ړ��E�t�� */
	protected boolean isTabEnabled = true;

	/** IME���� */
	protected boolean imeFlag = true;

	/** �S�p�E���p�̐ؑւ��s�����ǂ��� */
	protected boolean isChangeCharacterSubsets = true;

	/** �����ύX���Ȃ��Ă����������������OldValue���N���A���邩�ǂ��� */
	protected boolean clearOldValueByReplesedText = false;

	/** �ύX�O�e�L�X�g(focusGained�ŕύX) */
	protected String oldText = "";

	/** ������InputVerifier�������ׂ̃N���X */
	protected TInputVerifierMulti verifiers = new TInputVerifierMulti();

	/** �C���N�������g�T�[�`�@�\ */
	protected AutoCompleteUtil autoComplete = null;

	/** ���͐���h�L�������g */
	protected PlainDocument document = createPlainDocument();

	/** �e�L�X�g���e���R�s�[����TTextField */
	protected List<TTextField> valueCopyFields = new LinkedList<TTextField>();

	/** �ėpINDEX�L�[ */
	protected int index;

	/** TableCellEditor���p�� */
	protected boolean tableCellEditor = false;

	/** true:IME������s��Ȃ� */
	public static final boolean isNoImeControl = ClientConfig.isFlagOn("trans.textfield.no.ime.control");

	/** true:�t�H�[�J�X���Ă鎞���邢�� */
	public static final boolean isHighLight = ClientConfig.isFlagOn("trans.textfield.focus.highlight");

	/** �w�i�J���[ */
	protected Color backColor = getBackground();

	/** �\��t���@�\�g�����Btrue:�g�� */
	protected boolean useTablePaste = false;

	/** �^�C�v�z��(�\��t���񁨉E�S��) */
	protected int[] cellTypes = null;

	/**
	 * �R���X�g���N�^.
	 */
	public TTextField() {
		super();

		this.setDocument(document);
		this.setEditable(true);
		this.setValue("");

		// JRE1.6�Ή�(�ϊ��\�����؂����)
		Insets def = getMargin();
		setMargin(new Insets(1, def.left, 1, def.right));

		super.setInputVerifier(verifiers);

		// ���̂���͂��A���X�g�t�H�[�J�X�BlistCopyNameField�Ɏw���TTextField�֖��̂̓��e���R�s�[����
		this.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				copyText();
				return true;
			}
		});

		// �t�H�[�J�X�p���X�i
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				beforeKeyCode = -1;

				focusGainedActionPerformed(e);
			}

			@Override
			public void focusLost(FocusEvent e) {
				focusLostActionPerformed();
			}
		});

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (beforeKeyCode == KeyEvent.VK_ENTER) {
						evt.consume();
					}
				}

				handleKeyPressed(evt);

				beforeKeyCode = evt.getKeyCode();
			}

			@Override
			public void keyReleased(KeyEvent e) {

				beforeKeyCode = -1;
			}
		});

		super.setDropTarget(null);

		// TAB �L�[�̎����ړ����폜

		// ������
		Set<AWTKeyStroke> keySet;
		keySet = super.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);

		Set<AWTKeyStroke> newSet = new HashSet<AWTKeyStroke>();
		for (Iterator<AWTKeyStroke> i = keySet.iterator(); i.hasNext();) {
			AWTKeyStroke strk = i.next();
			if (strk.getKeyCode() != KeyEvent.VK_TAB) {
				newSet.add(strk);
			}
		}
		super.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newSet);

		// �t����
		keySet = super.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);

		newSet = new HashSet<AWTKeyStroke>();
		for (Iterator<AWTKeyStroke> i = keySet.iterator(); i.hasNext();) {
			AWTKeyStroke strk = i.next();
			if (strk.getKeyCode() != KeyEvent.VK_TAB) {
				newSet.add(strk);
			}
		}

		super.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newSet);

		initTextHelper();
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
	 * ������̒��̃}���`�o�C�g����
	 * 
	 * @param str
	 * @return �}���`�o�C�g����
	 */
	protected String exceptMultiByte(String str) {

		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		// ���K�\����ASCII�ȊO�����O
		Pattern p = Pattern.compile("[^\\p{ASCII}]");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * �L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		// �t�@���N�V�����L�[
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);

		if (!this.isFocusOwner()) {
			return;
		}

		// Enter�L�[
		TGuiUtil.transferFocusByEnterKey(this, evt);

		repaint();

		// // F2 �N���A�Ή�
		// int fkey = TGuiUtil.checkFunctionKeyEvent(evt);
		// if(fkey == 2){
		// if(this.isEnabled() && this.isEditable()){
		// this.setText("");
		// }
		// }
	}

	/**
	 * ���͐���
	 * 
	 * @return PlainDocument
	 */
	protected PlainDocument createPlainDocument() {
		return new TStringPlainDocument(this) {

			/**
			 * @see AbstractDocument#remove(int, int)
			 */
			@Override
			public void remove(int offs, int len) throws BadLocationException {
				if (clearOldValueByReplesedText) {
					TTextField.this.clearOldText(); // �Â����̓f�[�^���폜
				}

				super.remove(offs, len);
			}
		};
	}

	/**
	 * FocusGained�C�x���g�����m���AOldText��}��.
	 * 
	 * @param e �C�x���g
	 */
	protected void focusGainedActionPerformed(FocusEvent e) {

		// ����Window����t�H�[�J�X�������Ƃ���null�ɂȂ�B
		Component comp = e.getOppositeComponent();

		if (comp != null) {
			// �R���|�[�l���g�̐e��Frame,Dialog�������ł��ꂠ��oldText���㏑������B
			if (comp instanceof Container) {

				Container a = TGuiUtil.getParentFrameOrDialog((Container) comp);
				Container b = TGuiUtil.getParentFrameOrDialog(this);

				if (a.equals(b)) {

					this.pushOldText();
				}
			}
		}

		this.selectAll();

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
		this.commitEdit();
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
	 * �R�s�[�w��TTextField�֓��͒l���R�s�[����
	 */
	protected void copyText() {

		// �u�����N�̓R�s�[���Ȃ�
		if (Util.isNullOrEmpty(this.getText())) {
			return;
		}

		// �w���TTextField�֓��e���R�s�[
		for (TTextField txtField : valueCopyFields) {

			// �Ώۂ̃e�L�X�g���u�����N�ꍇ�́A�R�s�[���Ȃ�
			if (Util.isNullOrEmpty(txtField.getText())) {
				txtField.setText(this.getText());
			}
		}
	}

	/**
	 * @return �ύX�O�e�L�X�g
	 * @deprecated �g�p���Ȃ�(isValueChanged()�𗘗p)
	 */
	public String getOldText() {
		return this.oldText;
	}

	/**
	 * �ύX�O�e�L�X�g���N���A
	 */
	public void clearOldText() {
		this.oldText = null;
	}

	/**
	 * �ύX�O�e�L�X�g�Ɍ��e�L�X�g��ݒ�
	 */
	public void pushOldText() {
		this.oldText = this.getText();
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
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#isTabEnabled()
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
	 * space���͋���
	 * 
	 * @return true:����
	 */
	public boolean isAllowedSpace() {
		return ((TStringPlainDocument) document).isAllowedSpace();
	}

	/**
	 * space���͋���
	 * 
	 * @param b true:����
	 */
	public void setAllowedSpace(boolean b) {
		((TStringPlainDocument) document).setAllowedSpace(b);
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
	 * �����ύX���Ȃ��Ă����������������OldValue���N���A���邩�ǂ���
	 * 
	 * @return true:�N���A����
	 */
	public boolean isClearOldValueByReplesedText() {
		return clearOldValueByReplesedText;
	}

	/**
	 * �����ύX���Ȃ��Ă����������������OldValue���N���A���邩�ǂ���.<br>
	 * (�f�t�H���gfalse)
	 * 
	 * @param clearOldValueByReplesedText true:�N���A����
	 */
	public void setClearOldValueByReplesedText(boolean clearOldValueByReplesedText) {
		this.clearOldValueByReplesedText = clearOldValueByReplesedText;
	}

	/**
	 * editable, enabled�v���p�e�B�����ݒ�
	 * 
	 * @param flag setEditable(), setEnabled()�ݒ�l
	 */
	public void setEditableEnabled(boolean flag) {

		if (flag) {
			super.setEnabled(true);
			this.setEditable(true);
		} else {
			this.setEditable(false);
			super.setEnabled(false);
		}
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
	 * �l�̐ݒ�. <br>
	 * enabled=false,editable=false�ł��A������ݒ�ł���B
	 * 
	 * @param t �ݒ蕶����
	 */
	@Override
	public void setText(String t) {
		super.setText(t);

		super.moveCaretPosition(0);
	}

	/**
	 * �l�̐ݒ�. <br>
	 * �t�B�[���h�ɒl��ݒ肵�A�e�L�X�g�ɕ�����ݒ肷��.
	 * 
	 * @param obj �ݒ�l
	 */
	@Override
	public void setValue(Object obj) {
		this.setText(Util.avoidNull(obj));
	}

	/**
	 * ���͒l���N���A����.
	 */
	public void clear() {
		this.setValue("");
	}

	/**
	 * �C�x���g�̃}�X�N
	 * 
	 * @param mask
	 */
	public void setDisableEvents(long mask) {
		this.disableEvents(mask);
	}

	/**
	 * �C�x���g����
	 * 
	 * @param mask
	 */
	public void setEnableEvents(long mask) {
		this.enableEvents(mask);
	}

	/**
	 * �L�[�C�x���g���� <br>
	 * tab�L�[�̎����ړ����폜���Ă��邽�߁Atab�L�[���͎��� ����method���Ă΂��B
	 * 
	 * @param evt �L�[�C�x���g
	 */
	@Override
	public void processKeyEvent(KeyEvent evt) {

		if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			if (evt.getID() == KeyEvent.KEY_PRESSED) {
				this.handleKeyPressed(evt);
				evt.consume();
				return;
			}
		}
		super.processKeyEvent(evt);
	}

	/**
	 * �ύX���ꂽ���ǂ����𔻒肷��
	 * 
	 * @return true:�ύX����Ă���
	 */
	public boolean isValueChanged() {

		// ���݂̓��͒l
		String crnt = getText();

		if (this.oldText == null || "".equals(this.oldText)) {
			return true;
		}

		return !(Util.avoidNullNT(crnt).equals(Util.avoidNullNT(this.oldText)));
	}

	/**
	 * �ύX���ꂽ���ǂ����𔻒肷��. null(�u�����N)���Ώ�
	 * 
	 * @return true:�ύX����Ă���
	 */
	public boolean isValueChanged2() {

		// ���݂̓��͒l
		String crnt = getText();

		boolean flag1 = this.oldText == null;
		boolean flag2 = crnt == null;

		if ((flag1 && !flag2) || (!flag1 && flag2)) {
			return true;
		}

		return !(Util.avoidNullNT(crnt).equals(Util.avoidNullNT(this.oldText)));
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
	 * Value�擾(getText()�Ɠ���)
	 */
	@Override
	public Object getValue() {
		return this.getText();
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
	 * @see javax.swing.JComponent#setInputVerifier(javax.swing.InputVerifier)
	 */
	@Override
	public void setInputVerifier(InputVerifier inputVerifier) {
		verifiers.add(inputVerifier);
	}

	/**
	 * InputVerify��������
	 * 
	 * @param inputVerifier InputVerify
	 */
	public void removeInputVerifier(InputVerifier inputVerifier) {
		verifiers.remove(inputVerifier);
	}

	/**
	 * �S�Ă�InputVerify��������
	 */
	public void removeAllInputVerifier() {
		if (verifiers != null && verifiers.verifiers != null) {
			verifiers.verifiers.clear();
		}
	}

	/**
	 * �C���N�������g�T�[�`�@�\�̎擾
	 * 
	 * @return autoComplete �C���N�������g�T�[�`�@�\
	 */
	public AutoCompleteUtil getAutoComplete() {
		return autoComplete;
	}

	/**
	 * �C���N�������g�T�[�`�@�\�̐ݒ�
	 * 
	 * @param autoComplete �C���N�������g�T�[�`�@�\
	 */
	public void setAutoComplete(AutoCompleteUtil autoComplete) {
		this.autoComplete = autoComplete;
	}

	/**
	 * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
	 */
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * @see java.awt.Component#setSize(Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * @see java.awt.Component#setSize(int, int)
	 */
	@Override
	public void setSize(int width, int height) {
		Dimension d = TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, new Dimension(width, height));
		super.setSize(d.width, d.height);
	}

	/**
	 * @see javax.swing.JComponent#setMaximumSize(java.awt.Dimension)
	 */
	@Override
	public void setMaximumSize(Dimension d) {
		super.setMaximumSize(TGuiUtil.correctSize(TGuiUtil.TYPE_TEXTFIELD, d));
	}

	/**
	 * �{�^���A��.Enter�L�[�������ɓo�^�{�^������������.<br>
	 * �iEnter�ł̃t�H�[�J�X�ړ��������ɂȂ�̂Œ��Ӂj
	 * 
	 * @param btn �Ώۂ̃{�^��
	 */
	public void addEnterToButton(final TButton btn) {

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!isFocusOwner() || !isEditable()) {
						return;
					}

					btn.doClick();
					btn.requestFocusInWindow();
				}
			}
		});
	}

	/**
	 * ���X�g�t�H�[�J�X���A�e�L�X�g�̓��͒l���R�s�[����e�L�X�g�t�B�[���h��o�^.
	 * 
	 * @param txtField �Ώ�TTextField
	 */
	public void addValueCopyTextField(TTextField txtField) {
		valueCopyFields.add(txtField);
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

		TTextField renderer = new TTextField();
		return new TTextRenderer(renderer, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		tableCellEditor = true;
		return new TTextEditor(this, tbl);
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
	 * �\��t���@�\�g�����Btrue:�g���̎擾
	 * 
	 * @return useTablePaste �\��t���@�\�g�����Btrue:�g��
	 */
	public boolean isUseTablePaste() {
		return useTablePaste;
	}

	/**
	 * �\��t���@�\�g�����Btrue:�g���̐ݒ�
	 * 
	 * @param useTablePaste �\��t���@�\�g�����Btrue:�g��
	 */
	public void setUseTablePaste(boolean useTablePaste) {
		this.useTablePaste = useTablePaste;
	}

	/**
	 * �^�C�v�z��(�\��t���񁨉E�S��)�̎擾
	 * 
	 * @return cellTypes �^�C�v�z��(�\��t���񁨉E�S��)
	 */
	public int[] getCellTypes() {
		return cellTypes;
	}

	/**
	 * �^�C�v�z��(�\��t���񁨉E�S��)�̐ݒ�
	 * 
	 * @param cellTypes �^�C�v�z��(�\��t���񁨉E�S��)
	 */
	public void setCellTypes(int[] cellTypes) {
		this.cellTypes = cellTypes;
	}

}
