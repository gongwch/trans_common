package jp.co.ais.trans2.model.xml;

import java.io.*;

import jp.co.ais.trans.common.util.*;

/**
 * XMLファイルフィルター
 */
public class XMLFileFilter implements FileFilter {

	/**
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		return "xml".equalsIgnoreCase(ResourceUtil.getExtension(f));
	}

}
