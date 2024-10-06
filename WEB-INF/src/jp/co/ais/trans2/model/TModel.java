package jp.co.ais.trans2.model;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * �r�W�l�X���W�b�N��ʃN���X
 */
public class TModel {

	/** ���ݎ��� */
	private Date now;

	/** ��Џ�� */
	private Company company;

	/** ���[�U��� */
	private User user;

	/** �v���O�����R�[�h */
	private String programCode;

	/** �T�[�o�[���A�v�����ʎq���� */
	private String serverInstanceName = null;

	/** ��ʎ��ʎq */
	private String realUID = null;

	/** ���O�p�ǉ���� */
	private String realInfo = null;

	/**
	 * ��Џ��
	 * 
	 * @param company ��Џ��
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * ���[�U���
	 * 
	 * @param user ���[�U���
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * �v���O�����R�[�h�Z�b�g
	 * 
	 * @param code �R�[�h
	 */
	public void setProgramCode(String code) {
		this.programCode = code;
	}

	/**
	 * ��Џ��
	 * 
	 * @return ��Џ��
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * ���[�U���
	 * 
	 * @return ���[�U���
	 */
	public User getUser() {
		return user;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		if (company == null) {
			return null;
		}
		return company.getCode();
	}

	/**
	 * ���[�U�R�[�h
	 * 
	 * @return ���[�U�R�[�h
	 */
	public String getUserCode() {
		if (user == null) {
			return null;
		}
		return user.getCode();
	}

	/**
	 * �v���O�����R�[�h
	 * 
	 * @return �v���O�����R�[�h
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * ���ݎ���
	 * 
	 * @return now ���ݎ���
	 */
	public Date getNow() {
		return now;
	}

	/**
	 * ���ݓ�(������00:00)
	 * 
	 * @return now ���ݓ�
	 */
	public Date getNowYMD() {
		return DateUtil.toYMDDate(now);
	}

	/**
	 * ���ݎ���
	 * 
	 * @param now ���ݎ���
	 */
	public void setNow(Date now) {
		this.now = now;
	}

	/**
	 * �R���|�[�l���g�擾.<br>
	 * TModel�̌p���N���X�̏ꍇ�́A���O�C�����������p��
	 * 
	 * @param clazz �L�[
	 * @return �R���|�[�l���g
	 */
	public <T> T get(Class<T> clazz) {
		Object ret = TContainerFactory.getContainer().getComponent(clazz);

		if (ret instanceof TModel) {
			TModel model = (TModel) ret;
			model.setCompany(company);
			model.setUser(user);
			model.setProgramCode(programCode);
			model.setNow(now);

			model.setRealUID(realUID);
			model.setRealInfo(realInfo);
			model.setServerInstanceName(serverInstanceName);
		}

		return (T) ret;
	}

	/**
	 * �R���|�[�l���g�擾.<br>
	 * TModel�̌p���N���X�̏ꍇ�́A���O�C�����������p��
	 * 
	 * @param key �L�[
	 * @return �R���|�[�l���g
	 */
	public Object getComponent(Object key) {
		Object ret = TContainerFactory.getContainer().getComponent(key);

		if (ret instanceof TModel) {
			TModel model = (TModel) ret;
			model.setCompany(company);
			model.setUser(user);
			model.setProgramCode(programCode);
			model.setNow(now);

			model.setRealUID(realUID);
			model.setRealInfo(realInfo);
			model.setServerInstanceName(serverInstanceName);
		}

		return ret;
	}

	/**
	 * ID���烁�b�Z�[�W�擾
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds �P��ID�A�܂��́A�P��̃��X�g
	 * @return ���b�Z�[�W
	 */
	protected String getMessage(String messageID, Object... bindIds) {
		if (user != null) {
			String lang = user.getLanguage();
			return MessageUtil.convertMessage(lang, messageID, bindIds);
		}

		return messageID;
	}

	/**
	 * ID����P��擾
	 * 
	 * @param wordID �P��ID
	 * @return �P��
	 */
	protected String getWord(String wordID) {
		if (user != null) {
			String lang = user.getLanguage();
			return MessageUtil.getWord(lang, wordID);
		}

		return wordID;
	}

	/**
	 * ���b�Z�[�W(����)�擾
	 * 
	 * @param messageId
	 * @return ���b�Z�[�W(����)
	 */
	public String getShortWord(String messageId) {
		String lang = user.getLanguage();
		return MessageUtil.getShortWord(lang, messageId);

	}

	/**
	 * �T�[�o�[���A�v�����ʎq���̂̎擾
	 * 
	 * @return serverInstanceName �T�[�o�[���A�v�����ʎq����
	 */
	public String getServerInstanceName() {
		return serverInstanceName;
	}

	/**
	 * �T�[�o�[���A�v�����ʎq���̂̐ݒ�
	 * 
	 * @param serverInstanceName �T�[�o�[���A�v�����ʎq����
	 */
	public void setServerInstanceName(String serverInstanceName) {
		this.serverInstanceName = serverInstanceName;
	}

	/**
	 * ��ʎ��ʎq�̎擾
	 * 
	 * @return realUID ��ʎ��ʎq
	 */
	public String getRealUID() {
		return realUID;
	}

	/**
	 * ��ʎ��ʎq�̐ݒ�
	 * 
	 * @param realUID ��ʎ��ʎq
	 */
	public void setRealUID(String realUID) {
		this.realUID = realUID;
	}

	/**
	 * ���O�p�ǉ����̎擾
	 * 
	 * @return realInfo ���O�p�ǉ����
	 */
	public String getRealInfo() {
		return realInfo;
	}

	/**
	 * ���O�p�ǉ����̐ݒ�
	 * 
	 * @param realInfo ���O�p�ǉ����
	 */
	public void setRealInfo(String realInfo) {
		this.realInfo = realInfo;
	}
}
