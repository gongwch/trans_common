package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * 消費税マスタビジネスロジック
 * 
 * @author roh
 */
public interface ConsumptionTaxLogic extends CommonLogic {

	/**
	 * @param keys
	 * @return List
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * @param kaiCode
	 * @param zeiCode
	 * @param sName
	 * @param kName
	 * @param sales
	 * @param purChase
	 * @param elseTax
	 * @param termBasisDate
	 * @return List<Object>
	 */
	public abstract List<Object> refDailog(String kaiCode, String zeiCode, String sName, String kName, String sales,
		String purChase, String elseTax, Timestamp termBasisDate);

}
