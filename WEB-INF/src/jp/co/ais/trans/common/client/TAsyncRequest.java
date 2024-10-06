package jp.co.ais.trans.common.client;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;

/**
 * 非同期用リクエスター
 */
public class TAsyncRequest extends TAppletClientBase {

	/** 元コントローラー */
	private TAppletClientBase ctrl;

	/** マルチスレッドブロック用のダミーインスタンス */
	private String dummy = "";

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl 元コントローラー
	 */
	public TAsyncRequest(TAppletClientBase ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * 画面取得（override用）<br>
	 * コントロールする画面Panel or Dialogを返す.<br>
	 * ※元画面のコントローラに差し替え
	 * 
	 * @return 元コントローラーの画面
	 */
	@Override
	public Container getView() {
		return ctrl.getView();
	}

	/**
	 * 通信結果のエラー値用エラーハンドリング. <br>
	 * getView()を親コンポーネントとして扱う.<br>
	 * ※可視性変更
	 */
	@Override
	public void errorHandler() {
		super.errorHandler();
	}

	/**
	 * 送信(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param path 実行パス
	 * @param listener リクエスト後の処理リスナー
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void requestAsync(final String path, final TAsyncListener listener) {
		requestAsync(path, Collections.EMPTY_LIST, listener);
	}

	/**
	 * 送信(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param path 実行パス
	 * @param files ファイルリスト
	 * @param listener リクエスト後の処理リスナー
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void requestAsync(final String path, final List<File> files, final TAsyncListener listener) {

		try {
			synchronized (dummy) {

				ctrl.locked(true);

				new Thread() {

					public void run() {
						try {
							final boolean result = request(path, files);

							ctrl.locked(false);

							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									if (listener != null) {
										listener.after(result);
									}
								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (Exception e) {
							ctrl.locked(false);
							ctrl.errorHandler(e);

						} finally {
							ctrl.locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			ctrl.locked(false);

			throw ex;
		}
	}

	/**
	 * ファイルダウンロード＆表示(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void downloadAsync(final Container cont, final String servletName) {
		this.downloadAsync(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルダウンロード＆表示(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void downloadAsync(final Container cont, final String servletName, final Map funcArgs) {

		try {
			synchronized (dummy) {

				ctrl.locked(true);

				new Thread() {

					public void run() {
						try {

							downloadNative(servletName, funcArgs);

							// // イベントディスパッチスレッドで
							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									ctrl.locked(false);

									// ファイル展開&実行
									executeResultFile(cont);
								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (TRequestException e) {
							ctrl.locked(false);
							ctrl.errorHandler(cont);

						} catch (TException ex) {
							ctrl.locked(false);
							ctrl.errorHandler(cont, ex);

						} finally {
							ctrl.locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			ctrl.locked(false);

			throw ex;
		}
	}

	/**
	 * TEXTファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param listener
	 */
	@SuppressWarnings("deprecation")
	public void uploadTXTAsync(Container cont, String servletName, TAsyncListener listener) {
		super.uploadTXTAsync(cont, servletName, listener);
	}
}
