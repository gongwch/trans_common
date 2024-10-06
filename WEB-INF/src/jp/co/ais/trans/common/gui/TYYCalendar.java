package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * YY/MM/DD�p �J�����_�[�R���|�[�l���g.
 */
public class TYYCalendar extends TPopupCalendar {

	static {
		// YY
		INIT_CALENDAR_Y = "__";

		// YY/MM
		INIT_CALENDAR_YM = "__" + SLASH + "__";

		// YY/MM/DD
		INIT_CALENDAR_YMD = "__" + SLASH + "__" + SLASH + "__";

		// YY/MM/DD HH:MM
		INIT_CALENDAR_YMDHM = "__" + SLASH + "__" + SLASH + "__ __:__";

		// YY/MM/DD HH:MM:SS
		INIT_CALENDAR_YMDHMS = "__" + SLASH + "__" + SLASH + "__ __:__:__";
	}

	/**
	 * @see TCalendar#TCalendar()
	 */
	public TYYCalendar() {
		this(TYPE_YMD);
	}

	/**
	 * @see TCalendar#TCalendar(int)
	 */
	public TYYCalendar(int type) {
		super(type);

		minDate = DateUtil.getDate(2000, 1, 1);
	}

	/**
	 * @see TCalendar#focusAdjustment()
	 */
	@Override
	protected void focusAdjustment() {
		switch (calendarType) {
			case TYPE_Y:
				this.setCaretPosition(2);
				break;
			case TYPE_YM:
				this.setCaretPosition(5);
				break;
			case TYPE_YMD:
				this.setCaretPosition(8);
				break;
			case TYPE_YMDHM:
				this.setCaretPosition(14);
				break;
			case TYPE_YMDHMS:
				this.setCaretPosition(17);
				break;
		}
	}

	/**
	 * @see TCalendar#initType()
	 */
	@Override
	protected void initType() {
		super.initType();

		// YY�̕��T�C�Y����
		Dimension size = getPreferredSize();
		size.setSize(size.getWidth() - 20d, size.getHeight());
		TGuiUtil.setComponentSize(this, size);
	}

	/**
	 * @see TCalendar#isAppropriateValue(String)
	 */
	@Override
	protected boolean isAppropriateValue(String inputValue) {
		return super.isAppropriateValue("20" + inputValue);
	}

	/**
	 * @see TCalendar#dateFieldActionPerformed(KeyEvent)
	 */
	@Override
	protected void dateFieldActionPerformed(KeyEvent e) {
		// �ʒu�����Ŋۍ����ւ�

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
		if (position == 0 || position == 1) {
			if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
				e.consume();
				return;
			}
		}

		// �����`�F�b�N����
		if (position == 3) {
			if (e.getKeyChar() > KeyEvent.VK_1) {
				e.consume();
				return;
			}
		}
		if (position == 4) {
			if (inputText.substring(3, 4).equals("0")) {
				if (e.getKeyChar() == KeyEvent.VK_0) {
					e.consume();
					return;
				}
			}
			if (inputText.substring(3, 4).equals("1")) {
				if (e.getKeyChar() > KeyEvent.VK_2) {
					e.consume();
					return;
				}
			}
		}
		// �����`�F�b�N����
		if (position == 6) {

			if (inputText.substring(3, 5).equals("02")) {
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

		if (position == 7) {
			// ���̓e�L�X�g�̍ŏI�����擾����
			int year = Util.avoidNullAsInt("20" + inputText.substring(0, 2));
			int month = Util.avoidNullAsInt(inputText.substring(3, 5));
			int lastDay = DateUtil.getLastDay(year, month);

			// ���邤�N�ȊO��2/29���`�F�b�N����
			if (!isLeapYear(year) && month == 2 && inputText.substring(6, 7).equals("2")) {
				if (e.getKeyChar() > KeyEvent.VK_8) {
					e.consume();
					return;
				}
			}
			// �`�F�b�N00
			if (inputText.substring(6, 7).equals("0")) {
				if (e.getKeyChar() == KeyEvent.VK_0) {
					e.consume();
					return;
				}
			}
			// 31�����`�F�b�N����
			if (lastDay == 31) {
				if (inputText.substring(6, 7).equals("3")) {
					if (e.getKeyChar() > KeyEvent.VK_1) {
						e.consume();
						return;
					}
				}
			}
			// 30�����`�F�b�N����
			if (lastDay == 30) {
				if (inputText.substring(6, 7).equals("3")) {
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
			if (position == 9) {
				if (e.getKeyChar() > KeyEvent.VK_2) {
					e.consume();
					return;
				}
			}
			if (position == 10) {
				if (getPlainText().substring(11, 12).equals("2")) {
					if (e.getKeyChar() > KeyEvent.VK_3) {
						e.consume();
						return;
					}
				}
			}
			// �����`�F�b�N���� �b���`�F�b�N����
			if (position == 12 || position == 15) {
				if (e.getKeyChar() > KeyEvent.VK_5) {
					e.consume();
					return;
				}
			}
		}

		// �J�[�\�����ړ�����
		if (position < 2) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}
		if (position == 2) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 2 && this.getSelectionStart() == 1) {
				this.setCaretPosition(1);
				keyPress(KeyEvent.VK_DELETE);

			} else {
				if (calendarType != TYPE_Y) {
					// �J�[�\����3�Ɉړ�����
					this.setCaretPosition(3);
				}

				e.consume();
				return;
			}
		}
		if (position > 2 && position < 5) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}

		// �N��
		if (calendarType == TYPE_YM) {
			if (position == 5) {
				e.consume();
				return;
			}
		}

		if (position == 5) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 5 && this.getSelectionStart() == 4) {
				this.setCaretPosition(5);
				keyPress(KeyEvent.VK_DELETE);
			} else {
				e.consume();
				this.setCaretPosition(6);
			}
		}
		if (position > 5 && position < 8) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}

		// �N����
		if (calendarType == TYPE_YMD) {
			if (position == 8) {
				e.consume();
				return;
			}
		}

		if (position == 8) {
			e.consume();
			this.setCaretPosition(9);
		}
		if (position > 8 && position < 11) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}
		if (position == 11) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 11 && this.getSelectionStart() == 10) {
				this.setCaretPosition(10);
				keyPress(KeyEvent.VK_DELETE);
			} else {
				e.consume();
				this.setCaretPosition(12);
			}
		}
		if (position > 11 && position < 14) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}

		// �N��������
		if (calendarType == TYPE_YMDHM) {
			if (position == 14) {
				e.consume();
				return;
			}
		}

		if (position == 14) {
			// �J�[�\����"/"�O�̈�ʂ�I�� ���͂���
			if (isInputNumber(e) && this.getSelectionEnd() == 14 && this.getSelectionStart() == 13) {
				this.setCaretPosition(13);
				keyPress(KeyEvent.VK_DELETE);
			} else {
				e.consume();
				this.setCaretPosition(15);
			}
		}
		if (position > 14 && position < 17) {
			if (isInputNumber(e)) {
				keyPress(KeyEvent.VK_DELETE);
			}
		}
		if (position >= 17) {
			e.consume();
		}
	}

	/**
	 * @see TCalendar#downAction()
	 */
	@Override
	protected boolean downAction() {
		// �ʒu�����Ŋۍ����ւ�

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
		if (position <= 2) {
			// �N��I��
			this.select(0, 2);
			intYear = Integer.parseInt("20" + this.getSelectedText()) - 1;

			// �N�̍ŏ��l��1900
			if (intYear == DateUtil.getYear(minDate) - 1) {
				return false;
			}
			if (calendarType != TYPE_YM) {
				// ���邤�N�̔��f
				if (this.isLeapYear(year - 1)) {
					if (month == 2 && day == 28) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(intYear) + SLASH + 0 + month + SLASH + 29);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(intYear)
							+ SLASH + 0 + month + SLASH + 29 + inputText.substring(8, inputText.length()));
						this.select(0, 2);
						return false;
					}
				} else {
					if (month == 2 && day == 29) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(intYear) + SLASH + 0 + month + SLASH + 28);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(intYear)
							+ SLASH + 0 + month + SLASH + 28 + inputText.substring(8, inputText.length()));
						this.select(0, 2);
						return false;
					}
				}
			}

			setPlainText(adjustYear(intYear) + inputText.substring(2, inputText.length()));
			this.select(0, 2);
		}

		if (position <= 5 && position > 2) {
			// ����I��
			this.select(3, 5);
			intMonth = Integer.parseInt(this.getSelectedText()) - 1;

			if (intMonth == 0) {
				if (year != DateUtil.getYear(minDate)) {
					if (calendarType == TYPE_YM) setPlainText(adjustYear(year - 1) + SLASH + "12");
					else setPlainText(adjustYear(year - 1) + SLASH + 12 + inputText.substring(5, inputText.length()));

					this.select(3, 5);
				}
				return false;
			}

			if (intMonth < 10) {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 3) + 0 + intMonth);
				else {
					if (intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3)
								+ 0
								+ intMonth
								+ SLASH
								+ 31
								+ inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + 0 + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
					if (intMonth == 4 || intMonth == 6 || intMonth == 9) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3)
								+ 0
								+ intMonth
								+ SLASH
								+ 30
								+ inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + 0 + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
					if (intMonth == 2) {
						if (this.isLeapYear(year)) {
							if (day == 31 || day == 30) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ SLASH + 29);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 3)
									+ 0
									+ intMonth
									+ SLASH
									+ 29
									+ inputText.substring(8, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ inputText.substring(5, inputText.length()));
							}
						} else {
							if (day == 31 || day == 30 || day == 29) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ SLASH + 28);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 3)
									+ 0
									+ intMonth
									+ SLASH
									+ 28
									+ inputText.substring(8, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ inputText.substring(5, inputText.length()));
							}
						}
					}
				}
			} else {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 3) + intMonth);
				else {
					if (intMonth == 10 || intMonth == 12) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3) + intMonth + SLASH + 31 + inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
					if (intMonth == 11) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3) + intMonth + SLASH + 30 + inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
				}
			}

			this.select(3, 5);
		}

		if (position > 5 && position < 9) {
			// ����I��
			this.select(6, 8);
			intDay = Integer.parseInt(this.getSelectedText()) - 1;

			if (intDay == 0) {
				if (month < 11) {
					if (month == 1) {
						if (year != DateUtil.getYear(minDate)) {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year - 1) + SLASH + 12 + SLASH + 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year - 1)
								+ SLASH + 12 + SLASH + 31 + inputText.substring(8, inputText.length()));
						}
					} else {
						if (this.isLeapYear(year) && month == 3) {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month - 1)
								+ SLASH + 29);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
								+ SLASH + 0 + (month - 1) + SLASH + 29 + inputText.substring(8, inputText.length()));
						}
						if (!this.isLeapYear(year) && month == 3) {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month - 1)
								+ SLASH + 28);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
								+ SLASH + 0 + (month - 1) + SLASH + 28 + inputText.substring(8, inputText.length()));
						}
						if (month == 2 || month == 4 || month == 6 || month == 9 || month == 8 || month == 11) {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month - 1)
								+ SLASH + 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
								+ SLASH + 0 + (month - 1) + SLASH + 31 + inputText.substring(8, inputText.length()));
						}
						if (month == 12 || month == 5 || month == 7 || month == 10) {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month - 1)
								+ SLASH + 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
								+ SLASH + 0 + (month - 1) + SLASH + 30 + inputText.substring(8, inputText.length()));
						}
					}
				} else {
					if (month == 12) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month - 1) + SLASH + 30);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month - 1) + SLASH + 30 + inputText.substring(8, inputText.length()));
					} else {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month - 1) + SLASH + 31);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month - 1) + SLASH + 31 + inputText.substring(8, inputText.length()));
					}
				}

				this.select(6, 8);
				return false;
			}

			if (intDay < 10) {
				if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + "0" + intDay);
				if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText.substring(0, 6)
					+ "0" + intDay + inputText.substring(8, inputText.length()));
			} else {
				setPlainText(inputText.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
			}

			this.select(6, 8);
		}

		// ��
		if (position >= 9 && position <= 11) {
			// ����I��
			this.select(9, 11);

			intHour = Integer.parseInt(this.getSelectedText()) - 1;

			if (intHour == -1) {
				setPlainText(inputText.substring(0, 9) + "23" + inputText.substring(11, inputText.length()));
				this.select(9, 11);
				return false;
			}
			if (intHour < 10) setPlainText(inputText.substring(0, 9) + 0 + intHour
				+ inputText.substring(11, inputText.length()));
			else setPlainText(inputText.substring(0, 9) + intHour + inputText.substring(11, inputText.length()));

			this.select(9, 11);
		}

		// ��
		if (position > 11 && position <= 14) {
			// ����I��
			this.select(12, 14);

			intMinute = Integer.parseInt(this.getSelectedText()) - 1;

			if (intMinute == -1) {
				if (hour == 0) setPlainText(inputText.substring(0, 9) + getHMS("23", "59", second));
				else setPlainText(inputText.substring(0, 9) + getHMS((hour - 1), "59", second));

				this.select(12, 14);

				return false;
			}

			setPlainText(inputText.substring(0, 9) + getHMS(hour, intMinute, second));

			this.select(12, 14);
		}

		// �b
		if (position > 14 && position <= 17) {
			// �b��I��
			this.select(15, 17);

			intSecond = Integer.parseInt(this.getSelectedText()) - 1;

			if (intSecond == -1) {
				if (minute == 0) {
					if (hour == 0) setPlainText(inputText.substring(0, 9) + getHMS("23", "59", "59"));
					else setPlainText(inputText.substring(0, 9) + getHMS((hour - 1), "59", "59"));
				} else if (minute > 10 && minute <= 59) {
					setPlainText(inputText.substring(0, 9) + getHMS(hour, (minute - 1), "59"));
				} else if (minute > 0 && minute <= 10) {
					setPlainText(inputText.substring(0, 9) + getHMS(hour, (minute - 1), "59"));
				}
				this.select(15, 17);

				return false;
			}

			setPlainText(inputText.substring(0, 9) + getHMS(hour, minute, intSecond));

			this.select(15, 17);
		}

		return true;
	}

	/**
	 * @see TCalendar#upAction()
	 */
	@Override
	protected boolean upAction() {
		// �ʒu�����Ŋۍ����ւ�

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
		if (position <= 2) {
			this.select(0, 2);
			intYear = Integer.parseInt("20" + this.getSelectedText()) + 1;

			// �N�̍ő�l
			if (intYear == DateUtil.getYear(maxDate) + 1) {
				return false;
			}

			if (calendarType != TYPE_YM) {
				// ���邤�N�̔��f
				if (this.isLeapYear(year + 1)) {
					if (month == 2 && day == 28) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(intYear) + SLASH + 0 + month + SLASH + 29);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(intYear)
							+ SLASH + 0 + month + SLASH + 29 + inputText.substring(8, inputText.length()));
						this.select(0, 2);
						return false;
					}
				} else {
					if (month == 2 && day == 29) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(intYear) + SLASH + 0 + month + SLASH + 28);
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(intYear)
							+ SLASH + 0 + month + SLASH + 28 + inputText.substring(8, inputText.length()));
						this.select(0, 2);
						return false;
					}
				}
			}

			setPlainText(adjustYear(intYear) + inputText.substring(2, inputText.length()));
			this.select(0, 2);
		}
		if (position <= 5 && position > 2) {
			// ����I��
			this.select(3, 5);
			intMonth = Integer.parseInt(this.getSelectedText()) + 1;

			if (intMonth == 13) {
				if (year != DateUtil.getYear(maxDate)) {
					if (calendarType == TYPE_YM) setPlainText(adjustYear(year + 1) + SLASH + "01");
					else setPlainText(adjustYear(year + 1) + SLASH + "01" + inputText.substring(5, inputText.length()));
					this.select(3, 5);
				}
				return false;
			}

			if (intMonth < 10) {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 3) + 0 + intMonth);
				else {
					if (intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3)
								+ 0
								+ intMonth
								+ SLASH
								+ 31
								+ inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + 0 + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
					if (intMonth == 4 || intMonth == 6 || intMonth == 9) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3)
								+ 0
								+ intMonth
								+ SLASH
								+ 30
								+ inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + 0 + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
					if (intMonth == 2) {
						if (this.isLeapYear(year)) {
							if (day == 31 || day == 30) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ SLASH + 29);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 3)
									+ 0
									+ intMonth
									+ SLASH
									+ 29
									+ inputText.substring(8, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ inputText.substring(5, inputText.length()));
							}
						} else {
							if (day == 31 || day == 30 || day == 29) {
								if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ SLASH + 28);
								if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
									.substring(0, 3)
									+ 0
									+ intMonth
									+ SLASH
									+ 28
									+ inputText.substring(8, inputText.length()));
							} else {
								setPlainText(inputText.substring(0, 3) + 0 + intMonth
									+ inputText.substring(5, inputText.length()));
							}

						}
					}
				}
			} else {
				if (calendarType == TYPE_YM) setPlainText(inputText.substring(0, 3) + intMonth);
				else {
					if (intMonth == 10 || intMonth == 12) {
						if (day == 30) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + intMonth + SLASH
								+ 31);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3) + intMonth + SLASH + 31 + inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
					if (intMonth == 11) {
						if (day == 31) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 3) + intMonth + SLASH
								+ 30);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 3) + intMonth + SLASH + 30 + inputText.substring(8, inputText.length()));
						} else {
							setPlainText(inputText.substring(0, 3) + intMonth
								+ inputText.substring(5, inputText.length()));
						}
					}
				}
			}

			this.select(3, 5);
		}

		if (position > 5 && position < 9) {
			// ����I��
			this.select(6, 8);
			intDay = Integer.parseInt(this.getSelectedText()) + 1;

			if (this.isLeapYear(year)) {
				if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + 0 + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 4 || month == 6) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + 0 + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 9) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}
				}

				if (month == 2) {
					if (day == 29) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + 0 + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 10) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 11) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 12) {
					if (day == 31) {
						if (year == DateUtil.getYear(maxDate)) {
							return false;
						} else {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year + 1) + SLASH + "01" + SLASH
								+ "01");
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year + 1)
								+ SLASH + "01" + SLASH + "01" + inputText.substring(8, inputText.length()));
						}
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}

			} else {
				if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + 0 + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 4 || month == 6) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + 0 + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 9) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}

				if (month == 2) {
					if (day == 28) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + 0 + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + 0 + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(8, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 10) {
					if (day == 31) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}

				}
				if (month == 11) {
					if (day == 30) {
						if (calendarType == TYPE_YMD) setPlainText(adjustYear(year) + SLASH + (month + 1) + SLASH
							+ "01");
						if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year)
							+ SLASH + (month + 1) + SLASH + "01" + inputText.substring(8, inputText.length()));
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}
				}

				if (month == 12) {
					if (day == 31) {
						if (year == DateUtil.getYear(maxDate)) {
							return false;
						} else {
							if (calendarType == TYPE_YMD) setPlainText(adjustYear(year + 1) + SLASH + "01" + SLASH
								+ "01");
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(adjustYear(year + 1)
								+ SLASH + "01" + SLASH + "01" + inputText.substring(8, inputText.length()));
						}
					} else {
						if (intDay < 10) {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + 0 + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + 0 + intDay + inputText.substring(10, inputText.length()));
						} else {
							if (calendarType == TYPE_YMD) setPlainText(inputText.substring(0, 6) + intDay);
							if (calendarType == TYPE_YMDHM || calendarType == TYPE_YMDHMS) setPlainText(inputText
								.substring(0, 6) + intDay + inputText.substring(8, inputText.length()));
						}
					}
				}
			}
			this.select(6, 8);
		}

		// ��
		if (position >= 9 && position <= 11) {
			// ����I��
			this.select(9, 11);

			intHour = Integer.parseInt(this.getSelectedText()) + 1;
			// ���Ԃ̍ő�l��24
			if (intHour == 24) {
				setPlainText(inputText.substring(0, 9) + "00" + inputText.substring(11, inputText.length()));
				this.select(9, 11);
				return false;
			}
			if (intHour < 10) setPlainText(inputText.substring(0, 9) + 0 + intHour
				+ inputText.substring(11, inputText.length()));
			else setPlainText(inputText.substring(0, 9) + intHour + inputText.substring(11, inputText.length()));

			this.select(9, 11);
		}

		// ��
		if (position > 11 && position <= 14) {
			// ����I��
			this.select(12, 14);

			intMinute = Integer.parseInt(this.getSelectedText()) + 1;

			// ���Ԃ̍ő�l��24
			if (intMinute == 60) {

				if (hour == 23) setPlainText(inputText.substring(0, 9) + getHMS("00", "00", second));
				else setPlainText(inputText.substring(0, 9) + getHMS((hour + 1), "00", second));

				this.select(12, 14);

				return false;
			}

			setPlainText(inputText.substring(0, 9) + getHMS(hour, intMinute, second));

			this.select(12, 14);
		}

		// �b
		if (position > 14 && position <= 17) {
			// �b��I��
			this.select(15, 17);
			intSecond = Integer.parseInt(this.getSelectedText()) + 1;

			// ���Ԃ̍ő�l��24
			if (intSecond == 60) {
				if (minute == 59) {
					if (hour == 23) {
						setPlainText(inputText.substring(0, 9) + getHMS("00", "00", "00"));
					} else {
						setPlainText(inputText.substring(0, 9) + getHMS((hour + 1), "00", "00"));
					}

				} else if (minute >= 9 && minute < 59) {
					setPlainText(inputText.substring(0, 9) + getHMS(hour, (minute + 1), "00"));

				} else if (minute < 9) {
					setPlainText(inputText.substring(0, 9) + getHMS(hour, (minute + 1), "00"));
				}

				this.select(15, 17);

				return false;
			}

			setPlainText(inputText.substring(0, 9) + getHMS(hour, minute, intSecond));

			this.select(15, 17);
		}

		return true;
	}

	/**
	 * �N������.YYYY�N����YY�\�L�ɕύX
	 * 
	 * @param intYear �Ώ۔N
	 * @return YY�\�L
	 */
	protected String adjustYear(int intYear) {
		int yyYear = intYear - 2000;
		if (yyYear < 10) {
			return "0" + yyYear;
		} else {
			return String.valueOf(yyYear);
		}
	}

	/**
	 * @see TCalendar#setupSpinText()
	 */
	@Override
	protected String setupSpinText() {

		String inputText = getPlainText();

		// _ �܂܂�邩�ǂ����`�F�b�N
		int unscIndex = inputText.indexOf(UNDERSCORE);
		if (unscIndex != -1) {

			if (unscIndex < 2) {
				setValue(new Date());
				focusAdjustment();

			} else {
				// �r���܂œ��͂���Ă���΁A����𗘗p
				int year = Integer.parseInt("20" + inputText.substring(0, 2));
				int month = 1;
				int day = 1;
				int hour = 0;
				int minute = 0;
				int position = 5;

				if (5 < unscIndex) {
					month = Integer.parseInt(inputText.substring(3, 5));
					position = 8;
				}

				if (8 < unscIndex) {
					day = Integer.parseInt(inputText.substring(6, 8));
					position = 11;
				}

				if (11 < unscIndex) {
					hour = Integer.parseInt(inputText.substring(9, 11));
					position = 14;
				}

				if (14 < unscIndex) {
					minute = Integer.parseInt(inputText.substring(12, 14));
					position = 17;
				}

				setValue(DateUtil.getDate(year, month, day, hour, minute, 0));
				setCaretPosition(position);
			}

			inputText = this.getText();
		}

		return inputText;
	}

	/**
	 * @see TCalendar#getNowDate()
	 */
	@Override
	protected String getNowDate() {
		String text = super.getNowDate();
		if (Util.isNullOrEmpty(text)) {
			return text;
		}

		// �擪2�����폜
		return text.substring(2, text.length());
	}

	/**
	 * @see TCalendar#setText(String)
	 */
	@Override
	public void setText(String text) {
		if (Util.isNullOrEmpty(text)) {
			setPlainText(initText);
			return;
		}

		if (text.indexOf(SLASH) == 2) {
			setPlainText(text);
		} else {
			setPlainText(text.substring(2, text.length()));
		}
	}

	/**
	 * @see TCalendar#setValue(Date)
	 */
	@Override
	public void setValue(Date date) {
		super.setValue(date);

		if (date == null) {
			return;
		}

		// �擪2�����폜
		String text = getPlainText();
		setPlainText(text.substring(2, text.length()));
	}

	/**
	 * @see TCalendar#getValue(String)
	 */
	@Override
	protected Date getValue(String text) {
		return super.getValue("20" + text);
	}

	/**
	 * 2000�N�ȑO�͖���
	 * 
	 * @see TCalendar#setMinimumDate(Date)
	 */
	@Override
	public void setMinimumDate(Date dt) {
		if (dt == null) return;

		Date date2000 = DateUtil.getDate(2000, 1, 1);

		if (dt.before(date2000)) {
			return;
		}

		super.setMinimumDate(dt);
	}

	/**
	 * 2100�N�ȍ~�͖���
	 * 
	 * @see TCalendar#setMaximumDate(Date)
	 */
	@Override
	public void setMaximumDate(Date dt) {
		if (dt == null) return;

		Date date2099 = DateUtil.getDate(2099, 12, 31);

		if (dt.after(date2099)) {
			return;
		}

		super.setMaximumDate(dt);
	}
}
