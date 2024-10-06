package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 通貨換算フィールド
 * 
 * @author moriya
 */
public class TCurrencyExchangeField extends TPanel {

	/** シリアルUID */
	protected static final long serialVersionUID = 1L;

	/** デフォルト */
	public static final int TYPE_NOMAL = 0;

	/** コンポーネント位置調整可能 */
	public static final int TYPE_ADJUSTABLE = 1;

	/** コントロールクラス */
	protected TCurrencyExchangeFieldCtrl ctrl;

	/** ベースパネル */
	protected TPanel pnlBase;

	/** レート基準日付 */
	protected TPopupCalendar rateBaseDate;

	/** 通貨 */
	protected TCurrencyField currencyField;

	/** レート */
	protected TRateNumericField numRate;

	/** 入力金額 */
	protected TINputNumericField numInputAmount;

	/** 基準金額 */
	protected TBaseNumericField numBaseCurrencyAmount;

	/** 選択通貨の少数点以下桁数 */
	protected int digit = 0;

	/** Callクラス */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * コンストラクタ
	 * 
	 * @param rateBaseDate 連動日付コンポーネント
	 */
	public TCurrencyExchangeField(TPopupCalendar rateBaseDate) {
		this(rateBaseDate, TYPE_NOMAL);
	}

	/**
	 * コンストラクタ 各コンポーネントの位置が可変になる場合使用する
	 * 
	 * @param rateBaseDate 連動日付コンポーネント
	 * @param type タイプ
	 */
	public TCurrencyExchangeField(TPopupCalendar rateBaseDate, int type) {
		super();

		this.rateBaseDate = rateBaseDate;

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		switch (type) {
			case TYPE_ADJUSTABLE:
				initVariableComponents();
				break;

			default:
				initComponents();
				break;
		}

		this.ctrl = new TCurrencyExchangeFieldCtrl(this);

		componentEvent();

	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		currencyField = new TCurrencyField();
		numRate = new TRateNumericField();
		numInputAmount = new TINputNumericField();
		numBaseCurrencyAmount = new TBaseNumericField();

		pnlBase.setLayout(new GridBagLayout());
		pnlBase.setMaximumSize(new Dimension(430, 75));
		pnlBase.setMinimumSize(new Dimension(430, 75));
		pnlBase.setPreferredSize(new Dimension(430, 75));

		currencyField.setNoticeSize(0);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlBase.add(currencyField, gridBagConstraints);

		numRate.setFieldHAlignment(2);
		numRate.setFieldSize(130);
		numRate.setLabelHAlignment(3);
		numRate.setLabelSize(36);
		numRate.setLangMessageID("C00556");
		numRate.setMaxLength(15);
		numRate.getField().setNumericFormat(NumberFormatUtil.makeNumberFormat(4));
		numRate.getField().setPositiveOnly(true);
		numRate.getField().setNumber(1);
		numRate.setEditable(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 80, 0, 0);
		pnlBase.add(numRate, gridBagConstraints);

		numInputAmount.setFieldHAlignment(2);
		numInputAmount.setFieldSize(130);
		numInputAmount.setLabelSize(60);
		numInputAmount.setLangMessageID("C00574");
		numInputAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlBase.add(numInputAmount, gridBagConstraints);

		numBaseCurrencyAmount.setFieldHAlignment(2);
		numBaseCurrencyAmount.setFieldSize(130);
		numBaseCurrencyAmount.setLabelSize(60);
		numBaseCurrencyAmount.setLangMessageID("C00576");
		numBaseCurrencyAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		pnlBase.add(numBaseCurrencyAmount, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

	}

	/**
	 * コンポーネント配置が可変になる場合 画面構築
	 */
	protected void initVariableComponents() {

		GridBagConstraints gridBagConstraints;

		currencyField = new TCurrencyField();
		numRate = new TRateNumericField();
		numInputAmount = new TINputNumericField();
		numBaseCurrencyAmount = new TBaseNumericField();

		gridBagConstraints = new GridBagConstraints();
		add(currencyField, gridBagConstraints);

		numRate.setFieldHAlignment(2);
		numRate.setFieldSize(130);
		numRate.setLabelHAlignment(3);
		numRate.setLabelSize(36);
		numRate.setLangMessageID("C00556");
		numRate.setMaxLength(15);
		numRate.getField().setNumericFormat(NumberFormatUtil.makeNumberFormat(4));
		numRate.getField().setPositiveOnly(true);
		numRate.getField().setNumber(1);
		numRate.setEditable(false);
		gridBagConstraints = new GridBagConstraints();
		add(numRate, gridBagConstraints);

		numInputAmount.setFieldHAlignment(2);
		numInputAmount.setFieldSize(130);
		numInputAmount.setLabelSize(60);
		numInputAmount.setLangMessageID("C00574");
		numInputAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		add(numInputAmount, gridBagConstraints);

		numBaseCurrencyAmount.setFieldHAlignment(2);
		numBaseCurrencyAmount.setFieldSize(130);
		numBaseCurrencyAmount.setLabelSize(60);
		numBaseCurrencyAmount.setLangMessageID("C00576");
		numBaseCurrencyAmount.setMaxLength(17);
		gridBagConstraints = new GridBagConstraints();
		add(numBaseCurrencyAmount, gridBagConstraints);
	}

	/**
	 * 各コンポーネントのイベント
	 */
	protected void componentEvent() {

		// 通貨コードロストフォーカス
		currencyField.addCallControl(new CallBackListener() {

			@Override
			public void after(boolean sts) {

				if (sts) {
					ctrl.currencyFieldFocusLost(Util.avoidNull(currencyField.getValue()));
				}

			}
		});

		// レートロストフォーカス
		numRate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				boolean sts = ctrl.numRateLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return sts;

			}
		});

		// 入力金額ロストフォーカス
		numInputAmount.getField().setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.numInputAmountLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return true;
			}
		});

		// 基準金額ロストフォーカス
		numBaseCurrencyAmount.getField().setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.numBaseCurrencyAmountLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return true;
			}
		});
	}

	/**
	 * 通貨フィールド Callクラスをセットする。<BR>
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addCurCallControl(CallBackListener callCtrl) {
		this.currencyField.addCallControl(callCtrl);
	}

	/**
	 * レート Callクラスをセットする。<BR>
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addRateCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * 入力金額 Callクラスをセットする。<BR>
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addInputAmountCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * 基準金額 Callクラスをセットする。<BR>
	 * 
	 * @param callCtrl CallControlクラ
	 */
	public void addBaseCurAmountCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * レート基準日付の取得
	 * 
	 * @return TLabelPopupCalendar
	 */
	public Date getSelectedDate() {
		return rateBaseDate.getValue();
	}

	/**
	 * レート基準日付の設定
	 * 
	 * @param slipDate
	 */
	public void setSelectedDate(Date slipDate) {
		this.rateBaseDate.setValue(slipDate);

		// 通貨検索の有効期限も設定する
		currencyField.setTermBasisDate(DateUtil.toYMDString(rateBaseDate.getValue()));
	}

	/**
	 * コンポーネントを初期化する
	 */
	public void clear() {
		ctrl.initCtrlValue();
	}

	/**
	 * 通貨コードの取得
	 * 
	 * @return TCurrencyField
	 */
	public TCurrencyField getCurrencyField() {
		return currencyField;
	}

	/**
	 * 通貨コードの設定
	 * 
	 * @param currencyField
	 */
	public void setCurrencyField(TCurrencyField currencyField) {
		this.currencyField = currencyField;
	}

	/**
	 * レートの取得
	 * 
	 * @return TLabelNumericField
	 */
	public TLabelNumericField getNumRate() {
		return numRate;
	}

	/**
	 * レートの設定
	 * 
	 * @param numRate
	 */
	public void setNumRate(TRateNumericField numRate) {
		this.numRate = numRate;
	}

	/**
	 * 入力金額の取得
	 * 
	 * @return TLabelNumericField
	 */
	public TLabelNumericField getNumInputAmount() {
		return numInputAmount;
	}

	/**
	 * 入力金額の設定
	 * 
	 * @param numInputAmount
	 */
	public void setNumInputAmount(TINputNumericField numInputAmount) {
		this.numInputAmount = numInputAmount;
	}

	/**
	 * 基準金額の取得
	 * 
	 * @return TLabelNumericField
	 */
	public TLabelNumericField getNumBaseCurrencyAmount() {
		return numBaseCurrencyAmount;
	}

	/**
	 * 基準金額の設定
	 * 
	 * @param numJapaneseCurrencyAmount
	 */
	public void setNumBaseCurrencyAmount(TBaseNumericField numJapaneseCurrencyAmount) {
		this.numBaseCurrencyAmount = numJapaneseCurrencyAmount;
	}

	/**
	 * 小数点桁数を取得
	 * 
	 * @return digit
	 */
	public int getDigit() {
		return this.digit;
	}

	/**
	 * 小数点桁数の設定
	 * 
	 * @param digit
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * パネルの取得
	 * 
	 * @return TPanel
	 */
	public TPanel getBasePanel() {
		return pnlBase;
	}

	/**
	 * パネルの設定
	 * 
	 * @param pnlBase
	 */
	public void setBasePanel(TPanel pnlBase) {
		this.pnlBase = pnlBase;
	}

	/**
	 * 最新換算情報の取得
	 */
	public void setExchangeInfo() {
		ctrl.currencyFieldFocusLost(Util.avoidNull(currencyField.getValue()));
	}

	/**
	 * タブ移動順番号を通貨換算コンポーネント全体に設定する.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		currencyField.setTabControlNo(no);
		numRate.setTabControlNo(no);
		numInputAmount.setTabControlNo(no);
		numBaseCurrencyAmount.setTabControlNo(no);
	}

	/**
	 * 邦貨金額を自動計算で設定する（入力金額ロストフォーカス処理）
	 */
	public void reflectBaseAmountValue() {
		ctrl.numInputAmountLostFocus();
	}

	/**
	 * 入力額クラス
	 */
	public class TINputNumericField extends TLabelNumericField {

		/**
		 * コンストラクタ
		 */
		public TINputNumericField() {
			super();
		}

		/**
		 * @see jp.co.ais.trans.common.gui.TLabelNumericField#setNumberValue(java.lang.Number)
		 */
		@Override
		public void setNumberValue(Number value) {
			getField().setNumber(value);
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.before();
			}
			ctrl.numInputAmountLostFocus();
			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}
		}
	}

	/**
	 * 邦貨額クラス
	 */
	public class TBaseNumericField extends TLabelNumericField {

		/**
		 * コンストラクタ
		 */
		public TBaseNumericField() {
			super();
		}

		/**
		 * @see jp.co.ais.trans.common.gui.TLabelNumericField#setNumberValue(java.lang.Number)
		 */
		@Override
		public void setNumberValue(Number value) {
			getField().setNumber(value);
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.before();
			}

			ctrl.numBaseCurrencyAmountLostFocus();

			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}
		}
	}

	/**
	 * レートクラス
	 */
	public class TRateNumericField extends TLabelNumericField {

		/**
		 * コンストラクタ
		 */
		public TRateNumericField() {
			super();
		}

		/**
		 * @see jp.co.ais.trans.common.gui.TLabelNumericField#setNumberValue(java.lang.Number)
		 */
		@Override
		public void setNumberValue(Number value) {
			getField().setNumber(value);
			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.before();
			}

			ctrl.numRateLostFocus();

			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}
		}
	}
}
