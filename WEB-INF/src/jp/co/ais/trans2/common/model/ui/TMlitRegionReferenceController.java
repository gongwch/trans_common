package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * 輸送実績国マスタ検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TMlitRegionReferenceController extends TReferenceController {

	/** 検索条件 */
	protected YJRegionSearchCondition condition;

	/**
	 * @param field 輸送実績国マスタコンポーネント
	 */
	public TMlitRegionReferenceController(TReference field) {
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
	protected YJRegionSearchCondition createSearchCondition() {
		return new YJRegionSearchCondition();
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

			field.name.setText(getEntity().getREGION_NAME());
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
			YJRegionSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setRegionCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setRegionNameLike(dialog.names.getText());

			List<YJRegion> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (YJRegion bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getREGION_CODE(), bean.getREGION_NAME() });
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
				field.code.setText(getEntity().getREGION_CODE());
				field.name.setText(getEntity().getREGION_NAME());
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
		return YJRegionManager.class;
	}

	/**
	 * 入力された輸送実績国マスタを返す
	 * 
	 * @return Entity
	 */
	protected YJRegion getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			YJRegionSearchCondition searchCondition = condition.clone();
			searchCondition.setRegionCode(field.getCode());

			List<YJRegion> list = getList(searchCondition);

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
	 * 検索条件に該当する輸送実績国マスタリストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する輸送実績国マスタリスト
	 */
	protected List<YJRegion> getList(YJRegionSearchCondition condition_) {
		try {
			List<YJRegion> list = (List<YJRegion>) request(getModelClass(), "getRegion", condition_);
			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public YJRegionSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された輸送実績国マスタを返す
	 * 
	 * @return 選択された輸送実績国マスタ
	 */
	protected YJRegion getSelectedEntity() {
		return (YJRegion) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
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
		return getWord("Region Code"); // Region Code
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	@Override
	public String getNameCaption() {
		return getWord("Region Name"); // Region Name
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getWord("Region Code"); // Region Code
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public YJRegion getEntity() {
		return (YJRegion) entity;
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bean 輸送実績国マスタ
	 */
	public void setEntity(YJRegion bean) {
		if (bean == null) {
			clear();
			return;
		}

		field.code.setText(bean.getREGION_CODE());
		field.name.setText(bean.getREGION_NAME());
		entity = bean;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		YJRegion bean = new YJRegion();
		bean.setREGION_CODE(field.code.getText());
		bean.setREGION_NAME(field.name.getText());
		return bean;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		YJRegion bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
			return;
		}
		setEntity(bean);
	}
}
