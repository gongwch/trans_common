package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �⏕�Ȗڃ}�X�^��Excel��`�N���X
 */
public class SubItemMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0090";
	}

	public String getSheetName() {
		return "C02353";
	}

	public String[] getHeaderTexts() {
		return new String[] { "C00596", "C00572", "C00602", "C00701", "C00739", "C00740", "C01264", "C00573", "C01272",
				"C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102", "C01094", "C01134", "C01284",
				"C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036", "C01288", "C01289", "C01290",
				"C01282", "C01088", "C01223", "C01301", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		return new short[] { 0, 10, 10, 40, 20, 40, 5, 10, 15, 15, 18, 21, 21, 21, 21, 21, 21, 13, 13, 12, 12, 12, 12,
				12, 12, 12, 14, 14, 14, 15, 15, 12, 12, 6, 6, };
	}

	private Map triCodeFlgMap;

	private Map mapUkmKbn;

	private Map mapGlFlg1;

	private Map mapGlFlg2;

	private Map mapGlFlg3;

	private Map mapApFlg1;

	private Map mapApFlg2;

	private Map mapArFlg1;

	private Map mapArFlg2;

	private Map mapFaFlg1;

	private Map mapFaFlg2;

	private Map mapHasFlg;

	private Map mapEmpCodeFlg;

	private Map mapKnrFlg1;

	private Map mapKnrFlg2;

	private Map mapKnrFlg3;

	private Map mapKnrFlg4;

	private Map mapKnrFlg5;

	private Map mapKnrFlg6;

	private Map mapHmFlg1;

	private Map mapHmFlg2;

	private Map mapHmFlg3;

	private Map mapUriZeiFlg;

	private Map mapSirZeiFlg;

	private Map mapMcrFlg;

	/**
	 * 
	 */
	public SubItemMasterReportExcelDefine() {
		// ����敪 - 0:�� 1:�L
		mapUkmKbn = new HashMap();
		mapUkmKbn.put(0, "C02155");
		mapUkmKbn.put(1, "C02156");
		// �������̓t���O - 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ�
		triCodeFlgMap = new HashMap();
		triCodeFlgMap.put(0, "C01279");
		triCodeFlgMap.put(2, "C00401");
		triCodeFlgMap.put(3, "C00203");
		triCodeFlgMap.put(4, "C02122");
		// �����`�[�����׸� - 0:���͕s�� 1:���͉�
		mapGlFlg1 = new HashMap();
		mapGlFlg1.put(0, "C01279");
		mapGlFlg1.put(1, "C01276");
		// �o���`�[�����׸� - 0:���͕s�� 1:���͉�
		mapGlFlg2 = new HashMap();
		mapGlFlg2.put("0", "C01279");
		mapGlFlg2.put("1", "C01276");
		// �U�֓`�[�����׸� - 0:���͕s�� 1:���͉�
		mapGlFlg3 = new HashMap();
		mapGlFlg3.put("0", "C01279");
		mapGlFlg3.put("1", "C01276");
		// �o��Z�`�[�����׸�- 0:���͕s�� 1:���͉�
		mapApFlg1 = new HashMap();
		mapApFlg1.put("0", "C01279");
		mapApFlg1.put("1", "C01276");
		// �������`�[�����׸�- 0:���͕s�� 1:���͉�
		mapApFlg2 = new HashMap();
		mapApFlg2.put("0", "C01279");
		mapApFlg2.put("1", "C01276");
		// ���v��`�[�����׸�- 0:���͕s�� 1:���͉�
		mapArFlg1 = new HashMap();
		mapArFlg1.put("0", "C01279");
		mapArFlg1.put("1", "C01276");
		// �������`�[�����׸�- 0:���͕s�� 1:���͉�
		mapArFlg2 = new HashMap();
		mapArFlg2.put("0", "C01279");
		mapArFlg2.put("1", "C01276");
		// ���Y�v��`�[�����׸�- 0:���͕s�� 1:���͉�
		mapFaFlg1 = new HashMap();
		mapFaFlg1.put("0", "C01279");
		mapFaFlg1.put("1", "C01276");
		// �x���˗��`�[�����׸� - 0:���͕s�� 1:���͉�
		mapFaFlg2 = new HashMap();
		mapFaFlg2.put("0", "C01279");
		mapFaFlg2.put("1", "C01276");
		// �����������׸� - 0:���͕s�� 1:���͉�
		mapHasFlg = new HashMap();
		mapHasFlg.put(0, "C01279");
		mapHasFlg.put(1, "C01276");
		// �Ј����̓t���O- 0:���͕s�� 1:���͉�
		mapEmpCodeFlg = new HashMap();
		mapEmpCodeFlg.put(0, "C01279");
		mapEmpCodeFlg.put(1, "C01276");
		// �Ǘ��P���̓t���O- 0:���͕s�� 1:���͉�
		mapKnrFlg1 = new HashMap();
		mapKnrFlg1.put("0", "C01279");
		mapKnrFlg1.put("1", "C01276");
		// �Ǘ�2���̓t���O- 0:���͕s�� 1:���͉�
		mapKnrFlg2 = new HashMap();
		mapKnrFlg2.put("0", "C01279");
		mapKnrFlg2.put("1", "C01276");
		// �Ǘ�3���̓t���O- 0:���͕s�� 1:���͉�
		mapKnrFlg3 = new HashMap();
		mapKnrFlg3.put("0", "C01279");
		mapKnrFlg3.put("1", "C01276");
		// �Ǘ�4���̓t���O- 0:���͕s�� 1:���͉�
		mapKnrFlg4 = new HashMap();
		mapKnrFlg4.put("0", "C01279");
		mapKnrFlg4.put("1", "C01276");
		// �Ǘ�5���̓t���O- 0:���͕s�� 1:���͉�
		mapKnrFlg5 = new HashMap();
		mapKnrFlg5.put("0", "C01279");
		mapKnrFlg5.put("1", "C01276");
		// �Ǘ�6���̓t���O- 0:���͕s�� 1:���͉�
		mapKnrFlg6 = new HashMap();
		mapKnrFlg6.put("0", "C01279");
		mapKnrFlg6.put("1", "C01276");
		// ���v1���̓t���O- 0:���͕s�� 1:���͉�
		mapHmFlg1 = new HashMap();
		mapHmFlg1.put("0", "C01279");
		mapHmFlg1.put("1", "C01276");
		// ���v2���̓t���O- 0:���͕s�� 1:���͉�
		mapHmFlg2 = new HashMap();
		mapHmFlg2.put("0", "C01279");
		mapHmFlg2.put("1", "C01276");
		// ���v3���̓t���O- 0:���͕s�� 1:���͉�
		mapHmFlg3 = new HashMap();
		mapHmFlg3.put("0", "C01279");
		mapHmFlg3.put("1", "C01276");
		// ����ېœ��̓t���O- 0:���͕s�� 1:���͉�
		mapUriZeiFlg = new HashMap();
		mapUriZeiFlg.put("0", "C01279");
		mapUriZeiFlg.put("1", "C01276");
		// �d���ېœ��̓t���O- 0:���͕s�� 1:���͉�
		mapSirZeiFlg = new HashMap();
		mapSirZeiFlg.put("0", "C01279");
		mapSirZeiFlg.put("1", "C01276");
		// ���ʉݓ��̓t���O- 0:���͕s�� 1:���͉�
		mapMcrFlg = new HashMap();
		mapMcrFlg.put("0", "C01279");
		mapMcrFlg.put("1", "C01276");
		// //�]���֑Ώۃt���O - 0:���Ȃ� 1:����
		// excFlgMap = new HashMap();
		// excFlgMap.put("0", "C02099");
		// excFlgMap.put("1", "C02100");
	}

	public List convertDataToList(Object dto, String langCode) {
		HKM_MST entity = (HKM_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �ȖڃR�[�h
		list.add(entity.getKMK_CODE());
		// �⏕�ȖڃR�[�h
		list.add(entity.getHKM_CODE());
		// �⏕�Ȗږ���
		list.add(entity.getHKM_NAME());
		// �⏕�Ȗڗ���
		list.add(entity.getHKM_NAME_S());
		// �⏕�Ȗڌ�������
		list.add(entity.getHKM_NAME_K());
		// ����敪
		list.add(new AlignString(getWord(entity.getUKM_KBN(), mapUkmKbn, langCode), AlignString.CENTER));
		// ����ŃR�[�h
		list.add(entity.getZEI_CODE());
		// �����`�[�����׸�
		list.add(getWord(entity.getGL_FLG_1(), mapGlFlg1, langCode));
		// �o���`�[�����׸�
		list.add(getWord(entity.getGL_FLG_2(), mapGlFlg1, langCode));
		// �U�֓`�[�����׸�
		list.add(getWord(entity.getGL_FLG_3(), mapGlFlg1, langCode));
		// �o��Z�`�[�����׸�
		list.add(getWord(entity.getAP_FLG_1(), mapGlFlg1, langCode));
		// �������`�[�����׸�
		list.add(getWord(entity.getAP_FLG_2(), mapGlFlg1, langCode));
		// ���v��`�[�����׸�
		list.add(getWord(entity.getAR_FLG_1(), mapGlFlg1, langCode));
		// �������`�[�����׸�
		list.add(getWord(entity.getAR_FLG_2(), mapGlFlg1, langCode));
		// ���Y�v��`�[�����׸�
		list.add(getWord(entity.getFA_FLG_1(), mapGlFlg1, langCode));
		// �x���˗��`�[�����׸�
		list.add(getWord(entity.getFA_FLG_2(), mapGlFlg1, langCode));
		// �������̓t���O
		list.add(getWord(entity.getTRI_CODE_FLG(), triCodeFlgMap, langCode));
		// ���������̓t���O
		list.add(getWord(entity.getHAS_FLG(), mapHasFlg, langCode));
		// �Ј����̓t���O
		list.add(getWord(entity.getEMP_CODE_FLG(), mapEmpCodeFlg, langCode));
		// �Ǘ��P���̓t���O
		list.add(getWord(entity.getKNR_FLG_1(), mapEmpCodeFlg, langCode));
		// �Ǘ��Q���̓t���O
		list.add(getWord(entity.getKNR_FLG_2(), mapEmpCodeFlg, langCode));
		// �Ǘ��R���̓t���O
		list.add(getWord(entity.getKNR_FLG_3(), mapEmpCodeFlg, langCode));
		// �Ǘ��S���̓t���O
		list.add(getWord(entity.getKNR_FLG_4(), mapEmpCodeFlg, langCode));
		// �Ǘ��T���̓t���O
		list.add(getWord(entity.getKNR_FLG_5(), mapEmpCodeFlg, langCode));
		// �Ǘ��U���̓t���O
		list.add(getWord(entity.getKNR_FLG_6(), mapEmpCodeFlg, langCode));
		// ���v1���̓t���O
		list.add(getWord(entity.getHM_FLG_1(), mapEmpCodeFlg, langCode));
		// ���v2���̓t���O
		list.add(getWord(entity.getHM_FLG_2(), mapEmpCodeFlg, langCode));
		// ���v3���̓t���O
		list.add(getWord(entity.getHM_FLG_3(), mapEmpCodeFlg, langCode));
		// ����ېœ��̓t���O
		list.add(getWord(entity.getURI_ZEI_FLG(), mapEmpCodeFlg, langCode));
		// �d���ېœ��̓t���O
		list.add(getWord(entity.getSIR_ZEI_FLG(), mapEmpCodeFlg, langCode));
		// ���ʉݓ��̓t���O
		list.add(getWord(entity.getMCR_FLG(), mapEmpCodeFlg, langCode));
		// �]���֑Ώۃt���O
		list.add(entity.getEXC_FLG() == 0 ? getWord("C02099", langCode) : getWord("C02100", langCode));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
