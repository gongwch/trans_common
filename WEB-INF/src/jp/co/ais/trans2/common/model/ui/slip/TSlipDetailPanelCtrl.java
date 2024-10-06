package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel.SC;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 伝票明細コンポーネントコントローラ
 */
public class TSlipDetailPanelCtrl extends TController {

	/** true:グループ会計＜Client＞ */
	public static boolean groupAccounting = ClientConfig.isFlagOn("trans.slip.group.accounting");

	/** true:計上部門初期ブランク＜Client＞ */
	public static boolean departmentDefaultBlank = ClientConfig.isFlagOn("trans.slip.department.default.blank");

	/** true:社員のデフォルト値ブランク＜Client＞ */
	public static boolean employeeDefaultBlank = ClientConfig.isFlagOn("trans.slip.employee.default.blank");

	/** BS勘定消込機能使うかどうか＜Client＞ */
	public static boolean isUseBS = ClientConfig.isFlagOn("trans.slip.use.bs");

	/** true:BS勘定は計上会社コードの入力値を利用＜Client＞ */
	public static boolean isBsUseKCompany = ClientConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** 行挿入機能使うかどうか＜Client＞ */
	public static boolean isUseRowInsert = ClientConfig.isFlagOn("trans.slip.use.rowinsert");

	/** 行のエクセルダウンロード / アップロード機能を利用するか＜Client＞ */
	public static boolean isUseRowExcel = ClientConfig.isFlagOn("trans.slip.use.row.excel");

	/** 行のエクセルアップロード時行を上書きするか＜Client＞ */
	public static boolean isOverwriteExcel = !ClientConfig.isFlagOn("trans.slip.row.excel.not.overwrite");

	/** true:IFRS区分より発生日は科目の発生日フラグにより制御機能有効＜Client＞ */
	public static boolean allowOccurDateBlank = ClientConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** true:発生日ブランクの場合、デフォルト値を登録する機能有効 */
	public static boolean allowEntryDefaultOccurDate = ClientConfig
		.isFlagOn("trans.slip.detail.entry.default.occurdate");

	/** true:計上会社変更より明細項目最新取得を行う、存在していない場合のみクリア＜Client＞ */
	public static boolean notClearByCompany = ClientConfig.isFlagOn("trans.slip.detail.change.company.no.clear");

	/** 残高コンバーター */
	public static TDetailConverter detailConverter = null;

	/** 明細レイアウト調整 */
	public static TDetailLayoutController detailLayoutController = null;

	/** 債務残高消込機能無効かどうか＜Client＞ */
	public static boolean isNotUseAp = ClientConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** 債権残高消込機能無効かどうか＜Client＞ */
	public static boolean isNotUseAr = ClientConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:発生日のデフォルト値を伝票日付を設定する機能有効 */
	public static boolean allowDefaultOccurDate = ClientConfig.isFlagOn("trans.slip.detail.default.occurdate");

	/** true:計上部門全SPC機能有効＜Client＞ */
	public static boolean isUseAllCompanyDepartment = ClientConfig
		.isFlagOn("trans.slip.detail.use.all.company.department");

	/** 明細入力で行確定を押さずに他ボタンを押したときwarningを出すか否か→true:警告表示 */
	public static boolean inputDetailCheckAlert = ClientConfig.isFlagOn("trans.slip.detail.input.check.alert");

	/** 部門優先順位リスト */
	public static Map<String, String> depPriorityMap = null;

	/** true: 2023INVOICE制度対応を使用する */
	public static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	static {

		try {
			// 残高コンバーター
			Class clazz = Class.forName(ClientConfig.getProperty("trans.slip.detail.converter"));
			detailConverter = (TDetailConverter) clazz.newInstance();
		} catch (Throwable e) {
			detailConverter = new TDetailConverter();
		}

		try {
			// 明細レイアウト調整
			Class clazz = Class.forName(ClientConfig.getProperty("trans.slip.detail.layout.controller"));
			detailLayoutController = (TDetailLayoutController) clazz.newInstance();
		} catch (Throwable e) {
			// 処理なし
		}

		try {
			depPriorityMap = new HashMap<String, String>();

			// 部門を優先させる会社セット

			String[] depList = ClientConfig.getProperties("trans.slip.use.dep.priority.cmp");
			String sep = "#";
			for (String dep : depList) {
				String depCode = dep.split(sep)[0];
				String cmpCode = dep.split(sep)[1];
				depPriorityMap.put(depCode, cmpCode);
			}
		} catch (Throwable e) {
			// 処理なし
		}

	}

	/** パネル */
	protected TSlipDetailPanel panel;

	/** 会計系設定 */
	protected AccountConfig conf = getCompany().getAccountConfig();

	/** 基軸通貨 */
	protected Currency keyCurrency = conf.getKeyCurrency();

	/** 基軸通貨小数点桁数 */
	protected int keyDigit = keyCurrency.getDecimalPoint();

	/** 計算ロジック */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** デフォルトが内税 */
	protected boolean defaultTaxInnerDivision = true;

	/** 貸借のデフォルト値 */
	protected Dc defaultDC = Dc.DEBIT;

	/** 伝票種別 */
	protected SlipType slipType;

	/** 外部指定明細 */
	protected SWK_DTL outerDetail;

	/** 外部指定取引先 */
	protected Customer customer;

	/** 外部指定社員 */
	protected Employee employee;

	/** 回収取引先(債権残高一覧用) */
	protected Customer collectionCustomer;

	/** 基準日(伝票日付) */
	protected Date baseDate;

	/** 決算仕訳(true:決算仕訳) */
	protected boolean isClosing;

	/** 伝票伝票番号(編集の場合) */
	protected String slipNo;

	/** 画面反映している明細 */
	protected SWK_DTL dispatchDetail;

	/** 行編集済 */
	protected boolean isEditedDetail = false;

	/** 選択前の行番号を退避 */
	protected int beforeGyoNo = -1;

	/** 行選択処理中 */
	protected boolean isProcess = false;

	/** true: 2023INVOICE制度対応を使用する(会社情報含む) */
	public boolean isInvoice = false;

	/** 発生日チェックを使用するか */
	protected boolean isUseHasDateChk = conf.isUseHasDateCheck();

	/** ﾚｰﾄキャッシュマップ */
	protected Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

	
	/**
	 * コンストラクタ.
	 * 
	 * @param panel
	 */
	public TSlipDetailPanelCtrl(TSlipDetailPanel panel) {

		// インボイス使用するかどうか
		if (isInvoiceTaxProperty) {
			initInvoiceFlg();
		}

		this.panel = panel;

		// 初期画面制御
		initView();

		// イベント登録
		addViewEvent();

		// 初期表示
		init();
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getView()
	 */
	@Override
	public TSlipDetailPanel getView() {
		return panel;
	}

	/**
	 * 画面表示初期処理
	 */
	protected void initView() {

		beforeGyoNo = -1;

		// 発生日
		Date occurDate = panel.ctrlOccurDate.getValue();

		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		// 計上会社 条件
		panel.ctrlKCompany.getSearchCondition().setGroupAccountOnly(); // グループ会社のみ

		// グループ会計制御
		if (isGroupAccounting()) {
			panel.ctrlKCompany.getSearchCondition().setKeyCurrencyCode(null); // 基軸通貨が異なっても選択可能
		} else {
			panel.ctrlKCompany.getSearchCondition().setKeyCurrencyCode(conf.getKeyCurrency().getCode()); // 基軸通貨が同じ
		}

		// 摘要 条件
		panel.ctrlRemark.getSearchCondition().setSlipRemark(false);
		panel.ctrlRemark.getSearchCondition().setSlipRowRemark(true);

		// 管理１～6利用するか
		panel.ctrlManage1.setVisible(conf.isUseManagement1());
		panel.ctrlManage2.setVisible(conf.isUseManagement2());
		panel.ctrlManage3.setVisible(conf.isUseManagement3());
		panel.ctrlManage4.setVisible(conf.isUseManagement4());
		panel.ctrlManage5.setVisible(conf.isUseManagement5());
		panel.ctrlManage6.setVisible(conf.isUseManagement6());

		// コンポーネントの非表示に合わせて入力フィールドパネルの大きさを調整
		if (!conf.isUseManagement1() && !conf.isUseNotAccounting3()) {
			int h = panel.ctrlEmployee.getY() + panel.ctrlEmployee.getHeight() + 2;
			TGuiUtil.setComponentSize(panel.pnlInput, new Dimension(0, h));

		} else if (!conf.isUseManagement2()) {
			int h = panel.ctrlManage1.getY() + panel.ctrlManage1.getHeight() + 2;
			TGuiUtil.setComponentSize(panel.pnlInput, new Dimension(0, h));
		}

		// スプレッド設定
		initSpreadSheet();

		// 基軸通貨の小数点設定
		panel.ctrlInputAmount.setDecimalPoint(keyDigit);
		panel.ctrlTaxAmount.setDecimalPoint(keyDigit);
		panel.ctrlKeyAmount.setDecimalPoint(keyDigit);

		panel.ctrlCrTotal.setDecimalPoint(keyDigit);
		panel.ctrlDrTotal.setDecimalPoint(keyDigit);
		panel.ctrlExchangeDiff.setDecimalPoint(keyDigit);

		panel.ctrlCrTaxTotal.setDecimalPoint(keyDigit);
		panel.ctrlDrTaxTotal.setDecimalPoint(keyDigit);
		panel.ctrlDiff.setDecimalPoint(keyDigit);

		// 債務残高機能無効
		if (isNotUseAp) {
			panel.btnAP.setVisible(false);
		}

		// 債権残高機能無効
		if (isNotUseAr) {
			panel.btnAR.setVisible(false);
		}

		// BS勘定消込
		if (!isUseBS) {
			panel.btnBS.setVisible(false);
		}

		// 行挿入機能
		if (!isUseRowInsert) {
			// 行挿入ボタン非表示
			panel.btnRowInsert.setVisible(false);
		}

		// 明細アップ・ダウンロード
		if (!isUseRowExcel) {
			panel.btnDownload.setVisible(false);
			panel.btnUpload.setVisible(false);
		}

		if (isUseAllCompanyDepartment) {
			notClearByCompany = true; // 会社変更時に継続する(クリアしない)機能を強制に有効にする
			panel.ctrlKDepartment.setAllCompanyMode(true);
		} else {
			panel.ctrlKDepartment.setAllCompanyMode(false);
		}

		// 明細パネルのレイアウト調整
		if (detailLayoutController != null) {
			detailLayoutController.allocateComponents(panel);
		}

	}

	/**
	 * invoice使用するかどうか
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * 明細スプレッドシードの初期設定
	 */
	public void initSpreadSheet() {
		// 科目
		String kmkName = conf.getItemName();
		String hkmName = conf.getSubItemName();
		String ukmName = conf.getDetailItemName();
		int ukm = conf.isUseDetailItem() ? 100 : -1;

		// 管理1～6
		String mng1Name = conf.getManagement1Name();
		String mng2Name = conf.getManagement2Name();
		String mng3Name = conf.getManagement3Name();
		String mng4Name = conf.getManagement4Name();
		String mng5Name = conf.getManagement5Name();
		String mng6Name = conf.getManagement6Name();
		int mng1 = conf.isUseManagement1() ? 100 : -1;
		int mng2 = conf.isUseManagement2() ? 100 : -1;
		int mng3 = conf.isUseManagement3() ? 100 : -1;
		int mng4 = conf.isUseManagement4() ? 100 : -1;
		int mng5 = conf.isUseManagement5() ? 100 : -1;
		int mng6 = conf.isUseManagement6() ? 100 : -1;

		// 非会計明細
		NonAccountingDivision nad1 = conf.getNonAccounting1();
		int hkm1 = 100; // 幅
		int align1 = SwingConstants.LEFT; // 位置
		String hkm1Name = conf.getNonAccounting1Name(); // 名称

		switch (nad1) {
			case NONE:
				hkm1 = -1;
				break;

			case NUMBER:
				align1 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align1 = SwingConstants.CENTER;
				break;
		}

		NonAccountingDivision nad2 = conf.getNonAccounting2();
		int hkm2 = 100;
		int align2 = SwingConstants.LEFT;
		String hkm2Name = conf.getNonAccounting2Name();

		switch (nad2) {
			case NONE:
				hkm2 = -1;
				break;

			case NUMBER:
				align2 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align2 = SwingConstants.CENTER;
				break;
		}

		NonAccountingDivision nad3 = conf.getNonAccounting3();
		int hkm3 = 100;
		int align3 = SwingConstants.LEFT;
		String hkm3Name = conf.getNonAccounting3Name();

		switch (nad3) {
			case NONE:
				hkm3 = -1;
				break;

			case NUMBER:
				align3 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align3 = SwingConstants.CENTER;
				break;
		}

		// カラムラベル
		String lblCompanyCode = getWord("C00570"); // 計上会社コード
		String lblCompanyName = getWord("C01052"); // 計上会社
		String lblDeptCode = getWord("C00698"); // 部門コード
		String lblDeptName = getWord("C00467"); // 部門
		String lblCode = getWord("C00174"); // コード
		String lblTaxDivision = getWord("C00312"); // 税
		String lblTaxCode = getWord("C00573"); // 消費税コード
		String lblTaxName = getWord("C00774"); // 消費税名称
		String lblTaxRate = getWord("C01554"); // 税率
		String lblAmount = getWord("C00123"); // 金額
		String lblCurrency = getWord("C00665"); // 通貨コード
		String lblCurrencyRate = getWord("C01555"); // 通貨レート
		String lblDrFAmount = getWord("C01556"); // 借方金額(外貨)
		String lblDrAmount = getWord("C01557"); // 借方金額
		String lblTaxFAmount = getWord("C10585"); // 消費税額(外貨)
		String lblTaxAmount = getWord("C00674"); // 消費税額
		String lblCrFAmount = getWord("C01558"); // 貸方金額(外貨)
		String lblCrAmount = getWord("C01559"); // 貸方金額
		String lblTekCode = getWord("C01560"); // 行摘要コード
		String lblTek = getWord("C00119"); // 行摘要
		String lblCustomerCode = getWord("C00786"); // 取引先コード
		String lblCustomerName = getWord("C00408"); // 取引先
		String lblEmpCode = getWord("C00697"); // 社員コード
		String lblEmpName = getWord("C00246"); // 社員
		String lblIssuerDay = getWord("C00431"); // 発生日
		String lblInputAmount = getWord("C00574"); // 入力金額

		// カラム
		panel.tbl.addColumn(new TTableColumn(SC.bean, "", -1));
		panel.tbl.addColumn(new TTableColumn(SC.kCompCode, lblCompanyCode, 100)); // 計上会社コード
		panel.tbl.addColumn(new TTableColumn(SC.kCompName, lblCompanyName, 100)); // 計上会社
		panel.tbl.addColumn(new TTableColumn(SC.depCode, lblDeptCode, 100)); // 部門コード
		panel.tbl.addColumn(new TTableColumn(SC.depName, lblDeptName, 100)); // 部門
		panel.tbl.addColumn(new TTableColumn(SC.itemCode, kmkName + lblCode, 100)); // 科目コード
		panel.tbl.addColumn(new TTableColumn(SC.itemName, kmkName, 100)); // 科目
		panel.tbl.addColumn(new TTableColumn(SC.subItemCode, hkmName + lblCode, 100)); // 補助コード
		panel.tbl.addColumn(new TTableColumn(SC.subItemName, hkmName, 100)); // 補助
		panel.tbl.addColumn(new TTableColumn(SC.dtlItemCode, ukmName + lblCode, ukm)); // 内訳科目コード
		panel.tbl.addColumn(new TTableColumn(SC.dtlItemName, ukmName, ukm)); // 内訳
		panel.tbl.addColumn(new TTableColumn(SC.taxDivision, lblTaxDivision, 100, SwingConstants.CENTER)); // 税
		panel.tbl.addColumn(new TTableColumn(SC.taxCode, lblTaxCode, 100)); // 消費税コード
		panel.tbl.addColumn(new TTableColumn(SC.taxName, lblTaxName, 100)); // 消費税名称
		panel.tbl.addColumn(new TTableColumn(SC.taxRate, lblTaxRate, 100, SwingConstants.RIGHT)); // 税率
		panel.tbl.addColumn(new TTableColumn(SC.amount, lblAmount, 100, SwingConstants.RIGHT)); // 金額
		panel.tbl.addColumn(new TTableColumn(SC.currency, lblCurrency, 100)); // 通貨コード
		panel.tbl.addColumn(new TTableColumn(SC.currencyRate, lblCurrencyRate, 100, SwingConstants.RIGHT)); // 通貨レート
		panel.tbl.addColumn(new TTableColumn(SC.drFAmount, lblDrFAmount, 100, SwingConstants.RIGHT)); // 借方金額(外貨)
		panel.tbl.addColumn(new TTableColumn(SC.drAmount, lblDrAmount, 100, SwingConstants.RIGHT)); // 借方金額
		panel.tbl.addColumn(new TTableColumn(SC.taxFAmount, lblTaxFAmount, 100, SwingConstants.RIGHT)); // 消費税額
		panel.tbl.addColumn(new TTableColumn(SC.taxAmount, lblTaxAmount, 100, SwingConstants.RIGHT)); // 消費税額
		panel.tbl.addColumn(new TTableColumn(SC.crFAmount, lblCrFAmount, 100, SwingConstants.RIGHT)); // 貸方金額(外貨)
		panel.tbl.addColumn(new TTableColumn(SC.crAmount, lblCrAmount, 100, SwingConstants.RIGHT)); // 貸方金額
		panel.tbl.addColumn(new TTableColumn(SC.tekCode, lblTekCode, 100)); // 行摘要コード
		panel.tbl.addColumn(new TTableColumn(SC.tek, lblTek, 100)); // 行摘要
		panel.tbl.addColumn(new TTableColumn(SC.customerCode, lblCustomerCode, 100)); // 取引先コード
		panel.tbl.addColumn(new TTableColumn(SC.customerName, lblCustomerName, 100)); // 取引先
		panel.tbl.addColumn(new TTableColumn(SC.empCode, lblEmpCode, 100)); // 社員コード
		panel.tbl.addColumn(new TTableColumn(SC.empName, lblEmpName, 100)); // 社員
		panel.tbl.addColumn(new TTableColumn(SC.mng1Code, mng1Name + lblCode, mng1)); // 管理1コード
		panel.tbl.addColumn(new TTableColumn(SC.mng1Name, mng1Name, mng1)); // 管理1
		panel.tbl.addColumn(new TTableColumn(SC.mng2Code, mng2Name + lblCode, mng2)); // 管理2コード
		panel.tbl.addColumn(new TTableColumn(SC.mng2Name, mng2Name, mng2)); // 管理2
		panel.tbl.addColumn(new TTableColumn(SC.mng3Code, mng3Name + lblCode, mng3)); // 管理3コード
		panel.tbl.addColumn(new TTableColumn(SC.mng3Name, mng3Name, mng3)); // 管理3
		panel.tbl.addColumn(new TTableColumn(SC.mng4Code, mng4Name + lblCode, mng4)); // 管理4コード
		panel.tbl.addColumn(new TTableColumn(SC.mng4Name, mng4Name, mng4)); // 管理4
		panel.tbl.addColumn(new TTableColumn(SC.mng5Code, mng5Name + lblCode, mng5)); // 管理5コード
		panel.tbl.addColumn(new TTableColumn(SC.mng5Name, mng5Name, mng5)); // 管理5
		panel.tbl.addColumn(new TTableColumn(SC.mng6Code, mng6Name + lblCode, mng6)); // 管理6コード
		panel.tbl.addColumn(new TTableColumn(SC.mng6Name, mng6Name, mng6)); // 管理6
		panel.tbl.addColumn(new TTableColumn(SC.issuerDay, lblIssuerDay, 100, SwingConstants.CENTER)); // 発生日
		panel.tbl.addColumn(new TTableColumn(SC.nonAcDtl1, hkm1Name, hkm1, align1)); // 非会計明細1
		panel.tbl.addColumn(new TTableColumn(SC.nonAcDtl2, hkm2Name, hkm2, align2)); // 非会計明細2
		panel.tbl.addColumn(new TTableColumn(SC.nonAcDtl3, hkm3Name, hkm3, align3)); // 非会計明細3
		panel.tbl.addColumn(new TTableColumn(SC.inputAmount, lblInputAmount, 100, SwingConstants.RIGHT)); // 入力金額

		if (!inputDetailCheckAlert) {
			// 未選択状態でのFocusINで1行目を選択
			panel.tbl.addFocusListener(new FocusAdapter() {

				@Override
				public void focusGained(FocusEvent e) {
					if (panel.tbl.getRowCount() != 0 && panel.tbl.getSelectedRow() < 0) {
						panel.tbl.setRowSelection(0);
					}
				}
			});
		}
	}

	/**
	 * イベント登録
	 */
	protected void addViewEvent() {

		// 新規行
		panel.btnRowNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doNew(true); // アラートチェック行う
			}
		});

		// BS勘定消込
		if (isUseBS) {
			panel.btnBS.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doBS();
				}
			});
		}

		// 行挿入機能
		if (isUseRowInsert) {
			// 行挿入
			panel.btnRowInsert.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doInsert();
				}
			});

			// スプレッド行番号クリック処理
			panel.tbl.setAdapter(new TTableAdapter() {

				@Override
				public void afterRowHeaderClicked() {
					clickTableRowHeader();
				}

			});
		}

		// 行複写
		panel.btnRowCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doCopy();
			}
		});

		// 行削除
		panel.btnRowDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doDelete();
			}
		});

		// 行確定
		panel.btnRowEntry.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doEntry();
			}
		});

		// スプレッド最上位行移動ボタン
		panel.btnRowTop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveTop();
			}
		});

		// スプレッド上行移動ボタン
		panel.btnRowUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveUp();
			}
		});

		// スプレッド下行移動ボタン
		panel.btnRowDown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveDown();
			}
		});

		// スプレッド最下位行移動ボタン
		panel.btnRowUnder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveUnder();
			}
		});

		// スプレッド選択行変更
		panel.tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				if (isProcess) {
					return;
				}

				try {

					if (e.getValueIsAdjusting()) {
						return;
					}

					if (beforeGyoNo != -1 && beforeGyoNo == panel.tbl.getSelectedRow()) {
						return;
					}

					isProcess = true;

					// 明細に変更があった場合、行を確定せずに処理を行うか
					if (!rowSelectionDetailChanged()) {
						return;
					} else {
						selectedJornal();
					}
				} finally {
					isProcess = false;
				}

			}
		});

		// 計上会社
		panel.ctrlKCompany.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				// 行編集済
				isEditedDetail = true;

				return changedCompany();
			}
		});

		// 計上部門
		panel.ctrlKDepartment.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				// 行編集済
				isEditedDetail = true;

				changedDepartment();
			}
		});

		// 科目
		panel.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				// 行編集済
				isEditedDetail = true;

				return changedItem();
			}
		});

		// 発生日
		panel.ctrlOccurDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlOccurDate.isValueChanged2()) {
					return true;
				}
				// 行編集済
				isEditedDetail = true;

				changedOccurDate();
				return true;
			}
		});

		// 通貨
		panel.ctrlCurrency.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				// 行編集済
				isEditedDetail = true;

				changedCurrency();
			}
		});

		// 通貨レート
		panel.ctrlRate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlRate.isValueChanged2()) {
					return true;
				}
				// 行編集済
				isEditedDetail = true;

				changedRate();
				return true;
			}
		});

		// 消費税
		panel.ctrlTax.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				// 行編集済
				isEditedDetail = true;

				changedTax();
			}
		});

		// 税区分
		panel.ctrlTaxDivision.addItemListener(0, new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// 行編集済
				isEditedDetail = true;

				changedTaxDivision();
			}
		});

		// 貸借
		panel.ctrlDrCr.addItemListener(0, new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// 行編集済
				isEditedDetail = true;
				changedDC();
			}
		});

		// 入力金額
		panel.ctrlInputAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlInputAmount.isValueChanged2()) {
					return true;
				}
				// 行編集済
				isEditedDetail = true;

				changedInputAmount();

				return true;
			}
		});

		// 消費税金額
		panel.ctrlTaxAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlTaxAmount.isValueChanged2()) {
					return true;
				}
				// 行編集済
				isEditedDetail = true;

				changedTaxAmount();

				return true;
			}
		});

		// 基軸金額
		panel.ctrlKeyAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlKeyAmount.isValueChanged2()) {
					return true;
				}
				// 行編集済
				isEditedDetail = true;

				changedKeyAmount();

				return true;
			}
		});

		// 債権消込ボタン
		panel.btnAP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doAPErasing();
			}
		});

		// 債務消込ボタン
		panel.btnAR.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doARErasing();
			}
		});

		// 合計欄の通貨コンボボックス
		panel.cbxCurrencyOnTotal.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changedCurrencyOnTotal();
			}
		});

		if (isUseRowExcel) {
			// ダウンロードボタン
			panel.btnDownload.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doExcelDownload();
				}
			});

			// アップロードボタン
			panel.btnUpload.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doExcelUpload();
				}
			});
		}

		// 取引先リファレンス
		panel.ctrlCustomer.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {

				if (!flag) {
					return;
				}
				if (isInvoice) {
					// 取引先マスタで非適格請求書発行事業者で設定されている消費税を設定
					setTaxNoInvReg(panel.ctrlTax.getEntity());
				}
			}

		});

		// 行編集監視
		if (inputDetailCheckAlert) {
			TCallBackListener callback = new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag) {
						return;
					}
					// 行編集済
					isEditedDetail = true;

				}
			};

			TInputVerifier verifier = new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlRemark.name.isValueChanged2()) {
						return true;
					}
					// 行編集済
					isEditedDetail = true;
					return true;
				}
			};

			// 行摘要
			panel.ctrlRemark.addCallBackListener(callback);
			// 行摘要(文言)
			panel.ctrlRemark.name.setInputVerifier(verifier);
			// 取引先変更
			panel.ctrlCustomer.addCallBackListener(callback);
			// 社員変更
			panel.ctrlEmployee.addCallBackListener(callback);
			// 管理1変更
			panel.ctrlManage1.addCallBackListener(callback);
			// 管理2変更
			panel.ctrlManage2.addCallBackListener(callback);
			// 管理3変更
			panel.ctrlManage3.addCallBackListener(callback);
			// 管理4変更
			panel.ctrlManage4.addCallBackListener(callback);
			// 管理5変更
			panel.ctrlManage5.addCallBackListener(callback);
			// 管理6変更
			panel.ctrlManage6.addCallBackListener(callback);
		}
	}

	/**
	 * 伝票明細行をエクセル出力する
	 */
	protected void doExcelDownload() {
		if (panel.tbl.getRowCount() == 0) {
			// 明細がありません。
			showMessage("I00363");
			return;
		}
		try {
			List<SWK_DTL> list = getEntityList();
			if (outerDetail != null) {
				list.remove(outerDetail);
			}
			byte[] exl = (byte[]) request(getManagerClass(), "getDetailExcel", list);
			// "伝票明細一覧"

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(exl, getWord("C04293") + ".xls");
		} catch (Exception e) {
			// 帳票出力に失敗しました。
			showMessage("W00112");
		}

	}

	/**
	 * エクセルから伝票明細行をアップロードする
	 */
	protected void doExcelUpload() {
		try {
			// ファイル選択ダイアログを開く
			TFileChooser fc = new TFileChooser();

			File dir = getPreviousFolder(".chooser");

			// 前回のフォルダを復元
			if (dir != null) {
				fc.setCurrentDirectory(dir);
			}

			// テキストファイル(MS-Excel形式)フィルター
			TFileFilter filter = new TFileFilter(getWord("C04293"), ExtensionType.XLS, ExtensionType.XLSX);
			fc.setFileFilter(filter);

			if (TFileChooser.FileStatus.Selected != fc.show(panel)) {
				return;
			}

			// 同一ファイルが過去に取り込まれている場合警告メッセージを表示
			TFile file = fc.getSelectedTFile();
			file.setKey(getFileRecordKey());

			// 最後の取込フォルダ保存
			saveFolder(fc.getCurrentDirectory(), ".chooser");

			// 取込ファイルチェック
			List<SWK_DTL> dtlList = new ArrayList<SWK_DTL>();
			// コンバートをサーバーにリクエスト
			dtlList = (List<SWK_DTL>) request(getManagerClass(), "convertExcelToDetails", file.getFile(), this.slipType);

			if (isInvoice && dtlList != null && !dtlList.isEmpty()) {
				if (checkInvoiceItemTaxCode(dtlList)) {
					// インボイス用:取引先と消費税を非適格、適格かチェック
					return;
				}
			}

			// 行上書きの処理の場合前行クリア
			if (isOverwriteExcel) {
				panel.tbl.removeRow();
			}

			for (SWK_DTL dtl : dtlList) {
				// 行の追加
				List<Object> list = getRow(dtl);
				panel.tbl.addRow(list);
			}
		} catch (Exception e) {
			errorHandler(e);
		}
		// 合計の通貨
		makeCurrencyComboBox();

		// 合計計算
		summary();

		// 退避行の初期化
		beforeGyoNo = -1;

	}

	/**
	 * invoice用 科目コードが消費税科目か確認
	 * 
	 * @param list
	 * @return 続行するかどうか true:続行しない
	 */
	protected boolean checkInvoiceItemTaxCode(List<SWK_DTL> list) {

		if (chkSlipTypeInvoice()) {
			return false;
		}
		List<Message> msgList = new ArrayList<Message>();
		int row = 0;
		int rowNo = 1;

		for (SWK_DTL dtl : list) {
			row++;
			if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				continue;
			}

			String kmk = Util.avoidNull(dtl.getSWK_KMK_CODE());
			String hkm = Util.avoidNull(dtl.getSWK_HKM_CODE());
			String ukm = Util.avoidNull(dtl.getSWK_UKM_CODE());

			if (TLoginInfo.isTaxAutoItem(kmk, hkm, ukm)) {
				Message msg = new Message();
				msg.setMessage(getMessage("I01119", row));
				msg.setSubMessageID(Integer.toString(rowNo));
				msgList.add(msg);
				rowNo++;
				// n行目に消費税科目が入力されています。

			}
		}

		msgList = checkCustomerTaxInvReg(list, msgList, rowNo); // 非適格か適格か

		if (msgList.size() != 0) {
			if (ConfermMessageListDialog.OK_OPTION != showConfermMessageList(getView(), getMessage("I01111"), msgList)) {
				// メッセージ表示 続行しますか？
				return true;
			}
		}

		return false;

	}

	/**
	 * invoice用 uploadしたデータの取引先と消費税を非適格、適格かチェック
	 * 
	 * @param list
	 * @param msgList メッセージList
	 * @param rowNo 項番番号
	 * @return MessageList
	 */
	protected List<Message> checkCustomerTaxInvReg(List<SWK_DTL> list, List<Message> msgList, int rowNo) {

		int row = 0;

		for (SWK_DTL dtl : list) {
			row++;
			Message msg = new Message();

			if (Util.isNullOrEmpty(dtl.getSWK_TRI_CODE()) || Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}
			// 取引先、消費税のエンティティ取得
			Customer cus = dtl.getCustomer();
			ConsumptionTax tax = dtl.getTax();
			if (cus == null || tax == null) {
				continue;
			}

			if (cus.isNO_INV_REG_FLG()) {
				if (!tax.isNO_INV_REG_FLG()) {
					msg.setMessage(getMessage("I01112", row, "C12197", tax.getCode() + ":" + tax.getNames()));
					msg.setSubMessageID(Integer.toString(rowNo));
					msgList.add(msg);
					rowNo++;
					// x行目 非適格請求書発行事業者に対して【{消費税}】が設定されています。
				}
			} else {
				if (tax.isNO_INV_REG_FLG()) {
					msg.setMessage(getMessage("I01112", row, "C12196", tax.getCode() + ":" + tax.getNames()));
					msg.setSubMessageID(Integer.toString(rowNo));
					msgList.add(msg);
					rowNo++;
					// x行目 適格請求書発行事業者に対して【{消費税}】が設定されています。
				}
			}
		}

		return msgList;

	}

	/**
	 * INVOICE用：伝票種別でチェック制度使用するか
	 * 
	 * @return false:使用する
	 */
	protected boolean chkSlipTypeInvoice() {

		if (slipType == null || !slipType.isINV_SYS_FLG()) {
			return true;
		}

		if (slipType.getCode().equals("031") && Util.isNullOrEmpty(getCompany().getInvRegNo())) {
			// 債権計上かつ会社マスタに適格請求書発行事業者番号が入力されていない場合はエラーメッセージ不要
			return true;
		}
		return false;
	}

	/**
	 * 指定のフォルダ情報を保存する
	 * 
	 * @param type 種類
	 * @param dir フォルダ情報
	 */
	protected void saveFolder(File dir, String type) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + type);
	}

	/**
	 * 前回保存したフォルダ情報を取得
	 * 
	 * @param type 種類
	 * @return 前回保存したフォルダ情報
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * @return FolderKeyNameを取得
	 */
	protected String getFolderKeyName() {
		return getProgramCode();
	}

	/**
	 * ファイル呼び出しダイアログのキー
	 * 
	 * @return ファイル呼び出しダイアログのキー
	 */
	protected String getFileRecordKey() {
		return "SLIP";
	}

	/**
	 * 初期状態
	 */
	public void init() {
		this.dispatchDetail = null;
		this.slipNo = null;
		// 退避行の初期化
		this.beforeGyoNo = -1;
		// 行編集済
		this.isEditedDetail = false;

		// 明細クリア
		panel.tbl.removeRow();

		// ボタン制御
		controllButtons();

		// 入力クリア
		clearInput();
	}

	/**
	 * 入力部のみ初期状態
	 */
	public void clearInput() {
		// クリア
		panel.ctrlKCompany.clear();
		panel.ctrlKDepartment.clear();
		panel.ctrlItem.clear();
		panel.ctrlOccurDate.clear();
		panel.ctrlTaxDivision.setSelectON(defaultTaxInnerDivision ? 0 : 1);
		panel.ctrlRemark.clear();
		panel.ctrlDrCr.setDR(defaultDC == Dc.DEBIT);
		panel.ctrlInputAmount.clear();

		// 初期値
		setCompany(getCompany());

		if (isDefaultBlankDepartment()) {
			// 計上部門の初期値がnullの場合、ユーザ情報の設定不要
			setDepartment(null);
		} else {
			// デフォルト計上部門
			setDepartment(getUser().getDepartment());
		}

		// 入力制御
		panel.ctrlKCompany.setEditable(conf.isUseGroupAccount()); // グループ会計OFF時は入力不可
		panel.ctrlKDepartment.setEditable(true);
		panel.ctrlItem.ctrlItemReference.setEditable(!isDefaultBlankDepartment());
		panel.ctrlOccurDate.setEditable(true);
		panel.ctrlTaxDivision.setEnabled(true);
		panel.ctrlRemark.setEditable(true);
		panel.ctrlDrCr.setEnabled(true);
		panel.ctrlInputAmount.setEditable(true);

		// 科目連動系
		clearInputForItem();

		// 発生日
		Date occurDate = panel.ctrlOccurDate.getValue();

		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		// 発生日デフォルト値設定
		if (allowDefaultOccurDate && panel.ctrlOccurDate.isVisible() && panel.ctrlOccurDate.isEditable()) {
			panel.ctrlOccurDate.setValue(baseDate);
			changedOccurDate();
		}

	}

	/**
	 * @return true:計上部門初期ブランク
	 */
	protected boolean isDefaultBlankDepartment() {
		return TSlipDetailPanelCtrl.departmentDefaultBlank;
	}

	/**
	 * 科目関連入力部のみ初期状態
	 */
	public void clearInputForItem() {
		// クリア
		panel.ctrlCurrency.clear();
		panel.ctrlRate.clear();
		panel.ctrlTax.clear();
		panel.ctrlTaxAmount.clear();
		panel.ctrlKeyAmount.clear();
		panel.ctrlCustomer.clear();
		panel.ctrlEmployee.clear();
		panel.ctrlManage1.clear();
		panel.ctrlManage2.clear();
		panel.ctrlManage3.clear();
		panel.ctrlManage4.clear();
		panel.ctrlManage5.clear();
		panel.ctrlManage6.clear();
		panel.ctrlNonAcDetails.clear();

		// 入力制御
		panel.ctrlCurrency.setEditable(false);
		panel.ctrlRate.setEditable(false);
		panel.ctrlTax.setEditable(false);
		panel.ctrlTaxAmount.setEditable(false);
		panel.ctrlKeyAmount.setEditable(false);
		panel.ctrlCustomer.setEditable(false);
		panel.ctrlEmployee.setEditable(false);
		panel.ctrlManage1.setEditable(false);
		panel.ctrlManage2.setEditable(false);
		panel.ctrlManage3.setEditable(false);
		panel.ctrlManage4.setEditable(false);
		panel.ctrlManage5.setEditable(false);
		panel.ctrlManage6.setEditable(false);
		panel.ctrlNonAcDetails.setEditable(false);

		// 初期値
		// ヘッダー通貨
		Currency hdrCur = getHeaderCurrency();
		if (hdrCur != null) {
			setCurrecy(hdrCur);
		} else {
			setCurrecy(keyCurrency);
		}

		summary();
	}

	/**
	 * ボタンコントロール
	 */
	protected void controllButtons() {
		int count = panel.tbl.getRowCount();
		int row = panel.tbl.getSelectedRow();
		int starRow = panel.tbl.getRowHeaderStarIndex();

		// 複写は消込行の場合、押下不可
		panel.btnRowCopy.setEnabled(row >= 0 && dispatchDetail != null && !dispatchDetail.isErasing());
		panel.btnRowDelete.setEnabled(row >= 0);
		panel.btnRowInsert.setEnabled(starRow >= 0);

		panel.btnRowTop.setEnabled(count != 0 && row >= 0 && row != 0);
		panel.btnRowUp.setEnabled(count != 0 && row >= 0 && row != 0);
		panel.btnRowDown.setEnabled(count != 0 && row >= 0 && row != count - 1);
		panel.btnRowUnder.setEnabled(count != 0 && row >= 0 && row != count - 1);
	}

	/**
	 * デフォルトが内税か
	 * 
	 * @param defaultTaxInnerDivision true:内税
	 */
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		this.defaultTaxInnerDivision = defaultTaxInnerDivision;
		panel.ctrlTaxDivision.setSelectON(defaultTaxInnerDivision ? 0 : 1);
	}

	/**
	 * 貸借のデフォルト値
	 * 
	 * @param dc 貸借
	 */
	public void setDefaultDC(Dc dc) {
		this.defaultDC = dc;
		panel.ctrlDrCr.setDR(dc == Dc.DEBIT);
	}

	/**
	 * 伝票伝票番号(編集の場合)
	 * 
	 * @param slipNo 伝票伝票番号(編集の場合)
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * 伝票種別を設定
	 * 
	 * @param type
	 */
	public void setSlipType(SlipType type) {
		this.slipType = type;
	}

	/**
	 * 基準日設定(有効日判定基準)
	 * 
	 * @param baseDate 基準日
	 */
	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;

		// 有効期限
		panel.ctrlKCompany.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlKDepartment.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlItem.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlCurrency.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlTax.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlRemark.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlCustomer.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlEmployee.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage1.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage2.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage3.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage4.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage5.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage6.getSearchCondition().setValidTerm(baseDate);

		// 発生日
		Date occurDate = panel.ctrlOccurDate.getValue();
		if (occurDate != null) {
			panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);
		}

		// 発生日デフォルト値設定
		if (allowDefaultOccurDate && panel.ctrlOccurDate.isVisible() && panel.ctrlOccurDate.isEditable()) {
			panel.ctrlOccurDate.setValue(baseDate);
			changedOccurDate();
		}

		// ヘッダーから日付変更
		if (beforeGyoNo == -1 && !isEditedDetail) {
			// 行選択されていたら
			isEditedDetail = false;
		} else {
			isEditedDetail = true;
		}

	}

	/**
	 * 決算仕訳設定
	 * 
	 * @param isClosing true:決算仕訳、false:通常仕訳
	 */
	public void setClosingEntry(boolean isClosing) {
		this.isClosing = isClosing;
	}

	/**
	 * 外部指定明細(金額合計で利用)
	 * 
	 * @param outherDetail 外部指定明細
	 */
	public void setOuterDetail(SWK_DTL outherDetail) {
		this.outerDetail = outherDetail;
	}

	/**
	 * 外部指定取引先を設定(固定表示用)
	 * 
	 * @param customer 外部指定取引先
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * 外部指定社員を設定(初期表示用)
	 * 
	 * @param employee 社員
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * 回収取引先を設定(債権残高一覧用)
	 * 
	 * @param customer 回収取引先
	 */
	public void setCollectionCustomer(Customer customer) {
		this.collectionCustomer = customer;
	}

	/**
	 * @return true:グループ会計(基軸通貨異なる計上会社可)
	 */
	public static boolean isGroupAccounting() {
		return groupAccounting;
	}

	/**
	 * 廃止
	 * 
	 * @return true:発生日非表示
	 */
	@Deprecated
	public boolean isHideOccurDate() {
		return false;
	}

	/**
	 * @return true:発生日ブランク可能
	 */
	public boolean isAllowOccurDateBlank() {
		return allowOccurDateBlank && !conf.isUseIfrs();
	}

	/**
	 * @param dtl
	 * @return true:発生日ブランクの場合、伝票日付を登録する
	 */
	public boolean isAllowEntryDefaultOccurDate(SWK_DTL dtl) {
		if (dtl.getHAS_DATE() != null) {
			return false;
		}

		if (!dtl.isUseItemOccurDate()) {
			return false;
		}
		return allowEntryDefaultOccurDate;
	}

	/**
	 * 会社設定
	 * 
	 * @param kcompany 会社コード
	 */
	protected void setCompany(Company kcompany) {
		panel.ctrlKCompany.setEntity(kcompany);

		String code = kcompany.getCode();

		// 条件変更
		panel.ctrlKDepartment.getSearchCondition().setCompanyCode(code);
		panel.ctrlItem.getSearchCondition().setCompanyCode(code);

		// グループ会計制御
		if (isGroupAccounting()) {
			// 通貨情報の取得条件はログイン会社コードより
			panel.ctrlCurrency.getSearchCondition().setCompanyCode(getCompanyCode());
		} else {
			// 通貨情報の取得条件は計上会社コードより
			panel.ctrlCurrency.getSearchCondition().setCompanyCode(code);
		}

		panel.ctrlTax.getSearchCondition().setCompanyCode(code);
		panel.ctrlRemark.getSearchCondition().setCompanyCode(code);
		panel.ctrlCustomer.getSearchCondition().setCompanyCode(code);
		panel.ctrlEmployee.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage1.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage2.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage3.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage4.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage5.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage6.getSearchCondition().setCompanyCode(code);

		AccountConfig kconf = kcompany.getAccountConfig();

		// 名称セット
		panel.ctrlItem.ctrlItemReference.btn.setText(kconf.getItemName());
		panel.ctrlItem.ctrlSubItemReference.btn.setText(kconf.getSubItemName());
		panel.ctrlItem.ctrlDetailItemReference.btn.setText(kconf.getDetailItemName());
		panel.ctrlManage1.btn.setText(kconf.getManagement1Name());
		panel.ctrlManage2.btn.setText(kconf.getManagement2Name());
		panel.ctrlManage3.btn.setText(kconf.getManagement3Name());
		panel.ctrlManage4.btn.setText(kconf.getManagement4Name());
		panel.ctrlManage5.btn.setText(kconf.getManagement5Name());
		panel.ctrlManage6.btn.setText(kconf.getManagement6Name());

		// 利用するか
		panel.ctrlItem.ctrlDetailItemReference.setVisible(kconf.isUseDetailItem());
		panel.ctrlManage1.setVisible(kconf.isUseManagement1());
		panel.ctrlManage2.setVisible(kconf.isUseManagement2());
		panel.ctrlManage3.setVisible(kconf.isUseManagement3());
		panel.ctrlManage4.setVisible(kconf.isUseManagement4());
		panel.ctrlManage5.setVisible(kconf.isUseManagement5());
		panel.ctrlManage6.setVisible(kconf.isUseManagement6());

		// TODO:非会計明細

		// リフレッシュの場合、各値再取得
		if (isNotClearByCompany()) {

			boolean isBreak = false;

			panel.ctrlKDepartment.refleshEntity();
			if (panel.ctrlKDepartment.getEntity() == null) {
				changedDepartment();

				isBreak = true;

			} else if (isUseAllCompanyDepartment) {
				// 当該会社の部門かどうか、チェックを行う

				Department dep = panel.ctrlKDepartment.getEntity();
				if (!Util.equals(dep.getCompanyCode(), code)) {
					panel.ctrlKDepartment.setEntity(null);
					panel.ctrlKDepartment.setCode(dep.getCode());

					try {
						panel.ctrlKDepartment.setAllCompanyMode(false);
						panel.ctrlKDepartment.getSearchCondition().setCompanyCode(code);
						panel.ctrlKDepartment.refleshEntity();
					} finally {
						panel.ctrlKDepartment.setAllCompanyMode(true);
					}

					changedDepartment();

					isBreak = true;
				}
			}

			if (!isBreak) {
				panel.ctrlItem.refleshGroupEntity();

				if (panel.ctrlItem.getEntity() == null) {
					isBreak = true;
				}

				changedItem();
			}

			if (!isBreak) {
				Remark oldRemark = (Remark) panel.ctrlRemark.getController().getUnspecifiedEntity();

				panel.ctrlRemark.refleshEntity();
				panel.ctrlCustomer.refleshEntity();
				panel.ctrlEmployee.refleshEntity();
				panel.ctrlCurrency.refleshEntity();
				panel.ctrlManage1.refleshEntity();
				panel.ctrlManage2.refleshEntity();
				panel.ctrlManage3.refleshEntity();
				panel.ctrlManage4.refleshEntity();
				panel.ctrlManage5.refleshEntity();
				panel.ctrlManage6.refleshEntity();
				panel.ctrlTax.refleshEntity();

				if (panel.ctrlRemark.getEntity() != null) {
					panel.ctrlRemark.setNames(panel.ctrlRemark.getEntity().getName());
				} else if (oldRemark != null && Util.isNullOrEmpty(oldRemark.getCode())) {
					panel.ctrlRemark.setEntity(oldRemark);
				}
				if (panel.ctrlCustomer.getEntity() != null) {
					panel.ctrlCustomer.setNames(panel.ctrlCustomer.getEntity().getNames());
				}
				if (panel.ctrlEmployee.getEntity() != null) {
					panel.ctrlEmployee.setNames(panel.ctrlEmployee.getEntity().getNames());
				}
				if (panel.ctrlCurrency.getEntity() != null) {
					panel.ctrlCurrency.setNames(panel.ctrlCurrency.getEntity().getNames());
				}
				if (panel.ctrlManage1.getEntity() != null) {
					panel.ctrlManage1.setNames(panel.ctrlManage1.getEntity().getNames());
				}
				if (panel.ctrlManage2.getEntity() != null) {
					panel.ctrlManage2.setNames(panel.ctrlManage2.getEntity().getNames());
				}
				if (panel.ctrlManage3.getEntity() != null) {
					panel.ctrlManage3.setNames(panel.ctrlManage3.getEntity().getNames());
				}
				if (panel.ctrlManage4.getEntity() != null) {
					panel.ctrlManage4.setNames(panel.ctrlManage4.getEntity().getNames());
				}
				if (panel.ctrlManage5.getEntity() != null) {
					panel.ctrlManage5.setNames(panel.ctrlManage5.getEntity().getNames());
				}
				if (panel.ctrlManage6.getEntity() != null) {
					panel.ctrlManage6.setNames(panel.ctrlManage6.getEntity().getNames());
				}
				if (panel.ctrlTax.getEntity() != null) {
					panel.ctrlTax.setNames(panel.ctrlTax.getEntity().getNames());
				}
			}
		}

	}

	/**
	 * 計上部門設定
	 * 
	 * @param dept 計上部門
	 */
	protected void setDepartment(Department dept) {
		panel.ctrlKDepartment.setEntity(dept);

		if (dept == null) {
			panel.ctrlItem.setEntity(null);
			panel.ctrlItem.ctrlItemReference.setEditable(false);
			return;
		}

		// 科目初期化
		panel.ctrlItem.ctrlItemReference.setEditable(true);

		String code = dept.getCode();

		String oldDeptCode = panel.ctrlItem.getSearchCondition().getDepartmentCode();

		if (!code.equals(Util.avoidNull(oldDeptCode))) {
			// 部門コードが変更になった場合、条件を変更
			panel.ctrlItem.getSearchCondition().setDepartmentCode(code);

			// 条件変更により、整合性チェックでOKなら残す
			if (!panel.ctrlItem.isEmpty()) {
				if (!isNotClearByCompany()) {
					panel.ctrlItem.refleshEntity();
				} else {
					panel.ctrlItem.refleshGroupEntity();
				}
			}
		}
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨
	 */
	protected void setCurrecy(Currency currency) {

		panel.ctrlCurrency.setEntity(currency);

		// 合計欄の通貨
		makeCurrencyComboBox();

		if (currency == null) {
			panel.ctrlRate.clear();
			panel.ctrlRate.setEditable(false);
			panel.ctrlKeyAmount.clear();
			panel.ctrlKeyAmount.setEditable(false);
			return;
		}

		String currencyCode = currency.getCode();
		boolean isKey = keyCurrency.getCode().equals(currencyCode);

		// レート
		panel.ctrlRate.setEditable(!isKey);
		panel.ctrlRate.setNumberValue(isKey ? BigDecimal.ONE : null);
		changedRate();

		// 基軸金額
		panel.ctrlKeyAmount.setEditable(!isKey);

		// 小数点変更
		int digit = currency.getDecimalPoint();

		// 各コンポーネント
		panel.ctrlInputAmount.setDecimalPoint(digit);
		panel.ctrlTaxAmount.setDecimalPoint(digit);
		changedTax();
	}

	/**
	 * 合計欄の通貨コンボボックス構築
	 */
	protected void makeCurrencyComboBox() {

		// 選択中の通貨(合計欄)
		Currency selectedCurrency = (Currency) panel.cbxCurrencyOnTotal.getSelectedItemValue();

		Set<Currency> list = new LinkedHashSet<Currency>();

		// ヘッダの通貨
		if (outerDetail != null) {
			list.add(outerDetail.getCurrency());
		}

		// 明細の通貨
		int row = panel.tbl.getSelectedRow(); // 選択行

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			if (row == i) {
				continue;
			}

			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);
			list.add(dtl.getCurrency());
		}

		// 画面入力中の通貨
		list.add(panel.ctrlCurrency.getEntity());

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
	 * 計上会社変更
	 * 
	 * @return false:付替設定NG
	 */
	protected boolean changedCompany() {
		try {
			if (panel.ctrlKCompany.isEmpty()) {
				panel.ctrlKCompany.setEntity(getCompany());
			}

			Company company = panel.ctrlKCompany.getEntity();

			if (company == null) {
				return false;
			}

			String code = company.getCode();

			// 会社間付替マスタが設定されていません。
			if (!getCompanyCode().equals(code) && !isAppropriateCompanyReplace()) {
				showMessage("I00054");// 会社間付替マスタが設定されていません。
				panel.ctrlKCompany.code.requestFocus();
				panel.ctrlKCompany.code.clearOldText();

				return false;
			}

			// リフレッシュの場合、クリア不要
			if (!isNotClearByCompany()) {

				// クリア
				clearInput();

				setCompany(company);

				// 計上部門はログイン会社以外はブランク
				if (!getCompanyCode().equals(code)) {
					panel.ctrlKDepartment.clear();
				}

				changedDepartment();
			} else {

				// 会社設定のみ
				setCompany(company);
			}

			return true;

		} catch (TException ex) {
			errorHandler(ex);
			return true;
		}
	}

	/**
	 * @return true:計上会社変更より明細項目最新取得を行う、存在していない場合のみクリア
	 */
	protected boolean isNotClearByCompany() {
		return notClearByCompany;
	}

	/**
	 * 計上科目コードのチェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean isAppropriateCompanyReplace() throws TException {
		String kcompany = panel.ctrlKCompany.getCode();

		List<TransferConfig> list = (List<TransferConfig>) request(CompanyManager.class, "getTransferConfig",
			getCompanyCode(), kcompany);

		if (list == null) {
			return false;
		}

		return list.size() == 2;
	}

	/**
	 * 計上部門変更
	 */
	protected void changedDepartment() {
		try {
			Department dept = panel.ctrlKDepartment.getEntity();

			if (isUseAllCompanyDepartment && dept != null) {
				// 全SPC部門の場合
				String nowCompanyCode = panel.ctrlKCompany.getCode();
				String deptCompanyCode = dept.getCompanyCode();

				String depCode = dept.getCode();
				if (depPriorityMap.containsKey(depCode)) {
					deptCompanyCode = depPriorityMap.get(depCode);
					dept.setCompanyCode(deptCompanyCode);
				}

				if (!Util.isNullOrEmpty(deptCompanyCode) && !Util.equals(nowCompanyCode, deptCompanyCode)) {
					// 選択した部門の会社コードと計上会社が不一致
					// 計上会社を再取得する
					Company kcompany = (Company) request(CompanyManager.class, "get", deptCompanyCode);
					panel.ctrlKCompany.setEntity(kcompany);

					changedCompany();
				}

			}

			// 変更前科目コード
			String itemCode = panel.ctrlItem.ctrlItemReference.getCode();

			setDepartment(dept);

			// 変更があったら科目変更通知
			if (!itemCode.equals(panel.ctrlItem.ctrlItemReference.getCode())) {
				changedItem();
			}

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * @return ヘッダー通貨
	 */
	protected Currency getHeaderCurrency() {
		if (getView().parent != null) {

			if (getView().parent instanceof TSlipAddPartsPanel) {

				TSlipAddPartsPanel pnl = (TSlipAddPartsPanel) getView().parent;

				if (pnl.isUseHeaderDefaultCurreny() && pnl.ctrlCurrency != null) {
					return pnl.ctrlCurrency.getEntity();
				}

				return null;
			}

		}

		return null;
	}

	/**
	 * 科目変更
	 * 
	 * @return false:NG
	 */
	protected boolean changedItem() {
		Item item = panel.ctrlItem.getEntity();

		if (item == null) {
			clearInputForItem();
			changedCurrency();
			return true;
		}

		// 補助、内訳がある場合は、そっちを反映
		if (item.getSubItem() != null) {
			item = item.getSubItem();
		}

		if (item.getDetailItem() != null) {
			item = item.getDetailItem();
		}

		// 多通貨入力フラグ
		panel.ctrlCurrency.setEditable(item.isUseForeignCurrency());
		if (!item.isUseForeignCurrency()) {
			setCurrecy(keyCurrency);
			changedCurrency();
		} else {

			// ヘッダー通貨
			Currency hdrCur = getHeaderCurrency();
			if (hdrCur != null) {
				setCurrecy(hdrCur);
				changedCurrency();
			}
		}

		// 消費税
		if (item.isUseSalesTaxation() || item.isUsePurchaseTaxation()) {
			// 売上課税入力、または、仕入課税入力の場合、入力可
			panel.ctrlTax.setEditable(true);
			panel.ctrlTax.setEntity(item.getConsumptionTax());
			panel.ctrlTax.getSearchCondition().setHasSales(item.isUseSalesTaxation());
			panel.ctrlTax.getSearchCondition().setHasPurcharse(item.isUsePurchaseTaxation());

		} else {
			panel.ctrlTax.getSearchCondition().setHasSales(false);
			panel.ctrlTax.getSearchCondition().setHasPurcharse(false);
			panel.ctrlTax.setEditable(false);
			panel.ctrlTax.setEntity(null);
		}

		// 条件が変わってるので整合性チェック
		panel.ctrlTax.refleshEntity();
		changedTax();

		// 取引先
		CustomerType customerType = item.getClientType();
		panel.ctrlCustomer.setEditable(customerType != CustomerType.NONE);
		panel.ctrlCustomer.getSearchCondition().setType(customerType);

		if (customerType != CustomerType.NONE) {

			// 取引先未設定の場合は初期値として、外部設定の取引先を指定
			if (Util.isNullOrEmpty(panel.ctrlCustomer.getCode()) && customer != null) {
				panel.ctrlCustomer.setEntity(customer);
			}

			if (isInvoice) {
				// インボイス 非適格請求書発行事業者
				setTaxNoInvReg(item.getConsumptionTax());
			}
			panel.ctrlCustomer.refleshEntity();

		} else {
			panel.ctrlCustomer.clear();
		}

		// 社員
		panel.ctrlEmployee.setEditable(item.isUseEmployee());

		if (!item.isUseEmployee()) {
			panel.ctrlEmployee.clear();

		} else if (panel.ctrlEmployee.isEmpty() && !employeeDefaultBlank) {
			Company kcompany = panel.ctrlKCompany.getEntity();

			if (kcompany.getCode().equals(getCompanyCode())) {
				Employee refEmployee = employee == null ? getUser().getEmployee() : employee;
				panel.ctrlEmployee.setEntity(refEmployee);
			}
		}

		// 管理１～6
		panel.ctrlManage1.setEditable(item.isUseManagement1());
		panel.ctrlManage2.setEditable(item.isUseManagement2());
		panel.ctrlManage3.setEditable(item.isUseManagement3());
		panel.ctrlManage4.setEditable(item.isUseManagement4());
		panel.ctrlManage5.setEditable(item.isUseManagement5());
		panel.ctrlManage6.setEditable(item.isUseManagement6());

		if (!item.isUseManagement1()) panel.ctrlManage1.clear();
		if (!item.isUseManagement2()) panel.ctrlManage2.clear();
		if (!item.isUseManagement3()) panel.ctrlManage3.clear();
		if (!item.isUseManagement4()) panel.ctrlManage4.clear();
		if (!item.isUseManagement5()) panel.ctrlManage5.clear();
		if (!item.isUseManagement6()) panel.ctrlManage6.clear();

		// 非会計明細1～3
		panel.ctrlNonAcDetails.setEditable(1, item.isUseNonAccount1());
		panel.ctrlNonAcDetails.setEditable(2, item.isUseNonAccount2());
		panel.ctrlNonAcDetails.setEditable(3, item.isUseNonAccount3());

		if (!item.isUseNonAccount1()) panel.ctrlNonAcDetails.clear(1);
		if (!item.isUseNonAccount2()) panel.ctrlNonAcDetails.clear(2);
		if (!item.isUseNonAccount3()) panel.ctrlNonAcDetails.clear(3);

		if (isAllowOccurDateBlank()) {
			// 発生日ブランク可能の場合、科目フラグにより制御
			if (item.isUseOccurDate()) {
				panel.ctrlOccurDate.setEditable(true);
			} else {
				panel.ctrlOccurDate.setEditable(false);
				panel.ctrlOccurDate.clear();
			}
		}

		return true;
	}

	/**
	 * 通貨変更
	 */
	protected void changedCurrency() {
		Currency currency = panel.ctrlCurrency.getEntity();

		setCurrecy(currency);

		// レート
		panel.ctrlRate.setNumberValue(getCurrencyRate());
		if (isUseHasDateChk) {
			Date hasDate = panel.ctrlOccurDate.getValue();
			panel.ctrlRate.setNumberValue(getCurrencyRateByOccurDate(hasDate));
		}

		changedRate();
	}

	/**
	 * 発生日の変更
	 */
	protected void changedOccurDate() {
		if (isProcess) {
			return;
		}

		// レート変更
		BigDecimal old = panel.ctrlRate.getBigDecimal();
		BigDecimal nuw = getCurrencyRate();

		// 発生日
		Date occurDate = panel.ctrlOccurDate.getValue();

		if (occurDate == null) {
			occurDate = baseDate;
		}
		if (isUseHasDateChk) {
			nuw = getCurrencyRateByOccurDate(occurDate);
		}

		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		// 取得レートが無い
		if (nuw == null) {
			panel.ctrlRate.clear();
			return;
		}

		// レート値に変更がない
		if (old.compareTo(nuw) == 0) {
			return;
		}

		panel.ctrlRate.setNumberValue(nuw);
		changedRate();
	}

	/**
	 * 通貨レート 変更
	 */
	protected void changedRate() {
		// 邦貨換算
		Currency currency = panel.ctrlCurrency.getEntity();

		if (currency == null || panel.ctrlInputAmount.isEmpty()) {
			return;
		}

		BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
		panel.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

		// 消費税
		changedTax();
		summary();
	}

	/**
	 * 入力金額の変更
	 */
	protected void changedInputAmount() {

		// 消費税
		changedTax();
		summary();
	}

	/**
	 * 消費税額の変更
	 */
	protected void changedTaxAmount() {
		summary();
	}

	/**
	 * 基軸金額の変更
	 */
	protected void changedKeyAmount() {
		summary();
	}

	/**
	 * 内税/外税 切替
	 */
	protected void changedTaxDivision() {
		changedTax();
	}

	/**
	 * 消費税変更
	 */
	protected void changedTax() {
		try {
			// 消費税計算
			Company kcompany = panel.ctrlKCompany.getEntity();
			Currency currency = panel.ctrlCurrency.getEntity();
			ConsumptionTax tax = panel.ctrlTax.getEntity();

			if (kcompany == null || tax == null || DecimalUtil.isNullOrZero(tax.getRate())) {
				panel.ctrlTaxAmount.clear();
				panel.ctrlTaxAmount.setEditable(false);
				return;
			}

			// 通貨なし
			if (currency == null) {
				return;
			}

			AccountConfig kconf = kcompany.getAccountConfig();

			// 売上、仕入の場合は入力可
			switch (tax.getTaxType()) {
				case NOT:
					panel.ctrlTaxAmount.clear();
					panel.ctrlTaxAmount.setEditable(false);
					break;

				case SALES:
				case PURCHAESE:
					panel.ctrlTaxAmount.setEditable(true);

					BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();

					if (DecimalUtil.isZero(inAmount)) {
						panel.ctrlTaxAmount.clear();
						break;
					}

					TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
					param.setInside(panel.ctrlTaxDivision.isSelected(0)); // 内税or外税
					param.setAmount(inAmount); // 対象金額
					param.setTax(tax); // 消費税情報
					param.setDigit(currency.getDecimalPoint()); // 小数点桁数
					param.setReceivingFunction(kconf.getReceivingFunction()); // 借受
					param.setPaymentFunction(kconf.getPaymentFunction()); // 仮払

					if (isInvoice && !chkSlipTypeInvoice()) {

						setInvoiceTaxAmount(param, inAmount);

					} else {
						// インボイス設定false
						panel.ctrlTaxAmount.setNumberValue(calculator.calculateTax(param));
					}

					break;
			}

		} finally {
			// 邦貨換算
			BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
			panel.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

			// 合計計算
			summary();
		}
	}

	/**
	 * 貸借切替
	 */
	protected void changedDC() {

		// 合計計算
		summary();
	}

	/**
	 * 新規 <br>
	 * アラートチェックを行わない
	 */
	protected void doNew() {

		doNew(false);

		// 退避行の初期化
		beforeGyoNo = -1;
		// 行編集解除
		isEditedDetail = false;
	}

	/**
	 * 新規
	 * 
	 * @param isAlert アラートチェックを行うか否か
	 */
	protected void doNew(boolean isAlert) {

		// 行確定していないデータチェック
		if (isAlert && inputDetailCheckAlert) {
			if (!inputDetailCheckAlert()) {
				return;
			}
		}

		// 選択クリア
		panel.tbl.clearSelection();

		// 「*」クリア
		panel.tbl.setShowRowHeaderStar(false);

		// 行XXボタン
		controllButtons();

		// クリア
		clearInput();

		// フォーカス
		panel.ctrlKDepartment.requestTextFocus();

		// 退避行の初期化
		beforeGyoNo = -1;
		// 行編集解除
		isEditedDetail = false;

	}

	/**
	 * 挿入
	 */
	protected void doInsert() {
		if (!checkInput()) {
			return;
		}

		if (!panel.tbl.isShowRowHeaderStar()) {
			// 「*」選択なし
			return;
		}

		SWK_DTL dtl = createSlipDetail();

		// 入力値反映
		reflect(dtl);

		// 退避行の初期化を先に実施
		this.beforeGyoNo = -1;
		// 行編集済を先に実施
		this.isEditedDetail = false;

		// 最終行に追加
		List<Object> list = getRow(dtl);
		int insertRowIndex = panel.tbl.getRowHeaderStarIndex();
		panel.tbl.insertRow(insertRowIndex, list);

		// クリア
		doNew();
	}

	/**
	 * スプレッド行番号クリック処理
	 */
	protected void clickTableRowHeader() {

		// 消込行は行挿入禁止
		if (dispatchDetail != null && !dispatchDetail.isErasing()) {
			if (panel.tbl.getSelectedRow() == panel.tbl.getRowHeaderStarIndex()) {
				panel.tbl.setShowRowHeaderStar(false);
				panel.btnRowInsert.setEnabled(false);
			} else {
				panel.tbl.setShowRowHeaderStar(true);
				panel.btnRowInsert.setEnabled(true);
			}
		} else {
			panel.tbl.setShowRowHeaderStar(false);
			panel.btnRowInsert.setEnabled(false);
		}

		{
			// 新規行モード

			// 選択クリア
			panel.tbl.clearSelection();

			// 行XXボタン
			controllButtons();

			// クリア
			clearInput();

			// フォーカス
			panel.ctrlKDepartment.requestTextFocus();
		}
	}

	/**
	 * 複写
	 */
	protected void doCopy() {

		if (!checkInput()) {
			return;
		}

		SWK_DTL dtl = createSlipDetail();

		// 入力値反映
		reflect(dtl);

		// 最終行に追加
		List<Object> list = getRow(dtl);
		panel.tbl.addRow(list);

		// クリア
		doNew();
	}

	/**
	 * 削除
	 */
	protected void doDelete() {

		if (!showConfirmMessage(panel, FocusButton.NO, "Q00007")) {// 削除しますか？
			return;
		}

		int row = panel.tbl.getSelectedRow();

		// 行排他解除
		unlock((SWK_DTL) panel.tbl.getRowValueAt(row, SC.bean));

		// 一覧から削除
		panel.tbl.removeRow(row);

		// クリア
		doNew();
	}

	/**
	 * 行単位の排他解除
	 * 
	 * @param dtl 対象明細
	 */
	protected void unlock(SWK_DTL dtl) {

		try {
			// AP行排他解除
			AP_ZAN apZan = dtl.getAP_ZAN();
			if (apZan != null) {
				BalanceCondition condition = createBalanceCondition();
				condition.setCompanyCode(getCompanyCode());
				condition.setSlipNo(apZan.getZAN_DEN_NO());
				condition.setSlipLineNo(apZan.getZAN_GYO_NO());

				request(getBalanceManagerClass(), "unlockApBalance", condition);
			}

			// AR行排他解除
			AR_ZAN arZan = dtl.getAR_ZAN();
			if (arZan != null) {
				BalanceCondition condition = createBalanceCondition();
				condition.setCompanyCode(getCompanyCode());
				condition.setSlipNo(arZan.getZAN_SEI_DEN_NO());
				condition.setSlipLineNo(arZan.getZAN_SEI_GYO_NO());

				request(getBalanceManagerClass(), "unlockArBalance", condition);
			}

			if (isUseBS) {
				// BS行排他解除
				SWK_DTL bs = dtl.getBsDetail();
				if (bs != null) {
					BSEraseCondition condition = createBSEraseCondition();
					condition.setCompanyCode(getCompanyCode());
					condition.add(bs.getSWK_DEN_NO(), bs.getSWK_GYO_NO());

					request(getBSEraseManagerClass(), "unlock", condition);
				}
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * @return 債務、債権残高マネージャ
	 */
	protected Class getBalanceManagerClass() {
		return BalanceManager.class;
	}

	/**
	 * @return BS勘定消込マネージャ
	 */
	protected Class getBSEraseManagerClass() {
		return BSEraseManager.class;
	}

	/**
	 * @return 伝票明細構築用マネージャ
	 */
	protected Class getManagerClass() {
		return SlipManager.class;
	}

	/**
	 * @return 債務、債権残高抽出条件
	 */
	protected BalanceCondition createBalanceCondition() {
		return new BalanceCondition();
	}

	/**
	 * @return BS勘定消込抽出条件
	 */
	protected BSEraseCondition createBSEraseCondition() {
		return new BSEraseCondition();
	}

	/**
	 * 確定
	 */
	protected void doEntry() {

		if (!checkInput()) {
			return;
		}

		// 選択状態の場合は更新
		boolean isUpdate = panel.tbl.getSelectedRow() != -1;

		SWK_DTL dtl = isUpdate ? (SWK_DTL) panel.tbl.getSelectedRowValueAt(SC.bean) : createSlipDetail();

		// 入力値反映
		reflect(dtl);

		// 行の追加
		List<Object> list = getRow(dtl);

		if (isUpdate) {
			panel.tbl.modifySelectedRow(list);

		} else {
			panel.tbl.addRow(list);
		}

		// 画面クリア
		doNew();
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {

		// 計上部門
		if (!checkInputBlank(panel.ctrlKDepartment.code, panel.ctrlKDepartment.btn.getText())) {
			return false;
		}

		// 科目
		TItemGroup item = panel.ctrlItem;
		if (!checkInputBlank(item.ctrlItemReference.code, item.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlSubItemReference.code, item.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlDetailItemReference.code, item.ctrlDetailItemReference.btn.getText())) {
			return false;
		}

		// 発生日(ブランク可能の場合、チェック不要)
		boolean checkOccurDate = !isAllowOccurDateBlank();
		if (item.getEntity() != null) {
			Item itemBean = item.getEntity().getPreferedItem();
			if (itemBean.isUseOccurDate() && !Util.isNullOrEmpty(panel.ctrlCurrency.getCode())
				&& !keyCurrency.getCode().equals(panel.ctrlCurrency.getCode())) {
				// ブランク可能、且つ基軸通貨以外の場合は科目のフラグに従ってチェック
				checkOccurDate = true;
			}
		}

		if (checkOccurDate
			&& !checkInputBlank(panel.ctrlOccurDate.getCalendar(), panel.ctrlOccurDate.getLabel().getText())) {
			return false;
		}

		// 通貨
		if (!checkInputBlank(panel.ctrlCurrency.code, panel.ctrlCurrency.btn.getText())) {
			return false;
		}

		// レート
		if (!checkInputBlank(panel.ctrlRate.getField(), panel.ctrlRate.getLabel().getText())) {
			return false;
		}

		// 税区分
		if (!checkInputBlank(panel.ctrlTax.code, panel.ctrlTax.btn.getText())) {
			return false;
		}

		// 入力金額
		BigDecimal inKin = panel.ctrlInputAmount.getBigDecimal();

		// 基軸通貨と異なる通貨の場合は、0を認める.
		if (keyCurrency.getCode().equals(panel.ctrlCurrency.getCode())) {
			if (DecimalUtil.isZero(inKin)) {
				showMessage("I00037", panel.ctrlInputAmount.getLabel().getText());
				panel.ctrlInputAmount.requestFocus();
				return false;
			}
		}

		// 消費税額 (0でもOK)
		BigDecimal taxInKin = panel.ctrlTaxAmount.getBigDecimal();
		if (!DecimalUtil.isZero(taxInKin)) {

			if (!DecimalUtil.isZero(inKin) && inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// 入力金額と消費税額の符号が異なります。
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// 消費税額は入力金額未満である必要があります。
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}
		}

		// 邦貨金額
		BigDecimal kin = panel.ctrlKeyAmount.getBigDecimal();
		if (DecimalUtil.isZero(kin)) {
			showMessage("I00037", panel.ctrlKeyAmount.getLabel().getText());// {0}を入力してください。
			panel.ctrlKeyAmount.requestFocus();

			return false;
		}

		if (!DecimalUtil.isZero(inKin) && inKin.signum() != kin.signum()) {
			showMessage("I00125");// 入力金額と邦貨金額の符号が異なります。
			panel.ctrlKeyAmount.requestFocus();
			return false;
		}

		// 取引先
		if (!checkInputBlank(panel.ctrlCustomer.code, panel.ctrlCustomer.btn.getText())) {
			return false;
		}
		// 社員
		if (!checkInputBlank(panel.ctrlEmployee.code, panel.ctrlEmployee.btn.getText())) {
			return false;
		}
		// 管理1～6
		if (!checkInputBlank(panel.ctrlManage1.code, panel.ctrlManage1.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage2.code, panel.ctrlManage2.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage3.code, panel.ctrlManage3.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage4.code, panel.ctrlManage4.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage5.code, panel.ctrlManage5.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage6.code, panel.ctrlManage6.btn.getText())) {
			return false;
		}

		if (isUseHasDateChk && !chkHasDate()) {
			showMessage("I01161"); // 発生日に対応する通貨レートが設定されていません。
			panel.ctrlOccurDate.requestFocus();

			return false;
		}

		// インボイス専用
		if (isInvoice && !chkSlipTypeInvoice()) {

			// invoice用消費税科目が設定されている場合警告メッセージ
			if (!checkInvoiceItemTaxCode()) {
				return false;
			}

			String taxMsg = null;

			if (!panel.ctrlTax.isEmpty() && panel.ctrlTax.getEntity().isNO_INV_REG_FLG()
				&& Util.isNullOrEmpty(panel.ctrlCustomer.getCode())) {
				// セットされている消費税コードが非適格請求書発行事業者の場合取引先の入力必須
				showMessage("I01125", panel.ctrlCustomer.btn.getText());// 取引先が入力不可の科目には、非適格事業者向けの消費税コードは選択できません。

				panel.requestFocusInWindow();

				return false;
			}

			// 取引先リファレンス未設定の場合null
			if (panel.ctrlCustomer.isEmpty()) {
				return true;
			}

			// 明細の取引先情報を取得する。
			Customer bean = setCustomerEntity(panel.ctrlKCompany.getCode(), panel.ctrlCustomer.getCode());

			if (bean == null) {
				return true;

			} else if (bean.isNO_INV_REG_FLG()) {

				if (!panel.ctrlTax.isEmpty() && !panel.ctrlTax.getEntity().isNO_INV_REG_FLG()) {

					taxMsg = panel.ctrlTax.getEntity().getCode() + ":" + panel.ctrlTax.getEntity().getNames();
					if (!showConfirmMessage("I01107", "C12197", taxMsg)) {
						// 非適格請求書発行事業者に対して【{消費税}】が設定されています。続行しますか？
						return false;
					}
				}
			} else {

				if (!panel.ctrlTax.isEmpty() && panel.ctrlTax.getEntity().isNO_INV_REG_FLG()) {
					taxMsg = panel.ctrlTax.getEntity().getCode() + ":" + panel.ctrlTax.getEntity().getNames();
					if (!showConfirmMessage("I01107", "C12196", taxMsg)) {
						// 適格請求書発行事業者に対して【{消費税}】が設定されています。続行しますか？
						return false;
					}
				}

			}
		}

		return true;
	}

	/**
	 * 2023INVOICE制度用エラーメッセージ：消費税科目が入力されている場合エラー
	 * 
	 * @return false:NG
	 */
	protected boolean checkInvoiceItemTaxCode() {

		try {

			String kmk = Util.avoidNull(panel.ctrlItem.ctrlItemReference.getCode());
			String hkm = Util.avoidNull(panel.ctrlItem.ctrlSubItemReference.getCode());
			String ukm = Util.avoidNull(panel.ctrlItem.ctrlDetailItemReference.getCode());

			if (TLoginInfo.isTaxAutoItem(kmk, hkm, ukm)) {
				if (!showConfirmMessage("I01118")) {
					// 消費税科目が直接入力されています。続行しますか？
					return false;
				}

			}

		} catch (Exception e) {
			errorHandler(e);
		}
		return true;

	}

	/**
	 * 入力ブランクチェック
	 * 
	 * @param field 対象フィールド
	 * @param name エラー時の表示名
	 * @return false:NG
	 */
	protected boolean checkInputBlank(TTextField field, String name) {

		if (field.isVisible() && field.isEditable() && field.isEmpty()) {
			showMessage("I00037", name);// {0}を入力してください。
			field.requestFocusInWindow();
			return false;
		}

		return true;
	}

	/**
	 * 発生日に対応する通貨レートが設定されるか
	 * 
	 * @return true：設定される false：設定されない
	 */
	protected boolean chkHasDate() {
		try {
			Date hasDate = panel.ctrlOccurDate.getValue();
			String curCode = panel.ctrlCurrency.getCode();

			// 基軸通貨の場合
			if (curCode.equals(keyCurrency.getCode())) {
				return true;
			}

			if (hasDate == null && !panel.ctrlOccurDate.isEnabled() || isAllowOccurDateBlank()) {
				hasDate = baseDate;
			}

			// レートを取得する
			BigDecimal rate = getCurrencyRateByOccurDate(hasDate);
			if (rate != null) {
				panel.ctrlRate.setNumberValue(rate);
				return true;
			}
		} catch (Exception e) {
			errorHandler(e);
		}
		return false;
	}

	/**
	 * 最上位へ移動
	 */
	protected void doMoveTop() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), 0);
			controllButtons();

			panel.btnRowDown.requestFocus();

			// 退避行のセット
			beforeGyoNo = panel.tbl.getSelectedRow();
			// 行編集解除
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * 上位へ移動
	 */
	protected void doMoveUp() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), panel.tbl.getSelectedRow() - 1);
			controllButtons();

			if (!panel.btnRowDown.isEnabled()) {
				panel.btnRowDown.requestFocus();
			}

			// 退避行のセット
			beforeGyoNo = panel.tbl.getSelectedRow();
			// 行編集解除
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * 下位へ移動
	 */
	protected void doMoveDown() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), panel.tbl.getSelectedRow() + 1);
			controllButtons();
			if (!panel.btnRowUnder.isEnabled()) {
				panel.btnRowUp.requestFocus();
			}

			// 退避行のセット
			beforeGyoNo = panel.tbl.getSelectedRow();
			// 行編集解除
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * 最下位へ移動
	 */
	protected void doMoveUnder() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), panel.tbl.getRowCount() - 1);
			controllButtons();

			panel.btnRowUp.requestFocus();

			// 退避行のセット
			beforeGyoNo = panel.tbl.getSelectedRow();
			// 行編集解除
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * スプレッド選択行変更
	 */
	protected void selectedJornal() {

		isProcess = true;

		try {

			int row = panel.tbl.getSelectedRow();

			if (row < 0) {
				dispatch(null);

			} else {
				SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(row, SC.bean);
				dispatch(dtl);

				// 選択行の通貨に切替
				if (dtl.getCurrency() != null) {
					panel.cbxCurrencyOnTotal.setSelectedText(dtl.getCurrency().getCode());
				}
			}

			controllButtons();

			// 退避行のセット
			beforeGyoNo = panel.tbl.getSelectedRow();
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * 画面に反映
	 * 
	 * @param dtl 対象明細
	 */
	protected void dispatch(SWK_DTL dtl) {

		this.dispatchDetail = dtl;

		// クリア
		clearInput();

		if (dtl == null) {
			return;
		}
		if (dtl.getAppropriateCompany() != null) {
			setCompany(dtl.getAppropriateCompany()); // 計上会社
		} else {
			panel.ctrlKCompany.setCode(dtl.getKAI_CODE());
			panel.ctrlKCompany.refleshAndShowEntity();
			if (panel.ctrlKCompany.isEmpty()) {
				setCompany(getCompany());
			}
		}
		if (dtl.getDepartment() != null) {
			setDepartment(dtl.getDepartment()); // 計上部門
		} else {
			panel.ctrlKDepartment.setCode(dtl.getSWK_DEP_CODE());
			panel.ctrlKDepartment.refleshAndShowEntity();
		}

		Item item = dtl.getItem();
		panel.ctrlItem.setEntity(item != null ? item.clone() : null); // 科目
		changedItem();

		setCurrecy(dtl.getCurrency()); // 通貨コード

		panel.ctrlTax.setEntity(dtl.getTax()); // 税
		changedTax();

		if (dtl.isErasing()) {
			// 入力不可
			panel.btnRowCopy.setEnabled(false);

			panel.ctrlKCompany.setEditable(false);
			panel.ctrlKDepartment.setEditable(false);
			panel.ctrlItem.ctrlItemReference.setEditable(false);
			panel.ctrlItem.ctrlSubItemReference.setEditable(false);
			panel.ctrlItem.ctrlDetailItemReference.setEditable(false);
			panel.ctrlOccurDate.setEditable(false);
			panel.ctrlTaxDivision.setEnabled(false);
			panel.ctrlDrCr.setEnabled(false);

			panel.ctrlCurrency.setEditable(false);
			panel.ctrlRate.setEditable(false);
			panel.ctrlTax.setEditable(false);
			panel.ctrlTaxAmount.setEditable(false);
			panel.ctrlCustomer.setEditable(false);
			panel.ctrlEmployee.setEditable(false);
			panel.ctrlManage1.setEditable(false);
			panel.ctrlManage2.setEditable(false);
			panel.ctrlManage3.setEditable(false);
			panel.ctrlManage4.setEditable(false);
			panel.ctrlManage5.setEditable(false);
			panel.ctrlManage6.setEditable(false);
			panel.ctrlNonAcDetails.setEditable(false);

			if (dtl.isAPErasing() || (isUseBS && dtl.isBSErasing())) {
				// AP、BSの場合は金額入力不可
				panel.ctrlInputAmount.setEditable(false);
				panel.ctrlKeyAmount.setEditable(false);
			}
		}

		// 税
		if (dtl.getSWK_ZEI_KBN() != SWK_DTL.ZEI_KBN.TAX_OUT) {
			panel.ctrlTaxDivision.setSelectON(0);

		} else {
			panel.ctrlTaxDivision.setSelectON(1);
		}

		panel.ctrlKeyAmount.setNumberValue(dtl.getSWK_KIN()); // 金額
		panel.ctrlRate.setNumberValue(dtl.getSWK_CUR_RATE()); // 通貨レート
		panel.ctrlInputAmount.setNumberValue(dtl.getSWK_IN_KIN()); // 入力金額

		// 入力消費税額
		if (dtl.getTax() == null || DecimalUtil.isNullOrZero(dtl.getTax().getRate())) {
			panel.ctrlTaxAmount.clear();

		} else {
			panel.ctrlTaxAmount.setNumberValue(dtl.getSWK_IN_ZEI_KIN());
		}

		// 発生日
		Date occurDate = dtl.getHAS_DATE();
		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		panel.ctrlKeyAmount.setNumberValue(dtl.getSWK_KIN()); // 基軸金額
		panel.ctrlRemark.setCode(dtl.getSWK_GYO_TEK_CODE()); // 行摘要コード
		panel.ctrlRemark.setNames(dtl.getSWK_GYO_TEK()); // 行摘要
		panel.ctrlDrCr.setDR(dtl.isDR()); // 貸借
		panel.ctrlCustomer.setCode(dtl.getSWK_TRI_CODE()); // 取引先コード
		panel.ctrlCustomer.setNames(dtl.getSWK_TRI_NAME_S()); // 取引先
		panel.ctrlEmployee.setCode(dtl.getSWK_EMP_CODE()); // 社員コード
		panel.ctrlEmployee.setNames(dtl.getSWK_EMP_NAME_S()); // 社員
		panel.ctrlManage1.setCode(dtl.getSWK_KNR_CODE_1()); // 管理1コード
		panel.ctrlManage1.setNames(dtl.getSWK_KNR_NAME_S_1()); // 管理1
		panel.ctrlManage2.setCode(dtl.getSWK_KNR_CODE_2()); // 管理2コード
		panel.ctrlManage2.setNames(dtl.getSWK_KNR_NAME_S_2()); // 管理2
		panel.ctrlManage3.setCode(dtl.getSWK_KNR_CODE_3()); // 管理3コード
		panel.ctrlManage3.setNames(dtl.getSWK_KNR_NAME_S_3()); // 管理3
		panel.ctrlManage4.setCode(dtl.getSWK_KNR_CODE_4()); // 管理4コード
		panel.ctrlManage4.setNames(dtl.getSWK_KNR_NAME_S_4()); // 管理4
		panel.ctrlManage5.setCode(dtl.getSWK_KNR_CODE_5()); // 管理5コード
		panel.ctrlManage5.setNames(dtl.getSWK_KNR_NAME_S_5()); // 管理5
		panel.ctrlManage6.setCode(dtl.getSWK_KNR_CODE_6()); // 管理6コード
		panel.ctrlManage6.setNames(dtl.getSWK_KNR_NAME_S_6()); // 管理6
		panel.ctrlOccurDate.setValue(getOccurDate(dtl)); // 発生日
		panel.ctrlNonAcDetails.setValue(1, dtl.getSWK_HM_1()); // 非会計明細1
		panel.ctrlNonAcDetails.setValue(2, dtl.getSWK_HM_2()); // 非会計明細2
		panel.ctrlNonAcDetails.setValue(3, dtl.getSWK_HM_3()); // 非会計明細3

		// 合計計算
		summary();
	}

	/**
	 * 画面へ貼り付ける時発生日の値
	 * 
	 * @param dtl
	 * @return 発生日
	 */
	protected Date getOccurDate(SWK_DTL dtl) {

		if (isAllowOccurDateBlank()) {

			if (dtl.getItem() != null && !dtl.isUseItemOccurDate()) {
				// 科目が発生日未使用の場合、発生日はブランクにする
				return null;
			}
		}

		return dtl.getHAS_DATE();
	}

	/**
	 * リスト変換
	 * 
	 * @param dtl 明細
	 * @return リスト
	 */
	public List<Object> getRow(SWK_DTL dtl) {

		// 小数点桁数
		int digit = getCompany().getAccountConfig().getKeyCurrency().getDecimalPoint();
		if (dtl.getCurrency() != null) {
			digit = dtl.getCurrency().getDecimalPoint();
		}

		List<Object> list = new ArrayList(SC.values().length);
		list.add(dtl); // Entity
		list.add(dtl.getSWK_K_KAI_CODE()); // 計上会社コード
		list.add(dtl.getSWK_K_KAI_NAME_S()); // 計上会社
		list.add(dtl.getSWK_DEP_CODE()); // 部門コード
		list.add(dtl.getSWK_DEP_NAME_S()); // 部門
		list.add(dtl.getSWK_KMK_CODE()); // 科目コード
		list.add(dtl.getSWK_KMK_NAME_S()); // 科目
		list.add(dtl.getSWK_HKM_CODE()); // 補助コード
		list.add(dtl.getSWK_HKM_NAME_S()); // 補助
		list.add(dtl.getSWK_UKM_CODE()); // 内訳科目コード
		list.add(dtl.getSWK_UKM_NAME_S()); // 内訳

		String zkbn = "";
		switch (dtl.getSWK_ZEI_KBN()) {
			case 0:
				zkbn = getWord("C00337");// 外税
				break;
			case 1:
				zkbn = getWord("C00023");// 内税
				break;
			case 2:
				zkbn = getWord("C01971");// 非課税
				break;
		}
		list.add(getWord(zkbn)); // 税区分
		list.add(dtl.getSWK_ZEI_CODE()); // 消費税コード
		list.add(dtl.getSWK_ZEI_NAME_S()); // 消費税名称
		list.add(DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_RATE()) ? "" : NumberFormatUtil.formatNumber(
			dtl.getSWK_ZEI_RATE(), 1)
			+ "%"); // 税率
		list.add(NumberFormatUtil.formatNumber(dtl.getSWK_KIN(), keyDigit)); // 金額
		list.add(dtl.getSWK_CUR_CODE()); // 通貨コード
		list.add(NumberFormatUtil.formatNumber(dtl.getSWK_CUR_RATE(), 4)); // 通貨レート

		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getDebitInputAmount(), digit)); // 借方金額(外貨)
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getDebitKeyAmount(), keyDigit)); // 借方金額
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getSWK_IN_ZEI_KIN(), digit)); // 消費税額(外貨)
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getSWK_ZEI_KIN(), keyDigit)); // 消費税額
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getCreditInputAmount(), digit)); // 貸方金額(外貨)
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getCreditKeyAmount(), keyDigit)); // 貸方金額
		list.add(dtl.getSWK_GYO_TEK_CODE()); // 行摘要コード
		list.add(dtl.getSWK_GYO_TEK()); // 行摘要
		list.add(dtl.getSWK_TRI_CODE()); // 取引先コード
		list.add(dtl.getSWK_TRI_NAME_S()); // 取引先
		list.add(dtl.getSWK_EMP_CODE()); // 社員コード
		list.add(dtl.getSWK_EMP_NAME_S()); // 社員
		list.add(dtl.getSWK_KNR_CODE_1()); // 管理1コード
		list.add(dtl.getSWK_KNR_NAME_S_1()); // 管理1
		list.add(dtl.getSWK_KNR_CODE_2()); // 管理2コード
		list.add(dtl.getSWK_KNR_NAME_S_2()); // 管理2
		list.add(dtl.getSWK_KNR_CODE_3()); // 管理3コード
		list.add(dtl.getSWK_KNR_NAME_S_3()); // 管理3
		list.add(dtl.getSWK_KNR_CODE_4()); // 管理4コード
		list.add(dtl.getSWK_KNR_NAME_S_4()); // 管理4
		list.add(dtl.getSWK_KNR_CODE_5()); // 管理5コード
		list.add(dtl.getSWK_KNR_NAME_S_5()); // 管理5
		list.add(dtl.getSWK_KNR_CODE_6()); // 管理6コード
		list.add(dtl.getSWK_KNR_NAME_S_6()); // 管理6
		list.add(DateUtil.toYMDString(getOccurDate(dtl))); // 発生日
		list.add(dtl.getSWK_HM_1()); // 非会計明細1
		list.add(dtl.getSWK_HM_2()); // 非会計明細2
		list.add(dtl.getSWK_HM_3()); // 非会計明細3
		list.add(NumberFormatUtil.formatNumber(dtl.getSWK_IN_KIN(), digit)); // 入力金額

		return list;
	}

	/**
	 * 画面入力値をセット
	 * 
	 * @param dtl 明細
	 */
	protected void reflect(SWK_DTL dtl) {
		dtl.setAppropriateCompany(panel.ctrlKCompany.getEntity()); // 計上会社
		dtl.setDepartment(panel.ctrlKDepartment.getEntity()); // 計上部門
		dtl.setItem(panel.ctrlItem.getEntity()); // 科目
		dtl.setTax(panel.ctrlTax.getEntity()); // 税
		dtl.setCurrency(panel.ctrlCurrency.getEntity()); // 通貨
		dtl.setCustomer(panel.ctrlCustomer.getEntity()); // 取引先

		// 税
		ConsumptionTax tax = dtl.getTax();
		if (tax == null || tax.getTaxType() == TaxType.NOT || DecimalUtil.isNullOrZero(tax.getRate())) {
			dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		} else {
			dtl.setSWK_ZEI_KBN(panel.ctrlTaxDivision.isSelected(0) ? SWK_DTL.ZEI_KBN.TAX_IN : SWK_DTL.ZEI_KBN.TAX_OUT); // 税
		}

		dtl.setSWK_CUR_RATE(panel.ctrlRate.getBigDecimal()); // 通貨レート
		dtl.setSWK_IN_KIN(panel.ctrlInputAmount.getBigDecimal()); // 入力金額

		BigDecimal inTaxAmount = panel.ctrlTaxAmount.getBigDecimal(); // 入力消費税
		dtl.setSWK_IN_ZEI_KIN(inTaxAmount); // 入力消費税額

		dtl.setSWK_ZEI_KIN(convertKeyTaxAmount(inTaxAmount)); // 消費税額(邦貨)
		dtl.setSWK_KIN(panel.ctrlKeyAmount.getBigDecimal()); // 基軸金額

		dtl.setSWK_GYO_TEK_CODE(panel.ctrlRemark.getCode()); // 行摘要コード
		dtl.setSWK_GYO_TEK(panel.ctrlRemark.getNames()); // 行摘要
		dtl.setDC(panel.ctrlDrCr.isDR() ? Dc.DEBIT : Dc.CREDIT); // 貸借
		dtl.setSWK_TRI_CODE(panel.ctrlCustomer.getCode()); // 取引先コード
		dtl.setSWK_TRI_NAME(panel.ctrlCustomer.getEntity() == null ? "" : panel.ctrlCustomer.getEntity().getName());
		dtl.setSWK_TRI_NAME_S(panel.ctrlCustomer.getNames());
		dtl.setSWK_EMP_CODE(panel.ctrlEmployee.getCode()); // 社員コード
		dtl.setSWK_EMP_NAME(panel.ctrlEmployee.getEntity() == null ? "" : panel.ctrlEmployee.getEntity().getName());
		dtl.setSWK_EMP_NAME_S(panel.ctrlEmployee.getNames());
		dtl.setSWK_KNR_CODE_1(panel.ctrlManage1.getCode()); // 管理1コード
		dtl.setSWK_KNR_NAME_1(panel.ctrlManage1.getEntity() == null ? "" : panel.ctrlManage1.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_1(panel.ctrlManage1.getNames());
		dtl.setSWK_KNR_CODE_2(panel.ctrlManage2.getCode()); // 管理2コード
		dtl.setSWK_KNR_NAME_2(panel.ctrlManage2.getEntity() == null ? "" : panel.ctrlManage2.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_2(panel.ctrlManage2.getNames());
		dtl.setSWK_KNR_CODE_3(panel.ctrlManage3.getCode()); // 管理3コード
		dtl.setSWK_KNR_NAME_3(panel.ctrlManage3.getEntity() == null ? "" : panel.ctrlManage3.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_3(panel.ctrlManage3.getNames());
		dtl.setSWK_KNR_CODE_4(panel.ctrlManage4.getCode()); // 管理4コード
		dtl.setSWK_KNR_NAME_4(panel.ctrlManage4.getEntity() == null ? "" : panel.ctrlManage4.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_4(panel.ctrlManage4.getNames());
		dtl.setSWK_KNR_CODE_5(panel.ctrlManage5.getCode()); // 管理5コード
		dtl.setSWK_KNR_NAME_5(panel.ctrlManage5.getEntity() == null ? "" : panel.ctrlManage5.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_5(panel.ctrlManage5.getNames());
		dtl.setSWK_KNR_CODE_6(panel.ctrlManage6.getCode()); // 管理6コード
		dtl.setSWK_KNR_NAME_6(panel.ctrlManage6.getEntity() == null ? "" : panel.ctrlManage6.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_6(panel.ctrlManage6.getNames());
		dtl.setSWK_HM_1(panel.ctrlNonAcDetails.getValue(1)); // 非会計明細1
		dtl.setSWK_HM_2(panel.ctrlNonAcDetails.getValue(2)); // 非会計明細2
		dtl.setSWK_HM_3(panel.ctrlNonAcDetails.getValue(3)); // 非会計明細3

		// 発生日
		Date occurDate = panel.ctrlOccurDate.getValue();
		dtl.setHAS_DATE(occurDate); // 発生日
	}

	/**
	 * 合計欄の通貨変更
	 */
	protected void changedCurrencyOnTotal() {
		summaryInAmount();
	}

	/**
	 * 合計表示
	 */
	protected void summary() {
		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;
		BigDecimal drTax = BigDecimal.ZERO;
		BigDecimal crTax = BigDecimal.ZERO;

		int row = panel.tbl.getSelectedRow();

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			if (row == i) {
				continue;
			}

			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);

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

		// 入力分(画面表示で編集中)も足す
		BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
		BigDecimal inTaxAmount = panel.ctrlTaxAmount.getBigDecimal();

		BigDecimal keyAmount = panel.ctrlKeyAmount.getBigDecimal();
		BigDecimal taxAmount = convertKeyTaxAmount(inTaxAmount); // 消費税の基軸金額

		// 外税は合計に消費税プラス
		if (!panel.ctrlTaxDivision.isSelected(0)) {
			inAmount = inAmount.add(inTaxAmount);
			keyAmount = keyAmount.add(taxAmount);
		}

		if (panel.ctrlDrCr.isDR()) {
			dr = dr.add(keyAmount);
			drTax = drTax.add(taxAmount);

		} else {
			cr = cr.add(keyAmount);
			crTax = crTax.add(taxAmount);
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

		// 外貨合計
		summaryInAmount();
	}

	/**
	 * 外貨合計(summary()呼んでから呼ぶ前提)
	 */
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
	protected Map<String, BigDecimal[]> getForeignAmountMap() {

		String keyCode = keyCurrency.getCode();

		Map<String, BigDecimal[]> map = new TreeMap<String, BigDecimal[]>();

		int row = panel.tbl.getSelectedRow();

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			if (row == i) {
				continue;
			}

			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);

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

		// 入力分(画面表示で編集中)も足す
		String panelCode = panel.ctrlCurrency.getCode();
		if (!keyCode.equals(panelCode)) {
			BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
			BigDecimal inTaxAmount = panel.ctrlTaxAmount.getBigDecimal();

			// 外税は合計に消費税プラス
			if (!panel.ctrlTaxDivision.isSelected(0)) {
				inAmount = inAmount.add(inTaxAmount);
			}

			BigDecimal[] dec = map.get(panelCode);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(panelCode, dec);
			}

			if (panel.ctrlDrCr.isDR()) {
				dec[0] = dec[0].add(inAmount); // DR
			} else {
				dec[1] = dec[1].add(inAmount); // CR
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
	 * 画面入力情報を元に基軸金額に換算
	 * 
	 * @param amount 入力金額
	 * @return 基軸通貨金額
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		BigDecimal rate = panel.ctrlRate.getBigDecimal();
		Company kcompany = panel.ctrlKCompany.getEntity();
		Currency currency = panel.ctrlCurrency.getEntity();

		if (kcompany == null || currency == null) {
			return BigDecimal.ZERO;
		}

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * 画面入力情報を元に基軸消費税額に換算
	 * 
	 * @param taxAmount 入力消費税額
	 * @return 基軸通貨消費税額
	 */
	protected BigDecimal convertKeyTaxAmount(BigDecimal taxAmount) {

		if (DecimalUtil.isNullOrZero(taxAmount)) {
			return BigDecimal.ZERO;
		}

		Company kcompany = panel.ctrlKCompany.getEntity();
		Currency currency = panel.ctrlCurrency.getEntity();
		ConsumptionTax tax = panel.ctrlTax.getEntity();
		BigDecimal rate = panel.ctrlRate.getBigDecimal();

		if (kcompany == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(taxAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * 通貨レート取得
	 * 
	 * @return レート
	 */
	protected BigDecimal getCurrencyRate() {
		try {

			Currency currency = panel.ctrlCurrency.getEntity();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}

			Date occurDate = panel.ctrlOccurDate.getValue();

			// 発生日ブランク可能且つブランクの場合、基準日を設定する
			if (isAllowOccurDateBlank() && occurDate == null) {
				occurDate = baseDate;
			}

			if (occurDate == null) {
				return null;
			}

			return (BigDecimal) request(RateManager.class, isClosing ? "getSettlementRate" : "getRate", currency,
				occurDate);

		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
	}

	/**
	 * 通貨レート取得
	 * 
	 * @param hasDate 発生日
	 * 
	 * @return レート
	 */
	protected BigDecimal getCurrencyRateByOccurDate(Date hasDate) {
		BigDecimal rate = null;
		try {

			Currency currency = panel.ctrlCurrency.getEntity();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}
			String key = currency.getCode() + "<>" + DateUtil.toYMDString(hasDate);
			if (rateMap.containsKey(key) && rateMap.get(key) != null) {
				rate = rateMap.get(key);
			} else {
				rate = (BigDecimal) request(RateManager.class,
						isClosing ? "getSettlementRateByOccurDate" : "getRateByOccurDate", currency.getCode(), hasDate);
				rateMap.put(key, rate);
			}

		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
		return rate;
	}

	/**
	 * AP残高消込
	 */
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
			panel.tbl.addRow(getRow(toDetail(zan)));
		}

		// 合計の通貨
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * @param dialog
	 * @return 選択債務残高リスト
	 */
	protected List<AP_ZAN> getSelectedAPBalanceList(TApBalanceListDialogCtrl dialog) {
		return detailConverter.getSelectedAPBalanceList(dialog);
	}

	/**
	 * 残高リスト変換
	 * 
	 * @param zan 債務残高
	 * @return リスト
	 */
	protected SWK_DTL toDetail(AP_ZAN zan) {
		return detailConverter.toDetail(zan);
	}

	/**
	 * AR残高消込
	 */
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
			panel.tbl.addRow(getRow(toDetail(zan)));
		}

		// 合計の通貨
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * @param dialog
	 * @return 選択債権残高リスト
	 */
	protected List<AR_ZAN> getSelectedARBalanceList(TArBalanceListDialogCtrl dialog) {
		return detailConverter.getSelectedARBalanceList(dialog);
	}

	/**
	 * 残高リスト変換
	 * 
	 * @param zan 債権残高
	 * @return リスト
	 */
	protected SWK_DTL toDetail(AR_ZAN zan) {
		return detailConverter.toDetail(zan);
	}

	/**
	 * 仕訳のエンティティを擬似的にセット.
	 * 
	 * @param dtl 仕訳
	 * @return 仕訳
	 */
	@Deprecated
	protected SWK_DTL buildDetail(SWK_DTL dtl) {
		return detailConverter.buildDetail(dtl, getCompany());
	}

	/**
	 * 仕訳のエンティティを擬似的にセット.
	 * 
	 * @param dtl 仕訳
	 * @param kcompany 計上会社コード
	 * @return 仕訳
	 */
	@Deprecated
	protected SWK_DTL buildDetail(SWK_DTL dtl, Company kcompany) {
		return detailConverter.buildDetail(dtl, kcompany);
	}

	/**
	 * BS勘定ボタン押下
	 */
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
			panel.tbl.addRow(getRow(toDetail(bs)));
		}

		// 合計の通貨
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * @param dialog
	 * @return 選択BS勘定リスト
	 */
	protected List<SWK_DTL> getSelectedBSList(BSEraseDialogCtrl dialog) {
		return detailConverter.getSelectedBSList(dialog);
	}

	/**
	 * BS勘定消込ダイアログの作成
	 * 
	 * @return BS勘定消込ダイアログ
	 */
	protected BSEraseDialogCtrl createBSDialog() {

		if (isBsUseKCompany) {

			// 計上会社指定

			Company kcompany = panel.ctrlKCompany.getEntity();
			if (kcompany == null) {
				kcompany = getCompany();
			}

			return new BSEraseDialogCtrl(panel, getProgramInfo(), kcompany);
		}
		return new BSEraseDialogCtrl(panel, getProgramInfo());

	}

	/**
	 * BS消込変換
	 * 
	 * @param bs BS消込
	 * @return 仕訳ジャーナル
	 */
	protected SWK_DTL toDetail(SWK_DTL bs) {
		return detailConverter.toDetail(bs, isBsUseKCompany ? panel.ctrlKCompany.getEntity() : null);
	}

	/**
	 * @return 明細仕訳
	 */
	protected SWK_DTL createSlipDetail() {
		return new SWK_DTL();
	}

	/**
	 * 明細をEntity形式でセットする.
	 * 
	 * @param dtlList データ
	 */
	public void setEntityList(List<SWK_DTL> dtlList) {
		panel.tbl.removeRow();

		for (SWK_DTL dtl : dtlList) {

			// 行の追加
			List<Object> list = getRow(dtl);
			panel.tbl.addRow(list);
		}

		// スクロールバーの位置を左上に
		panel.tbl.getHorizontalScrollBar().setValue(0);
		panel.tbl.getVerticalScrollBar().setValue(0);

		// クリア
		clearInput();

		// 上下ボタン
		controllButtons();

		// 合計の通貨
		makeCurrencyComboBox();

		// 合計
		summary();
	}

	/**
	 * 明細をEntity形式で返す.
	 * 
	 * @return Entityリスト
	 */
	public List<SWK_DTL> getEntityList() {

		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);

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
	 * @return AP残高一覧ダイアログCtrl
	 */
	protected TApBalanceListDialogCtrl createAPBalanceListDialogCtrl() {
		return new TApBalanceListDialogCtrl(panel);
	}

	/**
	 * @return AR残高一覧ダイアログCtrl
	 */
	protected TArBalanceListDialogCtrl createARBalanceListDialogCtrl() {
		return new TArBalanceListDialogCtrl(panel);
	}

	/**
	 * 明細内容が変更されたかどうか警告表示
	 * 
	 * @return true:変更無
	 */
	protected boolean inputDetailCheckAlert() {
		// 行編集済
		if (isEditedDetail) {
			// 明細行が変更されています。行確定を押下しないと編集中の内容はクリアされますがよろしいですか？
			return showConfirmMessage("I01055");
		}
		isEditedDetail = false;
		return true;
	}

	/**
	 * 選択行直後の処理
	 * 
	 * @return boolean
	 */
	protected boolean rowSelectionDetailChanged() {

		// 行確定していないデータチェック
		int row = panel.tbl.getSelectedRow();
		if (row >= 0 && row != beforeGyoNo && inputDetailCheckAlert) {
			if (!inputDetailCheckAlert()) {
				if (beforeGyoNo == -1) {
					// 選択クリア
					panel.tbl.clearSelection();
				} else {
					panel.tbl.setRowSelection(beforeGyoNo);
				}
				return false;
			}
		}
		isEditedDetail = false;
		return true;

	}

	/**
	 * 選択行直後の処理
	 * 
	 * @return boolean
	 */
	protected boolean btnSelectDetailChanged() {

		// 行確定していないデータチェック
		if (inputDetailCheckAlert) {
			if (!inputDetailCheckAlert()) {
				if (beforeGyoNo == -1) {
					// 選択クリア
					panel.tbl.clearSelection();
				} else {
					panel.tbl.setRowSelection(beforeGyoNo);
				}
				return false;
			}
		}
		isEditedDetail = false;
		return true;

	}

	/**
	 * インボイス ON 取引先マスタで非適格請求書発行事業者設定されている消費税コードをセット
	 * 
	 * @param tax 消費税情報
	 */
	protected void setTaxNoInvReg(ConsumptionTax tax) {

		if (chkSlipTypeInvoice()) {// 伝票種別チェック
			return;
		}

		if (tax == null || tax.getTaxType().value != TaxType.PURCHAESE.value
			|| Util.isNullOrEmpty(panel.ctrlCustomer.getCode())) {
			// 科目に設定されている消費税コードが仕入以外または取引先コードが未入力の場合はコードセットしない
			return;
		}

		Customer bean = panel.ctrlCustomer.getEntity();

		if (bean == null) {
			return;
		}

		if (!Util.isNullOrEmpty(bean.getNO_INV_REG_ZEI_CODE())) {
			panel.ctrlTax.code.setValue(bean.getNO_INV_REG_ZEI_CODE());
		} else {
			panel.ctrlTax.code.setValue(tax.getCode());
		}
		panel.ctrlTax.refleshAndShowEntity();

		// 消費税変更処理
		changedTax();

	}

	/**
	 * インボイス 取引先Entity取得
	 * 
	 * @param cmpCode 会社コード
	 * @param code 取引先コード
	 * @return bean
	 */
	protected Customer setCustomerEntity(String cmpCode, String code) {

		List<Customer> list = new ArrayList<Customer>();
		try {

			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(cmpCode);
			condition.setCode(code);

			list = (List<Customer>) request(CustomerManager.class, "get", condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

		} catch (Exception e) {
			errorHandler(e);
		}

		return list.get(0);
	}

	/**
	 * インボイス用消費税セット
	 * 
	 * @param param
	 * @param inAmount
	 */
	protected void setInvoiceTaxAmount(TTaxCalculation param, BigDecimal inAmount) {

		Currency currency = panel.ctrlCurrency.getEntity();
		ConsumptionTax tax = panel.ctrlTax.getEntity();

		if (tax == null) {
			return;
		}

		// 2023INVOICE制度：計算
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal amountDr = BigDecimal.ZERO;
		BigDecimal amountCr = BigDecimal.ZERO;

		BigDecimal taxDr = BigDecimal.ZERO;
		BigDecimal taxCr = BigDecimal.ZERO;
		BigDecimal taxAmountSum = BigDecimal.ZERO;
		BigDecimal swkKin = BigDecimal.ZERO;
		BigDecimal zei = BigDecimal.ZERO;

		int row = 0;
		int zeiKbn = TaxCalcType.IN.value;

		boolean isDr = panel.ctrlDrCr.isSelected(Dc.DEBIT.value);

		if (!panel.ctrlTaxDivision.isSelected(0)) {
			// 外税
			zeiKbn = TaxCalcType.OUT.value;
		}

		TTable tbl = getTable();

		for (int i = 0; i < tbl.getRowCount(); i++) {

			SWK_DTL dtl = (SWK_DTL) tbl.getRowValueAt(i, SC.bean);

			if (isInvoiceTaxSum(i, tbl.getSelectedRow(), dtl)) {
				continue;
			}

			swkKin = getSwkKin(dtl);

			// key:消費税レート、通貨コード、消費税区分
			if (dtl.getSWK_ZEI_RATE().equals(tax.getRate()) && currency.getCode().equals(dtl.getSWK_CUR_CODE())
				&& zeiKbn == dtl.getSWK_ZEI_KBN()) {

				zei = dtl.getSWK_IN_ZEI_KIN();

				if (isCredit(dtl)) {
					amountCr = amountCr.add(swkKin);
					taxCr = taxCr.add(zei);
				} else {
					amountDr = amountDr.add(swkKin);
					taxDr = taxDr.add(zei);
				}

				row++;
			}
		}

		// 合計する明細が1行でも存在した場合
		if (row > 0) {

			// 画面値の金額を足す
			if (isDr) {
				amountDr = amountDr.add(inAmount);
			} else {
				amountCr = amountCr.add(inAmount);
			}
			// 減算
			amountCr = amountCr.negate();
			amount = amountDr.add(amountCr);

			taxCr = taxCr.negate();
			taxAmountSum = taxDr.add(taxCr);

			param.setAmount(amount); // 対象金額
			amount = calculator.calculateTax(param);

			taxAmountSum = amount.subtract(taxAmountSum);

			if ((taxAmountSum.signum() == -1 && inAmount.signum() >= 0)
				|| (taxAmountSum.signum() >= 0 && inAmount.signum() == -1)) {

				if (isDr) {
					// 借方：値が入力金額と異なる正負の場合ゼロ
					taxAmountSum = BigDecimal.ZERO;
				} else {
					// 貸方：値が入力金額と異なる正負の場合正負を逆にする
					taxAmountSum = taxAmountSum.negate();
				}

			}

			panel.ctrlTaxAmount.setNumberValue(taxAmountSum);

		} else {
			// 選択行以外に合計する明細ない場合
			panel.ctrlTaxAmount.setNumberValue(calculator.calculateTax(param));
		}
	}

	/**
	 * invoice用 仕訳金取得
	 * 
	 * @param dtl
	 * @return 仕訳金額
	 */
	protected BigDecimal getSwkKin(SWK_DTL dtl) {
		return DecimalUtil.avoidNull(dtl.getSWK_IN_KIN());
	}

	/**
	 * invoice用 明細テーブルの取得
	 * 
	 * @return tbl
	 */
	protected TTable getTable() {
		return panel.tbl;
	}

	/**
	 * invoice用 消費税計算する明細か判断
	 * 
	 * @param row
	 * @param selectRow
	 * @param dtl
	 * @return true continueする
	 */
	protected boolean isInvoiceTaxSum(int row, int selectRow, SWK_DTL dtl) {

		if (row == selectRow || Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())
			|| DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_RATE()) || Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
			// 選択行 または 税コード、税レート、通貨コードがnullの場合 スルー
			return true;
		}
		return false;
	}

	/**
	 * invoice用 明細のDrCr取得
	 * 
	 * @param dtl
	 * @return dtl
	 */
	protected boolean isCredit(SWK_DTL dtl) {
		return !dtl.isDR();
	}

}
