package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 実行ログのExcel定義クラス
 */
public class ReleasedFileReportExcelDefine extends ReportExcelDefineBase {

	/** 言語コード */
	private String langCode;

	/**
	 * ファイル名取得
	 * 
	 * @return MG0028 実行ログプログラム名
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getFileName()
	 */
	public String getFileName() {

		return "MG0029";
	}

	/**
	 * コンストラクタ
	 * 
	 * @param langCode 言語コード
	 */
	public ReleasedFileReportExcelDefine(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * シート名取得
	 * 
	 * @return シート名
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getSheetName()
	 */
	public String getSheetName() {

		return "C02914";
	}

	/**
	 * タイトル名の配列取得
	 * 
	 * @return String[] タイトルリスト
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getHeaderTexts()
	 */
	public String[] getHeaderTexts() {
		String updateDate = MessageUtil.getWord(langCode, "C00169") + MessageUtil.getWord(langCode, "C02906");

		return new String[] { "C02912", "C01988", updateDate, "C02915" };

	}

	/**
	 * カラム幅取得
	 * 
	 * @return short[] カラム幅配列
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getColumnWidths()
	 */
	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 50, 35, 15, 10 };
	}

	/**
	 * オブジェクトをArrayListに変換
	 * 
	 * @return ファイルリスト
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#convertDataToList(java.lang.Object, java.lang.String)
	 */
	public List convertDataToList(Object dto, String lang) {
		ReleasedFileObject entity = (ReleasedFileObject) dto;
		List list = new ArrayList();
		list.add(entity.getPATH_NAME());
		list.add(entity.getFILE_NAME());

		list.add(entity.getUPDATE_DATE());
		list.add(entity.getLENGTH());

		return list;

	}
}
