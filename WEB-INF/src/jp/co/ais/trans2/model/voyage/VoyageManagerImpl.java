package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * Voyage�Ǘ��̎����N���X
 * 
 * @author AIS
 */
public class VoyageManagerImpl extends TModel implements VoyageManager {

	/**
	 * @return Dao
	 */
	protected VoyageDao getDao() {
		return (VoyageDao) getComponent(VoyageDao.class);
	}

	@Override
	public Voyage get(String companyCode, String voyageCode) throws TException {
		VoyageDao dao = getDao();
		return dao.get(companyCode, voyageCode);
	}

	@Override
	public List<Voyage> get(VoyageSearchCondition condition) throws TException {
		VoyageDao dao = getDao();
		return dao.getByCondition(condition);
	}

	@Override
	public void entry(Voyage voyage) throws TException {
		VoyageDao dao = getDao();
		dao.insert(voyage);
	}

	@Override
	public void modify(Voyage voyage) throws TException {
		VoyageDao dao = getDao();
		dao.update(voyage);
	}

	@Override
	public void delete(Voyage voyage) throws TException {
		VoyageDao dao = getDao();
		dao.delete(voyage);
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	// TODO �ꎞ�I�ɃR�����g�A�E�g
	public byte[] getExcel(VoyageSearchCondition condition) throws TException {

		try {

			// ���q���f�[�^�𒊏o
			List<Voyage> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			VoyageExcel exl = new VoyageExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException {
		VoyageDao dao = getDao();
		return dao.hasDelete(condition);
	}

}
