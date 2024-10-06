package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ���[�U�}�X�^����
 */
public class UserLogicImpl extends CommonLogicBaseImpl implements UserLogic {

	/** �R���e�i */
	protected S2Container container;

	/** ���[�U�[�}�X�^Dao */
	protected USR_MSTDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param container �R���e�i
	 */
	public UserLogicImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * ���[�UDao�ݒ�
	 * 
	 * @param usrMstDao USR_MSTDao
	 */
	public void setUSR_MSTDao(USR_MSTDao usrMstDao) {
		this.dao = usrMstDao;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �J�n�R�[�h�̎擾
		String beginUsrCode = (String) conditions.get("beginUsrCode");
		// �I���R�[�h�̎擾
		String endUsrCode = (String) conditions.get("endUsrCode");

		// ���ʂ�Ԃ�
		return dao.getUsrMstData(kaiCode, beginUsrCode, endUsrCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���[�U�[�R�[�h�̎擾
		String usrCode = (String) keys.get("usrCode");
		// ���ʂ�Ԃ�
		return dao.getUSR_MSTByKaicodeUsrcode(kaiCode, usrCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			USR_MST e = (USR_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// ���[�U�[�R�[�h�̐ݒ�
			e2.setCode(e.getUSR_CODE());
			// ���[�U�[���̂̐ݒ�
			e2.setName_S(e.getUSR_NAME_S());
			// ���[�U�[�������̂̐ݒ�
			e2.setName_K(e.getUSR_NAME_K());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���̂̏�����
		USR_MST entity = dao.getRefName(kaiCode, code);

		// ���̂�Ԃ�
		return (entity == null ? null : entity.getUSR_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		USR_MST entity = (USR_MST) container.getComponent(USR_MST.class);

		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ���[�U�[�R�[�h�̎擾
		String usrCode = (String) conditions.get("usrCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// ���[�U�[�R�[�h�̐ݒ�
		entity.setUSR_CODE(usrCode);
		// �f�[�^���폜����
		dao.delete(entity);

		S2Container scontainer = SingletonS2ContainerFactory.getContainer();
		LOCK_OUT_TBLDao lockDao = (LOCK_OUT_TBLDao) scontainer.getComponent(LOCK_OUT_TBLDao.class);
		PWD_HST_TBLDao historyDao = (PWD_HST_TBLDao) scontainer.getComponent(PWD_HST_TBLDao.class);
		PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) scontainer.getComponent(PCI_CHK_CTLDao.class);
		pciDao.delete(kaiCode, usrCode);
		historyDao.deleteByPK(kaiCode, usrCode);
		lockDao.deleteByPK(kaiCode, usrCode);

	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// �f�[�^��o�^����
		dao.insert((USR_MST) dto);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {

		// �f�[�^���X�V����
		dao.update((USR_MST) dto);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		USR_MST entity = (USR_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String usrCode = entity.getUSR_CODE();

		return dao.getUSR_MSTByKaicodeUsrcode(kaiCode, usrCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		USR_MST entity = (USR_MST) dto;
		return entity.getUSR_CODE();
	}

	public List getREFItems(Map keys) {
		USR_MST entity = (USR_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(6);

			String empCode = entity.getEMP_CODE();
			String depCode = entity.getDEP_CODE();

			result.add(empCode);
			// ��s���̐ݒ�
			result.add(depCode != null ? entity.getDEP_CODE() : "");

			result.add(entity.getSTR_DATE());
			// �I��
			result.add(entity.getEND_DATE());
			// ���ʂ̐ݒ�
			list.add(result);
		} else {
			// ���ʂ̍폜
			list.add(null);
		}
		// ���ʂ�Ԃ�
		return list;
	}

	/**
	 * ��ЃR�[�h�Ń��[�U���X�g���K��
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return List<USR_MST> ���[�Ulist
	 */
	public List<USR_MST> searchUsrList(String kaiCode) {
		return dao.getUSR_MSTByKaicode(kaiCode);
	}

	/**
	 * �X�V���[�U�}�X�^
	 * 
	 * @param entity ���[�U�}�X�^
	 * @param oldKaiCode old��ЃR�[�h
	 */
	public void updateUsrMst(USR_MST entity, String oldKaiCode) {
		dao.updateUsrMst(entity, oldKaiCode);
	}
}
