package jp.co.ais.trans2.common.excel;

import java.math.*;
import java.util.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * Excel�V�[�g
 * 
 * @author AIS
 */
public class TExcelSheet {

	/** �u�b�N */
	public TExcel book;

	/** �V�[�g */
	public Sheet sheet;

	/** �w�b�_�[�s */
	protected Row row = null;

	/** �ŏI�s�ԍ� */
	protected int rowNo = 1;

	/** �ŏI��ԍ� */
	protected int columnNo = 0;

	/** �Z���X�^�C���ێ��p */
	protected Map<Integer, CellStyle> styleMap = new TreeMap<Integer, CellStyle>();

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param book TExcel
	 */
	public TExcelSheet(TExcel book) {
		this.book = book;
	}

	/**
	 * �ŏI�s�ԍ��Z�b�g(�����ʒu)
	 * 
	 * @param initRow �ŏI�s�ԍ�
	 */
	public void setRowNo(int initRow) {
		this.rowNo = (short) initRow;
	}

	/**
	 * �ŏI��̌��ɃJ������ǉ�����
	 * 
	 * @param title
	 * @param width
	 */
	public void addColumn(String title, int width) {
		if (row == null) {
			row = sheet.createRow(0);
		}

		Cell newcell = createCell(row, columnNo, book.getCellStyleManager().getHeadStyle());
		newcell.setCellValue(title);
		setColumnWidth(columnNo, width);
		columnNo++;
	}

	/**
	 * ����ݒ肷��
	 * 
	 * @param columIndex �J�����ԍ�
	 * @param width ��
	 */
	public void setColumnWidth(int columIndex, int width) {
		sheet.setColumnWidth(columIndex, width);
	}

	/**
	 * �w��^�C�v�̐ݒ���s���A�Z���𐶐�����.
	 * 
	 * @param hssfRow_ �Y���s
	 * @param cellIndex �Z���ԍ�
	 * @param cellStyle �^�C�v
	 * @return �������ꂽ�Z��
	 */
	protected Cell createCell(Row hssfRow_, int cellIndex, CellStyle cellStyle) {

		Cell newcell = hssfRow_.createCell(cellIndex);

		CellStyle style = styleMap.get(cellStyle.hashCode());

		if (style == null) {
			style = book.createCellStyleManager().createCellStyle();
			style.cloneStyleFrom(cellStyle);

			styleMap.put(cellStyle.hashCode(), style);
		}

		newcell.setCellStyle(style);

		return newcell;
	}

	/**
	 * �w��̃Z���ɃX�^�C�����Z�b�g����.
	 * 
	 * @param row �s�ԍ�
	 * @param column ��ԍ�
	 * @param style �l
	 */
	public void setCellStyle(int row, int column, CellStyle style) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell cell = hrow.getCell((short) column);
		if (cell == null) {
			cell = hrow.createCell((short) column);
		}

		cell.setCellStyle(style);
	}

	/**
	 * �s��ǉ�����
	 * 
	 * @return �ǉ������s
	 */
	public TExcelRow addRow() {

		// �s���`�F�b�N(Excel2003�̏ꍇ�̂݃`�F�b�N)
		if (65536 <= rowNo && !TExcel.isExcelXml()) {
			throw new TRuntimeException("W00213");
		}

		Row row_ = sheet.createRow(rowNo);
		TExcelRow er = new TExcelRow(book);
		er.row = row_;
		rowNo++;
		return er;
	}

	/**
	 * �s���擾����
	 * 
	 * @param rowNo_ �s�ԍ�
	 * @return �擾�����s
	 */
	public TExcelRow getRow(int rowNo_) {
		Row row_ = sheet.getRow(rowNo_);
		if (row_ == null) {
			row_ = sheet.createRow(rowNo_);
		}
		TExcelRow er = new TExcelRow(book);
		er.row = row_;
		return er;
	}

	/**
	 * ��������̐ݒ�
	 * 
	 * @param landscape true:�� / false:�c
	 */
	public void setLandscape(boolean landscape) {
		PrintSetup setup = sheet.getPrintSetup();
		setup.setLandscape(landscape);
	}

	/**
	 * �}�[�W���̐ݒ�<br>
	 * <li>SwingConstants.TOP</li><br>
	 * <li>SwingConstants.BOTTOM</li><br>
	 * <li>SwingConstants.LEFT</li><br>
	 * <li>SwingConstants.RIGHT</li><br>
	 * 
	 * @param align SwingConstants���g��
	 * @param margin
	 */
	public void setMargin(int align, double margin) {

		short sAlign = 0;

		switch (align) {
			case SwingConstants.TOP:
				sAlign = Sheet.TopMargin;
				break;

			case SwingConstants.BOTTOM:
				sAlign = Sheet.BottomMargin;
				break;

			case SwingConstants.LEFT:
				sAlign = Sheet.LeftMargin;
				break;

			case SwingConstants.RIGHT:
				sAlign = Sheet.RightMargin;
				break;
		}

		sheet.setMargin(sAlign, margin);

	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����
	 * 
	 * @param row
	 * @param column
	 * @param value �l
	 * @param decimalPoint
	 */
	public void setValue(int row, int column, BigDecimal value, int decimalPoint) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		TExcelCellStyleManager cm = book.getCellStyleManager();
		String format = NumberFormatUtil.makeNumberFormat(decimalPoint);

		if (!cm.getDecimalStyles().containsKey(format)) {

			CellStyle decimalCellStyle = cm.createCellStyle();
			decimalCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			decimalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			decimalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			decimalCellStyle.setFont(cm.getFontStyle());

			// �t�H�[�}�b�g���쐬����
			DataFormat df = cm.createDataFormat();
			decimalCellStyle.setDataFormat(df.getFormat(format));
			cm.getDecimalStyles().put(format, decimalCellStyle);
		}

		Cell newcell = createCell(hrow, (short) column, cm.getDecimalStyles().get(format));

		if (value == null) {
			newcell.setCellValue("");

		} else {
			newcell.setCellValue(value.doubleValue());
		}
	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����
	 * 
	 * @param row
	 * @param column
	 * @param value �l
	 */
	public void setValue(int row, int column, String value) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, (short) column, book.getCellStyleManager().getLeftStyle());

		newcell.setCellValue(value);
	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @param decimalPoint
	 * @param styleType
	 */
	@SuppressWarnings("unused")
	public void setValue(int row, int column, BigDecimal value, int decimalPoint, TCellStyle styleType) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, column, styleType.getCellStyle());

		if (value == null) {
			newcell.setCellValue("");

		} else {
			newcell.setCellValue(value.doubleValue());
		}

	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����
	 * 
	 * @param row
	 * @param column
	 * @param value �l
	 * @param styleType
	 */
	public void setValue(int row, int column, String value, TCellStyle styleType) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, column, styleType.getCellStyle());

		newcell.setCellValue(value);
	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����
	 * 
	 * @param row
	 * @param column
	 * @param value �l
	 * @param styleType
	 */
	public void setValue(int row, int column, Date value, TCellStyle styleType) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		Cell newcell = createCell(hrow, column, styleType.getCellStyle());

		newcell.setCellValue(value);
	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����i�s�X�^�C���R�s�[�A���l�ݒ�j
	 * 
	 * @param copyrow �R�s�[���s
	 * @param row �R�s�[��s
	 * @param column �w��Z��
	 * @param value �l
	 * @param decimalPoint �����_�ȉ�����
	 */
	public void setValueAndStyleGet(int copyrow, int row, int column, BigDecimal value, int decimalPoint) {

		Row hrow = sheet.getRow(row);

		if (hrow == null) {
			hrow = copyRow(copyrow, row);
		}

		TExcelCellStyleManager cm = book.getCellStyleManager();
		String format = NumberFormatUtil.makeNumberFormat(decimalPoint);

		cm.getDecimalStyles().put(format, hrow.getCell(column).getCellStyle());

		if (value == null) {
			hrow.getCell(column).setCellValue("");

		} else {
			hrow.getCell(column).setCellValue(value.doubleValue());
		}

	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����i�s�X�^�C���R�s�[�A������ݒ�j
	 * 
	 * @param copyrow �R�s�[���s
	 * @param row �R�s�[��s
	 * @param column �w��Z��
	 * @param value �l
	 */
	public void setValueAndStyleGet(int copyrow, int row, int column, String value) {

		Row hrow = sheet.getRow(row);

		if (hrow == null) {
			hrow = copyRow(copyrow, row);
		}

		if (value == null) {
			hrow.getCell(column).setCellValue("");
		} else {
			hrow.getCell(column).setCellValue(value);
		}
	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����i�s�X�^�C���R�s�[�A���t�ݒ�j
	 * 
	 * @param copyrow �R�s�[���s
	 * @param row �R�s�[��s
	 * @param column �w��Z��
	 * @param value �l
	 */
	public void setValueAndStyleGet(int copyrow, int row, int column, Date value) {

		Row hrow = sheet.getRow(row);

		if (hrow == null) {
			hrow = copyRow(copyrow, row);
		}

		if (value == null) {
			hrow.getCell(column).setCellValue("");
		} else {
			hrow.getCell(column).setCellValue(value);
		}
	}

	/**
	 * �w��̃Z���ɒl���Z�b�g����
	 * 
	 * @param row �Ώۍs
	 * @param column �ΏۃZ��
	 * @param value �l
	 */
	public void setValueAndStyleGet(int row, int column, String value) {

		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			hrow = sheet.createRow(row);
		}

		if (value == null) {
			hrow.getCell(column).setCellValue("");
		} else {
			hrow.getCell(column).setCellValue(value);
		}
	}

	/**
	 * �w��͈͂̍s���폜���āA�w��̍s���ړ�����B
	 * 
	 * @param fromRow �폜�J�n�s
	 * @param toRow �폜�I���s
	 * @param shitCount �ړ��s��
	 */
	public void removeRow(int fromRow, int toRow, int shitCount) {

		// �w��͈͂̍s�폜
		for (int i = fromRow; i <= toRow; i++) {
			Row hrow = sheet.getRow(i);
			sheet.removeRow(hrow);
		}

		// �s�폜��̎w��͈͂̍s�ړ��i�폜�s��s���̍s����Ō�s�܂ł��ړ�����j
		sheet.shiftRows(toRow + 1, sheet.getLastRowNum(), shitCount, true, true);
	}

	/**
	 * �s���R�s�[����
	 * 
	 * @param copy �R�s�[��
	 * @param insert �R�s�[��
	 * @return Row
	 */
	public Row copyRow(int copy, int insert) {

		// �R�s�[���̍s�̎擾
		Row copyRow = sheet.getRow(copy);

		// �R�s�[���̍s��ʂ̍s�փR�s�[����
		Row row_ = sheet.createRow(insert);

		// �R�s�[���̍s�̍������R�s�[��̍s�ɐݒ肷��
		row_.setHeight(copyRow.getHeight());

		// �R�s�[���̃Z�������擾����
		int length = copyRow.getLastCellNum();

		// �Z�����R�s�[����
		for (int i = 0; i < length; i++) {

			Cell coypCell = copyRow.getCell(i);
			Cell cell = row_.createCell(i);

			if (coypCell != null) {
				// �l���擾
				switch (coypCell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cell.setCellValue(coypCell.getBooleanCellValue());
						break;

					case Cell.CELL_TYPE_ERROR:
						cell.setCellValue(coypCell.getErrorCellValue());
						break;

					case Cell.CELL_TYPE_FORMULA:
						cell.setCellFormula(coypCell.getCellFormula());
						break;

					case Cell.CELL_TYPE_NUMERIC:
						cell.setCellValue(coypCell.getNumericCellValue());
						break;

					case Cell.CELL_TYPE_STRING:
						cell.setCellValue(coypCell.getRichStringCellValue());
						break;

					case Cell.CELL_TYPE_BLANK:
					default:
						break;
				}

				// �X�^�C�����擾
				cell.setCellStyle(coypCell.getCellStyle());
			}
		}

		return row_;
	}

	/**
	 * �ŏI�s�ԍ�.<br>
	 * �擪�̒l�������s�̓J�E���g���Ȃ�
	 * 
	 * @return �ŏI�s�ԍ�
	 */
	public int getLastRowNum() {

		return sheet.getLastRowNum();
	}

	/**
	 * �s���J�E���g
	 * 
	 * @return �s���J�E���g
	 */
	@SuppressWarnings({ "unused", "hiding" })
	public int getRowCount() {
		int i = 0;
		for (Row row : sheet) {
			i++;
		}

		return i;
	}

	/**
	 * �ėp�l�擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return ������
	 */
	public Object getValue(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);

		if (row_ == null) {
			return null;
		}

		Cell cell_ = row_.getCell(columnIndex);

		if (cell_ == null) {
			return null;
		}

		switch (cell_.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell_.getRichStringCellValue().getString();

			case Cell.CELL_TYPE_BOOLEAN:
				return cell_.getBooleanCellValue();

			case Cell.CELL_TYPE_NUMERIC:
				return cell_.getNumericCellValue();

			case Cell.CELL_TYPE_FORMULA: {
				FormulaEvaluator evaluator = book.workBook.getCreationHelper().createFormulaEvaluator();

				CellValue cellValue = evaluator.evaluate(cell_);
				int cellType = cellValue.getCellType();
				switch (cellType) {
					case Cell.CELL_TYPE_STRING:
						return cell_.getRichStringCellValue().getString();

					case Cell.CELL_TYPE_BOOLEAN:
						return cell_.getBooleanCellValue();

					case Cell.CELL_TYPE_NUMERIC:
						return cell_.getNumericCellValue();
					case Cell.CELL_TYPE_ERROR:
						return cell_.getErrorCellValue();
					case Cell.CELL_TYPE_BLANK:
					default:
						return null;
				}
			}
			case Cell.CELL_TYPE_ERROR:
				return cell_.getErrorCellValue();

			case Cell.CELL_TYPE_BLANK:
			default:
				return null;
		}

	}

	/**
	 * �Z���擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return �Z��
	 */
	public Cell getCell(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);

		if (row_ == null) {
			return null;
		}

		return row_.getCell(columnIndex);

	}

	/**
	 * �Z���擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return �G�N�Z���̃Z��
	 * @throws TException
	 */
	public TExcelCell getExcelCell(String cellNo) throws TException {
		int[] indexs = indexOfCell(cellNo);
		return getExcelCell(indexs[0], indexs[1]);
	}

	/**
	 * �Z���擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return �G�N�Z���̃Z��
	 * @throws TException
	 */
	public TExcelCell getExcelCell(int rowIndex, int columnIndex) throws TException {
		Cell cell = getCell(rowIndex, columnIndex);
		if (cell == null) {
			// I00435:�Z��{0}�����݂��Ă��܂���B
			throw new TException("I00435", toExcelNo(rowIndex, columnIndex));
		}
		return new TExcelCell(book, cell);
	}

	/**
	 * ������擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return ������
	 */
	public String getString(int rowIndex, int columnIndex) {
		return Util.avoidNullNT(getValue(rowIndex, columnIndex));
	}

	/**
	 * ������擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return ������
	 */
	public Date getDate(int rowIndex, int columnIndex) {
		String str = Util.avoidNullNT(getValue(rowIndex, columnIndex));

		if (Util.isNumber(str)) {

			return TExcel.getExcelDate(new BigDecimal(str));
		}

		return null;
	}

	/**
	 * INT�擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return INT
	 */
	public int getInt(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);
		Cell cell = row_.getCell(columnIndex);

		if (cell == null) {
			return 0;
		}

		return (int) cell.getNumericCellValue();
	}

	/**
	 * ���l�擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return ���l
	 */
	public BigDecimal getDecimal(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);
		Cell cell = row_.getCell(columnIndex);

		if (cell == null) {
			return null;
		}

		return BigDecimal.valueOf(cell.getNumericCellValue());
	}

	/**
	 * boolean�擾
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex �J����Index
	 * @return ���l
	 */
	public boolean getBoolean(int rowIndex, int columnIndex) {
		Row row_ = sheet.getRow(rowIndex);
		Cell cell = row_.getCell(columnIndex);

		if (cell == null) {
			return false;
		}

		return cell.getBooleanCellValue();
	}

	/**
	 * �Z���擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return �Z��
	 */
	public Cell getCell(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getCell(indexs[0], indexs[1]);
	}

	/**
	 * ������擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return ������
	 */
	public String getString(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getString(indexs[0], indexs[1]);
	}

	/**
	 * ���t�擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return ������
	 */
	public Date getDate(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getDate(indexs[0], indexs[1]);
	}

	/**
	 * INT�擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return INT
	 */
	public int getInt(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getInt(indexs[0], indexs[1]);
	}

	/**
	 * ���l�擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return ���l
	 */
	public BigDecimal getDecimal(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getDecimal(indexs[0], indexs[1]);
	}

	/**
	 * boolean�擾(A1�����w��)
	 * 
	 * @param cellNo �Z���ԍ�
	 * @return ���l
	 */
	public boolean getBoolean(String cellNo) {
		int[] indexs = indexOfCell(cellNo);
		return getBoolean(indexs[0], indexs[1]);
	}

	/**
	 * A1�����L�q����Index�ԍ�����肷��
	 * 
	 * @param cellNo �Z���ԍ�(A1����)
	 * @return Index 0:�s�A0:��
	 */
	public static int[] indexOfCell(String cellNo) {
		int rowIndex = 0;
		int columnIndex = 0;

		if (Util.isNumber(String.valueOf(cellNo.charAt(1)))) {
			// A1�`Z1
			String rowChar = cellNo.substring(1, cellNo.length());
			char columnChar = cellNo.charAt(0);

			rowIndex = Integer.parseInt(rowChar) - 1;
			columnIndex = Character.getNumericValue(columnChar) - 10;

		} else {
			// AA1�`
			String rowChar = cellNo.substring(2, cellNo.length());
			char columnChar1 = cellNo.charAt(0);
			char columnChar2 = cellNo.charAt(1);

			rowIndex = Integer.parseInt(rowChar) - 1;
			columnIndex = (Character.getNumericValue(columnChar1) - 9) * 26;
			columnIndex += Character.getNumericValue(columnChar2) - 10;
		}

		return new int[] { rowIndex, columnIndex };
	}

	/**
	 * �G�N�Z���̃Z���ԍ��̃R���o�[�g<br>
	 * ��)�Drow=2,col=91��cell=CN3<br>
	 * row<0�Acol<0�Acol>255�̏ꍇ�Anull�߂�
	 * 
	 * @param row �s�ԍ�
	 * @param col ��ԍ�
	 * @return �G�N�Z���̃Z���ԍ�
	 */
	public static String toExcelNo(int row, int col) {

		if (row < 0 || col < 0) {
			return null;
		}

		if (col > 255) {
			return null;
		}

		String cellNo = "";
		char a = 'A';

		if (col < 26) {
			cellNo = String.valueOf((char) (a + col));
		} else {
			int b = col % 26;
			int c = (col - b) / 26;
			cellNo = String.valueOf((char) (a + c - 1)) + String.valueOf((char) (a + b));
		}
		cellNo = cellNo + (row + 1);

		return cellNo;
	}

	/**
	 * �w��̃Z���ɃX�^�C�����Z�b�g����.
	 * 
	 * @param cellRow �s�ԍ�
	 * @param cellColumn ��ԍ�
	 * @return style �X�^�C��
	 */
	public TCellStyle getTCellStyle(int cellRow, int cellColumn) {
		Cell cell = getCell(cellRow, cellColumn);

		if (cell == null) {
			Row row_ = sheet.getRow(cellRow);
			cell = row_.createCell(cellColumn);
		}

		return new TCellStyle(cell.getCellStyle());
	}

	/**
	 * �V�[�g�Y�[���̐ݒ�
	 * 
	 * @param numerator ���q
	 * @param denominator ����
	 */
	public void setZoom(int numerator, int denominator) {
		sheet.setZoom(numerator, denominator);
	}

	/**
	 * �V�[�g�̑I��ݒ�
	 * 
	 * @param sel true:�I��
	 */
	public void setSelected(boolean sel) {
		sheet.setSelected(sel);
	}

	/**
	 * �Z������������
	 * 
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2
	 */
	public void addMergedRegion(int row1, int column1, int row2, int column2) {
		sheet.addMergedRegion(new CellRangeAddress(row1, row2, column1, column2));
	}

	/**
	 * �y�[�W�ݒ�F�w�肳�ꂽ���Əc�y�[�W���ɍ��킹�Ď������y�[�W�ɂ���
	 */
	public void setAutobreaksPrint() {
		// �������y�[�W���[�h
		sheet.setAutobreaks(true);
	}

	/**
	 * �u���̃y�[�W�ɍ��킹�Ĉ���v�I�v�V������ON�iXLSX�̏ꍇ�K�{�j
	 */
	public void setFitToPage() {
		sheet.setFitToPage(true);
	}

	/**
	 * �y�[�W�ݒ�F���킹�Ĉ���ꍇ�̉�
	 * 
	 * @param height ��
	 */
	public void setFitHeight(int height) {
		sheet.getPrintSetup().setFitHeight((short) height);
	}

	/**
	 * �y�[�W�ݒ�F���킹�Ĉ���ꍇ�̏c
	 * 
	 * @param width �c
	 */
	public void setFitWidth(int width) {
		sheet.getPrintSetup().setFitWidth((short) width);
	}

	/**
	 * �y�[�W�ݒ�F�y�[�W�����F�����Ƀ`�F�b�N
	 */
	public void setHorizontallyCenterPrint() {
		// �����Ƀ`�F�b�N����
		sheet.setHorizontallyCenter(true);
	}

	/**
	 * �y�[�W�ݒ�F�y�[�W�����F�����Ƀ`�F�b�N
	 */
	public void setVerticallyCenterPrint() {
		// �����Ƀ`�F�b�N����
		sheet.setVerticallyCenter(true);
	}

}
