package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * タブ順フォーカス移動ポリシー <br>
 * ポリシー単位でのsynchronizedを追加した。
 */
public class TFocusPolicy extends FocusTraversalPolicy {

	/** 順 */
	protected static final int SEQ_NORMAL = 1;

	/** 逆順 */
	protected static final int SEQ_REVERSE = -1;

	/** コンポーネントリスト */
	protected List<Component> listTab = null;

	/** インデックス */
	protected int index = 0;

	/**
	 * constructor.
	 * 
	 * @param list コンポーネントのリスト
	 */
	public TFocusPolicy(List<Component> list) {
		super();
		this.listTab = list;
	}

	/**
	 * 先頭コンポーネント取得
	 * 
	 * @param focusCycleRoot
	 * @return 先頭コンポーネント
	 */
	@Override
	public Component getFirstComponent(Container focusCycleRoot) {

		Component comp = null;

		synchronized (this) {

			comp = this.getComponentByIndex(0, SEQ_NORMAL);
		}

		return comp;
	}

	/**
	 * 最終コンポーネント取得
	 * 
	 * @param focusCycleRoot
	 * @return 最終コンポーネント
	 */
	@Override
	public Component getLastComponent(Container focusCycleRoot) {

		Component comp = null;

		synchronized (this) {

			comp = this.getComponentByIndex(-1, SEQ_REVERSE);
		}

		return comp;
	}

	/**
	 * 次コンポーネント取得
	 * 
	 * @param focusCycleRoot ルートコンテナ
	 * @param aComponent 現在のコンポーネント
	 */
	@Override
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {

		Component comp = null;

		synchronized (this) {

			int pos = this.indexOf(aComponent);

			// comboboxなど、タブ順component listに登録されていない
			// objectがaComponentに設定されていることがある。
			if (pos < 0) {
				pos = this.index;
			}

			comp = this.getComponentByIndex(pos + 1, SEQ_NORMAL);
		}

		return comp;
	}

	/**
	 * 前コンポーネント取得
	 * 
	 * @param focusCycleRoot ルートコンテナ
	 * @param aComponent 現在のコンポーネント
	 */
	@Override
	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {

		Component comp = null;

		synchronized (this) {

			int pos = this.indexOf(aComponent);

			// comboboxなど、タブ順component listに登録されていない
			// objectがaComponentに設定されていることがある。
			if (pos < 0) {
				pos = this.index;
			}

			comp = this.getComponentByIndex(pos - 1, SEQ_REVERSE);
		}

		return comp;
	}

	/**
	 * デフォルト取得
	 * 
	 * @param focusCycleRoot ルートコンテナ
	 */
	@Override
	public Component getDefaultComponent(Container focusCycleRoot) {

		Component comp = null;

		synchronized (this) {

			comp = this.getComponentByIndex(0, SEQ_NORMAL);
		}

		return comp;
	}

	/**
	 * 次の有効なComponentを取得する。
	 * 
	 * @param pos 指定index
	 * @param seq 増分値
	 * @return 有効Component
	 */
	protected Component getComponentByIndex(int pos, int seq) {

		if (this.countActiveComponent() <= 0) {
			return null;
		}

		for (int listPos = this.trimIndex(pos); listPos < this.listTab.size(); listPos += seq) {

			// 0番目までActiveでないコンポーネントの場合、末端まで戻す
			if (listPos < 0) {
				listPos = this.listTab.size() - 1;
			}

			Component comp = this.listTab.get(listPos);

			if (this.isActiveComponent(comp)) {
				// 有効なコンポーネントあり

				this.index = listPos;

				// comboboxのとき
				if (comp instanceof JComboBox) {

					JComboBox combo = (JComboBox) comp;
					if (combo.isEditable()) {
						return combo.getEditor().getEditorComponent();
					}
				}

				return comp;
			}
		}

		// 有効なコンポーネントなし
		return null;
	}

	/**
	 * 添え字のサイクリック可。
	 * 
	 * @param pos 指定index
	 * @return 補正index
	 */
	protected int trimIndex(int pos) {

		if (this.isNullOrEmpty()) {
			return 0;
		}

		if (pos < 0) {
			pos += this.listTab.size();
		}

		return pos % this.listTab.size();
	}

	/**
	 * タブが移動できる(focusできる)componentの数を取得する。
	 * 
	 * @return 数
	 */
	protected int countActiveComponent() {

		if (this.listTab == null) {
			return 0;
		}

		int count = 0;

		for (Iterator i = this.listTab.iterator(); i.hasNext();) {
			Component comp = (Component) i.next();
			if (this.isActiveComponent(comp)) {
				count += 1;
			}
		}

		return count;
	}

	/**
	 * タブが移動できる(focusできる)componentかチェックする。
	 * 
	 * @param comp コンポーネント
	 * @return true:有効
	 */
	protected boolean isActiveComponent(Component comp) {

		if (comp == null) {
			return false;
		}

		// not enable.
		if ((!comp.isFocusable()) || (!comp.isShowing()) || (!comp.isEnabled()) || (!comp.isVisible())) {
			return false;
		}

		// textfield, textarea
		if (comp instanceof JTextComponent) {

			// not editable.
			if (!((JTextComponent) comp).isEditable()) {
				return false;
			}
		}

		// popupCalendar
		if (comp instanceof TPopupCalendar) {

			// **
			// not editable.
			// **
			if (!((TPopupCalendar) comp).isEditable()) {
				return false;
			}
		}

		// table
		if (comp instanceof BaseTable) {

			// row = 0
			if (((BaseTable) comp).getRowCount() == 0) {
				return false;
			}
		}

		// table
		if (comp instanceof JCTable) {

			// row = 0
			if (((JCTable) comp).getNumRows() == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * リストの状態
	 * 
	 * @return true:nullまたはsize=0
	 */
	protected boolean isNullOrEmpty() {

		if (this.listTab == null) {
			return true;
		}

		if (this.listTab.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * comboBoxのとき、EditorComponentを比較する。
	 * 
	 * @param comp
	 * @return index
	 */
	protected int indexOf(Component comp) {

		int pos = this.listTab.indexOf(comp);

		if (0 <= pos) {
			return pos;
		}

		int cnt = 0;
		for (Iterator i = this.listTab.iterator(); i.hasNext();) {
			Component test = (Component) i.next();
			if (test == null) continue;

			if (test instanceof JComboBox) {
				JComboBox comb = (JComboBox) test;
				if (comb.getEditor() != null && comb.getEditor().getEditorComponent() != null) {
					if (comp.equals(comb.getEditor().getEditorComponent())) {
						return cnt;
					}
				}
			}
			cnt += 1;
		}

		return -1;
	}
}