package jp.co.ais.trans2.common.ui;

import java.util.*;
import java.util.Map.Entry;

import jp.co.ais.trans.common.log.*;

/**
 * マージクラス
 * 
 * @param <T>
 */
public class OPLoginMerge<T> {

	/** データ区分 */
	protected OPLoginDataType dataType;

	/**
	 * マージ処理
	 * 
	 * @param kbn
	 * @param bean
	 * @param list
	 * @return マージ後bean
	 */
	public OPLoginData merge(OPLoginDataType kbn, OPLoginData bean, Object list) {
		if (list == null) {
			return bean;
		}
		ClientLogger.debug("merge start:" + kbn);

		this.dataType = kbn;

		List<T> diffList = (List<T>) list;

		if (bean.getList() == null || bean.getList().isEmpty()) {
			// 1. beanにリストは持っていない場合に、差分リストは全て持つようにする
			bean.setList(diffList);
			return bean;
		}

		// 2. 自動ソートのマップを用意する
		Map<String, T> map = new TreeMap<String, T>();
		Map<String, T> diffMap = new TreeMap<String, T>();

		// 3. 既存のキーマップ
		for (int i = 0; i < bean.getList().size(); i++) {
			T e = (T) bean.getList().get(i);
			map.put(OPLoginDataType.getKey(dataType, e), e);
		}

		// 4.差分のキーマップ
		for (T e : diffList) {
			diffMap.put(OPLoginDataType.getKey(dataType, e), e);
		}

		// 5. キー単位で最新をプット
		for (Entry<String, T> entry : diffMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}

		// 6. Map to List
		List<T> newList = new ArrayList<T>(map.values());

		// 6.2
		// CODEマスタ、油種の場合にソート順訂正
		if (bean.getDataTypeValue() == OPLoginDataType.OP_CODE_MST.value //
			|| bean.getDataTypeValue() == OPLoginDataType.CM_BNKR_TYPE_MST.value) {
			// OP_CODE_MST, CM_BNKR_TYPE_MST
			// ソート順はDISP_ODR
			Collections.sort(newList, new OPLoginMergeComparator<T>());
		}

		// 7. 設定
		bean.setList(newList);

		ClientLogger.debug("merge finish:");

		return bean;

	}
}
