package jp.co.ais.trans2.model.vessel;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���q�O�q�敪�}�l�[�W��
 * 
 * @author AIS
 */
public interface VesselCOManager {

	/**
	 * �w������ɊY������f�[�^��Ԃ�
	 * 
	 * @param isExcel
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public List<VesselCO> get(boolean isExcel) throws TException;

	/**
	 * �o�^����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(List<VesselCO> bean) throws TException;

	/**
	 * �폜����
	 * 
	 * @throws TException
	 */
	public void delete() throws TException;

	/**
	 * ���q�O�q�敪(�G�N�Z��)��Ԃ�
	 * 
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}
