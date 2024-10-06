package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 伝票マネージャ 伝票関連の操作（取得・登録・削除など）を制御するクラス
 */
public interface SlipManager {

	/**
	 * 伝票を構築する.<br>
	 * 付替、消費税仕訳、機能通貨仕訳
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildSlip(Slip slip) throws TException;

	/**
	 * 伝票を起票する
	 * 
	 * @param slip 伝票クラス
	 * @throws TException
	 */
	public void entry(Slip slip) throws TException;

	/**
	 * 伝票を構築して登録する.<br>
	 * 付替、消費税仕訳、機能通貨仕訳、削除履歴 あり
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildAndEntry(Slip slip) throws TException;

	/**
	 * 伝票番号を採番し、伝票にセットする
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setSlipNo(Slip slip) throws TException;

	/**
	 * 伝票のチェック
	 * 
	 * @param slip 伝票
	 * @return エラーメッセージリスト
	 * @throws TException
	 */
	public List<Message> checkSlipError(Slip slip) throws TException;

	/**
	 * 伝票を削除する
	 * 
	 * @param slip 対象伝票
	 */
	public void delete(Slip slip);

	/**
	 * 伝票を削除する
	 * 
	 * @param slip 対象伝票
	 * @param isSaveHistory 履歴を保存するかどうか
	 * @param isSaveDelHistory 削除履歴を保存するかどうか
	 */
	public void delete(Slip slip, boolean isSaveHistory, boolean isSaveDelHistory);

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 */
	public void delete(String slipNo, String slipType);

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 * @param isSaveHistory 履歴を保存するかどうか
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory);

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 * @param isSaveHistory 履歴を保存するかどうか
	 * @param isSaveDelHistory 削除履歴を保存するかどうか
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory, boolean isSaveDelHistory);

	/**
	 * 伝票排他
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void lock(Slip slip) throws TException;

	/**
	 * 伝票関連テーブルロック
	 * 
	 * @throws TException
	 */
	public void lockSlipTable() throws TException;

	/**
	 * 伝票排他解除
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void unlock(Slip slip) throws TException;

	/**
	 * 伝票排他解除
	 * 
	 * @param slipType 伝票種別
	 * @throws TException
	 */
	public void unlockAll(String slipType) throws TException;

	/**
	 * 伝票を構築する.<br>
	 * 各種値設定、消費税仕訳
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票パターン
	 * @throws TException
	 */
	public List<Slip> buildSlipPattern(Slip slip) throws TException;

	/**
	 * 伝票パターンを起票する
	 * 
	 * @param slip 伝票クラス
	 * @throws TException
	 */
	public void entryPattern(Slip slip) throws TException;

	/**
	 * 伝票パターンを構築して登録する.<br>
	 * 消費税仕訳
	 * 
	 * @param motoSlip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildAndEntryPattern(Slip motoSlip) throws TException;

	/**
	 * 伝票パターンを削除する
	 * 
	 * @param slip 対象伝票
	 * @throws TException
	 */
	public void deletePattern(Slip slip) throws TException;

	/**
	 * 伝票パターン排他
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void lockPattern(Slip slip) throws TException;

	/**
	 * 伝票パターン排他解除
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void unlockPattern(Slip slip) throws TException;

	/**
	 * 伝票パターン排他解除
	 * 
	 * @param slipType 伝票種別
	 * @throws TException
	 */
	public void unlockPatternAll(String slipType) throws TException;

	/**
	 * ヘッダデータ取得
	 * 
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getHeader(SlipCondition param);

	/**
	 * ヘッダデータのエクセル取得
	 * 
	 * @param param 条件
	 * @return ヘッダリストのエクセル
	 * @throws TException
	 */
	public byte[] getHeaderExcel(SlipCondition param) throws TException;

	/**
	 * 明細データ取得
	 * 
	 * @param param 条件
	 * @return 仕訳リスト
	 */
	public List<SWK_DTL> getDetails(SlipCondition param);

	/**
	 * ヘッダデータ取得
	 * 
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getPatternHeader(SlipCondition param);

	/**
	 * 明細データ取得
	 * 
	 * @param param 条件
	 * @return 仕訳リスト
	 */
	public List<SWK_DTL> getPatternDetails(SlipCondition param);

	/**
	 * ヘッダデータ取得
	 * 
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getHistoryHeader(SlipCondition param);

	/**
	 * 明細データ取得
	 * 
	 * @param param 条件
	 * @return 仕訳リスト
	 */
	public List<SWK_DTL> getHistoryDetails(SlipCondition param);

	/**
	 * 伝票取得
	 * 
	 * @param param 条件
	 * @return 伝票リスト
	 */
	public List<Slip> getSlip(SlipCondition param);

	/**
	 * 指定ヘッダより伝票を取得する.
	 * 
	 * @param hdr 伝票
	 * @param condition
	 * @return 伝票
	 * @throws TException
	 */
	public Slip getSlip(SWK_HDR hdr, SlipCondition condition) throws TException;

	/**
	 * 指定条件の帳簿を取得する.
	 * 
	 * @param condition 条件
	 * @return 帳簿リスト
	 */
	public List<SlipBooks> getSlipBooks(SlipCondition condition);

	/**
	 * 指定番号の帳簿を取得する.
	 * 
	 * @param compCode 会社コード
	 * @param slipNo 伝票番号
	 * @return 帳簿
	 */
	public SlipBooks getSlipBooks(String compCode, String slipNo);

	/**
	 * 指定ヘッダより伝票パターンを取得する.
	 * 
	 * @param hdr 伝票
	 * @return 伝票
	 * @throws TException
	 */
	public Slip getPatternSlip(SWK_HDR hdr) throws TException;

	/**
	 * 伝票パターン取得
	 * 
	 * @param condition 条件
	 * @return 伝票パターンリスト
	 */
	public List<Slip> getPatternSlip(SlipCondition condition);

	/**
	 * 削除伝票取得
	 * 
	 * @param condition 条件
	 * @return 削除伝票リスト
	 */
	public List<Slip> getHistorySlip(SlipCondition condition);

	/**
	 * 指定条件の帳簿を取得する.(履歴用)
	 * 
	 * @param condition 条件
	 * @return 帳簿リスト
	 */
	public List<SlipBooks> getHistorySlipBooks(SlipCondition condition);

	/**
	 * 一時伝票帳票を返す
	 * 
	 * @param tempSlip 一時伝票
	 * @return 一時伝票帳票
	 * @throws TException
	 */
	public byte[] getTempSlipReport(Slip tempSlip) throws TException;

	/**
	 * 伝票帳票を返す
	 * 
	 * @param slips 伝票リスト(付替先を含む伝票データ)
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(List<Slip> slips) throws TException;

	/**
	 * 伝票帳票を返す
	 * 
	 * @param slip 伝票
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(Slip slip) throws TException;

	/**
	 * 指定会社、伝票番号の伝票帳票を返す。<BR>
	 * 原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, String slipNo) throws TException;

	/**
	 * 指定会社、伝票番号の伝票帳票を返す。<BR>
	 * 原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param slipNoList 伝票番号リスト
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, List<String> slipNoList) throws TException;

	/**
	 * 指定伝票番号(複数)の伝票帳票を返す。<BR>
	 * 原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。 付替仕訳の場合、自社・相手会社両方の伝票を出力する。
	 * 
	 * @param slipNoList 伝票番号リスト
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReportBySlipNos(List<String> slipNoList) throws TException;

	/**
	 * 伝票Bookを返す
	 * 
	 * @param slip
	 * @return 伝票Book
	 * @throws TException
	 */
	public SlipBook getReportBook(Slip slip) throws TException;

	/**
	 * 伝票を承認する
	 * 
	 * @param list 承認する伝票のリスト
	 * @return 承認後の伝票
	 * @throws TException
	 */
	public List<SlipDen> approveSlip(List<SlipDen> list) throws TException;

	/**
	 * 伝票を否認する
	 * 
	 * @param list 承認する伝票のリスト
	 * @return 否認後の伝票
	 * @throws TException
	 */
	public List<SlipDen> denySlip(List<SlipDen> list) throws TException;

	/**
	 * 伝票承認（経理承認）を取り消す
	 * 
	 * @param list 承認を取り消す伝票のリスト
	 * @return 承認取消後の伝票
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlip(List<SlipDen> list) throws TException;

	/**
	 * 伝票を承認（現場承認）する
	 * 
	 * @param list 承認する伝票のリスト
	 * @return 承認後の伝票
	 * @throws TException
	 */
	public List<SlipDen> approveSlipForFieldState(List<SlipDen> list) throws TException;

	/**
	 * 伝票を否認（現場否認）する
	 * 
	 * @param list 承認する伝票のリスト
	 * @return 否認後の伝票
	 * @throws TException
	 */
	public List<SlipDen> denySlipForFieldState(List<SlipDen> list) throws TException;

	/**
	 * 伝票承認を取り消す
	 * 
	 * @param list 承認を取り消す伝票のリスト
	 * @return 承認取消後の伝票
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlipForFieldState(List<SlipDen> list) throws TException;

	/**
	 * 伝票ロジックの取得
	 * 
	 * @param slipType 伝票種別
	 * @return 伝票ロジック
	 */
	public SlipLogic getSlipLogic(String slipType);

	/**
	 * 伝票明細を構築する.
	 * 
	 * @param slip 伝票
	 * @return 伝票
	 * @throws TException
	 */
	public Slip setupDetails(Slip slip) throws TException;

	/**
	 * 伝票明細を構築する.
	 * 
	 * @param slipList 同じ伝票種別想定
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @return 伝票
	 * @throws TException
	 */
	public List<Slip> setupDetails(List<Slip> slipList, boolean includeBalance) throws TException;

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip 伝票
	 * @return 伝票
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip) throws TException;

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip
	 * @param recalc 必要な場合のみ再計算
	 * @return 伝票
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip, boolean recalc) throws TException;

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip
	 * @param recalc 必要な場合のみ再計算
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @return 伝票
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip, boolean recalc, boolean includeBalance) throws TException;

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slipList 同じ伝票種別想定
	 * @param recalc 必要な場合のみ再計算
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @return 伝票
	 * @throws TException
	 */
	public List<Slip> setupDetailsOptional(List<Slip> slipList, boolean recalc, boolean includeBalance)
		throws TException;

	/**
	 * 自動仕訳科目の取得
	 * 
	 * @param companyCode 会社コード
	 * @param kind 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, AutoJornalAccountType kind) throws TException;

	/**
	 * 自動仕訳科目の取得
	 * 
	 * @param companyCode 会社コード
	 * @param type 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, int type) throws TException;

	/**
	 * 自動仕訳科目の取得(複数指定)
	 * 
	 * @param companyCode 会社コード
	 * @param kinds 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, AutoJornalAccountType... kinds)
		throws TException;

	/**
	 * 自動仕訳科目の取得(複数指定)
	 * 
	 * @param companyCode 会社コード
	 * @param types 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, int... types) throws TException;

	/**
	 * 削除伝票リストデータを返す
	 * 
	 * @param condition 検索条件
	 * @return DeleteSlipListGetterBook
	 * @throws TException
	 */
	public DeleteSlipListBook getDeletedSlipListBook(SlipCondition condition) throws TException;

	/**
	 * 削除伝票リストデータ(帳票)を返す
	 * 
	 * @param condition 検索条件
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListReport(SlipCondition condition) throws TException;

	/**
	 * 削除伝票リストデータ(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListExcel(SlipCondition condition) throws TException;

	/**
	 * 削除履歴の削除
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void deleteDelHistory(Slip slip) throws TException;

	/**
	 * 削除履歴の登録
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void entryDelHistory(Slip slip) throws TException;

	/**
	 * AP/AR消込情報の更新
	 * 
	 * @param slip
	 * @param kesiKbn
	 * @throws TException
	 */
	public void updateAPARInfo(Slip slip, int kesiKbn) throws TException;

	/**
	 * AP/AR消込仕訳の復元処理
	 * 
	 * @param condition
	 * @throws TException
	 */
	public void restoreAPAR(SlipCondition condition) throws TException;

	/**
	 * 明細リストからエクセルを取得
	 * 
	 * @param list
	 * @return 明細リストエクセル
	 * @throws TException
	 */
	public byte[] getDetailExcel(List<SWK_DTL> list) throws TException;

	/**
	 * エクセル明細リストをbeanに変換して返却
	 * 
	 * @param file
	 * @param slipType
	 * @return 明細beanリスト
	 * @throws TException
	 */
	public List<SWK_DTL> convertExcelToDetails(File file, SlipType slipType) throws TException;

	/**
	 * 借入情報の更新
	 * 
	 * @param slip
	 * @param status
	 * @throws TException
	 */
	public void updateLMInfo(Slip slip, int status) throws TException;

	/**
	 * 伝票エクスポートエクセルを生成＆取得
	 * 
	 * @param companyCode
	 * @param slipNoList
	 * @return 明細リストエクセル
	 * @throws TException
	 */
	public byte[] getExportSlipExcel(String companyCode, List<String> slipNoList) throws TException;

	/**
	 * 伝票エクスポートエクセルを生成＆取得(付替仕訳の場合、自社・相手会社両方の伝票を出力する。)
	 * 
	 * @param slipNoList
	 * @return 明細リストエクセル
	 * @throws TException
	 */
	public byte[] getExportSlipExcelBySlipNos(List<String> slipNoList) throws TException;

	/**
	 * 伝票を確認する
	 * 
	 * @param prgCode
	 * @param denSyuMst
	 * @return Class
	 * @throws TException
	 */
	public Class getSlipPanel(String prgCode, String denSyuMst) throws TException;

	/**
	 * 伝票番号/修正回数で伝票が存在しているかチェック
	 * 
	 * @param kaiCode
	 * @param slipNo
	 * @param slipUpdCnt
	 * @throws TException
	 */
	public void checkSlipInfo(String kaiCode, String slipNo, int slipUpdCnt) throws TException;

	/**
	 * 伝票をチェックして、承認
	 * 
	 * @param den
	 * @param isAsMuchAsPossible
	 * @return 承認後の伝票
	 * @throws TException
	 */
	public SlipDen checkAndApproveSlip(SlipDen den, Boolean isAsMuchAsPossible) throws TException;

	/**
	 * 伝票をチェックして、取消
	 * 
	 * @param den
	 * @return 承認取消後の伝票
	 * @throws TException
	 */
	public SlipDen checkAndCancelApprovedSlip(SlipDen den) throws TException;
}
