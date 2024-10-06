package jp.co.ais.trans2.model.department;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNodeクラスを継承して作成。<br>
 * Drag＆Drop時、DepartmentTreeNodeを転送するために使用する。
 * 
 * @author Yanwei
 */
public class DepartmentTreeNode extends DefaultMutableTreeNode {

	/** ノード情報 */
	protected DepartmentDisp depDisp;

	/**
	 * コンストラクタ
	 * 
	 * @param depDisp ノード情報
	 */
	public DepartmentTreeNode(DepartmentDisp depDisp) {

		// ①継承元のコンストラクタを呼ぶ。
		super(getName(depDisp));

		// ②引数のDnDDataをプロパティにセットする。
		this.depDisp = depDisp;
	}

	/**
	 * 部門階層名の取得
	 * 
	 * @param depDisp
	 * @return 部門階層名
	 */
	protected static String getName(DepartmentDisp depDisp) {

		return depDisp.getViewName();

	}

	/**
	 * ノード情報
	 * 
	 * @return ノード情報
	 */
	public DepartmentDisp getDepDisp() {
		return depDisp;
	}

	/**
	 * ノード情報
	 * 
	 * @param depDisp ノード情報設定する
	 */
	public void setDepDisp(DepartmentDisp depDisp) {
		this.depDisp = depDisp;
	}

}