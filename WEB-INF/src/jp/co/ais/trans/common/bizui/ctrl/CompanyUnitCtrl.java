package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.util.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 組織ユニット、会社用コントロール
 */
public class CompanyUnitCtrl extends TOrganizationUnitCtrl {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "InformationServlet";

	protected static final String FLAG_UPPER_COMPANY = "UpCompany";

	protected static final String FLAG_COMPANY = "Company";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** 階層レベル */
	protected int intLevel = -1;

	/** 上位会社ｺｰﾄﾞ */
	protected String strUpCompanyCode = "";

	/** 上位会社名 */
	protected String strUpCompanyName = "";

	/** Owner Companyｺｰﾄﾞ */
	protected String strCompanyCode = "";

	/** Owner Company名 */
	protected String strCompanyName = "";

	/** 現在選択中の開示階層レベル */
	protected int nowLevel = 0;

	/** 科目体系コード */
	protected String itemSystemCode;

	/**
	 * 上位会社フラグ
	 * 
	 * @return 上位会社フラグ
	 */
	public String getUpperOrgFlag() {
		return FLAG_UPPER_COMPANY;

	}

	/**
	 * Owner Companyフラグ
	 * 
	 * @return Owner Companyフラグ
	 */
	public String getOrgFlag() {
		return FLAG_COMPANY;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param unit 組織ユニット
	 */
	public CompanyUnitCtrl(TOrganizationUnit unit) {
		super(unit);
	}

	/**
	 * 各コンポーネントにメッセージIDを設定
	 */
	public void initLangMessageID() {

		// 出力単位
		unit.getOutputUnitPanel().setLangMessageID("C01159");
		// 組織コード
		unit.getOrganizationComboBox().setLangMessageID("C00335");
		// 階層レベル
		unit.getHierarchicalLevelComboBox().setLangMessageID("C00060");
		// 上位会社
		unit.getUpperCodeField().setLangMessageID("C01487");
		// Owner Company
		unit.getCodeField().setLangMessageID("C01436");
		// ラジオボタン(含む)
		unit.getRdoInclude().setLangMessageID("C00461");
		// ラジオボタン（含まない）
		unit.getRdoExclude().setLangMessageID("C00460");
		// 配下会社
		unit.getSubordinate().setLangMessageID("C01281");
	}

	/**
	 * 初期化処理
	 */
	public void initPanel() {
		super.initPanel();

		try {

			// 1.組織コード初期値取得
			String[] orgCodes = initOrganizationCode();
			unit.getOrganizationComboBox().getComboBox().setModel(orgCodes);

			// 2.開示レベルのデフォルト選択
			setItemSystemCode("00");

		} catch (Exception e) {
			errorHandler(unit, e);
		}
	}

	/**
	 * 会社を制御する
	 */
	public void changeUpper() {
		// 画面.階層レベル取得
		int selLevel = unit.getHierarchicalLevel();

		// 画面.階層レベル = 開示レベル.階層レベルの場合、操作しない
		if (selLevel == intLevel) {
			return;
		}

		// 上位会社は押下可且つ入力済みの場合、Owner Company押下可、他の場合逆
		boolean isEdit = !Util.isNullOrEmpty(unit.getUpperCodeField().getNotice().getValue());

		unit.getCodeField().getButton().setEnabled(isEdit);
		unit.getCodeField().getField().setEditable(isEdit);

		unit.getCodeField().setValue("");
		unit.getCodeField().setNoticeValue("");
	}

	/**
	 * Owner Companyを取得する
	 * 
	 * @param tbuttonField
	 * @param btnFlag
	 */
	public void showRefDialog(TButtonField tbuttonField, String btnFlag) {
		try {

			// 環境設定マスタ一覧の場合
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(unit, REFDialogMasterCtrl.ENV_MST);

			// 初期会社コード
			dialog.setBaseBmnCode(strCompanyCode);
			// 初期階層ﾚﾍﾞﾙ
			dialog.setBaseDpkLvl(String.valueOf(intLevel));
			// 組織コード
			dialog.setDpkSsk(unit.getOrganizationCode());
			// 配下会社(0:含む 、1:含まない、2:上位会社ｺｰﾄﾞ選択の場合)
			String kbn = FLAG_UPPER_COMPANY.equals(btnFlag) ? "2" : "1";

			dialog.setBmnKbn(kbn);
			// 上位会社ｺｰﾄﾞ
			dialog.setUpBmnCode(unit.getUpperCodeField().getValue());
			// 階層ﾚﾍﾞﾙ
			dialog.setDpkLvl(String.valueOf(unit.getHierarchicalLevel()));

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(tbuttonField.getValue())) {
				dialog.setCode(String.valueOf(tbuttonField.getValue()));
				dialog.searchData(false);
			}

			dialog.show();

			if (dialog.isSettle()) {
				String[] info = dialog.getCurrencyInfo();
				tbuttonField.setValue(info[0]);
				tbuttonField.setNoticeValue(info[1]);
			}

			tbuttonField.getField().requestFocus(true);

		} catch (Exception e) {
			errorHandler(unit, e);
		}
	}

	/**
	 * 科目体系コードのセット
	 * 
	 * @param code 科目体系コード
	 */
	public void setItemSystemCode(String code) {
		this.itemSystemCode = code;

		TComboBox combo = unit.getOrganizationComboBox().getComboBox();
		if (combo.getItemCount() != 0) {
			combo.setSelectedIndex(0);
		}

		changeOrgCode();
	}

	/**
	 * 組織ｺｰﾄﾞコンボボックスEvent処理。<br>
	 */
	public boolean changeOrgCode() {
		try {

			// 組織コード
			String orgCode = unit.getOrganizationCode();

			OrganizationInfo orgInfo = InformationUtil.getOrganizationInfo(itemSystemCode, orgCode);

			intLevel = orgInfo.getDisplayLevel(); // 階層ﾚﾍﾞﾙ
			strUpCompanyCode = orgInfo.getUpperDepartmentCode(); // 上位会社ｺｰﾄﾞ
			strUpCompanyName = orgInfo.getUpperDepartmentName(); // 上位会社名
			strCompanyCode = orgInfo.getDepartmentCode(); // Owner Companyｺｰﾄﾞ
			strCompanyName = orgInfo.getDepartmentName(); // Owner Company名

			// 画面階層ﾚﾍﾞﾙ設置
			if (intLevel > 0) {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(intLevel);
			} else {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(0);
			}

			// 上位会社の初期値設置
			unit.getUpperCodeField().setEditable(false);
			unit.getUpperCodeField().getButton().setEnabled(false);
			unit.getUpperCodeField().setValue(strUpCompanyCode);
			unit.getUpperCodeField().setNoticeValue(strUpCompanyName);

			// Owner Companyの値設置
			unit.getCodeField().setEditable(false);
			unit.getCodeField().getButton().setEnabled(false);
			unit.getCodeField().setValue(strCompanyCode);
			unit.getCodeField().setNoticeValue(strCompanyName);

			if (intLevel == -1) {
				unit.getCodeField().setEditable(true);
				unit.getCodeField().getButton().setEnabled(true);
			}

			return true;
		} catch (Exception e) {
			errorHandler(unit, e);

			return false;
		}
	}

	/**
	 * 階層レベル変更
	 */
	public void changeHierarchicalLevelItem() {

		int level = unit.getHierarchicalLevel();

		if (level < intLevel) {

			// 上位会社の値を保持
			String upperTextValue = unit.getUpperCodeField().getField().getText();
			String upperNoticeValue = unit.getUpperCodeField().getNotice().getText();
			boolean upperBol = unit.getUpperCodeField().isEditable();

			// Owner Companyの値を保持
			String companyTextValue = unit.getCodeField().getField().getText();
			String companyNotice = unit.getCodeField().getNotice().getText();
			boolean companyBol = unit.getCodeField().isEditable();

			// 無効です
			super.showMessage(unit, "I00016", "C00060");

			// 直前のレベルに戻す
			unit.getHierarchicalLevelComboBox().setSelectedIndex(this.nowLevel);

			unit.getUpperCodeField().getField().setText(upperTextValue);
			unit.getUpperCodeField().getNotice().setText(upperNoticeValue);

			unit.getCodeField().getField().setText(companyTextValue);
			unit.getCodeField().getNotice().setText(companyNotice);

			unit.getUpperCodeField().getField().setEditable(upperBol);
			unit.getUpperCodeField().getButton().setEnabled(upperBol);
			unit.getCodeField().getField().setEditable(companyBol);
			unit.getCodeField().getButton().setEnabled(companyBol);

			return;
		}

		// 上位会社設定
		boolean isEditUpper = level > intLevel && level != 0;
		String upperCode = (level == intLevel) ? strUpCompanyCode : "";
		String upperName = (level == intLevel) ? strUpCompanyName : "";

		unit.getUpperCodeField().getField().setEditable(isEditUpper);
		unit.getUpperCodeField().getButton().setEnabled(isEditUpper);
		unit.getUpperCodeField().setValue(upperCode);
		unit.getUpperCodeField().setNoticeValue(upperName);

		// Owner Company設定
		boolean isEditCompanyt = level == 0 && intLevel != 0;
		String deptCode = (level == intLevel) ? strCompanyCode : "";
		String deptName = (level == intLevel) ? strCompanyName : "";

		unit.getCodeField().getField().setEditable(isEditCompanyt);
		unit.getCodeField().getButton().setEnabled(isEditCompanyt);
		unit.getCodeField().setValue(deptCode);
		unit.getCodeField().setNoticeValue(deptName);

		unit.getUpperCodeField().getField().pushOldText();
		unit.getCodeField().getField().pushOldText();

		this.nowLevel = level;
	}

	/**
	 * 略称を設定する<br>
	 * 該当コードに対する略称が無い場合、返り値false
	 * 
	 * @param argBtnField 対象フィールド
	 * @param btnFlag Owner Companyｺｰﾄﾞ/上位会社ｺｰﾄﾞ フラグ
	 */
	public boolean setupName(TButtonField argBtnField, String btnFlag) {

		try {
			// Owner Companyコード
			String strFieldCode = argBtnField.getValue();
			if (Util.isNullOrEmpty(strFieldCode)) {
				// 空文字セット
				argBtnField.setNoticeValue("");
				return true;
			}

			addSendValues("FLAG", "isCode"); // パラメータ
			addSendValues("type", btnFlag); // Owner Companyｺｰﾄﾞ/上位会社ｺｰﾄﾞの区分
			addSendValues("kaiCode", getLoginUserCompanyCode()); // Owner Companyコード
			addSendValues("userId", getLoginUserID()); // ユーザコード

			// 組織ｺｰﾄﾞ
			String strOrganizationCode = unit.getOrganizationCode();
			addSendValues("organizationCode", strOrganizationCode);

			// 画面の階層ﾚﾍﾞﾙを取得する
			String hierarchicalLevel = String.valueOf(unit.getHierarchicalLevel());
			addSendValues("panelLevel", hierarchicalLevel);

			// 画面の上位会社コードを取得する
			String strUpCode = String.valueOf(unit.getUpperCodeField().getValue());
			addSendValues("upDepCode", strUpCode);

			addSendValues("Level", Util.avoidNull(intLevel)); // 開示レベル
			addSendValues("depCode", strCompanyCode); // 開示会社ｺｰﾄﾞ
			addSendValues("fieldCode", strFieldCode); // 上位会社ｺｰﾄﾞ

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				// 正常に処理されませんでした
				errorHandler(unit);

				argBtnField.clearOldText();
				argBtnField.setNoticeValue("");

				return false;
			}

			// データ取得
			String name = getResult().get("key");
			if (!Util.isNullOrEmpty(name)) {

				// 略称をセット
				argBtnField.setNoticeValue(name);
				if (FLAG_UPPER_COMPANY.equals(btnFlag)) {
					unit.getCodeField().setValue("");
					unit.getCodeField().setNoticeValue("");
				}

				return true;
			} else {

				if (FLAG_UPPER_COMPANY.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C01487");
				} else if (FLAG_COMPANY.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C01436");
				}

				argBtnField.clearOldText();
				argBtnField.setNoticeValue("");
				argBtnField.requestTextFocus();

				return false;
			}

		} catch (IOException e) {

			// 正常に処理されませんでした
			errorHandler(unit, e);

			argBtnField.clearOldText();
			argBtnField.setNoticeValue("");

			return false;
		}
	}

	/**
	 * 組織コード初期値取得
	 * 
	 * @return 組織コードリスト
	 */
	protected String[] initOrganizationCode() {
		try {
			// 送信するパラメータを設定
			addSendValues("FLAG", "CmpOrganizationCode");

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				// 正常に処理されませんでした
				errorHandler(unit);
			}

			// 組織ｺｰﾄﾞを設定する
			return StringUtil.toArrayFromDelimitString(getResult().get("orgCodes"));

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(unit, e);
		}
		return null;
	}

}
