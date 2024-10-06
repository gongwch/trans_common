package jp.co.ais.trans.common.util;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.*;

/**
 * Trans�̃��[�e�B���e�B�N���X�ł��B
 * 
 * @author AIS
 */
public class TransUtil {

	/**
	 * �ؕ�
	 */
	public final static int DEBIT = 0;

	/**
	 * �ݕ�
	 */
	public final static int CREDIT = 1;

	/**
	 * �W�v�O���[�v
	 * 
	 * @author AIS
	 */
	public enum SumGroup {
		/** �W�v�Ȃ� */
		None,
		/** ���� */
		Bmn,
		/** ����� */
		Tri,
		/** �����(�W�v) */
		TriSum,
		/** �����(�A��) */
		TriCon,
		/** �Ј� */
		Emp,
		/** �Ǘ�1 */
		Knr1,
		/** �Ǘ�2 */
		Knr2,
		/** �Ǘ�3 */
		Knr3,
		/** �Ǘ�4 */
		Knr4,
		/** �Ǘ�5 */
		Knr5,
		/** �Ǘ�6 */
		Knr6
	}

	/** �Ȗڃ��x�� */
	public enum ItemLevel {
		/** �Ȗ� */
		Item,
		/** �⏕�Ȗ� */
		SubItem,
		/** ����Ȗ� */
		DetailItem
	}

	/**
	 * ��{�Ȗڑ̌n�R�[�h
	 */
	public final static String DEFAULT_ITEM_ORGANIZATION_CODE = "00";

	/**
	 * �X�V�敪�F�o�^
	 */
	public final static int UPD_KBN_ENTRY = 1;

	/**
	 * �X�V�敪�F���ꏳ�F
	 */
	public final static int UPD_KBN_GENBA_APPROVE = 2;

	/**
	 * �X�V�敪�F���F
	 */
	public final static int UPD_KBN_APPROVE = 3;

	/**
	 * �X�V�敪�F�X�V
	 */
	public final static int UPD_KBN_UPDATE = 4;

	/**
	 * �X�V�敪�F����۔F
	 */
	public final static int UPD_KBN_GENBA_DENY = 11;

	/**
	 * �X�V�敪�F�۔F
	 */
	public final static int UPD_KBN_DENY = 12;

	/**
	 * �X�V�敪�F�o�^
	 */
	public final static String UPD_KBN_ENTRY_CHAR = "C01258";

	/**
	 * �X�V�敪�F���ꏳ�F
	 */
	public final static String UPD_KBN_GENBA_APPROVE_CHAR = "C00157";

	/**
	 * �X�V�敪�F���F
	 */
	public final static String UPD_KBN_APPROVE_CHAR = "C01168";

	/**
	 * �X�V�敪�F�X�V
	 */
	public final static String UPD_KBN_UPDATE_CHAR = "C00169";

	/**
	 * �X�V�敪�F����۔F
	 */
	public final static String UPD_KBN_GENBA_DENY_CHAR = "C01617";

	/**
	 * �X�V�敪�F�۔F
	 */
	public final static String UPD_KBN_DENY_CHAR = "C00447";

	/**
	 * �X�V�敪(int)�ɕR�t��������Ԃ��܂��B
	 * 
	 * @param updKbn �X�V�敪
	 * @return �X�V�敪(int)�ɕR�t������
	 */
	public final static String getUpdKbnChar(int updKbn) {

		if (UPD_KBN_ENTRY == updKbn) {
			return UPD_KBN_ENTRY_CHAR;
		} else if (UPD_KBN_GENBA_APPROVE == updKbn) {
			return UPD_KBN_GENBA_APPROVE_CHAR;
		} else if (UPD_KBN_APPROVE == updKbn) {
			return UPD_KBN_APPROVE_CHAR;
		} else if (UPD_KBN_UPDATE == updKbn) {
			return UPD_KBN_UPDATE_CHAR;
		} else if (UPD_KBN_GENBA_DENY == updKbn) {
			return UPD_KBN_GENBA_DENY_CHAR;
		} else if (UPD_KBN_DENY == updKbn) {
			return UPD_KBN_DENY_CHAR;
		}
		return null;
	}

	/**
	 * ����d���敪�F�ΏۊO
	 */
	public final static int US_KBN_NONE = 0;

	/**
	 * ����d���敪�F����
	 */
	public final static int US_KBN_URI = 1;

	/**
	 * ����d���敪�F�d��
	 */
	public final static int US_KBN_SIR = 2;

	/**
	 * ����ł̕���
	 * 
	 * @author AIS
	 */
	public enum ZeiKeiKbn {
		/** �ېŔ��� */
		UriKazei,
		/** �ƐŔ��� */
		UriMenzei,
		/** ��ېŔ��� */
		UriHikazei,
		/** �ېŎd�� */
		SirKazei,
		/** ��ېŎd�� */
		SirHikazei
	}

	/**
	 * ����ŕ��ނɕR�t��enum��Ԃ��܂��B
	 * 
	 * @param zeiKeiKbn ����ŕ���
	 * @return ����ŕ��ނɕR�t��enum
	 */
	public final static ZeiKeiKbn getZeiKeiKbn(String zeiKeiKbn) {

		if ("1".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.UriKazei;
		} else if ("2".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.UriMenzei;
		} else if ("3".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.UriHikazei;
		} else if ("4".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.SirKazei;
		} else if ("5".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.SirHikazei;
		}

		return null;
	}

	/**
	 * �e�[�u��ID��Ԃ�
	 * 
	 * @param model �T�[�o�x�[�X
	 * @param tableName
	 * @return �e�[�u��ID
	 */
	public static String getTableName(TModel model, String tableName) {
		if (model == null || model.getUser() == null) {
			return tableName;
		}

		String userLang = model.getUser().getLanguage();

		if (!Util.isNullOrEmpty(userLang)) {

			boolean isUseMultiLang = ServerConfig.isFlagOn("trans.use.multi.lang");
			if (!isUseMultiLang) {
				return tableName;
			}

			boolean hasLang = false;
			String[] langArr = ServerConfig.getProperties("trans.use.multi.lang.list");
			for (String lang : langArr) {
				if (Util.avoidNull(lang).equalsIgnoreCase(userLang)) {
					hasLang = true;
					break;
				}
			}

			if (!hasLang) {
				// ���ꂪ�ΏۊO
				return tableName;
			}

			boolean hasTable = false;
			String[] tableArr = ServerConfig.getProperties("trans.use.multi.lang.table.list");
			for (String tbl : tableArr) {
				if (Util.avoidNull(tbl).equalsIgnoreCase(tableName)) {
					hasTable = true;
					break;
				}
			}

			if (!hasTable) {
				// �e�[�u�����ΏۊO
				return tableName;
			}

			return (tableName + "_" + userLang).toUpperCase();
		}

		return tableName;
	}

	/**
	 * �e�[�u��ID��Ԃ�
	 * 
	 * @param ctrl �N���C�A���g�x�[�X
	 * @param tableName
	 * @return �e�[�u��ID
	 */
	public static String getTableName(TController ctrl, String tableName) {
		if (ctrl == null || ctrl.getUser() == null) {
			return tableName;
		}

		String userLang = ctrl.getUser().getLanguage();

		if (!Util.isNullOrEmpty(userLang)) {

			boolean isUseMultiLang = ClientConfig.isFlagOn("trans.use.multi.lang");
			if (!isUseMultiLang) {
				return tableName;
			}

			boolean hasLang = false;
			String[] langArr = ClientConfig.getProperties("trans.use.multi.lang.list");
			for (String lang : langArr) {
				if (Util.avoidNull(lang).equalsIgnoreCase(userLang)) {
					hasLang = true;
					break;
				}
			}

			if (!hasLang) {
				// ���ꂪ�ΏۊO
				return tableName;
			}

			boolean hasTable = false;
			String[] tableArr = ClientConfig.getProperties("trans.use.multi.lang.table.list");
			for (String tbl : tableArr) {
				if (Util.avoidNull(tbl).equalsIgnoreCase(tableName)) {
					hasTable = true;
					break;
				}
			}

			if (!hasTable) {
				// �e�[�u�����ΏۊO
				return tableName;
			}

			return (tableName + "_" + userLang).toUpperCase();
		}

		return tableName;
	}

}
