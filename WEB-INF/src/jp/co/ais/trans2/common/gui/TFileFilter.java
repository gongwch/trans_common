package jp.co.ais.trans2.common.gui;

import java.io.*;

import javax.swing.filechooser.FileFilter;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;

/**
 * FileChooser用フィルター
 */
public class TFileFilter extends FileFilter {

	/** タイトル */
	protected String title;

	/** 拡張子リスト */
	protected String[] extentions;

	/** 説明 */
	protected String description;

	/**
	 * コンストラクタ
	 * 
	 * @param extensionTypes 拡張子リスト
	 * @param title 説明の単語ID
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
	 * コンストラクタ
	 * 
	 * @param extentions 拡張子リスト
	 * @param title 説明の単語ID
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
	 * 拡張子判定
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
	 * 説明文
	 */
	@Override
	public String getDescription() {
		return description;
	}
}