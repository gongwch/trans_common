package jp.co.ais.trans.master.logic.report;

import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���ݒ�}�X�^��Excel��`�N���X
 */
public class EnvironmentalSettingMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0010";
	}
	
	public String getSheetName() {
		// ���ݒ�}�X�^��Ԃ�
		return "C00087";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] {
				"C00596", "C00685", "C00686", "C01150", "C01151", "C01152",
				"C00527", "C00393", "C00690", "C00055", "C00261"
				
			};
	}
	
	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] {
				10, 40, 20, 50, 50, 80, 10, 12, 12, 6, 6
			};
	}

	public List convertDataToList(Object dto, String langCode) {
		ENV_MST entity = (ENV_MST)dto;
		List list = new ArrayList();

		//��ЃR�[�h
		list.add(entity.getKAI_CODE());
		//��Ж���
		list.add(entity.getKAI_NAME());
		//��З���
		list.add(entity.getKAI_NAME_S());
		//�Z���P
		list.add(entity.getJYU_1());
		//�Z��2
		list.add(entity.getJYU_2());
		//�@�Z���J�i
		list.add(entity.getJYU_KANA());
		//�X�֔ԍ�
		list.add(entity.getZIP());
		//�d�b�ԍ�
		list.add(entity.getTEL());
		//FAX�ԍ�
		list.add(entity.getFAX());
		//�J�n�N����
		list.add(entity.getSTR_DATE());
		//�I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}

