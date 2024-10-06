package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���O�C�����
 * 
 * @author AIS
 */
public class TLoginInfo {

	/** ���O�C����� */
	protected static Company company = null;

	/** ���O�C�����[�U�[ */
	protected static User user = null;

	/** �����d��}�b�v */
	protected static Map<Integer, AutoJornalAccount> taxAutoJournalMap = null;

	/**
	 * ���O�C�����
	 * 
	 * @return ���O�C�����
	 */
	public static Company getCompany() {
		return company;
	}

	/**
	 * ���O�C�����
	 * 
	 * @param company ���O�C�����
	 */
	public static void setCompany(Company company) {
		TLoginInfo.company = company;
	}

	/**
	 * ���O�C�����[�U�[
	 * 
	 * @return ���O�C�����[�U�[
	 */
	public static User getUser() {
		return user;
	}

	/**
	 * ���O�C�����[�U�[
	 * 
	 * @param user ���O�C�����[�U�[
	 */
	public static void setUser(User user) {
		TLoginInfo.user = user;
	}

	/**
	 * �^�C�g���F
	 * 
	 * @return �^�C�g���F
	 */
	public static LookAndFeelColor getTitleColor() {
		return LookAndFeelColor.get(user.getLfColorType());
	}

	/**
	 * �����d��}�b�v�̎擾
	 * 
	 * @return autoJournalMap �����d��}�b�v
	 */
	public static Map<Integer, AutoJornalAccount> getAutoJournalMap() {
		return taxAutoJournalMap;
	}

	/**
	 * �����d��}�b�v�̐ݒ�
	 * 
	 * @param map �����d��}�b�v
	 */
	public static void setAutoJournalMap(Map<Integer, AutoJornalAccount> map) {
		taxAutoJournalMap = map;
	}

	/**
	 * �����d��̎擾
	 * 
	 * @param kbn
	 * @return �����d��
	 */
	public static AutoJornalAccount getAutoJornalAccount(int kbn) {
		return taxAutoJournalMap.get(kbn);
	}

	/**
	 * @param itemCode
	 * @param subItemCode
	 * @param detailItemCode
	 * @return true:����Ŏ����d��̉Ȗ�
	 */
	public static boolean isTaxAutoItem(String itemCode, String subItemCode, String detailItemCode) {
		if (taxAutoJournalMap == null || taxAutoJournalMap.isEmpty()) {
			return false;
		}
		for (AutoJornalAccount a : taxAutoJournalMap.values()) {
			if (Util.equals(itemCode, a.getItemCode()) //
				&& Util.equals(subItemCode, a.getSubItemCode()) //
				&& Util.equals(detailItemCode, a.getDetailItemCode()) //
			) {
				return true;
			}
		}
		return false;
	}
}
