package jp.co.ais.trans2.common.model.ui;

import java.util.List;

import javax.swing.JComponent;

import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.TReferenceController;
import jp.co.ais.trans2.common.gui.TReferenceDialog;
import jp.co.ais.trans2.common.ui.TLoginInfo;
import jp.co.ais.trans2.model.program.SystemDivision;
import jp.co.ais.trans2.model.program.SystemDivisionManager;
import jp.co.ais.trans2.model.program.SystemDivisionSearchCondition;

/**
 * システム区分検索コンポーネント コントローラ
 */
public class TSystemDivisionReferenceController extends TReferenceController {

	/** 検索条件 */
	protected SystemDivisionSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field
	 */
	public TSystemDivisionReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// 検索条件初期化
		initSearchCondition();

	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected SystemDivisionSearchCondition createSearchCondition() {
		return new SystemDivisionSearchCondition();
	}

	/**
	 * コード欄ロストフォーカスイベント
	 */
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
		SystemDivision system = getInputtedEntity();

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
			SystemDivisionSearchCondition condition_ = getCondition().clone();
			condition_.setCodeLike(dialog.code.getText());
			condition_.setNamesLike(dialog.names.getText());
			condition_.setNamekLike(dialog.namek.getText());
			List<SystemDivision> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (SystemDivision bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getName(), bean.getNamek() });
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
				setEntity((SystemDivision) entity);
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
	 * @return Entity
	 */
	protected SystemDivision getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			SystemDivisionSearchCondition inputCondition = new SystemDivisionSearchCondition();
			inputCondition.setCompanyCode(this.condition.getCompanyCode());
			inputCondition.setCode(field.getCode());

			List<SystemDivision> list = getList(inputCondition);

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
	protected SystemDivisionSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return システム情報
	 */
	protected List<SystemDivision> getList(SystemDivisionSearchCondition condition_) {

		try {
			List<SystemDivision> list = (List<SystemDivision>) request(getModelClass(), "get", condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return Class
	 */
	protected Class getModelClass() {
		return SystemDivisionManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return Entity
	 */
	protected SystemDivision getSelectedEntity() {
		return (SystemDivision) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		// システム区分
		return "C00980";
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		// システム区分
		return "C00980";
	}

	/**
	 * Entityをセット
	 * 
	 * @param entity
	 */
	public void setEntity(SystemDivision entity) {
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
	public SystemDivision getEntity() {
		return (SystemDivision) super.getEntity();
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		SystemDivision entity_ = new SystemDivision();
		entity_.setCode(field.code.getText());
		entity_.setNames(field.name.getText());
		return entity_;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		SystemDivision bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
		} else {
			entity = bean;
		}
	}

	/**
	 * コード表示カラムの幅を設定
	 */
	@Override
	public int getCodeColumnSize() {
		return 130;
	}

}
