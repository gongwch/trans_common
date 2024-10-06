package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ�6�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface Management6Manager {

	/**
	 * �w������ɊY������Ǘ�6����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�6���
	 * @throws TException
	 */
	public List<Management6> get(Management6SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�6����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�6���
	 * @throws TException
	 */
	public List<Management6> getRef(Management6SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�6����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�6���
	 * @throws TException
	 */
	public byte[] getExcel(Management6SearchCondition condition) throws TException;

	/**
	 * �Ǘ�6��o�^����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management6 management) throws TException;

	/**
	 * �Ǘ�6���C������B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management6 management) throws TException;

	/**
	 * �Ǘ�6���폜����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management6 management) throws TException;

}
