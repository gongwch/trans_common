package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 取引先フィールド
 * 
 * @author roh
 */
public class TCustomerField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 945316597939119806L;

	/** コントロール */
	protected TCustomerFieldCtrl ctrl;

	/** 会社コード */
	private String companyCode;

	/** 有効期間 */
	private String termBasisDate;

	/** 仕入先フラグ */
	private boolean siire;

	/** 得意先フラグ */
	private boolean tokui;

	/** 開始コード */
	private String beginCode;

	/** 終了コード */
	private String endCode;

	/** 借入先表示フラグ */
	private boolean isLoan;
	
	/** ロストフォーカス時のコード存在チェック */
	private boolean checkMode = true;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * コンストラクタ
	 */
	public TCustomerField() {
		super();
		ctrl = new TCustomerFieldCtrl(this);
		initComponents();
	}

	/**
	 * 構築
	 */
	private void initComponents() {

		// ロストフォーカス時イベント
		setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}

				return customerLostFocus();
			}
		});

		// 部門ボタン押下時イベント
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.showSearchDialog();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
			}
		});

		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(75);
		// 入力桁数初期値
		this.setMaxLength(10);
		// IME制御の設定
		this.setImeMode(false);
		// メッセージIDの設定
		this.setLangMessageID("C00408");
	}

	/**
	 * ローストフォーカス
	 * 
	 * @return boolean
	 */
	protected boolean customerLostFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setCustomerNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}

		return sts;
	}

	/**
	 * 会社コード習得
	 * 
	 * @return companyCode 会社コード
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
	 * 有効期間習得
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
	 * 仕入先フラグ習得
	 * 
	 * @return 仕入先フラグ
	 */
	public boolean isSiire() {
		return siire;
	}

	/**
	 * 仕入先フラグ設定
	 * 
	 * @param siire 仕入先フラグ<br>
	 *            trueに設定時、仕入先だけを表示する
	 */
	public void setSiire(boolean siire) {
		this.siire = siire;
	}

	/**
	 * 得意先フラグ習得
	 * 
	 * @return 得意先フラグ
	 */
	public boolean isTokui() {
		return tokui;
	}

	/**
	 * 得意先フラグ設定
	 * 
	 * @param tokui 得意先フラグ<br>
	 *            trueに設定時、 得意先だけを表示する。
	 */
	public void setTokui(boolean tokui) {
		this.tokui = tokui;
	}

	/**
	 * 開始コードの取得
	 * 
	 * @return beginCode 開始コード
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * 開始コードの設定
	 * 
	 * @param beginCode
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * 終了コードの取得
	 * 
	 * @return endCode 終了コード
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * 終了コードの設定
	 * 
	 * @param endCode
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * CallBackListenerを追加する
	 * 
	 * @param callCtrl CallBackListener
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}
	
	/**
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @return true: チェックする
	 */
	public boolean isCheckMode() {
		return checkMode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @param chekcMode true: チェックする
	 */
	public void setCheckMode(boolean chekcMode) {
		this.checkMode = chekcMode;
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
		boolean sts = ctrl.setCustomerNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * 借入先フラグ設定<br>
	 * TRUE設定時、ダイアログに借入金が表示される。
	 * 
	 * @param isLoan
	 */
	public void setLoanCustomer(boolean isLoan) {
		this.isLoan = isLoan;
	}

	/**
	 * 借入先フラグ取得
	 * 
	 * @return 借入先フラグ
	 */
	public boolean isLoanCustomer() {
		return isLoan;
	}

}
