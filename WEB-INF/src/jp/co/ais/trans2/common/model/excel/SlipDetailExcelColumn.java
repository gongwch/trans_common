package jp.co.ais.trans2.common.model.excel;

import jp.co.ais.trans2.common.excel.*;

/**
 * �`�[���׃G�N�Z�����`
 */
public enum SlipDetailExcelColumn implements ExcelImportableColumn {

	/** �v���ЃR�[�h */
	K_KAI_CODE("�v���ЃR�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �v���� */
	K_KAI_NAME("�v����", ExcelColumnType.STRING, true, 20, 6000),
	/** �v�㕔��R�[�h */
	K_DEP_CODE("�v�㕔��R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �v�㕔�� */
	K_DEP_NAME("�v�㕔��", ExcelColumnType.STRING, false, 20, 6000),
	/** �ȖڃR�[�h */
	KMK_CODE("�ȖڃR�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ȗږ� */
	KMK_NAME("�Ȗږ�", ExcelColumnType.STRING, false, 20, 6000),
	/** �⏕�R�[�h */
	HKM_CODE("�⏕�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �⏕�� */
	HKM_NAME("�⏕��", ExcelColumnType.STRING, false, 20, 6000),
	/** ����R�[�h */
	UKM_CODE("����R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** ���� */
	UKM_NAME("����", ExcelColumnType.STRING, false, 20, 6000),
	/** ������ */
	HAS_DATE("������", ExcelColumnType.DATE, true, 10, 3000),
	/** �ʉ� */
	CUR_CODE("�ʉ�", ExcelColumnType.STRING, true, 3, 1472),
	/** �� */
	ZEI("��", ExcelColumnType.STRING, true, 3, 3840),
	/** ����ŃR�[�h */
	SZEI_CODE("����ŃR�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 2, 3840),
	/** ����Ŗ� */
	SZEI_NAME("����Ŗ�", ExcelColumnType.STRING, false, 20, 6000),
	/** �ؕ����z(�ō�) */
	DR_KIN("�ؕ����z(�ō�)", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** �ݕ����z(�ō�) */
	CR_KIN("�ݕ����z(�ō�)", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** �ؕ�����Ŋz */
	DR_TAX("�ؕ�����Ŋz", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** �ݕ�����Ŋz */
	CR_TAX("�ݕ�����Ŋz", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** �s�E�v�R�[�h */
	GYO_TEK_CODE("�s�E�v�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3000),
	/** �s�E�v */
	GYO_TEK("�s�E�v", ExcelColumnType.STRING, true, 80, 13440),
	/** �����R�[�h */
	TRI_CODE("�����R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** ����於 */
	TRI_NAME("����於", ExcelColumnType.STRING, false, 40, 10240),
	/** �Ј��R�[�h */
	EMP_CODE("�Ј��R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ј��� */
	EMP_NAME("�Ј���", ExcelColumnType.STRING, false, 20, 6000),
	/** �Ǘ��P�R�[�h */
	KNR1_CODE("�Ǘ��P�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ǘ��P�� */
	KNR1_NAME("�Ǘ��P��", ExcelColumnType.STRING, false, 20, 6000),
	/** �Ǘ��Q�R�[�h */
	KNR2_CODE("�Ǘ��Q�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ǘ��Q�� */
	KNR2_NAME("�Ǘ��Q��", ExcelColumnType.STRING, false, 20, 6000),
	/** �Ǘ��R�R�[�h */
	KNR3_CODE("�Ǘ��R�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ǘ��R�� */
	KNR3_NAME("�Ǘ��R��", ExcelColumnType.STRING, false, 20, 6000),
	/** �Ǘ��S�R�[�h */
	KNR4_CODE("�Ǘ��S�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ǘ��S�� */
	KNR4_NAME("�Ǘ��S��", ExcelColumnType.STRING, false, 20, 6000),
	/** �Ǘ��T�R�[�h */
	KNR5_CODE("�Ǘ��T�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ǘ��T�� */
	KNR5_NAME("�Ǘ��T��", ExcelColumnType.STRING, false, 20, 6000),
	/** �Ǘ��U�R�[�h */
	KNR6_CODE("�Ǘ��U�R�[�h", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** �Ǘ��U�� */
	KNR6_NAME("�Ǘ��U��", ExcelColumnType.STRING, false, 20, 6000),
	/** ���v���ׂP */
	SWK_HM1("���v���ׂP", ExcelColumnType.STRING, true, 40, 3840),
	/** ���v���ׂQ */
	SWK_HM2("���v���ׂQ", ExcelColumnType.STRING, true, 40, 3840),
	/** ���v���ׂR */
	SWK_HM3("���v���ׂR", ExcelColumnType.STRING, true, 40, 3840),;

	/** ���� */
	protected String name;

	/** �J������� */
	protected ExcelColumnType columnType;

	/** �捞���s���J������ */
	protected boolean isImportColumn;

	/** �ő咷 */
	protected int maxLength;

	/** �����_�ȉ����� */
	protected int decimalPoint = 0;

	/** �� */
	protected int width;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param name
	 * @param columnType
	 * @param isImportColumn
	 * @param maxLength
	 * @param width
	 */
	private SlipDetailExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
		int width) {
		this.name = name;
		this.columnType = columnType;
		this.isImportColumn = isImportColumn;
		this.maxLength = maxLength;
		this.width = width;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param name
	 * @param columnType
	 * @param isImportColumn
	 * @param maxLength
	 * @param decimalPoint
	 * @param width
	 */
	private SlipDetailExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
		int width, int decimalPoint) {
		this.name = name;
		this.columnType = columnType;
		this.isImportColumn = isImportColumn;
		this.maxLength = maxLength;
		this.decimalPoint = decimalPoint;
		this.width = width;
	}

	/**
	 * �񖼂��擾����
	 * 
	 * @return ��
	 */
	public String getName() {
		switch (this) {
			//
		}
		return this.name;
	}

	/**
	 * �ő咷���擾����
	 * 
	 * @return �ő咷
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * �ő咷���擾����
	 * 
	 * @return �ő咷
	 */
	public int getDecimalPoint() {
		return this.decimalPoint;
	}

	/**
	 * �񕝂��擾����
	 * 
	 * @return ��
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * �捞���s���J������
	 * 
	 * @return true:�捞���s��
	 */
	public boolean isImportColumn() {
		return this.isImportColumn;
	}

	/**
	 * �J������ʂ��擾����
	 * 
	 * @return �J�������
	 */
	public ExcelColumnType getColumnType() {
		return this.columnType;

	}

	/**
	 * �捞���K�{�̃J������<br>
	 * �K��false
	 * 
	 * @return �K�{�łȂ�
	 */
	public boolean isMandatory() {
		return false;
	}

	/**
	 * �}�X�^���݃`�F�b�N���s���J������
	 */
	public boolean isChecksRelation() {
		return false;
	}

}
