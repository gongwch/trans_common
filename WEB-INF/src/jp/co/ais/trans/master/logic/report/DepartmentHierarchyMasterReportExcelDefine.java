package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ����K�w�}�X�^��Excel��`�N���X
 */
public class DepartmentHierarchyMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0050";
	}

	public String getSheetName() {
		// ����K�w�}�X�^��Ԃ�
		return "C02327";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00335", "C00698", "C01739", "C00991", "C01751", "C01752", "C01753", "C01754",
				"C01755", "C01756", "C01757", "C01758", "C01759", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 0, 10, 1, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 0 };
	}

	public List convertDataToList(Object dto, String langCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		// ����}�X�^Dao ����R�[�h->���̕ϊ��Ɏg�p����B
		BMN_MSTDao bmnMstDao = (BMN_MSTDao) container.getComponent(BMN_MSTDao.class);

		DPK_MST entity = (DPK_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		String kaiCode = entity.getKAI_CODE();
		list.add(kaiCode);
		// �g�D�R�[�h
		list.add(entity.getDPK_SSK());
		// ����R�[�h
		list.add(entity.getDPK_DEP_CODE());
		// ���x��
		int dpkLvl = entity.getDPK_LVL();
		list.add(dpkLvl);
		// �Ǘ�����R�[�h���x���O
		list.add("");
		// �Ǘ�����R�[�h���x���P
		list.add(dpkLvl != 1 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_1())));
		// �Ǘ�����R�[�h���x���Q
		list.add(dpkLvl != 2 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_2())));
		// �Ǘ�����R�[�h���x���R
		list.add(dpkLvl != 3 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_3())));
		// �Ǘ�����R�[�h���x���S
		list.add(dpkLvl != 4 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_4())));
		// �Ǘ�����R�[�h���x���T
		list.add(dpkLvl != 5 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_5())));
		// �Ǘ�����R�[�h���x���U
		list.add(dpkLvl != 6 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_6())));
		// �Ǘ�����R�[�h���x���V
		list.add(dpkLvl != 7 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_7())));
		// �Ǘ�����R�[�h���x���W
		list.add(dpkLvl != 8 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_8())));
		// �Ǘ�����R�[�h���x���X
		list.add(dpkLvl != 9 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_9())));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}

	/**
	 * ����}�X�^ ����R�[�h->���̕ϊ�
	 * 
	 * @param dao
	 * @param kaiCode
	 * @param depCode
	 * @return String
	 */
	protected String convCodeToName(BMN_MSTDao dao, String kaiCode, String depCode) {

		if (dao == null) {
			return null;
		}
		BMN_MST entity = dao.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);
		if (entity == null) {
			return null;
		}

		if (entity.getDEP_NAME() != null) {
			return entity.getDEP_NAME();
		}

		return entity.getDEP_CODE();
	}
}
