package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �����}�X�^��Excel��`�N���X
 */
public class CustomerMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0150";
	}

	public String getSheetName() {
		// �����}�X�^��Ԃ�
		return "C02326";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00786", "C00830", "C00787", "C00836", "C00581", "C00527", "C01152", "C01150",
				"C01151", "C00393", "C00690", "C00871", "C01089", "C01261", "C02038", "C02039", "C00870", "C02040",
				"C01130", "C10133", "C02041", "C02042", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 10, 40, 40, 80, 40, 10, 80, 50, 50, 12, 12, 12, 6, 6, 12, 16, 12, 14, 10, 48, 20, 11,
				6, 6 };
	}

	private Map mapSiireKbn;

	private Map mapTokuiKbn;

	private Map mapTriKbn;

	/**
	 * 
	 */
	public CustomerMasterReportExcelDefine() {
		// �d����敪 - 0:��d���� 1:�d����
		mapSiireKbn = new HashMap();
		mapSiireKbn.put(0, "C01295");
		mapSiireKbn.put(1, "C00203");
		// ���Ӑ�敪 - 0:�񓾈Ӑ� 1:���Ӑ�
		mapTokuiKbn = new HashMap();
		mapTokuiKbn.put(0, "C01296");
		mapTokuiKbn.put(1, "C00401");
		// ����`�ԋ敪 - 00:�ʏ� 01:�X�|�b�g
		mapTriKbn = new HashMap();
		mapTriKbn.put("00", "C00372");
		mapTriKbn.put("01", "C00308");

	}

	public List convertDataToList(Object dto, String langCode) {
		TRI_MST entity = (TRI_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �����R�[�h
		list.add(entity.getTRI_CODE());
		// ����於��
		list.add(entity.getTRI_NAME());
		// ����旪��
		list.add(entity.getTRI_NAME_S());
		// ����挟������
		list.add(entity.getTRI_NAME_K());
		// ���Ə�����
		list.add(entity.getJIG_NAME());
		// �X�֔ԍ�
		list.add(entity.getZIP());
		// �Z���J�i
		list.add(entity.getJYU_KANA());
		// �Z���P
		list.add(entity.getJYU_1());
		// �Z���Q
		list.add(entity.getJYU_2());
		// �d�b�ԍ�
		list.add(entity.getTEL());
		// FAX�ԍ�
		list.add(entity.getFAX());
		// �W�v�����R�[�h
		list.add(entity.getSUM_CODE());
		// �d����敪
		list.add(new AlignString(getWord(entity.getSIIRE_KBN(), mapSiireKbn, langCode), AlignString.CENTER));
		// ���Ӑ�敪
		list.add(new AlignString(getWord(entity.getTOKUI_KBN(), mapTokuiKbn, langCode), AlignString.CENTER));
		// �����������ߓ�
		list.add(entity.getNJ_C_DATE());
		// �����������ߌ㌎
		list.add(entity.getNJ_R_MON());
		// ��������������
		list.add(entity.getNJ_P_DATE());
		// ������s��������
		list.add(entity.getNKN_CBK_CODE());
		// ����`�ԋ敪
		list.add(new AlignString(getWord(entity.getTRI_KBN(), mapTriKbn, langCode), AlignString.CENTER));
		// �U���˗��l��
		list.add(entity.getIRAI_NAME());
		// �X�|�b�g�`�[�ԍ�
		list.add(entity.getSPOT_DEN_NO());
		// �����萔���敪
		list.add(new AlignString(entity.getNYU_TESU_KBN() == 1 ? getWord("C00399", langCode) : getWord("C00327",
			langCode), AlignString.CENTER));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
