package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社の検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TCompanyReferenceController extends TReferenceController {

	/** 検索条件 */
	protected CompanySearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TCompanyReferenceController(TReference field) {
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
	protected CompanySearchCondition createSearchCondition() {
		return new CompanySearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
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
			CompanySearchCondition condition_ = getCondition().clone();
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			List<Company> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Company bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getNames() });
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
			Company newEntity = getSelectedEntity();

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
	protected Company getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			CompanySearchCondition condition_ = condition.clone();
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Company> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				return null;
			}

			return list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @return 検索条件を返す
	 */
	protected CompanySearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return 会社情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Company> getList(CompanySearchCondition condition_) {
		try {
			List<Company> list = (List<Company>) request(CompanyManager.class, "get", condition_);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return entity
	 */
	protected Company getSelectedEntity() {
		return (Company) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00053"; // 会社
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00053"; // 会社
	}

	/**
	 * 検索ダイアログ(会社用)のインスタンスを生成し返す
	 * 
	 * @return 検索ダイアログ
	 */
	@Override
	protected TCompanyReferenceDialog createDialog() {
		return new TCompanyReferenceDialog(this);
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public Company getEntity() {
		return (Company) super.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param company エンティティ
	 */
	public void setEntity(Company company) {
		if (company == null) {
			clear();
			return;
		}

		field.code.setText(company.getCode());
		field.name.setText(company.getNames());
		entity = company;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Company bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
		} else {
			entity = bean;
		}
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Company entity1 = new Company();
		entity1.setCode(field.code.getText());
		entity1.setNames(field.name.getText());
		return entity1;
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	@Override
	public TReferable createEntity() {
		return new Company();
	}

}
