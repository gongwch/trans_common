package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 銀行/支店検索フィールド
 */
public class TBankUnitField extends TPanel {

	/** 銀行 */
	private TBankField bnkField;

	/** 支店 */
	private TBankStnField stnField;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/** 支店未使用モード(表示はする) */
	protected boolean stnEnableMode = true;

	/**
	 * コンストラクタ
	 */
	public TBankUnitField() {
		initComponents();
	}

	/**
	 * 画面初期化
	 */
	private void initComponents() {

		bnkField = new TBankField();
		stnField = new TBankStnField();

		setLayout(new GridBagLayout());

		bnkField.setTabControlNo(0);
		add(bnkField, new GridBagConstraints());

		stnField.setEnabled(false);
		stnField.setTabControlNo(1);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		add(stnField, gridBagConstraints);

		bnkField.addCallControl(new CallBackListener() {

			public void after(boolean flag) {
				if (Util.isNullOrEmpty(bnkField.getValue()) || !flag) {
					stnField.clear();
					stnField.setEnabled(false);
				} else {
					stnField.clear();
					stnField.setEnabled(true);
					stnField.setEditable(true);
					stnField.setBnkCode(bnkField.getValue());

					if (!Util.isNullOrEmpty(stnField.getValue())) {
						stnField.chkExist();
					}
				}
			}
		});
	}

	/**
	 * タブ移動順番号をテキストフィールドに設定する.
	 * 
	 * @param no 番号
	 */
	public void setTabControlNo(int no) {
		bnkField.setTabControlNo(no);
		stnField.setTabControlNo(no);
	}

	/**
	 * クリア
	 */
	public void clear() {
		bnkField.getField().setEditable(true);
		bnkField.getButton().setEnabled(true);
		stnField.getField().setEditable(false);
		stnField.getButton().setEnabled(false);
		bnkField.clear();
		stnField.clear();
	}

	/**
	 * 銀行フィールド取得
	 * 
	 * @return bnkField 銀行フィールド
	 */
	public TBankField getBnkField() {
		return bnkField;
	}

	/**
	 * 支店フィールド取得
	 * 
	 * @return stnField 支店フィールド
	 */
	public TBankStnField getStnField() {
		return stnField;
	}

	/**
	 * 銀行コード設定
	 * 
	 * @param code
	 */
	public void setBnkCode(String code) {
		bnkField.setValue(code);
	}

	/**
	 * 支店コード設定
	 * 
	 * @param code
	 */
	public void setStnCode(String code) {
		stnField.setValue(code);

	}

	/**
	 * 入力フィールドのEditable設定
	 * 
	 * @param isControl true:操作可能
	 */
	public void setEditable(boolean isControl) {
		bnkField.setButtonEnabled(isControl);
		bnkField.setEditable(isControl);
		stnField.setButtonEnabled(isControl);
		stnField.setEditable(isControl);
	}

	/**
	 * stnEnableMode取得
	 * 
	 * @return stnEnableMode
	 */
	public boolean isStnEnableMode() {
		return stnEnableMode;
	}

	/**
	 * stnEnableMode設定
	 * 
	 * @param stnEnableMode
	 */
	public void setStnEnableMode(boolean stnEnableMode) {
		this.stnEnableMode = stnEnableMode;
	}

}