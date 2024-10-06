package jp.co.ais.trans2.common.model.ui;

import java.util.*;
import javax.swing.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.security.*;

/**
 * プログラムロールマスタ検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TProgramRoleReferenceController extends TReferenceController {

	/** 検索条件 */
	protected ProgramRoleSearchCondition condition;

	/**
	 * @param field フィールド
	 */
	public TProgramRoleReferenceController(TReference field) {
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
	protected ProgramRoleSearchCondition createSearchCondition() {
		return new ProgramRoleSearchCondition();
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
			field.code.setText(getEntity().getCode());
			field.name.setText(getEntity().getNames());
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
			ProgramRoleSearchCondition param = getCondition().clone();
			param.setCodeLike(dialog.code.getText());
			param.setNamesLike(dialog.names.getText());
			param.setNamekLike(dialog.namek.getText());

			List<ProgramRole> list = getList(param);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (ProgramRole bean : list) {
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
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return エンティティ
	 */
	protected ProgramRole getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			ProgramRoleSearchCondition param = new ProgramRoleSearchCondition();
			param.setCompanyCode(this.condition.getCompanyCode());
			param.setCode(field.getCode());

			List<ProgramRole> list = getList(param);

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
	protected ProgramRoleSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param param
	 * @return プログラム情報
	 */
	@SuppressWarnings("unchecked")
	protected List<ProgramRole> getList(ProgramRoleSearchCondition param) {

		try {
			List<ProgramRole> list = (List<ProgramRole>) request(getModelClass(), "get", param);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	protected Class getModelClass() {
		return ProgramRoleManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return エンティティ
	 */
	protected ProgramRole getSelectedEntity() {
		return (ProgramRole) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// PGロール
		return getWord("C11132");
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// PGロール
		return getWord("C11132");
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public ProgramRole getEntity() {
		return (ProgramRole) super.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param programRole プログラムロール
	 */
	public void setEntity(ProgramRole programRole) {
		if (programRole == null) {
			clear();
			return;
		}

		field.code.setText(programRole.getCode());
		field.name.setText(programRole.getNames());
		entity = programRole;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	public Object getUnspecifiedEntity() {
		ProgramRole entity = new ProgramRole();
		entity.setCode(field.code.getText());
		entity.setNames(field.name.getText());
		return entity;
	}

}
