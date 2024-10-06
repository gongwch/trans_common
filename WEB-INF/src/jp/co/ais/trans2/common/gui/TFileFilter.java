package jp.co.ais.trans2.common.gui;

import java.io.*;

import javax.swing.filechooser.FileFilter;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;

/**
 * FileChooser�p�t�B���^�[
 */
public class TFileFilter extends FileFilter {

	/** �^�C�g�� */
	protected String title;

	/** �g���q���X�g */
	protected String[] extentions;

	/** ���� */
	protected String description;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param extensionTypes �g���q���X�g
	 * @param title �����̒P��ID
	 */
	public TFileFilter(String title, ExtensionType... extensionTypes) {
		this.title = title;
		extentions = new String[extensionTypes.length];

		for (int i = 0; i < extensionTypes.length; i++) {
			extentions[i] = extensionTypes[i].value;
		}

		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param extentions �g���q���X�g
	 * @param title �����̒P��ID
	 */
	public TFileFilter(String title, String... extentions) {
		this.title = title;
		this.extentions = extentions;

		init();
	}

	/**
	 * 
	 *
	 */
	protected void init() {
		StringBuilder asterExt = new StringBuilder();
		for (String extention : extentions) {
			if (asterExt.length() != 0) {
				asterExt.append(", ");
			}
			asterExt.append("*.").append(extention);
		}

		this.description = title + " (" + asterExt.toString() + ")";
	}

	/**
	 * �g���q����
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String ext = ResourceUtil.getExtension(f);

		boolean result = false;
		for (String extention : extentions) {
			result |= ext.equals(extention);
		}

		return result;
	}

	/**
	 * ������
	 */
	@Override
	public String getDescription() {
		return description;
	}
}