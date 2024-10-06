package jp.co.ais.trans2.common.gui;

import javax.swing.*;
import javax.swing.table.*;

/**
 * TTableのカラムコンポーネント
 * 
 * @author AIS
 */
public class TTableColumn {

	/** タイトル */
	protected String title = null;

	/** 幅 */
	protected int width = 0;

	/** 水平方向のalign */
	protected int horizontalAlign = SwingConstants.LEFT;

	/** 垂直寄せ */
	protected int verticalAlign = SwingConstants.CENTER;

	/** 自動改行 */
	protected boolean autoWordwrap = false;

	/** 自動改行時有効、テキスト寄せ、デフォルト：中央 */
	protected boolean textAlignCenter = false;

	/** enum */
	protected Enum e;

	/** 表示/非表示 */
	protected boolean visible = true;

	/** Component */
	protected TTableComponent component = null;

	/** 反映先テーブルカラム */
	protected TableColumn column;

	/**
	 * コンストラクター
	 */
	public TTableColumn() {
		// 処理なし
	}

	/**
	 * 列のタイトル、幅を指定してインスタンスを生成する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 */
	public TTableColumn(Enum e, String title, int width) {
		this(e, title, width, SwingConstants.LEFT);
	}

	/**
	 * 列のタイトル、幅を指定してインスタンスを生成する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param isVisible 表示/非表示
	 */
	public TTableColumn(Enum e, String title, int width, boolean isVisible) {
		this(e, title, width, SwingConstants.LEFT, isVisible);
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置を指定してインスタンスを生成する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign) {
		this(e, title, width, horizontalAlign, null);
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置、表示/非表示を指定してインスタンスを生成する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 * @param visible 表示/非表示
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign, boolean visible) {
		this(e, title, width, horizontalAlign, null, visible);
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置、セットするコンポーネントを指定してインスタンスを生成する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 * @param component コンポーネント
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign, TTableComponent component) {

		this(e, title, width, horizontalAlign, component, true);
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置、セットするコンポーネント、表示非表示を指定してインスタンスを生成する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 * @param component コンポーネント
	 * @param visible 表示するか
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign, TTableComponent component, boolean visible) {
		this.e = e;
		this.title = title;
		this.width = width;
		this.horizontalAlign = horizontalAlign;
		this.component = component;
		this.visible = visible;
	}

	/**
	 * Enum
	 * 
	 * @return e Enum
	 */
	public Enum getE() {
		return e;
	}

	/**
	 * タイトルを戻します。
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title タイトルを設定します。
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 表示/非表示
	 * 
	 * @return true:表示
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * 表示/非表示
	 * 
	 * @param visible true:表示
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return 幅を戻します。
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width 幅を設定します。
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return horizontalAlignを戻します。
	 */
	public int getHorizontalAlign() {
		return horizontalAlign;
	}

	/**
	 * @param horizontalAlign horizontalAlignを設定します。
	 */
	public void setHorizontalAlign(int horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	/**
	 * 垂直寄せの取得
	 * 
	 * @return verticalAlignment 垂直寄せ
	 */
	public int getVerticalAlign() {
		return verticalAlign;
	}

	/**
	 * 垂直寄せの設定
	 * 
	 * @param verticalAlign 垂直寄せ
	 */
	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	/**
	 * 自動改行の取得
	 * 
	 * @return autoWordwrap 自動改行
	 */
	public boolean isAutoWordwrap() {
		return autoWordwrap;
	}

	/**
	 * 自動改行の設定
	 * 
	 * @param autoWordwrap 自動改行
	 */
	public void setAutoWordwrap(boolean autoWordwrap) {
		this.autoWordwrap = autoWordwrap;
	}

	/**
	 * 自動改行時有効、テキスト寄せ、デフォルト：中央の取得
	 * 
	 * @return textAlignCenter 自動改行時有効、テキスト寄せ、デフォルト：中央
	 */
	public boolean isTextAlignCenter() {
		return textAlignCenter;
	}

	/**
	 * 自動改行時有効、テキスト寄せ、デフォルト：中央の設定
	 * 
	 * @param textAlignCenter 自動改行時有効、テキスト寄せ、デフォルト：中央
	 */
	public void setTextAlignCenter(boolean textAlignCenter) {
		this.textAlignCenter = textAlignCenter;
	}

	/**
	 * コンポーネント
	 * 
	 * @return コンポーネント
	 */
	public TTableComponent getComponent() {
		return component;
	}

	/**
	 * コンポーネント
	 * 
	 * @param component コンポーネント
	 */
	public void setComponent(TTableComponent component) {
		this.component = component;
	}

	/**
	 * 反映先テーブルカラム
	 * 
	 * @return column 反映先テーブルカラム
	 */
	public TableColumn getColumn() {
		return column;
	}

	/**
	 * 反映先テーブルカラム
	 * 
	 * @param column 反映先テーブルカラム
	 */
	public void setColumn(TableColumn column) {
		this.column = column;
	}
}
