package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.util.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 組織ユニット、部門用コントロール
 */
public class DepartmentUnitCtrl extends TOrganizationUnitCtrl {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "InformationServlet";

	protected static final String FLAG_UPPER_DEP = "UpDep";

	protected static final String FLAG_DEP = "Dep";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** 階層レベル */
	protected int intLevel = -1;

	/** 上位部門ｺｰﾄﾞ */
	protected String strUpDepCode = "";

	/** 上位部門名 */
	protected String strUpDepName = "";

	/** 部門ｺｰﾄﾞ */
	protected String strDepCode = "";

	/** 部門名 */
	protected String strDepName = "";

	/** 現在選択中の開示階層レベル */
	protected int nowLevel = 0;

	/** 科目体系コード */
	protected String itemSystemCode;

	/**
	 * 上位部門フラグ
	 * 
	 * @return 上位部門フラグ
	 */
	public String getUpperOrgFlag() {
		return FLAG_UPPER_DEP;

	}

	/**
	 * 部門フラグ
	 * 
	 * @return 部門フラグ
	 */
	public String getOrgFlag() {
		return FLAG_DEP;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param unit 組織ユニット
	 */
	public DepartmentUnitCtrl(TOrganizationUnit unit) {
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
		// 上位部門
		unit.getUpperCodeField().setLangMessageID("C00719");
		// 部門
		unit.getCodeField().setLangMessageID("C00467");
		// ラジオボタン(含む)
		unit.getRdoInclude().setLangMessageID("C00461");
		// ラジオボタン（含まない）
		unit.getRdoExclude().setLangMessageID("C00460");
		// 配下部門
		unit.getSubordinate().setLangMessageID("C00904");
	}

	/**
	 * 初期化処理
	 */
	public void initPanel() {
		super.initPanel();

		try {
			// 1.組織コードcomboBox作成
			String[] orgCodes = InformationUtil.getOrganizationCodeList();
			unit.getOrganizationComboBox().getComboBox().setModel(orgCodes);

			// 2.開示レベルのデフォルト選択
			setItemSystemCode("00");

		} catch (Exception e) {
			errorHandler(unit, e);
		}
	}

	/**
	 * 部門を制御する
	 */
	public void changeUpper() {
		// 画面.階層レベル取得
		int selLevel = unit.getHierarchicalLevel();

		// 画面.階層レベル = 開示レベル.階層レベルの場合、操作しない
		if (selLevel == intLevel) {
			return;
		}

		// 上位部門は押下可且つ入力済みの場合、部門押下可、他の場合逆
		boolean isEdit = !Util.isNullOrEmpty(unit.getUpperCodeField().getNotice().getValue());

		unit.getCodeField().getButton().setEnabled(isEdit);
		unit.getCodeField().getField().setEditable(isEdit);

		unit.getCodeField().setValue("");
		unit.getCodeField().setNoticeValue("");
	}

	/**
	 * 部門を取得する
	 * 
	 * @param tbuttonField
	 * @param btnFlag
	 */
	public void showRefDialog(TButtonField tbuttonField, String btnFlag) {
		try {

			// 部門マスタ一覧の場合
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(unit, REFDialogMasterCtrl.BMN_MST);

			// 初期部門コード
			dialog.setBaseBmnCode(strDepCode);
			// 初期階層ﾚﾍﾞﾙ
			dialog.setBaseDpkLvl(String.valueOf(intLevel));
			// 組織コード
			dialog.setDpkSsk(unit.getOrganizationCode());
			// 配下部門(0:含む 、1:含まない、2:上位部門ｺｰﾄﾞ選択の場合)
			String kbn = FLAG_UPPER_DEP.equals(btnFlag) ? "2" : "1";

			dialog.setBmnKbn(kbn);
			// 上位部門ｺｰﾄﾞ
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
			strUpDepCode = orgInfo.getUpperDepartmentCode(); // 上位部門ｺｰﾄﾞ
			strUpDepName = orgInfo.getUpperDepartmentName(); // 上位部門名
			strDepCode = orgInfo.getDepartmentCode(); // 部門ｺｰﾄﾞ
			strDepName = orgInfo.getDepartmentName(); // 部門名

			// 画面階層ﾚﾍﾞﾙ設置
			if (intLevel > 0) {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(intLevel);
			} else {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(0);
			}

			// 上位部門の初期値設置
			unit.getUpperCodeField().setEditable(false);
			unit.getUpperCodeField().getButton().setEnabled(false);
			unit.getUpperCodeField().setValue(strUpDepCode);
			unit.getUpperCodeField().setNoticeValue(strUpDepName);

			// 部門の値設置
			unit.getCodeField().setEditable(false);
			unit.getCodeField().getButton().setEnabled(false);
			unit.getCodeField().setValue(strDepCode);
			unit.getCodeField().setNoticeValue(strDepName);

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

			// 上位部門の値を保持
			String upperTextValue = unit.getUpperCodeField().getField().getText();
			String upperNoticeValue = unit.getUpperCodeField().getNotice().getText();
			boolean upperBol = unit.getUpperCodeField().isEditable();

			// 部門の値を保持
			String departTextValue = unit.getCodeField().getField().getText();
			String departNotice = unit.getCodeField().getNotice().getText();
			boolean departBol = unit.getCodeField().isEditable();

			// 無効です
			super.showMessage(unit, "I00016", "C00060");

			// 直前のレベルに戻す
			unit.getHierarchicalLevelComboBox().setSelectedIndex(this.nowLevel);

			unit.getUpperCodeField().getField().setText(upperTextValue);
			unit.getUpperCodeField().getNotice().setText(upperNoticeValue);

			unit.getCodeField().getField().setText(departTextValue);
			unit.getCodeField().getNotice().setText(departNotice);

			unit.getUpperCodeField().getField().setEditable(upperBol);
			unit.getUpperCodeField().getButton().setEnabled(upperBol);
			unit.getCodeField().getField().setEditable(departBol);
			unit.getCodeField().getButton().setEnabled(departBol);

			return;
		}

		// 上位部門設定
		boolean isEditUpper = level > intLevel && level != 0;
		String upperCode = (level == intLevel) ? strUpDepCode : "";
		String upperName = (level == intLevel) ? strUpDepName : "";

		unit.getUpperCodeField().getField().setEditable(isEditUpper);
		unit.getUpperCodeField().getButton().setEnabled(isEditUpper);
		unit.getUpperCodeField().setValue(upperCode);
		unit.getUpperCodeField().setNoticeValue(upperName);

		// 部門設定
		boolean isEditDept = level == 0 && intLevel != 0;
		String deptCode = (level == intLevel) ? strDepCode : "";
		String deptName = (level == intLevel) ? strDepName : "";

		unit.getCodeField().getField().setEditable(isEditDept);
		unit.getCodeField().getButton().setEnabled(isEditDept);
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
	 * @param btnFlag 部門ｺｰﾄﾞ/上位部門ｺｰﾄﾞ フラグ
	 */
	public boolean setupName(TButtonField argBtnField, String btnFlag) {

		try {
			// 部門コード
			String strFieldCode = argBtnField.getValue();
			if (Util.isNullOrEmpty(strFieldCode)) {
				// 空文字セット
				argBtnField.setNoticeValue("");
				return true;
			}

			addSendValues("FLAG", "isCode"); // パラメータ
			addSendValues("type", btnFlag); // 部門ｺｰﾄﾞ/上位部門ｺｰﾄﾞの区分
			addSendValues("kaiCode", getLoginUserCompanyCode()); // 会社コード
			addSendValues("userId", getLoginUserID()); // ユーザコード

			// 組織ｺｰﾄﾞ
			String strOrganizationCode = unit.getOrganizationCode();
			addSendValues("organizationCode", strOrganizationCode);

			// 画面の階層ﾚﾍﾞﾙを取得する
			String hierarchicalLevel = String.valueOf(unit.getHierarchicalLevel());
			addSendValues("panelLevel", hierarchicalLevel);

			// 画面の上位部門ｺｰﾄを取得する
			String strUpCode = String.valueOf(unit.getUpperCodeField().getValue());
			addSendValues("upDepCode", strUpCode);

			addSendValues("Level", Util.avoidNull(intLevel)); // 開示レベル
			addSendValues("depCode", strDepCode); // 開示部門ｺｰﾄﾞ
			addSendValues("fieldCode", strFieldCode); // 上位部門ｺｰﾄﾞ

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
				if (FLAG_UPPER_DEP.equals(btnFlag)) {
					unit.getCodeField().setValue("");
					unit.getCodeField().setNoticeValue("");
				}

				return true;
			} else {

				if (FLAG_UPPER_DEP.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C00719");
				} else if (FLAG_DEP.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C00467");
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

}
