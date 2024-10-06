package jp.co.ais.trans2.common.ledger;

import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 帳票描画クラスです。<br>
 * 描画関連の処理を行います。
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
	 * @return 帳票横幅
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

		// -0.1程度幅調整しないとテーブルが自動改行してしまう。JClassの障害？
		ledgerWidth = new JCUnit.Measure(JCUnit.CM, document.getFlow().getCurrentFrame().getSize().getWidth()
			.getAs(JCUnit.CM) - 0.1d);
	}

	/**
	 * テキストの描画
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
	 * テキストの描画
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
	 * テキストの描画<br>
	 * (ポイントはY座標しか認識しない。X座標も指定する場合はdrawTextAtを使用すること)
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
	 * テキストの描画<br>
	 * (ポイントはY座標しか認識しない。X座標も指定する場合はdrawTextAtを使用すること)
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
	 * テキストの描画
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
	 * テキストの描画
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

			// 高速化するためにはprintメソッドを工夫する。(ここが重い。複数行一度に描画しても重い)
			frame.print(jcPageTable);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * 線を描画する
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
	 * 線を描画する
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
	 * 縦線を描画する
	 * 
	 * @param style
	 * @param x x座標
	 * @param fromY Y座標起点
	 * @param toY Y座標終点
	 * @throws TException
	 */
	public void drawVerticalLine(JCDrawStyle style, double x, double fromY, double toY) throws TException {

		drawLine(style, new JCUnit.Point(JCUnit.CM, x, fromY), new JCUnit.Point(JCUnit.CM, x, toY));

	}

	/**
	 * 横線を描画する
	 * 
	 * @param style
	 * @param y Y座標
	 * @param fromX X座標起点
	 * @param toX X座標終点
	 * @throws TException
	 */
	public void drawHorizontalLine(JCDrawStyle style, double y, double fromX, double toX) throws TException {

		drawLine(style, new JCUnit.Point(JCUnit.CM, fromX, y), new JCUnit.Point(JCUnit.CM, toX, y));

	}

	/**
	 * 枠線を描画する
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
	 * 枠線を描画する
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
	 * 四角を描画し、指定色で塗りつぶす
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
	 * 四角を描画し、指定色で塗りつぶす
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
	 * ○を描画する
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
	 * 指定のバイト数で切り取った文字列をリストで返します。<br>
	 * 【使用例】<br>
	 * getParagraphString("あいうえおかき", 6, 4) の場合<br>
	 * 戻り値のListの要素0には６バイト分の"あいう"、要素1には"えおか", 要素2には"き"、要素3には""が入ります。
	 * 
	 * @param arg 文字列
	 * @param byteNo バイト数
	 * @param paragraphNo 段数
	 * @return 指定バイト数で区切られた文字列
	 */
	@Deprecated
	public static List<String> getParagraphString(String arg, int byteNo, int paragraphNo) {
		// TODO 大連で使用していたため、一時的に復活
		if (paragraphNo == 0) {
			return null;
		}

		String rt = Util.avoidNullNT(arg);

		List<String> rtList = new ArrayList<String>();

		for (int i = 0; i < paragraphNo; i++) {

			// 切り取った文字を追加
			rtList.add(Util.avoidNullNT(StringUtil.leftBX(rt, byteNo)));

			// 切り取った後の文字をセット
			rt = StringUtil.rightBX(rt, rt.getBytes().length - StringUtil.leftBX(rt, byteNo).getBytes().length);

		}

		return rtList;
	}

}
