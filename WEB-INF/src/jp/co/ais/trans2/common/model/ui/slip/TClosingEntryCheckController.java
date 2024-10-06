package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;

/**
 * 決算仕訳チェックコントローラ
 */
public class TClosingEntryCheckController extends TController {

	/** コンポ */
	protected TClosingEntryCheck entryCheck;

	/** 決算段階が入力可能(チェック時) */
	protected boolean isEdit = false;

	/**
	 * コンストラクタ.
	 * 
	 * @param entryCheck
	 */
	public TClosingEntryCheckController(TClosingEntryCheck entryCheck) {
		this.entryCheck = entryCheck;

		addEvent();

		init();
	}

	/**
	 * イベント登録
	 */
	protected void addEvent() {
		entryCheck.chk.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				switchStage();
			}
		});

		entryCheck.ctrlSlipDate.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				switchStage();
			}
		});
	}

	/**
	 * 初期処理
	 */
	protected void init() {

		// 利用可/不可
		boolean isView = true;

		isView &= getCompany().getFiscalPeriod().getMaxSettlementStage() != 0;

		// ユーザが経理認証者は表示
		isView &= getUser().getUserAccountRole() == UserAccountRole.ACCOUNT;

		entryCheck.setVisible(isView);
	}

	/**
	 * 決算段階が入力可能かどうか(チェック時)
	 * 
	 * @param isEdit true:可能
	 */
	public void setEditMode(boolean isEdit) {
		this.isEdit = isEdit;

		switchStage();
	}

	/**
	 * 決算段階変更
	 */
	protected void switchStage() {

		// 入力可能
		entryCheck.num.setEditable(isEdit && entryCheck.chk.isSelected());

		if (!entryCheck.chk.isSelected()) {
			entryCheck.num.clear();
			return;
		}

		Date slipDate = entryCheck.ctrlSlipDate.getValue();

		if (slipDate == null) {
			return; // NULL無いけど念のためにチェック
		}

		FiscalPeriod period = getCompany().getFiscalPeriod();
		Date closedDate = period.getLastDateOfClosedPeriod();// 締め月
		int stage = period.getSettlementStage(); // 決算段階
		int max = period.getMaxSettlementStage();

		// 締め月と同じなら決算段階
		if (DateUtil.getYear(closedDate) == DateUtil.getYear(slipDate)
			&& DateUtil.getMonth(closedDate) == DateUtil.getMonth(slipDate)) {

			if (max < stage + 1) {
				// もうMAXで締められている
				entryCheck.num.setNumber(1);

			} else {
				entryCheck.num.setNumber(stage + 1);
			}

		} else {
			entryCheck.num.setNumber(1);
		}
	}

	/**
	 * 決算段階の再設定
	 */
	public void resetStage() {
		switchStage();
	}
}
