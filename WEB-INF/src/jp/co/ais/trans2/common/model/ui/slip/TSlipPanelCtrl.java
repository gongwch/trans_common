package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel.*;
import jp.co.ais.trans2.common.objsave.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.item.ItemSearchCondition.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tag.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 伝票入力コントローラ
 */
public abstract class TSlipPanelCtrl extends TController implements TExclusive {

	/** true:伝票複写時伝票日付も複写<Client> */
	public static boolean notReservationSlipDate = ClientConfig.isFlagOn("trans.slip.copy.notreservation.slipdate");

	/** true:伝票複写時決算区分も複写<Client> */
	public static boolean notReservationCloseEntry = ClientConfig.isFlagOn("trans.slip.copy.notreservation.closeentry");

	/** true:伝票複写時発生日を伝票日付に変更<Client> */
	public static boolean isCopySlipDateToOccurDate = ClientConfig
		.isFlagOn("trans.slip.detail.copy.slipdate.to.occurdate");

	/** true:ファイル添付機能有効 */
	public static boolean isUseAttachment = ClientConfig.isFlagOn("trans.slip.use.attachment");

	/** true:一時プレビュー機能有効 */
	public static boolean isUseTempPreview = ClientConfig.isFlagOn("trans.slip.use.temp.preview");

	/** true:同じ行摘要をクリアする機能無効 */
	public static boolean isNoClearSameRemark = ClientConfig.isFlagOn("trans.slip.detail.noclear.same.remark");

	/** true:同じ発生日をクリアする機能有効 */
	public static boolean isClearSameOccurDate = ClientConfig.isFlagOn("trans.slip.detail.clear.same.occurdate");

	/** true:BS勘定は月末日を利用＜Client＞ */
	public static boolean isBsTermLastDay = ClientConfig.isFlagOn("trans.slip.bs.term.lastday");

	/** true:辞書登録機能有効 */
	public static boolean isUsePatternSave = ClientConfig.isFlagOn("trans.slip.use.pattern.save");

	/** true:付箋機能有効 */
	public static boolean isUseTag = ClientConfig.isFlagOn("trans.slip.use.tag");

	/** 計算ロジック */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** 会計系設定 */
	protected AccountConfig conf = getCompany().getAccountConfig();

	/** 基軸通貨 */
	protected Currency keyCurrency = conf.getKeyCurrency();

	/** 基軸通貨小数点桁数 */
	protected int keyDigit = keyCurrency.getDecimalPoint();

	/** 伝票種別 */
	protected SlipType slipType;

	/** 指示画面 */
	protected TSlipPanel mainView = null;

	/** 伝票データ(編集時) */
	protected Slip slip;

	/** 辞書登録ダイアログ */
	protected TSlipPatternSaveDialog patternSaveView;

	/** ヘッダー部門はログイン会社をセットするか？(根底部で宣言) */
	protected boolean isDepSetting = ClientConfig.isFlagOn("trans.slip.department.default.setting");

	/** 修正時：支払日を再計算させない(AP用に根底部で宣言) */
	protected boolean isNotChangePaymentDay = ClientConfig.isFlagOn("trans.slip.modify.not.change.paymentday");

	/** 編集か複写か(true:編集)(AP用に根底部で宣言) */
	protected boolean isModifyMode = false;

	/** 保留チェックボックスを非表示とするか(AP用に根底部で宣言) */
	protected boolean isHideSuspention = ClientConfig.isFlagOn("trans.AP0010.hide.suspension.check");

	/** 支払日のデフォルトを臨時とするか(AP用に根底部で宣言) */
	protected boolean isDefaultManual = ClientConfig.isFlagOn("trans.AP0010.paymentday.default.manual");

	/** true: 2023INVOICE制度対応を使用する */
	public static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** true: 2023INVOICE制度対応を使用する(会社情報含む) */
	public boolean isInvoice = false;

	/** 辞書の伝票種別(IFRS) */
	protected String patternSlipType = null;

	/** 発生日チェックを使用するか */
	protected boolean isUseHasDateChk = conf.isUseHasDateCheck();

	/** ﾚｰﾄキャッシュマップ */
	protected Map<String, BigDecimal> rateMapByOccurDate = new HashMap<String, BigDecimal>();

	/**
	 * コンストラクタ.
	 */
	public TSlipPanelCtrl() {
		// 指示画面生成
		mainView = createMainView();

		if (mainView.isTFormMode()) {
			// Tフォームモードの場合、伝票種別タイプを設定する
			((TFormSlipDetailPanel) mainView.ctrlDetail).setSlipInputType(getSlipInputType());
			((TFormSlipDetailPanel) mainView.ctrlDetail).init(mainView.getTFormDetailCount());
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
	@Override
	public void start() {

		try {

			// インボイス使用するかどうか
			if (isInvoiceTaxProperty) {
				initInvoiceFlg();
			}

			// 伝票種類別の初期設定
			initSlipType();

			// 一時伝票保存/復旧の初期化
			initSaveLoad();

			// 指示画面を初期化する
			initHeaderView();
			initDetailView();

			// イベント登録
			addViewEvent();

			// 排他解除
			unlockAll();

			// 初期フォーカス
			requestFocusFirst();

			// 指示画面表示
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 初期フォーカス
	 */
	protected void requestFocusFirst() {
		mainView.ctrlSlipDate.requestFocus();
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ
	 * 
	 * @return パネル
	 */
	protected abstract TSlipPanel createMainView();

	/**
	 * invoice使用するかどうか
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * 伝票種類別の初期設定
	 * 
	 * @throws TException
	 */
	protected void initSlipType() throws TException {
		String slipTypeNo = getSlipTypeNo();

		// データ区分
		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(slipTypeNo);

		List<SlipType> typeList = (List<SlipType>) request(SlipTypeManager.class, "get", condition);

		if (typeList.isEmpty()) {
			throw new TException("I00128", slipTypeNo);// 伝票種別[{0}]が設定されていません。
		}
		slipType = typeList.get(0);
	}

	/**
	 * 伝票種別(番号)
	 * 
	 * @return 伝票種別
	 */
	protected abstract String getSlipTypeNo();

	/**
	 * 画面ヘッダの初期設定
	 */
	protected void initHeaderView() {
		// 新規モード
		switchNew();

		// 伝票日付のデフォルト表示
		mainView.ctrlSlipDate.initSlipDate();

		// 有効期限設定
		changedSlipDate();

		// 伝票摘要
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRemark(true);
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRowRemark(false);
		mainView.ctrlSlipRemark.getSearchCondition().setDataType(slipType.getDataType()); // データ区分

		if (!isUseAttachment) {
			// 無効の場合、添付ボタン非表示
			mainView.btnAttach.setVisible(false);
		}

		if (!isUseTempPreview) {
			// 無効の場合、プレビューボタン非表示
			mainView.btnPreview.setVisible(false);
		}

		if (!isUsePatternSave) {
			// 無効の場合、辞書登録ボタン非表示
			mainView.btnPatternSave.setVisible(false);

			if (isUseTempPreview) {
				// 有効の場合、プレビューボタン座標修正
				mainView.btnPreview.setLocation(mainView.btnPatternSave.getLocation());
			}
		}

		if (!isUseTag) {
			// 無効の場合、付箋機能 非表示
			mainView.ctrlTag1.setVisible(false);
			mainView.ctrlTag2.setVisible(false);
		}
		// ワークフロー承認機能初期化
		initWorkFlowApprove();

	}

	/**
	 * 承認グループの表示設定
	 */
	protected void initWorkFlowApprove() {
		boolean isUseWorkFlowApprove = isUseWorkFlowApprove();
		mainView.ctrlAprvGroup.setVisible(isUseWorkFlowApprove);
		// 値設定
		initAprvGroup();
	}

	/**
	 * 承認グループ 初期値設定
	 */
	protected void initAprvGroup() {
		AprvRoleGroup grp = getUser().getAprvRoleGroup();
		mainView.ctrlAprvGroup.setEntity(grp);

	}

	/**
	 * ワークフロー承認機能を利用するか
	 * 
	 * @return true:利用する
	 */
	protected boolean isUseWorkFlowApprove() {
		return getCompany().getAccountConfig().isUseWorkflowApprove();
	}

	/**
	 * 画面明細の初期設定
	 */
	protected void initDetailView() {

		// プログラム情報
		mainView.ctrlDetail.getController().setProgramInfo(getProgramInfo());

		// 伝票種別
		mainView.ctrlDetail.getController().setSlipType(this.slipType);

		// 伝票日付
		Date slipDate = mainView.ctrlSlipDate.getValue();

		// 伝票日付を基準日としてセット
		mainView.ctrlDetail.setBaseDate(slipDate);

		// スプレッド状態保存キー
		mainView.ctrlDetail.setTableKeyName(getTableKeyName());
		mainView.ctrlDetail.tbl.restoreComponent(); // 復元

		// 伝票入力種類
		mainView.ctrlDetail.ctrlItem.getItemSearchCondition().setSlipInputType(getSlipInputType());
		mainView.ctrlDetail.ctrlItem.getSubItemSearchCondition().setSlipInputType(getSlipInputType());
		mainView.ctrlDetail.ctrlItem.getDetailItemSearchCondition().setSlipInputType(getSlipInputType());

		// 行摘要のデータ区分
		mainView.ctrlDetail.ctrlRemark.getSearchCondition().setDataType(slipType.getDataType());

		// 税区分のデフォルト
		mainView.ctrlDetail.setDefaultTaxInnerDivision(slipType.isInnerConsumptionTaxCalculation());
	}

	/**
	 * 伝票入力タイプ
	 * 
	 * @return 伝票入力タイプ
	 */
	protected abstract SlipInputType getSlipInputType();

	/**
	 * スプレッドテーブル状態保存キー
	 * 
	 * @return スプレッドテーブル状態保存キー
	 */
	protected String getTableKeyName() {
		return getProgramCode() + "_" + getUserCode();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addViewEvent() {

		// 新規ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doNew();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 修正ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doModify();
			}
		});

		// 複写ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCopy();
			}
		});

		// 削除ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doDelete();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 確定ボタン押下
		mainView.btnEntry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doEntry();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 仕訳辞書ボタン押下
		mainView.btnPattern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doPattern();
			}
		});

		// 辞書登録ボタン押下
		mainView.btnPatternSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doPatternSave();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// プレビューボタン押下
		mainView.btnPreview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doPreview();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 伝票日付
		mainView.ctrlSlipDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {

				if (!mainView.ctrlSlipDate.isValueChanged2()) {
					return true;
				}

				changedSlipDate();
				return true;
			}
		});

		// 決算段階
		mainView.ctrlCloseEntry.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changedClosingEntry();
			}
		});
	}

	/**
	 * Managerを指定する.
	 * 
	 * @return Managerクラス定義
	 */
	protected abstract Class getManagerClass();

	/**
	 * 新規モードにスイッチ
	 */
	protected void switchNew() {
		mainView.switchNew();
		mainView.btnDelete.setEnabled(false);
	}

	/**
	 * 編集モードにスイッチ
	 */
	protected void switchModify() {
		mainView.switchModify();
		mainView.btnDelete.setEnabled(true);
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void clearView() {
		// 排他解除
		unlock();

		slip = null;

		// 新規モード
		switchNew();

		// 各コンポーネント初期化＆制御
		clearComponents();

		// 初期フォーカス
		requestFocusFirst();
	}

	/**
	 * 排他解除(全体)
	 */
	protected void unlockAll() {
		try {
			// 排他解除
			request(getManagerClass(), "unlockAll", slipType.getCode());

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 排他解除(個別)
	 */
	protected void unlock() {
		try {
			if (slip != null && !Util.isNullOrEmpty(slip.getSlipNo())) {

				// 添付ファイルを退避
				List<SWK_ATTACH> attachments = null;

				try {
					if (slip.getHeader() != null) {
						attachments = slip.getHeader().getAttachments();
						slip.getHeader().setAttachments(null);
					}

					// 伝票指定の排他解除
					request(getManagerClass(), "unlock", slip);

				} finally {
					if (attachments != null) {
						slip.getHeader().setAttachments(attachments);
					}
				}

			} else {
				unlockAll();
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 値クリア
	 */
	protected void clearComponents() {
		// ヘッダ
		mainView.ctrlSlipNo.clear(); // 伝票番号
		mainView.ctrlEvidenceNo.clear(); // 証憑番号
		mainView.ctrlSlipRemark.clear(); // 伝票摘要
		mainView.ctrlPrintStop.clear(); // 伝票印刷停止
		mainView.ctrlCloseEntry.clear(); // 決算仕訳
		mainView.btnAttach.clear(); // 添付

		mainView.ctrlTag1.clear(); // 付箋機能
		mainView.ctrlTag2.clear(); // 付箋機能

		// 伝票日付のデフォルト表示
		mainView.ctrlSlipDate.initSlipDate();

		// 承認グループ
		initAprvGroup();

		// 明細
		mainView.ctrlDetail.init();

		// 伝票日付反映
		changedSlipDate();
	}

	/**
	 * 伝票日付を画面セット
	 * 
	 * @param slipDate 伝票日付
	 */
	protected void setSlipDate(Date slipDate) {
		mainView.ctrlSlipDate.setValue(slipDate);
		changedSlipDate();
	}

	/**
	 * 新規
	 */
	public void doNew() {
		try {
			if (!showConfirmMessage("Q00018")) {// 編集中のデータはクリアされますが、よろしいですか？
				return;
			}

			// クリア
			clearView();

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 伝票検索
	 */
	public void doModify() {
		searchSlip(true);
	}

	/**
	 * 伝票複写
	 */
	public void doCopy() {
		searchSlip(false);
	}

	/**
	 * 伝票検索と反映
	 * 
	 * @param isModify true:修正 false:複写
	 */
	protected void searchSlip(boolean isModify) {
		searchSlipAddResult(isModify);
	}

	/**
	 * 伝票検索と反映
	 * 
	 * @param isModify true:修正 false:複写
	 * @return true:成功
	 */
	protected boolean searchSlipAddResult(boolean isModify) {

		try {
			// 検索
			TSlipSearchCtrl ctrl = createSlipSearchCtrl();

			if (!isModify) {
				ctrl.switchCopyMode(); // 複写モード
			}

			if (ctrl.show() != TSlipSearchCtrl.OK_OPTION) {
				if (isModify) {
					mainView.btnModify.requestFocus();
				} else {
					mainView.btnCopy.requestFocus();
				}
				return false;
			}

			// 現在の伝票日付を取っておく
			Date slipDate = mainView.ctrlSlipDate.getValue();

			// 一旦クリア
			clearView();

			// 反映
			SWK_HDR hdr = ctrl.getSelectedRow();

			// 伝票構築
			slip = (Slip) request(getManagerClass(), "getSlip", hdr, isModify);

			// 複写の場合
			if (!isModify) {

				if (isInvoice && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
					// 複写のみ判断

					List<SWK_DTL> list = slip.getDetails();
					if (TSlipDetailPanelCtrl.isGroupAccounting() && !(this instanceof TSlipPatternPanelCtrl)) {

						// 普通伝票入力の場合、会社間付替対応を行う；パターンの場合、処理不要
						list = setupCompanyTranfer(list);

					}
					patternSlipType = slip.getHeader().getSWK_DEN_SYU();

					if (checkInvoiceItemTaxCode(list)) {
						// インボイス用:取引先と消費税を非適格、適格かチェック
						patternSlipType = null;
						return false;
					}
					patternSlipType = null;
				}

				// 伝票番号クリア
				slip.setSlipNo(null);

				// 複写時、添付ファイルをクリア
				slip.getHeader().setAttachments(null);
				// 複写時、付箋機能をクリア
				slip.getHeader().setSwkTags(null);

				Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

				// 明細の紐付け初期化
				for (SWK_DTL dtl : slip.getDetails()) {
					dtl.setSWK_DEN_NO(null);
					dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
					dtl.setSWK_KESI_DATE(null); // 消込伝票日付
					dtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
					dtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
					dtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
					dtl.setSWK_SOUSAI_GYO_NO(null); // 相殺行番号

					dtl.setAP_ZAN(null);
					dtl.setAR_ZAN(null);
					dtl.setBsDetail(null);

					if (isCopySlipDateToOccurDate) {
						// 複写時に発生日を伝票日付に変更する
						dtl.setHAS_DATE(slipDate);

						Company kcompany = dtl.getAppropriateCompany();
						Currency currency = dtl.getCurrency();
						ConsumptionTax tax = dtl.getTax();

						BigDecimal amount = dtl.getSWK_IN_KIN();
						BigDecimal taxAmount = dtl.getSWK_IN_ZEI_KIN();

						BigDecimal rate = null;
						String key = currency != null ? currency.getCode() : keyCurrency.getCode();
						if (rateMap.containsKey(key)) {
							rate = rateMap.get(key);
						} else {
							rate = getCurrencyRate(currency, slip.isClosingSlip(), slipDate);
							rateMap.put(key, rate);
						}

						// 発生日チェックを使用する場合
						if (isUseHasDateChk) {
							rate = getCurrencyRateByOccurDate(slipDate, currency.getCode(), slip.isClosingSlip());
						}

						BigDecimal keyAmount = convertKeyAmount(amount, rate, kcompany, currency);
						BigDecimal keyTaxAmount = convertKeyTaxAmount(taxAmount, rate, kcompany, currency, tax);

						dtl.setSWK_CUR_RATE(rate);
						dtl.setSWK_KIN(keyAmount);
						dtl.setSWK_ZEI_KIN(keyTaxAmount);
					}
				}

				if (ctrl.isSelectedCancel()) {
					// 赤
					slip = slip.toCancelSlip();

				} else if (ctrl.isSelectedReverse()) {
					// 逆
					slip = slip.toReverseSlip();
				}

			}

			// 画面反映
			dispatch();

			if (isModify) {
				// 修正
				switchModify();

			} else {
				// 複写(新規扱い)
				slip = null;
				if (!notReservationSlipDate) {
					setSlipDate(slipDate); // 日付は元のまま
				}
				mainView.ctrlSlipNo.clear();

				if (!notReservationCloseEntry) {
					mainView.ctrlCloseEntry.setSelected(false); // 決算仕訳
				}

				switchNew();
			}

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * 画面入力情報を元に基軸金額に換算
	 * 
	 * @param amount 入力金額
	 * @param rate
	 * @param kcompany
	 * @param currency
	 * @return 基軸通貨金額
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount, BigDecimal rate, Company kcompany, Currency currency) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

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
	 * @param rate
	 * @param kcompany
	 * @param currency
	 * @param tax
	 * @return 基軸通貨消費税額
	 */
	protected BigDecimal convertKeyTaxAmount(BigDecimal taxAmount, BigDecimal rate, Company kcompany, Currency currency,
		ConsumptionTax tax) {

		if (DecimalUtil.isNullOrZero(taxAmount)) {
			return BigDecimal.ZERO;
		}

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
	 * @param currency
	 * @param isClosing
	 * @param occurDate
	 * @return レート
	 */
	protected BigDecimal getCurrencyRate(Currency currency, boolean isClosing, Date occurDate) {
		try {

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
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
	 * 伝票検索コントローラ生成
	 * 
	 * @return 伝票検索コントローラ
	 */
	protected TSlipSearchCtrl createSlipSearchCtrl() {
		TSlipSearchCtrl ctrl = new TSlipSearchCtrl(mainView);
		ctrl.setSlipType(slipType.getCode()); // 伝票種別
		ctrl.setDataKind(slipType.getDataType()); // データ区分
		ctrl.setIncludeOtherSystem(true); // 他シスOK

		return ctrl;
	}

	/**
	 * 画面反映
	 */
	protected void dispatch() {

		if (TSlipDetailPanelCtrl.isGroupAccounting() && !(this instanceof TSlipPatternPanelCtrl)) {

			// 普通伝票入力の場合、会社間付替対応を行う；パターンの場合、処理不要
			setupCompanyTranfer();

		}

		// ヘッダ
		SWK_HDR hdr = slip.getHeader();

		setSlipDate(hdr.getSWK_DEN_DATE()); // 伝票日付
		mainView.ctrlSlipNo.setValue(hdr.getSWK_DEN_NO()); // 伝票番号
		mainView.ctrlSlipNo.setUpdateCount(hdr.getSWK_UPD_CNT()); // 修正回数
		mainView.ctrlEvidenceNo.setValue(hdr.getSWK_SEI_NO()); // 証憑番号
		mainView.ctrlSlipRemark.setCode(hdr.getSWK_TEK_CODE()); // 伝票摘要
		mainView.ctrlSlipRemark.setNames(hdr.getSWK_TEK()); // 伝票摘要

		mainView.btnAttach.setAttachments(hdr.getAttachments());

		if (hdr.getSwkTags() != null) {
			// 付箋機能
			for (SWK_TAG tag : hdr.getSwkTags()) {
				Tag bean = new Tag();
				bean.setCompanyCode(tag.getKAI_CODE());
				bean.setCode(tag.getTAG_CODE());
				bean.setColor(tag.getTAG_COLOR());
				bean.setTitle(tag.getTAG_TITLE());

				if (tag.getSEQ() == 1) {
					mainView.ctrlTag1.setEntity(bean);
				} else if (tag.getSEQ() == 2) {
					mainView.ctrlTag2.setEntity(bean);
				}
			}
		}
		mainView.ctrlAprvGroup.setEntity(hdr.getAprRoleGroup());
		if (slip.isClosingSlip()) {
			mainView.ctrlCloseEntry.setSelected(true); // 決算仕訳
		}

		// 明細
		List<SWK_DTL> dtlList = new ArrayList<SWK_DTL>(slip.getDetails().size());
		for (SWK_DTL dtl : slip.getDetails()) {
			SWK_DTL cdtl = dtl.clone();

			// 内税の場合、金額の編集
			if (dtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().add(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().add(cdtl.getSWK_ZEI_KIN()));
			}

			dtlList.add(cdtl);

			// 画面反映前の明細整備
			adjustDetails(hdr, cdtl);
		}

		mainView.ctrlDetail.setSlipNo(slip.getSlipNo());
		mainView.ctrlDetail.setEntityList(dtlList);

	}

	/**
	 * invoice用 科目コードが消費税科目か確認、非適格か適格か
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
			}

		}

		msgList = checkCustomerTaxInvReg(list, msgList, rowNo); // 非適格か適格か

		if (msgList.size() != 0) {
			if (ConfermMessageListDialog.OK_OPTION != showConfermMessageList(mainView, getMessage("I01111"), msgList)) {
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
	 * @param msgList メッセージlist
	 * @param rowNo 項番番号
	 * @return メッセージlist
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
			if (cus == null) {
				cus = setCustomerEntity(dtl.getSWK_K_KAI_CODE(), dtl.getSWK_TRI_CODE());
			}
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
	 * 画面反映前の明細整備
	 * 
	 * @param hdr
	 * @param dtl
	 */
	protected void adjustDetails(SWK_HDR hdr, SWK_DTL dtl) {

		if (!isNoClearSameRemark) {
			// 行摘要の整備
			if (Util.isNullOrEmpty(dtl.getSWK_GYO_TEK_CODE())
				&& Util.avoidNull(hdr.getSWK_TEK()).equals(dtl.getSWK_GYO_TEK())) {
				// 明細行摘要コードなし、且つ行摘要＝伝票摘要の場合、明細行摘要をクリアする
				dtl.setSWK_GYO_TEK(null);
			}
		}

		if (isClearSameOccurDate) {
			// 発生日の整備(基軸通貨の場合のみ)
			if (keyCurrency.getCode().equals(dtl.getSWK_CUR_CODE()) && dtl.getHAS_DATE() != null
				&& hdr.getSWK_DEN_DATE() != null && dtl.getHAS_DATE().compareTo(hdr.getSWK_DEN_DATE()) == 0) {
				// 明細行発生日＝伝票日付の場合、明細発生日をクリアする
				dtl.setHAS_DATE(null);
			}
		}
	}

	/**
	 * 会社間付替仕訳設定(インボイスワーニングメッセージ用)
	 * 
	 * @param list
	 * @return 仕訳明細
	 */
	protected List<SWK_DTL> setupCompanyTranfer(List<SWK_DTL> list) {

		// 処理マップ作成
		Map<String, SWK_DTL> details = new TreeMap<String, SWK_DTL>();
		// 親会社の基軸通貨
		String keyCur = conf.getKeyCurrency().getCode();

		for (SWK_DTL dtl : list) {

			// 通貨情報の会社コードをログイン会社コードに変更
			Currency currency = dtl.getCurrency();
			if (currency != null) {
				currency.setCompanyCode(getCompanyCode());
			}

			// 親会社仕訳取得するため、マップに一時保存
			String key = dtl.getSWK_DEN_NO() + "<>" + dtl.getSWK_GYO_NO();
			details.put(key, dtl);
		}

		// 処理マップと仕訳ジャーナルによって訂正処理を行う
		for (SWK_DTL dtl : list) {

			// 計上会社はログイン会社と異なる（付替仕訳）
			if (!dtl.getKAI_CODE().equals(getCompanyCode()) && !dtl.isAutoDetail()) {

				String key = dtl.getSWK_DEN_NO() + "<>" + (dtl.getSWK_GYO_NO() - 1);
				SWK_DTL baseDtl = details.get(key);

				if (baseDtl != null) {

					// 邦貨額、消費税額以外、入力金額、消費税はすべて子会社の仕訳明細より表示する。

					// 消費税額
					BigDecimal taxAmount = null;

					if (Util.equals(keyCur, dtl.getSWK_CUR_CODE())
						&& !DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// 取引通貨 = 親会社の基軸通貨が一緒の場合は入力消費税額をそのままセットする
						taxAmount = dtl.getSWK_IN_ZEI_KIN();
					} else if (DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// 子会社の入力消費税額＝０の場合、消費税額は０とする
						taxAmount = BigDecimal.ZERO;
					} else {
						taxAmount = getKeyTaxAmount(dtl, baseDtl.getSWK_KIN());
					}

					// 通貨情報（通貨コード、小数点以下小数点桁数）
					dtl.setCurrency(baseDtl.getCurrency());

					// レート
					dtl.setSWK_CUR_RATE(baseDtl.getSWK_CUR_RATE());

					// 邦貨額
					dtl.setSWK_KIN(baseDtl.getSWK_KIN().subtract(taxAmount));

					// 消費税額
					dtl.setSWK_ZEI_KIN(taxAmount);

				}

			}
		}

		// 自動仕訳外す
		List<SWK_DTL> newDetails = new ArrayList<SWK_DTL>();

		for (SWK_DTL dtl : list) {
			if (!dtl.isAutoDetail()) {
				newDetails.add(dtl);
			}
		}
		return newDetails;
	}

	/**
	 * 会社間付替仕訳設定
	 */
	protected void setupCompanyTranfer() {

		// 処理マップ作成
		Map<String, SWK_DTL> details = new TreeMap<String, SWK_DTL>();
		// 親会社の基軸通貨
		String keyCur = conf.getKeyCurrency().getCode();

		for (SWK_DTL dtl : slip.getDetails()) {

			// 通貨情報の会社コードをログイン会社コードに変更
			Currency currency = dtl.getCurrency();
			if (currency != null) {
				currency.setCompanyCode(getCompanyCode());
			}

			// 親会社仕訳取得するため、マップに一時保存
			String key = dtl.getSWK_DEN_NO() + "<>" + dtl.getSWK_GYO_NO();
			details.put(key, dtl);
		}

		// 処理マップと仕訳ジャーナルによって訂正処理を行う
		for (SWK_DTL dtl : slip.getDetails()) {

			// 計上会社はログイン会社と異なる（付替仕訳）
			if (!dtl.getKAI_CODE().equals(getCompanyCode()) && !dtl.isAutoDetail()) {

				String key = dtl.getSWK_DEN_NO() + "<>" + (dtl.getSWK_GYO_NO() - 1);
				SWK_DTL baseDtl = details.get(key);

				if (baseDtl != null) {

					// 邦貨額、消費税額以外、入力金額、消費税はすべて子会社の仕訳明細より表示する。

					// 消費税額
					BigDecimal taxAmount = null;

					if (Util.equals(keyCur, dtl.getSWK_CUR_CODE())
						&& !DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// 取引通貨 = 親会社の基軸通貨が一緒の場合は入力消費税額をそのままセットする
						taxAmount = dtl.getSWK_IN_ZEI_KIN();
					} else if (DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
						// 子会社の入力消費税額＝０の場合、消費税額は０とする
						taxAmount = BigDecimal.ZERO;
					} else {
						taxAmount = getKeyTaxAmount(dtl, baseDtl.getSWK_KIN());
					}

					// 通貨情報（通貨コード、小数点以下小数点桁数）
					dtl.setCurrency(baseDtl.getCurrency());

					// レート
					dtl.setSWK_CUR_RATE(baseDtl.getSWK_CUR_RATE());

					// 邦貨額
					dtl.setSWK_KIN(baseDtl.getSWK_KIN().subtract(taxAmount));

					// 消費税額
					dtl.setSWK_ZEI_KIN(taxAmount);

				}

			}
		}

		// 自動仕訳外す
		List<SWK_DTL> newDetails = new ArrayList<SWK_DTL>();

		for (SWK_DTL dtl : slip.getDetails()) {
			if (!dtl.isAutoDetail()) {
				newDetails.add(dtl);
			}
		}
		slip.setDetails(newDetails);
	}

	/**
	 * 仕訳ジャーナル情報を元に基軸消費税額に換算
	 * 
	 * @param dtl 仕訳ジャーナル
	 * @param amount 邦貨金額
	 * @return 基軸通貨消費税額
	 */
	protected BigDecimal getKeyTaxAmount(SWK_DTL dtl, BigDecimal amount) {

		if (amount == null) {
			return BigDecimal.ZERO;
		}

		Company kcompany = dtl.getAppropriateCompany();

		if (kcompany == null) {
			return BigDecimal.ZERO;
		}

		AccountConfig kconf = kcompany.getAccountConfig();
		Currency currency = getCompany().getAccountConfig().getKeyCurrency();
		ConsumptionTax tax = dtl.getTax();

		if (kconf == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
		param.setInside(true); // 内税or外税
		param.setAmount(amount); // 対象金額
		param.setTax(tax); // 消費税情報
		param.setDigit(currency.getDecimalPoint()); // 小数点桁数
		param.setReceivingFunction(kconf.getReceivingFunction()); // 借受
		param.setPaymentFunction(kconf.getPaymentFunction()); // 仮払
		return calculator.calculateTax(param);

	}

	/**
	 * 伝票削除
	 */
	public void doDelete() {
		try {
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除
			request(getManagerClass(), "delete", slip);

			// クリア
			clearView();

			showMessage("I00013");// 正常に処理が実行されました。

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 伝票確定
	 */
	public void doEntry() {
		try {
			// 入力チェック
			if (!checkInput()) {
				return;
			}

			String msgID = "Q00004"; // 確定しますか？

			if (mainView.btnAttach.isVisible()
				&& (mainView.btnAttach.getAttachments() == null || mainView.btnAttach.getAttachments().isEmpty())) {
				msgID = "Q00075"; // 添付ファイルがありませんが、確定しますか？
			}

			if (!showConfirmMessage(msgID)) {
				return;
			}

			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			// 伝票に反映
			reflectSlip();

			// 有効期限と明細の整合性チェック
			if (!checkError()) {
				return;
			}
			// 印刷停止チェックされていなかったら印刷処理
			boolean isPrint = !mainView.ctrlPrintStop.isSelected();

			// 登録
			byte[] bytes = (byte[]) request(getManagerClass(), "entry", slip, isPrint);

			// 印刷(PDF表示)
			if (isPrint) {
				TPrinter printer = new TPrinter();
				printer.preview(bytes, getPrintName() + ".pdf");
			}

			// 画面クリア 伝票日付だけ保持
			Date slipDate = mainView.ctrlSlipDate.getValue();

			clearView();

			setSlipDate(slipDate);

			showMessage("I00013");// 正常に処理が実行されました。

		} catch (Exception ex) {
			errorHandler(ex);

		} finally {
			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * 一時伝票保存/復旧の初期化
	 */
	protected void initSaveLoad() {

		mainView.addSaveListener(new TObjectSaveListener<Slip>() {

			/**
			 * プログラムID取得
			 * 
			 * @return プログラムID
			 */
			@Override
			public String getProgramCode() {
				return TSlipPanelCtrl.this.getProgramCode();
			}

			/**
			 * 保存キーの取得
			 * 
			 * @param obj 対象オブジェクト
			 * @return 保存キー
			 */
			@Override
			public String getKey(Slip obj) {
				return obj.getSlipNo();
			}

			/**
			 * 保存オブジェクトの設定
			 * 
			 * @param obj 対象オブジェクト
			 */
			@Override
			public void setSaveObject(Slip obj) {
				dispatchTempSlip(obj);
			}

			/**
			 * 保存オブジェクトの取得
			 * 
			 * @return オブジェクト
			 */
			@Override
			public Slip getSaveObject() {
				Slip obj = getSaveSlip();
				obj.setSlipNo(DateUtil.toYMDHMSString(Util.getCurrentDate()) + " " + obj.getRemarks());
				return obj;
			}
		});

	}

	/**
	 * 一時保存伝票を取得
	 * 
	 * @return 一時保存伝票
	 */
	protected Slip getSaveSlip() {

		Slip saveSlip = slip;

		try {
			if (slip != null) {
				slip = slip.clone();
			}

			// 伝票に反映
			reflectSlip();

			Slip tempSlip = slip;

			return tempSlip;

		} catch (Exception ex) {
			errorHandler(ex);
		} finally {
			slip = saveSlip; // 復元
		}
		return null;
	}

	/**
	 * 一時保存パターン伝票を取得
	 * 
	 * @return 一時保存パターン伝票
	 */
	protected Slip getSavePatternSlip() {

		Slip saveSlip = slip;

		try {
			if (slip != null) {
				slip = slip.clone();
			}

			// パターン伝票に反映
			reflectPatternSlip();

			Slip tempSlip = slip;

			return tempSlip;

		} catch (Exception ex) {
			errorHandler(ex);
		} finally {
			slip = saveSlip; // 復元
		}
		return null;
	}

	/**
	 * 一時伝票復旧処理
	 * 
	 * @param tempSlip 一時伝票
	 */
	protected void dispatchTempSlip(Slip tempSlip) {

		try {

			if (tempSlip.getDetails() == null) {
				tempSlip.setDetails(new ArrayList<SWK_DTL>());
			}

			// 一旦クリア
			clearView();

			// 複写モード
			slip = tempSlip;

			// 伝票番号クリア
			slip.setSlipNo(null);

			// 明細の紐付け初期化
			for (SWK_DTL dtl : slip.getDetails()) {
				dtl.setSWK_DEN_NO(null);
				dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
				dtl.setSWK_KESI_DATE(null); // 消込伝票日付
				dtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
				dtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
				dtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号

				dtl.setAP_ZAN(null);
				dtl.setAR_ZAN(null);
				dtl.setBsDetail(null);
			}

			// 画面反映
			dispatch();

			// 複写(新規扱い)
			slip = null;
			mainView.ctrlSlipNo.clear();

			switchNew();
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 伝票に反映
	 * 
	 * @throws TException
	 */
	protected void reflectSlip() throws TException {

		// Slip構築
		if (slip == null) {
			slip = new Slip();
		}

		// ヘッダ反映
		SWK_HDR hdr = slip.getHeader();
		reflectHeader(hdr);

		// 明細反映
		reflectDetails();

		// 為替差損益行追加
		slip = addLossOrProfit(slip);

		// 明細にヘッダ情報セット
		slip.synchDetails();
	}

	/**
	 * パターン伝票に反映
	 */
	protected void reflectPatternSlip() {

		// Slip構築
		if (slip == null) {
			slip = new Slip();
		}

		// ヘッダ反映
		SWK_HDR hdr = slip.getHeader();
		hdr.setINP_DATE(null);
		hdr.setUPD_DATE(null);
		hdr.setSWK_INP_DATE(null);
		reflectHeader(hdr);
		hdr.setSWK_DEN_DATE(null); // 伝票日付
		hdr.setSWK_IRAI_DATE(null); // 依頼日
		hdr.setSWK_DEN_NO(patternSaveView.ctrlPatternNo.getValue());
		hdr.setSWK_UKE_DEP_CODE(null);
		hdr.setSWK_UPD_CNT(0);

		if (mainView.ctrlCloseEntry.isSelected()) {
			// 日付が無いので決算伝票の場合は自分で1を
			hdr.setSWK_KSN_KBN(1);
		}

		// 明細反映
		reflectPatternDetails();

		// 明細にヘッダ情報セット
		slip.synchDetails();
	}

	/**
	 * 為替差損益行追加
	 * 
	 * @param slip_ 対象
	 * @return 為替差損益行追加後
	 * @throws TException
	 */
	protected Slip addLossOrProfit(Slip slip_) throws TException {
		Slip cslip = slip_.clone();
		cslip.getHeader().setAttachments(null); // 不要な添付を消す

		// 一度、消費税金額を足す
		for (SWK_DTL dtl : cslip.getDetails()) {
			if (!DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
				dtl.setSWK_KIN(dtl.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));
			}
		}

		// 差損益行追加
		cslip = (Slip) request(getManagerClass(), "addLossOrProfit", cslip);

		// 消費税金額を戻す
		for (SWK_DTL dtl : cslip.getDetails()) {
			if (!DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
				dtl.setSWK_KIN(dtl.getSWK_KIN().subtract(dtl.getSWK_ZEI_KIN()));
			}
		}

		cslip.getHeader().setAttachments(slip_.getHeader().getAttachments()); // 添付を復旧
		return cslip;
	}

	/**
	 * 有効期限と明細の整合性チェック
	 * 
	 * @return false:エラーあり
	 * @throws TException
	 */
	protected boolean checkError() throws TException {

		List<Message> errorList = null;

		// 添付ファイルを退避
		List<SWK_ATTACH> attachments = null;

		try {
			if (slip.getHeader() != null) {
				attachments = slip.getHeader().getAttachments();
				slip.getHeader().setAttachments(null);
			}

			// 伝票入力チェック
			errorList = (List<Message>) request(getManagerClass(), "checkError", slip);

		} finally {
			if (attachments != null) {
				slip.getHeader().setAttachments(attachments);
			}
		}

		if (!errorList.isEmpty()) {
			if (!getCompany().getAccountConfig().isSlipTermCheck()) {
				// 有効期間チェックが無い場合はエラー表示して終了
				showMessageList(mainView, errorList);
				return false;

			}

			for (Message error : errorList) {

				// 締め情報不正の場合は再ログインを促す
				if (error.getErrorType() == SlipError.CLOSED) {
					showMessage("W01136");// 他ユーザにより月次処理が行われました。再ログインを行い、再度入力し直してください。
					return false;
				}

				// 1件でも期間外エラー以外があれば表示して終了
				if (error.getErrorType() != SlipError.TERM_OUT) {
					showMessageList(mainView, errorList);
					return false;
				}
			}

			// 期間外エラーのみの場合、操作者に確認してOKなら登録
			if (ConfermMessageListDialog.OK_OPTION != showConfermMessageList(mainView, "Q00054", // 有効期間が過ぎているデータが含まれます。よろしいですか？
				errorList)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * 印刷時の名称
	 * 
	 * @return 名称
	 */
	protected abstract String getPrintName();

	/**
	 * 入力値チェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkInput() throws TException {

		// 伝票日付チェック
		if (!checkSlipDate()) {
			return false;
		}

		// ヘッダーチェック
		if (!checkHeaderInput()) {
			return false;
		}

		// T-Formの場合、明細チェック処理を行う
		if (mainView.isTFormMode() && !mainView.ctrlDetail.checkInput()) {
			return false;
		}

		// 明細チェック
		if (!checkDetailInput()) {
			return false;
		}

		// invoice用消費税の計算警告メッセージ
		if (isInvoice && !checkInvoiceTax()) {
			return false;
		}

		return true;
	}

	/**
	 * 伝票日付関連のチェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkSlipDate() throws TException {

		// 明細
		List<SWK_DTL> dtlList = mainView.ctrlDetail.getEntityList();

		// 決算伝票の場合、決算伝票入力可能月か
		if (mainView.ctrlCloseEntry.isSelected() && !mainView.ctrlSlipDate.isSettlementDate()) {
			showMessage("I00045");// 決算仕訳は決算月の末日でのみ入力できます。
			mainView.ctrlSlipDate.requestFocus();
			return false;
		}

		// 先行伝票日付チェック
		if (mainView.ctrlSlipDate.isPriorOver()) {
			showMessage("I00130");// 指定の伝票日付は先行伝票日付を超えています。
			mainView.ctrlSlipDate.requestFocus();
			return false;
		}

		// 締めチェック
		if (mainView.ctrlSlipDate.isClosed(mainView.ctrlCloseEntry.getStage())) {
			showMessage("I00131");// 指定の伝票日付は締められています。
			mainView.ctrlSlipDate.requestFocus();
			return false;
		}

		if (mainView.ctrlCloseEntry.isSelected()
			&& mainView.ctrlSlipDate.isClosed(mainView.ctrlCloseEntry.getStage())) {
			showMessage("I00132");// 月次処理が行われた為、決算段階に変更があります。
			mainView.ctrlCloseEntry.resetStage();
			mainView.ctrlCloseEntry.chk.requestFocus();
			return false;
		}

		// 決算仕訳チェック
		if (mainView.ctrlCloseEntry.num.isEditable()) {

			// 決算段階の入力チェック
			if (mainView.ctrlCloseEntry.num.isEmpty()) {
				// 決算段階を入力してください
				showMessage("I00037", "C00718");
				mainView.ctrlCloseEntry.num.requestFocus();
				return false;
			}

			// 決算段階の範囲チェック
			int stage = mainView.ctrlCloseEntry.num.getInt();
			int max = getCompany().getFiscalPeriod().getMaxSettlementStage();

			if (stage <= 0 || max < stage) {
				// {0}は{1}～{2}の範囲で指定してください
				showMessage("I00247", "C00718", 1, max);// 決算段階

				mainView.ctrlCloseEntry.num.requestFocus();
				return false;
			}
		}

		// 計上先の先行、締めチェック

		// ログインユーザの会社コード
		String keyCompany = getCompanyCode();

		// 最新Company取得(裏で締められている場合を想定)
		CompanySearchCondition compParam = new CompanySearchCondition();

		for (SWK_DTL dtl : dtlList) {
			String kcompany = dtl.getSWK_K_KAI_CODE();
			if (keyCompany.equals(kcompany)) {
				continue;
			}

			compParam.addCode(kcompany);
		}

		if (!compParam.getCodeList().isEmpty()) {
			List<Company> kcompanyList = (List<Company>) request(CompanyManager.class, "get", compParam);

			for (Company kcompany : kcompanyList) {
				// 先行伝票日付チェック
				if (mainView.ctrlSlipDate.isPriorOver(kcompany)) {
					showMessage("I00133", kcompany.getCode());// 指定の伝票日付は計上会社先[{0}]の先行伝票日付を超えています。
					mainView.ctrlSlipDate.requestFocus();
					return false;
				}

				// 締めチェック
				if (mainView.ctrlSlipDate.isClosed(kcompany, mainView.ctrlCloseEntry.getStage())) {
					showMessage("I00134", kcompany.getCode());// 指定の伝票日付は計上会社先[{0}]で締められています。
					mainView.ctrlSlipDate.requestFocus();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * ヘッダー項目のチェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected boolean checkHeaderInput() throws TException {
		// 摘要コード入力チェック
		if (!checkInputBlank(mainView.ctrlSlipRemark.name, "C00384")) {// 摘要
			return false;
		}
		// 承認グループ
		if (isUseWorkFlowApprove()
			&& !checkInputBlank(mainView.ctrlAprvGroup.code, mainView.ctrlAprvGroup.btn.getLangMessageID())) {
			// ワークフロー承認利用時に空
			return false;
		}
		return true;
	}

	/**
	 * 明細項目のチェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected boolean checkDetailInput() throws TException {

		String keyCompany = getCompanyCode(); // ログインユーザの会社コード
		String keyCurrencyCode = keyCurrency.getCode(); // 基軸通貨コード

		// 明細
		List<SWK_DTL> dtlList = mainView.ctrlDetail.getEntityList();

		// 明細チェック
		if (mainView.ctrlDetail.getDetailRowCount() == 0) {
			showMessage("I00037", "C01766");// {0}を入力してください。明細行
			mainView.ctrlDetail.ctrlKDepartment.requestTextFocus();
			return false;
		}

		// バランスチェック
		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;
		Map<String, BigDecimal[]> map = new TreeMap<String, BigDecimal[]>();

		for (SWK_DTL dtl : dtlList) {

			if (DecimalUtil.isNullOrZero(dtl.getSWK_KIN())) {
				showMessage("I00135");// 明細に金額を入力してください。
				return false;
			}

			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());

			// 外税は合計に消費税プラス
			if (!dtl.isTaxInclusive()) {
				dr = dr.add(dtl.getDebitTaxAmount());
				cr = cr.add(dtl.getCreditTaxAmount());
			}

			// 外貨
			String currencyCode = dtl.getSWK_CUR_CODE();

			if (keyCurrencyCode.equals(currencyCode)) {
				continue;
			}

			BigDecimal[] dec = map.get(currencyCode);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(currencyCode, dec);
			}

			dec[0] = dec[0].add(dtl.getDebitInputAmount());
			dec[1] = dec[1].add(dtl.getCreditInputAmount());

			if (!dtl.isTaxInclusive()) {
				dec[0] = dec[0].add(dtl.getDebitTaxInputAmount());
				dec[1] = dec[1].add(dtl.getCreditTaxInputAmount());
			}
		}

		for (SWK_DTL dtl : dtlList) {
			// BS勘定消込チェック
			SWK_DTL bs = dtl.getBsDetail();

			if (bs == null) {
				continue;
			}

			if (!checkBsSlipDate(bs)) {
				return false;
			}
		}

		// 外貨バランス状態
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

		if (!isFBalance && dr.compareTo(cr) != 0) {
			showMessage("I00136");// 貸借がバランスしていません。
			return false;
		}

		// 機能通貨設定チェック
		Set<String> companys = new TreeSet<String>();
		for (SWK_DTL dtl : dtlList) {
			Company kcomp = dtl.getAppropriateCompany();
			if (companys.contains(kcomp.getCode())) {
				continue;
			}

			Currency fCurrency = kcomp.getAccountConfig().getFunctionalCurrency();

			if (Util.isNullOrEmpty(fCurrency.getCode())) {
				showMessage("I00137", kcomp.getCode());// 会社[{0}]の機能通貨が設定されていません。
				return false;
			}

			companys.add(kcomp.getCode());
		}

		// 発生日チェック
		int row = 0;
		int rowCount = mainView.ctrlDetail.tbl.getRowCount();
		Boolean isClosing = mainView.ctrlCloseEntry.isSelected();
		for (SWK_DTL dtl : dtlList) {
			Date hasDate = dtl.getHAS_DATE();
			if (hasDate == null) {
				continue;
			}
			String curCode = dtl.getSWK_CUR_CODE();
			if (row < rowCount) {
				BigDecimal rate = getCurrencyRateByOccurDate(hasDate, curCode, isClosing);
				if (isUseHasDateChk && rate == null) {
					showMessage("I01161"); // 発生日に対応する通貨レートが設定されていません。
					mainView.ctrlDetail.tbl.requestFocus(row, SC.issuerDay);
					return false;
				} else if (rate != null) {
					dtl.setSWK_CUR_RATE(rate);
					mainView.ctrlDetail.getEntityList().set(row, dtl);
				}
			}
			row = row + 1;
		}

		return true;
	}

	/**
	 * BS勘定の伝票日付チェック
	 * 
	 * @param bs
	 * @return true:OK
	 */
	protected boolean checkBsSlipDate(SWK_DTL bs) {

		Date termDate = mainView.ctrlSlipDate.getValue();
		String msg = "I00455";
		if (isBsTermLastDay) {
			// BS勘定可能の期限日の取得
			termDate = DateUtil.getLastDate(termDate);
			msg = "I00594"; // 伝票年月を超えるBS勘定の計上年月は入力できません。
		}

		if (!Util.isSmallerThenByYMD(bs.getSWK_DEN_DATE(), termDate)) {
			// 伝票日付を超えるBS勘定の計上日付は入力できません。
			showMessage(msg);
			mainView.ctrlSlipDate.requestTextFocus();
			return false;
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
	 * 2023INVOICE制度用エラーメッセージ：消費税額と入力消費税額が合わない場合はエラー
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkInvoiceTax() throws TException {

		if (chkSlipTypeInvoice()) {
			return true;
		}

		List<String> list = new ArrayList<String>(); // エラーリスト
		Map<String, SWK_DTL> totalMap = new HashMap<String, SWK_DTL>(); // 総額用
		Map<String, SWK_DTL> addMap = new HashMap<String, SWK_DTL>(); // 加算用
		List<SWK_DTL> dtlList = mainView.ctrlDetail.getEntityList(); // 画面明細リスト
		String cmpCode = "";
		String code = "";
		SWK_DTL dtlMap = new SWK_DTL();
		// 計算用
		BigDecimal taxAmountSum = BigDecimal.ZERO;
		BigDecimal amountSum = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;

		// 総額用
		for (SWK_DTL dtl : dtlList) {
			if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}

			Company kcompany = dtl.getAppropriateCompany();
			AccountConfig kconf = kcompany.getAccountConfig();

			ConsumptionTax tax = dtl.getTax();

			if (tax == null) {
				cmpCode = dtl.getAppropriateCompany().getCode();
				code = dtl.getSWK_ZEI_CODE();
				tax = setTaxEntity(cmpCode, code);
				if (tax == null) {
					continue;
				}
			}

			// keySet:消費税レート+通貨コード+内・外税区分
			String key = DecimalUtil.toBigDecimalNVL(tax.getRate()) + "<>" + dtl.getSWK_CUR_CODE() + "<>"
				+ dtl.getSWK_ZEI_KBN();
			if (totalMap.containsKey(key)) {

				dtlMap = totalMap.get(key);

				// 明細金額取得
				amount = getAmount(dtl, amount);
				amountSum = dtlMap.getSWK_IN_KIN().add(amount);

			} else {
				dtlMap = new SWK_DTL();
				dtlMap.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				dtlMap.setCUR_DEC_KETA(dtl.getCUR_DEC_KETA());
				dtlMap.setTax(tax);

				taxAmountSum = BigDecimal.ZERO;

				// 明細金額取得
				amountSum = getAmount(dtl, amountSum);

			}

			TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
			param.setTax(tax); // 消費税情報
			param.setDigit(dtl.getCUR_DEC_KETA()); // 小数点桁数
			param.setReceivingFunction(kconf.getReceivingFunction()); // 借受
			param.setPaymentFunction(kconf.getPaymentFunction()); // 仮払
			param.setInside(dtl.isTaxInclusive()); // 内・外税
			param.setAmount(amountSum); // 対象金額

			// 1行計算用
			taxAmountSum = calculator.calculateTax(param);

			dtlMap.setSWK_IN_ZEI_KIN(taxAmountSum);
			dtlMap.setSWK_IN_KIN(amountSum);
			dtlMap.setSWK_ZEI_KBN(dtl.getSWK_ZEI_KBN());

			totalMap.put(key, dtlMap);
		}

		// 単純加算計算
		for (SWK_DTL dtl : dtlList) {

			if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}

			ConsumptionTax tax = dtl.getTax();
			if (tax == null) {
				cmpCode = dtl.getAppropriateCompany().getCode();
				code = dtl.getSWK_ZEI_CODE();
				tax = setTaxEntity(cmpCode, code);
				if (tax == null) {
					continue;
				}
			}

			dtlMap = new SWK_DTL();
			taxAmountSum = BigDecimal.ZERO;

			// keySet:消費税レート+通貨コード+内・外税区分
			String key = DecimalUtil.toBigDecimalNVL(tax.getRate()) + "<>" + dtl.getSWK_CUR_CODE() + "<>"
				+ dtl.getSWK_ZEI_KBN();

			if (addMap.containsKey(key)) {
				dtlMap = addMap.get(key);
				taxAmountSum = DecimalUtil.avoidNull(dtlMap.getSWK_IN_ZEI_KIN());
			} else {
				dtlMap = new SWK_DTL();
				dtlMap.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				dtlMap.setCUR_DEC_KETA(dtl.getCUR_DEC_KETA());
				amountSum = BigDecimal.ZERO;
				taxAmountSum = BigDecimal.ZERO;
			}

			taxAmount = DecimalUtil.avoidNull(dtl.getSWK_IN_ZEI_KIN());
			if (!dtl.isDR()) {
				// Crの場合正負反転
				taxAmount = taxAmount.negate();
			}

			taxAmountSum = taxAmountSum.add(taxAmount);

			dtlMap.setSWK_IN_ZEI_KIN(taxAmountSum);
			dtlMap.setSWK_ZEI_KBN(dtl.getSWK_ZEI_KBN());
			dtlMap.setTax(tax);
			dtlMap.setCUR_DEC_KETA(dtl.getCUR_DEC_KETA());

			addMap.put(key, dtlMap);

		}

		for (String key : totalMap.keySet()) {
			SWK_DTL totalDtl = totalMap.get(key);
			SWK_DTL addDtl = addMap.get(key);

			// 絶対値
			BigDecimal totalDtlKin = totalDtl.getSWK_IN_ZEI_KIN().abs();
			BigDecimal addDtlKin = addDtl.getSWK_IN_ZEI_KIN().abs();

			if (!DecimalUtil.equals(totalDtlKin, addDtlKin)) {
				String inOutZei = totalDtl.getSWK_ZEI_KBN() == TaxCalcType.OUT.value ? getWord(TaxCalcType.OUT
					.getName()) : getWord(TaxCalcType.IN.getName()); // 内税or外税
				String zeiRate = inOutZei + " " + getWord("C01554")
					+ DecimalUtil.toBigDecimalNVL(totalDtl.getTax().getRate()) + "%";// 税率XX%
				String totalZei = NumberFormatUtil.formatNumber(totalDtlKin, totalDtl.getCUR_DEC_KETA());
				String addZei = NumberFormatUtil.formatNumber(addDtlKin, addDtl.getCUR_DEC_KETA());

				String message = getMessage("I01080", zeiRate, totalZei, addZei);
				// 消費税：{0}の総額で消費税を計算すると[{1}]ですが、消費税計算後総額すると[{2}]です。

				list.add(message);
			}
		}

		if (list.size() != 0) {
			if (ConfermMessageListDialog.OK_OPTION != showConfermList(mainView, getMessage("I01079"), list)) {
				// 消費税額が総額計算と明細合計で異なっています。続行しますか？
				return false;
			}
		}
		return true;

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
	 * 消費税Entity取得
	 * 
	 * @param cmpCode 会社コード
	 * @param code 消費是コード
	 * @return 消費税
	 * @throws TException
	 */
	protected ConsumptionTax setTaxEntity(String cmpCode, String code) throws TException {

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
		condition.setCompanyCode(cmpCode);
		condition.setCode(code);
		List<ConsumptionTax> list = (List<ConsumptionTax>) request(ConsumptionTaxManager.class, "get", condition);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
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
	 * 明細金額の取得
	 * 
	 * @param dtl
	 * @param amount
	 * @return 明細金額
	 */
	protected BigDecimal getAmount(SWK_DTL dtl, BigDecimal amount) {

		amount = DecimalUtil.avoidNull(dtl.getSWK_IN_KIN());

		if (!dtl.isDR()) {
			// Crの場合マイナス
			amount = amount.negate();
		}

		return amount;
	}

	/**
	 * 発生日に対する通貨レート取得
	 * 
	 * @param hasDate 発生日
	 * @param curCode 通貨コード
	 * @param isClosing 
	 * 
	 * @return レート
	 */
	protected BigDecimal getCurrencyRateByOccurDate(Date hasDate, String curCode, Boolean isClosing) {
		BigDecimal rate = null;
		try {

			if (curCode.equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}
			String key = curCode + "<>" + DateUtil.toYMDString(hasDate);

			if (rateMapByOccurDate.containsKey(key) && rateMapByOccurDate.get(key) != null) {
				rate = rateMapByOccurDate.get(key);
			} else {
				rate = (BigDecimal) request(RateManager.class,
						isClosing ? "getSettlementRateByOccurDate" : "getRateByOccurDate", curCode, hasDate);
				rateMapByOccurDate.put(key, rate);
			}
		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
		return rate;
	}

	/**
	 * 画面入力の反映(ヘッダ)
	 * 
	 * @param hdr ヘッダ
	 */
	protected void reflectHeader(SWK_HDR hdr) {

		// 新規かどうか
		boolean isNew = hdr.getINP_DATE() == null;

		hdr.setSWK_UPD_KBN(SlipState.ENTRY.value); // 更新区分
		hdr.setSWK_SHR_KBN(Slip.SHR_KBN.NON_LOCKED); // 排他

		hdr.setKAI_CODE(getCompanyCode()); // 会社コード
		hdr.setSWK_DEN_DATE(mainView.ctrlSlipDate.getValue()); // 伝票日付
		hdr.setSWK_DEN_NO(mainView.ctrlSlipNo.getValue()); // 伝票番号
		hdr.setSWK_SEI_NO(mainView.ctrlEvidenceNo.getValue()); // 証憑番号/請求書No.
		hdr.setSWK_TEK_CODE(mainView.ctrlSlipRemark.getCode()); // 伝票摘要
		hdr.setSWK_TEK(mainView.ctrlSlipRemark.getNames());// 伝票摘要
		hdr.setSWK_KSN_KBN(mainView.ctrlCloseEntry.getStage());// 決算仕訳

		hdr.setSWK_DEN_SYU_NAME(slipType.getName());
		hdr.setSWK_DEN_SYU_NAME_S(slipType.getNames());
		hdr.setSWK_DEN_SYU_NAME_K(slipType.getNamek());

		// 伝票設定
		if (isNew) {
			hdr.setSWK_SYS_KBN(slipType.getSystemDiv()); // システム区分
			hdr.setSWK_DEN_SYU(slipType.getCode()); // 伝票種別
			hdr.setSWK_DATA_KBN(slipType.getDataType()); // データ区分
		}

		// 通貨情報
		hdr.setKEY_CUR_CODE(keyCurrency.getCode());
		hdr.setKEY_CUR_DEC_KETA(keyCurrency.getDecimalPoint());
		hdr.setFUNC_CUR_CODE(conf.getFunctionalCurrency().getCode());
		hdr.setFUNC_CUR_DEC_KETA(conf.getFunctionalCurrency().getDecimalPoint());

		// 伝票添付
		hdr.setAttachments(mainView.btnAttach.getAttachments());

		// 付箋機能
		List<SWK_TAG> list = new ArrayList<SWK_TAG>();
		if (mainView.ctrlTag1.getEntity() != null) {
			Tag bean = mainView.ctrlTag1.getEntity();
			SWK_TAG tag = new SWK_TAG();
			tag.setKAI_CODE(getCompanyCode());
			tag.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
			tag.setSEQ(1);
			tag.setTAG_CODE(bean.getCode());
			tag.setTAG_COLOR(bean.getColor());
			tag.setTAG_TITLE(mainView.ctrlTag1.getName()); // 画面値を使用
			list.add(tag);
		}
		if (mainView.ctrlTag2.getEntity() != null) {
			Tag bean = mainView.ctrlTag2.getEntity();
			SWK_TAG tag = new SWK_TAG();
			tag.setKAI_CODE(getCompanyCode());
			tag.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
			tag.setSEQ(2);
			tag.setTAG_CODE(bean.getCode());
			tag.setTAG_COLOR(bean.getColor());
			tag.setTAG_TITLE(mainView.ctrlTag2.getName()); // 画面値を使用
			list.add(tag);
		}
		hdr.setSwkTags(list);
		hdr.setSWK_APRV_GRP_CODE(mainView.ctrlAprvGroup.getCode());
		hdr.setAprRoleGroup(mainView.ctrlAprvGroup.getEntity());
	}

	/**
	 * 画面入力の反映(明細)
	 */
	protected void reflectDetails() {
		SWK_HDR hdr = slip.getHeader();
		String slipRemarks = hdr.getSWK_TEK();

		slip.clearDetail();

		for (SWK_DTL dtl : mainView.ctrlDetail.getEntityList()) {
			SWK_DTL cdtl = dtl.clone();

			// 内税の場合、金額の編集
			if (cdtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().subtract(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().subtract(cdtl.getSWK_ZEI_KIN()));
			}

			// 行摘要未設定の場合は、伝票摘要をセット
			if (Util.isNullOrEmpty(cdtl.getSWK_GYO_TEK())) {
				cdtl.setSWK_GYO_TEK(slipRemarks);
			}

			slip.addDetail(cdtl);
		}
	}

	/**
	 * 画面入力の反映(パターン明細)
	 */
	protected void reflectPatternDetails() {

		SWK_HDR hdr = slip.getHeader();
		String slipRemarks = hdr.getSWK_TEK();

		slip.clearDetail();

		for (SWK_DTL dtl : mainView.ctrlDetail.getEntityList()) {
			SWK_DTL cdtl = dtl.clone();

			// 内税の場合、金額の編集
			if (cdtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().subtract(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().subtract(cdtl.getSWK_ZEI_KIN()));
			}

			// 行摘要未設定の場合は、伝票摘要をセット
			if (Util.isNullOrEmpty(cdtl.getSWK_GYO_TEK())) {
				cdtl.setSWK_GYO_TEK(slipRemarks);
			}

			cdtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
			cdtl.setSWK_KESI_DATE(null); // 消込伝票日付
			cdtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
			cdtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
			cdtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
			cdtl.setAP_ZAN(null);
			cdtl.setAR_ZAN(null);
			cdtl.setBsDetail(null);

			slip.addDetail(cdtl);
		}
	}

	/**
	 * 仕訳辞書検索
	 */
	public void doPattern() {
		searchPatternSlip();
	}

	/**
	 * 辞書登録
	 */
	public void doPatternSave() {

		// 入力チェック
		if (!checkPatternSaveInput()) {
			return;
		}

		createPatternSaveView();
		patternSaveView.setLocationRelativeTo(null);
		patternSaveView.setVisible(true);
	}

	/**
	 * 入力値チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkPatternSaveInput() {

		return true;
	}

	/**
	 * 辞書登録ダイアログを開く
	 */
	protected void createPatternSaveView() {

		// モード選択ダイアログ生成
		patternSaveView = new TSlipPatternSaveDialog(mainView.getParentFrame(), true);
		// イベント定義
		addPatternSaveViewEvent();
	}

	/**
	 * 辞書登録ダイアログイベント定義
	 */
	protected void addPatternSaveViewEvent() {

		// 閉じるボタン押下時
		patternSaveView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				patternSaveView.setVisible(false);
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 確定ボタン押下時
		patternSaveView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnPatternSave_Settle_click();
				patternSaveView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 辞書登録ダイアログ確定ボタン押下時
	 */
	protected void btnPatternSave_Settle_click() {

		try {
			// 入力チェック
			if (!checkPatternNoInput()) {
				return;
			}

			// 伝票に反映
			Slip saveSlip = getSavePatternSlip();

			// パターン番号存在チェック
			SWK_HDR existsPattern = (SWK_HDR) request(getManagerClass(), "getExistsPattern",
				patternSaveView.ctrlPatternNo.getValue());
			if (existsPattern != null) {
				if (!showConfirmMessage(patternSaveView, "Q00079")) { // 指定のパターン番号は既に存在します。上書きしますか？
					patternSaveView.ctrlPatternNo.requestFocus();
					return;
				}
				saveSlip.getHeader().setSWK_UPD_CNT(existsPattern.getSWK_UPD_CNT());
				saveSlip.getHeader().setINP_DATE(existsPattern.getINP_DATE());
				saveSlip.getHeader().setSWK_INP_DATE(existsPattern.getINP_DATE());
				saveSlip.getHeader().setSWK_SYS_KBN(existsPattern.getSWK_SYS_KBN()); // システム区分
				saveSlip.getHeader().setSWK_DEN_SYU(existsPattern.getSWK_DEN_SYU()); // 伝票種別
				saveSlip.getHeader().setSWK_DATA_KBN(existsPattern.getSWK_DATA_KBN()); // データ区分
			}

			// 登録
			request(getManagerClass(), "entryPattern", saveSlip);

			patternSaveView.setVisible(false);

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 入力値チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkPatternNoInput() {

		// パターン番号が未入力の場合
		if (!checkInputBlank(patternSaveView.ctrlPatternNo.getField(), "C00987")) {
			return false;
		}
		return true;
	}

	/**
	 * プレビュー
	 */
	public void doPreview() {

		try {
			Slip tempSlip = getSaveSlip();

			if (tempSlip.getDetails() == null) {
				tempSlip.setDetails(new ArrayList<SWK_DTL>());
			}

			// TODO:出力可能の制御
			if (tempSlip.getDetails().size() == 0) {
				tempSlip.addDetail(new SWK_DTL());
			}

			byte[] data = (byte[]) request(SlipManager.class, "getTempSlipReport", tempSlip);
			if (data != null && data.length != 0) {
				TPrinter printer = new TPrinter();
				printer.preview(data, getPrintName() + ".pdf");
			}
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 伝票パターン検索と反映
	 */
	protected void searchPatternSlip() {
		searchPatternSlipAddResult();
	}

	/**
	 * 伝票パターン検索と反映
	 * 
	 * @return true:成功
	 */
	protected boolean searchPatternSlipAddResult() {

		try {
			TSlipPatternSearchCtrl ctrl = createPatternSearchCtrl();

			ctrl.switchSelfOnly(); // 自身のデータのみ呼び出し
			ctrl.setIncludeLocked(true); // 排他中データも含む

			if (ctrl.show() != TSlipSearchCtrl.OK_OPTION) {
				mainView.btnPattern.requestFocus();
				return false;
			}

			// 現在の伝票日付を取っておく
			Date slipDate = mainView.ctrlSlipDate.getValue();

			// 一旦クリア
			clearView();

			// 反映
			SWK_HDR hdr = ctrl.getSelectedRow();

			// 伝票構築
			slip = (Slip) request(getManagerClass(), "getPatternSlip", hdr);

			if (isInvoice && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
				patternSlipType = slip.getHeader().getSWK_DEN_SYU();
				if (checkInvoiceItemTaxCode(slip.getDetails())) {
					// インボイス用:取引先と消費税を非適格、適格かチェック
					patternSlipType = null;
					return false;
				}
				patternSlipType = null;
			}

			// 画面反映
			dispatch();

			// 新規扱い
			slip = null;
			setSlipDate(slipDate); // 日付は元のまま
			mainView.ctrlSlipNo.clear();
			switchNew();

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * パターン検索コントローラ生成
	 * 
	 * @return パターン検索コントローラ
	 */
	protected TSlipPatternSearchCtrl createPatternSearchCtrl() {
		TSlipPatternSearchCtrl ctrl = new TSlipPatternSearchCtrl(mainView);
		ctrl.setSlipType(slipType.getCode()); // 伝票種別
		ctrl.setDataKind(slipType.getDataType()); // データ区分

		return ctrl;
	}

	/**
	 * 伝票日付の変更
	 */
	protected void changedSlipDate() {
		Date slipDate = mainView.ctrlSlipDate.getValue();

		// 伝票日付を基準日としてセット
		mainView.ctrlSlipRemark.getSearchCondition().setValidTerm(slipDate);
		mainView.ctrlDetail.setBaseDate(slipDate);
	}

	/**
	 * 決算仕訳の切替
	 */
	protected void changedClosingEntry() {
		mainView.ctrlDetail.setClosingEntry(mainView.ctrlCloseEntry.isSelected());
	}

	/**
	 * 合計値計算
	 */
	protected void summary() {
		mainView.ctrlDetail.summary();
	}

	/**
	 * 画面起動/終了時の排他解除処理
	 * 
	 * @see jp.co.ais.trans2.model.security.TExclusive#getExclusiveControlMethod()
	 */
	@Override
	public TExclusiveControlMethod getExclusiveControlMethod() {
		return new SlipExclusiveControlMethod(this.getProgramInfo(), getSlipTypeNo());
	}

	/**
	 * 伝票検索と反映(ドリルダウン画面から遷移)
	 * 
	 * @param isModify true:修正 false:複写
	 * @param hdr
	 * @param copyMode 0:通常 1:赤伝 2:逆伝
	 * @return true:成功
	 */
	public boolean searchSlipAddResultNoneDialog(boolean isModify, SWK_HDR hdr, int copyMode) {

		try {
			// 起動中を考慮して一旦新規リセット
			clearView();

			// モードセット
			this.isModifyMode = isModify;

			// 現在の伝票日付を取っておく
			Date slipDate = mainView.ctrlSlipDate.getValue();

			// 伝票構築
			slip = (Slip) request(getManagerClass(), "getSlip", hdr, isModify);

			// 複写の場合
			if (!isModify) {
				// 伝票番号クリア
				slip.setSlipNo(null);

				// 複写時、添付ファイルをクリア
				slip.getHeader().setAttachments(null);

				// 複写時、付箋機能をクリア
				slip.getHeader().setSwkTags(null);

				Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

				// 明細の紐付け初期化
				for (SWK_DTL dtl : slip.getDetails()) {
					dtl.setSWK_DEN_NO(null);
					dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
					dtl.setSWK_KESI_DATE(null); // 消込伝票日付
					dtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
					dtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
					dtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
					dtl.setSWK_SOUSAI_GYO_NO(null); // 相殺行番号

					dtl.setAP_ZAN(null);
					dtl.setAR_ZAN(null);
					dtl.setBsDetail(null);

					if (isCopySlipDateToOccurDate) {
						// 複写時に発生日を伝票日付に変更する
						dtl.setHAS_DATE(slipDate);

						Company kcompany = dtl.getAppropriateCompany();
						Currency currency = dtl.getCurrency();
						ConsumptionTax tax = dtl.getTax();

						BigDecimal amount = dtl.getSWK_IN_KIN();
						BigDecimal taxAmount = dtl.getSWK_IN_ZEI_KIN();

						BigDecimal rate = null;
						String key = currency != null ? currency.getCode() : keyCurrency.getCode();
						if (rateMap.containsKey(key)) {
							rate = rateMap.get(key);
						} else {
							rate = getCurrencyRate(currency, slip.isClosingSlip(), slipDate);
							rateMap.put(key, rate);
						}

						// 発生日チェックを使用する場合
						if (isUseHasDateChk) {
							rate = getCurrencyRateByOccurDate(slipDate, currency.getCode(), slip.isClosingSlip());
						}

						BigDecimal keyAmount = convertKeyAmount(amount, rate, kcompany, currency);
						BigDecimal keyTaxAmount = convertKeyTaxAmount(taxAmount, rate, kcompany, currency, tax);

						dtl.setSWK_CUR_RATE(rate);
						dtl.setSWK_KIN(keyAmount);
						dtl.setSWK_ZEI_KIN(keyTaxAmount);
					}
				}

				if (copyMode == 1) {
					// 赤
					slip = slip.toCancelSlip();

				} else if (copyMode == 2) {
					// 逆
					slip = slip.toReverseSlip();
				}

			}

			// 画面反映
			dispatch();

			if (isModify) {
				// 修正
				switchModify();

			} else {
				// 複写(新規扱い)
				slip = null;
				if (!notReservationSlipDate) {
					setSlipDate(slipDate); // 日付は元のまま
				}
				mainView.ctrlSlipNo.clear();

				if (!notReservationCloseEntry) {
					mainView.ctrlCloseEntry.setSelected(false); // 決算仕訳
				}

				switchNew();
			}

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}
}