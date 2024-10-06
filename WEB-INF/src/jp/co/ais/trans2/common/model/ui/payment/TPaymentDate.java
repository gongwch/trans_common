package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 支払日コンポーネント
 */
public class TPaymentDate extends TPanel {

	/** 支払区分 */
	public TPaymentDateComboBox ctrlPayType;

	/** カレンダー */
	public TPopupCalendar calendar;

	/** 支払条件検索フィールド */
	public TPaymentSettingReference ctrlPaymentSetting;

	/** 伝票日付 */
	public TSlipDate ctrlSlipDate;

	/** コントローラー */
	public TPaymentDateController controller;

	/** 締め日 */
	public Date closeDay = null;

	/** 修正モードフラグ */
	protected boolean isModify = false;

	/**
	 * コンストラクタ.
	 */
	public TPaymentDate() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// コントローラー初期化
		createController();
	}

	/**
	 * コントローラー初期化
	 */
	protected void createController() {
		// コントローラー初期化
		controller = new TPaymentDateController(this);
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		ctrlPayType = new TPaymentDateComboBox();
		calendar = new TPopupCalendar();

		this.isModify = false;
	}

	/**
	 * 子itemの初期化.
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// 支払区分
		ctrlPayType.getLabel().setLangMessageID("C01098");// 支払日
		add(ctrlPayType, gridBagConstraints);

		// カレンダー
		add(calendar, gridBagConstraints);

		resize();
	}

	/**
	 * panelサイズを、並んだアイテムの横幅の合計にする
	 */
	public void resize() {
		Dimension size = this.getPreferredSize();
		size.setSize(ctrlPayType.getWidth() + calendar.getWidth(), size.getHeight());
		TGuiUtil.setComponentSize(this, size);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlPayType.setTabControlNo(tabControlNo);
		calendar.setTabControlNo(tabControlNo);
	}

	/**
	 * 支払日を取得する
	 * 
	 * @return 支払日
	 */
	public Date getPaymentDate() {
		return calendar.getValue();
	}

	/**
	 * 支払日 を設定する
	 * 
	 * @param date 支払日
	 */
	public void setPaymentDate(Date date) {
		calendar.setValue(date);
	}

	/**
	 * 支払条件と伝票日付を元に支払日を設定する(自動)
	 */
	public void setPaymentDate() {
		controller.setPaymentDate();
	}

	/**
	 * フォーカス設定
	 */
	public void requestTextFocus() {
		calendar.requestFocus();
	}

	/**
	 * 支払区分を取得する
	 * 
	 * @return 支払区分
	 */
	public PaymentDateType getPaymentDateType() {
		return ctrlPayType.getPaymentDateType();
	}

	/**
	 * クリア
	 */
	public void clear() {
		ctrlPayType.init();
		calendar.clear();
	}

	/**
	 * 支払区分を設定する
	 * 
	 * @param paymentDateType
	 */
	public void setPaymentDateType(PaymentDateType paymentDateType) {

		ctrlPayType.setPaymentDateType(paymentDateType);

	}

	/**
	 * 入力制御
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		ctrlPayType.setEnabled(isEditable);
		calendar.setEditable(isEditable);
	}

	/**
	 * 支払可能日かどうか
	 * 
	 * @return true:支払可能
	 */
	public boolean isPaymentDate() {
		return controller.isPaymentDate();
	}

	/**
	 * 参照するコンポーネントを設定する
	 * 
	 * @param ctrlSlipDate
	 * @param ctrlPaymentSetting
	 */
	public void setReferenceComponent(TSlipDate ctrlSlipDate, TPaymentSettingReference ctrlPaymentSetting) {

		// 引数の保存
		this.ctrlPaymentSetting = ctrlPaymentSetting;
		this.ctrlSlipDate = ctrlSlipDate;

		// イベントの設定
		controller.setReferenceComponentEvent();
	}

	/**
	 * 締め日を設定する。
	 * 
	 * @param closeDay
	 */
	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}

	/**
	 * 支払日が入力されているか
	 * 
	 * @return true:入力済、false:未入力
	 */
	public boolean isEmpty() {
		return calendar.isEmpty();
	}

	/**
	 * 修正モード を設定する。
	 * 
	 * @param isModify
	 */
	public void setModifyMode(boolean isModify) {
		this.isModify = isModify;
	}

	/**
	 * 修正モード を設定する。
	 * 
	 * @return isModify
	 */
	public boolean isModifyMode() {
		return this.isModify;
	}

}
