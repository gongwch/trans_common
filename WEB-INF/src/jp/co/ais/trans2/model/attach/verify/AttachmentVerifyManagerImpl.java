package jp.co.ais.trans2.model.attach.verify;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.attach.verify.AttachmentVerifyResult.*;

/**
 * 添付ファイル検証マネージャ実装クラス
 */
public class AttachmentVerifyManagerImpl extends TModel implements AttachmentVerifyManager {

	/**
	 * エラーリストを取得する
	 * 
	 * @return エラーリスト
	 * @throws TException
	 */
	public List<AttachmentVerifyResult> get() throws TException {
		List<AttachmentVerifyResult> list = new ArrayList();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			// 仕訳添付の取得
			getSWK_ATTACH(conn, list);
			// 船費添付の取得
			getSC_ATTACH(conn, list);
			// OP添付の取得
			getOP_ATTACH(conn, list);
			// BL添付の取得 TODO

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * @param conn
	 * @param list
	 * @throws TException
	 */
	protected void getSWK_ATTACH(Connection conn, List<AttachmentVerifyResult> list) throws TException {
		VCreator sql = getSwkAttachSQL();

		Statement state = null;
		ResultSet rs = null;
		List<AttachmentVerifyResult> tempList = new ArrayList();
		try {
			state = DBUtil.getStatement(conn);
			rs = DBUtil.select(state, sql);
			while (rs.next()) {
				AttachmentVerifyResult bean = new AttachmentVerifyResult();
				bean.setKAI_CODE(rs.getString("KAI_CODE"));
				bean.setKEY1(rs.getString("SWK_DEN_NO"));
				bean.setFILE_NAME(rs.getString("FILE_NAME"));
				bean.setSRV_FILE_NAME(rs.getString("SRV_FILE_NAME"));
				bean.setTYPE(VerifyResultType.SWK_ATTACH);
				Date denDate = rs.getDate("GL_DATE");
				String denSyuName = rs.getString("GL_DEN_SYU_NAME");
				if (Util.isNullOrEmpty(denSyuName)) {
					denSyuName = rs.getString("AP_DEN_SYU_NAME");
					denDate = rs.getDate("AP_DATE");
				}
				if (Util.isNullOrEmpty(denSyuName)) {
					denSyuName = rs.getString("AR_DEN_SYU_NAME");
					denDate = rs.getDate("AR_DATE");
				}
				bean.setKEY2(DateUtil.toYMDString(denDate));
				bean.setKEY3(denSyuName);
				tempList.add(bean);
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(state);
		}
		// ファイル存在状況検証
		for (AttachmentVerifyResult bean : tempList) {
			byte[] data = FileTransferUtil.getServerFile(bean.getFILE_NAME(), bean.getSRV_FILE_NAME());
			if (data == null) {
				bean.setMESSAGE("添付ファイルがサーバーに見つけられませんでした。");
				list.add(bean);
			}
		}
	}

	/**
	 * 仕訳添付取得SQL
	 * 
	 * @return 仕訳添付取得SQL
	 */
	protected VCreator getSwkAttachSQL() {
		VCreator sql = new VCreator();
		sql.add(" SELECT att.* ");
		sql.add("       ,glh.SWK_DEN_DATE GL_DATE ");
		sql.add("       ,glds.DEN_SYU_NAME GL_DEN_SYU_NAME ");
		sql.add("       ,aph.SWK_DEN_DATE AP_DATE");
		sql.add("       ,apds.DEN_SYU_NAME AP_DEN_SYU_NAME ");
		sql.add("       ,arh.SWK_DEN_DATE AR_DATE ");
		sql.add("       ,ards.DEN_SYU_NAME AR_DEN_SYU_NAME ");
		sql.add(" FROM SWK_ATTACH att ");
		sql.add(" LEFT JOIN GL_SWK_HDR glh ON glh.KAI_CODE   = att.KAI_CODE ");
		sql.add("                         AND glh.SWK_DEN_NO = att.SWK_DEN_NO ");
		sql.add(" LEFT JOIN DEN_SYU_MST glds ON glds.KAI_CODE     = glh.KAI_CODE ");
		sql.add("                           AND glds.DEN_SYU_CODE = glh.SWK_DEN_SYU ");
		sql.add(" LEFT JOIN AP_SWK_HDR aph ON aph.KAI_CODE   = att.KAI_CODE ");
		sql.add("                         AND aph.SWK_DEN_NO = att.SWK_DEN_NO ");
		sql.add(" LEFT JOIN DEN_SYU_MST apds ON apds.KAI_CODE     = aph.KAI_CODE ");
		sql.add("                           AND apds.DEN_SYU_CODE = aph.SWK_DEN_SYU ");
		sql.add(" LEFT JOIN AR_SWK_HDR arh ON arh.KAI_CODE   = att.KAI_CODE ");
		sql.add("                         AND arh.SWK_DEN_NO = att.SWK_DEN_NO ");
		sql.add(" LEFT JOIN DEN_SYU_MST ards ON ards.KAI_CODE     = arh.KAI_CODE ");
		sql.add("                           AND ards.DEN_SYU_CODE = arh.SWK_DEN_SYU ");
		return sql;
	}

	/**
	 * TODO
	 * 
	 * @param conn
	 * @param list
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void getSC_ATTACH(Connection conn, List<AttachmentVerifyResult> list) throws TException {
		VCreator sql = new VCreator();

		Statement state = null;
		ResultSet rs = null;
		try {
			//
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(state);
		}
	}

	/**
	 * @param conn
	 * @param list
	 * @throws TException
	 */
	protected void getOP_ATTACH(Connection conn, List<AttachmentVerifyResult> list) throws TException {
		VCreator sql = new VCreator();
		sql.add(" SELECT att.* ");
		sql.add("       ,voy.VOY_UID ");
		sql.add("       ,voy.VESSEL_CODE ");
		sql.add(" 		,vsl.VESSEL_NAME ");
		sql.add(" 		,voy.VOY_NO  ");
		sql.add(" 		,vcc.VCC_UID ");
		sql.add(" 		,vch.VC_CTRT_NO ");
		sql.add(" 		,vccvsl.VESSEL_NAME VCC_VESSEL_NAME ");
		sql.add(" 		,vch.VOY_NO VCC_VOY_NO ");
		sql.add(" 		,tch.TCC_UID ");
		sql.add(" 		,tch.TC_CTRT_NO ");
		sql.add(" 		,tccvsl.VESSEL_NAME TCC_VESSEL_NAME ");
		sql.add(" FROM op_attach att ");
		sql.add(" LEFT JOIN OP_VOY_HDR voy ON voy.KAI_CODE = att.KAI_CODE ");
		sql.add("                         AND voy.OP_ATT_UID = att.OP_ATT_UID ");
		sql.add(" LEFT JOIN CM_VESSEL_MST vsl ON vsl.KAI_CODE = voy.KAI_CODE  ");
		sql.add("                            AND vsl.VESSEL_CODE = voy.VESSEL_CODE ");
		sql.add(" LEFT JOIN OP_VC_CTRT_CRG_DTL vcc ON vcc.KAI_CODE = att.KAI_CODE ");
		sql.add("                                 AND vcc.OP_ATT_UID = att.OP_ATT_UID ");
		sql.add(" LEFT JOIN OP_VC_CTRT_HDR vch ON vch.KAI_CODE   = vcc.KAI_CODE ");
		sql.add("                             AND vch.VCC_UID = vcc.VCC_UID ");
		sql.add(" LEFT JOIN CM_VESSEL_MST vccvsl ON vccvsl.KAI_CODE    = vch.KAI_CODE  ");
		sql.add("                               AND vccvsl.VESSEL_CODE = vch.VESSEL_CODE ");
		sql.add(" LEFT JOIN OP_TC_CTRT_HDR tch ON tch.KAI_CODE   = att.KAI_CODE ");
		sql.add("                             AND tch.OP_ATT_UID = att.OP_ATT_UID ");
		sql.add(" LEFT JOIN CM_VESSEL_MST tccvsl ON tccvsl.KAI_CODE    = tch.KAI_CODE  ");
		sql.add("                               AND tccvsl.VESSEL_CODE = tch.VESSEL_CODE ");
		Statement state = null;
		ResultSet rs = null;
		List<AttachmentVerifyResult> tempList = new ArrayList();
		try {
			state = DBUtil.getStatement(conn);
			rs = DBUtil.select(state, sql);
			while (rs.next()) {
				AttachmentVerifyResult bean = new AttachmentVerifyResult();
				bean.setKAI_CODE(rs.getString("KAI_CODE"));
				bean.setFILE_NAME(rs.getString("FILE_NAME"));
				bean.setSRV_FILE_NAME(rs.getString("SRV_FILE_NAME"));
				bean.setTYPE(VerifyResultType.OP_ATTACH);
				bean.setKEY1(rs.getString("OP_ATT_UID"));
				String vslName = rs.getString("VESSEL_NAME");
				String voyNo = rs.getString("VOY_NO");
				String key2 = rs.getString("VOY_UID");
				String key3 = Util.avoidNull(vslName) + " " + Util.avoidNull(voyNo);
				String key4 = "";
				if (Util.isNullOrEmpty(key2)) {
					key2 = rs.getString("VCC_UID");
					key3 = rs.getString("VC_CTRT_NO");
					vslName = rs.getString("VCC_VESSEL_NAME");
					voyNo = rs.getString("VCC_VOY_NO");
					key4 = Util.avoidNull(vslName) + " " + Util.avoidNull(voyNo);
				}
				if (Util.isNullOrEmpty(key2)) {
					key2 = rs.getString("TCC_UID");
					key3 = rs.getString("VC_CTRT_NO");
					vslName = rs.getString("TCC_VESSEL_NAME");
					voyNo = "";
					key4 = Util.avoidNull(vslName) + " " + Util.avoidNull(voyNo);
				}
				bean.setKEY2(key2);
				bean.setKEY3(key3);
				bean.setKEY4(key4);
				tempList.add(bean);
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(state);
		}
		// ファイル存在状況検証
		for (AttachmentVerifyResult bean : tempList) {
			byte[] data = FileTransferUtil.getServerFile(bean.getFILE_NAME(), bean.getSRV_FILE_NAME());
			if (data == null) {
				bean.setMESSAGE("添付ファイルがサーバーに見つけられませんでした。");
				list.add(bean);
			}
		}
	}

	/**
	 * エラーリストエクセルを取得する
	 * 
	 * @return エラーリストエクセル
	 * @throws TException
	 */
	public byte[] getExcel() throws TException {
		try {
			// データ取得
			List<AttachmentVerifyResult> list = get();
			// データ非存在時null返却
			if (list == null || list.isEmpty()) {
				return null;
			}
			// エクセル
			AttachmentVerifyExcel excel = new AttachmentVerifyExcel(getUser().getLanguage());
			return excel.getReport(list);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/***/
	class VCreator extends SQLCreator {

		/***/
		public VCreator() {
			super();
			this.crlf = " ";
		}
	}

}
