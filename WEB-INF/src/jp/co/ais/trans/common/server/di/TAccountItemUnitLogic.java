package jp.co.ais.trans.common.server.di;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;

/**
 * �������W�b�N�C���^�t�F�[�X<BR>
 * <p>
 * 
 * @author Yitao
 */
public interface TAccountItemUnitLogic {

	/**
	 * �ȖڃR�[�h�I�����\���f�[�^�擾<BR>
	 * �ȖڃR�[�h�I�����\���f�[�^�擾�B
	 * 
	 * @param inputParameter �p�����[�^�[
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public Map<String, Object> getItemInfo(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * �Ȗڃ}�X�^�ꗗ�擾<BR>
	 * �Ȗڃ}�X�^�ꗗ�擾
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public List getItemInfoAll(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * �⏕�ȖڃR�[�h�I�����\���f�[�^�擾<BR>
	 * �⏕�ȖڃR�[�h�I�����\���f�[�^�擾�B
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public Map<String, Object> getSubItemInfo(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * �⏕�Ȗڃ}�X�^�ꗗ�擾<BR>
	 * �⏕�Ȗڃ}�X�^�ꗗ�擾
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public List getSubItemInfoAll(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * ����ȖڃR�[�h�I�����\���f�[�^�擾<BR>
	 * ����ȖڃR�[�h�I�����\���f�[�^�擾�B
	 * 
	 * @param inputParameter
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public Map<String, Object> getBreakDownItemInfo(AccountItemInputParameter inputParameter) throws TException;

	/**
	 * ����Ȗڃ}�X�^�ꗗ�擾<BR>
	 * ����Ȗڃ}�X�^�ꗗ�擾
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public List getBreakDownItemInfoAll(AccountItemInputParameter inputParameter) throws TException;

}
