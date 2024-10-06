package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �`�[��ʃ}�X�^ �}�X�^��Excel��`�N���X
 */
public class SlipTypeMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0330";
	}

	public String getSheetName() {
		// ���}�X�^��Ԃ�
		return "C00912";
	}

	public String[] getHeaderTexts() {
		// �`�[��ʃ}�X�^�C�g����Ԃ�
		return new String[] { "C00596", "C00837", "C00980", "C00838", "C00839", "C02757", "C02047", "C00355", "C00392",
				"C00018", "C00287", "C00299" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 0, 9, 7, 40, 20, 20, 9, 9, 20, 17, 20, 20 };
	}

	private Map dataKbnMap;

	private Map taSysKbnMap;

	private Map datSaiBanFlgMap;

	private Map taniMap;

	private Map zeiKbnMap;

	private Map swkInKbnMap;

	/**
	 * 
	 */
	public SlipTypeMasterReportExcelDefine() {
		// �ް��敪 - 11:�����`�[,12:�o���`�[,13:�U�֓`�[,14:���ϓ`�[,15:���Ϗ����`�[,
		// 21:�Ј�����,22:�o��Z,23:������,24:�x���`�[(�Ј�),
		// 25:�x���`�[(�Վ�),26:�x���`�[(�莞),27:�O����������,
		// 28:���֌�ʔ�,2E:���۔��c��(�Ј�),2T:���۔��c��(�ЊO),
		// 31:���v��,32:������,33:���֋�������,41:���o�^,42:��茈��,
		// 51:�x��o�^,52:�x�茈��,61:�Œ莑�Y�U��,62:�Œ莑�Y�x��
		dataKbnMap = new HashMap();
		dataKbnMap.put("11", "C00419");
		dataKbnMap.put("12", "C00264");
		dataKbnMap.put("13", "C00469");
		dataKbnMap.put("14", "C02079");
		dataKbnMap.put("15", "C02080");
		dataKbnMap.put("21", "C02081");
		dataKbnMap.put("22", "C02082");
		dataKbnMap.put("23", "C00314");
		dataKbnMap.put("24", "C02083");
		dataKbnMap.put("25", "C02084");
		dataKbnMap.put("26", "C02085");
		dataKbnMap.put("27", "C02086");
		dataKbnMap.put("28", "C02087");
		dataKbnMap.put("2E", "C02088");
		dataKbnMap.put("2T", "C02089");
		dataKbnMap.put("31", "C01978");
		dataKbnMap.put("32", "C01979");
		dataKbnMap.put("33", "C02090");
		dataKbnMap.put("41", "C02091");
		dataKbnMap.put("42", "C02092");
		dataKbnMap.put("51", "C02093");
		dataKbnMap.put("52", "C02094");
		dataKbnMap.put("61", "C02095");
		dataKbnMap.put("62", "C02096");
		// �����ы敪 - 0:��������ΏۊO 1:��������Ώ�
		taSysKbnMap = new HashMap();
		taSysKbnMap.put(0, "C02097");
		taSysKbnMap.put(1, "C02098");
		// �`�[�ԍ��̔��׸� - 0:���Ȃ� �P�F����
		datSaiBanFlgMap = new HashMap();
		datSaiBanFlgMap.put(0, "C02099");
		datSaiBanFlgMap.put(1, "C02100");
		// ����P�� - 0:�`�[��ʒP�ʂɴװ���� 1:�`�[�ԍ��P�ʂɴװ����

		taniMap = new HashMap();
		taniMap.put(0, "C02101");
		taniMap.put(1, "C02102");
		// ����Ōv�Z�敪 - 0:�O�� 1:����
		zeiKbnMap = new HashMap();
		zeiKbnMap.put(0, "C00337");
		zeiKbnMap.put(1, "C00023");
		// �d��C���^�[�t�F�[�X�敪 - 0:�o�^ 1:���F
		swkInKbnMap = new HashMap();
		swkInKbnMap.put(0, "C01258");
		swkInKbnMap.put(1, "C01168");
	}

	public List convertDataToList(Object dto, String langCode) {
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;
		List list = new ArrayList();

		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// �`�[��ʺ���
		list.add(entity.getDEN_SYU_CODE());
		// �V�X�e���敪
		list.add(new AlignString(entity.getSYS_KBN(), AlignString.CENTER));
		// �`�[��ʖ���
		list.add(entity.getDEN_SYU_NAME());
		// �`�[��ʗ���
		list.add(entity.getDEN_SYU_NAME_S());
		// ���[�^�C�g��
		list.add(entity.getDEN_SYU_NAME_K());
		// �ް��敪
		list.add(new AlignString(getWord(entity.getDATA_KBN(), dataKbnMap, langCode), AlignString.CENTER));
		// �����ы敪
		list.add(new AlignString(getWord(entity.getTA_SYS_KBN(), taSysKbnMap, langCode), AlignString.CENTER));
		// �`�[�ԍ��̔��׸�
		list.add(getWord(entity.getDAT_SAIBAN_FLG(), datSaiBanFlgMap, langCode));
		// ����P��
		list.add(getWord(entity.getTANI(), taniMap, langCode));
		// ����Ōv�Z�敪
		list.add(new AlignString(getWord(entity.getZEI_KBN(), zeiKbnMap, langCode), AlignString.CENTER));
		// �d��C���^�[�t�F�[�X�敪
		list.add(new AlignString(getWord(entity.getSWK_IN_KBN(), swkInKbnMap, langCode), AlignString.CENTER));

		return list;
	}
}
