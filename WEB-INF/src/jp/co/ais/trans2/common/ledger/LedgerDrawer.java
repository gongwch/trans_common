package jp.co.ais.trans2.common.ledger;

import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���[�`��N���X�ł��B<br>
 * �`��֘A�̏������s���܂��B
 * 
 * @author AIS
 */
public class LedgerDrawer {

	/** document */
	protected JCDocument document;

	/** nonlineStyle */
	protected JCDrawStyle nonlineStyle;

	/** ledgerWidth */
	protected JCUnit.Measure ledgerWidth;

	/**
	 * @return ���[����
	 */
	public double getLedgerDoubleWidth() {
		return ledgerWidth.getAs(JCUnit.CM);
	}

	/**
	 * @param document
	 */
	public LedgerDrawer(JCDocument document) {
		this.document = document;
		nonlineStyle = (JCDrawStyle) JCDrawStyle.BLANK.clone();
		reflesh();
	}

	/**
	 * 
	 */
	public void reflesh() {

		// -0.1���x���������Ȃ��ƃe�[�u�����������s���Ă��܂��BJClass�̏�Q�H
		ledgerWidth = new JCUnit.Measure(JCUnit.CM, document.getFlow().getCurrentFrame().getSize().getWidth()
			.getAs(JCUnit.CM) - 0.1d);
	}

	/**
	 * �e�L�X�g�̕`��
	 * 
	 * @param style
	 * @param text
	 * @param point
	 * @throws TException
	 */
	public void drawTextAt(JCTextStyle style, String text, JCUnit.Point point) throws TException {

		try {
			JCFrame frame = document.getFlow().getCurrentFrame();
			frame.setInsertionPoint(point);
			frame.print(style, text);
		} catch (Exception ex) {
			throw new TException(ex);
		}

	}

	/**
	 * �e�L�X�g�̕`��
	 * 
	 * @param style
	 * @param text
	 * @param x
	 * @param y
	 * @throws TException
	 */
	public void drawTextAt(JCTextStyle style, String text, double x, double y) throws TException {
		drawTextAt(style, text, new JCUnit.Point(JCUnit.CM, x, y));
	}

	/**
	 * �e�L�X�g�̕`��<br>
	 * (�|�C���g��Y���W�����F�����Ȃ��BX���W���w�肷��ꍇ��drawTextAt���g�p���邱��)
	 * 
	 * @param style
	 * @param text
	 * @param point
	 * @throws TException
	 */
	public void drawText(JCTextStyle style, String text, JCUnit.Point point) throws TException {

		try {

			JCFrame frame = document.getFlow().getCurrentFrame();
			JCPageTable jcPageTable = new JCPageTable(document, 1, ledgerWidth);
			jcPageTable.getCellFrame(0, 0).print(style, Util.avoidNullNT(text));
			jcPageTable.setAllBorders(nonlineStyle);
			frame.setInsertionPoint(point);
			frame.print(jcPageTable);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �e�L�X�g�̕`��<br>
	 * (�|�C���g��Y���W�����F�����Ȃ��BX���W���w�肷��ꍇ��drawTextAt���g�p���邱��)
	 * 
	 * @param style
	 * @param text
	 * @param x
	 * @param y
	 * @throws TException
	 */
	public void drawText(JCTextStyle style, String text, double x, double y) throws TException {
		drawText(style, text, new JCUnit.Point(JCUnit.CM, x, y));
	}

	/**
	 * �e�L�X�g�̕`��
	 * 
	 * @param cellTable
	 * @param x
	 * @param y
	 * @throws TException
	 */
	public void drawText(LedgerCellTable cellTable, double x, double y) throws TException {
		drawText(cellTable, new JCUnit.Point(JCUnit.CM, x, y));
	}

	/**
	 * �e�L�X�g�̕`��
	 * 
	 * @param cellTable
	 * @param point
	 * @throws TException
	 */
	public void drawText(LedgerCellTable cellTable, JCUnit.Point point) throws TException {

		try {

			JCFrame frame = document.getFlow().getCurrentFrame();
			JCPageTable jcPageTable = new JCPageTable(document, cellTable.getSize(), ledgerWidth);

			List<LedgerCell> cellList = cellTable.getCellList();

			for (int i = 0; i < cellList.size(); i++) {
				LedgerCell cell = cellList.get(i);
				if (cell.getWidth() == LedgerCell.WIDTH_REST) {

					jcPageTable.setColumnWidth(i, new JCUnit.Measure(JCUnit.CM, ledgerWidth.getAs(JCUnit.CM)
						- cellTable.getTotalWidthWithoutRest()));
				} else {
					jcPageTable.setColumnWidth(i, new JCUnit.Measure(JCUnit.CM, cell.getWidth()));
				}

				jcPageTable.getCellFrame(0, i).print(cell.getStyle(), Util.avoidNullNT(cell.getValue()));
			}

			jcPageTable.setAllBorders(nonlineStyle);

			frame.setInsertionPoint(point);

			// ���������邽�߂ɂ�print���\�b�h���H�v����B(�������d���B�����s��x�ɕ`�悵�Ă��d��)
			frame.print(jcPageTable);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * ����`�悷��
	 * 
	 * @param style
	 * @param pointFrom
	 * @param pointTo
	 * @throws TException
	 */
	public void drawLine(JCDrawStyle style, JCUnit.Point pointFrom, JCUnit.Point pointTo) throws TException {

		try {

			JCFrame frame = document.getFlow().getCurrentFrame();
			frame.drawLine(style, pointFrom, pointTo);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * ����`�悷��
	 * 
	 * @param style
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 * @throws TException
	 */
	public void drawLine(JCDrawStyle style, double fromX, double fromY, double toX, double toY) throws TException {

		drawLine(style, new JCUnit.Point(JCUnit.CM, fromX, fromY), new JCUnit.Point(JCUnit.CM, toX, toY));

	}

	/**
	 * �c����`�悷��
	 * 
	 * @param style
	 * @param x x���W
	 * @param fromY Y���W�N�_
	 * @param toY Y���W�I�_
	 * @throws TException
	 */
	public void drawVerticalLine(JCDrawStyle style, double x, double fromY, double toY) throws TException {

		drawLine(style, new JCUnit.Point(JCUnit.CM, x, fromY), new JCUnit.Point(JCUnit.CM, x, toY));

	}

	/**
	 * ������`�悷��
	 * 
	 * @param style
	 * @param y Y���W
	 * @param fromX X���W�N�_
	 * @param toX X���W�I�_
	 * @throws TException
	 */
	public void drawHorizontalLine(JCDrawStyle style, double y, double fromX, double toX) throws TException {

		drawLine(style, new JCUnit.Point(JCUnit.CM, fromX, y), new JCUnit.Point(JCUnit.CM, toX, y));

	}

	/**
	 * �g����`�悷��
	 * 
	 * @param style
	 * @param pointFrom
	 * @param pointTo
	 */
	public void drawFrameBorder(JCDrawStyle style, JCUnit.Point pointFrom, JCUnit.Point pointTo) {

		JCFrame frame = document.getFlow().getCurrentFrame();

		frame.drawLine(style, pointFrom, new JCUnit.Point(pointTo.xInt(), pointFrom.yInt()));
		frame.drawLine(style, pointFrom, new JCUnit.Point(pointFrom.xInt(), pointTo.yInt()));
		frame.drawLine(style, new JCUnit.Point(pointTo.xInt(), pointFrom.yInt()), pointTo);
		frame.drawLine(style, new JCUnit.Point(pointFrom.xInt(), pointTo.yInt()), pointTo);

	}

	/**
	 * �g����`�悷��
	 * 
	 * @param style
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 */
	public void drawFrameBorder(JCDrawStyle style, double fromX, double fromY, double toX, double toY) {

		drawFrameBorder(style, new JCUnit.Point(JCUnit.CM, fromX, fromY), new JCUnit.Point(JCUnit.CM, toX, toY));

	}

	/**
	 * �l�p��`�悵�A�w��F�œh��Ԃ�
	 * 
	 * @param style
	 * @param pointFrom
	 * @param pointTo
	 */
	public void drawRectangles(JCDrawStyle style, JCUnit.Point pointFrom, JCUnit.Point pointTo) {

		JCFrame frame = document.getFlow().getCurrentFrame();
		frame.fillRectangle(style, pointFrom, pointTo);

	}

	/**
	 * �l�p��`�悵�A�w��F�œh��Ԃ�
	 * 
	 * @param style
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 */
	public void drawRectangles(JCDrawStyle style, double fromX, double fromY, double toX, double toY) {
		drawRectangles(style, new JCUnit.Point(JCUnit.CM, fromX, fromY), new JCUnit.Point(JCUnit.CM, toX, toY));
	}

	/**
	 * ����`�悷��
	 * 
	 * @param style
	 * @param pointFrom
	 * @param measure
	 */
	public void drawCircle(JCDrawStyle style, JCUnit.Point pointFrom, JCUnit.Measure measure) {

		JCFrame frame = document.getFlow().getCurrentFrame();

		frame.drawCircle(style, pointFrom, measure);

	}

	/**
	 * �w��̃o�C�g���Ő؂���������������X�g�ŕԂ��܂��B<br>
	 * �y�g�p��z<br>
	 * getParagraphString("��������������", 6, 4) �̏ꍇ<br>
	 * �߂�l��List�̗v�f0�ɂ͂U�o�C�g����"������"�A�v�f1�ɂ�"������", �v�f2�ɂ�"��"�A�v�f3�ɂ�""������܂��B
	 * 
	 * @param arg ������
	 * @param byteNo �o�C�g��
	 * @param paragraphNo �i��
	 * @return �w��o�C�g���ŋ�؂�ꂽ������
	 */
	@Deprecated
	public static List<String> getParagraphString(String arg, int byteNo, int paragraphNo) {
		// TODO ��A�Ŏg�p���Ă������߁A�ꎞ�I�ɕ���
		if (paragraphNo == 0) {
			return null;
		}

		String rt = Util.avoidNullNT(arg);

		List<String> rtList = new ArrayList<String>();

		for (int i = 0; i < paragraphNo; i++) {

			// �؂�����������ǉ�
			rtList.add(Util.avoidNullNT(StringUtil.leftBX(rt, byteNo)));

			// �؂�������̕������Z�b�g
			rt = StringUtil.rightBX(rt, rt.getBytes().length - StringUtil.leftBX(rt, byteNo).getBytes().length);

		}

		return rtList;
	}

}
