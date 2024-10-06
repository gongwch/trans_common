package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ�3�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface Management3Manager {

	/**
	 * �w������ɊY������Ǘ�3����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�3���
	 * @throws TException
	 */
	public List<Management3> get(Management3SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�3����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�3���
	 * @throws TException
	 */
	public List<Management3> getRef(Management3SearchCondition condition) throws TException;

	/**
	 * �Ǘ�3��o�^����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management3 management) throws TException;

	/**
	 * �Ǘ�3���C������B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management3 management) throws TException;

	/**
	 * �Ǘ�3���폜����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management3 management) throws TException;

	/**
	 * �Ǘ�3�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�3�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management3SearchCondition condition) throws TException;

}
