package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票照会コンポーネント
 * 
 * @author AIS
 */
public class TDrillDown extends TFrame {

	/** 修正ボタン表示→伝票編集を可能にするか否か */
	public static boolean isDrillDownSlipModify = ClientConfig.isFlagOn("trans.use.drilldown.slip.modify");

	/** 複写ボタン表示→伝票編集を可能にするか否か */
	public static boolean isDrillDownSlipCopy = ClientConfig.isFlagOn("trans.use.drilldown.slip.copy");

	/** コントローラ */
	public TDrillDownController ctrl;

	/** メインボタン領域 */
	public TPanel pnlButton;

	/** 帳簿区分 */
	public TBookComboBox cmbBookComboBox;

	/** 通貨選択 */
	public TBookCurrencyComboBox cmbBookCurrencyComboBox;

	/** ボタン領域 */
	public TPanel pnlButtons;

	/** ← */
	public TButton btnPrevSlip;

	/** → */
	public TButton btnNextSlip;

	/** 承認ボタン */
	public TButton btnApprove;

	/** 否認ボタン */
	public TButton btnDeny;

	/** 承認取消ボタン */
	public TButton btnCancel;

	/** 添付出力ボタン */
	public TButton btnAttachment;

	/** 承認履歴ボタン */
	public TButton btnApproveHistory;

	/** プレビューボタン */
	public TButton btnPreview;

	/** 閉じるボタン */
	public TButton btnReturn;

	/** 境界線 */
	public JSeparator sep;

	/** ボティ領域 */
	public TPanel pnlBody;

	/** 伝票ヘッダー */
	public TPanel pnlSlipHeader;

	/** ドリルダウンヘッダーフィールド */
	public TDrillDownHeader ctrlDrillDownHeader;

	/** 境界線 */
	public JSeparator sep2;

	/** 伝票明細 */
	public TTable tblSlipDetail;

	/** 伝票フッター */
	public TPanel pnlSlipFotter;

	/** 借方合計金額 */
	public TLabelNumericField txDebitSum;

	/** 外貨借方計1 */
	public TLabelNumericField txDebitInSum1;

	/** 外貨借方計2 */
	public TLabelNumericField txDebitInSum2;

	/** 貸方合計金額 */
	public TNumericField txCreditSum;

	/** 外貨貸方計1 */
	public TNumericField txCreditInSum1;

	/** 外貨貸方計2 */
	public TNumericField txCreditInSum2;

	/** 外貨1 */
	public TTextField txCur1;

	/** 外貨2 */
	public TTextField txCur2;

	/** 外貨フィールドリスト */
	public List<TDrillDownForeignField> foreignList;

	/** グループ会社の関数名 */
	protected String groupMethodName = "getReportBySlipNos";

	/** 通常の関数名 */
	protected String methodName = "getReport";

	/** マネージャクラス名 */
	protected Class managerClass = SlipManager.class;

	/** 修正ボタン */
	public TButton btnModify;

	/** 複写ボタン */
	public TButton btnCopy;

	/** スプレッドシートのカラム定義 */
	public enum SC {
		/** 科目 */
		item,
		/** 借方 */
		debit,
		/** 貸方 */
		credit,
		/** 通貨 */
		currencyCode,
		/** 摘要 */
		remark,
		/** 部門 */
		department,
		/** 管理1 */
		management1to3,
		/** 管理4 */
		management4to6,
		/** 非会計明細 */
		nonAccount,
		/** BS情報 */
		bs,
		/** AP情報 */
		ap,
		/** AR情報 */
		ar
	}

	/**
	 * コンストラクタ
	 */
	public TDrillDown() {

		initComponents();

		allocateComponents();

		setTabIndex();

		ctrl = createController();

		// TMainCtrlに関連付け
		TMainCtrl.getInstance().addFrame(this);
	}

	/**
	 * コンポーネントの初期設定
	 */
	public void initComponents() {
		pnlButton = new TPanel();
		cmbBookComboBox = new TBookComboBox();
		cmbBookCurrencyComboBox = new TBookCurrencyComboBox();
		pnlButtons = new TPanel();
		btnApprove = new TButton();
		btnDeny = new TButton();
		btnCancel = new TButton();
		btnAttachment = new TImageButton(IconType.ATTACHMENT);
		btnApproveHistory = new TImageButton(IconType.PATTERN);
		btnPreview = new TImageButton(IconType.PREVIEW);
		btnReturn = new TButton();
		sep = new JSeparator();
		sep2 = new JSeparator();
		pnlBody = new TPanel();
		pnlSlipHeader = new TPanel();
		tblSlipDetail = new TTable();
		pnlSlipFotter = new TPanel();
		txDebitSum = new TLabelNumericField();
		txCreditSum = new TNumericField();

		btnPrevSlip = new TImageButton(IconType.ALLOW_PREVIOUS);
		btnNextSlip = new TImageButton(IconType.ALLOW_NEXT);

		// 修正
		btnModify = new TImageButton(IconType.EDIT);
		// 複写
		btnCopy = new TImageButton(IconType.COPY);

		// 外貨フィールドの初期化
		initForeignField();
	}

	/**
	 * 外貨フィールドの初期化
	 */
	public void initForeignField() {

		foreignList = new ArrayList<TDrillDownForeignField>();
		for (int i = 0; i < 4; i++) {
			foreignList.add(new TDrillDownForeignField());
		}

		// 既存機能の影響対応
		txDebitInSum1 = foreignList.get(0).txDebitInSum;
		txDebitInSum2 = foreignList.get(1).txDebitInSum;
		txCreditInSum1 = foreignList.get(0).txCreditInSum;
		txCreditInSum2 = foreignList.get(1).txCreditInSum;
		txCur1 = foreignList.get(0).txCur;
		txCur2 = foreignList.get(1).txCur;

	}

	/**
	 * コンポーネントの配置を行います。
	 */
	public void allocateComponents() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setTitle(getWord("C03662") + getWord("C00075")); // 伝票照会画面
		setSize(820, 650);

		// メインボタン領域
		GridBagConstraints gc = new GridBagConstraints();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(0, 40));
		pnlButton.setMinimumSize(new Dimension(0, 40));
		pnlButton.setPreferredSize(new Dimension(0, 40));
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(pnlButton, gc);

		// 帳簿区分
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 15, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlButton.add(cmbBookComboBox, gc);

		// 表示通貨
		pnlButton.add(cmbBookCurrencyComboBox, gc);

		// ←
		pnlButton.add(btnPrevSlip, gc);

		// →
		pnlButton.add(btnNextSlip, gc);

		// ボタン領域
		pnlButtons.setLayout(null);
		TGuiUtil.setComponentSize(pnlButtons, 700, 25);
		gc.insets = new Insets(0, 0, 0, 20);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.EAST;
		pnlButton.add(pnlButtons, gc);

		int x = 0;
		int y = 0;
		// 修正
		if (isDrillDownSlipModify) {
			btnModify.setLangMessageID("C01760");
			btnModify.setShortcutKey(KeyEvent.VK_F3);
			btnModify.setSize(25, 120);
			btnModify.setLocation(x, y);
			pnlButtons.add(btnModify);

			int width = btnModify.getWidth() + 5;
			x += width;
			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);
		}

		// 複写
		if (isDrillDownSlipCopy) {
			btnCopy.setLangMessageID("C00459");
			btnCopy.setShortcutKey(KeyEvent.VK_F4);
			btnCopy.setSize(25, 120);
			btnCopy.setLocation(x, y);
			pnlButtons.add(btnCopy);
			int width = btnCopy.getWidth() + 5;
			x += width;
			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);

		}
		// 承認ボタン
		btnApprove.setLangMessageID("C01168");
		btnApprove.setShortcutKey(KeyEvent.VK_F8);
		btnApprove.setSize(25, 120);
		btnApprove.setLocation(x, y);
		btnApprove.setVisible(false);
		pnlButtons.add(btnApprove);
		x += btnApprove.getWidth() + 5;

		// 否認ボタン
		btnDeny.setLangMessageID("C00447");
		btnDeny.setShortcutKey(KeyEvent.VK_F9);
		btnDeny.setSize(25, 120);
		btnDeny.setLocation(x, y);
		btnDeny.setVisible(false);
		pnlButtons.add(btnDeny);
		x += btnDeny.getWidth() + 5;

		// 承認取消ボタン
		btnCancel.setLangMessageID("C00285");
		btnCancel.setShortcutKey(KeyEvent.VK_F10);
		btnCancel.setSize(25, 120);
		btnCancel.setLocation(x, y);
		btnCancel.setVisible(false);
		pnlButtons.add(btnCancel);
		x += btnCancel.getWidth() + 70;

		// 承認履歴機能
		if (ClientConfig.isFlagOn("trans.slip.use.approve.history")) {
			// 無効の場合、非表示
			// 承認履歴ボタン
			btnApproveHistory.setLangMessageID("C11755"); // 承認履歴
			btnApproveHistory.setSize(25, 120);
			btnApproveHistory.setShortcutKey(KeyEvent.VK_F5);
			btnApproveHistory.setLocation(x, y);
			pnlButtons.add(btnApproveHistory);

			int width = btnApproveHistory.getWidth() + 5;
			x += width;

			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);
		}

		if (ClientConfig.isFlagOn("trans.slip.use.attachment")) {
			// 無効の場合、非表示
			// 添付表示ボタン
			btnAttachment.setLangMessageID("C11610"); // 添付表示
			btnAttachment.setSize(25, 120);
			btnAttachment.setShortcutKey(KeyEvent.VK_F6);
			btnAttachment.setLocation(x, y);
			pnlButtons.add(btnAttachment);

			int width = btnAttachment.getWidth() + 5;
			x += width;

			TGuiUtil.setComponentSize(pnlButtons, pnlButtons.getWidth() + width, 25);
		}

		// プレビューボタン
		btnPreview.setLangMessageID("C10019");
		btnPreview.setSize(25, 120);
		btnPreview.setShortcutKey(KeyEvent.VK_F7);
		btnPreview.setLocation(x, y);
		pnlButtons.add(btnPreview);

		x += btnPreview.getWidth() + 5;

		// 閉じるボタン
		btnReturn.setText(getWord("C02374")); // 閉じる
		btnReturn.setSize(25, 120);
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setLocation(x, y);
		pnlButtons.add(btnReturn);

		// 境界線
		sep = new JSeparator();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(sep, gc);

		// ボディ領域
		pnlBody.setLayout(new GridBagLayout());
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(pnlBody, gc);

		// 伝票ヘッダー
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlSlipHeader.setLayout(new GridBagLayout());
		pnlBody.add(pnlSlipHeader, gc);

		// 境界線
		sep2 = new JSeparator();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		pnlBody.add(sep2, gc);

		// 伝票明細
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = new Insets(10, 30, 0, 30);
		pnlBody.add(tblSlipDetail, gc);

		// 伝票フッター
		pnlSlipFotter.setLayout(null);
		pnlSlipFotter.setMaximumSize(new Dimension(0, 75));
		pnlSlipFotter.setMinimumSize(new Dimension(0, 75));
		pnlSlipFotter.setPreferredSize(new Dimension(0, 75));

		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(pnlSlipFotter, gc);

		// 借方合計金額
		x = 145;
		txDebitSum.setEditable(false);
		txDebitSum.setFieldSize(100);
		txDebitSum.setLabelSize(100);
		txDebitSum.setLangMessageID("C00165"); // 合計
		txDebitSum.setLocation(x, 0);
		pnlSlipFotter.add(txDebitSum);

		// 貸方合計金額
		x = txDebitSum.getX() + txDebitSum.getWidth();
		txCreditSum.setEditable(false);
		txCreditSum.setSize(100, 20);
		txCreditSum.setLocation(x, 0);
		pnlSlipFotter.add(txCreditSum);

		// 外貨フィールドのコンポーネントの配置を行います。
		allocateForeignField();

	}

	/**
	 * 外貨フィールドのコンポーネントの配置を行います。
	 */
	public void allocateForeignField() {

		int x = txDebitSum.getX();
		int y = txDebitSum.getHeight() + txDebitSum.getY();

		List<String> words = new ArrayList<String>();
		words.add("C01764"); // 外貨計1
		words.add("C01765"); // 外貨計2
		words.add("C11608"); // 外貨計3
		words.add("C11609"); // 外貨計4

		for (int i = 0; i < 4; i++) {

			if (i == 2) {
				// 外貨計3はX、Y変更
				x = txCur1.getX() + txCur1.getWidth() + 20;
				y = txCur1.getY();
			}

			TDrillDownForeignField field = foreignList.get(i);
			TLabelNumericField txDebitInSum = field.txDebitInSum;
			TNumericField txCreditInSum = field.txCreditInSum;
			TTextField txCur = field.txCur;

			// 外貨借方計1
			txDebitInSum.setEditable(false);
			txDebitInSum.setFieldSize(100);
			txDebitInSum.setLabelSize(100);
			txDebitInSum.setLangMessageID(words.get(i)); // 外貨計X
			txDebitInSum.setLocation(x, y);
			pnlSlipFotter.add(txDebitInSum);

			x += txDebitInSum.getWidth();

			// 外貨貸方計1
			txCreditInSum.setEditable(false);
			txCreditInSum.setSize(100, 20);
			txCreditInSum.setLocation(x, y);
			pnlSlipFotter.add(txCreditInSum);

			x += txCreditInSum.getWidth();

			// 通貨1
			txCur.setEditable(false);
			txCur.setSize(60, 20);
			txCur.setLocation(x, y);
			pnlSlipFotter.add(txCur);

			if (i >= 2) {
				// 外貨3～以上はデフォルト非表示
				field.setVisible(false);
			}

			x = txDebitInSum.getX();
			y += txDebitInSum.getHeight();
		}

	}

	/**
	 * コンポーネントにタブ順を設定します。
	 */
	public void setTabIndex() {
		int i = 1;
		cmbBookComboBox.setTabControlNo(i++);
		cmbBookCurrencyComboBox.setTabControlNo(i++);
		btnPrevSlip.setTabControlNo(i++);
		btnNextSlip.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnApprove.setTabControlNo(i++);
		btnDeny.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnApproveHistory.setTabControlNo(i++);
		btnAttachment.setTabControlNo(i++);
		btnPreview.setTabControlNo(i++);
		btnReturn.setTabControlNo(i++);
	}

	/**
	 * 指定された伝票に紐付く伝票を表示する
	 * 
	 * @param slipBooks 伝票
	 * @throws Exception
	 */
	public void show(SlipBooks slipBooks) throws Exception {
		ctrl.show(slipBooks);
	}

	/**
	 * 指定された会社コード、伝票番号に紐付く伝票を抽出し表示する
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNo) throws Exception {
		ctrl.show(companyCode, slipNo);
	}

	/**
	 * 指定された会社コード、伝票番号、Row番号に紐付く伝票を抽出し表示する
	 * 
	 * @param companyCode
	 * @param slipNO
	 * @param i 行
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNO, int i) throws Exception {
		ctrl.show(companyCode, slipNO, i);
	}

	/**
	 * 修正、複写押下不可
	 * 
	 * @param flg false 押下不可
	 */
	public void setBtnPush(boolean flg) {
		ctrl.setBtnPush(flg);
	}

	/**
	 * ドリルダウンヘッダーをセット
	 * 
	 * @param header
	 */
	public void setHeader(TDrillDownHeader header) {

		ctrlDrillDownHeader = header;
		pnlSlipHeader.setMaximumSize(header.getMaximumSize());
		pnlSlipHeader.setMinimumSize(header.getMinimumSize());
		pnlSlipHeader.setPreferredSize(header.getPreferredSize());

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlSlipHeader.add(header, gc);

		initFrame();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return ctrl
	 */
	public TDrillDownController createController() {
		return new TDrillDownController(this);
	}

	/**
	 * グループ会社の関数名の取得
	 * 
	 * @return groupMethodName グループ会社の関数名
	 */
	public String getGroupMethodName() {
		return groupMethodName;
	}

	/**
	 * グループ会社の関数名の設定
	 * 
	 * @param groupMethodName グループ会社の関数名
	 */
	public void setGroupMethodName(String groupMethodName) {
		this.groupMethodName = groupMethodName;
	}

	/**
	 * 通常の関数名の取得
	 * 
	 * @return methodName 通常の関数名
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * 通常の関数名の設定
	 * 
	 * @param methodName 通常の関数名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * マネージャクラス名の取得
	 * 
	 * @return managerClass マネージャクラス名
	 */
	public Class getManagerClass() {
		return managerClass;
	}

	/**
	 * マネージャクラス名の設定
	 * 
	 * @param managerClass マネージャクラス名
	 */
	public void setManagerClass(Class managerClass) {
		this.managerClass = managerClass;
	}

	/**
	 * 承認関連ボタンの表示を切替
	 * @param flag true:表示
	 */
	public void setAprvBtnVisible(boolean flag) {
		ctrl.setAprvBtnVisible(flag);
	}

}
