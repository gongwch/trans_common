package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * ���t�̑I���R���g���[��
 * 
 * @version 1.0
 * @author JV
 */
// TODO Calendar�𗘗p���Č����I��(����UP/DOWN)
public class TCalendar extends TTextField {

	/** �����⊮�@�\�𖳌� */
	public static boolean isNotUseAutoComplete = ClientConfig.isFlagOn("trans.calendar.not.use.auto.complete");

	/** �N */
	public static final int TYPE_Y = 1;

	/** �N�� */
	public static final int TYPE_YM = 2;

	/** �N���� */
	public static final int TYPE_YMD = 3;

	/** �N���������b */
	public static final int TYPE_YMDHM = 5;

	/** �N���������b */
	public static final int TYPE_YMDHMS = 6;

	/** Constant for the forward slash key "/" */
	protected static final String SLASH = "/";

	/** Constant for the ":" key. */
	protected static final String COLON = ":";

	/** Constant for the "_" key. */
	protected static final String UNDERSCORE = "_";

	/** ���ԃt�H�[�}�b�e�B���O������ */
	protected static final String DATE_HMS = "HH" + COLON + "mm" + COLON + "ss";

	/** ���t�����t�H�[�}�b�gY */
	protected static String INIT_CALENDAR_Y = "____";

	/** ���t�����t�H�[�}�b�gYM */
	protected static String INIT_CALENDAR_YM = "____" + SLASH + "__";

	/** ���t�����t�H�[�}�b�gYMD */
	protected static String INIT_CALENDAR_YMD = "____" + SLASH + "__" + SLASH + "__";

	/** ���ԏ����t�H�[�}�b�gYMDHM */
	protected static String INIT_CALENDAR_YMDHM = "____" + SLASH + "__" + SLASH + "__ __:__";

	/** ���ԏ����t�H�[�}�b�gYMDHMS */
	protected static String INIT_CALENDAR_YMDHMS = "____" + SLASH + "__" + SLASH + "__ __:__:__";

	/** �f�t�H���g�ŏ����� */
	protected static final Date MIN_DATE = DateUtil.getDate(1900, 1, 1);

	/** �f�t�H���g�ő厞�� */
	protected static final Date MAX_DATE = DateUtil.getDate(2099, 12, 31, 23, 59, 59);

	/** �j���̒P��ID */
	public static String[] dayOfWeekWords = { "��", "��", "��", "��", "��", "��", "�y" };

	/** ���t�^�C�v */
	protected int calendarType = TYPE_YMD;

	/** �ŏ����� */
	protected Date minDate = MIN_DATE;

	/** �ő厞�� */
	protected Date maxDate = MAX_DATE;

	/** popup calendar dialog */
	protected JDialog dateFrame;

	/** date panel */
	protected DatePanel datePanel;

	/** ���t�R���g���[����PopUp Dialog�̕� */
	protected int popupWidth = 200;

	/** ���t�R���g���[����PopUp Dialog�̍��� */
	protected int popupHeight = 210;

	/** �u�����N���� */
	protected boolean isAllowableBlank = true;

	/** �����e�L�X�g */
	protected String initText = INIT_CALENDAR_YMD;

	/** �N�̎��̓J�����_�[��\�����邩�ǂ��� */
	protected boolean isShowYCalendar = false;

	/**
	 * �R���X�g���N�^.<br>
	 * �J�����_�[�^�C�v��TYPE_YMD
	 */
	public TCalendar() {
		this(TYPE_YMD);
	}

	/**
	 * �R���X�g���N�^.<br>
	 * �w�肵���^�C�v�ɏ]���ăJ�����_�[���\�z����
	 * <ol>
	 * <li>TYPE_Y : �N
	 * <li>TYPE_YM : �N��
	 * <li>TYPE_YMD : �N����
	 * <li>TYPE_YMDHM : �N��������
	 * <li>TYPE_YMDHMS : �N���������b
	 * </ol>
	 * 
	 * @param type �J�����_�[�^�C�v
	 */
	public TCalendar(int type) {
		super();
		this.calendarType = type;

		TUIManager.setLogicalFont(this);

		initComponents();
	}

	/**
	 * �J�����_�[�^�C�v�ݒ�
	 * 
	 * @param type �J�����_�[�^�C�v
	 */
	public void setCalendarType(int type) {
		this.calendarType = type;
		initType();
	}

	/**
	 * �J�����_�[�^�C�v
	 * 
	 * @return �J�����_�[�^�C�v
	 */
	public int getCalendarType() {
		return this.calendarType;
	}

	/**
	 * �V�X�e��������
	 */
	protected void initComponents() {
		setLayout(new GridBagLayout());

		this.setEditable(true);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setImeMode(false);

		// calendar key listener
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				if (!TCalendar.this.isFocusOwner() || !TCalendar.this.isEditable()) {
					return;
				}

				// input number allowable
				if (!Character.isDigit(e.getKeyChar()) && !(UNDERSCORE.equals(e.getKeyChar()))) {
					e.consume();
				}

				// �C���e���W�F���g����
				dateFieldActionPerformed(e);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == 88) {
					// Ctrl+X
					TCalendar.this.selectAll();
					TCalendar.this.cut();
					TCalendar.this.clearField();
					e.consume();

				} else if (e.getKeyCode() == 86) {
					// Ctrl+V
					TCalendar.this.clear();
					TCalendar.this.paste();

					if (!isAppropriateValue(getPlainText())) {
						// �ҏW�O�̗L���Ȓl���Đݒ�
						setValue(getValue(getOldText()));
					}

					e.consume();

				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					// ���L�[��Up��Down������ Calendar�̃t���b�p�[����������
					if (tableCellEditor || !TCalendar.this.isFocusOwner() || !TCalendar.this.isEditable()) {
						return;
					}

					boolean isSuccess = (e.getKeyCode() == KeyEvent.VK_UP) ? upAction() : downAction();

					if (!isSuccess) {
						e.consume();
					}

				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && Util.isNullOrEmpty(getPlainText())) {
					TCalendar.this.clearField();
				}
			}
		});

		// mouse wheel listener
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!TCalendar.this.isFocusOwner() || !TCalendar.this.isEditable()) {
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

					if (!TCalendar.this.isEnabled() || !TCalendar.this.isEditable()) {
						return;
					}

					if (datePanel == null || !datePanel.isVisible()) {
						TCalendar.this.requestFocusInWindow();

						if (!isAppropriateValue(getPlainText())) {
							setPlainText(getNowDate());
						}
						showCalendar();
					} else {
						datePanel.setVisible(false);
					}
				}
			}
		});

		// calendar focus listener
		this.setInputVerifier(new TInputVerifier() {

			@Override
			@SuppressWarnings("deprecation")
			public boolean verifyCommit(JComponent comp) {

				if (!TCalendar.this.isEnabled() || !TCalendar.this.isEditable()) {
					return true;
				}

				// �����⊮����
				doAutoComplete();

				if (!isAppropriateValue(getPlainText())) {
					if (isAllowableBlank) {
						clearField();
					} else {
						// �ҏW�O�̗L���Ȓl���Đݒ�
						setValue(getValue(getOldText()));
					}
				}

				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				// �t�H�[�J�X���Ă鏈��
				focusGainedAction();
			}
		});

		initType();
	}

	/**
	 * �t�H�[�J�X���Ă鏈��
	 */
	protected void focusGainedAction() {
		if (Util.isNullOrEmpty(getText())) {
			TCalendar.this.setCaretPosition(0);
			return;
		}

		focusAdjustment();
		selectAll();
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
		switch (calendarType) {
			case TYPE_Y:
				this.setCaretPosition(4);
				break;
			case TYPE_YM:
				this.setCaretPosition(7);
				break;
			case TYPE_YMD:
				this.setCaretPosition(10);
				break;
			case TYPE_YMDHM:
				this.setCaretPosition(16);
				break;
			case TYPE_YMDHMS:
				this.setCaretPosition(19);
				break;
		}
	}

	/**
	 * ���t�^�C�v�̐ݒ�
	 */
	protected void initType() {
		int width;

		// �������t��ݒ�
		switch (calendarType) {
			case TYPE_Y:
				width = 40;
				initText = INIT_CALENDAR_Y;
				break;

			case TYPE_YM:
				width = 60;
				initText = INIT_CALENDAR_YM;
				break;

			case TYPE_YMDHM:
				this.popupWidth = 210;
				width = 110;
				initText = INIT_CALENDAR_YMDHM;
				break;

			case TYPE_YMDHMS:
				this.popupWidth = 210;
				width = 125;
				initText = INIT_CALENDAR_YMDHMS;
				break;

			default: // YMD
				width = 75;
				initText = INIT_CALENDAR_YMD;
				break;
		}

		setPlainText(getNowDate());
		this.pushOldText();

		Dimension size = new Dimension(width, 20);
		size = TGuiUtil.correctSize(TGuiUtil.TYPE_CALENDER, size);

		super.setPreferredSize(size);
		super.setMaximumSize(size);
		super.setMinimumSize(size);
		super.setSize(size.width, size.height);
	}

	/**
	 * �t�B�[���h�𖢓��͏�Ԃɂ���
	 */
	protected void clearField() {
		setPlainText(initText);
	}

	/**
	 * �����⊮����(HMS����)
	 */
	protected void doAutoComplete() {

		if (isTableCellEditor()) {
			// �X�v���b�h�R���|�̏ꍇ�͏����s�v
			return;
		}

		// �����⊮�����e�L�X�g�ōĐݒ�
		getAutoCompleteText(getPlainText(), true);
	}

	/**
	 * �����⊮�����e�L�X�g�̎擾
	 * 
	 * @param plain
	 * @param doReset true:�R���|�l���Đݒ肷��
	 * @return �����⊮�����e�L�X�g
	 */
	protected String getAutoCompleteText(String plain, boolean doReset) {

		if (isNotUseAutoComplete) {
			// �@�\������
			return plain;
		}

		String[] arr = plain.split(" ");

		if (arr.length != 2) {
			return plain;
		}

		switch (calendarType) {
			case TYPE_YMDHM:
			case TYPE_YMDHMS:

				arr[1] = arr[1].replaceAll(UNDERSCORE, "0");

				StringBuilder sb = new StringBuilder();
				sb.append(arr[0]);
				sb.append(" ");
				sb.append(arr[1]);

				String n = sb.toString();

				if (doReset) {
					setValue(n);
				}

				return n;
			default:
				return plain;
		}
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
			switch (calendarType) {
				case TYPE_Y:
					if (!Util.isNumber(inputValue)) {
						return false;
					}
					if (Integer.parseInt(inputValue) < DateUtil.getYear(minDate)) {
						return false;
					}
					if (Integer.parseInt(inputValue) > DateUtil.getYear(maxDate)) {
						return false;
					}
					break;

				case TYPE_YM:
					if (inputValue.length() != 7) {
						return false;
					}
					if (inputValue.split(SLASH).length != 2) {
						return false;
					}
					if (inputValue.indexOf(SLASH) != 4) {
						return false;
					}

					inputDate = DateUtil.toYMDate(inputValue);

					break;

				case TYPE_YMD:
					if (inputValue.split(SLASH).length != 3) {
						return false;
					}
					if (inputValue.length() != 10) {
						return false;
					}
					if (inputValue.indexOf(SLASH) != 4) {
						return false;
					}
					if (inputValue.lastIndexOf(SLASH) != 7) {
						return false;
					}

					inputDate = DateUtil.toYMDDate(inputValue);

					break;

				case TYPE_YMDHM:
					if (inputValue.split(SLASH).length != 3) {
						return false;
					}
					if (inputValue.length() != 16) {
						return false;
					}
					if (inputValue.indexOf(SLASH) != 4) {
						return false;
					}
					if (inputValue.lastIndexOf(SLASH) != 7) {
						return false;
					}

					inputDate = DateUtil.toYMDHMDate(inputValue);

					break;

				case TYPE_YMDHMS:
					if (inputValue.split(SLASH).length != 3) {
						return false;
					}
					if (inputValue.length() != 19) {
						return false;
					}
					if (inputValue.indexOf(SLASH) != 4) {
						return false;
					}
					if (inputValue.lastIndexOf(SLASH) != 7) {
						return false;
					}

					inputDate = DateUtil.toYMDHMSDate(inputValue);

					break;
			}

			if (inputDate != null && (inputDate.before(minDate) || inputDate.after(maxDate))) {
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

		// �X�y�[�X(SPACE)�L�[�������āA�J�����_�[��\������
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			if (!isAppropriateValue(getPlainText())) {
				setPlainText(getNowDate());
			}
			showCalendar();
			return;
		}

		// BakcSpace key press
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			setPlainText(inputText.substring(0, position) + initText.substring(position, initText.length()));
			this.setCaretPosition(position);
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

		// �N���`�F�b�N����
		if (position == 0) {
			if (e.getKeyChar() == KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_2) {
				e.consume();
				return;
			}
		}

		if (position == 1) {
			if (inputText.substring(0, 1).equals("2")) {
				if (e.getKeyChar() > KeyEvent.VK_0) {
					e.consume();
					return;
				}
			} else {
				if (e.getKeyChar() < KeyEvent.VK_9) {
					e.consume();
					return;
				}
			}
		}
		// �����`�F�b�N����
		if (position == 5) {
			if (e.getKeyChar() > KeyEvent.VK_1) {
				e.consume();
				return;
			}
		}
		if (position == 6) {
			if (inputText.substring(5, 6).equals("0")) {
				if (e.getKeyChar() == KeyEvent.VK_0) {
					e.consume();
					return;
				}
			}
			if (inputText.substring(5, 6).equals("1")) {
				if (e.getKeyChar() > KeyEvent.VK_2) {
					e.consume();
					return;
				}
			}
		}
		// �����`�F�b�N����
		if (position == 8) {

			if (inputText.substring(5, 7).equals("02")) {
				if (e.getKeyChar() > KeyEvent.VK_2) {
					e.consume();
					return;
				}
			} else {
				if (e.getKeyChar() > KeyEvent.VK_3) {
					e.consume();
					return;
				}
			}
		}

		if (position == 9) {
			// ���̓e�L�X�g�̍ŏI�����擾����
			int year = Util.avoidNullAsInt(inputText.substring(0, 4));
			int month = Util.avoidNullAsInt(inputText.substring(5, 7));
			int lastDay = DateUtil.getLastDay(year, month);

			// ���邤�N�ȊO��2/29���`�F�b�N����
			if (!isLeapYear(year) && month == 2 && inputText.substring(8, 9).equals("2")) {
				if (e.getKeyChar() > KeyEvent.VK_8) {
					e.consume();
					return;
				}
			}
			// �`�F�b�N00
			if (inputText.substring(8, 9).equals("0")) {
				if (e.getKeyChar() == KeyEvent.VK_0) {
					e.consume();
					return;
				}
			}
			// 31�����`�F�b�N����
			if (lastDay == 31) {
				if (inputText.substring(8, 9).equals("3")) {
					if (e.getKeyChar() > KeyEvent.VK_1) {
						e.consume();
						return;
					}
				}
			}
			// 30�����`�F�b�N����
			if (lastDay == 30) {
				if (inputText.substring(8, 9).equals("3")) {
					if (e.getKeyChar() > KeyEvent.VK_0) {
						e.consume();
						return;
					}
				}
			}
		}

		// �N���������b�̏�
		if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) {
			// �`�F�b�N�̎�
			if (position == 11) {
				if (e.getKeyChar() > KeyEvent.VK_2) {
					e.consume();
					return;
				}
			}
			if (position == 12) {
				if (getPlainText().substring(11, 12).equals("2")) {
					if (e.getKeyChar() > KeyEvent.VK_3) {
						e.consume();
						return;
					}
				}
			}
			// �����`�F�b�N���� �b���`�F�b�N����
			if (position == 14 || position == 17) {
				if (e.getKeyChar() > KeyEvent.VK_5) {
					e.consume();
					return;
				}
			}
		}

		// �J�[�\�����ړ�����
		if (position < 4) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}
		if (position == 4) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 4 && this.getSelectionStart() == 3) {
				this.setCaretPosition(3);
				keyPress(KeyEvent.VK_DELETE);

			} else {
				if (calendarType != TYPE_Y) {
					// �J�[�\����5�Ɉړ�����
					this.setCaretPosition(5);
				}

				e.consume();
				return;
			}
		}
		if (position > 4 && position < 7) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}

		// �N��
		if (calendarType == TYPE_YM) {
			if (position == 7) {
				e.consume();
				return;
			}
		}

		if (position == 7) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 7 && this.getSelectionStart() == 6) {
				this.setCaretPosition(6);
				keyPress(KeyEvent.VK_DELETE);
			} else {
				e.consume();
				this.setCaretPosition(8);
			}
		}
		if (position > 7 && position < 10) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}

		// �N����
		if (calendarType == TYPE_YMD) {
			if (position == 10) {
				e.consume();
				return;
			}
		}

		if (position == 10) {
			e.consume();
			this.setCaretPosition(11);
		}
		if (position > 10 && position < 13) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}
		if (position == 13) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 13 && this.getSelectionStart() == 12) {
				this.setCaretPosition(12);
				keyPress(KeyEvent.VK_DELETE);
			} else {
				e.consume();
				this.setCaretPosition(14);
			}
		}
		if (position > 13 && position < 16) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}

		// �N��������
		if (calendarType == TYPE_YMDHM) {
			if (position == 16) {
				e.consume();
				return;
			}
		}

		if (position == 16) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 16 && this.getSelectionStart() == 15) {
				this.setCaretPosition(15);
				keyPress(KeyEvent.VK_DELETE);
			} else {
				e.consume();
				this.setCaretPosition(17);
			}
		}
		if (position > 16 && position < 19) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}
		if (position >= 19) {
			e.consume();
		}
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

		Date dt = getValue();
		if (dt == null) {
			return false;
		}

		int year = DateUtil.getYear(dt);
		int month = DateUtil.getMonth(dt);
		int day = DateUtil.getDay(dt);
		int hour = DateUtil.getHour(dt);
		int minute = DateUtil.getMinute(dt);
		int second = DateUtil.getSecond(dt);

		// JTextField���J�����g�J�[�\���̈ʒu�F
		int position = this.getCaretPosition();

		int intYear = 0;
		int intMonth = 0;
		int intDay = 0;
		int intHour = 0;
		int intMinute = 0;
		int intSecond = 0;

		// DOWN key press
		if (position <= 4) {
			// �N��I��
			this.select(0, 4);
			intYear = Integer.parseInt(this.getSelectedText()) - 1;
			// �N�̍ŏ��l��1900
			if (intYear == DateUtil.getYear(minDate) - 1) {
				return false;
			}
			if (calendarType != TYPE_YM) {
				// ���邤�N�̔��f
				if (this.isLeapYear(year - 1)) {
					if (month == 2 && day == 28) {
						if (calendarType == TYPE_YMD) setPlainText(intYear + SLASH + 0 + month + SLASH + 29);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(intYear + SLASH + 0
							+ month + SLASH + 29 + inputText.substring(10, inputText.length()));
						this.select(0, 4);
						return false;
					}
				} else {
					if (month == 2 && day == 29) {
						if (calendarType == TYPE_YMD) setPlainText(intYear + SLASH + 0 + month + SLASH + 28);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(intYear + SLASH + 0
							+ month + SLASH + 28 + inputText.substring(10, inputText.length()));
						this.select(0, 4);
						return false;
					}
				}
			}

			setPlainText(intYear + inputText.substring(4, inputText.length()));
			this.select(0, 4);
		}
		if (position <= 7 && position > 4) {
			// ����I��
			this.select(5, 7);
			intMonth = Integer.parseInt(this.getSelectedText()) - 1;

			if (intMonth == 0) {
				if (year != DateUtil.getYear(minDate)) {
					if (calendarType == TYPE_YM) setPlainText((year - 1) + SLASH + "12");
					else setPlainText((year - 1) + SLASH + 12 + inputText.substring(7, inputText.length()));

					this.select(5, 7);
				}
				return false;
			}

			if (intMonth < 10) {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 5) + 0 + intMonth);
				else {
					if (intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5)
								+ 0
								+ intMonth
								+ SLASH
								+ 31
								+ inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + 0 + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
					if (intMonth == 4 || intMonth == 6 || intMonth == 9) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5)
								+ 0
								+ intMonth
								+ SLASH
								+ 30
								+ inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + 0 + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
					if (intMonth == 2) {
						if (this.isLeapYear(year)) {
							if (day == 31 || day == 30) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ SLASH + 29);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 5)
									+ 0
									+ intMonth
									+ SLASH
									+ 29
									+ inputText.substring(10, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ inputText.substring(7, inputText.length()));
							}
						} else {
							if (day == 31 || day == 30 || day == 29) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ SLASH + 28);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 5)
									+ 0
									+ intMonth
									+ SLASH
									+ 28
									+ inputText.substring(10, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ inputText.substring(7, inputText.length()));
							}

						}
					}
				}
			} else {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 5) + intMonth);
				else {
					if (intMonth == 10 || intMonth == 12) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5) + intMonth + SLASH + 31 + inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
					if (intMonth == 11) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5) + intMonth + SLASH + 30 + inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
				}
			}

			this.select(5, 7);
		}

		if (position > 7 && position < 11) {
			// ����I��
			this.select(8, 10);
			intDay = Integer.parseInt(this.getSelectedText()) - 1;

			if (intDay == 0) {
				if (month < 11) {
					if (month == 1) {
						if (year != DateUtil.getYear(minDate)) {
							if (calendarType == TYPE_YMD) setPlainText((year - 1) + SLASH + 12 + SLASH + 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year - 1)
								+ SLASH + 12 + SLASH + 31 + inputText.substring(10, inputText.length()));
						}
					} else {
						if (this.isLeapYear(year) && month == 3) {
							if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month - 1) + SLASH + 29);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
								+ 0 + (month - 1) + SLASH + 29 + inputText.substring(10, inputText.length()));
						}
						if (!this.isLeapYear(year) && month == 3) {
							if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month - 1) + SLASH + 28);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
								+ 0 + (month - 1) + SLASH + 28 + inputText.substring(10, inputText.length()));
						}
						if (month == 2 || month == 4 || month == 6 || month == 9 || month == 8 || month == 11) {
							if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month - 1) + SLASH + 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
								+ 0 + (month - 1) + SLASH + 31 + inputText.substring(10, inputText.length()));
						}
						if (month == 12 || month == 5 || month == 7 || month == 10) {
							if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month - 1) + SLASH + 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
								+ 0 + (month - 1) + SLASH + 30 + inputText.substring(10, inputText.length()));
						}
					}
				} else {
					if (month == 12) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month - 1) + SLASH + 30);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month - 1) + SLASH + 30 + inputText.substring(10, inputText.length()));
					} else {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month - 1) + SLASH + 31);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month - 1) + SLASH + 31 + inputText.substring(10, inputText.length()));
					}
				}

				this.select(8, 10);
				return false;
			}

			if (intDay < 10) {
				if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + "0" + intDay);
				if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText.substring(0, 8)
					+ "0" + intDay + inputText.substring(10, inputText.length()));
			} else {
				setPlainText(inputText.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
			}

			this.select(8, 10);
		}

		// ��
		if (position >= 11 && position <= 13) {
			// ����I��
			this.select(11, 13);

			intHour = Integer.parseInt(this.getSelectedText()) - 1;

			if (intHour == -1) {
				setPlainText(inputText.substring(0, 11) + "23" + inputText.substring(13, inputText.length()));
				this.select(11, 13);
				return false;
			}
			if (intHour < 10) setPlainText(inputText.substring(0, 11) + 0 + intHour
				+ inputText.substring(13, inputText.length()));
			else setPlainText(inputText.substring(0, 11) + intHour + inputText.substring(13, inputText.length()));

			this.select(11, 13);
		}

		// ��
		if (position > 13 && position <= 16) {
			// ����I��
			this.select(14, 16);

			intMinute = Integer.parseInt(this.getSelectedText()) - 1;

			if (intMinute == -1) {
				if (hour == 0) setPlainText(inputText.substring(0, 11) + getHMS("23", "59", second));
				else setPlainText(inputText.substring(0, 11) + getHMS((hour - 1), "59", second));

				this.select(14, 16);

				return false;
			}

			setPlainText(inputText.substring(0, 11) + getHMS(hour, intMinute, second));

			this.select(14, 16);
		}

		// �b
		if (position > 16 && position <= 19) {
			// �b��I��
			this.select(17, 19);

			intSecond = Integer.parseInt(this.getSelectedText()) - 1;

			if (intSecond == -1) {
				if (minute == 0) {
					if (hour == 0) setPlainText(inputText.substring(0, 11) + getHMS("23", "59", "59"));
					else setPlainText(inputText.substring(0, 11) + getHMS((hour - 1), "59", "59"));
				} else if (minute > 10 && minute <= 59) {
					setPlainText(inputText.substring(0, 11) + getHMS(hour, (minute - 1), "59"));
				} else if (minute > 0 && minute <= 10) {
					setPlainText(inputText.substring(0, 11) + getHMS(hour, (minute - 1), "59"));
				}
				this.select(17, 19);

				return false;
			}

			setPlainText(inputText.substring(0, 11) + getHMS(hour, minute, intSecond));

			this.select(17, 19);
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

		Date dt = getValue();
		if (dt == null) {
			return false;
		}

		int year = DateUtil.getYear(dt);
		int month = DateUtil.getMonth(dt);
		int day = DateUtil.getDay(dt);
		int hour = DateUtil.getHour(dt);
		int minute = DateUtil.getMinute(dt);
		int second = DateUtil.getSecond(dt);

		// JTextField���J�����g�J�[�\���̈ʒu�F
		int position = this.getCaretPosition();

		int intYear = 0;
		int intMonth = 0;
		int intDay = 0;
		int intHour = 0;
		int intMinute = 0;
		int intSecond = 0;

		// �N��I��
		if (position <= 4) {
			this.select(0, 4);
			intYear = Integer.parseInt(this.getSelectedText()) + 1;
			// �N�̍ő�l
			if (intYear == DateUtil.getYear(maxDate) + 1) {
				return false;
			}

			if (calendarType != TYPE_YM) {
				// ���邤�N�̔��f
				if (this.isLeapYear(year + 1)) {
					if (month == 2 && day == 28) {
						if (calendarType == TYPE_YMD) setPlainText(intYear + SLASH + 0 + month + SLASH + 29);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(intYear + SLASH + 0
							+ month + SLASH + 29 + inputText.substring(10, inputText.length()));
						this.select(0, 4);
						return false;
					}
				} else {
					if (month == 2 && day == 29) {
						if (calendarType == TYPE_YMD) setPlainText(intYear + SLASH + 0 + month + SLASH + 28);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(intYear + SLASH + 0
							+ month + SLASH + 28 + inputText.substring(10, inputText.length()));
						this.select(0, 4);
						return false;
					}
				}
			}

			setPlainText(intYear + inputText.substring(4, inputText.length()));
			this.select(0, 4);
		}
		if (position <= 7 && position > 4) {
			// ����I��
			this.select(5, 7);
			intMonth = Integer.parseInt(this.getSelectedText()) + 1;

			if (intMonth == 13) {
				if (year != DateUtil.getYear(maxDate)) {
					if (calendarType == TYPE_YM) setPlainText((year + 1) + SLASH + "01");
					else setPlainText((year + 1) + SLASH + "01" + inputText.substring(7, inputText.length()));
					this.select(5, 7);
				}
				return false;
			}

			if (intMonth < 10) {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 5) + 0 + intMonth);
				else {
					if (intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5)
								+ 0
								+ intMonth
								+ SLASH
								+ 31
								+ inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + 0 + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
					if (intMonth == 4 || intMonth == 6 || intMonth == 9) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5)
								+ 0
								+ intMonth
								+ SLASH
								+ 30
								+ inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + 0 + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
					if (intMonth == 2) {
						if (this.isLeapYear(year)) {
							if (day == 31 || day == 30) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ SLASH + 29);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 5)
									+ 0
									+ intMonth
									+ SLASH
									+ 29
									+ inputText.substring(10, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ inputText.substring(7, inputText.length()));
							}
						} else {
							if (day == 31 || day == 30 || day == 29) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ SLASH + 28);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 5)
									+ 0
									+ intMonth
									+ SLASH
									+ 28
									+ inputText.substring(10, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 5) + 0 + intMonth
									+ inputText.substring(7, inputText.length()));
							}

						}
					}
				}
			} else {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 5) + intMonth);
				else {
					if (intMonth == 10 || intMonth == 12) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5) + intMonth + SLASH + 31 + inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
					if (intMonth == 11) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 5) + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 5) + intMonth + SLASH + 30 + inputText.substring(10, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 5) + intMonth
								+ inputText.substring(7, inputText.length()));
						}
					}
				}
			}

			this.select(5, 7);
		}

		if (position > 7 && position < 11) {
			// ����I��
			this.select(8, 10);
			intDay = Integer.parseInt(this.getSelectedText()) + 1;

			if (this.isLeapYear(year)) {
				if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH + 0
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 4 || month == 6) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH + 0
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 9) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}
				}

				if (month == 2) {
					if (day == 29) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH + 0
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 10) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 11) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 12) {
					if (day == 31) {
						if (year == DateUtil.getYear(maxDate)) {
							return false;
						} else {
							if (calendarType == TYPE_YMD) setPlainText((year + 1) + SLASH + "01" + SLASH + "01");
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year + 1)
								+ SLASH + "01" + SLASH + "01" + inputText.substring(10, inputText.length()));
						}
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}

			} else {
				if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH + 0
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 4 || month == 6) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH + 0
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 9) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 2) {
					if (day == 28) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + 0 + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH + 0
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 10) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 11) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText((year) + SLASH + (month + 1) + SLASH + "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year) + SLASH
							+ (month + 1) + SLASH + "01" + inputText.substring(10, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
				if (month == 12) {
					if (day == 31) {
						if (year == DateUtil.getYear(maxDate)) {
							return false;
						} else {
							if (calendarType == TYPE_YMD) setPlainText((year + 1) + SLASH + "01" + SLASH + "01");
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText((year + 1)
								+ SLASH + "01" + SLASH + "01" + inputText.substring(10, inputText.length()));
						}
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 8) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 8) + intDay + inputText.substring(10, inputText.length()));
						}
					}

				}
			}
			this.select(8, 10);
		}

		// ��
		if (position >= 11 && position <= 13) {
			// ����I��
			this.select(11, 13);

			intHour = Integer.parseInt(this.getSelectedText()) + 1;
			// ���Ԃ̍ő�l��24
			if (intHour == 24) {
				setPlainText(inputText.substring(0, 11) + "00" + inputText.substring(13, inputText.length()));
				this.select(11, 13);
				return false;
			}
			if (intHour < 10) setPlainText(inputText.substring(0, 11) + 0 + intHour
				+ inputText.substring(13, inputText.length()));
			else setPlainText(inputText.substring(0, 11) + intHour + inputText.substring(13, inputText.length()));

			this.select(11, 13);
		}

		// ��
		if (position > 13 && position <= 16) {
			// ����I��
			this.select(14, 16);

			intMinute = Integer.parseInt(this.getSelectedText()) + 1;

			// ���Ԃ̍ő�l��24
			if (intMinute == 60) {

				if (hour == 23) setPlainText(inputText.substring(0, 11) + getHMS("00", "00", second));
				else setPlainText(inputText.substring(0, 11) + getHMS((hour + 1), "00", second));

				this.select(14, 16);

				return false;
			}

			setPlainText(inputText.substring(0, 11) + getHMS(hour, intMinute, second));

			this.select(14, 16);
		}

		// �b
		if (position > 16 && position <= 19) {
			// �b��I��
			this.select(17, 19);
			intSecond = Integer.parseInt(this.getSelectedText()) + 1;

			// ���Ԃ̍ő�l��24
			if (intSecond == 60) {
				if (minute == 59) {
					if (hour == 23) {
						setPlainText(inputText.substring(0, 11) + getHMS("00", "00", "00"));
					} else {
						setPlainText(inputText.substring(0, 11) + getHMS((hour + 1), "00", "00"));
					}

				} else if (minute >= 9 && minute < 59) {
					setPlainText(inputText.substring(0, 11) + getHMS(hour, (minute + 1), "00"));

				} else if (minute < 9) {
					setPlainText(inputText.substring(0, 11) + getHMS(hour, (minute + 1), "00"));
				}

				this.select(17, 19);

				return false;
			}

			setPlainText(inputText.substring(0, 11) + getHMS(hour, minute, intSecond));

			this.select(17, 19);
		}

		return true;
	}

	/**
	 * �����b�̃e�L�X�g���擾����(�^�C�v��)
	 * 
	 * @param hour ��
	 * @param min ��
	 * @param sec �b
	 * @return �����b�e�L�X�g
	 */
	protected String getHMS(Object hour, Object min, Object sec) {
		String strHour = String.valueOf(hour);
		String strMin = String.valueOf(min);
		String strSec = String.valueOf(sec);
		if (strHour.length() == 1) {
			strHour = "0" + strHour;
		}
		if (strMin.length() == 1) {
			strMin = "0" + strMin;
		}
		if (strSec.length() == 1) {
			strSec = "0" + strSec;
		}

		switch (calendarType) {
			case TYPE_YMDHM:
				return strHour + COLON + strMin;

			default: // YMDHMS
				return strHour + COLON + strMin + COLON + strSec;
		}
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

			if (unscIndex < 4) {
				setValue(new Date());
				focusAdjustment();

			} else {
				// �r���܂œ��͂���Ă���΁A����𗘗p
				int year = Integer.parseInt(inputText.substring(0, 4));
				int month = 1;
				int day = 1;
				int hour = 0;
				int minute = 0;
				int position = 7;

				if (7 < unscIndex) {
					month = Integer.parseInt(inputText.substring(5, 7));
					position = 10;
				}

				if (10 < unscIndex) {
					day = Integer.parseInt(inputText.substring(8, 10));
					position = 13;
				}

				if (13 < unscIndex) {
					hour = Integer.parseInt(inputText.substring(11, 13));
					position = 16;
				}

				if (16 < unscIndex) {
					minute = Integer.parseInt(inputText.substring(14, 16));
					position = 19;
				}

				setValue(DateUtil.getDate(year, month, day, hour, minute, 0));
				setCaretPosition(position);
			}

			inputText = this.getText();
		}

		return inputText;
	}

	/**
	 * ���邤�N���ۂ��𔻒f����
	 * 
	 * @param year
	 * @return true:���邤�N
	 */
	protected boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year);
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
	protected String getNowDate() {
		Date now = new Date();

		switch (calendarType) {
			case TYPE_Y:
				return DateUtil.toYString(now);
			case TYPE_YM:
				return DateUtil.toYMString(now);
			case TYPE_YMD:
				return DateUtil.toYMDString(now);
			case TYPE_YMDHM:
				return DateUtil.toYMDHMString(now);
			case TYPE_YMDHMS:
				return DateUtil.toYMDHMSString(now);
			default:
				return "";
		}
	}

	/**
	 * Calendar �I����ʕ\��
	 */
	protected void showCalendar() {

		if (!isShowYCalendar && calendarType == TYPE_Y) {
			// �N�݂͕̂\�����Ȃ�
			return;
		}

		Rectangle r = this.getBounds();
		Point pOnScreen = this.getLocationOnScreen();
		Point result = new Point(pOnScreen.x, pOnScreen.y + r.height);

		Container owner = TGuiUtil.getParentFrameOrDialog(this);
		Point powner = owner.getLocation();

		int offsetX = (pOnScreen.x - r.width - popupWidth) - (powner.x + owner.getWidth());
		int offsetY = (pOnScreen.y - r.height - popupHeight) - (powner.y + owner.getHeight());

		if (offsetX > 0) {
			result.x -= offsetX;
		}

		if (offsetY > 0) {
			result.y -= popupHeight + r.height;
		}

		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;

		if ((pOnScreen.y + r.height + popupHeight) > height) {
			result.y -= popupHeight + r.height;
		}

		if ((pOnScreen.x + popupWidth) > width) {
			result.x -= popupWidth - r.width;
		}

		if (owner instanceof JDialog) {
			dateFrame = new JDialog((JDialog) owner);
		} else {
			dateFrame = new JDialog((Frame) owner);
		}

		dateFrame.setModal(false);
		dateFrame.setUndecorated(true);
		dateFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		dateFrame.setLocation(result);
		dateFrame.setSize(popupWidth, popupHeight);
		dateFrame.addWindowListener(new WindowAdapter() {

			// �C�ӂ̔���t�I��n��ŃN���b�N����ꍇ�A���t�I���R���|�[�l���g�͔񊈓���ԂɂȂ�A
			// �����I�Ƀ��\�[�X���ߕ�����B
			@Override
			public void windowDeactivated(WindowEvent e) {
				((JDialog) e.getSource()).dispose();
			}
		});

		datePanel = new DatePanel(dateFrame);

		// �ő�A�ŏ��N���̐ݒ�
		datePanel.setStartYear(DateUtil.getYear(minDate));
		datePanel.setLastYear(DateUtil.getYear(maxDate));

		Date date = this.getValue();
		if (date == null) {
			// �����͏�Ԃ͌��ݓ���
			date = new Date();
		}

		datePanel.yearSpin.setValue(DateUtil.getYear(date));
		datePanel.monthSpin.setValue(DateUtil.getMonth(date));
		datePanel.timerSpin.setValue(date);

		dateFrame.getContentPane().setLayout(new BorderLayout());
		dateFrame.getContentPane().add(datePanel);
		dateFrame.setVisible(true);

		datePanel.initFocus();
	}

	/**
	 * �T�C�Y�͎����Ŕ��ʂ���ׁA����ȏꍇ�������g�p���Ȃ�����
	 * 
	 * @deprecated �T�C�Y�͎����Ŕ��ʂ���ׁA����ȏꍇ�������g�p���Ȃ�����
	 */
	@Override
	public void setPreferredSize(Dimension size) {
		Dimension newSize = new Dimension(size);
		// �����J�����_�[�Ή��̈� -15
		if (size.getWidth() - 15 > 0) {
			newSize.setSize(size.getWidth() - 15, size.getHeight());
		}
		super.setPreferredSize(newSize);
	}

	/**
	 * �T�C�Y�͎����Ŕ��ʂ���ׁA����ȏꍇ�������g�p���Ȃ�����
	 * 
	 * @deprecated �T�C�Y�͎����Ŕ��ʂ���ׁA����ȏꍇ�������g�p���Ȃ�����
	 */
	@Override
	public void setMaximumSize(Dimension size) {
		Dimension newSize = new Dimension(size);
		// �����J�����_�[�Ή��̈� -15
		if (size.getWidth() - 15 > 0) {
			newSize.setSize(size.getWidth() - 15, size.getHeight());
		}
		super.setMaximumSize(newSize);
	}

	/**
	 * ���͍ŏ����t
	 * 
	 * @return �ŏ����t
	 */
	public Date getMinimumDate() {
		return minDate;
	}

	/**
	 * ���͍ŏ����t
	 * 
	 * @param dt �ŏ����t
	 */
	public void setMinimumDate(Date dt) {
		if (dt == null) return;

		this.minDate = dt;

	}

	/**
	 * ���͍ő���t
	 * 
	 * @return �ő���t
	 */
	public Date getMaximumDate() {
		return maxDate;
	}

	/**
	 * ���͍ő���t
	 * 
	 * @param dt �ő���t
	 */
	public void setMaximumDate(Date dt) {
		if (dt == null) return;

		this.maxDate = dt;
	}

	/**
	 * �u�����N���͋����
	 * 
	 * @param b true:����
	 */
	public void setAllowableBlank(boolean b) {

		this.isAllowableBlank = b;

		if (getValue() == null) {
			if (this.isAllowableBlank) {
				clearField();
			} else {
				setPlainText(getNowDate());
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

		String txt = super.getText();

		if (isTableCellEditor()) {
			// �X�v���b�h�R���|�̏ꍇ�̂ݏ���

			// �����⊮�e�L�X�g���擾(�l�Đݒ�s�v)
			txt = getAutoCompleteText(txt, false);
		}

		return txt;
	}

	/**
	 * �e�L�X�g�ݒ�
	 * 
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		if (isAppropriateValue(text)) {
			setPlainText(text);
		} else {
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
	 * @param date ����
	 */
	public void setValue(Date date) {

		if (date == null) {
			if (!this.isAllowableBlank) {
				date = new Date();
			} else {
				clearField();
				return;
			}
		}

		String dateText = "";
		String minText = "";
		String maxText = "";
		switch (calendarType) {
			case TYPE_Y:
				dateText = DateUtil.toYString(date);
				minText = DateUtil.toYString(minDate);
				maxText = DateUtil.toYString(maxDate);
				break;
			case TYPE_YM:
				dateText = DateUtil.toYMString(date);
				minText = DateUtil.toYMString(minDate);
				maxText = DateUtil.toYMString(maxDate);
				break;
			case TYPE_YMD:
				dateText = DateUtil.toYMDString(date);
				minText = DateUtil.toYMDString(minDate);
				maxText = DateUtil.toYMDString(maxDate);
				break;
			case TYPE_YMDHM:
				dateText = DateUtil.toYMDHMString(date);
				minText = DateUtil.toYMDHMString(minDate);
				maxText = DateUtil.toYMDHMString(maxDate);
				break;
			case TYPE_YMDHMS:
				dateText = DateUtil.toYMDHMSString(date);
				minText = DateUtil.toYMDHMSString(minDate);
				maxText = DateUtil.toYMDHMSString(maxDate);
				break;
		}

		if (minText.compareTo(dateText) <= 0 && dateText.compareTo(maxText) <= 0) {
			setPlainText(dateText);
		}
	}

	/**
	 * �e�L�X�g�t�B�[���h������t���擾����.
	 * 
	 * @return ���t
	 */
	@Override
	public Date getValue() {
		return getValue(getPlainText());
	}

	/**
	 * �w�茎�̖�����Ԃ�(�N�A�N���\���p)
	 * 
	 * @return ���t
	 */
	public Date getLastDate() {
		return DateUtil.getLastDate(getValue());
	}

	/**
	 * �w��e�L�X�g�t�B�[���h������t���擾����.
	 * 
	 * @param text ���t�e�L�X�g
	 * @return ���t
	 */
	protected Date getValue(String text) {
		try {
			Date date = null;
			switch (calendarType) {
				case TYPE_Y:
					date = DateUtil.toYDate(text);
					break;
				case TYPE_YM:
					date = DateUtil.toYMDate(text);
					break;
				case TYPE_YMD:
					date = DateUtil.toYMDDate(text);
					break;
				case TYPE_YMDHM:
					date = DateUtil.toYMDHMDate(text);
					break;
				case TYPE_YMDHMS:
					date = DateUtil.toYMDHMSDate(text);
					break;
			}

			return date;

		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * ���͂���Ă���N���擾
	 * 
	 * @return �N
	 */
	public int getYear() {
		return DateUtil.getYear(getValue());
	}

	/**
	 * Calendar�̂��T�C�Y��ݒu����
	 * 
	 * @deprecated �����Ȃ̂ŗ��p���Ȃ�
	 * @param size �T�C�Y
	 */
	@Override
	public void setSize(Dimension size) {
		super.setPreferredSize(size);
		super.setMaximumSize(size);
		super.setMinimumSize(size);
	}

	/**
	 * Calendar�̂��T�C�Y��ݒu����
	 * 
	 * @deprecated �����Ȃ̂ŗ��p���Ȃ�
	 * @param width ���T�C�Y
	 * @param height �c�T�C�Y
	 */
	@Override
	public void setSize(int width, int height) {
		setSize(new Dimension(width, height));
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		TTextField renderer = new TTextField() {

			@Override
			public Font getFont() {
				return TCalendar.this.getFont();
			}
		};
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		return new TTextRenderer(renderer, tbl);
	}

	/**
	 * �����ށA���t���R���g���[���̎�̂�I������A�S�Ă̓��t�I����e���J�v�Z�������B��v��Panel
	 */
	protected class DatePanel extends TPanel implements MouseListener, ChangeListener {

		/** �����萔 */
		protected static final int YEAR = 1;

		protected static final int MONTH = 2;

		protected static final int DAY = 3;

		protected static final int HOUR = 4;

		protected static final int MINUTE = 5;

		protected static final int SECOND = 6;

		/** �ŏ��\���N��ٔF���� */
		protected int startYear = DateUtil.getYear(TCalendar.this.minDate);

		/** �ő�\���N��ٔF���� */
		protected int lastYear = DateUtil.getYear(TCalendar.this.maxDate);

		// ***�J�����_�[�V�[�g�z�F****

		/** �n�F */
		protected Color backGroundColor = Color.GRAY;

		/** �J�����_�[�̈�n�F */
		protected Color palletTableColor = new Color(252, 252, 252);

		/** �R���g���[���o�[�n�F */
		protected Color controlLineColor = Color.LIGHT_GRAY;

		/** �j�������F */
		protected Color weekFontColor = Color.BLACK;

		/** ���t�����F */
		protected Color dateFontColor = Color.BLACK;

		/** ���j(�@��x��)�����F */
		protected Color sundayColor = Color.RED;

		/** �y�j(����x��)�����F */
		protected Color saturdayColor = Color.BLUE;

		/** �}�E�X�[�ړ��̃J�����_�[�n�F */
		protected Color moveButtonColor = Color.PINK;

		/** �����̃J�����_�[�̒n�F */
		protected Color todayBtnColor = Color.GREEN;

		/** �t�H�[�J�X�J���[ */
		protected Color focusColor = Color.RED;

		/** �N */
		protected JSpinner yearSpin;

		/** �� */
		protected JSpinner monthSpin;

		/** ���� */
		protected JSpinner timerSpin;

		/** �����{�^�� */
		protected JButton[][] daysButton = new JButton[6][7];

		protected JButton numberButton;

		protected JDialog f;

		/** ���tpanel */
		protected JPanel dayPanel = new JPanel();

		protected JPanel yearPanel = new JPanel();

		/** �J�����_�[ */
		protected Calendar calendar = Calendar.getInstance();

		/**
		 * ���t�I���R���g���[�����񃂁[�_���_�C�A���O�ɓ��ꂽ�B
		 * 
		 * @param target �^�[�Q�b�gDialog
		 */
		public DatePanel(JDialog target) {
			super();

			this.f = target;

			// ESC������ �J�����_�[�����
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

				public boolean dispatchKeyEvent(KeyEvent ke) {
					if (ke.getKeyChar() == KeyEvent.VK_ESCAPE) {
						DatePanel.this.dispose();
					} else if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
						// TODO �X�y�[�X�ł��ł��Ȃ���
						numberButtonEnterKeyPressed(ke);
					}

					return false;
				}
			});

			setLayout(new BorderLayout());
			setBorder(new LineBorder(this.backGroundColor, 2));
			setBackground(this.backGroundColor);

			// �������h���b�v���t�̃{�^���B
			initButton();

			// �N�����̃p�l����V�K�쐬����
			createYearAndMonthPanal();

			// �L�[�{�[�h�œ��t��I������ �K�v
			isButtonListener();

			Date date = TCalendar.this.getValue();
			calendar.setTime(date == null ? new Date() : date);

			this.yearSpin.setValue(getCalendarOneInt(YEAR));
			this.monthSpin.setValue(getCalendarOneInt(MONTH));

			// �O�Ƀh���b�v���t�̃{�^���̏������������������Ƃ�ۏ؂��Ȃ���΂Ȃ�Ȃ��B
			this.flushWeekAndDayPanal(calendar);

			this.setLayout(new BorderLayout());
			this.add(yearPanel, BorderLayout.NORTH);

			JPanel dayPanelWrap = new JPanel(new BorderLayout());
			dayPanelWrap.add(dayPanel, BorderLayout.NORTH);
			dayPanelWrap.setBackground(palletTableColor);

			this.add(dayPanelWrap, BorderLayout.CENTER);
		}

		/**
		 * �L�[�{�[�h�œ��t��I������
		 */
		protected void isButtonListener() {
			if (this.numberButton.isFocusOwner()) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

					public boolean dispatchKeyEvent(KeyEvent ke) {
						if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
							numberButtonKeyPressed(ke);
						}
						return false;
					}
				});
			}
		}

		/**
		 * ���t�I���R���g���[���̃{�^��������
		 */
		protected void initButton() {
			int actionCommandId = 1;
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					numberButton = new JButton();
					numberButton.setBorder(BorderFactory.createEmptyBorder());
					numberButton.setHorizontalAlignment(SwingConstants.CENTER);
					numberButton.setActionCommand(String.valueOf(actionCommandId));
					numberButton.setContentAreaFilled(false);
					numberButton.addMouseListener(this);
					numberButton.setFocusPainted(false);

					numberButton.addKeyListener(new KeyAdapter() {

						@Override
						public void keyPressed(KeyEvent evt) {
							if (evt.getKeyChar() == KeyEvent.VK_ENTER || evt.getKeyChar() == KeyEvent.VK_SPACE) {
								numberButtonKeyPressed(evt);
							}
							if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
								downKeyListener();
							}
							if (evt.getKeyCode() == KeyEvent.VK_UP) {
								upKeyListener();
							}
							if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
								leftKeyListener();
							}
							if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
								rightKeyListener();
							}
						}
					});

					numberButton.addFocusListener(new FocusListener() {

						public void focusGained(FocusEvent e) {
							focusGainedEvent();
						}

						public void focusLost(FocusEvent e) {
							focusLostEvent();
						}
					});

					numberButton.setBackground(this.palletTableColor);
					numberButton.setForeground(this.dateFontColor);
					numberButton.setText(String.valueOf(actionCommandId));
					numberButton.setPreferredSize(new Dimension(25, 25));
					daysButton[i][j] = numberButton;
					actionCommandId++;
				}
			}
		}

		/**
		 * focus Gained
		 */
		protected void focusGainedEvent() {

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 7; j++) {

					int comm = Integer.parseInt(daysButton[i][j].getActionCommand());
					if (isNowMonth() && comm == getCalendarOneInt(DAY)) {
						daysButton[i][j].setBorder(new LineBorder(this.todayBtnColor, 1, true));
					}

					if (daysButton[i][j].isFocusOwner()) {
						daysButton[i][j].setBorder(new LineBorder(this.focusColor, 1, true));
					}
				}
			}
		}

		/**
		 * focus Lost
		 */
		protected void focusLostEvent() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 7; j++) {
					if (daysButton[i][j].isFocusOwner() == false) {
						daysButton[i][j].setBorder(null);
					}
					int comm = Integer.parseInt(daysButton[i][j].getActionCommand());
					if (isNowMonth() && comm == getCalendarOneInt(DAY)) {
						daysButton[i][j].setBorder(new LineBorder(this.todayBtnColor, 1, true));
					}
				}
			}
		}

		/**
		 * �E�{�^������
		 */
		protected void rightKeyListener() {
			int digit = getSelectedMonth();

			if (digit == 1 || digit == 3 || digit == 5 || digit == 7 || digit == 8 || digit == 10 || digit == 12) {
				if (daysButton[4][2].isFocusOwner()) {
					leftOrRightChangeCalendar("right");
				} else {
					keyPress(KeyEvent.VK_TAB);
				}
			} else if (digit == 4 || digit == 6 || digit == 9 || digit == 11) {
				if (daysButton[4][1].isFocusOwner()) {
					leftOrRightChangeCalendar("right");
				} else {
					keyPress(KeyEvent.VK_TAB);
				}
			} else if (digit == 2) {
				if (TCalendar.this.isLeapYear(this.getSelectedYear())) {
					if (daysButton[4][0].isFocusOwner()) {
						leftOrRightChangeCalendar("right");
					} else {
						keyPress(KeyEvent.VK_TAB);
					}
				} else {
					if (daysButton[3][6].isFocusOwner()) {
						leftOrRightChangeCalendar("right");
					} else {
						keyPress(KeyEvent.VK_TAB);
					}
				}
			}
		}

		/**
		 * ���{�^������
		 */
		protected void leftKeyListener() {
			try {
				if (daysButton[0][0].isFocusOwner()) {
					leftOrRightChangeCalendar("left");
				} else {
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_TAB);
				}
			} catch (AWTException e1) {
				//
			}
		}

		/**
		 * ���L�[�̏�L�[����
		 */
		protected void upKeyListener() {
			int digit = this.getSelectedMonth();
			int t = 0;
			if (daysButton[0][0].isFocusOwner()) t = Integer.parseInt(daysButton[0][0].getActionCommand());
			if (daysButton[0][1].isFocusOwner()) t = Integer.parseInt(daysButton[0][1].getActionCommand());
			if (daysButton[0][2].isFocusOwner()) t = Integer.parseInt(daysButton[0][2].getActionCommand());
			if (daysButton[0][3].isFocusOwner()) t = Integer.parseInt(daysButton[0][3].getActionCommand());
			if (daysButton[0][4].isFocusOwner()) t = Integer.parseInt(daysButton[0][4].getActionCommand());
			if (daysButton[0][5].isFocusOwner()) t = Integer.parseInt(daysButton[0][5].getActionCommand());
			if (daysButton[0][6].isFocusOwner()) t = Integer.parseInt(daysButton[0][6].getActionCommand());

			if (getUpFocusDay(digit, t) > 21 && getUpFocusDay(digit, t) <= 31) {
				if (digit == 1) {

					if (this.getSelectedYear() == startYear && getSelectedMonth() == 1) {
						calendar.set(Calendar.MONTH, 0);
						calendar.set(Calendar.YEAR, this.getSelectedYear());
						this.monthSpin.setValue(1);
						this.yearSpin.setValue(this.getSelectedYear());
					} else {
						calendar.set(Calendar.MONTH, 12);
						calendar.set(Calendar.YEAR, this.getSelectedYear() - 1);
						this.monthSpin.setValue(12);
						this.yearSpin.setValue(this.getSelectedYear() - 1);
					}

				} else {
					calendar.set(Calendar.MONTH, digit - 1);
					this.monthSpin.setValue(digit - 1);
				}
				dayPanel.removeAll();
				this.flushWeekAndDayPanal(calendar);
				setNextMonthFocus(getUpFocusDay(digit, t));
				dayPanel.revalidate();
				dayPanel.updateUI();
				return;
			} else {
				for (int i = 1; i < 5; i++) {
					for (int j = 0; j < 7; j++) {
						if (daysButton[i][j].isFocusOwner()) {
							daysButton[i - 1][j].requestFocus();
						}
					}
				}
			}
		}

		/**
		 * ���L�[�̉��L�[����
		 */
		protected void downKeyListener() {
			int digit = this.getSelectedMonth();
			int t = 0;
			if (daysButton[4][0].isFocusOwner()) t = Integer.parseInt(daysButton[4][0].getActionCommand());
			if (daysButton[4][1].isFocusOwner()) t = Integer.parseInt(daysButton[4][1].getActionCommand());
			if (daysButton[4][2].isFocusOwner()) t = Integer.parseInt(daysButton[4][2].getActionCommand());
			if (daysButton[3][0].isFocusOwner()) t = Integer.parseInt(daysButton[3][0].getActionCommand());
			if (daysButton[3][1].isFocusOwner()) t = Integer.parseInt(daysButton[3][1].getActionCommand());
			if (daysButton[3][2].isFocusOwner()) t = Integer.parseInt(daysButton[3][2].getActionCommand());
			if (daysButton[3][3].isFocusOwner()) t = Integer.parseInt(daysButton[3][3].getActionCommand());
			if (daysButton[3][4].isFocusOwner()) t = Integer.parseInt(daysButton[3][4].getActionCommand());
			if (daysButton[3][5].isFocusOwner()) t = Integer.parseInt(daysButton[3][5].getActionCommand());
			if (daysButton[3][6].isFocusOwner()) t = Integer.parseInt(daysButton[3][6].getActionCommand());
			if (daysButton[2][6].isFocusOwner()) t = Integer.parseInt(daysButton[2][6].getActionCommand());

			if (getDownFocusDay(digit, t) > 0) {
				if (digit == 12) {

					if (this.getSelectedYear() == lastYear && getSelectedMonth() == 12) {
						calendar.set(Calendar.MONTH, 12 - 1);
						calendar.set(Calendar.YEAR, this.getSelectedYear());
						this.monthSpin.setValue(12);
						this.yearSpin.setValue(this.getSelectedYear());
					} else {
						calendar.set(Calendar.MONTH, 1);
						calendar.set(Calendar.YEAR, this.getSelectedYear() + 1);
						this.monthSpin.setValue(1);
						this.yearSpin.setValue(this.getSelectedYear() + 1);
					}

				} else {
					calendar.set(Calendar.MONTH, digit + 1);
					this.monthSpin.setValue(digit + 1);
				}
				dayPanel.removeAll();
				this.flushWeekAndDayPanal(calendar);
				setNextMonthFocus(getDownFocusDay(digit, t));
				dayPanel.revalidate();
				dayPanel.updateUI();
				return;
			} else {
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 7; j++) {
						if (daysButton[i][j].isFocusOwner()) {
							daysButton[i + 1][j].requestFocus();
						}
					}
				}
			}
		}

		/**
		 * Down Key�����̓��t���擾����
		 * 
		 * @param month
		 * @param digit
		 * @return �����̓��t
		 */
		protected int getDownFocusDay(int month, int digit) {
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				return (digit + 7) - 31;
			}
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				return (digit + 7) - 30;
			}
			if (month == 2) {
				if (TCalendar.this.isLeapYear(this.getSelectedYear())) {
					return (digit + 7) - 29;
				} else {
					return (digit + 7) - 28;
				}
			}
			return 0;
		}

		/**
		 * UP Key�挎�̓��t���擾����
		 * 
		 * @param month
		 * @param digit
		 * @return �挎�̓��t
		 */
		protected int getUpFocusDay(int month, int digit) {
			if (month == 5 || month == 7 || month == 10 || month == 12) {
				if ((digit - 7) <= 0 && (digit - 7) > -7) return (digit + 30) - 7;
			}
			if (month == 1 || month == 4 || month == 6 || month == 9 || month == 11 || month == 8 || month == 2) {
				if ((digit - 7) <= 0 && (digit - 7) > -7) return (digit + 31) - 7;
			}
			if (month == 3) {
				if (TCalendar.this.isLeapYear(this.getSelectedYear())) {
					if ((digit - 7) <= 0 && (digit - 7) > -7) return (digit + 29) - 7;
				} else {
					if ((digit - 7) <= 0 && (digit - 7) > -7) return (digit + 28) - 7;
				}
			}
			return 0;
		}

		/**
		 * �t�H�[�J�X�ݒu
		 * 
		 * @param nextDay
		 */
		protected void setNextMonthFocus(int nextDay) {
			if (nextDay == 1) daysButton[0][0].requestFocus();
			if (nextDay == 2) daysButton[0][1].requestFocus();
			if (nextDay == 3) daysButton[0][2].requestFocus();
			if (nextDay == 4) daysButton[0][3].requestFocus();
			if (nextDay == 5) daysButton[0][4].requestFocus();
			if (nextDay == 6) daysButton[0][5].requestFocus();
			if (nextDay == 7) daysButton[0][6].requestFocus();

			if (nextDay == 31) daysButton[4][2].requestFocus();
			if (nextDay == 30) daysButton[4][1].requestFocus();
			if (nextDay == 29) daysButton[4][0].requestFocus();
			if (nextDay == 28) daysButton[3][6].requestFocus();
			if (nextDay == 27) daysButton[3][5].requestFocus();
			if (nextDay == 26) daysButton[3][4].requestFocus();
			if (nextDay == 25) daysButton[3][3].requestFocus();
			if (nextDay == 24) daysButton[3][2].requestFocus();
			if (nextDay == 23) daysButton[3][1].requestFocus();
			if (nextDay == 22) daysButton[3][0].requestFocus();
		}

		/**
		 * ���E�{�^���œ��t��ϊ�����
		 * 
		 * @param change
		 */
		protected void leftOrRightChangeCalendar(String change) {
			int digit = 0;
			if (change.equals("left")) {
				digit = getSelectedMonth() - 1;
			}
			if (change.equals("right")) {
				digit = getSelectedMonth() + 1;
			}
			if (change.equals("left") && digit == 0) {

				if (this.getSelectedYear() == startYear && getSelectedMonth() == 1) {
					calendar.set(Calendar.MONTH, 0);
					calendar.set(Calendar.YEAR, this.getSelectedYear());
					this.yearSpin.setValue(getSelectedYear());
					this.monthSpin.setValue(1);
				} else {
					calendar.set(Calendar.MONTH, 12);
					calendar.set(Calendar.YEAR, this.getSelectedYear() - 1);
					this.yearSpin.setValue(getSelectedYear() - 1);
					this.monthSpin.setValue(12);
				}

			} else if (change.equals("right") && digit == 13) {

				if (this.getSelectedYear() == lastYear && getSelectedMonth() == 12) {
					calendar.set(Calendar.MONTH, 12 - 1);
					calendar.set(Calendar.YEAR, this.getSelectedYear());
					this.yearSpin.setValue(getSelectedYear());
					this.monthSpin.setValue(12);
				} else {
					calendar.set(Calendar.MONTH, 1);
					calendar.set(Calendar.YEAR, this.getSelectedYear() + 1);
					this.yearSpin.setValue(getSelectedYear() + 1);
					this.monthSpin.setValue(1);
				}

			} else {
				calendar.set(Calendar.MONTH, digit);
				this.monthSpin.setValue(digit);
			}

			dayPanel.removeAll();
			this.flushWeekAndDayPanal(calendar);

			if (digit == 1 || digit == 3 || digit == 5 || digit == 7 || digit == 8 || digit == 10 || digit == 12
				|| digit == 0) {
				if (change.equals("left")) {
					daysButton[4][2].requestFocus();
				} else {
					daysButton[0][0].requestFocus();
				}
			}
			if (digit == 4 || digit == 6 || digit == 9 || digit == 11 || digit == 13) {
				if (change.equals("left")) {
					daysButton[4][1].requestFocus();
				} else {
					daysButton[0][0].requestFocus();
				}
			}
			if (digit == 2) {
				if (change.equals("left")) {
					if (TCalendar.this.isLeapYear(this.getSelectedYear())) {
						daysButton[4][0].requestFocus();
					} else {
						daysButton[3][6].requestFocus();
					}
				} else {
					daysButton[0][0].requestFocus();
				}
			}

			dayPanel.revalidate();
			dayPanel.updateUI();
		}

		/**
		 * �I���̓��t���擾����
		 * 
		 * @return �I����t
		 */
		protected Date getSelectDate() {
			return calendar.getTime();
		}

		/**
		 * �N�����̃p�l����V�K�쐬����
		 */
		protected void createYearAndMonthPanal() {
			// �N��
			Calendar c = Calendar.getInstance();
			int currentYear = c.get(Calendar.YEAR);
			int currentMonth = c.get(Calendar.MONTH) + 1;
			yearSpin = new JSpinner(new SpinnerNumberModel(currentYear, this.getStartYear(), this.getLastYear(), 1));
			monthSpin = new JSpinner(new SpinnerNumberModel(currentMonth, 0, 13, 1));

			yearSpin.addMouseWheelListener(new SpinnerWheelListener(yearSpin));
			monthSpin.addMouseWheelListener(new SpinnerWheelListener(monthSpin));

			// timer
			SpinnerDateModel timerModel = new SpinnerDateModel();
			timerModel.setCalendarField(Calendar.HOUR_OF_DAY);
			timerSpin = new JSpinner(timerModel);
			JSpinner.DateEditor timerEditor = new JSpinner.DateEditor(timerSpin, DATE_HMS);
			timerSpin.setEditor(timerEditor);

			yearPanel.setLayout(new FlowLayout());
			yearPanel.setBackground(controlLineColor);

			yearSpin.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_CALENDER, new Dimension(50, 20)));
			yearSpin.setName("Year");

			// TODO �\�ł���΁A���l�݂̂̓��͐����
			yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
			yearSpin.addChangeListener(this);
			yearPanel.add(yearSpin);

			if (calendarType != TYPE_Y) {
				monthSpin.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_CALENDER, new Dimension(37, 20)));
				monthSpin.setName("Month");
				monthSpin.addChangeListener(this);
				yearPanel.add(monthSpin);
			}

			// timer
			if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) {
				timerSpin.setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_CALENDER, new Dimension(72, 20)));
				timerSpin.setName("Timer");
				timerSpin.addChangeListener(this);
				yearPanel.add(timerSpin);
			}
		}

		/**
		 * ���t�Ɋ�Â��ăp�l�����X�V����
		 * 
		 * @param c �J�����_�[
		 */
		protected void flushWeekAndDayPanal(Calendar c) {

			c.set(Calendar.DAY_OF_MONTH, 1);
			c.setFirstDayOfWeek(0);

			int firstdayofWeek = c.get(Calendar.DAY_OF_WEEK);
			int lastdayofWeek = c.getActualMaximum(Calendar.DAY_OF_MONTH);

			int today = getCalendarOneInt(DAY);
			if (today == 31) {
				if (this.getSelectedMonth() == 2 || this.getSelectedMonth() == 4 || this.getSelectedMonth() == 6
					|| this.getSelectedMonth() == 9 || this.getSelectedMonth() == 11) {
					today = 1;
				}
			} else if (today == 30) {
				if (this.getSelectedMonth() == 2) {
					today = 1;
				}
			} else {
				today = getCalendarOneInt(DAY);
			}

			dayPanel.setLayout(new GridBagLayout());
			dayPanel.setBackground(palletTableColor);

			for (int i = 0; i < 7; i++) {
				JLabel cell = new JLabel(dayOfWeekWords[i]);
				TUIManager.setMonospacedFont(cell); // �T�C�Y�ψ�t�H���g��

				cell.setHorizontalAlignment(SwingConstants.CENTER);
				cell.setPreferredSize(new Dimension(25, 25));
				if (i == 0) {
					cell.setForeground(this.sundayColor);
				} else if (i == 6) {
					cell.setForeground(this.saturdayColor);
				} else {
					cell.setForeground(this.weekFontColor);
				}
				dayPanel.add(cell, new GridBagConstraints(i, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}

			int actionCommandId = 1;
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {

					numberButton = daysButton[i][j];
					actionCommandId = Integer.parseInt(numberButton.getActionCommand());
					if (isNowMonth() && actionCommandId == today) {
						numberButton.setBackground(todayBtnColor);
						numberButton.setBorder(new LineBorder(todayBtnColor, 1, true));
					} else {
						numberButton.setBackground(palletTableColor);
						numberButton.setBorder(new LineBorder(palletTableColor, 1, true));
					}
					if ((actionCommandId + firstdayofWeek - 2) % 7 == 0) {
						numberButton.setForeground(sundayColor);
					} else if ((actionCommandId + firstdayofWeek - 2) % 7 == 6) {
						numberButton.setForeground(saturdayColor);
					} else {
						numberButton.setForeground(dateFontColor);
					}

					if (actionCommandId <= lastdayofWeek) {
						int y = 0;
						if ((firstdayofWeek - 1) <= (j + firstdayofWeek - 1) % 7) {
							y = i + 1;
						} else {
							y = i + 2;
						}
						dayPanel.add(numberButton, new GridBagConstraints((j + firstdayofWeek - 1) % 7, y, 1, 1, 0.0,
							0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					}
				}
			}
		}

		/**
		 * �J�����_�[��ʂ����
		 */
		protected void dispose() {
			initFocus();
			f.dispose();
		}

		/**
		 * �t�H�[�J�X�����ݒ�
		 */
		public void initFocus() {

			// �t�H�[�J�X�͕\�����t��
			boolean isFocusOn = false;

			length: for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 7; j++) {

					int comm = Integer.parseInt(daysButton[i][j].getActionCommand());
					if (isNowMonth() && comm == getCalendarOneInt(DAY)) {
						daysButton[i][j].setBorder(new LineBorder(this.todayBtnColor, 1, true));
						daysButton[i][j].requestFocusInWindow();
						isFocusOn = true;
						break length;
					}
				}
			}

			// �t�H�[�J�X�Ώۂ����������ꍇ��1��
			if (!isFocusOn) {
				daysButton[0][0].requestFocusInWindow();
			}
		}

		/**
		 * �t�B�[���h��̃^�C�v�ʓ��t�l�擾
		 * 
		 * @param type �^�C�v
		 * @return �l
		 */
		protected int getCalendarOneInt(int type) {
			Date dt = TCalendar.this.getValue();

			// null���
			if (dt == null) {
				return 0;
			}

			switch (type) {
				case YEAR:
					return DateUtil.getYear(dt);

				case MONTH:
					return DateUtil.getMonth(dt);

				case DAY:
					return DateUtil.getDay(dt);

				case HOUR:
					return DateUtil.getHour(dt);

				case MINUTE:
					return DateUtil.getMinute(dt);

				case SECOND:
					return DateUtil.getSecond(dt);
				default:
					return 0;
			}
		}

		/**
		 * �N��I��
		 * 
		 * @return �N
		 */
		protected int getSelectedYear() {
			return ((Integer) yearSpin.getValue()).intValue();
		}

		/**
		 * ����I��
		 * 
		 * @return ��
		 */
		protected int getSelectedMonth() {
			return ((Integer) monthSpin.getValue()).intValue();
		}

		/**
		 * ���Ԃ�I��
		 * 
		 * @return ����
		 */
		protected String getSelectedTimer() {
			return DateUtil.toHMSString((Date) timerSpin.getValue());
		}

		/**
		 * �N�����Ԃ̃C�x���g����
		 * 
		 * @param e ChangeEvent
		 */
		public void stateChanged(ChangeEvent e) {

			JSpinner source = (JSpinner) e.getSource();

			if (source.getName().equals("Timer")) {
				String hms = getSelectedTimer();
				String[] arrHms = hms.split(COLON);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHms[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(arrHms[1]));
				calendar.set(Calendar.SECOND, Integer.parseInt(arrHms[2]));
				return;
			}
			if (source.getName().equals("Year")) {
				calendar.set(Calendar.YEAR, getSelectedYear());
				dayPanel.removeAll();
				this.flushWeekAndDayPanal(calendar);
				dayPanel.revalidate();
				dayPanel.updateUI();
				return;
			}

			if (source.getName().equals("Month")) {
				if ((getSelectedMonth() - 1) == 12) {
					dayPanel.removeAll();

					if (getSelectedYear() == lastYear) {
						yearSpin.setValue(getSelectedYear());
						calendar.set(Calendar.YEAR, getSelectedYear());
						calendar.set(Calendar.MONTH, 1);
					} else {
						yearSpin.setValue(getSelectedYear() + 1);
						calendar.set(Calendar.YEAR, getSelectedYear() + 1);
						calendar.set(Calendar.MONTH, 1);
					}

					yearSpin.revalidate();
					yearSpin.updateUI();
					monthSpin.setValue(1);
					monthSpin.revalidate();
					monthSpin.updateUI();
					this.flushWeekAndDayPanal(calendar);
					monthSpin.requestFocusInWindow();
					dayPanel.revalidate();
					dayPanel.updateUI();
					keyPress(KeyEvent.VK_TAB);
					return;

				} else if (getSelectedMonth() == 0) {
					dayPanel.removeAll();

					if (getSelectedYear() == startYear) {
						yearSpin.setValue(getSelectedYear());
						calendar.set(Calendar.YEAR, getSelectedYear());
						calendar.set(Calendar.MONTH, 12);
					} else {
						yearSpin.setValue(getSelectedYear() - 1);
						calendar.set(Calendar.YEAR, getSelectedYear() - 1);
						calendar.set(Calendar.MONTH, 12);
					}

					yearSpin.revalidate();
					yearSpin.updateUI();
					monthSpin.setValue(12);
					monthSpin.revalidate();
					monthSpin.updateUI();
					this.flushWeekAndDayPanal(calendar);
					monthSpin.requestFocusInWindow();
					dayPanel.revalidate();
					dayPanel.updateUI();
					keyPress(KeyEvent.VK_TAB);
					return;
				} else {
					calendar.set(Calendar.YEAR, getSelectedYear());
					calendar.set(Calendar.MONTH, getSelectedMonth() - 1);
					dayPanel.removeAll();
					this.flushWeekAndDayPanal(calendar);
					dayPanel.revalidate();
					dayPanel.updateUI();
					return;
				}
			}
		}

		/**
		 * ���t�{�^���̃}�E�X�[�C�x���g����
		 * 
		 * @param e MouseEvent
		 */
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				JButton source = (JButton) e.getSource();
				calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(source.getText()));
				String[] arrHms = getSelectedTimer().split(COLON);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHms[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(arrHms[1]));
				calendar.set(Calendar.SECOND, Integer.parseInt(arrHms[2]));

				this.reflectCalendarField();

				DatePanel.this.dispose();
			}
		}

		public void mousePressed(MouseEvent e) {
			//
		}

		public void mouseReleased(MouseEvent e) {
			//
		}

		/**
		 * �}�E�X�[���J�����_�[�Ɉړ�����C�x���g
		 * 
		 * @param e MouseEvent
		 */
		public void mouseEntered(MouseEvent e) {
			JButton jbutton = (JButton) e.getSource();
			jbutton.setBackground(this.moveButtonColor);
			jbutton.setBorder(new LineBorder(this.moveButtonColor, 1, true));
		}

		/**
		 * �}�E�X�[���J�����_�[����ڂ������C�x���g
		 * 
		 * @param e MouseEvent
		 */
		public void mouseExited(MouseEvent e) {
			JButton jbutton = (JButton) e.getSource();
			int comm = Integer.parseInt(jbutton.getActionCommand());

			jbutton.setBackground(this.palletTableColor);
			jbutton.setBorder(new LineBorder(this.palletTableColor, 1, true));

			if (isNowMonth() && comm == getCalendarOneInt(DAY)) {
				jbutton.setBackground(this.todayBtnColor);
				jbutton.setBorder(new LineBorder(todayBtnColor, 1, true));
			}

			if (jbutton.isFocusOwner()) {
				jbutton.setBorder(new LineBorder(this.focusColor, 1, true));
			}
		}

		/**
		 * �I������Ă���N�A�����e�L�X�g�Ɠ��ꂩ�ǂ���
		 * 
		 * @return true:����
		 */
		protected boolean isNowMonth() {

			return getSelectedYear() == getCalendarOneInt(YEAR) && getSelectedMonth() == getCalendarOneInt(MONTH);
		}

		/**
		 * ����Button�ǉ��L�[�I�[�h�C�x���g
		 * 
		 * @param evt
		 */
		protected void numberButtonKeyPressed(KeyEvent evt) {
			if (this.isJFormattedTextFieldCast(evt)) {
				calendar.set(Calendar.YEAR, getSelectedYear());
				calendar.set(Calendar.MONTH, getSelectedMonth() - 1);
				calendar.set(Calendar.DAY_OF_MONTH, getCalendarOneInt(DAY));

				this.reflectCalendarField();

				DatePanel.this.dispose();
				return;

			} else if (isJButtonCast(evt)) {
				calendar.set(Calendar.YEAR, getSelectedYear());
				calendar.set(Calendar.MONTH, getSelectedMonth() - 1);
				calendar.set(Calendar.DAY_OF_MONTH, getDayNo(evt));

				String[] arrHms = getSelectedTimer().split(COLON);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHms[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(arrHms[1]));
				calendar.set(Calendar.SECOND, Integer.parseInt(arrHms[2]));

				this.reflectCalendarField();

				DatePanel.this.dispose();
				return;
			}
		}

		/**
		 * day button key event
		 * 
		 * @param evt KeyEvent
		 */
		protected void numberButtonEnterKeyPressed(KeyEvent evt) {
			if (this.isJFormattedTextFieldCast(evt)) {
				calendar.set(Calendar.YEAR, getSelectedYear());
				calendar.set(Calendar.MONTH, getSelectedMonth() - 1);
				calendar.set(Calendar.DAY_OF_MONTH, getCalendarOneInt(DAY));
				String[] arrHms = getSelectedTimer().split(COLON);
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHms[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(arrHms[1]));
				calendar.set(Calendar.SECOND, Integer.parseInt(arrHms[2]));

				this.reflectCalendarField();

				DatePanel.this.dispose();
			}
		}

		/**
		 * �J�����_�[�t�B�[���h�ւ̔��f
		 */
		protected void reflectCalendarField() {
			switch (calendarType) {
				case TYPE_YM:
					setText(DateUtil.toYMString(getSelectDate()));
					break;
				case TYPE_YMD:
					setText(DateUtil.toYMDString(getSelectDate()));
					break;
				case TYPE_YMDHM:
					setText(DateUtil.toYMDHMString(getSelectDate()));
					break;
				case TYPE_YMDHMS:
					setText(DateUtil.toYMDHMSString(getSelectDate()));
					break;
				default:
					setText(DateUtil.toYString(getSelectDate()));
					break;
			}
		}

		/**
		 * Button�l
		 * 
		 * @param evt
		 * @return ��
		 */
		protected int getDayNo(KeyEvent evt) {
			JButton source = (JButton) evt.getSource();
			String value = source.getText();
			int day = Integer.parseInt(value);
			return day;
		}

		/**
		 * @param evt
		 * @return true:FmattedText
		 */
		protected boolean isJFormattedTextFieldCast(KeyEvent evt) {
			try {
				JFormattedTextField source = (JFormattedTextField) evt.getSource();
				if (source.equals("")) {
					return true;
				}
				return true;
			} catch (ClassCastException cce) {
				return false;
			}
		}

		/**
		 * @param evt
		 * @return true:JButton
		 */
		protected boolean isJButtonCast(KeyEvent evt) {
			try {
				JButton source = (JButton) evt.getSource();
				if (source.equals("")) {
					return true;
				}
				return true;
			} catch (ClassCastException cce) {
				return false;
			}
		}

		/**
		 * �ŏ��\���N���擾����
		 * 
		 * @return �ŏ��\���N
		 */
		public int getStartYear() {
			return startYear;
		}

		/**
		 * �ŏ��\���N��ݒu����
		 * 
		 * @param startYear
		 */
		public void setStartYear(int startYear) {
			this.startYear = startYear;
		}

		/**
		 * �ő�\���N���擾����
		 * 
		 * @return �ő�\���N
		 */
		public int getLastYear() {
			return lastYear;
		}

		/**
		 * �ő�\���N��ݒu����
		 * 
		 * @param lastYear �ŏ��\���N
		 */
		public void setLastYear(int lastYear) {
			this.lastYear = lastYear;
		}
	}

	/**
	 * JSppinner�̃}�E�X�z�C�[�����X�i�[
	 */
	protected class SpinnerWheelListener implements MouseWheelListener {

		protected JSpinner spinner;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param spinner �Ώ�Sppinner
		 */
		public SpinnerWheelListener(JSpinner spinner) {
			this.spinner = spinner;
		}

		public void mouseWheelMoved(MouseWheelEvent e) {

			if (!spinner.getEditor().getComponent(0).isFocusOwner()) {
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
				Object value = isDown ? spinner.getPreviousValue() : spinner.getNextValue();

				if (value != null) {
					spinner.setValue(value);
				}
			}
		}

	}

	/**
	 * �j���̒P��ݒ�(������p)
	 * 
	 * @param wordList �P�ꃊ�X�g
	 */
	public static void setDayOfWeekWords(String[] wordList) {
		dayOfWeekWords = wordList;
	}
}
