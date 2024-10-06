package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vesselの検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TVesselReferenceController extends TReferenceController {

	/** 検索条件 */
	protected VesselSearchCondition condition;

	/** フィールド */
	protected TVesselReference ctrl;

	/** 会社コード検証するか */
	protected boolean verifyCompanyCode = true;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TVesselReferenceController(TReference field) {
		super(field);
		this.ctrl = (TVesselReference) field;
	}

	@Override
	public void init() {

		super.init();

		// 検索条件初期化
		initSearchCondition();

	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected VesselSearchCondition createSearchCondition() {
		return new VesselSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

	@Override
	public boolean code_Verify(JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			clear();
			return true;
		}

		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
		}

		// 会社コードチェック
		if (!companyCode_Verify()) {
			clear();
			return false;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば略称をセット
		if (entity != null) {

			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getCode());
			field.name.setText(getEntity().getNames());
			return true;
		}

		// エンティティが存在しない場合は略称クリア
		field.name.setText(null);

		if (checkExist) {
			showMessage(field, "I00123");// 該当コードは存在しません
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}

		return true;
	}

	/**
	 * 有効期限チェック
	 * 
	 * @return true:有効期間内、false:有効期間外
	 */
	protected boolean isInValidTerm() {
		if (getCompany().getAccountConfig().isSlipTermCheck()) {
			Date validTerm = condition.getValidTerm();

			if (validTerm != null) {
				Date from = getEntity().getDateFrom();
				Date to = getEntity().getDateTo();
				if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
					|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {

					if (!showConfirmMessage("Q00025", getEntity().getCode())) {
						field.code.requestFocus();
						field.code.clearOldText();
						return false;
					}

					field.code.requestFocus();
				}
			}
		}

		return true;
	}

	/**
	 * 会社コードがセットされているか確認する
	 * 
	 * @return 会社コードチェック結果(true:エラーなし、false：エラーあり)
	 */
	protected boolean companyCode_Verify() {
		if (!verifyCompanyCode) {
			// 検証不要の場合、エラーなし
			return true;
		}

		// 会社コードが設定されていない場合or全SPCモードoffの時はメッセージ表示
		if (Util.isNullOrEmpty(this.ctrl.companyCode) && !ctrl.isAllCompanyMode()) {
			showMessage(field, "I00003", "C00596"); // 指定してください。会社コード
			return false;
		}
		return true;
	}

	/**
	 * 検索ダイアログ[検索]ボタン押下
	 */
	@Override
	public void btnSearch_Click() {
		try {
			// 確定ボタンを押下不可にする
			dialog.btnSettle.setEnabled(false);

			// 一覧をクリアする
			dialog.tbl.removeRow();

			// データを抽出する
			VesselSearchCondition condition_ = getCondition().clone();

			if (Util.isNullOrEmpty(this.ctrl.companyCode)) {
				condition_.setCompanyCode(getCompanyCode());
			} else {
				condition_.setCompanyCode(this.ctrl.companyCode);
			}
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			condition_.setNamekLike(dialog.namek.getText());
			condition_.setShipBuild(this.ctrl.isShipBuildEntry);

			String method = "get";
			if (ctrl.isAllCompanyMode()) {
				method = "getRef";
				condition_.setCompanyCode(null);
			}

			List<Vessel> list = getList(condition_, method);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Vessel bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getNames(), bean.getNamek() });
			}

			// 確定ボタンを押下可能にする
			dialog.btnSettle.setEnabled(true);

			// 1行目を選択
			dialog.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 検索ダイアログ[確定]ボタン押下
	 */
	@Override
	public void btnSettle_Click() {
		try {

			// 一覧で選択されたEntityを取得する
			Vessel newEntity = getSelectedEntity();

			// 同一のコードが選択された場合は、処理なし
			if (field.code.getText().equals(newEntity.getCode())) {
				dialog.setVisible(false);
				dialog.dispose();
				field.code.requestFocus();
				return;
			}

			entity = getSelectedEntity();

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
				field.code.pushOldText();
			}

			// ダイアログを閉じる
			dialog.setVisible(false);
			dialog.dispose();

			// 呼び出し元のコードフィールドにフォーカス
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(entity != null);
					listener.afterVerify(entity != null);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return entity
	 */
	protected Vessel getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			VesselSearchCondition condition_ = getCondition().clone();

			if (Util.isNullOrEmpty(this.ctrl.companyCode)) {
				condition_.setCompanyCode(getCompanyCode());
			} else {
				condition_.setCompanyCode(this.ctrl.companyCode);
			}

			condition_.setCode(field.getCode());
			// 直接入力の場合は範囲指定を考慮させない
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			String method = "get";
			if (ctrl.isAllCompanyMode()) {
				method = "getRef";
				condition_.setCompanyCode(null);
			}

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Vessel> list = getList(condition_, method);

			if (list == null || list.isEmpty()) {
				return null;
			}

			return list.get(0);

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;

	}

	/**
	 * 検索条件を取得する
	 * 
	 * @return 検索条件
	 */
	protected VesselSearchCondition getCondition() {
		return condition;
	}

	/**
	 * Vesselを取得する
	 * 
	 * @param condition_ 検索条件
	 * @return Vessel情報
	 * @throws Exception
	 */
	protected List<Vessel> getList(VesselSearchCondition condition_) throws Exception {

		List<Vessel> list = (List<Vessel>) request(getManager(), "get", condition_);
		return list;
	}

	/**
	 * Vesselを取得する
	 * 
	 * @param condition_ 検索条件
	 * @param method
	 * @return Vessel情報
	 * @throws Exception
	 */
	protected List<Vessel> getList(VesselSearchCondition condition_, String method) throws Exception {

		List<Vessel> list = (List<Vessel>) request(getManager(), method, condition_);
		return list;
	}

	/**
	 * マネージャを返す
	 * 
	 * @return VesselManager
	 */
	protected Class getManager() {
		return VesselManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return entity
	 */
	protected Vessel getSelectedEntity() {
		return (Vessel) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	/**
	 * 検索ダイアログのキャプションを返す
	 */
	@Override
	public String getDialogCaption() {
		return getWord("C00466"); // Vessel
	}

	/**
	 * 検索ダイアログのテーブル保存キーを返す
	 */
	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	/**
	 * ボタンのキャプションを取得します
	 */
	@Override
	public String getButtonCaption() {
		return getWord("C00466"); // Vessel
	}

	/**
	 * 検索ダイアログ(Vessel用)のインスタンスを生成し返す
	 * 
	 * @return 検索ダイアログ
	 */
	@Override
	protected TReferenceDialog createDialog() {
		return new TReferenceDialog(this);
	}

	/**
	 * フィールド[ボタン]押下
	 */
	@Override
	public void btn_Click() {

		// 会社コードが設定されていない場合はメッセージ表示し、以降の処理は行わない
		if (!companyCode_Verify()) {
			return;
		}

		super.btn_Click();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public Vessel getEntity() {
		return (Vessel) super.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	public void setEntity(Vessel bean) {
		if (bean == null) {
			clear();
		} else {
			field.code.setText(bean.getCode());
			field.name.setText(bean.getNames());
			entity = bean;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Vessel bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Vessel bean = new Vessel();
		bean.setCode(field.code.getText());
		bean.setNames(field.name.getText());
		return bean;
	}

	/**
	 * 会社コード検証するかの取得
	 * 
	 * @return verifyCompanyCode 会社コード検証するか
	 */
	public boolean isVerifyCompanyCode() {
		return verifyCompanyCode;
	}

	/**
	 * 会社コード検証するかの設定
	 * 
	 * @param verifyCompanyCode 会社コード検証するか
	 */
	public void setVerifyCompanyCode(boolean verifyCompanyCode) {
		this.verifyCompanyCode = verifyCompanyCode;
	}

}
