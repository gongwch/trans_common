package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

/**
 * プログレスバーを表示するダイアログ
 */
public class TProgressDialog extends JDialog implements Runnable {

	/** プログレスバー */
	public JProgressBar progressBar;

	/** キャンセル */
	public volatile boolean canceled = false;

	/**  */
	public JPanel pnlProgressBar;

	/** 表示メッセージ */
	public String message = null;

	/** 異常情報を持つ */
	public Throwable exception = null;

	/**
	 * コンストラクター.
	 * 
	 * @param owner オーナー
	 * @param modal モーダル有無
	 */
	public TProgressDialog(Frame owner, boolean modal) {
		super(owner, modal);
		setUndecorated(true);
	}

	/**
	 * プログレスバー生成.
	 * 
	 * @return プログレスバー
	 */
	protected JProgressBar createProgressBar() {
		return new JProgressBar();
	}

	/**
	 * プログレスバー取得.
	 * 
	 * @return プログレスバー
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * プログレスバー現在値設定.
	 * 
	 * @param n 進捗バーの現在の値
	 */
	public void setProgressValue(int n) {
		progressBar.setValue(n);

		StringBuilder sb = new StringBuilder();
		sb.append(n);
		sb.append(" / ");
		sb.append(getMaxValue());

		progressBar.setString(sb.toString());
	}

	/**
	 * プログレスバー現在値＋１、表示文字設定.
	 * 
	 * @param str 進捗バーの現在表示文字
	 */
	public void setProgressText(String str) {
		progressBar.setValue(progressBar.getValue() + 1);
		progressBar.setString(str);
	}

	/**
	 * プログレスバー現在値取得.
	 * 
	 * @return 進捗バーの現在の値
	 */
	public int getProgressValue() {
		return progressBar.getValue();
	}

	@Override
	public void dispose() {
		canceled = true;
		super.dispose();
	}

	/**
	 * 終了有無取得.
	 * <p>
	 * 主にキャンセルボタンによってキャンセルされたかどうかの判定に使用する。<br>
	 * ダイアログ自体が終了した際もtrueとなる。
	 * </p>
	 * 
	 * @return true：進捗が終了している
	 */
	public boolean canceled() {
		return canceled;
	}

	/**
	 * 進捗終了設定.
	 * <p>
	 * スレッド処理が終了した際に当メソッドを呼び出さなければならない。
	 * </p>
	 */
	public synchronized void progressEnd() {
		setVisible(false);
	}

	/**
	 * ダイアログ初期化.
	 * 
	 * @param maxValue 最大値
	 */
	public void init(int maxValue) {
		canceled = false;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setSize(450, 70);

		Container c = getContentPane();
		c.setLayout(new GridBagLayout());

		initProgressPane(c);
		setUndecorated(true);

		setLocationRelativeTo(null);
		setMaxValue(maxValue);
	}

	/**
	 * 初期化
	 * 
	 * @param c
	 */
	protected void initProgressPane(Container c) {
		progressBar = createProgressBar();
		progressBar.setStringPainted(true);

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		c.add(progressBar, gc);
	}

	/**
	 * ダイアログ表示設定.
	 * <p>
	 * trueにすると、スレッド（{@link #run()}）も実行開始する。<br>
	 * また、モーダルダイアログの場合、スレッドの終了も待つ。
	 * </p>
	 * 
	 * @param b true：ダイアログを表示する
	 */
	@Override
	public void setVisible(boolean b) {
		if (b) {
			startSilence(false);
		} else {
			super.setVisible(false);
		}
	}

	/**
	 * @param silence true:表示なし
	 */
	public void startSilence(boolean silence) {
		Thread thread = new Thread(this);
		thread.start();
		if (!silence) {
			super.setVisible(true);
		}
		if (isModal()) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// エラーなし
				e.printStackTrace();
			}
		}
	}

	/**
	 * スレッド処理.
	 * <p>
	 * 当メソッドをオーバーライドして、実際の処理を行う。
	 * </p>
	 * 
	 * <pre>
	 * // 例
	 * &#064;Override
	 * public void run() {
	 *  for (int i = 0; i &lt; 100; i++) {
	 *   if ({@link #canceled()}) {
	 *    return;
	 *   }
	 *   {@link #setProgressValue}(i + 1);
	 *  }
	 *  {@link #progressEnd()};
	 * }
	 * </pre>
	 */
	@Override
	public void run() {
		progressEnd();
	}

	/**
	 * 表示メッセージの取得
	 * 
	 * @return message 表示メッセージ
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 表示メッセージの設定
	 * 
	 * @param message 表示メッセージ
	 */
	public void setMessage(String message) {
		this.message = message;

		this.setTitle(message);
	}

	/**
	 * 最大値の取得
	 * 
	 * @return maxValue 最大値
	 */
	public int getMaxValue() {
		return progressBar.getMaximum();
	}

	/**
	 * 最大値の設定
	 * 
	 * @param maxValue 最大値
	 */
	public void setMaxValue(int maxValue) {
		progressBar.setMaximum(maxValue);
	}

	/**
	 * 異常情報の取得
	 * 
	 * @return exception 異常情報
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * 異常情報の設定
	 * 
	 * @param exception 異常情報
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
