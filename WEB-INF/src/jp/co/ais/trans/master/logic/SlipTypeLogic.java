package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �`�[��ރ}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface SlipTypeLogic extends CommonLogic {

	/**
	 * �`�[��ʗ��̎擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param isIncludeSystemEls true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 * @return �`�[��ʗ��̃��X�g
	 */
	public List<DEN_SYU_MST> getDenSyuNames(String kaiCode, boolean isIncludeSystemEls);
}
