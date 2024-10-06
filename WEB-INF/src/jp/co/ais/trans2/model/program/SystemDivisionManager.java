package jp.co.ais.trans2.model.program;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * �V�X�e���敪�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface SystemDivisionManager {

	/**
	 * ����̃V�X�e���敪����Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param code �V�X�e���敪�R�[�h
	 * @return ����̃V�X�e���敪���
	 * @throws TException
	 */
	public SystemDivision get(String companyCode, String code) throws TException;

	/**
	 * �w������ɊY������V�X�e���敪����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������V�X�e���敪
	 * @throws TException
	 */
	public List<SystemDivision> get(SystemDivisionSearchCondition condition) throws TException;

	/**
	 * �V�X�e���敪����ǉ�����B
	 * 
	 * @param bean �o�^�f�[�^
	 * @throws TException
	 */
	public void entry(SystemDivision bean) throws TException;

	/**
	 * �V�X�e���敪�����X�V����B
	 * 
	 * @param bean �C���f�[�^
	 * @throws TException
	 */
	public void modify(SystemDivision bean) throws TException;

	/**
	 * �V�X�e���敪�����폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(SystemDivision bean) throws TException;

	/**
	 * �V�X�e���敪�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̃V�X�e���敪�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(SystemDivisionSearchCondition condition) throws TException;

}
