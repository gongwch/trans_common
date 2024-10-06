package jp.co.ais.trans.common.bizui.ctrl;

import java.text.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * 支払情報フィールドコントロール
 */
public class TPaymentInformationFieldCtrl extends TAppletClientBase {

	/** フィールド */
	private TPaymentInformationField field;

	/**
	 * コンストラクタ
	 * 
	 * @param paymentInfoField field
	 */
	public TPaymentInformationFieldCtrl(TPaymentInformationField paymentInfoField) {
		try {
			this.field = paymentInfoField;
		} catch (Exception e) {
			errorHandler(paymentInfoField, e, "E00010");
		}
	}

	/**
	 * 支払方法入力時 画面制御
	 */
	public void paymentMethodScreenCtrl() {
		// 支払内部コード
		String naiCode = Util.avoidNull(field.getPaymentMethodField().getNaiCode());

		// 振出銀行制御
		// 支払方法が入力されているとき
		if (!Util.isNullOrEmpty(field.getPaymentMethodField().getValue())) {
			if (!Util.isNullOrEmpty(naiCode)) {
				// 支払内部コードが11・16・17・99のときは画面ロック
				if ("11".equals(naiCode) || "16".equals(naiCode) || "17".equals(naiCode) || "99".equals(naiCode)) {
					lockBankAccount();
				}
				// 画面ロック解除
				else {
					unLockBankAccount();
				}
			}
			// 支払内部コードが無い場合、画面ロック
			else {
				lockBankAccount();
			}
		}
		// 支払入力コードが入力されていない。
		else {
			lockBankAccount();
		}
	}

	/**
	 * 振出銀行 画面ロック
	 */
	private void lockBankAccount() {
		// 振出銀行コード
		field.getBankAccountField().setValue("");
		// 振出銀行名称
		field.getBankAccountField().setNoticeValue("");
		// 振出銀行ボタン不可
		field.getBankAccountField().getButton().setEnabled(false);
		// 振出銀行コード不可
		field.getBankAccountField().getField().setEditableEnabled(false);
	}

	/**
	 * 振出銀行 画面ロック解除
	 */
	private void unLockBankAccount() {
		// 振出銀行ボタン可
		field.getBankAccountField().getButton().setEnabled(true);
		// 振出銀行コード可
		field.getBankAccountField().getField().setEditableEnabled(true);
	}

	/**
	 * 支払条件入力時 画面制御
	 */
	public void paymentConditionScreenCtrl() {
		PaymentInformationParameter param = field.getPaymentConditionField().getParameter();
		if (!Util.isNullOrEmpty(field.getPaymentConditionField().getValue())) {
			// 有効期限が切れている場合、各コンポーネントをクリア
			if (!field.getPaymentConditionField().IsTermBasisDate()) {
				// 支払条件コードブランク
				field.getPaymentConditionField().setValue("");
				// 支払方法コードブランク
				field.getPaymentMethodField().setValue("");
				// 支払予定日ブランク
				field.getCalendar().setValue(null);
				// 支払区分：臨時
				field.getDivisonComboBox().getComboBox().setSelectedIndex(1);
			} else if (param.getFlag()) {
				// 支払方法コード
				field.getPaymentMethodField().setValue(param.getSihaHouCode());
				// 支払方法名称
				field.getPaymentMethodField().setNoticeValue(param.getSihaHouName());

				// 振出銀行制御
				if ("11".equals(param.getSihaNaiCode()) || "16".equals(param.getSihaNaiCode())
					|| "17".equals(param.getSihaNaiCode()) || "99".equals(param.getSihaNaiCode())) {
					// 振出銀行コード
					field.getBankAccountField().setValue("");
					// 振出銀行名称
					field.getBankAccountField().setNoticeValue("");
					// 振出銀行ボタン不可
					field.getBankAccountField().getButton().setEnabled(false);
					// 振出銀行コード不可
					field.getBankAccountField().getField().setEditableEnabled(false);
				} else {
					if (Util.isNullOrEmpty(param.getHuriCode())) {
						// 振出銀行コード
						field.getBankAccountField().setValue("");
						// 振出銀行名称
						field.getBankAccountField().setNoticeValue("");
					} else {
						// 振出銀行名称
						field.getBankAccountField().setNoticeValue(param.getHuriName());
						// 振出銀行コード
						field.getBankAccountField().setValue(param.getHuriCode());
					}
					// 振出銀行ボタン不可
					field.getBankAccountField().getButton().setEnabled(true);
					// 振出銀行コード不可
					field.getBankAccountField().getField().setEditableEnabled(true);
				}

				// 支払区分をセット
				// 支払区分 10：定時の場合
				if ("10".equals(param.getSihaKbn())) {
					field.getDivisonComboBox().getComboBox().setSelectedIndex(0);
				} else {
					field.getDivisonComboBox().getComboBox().setSelectedIndex(1);
				}
				// 支払日をセット
				try {
					field.getCalendar().setEnabled(true);
					field.getCalendar().setValue(DateUtil.toYMDDate(param.getSihaDate()));
				} catch (ParseException e) {
					// ブランク
					field.getCalendar().setEnabled(false);
					field.getCalendar().setValue(null);
				}
			}
		}
	}
}
