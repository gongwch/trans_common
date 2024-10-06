package jp.co.ais.trans2.model.remark;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �E�v�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface RemarkManager {

	/**
	 * �w������ɊY������E�v����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������E�v���
	 * @throws TException
	 */
	public List<Remark> get(RemarkSearchCondition condition) throws TException;

	/**
	 * �E�v����o�^����B
	 * 
	 * @param remark
	 * @throws TException
	 */
	public void entry(Remark remark) throws TException;

	/**
	 * �E�v�����C������B
	 * 
	 * @param remark
	 * @throws TException
	 */
	public void modify(Remark remark) throws TException;

	/**
	 * �E�v�����폜����B
	 * 
	 * @param remark
	 * @throws TException
	 */
	public void delete(Remark remark) throws TException;

	/**
	 * �E�v���(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition
	 * @return �E�v���
	 * @throws TException
	 */
	public byte[] getExcel(RemarkSearchCondition condition) throws TException;

}
