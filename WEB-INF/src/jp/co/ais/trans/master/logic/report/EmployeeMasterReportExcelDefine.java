package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �Ј��}�X�^��Excel��`�N���X
 */
public class EmployeeMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0160";
	}

	public String getSheetName() {
		// �Ј��}�X�^��Ԃ�
		return "C00913";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00697", "C00807", "C00808", "C00809", "C00811", "C00812", "C00813", "C00471",
				"C01068", "C00810", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 40, 20, 40, 10, 10, 10, 12, 30, 14, 6, 6 };
	}

	private Map empKozaKbnMap;

	/**
	 * 
	 */
	public EmployeeMasterReportExcelDefine() {
		empKozaKbnMap = new HashMap();
		empKozaKbnMap.put(1, "C00465");
		empKozaKbnMap.put(2, "C02154");
	}

	public List convertDataToList(Object dto, String langCode) {
		EMP_MST entity = (EMP_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �Ј��R�[�h
		list.add(entity.getEMP_CODE());
		// �Ј�����
		list.add(entity.getEMP_NAME());
		// �Ј�����
		list.add(entity.getEMP_NAME_S());
		// �Ј���������
		list.add(entity.getEMP_NAME_K());
		// �U����s�R�[�h
		list.add(entity.getEMP_FURI_BNK_CODE());
		// �U���x�X�R�[�h
		list.add(entity.getEMP_FURI_STN_CODE());
		// �U�������ԍ�
		list.add(entity.getEMP_YKN_NO());
		// �U�������a�����
		list.add(getWord(entity.getEMP_KOZA_KBN(), empKozaKbnMap, langCode));
		// �������`�J�i
		list.add(entity.getEMP_YKN_KANA());
		// �U�o��s�����R�[�h
		list.add(entity.getEMP_CBK_CODE());
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}

}
