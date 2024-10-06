package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.master.entity.BNK_MST;

/**
 * ��s�}�X�^��Excel��`�N���X
 */
public class BankMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0140";
	}
	
	public String getSheetName() {
		// ��s�}�X�^��Ԃ�
		return "C02323";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] {"C00779", "C00780", "C00781", "C00782", "C00829", "C00783",
				"C00784", "C00785", "C00055", "C00261" 
			};
	}
	
	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] {
				5, 9, 30, 30, 30, 30, 30, 30, 6, 6
			};
	}

	public List convertDataToList(Object dto, String langCode) {
		BNK_MST entity = (BNK_MST)dto;
		List list = new ArrayList();

		//��s�R�[�h
		list.add(entity.getBNK_CODE());
		//��s�x�X�R�[�h
		list.add(entity.getBNK_STN_CODE());
		//�@��s��
		list.add(entity.getBNK_NAME_S());
		//��s�J�i����
		list.add(entity.getBNK_KANA());
		//��s��������
		list.add(entity.getBNK_NAME_K());
		//��s�x�X��
		list.add(entity.getBNK_STN_NAME_S());
		//��s�x�X�J�i����
		list.add(entity.getBNK_STN_KANA());
		//��s�x�X��������
		list.add(entity.getBNK_STN_NAME_K());
		//�J�n�N����
		list.add(entity.getSTR_DATE());
		//�I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}

