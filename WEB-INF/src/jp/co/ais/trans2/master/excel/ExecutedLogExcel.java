package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.executedlog.*;

/**
 * 実行ログ参照エクセル
 * 
 * @author AIS
 */
public class ExecutedLogExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ExecutedLogExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param executed
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<ExecutedLog> executed) throws TException {

		try {
			createReport(executed);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param executed
	 */
	public void createReport(List<ExecutedLog> executed) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02911"));

		// カラム設定

		// 実行日時
		sheet.addColumn(getWord("C00218") + getWord("C02906"), 7600);
		// 実行ユーザーコード
		sheet.addColumn(getWord("C00218") + getWord("C00589"), 6000);
		// 実行ユーザー名称
		sheet.addColumn(getWord("C00218") + getWord("C00691"), 6000);
		// IPアドレス
		sheet.addColumn(getWord("C02907"), 6000);
		// プログラムコード
		sheet.addColumn(getWord("C00818"), 6000);
		// プログラム名称
		sheet.addColumn(getWord("C00819"), 6000);
		// ステータス
		sheet.addColumn(getWord("C02908"), 4600);
		// フォーマット

		// 明細描画
		for (ExecutedLog bean : executed) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(DateUtil.toYMDHMSString(bean.getExcDate()), SwingConstants.CENTER);
			newRow.addCell(bean.getExcCode());
			newRow.addCell(bean.getExcNames());
			newRow.addCell(bean.getIpAddress());

			if (ExecutedLogger.LOGIN.equals(bean.getProCode())) {
				bean.setProName(getWord("C03187")); // ログイン

			} else if (ExecutedLogger.LOGOUT.equals(bean.getProCode())) {
				bean.setProName(getWord("C03188")); // ログアウト
			}

			newRow.addCell(bean.getProCode());
			newRow.addCell(bean.getProName());
			newRow.addCell(bean.getStste());

		}

	}
}
