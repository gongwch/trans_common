package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * GL�R���g���[���}�X�^��Excel��`�N���X
 */
public class GLControlMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0040";
	}

	public String getSheetName() {
		// GL�R���g���[���}�X�^��Ԃ�
		return "C02339";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00145", "C01056", "C00524", "C00454" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 10, 10, 16, 18, 12 };
	}

	private Map mapKsnNyuKbn;

	private Map mapMtZanHyjKbn;

	private Map mapExcRateKbn;

	/**
	 * 
	 */
	public GLControlMasterReportExcelDefine() {
		// ���Z�`�[���͋敪 - 0:�P�N 1:���� 2:�l���� 3:����
		mapKsnNyuKbn = new HashMap();
		mapKsnNyuKbn.put(0, "C00009");
		mapKsnNyuKbn.put(1, "C00435");
		mapKsnNyuKbn.put(2, "C00239");
		mapKsnNyuKbn.put(3, "C00147");
		// �������ʎc���\���敪 - 0:���ʎc����\�����Ȃ� 1:���ʎc����\������
		mapMtZanHyjKbn = new HashMap();
		mapMtZanHyjKbn.put(0, "C02369");
		mapMtZanHyjKbn.put(1, "C02370");
		// �]���փ��[�g�敪 - 0:�������[�g 1:�����������[�g
		mapExcRateKbn = new HashMap();
		mapExcRateKbn.put(0, "C00148");
		mapExcRateKbn.put(1, "C00535");
	}

	public List convertDataToList(Object dto, String langCode) {
		GL_CTL_MST entity = (GL_CTL_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// ���Z�i�K�敪

		int ksdkbn = entity.getKSD_KBN();
		if (ksdkbn == 0) {
			list.add(new AlignString(getWord("C00038", langCode), AlignString.CENTER));
		} else if (ksdkbn > 0) {
			list.add(new AlignString(ksdkbn + getWord("C00373", langCode), AlignString.CENTER));
		} else {
			list.add(new AlignString(String.valueOf(ksdkbn), AlignString.CENTER));
		}

		// ���Z�`�[���͋敪
		list.add(new AlignString(getWord(entity.getKSN_NYU_KBN(), mapKsnNyuKbn, langCode), AlignString.CENTER));
		// �������ʎc���\���敪
		list.add(new AlignString(getWord(entity.getMT_ZAN_HYJ_KBN(), mapMtZanHyjKbn, langCode), AlignString.CENTER));
		// �]���փ��[�g�敪
		list.add(new AlignString(getWord(entity.getEXC_RATE_KBN(), mapExcRateKbn, langCode), AlignString.CENTER));

		return list;
	}
}
