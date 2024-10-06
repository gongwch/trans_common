package jp.co.ais.trans2.bat;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.model.attach.verify.*;

/**
 * 
 */
public class AttachmentVerifier extends Executable {

	/**
	 * メインメソッド
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new AttachmentVerifier().start();
	}

	/**
	 * コンテナ定義名
	 * 
	 * @return コンテナ定義名
	 */
	@Override
	protected String getContainerName() {
		return "verify.dicon";
	}

	/**
	 * 設定ファイル読込
	 * 
	 * @return false:失敗
	 */
	@Override
	protected boolean readConfig() {
		return true;
	}

	/**
	 * @see jp.co.ais.trans2.bat.Executable#verifyConfig()
	 */
	@Override
	protected boolean verifyConfig() {
		return true;
	}

	/**
	 * 取込処理
	 */
	@Override
	protected void execute() {
		writeDebug("添付ファイル検証 開始");

		try {
			// 実行モジュール
			AttachmentVerifyManager logic = (AttachmentVerifyManager) container
				.getComponent(AttachmentVerifyManager.class);

			try {
				List<AttachmentVerifyResult> result = logic.get();
				writeLog("AttachmentVerifyLog", result);
			} catch (Throwable ex) {
				writeError(ex);
			}

		} catch (Throwable ex) {
			writeError(ex);

		} finally {
			writeDebug("添付ファイル検証 終了");
		}
	}

	/**
	 * エラーログ記述
	 * 
	 * @param fileName エラーファイル名
	 * @param logList ログリスト
	 * @throws TException
	 */
	protected void writeLog(String fileName, List<AttachmentVerifyResult> logList) throws TException {

		FileWriter writer = null;
		try {
			String logName = FileUtil.removeExtension(fileName);
			logName += "_" + DateUtil.toYMDHMSPlainString(new Date()) + ".log";
			writer = new FileWriter(new File(getLogPath() + logName));

			for (AttachmentVerifyResult result : logList) {
				List<String> list = new LinkedList<String>();
				list.add(result.getKAI_CODE());
				list.add(result.getTYPE().toString());
				list.add(result.getKEY1());
				list.add(result.getKEY2());
				list.add(result.getKEY3());
				list.add(result.getKEY4());
				list.add(result.getFILE_NAME());
				list.add(result.getSRV_FILE_NAME());
				list.add(result.getMESSAGE());

				String line = StringUtil.toCommaString(list);
				writer.write(line + SystemUtil.LINE_SEP);
			}

			writer.flush();

		} catch (IOException e) {
			throw new TException(e, "エラーログ出力に失敗しました。");

		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				ServerLogger.debug(e);
			}
		}
	}

	/**
	 * ログファイル出力先パス
	 * 
	 * @return ログファイル出力先パス
	 */
	protected String getLogPath() {
		try {
			return ServerConfig.getProperty("trans.attachment.verify.log.dir");
		} catch (Exception e) {
			return "C:\\temp\\";
		}
	}

}
