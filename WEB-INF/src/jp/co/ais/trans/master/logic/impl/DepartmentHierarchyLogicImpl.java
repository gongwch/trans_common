package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ����K�w�}�X�^����
 */
public class DepartmentHierarchyLogicImpl extends CommonLogicBaseImpl implements DepartmentHierarchyLogic {

	/** ����K�w�}�X�^Dao */
	protected DPK_MSTDao dao;

	/** ����K�w�}�X�^���� */
	protected DPK_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao DPK_MSTDao
	 */
	public DepartmentHierarchyLogicImpl(DPK_MSTDao dao) {
		// ����K�w�}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * DPK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity DPK_MST
	 */
	public void setEntity(DPK_MST entity) {
		// ����K�w�}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �g�D�R�[�h�̎擾
		String dpkSsk = (String) conditions.get("dpkSsk");

		List result = dao.find(kaiCode, dpkSsk);

		for (int i = 0; i < result.size(); i++) {
			for (int j = i + 1; j < result.size(); j++) {
				DPK_MST entity1 = (DPK_MST) result.get(i);
				DPK_MST entity2 = (DPK_MST) result.get(j);

				int compare = compareTo(entity1.getDPK_LVL_0(), entity2.getDPK_LVL_0());

				if (compare == 0) {
					compare = compareTo(entity1.getDPK_LVL_1(), entity2.getDPK_LVL_1());

					if (compare == 0) {
						compare = compareTo(entity1.getDPK_LVL_2(), entity2.getDPK_LVL_2());

						if (compare == 0) {
							compare = compareTo(entity1.getDPK_LVL_3(), entity2.getDPK_LVL_3());

							if (compare == 0) {
								compare = compareTo(entity1.getDPK_LVL_4(), entity2.getDPK_LVL_4());

								if (compare == 0) {
									compare = compareTo(entity1.getDPK_LVL_5(), entity2.getDPK_LVL_5());

									if (compare == 0) {
										compare = compareTo(entity1.getDPK_LVL_6(), entity2.getDPK_LVL_6());

										if (compare == 0) {
											compare = compareTo(entity1.getDPK_LVL_7(), entity2.getDPK_LVL_7());

											if (compare == 0) {
												compare = compareTo(entity1.getDPK_LVL_8(), entity2.getDPK_LVL_8());

												if (compare == 0) {
													compare = compareTo(entity1.getDPK_LVL_9(), entity2.getDPK_LVL_9());
												}
											}
										}
									}
								}
							}
						}
					}
				}

				if (compare > 0) {
					result.set(i, entity2);
					result.set(j, entity1);
				}
			}
		}

		// ���ʂ�Ԃ�
		return result;
	}

	private int compareTo(String a, String b) {
		if (a == null) {
			return -1;
		} else if (b == null) {
			return 1;
		} else {
			return a.compareTo(b);
		}
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// �g�D�R�[�h�̎擾
		String dpkSsk = (String) keys.get("dpkSsk");
		// ����R�[�h�̎擾
		String dpkDepCode = (String) keys.get("dpkDepCode");
		// ���ʂ�Ԃ�
		return dao.getDPK_MSTByKaicodeDpksskDpkdepcode(kaiCode, dpkSsk, dpkDepCode);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param keys Map
	 */
	public void delete(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// �g�D�R�[�h�̎擾
		String dpkSsk = (String) keys.get("dpkSsk");
		// ����R�[�h�̎擾
		String dpkDepCode = (String) keys.get("dpkDepCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// �g�D�R�[�h�̐ݒ�
		entity.setDPK_SSK(dpkSsk);
		// ����R�[�h�̐ݒ�
		entity.setDPK_DEP_CODE(dpkDepCode);
		// �f�[�^���폜����
		dao.delete(entity);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param entity1 DPK_MST
	 */
	public void delete(DPK_MST entity1) {
		// �f�[�^���폜����
		dao.delete(entity1);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		DPK_MST entity1 = (DPK_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity1);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		DPK_MST entity1 = (DPK_MST) dto;

		// �f�[�^���X�V����
		dao.update(entity1);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param kaiCode String
	 */
	public List getOrganizations(String kaiCode) {
		// ���ʂ�Ԃ�
		return dao.getOrganizations(kaiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		DPK_MST entity1 = (DPK_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String dpkSSK = entity1.getDPK_SSK();
		String dpkdepCode = entity1.getDPK_DEP_CODE();

		return dao.getDPK_MSTByKaicodeDpksskDpkdepcode(kaiCode, dpkSSK, dpkdepCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		DPK_MST entity1 = (DPK_MST) dto;
		return entity1.getDPK_SSK();
	}

	public List getREFItems(Map keys) {
		DPK_MST entity1 = (DPK_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(10);

			int triCode = entity1.getDPK_LVL();
			// ���僌�x��
			result.add(triCode);
			// �����郌�x�����Ƃɕ���R�[�h�������Ă���B
			// ��ʕ���ݒ�̑Ή�
			result.add(entity1.getDPK_LVL_0());
			result.add(entity1.getDPK_LVL_1());
			result.add(entity1.getDPK_LVL_2());
			result.add(entity1.getDPK_LVL_3());
			result.add(entity1.getDPK_LVL_4());
			result.add(entity1.getDPK_LVL_5());
			result.add(entity1.getDPK_LVL_6());
			result.add(entity1.getDPK_LVL_7());
			result.add(entity1.getDPK_LVL_8());
			result.add(entity1.getDPK_LVL_9());

			// ���ʂ̐ݒ�
			list.add(result);
		} else {
			// ���ʂ̍폜
			list.add(null);
		}
		// ���ʂ�Ԃ�
		return list;
	}
}
