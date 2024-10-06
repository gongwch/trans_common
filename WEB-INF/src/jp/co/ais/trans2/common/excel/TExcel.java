package jp.co.ais.trans2.common.excel;

import java.io.*;
import java.math.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.report.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;

/**
 * Excel�����N���X
 * 
 * @author AIS
 */
public class TExcel extends LayoutBase {

	/** PRINT_AREA */
	public static final String PRINT_AREA = "Print_Area";

	/** COMMA_PATTERN */
	public static final Pattern COMMA_PATTERN = Pattern.compile(",");

	/** millseconds */
	@Deprecated
	public static final BigDecimal millseconds = new BigDecimal(24 * 60 * 60 * 1000);

	/** ����t */
	@Deprecated
	public static final Date baseDate = DateUtil.getDate(1900, 1, -1);

	/** �P�Z���`���[�g�����O�D�R�X�R�V�C���` */
	public static final double INCH_UNIT = 0.3937d;

	/** Excel XML ���[�h */
	public static boolean excelXml = false;

	static {
		try {
			excelXml = ServerConfig.isFlagOn("trans.excel.xml");
		} catch (Throwable e) {
			// �����Ȃ�
		}
	}

	/** �G�N�Z���^�C�v */
	protected ExcelType type = ExcelType.XLS;

	/** WorkBook */
	public Workbook workBook;

	/** �X�^�C���}�l�[�W�� */
	protected TExcelCellStyleManager cellStyleManager;

	/** �t�@�C��Stream */
	protected InputStream stream;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public TExcel(String lang) {
		this(lang, excelXml ? ExcelType.XLSX : ExcelType.XLS);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param type
	 */
	public TExcel(String lang, ExcelType type) {
		super(lang);

		this.type = type;

		// �V�K���[�N�u�b�N���쐬����
		this.workBook = createWorkbook();

		// �X�^�C��������
		cellStyleManager = createCellStyleManager();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param file �Ǎ��t�@�C��
	 * @throws TException �t�@�C���s��
	 */
	public TExcel(File file) throws TException {
		super("ja");

		// �t�@�C����
		String fileName = file.getName();

		if (fileName.indexOf(".") == -1) {
			// �t�@�C�����Ɋg���q������܂���B
			throw new TException("I00384");
		}

		// �g���q����G�N�Z���^�C�v����
		// �g���q���ʔp�~�B�^�C�v���ʂ�poi���ʋ@�\�g��

		// �t�@�C���Ǎ�
		readTempFile(null, file.getPath());

		// �X�^�C��������
		cellStyleManager = createCellStyleManager();
	}

	/**
	 * @return true:Excel XML ���[�h
	 */
	public static boolean isExcelXml() {
		return excelXml;
	}

	/**
	 * Workbook����
	 * 
	 * @return Workbook
	 */
	protected Workbook createWorkbook() {
		return TExcelOutput.createWorkbook(type);
	}

	/**
	 * CellStyleManager����
	 * 
	 * @return CellStyleManager
	 */
	protected TExcelCellStyleManager createCellStyleManager() {

		switch (type) {
		case XLSX:
			return new TXSSFCellStyleManager(workBook, lang);

		default:
			return new THSSFCellStyleManager(workBook, lang);
		}
	}

	/**
	 * �v�����^�̐ݒ�
	 * 
	 * @param sheet �ΏۃV�[�g
	 * @param printerSize �v�����^�T�C�Y
	 * @param landscape ������y�[�W�ݒ� true:��
	 */
	protected void setPrinterSytle(Sheet sheet, short printerSize, boolean landscape) {

		// �v�����^�ݒ�p��
		PrintSetup setup = sheet.getPrintSetup();

		// ��������ݒ肷��
		setup.setLandscape(landscape);

		// �T�C�Y��A4�ɐݒ肷��
		setup.setPaperSize(printerSize);

		// �y�[�W�����ݒ�
		setup.setLeftToRight(true);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����
	 * 
	 * @param sheetName �V�[�g��
	 * @return �V�[�g
	 */
	protected TExcelSheet addSheet(String sheetName) {

		TExcelSheet sheet = addSheet(sheetName, sheetName);
		return sheet;

	}

	/**
	 * Book�ɃV�[�g��ǉ�����.<br>
	 * �w�肳�ꂽ�P���\���S�㕔�ɕ\��������B
	 * 
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @return �V�[�g
	 */
	protected TExcelSheet addSheet(String sheetName, String headerWord) {
		return addSheet(sheetName, headerWord, true, SwingConstants.CENTER);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����.<br>
	 * �w�肳�ꂽ�P���\���S�㕔�ɕ\��������B
	 * 
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @param align
	 * @return �V�[�g
	 */
	protected TExcelSheet addSheet(String sheetName, String headerWord, int align) {
		return addSheet(sheetName, headerWord, true, align);
	}

	/**
	 * Book�ɃV�[�g��ǉ�����.<br>
	 * �w�肳�ꂽ�P���\���S�㕔�ɕ\��������B
	 * 
	 * @param sheetName �V�[�g��
	 * @param headerWord �w�b�_�\������
	 * @param hasColoumnTitle
	 * @param align
	 * @return �V�[�g
	 */
	protected TExcelSheet addSheet(String sheetName, String headerWord, boolean hasColoumnTitle, int align) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.createSheet();
		int sheetIndex = workBook.getNumberOfSheets() - 1;

		// �V�[�g���ݒ�
		setSheetName(sheetIndex, sheetName);
		setPrinterSytle(sheet, PrintSetup.A4_PAPERSIZE, true);

		es.sheet = sheet;

		// �w�b�_�[�ݒ�

		if (SwingConstants.LEFT == align) {
			es.sheet.getHeader().setLeft(
					HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11)
							+ headerWord);
		} else if (SwingConstants.RIGHT == align) {
			es.sheet.getHeader().setRight(
					HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11)
							+ headerWord);
		} else {
			es.sheet.getHeader().setCenter(
					HeaderFooter.font("Stencil-Normal", this.getFont()) + HeaderFooter.fontSize((short) 11)
							+ headerWord);
		}

		// �s�̃^�C�g��(��s�ڂ̃J��������)�w��
		if (hasColoumnTitle) {
			workBook.setRepeatingRowsAndColumns(sheetIndex, -1, -1, 0, 0);
		}

		return es;
	}

	/**
	 * �V�[�g���ݒ�<br>
	 * �����I��31�����Ő؂� �֎~�������u�����N�ɕϊ�
	 * 
	 * @param sheetIndex �ΏۃV�[�gIndex
	 * @param sheetName ����
	 */
	protected void setSheetName(int sheetIndex, String sheetName) {

		if (sheetName.length() > 31) {
			sheetName = sheetName.substring(0, 31);
		}

		// �t�@�C�����̋֎~�����ϊ�(/���u�����N)
		sheetName = getCorrectExcelSheetName(sheetName);

		// �V�[�g����ύX
		workBook.setSheetName(sheetIndex, sheetName);
	}

	/**
	 * �֎~�����ϊ���̃V�[�g���̎擾 <br>
	 * �V�[�g���̋֎~�����ϊ�(<b>���L�����˃u�����N</b>) <br>
	 * �R����(<b>:</b>)�A �~�L��(<b>\</b>)�A �X���b�V��(<b>/</b>)�A �^�╄(<b>?</b>)�A �A�X�^���X�N(<b>*</b>)�A ���p�J�b�R(<b>[</b>)�A �E�p�J�b�R(<b>]</b>) <br>
	 * 
	 * @param sheetName �V�[�g��
	 * @return ���m�V�[�g��
	 */
	public static String getCorrectExcelSheetName(String sheetName) {

		// �t�@�C�����̋֎~�����ϊ�(�˃u�����N)
		return sheetName.replaceAll(":", "").replaceAll("\\\\", "").replaceAll("/", "").replaceAll("\\?", "")
				.replaceAll("\\*", "").replaceAll("\\[", "").replaceAll("\\]", "");
	}

	/**
	 * �e���v���[�g�t�@�C����ǂݍ���
	 * 
	 * @param cls Class
	 * @param path �t�@�C���p�X
	 * @throws TException
	 */
	protected void readTempFile(Class cls, String path) throws TException {

		try {
			if (cls != null) {
				stream = ResourceUtil.getResourceAsStream(cls, path);

			} else {
				stream = new FileInputStream(path);
			}

			workBook = WorkbookFactory.create(stream);
			if (workBook instanceof HSSFWorkbook) {
				type = ExcelType.XLS;
			} else {
				type = ExcelType.XLSX;
			}

		} catch (Throwable ex) {
			ServerErrorHandler.handledException(ex);

			// �t�@�C���̓ǂݍ��݂Ɏ��s���܂����B{0}
			throw new TException("E00021", path);
		}
	}

	/**
	 * Excel�̃o�C�i���ϊ�
	 * 
	 * @return �o�C�i��
	 * @throws IOException
	 */
	public byte[] getBinary() throws IOException {

		ByteArrayOutputStream fileOut = null;
		try {

			fileOut = new ByteArrayOutputStream();

			// ���[�N�u�b�N�̎擾
			workBook = TExcelOutput.getWorkbook(workBook);

			// �쐬�������[�N�u�b�N��ۑ�����
			workBook.write(fileOut);

			fileOut.flush();

			return fileOut.toByteArray();
		} finally {
			ResourceUtil.closeOutputStream(fileOut);
		}
	}

	@Override
	protected String getFont() {
		return null;
	}

	/**
	 * �X�^�C���}�l�[�W����Ԃ�
	 * 
	 * @return �X�^�C���}�l�[�W��
	 */
	public TExcelCellStyleManager getCellStyleManager() {
		return cellStyleManager;
	}

	/**
	 * �G�N�Z���^�C�v
	 * 
	 * @return �G�N�Z���^�C�v
	 */
	public ExcelType getType() {
		return this.type;
	}

	/**
	 * �V�[�g���擾����
	 * 
	 * @param sheetIndex �V�[�gIndex
	 * @return �V�[�g
	 */
	public TExcelSheet getSheet(int sheetIndex) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.getSheetAt(sheetIndex);
		es.sheet = sheet;
		return es;
	}

	/**
	 * �V�[�g���擾����
	 * 
	 * @param sheetName �V�[�g��
	 * @return �V�[�g
	 */
	public TExcelSheet getSheet(String sheetName) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.getSheet(sheetName);
		es.sheet = sheet;
		return es;
	}

	/**
	 * Stream�����
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();

		ResourceUtil.closeInputStream(stream);
	}

	/**
	 * �G�N�Z���̓��t�`��Date���擾����.
	 * 
	 * @param dec ���l
	 * @return �w�肳�ꂽ���l�ɑΉ�����Date(1900/01/01 00:00:00�x�[�X)
	 */
	public static Date getExcelDate(BigDecimal dec) {
		Calendar parsed = org.apache.poi.ss.usermodel.DateUtil.getJavaCalendar(dec.doubleValue(), false);
		return parsed.getTime();

	}

	/**
	 * �V�[�g���폜����
	 * 
	 * @param sheetIndex �V�[�gIndex
	 */
	protected void removeSheet(int sheetIndex) {
		workBook.removeSheetAt(sheetIndex);
	}

	/**
	 * �V�[�g���̂̎擾
	 * 
	 * @param sheetIndex �V�[�gIndex
	 * @return �V�[�g����
	 */
	protected String getSheetName(int sheetIndex) {
		return workBook.getSheetName(sheetIndex);
	}

	/**
	 * ����͈͂�ݒ肷��<br>
	 * poi 3.2�ȍ~�AXLSX�Ή��̂��߁A����͈͎w��͌����Ȃ��Ȃ��Ă��܂�<br>
	 * ���̂��߂̌ʑΉ�
	 * 
	 * @param sheetIndex �V�[�gIndex
	 * @param startColumn �J�n��
	 * @param endColumn �I����
	 * @param startRow �J�n�s
	 * @param endRow �I���s
	 */
	protected void setPrintArea(int sheetIndex, int startColumn, int endColumn, int startRow, int endRow) {

		// �Z���͈͍쐬
		CellReference cell = new CellReference(startRow, startColumn, true, true);
		String reference = cell.formatAsString();

		cell = new CellReference(endRow, endColumn, true, true);
		reference = reference + ":" + cell.formatAsString();

		// ���O�擾
		Name name = null;
		for (int i = 0; i < workBook.getNumberOfNames(); i++) {
			Name n = workBook.getNameAt(i);
			if (n.getSheetIndex() == sheetIndex && PRINT_AREA.equals(n.getNameName())) {
				name = n;
				break;
			}
		}

		// �͈�
		String[] parts = COMMA_PATTERN.split(reference);
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			TExcelOutput.appendFormat(sb, getSheetName(sheetIndex));
			sb.append("!");
			sb.append(parts[i]);
		}

		if (name == null) {
			// �쐬
			name = workBook.createName();
			name.setNameName(PRINT_AREA);
			name.setSheetIndex(sheetIndex);
		}

		// �͈͐ݒ�
		name.setRefersToFormula(sb.toString());
	}

	/**
	 * �Œ�s���ݒ肷��
	 * 
	 * @param sheetIndex �V�[�gIndex
	 * @param startColumn �J�n��
	 * @param endColumn �I����
	 * @param startRow �J�n�s
	 * @param endRow �I���s
	 */
	protected void setRepeatingRowsAndColumns(int sheetIndex, int startColumn, int endColumn, int startRow,
			int endRow) {
		workBook.setRepeatingRowsAndColumns(sheetIndex, startColumn, endColumn, startRow, endRow);
	}

	/**
	 * �w��V�[�g��clone����
	 * 
	 * @param sheetIndex �V�[�gIndex
	 * @return �V�[�g
	 */
	protected TExcelSheet cloneSheet(int sheetIndex) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.cloneSheet(sheetIndex);

		{
			// ����ݒ���N���[��
			Sheet s = workBook.getSheetAt(sheetIndex);
			clonePrintSetup(s, sheet);
		}

		es.sheet = sheet;
		return es;

	}

	/**
	 * �w��V�[�g���w��̃V�[�g����clone����
	 * 
	 * @param sheetIndex �V�[�gIndex
	 * @param sheetName �V�[�g��
	 * @return �V�[�g
	 */
	protected TExcelSheet cloneSheet(int sheetIndex, String sheetName) {

		TExcelSheet es = new TExcelSheet(this);
		Sheet sheet = workBook.cloneSheet(sheetIndex);

		{
			// ����ݒ���N���[��
			Sheet s = workBook.getSheetAt(sheetIndex);
			clonePrintSetup(s, sheet);
		}

		workBook.setSheetName(workBook.getNumberOfSheets() - 1, sheetName);

		es.sheet = sheet;
		return es;

	}

	/**
	 * ����ݒ���N���[��<br>
	 * s -> sheet PrintSetup�N���[��
	 * 
	 * @param s
	 * @param sheet
	 */
	protected void clonePrintSetup(Sheet s, Sheet sheet) {
		// ����ݒ���N���[��

		PrintSetup ps = sheet.getPrintSetup();
		PrintSetup ori = s.getPrintSetup();

		if (ps != null && ori != null) {
			ps.setPaperSize(ori.getPaperSize());
			ps.setScale(ori.getScale());
			ps.setPageStart(ori.getPageStart());
			ps.setFitWidth(ori.getFitWidth());
			ps.setFitHeight(ori.getFitHeight()); // ���y�[�W���N���[����ɂ�����������
			ps.setLeftToRight(ori.getLeftToRight());
			ps.setLandscape(ori.getLandscape());
			ps.setValidSettings(ori.getValidSettings());
			ps.setNoColor(ori.getNoColor());
			ps.setDraft(ori.getDraft());
			ps.setNotes(ori.getNotes());
			ps.setNoOrientation(ori.getNoOrientation());
			ps.setUsePage(ori.getUsePage());
			ps.setHResolution(ori.getHResolution());
			ps.setVResolution(ori.getVResolution());
			ps.setHeaderMargin(ori.getHeaderMargin());
			ps.setFooterMargin(ori.getFooterMargin());
			ps.setCopies(ori.getCopies());
		}
	}

	/**
	 * �Z���`���[�g���˃C���`
	 * 
	 * @param cm �Z���`���[�g��
	 * @return �C���`
	 */
	public static double convertCmToInch(double cm) {
		return cm * INCH_UNIT;
	}

	/**
	 * �v�Z���Z���̍Čv�Z
	 * 
	 * @param wb
	 */
	public static void evaluateAll(Workbook wb) {
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
			Sheet sheet = wb.getSheetAt(sheetNum);
			for (Row r : sheet) {
				for (Cell c : r) {
					if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
						try {
							evaluator.evaluateFormulaCell(c);
						} catch (Exception ex) {
							// �s�v�c�ȃG���[�����
						}
					}
				}
			}
		}
	}

	/**
	 * �v�Z���Z���̍Čv�Z
	 */
	public void evaluate() {
		evaluateAll(workBook);
	}

	/**
	 * �V�[�g�����̎擾
	 * 
	 * @return �V�[�g����
	 */
	public int getSheetCount() {
		return workBook.getNumberOfSheets();
	}

	/**
	 * �s�R�s�[
	 * 
	 * @param workbook
	 * @param worksheet
	 * @param sourceRowNum ��
	 * @param destinationRowNum ��(���݂�����A�s�ǉ��B���݂��ĂȂ��ꍇ�A�s�쐬)
	 */
	public static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		Row newRow = worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = workbook.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}

		// If there are are any merged regions in the source row, copy to new row
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
						(newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
						cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}
}
