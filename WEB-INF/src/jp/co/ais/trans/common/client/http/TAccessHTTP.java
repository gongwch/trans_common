package jp.co.ais.trans.common.client.http;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.*;

import org.apache.hc.client5.http.config.*;
import org.apache.hc.client5.http.entity.*;
import org.apache.hc.client5.http.entity.mime.*;
import org.apache.hc.client5.http.impl.classic.*;
import org.apache.hc.client5.http.impl.io.*;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.support.*;
import org.apache.hc.core5.http.message.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * HTTPアクセス HTTP通信方法、タイムアウトなどの設定を行う.
 */
public class TAccessHTTP {

	/** プロキシホスト */
	private static String proxyHost;

	/** プロキシポート */
	private static String proxyPort;

	/** 接続先URL */
	private String host;

	/** HTTPクライアント */
	private CloseableHttpClient client;

	/** Httpメソッド */
	private ClassicHttpResponse response;

	/**
	 * Defines the charset to be used for encoding content body.
	 * <p>
	 * This parameter expects a value of type {@link String}.
	 * </p>
	 */
	public static final String HTTP_CONTENT_CHARSET = "http.protocol.content-charset";

	/**
	 * Defines {@link CookiePolicy cookie policy} to be used for cookie management.
	 * <p>
	 * This parameter expects a value of type {@link String}.
	 * </p>
	 */
	public static final String COOKIE_POLICY = "http.protocol.cookie-policy";

	/**
	 * The policy that provides high degree of compatibilty with common cookie management of popular HTTP agents.
	 * 
	 * @since 3.0
	 */
	public static final String BROWSER_COMPATIBILITY = "compatibility";

	static {
		try {
			// プロキシ設定を個別で行う場合に利用.
			proxyHost = System.getProperty("deployment.proxy.http.host");
			proxyPort = System.getProperty("deployment.proxy.http.port");

			if (Util.isNullOrEmpty(proxyPort)) {
				proxyPort = "80";
			}

		} catch (AccessControlException ex) {
			ClientLogger.info("ProxySettingError:" + ex.getMessage());
		}
	}

	/**
	 * コンストラクタ
	 * 
	 * @param host 接続先URL
	 */
	public TAccessHTTP(String host) {
		this.host = host;

		// client = HttpClients.createDefault();

		// FIXME: PROXY
		// try {
		// if (!Util.isNullOrEmpty(proxyHost)) {
		// HostConfiguration hostConfig = client.getHostConfiguration();
		// hostConfig.setProxy(proxyHost, Integer.parseInt(proxyPort));
		//
		// ClientLogger.info("SetProxy:" + proxyHost + ":" + proxyPort);
		// }
		// } catch (AccessControlException ex) {
		// ClientLogger.error("ProxySettingError:" + ex.getMessage());
		// }
	}

	/**
	 * 送信を実行する.
	 * 
	 * @param path パス
	 * @param pairs パラメータ
	 * @return 結果Responseのストリーム
	 * @throws IOException
	 */
	public InputStream send(String path, List pairs) throws IOException {
		return send(path, pairs, "");
	}

	/**
	 * 送信を実行する.
	 * 
	 * @param path パス
	 * @param pairs パラメータ
	 * @param refer リクエストヘッダ
	 * @return 結果Responseのストリーム
	 * @throws IOException 通信エラー
	 */
	public InputStream send(String path, List<String[]> pairs, String refer) throws IOException {

		try {

			List<NameValuePair> requestData = new ArrayList<NameValuePair>();

			// List<NameValuePair> requestData = Arrays.asList(new BasicNameValuePair("name1", "value1"), new
			// BasicNameValuePair("name2", "value2"));

			// method.getParams().setContentCharset(StringUtil.CHARSET_KIND_UTF8);
			// method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			// method.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			// method.getParams().setParameter( HttpMethodParams.HTTP_ELEMENT_CHARSET, "UTF-8");

			// List<NameValuePair> requestData = new ArrayList<NameValuePair>();
			//
			// HttpEntity requestEntity = HttpEntities.createUrlEncoded(pairs, StringUtil.CHARSET_KIND_UTF8);
			//
			// httpPost.setEntity(requestEntity);

			// Httpメソッドパラメータを設定.
			// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3,
			// false));
			// method.setEntity(null);

			// method.setRequestHeader("Refer", host + refer);

			// "charset=UTF-8"は必ず必要
			// method.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=" +
			// StringUtil.CHARSET_KIND_UTF8);

			// Cookie設定
			// method.setRequestHeader("Cookie", "JSESSIONID=" + TClientLoginInfo.getSessionID());

			// parameter
			if (pairs != null) {
				for (String[] params : pairs) {
					BasicNameValuePair pair = new BasicNameValuePair(params[0], Util.avoidNullNT(params[1]));
					requestData.add(pair);
				}
			}

			// if(objMap!=null) {
			// for(Entry<String, Serializable> entry:objMap.entrySet()) {
			// String key = entry.getKey();
			// String value = URLEncoder..
			// BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),entry.getValue());
			// requestData.add(pair);
			// }
			// }

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(requestData);
			ClassicHttpRequest method = ClassicRequestBuilder.post(host + path) //
					.setCharset(Charset.forName(StringUtil.CHARSET_KIND_UTF8)) //
					.setEntity(entity) //
					.build();

			method.addHeader(HTTP_CONTENT_CHARSET, StringUtil.CHARSET_KIND_UTF8);
			method.addHeader(COOKIE_POLICY, BROWSER_COMPATIBILITY);
			method.addHeader("Refer", host + refer);
			method.addHeader("content-type",
					"application/x-www-form-urlencoded; charset=" + StringUtil.CHARSET_KIND_UTF8);
			method.addHeader("Cookie", "JSESSIONID=" + TClientLoginInfo.getSessionID());

			// Httpメソッド実行.
			// int statusCode = client.executeMethod(method);

			ConnectionConfig connConfig = ConnectionConfig.custom() //
					.setConnectTimeout(24, TimeUnit.HOURS) //
					.setSocketTimeout(24, TimeUnit.HOURS) //
					.build();
			RequestConfig requestConfig = RequestConfig.custom() //
					.setResponseTimeout(24, TimeUnit.HOURS) //
					.setConnectionRequestTimeout(24, TimeUnit.HOURS) //
					.build();
			BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
			cm.setConnectionConfig(connConfig);

			client = HttpClientBuilder.create() //
					.setDefaultRequestConfig(requestConfig) //
					.setConnectionManager(cm) //
					.build();

			response = client.execute(method);

			int statusCode = response.getCode();

			ClientLogger.debug("send: " + host + path + " - " + statusCode);

			if (statusCode == 500 || statusCode == 404) {
				ClientLogger.error("  error path = " + method.getPath());
			}

			// 回答のヘッダ取得.
			// Header[] headers = method.getResponseHeaders(); for (int i = 0; i < headers.length; i++)
			// {
			// ClientLogger.debug("headers[" + i + "] = " + headers[i]); }

			// 回答の本文取得.
			InputStream st = response.getEntity().getContent();

			// 回答のフッタ取得.
			// Header[] footers = method.getResponseFooters();
			// for (int i = 0; i < footers.length; i++) { ClientLogger.debug("footers[" + i + "] = " +
			// footers[i]); }

			return st;

		} catch (IOException ex) {
			ClientErrorHandler.handledException(ex);
			throw ex;

		} catch (Throwable ex) {
			ClientErrorHandler.handledException(ex);
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 送信を実行する.
	 * 
	 * @param path パス
	 * @param pairs パラメータ
	 * @param refer リクエストヘッダ
	 * @param files ファイルリスト
	 * @return 結果Responseのストリーム
	 * @throws IOException 通信エラー
	 */
	public InputStream sendFile(String path, List<String[]> pairs, String refer, List<File> files) throws IOException {

		try {

			// List<NameValuePair> requestData = new ArrayList<NameValuePair>();

			// List<NameValuePair> requestData = Arrays.asList(new BasicNameValuePair("name1", "value1"), new
			// BasicNameValuePair("name2", "value2"));

			// method.getParams().setContentCharset(StringUtil.CHARSET_KIND_UTF8);
			// method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			// method.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			// method.getParams().setParameter( HttpMethodParams.HTTP_ELEMENT_CHARSET, "UTF-8");

			// List<NameValuePair> requestData = new ArrayList<NameValuePair>();
			//
			// HttpEntity requestEntity = HttpEntities.createUrlEncoded(pairs, StringUtil.CHARSET_KIND_UTF8);
			//
			// httpPost.setEntity(requestEntity);

			// Httpメソッドパラメータを設定.
			// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3,
			// false));
			// method.setEntity(null);

			// method.setRequestHeader("Refer", host + refer);

			// "charset=UTF-8"は必ず必要
			// method.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=" +
			// StringUtil.CHARSET_KIND_UTF8);

			// Cookie設定
			// method.setRequestHeader("Cookie", "JSESSIONID=" + TClientLoginInfo.getSessionID());

			final String utf8 = StringUtil.CHARSET_KIND_UTF8;

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			builder.setMode(HttpMultipartMode.EXTENDED);
			builder.setContentType(ContentType.MULTIPART_MIXED);

			// parameter
			if (pairs != null) {
				for (String[] params : pairs) {

					// 1
					// BasicNameValuePair pair = new BasicNameValuePair(params[0], Util.avoidNullNT(params[1]));
					// builder.addParameter(pair);

					// // 2
					// StringBody stringBody = new StringBody(Util.avoidNullNT(params[1]), ContentType.MULTIPART_FORM_DATA);
					// builder.addPart(params[0], stringBody);

					// 3
					builder.addTextBody(params[0], Util.avoidNullNT(params[1]), ContentType.DEFAULT_TEXT);
				}
			}

			// if(objMap!=null) {
			// for(Entry<String, Serializable> entry:objMap.entrySet()) {
			// String key = entry.getKey();
			// String value = URLEncoder..
			// BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(),entry.getValue());
			// requestData.add(pair);
			// }
			// }

			if (files != null) {
				// MultipartPartBuilder mppb = MultipartPartBuilder.create();

				for (File file : files) {
					// 1
					// FilePart part = new FilePart(file.getName(), file);
					// mpeBuilder.addPart(part);
					// mpeBuilder.addBinaryBody("files", file);

					// 2
					// FileBody body = new FileBody(file);
					// mppb.setBody(body);
					//
					// MultipartPart mpp = mppb.build();
					// mpeBuilder.addPart(mpp);

					// // 3
					// FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
					// builder.addPart("file", fileBody);
					//
					// fileName = file.getName();

					// 4
					builder.addBinaryBody("file", file);
				}
			}

			HttpEntity entity = builder.build();

			// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(requestData);
			ClassicHttpRequest method = ClassicRequestBuilder.post(host + path).setEntity(entity).build();

			// method.addHeader(HTTP_CONTENT_CHARSET, StringUtil.CHARSET_KIND_UTF8);
			// method.addHeader(COOKIE_POLICY, BROWSER_COMPATIBILITY);
			// method.addHeader("Content-disposition", fileName);
			// method.addHeader("Refer", host + refer);
			// method.addHeader("content-type", "multipart/form-data; boundary=" + TClientLoginInfo.getSessionID() + ";charset=" + StringUtil.CHARSET_KIND_UTF8);
			method.addHeader("Cookie", "JSESSIONID=" + TClientLoginInfo.getSessionID());
			method.addHeader(HTTP_CONTENT_CHARSET, StringUtil.CHARSET_KIND_UTF8);
			method.addHeader("charset", StringUtil.CHARSET_KIND_UTF8);

			// Httpメソッド実行.
			// int statusCode = client.executeMethod(method);

			// HttpPost post = new HttpPost(host + path);
			// post.setEntity(entity);
			client = HttpClients.createDefault();
			response = client.execute(method);

			int statusCode = response.getCode();

			ClientLogger.debug("send: " + host + path + " - " + statusCode);

			if (statusCode == 500 || statusCode == 404) {
				ClientLogger.error("  error path = " + method.getPath());
			}

			// 回答のヘッダ取得.
			// Header[] headers = method.getResponseHeaders(); for (int i = 0; i < headers.length; i++)
			// {
			// ClientLogger.debug("headers[" + i + "] = " + headers[i]); }

			// 回答の本文取得.
			InputStream st = response.getEntity().getContent();

			// 回答のフッタ取得.
			// Header[] footers = method.getResponseFooters();
			// for (int i = 0; i < footers.length; i++) { ClientLogger.debug("footers[" + i + "] = " +
			// footers[i]); }

			return st;

		} catch (IOException ex) {
			ClientErrorHandler.handledException(ex);
			throw ex;

		} catch (Throwable ex) {
			ClientErrorHandler.handledException(ex);
			throw new TRuntimeException(ex);
		}

		// try {
		// method = new PostMethod(host + path);
		//
		// method.getParams().setContentCharset(StringUtil.CHARSET_KIND_UTF8);
		// method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		//
		// // Httpメソッドパラメータを設定.
		// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		//
		// method.setRequestHeader("Refer", host + refer);
		// // method.setRequestHeader("content-type", "multipart/form-data; charset=UTF-8");
		//
		// // Cookie設定
		// method.setRequestHeader("Cookie", "JSESSIONID=" + TClientLoginInfo.getSessionID());
		//
		// // Partリスト
		// List<Part> partList = new ArrayList<Part>(pairs.size() + 1);
		//
		// // パラメーターは、文字列Partとして送信
		// if (pairs != null) {
		// Iterator ite = pairs.iterator();
		// for (int i = 0; ite.hasNext(); i++) {
		// String[] params = (String[]) ite.next();
		// partList.add(new StringPart(params[0], Util.avoidNullNT(params[1]), StringUtil.CHARSET_KIND_UTF8));
		// }
		// }
		//
		// // ファイルの指定
		// for (File file : files) {
		// partList.add(new FilePart(StringUtil.escapeHtml(file.getName()), file));
		// }
		//
		// method.setRequestEntity(new MultipartRequestEntity(partList.toArray(new Part[partList.size()]),
		// method.getParams()));
		//
		// // Httpメソッド実行.
		// int statusCode = client.executeMethod(method);
		//
		// ClientLogger.debug("file send: " + host + path + " - " + statusCode);
		//
		// if (statusCode == 500 || statusCode == 404) {
		// ClientLogger.error(" error path = " + method.getPath());
		// }
		//
		// // 回答の本文取得.
		// return method.getResponseBodyAsStream();
		//
		// } catch (IOException ex) {
		// ClientErrorHandler.handledException(ex);
		// throw ex;
		//
		// } catch (Throwable ex) {
		// ClientErrorHandler.handledException(ex);
		// throw new TRuntimeException(ex);
		// }
	}

	/**
	 * HTTPメソッドポートのリリース
	 */
	public void releasePort() {

		if (response != null) {
			// method.releaseConnection();

			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (client != null) {

			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
