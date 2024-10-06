package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �Q�ƃt�B�[���h�̊��N���X
 * 
 * @author AIS
 */
public abstract class TReference extends TPanel {

	/** �{�^�� */
	public TButton btn;

	/** �R�[�h */
	public TTextField code;

	/** ���� */
	public TTextField name;

	/** ���x�� */
	public TLabel lbl;

	/** �^�C�g�� */
	public String title;

	/** �^�C�v */
	public TYPE type;

	/** ��Џo�͒P�� */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/**
	 * �^�C�v
	 */
	public enum TYPE {
		/** �ʏ�^�C�v */
		BUTTON,
		/** ���x���^�C�v */
		LABEL
	}

	/**
	 * �R���X�g���N�^
	 */
	public TReference() {
		this(TYPE.BUTTON, "");
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TReference(String title) {
		this(TYPE.BUTTON, title);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 */
	public TReference(TYPE type) {
		this(type, "");
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 * @param title �O����ݒ肵�����ꍇ�̃L���v�V����
	 */
	public TReference(TYPE type, String title) {

		this.type = type;

		this.title = title;

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	/**
	 * �R���g���[���̃t�@�N�g��
	 * 
	 * @return �R���g���[��
	 */
	protected abstract TReferenceController getController();

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		btn = new TButton();
		code = new TTextField();
		name = new TTextField();
		lbl = new TLabel();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		GridBagConstraints gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setOpaque(false);

		// �{�^��
		TGuiUtil.setComponentSize(btn, new Dimension(80, 20));
		btn.setOpaque(false);

		// ���x��
		TGuiUtil.setComponentSize(lbl, new Dimension(80, 20));
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setOpaque(false);

		if (this.type == TYPE.BUTTON) {
			add(btn, gc);
			lbl.setVisible(false);
		} else {
			gc.insets = new Insets(0, 0, 0, 5);
			add(lbl, gc);
			btn.setVisible(false);
		}

		// �R�[�h
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(getCodeLength());
		gc.insets = new Insets(0, 0, 0, 0);
		add(code, gc);
		code.setAllowedSpace(false);
		code.setImeMode(false);

		// ����
		name.setEditable(false);
		TGuiUtil.setComponentSize(name, new Dimension(150, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(name, gc);

		resize();
	}

	/**
	 * �R�[�h�Ɨ��̂�����������
	 */
	public void clear() {
		getController().clear();
	}

	/**
	 * �l���ς��������Ԃ�
	 * 
	 * @return �l���ς������
	 */
	public boolean isValueChanged() {
		return getController().isValueChanged();
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		btn.setTabControlNo(tabControlNo);
		code.setTabControlNo(tabControlNo);
		name.setTabControlNo(tabControlNo);
	}

	/**
	 * �R�[�h���͔���
	 * 
	 * @return true:������
	 */
	public boolean isEmpty() {
		return code.isEmpty();
	}

	/**
	 * �R�[�h���͉�/�s����
	 * 
	 * @return true:���͉�
	 */
	public boolean isEditable() {
		return code.isEditable();
	}

	/**
	 * ���͂��ꂽ�R�[�h�l��Ԃ�
	 * 
	 * @return ���͂��ꂽ�R�[�h�l
	 */
	public String getCode() {
		return code.getText();
	}

	/**
	 * �\��/���͂��ꂽ���̂�Ԃ�.
	 * 
	 * @return ����
	 */
	public String getNames() {
		return name.getText();
	}

	/**
	 * ���͂��ꂽ�R�[�h�l���Z�b�g����.
	 * 
	 * @param value ���͂��ꂽ�R�[�h�l
	 */
	public void setCode(String value) {
		code.setText(value);
	}

	/**
	 * �\��/���͂��ꂽ���̂��Z�b�g����.
	 * 
	 * @param names ��
	 */
	public void setNames(String names) {
		name.setText(names);
	}

	/**
	 * @param callBackListener callBackListener��ݒ肷��B
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		getController().addCallBackListener(callBackListener);
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩��Ԃ��܂�
	 * 
	 * @return true ����
	 */
	public boolean isCheckExist() {
		return getController().isCheckExist();
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩�ݒ肵�܂�
	 * 
	 * @param checkExist
	 */
	public void setCheckExist(boolean checkExist) {
		getController().setCheckExist(checkExist);
	}

	/**
	 * �T�C�Y�̍Ĕ��f
	 */
	public void resize() {

		int width = (int) code.getPreferredSize().getWidth();
		if (btn.isVisible()) {
			width += (int) btn.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (lbl.isVisible()) {
			width += (int) lbl.getPreferredSize().getWidth() + 5; // �E�]�����v�Z����ׂ�
		}

		setSize(width, 20);
	}

	/**
	 * �ҏW�E�s����
	 * 
	 * @param edit
	 */
	public void setEditable(boolean edit) {
		getController().setEditable(edit);
	}

	/**
	 * �R�[�h�Ƀt�H�[�J�X�����킹��B
	 */
	public void requestTextFocus() {
		this.code.requestFocus();
	}

	/**
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean bln) {
		btn.setVisible(bln);
		code.setVisible(bln);
		name.setVisible(bln);
		lbl.setVisible(bln);
		super.setVisible(bln);
	}

	/**
	 * �R�[�h�̒�����Ԃ��B�f�t�H���g���ƈقȂ錟���t�B�[���h��<br>
	 * ���Y���\�b�h��Override����B
	 * 
	 * @return �R�[�h��
	 */
	protected int getCodeLength() {
		return TransUtil.DEFAULT_CODE_LENGTH;
	}

	/**
	 * �Œ�{�^���̖����擾���܂�
	 * 
	 * @return String
	 */
	public String getFixedButtonCaption() {
		return getController().fixedButtonCaption;
	}

	/**
	 * �Œ�{�^���̖���ݒ肵�܂�
	 * 
	 * @param fixedButtonCaption
	 */
	public void setFixedButtonCaption(String fixedButtonCaption) {
		getController().fixedButtonCaption = fixedButtonCaption;
	}

	/**
	 * �Œ茟���_�C�A���O���擾���܂�
	 * 
	 * @return String
	 */
	public String getFixedDialogCaption() {
		return getController().fixedDialogCaption;
	}

	/**
	 * �Œ茟���_�C�A���O��ݒ肵�܂�
	 * 
	 * @param fixedDialogCaption
	 */
	public void setFixedDialogCaption(String fixedDialogCaption) {
		getController().fixedDialogCaption = fixedDialogCaption;
	}

	/**
	 * �{�^������ύX����
	 * 
	 * @param width ��
	 */
	public void setButtonSize(int width) {
		Dimension size = new Dimension(width, 20);
		btn.setSize(size);
		btn.setPreferredSize(size);
		btn.setMinimumSize(size);
		btn.setMaximumSize(size);
		resize();
	}

	/**
	 * �{�^������ύX����
	 * 
	 * @param width ��
	 */
	public void setLabelSize(int width) {
		Dimension size = new Dimension(width, 20);
		lbl.setSize(size);
		lbl.setPreferredSize(size);
		lbl.setMinimumSize(size);
		lbl.setMaximumSize(size);
		resize();
	}

	/**
	 * �R�[�h����ύX����
	 * 
	 * @param width ��
	 */
	public void setCodeSize(int width) {
		Dimension size = new Dimension(width, 20);
		code.setSize(size);
		code.setPreferredSize(size);
		code.setMinimumSize(size);
		code.setMaximumSize(size);
		resize();
	}

	/**
	 * ���̕���ύX����
	 * 
	 * @param width ��
	 */
	public void setNameSize(int width) {
		Dimension size = new Dimension(width, 20);
		name.setSize(size);
		name.setPreferredSize(size);
		name.setMinimumSize(size);
		name.setMaximumSize(size);
		resize();
	}

	/**
	 * �_�C�A���O�̃R�[�h�̏����l�\�����邩�̎擾
	 * 
	 * @return showDefaultCode �_�C�A���O�̃R�[�h�̏����l�\�����邩
	 */
	public boolean isShowDefaultCode() {
		return getController().isShowDefaultCode();
	}

	/**
	 * �_�C�A���O�̃R�[�h�̏����l�\�����邩�̐ݒ�
	 * 
	 * @param showDefaultCode �_�C�A���O�̃R�[�h�̏����l�\�����邩
	 */
	public void setShowDefaultCode(boolean showDefaultCode) {
		getController().setShowDefaultCode(showDefaultCode);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.<br>
	 * �\�����X�V����
	 */
	public void refleshAndShowEntity() {
		// override �p
	}

	/**
	 * �{�^���y�у��x���̒P��ID�ݒ�
	 * 
	 * @param langMessageID ���b�Z�[�WID
	 */
	@Override
	public void setLangMessageID(String langMessageID) {
		btn.setLangMessageID(langMessageID);
		lbl.setLangMessageID(langMessageID);
	}

	/**
	 * �X�[�p�[�p�l���̒P��ID�ݒ�
	 * 
	 * @param langMessageID ���b�Z�[�WID
	 */
	public void setPanelLangMessageID(String langMessageID) {
		super.setLangMessageID(langMessageID);
	}

	/**
	 * ��Џo�͒P�ʂ̎擾
	 * 
	 * @return companyOrgUnit ��Џo�͒P��
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * ��Џo�͒P�ʂ̐ݒ�
	 * 
	 * @param companyOrgUnit ��Џo�͒P��
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;
	}

}
