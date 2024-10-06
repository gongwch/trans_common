package jp.co.ais.trans2.model.currency;

import java.io.*;
import java.util.*;

/**
 * レート一括取込インターフェイス
 */
public interface RateConverter {

	/**
	 * 取り込んだファイルをレートの情報にして返す
	 * 
	 * @param file
	 * @param companyCode
	 * @return List<Rate>
	 * @throws TErroneousRateDataException
	 */
	public List<Rate> toRateData(File file, String companyCode) throws TErroneousRateDataException;

}