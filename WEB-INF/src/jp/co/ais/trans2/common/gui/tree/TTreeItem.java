package jp.co.ais.trans2.common.gui.tree;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * Treeのノードにセットするアイテム
 * 
 * @author AIS
 */
public class TTreeItem extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -1628401786423721107L;

	/** ノードに格納するアイテム */
	protected Object obj = null;

	/** 画面上に表示する文字 */
	protected String caption = null;

	/**
	 * コンストラクター
	 * 
	 * @param caption
	 * @param obj
	 */
	public TTreeItem(String caption, Object obj) {
		this.caption = caption;
		this.obj = obj;
	}

	/**
	 * 表示名
	 */
	@Override
	public String toString() {
		return caption;
	}

	/**
	 * @return captionを戻します。
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption captionを設定します。
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return objを戻します。
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj objを設定します。
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
