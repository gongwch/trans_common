package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�p�X���[�h�������W�b�N
 */
public class UserPasswordImpl implements UserPassword {

	/** ���[�U�[�}�X�^Dao */
	protected USR_MSTDao usrDao;

	/** ���[�U�F�؃}�X�^DAO */
	protected USR_AUTH_MSTDao usrAuthDao;

	/** �p�X���[�h�����}�X�^DAO */
	protected PWD_HST_TBLDao historyDao;

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���[�U�R�[�h */
	protected String userCode;

	/** ���[�U�f�[�^ */
	protected USR_MST usrMst;

	/** ���[�U�F�؃f�[�^ */
	protected USR_AUTH_MST usrAuthMst;

	/** �G���[���b�Z�[�W */
	protected String errorMsg;

	/** �����܂ł̎c���� */
	protected int termDays;

	/** �G���[���b�Z�[�W���� */
	protected List<Object> errorArgs = new LinkedList<Object>();

	/**
	 * @param usrDao
	 */
	public void setUsrDao(USR_MSTDao usrDao) {
		this.usrDao = usrDao;
	}

	/**
	 * @param passDao
	 */
	public void setHistoryDao(PWD_HST_TBLDao passDao) {
		this.historyDao = passDao;
	}

	/**
	 * @param usrAuthDao
	 */
	public void setUsrAuthDao(USR_AUTH_MSTDao usrAuthDao) {
		this.usrAuthDao = usrAuthDao;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#setCode(String, String)
	 */
	public void setCode(String compCode, String userCode) {
		this.companyCode = compCode;
		this.userCode = userCode;

		this.usrMst = usrDao.getUSR_MSTByKaicodeUsrcode(compCode, userCode);
		this.usrAuthMst = usrAuthDao.findByKaiCode(compCode);

		if (this.usrMst == null) {
			throw new TRuntimeException("E00009", "user not found." + compCode + ":" + userCode);
		}
	}

	/**
	 * �p�X���[�h�Ǘ����s�����ǂ���
	 * 
	 * @return true:�p�X���[�h�Ǘ�����
	 */
	public boolean isPasswordManaged() {
		return usrAuthMst != null;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#equalsNowPassword(String)
	 */
	public boolean equalsNowPassword(String password) {

		return Util.safeNull(password).equals(usrMst.getPASSWORD());
	}

	/**
	 * �p�X���[�h�L�����ԃ`�F�b�N
	 * 
	 * @return boolean true:�p�X���[�h�L�����Ԑ؂�
	 */
	public boolean isPasswordTermOrver() {

		// �F�؏��Ȃ� = ���؂Ȃ�
		if (usrAuthMst == null) {
			return false;
		}

		// �L�����Ԑݒ肪0���� ���؂Ȃ�
		if (usrAuthMst.getPWD_TERM() != 0) {

			// �X�V�����K��
			Date updDate = usrMst.getPWD_UPD_DATE();

			// �o�b�`�����Ƃ����ړ��͂ɂ���ăp�X���[�h�X�V���t�����݂��Ȃ��ƃp�X���[�h���X�V������B
			if (updDate == null) {
				return true;
			}

			// �X�V����̊���
			double interval = (Util.getCurrentDate().getTime()) - (updDate.getTime());

			// �X�V���Ԑݒ�
			double pwdTerm = (usrAuthMst.getPWD_TERM());

			// �X�V����̎��ԂƗL�����Ԃ��r����B���Ԃ��߂����ꍇTrue ��Ԃ��ăp�X���[�h��ʂɁB
			if ((interval / 86400000) > pwdTerm) {

				return true;
			}
		}

		return false;
	}

	/**
	 * �p�X���[�h�L�������؂�ʒm�����`�F�b�N
	 * 
	 * @return boolean true: �p�X���[�h�L�������؂�̒ʒm������
	 */

	public boolean isPwdLimitMsgNotice() {
		// �F�؏��Ȃ� = ���؂Ȃ�
		if (usrAuthMst == null) {
			return true;
		}

		if (usrAuthMst.getPWD_TERM() != 0) {

			// �p�X���[�h�L�������؂�ʒm�����ݒ肪0���� ���؂Ȃ�
			if (usrAuthMst.getPWD_EXP_BEFORE_DAYS() != 0) {
				// �p�X���[�h�X�V���t
				Date updDate = usrMst.getPWD_UPD_DATE();

				// �p�X���[�h�����؂�ʒm�������擾
				int pwdLimitMsg = (usrAuthMst.getPWD_EXP_BEFORE_DAYS());

				// �p�X���[�h�L������
				int pwdTerm = (usrAuthMst.getPWD_TERM());

				// �L�����Ԃƒʒm���Ԃ̍���
				int totalTerm = pwdTerm - pwdLimitMsg;

				// ���ݓ��ƃp�X���[�h�X�V���̍���
				int term = DateUtil.getDayCount(updDate, Util.getCurrentDate());

				// �����܂ł̎c���� �� �p�X���[�h�̗L������ �| �V�X�e�����t
				termDays = DateUtil.getDayCount(Util.getCurrentDate(), DateUtil.addDay(updDate, pwdTerm));

				// �L�����Ԃƒʒm���Ԃ̍��������ݓ��ƃp�X���[�h�X�V���̍�����菬�����ꍇ�i�ʒm���Ԃ��߂��Ă���j
				if (term >= totalTerm) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#assertPassword(java.lang.String)
	 */
	public boolean assertPassword(String password) {
		errorArgs.clear();

		// �����`�F�b�N
		if (!assertLength(password)) {
			errorMsg = "I00027";
			errorArgs.add(usrAuthMst.getPWD_MIN_LENGTH());
			return false;
		}

		// ���G���x���`�F�b�N
		if (!assertComplicateLevel(password)) {
			errorMsg = "I00028";
			errorArgs.add(usrAuthMst.getCOMPLICATE_LVL());
			return false;
		}

		// �����`�F�b�N
		if (!containtsHistory(password)) {
			errorMsg = "I00029";
			return false;
		}

		return true;
	}

	/**
	 * �������`�F�b�N.<br>
	 * 
	 * @return boolean true:�������K��𖞂���
	 */
	public boolean assertLength(String password) {

		// ��Аݒ�Ȃ� = ���؂Ȃ�
		if (usrAuthMst == null) {
			return true;
		}

		// �����ݒ肪�O�̎��͌��؂Ȃ�
		if (usrAuthMst.getPWD_MIN_LENGTH() != 0) {

			// �����̊��}��
			if (password.length() < usrAuthMst.getPWD_MIN_LENGTH()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * ���G���x���`�F�b�N
	 * 
	 * @return boolean
	 */
	public boolean assertComplicateLevel(String password) {

		// ��Аݒ�Ȃ� �� ���؂Ȃ�
		if (usrAuthMst == null) {
			return true;
		}

		// ���ꂼ��̕������A�X�L�R�[�h�ŕ��ʁAcomplicate���X�g�ɂ��̋敪�������B
		Set<Integer> complicateList = new HashSet<Integer>();

		// �e�����𔻒�pint�z��ɕς���B
		for (char schar : password.toCharArray()) {
			String str = Character.toString(schar);

			if (str.matches("[A-Z]")) {
				complicateList.add(1); // �啶��

			} else if (str.matches("[a-z]")) {
				complicateList.add(2); // ������

			} else if (str.matches("[0-9]")) {
				complicateList.add(3); // ����

			} else if (str.matches("[!-~]")) {
				complicateList.add(4); // �L��
			}
		}

		// ���G���x�����ݒ���Ⴂ�ꍇ�G���[���o���B
		if (complicateList.size() < usrAuthMst.getCOMPLICATE_LVL()) {
			return false;
		}

		return true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#containtsHistory(String)
	 */
	public boolean containtsHistory(String password) {

		// ���[�U�F�ؐݒ肪�Ȃ���Η���F�؂Ȃ�
		if (this.usrAuthMst == null) {
			return true;
		}

		// ����ݒ肪0�̂Ƃ��͏����Ȃ��B
		if (usrAuthMst.getHISTORY_MAX_CNT() == 0) {
			return true;
		}

		// ����ݒ肪1�ȏ�̒l�ő��݂��A������񂪂Ȃ��ƐV�K�쐬
		List<PWD_HST_TBL> historyList = historyDao.findPassword(companyCode, userCode);

		for (PWD_HST_TBL histEntity : historyList) {

			// �������X�g�̃p�X���[�h�Ɠ��͒l���r�A�����ꍇfalse��Ԃ��p�X���[�h��ʂɁB
			if (histEntity.getPASSWORD().equals(password)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#update(String, String)
	 */
	public void update(String password, String prgID) {

		// ���̂̏�����
		USR_MST entity = usrDao.getUSR_MSTByKaicodeUsrcode(companyCode, userCode);

		// �X�V���̐ݒ�
		entity.setPASSWORD(password);
		entity.setPWD_UPD_DATE(Util.getCurrentDate());
		entity.setPRG_ID(prgID);
		entity.setUSR_ID(userCode);

		// �f�[�^���X�V����
		usrDao.update(entity);

		if (isPasswordManaged()) {
			updateHistory(password);
		}
	}

	/**
	 * �p�X���[�h�����X�V
	 * 
	 * @param password �p�X���[�h
	 */
	public void updateHistory(String password) {

		List<PWD_HST_TBL> passDtoList = historyDao.findPassword(companyCode, userCode);

		// ���𐔂��ݒ�l��葽���ꍇ�A�ŏ��̒l�������i�Â����j
		int maxCount = usrAuthMst.getHISTORY_MAX_CNT();
		if (passDtoList.size() >= maxCount) {

			// ���𐔐ݒ��r���ŕύX�����ꍇ�ɑΉ����邽�߁A���𐔂ɐݒ萔����������������
			for (int i = 0; i < (passDtoList.size() - maxCount); i++) {
				historyDao.delete(passDtoList.get(i));
			}
		}

		if (maxCount == 0) {
			return;
		}

		// �����X�V
		PWD_HST_TBL insertDto = new PWD_HST_TBL();
		insertDto.setKAI_CODE(companyCode);
		insertDto.setUSR_CODE(userCode);
		insertDto.setPASSWORD(password);
		insertDto.setINP_DATE(Util.getCurrentDate());
		historyDao.insert(insertDto);
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#getErrorMessageId()
	 */
	public String getErrorMessageId() {
		return this.errorMsg;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#getErrorArgs()
	 */
	public Object[] getErrorArgs() {
		return this.errorArgs.toArray(new Object[errorArgs.size()]);
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#getPwdLimitMsg()
	 */
	public int getPwdLimitMsg() {
		return termDays;
	}
}
