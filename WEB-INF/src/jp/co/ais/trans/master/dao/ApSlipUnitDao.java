package jp.co.ais.trans.master.dao;

import java.util.List;
import jp.co.ais.trans.master.entity.*;

/**
 * APヘッダ + 明細結合Dao
 */
public interface ApSlipUnitDao {

	/**
	 * データ戻り値
	 */
	public Class BEAN = ApSlipUnit.class;

	/**
	 * AP仕訳ジャーナルヘッダを取得する
	 * 
	 * @param param
	 * @return List<ApSlipUnit> ※但しヘッダ情報のみ取得
	 */
	public List<ApSlipUnit> getApHdr(ApSlipParameter param);

	/**
	 * AP仕訳ジャーナルヘッダ + 明細情報を取得する
	 * 
	 * @param param
	 * @return List<ApSlipUnit>
	 */
	public List<ApSlipUnit> getApSlip(ApSlipParameter param);

	/**
	 * APパターンジャーナルヘッダを取得する
	 * 
	 * @param param
	 * @return List<ApSlipUnit> ※但しヘッダ情報のみ取得
	 */
	public List<ApSlipUnit> getApPtnHdr(ApSlipParameter param);

	/**
	 * APパターンジャーナルヘッダ + 明細情報を取得する
	 * 
	 * @param param
	 * @return List<ApSlipUnit>
	 */
	public List<ApSlipUnit> getApPtnSlip(ApSlipParameter param);

	/**
	 * 社員に紐付くAP仕訳ジャーナルヘッダのを取得する
	 * 
	 * @param param
	 * @return List<ApSlipUnit>
	 */
	public List<ApSlipUnit> getApEmpData(ApSlipParameter param);

}
