package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 摘要共通フィールド
 * 
 * @author roh
 */
public class TSlipMemoField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = -3179324050026858374L;

	/** コントロール */
	private TSlipMemoFieldCtrl ctrl;

	/** 行摘要と伝票摘要の区分 */
	private int memoType;

	/** 有効期間 */
	private String termBasisDate;

	/** 会社コード */
	private String companyCode;

	/** 摘要データ区分 */
	private String slipDataType;

	/** 伝票摘要 */
	public static final int SLIP_DATA = 0;

	/** 行摘要 */
	public static final int MEMO_DATA = 1;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/**
	 * 摘要共通フィールド
	 * 
	 * @author roh
	 * @param type 摘要区分<br>
	 *            SLIP_DATA : 伝票摘要 MEMO_DATA : 行摘要
	 */

	public TSlipMemoField(int type) {
		super();
		memoType = type;
		ctrl = new TSlipMemoFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();

	}

	/**
	 * 画面構築
	 */

	private void initComponents() {

		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(75);
		// 入力桁数初期値
		this.setMaxLength(10);
		// 入力桁数初期値
		this.setNoticeMaxLength(80);
		// IMEモード
		this.setImeMode(false);
		// Noticeの入力を可にする
		this.setNoticeEditable(true);
		// 伝票摘要の場合
		if (memoType == SLIP_DATA) {
			this.setLangMessageID("C00569");
			// 行摘要の場合
		} else if (memoType == MEMO_DATA) {
			this.setLangMessageID("C00119");
		}

		// ロストフォーカス時
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

				boolean sts = ctrl.slipMomoLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return sts;
			}
		});

		// ボタン押下時
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.btnActionPerformed();
			}
		});

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
	 * 適用区分習得
	 * 
	 * @return memoType 適用区分
	 */
	public int getMemoType() {
		return memoType;
	}

	/**
	 * 適用区分設定
	 * 
	 * @param memoType 適用区分
	 */
	public void setMemoType(int memoType) {
		this.memoType = memoType;
	}

	/**
	 * データタイプ習得
	 * 
	 * @return slipDataType データタイプ
	 */
	public String getSlipDataType() {
		return slipDataType;
	}

	/**
	 * データタイプ設定
	 * 
	 * @param slipDataType データタイプ
	 */
	public void setSlipDataType(String slipDataType) {
		this.slipDataType = slipDataType;
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
	 * CallBackListenerを追加する
	 * 
	 * @param callCtrl CallBackListener
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
		ctrl.slipMomoLostFocus();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}
}
