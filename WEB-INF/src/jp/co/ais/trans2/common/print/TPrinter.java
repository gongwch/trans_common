package jp.co.ais.trans2.common.print;

import java.io.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * プリンター
 * 
 * @author AIS
 */
public class TPrinter {

	/** 伝票番号桁数 */
	public static final int SLIP_NO_LENGTH = 37;

	/** PDF */
	public static final String PDF = "PDF";

	/** XLS(Excel 2003) ヘッダーバイト定義 */
	public static final byte[] XLS_HEADER = new byte[] { -48, -49, 17, -32, -95, -79, 26, -31 };

	/** PDF直接印刷フラグ */
	public static boolean directPrint = ClientConfig.isFlagOn("trans.pdf.direct.print");

	/** true:バイナリはZIPバイナリ */
	public boolean zipBinary = false;

	/**
	 * コンストラクター
	 */
	public TPrinter() {
		//
	}

	/**
	 * コンストラクター
	 * 
	 * @param zipBinary true:バイナリはZIPバイナリ
	 */
	public TPrinter(boolean zipBinary) {
		this.zipBinary = zipBinary;
	}

	/**
	 * プレビューする
	 * 
	 * @param data データ
	 * @param fileName ファイル名
	 * @throws TException
	 */
	public void preview(byte[] data, String fileName) throws TException {

		if (hasSlipNo(data)) {
			// 伝票番号含み
			dispatchWithSlipNo(data, fileName);
		} else {
			createAndExecute(data, fileName);
		}

	}

	/**
	 * プレビューする
	 * 
	 * @param data データ
	 * @param fileName ファイル名
	 * @throws TException
	 */
	public void previewAttach(byte[] data, String fileName) throws TException {
		createAndExecute(data, fileName, true);
	}

	/**
	 * PDF出力判定
	 * 
	 * @param data
	 * @param fileName ファイル名
	 * @throws TException
	 */
	public void createAndExecute(byte[] data, String fileName) throws TException {
		createAndExecute(data, fileName, false);
	}

	/**
	 * PDF出力判定
	 * 
	 * @param data
	 * @param fileName ファイル名
	 * @param useOriginal
	 * @throws TException
	 */
	public void createAndExecute(byte[] data, String fileName, boolean useOriginal) throws TException {

		// ファイルを所定パスに保存
		String dir = createResultFile(fileName, data, useOriginal);
		String exts = StringUtil.rightBX(fileName, 3).toUpperCase();

		String printerName = null;
		if (TLoginInfo.getUser() != null) {
			printerName = TLoginInfo.getUser().getPrinterName();
		}

		// 直接印刷の判定
		if (isDirectPrint() && PDF.equals(exts) && !Util.isNullOrEmpty(printerName)) {
			// 直接印刷
			SystemUtil.printPDF(dir, printerName);

		} else {
			// ファイル起動
			SystemUtil.executeFile(dir);
		}

	}

	/**
	 * 伝票番号含むかどうか
	 * 
	 * @param data
	 * @return true:伝票番号含み
	 */
	public boolean hasSlipNo(byte[] data) {
		if (data == null || data.length < SLIP_NO_LENGTH) {
			return false;
		}

		byte[] sign = new byte[SLIP_NO_LENGTH];
		StringUtil.arraycopy(data, data.length - SLIP_NO_LENGTH, sign, 0, SLIP_NO_LENGTH);

		String signature = Util.avoidNull(new String(sign));
		if (signature.startsWith("<SlipNo>")) {
			return true;
		}

		return false;
	}

	/**
	 * 指定伝票番号をファイル名先頭に付けてPDFファイル作成
	 * 
	 * @param data
	 * @param fileName
	 * @throws TException
	 */
	public void dispatchWithSlipNo(byte[] data, String fileName) throws TException {

		byte[] sign = new byte[20];
		StringUtil.arraycopy(data, data.length - SLIP_NO_LENGTH + 8, sign, 0, 20);

		String signature = Util.avoidNull(new String(sign));
		fileName = signature + "_" + Util.avoidNull(fileName);

		createAndExecute(data, fileName);
	}

	/**
	 * 印刷する
	 * 
	 * @param data データ
	 * @param fileName ファイル名
	 * @throws TException
	 */
	public void print(byte[] data, String fileName) throws TException {

		// ファイルを所定パスに保存
		String dir = createResultFile(fileName, data);

		// ファイル起動
		SystemUtil.printPDF(dir);

	}

	/**
	 * ファイルダウンロード結果の出力(TEMPフォルダ保存)
	 * 
	 * @param fileName ファイル名
	 * @param binary
	 * @return 出力ファイル名
	 * @throws TException
	 */
	public String createResultFile(String fileName, byte[] binary) throws TException {
		return createResultFile(fileName, binary, false);
	}

	/**
	 * ファイルダウンロード結果の出力(TEMPフォルダ保存)
	 * 
	 * @param fileName ファイル名
	 * @param binary
	 * @param useOriginal
	 * @return 出力ファイル名
	 * @throws TException
	 */
	public String createResultFile(String fileName, byte[] binary, boolean useOriginal) throws TException {

		// 一時Tempファイル場所
		String dirPath = SystemUtil.getTemporaryDir();

		// ファイル展開.展開されたファイル名が戻る
		return createResultFile(dirPath, fileName, binary, useOriginal);

	}

	/**
	 * ファイルダウンロード結果の出力(指定フォルダ保存)
	 * 
	 * @param dirPath ファイル出力場所
	 * @param fileName ファイル名
	 * @param binary
	 * @return 出力ファイル名
	 * @throws TException
	 */
	public String createResultFile(String dirPath, String fileName, byte[] binary) throws TException {
		return createResultFile(dirPath, fileName, binary, false);
	}

	/**
	 * ファイルダウンロード結果の出力(指定フォルダ保存)
	 * 
	 * @param dirPath ファイル出力場所
	 * @param fileName ファイル名
	 * @param binary
	 * @param useOriginal
	 * @return 出力ファイル名
	 * @throws TException
	 */
	public String createResultFile(String dirPath, String fileName, byte[] binary, boolean useOriginal) throws TException {

		// 出力ファイルが「XLSX」の場合、ファイル拡張子を訂正「xls」⇒「xlsx」
		if (!useOriginal && ExcelType.XLS.toString().equals(StringUtil.rightBX(fileName, 3).toUpperCase())) {

			// OLE識別子判定
			boolean isXLS = true;

			if (isZipBinary()) {
				// ZIP バイナリの場合、解凍してから判定する
				try {
					binary = ResourceUtil.toBinaryInZip(binary);
					zipBinary = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			ClientLogger.info("check xls binary header");

			if (binary.length > 0) {
				for (int i = 0; i < XLS_HEADER.length; i++) {
					byte b1 = XLS_HEADER[i];
					byte b2 = binary[i];

					if (b1 != b2) {
						isXLS = false;
						break;
					}
				}
			}

			if (!isXLS) {
				ClientLogger.info("binary header is xlsx");
				fileName += "x";
			} else {
				ClientLogger.info("binary header is xls");
			}
		}

		// パス文字を削除
		fileName = FileUtil.toNonProhibitedName(fileName);

		String tmpFileName = fileName;

		// ファイルが開かれている場合、別名を付けて保存する
		for (int i = 1; !ResourceUtil.canWrite(dirPath + tmpFileName); i++) {

			if (i == 100) {
				// パス指定ミス時の無限ループ回避
				throw new TRuntimeException("File count over100." + fileName);
			}

			tmpFileName = "(" + i + ")" + fileName;

		}

		return createResultFileNative(dirPath, tmpFileName, binary);
	}

	/**
	 * ファイルダウンロード結果の出力(指定フォルダ、ファイル名保存).<br>
	 * 共通処理
	 * 
	 * @param dirPath ファイル出力場所
	 * @param fileName ファイル名(拡張子付き)
	 * @param binary
	 * @return 出力ファイル名
	 * @throws TException
	 */
	public String createResultFileNative(String dirPath, String fileName, byte[] binary) throws TException {

		try {

			String fullPath = dirPath + fileName;

			ClientLogger.info("zip file binary=" + binary.length);
			ClientLogger.memory("write binay start");

			if (isZipBinary()) {
				ClientLogger.info("write zip binary");
				ResourceUtil.writeBinaryInZip(fullPath, binary);
			} else {
				ClientLogger.info("write binary");
				ResourceUtil.writeBinary(fullPath, binary);
			}

			ClientLogger.memory("write binary end");
			ClientLogger.debug("create fileName=" + fullPath);

			return fullPath;

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @return true:直接印刷
	 */
	public boolean isDirectPrint() {
		return directPrint;
	}

	/**
	 * @return zipBinary true:バイナリはZIPバイナリ
	 */
	public boolean isZipBinary() {
		return zipBinary;
	}

	/**
	 * @param zipBinary true:バイナリはZIPバイナリ
	 */
	public void setZipBinary(boolean zipBinary) {
		this.zipBinary = zipBinary;
	}

}
