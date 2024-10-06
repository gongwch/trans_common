package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.master.ui.MG0026UserAuthUpdatePanel;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.security.*;

/**
 * ユーザー認証管理マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0026UserAuthUpdatePanelCtrl extends TController {

	/** 指示画面 */
	protected MG0026UserAuthUpdatePanel mainView = null;

	/** ロックアウト最大回数(文字) */
	protected String lockOutMaxReleaseStr = getWord("C02772") + getWord("C02773");

	/** ロックアウト後開放時間(文字) */
	protected String lockOutReleaseStr = getWord("C02772") + getWord("C02774");

	/** パスワード期間(文字) */
	protected String pwdTermStr = getWord("C00597") + getWord("C00101");

	/** 拡張子 */
	public static String[] supportFileExts = null;

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/** コンストラクタ */
	public MG0026UserAuthUpdatePanelCtrl() {
		mainView = new MG0026UserAuthUpdatePanel();
		addMainViewEvent();
		try {
			// 画面構築
			initPanel();

			// 既存データ反映
			reflectNowData();

		} catch (Exception e) {
			errorHandler(mainView.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * パネルのメッセージ構成とコンボボックス値を定義
	 */
	protected void initPanel() {
		// ロックアウト後開放時間
		mainView.rockOutLatTime.setLabelText(lockOutReleaseStr);

		MessageUtil.getShortWord(getLoginLanguage(), "C02773");

		boolean isShortWord = Util.isShortLanguage(getLoginLanguage());

		// ロックアウト最大回数
		mainView.rockOutMaxCount.setLabelText(getWord("C02772")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C02773") : getWord("C02773")));

		// 複雑レベル
		mainView.difficultLevel.setLabelText(getWord("C02776")
			+ (isShortWord ? MessageUtil.getShortWord(getLoginLanguage(), "C01739") : getWord("C01739")));

		// パスワード期間
		mainView.passTerm.setLabelText(pwdTermStr);

		// 複雑レベルコンボボックス値
		String kindOrverWord = getWord("C02780");
		for (int i = 1; i <= 4; i++) {
			mainView.difficultLevel.getComboBox().addTextValueItem(i, i + kindOrverWord);
		}

		// 1～9までの値を設定、コンボボックス値
		for (int i = 0; i <= 9; i++) {
			mainView.rockOutMaxCount.getComboBox().addTextValueItem(i, String.valueOf(i));
			mainView.histryCount.getComboBox().addTextValueItem(i, String.valueOf(i));
			mainView.minPasslength.getComboBox().addTextValueItem(i, String.valueOf(i));
		}
		mainView.minPasslength.getComboBox().addTextValueItem(10, "10");

	}

	/**
	 * 画面情報初期化
	 * 
	 * @throws IOException
	 */
	protected void reflectNowData() throws IOException {

		try {
			USR_AUTH_MST bean = getUserauth();
			bean = (USR_AUTH_MST) request(getModelClass(), "get");

			if (!Util.isNullOrEmpty(bean.getKAI_CODE())) {
				mainView.rockOutMaxCount.getComboBox().setSelectedIndex(bean.getLOCK_OUT_ARR_CNT());
				mainView.rockOutLatTime.setValue(bean.getLOCK_OUT_RELEASE_TIME());
				mainView.minPasslength.getComboBox().setSelectedIndex(bean.getPWD_MIN_LENGTH());
				mainView.passTerm.setValue(bean.getPWD_TERM());
				mainView.histryCount.getComboBox().setSelectedIndex(bean.getHISTORY_MAX_CNT());
				mainView.difficultLevel.getComboBox().setSelectedIndex(bean.getCOMPLICATE_LVL() - 1);
				mainView.messagePshu.setValue(bean.getPWD_EXP_BEFORE_DAYS());
			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面ボタン押下時イベント
	 */
	protected void addMainViewEvent() {
		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 指示画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isInputCorrect()) {
				return;
			}

			// 確定処理 登録または更新
			USR_AUTH_MST bean = getUserauth();
			request(getModelClass(), "modify", bean);

			// 処理完了のメッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面で入力された値を返す
	 * 
	 * @return 指示画面で入力された値
	 */
	protected USR_AUTH_MST getUserauth() {

		USR_AUTH_MST usrauthupd = new USR_AUTH_MST();

		usrauthupd.setKAI_CODE(getCompanyCode());
		usrauthupd.setLOCK_OUT_ARR_CNT((Integer) mainView.rockOutMaxCount.getComboBox().getSelectedItemValue());
		usrauthupd.setLOCK_OUT_RELEASE_TIME(mainView.rockOutLatTime.getValue());
		usrauthupd.setPWD_MIN_LENGTH((Integer) mainView.minPasslength.getComboBox().getSelectedItemValue());
		usrauthupd.setPWD_TERM(mainView.passTerm.getValue());
		usrauthupd.setCOMPLICATE_LVL((Integer) mainView.difficultLevel.getComboBox().getSelectedItemValue());
		usrauthupd.setHISTORY_MAX_CNT((Integer) mainView.histryCount.getComboBox().getSelectedItemValue());
		usrauthupd.setPWD_EXP_BEFORE_DAYS(mainView.messagePshu.getValue());

		return usrauthupd;
	}

	/**
	 * インタフェースクラスを返す
	 * 
	 * @return UserAuthMangager
	 */
	protected Class getModelClass() {
		return UserAuthUpdateManager.class;
	}

	/**
	 * 指示画面で入力した値が妥当かをチェックする
	 * 
	 * @return 指示画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// ロックアウト回数後解放時間設定チェック
		if (Util.isNullOrEmpty(mainView.rockOutLatTime.getValue())) {
			showMessage(mainView, "I00037", lockOutReleaseStr);
			mainView.rockOutLatTime.requestFocus();
			return false;
		}

		// パスワード有効期間設定チェック
		if (Util.isNullOrEmpty(mainView.passTerm.getValue())) {
			showMessage(mainView, "I00037", pwdTermStr);
			mainView.passTerm.requestFocus();
			return false;
		}

		// 期限切れメッセージ通知期間チェック
		if (Util.isNullOrEmpty(mainView.messagePshu.getValue())) {
			showMessage(mainView, "I00037", "C04285");
			mainView.messagePshu.requestFocus();
			return false;
		}

		return true;

	}

}
