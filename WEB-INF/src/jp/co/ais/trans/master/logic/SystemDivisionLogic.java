package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �V�X�e���敪�}�X�^�r�W�l�X���W�b�N
 */
public interface SystemDivisionLogic {

	/**
	 * �V�X�e���敪���X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return �V�X�e���敪���X�g
	 */
	List<SYS_MST> getSystemList(String kaiCode);

	/**
	 * �V�X�e���敪�擾
	 * 
	 * @param kaiCode
	 * @param syskbn
	 * @return �V�X�e���敪
	 */
	SYS_MST getSystemKbn(String kaiCode, String syskbn);
}
