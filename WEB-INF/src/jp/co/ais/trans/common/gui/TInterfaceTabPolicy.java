package jp.co.ais.trans.common.gui;

import java.awt.event.KeyEvent;

/**
 * tab focus policy interface.
 */
public interface TInterfaceTabPolicy {

	/**
	 * enter key ��policy�g�p
	 * 
	 * @return true:enter key��policy�g�p false:�ʏ�policy�g�p
	 */
	public boolean getModeEnter();

	/**
	 * enter key ��policy�g�p
	 * 
	 * @param b true:enter key��policy�g�p false:�ʏ�policy�g�p
	 */
	public void setModeEnter(boolean b);

	/**
	 * ���O�ɃA�N�Z�X���ꂽ�R���|�[�l���g setter.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	public void setEventBefore(KeyEvent evt);

	/**
	 * ���O�ɃA�N�Z�X���ꂽ�R���|�[�l���g getter.
	 * 
	 * @return �L�[�C�x���g
	 */
	public KeyEvent getEventBefore();
}
