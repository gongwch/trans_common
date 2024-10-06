package simple;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.print.*;

/**
 * ƒTƒ“ƒvƒ‹
 */
public class SampleExcelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			List<List<Object>> list = new ArrayList<List<Object>>();

			boolean sign = true;

			for (int i = 0; i < 100; i++) { // 100s

				List<Object> data = new ArrayList<Object>();
				for (int j = 0; j < 15; j++) { // 15—ñ

					if (j < 5) {
						data.add("col" + j);
						continue;
					}

					if (j < 13) {
						if (sign) {
							data.add(new BigDecimal(1000000 + j * 1000));
						} else {
							data.add(new BigDecimal(1000000 + j * 1000).negate());
						}
						sign = !sign;
						continue;
					}

					data.add(DateUtil.getDate(2013, 9, j));
				}
				list.add(data);
			}

			SampleExcel excel = new SampleExcel();
			byte[] data = excel.getExcel(list);

			TPrinter printer = new TPrinter();
			printer.preview(data, "sample.xls");

		} catch (TException e) {
			e.printStackTrace();
			return;
		}
	}
}
