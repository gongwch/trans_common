package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;

/**
 * MAIN��ʏ��������̏�������I/F
 */
public interface TMainInitialInterface extends TLoginInitialInterface {

	/**
	 * ������
	 * 
	 * @return ������
	 */
	public String getName();

	/**
	 * ����������(TMain��ʍ쐬�O)
	 */
	public void init();

	/**
	 * �J�n����(TMain��ʍ쐬��)
	 */
	public void afterCreate();

	/**
	 * @return �\����{�^��
	 */
	public List<TButton> getAddonButtonList();
}
