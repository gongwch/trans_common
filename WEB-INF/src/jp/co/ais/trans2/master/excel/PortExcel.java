package jp.co.ais.trans2.master.excel;

import java.math.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Port Excel
 * 
 * @author AIS
 */
public class PortExcel extends TExcel {

	/** OP�@�\�s�g�p */
	public static boolean isNotUseOP = ServerConfig.isFlagOn("trans.op.not.use");

	/** T/S�`�g�p�t���O */
	public static boolean isUseTS_PORT = ServerConfig.isFlagOn("trans.use.ts.port");

	/** �T�Z�`��ڗ��p�t���O */
	public static boolean isUseEstPortCharge = ServerConfig.isFlagOn("trans.op.use.port.est.amt");

	/** �T�Z�㗝�X�ݒ藘�p�t���O */
	public static boolean isUseEstAgent = ServerConfig.isFlagOn("trans.op.use.port.std.agent");

	/**
	 * Excel for port master
	 * 
	 * @param lang
	 */
	public PortExcel(String lang) {
		super(lang);
	}

	/**
	 * Get list data excel
	 * 
	 * @param list
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Port> list) throws TException {

		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Create report excel
	 * 
	 * @param list
	 */
	public void createReport(List<Port> list) {

		// Sheet excel
		TExcelSheet sheet = addSheet(getWord("C11771"));

		sheet.addColumn(getWord("C00584").toUpperCase(), 4200);
		sheet.addColumn(getWord("C00585").toUpperCase(), 12600);

		if (isNotUseOP) {
			// �`����
			sheet.addColumn(getWord("C00586"), 8400);
			// �`��������
			sheet.addColumn(getWord("C00587"), 12600);
			// �J�n�N����
			sheet.addColumn(getWord("C00055"), 4200);
			// �I���N����
			sheet.addColumn(getWord("C00261"), 4200);
		} else {
			if (isUseTS_PORT) {
				sheet.addColumn(getWord("T/S�`�R�[�h"), 4200); // TODO
				sheet.addColumn(getWord("T/S�`����"), 12600); // TODO
			}
			sheet.addColumn(getWord("COP001"), 4200);
			sheet.addColumn(getWord("COP1285"), 3500); // REGION CODE
			sheet.addColumn(getWord("COP1286"), 6400); // REGION NAME
			sheet.addColumn(getWord("COP046"), 4200); // ���R�[�h
			sheet.addColumn(getWord("COP047"), 4900); // ����
			sheet.addColumn(getWord("COP002"), 2600); // TZ
			sheet.addColumn(getWord("COP042"), 6400);
			sheet.addColumn(getWord("COP043"), 3500);
			sheet.addColumn(getWord("COP044"), 6400);
			sheet.addColumn(getWord("COP045"), 1700);
			sheet.addColumn(getWord("COP398"), 2100); // (S)ECA
			sheet.addColumn(getWord("COP1293") + getWord("C00174"), 3500); // MLIT REGION
			sheet.addColumn(getWord("COP1293"), 6400); // MLIT REGION
			sheet.addColumn(getWord("COP1294") + getWord("C00174"), 3500); // MLIT COUNTRY
			sheet.addColumn(getWord("COP1294"), 6400); // MLIT COUNTRY
			if (isUseEstPortCharge) {
				if (isUseEstAgent) {
					sheet.addColumn(getWord("C02005"), 3500); // Agent CODE
					sheet.addColumn(getWord("COP405"), 6400); // Agent
				}
				sheet.addColumn(getWord("COP391"), 3200); // Cur
				sheet.addColumn(getWord("COP406"), 6400); // Amount
			}
		}

		for (Port bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());

			if (isNotUseOP) {
				newRow.addCell(bean.getNames());
				newRow.addCell(bean.getNamek());
				newRow.addCell(bean.getDateFrom());
				newRow.addCell(bean.getDateTo());

			} else {
				if (isUseTS_PORT) {
					newRow.addCell(bean.getTS_PORT_CODE());
					newRow.addCell(bean.getTS_PORT_NAME());
				}
				newRow.addCell(bean.getUNLOCODE());
				newRow.addCell(bean.getREGION_CODE()); // REGION CODE
				newRow.addCell(bean.getREGION_NAME()); // REGION NAME
				newRow.addCell(bean.getCOU_CODE()); // ���R�[�h
				newRow.addCell(bean.getCOU_NAME());
				newRow.addCell(convertDBValueForScreen(bean.getGMT_TIMEZONE()), SwingConstants.CENTER);

				if (bean.getLAT() != null) {
					newRow.addCell(bean.getLAT().abs(), 14);
					if (bean.getLAT().signum() == -1) {
						newRow.addCell("S", SwingConstants.CENTER);
					} else {
						newRow.addCell("N", SwingConstants.CENTER);
					}
				} else {
					newRow.addCell("");
					newRow.addCell("");
				}
				if (bean.getLNG() != null) {
					newRow.addCell(bean.getLNG().abs(), 14);
					if (bean.getLNG().signum() == -1) {
						newRow.addCell("E", SwingConstants.CENTER);
					} else {
						newRow.addCell("W", SwingConstants.CENTER);
					}
				} else {
					newRow.addCell("");
					newRow.addCell("");
				}

				newRow.addCell(BooleanUtil.toBoolean(bean.getS_ECA_FLG()) ? getWord("COP644") : "",
					SwingConstants.CENTER);
				newRow.addCell(bean.getMLIT_REGION_CODE());
				newRow.addCell(bean.getMLIT_REGION_NAME());
				newRow.addCell(bean.getMLIT_COUNTRY_CODE());
				newRow.addCell(bean.getMLIT_COUNTRY_NAME());
				if (isUseEstPortCharge) {
					if (isUseEstAgent) {
						newRow.addCell(bean.getSTD_PORT_AGENT_CODE()); // Agent CODE
						newRow.addCell(bean.getSTD_PORT_AGENT_NAME()); // Agent
					}
					newRow.addCell(bean.getPCG_EST_CUR_CODE()); // Cur
					newRow.addCell(bean.getPCG_EST_AMT()); // Amount
				}
			}
		}
	}

	/**
	 * Convert value from database to screen
	 * 
	 * @param convertBigDecimal
	 * @return String of object
	 */
	public static String convertDBValueForScreen(BigDecimal convertBigDecimal) {
		String result = "";

		if (convertBigDecimal != null) {

			String rightValueToFormat = multiply(convertBigDecimal.remainder(BigDecimal.ONE), new BigDecimal(60))
				.toBigInteger().toString();
			if (rightValueToFormat.length() == 1) {
				rightValueToFormat = "0".concat(rightValueToFormat);
			}
			String replaceMinus = convertBigDecimal.toBigInteger().toString().replace("-", "");
			String value = replaceMinus.concat(":".concat(rightValueToFormat));
			if (convertBigDecimal.toBigInteger().toString().contains("-")) {
				if (replaceMinus.length() == 1) {
					result = "- 0".concat(value);
				} else {
					result = "- ".concat(value);
				}
			} else {
				if (replaceMinus.length() == 1) {
					result = "+ 0".concat(value);
				} else {
					result = "+ ".concat(value);
				}
			}
		}
		return result;
	}

	/**
	 * �R���̌v�Z(a * b)<br>
	 * �P���~����<br>
	 * �����_�ȉ�2���܂�
	 * 
	 * @param a
	 * @param b
	 * @return �R��
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		return multiply(a, b, 2);
	}

	/**
	 * ���ʂ̌v�Z(a * b)
	 * 
	 * @param a
	 * @param b
	 * @param digits
	 * @return ����
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b, int digits) {
		return DecimalUtil.roundNum(DecimalUtil.avoidNull(a).multiply(DecimalUtil.avoidNull(b)), digits);
	}
}
