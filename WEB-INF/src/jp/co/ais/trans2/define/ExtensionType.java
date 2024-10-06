package jp.co.ais.trans2.define;

/**
 * �g���q
 * 
 * @author AIS
 */
public enum ExtensionType {

	/** CSV�t�@�C�� */
	CSV("csv"),

	/** �e�L�X�g�t�@�C�� */
	TEXT("txt"),

	/** PDF�t�@�C�� */
	PDF("pdf"),

	/** XLS�t�@�C�� */
	XLS("xls"),

	/** XLSX�t�@�C�� */
	XLSX("xlsx");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ExtensionType(String value) {
		this.value = value;
	}

	/**
	 * extension��Ԃ�
	 * 
	 * @param extension �g���q
	 * @return �g���q��
	 */
	public static ExtensionType getType(String extension) {
		for (ExtensionType em : values()) {
			if (em.value == extension) {
				return em;
			}
		}

		return null;
	}

}
