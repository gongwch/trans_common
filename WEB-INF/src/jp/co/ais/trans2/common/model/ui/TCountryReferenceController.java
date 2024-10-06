package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.country.*;

/**
 * 国検索コンポーネントのコントローラ
 */
public class TCountryReferenceController extends TReferenceController {

	/** 検索条件 */
	protected CountrySearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TCountryReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#init()
	 */
	@Override
	public void init() {
		super.init();

		// 検索条件初期化
		condition = createSearchCondition();
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected CountrySearchCondition createSearchCondition() {
		return new CountrySearchCondition();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#code_Verify(javax.swing.JComponent)
	 */
	@Override
	public boolean code_Verify(JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (field.isEmpty()) {
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

			setEntity((Country) entity);
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
			CountrySearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNameLike(dialog.names.getText());

			List<Country> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Country bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getName() });
			}

			// 確定ボタンを押下可能にする
			dialog.btnSettle.setEnabled(true);

			// 1行目を選択
			dialog.tbl.setRowSelection(0);

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
				setEntity((Country) entity);
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
	 * @return 取引先情報
	 */
	protected Country getInputtedEntity() {

		try {
			if (field.isEmpty()) {
				return null;
			}

			CountrySearchCondition condition_ = condition.clone();
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Country> list = getList(condition_);

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
	 * ダイアログ画面での検索条件を設定
	 * 
	 * @return 検索条件
	 */
	protected CountrySearchCondition getCondition() {
		return condition;
	}

	/**
	 * リスト取得
	 * 
	 * @param condition_
	 * @return 取引先Entity
	 */
	protected List<Country> getList(CountrySearchCondition condition_) {

		try {
			List<Country> list = (List<Country>) request(getModelClass(), "get", condition_);
			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * モデル
	 * 
	 * @return モデル
	 */
	protected Class getModelClass() {
		return CountryManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return Entity
	 */
	protected Country getSelectedEntity() {
		return (Country) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getDialogCaption()
	 */
	@Override
	public String getDialogCaption() {
		return "C11415"; // 国
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getTableKeyName()
	 */
	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#getButtonCaption()
	 */
	@Override
	public String getButtonCaption() {
		return "C11415"; // 国
	}

	/**
	 * 2カラムの表示
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isShow3rdColumn() {
		return false;
	}

	/**
	 * 略称のカラムサイズを取得します
	 * 
	 * @return 略称のカラムサイズ
	 */
	@Override
	public int getNamesColumnSize() {
		return 320;
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Country getEntity() {
		return (Country) super.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param entity エンティティ
	 */
	public void setEntity(Country entity) {
		if (entity == null) {
			clear();
			return;
		}

		field.code.setText(entity.getCode());
		field.name.setText(entity.getName());
		this.entity = entity;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		entity = getInputtedEntity();
		setEntity((Country) entity);
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Country entity1 = createEntity();
		entity1.setCode(field.code.getText());
		entity1.setName(field.name.getText());
		return entity1;
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	@Override
	public Country createEntity() {
		return new Country();
	}

}
