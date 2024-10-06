package jp.co.ais.trans2.common.excel;

/**
 * �C���|�[�g�\Excel�J������`
 */
public interface ExcelImportableColumn {

	/**
	 * ���̂��擾����
	 * 
	 * @return ����
	 */
	public String getName();

	/**
	 * �񕝂��擾����
	 * 
	 * @return ��
	 */
	public int getWidth();

	/**
	 * �ő咷���擾����
	 * 
	 * @return �ő咷
	 */
	public int getMaxLength();

	/**
	 * �ő咷���擾����
	 * 
	 * @return �ő咷
	 */
	public int getDecimalPoint();

	/**
	 * �捞���s���J������
	 * 
	 * @return true:�捞���s��
	 */
	public boolean isImportColumn();

	/**
	 * �捞���K�{�̃J������
	 * 
	 * @return true:�K�{
	 */
	public boolean isMandatory();

	/**
	 * �J������ʂ��擾����
	 * 
	 * @return �J�������
	 */
	public ExcelColumnType getColumnType();

	/**
	 * �}�X�^���݃`�F�b�N���K�v�ȍ��ڂ�
	 * 
	 * @return true:�K�v
	 */
	public boolean isChecksRelation();
}
