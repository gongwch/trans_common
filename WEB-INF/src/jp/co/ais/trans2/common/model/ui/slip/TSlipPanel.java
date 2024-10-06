package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.objsave.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 伝票入力パネル
 */
public abstract class TSlipPanel extends TMainObjectSavePanel {

	/** 新規モード */
	protected static final int MODE_NEW = 1;

	/** 編集モード */
	protected static final int MODE_MOD = 2;

	/** 左開始位置 */
	protected final int X_POINT = 5;

	/** 状態ラベル */
	public TLabel lblState;

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 修正ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** 確定 */
	public TImageButton btnEntry;

	/** 仕訳辞書ボタン */
	public TImageButton btnPattern;

	/** 仕訳辞書登録ボタン */
	public TImageButton btnPatternSave;

	/** プレビューボタン */
	public TImageButton btnPreview;

	/** 伝票ヘッダパネル */
	public TPanel pnlSlipHdr;

	/** 伝票日付 */
	public TSlipDate ctrlSlipDate;

	/** 伝票番号 */
	public TSlipNo ctrlSlipNo;

	/** 証憑No./請求書No. */
	public TLabelField ctrlEvidenceNo;

	/** 添付ファイルボタン */
	public TAttachButton btnAttach;

	/** 決算仕訳 */
	public TClosingEntryCheck ctrlCloseEntry;

	/** 伝票印刷停止 */
	public TSlipPrintStopCheck ctrlPrintStop;

	/** 伝票摘要 */
	public TRemarkReference ctrlSlipRemark;

	/** 承認グループ */
	public TAprvRoleGroupReference ctrlAprvGroup;

	/** ヘッダと明細の境界線 */
	public JSeparator sepSlip;

	/** 伝票明細パネル */
	public TPanel pnlSlipDtl;

	/** 明細 */
	public TSlipDetailPanel ctrlDetail;

	/** 新規/編集モード */
	public int mode = MODE_NEW;

	/** ヘッダと明細の境界線 可変 */
	public JSplitPane spBody;

	/** コントローラー */
	public TSlipPanelCtrl controller;

	/** 付箋1 */
	public TTagReference ctrlTag1;

	/** 付箋2 */
	public TTagReference ctrlTag2;
	
	/**
	 * コンストラクタ.
	 */
	public TSlipPanel() {
		super();
	}
	
	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 */
	public TSlipPanel(Company company) {
		super(company);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		lblState = new TLabel();
		btnNew = new TImageButton(IconType.NEW);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnEntry = new TImageButton(IconType.SETTLE);
		btnPattern = new TImageButton(IconType.PATTERN);
		btnPatternSave = new TImageButton(IconType.PATTERN);
		btnPreview = new TImageButton(IconType.PREVIEW);

		pnlSlipHdr = new TPanel();
		pnlSlipDtl = new TPanel();

		// ヘッダ共通
		ctrlSlipDate = new TSlipDate();
		ctrlSlipNo = new TSlipNo();
		ctrlCloseEntry = new TClosingEntryCheck(ctrlSlipDate);
		ctrlEvidenceNo = new TLabelField();
		btnAttach = new TAttachButton(this);
		ctrlSlipRemark = new TRemarkReference(true);
		ctrlAprvGroup = new TAprvRoleGroupReference();
		ctrlPrintStop = new TSlipPrintStopCheck();

		// 明細
		ctrlDetail = createDetailPanel();

		// 初期設定 --

		// 伝票日付
		ctrlSlipDate.setAllowableBlank(false);

		// 伝票番号
		ctrlSlipNo.setLabelSize(60);

		// 証憑番号
		ctrlEvidenceNo.setLabelSize(60);
		ctrlEvidenceNo.setMaxLength(20);

		// 伝票摘要
		ctrlSlipRemark.btn.setLangMessageID("C00569");

		// 付箋機能
		ctrlTag1 = new TTagReference();
		ctrlTag2 = new TTagReference();

	}

	/**
	 * 明細パネル作成
	 * 
	 * @return 明細パネル
	 */
	public TSlipDetailPanel createDetailPanel() {

		if (isTFormMode()) {
			return new TFormSlipDetailPanel(this);
		}
		return new TSlipDetailPanel(this);
	}

	/**
	 * @return true: T-Formモード
	 */
	public boolean isTFormMode() {
		return false; // 各伝票入力画面でOverrideする
	}

	/**
	 * @return T-Formモード時の初期明細件数
	 */
	public int getTFormDetailCount() {
		return 2; // 各伝票入力画面でOverrideする
	}

	/**
	 * PanelのUI初期化を完結させる. <br>
	 * メッセージの変換表示。 タブ順の設定。<br>
	 * メッセージIDの設定、タブ順番号の設定が完了したら即このmethodを呼び出す。
	 */
	public void resetPanelTabIndex() {
		initPanel();
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// ボタン部
		allocateButtons();

		// BODY
		pnlBody.setLayout(new BorderLayout());

		TPanel hdr = new TPanel(new GridBagLayout());

		// ヘッダー領域
		pnlSlipHdr.setLayout(null);
		TGuiUtil.setComponentSize(pnlSlipHdr, new Dimension(0, 0));

		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;

		gc.gridx = 0;
		gc.gridy = 0;
		hdr.add(pnlSlipHdr, gc);

		allocateSlipHeader();
		pnlBody.add(hdr, BorderLayout.NORTH);

		// 明細領域
		pnlSlipDtl.setLayout(new BorderLayout());
		pnlSlipDtl.add(ctrlDetail);

		// ユーザー設定により、表示コンポーネントを切り替える
		if (ClientConfig.isFlagOn("trans.slip.splitpane")) {
			// 可変境界線
			spBody = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlSlipHdr, pnlSlipDtl);
			spBody.setOpaque(false);
			pnlSlipHdr.setMinimumSize(new Dimension(0, 0));

			pnlBody.add(spBody, BorderLayout.CENTER);

		} else {
			// 通常境界線
			sepSlip = new JSeparator();
			TGuiUtil.setComponentSize(sepSlip, new Dimension(0, 3));
			gc.gridx = 0;
			gc.gridy = 1;
			hdr.add(sepSlip, gc);

			pnlSlipDtl.setMinimumSize(new Dimension(0, 550));

			pnlBody.add(pnlSlipDtl, BorderLayout.CENTER);

		}

	}

	/**
	 * ボタン部の配置
	 */
	public void allocateButtons() {
		int x = X_POINT;
		// switchNew();
		lblState.setSize(30, 25);
		lblState.setHorizontalAlignment(SwingConstants.CENTER);
		lblState.setOpaque(true);
		lblState.setLocation(x, HEADER_Y);
		pnlHeader.add(lblState);

		// 新規ボタン
		x = x + lblState.getWidth() + HEADER_MARGIN_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F2);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 修正
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C01760");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);

		// 複写
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// 確定
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnEntry.setLangMessageID("C01019");
		btnEntry.setShortcutKey(KeyEvent.VK_F8);
		btnEntry.setSize(25, 110);
		btnEntry.setLocation(x, HEADER_Y);
		pnlHeader.add(btnEntry);

		// パターン
		x = x + btnEntry.getWidth() + HEADER_MARGIN_X;
		btnPattern.setLangMessageID("C00300");
		btnPattern.setShortcutKey(KeyEvent.VK_F11);
		btnPattern.setSize(25, 110);
		btnPattern.setLocation(x, HEADER_Y);
		pnlHeader.add(btnPattern);

		// パターン登録
		x = x + btnPattern.getWidth() + HEADER_MARGIN_X;
		btnPatternSave.setLangMessageID("C11887"); // 辞書登録
		btnPatternSave.setShortcutKey(KeyEvent.VK_F12);
		btnPatternSave.setSize(25, 110);
		btnPatternSave.setLocation(x, HEADER_Y);
		pnlHeader.add(btnPatternSave);

		// プレビュー
		x = x + btnPatternSave.getWidth() + HEADER_MARGIN_X;
		btnPreview.setLangMessageID("C10019"); // プレビュー
		btnPreview.setShortcutKey(KeyEvent.VK_F9);
		btnPreview.setSize(25, 110);
		btnPreview.setLocation(x, HEADER_Y);
		pnlHeader.add(btnPreview);
	}

	/**
	 * ヘッダの配置
	 */
	public abstract void allocateSlipHeader();

	/**
	 * 継承先でヘッダの番号をセットした後に呼ぶ
	 * 
	 * @param i 番号
	 */
	public void setTabIndex(int i) {

		// 明細
		i = ctrlDetail.setTabIndex(i);

		// ボタン
		btnNew.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnEntry.setTabControlNo(i++);
		btnPattern.setTabControlNo(i++);
		btnPatternSave.setTabControlNo(i++);
		btnPreview.setTabControlNo(i++);
	}

	/**
	 * 新規モード切替
	 */
	public void switchNew() {
		lblState.setBackground(new Color(0, 240, 255));
		lblState.setText(getShortWord("C00303"));

		mode = MODE_NEW;
	}

	/**
	 * 編集モード切替
	 */
	public void switchModify() {
		lblState.setBackground(new Color(255, 255, 50));
		lblState.setText(getShortWord("C00169"));

		mode = MODE_MOD;
	}

	/**
	 * 新規モードかどうか
	 * 
	 * @return true:新規モード
	 */
	public boolean isModifyMode() {
		return mode == MODE_MOD;
	}

	/**
	 * @param tSlipPanelCtrl
	 */
	public void setSlipController(TSlipPanelCtrl tSlipPanelCtrl) {
		this.controller = tSlipPanelCtrl;

	}

	/**
	 * 伝票画面CTRLの取得
	 * 
	 * @return controller 伝票画面CTRL
	 */
	public TSlipPanelCtrl getController() {
		return controller;
	}

	/**
	 * 伝票画面CTRLの設定
	 * 
	 * @param controller 伝票画面CTRL
	 */
	public void setController(TSlipPanelCtrl controller) {
		this.controller = controller;
	}
}
