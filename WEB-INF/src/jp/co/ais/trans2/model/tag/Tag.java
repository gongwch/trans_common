package jp.co.ais.trans2.model.tag;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * @author AIS
 */
public class Tag extends TransferBase {

	/** 会社コードの定義 */
	protected String companyCode = null;

	/** 付箋コードの定義 */
	protected String code = null;

	/** 付箋色の定義 */
	protected Color color = null;

	/** 付箋タイトルの定義 */
	protected String title = null;

	/** 登録日時の定義 */
	protected Date inpDate = null;

	/** 更新日時の定義 */
	protected Date updDate = null;

	/** プログラムIDの定義 */
	protected String programCode = null;

	/** ユーザーIDの定義 */
	protected String userCode = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 付箋コードの取得
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 付箋コードの設定
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 付箋色の取得
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 付箋色の設定
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 付箋タイトルの取得
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 付箋タイトルの設定
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 登録日時の取得
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * 登録日時の設定
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * 登録日時の取得
	 * 
	 * @return updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * 登録日時の設定
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * プログラムIDの取得
	 * 
	 * @return programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * プログラムIDの設定
	 * 
	 * @param programCode
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * ユーザーIDの取得
	 * 
	 * @return userCode
	 */
	public String getUserCode() {
		return programCode;
	}

	/**
	 * ユーザーIDの設定
	 * 
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
