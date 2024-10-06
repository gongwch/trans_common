package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 国際収支検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBalanceAccountsReferenceController extends TReferenceController {

	/** 検索条件 */
	protected RemittanceSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TBalanceAccountsReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// 検索名称カラム不要
		setShow3rdColumn(false);

		// 検索条件初期化
		initSearchCondition();
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected RemittanceSearchCondition createSearchCondition() {
		return new RemittanceSearchCondition();
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

			field.code.setText(getEntity().getCode());
			field.name.setText(getEntity().getName());
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
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return 国際収支
	 */
	protected Remittance getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			RemittanceSearchCondition remittanceCondition = condition.clone();
			remittanceCondition.setCode(field.getCode());

			List<Remittance> list = getList(remittanceCondition);

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
			RemittanceSearchCondition con = getCondition().clone();
			// コード前方一致
			con.setCodeLike(dialog.code.getText());
			// 名称あいまい
			con.setKanaLike(dialog.names.getText());

			List<Remittance> list = getList(con);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Remittance bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getName() });
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
				field.name.setText(getEntity().getName());
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
	 * @return ダイアログ画面での検索条件を設定
	 */
	protected RemittanceSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param con
	 * @return 備考Entity
	 */
	protected List<Remittance> getList(RemittanceSearchCondition con) {

		try {
			List<Remittance> list = (List<Remittance>) request(getModelClass(), "get", con);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return モデル
	 */
	protected Class getModelClass() {
		return RemittanceManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return Remark
	 */
	protected Remittance getSelectedEntity() {
		return (Remittance) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C11838"; // 国際収支
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C11838"; // 国際収支
	}

	@Override
	public String getNamesCaption() {
		return getWord(getDialogCaption()) + getWord("C00518"); // XX略称
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Remittance getEntity() {
		return (Remittance) super.getEntity();
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Remittance entity_ = new Remittance();
		entity_.setCode(field.code.getText());
		entity_.setName(field.name.getText());
		return entity_;
	}

	/**
	 * エンティティをセット
	 * 
	 * @param entity エンティティ
	 */
	public void setEntity(Remittance entity) {
		if (entity == null) {
			clear();
			return;
		}

		field.code.setText(entity.getCode());
		field.name.setText(entity.getName());
		this.entity = entity;
	}
}
