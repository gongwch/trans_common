package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �v���O�����}�X�^ �}�X�^��Excel��`�N���X
 */
public class ProgramMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0240";
	}

	public String getSheetName() {
		// �v���O�����}�X�^��Ԃ�
		return "C02147";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C02351", "C00818", "C00819", "C00820", "C00821", "C00822", "C00183", "C02146",
				"C00824", "C00520", "C00055", "C00261", "C02397" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 9, 11, 40, 20, 20, 6, 80, 340, 14, 7, 6, 6, 5 };
	}

	public List convertDataToList(Object dto, String langCode) {
		PRG_MST entity = (PRG_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �V�X�e���R�[�h
		list.add(entity.getSYS_CODE());
		// �v���O�����R�[�h
		list.add(entity.getPRG_CODE());
		// �v���O��������
		list.add(entity.getPRG_NAME());
		// �v���O��������
		list.add(entity.getPRG_NAME_S());
		// �v���O������������
		list.add(entity.getPRG_NAME_K());
		// �������x��
		list.add(entity.getKEN());
		// �R�����g
		list.add(entity.getCOM());
		// ۰��Ӽޭ��̧�ٖ�
		list.add(entity.getLD_NAME());
		// �e�v���O�����R�[�h
		list.add(entity.getPARENT_PRG_CODE());
		// ���j���[�敪
		list.add(new AlignString(getWord(entity.getMENU_KBN() == 0 ? getWord("C00519", langCode) : getWord("C02170",
				langCode), langCode), AlignString.CENTER));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());
		// �\������
		list.add(entity.getDISP_INDEX());

		return list;
	}
}
