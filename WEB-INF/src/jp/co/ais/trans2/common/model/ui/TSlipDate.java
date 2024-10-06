package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 伝票日付フィールド
 * 
 * @author AIS
 */
public class TSlipDate extends TLabelPopupCalendar {

	/** serialVersionUID */
	private static final long serialVersionUID = -6142880729524534321L;

	/** コントローラ */
	protected TSlipDateController controller;

	/** 年 */
	public static final int TYPE_Y = TCalendar.TYPE_Y;

	/** 年月 */
	public static final int TYPE_YM = TCalendar.TYPE_YM;

	/** 年月日 */
	public static final int TYPE_YMD = TCalendar.TYPE_YMD;

	/**
	 * コンストラクタ.
	 */
	public TSlipDate() {

		this(TYPE_YMD);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 * 
	 * @param calType 日付表示形式
	 */
	public TSlipDate(int calType) {

		super(calType);

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// コントローラ生成
		controller = createController();

	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	protected TSlipDateController createController() {
		return new TSlipDateController(this);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		//
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setLabelSize(60);
		setPreferredSize(new Dimension(getCalendarSize() + 60 + 5, 20));
		setSize(new Dimension(getCalendarSize() + 60 + 5, 20));
		setLangMessageID("C00599");
	}

	/**
	 * setLangMessageID()を使うこと
	 * 
	 * @deprecated
	 * @param text 設定文字列
	 */
	@Override
	public void setLabelText(String text) {
		this.label.setLangMessageID(text);
	}

	/**
	 * 現在日付を設定する。（先行伝票日付考慮あり）
	 */
	public void initSlipDate() {
		this.setValue(controller.getInitDate());
	}

	/**
	 * 当月の1日をセットする。<br>
	 * 決算段階は考慮しない。
	 */
	public void setFirstDateOfCurrentPeriod() {
		controller.setFirstDateOfCurrentPeriod();
	}

	/**
	 * 当月の末日をセットする。<br>
	 * 決算段階は考慮しない。
	 */
	public void setLastDateOfCurrentPeriod() {
		controller.setLastDateOfCurrentPeriod();
	}

	/**
	 * 当月の1日をセットする。<br>
	 * 決算段階を考慮する。
	 */
	public void setFirstDateOfCurrentPeriodOfSettlement() {
		controller.setFirstDateOfCurrentPeriodOfSettlement();
	}

	/**
	 * 当月の末日をセットする。<br>
	 * 決算段階を考慮する。
	 */
	public void setLastDateOfCurrentPeriodOfSettlement() {
		controller.setLastDateOfCurrentPeriodOfSettlement();
	}

	/**
	 * 期首日をセットする
	 */
	public void setDateBeginningOfPeriod() {
		controller.setDateBeginningOfPeriod();
	}

	/**
	 * 締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 決算段階は考慮しない。
	 * 
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed() {
		return controller.isClosed();
	}

	/**
	 * 指定の決算段階まで締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 
	 * @param settlementStage 決算段階
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed(int settlementStage) {
		return controller.isClosed(settlementStage);
	}

	/**
	 * 指定の決算段階まで締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 
	 * @param company 会社
	 * @param settlementStage 決算段階
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed(Company company, int settlementStage) {
		return controller.isClosed(company, settlementStage);
	}

	/**
	 * 締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 決算段階は考慮する。
	 * 
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosedPeriodOfSettlement() {
		return controller.isClosedPeriodOfSettlement();
	}

	/**
	 * 指定会社を基準に、締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 決算段階は考慮する。
	 * 
	 * @param company 会社
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosedPeriodOfSettlement(Company company) {
		return controller.isClosedPeriodOfSettlement(company);
	}

	/**
	 * 先行伝票入力日付を超えていないかを返す。<br>
	 * 超えている場合true、超えていない場合false
	 * 
	 * @return 先行伝票入力日付を超えている場合true、いない場合false
	 */
	public boolean isPriorOver() {
		return controller.isPriorOver();
	}

	/**
	 * 指定会社を基準に、先行伝票入力日付を超えていないかを返す。<br>
	 * 超えている場合true、超えていない場合false
	 * 
	 * @param company 会社
	 * @return 先行伝票入力日付を超えている場合true、いない場合false
	 */
	public boolean isPriorOver(Company company) {
		return controller.isPriorOver(company);
	}

	/**
	 * 決算可能日付(決算月＆末日)かを返す
	 * 
	 * @return true(決算可能日付) / false(決算可能日付以外)
	 */
	public boolean isSettlementDate() {
		return controller.isSettlementDate();
	}

	/**
	 * コールバックリスナー登録
	 * 
	 * @param listener コールバックリスナー
	 */
	public void addCallBackListener(TCallBackListener listener) {
		controller.addCallBackListener(listener);
	}

	/**
	 * コールバックリスナークリア
	 */
	public void clearCallBackListenerList() {
		controller.clearCallBackListenerList();
	}

	/**
	 * @return コールバックリスナーを戻します。
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return controller.getCallBackListenerList();
	}

	/**
	 * @param callBackListenerList コールバックリスナーを設定します。
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		controller.setCallBackListenerList(callBackListenerList);
	}

	/**
	 * コールバック用にOveride
	 * 
	 * @see jp.co.ais.trans.common.gui.TLabelPopupCalendar#setValue(java.util.Date)
	 */
	@Override
	public void setValue(Date date) {
		super.setValue(date);

		controller.changedValue();
	}
}