package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 科目体系フィールド
 * 
 * @author moriya
 */
public class TItemSystemField extends TButtonField {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** コントロールクラス */
	private TItemSystemFieldCtrl ctrl;

	/** Callクラス */
	private List<CallBackListener> callCtrlList;

	/**
	 * コンストラクタ
	 */
	public TItemSystemField() {
		super();
		initComponents();
		this.ctrl = new TItemSystemFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/** 画面構築 */
	private void initComponents() {

		this.setButtonSize(85);
		this.setFieldSize(35);
		this.setLangMessageID("C00609");
		this.setMaxLength(2);
		this.setNoticeSize(230);
		this.setImeMode(false);

		// ロストフォーカス時イベント
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

		// ボタン押下時イベント
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
	 * Callクラスをセットする。
	 * 
	 * @param callCtrl CallControlクラス
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}
}
