package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.im.*;
import java.lang.Character.Subset;

import jp.co.ais.trans.common.gui.*;

/**
 * ���p�Ő���e�L�X�g�t�B�[���h
 */
public class THalfAngleTextField extends TTextField {

	/** ���{��T�u�Z�b�g */
	protected static final Subset[] SUBSET_JP_HALF_KANA = new Subset[] { InputSubset.HALFWIDTH_KATAKANA };

	/**
	 * �R���X�g���N�^.
	 */
	public THalfAngleTextField() {
		super();

		// ���K�\���ł̓��͐���
		super.setRegex("[ -~�-�]");
	}

	/**
	 * FocusGained�C�x���g�����m���AOldText��}��.
	 * 
	 * @param e �C�x���g
	 */
	@Override
	protected void focusGainedActionPerformed(FocusEvent e) {

		// ����Window����t�H�[�J�X�������Ƃ���null�ɂȂ�B
		Component comp = e.getOppositeComponent();

		if (comp != null) {
			// �R���|�[�l���g�̐e��Frame,Dialog�������ł��ꂠ��oldText���㏑������B
			if (comp instanceof Container) {

				Container a = TGuiUtil.getParentFrameOrDialog((Container) comp);
				Container b = TGuiUtil.getParentFrameOrDialog(this);

				if (a.equals(b)) {

					this.pushOldText();
				}
			}
		}

		this.selectAll();

		// IME���̓��[�h����
		if (isChangeCharacterSubsets && this.getInputContext() != null) {
			if (!isNoImeControl) {
				// IME������s���ꍇ�̂�
				Subset[] subsets = isImeMode() ? SUBSET_JP_HALF_KANA : null;
				this.getInputContext().setCharacterSubsets(subsets);
			}
		}
	}
}
