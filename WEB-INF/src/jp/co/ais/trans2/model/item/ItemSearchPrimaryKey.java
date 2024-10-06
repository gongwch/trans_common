package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.dt.*;

/**
 * �ȖځE�⏕�E����}�X�^����<br>
 * �����v���C�}���L�[
 */
public class ItemSearchPrimaryKey extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String KAI_CODE;

	/** �ȖڃR�[�h */
	protected String KMK_CODE;

	/** �⏕�R�[�h */
	protected String HKM_CODE;

	/** ����R�[�h */
	protected String UKM_CODE;

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected ItemSearchPrimaryKey clone() throws CloneNotSupportedException {
		return (ItemSearchPrimaryKey) super.clone();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param kAI_CODE
	 * @param kMK_CODE
	 * @param hKM_CODE
	 * @param uKM_CODE
	 */
	public ItemSearchPrimaryKey(String kAI_CODE, String kMK_CODE, String hKM_CODE, String uKM_CODE) {
		super();
		KAI_CODE = kAI_CODE;
		KMK_CODE = kMK_CODE;
		HKM_CODE = hKM_CODE;
		UKM_CODE = uKM_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		KAI_CODE = kAI_CODE;
	}

	/**
	 * �ȖڃR�[�h
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getKMK_CODE() {
		return KMK_CODE;
	}

	/**
	 * �ȖڃR�[�h
	 * 
	 * @param kMK_CODE
	 */
	public void setKMK_CODE(String kMK_CODE) {
		KMK_CODE = kMK_CODE;
	}

	/**
	 * �⏕�R�[�h
	 * 
	 * @return �⏕�R�[�h
	 */
	public String getHKM_CODE() {
		return HKM_CODE;
	}

	/**
	 * �⏕�R�[�h
	 * 
	 * @param hKM_CODE
	 */
	public void setHKM_CODE(String hKM_CODE) {
		HKM_CODE = hKM_CODE;
	}

	/**
	 * ����R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public String getUKM_CODE() {
		return UKM_CODE;
	}

	/**
	 * ����R�[�h
	 * 
	 * @param uKM_CODE
	 */
	public void setUKM_CODE(String uKM_CODE) {
		UKM_CODE = uKM_CODE;
	}

}
