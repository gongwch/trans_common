package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * TPanel�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ���������item. <br>
 * �qitem.
 * <ol>
 * <li>label TLabel
 * <li>combo TComboBox
 * </ol>
 */
public class TLabelComboBox extends TPanel implements TInterfaceTabControl {

	/** ���x�� */
	protected TLabel label;

	/** �R���{�{�b�N�X */
	protected TComboBox combo;

	/** �A�C�e���̃T�C�Y */
	private Dimension labelSize = null;

	/** �R���{�{�b�N�X�̃T�C�Y */
	private Dimension comboSize = null;

	/**
	 * �R���X�g���N�^
	 */
	public TLabelComboBox() {
		super();
		initComponents();
	}

	/**
	 * �����\�z
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		label = createLabel();
		combo = createComboBox();

		setLayout(new GridBagLayout());

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setLabelFor(combo);
		label.setText("TLabelComboBox");

		TGuiUtil.setComponentSize(label, new Dimension(120, 20));
		add(label, new GridBagConstraints());

		combo.setModel(new DefaultComboBoxModel());
		TGuiUtil.setComponentSize(combo, new Dimension(160, 20));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(combo, gridBagConstraints);

		labelSize = this.label.getPreferredSize();
		comboSize = this.combo.getPreferredSize();

		setPanelSize();
	}

	/**
	 * ���x���𐶐�
	 * 
	 * @return ���x��
	 */
	protected TLabel createLabel() {
		return new TLabel();
	}

	/**
	 * �R���{�{�b�N�X�𐶐�
	 * 
	 * @return �R���{�{�b�N�X
	 */
	protected TComboBox createComboBox() {
		return new TComboBox();
	}

	// getter. **************************************************

	/**
	 * label getter.
	 * 
	 * @return label
	 */
	public TLabel getLabel() {
		return this.label;
	}

	/**
	 * combo getter.
	 * 
	 * @return comboBox
	 */
	public TComboBox getComboBox() {
		return this.combo;
	}

	// �A�C�e���̕� ***************************************************

	/**
	 * panel�T�C�Y���A���񂾃A�C�e���̉����̍��v�ɂ���
	 */
	private void setPanelSize() {
		TGuiUtil.setComponentSize(this, new Dimension(
			(int) (this.labelSize.getWidth() + this.comboSize.getWidth()) + 5, this.getPreferredSize().height));
	}

	/**
	 * label ���̐ݒ�.
	 * 
	 * @param size ��
	 */
	public void setLabelSize(int size) {
		this.labelSize.setSize(size, this.labelSize.getHeight());
		TGuiUtil.setComponentSize(this.label, this.labelSize);
		this.setPanelSize();
	}

	/**
	 * button ���̎擾
	 * 
	 * @return ��
	 */
	public int getLabelSize() {
		return (int) this.labelSize.getWidth();
	}

	/**
	 * combobox ���̐ݒ�
	 * 
	 * @param size ��
	 */
	public void setComboSize(int size) {
		this.comboSize.setSize(size, this.comboSize.getHeight());
		TGuiUtil.setComponentSize(this.combo, this.comboSize);
		this.setPanelSize();
	}

	/**
	 * combobox ���̎擾
	 * 
	 * @return ��
	 */
	public int getComboSize() {
		return (int) this.comboSize.getWidth();
	}

	// �A�C�e���̊� **********************************************

	/**
	 * label�̊�
	 * 
	 * @deprecated
	 * @param align 0:�� 1:���� 2:�E
	 */
	public void setLabelHAlignment(int align) {
		TGuiUtil.setLabelHorizonalAlignment(this.label, align);
	}

	// �����ݒ� ***************************************************

	/**
	 * ���x�������ݒ�
	 * 
	 * @param text ���x������
	 */
	public void setLabelText(String text) {
		this.label.setText(text);
	}

	/**
	 * ���x�������擾
	 * 
	 * @return ���x������
	 */
	public String getLabelText() {
		return this.label.getText();
	}

	/**
	 * ���x���t�H���g�ݒ�
	 * 
	 * @param font �t�H���g
	 */
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}

	/**
	 * ���x���t�H���g�擾
	 * 
	 * @return �t�H���g
	 */
	public Font getLabelFont() {
		return this.label.getFont();
	}

	// interface ���� ********************************************

	/**
	 * ���x���̕\�������̃��b�Z�[�WID���擾����.
	 * 
	 * @see TInterfaceLangMessageID#getLangMessageID()
	 */
	@Override
	public String getLangMessageID() {

		return this.label.getLangMessageID();
	}

	/**
	 * ���x���̕\�������̃��b�Z�[�WID��ݒ肷��.
	 * 
	 * @see TInterfaceLangMessageID#setLangMessageID(java.lang.String)
	 */
	@Override
	public void setLangMessageID(String langMessageID) {

		this.label.setLangMessageID(langMessageID);
	}

	/**
	 * @see TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.combo.getTabControlNo();
	}

	/**
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.combo.setTabControlNo(no);
	}

	/**
	 * 
	 */
	public boolean isTabEnabled() {
		return this.combo.isTabEnabled();
	}

	/**
	 * @see TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.combo.setTabEnabled(bool);
	}

	// enable �L���^�������쐧�� ************************************************

	/**
	 * ComboBox enabled property
	 * 
	 * @param bool false:����s��
	 */
	public void setComboEnabled(boolean bool) {
		this.combo.setEnabled(bool);
	}

	/**
	 * ComboBox enabled property
	 * 
	 * @return false:����s��
	 */
	public boolean isComboEnabled() {
		return this.combo.isEnabled();
	}

	/**
	 * Label enabled property
	 * 
	 * @param bool false:����s��
	 */
	public void setLabelEnabled(boolean bool) {
		this.label.setEnabled(bool);
	}

	/**
	 * Label enabled property
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
	@Override
	public void setEnabled(boolean bool) {
		// this.label.setEnabled(bool);
		this.combo.setEnabled(bool);
		super.setEnabled(bool);
	}

	// editable �ҏW�^�s���쐧�� ************************************************

	/**
	 * ComboBox editable property.
	 * 
	 * @param bool false:�ҏW�s��
	 */
	public void setEditable(boolean bool) {
		this.combo.setEditable(bool);
	}

	/**
	 * ComboBox editable property.
	 * 
	 * @return false:�ҏW�s��
	 */
	public boolean isEditable() {
		return this.combo.isEditable();
	}

	/**
	 * combo�Ƀt�H�[�J�X�����킹��. field.requestFocus()
	 */
	public void requestTextFocus() {
		this.combo.requestFocus();
	}

	/**
	 * add focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void addFocusListener(FocusListener listener) {

		this.combo.getEditor().getEditorComponent().addFocusListener(listener);
	}

	/**
	 * remove focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void removeFocusListener(FocusListener listener) {

		this.combo.getEditor().getEditorComponent().removeFocusListener(listener);
	}

	/**
	 * input verifier setter.
	 * 
	 * @param iv �ݒ�InputVerifier
	 */
	@Override
	public void setInputVerifier(InputVerifier iv) {

		this.combo.setInputVerifier(iv);
	}

	/**
	 * input verifier getter.
	 * 
	 * @return �擾InputVerifier
	 */
	@Override
	public InputVerifier getInputVerifier() {

		return this.combo.getInputVerifier();
	}

	/**
	 * �R���{�{�b�N�X�̃C���f�b�N�X�ݒ�
	 * 
	 * @param index �C���f�b�N�X
	 */
	public void setSelectedIndex(int index) {
		this.combo.setSelectedIndex(index);
	}

	/**
	 * �l���w�肵�đI����ύX����
	 * 
	 * @param value �l
	 */
	public void setSelectedItemValue(Object value) {
		this.combo.setSelectedItemValue(value);
	}

	/**
	 * �R���{�{�b�N�X�̃C���f�b�N�X�擾
	 * 
	 * @return �C���f�b�N�X
	 */
	public int getSelectedIndex() {
		return this.combo.getSelectedIndex();
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return �l
	 */
	public Object getSelectedItemValue() {
		return this.combo.getSelectedItemValue();
	}

	/**
	 * �l(�L�[)�E�\��������ǉ�
	 * 
	 * @param text �e�L�X�g
	 * @param value �l
	 */
	public void addTextValueItem(Object value, String text) {
		this.combo.addTextValueItem(value, text);
	}
}
