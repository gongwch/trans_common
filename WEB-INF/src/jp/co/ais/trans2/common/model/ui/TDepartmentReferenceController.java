package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TDepartmentReferenceController extends TReferenceController {

	/** 検索条件 */
	protected DepartmentSearchCondition condition;

	/**
	 * @param field 部門コンポーネント
	 */
	public TDepartmentReferenceController(TReference field) {
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
	protected DepartmentSearchCondition createSearchCondition() {
		return new DepartmentSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * フィールド[コード]のverify
	 * 
	 * @param comp コンポーネント
	 * @return false:NG
	 */
	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば略称をセット
		if (entity != null) {

			// 検索条件に該当するかをチェック
			DepartmentSearchCondition condition_ = getCondition();

			// 集計部門を除外する条件で集計部門の場合エラー
			if (!condition_.isSumDepartment() && getEntity().isSumDepartment()) {
				showMessage(dialog, "I00154");// 集計部門は指定できません。
				field.code.clearOldText();
				field.code.requestFocus();
				return false;
			}

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
			showMessage(field, "I00123");// 該当コードは存在しません。
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
			DepartmentSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<Department> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Department bean : list) {
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
	 * Classを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * 入力された部門を返す
	 * 
	 * @return Entity
	 */
	protected Department getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			DepartmentSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setSumDepartment(true);
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<Department> list = getList(searchCondition);

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
	 * 検索条件に該当する部門リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する部門リスト
	 */
	@SuppressWarnings("unchecked")
	protected List<Department> getList(DepartmentSearchCondition condition_) {

		String nowCompanyCode = condition_.getCompanyCode();

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";
				condition_.setCompanyCode(null);

				// 会社コードを取得
				List<String> companyCodeList = TCompanyClientUtil.getCodeList(this, getField().getCompanyOrgUnit());

				if (getField().getCompanyOrgUnit() != null) {
					if (companyCodeList == null || companyCodeList.isEmpty()) {
						// 会社が取得出来ない
						return null;
					}
				}

				condition_.setCompanyCodeList(companyCodeList);
			} else {
				condition_.setCompanyCodeList(null);
			}

			List<Department> list = (List<Department>) request(getModelClass(), method, condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		} finally {
			condition_.setCompanyCode(nowCompanyCode);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public DepartmentSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition 検索条件を設定します。
	 */
	public void setCondition(DepartmentSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 選択された部門を返す
	 * 
	 * @return 選択された部門
	 */
	protected Department getSelectedEntity() {
		return (Department) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00467"; // 部門
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00467"; // 部門
	}

	/**
	 * 伝票の参照権限をセットする。<br>
	 * 権限によって部門のフィールド制御する。
	 * 
	 * @param slipRole 伝票参照権限
	 * @param department ユーザーの所属部門
	 */
	public void setSlipRole(SlipRole slipRole, Department department) {

		if (SlipRole.ALL == slipRole) {
			field.setEnabled(true);
		} else {
			setEntity(department);
			field.setEditable(false);
		}
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Department getEntity() {
		return (Department) super.getEntity();
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TDepartmentReference getField() {
		return (TDepartmentReference) field;
	}

	/**
	 * Entityをセットする
	 * 
	 * @param department 部門
	 */
	public void setEntity(Department department) {
		if (department == null) {
			clear();
		} else {
			field.code.setText(department.getCode());
			field.name.setText(department.getNames());
			entity = department;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Department entity_ = createEntity();
		entity_.setCode(field.code.getText());
		entity_.setNames(field.name.getText());
		return entity_;
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	@Override
	public Department createEntity() {
		return new Department();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Department bean = getInputtedEntity();
		setEntity(bean);
	}

}
