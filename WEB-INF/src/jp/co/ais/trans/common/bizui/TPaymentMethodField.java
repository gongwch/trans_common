package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 支払い方法フィールド
 * 
 * @author roh
 */
public class TPaymentMethodField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 197997362164770458L;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/** コントロール */
	protected TPaymentMethodFieldCtrl ctrl;

	/** 有効期間 */
	private String termBasisDate;

	/** 会社コード */
	private String companyCode;

	/** 支払内部コード */
	private String naiCode;

	/** 検索条件：支払対象区分 */
	private String serchSihKbn;

	/** 検索条件：支払内部コード */
	private String serchNaiCode;

	/** 検索条件：支払内部コード(NOT条件) */
	private String serchNotNaiCode;

	/** 支払方法リスト */
	private List<String> hohCodes;

	/** 支払方法マスタ */
	private AP_HOH_MST bean;

	/**
	 * コンストラクタ
	 */
	public TPaymentMethodField() {
		super();
		ctrl = new TPaymentMethodFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();
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
		this.setLangMessageID("C00233");

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
				boolean sts = ctrl.setPaymentNotice();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return sts;
			}
		});

		// 支払い方法ボタン押下時イベント
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
	 * Callクラスをセットする。
	 * 
	 * @param callCtrl CallControlクラス
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * 会社コード習得
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
	 * 支払内部コードを設定
	 * 
	 * @param naiCode 支払内部コード
	 */
	public void setNaiCode(String naiCode) {
		this.naiCode = naiCode;
	}

	/**
	 * 支払内部コードを取得
	 * 
	 * @return naiCode 支払内部コード
	 */
	public String getNaiCode() {
		return this.naiCode;
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
		ctrl.setPaymentNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}

	/**
	 * 支払方法マスタを取得する
	 * 
	 * @return 支払方法マスタ
	 */
	public AP_HOH_MST getBean() {
		return bean;
	}

	/**
	 * 支払方法マスタを設定する
	 * 
	 * @param bean
	 */
	public void setBean(AP_HOH_MST bean) {
		this.bean = bean;
	}

	/**
	 * 検索条件：支払内部コードを取得する
	 * 
	 * @return 検索条件：支払内部コード
	 */
	public String getSerchNaiCode() {
		return serchNaiCode;
	}

	/**
	 * 検索条件：支払内部コードを設定する
	 * 
	 * @param serchNaiCode
	 */
	public void setSerchNaiCode(String serchNaiCode) {
		this.serchNaiCode = serchNaiCode;
	}

	/**
	 * 検索条件：支払対象区分を取得する
	 * 
	 * @return 検索条件：支払対象区分
	 */
	public String getSerchSihKbn() {
		return serchSihKbn;
	}

	/**
	 * 検索条件：支払対象区分を設定する
	 * 
	 * @param serchSihKbn
	 */
	public void setSerchSihKbn(String serchSihKbn) {
		this.serchSihKbn = serchSihKbn;
	}

	/**
	 * 検索条件：支払内部コード(NOT条件)を取得する
	 * 
	 * @return 検索条件：支払内部コード(NOT条件)
	 */
	public String getSerchNotNaiCode() {
		return serchNotNaiCode;
	}

	/**
	 * 検索条件：支払内部コード(NOT条件)を設定する
	 * 
	 * @param serchNotNaiCode
	 */
	public void setSerchNotNaiCode(String serchNotNaiCode) {
		this.serchNotNaiCode = serchNotNaiCode;
	}

	/**
	 * 支払方法コードリスト
	 * 
	 * @return hohCodes
	 */
	public List<String> getHohCodes() {
		return hohCodes;
	}

	/**
	 * 支払方法コードリスト
	 * 
	 * @param hohCodes
	 */
	public void setHohCodes(List<String> hohCodes) {
		this.hohCodes = hohCodes;
	}

}
