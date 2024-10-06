package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * �����t�B�[���h
 * 
 * @author Yanwei
 */
public class TTimeField extends TTextField {

	/** Constant for the ":" key. */
	protected static final String COLON = ":";

	/** Constant for the "_" key. */
	protected static final String UNDERSCORE = "_";

	/** ���ԏ����t�H�[�}�b�gHM */
	protected static String INIT_CALENDAR_HM = "__:__";

	/** �����e�L�X�g */
	protected String initText = INIT_CALENDAR_HM;

	/** �u�����N���� */
	protected boolean isAllowableBlank = true;

	/** ���ԃt�H�[�}�b�e�B���O������ */
	protected static final String DATE_HM = "HH" + COLON + "mm";

	/** �N���� */
	protected static final String BASE_YMD = "2000/01/01 ";

	/**
	 * �R���X�g���N�^.<br>
	 */
	public TTimeField() {
		super();
		TUIManager.setLogicalFont(this);
		initComponents();
		initType();
	}

	/**
	 * �V�X�e��������
	 */
	protected void initComponents() {
		setLayout(new GridBagLayout());

		Dimension size = new Dimension(50, 20);
		size = TGuiUtil.correctSize(TGuiUtil.TYPE_CALENDER, size);

		super.setPreferredSize(size);
		super.setMaximumSize(size);
		super.setMinimumSize(size);

		this.setEditable(true);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setImeMode(false);

		// calendar key listener
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				if (!TTimeField.this.isFocusOwner() || !TTimeField.this.isEditable()) {
					return;
				}

				// input number allowable
				if (!Character.isDigit(e.getKeyChar()) && !(UNDERSCORE.equals(e.getKeyChar()))) {
					e.consume();
				}

				// �C���e���W�F���g����
				dateFieldActionPerformed(e);
			}

			@Override
			@SuppressWarnings("deprecation")
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == 88) {
					// Ctrl+X
					TTimeField.this.selectAll();
					TTimeField.this.cut();
					TTimeField.this.clearField();
					e.consume();

				} else if (e.getKeyCode() == 86) {
					// Ctrl+V
					TTimeField.this.clear();
					TTimeField.this.paste();

					if (!isAppropriateValue(getPlainText())) {
						// �ҏW�O�̗L���Ȓl���Đݒ�
						setValue(getOldText());
					}

					e.consume();

				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					// ���L�[��Up��Down������ Calendar�̃t���b�p�[����������
					if (!TTimeField.this.isFocusOwner() || !TTimeField.this.isEditable()) {
						return;
					}

					boolean isSuccess = (e.getKeyCode() == KeyEvent.VK_UP) ? upAction() : downAction();

					if (!isSuccess) {
						e.consume();
					}

				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && Util.isNullOrEmpty(getPlainText())) {
					TTimeField.this.clearField();
				}
			}
		});

		// mouse wheel listener
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!TTimeField.this.isFocusOwner() || !TTimeField.this.isEditable()) {
					return;
				}

				boolean isDown = true;
				int rotation = e.getWheelRotation();

				// Down�����AUp����
				if (rotation < 0) {
					isDown = false;
					rotation *= -1;
				}

				for (int i = 0; i < rotation; i++) {
					boolean isSuccess = !isDown ? upAction() : downAction();

					if (!isSuccess) {
						e.consume();
						break;
					}
				}
			}
		});

		// mouse listener
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					if (!TTimeField.this.isEnabled() || !TTimeField.this.isEditable()) {
						return;
					}

				}
			}
		});

		// calendar focus listener
		this.setInputVerifier(new TInputVerifier() {

			@Override
			@SuppressWarnings("deprecation")
			public boolean verifyCommit(JComponent comp) {

				if (!TTimeField.this.isEnabled() || !TTimeField.this.isEditable()) {
					return true;
				}

				if (!isAppropriateValue(getPlainText())) {
					if (isAllowableBlank) {
						clearField();
					} else {
						// �ҏW�O�̗L���Ȓl���Đݒ�
						if (Util.isNullOrEmpty(oldText)) {
							setValue(getNowTime());

						} else {
							setValue(oldText);
						}
					}
				}

				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (Util.isNullOrEmpty(getText())) {
					TTimeField.this.setCaretPosition(0);
					return;
				}

				focusAdjustment();

				selectAll();
			}
		});

		initType();
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TTextField#initTextHelper()
	 */
	@Override
	protected void initTextHelper() {
		// �Ȃ�
	}

	/**
	 * �t�H�[�J�X����
	 */
	protected void focusAdjustment() {
		this.setCaretPosition(5);
	}

	/**
	 * ���t�^�C�v�̐ݒ�
	 */
	protected void initType() {

		setPlainText(getNowTime());
		this.pushOldText();

	}

	/**
	 * �t�B�[���h�𖢓��͏�Ԃɂ���
	 */
	protected void clearField() {
		setPlainText(initText);
	}

	/**
	 * CTRL+V ���쎞�ɁA�\��t��������͗v���ɍ������ǂ����𔻒f����
	 * 
	 * @param inputValue �Ώە���
	 * @return true:�\��t���\�Afalse:�\��t���s��(����)
	 */
	protected boolean isAppropriateValue(String inputValue) {
		try {

			if (inputValue.indexOf(UNDERSCORE) != -1) {
				return false;
			}

			Date inputDate = null;

			if (inputValue.length() != 5) {
				return false;
			}
			if (inputValue.indexOf(COLON) != 2) {
				return false;
			}

			inputDate = DateUtil.toYMDHMDate(BASE_YMD + inputValue);

			if (inputDate == null) {
				return false;
			}

			return true;

		} catch (ParseException e) {
			// ���͓��t�������s���ȏꍇ�́A�\��t���s��
			return false;
		}
	}

	/**
	 * �C���e���W�F���g����
	 * 
	 * @param e KeyEvent
	 */
	protected void dateFieldActionPerformed(KeyEvent e) {

		// JTextField���J�����g�J�[�\���̈ʒu�F�t�H�[�J�X�̈ʒu�擾
		int position = this.getCaretPosition();

		// ���̓e�L�X�g
		String subInputText = "0";
		if (position != 0) {
			// �t�H�[�J�X�������Ă�ӏ��܂ł̕������擾
			subInputText = getPlainText().substring(0, position);
		}

		if (!isNum(subInputText) && isInputNumber(e)) {
			this.setCaretPosition(0);
		}

		// ���̓e�L�X�g
		String inputText = getPlainText();

		// �J�����g�J�[�\���̈ʒu �Ō��1�ʂ̏���
		if (isInputNumber(e) && position == initText.length()) {
			if (this.getSelectedText() == null) {
				e.consume();
				return;
			}
		}

		// DELETE�L�[������
		if (!isInputNumber(e) && e.getKeyChar() == KeyEvent.VK_DELETE) {
			setPlainText(inputText.substring(0, position) + initText.substring(position, initText.length()));
			this.setCaretPosition(position);
		}

		// �X�y�[�X(SPACE)�L�[�������āA�|�b�v�A�b�v�\���͖���
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			e.consume();
			return;
		}

		// BakcSpace key press
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			setPlainText(inputText.substring(0, position) + initText.substring(position, initText.length()));
			this.setCaretPosition(position);
			return;
		}

		// Enter key press
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			return;
		}

		// Tab key press
		if (e.getKeyChar() == KeyEvent.VK_TAB) {
			return;
		}

		// �͈͑I����
		if (getSelectedText() != null) {
			position = getSelectionStart();
		}

		// �O��_���܂܂��Ȃ�A���̂�������
		if (position != 0 && inputText.substring(0, position).indexOf(UNDERSCORE) != -1) {
			e.consume();
			return;
		}

		// ���͓��t���`�F�b�N����
		if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
			e.consume();
		}

		// H���`�F�b�N����
		if (position == 0) {
			if (e.getKeyChar() > KeyEvent.VK_2) {
				e.consume();
				return;
			}
		}

		if (position == 1) {
			if (inputText.substring(0, 1).equals("2")) {
				if (e.getKeyChar() > KeyEvent.VK_3) {
					e.consume();
					return;
				}
			} else {
				if (e.getKeyChar() > KeyEvent.VK_9) {
					e.consume();
					return;
				}
			}
		}

		// :
		if (position == 2) {
			this.select(position + 1, position + 1);
			e.consume();
			return;
		}

		// M���`�F�b�N����
		if (position == 3) {
			if (e.getKeyChar() > KeyEvent.VK_5) {
				e.consume();
				return;
			}
		}
		if (position == 4) {
			if (e.getKeyChar() > KeyEvent.VK_9) {
				e.consume();
				return;
			}
		}

		if (position == 5) {
			e.consume();
			return;
		}

		// input number allowable
		if (!Character.isDigit(e.getKeyChar())) {
			e.consume();
			return;
		}

		setPlainText(inputText.substring(0, position) + e.getKeyChar()
			+ initText.substring(position + 1, initText.length()));
		e.consume();

		if (position == 1) {
			this.select(position + 2, position + 2);
		} else {
			this.select(position + 1, position + 1);
		}

		return;
	}

	/**
	 * �w�肳�ꂽ�����񂪐��l�ɕϊ����ǂ����̔��f���s��
	 * 
	 * @param subInputText
	 * @return true:�ϊ��\
	 */
	protected boolean isNum(String subInputText) {
		return Character.isDigit(subInputText.charAt(0));
	}

	/**
	 * ���͂����̂������ł��邩�ۂ��𔻒f����
	 * 
	 * @param e
	 * @return true:�ϊ��\
	 */
	protected boolean isInputNumber(KeyEvent e) {
		return Character.isDigit(e.getKeyChar());
	}

	/**
	 * DOWN�C�x���g
	 * 
	 * @return true:��������
	 */
	protected boolean downAction() {

		String inputText = setupSpinText();

		Date dt = getDateValue();
		if (dt == null) {
			return false;
		}

		int hour = DateUtil.getHour(dt);

		// JTextField���J�����g�J�[�\���̈ʒu�F
		int position = this.getCaretPosition();

		int intHour = 0;
		int intMinute = 0;

		// DOWN key press
		// ��
		if (position >= 0 && position <= 2) {
			// ����I��
			this.select(0, 2);

			intHour = Integer.parseInt(this.getSelectedText()) - 1;

			if (intHour == -1) {
				setPlainText("23" + inputText.substring(2, inputText.length()));
				this.select(0, 2);
				return false;
			}

			if (intHour < 10) {
				setPlainText("0" + intHour + inputText.substring(2, inputText.length()));
			} else {
				setPlainText(intHour + inputText.substring(2, inputText.length()));
			}

			this.select(0, 2);
		}

		// ��
		if (position > 2 && position <= 5) {
			// ����I��
			this.select(3, 5);

			intMinute = Integer.parseInt(this.getSelectedText()) - 1;

			if (intMinute == -1) {
				if (hour == 0) {
					setPlainText(getHM("23", "59"));
				} else {
					setPlainText(getHM((hour - 1), "59"));
				}

				this.select(3, 5);

				return false;
			}

			setPlainText(getHM(hour, intMinute));

			this.select(3, 5);
		}

		return true;
	}

	/**
	 * UP�C�x���g
	 * 
	 * @return true:��������
	 */
	protected boolean upAction() {

		String inputText = setupSpinText();

		Date dt = getDateValue();
		if (dt == null) {
			return false;
		}

		int hour = DateUtil.getHour(dt);

		// JTextField���J�����g�J�[�\���̈ʒu�F
		int position = this.getCaretPosition();

		int intHour = 0;
		int intMinute = 0;

		// ��
		if (position >= 0 && position <= 2) {
			// ����I��
			this.select(0, 2);

			intHour = Integer.parseInt(this.getSelectedText()) + 1;

			// ���Ԃ̍ő�l��24
			if (intHour == 24) {
				setPlainText("00" + inputText.substring(2, inputText.length()));
				this.select(0, 2);
				return false;
			}

			if (intHour < 10) {
				setPlainText("0" + intHour + inputText.substring(2, inputText.length()));
			} else {
				setPlainText(intHour + inputText.substring(2, inputText.length()));
			}

			this.select(0, 2);
		}

		// ��
		if (position > 2 && position <= 5) {
			// ����I��
			this.select(3, 5);

			intMinute = Integer.parseInt(this.getSelectedText()) + 1;

			// ���Ԃ̍ő�l��24
			if (intMinute == 60) {

				if (hour == 23) {
					setPlainText(getHM("00", "00"));
				} else {
					setPlainText(getHM((hour + 1), "00"));
				}

				this.select(3, 5);

				return false;
			}

			setPlainText(getHM(hour, intMinute));

			this.select(3, 5);
		}

		return true;
	}

	/**
	 * �����b�̃e�L�X�g���擾����(�^�C�v��)
	 * 
	 * @param hour ��
	 * @param min ��
	 * @return �����b�e�L�X�g
	 */
	protected String getHM(Object hour, Object min) {

		String strHour = String.valueOf(hour);
		String strMin = String.valueOf(min);

		if (strHour.length() == 1) {
			strHour = "0" + strHour;
		}

		if (strMin.length() == 1) {
			strMin = "0" + strMin;
		}

		return strHour + COLON + strMin;
	}

	/**
	 * �X�s���p�̃e�L�X�g���擾����
	 * 
	 * @return �e�L�X�g
	 */
	protected String setupSpinText() {

		String inputText = getPlainText();

		// _ �܂܂�邩�ǂ����`�F�b�N
		int unscIndex = inputText.indexOf(UNDERSCORE);

		if (unscIndex != -1) {
			// �r���܂œ��͂���Ă���΁A����𗘗p
			int hour = 0;
			int minute = 0;

			// JTextField���J�����g�J�[�\���̈ʒu�F
			int position = this.getCaretPosition();

			if (unscIndex <= 2) {
				hour = 0;
				minute = 0;
				position = 2;

			} else {
				hour = Integer.parseInt(inputText.substring(0, 2));
				minute = 0;
				position = 5;
			}

			setHMValue(hour, minute);
			setCaretPosition(position);

			inputText = this.getText();
		}

		return inputText;
	}

	/**
	 * �L�[�{�[�h��������s����
	 * 
	 * @param key Key
	 */
	protected void keyPress(int key) {
		try {
			Robot robot = new Robot();
			robot.keyPress(key);
			robot.keyRelease(key);
		} catch (AWTException e1) {
			ClientErrorHandler.handledException(e1);
		}
	}

	/**
	 * �^�C�v�Ɉˑ��������ݎ��ԕ�������擾����i�t�H�[�}�b�e�B���O�j
	 * 
	 * @return ���ݎ���������
	 */
	protected String getNowTime() {
		return DateUtil.toHMString(new Date());
	}

	/**
	 * �u�����N���͋����
	 * 
	 * @param b true:����
	 */
	public void setAllowableBlank(boolean b) {

		this.isAllowableBlank = b;

		if (getDateValue() == null) {

			if (this.isAllowableBlank) {
				clearField();
			} else {
				setPlainText(getNowTime());
			}

			this.pushOldText();
		}
	}

	/**
	 * �u�����N���͋����
	 * 
	 * @return true:����
	 */
	public boolean isAllowableBlank() {

		return this.isAllowableBlank;
	}

	/**
	 * ���t�R���g���[�����̒l���擾����
	 * 
	 * @return String
	 */
	@Override
	public String getText() {
		String txt = getPlainText();
		if (Util.isNullOrEmpty(txt) || !isAppropriateValue(txt)) {
			return "";
		}

		return txt;
	}

	/**
	 * �e�L�X�g�t�B�[���h�̕��������̂܂ܕԂ�.
	 * 
	 * @return Plain�e�L�X�g
	 */
	public String getPlainText() {
		return super.getText();
	}

	/**
	 * �e�L�X�g�ݒ�
	 * 
	 * @param text ����(HH:MM)
	 */
	@Override
	public void setText(String text) {

		if (isAppropriateValue(text)) {
			setPlainText(text);

		} else if (isAllowableBlank) {
			clearField();
		}
	}

	/**
	 * �e�L�X�g�ɂ��̂܂܂̕����𔽉f����.
	 * 
	 * @param text �e�L�X�g
	 */
	public void setPlainText(String text) {
		super.setText(text);
	}

	/**
	 * �l�̐ݒ�
	 * 
	 * @param hour ��
	 * @param minute ��
	 */
	protected void setHMValue(int hour, int minute) {

		String strHour = "";
		String strMinute = "";

		// ��
		if (hour < 10) {
			strHour = "0" + String.valueOf(hour);
		} else {
			strHour = String.valueOf(hour);
		}

		// ��
		if (minute < 10) {
			strMinute = "0" + String.valueOf(minute);
		} else {
			strMinute = String.valueOf(minute);
		}

		setPlainText(strHour + COLON + strMinute);
	}

	/**
	 * �l�̐ݒ�
	 */
	public void setEmptyValue() {

		if (this.isAllowableBlank) {
			clearField();
			return;
		}
	}

	/**
	 * �e�L�X�g�t�B�[���h������t���擾����.
	 * 
	 * @return ���t
	 */
	protected Date getDateValue() {
		return getDateValue(getPlainText());
	}

	/**
	 * �w��e�L�X�g�t�B�[���h������t���擾����.
	 * 
	 * @param text ���t�e�L�X�g
	 * @return ���t
	 */
	protected Date getDateValue(String text) {
		try {

			return DateUtil.toYMDHMDate(BASE_YMD + text);

		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * �l��ݒ肷��B
	 * 
	 * @param hm �l(HH:mm)
	 */
	public void setValue(String hm) {
		try {
			// HH:mm �`���ȊO�̓G���[�Ƃ���B
			if (hm.length() != 5) {
				return;
			}

			Date date = null;
			date = DateUtil.toYMDHMDate(BASE_YMD + hm);

			this.setText(DateUtil.toHMString(date));

		} catch (ParseException e) {
			return;
		}
	}

	/**
	 * �l���擾����B�i�\�������j
	 */
	@Override
	public String getValue() {
		return this.getText();
	}

	@Override
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TTimefieldRenderer(this, tbl);
	}

	@Override
	public TableCellEditor getCellEditor(TTable tbl) {
		tableCellEditor = true;
		return new TTimefieldEditor(this, tbl);
	}

}
