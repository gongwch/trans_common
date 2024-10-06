package jp.co.ais.trans.common.client;

import java.io.*;

import jp.co.ais.trans.common.log.*;

/**
 * メニュー(プログラム)切り替え通知
 */
public class TRequestProgram extends TRequestServiceBase {

	/**
	 * コンストラクタ.
	 * 
	 * @param access リクエスト
	 */
	public TRequestProgram(TRequestBase access) {
		super(access, "program");
	}

	/**
	 * 開始
	 * 
	 * @param prgCode
	 * @return true:成功
	 * @throws IOException
	 */
	public boolean start(String prgCode) throws IOException {
		return this.request("start", prgCode);
	}

	/**
	 * 終了（プログラム指定）
	 * 
	 * @param prgCode
	 * @return true:成功
	 * @throws IOException
	 */
	public boolean endPrg(String prgCode) throws IOException {
		return this.request("endPrg", prgCode);
	}

	/**
	 * 終了
	 * 
	 * @param prgCode
	 * @return true:成功
	 * @throws IOException
	 */
	public boolean end(String prgCode) throws IOException {
		return this.request("end", prgCode);
	}

	/**
	 * サービス呼び出し.
	 * 
	 * @param status
	 * @param prgCode
	 * @return true:成功
	 * @throws IOException
	 */
	private boolean request(String status, String prgCode) throws IOException {

		super.access.addSendValues("status", status);
		super.access.addSendValues("prgCode", prgCode);

		if (!super.request()) {
			ClientLogger.error("request error:" + access.getErrorMessage());
			return false;
		}

		if (!super.access.isNoDataResponse()) {
			return false;
		}

		return true;
	}

}