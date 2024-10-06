package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザー検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TUserReferenceController extends TReferenceController {

	/** 検索条件 */
	protected UserSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TUserReferenceController(TReference field) {
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
	protected UserSearchCondition createSearchCondition() {
		return new UserSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	public boolean code_Verify(JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			field.name.setText(null);
			return true;
		}

		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		User user = getInputtedEntity();

		// 値があれば略称をセット
		if (user != null) {
			field.code.setText(user.getCode());
			field.name.setText(user.getNames());
			return true;

		}
		field.name.setText(null);
		if (checkExist) {
			// 該当コードは存在しません
			showMessage(field, "I00123");
			field.code.requestFocus();
			field.code.clearOldText();
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
			UserSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			// データを抽出する
			List<User> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (User bean : list) {
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
			User user = getSelectedEntity();

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (user != null) {
				field.code.setText(user.getCode());
				field.name.setText(user.getNames());
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

			field.code.pushOldText();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return User
	 */
	protected User getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			UserSearchCondition condition1 = new UserSearchCondition();
			condition1.setCompanyCode(this.condition.getCompanyCode());
			condition1.setCode(field.getCode());

			List<User> list = getList(condition1);

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
	 * @return ダイアログ画面での検索条件を設定
	 */
	protected UserSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition1
	 * @return ユーザー情報
	 */
	protected List<User> getList(UserSearchCondition condition1) {

		try {
			List<User> list = (List<User>) request(getModelClass(), "get", condition1);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return UserManager
	 */
	protected Class getModelClass() {
		return UserManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return User
	 */
	protected User getSelectedEntity() {
		return (User) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// "ユーザー"
		return getWord("C00528");
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// "ユーザー"
		return getWord("C00528");
	}

	/**
	 * エンティティをセット
	 * 
	 * @param entity エンティティ
	 */
	public void setEntity(User entity) {
		if (entity == null) {
			clear();
		} else {
			field.code.setText(entity.getCode());
			field.name.setText(entity.getNames());
			this.entity = entity;
		}

		field.code.pushOldText();
		field.name.pushOldText();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		User bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		User entity1 = new User();
		entity1.setCode(field.code.getText());
		entity1.setNames(field.name.getText());
		return entity1;
	}

}
