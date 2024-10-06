package jp.co.ais.trans2.common.client;

import jp.co.ais.trans2.common.ui.*;

/**
 * プログラム起動クラス。<br>
 * アプリケーションは当該クラスのmainメソッドを起点とする。
 * 
 * @author AIS
 */
public class TSingleStart extends TStart {

	/**
	 * プログラムエントリポイント<BR>
	 * アプリケーションを起動する。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// アプリ引数によってプロパティ初期化
		initArgumentProperties(args);

		// シングルログインコントローラ起動
		TSingleLoginCtrl ctrl = new TSingleLoginCtrl();

		// シングルログイン開始
		ctrl.start();

	}

}
