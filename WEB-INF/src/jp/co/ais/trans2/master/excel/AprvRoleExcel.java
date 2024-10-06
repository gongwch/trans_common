package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * 承認権限ロール一覧エクセル
 */
public class AprvRoleExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public AprvRoleExcel(String lang) {
		super(lang);
	}

	/**
	 * 承認権限ロール一覧をエクセルで返す
	 * 
	 * @param aprvRoleList 承認権限ロール一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<AprvRole> aprvRoleList) throws TException {

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
	public void createReport(List<AprvRole> aprvRoleList) {

		// シート追加
		// 承認権限ロールマスタ
		TExcelSheet sheet = addSheet(getWord("C11940")); // 承認権限ロールマスタ

		// カラム設定
		// ロールコード
		sheet.addColumn(getWord("C11154"), 4200);
		// ロール名称
		sheet.addColumn(getWord("C11155"), 7000);
		// ロール略称
		sheet.addColumn(getWord("C11156"), 6000);
		// ロール検索名称
		sheet.addColumn(getWord("C11157"), 7000);
		// 開始年月日
		sheet.addColumn(getWord("COP706"), 5500);
		// 終了年月日
		sheet.addColumn(getWord("COP707"), 5500);

		// 明細描画
		for (AprvRole role : aprvRoleList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(role.getAPRV_ROLE_CODE());
			newRow.addCell(role.getAPRV_ROLE_NAME());
			newRow.addCell(role.getAPRV_ROLE_NAME_S());
			newRow.addCell(role.getAPRV_ROLE_NAME_K());
			newRow.addCell(DateUtil.toYMDString(role.getSTR_DATE()).toString(), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(role.getEND_DATE()).toString(), SwingConstants.CENTER);
		}

	}
}
