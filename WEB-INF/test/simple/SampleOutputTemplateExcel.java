package simple;

import java.io.*;

import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.print.*;

/**
 * エクセル入力規則ありデータ出力
 */
public class SampleOutputTemplateExcel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("D:/trans2.2/trans-common2.2/WEB-INF/test/simple/template_input.xls");
		try {
			TExcel excel = new TExcel(file);

			// 行追加
			TExcelSheet sheet = excel.getSheet(0);
			sheet.setRowNo(2);
			TExcelRow row = sheet.addRow();

			for (int i = 1; i <= 17; i++) {
				if (i == 9) {
					// Mode of Shipment
					row.addCell("0：RO/RO");
					continue;
				}

				row.addCell("XXX");
			}

			byte[] data = excel.getBinary();
			TPrinter printer = new TPrinter();
			printer.preview(data, "出力結果.xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
