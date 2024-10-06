package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 銀行口座フィールド
 * 
 * @author roh
 */
public class TBankAccountField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 2496968603353850946L;

	/** コントロール */
	protected TBankAccountFieldCtrl ctrl;

	/** 会社コード */
	private String companyCode;

	/** 社外区分 */
	private boolean outKbn;

	/** 社員支払い区分 */
	private boolean empKbn;

	/** 有効期間 */
	private String termBasisDate;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList;

	/**
	 * コンストラクタ
	 */
	public TBankAccountField() {
		super();
		initComponents();
		ctrl = new TBankAccountFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/**
	 * 構築
	 */
	private void initComponents() {
		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(75);
		// 入力桁数初期値
		this.setMaxLength(10);
		// IME制御の設定
		this.setImeMode(false);
		// メッセージIDの設定
		this.setLangMessageID("C00857");

		// ロストフォーカス
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}

				if (!isValueChanged()) {
					return true;
				}
				return setBankAccountFocus();
			}
		});

		// 部門ボタンを押下
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.showSearchDialog();
			}
		});
	}

	/**
	 * ローストフォーカス時
	 * 
	 * @return boolean
	 */
	protected boolean setBankAccountFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setBankAccountNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}

		return sts;
	}

	/**
	 * Callクラスをセットする。<BR>
	 * 
	 * @param callCtrl CallControlクラス
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 社員支払フラグ取得
	 * 
	 * @return empKbn 社員支払フラグ
	 */
	public boolean isEmpKbn() {
		return empKbn;
	}

	/**
	 * 社員支払フラグ設定
	 * 
	 * @param empKbn 社員支払フラグ<br>
	 *            true設定時、社員支払だけを表示する
	 */
	public void setEmpKbn(boolean empKbn) {
		this.empKbn = empKbn;
	}

	/**
	 * 社外支払区分取得
	 * 
	 * @return outKbn 社外支払
	 */
	public boolean isOutKbn() {
		return outKbn;
	}

	/**
	 * 社外支払区分取得
	 * 
	 * @param outKbn 社外支払<br>
	 *            trueに設定時、社外支払だけを表示する。
	 */
	public void setOutKbn(boolean outKbn) {
		this.outKbn = outKbn;
	}

	/**
	 * 有効期間取得
	 * 
	 * @return 有効期間
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * 有効期間設定
	 * 
	 * @param termBasisDate 有効期間
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
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
		ctrl.setBankAccountNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}

}
