package jp.co.ais.trans.common.bizui;

/**
 * �x��������ɕR�t�����
 */
public class PaymentInformationParameter {
	/** �x�����@�R�[�h */
	private String sihaHouCode = "";

	/** �x�����@���� */
	private String sihaHouName = "";

	/** �U�o��s�R�[�h */
	private String huriCode = "";

	/** �U�o��s���� */
	private String huriName = "";

	/** �x���敪 */
	private String sihaKbn = "";

	/** �x���� */
	private String sihaDate = "";
	
	/** �x�������R�[�h */
	private String sihaNaiCode = "";
	
	/** ����t���O  */
	private boolean flag = true;

	/**
	 * �x�����@�R�[�h��ݒ肷��B<BR>
	 * 
	 * @param sihaHouCode �x�����@�R�[�h
	 * 
	 */
	public void setSihaHouCode(String sihaHouCode) {
		this.sihaHouCode = sihaHouCode;
	}

	/**
	 * �x�����@�R�[�h��Ԃ��B<BR>
	 * 
	 * @return sihaHouCode �x�����@�R�[�h
	 * 
	 */
	public String getSihaHouCode() {
		return sihaHouCode;
	}

	/**
	 * �x�����@���̂�ݒ肷��B<BR>
	 * 
	 * @param sihaHouName �x�����@����
	 * 
	 */
	public void setSihaHouName(String sihaHouName) {
		this.sihaHouName = sihaHouName;
	}

	/**
	 * �x�����@���̂�Ԃ��B<BR>
	 * 
	 * @return sihaHouName �x�����@����
	 * 
	 */
	public String getSihaHouName() {
		return sihaHouName;
	}

	/**
	 * �U�o��s�R�[�h��ݒ肷��B<BR>
	 * 
	 * @param huriCode �U�o��s�R�[�h
	 * 
	 */
	public void setHuriCode(String huriCode) {
		this.huriCode = huriCode;
	}

	/**
	 * �U�o��s�R�[�h��Ԃ��B<BR>
	 * 
	 * @return huriCode �U�o��s�R�[�h
	 * 
	 */
	public String getHuriCode() {
		return huriCode;
	}

	/**
	 * �U�o��s���̂�ݒ肷��B<BR>
	 * 
	 * @param huriName �U�o��s����
	 * 
	 */
	public void setHuriName(String huriName) {
		this.huriName = huriName;
	}

	/**
	 * �U�o��s���̂�Ԃ��B<BR>
	 * 
	 * @return huriName �U�o��s����
	 * 
	 */
	public String getHuriName() {
		return huriName;
	}

	/**
	 * �x���敪��ݒ肷��B<BR>
	 * 
	 * @param sihaKbn �x���敪
	 * 
	 */
	public void setSihaKbn(String sihaKbn) {
		this.sihaKbn = sihaKbn;
	}

	/**
	 * �x���敪��Ԃ��B<BR>
	 * 
	 * @return sihaKbn �x���敪
	 * 
	 */
	public String getSihaKbn() {
		return sihaKbn;
	}

	/**
	 * �x������ݒ肷��B<BR>
	 * 
	 * @param sihaDate �x����
	 * 
	 */
	public void setSihaDate(String sihaDate) {
		this.sihaDate = sihaDate;
	}

	/**
	 * �x������Ԃ��B<BR>
	 * 
	 * @return sihaDate �x����
	 * 
	 */
	public String getSihaDate() {
		return sihaDate;
	}
	
	/**
	 * �x�������R�[�h��ݒ肷��B<BR>
	 * 
	 * @param sihaNaiCode �x�������R�[�h
	 * 
	 */
	public void setSihaNaiCode(String sihaNaiCode) {
		this.sihaNaiCode = sihaNaiCode;
	}

	/**
	 * �ݒ肪���������ǂ�����Ԃ��B<BR>
	 * 
	 * @return sihaNaiCode �x�������R�[�h
	 * 
	 */
	public String getSihaNaiCode() {
		return sihaNaiCode;
	}
	
	
	/**
	 * ����t���O��ݒ肷��B<BR>
	 * 
	 * @param flag ����t���O true:�f�[�^�L�� false:�f�[�^�Ȃ�
	 * 
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * ����t���O��Ԃ��B<BR>
	 * 
	 * @return flag ����t���O true:�f�[�^�L�� false:�f�[�^�Ȃ�
	 * 
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * �l�������l�ɖ߂�
	 * 
	 */
	public void clear() {
		sihaHouCode = "";
		sihaHouName = "";
		huriCode = "";
		huriName = "";
		sihaKbn = "";
		sihaDate = "";
		sihaNaiCode = "";
	}
}
