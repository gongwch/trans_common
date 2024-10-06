package jp.co.ais.trans.common.gui.dnd;

/**
 * TOrderedTable �f�[�^�ݒ�p�G���e�B�e�B
 */
public class OrderedData {

	/** ���͖��� */
	private String editableName;

	/** �\���敪 */
	private int hyjKbn;

	/** �R�[�h */
	private String code;

	/** ���� */
	private String name;

	/**
	 * �\���敪���擾����
	 * 
	 * @return �\���敪
	 */
	public int getHyjKbn() {
		return hyjKbn;
	}

	/**
	 * �\���敪��ݒ肷��
	 * 
	 * @param hyjKbn
	 */
	public void setHyjKbn(int hyjKbn) {
		this.hyjKbn = hyjKbn;
	}

	/**
	 * �R�[�h���擾����
	 * 
	 * @return �R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �R�[�h��ݒ肷��
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ���̂��擾����
	 * 
	 * @return ����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���̂�ݒ肷��
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���͖��̂��擾����
	 * 
	 * @return ���͖���
	 */
	public String getEditableName() {
		return editableName;
	}

	/**
	 * ���͖��̂�ݒ肷��
	 * 
	 * @param editableName
	 */
	public void setEditableName(String editableName) {
		this.editableName = editableName;
	}
	
	/**
	 * �G���e�B�e�B�̔�r�Ɏg�p����B
	 * 
	 * @param obj ��r
	 * @return boolean
	 */
	public boolean equals(OrderedData obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

}
