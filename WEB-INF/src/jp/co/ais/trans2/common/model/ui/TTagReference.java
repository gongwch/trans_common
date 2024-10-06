package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * ���匟���R���|�[�l���g
 * 
 * @author AIS
 */
public class TTagReference extends TPanel {

	/** �R���g���[�� */
	protected TTagReferenceController controller;

	/** �{�^�� */
	public TButton btn;

	/** �R�[�h */
	public TTextField code;

	/** �tⳐF */
	public TTextField color;

	/** �tⳃ^�C�g�� */
	public TTextField name;

	/** �^�C�g�� */
	public String title;

	/**
	 * �R���X�g���N�^
	 */
	public TTagReference() {

		this.title = "";

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		controller = createController();
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		btn = new TButton();
		code = new TTextField();
		color = new TTextField();
		name = new TTextField();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	// �����̃��t�@�����X���
	protected void allocateComponents() {

		setLayout(new GridBagLayout());
		setOpaque(false);

		// �{�^��
		TGuiUtil.setComponentSize(btn, new Dimension(80, 20));
		btn.setOpaque(false);

		add(btn);

		// �tⳃR�[�h
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(getCodeLength());
		add(code);
		code.setImeMode(false);

		// �tⳐF
		TGuiUtil.setComponentSize(color, new Dimension(40, 20));
		color.setEditable(false);// �ҏW�E�s����
		add(color);

		// �tⳃ^�C�g��
		TGuiUtil.setComponentSize(name, new Dimension(240, 20));
		name.setMaxLength(80);
		name.setEditable(false);
		add(name);

		resize();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	protected TTagReferenceController createController() {
		return new TTagReferenceController(this);
	}

	/**
	 * @return controller
	 */
	protected TTagReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public TagSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �R���g���[���[������������
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
		btn.setTabControlNo(tabControlNo++);
		code.setTabControlNo(tabControlNo++);
		name.setTabControlNo(tabControlNo++);
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
	 * �\��/���͂��ꂽ�tⳃ^�C�g����Ԃ�.
	 * 
	 * @return �tⳃ^�C�g��
	 */
	@Override
	public String getName() {
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
		if (color.isVisible()) {
			width += (int) color.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
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
		color.setVisible(bln);
		name.setVisible(bln);
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
	 * �tⳐF����ύX����
	 * 
	 * @param width ��
	 */
	public void setColorSize(int width) {
		Dimension size = new Dimension(width, 20);
		color.setSize(size);
		color.setPreferredSize(size);
		color.setMinimumSize(size);
		color.setMaximumSize(size);
		resize();
	}

	/**
	 * �^�C�g������ύX����
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
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}

	/**
	 * �{�^���y�у��x���̒P��ID�ݒ�
	 * 
	 * @param langMessageID ���b�Z�[�WID
	 */
	@Override
	public void setLangMessageID(String langMessageID) {
		btn.setLangMessageID(langMessageID);
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
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */

	public Tag getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param tag
	 */
	public void setEntity(Tag tag) {
		controller.setEntity(tag);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}
