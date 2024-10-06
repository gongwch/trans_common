package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * 補助科目フィールド
 */
public class TSubItemField extends TButtonField {

	protected TSubItemFieldCtrl ctrl;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/** 条件用BEANクラス */
	private AccountItemInputParameter inputParameter;

	/** 結果用BEANクラス */
	protected AccountItemOutputParameter outputParameter;

	/** ロストフォーカス時のコード存在チェック */
	private boolean chekcMode = true;

	/**
	 * Constructor.
	 */
	public TSubItemField() {
		super();

		// サイズ初期値
		this.setButtonSize(85);
		this.setFieldSize(75);

		// 入力桁数初期値
		this.setMaxLength(10);

		this.ctrl = new TSubItemFieldCtrl(this);

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		inputParameter = new AccountItemInputParameter();

		outputParameter = new AccountItemOutputParameter();

		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		// 会社コード取得
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		String name = compInfo.getSubItemName();
		if (!Util.isNullOrEmpty(name)) {
			this.getButton().setText(name);
		} else {
			// 名称が取得できない場合、デフォルト「補助科目」を設定
			setLangMessageID("C00488");
		}

		// 「補助科目」ボタンを押下
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSubItemActionPerformed();
			}
		});

		// 補助科目コードロストフォーカス
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged2()) {
					return true;
				}
				return subItemLostFocus();
			}
		});
	}

	/**
	 * ボタン押下時の処理
	 */
	private void btnSubItemActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean isChange = ctrl.search();

		if (isChange) {
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.after();
			}
		}
	}

	/**
	 * ロストフォーカス時の処理
	 * 
	 * @return boolean
	 */
	protected boolean subItemLostFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setupInfo();

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.after();
			callCtrl.after(sts);
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
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @return true: チェックする
	 */
	public boolean isCheckMode() {
		return chekcMode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @param chekcMode true: チェックする
	 */
	public void setCheckMode(boolean chekcMode) {
		this.chekcMode = chekcMode;
	}

	/**
	 * 条件用BEANクラスをセットする。<BR>
	 * 
	 * @param inputParameter AccountItemInputParameterクラス
	 */
	public void setInputParameter(AccountItemInputParameter inputParameter) {
		this.inputParameter = inputParameter;
	}

	/**
	 * 条件用BEANクラスを取得する。<BR>
	 * 
	 * @return inputParameter AccountItemInputParameterクラス
	 */
	public AccountItemInputParameter getInputParameter() {
		return this.inputParameter;
	}

	/**
	 * 条件用BEANクラスをセットする。<BR>
	 * 
	 * @param outputParameter AccountItemOutputParameterクラス
	 */
	public void setOutputParameter(AccountItemOutputParameter outputParameter) {
		this.outputParameter = outputParameter;
	}

	/**
	 * 条件用BEANクラスを取得する。<BR>
	 * 
	 * @return outputParameter AccountItemOutputParameterクラス
	 */
	public AccountItemOutputParameter getOutputParameter() {
		return this.outputParameter;
	}

	/**
	 * field テキストフィールドに文字列を設定する.
	 * 
	 * @param value 設定文字列
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);

		// 条件用BEANに補助科目ｺｰﾄﾞをセット
		inputParameter.setSubItemCode(value);
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		// 検索処理を実行
		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * 値の内部リセット. 結果セットが更新される.
	 */
	public void resetValue() {
		this.setValue(this.getValue());
	}
}
