package jp.co.ais.trans.common.client.util;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���ʏ��擾�R���g���[��
 */
public class InformationUtil extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** �C���X�^���X.Static�A�N�Z�X�̈� */
	private static InformationUtil instance = new InformationUtil();

	/** �g�D��� */
	private OrganizationInfo orgInfo = new OrganizationInfo();

	/** �Ȗڏ�� */
	private ItemInfo itemInfo = new ItemInfo();

	/** �⏕�Ȗڏ�� */
	private SubItemInfo subItemInfo = new SubItemInfo();

	/** ����Ȗڏ�� */
	private BreakDownItemInfo downItemInfo = new BreakDownItemInfo();

	/**
	 * �g�D�R�[�h���X�g�擾
	 * 
	 * @return �g�D�R�[�h���X�g
	 * @throws TException
	 */
	public static String[] getOrganizationCodeList() throws TException {
		return instance.getOrganizationCodeListNative();
	}

	/**
	 * �g�D�R�[�h���X�g�擾 ����
	 * 
	 * @return �g�D�R�[�h���X�g
	 * @throws TException
	 */
	private String[] getOrganizationCodeListNative() throws TException {
		try {
			addSendValues("FLAG", "OrganizationCode");

			// ���M
			if (!request(TARGET_SERVLET)) {
				throw new TException(getErrorMessage());
			}

			// �g�D���ނ�ݒ肷��
			return StringUtil.toArrayFromDelimitString(getResult().get("orgCodes"));
		} catch (IOException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * �g�D���擾
	 * 
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 * @param orgCode �g�D�R�[�h
	 * @return �g�D���
	 * @throws TException
	 */
	public static OrganizationInfo getOrganizationInfo(String kmtCode, String orgCode) throws TException {
		return instance.getOrganizationInfoNative(kmtCode, orgCode);
	}

	/**
	 * �g�D���擾
	 * 
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 * @param orgCode �g�D�R�[�h
	 * @return �g�D���
	 * @throws TException
	 */
	private OrganizationInfo getOrganizationInfoNative(String kmtCode, String orgCode) throws TException {

		try {
			addSendValues("FLAG", "OrganizationInfo"); // �����敪
			addSendValues("KAI_CODE", getLoginUserCompanyCode()); // ��ЃR�[�h
			addSendValues("USR_ID", getLoginUserID()); // ���[�U�R�[�h
			addSendValues("KMT_CODE", kmtCode); // �Ȗڑ̌n�R�[�h
			addSendValues("ORGANIZATION_CODE", orgCode); // �g�D�R�[�h

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				throw new TException(getErrorMessage());
			}

			// �l�̃Z�b�g
			orgInfo.set(getResult());

			return orgInfo;
		} catch (IOException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * �Ȗڏ��擾
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @return �Ȗڏ��
	 * @throws TException
	 */
	public static ItemInfo getItemInfo(String kmkCode) throws TException {
		return instance.getItemInfoNative(kmkCode, "", "");
	}

	/**
	 * �⏕�Ȗڏ��擾
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @return �⏕�Ȗڏ��
	 * @throws TException
	 */
	public static SubItemInfo getItemInfo(String kmkCode, String hkmCode) throws TException {
		return (SubItemInfo) instance.getItemInfoNative(kmkCode, hkmCode, "");
	}

	/**
	 * ����Ȗڏ��擾
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return ����Ȗڏ��
	 * @throws TException
	 */
	public static BreakDownItemInfo getItemInfo(String kmkCode, String hkmCode, String ukmCode) throws TException {
		return (BreakDownItemInfo) instance.getItemInfoNative(kmkCode, hkmCode, ukmCode);
	}

	/**
	 * �Ȗڏ��擾
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return �Ȗڏ��
	 * @throws TException
	 */
	private ItemInfo getItemInfoNative(String kmkCode, String hkmCode, String ukmCode) throws TException {

		try {
			addSendValues("FLAG", "ItemInfo"); // �����敪
			addSendValues("KAI_CODE", getLoginUserCompanyCode()); // ��ЃR�[�h
			addSendValues("KMK_CODE", kmkCode); // �ȖڃR�[�h
			addSendValues("HKM_CODE", hkmCode); // �⏕�ȖڃR�[�h
			addSendValues("UKM_CODE", ukmCode); // ����ȖڃR�[�h

			// �w�肳�ꂽ�������u�����N���ǂ����ŉȖڎ�ނ𔻒f
			ItemInfo item;
			if (Util.isNullOrEmpty(hkmCode)) {
				// �Ȗڎ��(�Ȗ�)
				addSendValues("ITEM_KIND", "Item");
				item = itemInfo;

			} else if (Util.isNullOrEmpty(ukmCode)) {
				// �Ȗڎ��(�⏕�Ȗ�)
				addSendValues("ITEM_KIND", "SubItem");
				item = subItemInfo;

			} else {
				// �Ȗڎ��(����Ȗ�)
				addSendValues("ITEM_KIND", "BreakDownItem");
				item = downItemInfo;
			}

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				throw new TException(getErrorMessage());
			}

			Map result = getResult();
			if (result.isEmpty()) {
				throw new TException("W00100", kmkCode, hkmCode, ukmCode);
			}

			item.set(result);
			return item;

		} catch (IOException e) {
			throw new TException(e, "E00009");
		}

	}
}
