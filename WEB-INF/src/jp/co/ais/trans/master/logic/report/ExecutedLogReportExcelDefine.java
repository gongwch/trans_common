package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 実行ログのExcel定義クラス
 */
public class ExecutedLogReportExcelDefine extends ReportExcelDefineBase {

	/** 言語コード */
	private String langCode;

	/**
	 * ファイル名取得
	 * 
	 * @return MG0028 実行ログプログラム名
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getFileName()
	 */
	public String getFileName() {
		return "MG0028";
	}

	/**
	 * コンストラクタ
	 * 
	 * @param langCode 言語コード
	 */
	public ExecutedLogReportExcelDefine(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * シート名取得
	 * 
	 * @return シート名
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getSheetName()
	 */
	public String getSheetName() {
		return MessageUtil.getWord(langCode, "C02911");
	}

	/**
	 * タイトル名の配列取得
	 * 
	 * @return String[] タイトルリスト
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getHeaderTexts()
	 */
	public String[] getHeaderTexts() {
		// タイトルを返す
		String logdate = MessageUtil.getWord(langCode, "C00218") + MessageUtil.getWord(langCode, "C02909");
		String userCode = MessageUtil.getWord(langCode, "C00218") + MessageUtil.getWord(langCode, "C00589");
		String userName = MessageUtil.getWord(langCode, "C00218") + MessageUtil.getWord(langCode, "C00691");
		return new String[] { logdate, userCode, userName, "C02907", "C00818", "C00819", "C02908" };

	}

	/**
	 * カラム幅取得
	 * 
	 * @return short[] カラム幅配列
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#getColumnWidths()
	 */
	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 15, 11, 11, 10, 10, 12, 8 };
	}

	/**
	 * オブジェクトをArrayListに変換
	 * 
	 * @return ログリスト
	 * @see jp.co.ais.trans.master.common.ReportExcelDefine#convertDataToList(java.lang.Object, java.lang.String)
	 */
	public List convertDataToList(Object dto, String lang) {
		ExecutedLog entity = (ExecutedLog) dto;
		List list = new ArrayList();
		list.add(DateUtil.toYMDHMSString(entity.getEXCUTED_DATE()));
		list.add(entity.getUSR_CODE());
		list.add(entity.getUSR_NAME());
		list.add(entity.getIP_ADDRESS());
		list.add(entity.getPROGRAM_CODE());
		list.add(entity.getPROGRAM_NAME());
		list.add(entity.getSTATE());

		return list;

	}
}
