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
 * Applet用ログイン情報作成
 */
public class TAppletLoginCtrl extends TLoginCtrl {

	@Override
	protected String getServletName() {
		return isLoginServlet ? "TAppletServlet" : super.getServletName();
	}

	/**
	 * ログイン処理
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param pw
	 * @throws TException
	 */
	public void login(String companyCode, String userCode, String pw) throws TException {

		// ユーザー認証
		try {
			String sessionID = authenticateUser(companyCode, userCode, pw);

			// ログアウトでセッション開放考慮
			TClientLoginInfo.setSessionID(sessionID);

		} catch (TException e) {
			showMessage(e.getMessageID());
			return;
		}

		// ユーザー情報取得
		User user = getUser(companyCode, userCode);

		if (user == null) {
			showMessage("Error:Can not find User");
			return;
		}

		if (TLoginCtrl.isDirectPrint) {
			// 直接印刷の場合、プリンタの名称取得
			String printerName = getPrinterName(companyCode, userCode);
			user.setPrinterName(printerName);
		}

		TLoginInfo.setUser(user);

		// 多言語対応
		String lang = user.getLanguage();
		TClientLoginInfo.getInstance().setUserLanguage(lang);

		TUIManager.setUILocale(TClientLoginInfo.getInstance().getUserLocale());

		// 各部品内単語
		TCalendar.setDayOfWeekWords(getWordList(TLoginCtrl.CALENDAR_WEEK_IDS));
		TGuiUtil.setLightPopupMenuWords(getWordList(TLoginCtrl.TXT_POPUP_IDS));
		TTable.setLightPopupMenuWords(getWordList(TLoginCtrl.TBL_POPUP_IDS));

		// 会社情報取得
		Company company = getCompany(companyCode);

		if (company == null) {
			showMessage("Error:Can not find Company");
			return;
		}
		TLoginInfo.setCompany(company);

		// 旧バージョン用
		TClientLoginInfo.getInstance().setCompanyCode(company.getCode());
		TClientLoginInfo.getInstance().setUserCode(user.getCode());
		TClientLoginInfo.reflesh();

		// パスワード有効期間チェック
		if (!checkPassword(company, user)) {
			// 排他を解除してから、処理中断
			request(UserExclusiveManager.class, "cancelExclude", company.getCode(), user.getCode());
			return;
		}
	}

	/**
	 * プログラム起動ログ作成
	 * 
	 * @param flag IN/OUT
	 */
	public void log(String flag) {
		log(getCompany(), getUser(), getProgramCode(), flag);
	}

	/**
	 * プログラム情報作成
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
