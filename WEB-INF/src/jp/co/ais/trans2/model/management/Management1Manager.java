package jp.co.ais.trans2.model.management;

import java.util.List;
import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ��C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface Management1Manager {

	/**
	 * �w������ɊY������Ǘ�����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ����
	 * @throws TException
	 */
	public List<Management1> get(Management1SearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ǘ�����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ǘ����
	 * @throws TException
	 */
	public List<Management1> getRef(Management1SearchCondition condition) throws TException;

	/**
	 * �Ǘ�1��o�^����B
	 * 
	 * @param management1 �Ǘ�1
	 * @throws TException
	 */
	public void entry(Management1 management1) throws TException;

	/**
	 * �Ǘ�1���C������B
	 * 
	 * @param management1 �Ǘ�1
	 * @throws TException
	 */
	public void modify(Management1 management1) throws TException;

	/**
	 * �Ǘ�1���폜����B
	 * 
	 * @param management1 �Ǘ�1
	 * @throws TException
	 */
	public void delete(Management1 management1) throws TException;

	/**
	 * �Ǘ�1�ꗗ���G�N�Z���`���ŕԂ�
	 * @param condition ��������
	 * @return �G�N�Z���`���̊Ǘ�1�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(Management1SearchCondition condition) throws TException;

}
