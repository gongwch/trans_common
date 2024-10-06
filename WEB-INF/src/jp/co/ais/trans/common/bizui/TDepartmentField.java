package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * 計上部門用フィールド <br>
 * 
 * @author roh
 */
public class TDepartmentField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = -762992297227133680L;

	/** 組織レベル設定 ０ */
	public static final int LVL_0 = 1;

	/** 組織レベル設定 １ */
	public static final int LVL_1 = 2;

	/** 組織レベル設定 ２ */
	public static final int LVL_2 = 3;

	/** 組織レベル設定 ３ */
	public static final int LVL_3 = 4;

	/** 組織レベル設定 ４ */
	public static final int LVL_4 = 5;

	/** 組織レベル設定 ５ */
	public static final int LVL_5 = 6;

	/** 組織レベル設定 ６ */
	public static final int LVL_6 = 7;

	/** 組織レベル設定 ７ */
	public static final int LVL_7 = 8;

	/** 組織レベル設定 ８ */
	public static final int LVL_8 = 9;

	/** 組織レベル設定 ９ */
	public static final int LVL_9 = 10;

	/** 組織レベル設定 なし */
	public static final int NOT_SET = 11;

	/** コントロール */
	private TDepartmentFieldCtrl ctrl;

	/** 会社コード */
	private String companyCode;

	/** 有効期間 */
	private String termBasisDate;

	/** 集計区分 */
	private boolean sumDepartment = true;

	/** 入力区分 */
	private boolean inputDepartment = true;

	/** 組織コード名 */
	private String organization;

	/** 階層レベル */
	private int departmentLevel;

	/** 上位部門 */
	private String upperDepartment;

	/** 開始コード */
	private String beginCode;

	/** 終了コード */
	private String endCode;
	
	/** ロストフォーカス時のコード存在チェック */
	private boolean chekcMode = true;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * コンストラクタ
	 */
	public TDepartmentField() {
		super();
		ctrl = new TDepartmentFieldCtrl(this);
		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {

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

				boolean sts = ctrl.setDepartNotice();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return sts;
			}
		});

		// 部門ボタン押下時イベント
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
		// ボタンサイズ初期値
		this.setButtonSize(85);
		// フィールドサイズ初期値
		this.setFieldSize(75);
		// 入力桁数初期値
		this.setMaxLength(10);
		// IME制御の設定
		this.setImeMode(false);
		// メッセージIDの設定
		this.setLangMessageID("C00467");
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
	 * 階層レベル習得
	 * 
	 * @return departmentLevel 階層レベル
	 */
	public int getDepartmentLevel() {
		return departmentLevel;
	}

	/**
	 * 階層レベル設定
	 * 
	 * @param departmentLevel 階層レベル<br>
	 *            組織上の上位レベル指定<br>
	 *            <br>
	 *            TDepartmentFieldクラスのLVL_1 - LVL9まで定数を使う。<br>
	 *            直接数字を入力する場合指定レベルの+１値をパラメータで渡す。
	 */
	public void setDepartmentLevel(int departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	/**
	 * 入力区分習得
	 * 
	 * @return inputDepartment
	 */
	public boolean isInputDepartment() {
		return inputDepartment;
	}

	/**
	 * 入力区分設定
	 * 
	 * @param inputDepartment 入力部門フラグ <br>
	 *            falseに設定すると集計部門だけを表示する。
	 */
	public void setInputDepartment(boolean inputDepartment) {
		this.inputDepartment = inputDepartment;
	}

	/**
	 * 組織コード習得
	 * 
	 * @return organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * 組織コード設定
	 * 
	 * @param organization 組織検索条件（組織コード）<br>
	 *            組織コードを設定すると,設定いた組織の部門だけが表示される。
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * 集計区分習得
	 * 
	 * @return sumDepartment
	 */
	public boolean isSumDepartment() {
		return sumDepartment;
	}

	/**
	 * 集計区分設定
	 * 
	 * @param sumDepartment 集計部門フラグ<br>
	 *            falseに設定すると入力部門だけを表示する。
	 */
	public void setSumDepartment(boolean sumDepartment) {
		this.sumDepartment = sumDepartment;
	}

	/**
	 * 有効期間設定
	 * 
	 * @return 有効期間
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * 有効期間習得
	 * 
	 * @param termBasisDate 有効期間
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * 上位部門コード習得
	 * 
	 * @return upperDepartment
	 */
	public String getUpperDepartment() {
		return upperDepartment;
	}

	/**
	 * 設定
	 * 
	 * @param upperDepartment 上位部門コード<br>
	 *            上位レベルが設定された場合、上位部門を設定する。<br>
	 *            上位部門コードに当たる部門だけを表示する。
	 */
	public void setUpperDepartment(String upperDepartment) {
		this.upperDepartment = upperDepartment;
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
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @return true: チェックする
	 */
	public boolean isChekcMode() {
		return chekcMode;
	}

	/**
	 * ロストフォーカス時のコード存在チェックを行うかどうか
	 * 
	 * @param chekcMode true: チェックする
	 */
	public void setChekcMode(boolean chekcMode) {
		this.chekcMode = chekcMode;
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

		ctrl.setDepartNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}
}
