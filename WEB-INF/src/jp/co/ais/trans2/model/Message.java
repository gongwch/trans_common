package jp.co.ais.trans2.model;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * メッセージEntity
 * 
 * @author AIS
 */
public class Message extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6908147419259825923L;

	/** サポートメッセージID */
	protected String subMessageID = null;

	/** メッセージID */
	protected String messageID = null;

	/** メッセージバインド文字の単語ID */
	protected Object[] bindIds = null;

	/** エラータイプ */
	protected Enum errorType = null;

	/** データタイプ */
	protected Enum dataType = null;

	/** 行番号 */
	protected int rowNo = -1;

	/**
	 * コンストラクタ.
	 */
	public Message() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public Message(String messageID, Object... bindIds) {
		setMessage(messageID, bindIds);
	}

	/**
	 * bindIdsを取得する。
	 * 
	 * @return Object[] bindIds
	 */
	public Object[] getBindIds() {
		return bindIds;
	}

	/**
	 * bindIdsを設定する。
	 * 
	 * @param bindIds
	 */
	public void setBindIds(Object[] bindIds) {
		this.bindIds = bindIds;
	}

	/**
	 * messageIDを取得する。
	 * 
	 * @return String messageID
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * messageIDを設定する。
	 * 
	 * @param messageID
	 */
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	/**
	 * メッセージを設定する
	 * 
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public void setMessage(String messageID, Object... bindIds) {
		this.messageID = messageID;
		this.bindIds = bindIds;
	}

	/**
	 * クローン
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
	 * errDivisionを取得する。
	 * 
	 * @return Enum errDivision
	 */
	public Enum getDataType() {
		return dataType;
	}

	/**
	 * errDivisionを設定する。
	 * 
	 * @param errDivision
	 */
	public void setDataType(Enum errDivision) {
		this.dataType = errDivision;
	}

	/**
	 * gyoNoを取得する。
	 * 
	 * @return int gyoNo
	 */
	public int getRowNo() {
		return rowNo;
	}

	/**
	 * gyoNoを設定する。
	 * 
	 * @param gyoNo
	 */
	public void setRowNo(int gyoNo) {
		this.rowNo = gyoNo;
	}

	/**
	 * エラー区分
	 * 
	 * @return errorType エラー区分
	 */
	public Enum getErrorType() {
		return errorType;
	}

	/**
	 * エラー区分
	 * 
	 * @param errorType エラー区分
	 */
	public void setErrorType(Enum errorType) {
		this.errorType = errorType;
	}

	/**
	 * サポートメッセージID
	 * 
	 * @return サポートメッセージID
	 */
	public String getSubMessageID() {
		return subMessageID;
	}

	/**
	 * サポートメッセージID
	 * 
	 * @param subMessageID サポートメッセージID
	 */
	public void setSubMessageID(String subMessageID) {
		this.subMessageID = subMessageID;
	}

	/**
	 * メッセージがあるかどうか
	 * 
	 * @return true:あり
	 */
	public boolean hasMessage() {
		return !Util.isNullOrEmpty(messageID);
	}

}
