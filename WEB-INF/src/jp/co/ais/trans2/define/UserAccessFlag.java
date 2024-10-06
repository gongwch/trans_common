package jp.co.ais.trans2.define;

import jp.co.ais.trans2.model.user.*;

/**
 * ユーザアクセス権限フラグ
 */
public enum UserAccessFlag {

	/** なし */
	NONE(0),

	/** 参照のみ */
	VIEWER(1),

	/** 処理可能 */
	EDITOR(2),

	/** 全て */
	ALL(9);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	UserAccessFlag(int value) {
		this.value = value;
	}

	/**
	 * 指定indexのアクセス権限を返す
	 * 
	 * @param user
	 * @param index
	 * @return 指定indexのアクセス権限
	 */
	public static UserAccessFlag get(User user, int index) {
		if (user == null) {
			return NONE;
		}

		if (index < 0 || index >= User.ACCESS_FLAG_COUNT) {
			return NONE;
		}

		if (index == 0) return get(user.getAccessPermissionFlag1());
		if (index == 1) return get(user.getAccessPermissionFlag2());
		if (index == 2) return get(user.getAccessPermissionFlag3());
		if (index == 3) return get(user.getAccessPermissionFlag4());
		if (index == 4) return get(user.getAccessPermissionFlag5());
		if (index == 5) return get(user.getAccessPermissionFlag6());
		if (index == 6) return get(user.getAccessPermissionFlag7());
		if (index == 7) return get(user.getAccessPermissionFlag8());
		if (index == 8) return get(user.getAccessPermissionFlag9());
		if (index == 9) return get(user.getAccessPermissionFlag10());

		return NONE;
	}

	/**
	 * 指定アクセス権限の値より大きいかどうか
	 * 
	 * @param user
	 * @param index
	 * @param permission
	 * @return true:指定アクセス権限の値より大きい権限である
	 */
	public static boolean hasPermission(User user, int index, UserAccessFlag permission) {

		UserAccessFlag e = get(user, index);
		UserAccessFlag p = permission;
		if (p == null) {
			p = NONE;
		}

		if (e != null) {
			return e.value >= p.value;
		}

		return false;
	}

	/**
	 * アクセス権限を返す
	 * 
	 * @param value 値
	 * @return アクセス権限
	 */
	public static UserAccessFlag get(int value) {
		for (UserAccessFlag em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return NONE;
	}

	/**
	 * アクセス権限名称を返す
	 * 
	 * @return アクセス権限名称を返す
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * アクセス権限名称を返す
	 * 
	 * @param userAccountRole アクセス権限
	 * @return アクセス権限名称を返す
	 */
	public static String getName(UserAccessFlag userAccountRole) {

		if (userAccountRole == null) {
			return null;
		}

		switch (userAccountRole) {
			case NONE:
				return "COP966"; // なし
			case VIEWER:
				return "COP1094"; // 参照者
			case EDITOR:
				return "COP1099"; // 処理者
			case ALL:
				return "COP1095"; // 全て

			default:
				return null;

		}
	}

}
