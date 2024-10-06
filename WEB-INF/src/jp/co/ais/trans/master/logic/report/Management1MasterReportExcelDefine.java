package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �Ǘ��P�}�X�^��Excel��`�N���X
 */
public class Management1MasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0180";
	}

	public String getSheetName() {
		// �Ǘ��P�}�X�^��Ԃ�
		return "C02343";
	}

	public String[] getHeaderTexts() {
		CMP_MST info = this.getCompanyInfo(getKaiCode());
		// ����R�[�h
		String lang = getLangCode();
		// �Ǘ��P�� �{ �R�[�h
		String code = String.valueOf(info.getKNR_NAME_1()) + getWord("C00174", lang);
		// �Ǘ��P�� �{ ����
		String name = String.valueOf(info.getKNR_NAME_1()) + getWord("C00518", lang);
		// �Ǘ��P�� �{ ����
		String nameS = String.valueOf(info.getKNR_NAME_1()) + getWord("C00548", lang);
		// �Ǘ��P�� �{ ��������
		String nameK = String.valueOf(info.getKNR_NAME_1()) + getWord("C00160", lang);
		// �^�C�g����Ԃ�
		return new String[] { "C00596", code, name, nameS, nameK, "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 10, 10, 20, 20, 40, 6, 6, };
	}

	public List convertDataToList(Object dto, String langCode) {
		KNR1_MST entity = (KNR1_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// [%�Ǘ����̂P%]�R�[�h
		list.add(entity.getKNR_CODE_1());
		// [%�Ǘ����̂P%]����
		list.add(entity.getKNR_NAME_1());
		// [%�Ǘ����̂P%]����
		list.add(entity.getKNR_NAME_S_1());
		// [%�Ǘ����̂P%]��������
		list.add(entity.getKNR_NAME_K_1());
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}

	/**
	 * @param kaiCode
	 * @return CMP_MST
	 */
	public CMP_MST getCompanyInfo(String kaiCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyControlLogic logic = (CompanyControlLogic) container.getComponent(CompanyControlLogic.class);

		CMP_MST map = null;
		// ���ʂ��擾����B
		map = (CMP_MST) logic.findOne(kaiCode);
		return map;
	}
}
