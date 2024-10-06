package jp.co.ais.trans2.common.file;

import java.io.*;

/**
 * TFileReader
 */
public class TFileReader extends BufferedReader {

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public TFileReader(TFile file) throws FileNotFoundException {
		super(new FileReader(file.getFile()));
	}

}
