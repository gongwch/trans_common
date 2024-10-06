package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * 承認権限ロールグループ一覧エクセル
 */
public class AprvRoleGroupExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public AprvRoleGroupExcel(String lang) {
		super(lang);
	}

	/**
	 * 承認権限ロールグループ一覧をエクセルで返す
	 * 
	 * @param aprvRoleList 承認権限ロールグループ一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<AprvRoleGroup> aprvRoleList) throws TException {

		try {
			createReport(aprvRoleList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param aprvRoleList
	 */
	public void createReport(List<AprvRoleGroup> aprvRoleList) {

		// シート追加
		// 承認権限ロールマスタ
		TExcelSheet sheet = addSheet(getWord("C11940")); // 承認権限ロールマスタ

		// カラム設定
		sheet.addColumn(getWord("C12230"), 4200); // 承認グループコード
		sheet.addColumn(getWord("C12231"), 7000); // 承認グループ名称
		sheet.addColumn(getWord("C12232"), 6000); // 承認グループ略称
		sheet.addColumn(getWord("C12233"), 7000); // 承認グループ検索名称
		sheet.addColumn(getWord("C11154"), 4200); // ロールコード
		sheet.addColumn(getWord("C11155"), 7000); // ロール名称
		sheet.addColumn(getWord("C01739"), 4200); // レベル
		sheet.addColumn(getWord("COP706"), 5500); // 開始年月日
		sheet.addColumn(getWord("COP707"), 5500); // 終了年月日

		// 明細描画
		for (AprvRoleGroup grp : aprvRoleList) {
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {

				TExcelRow newRow = sheet.addRow();
				newRow.addCell(grp.getAPRV_ROLE_GRP_CODE());
				newRow.addCell(grp.getAPRV_ROLE_GRP_NAME());
				newRow.addCell(grp.getAPRV_ROLE_GRP_NAME_S());
				newRow.addCell(grp.getAPRV_ROLE_GRP_NAME_K());
				newRow.addCell(dtl.getAPRV_ROLE_CODE());
				newRow.addCell(dtl.getAPRV_ROLE_NAME());
				newRow.addCell(dtl.getAPRV_LEVEL());
				newRow.addCell(DateUtil.toYMDString(grp.getSTR_DATE()).toString(), SwingConstants.CENTER);
				newRow.addCell(DateUtil.toYMDString(grp.getEND_DATE()).toString(), SwingConstants.CENTER);
			}

		}

	}
}
