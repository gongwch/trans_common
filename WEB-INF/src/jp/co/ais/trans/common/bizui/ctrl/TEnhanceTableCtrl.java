package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.define.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 拡張スプレッドのCTRL
 * 
 * @author Yanwei
 */
public class TEnhanceTableCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "TEnhanceTableServlet";

	/** フィールド */
	private TEnhanceTable field;

	/**
	 * コンストラクタ
	 * 
	 * @param tEnhanceTable 拡張スプレッド
	 */
	public TEnhanceTableCtrl(TEnhanceTable tEnhanceTable) {
		try {
			this.field = tEnhanceTable;

		} catch (Exception e) {
			errorHandler(field, e);
		}
	}

	/**
	 * ユーザー設定幅情報を取得する
	 * 
	 * @param prgId プログラムID
	 * @return スプレッドシート制御マスタ
	 */
	public COL_SLT_MST getWidths(String prgId) {
		COL_SLT_MST bean = null;

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "getWidths");
			addSendValues("programId", prgId);

			if (!request(TARGET_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field);
				return null;
			}
			// サーブレットから送られてきた結果を配列にセット
			bean = (COL_SLT_MST) super.getResultDto(COL_SLT_MST.class);

		} catch (IOException e) {
			errorHandler(field);
		}
		return bean;
	}

	/**
	 * カラム幅を保存する
	 * 
	 * @param widths カラム幅
	 * @param programId プログラムID
	 */
	public void saveColumnWidths(int[] widths, String programId) {
		try {
			// スプレッドシート制御マスタ
			COL_SLT_MST bean = new COL_SLT_MST();

			bean.setKAI_CODE(TClientLoginInfo.getInstance().getCompanyCode());// 会社コード
			bean.setUSR_ID(TClientLoginInfo.getInstance().getUserCode()); // ユーザーＩＤ
			bean.setPRG_ID(programId); // プログラムID

			bean.setWIDTH_00(widths[ColEvn.COL0.ordinal()] == 0 ? null : widths[ColEvn.COL0.ordinal()]); // 計上会社コード
			bean.setWIDTH_01(widths[ColEvn.COL1.ordinal()] == 0 ? null : widths[ColEvn.COL1.ordinal()]); // 計上会社
			bean.setWIDTH_02(widths[ColEvn.COL2.ordinal()] == 0 ? null : widths[ColEvn.COL2.ordinal()]); // 部門コード
			bean.setWIDTH_03(widths[ColEvn.COL3.ordinal()] == 0 ? null : widths[ColEvn.COL3.ordinal()]); // 部門
			bean.setWIDTH_04(widths[ColEvn.COL4.ordinal()] == 0 ? null : widths[ColEvn.COL4.ordinal()]); // 科目コード
			bean.setWIDTH_05(widths[ColEvn.COL5.ordinal()] == 0 ? null : widths[ColEvn.COL5.ordinal()]); // 科目
			bean.setWIDTH_06(widths[ColEvn.COL6.ordinal()] == 0 ? null : widths[ColEvn.COL6.ordinal()]); // 補助科目コード
			bean.setWIDTH_07(widths[ColEvn.COL7.ordinal()] == 0 ? null : widths[ColEvn.COL7.ordinal()]); // 補助科目
			bean.setWIDTH_08(widths[ColEvn.COL8.ordinal()] == 0 ? null : widths[ColEvn.COL8.ordinal()]); // 内訳科目コード
			bean.setWIDTH_09(widths[ColEvn.COL9.ordinal()] == 0 ? null : widths[ColEvn.COL9.ordinal()]); // 内訳科目
			bean.setWIDTH_10(widths[ColEvn.COL10.ordinal()] == 0 ? null : widths[ColEvn.COL10.ordinal()]); // 税
			bean.setWIDTH_11(widths[ColEvn.COL11.ordinal()] == 0 ? null : widths[ColEvn.COL11.ordinal()]); // 消費税コード
			bean.setWIDTH_12(widths[ColEvn.COL12.ordinal()] == 0 ? null : widths[ColEvn.COL12.ordinal()]); // 消費税名称
			bean.setWIDTH_13(widths[ColEvn.COL13.ordinal()] == 0 ? null : widths[ColEvn.COL13.ordinal()]); // 税率
			bean.setWIDTH_14(widths[ColEvn.COL14.ordinal()] == 0 ? null : widths[ColEvn.COL14.ordinal()]); // 借方金額
			bean.setWIDTH_15(widths[ColEvn.COL15.ordinal()] == 0 ? null : widths[ColEvn.COL15.ordinal()]); // 消費税額
			bean.setWIDTH_16(widths[ColEvn.COL16.ordinal()] == 0 ? null : widths[ColEvn.COL16.ordinal()]); // 貸方金額
			bean.setWIDTH_17(widths[ColEvn.COL17.ordinal()] == 0 ? null : widths[ColEvn.COL17.ordinal()]); // 行摘要コード
			bean.setWIDTH_18(widths[ColEvn.COL18.ordinal()] == 0 ? null : widths[ColEvn.COL18.ordinal()]); // 行摘要
			bean.setWIDTH_19(widths[ColEvn.COL19.ordinal()] == 0 ? null : widths[ColEvn.COL19.ordinal()]); // 取引先コード
			bean.setWIDTH_20(widths[ColEvn.COL20.ordinal()] == 0 ? null : widths[ColEvn.COL20.ordinal()]); // 取引先
			bean.setWIDTH_21(widths[ColEvn.COL21.ordinal()] == 0 ? null : widths[ColEvn.COL21.ordinal()]); // 社員コード
			bean.setWIDTH_22(widths[ColEvn.COL22.ordinal()] == 0 ? null : widths[ColEvn.COL22.ordinal()]); // 社員
			bean.setWIDTH_23(widths[ColEvn.COL23.ordinal()] == 0 ? null : widths[ColEvn.COL23.ordinal()]); // 管理1コード
			bean.setWIDTH_24(widths[ColEvn.COL24.ordinal()] == 0 ? null : widths[ColEvn.COL24.ordinal()]); // 管理1
			bean.setWIDTH_25(widths[ColEvn.COL25.ordinal()] == 0 ? null : widths[ColEvn.COL25.ordinal()]); // 管理2コード
			bean.setWIDTH_26(widths[ColEvn.COL26.ordinal()] == 0 ? null : widths[ColEvn.COL26.ordinal()]); // 管理2
			bean.setWIDTH_27(widths[ColEvn.COL27.ordinal()] == 0 ? null : widths[ColEvn.COL27.ordinal()]); // 管理3コード
			bean.setWIDTH_28(widths[ColEvn.COL28.ordinal()] == 0 ? null : widths[ColEvn.COL28.ordinal()]); // 管理3
			bean.setWIDTH_29(widths[ColEvn.COL29.ordinal()] == 0 ? null : widths[ColEvn.COL29.ordinal()]); // 管理4コード
			bean.setWIDTH_30(widths[ColEvn.COL30.ordinal()] == 0 ? null : widths[ColEvn.COL30.ordinal()]); // 管理4
			bean.setWIDTH_31(widths[ColEvn.COL31.ordinal()] == 0 ? null : widths[ColEvn.COL31.ordinal()]); // 管理5コード
			bean.setWIDTH_32(widths[ColEvn.COL32.ordinal()] == 0 ? null : widths[ColEvn.COL32.ordinal()]); // 管理5
			bean.setWIDTH_33(widths[ColEvn.COL33.ordinal()] == 0 ? null : widths[ColEvn.COL33.ordinal()]); // 管理6コード
			bean.setWIDTH_34(widths[ColEvn.COL34.ordinal()] == 0 ? null : widths[ColEvn.COL34.ordinal()]); // 管理6
			bean.setWIDTH_35(widths[ColEvn.COL35.ordinal()] == 0 ? null : widths[ColEvn.COL35.ordinal()]); // 非会計明細1
			bean.setWIDTH_36(widths[ColEvn.COL36.ordinal()] == 0 ? null : widths[ColEvn.COL36.ordinal()]); // 非会計明細2
			bean.setWIDTH_37(widths[ColEvn.COL37.ordinal()] == 0 ? null : widths[ColEvn.COL37.ordinal()]); // 非会計明細3
			bean.setWIDTH_38(widths[ColEvn.COL38.ordinal()] == 0 ? null : widths[ColEvn.COL38.ordinal()]); // 通貨コード
			bean.setWIDTH_39(widths[ColEvn.COL39.ordinal()] == 0 ? null : widths[ColEvn.COL39.ordinal()]); // 通貨レート
			bean.setWIDTH_40(widths[ColEvn.COL40.ordinal()] == 0 ? null : widths[ColEvn.COL40.ordinal()]); // 入力金額
			bean.setWIDTH_41(widths[ColEvn.COL41.ordinal()] == 0 ? null : widths[ColEvn.COL41.ordinal()]); // 金額
			bean.setWIDTH_42(widths[ColEvn.COL42.ordinal()] == 0 ? null : widths[ColEvn.COL42.ordinal()]); // 通貨コード表示
			bean.setWIDTH_43(widths[ColEvn.COL43.ordinal()] == 0 ? null : widths[ColEvn.COL43.ordinal()]); // 通貨レート表示
			bean.setWIDTH_44(widths[ColEvn.COL44.ordinal()] == 0 ? null : widths[ColEvn.COL44.ordinal()]); // 借方金額外貨
			bean.setWIDTH_45(widths[ColEvn.COL45.ordinal()] == 0 ? null : widths[ColEvn.COL45.ordinal()]); // 貸方金額外貨
			bean.setWIDTH_46(widths[ColEvn.COL46.ordinal()] == 0 ? null : widths[ColEvn.COL46.ordinal()]); // 発生日

			bean.setSAVE_MODE(1); // セーブモード 0：状態を保存しない 1：状態を保存する

			// 送信するパラメータを設定
			addSendValues("flag", "saveColumnWidths");
			this.addSendDto(bean);

			if (!request(TARGET_SERVLET)) {
				errorHandler(field);// クライアント 受信解析エラー。
				return;
			}

		} catch (IOException e) {
			errorHandler(field);
		}
	}
}
