package jp.co.ais.trans2.model.save;

import java.io.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * �I�u�W�F�N�g�ۑ�
 */
public class OBJ_SAVE extends TransferBase {

	/** ��ЃR�[�h */
	protected String kAI_CODE;

	/** ���[�UID */
	protected String uSR_ID;

	/** �v���O����ID */
	protected String pRG_ID;

	/** �v���O�����}�� */
	protected Integer sEQ;

	/** �ۑ��L�[ */
	protected String kEY;

	/** �ۑ��I�u�W�F�N�g */
	protected byte[] oBJECT;

	/** �I�u�W�F�N�g */
	protected Object saveObject;

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kai_code ��ЃR�[�h
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * �ۑ��L�[
	 * 
	 * @return �ۑ��L�[
	 */
	public String getKEY() {
		return kEY;
	}

	/**
	 * �ۑ��L�[
	 * 
	 * @param key �ۑ��L�[
	 */
	public void setKEY(String key) {
		kEY = key;
	}

	/**
	 * �v���O����ID
	 * 
	 * @return �v���O����ID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * �v���O����ID
	 * 
	 * @param prg_id �v���O����ID
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * �v���O�����}��
	 * 
	 * @return �v���O�����}��
	 */
	public Integer getSEQ() {
		return sEQ;
	}

	/**
	 * �v���O�����}��
	 * 
	 * @param seq �v���O�����}��
	 */
	public void setSEQ(Integer seq) {
		sEQ = seq;
	}

	/**
	 * ���[�UID
	 * 
	 * @return ���[�UID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ���[�UID
	 * 
	 * @param usr_id ���[�UID
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * �I�u�W�F�N�g(������)
	 * 
	 * @return oBJECT �I�u�W�F�N�g(������)
	 */
	public byte[] getOBJECT() {
		return oBJECT;
	}

	/**
	 * �I�u�W�F�N�g(������)
	 * 
	 * @param object �I�u�W�F�N�g(������)
	 */
	public void setOBJECT(byte[] object) {
		oBJECT = object;

		try {
			if (object != null) {
				this.saveObject = ResourceUtil.toObject(ResourceUtil.toBinaryInZip(object));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �I�u�W�F�N�g�̎擾
	 * 
	 * @return �I�u�W�F�N�g
	 */
	public Object getSaveObject() {
		return saveObject;
	}

	/**
	 * �I�u�W�F�N�g�Z�b�g
	 * 
	 * @param obj �I�u�W�F�N�g
	 */
	public void setSaveObject(Object obj) {
		this.saveObject = obj;

		try {
			if (obj != null) {
				this.oBJECT = ResourceUtil.zipBinary("bytes", ResourceUtil.toBinarry(obj));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
