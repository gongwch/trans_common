package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ����}�X�^��Excel��`�N���X
 */
public class DepartmentMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0060";
	}

	public String getSheetName() {
		// ����}�X�^��Ԃ�
		return "C02338";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00698", "C00723", "C00724", "C00725", "C00726", "C00727", "C00728", "C01303",
				"C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 40, 20, 40, 6, 6, 7, 7, 6, 6 };
	}

	public List convertDataToList(Object dto, String langCode) {
		BMN_MST entity = (BMN_MST) dto;
		List list = new ArrayList();
		FormatDecimal fd1, fd2, fd3;

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// ����R�[�h
		list.add(entity.getDEP_CODE());
		// ���喼��
		list.add(entity.getDEP_NAME());
		// ���嗪��
		list.add(entity.getDEP_NAME_S());
		// ���匟������
		list.add(entity.getDEP_NAME_K());
		// �l�����P
		if (entity.getMEN_1() == null) {
			list.add("");
		} else {
			fd1 = new FormatDecimal(entity.getMEN_1(), "###,##0");
			list.add(fd1);
		}

		// �l����2
		if (entity.getMEN_2() == null) {
			list.add("");
		} else {
			fd2 = new FormatDecimal(entity.getMEN_2(), "###,##0");
			list.add(fd2);
		}

		// ���ʐ�
		if (entity.getAREA() == null) {
			list.add("");
		} else {
			fd3 = new FormatDecimal(entity.getAREA(), "###,##0.00");
			list.add(fd3);
		}

		// ����敪
		String depKbn = String.valueOf(entity.getDEP_KBN());
		if ("0".equals(depKbn)) {
			list.add(new AlignString(getWord("C01275", langCode), AlignString.CENTER));
		} else {
			list.add(new AlignString(getWord("C00255", langCode), AlignString.CENTER));
		}
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
