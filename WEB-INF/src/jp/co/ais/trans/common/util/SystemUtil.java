package jp.co.ais.trans.common.util;

import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;

/**
 * システム系ユーティリティ
 */
public final class SystemUtil {

	/** 改行文字 */
	public static String LINE_SEP = System.getProperty("line.separator");

	/** 変換リスト「^」はエスケープのため、一番最初にする必要 */
	public static char[] ESCAPE_LIST = ("^" + " &()[]{}=;!'+,`~　").toCharArray();

	/**
	 * 一時ファイル保存ディレクトリパスを取得する
	 * 
	 * @return ディレクトリパス
	 */
	public static String getTemporaryDir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * @return AIS用各種(ログイン情報/スプレッド調整情報)の保存フォルダ
	 */
	public static String getAisSettingDir() {
		String dir = getUserHomeDir() + "\\.ais\\setting\\";
		File file = new File(dir);
		file.mkdirs();
		return dir;
	}

	/**
	 * @return AIS用フォルダ
	 */
	public static String getAisDir() {
		String dir = getUserHomeDir() + "\\.ais\\";
		File file = new File(dir);
		file.mkdirs();
		return dir;
	}

	/**
	 * ユーザホームディレクトリパスを取得する
	 * 
	 * @return ディレクトリパス
	 */
	public static String getUserHomeDir() {
		return System.getProperty("user.home");
	}

	/**
	 * @param fileName
	 * @return ファイル名エスケープ
	 */
	public static String escapeFileName(String fileName) {
		String name = fileName;
		for (char str : ESCAPE_LIST) {
			name = name.replaceAll("\\" + str, "^" + str);
		}
		return name;
	}

	/**
	 * ファイル起動
	 * 
	 * @param fileName 起動ファイル名
	 * @return 実行プロセス
	 * @throws TException 起動失敗
	 */
	public static Process executeFile(String fileName) throws TException {
		String name = escapeFileName(fileName);
		String cmd = "cmd /c " + "\"" + name + "\"";

		return exec(cmd);
	}

	/**
	 * PDFファイル印刷
	 * 
	 * @param fileName 印刷ファイル名
	 * @return 実行プロセス
	 * @throws TException 印刷失敗
	 */
	public static Process printPDF(String fileName) throws TException {
		startPDF();

		String cmd = "cmd /c start acrord32.exe /p /h " + "\"" + fileName + "\"";

		return exec(cmd);
	}

	/**
	 * PDFファイル印刷(Waitバージョン)
	 * 
	 * @param fileName 印刷ファイル名
	 * @return 実行プロセス
	 * @throws TException 印刷失敗
	 */
	public static Process printPDFForWait(String fileName) throws TException {

		String cmd = "cmd /c start /WAIT acrord32.exe /p /h " + "\"" + fileName + "\"";

		try {
			startPDF();

			Process process = exec(cmd);
			process.waitFor();

			return process;

		} catch (InterruptedException ex) {
			throw new TException(ex, "E00019", cmd);
		}
	}

	/**
	 * PDFファイル印刷
	 * 
	 * @param fileName 印刷ファイル名
	 * @param printerName プリンタ名
	 * @return 実行プロセス
	 * @throws TException 印刷失敗
	 */
	public static Process printPDF(String fileName, String printerName) throws TException {
		startPDF();

		String cmd = "cmd /c start acrord32.exe /t /h " + "\"" + fileName + "\" " + "\"" + printerName + "\"";

		return exec(cmd);
	}

	/**
	 * 初回印刷ズレを防ぐ為、最初にAcrobatを起動させる
	 */
	public static void startPDF() {
		try {
			String cmd = "cmd /c start acrord32.exe";

			exec(cmd);

			// 起動を少し待つ
			Thread.sleep(1000);
		} catch (Exception ex) {
			// なし
		}
	}

	/**
	 * Runtimeコマンド実行
	 * 
	 * @param cmd コマンド
	 * @return 実行プロセス
	 * @throws TException
	 */
	public static Process exec(String cmd) throws TException {

		Process process = null;

		try {
			process = Runtime.getRuntime().exec(cmd);

			ClientLogger.debug(cmd);

		} catch (IOException ex) {
			throw new TException(ex, "E00019", cmd);
		}

		return process;
	}

	/**
	 * 実行中のPDFアプリを強制的に閉じる
	 * 
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	public void closePDFApp() throws TException {
		String cmd = "cmd /c taskkill.exe /IM acrord32.exe /f /FI \"STATUS eq RUNNING\"";

		try {
			Runtime.getRuntime().exec(cmd);

			ClientLogger.debug(cmd);

		} catch (IOException ex) {
			throw new TException(ex, "E00019", cmd);
		}
	}
}
