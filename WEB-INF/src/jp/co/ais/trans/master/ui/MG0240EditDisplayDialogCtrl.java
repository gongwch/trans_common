package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * プログラムマスタダイアログ コントロール
 */
public class MG0240EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** プログラムマスタダイアログ */
	protected MG0240EditDisplayDialog dialog;

	/** REF */
	protected REFDialogCtrl ref1;

	/** システム区分のデータ */
	private Map sysMap;

	/** 新規or編集フラグ */
	private boolean isUpdate;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0240ProgramMasterServlet";
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	// プログラムマスタダイアログの初期化
	public MG0240EditDisplayDialogCtrl(Frame parent, String titleid) {
		dialog = createDialog(parent, titleid);

		ref1 = new REFDialogCtrl(dialog.ctrlParentsProgramCode, dialog.getParentFrame());
		ref1.setColumnLabels("C00818", "C00820", "C00821");
		ref1.setTargetServlet(getServletName());
		ref1.setTitleID(getWord("C02147"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Program");
				return keys;
			}
		});

		dialog.ctrlParentsProgramCode.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlParentsProgramCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlParentsProgramCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlParentsProgramCode.getValue());
					dialog.ctrlParentsProgramCode.getField().clearOldText();
					dialog.ctrlParentsProgramCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

		dialog.ctrlProgramCode.getField().requestFocus();
	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MG0240EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0240EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledPrgCode プログラムコードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledPrgCode) {
		dialog.setVisible(true);
		dialog.ctrlProgramCode.setEditable(isEnabledPrgCode);
	}

	/**
	 * @param data
	 */
	public void setSysMap(Map data) {
		sysMap = data;
		this.fillItemsToComboBox(dialog.ctrlSystemDivision.getComboBox(), sysMap);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		boolean isinsert = "insert".equals(map.get("flag"));
		dialog.rdoMenuOnly.setSelected(isinsert);
		dialog.ctrlLoadModuleFileName.setEditable(!isinsert);

		this.setComboBoxSelectedItem(dialog.ctrlSystemDivision.getComboBox(), (String) map.get("sysCode"));

		if (isinsert) {
			return;
		}

		dialog.ctrlProgramCode.setValue((String) map.get("prgCode"));
		dialog.ctrlProgramName.setValue((String) map.get("prgName"));
		dialog.ctrlProgramAbbreviationName.setValue((String) map.get("prgName_S"));
		dialog.ctrlProgramNameForSearch.setValue((String) map.get("prgName_K"));

		dialog.numAuthorityLevel.setValue((String) map.get("ken"));
		dialog.ctrlLoadModuleFileName.setValue((String) map.get("ldName"));

		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("menuKbn")));

		if (!boo) {
			dialog.ctrlLoadModuleFileName.setValue("");
		}

		dialog.ctrlLoadModuleFileName.setEditable(boo);
		dialog.ctrlComment.setValue((String) map.get("com"));
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));
		dialog.ctrlParentsProgramCode.setValue((String) map.get("parentPrgCode"));
		dialog.rdoMenuOnly.setSelected(!boo);
		dialog.rdoScreenHaving.setSelected(boo);

		// 表示順序
		dialog.ctrlOrderDisplay.setValue((String) map.get("dispIndex"));

		ref1.refreshName();

		// 編集モードのときはプログラムコードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		dialog.ctrlSystemDivision.getComboBox().setEnabled(!isUpdate);
		if (isUpdate) {
			dialog.ctrlProgramName.getField().requestFocus();
		}

		dialog.ctrlProgramCode.getField().setEditable(!isUpdate);
		dialog.ctrlParentsProgramCode.getField().setEnabled(true);
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:入力NG
	 * @throws TRequestException
	 * @throws IOException
	 */
	private boolean checkDialog() throws TRequestException, IOException {

		// プログラムコードチェック
		if (Util.isNullOrEmpty(dialog.ctrlProgramCode.getValue())) {
			showMessage(dialog, "I00002", "C00818");
			dialog.ctrlProgramCode.requestFocus();
			return false;
		}

		if (!isUpdate && checkCode(dialog.ctrlProgramCode.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlProgramCode.requestFocus();
			return false;
		}

		// システム区分チェック
		if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlSystemDivision.getComboBox()))) {
			showMessage(dialog, "I00002", "C00980");
			dialog.ctrlSystemDivision.getComboBox().requestFocus();
			return false;
		}

		// プログラム名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlProgramName.getValue())) {
			showMessage(dialog, "I00002", "C00819");
			dialog.ctrlProgramName.requestFocus();
			return false;
		}

		// プログラム略称チェック
		if (Util.isNullOrEmpty(dialog.ctrlProgramAbbreviationName.getValue())) {
			showMessage(dialog, "I00002", "C00820");
			dialog.ctrlProgramAbbreviationName.requestFocus();
			return false;
		}

		// プログラム検索名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlProgramNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00821");
			dialog.ctrlProgramNameForSearch.requestFocus();
			return false;
		}

		// 権限レベルチェック
		if (Util.isNullOrEmpty(dialog.numAuthorityLevel.getValue())) {
			showMessage(dialog, "I00002", "C00822");
			dialog.numAuthorityLevel.requestFocus();
			return false;
		}

		// ロードモジュールファイル名チェック(画面時のみ)
		if (dialog.rdoScreenHaving.isSelected() && Util.isNullOrEmpty(dialog.ctrlLoadModuleFileName.getValue())) {
			showMessage(dialog, "I00002", "C00823");
			dialog.ctrlLoadModuleFileName.requestFocus();
			return false;
		}

		// 開始年月日
		if (dialog.dtBeginDate.getValue() == null) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.getCalendar().requestFocus();
			return false;
		}

		// 終了年月日
		if (dialog.dtEndDate.getValue() == null) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// 開始年月日が終了年月日より大きい
		if (!Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
			// エラーを返す
			return false;
		}

		// 親コード
		if (Util.isNullOrEmpty(dialog.ctrlParentsProgramCode.getValue())) {
			showMessage(dialog, "I00002", "C00824");
			dialog.ctrlParentsProgramCode.requestTextFocus();
			return false;
		}

		// 表示順序
		if (dialog.ctrlOrderDisplay.isEmpty()) {
			showMessage(dialog, "I00002", "C02397");
			dialog.ctrlOrderDisplay.requestFocus();
			return false;
		}

		// 成功
		return true;
	}

	/**
	 * 閉じる
	 */
	protected void disposeDialog() {

		// 確定ボタン押下 チェックOKなら閉じる
		try {
			if (dialog.isSettle && !this.checkDialog()) {
				return;
			}

			dialog.setVisible(false);// 閉じる

		} catch (Exception ex) {
			errorHandler(this.dialog, ex);
		}
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	protected boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	protected Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("sysCode", this.getComboBoxSelectedValue(dialog.ctrlSystemDivision.getComboBox()));
		map.put("prgCode", dialog.ctrlProgramCode.getValue());
		map.put("prgName", dialog.ctrlProgramName.getValue());
		map.put("prgName_S", dialog.ctrlProgramAbbreviationName.getValue());
		map.put("prgName_K", dialog.ctrlProgramNameForSearch.getValue());
		map.put("ken", dialog.numAuthorityLevel.getValue());
		map.put("com", dialog.ctrlComment.getValue());
		map.put("ldName", dialog.ctrlLoadModuleFileName.getValue());
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());
		map.put("parentPrgCode", dialog.ctrlParentsProgramCode.getValue());
		map.put("menuKbn", String.valueOf(dialog.rdoMenuOnly.isSelected() ? 0 : 1));
		// 表示順序追加
		map.put("dispIndex", dialog.ctrlOrderDisplay.getField().getText());
		// 擬似データ
		map.put("kaiCode", getLoginUserCompanyCode());

		return map;
	}

	/**
	 * 入力コードの存在チェック
	 * 
	 * @param code
	 * @return true:存在する
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected boolean checkCode(String code) throws IOException, TRequestException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}

		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("prgCode", dialog.ctrlProgramCode.getValue());
		addSendValues("sysCode", this.getComboBoxSelectedValue(dialog.ctrlSystemDivision.getComboBox()));

		if (!request(getServletName())) {
			throw new TRequestException();
		}

		return !getResultList().isEmpty();
	}

	/**
	 * メニュー区分処理
	 * 
	 * @param bln
	 */
	protected void checkCtrl(Boolean bln) {
		if (!bln) {
			dialog.ctrlLoadModuleFileName.setValue("");
		}
		dialog.ctrlLoadModuleFileName.setEditable(bln);
	}
}
