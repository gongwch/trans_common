package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TAB�\�ȃ_�C�A���O
 */
public interface TTabbedDialog {

	/**
	 * @return �_�C�A���O
	 */
	public TDialog getDialog();

	/**
	 * @return �\���p�̏�ʃp�l��
	 */
	public TTabbedPanel getDialogBasePanel();

	/**
	 * Ctrl��Ԃ�
	 * 
	 * @return Ctrl
	 */
	public TController getController();

	/**
	 * @return �N���ς�PG�ƔF�߂�L�[
	 */
	public String getUID();

	/**
	 * @return �v���O�����R�[�h
	 */
	public String getProgramCode();

	/**
	 * @return �v���O������
	 */
	public String getProgramName();

	/**
	 * ��ʕ\���O�Ɋ�b�p�l���̏�����
	 */
	public void initDialogBasePanel();

	/**
	 * �\������
	 * 
	 * @param b
	 */
	public void setVisible(boolean b);

	/**
	 * ACTIVE����
	 */
	public void active();
}
