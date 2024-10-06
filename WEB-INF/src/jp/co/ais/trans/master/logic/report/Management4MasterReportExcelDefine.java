package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �Ǘ�4�}�X�^��Excel��`�N���X
 */
public class Management4MasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0210";
	}

	public String getSheetName() {
		// �Ǘ��S�}�X�^��Ԃ�
		return "C02346";
	}

	public String[] getHeaderTexts() {
		CMP_MST info = this.getCompanyInfo(getKaiCode());
		// ����R�[�h
		String lang = getLangCode();
		// �Ǘ��S�� �{ �R�[�h
		String code = String.valueOf(info.getKNR_NAME_4()) + getWord("C00174", lang);
		// �Ǘ��S�� �{ ����
		String name = String.valueOf(info.getKNR_NAME_4()) + getWord("C00518", lang);
		// �Ǘ��S�� �{ ����
		String nameS = String.valueOf(info.getKNR_NAME_4()) + getWord("C00548", lang);
		// �Ǘ��S�� �{ ��������
		String nameK = String.valueOf(info.getKNR_NAME_4()) + getWord("C00160", lang);
		// �^�C�g����Ԃ�
		return new String[] { "C00596", code, name, nameS, nameK, "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 10, 10, 20, 20, 40, 6, 6, };
	}

	public List convertDataToList(Object dto, String langCode) {
		KNR4_MST entity = (KNR4_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// [%�Ǘ�����4%]�R�[�h
		list.add(entity.getKNR_CODE_4());
		// [%�Ǘ�����4%]����
		list.add(entity.getKNR_NAME_4());
		// [%�Ǘ�����4%]����
		list.add(entity.getKNR_NAME_S_4());
		// [%�Ǘ�����4%]��������
		list.add(entity.getKNR_NAME_K_4());
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
