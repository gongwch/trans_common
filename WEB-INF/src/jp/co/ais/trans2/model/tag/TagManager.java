package jp.co.ais.trans2.model.tag;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0460TagMaster - �tⳃ}�X�^ - Interface Class
 * 
 * @author AIS
 */
public interface TagManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return Tag �tⳃ}�X�^
	 * @throws TException
	 */
	public List<Tag> get(TagSearchCondition condition) throws TException;

	/**
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(Tag bean) throws TException;

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(Tag bean) throws TException;

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param bean �I�����
	 * @throws TException
	 */
	public void delete(Tag bean) throws TException;
}