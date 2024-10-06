package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.*;

/**
 * TPanel�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ���������item. <br>
 * �qitem.
 * <ol>
 * <li>label TLabel
 * <li>field TTextField
 * </ol>
 */
public class TLabelField extends TPanel implements TInterfaceLangMessageID, TInterfaceTabControl, ILabelField {

	/** ���͂̊T�v��\������label. */
	protected TLabel label;

	/** �e�L�X�g����item. */
	protected TTextField field;

	/** label�̃T�C�Y. */
	protected Dimension labelSize = null;

	/** field�̃T�C�Y. */
	protected Dimension fieldSize = null;

	/**
	 * Constructor. <br>
	 * �qitem������������.
	 */
	public TLabelField() {
		super();
		initComponents();

		initControl();
	}

	/**
	 * �A�C�e���̃T�C�Y�ۑ�
	 */
	protected void initControl() {
		field.setEditable(true);
		field.setValue("");
	}

	/**
	 * �qitem�̏�����.
	 */
	protected void initComponents() {

		label = new TLabel();
		field = createTextField();

		setLayout(new GridBagLayout());

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setLabelFor(field);
		label.setText("TLabelField");

		TGuiUtil.setComponentSize(label, new Dimension(100, 20));
		add(label, new GridBagConstraints());

		TGuiUtil.setComponentSize(field, new Dimension(140, 20));

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(field, gridBagConstraints);

		labelSize = this.label.getPreferredSize();
		fieldSize = this.field.getPreferredSize();

		setPanelSize();
	}

	/**
	 * �e�L�X�g�t�B�[���h����
	 * 
	 * @return �e�L�X�g�t�B�[���h
	 */
	protected TTextField createTextField() {
		return new TTextField();
	}

	// �A�C�e���̕� ********************************************

	/**
	 * panel�T�C�Y���A���񂾃A�C�e���̉����̍��v�ɂ���.
	 */
	protected void setPanelSize() {
		TGuiUtil.setComponentSize(this, new Dimension(
			(int) (this.labelSize.getWidth() + this.fieldSize.getWidth()) + 5, this.getPreferredSize().height));
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
	 * label ���̎擾
	 * 
	 * @return ��
	 */
	public int getLabelSize() {
		return (int) this.labelSize.getWidth();
	}

	/**
	 * field ���̐ݒ�
	 * 
	 * @param size ��
	 */
	public void setFieldSize(int size) {
		this.fieldSize.setSize(size, this.fieldSize.getHeight());
		TGuiUtil.setComponentSize(this.field, this.fieldSize);
		this.setPanelSize();
	}

	/**
	 * field ���̎擾
	 * 
	 * @return ��
	 */
	public int getFieldSize() {
		return (int) this.fieldSize.getWidth();
	}

	// �A�C�e���̊� ***********************

	/**
	 * J�R���|�[�l���g��setHorizontalAlignment(int)���g������
	 * 
	 * @deprecated
	 * @param align 0:�� 1:���� 2:�E
	 */
	public void setLabelHAlignment(int align) {
		TGuiUtil.setLabelHorizonalAlignment(this.label, align);
	}

	/**
	 * J�R���|�[�l���g��setHorizontalAlignment(int)���g������
	 * 
	 * @deprecated
	 * @param align 0:�� 1:���� 2:�E
	 */
	public void setFieldHAlignment(int align) {
		TGuiUtil.setTextFieldHorizonalAlignment(this.field, align);
	}

	// item getter. ********************************************

	/**
	 * �p�l�����TLabel label�̃C���X�^���X��Ԃ�.
	 * 
	 * @return label
	 */
	public TLabel getLabel() {
		return this.label;
	}

	/**
	 * �p�l�����TTextField field�C���X�^���X��Ԃ�.
	 * 
	 * @return field
	 */
	public TTextField getField() {
		return this.field;
	}

	// �qitem�̕����ݒ�. **********************************************

	/**
	 * �\������Text��label�ɐݒ肷��.
	 * 
	 * @param text �ݒ蕶����
	 */
	public void setLabelText(String text) {
		this.label.setText(text);
	}

	/**
	 * setText()�Őݒ肳�ꂽ�������Ԃ�.
	 * 
	 * @return ���x������
	 */
	public String getLabelText() {
		return label.getText();
	}

	/**
	 * field�ɕ������ݒ肷��.
	 * 
	 * @param value �ݒ蕶����
	 */
	public void setValue(String value) {
		this.field.setText(value);
	}

	/**
	 * field���當������擾����.
	 * 
	 * @return field������
	 */
	public String getValue() {
		return this.field.getText();
	}

	/**
	 * �e�L�X�g�t�B�[���h�ɓ��͒l���L�邩�ǂ���
	 * 
	 * @return true:���͒l�L��
	 */
	public boolean isEmpty() {
		return this.field.isEmpty();
	}

	/**
	 * label��Font��ݒ肷��.
	 * 
	 * @param font �ݒ�t�H���g
	 */
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}

	/**
	 * label��Font���擾����.
	 * 
	 * @return �t�H���g
	 */
	public Font getLabelFont() {
		return this.label.getFont();
	}

	// interface ���� **************************************************

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h����擾����.
	 */
	public int getTabControlNo() {
		return this.field.getTabControlNo();
	}

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h�ɐݒ肷��.
	 */
	public void setTabControlNo(int no) {
		this.field.setTabControlNo(no);
	}

	/**
	 * �^�u�ړ��E�s�擾.
	 * 
	 * @return true: �Afalse: �s��
	 */
	public boolean isTabEnabled() {
		return this.field.isTabEnabled();
	}

	/**
	 * �^�u�ړ��E�s�ݒ�.
	 * 
	 * @param bool true: �Afalse: �s��
	 */
	public void setTabEnabled(boolean bool) {
		this.field.setTabEnabled(bool);
	}

	/**
	 * ���x���̕\�������̃��b�Z�[�WID���擾����.
	 */
	public String getLangMessageID() {
		return this.label.getLangMessageID();
	}

	/**
	 * ���x���̕\�������̃��b�Z�[�WID��ݒ肷��.
	 * 
	 * @param langMessageID
	 */
	public void setLangMessageID(String langMessageID) {
		this.label.setLangMessageID(langMessageID);
	}

	// �ő包. ****************************************************

	/**
	 * �ő包��
	 * 
	 * @return �ő包��
	 */
	public int getMaxLength() {
		return this.field.getMaxLength();
	}

	/**
	 * �ő包��
	 * 
	 * @param maxLength �ő包��
	 */
	public void setMaxLength(int maxLength) {
		this.field.setMaxLength(maxLength);
	}

	// InputMethod. ****************************************************

	/**
	 * IME���[�h�t���O
	 * 
	 * @param flag true: IME���[�h
	 */
	public void setImeMode(boolean flag) {
		this.field.setImeMode(flag);
	}

	/**
	 * IME���[�h�t���O
	 * 
	 * @return true: IME���[�h
	 */
	public boolean isImeMode() {
		return this.field.isImeMode();
	}

	// enable �L���^�������쐧�� ************************************************

	/**
	 * field enabled property.
	 * 
	 * @param bool false:����s��
	 */
	public void setFieldEnabled(boolean bool) {
		this.field.setEnabled(bool);
	}

	/**
	 * field enabled property.
	 * 
	 * @return bool
	 */
	public boolean isFieldEnabled() {
		return this.field.isEnabled();
	}

	/**
	 * label enabled property.
	 * 
	 * @param bool
	 */
	public void setLabelEnabled(boolean bool) {
		this.label.setEnabled(bool);
	}

	/**
	 * label enabled property.
	 * 
	 * @return label enabled property
	 */
	public boolean isLabelEnabled() {
		return this.label.isEnabled();
	}

	/**
	 * panel enabled property.
	 * 
	 * @param bool false:����s��
	 */
	public void setEnabled(boolean bool) {
		// this.label.setEnabled(bool);
		this.field.setEnabled(bool);
		super.setEnabled(bool);
	}

	// editable �ҏW�^�s���쐧�� ************************************************

	/**
	 * field editable property.
	 * 
	 * @param bool false:�ҏW�s��
	 */
	public void setEditable(boolean bool) {
		this.field.setEditable(bool);
	}

	/**
	 * field editable property.
	 * 
	 * @return false:�ҏW�s��
	 */
	public boolean isEditable() {
		return this.field.isEditable();
	}

	/**
	 * field�Ƀt�H�[�J�X�����킹��.
	 */
	public void requestTextFocus() {
		this.field.requestFocus();
	}

	/**
	 * field�Ƀt�H�[�J�X�����킹��.
	 */
	@Override
	public void requestFocus() {
		this.field.requestFocus();
	}

	/**
	 * ���̓`�F�b�N�N���X��ݒ肷��
	 * 
	 * @param iv InputVerifier
	 */
	@Override
	public void setInputVerifier(InputVerifier iv) {

		this.field.setInputVerifier(iv);
	}

	/**
	 * ���̓`�F�b�N�N���X���擾����
	 * 
	 * @return InputVerifier
	 */
	@Override
	public InputVerifier getInputVerifier() {

		return this.field.getInputVerifier();
	}

	/**
	 * �ύX���ꂽ���`�F�b�N����
	 * 
	 * @return true:�ύX����Ă���
	 */
	public boolean isValueChanged() {

		return this.field.isValueChanged();
	}

	/**
	 * ���O�l���N���A����
	 */
	public void clearOldText() {

		this.field.clearOldText();
	}

	/**
	 * �֑�����(���͂����Ȃ�����)�̐ݒ�
	 * 
	 * @param words �֑��������X�g
	 * @see TTextField#setProhibitionWords(String[])
	 */
	public void setProhibitionWords(String... words) {
		this.field.setProhibitionWords(words);
	}

	/**
	 * ���͉\�����𐳋K�\���Ŏw�肷��.
	 * 
	 * @param regex ���K�\������
	 * @see TTextField#setRegex(String)
	 */
	public void setRegex(String regex) {
		this.field.setRegex(regex);
	}

	/**
	 * �S�p�E���p�̃t���O��ێ����邩�ǂ���.<br>
	 * false�ɐݒ肷��ƁA�����t�H�[�J�X�C�����AIME:OFF�ɂȂ�.
	 * 
	 * @param bol true:�ێ����Ȃ��B false:�ێ�����B
	 * @see TTextField#setIsChangeCharacterSubsets(boolean)
	 */
	public void setIsChangeCharacterSubsets(boolean bol) {
		this.field.setIsChangeCharacterSubsets(bol);
	}

	/**
	 * space���͋���
	 * 
	 * @param b true:����
	 */
	public void setAllowedSpace(boolean b) {
		this.field.setAllowedSpace(b);
	}

	/**
	 * ���͒l���N���A����
	 */
	public void clear() {
		this.field.clear();
	}

	/**
	 * �R���|�[�l���g�̃T�C�Y���Đݒ肷��
	 */
	public void reWidth() {
		setSize(this.field.getWidth() + this.label.getWidth() + 5, this.getHeight());
	}
}