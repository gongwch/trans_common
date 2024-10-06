package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.beans.*;
import java.io.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;

/**
 * �����p�l��
 */
public class TSplitPane extends JSplitPane implements TStorable {

	/** �f�t�H���g�L�[ */
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
	 * ������
	 */
	protected void init() {

		// ���P�[�V�����ύX�C�x���g�Ή�
		addPropertyChangeListener(LAST_DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				saveLocation((Integer) evt.getNewValue());
			}
		});
	}

	/**
	 * �R���|�[�l���g����ۑ�����
	 * 
	 * @param key
	 * @param obj
	 */
	public void saveComponent(TStorableKey key, Serializable obj) {
		//
	}

	/**
	 * �R���|�[�l���g���𕜌�����
	 * 
	 * @param key
	 */
	public void restoreComponent(TStorableKey key) {
		try {

			if (key == null || Util.isNullOrEmpty(key.getKey())) {
				return;
			}

			this._key = key;

			// ���P�[�V��������
			restoreLocation();

			this.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ۑ��̕����L�[��Ԃ�
	 * 
	 * @return �ۑ��̕����L�[
	 */
	public TStorableKey getKey() {
		return _key; // �f�t�H���g���p
	}

	/**
	 * �w��̕����p�l�����𕜌�����
	 */
	protected void restoreLocation() {
		String loc = Util.avoidNull(FileUtil.getTemporaryObject(getKey().getKey()));
		int location = this.getDividerLocation();
		if (Util.isNumber(loc)) {
			location = DecimalUtil.toInt(loc);
		}

		// ���P�[�V��������
		this.setDividerLocation(location);
	}

	/**
	 * �w��̕����p�l�����P�[�V������ۑ�����
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
