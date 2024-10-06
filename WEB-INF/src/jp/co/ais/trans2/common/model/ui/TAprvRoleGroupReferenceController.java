package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * 承認権限ロールマスタ検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TAprvRoleGroupReferenceController extends TReferenceController {

	/** 検索条件 */
	protected AprvRoleGroupSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TAprvRoleGroupReferenceController(TReference field) {
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
	protected AprvRoleGroupSearchCondition createSearchCondition() {
		return new AprvRoleGroupSearchCondition();
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

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば略称をセット
		if (entity != null) {
			field.code.setText(getEntity().getAPRV_ROLE_GRP_CODE());
			field.name.setText(getEntity().getAPRV_ROLE_GRP_NAME_S());
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
			AprvRoleGroupSearchCondition param = getCondition().clone();
			param.setCodeLike(dialog.code.getText());
			param.setNamesLike(dialog.names.getText());
			param.setNamekLike(dialog.namek.getText());

			List<AprvRoleGroup> list = getList(param);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (AprvRoleGroup bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getAPRV_ROLE_GRP_CODE(), bean.getAPRV_ROLE_GRP_NAME_S(),
						bean.getAPRV_ROLE_GRP_NAME_K() });
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
				field.code.setText(getEntity().getAPRV_ROLE_GRP_CODE());
				field.name.setText(getEntity().getAPRV_ROLE_GRP_NAME_S());
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
	 * @return エンティティ
	 */
	protected AprvRoleGroup getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			AprvRoleGroupSearchCondition param = createSearchCondition();
			param.setCompanyCode(this.condition.getCompanyCode());
			param.setCode(field.getCode());

			List<AprvRoleGroup> list = getList(param);

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
	protected AprvRoleGroupSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param param
	 * @return ロール情報
	 */
	protected List<AprvRoleGroup> getList(AprvRoleGroupSearchCondition param) {

		try {
			List<AprvRoleGroup> list = (List<AprvRoleGroup>) request(getModelClass(), "get", param);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return モデルクラス
	 */
	protected Class getModelClass() {
		return AprvRoleGroupManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return エンティティ
	 */
	protected AprvRoleGroup getSelectedEntity() {
		return (AprvRoleGroup) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// 承認グループ
		return getWord("C12229");
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// 承認グループ
		return getWord("C12229");
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public AprvRoleGroup getEntity() {
		return (AprvRoleGroup) super.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param aprvRole
	 */
	public void setEntity(AprvRoleGroup aprvRole) {
		if (aprvRole == null) {
			clear();
			return;
		}

		field.code.setText(aprvRole.getAPRV_ROLE_GRP_CODE());
		field.name.setText(aprvRole.getAPRV_ROLE_GRP_NAME_S());

		entity = aprvRole;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		AprvRoleGroup bean = new AprvRoleGroup();
		bean.setAPRV_ROLE_GRP_CODE(field.code.getText());
		bean.setAPRV_ROLE_GRP_NAME_S(field.name.getText());
		return bean;
	}

}
