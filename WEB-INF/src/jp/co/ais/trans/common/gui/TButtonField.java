package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TPanel�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ���������item. <br>
 * �qitem.
 * <ol>
 * <li>button TButton
 * <li>field TTextField
 * <li>notice TTextField
 * </ol>
 */
public class TButtonField extends TPanel implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** �V���A��UID */
	private static final long serialVersionUID = 6680537868864513388L;

	/** �{�^�� */
	protected TButton button;

	/** �e�L�X�g */
	protected TTextField field;

	/** ���x���e�L�X�g */
	protected TTextField notice;

	/** �{�^���T�C�Y */
	private Dimension buttonSize = null;

	/** �e�L�X�g�T�C�Y */
	private Dimension fieldSize = null;

	/** ���x���e�L�X�g�T�C�Y */
	private Dimension noticeSize = null;

	/** Notice�t�B�[���h�Ń_�C�A���O���J���� */
	private boolean isOpenDialogNotice = false;

	/** �t�H�[�J�X��Notice�t�B�[���h�ɂ��������ǂ��� */
	private boolean isFocusNotice = false;

	/**
	 * Constructor.
	 */
	public TButtonField() {
		super();

		initComponents();
		initControl();
	}

	/**
	 * init
	 */
	protected void initControl() {
		field.setEditable(true);
		field.setValue("");
		notice.setEditable(false);
		notice.setValue("");
	}

	/**
	 * �R���|�[�l���g�\�z
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		button = new TButton();
		field = new TTextField();
		notice = new TTextField();

		setLayout(new GridBagLayout());
		setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON_FIELD, new Dimension(300, 20)));

		button.setText("TButtonField");
		button.setActionCommand("button");
		button.setMargin(new Insets(2, 2, 2, 2));
		TGuiUtil.setComponentSize(button, new Dimension(100, 20));

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				fireButtonAction(evt);
			}
		});
		add(button, new GridBagConstraints());

		TGuiUtil.setComponentSize(field, new Dimension(140, 20));

		field.setAllowedSpace(false);
		field.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(field, gridBagConstraints);

		TGuiUtil.setComponentSize(notice, new Dimension(180, 20));

		add(notice, new GridBagConstraints());

		buttonSize = this.button.getPreferredSize();
		fieldSize = this.field.getPreferredSize();
		noticeSize = this.notice.getPreferredSize();

		setPanelSize();

		// �L�[�p���X�i
		this.getField().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				paneKeyReleased(e);
			}
		});

		// �t�H�[�J�X�p���X�i
		this.getField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent e) {
				focusLostActionPerformed(e);
			}
		});

		// �L�[�p���X�i
		this.getNotice().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				paneKeyReleasedNotice(e);
			}
		});
	}

	/**
	 * �ēx�A�C�x���g���N���AlostFocus���ɁA�ړ����enabled���ς��ꍇ�ɑΉ�����B
	 * 
	 * @param e �C�x���g
	 */
	protected void focusLostActionPerformed(FocusEvent e) {
		// �t�H�[�J�X�󂯎�����R���|�[�l���g
		Component comp = e.getOppositeComponent();

		if (comp != null
			&& (TAppletMainCtrl.MenuTreeNode.class.equals(comp.getClass()) || "jp.co.ais.trans.common.ui.TAppletMainCtrl$2"
				.equals(comp.getClass().getName()))) {
			// ���j���[�������狭���I�ɒl���N���A����B�������l���ύX����Ă���Ƃ��̂�
			// ���s���l�m��h�~�̂���
			if (this.isValueChanged()) {
				this.field.setValue("");
				this.notice.setValue("");
			}
		}
	}

	/**
	 * �X�y�[�X�L�[�̏���. <br>
	 * �X�y�[�X�L�[�����Ń{�^�����N���b�N�������̂Ɠ����̏������s���B
	 * 
	 * @param e �L�[�C�x���g
	 */
	protected void paneKeyReleased(KeyEvent e) {
		if (TGuiUtil.isActive(field) && e.getKeyCode() == KeyEvent.VK_SPACE) {
			// �X�y�[�X�������ɕ����񂪏����Ȃ��悤�ɑS�I�����Ȃ�
			field.select(0, 0);
			// �t�H�[�J�X�̓t�B�[���h�ɂ���
			isFocusNotice = false;
			// �Y���{�^�����N���b�N
			button.doClick();
		}
	}

	/**
	 * �X�y�[�X�L�[�̏���. <br>
	 * �X�y�[�X�L�[�����Ń{�^�����N���b�N�������̂Ɠ����̏������s���B
	 * 
	 * @param e �L�[�C�x���g
	 */
	protected void paneKeyReleasedNotice(KeyEvent e) {
		if (TGuiUtil.isActive(notice) && isOpenDialogNotice && e.getKeyCode() == KeyEvent.VK_SPACE) {
			// �X�y�[�X�������ɕ����񂪏����Ȃ��悤�ɑS�I�����Ȃ�
			notice.select(0, 0);
			// �t�H�[�J�X��Notice�t�B�[���h�ɂ���
			isFocusNotice = true;
			// �Y���{�^�����N���b�N
			button.doClick();
		}
	}

	// for MouseListener ********************************

	/**
	 * �p�l���Ƀt�H�[�J�X�����킹��.
	 */
	public void panelRequestFoucus() {
		this.requestFocus(); // �L�[�{�[�h�t�H�[�J�X�̐ݒ�
	}

	// �A�C�e���̕� ******************************************

	/**
	 * panel�T�C�Y���A���񂾃A�C�e���̉����̍��v�ɂ���
	 */
	private void setPanelSize() {
		TGuiUtil.setComponentSize(this, new Dimension(
			(int) (this.buttonSize.getWidth() + this.fieldSize.getWidth() + this.noticeSize.getWidth()) + 5, this
				.getPreferredSize().height));
	}

	/**
	 * button ���̐ݒ�.
	 * 
	 * @param size ��
	 */
	public void setButtonSize(int size) {
		this.buttonSize.setSize(size, this.buttonSize.getHeight());
		TGuiUtil.setComponentSize(this.button, this.buttonSize);
		this.setPanelSize();
	}

	/**
	 * button ���̎擾
	 * 
	 * @return ��
	 */
	public int getButtonSize() {
		return (int) this.buttonSize.getWidth();
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

	/**
	 * notice ���̐ݒ�
	 * 
	 * @param size ��
	 */
	public void setNoticeSize(int size) {
		this.noticeSize.setSize(size, this.noticeSize.getHeight());
		TGuiUtil.setComponentSize(this.notice, this.noticeSize);
		this.setPanelSize();
	}

	/**
	 * notice ���̎擾
	 * 
	 * @return ��
	 */
	public int getNoticeSize() {
		return (int) this.noticeSize.getWidth();
	}

	// �A�C�e���̊� ******************************************

	/**
	 * button �̊�
	 * 
	 * @deprecated
	 * @param align 0:�� 1:���� 2:�E
	 */
	public void setButtonHAlignment(int align) {
		TGuiUtil.setButtonHorizonalAlignment(this.button, align);
	}

	/**
	 * field �̊�
	 * 
	 * @deprecated
	 * @param align 0:�� 1:���� 2:�E
	 */
	public void setFieldHAlignment(int align) {
		TGuiUtil.setTextFieldHorizonalAlignment(this.field, align);
	}

	/**
	 * notice �̊�
	 * 
	 * @deprecated
	 * @param align 0:�� 1:���� 2:�E
	 */
	public void setNoticeHAlignment(int align) {
		TGuiUtil.setTextFieldHorizonalAlignment(this.notice, align);
	}

	// getter. *****************************************

	/**
	 * �p�l�����TButton�C���X�^���X��Ԃ�.
	 * 
	 * @return button
	 */
	public TButton getButton() {
		return this.button;
	}

	/**
	 * �p�l�����TTextField field�C���X�^���X��Ԃ�.
	 * 
	 * @return field
	 */
	public TTextField getField() {
		return this.field;
	}

	/**
	 * �p�l�����TTextField notice�C���X�^���X��Ԃ�.
	 * 
	 * @return notice
	 */
	public TTextField getNotice() {
		return this.notice;
	}

	// �\������ ***************************************************

	/**
	 * �{�^���\��������ݒ肷��.
	 * 
	 * @param text
	 */
	public void setButtonText(String text) {
		this.button.setText(text);
	}

	/**
	 * �{�^���\���������擾����.
	 * 
	 * @return �{�^���\������
	 */
	public String getButtonText() {
		return this.button.getText();
	}

	/**
	 * �{�^���e�L�X�g�Ō�ɎO�_���[�_�[(...)��t�^���邩�ǂ���
	 * 
	 * @return true:�t�^����
	 */
	public boolean isButtonAddThreeDots() {
		return this.button.isAddThreeDots();
	}

	/**
	 * �{�^���e�L�X�g�Ō�ɎO�_���[�_�[(...)��t�^���邩�ǂ���
	 * 
	 * @param isAddThreeDots true:�t�^����
	 */
	public void setButtonAddThreeDots(boolean isAddThreeDots) {
		this.button.setAddThreeDots(isAddThreeDots);
	}

	/**
	 * field �e�L�X�g�t�B�[���h�ɕ������ݒ肷��.
	 * 
	 * @param value �ݒ蕶����
	 */
	public void setValue(String value) {
		this.field.setValue(value);
	}

	/**
	 * field �e�L�X�g�t�B�[���h���當������擾����.
	 * 
	 * @return ���͕�����
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
		return Util.isNullOrEmpty(getValue());
	}

	/**
	 * @return OldValue
	 * @deprecated �g�p���Ȃ�(isValueChanged()�𗘗p)
	 */
	public String getOldValue() {
		return this.field.getOldText();
	}

	/**
	 * �ύX�O�e�L�X�g���N���A
	 */
	public void clearOldText() {
		this.field.clearOldText();
	}

	/**
	 * notice �e�L�X�g�t�B�[���h���當������擾����.
	 * 
	 * @return notice������
	 */
	public String getNoticeValue() {
		return this.notice.getText();
	}

	/**
	 * notice �e�L�X�g�t�B�[���h�ɕ������ݒ肷��.
	 * 
	 * @param value �ݒ蕶����
	 */
	public void setNoticeValue(String value) {
		boolean en = notice.isEnabled();
		boolean ed = notice.isEditable();

		notice.setEnabled(true);
		notice.setEditable(true);

		this.notice.setText(value);

		notice.setEditable(ed);
		notice.setEnabled(en);
	}

	// interface ���� ************************************************

	/**
	 * ���x���̕\�������̃��b�Z�[�WID��ݒ肷��.
	 * 
	 * @see TInterfaceLangMessageID#setLangMessageID(java.lang.String)
	 */
	public void setLangMessageID(String messageID) {
		this.button.setLangMessageID(messageID);
	}

	/**
	 * ���x���̕\�������̃��b�Z�[�WID���擾����.
	 * 
	 * @see TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.button.getLangMessageID();
	}

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h�ɐݒ肷��.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.button.setTabControlNo(no);
		this.field.setTabControlNo(no);
		this.notice.setTabControlNo(no);
	}

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h����擾����.
	 * 
	 * @see TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		int no = this.button.getTabControlNo();
		if (no != -1) {
			return no;
		} else {
			return this.field.getTabControlNo();

		}
	}

	/**
	 * �^�u�ړ�������.
	 * 
	 * @see TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.button.setTabEnabled(bool);
		this.field.setTabEnabled(bool);
	}

	/**
	 * �^�u�ړ�������.
	 */
	public boolean isTabEnabled() {
		return this.button.isTabEnabled() || this.field.isTabEnabled();
	}

	// �ő包. ******************************************************

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

	/**
	 * �ő包��
	 * 
	 * @return �ő包��
	 */
	public int getNoticeMaxLength() {
		return this.notice.getMaxLength();
	}

	/**
	 * �ő包��
	 * 
	 * @param maxLength �ő包��
	 */
	public void setNoticeMaxLength(int maxLength) {
		this.notice.setMaxLength(maxLength);
	}

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

	/**
	 * �{�^���̃A�N�V�������X�i�[�o�^
	 * 
	 * @param listener �A�N�V�������X�i�[
	 */
	public void addButtonActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}

	/**
	 * �{�^���̃A�N�V�����̍ۂ�callback method(for override).
	 * 
	 * @param evt
	 */
	public void fireButtonAction(@SuppressWarnings("unused")
	ActionEvent evt) {
		// ��������
	}

	/**
	 * �e�L�X�g�t�B�[���h�̃t�H�[�J�X���X�i�[�o�^
	 * 
	 * @param listener �t�H�[�J�X���X�i�[
	 */
	public void addTextFocusListener(FocusListener listener) {
		field.addFocusListener(listener);
	}

	// enable �L���^�������쐧�� *************************************************

	/**
	 * Button enabled property
	 * 
	 * @param bool false:����s��
	 */
	public void setButtonEnabled(boolean bool) {
		this.button.setEnabled(bool);
	}

	/**
	 * Button enabled property
	 * 
	 * @return false:����s��
	 */
	public boolean isButtonEnabled() {
		return this.button.isEnabled();
	}

	/**
	 * TextField enabled property
	 * 
	 * @param bool false:����s��
	 */
	public void setFieldEnabled(boolean bool) {
		this.field.setEnabled(bool);
	}

	/**
	 * TextField enabled property
	 * 
	 * @return false:����s��
	 */
	public boolean isFieldEnabled() {
		return this.field.isEnabled();
	}

	/**
	 * notice enabled property
	 * 
	 * @param bool false:����s��
	 */
	public void setNoticeEnabled(boolean bool) {
		this.notice.setEnabled(bool);
	}

	/**
	 * notice enabled property
	 * 
	 * @return false:����s��
	 */
	public boolean isNoticeEnabled() {
		return this.notice.isEnabled();
	}

	/**
	 * panel enabled property
	 * 
	 * @param bool false:����s��
	 */
	public void setEnabled(boolean bool) {
		this.button.setEnabled(bool);
		this.field.setEnabled(bool);
		this.notice.setEnabled(bool);
		super.setEnabled(bool);
	}

	/**
	 * �{�^���ƃR�[�h�t�B�[���h��Enabled����
	 * 
	 * @param bool false:����s��
	 */
	public void setEnabledButtonField(boolean bool) {
		this.button.setEnabled(bool);
		this.field.setEnabled(bool);
	}

	/**
	 * �{�^���ƃR�[�h�t�B�[���h��Enabled����
	 * 
	 * @return false:����s��
	 */
	public boolean isEnabledButtonField() {
		return this.button.isEnabled() && this.field.isEnabled();
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
	 * notice editable property.
	 * 
	 * @param bool false:�ҏW�s��
	 */
	public void setNoticeEditable(boolean bool) {
		this.notice.setEditable(bool);
	}

	/**
	 * notice editable property.
	 * 
	 * @return false:�ҏW�s��
	 */
	public boolean isNoticeEditable() {
		return this.notice.isEditable();
	}

	/**
	 * �{�^����Enabled�A�t�B�[���h��Editable�𐧌�.<br>
	 * �܂��A�l�̃N���A���s��. notice�͒l�̃N���A�̂�
	 * 
	 * @param isControll true:����\
	 */
	public void setEditMode(boolean isControll) {
		this.button.setEnabled(isControll);
		this.field.setEditable(isControll);

		if (!isControll) {
			this.clear();
		}
	}

	/**
	 * �e�L�X�g�ւ̃t�H�[�J�X�ړ�
	 */
	public void requestTextFocus() {
		this.field.requestFocus();
	}

	// notice-field visible ************************************************

	/**
	 * notice visible property.
	 * 
	 * @param bool false:�s��
	 */
	public void setNoticeVisible(boolean bool) {
		this.notice.setVisible(bool);
	}

	/**
	 * notice visible property.
	 * 
	 * @return false:�s��
	 */
	public boolean isNoticeVisible() {
		return this.notice.isVisible();
	}

	/**
	 * button visible property.
	 * 
	 * @param bool false:�s��
	 */
	public void setButtonVisible(boolean bool) {
		this.button.setVisible(bool);
	}

	/**
	 * button visible property.
	 * 
	 * @return false:�s��
	 */
	public boolean isButtonVisible() {
		return this.button.isVisible();
	}

	/**
	 * �R�[�h�ȊO��s���ɂ���.
	 * 
	 * @param bool true:�R�[�h�ȊO�͕s��
	 */
	public void setCodeOnlyVisible(boolean bool) {
		setNoticeVisible(!bool);
		setButtonVisible(!bool);
	}

	/**
	 * button visible property.
	 * 
	 * @return true:�R�[�h�ȊO�͕s��
	 */
	public boolean isCodeOnlyVisible() {
		return !isNoticeVisible() && !isButtonVisible();
	}

	/**
	 * ���̓`�F�b�N�N���X��ݒ肷��
	 * 
	 * @param iv Verifier
	 */
	@Override
	public void setInputVerifier(InputVerifier iv) {

		this.field.setInputVerifier(iv);
	}

	/**
	 * ���̓`�F�b�N�N���X���擾����
	 * 
	 * @return Verifier
	 */
	@Override
	public InputVerifier getInputVerifier() {

		return this.field.getInputVerifier();
	}

	/**
	 * �ύX���ꂽ���`�F�b�N����
	 * 
	 * @return true:�ύX���ꂽ
	 */
	public boolean isValueChanged() {

		return this.field.isValueChanged();
	}

	/**
	 * �ύX���ꂽ���`�F�b�N����.null�A�u�����N���Ώ�
	 * 
	 * @return true:�ύX���ꂽ
	 */
	public boolean isValueChanged2() {

		return this.field.isValueChanged2();
	}

	/**
	 * �ύX���ꂽ���`�F�b�N����(Notice�p)
	 * 
	 * @return true:�ύX���ꂽ
	 */
	public boolean isNoticeValueChanged() {

		return this.notice.isValueChanged();
	}

	/**
	 * Notice�t�B�[���h�Ń_�C�A���O���J����
	 * 
	 * @param bol
	 */
	public void setIsOpenDialogNotice(boolean bol) {
		isOpenDialogNotice = bol;
	}

	/**
	 * Notice�t�B�[���h�Ń_�C�A���O���J����
	 * 
	 * @return true:�J��
	 */
	public boolean isOpenDialogNotice() {
		return this.isOpenDialogNotice;
	}

	/**
	 * Notice�Ƀt�H�[�J�X����������
	 * 
	 * @return true:Notice�Ƀt�H�[�J�X����
	 */
	public boolean isFocusNotice() {
		return this.isFocusNotice;
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
	 * �l���N���A����.�������N���A
	 */
	public void clear() {
		this.setValue("");
		this.field.clearOldText();
		this.setNoticeValue("");
		this.notice.clearOldText();
	}
}
