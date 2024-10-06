package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 銀行検索フィールド
 * 
 * @author roh
 */
public class TBankField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 8690144683548660582L;

	/** コントロール */
	protected TBankFieldCtrl ctrl;

	/** 有効期間 */
	private String termBasisDate;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/** 開始コード */
	private String beginCode;

	/** 終了コード */
	private String endCode;

	/**
	 * コンストラクタ
	 */
	public TBankField() {
		super();
		ctrl = new TBankFieldCtrl(this);

		initComponents();
	}

	/**
	 * 構築
	 */
	private void initComponents() {

		// ロストフォーカス時イベント
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				// 入力不可能の場合、フォーカスイベントは発生しない
				if (!getField().isEditable()) {
					return true;
				}
				// フィールド値を変更しないままだとフォーカスイベントは発生しない
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.termBasisDateFlag = true;
				// 詳細フィールドに指定の内容をセットする。
				// FALSEが返された場合verifyを行わない。
				boolean sts = ctrl.setBankNotice();

				for (CallBackListener listener : callCtrlList) {
					listener.after(sts);
				}
				return sts;
			}
		});

		// ボタンリスナーをつける
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 検索ダイアログを表示する。
				ctrl.showSearchDialog();
			}
		});

		// サイズ初期値
		this.setButtonSize(85);
		this.setFieldSize(75);

		// 入力桁数初期値
		this.setMaxLength(10);
		this.setImeMode(false);
		this.setLangMessageID("C01043");
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
		boolean sts = ctrl.setBankNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after(sts);
		}
	}

	/**
	 * 社員開始取得
	 * 
	 * @return 社員開始
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * 社員開始設定
	 * 
	 * @param beginCode 社員開始
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * 社員終了取得
	 * 
	 * @return 社員終了
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * 社員終了設定
	 * 
	 * @param endCode 社員終了
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
}
