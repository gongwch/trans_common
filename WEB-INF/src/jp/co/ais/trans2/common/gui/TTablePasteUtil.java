package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * フィールド貼り付け処理Util
 */
public class TTablePasteUtil {

	/** 数値 */
	public static final int NUMBER = 0;

	/** 日付 */
	public static final int DATE = 1;

	/** 文字列 */
	public static final int STRING = 2;

	/** ペスト対象外 */
	public static final int READONLY = 9;

	/**
	 * 貼り付け機能
	 * 
	 * @param editor テキストフィールド
	 * @param tbl テーブル
	 * @param rowIndex 行番号
	 * @param columnIndex 列番号
	 * @param types タイプ配列(貼り付け列→右全列)
	 */
	public static void handleKeyEvent(final TTextField editor, final TTable tbl, final int rowIndex,
		final int columnIndex, final int[] types) {

		// カスタマイズキーリスナー消す
		for (KeyListener l : editor.getKeyListeners()) {
			if (l instanceof TTableKeyAdapter) {
				editor.removeKeyListener(l);
			}
		}

		final TTableKeyAdapter keyAdapter = new TTableKeyAdapter(editor, tbl, rowIndex, columnIndex, types);
		editor.addKeyListener(keyAdapter);
	}

	/**
	 * クリップボードのテキストの取得
	 * 
	 * @return クリップボードのテキスト
	 */
	public static String getClipboardText() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();
		try {
			return Util.avoidNullNT(clip.getData(DataFlavor.stringFlavor));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 区切りによって文字列配列の取得
	 * 
	 * @param value 文字列
	 * @param split 区切り
	 * @return 配列
	 */
	public static String[] getArray(String value, String split) {
		if (Util.avoidNull(value).contains(split)) {
			return value.split(split);
		} else {
			return new String[] { value };
		}
	}

	/**
	 * 文字列から数値の取得
	 * 
	 * @param input 文字列
	 * @return 数値
	 */
	public static BigDecimal getNumber(String input) {
		String strNum = input.replaceAll(",", "").replaceAll("%", "").replaceAll("\\$", "").replaceAll("\\\\", "")
			.trim();
		if (Util.isNullOrEmpty(strNum) || !Util.isNumber(strNum)) {
			return null;
		}
		return DecimalUtil.toBigDecimal(strNum);
	}

	/**
	 * 文字列から日付の取得
	 * 
	 * @param strDate 文字列
	 * @return 日付
	 */
	public static Date getDate(String strDate) {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}

		try {
			// 日付書式対応
			// yyyy/m/d
			// yyyy/m/dd
			// yyyy/mm/d
			// yyyy-m-d
			// yyyy-m-dd
			// yyyy-mm-d
			if (strDate.getBytes().length == 8 || strDate.getBytes().length == 9) {
				String input = strDate.replaceAll("-", "/");
				if (input.indexOf("/") != -1) {
					String[] ymd = input.split("/");
					if (ymd.length == 3) {
						Date date = DateUtil.getDate(Util.avoidNullAsInt(ymd[0]), Util.avoidNullAsInt(ymd[1]),
							Util.avoidNullAsInt(ymd[2]));
						return date;
					}
				}
			}

			// 日時書式対応
			// yyyy/m/d
			// yyyy/m/dd
			// yyyy/mm/d
			// yyyy-m-d
			// yyyy-m-dd
			// yyyy-mm-d
			// hh:ms
			// hh:m
			// h:ms
			// h:m
			if (strDate.getBytes().length > 10) {
				String input = strDate.replaceAll("-", "/");
				if (input.indexOf(" ") != -1) {
					String[] ymdhm = input.split(" ");
					if (ymdhm.length == 2 && ymdhm[0].indexOf("/") != -1 && ymdhm[1].indexOf(":") != -1) {
						String[] ymd = ymdhm[0].split("/");
						String[] hm = ymdhm[1].split(":");
						if (ymd.length == 3 && hm.length >= 2) {
							// 秒はゼロ固定
							Date date = DateUtil.getDate(Util.avoidNullAsInt(ymd[0]), Util.avoidNullAsInt(ymd[1]),
								Util.avoidNullAsInt(ymd[2]), Util.avoidNullAsInt(hm[0]), Util.avoidNullAsInt(hm[1]), 0);
							return date;
						}
					}
				}
			}

		} catch (Exception e) {
			// 処理なし
		}

		return DateUtil.toDateNE(strDate);
	}
}
