package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * Applet�p���O�C�����쐬
 */
public class TAppletLoginCtrl extends TLoginCtrl {

	@Override
	protected String getServletName() {
		return isLoginServlet ? "TAppletServlet" : super.getServletName();
	}

	/**
	 * ���O�C������
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param pw
	 * @throws TException
	 */
	public void login(String companyCode, String userCode, String pw) throws TException {

		// ���[�U�[�F��
		try {
			String sessionID = authenticateUser(companyCode, userCode, pw);

			// ���O�A�E�g�ŃZ�b�V�����J���l��
			TClientLoginInfo.setSessionID(sessionID);

		} catch (TException e) {
			showMessage(e.getMessageID());
			return;
		}

		// ���[�U�[���擾
		User user = getUser(companyCode, userCode);

		if (user == null) {
			showMessage("Error:Can not find User");
			return;
		}

		if (TLoginCtrl.isDirectPrint) {
			// ���ڈ���̏ꍇ�A�v�����^�̖��̎擾
			String printerName = getPrinterName(companyCode, userCode);
			user.setPrinterName(printerName);
		}

		TLoginInfo.setUser(user);

		// ������Ή�
		String lang = user.getLanguage();
		TClientLoginInfo.getInstance().setUserLanguage(lang);

		TUIManager.setUILocale(TClientLoginInfo.getInstance().getUserLocale());

		// �e���i���P��
		TCalendar.setDayOfWeekWords(getWordList(TLoginCtrl.CALENDAR_WEEK_IDS));
		TGuiUtil.setLightPopupMenuWords(getWordList(TLoginCtrl.TXT_POPUP_IDS));
		TTable.setLightPopupMenuWords(getWordList(TLoginCtrl.TBL_POPUP_IDS));

		// ��Џ��擾
		Company company = getCompany(companyCode);

		if (company == null) {
			showMessage("Error:Can not find Company");
			return;
		}
		TLoginInfo.setCompany(company);

		// ���o�[�W�����p
		TClientLoginInfo.getInstance().setCompanyCode(company.getCode());
		TClientLoginInfo.getInstance().setUserCode(user.getCode());
		TClientLoginInfo.reflesh();

		// �p�X���[�h�L�����ԃ`�F�b�N
		if (!checkPassword(company, user)) {
			// �r�����������Ă���A�������f
			request(UserExclusiveManager.class, "cancelExclude", company.getCode(), user.getCode());
			return;
		}
	}

	/**
	 * �v���O�����N�����O�쐬
	 * 
	 * @param flag IN/OUT
	 */
	public void log(String flag) {
		log(getCompany(), getUser(), getProgramCode(), flag);
	}

	/**
	 * �v���O�������쐬
	 * 
	 * @param prgCode_
	 * @param prgName_
	 */
	@SuppressWarnings("deprecation")
	public void setProgramInfo(String prgCode_, String prgName_) {

		TClientProgramInfo prgInfo_ = TClientProgramInfo.getInstance();
		prgInfo_.setProgramCode(prgCode_);
		prgInfo_.setProgramName(prgName_);

		setProgramInfo(prgInfo_);
	}

}
