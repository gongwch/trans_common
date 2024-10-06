package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * IF_SWK_DAT
 * 
 * @author zlw
 */
public class IF_SWK_DAT extends MasterBase {

	/** IF_SWK_DAT */
	public static final String TABLE = "IF_SWK_DAT";

	private String kAICODE; // 会社コード

	private String sWKDENDATE; // 伝票日付

	private String sWKDENNO; // 伝票番号

	private String sWKGYONO; // 行番号

	private String sWKUKEDEPCODE; // 受付部門コード

	private String sWKTEKCODE; // 伝票摘要コード

	private String sWKTEK; // 伝票摘要

	private String sWKSYOEMPCODE; // 承認者

	private String sWKSYODATE; // 承認日

	private String sWKIRAIEMPCODE; // 依頼者

	private String sWKIRAIDEPCODE; // 依頼部門コード

	private String sWKIRAIDATE; // 依頼日

	private String sWKSYSKBN; // ｼｽﾃﾑ区分

	private String sWKDENSYU; // 伝票種別

	private int sWKUPDKBN; // 更新区分

	private int sWKKSNKBN; // 決算区分

	private String sWKKMKCODE; // 科目コード

	private String sWKHKMCODE; // 補助科目コード

	private String sWKUKMCODE; // 内訳科目コード

	private String sWKDEPCODE; // 計上部門コード

	private String sWKTRICODE; // 取引先コード

	private String sWKEMPCODE; // 社員コード

	private String sWKCURCODE; // 通貨コード

	private double sWKCURRATE; // レート

	private int sWKDCKBN; // 貸借区分

	private int sWKZEIKBN; // 税区分

	private double sWKZEIKIN; // 消費税額

	private String sWKZEICODE; // 消費税コード

	private String sWKGYOTEKCODE; // 行摘要コード

	private String sWKGYOTEK; // 行摘要

	private String sWKKNRCODE1; // 管理１コード

	private String sWKKNRCODE2; // 管理２コード

	private String sWKKNRCODE3; // 管理３コード

	private String sWKKNRCODE4; // 管理４コード

	private String sWKKNRCODE5; // 管理５コード

	private String sWKKNRCODE6; // 管理６コード

	private String sWKHM1; // 非会計明細１

	private String sWKHM2; // 非会計明細２

	private String sWKHM3; // 非会計明細３

	private String sWKAUTOKBN; // 自動仕訳区分

	private String hASDATE; // DATE

	private String sWKATKMKCODE; // 相手科目コード

	private String sWKATHKMCODE; // 相手補助科目コード

	private String sWKATUKMCODE; // 相手内訳科目コード

	private String sWKATDEPCODE; // 相手部門コード

	private String sWKKKAICODE; // 特別地方消費税額

	private String sWKSEINO; // 計上会社ｺｰﾄﾞ

	private double sWKKIN; // 証憑番号

	private double sWKINKIN; // 邦貨金額

	private String sWKSIHAKBN; // 入力金額

	private String sWKSIHADATEAP; // 支払区分

	private String sWKHOHCODEAP; // 支払日（ﾍｯﾀﾞ）

	private String sWKHORYUKBN; // 保留区分

	private String sWKTJKCODE; // 取引先条件ｺｰﾄﾞ

	private String sWKARDATE; // 入金予定日

	private String sWKUKEDATE; // 受入日

	private String sWKCBKCODE; // 銀行口座ｺｰﾄﾞ

	private String sWKTUKEKBN; // 付替区分

	private double sWKINZEIKIN; // 入力消費税額

	/**
	 * @return Returns the hASDATE.
	 */
	public String getHASDATE() {
		return hASDATE;
	}

	/**
	 * @param hasdate The hASDATE to set.
	 */
	public void setHASDATE(String hasdate) {
		hASDATE = hasdate;
	}

	/**
	 * @return Returns the kAICODE.
	 */
	public String getKAICODE() {
		return kAICODE;
	}

	/**
	 * @param kaicode The kAICODE to set.
	 */
	public void setKAICODE(String kaicode) {
		kAICODE = kaicode;
	}

	/**
	 * @return Returns the sFRIRAIDEPCODE.
	 */
	public String getSWKIRAIDEPCODE() {
		return sWKIRAIDEPCODE;
	}

	/**
	 * @param sWKIRAIDEPCODE The sFRIRAIDEPCODE to set.
	 */
	public void setSWKIRAIDEPCODE(String sWKIRAIDEPCODE) {
		this.sWKIRAIDEPCODE = sWKIRAIDEPCODE;
	}

	/**
	 * @return Returns the sWKARDATE.
	 */
	public String getSWKARDATE() {
		return sWKARDATE;
	}

	/**
	 * @param swkardate The sWKARDATE to set.
	 */
	public void setSWKARDATE(String swkardate) {
		sWKARDATE = swkardate;
	}

	/**
	 * @return Returns the sWKATDEPCODE.
	 */
	public String getSWKATDEPCODE() {
		return sWKATDEPCODE;
	}

	/**
	 * @param swkatdepcode The sWKATDEPCODE to set.
	 */
	public void setSWKATDEPCODE(String swkatdepcode) {
		sWKATDEPCODE = swkatdepcode;
	}

	/**
	 * @return Returns the sWKATHKMCODE.
	 */
	public String getSWKATHKMCODE() {
		return sWKATHKMCODE;
	}

	/**
	 * @param swkathkmcode The sWKATHKMCODE to set.
	 */
	public void setSWKATHKMCODE(String swkathkmcode) {
		sWKATHKMCODE = swkathkmcode;
	}

	/**
	 * @return Returns the sWKATKMKCODE.
	 */
	public String getSWKATKMKCODE() {
		return sWKATKMKCODE;
	}

	/**
	 * @param swkatkmkcode The sWKATKMKCODE to set.
	 */
	public void setSWKATKMKCODE(String swkatkmkcode) {
		sWKATKMKCODE = swkatkmkcode;
	}

	/**
	 * @return Returns the sWKATUKMCODE.
	 */
	public String getSWKATUKMCODE() {
		return sWKATUKMCODE;
	}

	/**
	 * @param swkatukmcode The sWKATUKMCODE to set.
	 */
	public void setSWKATUKMCODE(String swkatukmcode) {
		sWKATUKMCODE = swkatukmcode;
	}

	/**
	 * @return Returns the sWKAUTOKBN.
	 */
	public String getSWKAUTOKBN() {
		return sWKAUTOKBN;
	}

	/**
	 * @param swkautokbn The sWKAUTOKBN to set.
	 */
	public void setSWKAUTOKBN(String swkautokbn) {
		sWKAUTOKBN = swkautokbn;
	}

	/**
	 * @return Returns the sWKCBKCODE.
	 */
	public String getSWKCBKCODE() {
		return sWKCBKCODE;
	}

	/**
	 * @param swkcbkcode The sWKCBKCODE to set.
	 */
	public void setSWKCBKCODE(String swkcbkcode) {
		sWKCBKCODE = swkcbkcode;
	}

	/**
	 * @return Returns the sWKCURCODE.
	 */
	public String getSWKCURCODE() {
		return sWKCURCODE;
	}

	/**
	 * @param swkcurcode The sWKCURCODE to set.
	 */
	public void setSWKCURCODE(String swkcurcode) {
		sWKCURCODE = swkcurcode;
	}

	/**
	 * @return Returns the sWKCURRATE.
	 */
	public double getSWKCURRATE() {
		return sWKCURRATE;
	}

	/**
	 * @param swkcurrate The sWKCURRATE to set.
	 */
	public void setSWKCURRATE(double swkcurrate) {
		sWKCURRATE = swkcurrate;
	}

	/**
	 * @return Returns the sWKDCKBN.
	 */
	public int getSWKDCKBN() {
		return sWKDCKBN;
	}

	/**
	 * @param swkdckbn The sWKDCKBN to set.
	 */
	public void setSWKDCKBN(int swkdckbn) {
		sWKDCKBN = swkdckbn;
	}

	/**
	 * @return Returns the sWKDENDATE.
	 */
	public String getSWKDENDATE() {
		return sWKDENDATE;
	}

	/**
	 * @param swkdendate The sWKDENDATE to set.
	 */
	public void setSWKDENDATE(String swkdendate) {
		sWKDENDATE = swkdendate;
	}

	/**
	 * @return Returns the sWKDENNO.
	 */
	public String getSWKDENNO() {
		return sWKDENNO;
	}

	/**
	 * @param swkdenno The sWKDENNO to set.
	 */
	public void setSWKDENNO(String swkdenno) {
		sWKDENNO = swkdenno;
	}

	/**
	 * @return Returns the sWKDENSYU.
	 */
	public String getSWKDENSYU() {
		return sWKDENSYU;
	}

	/**
	 * @param swkdensyu The sWKDENSYU to set.
	 */
	public void setSWKDENSYU(String swkdensyu) {
		sWKDENSYU = swkdensyu;
	}

	/**
	 * @return Returns the sWKDEPCODE.
	 */
	public String getSWKDEPCODE() {
		return sWKDEPCODE;
	}

	/**
	 * @param swkdepcode The sWKDEPCODE to set.
	 */
	public void setSWKDEPCODE(String swkdepcode) {
		sWKDEPCODE = swkdepcode;
	}

	/**
	 * @return Returns the sWKEMPCODE.
	 */
	public String getSWKEMPCODE() {
		return sWKEMPCODE;
	}

	/**
	 * @param swkempcode The sWKEMPCODE to set.
	 */
	public void setSWKEMPCODE(String swkempcode) {
		sWKEMPCODE = swkempcode;
	}

	/**
	 * @return Returns the sWKGYONO.
	 */
	public String getSWKGYONO() {
		return sWKGYONO;
	}

	/**
	 * @param swkgyono The sWKGYONO to set.
	 */
	public void setSWKGYONO(String swkgyono) {
		sWKGYONO = swkgyono;
	}

	/**
	 * @return Returns the sWKGYOTEK.
	 */
	public String getSWKGYOTEK() {
		return sWKGYOTEK;
	}

	/**
	 * @param swkgyotek The sWKGYOTEK to set.
	 */
	public void setSWKGYOTEK(String swkgyotek) {
		sWKGYOTEK = swkgyotek;
	}

	/**
	 * @return Returns the sWKGYOTEKCODE.
	 */
	public String getSWKGYOTEKCODE() {
		return sWKGYOTEKCODE;
	}

	/**
	 * @param swkgyotekcode The sWKGYOTEKCODE to set.
	 */
	public void setSWKGYOTEKCODE(String swkgyotekcode) {
		sWKGYOTEKCODE = swkgyotekcode;
	}

	/**
	 * @return Returns the sWKHKMCODE.
	 */
	public String getSWKHKMCODE() {
		return sWKHKMCODE;
	}

	/**
	 * @param swkhkmcode The sWKHKMCODE to set.
	 */
	public void setSWKHKMCODE(String swkhkmcode) {
		sWKHKMCODE = swkhkmcode;
	}

	/**
	 * @return Returns the sWKHM1.
	 */
	public String getSWKHM1() {
		return sWKHM1;
	}

	/**
	 * @param swkhm1 The sWKHM1 to set.
	 */
	public void setSWKHM1(String swkhm1) {
		sWKHM1 = swkhm1;
	}

	/**
	 * @return Returns the sWKHM2.
	 */
	public String getSWKHM2() {
		return sWKHM2;
	}

	/**
	 * @param swkhm2 The sWKHM2 to set.
	 */
	public void setSWKHM2(String swkhm2) {
		sWKHM2 = swkhm2;
	}

	/**
	 * @return Returns the sWKHM3.
	 */
	public String getSWKHM3() {
		return sWKHM3;
	}

	/**
	 * @param swkhm3 The sWKHM3 to set.
	 */
	public void setSWKHM3(String swkhm3) {
		sWKHM3 = swkhm3;
	}

	/**
	 * @return Returns the sWKHOHCODEAP.
	 */
	public String getSWKHOHCODEAP() {
		return sWKHOHCODEAP;
	}

	/**
	 * @param swkhohcodeap The sWKHOHCODEAP to set.
	 */
	public void setSWKHOHCODEAP(String swkhohcodeap) {
		sWKHOHCODEAP = swkhohcodeap;
	}

	/**
	 * @return Returns the sWKHORYUKBN.
	 */
	public String getSWKHORYUKBN() {
		return sWKHORYUKBN;
	}

	/**
	 * @param swkhoryukbn The sWKHORYUKBN to set.
	 */
	public void setSWKHORYUKBN(String swkhoryukbn) {
		sWKHORYUKBN = swkhoryukbn;
	}

	/**
	 * @return Returns the sWKINKIN.
	 */
	public double getSWKINKIN() {
		return sWKINKIN;
	}

	/**
	 * @param swkinkin The sWKINKIN to set.
	 */
	public void setSWKINKIN(double swkinkin) {
		sWKINKIN = swkinkin;
	}

	/**
	 * @return Returns the sWKINZEIKIN.
	 */
	public double getSWKINZEIKIN() {
		return sWKINZEIKIN;
	}

	/**
	 * @param swkinzeikin The sWKINZEIKIN to set.
	 */
	public void setSWKINZEIKIN(double swkinzeikin) {
		sWKINZEIKIN = swkinzeikin;
	}

	/**
	 * @return Returns the sWKIRAIEMPCODE.
	 */
	public String getSWKIRAIEMPCODE() {
		return sWKIRAIEMPCODE;
	}

	/**
	 * @param swkiraiempcode The sWKIRAIEMPCODE to set.
	 */
	public void setSWKIRAIEMPCODE(String swkiraiempcode) {
		sWKIRAIEMPCODE = swkiraiempcode;
	}

	/**
	 * @return Returns the sWKKIN.
	 */
	public double getSWKKIN() {
		return sWKKIN;
	}

	/**
	 * @param swkkin The sWKKIN to set.
	 */
	public void setSWKKIN(double swkkin) {
		sWKKIN = swkkin;
	}

	/**
	 * @return Returns the sWKKKAICODE.
	 */
	public String getSWKKKAICODE() {
		return sWKKKAICODE;
	}

	/**
	 * @param swkkkaicode The sWKKKAICODE to set.
	 */
	public void setSWKKKAICODE(String swkkkaicode) {
		sWKKKAICODE = swkkkaicode;
	}

	/**
	 * @return Returns the sWKKMKCODE.
	 */
	public String getSWKKMKCODE() {
		return sWKKMKCODE;
	}

	/**
	 * @param swkkmkcode The sWKKMKCODE to set.
	 */
	public void setSWKKMKCODE(String swkkmkcode) {
		sWKKMKCODE = swkkmkcode;
	}

	/**
	 * @return Returns the sWKKNRCODE1.
	 */
	public String getSWKKNRCODE1() {
		return sWKKNRCODE1;
	}

	/**
	 * @param swkknrcode1 The sWKKNRCODE1 to set.
	 */
	public void setSWKKNRCODE1(String swkknrcode1) {
		sWKKNRCODE1 = swkknrcode1;
	}

	/**
	 * @return Returns the sWKKNRCODE2.
	 */
	public String getSWKKNRCODE2() {
		return sWKKNRCODE2;
	}

	/**
	 * @param swkknrcode2 The sWKKNRCODE2 to set.
	 */
	public void setSWKKNRCODE2(String swkknrcode2) {
		sWKKNRCODE2 = swkknrcode2;
	}

	/**
	 * @return Returns the sWKKNRCODE3.
	 */
	public String getSWKKNRCODE3() {
		return sWKKNRCODE3;
	}

	/**
	 * @param swkknrcode3 The sWKKNRCODE3 to set.
	 */
	public void setSWKKNRCODE3(String swkknrcode3) {
		sWKKNRCODE3 = swkknrcode3;
	}

	/**
	 * @return Returns the sWKKNRCODE4.
	 */
	public String getSWKKNRCODE4() {
		return sWKKNRCODE4;
	}

	/**
	 * @param swkknrcode4 The sWKKNRCODE4 to set.
	 */
	public void setSWKKNRCODE4(String swkknrcode4) {
		sWKKNRCODE4 = swkknrcode4;
	}

	/**
	 * @return Returns the sWKKNRCODE5.
	 */
	public String getSWKKNRCODE5() {
		return sWKKNRCODE5;
	}

	/**
	 * @param swkknrcode5 The sWKKNRCODE5 to set.
	 */
	public void setSWKKNRCODE5(String swkknrcode5) {
		sWKKNRCODE5 = swkknrcode5;
	}

	/**
	 * @return Returns the sWKKNRCODE6.
	 */
	public String getSWKKNRCODE6() {
		return sWKKNRCODE6;
	}

	/**
	 * @param swkknrcode6 The sWKKNRCODE6 to set.
	 */
	public void setSWKKNRCODE6(String swkknrcode6) {
		sWKKNRCODE6 = swkknrcode6;
	}

	/**
	 * @return Returns the sWKKSNKBN.
	 */
	public int getSWKKSNKBN() {
		return sWKKSNKBN;
	}

	/**
	 * @param swkksnkbn The sWKKSNKBN to set.
	 */
	public void setSWKKSNKBN(int swkksnkbn) {
		sWKKSNKBN = swkksnkbn;
	}

	/**
	 * @return Returns the sWKSEINO.
	 */
	public String getSWKSEINO() {
		return sWKSEINO;
	}

	/**
	 * @param swkseino The sWKSEINO to set.
	 */
	public void setSWKSEINO(String swkseino) {
		sWKSEINO = swkseino;
	}

	/**
	 * @return Returns the sWKSIHADATEAP.
	 */
	public String getSWKSIHADATEAP() {
		return sWKSIHADATEAP;
	}

	/**
	 * @param swksihadateap The sWKSIHADATEAP to set.
	 */
	public void setSWKSIHADATEAP(String swksihadateap) {
		sWKSIHADATEAP = swksihadateap;
	}

	/**
	 * @return Returns the sWKSIHAKBN.
	 */
	public String getSWKSIHAKBN() {
		return sWKSIHAKBN;
	}

	/**
	 * @param swksihakbn The sWKSIHAKBN to set.
	 */
	public void setSWKSIHAKBN(String swksihakbn) {
		sWKSIHAKBN = swksihakbn;
	}

	/**
	 * @return Returns the sWKSYODATE.
	 */
	public String getSWKSYODATE() {
		return sWKSYODATE;
	}

	/**
	 * @param swksyodate The sWKSYODATE to set.
	 */
	public void setSWKSYODATE(String swksyodate) {
		sWKSYODATE = swksyodate;
	}

	/**
	 * @return Returns the sWKSYOEMPCODE.
	 */
	public String getSWKSYOEMPCODE() {
		return sWKSYOEMPCODE;
	}

	/**
	 * @param swksyoempcode The sWKSYOEMPCODE to set.
	 */
	public void setSWKSYOEMPCODE(String swksyoempcode) {
		sWKSYOEMPCODE = swksyoempcode;
	}

	/**
	 * @return Returns the sWKSYSKBN.
	 */
	public String getSWKSYSKBN() {
		return sWKSYSKBN;
	}

	/**
	 * @param swksyskbn The sWKSYSKBN to set.
	 */
	public void setSWKSYSKBN(String swksyskbn) {
		sWKSYSKBN = swksyskbn;
	}

	/**
	 * @return Returns the sWKTEK.
	 */
	public String getSWKTEK() {
		return sWKTEK;
	}

	/**
	 * @param swktek The sWKTEK to set.
	 */
	public void setSWKTEK(String swktek) {
		sWKTEK = swktek;
	}

	/**
	 * @return Returns the sWKTEKCODE.
	 */
	public String getSWKTEKCODE() {
		return sWKTEKCODE;
	}

	/**
	 * @param swktekcode The sWKTEKCODE to set.
	 */
	public void setSWKTEKCODE(String swktekcode) {
		sWKTEKCODE = swktekcode;
	}

	/**
	 * @return Returns the sWKTJKCODE.
	 */
	public String getSWKTJKCODE() {
		return sWKTJKCODE;
	}

	/**
	 * @param swktjkcode The sWKTJKCODE to set.
	 */
	public void setSWKTJKCODE(String swktjkcode) {
		sWKTJKCODE = swktjkcode;
	}

	/**
	 * @return Returns the sWKTRICODE.
	 */
	public String getSWKTRICODE() {
		return sWKTRICODE;
	}

	/**
	 * @param swktricode The sWKTRICODE to set.
	 */
	public void setSWKTRICODE(String swktricode) {
		sWKTRICODE = swktricode;
	}

	/**
	 * @return Returns the sWKTUKEKBN.
	 */
	public String getSWKTUKEKBN() {
		return sWKTUKEKBN;
	}

	/**
	 * @param swktukekbn The sWKTUKEKBN to set.
	 */
	public void setSWKTUKEKBN(String swktukekbn) {
		sWKTUKEKBN = swktukekbn;
	}

	/**
	 * @return Returns the sWKUKEDEPCODE.
	 */
	public String getSWKUKEDEPCODE() {
		return sWKUKEDEPCODE;
	}

	/**
	 * @param swkukedepcode The sWKUKEDEPCODE to set.
	 */
	public void setSWKUKEDEPCODE(String swkukedepcode) {
		sWKUKEDEPCODE = swkukedepcode;
	}

	/**
	 * @return Returns the sWKUKMCODE.
	 */
	public String getSWKUKMCODE() {
		return sWKUKMCODE;
	}

	/**
	 * @param swkukmcode The sWKUKMCODE to set.
	 */
	public void setSWKUKMCODE(String swkukmcode) {
		sWKUKMCODE = swkukmcode;
	}

	/**
	 * @return Returns the sWKUPDKBN.
	 */
	public int getSWKUPDKBN() {
		return sWKUPDKBN;
	}

	/**
	 * @param swkupdkbn The sWKUPDKBN to set.
	 */
	public void setSWKUPDKBN(int swkupdkbn) {
		sWKUPDKBN = swkupdkbn;
	}

	/**
	 * @return Returns the sWKZEICODE.
	 */
	public String getSWKZEICODE() {
		return sWKZEICODE;
	}

	/**
	 * @param swkzeicode The sWKZEICODE to set.
	 */
	public void setSWKZEICODE(String swkzeicode) {
		sWKZEICODE = swkzeicode;
	}

	/**
	 * @return Returns the sWKZEIKBN.
	 */
	public int getSWKZEIKBN() {
		return sWKZEIKBN;
	}

	/**
	 * @param swkzeikbn The sWKZEIKBN to set.
	 */
	public void setSWKZEIKBN(int swkzeikbn) {
		sWKZEIKBN = swkzeikbn;
	}

	/**
	 * @return Returns the sWKZEIKIN.
	 */
	public double getSWKZEIKIN() {
		return sWKZEIKIN;
	}

	/**
	 * @param swkzeikin The sWKZEIKIN to set.
	 */
	public void setSWKZEIKIN(double swkzeikin) {
		sWKZEIKIN = swkzeikin;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("");
		buff.append("").append(kAICODE);
		buff.append("/").append(sWKDENDATE);
		buff.append("/").append(sWKDENNO);
		buff.append("/").append(sWKGYONO);
		buff.append("/").append(sWKUKEDEPCODE);
		buff.append("/").append(sWKTEKCODE);
		buff.append("/").append(sWKTEK);
		buff.append("/").append(sWKSYOEMPCODE);
		buff.append("/").append(sWKSYODATE);
		buff.append("/").append(sWKIRAIEMPCODE);
		buff.append("/").append(sWKIRAIDEPCODE);
		buff.append("/").append(sWKIRAIDATE);
		buff.append("/").append(sWKSYSKBN);
		buff.append("/").append(sWKDENSYU);
		buff.append("/").append(sWKUPDKBN);
		buff.append("/").append(sWKKSNKBN);
		buff.append("/").append(sWKKMKCODE);
		buff.append("/").append(sWKHKMCODE);
		buff.append("/").append(sWKUKMCODE);
		buff.append("/").append(sWKDEPCODE);
		buff.append("/").append(sWKTRICODE);
		buff.append("/").append(sWKEMPCODE);
		buff.append("/").append(sWKCURCODE);
		buff.append("/").append(sWKCURRATE);
		buff.append("/").append(sWKDCKBN);
		buff.append("/").append(sWKZEIKBN);
		buff.append("/").append(sWKZEIKIN);
		buff.append("/").append(sWKZEICODE);
		buff.append("/").append(sWKGYOTEKCODE);
		buff.append("/").append(sWKGYOTEK);
		buff.append("/").append(sWKKNRCODE1);
		buff.append("/").append(sWKKNRCODE2);
		buff.append("/").append(sWKKNRCODE3);
		buff.append("/").append(sWKKNRCODE4);
		buff.append("/").append(sWKKNRCODE5);
		buff.append("/").append(sWKKNRCODE6);
		buff.append("/").append(sWKHM1);
		buff.append("/").append(sWKHM2);
		buff.append("/").append(sWKHM3);
		buff.append("/").append(sWKAUTOKBN);
		buff.append("/").append(hASDATE);
		buff.append("/").append(sWKATKMKCODE);
		buff.append("/").append(sWKATHKMCODE);
		buff.append("/").append(sWKATUKMCODE);
		buff.append("/").append(sWKATDEPCODE);
		buff.append("/").append(sWKKKAICODE);
		buff.append("/").append(sWKSEINO);
		buff.append("/").append(sWKKIN);
		buff.append("/").append(sWKINKIN);
		buff.append("/").append(sWKSIHAKBN);
		buff.append("/").append(sWKSIHADATEAP);
		buff.append("/").append(sWKHOHCODEAP);
		buff.append("/").append(sWKHORYUKBN);
		buff.append("/").append(sWKTJKCODE);
		buff.append("/").append(sWKARDATE);
		buff.append("/").append(sWKUKEDATE);
		buff.append("/").append(sWKCBKCODE);
		buff.append("/").append(sWKTUKEKBN);
		buff.append("/").append(sWKINZEIKIN);
		buff.append("");
		return buff.toString();
	}

	/**
	 * servlet response用
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();
		list.add(kAICODE);
		list.add(sWKDENDATE);
		list.add(sWKDENNO);
		list.add(sWKGYONO);
		list.add(sWKUKEDEPCODE);
		list.add(sWKTEKCODE);
		list.add(sWKTEK);
		list.add(sWKSYOEMPCODE);
		list.add(sWKSYODATE);
		list.add(sWKIRAIEMPCODE);
		list.add(sWKIRAIDEPCODE);
		list.add(sWKIRAIDATE);
		list.add(sWKSYSKBN);
		list.add(sWKDENSYU);
		list.add(sWKUPDKBN);
		list.add(sWKKSNKBN);
		list.add(sWKKMKCODE);
		list.add(sWKHKMCODE);
		list.add(sWKUKMCODE);
		list.add(sWKDEPCODE);
		list.add(sWKTRICODE);
		list.add(sWKEMPCODE);
		list.add(sWKCURCODE);
		list.add(sWKCURRATE);
		list.add(sWKDCKBN);
		list.add(sWKZEIKBN);
		list.add(sWKZEIKIN);
		list.add(sWKZEICODE);
		list.add(sWKGYOTEKCODE);
		list.add(sWKGYOTEK);
		list.add(sWKKNRCODE1);
		list.add(sWKKNRCODE2);
		list.add(sWKKNRCODE3);
		list.add(sWKKNRCODE4);
		list.add(sWKKNRCODE5);
		list.add(sWKKNRCODE6);
		list.add(sWKHM1);
		list.add(sWKHM2);
		list.add(sWKHM3);
		list.add(sWKAUTOKBN);
		list.add(hASDATE);
		list.add(sWKATKMKCODE);
		list.add(sWKATHKMCODE);
		list.add(sWKATUKMCODE);
		list.add(sWKATDEPCODE);
		list.add(sWKKKAICODE);
		list.add(sWKSEINO);
		list.add(sWKKIN);
		list.add(sWKINKIN);
		list.add(sWKSIHAKBN);
		list.add(sWKSIHADATEAP);
		list.add(sWKHOHCODEAP);
		list.add(sWKHORYUKBN);
		list.add(sWKTJKCODE);
		list.add(sWKARDATE);
		list.add(sWKUKEDATE);
		list.add(sWKCBKCODE);
		list.add(sWKTUKEKBN);
		list.add(sWKINZEIKIN);
		return list;
	}

	/**
	 * @return String
	 */
	public String getSWKIRAIDATE() {
		return sWKIRAIDATE;
	}

	/**
	 * @param swkiraidate
	 */
	public void setSWKIRAIDATE(String swkiraidate) {
		sWKIRAIDATE = swkiraidate;
	}

	/**
	 * @return String
	 */
	public String getSWKUKEDATE() {
		return sWKUKEDATE;
	}

	/**
	 * @param swkukedate
	 */
	public void setSWKUKEDATE(String swkukedate) {
		sWKUKEDATE = swkukedate;
	}

}
