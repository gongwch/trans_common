package jp.co.ais.trans.common.gui;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import com.klg.jclass.field.JCTextField;

/**
 * ���̓`�F�b�N�N���X <br>
 * verifyCommit()���\�b�h����������B TTextField, TLabelField, TButtonField��setInputVerifier()���\�b�h �œo�^����B
 */
public abstract class TInputVerifier extends InputVerifier {

	/** false:verify���s��Ȃ� */
	private boolean isCheck = true;

	/**
	 * constructor
	 */
	public TInputVerifier() {
		super();
		isCheck = true;
	}

	/**
	 * verify <br>
	 * verifyCommit���I�[�o�[���C�h���Ă��������B
	 * 
	 * @comp �R���|�[�l���g
	 */
	final public boolean verify(JComponent comp) {

		// JFormattedTextField, JCTextField���p�������R���|�[�l���g�Ȃ�
		// commit����B
		// (getText,vetValue�œ�����l��commit�O�̃f�[�^�̂���)

		this.commitEdit(comp);

		// editable=true�ł���΃`�F�b�N���s���B
		if (this.checkEditable(comp)) {

			// �eFrame�܂���Dialog���擾
			// �����A�C�e����TInputVerifier�C���X�^���X�����L���邱�Ƃ����邽�߁A
			// ����eWindow�𒲂ׂ�B
			Container parent = TGuiUtil.getParentWindow(comp);

			boolean b;

			if (parent != null) {

				// �eFrame,Dialog������΁A���̃C���X�^���X��lock��������B
				// TDialog�Őݒ肷��`�F�b�N�����������Ƌ������Ȃ��悤�ɂ���B

				synchronized (parent) {
					b = this.verifyWhenIsCheck(comp);
				}

			} else {
				b = this.verifyWhenIsCheck(comp);
			}

			return b;

		} else {
			// editable�łȂ���΁AverifyCommit���Ă΂��ɏ��true��Ԃ��B
			return true;
		}
	}

	/**
	 * isCheck()=�`�F�b�N���s���Ƃ��AverifyCommit()���s���B
	 * 
	 * @param comp
	 * @return true:OK false:�s���l
	 */
	protected boolean verifyWhenIsCheck(JComponent comp) {

		// �I�[�o�[���C�h�p�̃��\�b�h���Ăяo��
		boolean b;

		// ClientLogger.debug("**verify");

		if (this.isCheck) {

			// *****************************************
			// �I�[�o�[���C�h�s�����̓`�F�b�N���\�b�h�ďo
			// *****************************************
			b = this.verifyCommit(comp);

		} else {

			// ClientLogger.debug("verify masked");
			b = true;
		}

		// ClientLogger.debug("**verify:"+b);
		return b;
	}

	/**
	 * JFormattedTextField, JCTextField���p�������R���|�[�l���g�Ȃ� commit����B
	 * 
	 * @param comp
	 */
	protected void commitEdit(JComponent comp) {

		try {
			if (comp instanceof JFormattedTextField) {
				((JFormattedTextField) comp).commitEdit();

			} else if (comp instanceof JCTextField) {
				((JCTextField) comp).commitEdit();
			}

		} catch (ParseException e) {
			//
		}
	}

	/**
	 * JFormattedTextField, JCTextField���p�������R���|�[�l���g�Ȃ� editable�����ׂ�B
	 * 
	 * @param comp ��܃R���|�[�l���g
	 * @return true:Edit�\���
	 */
	protected boolean checkEditable(JComponent comp) {

		if (comp instanceof JFormattedTextField) {
			return ((JFormattedTextField) comp).isEditable();

		} else if (comp instanceof JCTextField) {
			return ((JCTextField) comp).isEditable();
		}

		// ����ȊO��editable�Ȃ��Ƃɂ���B
		return true;
	}

	/**
	 * �I�[�o�[���C�h�pverify���\�b�h <br>
	 * InputVerifier#verify()�̑����TInputVerifier�ł� verifyCommit()���`���Ă��������B
	 * 
	 * @param comp
	 * @return true:OK
	 */
	abstract public boolean verifyCommit(JComponent comp);

	/**
	 * verify�L���� <br>
	 * 
	 * @param b =true�L��(�ʏ퓮��) =false����(vrifyCommit���Ă΂���true��Ԃ�
	 */
	public void setCheck(boolean b) {
		this.isCheck = b;
	}

	/**
	 * verify�L���� <br>
	 * 
	 * @return b =true�L��(�ʏ퓮��) =false����(vrifyCommit���Ă΂���true��Ԃ�
	 */
	public boolean isCheck() {
		return this.isCheck;
	}

	/**
	 * ����R���e�i�z���̃A�C�e����verify�L��/���� �ꊇ�ݒ�
	 * 
	 * @param cont �R���e�i(Panel, Dialog�Ȃ�)
	 * @param active setActive()�ݒ�t���O
	 */
	public static void setActiveChildren(Container cont, boolean active) {

		if (cont == null) {
			return;
		}

		// �q�R���|�[�l���g�擾
		Component[] comp = cont.getComponents();
		if (comp == null) {
			return;
		}

		// ���ׂĂ̎q�R���|�[�l���g�ɂ���
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JComponent) {
				// TInputVerifier��InputVerifier�Ƃ��Ďg���Ă����
				// verify�̗L���t���O��ݒ肷��
				InputVerifier iv = ((JComponent) comp[i]).getInputVerifier();
				if (iv != null && iv instanceof TInputVerifier) {
					((TInputVerifier) iv).setCheck(active);
				}
			}

			if (comp[i] instanceof Container) {
				// �ċA�ďo
				setActiveChildren((Container) comp[i], active);
			}
		}
	}
}
