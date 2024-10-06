package jp.co.ais.trans2.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;

import javax.swing.tree.*;

/**
 * Object受け渡し用リスナー
 */
public interface TDnDListener {

	/**
	 * ドラッグ時のオブジェクト提供
	 * 
	 * @return 渡すオブジェクト
	 */
	public Object getDragData();

	/**
	 * ドラッグ時のオブジェクト提供
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @return 渡すオブジェクト
	 */
	public Object getDragData(DragGestureEvent evt);

	/**
	 * オブジェクトドロップ時処理
	 * 
	 * @param component 発生元コンポーネント
	 * @param point ドロップポイント
	 * @param obj ドロップオブジェクト
	 */
	public void dropData(Component component, Point point, Object obj);

	/**
	 * オブジェクトドロップ時処理
	 * 
	 * @param evt ドロップターゲットイベント
	 * @param obj ドロップオブジェクト
	 */
	public void dropData(DropTargetDropEvent evt, Object obj);

	/**
	 * ドロップ先ノードのタイプ確認
	 * 
	 * @param node ノード
	 * @return true:子項目なし、false:子項目あり
	 */
	public boolean isLeaf(TreeNode node);
}
