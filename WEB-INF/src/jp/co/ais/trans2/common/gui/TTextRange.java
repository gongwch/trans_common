package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * テキスト範囲フィールド
 * 
 * @author AIS
 */
public class TTextRange extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -5671715054170282481L;

	/** コンポーネント（開始） */
	public TLabelField fieldFrom;

	/** コンポーネント（終了） */
	public TLabelField fieldTo;

	/** 高さ */
	public static final int height = 20;

	/**
	 * コンストラクタ.
	 */
	public TTextRange() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		fieldFrom = new TLabelField();
		fieldTo = new TLabelField();
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setLayout(null);

		// 開始
		fieldFrom.setLocation(0, 0);
		fieldFrom.setSize(fieldFrom.getFieldSize() + fieldFrom.getLabelSize() + 5, height);
		add(fieldFrom);

		// 終了
		fieldTo.setLabelSize(20);
		fieldTo.setSize(fieldTo.getFieldSize() + fieldTo.getLabelSize() + 5, height);
		fieldTo.setLangMessageID("C01333");
		fieldTo.setLocation(fieldFrom.getWidth(), 0);
		add(fieldTo);

		// 全体のサイズを設定
		setSize();

	}

	/**
	 * 全体のサイズ設定
	 */
	public void setSize() {
		setSize(fieldFrom.getWidth() + fieldTo.getWidth() + 5, 20);
	}

	/**
	 * FROM、TO、全体のサイズを再設定
	 */
	public void resize() {
		fieldFrom.setSize(fieldFrom.getFieldSize() + fieldFrom.getLabelSize() + 5, height);
		fieldTo.setSize(fieldTo.getFieldSize() + fieldTo.getLabelSize() + 5, height);
		fieldTo.setLocation(fieldFrom.getWidth(), 0);
		setSize(fieldFrom.getWidth() + fieldTo.getWidth() + 5, 20);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		fieldFrom.setTabControlNo(tabControlNo);
		fieldTo.setTabControlNo(tabControlNo);
	}

	/**
	 * 「FROM <= TO」を比較する.
	 * 
	 * @return 「FROM <= TO」または、どちらかがブランクならばtrue.
	 */
	public boolean isSmallerFrom() {
		return Util.isSmallerThen(fieldFrom.getValue(), fieldTo.getValue());
	}

	/**
	 * コンポーネント（開始）を取得する
	 * 
	 * @return コンポーネント（開始）
	 */
	public TLabelField getFieldFrom() {
		return fieldFrom;
	}

	/**
	 * コンポーネント（終了）を取得する
	 * 
	 * @return コンポーネント（終了）
	 */
	public TLabelField getFieldTo() {
		return fieldTo;
	}

	/**
	 * 編集状態を設定する
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		fieldFrom.setEditable(isEditable);
		fieldTo.setEditable(isEditable);
	}

	/**
	 * コンポーネント（開始）の値を取得する
	 * 
	 * @return コンポーネント（開始）の値
	 */
	public String getValueFrom() {
		return fieldFrom.getValue();
	}

	/**
	 * コンポーネント（終了）の値を取得する
	 * 
	 * @return コンポーネント（終了）の値
	 */
	public String getValueTo() {
		return fieldTo.getValue();
	}

	/**
	 * 値を設定する
	 * 
	 * @param valueFrom コンポーネント（開始） 設定値
	 * @param valueTo コンポーネント（終了） 設定値
	 */
	public void setValue(String valueFrom, String valueTo) {
		fieldFrom.setValue(valueFrom);
		fieldTo.setValue(valueTo);
	}

	/**
	 * ラベルを設定する
	 * 
	 * @param value ラベルテキスト
	 * @param Width ラベルサイズ
	 */
	public void setLabelFrom(String value, int Width) {
		fieldFrom.setLangMessageID(value);
		fieldFrom.setLabelSize(Width);
		resize();
	}

	/**
	 * フィールドサイズを設定する
	 * 
	 * @param size
	 */
	public void setFieldSize(int size) {
		fieldFrom.setFieldSize(size);
		fieldTo.setFieldSize(size);
		resize();
	}

	/**
	 * 最大桁数を設定する
	 * 
	 * @param length
	 */
	public void setMaxLength(int length) {
		fieldFrom.setMaxLength(length);
		fieldTo.setMaxLength(length);
	}

	/**
	 * IMEを設定する
	 * 
	 * @param flag
	 */
	public void setImeMode(boolean flag) {
		fieldFrom.setImeMode(flag);
		fieldTo.setImeMode(flag);
	}

	/**
	 * クリア
	 */
	public void clear() {
		fieldFrom.clear();
		fieldTo.clear();
	}
}
