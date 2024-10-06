package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ��s�����}�X�^��Excel��`�N���X
 */
public class BankAccountMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MP0030";
	}

	public String getSheetName() {
		// ��s�����}�X�^��Ԃ�
		return "C02322";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C01879", "C02145", "C00665", "C00880", "C00779", "C00781", "C02055", "C02060",
				"C00858", "C10133", "C00860", "C00861", "C01326", "C00794", "C01117", "C01122", "C00571", "C00572",
				"C00602", "C00603", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 40, 6, 40, 6, 30, 6, 30, 12, 40, 40, 70, 5, 10, 7, 7, 10, 10, 10, 10, 6, 6 };
	}

	private Map yknKbnMap;

	/**
	 * 
	 */
	public BankAccountMasterReportExcelDefine() {
		// �a�����
		yknKbnMap = new LinkedHashMap();
		yknKbnMap.put(1, "C00463");
		yknKbnMap.put(2, "C00397");
		yknKbnMap.put(3, "C00045");
		yknKbnMap.put(4, "C00368");
	}

	public List convertDataToList(Object dto, String langCode) {
		AP_CBK_MST2 entity = (AP_CBK_MST2) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// ��s�����R�[�h
		list.add(entity.getCBK_CBK_CODE());
		// ��s��������
		list.add(entity.getCBK_NAME());
		// �ʉ݃R�[�h
		//list.add(entity.getCUR_CODE());
		
		list.add(new AlignString(entity.getCUR_CODE(),AlignString.CENTER));
		// �ʉݖ���
		list.add(entity.getCUR_NAME());
		// ��s�R�[�h
		list.add(entity.getCBK_BNK_CODE());
		// ��s����
		list.add(entity.getBNK_NAME_S());
		// �x�X�R�[�h
		list.add(entity.getCBK_STN_CODE());
		// �x�X����
		list.add(entity.getBNK_STN_NAME_S());
		// �U���˗��l�R�[�h
		list.add(entity.getCBK_IRAI_EMP_CODE());
		// �U���˗��l��
		list.add(entity.getCBK_IRAI_NAME());
		// �U���˗��l���i�����j
		list.add(entity.getCBK_IRAI_NAME_J());
		// �U���˗��l���i�p���j
		list.add(entity.getCBK_IRAI_NAME_E());
		// �a�����
		list.add(getWord(entity.getCBK_YKN_KBN(), yknKbnMap, langCode));
		// �����ԍ�
		list.add(entity.getCBK_YKN_NO());
		// �Ј��e�a�敪
		list.add(new AlignString(entity.getCBK_EMP_FB_KBN() == 0 ? getWord("C02148", langCode) : getWord("C02149",
				langCode), AlignString.CENTER));
		// �ЊO�e�a�敪
		list.add(new AlignString(entity.getCBK_OUT_FB_KBN() == 0 ? getWord("C02150", langCode) : getWord("C02151",
				langCode), AlignString.CENTER));
		// �v�㕔��R�[�h
		list.add(entity.getCBK_DEP_CODE());
		// �ȖڃR�[�h
		list.add(entity.getCBK_KMK_CODE());
		// �⏕�ȖڃR�[�h
		list.add(entity.getCBK_HKM_CODE());
		// ����ȖڃR�[�h
		list.add(entity.getCBK_UKM_CODE());
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
