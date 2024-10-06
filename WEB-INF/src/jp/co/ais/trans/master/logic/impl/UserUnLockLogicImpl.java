package jp.co.ais.trans.master.logic.impl;

import java.util.List;

import jp.co.ais.trans.master.dao.PCI_CHK_CTLDao;
import jp.co.ais.trans.master.entity.PCI_CHK_CTL;
import jp.co.ais.trans.master.logic.UserUnLockLogic;

/**
 * �r���Ǘ��}�X�^�r�W�l�X���W�b�N
 */
public class UserUnLockLogicImpl implements UserUnLockLogic {

	/** �r���Ǘ��}�X�^Dao */
	private PCI_CHK_CTLDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao PCI_CHK_CTLDao
	 */
	public UserUnLockLogicImpl(PCI_CHK_CTLDao dao) {
		this.dao = dao;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.UserUnLockLogic#getPCIListByCompanyCode(java.lang.String)
	 */
	public List<PCI_CHK_CTL> getPCIListByCompanyCode(String companyCode) {
		return dao.getPCIListByCompanyCode(companyCode);
	}

	/**
	 * @see jp.co.ais.trans.master.logic.UserUnLockLogic#delete(java.util.List)
	 */
	public void delete(List<PCI_CHK_CTL> dtoList) {
		for (PCI_CHK_CTL pci_chk_dto : dtoList) {
			dao.delete(pci_chk_dto.getKAI_CODE(), pci_chk_dto.getUSR_ID());
		}
	}
}
