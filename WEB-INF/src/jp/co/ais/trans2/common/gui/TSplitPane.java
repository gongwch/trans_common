package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.beans.*;
import java.io.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;

/**
 * 分割パネル
 */
public class TSplitPane extends JSplitPane implements TStorable {

	/** デフォルトキー */
	protected TStorableKey _key = null;

	/**
	 * @see JSplitPane#JSplitPane
	 */
	public TSplitPane() {
		super();

		init();
	}

	/**
	 * @see JSplitPane#JSplitPane(int)
	 */
	public TSplitPane(int newOrientation) {
		super(newOrientation);

		init();
	}

	/**
	 * @see JSplitPane#JSplitPane(int, boolean)
	 */
	public TSplitPane(int newOrientation, boolean newContinuousLayout) {
		super(newOrientation, newContinuousLayout);

		init();
	}

	/**
	 * @see JSplitPane#JSplitPane(int, Component, Component)
	 */
	public TSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent) {
		super(newOrientation, newLeftComponent, newRightComponent);

		init();
	}

	/**
	 * @see JSplitPane#JSplitPane(int, boolean, Component, Component)
	 */
	public TSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent,
		Component newRightComponent) {
		super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);

		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// ロケーション変更イベント対応
		addPropertyChangeListener(LAST_DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				saveLocation((Integer) evt.getNewValue());
			}
		});
	}

	/**
	 * コンポーネント情報を保存する
	 * 
	 * @param key
	 * @param obj
	 */
	public void saveComponent(TStorableKey key, Serializable obj) {
		//
	}

	/**
	 * コンポーネント情報を復元する
	 * 
	 * @param key
	 */
	public void restoreComponent(TStorableKey key) {
		try {

			if (key == null || Util.isNullOrEmpty(key.getKey())) {
				return;
			}

			this._key = key;

			// ロケーション復元
			restoreLocation();

			this.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存⇔復元キーを返す
	 * 
	 * @return 保存⇔復元キー
	 */
	public TStorableKey getKey() {
		return _key; // デフォルト利用
	}

	/**
	 * 指定の分割パネル情報を復元する
	 */
	protected void restoreLocation() {
		String loc = Util.avoidNull(FileUtil.getTemporaryObject(getKey().getKey()));
		int location = this.getDividerLocation();
		if (Util.isNumber(loc)) {
			location = DecimalUtil.toInt(loc);
		}

		// ロケーション復元
		this.setDividerLocation(location);
	}

	/**
	 * 指定の分割パネルロケーションを保存する
	 * 
	 * @param location
	 */
	protected void saveLocation(int location) {
		if (getKey() == null || Util.isNullOrEmpty(getKey().getKey())) {
			return;
		}

		FileUtil.saveTemporaryObject(location, getKey().getKey());
	}
}
