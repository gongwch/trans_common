package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * Port�Ǘ��̎����N���X
 * 
 * @author AIS
 */
public class PortManagerImpl extends TModel implements PortManager {

	/**
	 * @return Dao
	 */
	protected PortDao getDao() {
		return (PortDao) getComponent(PortDao.class);
	}

	/**
	 * �w��R�[�h�ɕR�t��Port����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param voyageCode Port�R�[�h
	 * @return �w��R�[�h�ɕR�t��Port���
	 * @throws TException
	 */
	@Override
	public Port get(String companyCode, String voyageCode) throws TException {
		PortDao dao = getDao();
		return dao.get(companyCode, voyageCode);
	}

	/**
	 * �w������ɊY������Port����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Port���
	 * @throws TException
	 */
	@Override
	public List<Port> get(PortSearchCondition condition) throws TException {
		PortDao dao = getDao();
		return dao.getByCondition(condition);
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException {
		PortDao dao = getDao();
		return dao.hasDelete(condition);
	}

	/**
	 * Port��o�^����B
	 * 
	 * @param voyage Port
	 * @throws TException
	 */
	@Override
	public void entry(Port voyage) throws TException {
		PortDao dao = getDao();
		dao.insert(voyage);
	}

	/**
	 * Port���C������B
	 * 
	 * @param voyage Port
	 * @throws TException
	 */
	@Override
	public void modify(Port voyage) throws TException {
		PortDao dao = getDao();
		dao.update(voyage);
	}

	/**
	 * Port���폜����B
	 * 
	 * @param voyage Port
	 * @throws TException
	 */
	@Override
	public void delete(Port voyage) throws TException {
		PortDao dao = getDao();
		dao.delete(voyage);
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */

	public byte[] getExcel(PortSearchCondition condition) throws TException {

		try {

			// �`���f�[�^�𒊏o
			List<Port> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PortExcel exl = new PortExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}
}
