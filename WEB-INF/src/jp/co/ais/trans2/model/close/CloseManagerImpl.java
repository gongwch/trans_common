package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 締め管理マネージャの実装
 * 
 * @author AIS
 */
public class CloseManagerImpl extends TModel implements CloseManager {

	/** true:CM_FUND_DTL登録を行うか */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/**
	 * 指定会社の会計締め情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @return 指定会社の会計締め情報
	 * @throws TException
	 */
	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException {
		CloseDao dao = (CloseDao) getComponent(CloseDao.class);
		return dao.getFiscalPeriod(companyCode);
	}

	public int getFiscalYear(Date date, Company company) {
		return company.getFiscalPeriod().getFiscalYear(date);
	}

	/**
	 * 指定日付の月度を返す
	 * 
	 * @param date 日付
	 * @param company 会社情報(期首月がセットされていることが前提)
	 * @return 指定日付の月度
	 */
	public int getFiscalMonth(Date date, Company company) {

		return company.getFiscalPeriod().getFiscalMonth(date);

	}

	/**
	 * 指定日付の年度の期首日を返す
	 * 
	 * @param date 日付
	 * @param company 会社情報(期首月がセットされていることが前提)
	 * @return 指定日付の年度の期首日
	 */
	public Date getDateBeginningOfPeriod(Date date, Company company) {

		return company.getFiscalPeriod().getDateBeginningOfPeriod(date);

	}

	/**
	 * 日次の締めを行う
	 * 
	 * @param date 伝票日付
	 * @param company 会社
	 * @throws TException
	 */
	public void closeDaily(Date date, Company company) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		dc.closeDaily(date, company);
	}

	/**
	 * 最後に日次更新した日付を返す
	 * 
	 * @param company
	 * @return 最後に日次更新した日付
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		return dc.getLastDailyClosedDate(company);
	}

	/**
	 * 月次更新する。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間情報
	 * @return 会計期間Entity
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// 伝票種別｢01Z：外貨評価替概算伝票｣が存在する場合エラー
		if (mc.isExistsForeignCurrencyRevaluationSlip(companyCode, fp)) {
			// 外貨評価替概算伝票が存在するため実行できません。
			throw new TWarningException("I01094");
		}

		// 未更新データが存在する場合エラー
		if (mc.isExistsNotClosedSlip(companyCode, fp)) {
			// 未更新データが残っています。
			throw new TWarningException("W00201");
		}

		// 外貨評価替対象の勘定科目が更新されているかチェック
		if (mc.isUpdateRevaluationSlip(companyCode, fp)) {
			// 外貨評価替対象の勘定科目が追加、または修正されました。外貨評価替伝票作成を実行してください。
			throw new TWarningException("I01100");
		}

		// 燃料管理状況のチェック 締日以前に未処理が存在の場合エラー
		checkBunkerStatus(fp);

		// 支払処理が完了していないデータが存在する場合エラー
		List<Message> errList = mc.getNotPaidData(companyCode, fp);
		if (errList != null && !errList.isEmpty()) {
			// I00426:支払処理が完了していないデータが残っています。
			throw new TMessageListException("I00426", errList);
		}

		if (isUseCmFund) {
			// 伝票未作成のCM_FUND_DTLが存在したらエラー
			if (mc.isExistsNotSlipByCmFund(companyCode, fp)) {
				// 伝票が未作成の銀行口座資金明細が存在します。\n口座別資金繰表で確認してください。
				throw new TWarningException("I01109");
			}
		}
		
		// 月次締め実行
		fp = mc.closeMonthly(companyCode, fp);

		return fp;

	}

	/**
	 * 燃料管理のステータスをチェックする<br>
	 * エラーをThrow時：締日以前に燃料管理で処理が必要
	 * 
	 * @param fp 会計期間情報
	 * @throws TException
	 */
	protected void checkBunkerStatus(FiscalPeriod fp) throws TException {
		MonthlyClose dao = (MonthlyClose) getComponent(MonthlyClose.class);
		List<BMCloseInfo> list = null;
		List<BunkerInfo> listBunker = null;
		try {
			list = dao.getBM();
			listBunker = dao.getBunker(fp);
		} catch (Exception e) {
			// エラーキャッチ想定：テーブル非存在
			// 燃料管理なしの場合チェック不要
			return;
		}

		// エラーメッセージチェック・構築
		List<Message> msgs = new ArrayList();
		Date closeDate = DateUtil.getLastDate(DateUtil.addMonth(fp.getFirstDateOfClosedPeriodOfSettlement(), 1));

		// 対象データ非存在の場合、エラー無しで処理終了
		if (list != null) {
			for (BMCloseInfo info : list) {
				Date slipDate = info.getDEN_DATE(); // 伝票日付
				if (slipDate == null) {
					slipDate = closeDate;
				} else {
					slipDate = DateUtil.getLastDate(slipDate);
				}
				// 月次費用振替入力が未入力かどうかをチェック
				if (closeDate.compareTo(slipDate) == 1) {
					// 燃料管理の月次費用振替入力が実行されていません。船：{0} 油種区分：{1}
					msgs.add(new Message("I00452", info.getVESSEL_NAME(), info.getOIL_TYPE_NAME()));
				}
			}
		}

		// 対象データ非存在の場合、エラー無しで処理終了
		if (listBunker != null) {
			for (BunkerInfo bnkrInf : listBunker) {
				// 燃料管理の月次費用振替入力が実行されていません。船：{0} 油種区分：{1}
				msgs.add(new Message("I00452", bnkrInf.getVESSEL_NAME(), bnkrInf.getOIL_TYPE_NAME()));
			}
		}

		if (!msgs.isEmpty()) {
			throw new TMessageListException(msgs);
		}
	}

	/**
	 * 月次更新の取消をする。
	 * 
	 * @param companyCode 会社コード
	 * @param fp 会計期間情報
	 * @return 会計期間Entity
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// 月次締め取消実行
		fp = mc.cancelMonthly(companyCode, fp);

		return fp;

	}

	/****************************************************************************************************************
	 * 以下複数会社版メソッド
	 ****************************************************************************************************************/

	/**
	 * 日次更新する。
	 * 
	 * @param baseDate 処理年月日
	 * @param list 月次更新情報
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllDaily(Date baseDate, List<DailyData> list) throws TWarningException, TException {

		Company loginCompany = getCompany();
		User loginUser = getUser();

		try {

			DailyClose dc = (DailyClose) getComponent(DailyClose.class);
			CompanyManager cm = get(CompanyManager.class);

			// メッセージ作成
			List<Message> msgList = new ArrayList<Message>();

			String msgCmp = getWord("C12111");
			
			for (DailyData dd : list) {

				if (Util.isNullOrEmpty(dd.getCompanyCode())) {
					continue;
				}

				Company cmp = cm.get(dd.getCompanyCode());

				dd.setLastUpdateDate(getNow());
				setCompany(cmp);

				String subTitle = msgCmp + dd.getCompanyCode();

				try {
					// 実行

					// 日次更新実行
					dc.closeDaily(baseDate, cmp);

					// 完了メッセージ
					Message msg = new Message();
					msg.setMessage("I00013");
					msg.setSubMessageID(subTitle);
					msgList.add(msg);

				} catch (TException e) {

					// エラーメッセージ
					Message msg = new Message();
					msg.setMessage(e.getMessageID(), e.getMessageArgs());
					msg.setSubMessageID(subTitle);
					msgList.add(msg);

				} catch (Exception e) {
					throw new TException(e);
				}
			}

			return msgList;

		} finally {
			setCompany(loginCompany);
			setUser(loginUser);
		}
	}

	/**
	 * 日次更新データを取得する
	 * 
	 * @return 日次更新データ
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		List<DailyData> list = dc.getDailyData();

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list;
	}

	/**
	 * 月次更新する。
	 * 
	 * @param list 月次更新情報
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllMonthly(List<MonthData> list) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// 他のユーザーにより対象レコードが変更チェック
		if (!checkMonthly(list)) {
			throw new TException("W00229");
		}

		List<Message> msgList = new ArrayList<Message>();

		String msgCmp = getWord("C12111");
		
		for (MonthData monthData : list) {

			Message msg = new Message();
			String subTitle = msgCmp + monthData.getCompanyCode();

			String companyCode = monthData.getCompanyCode();
			FiscalPeriod fp = monthData.getCompany().getFiscalPeriod();
			
			// 伝票種別｢01Z：外貨評価替概算伝票｣が存在する場合エラー
			if (mc.isExistsForeignCurrencyRevaluationSlip(companyCode, fp)) {
				// 外貨評価替概算伝票が存在するため実行できません。
				msg.setMessage("I01094");
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}

			// 未更新データが存在する場合エラー
			if (mc.isExistsNotClosedSlip(companyCode, fp)) {
				// 未更新のデータが残っています。
				msg.setMessage("W00201");
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}

			// 外貨評価替対象の勘定科目が更新されているかチェック
			if (mc.isUpdateRevaluationSlip(companyCode, fp)) {
				// 外貨評価替対象の勘定科目が追加、または修正されました。外貨評価替伝票作成を実行してください。
				msg.setMessage("I01100");
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}

			// 燃料管理状況のチェック 締日以前に未処理が存在の場合エラー
			checkBunkerStatus(fp);

			// 支払処理が完了していないデータが存在する場合エラー
			List<Message> errList = mc.getNotPaidData(companyCode, fp);
			if (errList != null && !errList.isEmpty()) {
				// I00426:支払処理が完了していないデータが残っています。
				msg.setMessage("I00426", errList);
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}


			if (isUseCmFund) {
				// 伝票未作成のCM_FUND_DTLが存在したらエラー
				if (mc.isExistsNotSlipByCmFund(companyCode, fp)) {
					// 伝票が未作成の銀行口座資金明細が存在します。\n口座別資金繰表で確認してください。
					msg.setMessage("I01109");
					msg.setSubMessageID(subTitle);
					msgList.add(msg);
					continue;
				}
			}

			// 月次締め実行
			mc.closeMonthly(monthData.getCompanyCode(), monthData.getCompany().getFiscalPeriod());

			// 完了メッセージ
			msg.setMessage("I00013");
			msg.setSubMessageID(subTitle);
			msgList.add(msg);

		}
		return (msgList);
	}

	/**
	 * 月次更新データを取得する
	 * 
	 * @param condition 検索条件
	 * @return 月次更新データ
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException {
		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);
		List<MonthData> list = mc.getMonthData(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list;
	}

	/**
	 * 他のユーザーにより対象レコードが変更チェック
	 * 
	 * @param oldList 月次更新情報
	 * @return true:問題なし、false:対象レコードが違う会社あり
	 * @throws TException
	 */
	public boolean checkMonthly(List<MonthData> oldList) throws TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// 他のユーザーにより対象レコードが変更されています。再度、検索を行ってください。
		List<String> companyCodeList = new ArrayList<String>();

		for (MonthData monthData : oldList) {
			companyCodeList.add(monthData.getCompanyCode());
		}

		MonthDataSearchCondition condition = new MonthDataSearchCondition();
		condition.setCompanyCodeList(companyCodeList);
		List<MonthData> newList = mc.getMonthData(condition);

		if (newList == null || newList.isEmpty()) {
			return false;
		}

		for (MonthData oldData : oldList) {

			for (MonthData newData : newList) {

				if (newData.getCompanyCode().equals(oldData.getCompanyCode())
					&& (newData.getEntry() != oldData.getEntry() || newData.getDeny() != oldData.getDeny() || newData
						.getApprove() != oldData.getApprove())) {
					// 同じ会社の対象レコード値が違う場合、エラー戻る
					return false;

				}

			}
		}

		return true;

	}

	/**
	 * 月次更新の取消をする。
	 * 
	 * @param list 月次更新情報
	 * @throws TWarningException
	 * @throws TException
	 */
	public void cancelAllMonthly(List<MonthData> list) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// 他のユーザーにより対象レコードが変更チェック
		if (!checkMonthly(list)) {
			throw new TException("W00229");
		}

		for (MonthData monthData : list) {
			// 月次取消実行
			mc.cancelMonthly(monthData.getCompanyCode(), monthData.getCompany().getFiscalPeriod());
		}
	}
	
	/**
	 * 「 REVALUATION_CTL」のSTATUS_KBNを更新する
	 * 
	 * @param date
	 * @param companyCode
	 * @throws TException
	 */
	public void checkStatus(Date date, String companyCode) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		dc.checkStatus(date, companyCode);
	}

	/**
	 * 外貨評価替伝票の存在チェック
	 * 
	 * @param companyCode
	 * @return PROC_YM
	 * @throws TException
	 */
	public List<Date> existSlip(String companyCode) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		return dc.existSlip(companyCode);
	}
}
