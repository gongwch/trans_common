package jp.co.ais.trans2.model.calendar;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �J�����_�[�Ǘ��C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface CalendarManager {

	/**
	 * �w������ɊY������J�����_�[����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������J�����_�[���
	 * @throws TException
	 */
	public Map<String, Boolean> get(CalendarSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������J�����_�[����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������J�����_�[���
	 * @throws TException
	 */
	public List<CalendarEntity> getCalendar(CalendarSearchCondition condition) throws TException;

	/**
	 * �J�����_�[��o�^����B
	 * 
	 * @param list �J�����_�[
	 * @param condition
	 * @throws TException
	 */
	public void entry(List<CalendarEntity> list, CalendarSearchCondition condition) throws TException;

	/**
	 * �G�N�Z���捞�̏���
	 * 
	 * @param file
	 * @throws TException
	 */
	public void importExcel(File file) throws TException;

	/**
	 * �G�N�Z���o�͂̏���
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] exportExcel(CalendarSearchCondition condition) throws TException;
}
