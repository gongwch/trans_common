package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;

/**
 * 科目体系名称マスタ一覧エクセル
 * 
 * @author AIS
 */
public class ItemOrganizationExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ItemOrganizationExcel(String lang) {
		super(lang);
	}

	/**
	 * 科目体系名称マスタ一覧をエクセルで返す
	 * 
	 * @param itemorgList 科目体系名称マスタ
	 * @return userList
	 * @throws TException
	 */
	public byte[] getExcel(List<ItemOrganization> itemorgList) throws TException {

		try {
			createReport(itemorgList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param ItemOrgList
	 */
	public void createReport(List<ItemOrganization> ItemOrgList) {

		// シート追加
		// 科目体系名称マスタ
		TExcelSheet sheet = addSheet(getWord("C00618") + getWord("C00500"));

		// カラム設定
		// 科目体系コード
		sheet.addColumn(getWord("C00617"), 4200);
		// 科目体系名称
		sheet.addColumn(getWord("C00618"), 8400);
		// 科目体系略称
		sheet.addColumn(getWord("C00619"), 8400);
		// 科目体系検索名称
		sheet.addColumn(getWord("C00620"), 8400);
		// 明細描画
		for (ItemOrganization bean : ItemOrgList) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
		}

	}
}
