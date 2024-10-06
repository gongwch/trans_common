package jp.co.ais.trans2.common.ledger;

import java.math.*;
import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���[�̃Z���e�[�u��
 * 
 * @author AIS
 */
public class LedgerCellTable {

	/** �E */
	public static int ALIGN_RIGHT = 0;

	/** ���� */
	public static int ALIGN_CENTER = 1;

	/** �� */
	public static int ALIGN_LEFT = 2;

	/** �Z�����X�g */
	protected List<LedgerCell> cellList;

	/** �A���C�� */
	protected int align;

	/**
	 * �R���X�g���N�^.
	 */
	public LedgerCellTable() {
		cellList = new ArrayList<LedgerCell>();
		align = ALIGN_CENTER;
	}

	/**
	 * �Z����ǉ�
	 * 
	 * @param cell
	 */
	public void addCell(LedgerCell cell) {
		cellList.add(cell);
	}

	/**
	 * �Z����ǉ�(�l�u�����N)
	 * 
	 * @param style
	 * @param width
	 */
	public void addCell(JCTextStyle style, double width) {
		cellList.add(new LedgerCell(style, width, ""));
	}

	/**
	 * �Z����ǉ�
	 * 
	 * @param style
	 * @param width
	 * @param value
	 */
	public void addCell(JCTextStyle style, double width, String value) {
		cellList.add(new LedgerCell(style, width, value));
	}

	/**
	 * �Z����ǉ�(�w��o�C�g���Ő؂�̂�)
	 * 
	 * @param style
	 * @param width
	 * @param value
	 * @param bytes
	 */
	public void addCell(JCTextStyle style, double width, String value, int bytes) {
		cellList.add(new LedgerCell(style, width, StringUtil.leftBX(value, bytes)));
	}

	/**
	 * �Z����ǉ�
	 * 
	 * @param style
	 * @param width
	 * @param value
	 * @param decimalPoint
	 */
	public void addCell(JCTextStyle style, double width, BigDecimal value, int decimalPoint) {
		cellList.add(new LedgerCell(style, width, value, decimalPoint));
	}

	/**
	 * �Z���̗v�f����Ԃ�
	 * 
	 * @return �v�f��
	 */
	public int getSize() {
		return cellList.size();
	}

	/**
	 * �Z���̃��X�g��Ԃ�
	 * 
	 * @return �Z���̃��X�g
	 */
	public List<LedgerCell> getCellList() {
		return cellList;
	}

	/**
	 * �ʒu
	 * 
	 * @return �ʒu
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * �ʒu
	 * 
	 * @param align �ʒu
	 */
	public void setAlign(int align) {
		this.align = align;
	}

	/**
	 * �c�蕪�����������g�[�^����Ԃ�
	 * 
	 * @return ���g�[�^��
	 */
	public double getTotalWidthWithoutRest() {
		double rt = 0.0;
		for (LedgerCell bean : cellList) {
			if (bean.getWidth() != LedgerCell.WIDTH_REST) {
				rt = rt + bean.getWidth();
			}
		}
		return rt;
	}
}
