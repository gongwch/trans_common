package jp.co.ais.trans2.model.user;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.security.*;

/**
 * ユーザーマスタのDao実装
 * 
 * @author AIS
 */
public class UserDaoImpl extends TModel implements UserDao {

	/** アクセス権限設定フラグを使う */
	protected static final boolean USE_ACCESS_FLAG = ServerConfig.isFlagOn("trans.user.mst.use.access.flag");

	/** サイナー系情報を使う */
	protected static final boolean USE_BL_SIGNER = ServerConfig.isFlagOn("trans.user.mst.use.bl.signer");

	/** サイナー画像を使う */
	protected static final boolean USE_SIGNER_ATTACH = ServerConfig.isFlagOn("trans.user.mst.use.signer.attach");

	/** ダッシュボード権限を使う */
	protected static final boolean DASH_BOARD_CONDITION = ServerConfig.isFlagOn("trans.user.master.use.dashboard");

	/**
	 * 指定の会社コード、ユーザーコードに紐付くユーザー情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return 指定の会社コード、ユーザーコードに紐付くユーザー
	 * @throws TException
	 */
	public User get(String companyCode, String userCode) throws TException {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(userCode);

		List<User> users = get(condition);
		if (users == null || users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}

	/**
	 * 指定の会社コード、ユーザーコード、パスワードに紐付くユーザー情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 * @return 指定の会社コード、ユーザーコード、パスワードに紐付くユーザー
	 * @throws TException
	 */
	public User get(String companyCode, String userCode, String password) throws TException {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(userCode);
		condition.setPassword(password);

		List<User> users = get(condition);
		if (users == null || users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}

	/**
	 * 指定条件に該当するユーザー情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するユーザー情報
	 * @throws TException
	 */
	public List<User> get(UserSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<User> list = new ArrayList<User>();

		try {

			VCreator sql = new VCreator();

			sql.add(" SELECT usr.KAI_CODE ");
			sql.add("       ,usr.USR_CODE ");
			sql.add("       ,usr.USR_NAME ");
			sql.add("       ,usr.USR_NAME_S ");
			sql.add("       ,usr.USR_NAME_K ");
			sql.add("       ,usr.PASSWORD ");
			sql.add("       ,usr.PRG_ROLE_CODE ");
			sql.add("       ,prgrole.ROLE_NAME   PRG_ROLE_NAME ");
			sql.add("       ,prgrole.ROLE_NAME_S PRG_ROLE_NAME_S ");
			sql.add("       ,usr.USR_ROLE_CODE ");
			sql.add("       ,usrrole.ROLE_NAME   USR_ROLE_NAME ");
			sql.add("       ,usrrole.ROLE_NAME_S USR_ROLE_NAME_S ");
			if (USE_BL_SIGNER) {
				sql.add("       ,usr.SIGNER_DEPT ");
				sql.add("       ,usr.SIGNER_TITLE ");
				sql.add("       ,usr.SIGNER_NAME ");
			} else {
				sql.add("       ,NULL SIGNER_DEPT ");
				sql.add("       ,NULL SIGNER_TITLE ");
				sql.add("       ,NULL SIGNER_NAME ");
			}
			if (USE_SIGNER_ATTACH) {
				sql.add("       ,sign.FILE_NAME ");
			} else {
				sql.add("       ,NULL FILE_NAME ");
			}
			sql.add("       ,usr.EMAIL_ADDRESS ");
			sql.add("       ,usr.UPD_KEN ");
			sql.add("       ,usr.KEIRI_KBN ");
			sql.add("       ,usr.EMP_CODE ");
			sql.add("       ,emp.EMP_NAME ");
			sql.add("       ,emp.EMP_NAME_S ");
			sql.add("       ,emp.EMP_CBK_CODE ");
			sql.add("       ,usr.DEP_CODE ");
			sql.add("       ,bmn.DEP_NAME ");
			sql.add("       ,bmn.DEP_NAME_S ");
			sql.add("       ,usr.LNG_CODE ");
			sql.add("       ,lang.LNG_NAME ");
			sql.add("       ,lf.LF_NAME ");
			sql.add("       ,lf.LF_COLOR_TYPE ");
			sql.add("       ,lf.MENU_TYPE ");
			sql.add("       ,usr.STR_DATE ");
			sql.add("       ,usr.END_DATE ");

			// アクセス権限
			if (USE_ACCESS_FLAG) {
				sql.add("       ,usr.ACCESS_FLG_1 ");
				sql.add("       ,usr.ACCESS_FLG_2 ");
				sql.add("       ,usr.ACCESS_FLG_3 ");
				sql.add("       ,usr.ACCESS_FLG_4 ");
				sql.add("       ,usr.ACCESS_FLG_5 ");
				sql.add("       ,usr.ACCESS_FLG_6 ");
				sql.add("       ,usr.ACCESS_FLG_7 ");
				sql.add("       ,usr.ACCESS_FLG_8 ");
				sql.add("       ,usr.ACCESS_FLG_9 ");
				sql.add("       ,usr.ACCESS_FLG_10 ");
			} else {
				sql.add("       ,0 ACCESS_FLG_1 ");
				sql.add("       ,0 ACCESS_FLG_2 ");
				sql.add("       ,0 ACCESS_FLG_3 ");
				sql.add("       ,0 ACCESS_FLG_4 ");
				sql.add("       ,0 ACCESS_FLG_5 ");
				sql.add("       ,0 ACCESS_FLG_6 ");
				sql.add("       ,0 ACCESS_FLG_7 ");
				sql.add("       ,0 ACCESS_FLG_8 ");
				sql.add("       ,0 ACCESS_FLG_9 ");
				sql.add("       ,0 ACCESS_FLG_10 ");
			}

			sql.add("       ,usr.APRV_ROLE_GRP_CODE ");
			sql.add("       ,aprv.APRV_ROLE_GRP_NAME ");
			sql.add("       ,aprv.APRV_ROLE_GRP_NAME_S ");

			sql.add(" FROM   USR_MST usr ");
			sql.add(" LEFT   JOIN PRG_ROLE_HDR prgrole ON prgrole.KAI_CODE = usr.KAI_CODE ");
			sql.add("                              AND    prgrole.ROLE_CODE = usr.PRG_ROLE_CODE ");
			sql.add(" LEFT   JOIN USR_ROLE_MST usrrole ON usrrole.KAI_CODE = usr.KAI_CODE ");
			sql.add("                              AND    usrrole.ROLE_CODE = usr.USR_ROLE_CODE ");
			sql.add(" LEFT   JOIN BMN_MST bmn ON bmn.KAI_CODE = usr.KAI_CODE ");
			sql.add("                     AND    bmn.DEP_CODE = usr.DEP_CODE ");
			sql.add(" LEFT   JOIN LNG_MST lang ON lang.KAI_CODE = usr.KAI_CODE ");
			sql.add("                      AND    lang.LNG_CODE = usr.LNG_CODE ");
			sql.add(" LEFT   JOIN USR_LF_DAT lf ON lf.KAI_CODE = usr.KAI_CODE ");
			sql.add("                       AND    lf.USR_CODE = usr.USR_CODE ");
			sql.add(" LEFT   JOIN EMP_MST emp ON emp.KAI_CODE = usr.KAI_CODE ");
			sql.add("                     AND    emp.EMP_CODE = usr.EMP_CODE ");

			sql.add(" LEFT   JOIN ( ");
			sql.add("        SELECT DISTINCT ");
			sql.add("               KAI_CODE ");
			sql.add("               ,APRV_ROLE_GRP_CODE ");
			sql.add("               ,APRV_ROLE_GRP_NAME ");
			sql.add("               ,APRV_ROLE_GRP_NAME_S ");
			sql.add("        FROM APRV_ROLE_GRP_MST ");
			sql.add("        ) aprv ");
			sql.add("				ON aprv.KAI_CODE           = usr.KAI_CODE ");
			sql.add("              AND aprv.APRV_ROLE_GRP_CODE = usr.APRV_ROLE_GRP_CODE ");

			if (USE_SIGNER_ATTACH) {
				sql.add(" LEFT JOIN USR_SIGN_ATTACH sign ON sign.KAI_CODE = usr.KAI_CODE ");
				sql.add("                               AND sign.USR_CODE = usr.USR_CODE ");
			}

			sql.add(" WHERE  1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND	usr.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND	usr.USR_CODE = ? ", condition.getCode());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND	usr.USR_CODE >= ? ", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND	usr.USR_CODE <= ? ", condition.getCodeTo());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND	usr.USR_CODE ? ", condition.getCodeLike());
			}

			// 略称
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi(" AND	usr.USR_NAME_S ? ", condition.getNamesLike());
			}

			// 検索名称
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql.addNLikeAmbi(" AND	usr.USR_NAME_K ? ", condition.getNamekLike());
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add(" AND	usr.STR_DATE <= ? ", condition.getValidTerm());
				sql.add(" AND	usr.END_DATE >= ? ", condition.getValidTerm());
			}

			// パスワード
			if (!Util.isNullOrEmpty(condition.getPassword())) {
				sql.add(" AND	usr.PASSWORD = ? ", condition.getPassword());
			}

			// 社員コード
			if (!Util.isNullOrEmpty(condition.getEmployeeCode())) {
				sql.add(" AND	usr.EMP_CODE = ? ", condition.getEmployeeCode());
			}

			// アクセス権限
			if (USE_ACCESS_FLAG) {
				if (condition.getAccessPermissionFlag1() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_1 = ? ", condition.getAccessPermissionFlag1());
				}
				if (condition.getAccessPermissionFlag2() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_2 = ? ", condition.getAccessPermissionFlag2());
				}
				if (condition.getAccessPermissionFlag3() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_3 = ? ", condition.getAccessPermissionFlag3());
				}
				if (condition.getAccessPermissionFlag4() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_4 = ? ", condition.getAccessPermissionFlag4());
				}
				if (condition.getAccessPermissionFlag5() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_5 = ? ", condition.getAccessPermissionFlag5());
				}
				if (condition.getAccessPermissionFlag6() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_6 = ? ", condition.getAccessPermissionFlag6());
				}
				if (condition.getAccessPermissionFlag7() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_7 = ? ", condition.getAccessPermissionFlag7());
				}
				if (condition.getAccessPermissionFlag8() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_8 = ? ", condition.getAccessPermissionFlag8());
				}
				if (condition.getAccessPermissionFlag9() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_9 = ? ", condition.getAccessPermissionFlag9());
				}
				if (condition.getAccessPermissionFlag10() != -1) {
					sql.add(" AND   usr.ACCESS_FLG_10 = ? ", condition.getAccessPermissionFlag10());
				}
			}

			sql.add(" ORDER BY ");
			sql.add("     usr.KAI_CODE ");
			sql.add("    ,usr.USR_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			if (USE_SIGNER_ATTACH) {
				setupSignAttach(list, conn);
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

	public void entry(User user) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add(" INSERT INTO USR_MST ( ");
			sql.add("    KAI_CODE ");
			sql.add("   ,USR_CODE ");
			sql.add("   ,PASSWORD ");
			sql.add("   ,LNG_CODE ");
			sql.add("   ,USR_NAME ");
			sql.add("   ,USR_NAME_S ");
			sql.add("   ,USR_NAME_K ");
			if (USE_BL_SIGNER) {
				sql.add("   ,SIGNER_DEPT ");
				sql.add("   ,SIGNER_TITLE ");
				sql.add("   ,SIGNER_NAME ");
			}
			sql.add("   ,EMAIL_ADDRESS ");
			sql.add("   ,UPD_KEN ");
			sql.add("   ,EMP_CODE ");
			sql.add("   ,DEP_CODE ");
			sql.add("   ,KEIRI_KBN ");
			sql.add("   ,STR_DATE ");
			sql.add("   ,END_DATE ");
			sql.add("   ,INP_DATE ");
			sql.add("   ,PRG_ID ");
			sql.add("   ,USR_ID ");
			sql.add("   ,PRG_ROLE_CODE ");
			sql.add("   ,USR_ROLE_CODE ");
			sql.add("   ,APRV_ROLE_GRP_CODE ");

			// アクセス権限
			if (USE_ACCESS_FLAG) {
				sql.add("   ,ACCESS_FLG_1 ");
				sql.add("   ,ACCESS_FLG_2 ");
				sql.add("   ,ACCESS_FLG_3 ");
				sql.add("   ,ACCESS_FLG_4 ");
				sql.add("   ,ACCESS_FLG_5 ");
				sql.add("   ,ACCESS_FLG_6 ");
				sql.add("   ,ACCESS_FLG_7 ");
				sql.add("   ,ACCESS_FLG_8 ");
				sql.add("   ,ACCESS_FLG_9 ");
				sql.add("   ,ACCESS_FLG_10 ");
			}
			sql.add(" ) VALUES ( ");
			sql.add("    ? ", user.getCompanyCode());
			sql.add("   ,? ", user.getCode());
			sql.add("   ,? ", user.getPassword());
			sql.add("   ,? ", user.getLanguage());
			sql.add("   ,? ", user.getName());
			sql.add("   ,? ", user.getNames());
			sql.add("   ,? ", user.getNamek());
			if (USE_BL_SIGNER) {
				sql.add("   ,? ", user.getSignerDept());
				sql.add("   ,? ", user.getSignerTitle());
				sql.add("   ,? ", user.getSignerName());
			}
			sql.add("   ,? ", user.getEMailAddress());
			sql.add("   ,? ", user.getSlipRole().value);
			sql.add("   ,? ", user.getEmployee().getCode());
			sql.add("   ,? ", user.getDepartment().getCode());
			sql.add("   ,? ", user.getUserAccountRole().value);
			sql.add("   ,? ", user.getDateFrom());
			sql.add("   ,? ", user.getDateTo());
			sql.adt("   ,? ", getNow());
			sql.add("   ,? ", getProgramCode());
			sql.add("   ,? ", getUserCode());
			sql.add("   ,? ", user.getProgramRole().getCode());
			sql.add("   ,? ", user.getUserRole() == null ? null : user.getUserRole().getCode());
			sql.add("   ,? ", user.getAprvRoleGroup() == null ? null : user.getAprvRoleGroup().getAPRV_ROLE_GRP_CODE());
			// アクセス権限
			if (USE_ACCESS_FLAG) {
				sql.add("   ,? ", user.getAccessPermissionFlag1());
				sql.add("   ,? ", user.getAccessPermissionFlag2());
				sql.add("   ,? ", user.getAccessPermissionFlag3());
				sql.add("   ,? ", user.getAccessPermissionFlag4());
				sql.add("   ,? ", user.getAccessPermissionFlag5());
				sql.add("   ,? ", user.getAccessPermissionFlag6());
				sql.add("   ,? ", user.getAccessPermissionFlag7());
				sql.add("   ,? ", user.getAccessPermissionFlag8());
				sql.add("   ,? ", user.getAccessPermissionFlag9());
				sql.add("   ,? ", user.getAccessPermissionFlag10());
			}
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

			if (USE_SIGNER_ATTACH) {
				// 登録
				insertAttach(conn, user);
			}

			// ダッシュボード権限
			if (DASH_BOARD_CONDITION) {
				insertUSR_DASH_CTL(user);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @param conn
	 * @param user
	 * @throws TException
	 */
	protected void insertAttach(Connection conn, User user) throws TException {
		USR_SIGN bean = user.getSignFile();

		if (bean == null || bean.getFILE_NAME() == null) {
			// 添付なしの場合、INSERT不要
			return;
		}

		// 伝票番号設定
		bean.setKAI_CODE(user.getCompanyCode());
		bean.setUSR_CODE(user.getCode());

		if (bean.getINP_DATE() == null) {
			bean.setINP_DATE(getNow());
		}
		if (Util.isNullOrEmpty(bean.getPRG_ID())) {
			bean.setPRG_ID(getProgramCode());
		}
		if (Util.isNullOrEmpty(bean.getUSR_ID())) {
			bean.setUSR_ID(getUserCode());
		}
		if (!Util.isNullOrEmpty(bean.getFile())) {
			// サーバサイト一時ファイル→指定フォルダへ保存
			String serverFileName = FileTransferUtil.createServerFile(bean.getFile());
			bean.setServerFileName(serverFileName);
		}

		// 削除
		VCreator sql = new VCreator();

		sql.add(" INSERT INTO USR_SIGN_ATTACH ");
		sql.add("   (KAI_CODE ");
		sql.add("   ,USR_CODE ");
		sql.add("   ,FILE_NAME ");
		sql.add("   ,SRV_FILE_NAME ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID) ");
		sql.add(" VALUES ");
		sql.add("   (? ", bean.getKAI_CODE());
		sql.add("   ,? ", bean.getUSR_CODE());
		sql.add("   ,? ", bean.getFILE_NAME());
		sql.add("   ,? ", bean.getServerFileName());
		sql.adt("   ,? ", bean.getINP_DATE());
		sql.add("   ,? ", bean.getPRG_ID());
		sql.add("   ,? ", bean.getUSR_ID());
		sql.add("   ) ");

		DBUtil.execute(conn, sql);

	}

	public void modify(User user) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();

			sql.add(" UPDATE USR_MST ");
			sql.add(" SET    PASSWORD      = ? ", user.getPassword());
			sql.add("       ,LNG_CODE      = ? ", user.getLanguage());
			sql.add("       ,USR_NAME      = ? ", user.getName());
			sql.add("       ,USR_NAME_S    = ? ", user.getNames());
			sql.add("       ,USR_NAME_K    = ? ", user.getNamek());
			if (USE_BL_SIGNER) {
				sql.add("       ,SIGNER_DEPT   = ? ", user.getSignerDept());
				sql.add("       ,SIGNER_TITLE  = ? ", user.getSignerTitle());
				sql.add("       ,SIGNER_NAME   = ? ", user.getSignerName());
			}
			sql.add("       ,EMAIL_ADDRESS = ? ", user.getEMailAddress());
			sql.add("       ,UPD_KEN       = ? ", user.getSlipRole().value);
			sql.add("       ,EMP_CODE      = ? ", user.getEmployee().getCode());
			sql.add("       ,DEP_CODE      = ? ", user.getDepartment().getCode());
			sql.add("       ,KEIRI_KBN     = ? ", user.getUserAccountRole().value);
			sql.add("       ,STR_DATE      = ? ", user.getDateFrom());
			sql.add("       ,END_DATE      = ? ", user.getDateTo());
			sql.adt("       ,UPD_DATE      = ? ", getNow());
			sql.add("       ,PRG_ID        = ? ", getProgramCode());
			sql.add("       ,USR_ID        = ? ", getUserCode());
			sql.add("       ,PRG_ROLE_CODE = ? ", user.getProgramRole().getCode());
			sql.add("       ,USR_ROLE_CODE = ? ", user.getUserRole() == null ? null : user.getUserRole().getCode());
			String roleCode = user.getAprvRoleGroup() == null ? null : user.getAprvRoleGroup().getAPRV_ROLE_GRP_CODE();
			sql.add("       ,APRV_ROLE_GRP_CODE = ? ", roleCode);
			// アクセス権限
			if (USE_ACCESS_FLAG) {
				sql.add("       ,ACCESS_FLG_1        = ? ", user.getAccessPermissionFlag1());
				sql.add("       ,ACCESS_FLG_2        = ? ", user.getAccessPermissionFlag2());
				sql.add("       ,ACCESS_FLG_3        = ? ", user.getAccessPermissionFlag3());
				sql.add("       ,ACCESS_FLG_4        = ? ", user.getAccessPermissionFlag4());
				sql.add("       ,ACCESS_FLG_5        = ? ", user.getAccessPermissionFlag5());
				sql.add("       ,ACCESS_FLG_6        = ? ", user.getAccessPermissionFlag6());
				sql.add("       ,ACCESS_FLG_7        = ? ", user.getAccessPermissionFlag7());
				sql.add("       ,ACCESS_FLG_8        = ? ", user.getAccessPermissionFlag8());
				sql.add("       ,ACCESS_FLG_9        = ? ", user.getAccessPermissionFlag9());
				sql.add("       ,ACCESS_FLG_10       = ? ", user.getAccessPermissionFlag10());

			}
			sql.add(" WHERE  KAI_CODE = ? ", user.getCompanyCode());
			sql.add(" AND    USR_CODE = ? ", user.getCode());

			DBUtil.execute(conn, sql);

			if (USE_SIGNER_ATTACH) {
				// 削除 → 登録
				deleteAttach(conn, user.getCompanyCode(), user.getCode());
				insertAttach(conn, user);
			}

			// ダッシュボード権限
			if (DASH_BOARD_CONDITION) {
				insertUSR_DASH_CTL(user);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void delete(User user) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();
			sql.add(" DELETE FROM USR_MST ");
			sql.add(" WHERE KAI_CODE = ? ", user.getCompanyCode());
			sql.add(" AND	USR_CODE = ? ", user.getCode());

			DBUtil.execute(conn, sql);

			sql = new VCreator();
			sql.add(" DELETE FROM USR_LF_DAT ");
			sql.add(" WHERE KAI_CODE = ? ", user.getCompanyCode());
			sql.add(" AND	USR_CODE = ? ", user.getCode());

			DBUtil.execute(conn, sql);

			if (USE_SIGNER_ATTACH) {
				// 削除
				deleteAttach(conn, user.getCompanyCode(), user.getCode());
			}

			if (DASH_BOARD_CONDITION) {
				// 削除
				deleteUSR_DASH_CTL(conn, user.getCompanyCode(), user.getCode());
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ユーザーのL&Fを登録する。
	 * 
	 * @param user ユーザー
	 */
	public void entryLookandFeel(User user) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();
			sql = new VCreator();
			sql.add(" DELETE FROM USR_LF_DAT ");
			sql.add(" WHERE KAI_CODE = ? ", user.getCompanyCode());
			sql.add(" AND	USR_CODE = ? ", user.getCode());

			DBUtil.execute(conn, sql);

			sql = new VCreator();
			sql.add(" INSERT INTO USR_LF_DAT ( ");
			sql.add("    KAI_CODE ");
			sql.add("   ,USR_CODE ");
			sql.add("   ,LF_NAME ");
			sql.add("   ,LF_COLOR_TYPE ");
			sql.add("   ,MENU_TYPE ");
			sql.add("   ,INP_DATE ");
			sql.add("   ,PRG_ID ");
			sql.add("   ,USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("    ? ", user.getCompanyCode());
			sql.add("   ,? ", user.getCode());
			sql.add("   ,? ", user.getLfName());
			sql.add("   ,? ", user.getLfColorType());
			sql.add("   ,? ", user.getMenuType().value);
			sql.adt("   ,? ", getNow());
			sql.add("   ,? ", getProgramCode());
			sql.add("   ,? ", getUserCode());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return Entity
	 * @throws Exception
	 */
	protected User mapping(ResultSet rs) throws Exception {
		User bean = createUser();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("USR_CODE"));
		bean.setName(rs.getString("USR_NAME"));
		bean.setNames(rs.getString("USR_NAME_S"));
		bean.setNamek(rs.getString("USR_NAME_K"));
		bean.setPassword(rs.getString("PASSWORD"));
		bean.setLfName(Util.avoidNull(rs.getString("LF_NAME")));
		bean.setLfColorType(Util.avoidNull(rs.getString("LF_COLOR_TYPE")));
		bean.setMenuType(MenuType.get(rs.getString("MENU_TYPE")));
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));
		bean.setSlipRole(SlipRole.getSlipRole(rs.getInt("UPD_KEN")));
		bean.setUserAccountRole(UserAccountRole.get(rs.getInt("KEIRI_KBN")));
		if (USE_BL_SIGNER) {
			bean.setSignerDept(rs.getString("SIGNER_DEPT"));
			bean.setSignerTitle(rs.getString("SIGNER_TITLE"));
			bean.setSignerName(rs.getString("SIGNER_NAME"));
		}
		if (USE_SIGNER_ATTACH) {
			bean.setSignFileName(rs.getString("FILE_NAME"));
		}
		bean.setEMailAddress(rs.getString("EMAIL_ADDRESS"));

		Department department = createDepartment();
		department.setCode(rs.getString("DEP_CODE"));
		department.setName(rs.getString("DEP_NAME"));
		department.setNames(rs.getString("DEP_NAME_S"));
		bean.setDepartment(department);

		Employee employee = createEmployee();
		employee.setCode(rs.getString("EMP_CODE"));
		employee.setName(rs.getString("EMP_NAME"));
		employee.setNames(rs.getString("EMP_NAME_S"));
		employee.setCbkCode(rs.getString("EMP_CBK_CODE"));
		bean.setEmployee(employee);

		ProgramRole programRole = createProgramRole();
		programRole.setCode(rs.getString("PRG_ROLE_CODE"));
		programRole.setName(rs.getString("PRG_ROLE_NAME"));
		programRole.setNames(rs.getString("PRG_ROLE_NAME_S"));
		bean.setProgramRole(programRole);

		UserRole userRole = createUserRole();
		userRole.setCode(rs.getString("USR_ROLE_CODE"));
		userRole.setName(rs.getString("USR_ROLE_NAME"));
		userRole.setNames(rs.getString("USR_ROLE_NAME_S"));
		bean.setUserRole(userRole);

		AprvRoleGroup aprvRoleGroup = new AprvRoleGroup();
		aprvRoleGroup.setAPRV_ROLE_GRP_CODE(rs.getString("APRV_ROLE_GRP_CODE"));
		aprvRoleGroup.setAPRV_ROLE_GRP_NAME(rs.getString("APRV_ROLE_GRP_NAME"));
		aprvRoleGroup.setAPRV_ROLE_GRP_NAME_S(rs.getString("APRV_ROLE_GRP_NAME_S"));
		bean.setAprvRoleGroup(aprvRoleGroup);

		bean.setSlipRole(SlipRole.getSlipRole(rs.getInt("UPD_KEN")));
		bean.setUserAccountRole(UserAccountRole.get(rs.getInt("KEIRI_KBN")));

		bean.setLanguage(rs.getString("LNG_CODE"));
		bean.setLanguageName(rs.getString("LNG_NAME"));

		if (USE_ACCESS_FLAG) {
			bean.setAccessPermissionFlag1(rs.getInt("ACCESS_FLG_1"));
			bean.setAccessPermissionFlag2(rs.getInt("ACCESS_FLG_2"));
			bean.setAccessPermissionFlag3(rs.getInt("ACCESS_FLG_3"));
			bean.setAccessPermissionFlag4(rs.getInt("ACCESS_FLG_4"));
			bean.setAccessPermissionFlag5(rs.getInt("ACCESS_FLG_5"));
			bean.setAccessPermissionFlag6(rs.getInt("ACCESS_FLG_6"));
			bean.setAccessPermissionFlag7(rs.getInt("ACCESS_FLG_7"));
			bean.setAccessPermissionFlag8(rs.getInt("ACCESS_FLG_8"));
			bean.setAccessPermissionFlag9(rs.getInt("ACCESS_FLG_9"));
			bean.setAccessPermissionFlag10(rs.getInt("ACCESS_FLG_10"));
		}

		return bean;
	}

	/**
	 * @return プリンタ名称を返す(プリンタ設定がある場合)
	 * @throws TException
	 */
	public String getPrinterName(String companyCode, String userCode) throws TException {
		Connection conn = DBUtil.getConnection();
		try {

			VCreator sql = new VCreator();

			sql.add("SELECT CLT_PRINTER_NAME ");
			sql.add("FROM   USR_MST ");
			sql.add("WHERE  1 = 1 ");

			if (!Util.isNullOrEmpty(companyCode)) {
				sql.add("  AND KAI_CODE = ? ", companyCode);
			}
			if (!Util.isNullOrEmpty(userCode)) {
				sql.add("  AND USR_CODE = ? ", userCode);
			}

			return Util.avoidNull(DBUtil.selectOne(conn, sql.toSQL()));

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void updatePrinter(User bean) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("UPDATE USR_MST SET");
			s.add("  CLT_PRINTER_NAME = ?", bean.getPrinterName());

			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("  AND USR_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * @return ユーザロール
	 */
	protected UserRole createUserRole() {
		return new UserRole();
	}

	/**
	 * @return プログラムロール
	 */
	protected ProgramRole createProgramRole() {
		return new ProgramRole();
	}

	/**
	 * @return 社員
	 */
	protected Employee createEmployee() {
		return new Employee();
	}

	/**
	 * @return 部門
	 */
	protected Department createDepartment() {
		return new Department();
	}

	/**
	 * @return ユーザ
	 */
	protected User createUser() {
		return new User();
	}

	/**
	 * SQL creator
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}

	/**
	 * @param list
	 * @param conn
	 * @return List<User>
	 * @throws Exception
	 */
	protected List<User> setupSignAttach(List<User> list, Connection conn) throws Exception {

		for (User usr : list) {
			String fileName = usr.getSignFileName();
			if (!Util.isNullOrEmpty(fileName)) {
				usr.setSignFile(getSignAttach(usr.getCompanyCode(), usr.getCode(), conn));
			}

		}

		return list;
	}

	/**
	 * 特定の伝票の添付情報を設定する
	 * 
	 * @param companyCode
	 * @param usrCode
	 * @param conn
	 * @return 添付情報<ファイル名, バイナリ>
	 * @throws Exception
	 */
	protected USR_SIGN getSignAttach(String companyCode, String usrCode, Connection conn) throws Exception {

		if (Util.isNullOrEmpty(usrCode)) {
			return null;
		}

		USR_SIGN bean = new USR_SIGN();

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT att.KAI_CODE ");
		sql.add("       ,att.USR_CODE ");
		sql.add("       ,att.FILE_NAME ");
		sql.add("       ,att.SRV_FILE_NAME ");
		sql.add("       ,att.INP_DATE ");
		sql.add("       ,att.PRG_ID ");
		sql.add("       ,att.USR_ID ");
		sql.add(" FROM   USR_SIGN_ATTACH att ");

		sql.add(" WHERE 1 = 1 ");

		// 会社コード
		if (!Util.isNullOrEmpty(companyCode)) {
			sql.add(" AND   att.KAI_CODE = ? ", companyCode);
		}

		// ユーザーコード
		if (!Util.isNullOrEmpty(usrCode)) {
			sql.add(" AND   att.USR_CODE = ? ", usrCode);
		}

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		while (rs.next()) {
			bean = mappingAttach(rs);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return bean;

	}

	/**
	 * マッピング処理
	 * 
	 * @param rs
	 * @return 添付
	 * @throws Exception
	 */
	protected USR_SIGN mappingAttach(ResultSet rs) throws Exception {

		String fileName = rs.getString("FILE_NAME");
		String serverFileName = rs.getString("SRV_FILE_NAME");

		USR_SIGN bean = new USR_SIGN();
		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setUSR_CODE(rs.getString("USR_CODE"));
		bean.setServerFileName(rs.getString("SRV_FILE_NAME"));
		bean.setFILE_NAME(fileName);
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));

		// ファイルバイナリ取得
		bean.setFILE_DATA(FileTransferUtil.getServerFile(fileName, serverFileName));

		return bean;
	}

	/**
	 * 特定の添付情報の削除
	 * 
	 * @param conn
	 * @param companyCode
	 * @param usrCode
	 * @throws Exception
	 */
	protected void deleteAttach(Connection conn, String companyCode, String usrCode) throws Exception {

		String serverFileName = new String();

		{
			// 取得
			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT SRV_FILE_NAME ");
			sql.add(" FROM   USR_SIGN_ATTACH att ");
			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(companyCode)) {
				sql.add(" AND   att.KAI_CODE = ? ", companyCode);
			}

			// 伝票番号
			if (!Util.isNullOrEmpty(usrCode)) {
				sql.add(" AND   att.USR_CODE = ? ", usrCode);
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				serverFileName = rs.getString("SRV_FILE_NAME");
			}

			DBUtil.close(rs);
			DBUtil.close(statement);
		}

		if (serverFileName != null) {
			// 削除
			VCreator sql = new VCreator();

			sql.add(" DELETE FROM USR_SIGN_ATTACH ");
			sql.add(" WHERE  USR_CODE = ? ", usrCode);

			if (!Util.isNullOrEmpty(companyCode)) {
				sql.add(" AND    KAI_CODE = ? ", companyCode);
			}

			DBUtil.execute(conn, sql);

			// ファイル削除
			FileTransferUtil.deleteServerFile(serverFileName);
		}

	}

	/**
	 * ダッシュボード権限コントロール一覧取得
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するユーザー情報
	 * @throws TException
	 */
	public List<USR_DASH_CTL> getDashBoardSysKbn(UserSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<USR_DASH_CTL> list = new ArrayList<USR_DASH_CTL>();
		try {

			VCreator sql = new VCreator();

			sql.add(" SELECT ");
			sql.add(" sys.KAI_CODE ");
			sql.add(" ,NVL(ctl.USE_KBN,0) USE_KBN ");
			sql.add(" ,sys.SYS_KBN ");
			sql.add(" ,sys.SYS_KBN_NAME_S SYS_KBN_NAME ");
			sql.add(" FROM SYS_MST sys ");
			sql.add(" LEFT JOIN USR_DASH_CTL ctl  ON ctl.KAI_CODE = sys.KAI_CODE ");
			sql.add("                            AND ctl.SYS_KBN = sys.SYS_KBN ");
			sql.add("                            AND ctl.USR_CODE = ? ", condition.getCode());
			sql.add(" WHERE sys.KAI_CODE = ? ", condition.getCompanyCode());
			sql.add("   AND sys.SYS_KBN IN ? ", condition.getSysKbnList());
			sql.add(" ORDER BY ");
			sql.add(" sys.KAI_CODE ");
			sql.add(" ,sys.SYS_KBN ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingSysKbn(rs));
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
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return Entity
	 * @throws Exception
	 */
	protected USR_DASH_CTL mappingSysKbn(ResultSet rs) throws Exception {

		USR_DASH_CTL bean = new USR_DASH_CTL();
		bean.setUSE_KBN(BooleanUtil.toBoolean(rs.getInt("USE_KBN")));
		bean.setSYS_KBN_CODE(rs.getString("SYS_KBN"));
		bean.setSYS_KBN_NAME(rs.getString("SYS_KBN_NAME"));

		return bean;
	}

	/**
	 * USR_DASH_CTLにInsert
	 * 
	 * @param user
	 * @throws TException
	 */
	public void insertUSR_DASH_CTL(User user) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			deleteUSR_DASH_CTL(conn, user.getCompanyCode(), user.getCode());

			for (USR_DASH_CTL bean : user.getUSR_DASH_CTLList()) {
				sql = new VCreator();
				sql.add(" INSERT INTO USR_DASH_CTL ( ");
				sql.add("    KAI_CODE ");
				sql.add("   ,USR_CODE ");
				sql.add("   ,SYS_KBN ");
				sql.add("   ,USE_KBN ");
				sql.add("   ,INP_DATE ");
				sql.add("   ,PRG_ID ");
				sql.add("   ,USR_ID ");
				sql.add(" ) VALUES ( ");
				sql.add("    ? ", bean.getKAI_CODE());
				sql.add("   ,? ", bean.getUSR_CODE());
				sql.add("   ,? ", bean.getSYS_KBN_CODE());
				sql.add("   ,? ", BooleanUtil.toInt(bean.isUSE_KBN()));
				sql.adt("   ,? ", getNow());
				sql.add("   ,? ", getProgramCode());
				sql.add("   ,? ", getUserCode());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * USR_DASH_CTLの削除
	 * 
	 * @param conn
	 * @param companyCode
	 * @param usrCode
	 * @throws Exception
	 */
	protected void deleteUSR_DASH_CTL(Connection conn, String companyCode, String usrCode) throws Exception {

		// 削除
		VCreator sql = new VCreator();

		sql.add(" DELETE FROM USR_DASH_CTL ");
		sql.add(" WHERE  KAI_CODE = ? ", companyCode);
		sql.add("   AND  USR_CODE = ? ", usrCode);

		DBUtil.execute(conn, sql);

	}

}
