package jp.co.ais.trans2.op.model.item;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * OP�A�C�e���}�l�[�W��
 */
public interface OPItemManager {

	/**
	 * REF�p�f�[�^�̎擾
	 * 
	 * @param condition
	 * @return REF�p�f�[�^
	 * @throws TException
	 */
	public List<OPItem> get(OPItemSearchCondition condition) throws TException;

	/**
	 * OPITEM����Ԃ�
	 * 
	 * @param company
	 * @param item
	 * @return OPItem
	 * @throws TException
	 */
	public OPItem getItem(String company, String item) throws TException;

	/**
	 * �w������ɊY������A�C�e����Ԃ�
	 * 
	 * @param condition
	 * @return �w������ɊY������A�C�e��
	 * @throws TException
	 */
	public OPItem getCode(OPItemSearchCondition condition) throws TException;

	/**
	 * ITEM����o�^����B
	 * 
	 * @param bean item
	 * @param oldBean old item
	 * @return result
	 * @throws TException
	 */
	public OPItem entry(OPItem bean, OPItem oldBean) throws TException;

	/**
	 * Checks if is exist item
	 * 
	 * @param bean the bean
	 * @return true, if is exist
	 * @throws TException exception
	 */
	public boolean isExist(OPItem bean) throws TException;

	/**
	 * ITEM�����C������B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modify(OPItem item) throws TException;

	/**
	 * Insert new item
	 * 
	 * @param item
	 * @throws TException exception
	 */
	public void insert(OPItem item) throws TException;

	/**
	 * @param conn
	 * @param list
	 * @throws Exception
	 */
	public void insert(Connection conn, List<OPItem> list) throws Exception;

	/**
	 * ITEM�����폜����B
	 * 
	 * @param item
	 * @throws TException
	 */
	public void delete(OPItem item) throws TException;

	/**
	 * OP�A�C�e���폜(�Ώۖ��킪���݂��ĂȂ��ꍇ)
	 * 
	 * @param conn
	 * @throws TException
	 */
	@Deprecated
	public void delete(Connection conn) throws TException;

	/**
	 * Checks if is exist item by control type
	 * 
	 * @param companyCode the company code
	 * @param itemControlType the item control type
	 * @param subItemControlType the sub item control type
	 * @return true, if is exist item
	 * @throws TException exception
	 */
	public Boolean isExistControlType(String companyCode, String itemControlType, String subItemControlType) throws TException;

	/**
	 * Gets the excel data.
	 * 
	 * @param condition the condition
	 * @return the excel
	 * @throws TException exception
	 */
	public byte[] getExcel(OPItemSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(OPItemSearchCondition condition) throws TException;

}
