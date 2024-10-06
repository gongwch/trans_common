package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理4検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TManagement4ReferenceController extends TReferenceController {

	/** 検索条件 */
	protected Management4SearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TManagement4ReferenceController(TReference field) {
		super(field);
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
	protected Management4SearchCondition createSearchCondition() {
		return new Management4SearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			return true;
		}

		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
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
			Management4SearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<Management4> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Management4 bean : list) {
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
			entity = getSelectedEntity();

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
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

			field.code.pushOldText();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return 管理4情報
	 */
	protected Management4 getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			Management4SearchCondition condition_ = condition.clone();
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Management4> list = getList(condition_);

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
	 * @return ダイアログ画面での検索条件を設定
	 */
	protected Management4SearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition1
	 * @return 科目情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Management4> getList(Management4SearchCondition condition1) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";

				// 会社コードを取得
				List<String> companyCodeList = TCompanyClientUtil.getCodeList(this, getField().getCompanyOrgUnit());

				if (companyCodeList == null || companyCodeList.isEmpty()) {
					// 会社が取得出来ない
					return null;
				}

				condition1.setCompanyCodeList(companyCodeList);
			} else {
				condition1.setCompanyCodeList(null);
			}

			List<Management4> list = (List<Management4>) request(getModelClass(), method, condition1);
			return list;
		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return Class
	 */
	protected Class getModelClass() {
		return Management4Manager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return 管理4情報
	 */
	protected Management4 getSelectedEntity() {
		return (Management4) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return getCompany().getAccountConfig().getManagement4Name();
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getCompany().getAccountConfig().getManagement4Name();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Management4 getEntity() {
		return (Management4) super.getEntity();
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TManagement4Reference getField() {
		return (TManagement4Reference) field;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Management4 bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Management4 entity1 = new Management4();
		entity1.setCode(field.code.getText());
		entity1.setNames(field.name.getText());
		return entity1;
	}

	/**
	 * Entityをセットする
	 * 
	 * @param management 管理
	 */
	public void setEntity(Management4 management) {
		if (management == null) {
			clear();
			return;
		}

		field.code.setText(management.getCode());
		field.name.setText(management.getNames());
		entity = management;
	}
}
