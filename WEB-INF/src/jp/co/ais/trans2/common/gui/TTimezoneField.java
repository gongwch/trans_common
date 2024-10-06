package jp.co.ais.trans2.common.gui;

import java.awt.event.*;
import java.math.*;
import java.text.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * �����t�B�[���h
 * 
 * @author AIS
 */
public class TTimezoneField extends TTextField {

	/** �����_�ȉ��P�� */
	public static final BigDecimal DEFAULT_UNIT = new BigDecimal("0.15");

	/** �f�t�H���g�l */
	public static final String DEFAULT_VALUE = "+00:00";

	/** �����_��|�C���g */
	protected static final String DECIMAL_POINT = ":";

	/** �w�菬���_���� */
	protected static final int DIGITS = 2;

	/** �ő�l */
	protected BigDecimal MAX_VALUE = new BigDecimal("23.45");

	/** �ŏ��l */
	protected BigDecimal MIN_VALUE = new BigDecimal("-23.45");

	/** �����_�ȉ��P�� */
	protected BigDecimal unit = DEFAULT_UNIT;

	/** �w��t�H�[�}�b�g */
	protected DecimalFormat formatter = null;

	/** �X�v���b�h�̃t�H���g���g�� */
	protected boolean useTableFont = false;

	/** 15���P�� */
	protected boolean minus15Only = true;

	/**
	 * Constructor.
	 */
	public TTimezoneField() {
		super();

		// ������
		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param i �C���f�b�N�X�ԍ�
	 */
	public TTimezoneField(int i) {
		this();

		setRowIndex(i);
	}

	/**
	 * ������
	 */
	public void init() {

		TUIManager.setLogicalFont(this);

		super.setImeMode(false);
		super.setHorizontalAlignment(SwingConstants.CENTER);
		this.formatter = new DecimalFormat("00.00");
		this.formatter.setPositivePrefix("+");
		this.formatter.setNegativePrefix("-");

		this.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				String text = getText();
				setText(text);
				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				if (isActive()) {
					String text = TTimezoneField.this.getText();

					TTimezoneField.this.setTextNonFormat(text.replace(",", ""));
					selectAll();
				}
			}
		});

		// numeric key listener
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					// ���L�[��Up��Down������ �t���b�p�[����������
					if (tableCellEditor || !TTimezoneField.this.isFocusOwner() || !TTimezoneField.this.isEditable()) {
						return;
					}

					if (!upOrDownAction(e.getKeyCode())) {
						e.consume();
					}
				}
			}
		});

		// mouse wheel listener
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!TTimezoneField.this.isFocusOwner() || !TTimezoneField.this.isEditable()) {
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
					if (!upOrDownAction(isDown ? KeyEvent.VK_DOWN : KeyEvent.VK_UP)) {
						e.consume();
						break;
					}
				}
			}
		});
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TTextField#createPlainDocument()
	 */
	@Override
	protected TStringPlainDocument createPlainDocument() {
		TStringPlainDocument doc = (TStringPlainDocument) super.createPlainDocument();

		doc.setRegex("[\\+\\-\\.0-9:]");

		return doc;
	}

	/**
	 * �e�L�X�g�⏕�@�\
	 */
	@Override
	protected void initTextHelper() {
		// UNDO/REDO �͓��삪�������̂Ŗ���

		// �J�b�g�A���h�y�[�X�g
		addMouseListener(TGuiUtil.createCnPListener(this));
	}

	/**
	 * @return true:������������
	 */
	protected boolean isActive() {
		return TGuiUtil.isActive(TTimezoneField.this);
	}

	/**
	 * space���͋���
	 * 
	 * @return true:����
	 */
	@Override
	public boolean isAllowedSpace() {
		return false;
	}

	/**
	 * space���͋���
	 * 
	 * @param b true:����
	 */
	@Override
	public void setAllowedSpace(boolean b) {
		// �X�y�[�X�͓��͕s��
	}

	/**
	 * �ő包��
	 * 
	 * @return �ő包��
	 */
	@Override
	public int getMaxLength() {
		return 6;
	}

	/**
	 * ���IME���[�h�ł�
	 * 
	 * @deprecated ���IME���[�h�ł�
	 */
	@Override
	public void setImeMode(boolean flag) {
		super.setImeMode(false);
	}

	/**
	 * �t�H�[�}�b�g�\�����琔�l�\���֏C�����ăe�L�X�g�l���擾����
	 * 
	 * @return ���l�\��������
	 */
	@Override
	public String getText() {
		return super.getInputText();
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @return ���l(����:10�i�@)
	 */
	public BigDecimal getNumber() {
		return getNumber(getText());
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @return ���l(60�i�@)
	 */
	protected BigDecimal getNativeBigDecimal() {
		String txt = getText();
		return getNativeBigDecimal(txt);
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @param tz
	 * @return ���l(����:10�i�@)
	 */
	public static BigDecimal getNumber(String tz) {
		return getNumber(tz, DEFAULT_UNIT);
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @param tz
	 * @param unit �ŏ��P�ʎw��
	 * @return ���l(����:10�i�@)
	 */
	public static BigDecimal getNumber(String tz, BigDecimal unit) {
		BigDecimal value = getNativeBigDecimal(tz);
		BigDecimal num = remainder(value, unit);
		return DecimalUtil.convert60to10(num);
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @param txt
	 * @return ���l(60�i�@)
	 */
	public static BigDecimal getNativeBigDecimal(String txt) {

		if (Util.isNullOrEmpty(txt) || txt.trim().equals("-") || txt.trim().equals("+")) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(txt.replaceAll(":", "."));
	}

	/**
	 * �e�L�X�g�ݒ�. �t�H�[�}�b�g�ϊ�������
	 * 
	 * @param text �e�L�X�g
	 */
	@Override
	public void setText(String text) {
		// �󔒂܂��͋L���݂̂̏ꍇ�̓t�H�[�}�b�g�����Ƀe�L�X�g���N���A
		if (text == null || "".equals(text.replace("-", "").replace("+", "").replace(".", ""))) {
			super.setText(DEFAULT_VALUE);
			return;
		}

		String ftext = getFormattedString(text);
		super.setText(ftext);
	}

	/**
	 * Number�l�ݒ�. �t�H�[�}�b�g�ϊ�������<br>
	 * 10�i�@��60�i�@�ϊ��܂�
	 * 
	 * @param value ���l(10�i�@)
	 */
	public void setNumber(Number value) {
		if (value == null) {
			super.setText(DEFAULT_VALUE);
			return;
		}

		// 10�i�@��60�i�@
		BigDecimal num = DecimalUtil.convert10to60(value);
		super.setText(getFormattedString(num));
	}

	/**
	 * @param text
	 * @return �t�H�[�}�b�g������
	 */
	protected String getFormattedString(String text) {
		try {

			String sign = text.length() >= 1 ? text.substring(0, 1) : "+";

			BigDecimal dec = new BigDecimal(text.replaceAll("[\\+\\-,]", "").replaceAll(":", "."));
			if (sign.equals("-")) {
				dec = dec.negate(); // �������]
			}
			return getFormattedString(dec);

		} catch (NumberFormatException ex) {
			// �G���[�̏ꍇ�A�����l��Ԃ�(�����l�Ƃ���)
			return DEFAULT_VALUE;
		}
	}

	/**
	 * @param num
	 * @return �t�H�[�}�b�g������
	 */
	public String getFormattedString(Number num) {

		if (num instanceof BigDecimal) {
			// BigDecimal�̏ꍇ�A60�]�v�����O��
			num = remainder((BigDecimal) num);

			BigDecimal chk = (BigDecimal) num;

			if (chk.compareTo(MAX_VALUE) == 1 //
				|| chk.compareTo(MIN_VALUE) == -1) {
				return DEFAULT_VALUE;
			}
		}

		String ftext = this.formatter.format(num);
		return ftext.replace(".", ":");
	}

	/**
	 * @param value
	 * @return �]�v�O�����l
	 */
	public BigDecimal remainder(BigDecimal value) {
		return remainder(value, unit);
	}

	/**
	 * @param value
	 * @param unit
	 * @return �]�v�O�����l
	 */
	public static BigDecimal remainder(BigDecimal value, BigDecimal unit) {
		BigDecimal num[] = DecimalUtil.separateDecimal(value);
		BigDecimal b = value.subtract(num[1].remainder(unit));

		switch (b.signum()) {
			case 1:
				return calculateUpper(b, unit);
			case -1:
				return calculateLower(b, unit);
		}

		return b;
	}

	/**
	 * �t�H�[�}�b�g�ϊ������̃e�L�X�g�ݒ�
	 * 
	 * @param text �e�L�X�g
	 */
	protected void setTextNonFormat(String text) {
		super.setText(text);
	}

	/**
	 * UP��Down�C�x���g
	 * 
	 * @param actionFlg ����t���O
	 * @return true:��������
	 */
	protected boolean upOrDownAction(int actionFlg) {
		// �u.�v�����c������ԂŃX�s��������Ɠ����ŃG���[�������
		if (this.getText().equals(".") || this.getText().equals(":")) {
			return false;
		}
		// �������t���O
		boolean flgPositive = true;
		// �e�X�g�̒l
		BigDecimal value = this.getNativeBigDecimal();

		// �e�X�g�̉��ψȑO�̒l
		BigDecimal oldValue = this.getNativeBigDecimal();

		// JTextField���J�����g�J�[�\���̈ʒu�F
		int position = this.getCaretPosition();

		this.setTextNonFormat(getFormattedString(value));

		// �����_�ʒu
		int pointIndex = DIGITS;

		if (position <= pointIndex) {
			position = pointIndex;
			flgPositive = true;
		} else {
			position = getText().length();
			flgPositive = false;
		}

		// ������
		int digit_ = DIGITS;

		// UP�C�x���g
		if (actionFlg == KeyEvent.VK_UP) {
			if (pointIndex == -1 || position <= pointIndex) {
				// ���������v�Z

				value = value.add(BigDecimal.ONE);

			} else {
				// ���������v�Z

				value = value.add(unit);

				// �v�Z���ʂ̂U�O�i����
				value = calculateUpper(value);
			}

		} else if (actionFlg == KeyEvent.VK_DOWN) {
			if (pointIndex == -1 || position <= pointIndex) {
				// ���������v�Z

				value = value.subtract(BigDecimal.ONE);

			} else {
				// ���������v�Z

				value = value.subtract(unit);

				// �v�Z���ʂ̂U�O�i����
				value = calculateLower(value);
			}
		}

		BigDecimal num = DecimalUtil.roundNum(value, digit_);

		if (num.compareTo(MAX_VALUE) == 1) {
			num = MAX_VALUE;
		} else if (num.compareTo(MIN_VALUE) == -1) {
			num = MIN_VALUE;
		}

		// Number�l�ݒ�
		this.setTextNonFormat(getFormattedString(num));

		// �u�����N�ꍇ�A���ψȑO�̒l�ݒ�
		if (this.getValue().equals("")) {
			BigDecimal oldNum = DecimalUtil.roundNum(oldValue, digit_);
			this.setTextNonFormat(getFormattedString(oldNum));
		}

		// �����_�ʒu
		position = flgPositive ? DIGITS : getText().length();

		// �t�H�[�J�X�ʒu�������Â���
		this.setCaretPosition(position);

		return true;
	}

	/**
	 * @param value
	 * @return �U�O�i����
	 */
	protected BigDecimal calculateLower(BigDecimal value) {
		return calculateLower(value, unit);
	}

	/**
	 * @param value
	 * @param unit
	 * @return �U�O�i����
	 */
	public static BigDecimal calculateLower(BigDecimal value, BigDecimal unit) {
		// �v�Z���ʂ̂U�O�i����
		BigDecimal num[] = DecimalUtil.separateDecimal(value);
		if (num[1].abs().compareTo(DecimalUtil.NUM_60) >= 0) {
			if (num[1].signum() == -1) {
				// �}�C�i�X
				num[0] = num[0].subtract(BigDecimal.ONE);
				value = num[0];
			} else {
				// �v���X
				num[0] = num[0].add(DecimalUtil.NUM_60).subtract(unit);
				value = num[0];
			}
		}
		return value;
	}

	/**
	 * @param value
	 * @return �U�O�i����
	 */
	protected BigDecimal calculateUpper(BigDecimal value) {
		return calculateUpper(value, unit);
	}

	/**
	 * @param value
	 * @param unit
	 * @return �U�O�i����
	 */
	public static BigDecimal calculateUpper(BigDecimal value, BigDecimal unit) {
		// �v�Z���ʂ̂U�O�i����
		BigDecimal num[] = DecimalUtil.separateDecimal(value);
		if (num[1].abs().compareTo(DecimalUtil.NUM_60) >= 0) {

			if (num[1].signum() == -1) {
				// �}�C�i�X
				num[0] = num[0].subtract(DecimalUtil.NUM_60).add(unit);
				value = num[0];
			} else {
				// �v���X
				num[0] = num[0].add(BigDecimal.ONE);
				value = num[0];
			}
		}
		return value;
	}

	/**
	 * �e�L�X�g�t�B�[���h�̕��������̂܂ܕԂ�.
	 * 
	 * @return Plain�e�L�X�g
	 */
	public String getPlainText() {
		return getText();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TTimezoneRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TTimezoneEditor(this, tbl);
	}

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	@Override
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * 15���P�ʂ̎擾
	 * 
	 * @return minus15Only 15���P��
	 */
	public boolean isMinus15Only() {
		return minus15Only;
	}

	/**
	 * 15���P�ʂ̐ݒ�
	 * 
	 * @param minus15Only 15���P��
	 */
	public void setMinus15Only(boolean minus15Only) {
		this.minus15Only = minus15Only;

		if (this.minus15Only) {
			unit = new BigDecimal("0.15");
			MAX_VALUE = new BigDecimal("23.45");
			MIN_VALUE = new BigDecimal("-23.45");
		} else {
			unit = new BigDecimal("0.01");
			MAX_VALUE = new BigDecimal("23.59");
			MIN_VALUE = new BigDecimal("-23.59");
		}
	}

}
