package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.ItemSearchCondition.SlipInputType;
import jp.co.ais.trans2.model.slip.*;

/**
 * 仕訳明細コントローラ
 * 
 * @author AIS
 */
public class TFormSlipDetailPanelCtrl extends TSlipDetailPanelCtrl {

	/** 選択した行番号 */
	protected int selectRowIndex;

	/** 仕訳明細入力パネルリスト */
	protected List<TFormDCInputPanel> dcList;

	/** 通貨リスト */
	protected List<Currency> currencyList = null;

	/** ヘッダの通貨 */
	protected Currency headerCurrency;

	/** デフォルトが内税 */
	protected String dataType;

	/** 発生日 */
	protected Date occurDate;

	/** 伝票入力タイプ */
	protected SlipInputType slipInputType = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param panel パネル
	 */
	protected TFormSlipDetailPanelCtrl(TFormSlipDetailPanel panel) {
		super(panel);
	}

	@Override
	public TFormSlipDetailPanel getView() {
		return (TFormSlipDetailPanel) panel;
	}

	/**
	 * 画面表示初期処理
	 */
	@Override
	protected void initView() {

		panel.ctrlFcDrTotal.setEditable(false);
		panel.ctrlDrTotal.setEditable(false);
		panel.ctrlFcDiff.setEditable(false);
		panel.ctrlDiff.setEditable(false);
		panel.ctrlFcCrTotal.setEditable(false);
		panel.ctrlCrTotal.setEditable(false);

		panel.cbxCurrencyOnTotal.setEnabled(true);

		panel.ctrlDrTotal.setDecimalPoint(keyDigit);
		panel.ctrlDiff.setDecimalPoint(keyDigit);
		panel.ctrlCrTotal.setDecimalPoint(keyDigit);
	}

	/**
	 * 仕訳明細入力パネルのイベント登録
	 */
	@Override
	protected void addViewEvent() {

		// スプレッド最上位行移動
		panel.btnRowTop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowTop();
			}
		});

		// スプレッド上行移動
		panel.btnRowUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowUp();
			}
		});

		// スプレッド下行移動ボタン
		panel.btnRowDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowDown();
			}
		});

		// スプレッド最下位行移動ボタン
		panel.btnRowUnder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowUnder();
			}
		});

		// 合計欄の通貨コンボボックス
		panel.cbxCurrencyOnTotal.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changedCurrencyOnTotal();
			}
		});

		// 債権消込ボタン
		panel.btnAP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doAPErasing();
			}
		});

		// 債務消込ボタン
		panel.btnAR.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doARErasing();
			}
		});

		// BS勘定消込
		if (isUseBS) {
			panel.btnBS.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					doBS();
				}
			});
		}
	}

	/**
	 * イベント登録
	 * 
	 * @param dcPane 仕訳明細入力パネル
	 */
	protected void addDcEvent(final TFormDCInputPanel dcPane) {

		// 合計計算
		dcPane.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {

				// 合計計算
				summary();
			}
		});

		// 行複写
		dcPane.btnRowCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCopy(dcList.indexOf(dcPane));
			}
		});

		// 反転複写
		dcPane.btnRowCopyReverse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCopyReverse(dcList.indexOf(dcPane));
			}
		});

		// 行追加
		dcPane.btnRowNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doNew(dcList.indexOf(dcPane));
			}
		});

		// 行削除
		dcPane.btnRowDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doDelete(dcList.indexOf(dcPane));
			}
		});

		// 行移動指定
		dcPane.ctrlMoveCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doMoveCheck(dcList.indexOf(dcPane));
			}
		});

		dcPane.ctrlItemDebit.ctrlItemReference.code.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setScrollPosition(dcList.indexOf(dcPane), false);
			}
		});

		dcPane.ctrlItemCredit.ctrlItemReference.code.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setScrollPosition(dcList.indexOf(dcPane), false);
			}
		});

		dcPane.ctrlMoveCheck.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setScrollPosition(dcList.indexOf(dcPane), false);
			}
		});
	}

	/**
	 * 初期表示
	 */
	@Override
	public void init() {
		dcList = new ArrayList<TFormDCInputPanel>();
	}

	/**
	 * 初期表示
	 * 
	 * @param detailCount
	 */
	public void init(int detailCount) {
		dcList = new ArrayList<TFormDCInputPanel>();

		// 明細の初期表示:2行
		for (int i = 0; i < detailCount; i++) {
			TFormDCInputPanel dcPane = createDcPane();
			dcList.add(dcPane);
			addDcEvent(dcPane);
		}

		// 明細をバインドする
		bindList();

		selectRowIndex = -1;

		// 行移動指定ボタンの初期表示
		panel.btnRowTop.setEnabled(false);
		panel.btnRowUp.setEnabled(false);
		panel.btnRowDown.setEnabled(false);
		panel.btnRowUnder.setEnabled(false);

		// 金額合計欄の初期表示
		panel.ctrlFcDrTotal.clear();
		panel.ctrlDrTotal.setNumberValue(BigDecimal.ZERO);
		panel.ctrlFcDiff.clear();
		panel.ctrlDiff.setNumberValue(BigDecimal.ZERO);
		panel.ctrlFcCrTotal.clear();
		panel.ctrlCrTotal.setNumberValue(BigDecimal.ZERO);
		panel.cbxCurrencyOnTotal.removeAllItems();

		// 合計値再計算
		summary();

		// Tab順を変更
		changedTabIndex();
	}

	/**
	 * 明細をバインドする
	 */
	protected void bindList() {

		// 行番号リセット
		refreshRowNo();

		getView().pnlDetail.removeAll();

		int x = getView().X_POINT;

		int y = getView().Y_POINT;

		for (TFormDCInputPanel dcPane : dcList) {

			dcPane.setLocation(x, y);
			getView().pnlDetail.add(dcPane);

			x = dcPane.getX();
			y = dcPane.getY() + dcPane.getHeight() + getView().MARGIN_Y;
		}

		int size = getView().pnlDetail.getComponentCount();
		int width = 1003;
		int height = dcList.get(0).getHeight() * size;
		height += getView().MARGIN_Y * size;

		TGuiUtil.setComponentSize(getView().pnlDetail, width, height);

		getView().pnlDetail.repaint();
	}

	/**
	 * 明細最上位行移動
	 */
	protected void doRowTop() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex = 0;
		dcList.add(selectRowIndex, dcPane);

		// 明細をバインドする
		bindList();

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		if (!panel.btnRowTop.isEnabled()) {

			panel.btnRowDown.requestFocus();
		}

		// Tab順を変更
		changedTabIndex();

		// スクロールの位置を設定
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * 明細上行移動
	 */
	protected void doRowUp() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex--;
		dcList.add(selectRowIndex, dcPane);

		// 明細をバインドする
		bindList();

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();

		// スクロールの位置を設定
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * 明細下行移動
	 */
	protected void doRowDown() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex++;
		dcList.add(selectRowIndex, dcPane);

		// 明細をバインドする
		bindList();

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		if (!panel.btnRowUnder.isEnabled()) {
			panel.cbxCurrencyOnTotal.requestFocus();
		}

		// Tab順を変更
		changedTabIndex();

		// スクロールの位置を設定
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * 明細最下位行移動
	 */
	protected void doRowUnder() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex = dcList.size();
		dcList.add(selectRowIndex, dcPane);

		// 明細をバインドする
		bindList();

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();

		// スクロールの位置を設定
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * 合計欄の通貨変更
	 */
	@Override
	protected void changedCurrencyOnTotal() {

		summaryInAmount();
	}

	/**
	 * 行複写
	 * 
	 * @param rowIndex 行番号
	 */
	protected void doCopy(int rowIndex) {

		TFormDCInputPanel dcPane = createDcPane();

		SWK_DTL dtl = dcList.get(rowIndex).getEntity();

		if (dtl != null) {

			// クローン
			dtl = dtl.clone();

			// AR,AP,BS情報クリア
			dtl.setAR_ZAN(null);
			dtl.setAP_ZAN(null);
			dtl.setBsDetail(null);
		}

		dcPane.setEntity(dtl);

		addDcEvent(dcPane);

		dcList.add(rowIndex + 1, dcPane);

		// 明細をバインドする
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex++;
		}

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();

		// 合計値再計算
		summary();

		// スクロールの位置を設定
		setScrollPosition(rowIndex + 1, true);
	}

	/**
	 * 反転複写
	 * 
	 * @param rowIndex 行番号
	 */
	protected void doCopyReverse(int rowIndex) {

		TFormDCInputPanel dcPane = createDcPane();

		SWK_DTL dtl = dcList.get(rowIndex).getEntity();

		if (dtl != null) {

			// クローン
			dtl = dtl.clone();

			dtl.setDC(dtl.isDR() ? Dc.CREDIT : Dc.DEBIT);

			// AR,AP,BS情報クリア
			dtl.setAR_ZAN(null);
			dtl.setAP_ZAN(null);
			dtl.setBsDetail(null);
		}

		dcPane.setEntity(dtl);

		addDcEvent(dcPane);

		dcList.add(rowIndex + 1, dcPane);

		// 明細をバインドする
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex++;
		}

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();

		// 合計値再計算
		summary();

		// スクロールの位置を設定
		setScrollPosition(rowIndex + 1, true);
	}

	/**
	 * 行追加
	 * 
	 * @param rowIndex 行番号
	 */
	protected void doNew(int rowIndex) {

		TFormDCInputPanel dcPane = createDcPane();
		addDcEvent(dcPane);

		dcList.add(rowIndex + 1, dcPane);

		// 明細をバインドする
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex++;
		}

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();

		// 合計値再計算
		summary();

		// スクロールの位置を設定
		setScrollPosition(rowIndex + 1, true);
	}

	/**
	 * 行削除
	 * 
	 * @param rowIndex 行番号
	 */
	protected void doDelete(int rowIndex) {

		if (!showConfirmMessage("行の削除を行いますか?")) {
			return;
		}

		SWK_DTL bean = dcList.get(rowIndex).getEntity();

		if (bean != null) {
			// 行排他解除
			unlock(bean);
		}

		if (getView().pnlDetail.getComponentCount() == 1) {
			dcList.get(rowIndex).init();
		} else {
			dcList.remove(rowIndex);
		}

		// 明細をバインドする
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex--;

		} else if (rowIndex == selectRowIndex) {
			selectRowIndex = -1;
		}

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();

		// 合計値再計算
		summary();
	}

	/**
	 * 行移動指定
	 * 
	 * @param rowIndex 行番号
	 */
	protected void doMoveCheck(int rowIndex) {

		TFormDCInputPanel dcPane = (TFormDCInputPanel) getView().pnlDetail.getComponent(rowIndex);

		if (dcPane.ctrlMoveCheck.isSelected()) {

			selectRowIndex = rowIndex;

			for (int i = 0; i < getView().pnlDetail.getComponentCount(); i++) {

				if (i != rowIndex) {

					dcPane = (TFormDCInputPanel) getView().pnlDetail.getComponent(i);
					dcPane.ctrlMoveCheck.setSelected(false);
				}
			}

		} else {

			selectRowIndex = -1;
		}

		// 行移動ボタンの制御を行う
		setMoveButtonState();

		// Tab順を変更
		changedTabIndex();
	}

	/**
	 * 行移動ボタンの制御を行う
	 */
	protected void setMoveButtonState() {
		int count = getView().pnlDetail.getComponentCount();

		panel.btnRowTop.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != 0);
		panel.btnRowUp.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != 0);
		panel.btnRowDown.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != count - 1);
		panel.btnRowUnder.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != count - 1);
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨コード
	 */
	@Override
	protected void setCurrecy(Currency currency) {
		headerCurrency = currency;

		for (TFormDCInputPanel dcPane : dcList) {

			dcPane.setCurrecy(currency);
		}

		// 合計値再計算
		summary();
	}

	/**
	 * 基準日設定
	 * 
	 * @param baseDate 基準日
	 */
	@Override
	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setBaseDate(baseDate);
		}
	}

	/**
	 * 決算仕訳設定
	 * 
	 * @param isClosing true:決算仕訳、false:通常仕訳
	 */
	@Override
	public void setClosingEntry(boolean isClosing) {
		this.isClosing = isClosing;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setClosingEntry(isClosing);
		}

		// 合計値再計算
		summary();
	}

	/**
	 * 外部指定取引先を設定(固定表示用)
	 * 
	 * @param customer 外部指定取引先
	 */
	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setCustomer(customer);
		}
	}

	/**
	 * 外部指定社員を設定(初期表示用)
	 * 
	 * @param employee 社員
	 */
	@Override
	public void setEmployee(Employee employee) {
		this.employee = employee;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setEmployee(employee);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel#setDefaultDC(jp.co.ais.trans2.define.Dc)
	 */
	@Override
	public void setDefaultDC(Dc dc) {
		// 処理なし
	}

	/**
	 * デフォルトが内税か
	 * 
	 * @param defaultTaxInnerDivision true:内税
	 */
	@Override
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		this.defaultTaxInnerDivision = defaultTaxInnerDivision;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
		}

		// 合計値再計算
		summary();
	}

	/**
	 * デフォルトがデータ区分か
	 * 
	 * @param dataType データ区分
	 */
	protected void setDataType(String dataType) {
		this.dataType = dataType;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.ctrlRemark.getSearchCondition().setDataType(dataType);
		}
	}

	/**
	 * 明細をEntity形式でセットする.
	 * 
	 * @param details データ
	 */
	@Override
	public void setEntityList(List<SWK_DTL> details) {
		dcList = new ArrayList<TFormDCInputPanel>();

		for (SWK_DTL dtl : details) {

			TFormDCInputPanel dcPane = createDcPane();
			dcList.add(dcPane);
			addDcEvent(dcPane);

			dcPane.setEntity(dtl);
		}

		if (dcList.size() == 0) {
			TFormDCInputPanel dcPane = createDcPane();
			dcList.add(dcPane);
			addDcEvent(dcPane);
		}

		// 明細をバインドする
		bindList();

		selectRowIndex = -1;

		// Tab順を変更
		changedTabIndex();

		// 合計値再計算
		summary();

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				getView().scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}

	/**
	 * 明細をEntity形式で取得する
	 * 
	 * @return SWK_DTLリスト
	 */
	@Override
	public List<SWK_DTL> getEntityList() {

		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		for (TFormDCInputPanel dcPane : dcList) {

			SWK_DTL dtl = dcPane.getEntity();

			if (dtl == null) {
				continue;
			}

			if (isAllowEntryDefaultOccurDate(dtl)) {
				dtl.setHAS_DATE(baseDate); // 発生日＝伝票日付
			}

			list.add(dtl);
		}

		// 外部指定明細もセット
		if (outerDetail != null) {
			list.add(outerDetail);
		}

		return list;
	}

	/**
	 * 明細をEntity形式で取得する
	 * 
	 * @return SWK_DTLリスト
	 */
	public int getDetailRowCount() {
		return getEntityList().size();
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkInput() {

		for (int i = 0; i < dcList.size(); i++) {
			if (!dcList.get(i).checkInput()) {
				setScrollPosition(i, false);
				return false;
			}
		}
		return true;
	}

	/**
	 * 合計欄の通貨コンボボックス構築
	 */
	@Override
	protected void makeCurrencyComboBox() {

		// 選択中の通貨(合計欄)
		Currency selectedCurrency = (Currency) panel.cbxCurrencyOnTotal.getSelectedItemValue();

		Set<Currency> list = new LinkedHashSet<Currency>();

		// ヘッダの通貨
		if (outerDetail != null) {
			list.add(outerDetail.getCurrency());
		}

		// 明細の通貨
		for (TFormDCInputPanel dcPane : dcList) {

			list.add(dcPane.getDebitCurrency());
			list.add(dcPane.getCreditCurrency());
		}

		// 消して追加
		panel.cbxCurrencyOnTotal.removeAllItems();

		for (Currency currency : list) {

			if (currency == null) {
				continue;
			}

			String code = currency.getCode();
			boolean isKey = keyCurrency.getCode().equals(code);

			if (!isKey && !panel.cbxCurrencyOnTotal.containsText(code)) {
				panel.cbxCurrencyOnTotal.addTextValueItem(currency, code);
			}
		}

		if (selectedCurrency != null) {
			panel.cbxCurrencyOnTotal.setSelectedText(selectedCurrency.getCode());
		}
	}

	/**
	 * 合計値再計算
	 */
	@Override
	protected void summary() {

		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;
		BigDecimal drTax = BigDecimal.ZERO;
		BigDecimal crTax = BigDecimal.ZERO;

		for (TFormDCInputPanel dcPane : dcList) {

			SWK_DTL dtl = dcPane.getEntity();

			if (dtl == null) {
				continue;
			}

			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());
			drTax = drTax.add(dtl.getDebitTaxAmount());
			crTax = crTax.add(dtl.getCreditTaxAmount());

			// 外税は合計に消費税プラス
			if (!dtl.isTaxInclusive()) {
				dr = dr.add(dtl.getDebitTaxAmount());
				cr = cr.add(dtl.getCreditTaxAmount());
			}
		}

		// 外部指定あれば足す
		if (outerDetail != null) {
			dr = dr.add(outerDetail.getDebitKeyAmount());
			cr = cr.add(outerDetail.getCreditKeyAmount());
			drTax = drTax.add(outerDetail.getDebitTaxAmount());
			crTax = crTax.add(outerDetail.getCreditTaxAmount());

			// 外税は合計に消費税プラス
			if (!outerDetail.isTaxInclusive()) {
				dr = dr.add(outerDetail.getDebitTaxAmount());
				cr = cr.add(outerDetail.getCreditTaxAmount());
			}
		}

		panel.ctrlDrTotal.setNumberValue(dr);
		panel.ctrlCrTotal.setNumberValue(cr);
		panel.ctrlDrTaxTotal.setNumberValue(drTax);
		panel.ctrlCrTaxTotal.setNumberValue(crTax);

		// 合計欄
		makeCurrencyComboBox();

		// 外貨合計
		summaryInAmount();

	}

	/**
	 * 外貨合計(summary()呼んでから呼ぶ前提)
	 */
	@Override
	protected void summaryInAmount() {
		Map<String, BigDecimal[]> map = getForeignAmountMap();

		// デフォルト
		panel.ctrlFcDrTotal.setValue(null);
		panel.ctrlFcCrTotal.setValue(null);
		panel.ctrlFcDiff.setValue(null);
		panel.ctrlExchangeDiff.setValue(null);

		// 選択通貨
		Currency selectCurrency = (Currency) panel.cbxCurrencyOnTotal.getSelectedItemValue();

		if (selectCurrency != null) {
			int digit = selectCurrency.getDecimalPoint();
			panel.ctrlFcDrTotal.setDecimalPoint(digit);
			panel.ctrlFcCrTotal.setDecimalPoint(digit);
			panel.ctrlFcDiff.setDecimalPoint(digit);

			BigDecimal[] dec = map.get(selectCurrency.getCode());
			if (dec != null) {
				panel.ctrlFcDrTotal.setNumberValue(dec[0]);
				panel.ctrlFcCrTotal.setNumberValue(dec[1]);
				panel.ctrlFcDiff.setNumberValue(dec[0].subtract(dec[1]));
			}
		}

		BigDecimal dr = panel.ctrlDrTotal.getBigDecimal();
		BigDecimal cr = panel.ctrlCrTotal.getBigDecimal();

		// 為替差
		if (panel.cbxCurrencyOnTotal.getItemCount() != 0 && !map.isEmpty()) {
			boolean isFBalance = false;
			for (BigDecimal[] dec : map.values()) {
				if (DecimalUtil.isZero(dec[0]) && DecimalUtil.isZero(dec[1])) {
					continue; // 入力0/0は含めない
				}
				isFBalance = dec[0].compareTo(dec[1]) == 0;

				if (!isFBalance) {
					break;
				}
			}

			if (isFBalance) {
				panel.ctrlExchangeDiff.setNumberValue(dr.subtract(cr));
			}
		}

		panel.ctrlDiff.setNumberValue(panel.ctrlExchangeDiff.getBigDecimal().subtract(dr.subtract(cr)));
	}

	/**
	 * 外貨合計
	 * 
	 * @return 外貨Map
	 */
	@Override
	protected Map<String, BigDecimal[]> getForeignAmountMap() {

		String keyCode = keyCurrency.getCode();

		Map<String, BigDecimal[]> map = new TreeMap<String, BigDecimal[]>();

		for (TFormDCInputPanel dcPane : dcList) {

			SWK_DTL dtl = dcPane.getEntity();

			if (dtl == null) {
				continue;
			}

			// 通貨
			String code = dtl.getSWK_CUR_CODE();

			if (Util.isNullOrEmpty(code) || keyCode.equals(code)) {
				continue;
			}

			BigDecimal[] dec = map.get(code);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(code, dec);
			}

			dec[0] = dec[0].add(dtl.getDebitInputAmount());
			dec[1] = dec[1].add(dtl.getCreditInputAmount());

			// 外税は合計に消費税プラス
			if (!dtl.isTaxInclusive()) {
				dec[0] = dec[0].add(dtl.getDebitTaxInputAmount());
				dec[1] = dec[1].add(dtl.getCreditTaxInputAmount());
			}
		}

		// 外部指定あれば足す
		if (outerDetail != null && !Util.isNullOrEmpty(outerDetail.getSWK_CUR_CODE())
			&& !keyCode.equals(outerDetail.getSWK_CUR_CODE())) {
			String otherCode = outerDetail.getSWK_CUR_CODE();

			BigDecimal[] dec = map.get(otherCode);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(otherCode, dec);
			}

			dec[0] = dec[0].add(outerDetail.getDebitInputAmount()); // DR
			dec[1] = dec[1].add(outerDetail.getCreditInputAmount()); // CR

			// 外税は合計に消費税プラス
			if (!outerDetail.isTaxInclusive()) {
				dec[0] = dec[0].add(outerDetail.getDebitTaxInputAmount());
				dec[1] = dec[1].add(outerDetail.getCreditTaxInputAmount());
			}
		}

		return map;
	}

	/**
	 * Tab順を変更
	 */
	protected void changedTabIndex() {

		int tabControlNo = getView().firstTabIndex;

		for (TFormDCInputPanel pnl : dcList) {
			tabControlNo = pnl.setTabControlNo(tabControlNo++);
		}

		// パネル再度初期化
		getView().parent.resetPanelTabIndex();
	}

	/**
	 * 仕訳明細入力パネルを作成する
	 * 
	 * @return 仕訳明細入力パネル
	 */
	protected TFormDCInputPanel createDcPane() {

		TFormDCInputPanel dcPane = createTFormDCInputPanel();

		dcPane.setBaseDate(baseDate);
		dcPane.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
		dcPane.setClosingEntry(isClosing);

		dcPane.setCurrecyList(currencyList);

		dcPane.ctrlItemDebit.getItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemDebit.getSubItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemDebit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

		dcPane.ctrlItemCredit.getItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemCredit.getSubItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemCredit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

		dcPane.ctrlRemark.getSearchCondition().setDataType(dataType);

		return dcPane;
	}

	/**
	 * @return T-Form 入力Panel
	 */
	protected TFormDCInputPanel createTFormDCInputPanel() {
		return new TFormDCInputPanel();
	}

	/**
	 * スクロールの位置を設定
	 * 
	 * @param rowIndex 行番号
	 * @param isAddRow 行を増加しますか
	 */
	protected void setScrollPosition(int rowIndex, boolean isAddRow) {

		JScrollBar bar = getView().scrollPane.getVerticalScrollBar();

		int eachSize = 0;

		if (isAddRow) {
			eachSize = bar.getMaximum() / (dcList.size() - 1);
			bar.setMaximum(bar.getMaximum() + eachSize);

		} else {
			eachSize = bar.getMaximum() / dcList.size();
		}

		int barValue = bar.getValue();

		// パネルの表示範囲
		int startY = bar.getValue();
		int endY = startY + 4 * eachSize;

		int currentMinY = dcList.get(rowIndex).getY() - getView().Y_POINT;
		int currentMaxY = currentMinY + eachSize;

		if (currentMinY < startY) {
			barValue = rowIndex * eachSize;

		} else if (currentMaxY > endY) {
			barValue = (rowIndex - 3) * eachSize;
		}

		if (barValue < 0) {
			bar.setValue(0);

		} else if (barValue > bar.getMaximum()) {
			bar.setValue(bar.getMaximum());

		} else {
			bar.setValue(barValue);
		}
	}

	/**
	 * 伝票入力タイプ
	 * 
	 * @param slipInputType SlipInputType
	 */
	public void setSlipInputType(SlipInputType slipInputType) {
		this.slipInputType = slipInputType;

		// 伝票種別設定
		for (TFormDCInputPanel pnl : dcList) {
			pnl.ctrlItemDebit.getItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemDebit.getSubItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemDebit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

			pnl.ctrlItemCredit.getItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemCredit.getSubItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemCredit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

			pnl.ctrlRemark.getSearchCondition().setDataType(dataType);
		}
	}

	/**
	 * 明細データの通貨リスト作成
	 * 
	 * @param list 通貨リスト
	 */
	protected void setCurrecyList(List<Currency> list) {
		currencyList = list;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setCurrecyList(list);
		}
	}

	/**
	 * 行番号再設定
	 */
	protected void refreshRowNo() {
		int i = 1;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setRowNo(i++);
		}
	}

	/**
	 * AP残高消込
	 */
	@Override
	public void doAPErasing() {
		TApBalanceListDialogCtrl dialog = createAPBalanceListDialogCtrl();
		dialog.setProgramInfo(getProgramInfo());
		dialog.setNowSlipNo(this.slipNo); // 編集中伝票番号

		if (collectionCustomer != null) {
			dialog.setCustomer(collectionCustomer);

			dialog.dialog.ctrlClientRange.getFieldFrom().setEditable(true);
			dialog.dialog.ctrlClientRange.getFieldTo().setEditable(true);
		}

		// 編集中消込データ
		List<AP_ZAN> eraseList = new ArrayList<AP_ZAN>();
		for (SWK_DTL dtl : getEntityList()) {
			if (dtl.getAP_ZAN() != null) {
				eraseList.add(dtl.getAP_ZAN());
			}
		}

		dialog.setNowEraseList(eraseList);

		if (dialog.show() != TApBalanceListDialogCtrl.OK_OPTION) {
			return;
		}

		// 仕訳行追加
		List<AP_ZAN> list = getSelectedAPBalanceList(dialog);

		for (AP_ZAN zan : list) {

			// 行の追加
			int lastIndex = dcList.size() - 1;
			doNew(lastIndex);

			dcList.get(lastIndex + 1).setEntity(toDetail(zan));
		}

		// 合計の通貨
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * AR残高消込
	 */
	@Override
	public void doARErasing() {
		TArBalanceListDialogCtrl dialog = createARBalanceListDialogCtrl();
		dialog.setProgramInfo(getProgramInfo());
		dialog.setNowSlipNo(this.slipNo); // 編集中伝票番号

		if (collectionCustomer != null) {
			dialog.setCollectionCustomer(collectionCustomer);
		}

		// 編集中消込データ
		List<AR_ZAN> eraseList = new ArrayList<AR_ZAN>();
		for (SWK_DTL dtl : getEntityList()) {
			if (dtl.getAR_ZAN() != null) {
				eraseList.add(dtl.getAR_ZAN());
			}
		}

		dialog.setNowEraseList(eraseList);

		if (dialog.show() != TApBalanceListDialogCtrl.OK_OPTION) {
			return;
		}

		// 仕訳行追加
		List<AR_ZAN> list = getSelectedARBalanceList(dialog);

		for (AR_ZAN zan : list) {

			// 行の追加
			int lastIndex = dcList.size() - 1;
			doNew(lastIndex);

			dcList.get(lastIndex + 1).setEntity(toDetail(zan));
		}

		// 合計の通貨
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * BS勘定ボタン押下
	 */
	@Override
	protected void doBS() {
		BSEraseDialogCtrl dialog = createBSDialog();

		dialog.setBaseDate(this.baseDate); // 基準伝票日付
		dialog.setCurrentSlipNo(this.slipNo); // 現状編集中の伝票番号
		if (this.customer != null) {
			dialog.setCustomer(this.customer); // ヘッダー取引先設定
		} else if (collectionCustomer != null) {
			dialog.setCustomer(collectionCustomer); // 回収取引先設定
		}

		// 編集中消込データ
		List<SWK_DTL> eraseList = new ArrayList<SWK_DTL>();
		for (SWK_DTL dtl : getEntityList()) {
			if (dtl.getBsDetail() != null) {
				eraseList.add(dtl.getBsDetail());
			}
		}

		dialog.setNowEraseList(eraseList);

		if (dialog.show() != BSEraseDialogCtrl.OK_OPTION) {
			return;
		}

		// 仕訳行追加
		List<SWK_DTL> list = getSelectedBSList(dialog);

		for (SWK_DTL bs : list) {

			// 行の追加
			int lastIndex = dcList.size() - 1;
			doNew(lastIndex);

			dcList.get(lastIndex + 1).setEntity(toDetail(bs));
		}

		// 合計の通貨
		makeCurrencyComboBox();

		summary();
	}
}
