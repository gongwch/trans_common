package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;

/**
 * 管理マスタ検索フィールド
 * 
 * @author moriya
 */
public class TManagementField extends TButtonField {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** コントロールクラス */
	protected TManagementFieldCtrl ctrl;

	/** 会社コード */
	private String companyCode;

	/** 開始コード */
	private String beginCode;

	/** 終了コード */
	private String endCode;

	/** 有効期間のチェック */
	private String termBasisDate;

	/** 管理コード１ */
	public static final int TYPE_MANAGEMENT1 = 1;

	/** 管理コード２ */
	public static final int TYPE_MANAGEMENT2 = 2;

	/** 管理コード３ */
	public static final int TYPE_MANAGEMENT3 = 3;

	/** 管理コード４ */
	public static final int TYPE_MANAGEMENT4 = 4;

	/** 管理コード５ */
	public static final int TYPE_MANAGEMENT5 = 5;

	/** 管理コード６ */
	public static final int TYPE_MANAGEMENT6 = 6;

	/** コントロールタイプをセット */
	private int managementType;

	/** ロストフォーカス時のコード存在チェック */
	private boolean checkMode = true;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * コンストラクタ<br>
	 * 管理コード1～6の設定は引数に渡すパラメータの数値によって決定<br>
	 * 管理1～6：TYPE_MANAGEMENT1～6
	 * 
	 * @param type 管理タイプ
	 */
	public TManagementField(int type) {
		super();

		this.ctrl = new TManagementFieldCtrl(this);

		this.managementType = type;

		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {

		// サイズ初期値
		this.setButtonSize(85);
		this.setFieldSize(75);
		this.setNoticeSize(135);
		this.setImeMode(false);
		this.setMaxLength(10);

		// 会社コード取得
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
		// 管理区分の取得
		boolean[] mngViews = compInfo.isUseManageDivs();
		// 管理名称の取得
		String[] name = compInfo.getManageDivNames();

		switch (managementType) {

			// 管理1(表示区分で表示/非表示を決める)
			case TYPE_MANAGEMENT1:
				if (mngViews[0]) {
					this.button.setText(name[0]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;
			// 管理2(表示区分で表示/非表示を決める)
			case TYPE_MANAGEMENT2:
				if (mngViews[1]) {
					this.button.setText(name[1]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// 管理3(表示区分で表示/非表示を決める)
			case TYPE_MANAGEMENT3:
				if (mngViews[2]) {
					this.button.setText(name[2]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// 管理4(表示区分で表示/非表示を決める)
			case TYPE_MANAGEMENT4:
				if (mngViews[3]) {
					this.button.setText(name[3]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// 管理5(表示区分で表示/非表示を決める)
			case TYPE_MANAGEMENT5:
				if (mngViews[4]) {
					this.button.setText(name[4]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// 管理6(表示区分で表示/非表示を決める)
			case TYPE_MANAGEMENT6:
				if (mngViews[5]) {
					this.button.setText(name[5]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;
		}

		// ボタンを押下時イベント
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.managementMouseClicked();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
			}
		});

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

				boolean sts = ctrl.managementLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
					listener.after(sts);
				}
				return sts;
			}
		});

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

		// 検索処理を実行
		boolean sts = ctrl.managementLostFocus();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * 会社コードの取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * 基準日の取得
	 * 
	 * @return 基準日
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * 基準日の設定
	 * 
	 * @param termBasisDate
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどう
	 * 
	 * @param checkMode true:チェックする false：チェックしない
	 */
	public void setChekcMode(boolean checkMode) {
		this.checkMode = checkMode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどう
	 * 
	 * @return true: チェックする
	 */
	public boolean isCheckMode() {
		return checkMode;
	}

	/**
	 * コントロールタイプをセット
	 * 
	 * @param managementType
	 */
	public void setManagementType(int managementType) {
		this.managementType = managementType;
	}

	/**
	 * コントロールタイプをセット
	 * 
	 * @return タイプ
	 */
	public int getManagementType() {
		return this.managementType;
	}

	/**
	 * Callクラスをセットする。
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

}
