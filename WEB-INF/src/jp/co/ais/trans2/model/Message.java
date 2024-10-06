package jp.co.ais.trans2.model;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���b�Z�[�WEntity
 * 
 * @author AIS
 */
public class Message extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6908147419259825923L;

	/** �T�|�[�g���b�Z�[�WID */
	protected String subMessageID = null;

	/** ���b�Z�[�WID */
	protected String messageID = null;

	/** ���b�Z�[�W�o�C���h�����̒P��ID */
	protected Object[] bindIds = null;

	/** �G���[�^�C�v */
	protected Enum errorType = null;

	/** �f�[�^�^�C�v */
	protected Enum dataType = null;

	/** �s�ԍ� */
	protected int rowNo = -1;

	/**
	 * �R���X�g���N�^.
	 */
	public Message() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public Message(String messageID, Object... bindIds) {
		setMessage(messageID, bindIds);
	}

	/**
	 * bindIds���擾����B
	 * 
	 * @return Object[] bindIds
	 */
	public Object[] getBindIds() {
		return bindIds;
	}

	/**
	 * bindIds��ݒ肷��B
	 * 
	 * @param bindIds
	 */
	public void setBindIds(Object[] bindIds) {
		this.bindIds = bindIds;
	}

	/**
	 * messageID���擾����B
	 * 
	 * @return String messageID
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * messageID��ݒ肷��B
	 * 
	 * @param messageID
	 */
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	/**
	 * ���b�Z�[�W��ݒ肷��
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public void setMessage(String messageID, Object... bindIds) {
		this.messageID = messageID;
		this.bindIds = bindIds;
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Message clone() {
		try {
			return (Message) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * errDivision���擾����B
	 * 
	 * @return Enum errDivision
	 */
	public Enum getDataType() {
		return dataType;
	}

	/**
	 * errDivision��ݒ肷��B
	 * 
	 * @param errDivision
	 */
	public void setDataType(Enum errDivision) {
		this.dataType = errDivision;
	}

	/**
	 * gyoNo���擾����B
	 * 
	 * @return int gyoNo
	 */
	public int getRowNo() {
		return rowNo;
	}

	/**
	 * gyoNo��ݒ肷��B
	 * 
	 * @param gyoNo
	 */
	public void setRowNo(int gyoNo) {
		this.rowNo = gyoNo;
	}

	/**
	 * �G���[�敪
	 * 
	 * @return errorType �G���[�敪
	 */
	public Enum getErrorType() {
		return errorType;
	}

	/**
	 * �G���[�敪
	 * 
	 * @param errorType �G���[�敪
	 */
	public void setErrorType(Enum errorType) {
		this.errorType = errorType;
	}

	/**
	 * �T�|�[�g���b�Z�[�WID
	 * 
	 * @return �T�|�[�g���b�Z�[�WID
	 */
	public String getSubMessageID() {
		return subMessageID;
	}

	/**
	 * �T�|�[�g���b�Z�[�WID
	 * 
	 * @param subMessageID �T�|�[�g���b�Z�[�WID
	 */
	public void setSubMessageID(String subMessageID) {
		this.subMessageID = subMessageID;
	}

	/**
	 * ���b�Z�[�W�����邩�ǂ���
	 * 
	 * @return true:����
	 */
	public boolean hasMessage() {
		return !Util.isNullOrEmpty(messageID);
	}

}
