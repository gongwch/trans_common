package jp.co.ais.trans.master.logic.report;

import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �J������Ͻ��}�X�^��Excel��`�N���X
 */
public class IndicationLevelMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0340";
	}
	
	public String getSheetName() {
		// �J�����ك}�X�^��Ԃ�
		return "C02340";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] {
				"C00596", "C00589", "C02048", "C00335", "C00057", "C01909", "C00698"
			};
	}
	
	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] {
				0, 10, 10, 6, 6, 10, 10 
			};
	}


	public List convertDataToList(Object dto, String langCode) {
		KJL_MST entity = (KJL_MST)dto;
		List list = new ArrayList();

		//��ЃR�[�h
		list.add(entity.getKAI_CODE());
		//���[�U�[�R�[�h
		list.add(entity.getKJL_USR_ID());
		//�@�Ȗڑ̌n����
		list.add(entity.getKJL_KMT_CODE());
		//�g�D�R�[�h
		list.add(entity.getKJL_DPK_SSK());
		//�@�J�����x��
		String kjlLvl = String.valueOf(entity.getKJL_LVL());
		list.add(getWord("C01739", langCode)+ kjlLvl);
		//��ʕ���R�[�h
		list.add(entity.getKJL_UP_DEP_CODE());
		//�@����R�[�h
		list.add(entity.getKJL_DEP_CODE());
		
		return list;
	}
}
