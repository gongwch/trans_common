package jp.co.ais.trans.master.ui;

import java.io.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ユーザ認証管理マスタコントロール
 * 
 * @author roh
 */
public class MG0026UserAuthUpdatePanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0026";

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0026UserAuthUpdateServlet";
	}

	/** パネル */
	protected MG0026UserAuthUpdatePanel panel;

	/** ロックアウト後開放時間(文字) */
	protected String lockOutReleaseStr = getWord("C02772") + getWord("C02774");

	/** パスワード期間(文字) */
	protected String pwdTermStr = getWord("C00597") + getWord("C00101");

	/** 認証管理エンティティ */
	protected USR_AUTH_MST usrAuthDto;

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/** コンストラクタ */
	public MG0026UserAuthUpdatePanelCtrl() {
		panel = new MG0026UserAuthUpdatePanel(this);

		try {
			// 画面構築
			initPanel();

			// データ反映
			reflectNowData();

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * パネルのメッセージ構成
	 */
	protected void initPanel() {
		// ロックアウト後開放時間
		panel.lockOutReleaseField.setLabelText(lockOutReleaseStr);

		MessageUtil.getShortWord(getLoginLanguage(), "C02773");

		boolean isShortWord = Util.isShortLanguage(getLoginLanguage());

		// ロックアウト最大回数
		panel.lockOutArrField.setLabelText(getWord("C02772")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C02773") : getWord("C02773")));
		// 複雑レベル
		panel.complicateLvlField.setLabelText(getWord("C02776")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C01739") : getWord("C01739")));
		// パスワード期間
		panel.pwdTermField.setLabelText(pwdTermStr);

		// 複雑レベル
		String kindOrverWord = getWord("C02780");
		for (int i = 1; i <= 4; i++) {
			panel.complicateLvlField.getComboBox().addTextValueItem(i, i + kindOrverWord);
		}

		// 1～9までの値を設定
		for (int i = 0; i <= 9; i++) {
			panel.lockOutArrField.getComboBox().addTextValueItem(i, String.valueOf(i));
			panel.historyMaxField.getComboBox().addTextValueItem(i, String.valueOf(i));
			panel.pwdMinField.getComboBox().addTextValueItem(i, String.valueOf(i));
		}

		panel.pwdMinField.getComboBox().addTextValueItem(10, "10");
	}

	/**
	 * 画面情報初期化
	 * 
	 * @throws IOException
	 */
	protected void reflectNowData() throws IOException {

		addSendValues("flag", "findByKaiCode");

		if (!request(getServletName())) {
			errorHandler(panel);
			return;
		}

		usrAuthDto = (USR_AUTH_MST) super.getResultDto(USR_AUTH_MST.class);

		if (usrAuthDto != null) {
			panel.lockOutArrField.getComboBox().setSelectedIndex(usrAuthDto.getLOCK_OUT_ARR_CNT());
			panel.lockOutReleaseField.setNumberValue(usrAuthDto.getLOCK_OUT_RELEASE_TIME());
			panel.pwdMinField.getComboBox().setSelectedIndex(usrAuthDto.getPWD_MIN_LENGTH());
			panel.pwdTermField.setNumberValue(usrAuthDto.getPWD_TERM());
			panel.historyMaxField.getComboBox().setSelectedIndex(usrAuthDto.getHISTORY_MAX_CNT());
			panel.complicateLvlField.getComboBox().setSelectedIndex(usrAuthDto.getCOMPLICATE_LVL() - 1);
			panel.pwdTimeLimitMsgField.setNumberValue(usrAuthDto.getPWD_EXP_BEFORE_DAYS());
		}
	}

	/**
	 * パネルフィールドの値チェック
	 * 
	 * @return false:値不正
	 */
	protected boolean checkPanel() {

		// ロックアウト回数設定チェック
		if (Util.isNullOrEmpty(panel.lockOutReleaseField.getValue())) {
			showMessage(panel, "I00002", lockOutReleaseStr);
			panel.lockOutReleaseField.requestFocus();
			return false;
		}

		// パスワード有効期間設定チェック
		if (Util.isNullOrEmpty(panel.pwdTermField.getValue())) {
			showMessage(panel, "I00002", pwdTermStr);
			panel.pwdTermField.requestFocus();
			return false;
		}

		// 期限切れメッセージ通知期間チェック
		if (Util.isNullOrEmpty(panel.pwdTimeLimitMsgField.getValue())) {
			showMessage(panel, "I00002", "C04285");
			panel.pwdTimeLimitMsgField.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 情報更新(確定)
	 */
	protected void update() {

		try {
			if (!checkPanel()) {
				return;
			}

			addSendValues("flag", "updateAuthDto");

			USR_AUTH_MST usrAuthUpdateDto = new USR_AUTH_MST();

			// Dtoをパラメータとして設定
			usrAuthUpdateDto
				.setLOCK_OUT_ARR_CNT((Integer) (panel.lockOutArrField.getComboBox().getSelectedItemValue()));
			usrAuthUpdateDto.setLOCK_OUT_RELEASE_TIME(panel.lockOutReleaseField.getIntValue());
			usrAuthUpdateDto.setPWD_MIN_LENGTH((Integer) panel.pwdMinField.getComboBox().getSelectedItemValue());
			usrAuthUpdateDto.setPWD_TERM(panel.pwdTermField.getIntValue());
			usrAuthUpdateDto.setCOMPLICATE_LVL((Integer) panel.complicateLvlField.getComboBox().getSelectedItemValue());
			usrAuthUpdateDto.setHISTORY_MAX_CNT((Integer) (panel.historyMaxField.getComboBox().getSelectedItemValue()));
			usrAuthUpdateDto.setPRG_ID(PROGRAM_ID);
			usrAuthUpdateDto.setPWD_EXP_BEFORE_DAYS(panel.pwdTimeLimitMsgField.getIntValue());

			addSendDto(usrAuthUpdateDto);

			if (!request(getServletName())) {
				errorHandler(panel);
				return;
			}

			showMessage(panel, "I00008");

		} catch (IOException ex) {
			super.errorHandler(panel, ex, "E00009");
		}
	}
}
