package jp.co.ais.trans2.common.ui;

import java.awt.event.*;

import jp.co.ais.trans2.common.client.*;

/**
 * バージョン表示ダイアログCTRL
 */
public class TVersionDialogCtrl extends TController {

	/** ダイアログ */
	protected TVersionDialog editView;

	/**
	 * コンストラクター
	 * 
	 * @param editView
	 */
	public TVersionDialogCtrl(TVersionDialog editView) {
		this.editView = editView;

		initEvents();
	}

	/**
	 * イベント初期化
	 */
	protected void initEvents() {

		editView.btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnOK_Click();
			}
		});
	}

	/**
	 * OKボタン押下処理
	 */
	protected void btnOK_Click() {
		editView.dispose();
	}

}
