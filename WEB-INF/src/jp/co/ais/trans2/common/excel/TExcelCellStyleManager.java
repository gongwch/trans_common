package jp.co.ais.trans2.common.excel;

import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.config.*;

/**
 * �G�N�Z���̃Z���X�^�C�����Ǘ�����
 * 
 * @author AIS
 */
public abstract class TExcelCellStyleManager {

	/** Workbook */
	protected Workbook workBook;

	/** �G�N�Z���̃t�H���g�T�C�Y(�f�t�H���g) */
	protected static final short DEFAULT_FONT_SIZE = 11;

	/** ���[�^�C�g���̃t�H�[�}�b�g */
	protected CellStyle headStyle;

	/** ���[�^�C�g���̍��񂹃t�H�[�}�b�g */
	protected CellStyle headLeftStyle;

	/** �Z���X�^�C��(����) */
	protected CellStyle leftStyle;

	/** �Z���X�^�C��(�E��) */
	protected CellStyle rightStyle;

	/** �Z���X�^�C��(����) */
	protected CellStyle centerStyle;

	/** �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒����񂹁A����&�T�C�Y+4) */
	protected CellStyle centerStyleBoldNoneBorder;

	/** �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒�����) */
	protected CellStyle centerStyleNoneBorder;

	/** �Z���X�^�C��(�����A�{�[�_�[����A�I��͈͒�����) */
	protected CellStyle centerStyleBorder;

	/** �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�) */
	protected CellStyle leftStyleNoneBorder;

	/** �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�) */
	protected CellStyle rightStyleNoneBorder;

	/** �Z���X�^�C��(���t) */
	protected CellStyle dateCellStyle;

	/** �Z���X�^�C��(�C�ӂ̌^) */
	protected Map<String, CellStyle> stylesMap = new TreeMap<String, CellStyle>();

	/** �Z���X�^�C��(���l) */
	protected Map<String, CellStyle> decimalStyles = new TreeMap<String, CellStyle>();

	/** �e���v���[�g�G�N�Z������쐬�����Z���X�^�C�� */
	protected Map<String, CellStyle> templateStyles = new TreeMap<String, CellStyle>();

	/** �t�H���g */
	protected String font;

	/** �t�H���g */
	protected String languageCode;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param workBook
	 * @param languageCode ����R�[�h
	 */
	public TExcelCellStyleManager(Workbook workBook, String languageCode) {
		this.workBook = workBook;
		this.languageCode = languageCode;
		this.font = ReportConfig.getExcelFontName(getLanguageCode());

		initStyle();
	}

	/**
	 * �Z���X�^�C���𐶐�����
	 * 
	 * @return �Z���X�^�C��
	 */
	public CellStyle createCellStyle() {
		return workBook.createCellStyle();
	}

	/**
	 * �u�k�����đS�̂�\������v�̃Z���X�^�C���𐶐�����
	 * 
	 * @return �u�k�����đS�̂�\������v�̃Z���X�^�C��
	 */
	public CellStyle createShrinkToFitCellStyle() {
		CellStyle style = createCellStyle();
		setShrinkToFit(style);
		return style;
	}

	/**
	 * �u�k�����đS�̂�\������v�̐ݒ�
	 * 
	 * @param style �Z���X�^�C��
	 */
	public abstract void setShrinkToFit(CellStyle style);

	/**
	 * DataFormat�𐶐�����
	 * 
	 * @return DataFormat
	 */
	public DataFormat createDataFormat() {
		return workBook.createDataFormat();
	}

	/**
	 * �X�^�C��������������
	 */
	protected void initStyle() {

		// �w�b�_�[�̃X�^�C��
		this.headStyle = workBook.createCellStyle();
		this.headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.headStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headStyle.setFont(this.getFontStyle());

		// �w�b�_�[�̍��񂹃X�^�C��
		this.headLeftStyle = workBook.createCellStyle();
		this.headLeftStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.headLeftStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.headLeftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.headLeftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.headLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.headLeftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.headLeftStyle.setFont(this.getFontStyle());

		// ���Ԃ̃X�^�C��
		this.centerStyle = workBook.createCellStyle();
		this.centerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.centerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.centerStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyle.setFont(this.getFontStyle());

		// �����̃X�^�C��
		this.leftStyle = workBook.createCellStyle();
		this.leftStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.leftStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.leftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		this.leftStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.leftStyle.setFont(this.getFontStyle());

		// �E���̃X�^�C��
		this.rightStyle = workBook.createCellStyle();
		this.rightStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.rightStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		this.rightStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.rightStyle.setFont(this.getFontStyle());

		// ���Ԃ̃X�^�C��(�{�[�_�[�Ȃ��A�I��͈͒����񂹁A����)
		this.centerStyleBoldNoneBorder = workBook.createCellStyle();
		this.centerStyleBoldNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.centerStyleBoldNoneBorder.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		this.centerStyleBoldNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		Font boldFont = this.getFontStyle((short) (DEFAULT_FONT_SIZE + 4));
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		this.centerStyleBoldNoneBorder.setFont(boldFont);

		// ���Ԃ̃X�^�C��(�{�[�_�[�Ȃ��A�I��͈͒�����)
		this.centerStyleNoneBorder = workBook.createCellStyle();
		this.centerStyleNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.centerStyleNoneBorder.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		this.centerStyleNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyleNoneBorder.setFont(this.getFontStyle());

		// ���Ԃ̃X�^�C��(�{�[�_�[����A�I��͈͒�����)
		this.centerStyleBorder = workBook.createCellStyle();
		this.centerStyleBorder.setFillPattern(CellStyle.SOLID_FOREGROUND);
		this.centerStyleBorder.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.centerStyleBorder.setBorderLeft(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setBorderRight(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setBorderTop(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setBorderBottom(CellStyle.BORDER_THIN);
		this.centerStyleBorder.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		this.centerStyleBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.centerStyleBorder.setFont(this.getFontStyle());

		// �����̃X�^�C��(�{�[�_�[�Ȃ�)
		this.leftStyleNoneBorder = workBook.createCellStyle();
		this.leftStyleNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.leftStyleNoneBorder.setAlignment(CellStyle.ALIGN_LEFT);
		this.leftStyleNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.leftStyleNoneBorder.setFont(this.getFontStyle());

		// �E���̃X�^�C��(�{�[�_�[�Ȃ�)
		this.rightStyleNoneBorder = workBook.createCellStyle();
		this.rightStyleNoneBorder.setBorderLeft(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setBorderRight(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setBorderTop(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setBorderBottom(CellStyle.BORDER_NONE);
		this.rightStyleNoneBorder.setAlignment(CellStyle.ALIGN_RIGHT);
		this.rightStyleNoneBorder.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.rightStyleNoneBorder.setFont(this.getFontStyle());

		// ���t�`���Z���쐬
		this.dateCellStyle = workBook.createCellStyle();
		this.dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		this.dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		this.dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		this.dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		this.dateCellStyle.setFont(this.getFontStyle());

		// �e���v���[�g�G�N�Z���̃Z���X�^�C���̏�����
		clearTemplateStyles();

		// �^�C�v�ʐݒ�
		initStyleByType();
	}

	/**
	 * �X�^�C���ʐݒ�
	 */
	protected abstract void initStyleByType();

	/**
	 * Excel�p�f�t�H���g�t�H���g�X�^�C��
	 * 
	 * @return �t�H���g�X�^�C��
	 */
	public Font getFontStyle() {
		return getFontStyle(DEFAULT_FONT_SIZE);
	}

	/**
	 * Excel�p�t�H���g�X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �t�H���g�X�^�C��
	 */
	public Font getFontStyle(short fontSize) {
		Font font_ = this.workBook.createFont();
		font_.setFontName(this.font);
		font_.setFontHeightInPoints(fontSize);

		return font_;
	}

	/**
	 * ����R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * �w�b�_�[�Z���X�^�C��
	 * 
	 * @return �w�b�_�[�Z���X�^�C��
	 */
	public CellStyle getHeadStyle() {
		return headStyle;
	}

	/**
	 * �w�b�_�[�Z���X�^�C��
	 * 
	 * @param headStyle �w�b�_�[�Z���X�^�C��
	 */
	public void setHeadStyle(CellStyle headStyle) {
		this.headStyle = headStyle;
	}

	/**
	 * ���[�^�C�g���̍��񂹃t�H�[�}�b�g�̎擾
	 * 
	 * @return headLeftStyle ���[�^�C�g���̍��񂹃t�H�[�}�b�g
	 */
	public CellStyle getHeadLeftStyle() {
		return headLeftStyle;
	}

	/**
	 * ���[�^�C�g���̍��񂹃t�H�[�}�b�g�̐ݒ�
	 * 
	 * @param headLeftStyle ���[�^�C�g���̍��񂹃t�H�[�}�b�g
	 */
	public void setHeadLeftStyle(CellStyle headLeftStyle) {
		this.headLeftStyle = headLeftStyle;
	}

	/**
	 * �����Z���X�^�C��
	 * 
	 * @return �����Z���X�^�C��
	 */
	public CellStyle getCenterStyle() {
		return centerStyle;
	}

	/**
	 * �����Z���X�^�C��
	 * 
	 * @param centerStyle �����Z���X�^�C��
	 */
	public void setCenterStyle(CellStyle centerStyle) {
		this.centerStyle = centerStyle;
	}

	/**
	 * ���t�Z���X�^�C��
	 * 
	 * @return ���t�Z���X�^�C��
	 */
	public CellStyle getDateCellStyle() {
		return dateCellStyle;
	}

	/**
	 * ���t�Z���X�^�C��
	 * 
	 * @param dateCellStyle ���t�Z���X�^�C��
	 */
	public void setDateCellStyle(CellStyle dateCellStyle) {
		this.dateCellStyle = dateCellStyle;
	}

	/**
	 * ���Z���X�^�C��
	 * 
	 * @return ���Z���X�^�C��
	 */
	public CellStyle getLeftStyle() {
		return leftStyle;
	}

	/**
	 * ���Z���X�^�C��
	 * 
	 * @param leftStyle ���Z���X�^�C��
	 */
	public void setLeftStyle(CellStyle leftStyle) {
		this.leftStyle = leftStyle;
	}

	/**
	 * �E�Z���X�^�C��
	 * 
	 * @return �E�Z���X�^�C��
	 */
	public CellStyle getRightStyle() {
		return rightStyle;
	}

	/**
	 * �E�Z���X�^�C��
	 * 
	 * @param rightStyle �E�Z���X�^�C��
	 */
	public void setRightStyle(CellStyle rightStyle) {
		this.rightStyle = rightStyle;
	}

	/**
	 * ���l�Z���X�^�C��
	 * 
	 * @return ���l�Z���X�^�C��
	 */
	public Map<String, CellStyle> getDecimalStyles() {
		return decimalStyles;
	}

	/**
	 * �C�ӂ̕��̃Z���X�^�C��Map<br>
	 * ���l�n�̃X�^�C���ɂ����Ă�getDecimalStyles�𗘗p
	 * 
	 * @return �Z���X�^�C���̃}�b�v
	 */
	public Map<String, CellStyle> getStyleMap() {
		return stylesMap;
	}

	/**
	 * �e���v���[�g�G�N�Z���̃Z���X�^�C���̏�����
	 */
	public void clearTemplateStyles() {
		this.templateStyles.clear();
	}

	/**
	 * �e���v���[�g�G�N�Z���̃Z���X�^�C���̎擾
	 * 
	 * @param template �Z��Enum
	 * @return dtlLeft �Z���X�^�C��
	 */
	public CellStyle getTemplateStyle(Enum template) {
		return templateStyles.get(template.name());
	}

	/**
	 * �e���v���[�g�G�N�Z���̃Z���X�^�C���̐ݒ�
	 * 
	 * @param template �Z��Enum
	 * @param style �Z���X�^�C��
	 */
	public void setTemplateStyle(Enum template, CellStyle style) {
		this.templateStyles.put(template.name(), style);
	}

	/**
	 * �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒����񂹁A����&�T�C�Y+4)�̎擾
	 * 
	 * @return centerStyleBoldNoneBorder �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒����񂹁A����&�T�C�Y+4)
	 */
	public CellStyle getCenterStyleBoldNoneBorder() {
		return centerStyleBoldNoneBorder;
	}

	/**
	 * �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒����񂹁A����&�T�C�Y+4)�̐ݒ�
	 * 
	 * @param centerStyleBoldNoneBorder �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒����񂹁A����&�T�C�Y+4)
	 */
	public void setCenterStyleBoldNoneBorder(CellStyle centerStyleBoldNoneBorder) {
		this.centerStyleBoldNoneBorder = centerStyleBoldNoneBorder;
	}

	/**
	 * �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒�����)�̎擾
	 * 
	 * @return centerStyleNoneBorder �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒�����)
	 */
	public CellStyle getCenterStyleNoneBorder() {
		return centerStyleNoneBorder;
	}

	/**
	 * �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒�����)�̐ݒ�
	 * 
	 * @param centerStyleNoneBorder �Z���X�^�C��(�����A�{�[�_�[�Ȃ��A�I��͈͒�����)
	 */
	public void setCenterStyleNoneBorder(CellStyle centerStyleNoneBorder) {
		this.centerStyleNoneBorder = centerStyleNoneBorder;
	}

	/**
	 * �Z���X�^�C��(�����A�{�[�_�[����A�I��͈͒�����)�̎擾
	 * 
	 * @return centerStyleBorder �Z���X�^�C��(�����A�{�[�_�[����A�I��͈͒�����)
	 */
	public CellStyle getCenterStyleBorder() {
		return centerStyleBorder;
	}

	/**
	 * �Z���X�^�C��(�����A�{�[�_�[����A�I��͈͒�����)�̐ݒ�
	 * 
	 * @param centerStyleBorder �Z���X�^�C��(�����A�{�[�_�[����A�I��͈͒�����)
	 */
	public void setCenterStyleBorder(CellStyle centerStyleBorder) {
		this.centerStyleBorder = centerStyleBorder;
	}

	/**
	 * �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)�̎擾
	 * 
	 * @return leftStyleNoneBorder �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)
	 */
	public CellStyle getLeftStyleNoneBorder() {
		return leftStyleNoneBorder;
	}

	/**
	 * �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)�̐ݒ�
	 * 
	 * @param leftStyleNoneBorder �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)
	 */
	public void setLeftStyleNoneBorder(CellStyle leftStyleNoneBorder) {
		this.leftStyleNoneBorder = leftStyleNoneBorder;
	}

	/**
	 * �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)�̎擾
	 * 
	 * @return rightStyleNoneBorder �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)
	 */
	public CellStyle getRightStyleNoneBorder() {
		return rightStyleNoneBorder;
	}

	/**
	 * �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)�̐ݒ�
	 * 
	 * @param rightStyleNoneBorder �Z���X�^�C��(���񂹁A�{�[�_�[�Ȃ�)
	 */
	public void setRightStyleNoneBorder(CellStyle rightStyleNoneBorder) {
		this.rightStyleNoneBorder = rightStyleNoneBorder;
	}

}
