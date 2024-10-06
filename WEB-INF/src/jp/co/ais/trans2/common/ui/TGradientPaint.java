package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.geom.*;

import jp.co.ais.trans2.common.config.*;

/**
 * グラデーション描画
 */
public class TGradientPaint extends GradientPaint {

	/** true:平面UI */
	public static final boolean isFlat = ClientConfig.isFlagOn("trans.flat.ui");

	/**
	 * コンストラクター
	 * 
	 * @param x1
	 * @param y1
	 * @param color1
	 * @param x2
	 * @param y2
	 * @param color2
	 */
	public TGradientPaint(float x1, float y1, Color color1, float x2, float y2, Color color2) {
		super(x1, y1, isFlat ? color2 : color1, x2, y2, color2);
	}

	/**
	 * コンストラクター
	 * 
	 * @param pt1
	 * @param color1
	 * @param pt2
	 * @param color2
	 */
	public TGradientPaint(Point2D pt1, Color color1, Point2D pt2, Color color2) {
		super(pt1, isFlat ? color2 : color1, pt2, color2);
	}

	/**
	 * コンストラクター
	 * 
	 * @param x1
	 * @param y1
	 * @param color1
	 * @param x2
	 * @param y2
	 * @param color2
	 * @param cyclic
	 */
	public TGradientPaint(float x1, float y1, Color color1, float x2, float y2, Color color2, boolean cyclic) {
		super(x1, y1, isFlat ? color2 : color1, x2, y2, color2, cyclic);
	}

	/**
	 * コンストラクター
	 * 
	 * @param pt1
	 * @param color1
	 * @param pt2
	 * @param color2
	 * @param cyclic
	 */
	public TGradientPaint(Point2D pt1, Color color1, Point2D pt2, Color color2, boolean cyclic) {
		super(pt1, isFlat ? color2 : color1, pt2, color2, cyclic);
	}
}
