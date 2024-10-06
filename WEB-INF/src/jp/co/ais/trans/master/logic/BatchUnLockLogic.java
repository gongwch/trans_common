package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �o�b�`�r�����䃍�W�b�N
 * 
 * @author �גJ
 */
public interface BatchUnLockLogic {

	/**
	 * ��ЃR�[�h�Ńo�b�`�r�����X�g���擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return �o�b�`�r�����X�g
	 */
	public List<BAT_CTL> getBatchListByCompanyCode(String companyCode);

	/**
	 * ���X�g�폜
	 * 
	 * @param dtoList �o�b�`�r�����X�g
	 */
	public void delete(List<BAT_CTL> dtoList);
}
