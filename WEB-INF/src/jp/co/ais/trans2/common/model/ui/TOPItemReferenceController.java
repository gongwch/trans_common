package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * SAアイテム検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TOPItemReferenceController extends TReferenceController {

	/** 検索条件 */
	protected OPItemSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TOPItemReferenceController(TReference field) {
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
	protected OPItemSearchCondition createSearchCondition() {
		return new OPItemSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * フィールド[ボタン]押下
	 */
	@Override
	public void btn_Click() {
		if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
			for (TCallBackListener listener : callBackListenerList) {
				listener.before();
			}
		}

		super.btn_Click();
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
			OPItemSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<OPItem> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (OPItem bean : list) {
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
	 * @return SAアイテム情報
	 */
	protected OPItem getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			OPItemSearchCondition condition_ = condition.clone();
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			List<OPItem> list = getList(condition_);

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
	protected OPItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return SAアイテムEntity
	 */
	protected List<OPItem> getList(OPItemSearchCondition condition_) {

		try {
			List<OPItem> list = (List<OPItem>) request(getModelClass(), "get", condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return OPItemManager
	 */
	protected Class getModelClass() {
		return OPItemManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return Entity
	 */
	protected OPItem getSelectedEntity() {
		return (OPItem) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "SAアイテム"; // SAアイテム
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "SAアイテム"; // SAアイテム
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public OPItem getEntity() {
		return (OPItem) super.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param entity エンティティ
	 */
	public void setEntity(OPItem entity) {
		if (entity == null) {
			clear();
			return;
		}

		field.code.setText(entity.getCode());
		field.name.setText(entity.getNames());
		this.entity = entity;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		OPItem bean = getInputtedEntity();
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
		OPItem entity1 = createEntity();
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
	public OPItem createEntity() {
		return new OPItem();
	}

}
