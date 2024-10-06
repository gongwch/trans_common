package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目体系コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TItemOrganizationReferenceController extends TReferenceController {

	/** 検索条件 */
	protected ItemOrganizationSearchCondition condition;

	/**
	 * @param field 科目体系コンポーネント
	 */
	public TItemOrganizationReferenceController(TReference field) {
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
	protected ItemOrganizationSearchCondition createSearchCondition() {
		return new ItemOrganizationSearchCondition();
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
	 * @return flag
	 */
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
			field.name.setText(getEntity().getNames());
			return true;

		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123"); // 該当コードは存在しません
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
			ItemOrganizationSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<ItemOrganization> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (ItemOrganization bean : list) {
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

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.before();
				}
			}

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
				field.code.pushOldText();
				field.code.getInputVerifier().verify(field.code);
			}

			// ダイアログを閉じる
			dialog.setVisible(false);
			dialog.dispose();

			// 呼び出し元のコードフィールドにフォーカス
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(true);
					listener.afterVerify(true);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ServletのClassを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件を返す
	 */
	protected ItemOrganizationSearchCondition getSearchCondition() {
		return condition;
	}

	/**
	 * 入力された科目体系を返す
	 * 
	 * @return 科目体系
	 */
	protected ItemOrganization getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			ItemOrganizationSearchCondition param = createSearchCondition();
			param.setCompanyCode(this.condition.getCompanyCode());
			param.setCode(field.getCode());

			List<ItemOrganization> list = getList(param);

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
	 * 検索条件に該当する科目体系リストを返す
	 * 
	 * @param param 検索条件
	 * @return 検索条件に該当する科目体系リスト
	 */
	protected List<ItemOrganization> getList(ItemOrganizationSearchCondition param) {

		try {

			List<ItemOrganization> list = (List<ItemOrganization>) request(getModelClass(), "getItemOrganization",
				param);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public ItemOrganizationSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition 検索条件を設定します。
	 */
	public void setCondition(ItemOrganizationSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 選択された科目体系を返す
	 * 
	 * @return 選択された科目体系
	 */
	protected ItemOrganization getSelectedEntity() {
		return (ItemOrganization) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00609"; // 科目体系
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00609"; // 科目体系
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public ItemOrganization getEntity() {
		return (ItemOrganization) super.getEntity();
	}

	/**
	 * 基本科目体系をセットする
	 */
	public void setBaseItemOrganization() {

		try {

			ItemOrganization itemOrganization = (ItemOrganization) request(getModelClass(), "getBaseItemOrganization",
				condition.getCompanyCode());

			setEntity(itemOrganization);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Entityをセットする
	 * 
	 * @param itemOrganization 科目体系
	 */
	public void setEntity(ItemOrganization itemOrganization) {
		if (itemOrganization == null) {
			clear();
			return;
		}

		entity = itemOrganization;
		field.code.setText(itemOrganization.getCode());
		field.name.setText(itemOrganization.getNames());
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		ItemOrganization bean = new ItemOrganization();
		bean.setCode(field.code.getText());
		bean.setNames(field.name.getText());
		return bean;
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	@Override
	public TReferable createEntity() {
		return new ItemOrganization();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		ItemOrganization item = getInputtedEntity();
		if (item == null) {
			this.clear();
		} else {
			entity = item;
		}
	}
}
