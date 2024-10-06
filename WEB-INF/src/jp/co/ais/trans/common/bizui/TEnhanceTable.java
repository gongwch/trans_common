package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.define.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 拡張スプレッド
 * 
 * @author Yanwei
 */
public class TEnhanceTable extends TTable {

	/** プログラムID */
	private String programId = "COM16";

	/** 拡張スプレッドのCTRL */
	private TEnhanceTableCtrl ctrl;

	/**
	 * コンストラクタ
	 */
	public TEnhanceTable() {
		super();
		ctrl = new TEnhanceTableCtrl(this);
	}

	/**
	 * コンストラクタ プログラムIDを設定する。
	 * 
	 * @param prgId プログラムID
	 */
	public TEnhanceTable(String prgId) {
		this.programId = prgId;
		ctrl = new TEnhanceTableCtrl(this);
	}

	/**
	 * コンストラクタ プログラムIDを枝番付で設定する。
	 * 
	 * @param prgId プログラムID
	 * @param subNo 支払確定起動日
	 */
	public TEnhanceTable(String prgId, int subNo) {
		this.programId = prgId + "-" + subNo;
	}

	/**
	 * 引数の配列でカラムの幅を設定する（DBデータの状態に依存しない）
	 * 
	 * @param widths 幅リスト
	 */
	@Override
	public void setColumnWidths(int[] widths) {
		// ①ユーザー設定幅情報を取得する。
		COL_SLT_MST bean = ctrl.getWidths(programId);

		// データが存在しない場合
		if (bean == null) {
			this.setCustomColumnWidths(widths);// メソッド.引数.デフォルトカラム幅で幅の設定を行なう。
		}
		// データが存在する場合
		else {
			int[] colWidths = new int[48];

			colWidths[ColEvn.COL0.ordinal()] = bean.getWIDTH_00() == null ? 0 : bean.getWIDTH_00();// 計上会社コード
			colWidths[ColEvn.COL1.ordinal()] = bean.getWIDTH_01() == null ? 0 : bean.getWIDTH_01();// 計上会社
			colWidths[ColEvn.COL2.ordinal()] = bean.getWIDTH_02() == null ? 0 : bean.getWIDTH_02();// 部門コード
			colWidths[ColEvn.COL3.ordinal()] = bean.getWIDTH_03() == null ? 0 : bean.getWIDTH_03();// 部門
			colWidths[ColEvn.COL4.ordinal()] = bean.getWIDTH_04() == null ? 0 : bean.getWIDTH_04();// 科目コード
			colWidths[ColEvn.COL5.ordinal()] = bean.getWIDTH_05() == null ? 0 : bean.getWIDTH_05();// 科目
			colWidths[ColEvn.COL6.ordinal()] = bean.getWIDTH_06() == null ? 0 : bean.getWIDTH_06();// 補助科目コード
			colWidths[ColEvn.COL7.ordinal()] = bean.getWIDTH_07() == null ? 0 : bean.getWIDTH_07();// 補助科目
			colWidths[ColEvn.COL8.ordinal()] = bean.getWIDTH_08() == null ? 0 : bean.getWIDTH_08();// 内訳科目コード
			colWidths[ColEvn.COL9.ordinal()] = bean.getWIDTH_09() == null ? 0 : bean.getWIDTH_09();// 内訳科目
			colWidths[ColEvn.COL10.ordinal()] = bean.getWIDTH_10() == null ? 0 : bean.getWIDTH_10();// 税
			colWidths[ColEvn.COL11.ordinal()] = bean.getWIDTH_11() == null ? 0 : bean.getWIDTH_11();// 消費税コード
			colWidths[ColEvn.COL12.ordinal()] = bean.getWIDTH_12() == null ? 0 : bean.getWIDTH_12();// 消費税名称
			colWidths[ColEvn.COL13.ordinal()] = bean.getWIDTH_13() == null ? 0 : bean.getWIDTH_13();// 税率
			colWidths[ColEvn.COL14.ordinal()] = bean.getWIDTH_14() == null ? 0 : bean.getWIDTH_14();// 借方金額
			colWidths[ColEvn.COL15.ordinal()] = bean.getWIDTH_15() == null ? 0 : bean.getWIDTH_15();// 消費税額
			colWidths[ColEvn.COL16.ordinal()] = bean.getWIDTH_16() == null ? 0 : bean.getWIDTH_16();// 貸方金額
			colWidths[ColEvn.COL17.ordinal()] = bean.getWIDTH_17() == null ? 0 : bean.getWIDTH_17();// 行摘要コード
			colWidths[ColEvn.COL18.ordinal()] = bean.getWIDTH_18() == null ? 0 : bean.getWIDTH_18();// 行摘要
			colWidths[ColEvn.COL19.ordinal()] = bean.getWIDTH_19() == null ? 0 : bean.getWIDTH_19();// 取引先コード
			colWidths[ColEvn.COL20.ordinal()] = bean.getWIDTH_20() == null ? 0 : bean.getWIDTH_20();// 取引先
			colWidths[ColEvn.COL21.ordinal()] = bean.getWIDTH_21() == null ? 0 : bean.getWIDTH_21();// 社員コード
			colWidths[ColEvn.COL22.ordinal()] = bean.getWIDTH_22() == null ? 0 : bean.getWIDTH_22();// 社員
			colWidths[ColEvn.COL23.ordinal()] = bean.getWIDTH_23() == null ? 0 : bean.getWIDTH_23();// 管理1コード
			colWidths[ColEvn.COL24.ordinal()] = bean.getWIDTH_24() == null ? 0 : bean.getWIDTH_24();// 管理1
			colWidths[ColEvn.COL25.ordinal()] = bean.getWIDTH_25() == null ? 0 : bean.getWIDTH_25();// 管理2コード
			colWidths[ColEvn.COL26.ordinal()] = bean.getWIDTH_26() == null ? 0 : bean.getWIDTH_26();// 管理2
			colWidths[ColEvn.COL27.ordinal()] = bean.getWIDTH_27() == null ? 0 : bean.getWIDTH_27();// 管理3コード
			colWidths[ColEvn.COL28.ordinal()] = bean.getWIDTH_28() == null ? 0 : bean.getWIDTH_28();// 管理3
			colWidths[ColEvn.COL29.ordinal()] = bean.getWIDTH_29() == null ? 0 : bean.getWIDTH_29();// 管理4コード
			colWidths[ColEvn.COL30.ordinal()] = bean.getWIDTH_30() == null ? 0 : bean.getWIDTH_30();// 管理4
			colWidths[ColEvn.COL31.ordinal()] = bean.getWIDTH_31() == null ? 0 : bean.getWIDTH_31();// 管理5コード
			colWidths[ColEvn.COL32.ordinal()] = bean.getWIDTH_32() == null ? 0 : bean.getWIDTH_32();// 管理5
			colWidths[ColEvn.COL33.ordinal()] = bean.getWIDTH_33() == null ? 0 : bean.getWIDTH_33();// 管理6コード
			colWidths[ColEvn.COL34.ordinal()] = bean.getWIDTH_34() == null ? 0 : bean.getWIDTH_34();// 管理6
			colWidths[ColEvn.COL35.ordinal()] = bean.getWIDTH_35() == null ? 0 : bean.getWIDTH_35();// 非会計明細1
			colWidths[ColEvn.COL36.ordinal()] = bean.getWIDTH_36() == null ? 0 : bean.getWIDTH_36();// 非会計明細2
			colWidths[ColEvn.COL37.ordinal()] = bean.getWIDTH_37() == null ? 0 : bean.getWIDTH_37();// 非会計明細3
			colWidths[ColEvn.COL38.ordinal()] = bean.getWIDTH_38() == null ? 0 : bean.getWIDTH_38();// 通貨コード
			colWidths[ColEvn.COL39.ordinal()] = bean.getWIDTH_39() == null ? 0 : bean.getWIDTH_39();// 通貨レート
			colWidths[ColEvn.COL40.ordinal()] = bean.getWIDTH_40() == null ? 0 : bean.getWIDTH_40();// 入力金額
			colWidths[ColEvn.COL41.ordinal()] = bean.getWIDTH_41() == null ? 0 : bean.getWIDTH_41();// 金額
			colWidths[ColEvn.COL42.ordinal()] = bean.getWIDTH_42() == null ? 0 : bean.getWIDTH_42();// 通貨コード表示
			colWidths[ColEvn.COL43.ordinal()] = bean.getWIDTH_43() == null ? 0 : bean.getWIDTH_43();// 通貨レート表示
			colWidths[ColEvn.COL44.ordinal()] = bean.getWIDTH_44() == null ? 0 : bean.getWIDTH_44();// 借方金額外貨
			colWidths[ColEvn.COL45.ordinal()] = bean.getWIDTH_45() == null ? 0 : bean.getWIDTH_45();// 貸方金額外貨
			colWidths[ColEvn.COL46.ordinal()] = bean.getWIDTH_46() == null ? 0 : bean.getWIDTH_46();// 発生日

			colWidths[ColEvn.COL47.ordinal()] = 0;// Bean

			// セル幅のセット
			this.setCustomColumnWidths(colWidths);
		}
	}

	/**
	 * セル幅のセット
	 * 
	 * @param widths 幅リスト
	 */
	public void setCustomColumnWidths(int[] widths) {
		for (ColEvn col : ColEvn.values()) {
			if (widths[col.ordinal()] <= 0) {
				this.setColumnHidden(col.ordinal(), true);
			} else {
				this.setPixelWidth(col.ordinal(), widths[col.ordinal()]);
				this.setColumnHidden(col.ordinal(), false);
			}
		}

	}

	/**
	 * カラム幅を保存する
	 */
	public void saveColumnWidths() {
		int[] widths = new int[48];

		for (ColEvn col : ColEvn.values()) {
			if (this.getPixelWidth(col.ordinal()) < 0 || isColumnHidden(col.ordinal())) {
				widths[col.ordinal()] = 0;
			} else {
				widths[col.ordinal()] = this.getPixelWidth(col.ordinal());
			}
		}

		// カラム幅を保存する
		ctrl.saveColumnWidths(widths, programId);
	}

	/**
	 * プログラムIDの取得
	 * 
	 * @return プログラムID
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * プログラムIDのセット
	 * 
	 * @param programId プログラムID
	 */
	public void setProgramId(String programId) {
		this.programId = programId;
	}

}
