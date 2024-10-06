package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.item.ItemSearchCondition.SlipInputType;
import jp.co.ais.trans2.model.slip.*;

/**
 * 仕訳明細パネル
 * 
 * @author AIS
 */
public class TFormSlipDetailPanel extends TSlipDetailPanel {

	/** 上開始位置 */
	public final int Y_POINT = 5;

	/** 余白_横 */
	public final int MARGIN_X = 2;

	/** 余白_縦 */
	public final int MARGIN_Y = 5;

	/** ヘッダパネル */
	public TPanel pnlHeader;

	/** ヘッダと明細の境界線 */
	public JSeparator sepSlipHeader;

	/** 明細パネル */
	public TPanel pnlDetail;

	/** 明細のスクロールパネル */
	public JScrollPane scrollPane;

	/** 明細と合計の境界線 */
	public JSeparator sepSlip;

	/** 最初のTAB番号 */
	public int firstTabIndex = 0;

	/** 初期明細件数 */
	protected int detailCount = 2;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親
	 */
	public TFormSlipDetailPanel(TSlipPanel parent) {
		super(parent);
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return コントローラー
	 */
	@Override
	public TFormSlipDetailPanelCtrl createController() {
		return new TFormSlipDetailPanelCtrl(this);
	}

	/**
	 * コントローラ取得
	 * 
	 * @return コントローラ
	 */
	@Override
	public TFormSlipDetailPanelCtrl getController() {
		return (TFormSlipDetailPanelCtrl) controller;
	}

	/**
	 * コンポーネントを初期化する
	 */
	@Override
	public void initComponents() {

		pnlHeader = new TPanel();
		pnlInput = new TPanel();

		btnRowTop = new TButton();
		btnRowUp = new TButton();
		btnRowDown = new TButton();
		btnRowUnder = new TButton();
		pnlSsUDPanel = new TPanel();

		pnlDetail = new TPanel();

		pnlTotalAmount = new TPanel();

		// 外貨
		ctrlFcDrTotal = new TLabelNumericField();
		ctrlFcCrTotal = new TLabelNumericField();
		ctrlFcDiff = new TLabelNumericField();
		cbxCurrencyOnTotal = new TComboBox();

		// 邦貨
		ctrlDrTotal = new TLabelNumericField();
		ctrlExchangeDiff = new TLabelNumericField();
		ctrlCrTotal = new TLabelNumericField();

		// 消費税
		ctrlDrTaxTotal = new TLabelNumericField();
		ctrlCrTaxTotal = new TLabelNumericField();
		ctrlDiff = new TLabelNumericField();

		// 余計ロジックを保証する
		ctrlKCompany = new TCompanyReference();
		ctrlKDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();
		ctrlRemark = new TRemarkReference();

		// ctrlOccurDate = new TLabelPopupCalendar();

		// 換算コンポに
		// ctrlCurrency = new TCurrencyReference();
		// ctrlRate = new TLabelNumericField();
		// ctrlTax = new TTaxReference();
		// ctrlTaxDivision = new TRadioPanel("C01175");

		// ctrlDrCr = new TDrCrPanel();

		// ctrlInputAmount = new TLabelNumericField();
		// ctrlTaxAmount = new TLabelNumericField();
		// ctrlKeyAmount = new TLabelNumericField();

		// ctrlCustomer = new TCustomerReference();
		// ctrlEmployee = new TEmployeeReference();
		// ctrlManage1 = new TManagement1Reference();
		// ctrlManage2 = new TManagement2Reference();
		// ctrlManage3 = new TManagement3Reference();
		// ctrlManage4 = new TManagement4Reference();
		// ctrlManage5 = new TManagement5Reference();
		// ctrlManage6 = new TManagement6Reference();

		// ctrlNonAcDetails = new TNonAccountintDetailUnit();

		// pnlDtlButtons = new TPanel();
		// pnlFreeButtons = new TPanel();
		// pnlRowButtons = new TPanel();

		btnAR = new TButton();
		btnAP = new TButton();
		btnBS = new TButton();

		// btnRowNew = new TButton();
		// btnRowInsert = new TButton();
		// btnRowCopy = new TButton();
		// btnRowDelete = new TButton();
		// btnRowEntry = new TButton();

		// btnRowTop = new TButton();
		// btnRowUp = new TButton();
		// btnRowDown = new TButton();
		// btnRowUnder = new TButton();
		tbl = new TTable();
		// pnlSpreadSheet = new TPanel();

		// pnlSsUDPanel = new TPanel();
		// pnlTotalAmount = new TPanel();

	}

	/**
	 * 明細の配置
	 */
	@Override
	public void allocateSlipDetail() {

		TPanel pnlCenter = new TPanel(new GridBagLayout());

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			this.setLayout(new BorderLayout());

		} else {
			this.setLayout(new GridBagLayout());
		}

		setSize(1100, 800);

		Dimension buttonSize = new Dimension(20, 100);

		pnlHeader.setLayout(null);
		TGuiUtil.setComponentSize(pnlHeader, new Dimension(0, 25));

		int x = 28 + 20;
		int y = 5;
		// 債権残高
		btnAR.setLangMessageID("C01080");
		TGuiUtil.setComponentSize(btnAR, buttonSize);
		btnAR.setLocation(x, y);
		pnlHeader.add(btnAR);

		x += btnAR.getWidth() + MARGIN_X;

		// 債務残高
		btnAP.setLangMessageID("C01084");
		TGuiUtil.setComponentSize(btnAP, buttonSize);
		btnAP.setLocation(x, y);
		pnlHeader.add(btnAP);

		x += btnAP.getWidth() + MARGIN_X;

		// BS勘定
		btnBS.setLangMessageID("C04291");
		TGuiUtil.setComponentSize(btnBS, buttonSize);
		btnBS.setLocation(x, y);
		pnlHeader.add(btnBS);

		x += btnBS.getWidth() + MARGIN_X;

		Dimension size = new Dimension(0, 250);
		pnlInput.setLayout(new GridBagLayout());
		pnlInput.setPreferredSize(size);
		pnlInput.setSize(size);

		// スプレッド上下ボタンパネル
		pnlSsUDPanel.setLayout(null);
		TGuiUtil.setComponentSize(pnlSsUDPanel, new Dimension(28, 0));

		// ボタン
		TGuiUtil.setComponentSize(btnRowTop, new Dimension(20, 22));
		TGuiUtil.setComponentSize(btnRowUp, new Dimension(20, 22));
		TGuiUtil.setComponentSize(btnRowDown, new Dimension(20, 22));
		TGuiUtil.setComponentSize(btnRowUnder, new Dimension(20, 22));

		btnRowTop.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowTop.png"));
		btnRowUp.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUp.png"));
		btnRowDown.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowDown.png"));
		btnRowUnder.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUnder.png"));

		btnRowTop.setLocation(X_POINT * 2, 150);
		btnRowUp.setLocation(btnRowTop.getX(), btnRowTop.getY() + btnRowTop.getHeight());
		btnRowDown.setLocation(btnRowTop.getX(), btnRowUp.getY() + btnRowUp.getHeight());
		btnRowUnder.setLocation(btnRowTop.getX(), btnRowDown.getY() + btnRowDown.getHeight());

		pnlSsUDPanel.add(btnRowTop);
		pnlSsUDPanel.add(btnRowUp);
		pnlSsUDPanel.add(btnRowDown);
		pnlSsUDPanel.add(btnRowUnder);

		GridBagConstraints gc_ = new GridBagConstraints();

		gc_.gridx = 0;
		gc_.gridy = 0;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		pnlInput.add(pnlSsUDPanel, gc_);

		// 明細
		pnlDetail.setLayout(null);
		scrollPane = new JScrollPane(pnlDetail);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		TGuiUtil.setComponentSize(scrollPane, new Dimension(0, 250));
		scrollPane.getVerticalScrollBar().setUnitIncrement(48);
		scrollPane.setLocation(pnlSsUDPanel.getX() + pnlSsUDPanel.getWidth(), 0);

		// 明細項目
		gc_ = new GridBagConstraints();

		gc_.gridx = 1;
		gc_.gridy = 0;
		gc_.weightx = 1.0d;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		pnlInput.add(scrollPane, gc_);

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 0;
		gc_.weightx = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(pnlHeader, gc_);
		} else {
			this.add(pnlHeader, gc_);
		}

		// 境界線
		sepSlipHeader = new JSeparator();
		TGuiUtil.setComponentSize(sepSlipHeader, new Dimension(0, 3));

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 1;
		gc_.weightx = 1.0d;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(sepSlipHeader, gc_);
		} else {
			this.add(sepSlipHeader, gc_);
		}

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 2;
		gc_.weightx = 1.0d;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(pnlInput, gc_);
		} else {
			this.add(pnlInput, gc_);
		}

		// 境界線
		sepSlip = new JSeparator();
		TGuiUtil.setComponentSize(sepSlip, new Dimension(0, 3));

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 3;
		gc_.weightx = 1.0d;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(sepSlip, gc_);
		} else {
			this.add(sepSlip, gc_);
		}

		// 合計金額パネル
		pnlTotalAmount.setLayout(null);
		TGuiUtil.setComponentSize(pnlTotalAmount, new Dimension(0, 80));

		// 借方外貨合計
		ctrlFcDrTotal.setLangMessageID("C10725");
		ctrlFcDrTotal.setLabelSize(100);
		ctrlFcDrTotal.setNumericFormat("#,##0");
		ctrlFcDrTotal.setEditable(false);
		ctrlFcDrTotal.setChangeRedOfMinus(true);
		ctrlFcDrTotal.setLocation(30, 5);
		pnlTotalAmount.add(ctrlFcDrTotal);

		// 外貨差額
		ctrlFcDiff.setLangMessageID("C10727");
		ctrlFcDiff.setLabelSize(50);
		ctrlFcDiff.setNumericFormat("#,##0");
		ctrlFcDiff.setEditable(false);
		ctrlFcDiff.setChangeRedOfMinus(true);
		ctrlFcDiff.setLocation(ctrlFcDrTotal.getX() + ctrlFcDrTotal.getWidth() + 10, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(ctrlFcDiff);

		// 外貨通貨
		cbxCurrencyOnTotal.setSize(new Dimension(50, 20));
		cbxCurrencyOnTotal.setLocation(ctrlFcDiff.getX() + ctrlFcDiff.getWidth() + 1, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(cbxCurrencyOnTotal);

		// 貸方外貨合計
		ctrlFcCrTotal.setLangMessageID("C10726");
		ctrlFcCrTotal.setLabelSize(100);
		ctrlFcCrTotal.setNumericFormat("#,##0");
		ctrlFcCrTotal.setEditable(false);
		ctrlFcCrTotal.setChangeRedOfMinus(true);
		ctrlFcCrTotal.setLocation(ctrlFcDiff.getX() + ctrlFcDiff.getWidth() + 40, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(ctrlFcCrTotal);

		// 借方金額合計
		ctrlDrTotal.setLangMessageID("C01126");
		ctrlDrTotal.setLabelSize(100);
		ctrlDrTotal.setNumericFormat("#,##0");
		ctrlDrTotal.setEditable(false);
		ctrlDrTotal.setChangeRedOfMinus(true);
		ctrlDrTotal.setLocation(ctrlFcDrTotal.getX(), ctrlFcDrTotal.getY() + ctrlFcDrTotal.getHeight() + 1);
		pnlTotalAmount.add(ctrlDrTotal);

		// 為替差
		ctrlExchangeDiff.setLangMessageID("C10728");
		ctrlExchangeDiff.setLabelSize(50);
		ctrlExchangeDiff.setNumericFormat("#,##0");
		ctrlExchangeDiff.setEditable(false);
		ctrlExchangeDiff.setChangeRedOfMinus(true);
		ctrlExchangeDiff.setLocation(ctrlDrTotal.getX() + ctrlDrTotal.getWidth() + 10, ctrlDrTotal.getY());
		pnlTotalAmount.add(ctrlExchangeDiff);

		// 貸方金額合計
		ctrlCrTotal.setLangMessageID("C01229");
		ctrlCrTotal.setLabelSize(100);
		ctrlCrTotal.setNumericFormat("#,##0");
		ctrlCrTotal.setEditable(false);
		ctrlCrTotal.setChangeRedOfMinus(true);
		ctrlCrTotal.setLocation(ctrlExchangeDiff.getX() + ctrlExchangeDiff.getWidth() + 40, ctrlDrTotal.getY());
		pnlTotalAmount.add(ctrlCrTotal);

		// 借方消費税額合計
		ctrlDrTaxTotal.setLangMessageID("C01174");
		ctrlDrTaxTotal.setLabelSize(100);
		ctrlDrTaxTotal.setNumericFormat("#,##0");
		ctrlDrTaxTotal.setEditable(false);
		ctrlDrTaxTotal.setChangeRedOfMinus(true);
		ctrlDrTaxTotal.setLocation(ctrlDrTotal.getX(), ctrlDrTotal.getY() + ctrlDrTotal.getHeight() + 1);
		pnlTotalAmount.add(ctrlDrTaxTotal);

		// 差額
		ctrlDiff.setLangMessageID("C00191");
		ctrlDiff.setLabelSize(50);
		ctrlDiff.setNumericFormat("#,##0");
		ctrlDiff.setEditable(false);
		ctrlDiff.setChangeRedOfMinus(true);
		ctrlDiff.setLocation(ctrlDrTaxTotal.getX() + ctrlDrTaxTotal.getWidth() + 10, ctrlDrTaxTotal.getY());
		pnlTotalAmount.add(ctrlDiff);

		// 貸方消費税額合計
		ctrlCrTaxTotal.setLangMessageID("C01174");
		ctrlCrTaxTotal.setLabelSize(100);
		ctrlCrTaxTotal.setNumericFormat("#,##0");
		ctrlCrTaxTotal.setEditable(false);
		ctrlCrTaxTotal.setChangeRedOfMinus(true);
		ctrlCrTaxTotal.setLocation(ctrlDiff.getX() + ctrlDiff.getWidth() + 40, ctrlDrTaxTotal.getY());
		pnlTotalAmount.add(ctrlCrTaxTotal);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			// 境界線の制御
			JSplitPane spBody = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlCenter, pnlTotalAmount);
			spBody.setOpaque(false);
			pnlTotalAmount.setMinimumSize(new Dimension(0, 0));
			pnlCenter.setMinimumSize(new Dimension(0, 0));
			pnlTotalAmount.setMaximumSize(new Dimension(0, 80));
			spBody.setResizeWeight(1);

			// フッダを隠す為の設定
			TUIManager.switchMaximumMode(spBody);

			this.add(spBody, BorderLayout.CENTER);
		} else {

			gc_.gridx = 0;
			gc_.gridy = 4;
			gc_.weighty = 0d;
			gc_.fill = GridBagConstraints.BOTH;
			gc_.anchor = GridBagConstraints.NORTHWEST;
			gc_.insets = new Insets(0, 0, 0, 0);

			add(pnlTotalAmount, gc_);
		}

	}

	/**
	 * 初期化
	 */
	@Override
	public void init() {
		getController().init(detailCount);
	}

	/**
	 * 初期化
	 * 
	 * @param count
	 */
	public void init(int count) {
		this.detailCount = count;
		getController().init(detailCount);
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨コード
	 */
	public void setCurrecy(Currency currency) {
		getController().setCurrecy(currency);
	}

	/**
	 * 基準日設定
	 * 
	 * @param baseDate 基準日
	 */
	@Override
	public void setBaseDate(Date baseDate) {
		getController().setBaseDate(baseDate);
	}

	/**
	 * 決算仕訳設定
	 * 
	 * @param isClosing true:決算仕訳、false:通常仕訳
	 */
	@Override
	public void setClosingEntry(boolean isClosing) {
		getController().setClosingEntry(isClosing);
	}

	/**
	 * デフォルトが内税か
	 * 
	 * @param defaultTaxInnerDivision true:内税
	 */
	@Override
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		getController().setDefaultTaxInnerDivision(defaultTaxInnerDivision);
	}

	/**
	 * デフォルトがデータ区分か
	 * 
	 * @param dataType データ区分
	 */
	public void setDataType(String dataType) {

		getController().setDataType(dataType);
	}

	/**
	 * 明細をEntity形式でセットする.
	 * 
	 * @param details データ
	 */
	@Override
	public void setEntityList(List<SWK_DTL> details) {
		getController().setEntityList(details);
	}

	/**
	 * 明細をEntity形式で取得する
	 * 
	 * @return SWK_DTLリスト
	 */
	@Override
	public List<SWK_DTL> getEntityList() {
		return getController().getEntityList();
	}

	/**
	 * 明細の行数を取得する
	 * 
	 * @return 明細の行数
	 */
	@Override
	public int getDetailRowCount() {
		return getController().getDetailRowCount();
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	@Override
	public boolean checkInput() {
		return getController().checkInput();
	}

	/**
	 * 伝票入力タイプ
	 * 
	 * @param slipInputType SlipInputType
	 */
	public void setSlipInputType(SlipInputType slipInputType) {
		getController().setSlipInputType(slipInputType);
	}

	/**
	 * 明細の1行目のフォーカスの設定
	 */
	public void setDetailFocus() {
		if (getController().dcList != null && getController().dcList.size() > 0) {
			getController().dcList.get(0).ctrlItemDebit.ctrlItemReference.code.requestFocusInWindow();
		}
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 * @return 最終番号
	 */
	@Override
	public int setTabIndex(int tabControlNo) {

		btnAR.setTabControlNo(tabControlNo++);
		btnAP.setTabControlNo(tabControlNo++);
		btnBS.setTabControlNo(tabControlNo++);

		this.firstTabIndex = tabControlNo;

		for (TFormDCInputPanel dcPane : getController().dcList) {
			tabControlNo = dcPane.setTabControlNo(tabControlNo++);
		}

		tabControlNo += 90000; // 9万余計を持つ

		btnRowTop.setTabControlNo(tabControlNo++);
		btnRowUp.setTabControlNo(tabControlNo++);
		btnRowDown.setTabControlNo(tabControlNo++);
		btnRowUnder.setTabControlNo(tabControlNo++);

		cbxCurrencyOnTotal.setTabControlNo(tabControlNo++);

		return tabControlNo;
	}

	/**
	 * 明細データの通貨リスト作成
	 * 
	 * @param list 通貨リスト
	 */
	public void setCurrecyList(List<Currency> list) {
		getController().setCurrecyList(list);
	}

	/**
	 * @see jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel#setTableKeyName(java.lang.String)
	 */
	@Override
	public void setTableKeyName(String tableKeyName) {
		// 処理なし
	}
}
