package jp.co.ais.trans2.model.slip;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �`�[�tⳋ@�\
 */
public class SWK_TAG extends TransferBase {

	/** �e�[�u���� */
	public static final String TABLE = "SWK_TAG";

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �`�[�ԍ� */
	protected String SWK_DEN_NO = null;

	/** �A�� */
	protected int SEQ = 0;

	/** �tⳃR�[�h */
	protected String TAG_CODE = null;

	/** �tⳐF */
	protected Color TAG_COLOR = null;

	/** �tⳃ^�C�g�� */
	protected String TAG_TITLE = null;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** �X�V���t */
	protected Date UPD_DATE = null;

	/** �v���O����ID */
	protected String PRG_ID = null;

	/** ���[�UID */
	protected String USR_ID = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return KAI_CODE ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * �`�[�ԍ��̎擾
	 * 
	 * @return SWK_DEN_NO �`�[�ԍ�
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * �`�[�ԍ��̐ݒ�
	 * 
	 * @param SWK_DEN_NO �`�[�ԍ�
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * �A�Ԃ̎擾
	 * 
	 * @return SEQ �A��
	 */
	public int getSEQ() {
		return SEQ;
	}

	/**
	 * �A�Ԃ̐ݒ�
	 * 
	 * @param SEQ �A��
	 */
	public void setSEQ(int SEQ) {
		this.SEQ = SEQ;
	}

	/**
	 * �tⳃR�[�h�̎擾
	 * 
	 * @return TAG_CODE �tⳃR�[�h
	 */
	public String getTAG_CODE() {
		return TAG_CODE;
	}

	/**
	 * �tⳃR�[�h�̐ݒ�
	 * 
	 * @param TAG_CODE �tⳃR�[�h
	 */
	public void setTAG_CODE(String TAG_CODE) {
		this.TAG_CODE = TAG_CODE;
	}

	/**
	 * �tⳐF�̎擾
	 * 
	 * @return TAG_COLOR �tⳐF
	 */
	public Color getTAG_COLOR() {
		return TAG_COLOR;
	}

	/**
	 * �tⳐF�̐ݒ�
	 * 
	 * @param TAG_COLOR �tⳐF
	 */
	public void setTAG_COLOR(Color TAG_COLOR) {
		this.TAG_COLOR = TAG_COLOR;
	}

	/**
	 * �tⳃ^�C�g���̎擾
	 * 
	 * @return TAG_TITLE �tⳃ^�C�g��
	 */
	public String getTAG_TITLE() {
		return TAG_TITLE;
	}

	/**
	 * �tⳃ^�C�g���̐ݒ�
	 * 
	 * @param TAG_TITLE �tⳃ^�C�g��
	 */
	public void setTAG_TITLE(String TAG_TITLE) {
		this.TAG_TITLE = TAG_TITLE;
	}

	/**
	 * �o�^���t�̎擾
	 * 
	 * @return INP_DATE �o�^���t
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^���t�̐ݒ�
	 * 
	 * @param INP_DATE �o�^���t
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V���t�̎擾
	 * 
	 * @return UPD_DATE �X�V���t
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V���t�̐ݒ�
	 * 
	 * @param UPD_DATE �X�V���t
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O����ID�̎擾
	 * 
	 * @return PRG_ID �v���O����ID
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O����ID�̐ݒ�
	 * 
	 * @param PRG_ID �v���O����ID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�UID�̎擾
	 * 
	 * @return USR_ID ���[�UID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�UID�̐ݒ�
	 * 
	 * @param USR_ID ���[�UID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
