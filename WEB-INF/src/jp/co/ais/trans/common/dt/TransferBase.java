package jp.co.ais.trans.common.dt;

import java.io.*;
import java.lang.reflect.*;
import java.math.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;

/**
 * �]������f�[�^(Entity�ADto)�̊��N���X.<br>
 * �p�����邱�Ƃɂ��A�ȉ��̃��\�b�h�������Ŏ������܂��B<br>
 * <ul>
 * <li>������\��: toString()
 * <li>���X�g�ϊ�: toStringArray()
 * <li>���X�g���f: reflectArray()
 * </ul>
 * <br>
 * �������A���X�g�ϊ��������X�g���f�̃��[���Ƃ��āA<br>
 * �ȉ��̃I�u�W�F�N�g�^�𐄏�������̂Ƃ��A����ɊY�����Ȃ��ꍇ��<br>
 * Orverride���ēƎ��Ŏ������s���Ă��������B<br>
 * <ul>
 * <li>String
 * <li>int/Integer
 * <li>boolean/Boolean
 * <li>java.math.BigDecimal
 * <li>java.util.Date
 * </ul>
 * �Ȃ��Astatic��final�̕ϐ��͑Ώۂ���͏Ȃ���܂��B<br>
 * ex) public static final String TABLE = "USR_MST";<br>
 * <br>
 * �܂��Atransient�����̕ϐ������X�g�ϊ�����Ȃ���܂��B<br>
 * ��toString()�ɂ͊܂܂�܂�
 */
public class TransferBase implements TInterfaceHasToObjectArray, Serializable {

	/** ��`���ꂽ�N���X */
	protected Class[] classList = new Class[] { getClass() };

	/**
	 * ������\��
	 */
	@Override
	public String toString() {
		// �f�o�b�O���[�h�ȊO�̏ꍇ���������Ԃ��|���肷���A�قڎg���Ă��Ȃ��f�o�b�O���̂��߁A�͂���
		if (classList.length > 0) {
			return classList[0].getName();
		} else {
			return "...";
		}
	}

	/**
	 * �I�u�W�F�N�g���X�g.<br>
	 * �l��null�̏ꍇ�́Anull������\���Ƃ��ă��X�g�֑}������.
	 * 
	 * @return ���X�g
	 */
	public List<String> toStringArray() {

		try {
			List<String> list = new LinkedList<String>();

			for (Class clazz : classList) {
				Field[] fields = clazz.getDeclaredFields();

				// �S�t�B�[���h�̒l�����X�g�֊i�[����
				for (Field field : fields) {
					field.setAccessible(true);

					int modif = field.getModifiers();
					if (Modifier.isStatic(modif) || Modifier.isFinal(modif) || Modifier.isTransient(modif)) {
						continue;
					}

					// null�̃P�[�X
					if (field.get(this) == null) {
						list.add(Util.safeNull(null));
						continue;
					}

					// �^�C�v�ʂɒl�𕶎���
					Class type = field.getType();

					String value;
					if (String.class.equals(type)) {
						value = (String) field.get(this);

					} else if (double.class.equals(type)) {
						value = DecimalUtil.doubleToString(field.getDouble(this));

					} else if (int.class.equals(type)) {
						value = String.valueOf(field.getInt(this));

					} else if (BigDecimal.class.equals(type)) {
						value = ((BigDecimal) field.get(this)).toPlainString();

					} else if (Date.class.equals(type)) {
						value = DateUtil.toYMDHMSString((Date) field.get(this));

					} else if (boolean.class.equals(type)) {
						value = BooleanUtil.toString(field.getBoolean(this));

					} else {
						value = Util.avoidNullNT(field.get(this));
					}

					list.add(value);
				}
			}

			return list;

		} catch (IllegalArgumentException ex) {
			throw new TRuntimeException(ex, "E00009");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException(ex, "E00009");
		}
	}

	/**
	 * ObjectArray�����Ԃ��\�z����<br>
	 * (toObjectArray()�Ń��X�g��Ԃɂ������̂����ɖ߂�)<br>
	 * ���X�g���e��Entity�̃v���p�e�B���e�͍����Ă�����̂Ƃ��Ď�舵���̂ŁA<br>
	 * �`�F�b�N�͂��Ȃ��B���s���ɃG���[�̏o�Ȃ��悤�ɒ��ӂ��邱�ƁB
	 * 
	 * @param list ���X�g
	 */
	public void reflectArray(List<String> list) {

		try {
			// ���X�g���f�[�^���t�B�[���h�֔��f����
			int index = 0;
			for (Class clazz : classList) {

				for (Field field : clazz.getDeclaredFields()) {
					String value = list.get(index);

					field.setAccessible(true);

					int modif = field.getModifiers();
					if (Modifier.isStatic(modif) || Modifier.isFinal(modif) || Modifier.isTransient(modif)) {
						continue;
					}

					// null�̃P�[�X
					if (value == null || Util.isNullString(value)) {
						field.set(this, null);
						index++;
						continue;
					}

					// �^�C�v�ʂɒl��ϊ��A�ݒ�
					Class type = field.getType();

					// String
					if (String.class.equals(type)) {
						field.set(this, value);
						index++;
						continue;
					}

					// String����Ȃ��ău�����N�̏ꍇ��null
					if (Util.isNullOrEmpty(value)) {
						field.set(this, null);
						index++;
						continue;
					}

					if (int.class.equals(type)) {
						field.setInt(this, Integer.parseInt(value));

					} else if (Integer.class.equals(type)) {
						field.set(this, new Integer(value));

					} else if (BigDecimal.class.equals(type)) {
						field.set(this, new BigDecimal(value));

					} else if (Date.class.equals(type)) {
						field.set(this, DateUtil.toYMDHMSDate(value));

					} else if (boolean.class.equals(type)) {
						field.setBoolean(this, BooleanUtil.toBoolean(value));

					} else if (Boolean.class.equals(type)) {
						field.set(this, new Boolean(value));

					} else {
						// ��������Ă��Ȃ��^�C�v���g�p
						ClientLogger.error("The type not recommended to this is included.: " + getClass().getName()
							+ "#" + field.getName() + ", " + type.getName());

						field.set(this, value);
					}

					index++;
				}
			}

		} catch (IllegalArgumentException ex) {
			throw new TRuntimeException(ex, "E00009");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException(ex, "E00009");
		} catch (ParseException ex) {
			throw new TRuntimeException(ex, "E00009");
		}
	}

	/**
	 * 1���J���ۊǗp. <br>
	 * ���p����ꍇ��Orverride���邱�ƁB
	 * 
	 * @return Object�z��
	 */
	public List toObjectArray() {
		return toStringArray();
	}

}
