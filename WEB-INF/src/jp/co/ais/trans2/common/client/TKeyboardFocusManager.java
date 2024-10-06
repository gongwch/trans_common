package jp.co.ais.trans2.common.client;

import java.awt.*;

/**
 * Java7�̃t�H�[�J�X�N���A�̏����֎~<br>
 * Shift+TAB�̖����ȃo�O�Ή�<br>
 * <b>if (!res)</b> �� �����Ɂu<b>clearOnFailure</b>�̔���R��I�v<br>
 * Oracle�����Y�o�O�𗠂ŏ���ɒ��������̂ŁA�����ʑΉ��K�v�Ȃ��B<br>
 * Java9�ɂ�Oracle�͏����sun.awt��AppContext�ւ̃A�N�Z�X���֎~���ꂽ���߁A<br>
 * ���Y�N���X�̎g���ӏ����O���Ȃ��Ƃ����Ȃ�
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
// if (!res) { �� �����ɁuclearOnFailure�̔���R��I�v
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