package jp.co.ais.trans2.define;

import java.util.*;

/**
 * �`�[���
 */
public enum SlipKind {

	/** �����`�[ */
	INPUT_CASH_FLOW,

	/** �o���`�[ */
	OUTPUT_CASH_FLOW,

	/** �U�֓`�[ */
	TRANSFER,

	/** ����`�[ */
	SALES,

	/** �d���`�[ */
	PURCHASE,

	/** IFRS�����`�[ */
	IFRS_TRANSFER,

	/** �Ј��x���`�[ */
	EMPLOYEE;

	/** ��ރ��X�g */
	public static Map<String, SlipKind> kindList = Collections.synchronizedMap(new TreeMap<String, SlipKind>());

	static {
		kindList.put("11", INPUT_CASH_FLOW); // 11:�����`�[
		kindList.put("12", OUTPUT_CASH_FLOW); // 12:�o���`�[
		kindList.put("13", TRANSFER); // 13:�U�֓`�[
		kindList.put("14", TRANSFER); // 14:���ϓ`�[(�U��)
		kindList.put("15", TRANSFER); // 15:���Ϗ����`�[(�U�ߎ��)
		kindList.put("16", IFRS_TRANSFER); // 16:IFRS�����`�[
		kindList.put("17", IFRS_TRANSFER); // 17:���������`�[

		kindList.put("21", EMPLOYEE); // 21:�Ј�����
		kindList.put("22", EMPLOYEE); // 22:�o��Z
		kindList.put("23", PURCHASE); // 23:������(��)
		kindList.put("24", TRANSFER); // 24:�x���`�[(�Ј�)
		kindList.put("25", TRANSFER); // 25:�x���`�[(�Վ�)
		kindList.put("26", TRANSFER); // 26:�x���`�[(�莞)
		kindList.put("27", TRANSFER); // 27:�O����������
		kindList.put("28", TRANSFER); // 28:�x���`�[(����)
		kindList.put("2E", TRANSFER); // 2E:���۔��c��(�Ј�)
		kindList.put("2T", TRANSFER); // 2T:���۔��c��(�ЊO)

		kindList.put("31", SALES); // 31:���v��
		kindList.put("32", TRANSFER); // 32:������
		kindList.put("33", TRANSFER); // 33:���֋�������

		kindList.put("41", TRANSFER); // 41:���o�^
		kindList.put("42", TRANSFER); // 42:��茈��

		kindList.put("51", TRANSFER); // 51:�x��o�^
		kindList.put("52", TRANSFER); // 52:�x�茈��

		kindList.put("61", TRANSFER); // 61:�Œ莑�Y�U��
		kindList.put("62", TRANSFER); // 62:�Œ莑�Y�x��
	}

	/**
	 * �w��`�[�̓`�[��ނ���肷�遏
	 * 
	 * @param dataKind �f�[�^�敪
	 * @return �`�[���
	 */
	public static SlipKind get(String dataKind) {
		return kindList.get(dataKind);
	}
}
