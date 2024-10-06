package jp.co.ais.trans.common.client;

import java.io.*;
import java.net.*;
import java.util.*;

import jp.co.ais.trans.common.client.http.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;

/**
 * リクエストクラス基盤
 */
public class TRequestBase {

	/** response 区切り文字START */
	public static final String TOKEN_RESPONSE = "<trans.response>";

	/** response 区切り文字END */
	public static final String TOKEN_RESPONSE_END = "</trans.response>";

	/** KEY-VALUE形式START */
	public static final String TOKEN_KEY_VALUE_LIST = "<trans.keyValueList>";

	/** KEY-VALUE形式END */
	public static final String TOKEN_KEY_VALUE_LIST_END = "</trans.keyValueList>";

	/** List形式START */
	public static final String TOKEN_RECORD_LIST = "<trans.recordList>";

	/** List形式END */
	public static final String TOKEN_RECORD_LIST_END = "</trans.recordList>";

	/** バイナリ形式START */
	public static final String TOKEN_BINARY = "<trans.binary>";

	/** バイナリ形式END */
	public static final String TOKEN_BINARY_END = "</trans.binary>";

	/** オブジェクト形式START */
	public static final String TOKEN_OBJECT = "<trans.object>";

	/** オブジェクト形式END */
	public static final String TOKEN_OBJECT_END = "</trans.object>";

	/** オブジェクトEX形式START */
	public static final String TOKEN_OBJECT_EX = "<trans.object.ex>";

	/** オブジェクトEX形式END */
	public static final String TOKEN_OBJECT_EX_END = "</trans.object.ex>";

	/** エラー形式START */
	public static final String TOKEN_ERROR = "<trans.error>";

	/** エラー形式END */
	public static final String TOKEN_ERROR_END = "</trans.error>";

	/** SUCCESS通知 */
	public static final String TOKEN_SUCCESS = "<trans.success/>";

	/** レスポンスタイプ:データなし */
	public static final int RESP_NO_DATA = 0;

	/** レスポンスタイプ:Map */
	public static final int RESP_MAP = 1;

	/** レスポンスタイプ:List */
	public static final int RESP_LIST = 2;

	/** レスポンスタイプ:バイナリ */
	public static final int RESP_BINARY = 3;

	/** レスポンスタイプ:オブジェクト */
	public static final int RESP_OBJECT = 4;

	/** レスポンスタイプ:エラー */
	public static final int RESP_ERROR = 16;

	/** レスポンスタイプ:構文エラー */
	public static final int RESP_ERROR_SYNTAX = 32;

	/** エラーコード取得時のキー */
	public static final String ERR_CODE_KEY = "code";

	/** エラーメッセージ取得時のキー */
	public static final String ERR_MSG_KEY = "notice";

	/** Dto文字を送信する際のキー */
	public static final String SEND_DTO_KEY = "trans.dtoString";

	/** LISTを送信する際のキー */
	public static final String SEND_LIST_KEY = "trans.listString";

	/** Object文字を送信する際のキー */
	public static final String SEND_OBJ_KEY = "trans.objString";

	/** Boolean結果のキー */
	public static final String RESULT_BOOLEAN = "trans.BooleanResult";

	/** バイナリデータ結果のデータ部キー */
	public static final String RESULT_BYTE_DATA = "trans.BinaryData";

	/** バイナリデータ結果のファイル名キー */
	public static final String RESULT_BYTE_FILENAME = "trans.FileName";

	/** バイナリデータ結果のファイル拡張子キー */
	public static final String RESULT_BYTE_EXTENSION = "trans.FileExtension";

	/** 接続先アドレス */
	private static String urlAddless;

	static {
		// 外部設定から接続先取得
		urlAddless = ClientConfig.getBaseAddress();

		ClientLogger.info(urlAddless);
	}

	/** HTTPリクエスト用 */
	public TAccessHTTP tAccessHTTP;

	/** 送信値リスト */
	private List<String[]> sendValues;

	/** 送信値Dtoリスト */
	private Map<String, List<TransferBase>> sendDtoValues;

	/** 結果値リスト(リスト形式) */
	private List<List<String>> resultList;

	/** 結果値リスト(key/value形式) */
	private Map<String, String> resultMap;

	/** エラー結果 */
	private Map<String, String> errorMap;

	/** バイナリデータ結果 */
	private Map<String, Object> resultBinaryDataSet;

	/** Object結果 */
	private Object resultObject;

	/** respMode */
	private int respMode = 0;

	/**
	 * コンストラクタ
	 */
	public TRequestBase() {
		tAccessHTTP = new TAccessHTTP(urlAddless);

		sendValues = new LinkedList<String[]>();
		sendDtoValues = new TreeMap<String, List<TransferBase>>();
		resultList = new LinkedList<List<String>>();
		resultMap = new TreeMap<String, String>();
		errorMap = new TreeMap<String, String>();
		resultBinaryDataSet = new TreeMap<String, Object>();
		resultObject = null;
		respMode = 0;
	}

	/**
	 * 送信
	 * 
	 * @param path 実行パス
	 * @return true:success false:error
	 * @throws IOException 送信エラー
	 */
	public boolean request(String path) throws IOException {
		return request(path, new ArrayList<File>(0));
	}

	/**
	 * 送信
	 * 
	 * @param path 実行パス
	 * @param file ファイル
	 * @return true:success false:error
	 * @throws IOException 送信エラー
	 */
	public boolean request(String path, File file) throws IOException {
		List<File> list = new ArrayList<File>(1);
		list.add(file);
		return request(path, list);
	}

	/**
	 * 送信
	 * 
	 * @param path 実行パス
	 * @param files ファイルリスト
	 * @return true:success false:error
	 * @throws IOException 送信エラー
	 */
	public boolean request(String path, List<File> files) throws IOException {

		if (ClientConfig.isFlagOn("system.type.trans1")) {
			// TRANS1バージョンのローカルテスト用
			addSendValues("T_CC", TClientLoginInfo.getInstance().getCompanyCode());
			addSendValues("T_UC", TClientLoginInfo.getInstance().getUserCode());
		}

		respMode = 0;
		resultList = new LinkedList<List<String>>();
		resultMap = new TreeMap<String, String>();
		errorMap = new TreeMap<String, String>();
		resultBinaryDataSet = new TreeMap<String, Object>();
		resultObject = null;

		InputStream stream = null;
		try {

			if (!jp.co.ais.trans2.common.config.ClientConfig.isWeb()) {
				// WEBモード以外、処理なし
				throw new TRuntimeException("E00001", "Request need web mode ON.");
			}

			// DtoリストをStringペアでsendValuesへ配置
			for (String key : sendDtoValues.keySet()) {
				List<TransferBase> sendDtoList = sendDtoValues.get(key);
				if (sendDtoList == null) {
					continue;
				}

				StringBuilder dtoStr = new StringBuilder();
				for (TransferBase row : sendDtoList) {
					if (row == null) {
						continue;
					}

					if (dtoStr.length() != 0) {
						dtoStr.append(StringUtil.LINESEPARATOR);
					}

					List<String> list = row.toStringArray();
					dtoStr.append(StringUtil.toHTMLEscapeString(list));
				}

				addSendValues(key, dtoStr.toString());
			}

			// 送信.
			if (files.isEmpty()) {
				stream = tAccessHTTP.send(path, sendValues, "");
			} else {
				stream = tAccessHTTP.sendFile(path, sendValues, "", files);
			}

			// 返信解析.
			boolean stat = this.parseResponse(stream);

			if (!stat) {
				return false;
			}

			return respMode != RESP_ERROR;

		} finally {
			// 送信値クリア
			sendValues.clear();
			sendDtoValues.clear();

			ResourceUtil.closeInputStream(stream);
		}
	}

	/**
	 * 返信解析.
	 * 
	 * @param stream
	 * @return false:形式エラー
	 * @throws IOException
	 */
	private boolean parseResponse(InputStream stream) throws IOException {

		// データの取得・結果リスト保持
		InputStreamReader rd = new InputStreamReader(stream);
		BufferedReader rb = new BufferedReader(rd);

		// 受信解析.
		String line;

		/* 最初の区切文字を探す. */
		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_RESPONSE) == 0) {
				break;
			}
		}

		/* 先頭の区切文字なし */
		if (line == null) {
			respMode = RESP_ERROR_SYNTAX;
			return false;
		}

		/* データ種の区切文字を取得 */
		line = rb.readLine();
		if (line == null) {
			respMode = RESP_ERROR_SYNTAX;
			return false;
		}

		boolean result = false;
		if (line.compareTo(TOKEN_KEY_VALUE_LIST) == 0) {
			// keyValue 形式
			result = makeMapFromStream(rb, TOKEN_KEY_VALUE_LIST_END, resultMap);
			respMode = RESP_MAP;

		} else if (line.compareTo(TOKEN_RECORD_LIST) == 0) {
			// list 形式.
			result = makeListFromStream(rb);
			respMode = RESP_LIST;

		} else if (line.compareTo(TOKEN_BINARY) == 0) {
			// バイナリ形式
			result = makeBinaryFromStream(rb);
			respMode = RESP_BINARY;

		} else if (line.compareTo(TOKEN_OBJECT) == 0) {
			// バイナリ形式
			result = makeObjectFromStream(rb);
			respMode = RESP_OBJECT;

		} else if (line.compareTo(TOKEN_OBJECT_EX) == 0) {
			// バイナリEX形式
			result = makeObjectExFromStream(rb);
			respMode = RESP_OBJECT;

		} else if (line.compareTo(TOKEN_SUCCESS) == 0) {
			// 実行のみのリクエスト.(insert,updateなどmodify系)
			result = true;
			respMode = RESP_NO_DATA;

		} else if (line.compareTo(TOKEN_ERROR) == 0) {
			// server処理でのエラー.
			result = makeMapFromStream(rb, TOKEN_ERROR_END, errorMap);
			respMode = RESP_ERROR;
		}

		// 最後の区切文字まで読飛し.
		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_RESPONSE_END) == 0) {
				break;
			}
		}

		if (!result || line == null) {
			respMode = RESP_ERROR_SYNTAX;
			return false;
		}

		// 最後まで読飛し.
		while (rb.readLine() != null) {
			/* Nothing */
		}

		return true;
	}

	/**
	 * responseのstreamから、通信規約のlist形式で読込み、Listを作成する.
	 * 
	 * @param rb 元Buffer
	 * @return false:処理失敗
	 * @throws IOException
	 */
	private boolean makeListFromStream(BufferedReader rb) throws IOException {

		String line;
		String[] columns;

		if (rb == null) {
			return false;
		}

		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_RECORD_LIST_END) == 0) {
				break;
			}
			columns = StringUtil.toArrayFromDelimitString(line);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < columns.length; i++) {
				// escapeHTMLのデコード.
				String strDec = StringUtil.unescapeHtml(columns[i]);
				list.add(strDec);
			}
			resultList.add(list);
		}

		if (line == null) {
			return false;
		}
		return true;
	}

	/**
	 * responseのstreamから、通信規約のkey:value形式で読込み、Mapを作成する.
	 * 
	 * @param rb 元Buffer
	 * @param tokenEnd END条件Token
	 * @param map 入れ込むMap
	 * @return false:処理失敗
	 * @throws IOException
	 */
	private boolean makeMapFromStream(BufferedReader rb, String tokenEnd, Map<String, String> map) throws IOException {

		String line;
		String[] keyValue;

		if (rb == null) {
			return false;
		}

		while ((line = rb.readLine()) != null) {
			if (line.compareTo(tokenEnd) == 0) {
				break;
			}

			keyValue = StringUtil.toArrayFromDelimitString(line);

			if (keyValue.length == 1) {
				map.put(keyValue[0], "");

			} else if (2 <= keyValue.length) {

				// escapeHTMLのデコード.
				String strDec = StringUtil.unescapeHtml(keyValue[1]);
				map.put(keyValue[0], strDec);
			} else {
				throw new TRuntimeException("E00009", "C02768");
			}
		}

		if (line == null) {
			return false;
		}

		return true;
	}

	/**
	 * responseのstreamから、バイナリ形式を読込む.
	 * 
	 * @param rb 元Buffer
	 * @return false:処理失敗
	 * @throws IOException
	 */
	private boolean makeBinaryFromStream(BufferedReader rb) throws IOException {

		String line;

		if (rb == null) {
			return false;
		}

		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_BINARY_END) == 0) {
				break;
			}

			String[] splits = StringUtil.toArrayFromDelimitString(line);
			if (splits.length != 3) {
				throw new TRuntimeException("E00009", "C02768");
			}

			resultBinaryDataSet.put(RESULT_BYTE_FILENAME, StringUtil.unescapeHtml(splits[0]));
			resultBinaryDataSet.put(RESULT_BYTE_EXTENSION, splits[1]);

			// デコード.
			resultBinaryDataSet.put(RESULT_BYTE_DATA, StringUtil.toURLDecodeBytes(splits[2]));
		}

		if (line == null) {
			return false;
		}

		return true;
	}

	/**
	 * responseのstreamから、バイナリ形式を読込む.
	 * 
	 * @param rb 元Buffer
	 * @return false:処理失敗
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean makeObjectFromStream(BufferedReader rb) throws UnsupportedEncodingException, IOException {

		ByteArrayOutputStream output = null;
		ByteArrayInputStream input = null;
		ObjectInputStream ois = null;

		try {
			String line;
			if (rb == null) {
				return false;
			}

			boolean hasData = false;
			output = new ByteArrayOutputStream();

			while ((line = rb.readLine()) != null) {
				if (line.compareTo(TOKEN_OBJECT_END) == 0) {
					break;
				}

				if (Util.NULL_STRING1.equals(line)) {
					// nullの場合
					resultObject = null;

				} else {
					// デコード.
					byte[] binarry = StringUtil.toURLDecodeBytes(line);
					output.write(binarry);
					output.flush();

					hasData = true;
				}
			}

			if (hasData) {
				input = new ByteArrayInputStream(output.toByteArray());
				ois = new ObjectInputStream(input);
				resultObject = ois.readObject();
			}

			if (line == null) {
				return false;
			}

			return true;

		} catch (ClassNotFoundException ex) {
			throw new IOException(ex.getMessage());

		} finally {
			ResourceUtil.closeOutputStream(output);
			ResourceUtil.closeInputStream(ois);
			ResourceUtil.closeInputStream(input);
		}
	}

	/**
	 * responseのstreamから、バイナリ形式を読込む.
	 * 
	 * @param rb 元Buffer
	 * @return false:処理失敗
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean makeObjectExFromStream(BufferedReader rb) throws UnsupportedEncodingException, IOException {

		ByteArrayOutputStream output = null;
		String fileToken = "";

		try {
			String line;
			if (rb == null) {
				return false;
			}

			output = new ByteArrayOutputStream();

			while ((line = rb.readLine()) != null) {
				if (line.compareTo(TOKEN_OBJECT_EX_END) == 0) {
					break;
				}

				if (Util.NULL_STRING1.equals(line)) {
					// nullの場合
					fileToken = null;
					break;

				} else {
					// デコード.
					fileToken = line;
					break;
				}
			}

			if (fileToken == null) {
				return false;
			}

		} finally {
			ResourceUtil.closeOutputStream(output);
		}

		if (Util.isNullOrEmpty(fileToken)) {
			return false;
		} else {

			// ファイルあり
			// ダウンロード処理
			URL url = new URL(urlAddless + "/TDownloadServlet?token=" + fileToken);
			String fileName = getTempFileName("client_" + fileToken);
			File file = createTempFile(fileName);
			FileUtil.copyURLToFile(url, file);

			try {
				resultObject = FileUtil.getObject(fileName);

				FileUtil.removeFile(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
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
	 * 送信するパラメータの追加
	 * 
	 * @param name パラメータ名
	 * @param param パラメータ
	 */
	public void addSendValues(String name, String param) {
		String[] pair = { name, param };
		sendValues.add(pair);
	}

	/**
	 * 送信するパラメータの追加
	 * 
	 * @param paramMap パラメータ
	 */
	public void addSendValues(Map<String, Object> paramMap) {
		for (Iterator ite = paramMap.keySet().iterator(); ite.hasNext();) {
			String key = (String) ite.next();
			addSendValues(key, Util.avoidNullNT(paramMap.get(key)));
		}
	}

	/**
	 * 送信するObjectパラメータの追加.<br>
	 * システムのデフォルトキーで設定.<br>
	 * Objectパラメータ専用メソッドで受け取る必要がある.
	 * 
	 * @param obj Objectパラメータ
	 */
	public void addSendObject(Object obj) {
		addSendObject(SEND_OBJ_KEY, obj);
	}

	/**
	 * 送信するObjectパラメータの追加.<br>
	 * Objectパラメータ専用メソッドで受け取る必要がある.
	 * 
	 * @param key キー
	 * @param obj Objectパラメータ
	 */
	public void addSendObject(String key, Object obj) {

		ByteArrayOutputStream out = null;
		ObjectOutputStream oos = null;

		try {

			String value;

			if (obj == null) {
				value = Util.NULL_STRING1;

			} else {

				double startMemory = 0d;
				double endMemory = 0d;
				double minMemory = 1d; // メッセージ表示最小値
				if (ClientConfig.isFlagOn("trans.show.used.request.memory")) {
					startMemory = Runtime.getRuntime().freeMemory() / 1024d / 1024d;
					try {
						minMemory = Double.parseDouble(ClientConfig
							.getProperty("trans.show.used.request.memory.min.MB"));
					} catch (Throwable e) {
						// 処理なし
					}
				}

				out = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(out);
				oos.writeObject(obj);
				oos.flush();

				byte[] image = out.toByteArray();

				// オブジェクトをバイナリ化→文字列
				value = StringUtil.toURLEncodeString(image);

				if (ClientConfig.isFlagOn("trans.show.used.request.memory")) {
					endMemory = Runtime.getRuntime().freeMemory() / 1024d / 1024d;
				}

				if (startMemory - endMemory > minMemory) {
					// メモリ使用量＞１MB(指定MIN値)の場合
					String num = NumberFormatUtil.formatNumber(startMemory - endMemory, 2) + "MB";
					System.out.println("More than " + num + " of request memory used.");
				}

			}

			String[] pair = { key, value };
			sendValues.add(pair);

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);

		} finally {
			ResourceUtil.closeOutputStream(out);
			ResourceUtil.closeOutputStream(oos);
		}
	}

	/**
	 * 文字リストパラメータの設定
	 * 
	 * @param list
	 */
	public void addSendList(List<String> list) {
		addSendValues(SEND_LIST_KEY, StringUtil.toHTMLEscapeString(list));
	}

	/**
	 * 送信するDtoパラメータの追加.<br>
	 * システムのデフォルトキーで設定.<br>
	 * Dtoパラメータ専用メソッドで受け取る必要がある.
	 * 
	 * @param dto Dtoパラメータ
	 */
	public void addSendDto(TransferBase dto) {
		List<TransferBase> tbList = sendDtoValues.get(SEND_DTO_KEY);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(SEND_DTO_KEY, tbList);
		}

		tbList.add(dto);
	}

	/**
	 * 送信するDtoパラメータの追加.<br>
	 * Dtoパラメータ専用メソッドで受け取る必要がある.
	 * 
	 * @param key キー
	 * @param dto Dtoパラメータ
	 */
	public void addSendDto(String key, TransferBase dto) {
		List<TransferBase> tbList = sendDtoValues.get(key);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(key, tbList);
		}

		tbList.add(dto);
	}

	/**
	 * 送信するDtoパラメータリストの追加.<br>
	 * システムのデフォルトキーで設定.<br>
	 * Dtoパラメータ専用メソッドで受け取る必要がある.
	 * 
	 * @param list Dtoパラメータリスト
	 */
	public void addSendDtoList(List<? extends TransferBase> list) {
		List<TransferBase> tbList = sendDtoValues.get(SEND_DTO_KEY);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(SEND_DTO_KEY, tbList);
		}

		tbList.addAll(list);
	}

	/**
	 * 送信するDtoパラメータリストの追加.<br>
	 * Dtoパラメータ専用メソッドで受け取る必要がある.
	 * 
	 * @param key キー
	 * @param list Dtoパラメータリスト
	 */
	public void addSendDtoList(String key, List<? extends TransferBase> list) {
		List<TransferBase> tbList = sendDtoValues.get(key);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(key, tbList);
		}

		tbList.addAll(list);
	}

	/**
	 * 送信するパラメータのクリア
	 */
	public void clearSendValues() {
		sendValues.clear();
		sendDtoValues.clear();
	}

	/**
	 * 結果(レスポンス)の状態
	 * 
	 * @return 状態
	 */
	public int getRespMode() {
		return this.respMode;
	}

	/**
	 * 結果(レスポンス)がシステムエラーであるかどうか
	 * 
	 * @return true: システムエラー
	 */
	public boolean isErrorResponse() {

		if (this.respMode == RESP_ERROR) {
			if (this.errorMap == null) {
				return false;
			}
			if (Util.isNullOrEmpty(this.errorMap.get("code"))) {
				return false;
			}

			if (this.errorMap.get("code").equals("systemerror")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 結果(レスポンス)がMap形式であるかどうか
	 * 
	 * @return true: Map形式
	 */
	public boolean isMapResponse() {
		return this.respMode == RESP_MAP;
	}

	/**
	 * 結果(レスポンス)がList形式であるかどうか
	 * 
	 * @return true: List形式
	 */
	public boolean isListResponse() {
		return this.respMode == RESP_LIST;
	}

	/**
	 * 結果(レスポンス)がバイナリ形式であるかどうか
	 * 
	 * @return true: バイナリ形式
	 */
	public boolean isBinaryResponse() {
		return this.respMode == RESP_BINARY;
	}

	/**
	 * 結果(レスポンス)がオブジェクト形式であるかどうか
	 * 
	 * @return true: Object形式
	 */
	public boolean isObjectResponse() {
		return this.respMode == RESP_OBJECT;
	}

	/**
	 * 結果(レスポンス)がデータ無し状態であるかどうか
	 * 
	 * @return true: データ無し状態
	 */
	public boolean isNoDataResponse() {
		return this.respMode == RESP_NO_DATA;
	}

	/**
	 * 結果(レスポンス)の取得
	 * 
	 * @param key キー
	 * @return 結果(レスポンス)値
	 */
	public String getResultValue(String key) {
		return resultMap.get(key);
	}

	/**
	 * key/value形式結果(レスポンス)の取得
	 * 
	 * @return 結果(レスポンス)値
	 */
	public Map<String, String> getResult() {
		return resultMap;
	}

	/**
	 * boolean形式結果(レスポンス)の取得
	 * 
	 * @return 結果(レスポンス)boolean値
	 */
	public boolean getResultBoolean() {
		String res = resultMap.get(RESULT_BOOLEAN);

		if (res == null) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}

		return BooleanUtil.toBoolean(res);
	}

	/**
	 * リスト形式結果(レスポンス)の取得
	 * 
	 * @return 結果(レスポンス)値
	 */
	public List<List<String>> getResultList() {
		return resultList;
	}

	/**
	 * バイナリデータ結果（システム用）
	 * 
	 * @return バイナリデータセット
	 */
	public Map getResultBinaryDataSet() {
		return resultBinaryDataSet;
	}

	/**
	 * オブジェクト結果の取得
	 * 
	 * @return オブジェクト
	 */
	public Object getResultObject() {
		return resultObject;
	}

	/**
	 * エラー結果がある場合、エラーコードを返す.<br>
	 * 極力TappletClientBase.errorHandler()を利用すること.
	 * 
	 * @return エラーコード
	 */
	public String getErrorCode() {
		return errorMap.get(ERR_CODE_KEY);
	}

	/**
	 * エラー結果がある場合、エラーメッセージを返す. <br>
	 * 極力TappletClientBase.errorHandler()を利用すること.
	 * 
	 * @return エラーメッセージ
	 */
	public String getErrorMessage() {
		return errorMap.get(ERR_MSG_KEY);
	}

	/**
	 * サーバ通信のリスト結果をEntityの形で取得する.
	 * 
	 * @param clazz TransferBaseを継承したEntityのクラス定義
	 * @return 結果のEntityクラス.結果が無い場合は、nullが返る.
	 */
	public TransferBase getResultDto(Class clazz) {
		try {
			if (getRespMode() != RESP_LIST) {
				throw new TRuntimeException("E00003", "Coding Miss");
			}

			List<List<String>> resultList_ = getResultList();

			if (resultList_.isEmpty()) {
				return null;
			}

			TransferBase base = (TransferBase) clazz.newInstance();
			base.reflectArray(resultList_.get(0));

			return base;

		} catch (InstantiationException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}
	}

	/**
	 * サーバ通信のリスト結果をEntityリストの形で取得する.
	 * 
	 * @param clazz TransferBaseを継承したEntityのクラス定義
	 * @return 結果のEntityクラスリスト.結果が無い場合は、空リストが返る.
	 */
	public List getResultDtoList(Class clazz) {
		try {
			if (getRespMode() != RESP_LIST) {
				throw new TRuntimeException("E00003", "Coding Miss");
			}

			List<List<String>> resultList_ = getResultList();
			List<TransferBase> returnList = new ArrayList<TransferBase>(resultList_.size());

			for (List<String> list : resultList_) {
				TransferBase base = (TransferBase) clazz.newInstance();
				base.reflectArray(list);

				returnList.add(base);
			}

			return returnList;

		} catch (InstantiationException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}
	}

	/**
	 * HTTPポートを閉じる
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		tAccessHTTP.releasePort();
	}

	/**
	 * 送信
	 * 
	 * @param path 実行パス
	 * @throws TException 送信エラー
	 */
	public void send(String path) throws TException {
		send(path, new ArrayList<File>(0));
	}

	/**
	 * 送信
	 * 
	 * @param path 実行パス
	 * @param files ファイルリスト
	 * @return true:success false:error
	 * @throws TException 送信エラー
	 */
	public boolean send(String path, List<File> files) throws TException {

		if (Util.isNullOrEmpty(TClientLoginInfo.getSessionID())) {
			addSendValues("T_CC", TClientLoginInfo.getInstance().getCompanyCode());
			addSendValues("T_UC", TClientLoginInfo.getInstance().getUserCode());
		}

		respMode = 0;
		resultList = new LinkedList<List<String>>();
		resultMap = new TreeMap<String, String>();
		errorMap = new TreeMap<String, String>();
		resultBinaryDataSet = new TreeMap<String, Object>();
		resultObject = null;

		InputStream stream = null;

		try {

			// DtoリストをStringペアでsendValuesへ配置
			for (String key : sendDtoValues.keySet()) {

				List<TransferBase> sendDtoList = sendDtoValues.get(key);
				if (sendDtoList == null) {
					continue;
				}

				StringBuilder dtoStr = new StringBuilder();
				for (TransferBase row : sendDtoList) {
					if (row == null) {
						continue;
					}

					if (dtoStr.length() != 0) {
						dtoStr.append(StringUtil.LINESEPARATOR);
					}

					List<String> list = row.toStringArray();
					dtoStr.append(StringUtil.toHTMLEscapeString(list));
				}

				addSendValues(key, dtoStr.toString());
			}

			// 送信.
			if (files.isEmpty()) {
				stream = tAccessHTTP.send(path, sendValues, "");
			} else {
				stream = tAccessHTTP.sendFile(path, sendValues, "", files);
			}

			// 返信解析.
			boolean stat = this.parseResponse(stream);

			if (!stat) {
				return false;
			}

			return respMode != RESP_ERROR;

		} catch (Exception e) {
			throw new TException(e, "E00037");
		} finally {
			// 送信値クリア
			sendValues.clear();
			sendDtoValues.clear();

			ResourceUtil.closeInputStream(stream);
		}
	}

}