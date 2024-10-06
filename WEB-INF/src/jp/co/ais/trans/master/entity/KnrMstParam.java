package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 管理マスタパラメータ
 */
public class KnrMstParam  {

	private String kaiCode = "";

	private String knrCode = "";

	private String knrName = "";

	private String knrNameS = "";

	private String knrNameK = "";

	private Date strDate = null;

	private Date endDate = null;

	private String knrCodeLike = "";
	
	private String knrNameLike = "";

	private String knrNameSLike = "";

	private String knrNameKLike = "";

	private String knrCodeBegin = "";

	private String knrCodeEnd = "";

	
	/**
	 *
	 * @return value
	 */
	public Date getEndDate() {
		return endDate;
	}

	
	/**
	 *
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	
	/**
	 *
	 * @param kaiCode
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrCode() {
		return knrCode;
	}

	
	/**
	 *
	 * @param knrCode
	 */
	public void setKnrCode(String knrCode) {
		this.knrCode = knrCode;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrCodeBegin() {
		return knrCodeBegin;
	}

	
	/**
	 *
	 * @param knrCodeBegin
	 */
	public void setKnrCodeBegin(String knrCodeBegin) {
		this.knrCodeBegin = knrCodeBegin;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrCodeEnd() {
		return knrCodeEnd;
	}

	
	/**
	 *
	 * @param knrCodeEnd
	 */
	public void setKnrCodeEnd(String knrCodeEnd) {
		this.knrCodeEnd = knrCodeEnd;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrName() {
		return knrName;
	}

	
	/**
	 *
	 * @param knrName
	 */
	public void setKnrName(String knrName) {
		this.knrName = knrName;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrNameK() {
		return knrNameK;
	}

	
	/**
	 *
	 * @param knrNameK
	 */
	public void setKnrNameK(String knrNameK) {
		this.knrNameK = knrNameK;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrNameKLike() {
		return knrNameKLike;
	}

	
	/**
	 *
	 * @param knrNameKLike
	 */
	public void setKnrNameKLike(String knrNameKLike) {
		this.knrNameKLike = knrNameKLike;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrNameLike() {
		return knrNameLike;
	}

	
	/**
	 *
	 * @param knrNameLike
	 */
	public void setKnrNameLike(String knrNameLike) {
		this.knrNameLike = knrNameLike;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrNameS() {
		return knrNameS;
	}

	
	/**
	 *
	 * @param knrNameS
	 */
	public void setKnrNameS(String knrNameS) {
		this.knrNameS = knrNameS;
	}

	
	/**
	 *
	 * @return value
	 */
	public String getKnrNameSLike() {
		return knrNameSLike;
	}

	
	/**
	 *
	 * @param knrNameSLike
	 */
	public void setKnrNameSLike(String knrNameSLike) {
		this.knrNameSLike = knrNameSLike;
	}

	/**
	 *
	 * @return value
	 */
	public String getKnrCodeLike() {
		return knrCodeLike;
	}

	
	/**
	 *
	 * @param knrCodeLike
	 */
	public void setKnrCodeLike(String knrCodeLike) {
		this.knrCodeLike = knrCodeLike;
	}

	
	/**
	 *
	 * @return value
	 */
	public Date getStrDate() {
		return strDate;
	}

	
	/**
	 *
	 * @param strDate
	 */
	public void setStrDate(Date strDate) {
		this.strDate = strDate;
	}

}
