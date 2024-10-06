package jp.co.ais.trans.common.gui;

/**
 * �^�u������ interface. 
 * <br>
 * item ��tab�ړ����Ԃ�ݒ肷��B 
 * ���l�̏�����item����傫��item�ɏ��Ɉړ�����B
 * �����l��item�́A�ǂ��炪��ɂȂ邩�K�肵�Ȃ��B
 */
public interface TInterfaceTabControl {

	/**
	 * GUI�A�C�e���ɐݒ肳�ꂽ�^�u�ړ����ԍ��擾
	 * 
	 * @return int �^�u���ԍ�
	 */
	int getTabControlNo();

	/**
	 * GUI�A�C�e���Ƀ^�u�ړ����ԍ���ݒ�
	 * 
	 * @param tabControlNo �^�u���ԍ�
	 */
	void setTabControlNo(int tabControlNo);

	/**
	 * �^�u�ړ��Ńt�H�[�J�X�L������ݒ�
	 * 
	 * @param bool true:�L�� false:����
	 */
	void setTabEnabled(boolean bool);
	
	/**
	 * �^�u�ړ��Ńt�H�[�J�X�L�������擾
	 * 
	 * @return true:�L�� false:����
	 */
	boolean isTabEnabled();
}
