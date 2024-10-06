package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 消費税フィールド<br>
 * 
 * @author roh
 */
public class TTaxField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 152234308781802217L;

	/** コントロール */
	protected TTaxFieldCtrl ctrl;

	/** 有効期間 */
	private String termBasisDate;

	/** 会社コード */
	private String companyCode;

	/** 売り上げ区分 */
	private boolean sales;

	/** 仕入れ区分 */
	private boolean purchase;

	/** 対象外区分 */
	private boolean elseTax;

	/** 税レート */
	private Float rate;

	/** 売上仕入区分：仕入 */
	private boolean uskbnPurchase;

	/** 売上仕入区分：売上 */
	private boolean uskbnSales;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/**
	 * 会社コード取得
	 * 
	 * @return companyCode <br>
	 *         会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 対象外区分取得
	 * 
	 * @return 対象外区分
	 */
	public boolean isElseTax() {
		return elseTax;
	}

	/**
	 * 対象外区分取得
	 * 
	 * @param elseTax
	 */
	public void setElseTax(boolean elseTax) {
		this.elseTax = elseTax;
	}

	/**
	 * 仕入れ区分取得
	 * 
	 * @return 仕入れ区分
	 */
	public boolean isPurchase() {
		return purchase;
	}

	/**
	 * 仕入れ区分設定
	 * 
	 * @param purchase
	 */
	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	/**
	 * 売り上げ区分取得
	 * 
	 * @return 売り上げ区分
	 */
	public boolean isSales() {
		return sales;
	}

	/**
	 * 売り上げ区分設定
	 * 
	 * @param sales
	 */
	public void setSales(boolean sales) {
		this.sales = sales;
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
	 * 売上仕入区分：仕入 取得
	 * 
	 * @return 売上仕入区分：仕入
	 */
	public boolean isUsKbnPurchase() {
		return uskbnPurchase;
	}

	/**
	 * 売上仕入区分：仕入 設定
	 * 
	 * @param uskbnPurchase
	 */
	public void setUsKbnPurchase(boolean uskbnPurchase) {
		this.uskbnPurchase = uskbnPurchase;
	}

	/**
	 * 売上仕入区分：売上 取得
	 * 
	 * @return 売上仕入区分：売上
	 */
	public boolean isUsKbnSales() {
		return uskbnSales;
	}

	/**
	 * 売上仕入区分：売上 設定
	 * 
	 * @param uskbnSales
	 */
	public void setUsKbnSales(boolean uskbnSales) {
		this.uskbnSales = uskbnSales;
	}

	/**
	 * 税レート取得
	 * 
	 * @return companyCode <br>
	 *         税レート
	 */
	public Float getRate() {
		return rate;
	}

	/**
	 * 税レート設定
	 * 
	 * @param rate
	 */
	public void setRate(Float rate) {
		this.rate = rate;
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
	 * 消費税フィールド
	 * 
	 * @author roh
	 */
	public TTaxField() {
		super();
		ctrl = new TTaxFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();

	}

	/** 画面構築 */
	private void initComponents() {

		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(25);
		// Noticeサイズ初期値
		this.setNoticeSize(135);
		// 入力桁数の設定
		this.setMaxLength(10);
		// IME制御の設定
		this.setImeMode(false);
		// メッセージIDの設定
		this.setLangMessageID("C00673");

		// ロストフォーカス時イベント
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}
				
				ctrl.termBasisDateFlag = true;
				boolean sts = taxlostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return sts;

			}
		});

		// 税区分ボタン押下時イベント
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.btnActionPerformed();
			}
		});
	}

	/**
	 * ローストフォーカス
	 * 
	 * @return boolean
	 */
	public boolean taxlostFocus() {
		return ctrl.taxLostFocus();
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
		ctrl.termBasisDateFlag = false;
		ctrl.taxLostFocus();

		for (CallBackListener listener : callCtrlList) {
 			listener.after();
		}
	}
}
