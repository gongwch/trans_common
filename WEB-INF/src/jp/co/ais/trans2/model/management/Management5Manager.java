package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ�5�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface Management5Manager {

	/**
	 * �w������ɊY������Ǘ�5����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�5���
	 * @throws TException
	 */
	public List<Management5> get(Management5SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�5����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�5���
	 * @throws TException
	 */
	public List<Management5> getRef(Management5SearchCondition condition) throws TException;

	/**
	 * �Ǘ�5��o�^����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management5 management) throws TException;

	/**
	 * �Ǘ�5���C������B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management5 management) throws TException;

	/**
	 * �Ǘ�5���폜����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management5 management) throws TException;

	/**
	 * �Ǘ�5�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�5�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management5SearchCondition condition) throws TException;

}
