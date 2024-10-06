package jp.co.ais.trans.common.gui;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import com.klg.jclass.field.JCTextField;

/**
 * 入力チェッククラス <br>
 * verifyCommit()メソッドを実装する。 TTextField, TLabelField, TButtonFieldのsetInputVerifier()メソッド で登録する。
 */
public abstract class TInputVerifier extends InputVerifier {

	/** false:verifyを行わない */
	private boolean isCheck = true;

	/**
	 * constructor
	 */
	public TInputVerifier() {
		super();
		isCheck = true;
	}

	/**
	 * verify <br>
	 * verifyCommitをオーバーライドしてください。
	 * 
	 * @comp コンポーネント
	 */
	final public boolean verify(JComponent comp) {

		// JFormattedTextField, JCTextFieldを継承したコンポーネントなら
		// commitする。
		// (getText,vetValueで得られる値はcommit前のデータのため)

		this.commitEdit(comp);

		// editable=trueであればチェックを行う。
		if (this.checkEditable(comp)) {

			// 親FrameまたはDialogを取得
			// 複数アイテムでTInputVerifierインスタンスを共有することがあるため、
			// 毎回親Windowを調べる。
			Container parent = TGuiUtil.getParentWindow(comp);

			boolean b;

			if (parent != null) {

				// 親Frame,Dialogがあれば、そのインスタンスでlockをかける。
				// TDialogで設定するチェック無効化処理と競合しないようにする。

				synchronized (parent) {
					b = this.verifyWhenIsCheck(comp);
				}

			} else {
				b = this.verifyWhenIsCheck(comp);
			}

			return b;

		} else {
			// editableでなければ、verifyCommitを呼ばずに常にtrueを返す。
			return true;
		}
	}

	/**
	 * isCheck()=チェックを行うとき、verifyCommit()を行う。
	 * 
	 * @param comp
	 * @return true:OK false:不正値
	 */
	protected boolean verifyWhenIsCheck(JComponent comp) {

		// オーバーライド用のメソッドを呼び出す
		boolean b;

		// ClientLogger.debug("**verify");

		if (this.isCheck) {

			// *****************************************
			// オーバーライド不正入力チェックメソッド呼出
			// *****************************************
			b = this.verifyCommit(comp);

		} else {

			// ClientLogger.debug("verify masked");
			b = true;
		}

		// ClientLogger.debug("**verify:"+b);
		return b;
	}

	/**
	 * JFormattedTextField, JCTextFieldを継承したコンポーネントなら commitする。
	 * 
	 * @param comp
	 */
	protected void commitEdit(JComponent comp) {

		try {
			if (comp instanceof JFormattedTextField) {
				((JFormattedTextField) comp).commitEdit();

			} else if (comp instanceof JCTextField) {
				((JCTextField) comp).commitEdit();
			}

		} catch (ParseException e) {
			//
		}
	}

	/**
	 * JFormattedTextField, JCTextFieldを継承したコンポーネントなら editableか調べる。
	 * 
	 * @param comp 大賞コンポーネント
	 * @return true:Edit可能状態
	 */
	protected boolean checkEditable(JComponent comp) {

		if (comp instanceof JFormattedTextField) {
			return ((JFormattedTextField) comp).isEditable();

		} else if (comp instanceof JCTextField) {
			return ((JCTextField) comp).isEditable();
		}

		// それ以外はeditableなことにする。
		return true;
	}

	/**
	 * オーバーライド用verifyメソッド <br>
	 * InputVerifier#verify()の代わりにTInputVerifierでは verifyCommit()を定義してください。
	 * 
	 * @param comp
	 * @return true:OK
	 */
	abstract public boolean verifyCommit(JComponent comp);

	/**
	 * verify有効化 <br>
	 * 
	 * @param b =true有効(通常動作) =false無効(vrifyCommitを呼ばずにtrueを返す
	 */
	public void setCheck(boolean b) {
		this.isCheck = b;
	}

	/**
	 * verify有効化 <br>
	 * 
	 * @return b =true有効(通常動作) =false無効(vrifyCommitを呼ばずにtrueを返す
	 */
	public boolean isCheck() {
		return this.isCheck;
	}

	/**
	 * あるコンテナ配下のアイテムのverify有効/無効 一括設定
	 * 
	 * @param cont コンテナ(Panel, Dialogなど)
	 * @param active setActive()設定フラグ
	 */
	public static void setActiveChildren(Container cont, boolean active) {

		if (cont == null) {
			return;
		}

		// 子コンポーネント取得
		Component[] comp = cont.getComponents();
		if (comp == null) {
			return;
		}

		// すべての子コンポーネントについて
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JComponent) {
				// TInputVerifierをInputVerifierとして使っていれば
				// verifyの有効フラグを設定する
				InputVerifier iv = ((JComponent) comp[i]).getInputVerifier();
				if (iv != null && iv instanceof TInputVerifier) {
					((TInputVerifier) iv).setCheck(active);
				}
			}

			if (comp[i] instanceof Container) {
				// 再帰呼出
				setActiveChildren((Container) comp[i], active);
			}
		}
	}
}
