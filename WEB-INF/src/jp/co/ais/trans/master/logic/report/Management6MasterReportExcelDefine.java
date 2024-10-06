package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �Ǘ�6�}�X�^��Excel��`�N���X
 */
public class Management6MasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0230";
	}

	public String getSheetName() {
		// �Ǘ��U�}�X�^��Ԃ�
		return "C02348";
	}

	public String[] getHeaderTexts() {
		CMP_MST info = this.getCompanyInfo(getKaiCode());
		// ����R�[�h
		String lang = getLangCode();
		// �Ǘ��U�� �{ �R�[�h
		String code = String.valueOf(info.getKNR_NAME_6()) + getWord("C00174", lang);
		// �Ǘ��U�� �{ ����
		String name = String.valueOf(info.getKNR_NAME_6()) + getWord("C00518", lang);
		// �Ǘ��U�� �{ ����
		String nameS = String.valueOf(info.getKNR_NAME_6()) + getWord("C00548", lang);
		// �Ǘ��U�� �{ ��������
		String nameK = String.valueOf(info.getKNR_NAME_6()) + getWord("C00160", lang);
		// �^�C�g����Ԃ�
		return new String[] { "C00596", code, name, nameS, nameK, "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 10, 10, 20, 20, 40, 6, 6, };
	}

	public List convertDataToList(Object dto, String langCode) {
		KNR6_MST entity = (KNR6_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// [%�Ǘ�����6%]�R�[�h
		list.add(entity.getKNR_CODE_6());
		// [%�Ǘ�����6%]����
		list.add(entity.getKNR_NAME_6());
		// [%�Ǘ�����6%]����
		list.add(entity.getKNR_NAME_S_6());
		// [%�Ǘ�����6%]��������
		list.add(entity.getKNR_NAME_K_6());
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
