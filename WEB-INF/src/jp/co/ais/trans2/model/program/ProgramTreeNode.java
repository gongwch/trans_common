package jp.co.ais.trans2.model.program;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNodeクラスを継承して作成。<br>
 * Drag＆Drop時、ProgramTreeNodeを転送するために使用する。
 * 
 * @author Yanwei
 */
public class ProgramTreeNode extends DefaultMutableTreeNode {

	/** ノード情報 */
	private MenuDisp menuDisp;

	/**
	 * コンストラクタ
	 * 
	 * @param menuDisp ノード情報
	 */
	public ProgramTreeNode(MenuDisp menuDisp) {

		// ①継承元のコンストラクタを呼ぶ。
		super(getName(menuDisp));

		// ②引数のDnDDataをプロパティにセットする。
		this.menuDisp = menuDisp;
	}

	/**
	 * メニュー表示名の取得
	 * 
	 * @param menuDisp
	 * @return メニュー表示名
	 */
	protected static String getName(MenuDisp menuDisp) {

		// TODO:プログラムコード表示機能保留
		// if (menuDisp.isMenu() || menuDisp.getProgram() == null) {
		return menuDisp.getViewName();
		// } else {
		// return menuDisp.getCode() + " " + menuDisp.getProgram().getNames();
		// }
	}

	/**
	 * ノード情報
	 * 
	 * @return ノード情報
	 */
	public MenuDisp getMenuDisp() {
		return menuDisp;
	}

	/**
	 * ノード情報
	 * 
	 * @param menuDisp ノード情報設定する
	 */
	public void setMenuDisp(MenuDisp menuDisp) {
		this.menuDisp = menuDisp;
	}

}