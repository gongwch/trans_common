package jp.co.ais.trans.common.info;

import java.awt.im.*;
import java.io.*;
import java.lang.Character.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �T�[�o���p�ҏ��.
 */
public final class TUserInfo implements Serializable {

	/** �V���A��UID */
	private static final long serialVersionUID = 6559556905634237059L;

	/** ���{��T�u�Z�b�g */
	private static final Subset[] SUBSET_JP = new Subset[] { InputSubset.KANJI };

	/** �؍���T�u�Z�b�g */
	private static final Subset[] SUBSET_KO = new Subset[] { InputSubset.HANJA };

	/** ������T�u�Z�b�g */
	private static final Subset[] SUBSET_ZH = new Subset[] { InputSubset.SIMPLIFIED_HANZI };

	/** login user Code */
	private String usrCode = "";

	/** ���O�C�����p�Ҏ��� */
	private String usrName = "";

	/** �����Ј��R�[�h */
	private String empCode = "";

	/** �����Ј����̃R�[�h */
	private String empNameS = "";

	/** ��������R�[�h */
	private String depCode = "";

	/** �������喼���� */
	private String depNameS = "";

	/** �����������x�� */
	private int prcKen = 9;

	/** �X�V�������x�� */
	private int updKen = 9;

	/** ���p�Ҏg�p���� */
	private String usrLang = "";

	/** �o���S���ҋ敪 */
	private boolean isAccountChargePerson;

	/** ���P�[�� */
	private Locale userLocale = Locale.getDefault();

	/** �S�p�L�����N�^�[�Z�b�g(IME����p) */
	private transient Subset[] charSubsets = null;

	/** ���̑��f�[�^(�J�X�^�}�C�Y�p) */
	private Map<String, String> otherData = new HashMap<String, String>(0);

	/** ��Џ�� */
	private TCompanyInfo compInfo = new TCompanyInfo();

	/**
	 * ��ЃR�[�h�擾setter
	 * 
	 * @param kaiCode ��ЃR�[�h
	 */
	public void setCompanyCode(String kaiCode) {
		this.compInfo.setCompanyCode(kaiCode);
	}

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return this.compInfo.getCompanyCode();
	}

	/**
	 * ���[�U�[�R�[�h�擾setter
	 * 
	 * @param usrCode ���[�U�[�R�[�h
	 */
	public void setUserCode(String usrCode) {
		this.usrCode = usrCode;
	}

	/**
	 * ���[�U�[�R�[�h�擾
	 * 
	 * @return ���[�U�[�R�[�h
	 */
	public String getUserCode() {
		return this.usrCode;
	}

	/**
	 * ���[�U�[���̎擾setter
	 * 
	 * @param usrName ���[�U�[����
	 */
	public void setUserName(String usrName) {
		this.usrName = usrName;
	}

	/**
	 * ���[�U�[���̎擾
	 * 
	 * @return usrName ���[�U�[����
	 */
	public String getUserName() {
		return this.usrName;
	}

	/**
	 * ��������R�[�hsetter
	 * 
	 * @param depCode ��������R�[�h
	 */
	public void setDepartmentCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * ��������R�[�h
	 * 
	 * @return ��������R�[�h
	 */
	public String getDepartmentCode() {
		return this.depCode;
	}

	/**
	 * �����������x��
	 * 
	 * @param prcKen �����������x��
	 */
	public void setProcessLevel(int prcKen) {
		this.prcKen = prcKen;
	}

	/**
	 * �����������x��
	 * 
	 * @return �����������x��
	 */
	public int getProcessLevel() {
		return this.prcKen;
	}

	/**
	 * �X�V�������x��
	 * 
	 * @param updKen �X�V�������x��
	 */
	public void setUpdateLevel(int updKen) {
		this.updKen = updKen;
	}

	/**
	 * �X�V�������x��
	 * 
	 * @return �X�V�������x��
	 */
	public int getUpdateLevel() {
		return this.updKen;
	}

	/**
	 * �Ј��R�[�hsetter
	 * 
	 * @param empCode �Ј��R�[�h
	 */
	public void setEmployerCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * �Ј��R�[�h
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getEmployerCode() {
		return this.empCode;
	}

	/**
	 * �Ј�����setter
	 * 
	 * @param empNameS �Ј�����
	 */
	public void setEmployerShortName(String empNameS) {
		this.empNameS = empNameS;
	}

	/**
	 * �Ј�����
	 * 
	 * @return �Ј�����
	 */
	public String getEmployerShortName() {
		return this.empNameS;
	}

	/**
	 * �������嗪��setter
	 * 
	 * @param depNameS �������嗪��
	 */
	public void setDepartmentShortName(String depNameS) {
		this.depNameS = depNameS;
	}

	/**
	 * �������喼����
	 * 
	 * @return depName
	 */
	public String getDepartmentShortName() {
		return this.depNameS;
	}

	/**
	 * �o���敪setter
	 * 
	 * @param isAccountChargePerson �o���敪
	 */
	public void setAccountChargePerson(boolean isAccountChargePerson) {
		this.isAccountChargePerson = isAccountChargePerson;
	}

	/**
	 * �o���敪getter
	 * 
	 * @return true:�o���S���� false:���̑�
	 */
	public boolean isAccountChargePerson() {
		return this.isAccountChargePerson;
	}

	/**
	 * ���p�Ҏg�p����R�[�hsetter
	 * 
	 * @param usrLang ���p�Ҏg�p����R�[�h
	 */
	public void setUserLanguage(String usrLang) {
		this.usrLang = usrLang;

		// �L�����N�^�[�Z�b�g(IME����)�̐ݒ�
		// ���P�[���ݒ�
		if ("ja".equals(usrLang)) {
			this.charSubsets = SUBSET_JP;
			this.userLocale = Locale.JAPANESE;

		} else if ("zh".equals(usrLang)) {
			this.charSubsets = SUBSET_ZH;
			this.userLocale = Locale.SIMPLIFIED_CHINESE;

		} else if ("ko".equals(usrLang)) {
			this.charSubsets = SUBSET_KO;
			this.userLocale = Locale.KOREAN;

		} else {
			this.charSubsets = null;
			this.userLocale = Locale.ENGLISH;
		}
	}

	/**
	 * ���p�Ҏg�p����R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public String getUserLanguage() {
		return this.usrLang;
	}

	/**
	 * ���p�Ҏg�p����R�[�h�ɕR�Â����P�[����Ԃ�
	 * 
	 * @return ���P�[��
	 */
	public Locale getUserLocale() {
		return this.userLocale;
	}

	/**
	 * ���p�Ҏg�p����ɕR�Â��L�����N�^�[�Z�b�g��Ԃ�
	 * 
	 * @return �L�����N�^�[�Z�b�g
	 */
	public Subset[] getCharacterSubsets() {
		return this.charSubsets;
	}

	/**
	 * ���[�U�ɕR�Â���Џ���ݒ肷��
	 * 
	 * @param compInfo ��Џ��
	 */
	public void setCompanyInfo(TCompanyInfo compInfo) {
		this.compInfo = compInfo;
	}

	/**
	 * ���[�U�ɕR�Â���Џ����擾����
	 * 
	 * @return ��Џ��
	 */
	public TCompanyInfo getCompanyInfo() {
		return this.compInfo;
	}

	/**
	 * ���̑��f�[�^�̒ǉ�(�J�X�^�}�C�Y�p)
	 * 
	 * @param key �L�[
	 * @param value �l
	 */
	public void addData(String key, String value) {
		this.otherData.put(key, value);
	}

	/**
	 * ������ϊ�
	 * 
	 * @return �l�̕�����\��
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder("[");
		buff.append(Util.safeNull(usrCode)).append("/");
		buff.append(Util.safeNull(usrName)).append("/");
		buff.append(Util.safeNull(empCode)).append("/");
		buff.append(Util.safeNull(depCode)).append("/");
		buff.append(Util.safeNull(depNameS)).append("/");
		buff.append(Util.safeNull(prcKen)).append("/");
		buff.append(Util.safeNull(updKen)).append("/");
		buff.append(Util.safeNull(usrLang));
		buff.append("]");

		return buff.toString();
	}

	/**
	 * �f�[�^��String��Map�`���ɕϊ�
	 * 
	 * @return Map
	 */
	public Map<String, String> toStringMap() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("usrCode", this.usrCode); // ���[�U�[�R�[�h
		map.put("usrName", this.usrName); // ���[�U�[��
		map.put("empCode", this.empCode); // �Ј��R�[�h
		map.put("empNameS", this.empNameS); // �Ј�����
		map.put("prcKen", String.valueOf(this.prcKen)); // �����������x��
		map.put("updKen", String.valueOf(this.updKen)); // �X�V�������x��
		map.put("lngCode", this.usrLang); // ����R�[�h
		map.put("depCode", this.depCode); // ��������R�[�h
		map.put("depNameS", this.depNameS); // �������喼����
		map.put("isAccountChargePerson", BooleanUtil.toString(this.isAccountChargePerson)); // �o���S���ҋ敪

		// ���̑��f�[�^
		map.putAll(otherData);

		return map;
	}

	/**
	 * �f�[�^�𔽉f����.
	 * 
	 * @param map �f�[�^
	 */
	public void reflect(Map<String, String> map) {
		this.otherData = map;

		this.usrCode = map.get("usrCode"); // ���[�U�[�R�[�h
		this.usrName = (map.get("usrName")); // ���[�U�[��
		this.empCode = (map.get("empCode")); // �Ј��R�[�h

		// �����������x��
		try {
			this.prcKen = Util.isNullOrEmpty(map.get("prcKen")) ? 9 : Integer.parseInt(map.get("prcKen"));
		} catch (NumberFormatException e) {
			ServerErrorHandler.handledException(e);
		}

		// �X�V�������x��
		try {
			this.updKen = Util.isNullOrEmpty(map.get("updKen")) ? 9 : Integer.parseInt(map.get("updKen"));
		} catch (NumberFormatException e) {
			ServerErrorHandler.handledException(e);
		}

		setUserLanguage(map.get("lngCode")); // ����R�[�h
		this.depCode = map.get("depCode"); // ��������R�[�h
		this.depNameS = map.get("depNameS"); // �������喼����
		this.isAccountChargePerson = BooleanUtil.toBoolean(map.get("isAccountChargePerson")); // �o���S���ҋ敪
		this.empNameS = map.get("empNameS"); // �Ј�����
	}

	/**
	 * �w��L�[�ɑ΂����Џ���Ԃ�.(�J�X�^�}�C�Y�p)
	 * 
	 * @param key �L�[
	 * @return ��Џ��
	 */
	public String getData(String key) {
		return this.otherData.get(key);
	}
}
