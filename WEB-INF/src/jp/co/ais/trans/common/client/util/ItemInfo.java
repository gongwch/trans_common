package jp.co.ais.trans.common.client.util;

import java.util.*;

/**
 * �Ȗڏ��
 */
public class ItemInfo {

	/** �⏕�敪 0:�Ȃ� 1:���� */
	private static final String SUB_DIVISION = "1";

	/** GL�Ȗڐ���敪 00:�ʏ� */
	public static final String GLCTL_NOMAL = "00";

	/** GL�Ȗڐ���敪 01:�O���J�z���v */
	public static final String GLCTL_FORWARD_EARNINGS = "01";

	/** GL�Ȗڐ���敪 04:�����Ȗ� */
	public static final String GLCTL_FUND = "04";

	/** GL�Ȗڐ���敪 05:����Ȗ� */
	public static final String GLCTL_SALES = "05";

	/** GL�Ȗڐ���敪 06:�ב֊��Z�����v */
	public static final String GLCTL_EXCHANGE_GAIN_OR_LOSS = "06";

	/** GL�Ȗڐ���敪 07:������ */
	public static final String GLCTL_TEMP_ACCOUNT = "07";

	/** GL�Ȗڐ���敪 08:�ב֍��� */
	public static final String GLCTL_EXCHANGE_LOSS = "08";

	/** GL�Ȗڐ���敪 09:�ב֍��v */
	public static final String GLCTL_EXCHANGE_GAIN = "09";

	/** �W�v�敪�i0:���͉Ȗځj */
	public static final String SUMDIV_INPUT = "0";

	/** �W�v�敪�i1:�W�v�Ȗځj */
	public static final String SUMDIV_SUMMARY = "1";

	/** �W�v�敪�i2:���o�Ȗځj */
	public static final String SUMDIV_CAPTION = "2";

	/** �f�[�^ */
	protected Map<String, String> data;

	/** ���̃L�[ */
	protected String nameCode;
	
	/** ���̃L�[ */
	protected String shortNameCode;
	
	/**
	 * �R���X�g���N�^
	 */
	protected ItemInfo() {
		nameCode = "KMK_NAME";
		shortNameCode = "KMK_NAME_S";
	}
	
	/**
	 * �f�[�^���Z�b�g
	 * 
	 * @param data �f�[�^
	 */
	protected void set(Map<String, String> data) {
		this.data = data;
	}

	/**
	 * �Ȗڗ��̎擾
	 * 
	 * @return �Ȗڗ���
	 */
	public String getName() {
		return data.get(nameCode);
	}

	/**
	 * �Ȗڗ��̎擾
	 * 
	 * @return �Ȗڗ���
	 */
	public String getShortName() {
		return data.get(shortNameCode);
	}

	/**
	 * �W�v�敪
	 * 
	 * @return �W�v�敪
	 */
	public String getSumKbn() {
		return data.get("SUM_KBN");
	}

	/**
	 * GL�Ȗڐ���敪
	 * 
	 * @return GL�Ȗڐ���敪
	 */
	public String getKmKCntGL() {
		return data.get("KMK_CNT_GL");
	}

	/**
	 * �⏕�Ȗڂ����݂��邩�ǂ���
	 * 
	 * @return ���݂���ꍇtrue
	 */
	public boolean isExistSubItem() {
		return SUB_DIVISION.equals(data.get("HKM_KBN"));
	}
}
