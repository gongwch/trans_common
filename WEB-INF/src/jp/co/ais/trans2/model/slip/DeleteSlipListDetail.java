package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans2.common.ledger.*;

/**
 * �폜�`�[���X�g����
 * 
 * @author AIS
 */
public class DeleteSlipListDetail extends LedgerSheetDetail {

	/** �폜�� */
	protected Date delDate = null;

	/** �폜�`�[NO */
	protected String delSlipNo = "";

	/** �폜���[�U�[ID */
	protected String usrID = "";

	/** �폜���[�U�[�� */
	protected String usrName = "";

	public String getDelSlipNo() {
		return delSlipNo;
	}

	public void setDelSlipNo(String delSlipNo) {
		this.delSlipNo = delSlipNo;
	}

	/**
	 * �폜���̎擾
	 * 
	 * @return String
	 */
	public Date getDelDate() {
		return delDate;
	}

	/**
	 * �폜���̐ݒ肷��
	 * 
	 * @param delDate
	 */
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	/**
	 * �폜���[�U�[ID���擾����
	 * 
	 * @return ���[�U�[ID
	 */
	public String getUsrID() {
		return usrID;
	}

	/**
	 * �폜���[�U�[ID��ݒ肷��
	 * 
	 * @param usrID
	 */
	public void setUsrID(String usrID) {
		this.usrID = usrID;
	}

	/**
	 * �폜���[�U�[�����擾����
	 * 
	 * @return ���[�U�[��
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * �폜���[�U�[����ݒ肷��
	 * 
	 * @param usrName
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

}
