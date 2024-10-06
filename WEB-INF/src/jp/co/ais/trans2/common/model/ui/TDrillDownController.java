package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.common.model.ui.slip.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL;
import jp.co.ais.trans2.model.slip.SWK_HDR;
import jp.co.ais.trans2.model.tag.*;

/**
 * 伝票照会コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TDrillDownController extends TController {

	/** true:IFRS区分より発生日は科目の発生日フラグにより制御機能有効＜Client＞ */
	public static boolean allowOccurDateBlank = ClientConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** true:完了メッセージを非表示 */
	public static boolean isCompletionNotMessage = ClientConfig.isFlagOn("trans.GL0050.show.completion.not.message");

	/** BS勘定消込機能使うかどうか＜Client＞ */
	public static boolean isUseBS = ClientConfig.isFlagOn("trans.slip.use.bs");

	/** true:BS勘定消込・呼出どちらでもマークを記載<Server> */
	public static boolean isShowAllBS = ClientConfig.isFlagOn("trans.slip.use.bs.sousai.mark");

	/** true:AP勘定消込機能無効＜Client＞ */
	public static boolean isNotUseAP = ClientConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR勘定消込機能無効＜Client＞ */
	public static boolean isNotUseAR = ClientConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** 付替会社のドリルダウン機能使うかどうか＜Client＞ */
	public static boolean isUseGroupCompanyDrillDown = ClientConfig.isFlagOn("trans.slip.drilldown.use.group.company");

	/** 伝票照会ダイアログ */
	protected TDrillDown field;

	/** 照会対象の伝票リスト */
	protected List<SlipBooks> slipBooksList;

	/** 照会対象の伝票 */
	protected SlipBooks slipBooks;

	/** CallBackListener */
	protected List<TDrillDownCallBackListener> callBackListenerList = null;

	/** SlipStateListener */
	protected List<TDrillDownSlipStateListener> slipStateListenerList = null;

	/** 添付ダイアログコントローラー */
	protected TAttachListDialogCtrl ctrl = null;

	/** アクセスできるプログラム一覧 */
	protected List<SystemizedProgram> prgGroupList = null;

	/** ロジック名 */
	protected Map<String, String> logicMap = new TreeMap<String, String>();

	/** true:付箋機能有効 */
	public static boolean isUseTag = ClientConfig.isFlagOn("trans.slip.use.tag");

	/** 承認時オプション: 可能な限り先まで承認 */
	protected boolean isApproveAsMuchAsPossible = false;

	/** 承認グループリスト */
	protected List<AprvRoleGroup> grpList;

	/** 承認ロールリスト */
	protected List<AprvRole> roleList;

	/** Row番号 */
	protected int row;

	/**
	 * @param field
	 */
	public TDrillDownController(TDrillDown field) {
		this.field = field;
		initEvent();
		initField();
		initData();
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * フィールド初期化
	 */
	protected void initField() {

		// 最大化表示
		field.setExtendedState(Frame.MAXIMIZED_BOTH);

		// 機能通貨は選択不可
		field.cmbBookCurrencyComboBox.getComboBox().setEnabled(false);

		// 仕訳一覧初期化
		initSlipDetailTable(getCompany());

		if (!isUseGroupCompanyDrillDown) {
			field.btnPrevSlip.setVisible(false);
			field.btnNextSlip.setVisible(false);
		}
	}

	/**
	 * 伝票ロジックを内部保持.
	 */
	protected void initData() {
		// 011 入金伝票
		logicMap.put("GL0010", "GL0010InputCashFlowSlipPanelCtrl");
		// 012 出金伝票
		logicMap.put("GL0020", "GL0020OutputCashFlowSlipPanelCtrl");
		// 013 振替伝票
		logicMap.put("GL0030", "GL0030TransferSlipPanelCtrl");
		// 014 振戻伝票
		logicMap.put("GL6010", "GL6010ReversingSlipPanelCtrl");
		// 023 債務計上
		logicMap.put("AP0010", "AP0010PaymentSlipPanelCtrl");
		// 031 債権計上
		logicMap.put("AR0010", "AR0010ReceivableSlipPanelCtrl");
	}

	/**
	 * 仕訳一覧初期化
	 * 
	 * @param company
	 */
	protected void initSlipDetailTable(Company company) {

		AccountConfig ac = company.getAccountConfig();

		// 科目
		String itemCaption = ac.getItemName();
		if (!Util.isNullOrEmpty(ac.getSubItemName())) {
			itemCaption = itemCaption + " ・ " + ac.getSubItemName();
		}
		if (ac.isUseDetailItem() && !Util.isNullOrEmpty(ac.getDetailItemName())) {
			itemCaption = itemCaption + " ・ " + ac.getDetailItemName();
		}

		// 管理1～3
		int management1to3Width = -1;
		String management1to3Caption = "";
		if (ac.isUseManagement1() || ac.isUseManagement2() || ac.isUseManagement3()) {

			management1to3Width = 242;

			if (ac.isUseManagement1()) {
				management1to3Caption = ac.getManagement1Name();
			}
			if (ac.isUseManagement2()) {
				management1to3Caption = management1to3Caption + " ・ " + ac.getManagement2Name();
			}
			if (ac.isUseManagement3()) {
				management1to3Caption = management1to3Caption + " ・ " + ac.getManagement3Name();
			}

		}

		// 管理4～6
		int management4to6Width = -1;
		String management4to6Caption = "";
		if (ac.isUseManagement4() || ac.isUseManagement5() || ac.isUseManagement6()) {

			management4to6Width = 242;

			if (ac.isUseManagement4()) {
				management4to6Caption = ac.getManagement4Name();
			}
			if (ac.isUseManagement5()) {
				management4to6Caption = management4to6Caption + " ・ " + ac.getManagement5Name();
			}
			if (ac.isUseManagement6()) {
				management4to6Caption = management4to6Caption + " ・ " + ac.getManagement6Name();
			}

		}

		// 非会計明細1～3
		int nonAccountWidth = -1;
		String nonAccountCaption = "";
		if (ac.isUseNotAccounting1() || ac.isUseNotAccounting2() || ac.isUseNotAccounting3()) {

			nonAccountWidth = 150;

			if (ac.isUseNotAccounting1()) {
				nonAccountCaption = ac.getNonAccounting1Name();
			}
			if (ac.isUseNotAccounting2()) {
				nonAccountCaption = nonAccountCaption + " ・ " + ac.getNonAccounting2Name();
			}
			if (ac.isUseNotAccounting3()) {
				nonAccountCaption = nonAccountCaption + " ・ " + ac.getNonAccounting3Name();
			}

		}

		field.tblSlipDetail.addColumn(TDrillDown.SC.item, itemCaption, 190);
		// 借方金額
		field.tblSlipDetail.addColumn(TDrillDown.SC.debit, getWord("C01557"), 100, SwingConstants.RIGHT);
		// 貸方金額
		field.tblSlipDetail.addColumn(TDrillDown.SC.credit, getWord("C01559"), 100, SwingConstants.RIGHT);
		// "通貨 ・ 発生日 ・ 税"
		field.tblSlipDetail.addColumn(TDrillDown.SC.currencyCode,
			getWord("C00371") + "・" + getWord("C00431") + "・" + getWord("C00312"), 120, SwingConstants.CENTER);
		// 行摘要
		field.tblSlipDetail.addColumn(TDrillDown.SC.remark, getWord("C00119"), 242);
		// "計上部門 ・ 取引先 ・ 社員"
		field.tblSlipDetail.addColumn(TDrillDown.SC.department,
			getWord("C00863") + "・" + getWord("C00408") + "・" + getWord("C00246"), 242);
		field.tblSlipDetail.addColumn(TDrillDown.SC.management1to3, management1to3Caption, management1to3Width);
		field.tblSlipDetail.addColumn(TDrillDown.SC.management4to6, management4to6Caption, management4to6Width);
		field.tblSlipDetail.addColumn(TDrillDown.SC.nonAccount, nonAccountCaption, nonAccountWidth);
		field.tblSlipDetail.addColumn(TDrillDown.SC.bs, getWord("C04291"), isUseBS ? 100 : -1); // BS勘定
		field.tblSlipDetail.addColumn(TDrillDown.SC.ap, getWord("C01084"), !isNotUseAP ? 100 : -1); // 債務残高
		field.tblSlipDetail.addColumn(TDrillDown.SC.ar, getWord("C01080"), !isNotUseAR ? 100 : -1); // 債権残高
		field.tblSlipDetail.setRowHeight(50);
	}

	/**
	 * 画面のイベント定義
	 */
	protected void initEvent() {

		// 帳簿の処理
		field.cmbBookComboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					cmbBookComboBox_itemStateChanged();
				}
			}
		});

		// 通貨選択の処理
		field.cmbBookCurrencyComboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					cmbBookCurrencyComboBox_itemStateChanged();
				}
			}
		});

		// ←
		field.btnPrevSlip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnPrevSlip_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// →
		field.btnNextSlip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNextSlip_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		field.btnApprove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDoApprove_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		field.btnDeny.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDoDenySlip_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		field.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDoCancel_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 添付表示ボタンの処理
		field.btnAttachment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAttachment_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 承認履歴ボタンの処理
		field.btnApproveHistory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnApproveHistory_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// プレビューボタンの処理
		field.btnPreview.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnPreview_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 取消ボタンの処理
		field.btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnReturn_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 修正ボタンの処理
		field.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 複写ボタンの処理
		field.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		field.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				fireChangedSlipHeaderListener();
			}
		});
	}

	/**
	 * 帳簿が選択された場合
	 */
	protected void cmbBookComboBox_itemStateChanged() {

		try {

			if (field.cmbBookComboBox.getComboBox().getSelectedIndex() == 0) {
				field.cmbBookCurrencyComboBox.getComboBox().setEnabled(false);
			} else {
				field.cmbBookCurrencyComboBox.getComboBox().setEnabled(true);
			}
			field.cmbBookCurrencyComboBox.getComboBox().setSelectedIndex(0);

			// 伝票データセット
			setSlip(getSlip());

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 表示通貨が選択された場合
	 */
	protected void cmbBookCurrencyComboBox_itemStateChanged() {

		try {
			// 伝票データセット
			setSlip(getSlip());

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ←伝票
	 */
	protected void btnPrevSlip_Click() {

		try {

			SlipBooks books = slipBooks;

			// 左伝票表示
			if (slipBooksList != null) {
				int index = slipBooksList.indexOf(slipBooks) - 1;
				if (index >= 0) {
					books = slipBooksList.get(index);
					row--;
				}
			}

			// ヘッダー初期化不要
			show(books, false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * →伝票
	 */
	protected void btnNextSlip_Click() {

		try {

			SlipBooks books = slipBooks;

			// 左伝票表示
			if (slipBooksList != null) {
				int index = slipBooksList.indexOf(slipBooks) + 1;
				if (index < slipBooksList.size()) {
					books = slipBooksList.get(index);
					row++;
				}
			}

			// ヘッダー初期化不要
			show(books, false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [承認]押下処理
	 */
	protected void btnDoApprove_Click() {
		try {
			TApproveOptionDialog d = new TApproveOptionDialog(field, true);
			d.setLocationRelativeTo(field);
			d.setVisible(true);
			if (!d.isOK()) {
				// キャンセルの場合処理中断
				return;
			}
			isApproveAsMuchAsPossible = d.isApproveAsMuchAsPossible();
			SWK_HDR hdr = slipBooks.getSlip().getHeader();
			if (hdr == null) {
				throw new TException("I00071");
			}
			SlipDen localDen = getDen(hdr);
			SlipDen bean = (SlipDen) request(getSlipManagerClass(), "checkAndApproveSlip", localDen,
				isApproveAsMuchAsPossible);
			fireUpateSlipStateListener(bean, row);

			// ヘッダーを更新
			updateSlipBookHDR(bean);

			// 取消ボタンを制御
			switchApprovalBtnsEnabled(bean.getSWK_UPD_KBN());

			setSlip(getSlip());
			if (!isCompletionNotMessage) {
				// 完了メッセージ
				showMessage(field, "I00013");
			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [否認]ボタン押下処理
	 */
	protected void btnDoDenySlip_Click() {
		try {
			// FIXME ワークフロー承認ではない通常の承認の場合の否認については必要に応じて制御追加
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [承認取消]押下処理
	 */
	protected void btnDoCancel_Click() {
		try {
			SWK_HDR hdr = slipBooks.getSlip().getHeader();
			if (hdr == null) {
				throw new TException("I00071");
			}
			SlipDen localDen = getDen(hdr);
			SlipDen bean = (SlipDen) request(getSlipManagerClass(), "checkAndCancelApprovedSlip", localDen);
			fireUpateSlipStateListener(bean, row);

			// ヘッダーを更新
			updateSlipBookHDR(bean);
			// ボタン押下可否の切替
			switchApprovalBtnsEnabled(bean.getSWK_UPD_KBN());

			setSlip(getSlip());
			if (!isCompletionNotMessage) {
				// 完了メッセージ
				showMessage(field, "I00013");
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * SlipBooksのヘッダー更新
	 * 
	 * @param slipDen
	 */
	protected void updateSlipBookHDR(SlipDen slipDen) {
		SWK_HDR hdr = slipBooks.getSlip().getHeader();
		hdr.setSWK_UPD_KBN(slipDen.getSWK_UPD_KBN());
		hdr.getSyoCtl().setAPRV_ROLE_CODE(slipDen.getAPRV_ROLE_CODE());
		hdr.getSyoCtl().setNEXT_APRV_ROLE_CODE(slipDen.getNEXT_APRV_ROLE_CODE());
	}

	/**
	 * 指示画面[添付表示]押下処理
	 */
	protected void btnAttachment_Click() {

		try {

			Slip slip = slipBooks.getSlip();

			List<SWK_ATTACH> attachments = (List<SWK_ATTACH>) request(SlipAttachmentManager.class, "get",
				slip.getCompanyCode(), slip.getSlipNo());

			if (attachments == null || attachments.isEmpty()) {
				attachments = new ArrayList<SWK_ATTACH>();
			}

			// 添付データ設定
			slip.getHeader().setAttachments(attachments);

			ctrl = createAttachListDialogCtrl();
			ctrl.setSlip(slip); // 伝票
			ctrl.show();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return 添付一覧ダイアログCtrl
	 */
	protected TAttachListDialogCtrl createAttachListDialogCtrl() {
		return new TAttachListDialogCtrl(field.pnlBody, getProgramInfo());
	}

	/**
	 * 指示画面[承認履歴]押下処理
	 */
	protected void btnApproveHistory_Click() {

		try {

			Slip slip = slipBooks.getSlip();

			ApproveHistoryCondition condition = new ApproveHistoryCondition();
			condition.setCompanyCode(slip.getCompanyCode());
			condition.setSlipNo(slip.getSlipNo());

			List<ApproveHistory> historyList = (List<ApproveHistory>) request(ApproveHistoryManager.class, "get",
				condition);

			if (historyList == null || historyList.isEmpty()) {
				showMessage("I00022");
				return;
			}

			TApproveHistoryDialog dialog = new TApproveHistoryDialog(field, true);
			dialog.setData(historyList);
			dialog.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[プレビュー]押下処理
	 */
	protected void btnPreview_Click() {

		try {

			Slip slip = slipBooks.getSlip();

			List<String> slipNoList = new ArrayList<String>();
			slipNoList.add(slip.getSlipNo());

			// データ抽出
			byte[] data = null;

			// GROUP会計を使う会社の場合、付替先も抽出する
			AccountConfig ac = getCompany().getAccountConfig();
			if (ac.isUseGroupAccount()) {
				data = (byte[]) request(getManagerClass(), field.groupMethodName, slipNoList);

				// GROUP会計を使わない会社の場合、自社のみ
			} else {
				data = (byte[]) request(getManagerClass(), field.methodName, slip.getCompanyCode(), slipNoList);
			}

			String fileName = slip.getHeader().getSWK_DEN_SYU_NAME();
			printOutPDF(data, fileName);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 取消ボタン押下
	 */
	protected void btnReturn_Click() {
		field.setVisible(false);
		field.dispose();
		fireChangedSlipHeaderListener();
	}

	/**
	 * 伝票ヘッダ変更リスナーを設定
	 */
	protected void fireChangedSlipHeaderListener() {

		if (ctrl != null) {
			SWK_HDR hdr = slipBooks.getSlip().getHeader();
			hdr.setExistAttachment(ctrl.getView().tbl.getRowCount() != 0);

			if (getCallBackListenerList() != null && !getCallBackListenerList().isEmpty()) {
				for (TDrillDownCallBackListener listener : getCallBackListenerList()) {
					listener.changedSlipHeader(hdr);
				}
			}
		}
	}

	/**
	 * 伝票状態変更リスナーを設定
	 * 
	 * @param slipDen
	 * @param n
	 */
	protected void fireUpateSlipStateListener(SlipDen slipDen, int n) {
		if (getSlipStateListenerList() != null && !getSlipStateListenerList().isEmpty()) {
			for (TDrillDownSlipStateListener listener : getSlipStateListenerList()) {
				listener.updateSlipState(slipDen, n);
			}
		}

	}

	/**
	 * 指定された伝票 に紐付く伝票を表示する
	 * 
	 * @param books 伝票
	 * @throws Exception
	 */
	public void show(SlipBooks books) throws Exception {
		show(books, true);
	}

	/**
	 * 指定された伝票 に紐付く伝票を表示する
	 * 
	 * @param books 伝票
	 * @param isInitHeader true:ヘッダー描画
	 * @throws Exception
	 */
	public void show(SlipBooks books, boolean isInitHeader) throws Exception {

		if (books == null) {
			// 指定のデータは既に削除されています。
			throw new TException("I00167");
		}

		// 伝票セット
		slipBooks = books;

		if (isInitHeader) {
			// ヘッダーフィールド初期化
			initSlipHeader(slipBooks);
		}

		// 付替会社のドリルダウン機能
		if (isUseGroupCompanyDrillDown && slipBooksList != null) {
			// 現在表示中帳簿により左ボタン、右ボタンの制御

			int index = slipBooksList.indexOf(slipBooks);

			field.btnPrevSlip.setEnabled(index > 0);
			field.btnNextSlip.setEnabled(index < slipBooksList.size() - 1);
		}

		// 台帳初期化
		initBook(slipBooks);

		// 伝票データセット
		setSlip(getSlip());

		// 表示
		field.setVisible(true);

		// ボタン押下制御
		SWK_HDR swk_hdr = slipBooks.getSlip().getHeader();
		switchApprovalBtnsEnabled(swk_hdr.getSWK_UPD_KBN());

	}

	/**
	 * 指定された会社コード、伝票番号に紐付く伝票を抽出し表示する
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNo) throws Exception {

		SlipBooks books = null;

		// 付替会社のドリルダウン機能
		if (isUseGroupCompanyDrillDown) {
			slipBooksList = getSlipBooksList(companyCode, slipNo);

			if (slipBooksList != null && !slipBooksList.isEmpty()) {

				// 初期値
				books = slipBooksList.get(0);

				// 会社コードと同じの伝票を表示する
				for (SlipBooks bean : slipBooksList) {
					if (bean.getSlip() != null && companyCode.equals(bean.getSlip().getCompanyCode())) {
						books = bean;
						break;
					}
				}
			}

		} else {
			// 通常モード
			books = getSlipBooks(companyCode, slipNo);
		}

		show(books);
	}

	/**
	 * 指定された会社コード、伝票番号,row番号に紐付く伝票を抽出し表示する
	 * 
	 * @param companyCode
	 * @param slipNO
	 * @param i
	 * @throws Exception
	 */
	public void show(String companyCode, String slipNO, int i) throws Exception {
		show(companyCode, slipNO);
		row = i;
	}

	/**
	 * 修正、複写ボタン押下不可 flg
	 * 
	 * @param flg false 押下不可
	 */
	public void setBtnPush(boolean flg) {

		field.btnModify.setEnabled(flg);
		field.btnCopy.setEnabled(flg);
	}

	/**
	 * 台帳の初期化
	 * 
	 * @param books
	 */
	protected void initBook(SlipBooks books) {
		if (books == null) {
			return;
		}

		Slip slip = books.getOwnBookSlip();
		// 自国伝票がなければ、IFRS帳簿を表示
		if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
			slip = books.getIFRSBookSlip();
			if (slip != null && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
				field.cmbBookComboBox.selectAccountBook(AccountBook.IFRS);
			}
		}

		// 複数外貨合計表示
		int count = 2;
		try {
			count = Util.avoidNullAsInt(ClientConfig.getProperty("trans.slip.max.foreign.currency.count"));
		} catch (Throwable e) {
			// 処理なし
			count = 2;
		}

		if (count > 2) {
			// 可能の個数まで表示する
			for (int i = 2; i < count; i++) {
				if (i >= field.foreignList.size()) {
					break;
				}
				field.foreignList.get(i).setVisible(true);
			}

		}

	}

	/**
	 * ヘッダーの初期化
	 * 
	 * @param books
	 */
	protected void initSlipHeader(SlipBooks books) {

		Slip slip = books.getOwnBookSlip();
		TDrillDownHeader header = getDrillDownHeader(slip);

		// 付替会社のドリルダウン機能
		if (isUseGroupCompanyDrillDown) {
			header.txCompany.setVisible(true);
		}

		field.setHeader(header);

		if (!isUseTag) {
			// 無効の場合、付箋機能 非表示
			field.ctrlDrillDownHeader.btnTag.setVisible(false);
			field.ctrlDrillDownHeader.ctrlTag1.setVisible(false);
			field.ctrlDrillDownHeader.ctrlTag2.setVisible(false);
		}
		if (!getCompany().getAccountConfig().isUseWorkflowApprove()) {
			field.ctrlDrillDownHeader.ctrlAprvGroup.setVisible(false);
		}

		// イベント定義
		initFieldEvent();

	}

	/**
	 * フィールドのイベント定義
	 */
	protected void initFieldEvent() {
		// 付箋機能保存の処理
		field.ctrlDrillDownHeader.btnTag.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnTag_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 付箋保存ボタン
	 */
	protected void btnTag_Click() {

		try {
			// 保存＆エンティティセット
			// 付箋機能
			List<SWK_TAG> list = new ArrayList<SWK_TAG>();
			if (field.ctrlDrillDownHeader.ctrlTag1.getEntity() != null) {
				Tag bean = field.ctrlDrillDownHeader.ctrlTag1.getEntity();
				SWK_TAG tag = new SWK_TAG();
				tag.setKAI_CODE(getCompanyCode());
				tag.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
				tag.setSEQ(1);
				tag.setTAG_CODE(bean.getCode());
				tag.setTAG_COLOR(bean.getColor());
				tag.setTAG_TITLE(field.ctrlDrillDownHeader.ctrlTag1.getName()); // 画面値を使用
				list.add(tag);
			}
			if (field.ctrlDrillDownHeader.ctrlTag2.getEntity() != null) {
				Tag bean = field.ctrlDrillDownHeader.ctrlTag2.getEntity();
				SWK_TAG tag = new SWK_TAG();
				tag.setKAI_CODE(getCompanyCode());
				tag.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
				tag.setSEQ(2);
				tag.setTAG_CODE(bean.getCode());
				tag.setTAG_COLOR(bean.getColor());
				tag.setTAG_TITLE(field.ctrlDrillDownHeader.ctrlTag2.getName()); // 画面値を使用
				list.add(tag);
			}
			slipBooks.getSlip().getHeader().setSwkTags(list);
			request(SlipTagManager.class, "entry", slipBooks.getSlip());

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面で指定された台帳、表示通貨に紐付く伝票を返す
	 * 
	 * @return 指示画面で指定された台帳、表示通貨に紐付く伝票
	 */
	protected Slip getSlip() {

		if (slipBooks == null) {
			return null;
		}

		if (AccountBook.OWN == field.cmbBookComboBox.getAccountBook()) {
			return slipBooks.getOwnBookSlip();
		} else if (AccountBook.IFRS == field.cmbBookComboBox.getAccountBook()) {
			if (CurrencyType.KEY == field.cmbBookCurrencyComboBox.getCurrencyType()) {
				return slipBooks.getIFRSBookSlip();
			}
			return slipBooks.getIFRSFuncSlip();
		}

		return null;

	}

	/**
	 * 伝票情報を指示画面にセットする
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	protected void setSlip(Slip slip) throws TException {

		// ヘッダーのセット
		field.ctrlDrillDownHeader.setSlipHeader(slip);

		// 明細のセット
		field.tblSlipDetail.removeRow();
		for (SWK_DTL swk : slip.getDetails()) {
			field.tblSlipDetail.addRow(toTableList(slip, swk));
		}

		// フッターのセット
		field.txDebitSum.setValue(null);
		field.txDebitInSum1.setValue(null);
		field.txDebitInSum2.setValue(null);
		field.txCreditSum.setValue(null);
		field.txCreditInSum1.setValue(null);
		field.txCreditInSum2.setValue(null);
		field.txCur1.setText(null);
		field.txCur2.setText(null);

		if (slip.getDetails() == null || slip.getDetails().isEmpty()) {
			return;
		}

		// 借方計
		if (slip.getDebitKeyAmount() != null) {
			field.txDebitSum.setValue(
				NumberFormatUtil.formatNumber(slip.getDebitKeyAmount(), slip.getBaseCurrency().getDecimalPoint()));
		}
		// 借方外貨計1
		if (slip.getForeignCurrencyAt(0) != null) {
			field.txDebitInSum1.setValue(NumberFormatUtil.formatNumber(slip.getDebitForeignAmountAt(0),
				slip.getForeignCurrencyAt(0).getDecimalPoint()));
			field.txCur1.setText(slip.getForeignCurrencyAt(0).getCode());
		}
		// 借方外貨計2
		if (slip.getForeignCurrencyAt(1) != null) {
			field.txDebitInSum2.setValue(NumberFormatUtil.formatNumber(slip.getDebitForeignAmountAt(1),
				slip.getForeignCurrencyAt(1).getDecimalPoint()));
			field.txCur2.setText(slip.getForeignCurrencyAt(1).getCode());
		}

		// 貸方計
		if (slip.getCreditKeyAmount() != null) {
			field.txCreditSum.setValue(
				NumberFormatUtil.formatNumber(slip.getCreditKeyAmount(), slip.getBaseCurrency().getDecimalPoint()));
		}
		// 貸方外貨計1
		if (slip.getForeignCurrencyAt(0) != null) {
			field.txCreditInSum1.setValue(NumberFormatUtil.formatNumber(slip.getCreditForeignAmountAt(0),
				slip.getForeignCurrencyAt(0).getDecimalPoint()));
		}
		// 貸方外貨計2
		if (slip.getForeignCurrencyAt(1) != null) {
			field.txCreditInSum2.setValue(NumberFormatUtil.formatNumber(slip.getCreditForeignAmountAt(1),
				slip.getForeignCurrencyAt(1).getDecimalPoint()));
		}

		// 複数外貨合計の表示
		for (int i = 2; i < field.foreignList.size(); i++) {
			TDrillDownForeignField foreignField = field.foreignList.get(i);

			if (foreignField.isVisible()) {
				Currency foreignCurrency = slip.getForeignCurrencyAt(i);
				if (foreignCurrency != null) {
					int digit = foreignCurrency.getDecimalPoint();
					String debit = NumberFormatUtil.formatNumber(slip.getDebitForeignAmountAt(i), digit);
					String credit = NumberFormatUtil.formatNumber(slip.getCreditForeignAmountAt(i), digit);

					foreignField.txDebitInSum.setValue(debit);
					foreignField.txCreditInSum.setValue(credit);
					foreignField.txCur.setText(foreignCurrency.getCode());
				}
			}
		}

	}

	/**
	 * 明細行を一覧にセットする形式に変換する
	 * 
	 * @param slip
	 * @param swk
	 * @return 一覧データ
	 * @throws TException
	 */
	protected List<Object> toTableList(Slip slip, SWK_DTL swk) throws TException {

		List<Object> list = new ArrayList<Object>();

		// 科目
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_KMK_CODE()) + " " + Util.avoidNull(swk.getSWK_KMK_NAME_S()),
					Util.avoidNull(swk.getSWK_HKM_CODE()) + " " + Util.avoidNull(swk.getSWK_HKM_NAME_S()),
					Util.avoidNull(swk.getSWK_UKM_CODE()) + " " + Util.avoidNull(swk.getSWK_UKM_NAME_S()) }));

		// 金額
		String amount = StringUtil.getHtmlString(SwingConstants.RIGHT, new String[] {
				FormatUtil.foreingAmountFormat(slip.getBaseCurrency().getCode(), swk.getSWK_CUR_CODE(),
					swk.getSWK_IN_KIN(), swk.getCUR_DEC_KETA()),
				FormatUtil.rateFormat(slip.getBaseCurrency().getCode(), swk.getSWK_CUR_CODE(), swk.getSWK_CUR_RATE()),
				NumberFormatUtil.formatNumber(swk.getSWK_KIN(), slip.getBaseCurrency().getDecimalPoint()) });

		// 借方
		if (swk.isDR()) {
			list.add(amount);
			list.add(null);

			// 貸方
		} else {
			list.add(null);
			list.add(amount);
		}

		// 発生日
		String occurDate = DateUtil.toYMDString(swk.getHAS_DATE());
		if (isAllowOccurDateBlank()) {
			if (!swk.isUseItemOccurDate()) {
				// 科目が発生日未使用の場合、発生日はブランクにする
				occurDate = "";
			}
		}

		// 通貨、発生日、税
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { FormatUtil.currencyFormat(slip.getBaseCurrency().getCode(), swk.getSWK_CUR_CODE()),
					occurDate,
					Util.avoidNull(swk.getSWK_ZEI_CODE()) + " " + Util.avoidNull(swk.getSWK_ZEI_NAME_S()) }));

		// 行摘要
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			StringUtil
				.getParagraphString(
					Util.avoidNull(swk.getSWK_GYO_TEK_CODE()) + " " + Util.avoidNull(swk.getSWK_GYO_TEK()), 40, 3)
				.toArray()));

		// 計上部門、取引先、社員
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_DEP_CODE()) + " " + Util.avoidNull(swk.getSWK_DEP_NAME_S()),
					Util.avoidNull(swk.getSWK_TRI_CODE()) + " " + Util.avoidNull(swk.getSWK_TRI_NAME_S()),
					Util.avoidNull(swk.getSWK_EMP_CODE()) + " " + Util.avoidNull(swk.getSWK_EMP_NAME_S()) }));

		// 管理1～3
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_KNR_CODE_1()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_1()),
					Util.avoidNull(swk.getSWK_KNR_CODE_2()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_2()),
					Util.avoidNull(swk.getSWK_KNR_CODE_3()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_3()) }));

		// 管理4～6
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT,
			new String[] { Util.avoidNull(swk.getSWK_KNR_CODE_4()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_4()),
					Util.avoidNull(swk.getSWK_KNR_CODE_5()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_5()),
					Util.avoidNull(swk.getSWK_KNR_CODE_6()) + " " + Util.avoidNull(swk.getSWK_KNR_NAME_S_6()) }));

		// 非会計明細1～3
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { Util.avoidNull(swk.getSWK_HM_1()),
				Util.avoidNull(swk.getSWK_HM_2()), Util.avoidNull(swk.getSWK_HM_3()) }));

		// BS勘定マーク
		String bsMark = "";
		String bsSlipNo = "";
		if (isUseBS) {
			if (isShowAllBS) {
				// 全て記載の場合、消込元・先を必ず記載
				if (swk.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
					bsMark = getWord("C03191"); // ＊ 元
				} else if (swk.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.FORWARD) {
					bsMark = getWord("C11766"); // ※
				}
				bsSlipNo = Util.avoidNull(swk.getSWK_SOUSAI_DEN_NO()); // 消込伝票番号
			} else {
				// 従来処理 BS消込は行番号まで記載のもののみ明確に表記
				if (swk.getSWK_SOUSAI_GYO_NO() != null) {
					if (swk.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
						bsMark = getWord("C03191"); // ＊ 元
					} else {
						bsMark = getWord("C11766"); // ※
					}
					bsSlipNo = swk.getSWK_SOUSAI_DEN_NO(); // 消込伝票番号
				}
			}
			if (Util.isNullOrEmpty(bsSlipNo)) {
				bsSlipNo = Util.avoidNull(swk.getSWK_KESI_DEN_NO()); // 消込番号
			}
		}
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { bsMark, bsSlipNo }));

		int aparKbn = swk.getSWK_APAR_KESI_KBN();

		// APマーク
		String apMark = "";
		String apSlipNo = "";
		if (!isNotUseAP && (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AP_FORWARD)) {

			if (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE) {
				apMark = getWord("C03191"); // ＊ 元
			} else {
				apMark = getWord("C11766"); // ※
			}
			apSlipNo = swk.getSWK_APAR_DEN_NO(); // 消込伝票番号
		}
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { apMark, apSlipNo }));

		// ARマーク
		String arMark = "";
		String arSlipNo = "";
		if (!isNotUseAR && (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AR_FORWARD)) {

			if (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE) {
				arMark = getWord("C03191"); // ＊ 元
			} else {
				arMark = getWord("C11766"); // ※
			}
			arSlipNo = swk.getSWK_APAR_DEN_NO(); // 消込伝票番号
		}
		list.add(StringUtil.getHtmlString(SwingConstants.LEFT, new String[] { arMark, arSlipNo }));
		return list;
	}

	/**
	 * @return true:発生日ブランク可能
	 */
	protected boolean isAllowOccurDateBlank() {
		return allowOccurDateBlank && !getCompany().getAccountConfig().isUseIfrs();
	}

	/**
	 * ドリルダウンヘッダーを返す
	 * 
	 * @param slip
	 * @return 伝票に応じたドリルダウンヘッダー
	 */
	public TDrillDownHeader getDrillDownHeader(Slip slip) {

		if (SlipKind.PURCHASE == slip.getSlipKind()) {
			return new TDrillDownHeaderAP(getLoginLanguage());
		} else if (SlipKind.SALES == slip.getSlipKind()) {
			return new TDrillDownHeaderAR(getLoginLanguage());
		} else if (SlipKind.EMPLOYEE == slip.getSlipKind()) {
			return new TDrillDownHeaderEP(getLoginLanguage());
		} else {
			return new TDrillDownHeader(getLoginLanguage());
		}

	}

	/**
	 * 指定の会社コード、伝票番号に紐付く伝票を返す
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return 伝票
	 * @throws Exception
	 */
	protected SlipBooks getSlipBooks(String companyCode, String slipNo) throws Exception {
		List<SlipBooks> list = getSlipBooksList(companyCode, slipNo);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 指定の会社コード、伝票番号に紐付く伝票を返す <br>
	 * 付替会社の伝票取得
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return 伝票
	 * @throws Exception
	 */
	protected List<SlipBooks> getSlipBooksList(String companyCode, String slipNo) throws Exception {

		SlipCondition condition = createSlipCondition();

		// 付替会社のドリルダウン機能以外、会社コード設定
		if (!isUseGroupCompanyDrillDown) {
			// 会社コード
			condition.setCompanyCode(companyCode);
		}
		// 伝票番号
		condition.setSlipNo(slipNo);
		condition.setSearchWorkFlow(true);
		List<SlipBooks> list = (List<SlipBooks>) request(getManagerClass(), "getSlipBooks", condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list;

	}

	/**
	 * @return 伝票検索条件
	 */
	protected SlipCondition createSlipCondition() {
		return new SlipCondition();
	}

	/**
	 * @return マネージャの取得
	 */
	protected Class getManagerClass() {
		return field.managerClass;
	}

	/**
	 * @return callBackListenerを戻します。
	 */
	public List<TDrillDownCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListener callBackListenerを追加する
	 */
	public void addCallBackListener(TDrillDownCallBackListener callBackListener) {

		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TDrillDownCallBackListener>();
		}
		callBackListenerList.add(callBackListener);
	}

	/**
	 * @return slipStateListenersを戻します。
	 */
	public List<TDrillDownSlipStateListener> getSlipStateListenerList() {
		return slipStateListenerList;
	}

	/**
	 * @param slipStateListener slipStateListenerを追加する
	 */
	public void addSlipStateListener(TDrillDownSlipStateListener slipStateListener) {
		if (slipStateListenerList == null) {
			slipStateListenerList = new ArrayList<TDrillDownSlipStateListener>();
		}
		slipStateListenerList.add(slipStateListener);
	}

	/**
	 * 修正ボタン押下
	 */
	protected void btnModify_Click() {
		// 修正可能チェック
		try {
			Slip slip = slipBooks.getSlip().clone();

			DEN_SYU_MST denSyuMst = getDEN_SYU_MST(slip.getSlipType());

			if (!isCheck(slip, denSyuMst, true)) {
				return;
			}

			// 伝票画面表示
			SWK_HDR hdr = slip.getHeader();
			// ブランクだったら明示的にセット
			if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {
				hdr.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
			}
			startSlipEntryChange(hdr, denSyuMst, true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 複写ボタン押下
	 */
	protected void btnCopy_Click() {
		// 複写可能チェック
		try {
			Slip slip = slipBooks.getSlip().clone();

			DEN_SYU_MST denSyuMst = getDEN_SYU_MST(slip.getSlipType());

			if (!isCheck(slip, denSyuMst, false)) {
				return;
			}

			// 伝票画面表示
			SWK_HDR hdr = slip.getHeader();
			// ブランクだったら明示的にセット
			if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {
				hdr.setSWK_DEN_NO(field.ctrlDrillDownHeader.txSlipNo.getValue());
			}
			startSlipEntryChange(hdr, denSyuMst, false);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 伝票編集チェック
	 * 
	 * @param slip Slip
	 * @param denSyuMst
	 * @param isModify
	 * @return boolean
	 */
	protected boolean isCheck(Slip slip, DEN_SYU_MST denSyuMst, boolean isModify) {

		// 該当伝票の伝票種別マスタ．仕訳インターフェース区分 = 対象
		if (Util.avoidNull(denSyuMst.getTA_SYS_KBN()).equals("1")) {
			// 指定の伝票は修正できません。
			String msg = "IAC006";
			if (!isModify) {
				// 複写の場合：指定の伝票は複写できません。
				msg = "W00124";
			}
			showMessage(msg);
			return false;
		}

		if (getTargetPrgCode(denSyuMst) == null) {
			// 指定の伝票は修正できません。
			String msg = "IAC006";
			if (!isModify) {
				// 複写の場合：指定の伝票は複写できません。
				msg = "W00124";
			}
			showMessage(msg);
			return false;
		}

		if (isModify) {
			SlipState slipState = slip.getHeader().getSlipState();
			if (slipState == SlipState.APPROVE || slipState == SlipState.FIELD_APPROVE) {
				// 該当伝票の伝票の更新区分が 現場承認 または 経理承認
				// 指定の伝票は承認されています。
				showMessage("W00127");
				return false;

			} else if (slipState == SlipState.UPDATE) {
				// 該当伝票の伝票の更新区分が 更新
				// 指定の伝票は修正できません。
				showMessage("W00126");
				return false;

			}

		}
		SWK_HDR hdr = slip.getHeader();
		// 付替伝票はNG
		String cmpCode = null;
		for (SlipBooks slipBook : slipBooksList) {
			int tuke = slipBook.getSlip().getHeader().getSWK_TUKE_KBN();
			if (tuke == 0) {
				cmpCode = slipBook.getSlip().getCompanyCode();
				break;
			}
		}
		// if (hdr.getSWK_TUKE_KBN() != 0 && !Util.equals(cmpCode, hdr.getKAI_CODE())
		// && !Util.equals(getCompanyCode(), hdr.getKAI_CODE())) {
		if (!(hdr.getSWK_TUKE_KBN() == 0 && Util.equals(getCompanyCode(), hdr.getKAI_CODE()))) {
			// 指定の伝票は作成されたログイン会社が異なる為、修正できません。 伝票作成会社：{0}
			String msg = "I01073";
			if (!isModify) {
				// 指定の伝票は作成されたログイン会社が異なる為、複写できません。 伝票作成会社：{0}
				msg = "I01074";
			}
			showMessage(msg, cmpCode);
			return false;
		}

		// 確認の為存在チェック処理
		try {
			request(SlipManager.class, "checkSlipInfo", hdr.getKAI_CODE(), hdr.getSWK_DEN_NO(), hdr.getSWK_UPD_CNT());
		} catch (TException e) {
			errorHandler(e);
		}

		return true;
	}

	/**
	 * 伝票種別マスタ取得
	 * 
	 * @param denSyuCode
	 * @return DEN_SYU_MST
	 * @throws TException
	 */
	protected DEN_SYU_MST getDEN_SYU_MST(String denSyuCode) throws TException {

		return (DEN_SYU_MST) request(DEN_SYU_MSTDao.class, "getDEN_SYU_MSTByKaicodeDensyucode", getCompanyCode(),
			denSyuCode);

	}

	/**
	 * 伝票画面表示
	 * 
	 * @param hdr
	 * @param denSyuMst
	 * @param isModify
	 * @throws Exception
	 */
	protected void startSlipEntryChange(SWK_HDR hdr, DEN_SYU_MST denSyuMst, boolean isModify) throws Exception {
		TMainCtrl ins = TMainCtrl.getInstance();

		Program mgrProgram = null;

		if (prgGroupList == null) {
			if (ins.isUseMenuMaster) {
				prgGroupList = ins.getMenuPrograms(TLoginInfo.getCompany(), TLoginInfo.getUser());
			} else {
				prgGroupList = ins.getMenuProgramsOld(TLoginInfo.getCompany(), TLoginInfo.getUser());
			}
		}

		String prgCode = getTargetPrgCode(denSyuMst);
		Class pnlCtrl = (Class) request(SlipManager.class, "getSlipPanel", prgCode, denSyuMst.getDEN_SYU_CODE());
		if (pnlCtrl == null) {
			// 指定の伝票は修正できません。
			String msg = "IAC006";
			if (!isModify) {
				// 複写の場合：指定の伝票は複写できません。
				msg = "W00124";
			}
			showMessage(msg);
			return;
		}

		if (prgGroupList != null) {
			for (SystemizedProgram prg : prgGroupList) {
				if (prg.getMenuDisp() != null) {
					for (MenuDisp menu : prg.getMenuDisp()) {
						mgrProgram = getManagerProgram(menu.getProgram(), pnlCtrl);
						if (mgrProgram != null) {
							break;
						}
					}
				} else if (prg.getPrograms() != null) {
					for (Program program : prg.getPrograms()) {
						mgrProgram = getManagerProgram(program, pnlCtrl);
						if (mgrProgram != null) {
							break;
						}
					}
				}

				if (mgrProgram != null) {
					break;
				}
			}
		}

		if (mgrProgram != null) {
			// メニュー起動
			ins.startProgram(mgrProgram.getCode(), mgrProgram.getName(), mgrProgram.getLoadClassName(), true);
		}

		TSlipPanelCtrl denCtrl = getManagerCtrl(ins, pnlCtrl);

		if (denCtrl == null) {
			denCtrl = getManagerCtrl(ins, pnlCtrl);
		}
		if (denCtrl != null) {
			TSlipPanel panel = (TSlipPanel) denCtrl.getPanel();

			// 検索処理
			denCtrl.searchSlipAddResultNoneDialog(isModify, hdr, 0);

			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); // カーソル戻す
			panel.requestFocus(); // TODO

		}
	}

	/**
	 * プログラムコード判定
	 * 
	 * @param denSyuMst
	 * @return prgCode
	 */
	protected String getTargetPrgCode(DEN_SYU_MST denSyuMst) {
		String dataKbn = denSyuMst.getDATA_KBN();
		String prgCode = null;
		if (dataKbn.equals("11")) {
			prgCode = "GL0010";
		} else if (dataKbn.equals("12")) {
			prgCode = "GL0020";
		} else if (dataKbn.equals("13")) {
			prgCode = "GL0030";
		} else if (dataKbn.equals("14")) {
			prgCode = "GL6010";
		} else if (dataKbn.equals("23")) {
			prgCode = "AP0010";
		} else if (dataKbn.equals("31")) {
			prgCode = "AR0010";
		}
		return prgCode;

	}

	/**
	 * @param prg
	 * @param pnlCtrl
	 * @return プログラム
	 */
	protected static Program getManagerProgram(Program prg, Class pnlCtrl) {

		if (prg == null) {
			return null;
		}
		String ldName = Util.avoidNull(prg.getLoadClassName());
		if (equals(ldName, pnlCtrl.getName())) {
			return prg;
		}

		if (!Util.isNullOrEmpty(ldName)) {
			try {
				Class cls = Class.forName(ldName);
				if (equals(cls.getSuperclass().getName(), pnlCtrl.getName())) {
					// カスタマイズチェック
					return prg;
				}
			} catch (ClassNotFoundException e) {
				// エラーなし
			}
		}

		return null;
	}

	/**
	 * @param ins
	 * @param pnlCtrl
	 * @return MANAGER CTRL
	 */
	protected TSlipPanelCtrl getManagerCtrl(TMainCtrl ins, Class pnlCtrl) {

		if (ins.selectedProgram != null) {
			for (TAppletClientBase denCtrl : ins.selectedProgram.values()) {
				if (denCtrl instanceof TSlipPanelCtrl) {
					String ldName = Util.avoidNull(denCtrl.getClass().getName());
					if (equals(ldName, pnlCtrl.getName())) {
						return (TSlipPanelCtrl) denCtrl;
					}
				}
			}
		}

		if (ins.frameProgram != null) {
			for (TPanelBusiness biz : ins.frameProgram.values()) {
				if (biz instanceof TSlipPanel) {
					String ldName = Util.avoidNull(biz.getClass().getName());
					if (equals(ldName, pnlCtrl.getName())) {
						return ((TSlipPanel) biz).getController();
					}
				}
			}
		}

		return null;
	}

	/**
	 * 文字列同一比較
	 * 
	 * @param a
	 * @param b
	 * @return true:同じ
	 */
	public static boolean equals(String a, String b) {
		return Util.avoidNullNT(a).equals(Util.avoidNullNT(b));
	}

	/**
	 * 仕訳ヘッダーに基づいて、SlipDenを作成
	 * 
	 * @param hdr
	 * @return SlipDen
	 */
	protected SlipDen getDen(SWK_HDR hdr) {
		SlipDen bean = new SlipDen();
		bean.setKAI_CODE(hdr.getKAI_CODE());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());
		bean.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
		bean.setSWK_BEFORE_DEN_NO(hdr.getSWK_BEFORE_DEN_NO());
		bean.setSWK_BEFORE_UPD_KBN(hdr.getSWK_BEFORE_UPD_KBN());
		bean.setSWK_DATA_KBN(hdr.getSWK_DATA_KBN());
		bean.setSWK_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());

		bean.setSWK_IRAI_EMP_CODE(hdr.getSWK_IRAI_EMP_CODE());
		bean.setSWK_TEK(hdr.getSWK_TEK());
		bean.setSWK_DEN_SYU(hdr.getSWK_DEN_SYU());
		bean.setSWK_CUR_CODE(hdr.getSWK_CUR_CODE());
		bean.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT());
		bean.setSWK_KSN_KBN(hdr.getSWK_KSN_KBN());

		return bean;

	}

	/**
	 * 承認クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getSlipManagerClass() {
		return SlipManager.class;
	}

	/**
	 * 承認関係ボタン押下可否を切替
	 * 
	 * @param updKbn 伝票更新区分
	 */
	protected void switchApprovalBtnsEnabled(int updKbn) {
		SlipState state = SlipState.getSlipState(updKbn);
		// 承認 : 更新/経理承認以外は押下可能
		boolean isApprovable = state != SlipState.UPDATE && state != SlipState.APPROVE;
		// 否認 : 更新以外は押下可能
		boolean isDeniable = state != SlipState.UPDATE;
		// 承認取消 : 更新/取消以外は押下可能
		boolean isCancellable = state != SlipState.UPDATE && state != SlipState.ENTRY;
		if (state == null) {
			// 更新区分が正常に取得できていない場合すべて押下不可
			isApprovable = false;
			isDeniable = false;
			isCancellable = false;
		}
		field.btnApprove.setEnabled(isApprovable);
		field.btnDeny.setEnabled(isDeniable);
		field.btnCancel.setEnabled(isCancellable);

	}

	/**
	 * 承認関係のメインボタンの表示を切替
	 * 
	 * @param flag
	 */
	public void setAprvBtnVisible(boolean flag) {
		field.btnApprove.setVisible(false);
		field.btnDeny.setVisible(false);
		field.btnCancel.setVisible(false);
		if (getCompany() == null || getCompany().getAccountConfig() == null) {
			// 会社設定が読み込めない場合非表示
			return;
		}
		AccountConfig ac = getCompany().getAccountConfig();
		if (ac.isUseWorkflowApprove()) {
			field.btnApprove.setVisible(flag);
			field.btnCancel.setVisible(flag);
			// ワークフロー承認の場合否認は非表示
			field.btnDeny.setVisible(false);
		} else {
			// TODO ワークフロー承認にのみ対応したサーバー処理のため、通常の場合は非表示
			// 実装完了時に修正
			field.btnApprove.setVisible(false);
			field.btnDeny.setVisible(false);
			field.btnCancel.setVisible(false);
		}
	}

}
