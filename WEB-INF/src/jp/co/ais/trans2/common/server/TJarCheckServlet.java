package jp.co.ais.trans2.common.server;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.codec.digest.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;

/**
 * ファイルダウンロード用
 */
public class TJarCheckServlet extends TServletBase {

	/** 各ファイルのハッシュマップ */
	protected static Map<String, String> hashMap;

	static {
		hashMap = new HashMap<String, String>();
	}

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
		ParseException {
		//
	}

	/**
	 * Post method 処理. <br>
	 * method=postで送信されたデータを処理する。
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String filename = Util.avoidNull(req.getParameter("file"));
		if (Util.isNullOrEmpty(filename)) {
			return;
		}

		File file = getFile(filename);

		long ts = getFileTimestamp(file);
		String hash = sha1Hex(file);

		printOutTimestamp(resp, ts, hash);
	}

	/**
	 * @param filename
	 * @return ファイル
	 * @throws TRuntimeException
	 */
	protected File getFile(String filename) throws TRuntimeException {
		String realpath = getServletContext().getRealPath(filename);
		File file = new File(realpath);
		if (!file.exists()) {
			throw new TRuntimeException("file is not exists. " + filename);
		}

		return file;
	}

	/**
	 * ファイルTimestampの取得
	 * 
	 * @param file
	 * @return ファイルTimestamp
	 * @throws TRuntimeException
	 */
	protected long getFileTimestamp(File file) throws TRuntimeException {
		return file.lastModified();
	}

	/**
	 * @param file
	 * @return SHA-1
	 * @throws TRuntimeException
	 */
	public static String sha1Hex(File file) throws TRuntimeException {

		String key = file.toString();
		if (hashMap.containsKey(key)) {
			return hashMap.get(key);
		}

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			String sha1 = DigestUtils.sha1Hex(fis);
			hashMap.put(key, sha1);

			return sha1;

		} catch (FileNotFoundException ex) {
			throw new TRuntimeException(ex, "file is not exists. " + file.toString());
		} catch (IOException ex) {
			throw new TRuntimeException(ex, "file access error. " + file.toString());
		} finally {
			ResourceUtil.closeInputStream(fis);
		}
	}

	/**
	 * @param timestamp
	 * @return String 2 Long
	 */
	protected static long getTimestamp(String timestamp) {
		try {
			long ts = Long.parseLong(timestamp);
			return ts;
		} catch (Exception ex) {
			return -1;
		}
	}

	/**
	 * @param res
	 * @param ts
	 * @param hash ファイルHASH
	 */
	protected void printOutTimestamp(HttpServletResponse res, long ts, String hash) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(Long.toString(ts));
			sb.append("<>");
			sb.append(hash);

			OutputStream toClient = res.getOutputStream();
			res.setContentType("text/html;charset=utf-8");
			toClient.write(sb.toString().getBytes());
			toClient.close();
		} catch (IOException e) {
			// do nothing
		}
	}
}
