package jp.co.ais.trans2.common.server;

import java.io.*;
import java.text.*;

import javax.servlet.*;
import javax.servlet.http.*;

import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;

/**
 * ファイルダウンロード用
 */
public class TDownloadServlet extends TServletBase {

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
		String uid = Util.avoidNull(req.getParameter("token"));
		if (Util.isNullOrEmpty(uid)) {
			return;
		}

		String fileName = getTempFileName(uid);
		File file = createTempFile(fileName);

		try {
			printOutFile(resp, file);
			
			FileUtil.removeFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param resp
	 * @param fileOut
	 * @throws IOException
	 */
	protected void printOutFile(HttpServletResponse resp, File fileOut) throws IOException {
		OutputStream os = resp.getOutputStream();
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(fileOut);
			bis = new BufferedInputStream(fis);

			// レスポンス設定
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "filename=\"" + fileOut.getName() + "\"");

			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}

			bis.close();

		} catch (IOException e) {
			printOutNotFound(resp);
		} finally {
			ResourceUtil.closeInputStream(fis);
			ResourceUtil.closeInputStream(bis);
			ResourceUtil.closeOutputStream(os);
		}
	}

	/**
	 * @param res
	 */
	protected void printOutNotFound(HttpServletResponse res) {

		try {
			OutputStream toClient = res.getOutputStream();
			res.setContentType("text/html;charset=utf-8");
			toClient.write("File not found".getBytes());
			toClient.close();
		} catch (IOException e) {
			// do nothing
		}
	}
}
