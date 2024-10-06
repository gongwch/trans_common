package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - Interface Class
 * 
 * @author AIS
 */
public interface ItemSummaryManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List �ȖڏW�v�}�X�^���
	 * @throws TException
	 */
	public List<ItemSummary> get(ItemSummarySearchCondition condition) throws TException;

	/**
	 * ���t�@�����X����
	 * 
	 * @param condition
	 * @return ���t�@�����X���
	 * @throws TException
	 */
	public List<ItemSummary> getRef(ItemSummarySearchCondition condition) throws TException;

	/**
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(ItemSummary bean) throws TException;

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(ItemSummary bean) throws TException;

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param bean �I�����
	 * @throws TException
	 */
	public void delete(ItemSummary bean) throws TException;

	/**
	 * ��񌟍���Excel�ŏo�� (SELECT)
	 * 
	 * @param condition ��������
	 * @return Excel
	 * @throws TException
	 */
	public byte[] getExcel(ItemSummarySearchCondition condition) throws TException;

	/**
	 * �ȖڏW�v���o�^ (INSERT)
	 * 
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 * @param list ���͏��
	 * @throws TException
	 */
	public void entryItemSummary(String kmtCode, List<ItemSummary> list) throws TException;

	/**
	 * �ȖڏW�v���o�^ (INSERT)���ʎ�
	 * 
	 * @param preKmtCode �O��Ȗڑ̌n�R�[�h
	 * @param kmtCode ����Ȗڑ̌n�R�[�h
	 * @throws TException
	 */
	public void entryCopyItemSummary(String preKmtCode, String kmtCode) throws TException;

	/**
	 * �Ȗڃ}�X�^�ɑ��݁A�ȖڏW�v�}�X�^�ɑ��݂��Ȃ��Ȗڃ`�F�b�N
	 * 
	 * @param orgCode
	 * @return �Ȗڃ}�X�^�ɑ��݁A�ȖڏW�v�}�X�^�ɑ��݂��Ȃ����ǂ��� true�F���o�^
	 * @throws TException
	 */
	public String getNotExistItemSum(String orgCode) throws TException;

	/**
	 * �Ȗڃ}�X�^�f�[�^�����݂��Ȃ��W�v�Ȗڃ`�F�b�N
	 * 
	 * @param orgCode
	 * @return �Ȗڃ}�X�^�f�[�^�����݂��Ȃ����ǂ��� true�F���o�^
	 * @throws TException
	 */
	public String getNotExistItem(String orgCode) throws TException;
}