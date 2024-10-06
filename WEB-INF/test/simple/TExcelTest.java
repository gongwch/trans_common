package simple;

import java.io.*;

import jp.co.ais.trans2.common.excel.*;

/**
 * TExcelテスト
 */
public class TExcelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 testOld();
		testNew();
	}

	/**
	 * XLSテスト
	 */
	public static void testOld() {
		System.out.println("■XLS");
		test("D:/temp/maezawa009130.111.1211.070122.xls");
	}

	/**
	 * XLSXテスト
	 */
	public static void testNew() {
		System.out.println("■XLSX");
		test("D:/temp/maezawa009130.111.1211.070122.xlsx");
	}

	/**
	 * @param path
	 */
	private static void test(String path) {
		try {
			File file = new File(path);

			TExcel excel = new TExcel(file);
			// TExcelSheet sheet = excel.getSheet(0);
			//
			// for (int row = 1; row < sheet.getLastRowNum(); row++) {
			//
			// for (int column = 0; column <= 17; column++) {
			// System.out.println(row + ", " + column + "=" + sheet.getString(row, column));
			// }
			// }

			TExcelSheet sheet2 = excel.getSheet(0);
			System.out.println(sheet2.getString(3, 2));

			System.out.println("--");

			TExcelSheet sheet3 = excel.getSheet("港費請求書");
			System.out.println(sheet3.getString(3, 2));
			System.out.println(sheet3.getDecimal(6, 4));

			System.out.println("--");

			System.out.println(sheet3.getString("A7"));
			System.out.println(sheet3.getString("B7"));
			System.out.println(sheet3.getString("C7"));
			System.out.println(sheet3.getString("E7"));
			System.out.println(sheet3.getString("E117"));

			System.out.println("--");
			//
			System.out.println(sheet3.getString("a7"));
			System.out.println(sheet3.getString("b7"));
			System.out.println(sheet3.getString("c7"));
			System.out.println(sheet3.getString("e7"));

			System.out.println("--");

			System.out.println(sheet3.getString("AA1"));
			System.out.println(sheet3.getString("AB1"));
			System.out.println(sheet3.getString("BA1"));
			System.out.println(sheet3.getString("BB1"));

			System.out.println(sheet3.getString("CA1"));
			System.out.println(sheet3.getString("CE1"));

			System.out.println(sheet3.getString("CE117"));

			System.out.println("--");

			System.out.println(sheet3.getString("aa1"));
			System.out.println(sheet3.getString("ab1"));
			System.out.println(sheet3.getString("ba1"));
			System.out.println(sheet3.getString("bb1"));

			System.out.println(sheet3.getString("ca1"));
			System.out.println(sheet3.getString("ce1"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
