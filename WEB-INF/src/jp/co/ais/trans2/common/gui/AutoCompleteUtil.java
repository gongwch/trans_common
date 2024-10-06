package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.*;
import javax.swing.Timer;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * インクリメントサーチ機能
 * 
 * @author AIS
 */
public class AutoCompleteUtil {

	/** DefaultMaxVisibleRows */
	public static final int DefaultMaxVisibleRows = 5;

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param list
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(TTextField textComponent, List list) {
		return decorate(textComponent, textComponent.getWidth(), list);
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param cellWidth
	 * @param list
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(TTextField textComponent, int cellWidth, List list) {
		return decorate(textComponent, cellWidth, list, new DefaultCommitAdapter(textComponent));
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param list
	 * @param listener
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(TTextField textComponent, List list, CommitListener listener) {
		return decorate(textComponent, textComponent.getWidth(), list, listener);
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param list
	 * @param listener
	 * @param autoSelectFirstItem
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(TTextField textComponent, List list, CommitListener listener,
		boolean autoSelectFirstItem) {
		return decorate(textComponent, textComponent.getWidth(), list, listener, autoSelectFirstItem);
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param ref
	 * @param list
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(TReference ref, List list) {

		if (ref instanceof TCurrencyReference) {
			return decorate(ref, list, ref.getWidth());
		} else {
			int width = ref.code.getWidth();
			width += ref.name.getWidth() + 100;
			return decorate(ref, list, width);
		}
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param ref
	 * @param list
	 * @param viewWidth 一覧幅
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(TReference ref, List list, int viewWidth) {
		return decorate(ref, list, viewWidth, new RefableCommitAdapter(ref));
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param ref
	 * @param list
	 * @param adapter 個別Adapter
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TReference ref, List list, RefableCommitAdapter adapter) {

		if (ref instanceof TCurrencyReference) {
			return decorate(ref, list, ref.getWidth(), adapter);
		} else {
			int width = ref.code.getWidth();
			width += ref.name.getWidth() + 100;
			return decorate(ref, list, width, adapter);
		}
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param ref
	 * @param list
	 * @param adapter 個別Adapter
	 * @param autoSelectFirstItem
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TReference ref, List list, RefableCommitAdapter adapter,
		boolean autoSelectFirstItem) {

		if (ref instanceof TCurrencyReference) {
			return decorate(ref, list, ref.getWidth(), adapter, autoSelectFirstItem);
		} else {
			int width = ref.code.getWidth();
			width += ref.name.getWidth() + 100;
			return decorate(ref, list, width, adapter, autoSelectFirstItem);
		}
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param ref
	 * @param list
	 * @param viewWidth 一覧幅
	 * @param adapter 個別Adapter
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TReference ref, List list, int viewWidth,
			RefableCommitAdapter adapter) {

		// 名称フィールドのverifyイベント
		ref.name.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (ref.name.isEmpty()) {
					ref.clear();
				}
				return true;
			}
		});

		if (ref instanceof TCurrencyReference) {
			ref.code.setEditable(true);
			ref.setCheckExist(false);
			return decorate(ref.code, viewWidth, list, adapter);
		} else {
			int width = ref.code.getWidth();
			width += ref.name.getWidth();
			ref.name.setEditable(true);
			ref.setNameSize(width);
			ref.setCodeSize(0);
			ref.code.setVisible(false);
			ref.resize();
			return decorate(ref.name, viewWidth, list, adapter);
		}
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param ref
	 * @param list
	 * @param viewWidth 一覧幅
	 * @param adapter 個別Adapter
	 * @param autoSelectFirstItem
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TReference ref, List list, int viewWidth,
		RefableCommitAdapter adapter, boolean autoSelectFirstItem) {

		// 名称フィールドのverifyイベント
		ref.name.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (ref.name.isEmpty()) {
					ref.clear();
				}
				return true;
			}
		});

		if (ref instanceof TCurrencyReference) {
			ref.code.setEditable(true);
			ref.setCheckExist(false);
			return decorate(ref.code, viewWidth, list, adapter, autoSelectFirstItem);
		} else {
			int width = ref.code.getWidth();
			width += ref.name.getWidth();
			ref.name.setEditable(true);
			ref.setNameSize(width);
			ref.setCodeSize(0);
			ref.code.setVisible(false);
			ref.resize();
			return decorate(ref.name, viewWidth, list, adapter, autoSelectFirstItem);
		}
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param cellWidth
	 * @param list
	 * @param listener
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TTextField textComponent, int cellWidth, List list,
			CommitListener listener) {

		if (!(listener instanceof RefableCommitAdapter)) {
			// TReference以外の場合、フィールドのverifyイベント
			textComponent.setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (textComponent.isEmpty()) {
						textComponent.pushOldText();
					}
					return true;
				}
			});
		}

		// 幅自動調整
		if (list != null) {

			int maxLength = 0;

			for (Object obj : list) {
				if (obj == null) {
					continue;
				}

				String txt = null;

				if (obj instanceof AutoCompletable) {
					txt = ((AutoCompletable) obj).getDisplayText();
				} else {
					txt = obj.toString();
				}

				maxLength = Math.max(maxLength, txt.getBytes().length);
			}

			if (maxLength > 0) {
				String str = StringUtil.fill("", maxLength, '0');

				FontMetrics fm = textComponent.getFontMetrics(textComponent.getFont());
				int width = fm.stringWidth(str);

				cellWidth = Math.max(width, cellWidth);
			}
		}

		AutoCompleteUtil util = new AutoCompleteUtil(textComponent, cellWidth, list, listener);
		return util;
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param cellWidth
	 * @param list
	 * @param listener
	 * @param autoSelectFirstItem
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TTextField textComponent, int cellWidth, List list,
			CommitListener listener, boolean autoSelectFirstItem) {

		if (!(listener instanceof RefableCommitAdapter)) {
			// TReference以外の場合、フィールドのverifyイベント
			textComponent.setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (textComponent.isEmpty()) {
						textComponent.pushOldText();
					}
					return true;
				}
			});
		}

		// 幅自動調整
		if (list != null) {

			int maxLength = 0;

			for (Object obj : list) {
				if (obj == null) {
					continue;
				}

				String txt = null;

				if (obj instanceof AutoCompletable) {
					txt = ((AutoCompletable) obj).getDisplayText();
				} else {
					txt = obj.toString();
				}

				maxLength = Math.max(maxLength, txt.getBytes().length);
			}

			if (maxLength > 0) {
				String str = StringUtil.fill("", maxLength, '0');

				FontMetrics fm = textComponent.getFontMetrics(textComponent.getFont());
				int width = fm.stringWidth(str);

				cellWidth = Math.max(width, cellWidth);
			}
		}

		AutoCompleteUtil util = new AutoCompleteUtil(textComponent, cellWidth, list, listener, autoSelectFirstItem);
		return util;
	}

	/**
	 * 自動完成の機能有効に設定する
	 * 
	 * @param textComponent
	 * @param cellWidth
	 * @param list
	 * @param listener
	 * @param autoSelectFirstItem 
	 * @param alowBlank
	 * @return 自動完成Util
	 */
	public static AutoCompleteUtil decorate(final TTextField textComponent, int cellWidth, List list,
			CommitListener listener, boolean autoSelectFirstItem, int alowBlank) {

		if (!(listener instanceof RefableCommitAdapter)) {
			// TReference以外の場合、フィールドのverifyイベント
			textComponent.setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (textComponent.isEmpty()) {
						textComponent.pushOldText();
					}
					return true;
				}
			});
		}

		// 幅自動調整
		if (list != null) {

			int maxLength = 0;

			for (Object obj : list) {
				if (obj == null) {
					continue;
				}

				String txt = null;

				if (obj instanceof AutoCompletable) {
					txt = ((AutoCompletable) obj).getDisplayText();
				} else {
					txt = obj.toString();
				}

				maxLength = Math.max(maxLength, txt.getBytes().length);
			}

			if (maxLength > 0) {
				String str = StringUtil.fill("", maxLength, '0');

				FontMetrics fm = textComponent.getFontMetrics(textComponent.getFont());
				int width = fm.stringWidth(str);

				cellWidth = Math.max(width, cellWidth);
			}
		}

		AutoCompleteUtil util = new AutoCompleteUtil(textComponent, cellWidth, list, listener, autoSelectFirstItem,
				alowBlank);
		return util;
	}

	/** Binding of the text component */
	protected TTextField textComponent;

	/** JListセル幅 */
	protected int cellWidth;

	/** Results list to display the pop-up menu component */
	protected JPopupMenu popupMenu;

	/** Used to display a list of components matching results */
	protected JList resultList;

	/** To provide rolling support for the list of components */
	protected JScrollPane scrollPane;

	/** The data used for matching */
	protected ArrayList matchData;

	/** Tag matches the data is changed */
	protected boolean matchDataChanged;

	/** Record the current text that was matched */
	protected String matchedText;

	/** Edit the original text */
	protected String originalEditText;

	/** Help determine whether the matching components */
	protected DataMatchHelper dataMatchHelper;

	/** Determine the listener, the default for the press ' Enter 'or click on the mouse will be triggered */
	protected CommitListener commitListener;

	/** Thread pool */
	/** The task queue used to store */
	protected BlockingQueue<Runnable> queue;

	/** Thread pool object */
	protected ThreadPoolExecutor executor;

	/** Matching operation is asynchronous */
	protected boolean matchDataAsync = false;

	/** 存在しない名称は許可 */
	protected boolean allowNotExists = false;

	/** 最初の項目の自動選択にチェックを入れる */
	protected boolean autoSelectFirstItem = true;

	/** リストから選択した値 */
	protected AutoCompleteValue prevSelectValue = null;

	/** 比較用 */
	protected AutoCompleteValueComparator listComprator = new AutoCompleteValueComparator();

	/** alowBlank */
	protected boolean alowBlank = false;

	/**
	 * 比較用
	 */
	protected class AutoCompleteValueComparator implements Comparator<AutoCompleteValue> {

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(AutoCompleteValue o1, AutoCompleteValue o2) {
			return Util.avoidNull(o1.toString()).compareTo(Util.avoidNull(o2.toString()));
		}
	}

	/**
	 * Bound given by the text component to construct an object
	 * 
	 * @param textComponent
	 * @param cellWidth JListセル幅
	 * @param list 表示データリスト
	 * @param listener
	 */
	public AutoCompleteUtil(final TTextField textComponent, int cellWidth, List list, CommitListener listener) {
		/**
		 * To ensure that the plug is not bound null
		 */
		if (textComponent == null) {
			throw new IllegalArgumentException("  Parameter can not be  null!");
		}
		this.textComponent = textComponent;
		this.textComponent.setAutoComplete(this); // 連動
		this.cellWidth = cellWidth;
		resetAll();

		setMatchDataAsync(true);
		setMaxVisibleRows(8);

		if (list != null) {
			setMatchData(list);
			setCommitListener(listener);
		}
	}

	/**
	 * Bound given by the text component to construct an object
	 * 
	 * @param textComponent
	 * @param cellWidth JListセル幅
	 * @param list 表示データリスト
	 * @param listener
	 * @param alowBlank 
	 */
	public AutoCompleteUtil(final TTextField textComponent, int cellWidth, List list, CommitListener listener,
			int alowBlank) {
		/**
		 * To ensure that the plug is not bound null
		 */
		if (textComponent == null) {
			throw new IllegalArgumentException("  Parameter can not be  null!");
		}
		if (alowBlank == 1) {
			this.alowBlank = true;
		}

		this.textComponent = textComponent;
		this.textComponent.setAutoComplete(this); // 連動
		this.cellWidth = cellWidth;
		resetAll();

		setMatchDataAsync(true);
		setMaxVisibleRows(8);

		if (list != null) {
			setMatchData(list);
			setCommitListener(listener);
		}
	}

	/**
	 * Bound given by the text component to construct an object
	 * 
	 * @param textComponent
	 * @param cellWidth JListセル幅
	 * @param list 表示データリスト
	 * @param listener
	 * @param autoSelectFirstItem
	 * @param alowBlank 
	 */
	public AutoCompleteUtil(final TTextField textComponent, int cellWidth, List list, CommitListener listener,
			boolean autoSelectFirstItem, int alowBlank) {
		/**
		 * To ensure that the plug is not bound null
		 */
		if (textComponent == null) {
			throw new IllegalArgumentException("  Parameter can not be  null!");
		}

		if (alowBlank == 1) {
			this.alowBlank = true;
		}
		this.textComponent = textComponent;
		this.textComponent.setAutoComplete(this); // 連動
		this.cellWidth = cellWidth;
		this.autoSelectFirstItem = autoSelectFirstItem;
		this.setAllowNotExists(true);
		resetAll();

		setMatchDataAsync(true);
		setMaxVisibleRows(8);

		if (list != null) {
			setMatchData(list);
			setCommitListener(listener);
		}
	}

	/**
	 * Bound given by the text component to construct an object
	 * 
	 * @param textComponent
	 * @param cellWidth JListセル幅
	 * @param list 表示データリスト
	 * @param listener
	 * @param autoSelectFirstItem
	 */
	public AutoCompleteUtil(final TTextField textComponent, int cellWidth, List list, CommitListener listener,
			boolean autoSelectFirstItem) {
		/**
		 * To ensure that the plug is not bound null
		 */
		if (textComponent == null) {
			throw new IllegalArgumentException("  Parameter can not be  null!");
		}
		this.textComponent = textComponent;
		this.textComponent.setAutoComplete(this); // 連動
		this.cellWidth = cellWidth;
		this.autoSelectFirstItem = autoSelectFirstItem;
		this.setAllowNotExists(true);
		resetAll();

		setMatchDataAsync(true);
		setMaxVisibleRows(8);

		if (list != null) {
			setMatchData(list);
			setCommitListener(listener);
		}
	}

	/**
	 * Set as the default configuration, the original data will be cleared
	 */
	public synchronized void resetAll() {
		initTextComponent();
		initResultList();
		initValues();
		setFocusOnTextComponent();
		updateUI();
	}

	/**
	 * updateUI
	 */
	public synchronized void updateUI() {
		popupMenu.pack();
		popupMenu.updateUI();
	}

	/**
	 * Specifies the value to match the data set
	 * 
	 * @param list
	 */
	public synchronized void setMatchData(List list) {
		clearMatchData();
		if (list != null) {
			for (Object value : list) {
				this.matchData.add(value);
			}
		}
		notifyDataChanged();
	}

	/**
	 * Specifies the value to match the data set
	 * 
	 * @param data
	 */
	public synchronized void setMatchData(Object[] data) {
		clearMatchData();
		if (data != null) {
			for (Object value : data) {
				this.matchData.add(value);
			}
		}
		notifyDataChanged();
	}

	/**
	 * Setting specifies the value to match the data
	 * 
	 * @param data
	 */
	public synchronized void setMatchData(Vector data) {
		clearMatchData();
		if (data != null) {
			for (Object value : data) {
				this.matchData.add(value);
			}
		}
		notifyDataChanged();
	}

	/**
	 * Add the specified value to match the data
	 * 
	 * @param value
	 */
	public synchronized void addMatchData(Object value) {
		if (value != null) {
			this.matchData.add(value);
		}
		notifyDataChanged();
	}

	/**
	 * Remove the specified index match the data in the data ( If it exists )
	 * 
	 * @param index
	 */
	public synchronized void removeMatchData(int index) {
		if (index < 0 || index >= matchData.size()) {
			return;
		}
		matchData.remove(index);
	}

	/**
	 * Remove the specified data matches the data ( If it exists )
	 * 
	 * @param obj
	 */
	public synchronized void removeMatchData(Object obj) {
		if (obj != null) {
			matchData.remove(obj);
		}
	}

	/**
	 * Remove the specified index match the data in the data set ( If it exists )
	 * 
	 * @param indices
	 */
	public synchronized void removeMatchData(int[] indices) {
		if (indices == null) {
			return;
		}
		for (int index : indices) {
			removeMatchData(index);
		}
	}

	/**
	 * Match the data in the specified group removed the data ( If it exists )
	 * 
	 * @param data
	 */
	public synchronized void removeMatchData(Object[] data) {
		if (data == null) {
			return;
		}
		for (Object obj : data) {
			removeMatchData(obj);
		}
	}

	/**
	 * Clear match data
	 */
	public synchronized void clearMatchData() {
		matchData.clear();
	}

	/**
	 * Get the current match data
	 * 
	 * @return toArray
	 */
	public synchronized Object[] getMatchData() {
		return matchData.toArray();
	}

	/**
	 * Get the current match data
	 * 
	 * @return toArray
	 */
	public synchronized ArrayList getMatchDataList() {
		return matchData;
	}

	/**
	 * clearMatchResult
	 */
	public synchronized void clearMatchResult() {
		collapseResultList();
		if (queue != null) {
			queue.clear();
		}
		((DefaultListModel) resultList.getModel()).removeAllElements();
	}

	/**
	 * Change the tag matches the data
	 */
	protected void notifyDataChanged() {
		matchDataChanged = true;
	}

	/**
	 * @param commitListener
	 */
	public void setCommitListener(CommitListener commitListener) {
		this.commitListener = commitListener;
	}

	/**
	 * @param dataMatchHelper
	 */
	public void setDataMatchHelper(DataMatchHelper dataMatchHelper) {
		if (dataMatchHelper != null) {
			this.dataMatchHelper = dataMatchHelper;
		} else {
			this.dataMatchHelper = new DefaultDataMatchHelper();
		}
	}

	/**
	 * Get the current text that was matched
	 * 
	 * @return matchedText
	 */
	public synchronized String getMatchText() {
		return matchedText;
	}

	/**
	 * Get the current match result
	 * 
	 * @return toArray
	 */
	public synchronized Object[] getMatchResult() {
		return ((DefaultListModel) resultList.getModel()).toArray();
	}

	/**
	 * Get the current value of the selected
	 * 
	 * @return getSelectedValue
	 */
	public synchronized AutoCompleteValue getSelectedValue() {
		AutoCompleteValue value = (AutoCompleteValue) resultList.getSelectedValue();

		if (value == null && isAllowNotExists()) {
			if (!Util.isNullOrEmpty(textComponent.getText())) {
				// 存在しない名称もそのまま返す
				return new AutoCompleteValue(textComponent.getText());
			}
		}

		return value;
	}

	/**
	 * Determine the final selection of the specified text
	 * 
	 * @param val
	 */
	public synchronized void commitText(AutoCompleteValue val) {
		// TODO: 選択Object影響しないように
		// textComponent.setText(text);
		if (commitListener != null) {

			// クリア処理→nullでコミット
			Object v = val != null ? val.value : null;
			commitListener.commit(v);

			if (commitListener.isDoPush()) {
				originalEditText = commitListener.getText(v);
				textComponent.setText(originalEditText);
			}
		}
	}

	/**
	 * Get the index of the currently selected item
	 * 
	 * @return getSelectedIndex
	 */
	public synchronized int getSelectedIndex() {
		return resultList.getSelectedIndex();
	}

	/**
	 * Select the index value specified
	 * 
	 * @param index
	 */
	public synchronized void setSelectedIndex(int index) {
		if (index < 0 || index >= getResultListSize()) {
			return;
		}
		resultList.setSelectedIndex(index);
		// The selected item in the visible range
		resultList.ensureIndexIsVisible(index);
	}

	/**
	 * Open the list of results ( If minors match, the automated matching processing , If there is no effective results
	 * will not be launched )( The focus will shift to the list )
	 * 
	 * @return true: popupMenu.isVisible
	 */
	public synchronized boolean expandResultList() {
		if (alowBlank) {
			if (doMatch()) {
				// Expand List
				updateResultListUI();
				popupMenu.show(textComponent, 0, textComponent.getHeight());
			}
			return popupMenu.isVisible();
		} else {
			if (!hasMatched()) {
				if (doMatch()) {
					// Expand List
					updateResultListUI();
					popupMenu.show(textComponent, 0, textComponent.getHeight());
				}
			} else if (getResultListSize() > 0) {
				popupMenu.setVisible(true);
			}
			return popupMenu.isVisible();
		}
	}

	/**
	 * Off the list of results ( Data will not be clear, direct re-opened again displayed )
	 */
	public synchronized void collapseResultList() {
		removeSelectionInterval();
		popupMenu.setVisible(false);
	}

	/**
	 * Be opened to determine whether the results list
	 * 
	 * @return true: popupMenu.isVisible
	 */
	public synchronized boolean isResultListExpanded() {
		return popupMenu.isVisible();
	}

	/**
	 * Get the current number of entries in the list of results
	 * 
	 * @return size
	 */
	public synchronized int getResultListSize() {
		return ((DefaultListModel) resultList.getModel()).getSize();
	}

	/**
	 * For the display of a maximum number of rows ( The surplus to be displayed by dragging the scroll bar )
	 * 
	 * @param rows
	 */
	public synchronized void setMaxVisibleRows(int rows) {
		resultList.setVisibleRowCount(rows);
	}

	/**
	 * To set the focus to the text editor box
	 */
	public synchronized void setFocusOnTextComponent() {
		textComponent.requestFocus();
	}

	/**
	 * To set the focus to results list
	 */
	public synchronized void setFocusOnResultList() {
		resultList.requestFocus();
	}

	/**
	 * Determine whether the focus on the text edit box
	 * 
	 * @return true: isFocusOwner
	 */
	public synchronized boolean isFocusOnTextComponent() {
		return textComponent.isFocusOwner();
	}

	/**
	 * Determine whether the focus on the results list
	 * 
	 * @return true: isFocusOwner
	 */
	public synchronized boolean isFocusOnResultList() {
		return resultList.isFocusOwner();
	}

	/**
	 * Cancel the current list of selected ( So selectedIndex==-1)
	 */
	public synchronized void removeSelectionInterval() {
		final int selectedIndex = resultList.getSelectedIndex();
		resultList.removeSelectionInterval(selectedIndex, selectedIndex);
	}

	/**
	 * After a match to determine whether ( Testing should be conducted before match, match operation to avoid
	 * duplication )
	 * 
	 * @return true: hasMatched
	 */
	public synchronized boolean hasMatched() {
		if (matchDataChanged) {
			return false;
		}
		if (matchedText == null || matchedText.length() < 1) {
			return false;
		}
		String text = textComponent.getText();
		if (text == null || !text.equals(matchedText)) {
			return false;
		}
		return true;
	}

	/**
	 * Perform the matching operation
	 * 
	 * @return true: matchDataChanged
	 */
	public synchronized boolean doMatch() {
		// Clear the original results
		clearMatchResult();

		matchedText = textComponent.getText();
		originalEditText = matchedText;

		if (!textComponent.isTableCellEditor() && !textComponent.isValueChanged2() && this.autoSelectFirstItem) {
			return false;
		}

		String keyWord = matchedText;
		if (keyWord != null) {
			keyWord = matchedText.trim();
		}

		if (!alowBlank) {
			if (dataMatchHelper != null) {
				if (!dataMatchHelper.isMatchTextAccept(keyWord)) {
					return false;
				}
			}
		}

		if (matchDataAsync) {
			doMatchAsync(keyWord);
			matchDataChanged = false;
			return true;
		} else {
			doMatchSync(keyWord);
			matchDataChanged = false;
			return getResultListSize() > 0;
		}
	}

	/**
	 * Asynchronous data set matches
	 * 
	 * @param async
	 */
	public synchronized void setMatchDataAsync(boolean async) {
		if (this.matchDataAsync != async) {
			this.matchDataAsync = async;
			if (async) {
				queue = new LinkedBlockingQueue<Runnable>();
				// Create a maximum of two tasks running , Support the 10 tasks , Delay of 20 seconds to allow the
				// thread pool
				executor = new ThreadPoolExecutor(2, 10, 20, TimeUnit.SECONDS, queue);
			} else {
				if (queue != null) {
					queue.clear();
				}
				if (executor != null) {
					executor.shutdown();
				}
				queue = null;
				executor = null;
			}
		}
	}

	/**
	 * Determine whether the asynchronous match the current
	 * 
	 * @return true:isMatchDataAsync
	 */
	public synchronized boolean isMatchDataAsync() {
		return this.matchDataAsync;
	}

	/**
	 * In the results list select the item displayed on the prompt bar too
	 * 
	 * @param asNeed Whether the need to display (true-> When the text is longer than the display range display )
	 */
	public synchronized void showToolTipOnResultListBySelectedValue(boolean asNeed) {
		Object value = resultList.getSelectedValue();
		if (value != null) {
			// Show tips
			String txt = null;

			if (value instanceof AutoCompletable) {
				txt = ((AutoCompletable) value).getDisplayText();
			} else {
				txt = value.toString();
			}

			if (txt != null) {
				if (asNeed) {
					// Out of range before displaying the prompt
					int txtW = SwingUtilities.computeStringWidth(resultList.getFontMetrics(resultList.getFont()), txt);
					if (txtW >= resultList.getFixedCellWidth()) {
						resultList.setToolTipText(txt);
						return;
					}
				} else {
					resultList.setToolTipText(txt);
					return;
				}
			}
		}
		resultList.setToolTipText(null);
	}

	/**
	 * In the results list displays the specified text as a prompt
	 * 
	 * @param text
	 */
	public void showToolTipOnResultListBy(String text) {
		if (text != null) {
			resultList.setToolTipText(text);
		} else {
			resultList.setToolTipText(null);
		}
	}

	/**
	 * To obtain a maximum number of rows visible
	 * 
	 * @return rows
	 */
	public synchronized int getMaxVisibleRows() {
		return resultList.getVisibleRowCount();
	}

	/**
	 * Get the width of the result list item elements
	 * 
	 * @return width
	 */
	public synchronized int getResultListCellWidth() {
		return resultList.getFixedCellWidth();
	}

	/**
	 * Unit for the height of the result list
	 * 
	 * @return height
	 */
	public synchronized int getResultListCellHeight() {
		return resultList.getFixedCellHeight();
	}

	/**
	 * Whether the specified point within the text box
	 * 
	 * @param p
	 * @return true:contains
	 */
	public synchronized boolean isTextFieldContains(Point p) {
		if (p == null) {
			return false;
		}
		return textComponent.contains(p);
	}

	/**
	 * Whether the specified point range in the results list
	 * 
	 * @param p
	 * @return true:contains
	 */
	public synchronized boolean isResultListContains(Point p) {
		if (p == null) {
			return false;
		}
		return resultList.contains(p);
	}

	/**
	 * initTextComponent
	 */
	protected synchronized void initTextComponent() {
		textComponent.setVisible(true);
		textComponent.setEnabled(true);
		textComponent.setEditable(true);
		// Must remove and then add, otherwise repeat ....
		textComponent.removeKeyListener(defaultTextFieldKeyAdapter);
		textComponent.addKeyListener(defaultTextFieldKeyAdapter);
		textComponent.removeFocusListener(defaultFocusAdapter);
		textComponent.addFocusListener(defaultFocusAdapter);
	}

	/**
	 * initResultList
	 */
	protected synchronized void initResultList() {
		/**
		 * list
		 */
		if (resultList != null) {
			resultList.removeAll();
		} else {
			resultList = new JList(new DefaultListModel());
			resultList.addMouseListener(defaultResultListMouseAdapter);
			resultList.addMouseMotionListener(defaultResultListMouseMotionAdapter);
		}
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.setVisibleRowCount(DefaultMaxVisibleRows);
		// Allow the prompt box
		ToolTipManager.sharedInstance().registerComponent(resultList);

		/**
		 * scroll pane
		 */
		if (scrollPane == null) {
			scrollPane = new JScrollPane(resultList);
		}
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		/**
		 * popup menu
		 */
		if (popupMenu == null) {
			popupMenu = new JPopupMenu();
		}
		popupMenu.add(scrollPane);
		popupMenu.setVisible(false);
		popupMenu.setFocusable(false);
		popupMenu.setBorder(BorderFactory.createEmptyBorder()); // Remove border
	}

	/**
	 * initValues
	 */
	protected synchronized void initValues() {
		/** Match Data */
		if (matchData != null) {
			matchData.clear();
		} else {
			matchData = new ArrayList();
		}

		/** Other */
		setDataMatchHelper(null);
		setCommitListener(null);

		// System.gc();

		matchedText = null;
		matchDataChanged = true;
		this.matchDataAsync = false;
		originalEditText = textComponent.getText();
	}

	/**
	 * The implementation of the given value match operation ( The operation is asynchronous )
	 * 
	 * @param content
	 */
	protected synchronized void doMatchAsync(String content) {
		final String matchText = content;

		if (queue != null) {
			queue.clear();
		}

		executor.execute(new Runnable() {

			public void run() {
				/**
				 * Match
				 */
				doMatchInner(matchText);
				/**
				 * If no match, close the currently displayed
				 */
				if (getResultListSize() > 0) {
					updateResultListUI();
				} else {
					collapseResultList();
				}
			}
		});
	}

	/**
	 * The implementation of the given value match operation ( This operation is synchronized )
	 * 
	 * @param content
	 */
	protected synchronized void doMatchSync(String content) {
		/**
		 * Match
		 */
		doMatchInner(content);
	}

	/**
	 * Matching Treatment ( Internal call )
	 * 
	 * @param matchText
	 */
	protected void doMatchInner(String matchText) {
		if (matchData != null) {
			DefaultListModel listModel = (DefaultListModel) resultList.getModel();

			List<AutoCompleteValue> sub1List = new ArrayList<AutoCompleteValue>();
			List<AutoCompleteValue> sub2List = new ArrayList<AutoCompleteValue>();

			for (Object value : matchData) {
				if (dataMatchHelper != null) {
					if (dataMatchHelper.isStartWith(matchText, value)) {
						sub1List.add(new AutoCompleteValue(value));

					} else if (dataMatchHelper.isDataMatched(matchText, value)) {
						sub2List.add(new AutoCompleteValue(value));
					}
				} else {
					// Added directly
					listModel.addElement(new AutoCompleteValue(value));
				}
			}

			Collections.sort(sub1List, listComprator);
			Collections.sort(sub2List, listComprator);

			for (AutoCompleteValue v : sub1List) {
				listModel.addElement(v);
			}
			for (AutoCompleteValue v : sub2List) {
				listModel.addElement(v);
			}
		}
	}

	/**
	 * Set the current options for the final selected value
	 */
	protected void commitTextBySelectedValue() {
		AutoCompleteValue value = getSelectedValue();

		// クリア処理→nullでコミット
		if (!this.autoSelectFirstItem) {
			if (value != null) {
				commitText(value);
			}
		} else {
			commitText(value);
		}
		//		commitText(value);
		prevSelectValue = value;

		textComponent.pushOldText();

		collapseResultList();
	}

	/**
	 * To shift the focus to the text edit box and close the list of results
	 */
	protected void changeFocusToTextField() {
		// Deselect
		removeSelectionInterval();
		// To shift the focus to the text box
		setFocusOnTextComponent();

		// TODO: 選択Object影響しないように
		// Set the text value of the original edit
		// textComponent.setText(originalEditText);

		if (commitListener != null && commitListener.isDoPush()) {
			textComponent.setText(commitListener.getText(originalEditText));
		}
	}

	/**
	 * Set the current value of the selected item into the text box
	 */
	protected void showCurrentSelectedValue() {
		Object value = getSelectedValue();
		if (value != null) {
			// TODO: 選択Object影響しないように
			// textComponent.setText(value.toString());
		}

		if (commitListener != null && commitListener.isDoPush()) {
			textComponent.setText(commitListener.getText(value));
		}
	}

	/**
	 * JListセル幅の取得
	 * 
	 * @return cellWidth JListセル幅
	 */
	public int getCellWidth() {
		return cellWidth;
	}

	/**
	 * JListセル幅の設定
	 * 
	 * @param cellWidth JListセル幅
	 */
	public void setCellWidth(int cellWidth) {
		if (cellWidth != this.cellWidth) {
			this.cellWidth = cellWidth;

			updateResultListUI();
		}
	}

	/**
	 * Refresh the display of the results list ( The focus will shift to the list )
	 */
	protected synchronized void updateResultListUI() {

		DefaultListModel listModel = (DefaultListModel) resultList.getModel();
		int dataSize = listModel.getSize();

		/**
		 * Set the input box to match the display size
		 */
		resultList.setFixedCellWidth(cellWidth);
		resultList.setFixedCellHeight(textComponent.getHeight());

		int preferredWidth = cellWidth;
		if (dataSize > resultList.getVisibleRowCount()) {
			preferredWidth += scrollPane.getVerticalScrollBar().getPreferredSize().width;
		}

		// Reserve some space for more, this value can make their own adjustments not very accurate
		int preferredHeight = Math.min(resultList.getVisibleRowCount(), dataSize) * resultList.getFixedCellHeight() + 3;

		scrollPane.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		resultList.updateUI();
		popupMenu.pack();
	}

	/**
	 * The default list of results provided by the mouse movement event handler
	 */
	protected MouseMotionAdapter defaultResultListMouseMotionAdapter = new MouseMotionAdapter() {

		@Override
		public void mouseMoved(MouseEvent e) {
			onMouseMoved(e);
		}

	};

	/**
	 * MouseMoved
	 * 
	 * @param e
	 */
	protected void onMouseMoved(MouseEvent e) {
		/**
		 * The operating result is : Select the mouse option, and display the prompt
		 */
		Point p = e.getPoint();
		if (isResultListContains(p)) {
			/**
			 * The mouse moves within the region in the list
			 */
			int index = p.y / getResultListCellHeight();
			// Follow the cursor
			setSelectedIndex(index);
			// Show tips long text
			showToolTipOnResultListBySelectedValue(true);
			// Back to text edit box focus
			setFocusOnTextComponent();
		}
	}

	/**
	 * The results provided a list of the default button on a mouse event handler
	 */
	protected final MouseAdapter defaultResultListMouseAdapter = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			onMouseClicked(e);
		}

	};

	/**
	 * MouseClicked
	 * 
	 * @param e
	 */
	protected void onMouseClicked(MouseEvent e) {
		/**
		 * The operating result is : Set the edit box text for the selected item, close the list of results , The focus
		 * back to edit box, while the trigger commit Monitor
		 */
		Point p = e.getPoint();
		if (isResultListContains(p)) {
			/**
			 * Mouse click the list item
			 */
			int index = p.y / getResultListCellHeight();
			// The selected
			setSelectedIndex(index);
			//
			if (getSelectedIndex() == index) {
				commitTextBySelectedValue();
			}
			// Back to text edit box focus
			setFocusOnTextComponent();
		}
	}

	/**
	 * The default text editor provided by the event handler keyboard box
	 */
	protected final KeyAdapter defaultTextFieldKeyAdapter = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			onTextFieldKeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			onTextFieldKeyReleased(e);
		}

	};

	/**
	 * KeyPressed
	 * 
	 * @param e
	 */
	protected void onTextFieldKeyPressed(KeyEvent e) {
		/**
		 * Only be dealt with in the current focus
		 */
		if (!e.getComponent().isFocusOwner()) {
			collapseResultList();
			return;
		}

		switch (e.getKeyCode()) {

		case KeyEvent.VK_ENTER:
			/**
			 * The operating result is : Set the edit box text for the selected item, close the list of results ,
			 * The focus back to edit box, while the trigger commit Monitor
			 */
			if (getSelectedIndex() == -1 && getResultListSize() > 0) {
				// DKIV: 2023/12/07
				// setSelectedIndex(0);

				if (this.autoSelectFirstItem) {
					setSelectedIndex(0);
				} else {
					return;
				}
			}

			if (!textComponent.isValueChanged2()) {
				return;
			}

			commitTextBySelectedValue();
			break;

		case KeyEvent.VK_DOWN:
			/**
			 * The operating result is : 1. If the results list is not open, open the list of results , And select
			 * the first item, set the edit box text 2. If the currently selected item is the last one, so the focus
			 * back to edit box 3. Otherwise, down options , And change the text for the current edit box Option
			 */
			if (isResultListExpanded()) {
				/**
				 * If the list is expanded
				 */
				final int selectedIndex = getSelectedIndex();
				if (selectedIndex == getResultListSize() - 1) {
					/**
					 * And select the entry for the last
					 */
					// The focus to a text box
					changeFocusToTextField();
				} else {
					/**
					 * Otherwise,
					 */
					// Down a
					setSelectedIndex(selectedIndex + 1);
					showCurrentSelectedValue();
					setFocusOnTextComponent();
				}
			} else {
				if (expandResultList()) {
					/**
					 * Successfully opened the list of results
					 */
					// Select the first item
					setSelectedIndex(0);
				}
			}
			break;

		case KeyEvent.VK_UP:
			/**
			 * The operating result is : 1. If the results list is not open, open the list of results , And select
			 * the last item, set the edit box text 2. If the currently selected item as the first item, so the
			 * focus back to edit box 3. Otherwise, the Move Options , And change the text for the current edit box
			 * Option
			 */
			if (isResultListExpanded()) {
				/**
				 * If the list is expanded
				 */
				final int selectedIndex = getSelectedIndex();
				if (selectedIndex == 0) {
					/**
					 * And select the entry for the first
					 */
					// The focus to a text box
					changeFocusToTextField();
				} else {
					/**
					 * Otherwise,
					 */
					if (selectedIndex == -1) {
						// Moved to the last
						setSelectedIndex(getResultListSize() - 1);
					} else {
						// Move a
						setSelectedIndex(selectedIndex - 1);
					}
					showCurrentSelectedValue();
				}
			} else {
				if (expandResultList()) {
					/**
					 * Successfully opened the list of results
					 */
					// Select the last item
					setSelectedIndex(getResultListSize() - 1);
				}
			}
			break;

		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			// case KeyEvent.VK_BACK_SPACE: // Operation around the same
			/**
			 * The operating result is : Set the edit text item is selected, and close the list of results , The
			 * focus back to edit box
			 */
			if (isResultListExpanded()) {
				/**
				 * If the list is expanded
				 */
				if (getSelectedIndex() != -1) {
					/**
					 * Option is selected and there
					 */
					showCurrentSelectedValue();
				}
				collapseResultList();
			}
			// To shift the focus to the text edit box
			changeFocusToTextField();
			break;
		}

		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			e.consume();
			return;
		}

		if (textComponent.isTableCellEditor()) {
			// IMEに対する不具合があるため、ENTERキー以外は禁止不要
			if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT
					|| keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_ENTER
			/* || keyCode == KeyEvent.VK_BACK_SPACE */) {

				e.consume();
				return;
			}
		}

		/**
		 * In order to ensure the focus is always in edit box
		 */
		if (TGuiUtil.checkFunctionKeyEvent(e) == 0) {
			setFocusOnTextComponent();
		}
	}

	/**
	 * KeyReleased
	 * 
	 * @param e
	 */
	protected void onTextFieldKeyReleased(KeyEvent e) {
		if (!e.getComponent().isFocusOwner()) {
			collapseResultList();
			return;
		}

		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER /* || keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12 */) {

			e.consume();
			return;
		}

		if (textComponent.isTableCellEditor()) {
			// IMEに対する不具合があるため、ENTERキー以外は禁止不要
			if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT
					|| keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB
			/* || keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12 || keyCode == KeyEvent.VK_BACK_SPACE */) {

				e.consume();
				return;
			}
		}

		/**
		 * Open the list of results
		 */
		expandResultList();
		/**
		 * In order to ensure the focus is always in edit box
		 */
		if (TGuiUtil.checkFunctionKeyEvent(e) == 0) {
			setFocusOnTextComponent();
		}
	}

	/**
	 * フォーカス処理
	 */
	protected final FocusAdapter defaultFocusAdapter = new FocusAdapter() {

		@Override
		public void focusLost(FocusEvent e) {
			onFocusLost();
		}

	};

	/**
	 * フォーカスなくす
	 */
	protected void onFocusLost() {
		// フォーカスなくす

		if (isAllowNotExists()) {

			// 閉じる処理のみ
			collapseResultList();
		} else {

			// 強制ペア処理を行う

			try {

				if (!textComponent.isEditable()) {
					return;
				}

				// リスト未表示の場合は入力有効判定を行う
				if (textComponent.isEmpty()) {
					if (!isResultListExpanded() && commitListener != null) {
						commitListener.commit(null);
					}
				} else if (textComponent.isValueChanged2()) {
					if (getSelectedIndex() == -1 && getResultListSize() > 0) {
						if (this.autoSelectFirstItem) {
							setSelectedIndex(0);
						} else {
							return;
						}
					}

					if (!textComponent.isValueChanged2()) {
						return;
					}

					commitTextBySelectedValue();

					// 100ms後に再度テキストボックスに選択された内容を設定する。
					Timer timer = new Timer(100, new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							commitText(prevSelectValue);
						}
					});
					timer.setRepeats(false);
					timer.start();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {

				// 閉じる
				collapseResultList();
			}
		}
	}

	/**
	 * DataMatchHelper
	 */
	public interface DataMatchHelper {

		/**
		 * Determines whether the specified text is allowed to match
		 * 
		 * @param text
		 * @return true:isMatchTextAccept
		 */
		public boolean isMatchTextAccept(String text);

		/**
		 * To determine whether a given text value matches the value
		 * 
		 * @param matchedText
		 * @param data
		 * @return true:isDataMatched
		 */
		public boolean isDataMatched(String matchedText, Object data);

		/**
		 * @param matchedText
		 * @param data
		 * @return true:先頭一致
		 */
		public boolean isStartWith(String matchedText, Object data);
	}

	/*********************************************************
	 * Default implementation
	 */
	/**
	 * The default data matching assistant
	 * 
	 * @author Univasity
	 */
	public class DefaultDataMatchHelper implements DataMatchHelper {

		public boolean isMatchTextAccept(String text) {
			return (text != null && text.length() > 0);
		}

		public boolean isDataMatched(String matchText, Object value) {
			if (value == null) {
				return false;
			}

			String str = null;
			if (value instanceof AutoCompletable) {
				str = ((AutoCompletable) value).getDisplayText().toUpperCase();
			} else {
				str = value.toString().toUpperCase();
			}

			return str.indexOf(matchText.toUpperCase()) != -1;
		}

		/**
		 * @param matchText
		 * @param value
		 * @return true:先頭一致
		 */
		public boolean isStartWith(String matchText, Object value) {
			if (value == null) {
				return false;
			}

			String str = null;
			if (value instanceof AutoCompletable) {
				str = ((AutoCompletable) value).getDisplayText().toUpperCase();
			} else {
				str = value.toString().toUpperCase();
			}

			return str.startsWith(matchText.toUpperCase());
		}
	}

	/**
	 * 存在しない名称は許可の取得
	 * 
	 * @return allowNotExists 存在しない名称は許可
	 */
	public boolean isAllowNotExists() {
		return allowNotExists;
	}

	/**
	 * 存在しない名称は許可の設定
	 * 
	 * @param allowNotExists 存在しない名称は許可
	 */
	public void setAllowNotExists(boolean allowNotExists) {
		this.allowNotExists = allowNotExists;
	}

}