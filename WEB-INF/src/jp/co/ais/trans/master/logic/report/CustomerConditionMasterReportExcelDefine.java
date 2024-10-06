package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.TRI_SJ_MST;

/**
 * ���������}�X�^��Excel��`�N���X
 */
public class CustomerConditionMasterReportExcelDefine extends ReportExcelDefineBase {

	private Map mapYknKbn;

	/**
	 * �R���X�g���N�^
	 */
	public CustomerConditionMasterReportExcelDefine() {
		// �a����� 1:���ʗa�� 2:�����a�� 3:�O�ݗa�� 4:���~�a��
		mapYknKbn = new HashMap();
		mapYknKbn.put("1", "C00465");
		mapYknKbn.put("2", "C02154");
		mapYknKbn.put("3", "C02168");
		mapYknKbn.put("4", "C02169");
	}

	public String getFileName() {
		return "MG0155";
	}

	public String getSheetName() {
		// �����x������Ͻ���Ԃ�
		return "C02325";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00786", "C00787", "C00788", "C02056", "C02057", "C02058", "C02059", "C00682",
				"C00233", "C02061", "C00779", "C02055", "C01326", "C00794", "C01068", "C00793", "C02037", "C00795",
				"C00796", "C00797", "C10224", "C00799", "C00800", "C00801", "C00802", "C00803", "C00804", "C00805",
				"C00055", "C00261" };

	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 11, 13, 11, 11, 13, 13, 13, 6, 5, 18, 5, 5, 6, 30, 30, 11, 11, 35, 35, 35, 6, 35, 35,
				35, 35, 35, 35, 35, 6, 6 };
	}

	public List convertDataToList(Object dto, String langCode) {
		TRI_SJ_MST entity = (TRI_SJ_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �����R�[�h
		list.add(entity.getTRI_CODE());
		// ����旪��
		list.add(entity.getTRI_NAME_S());
		// ���������R�[�h
		list.add(entity.getTJK_CODE());
		// �U���萔���敪
		list.add(new AlignString(entity.getFURI_TESU_KBN() == 1 ? getWord("C00399", langCode) : getWord("C00327",
			langCode), AlignString.CENTER));
		// �x���������ߓ�
		// list.add(Integer.parseInt(entity.getSJC_DATE()));
		list.add(new AlignString(entity.getSJC_DATE(), AlignString.LEFT));
		// �x���������ߌ㌎
		// list.add(Integer.parseInt(entity.getSJR_MON()));
		list.add(new AlignString(entity.getSJR_MON(), AlignString.LEFT));
		// �x�������x����
		// list.add(Integer.parseInt(entity.getSJP_DATE()));
		list.add(new AlignString(entity.getSJP_DATE(), AlignString.LEFT));
		// �x���敪
		list.add(new AlignString("00".equals(entity.getSIHA_KBN()) ? getWord("C02166", langCode) : getWord("C02167",
			langCode), AlignString.CENTER));
		// �x�����@
		list.add(entity.getSIHA_HOU_CODE());
		// �U���U�o��s��������
		list.add(entity.getFURI_CBK_CODE());
		// ��s�R�[�h
		list.add(entity.getBNK_CODE());
		// �x�X�R�[�h
		list.add(entity.getBNK_STN_CODE());
		// �a�����
		list.add(new AlignString(getWord(entity.getYKN_KBN(), mapYknKbn, langCode), AlignString.CENTER));
		// �����ԍ�
		list.add(entity.getYKN_NO());
		// �������`�J�i
		list.add(entity.getYKN_KANA());
		// �����ړI�R�[�h
		list.add(entity.getGS_MKT_CODE());
		// �����ړI��
		list.add(entity.getMKT_NAME());
		// ��d����s����
		list.add(entity.getGS_BNK_NAME());
		// ��d���x�X����
		list.add(entity.getGS_STN_NAME());
		// �������`
		list.add(entity.getYKN_NAME());
		// �萔���敪
		list.add(new AlignString(entity.getGS_TESU_KBN() == 1 ? getWord("C00021", langCode) : getWord("C02319",
			langCode), AlignString.CENTER));
		// �x����s����
		list.add(entity.getSIHA_BNK_NAME());
		// �x���x�X����
		list.add(entity.getSIHA_STN_NAME());
		// �x����s�Z��
		list.add(entity.getSIHA_BNK_ADR());
		// �o�R��s����
		list.add(entity.getKEIYU_BNK_NAME());
		// �o�R�x�X����
		list.add(entity.getKEIYU_STN_NAME());
		// �o�R��s�Z��
		list.add(entity.getKEIYU_BNK_ADR());
		// ���l�Z��
		list.add(entity.getUKE_ADR());
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
