package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ȗڐ���敪�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface AutomaticJournalItemManager {

	/**
	 * �w������ɊY������Ȗڐ���敪��Ԃ�
	 * @param condition 
	 * 
	 * @return �w������ɊY������Ȗڐ���敪���
	 * @throws TException
	 */
	public List<AutomaticJournalItem> get(AutomaticJournalItemSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ȗڐ���敪��o�^����
	 * @param bean 
	 * @throws TException 
	 */
	public void entry(AutomaticJournalItem bean) throws TException;

	/**
	 * �w������ɊY������Ȗڐ���敪���C������
	 * @param bean 
	 * @throws TException 
	 */
	public void modify(AutomaticJournalItem bean) throws TException;

	/**
	 * �w������ɊY������Ȗڐ���敪���폜����
	 * @param bean 
	 * @throws TException 
	 */
	public void delete(AutomaticJournalItem bean) throws TException;

	/**
	 * �w������ɊY������Ȗڐ���敪�̑ݎ؂��`�F�b�N����
	 * @param bean 
	 * @return int
	 * @throws TException 
	 */
	public int check(AutomaticJournalItem bean) throws TException;

	/**
	 * �G�N�Z��
	 * 
	 * @param condition
	 * @return �Ȗڐ���敪���
	 * @throws TException
	 */
	public byte[] getExcel(AutomaticJournalItemSearchCondition condition) throws TException;

}
