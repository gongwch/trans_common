package jp.co.ais.trans.common.gui;

import javax.swing.*;

/***************************************************************************************************
 * 
 * JTextArea�ɁA���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�����TextArea.
 * <br>
 * �܂�Ԃ��Œ蕶���\���p�B(Label�p�r)
 **************************************************************************************************/
public class TFixedTextArea extends JTextArea implements TInterfaceLangMessageID {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** message ID */
	private String langMessageID = null;

	/**
	 * �R���X�g���N�^.
	 */
	public TFixedTextArea() {
		super();
		this.setEditable(false);
	}

	/**
	 * ������̐ݒ�.
	 * <br>
	 * Label�p�r�̂��߁AJTextArea��editable property��false�ɂ��Ă���B
	 * setText()��override���Afalse�ł��������ݒ�ł���悤�ɂ���B
	 * @param value �ݒ蕶����
	 * 
	 */
	@Override
	public void setText(String value) {
		boolean b;

		b = super.isEditable();
		super.setEditable(true);

		super.setText(value);

		super.setEditable(b);
	}

	/* (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/* (non-Javadoc)
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

}
