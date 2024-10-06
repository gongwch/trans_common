package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * 摘要検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TRemarkReferenceController extends TReferenceController {

	/** 検索条件 */
	protected RemarkSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TRemarkReferenceController(TReference field) {
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
	protected RemarkSearchCondition createSearchCondition() {
		return new RemarkSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	public boolean code_Verify(JComponent comp) {

		// 値がブランクの場合
		if (Util.isNullOrEmpty(field.getCode())) {
			if (!field.name.isEditable()) {
				field.name.clear();
			}
			entity = null;
			return true;
		}

		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば名称をセット
		if (entity != null) {

			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.name.setText(getEntity().getName());
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
	 * * 有効期限チェック
	 * 
	 * @return true:有効期間内、false:有効期間外
	 */
	protected boolean isInValidTerm() {
		if (getCompany().getAccountConfig().isSlipTermCheck()) {
			Date validTerm = condition.getValidTerm();

			if (validTerm != null) {
				Date from = getEntity().getDateFrom();
				Date to = getEntity().getDateTo();
				if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
					|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {

					if (!showConfirmMessage("Q00025", getEntity().getCode())) {
						field.code.requestFocus();
						field.code.clearOldText();
						return false;
					}

					field.code.requestFocus();
				}
			}
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
			RemarkSearchCondition con = getCondition().clone();
			// コード前方一致
			con.setCodeLike(dialog.code.getText());
			// 名称あいまい
			con.setNameLike(dialog.names.getText());
			// 検索名称あいまい
			con.setNamekLike(dialog.namek.getText());

			List<Remark> list = getList(con);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Remark bean : list) {
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
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return 備考情報
	 */
	protected Remark getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			RemarkSearchCondition con = condition.clone();
			con.setCode(field.getCode());
			con.setCodeFrom(null);
			con.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				con.setValidTerm(null);
			}

			List<Remark> list = getList(con);

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
	protected RemarkSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param con
	 * @return 備考Entity
	 */
	protected List<Remark> getList(RemarkSearchCondition con) {

		try {
			List<Remark> list = (List<Remark>) request(getModelClass(), "get", con);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * @return RemarkManager
	 */
	protected Class getModelClass() {
		return RemarkManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return Remark
	 */
	protected Remark getSelectedEntity() {
		return (Remark) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00384"; // 摘要
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00384"; // 摘要
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 名称のキャプション
	 */
	@Override
	public String getNamesCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00518"); // 名称に変更
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00518");
		}
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Remark getEntity() {
		return (Remark) super.getEntity();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Remark bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Remark bean = new Remark();
		bean.setCode(field.code.getText());
		bean.setName(field.name.getText());
		return bean;
	}

	/**
	 * Entityをセットする
	 * 
	 * @param remark 摘要
	 */
	public void setEntity(Remark remark) {
		if (remark == null) {
			clear();
			return;
		}

		field.code.setText(remark.getCode());
		field.name.setText(remark.getName());
		entity = remark;
	}
}
