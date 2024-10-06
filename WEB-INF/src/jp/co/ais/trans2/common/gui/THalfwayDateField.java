package jp.co.ais.trans2.common.gui;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 日付の文字入力.<br>
 * 2010 or 2010/06 など中途半端に入力する際に利用.
 */
public class THalfwayDateField extends TTextField {

	/**
	 * コンストラクタ.
	 */
	public THalfwayDateField() {
		super();

		this.setImeMode(false);
		this.setMaxLength(10);
		this.setRegex("[0-9/]");
	}

	/**
	 * 入力が正式な日付形式になっているかどうか
	 * 
	 * @return true:OK
	 */
	public boolean isDateFormat() {
		return DateUtil.isDate(getText());
	}

	/**
	 * 入力日付を取得.<br>
	 * 2010 → 2010/01/01 <br>
	 * 2010/08 → 2010/08/01 <br>
	 * 2010/08/17 → 2010/08/17
	 * 
	 * @return 開始日付
	 */
	public Date getDate() {
		try {
			// 入力文字
			String inputDate = getText();
			int length = inputDate.length();

			switch (length) {
				case 4:
					return DateUtil.toYDate(inputDate);

				case 7:
					return DateUtil.toYMDate(inputDate);

				case 10:
					return DateUtil.toYMDDate(inputDate);
			}

			return null;

		} catch (ParseException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 入力の開始日付を取得.<br>
	 * 2010 → 2010/01/01 <br>
	 * 2010/08 → 2010/08/01 <br>
	 * 2010/08/17 → 2010/08/17
	 * 
	 * @return 開始日付
	 */
	public Date getStartDate() {
		return getDate();
	}

	/**
	 * 入力の終了日付を取得.<br>
	 * 2010 → 2010/12/31 <br>
	 * 2010/08 → 2010/08/31 <br>
	 * 2010/08/17 → 2010/08/17
	 * 
	 * @return 終了日付
	 */
	public Date getEndDate() {
		// 入力文字
		String inputDate = getText();
		int length = inputDate.length();

		Date date = getDate();

		switch (length) {
			case 4:
				return DateUtil.getDate(DateUtil.getYear(date), 12, 31);

			case 7:
				return DateUtil.getLastDate(date);
		}

		return date;
	}
}
