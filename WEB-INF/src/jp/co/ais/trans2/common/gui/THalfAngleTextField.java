package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.im.*;
import java.lang.Character.Subset;

import jp.co.ais.trans.common.gui.*;

/**
 * 半角ｶﾅ制御テキストフィールド
 */
public class THalfAngleTextField extends TTextField {

	/** 日本語サブセット */
	protected static final Subset[] SUBSET_JP_HALF_KANA = new Subset[] { InputSubset.HALFWIDTH_KATAKANA };

	/**
	 * コンストラクタ.
	 */
	public THalfAngleTextField() {
		super();

		// 正規表現での入力制限
		super.setRegex("[ -~ｦ-ﾟ]");
	}

	/**
	 * FocusGainedイベントを感知し、OldTextを挿入.
	 * 
	 * @param e イベント
	 */
	@Override
	protected void focusGainedActionPerformed(FocusEvent e) {

		// 他のWindowからフォーカスが来たときにnullになる。
		Component comp = e.getOppositeComponent();

		if (comp != null) {
			// コンポーネントの親のFrame,Dialogが同じであれあばoldTextを上書きする。
			if (comp instanceof Container) {

				Container a = TGuiUtil.getParentFrameOrDialog((Container) comp);
				Container b = TGuiUtil.getParentFrameOrDialog(this);

				if (a.equals(b)) {

					this.pushOldText();
				}
			}
		}

		this.selectAll();

		// IME入力モード制御
		if (isChangeCharacterSubsets && this.getInputContext() != null) {
			if (!isNoImeControl) {
				// IME制御を行う場合のみ
				Subset[] subsets = isImeMode() ? SUBSET_JP_HALF_KANA : null;
				this.getInputContext().setCharacterSubsets(subsets);
			}
		}
	}
}
