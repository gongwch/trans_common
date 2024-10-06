package jp.co.ais.trans.common.gui;

import java.awt.*;

/**
 * ���O(��)�ƃR���|�[�l���g�̑�.
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
	 * @param name key�l.
	 * @param value key�ɑΉ�����Component.
	 */
	public TNameComponent(int name, Component value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * key�l���擾.
	 * 
	 * @return key�l
	 */
	public int getName() {
		return this.name;
	}

	/**
	 * key�l��ݒ�.
	 * 
	 * @param name key�l
	 */
	public void setName(int name) {
		this.name = name;
	}

	/**
	 * Component���擾.
	 * 
	 * @return �R���|�[�l���g
	 */
	public Component getValue() {
		return this.value;
	}

	/**
	 * Component��ݒ�.
	 * 
	 * @param value �R���|�[�l���g
	 */
	public void setValue(Component value) {
		this.value = value;
	}
}
