package jp.co.ais.trans2.model.slip;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * �`�[��ʃC���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface SlipTypeManager {

	/**
	 * ����̓`�[��ʏ���Ԃ�
	 * @param companyCode ��ЃR�[�h
	 * @param slipTypeCode �`�[��ʃR�[�h
	 * @return ����̓`�[��ʏ��
	 * @throws TException
	 */
	public SlipType get(String companyCode, String slipTypeCode) throws TException;

	/**
	 * �w������ɊY������`�[��ʏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������`�[���
	 * @throws TException
	 */
	public List<SlipType> get(SlipTypeSearchCondition condition) throws TException;

	/**
	 * 
	 * @param sliptype ��������
	 * @throws TException
	 */
	public void entry(SlipType sliptype) throws TException;

	/**
	 * 
	 * @param sliptype ��������
	 * @throws TException
	 */
	public void modify(SlipType sliptype) throws TException;

	/**
	 * �`�[��ʏ����폜����B
	 * @param sliptype 
	 * @throws TException 
	 */
	public void delete(SlipType sliptype) throws TException;


	/**
	 * �`�[��ʃ}�X�^�ꗗ���G�N�Z���`���ŕԂ�
	 * @param condition ��������
	 * @return �G�N�Z���`���̓`�[��ʃ}�X�^�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(SlipTypeSearchCondition condition) throws TException;
}
