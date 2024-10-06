package jp.co.ais.trans2.define;

import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�A�N�Z�X�����t���O
 */
public enum UserAccessFlag {

	/** �Ȃ� */
	NONE(0),

	/** �Q�Ƃ̂� */
	VIEWER(1),

	/** �����\ */
	EDITOR(2),

	/** �S�� */
	ALL(9);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	UserAccessFlag(int value) {
		this.value = value;
	}

	/**
	 * �w��index�̃A�N�Z�X������Ԃ�
	 * 
	 * @param user
	 * @param index
	 * @return �w��index�̃A�N�Z�X����
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
	 * �w��A�N�Z�X�����̒l���傫�����ǂ���
	 * 
	 * @param user
	 * @param index
	 * @param permission
	 * @return true:�w��A�N�Z�X�����̒l���傫�������ł���
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
	 * �A�N�Z�X������Ԃ�
	 * 
	 * @param value �l
	 * @return �A�N�Z�X����
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
	 * �A�N�Z�X�������̂�Ԃ�
	 * 
	 * @return �A�N�Z�X�������̂�Ԃ�
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * �A�N�Z�X�������̂�Ԃ�
	 * 
	 * @param userAccountRole �A�N�Z�X����
	 * @return �A�N�Z�X�������̂�Ԃ�
	 */
	public static String getName(UserAccessFlag userAccountRole) {

		if (userAccountRole == null) {
			return null;
		}

		switch (userAccountRole) {
			case NONE:
				return "COP966"; // �Ȃ�
			case VIEWER:
				return "COP1094"; // �Q�Ǝ�
			case EDITOR:
				return "COP1099"; // ������
			case ALL:
				return "COP1095"; // �S��

			default:
				return null;

		}
	}

}
