package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ��ЃR���g���[���}�X�^��Excel��`�N���X
 */
public class CompanyControlMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0030";
	}

	public String getSheetName() {
		// ��ЃR���g���[���}�X�^��Ԃ�
		return "C00910";
	}

	public String[] getHeaderTexts() {

		if (!"MG0031".equals(Util.avoidNull(fileName))) {
			// �^�C�g����Ԃ�
			return new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936", "C00937", "C00938",
					"C00939", "C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707", "C00708", "C00943",
					"C00944", "C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714", "C00715", "C00224",
					"C01248", "C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083" };
		}

		String strDirectKbn = getWord("C10547", getLangCode()) + getWord("C00017", getLangCode())
			+ getWord("C00127", getLangCode());
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936", "C00937", "C00938", "C00939",
				"C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707", "C00708", "C00943", "C00944",
				"C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714", "C00715", "C00224", "C01248",
				"C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083", strDirectKbn };
	}

	public short[] getColumnWidths() {

		if (!"MG0031".equals(Util.avoidNull(fileName))) {
			// �񕝂�Ԃ�
			return new short[] { 10, 10, 10, 8, 10, 6, 6, 6, 6, 6, 6, 10, 10, 10, 10, 10, 10, 13, 13, 13, 13, 13, 13,
					2, 10, 10, 10, 15, 10, 10, 10, 10, 10, 14, 18, 18 };
		}

		// �񕝂�Ԃ�
		return new short[] { 10, 10, 10, 8, 10, 6, 6, 6, 6, 6, 6, 10, 10, 10, 10, 10, 10, 13, 13, 13, 13, 13, 13, 2,
				10, 10, 10, 15, 10, 10, 10, 10, 10, 14, 18, 18, 14 };
	}

	private Map mapCheck;

	private Map mapCheck1;

	private Map mapCheck2;

	private Map mapCmpHmKbn;

	private Map mapJid;

	private Map mapRadio;

	private String fileName;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param fileName �v���O�����h�c
	 */
	public CompanyControlMasterReportExcelDefine(String fileName) {
		this.fileName = fileName;

		// 0:�g�p���Ȃ� 1:�g�p����
		mapCheck = new HashMap();
		mapCheck.put(0, "C00282");
		mapCheck.put(1, "C00281");

		// ������̏����l
		mapCheck1 = new HashMap();
		mapCheck1.put(0, "C02367");
		mapCheck1.put(1, "C02368");

		// ���ꏳ�F�׸�,�o�����F�׸�
		mapCheck2 = new HashMap();
		mapCheck2.put(0, "C02099");
		mapCheck2.put(1, "C02100");

		// ���v���׋敪1-3
		mapCmpHmKbn = new HashMap();
		mapCmpHmKbn.put(0, "C00282");
		mapCmpHmKbn.put(1, "C02160");
		mapCmpHmKbn.put(2, "C02161");
		mapCmpHmKbn.put(3, "C00446");

		// �����ݒ荀�ڂP-3
		mapJid = new HashMap();
		mapJid.put("0", "C00412");
		mapJid.put("1", "C02162");
		mapJid.put("2", "C02163");
		mapJid.put("3", "C02164");
		mapJid.put("4", "C02165");
		mapJid.put("5", "C00528");
		mapJid.put("6", "C00467");
		mapJid.put("7", "C00980");
		mapJid.put("8", "C00596");

		// 0:�؎� 1:�؏� 2:�l�̌ܓ�
		mapRadio = new HashMap();
		mapRadio.put(0, "C00121");
		mapRadio.put(1, "C00120");
		mapRadio.put(2, "C00215");
	}

	public List convertDataToList(Object dto, String langCode) {
		CMP_MST entity = (CMP_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �Ȗږ���
		list.add(entity.getCMP_KMK_NAME());
		// �⏕�Ȗږ���
		list.add(entity.getCMP_HKM_NAME());
		// ����ȖڊǗ�
		list.add(new AlignString(getWord(entity.getCMP_UKM_KBN(), mapCheck, langCode), AlignString.CENTER));
		// ����Ȗږ���
		list.add(entity.getCMP_UKM_NAME());
		// �Ǘ��敪1
		list.add(new AlignString(getWord(entity.getKNR_KBN_1(), mapCheck, langCode), AlignString.CENTER));
		// �Ǘ��敪2
		list.add(new AlignString(getWord(entity.getKNR_KBN_2(), mapCheck, langCode), AlignString.CENTER));
		// �Ǘ��敪3
		list.add(new AlignString(getWord(entity.getKNR_KBN_3(), mapCheck, langCode), AlignString.CENTER));
		// �Ǘ��敪4
		list.add(new AlignString(getWord(entity.getKNR_KBN_4(), mapCheck, langCode), AlignString.CENTER));
		// �Ǘ��敪5
		list.add(new AlignString(getWord(entity.getKNR_KBN_5(), mapCheck, langCode), AlignString.CENTER));
		// �Ǘ��敪6
		list.add(new AlignString(getWord(entity.getKNR_KBN_6(), mapCheck, langCode), AlignString.CENTER));
		// �Ǘ�����1
		list.add(entity.getKNR_NAME_1());
		// �Ǘ�����2
		list.add(entity.getKNR_NAME_2());
		// �Ǘ�����3
		list.add(entity.getKNR_NAME_3());
		// �Ǘ�����4
		list.add(entity.getKNR_NAME_4());
		// �Ǘ�����5
		list.add(entity.getKNR_NAME_5());
		// �Ǘ�����6
		list.add(entity.getKNR_NAME_6());
		// ���v���׋敪1
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_1(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// ���v���׋敪2
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_2(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// ���v���׋敪3
		list.add(new AlignString(getWord(entity.getCMP_HM_KBN_3(), mapCmpHmKbn, langCode), AlignString.CENTER));
		// ���v���ז���1
		list.add(entity.getCMP_HM_NAME_1());
		// ���v���ז���2
		list.add(entity.getCMP_HM_NAME_2());
		// ���v���ז���3
		list.add(entity.getCMP_HM_NAME_3());
		// ����
		list.add(entity.getCMP_KISYU());
		// �����ݒ荀�ڂP
		list.add(getWord(entity.getJID_1(), mapJid, langCode));
		// �����ݒ荀��2
		list.add(getWord(entity.getJID_2(), mapJid, langCode));
		// �����ݒ荀��3
		list.add(getWord(entity.getJID_3(), mapJid, langCode));
		// �����̔ԕ�����
		String autoNoKeta = String.valueOf(entity.getAUTO_NO_KETA());
		if ("0".equals(autoNoKeta)) {
			list.add("");
		} else {
			list.add(entity.getAUTO_NO_KETA());
		}
		// �`�[����敪
		list.add(new AlignString(getWord(entity.getPRINT_KBN(), mapCheck, langCode), AlignString.CENTER));
		// ������̏����l
		list.add(getWord(entity.getPRINT_DEF(), mapCheck1, langCode));
		// ���ꏳ�F�׸�
		list.add(getWord(entity.getCMP_G_SHONIN_FLG(), mapCheck2, langCode));
		// �o�����F�׸�
		list.add(getWord(entity.getCMP_K_SHONIN_FLG(), mapCheck2, langCode));
		// �{�M�ʉ݃R�[�h
		list.add(new AlignString(entity.getCUR_CODE(), AlignString.CENTER));
		// ���[�g���Z�[������
		list.add(getWord(entity.getRATE_KBN(), mapRadio, langCode));
		// �������Œ[������
		list.add(getWord(entity.getKU_KBN(), mapRadio, langCode));
		// ��������Œ[������
		list.add(getWord(entity.getKB_KBN(), mapRadio, langCode));
		if ("MG0031".equals(Util.avoidNull(fileName))) {
			// ���ڈ���敪
			list.add(getWord(0, mapCheck1, langCode));
		}

		return list;
	}
}
