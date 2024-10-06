package jp.co.ais.trans2.model.xml;

import org.xml.sax.helpers.*;

/**
 * ハンドラ
 */
public abstract class XMLHandler extends DefaultHandler {

	/**
	 * 変換後データを返す
	 * 
	 * @return 変換後データ
	 */
	protected abstract Object get();

}
