package jp.co.ais.trans.master.logic.report;

import java.util.*;
import jp.co.ais.trans.master.entity.*;
/**
 * ��Њԕt�փ}�X�^��Excel��`�N���X
 */
public class InterCompanyTransferMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0350";
	}	
	
	public String getSheetName() {
		// ��Њԕt�փ}�X�^��Ԃ�
		return "C02341";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] {
				"C00596", "C02050", "C02051", "C02052", "C02053", "C02054"
			};
	}
	
	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] {
				0, 10, 16, 11, 16, 16
			};
	}
	
	public List convertDataToList(Object dto, String langCode) {
		KTK_MST entity = (KTK_MST) dto;
		List list = new ArrayList();
		
		//��ЃR�[�h
		list.add(entity.getKAI_CODE());
		//�t�։�ЃR�[�h
		list.add(entity.getKTK_KAI_CODE());
		//�t�֌v�㕔��R�[�h
		list.add(entity.getKTK_DEP_CODE());
		//�t�։ȖڃR�[�h
		list.add(entity.getKTK_KMK_CODE());
		//�t�֕⏕�ȖڃR�[�h
		list.add(entity.getKTK_HKM_CODE());
		//�t�֓���ȖڃR�[�h
		list.add(entity.getKTK_UKM_CODE());
		
		return list;

	}
}
