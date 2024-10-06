package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * �g���I�yGUI����Util
 */
public class OPGuiUtil {

	/** true:���x�����[�h */
	public static boolean isLabelMode;

	static {
		// ������
		isLabelMode = ClientConfig.isFlagOn("trans.op.ref.label.mode");
	}

	/**
	 * @return true:���x�����[�h
	 */
	public static boolean isLabelMode() {
		return isLabelMode;
	}

	/**
	 * �R���|�[�l���g��z�u����
	 * 
	 * @param ref
	 */
	public static void allocateComponents(TReference ref) {

		GridBagConstraints gc = new GridBagConstraints();

		TButton btn = ref.btn;
		TLabel lbl = ref.lbl;
		TTextField code = ref.code;
		TTextField name = ref.name;

		ref.setLayout(new GridBagLayout());
		ref.setOpaque(false);

		// ���x��
		TGuiUtil.setComponentSize(lbl, new Dimension(80, 20));
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setOpaque(false);
		gc.insets = new Insets(0, 0, 0, 5);
		ref.add(lbl, gc);
		btn.setVisible(false);

		// �R�[�h
		TGuiUtil.setComponentSize(code, new Dimension(0, 20));

		// ����
		name.setEditable(true);
		TGuiUtil.setComponentSize(name, new Dimension(150, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		ref.add(name, gc);

		ref.resize();
	}

	/**
	 * �R���|�[�l���g��z�u����<br>
	 * �ʉ݂͓���(���̕s�v)
	 * 
	 * @param ref
	 */
	public static void allocateComponents(TCurrencyReference ref) {

		GridBagConstraints gc = new GridBagConstraints();

		TButton btn = ref.btn;
		TLabel lbl = ref.lbl;
		TTextField code = ref.code;
		TTextField name = ref.name;

		ref.setLayout(new GridBagLayout());
		ref.setOpaque(false);

		// ���x��
		TGuiUtil.setComponentSize(lbl, new Dimension(80, 20));
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setOpaque(false);
		gc.insets = new Insets(0, 0, 0, 5);
		ref.add(lbl, gc);
		btn.setVisible(false);

		// �R�[�h
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(3);
		gc.insets = new Insets(0, 0, 0, 0);
		ref.add(code, gc);
		code.setAllowedSpace(false);
		code.setImeMode(false);

		// ����
		name.setVisible(false);

		ref.resize();
	}
}
