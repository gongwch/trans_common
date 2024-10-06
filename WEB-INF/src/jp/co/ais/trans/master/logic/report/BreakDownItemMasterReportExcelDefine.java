package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ����Ȗڃ}�X�^��Excel��`�N���X
 */
public class BreakDownItemMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0100";
	}

	public String getSheetName() {
		// ����Ȗڃ}�X�^��Ԃ�
		return "C02320";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00572", "C00602", "C00603", "C00702", "C01594", "C01593", "C00573", "C01272",
				"C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102", "C01094", "C01223", "C01301",
				"C01134", "C01284", "C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036", "C01288",
				"C01289", "C01290", "C01282", "C01088", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 10, 10, 40, 20, 40, 10, 15, 15, 15, 21, 21, 20, 20, 20, 20, 12, 12, 12, 12, 10, 12,
				12, 12, 12, 12, 13, 14, 14, 14, 15, 15, 6, 6 };
	}

	private Map mapCheck;

	private Map mapCheck1;

	private Map triCodeFlgMap;

	/**
	 * 
	 */
	public BreakDownItemMasterReportExcelDefine() {
		// 0:���͕s�� 1:���͉�
		mapCheck = new HashMap();
		mapCheck.put(0, "C01279");
		mapCheck.put(1, "C01276");

		// 0:���͕K�{ 1:���͉�
		mapCheck1 = new HashMap();
		mapCheck1.put(0, "C01279");
		mapCheck1.put(1, "C02371");

		triCodeFlgMap = new HashMap();
		triCodeFlgMap.put(0, "C01279");
		triCodeFlgMap.put(2, "C00401");
		triCodeFlgMap.put(3, "C00203");
		triCodeFlgMap.put(4, "C02122");
	}

	public List convertDataToList(Object dto, String langCode) {
		UKM_MST entity = (UKM_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �ȖڃR�[�h
		list.add(entity.getKMK_CODE());
		// �⏕�ȖڃR�[�h
		list.add(entity.getHKM_CODE());
		// ����ȖڃR�[�h
		list.add(entity.getUKM_CODE());
		// ����Ȗږ���
		list.add(entity.getUKM_NAME());
		// ����Ȗڗ���
		list.add(entity.getUKM_NAME_S());
		// ����Ȗڌ�������
		list.add(entity.getUKM_NAME_K());
		// ����ŃR�[�h
		list.add(entity.getZEI_CODE());
		// �����`�[�����׸�
		list.add(getWord(entity.getGL_FLG_1(), mapCheck, langCode));
		// �o���`�[�����׸�
		list.add(getWord(entity.getGL_FLG_2(), mapCheck, langCode));
		// �U�֓`�[�����׸�
		list.add(getWord(entity.getGL_FLG_3(), mapCheck, langCode));
		// �o��Z�`�[�����׸�
		list.add(getWord(entity.getAP_FLG_1(), mapCheck, langCode));
		// �������`�[�����׸�
		list.add(getWord(entity.getAP_FLG_2(), mapCheck, langCode));
		// ���v��`�[�����׸�
		list.add(getWord(entity.getAR_FLG_1(), mapCheck, langCode));
		// �������`�[�����׸�
		list.add(getWord(entity.getAR_FLG_2(), mapCheck, langCode));
		// ���Y�v��`�[�����׸�
		list.add(getWord(entity.getFA_FLG_1(), mapCheck, langCode));
		// �x���˗��`�[�����׸�
		list.add(getWord(entity.getFA_FLG_2(), mapCheck, langCode));
		// ���ʉݓ��̓t���O
		list.add(getWord(entity.getMCR_FLG(), mapCheck, langCode));
		// �]���֑Ώۃt���O
		list.add(getWord(entity.getEXC_FLG(), mapCheck, langCode));
		// �������̓t���O
		list.add(getWord(entity.getTRI_CODE_FLG(), triCodeFlgMap, langCode));
		// ���������̓t���O
		list.add(getWord(entity.getHAS_FLG(), mapCheck, langCode));
		// �Ј����̓t���O
		list.add(getWord(entity.getEMP_CODE_FLG(), mapCheck1, langCode));
		// �Ǘ��P���̓t���O
		list.add(getWord(entity.getKNR_FLG_1(), mapCheck1, langCode));
		// �Ǘ�2���̓t���O
		list.add(getWord(entity.getKNR_FLG_2(), mapCheck1, langCode));
		// �Ǘ�3���̓t���O
		list.add(getWord(entity.getKNR_FLG_3(), mapCheck1, langCode));
		// �Ǘ�4���̓t���O
		list.add(getWord(entity.getKNR_FLG_4(), mapCheck1, langCode));
		// �Ǘ�5���̓t���O
		list.add(getWord(entity.getKNR_FLG_5(), mapCheck1, langCode));
		// �Ǘ�6���̓t���O
		list.add(getWord(entity.getKNR_FLG_6(), mapCheck1, langCode));
		// ���v1���̓t���O
		list.add(getWord(entity.getHM_FLG_1(), mapCheck, langCode));
		// ���v2���̓t���O
		list.add(getWord(entity.getHM_FLG_2(), mapCheck, langCode));
		// ���v3���̓t���O
		list.add(getWord(entity.getHM_FLG_3(), mapCheck, langCode));
		// ����ېœ��̓t���O
		list.add(getWord(entity.getURI_ZEI_FLG(), mapCheck, langCode));
		// �d���ېœ��̓t���O
		list.add(getWord(entity.getSIR_ZEI_FLG(), mapCheck, langCode));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
