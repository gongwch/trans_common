package jp.co.ais.trans2.common.client;

import java.awt.*;

/**
 * Java7のフォーカスクリアの処理禁止<br>
 * Shift+TABの無効なバグ対応<br>
 * <b>if (!res)</b> ← ここに「<b>clearOnFailure</b>の判定漏れ！」<br>
 * Oracleが当該バグを裏で勝手に訂正したので、もう個別対応必要なし。<br>
 * Java9にてOracleは勝手にsun.awtのAppContextへのアクセスを禁止されたため、<br>
 * 当該クラスの使い箇所を外さないといけない
 * 
 * @see java.awt.Component#transferFocusBackward()
 */
@Deprecated
public class TKeyboardFocusManager extends DefaultKeyboardFocusManager {

	/**
	 * @see java.awt.KeyboardFocusManager#clearGlobalFocusOwner()
	 */
	@Override
	public void clearGlobalFocusOwner() {
		return;
	}
}

// /**
// * Transfers the focus to the previous component, as though this Component
// * were the focus owner.
// * @see #requestFocus()
// * @since 1.4
// */
// public void transferFocusBackward() {
// transferFocusBackward(false);
// }
//
// boolean transferFocusBackward(boolean clearOnFailure) {
// Container rootAncestor = getTraversalRoot();
// Component comp = this;
// while (rootAncestor != null &&
// !(rootAncestor.isShowing() && rootAncestor.canBeFocusOwner()))
// {
// comp = rootAncestor;
// rootAncestor = comp.getFocusCycleRootAncestor();
// }
// boolean res = false;
// if (rootAncestor != null) {
// FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
// Component toFocus = policy.getComponentBefore(rootAncestor, comp);
// if (toFocus == null) {
// toFocus = policy.getDefaultComponent(rootAncestor);
// }
// if (toFocus != null) {
// res = toFocus.requestFocusInWindow(CausedFocusEvent.Cause.TRAVERSAL_BACKWARD);
// }
// }
// if (!res) { ← ここに「clearOnFailureの判定漏れ！」
// if (focusLog.isLoggable(PlatformLogger.FINER)) {
// focusLog.finer("clear global focus owner");
// }
// KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
// }
// if (focusLog.isLoggable(PlatformLogger.FINER)) {
// focusLog.finer("returning result: " + res);
// }
// return res;
// }