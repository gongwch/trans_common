package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 入力者フィールド
 */
public class TInputPersonField extends TButtonField {

	/** シリアルUID */
	private static final long serialVersionUID = -2656930051806872824L;

	private TInputPersonFieldCtrl ctrl;

	/** ロストフォーカス時のコード存在チェック */
	private boolean chekcMode = true;

	/** 入力部門コード */
	private String inputDepartmentCode;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/**
	 * Constructor.
	 */
	public TInputPersonField() {
		super();

		// サイズ初期値
		this.setButtonSize(85);
		this.setFieldSize(75);

		// 入力桁数初期値
		this.setMaxLength(10);

		// IME制御
		this.setImeMode(false);

		ctrl = new TInputPersonFieldCtrl(this);

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		initComponents();

	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		// 利用者情報取得し、値をセット
		ctrl.setInit();

		// ボタン名セット
		this.setLangMessageID("C01278");

		// 「入力者」ボタンを押下
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnEmployeeActionPerformed();
			}
		});

		// 入力者コードロストフォーカス
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged()) {
					return true;
				}
				return employeeLostFocus();
			}
		});
	}

	/**
	 * ボタン押下時の処理
	 */
	private void btnEmployeeActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean isChange = ctrl.search();

		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ロストフォーカス時の処理
	 * 
	 * @return true:正常処理
	 */
	protected boolean employeeLostFocus() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return sts;
	}

	/**
	 * Callクラスをセットする。<BR>
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * 入力部門をセットする
	 * 
	 * @param inputDepartmentCode 入力部門
	 */
	public void setInputDepartmentCode(String inputDepartmentCode) {
		this.inputDepartmentCode = inputDepartmentCode;
	}

	/**
	 * 入力部門を返す
	 * 
	 * @return inputDepartmentCode 入力部門
	 */
	public String getInputDepartmentCode() {
		return this.inputDepartmentCode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどう
	 * 
	 * @return true: チェックする
	 */
	public boolean isChekcMode() {
		return chekcMode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどう
	 * 
	 * @param chekcMode true: チェックする
	 */
	public void setChekcMode(boolean chekcMode) {
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

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		// 検索処理を実行
		ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}
}
