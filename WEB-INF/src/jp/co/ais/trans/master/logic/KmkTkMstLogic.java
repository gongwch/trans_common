package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * �Ȗڑ̌n�}�X�^
 */
public interface KmkTkMstLogic extends CommonLogic {

	/**
	 * �Ȗڑ̌n�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[�h
	 * @param strKmtCode �Ȗڑ̌n�R�[�h
	 * @param strKmtName �Ȗڑ̌n����
	 * @param strKmtName_K �Ȗڑ̌n������
	 * @param args ���̑��p�����[�^
	 * @return �Ȗڑ̌n�}�X�^���X�g
	 */
	public List searchKmtMstData(String strKaiCode, String strKmtCode, String strKmtName, String strKmtName_K,
		String... args);

	/**
	 * ��{�Ȗڑ̌n���擾����<BR>
	 * ��{�Ȗڑ̌n���擾����
	 * 
	 * @param conditionMap �p�����[�^�[
	 * @return Map
	 */
	public Map getItemSystemValue(Map conditionMap);

}
