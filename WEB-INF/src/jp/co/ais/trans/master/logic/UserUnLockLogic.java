package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �r�����䃍�W�b�N
 */
public interface UserUnLockLogic {

	/**
	 * ��ЃR�[�h�Ń��[�U�r�����X�g���擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ���[�U�r�����X�g
	 */
	List<PCI_CHK_CTL> getPCIListByCompanyCode(String companyCode);

	/**
	 * ���X�g�폜
	 * 
	 * @param dtoList ���[�U�r�����X�g
	 */
	void delete(List<PCI_CHK_CTL> dtoList);
}
