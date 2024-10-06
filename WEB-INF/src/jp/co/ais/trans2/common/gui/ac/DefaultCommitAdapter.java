package jp.co.ais.trans2.common.gui.ac;

import javax.swing.text.*;

import jp.co.ais.trans2.model.*;

/**
 * デフォルト処理
 */
public class DefaultCommitAdapter extends CommitAdapter {

	/** 対象コンポ */
	protected JTextComponent textComponent;

	/**
	 * コンストラクター
	 * 
	 * @param textComponent
	 */
	public DefaultCommitAdapter(JTextComponent textComponent) {
		this.textComponent = textComponent;
	}

	@Override
	public void commit(Object value) {
		if (value != null) {
			if (value instanceof AutoCompletable) {
				textComponent.setText(((AutoCompletable) value).getName());
			} else if (value instanceof TReferable) {
				textComponent.setText(((TReferable) value).getNames());
			} else {
				textComponent.setText(value.toString());
			}
		} else {
			textComponent.setText("");
		}
	}
}
