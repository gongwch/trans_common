package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ����Ń}�X�^��Excel��`�N���X
 */
public class ConsumptionTaxMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0130";
	}

	public String getSheetName() {
		// ����Ń}�X�^��Ԃ�
		return "C02324";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00573", "C00774", "C00775", "C00828", "C01283", "C00777", "C02045", "C00055",
				"C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 40, 20, 40, 10, 8, 25, 6, 6 };
	}

	private Map mapUsKbn;

	/**
	 * 
	 */
	public ConsumptionTaxMasterReportExcelDefine() {
		// ����d���敪 0:�ΏۊO 1:���� 2:�d��
		mapUsKbn = new HashMap();
		mapUsKbn.put(0, "C02103");
		mapUsKbn.put(1, "C00026");
		mapUsKbn.put(2, "C00201");

	}

	public List convertDataToList(Object dto, String langCode) {
		SZEI_MST entity = (SZEI_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// ����ŃR�[�h
		list.add(entity.getZEI_CODE());
		// ����Ŗ���
		list.add(entity.getZEI_NAME());
		// ����ŗ���
		list.add(entity.getZEI_NAME_S());
		// ����Ō�������
		list.add(entity.getZEI_NAME_K());
		// ����d���敪
		list.add(new AlignString(getWord(entity.getUS_KBN(), mapUsKbn, langCode), AlignString.CENTER));
		// ����ŗ�
		// DecimalFormat format = new DecimalFormat("##.0");
		// String zeiRate = format.format(Float.valueOf(String.valueOf(entity.getZEI_RATE())));
		// list.add(zeiRate);
		double rate = (Float.valueOf(entity.getZEI_RATE() * 10)).intValue();
		list.add(new AlignString(Double.toString(rate / 10), AlignString.RIGHT));// entity.getZEI_RATE());
		// ����Ōv�Z���o�͏���
		list.add(new AlignString(Util.isNullOrEmpty(entity.getSZEI_KEI_KBN()) ? getWord("C00282", langCode) : entity
			.getSZEI_KEI_KBN(), AlignString.RIGHT));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
