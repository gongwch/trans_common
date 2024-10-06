package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 支払条件フィールド
 * 
 * @author roh
 */
public class TPaymentConditionField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = -6991199356860005469L;

	/** コントロール */
	protected TPaymentConditionFieldCtrl ctrl;

	/** 有効期間 */
	private String termBasisDate;

	/** 会社コード */
	private String companyCode;

	/** 取引先コード */
	private String customerCode;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList;

	/**
	 * コンストラクタ
	 */
	public TPaymentConditionField() {
		super();
		ctrl = new TPaymentConditionFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();
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
		this.setLangMessageID("C00238");

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

				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				return setPayConditionFocus();
			}
		});

		// 支払い条件ボタン押下時イベント
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
	}

	/**
	 * ローストフォーカス時
	 * 
	 * @return boolean
	 */
	protected boolean setPayConditionFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setPaymentConditionNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
		return sts;
	}

	/**
	 * 取引先設定
	 * 
	 * @return customerCode 取引先コード
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 取引先設定
	 * 
	 * @param customerCode 取引先
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Callクラスをセットする
	 * 
	 * @param callCtrl CallControlクラス
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
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
		boolean sts = ctrl.setPaymentConditionNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}
}
