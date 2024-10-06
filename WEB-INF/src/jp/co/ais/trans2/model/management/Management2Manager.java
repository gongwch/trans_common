package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ�2�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface Management2Manager {

	/**
	 * �w������ɊY������Ǘ�����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ����
	 * @throws TException
	 */
	public List<Management2> get(Management2SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ����
	 * @throws TException
	 */
	public List<Management2> getRef(Management2SearchCondition condition) throws TException;

	/**
	 * �Ǘ�2��o�^����B
	 * 
	 * @param management2
	 * @throws TException
	 */
	public void entry(Management2 management2) throws TException;

	/**
	 * �Ǘ�2���C������B
	 * 
	 * @param management2
	 * @throws TException
	 */
	public void modify(Management2 management2) throws TException;

	/**
	 * �Ǘ�2���폜����B
	 * 
	 * @param management2
	 * @throws TException
	 */
	public void delete(Management2 management2) throws TException;

	/**
	 * �v���O����(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �v���O����
	 * @throws TException
	 */
	public byte[] getExcel(Management2SearchCondition condition) throws TException;

}
