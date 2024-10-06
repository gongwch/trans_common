package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * 科目フィールド
 */
public class TItemField extends TButtonField {

	/** コントロール */
	protected TItemFieldCtrl ctrl;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/** 条件用パラメータ */
	private AccountItemInputParameter inputParameter;

	/** 結果用パラメータ */
	protected AccountItemOutputParameter outputParameter;

	/** ロストフォーカス時のコード存在チェック */
	private boolean chekcMode = true;

	/**
	 * Constructor.
	 */
	public TItemField() {
		super();

		// サイズ初期値
		this.setButtonSize(85);
		this.setFieldSize(75);

		// 入力桁数初期値
		this.setMaxLength(10);

		// IME制御
		this.setImeMode(false);

		ctrl = new TItemFieldCtrl(this);

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		inputParameter = new AccountItemInputParameter();

		outputParameter = new AccountItemOutputParameter();

		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		// 会社コード取得
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		String name = compInfo.getItemName();
		if (!Util.isNullOrEmpty(name)) {
			this.getButton().setText(name);
		} else {
			// 名称が取得できない場合、デフォルト「科目」を設定
			this.setLangMessageID("C00077");
		}

		// 「科目」ボタンを押下
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnItemActionPerformed();
			}
		});

		// 科目コードロストフォーカス
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged2()) {
					return true;
				}
				return itemLostFocus();
			}
		});
	}

	/**
	 * ボタン押下時の処理
	 */
	private void btnItemActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean isChange = ctrl.search();

		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener listener : callCtrlList) {
				listener.after();
				listener.after(isChange);
			}

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ロストフォーカス時の処理
	 * 
	 * @return true:正常処理
	 */
	public boolean itemLostFocus() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return sts;
	}

	/**
	 * CallBackLisneterの登録
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * 条件パラメータをセットする。
	 * 
	 * @param inputParameter 条件パラメータ
	 */
	public void setInputParameter(AccountItemInputParameter inputParameter) {
		this.inputParameter = inputParameter;
	}

	/**
	 * 条件パラメータを取得する。
	 * 
	 * @return inputParameter 条件パラメータ
	 */
	public AccountItemInputParameter getInputParameter() {
		return this.inputParameter;
	}

	/**
	 * 結果パラメータをセットする。
	 * 
	 * @param outputParameter 結果パラメータ
	 */
	public void setOutputParameter(AccountItemOutputParameter outputParameter) {
		this.outputParameter = outputParameter;
	}

	/**
	 * 結果パラメータを取得する。
	 * 
	 * @return outputParameter 結果パラメータ
	 */
	public AccountItemOutputParameter getOutputParameter() {
		return this.outputParameter;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @return true: チェックする
	 */
	public boolean isCheckMode() {
		return chekcMode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @param chekcMode true: チェックする
	 */
	public void setCheckMode(boolean chekcMode) {
		this.chekcMode = chekcMode;
	}

	/**
	 * field テキストフィールドに文字列を設定する
	 * 
	 * @param value 設定文字列
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);

		// 条件用BEANに科目ｺｰﾄﾞをセット
		inputParameter.setItemCode(value);

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		// 検索処理を実行
		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * 値の内部リセット. 結果セットが更新される.
	 */
	public void resetValue() {
		this.setValue(this.getValue());
	}

	/**
	 * 部門コードの設定.<br>
	 * 現在入力されている科目が指定部門に該当しない<br>
	 * （別の部門コードが固定部門として登録されている）場合、 科目コードをクリアする.
	 * 
	 * @param depCode 部門コード
	 */
	public void setDepartmentCode(String depCode) {
		this.inputParameter.setDepartmentCode(depCode);

		boolean isCheck = this.chekcMode;

		this.chekcMode = false;
		this.ctrl.setupInfo();
		if (!this.outputParameter.isExist()) {
			this.clear();
		}

		this.chekcMode = isCheck;
	}
}
