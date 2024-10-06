package jp.co.ais.trans.common.gui;

import javax.swing.*;

/**
 * JLabel�ɁA���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�����Label.
 */
public class TLabel extends JLabel implements TInterfaceLangMessageID {

	/** �V���A��UID */
	private static final long serialVersionUID = 0L;

	/** �P��ID */
	private String langMessageID = null;

	/** ��]�̗L�� true:90�x��] */
	protected boolean isRotate = false;

	/**
	 * �R���X�g���N�^.
	 */
	public TLabel() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param langMessageID
	 */
	public TLabel(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param langMessageID
	 * @param isRotate
	 */
	public TLabel(String langMessageID, boolean isRotate) {
		this.langMessageID = langMessageID;
		setRotate(isRotate);
	}

	/*
	 * (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/*
	 * (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * ���x������]���邩�ۂ�
	 * 
	 * @return true:��]����
	 */
	public boolean isRotate() {
		return isRotate;
	}

	/**
	 * ���x������]���邩�ۂ�
	 * 
	 * @param isRotate
	 */
	public void setRotate(boolean isRotate) {
		this.isRotate = isRotate;
		// LabelUI ui = getUI();
		// if (ui instanceof BaseLabelUI) {
		// }
	}

}
