package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �V�X�e���敪�}�X�^�}�X�^��Excel��`�N���X
 */
public class SystemDivisionMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0320";
	}

	public String getSheetName() {
		return "C02354";
	}

	public String[] getHeaderTexts() {
		return new String[] { "C00596", "C00980", "C00832", "C00833", "C00834", "C01018" };
	}

	public short[] getColumnWidths() {
		return new short[] { 0, 7, 40, 20, 20, 12 };
	}

	private Map dataKbnMap;

	/**
	 * 
	 */
	public SystemDivisionMasterReportExcelDefine() {
		// �O���V�X�e���敪 - �O�F�߯���ގg�p���� �P�F�߯���ގg�p���Ȃ� �Q�F�O���V�X�e��
		dataKbnMap = new HashMap();
		dataKbnMap.put("0", "C02104");
		dataKbnMap.put("1", "C02105");
		dataKbnMap.put("2", "C02106");
	}

	public List convertDataToList(Object dto, String langCode) {
		SYS_MST entity = (SYS_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �V�X�e���敪
		list.add(new AlignString(entity.getSYS_KBN(), AlignString.CENTER));
		// �V�X�e���敪����
		list.add(entity.getSYS_KBN_NAME());
		// �V�X�e���敪����
		list.add(entity.getSYS_KBN_NAME_S());
		// �V�X�e���敪��������
		list.add(entity.getSYS_KBN_NAME_K());
		// �O���V�X�e���敪
		list.add(new AlignString(getWord(entity.getOSY_KBN(), dataKbnMap, langCode), AlignString.CENTER));

		return list;
	}
}
