package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �t�B�[���h�\��t������Util
 */
public class TTablePasteUtil {

	/** ���l */
	public static final int NUMBER = 0;

	/** ���t */
	public static final int DATE = 1;

	/** ������ */
	public static final int STRING = 2;

	/** �y�X�g�ΏۊO */
	public static final int READONLY = 9;

	/**
	 * �\��t���@�\
	 * 
	 * @param editor �e�L�X�g�t�B�[���h
	 * @param tbl �e�[�u��
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex ��ԍ�
	 * @param types �^�C�v�z��(�\��t���񁨉E�S��)
	 */
	public static void handleKeyEvent(final TTextField editor, final TTable tbl, final int rowIndex,
		final int columnIndex, final int[] types) {

		// �J�X�^�}�C�Y�L�[���X�i�[����
		for (KeyListener l : editor.getKeyListeners()) {
			if (l instanceof TTableKeyAdapter) {
				editor.removeKeyListener(l);
			}
		}

		final TTableKeyAdapter keyAdapter = new TTableKeyAdapter(editor, tbl, rowIndex, columnIndex, types);
		editor.addKeyListener(keyAdapter);
	}

	/**
	 * �N���b�v�{�[�h�̃e�L�X�g�̎擾
	 * 
	 * @return �N���b�v�{�[�h�̃e�L�X�g
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
	 * ��؂�ɂ���ĕ�����z��̎擾
	 * 
	 * @param value ������
	 * @param split ��؂�
	 * @return �z��
	 */
	public static String[] getArray(String value, String split) {
		if (Util.avoidNull(value).contains(split)) {
			return value.split(split);
		} else {
			return new String[] { value };
		}
	}

	/**
	 * �����񂩂琔�l�̎擾
	 * 
	 * @param input ������
	 * @return ���l
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
	 * �����񂩂���t�̎擾
	 * 
	 * @param strDate ������
	 * @return ���t
	 */
	public static Date getDate(String strDate) {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}

		try {
			// ���t�����Ή�
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

			// ���������Ή�
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
							// �b�̓[���Œ�
							Date date = DateUtil.getDate(Util.avoidNullAsInt(ymd[0]), Util.avoidNullAsInt(ymd[1]),
								Util.avoidNullAsInt(ymd[2]), Util.avoidNullAsInt(hm[0]), Util.avoidNullAsInt(hm[1]), 0);
							return date;
						}
					}
				}
			}

		} catch (Exception e) {
			// �����Ȃ�
		}

		return DateUtil.toDateNE(strDate);
	}
}
