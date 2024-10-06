package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * パターン入力コントローラ
 */
public abstract class TSlipPatternPanelCtrl extends TSlipPanelCtrl {

	/**
	 * 画面ヘッダの初期設定<br>
	 * 日付系なし
	 */
	@Override
	protected void initHeaderView() {
		// 新規モード
		switchNew();

		// 伝票摘要
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRemark(true);
		mainView.ctrlSlipRemark.getSearchCondition().setSlipRowRemark(false);
		mainView.ctrlSlipRemark.getSearchCondition().setDataType(slipType.getDataType()); // データ区分
	}
	
	/**
	 * invoice使用するかどうか(パターンはfalse)
	 */
	@Override
	protected void initInvoiceFlg() {

		isInvoice = false;
	}

	/**
	 * 画面明細の初期設定<br>
	 * 日付系なし
	 */
	@Override
	protected void initDetailView() {
		// プログラム情報
		mainView.ctrlDetail.getController().setProgramInfo(getProgramInfo());

		// スプレッド状態保存キー
		mainView.ctrlDetail.setTableKeyName(getTableKeyName());

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
	 * 値クリア<br>
	 * 日付系なし
	 */
	@Override
	protected void clearComponents() {
		// ヘッダ
		mainView.ctrlSlipNo.clear(); // 伝票番号
		mainView.ctrlEvidenceNo.clear(); // 証憑番号
		mainView.ctrlSlipRemark.clear(); // 伝票摘要
		mainView.ctrlPrintStop.clear(); // 伝票印刷停止
		mainView.ctrlCloseEntry.clear(); // 決算仕訳

		// 明細
		mainView.ctrlDetail.init();

		// パターン番号入力可
		mainView.ctrlSlipNo.setEditable(true);
	}

	/**
	 * 初期フォーカス
	 */
	@Override
	protected void requestFocusFirst() {
		mainView.ctrlCloseEntry.chk.requestFocus();
	}

	/**
	 * 伝票検索と反映<br>
	 * 呼び出すのはパターン
	 * 
	 * @param isModify true:修正 false:複写
	 * @return true:成功
	 */
	@Override
	protected boolean searchSlipAddResult(boolean isModify) {

		try {
			// 検索
			TSlipPatternSearchCtrl ctrl = createPatternSearchCtrl();

			if (!isModify) {
				// 複写モード
				ctrl.switchSelfOnly(); // 自身のデータのみ呼び出し
				ctrl.setIncludeLocked(true); // 排他含むか
			}

			if (ctrl.show() != TSlipSearchCtrl.OK_OPTION) {
				mainView.btnPattern.requestFocus();
				return false;
			}

			// 一旦クリア
			clearView();

			// 反映
			SWK_HDR hdr = ctrl.getSelectedRow();

			// 伝票構築
			slip = (Slip) request(getManagerClass(), "getPatternSlip", hdr, isModify);

			// 画面反映
			dispatch();

			if (isModify) {
				// 修正
				switchModify();

			} else {
				// 複写(新規扱い)
				slip = null;
				mainView.ctrlSlipNo.clear();

				switchNew();
			}

			mainView.ctrlSlipNo.setEditable(!isModify);

			return true;

		} catch (Exception ex) {
			errorHandler(ex);
			return false;
		}
	}

	/**
	 * 伝票確定
	 */
	@Override
	public void doEntry() {
		try {
			// 入力チェック
			if (!checkInput()) {
				return;
			}

			if (!showConfirmMessage("Q00004")) {// 確定しますか？
				return;
			}

			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			// 伝票に反映
			reflectSlip();

			// 登録
			request(getManagerClass(), "entryPattern", slip);

			clearView();

			showMessage("I00013");// 正常に処理が実行されました。

		} catch (Exception ex) {
			errorHandler(ex);

		} finally {
			mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * 伝票削除
	 */
	@Override
	public void doDelete() {
		try {
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除
			request(getManagerClass(), "deletePattern", slip);

			// クリア
			clearView();

			showMessage("I00013");// 正常に処理が実行されました。

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 伝票日付関連のチェック
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkSlipDate() {
		// なし
		return true;
	}

	/**
	 * ヘッダー項目のチェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@Override
	protected boolean checkHeaderInput() throws TException {
		// 摘要コード入力チェック
		if (!checkInputBlank(mainView.ctrlSlipNo.getField(), "C00987")) {// パターン番号
			return false;
		}

		// 新規の場合、コード存在チェック
		if (!mainView.isModifyMode()) {
			Boolean exists = (Boolean) request(getManagerClass(), "existsPattern", mainView.ctrlSlipNo.getValue());
			if (exists) {
				showMessage("I00138");// 指定のパターン番号は既に存在します。
				mainView.ctrlSlipNo.requestFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * 明細項目のチェック
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkDetailInput() {
		// なし
		return true;
	}

	/**
	 * 画面入力の反映(ヘッダ)
	 * 
	 * @param hdr ヘッダ
	 */
	@Override
	protected void reflectHeader(SWK_HDR hdr) {
		super.reflectHeader(hdr);

		// 日付が無いので決算伝票の場合は自分で1を
		if (mainView.ctrlCloseEntry.isSelected()) {
			hdr.setSWK_KSN_KBN(1);
		}
	}

	/**
	 * 為替差損益行追加
	 * 
	 * @param slip_ 対象
	 * @return 為替差損益行追加後
	 */
	@Override
	protected Slip addLossOrProfit(Slip slip_) {
		// 差損益行は追加しない
		return slip_;
	}

	/**
	 * 印刷時の名称(不要)
	 * 
	 * @return 名称
	 */
	@Override
	protected String getPrintName() {
		return "";
	}

	/**
	 * パターン用
	 * 
	 * @see jp.co.ais.trans2.model.security.TExclusive#getExclusiveControlMethod()
	 */
	@Override
	public TExclusiveControlMethod getExclusiveControlMethod() {
		return new SlipPatternExclusiveControlMethod(this.getProgramInfo(), getSlipTypeNo());
	}

	/**
	 * 排他解除(全体)
	 */
	@Override
	protected void unlockAll() {
		try {
			// 排他解除
			request(getManagerClass(), "unlockPatternAll", slipType.getCode());

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 排他解除(個別)
	 */
	@Override
	protected void unlock() {
		try {
			if (slip != null && !Util.isNullOrEmpty(slip.getSlipNo())) {
				// 伝票指定の排他解除
				request(getManagerClass(), "unlockPattern", slip);

			} else {
				unlockAll();
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

}
