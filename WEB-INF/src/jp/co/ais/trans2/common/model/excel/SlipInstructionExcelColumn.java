package jp.co.ais.trans2.common.model.excel;

import jp.co.ais.trans2.common.excel.*;

/**
 * �`�[�G�N�X�|�[�g���� �G�N�Z�����`
 */
public enum SlipInstructionExcelColumn implements ExcelImportableColumn {

	/** ��ЃR�[�h */
	KAI_CODE("C00596", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �`�[���t */
	SLIP_DATE("C00599", ExcelColumnType.DATE, true, 10, 3000),
	/** �`�[�ԍ� */
	SLIP_NO("C00605", ExcelColumnType.ALPHANUMERIC, true, 20, 6000),
	/** �s�ԍ� */
	GYO_NO("C01588", ExcelColumnType.INTEGER, true, 5, 2000),
	/** ��t����R�[�h */
	U_DEP_CODE("C04248", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �`�[�E�v�R�[�h */
	TEK_CODE("C01652", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �`�[�E�v */
	TEK("C00569", ExcelColumnType.STRING, true, 80, 13440),
	/** ���F�҃R�[�h */
	SYO_EMP_CODE("C00284", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** ���F�� */
	SYO_DATE("C03086", ExcelColumnType.DATE, true, 10, 3000),
	/** �˗��҃R�[�h */
	IRAI_EMP_CODE("C01945", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �˗�����R�[�h */
	IRAI_DEP_CODE("C01812", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �˗��� */
	IRAI_DATE("C01946", ExcelColumnType.DATE, true, 10, 3000),
	/** �V�X�e���敪 */
	SYS_KBN("C00980", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �`�[��ʃR�[�h */
	DEN_SYU_CODE("C00837", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �X�V�敪 */
	UPD_KBN("C01069", ExcelColumnType.INTEGER, true, 10, 3840),
	/** ���Z�敪 */
	KSN_KBN("C00610", ExcelColumnType.INTEGER, true, 10, 3840),
	/** �ȖڃR�[�h */
	KMK_CODE("C00572", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �⏕�R�[�h */
	HKM_CODE("C00890", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** ����R�[�h */
	UKM_CODE("C00876", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �v�㕔��R�[�h */
	DEP_CODE("C00571", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �����R�[�h */
	TRI_CODE("C00786", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �Ј��R�[�h */
	EMP_CODE("C00697", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �ʉ� */
	CUR_CODE("C00665", ExcelColumnType.STRING, true, 3, 3840),
	/** ���[�g */
	CUR_RATE("C00556", ExcelColumnType.DECIMAL, true, 17, 3840, 4),
	/** �ݎ؋敪 */
	DC_KBN("C01226", ExcelColumnType.INTEGER, true, 10, 3840),
	/** �ŋ敪 */
	ZEI_KBN("C00673", ExcelColumnType.INTEGER, true, 3, 3840),
	/** ����Ŋz */
	SZEI_KIN("C00674", ExcelColumnType.DECIMAL, true, 17, 5760, 4),
	/** ����ŃR�[�h */
	SZEI_CODE("C00573", ExcelColumnType.ALPHANUMERIC, true, 2, 3840),
	/** �s�E�v�R�[�h */
	GYO_TEK_CODE("C01560", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �s�E�v */
	GYO_TEK("C00119", ExcelColumnType.STRING, true, 80, 13440),
	/** �Ǘ��P�R�[�h */
	KNR1_CODE("C01561", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �Ǘ��Q�R�[�h */
	KNR2_CODE("C01562", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �Ǘ��R�R�[�h */
	KNR3_CODE("C01563", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �Ǘ��S�R�[�h */
	KNR4_CODE("C01564", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �Ǘ��T�R�[�h */
	KNR5_CODE("C01565", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �Ǘ��U�R�[�h */
	KNR6_CODE("C01566", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** ���v���ׂP */
	SWK_HM1("C01291", ExcelColumnType.STRING, true, 40, 3840),
	/** ���v���ׂQ */
	SWK_HM2("C01292", ExcelColumnType.STRING, true, 40, 3840),
	/** ���v���ׂR */
	SWK_HM3("C01293", ExcelColumnType.STRING, true, 40, 3840),
	/** �����d��敪 */
	AUTO_KBN("C10896", ExcelColumnType.INTEGER, true, 1, 3840),
	/** ������ */
	HAS_DATE("C00431", ExcelColumnType.DATE, true, 10, 3000),
	/** ����ȖڃR�[�h */
	AT_KMK_CODE("C10897", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** ����⏕�R�[�h */
	AT_HKM_CODE("C10898", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** �������R�[�h */
	AT_UKM_CODE("C10899", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** ���蕔��R�[�h */
	AT_DEP_CODE("C10900", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** �v���ЃR�[�h */
	K_KAI_CODE("C00570", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** �؜ߔԍ� */
	SEI_NO("C01178", ExcelColumnType.STRING, true, 40, 3840),
	/** �M�݋��z */
	SWK_KIN("C00576", ExcelColumnType.DECIMAL, true, 17, 5760, 4),
	/** ���͋��z */
	SWK_IN_KIN("C00574", ExcelColumnType.DECIMAL, true, 17, 5760, 4),
	/** �x���敪 */
	SIHA_KBN("C00682", ExcelColumnType.STRING, true, 2, 3840),
	/** �x���� */
	SIHA_DATE("C01098", ExcelColumnType.DATE, true, 10, 3000),
	/** �x�����@ */
	SIHA_HOU("C00233", ExcelColumnType.ALPHANUMERIC, true, 3, 3840),
	/** �ۗ��敪 */
	HORYU_KBN("C10267", ExcelColumnType.INTEGER, true, 1, 3840),
	/** ���������R�[�h */
	TJK_CODE("C00788", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** �����\��� */
	NYU_DATE("C01273", ExcelColumnType.DATE, true, 10, 3000),
	/** ����� */
	UKE_DATE("C00019", ExcelColumnType.DATE, true, 10, 3000),
	/** ��s�����R�[�h */
	CBK_CODE("C01879", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** �t�֋敪 */
	TUKE_KBN("C10903", ExcelColumnType.INTEGER, true, 1, 3840),
	/** ���͏���Ŋz */
	SZEI_IN_KIN("C00575", ExcelColumnType.DECIMAL, true, 17, 5760, 4);

	
	
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
	private SlipInstructionExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
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
	private SlipInstructionExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
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
