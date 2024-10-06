package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�[�}�X�^��Excel��`�N���X
 */
public class UserMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0020";
	}

	public String getSheetName() {
		// ���[�U�[�}�X�^��Ԃ�
		return "C02355";
	}

	public String[] getHeaderTexts() {
		// �^�C�g����Ԃ�
		return new String[] { "C00596", "C00589", "C00597", "C00699", "C00691", "C00692", "C00693", "C00112", "C01082",
				"C01078", "C00180", "C00230", "C00020", "C00096", "C00041", "C00491", "C01222", "C00537", "C00206",
				"C00176", "C02044", "C00525", "C00297", "C00170", "C00697", "C02043", "C00139", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// �񕝂�Ԃ�
		return new short[] { 10, 10, 0, 10, 40, 40, 40, 4, 4, 4, 4, 4, 4, 4, 6, 6, 0, 4, 4, 6, 0, 4, 9, 9, 10, 10, 12,
				6, 6 };
	}

	private Map updKenMap;

	private Map keiriKbnMap;

	private Map sysKbnMap0;

	private Map sysKbnMap1;

	private Map sysKbnMap2;

	private Map sysKbnMap3;

	private Map sysKbnMap4;

	private Map sysKbnMap5;

	private Map sysKbnMap6;

	private Map sysKbnMap7;

	private Map sysKbnMap8;

	private Map sysKbnMap9;

	private Map sysKbnMap10;

	private Map sysKbnMap11;

	private Map sysKbnMap12;

	private Map sysKbnMap13;

	private Map sysKbnMap14;

	/**
	 * 
	 */
	public UserMasterReportExcelDefine() {
		// �X�V�������x�� - 0:ALL 1:���͎� 2:��������
		updKenMap = new HashMap();
		updKenMap.put(0, "C00310");
		updKenMap.put(1, "C01278");
		updKenMap.put(2, "C00295");
		// �o���S���ҋ敪 - �O�F�o���S���҈ȊO �P�F�o���S����
		keiriKbnMap = new HashMap();
		keiriKbnMap.put(0, "C00140");
		keiriKbnMap.put(1, "C01050");

		// �V�X�e���敪

		// ��{��v- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap0 = new HashMap();
		sysKbnMap0.put("0", "C02366");
		sysKbnMap0.put("1", "C02365");
		// ���Ǘ�- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap1 = new HashMap();
		sysKbnMap1.put("0", "C02366");
		sysKbnMap1.put("1", "C02365");
		// ���Ǘ�- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap2 = new HashMap();
		sysKbnMap2.put("0", "C02366");
		sysKbnMap2.put("1", "C02365");
		// �Œ莑�Y- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap3 = new HashMap();
		sysKbnMap3.put("0", "C02366");
		sysKbnMap3.put("1", "C02365");
		// �x����`- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap4 = new HashMap();
		sysKbnMap4.put("0", "C02366");
		sysKbnMap4.put("1", "C02365");
		// ����`- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap5 = new HashMap();
		sysKbnMap5.put("0", "C02366");
		sysKbnMap5.put("1", "C02365");
		// �Ǘ���v- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap6 = new HashMap();
		sysKbnMap6.put("0", "C02366");
		sysKbnMap6.put("1", "C02365");
		// �e��ИA��- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap7 = new HashMap();
		sysKbnMap7.put("0", "C02366");
		sysKbnMap7.put("1", "C02365");
		// �{�x�X��v- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap8 = new HashMap();
		sysKbnMap8.put("0", "C02366");
		sysKbnMap8.put("1", "C02365");
		// ���ʉ݉�v- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap9 = new HashMap();
		sysKbnMap9.put("0", "C02366");
		sysKbnMap9.put("1", "C02365");
		// �\�Z�Ǘ�- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap10 = new HashMap();
		sysKbnMap10.put("0", "C02366");
		sysKbnMap10.put("1", "C02365");
		// �����Ǘ�- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap11 = new HashMap();
		sysKbnMap11.put("0", "C02366");
		sysKbnMap11.put("1", "C02365");
		// �q��ИA��- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap12 = new HashMap();
		sysKbnMap12.put("0", "C02366");
		sysKbnMap12.put("1", "C02365");
		// �O���v��v- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap13 = new HashMap();
		sysKbnMap13.put("0", "C02366");
		sysKbnMap13.put("1", "C02365");
		// �L���،���v- 1:����̏ꍇ�u�v�A0:���Ȃ��̏ꍇ�u�s�v
		sysKbnMap14 = new HashMap();
		sysKbnMap14.put("0", "C02366");
		sysKbnMap14.put("1", "C02365");
	}

	public List convertDataToList(Object dto, String langCode) {
		USR_MST entity = (USR_MST) dto;
		List list = new ArrayList();

		char[] sysKbn = entity.getSYS_KBN().toCharArray();
		// ��ЃR�[�h
		list.add(entity.getKAI_CODE());
		// ���[�U�[�R�[�h
		list.add(entity.getUSR_CODE());
		// �p�X���[�h
		list.add(entity.getPASSWORD());
		// ����R�[�h
		list.add(entity.getLNG_CODE());
		// ���[�U�[����
		list.add(entity.getUSR_NAME());
		// ���[�U�[����
		list.add(entity.getUSR_NAME_S());
		// ���[�U�[��������
		list.add(entity.getUSR_NAME_K());

		// �V�X�e���敪

		// ��{��v
		list.add(getWord(String.valueOf(sysKbn[0]), sysKbnMap0, langCode));
		// ���Ǘ�
		list.add(getWord(String.valueOf(sysKbn[1]), sysKbnMap1, langCode));
		// ���Ǘ�
		list.add(getWord(String.valueOf(sysKbn[2]), sysKbnMap2, langCode));
		// �Œ莑�Y
		list.add(getWord(String.valueOf(sysKbn[3]), sysKbnMap3, langCode));
		// �x����`
		list.add(getWord(String.valueOf(sysKbn[4]), sysKbnMap4, langCode));
		// ����`
		list.add(getWord(String.valueOf(sysKbn[5]), sysKbnMap5, langCode));
		// �Ǘ���v
		list.add(getWord(String.valueOf(sysKbn[6]), sysKbnMap6, langCode));
		// �e��ИA��
		list.add(getWord(String.valueOf(sysKbn[7]), sysKbnMap7, langCode));
		// �{�x�X��v
		list.add(getWord(String.valueOf(sysKbn[8]), sysKbnMap8, langCode));
		// ���ʉ݉�v
		list.add(getWord(String.valueOf(sysKbn[9]), sysKbnMap9, langCode));
		// �\�Z�Ǘ�
		list.add(getWord(String.valueOf(sysKbn[10]), sysKbnMap10, langCode));
		// �����Ǘ�
		list.add(getWord(String.valueOf(sysKbn[11]), sysKbnMap11, langCode));
		// �q��ИA��
		list.add(getWord(String.valueOf(sysKbn[12]), sysKbnMap12, langCode));
		// �O���v��v
		list.add(getWord(String.valueOf(sysKbn[13]), sysKbnMap13, langCode));
		// �L���،���v
		list.add(getWord(String.valueOf(sysKbn[14]), sysKbnMap14, langCode));
		// �����������x��
		list.add(entity.getPRC_KEN());
		// �X�V�������x��
		list.add(getWord(entity.getUPD_KEN(), updKenMap, langCode));
		// �Ј��R�[�h
		list.add(entity.getEMP_CODE());
		// ��������R�[�h
		list.add(entity.getDEP_CODE());
		// �o���S���ҋ敪
		list.add(new AlignString(getWord(entity.getKEIRI_KBN(), keiriKbnMap, langCode), AlignString.CENTER));
		// �J�n�N����
		list.add(entity.getSTR_DATE());
		// �I���N����
		list.add(entity.getEND_DATE());

		return list;
	}
}
