package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �Ȗڑ̌n���̃}�X�^����
 */
public class KmkTkMstLogicImpl extends CommonLogicBaseImpl implements KmkTkMstLogic {

	private KMK_TK_MSTDao dao;

	/**
	 * @param dao
	 */
	public KmkTkMstLogicImpl(KMK_TK_MSTDao dao) {
		this.dao = dao;
	}

	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			KMK_TK_MST e = (KMK_TK_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getKMT_CODE());
			e2.setName_S(e.getKMT_NAME_S());
			e2.setName_K(e.getKMT_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");

		KMK_TK_MST entity = dao.getKMK_TK_MST(kaiCode, code);

		return (entity == null ? null : entity.getKMT_NAME_S());
	}

	/**
	 * �Ȗڑ̌n�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[�h
	 * @param strKmtCode �Ȗڑ̌n�R�[�h
	 * @param strKmtName �Ȗڑ̌n����
	 * @param strKmtName_K �Ȗڑ̌n������
	 * @param args ���̑��p�����[�^
	 * @return �Ȗڑ̌n�}�X�^���X�g
	 */
	public List searchKmtMstData(String strKaiCode, String strKmtCode, String strKmtName, String strKmtName_K,
		String... args) {

		// �f�[�^���擾����B
		List lstResult = dao.searchKmtMstData(strKaiCode, strKmtCode, strKmtName, strKmtName_K, args[0], args[1]);

		return lstResult;
	}

	/**
	 * ��{�Ȗڑ̌n���擾����
	 * 
	 * @param conditionMap �p�����[�^�[
	 * @return Map
	 */
	public Map getItemSystemValue(Map conditionMap) {
		// �߂�Map
		Map resultMap = new HashMap();
		// ��ЃR�[�h���擾����
		String strKaiCode = Util.avoidNull(conditionMap.get("KAI_CODE"));
		// ��{�Ȗڑ̌n�R�[�h���擾����
		String strItemSystemCode = Util.avoidNull(conditionMap.get("ITEM_SYSTEM_CODE"));

		// ��{�Ȗڑ̌nList���擾����
		KMK_TK_MST kmtMst = dao.getKmkTkMst(strKaiCode, strItemSystemCode);
		if (kmtMst != null) {
			// ��{�Ȗڑ̌n�R�[�h���擾����
			resultMap.put("ITEM_SYSTEM_CODE", kmtMst.getKMT_CODE());
			// ��{�Ȗڑ̌n�����擾����
			resultMap.put("ITEM_SYSTEM_NAME", kmtMst.getKMT_NAME_S());
		}
		// �߂�
		return resultMap;
	}
}
