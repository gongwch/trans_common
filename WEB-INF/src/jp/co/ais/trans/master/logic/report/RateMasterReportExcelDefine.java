package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���[�g�}�X�^��Excel��`�N���X
 */
public class RateMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0310";
	}

	public String getSheetName() {
		// ���[�g�}�X�^��Ԃ�
		return "C02352";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00665", "C02046", "C00556" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 6, 11, 11 };
	}

	public List convertDataToList(Object dto, String langCode) {
		RATE_MST entity = (RATE_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �ʉ݃R�[�h
		list.add(entity.getCUR_CODE());
		// �K�p�J�n���t
		list.add(entity.getSTR_DATE());
		// ���[�g
		FormatDecimal format = new FormatDecimal(entity.getCUR_RATE(), "###,##0.0000");
		list.add(format);

		return list;
	}
}
