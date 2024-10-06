package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザー一覧エクセル
 * 
 * @author AIS
 */
public class UserExcel extends TExcel {

	/** サイナー系情報を使う */
	protected static final boolean USE_BL_SIGNER = ServerConfig.isFlagOn("trans.user.mst.use.bl.signer");

	/** サイナー系情報を使う */
	protected static final boolean USE_SIGNER_ATTACH = ServerConfig.isFlagOn("trans.user.mst.use.signer.attach");

	// protected static boolean APRV_ROLE = true;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public UserExcel(String lang) {
		super(lang);
	}

	/**
	 * ユーザー一覧をエクセルで返す
	 * 
	 * @param userList ユーザー一覧
	 * @return userList
	 * @throws TException
	 */
	public byte[] getExcel(List<User> userList) throws TException {

		try {
			createReport(userList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param userList
	 */
	public void createReport(List<User> userList) {

		// シート追加
		// ユーザーマスタ
		TExcelSheet sheet = addSheet(getWord("C02355"));

		// カラム設定]
		// ユーザーコード
		sheet.addColumn(getWord("C00589"), 4200);
		// ユーザー名称
		sheet.addColumn(getWord("C00691"), 8400);
		// ユーザー略称
		sheet.addColumn(getWord("C00692"), 8400);
		// ユーザー検索名称
		sheet.addColumn(getWord("C00693"), 8400);
		// プログラムロールコード
		sheet.addColumn(getWord("C11159"), 4200);
		// プログラムロール名称
		sheet.addColumn(getWord("C11160"), 8400);
		// ユーザーロールコード
		sheet.addColumn(getWord("C11161"), 4200);
		// ユーザーロール名称
		sheet.addColumn(getWord("C11162"), 8400);
		// 承認グループコード
		sheet.addColumn(getWord("C12230"), 4200);
		// 承認グループ名称
		sheet.addColumn(getWord("C12231"), 8400);
		if (USE_BL_SIGNER) {
			// INV. SIGNER DEPT
			sheet.addColumn(getWord("CM0074"), 8400);
			// INV. SIGNER TITLE
			sheet.addColumn(getWord("CM0075"), 8400);
			// INV. SIGNER NAME
			sheet.addColumn(getWord("CM0076"), 8400);
		}
		if (USE_SIGNER_ATTACH) {
			// INV. SIGN FILE NAME
			sheet.addColumn(getWord("SIGN"), 8400);
		}
		// E-MAIL
		sheet.addColumn(getWord("COP065"), 8400);
		// 更新権限レベル
		sheet.addColumn(getWord("C00170"), 4200);
		// 決算伝票入力区分
		sheet.addColumn(getWord("C01056"), 4200);
		// 社員コード
		sheet.addColumn(getWord("C00697"), 4200);
		// 社員名称
		sheet.addColumn(getWord("C00807"), 4200);
		// 所属部門コード
		sheet.addColumn(getWord("C02043"), 4200);
		// 所属部門名称
		sheet.addColumn(getWord("C11163"), 4200);
		// 言語
		sheet.addColumn(getWord("C00153"), 4200);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (User user : userList) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(user.getCode());
			newRow.addCell(user.getName());
			newRow.addCell(user.getNames());
			newRow.addCell(user.getNamek());
			newRow.addCell(user.getProgramRole().getCode());
			newRow.addCell(user.getProgramRole().getName());
			newRow.addCell(user.getUserRole().getCode());
			newRow.addCell(user.getUserRole().getName());
			newRow.addCell(user.getAprvRoleGroup().getAPRV_ROLE_GRP_CODE());
			newRow.addCell(user.getAprvRoleGroup().getAPRV_ROLE_GRP_NAME());
			if (USE_BL_SIGNER) {
				newRow.addCell(user.getSignerDept());
				newRow.addCell(user.getSignerTitle());
				newRow.addCell(user.getSignerName());
			}
			if (USE_SIGNER_ATTACH) {
				newRow.addCell(user.getSignFileName());
			}
			newRow.addCell(user.getEMailAddress());
			newRow.addCell(getWord(SlipRole.getSlipRoleName(user.getSlipRole())));
			newRow.addCell(getWord(UserAccountRole.getUserAccountRoleName(user.getUserAccountRole())));
			newRow.addCell(user.getEmployee().getCode());
			newRow.addCell(user.getEmployee().getName());
			newRow.addCell(user.getDepartment().getCode());
			newRow.addCell(user.getDepartment().getName());
			newRow.addCell(user.getLanguageName());
			newRow.addCell(user.getDateFrom());
			newRow.addCell(user.getDateTo());
		}

	}
}
