package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Ȗڏ��C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface ItemManager {

	/**
	 * �w������ɊY������Ȗڏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ȗڏ��
	 * @throws TException
	 */
	public List<Item> get(ItemSearchCondition condition) throws TException;

	/**
	 * �Ȗڏ���o�^����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void entry(Item item) throws TException;

	/**
	 * �Ȗڏ����C������B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modify(Item item) throws TException;

	/**
	 * �Ȗڏ����폜����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void delete(Item item) throws TException;

	/**
	 * �G�N�Z��
	 * 
	 * @param condition
	 * @return �Ȗڏ��
	 * @throws TException
	 */
	public byte[] getExcel(ItemSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Ȗڑ̌n����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Ȗڑ̌n���
	 * @throws TException
	 */
	public List<ItemOrganization> getItemOrganization(ItemOrganizationSearchCondition condition) throws TException;

	/**
	 * @param bean ��������
	 * @throws TException
	 */
	public void entryItemOrganization(ItemOrganization bean) throws TException;

	/**
	 * @param bean ��������
	 * @throws TException
	 */
	public void modifyItemOrganization(ItemOrganization bean) throws TException;

	/**
	 * �Ȗڑ̌n���̃}�X�^�����폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteItemOrganization(ItemOrganization bean) throws TException;

	/**
	 * �Ȗڑ̌n���̃}�X�^�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̉Ȗڑ̌n���̃}�X�^�ꗗ
	 * @throws TException
	 */
	public byte[] getExcelItemOrganization(ItemOrganizationSearchCondition condition) throws TException;

	/**
	 * ��{�Ȗڑ̌n����Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ��{�Ȗڑ̌n���
	 * @throws TException
	 */
	public ItemOrganization getBaseItemOrganization(String companyCode) throws TException;

	/**
	 * �w������ɊY������⏕�Ȗڏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������⏕�Ȗڏ��
	 * @throws TException
	 */
	public List<Item> get(SubItemSearchCondition condition) throws TException;

	/**
	 * �⏕�Ȗڏ���o�^����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void entrySubItem(Item item) throws TException;

	/**
	 * �⏕�Ȗڏ����C������B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modifySubItem(Item item) throws TException;

	/**
	 * �⏕�Ȗڏ����폜����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void deleteSubItem(Item item) throws TException;

	/**
	 * hjokamoku �⏕�Ȗڂ̃G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getSubItemExcel(SubItemSearchCondition condition) throws TException;

	/**
	 * ����Ȗڏ���o�^����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void entryDetailItem(Item item) throws TException;

	/**
	 * ����Ȗڏ����C������B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modifyDetailItem(Item item) throws TException;

	/**
	 * ����Ȗڏ����폜����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void deleteDetailItem(Item item) throws TException;

	/**
	 * ����Ȗڂ̃G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getDetailItemExcel(DetailItemSearchCondition condition) throws TException;

	/**
	 * �w������ɊY���������Ȗڏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY���������Ȗڏ��
	 * @throws TException
	 */
	public List<Item> get(DetailItemSearchCondition condition) throws TException;

	/**
	 * �Ȗڏ���Ԃ�
	 * 
	 * @param company ��ЃR�[�h
	 * @param item �ȖڃR�[�h
	 * @param sub �⏕�ȖڃR�[�h
	 * @param detail ����ȖڃR�[�h
	 * @return �w������ɊY������Ȗڏ��
	 * @throws TException
	 */
	public Item getItem(String company, String item, String sub, String detail) throws TException;

}
