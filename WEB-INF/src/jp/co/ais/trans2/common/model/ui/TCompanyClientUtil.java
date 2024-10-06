package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社クライアントUtil
 */
public class TCompanyClientUtil {

	/**
	 * @param ctrl
	 * @param unit
	 * @return 会社コードリスト
	 * @throws TException
	 */
	public static List<String> getCodeList(TController ctrl, TCompanyOrganizationUnit unit) throws TException {

		if (unit == null) {
			return null;
		}

		CompanyOutputCondition param = unit.getCompanyOutputCondition();

		if (param == null) {
			return null;
		}

		if (Util.isNullOrEmpty(param.getOrganizationCode())) {
			// 組織コード未指定
			return null;
		}

		// 会社コードを取得
		List<String> companyCodeList = (List<String>) ctrl.request(CompanyOrganizationManager.class,
			"getCompanyCodeList", param);

		return companyCodeList;
	}

}
