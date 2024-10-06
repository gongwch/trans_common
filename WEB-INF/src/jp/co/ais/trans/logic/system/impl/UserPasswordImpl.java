package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ユーザパスワード処理ロジック
 */
public class UserPasswordImpl implements UserPassword {

	/** ユーザーマスタDao */
	protected USR_MSTDao usrDao;

	/** ユーザ認証マスタDAO */
	protected USR_AUTH_MSTDao usrAuthDao;

	/** パスワード履歴マスタDAO */
	protected PWD_HST_TBLDao historyDao;

	/** 会社コード */
	protected String companyCode;

	/** ユーザコード */
	protected String userCode;

	/** ユーザデータ */
	protected USR_MST usrMst;

	/** ユーザ認証データ */
	protected USR_AUTH_MST usrAuthMst;

	/** エラーメッセージ */
	protected String errorMsg;

	/** 期限までの残日数 */
	protected int termDays;

	/** エラーメッセージ引数 */
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
	 * パスワード管理を行うかどうか
	 * 
	 * @return true:パスワード管理する
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
	 * パスワード有効期間チェック
	 * 
	 * @return boolean true:パスワード有効期間切れ
	 */
	public boolean isPasswordTermOrver() {

		// 認証情報なし = 検証なし
		if (usrAuthMst == null) {
			return false;
		}

		// 有効期間設定が0だと 検証なし
		if (usrAuthMst.getPWD_TERM() != 0) {

			// 更新情報を習得
			Date updDate = usrMst.getPWD_UPD_DATE();

			// バッチ処理とか直接入力によってパスワード更新日付が存在しないとパスワードを更新させる。
			if (updDate == null) {
				return true;
			}

			// 更新からの期間
			double interval = (Util.getCurrentDate().getTime()) - (updDate.getTime());

			// 更新期間設定
			double pwdTerm = (usrAuthMst.getPWD_TERM());

			// 更新からの時間と有効期間を比較する。期間を過ぎた場合True を返してパスワード画面に。
			if ((interval / 86400000) > pwdTerm) {

				return true;
			}
		}

		return false;
	}

	/**
	 * パスワード有効期限切れ通知日数チェック
	 * 
	 * @return boolean true: パスワード有効期限切れの通知をする
	 */

	public boolean isPwdLimitMsgNotice() {
		// 認証情報なし = 検証なし
		if (usrAuthMst == null) {
			return true;
		}

		if (usrAuthMst.getPWD_TERM() != 0) {

			// パスワード有効期限切れ通知日数設定が0だと 検証なし
			if (usrAuthMst.getPWD_EXP_BEFORE_DAYS() != 0) {
				// パスワード更新日付
				Date updDate = usrMst.getPWD_UPD_DATE();

				// パスワード期限切れ通知日数を取得
				int pwdLimitMsg = (usrAuthMst.getPWD_EXP_BEFORE_DAYS());

				// パスワード有効期間
				int pwdTerm = (usrAuthMst.getPWD_TERM());

				// 有効期間と通知期間の差分
				int totalTerm = pwdTerm - pwdLimitMsg;

				// 現在日とパスワード更新日の差分
				int term = DateUtil.getDayCount(updDate, Util.getCurrentDate());

				// 期限までの残日数 ＝ パスワードの有効期限 － システム日付
				termDays = DateUtil.getDayCount(Util.getCurrentDate(), DateUtil.addDay(updDate, pwdTerm));

				// 有効期間と通知期間の差分が現在日とパスワード更新日の差分より小さい場合（通知期間を過ぎている）
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

		// 字数チェック
		if (!assertLength(password)) {
			errorMsg = "I00027";
			errorArgs.add(usrAuthMst.getPWD_MIN_LENGTH());
			return false;
		}

		// 複雑レベルチェック
		if (!assertComplicateLevel(password)) {
			errorMsg = "I00028";
			errorArgs.add(usrAuthMst.getCOMPLICATE_LVL());
			return false;
		}

		// 履歴チェック
		if (!containtsHistory(password)) {
			errorMsg = "I00029";
			return false;
		}

		return true;
	}

	/**
	 * 文字数チェック.<br>
	 * 
	 * @return boolean true:文字数規定を満たす
	 */
	public boolean assertLength(String password) {

		// 会社設定なし = 検証なし
		if (usrAuthMst == null) {
			return true;
		}

		// 長さ設定が０の時は検証なし
		if (usrAuthMst.getPWD_MIN_LENGTH() != 0) {

			// 長さの基準を図る
			if (password.length() < usrAuthMst.getPWD_MIN_LENGTH()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 複雑レベルチェック
	 * 
	 * @return boolean
	 */
	public boolean assertComplicateLevel(String password) {

		// 会社設定なし ＝ 検証なし
		if (usrAuthMst == null) {
			return true;
		}

		// それぞれの文字をアスキコードで分別、complicateリストにその区分をいれる。
		Set<Integer> complicateList = new HashSet<Integer>();

		// 各文字を判定用int配列に変える。
		for (char schar : password.toCharArray()) {
			String str = Character.toString(schar);

			if (str.matches("[A-Z]")) {
				complicateList.add(1); // 大文字

			} else if (str.matches("[a-z]")) {
				complicateList.add(2); // 小文字

			} else if (str.matches("[0-9]")) {
				complicateList.add(3); // 数字

			} else if (str.matches("[!-~]")) {
				complicateList.add(4); // 記号
			}
		}

		// 複雑レベルが設定より低い場合エラーを出す。
		if (complicateList.size() < usrAuthMst.getCOMPLICATE_LVL()) {
			return false;
		}

		return true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserPassword#containtsHistory(String)
	 */
	public boolean containtsHistory(String password) {

		// ユーザ認証設定がなければ履歴認証なし
		if (this.usrAuthMst == null) {
			return true;
		}

		// 履歴設定が0のときは処理なし。
		if (usrAuthMst.getHISTORY_MAX_CNT() == 0) {
			return true;
		}

		// 履歴設定が1以上の値で存在し、履歴情報がないと新規作成
		List<PWD_HST_TBL> historyList = historyDao.findPassword(companyCode, userCode);

		for (PWD_HST_TBL histEntity : historyList) {

			// 履歴リストのパスワードと入力値を比較、同じ場合falseを返しパスワード画面に。
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

		// 実体の初期化
		USR_MST entity = usrDao.getUSR_MSTByKaicodeUsrcode(companyCode, userCode);

		// 更新情報の設定
		entity.setPASSWORD(password);
		entity.setPWD_UPD_DATE(Util.getCurrentDate());
		entity.setPRG_ID(prgID);
		entity.setUSR_ID(userCode);

		// データを更新する
		usrDao.update(entity);

		if (isPasswordManaged()) {
			updateHistory(password);
		}
	}

	/**
	 * パスワード履歴更新
	 * 
	 * @param password パスワード
	 */
	public void updateHistory(String password) {

		List<PWD_HST_TBL> passDtoList = historyDao.findPassword(companyCode, userCode);

		// 履歴数が設定値より多い場合、最初の値を消去（古い順）
		int maxCount = usrAuthMst.getHISTORY_MAX_CNT();
		if (passDtoList.size() >= maxCount) {

			// 履歴数設定を途中で変更した場合に対応するため、履歴数に設定数を引いた分を消す
			for (int i = 0; i < (passDtoList.size() - maxCount); i++) {
				historyDao.delete(passDtoList.get(i));
			}
		}

		if (maxCount == 0) {
			return;
		}

		// 履歴更新
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
