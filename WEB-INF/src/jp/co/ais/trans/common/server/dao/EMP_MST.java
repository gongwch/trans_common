package jp.co.ais.trans.common.server.dao;

import java.io.*;
import java.util.*;

/**
 * 社員マスタ(旧バージョン).<br>
 * KTの方を利用すること。
 */
public class EMP_MST implements Serializable {

	/** シリアルUID */
	public static final long serialVersionUID = 0L;

	/** テーブル名 */
	public static final String TABLE = "emp_mst";

	private String kai_code;

	private String emp_code;

	private String emp_name;

	private String emp_name_s;

	private String emp_name_k;

	private String emp_furi_bnk_code;

	private String emp_furi_stn_code;

	private String emp_ykn_no;

	private int emp_koza_kbn;

	private String emp_ykn_kana;

	private String emp_cbk_code;

	private Date str_date;

	private Date end_date;

	private Date inp_date;

	private Date upd_date;

	private String prg_id;

	private String usr_id;

	/**
	 * constractor.
	 */
	public EMP_MST() {
		//
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param kai_code 会社コード
	 * @param emp_code 社員コード
	 */
	public EMP_MST(String kai_code, String emp_code) {
		this.kai_code = kai_code;
		this.emp_code = emp_code;
	}

	/**
	 * object比較.
	 * 
	 * @param other
	 */
	public boolean equals(Object other) {

		if (!(other instanceof EMP_MST)) {
			return false;
		}

		EMP_MST castOther = (EMP_MST) other;

		return this.getKai_code() == castOther.getKai_code() && this.getEmp_code() == castOther.getEmp_code();
	}

	/**
	 * @return emp_cbk_code
	 */
	public String getEmp_cbk_code() {
		return emp_cbk_code;
	}

	/**
	 * @param emp_cbk_code 設定する emp_cbk_code
	 */
	public void setEmp_cbk_code(String emp_cbk_code) {
		this.emp_cbk_code = emp_cbk_code;
	}

	/**
	 * @return emp_code
	 */
	public String getEmp_code() {
		return emp_code;
	}

	/**
	 * @param emp_code 設定する emp_code
	 */
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	/**
	 * @return emp_furi_bnk_code
	 */
	public String getEmp_furi_bnk_code() {
		return emp_furi_bnk_code;
	}

	/**
	 * @param emp_furi_bnk_code 設定する emp_furi_bnk_code
	 */
	public void setEmp_furi_bnk_code(String emp_furi_bnk_code) {
		this.emp_furi_bnk_code = emp_furi_bnk_code;
	}

	/**
	 * @return emp_furi_stn_code
	 */
	public String getEmp_furi_stn_code() {
		return emp_furi_stn_code;
	}

	/**
	 * @param emp_furi_stn_code 設定する emp_furi_stn_code
	 */
	public void setEmp_furi_stn_code(String emp_furi_stn_code) {
		this.emp_furi_stn_code = emp_furi_stn_code;
	}

	/**
	 * @return emp_koza_kbn
	 */
	public int getEmp_koza_kbn() {
		return emp_koza_kbn;
	}

	/**
	 * @param emp_koza_kbn 設定する emp_koza_kbn
	 */
	public void setEmp_koza_kbn(int emp_koza_kbn) {
		this.emp_koza_kbn = emp_koza_kbn;
	}

	/**
	 * @return emp_name
	 */
	public String getEmp_name() {
		return emp_name;
	}

	/**
	 * @param emp_name 設定する emp_name
	 */
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	/**
	 * @return emp_name_k
	 */
	public String getEmp_name_k() {
		return emp_name_k;
	}

	/**
	 * @param emp_name_k 設定する emp_name_k
	 */
	public void setEmp_name_k(String emp_name_k) {
		this.emp_name_k = emp_name_k;
	}

	/**
	 * @return emp_name_s
	 */
	public String getEmp_name_s() {
		return emp_name_s;
	}

	/**
	 * @param emp_name_s 設定する emp_name_s
	 */
	public void setEmp_name_s(String emp_name_s) {
		this.emp_name_s = emp_name_s;
	}

	/**
	 * @return emp_ykn_kana
	 */
	public String getEmp_ykn_kana() {
		return emp_ykn_kana;
	}

	/**
	 * @param emp_ykn_kana 設定する emp_ykn_kana
	 */
	public void setEmp_ykn_kana(String emp_ykn_kana) {
		this.emp_ykn_kana = emp_ykn_kana;
	}

	/**
	 * @return emp_ykn_no
	 */
	public String getEmp_ykn_no() {
		return emp_ykn_no;
	}

	/**
	 * @param emp_ykn_no 設定する emp_ykn_no
	 */
	public void setEmp_ykn_no(String emp_ykn_no) {
		this.emp_ykn_no = emp_ykn_no;
	}

	/**
	 * @return end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date 設定する end_date
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return inp_date
	 */
	public Date getInp_date() {
		return inp_date;
	}

	/**
	 * @param inp_date 設定する inp_date
	 */
	public void setInp_date(Date inp_date) {
		this.inp_date = inp_date;
	}

	/**
	 * @return kai_code
	 */
	public String getKai_code() {
		return kai_code;
	}

	/**
	 * @param kai_code 設定する kai_code
	 */
	public void setKai_code(String kai_code) {
		this.kai_code = kai_code;
	}

	/**
	 * @return prg_id
	 */
	public String getPrg_id() {
		return prg_id;
	}

	/**
	 * @param prg_id 設定する prg_id
	 */
	public void setPrg_id(String prg_id) {
		this.prg_id = prg_id;
	}

	/**
	 * @return str_date
	 */
	public Date getStr_date() {
		return str_date;
	}

	/**
	 * @param str_date 設定する str_date
	 */
	public void setStr_date(Date str_date) {
		this.str_date = str_date;
	}

	/**
	 * @return upd_date
	 */
	public Date getUpd_date() {
		return upd_date;
	}

	/**
	 * @param upd_date 設定する upd_date
	 */
	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}

	/**
	 * @return usr_id
	 */
	public String getUsr_id() {
		return usr_id;
	}

	/**
	 * @param usr_id 設定する usr_id
	 */
	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}
}
