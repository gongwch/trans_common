package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * ��ЃR���g���[���r�W�l�X���W�b�N
 */
public interface CompanyControlLogic extends CommonLogic {

	/**
	 * ��ЃR�[�h�ŃR���g���[���G���e�B�e�B���擾
	 * 
	 * @param kaiCode
	 * @return ��ЃR���g���[�����
	 */
	public abstract Object findOne(String kaiCode);

	/**
	 * ���������ɂ���ЃR���g���[�����X�g�擾
	 * 
	 * @param keys
	 * @return ��ЃR���g���[�����X�g
	 */
	public abstract List getREFItems(Map keys);

}
