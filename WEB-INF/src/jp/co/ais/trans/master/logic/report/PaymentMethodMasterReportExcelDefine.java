package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �x�����@�}�X�^��Excel��`�N���X
 */
public class PaymentMethodMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MP0040";
	}

	public String getSheetName() {
		// �x�����@�}�X�^��Ԃ�
		return "C02350";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00864", "C00865", "C00866", "C00572", "C00602", "C00876", "C00571", "C01096", "C01097",
				"C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 9, 20, 40, 10, 10, 10, 10, 8, 13, 6, 6 };
	}

	private Map hohNaiCodeMap;

	/**
	 * 
	 */
	public PaymentMethodMasterReportExcelDefine() {
		// 01:�����i�Ј��j 03:�����U���i�Ј��j 04:�N���W�b�g�i�Ј��j
		// 10�F�Ј������i�Ј��j 11:���� 12:�U���i��s�����j 13:�U���iFB�쐬�j
		// 14:���؎� 15:�x����` 16:���� 17:���E 18: �O������
		// 9�F�U���i���O�p�����j 99: ���̑�
		hohNaiCodeMap = new LinkedHashMap();
		hohNaiCodeMap.put("01", "C02135");
		hohNaiCodeMap.put("03", "C02136");
		hohNaiCodeMap.put("04", "C02137");
		hohNaiCodeMap.put("10", "C02138");
		hohNaiCodeMap.put("11", "C00154");
		hohNaiCodeMap.put("12", "C02139");
		hohNaiCodeMap.put("13", "C02140");
		hohNaiCodeMap.put("14", "C02141");
		hohNaiCodeMap.put("15", "C00230");
		hohNaiCodeMap.put("16", "C02142");
		hohNaiCodeMap.put("17", "C00331");
		hohNaiCodeMap.put("18", "C02143");
		hohNaiCodeMap.put("19", "C02144");
		hohNaiCodeMap.put("99", "C00338");
	}

	public List convertDataToList(Object dto, String langCode) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		List list = new ArrayList();
		// �x�����@�R�[�h
		list.add(entity.getHOH_HOH_CODE());
		// �x�����@��
		list.add(entity.getHOH_HOH_NAME());
		// �x�����@��������
		list.add(entity.getHOH_HOH_NAME_K());
		// �ȖڃR�[�h
		list.add(entity.getHOH_KMK_CODE());
		// �⏕�ȖڃR�[�h
		list.add(entity.getHOH_HKM_CODE());
		// ����ȖڃR�[�h
		list.add(entity.getHOH_UKM_CODE());
		// �v�㕔��R�[�h
		list.add(entity.getHOH_DEP_CODE());
		// �x���Ώۋ敪
		list.add(new AlignString(0 == entity.getHOH_SIH_KBN() ? getWord("C01119", langCode) : getWord("C01124",
				langCode), AlignString.CENTER));
		// �x�������R�[�h
		list.add(getWord(entity.getHOH_NAI_CODE(), hohNaiCodeMap, langCode));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
