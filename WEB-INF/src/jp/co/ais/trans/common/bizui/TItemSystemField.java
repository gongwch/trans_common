package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �Ȗڑ̌n�t�B�[���h
 * 
 * @author moriya
 */
public class TItemSystemField extends TButtonField {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �R���g���[���N���X */
	private TItemSystemFieldCtrl ctrl;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/**
	 * �R���X�g���N�^
	 */
	public TItemSystemField() {
		super();
		initComponents();
		this.ctrl = new TItemSystemFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/** ��ʍ\�z */
	private void initComponents() {

		this.setButtonSize(85);
		this.setFieldSize(35);
		this.setLangMessageID("C00609");
		this.setMaxLength(2);
		this.setNoticeSize(230);
		this.setImeMode(false);

		// ���X�g�t�H�[�J�X���C�x���g
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				boolean sts = ctrl.getItemSystemInfo("lostFocus");

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return sts;
			}
		});

		// �{�^���������C�x���g
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.showItemDialog();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

			}
		});
	}

	/**
	 * Call�N���X���Z�b�g����B
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}
}
