package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.*;

/**
 * �^�u���t�H�[�J�X�ړ��|���V�[(Enter key�̂Ƃ�)
 */
public class TFocusPolicyEnter extends TFocusPolicy {

	/**
	 * constructor.
	 * 
	 * @param list �R���|�[�l���g�̃��X�g
	 */
	public TFocusPolicyEnter(java.util.List<Component> list) {
		super(list);
	}

	/**
	 * �^�u���ړ��ł���(focus�ł���)component���`�F�b�N����B
	 * 
	 * @param comp �R���|�[�l���g
	 * @return true:�L��
	 */
	@Override
	protected boolean isActiveComponent(Component comp) {

		if (comp == null || comp instanceof JTabbedPane || (comp instanceof JButton && !(comp instanceof TButton))) {
			return false;
		}

		if (comp instanceof TButton && !((TButton) comp).isEnterFocusable()) {
			return false;
		}

		if (comp instanceof TCheckBox && !((TCheckBox) comp).isEnterFocusable()) {
			return false;
		}

		if (comp instanceof TToggleButton && !((TToggleButton) comp).isEnterFocusable()) {
			return false;
		}

		return super.isActiveComponent(comp);
	}
}
