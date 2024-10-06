package jp.co.ais.trans2.model.bank;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * 銀行統廃合実装クラス
 */
public class BankMergeManagerImpl extends TModel implements BankMergeManager {

	/**
	 * @return list
	 * @throws TException
	 */
	public List<BankMerge> get() throws TException {

		Connection conn = DBUtil.getConnection();

		List<BankMerge> list = new ArrayList<BankMerge>();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("SELECT distinct");
			sql.add("   mer.OLD_BNK_CODE,");
			sql.add("   mer.OLD_BNK_STN_CODE,");
			sql.add("   bnk.BNK_NAME_S,");
			sql.add("   mer.BNK_MER_CODE,");
			sql.add("   mer.BNK_MER_NAME,");
			sql.add("   mer.BNK_MER_KANA_FB,");
			sql.add("   mer.BNK_MER_KANA,");
			sql.add("   mer.BNK_MER_STN_CODE,");
			sql.add("   mer.BNK_MER_STN_NAME,");
			sql.add("   mer.BNK_MER_STN_KANA_FB,");
			sql.add("   mer.BNK_MER_STN_KANA,");
			sql.add("   mer.STR_DATE,");
			sql.add("   mer.END_DATE");
			sql.add("FROM BNK_MER_DAT mer");
			sql.add("  LEFT OUTER JOIN BNK_MST bnk");
			sql.add("    ON mer.OLD_BNK_CODE = bnk.BNK_CODE");
			sql.add("WHERE BNK_MER_KOS_KBN = 0");
			sql.add("ORDER BY mer.BNK_MER_NAME");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected BankMerge mapping(ResultSet rs) throws Exception {
		BankMerge bean = new BankMerge();

		bean.setOldBankCode(rs.getString("OLD_BNK_CODE"));
		bean.setOldBankOffCode(rs.getString("OLD_BNK_STN_CODE"));
		bean.setOldBankName(rs.getString("BNK_NAME_S"));
		bean.setNewBankCode(rs.getString("BNK_MER_CODE"));
		bean.setNewBankName(rs.getString("BNK_MER_NAME"));
		bean.setNewBankKanaFb(rs.getString("BNK_MER_KANA_FB"));
		bean.setNewBankKana(rs.getString("BNK_MER_KANA"));
		bean.setNewBankOffCode(rs.getString("BNK_MER_STN_CODE"));
		bean.setNewBankOffName(rs.getString("BNK_MER_STN_NAME"));
		bean.setNewBankOffKanaFb(rs.getString("BNK_MER_STN_KANA_FB"));
		bean.setNewBankOffKana(rs.getString("BNK_MER_STN_KANA"));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		return bean;

	}


	/**
	 * @see jp.co.ais.trans2.model.bank.BankMergeManager#entry(jp.co.ais.trans2.model.bank.BankMerge, int)
	 */
	@Override
	public void entry(BankMerge bankMe, int i) throws TException {
		Connection conn = DBUtil.getConnection();
		SQLCreator sql = new SQLCreator();
		
		try {
			
			if(i == 0){
				sql = new SQLCreator();
				sql.add("DELETE FROM BNK_MER_DAT");
				DBUtil.execute(conn, sql);
			}
			

			
			boolean isOldBankCode = !Util.isNullOrEmpty(bankMe.getOldBankCode());
			boolean isOldBankOffCode = !Util.isNullOrEmpty(bankMe.getOldBankOffCode());

			
			sql = new SQLCreator();
			sql.add("INSERT INTO BNK_MER_DAT (");

			if (isOldBankCode) {
				sql.add("   OLD_BNK_CODE,");
			}

			if (isOldBankOffCode) {
				sql.add("   OLD_BNK_STN_CODE,");
			}

			sql.add("   BNK_MER_CODE,");
			sql.add("   BNK_MER_NAME,");
			sql.add("   BNK_MER_KANA_FB,");
			sql.add("   BNK_MER_KANA,");
			sql.add("   BNK_MER_STN_CODE,");
			sql.add("   BNK_MER_STN_NAME,");
			sql.add("   BNK_MER_STN_KANA,");
			sql.add("   BNK_MER_STN_KANA_FB,");
			sql.add("   STR_DATE,");
			sql.add("   END_DATE,");
			sql.add("   BNK_MER_KOS_KBN");
			sql.add(") VALUES (");

			if (isOldBankCode) {

				sql.add("?,", bankMe.getOldBankCode());
			}

			if (isOldBankOffCode) {
				
				sql.add("?,", bankMe.getOldBankOffCode());
			}
			sql.add("?,", bankMe.getNewBankCode());
			sql.add("?,", bankMe.getNewBankName());
			sql.add("?,", bankMe.getNewBankKanaFb());
			sql.add("?,", bankMe.getNewBankKana());
			sql.add("?,", bankMe.getNewBankOffCode());
			sql.add("?,", bankMe.getNewBankOffName());
			sql.add("?,", bankMe.getNewBankOffKana());
			sql.add("?,", bankMe.getNewBankOffKanaFb());
			sql.add("?,", bankMe.getDateFrom());
			sql.add("?,", bankMe.getDateTo());
			sql.add("?", 0);
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}


	/**
	 * @see jp.co.ais.trans2.model.bank.BankMergeManager#updata(java.util.List)
	 */
	public void updata(List<BankMerge> bankMe) throws TException {
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			for (int i = 0; i < bankMe.size(); i++) {

				
				// 新銀行、支店コードが存在する場合
				if (!Util.isNullOrEmpty(bankMe.get(i).getNewBankCode())
					&& !Util.isNullOrEmpty(bankMe.get(i).getNewBankOffCode())) {
					// 銀行マスタ削除


					sql = new SQLCreator();
					sql.add("DELETE FROM BNK_MST");
					sql.add(" WHERE BNK_CODE = ?", bankMe.get(i).getOldBankCode());

					DBUtil.execute(conn, sql);

					// 銀行マスタにデータ追加
					sql = new SQLCreator();
					sql.add("INSERT INTO BNK_MST ");
					sql.add("  SELECT ");
					sql.add("    BNK_MER_CODE,");
					sql.add("    BNK_MER_STN_CODE,");
					sql.add("    BNK_MER_NAME,");
					sql.add("    BNK_MER_KANA_FB,");
					sql.add("    BNK_MER_KANA,");
					sql.add("    BNK_MER_STN_NAME,");
					sql.add("    BNK_MER_STN_KANA_FB,");
					sql.add("    BNK_MER_STN_KANA,");
					sql.add("    STR_DATE,");
					sql.add("    END_DATE,");
					sql.addYMDHMS("    ?,", getNow());
					sql.add("    ?,", Util.avoidNull(null));
					sql.add("    ?,", getProgramCode());
					sql.add("    ?", getUserCode());
					sql.add("  FROM BNK_MER_DAT");

					DBUtil.execute(conn, sql);

					if (!Util.isNullOrEmpty(bankMe.get(i).getOldBankCode())
						&& !Util.isNullOrEmpty(bankMe.get(i).getOldBankOffCode())) {
					
						
						// 銀行口座マスタ更新
						sql = new SQLCreator();
						sql.add("UPDATE AP_CBK_MST");
						sql.add("  SET (CBK_BNK_CODE, CBK_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN AP_CBK_MST cbk");
						sql.add("          ON cbk.CBK_BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND cbk.CBK_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE cbk.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE CBK_BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

						// 社員マスタ更新
						sql = new SQLCreator();
						sql.add("UPDATE EMP_MST");
						sql.add("  SET (EMP_FURI_BNK_CODE, EMP_FURI_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN EMP_MST emp");
						sql.add("          ON emp.EMP_FURI_BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND emp.EMP_FURI_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE emp.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE EMP_FURI_BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

						// 取引先支払条件マスタ更新
						sql = new SQLCreator();
						sql.add("UPDATE TRI_SJ_MST");
						sql.add("  SET (BNK_CODE, BNK_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN TRI_SJ_MST tri");
						sql.add("          ON tri.BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND tri.BNK_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE tri.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

						// 社外振込データ更新
						sql = new SQLCreator();
						sql.add("UPDATE AP_HFR_DAT");
						sql.add("  SET (HFR_FURI_BNK_CODE, HFR_FURI_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN AP_HFR_DAT hfr");
						sql.add("          ON hfr.HFR_FURI_BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND hfr.HFR_FURI_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE hfr.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE HFR_FURI_BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

						// 支払集計データ更新
						sql = new SQLCreator();
						sql.add("UPDATE AP_HSK_DAT");
						sql.add("  SET (HSK_FURI_BNK_CODE, HSK_FURI_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN AP_HSK_DAT hsk");
						sql.add("          ON hsk.HSK_FURI_BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND hsk.HSK_FURI_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE hsk.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE HSK_FURI_BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

						// 支払手形データ更新
						sql = new SQLCreator();
						sql.add("UPDATE AP_HTG_DAT");
						sql.add("  SET (HTG_FURI_BNK_CODE, HTG_FURI_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN AP_HTG_DAT htg");
						sql.add("          ON htg.HTG_FURI_BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND htg.HTG_FURI_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE htg.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE HTG_FURI_BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

						// 社員振込データ更新
						sql = new SQLCreator();
						sql.add("UPDATE AP_SFR_DAT");
						sql.add("  SET (SFR_FURI_BNK_CODE, SFR_FURI_STN_CODE) = (");
						sql.add(" 	  SELECT distinct");
						sql.add("         mer.BNK_MER_CODE,");
						sql.add("         mer.BNK_MER_STN_CODE");
						sql.add("     FROM BNK_MER_DAT mer");
						sql.add("       INNER JOIN AP_SFR_DAT sfr");
						sql.add("          ON sfr.SFR_FURI_BNK_CODE = mer.OLD_BNK_CODE");
						sql.add("          AND sfr.SFR_FURI_STN_CODE = mer.OLD_BNK_STN_CODE");
						sql.add("     WHERE sfr.KAI_CODE = ?", getCompanyCode());
						sql.add("     AND mer.OLD_BNK_CODE IS NOT NULL");
						sql.add("     AND mer.OLD_BNK_STN_CODE IS NOT NULL ");
						sql.add("     )");
						sql.add("WHERE SFR_FURI_BNK_CODE = ?", bankMe.get(i).getOldBankCode()  );

						DBUtil.execute(conn, sql);

					}

				} else {

					// 旧銀行、支店コードだけある場合
					sql = new SQLCreator();
					sql.add("UPDATE BNK_MST");
					sql.add("   SET END_DATE = (");
					sql.add("	  SELECT distinct");
					sql.add("        mer.END_DATE");
					sql.add("     FROM BNK_MER_DAT mer");
					sql.add("        INNER JOIN BNK_MST bnk");
					sql.add("          ON bnk.BNK_CODE = mer.OLD_BNK_CODE");
					sql.add("          AND bnk.BNK_STN_CODE = mer.OLD_BNK_STN_CODE");
					sql.add("     WHERE BNK_CODE =?", bankMe.get(0).getOldBankCode());
					sql.add(")");
					DBUtil.execute(conn, sql);

				}

				sql = new SQLCreator();

				sql.add("UPDATE BNK_MER_DAT");
				sql.add("  SET BNK_MER_KOS_KBN = 1");
				sql.add("  WHERE BNK_MER_CODE = ? ", bankMe.get(i).getNewBankCode());
				sql.add("  OR OLD_BNK_CODE = ?", bankMe.get(i).getOldBankCode());

				DBUtil.execute(conn, sql);

			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);

		}

	}

	

	/**
	 * @see jp.co.ais.trans2.model.bank.BankMergeManager#seachcount(java.util.List)
	 */
	@Override
	public List<BankMerge> seachcount(List<BankMerge> bankMe) throws TException {
		
		Connection conn = DBUtil.getConnection();
		
		List<BankMerge> searchList = new ArrayList<BankMerge>();
			
			try {
				int[] renewCount = {0,0,0,0,0,0,0,0};
				int[] addCount = {0,0,0,0,0,0,0,0};
				
				SQLCreator sql = new SQLCreator();

				//銀行マスタCount
				if(Util.isNullOrEmpty(bankMe.get(0).getOldBankCode())){
					sql.add("SELECT COUNT(*) FROM BNK_MER_DAT");
					sql.add("WHERE BNK_MER_CODE =?",bankMe.get(0).getNewBankCode());
					
					String bankAdd = DBUtil.selectOne(conn,sql.toString()).toString();
					 
					addCount[0] = Integer.parseInt(bankAdd);
					renewCount[0] = 0;
				}else{
					addCount[0] = 0;
					
					sql = new SQLCreator();
					sql.add("SELECT COUNT(*) FROM BNK_MER_DAT");
					sql.add("WHERE OLD_BNK_CODE =?", bankMe.get(0).getOldBankCode());
					
				
					String bankRenew =  DBUtil.selectOne(conn, sql.toString()).toString();
					renewCount[0] = Integer.parseInt(bankRenew);
				}
				
				//銀行口座マスタCount
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM AP_CBK_MST");
				sql.add("WHERE CBK_BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String bankAccount = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[1] = Integer.parseInt(bankAccount);
				
				addCount[1] = 0;
				
				//社員マスタCount 
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM EMP_MST");
				sql.add("WHERE EMP_FURI_BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String emp = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[2] = Integer.parseInt(emp);
				
				addCount[2] = 0;
				
				//取引先支払条件マスタ
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM TRI_SJ_MST");
				sql.add("WHERE BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String tri = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[3] = Integer.parseInt(tri);
				
				addCount[3] = 0;
				
				//社員振込データ
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM AP_HFR_DAT");
				sql.add("WHERE HFR_FURI_BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String hfr = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[4] = Integer.parseInt(hfr);
				
				addCount[4] = 0;
				
				//支払集計データ
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM AP_HSK_DAT");
				sql.add("WHERE HSK_FURI_BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String hsk = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[5] = Integer.parseInt(hsk);
				
				addCount[5] = 0;
				
				//支払手形データCount
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM AP_HTG_DAT");
				sql.add("WHERE HTG_FURI_BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String htg = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[6] = Integer.parseInt(htg);
				
				addCount[6] = 0;
				
				//社員振込データCount
				sql = new SQLCreator();
				sql.add("SELECT COUNT(*) FROM AP_SFR_DAT");
				sql.add("WHERE SFR_FURI_BNK_CODE =?",bankMe.get(0).getOldBankCode());
				
				String sfr = DBUtil.selectOne(conn,sql.toString()).toString();
				renewCount[7] = Integer.parseInt(sfr);
				
				addCount[7] = 0;
				

				Statement statement = DBUtil.getStatement(conn);
				
				
				for(int i = 0; i < 8; i++){
					searchList.add(countMapping(addCount,renewCount , i));
				}
				
				
				DBUtil.close(statement);
				
			} catch (Exception e) {
				throw new TException(e);
			} finally {
				DBUtil.close(conn);
			}
			
			return searchList;
		}



	/**
	 * @param addCount
	 * @param renewCount
	 * @param i
	 * @return カウント数
	 */
	protected BankMerge countMapping(int[] addCount , int[] renewCount,int i){
		BankMerge bankMe = new BankMerge();
		
		
		bankMe.setAdd(addCount[i]);
		bankMe.setRenew(renewCount[i]);
		
		
		return bankMe;
	}

}

