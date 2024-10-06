package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.code.*;

/**
 * Codeの検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TCodeReferenceController extends TReferenceController {

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TCodeReferenceController(TCodeReference field) {
		super(field);
	}

	/**
	 * 検索フィールドで発生するイベントを追加する
	 */
	@Override
	protected void addEvent() {
		super.addEvent();

		List<OP_CODE_MST> list = getList(null, null);
		AutoCompleteUtil.decorate(field, list);
	}

	/**
	 * @return REF
	 */
	protected TCodeReference getField() {
		return (TCodeReference) field;
	}

	@Override
	public boolean code_Verify(JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			clear();
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
			field.code.setText(getEntity().getCODE());
			field.name.setText(getEntity().getCODE_NAME());
			return true;
		}

		// エンティティが存在しない場合は略称クリア
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

			List<OP_CODE_MST> list = getList(dialog.code.getText(), dialog.names.getText());

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (OP_CODE_MST bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCODE(), bean.getCODE_NAME() });
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
			OP_CODE_MST newEntity = getSelectedEntity();

			// 同一のコードが選択された場合は、処理なし
			if (field.code.getText().equals(newEntity.getCODE())) {
				dialog.setVisible(false);
				dialog.dispose();
				field.code.requestFocus();
				return;
			}

			entity = getSelectedEntity();

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (entity != null) {
				field.code.setText(getEntity().getCODE());
				field.name.setText(getEntity().getCODE_NAME());
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
	 * @return entity
	 */
	protected OP_CODE_MST getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			return OPLoginUtil.getCodeMst(getField().isLocal(), getField().getCodeDivision(), getField().getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @param codeLike
	 * @param namesLike
	 * @return Code情報
	 */
	protected List<OP_CODE_MST> getList(String codeLike, String namesLike) {
		try {
			List<OP_CODE_MST> list = OPLoginUtil.getCodeMstList(getField().isLocal(), getField().getCodeDivision(),
				getField().getCodes());

			if (list == null) {
				return null;
			}

			List<OP_CODE_MST> filter = new ArrayList<OP_CODE_MST>();

			for (OP_CODE_MST bean : list) {
				boolean doAdd = true;

				if (!Util.isNullOrEmpty(codeLike)) {
					doAdd &= bean.getCODE().indexOf(codeLike) != -1;
				}
				if (!Util.isNullOrEmpty(namesLike)) {
					doAdd &= bean.getCODE_NAME().indexOf(namesLike) != -1;
				}

				if (doAdd) {
					filter.add(bean);
				}
			}

			return filter;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * マネージャを返す
	 * 
	 * @return CodeManager
	 */
	protected Class getManager() {
		return CodeManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return entity
	 */
	protected OP_CODE_MST getSelectedEntity() {
		return (OP_CODE_MST) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return getWord(getField().getCodeDivision().getCaption()); // Code
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getWord(getField().getCodeDivision().getName()); // Code
	}

	/**
	 * 検索ダイアログ(Code用)のインスタンスを生成し返す
	 * 
	 * @return 検索ダイアログ
	 */
	@Override
	protected TReferenceDialog createDialog() {
		return new TCodeReferenceDialog(this);
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public OP_CODE_MST getEntity() {
		return (OP_CODE_MST) super.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	public void setEntity(OP_CODE_MST bean) {
		if (bean == null) {
			clear();
			return;
		}

		field.code.setText(bean.getCODE());
		field.name.setText(bean.getCODE_NAME());
		entity = bean;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		OP_CODE_MST bean = new OP_CODE_MST();
		bean.setCODE(getField().code.getText());
		bean.setCODE_DIV(getField().getCodeDivision().getValue());
		bean.setCODE_NAME(getField().name.getText());
		return bean;
	}

}
