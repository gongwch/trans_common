package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;

/**
 * ダッシュボードのコントローラー
 * 
 * @author AIS
 */
public class ManualInitialCtrl extends TController implements TMainInitialInterface {

	/** ダッシュボード画面 */
	protected ManualInitial mainView = null;

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#getName()
	 */
	public String getName() {
		return "Manual";
	}

	/**
	 * 画面識別子
	 */
	@Override
	public String getRealUID() {
		return "Manual";
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#init()
	 */
	public void init() {
		try {

			// 画面生成
			createMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#afterCreate()
	 */
	public void afterCreate() {
		try {

			// 処理なし

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#getAddonButtonList()
	 */
	public List<TButton> getAddonButtonList() {
		List<TButton> btnList = new ArrayList<TButton>();
		btnList.add(mainView.btnManual);
		return btnList;
	}

	/**
	 * ダッシュボードのファクトリ。イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new ManualInitial(getCompany(), TMainCtrl.instance.mainView, false);

		addMainViewEvent();
	}

	/**
	 * ダッシュボードのイベント定義。
	 */
	protected void addMainViewEvent() {

		// DASHボタン押下
		mainView.btnManual.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TMainCtrl.getInstance().mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnManual_Click();
				TMainCtrl.getInstance().mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 画面を初期化する
	 */
	protected void initMainView() {
		// 処理なし
	}

	/**
	 * ダッシュボタン
	 */
	protected void btnManual_Click() {
		try {

			ManualAttachListDialogCtrl ctrl = new ManualAttachListDialogCtrl(TMainCtrl.getInstance().mainView.pnlLfAndTime);

			// TODO:暫定：AIS及びadminは編集可能
			if (Util.equals(getUserCode().toUpperCase(), "AIS") //
				|| Util.equals(getUserCode().toLowerCase(), "admin")) {
				ctrl.dialog.btnAdd.setVisible(true);
				ctrl.dialog.btnDelete.setVisible(true);
			} else {
				ctrl.dialog.btnAdd.setVisible(false);
				ctrl.dialog.btnDelete.setVisible(false);
			}

			ctrl.show();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return DashManager
	 */
	protected Class getManager() {
		// return ManualManager.class;
		return null;
	}
}
