package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.text.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * ���l���� TextField.
 */
public class TNumericField extends TTextField {

	/** �����_��|�C���g */
	protected static final String DECIMAL_POINT = ".";

	/** �w��t�H�[�}�b�g */
	protected DecimalFormat formatter = null;

	/** �}�C�i�X�l����͂��ꂽ�ہA�ԕ����ɕύX���邩�ǂ���. */
	protected boolean isChangeRedOfMinus = false;

	/** �w�菬���_���� */
	protected int digit;

	/** �f�t�H���g�����F */
	protected Color defaultForeground;

	/** �X�v���b�h�̃t�H���g���g�� */
	protected boolean useTableFont = false;

	/** �ő�l */
	protected BigDecimal maxValue = null;

	/** �ŏ��l */
	protected BigDecimal minValue = null;

	/** �t�b�^�[���l�t�B�[���h */
	protected boolean tableFooterField = false;

	/**
	 * Constructor.
	 */
	public TNumericField() {
		super();

		defaultForeground = TUIManager.getTextDefaultForeground();

		TUIManager.setLogicalFont(this);

		super.setImeMode(false);
		super.setHorizontalAlignment(SwingConstants.RIGHT);

		this.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				String text = TNumericField.this.getText();
				TNumericField.this.setText(text);

				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				if (TGuiUtil.isActive(TNumericField.this)) {
					String text = TNumericField.this.getText();

					TNumericField.this.setTextNonFormat(text.replace(",", ""));
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
					if (tableCellEditor || !TNumericField.this.isFocusOwner() || !TNumericField.this.isEditable()) {
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
				if (!TNumericField.this.isFocusOwner() || !TNumericField.this.isEditable()) {
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
	 * �R���X�g���N�^.
	 * 
	 * @param format
	 */
	public TNumericField(String format) {
		this();

		setNumericFormat(format);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param i �C���f�b�N�X�ԍ�
	 */
	public TNumericField(int i) {
		this();

		setRowIndex(i);
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
	 * ���͐���
	 * 
	 * @return PlainDocument
	 */
	@Override
	protected PlainDocument createPlainDocument() {
		return new TNumericPlainDocument(this);
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
		return ((TNumericPlainDocument) document).getMaxLength();
	}

	/**
	 * �ő包���ݒ�.<br>
	 * setMaxLength(17,4)�� #,###,###,###,##0.0000
	 * 
	 * @param maxLength �ő包��(�������A���������킹��)
	 * @param decimalPoint �����_����
	 */
	public void setMaxLength(int maxLength, int decimalPoint) {
		setMaxLength(maxLength);
		setNumericFormat("#,##0");
		setDecimalPoint(decimalPoint);
	}

	/**
	 * �ő包��
	 * 
	 * @param maxLength �ő包��
	 */
	@Override
	public void setMaxLength(int maxLength) {
		((TNumericPlainDocument) document).setMaxLength(maxLength);

		if (maxLength == 1) {
			setNumericFormat("#");
		}
	}

	/**
	 * ���l�^�̃t�H�[�}�b�g.<br>
	 * �w��t�H�[�}�b�g�ɏ����_���܂܂�Ă���ꍇ�A�������ő包����<br>
	 * MaxLength���珬���_���������������̂Ɏ����I�Ɋ��蓖�Ă���.<br>
	 * MaxLength = ���������� + �����_����
	 * 
	 * @param format �t�H�[�}�b�g
	 */
	public void setNumericFormat(String format) {
		// �󕶎����w�肳�ꂽ��ݒ肵�Ȃ�
		if (Util.isNullOrEmpty(format)) {
			return;
		}

		this.formatter = new DecimalFormat(format);

		// �ő召���_����
		this.digit = formatter.getMaximumFractionDigits();
		((TNumericPlainDocument) document).setDecimalPoint(digit);

		if (!isEmpty()) {
			this.setNumber(this.getBigDecimal());
		}
	}

	/**
	 * �����_������0�t�H�[�}�b�g(ex:#.0000)�ŕύX����.
	 * 
	 * @param digit �����_����
	 */
	public void setDecimalPoint(int digit) {
		this.digit = digit;

		if (this.formatter == null) {
			((TNumericPlainDocument) document).setDecimalPoint(digit);
			return;
		}

		String format = this.formatter.toPattern();
		String[] splitFormat = StringUtil.split(format, ".");

		StringBuilder buff = new StringBuilder();

		if (digit > 0) {
			buff.append(".");
			for (int i = 1; i <= digit; i++) {
				buff.append("0");
			}
		}

		this.setNumericFormat(splitFormat[0] + buff.toString());
	}

	/**
	 * �����_����
	 * 
	 * @return �����_����
	 */
	public int getDecimalPoint() {
		return this.digit;
	}

	/**
	 * �}�C�i�X�l����͂��ꂽ�ہA�ԕ����ɕύX���邩�ǂ���.
	 * 
	 * @param isChangeRedOfMinus true:�ύX����
	 */
	public void setChangeRedOfMinus(boolean isChangeRedOfMinus) {
		this.isChangeRedOfMinus = isChangeRedOfMinus;
	}

	/**
	 * �}�C�i�X�l����͂��ꂽ�ہA�ԕ����ɕύX���邩�ǂ���.
	 * 
	 * @return true:�ύX����
	 */
	public boolean isChangeRedOfMinus() {
		return this.isChangeRedOfMinus;
	}

	/**
	 * ���l�^�̃t�H�[�}�b�g
	 * 
	 * @return ���l�^�̃t�H�[�}�b�g
	 */
	public String getNumericFormat() {
		if (formatter == null) {
			return "";
		}
		return formatter.toPattern();
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
	 * �����̂݃��[�h�ݒ�
	 * 
	 * @param isPositive true�̏ꍇ�A�����̂�
	 */
	public void setPositiveOnly(boolean isPositive) {
		((TNumericPlainDocument) document).setPositiveOnly(isPositive);
	}

	/**
	 * �����̂݃��[�h
	 * 
	 * @return true�̏ꍇ�A�����̂�
	 */
	public boolean isPositiveOnly() {
		return ((TNumericPlainDocument) document).isPositive();
	}

	/**
	 * �t�H�[�}�b�g�\�����琔�l�\���֏C�����ăe�L�X�g�l���擾����
	 * 
	 * @return ���l�\��������
	 */
	@Override
	public String getText() {
		return super.getInputText().replace(",", "");
	}

	/**
	 * int�^�ŕ\�����l���擾����
	 * 
	 * @return ���l
	 */
	public int getInt() {
		String txt = getText();

		if (Util.isNullOrEmpty(txt)) {
			return 0;
		}

		return Integer.parseInt(txt);
	}

	/**
	 * double�^�ŕ\�����l���擾����<br>
	 * double�ł͂Ȃ�BigDecimal��.
	 * 
	 * @deprecated double�g����
	 * @return ���l
	 */
	public double getDouble() {
		String txt = getText();

		if (Util.isNullOrEmpty(txt)) {
			return 0d;
		}

		return Double.parseDouble(txt);
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @return ���l
	 */
	public BigDecimal getBigDecimal() {
		String txt = getText();

		if (Util.isNullOrEmpty(txt) || txt.trim().equals("-")) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(txt);
	}

	/**
	 * �e�L�X�g�ݒ�. �t�H�[�}�b�g�ϊ�������
	 * 
	 * @param text �e�L�X�g
	 */
	@Override
	public void setText(String text) {
		try {
			// �󔒂܂��͋L���݂̂̏ꍇ�̓t�H�[�}�b�g�����Ƀe�L�X�g���N���A
			if (text == null || "".equals(text.replace("-", "").replace(".", ""))) {
				super.setText("");
				return;
			}

			if (formatter != null) {
				try {
					BigDecimal dec = new BigDecimal(text.replaceAll(",", ""));

					String ftext = this.formatter.format(dec);
					// -0�\����h��
					if ("-0".equals(ftext)) {
						ftext = "0";
					}

					super.setText(ftext);

				} catch (NumberFormatException ex) {
					throw new TRuntimeException(ex);
				}
			} else {
				super.setText(text);
			}

		} finally {

			if (this.isChangeRedOfMinus) {
				// �l���}�C�i�X���ɐԎ��ϊ�
				super.setForeground(this.isMinus() ? Color.RED : TUIManager.getTextDefaultForeground());
			}
		}
	}

	/**
	 * Double�l�ݒ�. �t�H�[�}�b�g�ϊ�������<br>
	 * double�ł͂Ȃ�BigDecimal��.
	 * 
	 * @deprecated double�g����
	 * @param value ���l
	 */
	public void setDouble(Double value) {
		if (value == -0d) {
			// -0�\����h��
			value = 0d;
		}

		this.setNumber(value);
	}

	/**
	 * Number�l�ݒ�. �t�H�[�}�b�g�ϊ�������
	 * 
	 * @param value ���l
	 */
	public void setNumber(Number value) {
		try {
			if (value == null) {
				super.setText("");
				return;
			}

			if (formatter != null) {
				super.setText(this.formatter.format(value));

			} else {
				super.setText(String.valueOf(value));
			}

		} finally {
			if (this.isChangeRedOfMinus) {
				// �l���}�C�i�X���ɐԎ��ϊ�
				super.setForeground(this.isMinus() ? Color.RED : defaultForeground);
			}
		}
	}

	/**
	 * @see com.klg.jclass.field.JCTextField#setForeground(java.awt.Color)
	 */
	@Override
	public void setForeground(Color color) {
		super.setForeground(color);

		this.defaultForeground = color;
	}

	/**
	 * �t�H�[�}�b�g���ݒ肳��Ă��邩�ǂ���
	 * 
	 * @return true : �t�H�}�b�g�L�� false : �t�H�[�}�b�g�Ȃ�
	 */
	public boolean isFormatterExist() {
		return this.formatter != null;
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
	 * ���l���}�C�i�X���ǂ���
	 * 
	 * @return true:�}�C�i�X
	 */
	protected boolean isMinus() {
		return getBigDecimal().compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * UP��Down�C�x���g
	 * 
	 * @param actionFlg ����t���O
	 * @return true:��������
	 */
	protected boolean upOrDownAction(int actionFlg) {
		// �u.�v�����c������ԂŃX�s��������Ɠ����ŃG���[�������
		if (this.getText().equals(".")) {
			return false;
		}
		// �������t���O
		boolean flgPositive = true;
		// �e�X�g�̒l
		BigDecimal value = this.getBigDecimal();

		// �e�X�g�̉��ψȑO�̒l
		BigDecimal oldValue = this.getBigDecimal();

		// JTextField���J�����g�J�[�\���̈ʒu�F
		int position = this.getCaretPosition();

		this.setTextNonFormat(getBigDecimal().toString());

		// �����_�ʒu
		int pointIndex = getPointIndex();

		if (position <= pointIndex) {
			position = pointIndex;
			flgPositive = true;
		} else {
			position = getText().length();
			flgPositive = false;
		}

		// ������
		int digit_ = getDigit();

		// UP�C�x���g
		if (actionFlg == KeyEvent.VK_UP) {

			// �����敪���v�Z
			if (pointIndex == -1 || position <= pointIndex) {

				BigDecimal positive = DecimalUtil.separateDecimal(value)[0];

				if (value.compareTo(BigDecimal.ZERO) < 0 && positive.compareTo(BigDecimal.ZERO) == 0) {
					value = value.abs().add(BigDecimal.ONE);

				} else {
					value = value.add(BigDecimal.ONE);
				}

			} else {
				value = value.add(new BigDecimal(DecimalUtil.calculatePower(10, -(digit_))));
			}

		} else if (actionFlg == KeyEvent.VK_DOWN) {
			BigDecimal positive = DecimalUtil.separateDecimal(value)[0];

			// �����敪���v�Z
			if (pointIndex == -1 || position <= pointIndex) {

				if (((TNumericPlainDocument) document).isPositive() && positive.compareTo(BigDecimal.ZERO) == 0) {
					value = oldValue;
					return true;
				}

				if (positive.compareTo(BigDecimal.ZERO) <= 0) {
					value = value.abs().add(BigDecimal.ONE).multiply(new BigDecimal(-1));

				} else {
					value = value.subtract(BigDecimal.ONE);
				}

			} else {

				if (((TNumericPlainDocument) document).isPositive() && value.compareTo(BigDecimal.ZERO) == 0) {
					value = oldValue;
					return true;
				}

				value = value.subtract(new BigDecimal(DecimalUtil.calculatePower(10, -(digit_))));
			}
		}

		// Number�l�ݒ�
		this.setTextNonFormat(String.valueOf(DecimalUtil.roundNum(value, digit_)));

		// �u�����N�ꍇ�A���ψȑO�̒l�ݒ�
		if (this.getValue().equals("")) {
			this.setTextNonFormat(String.valueOf(DecimalUtil.roundNum(oldValue, digit_)));
		}

		// �����_�ʒu
		pointIndex = getPointIndex();

		position = flgPositive ? pointIndex : getText().length();

		// �t�H�[�J�X�ʒu�������Â���
		this.setCaretPosition(position);

		return true;
	}

	/**
	 * �����_�ʒu�擾
	 * 
	 * @return �����_�ʒu
	 */
	protected int getPointIndex() {
		// �����_�ʒu
		return getBigDecimal().toString().indexOf(DECIMAL_POINT);
	}

	/**
	 * ���������擾
	 * 
	 * @return ��������
	 */
	protected int getDigit() {
		// �����_�ʒu
		int pointIndex = getPointIndex();
		// �e�L�X�g�l�����|�����_�ʒu�|1
		return (pointIndex != -1) ? getBigDecimal().toString().length() - pointIndex - 1 : 0;
	}

	/**
	 * �e�L�X�g�t�B�[���h�̕��������̂܂ܕԂ�.
	 * 
	 * @return Plain�e�L�X�g
	 */
	public String getPlainText() {
		return getBigDecimal().toString();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TAmountRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TAmountEditor(this, tbl);
	}

	/**
	 * �X�v���b�h�̃t�H���g���g���̎擾
	 * 
	 * @return useTableFont �X�v���b�h�̃t�H���g���g��
	 */
	public boolean isUseTableFont() {
		return useTableFont;
	}

	/**
	 * �X�v���b�h�̃t�H���g���g���̐ݒ�
	 * 
	 * @param useTableFont �X�v���b�h�̃t�H���g���g��
	 */
	public void setUseTableFont(boolean useTableFont) {
		this.useTableFont = useTableFont;
	}

	/**
	 * ���͒l���[�����ǂ���.
	 * 
	 * @return true:�[��
	 */
	public boolean isZero() {
		return DecimalUtil.isZero(getBigDecimal());
	}

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	@Override
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.RIGHT;
	}

	/**
	 * �ő�l�̎擾
	 * 
	 * @return maxValue �ő�l
	 */
	public BigDecimal getMaxValue() {
		return maxValue;
	}

	/**
	 * �ő�l�̐ݒ�
	 * 
	 * @param maxValue �ő�l
	 */
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;

		if (getDocument() != null) {
			((TNumericPlainDocument) getDocument()).maxValue = maxValue;
		}
	}

	/**
	 * �ŏ��l�̎擾
	 * 
	 * @return minValue �ŏ��l
	 */
	public BigDecimal getMinValue() {
		return minValue;
	}

	/**
	 * �ŏ��l�̐ݒ�
	 * 
	 * @param minValue �ŏ��l
	 */
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;

		if (getDocument() != null) {
			((TNumericPlainDocument) getDocument()).minValue = minValue;
		}
	}

	/**
	 * �t�b�^�[���l�t�B�[���h�̎擾
	 * 
	 * @return tableFooterField �t�b�^�[���l�t�B�[���h
	 */
	public boolean isTableFooterField() {
		return tableFooterField;
	}

	/**
	 * �t�b�^�[���l�t�B�[���h�̐ݒ�
	 * 
	 * @param tableFooterField �t�b�^�[���l�t�B�[���h
	 */
	public void setTableFooterField(boolean tableFooterField) {
		this.tableFooterField = tableFooterField;
	}

}