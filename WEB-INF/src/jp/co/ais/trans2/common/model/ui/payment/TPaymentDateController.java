package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払日コンポーネントのコントローラー
 * 
 * @author AIS
 */
public class TPaymentDateController extends TController {

	/** 支払日コンポーネント */
	protected TPaymentDate field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TPaymentDateController(TPaymentDate field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// イベント定義 支払区分
		field.ctrlPayType.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					// 支払日取得
					setPaymentDate();
				}
			}

		});

	}

	/**
	 * 参照するコンポーネントを設定する
	 */
	public void setReferenceComponentEvent() {

		// イベント定義 伝票日付
		field.ctrlSlipDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {

				if (!field.ctrlSlipDate.isValueChanged2()) {
					return true;
				}
				// 修正モードは変更しない
				if (field.isModifyMode()) {
					return true;
				}

				// 支払日取得
				setPaymentDate();
				return true;
			}
		});

		// イベント定義 支払条件
		field.ctrlPaymentSetting.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				// 修正モードは変更しない
				if (field.isModifyMode()) {
					return true;
				}

				// 支払日取得
				setPaymentDate();
				return true;
			}
		});

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 支払条件と伝票日付を元に支払日を設定する
	 */
	public void setPaymentDate() {

		try {

			// データが無い場合は処理終了
			if (field.ctrlPaymentSetting.getEntity() == null || field.ctrlSlipDate.getValue() == null) {
				return;
			}
			// 修正モードは変更しない
			if (field.isModifyMode()) {
				return;
			}

			// 支払条件と伝票日付の取得
			PaymentSetting bean = field.ctrlPaymentSetting.getEntity().clone();
			Date slipDate = field.ctrlSlipDate.getValue();
			bean.setPaymentDateType(field.ctrlPayType.getPaymentDateType());
			Date paymentDate = null;

			// 定時で締め日に値がある場合は締め日を元に支払日を取得
			if (field.ctrlPayType.getPaymentDateType().equals(PaymentDateType.REGULAR)
				&& !Util.isNullOrEmpty(field.closeDay)) {
				paymentDate = (Date) request(getManagerClass(), "getPaymentDate", bean, field.closeDay);
			} else {
				paymentDate = (Date) request(getManagerClass(), "getPaymentDate", bean, slipDate);
			}

			// 戻り値を支払日に設定
			field.calendar.setValue(paymentDate);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getManagerClass() {
		return PaymentManager.class;
	}

	/**
	 * 支払可能日かどうか
	 * 
	 * @return true:支払可能
	 */
	public boolean isPaymentDate() {

		try {

			// 支払条件と伝票日付の取得
			Date paymentDate = field.calendar.getValue();
			PaymentDateType paymentDateType = field.ctrlPayType.getPaymentDateType();

			// 支払日がブランクの場合はfalseを返す
			if (Util.isNullOrEmpty(paymentDate)) {
				return false;
			}

			return (Boolean) request(getManagerClass(), "isPaymentDate", paymentDateType, paymentDate);

		} catch (Exception e) {
			errorHandler(e);
			return false;
		}
	}

}
