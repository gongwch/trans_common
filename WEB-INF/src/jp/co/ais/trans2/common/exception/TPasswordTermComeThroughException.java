package jp.co.ais.trans2.common.exception;

/**
 * �p�X���[�h�̗L�����Ԑ؂�Exception
 * 
 * @author AIS
 */
public class TPasswordTermComeThroughException extends TWarningException {

	/** �V�X�e���Ǘ��҂��ǂ��� */
	protected boolean admin = false;

	/** �L�����Ԑ؂�܂ł̓��� */
	protected int daysBeforePasswordTermComeThrough = 0;

	/**
	 * �V�X�e���Ǘ��҂��ǂ����̎擾
	 * 
	 * @return admin �V�X�e���Ǘ��҂��ǂ���
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * �V�X�e���Ǘ��҂��ǂ����̐ݒ�
	 * 
	 * @param admin �V�X�e���Ǘ��҂��ǂ���
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return int
	 */
	public int getDaysBeforePasswordTermComeThrough() {
		return daysBeforePasswordTermComeThrough;
	}

	/**
	 * @param daysBeforePasswordTermComeThrough
	 */
	public void setDaysBeforePasswordTermComeThrough(int daysBeforePasswordTermComeThrough) {
		this.daysBeforePasswordTermComeThrough = daysBeforePasswordTermComeThrough;
	}

}
