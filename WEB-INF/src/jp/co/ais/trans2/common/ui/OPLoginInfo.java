package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * OPキャッシュ情報(一括取得及びチェック用)
 */
public class OPLoginInfo extends TransferBase implements Cloneable {

	/** クローン */
	@Override
	public OPLoginInfo clone() {
		try {
			return (OPLoginInfo) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** 結果データ:マップ */
	public Map<OPLoginDataType, OPLoginData> dataMap = new HashMap<OPLoginDataType, OPLoginData>();

	/** 結果データタイプ:マップ */
	public Map<OPLoginDataType, OPLoginResultType> resultMap = new HashMap<OPLoginDataType, OPLoginResultType>();

	/** タイムスタンプ:マップ */
	public Map<OPLoginDataType, Date> timestampMap = new HashMap<OPLoginDataType, Date>();

	/** 検索条件:マップ */
	public Map<OPLoginDataType, OPLoginCondition> conditionMap = new HashMap<OPLoginDataType, OPLoginCondition>();

	/** 判定用コードリスト */
	public Map<OPLoginDataType, List<String>> codeListMap = new HashMap<OPLoginDataType, List<String>>();

	/**
	 * @param type
	 * @return 結果データ
	 */
	public OPLoginData getData(OPLoginDataType type) {
		if (dataMap == null || type == null) {
			return null;
		}

		return dataMap.get(type);
	}

	/**
	 * 結果データ設定
	 * 
	 * @param type
	 * @param data
	 */
	public void setData(OPLoginDataType type, OPLoginData data) {
		if (type == null) {
			return;
		}

		dataMap.put(type, data);
	}

	/**
	 * @param type
	 * @return 結果データタイプ
	 */
	public OPLoginResultType getResultType(OPLoginDataType type) {
		if (resultMap == null || type == null) {
			return null;
		}

		return resultMap.get(type);
	}

	/**
	 * 結果データタイプ設定
	 * 
	 * @param type
	 * @param t
	 */
	public void setResultType(OPLoginDataType type, OPLoginResultType t) {
		if (type == null || t == null) {
			return;
		}

		resultMap.put(type, t);
	}

	/**
	 * @param type
	 * @return タイムスタンプ
	 */
	public Date getTimestamp(OPLoginDataType type) {
		if (timestampMap == null || type == null) {
			return null;
		}

		return timestampMap.get(type);
	}

	/**
	 * タイムスタンプ設定
	 * 
	 * @param type
	 * @param ts
	 */
	public void setTimestamp(OPLoginDataType type, Date ts) {
		if (type == null) {
			return;
		}

		timestampMap.put(type, ts);
	}

	/**
	 * @param type
	 * @return 検索条件
	 */
	public OPLoginCondition getCondition(OPLoginDataType type) {
		if (conditionMap == null || type == null) {
			return null;
		}

		return conditionMap.get(type);
	}

	/**
	 * 検索条件設定
	 * 
	 * @param type
	 * @param param
	 */
	public void setCondition(OPLoginDataType type, OPLoginCondition param) {
		if (type == null || param == null) {
			return;
		}

		conditionMap.put(type, param);
	}

	/**
	 * @param type
	 * @return 各種判定用コード(PK)リスト
	 */
	public List<String> getCodeList(OPLoginDataType type) {
		if (codeListMap == null || type == null) {
			return null;
		}

		return codeListMap.get(type);
	}

	/**
	 * 各種判定用コード(PK)リスト設定
	 * 
	 * @param type
	 * @param codeList
	 */
	public void setCodeList(OPLoginDataType type, List<String> codeList) {
		if (type == null || codeList == null) {
			return;
		}

		codeListMap.put(type, codeList);
	}
}
