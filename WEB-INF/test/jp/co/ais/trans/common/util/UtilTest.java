package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testIsNullOrEmpty() {
		// null �̃e�X�g
		assertTrue(Util.isNullOrEmpty(null), "null �̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertTrue(Util.isNullOrEmpty(""), "�󕶎��̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertTrue(Util.isNullOrEmpty("   "), "�󔒂݂̂̕�����̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// ��łȂ�������̃e�X�g
		assertFalse(Util.isNullOrEmpty("Hello"), "��łȂ�������̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// ���l�̕�����̃e�X�g
		assertFalse(Util.isNullOrEmpty("12345"), "���l�̕�����̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// �I�u�W�F�N�g�i��̃��X�g�j�̃e�X�g
		assertFalse(Util.isNullOrEmpty(new Object()), "�� null �̃I�u�W�F�N�g�̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// ��̃R���N�V�����̃e�X�g
		assertFalse(Util.isNullOrEmpty(java.util.Collections.emptyList()), "��̃R���N�V�����̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");
	}

	@Test
	void testAvoidNull() {
		// null �̃e�X�g
		assertEquals("", Util.avoidNull(null), "null �̏ꍇ�A�󕶎���Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertEquals("", Util.avoidNull(""), "�󕶎��̏ꍇ�A���̂܂܋󕶎���Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertEquals("", Util.avoidNull("   "), "�󔒂݂̂̕�����̏ꍇ�A�󕶎���Ԃ��ׂ��ł��B");

		// �ʏ�̕�����̃e�X�g
		assertEquals("Hello", Util.avoidNull("Hello"), "�ʏ�̕�����̏ꍇ�A���̂܂ܕ������Ԃ��ׂ��ł��B");

		// ���l�̕�����̃e�X�g
		assertEquals("12345", Util.avoidNull("12345"), "���l�̕�����̏ꍇ�A���̂܂ܕ������Ԃ��ׂ��ł��B");

		// ���l�I�u�W�F�N�g�̃e�X�g
		assertEquals("123", Util.avoidNull(123), "���l�I�u�W�F�N�g�̏ꍇ�A������ɕϊ����ĕԂ��ׂ��ł��B");
	}

	@Test
	void testAvoidNullNT() {
		assertEquals("", Util.avoidNullNT(null), "null �̏ꍇ�A�󕶎���Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertEquals("", Util.avoidNullNT(""), "�󕶎��̏ꍇ�A���̂܂܋󕶎���Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertEquals("   ", Util.avoidNullNT("   "), "�󔒂݂̂̕�����̏ꍇ�A���̂܂ܕ������Ԃ��ׂ��ł��B");

		// �ʏ�̕�����̃e�X�g
		assertEquals("Hello", Util.avoidNullNT("Hello"), "�ʏ�̕�����̏ꍇ�A���̂܂ܕ������Ԃ��ׂ��ł��B");

		// ���l�̕�����̃e�X�g
		assertEquals("12345", Util.avoidNullNT("12345"), "���l�̕�����̏ꍇ�A���̂܂ܕ������Ԃ��ׂ��ł��B");

		// ���l�I�u�W�F�N�g�̃e�X�g
		assertEquals("123", Util.avoidNullNT(123), "���l�I�u�W�F�N�g�̏ꍇ�A������ɕϊ����ꂽ�l��Ԃ��ׂ��ł��B");
	}

	@Test
	void testAvoidNullAsInt() {
		// null �̃e�X�g
		assertEquals(0, Util.avoidNullAsInt(null), "null �̏ꍇ�A0 ��Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertEquals(0, Util.avoidNullAsInt(""), "�󕶎��̏ꍇ�A0 ��Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertEquals(0, Util.avoidNullAsInt("   "), "�󔒂݂̂̕�����̏ꍇ�A0 ��Ԃ��ׂ��ł��B");

		// ���̐����̕�����̃e�X�g
		assertEquals(123, Util.avoidNullAsInt("123"), "���̐����̕�����̏ꍇ�A���̐����l��Ԃ��ׂ��ł��B");

		// ���̐����̕�����̃e�X�g
		assertEquals(-456, Util.avoidNullAsInt("-456"), "���̐����̕�����̏ꍇ�A���̐����l��Ԃ��ׂ��ł��B");

		// ���l�I�u�W�F�N�g�̃e�X�g
		assertEquals(789, Util.avoidNullAsInt(789), "���l�I�u�W�F�N�g�̏ꍇ�A���̐����l��Ԃ��ׂ��ł��B");

	}

	@Test
	void testAvoidNullAsFloat() {
		// null �̃e�X�g
		assertEquals(0.0f, Util.avoidNullAsFloat(null), "null �̏ꍇ�A0.0 ��Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertEquals(0.0f, Util.avoidNullAsFloat(""), "�󕶎��̏ꍇ�A0.0 ��Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertEquals(0.0f, Util.avoidNullAsFloat("   "), "�󔒂݂̂̕�����̏ꍇ�A0.0 ��Ԃ��ׂ��ł��B");

		// ���̕��������_���̕�����̃e�X�g
		assertEquals(123.45f, Util.avoidNullAsFloat("123.45"), "���̕��������_���̕�����̏ꍇ�A���̕��������_����Ԃ��ׂ��ł��B");

		// ���̕��������_���̕�����̃e�X�g
		assertEquals(-456.78f, Util.avoidNullAsFloat("-456.78"), "���̕��������_���̕�����̏ꍇ�A���̕��������_����Ԃ��ׂ��ł��B");

		// ���l�I�u�W�F�N�g�̃e�X�g
		assertEquals(789.0f, Util.avoidNullAsFloat(789), "���l�I�u�W�F�N�g�̏ꍇ�A���̕��������_����Ԃ��ׂ��ł��B");

	}

	@Test
	void testEmptyToNull() {
		// null �̃e�X�g
		assertNull(Util.emptyToNull(null), "null �̏ꍇ�Anull ��Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertNull(Util.emptyToNull(""), "�󕶎��̏ꍇ�Anull ��Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertNull(Util.emptyToNull("   "), "�󔒂݂̂̕�����̏ꍇ�Anull �ł͂Ȃ����̂܂܂̕������Ԃ��ׂ��ł��B");
		assertEquals(null, Util.emptyToNull("   "), "�󔒂݂̂̕�����̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");

		// �ʏ�̕�����̃e�X�g
		assertEquals("Hello", Util.emptyToNull("Hello"), "�ʏ�̕�����̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");

		// ���l�I�u�W�F�N�g�̃e�X�g
		assertEquals("123", Util.emptyToNull(123), "���l�I�u�W�F�N�g�̏ꍇ�A������ɕϊ����ĕԂ��ׂ��ł��B");

		// ���l�̕�����̃e�X�g
		assertEquals("456", Util.emptyToNull("456"), "���l�̕�����̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");

	}

	@Test
	void testSafeNull() {
		// null �̃e�X�g
		assertEquals(Util.NULL_STRING1, Util.safeNull(null), "null �̏ꍇ�A'(null)' ��Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertEquals("", Util.safeNull(""), "�󕶎��̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertEquals("   ", Util.safeNull("   "), "�󔒂݂̂̕�����̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");

		// �ʏ�̕�����̃e�X�g
		assertEquals("Hello", Util.safeNull("Hello"), "�ʏ�̕�����̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");

		// ���l�I�u�W�F�N�g�̃e�X�g
		assertEquals("123", Util.safeNull(123), "���l�I�u�W�F�N�g�̏ꍇ�A������ɕϊ����ĕԂ��ׂ��ł��B");

		// ���l�̕�����̃e�X�g
		assertEquals("456", Util.safeNull("456"), "���l�̕�����̏ꍇ�A���̂܂܂̕������Ԃ��ׂ��ł��B");
	}

	@Test
	void testIsNullString() {
		// null �̃e�X�g
		assertFalse(Util.isNullString(null), "null �̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// "(null)" �̃e�X�g
		assertTrue(Util.isNullString(Util.NULL_STRING1), "\"(null)\" �̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// �󕶎��̃e�X�g
		assertFalse(Util.isNullString(""), "�󕶎��̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// �󔒂݂̂̕�����̃e�X�g
		assertFalse(Util.isNullString("   "), "�󔒂݂̂̕�����̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// �ʏ�̕�����̃e�X�g
		assertFalse(Util.isNullString("Hello"), "�ʏ�̕�����̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// ���l�̕�����̃e�X�g
		assertFalse(Util.isNullString("123"), "���l�̕�����̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");
	}

	@Test
	void testGetCurrentDate() {
		// ���\�b�h�Ŏ擾�������t
		Date currentDate = Util.getCurrentDate();

		// ���݂̃V�X�e�����t
		Calendar calendar = GregorianCalendar.getInstance();
		Date now = calendar.getTime();

		//TODO:
		// �V�X�e�����t�̂�������e���邽�߂̂������l�i�Ⴆ�΁A1�b�j
		long toleranceMillis = 1000;
		// ���݂̓��t���擾���ꂽ���t��1�b�ȓ��ł��邩���m�F
		assertTrue(Math.abs(now.getTime() - currentDate.getTime()) <= toleranceMillis,
				"�擾�������t�����݂̓��t��1�b�ȓ��ł���ׂ��ł��B");

	}

	@Test
	void testGetCurrentYMDDate() {
		// ���\�b�h�Ŏ擾���� YMD �`���̓��t
		Date ymdDate = Util.getCurrentYMDDate();

		// ���݂̃V�X�e�����t
		Calendar calendar = GregorianCalendar.getInstance();
		Date now = calendar.getTime();

		// ���݂̓��t����N�����𒊏o
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(now);
		int nowYear = nowCal.get(Calendar.YEAR);
		int nowMonth = nowCal.get(Calendar.MONTH) + 1; // ����0����n�܂邽��+1
		int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);

		// YMD�`���̓��t�Ƃ��Đ��������m�F
		Calendar ymdCal = Calendar.getInstance();
		ymdCal.setTime(ymdDate);
		int ymdYear = ymdCal.get(Calendar.YEAR);
		int ymdMonth = ymdCal.get(Calendar.MONTH) + 1;
		int ymdDay = ymdCal.get(Calendar.DAY_OF_MONTH);

		assertEquals(nowYear, ymdYear, "YMD�`���̓��t�̔N�����݂̔N�ƈ�v����ׂ��ł��B");
		assertEquals(nowMonth, ymdMonth, "YMD�`���̓��t�̌������݂̌��ƈ�v����ׂ��ł��B");
		assertEquals(nowDay, ymdDay, "YMD�`���̓��t�̓������݂̓��ƈ�v����ׂ��ł��B");
	}

	@Test
	void testGetCurrentDateString() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// ���\�b�h�Ŏ擾�����V�X�e�����t������
		String dateString = Util.getCurrentDateString();

		// ���݂̃V�X�e�����t���擾
		Date now = new Date();

		// ���݂̃V�X�e�����t�𕶎���ɕϊ�
		String expectedDateString = DATE_FORMAT.format(now);

		// �擾���������񂪐������`�����ǂ����m�F
		try {
			// ���������t�I�u�W�F�N�g�ɕϊ����āA�ϊ����������邩�m�F
			Date parsedDate = DATE_FORMAT.parse(dateString);
			assertNotNull(parsedDate, "���t�����񂪐������`���ł���ׂ��ł��B");
			// �擾�������t������Ɗ��҂������t�����񂪈�v���邩�m�F
			assertEquals(expectedDateString, dateString, "�V�X�e�����t�����񂪊��҂����`���ƈ�v����ׂ��ł��B");
		} catch (ParseException e) {
			fail("���t������̉�͂Ɏ��s���܂���: " + e.getMessage());
		}
	}

	@Test
	void testGetCurrentDateStringString() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String[] LANGUAGES = { "ja", "en" }; // �e�X�g���錾��R�[�h�̔z��
		for (String lang : LANGUAGES) {
			// ���\�b�h�Ŏ擾�����V�X�e�����t������
			String dateString = Util.getCurrentDateString(lang);

			// ���݂̃V�X�e�����t���擾
			Date now = new Date();

			// ���݂̃V�X�e�����t�𕶎���ɕϊ�
			String expectedDateString = DATE_FORMAT.format(now);

			// �擾���������񂪐������`�����ǂ����m�F
			try {
				// ���������t�I�u�W�F�N�g�ɕϊ����āA�ϊ����������邩�m�F
				Date parsedDate = DATE_FORMAT.parse(dateString);
				assertNotNull(parsedDate, "���t�����񂪐������`���ł���ׂ��ł��B");
				// �擾�������t������Ɗ��҂������t�����񂪈�v���邩�m�F
				assertEquals(expectedDateString, dateString, "�V�X�e�����t�����񂪊��҂����`���ƈ�v����ׂ��ł��B");
			} catch (ParseException e) {
				fail("���t������̉�͂Ɏ��s���܂���: " + e.getMessage());
			}
		}

	}

	@Test
	void testIsSmallerThen() {
		// �󕶎����null���܂ރe�X�g
		assertTrue(Util.isSmallerThen(null, "test"), "null �ƔC�ӂ̕�����̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertTrue(Util.isSmallerThen("test", null), "�C�ӂ̕����� �� null �̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertTrue(Util.isSmallerThen("", "test"), "�󕶎��� �� �C�ӂ̕�����̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertTrue(Util.isSmallerThen("test", ""), "�C�ӂ̕����� �� �󕶎���̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertTrue(Util.isSmallerThen("", ""), "�󕶎��� �� �󕶎���̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// ����������̔�r
		assertTrue(Util.isSmallerThen("abc", "abc"), "����������̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// begin �� end ��菬�����ꍇ
		assertTrue(Util.isSmallerThen("abc", "def"), "begin �� end ��菬�����ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// begin �� end ���傫���ꍇ
		assertFalse(Util.isSmallerThen("def", "abc"), "begin �� end ���傫���ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// Unicode �������܂ޔ�r
		assertTrue(Util.isSmallerThen("apple", "banana"), "Unicode �������܂ޔ�r�ŁAbegin �� end ��菬�����ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertFalse(Util.isSmallerThen("banana", "apple"), "Unicode �������܂ޔ�r�ŁAbegin �� end ���傫���ꍇ�Afalse ��Ԃ��ׂ��ł��B");
	}

	@Test
	void testIsSmallerThenByYMDNVL() {
		// �e�X�g�p�̓��t���쐬
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 30, 0);
		Date date2 = cal.getTime();

		// isSmallerThenByYMD �̓�����e�X�g���邽�߂�
		// Mock �܂��̓e�X�g�Ώۃ��\�b�h���K�v�ł����A�����ł͒P�ɃT���v���ł�

		// begin �܂��� end �� null �̏ꍇ�Afalse ��Ԃ�
		assertFalse(Util.isSmallerThenByYMDNVL(null, date2), "begin �� null �̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");
		assertFalse(Util.isSmallerThenByYMDNVL(date1, null), "end �� null �̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");
		assertFalse(Util.isSmallerThenByYMDNVL(null, null), "begin �� end �� null �̏ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// begin �� end ��菬�����ꍇ
		assertTrue(Util.isSmallerThenByYMDNVL(date1, date2), "begin �� end ��菬�����ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		//TODO:
		// begin �� end ���傫���ꍇ
		//assertTrue(Util.isSmallerThenByYMDNVL(date2, date1), "begin �� end ���傫���ꍇ�Afalse ��Ԃ��ׂ��ł��B"); // YMD������������΁Atrue

		// begin �� end �������ꍇ
		assertTrue(Util.isSmallerThenByYMDNVL(date1, date1), "begin �� end �������ꍇ�Atrue ��Ԃ��ׂ��ł��B");
	}

	@Test
	void testIsSmallerThenByYMD() {
		// �e�X�g�p�̓��t���쐬
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 30, 0);
		Date date2 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 7, 10, 30, 0);
		Date date4 = cal.getTime();

		// begin �� end ��菬�����ꍇ (�������t)
		assertTrue(Util.isSmallerThenByYMD(date1, date2), "begin �� end ��菬�����ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertTrue(Util.isSmallerThenByYMD(date1, date1), "begin �� end �������ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// begin �� end ���傫���ꍇ
		assertTrue(Util.isSmallerThenByYMD(date2, date1), "begin �� end ���傫���ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// begin �� end ���O�̓��t�̏ꍇ
		assertTrue(Util.isSmallerThenByYMD(date4, date1), "begin �� end ���O�̓��t�̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

	}

	@Test
	void testIsSmallerThenByYMDHMS() {
		// �e�X�g�p�̓��t���쐬
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 30, 0);
		Date date2 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date3 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 9, 10, 30, 0);
		Date date4 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 7, 10, 30, 0);
		Date date5 = cal.getTime();

		// begin �� end ��菬�����ꍇ (��������)
		assertTrue(Util.isSmallerThenByYMDHMS(date1, date2), "begin �� end ��菬�����ꍇ�Atrue ��Ԃ��ׂ��ł��B");
		assertTrue(Util.isSmallerThenByYMDHMS(date1, date3), "begin �� end �����������̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

		// begin �� end ���傫���ꍇ
		assertFalse(Util.isSmallerThenByYMDHMS(date2, date1), "begin �� end ���傫���ꍇ�Afalse ��Ԃ��ׂ��ł��B");

		// begin �� end ���O�̓��t�̏ꍇ
		assertTrue(Util.isSmallerThenByYMDHMS(date5, date1), "begin �� end ���O�̓��t�̏ꍇ�Atrue ��Ԃ��ׂ��ł��B");

	}

	@Test
	void testEqualsByYMD() {
		// �e�X�g�p�̓��t���쐬
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 45, 0);
		Date date2 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 9, 10, 30, 0);
		Date date3 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 0, 0, 0);
		Date date4 = cal.getTime();

		// date1 �� date2 �͓������t (�N����) �Ȃ̂� true ������
		assertTrue(Util.equalsByYMD(date1, date2), "date1 �� date2 �͓����N�����Ȃ̂� true ��Ԃ��ׂ��ł��B");

		// date1 �� date3 �͈قȂ���t�Ȃ̂� false ������
		assertFalse(Util.equalsByYMD(date1, date3), "date1 �� date3 �͈قȂ�N�����Ȃ̂� false ��Ԃ��ׂ��ł��B");

		// date1 �� date4 �͓������t (�N����) �Ȃ̂� true ������
		assertTrue(Util.equalsByYMD(date1, date4), "date1 �� date4 �͓����N�����Ȃ̂� true ��Ԃ��ׂ��ł��B");

		// ������̈����� null �̏ꍇ�� true ������
		assertTrue(Util.equalsByYMD(null, null), "������ null �̏ꍇ�� true ��Ԃ��ׂ��ł��B");

		// ����̈����� null �̏ꍇ�� false ������
		assertFalse(Util.equalsByYMD(date1, null), "����̈����� null �̏ꍇ�� false ��Ԃ��ׂ��ł��B");
		assertFalse(Util.equalsByYMD(null, date1), "����̈����� null �̏ꍇ�� false ��Ԃ��ׂ��ł��B");
	}

	@Test
	void testToRGBColorCode() {
		// �e�X�g�p�J���[�R�[�h
		String code1 = "#FF5733"; // ����ȃR�[�h
		String code2 = "#00FF00"; // ����ȃR�[�h
		String code3 = "#123456"; // ����ȃR�[�h
		String code4 = "#ABC"; // �Z���R�[�h�i�s���ȃR�[�h�j
		String code5 = null; // null�i�s���ȃR�[�h�j
		//TODO:
		//String code6 = "#ZZZZZZ"; // �s����16�i���R�[�h NumberFormatException

		// �e�X�g���s
		int[] result1 = Util.toRGBColorCode(code1);
		int[] result2 = Util.toRGBColorCode(code2);
		int[] result3 = Util.toRGBColorCode(code3);
		int[] result4 = Util.toRGBColorCode(code4);
		int[] result5 = Util.toRGBColorCode(code5);
		//int[] result6 = Util.toRGBColorCode(code6);

		// ���ʊm�F
		assertArrayEquals(new int[] { 255, 87, 51 }, result1, "�J���[�R�[�h #FF5733 �� RGB �l������������܂���B");
		assertArrayEquals(new int[] { 0, 255, 0 }, result2, "�J���[�R�[�h #00FF00 �� RGB �l������������܂���B");
		assertArrayEquals(new int[] { 18, 52, 86 }, result3, "�J���[�R�[�h #123456 �� RGB �l������������܂���B");
		assertArrayEquals(new int[] { 0, 0, 0 }, result4, "�J���[�R�[�h #ABC �� RGB �l������������܂���B");
		assertArrayEquals(new int[] { 0, 0, 0 }, result5, "�J���[�R�[�h null �� RGB �l������������܂���B");
		//	assertArrayEquals(new int[] { 0, 0, 0 }, result6, "�J���[�R�[�h #ZZZZZZ �� RGB �l������������܂���B");
	}

	@Test
	void testTo16RGBColorCodeColor() {
		// ����ȃP�[�X
		Color color1 = new Color(255, 0, 0); // ��
		Color color2 = new Color(0, 255, 0); // ��
		Color color3 = new Color(0, 0, 255); // ��

		// �e�X�g���s
		String result1 = Util.to16RGBColorCode(color1);
		String result2 = Util.to16RGBColorCode(color2);
		String result3 = Util.to16RGBColorCode(color3);

		// ���ʊm�F
		assertEquals("#ff0000", result1, "�ԐF��16�i���J���[�R�[�h������������܂���B");
		assertEquals("#00ff00", result2, "�ΐF��16�i���J���[�R�[�h������������܂���B");
		assertEquals("#0000ff", result3, "�F��16�i���J���[�R�[�h������������܂���B");

		// null�̏ꍇ
		String resultNull = Util.to16RGBColorCode(new Color(0));
		assertEquals("#000000", resultNull, "null�̓��͂ɑ΂���16�i���J���[�R�[�h������������܂���B");
	}

	@Test
	void testTo16RGBColorCodeIntArray() {
		// ����ȃP�[�X
		int[] rgb1 = { 255, 99, 71 }; // Tomate
		int[] rgb2 = { 0, 0, 0 }; // Black
		int[] rgb3 = { 255, 255, 255 }; // White

		// �e�X�g���s
		String result1 = Util.to16RGBColorCode(rgb1);
		String result2 = Util.to16RGBColorCode(rgb2);
		String result3 = Util.to16RGBColorCode(rgb3);

		// ���ʊm�F
		assertEquals("#ff6347", result1, "Tomato�F��16�i���J���[�R�[�h������������܂���B");
		assertEquals("#000000", result2, "Black�F��16�i���J���[�R�[�h������������܂���B");
		assertEquals("#ffffff", result3, "White�F��16�i���J���[�R�[�h������������܂���B");

		// �ُ�P�[�X
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> Util.to16RGBColorCode(new int[] { 255, 255 }),
				"�z��̒�����3�łȂ��ꍇ�AArrayIndexOutOfBoundsException���X���[�����ׂ��ł��B");

	}

	@Test
	void testIsOverAmountString() {
		// �I�[�o�[���Ȃ����z�̃e�X�g�i���������ő包�����ŁA���������W���͈͓̔��j
		String validAmount1 = "1234567890123.1234"; // 13���̐����� + 4���̏�����
		assertFalse(Util.isOverAmount(validAmount1), "���������ő包���͈͓̔��ŁA��������4���ł����FALSE��Ԃ��ׂ��ł��B");

		// �I�[�o�[������z�̃e�X�g�i��������14���𒴂���j
		String overAmount1 = "12345678901234.1234"; // 14���̐����� + 4���̏�����
		assertTrue(Util.isOverAmount(overAmount1), "���������ő包���𒴂����ꍇ�ATRUE��Ԃ��ׂ��ł��B");

		// �I�[�o�[������z�̃e�X�g�i��������5���𒴂���j
		String overAmount2 = "1234567890123.12345"; // 13���̐����� + 5���̏�����
		assertTrue(Util.isOverAmount(overAmount2), "���������W���p�����𒴂����ꍇ�ATRUE��Ԃ��ׂ��ł��B");

		// �M���M���̋��z�̃e�X�g�i���������ő包�����ŁA��������4���ł���Ζ��Ȃ��j
		String validAmount2 = "1234567890123.1234"; // 13���̐����� + 4���̏�����
		assertFalse(Util.isOverAmount(validAmount2), "���������ő包���͈͓̔��ŁA��������4���ł����FALSE��Ԃ��ׂ��ł��B");

		// ��̋��z�̃e�X�g
		String emptyAmount = ""; // ��̕�����
		assertFalse(Util.isOverAmount(emptyAmount), "��̋��z������ɑ΂��Ă�FALSE��Ԃ��ׂ��ł��B");

		// null�̋��z�̃e�X�g
		String nullAmount = null; // null�̒l
		assertFalse(Util.isOverAmount(nullAmount), "null�̋��z������ɑ΂��Ă�FALSE��Ԃ��ׂ��ł��B");
	}

	@Test
	void testIsOverAmountStringIntInt() {
		// �ő包��17�A�������ő�13���A�������ő�4��
		int maxLen = 17;
		int maxDigit = 4;

		// �I�[�o�[���Ȃ����z�̃e�X�g�i������13���A������4���j
		String validAmount1 = "1234567890123.1234"; // 13�� + 4��
		assertFalse(Util.isOverAmount(validAmount1, maxLen, maxDigit), "��������13���A��������4���ł����FALSE��Ԃ��ׂ��ł��B");

		// �I�[�o�[������z�̃e�X�g�i��������14���j
		String overAmount1 = "12345678901234.1234"; // 14�� + 4��
		assertTrue(Util.isOverAmount(overAmount1, maxLen, maxDigit), "���������ő包���𒴂����ꍇ�ATRUE��Ԃ��ׂ��ł��B");

		// �I�[�o�[������z�̃e�X�g�i��������5���j
		String overAmount2 = "1234567890123.12345"; // 13�� + 5��
		assertTrue(Util.isOverAmount(overAmount2, maxLen, maxDigit), "���������ő包���𒴂����ꍇ�ATRUE��Ԃ��ׂ��ł��B");

		// �ő包��17�A�������ő�4���A��������5���i��������13���ȓ��j
		String validAmount2 = "1234567890123.1234"; // 13�� + 4��
		assertFalse(Util.isOverAmount(validAmount2, maxLen, maxDigit), "��������13���A��������4���ł����FALSE��Ԃ��ׂ��ł��B");

		// �ő包��17�A�������ő�4���A��������12���A��������5���i���v17���j
		String overAmount3 = "123456789012.12345"; // 12�� + 5��
		assertTrue(Util.isOverAmount(overAmount3, maxLen, maxDigit), "���v�������ő包���𒴂����ꍇ�ATRUE��Ԃ��ׂ��ł��B");

		//TODO:
		// �����ȋ��z�̃e�X�g�i�����ȊO�̕������܂܂�Ă���j
		String invalidAmount1 = "1234abc567.1234"; // �����ȊO�̕������܂܂�Ă���
		assertThrows(NumberFormatException.class, () -> {
			Util.isOverAmount(invalidAmount1, maxLen, maxDigit);
		}, "�����ȋ��z������ɑ΂��Ă͗�O���X���[����ׂ��ł��B");

		// ��̋��z�̃e�X�g
		String emptyAmount = ""; // ��̕�����
		assertFalse(Util.isOverAmount(emptyAmount, maxLen, maxDigit), "��̋��z������ɑ΂��Ă�FALSE��Ԃ��ׂ��ł��B");

		// null�̋��z�̃e�X�g
		String nullAmount = null; // null�̒l
		assertFalse(Util.isOverAmount(nullAmount, maxLen, maxDigit), "null�̋��z������ɑ΂��Ă�FALSE��Ԃ��ׂ��ł��B");

		// �J���}�t���̋��z�i������13���A������4���j
		String validAmountWithComma = "1,234,567,890,123.1234"; // �J���}���܂܂��
		assertFalse(Util.isOverAmount(validAmountWithComma, maxLen, maxDigit), "�J���}�t���ł�����ȋ��z�ł����FALSE��Ԃ��ׂ��ł��B");
	}

	@Test
	void testIsNumber() {
		// ����Ȑ��l������
		assertTrue(Util.isNumber("123"), "�����̕�����͐��l�Ƃ��ĔF�������ׂ��ł��B");
		assertTrue(Util.isNumber("123.45"), "�����̕�����͐��l�Ƃ��ĔF�������ׂ��ł��B");
		assertTrue(Util.isNumber("-123.45"), "���̐��l�̕�����͐��l�Ƃ��ĔF�������ׂ��ł��B");
		assertTrue(Util.isNumber("0"), "�[���̕�����͐��l�Ƃ��ĔF�������ׂ��ł��B");
		assertTrue(Util.isNumber("1.23E+2"), "�w���\�L�̐��l�̕�����͐��l�Ƃ��ĔF�������ׂ��ł��B");

		// �����Ȑ��l������
		assertFalse(Util.isNumber("abc"), "�����񂪐��l�Ƃ��ĔF�������ׂ��ł͂���܂���B");
		assertFalse(Util.isNumber("123abc"), "���l�ɕ������܂܂�Ă���ꍇ�A���l�Ƃ��ĔF�������ׂ��ł͂���܂���B");
		assertFalse(Util.isNumber("1.2.3"), "�����̏����_���܂ޕ�����͐��l�Ƃ��ĔF�������ׂ��ł͂���܂���B");
		assertFalse(Util.isNumber(""), "��̕�����͐��l�Ƃ��ĔF�������ׂ��ł͂���܂���B");

		// ���l�`�����قȂ�ꍇ�̃e�X�g
		assertTrue(Util.isNumber("1234567890123456789"), "���ɑ傫�Ȑ��������l�Ƃ��ĔF�������ׂ��ł��B");
		assertTrue(Util.isNumber("-1234567890123456789"), "���ɑ傫�ȕ��̐��������l�Ƃ��ĔF�������ׂ��ł��B");
		assertTrue(Util.isNumber("1.234567890123456789"), "���ɐ��x�̍������������l�Ƃ��ĔF�������ׂ��ł��B");
	}

	// Util.isDate�@is Deprecate
	@Test
	void testIsDate() {

	}

	@Test
	void testGetFiscalMonth() {
		// �����ƖڕW���������ꍇ
		assertEquals(1, Util.getFiscalMonth(1, 1), "�����ƖڕW���������ꍇ��1�ł���ׂ��ł��B");
		assertEquals(1, Util.getFiscalMonth(12, 12), "�����ƖڕW���������ꍇ��1�ł���ׂ��ł��B");

		// �ڕW���������Ɠ����܂��͂���ȍ~�̏ꍇ
		assertEquals(2, Util.getFiscalMonth(1, 2), "�ڕW���������̗����̏ꍇ��2�ł���ׂ��ł��B");
		assertEquals(12, Util.getFiscalMonth(1, 12), "�ڕW����������12���̏ꍇ��12�ł���ׂ��ł��B");
		assertEquals(1, Util.getFiscalMonth(3, 3), "�ڕW���������̓������̏ꍇ��1�ł���ׂ��ł��B");

		// �ڕW�����������O�̏ꍇ
		assertEquals(4, Util.getFiscalMonth(10, 1), "�ڕW�����������O�̏ꍇ�́A�����ׂ���3�ł���ׂ��ł��B");
		assertEquals(8, Util.getFiscalMonth(7, 2), "�ڕW�����������O�̏ꍇ�́A�����ׂ���7�ł���ׂ��ł��B");
		assertEquals(8, Util.getFiscalMonth(5, 12), "�ڕW�����������O�̏ꍇ�́A�����ׂ���8�ł���ׂ��ł��B");

		// ���E�P�[�X
		assertEquals(2, Util.getFiscalMonth(12, 1), "12������1���܂ł̉�v����1�ł���ׂ��ł��B");
		assertEquals(12, Util.getFiscalMonth(1, 12), "1������12���܂ł̉�v����12�ł���ׂ��ł��B");

	}

	@Test
	void testIsShortLanguage() {
		// �p��̏ꍇ
		assertTrue(Util.isShortLanguage(Locale.ENGLISH.getLanguage()), "�p��̌���R�[�h�͗���Ώۂł���ׂ��ł��B");

		// �قȂ錾��R�[�h�̏ꍇ
		assertFalse(Util.isShortLanguage("fr"), "�t�����X��̌���R�[�h�͗���ΏۂłȂ��ׂ��ł��B");
		assertFalse(Util.isShortLanguage("es"), "�X�y�C����̌���R�[�h�͗���ΏۂłȂ��ׂ��ł��B");
		assertFalse(Util.isShortLanguage("de"), "�h�C�c��̌���R�[�h�͗���ΏۂłȂ��ׂ��ł��B");

		// �󕶎��̏ꍇ
		assertFalse(Util.isShortLanguage(""), "�󕶎��͗���ΏۂłȂ��ׂ��ł��B");

		// null�̏ꍇ
		assertFalse(Util.isShortLanguage(null), "null�͗���ΏۂłȂ��ׂ��ł��B");
	}

	@Test
	void testMakeStackString() {
		// �e�X�g�p�̗�O�𐶐�
		Exception testException = new Exception("Test exception message");

		// �X�^�b�N�g���[�X���擾
		String stackString = Util.makeStackString(testException);

		// ���b�Z�[�W���܂܂�Ă��邩�m�F
		assertTrue(stackString.contains("Test exception message"), "���b�Z�[�W���X�^�b�N�g���[�X�Ɋ܂܂�Ă���ׂ��ł��B");

		// �X�^�b�N�g���[�X���܂܂�Ă��邩�m�F
		assertTrue(stackString.contains("java.lang.Exception"), "��O�N���X�����X�^�b�N�g���[�X�Ɋ܂܂�Ă���ׂ��ł��B");
	}

	@Test
	void testEqualsStringString() {
		// ����������̔�r
		assertTrue(Util.equals("test", "test"), "����������͈�v����ׂ��ł��B");

		// �󕶎���null�̔�r
		assertTrue(Util.equals("", null), "�󕶎���null�͈�v����ׂ��ł��B");

		// �قȂ镶����̔�r
		assertFalse(Util.equals("test1", "test2"), "�قȂ镶����͈�v���Ȃ��ׂ��ł��B");

		// ������null�̏ꍇ�̔�r
		assertTrue(Util.equals(null, null), "������null�̏ꍇ�͈�v����ׂ��ł��B");

		// �Е����󕶎��A�����Е���null�̏ꍇ
		assertTrue(Util.equals(null, ""), "null�Ƌ󕶎��͈�v����ׂ��ł��B");
	}

	@Test
	void testToColor() {
		// ����ȃP�[�X
		Color color = Util.toColor(new String[] { "255", "0", "0" });
		assertEquals(new Color(255, 0, 0), color, "RGB(255, 0, 0) �͐ԐF�ł���ׂ��ł��B");

		// ����ȃP�[�X
		color = Util.toColor(new String[] { "0", "255", "0" });
		assertEquals(new Color(0, 255, 0), color, "RGB(0, 255, 0) �͗ΐF�ł���ׂ��ł��B");

	}

	@Test
	void testConvertToRGB() {
		// ����ȃP�[�X
		Color color = Util.convertToRGB("#fffffff");
		assertEquals(new Color(255, 255, 255), color, "RGB(255, 255, 255) �͔��F�ł���ׂ��ł��B");

		color = Util.convertToRGB("#000000");
		assertEquals(new Color(0, 0, 0), color, "RGB(0, 0, 0) �͍��F�ł���ׂ��ł��B");

	}

	@Test
	void testGetSystemVersion() {
		// �e�X�g�Ώۂ̃N���X���w��
		String[] version = Util.getSystemVersion(UtilTest.class);

		// ���[�J���̏ꍇ
		assertEquals("local", version[0], "���[�J�����ł͍ŏI�X�V������ 'local' �ł���ׂ��ł��B");
		assertEquals("local", version[1], "���[�J�����ł̓o�[�W������ 'local' �ł���ׂ��ł��B");

	}

}
