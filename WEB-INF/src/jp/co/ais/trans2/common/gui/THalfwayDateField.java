package jp.co.ais.trans2.common.gui;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���t�̕�������.<br>
 * 2010 or 2010/06 �Ȃǒ��r���[�ɓ��͂���ۂɗ��p.
 */
public class THalfwayDateField extends TTextField {

	/**
	 * �R���X�g���N�^.
	 */
	public THalfwayDateField() {
		super();

		this.setImeMode(false);
		this.setMaxLength(10);
		this.setRegex("[0-9/]");
	}

	/**
	 * ���͂������ȓ��t�`���ɂȂ��Ă��邩�ǂ���
	 * 
	 * @return true:OK
	 */
	public boolean isDateFormat() {
		return DateUtil.isDate(getText());
	}

	/**
	 * ���͓��t���擾.<br>
	 * 2010 �� 2010/01/01 <br>
	 * 2010/08 �� 2010/08/01 <br>
	 * 2010/08/17 �� 2010/08/17
	 * 
	 * @return �J�n���t
	 */
	public Date getDate() {
		try {
			// ���͕���
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
	 * ���͂̊J�n���t���擾.<br>
	 * 2010 �� 2010/01/01 <br>
	 * 2010/08 �� 2010/08/01 <br>
	 * 2010/08/17 �� 2010/08/17
	 * 
	 * @return �J�n���t
	 */
	public Date getStartDate() {
		return getDate();
	}

	/**
	 * ���͂̏I�����t���擾.<br>
	 * 2010 �� 2010/12/31 <br>
	 * 2010/08 �� 2010/08/31 <br>
	 * 2010/08/17 �� 2010/08/17
	 * 
	 * @return �I�����t
	 */
	public Date getEndDate() {
		// ���͕���
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
