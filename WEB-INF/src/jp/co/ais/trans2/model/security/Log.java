package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ログ
 * 
 * @author AIS
 */
public class Log extends TransferBase {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[LOG]dt=").append(DateUtil.toYMDHMSString(date));
		sb.append(",cmp=").append(company != null ? (company.getCode() + " " + company.getName()) : "");
		sb.append(",user=").append(user != null ? (user.getCode() + " " + user.getName()) : "");
		sb.append(",program=").append(program != null ? (program.getCode() + " " + program.getName()) : "");
		sb.append(",ip=").append(ip);
		sb.append(",msg=").append(message);
		sb.append(",info=").append(info);

		return sb.toString();
	}

	/** 時刻 */
	protected Date date = null;

	/** 会社 */
	protected Company company = null;

	/** ユーザー */
	protected User user = null;

	/** プログラム */
	protected Program program = null;

	/** IPアドレス */
	protected String ip = null;

	/** ログメッセージ */
	protected String message = null;

	/** ログ用追加情報 */
	protected String info = null;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * ログ用追加情報の取得
	 * 
	 * @return info ログ用追加情報
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * ログ用追加情報の設定
	 * 
	 * @param info ログ用追加情報
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
