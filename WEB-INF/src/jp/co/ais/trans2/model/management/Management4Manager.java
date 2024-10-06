package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ�4�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface Management4Manager {

	/**
	 * �w������ɊY������Ǘ�4����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�4���
	 * @throws TException
	 */
	public List<Management4> get(Management4SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�4����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ�4���
	 * @throws TException
	 */
	public List<Management4> getRef(Management4SearchCondition condition) throws TException;

	/**
	 * �Ǘ�4��o�^����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management4 management) throws TException;

	/**
	 * �Ǘ�4���C������B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management4 management) throws TException;

	/**
	 * �Ǘ�4���폜����B
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management4 management) throws TException;

	/**
	 * �Ǘ�4�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�4�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management4SearchCondition condition) throws TException;

}
