package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 科目情報インターフェース。
 * 
 * @author AIS
 */
public interface ItemManager {

	/**
	 * 指定条件に該当する科目情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する科目情報
	 * @throws TException
	 */
	public List<Item> get(ItemSearchCondition condition) throws TException;

	/**
	 * 科目情報を登録する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void entry(Item item) throws TException;

	/**
	 * 科目情報を修正する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modify(Item item) throws TException;

	/**
	 * 科目情報を削除する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void delete(Item item) throws TException;

	/**
	 * エクセル
	 * 
	 * @param condition
	 * @return 科目情報
	 * @throws TException
	 */
	public byte[] getExcel(ItemSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する科目体系情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する科目体系情報
	 * @throws TException
	 */
	public List<ItemOrganization> getItemOrganization(ItemOrganizationSearchCondition condition) throws TException;

	/**
	 * @param bean 検索条件
	 * @throws TException
	 */
	public void entryItemOrganization(ItemOrganization bean) throws TException;

	/**
	 * @param bean 検索条件
	 * @throws TException
	 */
	public void modifyItemOrganization(ItemOrganization bean) throws TException;

	/**
	 * 科目体系名称マスタ情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteItemOrganization(ItemOrganization bean) throws TException;

	/**
	 * 科目体系名称マスタ一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の科目体系名称マスタ一覧
	 * @throws TException
	 */
	public byte[] getExcelItemOrganization(ItemOrganizationSearchCondition condition) throws TException;

	/**
	 * 基本科目体系情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @return 基本科目体系情報
	 * @throws TException
	 */
	public ItemOrganization getBaseItemOrganization(String companyCode) throws TException;

	/**
	 * 指定条件に該当する補助科目情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する補助科目情報
	 * @throws TException
	 */
	public List<Item> get(SubItemSearchCondition condition) throws TException;

	/**
	 * 補助科目情報を登録する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void entrySubItem(Item item) throws TException;

	/**
	 * 補助科目情報を修正する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modifySubItem(Item item) throws TException;

	/**
	 * 補助科目情報を削除する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void deleteSubItem(Item item) throws TException;

	/**
	 * hjokamoku 補助科目のエクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getSubItemExcel(SubItemSearchCondition condition) throws TException;

	/**
	 * 内訳科目情報を登録する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void entryDetailItem(Item item) throws TException;

	/**
	 * 内訳科目情報を修正する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void modifyDetailItem(Item item) throws TException;

	/**
	 * 内訳科目情報を削除する。
	 * 
	 * @param item
	 * @throws TException
	 */
	public void deleteDetailItem(Item item) throws TException;

	/**
	 * 内訳科目のエクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getDetailItemExcel(DetailItemSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する内訳科目情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する内訳科目情報
	 * @throws TException
	 */
	public List<Item> get(DetailItemSearchCondition condition) throws TException;

	/**
	 * 科目情報を返す
	 * 
	 * @param company 会社コード
	 * @param item 科目コード
	 * @param sub 補助科目コード
	 * @param detail 内訳科目コード
	 * @return 指定条件に該当する科目情報
	 * @throws TException
	 */
	public Item getItem(String company, String item, String sub, String detail) throws TException;

}
