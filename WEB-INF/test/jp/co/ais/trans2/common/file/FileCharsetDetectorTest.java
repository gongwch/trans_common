package jp.co.ais.trans2.common.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.charset.*;

import org.junit.jupiter.api.*;

/**
 * 
 */
class FileCharsetDetectorTest {

	/** デバッグ用 */
	boolean isDebug = false;

	/**  */
	public static FileCharsetDetectorTest instance;

	/**
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance = new FileCharsetDetectorTest();
		instance.test();
	}

	/**
	 * 
	 */
	@Test
	void test() {
		assertEquals("windows-31j", test("SJIS.txt").name());
		assertEquals("UTF-8", test("UTF8.txt").name());
		assertEquals("UTF-16BE", test("UTF16BE.txt").name());
		assertEquals("UTF-16LE", test("UTF16LE.txt").name());
	}

	/**
	 * @param fileName
	 * @return 指定ファイルでテスト
	 */
	public Charset test(String fileName) {
		File file = new File("src/test/resources/jp/co/ais/trans2/common/file/" + fileName);
		System.out.println(file.getAbsolutePath());
		InputStreamReader rd = null;
		BufferedReader br = null;
		Charset cs = null;
		try {
			cs = FileCharsetDetector.getCharset(file);
			if (isDebug) {
				System.out.println(cs.displayName());
				System.out.println("File Name : " + fileName + " / このファイルの文字コードは : " + cs.displayName());
				rd = new InputStreamReader(new FileInputStream(file), cs);
				br = new BufferedReader(rd);
				while (true) {
					String line = br.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeInstance(br);
			closeInstance(rd);
		}
		return cs;
	}

	/**
	 * @param instance
	 */
	static void closeInstance(Closeable instance) {
		try {
			if (instance != null) {
				instance.close();
			}
		} catch (Exception e) {
			//
		}
	}
}
