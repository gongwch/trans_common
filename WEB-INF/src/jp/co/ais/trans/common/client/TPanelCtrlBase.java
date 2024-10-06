package jp.co.ais.trans.common.client;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;

/**
 * メインパネルコントロール実装用
 */
public abstract class TPanelCtrlBase extends TAppletClientBase {

	/** プログラム情報 */
	protected TClientProgramInfo prgInfo;

	/**
	 * パネルを返す.
	 * 
	 * @see TAppletClientBase#getView()
	 */
	@Override
	public Container getView() {
		return getPanel();
	}

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	@SuppressWarnings("deprecation")
	public abstract TPanelBusiness getPanel();

	/**
	 * プログラム情報を設定
	 * 
	 * @param prgInfo プログラム情報
	 */
	public void setProgramInfo(TClientProgramInfo prgInfo) {
		this.prgInfo = prgInfo;
	}

	/**
	 * プログラム情報の取得
	 * 
	 * @return プログラム情報
	 */
	@SuppressWarnings("deprecation")
	public TClientProgramInfo getProgramInfo() {

		if (this.prgInfo == null) {
			return TClientProgramInfo.getInstance();
		}

		return this.prgInfo;
	}

	/**
	 * プログラムID取得
	 * 
	 * @return プログラムID
	 */
	public String getProgramCode() {

		return this.getProgramInfo().getProgramCode();
	}

	/**
	 * プログラム名称取得
	 * 
	 * @return プログラム名称
	 */
	public String getProgramName() {

		return this.getProgramInfo().getProgramName();
	}

	/**
	 * 画面識別子取得
	 * 
	 * @return 画面識別子
	 */
	public String getRealUID() {
		return "";
	}

	/**
	 * ログ用追加情報の取得
	 * 
	 * @return ログ用追加情報
	 */
	public String getRealInfo() {
		return "";
	}

}
