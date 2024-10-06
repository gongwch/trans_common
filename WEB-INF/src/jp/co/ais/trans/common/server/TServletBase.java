package jp.co.ais.trans.common.server;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Map.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * Servletベースクラス.
 * 
 * @author ais-y inumazawa
 */
public abstract class TServletBase extends HttpServlet {

	/** ダウンロードファイル種類 PDF */
	private static final int FILE_KIND_PDF = 1;

	/** ダウンロードファイル種類 EXCEL */
	private static final int FILE_KIND_EXCEL = 2;

	/** ダウンロードファイル種類 XML */
	private static final int FILE_KIND_XML = 3;

	/** ダウンロードファイル種類 CSV */
	private static final int FILE_KIND_CSV = 4;

	/** ダウンロードファイル種類 テキスト */
	private static final int FILE_KIND_TEXT = 5;

	/** ファイルモードで受信 */
	protected static boolean RESPONSE_FILE_MODE = false;

	/** true:アップロードしたファイルが処理完了時、削除を行なう */
	protected static boolean DELETE_FILE_WHEN_REQUEST_COMPLETED = false;

	/** システムバージョン */
	public static String version = "";

	static {
		try {
			RESPONSE_FILE_MODE = ServerConfig.isFlagOn("trans.response.file.mode");
			DELETE_FILE_WHEN_REQUEST_COMPLETED = ServerConfig
					.isFlagOn("trans.delete.upload.file.when.request.completed");
		} catch (Throwable e) {
			// 処理なし
		}
	}

	/**
	 * 所属HTTPセッション取得.<br>
	 * セッションが開始されていない場合、nullが返る.
	 * 
	 * @param req リクエスト
	 * @return セッション
	 */
	protected HttpSession getSession(HttpServletRequest req) {
		return req.getSession(false);
	}

	/**
	 * Get method 処理. <br>
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) {
		this.doPost(req, resp);
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

		this.doPost(req, resp, ServerConfig.isSessionMode());
	}

	/**
	 * Post method 処理. <br>
	 * method=postで送信されたデータを処理する。
	 * 
	 * @param req
	 * @param resp
	 * @param isSessionAuth セッション認証を行うかどうか
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp, boolean isSessionAuth) {
		long stime = System.currentTimeMillis();
		ServerLogger.info("**doPost in** : " + this.getServletName());

		try {
			// 認証状態を確認する.

			// 継続しているセッション取得.
			HttpSession httpSession = getSession(req);

			// log 出力
			ServerLogger.debug(req.getServletPath() + " " + (httpSession == null ? "null" : httpSession.getId()));

			// セッション認証
			if (isSessionAuth && !authenticateSession(httpSession)) {

				// 認証失敗時はエラー出力で終了.処理を受け付けない
				this.dispatchSystemError(req, resp, getMessage(req, "W01001"));
				return;
			}

			// アップロードファイルの処理.
			if (ServletFileUpload.isMultipartContent(req)) {

				this.handleUploadFile(req);
				this.doMethodPostMultipart(req, resp);
			} else {

				// 処理メソッドを呼び出す.
				this.doMethodPost(req, resp);
			}

		} catch (OutOfMemoryError e) {
			try {
				ServerErrorHandler.handledException(e);

				dispatchError(req, resp, "E00036");
			} catch (Exception e1) {
				ServerErrorHandler.handledException(e1);
			}

		} catch (Throwable e) {
			try {
				// ハンドラーの呼び出し
				ServerErrorHandler.handledException(e);

				// ServletExceptionで元のエラーがTExceptionだった場合
				if (e instanceof ServletException) {
					Throwable causeEx = e.getCause();
					if (causeEx != null && causeEx instanceof TException) {
						dispatchError(req, resp, (TException) causeEx);
						return;
					}
				}

				// クライアントにエラーを送信
				dispatchSystemError(req, resp, getMessage(req, "S00001"));

			} catch (Exception e1) {
				ServerErrorHandler.handledException(e);
			}

		} finally {

			if (ServletFileUpload.isMultipartContent(req)) {
				// アップロードファイル(テンポラリ)を削除
				closeUploadFile(req);
			}

			long etime = System.currentTimeMillis();
			ServerLogger.info("**doPost out** : " + this.getServletName() + " - " + (etime - stime));
		}
	}

	/**
	 * セッション認証を行う.
	 * 
	 * @param httpSession セッション
	 * @return 認証に失敗した場合false
	 */
	private boolean authenticateSession(HttpSession httpSession) {
		if (httpSession == null) {
			return false;
		}

		// 認証状態取得
		TServerCertification certification = (TServerCertification) httpSession
				.getAttribute(TServerEnv.SYS_PREFIX + "certificationification"); // 認証オブジェクトが認証状態にない.

		boolean isAuth = certification != null && certification.isCertified();

		// ユーザ情報の保持状態
		TUserInfo info = (TUserInfo) httpSession.getAttribute(TServerEnv.SYS_PREFIX + "userinfo");

		isAuth &= info != null && !Util.isNullOrEmpty(info.getUserCode());

		return isAuth;
	}

	/**
	 * オーバーライドのための、Post処理.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ServletException
	 * @throws ParseException
	 */
	protected abstract void doMethodPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, ParseException;

	/**
	 * オーバーライドのための、multipart Post処理.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ServletException
	 */
	@SuppressWarnings("unused")
	protected void doMethodPostMultipart(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// Orverrideさせる為、処理実装無し
	}

	/**
	 * Exceptionの種類を判定して処理を行う
	 * 
	 * @param ex 対象Exception
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ServletException
	 */
	protected final void handledException(Exception ex, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {

		if (!(ex instanceof TException)) {
			ServerErrorHandler.handledException(ex);
		}

		if (ex instanceof ServletException) {
			throw (ServletException) ex;

		} else {
			dispatchError(req, resp, ex);
		}
	}

	/**
	 * アップロードファイル処理 (1ファイルのみ対応)
	 * 
	 * @param req リクエスト
	 * @return 成功
	 * @throws Exception
	 */
	private boolean handleUploadFile(HttpServletRequest req) throws Exception {

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024 * 64); // このサイズ以下ならon memoryでファイルを持つ.

		String uploadPath = getServletContext().getRealPath(TServerEnv.TMP_DIR.toString());
		ServerLogger.debug("upload path : " + uploadPath);

		factory.setRepository(new File(uploadPath));

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setSizeMax(-1); // 最大サイズ -1:制限無し
		// upload.setHeaderEncoding("UTF-8"); // ヘッダの文字エンコーディング

		Map<String, List<FileItem>> map = upload.parseParameterMap(req);
		// System.out.println(map);

		Map<String, Object> para = new HashMap<String, Object>();
		List<String> fileNames = new LinkedList<String>();
		List<File> files = new LinkedList<File>();

		// // アップロードされたファイル情報をFileItemオブジェクトのリストとして取得
		// List<FileItem> objLst = upload.parseRequest(req);

		for (Entry<String, List<FileItem>> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				// パラメータ
				for (FileItem i : entry.getValue()) {
					para.put(i.getFieldName(), i.getString());
				}
			}
		}
		// // パラメータ
		// for (FileItem objFil : objLst) {
		//
		// if (objFil.isFormField()) {
		//
		// // parameter.
		// para.put(objFil.getFieldName(), objFil.getString());
		//
		// }
		// }
		req.setAttribute(TServerEnv.SYS_PREFIX + "upload.parameters", para); // パラメータ

		// リストから順にファイルデータを取り出し、「/WEB-INF/data/元のファイル名」の形式でアップロードファイルを保存
		TUserInfo info = refTServerUserInfo(req);
		String preFix = "_" + info.getCompanyCode() + "-" + info.getUserCode();

		for (Entry<String, List<FileItem>> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				// パラメータ
				for (FileItem i : entry.getValue()) {

					if (!i.isFormField()) {
						// テンポラリファイル作成.
						String fileName = StringUtil.unescapeHtml(i.getFieldName());
						String ext = "." + ResourceUtil.getExtension(fileName); // 拡張子

						File temp = File.createTempFile(fileName.replace(ext, "") + preFix, ext);
						temp.deleteOnExit();

						// ファイルを出力.
						i.write(temp);

						fileNames.add(fileName); // ファイル名
						files.add(temp); // ファイル
					}
				}
			}
		}
		req.setAttribute(TServerEnv.SYS_PREFIX + "upload.name", fileNames); // 元ファイル名
		req.setAttribute(TServerEnv.SYS_PREFIX + "upload.file", files); // アップロードファイル

		return true;
	}

	/**
	 * upload 元ファイル名取得.
	 * 
	 * @param req リクエスト
	 * @return ファイル名
	 */
	protected final String getUploadFileName(HttpServletRequest req) {

		List<String> list = getUploadFileNames(req);

		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * upload 元ファイル名リスト取得.
	 * 
	 * @param req リクエスト
	 * @return ファイル名リスト
	 */
	protected final List<String> getUploadFileNames(HttpServletRequest req) {

		return (List<String>) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.name");
	}

	/**
	 * uploadファイル取得.
	 * 
	 * @param req リクエスト
	 * @return ファイル
	 */
	protected final File getUploadFile(HttpServletRequest req) {

		List<File> list = getUploadFiles(req);

		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * uploadファイル取得.
	 * 
	 * @param req リクエスト
	 * @return ファイルリスト
	 */
	protected final List<File> getUploadFiles(HttpServletRequest req) {

		return (List<File>) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.file");
	}

	/**
	 * upload パラメータ取得.
	 * 
	 * @param req リクエスト
	 * @param key パラメータキー
	 * @return パラメータ
	 */
	protected final String getUploadParameter(HttpServletRequest req, String key) {

		Map para = (Map) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.parameters");
		if (para == null) {
			return null;
		}

		return (String) para.get(key);
	}

	/**
	 * upload パラメータ取得.
	 * 
	 * @param req リクエスト
	 * @param key パラメータキー
	 * @return パラメータ
	 */
	protected final Object getUploadObject(HttpServletRequest req, String key) {
		String objString = getUploadParameter(req, key);
		return toObjectFromBinary(objString);
	}

	/**
	 * オブジェクトバイナリ文字をオブジェクトに変換
	 * 
	 * @param objString オブジェクトバイナリ文字
	 * @return オブジェクト
	 */
	protected Object toObjectFromBinary(String objString) {

		ByteArrayInputStream input = null;
		ObjectInputStream ois = null;

		try {
			if (objString == null || Util.isNullString(objString)) {
				return null;
			}

			// デコード.
			byte[] binarry = StringUtil.toURLDecodeBytes(objString);

			input = new ByteArrayInputStream(binarry);
			ois = new ObjectInputStream(input);

			return ois.readObject();

		} catch (UnsupportedEncodingException ex) {
			throw new TRuntimeException("E00023", ex);

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);

		} catch (ClassNotFoundException ex) {
			throw new TRuntimeException("E00023", ex);

		} finally {
			ResourceUtil.closeInputStream(ois);
			ResourceUtil.closeInputStream(input);
		}
	}

	/**
	 * uploadファイル破棄.
	 * 
	 * @param req リクエスト
	 */
	protected final void closeUploadFile(HttpServletRequest req) {

		if (DELETE_FILE_WHEN_REQUEST_COMPLETED) {
			List<File> files = (List<File>) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.file");

			if (files != null) {
				for (File fil : files) {
					if (fil != null) {
						try {
							fil.delete();
						} catch (Throwable ex) {
							ServerErrorHandler.handledException(ex);
						}
					}
				}
			}
		}

		req.removeAttribute(TServerEnv.SYS_PREFIX + "upload.file");
		req.removeAttribute(TServerEnv.SYS_PREFIX + "upload.name");
	}

	/**
	 * 文字列リストパラメータ取得.<br>
	 * パラメータが無い場合、空のリストが返る.
	 * 
	 * @param req リクエスト
	 * @return パラメータリスト
	 */
	protected final List<String> getListParameter(HttpServletRequest req) {
		String listString = req.getParameter(TRequestBase.SEND_LIST_KEY);
		return Util.isNullOrEmpty(listString) ? new ArrayList<String>(0)
				: StringUtil.toListFromHTMLEscapeString(listString);
	}

	/**
	 * Dtoパラメータリスト取得. <br>
	 * パラメータが無い場合、空のリストが返る.
	 * 
	 * @param req リクエスト
	 * @param clazz 対象DtoクラスClass
	 * @return Dtoパラメータリスト
	 */
	protected final List<? extends TransferBase> getDtoListParameter(HttpServletRequest req, Class clazz) {
		return getDtoListParameter(req, clazz, TRequestBase.SEND_DTO_KEY);
	}

	/**
	 * 指定キーに対応するDtoパラメータ取得.<br>
	 * パラメータが無い場合、nullが返る.
	 * 
	 * @param req リクエスト
	 * @param clazz 対象DtoクラスClass
	 * @param key キー
	 * @return Dtoパラメータ
	 */
	protected final TransferBase getDtoParameter(HttpServletRequest req, Class clazz, String key) {
		List<TransferBase> dtoList = getDtoListParameter(req, clazz, key);
		return dtoList.isEmpty() ? null : dtoList.get(0);
	}

	/**
	 * Dtoパラメータ取得.<br>
	 * パラメータが無い場合、nullが返る.
	 * 
	 * @param req リクエスト
	 * @param clazz 対象DtoクラスClass
	 * @return Dtoパラメータ
	 */
	protected final TransferBase getDtoParameter(HttpServletRequest req, Class clazz) {
		List<? extends TransferBase> dtoList = getDtoListParameter(req, clazz);
		return dtoList.isEmpty() ? null : dtoList.get(0);
	}

	/**
	 * 指定キーに対応するDtoパラメータリスト取得. <br>
	 * パラメータが無い場合、空のリストが返る.
	 * 
	 * @param req リクエスト
	 * @param clazz 対象DtoクラスClass
	 * @param key キー
	 * @return Dtoパラメータリスト
	 */
	protected final List<TransferBase> getDtoListParameter(HttpServletRequest req, Class clazz, String key) {
		try {
			String dtoString = req.getParameter(key);

			if (Util.isNullOrEmpty(dtoString)) {
				return new ArrayList<TransferBase>(0);
			}

			String[] values = StringUtil.split(dtoString, StringUtil.LINESEPARATOR);

			List<TransferBase> list = new ArrayList<TransferBase>(values.length);

			for (String value : values) {
				List<String> dtoList = StringUtil.toListFromHTMLEscapeString(value);
				TransferBase dto = (TransferBase) clazz.newInstance();
				dto.reflectArray(dtoList);
				list.add(dto);
			}

			return list;

		} catch (InstantiationException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}
	}

	/**
	 * 指定キーに対応するObjectパラメータ取得.<br>
	 * パラメータが無い場合、nullが返る.
	 * 
	 * @param req リクエスト
	 * @param key キー
	 * @return Objectパラメータ
	 */
	protected final Object getObjectParameter(HttpServletRequest req, String key) {
		String objString = req.getParameter(key);
		return toObjectFromBinary(objString);

	}

	/**
	 * Objectパラメータ取得.<br>
	 * パラメータが無い場合、nullが返る.
	 * 
	 * @param req リクエスト
	 * @return Objectパラメータ
	 */
	protected final Object getObjectParameter(HttpServletRequest req) {
		return getObjectParameter(req, TRequestBase.SEND_OBJ_KEY);
	}

	/**
	 * エラーを通知する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param ex 対象エラー
	 */
	protected final void dispatchError(HttpServletRequest req, HttpServletResponse resp, Exception ex) {

		if (ex instanceof TException) {
			TException tex = (TException) ex;
			this.dispatchError(req, resp, tex.getMessageID(), tex.getMessageArgs());

		} else if (ex instanceof TRuntimeException) {
			TRuntimeException tex = (TRuntimeException) ex;
			this.dispatchError(req, resp, tex.getMessageID(), tex.getMessageArgs());

		} else {
			ServerErrorHandler.handledException(ex);

			this.dispatchError(req, resp, "E00009");
		}
	}

	/**
	 * エラーを通知する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param messageID メッセージID
	 * @param objects 単語ID
	 */
	protected final void dispatchError(HttpServletRequest req, HttpServletResponse resp, String messageID,
			Object... objects) {

		PrintWriter bw = null;
		try {
			// メッセージを変換.
			String message = this.getMessage(req, messageID, objects);

			// errorのレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_ERROR);

			bw.println(StringUtil
					.toDelimitString(new String[] { TRequestBase.ERR_CODE_KEY, StringUtil.escapeHtml(messageID) }));
			bw.println(StringUtil.toDelimitString(
					new String[] { TRequestBase.ERR_MSG_KEY, StringUtil.escapeHtml(Util.avoidNullNT(message)) }));

			bw.println(TRequestBase.TOKEN_ERROR_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);
			bw.flush();
			bw.close();
		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * エラーをJSPへ送信する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param messageID メッセージID
	 * @param objects 単語ID
	 */
	protected final void dispatchJspErrorForLink(HttpServletRequest req, HttpServletResponse resp, String messageID,
			Object... objects) {
		try {
			req.setAttribute("error.id", messageID);
			req.setAttribute("error.message", this.getMessage(req, messageID, objects)); // メッセージを変換.
			req.setAttribute("error.button", this.getWord(req, "C02374")); // close文字.
			req.setAttribute("error.dispButton", Boolean.FALSE);
			req.setAttribute("error.loginLink", Boolean.TRUE);
			req.setAttribute("error.goBackMessage", "back to login"); // ログイン画面へ戻る

			String errorJSP = TServerEnv.JSP_PATH.toString() + TServerEnv.ERR_JSP.toString();
			super.getServletContext().getRequestDispatcher(errorJSP).forward(req, resp);
		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * システムエラーを転送する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param message エラーメッセージ
	 */
	@SuppressWarnings("unused")
	private void dispatchSystemError(HttpServletRequest req, HttpServletResponse resp, String message) {

		PrintWriter bw = null;
		try {
			// errorのレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_ERROR);
			bw.println(StringUtil.toDelimitString(new String[] { TRequestBase.ERR_CODE_KEY, "systemerror" }));
			bw.println(StringUtil.toDelimitString(
					new String[] { TRequestBase.ERR_MSG_KEY, StringUtil.escapeHtml(Util.avoidNullNT(message)) }));
			bw.println(TRequestBase.TOKEN_ERROR_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);
			bw.flush();
			bw.close();
		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * Mapを結果として送信する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param map 結果Map
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultMap(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> map) {

		PrintWriter bw = null;
		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_KEY_VALUE_LIST);

			for (Iterator i = map.keySet().iterator(); i.hasNext();) {

				String key = (String) i.next();
				if (key != null && map.get(key) != null) {
					String strEnc;
					if (map.get(key) instanceof Double) {
						strEnc = StringUtil.escapeHtml(DecimalUtil.doubleToString((Double) map.get(key)));
					} else {
						strEnc = StringUtil.escapeHtml(Util.avoidNullNT(map.get(key)));
					}
					bw.println(StringUtil.toDelimitString(new String[] { key, strEnc }));
				}
			}

			bw.println(TRequestBase.TOKEN_KEY_VALUE_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();
			bw.close();
		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * boolean値を結果として送信する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param result boolean結果
	 */
	protected final void dispatchResult(HttpServletRequest req, HttpServletResponse resp, boolean result) {

		this.dispatchResult(req, resp, TRequestBase.RESULT_BOOLEAN, BooleanUtil.toString(result));
	}

	/**
	 * キーと値を結果として送信する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param key キー
	 * @param value 値
	 */
	protected final void dispatchResult(HttpServletRequest req, HttpServletResponse resp, String key, Object value) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		this.dispatchResultMap(req, resp, map);
	}

	/**
	 * Entityを結果として送信する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param dto 対象Entity
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultDto(HttpServletRequest req, HttpServletResponse resp, TransferBase dto) {

		PrintWriter bw = null;
		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			if (dto != null) {
				List<String> columns = dto.toStringArray();
				for (Iterator j = columns.iterator(); j.hasNext();) {
					String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
					bw.print(strEnc);
					if (j.hasNext()) {
						bw.print(StringUtil.DELIMITER);
					}
				}
				bw.println();
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * Entityのリストを結果として送信する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param list Entityリスト
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultDtoList(HttpServletRequest req, HttpServletResponse resp,
			List<TransferBase> list) {

		PrintWriter bw = null;
		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			for (TransferBase row : list) {

				if (row == null) {
					continue;
				}

				List<String> columns = row.toStringArray();
				for (Iterator j = columns.iterator(); j.hasNext();) {
					String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
					bw.print(strEnc);
					if (j.hasNext()) {
						bw.print(StringUtil.DELIMITER);
					}
				}
				bw.println();
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * dispathResultDtoListを利用すること. <br>
	 * <s>リスト-Entity形式の結果を送信する.</s>
	 * 
	 * @deprecated dispathResultDtoListを利用すること.
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param list 対象リスト
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultListObject(HttpServletRequest req, HttpServletResponse resp, List<Object> list) {

		PrintWriter bw = null;
		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			for (Object row : list) {

				if (row == null) {
					continue;
				}

				if (row instanceof TInterfaceHasToObjectArray) {
					List<Object> columns = ((TInterfaceHasToObjectArray) row).toObjectArray();
					for (Iterator j = columns.iterator(); j.hasNext();) {
						String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
						bw.print(strEnc);
						if (j.hasNext()) {
							bw.print(StringUtil.DELIMITER);
						}
					}
					bw.println();
				}
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * リスト-リスト形式の結果を送信する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param list 対象リスト
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultList(HttpServletRequest req, HttpServletResponse resp, List<List<Object>> list) {

		PrintWriter bw = null;
		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			for (List columns : list) {

				if (columns == null) {
					continue;
				}

				for (Iterator j = columns.iterator(); j.hasNext();) {
					String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
					bw.print(strEnc);
					if (j.hasNext()) {
						bw.print(StringUtil.DELIMITER);
					}
				}
				bw.println();
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * 結果としてsuccessを送信する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultSuccess(HttpServletRequest req, HttpServletResponse resp) {

		PrintWriter bw = null;
		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_SUCCESS);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * output stream にバイナリデータを送信する. <br>
	 * ファイルを文字列化して結果として送信
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param fileKind ファイル種類
	 * @param fileName ファイル名称
	 * @param bytes 対象バイナリ
	 */
	@SuppressWarnings("unused")
	private void dispatchResultBinary(HttpServletRequest req, HttpServletResponse resp, int fileKind, String fileName,
			byte[] bytes) {

		PrintWriter bw = null;

		try {
			byte[] zipBytes = ResourceUtil.zipBinary(fileName, bytes);

			// Map形式のレスポンス.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_BINARY);

			String encStr = StringUtil.toURLEncodeString(zipBytes);

			String extension = "";

			switch (fileKind) {
			case FILE_KIND_PDF:
				extension = "pdf";
				break;
			case FILE_KIND_EXCEL:
				extension = "xls";
				break;
			case FILE_KIND_XML:
				extension = "xml";
				break;
			case FILE_KIND_CSV:
				extension = "csv";
				break;
			case FILE_KIND_TEXT:
				extension = "txt";
				break;
			default:
				throw new TRuntimeException("E00003", "file kind");
			}

			// ファイル名が拡張子付きで指定されている場合(特殊)
			String[] fileNameArray = StringUtil.split(fileName, ".");
			if (fileNameArray.length != 1) {
				fileName = fileNameArray[0];
				extension = fileNameArray[1];
			}

			ServerLogger.debug("binary dispatch:" + fileName + ", " + extension + ", " + encStr.getBytes().length);

			String[] array = new String[] { StringUtil.escapeHtml(fileName), extension, encStr };
			bw.println(StringUtil.toDelimitString(array));

			bw.println(TRequestBase.TOKEN_BINARY_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * PDFファイルを送信（ダウンロード）する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param fileName ファイル名(拡張子無し)
	 * @param image バイナリ
	 */
	protected final void dispatchPDF(HttpServletRequest req, HttpServletResponse resp, String fileName, byte[] image) {

		this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_PDF, fileName, image);
	}

	/**
	 * エクセルファイルを送信（ダウンロード）する.
	 * 
	 * @param req リクエスト
	 * @param res レスポンス
	 * @param fileName ファイル名(拡張子無し)
	 * @param image バイナリ
	 */
	protected final void dispatchExcel(HttpServletRequest req, HttpServletResponse res, String fileName, byte[] image) {

		this.dispatchResultBinary(req, res, TServletBase.FILE_KIND_EXCEL, fileName, image);
	}

	/**
	 * CSVファイルを送信（ダウンロード）する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param fileName ファイル名(拡張子無し)
	 * @param image バイナリ
	 */
	protected final void dispatchCSV(HttpServletRequest req, HttpServletResponse resp, String fileName, byte[] image) {

		this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_CSV, fileName, image);
	}

	/**
	 * テキストファイルを送信（ダウンロード）する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param fileName 拡張子無し(.txt固定)
	 * @param text 送信TEXT
	 */
	protected final void dispatchTextPlain(HttpServletRequest req, HttpServletResponse resp, String fileName,
			String text) {

		this.dispatchTextPlain(req, resp, fileName, text, null);
	}

	/**
	 * テキストファイルを送信（ダウンロード）する.<br>
	 * ファイルの内容の文字コードを指定文字セットに変換する。
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param fileName 拡張子無し(.txt固定)
	 * @param text 送信TEXT
	 * @param encode encode文字セット名
	 */
	protected final void dispatchTextPlain(HttpServletRequest req, HttpServletResponse resp, String fileName,
			String text, String encode) {

		try {
			byte[] textBytes = Util.isNullOrEmpty(encode) ? text.getBytes() : text.getBytes(encode);

			this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_TEXT, fileName, textBytes);

		} catch (UnsupportedEncodingException ex) {
			throw new TRuntimeException("E00009", ex);
		}
	}

	/**
	 * CSVファイルを送信（ダウンロード）する.
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param fileName 拡張子無し(.csv固定)
	 * @param text 送信TEXT
	 */
	protected final void dispatchTextCSV(HttpServletRequest req, HttpServletResponse resp, String fileName,
			String text) {

		byte textBytes[] = text.getBytes();
		this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_CSV, fileName, textBytes);
	}

	/**
	 * オブジェクトをバイナリデータ化して送信する. <br>
	 * オブジェクト条件 : SerializableなObjectであること
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @param obj 対象オブジェクト
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultObject(HttpServletRequest req, HttpServletResponse resp, Object obj) {

		PrintWriter bw = null;
		ByteArrayOutputStream out = null;
		ObjectOutputStream oos = null;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;

		try {
			// Map形式のレスポンス.
			bw = resp.getWriter();

			if (obj != null && !(obj instanceof Throwable)) {
				System.out.println("it is Throwable.");
			}

			if (obj != null && !(obj instanceof Throwable) && RESPONSE_FILE_MODE) {

				String uid = createTempFileUID();
				String fileName = getTempFileName(uid);

				File file = createTempFile(fileName);
				fos = new FileOutputStream(file, false);
				bos = new BufferedOutputStream(fos, 8192);
				oos = new ObjectOutputStream(bos);
				oos.writeObject(obj);
				oos.flush();
				bos.flush();
				fos.flush();

				oos.close();
				bos.close();
				fos.close();

				ServerLogger.debug("object ex dispatch:" + uid + ", at " + fileName);

				bw.println(TRequestBase.TOKEN_RESPONSE);
				bw.println(TRequestBase.TOKEN_OBJECT_EX);
				bw.println(uid);
				bw.println(TRequestBase.TOKEN_OBJECT_EX_END);
				bw.println(TRequestBase.TOKEN_RESPONSE_END);

				bw.flush();

			} else {

				out = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(out);
				oos.writeObject(obj);
				oos.flush();

				byte[] image = out.toByteArray();

				bw.println(TRequestBase.TOKEN_RESPONSE);
				bw.println(TRequestBase.TOKEN_OBJECT);

				if (obj == null) {
					// nullの場合
					bw.println(Util.NULL_STRING1);

				} else {

					ServerLogger.debug("object dispatch:" + obj.getClass().getCanonicalName() + ", " + image.length);

					int base = 1000;

					for (int i = 0;; i++) {

						// 処理対象範囲
						int from = i * base;
						int to = from + base - 1;

						// List.subList(from, to)の注意事項：from:開始番号、to:終了番号の次の番号
						to = Math.min(to, image.length - 1) + 1;

						byte[] data = Arrays.copyOfRange(image, from, to);

						// オブジェクトをバイナリ化→文字列
						String encStr = StringUtil.toURLEncodeString(data);

						bw.println(encStr);
						bw.flush();

						// ループ終了条件
						if ((from + base) >= image.length) {
							break;
						}
					}
				}

				bw.println(TRequestBase.TOKEN_OBJECT_END);
				bw.println(TRequestBase.TOKEN_RESPONSE_END);

				bw.flush();
			}

		} catch (Exception ex) {
			resp.resetBuffer();
			System.out.println("----------error----------------");
			ex.printStackTrace();
			throw new TRuntimeException("E00036", ex);

		} finally {
			ResourceUtil.closeOutputStream(out);
			ResourceUtil.closeOutputStream(oos);
			ResourceUtil.closeOutputStream(bos);
			ResourceUtil.closeOutputStream(fos);

			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * @return 一時ファイルのUID作成
	 */
	protected String createTempFileUID() {
		UUID u1 = UUID.randomUUID();
		String fileToken = u1.toString();
		File file = createTempFile(getTempFileName(fileToken));
		if (!file.exists()) {
			return fileToken;
		} else {
			return createTempFileUID();
		}
	}

	/**
	 * 一時ファイル名の作成
	 * 
	 * @param fileToken
	 * @return 一時ファイル名
	 */
	public static String getTempFileName(String fileToken) {
		return SystemUtil.getTemporaryDir() + File.separator + fileToken + ".obj";
	}

	/**
	 * 一時ファイルの作成
	 * 
	 * @param filename
	 * @return 一時ファイル
	 */
	public static File createTempFile(String filename) {
		return new File(filename);
	}

	/**
	 * ログインユーザ情報を取得する.
	 * 
	 * @param req リクエスト
	 * @return ログインユーザ情報
	 */
	protected final TUserInfo refTServerUserInfo(HttpServletRequest req) {

		if (req == null) {
			throw new IllegalArgumentException("request is null.");
		}

		if (!ServerConfig.isSessionMode()) {
			// 開発モード時(セッション無し)、毎回ユーザ情報取得
			try {
				String userCode; // 会社コード
				String companyCode; // ユーザコード

				if (ServletFileUpload.isMultipartContent(req)) {
					// アップロード
					companyCode = getUploadParameter(req, "T_CC");
					userCode = getUploadParameter(req, "T_UC");
				} else {
					// 通常
					companyCode = req.getParameter("T_CC");
					userCode = req.getParameter("T_UC");
				}

				if (Util.isNullOrEmpty(companyCode) || Util.isNullOrEmpty(userCode)) {
					// 引数指定が無い場合は、モードエラー
					throw new TRuntimeException("AppletViewer MODE: Mode error or Parameter is not set");
				}

				S2Container container = SingletonS2ContainerFactory.getContainer();
				UserManager logic = (UserManager) container.getComponent("old-UserManager");
				logic.setCompanyCode(companyCode);
				logic.setUserCode(userCode);

				return logic.makeUserInfo();

			} catch (TException ex) {
				ServerErrorHandler.handledException(ex);
				return null;
			}
		}

		HttpSession session = req.getSession(false);
		if (session == null) {
			return null;
		}

		return (TUserInfo) session.getAttribute(TServerEnv.SYS_PREFIX + "userinfo");
	}

	/**
	 * 指定単語ID、または単語をバインドして、メッセージを返す.
	 * 
	 * @param req リクエスト
	 * @param messageID メッセージID
	 * @param objects 単語ID、または、単語のリスト
	 * @return 変換されたメッセージ
	 */
	protected String getMessage(HttpServletRequest req, String messageID, Object... objects) {

		return MessageUtil.convertMessage(getLanguage(req), messageID, objects);
	}

	/**
	 * 指定された単語IDに紐づく単語文字を返す.
	 * 
	 * @param req リクエスト
	 * @param wordID 単語ID
	 * @return 単語文字
	 */
	protected String getWord(HttpServletRequest req, String wordID) {

		return getWord(getLanguage(req), wordID);
	}

	/**
	 * 指定された単語IDに紐づく単語文字を返す.
	 * 
	 * @param lang 言語コード
	 * @param wordID 単語ID
	 * @return 単語文字
	 */
	protected String getWord(String lang, String wordID) {

		return MessageUtil.getWord(lang, wordID);
	}

	/**
	 * 指定された単語ID(リスト)に紐づく単語文字リスト(リスト)を返す.
	 * 
	 * @param req リクエスト
	 * @param wordIDs 単語IDリスト
	 * @return 単語文字リスト
	 */
	protected String[] getWordList(HttpServletRequest req, String[] wordIDs) {

		return MessageUtil.getWordList(getLanguage(req), wordIDs);
	}

	/**
	 * 言語コードを取得する.<br>
	 * ログイン前やユーザ情報が正確でない場合、システム言語を返す.
	 * 
	 * @param req リクエスト
	 * @return 言語コード
	 */
	protected String getLanguage(HttpServletRequest req) {

		// ユーザ情報の取得.
		TUserInfo userInfo = refTServerUserInfo(req);

		String lang = (userInfo == null) ? "" : (String) userInfo.getUserLanguage();
		if (Util.isNullOrEmpty(lang)) {
			// ログイン状態でない、ユーザ情報構築前 等の場合は、システム言語
			lang = ServerConfig.getSystemLanguageCode();
		}

		return lang;
	}

	/**
	 * コンテナを取得する
	 * 
	 * @return コンテナ
	 */
	protected TContainer getContainer() {
		return new TContainer(SingletonS2ContainerFactory.getContainer());
	}

	// 以下、不要HTTP method

	/**
	 * 非対応 HTTP method.
	 */
	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doDelete(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * 非対応 HTTP method.
	 */
	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doHead(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * 非対応 HTTP method.
	 */
	@Override
	protected final void doOptions(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doOptions(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * 非対応 HTTP method.
	 */
	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doPut(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * 非対応 HTTP method.
	 */
	@Override
	protected final void doTrace(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doTrace(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * 非対応 HTTP method.
	 */
	@Override
	protected final long getLastModified(HttpServletRequest req) {
		try {
			return super.getLastModified(req);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
		return 0L;
	}
}
