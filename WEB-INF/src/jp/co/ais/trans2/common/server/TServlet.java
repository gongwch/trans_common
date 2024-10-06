package jp.co.ais.trans2.common.server;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.fw.TContainer;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * サーブレット基盤
 * 
 * @author AIS
 */
public class TServlet extends TServletBase {

	/** ファイルサイズMAX設定値 */
	protected static int autosaveSize = 0;

	/** サーバ保存パス */
	protected static String autosaveOutputDir;

	/** true:ZIPモード */
	protected static boolean isZipMode = false;

	static {
		try {
			// (単位変換:MB→B)
			autosaveSize = Util.avoidNullAsInt(ServerConfig.getProperty("trans.auto.save.size")) * 1024 * 1024;
			autosaveOutputDir = ServerConfig.getProperty("trans.auto.save.output.dir");
		} catch (MissingResourceException e) {
			// 処理なし
		}
		try {
			// (true:ZIPモード)
			isZipMode = ServerConfig.isFlagOn("trans.response.zip.mode");
		} catch (MissingResourceException e) {
			// 処理なし
		}
	}

	/**
	 * ファイル付き
	 * 
	 * @see jp.co.ais.trans.common.server.TServletBase#doMethodPostMultipart(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doMethodPostMultipart(HttpServletRequest req, HttpServletResponse resp) {
		this.doMethodPost(req, resp);
	}

	/**
	 * Webアプリケーションの場合
	 * 
	 * @see TServletBase#doMethodPost(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {

		// イベントハンドリング
		String className = null;
		String methodName = null;
		List<Object> objectList;

		try {

			String reqVersion = Util.avoidNull(req.getParameter("systemVersion"));

			ServerLogger.debug("systemVersion:" + version);
			ServerLogger.debug("reqVersion:" + reqVersion);

			// E00041">ご利用端末のシステムバージョンとサーバのシステムバージョンが異なります。\n起動中のアプリケーションを全て閉じた後、再度ログインを行ってください。
			if (!Util.isNullOrEmpty(reqVersion)) {
				if (!"local".equals(version) && !"local".equals(reqVersion)) {
					if (!version.equals(reqVersion)) {
						super.dispatchResultObject(req, resp, new TException("E00041"));
						return;
					}
				}
			}

			// ファイルがある場合、を元の箇所に入れなおす
			List<File> fileList = getUploadFiles(req);

			if (fileList == null) {
				// 通常
				className = Util.avoidNull(req.getParameter("className"));
				methodName = Util.avoidNull(req.getParameter("methodName"));
				objectList = (List<Object>) getObjectParameter(req, "methodArg");

			} else {
				// ファイルあり(multipart送信)
				className = Util.avoidNull(getUploadParameter(req, "className"));
				methodName = Util.avoidNull(getUploadParameter(req, "methodName"));
				objectList = (List<Object>) getUploadObject(req, "methodArg");

				// List<Integer> fileIndexs = (List<Integer>) getUploadObject(req, "fileIndex");

				// ファイルをサーバサイトにコンバート
				FileTransferUtil.dispatchWebObjectList(objectList, fileList);
			}

			Class clazz = Class.forName(className);

			// 引数
			Object[] arg = objectList.toArray(new Object[objectList.size()]);

			TContainer container = TContainerFactory.getContainer();
			Object instance = container.getComponent(clazz);

			if (instance instanceof TModel) {
				TModel model = (TModel) instance;

				Company company; // 会社
				User user; // ユーザ
				String prg; // プログラムコード

				if (fileList == null) {
					// 通常
					company = (Company) getObjectParameter(req, "trans.system.company");
					user = (User) getObjectParameter(req, "trans.system.user");
					prg = Util.avoidNull(req.getParameter("programCode"));

				} else {
					// ファイルあり(multipart送信)
					company = (Company) getUploadObject(req, "trans.system.company");
					user = (User) getUploadObject(req, "trans.system.user");
					prg = Util.avoidNull(getUploadParameter(req, "programCode"));
				}

				model.setCompany(company);
				model.setUser(user);
				model.setProgramCode(prg);
				model.setNow(Util.getCurrentDate());

				String url = req.getRequestURL().toString();

				url = url.replaceAll("/", "_");
				url = url.replaceAll(":", "_");
				url = url.replaceAll("\\\\", "");

				if (Util.isNullOrEmpty(url)) {
					url = ServerConfig.getSchemaName();
				}

				model.setServerInstanceName(url);
			}

			// 戻り
			Object ret = null;

			if (arg == null || arg.length == 0) {
				// 引数なしメソッド
				Method method = clazz.getMethod(methodName);
				ret = method.invoke(instance);

			} else {
				// 引数ありメソッド
				Method method = getMethod(clazz, methodName, arg);

				if (method == null) {
					throw new TException(getMessage(req, "E00038") + clazz.getName() + "#" + methodName + "()");// 呼び出し先メソッドが見つかりません:
				}

				ret = method.invoke(instance, arg);
			}

			if (ret instanceof byte[] || ret instanceof Byte[]) {
				if (instance instanceof TModel) {
					TModel model = (TModel) instance;

					// ファイルサイズチェック
					checkFileSize((byte[]) ret, model, methodName);

				}
				// バイナリ(ファイル)の場合は圧縮
				if (isZipMode) {
					byte[] zipBytes = ResourceUtil.zipBinary("bytes", (byte[]) ret);
					super.dispatchResultObject(req, resp, zipBytes);
				} else {
					// 非ZIPモード
					super.dispatchResultObject(req, resp, ret);
				}

				return;
			}

			super.dispatchResultObject(req, resp, ret);

		} catch (TFileSizeOverException e) {

			super.dispatchResultObject(req, resp, e);

			String msg = getServletContext().getRealPath(Util.avoidNull(e.getMessageArgs()[0]));
			ServerLogger.error(getMessage(req, e.getMessageID(), msg));

		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof TException || e.getTargetException() instanceof TRuntimeException) {
				super.dispatchResultObject(req, resp, e.getTargetException());
				return;
			}

			if (e.getTargetException() instanceof OutOfMemoryError) {
				throw (OutOfMemoryError) e.getTargetException();
			}

			super.dispatchResultObject(req, resp, e);
			ServerLogger.error("className=" + className);
			ServerLogger.error("methodName=" + methodName);
			ServerErrorHandler.handledException(e);

		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				throw (OutOfMemoryError) e;
			}

			super.dispatchResultObject(req, resp, e);
			ServerLogger.error("className=" + className);
			ServerLogger.error("methodName=" + methodName);
			ServerErrorHandler.handledException(e);

		} finally {
			System.gc();
		}
	}

	/**
	 * 呼び出しメソッドの特定
	 * 
	 * @param clazz クラス
	 * @param name メソッド名
	 * @param args 引数
	 * @return メソッド
	 */
	protected Method getMethod(Class clazz, String name, Object[] args) {

		// 単純呼び出し
		try {
			Class classes[] = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				classes[i] = args[i].getClass();
			}
			return clazz.getMethod(name, classes);

		} catch (Exception ex) {
			// 次へ
		}

		// クラスにあるメソッド観点からの割り出し
		Method[] methods = clazz.getMethods();

		Map<Method, Class[]> map = new HashMap<Method, Class[]>();

		// 名前、引数の数が一致するメソッドのみピックアップ
		for (Method method : methods) {
			if (name.equals(method.getName()) && method.getParameterTypes().length == args.length) {
				map.put(method, method.getParameterTypes());
			}
		}

		if (map.isEmpty()) {
			// 該当なし
			return null;

		} else if (map.size() == 1) {
			// 該当1件なので返してしまう
			return map.keySet().toArray(new Method[1])[0];
		}

		// 引数Classの候補一覧を順に纏める
		List<List<Class>> argClassList = new ArrayList<List<Class>>(args.length);

		for (Object arg : args) {
			List<Class> classList = new LinkedList<Class>();

			Class aclass = arg.getClass();
			classList.add(aclass); // 元
			classList.add(aclass.getSuperclass()); // 親

			// interface
			for (Class iclass : aclass.getInterfaces()) {
				classList.add(iclass);
			}

			argClassList.add(classList);
		}

		// マッチ検索
		m1: for (Map.Entry<Method, Class[]> entry : map.entrySet()) {
			Class[] values = entry.getValue();

			// 引数先頭からマッチするか確認
			m2: for (int i = 0; i < values.length; i++) {
				Class value = values[i];

				// 候補リスト
				List<Class> kList = argClassList.get(i);

				for (Class kc : kList) {
					if (value.equals(kc)) {
						// 候補にあれば次の引数へ
						continue m2;
					}
				}

				// 候補になければ、次のメソッドへ
				continue m1;
			}

			// オールクリアでMethod返す
			return entry.getKey();
		}

		// 結局見つからない
		return null;
	}

	/**
	 * ファイルサイズチェック
	 * 
	 * @param data
	 * @param model モジュール
	 * @param method 関数名
	 * @throws Exception
	 */
	protected void checkFileSize(byte[] data, TModel model, String method) throws Exception {
		if (autosaveSize > 0 && data.length > autosaveSize) {
			saveFile(data, model, method);
		}
	}

	/**
	 * ファイルサーバ保存
	 * 
	 * @param data
	 * @param model モジュール
	 * @param method 関数名
	 * @throws TException
	 */
	protected void saveFile(byte[] data, TModel model, String method) throws TException {
		try {
			// 存在チェック、存在しない場合、作成
			File dir = new File(autosaveOutputDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// ファイルサイズチェック
			String filename = getSaveFileName(model, method);
			ResourceUtil.writeBinary(autosaveOutputDir + File.separator + filename, data);

			// ファイルサイズが大きいためサーバに保存しました。<改行>（保存先:{0}）
			throw new TFileSizeOverException("W01144", autosaveOutputDir + File.separator + filename);
		} catch (IOException e) {
			// ファイルの保存に失敗しました。
			throw new TException("E00032", e);
		}
	}

	/**
	 * サーバ保存パスを取得
	 * 
	 * @param model モジュール
	 * @param method 関数名
	 * @return サーバ保存パス
	 */
	protected String getSaveFileName(TModel model, String method) {

		String fileName = (model.getCompany() == null ? "" : model.getCompany().getCode()); // 会社コード
		fileName += "_" + (model.getUser() == null ? "" : model.getUser().getCode()); // ログインユーザコード
		fileName += "_" + model.getProgramCode();
		fileName += "_" + DateUtil.toYMDHMSSSSPlainString(Util.getCurrentDate()).replaceAll("\\.", ""); // タイムスタンプ

		String methodName = Util.avoidNull(method).toLowerCase();
		if (methodName.contains("excel") || methodName.contains("xls")) {
			if (TExcel.isExcelXml()) {
				fileName += ".xlsx"; // Excel 2007
			} else {
				fileName += ".xls"; // Excel 2003
			}
		} else {
			fileName += ".pdf"; // PDF
		}

		return fileName;
	}
}
