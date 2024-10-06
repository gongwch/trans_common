package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 計上会社フィールド
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyField extends TButtonField {

	/** コントロール */
	protected TAppropriateCompanyFieldCtrl ctrl;

	/** 有効期間 */
	protected String termBasisDate;

	/** 開始会社コード */
	protected String strCompanyCode;

	/** 終了会社コード */
	protected String endCompanyCode;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/** 不正値が入力できるかどうか */
	private boolean isFocusOut;

	/** 存在（after用） */
	private boolean isBln = true;

	/** 通貨区分 */
	private boolean curKbn = true;

	/**
	 * コンストラクタ
	 */
	public TAppropriateCompanyField() {
		super();

		initComponents();

		ctrl = new TAppropriateCompanyFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/**
	 * 構築
	 */
	protected void initComponents() {

		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(75);

		this.setNoticeSize(130);

		// 入力桁数初期値
		this.setMaxLength(10);
		// IME制御の設定
		this.setImeMode(false);
		// メッセージIDの設定
		this.setLangMessageID("C01052");

		// 「計上会社」ボタンを押下
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				isBln = true;
				btnCurrencyActionPerformed();
			}
		});

		// 計上会社ロストフォーカス
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				isBln = true;
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}
				return currencyLostFocus();

			}
		});
	}

	/**
	 * フォーカス対応
	 * 
	 * @return boolean
	 */
	protected boolean currencyLostFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean bol = ctrl.setCompanyInfo();

		for (CallBackListener listener : callCtrlList) {
			if (isBln) {
				listener.after();
			}

			if (!isBln) {
				return false;
			}
		}

		return bol;
	}

	/**
	 * ダイアログ検索
	 */
	protected void btnCurrencyActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}
		boolean isChange = ctrl.showSearchDialog();
		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener listener : callCtrlList) {
				if (isBln) {
					listener.after();
				}

			}
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * Callクラスをセットする。<BR
	 * 
	 * @param callCtrl CallControlクラス
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
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

		ctrl.setCompanyInfo();

		for (CallBackListener listener : callCtrlList) {
			if (isBln) {
				listener.after();
			}
			if (!isBln) {
				return;
			}
		}
	}

	/**
	 * 不正値を入力しても不正メッセージを出さないか。
	 * 
	 * @return boolean
	 */
	public boolean isFocusOut() {
		return isFocusOut;
	}

	/**
	 * 不正値入力フラグ設定
	 * 
	 * @param isFocusOut true : 不正値メッセージをださない。
	 */
	public void setFocusOut(boolean isFocusOut) {
		this.isFocusOut = isFocusOut;
	}

	/**
	 * 存在（after用）
	 * 
	 * @param isBln
	 */
	public void setBln(boolean isBln) {
		this.isBln = isBln;
	}

	/**
	 * 通貨区分 true:基軸通貨コードの会社のみ対象 false:全会社対象
	 * 
	 * @param curKbn
	 */
	public void setCurKbn(boolean curKbn) {
		this.curKbn = curKbn;
	}

	/**
	 * 通貨区分の取得
	 * 
	 * @return curKbn
	 */
	public boolean isCurKbn() {
		return curKbn;
	}

	
	/**
	 * 終了会社コードを取得する。
	 * @return String 終了会社コード
	 */
	public String getEndCompanyCode() {
		return endCompanyCode;
	}

	
	/**
	 * 終了会社コードを設定する。
	 * @param endCompanyCode
	 */
	public void setEndCompanyCode(String endCompanyCode) {
		this.endCompanyCode = endCompanyCode;
	}

	
	/**
	 * 開始会社コードを取得する。
	 * @return String 開始会社コード
	 */
	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	
	/**
	 * 開始会社コードを設定する。
	 * @param strCompanyCode
	 */
	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

}
