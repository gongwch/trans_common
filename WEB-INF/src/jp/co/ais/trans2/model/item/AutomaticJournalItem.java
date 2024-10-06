package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * 
 */
public class AutomaticJournalItem extends TransferBase implements TReferable {

	/** ��ЃR�[�h */
	private String kAI_CODE = "";

	/** �Ȗڐ���敪 */
	private String kMK_CNT;

	/** �Ȗڐ���敪�� */
	private String kMK_CNT_NAME;

	/** ����R�[�h */
	private String dEP_CODE;

	/** ���喼�� */
	private String dEP_NAME;

	/** ���嗪�� */
	private String dEP_NAME_S;

	/** �ȖڃR�[�h */
	private String kMK_CODE;

	/** �Ȗږ��� */
	private String kMK_NAME;

	/** �Ȗڗ��� */
	private String kMK_NAME_S;

	/** �⏕�R�[�h */
	private String hKM_CODE;

	/** �⏕���� */
	private String hKM_NAME;

	/** �⏕���� */
	private String hKM_NAME_S;

	/** ����R�[�h */
	private String uKM_CODE;

	/** ���󖼏� */
	private String uKM_NAME;

	/** ���󗪏� */
	private String uKM_NAME_S;

	/** �ݎ؋敪 */
	private String dC_KBN;

	/** �Ȗ� */
	private Item item;

	/**
	 * @return String
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * @return int
	 */
	public String getKMK_CNT() {
		return kMK_CNT;
	}

	/**
	 * @param kMK_CNT
	 */
	public void setKMK_CNT(String kMK_CNT) {
		this.kMK_CNT = kMK_CNT;
	}

	/**
	 * @return String
	 */
	public String getKMK_CNT_NAME() {
		return kMK_CNT_NAME;
	}

	/**
	 * @param kMK_CNT_NAME
	 */
	public void setKMK_CNT_NAME(String kMK_CNT_NAME) {
		this.kMK_CNT_NAME = kMK_CNT_NAME;
	}

	/**
	 * @return String
	 */
	public String getKMK_CODE() {
		return kMK_CODE;
	}

	/**
	 * @param kMK_CODE
	 */
	public void setKMK_CODE(String kMK_CODE) {
		this.kMK_CODE = kMK_CODE;
	}

	/**
	 * @return String
	 */
	public String getHKM_CODE() {
		return hKM_CODE;
	}

	/**
	 * @param hKM_CODE
	 */
	public void setHKM_CODE(String hKM_CODE) {
		this.hKM_CODE = hKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getUKM_CODE() {
		return uKM_CODE;
	}

	/**
	 * @param uKM_CODE
	 */
	public void setUKM_CODE(String uKM_CODE) {
		this.uKM_CODE = uKM_CODE;
	}

	/**
	 * @return String
	 */
	public String getDEP_CODE() {
		return dEP_CODE;
	}

	/**
	 * @param dEP_CODE
	 */
	public void setDEP_CODE(String dEP_CODE) {
		this.dEP_CODE = dEP_CODE;
	}

	/**
	 * @return dC_KBN
	 */
	public String getDC_KBN() {
		return dC_KBN;
	}

	/**
	 * @param dC_KBN
	 */
	public void setDC_KBN(String dC_KBN) {
		this.dC_KBN = dC_KBN;
	}

	/**
	 * @return String
	 */
	public String getKMK_NAMES() {
		return kMK_NAME_S;
	}

	/**
	 * @param kMK_NAME_S
	 */
	public void setKMK_NAMES(String kMK_NAME_S) {
		this.kMK_NAME_S = kMK_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getHKM_NAMES() {
		return hKM_NAME_S;
	}

	/**
	 * @param hKM_NAME_S
	 */
	public void setHKM_NAMES(String hKM_NAME_S) {
		this.hKM_NAME_S = hKM_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getUKM_NAMES() {
		return uKM_NAME_S;
	}

	/**
	 * @param uKM_NAME_S
	 */
	public void setUKM_NAMES(String uKM_NAME_S) {
		this.uKM_NAME_S = uKM_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getDEP_NAMES() {
		return dEP_NAME_S;
	}

	/**
	 * @param dEP_NAME_S
	 */
	public void setDEP_NAMES(String dEP_NAME_S) {
		this.dEP_NAME_S = dEP_NAME_S;
	}

	/**
	 * @return String
	 */
	public String getKMK_NAME() {
		return kMK_NAME;
	}

	/**
	 * @param kMK_NAME
	 */
	public void setKMK_NAME(String kMK_NAME) {
		this.kMK_NAME = kMK_NAME;
	}

	/**
	 * @return String
	 */
	public String getHKM_NAME() {
		return hKM_NAME;
	}

	/**
	 * @param hKM_NAME
	 */
	public void setHKM_NAME(String hKM_NAME) {
		this.hKM_NAME = hKM_NAME;
	}

	/**
	 * @return String
	 */
	public String getUKM_NAME() {
		return uKM_NAME;
	}

	/**
	 * @param uKM_NAME
	 */
	public void setUKM_NAME(String uKM_NAME) {
		this.uKM_NAME = uKM_NAME;
	}

	/**
	 * @return String
	 */
	public String getDEP_NAME() {
		return dEP_NAME;
	}

	/**
	 * @param dEP_NAME
	 */
	public void setDEP_NAME(String dEP_NAME) {
		this.dEP_NAME = dEP_NAME;
	}

	/**
	 * @see jp.co.ais.trans2.model.TReferable#getCode()
	 */
	public String getCode() {
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.model.TReferable#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		// �Ȃ�
	}

	/**
	 * @see jp.co.ais.trans2.model.TReferable#getNames()
	 */
	public String getNames() {
		return null;
	}

	/**
	 * @see jp.co.ais.trans2.model.TReferable#setNames(java.lang.String)
	 */
	public void setNames(String names) {
		// �Ȃ�
	}

	/**
	 * @return item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item 
	 */
	public void setItem(Item item) {
		this.item = item;
	}

}
