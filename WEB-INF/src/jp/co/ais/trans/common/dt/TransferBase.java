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
 * 転送するデータ(Entity、Dto)の基底クラス.<br>
 * 継承することにより、以下のメソッドを自動で実現します。<br>
 * <ul>
 * <li>文字列表現: toString()
 * <li>リスト変換: toStringArray()
 * <li>リスト反映: reflectArray()
 * </ul>
 * <br>
 * ただし、リスト変換←→リスト反映のルールとして、<br>
 * 以下のオブジェクト型を推奨するものとし、それに該当しない場合は<br>
 * Orverrideして独自で実装を行ってください。<br>
 * <ul>
 * <li>String
 * <li>int/Integer
 * <li>boolean/Boolean
 * <li>java.math.BigDecimal
 * <li>java.util.Date
 * </ul>
 * なお、staticやfinalの変数は対象からは省かれます。<br>
 * ex) public static final String TABLE = "USR_MST";<br>
 * <br>
 * また、transient属性の変数もリスト変換から省かれます。<br>
 * ※toString()には含まれます
 */
public class TransferBase implements TInterfaceHasToObjectArray, Serializable {

	/** 定義されたクラス */
	protected Class[] classList = new Class[] { getClass() };

	/**
	 * 文字列表現
	 */
	@Override
	public String toString() {
		// デバッグモード以外の場合も処理時間が掛かりすぎ、ほぼ使っていないデバッグ情報のため、はずす
		if (classList.length > 0) {
			return classList[0].getName();
		} else {
			return "...";
		}
	}

	/**
	 * オブジェクトリスト.<br>
	 * 値がnullの場合は、null文字列表現としてリストへ挿入する.
	 * 
	 * @return リスト
	 */
	public List<String> toStringArray() {

		try {
			List<String> list = new LinkedList<String>();

			for (Class clazz : classList) {
				Field[] fields = clazz.getDeclaredFields();

				// 全フィールドの値をリストへ格納する
				for (Field field : fields) {
					field.setAccessible(true);

					int modif = field.getModifiers();
					if (Modifier.isStatic(modif) || Modifier.isFinal(modif) || Modifier.isTransient(modif)) {
						continue;
					}

					// nullのケース
					if (field.get(this) == null) {
						list.add(Util.safeNull(null));
						continue;
					}

					// タイプ別に値を文字列化
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
	 * ObjectArrayから状態を構築する<br>
	 * (toObjectArray()でリスト状態にしたものを元に戻す)<br>
	 * リスト内容とEntityのプロパティ内容は合っているものとして取り扱うので、<br>
	 * チェックはしない。実行時にエラーの出ないように注意すること。
	 * 
	 * @param list リスト
	 */
	public void reflectArray(List<String> list) {

		try {
			// リスト内データをフィールドへ反映する
			int index = 0;
			for (Class clazz : classList) {

				for (Field field : clazz.getDeclaredFields()) {
					String value = list.get(index);

					field.setAccessible(true);

					int modif = field.getModifiers();
					if (Modifier.isStatic(modif) || Modifier.isFinal(modif) || Modifier.isTransient(modif)) {
						continue;
					}

					// nullのケース
					if (value == null || Util.isNullString(value)) {
						field.set(this, null);
						index++;
						continue;
					}

					// タイプ別に値を変換、設定
					Class type = field.getType();

					// String
					if (String.class.equals(type)) {
						field.set(this, value);
						index++;
						continue;
					}

					// Stringじゃなくてブランクの場合はnull
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
						// 推奨されていないタイプを使用
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
	 * 1次開発保管用. <br>
	 * 利用する場合はOrverrideすること。
	 * 
	 * @return Object配列
	 */
	public List toObjectArray() {
		return toStringArray();
	}

}
