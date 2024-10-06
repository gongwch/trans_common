package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * 輸送実績アイテムマスタ検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TMlitItemReferenceController extends TReferenceController {

	/** 検索条件 */
	protected YJItemSearchCondition condition;

	/**
	 * @param field 輸送実績アイテムマスタコンポーネント
	 */
	public TMlitItemReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// 検索条件初期化
		initSearchCondition();
		setShow3rdColumn(false);
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected YJItemSearchCondition createSearchCondition() {
		return new YJItemSearchCondition();
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
	public boolean code_Verify(JComponent comp) {

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

			field.name.setText(getEntity().getITEM_NAME());
			return true;
		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "W00081");// 該当コードは存在しません
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
			YJItemSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setItemCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setItemNameLike(dialog.names.getText());

			List<YJItem> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (YJItem bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getITEM_CODE(), bean.getITEM_NAME() });
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
				field.code.setText(getEntity().getITEM_CODE());
				field.name.setText(getEntity().getITEM_NAME());
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
		return YJItemManager.class;
	}

	/**
	 * 入力された輸送実績アイテムマスタを返す
	 * 
	 * @return Entity
	 */
	protected YJItem getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			YJItemSearchCondition searchCondition = condition.clone();
			searchCondition.setItemCode(field.getCode());

			List<YJItem> list = getList(searchCondition);

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
	 * 検索条件に該当する輸送実績アイテムマスタリストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する輸送実績アイテムマスタリスト
	 */
	protected List<YJItem> getList(YJItemSearchCondition condition_) {
		try {
			List<YJItem> list = (List<YJItem>) request(getModelClass(), "getItems", condition_);
			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public YJItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された輸送実績アイテムマスタを返す
	 * 
	 * @return 選択された輸送実績アイテムマスタ
	 */
	protected YJItem getSelectedEntity() {
		return (YJItem) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return field.btn.getText() + " ";
	}

	/**
	 * コードのキャプションを取得します
	 * 
	 * @return コードのキャプション
	 */
	@Override
	public String getCodeCaption() {
		return getWord("Item Code"); // Item Code
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	@Override
	public String getNameCaption() {
		return getWord("Item Name"); // Item Name
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getWord("Item Code"); // Item Code
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public YJItem getEntity() {
		return (YJItem) entity;
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bean 輸送実績アイテムマスタ
	 */
	public void setEntity(YJItem bean) {
		if (bean == null) {
			clear();
			return;
		}

		field.code.setText(bean.getITEM_CODE());
		field.name.setText(bean.getITEM_NAME());
		entity = bean;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		YJItem bean = new YJItem();
		bean.setITEM_CODE(field.code.getText());
		bean.setITEM_NAME(field.name.getText());
		return bean;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		YJItem bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
			return;
		}
		setEntity(bean);
	}
}
