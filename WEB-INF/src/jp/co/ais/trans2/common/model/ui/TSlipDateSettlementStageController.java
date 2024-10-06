package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 伝票日付と決算段階コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSlipDateSettlementStageController extends TController {

	/** コンポーネント */
	protected TSlipDateSettlementStage field;

	/** 対象会社 */
	protected Company company = null;

	/** 起票ユーザー */
	protected User user = null;

	/**
	 * @param field
	 */
	public TSlipDateSettlementStageController(TSlipDateSettlementStage field) {
		this.field = field;
		init();
		refresh();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		company = getCompany();
		user = getUser();
	}

	/**
	 * リフレッシュ
	 */
	protected void refresh() {

		// 決算を使用しない場合、またはユーザーが経理担当ではない場合、決算段階は非表示
		FiscalPeriod fp = company.getFiscalPeriod();
		boolean isUseSettlement = (fp.getMaxSettlementStage() != 0);
		if (!user.isAccountant()) {
			isUseSettlement = false;
		}

		field.chkSettlementStage.setVisible(isUseSettlement);

	}

	/**
	 * 伝票作成チェック
	 * 
	 * @return true：正常、false：異常
	 */
	public boolean canCreateSlip() {

		// 伝票日付が未入力の場合エラー
		if (Util.isNullOrEmpty(field.slipDate.getValue())) {
			showMessage(field, "I00037", "C00599");// 伝票日付を入力してください。
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// 伝票日付が決算伝票入力可能月では無い場合エラー
		if (field.chkSettlementStage.isSelected() && !field.slipDate.isSettlementDate()) {
			showMessage(field, "I00045");// 決算仕訳は決算月の末日でのみ入力できます。
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// 既に締められている場合エラー
		if (field.slipDate.isClosed(company, field.chkSettlementStage.getStage())) {
			showMessage(field, "I00131");// 指定の伝票日付は締められています。
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// 裏で月次処理が行われた場合エラー
		if (field.chkSettlementStage.isSelected()
			&& field.slipDate.isClosed(company, field.chkSettlementStage.getStage())) {
			showMessage(field, "I00132");// 月次処理が行われた為、決算段階に変更があります。
			field.slipDate.requestTextFocus();
			field.chkSettlementStage.setSelected(false);
			return false;
		}

		// 決算仕訳チェック
		if (field.chkSettlementStage.num.isEditable()) {

			// 決算段階の入力チェック
			if (field.chkSettlementStage.num.isEmpty()) {
				// 決算段階を入力してください
				showMessage("I00037", "C00718");
				field.chkSettlementStage.num.requestFocus();
				return false;
			}

			// 決算段階の範囲チェック
			int stage = field.chkSettlementStage.num.getInt();
			int max = company.getFiscalPeriod().getMaxSettlementStage();

			if (stage <= 0 || max < stage) {
				// {0}は{1}～{2}の範囲で指定してください
				showMessage("I00247", "C00718", 1, max);// 決算段階

				field.chkSettlementStage.num.requestFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * 決算段階を返す
	 * 
	 * @return 決算段顔
	 */
	public int getSettlementStage() {
		if (field.chkSettlementStage.isSelected()) {
			return field.chkSettlementStage.getStage();
		}
		return 0;
	}

}
