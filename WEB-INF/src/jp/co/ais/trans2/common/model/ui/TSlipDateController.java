package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 伝票日付フィールドのコントローラ
 * 
 * @author AIS
 */
public class TSlipDateController extends TController {

	/** 伝票日付フィールド */
	protected TSlipDate slipDate;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/**
	 * @param slipDate 伝票日付フィールド
	 */
	public TSlipDateController(TSlipDate slipDate) {
		this.slipDate = slipDate;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		//
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 現在日付を取得する。（先行伝票日付考慮あり）
	 * 
	 * @return 現在日付
	 */
	public Date getInitDate() {

		// 今日 > 先行伝票日付の場合 先行伝票日付
		// 今日 <= 先行伝票日付の場合 今日
		Date priorOverDate = getCompany().getFiscalPeriod().getPriorOverDate();
		Date currentDate = Util.getCurrentDate();

		if (currentDate.compareTo(priorOverDate) != 1) {
			return currentDate;
		} else {
			return priorOverDate;
		}
	}

	/**
	 * 当月の1日をセットする。<br>
	 * 決算段階は考慮しない。
	 */
	public void setFirstDateOfCurrentPeriod() {
		slipDate.setValue(getCompany().getFiscalPeriod().getFirstDateOfCurrentPeriod());
	}

	/**
	 * 当月の末日をセットする。<br>
	 * 決算段階は考慮しない。
	 */
	public void setLastDateOfCurrentPeriod() {
		slipDate.setValue(getCompany().getFiscalPeriod().getLastDateOfCurrentPeriod());
	}

	/**
	 * 当月の1日をセットする。<br>
	 * 決算段階を考慮する。
	 */
	public void setFirstDateOfCurrentPeriodOfSettlement() {
		slipDate.setValue(getCompany().getFiscalPeriod().getFirstDateOfCurrentPeriodOfSettlement());
	}

	/**
	 * 当月の末日をセットする。<br>
	 * 決算段階を考慮する。
	 */
	public void setLastDateOfCurrentPeriodOfSettlement() {
		slipDate.setValue(getCompany().getFiscalPeriod().getLastDateOfCurrentPeriodOfSettlement());
	}

	/**
	 * 期首日をセットする
	 */
	public void setDateBeginningOfPeriod() {
		slipDate.setValue(getCompany().getFiscalPeriod().getDateBeginningOfPeriod());
	}

	/**
	 * 締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 決算段階は考慮しない。
	 * 
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed() {

		Date closedDate = getCompany().getFiscalPeriod().getLastDateOfClosedPeriod();
		if (!Util.isNullOrEmpty(slipDate.getValue())) {
			return (closedDate.compareTo(slipDate.getValue()) >= 0);

		}
		return false;
	}

	/**
	 * 締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 
	 * @param settlementStage 決算段階
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed(int settlementStage) {

		return isClosed(getCompany(), settlementStage);
	}

	/**
	 * 指定会社を基準に、締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 
	 * @param company 会社
	 * @param settlementStage 決算段階
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed(Company company, int settlementStage) {

		Date closedDate = company.getFiscalPeriod().getLastDateOfClosedPeriod();

		Date date = slipDate.getValue();

		if (date != null && slipDate.getCalendarType() == TPopupCalendar.TYPE_YM) {
			// 年月指定なら、末日に変換
			date = DateUtil.getLastDate(date);
		}

		if (date != null) {
			if (closedDate.compareTo(date) > 0) {
				return true;

			} else if (closedDate.compareTo(date) == 0
				&& settlementStage <= company.getFiscalPeriod().getSettlementStage()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 決算段階は考慮する。
	 * 
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosedPeriodOfSettlement() {
		return isClosedPeriodOfSettlement(getCompany());
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
		return isClosed(company, company.getFiscalPeriod().getMaxSettlementStage());
	}

	/**
	 * 先行伝票入力日付を超えていないかを返す。<br>
	 * 超えている場合true、超えていない場合false
	 * 
	 * @return 先行伝票入力日付を超えている場合true、いない場合false
	 */
	public boolean isPriorOver() {
		return isPriorOver(getCompany());
	}

	/**
	 * 指定会社を基準に、先行伝票入力日付を超えていないかを返す。<br>
	 * 超えている場合true、超えていない場合false
	 * 
	 * @param company 会社
	 * @return 先行伝票入力日付を超えている場合true、いない場合false
	 */
	public boolean isPriorOver(Company company) {
		Date date = company.getFiscalPeriod().getPriorOverDate();
		if (!Util.isNullOrEmpty(slipDate.getValue())) {
			return (slipDate.getValue().compareTo(date) > 0);
		}

		return false;
	}

	/**
	 * 決算可能日付(決算月＆末日)かを返す
	 * 
	 * @return true(決算月) / false(通常月)
	 */
	public boolean isSettlementDate() {

		Date date = slipDate.getValue();

		if (date != null && slipDate.getCalendarType() == TPopupCalendar.TYPE_YM) {
			// 年月指定なら、末日に変換
			date = DateUtil.getLastDate(date);
		}

		return getCompany().getFiscalPeriod().isSettlementMonth(date)
			&& DateUtil.getDay(date) == DateUtil.getLastDay(date);
	}

	/**
	 * コールバックリスナー登録
	 * 
	 * @param listener コールバックリスナー
	 */
	public void addCallBackListener(TCallBackListener listener) {
		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TCallBackListener>();

			slipDate.setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!slipDate.isValueChanged()) {
						return true;
					}

					changedValue();

					return true;
				}
			});

		}

		callBackListenerList.add(listener);
	}

	/**
	 * コールバックリスナークリア
	 */
	public void clearCallBackListenerList() {
		if (callBackListenerList != null) {
			this.callBackListenerList.clear();
		}
	}

	/**
	 * @return コールバックリスナーを戻します。
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListenerList コールバックリスナーを設定します。
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		this.callBackListenerList = callBackListenerList;
	}

	/**
	 * 伝票日付の変更イベント
	 */
	public void changedValue() {
		if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
			for (TCallBackListener listener : callBackListenerList) {
				listener.after();
				listener.after(true);
				listener.afterVerify(true);
			}
		}
	}
}
