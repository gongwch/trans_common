package jp.co.ais.trans.common.gui;

import java.awt.*;

/**
 * 名前(数)とコンポーネントの対.
 */
public class TNameComponent {

	private int name = -1;

	private Component value = null;

	/**
	 * Constructor.
	 */
	public TNameComponent() {
		//
	}

	/**
	 * Constructor.
	 * 
	 * @param name key値.
	 * @param value keyに対応したComponent.
	 */
	public TNameComponent(int name, Component value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * key値を取得.
	 * 
	 * @return key値
	 */
	public int getName() {
		return this.name;
	}

	/**
	 * key値を設定.
	 * 
	 * @param name key値
	 */
	public void setName(int name) {
		this.name = name;
	}

	/**
	 * Componentを取得.
	 * 
	 * @return コンポーネント
	 */
	public Component getValue() {
		return this.value;
	}

	/**
	 * Componentを設定.
	 * 
	 * @param value コンポーネント
	 */
	public void setValue(Component value) {
		this.value = value;
	}
}
