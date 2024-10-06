package jp.co.ais.trans.common.client.http;

import java.io.UnsupportedEncodingException;

/**
 * �����G���R�[�f�B���O��\���N���X.
 * <p>
 * ��1:<br>
 * �G���R�[�h�����w�肵��Encoding�I�u�W�F�N�g�𐶐�����B<br>
 * 
 * <pre>
 * Encoding sjis = Encoding.getEncoding(&quot;Shift_JIS&quot;);
 * 
 * String unicodeString = sjis.getString(shift_jis_string);
 * </pre>
 * 
 * </p>
 * <p>
 * ��2:<br>
 * ��`�ς�Encoding�I�u�W�F�N�g�𗘗p����B<br>
 * 
 * <pre>
 * String unicodeString = Encoding.sjis.getString(shift_jis_string);
 * </pre>
 * 
 * ��L�R�[�h�͎��Ɠ����ɂȂ�D<br>
 * 
 * <pre>
 * String unicodeString;
 * try {
 * 	unicodeString = new String(shift_jis_string.getByte(&quot;iso-8859-1&quot;), &quot;Shift_JIS&quot;);
 * } catch (UnsupportedEncodingException e) {
 * 	throw new IllegalArgumentException(e.getMessage());
 * }
 * </pre>
 * 
 * </p>
 */
public class Encoding {

	// /** Shift_JIS��\����`�ς݃I�u�W�F�N�g */
	// public static final Encoding sjis = getEncoding("Shift_JIS");
	// /** EUC_JP��\����`�ς݃I�u�W�F�N�g */
	// public static final Encoding eucjp = getEncoding("EUC_JP");

	/** �����G���R�[�f�B���O�̕����\�� */
	private final String encoding;

	/**
	 * private�R���X�g���N�^
	 * 
	 * @param enc �G���R�[�f�B���O�̕����\��
	 */
	private Encoding(String enc) {
		this.encoding = enc;
	}

	/**
	 * Encoding�I�u�W�F�N�g���擾����B
	 * 
	 * @param enc
	 * @return Encoding�I�u�W�F�N�g
	 * @exception IllegalArgumentException <code>enc</code>���T�|�[�g����Ȃ������R�[�h�̎�
	 */
	public static Encoding getEncoding(String enc) {
		try {
			// enc���T�|�[�g����Ȃ��ꍇ��UnsupportedEncodingException����������B
			new String(new byte[0], enc);
			return new Encoding(enc);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Unicode��������擾����B
	 * 
	 * @param s
	 * @return Unicode������
	 */
	public String getString(String s) {
		if (s == null) return null;
		try {
			return new String(getRawBytes(s), this.encoding);
		} catch (UnsupportedEncodingException e) {
			// ��΂ɋN����Ȃ��͂��̗�O
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}

	/**
	 * <code>s</code>��iso-8859-1 ������Ƃ݂Ȃ��āAbyte�z��ɕϊ�����B
	 * <p>
	 * �����ASCII�Ƃ��ĕ����񂵂č\�z���ꂽString�����̃o�C�g��ɖ߂����ɗ��p����B
	 * </p>
	 * 
	 * @param s �ϊ����镶����
	 * @return �ϊ���o�C�i��
	 */
	public static byte[] getRawBytes(String s) {
		if (s == null) {
			return null;
		}
		try {
			return s.getBytes("MS932");
			// return s.getBytes("iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			// ��΂ɋN����Ȃ��͂��̗�O
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
}
