package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 通貨フィールド
 * 
 * @author roh
 */
public class TCurrencyField extends TButtonField {

	/** コントロール */
	protected TCurrencyFieldCtrl ctrl;

	/** 会社コード */
	protected String companyCode;

	/** 有効期間 */
	protected String termBasisDate;

	/** 通貨オブジェクト */
	private int decKeta;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList;

	/** 開始コード */
	private String beginCode;

	/** 終了コード */
	private String endCode;

	/** 不正値が入力できるかどうか */
	private boolean isFocusOut;

	/**
	 * コンストラクタ
	 */
	public TCurrencyField() {
		this(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isShowNotice 略称フィールドを表示するかどうか
	 */
	public TCurrencyField(boolean isShowNotice) {
		super();

		initComponents(isShowNotice);

		ctrl = new TCurrencyFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/**
	 * 構築
	 * 
	 * @param isShowNotice 略称フィールドを表示するかどうか
	 */
	protected void initComponents(boolean isShowNotice) {

		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(40);

		// Noticeはデフォルト表示しない
		this.setNoticeVisible(isShowNotice);
		this.setNoticeSize(isShowNotice ? 120 : 0);

		// 入力桁数初期値
		this.setMaxLength(3);
		// IME制御の設定
		this.setImeMode(false);
		// メッセージIDの設定
		this.setLangMessageID("C00371");

		// 「通貨」ボタンを押下
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnCurrencyActionPerformed();
			}
		});

		// 通貨ロストフォーカス
		setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
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

		boolean bol = ctrl.setCurrencyInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(bol);
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
				listener.after();
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

		boolean bol = ctrl.setCurrencyInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(bol);
		}
	}

	/**
	 * 開始コード取得
	 * 
	 * @return 開始コード
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * 開始コード設定
	 * 
	 * @param beginCode
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * 終了コード取得
	 * 
	 * @return 終了コード
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * 終了コード設定
	 * 
	 * @param endCode
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * @return 小数点以下の桁数
	 */
	public int getDecKeta() {
		return decKeta;
	}

	/**
	 * @param decKeta
	 */
	public void setDecKeta(int decKeta) {
		this.decKeta = decKeta;
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

}
