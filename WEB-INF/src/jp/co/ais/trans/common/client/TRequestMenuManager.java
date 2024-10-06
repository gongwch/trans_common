package jp.co.ais.trans.common.client;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.server.dao.*;
import jp.co.ais.trans.common.util.Util;

/**
 * 利用者情報リクエスト class.
 */
public class TRequestMenuManager extends TRequestServiceBase {

	private List resList;

	/**
	 * コンストラクタ
	 * 
	 * @param access リクエストクラス
	 */
	public TRequestMenuManager(TRequestBase access) {
		super(access, "MenuManager");
	}

	/**
	 * メニュー情報を取得。 true 成功：結果のListをBeanに変換し、trueを返す
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 * @return false 失敗
	 * @throws IOException
	 */
	public boolean request(String kaiCode, String usrCode) throws IOException {

		if (Util.isNullOrEmpty(kaiCode) || Util.isNullOrEmpty(usrCode)) {
			throw new IllegalArgumentException("kaiCode or userCode is empty.");
		}

		// 送信するパラメータを設定
		access.addSendValues("kaiCode", kaiCode);
		access.addSendValues("usrCode", usrCode);

		// 正否チェック
		boolean result = request();

		if (result) {
			// メニュー情報をリストにセット
			resList = new LinkedList();

			List list = access.getResultList();

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				List element = (List) iter.next();
				if (element.size() == 0) {
					continue;
				}
				MenuBean bean = new MenuBean();
				bean.setSYS_CODE(String.valueOf(element.get(0)));
				bean.setPRG_CODE(String.valueOf(element.get(1)));
				bean.setPRG_NAME(String.valueOf(element.get(2)));
				bean.setPRG_NAME_S(String.valueOf(element.get(3)));
				bean.setKEN(Integer.parseInt(String.valueOf(element.get(4))));
				bean.setCOM(String.valueOf(element.get(5)));
				bean.setLD_NAME(String.valueOf(element.get(6)));
				bean.setPARENT_PRG_CODE(String.valueOf(element.get(7)));
				bean.setMENU_KBN(Boolean.parseBoolean(String.valueOf(element.get(8))));
				bean.setDISP_INDEX(Integer.parseInt(String.valueOf(element.get(9))));
				resList.add(bean);
			}

		}

		return result;
	}

	/**
	 * リクエスト結果のListを取得
	 * 
	 * @return ListをBeanに変換したリスト
	 */
	public List getMenuList() {
		if (resList == null) {
			throw new IllegalStateException("List is Null.");
		}
		return resList;
	}

}
