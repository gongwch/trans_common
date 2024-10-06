package jp.co.ais.trans.logic.system;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �r�����䃍�W�b�N
 */
public interface Lock extends Serializable {

	/**
	 * �R�[�h�ݒ�
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param compCode ��ЃR�[�h
	 */
	public void setCode(String compCode, String userCode);

	/**
	 * �R�[�h�ݒ�i�ʔr�������j
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param prgCode �v���O�����R�[�h
	 */
	public void setCode(String compCode, String userCode, String prgCode);

	/**
	 * �@�\�r�� ������������
	 */
	public void deleteForced();

	/**
	 * �R�[�h�r��(HAITA_CTL) ������������
	 */
	public void deleteHaitaForced();

	/**
	 * �S�����r������
	 */
	public void unlockAll();

	/**
	 * �w��̓`�[���X�g��r����������B
	 * 
	 * @param slipList
	 */
	public void unlockSlip(List<SlipUnlockObject> slipList);

	/**
	 * �o�b�`�r�� �ʔr������
	 */
	public void batctlDeleteByUsrPrg();

	/**
	 * �R�[�h�r�� �ʔr������
	 */
	public void haitactlDeleteByUsrPrg();

	/**
	 * �o�b�`, �R�[�h �ʔr������
	 */
	public void unlockPrg();

	/**
	 * �w���ЃR�[�h�Ŕr���`�[���X�g���擾
	 * 
	 * @return �r���`�[���X�g
	 */
	public List<SlipUnlockObject> getExclusiveSlip();

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode();

	/**
	 * ���[�U�R�[�h�擾
	 * 
	 * @return ���[�U�R�[�h
	 */
	public String getUserCode();

	/**
	 * �v���O�����R�[�h�擾
	 * 
	 * @return �v���O�����R�[�h
	 */
	public String getPrgCode();
	
	/**
	 * �r�������`�[���X�g�擾
	 * 
	 * @return �r�������`�[���X�g
	 */
	public List<SlipUnlockObject> getUnlockSlipList();

	/**
	 * �r���`�[���X�g��ǉ�
	 * 
	 * @param exclusiveSlipList
	 */
	public void addSliplist(List<SlipUnlockObject> exclusiveSlipList);
}
