package applet;

import jp.co.ais.trans2.common.ui.TLoginCtrl;

/**
 * ログイン テストクラス
 * 
 * @author yanwei
 */
public class TLoginTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// ログインコントローラ起動
		TLoginCtrl ctrl = new TLoginCtrl();

		// ログイン開始
		ctrl.start();

	}
}
