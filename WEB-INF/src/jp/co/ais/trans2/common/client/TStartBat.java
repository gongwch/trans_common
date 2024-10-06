package jp.co.ais.trans2.common.client;

import jp.co.ais.trans2.common.ui.*;

/**
 * バッチプログラム起動クラス。<br>
 * アプリケーションは当該クラスのmainメソッドを起点とする。
 * 
 * @author AIS
 */
public class TStartBat {

	/**
	 * プログラムエントリポイント<BR>
	 * アプリケーションを起動する。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// ログインコントローラ起動
		TLoginCtrl ctrl = new TLoginCtrl();

		// ログイン開始
		ctrl.start();
	}
}
