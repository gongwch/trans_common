package jp.co.ais.trans.master.common;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 共通マスタ検索ダイアログ
 * 
 * @author Yit
 */
public class REFDialogMasterCtrl extends TAppletClientBase {

	/** ダイアログ */
	protected REFDialogMaster dialog;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "REFDialogMasterServlet";

	/** 科目マスタ一覧 */
	public static final int KMK_MST = 1;

	/** 補助科目マスタ一覧 */
	public static final int HKM_MST = 2;

	/** 内訳科目マスタ一覧 */
	public static final int UKM_MST = 3;

	/** 支払条件マスタ一覧 */
	public static final int PAYMENTCON_MST = 4;

	/** 科目体系マスタ一覧 */
	public static final int KMK_TK_MST = 5;

	/** 社員マスタ一覧 */
	public static final int EMP_MST = 10;

	/** 銀行口座マスタ */
	public static final int BANK_ACCOUNT = 11;

	/** 取引先マスタ */
	public static final int CUSTOMER_MST = 12;

	/** 通貨マスタ */
	public static final int CURRENCY_MST = 13;

	/** 部門マスタ */
	public static final int DEPARTMENT_MST = 14;

	/** 伝票摘要と行摘要マスタ（共通） */
	public static final int MEMO_MST = 15;

	/** 消費税マスタ */
	public static final int TAX_MST = 16;

	/** 社員検索マスタ */
	public static final int EMP_SEARCH_MST = 17;

	/** 支払い方法マスタ */
	public static final int PAYMENT_MST = 18;

	/** 部門マスタ一覧 */
	public static final int BMN_MST = 19;

	/** 管理1マスタ一覧 */
	public static final int KNR1_MST = 20;

	/** 管理2マスタ一覧 */
	public static final int KNR2_MST = 21;

	/** 管理3マスタ一覧 */
	public static final int KNR3_MST = 22;

	/** 管理4マスタ一覧 */
	public static final int KNR4_MST = 23;

	/** 管理5マスタ一覧 */
	public static final int KNR5_MST = 24;

	/** 管理6マスタ一覧 */
	public static final int KNR6_MST = 25;

	/** 環境設定マスタ */
	public static final int ENV_MST = 28;

	/** 銀行口座マスタ(銀行名に銀行マスタ.銀行名+銀行マスタ.支店名を表示する) */
	public static final int BANK_ACCOUNT_B = 29;

	/** 計上会社 */
	public static final int APP_KAI = 30;

	/** 銀行マスタ */
	public static final int BNK_BNK = 31;

	/** 銀行マスタ（支店） */
	public static final int BNK_STN = 32;

	/** アクセステープ */
	protected int gamenType = 0;

	/** ＧＬ科目制御区分 */
	protected String kmkCntGl = "";

	/** 科目コード */
	protected String kmkCode = "";

	/** 伝票日付 */
	protected String slipDate = "";

	/** BS勘定消込区分 */
	protected String kesiKbn = "";

	/** 集計区分 */
	protected String sumKbn = "";

	/** 会社コード */
	protected String kaiCode = "";

	/** 振替伝票入力ﾌﾗｸﾞ */
	protected String furikaeFlg = "";

	/** AR科目制御区分 */
	protected String kmkCntAr = "";

	/** AR科目制御区分(消込用) */
	protected String kmkCntUnAr = "";

	/** AP科目制御区分 */
	protected String kmkCntAp = "";

	/** 経費精算伝票入力ﾌﾗｸﾞ */
	protected String keihiFlg = "";

	/** 債務計上入力ﾌﾗｸﾞ */
	protected String saimuFlg = "";

	/** 債権計上入力ﾌﾗｸﾞ */
	protected String saikenFlg = "";

	/** 債権消込伝票入力ﾌﾗｸﾞ */
	protected String saikesiFlg = "";

	/** 資産計上伝票入力ﾌﾗｸﾞ */
	protected String sisanFlg = "";

	/** 支払依頼伝票入力ﾌﾗｸﾞ */
	protected String siharaiFlg = "";

	/** 補助科目コード */
	protected String hkmCode = "";

	/** 部門コード */
	protected String bmnCode = "";

	/** 評価替対象フラグ */
	protected String excFlg = "";

	/** 入金伝票入力フラグ */
	protected String nyuKin = "";

	/** 出金伝票入力フラグ */
	protected String shutsuKin = "";

	/** 組織コード */
	protected String dpkSsk = "";

	/** 配下部門(0:含む 1:含まない) */
	protected String bmnKbn = "";

	/** 上位部門ｺｰﾄﾞ */
	protected String upBmnCode = "";

	/** 階層ﾚﾍﾞﾙ */
	protected String dpkLvl = "";

	/** 初期部門ｺｰﾄﾞ */
	protected String baseBmnCode = "";

	/** 初期階層ﾚﾍﾞﾙ */
	protected String baseDpkLvl = "";

	/** 開始コード */
	protected String beginCode = "";

	/** 終了コード */
	protected String endCode = "";

	/** 科目体系コード */
	protected String kmtCode = "";

	/** 科目体系フラグ */
	protected String kmtFlg = "";

	/** 科目体系区分 */
	protected String kmtKbn = "";

	/** 科目種別 */
	protected String kmkShu = "";

	/** 貸借区分 */
	protected String dcKbn = "";

	/** 補助区分 */
	protected String hkmKbn = "";

	/** 内訳区分 */
	protected String ukmKbn = "";

	/** 固定部門ｺｰﾄﾞ */
	protected String koteiDepCode = "";

	/** 消費税ｺｰﾄﾞ */
	protected String zeiCode = "";

	/** 取引先入力フラグ */
	protected String triCodeFlg = "";

	/** 発生日入力フラグ */
	protected String hasFlg = "";

	/** 社員入力フラグ */
	protected String empCodeFlg = "";

	/** 管理１入力ﾌﾗｸﾞ */
	protected String knrFlg1 = "";

	/** 管理２入力ﾌﾗｸﾞ */
	protected String knrFlg2 = "";

	/** 管理３入力ﾌﾗｸﾞ */
	protected String knrFlg3 = "";

	/** 管理４入力ﾌﾗｸﾞ */
	protected String knrFlg4 = "";

	/** 管理５入力ﾌﾗｸﾞ */
	protected String knrFlg5 = "";

	/** 管理６入力ﾌﾗｸﾞ */
	protected String knrFlg6 = "";

	/** 非会計１入力ﾌﾗｸﾞ */
	protected String hmFlg1 = "";

	/** 非会計２入力ﾌﾗｸﾞ */
	protected String hmFlg2 = "";

	/** 非会計３入力ﾌﾗｸﾞ */
	protected String hmFlg3 = "";

	/** 売上課税入力ﾌﾗｸﾞ */
	protected String uriZeiFlg = "";

	/** 仕入課税入力ﾌﾗｸﾞ */
	protected String sirZeiFlg = "";

	/** 多通貨フラグ */
	protected String mcrFlg = "";

	/** BG科目制御区分 */
	protected String kmkCntBg = "";

	/** 相殺精算制御区分 */
	protected String kmkCntSousai = "";

	/** 所属部門コード区分 */
	protected String depCodeKbn = "";

	/** 清算対象フラグ */
	protected String clearanceFlg = "";

	/** 所属部門コード */
	protected String depCode = "";

	/** 参照カラム数 : DataSourceに対しgetNumColumn()設定数以上の参照をするとき、そのカラム数を設定する */
	protected int numReferColumn = 0;

	/** 通貨区分 */
	private boolean curKbn;

	/** サーバー名 */
	private String serverName;

	/** 開始コード */
	private String startCode = "";

	/** 銀行口座コード */
	protected List<String> cbkCodes;

	/** コードリスト */
	protected List<String> codes;

	/** dialog用パラメタ */
	protected Map<String, String> paramMap;

	/** パラメータクラス保持用 */
	protected TransferBase paramObject;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param gamenKbn 画面区分
	 */
	public REFDialogMasterCtrl(TPanel parent, int gamenKbn) {
		this(parent.getParentFrame(), gamenKbn);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param gamenKbn 画面区分
	 */
	public REFDialogMasterCtrl(Frame parent, int gamenKbn) {
		dialog = new REFDialogMaster(parent, true, this, gamenKbn);

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * コンストラクタ(ダイアログ時)
	 * 
	 * @param parent
	 * @param gamenKbn
	 */
	public REFDialogMasterCtrl(Dialog parent, int gamenKbn) {
		dialog = new REFDialogMaster(parent, true, this, gamenKbn);

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param comp 対象コンポーネント
	 * @param gamenKbn 画面区分
	 */
	public REFDialogMasterCtrl(Component comp, int gamenKbn) {
		Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

		if (parent instanceof Frame) {
			dialog = new REFDialogMaster((Frame) parent, true, this, gamenKbn);
		} else if (parent instanceof Dialog) {
			dialog = new REFDialogMaster((Dialog) parent, true, this, gamenKbn);
		}

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param comp 対象コンポーネント
	 * @param gamenKbn 画面区分
	 * @param param 各種パラメータ(タイトル等)
	 */
	public REFDialogMasterCtrl(Component comp, int gamenKbn, Map param) {

		param.put("kbn", String.valueOf(gamenKbn));// 画面構成集類

		Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

		if (parent instanceof Frame) {
			dialog = new REFDialogMaster((Frame) parent, true, this, param);
		} else if (parent instanceof Dialog) {
			dialog = new REFDialogMaster((Dialog) parent, true, this, param);
		}

		gamenType = gamenKbn;
		lockBtn(false);
	}

	/**
	 * 表示<br>
	 * 検索ダイアログエリア編集可(true)
	 */
	public void show() {
		dialog.setVisible(true);
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	public boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * 確定ボタン制御
	 * 
	 * @param bol
	 */
	void lockBtn(boolean bol) {
		dialog.btnSettle.setEnabled(bol);
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		dialog.setVisible(false);
		dialog.dispose();
	}

	/**
	 * 検索処理
	 */
	public void searchData() {
		searchData(true);
	}

	/**
	 * 検索処理
	 * 
	 * @param msgFlg メッセージ表示フラグ true：表示 false:表示しない
	 */
	public void searchData(boolean msgFlg) {

		try {
			this.reflesh();

			if (dialog.ssJournal.getDataSource().getNumRows() == 0) {
				// 検索結果無しメッセージ表示
				if (msgFlg) {
					super.showMessage(dialog, "W00100");
				}
				dialog.txtCode.requestFocus();
				return;
			} else {
				dialog.ssJournal.requestFocus();
			}
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * ＧＬ科目制御区分を設定する。<BR>
	 * 
	 * @param kmkCntGl ＧＬ科目制御区分
	 */
	public void setKmkCntGl(String kmkCntGl) {
		this.kmkCntGl = kmkCntGl;
	}

	/**
	 * 科目コードを設定する。<BR>
	 * 
	 * @param strKmkCode 科目コード
	 */
	public void setKmkCode(String strKmkCode) {
		this.kmkCode = strKmkCode;
	}

	/**
	 * 伝票日付を設定する。
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(String slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * BS勘定消込区分を設定する。
	 * 
	 * @param kesiKbn BS勘定消込区分
	 */
	public void setKesiKbn(String kesiKbn) {
		this.kesiKbn = kesiKbn;
	}

	/**
	 * 集計区分を設定する。
	 * 
	 * @param sumKbn 集計区分
	 */
	public void setSumKbn(String sumKbn) {
		this.sumKbn = sumKbn;
	}

	/**
	 * 科目種別を設定する。
	 * 
	 * @param kmkShu 科目種別
	 */
	public void setKmkShu(String kmkShu) {
		this.kmkShu = kmkShu;
	}

	/**
	 * 貸借区分を設定する。
	 * 
	 * @param dcKbn 貸借区分
	 */
	public void setDcKbn(String dcKbn) {
		this.dcKbn = dcKbn;
	}

	/**
	 * 補助区分を設定する。
	 * 
	 * @param hkmKbn 補助区分
	 */
	public void setHkmKbn(String hkmKbn) {
		this.hkmKbn = hkmKbn;
	}

	/**
	 * 内訳区分を設定する。
	 * 
	 * @param ukmKbn 内訳区分
	 */
	public void setUkmKbn(String ukmKbn) {
		this.ukmKbn = ukmKbn;
	}

	/**
	 * 会社コードを設定する。
	 * 
	 * @param kaiCode 会社コード
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * 振替伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param furikaeFlg 振替伝票入力ﾌﾗｸﾞ
	 */
	public void setFurikaeFlg(String furikaeFlg) {
		this.furikaeFlg = furikaeFlg;
	}

	/**
	 * AR制御区分を設定する。
	 * 
	 * @param kmkCntAr AR制御区分
	 */
	public void setkmkCntAr(String kmkCntAr) {
		this.kmkCntAr = kmkCntAr;
	}

	/**
	 * AR制御区分を設定する。(消込用)
	 * 
	 * @param kmkCntUnAr AR制御区分
	 */
	public void setkmkCntUnAr(String kmkCntUnAr) {
		this.kmkCntUnAr = kmkCntUnAr;
	}

	/**
	 * AP制御区分を設定する。
	 * 
	 * @param kmkCntAp AR制御区分
	 */
	public void setkmkCntAp(String kmkCntAp) {
		this.kmkCntAp = kmkCntAp;
	}

	/**
	 * 固定部門ｺｰﾄﾞを設定する。
	 * 
	 * @param koteiDepCode 固定部門ｺｰﾄﾞ
	 */
	public void setKoteiDepCode(String koteiDepCode) {
		this.koteiDepCode = koteiDepCode;
	}

	/**
	 * 消費税ｺｰﾄﾞを設定する。
	 * 
	 * @param zeiCode 消費税ｺｰﾄﾞ
	 */
	public void setZeiCode(String zeiCode) {
		this.zeiCode = zeiCode;
	}

	/**
	 * 経費精算伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param keihiFlg 経費精算伝票入力ﾌﾗｸﾞ
	 */
	public void setKeihiFlg(String keihiFlg) {
		this.keihiFlg = keihiFlg;
	}

	/**
	 * 債務計上入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saimuFlg 債務計上入力ﾌﾗｸﾞ
	 */
	public void setSaimuFlg(String saimuFlg) {
		this.saimuFlg = saimuFlg;
	}

	/**
	 * 債権計上入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saikenFlg 債権計上入力ﾌﾗｸﾞ
	 */
	public void setSaikenFlg(String saikenFlg) {
		this.saikenFlg = saikenFlg;
	}

	/**
	 * 債権消込伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param saikesiFlg 債権消込伝票入力ﾌﾗｸﾞ
	 */
	public void setSaikesiFlg(String saikesiFlg) {
		this.saikesiFlg = saikesiFlg;
	}

	/**
	 * 資産計上伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param sisanFlg 資産計上伝票入力ﾌﾗｸﾞ
	 */
	public void setSisanFlg(String sisanFlg) {
		this.sisanFlg = sisanFlg;
	}

	/**
	 * 支払依頼伝票入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param siharaiFlg 支払依頼伝票入力ﾌﾗｸﾞ
	 */
	public void setSiharaiFlg(String siharaiFlg) {
		this.siharaiFlg = siharaiFlg;
	}

	/**
	 * 取引先入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param triCodeFlg 取引先入力ﾌﾗｸﾞ
	 */
	public void setTriCodeFlag(String triCodeFlg) {
		this.triCodeFlg = triCodeFlg;
	}

	/**
	 * 発生日入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param hasFlg 発生日入力ﾌﾗｸﾞ
	 */
	public void setHasFlg(String hasFlg) {
		this.hasFlg = hasFlg;
	}

	/**
	 * 社員入力ﾌﾗｸﾞを設定する。
	 * 
	 * @param empCodeFlg 社員入力ﾌﾗｸﾞ
	 */
	public void setEmpCodeFlg(String empCodeFlg) {
		this.empCodeFlg = empCodeFlg;
	}

	/**
	 * 補助科目コードを設定する。
	 * 
	 * @param hkmCode 補助科目コード
	 */
	public void setHkmCode(String hkmCode) {
		this.hkmCode = hkmCode;
	}

	/**
	 * 部門コードを設定する。
	 * 
	 * @param bmnCode 部門コード
	 */
	public void setBmnCode(String bmnCode) {
		this.bmnCode = bmnCode;
	}

	/**
	 * 評価替対象フラグを設定する。
	 * 
	 * @param excFlg 評価替対象フラグ
	 */
	public void setExcFlg(String excFlg) {
		this.excFlg = excFlg;
	}

	/**
	 * 入金伝票入力フラグを設定する。
	 * 
	 * @param nyuKin 入金伝票入力フラグ
	 */
	public void setNyuKin(String nyuKin) {
		this.nyuKin = nyuKin;
	}

	/**
	 * 出金伝票入力フラグを設定する。
	 * 
	 * @param shutsuKin 出金伝票入力フラグ
	 */
	public void setShutsuKin(String shutsuKin) {
		this.shutsuKin = shutsuKin;
	}

	/**
	 * 組織コードフラグを設定する。
	 * 
	 * @param dpkSsk 組織コード
	 */
	public void setDpkSsk(String dpkSsk) {
		this.dpkSsk = dpkSsk;
	}

	/**
	 * 配下部門(0:含む 1:含まない)
	 * 
	 * @param bmnKbn 配下部門
	 */
	public void setBmnKbn(String bmnKbn) {
		this.bmnKbn = bmnKbn;
	}

	/**
	 * 上位部門ｺｰﾄﾞ
	 * 
	 * @param upBmnCode 上位部門ｺｰﾄﾞ
	 */
	public void setUpBmnCode(String upBmnCode) {
		this.upBmnCode = upBmnCode;
	}

	/**
	 * 階層ﾚﾍﾞﾙ
	 * 
	 * @param dpkLvl 階層ﾚﾍﾞﾙ
	 */
	public void setDpkLvl(String dpkLvl) {
		this.dpkLvl = dpkLvl;
	}

	/**
	 * 初期部門ｺｰﾄﾞ
	 * 
	 * @param baseBmnCode 初期部門ｺｰﾄﾞ
	 */
	public void setBaseBmnCode(String baseBmnCode) {
		this.baseBmnCode = baseBmnCode;
	}

	/**
	 * 初期階層ﾚﾍﾞﾙ
	 * 
	 * @param baseDpkLvl 初期階層ﾚﾍﾞﾙ
	 */
	public void setBaseDpkLvl(String baseDpkLvl) {
		this.baseDpkLvl = baseDpkLvl;
	}

	/**
	 * 開始コード
	 * 
	 * @param beginCode 開始コード
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * 終了コード
	 * 
	 * @param endCode 終了コード
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * 科目体系コード
	 * 
	 * @param kmtCode 科目体系コード
	 */
	public void setKmtCode(String kmtCode) {
		this.kmtCode = kmtCode;
	}

	/**
	 * 科目体系フラグ
	 * 
	 * @param kmtFlg 科目体系フラグ
	 */
	public void setKmtFlg(String kmtFlg) {
		this.kmtFlg = kmtFlg;
	}

	/**
	 * 科目体系区分
	 * 
	 * @param kmtKbn 科目体系区分
	 */
	public void setKmtKbn(String kmtKbn) {
		this.kmtKbn = kmtKbn;
	}

	/**
	 * 管理１入力ﾌﾗｸﾞ
	 * 
	 * @param knrFlg1 管理１入力ﾌﾗｸ
	 */
	public void setKnrFlg1(String knrFlg1) {
		this.knrFlg1 = knrFlg1;
	}

	/**
	 * 管理２入力ﾌﾗｸﾞ
	 * 
	 * @param knrFlg2 管理２入力ﾌﾗｸ
	 */
	public void setKnrFlg2(String knrFlg2) {
		this.knrFlg2 = knrFlg2;
	}

	/**
	 * 管理３入力ﾌﾗｸﾞ
	 * 
	 * @param knrFlg3 管理３入力ﾌﾗｸ
	 */
	public void setKnrFlg3(String knrFlg3) {
		this.knrFlg3 = knrFlg3;
	}

	/**
	 * 管理４入力ﾌﾗｸﾞ
	 * 
	 * @param knrFlg4 管理４入力ﾌﾗｸ
	 */
	public void setKnrFlg4(String knrFlg4) {
		this.knrFlg4 = knrFlg4;
	}

	/**
	 * 管理５入力ﾌﾗｸﾞ
	 * 
	 * @param knrFlg5 管理５入力ﾌﾗｸ
	 */
	public void setKnrFlg5(String knrFlg5) {
		this.knrFlg5 = knrFlg5;
	}

	/**
	 * 管理６入力ﾌﾗｸﾞ
	 * 
	 * @param knrFlg6 管理６入力ﾌﾗｸ
	 */
	public void setKnrFlg6(String knrFlg6) {
		this.knrFlg6 = knrFlg6;
	}

	/**
	 * 非会計１入力ﾌﾗｸﾞ
	 * 
	 * @param hmFlg1 非会計１入力ﾌﾗｸﾞ
	 */
	public void setHmFlg1(String hmFlg1) {
		this.hmFlg1 = hmFlg1;
	}

	/**
	 * 非会計２入力ﾌﾗｸﾞ
	 * 
	 * @param hmFlg2 非会計２入力ﾌﾗｸﾞ
	 */
	public void setHmFlg2(String hmFlg2) {
		this.hmFlg2 = hmFlg2;
	}

	/**
	 * 非会計３入力ﾌﾗｸﾞ
	 * 
	 * @param hmFlg3 非会計３入力ﾌﾗｸﾞ
	 */
	public void setHmFlg3(String hmFlg3) {
		this.hmFlg3 = hmFlg3;
	}

	/**
	 * 売上課税入力ﾌﾗｸﾞ
	 * 
	 * @param uriZeiFlg 売上課税入力ﾌﾗｸﾞ
	 */
	public void setUriZeiFlg(String uriZeiFlg) {
		this.uriZeiFlg = uriZeiFlg;
	}

	/**
	 * 仕入課税入力ﾌﾗｸﾞ
	 * 
	 * @param sirZeiFlg 仕入課税入力ﾌﾗｸﾞ
	 */
	public void setSirZeiFlg(String sirZeiFlg) {
		this.sirZeiFlg = sirZeiFlg;
	}

	/**
	 * 多通貨フラグを設定する。
	 * 
	 * @param mcrFlg 多通貨フラグ
	 */
	public void setMcrFlg(String mcrFlg) {
		this.mcrFlg = mcrFlg;
	}

	/**
	 * BG科目制御区分を設定する。
	 * 
	 * @param kmkCntBg BG科目制御区分
	 */
	public void setKmkCntBg(String kmkCntBg) {
		this.kmkCntBg = kmkCntBg;
	}

	/**
	 * 相殺精算制御区分を設定する。
	 * 
	 * @param kmkCntSousai 相殺精算制御区分
	 */
	public void setKmkCntSousai(String kmkCntSousai) {
		this.kmkCntSousai = kmkCntSousai;
	}

	/**
	 * 所属部門コード区分
	 * 
	 * @param depCodeKbn 所属部門コード区分
	 */
	public void setDepCodeKbn(String depCodeKbn) {
		this.depCodeKbn = depCodeKbn;
	}

	/**
	 * 所属部門コード
	 * 
	 * @param depCode 所属部門コード
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * 通貨区分の取得
	 * 
	 * @return curKbn
	 */
	public boolean isCurKbn() {
		return curKbn;
	}

	/**
	 * 通貨区分の設定
	 * 
	 * @param curKbn
	 */
	public void setCurKbn(boolean curKbn) {
		this.curKbn = curKbn;
	}

	/**
	 * dialog用 パラメタ（銀行口座）
	 * 
	 * @param paramMap 四つの情報（dailogの４つの入力フィールドから）
	 */
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 検索条件作成
	 */
	protected void setCondition() {
		switch (gamenType) {
			case KMK_MST:// 科目一覧マスタ検索
				setKmkMstCondition();
				break;
			case HKM_MST:
				// 補助科目マスタ一覧
				setHkmMstCondition();
				break;
			case UKM_MST:
				// 内訳科目マスタ一覧
				setUkmMstCondition();
				break;
			case BMN_MST:
				// 部門マスタ一覧
				setBmnMstCondition();
				break;
			case ENV_MST:
				// 部門マスタ一覧
				setEnvMstCondition();
				break;
			case KMK_TK_MST:
				// 科目体系マスタ一覧
				setKmkTkMstCondition();
				break;
			case BANK_ACCOUNT:// 銀行口座マスタ一覧
				setSearchCondition(serverName);
				break;
			case CUSTOMER_MST: // 取引先マスタ一覧
				setCustomerCondition(serverName);
				break;
			case CURRENCY_MST:// 通貨マスタ一覧
				setCurrencyCondition(serverName);
				break;
			case DEPARTMENT_MST:// 部門マスタ一覧
				setDepartmentCondition(serverName);
				break;
			case MEMO_MST:// 摘要マスタ一覧
				setMemoCondition(serverName);
				break;
			case TAX_MST:// 消費税マスタ一覧
				setTaxCondition(serverName);
				break;
			case EMP_SEARCH_MST:// 社員検索マスタ一覧
				setEmployeeCondition(serverName);
				break;
			case PAYMENT_MST:// 支払い方法マスタ一覧
				setPaymentCondition(serverName);
				break;
			case PAYMENTCON_MST:// 支払条件マスタ一覧
				setPaymentConCondition(serverName);
				break;
			case EMP_MST:
				// 社員マスタ一覧
				setEmpMstCondition();
				break;
			case KNR1_MST:
				// 管理1マスタ一覧
				setKnrMstCondition("1");
				break;
			case KNR2_MST:
				// 管理2マスタ一覧
				setKnrMstCondition("2");
				break;
			case KNR3_MST:
				// 管理3マスタ一覧
				setKnrMstCondition("3");
				break;
			case KNR4_MST:
				// 管理4マスタ一覧
				setKnrMstCondition("4");
				break;
			case KNR5_MST:
				// 管理5マスタ一覧
				setKnrMstCondition("5");
				break;
			case KNR6_MST:
				// 管理6マスタ一覧
				setKnrMstCondition("6");
				break;
			case BANK_ACCOUNT_B:// 銀行口座マスタ一覧
				setBankSearchCondition(serverName);
				break;
			case APP_KAI:// 計上会社
				setAppropriateCompanyCondition();
				break;
			case BNK_BNK:// 銀行一覧
				setBnkMstCondition(serverName);
				break;
			case BNK_STN:// 銀行支店一覧
				setBnkStnMstCondition(serverName);
				break;
			default:
				break;
		}
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @throws IOException
	 */
	protected void reflesh() throws IOException {
		// 画面区分による、画面別サブレットに送信するパラメタを設定。
		setCondition();
		// 開始コード
		addSendValues("START_CODE", Util.avoidNull(this.beginCode));
		// 終了コード
		addSendValues("END_CODE", Util.avoidNull(this.endCode));
		// 送信
		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		Iterator recordIte;

		// 社員リスト重複を消す
		if (gamenType == REFDialogMasterCtrl.EMP_SEARCH_MST) {

			// 重複識別用mapと再構成用リストを生成する。
			List list = getResultList();
			Map<String, String> b = new HashMap<String, String>();
			List<Object> distinctlist = new ArrayList<Object>();

			// 社員コードが重複していない社員情報をリストに加える。
			for (Object empObject : list) {
				List emplist = (List) empObject;
				String empName = (String) emplist.get(1);
				if (!b.containsKey(empName)) {
					b.put(empName, "");
					distinctlist.add(empObject);
				}
			}
			recordIte = distinctlist.iterator();

		}

		// Objectリストの場合
		else if (gamenType == REFDialogMasterCtrl.BNK_BNK || gamenType == REFDialogMasterCtrl.BNK_STN) {
			List<TransferBase> list = (List<TransferBase>) getResultObject();

			// スプレッド反映
			setObjectList(list);
			return;
		}

		else {
			recordIte = getResultList().iterator();
		}

		for (int row = 0; recordIte.hasNext(); row++) {
			List dataList = (List) recordIte.next();

			Vector<String> colum = new Vector<String>();
			switch (gamenType) {
				case REFDialogMasterCtrl.KMK_MST:// 科目一覧マスタ検索
					// 科目コード、科目略称、科目検索名称
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					colum.add((String) dataList.get(8));
					break;
				case REFDialogMasterCtrl.HKM_MST:
					// 補助科目マスタ一覧
					// 補助科目コード、補助科目略称、補助科目検索名称
					colum.add((String) dataList.get(2));
					colum.add((String) dataList.get(4));
					colum.add((String) dataList.get(5));
					colum.add((String) dataList.get(6));
					break;
				case REFDialogMasterCtrl.UKM_MST:
					// 内訳科目マスタ一覧
					// 内訳科目コード、内訳科目略称、内訳科目検索名称
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(5));
					colum.add((String) dataList.get(6));
					break;
				case REFDialogMasterCtrl.BMN_MST:// 部門マスタ一覧
					// コード、略称、検索名称
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.EMP_MST:// 社員マスタ一覧
					// コード、略称、検索名称
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.ENV_MST:// 環境設定マスタ一覧
					// コード、略称
					colum.add((String) dataList.get(0));
					colum.add((String) dataList.get(1));
					break;
				case REFDialogMasterCtrl.BANK_ACCOUNT: // 銀行口座マスタ一覧
					// 銀行口座、銀行支店略称、口座番号
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(2));
					colum.add(getWordByDepositType((String) dataList.get(10)) + " " + (String) dataList.get(11));
					break;
				case REFDialogMasterCtrl.CUSTOMER_MST:// 取引先 マスタ一覧

					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.CURRENCY_MST:// 通貨マスタ一覧

					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.DEPARTMENT_MST:// 部門マスタ一覧

					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.MEMO_MST:// 行摘要・伝票摘要マスタ一覧
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					colum.add((String) dataList.get(5));
					break;
				case REFDialogMasterCtrl.PAYMENT_MST:// 支払い方法マスタ一覧
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(2));
					colum.add((String) dataList.get(3));
					break;
				case REFDialogMasterCtrl.PAYMENTCON_MST:// 支払条件マスタ一覧
					colum.add((String) dataList.get(0));
					colum.add((String) dataList.get(1));
					break;
				case REFDialogMasterCtrl.EMP_SEARCH_MST:// 社員検索マスタ一覧
				case REFDialogMasterCtrl.KMK_TK_MST:// 科目体系マスタ一覧
				case REFDialogMasterCtrl.TAX_MST:// 消費税マスタ一覧
				case REFDialogMasterCtrl.KNR1_MST:// 管理1マスタ一覧
				case REFDialogMasterCtrl.KNR2_MST:// 管理2マスタ一覧
				case REFDialogMasterCtrl.KNR3_MST:// 管理3マスタ一覧
				case REFDialogMasterCtrl.KNR4_MST:// 管理4マスタ一覧
				case REFDialogMasterCtrl.KNR5_MST:// 管理5マスタ一覧
				case REFDialogMasterCtrl.KNR6_MST:// 管理6マスタ一覧
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(3));
					colum.add((String) dataList.get(4));
					break;
				case REFDialogMasterCtrl.BANK_ACCOUNT_B: // 銀行口座マスタ一覧
					// 銀行口座、銀行名+支店名、口座番号
					colum.add((String) dataList.get(1));
					colum.add((String) dataList.get(24) + " " + (String) dataList.get(25));
					colum.add(getWordByDepositType((String) dataList.get(10)) + " " + (String) dataList.get(11));
					break;
				case REFDialogMasterCtrl.APP_KAI: // 計上会社一覧
					colum.add((String) dataList.get(0));
					colum.add((String) dataList.get(1));
					break;
				case REFDialogMasterCtrl.BNK_BNK: // 銀行一覧
					colum.add(((BNK_MST) dataList).getBNK_CODE());
					colum.add(((BNK_MST) dataList).getBNK_NAME_S());
					colum.add(((BNK_MST) dataList).getBNK_NAME_K());
					break;
				case REFDialogMasterCtrl.BNK_STN: // 銀行支店一覧
					colum.add(((BNK_MST) dataList).getBNK_STN_CODE());
					colum.add(((BNK_MST) dataList).getBNK_STN_NAME_S());
					colum.add(((BNK_MST) dataList).getBNK_STN_NAME_K());
					break;
				default:
					break;
			}

			cells.add(row, colum);
		}

		lockBtn(cells.size() != 0);

		dialog.setDataList(cells);
	}

	/**
	 * スプレッド反映
	 * 
	 * @param list オブジェクトリスト
	 */
	protected void setObjectList(List<TransferBase> list) {
		Vector<Vector> cells = new Vector<Vector>();

		for (TransferBase base : list) {
			Vector<String> colum = new Vector<String>();

			switch (gamenType) {
				case REFDialogMasterCtrl.BNK_BNK: // 銀行一覧
					colum.add(((BNK_MST) base).getBNK_CODE());
					colum.add(((BNK_MST) base).getBNK_NAME_S());
					colum.add(((BNK_MST) base).getBNK_NAME_K());
					break;
				case REFDialogMasterCtrl.BNK_STN: // 銀行支店一覧
					colum.add(((BNK_MST) base).getBNK_STN_CODE());
					colum.add(((BNK_MST) base).getBNK_STN_NAME_S());
					colum.add(((BNK_MST) base).getBNK_STN_NAME_K());
					break;
				default:
					break;
			}
			cells.add(colum);
		}

		lockBtn(cells.size() != 0);

		dialog.setDataList(cells);
	}

	/**
	 * 現在選択されているセルの情報を取得<BR>
	 * 
	 * @return 情報リスト
	 */
	public String[] getCurrencyInfo() {

		// 選択されている行の1つ目のカラムを取得
		int numRow = dialog.ssJournal.getCurrentRow();
		TableDataModel model = dialog.ssJournal.getDataView();

		int numClmn = ((0 < this.numReferColumn) ? this.numReferColumn : dialog.ssJournal.getDataSource()
			.getNumColumns());

		String[] result = new String[numClmn];
		for (int i = 0; i < numClmn; i++) {
			result[i] = (String) model.getTableDataItem(numRow, i);
		}

		return result;
	}

	/**
	 * 現在選択されているセルの情報を取得<BR>
	 * 
	 * @param clmn 参照カラム数
	 * @return 情報リスト
	 */
	public String[] getCurrencyInfo(int clmn) {

		this.setNumReferColumn(clmn);

		return this.getCurrencyInfo();
	}

	/**
	 * 科目一覧検索条件設定
	 */
	protected void setKmkMstCondition() {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 科目コード
		String strKmkCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 科目略称
		String strKmkName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 科目検索名称
		String strKmkName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "KMK_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 科目コード
		addSendValues("KMK_CODE", (Util.avoidNull(strKmkCode)));
		// 科目略称
		addSendValues("KMK_NAME", (Util.avoidNull(strKmkName)));
		// 科目検索名称
		addSendValues("KMK_NAME_K", (Util.avoidNull(strKmkName_K)));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// BS勘定消込区分
		addSendValues("KESI_KBN", Util.avoidNull(this.kesiKbn));
		// 集計区分
		addSendValues("SUM_KBN", Util.avoidNull(this.sumKbn));
		// ＧＬ科目制御区分
		addSendValues("KMK_CNT_GL", Util.avoidNull(this.kmkCntGl));
		// 部門コード
		addSendValues("BMN_CODE", Util.avoidNull(this.bmnCode));
		// 評価替対象フラグ
		addSendValues("EXC_FLG", Util.avoidNull(this.excFlg));
		// 入金伝票入力フラグ
		addSendValues("NYU_KIN", Util.avoidNull(this.nyuKin));
		// 出金伝票入力フラグ
		addSendValues("SHUTSU_KIN", Util.avoidNull(this.shutsuKin));
		// 振替伝票入力フラグ
		addSendValues("FURIKAE_FLG", Util.avoidNull(this.furikaeFlg));
		// 科目体系区分
		addSendValues("KMT_KBN", Util.avoidNull(this.kmtKbn));
		// 科目体系コード
		addSendValues("KMT_CODE", Util.avoidNull(this.kmtCode));
		// AR制御区分
		addSendValues("KMK_CNT_AR", Util.avoidNull(this.kmkCntAr));
		// AR制御区分(消込用)
		addSendValues("KMK_CNT_UN_AR", Util.avoidNull(this.kmkCntUnAr));
		// AP制御区分
		addSendValues("KMK_CNT_AP", Util.avoidNull(this.kmkCntAp));
		// 経費精算伝票入力フラグ
		addSendValues("KEIHI_FLG", Util.avoidNull(this.keihiFlg));
		// 債務計上入力フラグ
		addSendValues("SAIMU_FLG", Util.avoidNull(this.saimuFlg));
		// 債権計上入力フラグ
		addSendValues("SAIKEN_FLG", Util.avoidNull(this.saikenFlg));
		// 債権消込伝票入力フラグ
		addSendValues("SAIKESI_FLG", Util.avoidNull(this.saikesiFlg));
		// 資産計上伝票入力フラグ
		addSendValues("SISAN_FLG", Util.avoidNull(this.sisanFlg));
		// 支払依頼伝票入力フラグ
		addSendValues("SIHARAI_FLG", Util.avoidNull(this.siharaiFlg));

		// 科目種別
		addSendValues("KMK_SHU", Util.avoidNull(this.kmkShu));
		// 貸借区分
		addSendValues("DC_KBN", Util.avoidNull(this.dcKbn));
		// 補助区分
		addSendValues("HKM_KBN", Util.avoidNull(this.hkmKbn));
		// 固定部門ｺｰﾄﾞ
		addSendValues("KOTEI_DEP_CODE", Util.avoidNull(this.koteiDepCode));
		// 消費税ｺｰﾄﾞ
		addSendValues("ZEI_CODE", Util.avoidNull(this.zeiCode));
		// 取引先入力ﾌﾗｸﾞ
		addSendValues("TRI_CODE_FLG", Util.avoidNull(this.triCodeFlg));
		// 発生日入力ﾌﾗｸﾞ
		addSendValues("HAS_FLG", Util.avoidNull(this.hasFlg));
		// 社員入力ﾌﾗｸﾞ
		addSendValues("EMP_CODE_FLG", Util.avoidNull(this.empCodeFlg));
		// 管理1
		addSendValues("KNR_FLG1", Util.avoidNull(this.knrFlg1));
		// 管理2
		addSendValues("KNR_FLG2", Util.avoidNull(this.knrFlg2));
		// 管理3
		addSendValues("KNR_FLG3", Util.avoidNull(this.knrFlg3));
		// 管理4
		addSendValues("KNR_FLG4", Util.avoidNull(this.knrFlg4));
		// 管理5
		addSendValues("KNR_FLG5", Util.avoidNull(this.knrFlg5));
		// 管理6
		addSendValues("KNR_FLG6", Util.avoidNull(this.knrFlg6));
		// 非会計1
		addSendValues("HM_FLG1", Util.avoidNull(this.hmFlg1));
		// 非会計2
		addSendValues("HM_FLG2", Util.avoidNull(this.hmFlg2));
		// 非会計3
		addSendValues("HM_FLG3", Util.avoidNull(this.hmFlg3));
		// 売上課税入力ﾌﾗｸﾞ
		addSendValues("URI_ZEI_FLG", Util.avoidNull(this.uriZeiFlg));
		// 仕入課税入力ﾌﾗｸﾞ
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.sirZeiFlg));
		// 多通貨入力ﾌﾗｸﾞ
		addSendValues("MCR_FLG", Util.avoidNull(this.mcrFlg));
		// BG科目制御区分
		addSendValues("KMK_CNT_BG", Util.avoidNull(this.kmkCntBg));
		// 相殺科目制御区分
		addSendValues("KMK_CNT_SOUSAI", Util.avoidNull(this.kmkCntSousai));
		// 清算対象区分
		addSendValues("KMK_CLR_FLG", Util.avoidNull(this.clearanceFlg));

		// 開始コード
		addSendValues("BEGIN_CODE", Util.avoidNull(this.beginCode));
		// 終了コード
		addSendValues("END_CODE", Util.avoidNull(this.endCode));

		// 科目体系コード
		addSendValues("KMK_TK_CODE", Util.avoidNull(this.kmtCode));
		// 科目体系フラグ
		addSendValues("KMK_TK_FLG", Util.avoidNull(this.kmtFlg));
	}

	/**
	 * 補助科目覧検索条件設定
	 */
	private void setHkmMstCondition() {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 補助科目コード
		String strHkmCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 補助科目略称
		String strHkmName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 補助科目検索名称
		String strHkmName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "HKM_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 科目コード
		addSendValues("KMK_CODE", Util.avoidNull(this.kmkCode));
		// 補助科目コード
		addSendValues("HKM_CODE", (Util.avoidNull(strHkmCode)));
		// 補助科目略称
		addSendValues("HKM_NAME", (Util.avoidNull(strHkmName)));
		// 補助科目検索名称
		addSendValues("HKM_NAME_K", (Util.avoidNull(strHkmName_K)));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// 振替伝票入力フラグ
		addSendValues("FURIKAE_FLG", Util.avoidNull(this.furikaeFlg));
		// 入金伝票入力フラグ
		addSendValues("NYU_KIN", Util.avoidNull(this.nyuKin));
		// 出金伝票入力フラグ
		addSendValues("SHUTSU_KIN", Util.avoidNull(this.shutsuKin));
		// 経費精算伝票入力フラグ
		addSendValues("KEIHI_FLG", Util.avoidNull(this.keihiFlg));
		// 債務計上入力フラグ
		addSendValues("SAIMU_FLG", Util.avoidNull(this.saimuFlg));
		// 債権計上入力フラグ
		addSendValues("SAIKEN_FLG", Util.avoidNull(this.saikenFlg));
		// 債権消込伝票入力フラグ
		addSendValues("SAIKESI_FLG", Util.avoidNull(this.saikesiFlg));
		// 資産計上伝票入力フラグ
		addSendValues("SISAN_FLG", Util.avoidNull(this.sisanFlg));
		// 支払依頼伝票入力フラグ
		addSendValues("SIHARAI_FLG", Util.avoidNull(this.siharaiFlg));

		// 評価替対象フラグ
		addSendValues("EXC_FLG", Util.avoidNull(this.excFlg));
		// 内訳区分
		addSendValues("UKM_KBN", Util.avoidNull(this.ukmKbn));
		// 消費税ｺｰﾄﾞ
		addSendValues("ZEI_CODE", Util.avoidNull(this.zeiCode));
		// 取引先入力ﾌﾗｸﾞ
		addSendValues("TRI_CODE_FLG", Util.avoidNull(this.triCodeFlg));
		// 発生日入力ﾌﾗｸﾞ
		addSendValues("HAS_FLG", Util.avoidNull(this.hasFlg));
		// 社員入力ﾌﾗｸﾞ
		addSendValues("EMP_CODE_FLG", Util.avoidNull(this.empCodeFlg));
		// 管理1
		addSendValues("KNR_FLG1", Util.avoidNull(this.knrFlg1));
		// 管理2
		addSendValues("KNR_FLG2", Util.avoidNull(this.knrFlg2));
		// 管理3
		addSendValues("KNR_FLG3", Util.avoidNull(this.knrFlg3));
		// 管理4
		addSendValues("KNR_FLG4", Util.avoidNull(this.knrFlg4));
		// 管理5
		addSendValues("KNR_FLG5", Util.avoidNull(this.knrFlg5));
		// 管理6
		addSendValues("KNR_FLG6", Util.avoidNull(this.knrFlg6));
		// 非会計1
		addSendValues("HM_FLG1", Util.avoidNull(this.hmFlg1));
		// 非会計2
		addSendValues("HM_FLG2", Util.avoidNull(this.hmFlg2));
		// 非会計3
		addSendValues("HM_FLG3", Util.avoidNull(this.hmFlg3));
		// 売上課税入力ﾌﾗｸﾞ
		addSendValues("URI_ZEI_FLG", Util.avoidNull(this.uriZeiFlg));
		// 仕入課税入力ﾌﾗｸﾞ
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.sirZeiFlg));
		// 多通貨入力ﾌﾗｸﾞ
		addSendValues("MCR_FLG", Util.avoidNull(this.mcrFlg));

		// 開始コード
		addSendValues("BEGIN_CODE", Util.avoidNull(this.beginCode));
		// 終了コード
		addSendValues("END_CODE", Util.avoidNull(this.endCode));
	}

	/**
	 * 内訳科目一覧検索条件設定
	 */
	private void setUkmMstCondition() {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 内訳科目コード
		String strUkmCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 内訳科目略称
		String strUkmName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 内訳科目検索名称
		String strUkmName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "UKM_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 科目コード
		addSendValues("KMK_CODE", Util.avoidNull(this.kmkCode));
		// 補助科目コード
		addSendValues("HKM_CODE", Util.avoidNull(this.hkmCode));
		// 内訳科目コード
		addSendValues("UKM_CODE", (Util.avoidNull(strUkmCode)));
		// 内訳科目略称
		addSendValues("UKM_NAME", (Util.avoidNull(strUkmName)));
		// 内訳科目検索名称
		addSendValues("UKM_NAME_K", (Util.avoidNull(strUkmName_K)));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// 振替伝票入力フラグ
		addSendValues("FURIKAE_FLG", Util.avoidNull(this.furikaeFlg));
		// 入金伝票入力フラグ
		addSendValues("NYU_KIN", Util.avoidNull(this.nyuKin));
		// 出金伝票入力フラグ
		addSendValues("SHUTSU_KIN", Util.avoidNull(this.shutsuKin));
		// 経費精算伝票入力フラグ
		addSendValues("KEIHI_FLG", Util.avoidNull(this.keihiFlg));
		// 債務計上入力フラグ
		addSendValues("SAIMU_FLG", Util.avoidNull(this.saimuFlg));
		// 債権計上入力フラグ
		addSendValues("SAIKEN_FLG", Util.avoidNull(this.saikenFlg));
		// 債権消込伝票入力フラグ
		addSendValues("SAIKESI_FLG", Util.avoidNull(this.saikesiFlg));
		// 資産計上伝票入力フラグ
		addSendValues("SISAN_FLG", Util.avoidNull(this.sisanFlg));
		// 支払依頼伝票入力フラグ
		addSendValues("SIHARAI_FLG", Util.avoidNull(this.siharaiFlg));

		// 評価替対象フラグ
		addSendValues("EXC_FLG", Util.avoidNull(this.excFlg));
		// 消費税ｺｰﾄﾞ
		addSendValues("ZEI_CODE", Util.avoidNull(this.zeiCode));
		// 取引先入力ﾌﾗｸﾞ
		addSendValues("TRI_CODE_FLG", Util.avoidNull(this.triCodeFlg));
		// 発生日入力ﾌﾗｸﾞ
		addSendValues("HAS_FLG", Util.avoidNull(this.hasFlg));
		// 社員入力ﾌﾗｸﾞ
		addSendValues("EMP_CODE_FLG", Util.avoidNull(this.empCodeFlg));
		// 管理1
		addSendValues("KNR_FLG1", Util.avoidNull(this.knrFlg1));
		// 管理2
		addSendValues("KNR_FLG2", Util.avoidNull(this.knrFlg2));
		// 管理3
		addSendValues("KNR_FLG3", Util.avoidNull(this.knrFlg3));
		// 管理4
		addSendValues("KNR_FLG4", Util.avoidNull(this.knrFlg4));
		// 管理5
		addSendValues("KNR_FLG5", Util.avoidNull(this.knrFlg5));
		// 管理6
		addSendValues("KNR_FLG6", Util.avoidNull(this.knrFlg6));
		// 非会計1
		addSendValues("HM_FLG1", Util.avoidNull(this.hmFlg1));
		// 非会計2
		addSendValues("HM_FLG2", Util.avoidNull(this.hmFlg2));
		// 非会計3
		addSendValues("HM_FLG3", Util.avoidNull(this.hmFlg3));
		// 売上課税入力ﾌﾗｸﾞ
		addSendValues("URI_ZEI_FLG", Util.avoidNull(this.uriZeiFlg));
		// 仕入課税入力ﾌﾗｸﾞ
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.sirZeiFlg));
		// 多通貨入力ﾌﾗｸﾞ
		addSendValues("MCR_FLG", Util.avoidNull(this.mcrFlg));

		// 開始コード
		addSendValues("BEGIN_CODE", Util.avoidNull(this.beginCode));
		// 終了コード
		addSendValues("END_CODE", Util.avoidNull(this.endCode));
	}

	/**
	 * 部門マスタ一覧検索条件設定
	 */
	private void setBmnMstCondition() {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 部門コード
		String strKnrCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 部門略称
		String strKnrName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 部門検索名称
		String strKnrName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "BMN_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 部門コード
		addSendValues("DEP_CODE", (Util.avoidNull(strKnrCode)));
		// 部門略称
		addSendValues("DEP_NAME", (Util.avoidNull(strKnrName)));
		// 部門検索名称
		addSendValues("DEP_NAME_K", (Util.avoidNull(strKnrName_K)));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// 組織コード
		addSendValues("DPK_SSK", Util.avoidNull(this.dpkSsk));
		// 配下部門(0:含む 、1:含まない、2:上位部門ｺｰﾄﾞ選択の場合)
		addSendValues("BMN_KBN", Util.avoidNull(this.bmnKbn));
		// 上位部門ｺｰﾄﾞ
		addSendValues("UP_BMN_CODE", Util.avoidNull(this.upBmnCode));
		// 階層ﾚﾍﾞﾙ
		addSendValues("DPK_LVL", Util.avoidNull(this.dpkLvl));

		// 初期部門ｺｰﾄﾞ
		addSendValues("BASE_BMN_CODE", Util.avoidNull(this.baseBmnCode));
		// 初期階層ﾚﾍﾞﾙ
		addSendValues("BASE_DPK_LVL", Util.avoidNull(this.baseDpkLvl));
	}

	/**
	 * 環境設定マスタ一覧検索条件設定
	 */
	private void setEnvMstCondition() {

		// 会社コード
		String CompCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			CompCode = Util.avoidNull(this.kaiCode);
		} else {
			CompCode = super.getLoginUserCompanyCode();
		}

		// 会社コード
		String strKaiCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 会社略称
		String strKaiNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "ENV_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 会社略称
		addSendValues("KAI_NAME_S", Util.avoidNull(strKaiNameS));

		// ログイン会社コード
		addSendValues("LOGIN_KAI_CODE", Util.avoidNull(CompCode));
		// 組織コード
		addSendValues("DPK_SSK", Util.avoidNull(this.dpkSsk));
		// 配下会社(0:含む 、1:含まない、2:上位会社ｺｰﾄﾞ選択の場合)
		addSendValues("COMPANY_KBN", Util.avoidNull(this.bmnKbn));
		// 上位会社ｺｰﾄﾞ
		addSendValues("UP_COMPANY_CODE", Util.avoidNull(this.upBmnCode));
		// 階層ﾚﾍﾞﾙ
		addSendValues("DPK_LVL", Util.avoidNull(this.dpkLvl));
		// 初期会社ｺｰﾄﾞ
		addSendValues("BASE_COMPANY_CODE", Util.avoidNull(this.baseBmnCode));
		// 初期階層ﾚﾍﾞﾙ
		addSendValues("BASE_DPK_LVL", Util.avoidNull(this.baseDpkLvl));
	}

	/**
	 * ヘッダとカラム名取得
	 * 
	 * @return map
	 */
	public Map setCmpCondition() {
		// 会社コード取得
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// 管理名称の取得
		String[] name = compInfo.getManageDivNames();

		Map map = new HashMap();
		map.put("kmkName", Util.avoidNull(Util.isNullOrEmpty(compInfo.getItemName()) ? getWord("C00077") : compInfo
			.getItemName()));
		map.put("hkmName", Util.avoidNull(Util.isNullOrEmpty(compInfo.getSubItemName()) ? getWord("C00488") : compInfo
			.getSubItemName()));
		map.put("ukmName", Util.avoidNull(Util.isNullOrEmpty(compInfo.getBreakDownItemName()) ? getWord("C00025")
			: compInfo.getBreakDownItemName()));

		map.put("knrName1", Util.avoidNull(Util.isNullOrEmpty(name[0]) ? getWord("C01025") : name[0]));
		map.put("knrName2", Util.avoidNull(Util.isNullOrEmpty(name[1]) ? getWord("C01027") : name[1]));
		map.put("knrName3", Util.avoidNull(Util.isNullOrEmpty(name[2]) ? getWord("C01029") : name[2]));
		map.put("knrName4", Util.avoidNull(Util.isNullOrEmpty(name[3]) ? getWord("C01031") : name[3]));
		map.put("knrName5", Util.avoidNull(Util.isNullOrEmpty(name[4]) ? getWord("C01033") : name[4]));
		map.put("knrName6", Util.avoidNull(Util.isNullOrEmpty(name[5]) ? getWord("C01035") : name[5]));

		return map;
	}

	/**
	 * 銀行口座の検索条件設定
	 * 
	 * @param flag
	 */
	private void setSearchCondition(String flag) {

		// コード
		String triCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String triNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String triNameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("KaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("Code", Util.avoidNull(triCode));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("NameS", Util.avoidNull(triNameS));
		// 検索名
		addSendValues("NameK", Util.avoidNull(triNameK));
		// 社員支払いフラグ
		addSendValues("empKbn", Util.avoidNull(paramMap.get("empKbn")));
		// 社外支払いフラグ
		addSendValues("outKbn", Util.avoidNull(paramMap.get("outKbn")));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));

	}

	/**
	 * 銀行口座の検索条件設定
	 * 
	 * @param flag
	 */
	private void setBankSearchCondition(String flag) {

		// コード
		String cbkCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String cbkNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String cbkNameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("KaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("Code", Util.avoidNull(cbkCode));
		// 略称
		addSendValues("NameS", Util.avoidNull(cbkNameS));
		// 検索名
		addSendValues("NameK", Util.avoidNull(cbkNameK));
		// 社員支払フラグ
		addSendValues("empKbn", Util.avoidNull(paramMap.get("empKbn")));
		// 社外支払フラグ
		addSendValues("outKbn", Util.avoidNull(paramMap.get("outKbn")));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// 銀行口座リスト
		addSendObject(cbkCodes);
	}

	/**
	 * 取引先の検索条件設定
	 * 
	 * @param flag
	 */
	protected void setCustomerCondition(String flag) {

		// コード
		String triCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String triNameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String triNameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("triCode", Util.avoidNull(triCode));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(triNameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(triNameK));
		// 社員支払いフラグ
		addSendValues("siire", Util.avoidNull(paramMap.get("siire")));
		// 社外支払いフラグ
		addSendValues("tokui", Util.avoidNull(paramMap.get("tokui")));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// 開始コード
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// 終了コード
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));

	}

	/**
	 * 通貨フィールドマスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setCurrencyCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("curCode", Util.avoidNull(Code));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(NameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(NameK));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// 開始コード
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// 終了コード
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));

	}

	/**
	 * 計上会社フィールド検索条件設定
	 */
	private void setAppropriateCompanyCondition() {

		AppropriateCompany param = new AppropriateCompany();
		// コード
		param.setKAI_CODE(StringUtil.convertPrm(dialog.txtCode.getText()));

		param.setKAI_NAME_S(StringUtil.convertPrm(dialog.txtAbbreviationName.getText()));

		// 開始コード
		param.setStrCompanyCode(beginCode);

		// 終了コード
		param.setEndCompanyCode(endCode);

		// 通貨区分
		param.setCurKbn(curKbn);

		// 送信するパラメータを設定
		addSendValues("FLAG", "APP_KAI");

		this.addSendDto(param);

	}

	/**
	 * 部門フィールドマスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setDepartmentCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("depCode", Util.avoidNull(Code));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(NameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(NameK));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// 組織名
		addSendValues("organization", Util.avoidNull(paramMap.get("organization")));
		// 上位部門
		addSendValues("upper", paramMap.get("upper"));
		// 上位部門レベル
		addSendValues("level", Util.avoidNull(paramMap.get("level")));
		// 集計部門フラグ
		addSendValues("sum", Util.avoidNull(paramMap.get("sum")));
		// 入力部門フラグ
		addSendValues("input", Util.avoidNull(paramMap.get("input")));
		// 開始コード
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// 終了コード
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));
	}

	/**
	 * 行摘要・伝票摘要フィールドマスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setMemoCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());
		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("tekCode", Util.avoidNull(Code));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(NameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(NameK));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// データタイプ
		addSendValues("slipType", Util.avoidNull(paramMap.get("slipType")));
		// 摘要区分
		addSendValues("memoType", Util.avoidNull(paramMap.get("memoType")));
	}

	/**
	 * 社員検索フィールドマスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setEmployeeCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());

		// 検索名
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("empCode", Util.avoidNull(Code));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(NameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(NameK));
		// ユーザ登録フラグ
		addSendValues("user", Util.avoidNull(paramMap.get("user")));
		// 部門設定
		addSendValues("depCode", Util.avoidNull(paramMap.get("depCode")));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
		// 開始コード
		addSendValues("beginCode", Util.avoidNull(paramMap.get("beginCode")));
		// 終了コード
		addSendValues("endCode", Util.avoidNull(paramMap.get("endCode")));
	}

	/**
	 * 支払い方法マスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setPaymentCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("hohCode", Util.avoidNull(Code));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(NameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(NameK));
		// 支払対象区分
		addSendValues("sihKbn", Util.avoidNull(paramMap.get("sihKbn")));
		// 支払方法内部コード
		addSendValues("naiCode", Util.avoidNull(paramMap.get("naiCode")));
		// 支払方法内部コード NOT条件
		addSendValues("notNaiCode", Util.avoidNull(paramMap.get("notNaiCode")));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));

		// コードリスト
		addSendObject(codes);
	}

	/**
	 * 支払条件マスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setPaymentConCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("tjkCode", Util.avoidNull(Code));
		// 取引先コード
		addSendValues("triCode", Util.avoidNull(paramMap.get("triCode")));

		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));
	}

	/**
	 * 消費税マスタ一覧検索条件設定
	 * 
	 * @param flag
	 */
	private void setTaxCondition(String flag) {

		// コード
		String Code = StringUtil.convertPrm(dialog.txtCode.getText());
		// 略称
		String NameS = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 検索名
		String NameK = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", flag);
		// 会社コード
		addSendValues("kaiCode", Util.avoidNull(paramMap.get("companyCode")));
		// コード
		addSendValues("zeiCode", Util.avoidNull(Code));
		// 略称 - accoututilのワイルドカード対応
		addSendValues("sName", Util.avoidNull(NameS));
		// 検索名
		addSendValues("kName", Util.avoidNull(NameK));
		// 有効期間 条件検索
		addSendValues("termBasisDate", Util.avoidNull(paramMap.get("termBasisDate")));

		// 売り上げ区分
		addSendValues("sales", Util.avoidNull(paramMap.get("sales")));
		// 仕入れ区分
		addSendValues("purchase", Util.avoidNull(paramMap.get("purchase")));
		// 対象外区分
		addSendValues("elseTax", Util.avoidNull(paramMap.get("elseTax")));
	}

	/**
	 * 社員マスタ一覧検索条件設定
	 */
	private void setEmpMstCondition() {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 社員コード
		String strEmpCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 社員略称
		String strEmpName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 社員検索名称
		String strEmpName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "EMP_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 社員コード
		addSendValues("EMP_CODE", Util.avoidNull(strEmpCode));
		// 社員略称
		addSendValues("EMP_NAME", Util.avoidNull(strEmpName));
		// 社員検索名称
		addSendValues("EMP_NAME_K", Util.avoidNull(strEmpName_K));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// 所属部門コード区分
		addSendValues("DEP_CODE_KBN", Util.avoidNull(this.depCodeKbn));
		// 所属部門コード
		addSendValues("DEP_CODE", Util.avoidNull(this.depCode));
	}

	/**
	 * 管理1マスタ一覧検索条件設定
	 * 
	 * @param strMstKbn (1-6)管理１－管理６
	 */
	protected void setKnrMstCondition(String strMstKbn) {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 管理コード
		String strKnrCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 管理略称
		String strKnrName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 管理検索名称
		String strKnrName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "KNR_MST");
		addSendValues("KNR_FLAG", strMstKbn);
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 管理コード
		addSendValues("KNR_CODE", Util.avoidNull(strKnrCode));
		// 管理略称
		addSendValues("KNR_NAME", Util.avoidNull(strKnrName));
		// 管理コード検索名称
		addSendValues("KNR_NAME_K", Util.avoidNull(strKnrName_K));
		// 伝票日付
		addSendValues("SLIP_DATE", Util.avoidNull(this.slipDate));
		// 開始コード
		addSendValues("START_CODE", Util.avoidNull(this.beginCode));
		// 終了コード
		addSendValues("END_CODE", Util.avoidNull(this.endCode));

	}

	/**
	 * 科目体系名称マスタ一覧検索条件設定
	 */
	private void setKmkTkMstCondition() {
		// 会社コード
		String strKaiCode = "";
		if (this.kaiCode != null && !"".equals(this.kaiCode)) {
			strKaiCode = Util.avoidNull(this.kaiCode);
		} else {
			strKaiCode = super.getLoginUserCompanyCode();
		}
		// 科目体系コード
		String strKmtCode = StringUtil.convertPrm(dialog.txtCode.getText());
		// 科目体系略称
		String strKmtName = StringUtil.convertPrm(dialog.txtAbbreviationName.getText());
		// 科目体系検索名称
		String strKmtName_K = StringUtil.convertPrm(dialog.txtNameForSearch.getText());

		// 送信するパラメータを設定
		addSendValues("FLAG", "KMT_MST");
		// 会社コード
		addSendValues("KAI_CODE", Util.avoidNull(strKaiCode));
		// 科目体系コード
		addSendValues("KMT_CODE", Util.avoidNull(strKmtCode));
		// 科目体系略称
		addSendValues("KMT_NAME", Util.avoidNull(strKmtName));
		// 科目体系検索名称
		addSendValues("KMT_NAME_K", Util.avoidNull(strKmtName_K));
	}

	/**
	 * 銀行マスタ一覧条件設定
	 * 
	 * @param flag 検索フラグ
	 */
	private void setBnkMstCondition(String flag) {
		BnkMstParameter param = (BnkMstParameter) paramObject;
		param.setLikeBnkCode(StringUtil.convertPrm(dialog.txtCode.getText()));
		param.setLikeBnkName(StringUtil.convertPrm(dialog.txtAbbreviationName.getText()));
		param.setLikeBnkNameK(StringUtil.convertPrm(dialog.txtNameForSearch.getText()));

		// 送信するパラメータを設定
		addSendValues("FLAG", flag); // flag
		addSendObject(param);// 銀行マスタパラメータ
	}

	/**
	 * 銀行支店マスタ一覧条件設定
	 * 
	 * @param flag 検索フラグ
	 */
	private void setBnkStnMstCondition(String flag) {
		BnkMstParameter param = (BnkMstParameter) paramObject;
		param.setLikeBnkStnCode(StringUtil.convertPrm(dialog.txtCode.getText()));
		param.setLikeBnkStnName(StringUtil.convertPrm(dialog.txtAbbreviationName.getText()));
		param.setLikeBnkStnNameK(StringUtil.convertPrm(dialog.txtNameForSearch.getText()));

		// 送信するパラメータを設定 "BNK_STN_MST"
		addSendValues("FLAG", flag); // flag
		addSendObject(param);// 銀行マスタパラメータ
	}

	/**
	 * コンバート用(マスタ一覧)
	 * 
	 * @param word
	 * @return マスタ一覧
	 */
	public String convertTop(String word) {
		String wordID = getWord("C02406");
		String top = word + wordID;
		return top;
	}

	/**
	 * コンバート用(コード)
	 * 
	 * @param word
	 * @return コード
	 */
	public String convertCode(String word) {
		String wordID = getWord("C00174");
		String code = word + wordID;
		return code;
	}

	/**
	 * コンバート用(略称)
	 * 
	 * @param word
	 * @return 略称
	 */
	public String convertNameS(String word) {
		String wordID = getWord("C00548");
		String nameS = word + wordID;
		return nameS;
	}

	/**
	 * コンバート用(検索名称)
	 * 
	 * @param word
	 * @return 検索名称
	 */
	public String convertNameK(String word) {
		String wordID = getWord("C00160");
		String nameK = word + wordID;
		return nameK;
	}

	/**
	 * @return numReferColumn
	 */
	public int getNumReferColumn() {
		return numReferColumn;
	}

	/**
	 * @param numReferColumn 設定する numReferColumn
	 */
	public void setNumReferColumn(int numReferColumn) {
		this.numReferColumn = numReferColumn;
	}

	/**
	 * @return ServerName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName ServerName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return 開始コード
	 */
	public String getStartCode() {
		return startCode;
	}

	/**
	 * @param startCode 開始コード
	 */
	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}

	/**
	 * @return 科目清算対象フラグ
	 */
	public String getClearanceFlg() {
		return clearanceFlg;
	}

	/**
	 * @param clearanceFlg 1:清算対象
	 */
	public void setClearanceFlg(String clearanceFlg) {
		this.clearanceFlg = clearanceFlg;
	}

	/**
	 * 銀行口座コード設定
	 * 
	 * @param cbkCodes
	 */
	public void setCbkCodes(List<String> cbkCodes) {
		this.cbkCodes = cbkCodes;
	}

	/**
	 * コードリスト設定
	 * 
	 * @param codes
	 */
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	/**
	 * 預金種目の多言語対応
	 * 
	 * @param kbn
	 * @return 単語
	 */
	protected String getWordByDepositType(String kbn) {
		if ("1".equals(kbn)) {
			return getWord("C00463"); // 普通 C00463
		} else if ("2".equals(kbn)) {
			return getWord("C00397"); // 当座 C00397
		} else if ("3".equals(kbn)) {
			return getWord("C00045"); // 外貨 C00045
		} else {
			return getWord("C00368"); // 貯蓄 C00368
		}
	}

	/**
	 * パラメータクラス設定
	 * 
	 * @param paramObject パラメータクラス
	 */
	public void setParamObject(TransferBase paramObject) {
		this.paramObject = paramObject;
	}

	/**
	 * REF画面のｺｰﾄﾞを設定する
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		dialog.txtCode.setValue(code);
	}
}
