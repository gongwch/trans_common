package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �Ȗڃ}�X�^��Excel��`�N���X
 */
public class ItemMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0080";
	}

	public String getSheetName() {
		// �Ȗڃ}�X�^��Ԃ�
		return "C02342";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00572", "C00700", "C00730", "C00731", "C01148", "C01007", "C01226", "C01314",
				"C02066", "C00573", "C00968", "C00959", "C00960", "C00962", "C01134", "C01284", "C01217", "C02078",
				"C01301", "C01272", "C01155", "C01188", "C01049", "C01083", "C01079", "C01081", "C01102", "C01094",
				"C01223", "C01120", "C01026", "C01028", "C01030", "C01032", "C01034", "C01036", "C01288", "C01289",
				"C01290", "C01282", "C01088", "C02076", "C02077", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 40, 20, 40, 4, 5, 4, 4, 7, 10, 11, 11, 11, 11, 12, 12, 13, 18, 12, 15, 15, 15, 21,
				21, 21, 21, 21, 21, 12, 10, 12, 12, 12, 12, 12, 12, 14, 14, 14, 15, 15, 9, 9, 6, 6 };
	}

	private Map kmkShuMap;

	private Map hkmKbnMap;

	private Map kmkCntGlMap;

	private Map kmkCntApMap;

	private Map kmkCntArMap;

	private Map triCodeFlgMap;

	private Map kmkCntBgMap;

	private Map kmkCntSousaiMap;

	/**
	 * 
	 */
	public ItemMasterReportExcelDefine() {

		// �Ȗڎ�� - 0:�ݎ؉Ȗ� 1:���v�Ȗ� 2:���v�����Ȗ� 3:���������Ȗ� 9:���v�Ȗ�
		kmkShuMap = new HashMap();
		kmkShuMap.put("0", "C02108");
		kmkShuMap.put("1", "C02109");
		kmkShuMap.put("2", "C02110");
		kmkShuMap.put("3", "C02111");
		kmkShuMap.put("9", "C02112");
		// �⏕�敪 - 0:�Ȃ� 1:����
		hkmKbnMap = new HashMap();
		hkmKbnMap.put(0, "C00412");
		hkmKbnMap.put(1, "C00006");
		// �f�k�Ȗڐ���敪 - 00:�ʏ� 01:�O���J�z���v 04:�����Ȗ� 05:����Ȗ� 06:�ב֊��Z�����v 07:������ 08:�ב֍��� 09:�ב֍��v
		kmkCntGlMap = new HashMap();
		kmkCntGlMap.put("00", "C00372");
		kmkCntGlMap.put("01", "C02113");
		kmkCntGlMap.put("04", "C02114");
		kmkCntGlMap.put("05", "C02115");
		kmkCntGlMap.put("06", "C02116");
		kmkCntGlMap.put("07", "C02117");
		kmkCntGlMap.put("08", "C00995");
		kmkCntGlMap.put("09", "C01783");
		// AP�Ȗڐ���敪 - 00:�ʏ� 01:���Ǘ��Ȗ� 02�F�]�ƈ������Ȗ�
		kmkCntApMap = new HashMap();
		kmkCntApMap.put("00", "C00372");
		kmkCntApMap.put("01", "C02118");
		kmkCntApMap.put("02", "C02119");
		// AR�Ȗڐ���敪 - 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
		kmkCntArMap = new HashMap();
		kmkCntArMap.put("00", "C00372");
		kmkCntArMap.put("01", "C02120");
		kmkCntArMap.put("02", "C02121");
		// �������̓t���O - 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ�
		triCodeFlgMap = new HashMap();
		triCodeFlgMap.put(0, "C01279");
		triCodeFlgMap.put(2, "C00401");
		triCodeFlgMap.put(3, "C00203");
		triCodeFlgMap.put(4, "C02122");
		// BG�Ȗڐ���敪 - 00:�ʏ� 11:�\�Z�Ǘ��Ώ�
		kmkCntBgMap = new HashMap();
		kmkCntBgMap.put("00", "C00372");
		kmkCntBgMap.put("11", "C02125");
		// ���E�Ȗڐ���敪 - 0:���Ȃ� 1:����
		kmkCntSousaiMap = new HashMap();
		kmkCntSousaiMap.put("0", "C02099");
		kmkCntSousaiMap.put("1", "C02100");
	}

	public List convertDataToList(Object dto, String langCode) {
		KMK_MST entity = (KMK_MST) dto;
		List list = new ArrayList();

		boolean input = !(entity.getSUM_KBN() == 0);

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �ȖڃR�[�h
		list.add(entity.getKMK_CODE());
		// �Ȗږ���
		list.add(entity.getKMK_NAME());
		// �Ȗڗ���
		list.add(entity.getKMK_NAME_S());
		// �Ȗڌ�������
		list.add(entity.getKMK_NAME_K());
		// �W�v�敪
		String sumKbn = String.valueOf(entity.getSUM_KBN());
		if ("0".equals(sumKbn)) {
			list.add(new AlignString(getWord("C02157", langCode), AlignString.CENTER));
		} else if ("1".equals(sumKbn)) {
			list.add(new AlignString(getWord("C02158", langCode), AlignString.CENTER));
		} else {
			list.add(new AlignString(getWord("C02159", langCode), AlignString.CENTER));
		}
		// �Ȗڎ��
		list.add(getWord(entity.getKMK_SHU(), kmkShuMap, langCode));
		// �ݎ؋敪
		String dcKbn = entity.getDC_KBN() == 0 ? getWord("C01125", langCode) : getWord("C01228", langCode);
		list.add(new AlignString(dcKbn, AlignString.CENTER));
		// �⏕�敪
		String hkmKbn = getWord(entity.getHKM_KBN(), hkmKbnMap, langCode);
		list.add(new AlignString(input ? "" : hkmKbn, AlignString.CENTER));
		// �Œ蕔�庰��
		list.add(entity.getKOTEI_DEP_CODE());
		// ����ŃR�[�h
		list.add(entity.getZEI_CODE());
		// GL�Ȗڐ���敪
		String kmkCntGl = getWord(entity.getKMK_CNT_GL(), kmkCntGlMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntGl, AlignString.CENTER));
		// AP�Ȗڐ���敪
		String kmkCntAp = getWord(entity.getKMK_CNT_AP(), kmkCntApMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntAp, AlignString.CENTER));
		// AR�Ȗڐ���敪
		String kmkCntAR = getWord(entity.getKMK_CNT_AR(), kmkCntArMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntAR, AlignString.CENTER));
		// BG�Ȗڐ���敪
		String kmkCntBg = getWord(entity.getKMK_CNT_BG(), kmkCntBgMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntBg, AlignString.CENTER));
		// �������̓t���O
		String triCodeFlg = getWord(entity.getTRI_CODE_FLG(), triCodeFlgMap, langCode);
		list.add(input ? "" : triCodeFlg);
		// ���������̓t���O
		String hasFlg = entity.getHAS_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hasFlg);
		// ���E�Ȗڐ���敪
		String kmkCntSousai = getWord(entity.getKMK_CNT_SOUSAI(), kmkCntSousaiMap, langCode);
		list.add(new AlignString(input ? "" : kmkCntSousai, AlignString.CENTER));
		// BS��������敪
		String kesiKbn = entity.getKESI_KBN() == 0 ? getWord("C02099", langCode) : getWord("C02100", langCode);
		list.add(new AlignString(input ? "" : kesiKbn, AlignString.CENTER));
		// �]���֑Ώۃt���O
		String excFlg = String.valueOf(entity.getEXC_FLG());
		if ("0".equals(excFlg)) {
			list.add(input ? "" : getWord("C02099", langCode));
		} else if ("1".equals(excFlg)) {
			list.add(input ? "" : getWord("C02123", langCode));
		} else if ("2".equals(excFlg)) {
			list.add(input ? "" : getWord("C02124", langCode));
		}
		// �����`�[�����׸�
		String glFlg1 = entity.getGL_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : glFlg1);
		// �o���`�[�����׸�
		String glFlg2 = entity.getGL_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : glFlg2);
		// �U�֓`�[�����׸�
		String glFlg3 = entity.getGL_FLG_3() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : glFlg3);
		// �o��Z�`�[�����׸�
		String apFlg1 = entity.getAP_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : apFlg1);
		// �������`�[�����׸�
		String apFlg2 = entity.getAP_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : apFlg2);
		// ���v��`�[�����׸�
		String arFlg1 = entity.getAR_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : arFlg1);
		// �������`�[�����׸�
		String arFlg2 = entity.getAR_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : arFlg2);
		// ���Y�v��`�[�����׸�
		String faFlg1 = entity.getFA_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : faFlg1);
		// �x���˗��`�[�����׸�
		String faFlg2 = entity.getFA_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : faFlg2);
		// ���ʉݓ��̓t���O
		String mcrFlg = entity.getMCR_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : mcrFlg);
		// �Ј����̓t���O
		String empCodeFlg = entity.getEMP_CODE_FLG() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : empCodeFlg);
		// �Ǘ��P���̓t���O
		String knrFlg1 = entity.getKNR_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg1);
		// �Ǘ��Q���̓t���O
		String knrFlg2 = entity.getKNR_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg2);
		// �Ǘ��R���̓t���O
		String knrFlg3 = entity.getKNR_FLG_3() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg3);
		// �Ǘ��S���̓t���O
		String knrFlg4 = entity.getKNR_FLG_4() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg4);
		// �Ǘ��T���̓t���O
		String knrFlg5 = entity.getKNR_FLG_5() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg5);
		// �Ǘ��U���̓t���O
		String knrFlg6 = entity.getKNR_FLG_6() == 0 ? getWord("C01279", langCode) : getWord("C02371", langCode);
		list.add(input ? "" : knrFlg6);
		// ���v1���̓t���O
		String hmFlg1 = entity.getHM_FLG_1() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hmFlg1);
		// ���v2���̓t���O
		String hmFlg2 = entity.getHM_FLG_2() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hmFlg2);
		// ���v3���̓t���O
		String hmFlg3 = entity.getHM_FLG_3() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : hmFlg3);
		// ����ېœ��̓t���O
		String uriZeiFlg = entity.getURI_ZEI_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : uriZeiFlg);
		// �d���ېœ��̓t���O
		String sirZeiFlg = entity.getSIR_ZEI_FLG() == 0 ? getWord("C01279", langCode) : getWord("C01276", langCode);
		list.add(input ? "" : sirZeiFlg);
		// �ؕ������R�[�h
		list.add(entity.getSKN_CODE_DR());
		// �ݕ������R�[�h
		list.add(entity.getSKN_CODE_CR());
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
