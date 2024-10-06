package jp.co.ais.trans2.model.slip.parts;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票チェッカー
 */
public class SlipChecker extends TModel {

	/** ヘッダチェッカー */
	protected SlipHeaderChecker hchecker;

	/** 明細チェッカー */
	protected SlipDetailChecker dchecker;

	/**
	 * 伝票チェック
	 * 
	 * @param slip 伝票
	 * @return メッセージ
	 * @throws TException
	 */
	public boolean check(Slip slip) throws TException {
		List<Slip> list = new ArrayList<Slip>(1);
		list.add(slip);
		return check(list);
	}

	/**
	 * 伝票チェック
	 * 
	 * @param slipList 伝票
	 * @return メッセージ
	 * @throws TException
	 */
	public boolean check(List<Slip> slipList) throws TException {
		hchecker = getHeaderChecker();
		dchecker = getDetailChecker();

		hchecker.check(slipList);
		dchecker.check(slipList);

		return hchecker.getErrorList().isEmpty() && dchecker.getErrorList().isEmpty();
	}

	/**
	 * ヘッダチェッカー
	 * 
	 * @return ヘッダチェッカー
	 */
	protected SlipHeaderChecker getHeaderChecker() {
		return (SlipHeaderChecker) getComponent(SlipHeaderChecker.class);
	}

	/**
	 * 明細チェッカー
	 * 
	 * @return 明細チェッカー
	 */
	protected SlipDetailChecker getDetailChecker() {
		return (SlipDetailChecker) getComponent(SlipDetailChecker.class);
	}

	/**
	 * 伝票エラーをメッセージに変換する
	 * 
	 * @return メッセージ
	 */
	public List<Message> getMessages() {
		List<SlipHeaderError> hdrList = hchecker.getErrorList();
		List<SlipDetailError> dtlList = dchecker.getErrorList();

		List<Message> list = new ArrayList<Message>(hdrList.size() + dtlList.size());

		// ヘッダ
		for (SlipHeaderError err : hdrList) {
			Message msg = convertHeader(err);
			list.add(msg);
		}

		// 明細
		for (SlipDetailError err : dtlList) {
			Message msg = convertDetail(err);
			msg.setSubMessageID(err.getRowNo() + getWord("C04288")); // 行目

			list.add(msg);
		}

		return list;
	}

	/**
	 * ヘッダエラーをメッセージに変換する
	 * 
	 * @param err ヘッダエラー
	 * @return メッセージ
	 */
	protected Message convertHeader(SlipHeaderError err) {

		SWK_HDR hdr = err.getHeader();

		String id = "";
		List<Object> binds = new LinkedList<Object>();

		String company = hdr.getKAI_CODE();
		String word = err.getDataWord();
		String value = err.getValue();

		switch (err.getErrorType()) {
			case CLOSED_SLIP_DATE:
				// 指定の日付[{0}] は計上会社先[{1}]で既に締められています。
				id = "I00348";
				binds.add(value);
				binds.add(company);
				break;

			case NULL:
				// 会社[{0}] {1}が設定されていません。
				id = "I00349";
				binds.add(company);
				binds.add(word);
				break;

			case NULL_ON_ITEM:
				// 科目に必要な{0}が不足しています。会社[{0}] 科目[{1}]
				id = "I00350";
				binds.add(word);
				binds.add(company);
				binds.add(hdr.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {
					// 科目に必要な{0}が不足しています。会社[{0}] 科目[{1}] 補助[{2}]
					id = "I00351";
					binds.add(hdr.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {
						// 科目に必要な{0}が不足しています。会社[{0}] 科目[{1}] 補助[{2}] 内訳[{3}]
						id = "I00352";
						binds.add(hdr.getSWK_UKM_CODE());
					}
				}

				break;

			case NOT_FOUND:

				switch (err.getDataType()) {
					case PAY_SETTING: // 取引先支払条件
						// 会社[XX] 取引先[XX] 支払条件[XX]が見つかりませんでした。
						id = "I00354";
						binds.add(company);
						binds.add("C00408"); // 取引先
						binds.add(hdr.getSWK_TRI_CODE());
						binds.add("C00238"); // 支払条件
						binds.add(hdr.getSWK_TJK_CODE());

						break;

					case SUB_ITEM: // 補助
						// 会社[XX] 科目[XX] 補助[XX]が見つかりませんでした。
						id = "I00354";
						binds.add(company);
						binds.add("C00077"); // 科目
						binds.add(hdr.getSWK_KMK_CODE());
						binds.add("C00488"); // 補助
						binds.add(hdr.getSWK_HKM_CODE());

						break;

					case DETAIL_ITEM: // 内訳
						// 会社[XX] 科目[XX] 補助[XX] 内訳[XX]が見つかりませんでした。
						id = "I00355";
						binds.add(company);
						binds.add("C00077"); // 科目
						binds.add(hdr.getSWK_KMK_CODE());
						binds.add("C00488"); // 補助
						binds.add(hdr.getSWK_HKM_CODE());
						binds.add("C00025"); // 内訳
						binds.add(hdr.getSWK_UKM_CODE());

						break;

					default:
						// 会社[{0}] {1}[{2}]が見つかりませんでした。
						id = "I00353";
						binds.add(company);
						binds.add(word);
						binds.add(value);

						break;
				}

				break;

			case ITEM_FIXED_OUT:
				// 会社[XX] 科目[XX]は部門XX]で利用できません。
				id = "I00356";
				binds.add(company);
				binds.add("C00077"); // 科目
				binds.add(hdr.getSWK_KMK_CODE());
				binds.add("C00467"); // 部門
				binds.add(hdr.getSWK_DEP_CODE());

				break;

			case TERM_OUT:
				// 会社[{0}] {1}[{2}]は有効期間から外れています。
				id = "I00357";
				binds.add(company);
				binds.add(word);
				binds.add(value);

				break;

			case NOT_KEY_CURRENCY:
				// 多通貨禁止の科目に基軸通貨以外が指定してあります。会社[{0}] 通貨[{1}] 科目[{2}]
				id = "I00360";
				binds.add(company);
				binds.add(hdr.getSWK_CUR_CODE());
				binds.add(hdr.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {
					// 多通貨禁止の科目に基軸通貨以外が指定してあります。会社[{0}] 通貨[{1}] 科目[{2}] 補助[{3}]
					id = "I00361";
					binds.add(hdr.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {
						// 多通貨禁止の科目に基軸通貨以外が指定してあります。会社[{0}] 通貨[{1}] 科目[{2}] 補助[{3}] 内訳[{4}]
						id = "I00362";
						binds.add(hdr.getSWK_UKM_CODE());
					}
				}

				break;

			case EMPTY_DETAIL:
				// 明細行がありません。
				id = "I00363";

				break;

			case NONE_OWN_DETAIL:
				// 明細に自社の計上会社が指定されていません。
				id = "I00364";

				break;

			case UNBALANCE_AMOUNT:
				// 貸借がバランスしていません。
				id = "I00136";

				break;
		}

		Message error = new Message(id, binds.toArray(new Object[binds.size()]));
		error.setErrorType(err.getErrorType());
		error.setDataType(err.getDataType());
		return error;
	}

	/**
	 * 明細エラーをメッセージに変換する
	 * 
	 * @param err 明細エラー
	 * @return メッセージ
	 */
	protected Message convertDetail(SlipDetailError err) {
		SWK_DTL dtl = err.getDetail();

		String id = "";
		List<Object> binds = new LinkedList<Object>();

		String company = dtl != null ? dtl.getSWK_K_KAI_CODE() : "";
		String word = err.getDataWord();
		String value = err.getValue();

		switch (err.getErrorType()) {
			case CLOSED_SLIP_DATE:
				// 指定の日付[{0}]は計上会社先[{1}]で既に締められています。
				id = "I00348";
				binds.add(value);
				binds.add(company);
				break;

			case NULL:
				// 会社[{0}] {1}が設定されていません。
				id = "I00349";
				binds.add(company);
				binds.add(word);
				binds.add(value);
				break;

			case NULL_ON_ITEM:
				// 科目に必要な項目[{0}]が不足しています。会社[{1}] 科目[{2}]
				id = "I00350";
				binds.add(word);
				binds.add(company);
				binds.add(dtl.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					// 科目に必要な項目[{0}]が不足しています。会社[{1}] 科目[{2}] 補助[{3}]
					id = "I00351";
					binds.add(dtl.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
						// 科目に必要な項目[{0}]が不足しています。会社[{1}] 科目[{2}] 補助[{3}] 内訳[{4}]
						id = "I00352";
						binds.add(dtl.getSWK_UKM_CODE());
					}
				}

				break;

			case NOT_FOUND:
				switch (err.getDataType()) {
					case SUB_ITEM: // 補助
						// 会社[{0}] 科目[{1}] 補助[{2}]が見つかりませんでした。
						id = "I00354";
						binds.add(company);
						binds.add("C00077"); // 科目
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // 補助
						binds.add(dtl.getSWK_HKM_CODE());

						break;

					case DETAIL_ITEM: // 内訳
						// 会社[{0}] 科目[{1}] 補助[{2}] 内訳[{3}]が見つかりませんでした。
						id = "I00355";
						binds.add(company);
						binds.add("C00077"); // 科目
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // 補助
						binds.add(dtl.getSWK_HKM_CODE());
						binds.add("C00025"); // 内訳
						binds.add(dtl.getSWK_UKM_CODE());

						break;

					default:
						// 会社[{0}] {1}[{2}]が見つかりませんでした。
						id = "I00353";
						binds.add(company);
						binds.add(word);
						binds.add(value);

						break;
				}

				break;

			case ITEM_FIXED_OUT:
				// 会社[XX] 科目[XX]は部門XX]で利用できません。
				id = "I00356";
				binds.add(company);
				binds.add("C00077"); // 科目
				binds.add(dtl.getSWK_KMK_CODE());
				binds.add("C00467"); // 部門
				binds.add(dtl.getSWK_DEP_CODE());

				break;

			case TERM_OUT:
				switch (err.getDataType()) {
					case SUB_ITEM: // 補助
						// 会社[XX] 科目[XX] 補助[XX]は有効期間から外れています。
						id = "I00358";
						binds.add(company);
						binds.add("C00077"); // 科目
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // 補助
						binds.add(dtl.getSWK_HKM_CODE());

						break;

					case DETAIL_ITEM: // 内訳
						// 会社[XX] 科目[XX] 補助[XX] 内訳[XX]は有効期間から外れています。
						id = "I00359";
						binds.add(company);
						binds.add("C00077"); // 科目
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // 補助
						binds.add(dtl.getSWK_HKM_CODE());
						binds.add("C00025"); // 内訳
						binds.add(dtl.getSWK_UKM_CODE());

						break;

					default:
						// 会社[{0}] {1}[{2}]は有効期間から外れています。
						id = "I00357";
						binds.add(company);
						binds.add(word);
						binds.add(value);
						break;
				}

				break;

			case NOT_KEY_CURRENCY:
				// 多通貨禁止の科目に基軸通貨以外が指定してあります。会社[{0}] 通貨[{1}] 科目[{2}]
				id = "I00360";
				binds.add(company);
				binds.add(dtl.getSWK_CUR_CODE());
				binds.add(dtl.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					// 多通貨禁止の科目に基軸通貨以外が指定してあります。会社[{0}] 通貨[{1}] 科目[{2}] 補助[{3}]
					id = "I00361";
					binds.add(dtl.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
						// 多通貨禁止の科目に基軸通貨以外が指定してあります。会社[{0}] 通貨[{1}] 科目[{2}] 補助[{3}] 内訳[{4}]
						id = "I00362";
						binds.add(dtl.getSWK_UKM_CODE());
					}
				}

				break;
		}

		Message error = new Message(id, binds.toArray(new Object[binds.size()]));
		error.setErrorType(err.getErrorType());
		error.setDataType(err.getDataType());
		return error;
	}
}
