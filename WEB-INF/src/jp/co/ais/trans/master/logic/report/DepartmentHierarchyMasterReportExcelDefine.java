package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 部門階層マスタのExcel定義クラス
 */
public class DepartmentHierarchyMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0050";
	}

	public String getSheetName() {
		// 部門階層マスタを返す
		return "C02327";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00335", "C00698", "C01739", "C00991", "C01751", "C01752", "C01753", "C01754",
				"C01755", "C01756", "C01757", "C01758", "C01759", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 0, 10, 1, 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 0 };
	}

	public List convertDataToList(Object dto, String langCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		// 部門マスタDao 部門コード->名称変換に使用する。
		BMN_MSTDao bmnMstDao = (BMN_MSTDao) container.getComponent(BMN_MSTDao.class);

		DPK_MST entity = (DPK_MST) dto;
		List list = new ArrayList();

		// 会社コード
		String kaiCode = entity.getKAI_CODE();
		list.add(kaiCode);
		// 組織コード
		list.add(entity.getDPK_SSK());
		// 部門コード
		list.add(entity.getDPK_DEP_CODE());
		// レベル
		int dpkLvl = entity.getDPK_LVL();
		list.add(dpkLvl);
		// 管理部門コードレベル０
		list.add("");
		// 管理部門コードレベル１
		list.add(dpkLvl != 1 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_1())));
		// 管理部門コードレベル２
		list.add(dpkLvl != 2 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_2())));
		// 管理部門コードレベル３
		list.add(dpkLvl != 3 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_3())));
		// 管理部門コードレベル４
		list.add(dpkLvl != 4 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_4())));
		// 管理部門コードレベル５
		list.add(dpkLvl != 5 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_5())));
		// 管理部門コードレベル６
		list.add(dpkLvl != 6 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_6())));
		// 管理部門コードレベル７
		list.add(dpkLvl != 7 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_7())));
		// 管理部門コードレベル８
		list.add(dpkLvl != 8 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_8())));
		// 管理部門コードレベル９
		list.add(dpkLvl != 9 ? "" : Util.avoidNull(convCodeToName(bmnMstDao, kaiCode, entity.getDPK_LVL_9())));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}

	/**
	 * 部門マスタ 部門コード->名称変換
	 * 
	 * @param dao
	 * @param kaiCode
	 * @param depCode
	 * @return String
	 */
	protected String convCodeToName(BMN_MSTDao dao, String kaiCode, String depCode) {

		if (dao == null) {
			return null;
		}
		BMN_MST entity = dao.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);
		if (entity == null) {
			return null;
		}

		if (entity.getDEP_NAME() != null) {
			return entity.getDEP_NAME();
		}

		return entity.getDEP_CODE();
	}
}
