package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.program.*;

/**
 * システム検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSystemClassificationReferenceController extends TReferenceController {

	/** 検索条件 */
	protected SystemClassificationSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TSystemClassificationReferenceController(TReference field) {
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
	protected SystemClassificationSearchCondition createSearchCondition() {
		return new SystemClassificationSearchCondition();
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
			field.name.setText(null);
			return true;
		}

		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		SystemClassification system = getInputtedEntity();

		// 値があれば略称をセット
		if (system != null) {
			field.code.setText(system.getCode());
			field.name.setText(system.getName());
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
			SystemClassificationSearchCondition condition_ = getCondition().clone();
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			List<SystemClassification> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (SystemClassification bean : list) {
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
				setEntity((SystemClassification) entity);
				field.code.pushOldText();
			}

			// ダイアログを閉じる
			dialog.setVisible(false);
			dialog.dispose();

			// 呼び出し元のコードフィールドにフォーカス
			field.code.requestFocus();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return
	 */
	protected SystemClassification getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			SystemClassificationSearchCondition condition = new SystemClassificationSearchCondition();
			condition.setCompanyCode(this.condition.getCompanyCode());
			condition.setCode(field.getCode());

			List<SystemClassification> list = getList(condition);

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
	protected SystemClassificationSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 * @return システム情報
	 */
	@SuppressWarnings("unchecked")
	protected List<SystemClassification> getList(SystemClassificationSearchCondition condition) {

		try {
			List<SystemClassification> list = (List<SystemClassification>) request(getModelClass(), "getSystem",
				condition);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	protected Class getModelClass() {
		return ProgramManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return
	 */
	protected SystemClassification getSelectedEntity() {
		return (SystemClassification) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// システム
		return getWord("C11134");
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// システム
		return getWord("C11134");
	}

	/**
	 * Entityをセットする
	 * 
	 * @param entity Entity
	 */
	public void setEntity(SystemClassification entity) {
		this.entity = entity;
		if (entity == null) {
			field.code.setText(null);
			field.name.setText(null);
		} else {
			field.code.setText(entity.getCode());
			field.name.setText(entity.getName());
		}
	}

	/**
	 * Entityを返す
	 * 
	 * @return Entity
	 */
	@Override
	public SystemClassification getEntity() {
		return (SystemClassification) super.getEntity();
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		SystemClassification entity = new SystemClassification();
		entity.setCode(field.code.getText());
		entity.setNames(field.name.getText());
		return entity;
	}

}
