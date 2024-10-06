package jp.co.ais.trans2.model.slip.parts;

import static jp.co.ais.trans2.define.SlipError.*;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 仕訳明細チェッククラス
 */
public class JournalDetailCheck extends TModel {

	/** true:IFRS区分より発生日は科目の発生日フラグにより制御機能有効＜Server＞ */
	public static boolean allowOccurDateBlank = ServerConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** 会社情報 */
	protected Map<String, Company> companys = new TreeMap<String, Company>();

	/** 基軸通貨 */
	protected String keyCurrency;

	/** エラー情報 */
	protected List<Message> errorList = new LinkedList<Message>();

	/** チェック対象 */
	protected List<SWK_DTL> detailList;

	/** header情報 */
	protected SWK_HDR header;

	/**
	 * 仕訳明細の有効期限/整合性チェックをする
	 * 
	 * @param slip 伝票
	 * @return errList エラーメッセージリスト
	 * @throws TException
	 */
	public List<Message> checkDetail(Slip slip) throws TException {
		errorList.clear();
		companys.clear();

		header = slip.getHeader();
		detailList = slip.getDetails();

		// 明細が存在しない場合は、処理終了
		if (Util.isNullOrEmpty(detailList) || detailList.size() == 0) {
			return errorList;
		}

		// 基軸通貨
		keyCurrency = getCompany().getAccountConfig().getKeyCurrency().getCode();

		// 明細の有効期限チェック
		checkTerm();

		// 明細の整合性チェック
		checkConsistency();

		return errorList;
	}

	/**
	 * 仕訳明細の有効期限をチェックする
	 * 
	 * @throws TException
	 */
	protected void checkTerm() throws TException {

		int count = 0;// 行数カウント

		for (SWK_DTL dtl : detailList) {
			count++;

			// 自動仕訳の場合は次の明細へ
			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue;
			}

			// 債権・債務残高消込行の場合は次の明細へ
			if (isSkip(dtl)) {
				continue;
			}

			Company company = getCompany(dtl.getSWK_K_KAI_CODE());
			// データが見つからない
			if (company == null) {
				// エラー情報の設定
				addError(NOT_FOUND, K_KAI_CODE, count, "I00065", count, "C00570", dtl.getSWK_K_KAI_CODE());
				continue;

			}
			AccountConfig conf = company.getAccountConfig();

			// 仕訳ジャーナル.伝票日付
			Date slipDate = dtl.getSWK_DEN_DATE();

			// 仕訳ジャーナル.発生日
			Date occurDate = dtl.getHAS_DATE();
			if (occurDate == null) {
				occurDate = slipDate;
			}

			// 仕訳ジャーナル.計上会社コード
			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			// 仕訳ジャーナル.部門コード
			String depCode = dtl.getSWK_DEP_CODE();

			// 仕訳ジャーナル.科目コード
			String kmkCode = dtl.getSWK_KMK_CODE();

			// 仕訳ジャーナル.補助科目コード
			String hkmCode = dtl.getSWK_HKM_CODE();

			// 仕訳ジャーナル.内訳科目コード
			String ukmCode = dtl.getSWK_UKM_CODE();

			// 仕訳ジャーナル.通貨コード
			String curCode = dtl.getSWK_CUR_CODE();

			// 仕訳ジャーナル.行摘要コード
			String tekCode = dtl.getSWK_GYO_TEK_CODE();

			// 仕訳ジャーナル.データ区分
			String dataKbn = dtl.getSWK_DATA_KBN();

			// 仕訳ジャーナル.消費税コード
			String zeiCode = dtl.getSWK_ZEI_CODE();

			// 仕訳ジャーナル.社員コード
			String empCode = dtl.getSWK_EMP_CODE();

			// 仕訳ジャーナル.取引先コード
			String triCode = dtl.getSWK_TRI_CODE();

			// 仕訳ジャーナル.管理1コード
			String knrCode1 = dtl.getSWK_KNR_CODE_1();

			// 仕訳ジャーナル.管理2コード
			String knrCode2 = dtl.getSWK_KNR_CODE_2();

			// 仕訳ジャーナル.管理3コード
			String knrCode3 = dtl.getSWK_KNR_CODE_3();

			// 仕訳ジャーナル.管理4コード
			String knrCode4 = dtl.getSWK_KNR_CODE_4();

			// 仕訳ジャーナル.管理5コード
			String knrCode5 = dtl.getSWK_KNR_CODE_5();

			// 仕訳ジャーナル.管理6コード
			String knrCode6 = dtl.getSWK_KNR_CODE_6();

			// 環境設定マスタ
			if (!Util.isNullOrEmpty(kKaiCode)) {
				// 有効期限チェック
				Date from = company.getDateFrom();
				Date to = company.getDateTo();

				if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
					|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {
					// エラー情報の設定
					addError(TERM_OUT, K_KAI_CODE, count, "I00060", count, "C00570", kKaiCode);
				}
			}

			// 部門マスタ
			if (!Util.isNullOrEmpty(depCode)) {
				DepartmentManager dao = (DepartmentManager) getComponent(DepartmentManager.class);

				// 部門マスタ検索（存在チェック）
				DepartmentSearchCondition condition = new DepartmentSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(depCode);
				List<Department> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, DEP_CODE, count, "I00065", count, "C00571", depCode);

				} else {
					Department bean = list.get(0);

					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, DEP_CODE, count, "I00060", count, "C00571", depCode);
					}
				}
			}

			ItemManager itemMn = (ItemManager) getComponent(ItemManager.class);
			// 科目マスタ
			if (!Util.isNullOrEmpty(kmkCode)) {
				Item bean = itemMn.getItem(kKaiCode, kmkCode, null, null);

				// データが見つからない
				if (bean == null) {

					// エラー情報の設定
					addError(NOT_FOUND, KMK_CODE, count, "I00079", count, conf.getItemName(), kmkCode);

				} else {

					if (!Util.isNullOrEmpty(bean.getFixedDepartmentCode())
						&& !bean.getFixedDepartmentCode().equals(depCode)) {

						// エラー情報の設定
						// 部門コードを科目.固定部門にしてください。
						addError(ITEM_FIXED_OUT, KMK_CODE, count, "I00584", count, conf.getItemName(), kmkCode);

					} else {
						// 有効期限チェック
						Date from = bean.getDateFrom();
						Date to = bean.getDateTo();

						if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
							|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

							// エラー情報の設定
							addError(TERM_OUT, KMK_CODE, count, "I00080", count, conf.getItemName(), kmkCode);
						}
					}
				}
			}

			// 補助科目マスタ
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode)) {

				// 補助科目マスタ検索
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, null);

				// データが見つからない
				if (bean == null || bean.getSubItem() == null) {

					// エラー情報の設定
					addError(NOT_FOUND, HKM_CODE, count, "I00079", count, conf.getSubItemName(), hkmCode);

				} else {
					// 有効期限チェック
					Date from = bean.getSubItem().getDateFrom();
					Date to = bean.getSubItem().getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {
						// エラー情報の設定
						addError(TERM_OUT, HKM_CODE, count, "I00080", count, conf.getSubItemName(), hkmCode);
					}
				}
			}

			// 内訳科目マスタ
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode) && !Util.isNullOrEmpty(ukmCode)) {

				// 内訳科目マスタ検索
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, ukmCode);

				// データが見つからない
				if (bean == null || bean.getSubItem() == null || bean.getSubItem().getDetailItem() == null) {

					// エラー情報の設定
					addError(NOT_FOUND, UKM_CODE, count, "I00079", count, conf.getDetailItemName(), ukmCode);

				} else {
					// 有効期限チェック
					Date from = bean.getSubItem().getDetailItem().getDateFrom();
					Date to = bean.getSubItem().getDetailItem().getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, UKM_CODE, count, "I00080", count, conf.getDetailItemName(), ukmCode);
					}
				}
			}

			// 通貨マスタ
			if (!Util.isNullOrEmpty(curCode)) {
				// 通貨マスタ検索
				CurrencyManager dao = (CurrencyManager) getComponent(CurrencyManager.class);
				CurrencySearchCondition condition = new CurrencySearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(curCode);
				List<Currency> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, CUR_CODE, count, "I00065", count, "C00665", curCode);

				} else {
					Currency bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, CUR_CODE, count, "I00060", count, "C00665", curCode);
					}
				}
			}

			// 摘要マスタ
			if (!Util.isNullOrEmpty(tekCode)) {
				// 摘要マスタ検索
				RemarkManager dao = (RemarkManager) getComponent(RemarkManager.class);
				RemarkSearchCondition condition = new RemarkSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(tekCode);
				condition.setDataType(dataKbn);
				condition.setSlipRemark(false);
				List<Remark> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, GYO_TEK_CODE, count, "I00065", count, "C00564", tekCode);

				} else {
					Remark bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, GYO_TEK_CODE, count, "I00060", count, "C00564", tekCode);
					}
				}
			}

			// 消費税マスタ
			if (!Util.isNullOrEmpty(zeiCode)) {
				ConsumptionTaxManager dao = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);
				ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(zeiCode);
				// 消費税マスタ検索
				List<ConsumptionTax> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, ZEI_CODE, count, "I00065", count, "C00573", zeiCode);

				} else {
					ConsumptionTax bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, occurDate))
						|| (to != null && !Util.isSmallerThenByYMD(occurDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, ZEI_CODE, count, "I00060", count, "C00573", zeiCode);
					}
				}
			}

			// 社員マスタ
			if (!Util.isNullOrEmpty(empCode)) {
				// 社員マスタDao
				EmployeeManager dao = (EmployeeManager) getComponent(EmployeeManager.class);

				// 社員マスタデータを検索
				EmployeeSearchCondition condition = new EmployeeSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(empCode);
				List<Employee> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, EMP_CODE, count, "I00065", count, "C00697", empCode);

				} else {
					Employee bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, EMP_CODE, count, "I00060", count, "C00697", empCode);
					}
				}
			}

			// 取引先マスタ
			if (!Util.isNullOrEmpty(triCode)) {
				// 取引先マスタDao
				CustomerManager dao = (CustomerManager) getComponent(CustomerManager.class);

				// 取引先マスタデータを検索
				CustomerSearchCondition condition = new CustomerSearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(triCode);
				List<Customer> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, TRI_CODE, count, "I00065", count, "C00786", triCode);

				} else {
					Customer bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, TRI_CODE, count, "I00060", count, "C00786", triCode);
					}
				}
			}

			// 管理1マスタ
			if (!Util.isNullOrEmpty(knrCode1)) {
				Management1Manager dao = (Management1Manager) getComponent(Management1Manager.class);
				Management1SearchCondition condition = new Management1SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode1);

				// 管理1マスタ検索
				List<Management1> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, KNR_CODE_1, count, "I00079", count, conf.getManagement1Name(), knrCode1);

				} else {
					// 有効期限チェック
					Management1 bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, KNR_CODE_1, count, "I00080", count, conf.getManagement1Name(), knrCode1);
					}
				}
			}

			// 管理2マスタ
			if (!Util.isNullOrEmpty(knrCode2)) {
				Management2Manager dao = (Management2Manager) getComponent(Management2Manager.class);
				Management2SearchCondition condition = new Management2SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode2);

				// 管理2マスタ検索
				List<Management2> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, KNR_CODE_2, count, "I00079", count, conf.getManagement2Name(), knrCode2);

				} else {
					Management2 bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, KNR_CODE_2, count, "I00080", count, conf.getManagement2Name(), knrCode2);

					}
				}
			}

			// 管理3マスタ
			if (!Util.isNullOrEmpty(knrCode3)) {
				Management3Manager dao = (Management3Manager) getComponent(Management3Manager.class);
				Management3SearchCondition condition = new Management3SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode3);

				// 管理3マスタ検索
				List<Management3> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, KNR_CODE_3, count, "I00079", count, conf.getManagement3Name(), knrCode3);

				} else {
					Management3 bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, KNR_CODE_3, count, "I00080", count, conf.getManagement3Name(), knrCode3);
					}
				}
			}

			// 管理4マスタ
			if (!Util.isNullOrEmpty(knrCode4)) {
				Management4Manager dao = (Management4Manager) getComponent(Management4Manager.class);
				Management4SearchCondition condition = new Management4SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode4);

				// 管理4マスタ検索
				List<Management4> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, KNR_CODE_4, count, "I00079", count, conf.getManagement4Name(), knrCode4);

				} else {
					Management4 bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, KNR_CODE_4, count, "I00080", count, conf.getManagement4Name(), knrCode4);

					}
				}
			}

			// 管理5マスタ
			if (!Util.isNullOrEmpty(knrCode5)) {
				Management5Manager dao = (Management5Manager) getComponent(Management5Manager.class);
				Management5SearchCondition condition = new Management5SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode5);

				// 管理5マスタ検索
				List<Management5> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, KNR_CODE_5, count, "I00079", count, conf.getManagement5Name(), knrCode5);

				} else {
					Management5 bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, KNR_CODE_5, count, "I00080", count, conf.getManagement5Name(), knrCode5);

					}
				}
			}

			// 管理6マスタ
			if (!Util.isNullOrEmpty(knrCode6)) {
				Management6Manager dao = (Management6Manager) getComponent(Management6Manager.class);
				Management6SearchCondition condition = new Management6SearchCondition();
				condition.setCompanyCode(kKaiCode);
				condition.setCode(knrCode6);

				// 管理6マスタ検索
				List<Management6> list = dao.get(condition);

				// データが見つからない
				if (list == null || list.isEmpty()) {

					// エラー情報の設定
					addError(NOT_FOUND, KNR_CODE_6, count, "I00079", count, conf.getManagement6Name(), knrCode6);

				} else {
					Management6 bean = list.get(0);
					// 有効期限チェック
					Date from = bean.getDateFrom();
					Date to = bean.getDateTo();

					if ((from != null && !Util.isSmallerThenByYMD(from, slipDate))
						|| (to != null && !Util.isSmallerThenByYMD(slipDate, to))) {

						// エラー情報の設定
						addError(TERM_OUT, KNR_CODE_6, count, "I00080", count, conf.getManagement6Name(), knrCode6);
					}
				}
			}
		}
	}

	/**
	 * 仕訳明細の整合性チェックをする
	 * 
	 * @throws TException
	 */
	protected void checkConsistency() throws TException {

		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG() && isSlipTypeInvoiceSys(header);
		// 各変数初期化
		int count = 0;// 行数カウント

		for (SWK_DTL dtl : detailList) {
			count++;

			// 自動仕訳の場合は次の明細へ
			if (dtl.getSWK_AUTO_KBN() == 1) {
				continue;
			}

			// 債権・債務残高消込行の場合は次の明細へ
			if (isSkip(dtl)) {
				continue;
			}

			Company company = getCompany(dtl.getSWK_K_KAI_CODE());
			AccountConfig conf = company.getAccountConfig();

			// 仕訳ジャーナル.計上会社コード
			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			// 仕訳ジャーナル.部門コード
			String depCode = dtl.getSWK_DEP_CODE();

			// 仕訳ジャーナル.科目コード
			String kmkCode = dtl.getSWK_KMK_CODE();

			// 仕訳ジャーナル.補助科目コード
			String hkmCode = dtl.getSWK_HKM_CODE();

			// 仕訳ジャーナル.内訳科目コード
			String ukmCode = dtl.getSWK_UKM_CODE();

			// 仕訳ジャーナル.通貨コード
			String curCode = dtl.getSWK_CUR_CODE();

			// 仕訳ジャーナル.消費税コード
			String zeiCode = dtl.getSWK_ZEI_CODE();

			// 仕訳ジャーナル.社員コード
			String empCode = dtl.getSWK_EMP_CODE();

			// 仕訳ジャーナル.取引先コード
			String triCode = dtl.getSWK_TRI_CODE();

			// 仕訳ジャーナル.管理1コード
			String knrCode1 = dtl.getSWK_KNR_CODE_1();

			// 仕訳ジャーナル.管理2コード
			String knrCode2 = dtl.getSWK_KNR_CODE_2();

			// 仕訳ジャーナル.管理3コード
			String knrCode3 = dtl.getSWK_KNR_CODE_3();

			// 仕訳ジャーナル.管理4コード
			String knrCode4 = dtl.getSWK_KNR_CODE_4();

			// 仕訳ジャーナル.管理5コード
			String knrCode5 = dtl.getSWK_KNR_CODE_5();

			// 仕訳ジャーナル.管理6コード
			String knrCode6 = dtl.getSWK_KNR_CODE_6();

			// ﾚｰﾄ
			BigDecimal curRate = dtl.getSWK_CUR_RATE();

			// 邦貨金額
			BigDecimal kin = dtl.getSWK_KIN();

			// 発生日
			Date hasDate = dtl.getHAS_DATE();

			// 計上会社コードがブランク
			if (Util.isNullOrEmpty(kKaiCode)) {
				// エラー情報の設定
				addError(NULL, K_KAI_CODE, count, "I00081", count, "C00570");
			}

			// 計上部門コードがブランク
			if (Util.isNullOrEmpty(depCode)) {
				// エラー情報の設定
				addError(NULL, DEP_CODE, count, "I00081", count, "C00571");
			}

			// 通貨コードがブランク
			if (Util.isNullOrEmpty(curCode)) {
				// エラー情報の設定
				addError(NULL, CUR_CODE, count, "I00081", count, "C00665");
			}

			// レートがブランク、または0
			if (curRate == null || curRate == new BigDecimal(0)) {
				// エラー情報の設定
				addError(NULL, RATE, count, "I00081", count, "C00556");
			}

			// 邦貨金額がブランク、または0
			if (kin == null || kin == new BigDecimal(0)) {
				// エラー情報の設定
				addError(NULL, BASE_AMOUNT, count, "I00081", count, "C00576");
			}

			// 発生日なし(発生日NULL可能の場合、必須チェック不要)
			if (!isAllowOccurDateBlank() && Util.isNullOrEmpty(hasDate)) {
				// エラー情報の設定
				addError(NULL, HAS_DATE, count, "I00081", count, "C00431");
			}

			// 科目
			Item kmkMst = null;

			// 補助科目
			SubItem hkmMst = null;

			// 内訳科目リスト
			DetailItem ukmMst = null;

			ItemManager itemMn = (ItemManager) getComponent(ItemManager.class);

			// 科目マスタ
			if (!Util.isNullOrEmpty(kmkCode)) {
				kmkMst = itemMn.getItem(kKaiCode, kmkCode, null, null);
			}

			// 補助科目マスタ
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode)) {
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, null);
				hkmMst = bean.getSubItem();
			}

			// 内訳科目マスタ
			if (!Util.isNullOrEmpty(kmkCode) && !Util.isNullOrEmpty(hkmCode) && !Util.isNullOrEmpty(ukmCode)) {
				Item bean = itemMn.getItem(kKaiCode, kmkCode, hkmCode, ukmCode);
				ukmMst = bean.getSubItem() != null ? bean.getSubItem().getDetailItem() : null;
			}

			// 科目コードがブランク
			if (Util.isNullOrEmpty(kmkCode)) {
				// エラー情報の設定
				addError(NULL, KMK_CODE, count, "I00082", count, conf.getItemName());
			}

			// a.科目マスタ.補助区分 = 1：あり AND 補助科目コードがブランク
			if (kmkMst != null) {
				if (kmkMst.hasSubItem() && Util.isNullOrEmpty(hkmCode)) {
					// エラー情報の設定
					addError(NULL, HKM_CODE, count, "I00082", count, conf.getSubItemName());
				}
			}

			// a.補助科目マスタ.内訳区分 = 1：あり AND 内訳科目コードがブランク
			if (conf.isUseDetailItem() && hkmMst != null) {
				if (hkmMst.hasDetailItem() && Util.isNullOrEmpty(ukmCode)) {
					// エラー情報の設定
					addError(NULL, UKM_CODE, count, "I00082", count, conf.getDetailItemName());
				}
			}

			// 参照先フラグ
			String itemFlg = "";

			// 各マスタ 検索条件詳細に従い、対象科目/補助/内訳コードのマスタデータを取得する。（開始日と終了日は指定しない）
			if (kmkMst != null && hkmMst != null && ukmMst != null) {

				// 科目マスタ.補助区分 = 0
				if (!kmkMst.hasSubItem()) {
					// 参照先は科目
					itemFlg = "0";

				} else if (kmkMst.hasSubItem() && !hkmMst.hasDetailItem()) {
					// 参照先は補助科目
					itemFlg = "1";

				} else {
					// 参照先は内訳科目
					itemFlg = "2";
				}

			} else if (kmkMst != null && hkmMst == null) {

				// 科目マスタ.補助区分 = 0
				if (!kmkMst.hasSubItem()) {
					// 参照先は科目
					itemFlg = "0";

				}
			} else if (kmkMst != null && hkmMst != null && ukmMst == null) {

				// 科目マスタ.補助区分 = 0
				if (!kmkMst.hasSubItem()) {
					// 参照先は科目
					itemFlg = "0";

				} else if (kmkMst.hasSubItem() && !hkmMst.hasDetailItem()) {
					// 参照先は補助科目
					itemFlg = "1";
				}
			}

			// 科目/補助/内訳の参照先が設定されていない場合、次のループへ
			if (Util.isNullOrEmpty(itemFlg)) {
				continue;
			}

			boolean mcrFlg = false; // 他通貨入力フラグ
			boolean uriZeiFlg = false; // 売上課税入力フラグ
			boolean sirZeiFlg = false;// 仕入課税入力フラグ
			boolean triCodeFlg = false; // 取引先入力フラグ
			boolean empCodeFlg = false; // 社員入力フラグ
			boolean knr1Flg = false; // 管理1入力フラグ
			boolean knr2Flg = false; // 管理2入力フラグ
			boolean knr3Flg = false; // 管理3入力フラグ
			boolean knr4Flg = false; // 管理4入力フラグ
			boolean knr5Flg = false; // 管理5入力フラグ
			boolean knr6Flg = false; // 管理6入力フラグ

			// 科目マスタ参照の場合
			if (itemFlg.equals("0")) {
				mcrFlg = kmkMst.isUseForeignCurrency(); // 他通貨入力フラグ
				uriZeiFlg = kmkMst.isUseSalesTaxation(); // 売上課税入力フラグ
				sirZeiFlg = kmkMst.isUsePurchaseTaxation();// 仕入課税入力フラグ
				triCodeFlg = kmkMst.isUseCustomer(); // 取引先入力フラグ
				empCodeFlg = kmkMst.isUseEmployee(); // 社員入力フラグ
				knr1Flg = kmkMst.isUseManagement1(); // 管理1入力フラグ
				knr2Flg = kmkMst.isUseManagement2(); // 管理2入力フラグ
				knr3Flg = kmkMst.isUseManagement3(); // 管理3入力フラグ
				knr4Flg = kmkMst.isUseManagement4(); // 管理4入力フラグ
				knr5Flg = kmkMst.isUseManagement5(); // 管理5入力フラグ
				knr6Flg = kmkMst.isUseManagement6(); // 管理6入力フラグ
			}

			// 補助科目マスタ参照の場合
			if (itemFlg.equals("1")) {
				mcrFlg = hkmMst.isUseForeignCurrency(); // 他通貨入力フラグ
				uriZeiFlg = hkmMst.isUseSalesTaxation(); // 売上課税入力フラグ
				sirZeiFlg = hkmMst.isUsePurchaseTaxation();// 仕入課税入力フラグ
				triCodeFlg = hkmMst.isUseCustomer(); // 取引先入力フラグ
				empCodeFlg = hkmMst.isUseEmployee(); // 社員入力フラグ
				knr1Flg = hkmMst.isUseManagement1(); // 管理1入力フラグ
				knr2Flg = hkmMst.isUseManagement2(); // 管理2入力フラグ
				knr3Flg = hkmMst.isUseManagement3(); // 管理3入力フラグ
				knr4Flg = hkmMst.isUseManagement4(); // 管理4入力フラグ
				knr5Flg = hkmMst.isUseManagement5(); // 管理5入力フラグ
				knr6Flg = hkmMst.isUseManagement6(); // 管理6入力フラグ
			}

			// 内訳科目マスタ参照の場合
			if (itemFlg.equals("2")) {
				mcrFlg = ukmMst.isUseForeignCurrency(); // 他通貨入力フラグ
				uriZeiFlg = ukmMst.isUseSalesTaxation(); // 売上課税入力フラグ
				sirZeiFlg = ukmMst.isUsePurchaseTaxation();// 仕入課税入力フラグ
				triCodeFlg = ukmMst.isUseCustomer(); // 取引先入力フラグ
				empCodeFlg = ukmMst.isUseEmployee(); // 社員入力フラグ
				knr1Flg = ukmMst.isUseManagement1(); // 管理1入力フラグ
				knr2Flg = ukmMst.isUseManagement2(); // 管理2入力フラグ
				knr3Flg = ukmMst.isUseManagement3(); // 管理3入力フラグ
				knr4Flg = ukmMst.isUseManagement4(); // 管理4入力フラグ
				knr5Flg = ukmMst.isUseManagement5(); // 管理5入力フラグ
				knr6Flg = ukmMst.isUseManagement6(); // 管理6入力フラグ
			}

			// インボイス専用。消費税に非適格請求書発行事業者フラグtrueの場合取引先コードtrue
			if (isInvoice && dtl.getTax() != null && Util.isNullOrEmpty(triCode)) {
				if (dtl.getTax().isNO_INV_REG_FLG()) {
					// エラー情報の設定
					String rowMsg = count + getWord("C04288"); // X行目
					addError(NULL, TRI_CODE, count, "I01125", "C00408", rowMsg);
				}
			}

			// 他通貨入力フラグ = 0:入力不可 AND 通貨コード <> 基軸通貨
			if (!mcrFlg && !curCode.equals(keyCurrency)) {
				// エラー情報の設定
				addError(NULL, CUR_CODE, count, "I00062", count);
			}

			// 売上課税入力フラグ = 1:入力可 OR 仕入課税入力フラグ = 1:入力可 AND 消費税コードがブランク
			if ((uriZeiFlg || sirZeiFlg) && Util.isNullOrEmpty(zeiCode)) {
				// エラー情報の設定
				addError(NULL, ZEI_CODE, count, "I00081", count, "C00573");

			}

			// 取引先入力フラグ = 1:入力必須 AND 取引先コードがブランク
			if (triCodeFlg && Util.isNullOrEmpty(triCode)) {
				// エラー情報の設定
				addError(NULL, TRI_CODE, count, "I00081", count, "C00786");
			}

			// 社員入力フラグ = 1:入力必須 AND 社員コードがブランク
			if (empCodeFlg && Util.isNullOrEmpty(empCode)) {
				// エラー情報の設定
				addError(NULL, EMP_CODE, count, "I00081", count, "C00697");
			}

			// 管理１～6を利用するかどうか
			boolean isUseMng1 = conf.isUseManagement1();
			boolean isUseMng2 = conf.isUseManagement2();
			boolean isUseMng3 = conf.isUseManagement3();
			boolean isUseMng4 = conf.isUseManagement4();
			boolean isUseMng5 = conf.isUseManagement5();
			boolean isUseMng6 = conf.isUseManagement6();

			// 管理１入力フラグ = 1:入力必須 AND 管理１コードがブランク
			if (isUseMng1 && knr1Flg && Util.isNullOrEmpty(knrCode1)) {
				// エラー情報の設定
				addError(NULL, KNR_CODE_1, count, "I00082", count, conf.getManagement1Name());
			}

			// 管理２入力フラグ = 1:入力必須 AND 管理２コードがブランク
			if (isUseMng2 && knr2Flg && Util.isNullOrEmpty(knrCode2)) {
				// エラー情報の設定
				addError(NULL, KNR_CODE_2, count, "I00082", count, conf.getManagement2Name());
			}

			// 管理３入力フラグ = 1:入力必須 AND 管理３コードがブランク
			if (isUseMng3 && knr3Flg && Util.isNullOrEmpty(knrCode3)) {
				// エラー情報の設定
				addError(NULL, KNR_CODE_3, count, "I00082", count, conf.getManagement3Name());
			}

			// 管理４入力フラグ = 1:入力必須 AND 管理４コードがブランク
			if (isUseMng4 && knr4Flg && Util.isNullOrEmpty(knrCode4)) {
				// エラー情報の設定
				addError(NULL, KNR_CODE_4, count, "I00082", count, conf.getManagement4Name());
			}

			// 管理５入力フラグ = 1:入力必須 AND 管理５コードがブランク
			if (isUseMng5 && knr5Flg && Util.isNullOrEmpty(knrCode5)) {
				// エラー情報の設定
				addError(NULL, KNR_CODE_5, count, "I00082", count, conf.getManagement5Name());
			}

			// 管理６入力フラグ = 1:入力必須 AND 管理６コードがブランク
			if (isUseMng6 && knr6Flg && Util.isNullOrEmpty(knrCode6)) {
				// エラー情報の設定
				addError(NULL, KNR_CODE_6, count, "I00082", count, conf.getManagement6Name());
			}
		}
	}

	/**
	 * エラー情報を設定する
	 * 
	 * @param errorType エラー種類
	 * @param dataType エラーデータタイプ
	 * @param rowNo 行番号
	 * @param messageID
	 * @param bindIds
	 */
	protected void addError(SlipError errorType, SlipError dataType, int rowNo, String messageID, Object... bindIds) {
		Message msg = new Message(messageID, bindIds);
		msg.setErrorType(errorType);
		msg.setDataType(dataType);
		msg.setRowNo(rowNo);

		errorList.add(msg);
	}

	/**
	 * 会社情報の取得
	 * 
	 * @param companyCode 会社コード
	 * @return 会社情報
	 * @throws TException
	 */
	protected Company getCompany(String companyCode) throws TException {
		CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);

		Company company = companys.get(companyCode);

		if (company == null) {
			company = manager.get(companyCode);
			companys.put(companyCode, company);
		}

		return company;
	}

	/**
	 * 対象外仕訳かどうか
	 * 
	 * @param dtl
	 * @return true:対象外仕訳
	 */
	protected boolean isSkip(SWK_DTL dtl) {
		return dtl.isErasing();
	}

	/**
	 * @return true:発生日ブランク可能
	 */
	protected boolean isAllowOccurDateBlank() {
		Company company = getCompany();
		return allowOccurDateBlank && !company.getAccountConfig().isUseIfrs();
	}

	/**
	 * インボイス用：伝票種別インボイスチェックするかどうか
	 * 
	 * @param hdr
	 * @return 伝票種別情報
	 * @throws TException
	 */
	protected boolean isSlipTypeInvoiceSys(SWK_HDR hdr) throws TException {

		SlipTypeManager manager = (SlipTypeManager) getComponent(SlipTypeManager.class);
		SlipType slipType = manager.get(getCompanyCode(), hdr.getSWK_DEN_SYU());
		if (slipType == null) {
			return false;
		}
		// 伝票種別債権且つ会社情報適格請求書発行事業者登録番号番号がなし
		if (Util.isNullOrEmpty(getCompany().getInvRegNo()) && "031".equals(hdr.getSWK_DEN_SYU())) {
			return false;
		}
		return slipType.isINV_SYS_FLG();
	}

}
