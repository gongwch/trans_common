package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;

/**
 * Label + TPopupCalendar�����A�C�e��.<br>
 * TPanel�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�. <br>
 * <ol>
 * <li>label TLabel
 * <li>calendar TPopupCalendar
 * </ol>
 */
public class TLabelPopupCalendar extends TPanel implements TInterfaceTabControl, ILabelField {

	/** YYYY�\���^�C�v */
	public static final int TYPE_YYYY = 1;

	/** YY�\���^�C�v */
	public static final int TYPE_YY = 2;

	/** ���x�� */
	protected TLabel label;

	/** �J�����_�[ */
	protected TPopupCalendar calendar;

	/**
	 * �f�t�H���g�R���X�g���N�^�[.<br>
	 * �N�����^�C�v�ō\�z
	 */
	public TLabelPopupCalendar() {
		this(TPopupCalendar.TYPE_YMD);
	}

	/**
	 * �R���X�g���N�^.Label+Calendar<br>
	 * �J�����_�[�^�C�v(TPopupCalendar.TYPE_XXX)���w��.
	 * 
	 * @param calendarType �J�����_�[�^�C�v(TPopupCalendar.TYPE_XXX)
	 */
	public TLabelPopupCalendar(int calendarType) {
		super();
		initComponents(calendarType, TYPE_YYYY);
	}

	/**
	 * �R���X�g���N�^.Label+Calendar<br>
	 * �J�����_�[�^�C�v(TPopupCalendar.TYPE_XXX)���w��.<br>
	 * �N�\���^�C�v�^�C�v(TYPE_YYYY or TYPE_YY)���w��.<br>
	 * 
	 * @param calendarType �J�����_�[�^�C�v(TPopupCalendar.TYPE_XXX)
	 * @param yearType �N�^�C�v(TYPE_YYYY or TYPE_YY)
	 */
	public TLabelPopupCalendar(int calendarType, int yearType) {
		super();
		initComponents(calendarType, yearType);
	}

	/**
	 * �J�����_�[�^�C�v�ݒ�
	 * 
	 * @param calendarType �J�����_�[�^�C�v
	 */
	public void setCalendarType(int calendarType) {
		calendar.setCalendarType(calendarType);
		this.setPanelSize();
	}

	/**
	 * �J�����_�[�^�C�v
	 * 
	 * @return �J�����_�[�^�C�v
	 */
	public int getCalendarType() {
		return this.calendar.getCalendarType();
	}

	/**
	 * ��ʍ\�z
	 * 
	 * @param type �J�����_�[�^�C�v
	 * @param yearType �N�^�C�v
	 */
	protected void initComponents(int type, int yearType) {

		label = createLabel();
		calendar = (yearType == TYPE_YY) ? createYYCalendar(type) : createCalendar(type);

		setLayout(new GridBagLayout());

		label.setLabelFor(calendar);
		label.setText("TLabelCalendar");

		TGuiUtil.setComponentSize(label, new Dimension(110, 20));

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label, new GridBagConstraints());

		// �J�����_�[�̃T�C�Y�͎���
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(calendar, gridBagConstraints);

		setPanelSize();
	}

	/**
	 * ���x���̐���
	 * 
	 * @return ���x��
	 */
	protected TLabel createLabel() {
		return new TLabel();
	}

	/**
	 * �J�����_�[�̐���
	 * 
	 * @param type �J�����_�[�^�C�v
	 * @return �J�����_�[
	 */
	protected TPopupCalendar createCalendar(int type) {
		return new TPopupCalendar(type);
	}

	/**
	 * �J�����_�[�̐���
	 * 
	 * @param type �J�����_�[�^�C�v
	 * @return �J�����_�[
	 */
	protected TPopupCalendar createYYCalendar(int type) {
		return new TYYCalendar(type);
	}

	// item�̕� ******************************************

	/**
	 * label ���̐ݒ�.
	 * 
	 * @param size ��
	 */
	public void setLabelSize(int size) {
		Dimension labelSize = this.label.getPreferredSize();
		labelSize.setSize(size, labelSize.getHeight());
		TGuiUtil.setComponentSize(this.label, labelSize);
		this.setPanelSize();
	}

	/**
	 * label ���̎擾
	 * 
	 * @return ��
	 */
	public int getLabelSize() {
		return (int) this.label.getPreferredSize().getWidth();
	}

	/**
	 * �J�����_�[�͎����ŃT�C�Y��������ׁA����P�[�X���������p���Ȃ�����.<br>
	 * calendar ���̐ݒ�
	 * 
	 * @deprecated ���p���Ȃ�
	 * @param width ��
	 */
	public void setCalendarSize(int width) {
		Dimension calendarSize = this.calendar.getPreferredSize();
		calendarSize.setSize(width, calendarSize.getHeight());

		this.calendar.setPreferredSize(calendarSize);
		this.calendar.setMaximumSize(calendarSize);
		this.setPanelSize();
	}

	/**
	 * calendar ���̎擾
	 * 
	 * @return ��
	 */
	public int getCalendarSize() {
		return (int) this.calendar.getPreferredSize().getWidth();
	}

	/**
	 * panel�T�C�Y���A���񂾃A�C�e���̉����̍��v�ɂ���
	 */
	public void setPanelSize() {
		Dimension size = this.getPreferredSize();
		size.setSize(getLabelSize() + getCalendarSize() + 5, size.getHeight());
		TGuiUtil.setComponentSize(this, size);
	}

	// �A�C�e���̊� *********************************************

	/**
	 * label�̊�
	 * 
	 * @deprecated
	 * @param align ��:0 ����:1 �E:2
	 */
	public void setLabelHAlignment(int align) {
		TGuiUtil.setLabelHorizonalAlignment(this.label, align);
	}

	// property *********************************************

	/**
	 * Label getter
	 * 
	 * @return ���x��
	 */
	public TLabel getLabel() {
		return this.label;
	}

	/**
	 * popupCarendar getter.
	 * 
	 * @return �J�����_�[
	 */
	public TPopupCalendar getCalendar() {
		return this.calendar;
	}

	/**
	 * �u�����N���͋����
	 * 
	 * @param b true:����
	 */
	public void setAllowableBlank(boolean b) {

		this.calendar.setAllowableBlank(b);
	}

	/**
	 * �u�����N���͋����
	 * 
	 * @return true:����
	 */
	public boolean isAllowableBlank() {

		return this.calendar.isAllowableBlank();
	}

	// �����ݒ�. *********************************************

	/**
	 * label�̕\��Text���擾����.
	 * 
	 * @return String
	 */
	public String getLabelText() {
		return this.label.getText();
	}

	/**
	 * �\������Text��label�ɐݒ肷��.
	 * 
	 * @param text �ݒ蕶����
	 */
	public void setLabelText(String text) {
		this.label.setText(text);
	}

	/**
	 * label�̃t�H���g��ݒ肷��.
	 * 
	 * @param font �t�H���g
	 */
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}

	/**
	 * label�̃t�H���g���擾����.
	 * 
	 * @return �t�H���g
	 */
	public Font getLabelFont() {
		return this.label.getFont();
	}

	/**
	 * �J�����_�[�t�B�[���h�ɓ��t��ݒ肷��.
	 * 
	 * @param value �ݒ���t
	 */
	public void setValue(Date value) {
		this.calendar.setValue(value);
	}

	/**
	 * �e�L�X�g�t�B�[���h������t���擾����.
	 * 
	 * @return ���t
	 */
	public Date getValue() {
		return this.calendar.getValue();
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
	 * ���͂���Ă���N���擾
	 * 
	 * @return �N
	 */
	public int getYear() {
		return this.calendar.getYear();
	}

	/**
	 * �e�L�X�g�t�B�[���h�ɓ��͒l���L�邩�ǂ���
	 * 
	 * @return true:���͒l�L��
	 */
	public boolean isEmpty() {
		return this.calendar.isEmpty();
	}

	// interface ���� ***************************************************

	/**
	 * ���x���̕\�������̃��b�Z�[�WID���擾����.
	 * 
	 * @see TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.label.getLangMessageID();
	}

	/**
	 * ���x���̕\�������̃��b�Z�[�WID��ݒ肷��.
	 * 
	 * @see TInterfaceLangMessageID#setLangMessageID(java.lang.String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.label.setLangMessageID(langMessageID);
	}

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h����擾����.
	 * 
	 * @see TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.calendar.getTabControlNo();
	}

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h�ɐݒ肷��.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.calendar.setTabControlNo(no);
	}

	/**
	 * �^�u�ړ��� �s��
	 * 
	 * @return true: �Afalse: �s��
	 */
	public boolean isTabEnabled() {
		return this.calendar.isTabEnabled();
	}

	/**
	 * �^�u�ړ�������
	 * 
	 * @see TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.calendar.setTabEnabled(bool);
	}

	// enable �L���^�������쐧�� ************************************************

	/**
	 * PopupCalendar enabled property.
	 * 
	 * @param bool false:����s��
	 */
	public void setCalendarEnabled(boolean bool) {
		this.calendar.setEnabled(bool);
	}

	/**
	 * PopupCalendar enabled property.
	 * 
	 * @return false:����s��
	 */
	public boolean isCalendarEnabled() {
		return this.calendar.isEnabled();
	}

	/**
	 * Label enabled property.
	 * 
	 * @param bool false:����s��
	 */
	public void setLabelEnabled(boolean bool) {
		this.label.setEnabled(bool);
	}

	/**
	 * Label enabled property.
	 * 
	 * @return false:����s��
	 */
	public boolean isLabelEnabled() {
		return this.label.isEnabled();
	}

	/**
	 * panel enabled
	 * 
	 * @param bool false:����s��
	 */
	public void setEnabled(boolean bool) {
		this.calendar.setEnabled(bool);
		super.setEnabled(bool);
	}

	// editable �ҏW�^�s���쐧�� ************************************************

	/**
	 * calendar editable property.
	 * 
	 * @param bool false:�ҏW�s��
	 */
	public void setEditable(boolean bool) {
		this.calendar.setEditable(bool);
	}

	/**
	 * calendar editable property.
	 * 
	 * @return false:�ҏW�s��
	 */
	public boolean isEditable() {
		return this.calendar.isEditable();
	}

	/**
	 * calendar�Ƀt�H�[�J�X�����킹��. calendar.requestFocus()
	 */
	public void requestTextFocus() {
		this.calendar.requestFocus();
	}

	/**
	 * calendar�Ƀt�H�[�J�X�����킹��. calendar.requestFocus()
	 */
	@Override
	public void requestFocus() {
		this.calendar.requestFocus();
	}

	/**
	 * popup calendar��FocusListener��o�^����.
	 * 
	 * @param l ���X�i
	 */
	public synchronized void addFocusListener(FocusListener l) {
		super.addFocusListener(l);

		this.calendar.addFocusListener(l);
	}

	/**
	 * popup calendar����FocusListener���폜����.
	 * 
	 * @param l ���X�i
	 */
	public synchronized void removeFocusListener(FocusListener l) {
		this.calendar.removeFocusListener(l);

		super.removeFocusListener(l);
	}

	/**
	 * ���͍ŏ����t
	 * 
	 * @param dt �ŏ����t
	 */
	public void setMinimumDate(Date dt) {
		this.calendar.setMinimumDate(dt);
	}

	/**
	 * ���͍ő���t
	 * 
	 * @param dt �ő���t
	 */
	public void setMaximumDate(Date dt) {
		this.calendar.setMaximumDate(dt);
	}

	/**
	 * @see TPopupCalendar#setInputVerifier(InputVerifier)
	 */
	public void setInputVerifier(InputVerifier inputVerifier) {
		this.calendar.setInputVerifier(inputVerifier);
	}

	/**
	 * @return true:�ύX����Ă���
	 * @see TPopupCalendar#isValueChanged()
	 */
	public boolean isValueChanged() {
		return this.calendar.isValueChanged();
	}

	/**
	 * @return true:�ύX����Ă���
	 * @see TPopupCalendar#isValueChanged2()
	 */
	public boolean isValueChanged2() {

		return this.calendar.isValueChanged2();
	}

	/**
	 * ���͒l���N���A����
	 */
	public void clear() {
		this.calendar.setValue(null);
	}
}
