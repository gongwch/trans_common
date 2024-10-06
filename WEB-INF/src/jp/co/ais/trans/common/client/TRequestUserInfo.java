package jp.co.ais.trans.common.client;

import java.io.*;
import java.net.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;

/**
 * 利用者情報リクエスト class.
 */
public final class TRequestUserInfo extends TAppletClientBase {

	/**
	 * 利用者情報 リクエスト
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 * @throws TException
	 */
	public void request(String kaiCode, String usrCode) throws TException {
		try {

			// 送信するパラメータを設定
			addSendValues("flag", "base");
			addSendValues("kaiCode", kaiCode);
			addSendValues("usrCode", usrCode);
			addSendValues("ipadd", InetAddress.getLocalHost().getHostAddress());

			// 送信
			if (!request("common/userInfo")) {
				throw new TRequestException();
			}

			// サーブレットから送られてきた結果を情報保持クラスにセット
			TUserInfo userInfo = TClientLoginInfo.getInstance();

			Map<String, String> map = getResult();

			// ユーザ情報
			userInfo.reflect(map);

			// 会社情報
			TCompanyInfo compInfo = userInfo.getCompanyInfo();
			compInfo.reflect(map);

			// 通貨情報の取得 --
			addSendValues("flag", "currency");
			addSendValues("kaiCode", userInfo.getCompanyCode());

			if (!request("common/userInfo")) {
				throw new TRequestException();
			}

			compInfo.setCurrencyDigits(getResult());

			ClientLogger.debug(userInfo.toString());
			ClientLogger.debug(compInfo.toString());

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}
}
