package jp.co.ais.trans2.common.viewer;

import jp.co.ais.trans2.common.gui.TStorableKey;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * TViewerのコンポーネント復元キー
 * @author AIS
 *
 */
public class TFrameViewerStorableKey implements TStorableKey {

	/**
	 * field
	 */
	protected TViewerLayout layout = null;

	public TViewerLayout getLayout() {
		return layout;
	}

	public void setLayout(TViewerLayout layout) {
		this.layout = layout;
	}

	public TFrameViewerStorableKey(TViewerLayout layout) {
		this.layout = layout;
	}

	public String getKey() {
		String key =
			TLoginInfo.getCompany().getCode() +"_" +
			TLoginInfo.getUser().getCode() +"_" +
			layout.getClass().getName();

		return key;
	}

}
